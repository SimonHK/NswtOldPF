package com.nswt.statical.tag;

/**
 * ���η�������������${Holder|Format=yyyy-MM-dd}�����ߺ���Ĳ���
 * 
 * ���� : 2010-7-9 <br>
 * ����: NSWT <br>
 * ���䣺nswt@nswt.com.cn <br>
 */
public interface IModifierHandler {
	public String getName();

	public String getUsage();

	public Object deal(Object value, String[] args);
}
