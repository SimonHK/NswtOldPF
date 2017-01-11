package com.nswt.cms.template.generator;

import java.io.File;
import java.util.Date;

import com.nswt.cms.pub.CatalogUtil;
import com.nswt.cms.pub.SiteUtil;
import com.nswt.cms.template.ParserCache;
import com.nswt.framework.Config;
import com.nswt.framework.data.DataRow;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.utility.DateUtil;
import com.nswt.framework.utility.FileUtil;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.StringUtil;
import com.nswt.schema.ZCCatalogSchema;
import com.nswt.schema.ZCSiteSchema;
import com.nswt.statical.template.TemplateBase;
import com.nswt.statical.template.TemplateContext;
import com.nswt.statical.template.TemplateManager;

/*
 * 1.x的页面生成器
 * 
 * @Author NSWT
 * @Date 2016-7-30
 * @Mail nswt@nswt.com.cn
 */
public class Generator1x {
	/**
	 * 生成站点首页
	 */
	public static void generateIndex(long siteID) throws Exception {
		ZCSiteSchema site = SiteUtil.getSchema(siteID);
		TemplateContext context = new TemplateContext();
		context.setSSIContext(true);
		context.addDataVariable("site", site.toDataRow());
		context.addDataVariable("level", "");
		context.addDataVariable("system", ParserCache.getConfig(site.getID()));
		String fileName = SiteUtil.getAbsolutePath(site.getID()) + "index.shtml";
		writeFile(SiteUtil.getAbsolutePath(site.getID()) + site.getIndexTemplate(), fileName, context);
	}

	/**
	 * 生成栏目下页面，包括栏目首页，列表页，详细页
	 * 
	 * @param pageCount 要生成多少页，-1表示生成所有
	 * @param pageSize 每页记录数
	 * @param detailFlag 是否生成详细页
	 */
	public static void generateCatalog(long catalogID, int pageCount, boolean detailFlag) throws Exception {
		ZCCatalogSchema catalog = CatalogUtil.getSchema(catalogID);
		TemplateContext context = new TemplateContext();
		context.setSSIContext(true);
		context.addDataVariable("site", SiteUtil.getSchema(catalog.getSiteID()).toDataRow());
		context.addDataVariable("catalog", catalog.toDataRow());
		context.addDataVariable("level", "../");
		context.addDataVariable("system", ParserCache.getConfig(catalog.getSiteID()));

		// 生成栏目首页
		if (StringUtil.isNotEmpty(catalog.getIndexTemplate())) {
			String fileName = CatalogUtil.getAbsolutePath(catalogID) + "index.shtml";
			writeFile(catalog.getIndexTemplate(), fileName, context);
		}
		if (pageCount < 0) {
			pageCount = 1000000;
		}

		// 生成相应区块

		int pageSize = 0;
		int total = 0;
		// 生成列表页
		if (StringUtil.isNotEmpty(catalog.getListTemplate()) && pageCount != 0) {
			TemplateBase template = TemplateManager.find(SiteUtil.getAbsolutePath(catalog.getSiteID())
					+ catalog.getListTemplate());
			String fileName = CatalogUtil.getAbsolutePath(catalogID) + "list.shtml";
			if (StringUtil.isEmpty(catalog.getIndexTemplate())) {
				fileName = CatalogUtil.getAbsolutePath(catalogID) + "index.shtml";
			}
			writeFile(template, fileName, context);
			pageSize = template.getPageSize();
			total = template.getPageTotal();
			for (int i = 1; i < pageCount; i++) {
				template.setPageIndex(i);
				if (template.hasNextPage()) {
					if (StringUtil.isEmpty(catalog.getIndexTemplate())) {
						fileName = CatalogUtil.getAbsolutePath(catalogID) + "index_" + (i + 1) + ".shtml";
					} else {
						fileName = CatalogUtil.getAbsolutePath(catalogID) + "list_" + (i + 1) + ".shtml";
					}
					writeFile(template, fileName, context);
				} else {
					break;
				}
			}
		}

		// 生成栏目下详细页
		if (StringUtil.isNotEmpty(catalog.getDetailTemplate()) && detailFlag && pageCount != 0) {
			for (int i = 0; i < pageCount && i * pageSize < total; i++) {
				DataTable dt = new QueryBuilder("select * from ZCArticle where CatalogID=?", catalogID)
						.executeDataTable();
				if (dt.getRowCount() > 0) {
					for (int j = 0; j < dt.getRowCount(); j++) {
						DataRow dr = dt.getDataRow(j);
						generateArticle(dr,
								SiteUtil.getAbsolutePath(catalog.getSiteID()) + catalog.getDetailTemplate(), context);
					}
				}
			}
		}
	}

	public static void generateArticle(DataRow dr, String templateFile, TemplateContext context) throws Exception {
		String fileName = CatalogUtil.getAbsolutePath(dr.getLong("CatalogID")) + dr.get("ID") + ".shtml";
		context.addDataVariable("article", dr);
		writeFile(templateFile, fileName, context);
	}

	public static void generateBlock(DataRow dr, String templateFile, TemplateContext context) throws Exception {
		String fileName = CatalogUtil.getAbsolutePath(dr.getLong("CatalogID")) + dr.get("ID") + ".shtml";
		context.addDataVariable("article", dr);
		writeFile(templateFile, fileName, context);
	}

	public static void writeFile(String templateFile, String destFile, TemplateContext context) throws Exception {
		TemplateBase template = TemplateManager.find(templateFile);
		writeFile(template, destFile, context);
	}

	public static void writeFile(TemplateBase template, String destFile, TemplateContext context) throws Exception {
		long t = System.currentTimeMillis();
		template.setDestination(destFile);
		template.setContext(context);
		template.execute();
		long cost = System.currentTimeMillis() - t;
		String comment = "\n<!-- Published by NSWT(新宇联安信息系统统一平台) " + Config.getMainVersion() + "."
				+ Config.getMinorVersion() + " 耗时" + cost + "毫秒 PublishDate " + DateUtil.toDateTimeString(new Date())
				+ "-->";

		File f = new File(destFile.substring(0, destFile.lastIndexOf("/")));
		if (!f.exists()) {
			f.mkdirs();
		}
		FileUtil.writeText(destFile, template.getResult() + comment);
		LogUtil.info("生成文件" + destFile + "耗时" + cost + "毫秒");
	}

	public static void main(String[] args) {
		try {
			generateIndex(206);
			// generateCatalog(8678, -1, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
