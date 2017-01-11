package com.nswt.cms.dataservice;

import com.nswt.cms.api.SearchAPI;
import com.nswt.framework.User;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.utility.Mapx;
import com.nswt.platform.Application;
import com.nswt.platform.pub.ConfigEanbleTaskManager;

public class FullTextTaskManager extends ConfigEanbleTaskManager {
	public static final String CODE = "IndexMaintenance";

	Mapx runningMap = new Mapx();

	public boolean isRunning(long id) {
		int r = runningMap.getInt(new Long(id));
		return r != 0;
	}

	public void execute(final long id) {
		runningMap.put(new Long(id), 1);
		new Thread() {
			public void run() {
				try {
					SearchAPI.update(id);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					runningMap.remove(new Long(id));
				}
			}
		}.start();
	}

	public Mapx getConfigEnableTasks() {
		DataTable dt = null;
		if (User.getCurrent() != null) {
			dt = new QueryBuilder("select id,name from ZCFullText where siteid=?", Application.getCurrentSiteID())
					.executeDataTable();
		} else {
			dt = new QueryBuilder("select id,name from ZCFullText").executeDataTable();
		}
		return dt.toMapx(0, 1);
	}

	public String getCode() {
		return "IndexMaintenance";
	}

	public String getName() {
		return "索引维护任务";
	}

}
