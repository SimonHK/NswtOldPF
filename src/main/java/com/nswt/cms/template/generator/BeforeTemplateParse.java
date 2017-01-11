package com.nswt.cms.template.generator;

import com.nswt.framework.Config;
import com.nswt.framework.extend.IExtendAction;
import com.nswt.framework.utility.FileUtil;
import com.nswt.statical.template.TemplateParser;

/*
 * @Author 王育春
 * @Date 2016-8-4
 * @Mail nswt@nswt.com.cn
 */
public class BeforeTemplateParse implements IExtendAction {

	public String getTarget() {
		return "Template.BeforeParse";
	}

	public String getName() {
		return "CMS模板解析前扩展";
	}

	public void execute(Object[] args) {
		TemplateParser parser = (TemplateParser) args[0];
		String fileName = parser.getFileName();
		String content = parser.getContent();

		String prefix = Config.getContextRealPath() + Config.getValue("Statical.TemplateDir");
		prefix = FileUtil.normalizePath(prefix);
		fileName = FileUtil.normalizePath(fileName);
		String base = fileName.substring(0, fileName.indexOf("/", prefix.length() + 1));

		content = ResourceReplacer.dealResource(base, fileName, content);
		parser.setContent(content);
	}
}
