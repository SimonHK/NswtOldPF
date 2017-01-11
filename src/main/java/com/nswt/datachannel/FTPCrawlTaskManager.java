package com.nswt.datachannel;

import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.platform.pub.ConfigEanbleTaskManager;
import com.nswt.schema.ZCFtpGatherSchema;

/**
 * @Author NSWT
 * @Date 2008-7-17
 * @Mail nswt@nswt.com.cn
 */
public class FTPCrawlTaskManager extends ConfigEanbleTaskManager {
	public static final String CODE = "FTPCrawl";

	Mapx runningMap = new Mapx();

	public boolean isRunning(long id) {
		int r = runningMap.getInt(new Long(id));
		return r != 0;
	}

	public Mapx getConfigEnableTasks() {
		DataTable dt = new QueryBuilder("select ID,Name from ZCFTPGather").executeDataTable();
		return dt.toMapx(0, 1);
	}

	public void execute(long id) {
		runningMap.put(new Long(id), 1);
		final ZCFtpGatherSchema ftpSchema = new ZCFtpGatherSchema();
		ftpSchema.setID(id);
		if (ftpSchema.fill()) {
			if("N".equals(ftpSchema.getStatus())){
				return;//停用的任务不自动执行
			}
			Thread thread = new Thread() {
				public void run() {
					try {
						FromFTP.taskDeal(String.valueOf(ftpSchema.getID()));
					} finally {
						runningMap.remove(new Long(ftpSchema.getID()));
					}
				}
			};
			thread.start();
		} else {
			LogUtil.info("没有找到对应的抓取任务.任务ID:" + id);
		}
	}

	public String getCode() {
		return CODE;
	}

	public String getName() {
		return "FTP采集任务";
	}
}
