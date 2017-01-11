package com.nswt.cms.stat;

import java.util.Date;

import com.nswt.cms.stat.impl.GlobalStat;
import com.nswt.framework.schedule.GeneralTask;
import com.nswt.framework.utility.DateUtil;

/**
 * @Author NSWT
 * @Date 2008-12-19
 * @Mail nswt@nswt.com.cn
 */
public class StatUpdateTask extends GeneralTask {
	private long LastUpdateTime = System.currentTimeMillis();

	public void execute() {
		long current = System.currentTimeMillis();
		VisitHandler.init(current);
		GlobalStat.dealVisitHistory(current);
		if (!DateUtil.toString(new Date(current)).equals(DateUtil.toString(new Date(LastUpdateTime)))) {
			VisitHandler.changePeriod(AbstractStat.PERIOD_DAY, current);// 新的一天
		} else {
			VisitHandler.update(System.currentTimeMillis(), false, false);
		}
		LastUpdateTime = current;
	}

	public long getID() {
		return 200812191853L;
	}

	public String getName() {
		return "定时更新CMS统计信息";
	}

}
