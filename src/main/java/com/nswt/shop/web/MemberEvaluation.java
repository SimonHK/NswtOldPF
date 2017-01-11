package com.nswt.shop.web;

import java.util.Date;

import com.nswt.cms.pub.SiteUtil;
import com.nswt.framework.Config;
import com.nswt.framework.Ajax;
import com.nswt.framework.User;
import com.nswt.framework.controls.DataGridAction;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.utility.DateUtil;
import com.nswt.platform.Application;
import com.nswt.platform.pub.NoUtil;
import com.nswt.schema.ZCCommentSchema;
import com.nswt.schema.ZSGoodsSchema;

/**
 * @Author ��ΰ��
 * @Date 2016-1-26
 * @Mail lwy@nswt.com
 */

public class MemberEvaluation extends Ajax {

	public static void dg1DataBind(DataGridAction dga) {
		if (dga.getTotal() == 0) {
			String sql2 = "select count(*) from ZSOrderItem where SiteID = ? and UserName = ?";
			QueryBuilder qb = new QueryBuilder(sql2);
			qb.add(Application.getCurrentSiteID());
			qb.add(User.getValue("UserName")); 
			dga.setTotal(qb.executeInt());
		}

		String sql1 = "select a.*, b.Image0 from ZSOrderItem a, ZSGoods b, zsorder c where a.SiteID = ? and a.UserName = ? and a.orderid = c.id and c.status='7' and a.GoodsID = b.ID order by AddTime";
		QueryBuilder qb = new QueryBuilder(sql1);
		qb.add(dga.getParam("SiteID"));
		qb.add(User.getValue("UserName"));
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		
		dt.insertColumn("Image");
		dt.insertColumn("Evaluate");
		for (int i = dt.getRowCount() - 1; i >= 0 ; i--) {
			dt.set(i, "Image", (Config.getContextPath() + Config.getValue("UploadDir") + "/"
					+ SiteUtil.getAlias(dga.getParam("SiteID")) + "/" + dt.getString(i, "Image0")).replaceAll(
					"//", "/"));
			int flag = new QueryBuilder("select count(*) from ZCComment where RelaID = ? and AddUser = ?", dt.get(i,
					"GoodsID"), dt.get(i, "UserName")).executeInt();

			if (DateUtil.addMonth(dt.getDate(i, "AddTime"), 6).before(new Date())) {
				dt.set(i, "Evaluate", flag > 0 ? "������" : "δ����");
			} else {
				dt.set(i, "Evaluate", flag > 0 ? "������" : "<button type='button' onClick='evaluate("
						+ dt.getString(i, "GoodsID") + ");'>����</button>");
			}
		}
		dga.bindData(dt);
	}

	/**
	 * ���ָ����Ʒ������
	 */
	public void evaluate() {
		String goodsID = $V("GoodsID");
		ZSGoodsSchema goods = new ZSGoodsSchema();
		goods.setID(goodsID);
		if (goods.fill()) {
			ZCCommentSchema comment = new ZCCommentSchema();
			comment.setID(NoUtil.getMaxID("ZCCommentID"));
			comment.setValue(Request);

			comment.setRelaID(goodsID);
			comment.setCatalogID(goods.getCatalogID());
			comment.setSiteID(goods.getSiteID());
			comment.setCatalogType("9");
			comment.setVerifyFlag("X");

			comment.setAddUser(User.getValue("UserName").toString()); // ǰ̨Ӧ����User.getValue("Name");��ȡ��Ա����
			comment.setAddTime(new Date());
			comment.setAddUserIP($V("MemberIP"));
			if (comment.insert()) {
				Response.setStatus(1);
				Response.setMessage("������۳ɹ�!");
			} else {
				Response.setStatus(0);
				Response.setMessage("�Բ���!��������!������ϵ�ͷ�!");
			}
		} else {
			Response.setStatus(0);
			Response.setMessage("�Բ���!��������!������ϵ�ͷ�!");
		}
	}
}
