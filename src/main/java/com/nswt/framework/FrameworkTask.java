package com.nswt.framework;

import java.io.File;

import com.nswt.framework.schedule.GeneralTask;
import com.nswt.framework.utility.FileUtil;

/**
 * 清空Debug模式下的Session缓存
 * 
 * @Author 王育春
 * @Date 2008-12-27
 * @Mail nswt@nswt.com.cn
 */
public class FrameworkTask extends GeneralTask {

	public void execute() {
		// 清除缓存
		File dir = new File(Config.getContextRealPath() + "WEB-INF/cache/");
		File[] fs = dir.listFiles();
		for (int i = 0; i < fs.length; i++) {
			File f = fs[i];
			if (f.isFile()) {
				FileUtil.delete(f);
			}
		}
		// 清除三十天以前的上传文件
		// dir = new File(Config.getContextRealPath() + "WEB-INF/data/backup/");
		// if (dir.exists()) {
		// fs = dir.listFiles();
		// long cleanTime = System.currentTimeMillis() - 30 * 24 * 60 * 1000;
		// for (int i = 0; i < fs.length; i++) {
		// File f = fs[i];
		// if (f.isFile() && !f.getName().equalsIgnoreCase("install.dat") &&
		// f.lastModified() < cleanTime) {
		// FileUtil.delete(f);
		// }
		// }
		// }
	}

	public long getID() {
		return 20080315112344L;
	}

	public String getName() {
		return "Framework定时任务";
	}

}
