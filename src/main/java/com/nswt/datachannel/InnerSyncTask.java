package com.nswt.datachannel;

import com.nswt.framework.User;
import com.nswt.framework.User.UserData;
import com.nswt.framework.schedule.GeneralTask;
import com.nswt.framework.utility.LogUtil;
import com.nswt.schema.ZCInnerDeploySchema;
import com.nswt.schema.ZCInnerDeploySet;
import com.nswt.schema.ZCInnerGatherSchema;
import com.nswt.schema.ZCInnerGatherSet;

/**
 * 日期 : 2010-5-21 <br>
 * 作者: NSWT <br>
 * 邮箱：nswt@nswt.com.cn <br>
 */
public class InnerSyncTask extends GeneralTask {
	private boolean isRunning = false;
	private static Object mutex = new Object();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nswt.framework.schedule.GeneralTask#execute()
	 */
	public void execute() {
		if (!isRunning) {
			synchronized (mutex) {
				if (!isRunning) {
					new Thread() {
						public void run() {
							isRunning = true;
							UserData ud = new UserData();
							ud.setUserName("SYSTEM");
							ud.setLogin(true);
							ud.setManager(true);
							User.setCurrent(new UserData());
							long t = System.currentTimeMillis();
							try {
								ZCInnerGatherSet gatherSet = new ZCInnerGatherSchema().query();
								for (int i = 0; i < gatherSet.size(); i++) {
									if ("N".equals(gatherSet.get(i).getStatus())) {
										continue;
									}
									InnerSyncService.executeGather(gatherSet.get(i), null);
								}
								ZCInnerDeploySet deploySet = new ZCInnerDeploySchema().query();
								for (int i = 0; i < deploySet.size(); i++) {
									if ("N".equals(deploySet.get(i).getStatus())) {
										continue;
									}
									InnerSyncService.executeDeploy(deploySet.get(i), null);
								}
							} finally {
								isRunning = false;
							}
							LogUtil.info("网站群任务耗时：" + (System.currentTimeMillis() - t) + "毫秒");
						}
					}.start();
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nswt.framework.schedule.AbstractTask#getID()
	 */
	public long getID() {
		return 201005212237L;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nswt.framework.schedule.AbstractTask#getName()
	 */
	public String getName() {
		return "网站群任务";
	}

}
