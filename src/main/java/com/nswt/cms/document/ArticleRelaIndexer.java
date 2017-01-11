package com.nswt.cms.document;

import java.io.File;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.FSDirectory;

import com.nswt.cms.pub.CatalogUtil;
import com.nswt.framework.Config;
import com.nswt.framework.data.DBUtil;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.utility.FileUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.NumberUtil;
import com.nswt.framework.utility.StringUtil;
import com.nswt.search.SearchParameters;
import com.nswt.search.SearchResult;
import com.nswt.search.index.IndexUtil;
import com.nswt.search.index.Indexer;

/**
 * 日期 : 2009-11-10 <br>
 * 作者: NSWT <br>
 * 邮箱：nswt@nswt.com.cn <br>
 */
public class ArticleRelaIndexer extends Indexer {
	private static final int PageSize = 500;
	private static Object mutex = new Object();

	public ArticleRelaIndexer() {
		setPath(Config.getContextRealPath() + "WEB-INF/data/index/keyword/");
	}

	private static Mapx prepareSettingMap() {
		QueryBuilder qb = new QueryBuilder(
				"select InnerCode,KeywordSetting from ZCCatalog where KeywordSetting is not null");
		Mapx map = qb.executeDataTable().toMapx(0, 1);
		Object[] ks = map.keyArray();
		for (int i = 0; i < ks.length; i++) {
			String k = ks[i].toString();
			String v = map.getString(k);
			if (v == null) {
				v = "";
			}
			while (k.length() > 6) {
				k = k.substring(0, k.length() - 6);
				String setting = map.getString(k);
				if (setting != null) {
					v = v + "," + setting;
				}
			}
			map.put(k, v);
		}
		return map;
	}

	private void index(DataTable dt, Mapx map, boolean extractFlag) {
		for (int m = 0; m < dt.getRowCount(); m++) {
			String CatalogInnerCode = dt.getString(m, "CatalogInnerCode");
			String Title = dt.getString(m, "Title");
			String Keyword = dt.getString(m, "Keyword");
			if (extractFlag) {
				String Content = dt.getString(m, "Content");
				Keyword = StringUtil.join(IndexUtil.getKeyword(Title, Content), " ");
			}
			Keyword = "," + Keyword.replace(' ', ',') + ",";
			String SiteID = dt.getString(m, "SiteID");
			String ID = dt.getString(m, "ID");
			String k = CatalogInnerCode;
			String Setting = map.getString(k);
			if (StringUtil.isNotEmpty(Setting)) {
				Setting = "," + Setting.replace(' ', ',') + ",";
			} else {
				Setting = "";
			}
			Document doc = new Document();
			doc.add(new Field("CATALOGINNERCODE", CatalogInnerCode, Field.Store.YES, Field.Index.NOT_ANALYZED));
			doc.add(new Field("ID", ID, Field.Store.YES, Field.Index.NOT_ANALYZED));
			doc.add(new Field("SITEID", SiteID, Field.Store.YES, Field.Index.NOT_ANALYZED));
			doc.add(new Field("SETTING", Setting, Field.Store.YES, Field.Index.NOT_ANALYZED));
			doc.add(new Field("KEYWORD", Keyword, Field.Store.YES, Field.Index.NOT_ANALYZED));
			doc.add(new Field("TITLE", Title, Field.Store.YES, Field.Index.NOT_ANALYZED));
			try {
				writer.updateDocument(new Term("ID", ID), doc);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nswt.search.index.Indexer#create()
	 */
	public void create() {
		synchronized (mutex) {
			Mapx map = prepareSettingMap();
			QueryBuilder qb = new QueryBuilder("select ID,SiteID,CatalogInnerCode,Title,Keyword from ZCArticle "
					+ "where Status=? and Keyword is not null", Article.STATUS_PUBLISHED);
			int total = DBUtil.getCount(qb);
			for (int i = 0; i * PageSize < total; i++) {
				DataTable dt = qb.executePagedDataTable(PageSize, i);
				index(dt, map, false);
			}
			qb = new QueryBuilder("select ID,SiteID,CatalogInnerCode,Title,Content from ZCArticle "
					+ "where Status=? and Keyword is null", Article.STATUS_PUBLISHED);
			total = DBUtil.getCount(qb);
			for (int i = 0; i * 50 < total; i++) {
				DataTable dt = qb.executePagedDataTable(50, i);
				index(dt, map, true);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nswt.search.index.Indexer#update()
	 */
	public void update() {
		synchronized (mutex) {
			Mapx map = prepareSettingMap();
			QueryBuilder qb = new QueryBuilder("select ID,SiteID,CatalogInnerCode,Title,Keyword from ZCArticle "
					+ "where Status=? and (AddTime>? or ModifyTime>?) and Keyword is not null",
					Article.STATUS_PUBLISHED);
			qb.add(lastDate);
			qb.add(lastDate);
			int total = DBUtil.getCount(qb);
			for (int i = 0; i * PageSize < total; i++) {
				DataTable dt = qb.executePagedDataTable(PageSize, i);
				index(dt, map, false);
			}
			qb = new QueryBuilder("select ID,SiteID,CatalogInnerCode,Title,Content from ZCArticle "
					+ "where Status=? and (AddTime>? or ModifyTime>?) and Keyword is null", Article.STATUS_PUBLISHED);
			qb.add(lastDate);
			qb.add(lastDate);
			total = DBUtil.getCount(qb);
			for (int i = 0; i * PageSize < total; i++) {
				DataTable dt = qb.executePagedDataTable(PageSize, i);
				index(dt, map, true);
			}
			DataTable dt = new QueryBuilder("select id from BZCArticle where status=" + Article.STATUS_PUBLISHED
					+ " and backuptime>?", lastDate).executeDataTable();
			for (int i = 0; i < dt.getRowCount(); i++) {
				try {
					writer.deleteDocuments(new Term("ID", dt.getString(i, 0)));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void updateCatalog(long catalogID) {
		// 只需将栏目下的所有文章的ModfiyTime置为当前时间，即可在下次定时任务时自动更新索引
		synchronized (mutex) {
			try {
				prepareWriter();
				Mapx map = prepareSettingMap();
				QueryBuilder qb = new QueryBuilder("select ID,SiteID,CatalogInnerCode,Title,Keyword from ZCArticle "
						+ "where Status=? and CatalogInnerCode like ? and Keyword is not null",
						Article.STATUS_PUBLISHED);
				qb.add(CatalogUtil.getInnerCode(catalogID) + "%");
				int total = DBUtil.getCount(qb);
				for (int i = 0; i * PageSize < total; i++) {
					DataTable dt = qb.executePagedDataTable(PageSize, i);
					index(dt, map, false);
				}
				qb = new QueryBuilder("select ID,SiteID,CatalogInnerCode,Title,Content from ZCArticle "
						+ "where Status=? and CatalogInnerCode like ? and Keyword is null", Article.STATUS_PUBLISHED);
				qb.add(CatalogUtil.getInnerCode(catalogID) + "%");
				total = DBUtil.getCount(qb);
				for (int i = 0; i * PageSize < total; i++) {
					DataTable dt = qb.executePagedDataTable(PageSize, i);
					index(dt, map, true);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				releaseWriter();
			}
		}
	}

	public static SearchResult search(SearchParameters sps) {
		try {
			long t = System.currentTimeMillis();
			IndexSearcher searcher = new IndexSearcher(FSDirectory.open(new File(Config.getContextRealPath()
					+ "WEB-INF/data/index/keyword")), true);

			if (sps.getBooleanQuery().clauses().size() == 0) {
				sps.addLeftLikeField("CatalogInnerCode", "");
			}

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
				dt.insertRow(new String[dt.getColCount()]);
				for (int j = 0; j < dt.getColCount(); j++) {
					dt.set(i - start, j, doc.get(dt.getDataColumn(j).getColumnName()));
				}
			}
			searcher.close();
			for (int i = 0; i < dt.getRowCount(); i++) {
				String Keyword = dt.getString(i, "Keyword");
				if (StringUtil.isNotEmpty(Keyword)) {
					Keyword = Keyword.substring(1, Keyword.length() - 1);
				}
				dt.set(i, "Keyword", Keyword);

				String Setting = dt.getString(i, "Setting");
				if (StringUtil.isNotEmpty(Setting)) {
					Setting = Setting.substring(1, Setting.length() - 1);
				}
				dt.set(i, "Setting", Setting);
			}

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

	public static SearchResult getRelaArticles(long siteID, long articleID, String setting, String keywords) {
		SearchParameters sps = new SearchParameters();
		sps.setPageSize(30);
		if (StringUtil.isNotEmpty(keywords)) {
			String[] arr = keywords.split("[\\s\\,，]+");
			BooleanQuery bq = new BooleanQuery();
			for (int i = 0; i < arr.length; i++) {
				if (StringUtil.isNotEmpty(arr[i])) {
					WildcardQuery q = new WildcardQuery(new Term("Keyword".toUpperCase(), "*" + arr[i] + "*"));
					bq.add(q, Occur.SHOULD);
				}
			}
			sps.addQuery(bq);
		}
		if (StringUtil.isNotEmpty(setting)) {
			String[] arr = setting.split("[\\s\\,，]+");
			BooleanQuery bq = new BooleanQuery();
			for (int i = 0; i < arr.length; i++) {
				if (StringUtil.isNotEmpty(arr[i])) {
					WildcardQuery q = new WildcardQuery(new Term("Setting".toUpperCase(), "*," + arr[i] + ",*"));
					bq.add(q, Occur.SHOULD);
				}
			}
			sps.addQuery(bq);
		}
		if (articleID != 0) {
			sps.addNotEqualField("ID", "" + articleID);
		}
		sps.addEqualField("SiteID", "" + siteID);
		SearchResult sr = search(sps);
		return sr;
	}

	public static SearchResult getRelaArticles(long siteID, long catalogID, long articleID, String keywords) {
		SearchParameters sps = new SearchParameters();
		sps.setPageSize(30);
		if (StringUtil.isNotEmpty(keywords)) {
			String[] arr = keywords.split("[\\s\\,，]+");
			BooleanQuery bq = new BooleanQuery();
			for (int i = 0; i < arr.length; i++) {
				if (StringUtil.isNotEmpty(arr[i])) {
					WildcardQuery q = new WildcardQuery(new Term("Keyword".toUpperCase(), "*" + arr[i] + "*"));
					bq.add(q, Occur.SHOULD);
				}
			}
			sps.addQuery(bq);
		}
		if (articleID != 0) {
			sps.addNotEqualField("ID", "" + articleID);
		}
		if (catalogID != 0) {
			sps.addLeftLikeField("CatalogInnerCode", CatalogUtil.getInnerCode(catalogID));
		}
		sps.addEqualField("SiteID", "" + siteID);
		SearchResult sr = search(sps);
		return sr;
	}

	public static void extractKeyword(String catalogInnerCode) {
		if (catalogInnerCode == null) {
			catalogInnerCode = "";
		}
		QueryBuilder qb = new QueryBuilder(
				"select ID,Title,Content from ZCArticle where Status=? and CatalogInnerCode like ? and (Keyword is null or Keyword='')");
		qb.add(Article.STATUS_PUBLISHED);
		qb.add(catalogInnerCode + "%");
		int total = DBUtil.getCount(qb);
		int size = 50;
		for (int i = 0; i * size < total; i++) {
			DataTable dt = qb.executePagedDataTable(size, i);
			QueryBuilder updateQB = new QueryBuilder("update ZCArticle set  Keyword=? where ID=?");
			updateQB.setBatchMode(true);
			for (int j = 0; j < dt.getRowCount(); j++) {
				String Title = dt.getString(j, "Title");
				String Content = dt.getString(j, "Content");
				String Keyword = StringUtil.join(IndexUtil.getKeyword(Title, Content), " ");
				updateQB.add(Keyword);
				updateQB.add(dt.getLong(j, "ID"));
				updateQB.addBatch();
			}
			updateQB.executeNoQuery();
		}
	}

	public static void manual() {
		String file = Config.getContextRealPath() + "WEB-INF/data/index/keyword/time.lock";
		FileUtil.delete(file);
		ArticleRelaIndexer ari = new ArticleRelaIndexer();
		ari.start();
	}

	public static void main(String[] args) {
		long t = System.currentTimeMillis();
		// extractKeyword("");
		manual();
		for (int i = 0; i < 1; i++) {
			SearchResult sr = getRelaArticles(206, 226411, "", "木乃伊");
			System.out.println(sr.Data);
			System.out.println(sr.Total);
		}
		System.out.println(System.currentTimeMillis() - t);
	}
}
