package com.nswt.cms.template.generator;

import com.nswt.cms.pub.CatalogUtil;
import com.nswt.cms.pub.SiteUtil;
import com.nswt.cms.template.ParserCache;
import com.nswt.framework.utility.FileUtil;
import com.nswt.schema.ZCPageBlockSchema;
import com.nswt.statical.template.TemplateContext;

/*
 * @Author 王育春
 * @Date 2016-8-18
 * @Mail nswt@nswt.com.cn
 */
public class PageblockGenerator {
	public static void generate(long blockID) throws Exception {
		ZCPageBlockSchema block = new ZCPageBlockSchema();
		block.setID(blockID);
		block.fill();
		generate(block);
	}

	public static void generate(ZCPageBlockSchema block) throws Exception {
		if (block.getType() == 4) {// 1.x自动生成的区块
			return;
		}
		TemplateContext context = new TemplateContext();
		context.setSSIContext(true);
		context.addDataVariable("site", SiteUtil.getSchema(block.getSiteID()).toDataRow());
		if (block.getCatalogID() == 0) {
			context.addDataVariable("level", "");
		} else {
			context.addDataVariable("catalog", CatalogUtil.getSchema(block.getCatalogID()));
			context.addDataVariable("level", "../");
		}
		context.addDataVariable("system", ParserCache.getConfig(block.getSiteID()));
	}

	public static void generate(ZCPageBlockSchema block, TemplateContext context) throws Exception {
		if (block.getType() == 4) {// 1.x自动生成的区块
			return;
		}
		String fileName = SiteUtil.getAbsolutePath(block.getSiteID()) + "/" + block.getFileName();
		fileName = FileUtil.normalizePath(fileName);
		if (block.getType() == 1) {
			String template = SiteUtil.getAbsolutePath(block.getSiteID()) + "/" + block.getTemplate();
			template = FileUtil.normalizePath(template);
			GenerateUtil.execute(template, fileName, context, "Not");
		} else {
			FileUtil.writeText(fileName, block.getContent());
		}
	}

}
