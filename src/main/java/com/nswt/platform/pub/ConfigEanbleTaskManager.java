package com.nswt.platform.pub;

import com.nswt.framework.schedule.AbstractTaskManager;
import com.nswt.framework.utility.Mapx;
import com.nswt.schema.ZDScheduleSchema;
import com.nswt.schema.ZDScheduleSet;

/**
 * 可通过界面配置的任务管理器，会将任务执行计划保存在数据库ZDSchedule表中
 * 
 * @Author 王育春
 * @Date 2009-4-16
 * @Mail nswt@nswt.com.cn
 */
public abstract class ConfigEanbleTaskManager extends AbstractTaskManager {
	protected static ZDScheduleSet set;
	private static Object mutex = new Object();
	protected static long LastTime;

	public Mapx getUsableTasks() {
		synchronized (mutex) {
			if (set == null || System.currentTimeMillis() - LastTime > 60000) {
				set = new ZDScheduleSchema().query();
				LastTime = System.currentTimeMillis();
			}
		}
		Mapx map = new Mapx();
		for (int i = 0; i < set.size(); i++) {
			ZDScheduleSchema s = set.get(i);
			if (s.getTypeCode().equals(this.getCode()) && "Y".equals(s.getIsUsing())) {
				map.put(new Long(s.getSourceID()), "");
			}
		}
		return map;
	}

	public String getTaskCronExpression(long id) {
		synchronized (mutex) {
			if (set == null || System.currentTimeMillis() - LastTime > 60000) {
				set = new ZDScheduleSchema().query();
				LastTime = System.currentTimeMillis();
			}
		}
		for (int i = 0; i < set.size(); i++) {
			ZDScheduleSchema s = set.get(i);
			if (s.getTypeCode().equals(this.getCode()) && s.getSourceID() == id) {
				return s.getCronExpression();
			}
		}
		return null;
	}
}
