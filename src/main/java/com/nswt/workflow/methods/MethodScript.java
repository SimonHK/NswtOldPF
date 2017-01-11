package com.nswt.workflow.methods;

import com.nswt.framework.script.EvalException;
import com.nswt.framework.script.ScriptEngine;
import com.nswt.framework.utility.StringUtil;
import com.nswt.workflow.Context;

/**
 * 日期 : 2009-9-6 <br>
 * 作者: NSWT <br>
 * 邮箱：nswt@nswt.com.cn <br>
 */
public class MethodScript extends NodeMethod {
	private String script;

	public MethodScript(String script) {
		this.script = script;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nswt.flow.NodeAction#excute()
	 */
	public void execute(Context context) throws EvalException {
		if (StringUtil.isEmpty(script)) {
			return;
		}
		ScriptEngine se = new ScriptEngine(ScriptEngine.LANG_JAVASCRIPT);
		se.importPackage("com.nswt.framework.cache");
		se.importPackage("com.nswt.framework.data");
		se.importPackage("com.nswt.framework.utility");
		se.importPackage("com.nswt.workflow");
		se.compileFunction("_tmp", "return " + script);
		se.setVar("context", context);
		se.executeFunction("_tmp");
	}

}
