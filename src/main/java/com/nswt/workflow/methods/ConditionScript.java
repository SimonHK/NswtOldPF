package com.nswt.workflow.methods;

import com.nswt.framework.script.EvalException;
import com.nswt.framework.script.ScriptEngine;
import com.nswt.framework.utility.StringUtil;
import com.nswt.workflow.Context;

/**
 * 日期 : 2009-9-3 <br>
 * 作者: NSWT <br>
 * 邮箱：nswt@nswt.com.cn <br>
 */
public class ConditionScript extends ConditionMethod {
	private String script;

	public void setScript(String script) {
		this.script = script;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nswt.flow.Condition#passesCondition(java.lang.String)
	 */
	public boolean validate(Context context) throws EvalException {
		if (StringUtil.isEmpty(script)) {
			return true;
		}
		ScriptEngine se = new ScriptEngine(ScriptEngine.LANG_JAVASCRIPT);
		se.importPackage("com.nswt.framework.cache");
		se.importPackage("com.nswt.framework.data");
		se.importPackage("com.nswt.framework.utility");
		se.compileFunction("_tmp", "return " + script);
		se.setVar("context", context);
		Object obj = se.executeFunction("_tmp");
		if (obj instanceof Boolean) {
			return ((Boolean) obj).booleanValue();
		}
		throw new RuntimeException("流程条件脚本返回的不是布尔型!");
	}
}
