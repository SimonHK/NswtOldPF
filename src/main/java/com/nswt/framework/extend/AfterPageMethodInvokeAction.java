package com.nswt.framework.extend;

/**
 * 日期 : 2016-11-7 <br>
 * 作者: NSWT <br>
 * 邮箱：nswt@nswt.com.cn <br>
 */
public abstract class AfterPageMethodInvokeAction implements IExtendAction {
	public static final String Type = "AfterPageMethodInvoke";

	public String getTarget() {
		return Type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nswt.framework.extend.IExtendAction#execute(java.lang.Object[])
	 */
	public void execute(Object[] args) {
		execute((String) args[0]);
	}

	public abstract void execute(String method);
}
