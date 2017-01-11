package com.nswt.cms.template.generator;

import com.nswt.cms.document.Article;
import com.nswt.cms.pub.CatalogUtil;
import com.nswt.cms.pub.PubFun;
import com.nswt.cms.pub.SiteUtil;
import com.nswt.cms.resource.ConfigImageLib;
import com.nswt.cms.site.Keyword;
import com.nswt.cms.template.ParserCache;
import com.nswt.framework.Config;
import com.nswt.framework.Constant;
import com.nswt.framework.data.DataRow;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.schema.ZCCatalogSchema;
import com.nswt.schema.ZCKeywordSchema;
import com.nswt.schema.ZCKeywordSet;
import com.nswt.statical.TemplateData;
import com.nswt.statical.exception.TemplateRuntimeException;
import com.nswt.statical.template.TemplateBase;
import com.nswt.statical.template.TemplateContext;
import com.nswt.statical.template.TemplateManager;

/*
 * @Author 王育春
 * @Date 2016-8-18
 * @Mail nswt@nswt.com.cn
 */
public class ArticleGenerator {
	public static void generate(long articleID) throws Exception {
		DataTable dt = new QueryBuilder("select * from ZCArticle where ID=?", articleID).executeDataTable();
		if (dt.getRowCount() > 0) {
			generate(dt.getDataRow(0));
		} else {
			throw new TemplateRuntimeException("没有ID为" + articleID + "的文章!");
		}
	}

	public static void generate(DataRow dr) throws Exception {
		TemplateContext context = new TemplateContext();
		context.setSSIContext(true);
		ZCCatalogSchema catalog = CatalogUtil.getSchema(dr.getLong("CatalogID"));
		context.addDataVariable("site", SiteUtil.getSchema(dr.getLong("SiteID")).toDataRow());
		context.addDataVariable("catalog", catalog.toDataRow());
		context.addDataVariable("system", ParserCache.getConfig(dr.getLong("SiteID")));
		context.addDataVariable("level", CatalogGenerator.getArticlePathLevel(catalog));
		String fileName = CatalogGenerator.getArticlePath(catalog, dr);
		generate(dr, SiteUtil.getAbsolutePath(catalog.getSiteID()) + catalog.getDetailTemplate(), fileName, context);
	}

	public static void generate(DataRow dr, String templateFile, String fileName, TemplateContext context)
			throws Exception {
		context.addDataVariable("article", dr);
		extendArticleData(dr);
		if ("Y".equals(dr.getString("TemplateFlag"))) {// 文章单独模板
			String template = dr.getString("Template");
			if (StringUtil.isNotEmpty(template)) {
				templateFile = SiteUtil.getAbsolutePath(dr.getLong("SiteID")) + template;
			}
		}
		ZCCatalogSchema catalog = CatalogUtil.getSchema(dr.getLong("CatalogID"));

		// 替换图片、附件路径
		String content = dr.getString("Content");
		long siteID = dr.getLong("SiteID");

		String imagePath = Config.getContextPath() + Config.getValue("UploadDir") + "/" + SiteUtil.getAlias(siteID)
				+ "/";
		imagePath = imagePath.replaceAll("/+", "/");

		String attachPath = Config.getContextPath() + "/Services/AttachDownLoad.jsp";
		attachPath = attachPath.replaceAll("/+", "/");

		String serviceContext = Config.getValue("ServicesContext");
		if (!serviceContext.endsWith("/")) {
			serviceContext = serviceContext + "/";
		}

		if (StringUtil.isNotEmpty(content)) {
			String siteurl = SiteUtil.getURL(siteID);
			if (StringUtil.isNotEmpty(siteurl) && !"http://".equalsIgnoreCase(siteurl)) {
				content = content.replaceAll(siteurl, StringUtil.md5Hex(siteurl));
			}
			String prefix = context.eval("Level");
			// 如果有分离部署
			if ("Y".equals(ConfigImageLib.getImageLibConfig(siteID).getString("ImageSeparateFlag"))) {
				prefix = ConfigImageLib.getImageLibConfig(siteID).getString("ImageSeparateURLPrefix");
			}
			content = content.replaceAll(imagePath, prefix);
			content = content.replaceAll(attachPath, serviceContext + "AttachDownLoad.jsp");

			if (StringUtil.isNotEmpty(siteurl) && !"http://".equalsIgnoreCase(siteurl)) {
				content = content.replaceAll(StringUtil.md5Hex(siteurl), siteurl);
			}
		}

		// 点击量
		if (StringUtil.isNotEmpty(dr.getString("HitCount"))) {
			dr.set("HitCount", getClickScript(dr.getString("ID")));
		}

		// 如果设置了关键词替换功能
		String cid = dr.getString("CatalogID");
		String keyWordType = CatalogUtil.getHotWordType(cid);

		if (StringUtil.isNotEmpty(keyWordType) && !"0".equalsIgnoreCase(keyWordType)) {
			ZCKeywordSet keywordSet = (ZCKeywordSet) Keyword.getKeyWordSet(keyWordType);
			if (keywordSet != null && keywordSet.size() > 0) {
				String searchUrl = getSearchURL();
				Mapx keyWordCache = new Mapx();
				for (int i = 0; i < keywordSet.size(); i++) {
					ZCKeywordSchema keyword = keywordSet.get(i);
					String word = keyword.getKeyword();
					String url = keyword.getLinkUrl();
					if (StringUtil.isEmpty(url)) {
						url = searchUrl + "?site=" + dr.getString("SiteID") + "&query=" + word;
					}
					String target = keyword.getLinkTarget();
					String alt = keyword.getLinkAlt();
					if (StringUtil.isEmpty(alt)) {
						alt = word;
					}
					String text = "<a href='" + url + "' target='" + target + "' title='" + alt + "'>" + word + "</a>";
					String md5data = StringUtil.md5Hex(i + "");
					keyWordCache.put(md5data, text);
					content = content.replaceAll(word, md5data);
				}

				DataTable dtCache = keyWordCache.toDataTable();
				for (int i = 0; i < keywordSet.size(); i++) {
					content = content.replaceAll(dtCache.getString(i, 0), dtCache.getString(i, 1));
				}
			}
		}
		dr.insertColumn("FullContent", content);

		// 文章分页
		String[] pages = content.split(Constant.PAGE_BREAK, -1);
		if (pages.length > 0) {
			TemplateBase template = TemplateManager.find(templateFile);
			GenerateUtil.execute(template, fileName, context, "Article");
			template.setPageTotal(pages.length);
			template.setPageSize(1);
			String firstFileName = CatalogGenerator.getArticlePath(catalog, dr);
			for (int i = 0; i < pages.length; i++) {
				template.setPageIndex(i);
				dr.set("Content", pages[i]);
				context.addDataVariable("Article", dr);
				addPageBreakBar(template, context);
				fileName = firstFileName;
				if (i != 0) {
					fileName = fileName.substring(0, fileName.lastIndexOf(".")) + "_" + (i + 1)
							+ fileName.substring(fileName.lastIndexOf("."));
				}
				GenerateUtil.execute(templateFile, fileName, context, "Article");
			}
		} else {
			GenerateUtil.execute(templateFile, fileName, context, "Article");
		}
	}

	public static void addPageBreakBar(TemplateBase template, TemplateContext context) {
		String dest = template.getDestination();
		if (dest.indexOf("_" + (template.getPageIndex() + 1) + ".") > 0) {
			dest = StringUtil.replaceEx(dest, "_" + (template.getPageIndex() + 1) + ".", ".");
		}
		String firstName = dest.substring(dest.lastIndexOf("/") + 1);
		String otherName = firstName.substring(0, firstName.lastIndexOf(".")) + "_@INDEX"
				+ firstName.substring(firstName.lastIndexOf("."));
		TemplateData td = new TemplateData();
		td.setPageCount(new Double(Math.ceil(template.getPageTotal() * 1.0 / template.getPageSize())).intValue());
		td.setPageSize(template.getPageSize());
		td.setPageIndex(template.getPageIndex());
		td.setTotal(template.getPageTotal());
		td.setFirstFileName(firstName);
		td.setOtherFileName(otherName);
		String html = td.getPageBreakBar(1);
		Mapx map = (Mapx) context.getTree().getRoot().getData();
		map.put("PageBreakBar", html);
	}

	public static void extendArticleData(DataRow dr) {
		dr.insertColumn("PrevLink", "#");
		dr.insertColumn("PrevTitle", "没有文章");
		dr.insertColumn("NextLink", "#");
		dr.insertColumn("NextTitle", "没有文章");
		dr.insertColumn("FirstImagePath", null);
		dr.insertColumn("FirstVideoImage", null);
		dr.insertColumn("FirstVideoHtml", null);

		PubFun.dealArticleMedia(dr);
		dr.insertColumn("BranchName", PubFun.getBranchName(dr.getString("BranchInnerCode")));

		DataTable prevDT = new QueryBuilder(
				"select * from zcarticle where catalogid=? and orderflag >? and status in (" + Article.STATUS_TOPUBLISH
						+ "," + Article.STATUS_PUBLISHED + ") order by orderflag asc", dr.getLong("CatalogID"),
				dr.getLong("OrderFlag")).executePagedDataTable(1, 0);
		if (prevDT.getRowCount() == 1) {
			dr.set("PrevLink", prevDT.getString(0, "ID") + ".shtml");
			dr.set("PrevTitle", prevDT.getString(0, "Title"));
		}
		DataTable nextDT = new QueryBuilder(
				"select * from zcarticle where catalogid=? and orderflag <? and status in (" + Article.STATUS_TOPUBLISH
						+ "," + Article.STATUS_PUBLISHED + ") order by orderflag desc", dr.getLong("CatalogID"),
				dr.getLong("OrderFlag")).executePagedDataTable(1, 0);
		if (nextDT.getRowCount() == 1) {
			dr.set("NextLink", nextDT.getString(0, "ID") + ".shtml");
			dr.set("NextTitle", nextDT.getString(0, "Title"));
		}
	}

	public static void extendArticleData(DataTable dt) {
		dt.insertColumn("PrevLink");
		dt.insertColumn("PrevTitle");
		dt.insertColumn("NextLink");
		dt.insertColumn("NextTitle");
		dt.insertColumn("FirstImagePath");
		dt.insertColumn("FirstVideoImage");
		dt.insertColumn("FirstVideoHtml");
		dt.insertColumn("BranchName");

		PubFun.dealArticleMedia(dt);
		for (int i = 0; i < dt.getRowCount(); i++) {
			DataRow dr = dt.getDataRow(i);
			dr.set("BranchName", PubFun.getBranchName(dr.getString("BranchInnerCode")));
			if (i == 0) {
				DataTable prevDT = new QueryBuilder(
						"select * from zcarticle where catalogid=? and orderflag >? and status in ("
								+ Article.STATUS_TOPUBLISH + "," + Article.STATUS_PUBLISHED
								+ ") order by orderflag asc", dr.getLong("CatalogID"), dr.getLong("OrderFlag"))
						.executePagedDataTable(1, 0);
				if (prevDT.getRowCount() == 1) {
					dr.set("PrevLink", prevDT.getString(0, "ID") + ".shtml");
					dr.set("PrevTitle", prevDT.getString(0, "Title"));
				} else {
					dr.set("PrevLink", "#");
					dr.set("PrevTitle", "没有文章");
				}
			} else {
				dr.set("PrevLink", dt.getString(i - 1, "ID") + ".shtml");
				dr.set("PrevTitle", dt.getString(i - 1, "Title"));
			}
			if (i == dt.getRowCount() - 1) {
				DataTable nextDT = new QueryBuilder(
						"select * from zcarticle where catalogid=? and orderflag <? and status in ("
								+ Article.STATUS_TOPUBLISH + "," + Article.STATUS_PUBLISHED
								+ ") order by orderflag desc", dr.getLong("CatalogID"), dr.getLong("OrderFlag"))
						.executePagedDataTable(1, 0);
				if (nextDT.getRowCount() == 1) {
					dr.set("NextLink", nextDT.getString(0, "ID") + ".shtml");
					dr.set("NextTitle", nextDT.getString(0, "Title"));
				} else {
					dr.set("NextLink", "#");
					dr.set("NextTitle", "没有文章");
				}
			} else {
				dr.set("NextLink", dt.getString(i + 1, "ID") + ".shtml");
				dr.set("NextTitle", dt.getString(i + 1, "Title"));
			}
		}
	}

	public static String getClickScript(String articleID) {
		String serviceUrl = Config.getValue("ServicesContext");
		String clickScript = "\n<script src=\"" + serviceUrl + "/Counter.jsp?Type=Article&ID=" + articleID
				+ "\" type=\"text/javascript\"></script>\n";
		return clickScript;
	}

	public static String getSearchURL() {
		String serviceUrl = Config.getValue("ServicesContext");
		String context = serviceUrl;
		if (serviceUrl.endsWith("/")) {
			context = serviceUrl.substring(0, serviceUrl.length() - 1);
		}
		int index = context.lastIndexOf('/');
		if (index != -1) {
			context = context.substring(0, index);
		}
		String searchUrl = context + "/Search/Result.jsp";// 搜索路径
		return searchUrl;
	}

}
