package com.nswt.cms.dataservice;

import java.util.Date;
import com.nswt.cms.pub.PubFun;
import com.nswt.framework.Page;
import com.nswt.framework.User;
import com.nswt.framework.controls.DataGridAction;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.pub.NoUtil;
import com.nswt.schema.ZDMemberAddrSchema;

public class MemberAddr extends Page {
	
	public static Mapx initDialog(Mapx params) {
		String ID = params.getString("ID");
		if(StringUtil.isNotEmpty(ID)){
			ZDMemberAddrSchema addr = new ZDMemberAddrSchema();
			addr.setID(ID);
			addr.fill();
			if(addr.getIsDefault().equals("Y")){
				params.put("checkDefault","checked");
			}
			Mapx DistrictMap = PubFun.getDistrictMap();
			params.put("ProvinceName",DistrictMap.getString(addr.getProvince()));
			params.put("CityName",DistrictMap.getString(addr.getCity()));
			params.put("DistrictName",DistrictMap.getString(addr.getDistrict()));
			params.putAll(addr.toMapx());
		}
		return params;
	}
		
	public static void dg1DataBind(DataGridAction dga) {
		String UserName = dga.getParam("UserName");
		QueryBuilder qb = new QueryBuilder("select * from zdmemberaddr where UserName = ?",UserName);
		qb.append(" order by addTime desc");
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dt.insertColumn("CityName");
		dt.insertColumn("IsDefaultName");
		Mapx DistrictMap = PubFun.getDistrictMap();
		for (int i = 0; i < dt.getRowCount(); i++) {
			if(StringUtil.isNotEmpty(dt.getString(i,"City"))){
				dt.set(i,"CityName",DistrictMap.getString(dt.getString(i,"City")));
			}
			if(dt.getString(i,"IsDefault").equals("Y")){
				dt.set(i,"IsDefaultName","<b>默认</b>");
			}else{
				dt.set(i,"IsDefaultName","<a href='#;' onClick='setDefault(\""+dt.getString(i,"ID")+"\",\""+dt.getString(i,"UserName")+"\")'>设为默认</a>");
			}
		}
		dga.bindData(dt);
	}
	
	public void add() {
		String ID = $V("ID");
		String UserName = $V("UserName");
		ZDMemberAddrSchema memberaddr = new ZDMemberAddrSchema();
		boolean isExists = false;
		if(StringUtil.isNotEmpty(ID)){
			memberaddr.setID(ID);
			memberaddr.fill();
			memberaddr.setModifyUser(User.getUserName());
			memberaddr.setModifyTime(new Date());
			isExists = true;
		}else{
			memberaddr.setID(NoUtil.getMaxID("MemberAddr"));
			memberaddr.setAddUser(User.getUserName());
			memberaddr.setAddTime(new Date());
		}
		memberaddr.setUserName(UserName);
		memberaddr.setValue(Request);
		if(memberaddr.getIsDefault().equals("Y")){
			new QueryBuilder("update zdmemberaddr set IsDefault = 'N' where UserName = ?",UserName).executeNoQuery();
		}
		if(isExists){
			if (memberaddr.update()) {
				Response.setLogInfo(1, "修改成功");
			} else {
				Response.setLogInfo(0, "修改失败!");
			}
		}else{
			if (memberaddr.insert()) {
				Response.setLogInfo(1, "新增成功");
			} else {
				Response.setLogInfo(0, "新增失败!");
			}
		}
	}
	
	public void setDefault() {
		String ID = $V("ID");
		String UserName = $V("UserName");
		Transaction trans  = new Transaction();
		trans.add(new QueryBuilder("update zdmemberaddr set IsDefault = 'N' where UserName = ?",UserName));
		ZDMemberAddrSchema addr = new ZDMemberAddrSchema();
		addr.setID(ID);
		addr.fill();
		addr.setIsDefault("Y");
		trans.add(addr,Transaction.UPDATE);
		if (trans.commit()) {
			Response.setLogInfo(1, "设置成功");
		} else {
			Response.setLogInfo(0, "发生错误");
		}
	}
	
	public void del() {
		String IDs = $V("IDs");
		String[] ids = IDs.split(",");
		Transaction trans = new Transaction();
		ZDMemberAddrSchema memberaddr = new ZDMemberAddrSchema();
		for (int i = 0; i < ids.length; i++) {
			trans.add(memberaddr.query(new QueryBuilder(" where ID = ?", Long.parseLong(ids[i]))), Transaction.DELETE_AND_BACKUP);
		}
		if (trans.commit()) {
			Response.setLogInfo(1, "删除成功");
		} else {
			Response.setLogInfo(0, "删除失败");
		}
	}
	
}
