package com.nswt.framework.cache;

import com.nswt.framework.utility.Mapx;

/**
 * @Author NSWT
 * @Date 2008-10-30
 * @Mail nswt@nswt.com.cn
 */
public abstract class CacheProvider {
	protected Mapx TypeMap = new Mapx();

	/**
	 * ��������
	 */
	public abstract String getProviderName();

	/**
	 * ������ֵʱ���ô˷�����Ĭ�ϲ���Ҫ���ر�����
	 */
	public void onKeySet(String type, Object key, Object value) {
	}

	/**
	 * ��ĳ���������û���ҵ�ʱ���ô˷���
	 */
	public abstract void onTypeNotFound(String type);

	/**
	 * ��ĳ��������û���ҵ�ʱ���ô˷���
	 */
	public abstract void onKeyNotFound(String type, Object key);
}
