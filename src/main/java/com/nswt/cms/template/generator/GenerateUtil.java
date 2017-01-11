package com.nswt.cms.template.generator;

import java.io.File;
import java.util.Date;

import com.nswt.cms.pub.SiteUtil;
import com.nswt.framework.Config;
import com.nswt.framework.utility.DateUtil;
import com.nswt.framework.utility.FileUtil;
import com.nswt.framework.utility.LogUtil;
import com.nswt.statical.template.TemplateBase;
import com.nswt.statical.template.TemplateContext;
import com.nswt.statical.template.TemplateManager;

/*
 * @Author 王育春
 * @Date 2016-8-18
 * @Mail nswt@nswt.com.cn
 */
public class GenerateUtil {

	public static void execute(String templateFile, String destFile, TemplateContext context, String type)
			throws Exception {
		TemplateBase template = TemplateManager.find(templateFile);
		execute(template, destFile, context, type);
	}

	public static void execute(TemplateBase template, String destFile, TemplateContext context, String type)
			throws Exception {
		long t = System.currentTimeMillis();
		template.setDestination(destFile);
		template.setContext(context);
		template.execute();
		long cost = System.currentTimeMillis() - t;
		String result = template.getResult();
		if (!"Not".equalsIgnoreCase(type)) {
			String script = getStatScript(context, type);
			String comment = "\n<!-- Published by " + Config.getAppCode() + "(" + Config.getAppName() + ") "
					+ Config.getMainVersion() + "." + Config.getMinorVersion() + " 耗时" + cost + "毫秒 PublishDate "
					+ DateUtil.toDateTimeString(new Date()) + "-->";
			result = result + script + comment;
		}
		File f = new File(destFile.substring(0, destFile.lastIndexOf("/")));
		if (!f.exists()) {
			f.mkdirs();
		}
		FileUtil.writeText(destFile, result);
		LogUtil.info("生成文件" + destFile + "耗时" + cost + "毫秒");
	}

	public static String getStatScript(TemplateContext context, String type) {
		String url = Config.getValue("ServicesContext");
		if ("Y".equals(SiteUtil.getSchema(context.eval("Site.ID")).getAutoStatFlag())) {
			// 统计代码
			StringBuffer sb = new StringBuffer();
			sb.append("SiteID=" + context.eval("Site.ID"));
			sb.append("&Type=Article");
			if ("Catalog".equalsIgnoreCase(type)) {
				sb.append("&CatalogInnerCode=" + context.eval("Catalog.InnerCode"));
			}
			if ("Article".equalsIgnoreCase(type)) {
				sb.append("&LeafID=" + context.eval("Article.ID"));
			}
			sb.append("&Dest=" + url + "/Stat.jsp");
			url = sb.toString();
		}
		String serviceUrl = Config.getValue("ServicesContext");
		String statScript = "\n<script src=\"" + serviceUrl + "/Stat.js\" type=\"text/javascript\"></script>\n";
		statScript += "<script>\n";
		statScript += "if(window._nswtp_stat)_nswtp_stat(\"" + url + "\");\n";
		statScript += "</script>\n";
		return statScript;
	}
}
