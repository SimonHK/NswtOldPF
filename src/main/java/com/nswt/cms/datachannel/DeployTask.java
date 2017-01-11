package com.nswt.cms.datachannel;

import java.util.Date;

import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.schedule.GeneralTask;
import com.nswt.framework.utility.DateUtil;
import com.nswt.framework.utility.LogUtil;
import com.nswt.schema.ZCDeployConfigSchema;
import com.nswt.schema.ZCDeployConfigSet;
import com.nswt.schema.ZCDeployJobSchema;
import com.nswt.schema.ZCDeployJobSet;

public class DeployTask extends GeneralTask {

	public void execute() {
		// LogUtil.getLogger().info("扫描分发任务");
		ZCDeployConfigSet configSet = new ZCDeployConfigSchema().query();
		if (configSet.size()>0) {
			for (int i = 0; i < configSet.size(); i++) {
				//将多个任务分批次执行
				int count = new QueryBuilder("select count(*) from ZCDeployJob where RetryCount<5 and status<>? and configID=?",
						Deploy.SUCCESS, configSet.get(i).getID()).executeInt();
				int batchCount = count/1000;
				for (int j = 0; j < batchCount+1; j++) {
					ZCDeployJobSchema job = new ZCDeployJobSchema();
					ZCDeployJobSet jobs = job.query(new QueryBuilder("where RetryCount<5 and status<>? and configID=?",
							Deploy.SUCCESS, configSet.get(i).getID()),1000,j);
					if (jobs != null && jobs.size() > 0) {
						LogUtil.getLogger().info("执行分发任务 任务数：" + jobs.size());
						Deploy d = new Deploy();
						d.executeBatchJob(configSet.get(i),jobs);
					}
				}
			}
		}
		
		// 删除5小时前的成功任务和失败任务
		Date yesterday = DateUtil.addHour(new Date(), -5);
		Date beforeYesterday = DateUtil.addHour(new Date(), -5);
		new QueryBuilder("delete from ZCDeployJob where RetryCount>=5  and status=" + Deploy.FAIL
					+ " and addtime<=?", beforeYesterday).executeNoQuery();
		new QueryBuilder("delete from ZCDeployJob where  status=" + Deploy.SUCCESS + " and addtime<=?",
					yesterday).executeNoQuery();
		new QueryBuilder(
					"delete from ZCDeployLog where not exists (select id from ZCDeployJob where  ZCDeployLog.jobid= ZCDeployJob.id)")
					.executeNoQuery();

		// LogUtil.getLogger().info("扫描分发任务结束");
	}

	public long getID() {
		return 200907241819L;
	}

	public String getName() {
		return "分发到目录定时任务";
	}
}
