package com.nswt.framework.extend;

/**
 * ���� : 2016-11-7 <br>
 * ����: NSWT <br>
 * ���䣺nswt@nswt.com.cn <br>
 */
public abstract class BeforeSessionDestroyAction implements IExtendAction {
	public static final String Type = "BeforeSessionDestroy";

	public String getTarget() {
		return Type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nswt.framework.extend.IExtendAction#execute(java.lang.Object[])
	 */
	public void execute(Object[] args) {
	}
}
