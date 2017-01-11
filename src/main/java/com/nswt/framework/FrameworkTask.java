package com.nswt.framework;

import java.io.File;

import com.nswt.framework.schedule.GeneralTask;
import com.nswt.framework.utility.FileUtil;

/**
 * ���Debugģʽ�µ�Session����
 * 
 * @Author ������
 * @Date 2008-12-27
 * @Mail nswt@nswt.com.cn
 */
public class FrameworkTask extends GeneralTask {

	public void execute() {
		// �������
		File dir = new File(Config.getContextRealPath() + "WEB-INF/cache/");
		File[] fs = dir.listFiles();
		for (int i = 0; i < fs.length; i++) {
			File f = fs[i];
			if (f.isFile()) {
				FileUtil.delete(f);
			}
		}
		// �����ʮ����ǰ���ϴ��ļ�
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
		return "Framework��ʱ����";
	}

}
