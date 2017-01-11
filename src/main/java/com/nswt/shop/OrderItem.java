package com.nswt.shop;

import java.util.Date;

import com.nswt.framework.Page;
import com.nswt.framework.User;
import com.nswt.framework.controls.DataGridAction;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.Application;
import com.nswt.schema.ZDMemberSchema;
import com.nswt.schema.ZSOrderItemSchema;
import com.nswt.schema.ZSOrderItemSet;
import com.nswt.schema.ZSOrderSchema;

/**
 * @Author wyli
 * @Date 2016-01-20
 * @Mail lwy@nswt.com
 */

public class OrderItem extends Page {
	public static void dg1DataBind(DataGridAction dga) {
		QueryBuilder qb = new QueryBuilder("select * from ZSOrderItem where OrderID =? order by GoodsID", dga.getParam("OrderID"));
		dga.bindData(qb);
	}
	
	public static Mapx init(Mapx params){
		return params;
		
	}
	
	/*
	 * У�������ҩƷ����Ƿ���ڣ� ��������򷵻�1�������,�۸��ۿۣ��ۿۼۣ�����
	 * 						����������򷵻�0
	 * OrderItem�����DiscountPrice��Ӧgoods�����MemberPrice
	 * OrderItem�����Price��Ӧgoods�����Price
	 */
	public void checkSN(){
		String sn=$V("SN").trim();
		if(sn.length()==0){
			return;
		}
		DataTable dt=new QueryBuilder("select * from zsgoods where SN=?",sn).executeDataTable();
		if(dt==null||dt.getRowCount()==0){
			Response.setStatus(0);
			return;
		}else{
			Response.setStatus(1);
			Response.put("Name",dt.get(0, "Name").toString());
			Response.put("Price", dt.get(0, "Price").toString());
			Response.put("DiscountPrice", dt.get(0, "MemberPrice").toString());
			Response.put("Score", dt.get(0, "Score").toString());
			Response.put("GoodsID", dt.get(0, "ID").toString());
			Response.put("Factory", dt.get(0, "Factory").toString());
			Response.put("Standard", dt.get(0, "Standard").toString());
			float price=Float.valueOf(dt.get(0, "MemberPrice").toString()).floatValue();
			float memberPrice=Float.valueOf(dt.get(0, "Price").toString()).floatValue();
			int discount=((int)(price/memberPrice)*100)/100;
			Response.put("Discount",discount);
		}
	}
	
	/*
	 * У�������ҩƷ�����Ƿ���ڣ���������򷵻�1�������,�۸��ۿۣ��ۿۼۣ�����
	 * 						����������򷵻�0
	 */
	public void checkName(){
		String name=$V("Name").trim();
		if(name.length()==0){
			return;
		}
		DataTable dt=new QueryBuilder("select * from zsgoods where Name=?",name).executeDataTable();
		if(dt==null||dt.getRowCount()==0){
			Response.setStatus(0);
			return;
		}else{
			Response.setStatus(1);
			Response.put("SN",dt.get(0, "SN").toString());
			Response.put("Price", StringUtil.isEmpty(dt.getString(0, "Price")) ? "0" : dt.getString(0, "Price"));
			Response.put("DiscountPrice", StringUtil.isEmpty(dt.getString(0, "MemberPrice")) ? "0" : dt.getString(0, "MemberPrice"));
			Response.put("Score", StringUtil.isEmpty(dt.getString(0, "Score")) ? "0" : dt.getString(0, "Score"));
			Response.put("GoodsID", dt.getString(0, "ID"));
			Response.put("Factory", StringUtil.isEmpty(dt.getString(0, "Factory")) ? "" : dt.getString(0, "Factory"));
			Response.put("Standard", StringUtil.isEmpty(dt.getString(0, "Standard")) ? "" : dt.getString(0, "Standard"));
			float price=Float.valueOf(StringUtil.isEmpty(dt.getString(0, "MemberPrice")) ? "0" : dt.getString(0, "MemberPrice")).floatValue();
			float memberPrice=Float.valueOf(StringUtil.isEmpty(dt.getString(0, "Price")) ? "0" : dt.getString(0, "Price")).floatValue();
			int discount=((int)(price/memberPrice)*100)/100;
			Response.put("Discount", discount);
		}
	}
	
	/*
	 * Ϊһ�����嶩�����ҩƷ��
	 */
	public void add() {
		Transaction trans=new Transaction();
		//���ҩƷ��
		ZSOrderItemSchema ZSOrderItem = new ZSOrderItemSchema();
		ZSOrderItem.setValue(Request);
		ZSOrderItem.setSiteID(Application.getCurrentSiteID());
		ZSOrderItem.setAddUser(User.getUserName());
		ZSOrderItem.setAddTime(new Date());
		trans.add(ZSOrderItem, Transaction.INSERT);
		//�޸Ķ������
		String orderID=	$V("OrderID");
		if(StringUtil.isEmpty(orderID)){
			Response.setLogInfo(1, "�����Ų���Ϊ�գ�");
			return;
		}
		ZSOrderSchema order=new ZSOrderSchema();
		order.setID(orderID);
		if(order.fill()){
			String amount = Request.getString("Amount");
			order.setAmount(order.getAmount() + Float.parseFloat(amount));
			order.setOrderAmount(order.getOrderAmount() + Float.parseFloat(amount));
			trans.add(order, Transaction.UPDATE);
		}else{
			Response.setLogInfo(1, "�����Ų����ڣ�");
			return;
		}
		//�޸��û�����
		String memberID=Request.getString("MemberID");
		String score=Request.getString("Score");
		//���ܵ���memberID��Ϊ��,�Ҳ�Ϊ0(�ǻ�Ա����ļ�¼,MemberID��¼Ϊ0)
		if(StringUtil.isNotEmpty(memberID)&&!memberID.equalsIgnoreCase("0")){
			ZDMemberSchema member=new ZDMemberSchema();
			member.setUserName(memberID);
			if(member.fill()){
				member.setScore(member.getScore()+Integer.parseInt(score));
				trans.add(member, Transaction.UPDATE);
			}
		}
		if (trans.commit()) {
			Response.setLogInfo(1, "����������ϸ��ɹ���");
		} else {
			Response.setLogInfo(0, "��������!");
		}
	}
	
	/*
	 * ɾ��һ�����嶩���е�ҩƷ��
	 */
	public void del() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setLogInfo(0,"����IDʱ��������!");
			return;
		}
		Transaction trans = new Transaction();
		//���޸Ļ���֮����ɾ������ҩƷ��
		//�޸��û�����
		String memberID=$V("MemberID");
		String sqlScore="select sum(score) from zsorderitem where GoodsID in ('" + ids + "')";
		Object obj=new QueryBuilder(sqlScore).executeOneValue();
		String score=obj==null?"0":obj.toString();
		//���ܵ���memberID��Ϊ��,�Ҳ�Ϊ0(�ǻ�Ա����ļ�¼,MemberID��¼Ϊ0)
		if(StringUtil.isNotEmpty(memberID)&&!memberID.equalsIgnoreCase("0")){
			ZDMemberSchema member=new ZDMemberSchema();
			member.setUserName(memberID);
			if(member.fill()){
				member.setScore(String.valueOf(Integer.parseInt(member.getScore()) - Integer.parseInt(score)));
				trans.add(member, Transaction.UPDATE);
			}
		}
		
		//ɾ��ҩƷ��
		ZSOrderItemSchema ZSOrderItem = new ZSOrderItemSchema();
		ZSOrderItemSet set = ZSOrderItem.query(new QueryBuilder("where GoodsID in ('" + ids + "')"));
		trans.add(set, Transaction.DELETE);
		//�޸Ķ������
		String orderID=$V("OrderID");
		String sqlAmount="select sum(Amount) from zsorderitem where GoodsID in ('" + ids + "')";
		String amount=new QueryBuilder(sqlAmount).executeString();
		ZSOrderSchema order=new ZSOrderSchema();
		order.setID(orderID);
		order.fill();
		order.setAmount(order.getAmount()-Float.parseFloat(amount));
		trans.add(order, Transaction.UPDATE);
		if (trans.commit()) {
			Response.setLogInfo(1, "ɾ���ɹ�");
		} else {
			Response.setLogInfo(0, "ɾ��ʧ��");
		}
	}

	public void dg1Edit() {
		DataTable dt = Request.getDataTable("DT");
		Transaction trans = new Transaction();
		for (int i = 0; i < dt.getRowCount(); i++) {
			ZSOrderItemSchema item = new ZSOrderItemSchema();
			item.setValue(dt.getDataRow(i));
			item.fill();
			item.setValue(dt.getDataRow(i));
			
			item.setModifyTime(new Date());
			item.setModifyUser(User.getUserName());

			trans.add(item, Transaction.UPDATE);
		}
		if (trans.commit()) {
			Response.setStatus(1);
			Response.setMessage("�޸ĳɹ�!");
		} else {
			Response.setStatus(0);
			Response.setMessage("�޸�ʧ��!");
		}
	}
}
