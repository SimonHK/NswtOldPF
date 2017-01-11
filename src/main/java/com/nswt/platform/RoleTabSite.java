package com.nswt.platform;

import java.util.Map;

import com.nswt.framework.Page;
import com.nswt.framework.controls.DataGridAction;
import com.nswt.framework.data.DataRow;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.StringUtil;
import com.nswt.schema.ZDPrivilegeSchema;

/**
 * 
 * @author huanglei
 * @mail huanglei@nswt.com
 * @date 2007-11-8
 */

public class RoleTabSite extends Page {

	public static void dg1DataBind(DataGridAction dga) {
		String RoleCode = dga.getParam("RoleCode");
		if (StringUtil.isEmpty(RoleCode)) {
			RoleCode = dga.getParam("Role.LastRoleCode");
			if (StringUtil.isEmpty(RoleCode)) {
				dga.bindData(new DataTable());
				return;
			}
		}
		String PrivType = dga.getParam("PrivType");
		StringBuffer sb = new StringBuffer();
		sb.append(",'" + RoleCode + "' as RoleCode");
		Object[] ks = Priv.SITE_MAP.keyArray();
		for (int i = 0; i < Priv.SITE_MAP.size(); i++) {
			sb.append(",'' as " + ks[i]);
		}

		String sql = "select ID,Name,0 as TreeLevel ,'" + Priv.SITE + "' as PrivType" + sb.toString()
				+ " from ZCSite a order by orderflag ,id";
		dga.setTotal(new QueryBuilder("select * from ZCSite a"));
		DataTable siteDT = new QueryBuilder(sql).executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		Map PrivTypeMap = RolePriv.getPrivTypeMap(RoleCode, PrivType);
		DataRow dr = null;
		for (int i = 0; i < siteDT.getRowCount(); i++) {
			dr = siteDT.getDataRow(i);
			String ID = dr.getString("ID");
			Map mapRow = (Map) PrivTypeMap.get(ID);
			if (mapRow != null) {
				for (int j = 0; j < dr.getColumnCount(); j++) {
					if (dr.getDataColumn(j).getColumnName().toLowerCase().indexOf("_") > 0) {
						if("admin".equalsIgnoreCase(RoleCode)){
							dr.set(j, "√");
						}else {
							dr.set(j, "0".equals(mapRow.get(dr.getDataColumn(j).getColumnName().toLowerCase())) ? "" : "√");
						}
					}
				}
			}
		}
		dga.bindData(siteDT);
	}

	public void dg1Edit() {
		DataTable dt = (DataTable) Request.get("DT");
		String RoleCode = $V("RoleCode");
		// String PrivType = $V("PrivType");
		Transaction trans = new Transaction();
		ZDPrivilegeSchema p = new ZDPrivilegeSchema();
		for (int i = 0; i < dt.getRowCount(); i++) {
			for (int j = 0; j < dt.getColCount(); j++) {
				if (dt.getDataColumn(j).getColumnName().toLowerCase().indexOf("_") > 0) {
					trans.add(p.query(new QueryBuilder("where OwnerType='" + RolePriv.OWNERTYPE_ROLE
							+ "' and Owner =? and PrivType = '" + dt.getString(i, "PrivType") + "' and ID = '"
							+ dt.getString(i, "ID") + "' and Code = '" + dt.getDataColumn(j).getColumnName().toLowerCase() + "' ",
							RoleCode)), Transaction.DELETE_AND_BACKUP);
				}
			}
		}

		for (int i = 0; i < dt.getRowCount(); i++) {
			DataRow dr = dt.getDataRow(i);
			for (int j = 0; j < dr.getColumnCount(); j++) {
				if (dr.getDataColumn(j).getColumnName().toLowerCase().indexOf("_") > 0) {
					ZDPrivilegeSchema priv = new ZDPrivilegeSchema();
					priv.setOwnerType(RolePriv.OWNERTYPE_ROLE);
					priv.setOwner(dr.getString("RoleCode"));
					priv.setID(dr.getString("ID"));
					priv.setPrivType(dr.getString("PrivType"));
					priv.setCode(dr.getDataColumn(j).getColumnName().toLowerCase());
					priv.setValue("".equals(dr.getString(j)) ? "0" : "1");
					trans.add(priv, Transaction.INSERT);
				}
			}
		}
		if (trans.commit()) {
			RolePriv.updateAllPriv(RoleCode);
			Response.setLogInfo(1, "修改成功!");
		} else {
			Response.setLogInfo(0, "修改失败!");
		}
	}

}
