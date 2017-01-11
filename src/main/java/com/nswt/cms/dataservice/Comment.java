package com.nswt.cms.dataservice;

import java.util.Date;
import java.util.List;

import com.nswt.cms.pub.CatalogUtil;
import com.nswt.framework.Page;
import com.nswt.framework.User;
import com.nswt.framework.cache.CacheManager;
import com.nswt.framework.controls.DataListAction;
import com.nswt.framework.controls.TreeAction;
import com.nswt.framework.controls.TreeItem;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.Application;
import com.nswt.schema.ZCCommentSchema;
import com.nswt.schema.ZCCommentSet;

/**
 * @Author 陈海强
 * @Date 2008-01-17
 * @Mail chq@nswt.com
 */

public class Comment extends Page {

	public static Mapx initDetail(Mapx params) {
		String id = params.getString("ID");
		if (StringUtil.isNotEmpty(id)) {
			ZCCommentSchema comment = new ZCCommentSchema();
			comment.setID(id);
			if (comment.fill()) {
				params.putAll(comment.toMapx());
				params.put("VerifyFlag", CacheManager.getMapx("Code", "Comment.Status").get(params.get("VerifyFlag")));
				String addTimeStr = params.getString("AddTime");
				params.put("AddTime", addTimeStr.substring(0, addTimeStr.lastIndexOf(".")));
			}
		}
		return params;
	}

	public static void treeDataBind(TreeAction ta) {
		long SiteID = Application.getCurrentSiteID();
		DataTable dt = null;
		Mapx params = ta.getParams();
		long CatalogType = params.getLong("Type");
		int parentLevel = params.getInt("ParentLevel");
		String parentID = (String) params.get("ParentID");
		String rootText = "";
		if (CatalogType == 1) {
			rootText = "APP应用";
		} else if (CatalogType == 4) {
			rootText = "图片库";
		} else if (CatalogType == 5) {
			rootText = "视频库";
		} else if (CatalogType == 6) {
			rootText = "音频库";
		} else if (CatalogType == 7) {
			rootText = "附件库";
		}
		if (ta.isLazyLoad()) {
			QueryBuilder qb = new QueryBuilder(
					"select ID,ParentID,TreeLevel,Name,SingleFlag,prop1 from ZCCatalog Where Type=? and SiteID=? and TreeLevel>? and innerCode like ? order by orderflag");
			qb.add(CatalogType);
			qb.add(SiteID);
			qb.add(parentLevel);
			qb.add(CatalogUtil.getInnerCode(parentID) + "%");
			dt = qb.executeDataTable();
		} else {
			QueryBuilder qb = new QueryBuilder(
					"select ID,ParentID,TreeLevel,Name,SingleFlag,prop1 from ZCCatalog Where Type=? and SiteID=? and TreeLevel-1<=? order by orderflag");
			qb.add(CatalogType);
			qb.add(SiteID);
			qb.add(ta.getLevel());
			dt = qb.executeDataTable();
		}
		ta.setRootText(rootText);
		dt.setWebMode(false);
		ta.bindData(dt);
		if (CatalogType == 1) {
			List items = ta.getItemList();
			for (int i = 1; i < items.size(); i++) {
				TreeItem item = (TreeItem) items.get(i);
				if ("Y".equals(item.getData().getString("SingleFlag"))) {
					item.setIcon("Icons/treeicon11.gif");
				}
			}
		}
	}

	public static void dg1DataBind(DataListAction dla) {
		String CatalogID = dla.getParam("CatalogID");
		String CatalogType = dla.getParam("CatalogType");
		String VerifyStatus = dla.getParam("VerifyStatus");
		QueryBuilder qb = new QueryBuilder("select ZCComment.*,(select Name from zccatalog "
				+ "where zccatalog.ID=ZCComment.CatalogID) as CatalogName,");
		if (StringUtil.isEmpty(CatalogType)) {
			CatalogType = "1";
		}
		if (StringUtil.isEmpty(VerifyStatus)) {
			VerifyStatus = "X";
		}
		if (CatalogType.equals("1")) {
			qb.append("(select Title from ZCArticle where ZCArticle.ID=ZCComment.RelaID) as Name");
		} else if (CatalogType.equals("4")) {
			qb.append("(select Name from ZCImage where ZCImage.ID=ZCComment.RelaID) as Name");
		} else if (CatalogType.equals("5")) {
			qb.append("(select Name from ZCVideo where ZCVideo.ID=ZCComment.RelaID) as Name");
		} else if (CatalogType.equals("6")) {
			qb.append("(select Name from ZCAudio where ZCAudio.ID=ZCComment.RelaID) as Name");
		} else if (CatalogType.equals("7")) {
			qb.append("(select Name from ZCAttachment where ZCAttachment.ID=ZCComment.RelaID) as Name");
		}
		qb.append(" from ZCComment where SiteID=?", Application.getCurrentSiteID());
		qb.append(" and CatalogType=?", Long.parseLong(CatalogType));
		if (StringUtil.isNotEmpty(CatalogID)) {
			qb.append(" and CatalogID=?", Long.parseLong(CatalogID));
		}
		if (!VerifyStatus.equals("All")) {
			qb.append(" and VerifyFlag=?", VerifyStatus);
		}
		qb.append(" order by VerifyFlag asc, ID desc");
		dla.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dla.getPageSize(), dla.getPageIndex());
		dt.insertColumn("PreLink");
		dt.insertColumn("FlagColor");
		for (int i = 0; i < dt.getRowCount(); i++) {
			if (dt.getString(i, "CatalogType").equals("1")) {
				dt.set(i, "PreLink", "../Document/Preview.jsp?ArticleID=" + dt.getString(i, "RelaID"));
			} else {
				dt.set(i, "PreLink", "#;");
			}
			if (dt.getString(i, "VerifyFlag").equals("Y")) {
				dt.set(i, "FlagColor", "#00ff00");
			} else if (dt.getString(i, "VerifyFlag").equals("X")) {
				dt.set(i, "FlagColor", "#ff7700");
			} else {
				dt.set(i, "FlagColor", "#ff0000");
			}
		}
		dt.decodeColumn("VerifyFlag", CacheManager.getMapx("Code", "Comment.Status"));
		if (dt.getRowCount() == 0) {
			dt.insertRow((Object[]) null);
			dt.set(0, "ID", "0");
		}
		dla.bindData(dt);
	}

	public void Verify() {
		String ID = $V("ID");
		String Type = $V("Type");
		String IDs = $V("IDs");
		if (StringUtil.isNotEmpty(ID) && StringUtil.isEmpty(IDs)) {
			ZCCommentSchema comment = new ZCCommentSchema();
			comment.setID(ID);
			comment.fill();
			if (Type.equals("Pass")) {
				comment.setVerifyFlag("Y");
			} else if (Type.equals("NoPass")) {
				comment.setVerifyFlag("N");
			}
			comment.setVerifyUser(User.getUserName());
			comment.setVerifyTime(new Date());
			if (comment.update()) {
				Response.setLogInfo(1, "审核成功");
			} else {
				Response.setLogInfo(0, "审核失败");
			}
		} else if (StringUtil.isNotEmpty(IDs) && StringUtil.isEmpty(ID)) {
			ZCCommentSchema comment = new ZCCommentSchema();
			ZCCommentSet set = comment.query(new QueryBuilder("where ID in (" + IDs + ")"));
			Transaction trans = new Transaction();
			for (int i = 0; i < set.size(); i++) {
				comment = set.get(i);
				if (Type.equals("Pass")) {
					comment.setVerifyFlag("Y");
				} else if (Type.equals("NoPass")) {
					comment.setVerifyFlag("N");
				}
				comment.setVerifyUser(User.getUserName());
				comment.setVerifyTime(new Date());
				trans.add(comment, Transaction.UPDATE);
			}
			if (trans.commit()) {
				Response.setLogInfo(1, "审核成功");
			} else {
				Response.setLogInfo(0, "审核失败");
			}
		}
	}

	public void del() {
		String ids = $V("IDs");
		if (ids.indexOf("\"") >= 0 || ids.indexOf("\'") >= 0) {
			Response.setLogInfo(0, "传入ID时发生错误");
			return;
		}
		Transaction trans = new Transaction();
		ZCCommentSchema task = new ZCCommentSchema();
		ZCCommentSet set = task.query(new QueryBuilder("where id in (" + ids + ")"));
		trans.add(set, Transaction.DELETE_AND_BACKUP);
		if (trans.commit()) {
			Response.setLogInfo(1, "删除评论成功");
		} else {
			Response.setLogInfo(0, "删除评论失败");
		}
	}

	public void addSupporterCount() {
		String ip = Request.getClientIP();
		String id = $V("ID");

		Transaction trans = new Transaction();
		ZCCommentSchema task = new ZCCommentSchema();

		task.setID(id);
		task.fill();
		String supportAntiIP = task.getSupportAntiIP();
		if (StringUtil.isNotEmpty(supportAntiIP) && supportAntiIP.indexOf(ip) >= 0) {
			Response.setMessage("您已经评论过，谢谢支持！");
			Response.put("count", task.getSupporterCount());
			return;
		}

		long count = task.getSupporterCount();

		task.setSupporterCount(count + 1);
		task.setSupportAntiIP((StringUtil.isEmpty(supportAntiIP) ? "" : supportAntiIP) + ip);
		trans.add(task, Transaction.UPDATE);
		if (trans.commit()) {
			Response.setStatus(1);
			Response.setMessage("您的评论提交成功！");
			Response.put("count", count + 1);
		} else {
			Response.setLogInfo(0, "审核失败");
		}
	}

	public void addAntiCount() {
		String ip = Request.getClientIP();
		String id = $V("ID");

		Transaction trans = new Transaction();
		ZCCommentSchema task = new ZCCommentSchema();

		task.setID(id);
		task.fill();
		String supportAntiIP = task.getSupportAntiIP();
		if (StringUtil.isNotEmpty(supportAntiIP) && supportAntiIP.indexOf(ip) >= 0) {
			Response.setMessage("您已经评论过，谢谢支持！");
			Response.put("count", task.getAntiCount());
			return;
		}
		long count = task.getAntiCount();

		task.setAntiCount(count + 1);
		task.setSupportAntiIP((StringUtil.isEmpty(supportAntiIP) ? "" : supportAntiIP) + ip);
		trans.add(task, Transaction.UPDATE);
		if (trans.commit()) {
			Response.setStatus(1);
			Response.setMessage("您的评论提交成功！");
			Response.put("count", count + 1);
		} else {
			Response.setLogInfo(0, "审核失败");
		}

	}
}
