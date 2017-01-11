package com.nswt.datachannel;

import com.nswt.framework.User;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.platform.Application;
import com.nswt.platform.pub.ConfigEanbleTaskManager;
import com.nswt.schema.ZCWebGatherSchema;
import com.nswt.search.crawl.CrawlConfig;
import com.nswt.search.crawl.Crawler;

/**
 * @Author NSWT
 * @Date 2008-7-17
 * @Mail nswt@nswt.com.cn
 */
public class WebCrawlTaskManager extends ConfigEanbleTaskManager {
	public static final String CODE = "WebCrawl";

	Mapx runningMap = new Mapx();

	public boolean isRunning(long id) {
		int r = runningMap.getInt(new Long(id));
		return r != 0;
	}

	public Mapx getConfigEnableTasks() {
		DataTable dt = null;
		if (User.getCurrent() != null) {
			dt =
					new QueryBuilder("select id,name from ZCWebGather where siteid=?", Application.getCurrentSiteID()).executeDataTable();
		} else {
			dt = new QueryBuilder("select id,name from ZCWebGather").executeDataTable();
		}
		return dt.toMapx(0, 1);
	}

	public void execute(long id) {
		runningMap.put(new Long(id), 1);
		final ZCWebGatherSchema wg = new ZCWebGatherSchema();
		wg.setID(id);
		if (wg.fill()) {
			if("N".equals(wg.getStatus())){
				return;//停用的任务不自动执行
			}
			Thread thread = new Thread() {
				public void run() {
					try {
						CrawlConfig cc = new CrawlConfig();
						cc.parse(wg);
						Crawler crawler = new Crawler();
						crawler.setConfig(cc);
						crawler.crawl();
					} finally {
						runningMap.remove(new Long(wg.getID()));
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
		return "Web采集任务";
	}
}
