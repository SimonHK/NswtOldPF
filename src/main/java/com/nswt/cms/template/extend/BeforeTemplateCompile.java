package com.nswt.cms.template.extend;

import com.nswt.framework.extend.IExtendAction;
import com.nswt.statical.template.TemplateCompiler;

/*
 * @Author NSWT
 * @Date 2016-8-4
 * @Mail nswt@nswt.com.cn
 */
public class BeforeTemplateCompile implements IExtendAction {

	public String getTarget() {
		return "Template.BeforeCompile";
	}

	public String getName() {
		return "CMSƒ£∞Â±‡“Î«∞¿©’π";
	}

	public void execute(Object[] args) {
		TemplateCompiler compiler = (TemplateCompiler) args[0];
		compiler.addImport("com.nswt.cms.pub.*");
		compiler.addImport("com.nswt.cms.tag.*");
	}

}
