package com.nswt.cms.datachannel;

import com.nswt.framework.Page;
import com.nswt.framework.cache.CacheManager;
import com.nswt.framework.controls.DataGridAction;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.Errorx;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.Application;
import com.nswt.schema.ZCDeployJobSchema;
import com.nswt.schema.ZCDeployJobSet;

/**
 * @Author 兰军
 * @Date 2008-3-26
 * @Mail lanjun@nswt.com
 */

public class DeployJob extends Page {

	public static Mapx init(Mapx params) {
		return null;
	}

	public static void dg1DataBind(DataGridAction dga) {
		QueryBuilder qb = new QueryBuilder("select * from ZCDeployJob where siteid=? order by addtime desc",
				Application.getCurrentSiteID());
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dt.decodeColumn("status", Deploy.depolyStatus);
		dt.decodeColumn("Method", CacheManager.getMapx("Code", "DeployMethod"));
		dga.bindData(dt);
	}

	public static Mapx initDialog(Mapx params) {
		String sql = "select * from ZCDeployJob a where id=? ";
		DataTable dt = new QueryBuilder(sql, params.getString("ID")).executeDataTable();
		dt.decodeColumn("status", Deploy.depolyStatus);
		dt.decodeColumn("Method", CacheManager.getMapx("Code", "DeployMethod"));
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
		ZCDeployJobSchema ZCDeployJob = new ZCDeployJobSchema();
		ZCDeployJobSet set = ZCDeployJob.query(new QueryBuilder(tsql));

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
		trans.add(new QueryBuilder("delete from zcdeployjob where siteid=?", Application.getCurrentSiteID()));
		if (trans.commit()) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("操作数据库时发生错误!");
		}
	}

	/**
	 * 重新执行任务
	 */
	public void reExecuteJob() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		String tsql = "where id in (" + ids + ")";
		ZCDeployJobSchema ZCDeployJob = new ZCDeployJobSchema();
		ZCDeployJobSet set = ZCDeployJob.query(new QueryBuilder(tsql));
		Deploy helper = new Deploy();

		for (int i = 0; i < set.size(); i++) {
			helper.executeJob(set.get(i));
		}

		if (Errorx.hasError()) {
			Response.setStatus(0);
			Response.setMessage("分发错误。" + Errorx.printString());
		} else {
			Response.setStatus(1);
		}
	}

	/**
	 * 执行失败任务
	 */

	public void executeFailJob() {
		String tsql = "where status=? and siteid=?";
		ZCDeployJobSchema ZCDeployJob = new ZCDeployJobSchema();
		ZCDeployJobSet set = ZCDeployJob.query(new QueryBuilder(tsql, Deploy.FAIL, Application.getCurrentSiteID()));
		Deploy helper = new Deploy();
		for (int i = 0; i < set.size(); i++) {
			helper.executeJob(set.get(i));
		}
		if (Errorx.hasError()) {
			Response.setStatus(0);
			Response.setMessage("分发错误。" + Errorx.printString());
		} else {
			Response.setStatus(1);
		}
	}

}
