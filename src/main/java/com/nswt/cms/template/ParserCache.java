package com.nswt.cms.template;

import java.io.File;
import java.util.ArrayList;

import com.nswt.framework.Config;
import com.nswt.framework.script.EvalException;
import com.nswt.framework.utility.CaseIgnoreMapx;
import com.nswt.framework.utility.FileUtil;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.statical.TemplateParser;

public class ParserCache {
	private static Mapx parserMap; // 模板解析结果缓存

	private static Mapx lastModifyMap; // 模板模板修改时间

	public static TemplateParser get(long siteID, String template, int level, boolean isPageBlock,ArrayList list) {
		template = template.trim();
		if (parserMap == null) {
			parserMap = new Mapx();
			lastModifyMap = new Mapx();
		}

		File templateFile = new File(template);
		template = templateFile.getPath().replace('\\', '/');

		String key = template + level;
		
		TemplateParser tp = null;

		if (parserMap.get(key) == null) {
			if (!templateFile.exists() || templateFile.isDirectory()) {
				return null;
			}
			update(siteID, templateFile, level, isPageBlock,list);
			tp = (TemplateParser) parserMap.get(template + level);
		} else {
			if (!templateFile.exists() || templateFile.isDirectory()) {
				return null;
			}
			long lastModifyTime = ((Long) lastModifyMap.get(template + level)).longValue();
			if (lastModifyTime != templateFile.lastModified()) {
				update(siteID, templateFile, level, isPageBlock,list);
			}
			tp = (TemplateParser) parserMap.get(template + level);
		}
		
        Mapx map = getConfig(siteID);
		tp.define("system", map);
		
		return tp;
	}

	public static Mapx getConfig(long siteID) {
		//定义全局变量
		CaseIgnoreMapx map = new CaseIgnoreMapx();
		String serviceUrl = Config.getValue("ServicesContext");
		String context = serviceUrl;
		if (serviceUrl.endsWith("/")) {
			context = serviceUrl.substring(0, serviceUrl.length() - 1);
		}
		int index = context.lastIndexOf('/');
		if (index != -1) {
			context = context.substring(0, index);
		}

		map.put("servicescontext", serviceUrl);
		map.put("cmscontext", context);//指向CMS应用根目录
		map.put("searchaction", context + "/Search/Result.jsp");// 搜索路径
		map.put("voteaction", serviceUrl + Config.getValue("Vote.ActionURL"));// 投票路径
		map.put("voteresult", serviceUrl + Config.getValue("Vote.ResultURL"));// 投票结果
		map.put("commentaction", serviceUrl + Config.getValue("CommentActionURL"));// 评论结果
		map.put("totalhitcount", "\n<script src=\"" + serviceUrl + "/Counter.jsp?Type=Total&ID=" + siteID
				+ "\" type=\"text/javascript\"></script>\n"); // 点击总数
		map.put("todayhitcount", "\n<script src=\"" + serviceUrl + "/Counter.jsp?Type=Today&ID=" + siteID
				+ "\" type=\"text/javascript\"></script>\n");// 当日点击数
		return map;
	}

	public static void update(long siteID, File templateFile, int level, boolean isPageBlock,ArrayList list) {
		String content = FileUtil.readText(templateFile);
		String templatePath = templateFile.getPath().replace('\\', '/');

		LogUtil.info("模板更新" + templatePath);
		
		//模板预处理
		PreParser p = new PreParser();
		p.setSiteID(siteID);
		p.setTemplateFileName(templatePath);
		p.parse();

		//模板标签解析
		TagParser tagParser = new TagParser();
		tagParser.setSiteID(siteID);
		tagParser.setTemplateFileName(templatePath);
		tagParser.setPageBlock(isPageBlock);
		tagParser.setContent(content);
		tagParser.setDirLevel(level);
		tagParser.parse();

		TemplateParser tp = new TemplateParser();
		tp.setFileName(templateFile.getPath());
		tp.addClass("com.nswt.cms.pub.CatalogUtil");
		tp.addClass("com.nswt.cms.pub.SiteUtil");
		tp.addClass("com.nswt.cms.pub.PubFun");
		tp.addClass("com.nswt.cms.template.TemplateUtil");
		tp.setPageListPrams(tagParser.getPageListPrams());
		tp.setTemplate(tagParser.getContent());

		try {
			tp.parse();
			
			parserMap.put(templatePath + level, tp);
			lastModifyMap.put(templatePath + level, new Long(templateFile.lastModified()));
			
			list.addAll(tagParser.getFileList());
		} catch (EvalException e) {
			e.printStackTrace();
		}
	}
}
