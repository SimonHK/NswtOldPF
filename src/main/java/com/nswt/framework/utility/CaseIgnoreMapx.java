package com.nswt.framework.utility;

import java.util.Map;

/**
 * ���������ִ�дС��Map
 * 
 * @Author NSWT
 * @Date 2009-1-5
 * @Mail nswt@nswt.com.cn
 */
public class CaseIgnoreMapx extends Mapx {
	private static final long serialVersionUID = 1L;

	/**
	 * ����һ�����������ִ�С��Map
	 */
	public CaseIgnoreMapx() {
		super();
	}

	/**
	 * ����ָ��Map����һ�����������ִ�С��Map
	 */
	public CaseIgnoreMapx(Map map) {
		super();
		this.putAll(map);
	}

	/**
	 * ����Map��ֵ�ԣ���������ַ����������ִ�Сд
	 * 
	 * @see java.util.HashMap#put(K, V)
	 */
	public Object put(Object key, Object value) {
		if (key != null && key instanceof String) {
			return super.put(key.toString().toLowerCase(), value);
		}
		return super.put(key, value);
	}

	/**
	 * ��ȡMap�еļ�ֵ����������ַ����������ִ�Сд
	 * 
	 * @see com.nswt.framework.utility.Mapx#get(java.lang.Object)
	 */
	public Object get(Object key) {
		if (key != null && key instanceof String) {
			return super.get(key.toString().toLowerCase());
		}
		return super.get(key);
	}

	/**
	 * �ж��Ƿ���ָ��������������ַ����������ִ�Сд
	 */
	public boolean containsKey(Object key) {
		if (key != null && key instanceof String) {
			return super.containsKey(key.toString().toLowerCase());
		}
		return super.containsKey(key);
	}

	/**
	 * �Ƴ�ָ��������������ַ����������ִ�Сд
	 */
	public Object remove(Object key) {
		if (key != null && key instanceof String) {
			return super.remove(key.toString().toLowerCase());
		}
		return super.remove(key);
	}
}
