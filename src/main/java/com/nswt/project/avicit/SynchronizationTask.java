/**
 * 
 */
package com.nswt.project.avicit;

import com.nswt.framework.schedule.GeneralTask;

/**
 * @author Administrator
 *
 */
public class SynchronizationTask extends GeneralTask {

	public void execute() {
		SynchronizationData.excute();
	}
	
	public long getID() {
		return 201008251819L;
	}

	public String getName() {
		return "同步用户定时任务";
	}
}
