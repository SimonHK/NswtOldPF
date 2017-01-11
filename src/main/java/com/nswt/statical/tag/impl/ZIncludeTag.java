package com.nswt.statical.tag.impl;

import java.io.File;

import com.nswt.cms.pub.SiteUtil;
import com.nswt.framework.utility.Errorx;
import com.nswt.framework.utility.FileUtil;
import com.nswt.framework.utility.StringUtil;
import com.nswt.statical.exception.TemplateRuntimeException;
import com.nswt.statical.tag.SimpleTag;
import com.nswt.statical.tag.TagAttributeDesc;
import com.nswt.statical.tag.TagBase;
import com.nswt.statical.template.TemplateBase;
import com.nswt.statical.template.TemplateManager;

/**
 * 日期 : 2010-6-12 <br>
 * 作者: NSWT <br>
 * 邮箱：nswt@nswt.com.cn <br>
 */
public class ZIncludeTag extends SimpleTag {
	public TagAttributeDesc[] getAllAttributeDescs() {
		return new TagAttributeDesc[] { new TagAttributeDesc("file", true), new TagAttributeDesc("type") };
	}

	public void editWrapStart() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < tagTreeIndex.length; i++) {
			if (i != 0) {
				sb.append("_");
			}
			sb.append(tagTreeIndex[i]);
		}
		String id = sb.toString();
		sb = new StringBuffer();
		Object[] ks = attributes.keyArray();
		for (int i = 0; i < attributes.size(); i++) {
			sb.append(ks[i] + ":" + attributes.get(ks[i]) + "\n");
		}
		String attr = sb.toString();
		template.getWriter().print(
				"<textarea id='_template_textarea_" + id + "' style='display:none'>" + attr + "</textarea>");
		template.getWriter()
				.print("<span id='_template_span_" + id + "' ztype='template' style='display:none'></span>");
	}

	public int onTagStart() throws TemplateRuntimeException {
		String path = template.getTemplateFilePath();
		String file = attributes.getString("file");
		file = StringUtil.replaceEx(file, "${Level}", "");
		if (template.getConfig().getVersion() == 1.0
				&& (file.startsWith("template/") || file.indexOf("/template/") > 0)) {
			file = StringUtil.replaceEx(file, "template/", "");
		}
		if (path.endsWith("/")) {
			path = path.substring(0, path.length() - 1);
		}
		int level = 0;
		String levelStr = context.eval("Level");
		while (true) {
			if (StringUtil.isNotEmpty(levelStr) && levelStr.startsWith("../")) {
				level++;
				levelStr = levelStr.substring(3);
			} else {
				break;
			}
		}
		String oldFile = file;
		file = path + "/" + file;
		String type = attributes.getString("type");
		if ("template".equalsIgnoreCase(type)) {
			TemplateBase tpl = null;
			try {
				file = StringUtil.replaceEx(file, "include/", "");
				tpl = TemplateManager.find(file);
				tpl.setDestination(template.getDestination());
				tpl.setContext(context);
				tpl.execute();
				template.getWriter().print(tpl.getResult());
			} catch (Exception e1) {
				throw new TemplateRuntimeException(e1.getMessage(), e1);
			}
		} else {
			file = oldFile.substring(0, oldFile.lastIndexOf(".")) + "_" + level + "."
					+ oldFile.substring(oldFile.lastIndexOf(".") + 1);
			String absoluteFile = SiteUtil.getAbsolutePath(context.eval("Site.ID")) + "/" + file;
			try {
				String templateFile = path + "/" + oldFile;
				templateFile = StringUtil.replaceEx(templateFile, "include/", "template/");
				templateFile = StringUtil.replaceEx(templateFile, "template/template/", "template/");
				TemplateBase tpl = TemplateManager.find(templateFile);
				File f = new File(absoluteFile);
				if (!f.exists() || tpl.getConfig().getLastModified() > f.lastModified()) {
					tpl.setDestination(absoluteFile);
					tpl.setContext(context);
					tpl.execute();
					FileUtil.writeText(absoluteFile, tpl.getResult());
				}
			} catch (Exception e) {
				throw new TemplateRuntimeException(e.getMessage(), e);
			}
			if (context.isSSIContext()) {
				template.getWriter().print("<!--#include virtual=\"" + context.eval("Level") + file + "\"-->");
			} else {
				template.getWriter().print(TemplateManager.getFileText(absoluteFile));
			}
		}
		return TagBase.SKIP;// 不输出body
	}

	public String getPrefix() {
		return "z";
	}

	public String getTagName() {
		return "include";
	}

	public boolean checkAttribute() {
		if (!super.checkAttribute()) {
			return false;
		}
		String type = attributes.getString("type");
		if (StringUtil.isNotEmpty(type)) {
			if (type.equalsIgnoreCase("html") || type.equalsIgnoreCase("template")) {
				return true;
			} else {
				Errorx.addError(this.getPrefix() + ":" + this.getTagName() + "的type属性值不正确:" + type);
				return false;
			}
		}
		return true;
	}
}
