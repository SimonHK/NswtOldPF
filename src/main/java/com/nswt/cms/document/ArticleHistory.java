package com.nswt.cms.document;

import java.util.Date;

import com.nswt.cms.dataservice.ColumnUtil;
import com.nswt.framework.Page;
import com.nswt.framework.User;
import com.nswt.framework.controls.DataGridAction;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.orm.SchemaUtil;
import com.nswt.framework.utility.DateUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.schema.BZCArticleSchema;
import com.nswt.schema.BZCArticleSet;
import com.nswt.schema.ZCArticleSchema;

/**
 * ������ʷ�������
 * 
 * @Author ����
 * @Date 2007-8-10
 * @Mail lanjun@nswt.com
 */
public class ArticleHistory extends Page {

	private static final String PAGE_SPLIT = "--abcdefghijklmnopqrstuvwxyz--";

	// �༭��ҳ���ʼ��
	public static Mapx basicInit(Mapx params) {
		Mapx map = new Mapx();
		String catalogID = (String) params.get("CatalogID");
		String articleID = (String) params.get("ArticleID");
		String backupNo = (String) params.get("BackupNo");
		if (articleID != null && !"".equals(articleID) && !"null".equals(articleID)) {
			BZCArticleSchema article = new BZCArticleSchema();
			article.setID(Integer.parseInt(articleID));
			article.setBackupNo(backupNo);
			BZCArticleSet set = article.query();
			if (set.size() < 1) {
				return null;
			}
			article = set.get(0);
			catalogID = article.getCatalogID() + "";
			map.putAll(article.toMapx());

			String content = article.getContent();
			String[] pages = content.split(PAGE_SPLIT);

			map.put("Pages", new Integer(pages.length));
			map.put("Content", StringUtil.htmlEncode(pages[0]));
			// �Զ����ֶ�
			map.put("CustomColumn", ColumnUtil.getHtml(ColumnUtil.RELA_TYPE_CATALOG_COLUMN, catalogID,
					ColumnUtil.RELA_TYPE_DOCID, article.getID() + ""));
		}

		DataTable dtCatalog = new QueryBuilder("select siteid,name from zccatalog where id=?", catalogID)
				.executeDataTable();
		String catalogName = dtCatalog.get(0, "name").toString();
		map.put("SiteID", dtCatalog.getString(0, "siteid"));
		map.put("CatalogName", catalogName);
		return map;
	}

	// ���ҳ��
	public static Mapx init(Mapx params) {
		Mapx map = new Mapx();
		String catalogID = (String) params.get("CatalogID");
		String articleID = (String) params.get("ArticleID");
		String backupNo = (String) params.get("BackupNo");
		if (articleID != null && !"".equals(articleID) && !"null".equals(articleID)) {
			BZCArticleSchema article = new BZCArticleSchema();
			article.setID(Integer.parseInt(articleID));
			article.setBackupNo(backupNo);
			BZCArticleSet set = article.query();
			if (set.size() < 1) {
				return null;
			}
			article = set.get(0);
			catalogID = article.getCatalogID() + "";
			map.putAll(article.toMapx());

			Date publishDate = article.getPublishDate();
			if (publishDate != null) {
				map.put("PublishDate", DateUtil.toString(publishDate, DateUtil.Format_Date));
				map.put("PublishTime", DateUtil.toString(publishDate, DateUtil.Format_Time));
			}

			Date DownlineDate = article.getDownlineDate();
			if (DownlineDate != null) {
				map.put("DownlineDate", DateUtil.toString(DownlineDate, DateUtil.Format_Date));
				map.put("DownlineTime", DateUtil.toString(DownlineDate, DateUtil.Format_Time));
			}

			String content = article.getContent();
			String[] pages = content.split(PAGE_SPLIT);
			StringBuffer pageStr = new StringBuffer();
			for (int i = 0; i < pages.length; i++) {
				pageStr.append("'" + StringUtil.htmlEncode(pages[i]) + "'");
				if (i != pages.length - 1) {
					pageStr.append(",");
				}
			}
			map.put("Pages", new Integer(pages.length));
			map.put("ContentPages", pageStr.toString());
			map.put("Content", pages[0]);

			map.put("Method", "UPDATE");
		}

		DataTable dtCatalog = new QueryBuilder("select siteid,name from zccatalog where id=?", Long.parseLong(catalogID))
				.executeDataTable();
		String catalogName = dtCatalog.get(0, "Name").toString();
		map.put("SiteID", dtCatalog.getString(0, "siteid"));
		map.put("CatalogName", catalogName);

		return map;
	}

	public static void historyDataBind(DataGridAction dga) {
		String articleID = (String) dga.getParams().get("ArticleID");
		QueryBuilder qb = new QueryBuilder("select ID,BackupNo,Title,author,publishDate,Addtime, type,topflag,"
				+ "(case modifytime when null then addtime else modifytime end) as modifytime,backuptime,"
				+ "modifyUser from BZCArticle where id=? order by ID desc", Long.parseLong(articleID));
		dga.bindData(qb);
	}

	public void del() {
		String ids = $V("BackupNo");
		if (!StringUtil.checkID(ids)) {
			return;
		}
		Transaction trans = new Transaction();
		BZCArticleSchema site = new BZCArticleSchema();
		BZCArticleSet set = site.query(new QueryBuilder("where BackupNo in ('" + ids.replaceAll(",", "','") + "')"));
		trans.add(set, Transaction.DELETE);

		// ��δ�����Զ����ֶ�
		// trans.add("delete from zdcolumnvalue where classcode='Sys_CMS' and articleid in ("
		// + ids + ")");

		if (trans.commit()) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("�������ݿ�ʱ��������!");
		}
	}

	/**
	 * ���浱ǰ�ļ�Ϊ��ʷ�汾
	 * 
	 */
	public void addVersion() {
		String articleID = $V("ArticleID");
		long id = Long.parseLong(articleID);
		ZCArticleSchema article = new ZCArticleSchema();
		article.setID(id);

		if (!article.fill()) {
			Response.setStatus(0);
			Response.setMessage("û���ҵ���Ӧ������");
			return;
		}

		Transaction trans = new Transaction();
		trans.add(article, Transaction.BACKUP);

		if (trans.commit()) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("�������ݿ�ʱ��������!");
		}
	}

	public void restore() {
		String articleID = $V("ArticleID");
		long id = Long.parseLong(articleID);
		String backupNo = $V("BackupNo");
		BZCArticleSchema article = new BZCArticleSchema();
		article.setID(id);
		article.setBackupNo(backupNo);

		if (!article.fill()) {
			Response.setStatus(0);
			Response.setMessage("û���ҵ���Ӧ����ʷ�汾");
			return;
		}

		ZCArticleSchema currentArticle = new ZCArticleSchema();

		currentArticle.setID(id);
		if (!currentArticle.fill()) {
			Response.setStatus(0);
			Response.setMessage("û���ҵ���Ӧ���ĵ�");
			return;
		}

		ZCArticleSchema currentArticleBak = (ZCArticleSchema) currentArticle.clone();

		currentArticle.setModifyTime(new Date());
		currentArticle.setModifyUser(User.getUserName());
		currentArticle.setStatus(currentArticleBak.getStatus());

		SchemaUtil.copyFieldValue(article, currentArticle);

		Transaction trans = new Transaction();
		trans.add(currentArticle, Transaction.UPDATE);
		trans.add(currentArticleBak, Transaction.BACKUP);

		// ��δ�����Զ����ֶ�

		if (trans.commit()) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("�������ݿ�ʱ��������!");
		}
	}
}
