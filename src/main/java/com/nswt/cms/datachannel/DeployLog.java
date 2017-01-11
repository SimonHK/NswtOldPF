package com.nswt.cms.datachannel;

import com.nswt.framework.Page;
import com.nswt.framework.controls.DataGridAction;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.Application;
import com.nswt.schema.ZCDeployLogSchema;
import com.nswt.schema.ZCDeployLogSet;

/**
 * @Author 兰军
 * @Date 2008-3-26
 * @Mail lanjun@nswt.com
 */

public class DeployLog extends Page {
	public static void dg1DataBind(DataGridAction dga) {
		String sql1 = "select a.id,a.jobid,a.message,a.begintime,a.endtime,b.method,b.host,b.source,b.target,"
				+ "(select codename from zdcode where codetype='DeployMethod' and parentCode='DeployMethod' and codevalue=b.method) as methodDesc "
				+ "from ZCDeployLog a,ZCDeployJob b where  a.JobID=b.ID and b.SiteID=? order by a.begintime desc";
		QueryBuilder qb = new QueryBuilder(sql1, Application.getCurrentSiteID());
		dga.bindData(qb);
	}

	public static Mapx initDialog(Mapx params) {
		String sql = "select a.id,a.jobid,a.message,a.begintime,a.endtime,b.method,b.host,b.source,b.target,"
				+ "(select codename from zdcode where codetype='DeployMethod' and parentCode='DeployMethod' and codevalue=b.method) as methodDesc"
				+ " from ZCDeployLog a,ZCDeployJob b where  a.JobID=b.ID and a.ID=?";
		DataTable dt = new QueryBuilder(sql, params.getString("ID")).executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			params.putAll(dt.get(0).toCaseIgnoreMapx());
		}
		return params;
	}

	public void del() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		String tsql = "where id in (" + ids + ")";
		ZCDeployLogSchema ZCDeployLog = new ZCDeployLogSchema();
		ZCDeployLogSet set = ZCDeployLog.query(new QueryBuilder(tsql));

		Transaction trans = new Transaction();
		trans.add(set, Transaction.DELETE);
		if (trans.commit()) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("操作数据库时发生错误!");
		}
	}

	public void delAll() {
		Transaction trans = new Transaction();
		trans.add(new QueryBuilder("delete from ZCDeployLog where siteid=?",Application.getCurrentSiteID()));
		if (trans.commit()) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("操作数据库时发生错误!");
		}
	}
}
