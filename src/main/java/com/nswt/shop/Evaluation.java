package com.nswt.shop;

import java.util.Date;

import com.nswt.framework.Page;
import com.nswt.framework.User;
import com.nswt.framework.cache.CacheManager;
import com.nswt.framework.controls.DataGridAction;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.HtmlUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.Application;
import com.nswt.schema.ZCCommentSchema;
import com.nswt.schema.ZCCommentSet;

/**
 * @Author wyli
 * @Date 2016-01-27
 * @Mail lwy@nswt.com
 */

public class Evaluation extends Page {

	public static Mapx init(Mapx params) {
		params.put("VerifyStatusOptions", HtmlUtil.codeToOptions("Comment.Status", false));
		return params;
	}

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

	public static void dg1DataBind(DataGridAction dga) {
		String VerifyStatus = dga.getParam("VerifyStatus");
		String CatalogID = dga.getParam("CatalogID");

		QueryBuilder qb = new QueryBuilder(
				"select ZCComment.*,(select Name from zccatalog where zccatalog.ID = ZCComment.CatalogID) as CatalogName,"
						+ " (select Name from ZSGoods where ZSGoods.ID = ZCComment.RelaID) as GoodsName"
						+ " from ZCComment where SiteID = ? and CatalogType = '9' ");
		qb.add(Application.getCurrentSiteID());
		if (StringUtil.isNotEmpty(VerifyStatus) && !"All".equals(VerifyStatus)) {
			qb.append(" and VerifyFlag = ?", VerifyStatus);
		}
		if (StringUtil.isNotEmpty(CatalogID)) {
			qb.append(" and CatalogID = ? ", CatalogID);
		}
		dga.setTotal(qb);
		qb.append(" Order by VerifyFlag asc, ID desc");

		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dt.insertColumn("PreLink");
		for (int i = 0; i < dt.getRowCount(); i++) {
			if (dt.getString(i, "CatalogType").equals("9")) {
				dt.set(i, "PreLink", "../Document/Preview.jsp?GoodsID=" + dt.getString(i, "RelaID"));
			} else {
				dt.set(i, "PreLink", "#;");
			}
		}
		dt.decodeColumn("VerifyFlag", new QueryBuilder(
				"select CodeName,CodeValue from ZDCode where CodeType = 'Comment.Status'").executeDataTable().toMapx(1,
				0));
		dga.bindData(dt);
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
				Response.setLogInfo(1, "��˳ɹ�");
			} else {
				Response.setLogInfo(0, "���ʧ��");
			}
		} else if (StringUtil.isNotEmpty(IDs) && StringUtil.isEmpty(ID)) {
			ZCCommentSchema comment = new ZCCommentSchema();
			if (!StringUtil.checkID(IDs)) {// ���SQLע��
				return;
			}
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
				Response.setLogInfo(1, "��˳ɹ�");
			} else {
				Response.setLogInfo(0, "���ʧ��");
			}
		}
	}

	public void del() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setLogInfo(0, "����IDʱ��������");
			return;
		}
		Transaction trans = new Transaction();
		ZCCommentSchema task = new ZCCommentSchema();
		ZCCommentSet set = task.query(new QueryBuilder("where id in (" + ids + ")"));
		trans.add(set, Transaction.DELETE_AND_BACKUP);
		if (trans.commit()) {
			Response.setLogInfo(1, "ɾ�����۳ɹ�");
		} else {
			Response.setLogInfo(0, "ɾ������ʧ��");
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
			Response.setMessage("���Ѿ����۹���лл֧�֣�");
			Response.put("count", task.getSupporterCount());
			return;
		}

		long count = task.getSupporterCount();

		task.setSupporterCount(count + 1);
		task.setSupportAntiIP((StringUtil.isEmpty(supportAntiIP) ? "" : supportAntiIP) + ip);
		trans.add(task, Transaction.UPDATE);
		if (trans.commit()) {
			Response.setStatus(1);
			Response.setMessage("���������ύ�ɹ���");
			Response.put("count", count + 1);
		} else {
			Response.setLogInfo(0, "���ʧ��");
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
			Response.setMessage("���Ѿ����۹���лл֧�֣�");
			Response.put("count", task.getAntiCount());
			return;
		}
		long count = task.getAntiCount();

		task.setAntiCount(count + 1);
		task.setSupportAntiIP((StringUtil.isEmpty(supportAntiIP) ? "" : supportAntiIP) + ip);
		trans.add(task, Transaction.UPDATE);
		if (trans.commit()) {
			Response.setStatus(1);
			Response.setMessage("���������ύ�ɹ���");
			Response.put("count", count + 1);
		} else {
			Response.setLogInfo(0, "���ʧ��");
		}

	}
}
