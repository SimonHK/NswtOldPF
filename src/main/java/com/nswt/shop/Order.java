package com.nswt.shop;

import java.util.Date;

import com.nswt.framework.Page;
import com.nswt.framework.User;
import com.nswt.framework.controls.DataGridAction;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.DateUtil;
import com.nswt.framework.utility.HtmlUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.Application;
import com.nswt.platform.pub.NoUtil;
import com.nswt.schema.ZSGoodsSchema;
import com.nswt.schema.ZSOrderSchema;

/**
 * @Author ��ΰ��
 * @Date 2016-1-19
 * @Mail lwy@nswt.com
 */

public class Order extends Page {

	public static void dg1DataBind(DataGridAction dga) {
		if (StringUtil.isNotEmpty(dga.getParam("PageIndex"))) {
			dga.setPageIndex(Integer.parseInt(dga.getParam("PageIndex")));
			dga.getParams().put("PageIndex", "");
		}
		String searchUserName = dga.getParam("searchUserName");
		String searchStatus = dga.getParam("searchStatus");
		QueryBuilder qb = new QueryBuilder("select * from ZSOrder where SiteID = ?");
		qb.add(Application.getCurrentSiteID());
		if (StringUtil.isNotEmpty(searchUserName)) {
			qb.append(" and UserName like ? ", "%" + searchUserName + "%");
		}
		if (StringUtil.isNotEmpty(searchStatus)) {
			qb.append(" and Status = ? ");
			qb.add(searchStatus);
			if(!searchStatus.equals("5")) {
				qb.append(" and IsValid = 'Y' ");
			}
		} else {
			qb.append(" and IsValid = 'Y' ");
		}
		qb.append(" and status not in ('8','9') order by id desc");
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dt.decodeColumn("IsValid", HtmlUtil.codeToMapx("Order.IsValid"));
		dt.decodeColumn("HasInvoice", HtmlUtil.codeToMapx("Order.HasInvoice"));
		dt.decodeColumn("Status", HtmlUtil.codeToMapx("Order.Status"));
		DataTable dc = new QueryBuilder("select Name,Code from zddistrict Order by Code").executeDataTable();
		Mapx map = dc.toMapx("Code", "Name");
		dt.decodeColumn("Province", map);
		dt.decodeColumn("City", map);
		dt.decodeColumn("District", map);
		dga.bindData(dt);
	}

	public static Mapx initStatusSelect(Mapx params) {
		params.put("StatusSelect", HtmlUtil.codeToOptions("Order.Status", true));
		return params;
	}

	public static Mapx initStore(Mapx params) {
		DataTable storeDT = new QueryBuilder("select name,storecode from zsstore where Prop1 = 'Y'").executeDataTable();
		params.put("StoreCode", HtmlUtil.dataTableToOptions(storeDT));
		return params;
	}

	public static Mapx initAddDialog(Mapx params) {
		Mapx map = new Mapx();
		map.put("IsValid", HtmlUtil.codeToRadios("IsValid", "Order.IsValid", "Y"));
		map.put("HasInvoice", HtmlUtil.codeToRadios("HasInvoice", "Order.HasInvoice", "Y"));
		map.put("Status", HtmlUtil.codeToOptions("Order.Status"));
		map.put("SendTimeSlice", HtmlUtil.codeToOptions("Order.SendTimeSlice"));
		DataTable sendTypeDT = new QueryBuilder("select name,id from zssend order by id").executeDataTable();
		map.put("SendType", HtmlUtil.dataTableToOptions(sendTypeDT));
		DataTable paymentTypeDT = new QueryBuilder("select name,id from zspayment order by id").executeDataTable();
		map.put("PaymentType", HtmlUtil.dataTableToOptions(paymentTypeDT));
		return map;
	}

	public static Mapx initEditDialog(Mapx params) {
		String ID = params.getString("OrderID");
		if (StringUtil.isNotEmpty(ID)) {
			ZSOrderSchema order = new ZSOrderSchema();
			order.setID(ID);
			if (order.fill()) {
				params.putAll(order.toMapx());
				params.put("IsValid", HtmlUtil.codeToRadios("IsValid", "Order.IsValid", order.getIsValid()));
				params
						.put("HasInvoice", HtmlUtil.codeToRadios("HasInvoice", "Order.HasInvoice", order
								.getHasInvoice()));
				params.put("Status", HtmlUtil.codeToOptions("Order.Status", order.getStatus()));
				params.put("SendTimeSlice", HtmlUtil.codeToOptions("Order.SendTimeSlice", order.getSendTimeSlice()));
				DataTable sendTypeDT = new QueryBuilder("select name,id from zssend order by id").executeDataTable();
				DataTable paymentTypeDT = new QueryBuilder("select name,id from zspayment order by id")
						.executeDataTable();
				params.put("SendType", HtmlUtil.dataTableToOptions(sendTypeDT, order.getSendType()));
				params.put("PaymentType", HtmlUtil.dataTableToOptions(paymentTypeDT, order.getPaymentType()));
				params.put("OrderID", ID);
			}
			return params;
		} else {
			return params;
		}
	}

	public void add() {
		ZSOrderSchema ZSorder = new ZSOrderSchema();
		String UserName = $V("UserName");
		String date = DateUtil.getCurrentDate("yyyyMMdd");
		String OrderID = NoUtil.getMaxNo("OrderID", date, 4);
		ZSorder.setID(OrderID);
		ZSorder.setValue(Request);
		ZSorder.setAddUser(User.getUserName());
		ZSorder.setAddTime(new Date());
		ZSorder.setUserName(UserName);
		ZSorder.setSiteID(Application.getCurrentSiteID());
		ZSorder.setAmount(0);
		if (ZSorder.insert()) {
			Response.setLogInfo(1, "���������ɹ���");
		} else {
			Response.setLogInfo(0, "��������!");
		}
	}

	public void orderEdit() {
		ZSOrderSchema order = new ZSOrderSchema();
		order.setValue(Request);
		order.fill();
		if(order.getStatus().equals("7")) {
			if(!Request.getString("Status").equals("8") && !Request.getString("Status").equals("9")) {
				Response.setLogInfo(0, "����ɽ��׵Ķ���ֻ�ܽ���Ͷ�߻��˻�!");
				return;
			}
		}
		order.setValue(Request);
		
		boolean flag = false;
		StringBuffer sb = new StringBuffer();
		Transaction trans = new Transaction();
		//������ʱȷ�϶�������Ʒ�Ƿ����㹻
		if(order.getStatus().equals("1")) {
			DataTable orderItems = new QueryBuilder("select GoodsID, Name, Count from ZSOrderItem where OrderID = ?", order.getID()).executeDataTable();
			for(int j = 0; j < orderItems.getRowCount(); j++) {
				ZSGoodsSchema goods = new ZSGoodsSchema();
				goods.setID(orderItems.getString(j, "GoodsID"));
				if(goods.fill()) {
					if(goods.getStore() < orderItems.getInt(j, "Count")) {
						sb.append("��Ʒ<span style='color:red'>" + goods.getName() + "</span>�Ŀ�治����<br/>");//��治��
						flag = true;
					}
				} else {
					sb.append("��Ʒ<span style='color:red'>" + orderItems.getString(j, "Name") + "</span>�����ڣ�<br/>");//��Ʒ������
					flag = true;
				}
			}
		}
		//����ɽ��׵���Ʒ��������Ʒ����м�
		if(order.getStatus().equals("7")) {
			DataTable orderItems = new QueryBuilder("select GoodsID, Name, Count from ZSOrderItem where OrderID = ?", order.getID()).executeDataTable();
			for(int j = 0; j < orderItems.getRowCount(); j++) {
				ZSGoodsSchema goods = new ZSGoodsSchema();
				goods.setID(orderItems.getString(j, "GoodsID"));
				if(goods.fill()) {
					goods.setStore(goods.getStore() - orderItems.getLong(j, "Count"));
					trans.add(goods, Transaction.UPDATE);
				} else {
					sb.append("��Ʒ<span style='color:red'>" + orderItems.getString(j, "Name") + "</span>�����ڣ�<br/>");
					flag = true;
				}
			}
		}
		if(flag) {
			Response.setLogInfo(0, sb.toString());
			return;
		}
		
		order.setSiteID(Application.getCurrentSiteID());
		order.setModifyUser(User.getUserName());
		order.setModifyTime(new Date());
		trans.add(order, Transaction.UPDATE);
		if ("N".equals(order.getIsValid())) {
			String logMessage = User.getRealName() + "��" + DateUtil.getCurrentDateTime("yyyy-MM-dd") + "���ö���"
					+ order.getID() + "Ϊ��Ч״̬";
			// PubFun.addLog("OrdCancel", User.getUserName(), logMessage,
			// Request.getClientIP(), trans);
		}

		if (trans.commit()) {
			Response.setLogInfo(1, "�޸ĳɹ�");
		} else {
			Response.setLogInfo(0, "�޸�" + order.getUserName() + "ʧ��!");
		}
	}

	public void dg1Edit() {
		DataTable dt = Request.getDataTable("DT");
		Transaction trans = new Transaction();
		for (int i = 0; i < dt.getRowCount(); i++) {
			ZSOrderSchema order = new ZSOrderSchema();
			order.setValue(dt.getDataRow(i));
			order.fill();
			if(order.getStatus().equals("7")) {
				if(!dt.getString(i, "Status").equals("8") && !dt.getString(i, "Status").equals("9")) {
					Response.setLogInfo(0, "����ɽ��׵Ķ���ֻ�ܽ���Ͷ�߻��˻�!");
					return;
				}
			}
			
			order.setValue(dt.getDataRow(i));
			//������ʱȷ�϶�������Ʒ�Ƿ����㹻,����Ƕ�����������ڵ�һ����������Ķ����ͷ��أ����ж����������κδ���
			if(order.getStatus().equals("1")) {
				DataTable orderItems = new QueryBuilder("select GoodsID, Count from ZSOrderItem where OrderID = ?", order.getID()).executeDataTable();
				boolean flag = false;
				StringBuffer sb = new StringBuffer();
				for(int j = 0; j < orderItems.getRowCount(); j++) {
					ZSGoodsSchema goods = new ZSGoodsSchema();
					goods.setID(orderItems.getString(j, "GoodsID"));
					if(goods.fill()) {
						if(goods.getStore() < orderItems.getInt(j, "Count")) {
							sb.append("��Ʒ" + goods.getName() + "�Ŀ�治����<br/>");//��治��
							flag = true;
						}
					} else {
						sb.append("IDΪ" + orderItems.getString(j, "GoodsID") + "����Ʒ�����ڣ�<br/>");//��Ʒ������
						flag = true;
					}
				}
				if(flag) {
					Response.setLogInfo(0, sb.toString());
					return;
				}
			}
			//����ɽ��׵���Ʒ��������Ʒ����м�
			if(order.getStatus().equals("7")) {
				DataTable orderItems = new QueryBuilder("select GoodsID, Count from ZSOrderItem where OrderID = ?", order.getID()).executeDataTable();
				for(int j = 0; j < orderItems.getRowCount(); j++) {
					ZSGoodsSchema goods = new ZSGoodsSchema();
					goods.setID(orderItems.getString(j, "GoodsID"));
					if(goods.fill()) {
						goods.setStore(goods.getStore() - orderItems.getLong(j, "Count"));
						trans.add(goods, Transaction.UPDATE);
					} else {
						Response.setLogInfo(0, "IDΪ" + orderItems.getString(j, "GoodsID") + "����Ʒ�ѱ�ɾ����");
						return;
					}
				}
			}
			
			order.setModifyTime(new Date());
			order.setModifyUser(User.getUserName());
			trans.add(order, Transaction.UPDATE);
			
			if ("N".equals(order.getIsValid())) {
				String logMessage = User.getRealName() + "��" + DateUtil.getCurrentDateTime("yyyy-MM-dd") + "���ö���"
						+ order.getID() + "Ϊ��Ч״̬";
				// PubFun.addLog("OrdCancel", User.getUserName(), logMessage,
				// Request.getClientIP(), trans);
			}
		}
		if (trans.commit()) {
			Response.setLogInfo(1, "�޸ĳɹ�");
		} else {
			Response.setLogInfo(0, "�޸�ʧ��");
		}
	}

	public void createSending() {
		String ids = $V("IDs");
		// String storeCode = $V("StoreCode");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("����IDʱ��������!");
			return;
		}
		ids = ids.replaceAll(",", "','");
		Transaction trans = new Transaction();
		trans.add(new QueryBuilder("update zsorder set status=10 where ID in ('" + ids + "')"));
		if (trans.commit()) {
			Response.setLogInfo(1, "�������͵��ɹ�!");
		} else {
			Response.setLogInfo(0, "�������͵�ʧ��!");
		}
	}
}
