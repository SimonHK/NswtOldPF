package com.nswt.workflow.methods;

import com.nswt.workflow.Context;

/**
 * ������������̳б���
 * 
 * ���� : 2009-9-6 <br>
 * ����: NSWT <br>
 * ���䣺nswt@nswt.com.cn <br>
 */
public abstract class ConditionMethod {
	/**
	 * ��������Ƿ�����
	 */
	public abstract boolean validate(Context context) throws Exception;
}
