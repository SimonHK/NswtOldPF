package com.nswt.platform;

import java.util.List;

import com.nswt.cms.pub.PubFun;
import com.nswt.framework.Page;
import com.nswt.framework.controls.DataGridAction;
import com.nswt.framework.controls.DataListAction;
import com.nswt.framework.controls.TreeAction;
import com.nswt.framework.controls.TreeItem;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;

public class UserSelect extends Page{
	
	public static Mapx init(Mapx params) {
		String SelectedUser = params.getString("SelectedUser");
		if(StringUtil.isNotEmpty(SelectedUser)){
			String[] arr = StringUtil.splitEx(SelectedUser, "|");
			params.put("UserNames",arr[0]);
		}
		return params;
	}
	
	public static void treeDataBind(TreeAction ta) {
		DataTable dt = null;
		String[] branches = new String[0];
		if(StringUtil.isNotEmpty(ta.getParam("Branches"))){
			branches = getSelectedBranchList(ta.getParam("Branches"),2);
		}
		String parentTreeLevel = ta.getParams().getString("ParentLevel");
		String parentInnerCode = ta.getParams().getString("ParentID");
		QueryBuilder qb = null;
		if (ta.isLazyLoad()) {
			qb = new QueryBuilder("select * from ZDBranch where TreeLevel>? and BranchinnerCode like ? order by OrderFlag,BranchInnerCode");
			qb.add(Long.parseLong(parentTreeLevel));
			qb.add(parentInnerCode + "%");
			dt = qb.executeDataTable();
		}else{
			qb = new QueryBuilder("select * from ZDBranch where TreeLevel-1 <=? order by OrderFlag,BranchInnerCode");
			qb.add(ta.getLevel());
			dt = qb.executeDataTable();
			prepareSelectedBranchData(dt,branches,ta.getLevel());
		}
		ta.setIdentifierColumnName("BranchInnerCode");
		ta.setParentIdentifierColumnName("ParentInnerCode");
		ta.setRootText("机构列表");
		ta.bindData(dt);
		addSelectedBranches(ta,branches);
	}
	
	public static void dg1DataBind(DataListAction dla){
		String branchInnerCode = dla.getParam("BranchInnerCode");
		String SearchContent = dla.getParam("SearchContent");
		String sql = "select * from ZDUser where 1=1 ";
		if(StringUtil.isNotEmpty(branchInnerCode)){
			sql += " and BranchInnerCode='"+branchInnerCode+"'";
		}
		if(StringUtil.isNotEmpty(SearchContent)){
			sql += " and (UserName like '%"+SearchContent+"%' or RealName like '%"+SearchContent+"%') ";
		}
		dla.setTotal(new QueryBuilder(sql));
		sql += " order by UserName";
		DataTable dt = new QueryBuilder(sql).executePagedDataTable(dla.getPageSize(),dla.getPageIndex());
		dla.bindData(dt);
	}
	
	public static void selectedUserDataBind(DataGridAction dga){
		String[] Users = null;
		String SelectedUser = dga.getParam("SelectedUser");
		if(StringUtil.isNotEmpty(SelectedUser)){
			if(SelectedUser.indexOf("|")==-1){
				Users = SelectedUser.split(",");
			}else{
				Users = StringUtil.splitEx(SelectedUser, "|")[0].split(",");
			}
			if(Users.length==0||(Users.length==1&&StringUtil.isEmpty(Users[0]))){
				dga.bindData(new DataTable());
				return;
			}
			String str = "";
			for (int i = 0; i < Users.length; i++) {
				str += "'"+Users[i]+"'";
				if(i!=Users.length-1){
					str += ",";
				}
			}
			DataTable dt = new QueryBuilder("select ZDUser.*,(select Name from ZDBranch where BranchInnerCode=ZDUser.BranchInnerCode) as BranchName from ZDUser where UserName in ("+str+")").executeDataTable();
			dga.setTotal(dt.getRowCount());
			dga.bindData(dt);
		}else{
			dga.bindData(new DataTable());
			return;
		}
	}
	
	public void getRealNames(){
		String UserNames = $V("UserNames");
		String[] Names = UserNames.split(",");
		String RealNames = "";
		UserNames = "";
		for (int i = 0; i < Names.length; i++) {
			if(new QueryBuilder("select count(*) from ZDUser where UserName=?",Names[i]).executeInt()==0){
				continue;
			}
			String realName = PubFun.getUserRealName(Names[i]);
			UserNames += Names[i];
			if(StringUtil.isEmpty(realName)){
				realName = "";
			}
			RealNames += realName;
			if(i!=Names.length-1){
				RealNames += ",";
				UserNames += ",";
			}
		}
		Response.setLogInfo(1,UserNames+"|"+RealNames);
	}
	
	public static String[] getSelectedBranchList(String Branches, int level) {
		Mapx map = new Mapx();
		if (StringUtil.isNotEmpty(Branches)) {
			String[] arr = Branches.split("\\,");
			for (int i = 0; i < arr.length; i++) {
				if (StringUtil.isNotEmpty(arr[i])) {
					String innerCode = arr[i];
					if (StringUtil.isNotEmpty(innerCode)) {
						if (innerCode.length() > level * 4) {
							innerCode = innerCode.substring(0, level * 4);
							map.put(innerCode, "1");
						}
					}
				}
			}
		}
		String[] codes = new String[map.size()];
		Object[] ks = map.keyArray();
		for (int i = 0; i < map.size(); i++) {
			codes[i] = ks[i].toString();
		}
		return codes;
	}
	
	public static void prepareSelectedBranchData(DataTable dt, String[] codes, int level) {
		for (int i = 0; i < codes.length; i++) {
			String innerCode = codes[i];
			QueryBuilder qb = new QueryBuilder("select * from ZDBranch where TreeLevel>? and BranchinnerCode like ? order by OrderFlag,BranchInnerCode");
			qb.add(level + 1);
			qb.add(innerCode + "%");
			DataTable dt2 = qb.executeDataTable();
			dt.union(dt2);
		}
	}
	
	public static void addSelectedBranches(TreeAction ta, String[] codes) {
		List list = ta.getItemList();
		for (int i = 0; i < list.size(); i++) {
			TreeItem item = (TreeItem) list.get(i);
			if (item.isRoot()) {
				continue;
			}
			for (int j = 0; j < codes.length; j++) {
				if (item.getData().getString("BranchInnerCode").equals(codes[j].toString())) {
					item.setLazy(false);
					item.setExpanded(true);
					try {
						int level = ta.getLevel();
						ta.setLevel(1000);
						ta.addChild(item);
						ta.setLevel(level);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
}
