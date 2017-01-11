package com.nswt.datachannel;

import com.nswt.framework.User;
import com.nswt.framework.User.UserData;
import com.nswt.framework.schedule.GeneralTask;
import com.nswt.framework.utility.LogUtil;
import com.nswt.schema.ZCDBGatherSchema;
import com.nswt.schema.ZCDBGatherSet;

/**
 * ���� : 2010-5-28 <br>
 * ����: NSWT <br>
 * ���䣺nswt@nswt.com.cn <br>
 */
public class DBSyncTask extends GeneralTask {
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
								ZCDBGatherSet gatherSet = new ZCDBGatherSchema().query();
								for (int i = 0; i < gatherSet.size(); i++) {
									if ("N".equals(gatherSet.get(i).getStatus())) {
										continue;
									}
									FromDB.executeGather(gatherSet.get(i), false, null);
								}
							} finally {
								isRunning = false;
							}
							LogUtil.info("���ݿ�ɼ������ʱ��" + (System.currentTimeMillis() - t) + "����");
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
		return 201005281237L;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nswt.framework.schedule.AbstractTask#getName()
	 */
	public String getName() {
		return "���ݿ�ɼ�����";
	}

}
