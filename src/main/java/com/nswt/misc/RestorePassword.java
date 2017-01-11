package com.nswt.misc;

import com.nswt.framework.schedule.GeneralTask;
import com.nswt.framework.utility.StringUtil;
import com.nswt.schema.ZDUserSchema;

/**
 * @Author NSWT
 * @Date 2009-5-25
 * @Mail nswt@nswt.com.cn
 */
public class RestorePassword extends GeneralTask {

	public void execute() {
		ZDUserSchema user = new ZDUserSchema();
		user.setUserName("demo");
		user.fill();
		user.setPassword(StringUtil.md5Hex("demo"));
		user.setStatus("1");
		user.update();
	}

	public String getCronExpression() {
		return "* * * * *";
	}

	public long getID() {
		return 200905251628L;
	}

	public String getName() {
		return "定时恢复密码，演示站专用";
	}

	public static void main(String[] args) {
		RestorePassword rp = new RestorePassword();
		rp.execute();
	}
}
