package com.nswt.member;

import java.util.Date;
import com.nswt.cms.pub.PubFun;
import com.nswt.framework.Ajax;
import com.nswt.framework.User;
import com.nswt.framework.controls.DataListAction;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.pub.NoUtil;
import com.nswt.schema.ZDMemberAddrSchema;

public class MemberAddress extends Ajax {
	
	public static void dg1DataList(DataListAction dla) {
		QueryBuilder qb = new QueryBuilder("select * from zdmemberaddr where UserName = ?  Order by AddTime Desc",User.getUserName());
		dla.setTotal(qb);
		DataTable dt = qb.executeDataTable();
		dt.insertColumn("ProvinceName");
		dt.insertColumn("CityName");
		dt.insertColumn("DistrictName");
		dt.insertColumn("IsDefaultName");
		Mapx DistrictMap = PubFun.getDistrictMap();
		for (int i = 0; i < dt.getRowCount(); i++) {
			if(StringUtil.isNotEmpty(dt.getString(i,"Province"))){
				dt.set(i,"ProvinceName",DistrictMap.getString(dt.getString(i,"Province")));
			}
			if(StringUtil.isNotEmpty(dt.getString(i,"City"))){
				dt.set(i,"CityName",DistrictMap.getString(dt.getString(i,"City")));
			}
			if(StringUtil.isNotEmpty(dt.getString(i,"District"))){
				dt.set(i,"DistrictName",DistrictMap.getString(dt.getString(i,"District")));
			}
			if(dt.getString(i,"IsDefault").equals("Y")){
				dt.set(i,"IsDefaultName","<b>Ĭ��</b>");
			}else{
				dt.set(i,"IsDefaultName","<a href='#;' onClick='setDefault(\""+dt.getString(i,"ID")+"\",\""+dt.getString(i,"UserName")+"\")'>��ΪĬ��</a>");
			}
		}
		dla.bindData(dt);
	}
	
	public void setDefault() {
		String ID = $V("ID");
		String UserName = $V("UserName");
		if(StringUtil.isEmpty(UserName)||StringUtil.isEmpty(ID)){
			Response.setLogInfo(0, "��������");
			return;
		}
		Transaction trans  = new Transaction();
		trans.add(new QueryBuilder("update zdmemberaddr set IsDefault = 'N' where UserName = ?",UserName));
		ZDMemberAddrSchema addr = new ZDMemberAddrSchema();
		addr.setID(ID);
		addr.fill();
		addr.setIsDefault("Y");
		trans.add(addr,Transaction.UPDATE);
		if (trans.commit()) {
			Response.setLogInfo(1, "���óɹ�");
		} else {
			Response.setLogInfo(0, "��������");
		}
	}
	
	public void getAddress() {
		String ID = $V("AddrID");
		if(StringUtil.isEmpty(ID)){
			Response.setLogInfo(0, "��������");
			return;
		}
		ZDMemberAddrSchema addr = new ZDMemberAddrSchema();
		addr.setID(ID);
		addr.fill();
		Response.putAll(addr.toMapx());
	}
	
	public void doSave(){
		String UserName = User.getUserName();
		String ID = $V("ID");
		if(StringUtil.isEmpty(UserName)){
			Response.setLogInfo(0, "��������");
			return;
		}
		ZDMemberAddrSchema memberaddr = new ZDMemberAddrSchema();
		boolean isExists = false;
		if(StringUtil.isNotEmpty(ID)){
			memberaddr.setID(ID);
			memberaddr.fill();
			memberaddr.setModifyUser(UserName);
			memberaddr.setModifyTime(new Date());
			isExists = true;
		}else{
			memberaddr.setID(NoUtil.getMaxID("MemberAddr"));
			memberaddr.setAddUser(UserName);
			memberaddr.setAddTime(new Date());
		}
		memberaddr.setValue(Request);
		memberaddr.setUserName(UserName);
		if(isExists){
			if (memberaddr.update()) {
				Response.setLogInfo(1, "�޸ĳɹ�");
			} else {
				Response.setLogInfo(0, "�޸�ʧ��!");
			}
		}else{
			if (memberaddr.insert()) {
				Response.setLogInfo(1, "�����ɹ�");
			} else {
				Response.setLogInfo(0, "����ʧ��!");
			}
		}
	}
	
	public void del(){
		String ID = $V("ID");
		if(StringUtil.isNotEmpty(ID)){
			ZDMemberAddrSchema addr = new ZDMemberAddrSchema();
			addr.setID(ID);
			addr.fill();
			if(addr.deleteAndBackup()){
				Response.setLogInfo(1,"ɾ����ַ�ɹ�");
			}else{
				Response.setLogInfo(0,"ɾ����ַʧ��");
			}
		}
	}
	
}
