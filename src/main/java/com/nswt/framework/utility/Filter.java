package com.nswt.framework.utility;

/**
 * �ɴ��ݲ����Ĺ�����
 * 
 * @author NSWT
 * @date 2009-11-15
 * @email nswt@nswt.com.cn
 */
public abstract class Filter {
	protected Object Param;// ���ڴ��ݲ���

	/**
	 * ����һ���յĹ���������
	 */
	public Filter() {
	}

	/**
	 * ����һ���в����Ĺ���������
	 * 
	 * @param param
	 */
	public Filter(Object param) {
		this.Param = param;
	}

	/**
	 * ����true��ʾ����������false��ʾ���˵�
	 */
	public abstract boolean filter(Object obj);

}
