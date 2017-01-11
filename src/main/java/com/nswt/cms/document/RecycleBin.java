package com.nswt.cms.document;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.nswt.cms.dataservice.ColumnUtil;
import com.nswt.cms.pub.CatalogUtil;
import com.nswt.cms.site.Catalog;
import com.nswt.framework.Config;
import com.nswt.framework.Page;
import com.nswt.framework.User;
import com.nswt.framework.controls.DataGridAction;
import com.nswt.framework.controls.TreeAction;
import com.nswt.framework.controls.TreeItem;
import com.nswt.framework.data.BlockingTransaction;
import com.nswt.framework.data.DataRow;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.orm.SchemaUtil;
import com.nswt.framework.utility.Filter;
import com.nswt.framework.utility.HtmlUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.Application;
import com.nswt.platform.Priv;
import com.nswt.platform.UserLog;
import com.nswt.platform.pub.NoUtil;
import com.nswt.schema.BZCArticleLogSchema;
import com.nswt.schema.BZCArticleLogSet;
import com.nswt.schema.BZCArticleSchema;
import com.nswt.schema.BZCArticleSet;
import com.nswt.schema.BZCAttachmentRelaSchema;
import com.nswt.schema.BZCAttachmentRelaSet;
import com.nswt.schema.BZCAudioRelaSchema;
import com.nswt.schema.BZCAudioRelaSet;
import com.nswt.schema.BZCCatalogConfigSchema;
import com.nswt.schema.BZCCatalogConfigSet;
import com.nswt.schema.BZCCatalogSchema;
import com.nswt.schema.BZCCatalogSet;
import com.nswt.schema.BZCCommentSchema;
import com.nswt.schema.BZCCommentSet;
import com.nswt.schema.BZCImageRelaSchema;
import com.nswt.schema.BZCImageRelaSet;
import com.nswt.schema.BZCPageBlockSchema;
import com.nswt.schema.BZCPageBlockSet;
import com.nswt.schema.BZCVideoRelaSchema;
import com.nswt.schema.BZCVideoRelaSet;
import com.nswt.schema.BZDColumnRelaSchema;
import com.nswt.schema.BZDColumnRelaSet;
import com.nswt.schema.BZDColumnSchema;
import com.nswt.schema.BZDColumnSet;
import com.nswt.schema.BZDColumnValueSchema;
import com.nswt.schema.BZDColumnValueSet;
import com.nswt.schema.BZWInstanceSchema;
import com.nswt.schema.BZWInstanceSet;
import com.nswt.schema.BZWStepSchema;
import com.nswt.schema.BZWStepSet;
import com.nswt.schema.ZCArticleLogSchema;
import com.nswt.schema.ZCArticleSet;
import com.nswt.schema.ZCCatalogSchema;
import com.nswt.schema.ZCCatalogSet;

/**
 * �ر�ע�⣺��Ҫ���Ƕ��ɾ���������������/��Ŀ����֮�⣬��ص�����Ҳ�п��ܻ��ж���ɾ�����ݼ�¼<br>
 * Ŀǰ��������һ��ȡ��ȫ����Ȼ����SchemaUtil.getZSetFromBSet()������ȡ������ͬ�ļ�¼��BackupNo���ļ�¼
 * 
 * ���� : 2010-4-6 <br>
 * ����: NSWT <br>
 * ���䣺nswt@nswt.com.cn <br>
 */
public class RecycleBin extends Page {
	public static void treeDataBind(TreeAction ta) {
		long siteID = Application.getCurrentSiteID();
		String parentTreeLevel = (String) ta.getParams().get("ParentLevel");
		String parentID = (String) ta.getParams().get("ParentID");
		DataTable dt = null;
		if (ta.isLazyLoad()) {
			QueryBuilder qb = new QueryBuilder(
					"select ID,ParentID,TreeLevel,Name,InnerCode from ZCCatalog Where Type = ? and SiteID = ? and TreeLevel>? and innerCode like ? order by orderflag,innercode ");
			qb.add(Catalog.CATALOG);
			qb.add(siteID);
			qb.add(Integer.parseInt(parentTreeLevel));
			qb.add(CatalogUtil.getInnerCode(parentID) + "%");
			dt = qb.executeDataTable();
		} else {
			QueryBuilder qb = new QueryBuilder(
					"select ID,ParentID,TreeLevel,Name,InnerCode,SingleFlag from ZCCatalog Where Type = ? and SiteID = ? and TreeLevel-1 <=? order by orderflag,innercode ");
			qb.add(Catalog.CATALOG);
			qb.add(siteID);
			qb.add(ta.getLevel());
			dt = qb.executeDataTable();
		}
		ta.setRootText("APPӦ��");
		dt = dt.filter(new Filter() {
			public boolean filter(Object obj) {
				DataRow dr = (DataRow) obj;
				return Priv.getPriv(User.getUserName(), Priv.ARTICLE, dr.getString("InnerCode"), Priv.ARTICLE_BROWSE);
			}
		});
		ta.bindData(dt);
		List items = ta.getItemList();
		for (int i = 1; i < items.size(); i++) {
			TreeItem item = (TreeItem) items.get(i);
			if ("Y".equals(item.getData().getString("SingleFlag"))) {
				item.setIcon("Icons/treeicon11.gif");
			}
		}
	}

	public static void treeCatalogDataBind(TreeAction ta) {
		long siteID = Application.getCurrentSiteID();
		if (!Priv.getPriv("site", siteID + "", Priv.SITE_MANAGE)) {// ����Ҫ��Ӧ�ù���Ȩ��
			ta.setRootText("��ǰ�û�û��Ӧ�ù���Ȩ��");
			return;
		}
		String catalogID = ta.getParam("CatalogID");
		if (CatalogUtil.getSchema(catalogID) == null) {// ��CatalogID�����Ǵ�Cookie�����ģ�дCookieʱ�д���Ŀ�������ڿ�����Ŀ�Ѿ���������
			catalogID = "";// ��������ڣ���Ĭ�ϸ��ڵ�Ϊվ��
		}
		DataTable dt = null;
		QueryBuilder qb = new QueryBuilder("select * from BZCCatalog Where Type = ? and SiteID = ? and TreeLevel-1 <=?");
		qb.add(Catalog.CATALOG);
		qb.add(siteID);
		qb.add(ta.getLevel());
		if (StringUtil.isNotEmpty(catalogID)) {
			String innerCode = CatalogUtil.getInnerCode(catalogID);
			qb.append(" and InnerCode like ?", innerCode + "%");
		}
		qb.append(" and not exists (select 1 from ZCCatalog where ID=BZCCatalog.ID)");
		qb.append(" order by orderflag,innercode,backupNo desc");
		dt = qb.executeDataTable();
		for (int i = dt.getRowCount() - 1; i > 0; i--) {
			if (dt.getInt(i, "ID") == dt.getInt(i - 1, "ID")) {
				dt.deleteRow(i);// ������ظ��ļ�¼
			}
		}
		Mapx map = dt.toMapx("ID", "InnerCode");
		ZCCatalogSet set = new ZCCatalogSet();
		for (int i = 0; i < dt.getRowCount(); i++) {
			String pid = dt.getString(i, "ParentID");
			dt.set(i, "Prop1", "B");// ������B��
			while (!"0".equals(pid) && !map.containsKey(pid)) {
				ZCCatalogSchema catalog = CatalogUtil.getSchema(pid);
				catalog.setProp1("Z");// ������Z��δ��ɾ��
				map.put(pid, catalog.getInnerCode());
				set.add(catalog);
				pid = "" + catalog.getParentID();
			}
		}
		DataTable dt2 = set.toDataTable();
		dt2.insertColumn("BackupNo");
		dt2.insertColumn("BackupOperator");
		dt2.insertColumn("BackupTime");
		dt2.insertColumn("BackupMemo");
		dt.union(dt2);

		if (dt.getRowCount() == 0) {
			if (StringUtil.isEmpty(catalogID)) {
				ta.setRootText("��ǰվ����û�б�ɾ������Ŀ");
			} else {
				ta.setRootText("��Ŀ <font class='red'>" + CatalogUtil.getName(catalogID) + "</font> ��û�б�ɾ������Ŀ");
			}
		} else {
			ta.setRootText("APPӦ��");
		}

		ta.bindData(dt);
		List items = ta.getItemList();
		for (int i = 1; i < items.size(); i++) {
			TreeItem item = (TreeItem) items.get(i);
			if ("Y".equals(item.getData().getString("SingleFlag"))) {
				item.setIcon("Icons/treeicon11.gif");
			}
			String text = item.getText();
			if ("Z".equals(item.getData().getString("Prop1"))) {
				text = "<font class='red'>" + text + "</font>";
			} else {
				text = text + "&nbsp;<font color='#ccc'>(" + item.getData().getString("Total") + "ƪ����)</font>";
			}
			item.setText(text);
		}
	}

	public static void dg1DataBind(DataGridAction dga) {
		long catalogID = dga.getParams().getLong("CatalogID");
		if (catalogID == 0) {
			catalogID = dga.getParams().getLong("Cookie.DocList.LastCatalog");
			dga.getParams().put("CatalogID", catalogID);
		}
		String keyword = (String) dga.getParams().get("Keyword");
		String startDate = (String) dga.getParams().get("StartDate");
		String endDate = (String) dga.getParams().get("EndDate");
		QueryBuilder qb = new QueryBuilder("select distinct id from BZCArticle where CatalogID=?");
		qb.add(catalogID);
		if (StringUtil.isNotEmpty(keyword)) {
			qb.append(" and title like ? ", "%" + keyword.trim() + "%");
		}
		if (StringUtil.isNotEmpty(startDate)) {
			qb.append(" and publishdate >= ? ", startDate);
		}
		if (StringUtil.isNotEmpty(endDate)) {
			qb.append(" and publishdate <= ? ", endDate);
		}
		qb.append(" and not exists (select 1 from ZCArticle where ID=BZCArticle.ID)");
		dga.setTotal(qb);// ����������
		qb.append(dga.getSortString());

		// ��ȡ��ҳ������ID
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());

		StringBuffer sb = new StringBuffer(
				"select ID,Attribute,Title,AddUser,PublishDate,Addtime,Status,WorkFlowID,Type,"
						+ "TopFlag,OrderFlag,TitleStyle,TopDate,BackupTime,BackupOperator,BackupNo from BZCArticle where id in (");
		for (int i = 0; i < dt.getRowCount(); i++) {
			if (i != 0) {
				sb.append(",");
			}
			sb.append(dt.getString(i, "ID"));
		}
		if (dt.getRowCount() == 0) {
			sb.append("0");// û������Ϊ0,��ֹSQL����
		}
		qb = new QueryBuilder(sb.toString() + ") ");
		qb.append(dga.getSortString());
		if (StringUtil.isNotEmpty(dga.getSortString())) {
			qb.append(" ,id desc,BackupNo desc");
		} else {
			qb.append(" order by id desc,BackupNo desc");
		}
		dt = qb.executeDataTable();
		for (int i = dt.getRowCount() - 1; i > 0; i--) {
			if (dt.getInt(i, "ID") == dt.getInt(i - 1, "ID")) {
				dt.deleteRow(i);// ������ظ��ļ�¼
			}
		}
		dt.sort("BackupNo", "desc");
		if (dt != null && dt.getRowCount() > 0) {
			dt.decodeColumn("Status", Article.STATUS_MAP);
			dt.getDataColumn("BackupTime").setDateFormat("yy-MM-dd HH:mm");
		}
		Mapx attributemap = HtmlUtil.codeToMapx("ArticleAttribute");
		if (dt.getRowCount() > 0) {
			for (int i = 0; i < dt.getRowCount(); i++) {
				if (StringUtil.isNotEmpty(dt.getString(i, "Attribute"))) {
					String[] array = dt.getString(i, "Attribute").split(",");
					String attributeName = "";
					for (int j = 0; j < array.length; j++) {
						if (j != array.length - 1) {
							attributeName = attributeName + attributemap.getString(array[j]) + ",";
						} else {
							attributeName = attributeName + attributemap.getString(array[j]);
						}

					}
					dt.set(i, "Title", dt.getString(i, "Title") + "<font class='red'>[" + attributeName + "]</font>");
				}
			}
		}
		dga.bindData(dt);
	}

	public void restoreDocument() {
		restoreDocument($V("IDs"), false);
	}

	/**
	 * �ָ���Ŀʱ�����ָ����µ������batchModeΪtrue
	 */
	public void restoreDocument(String ids, boolean batchMode) {
		if (!StringUtil.checkID(ids) && !batchMode) {
			Response.setStatus(0);
			Response.setMessage("����Ĳ���,ID����Ϊ��!");
			return;
		}
		Transaction trans = new Transaction();
		BZCArticleSet set = new BZCArticleSchema().query(new QueryBuilder("where id in(" + ids
				+ ") or id in (select id from bzcarticle where refersourceid in (" + ids + ") )"));
		ZCArticleSet zset = (ZCArticleSet) SchemaUtil.getZSetFromBSet(set);
		for (int i = 0; i < zset.size(); i++) {
			long status = zset.get(i).getStatus();
			if (status == Article.STATUS_PUBLISHED || status == Article.STATUS_TOPUBLISH) {
				zset.get(i).setStatus(Article.STATUS_TOPUBLISH);// ��Ϊ������
			} else if (status == Article.STATUS_OFFLINE || status == Article.STATUS_ARCHIVE) {
				zset.get(i).setStatus(status);// ����/�鵵�ı���ԭ״̬
			} else {
				zset.get(i).setStatus(Article.STATUS_DRAFT);// ������һ�ɱ�ɳ���
			}
		}
		trans.add(zset, Transaction.INSERT);
		StringBuffer logs = new StringBuffer("�ָ�����:");

		// �ָ����µ�������۵ȣ���δ�ָ��������������
		if (set.size() > 0) {
			// �ָ�������ص��Զ����ֶ�
			BZDColumnValueSchema colValue = new BZDColumnValueSchema();
			BZDColumnValueSet colValueSet = colValue.query(new QueryBuilder("where RelaID in ('" + ids.replaceAll(",", "','")
					+ "') and RelaType=?", ColumnUtil.RELA_TYPE_DOCID));
			trans.add(SchemaUtil.getZSetFromBSet(colValueSet), Transaction.INSERT);

			// �ָ�����ͼƬ����
			BZCImageRelaSchema imageRela = new BZCImageRelaSchema();
			BZCImageRelaSet imageRelaSet = imageRela.query(new QueryBuilder("where RelaID in (" + ids
					+ ") and RelaType=?", Article.RELA_IMAGE));
			trans.add(SchemaUtil.getZSetFromBSet(imageRelaSet), Transaction.INSERT);

			// �ָ�������Ƶ����
			BZCVideoRelaSchema videoRela = new BZCVideoRelaSchema();
			BZCVideoRelaSet videoRelaSet = videoRela.query(new QueryBuilder("where RelaID in (" + ids
					+ ") and RelaType=?", Article.RELA_VIDEO));
			trans.add(SchemaUtil.getZSetFromBSet(videoRelaSet), Transaction.INSERT);

			// �ָ����¸�������
			BZCAttachmentRelaSchema attachmentRela = new BZCAttachmentRelaSchema();
			BZCAttachmentRelaSet attachmentRelaSet = attachmentRela.query(new QueryBuilder("where RelaID in (" + ids
					+ ") and RelaType=?", Article.RELA_ATTACH));
			trans.add(SchemaUtil.getZSetFromBSet(attachmentRelaSet), Transaction.INSERT);

			// �ָ�������Ƶ����
			BZCAudioRelaSchema audioRela = new BZCAudioRelaSchema();
			BZCAudioRelaSet audioRelaSet = audioRela.query(new QueryBuilder("where RelaID in (" + ids + ")"));
			trans.add(SchemaUtil.getZSetFromBSet(audioRelaSet), Transaction.INSERT);

			// �ָ����µ��������
			BZCCommentSchema comment = new BZCCommentSchema();
			BZCCommentSet commentSet = comment.query(new QueryBuilder("where RelaID in (" + ids + ")"));
			trans.add(SchemaUtil.getZSetFromBSet(commentSet), Transaction.INSERT);
		}

		// ������־
		if (!batchMode) {
			for (int i = 0; i < set.size(); i++) {
				ZCArticleLogSchema articleLog = new ZCArticleLogSchema();
				articleLog.setID(NoUtil.getMaxID("ArticleLogID"));
				articleLog.setArticleID(set.get(i).getID());
				articleLog.setAction("RESTORE");
				articleLog.setActionDetail("�ָ��ɹ�");
				articleLog.setAddUser(User.getUserName());
				articleLog.setAddTime(new Date());
				trans.add(articleLog, Transaction.INSERT);
				logs.append(set.get(i).getTitle() + ",");
			}
		}

		if (!batchMode) {// ��Ŀ�ָ�ʱ����Ҫ�ύ��ʹ��������������
			String innerCode = "";
			if (set.size() > 0) {
				innerCode = set.get(0).getCatalogInnerCode();
				String sqlArticleCount = "update zccatalog set " + "total=total+" + set.size()
						+ ",isdirty=1 where innercode in(" + CatalogUtil.getParentCatalogCode(innerCode) + ")";
				trans.add(new QueryBuilder(sqlArticleCount));
			}
			if (trans.commit()) {
				for (int i = 0; i < innerCode.length() / 6; i++) {
					String str = innerCode.substring(0, 6 + i * 6);
					String id = CatalogUtil.getIDByInnerCode(str);
					CatalogUtil.update(id);// ��Ҫ���»���
				}
				UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE, logs + "�ɹ�", Request.getClientIP());
				Response.setStatus(1);
				Response.setMessage("�ָ��ɹ�!");
			} else {
				UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_DELARTICLE, logs + "ʧ��", Request.getClientIP());
				Response.setStatus(0);
				Response.setMessage("�������ݿ�ʱ��������:" + trans.getExceptionMessage());
			}
		}
	}

	public void deleteReally() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("����Ĳ���!");
			return;
		}
		Transaction trans = new Transaction();
		BZCArticleSchema article = new BZCArticleSchema();
		BZCArticleSet set = article.query(new QueryBuilder("where id in(" + ids
				+ ") or id in(select id from bzcarticle where refersourceid in (" + ids + ") )"));

		trans.add(set, Transaction.DELETE);
		StringBuffer logs = new StringBuffer("����ɾ������:");
		if (set.size() > 0) {
			// ɾ��������ص��Զ����ֶ�
			BZDColumnValueSchema colValue = new BZDColumnValueSchema();
			BZDColumnValueSet colValueSet = colValue.query(new QueryBuilder("where RelaID in (" + ids
					+ ") and RelaType=?", ColumnUtil.RELA_TYPE_DOCID));
			trans.add(colValueSet, Transaction.DELETE);

			// ɾ������ͼƬ����
			BZCImageRelaSchema imageRela = new BZCImageRelaSchema();
			BZCImageRelaSet imageRelaSet = imageRela.query(new QueryBuilder("where RelaID in (" + ids
					+ ") and RelaType=?", Article.RELA_IMAGE));
			trans.add(imageRelaSet, Transaction.DELETE);

			// ɾ��������Ƶ����
			BZCVideoRelaSchema videoRela = new BZCVideoRelaSchema();
			BZCVideoRelaSet videoRelaSet = videoRela.query(new QueryBuilder("where RelaID in (" + ids
					+ ") and RelaType=?", Article.RELA_VIDEO));
			trans.add(videoRelaSet, Transaction.DELETE);

			// ɾ�����¸�������
			BZCAttachmentRelaSchema attachmentRela = new BZCAttachmentRelaSchema();
			BZCAttachmentRelaSet attachmentRelaSet = attachmentRela.query(new QueryBuilder("where RelaID in (" + ids
					+ ") and RelaType=?", Article.RELA_ATTACH));
			trans.add(attachmentRelaSet, Transaction.DELETE);

			// ɾ��������Ƶ����
			BZCAudioRelaSchema audioRela = new BZCAudioRelaSchema();
			BZCAudioRelaSet audioRelaSet = audioRela.query(new QueryBuilder("where RelaID in (" + ids + ")"));
			trans.add(audioRelaSet, Transaction.DELETE);

			// ɾ�����µ��������
			BZCCommentSchema comment = new BZCCommentSchema();
			BZCCommentSet commentSet = comment.query(new QueryBuilder("where RelaID in (" + ids + ")"));
			trans.add(commentSet, Transaction.DELETE);

			// ɾ��������־
			BZCArticleLogSchema articleLog = new BZCArticleLogSchema();
			BZCArticleLogSet logset = articleLog.query(new QueryBuilder("where ArticleID in (" + ids + ")"));
			trans.add(logset, Transaction.DELETE);

			// ɾ����������¼
			BZWStepSchema step = new BZWStepSchema();
			BZWStepSet stepset = step.query(new QueryBuilder(
					"where InstanceID in (select id from ZWInstance where DataID in (" + ids + "))"));
			trans.add(stepset, Transaction.DELETE);

			BZWInstanceSchema instance = new BZWInstanceSchema();
			BZWInstanceSet instanceset = instance.query(new QueryBuilder("where DataID in (" + ids + ")"));
			trans.add(instanceset, Transaction.DELETE);
		}

		if (trans.commit()) {
			UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_DELARTICLE, logs + "�ɹ�", Request.getClientIP());
			Response.setStatus(1);
			Response.setMessage("����ɾ���ɹ�!");
		} else {
			UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_DELARTICLE, logs + "ʧ��", Request.getClientIP());
			Response.setStatus(0);
			Response.setMessage("�������ݿ�ʱ��������!");
		}
	}

	public void restoreCatalog() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("����Ĳ���!");
			return;
		}
		String[] arr = ids.split(",");
		ArrayList list = new ArrayList(20);
		for (int i = 0; i < arr.length; i++) {
			if (StringUtil.isNotEmpty(arr[i])) {
				if (CatalogUtil.getSchema(arr[i]) == null) {// ˵����Ŀ���ڣ�����Ҫ�ָ�
					list.add(arr[i]);
				}
			}
		}
		if (list.size() == 0) {
			Response.setError("��Ŀ�Ѿ����ڣ������ٴλָ���");
			return;
		}
		ids = StringUtil.join(list);
		BZCCatalogSet set = new BZCCatalogSchema().query(new QueryBuilder("where id in (" + ids
				+ ") order by backupNo desc"));
		for (int i = set.size() - 1; i > 0; i--) {
			if (set.get(i).getID() == set.get(i - 1).getID()) {
				set.remove(set.get(i));// ������ظ��ļ�¼
			}
		}

		// ��������ڵ�δɾ��������Ҫ���ͬһ�ڵ��Ƿ���ͬ����Ŀ
		for (int i = 0; i < set.size(); i++) {
			BZCCatalogSchema catalog = set.get(i);
			if (CatalogUtil.getSchema(catalog.getParentID()) != null) {// ���ڸ���Ŀ
				if (Catalog.checkNameExists(catalog.getName(), catalog.getParentID())) {
					Response.setError("���ָܻ���Ŀ��" + catalog.getName() + "��,�ϼ���Ŀ������ͬ������Ŀ��");
					return;
				}
				String existsName = Catalog.checkAliasExists(catalog.getAlias(), catalog.getParentID());
				if (StringUtil.isNotEmpty(existsName)) {
					Response.setError("��Ŀ��" + existsName + "����ʹ���˱���" + catalog.getAlias());
					return;
				}
			}
		}
		list.clear();
		// ���ܻ��������������ɾ��������ΪA����Ŀ���ֽ���һ������ΪA����Ŀ����ɾ�������Ŀ�����ݾ���������ͬ��ɾ����¼�������ƶ���A
		for (int i = 0; i < set.size(); i++) {
			BZCCatalogSchema catalog = set.get(i);
			for (int j = i + 1; j < set.size(); j++) {
				if (set.get(j).getParentID() == catalog.getParentID()) {
					if (set.get(j).getName().equals(catalog.getName())) {
						set.get(j).setName(set.get(j).getName() + "_" + j);
					}
					if (set.get(j).getAlias().equals(catalog.getAlias())) {
						set.get(j).setAlias(set.get(j).getAlias() + "_" + j);
					}
				}
			}
			list.add("" + catalog.getID());
		}
		ids = StringUtil.join(list);

		BlockingTransaction tran = new BlockingTransaction();// ����Ҫ�������������п���Ҫ�ָ��������������ǳ���
		tran.add(SchemaUtil.getZSetFromBSet(set), Transaction.INSERT);// ���Ȼָ���Ŀ����

		// �ָ���Ŀ�µ�����
		BZCPageBlockSet blockSet = new BZCPageBlockSchema().query(new QueryBuilder("where CatalogID in (" + ids + ")"));
		tran.add(SchemaUtil.getZSetFromBSet(blockSet), Transaction.INSERT);

		// �ָ���Ŀ��չ����
		BZCCatalogConfigSet configSet = new BZCCatalogConfigSchema().query(new QueryBuilder("where CatalogID in ("
				+ ids + ")"));
		tran.add(SchemaUtil.getZSetFromBSet(configSet), Transaction.INSERT);

		// �ָ���Ŀ��չ���Ժ��Զ����ֶ�
		String types = ColumnUtil.RELA_TYPE_CATALOG_COLUMN + "," + ColumnUtil.RELA_TYPE_CATALOG_EXTEND;
		BZDColumnRelaSet columnRelaSet = new BZDColumnRelaSchema().query(new QueryBuilder(" where RelaType in ("
				+ types + ") and RelaID in(" + ids + ")"));
		tran.add(SchemaUtil.getZSetFromBSet(columnRelaSet), Transaction.INSERT);

		if(columnRelaSet.size() > 0){
			BZDColumnSet columnSet = new BZDColumnSchema().query(new QueryBuilder(" where ID in("
					+ StringUtil.join(columnRelaSet.toDataTable().getColumnValues("ColumnID")) + ")"));
			
			tran.add(SchemaUtil.getZSetFromBSet(columnSet), Transaction.INSERT);
		}

		// �ָ���Ŀ������zdcolumnvalue����,������չ���Ժ��Զ����ֶ�
		BZDColumnValueSet columnValueSet = new BZDColumnValueSchema().query(new QueryBuilder(" where RelaType in ("
				+ types + ") and RelaID in(" + ids + ")"));
		tran.add(SchemaUtil.getZSetFromBSet(columnValueSet), Transaction.INSERT);

		// ��ʼ�����Ŀ�ָ�����
		for (int i = 0; i < set.size(); i++) {
			QueryBuilder qb = new QueryBuilder("select distinct id from BZCArticle where CatalogID=?");
			qb.add(set.get(i).getID());
			DataTable dt = qb.executeDataTable();
			for (int j = 0; j * 100.0 < dt.getRowCount(); j++) {
				list = new ArrayList();
				for (int k = j * 100; k < (j + 1) * 100 && k < dt.getRowCount(); k++) {
					list.add(dt.getString(k, "ID"));
				}
				ids = StringUtil.join(list);
				restoreDocument(ids, true);
			}
		}
		if (tran.commit()) {
			// ��ʼ����Ŀ¼
			for (int i = 0; i < set.size(); i++) {
				String path = Config.getContextRealPath() + CatalogUtil.getAbsolutePath(set.get(i).getID());
				File f = new File(path);
				if (!f.exists()) {
					f.mkdir();
				}
			}
			Response.setMessage("�ָ��ɹ�!");
		} else {
			Response.setError("�ָ�ʧ��:" + tran.getExceptionMessage());
		}
	}
}
