package com.nswt.cms.template.generator;

import java.util.Date;

import com.nswt.cms.pub.CatalogUtil;
import com.nswt.cms.pub.SiteUtil;
import com.nswt.cms.template.ParserCache;
import com.nswt.framework.data.DataRow;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.utility.DateUtil;
import com.nswt.framework.utility.FileUtil;
import com.nswt.framework.utility.StringUtil;
import com.nswt.schema.ZCCatalogSchema;
import com.nswt.schema.ZCPageBlockSchema;
import com.nswt.schema.ZCPageBlockSet;
import com.nswt.statical.template.TemplateBase;
import com.nswt.statical.template.TemplateContext;
import com.nswt.statical.template.TemplateManager;

/*
 * @Author ������
 * @Date 2016-8-18
 * @Mail nswt@nswt.com.cn
 */
public class CatalogGenerator {
	/**
	 * ������Ŀ��ҳ�棬������Ŀ��ҳ���б�ҳ����ϸҳ
	 * 
	 * @param pageCount Ҫ���ɶ���ҳ��-1��ʾ��������
	 * @param pageSize ÿҳ��¼��
	 * @param detailFlag �Ƿ�������ϸҳ
	 */
	public static void generate(long catalogID, int pageCount, boolean childFlag, boolean detailFlag) throws Exception {
		ZCCatalogSchema catalog = CatalogUtil.getSchema(catalogID);
		// ��������ӵ�������Ŀ��������
		if (catalog.getURL().indexOf("://") > 0) {
			return;
		}
		TemplateContext context = new TemplateContext();
		context.setSSIContext(true);
		context.addDataVariable("site", SiteUtil.getSchema(catalog.getSiteID()).toDataRow());
		context.addDataVariable("catalog", catalog.toDataRow());
		context.addDataVariable("system", ParserCache.getConfig(catalog.getSiteID()));

		String sitePath = SiteUtil.getAbsolutePath(catalog.getSiteID());
		String prefix = getCatalogPathPrefix(catalog);
		context.addDataVariable("level", getCatalogPathLevel(catalog));

		// �����������
		ZCPageBlockSet set = new ZCPageBlockSchema().query(new QueryBuilder("where type<>'4' and CatalogID=?",
				catalogID));
		for (int i = 0; i < set.size(); i++) {
			PageblockGenerator.generate(set.get(i), context);
		}

		// ��ҳ��Ŀ
		if ("Y".equalsIgnoreCase(catalog.getSingleFlag())) {
			DataTable dt = new QueryBuilder(
					"select * from ZCArticle where CatalogID=? order by orderflag desc,id desc", catalogID)
					.executePagedDataTable(1, 0);
			if (dt.getRowCount() > 0) {
				ArticleGenerator.generate(dt.getDataRow(0), sitePath + catalog.getDetailTemplate(), prefix
						+ "index.shtml", context);
			}
			return;
		}

		// ������Ŀ��ҳ
		if (StringUtil.isNotEmpty(catalog.getIndexTemplate())) {
			String fileName = prefix + "index.shtml";
			GenerateUtil.execute(sitePath + catalog.getIndexTemplate(), fileName, context, "Catalog");
		}
		if (pageCount < 0) {
			pageCount = 1000000;
		}

		int pageSize = 0;
		int total = 0;
		// �����б�ҳ
		if (StringUtil.isNotEmpty(catalog.getListTemplate()) && pageCount != 0) {
			TemplateBase template = TemplateManager.find(sitePath + catalog.getListTemplate());
			String fileName = prefix + "list.shtml";
			if (StringUtil.isEmpty(catalog.getIndexTemplate())) {
				fileName = prefix + "index.shtml";
			}
			GenerateUtil.execute(template, fileName, context, "Catalog");
			total = template.getPageTotal();
			pageSize = template.getPageSize();
			for (int i = 1; i < pageCount; i++) {
				template.setPageIndex(i);
				if (template.hasNextPage()) {
					if (StringUtil.isEmpty(catalog.getIndexTemplate())) {
						fileName = prefix + "index_" + (i + 1) + ".shtml";
					} else {
						fileName = prefix + "list_" + (i + 1) + ".shtml";
					}
					GenerateUtil.execute(template, fileName, context, "Catalog");
				} else {
					break;
				}
			}
		}

		// ������Ŀ����ϸҳ
		if (StringUtil.isNotEmpty(catalog.getDetailTemplate()) && detailFlag && pageCount != 0) {
			context.addDataVariable("level", getArticlePathLevel(catalog));
			for (int i = 0; i < pageCount && i * pageSize < total; i++) {
				DataTable dt = new QueryBuilder(
						"select * from ZCArticle where CatalogID=? order by OrderFlag desc,ID desc", catalogID)
						.executePagedDataTable(pageSize, i);
				ArticleGenerator.extendArticleData(dt);
				if (dt.getRowCount() > 0) {
					for (int j = 0; j < dt.getRowCount(); j++) {
						DataRow dr = dt.getDataRow(j);
						String fileName = getArticlePath(catalog, dr);
						ArticleGenerator.generate(dr, sitePath + catalog.getDetailTemplate(), fileName, context);
					}
				}
			}
		}

		// ��������Ŀ
		if (childFlag && catalog.getChildCount() > 0) {
			DataTable dt = new QueryBuilder("select ID from ZCCatalog where ID<>? and InnerCode like ?", catalogID,
					catalog.getInnerCode() + "%").executeDataTable();
			for (int i = 0; i < dt.getRowCount(); i++) {
				generate(dt.getLong(i, "ID"), pageCount, false, detailFlag);
			}
		}
	}

	/**
	 * ������Ŀ·��ǰ׺
	 */
	public static String getCatalogPathPrefix(ZCCatalogSchema catalog) {
		return CatalogUtil.getAbsolutePath(catalog.getID());
	}

	/**
	 * ������Ŀ·�����
	 */
	public static String getCatalogPathLevel(ZCCatalogSchema catalog) {
		int level = CatalogUtil.getLevel(catalog.getID());
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < level; i++) {
			sb.append("../");
		}
		return sb.toString();
	}

	/**
	 * ��������·��
	 */
	public static String getArticlePath(ZCCatalogSchema catalog, DataRow article) {
		TemplateContext context = new TemplateContext();
		context.addDataVariable("Document", article);
		context.addDataVariable("CatalogPath", CatalogUtil.getAbsolutePath(catalog.getID()));
		context.addDataVariable("Catalog", catalog.toDataRow());
		context.addDataVariable("Site", SiteUtil.getSchema(catalog.getSiteID()));

		Date date = article.getDate("PublishDate");
		if (date == null) {
			date = new Date();
		}
		context.addDataVariable("Year", DateUtil.toString(date, "yyyy"));
		context.addDataVariable("Month", DateUtil.toString(date, "MM"));
		context.addDataVariable("Day", DateUtil.toString(date, "dd"));
		context.addDataVariable("Timestamp", "" + date.getTime());
		String file = context.replaceHolder(catalog.getDetailNameRule());
		file = FileUtil.normalizePath(file);
		return file;
	}

	/**
	 * ��������·�����
	 */
	public static String getArticlePathLevel(ZCCatalogSchema catalog) {
		int level = CatalogUtil.getDetailLevel(catalog.getID());
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < level; i++) {
			sb.append("../");
		}
		return sb.toString();
	}
}
