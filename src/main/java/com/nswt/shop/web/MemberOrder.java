package com.nswt.shop.web;

import java.util.Date;

import com.nswt.framework.User;
import com.nswt.framework.controls.DataGridAction;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.utility.HtmlUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.schema.ZSOrderSchema;
import com.nswt.framework.Ajax;

/**
 * @Author ��ΰ��
 * @Date 2016-1-26
 * @Mail lwy@nswt.com
 */

public class MemberOrder extends Ajax {

	public static void dg1DataBind(DataGridAction dga) {
		String orderID = dga.getParam("OrderID");
		String startDate = dga.getParam("StartDate");
		String endDate = dga.getParam("EndDate");
		String siteID = dga.getParam("SiteID");

		QueryBuilder qb = new QueryBuilder(
				"select * from ZSOrder where SiteID = ? and UserName = ?",siteID,User.getUserName());
		
		if (StringUtil.isNotEmpty(orderID)) {
			qb.append(" and ID = ?", orderID);
		}
		if (StringUtil.isNotEmpty(startDate)) {
			qb.append(" and AddTime > ?", startDate);
		}
		if (StringUtil.isNotEmpty(endDate)) {
			qb.append(" and AddTime < ?", endDate);
		}

		qb.append(" order by id desc");
		dga.setTotal(qb);

		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga
				.getPageIndex());
		dt.insertColumn("CancelCol");
		for (int i = 0; i < dt.getRowCount(); i++) {
			if (dt.getInt(i, "Status") == 0) {
				dt.set(i, "CancelCol", "<a href='javascript:void(0)' onClick=\"cancel(" + dt.getString(i, "ID") + ")\">ȡ������</a>");
			} else if(dt.getInt(i, "Status") == 5) {
				dt.set(i, "CancelCol", "��ȡ��");
			} else {
				dt.set(i, "CancelCol", "����Ч");
			}
		}
		dt.decodeColumn("Status", HtmlUtil.codeToMapx("Order.Status"));
		Mapx paymentMapx = new QueryBuilder("select ID, Name from ZSPayment")
				.executeDataTable().toMapx(0, 1);
		dt.decodeColumn("PaymentType", paymentMapx);

		dga.bindData(dt);
	}

	/**
	 * �û�����ȡ��δ����Ķ���
	 */
	public void cancel() {
		ZSOrderSchema order = new ZSOrderSchema();
		String orderID = $V("OrderID");
		order.setID(orderID);
		if (order.fill()) {
			if (StringUtil.isNotEmpty(order.getStatus())) {
				order.setIsValid("N");
				order.setStatus("5");
				order.setModifyTime(new Date());
				order.setModifyUser(User.getUserName());
				if (order.update()) {
					Response.setLogInfo(1, "ȡ�������ɹ���");
				} else {
					Response.setLogInfo(0, "�Բ��𣬷�������!������ϵ�ͷ���");
				}
			} else {
				Response.setLogInfo(0, "�Բ��𣬷�������!������ϵ�ͷ���");
			}
		} else {
			Response.setLogInfo(0, "�Բ��𣬷�������!������ϵ�ͷ���");
		}
	}
}
