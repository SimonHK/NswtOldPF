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
	 * 返回类型
	 */
	public abstract String getProviderName();

	/**
	 * 当置入值时调用此方法，默认不需要重载本方法
	 */
	public void onKeySet(String type, Object key, Object value) {
	}

	/**
	 * 当某个组存类型没有找到时调用此方法
	 */
	public abstract void onTypeNotFound(String type);

	/**
	 * 当某个数据项没有找到时调用此方法
	 */
	public abstract void onKeyNotFound(String type, Object key);
}
