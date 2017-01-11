package com.nswt.framework.extend;

/**
 * 需要在宿主类中扩展逻辑的类必须继承此类
 * 
 * 日期 : 2016-11-7 <br>
 * 作者: NSWT <br>
 * 邮箱：nswt@nswt.com.cn <br>
 */
public interface IExtendAction {
	/**
	 * 目标扩展点
	 */
	public String getTarget();

	/**
	 * 扩展名称
	 */
	public String getName();

	/**
	 * 扩展逻辑
	 */
	public void execute(Object[] args);
}
