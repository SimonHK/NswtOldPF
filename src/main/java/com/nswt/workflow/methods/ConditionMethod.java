package com.nswt.workflow.methods;

import com.nswt.workflow.Context;

/**
 * 条件动作必须继承本类
 * 
 * 日期 : 2009-9-6 <br>
 * 作者: NSWT <br>
 * 邮箱：nswt@nswt.com.cn <br>
 */
public abstract class ConditionMethod {
	/**
	 * 检查条件是否满足
	 */
	public abstract boolean validate(Context context) throws Exception;
}
