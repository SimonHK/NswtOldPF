package com.nswt.framework.utility;

/**
 * ִ����,��Ҫִ�е�JAVA�߼����ݸ���������������������������á�<br>
 * 
 * ���� : 2010-2-8 <br>
 * ����: NSWT <br>
 * ���䣺nswt@nswt.com.cn <br>
 */
public abstract class Executor {
	protected Object param;

	public Executor(Object param) {
		this.param = param;
	}

	public abstract boolean execute();
}
