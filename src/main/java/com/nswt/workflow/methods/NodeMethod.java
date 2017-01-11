package com.nswt.workflow.methods;

import com.nswt.workflow.Context;

/**
 * 在流程实例开始之前初始化表单中的值
 * 
 * 日期 : 2009-9-6 <br>
 * 作者: NSWT <br>
 * 邮箱：nswt@nswt.com.cn <br>
 */
public abstract class NodeMethod {
	public abstract void execute(Context context) throws Exception;
}
