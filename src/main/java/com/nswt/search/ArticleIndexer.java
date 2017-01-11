package com.nswt.search;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.Date;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.MultiSearcher;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.nswt.cms.dataservice.ColumnUtil;
import com.nswt.cms.document.Article;
import com.nswt.cms.pub.CatalogUtil;
import com.nswt.cms.pub.PubFun;
import com.nswt.cms.pub.SiteUtil;
import com.nswt.framework.Config;
import com.nswt.framework.data.DBUtil;
import com.nswt.framework.data.DataRow;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.NumberUtil;
import com.nswt.framework.utility.StringUtil;
import com.nswt.schema.ZCFullTextSchema;
import com.nswt.search.index.IndexUtil;
import com.nswt.search.index.Indexer;

/**
 * 文章索引类
 * 
 * @Author NSWT
 * @Date 2008-12-6
 * @Mail nswt@nswt.com.cn
 */
public class ArticleIndexer extends Indexer {
	private long id;

	private int PageSize = 200;

	public ArticleIndexer(long fullTextID) {
		id = fullTextID;
		setPath(Config.getContextRealPath() + "WEB-INF/data/index/" + id + "/");
	}

	/**
	 * 第一次需创建索引
	 */
	public void create() {
		try {
			ZCFullTextSchema ft = new ZCFullTextSchema();
			ft.setID(id);
			if (!ft.fill()) {
				return;
			}
			if (!ft.getType().equals("Article")) {// 暂时只支持文章索引
				return;
			}
			if ("true".equals(Config.getValue("App.MinimalMemory"))) {
				PageSize = 50;
			}
			String rela = ft.getRelaText();
			if (rela.indexOf("-1") >= 0) {
				QueryBuilder qb = new QueryBuilder("select ID from ZCCatalog where SiteID=?", ft.getSiteID());
				DataTable catalogs = qb.executeDataTable();
				for (int j = 0; j < catalogs.getRowCount(); j++) {
					qb = new QueryBuilder("select * from zcarticle where siteid=" + ft.getSiteID() + " and status="
							+ Article.STATUS_PUBLISHED + " and catalogid=?", catalogs.getLong(j, "ID"));
					int total = DBUtil.getCount(qb);
					for (int i = 0; i * PageSize < total; i++) {
						DataTable dt = qb.executePagedDataTable(PageSize, i);
						CatalogUtil.addCatalogName(dt, "CatalogID");
						ColumnUtil.extendDocColumnData(dt, catalogs.getString(j, "ID"));
						add(dt, writer);
					}
				}
			} else {
				String[] catalogs = rela.split(",");
				for (int j = 0; j < catalogs.length; j++) {
					QueryBuilder qb = new QueryBuilder("select * from zcarticle where siteid=" + ft.getSiteID()
							+ " and status=" + Article.STATUS_PUBLISHED + " and catalogid=?",
							Long.parseLong(catalogs[j]));
					int total = DBUtil.getCount(qb);
					for (int i = 0; i * PageSize < total; i++) {
						DataTable dt = qb.executePagedDataTable(PageSize, i);
						CatalogUtil.addCatalogName(dt, "CatalogID");
						ColumnUtil.extendDocColumnData(dt, catalogs[j]);
						add(dt, writer);
					}
				}
			}
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void index(DataTable dt, IndexWriter writer, boolean updateFlag) throws CorruptIndexException, IOException {
		Field[] fields = new Field[dt.getColCount() + 1];
		for (int n = 0; n < dt.getColCount(); n++) {
			String columnName = dt.getDataColumn(n).getColumnName();
			if (columnName.equalsIgnoreCase("URL")) {
				fields[n] = new Field("URL", "", Field.Store.YES, Field.Index.NO);
			} else if (columnName.equalsIgnoreCase("TITLE")) {
				fields[n] = new Field("TITLE", "", Field.Store.YES, Field.Index.ANALYZED);
			} else if (columnName.equalsIgnoreCase("Content")) {
				fields[n] = new Field("CONTENT", "", Field.Store.YES, Field.Index.ANALYZED);
			} else {
				fields[n] = new Field(columnName.toUpperCase(), "", Field.Store.YES, Field.Index.NOT_ANALYZED);
			}
		}
		fields[fields.length - 1] = new Field("_KEYWORD", "", Field.Store.NO, Field.Index.ANALYZED);
		for (int i = 0; i < dt.getRowCount(); i++) {
			Document doc = new Document();
			DataRow dr = dt.getDataRow(i);
			String content = IndexUtil.getTextFromHtml(dr.getString("Content"));

			Date d1 = dr.getDate("AddTime");
			Date d2 = dr.getDate("ModifyTime");
			if (d1 != null && d1.getTime() > nextLastDate.getTime()) {
				nextLastDate = d1;
			}
			if (d2 != null && d2.getTime() > nextLastDate.getTime()) {
				nextLastDate = d2;
			}

			StringBuffer sb = new StringBuffer();
			sb.append(dr.getString("Title"));
			sb.append(" ");
			sb.append(content);
			try {
				String url = PubFun.getDocURL(dr);
				url = url.replaceAll("/+", "/");

				for (int n = 0; n < dt.getColCount(); n++) {
					String columnName = dt.getDataColumn(n).getColumnName();
					Field field = fields[n];
					if (columnName.equalsIgnoreCase("URL")) {
						field.setValue(url);
					} else if (columnName.equalsIgnoreCase("Content")) {
						field.setValue(content);
					} else {
						field.setValue(dr.getString(n));
					}
					doc.add(field);
				}
				Field field = fields[fields.length - 1];
				field.setValue(sb.toString());
				doc.add(field);
				if (updateFlag && StringUtil.isNotEmpty(dt.getString(i, "ModifyTime"))
						&& dt.getDate(i, "AddTime").getTime() < lastDate.getTime()) {
					writer.updateDocument(new Term("ID", dt.getString(i, "ID")), doc);
				} else {
					writer.addDocument(doc);
				}
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
	}

	private void add(DataTable dt, IndexWriter writer) throws CorruptIndexException, IOException {
		index(dt, writer, false);
	}

	private void update(DataTable dt, IndexWriter writer) throws CorruptIndexException, IOException {
		index(dt, writer, true);
	}

	/**
	 * 更新索引
	 */
	public void update() {
		try {
			ZCFullTextSchema ft = new ZCFullTextSchema();
			ft.setID(id);
			if (!ft.fill()) {
				return;
			}
			if (!ft.getType().equals("Article")) {// 暂时只支持文章索引
				return;
			}
			if ("true".equals(Config.getValue("App.MinimalMemory"))) {
				PageSize = 50;
			}
			String rela = ft.getRelaText();
			if (rela.indexOf("-1") >= 0) {
				DataTable catalogs = new QueryBuilder("select id from zccatalog where siteid = " + ft.getSiteID()
						+ " and exists (select '' from zcarticle where zcarticle.catalogid=zccatalog.id and siteid="
						+ ft.getSiteID() + " and (addtime>=?" + " or modifytime>=?) and status="
						+ Article.STATUS_PUBLISHED + ")", lastDate, lastDate).executeDataTable();
				for (int j = 0; j < catalogs.getRowCount(); j++) {
					QueryBuilder qb = new QueryBuilder("select * from zcarticle where siteid=" + ft.getSiteID()
							+ " and CatalogID=? and (addtime>=? or modifytime>=?) and status="
							+ Article.STATUS_PUBLISHED, catalogs.getLong(j, "id"), lastDate);
					qb.add(lastDate);
					int total = DBUtil.getCount(qb);
					for (int i = 0; i * PageSize < total; i++) {
						DataTable dt = qb.executePagedDataTable(PageSize, i);
						CatalogUtil.addCatalogName(dt, "CatalogID");
						ColumnUtil.extendDocColumnData(dt, catalogs.getLong(j, "id"));
						update(dt, writer);
					}
				}
				DataTable dt = new QueryBuilder("select id from bzcarticle where status=" + Article.STATUS_PUBLISHED
						+ " and siteid=? and backuptime>?", ft.getSiteID(), lastDate).executeDataTable();
				for (int i = 0; i < dt.getRowCount(); i++) {
					writer.deleteDocuments(new Term("ID", dt.getString(i, 0)));
				}
				dt = new QueryBuilder("select id from zcarticle where status=" + Article.STATUS_OFFLINE
						+ " and siteid=? and modifytime>?", ft.getSiteID(), lastDate).executeDataTable();
				for (int i = 0; i < dt.getRowCount(); i++) {
					writer.deleteDocuments(new Term("ID", dt.getString(i, 0)));
				}
			} else {
				String[] catalogs = rela.split(",");
				for (int j = 0; j < catalogs.length; j++) {
					QueryBuilder qb = new QueryBuilder("select * from zcarticle where status="
							+ Article.STATUS_PUBLISHED + " and catalogid=? and (addtime>=? or modifytime>=?)",
							Long.parseLong(catalogs[j]), lastDate);
					qb.add(lastDate);
					int total = DBUtil.getCount(qb);
					for (int i = 0; i * PageSize < total; i++) {
						DataTable dt = qb.executePagedDataTable(PageSize, i);
						CatalogUtil.addCatalogName(dt, "CatalogID");
						ColumnUtil.extendDocColumnData(dt, catalogs[j]);
						update(dt, writer);
					}
				}
				DataTable dt = new QueryBuilder("select id from bzcarticle where status=" + Article.STATUS_PUBLISHED
						+ " and siteid=? and catalogid in (" + rela + ") and backuptime>?", ft.getSiteID(), lastDate)
						.executeDataTable();
				for (int i = 0; i < dt.getRowCount(); i++) {
					writer.deleteDocuments(new Term("ID", dt.getString(i, 0)));
				}
				dt = new QueryBuilder("select id from zcarticle where status=" + Article.STATUS_OFFLINE
						+ " and siteid=? and modifytime>?", ft.getSiteID(), lastDate).executeDataTable();
				for (int i = 0; i < dt.getRowCount(); i++) {
					writer.deleteDocuments(new Term("ID", dt.getString(i, 0)));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static SearchResult search(SearchParameters sps) {
		try {
			long t = System.currentTimeMillis();
			IndexSearcher searcher = new IndexSearcher(FSDirectory.open(new File(Config.getContextRealPath()
					+ "WEB-INF/data/index/" + sps.getIndexID())), true);

			if (sps.getBooleanQuery().clauses().size() == 0) {
				MatchAllDocsQuery maq = new MatchAllDocsQuery();
				sps.addQuery(maq);
			}

			SimpleHTMLFormatter formatter = new SimpleHTMLFormatter("<font color=red>", "</font>");
			Highlighter highlighter = new Highlighter(formatter, new QueryScorer(sps.getBooleanQuery()));
			int abstractLength = 150;
			highlighter.setTextFragmenter(new SimpleFragmenter(abstractLength));
			DataTable dt = new DataTable();

			int start = sps.getPageIndex() * sps.getPageSize();
			TopDocs docs = null;
			if (sps.getSort() != null) {
				docs = searcher.search(sps.getBooleanQuery(), sps.getDateRangeFilter(), start + sps.getPageSize(),
						sps.getSort());
			} else {
				docs = searcher.search(sps.getBooleanQuery(), sps.getDateRangeFilter(), start + sps.getPageSize());
			}

			for (int i = start; i < start + sps.getPageSize() && i < docs.scoreDocs.length; i++) {
				Document doc = searcher.doc(docs.scoreDocs[i].doc);
				if (i == start) {
					Object[] fields = doc.getFields().toArray();
					for (int j = 0; j < fields.length; j++) {
						String name = ((Field) fields[j]).name();
						dt.insertColumn(name);
					}
				}
				String title = doc.get("TITLE");
				TokenStream tokenStream = new IKAnalyzer().tokenStream("TITLE", new StringReader(title));
				String tmp = highlighter.getBestFragment(tokenStream, title);
				if (StringUtil.isNotEmpty(tmp)) {
					title = tmp;
				}
				String content = doc.get("CONTENT");
				content = StringUtil.replaceEx(content, "<", "&lt;");
				content = StringUtil.replaceEx(content, ">", "&gt;");
				tokenStream = new IKAnalyzer().tokenStream("CONTENT", new StringReader(content));
				tmp = highlighter.getBestFragment(tokenStream, content);
				if (StringUtil.isNotEmpty(tmp)) {
					content = tmp.trim();
				} else {
					if (content.length() > abstractLength) {
						int index1 = content.indexOf("</", abstractLength);
						int index2 = content.indexOf("<", abstractLength);
						int index3 = content.indexOf(">", abstractLength);
						if (index1 >= 0 && index1 == index2) {// 说明最近一个的标签是</font>
							content = content.substring(0, index3 + 1);
						} else if (index3 >= 0 && index3 < index2) {// 说明从</font>中间拆开了,并且后面还有标签
							content = content.substring(0, index3 + 1);
						} else if (index3 >= 0 && index2 < 0) {// 说明从</font>中间拆开了,并且后面再也没有标签了
							content = content.substring(0, index3 + 1);
						} else {
							content = content.substring(0, abstractLength);
						}
					}
				}
				dt.insertRow(new String[dt.getColCount()]);
				for (int j = 0; j < dt.getColCount(); j++) {
					dt.set(i - start, j, doc.get(dt.getDataColumn(j).getColumnName()));
				}
				dt.set(i - start, "TITLE", title);
				dt.set(i - start, "CONTENT", content);
				String url = dt.getString(i - start, "URL");
				if (url.indexOf("://") < 0) {
					String siteUrl = SiteUtil.getURL(dt.getString(i - start, "SITEID"));
					if (!siteUrl.endsWith("/")) {
						siteUrl = siteUrl + "/";
					}
					if (url.startsWith("/")) {
						url = url.substring(1);
					}
					url = siteUrl + url;
				}
				dt.set(i - start, "URL", url);
			}
			searcher.close();

			SearchResult r = new SearchResult();
			r.Data = dt;
			r.Total = docs.totalHits;
			r.UsedTime = NumberUtil.round((System.currentTimeMillis() - t) * 1.0 / 1000, 3);
			return r;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static SearchResult multiSearch(SearchParameters sps,String[] index) {
		try {
			long t = System.currentTimeMillis();
			Mapx map=new Mapx();
			for (int i = 0; i < index.length; i++) {
				map.put(""+i,  new IndexSearcher(FSDirectory.open(new File(Config.getContextRealPath()
					+ "WEB-INF/data/index/" +index[i])), true));
			}
			Object[] valueArray=map.valueArray();
			IndexSearcher[] indexSearcher=new IndexSearcher[map.valueArray().length];
			for(int i=0;i<valueArray.length;i++){
				indexSearcher[i]=(IndexSearcher)valueArray[i];
			}
			MultiSearcher search = new MultiSearcher(indexSearcher);

			if (sps.getBooleanQuery().clauses().size() == 0) {
				MatchAllDocsQuery maq = new MatchAllDocsQuery();
				sps.addQuery(maq);
			}

			SimpleHTMLFormatter formatter = new SimpleHTMLFormatter("<font color=red>", "</font>");
			Highlighter highlighter = new Highlighter(formatter, new QueryScorer(sps.getBooleanQuery()));
			int abstractLength = 150;
			highlighter.setTextFragmenter(new SimpleFragmenter(abstractLength));
			DataTable dt = new DataTable();

			int start = sps.getPageIndex() * sps.getPageSize();
			TopDocs docs = null;
			if (sps.getSort() != null) {
				docs = search.search(sps.getBooleanQuery(), sps.getDateRangeFilter(), start + sps.getPageSize(), sps
						.getSort());
			} else {
				docs = search.search(sps.getBooleanQuery(), sps.getDateRangeFilter(), start + sps.getPageSize());
			}

			for (int i = start; i < start + sps.getPageSize() && i < docs.scoreDocs.length; i++) {
				Document doc = search.doc(docs.scoreDocs[i].doc);
				if (i == start) {
					Object[] fields = doc.getFields().toArray();
					for (int j = 0; j < fields.length; j++) {
						String name = ((Field) fields[j]).name();
						dt.insertColumn(name);
					}
				}
				String title = doc.get("TITLE");
				TokenStream tokenStream = new IKAnalyzer().tokenStream("TITLE", new StringReader(title));
				// System.out.println(title);
				String tmp = highlighter.getBestFragment(tokenStream, title);
				if (StringUtil.isNotEmpty(tmp)) {
					title = tmp;
				}
				String content = doc.get("CONTENT");
				content = StringUtil.replaceEx(content, "<", "&lt;");
				content = StringUtil.replaceEx(content, ">", "&gt;");
				tokenStream = new IKAnalyzer().tokenStream("CONTENT", new StringReader(content));
				tmp = highlighter.getBestFragment(tokenStream, content);
				if (StringUtil.isNotEmpty(tmp)) {
					content = tmp.trim();
				} else {
					if (content.length() > abstractLength) {
						int index1 = content.indexOf("</", abstractLength);
						int index2 = content.indexOf("<", abstractLength);
						int index3 = content.indexOf(">", abstractLength);
						if (index1 >= 0 && index1 == index2) {// 说明最近一个的标签是</font>
							content = content.substring(0, index3 + 1);
						} else if (index3 >= 0 && index3 < index2) {// 说明从</font>中间拆开了,并且后面还有标签
							content = content.substring(0, index3 + 1);
						} else if (index3 >= 0 && index2 < 0) {// 说明从</font>中间拆开了,并且后面再也没有标签了
							content = content.substring(0, index3 + 1);
						} else {
							content = content.substring(0, abstractLength);
						}
					}
				}
				dt.insertRow(new String[dt.getColCount()]);
				for (int j = 0; j < dt.getColCount(); j++) {
					dt.set(i - start, j, doc.get(dt.getDataColumn(j).getColumnName()));
				}
				dt.set(i - start, "TITLE", title);
				dt.set(i - start, "CONTENT", content);
				String url = dt.getString(i - start, "URL");
				if (url.indexOf("://") < 0) {
					String siteUrl = SiteUtil.getURL(dt.getString(i - start, "SITEID"));
					if (!siteUrl.endsWith("/")) {
						siteUrl = siteUrl + "/";
					}
					if (url.startsWith("/")) {
						url = url.substring(1);
					}
					url = siteUrl + url;
				}
				dt.set(i - start, "URL", url);
			}
			search.close();

			SearchResult r = new SearchResult();
			r.Data = dt;
			r.Total = docs.totalHits;
			r.UsedTime = NumberUtil.round((System.currentTimeMillis() - t) * 1.0 / 1000, 3);
			return r;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
