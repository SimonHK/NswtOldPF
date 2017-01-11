package com.nswt.platform;

import java.util.List;

import com.nswt.cms.pub.PubFun;
import com.nswt.framework.Page;
import com.nswt.framework.User;
import com.nswt.framework.controls.TreeAction;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;

/**
 * @Author »ÆÀ×
 * @Date 2007-8-6
 * @Mail huanglei@nswt.com
 */
public class Role extends Page {

	public final static String EVERYONE = "everyone";
	
	public static void treeDataBind(TreeAction ta) {
		QueryBuilder qb=new QueryBuilder("select RoleCode,'' as ParentID,'1' as TreeLevel,RoleName from ZDRole ");
		List roleCodeList = PubFun.getRoleCodesByUserName(User.getUserName());
		if (roleCodeList != null && roleCodeList.size() != 0) {
			if(!roleCodeList.contains("admin")){
				qb.append(" Where BranchInnerCode like ?", User.getBranchInnerCode()+"%");
			}
		}
		DataTable dt = qb.executeDataTable();
		ta.setIdentifierColumnName("RoleCode");
		ta.setBranchIcon("Icons/icon025a1.gif");
		ta.setLeafIcon("Icons/icon025a1.gif");
		ta.bindData(dt);
	}

}
