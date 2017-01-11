package com.nswt.statical.tag;

import com.nswt.statical.template.TemplateCompiler;

/**
 * @Author NSWT
 * @Date 2016-5-30
 * @Mail nswt@nswt.com.cn
 */
public abstract class SimpleTag extends TagBase {
	public boolean isIterative(TemplateCompiler compiler) {
		return false;
	}

	public int onTagEnd() {
		return TagBase.CONTINUE;
	}

	public boolean prepareNext() {
		return false;
	}

}
