package com.nswt.statical.tag;

/**
 * 修饰符处理器，处理${Holder|Format=yyyy-MM-dd}中竖线后面的部分
 * 
 * 日期 : 2010-7-9 <br>
 * 作者: NSWT <br>
 * 邮箱：nswt@nswt.com.cn <br>
 */
public interface IModifierHandler {
	public String getName();

	public String getUsage();

	public Object deal(Object value, String[] args);
}
