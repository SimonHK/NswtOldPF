package com.nswt.cms.template.generator;

import com.nswt.cms.pub.SiteUtil;
import com.nswt.cms.template.ParserCache;
import com.nswt.framework.Config;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.schema.ZCPageBlockSchema;
import com.nswt.schema.ZCPageBlockSet;
import com.nswt.schema.ZCSiteSchema;
import com.nswt.statical.template.TemplateBase;
import com.nswt.statical.template.TemplateContext;
import com.nswt.statical.template.TemplateManager;

/*
 * @Author 王育春
 * @Date 2016-8-18
 * @Mail nswt@nswt.com.cn
 */
public class SiteGenerator {
	public static void generate(long siteID) throws Exception {
		generateIndex(siteID);
		DataTable dt = new QueryBuilder("select ID from ZCCatalog where SiteID=?", siteID).executeDataTable();
		for (int i = 0; i < dt.getRowCount(); i++) {
			CatalogGenerator.generate(dt.getLong(i, "ID"), -1, false, true);
		}
	}

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

		// 生成相关区块
		ZCPageBlockSet set = new ZCPageBlockSchema().query(new QueryBuilder(
				"where SiteID=? and type<>'4' and CatalogID is null", siteID));
		for (int i = 0; i < set.size(); i++) {
			PageblockGenerator.generate(set.get(i), context);
		}

		String fileName = SiteUtil.getAbsolutePath(site.getID()) + "index.shtml";
		GenerateUtil.execute(SiteUtil.getAbsolutePath(site.getID()) + site.getIndexTemplate(), fileName, context,
				"Site");
	}

	/**
	 * 生成站点首页
	 */
	public static String editIndex(long siteID) throws Exception {
		ZCSiteSchema site = SiteUtil.getSchema(siteID);
		TemplateContext context = new TemplateContext();
		context.setSSIContext(false);
		context.addDataVariable("site", site.toDataRow());
		context.addDataVariable("level", "../wwwroot/" + site.getAlias() + "/");
		context.addDataVariable("system", ParserCache.getConfig(site.getID()));
		context.setEditTime(true);

		String fileName = Config.getContextRealPath() + "Services";
		TemplateBase template = TemplateManager.find(SiteUtil.getAbsolutePath(site.getID()) + site.getIndexTemplate());
		template.setDestination(fileName);
		template.setContext(context);
		template.execute();
		return template.getResult();
	}

	public static void main(String[] args) {
		long t = System.currentTimeMillis();
		try {
			for (int i = 0; i < 1; i++) {
				// SiteGenerator.generate(206);
				// CatalogGenerator.generate(8708, -1, true, true);
				ArticleGenerator.generate(224805);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(System.currentTimeMillis() - t);

	}

}
