package com.nswt.framework.extend;

/**
 * ��Ҫ������������չ�߼��������̳д���
 * 
 * ���� : 2016-11-7 <br>
 * ����: NSWT <br>
 * ���䣺nswt@nswt.com.cn <br>
 */
public interface IExtendAction {
	/**
	 * Ŀ����չ��
	 */
	public String getTarget();

	/**
	 * ��չ����
	 */
	public String getName();

	/**
	 * ��չ�߼�
	 */
	public void execute(Object[] args);
}
