package com.nswt.datachannel;

import java.util.Date;

import com.nswt.cms.document.Article;
import com.nswt.cms.pub.CatalogUtil;
import com.nswt.framework.Page;
import com.nswt.framework.User;
import com.nswt.framework.controls.DataGridAction;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.DataTableUtil;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.messages.LongTimeTask;
import com.nswt.framework.utility.HtmlUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.Application;
import com.nswt.platform.pub.NoUtil;
import com.nswt.schema.ZCInnerGatherSchema;
import com.nswt.schema.ZCInnerGatherSet;

public class FromCatalog extends Page {
	public static Mapx init(Mapx params) {
		String id = params.getString("ID");
		String syncCatalogInsert = "N";
		String syncCatalogModify = "N";
		String syncArticleModify = "N";
		String afterInsertStatus = "" + Article.STATUS_DRAFT;
		String afterModifyStatus = "" + Article.STATUS_EDITING;
		if (StringUtil.isNotEmpty(id)) {
			ZCInnerGatherSchema ig = new ZCInnerGatherSchema();
			ig.setID(id);
			ig.fill();
			params.putAll(ig.toMapx());
			syncCatalogInsert = ig.getSyncCatalogInsert();
			syncCatalogModify = ig.getSyncCatalogModify();
			syncArticleModify = ig.getSyncArticleModify();
			afterInsertStatus = "" + ig.getAfterInsertStatus();
			afterModifyStatus = "" + ig.getAfterModifyStatus();
		} else {
			params.put("SiteID", Application.getCurrentSiteID());
		}
		params.put("SyncCatalogInsert", HtmlUtil.codeToRadios("SyncCatalogInsert", "YesOrNo", syncCatalogInsert));
		params.put("SyncCatalogModify", HtmlUtil.codeToRadios("SyncCatalogModify", "YesOrNo", syncCatalogModify));
		params.put("SyncArticleModify", HtmlUtil.codeToRadios("SyncArticleModify", "YesOrNo", syncArticleModify));
		params.put("AfterInsertStatus", HtmlUtil.mapxToOptions(Article.STATUS_MAP, afterInsertStatus));
		params.put("AfterModifyStatus", HtmlUtil.mapxToOptions(Article.STATUS_MAP, afterModifyStatus));
		return params;
	}

	public static void dg1DataBind(DataGridAction dga) {
		String sql = "select * from ZCInnerGather where SiteID=?";
		DataTable dt = new QueryBuilder(sql, Application.getCurrentSiteID()).executeDataTable();
		dt.insertColumn("CatalogName");
		dt.insertColumn("CatalogID");
		for (int i = 0; i < dt.getRowCount(); i++) {
			dt.set(i, "CatalogName", CatalogUtil.getNameByInnerCode(dt.getString(i, "CatalogInnerCode")));
			dt.set(i, "CatalogID", CatalogUtil.getIDByInnerCode(dt.getString(i, "CatalogInnerCode")));
		}
		Mapx map = new Mapx();
		map.put("Y", "����");
		map.put("N", "ͣ��");
		dt.decodeColumn("Status", map);
		dga.bindData(dt);
	}

	public static void dialogDataBind(DataGridAction dga) {
		String id = dga.getParam("ID");
		DataTable dt = (DataTable) dga.getParams().get("Data");
		if (dt != null) {
			dga.bindData(dt);
		} else {
			if (StringUtil.isEmpty(id)) {
				dt = new DataTable();
				dt.insertColumn("ServerAddr");
				dt.insertColumn("SiteID");
				dt.insertColumn("SiteName");
				dt.insertColumn("CatalogID");
				dt.insertColumn("CatalogName");
				dt.insertColumn("Password");
				dga.bindData(dt);
			} else {
				ZCInnerGatherSchema ig = new ZCInnerGatherSchema();
				ig.setID(id);
				ig.fill();
				String data = ig.getTargetCatalog();
				dt = DataTableUtil.txtToDataTable(data, (String[]) null, "\t", "\n");
				dga.bindData(dt);
			}
		}
	}

	public void add() {
		String id = $V("ID");
		DataTable dt = (DataTable) Request.get("Data");
		ZCInnerGatherSchema ig = new ZCInnerGatherSchema();
		Transaction tran = new Transaction();
		if (StringUtil.isNotEmpty(id)) {
			ig.setID(id);
			ig.fill();
			ig.setModifyTime(new Date());
			ig.setModifyUser(User.getUserName());
			tran.add(ig, Transaction.UPDATE);
		} else {
			ig.setID(NoUtil.getMaxID("InnerGatherID"));
			ig.setAddTime(new Date());
			ig.setAddUser(User.getUserName());
			tran.add(ig, Transaction.INSERT);
		}
		ig.setValue(Request);
		ig.setSiteID(Application.getCurrentSiteID());
		for (int i = dt.getRowCount() - 1; i >= 0; i--) {
			if (dt.getString(i, "ServerAddr").equalsIgnoreCase("localhost")
					&& dt.getLong(i, "SiteID") == ig.getSiteID()
					&& dt.getString(i, "CatalogID").equals(CatalogUtil.getIDByInnerCode(ig.getCatalogInnerCode()))) {
				dt.deleteRow(i);
			}
		}
		ig.setTargetCatalog(dt.toString());
		ig.setCatalogInnerCode(CatalogUtil.getInnerCode($V("CatalogID")));
		if (tran.commit()) {
			Response.setMessage("����ɹ�!");
		} else {
			Response.setMessage("��������ʱ��������!");
		}
	}

	public void del() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			return;
		}
		ZCInnerGatherSet set = new ZCInnerGatherSchema().query(new QueryBuilder("where id in (" + ids + ")"));
		if (set.deleteAndBackup()) {
			Response.setMessage("ɾ���ɹ�!");
		} else {
			Response.setMessage("ɾ������ʱ��������!");
		}
	}

	public void execute() {
		final long id = Long.parseLong($V("ID"));
		final boolean restartFlag = "Y".equals($V("RestartFlag"));
		LongTimeTask ltt = LongTimeTask.getInstanceByType("InnerGather" + id);
		if (ltt != null) {
			if (!ltt.isAlive()) {
				LongTimeTask.removeInstanceById(ltt.getTaskID());
			} else {
				Response.setError("����������������У�������ֹ��");
				return;
			}
		}
		ltt = new LongTimeTask() {
			public void execute() {
				ZCInnerGatherSchema gather = new ZCInnerGatherSchema();
				gather.setID(id);
				gather.fill();
				DataTable dt = DataTableUtil.txtToDataTable(gather.getTargetCatalog(), (String[]) null, "\t", "\n");
				if (restartFlag) {
					dt.deleteColumn("LastTime");
					gather.setTargetCatalog(dt.toString());
				}
				InnerSyncService.executeGather(gather, this);
				setPercent(100);
			}
		};
		ltt.setType("InnerGather" + id);
		ltt.setUser(User.getCurrent());
		ltt.start();
		$S("TaskID", "" + ltt.getTaskID());
	}
}
