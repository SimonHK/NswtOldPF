package com.nswt.framework.extend;

import javax.servlet.http.HttpSession;

/**
 * 日期 : 2016-11-7 <br>
 * 作者: NSWT <br>
 * 邮箱：nswt@nswt.com.cn <br>
 */
public abstract class AfterSessionCreateAction implements IExtendAction {
	public static final String Type = "AfterSessionCreate";

	public String getTarget() {
		return Type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nswt.framework.extend.IExtendAction#execute(java.lang.Object[])
	 */
	public void execute(Object[] args) {
		HttpSession session = (HttpSession) args[0];
		execute(session);
	}

	public abstract void execute(HttpSession session);
}
