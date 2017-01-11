package com.nswt.cms.template;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.nswt.Product;
import com.nswt.cms.dataservice.ColumnUtil;
import com.nswt.cms.document.Article;
import com.nswt.cms.pub.CatalogUtil;
import com.nswt.cms.pub.PubFun;
import com.nswt.cms.pub.SiteUtil;
import com.nswt.cms.resource.ConfigImageLib;
import com.nswt.cms.site.Catalog;
import com.nswt.cms.site.Keyword;
import com.nswt.framework.Config;
import com.nswt.framework.Constant;
import com.nswt.framework.data.DataRow;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.messages.LongTimeTask;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.script.EvalException;
import com.nswt.framework.utility.Big5Convert;
import com.nswt.framework.utility.DateUtil;
import com.nswt.framework.utility.Errorx;
import com.nswt.framework.utility.FileUtil;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.ServletUtil;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.pub.NoUtil;
import com.nswt.schema.ZCArticleSchema;
import com.nswt.schema.ZCCatalogSchema;
import com.nswt.schema.ZCCatalogSet;
import com.nswt.schema.ZCKeywordSchema;
import com.nswt.schema.ZCKeywordSet;
import com.nswt.schema.ZCPageBlockSchema;
import com.nswt.schema.ZCPageBlockSet;
import com.nswt.schema.ZCSiteSchema;
import com.nswt.statical.TemplateException;
import com.nswt.statical.TemplateParser;

/**
 * ��̬��ҳ��
 * 
 * @Author ����
 * @Date 2007-9-10
 * @Mail lanjun@nswt.com
 */
public class PageGenerator {
	private String templateDir;

	private String staticDir;

	private ArrayList fileList = new ArrayList();

	private Product product = new Product();

	private LongTimeTask task;

	public PageGenerator() {
		this(null);
	}

	public PageGenerator(LongTimeTask ltt) {
		String contextRealPath = Config.getContextRealPath();
		templateDir = contextRealPath + Config.getValue("Statical.TemplateDir").replace('\\', '/');
		staticDir = contextRealPath + Config.getValue("Statical.TargetDir").replace('\\', '/');
		staticDir = staticDir.replaceAll("/+", "/");

		task = ltt;
		if (ltt == null) {
			task = LongTimeTask.createEmptyInstance();
		}
	}

	public boolean staticCatalog(ZCCatalogSchema catalog, boolean isGenerateDetail) {
		return staticCatalog(catalog, isGenerateDetail, 0);
	}

	/**
	 * ������Ŀ��ҳ���б�ҳ�����¾�̬ҳ�� ��������Ŀ¼
	 * 
	 * @param className
	 * @param catalogID
	 * @param articleClass
	 */
	public boolean staticCatalog(ZCCatalogSchema catalog, boolean isGenerateDetail, int publishPages) {
		TemplateCache.clear();
		long t = System.currentTimeMillis();
		String message = "";

		int level = CatalogUtil.getLevel(catalog.getID());
		boolean flag = true; // �ɹ����

		ZCSiteSchema site = SiteUtil.getSchema(catalog.getSiteID());

		task.setCurrentInfo("���ڷ�����Ŀ\"" + catalog.getName() + "\"");

		// ������Ŀ�������
		staticPageBlock(site, catalog, 0);

		// �����Ŀ���ӵ��ⲿ��ַ������Ŀ������
		String catalogURL = catalog.getURL();
		if (StringUtil.isNotEmpty(catalogURL)
				&& (catalogURL.startsWith("http://") || catalogURL.startsWith("https://"))) {
			staticInnerPageBlock(site, catalog);
			staticPageBlock(site, catalog, 4);
			return true;
		}

		String siteAlias = site.getAlias();
		String rootPath = staticDir + "/" + siteAlias + "/";
		rootPath = rootPath.replaceAll("/+", "/");

		// ��̬��ģ��������
		CmsTemplateData catalogTemplateData = new CmsTemplateData();
		catalogTemplateData.setLevel(level);
		catalogTemplateData.setSite(site);
		catalogTemplateData.setCatalog(catalog);

		// ������Ŀ��ҳ
		String indexTemplate = catalog.getIndexTemplate();
		if (StringUtil.isNotEmpty(indexTemplate) && !"Y".equals(catalog.getSingleFlag())) {
			indexTemplate = templateDir + "/" + siteAlias + "/" + indexTemplate;
			indexTemplate = indexTemplate.replaceAll("/+", "/");
			String path = rootPath + CatalogUtil.getPath(catalog.getID());
			TemplateParser tp = getParser(site.getID(), indexTemplate, level);
			if (tp == null) {
				message = "��Ŀ��" + catalog.getName() + "����ҳģ��" + catalog.getIndexTemplate() + "������";
				LogUtil.info(message);
				task.addError(message);
				return false;
			}
			tp.define("site", catalogTemplateData.getSite());
			tp.define("TemplateData", catalogTemplateData);
			tp.define("catalog", catalogTemplateData.getCatalog());

			String ext = SiteUtil.getExtensionType(catalog.getSiteID());
			String fileName = "index." + ext;
			String statScript = "";
			if ("Y".equals(site.getAutoStatFlag())) {
				// ͳ�ƴ���
				String serviceUrl = Config.getValue("ServicesContext");
				String statUrl = "SiteID=" + site.getID() + "&Type=Article&CatalogInnerCode=" + catalog.getInnerCode()
						+ "&Dest=" + serviceUrl + "/Stat.jsp";
				statScript = getStatScript(statUrl);
			}

			statScript += getVersionInfo();
			fileName = path + "/" + fileName;
			if (!writeHTML(tp, fileName, statScript)) {
				return false;
			}
		}

		// ������ĿRss
		String rssTemplate = catalog.getRssTemplate();
		if (StringUtil.isNotEmpty(rssTemplate)) {
			rssTemplate = templateDir + "/" + siteAlias + "/" + rssTemplate;
			rssTemplate = rssTemplate.replaceAll("/+", "/");
			String path = rootPath + CatalogUtil.getPath(catalog.getID());
			TemplateParser tp = getParser(site.getID(), rssTemplate, level);
			if (tp != null) {
				tp.define("site", catalogTemplateData.getSite());
				tp.define("TemplateData", catalogTemplateData);
				tp.define("catalog", catalogTemplateData.getCatalog());
				task.setCurrentInfo("����������Ŀ\"" + catalog.getName() + "\" Rss�ļ�");
				if (!writeHTML(tp, path + "/rss.xml", "")) {
					return false;
				}
			} else {
				message = "��Ŀ��" + catalog.getName() + "��Rssģ��" + catalog.getRssTemplate() + "������";
				LogUtil.info(message);
				task.addError(message);
				return false;
			}
		}

		TemplateParser listParser = null;
		if (!"Y".equals(catalog.getSingleFlag())) {
			String listTemplate = catalog.getListTemplate();
			// ֻ������ҳ �����б�ҳģ��Ϊ��
			if (StringUtil.isNotEmpty(indexTemplate) && StringUtil.isEmpty(listTemplate)) {
				// ���û����ϸҳ���б�ҳ����Ȼ��Ҫ������Ӧ��ϵͳ��������
				staticInnerPageBlock(site, catalog);
				staticPageBlock(site, catalog, 4);
				return true;
			}

			if (StringUtil.isEmpty(listTemplate)) {
				message = "��Ŀ��" + catalog.getName() + "���б�ҳģ��" + catalog.getListTemplate() + "������";
				task.addError(message);
				return true;
			}

			// ���������б�ҳ��ģ��
			listTemplate = templateDir + "/" + siteAlias + "/" + listTemplate;
			listTemplate = listTemplate.replaceAll("//", "/");
			listParser = getParser(site.getID(), listTemplate, level);
			if (listParser == null) {
				message = "��Ŀ��" + catalog.getName() + "���б�ҳģ��" + catalog.getListTemplate() + "������";
				task.addError(message);
				return true;
			}
		}

		// ��ϸҳģ�壬������ϸҳģ��Ϊ��
		if (StringUtil.isEmpty(catalog.getDetailTemplate())) {
			if (catalog.getType() != Catalog.CATALOG) {// ý�����Ŀֱ�Ӻ���
				return true;
			}
			if (!"Y".equals(catalog.getSingleFlag()) && StringUtil.isEmpty(catalog.getIndexTemplate())) {
				message = "��Ŀ��" + catalog.getName() + "����ϸҳģ��" + catalog.getDetailTemplate() + "������";
				task.addError(message);
			}
		}

		// �б�ҳ��������
		String listNameRule = null;
		if (StringUtil.isEmpty(indexTemplate)) {
			String ext = ServletUtil.getUrlExtension(catalog.getListTemplate());
			String extSite = SiteUtil.getExtensionType(catalog.getSiteID());
			if (ext.equals(".jsp") || "jsp".equals(extSite)) {
				listNameRule = "index.jsp";
			} else {
				listNameRule = "index.shtml";
			}
		} else {
			String ext = ServletUtil.getUrlExtension(indexTemplate);
			String extSite = SiteUtil.getExtensionType(catalog.getSiteID());
			if (ext.equals(".jsp") || "jsp".equals(extSite)) {
				listNameRule = "list.jsp";
			} else {
				listNameRule = "list.shtml";
			}
		}

		String catalogPath = rootPath + CatalogUtil.getPath(catalog.getID());

		// �����б�ҳ
		// ��ҳ��Ŀ��ֻ������Ŀ��ϸҳ����
		if ("Y".equals(catalog.getSingleFlag())) {
			flag = staticListPages(site, catalog, catalogPath + listNameRule, listParser, catalogTemplateData,
					isGenerateDetail, publishPages);
		} else {
			// ����б�ҳû�з�ҳ��ǩ����Ȼ��Ҫ������ϸҳ��ģ��һ����ҳ��ǩ
			if (listParser.getPageListPrams().size() == 0) {
				long type = catalog.getType();
				String item = "article";
				if (type == Catalog.CATALOG || type == Catalog.MAGAZINE || type == Catalog.SUBJECT
						|| type == Catalog.NEWSPAPER) {
					item = "article";
				} else if (type == Catalog.IMAGELIB) {
					item = "image";
				} else if (type == Catalog.VIDEOLIB) {
					item = "video";
				} else if (type == Catalog.AUDIOLIB) {
					item = "audio";
				} else if (type == Catalog.ATTACHMENTLIB) {
					item = "attachment";
				} else if (type == Catalog.SHOP_GOODS || type == Catalog.SHOP_GOODS_BRAND) {
					item = "goods";
				}
				listParser.getPageListPrams().put("item", item);
				listParser.getPageListPrams().put("page", "true");
				listParser.getPageListPrams().put("pagesize", "20");
			}
			flag = staticListPages(site, catalog, catalogPath + listNameRule, listParser, catalogTemplateData,
					isGenerateDetail, publishPages);
		}

		// ������Ŀ��Ӧ��ϵͳ�����ļ���������Ŀģ���ҵ���������Ŀ�еĶ�Ӧ����
		staticInnerPageBlock(site, catalog);
		// ����ϵͳ���ɵ�����
		staticPageBlock(site, catalog, 4);

		LogUtil.info("������Ŀ" + catalog.getName() + "��ʱ" + (System.currentTimeMillis() - t));
		return flag;
	}

	/**
	 * ��̬������Ŀ���������� ���õݹ鷽����̬�����е�����Ŀ
	 * 
	 * @param parentID
	 * @param detail
	 * @param publishSize
	 * @return
	 */
	public boolean staticChildCatalog(long parentID, boolean detail, int publishSize) {
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		ZCCatalogSet catalogSet = catalog.query(new QueryBuilder("where parentid=?", parentID));
		for (int i = 0; i < catalogSet.size(); i++) {
			catalog = catalogSet.get(i);
			if (!staticCatalog(catalog, true, publishSize)) {
				return false;
			}
			if (!staticChildCatalog(catalogSet.get(i).getID(), detail, publishSize)) {
				return false;
			}
		}
		return true;
	}

	public boolean staticChildCatalog(long parentID, boolean detail) {
		return staticChildCatalog(parentID, detail, 0);
	}

	/**
	 * �����б�ҳ
	 * 
	 * @param site
	 * @param catalog
	 * @param fileName
	 * @param catalogParser
	 * @param templateData
	 * @param generateDetail
	 * @param publishPages
	 * @return
	 */
	private boolean staticListPages(ZCSiteSchema site, ZCCatalogSchema catalog, String fileName,
			TemplateParser catalogParser, CmsTemplateData templateData, boolean generateDetail, int publishPages) {
		Mapx listParams = new Mapx();

		// �Ƿ�Ϊ��ҳ��Ŀ
		boolean singlePageCatalog = catalog != null && "Y".equals(catalog.getSingleFlag());
		if (singlePageCatalog) {
			listParams.put("item", "article");
			listParams.put("page", "true");
			listParams.put("pagesize", "1");
		} else {
			listParams = catalogParser.getPageListPrams();
		}

		String item = (String) listParams.get("item");
		if (StringUtil.isEmpty(item)) {
			String message = "�б�ҳģ��" + catalogParser.getTemplate() + "��cms:list��ǩδ����item������";
			task.addError(message);
			return false;
		} else {
			item = item.toLowerCase();
		}
		String type = (String) listParams.get("type");
		String pagesizeStr = (String) listParams.get("pagesize");
		String condition = (String) listParams.get("condition");
		String displayLevel = (String) listParams.get("level");
		String hasAttribute = (String) listParams.get("hasattribute");
		String catalogName = (String) listParams.get("name");
		String catalogID = (String) listParams.get("id");
		if (StringUtil.isNotEmpty(catalogID)) {
			catalogName = catalogID;
		}
		if (StringUtil.isEmpty(catalogName)) {
			catalogName = catalog.getID() + "";
		}
		// �ж��Ƿ��ǵ�ǰ��Ŀ���������������ϸҳ������������ϸҳ
		boolean isCurrentCatalog = catalog != null
				&& (catalogName.equals(catalog.getID() + "") || catalogName.equals(catalog.getName()));

		TemplateParser detailParser = null;
		int detailLevel = templateData.getLevel();
		if ((generateDetail && isCurrentCatalog) || singlePageCatalog) {
			String detailTemplate = templateDir + "/" + site.getAlias() + "/" + catalog.getDetailTemplate();
			detailTemplate = detailTemplate.replaceAll("//", "/");
			if (StringUtil.isNotEmpty(catalog.getDetailNameRule())) {
				detailLevel = CatalogUtil.getDetailLevel(catalog.getID());
			}
			detailParser = getParser(site.getID(), detailTemplate, detailLevel);
		}
		CmsTemplateData detailTemplateData = new CmsTemplateData();
		detailTemplateData.setLevel(detailLevel);
		detailTemplateData.setSite(site);
		if (catalog != null) {
			detailTemplateData.setCatalog(catalog);
		}

		// ͳ�ƴ���
		String statScript = "";
		if ("Y".equals(site.getAutoStatFlag())) {
			if (catalog != null) {
				String serviceUrl = Config.getValue("ServicesContext");
				String statUrl = "SiteID=" + site.getID() + "&Type=Article&CatalogInnerCode=" + catalog.getInnerCode()
						+ "&Dest=" + serviceUrl + "/Stat.jsp";
				statScript = getStatScript(statUrl);
			}
		}

		int total = templateData.getPagedDocListTotal(item, catalogName, displayLevel, hasAttribute, type, condition);
		if (total < 1) {
			// �հ�ҳ
			if (singlePageCatalog && detailParser != null) {
				ZCArticleSchema row = new ZCArticleSchema();
				row.setPublishDate(new Date());
				row.setContent("����Ŀû��������£���������¡�");
				row.setSiteID(catalog.getSiteID());
				row.setCatalogID(catalog.getID());
				row.setCatalogInnerCode(catalog.getInnerCode());

				DataRow emptyRow = row.toDataRow();
				if (Config.isOracle()) {
					emptyRow.insertColumn("RNM", "1");
				}
				staticDoc(item, detailParser, detailTemplateData, emptyRow, fileName);
			} else {
				templateData.setTotal(total);
				templateData.setPageCount(0);
				templateData.setPageSize(1);
				templateData.setPageIndex(0);
				templateData.setListTable(new DataTable());

				if (!writeListHTML(catalogParser, templateData, fileName, 0, statScript)) {
					return false;
				}
			}
		} else {
			if (singlePageCatalog && detailParser != null) {
				DataTable listTable = templateData.getPagedDocList(item, catalogName, displayLevel, hasAttribute, type,
						condition, 1, 0);
				DataRow docRow = listTable.get(0);
				staticDoc(item, detailParser, detailTemplateData, docRow, fileName);
			} else {
				int pageSize = Integer.parseInt(pagesizeStr);
				if (pageSize == 0) {// ��ǩ��δ����ÿҳ����
					if (catalog != null && catalog.getListPageSize() > 0) {// ʹ����Ŀ����
						pageSize = (int) catalog.getListPageSize();
					} else {
						// Ĭ��20��
						pageSize = 20;
					}
				}

				if (publishPages == 0 && catalog.getListPage() > 0) {
					publishPages = (int) catalog.getListPage();// �б�ҳ����ҳ��
				}

				// ���б�ҳ����ҳ��С��ָ��ҳ��ʱ�����б�ҳ����ҳ��Ϊ׼
				if (catalog != null && catalog.getListPage() > 0 && catalog.getListPage() < publishPages) {
					publishPages = (int) catalog.getListPage();
				}

				// ���ָ��������ҳ���������ĵ�����������Ҫ���ɵ���Ŀ������������Ϊ׼
				if (catalog != null && catalog.getListPage() > 0) {
					int publishTotal = publishPages * pageSize;
					if (total > publishTotal) {
						total = publishTotal;
					}
				}

				int pageCount = (total % pageSize == 0) ? total / pageSize : total / pageSize + 1;

				templateData.setTotal(total);
				templateData.setPageCount(pageCount);
				templateData.setPageSize(pageSize);

				int pageIndex = 0;
				// ���÷���ҳ��
				if (publishPages > 0) {
					pageCount = publishPages;
				}

				int rowNum = 1;
				for (int k = 0; k < pageCount; k++) {
					templateData.setPageIndex(pageIndex);

					int count = pageSize;
					if ((k + 1) * pageSize > total) {
						count = total - k * pageSize;
						if (count <= 0) {
							break;
						}
					}
					
					DataTable listTable = null;
					if (detailParser != null) { //����������ϸҳ����Ҫ��װ����ҳ
						listTable = templateData.getWrapedPagedDocList(item, catalogName, displayLevel, hasAttribute,
								type, condition, pageSize, pageIndex);
					}else {
						listTable = templateData.getPagedDocList(item, catalogName, displayLevel, hasAttribute,
								type, condition, pageSize, pageIndex);
					}

					// ��¼���
					for (int i = 0; i < listTable.getRowCount(); i++) {
						listTable.set(i, "_RowNo", rowNum);
						rowNum++;
					}

					templateData.setListTable(listTable);

					if (!writeListHTML(catalogParser, templateData, fileName, pageIndex, statScript)) {
						return false;
					}
					pageIndex++;

					// ������ϸҳ
					if (detailParser != null) {
						for (int i = 0; i < listTable.getRowCount(); i++) {
							DataRow docRow = listTable.get(i);
							int catalogType = (int) catalog.getType();
							boolean isArticle = catalogType == Catalog.CATALOG || catalogType == Catalog.SUBJECT
									|| catalogType == Catalog.MAGAZINE || catalogType == Catalog.NEWSPAPER;

							if (isArticle && "4".equals((String) docRow.get("Type"))) { // ת�����Ӳ�������ϸҳ
								continue;
							} else {
								HtmlNameParser nameParser = new HtmlNameParser(site.toDataRow(), catalog.toDataRow(),
										docRow, catalog.getDetailNameRule());
								HtmlNameRule h = nameParser.getNameRule();
								String detailName = h.getFullPath();
								if (detailName.indexOf(".") != -1) {
									detailName = detailName.substring(0, detailName.indexOf(".") + 1)
											+ SiteUtil.getExtensionType(site.getID());
								}
								staticDoc(item, detailParser, detailTemplateData, docRow,
										staticDir + "/" + site.getAlias() + "/" + detailName);
							}
						}
					}
				}
			}
		}
		return true;
	}

	/**
	 * ����վ��id���ɾ�̬ҳ�棬�ݹ����
	 * 
	 * @param siteID
	 * @param parentID
	 */
	public boolean staticSite(long siteID) {
		TemplateCache.clear();
		// ����
		task.setCurrentInfo("���ڴ�������");
		ZCSiteSchema site = new ZCSiteSchema();
		site.setID(siteID);
		site.fill();

		if (!staticPageBlock(site, null, 0)) {
			return false;
		}
		task.setPercent(4);

		// ��ҳ
		task.setCurrentInfo("���ڴ���վ����ҳ");
		if (!staticSiteIndex(site)) {
			return false;
		}

		// ��Ŀ
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		ZCCatalogSet catalogSet = catalog.query(new QueryBuilder("where siteid=? and parentid=0 and type in (1,9)",
				siteID));
		for (int i = 0; i < catalogSet.size(); i++) {
			if (task.checkStop()) {
				return true;
			}
			catalog = catalogSet.get(i);
			task.setCurrentInfo("����������Ŀ" + catalog.getName());
			if (!staticCatalog(catalog, true)) {
				return false;
			}
			if (!staticChildCatalog(catalog.getID(), true)) {
				return false;
			}
			LogUtil.info("percent:" + (4 + i * 90 / catalogSet.size()));
			task.setPercent(4 + i * 90 / catalogSet.size());
		}
		task.setPercent(98);
		return true;
	}

	/**
	 * ��̬����ҳ
	 * 
	 * @param siteID
	 * @return
	 */
	public boolean staticSiteIndex(long siteID) {
		ZCSiteSchema site = new ZCSiteSchema();
		site.setID(siteID);
		if (!site.fill()) {
			return false;
		}
		task.setCurrentInfo("����վ����ҳ");

		if (!staticPageBlock(site, null, 0)) {
			return false;
		}

		if (task.getPercent() < 40) {
			task.setPercent(40);
		}

		return staticSiteIndex(site);
	}

	public boolean staticSiteIndex(ZCSiteSchema site) {
		long t = System.currentTimeMillis();
		TemplateCache.clear();

		String template = templateDir + "/" + site.getAlias() + "/" + site.getIndexTemplate();
		if (StringUtil.isEmpty(site.getIndexTemplate())) {
			return false;
		}
		template = template.replaceAll("//", "/");
		TemplateParser tp = getParser(site.getID(), template, 0);
		LogUtil.info("�����м�ű�:" + (System.currentTimeMillis() - t));

		if (tp == null) {
			return false;
		}

		CmsTemplateData templateData = new CmsTemplateData();
		templateData.setSite(site);
		tp.define("TemplateData", templateData);
		tp.define("site", site.toDataRow());
		String path = SiteUtil.getAbsolutePath(site.getID()) + "/";
		String fileName = "index." + SiteUtil.getExtensionType(site.getID());

		String statScript = "";
		if ("Y".equals(site.getAutoStatFlag())) {
			// ͳ�ƴ���
			String serviceUrl = Config.getValue("ServicesContext");
			String statUrl = "SiteID=" + site.getID() + "&Type=Article&Dest=" + serviceUrl + "/Stat.jsp";
			statScript = getStatScript(statUrl);
		}

		statScript += getVersionInfo();

		task.setCurrentInfo("������ҳ�ļ���" + fileName);

		boolean flag = writeHTML(tp, path + "/" + fileName, statScript);

		template = template.replaceAll(staticDir, "");
		int level = 0;
		ZCPageBlockSet blockSet = new ZCPageBlockSchema().query(new QueryBuilder(
				"where exists (select blockid from ZCTemplateBlockRela where filename=? and blockid=ZCPageBlock.ID)",
				template + "_" + level));
		if (blockSet != null && blockSet.size() > 0) {
			staticPageBlock(blockSet);
		}

		LogUtil.info("������ҳ��ʱ:" + (System.currentTimeMillis() - t));
		return flag;
	}

	/**
	 * ��̬���ĵ�ҳ��
	 * 
	 * @param doc
	 */
	public boolean staticDoc(String docType, Schema doc) {
		TemplateCache.clear();

		DataRow docDataRow = doc.toDataRow();
		String templateName = "";
		ZCCatalogSchema catalog = CatalogUtil.getSchema(docDataRow.getString("CatalogID"));
		if (catalog == null) {
			return false;
		}

		ZCSiteSchema site = SiteUtil.getSchema(catalog.getSiteID());
		if (site == null) {
			return false;
		}

		DataTable dt = new DataTable();
		dt.insertRow(docDataRow);
		ColumnUtil.extendDocColumnData(dt, catalog.getID());
		docDataRow = dt.get(0);

		templateName = catalog.getDetailTemplate();

		String siteCode = site.getAlias();
		if ("1".equals(docDataRow.getString("TemplateFlag"))) {
			templateName = docDataRow.getString("Template");
		}

		templateName = templateDir + "/" + siteCode + templateName;
		templateName = templateName.replaceAll("/+", "/");

		String rootPath = staticDir + "/" + site.getAlias() + "/";

		HtmlNameParser nameParser = new HtmlNameParser(site.toDataRow(), catalog.toDataRow(), docDataRow,
				catalog.getDetailNameRule());
		HtmlNameRule h = nameParser.getNameRule();
		int level = h.getLevel();

		// ��һҳ����һҳ
		docDataRow.insertColumn("PrevLink", "#");
		docDataRow.insertColumn("PrevTitle", "û������");
		docDataRow.insertColumn("NextLink", "#");
		docDataRow.insertColumn("NextTitle", "û������");
		docDataRow.insertColumn("FirstImagePath", null);
		docDataRow.insertColumn("FirstVideoImage", null);
		docDataRow.insertColumn("FirstVideoHtml", null);

		PubFun.dealArticleMedia(docDataRow);
		docDataRow.insertColumn("BranchName", PubFun.getBranchName(docDataRow.getString("BranchInnerCode")));

		DataTable prevDT = new QueryBuilder(
				"select * from zcarticle where catalogid=? and orderflag >? and status in (" + Article.STATUS_TOPUBLISH
						+ "," + Article.STATUS_PUBLISHED + ") order by orderflag asc", docDataRow.getLong("CatalogID"),
				docDataRow.getLong("OrderFlag")).executePagedDataTable(1, 0);
		if (prevDT.getRowCount() == 1) {
			docDataRow.set("PrevLink", prevDT.getString(0, "ID") + ".shtml");
			docDataRow.set("PrevTitle", prevDT.getString(0, "Title"));
		}
		DataTable nextDT = new QueryBuilder(
				"select * from zcarticle where catalogid=? and orderflag <? and status in (" + Article.STATUS_TOPUBLISH
						+ "," + Article.STATUS_PUBLISHED + ") order by orderflag desc",
				docDataRow.getLong("CatalogID"), docDataRow.getLong("OrderFlag")).executePagedDataTable(1, 0);
		if (nextDT.getRowCount() == 1) {
			docDataRow.set("NextLink", nextDT.getString(0, "ID") + ".shtml");
			docDataRow.set("NextTitle", nextDT.getString(0, "Title"));
		}

		TemplateParser parser = getParser(site.getID(), templateName, level);
		if (parser == null) {
			task.addError("û���ҵ���Ӧ��ģ���ļ�" + catalog.getDetailTemplate());
			return false;
		}

		CmsTemplateData templateData = new CmsTemplateData();
		templateData.setLevel(level);
		templateData.setSite(site);
		templateData.setCatalog(catalog);

		return staticDoc(docType, parser, templateData, docDataRow, rootPath + h.getFullPath());
	}

	public String previewDoc(String docType, DataRow docDataRow) {
		return previewDoc(docType, docDataRow, 1);
	}

	public String previewDoc(String docType, DataRow docDataRow, int currentPage) {
		String templateName = "";
		ZCCatalogSchema catalog = CatalogUtil.getSchema(docDataRow.getString("CatalogID"));
		if (catalog == null) {
			return "";
		}

		ZCSiteSchema site = SiteUtil.getSchema(catalog.getSiteID());
		if (site == null) {
			return "";
		}

		DataTable dt = new DataTable();
		dt.insertRow(docDataRow);
		ColumnUtil.extendDocColumnData(dt, catalog.getID());
		docDataRow = dt.get(0);

		templateName = catalog.getDetailTemplate();

		String siteCode = site.getAlias();
		if ("1".equals(docDataRow.getString("TemplateFlag"))) {
			templateName = docDataRow.getString("Template");
		}

		templateName = templateDir + "/" + siteCode + templateName;
		templateName = templateName.replaceAll("/+", "/");

		// ��һҳ����һҳ
		docDataRow.insertColumn("PrevLink", "#");
		docDataRow.insertColumn("PrevTitle", "û������");
		docDataRow.insertColumn("NextLink", "#");
		docDataRow.insertColumn("NextTitle", "û������");
		docDataRow.insertColumn("FirstImagePath", null);
		docDataRow.insertColumn("FirstVideoImage", null);
		docDataRow.insertColumn("FirstVideoHtml", null);

		PubFun.dealArticleMedia(docDataRow);
		docDataRow.insertColumn("BranchName", PubFun.getBranchName(docDataRow.getString("BranchInnerCode")));

		DataTable prevDT = new QueryBuilder(
				"select * from zcarticle where catalogid=? and orderflag >? and status in (" + Article.STATUS_TOPUBLISH
						+ "," + Article.STATUS_PUBLISHED + ") order by orderflag asc", docDataRow.getLong("CatalogID"),
				docDataRow.getLong("OrderFlag")).executePagedDataTable(1, 0);
		if (prevDT.getRowCount() == 1) {
			docDataRow.set("PrevLink", prevDT.getString(0, "ID") + ".shtml");
			docDataRow.set("PrevTitle", prevDT.getString(0, "Title"));
		}
		DataTable nextDT = new QueryBuilder(
				"select * from zcarticle where catalogid=? and orderflag <? and status in (" + Article.STATUS_TOPUBLISH
						+ "," + Article.STATUS_PUBLISHED + ") order by orderflag desc",
				docDataRow.getLong("CatalogID"), docDataRow.getLong("OrderFlag")).executePagedDataTable(1, 0);
		if (nextDT.getRowCount() == 1) {
			docDataRow.set("NextLink", nextDT.getString(0, "ID") + ".shtml");
			docDataRow.set("NextTitle", nextDT.getString(0, "Title"));
		}

		String content = docDataRow.getString("Content");

		String siteurl = SiteUtil.getURL(docDataRow.getLong("SiteID"));
		if (StringUtil.isNotEmpty(siteurl) && !"http://".equalsIgnoreCase(siteurl)) {
			String cmsurl = (Config.getContextPath() + Config.getValue("Statical.TargetDir") + "/"
					+ SiteUtil.getAlias(docDataRow.getLong("SiteID")) + "/").replaceAll("/+", "/");
			content = content.replaceAll(siteurl, cmsurl);
		}

		// �����ҳ������
		String[] pages = content.split(Constant.PAGE_BREAK, -1);
		if (pages.length > 0 && currentPage <= pages.length) {
			content = pages[currentPage - 1];
		}

		docDataRow.set("Content", content);

		if (!new File(templateName).exists()) {
			return "��ϸҳģ���ļ������ڣ�" + templateName;
		} else if (!new File(templateName).isFile()) {
			return "��ϸҳģ�岻��Ϊ�ļ���:" + templateName;
		}

		String templatecontent = FileUtil.readText(templateName);

		TagParser tagParser = new TagParser();
		tagParser.setSiteID(site.getID());
		tagParser.setTemplateFileName(templateName);
		tagParser.setPageBlock(false);
		tagParser.setPreview(true);
		tagParser.setContent(templatecontent);
		tagParser.setDirLevel(0);
		tagParser.parse();

		TemplateParser templateParser = new TemplateParser();
		templateParser.setFileName(templateName);
		templateParser.addClass("com.nswt.cms.pub.CatalogUtil");
		templateParser.addClass("com.nswt.cms.pub.SiteUtil");
		templateParser.addClass("com.nswt.cms.pub.PubFun");
		templateParser.setPageListPrams(tagParser.getPageListPrams());
		templateParser.setTemplate(tagParser.getContent());

		try {
			templateParser.parse();
		} catch (EvalException e) {
			return "ģ���������" + e.getMessage();
		}

		CmsTemplateData templateData = new CmsTemplateData();
		templateData.setLevel(0);
		templateData.setSite(site);
		templateData.setCatalog(catalog);
		templateData.setDoc(docDataRow);
		templateData.setPageIndex(currentPage - 1);
		templateData.setPageSize(1);
		templateData.setPageCount(pages.length);
		templateData.setTotal(pages.length);
		templateData.setFirstFileName("PreviewDoc.jsp?ArticleID=" + docDataRow.getString("ID") + "&currentPage=1");
		templateData.setOtherFileName("PreviewDoc.jsp?ArticleID=" + docDataRow.getString("ID") + "&currentPage=@INDEX");

		templateParser.define("site", templateData.getSite());
		templateParser.define("catalog", templateData.getCatalog());
		templateParser.define("TemplateData", templateData);
		templateParser.define(docType.toLowerCase(), docDataRow);
		templateParser.define("system", ParserCache.getConfig(site.getID()));
		templateParser.define("page", templateData.getPageRow());

		String html = "";
		try {
			templateParser.generate();
			html = templateParser.getResult();
		} catch (TemplateException e) {
			e.printStackTrace();
			html = "����ʧ�ܣ�������Ϣ��" + e.getMessage();
		}

		return html;
	}

	private boolean staticDoc(String docType, TemplateParser parser, CmsTemplateData templateData, DataRow docDataRow,
			String fileName) {
		fileName = fileName.replace('\\', '/').replaceAll("/+", "/");
		String rootPath = fileName.substring(0, fileName.lastIndexOf("/") + 1);
		fileName = fileName.substring(fileName.lastIndexOf("/") + 1);

		// �������õ�ģ��
		String detailTemplateName = "";
		TemplateParser currentDetailParser;
		if ("1".equals(docDataRow.getString("TemplateFlag"))) {
			detailTemplateName = docDataRow.getString("Template");
			detailTemplateName = templateDir + "/" + templateData.getSite().getString("Alias") + detailTemplateName;
			detailTemplateName = detailTemplateName.replaceAll("/+", "/");
			currentDetailParser = getParser(templateData.getSite().getLong("Alias"), detailTemplateName,
					templateData.getLevel());
			if (currentDetailParser == null) {
				currentDetailParser = parser;
			}
		} else {
			currentDetailParser = parser;
		}

		currentDetailParser.define("site", templateData.getSite());
		currentDetailParser.define("catalog", templateData.getCatalog());

		File f = new File(rootPath);
		if (!f.exists()) {
			f.mkdirs();
		}

		// ���·�ҳ
		if ("article".equalsIgnoreCase(docType)) {
			// �滻ͼƬ������·��
			String content = docDataRow.getString("Content");
			long siteID = docDataRow.getLong("SiteID");

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
				String prefix = PubFun.getLevelStr(templateData.getLevel());
				// ����з��벿��
				if ("Y".equals(ConfigImageLib.getImageLibConfig(siteID).getString("ImageSeparateFlag"))) {
					prefix = ConfigImageLib.getImageLibConfig(siteID).getString("ImageSeparateURLPrefix");
				}
				content = content.replaceAll(imagePath, prefix);
				content = content.replaceAll(attachPath, serviceContext + "AttachDownLoad.jsp");

				if (StringUtil.isNotEmpty(siteurl) && !"http://".equalsIgnoreCase(siteurl)) {
					content = content.replaceAll(StringUtil.md5Hex(siteurl), siteurl);
				}
			}

			// �����
			if (StringUtil.isNotEmpty(docDataRow.getString("HitCount"))) {
				docDataRow.set("HitCount", getClickScript(docDataRow.getString("ID")));
			}

			// ��������˹ؼ����滻����
			String cid = docDataRow.getString("CatalogID");
			String keyWordType = CatalogUtil.getHotWordType(cid);

			if (StringUtil.isNotEmpty(keyWordType) && !"0".equalsIgnoreCase(keyWordType)) {
				ZCKeywordSet keywordSet = (ZCKeywordSet) Keyword.getKeyWordSet(keyWordType);
				if (keywordSet != null && keywordSet.size() > 0) {
					String searchUrl = templateData.getSearchURL();
					Mapx keyWordCache = new Mapx();
					for (int i = 0; i < keywordSet.size(); i++) {
						ZCKeywordSchema keyword = keywordSet.get(i);
						String word = keyword.getKeyword();
						String url = keyword.getLinkUrl();
						if (StringUtil.isEmpty(url)) {
							url = searchUrl + "?site=" + docDataRow.getString("SiteID") + "&query=" + word;
						}
						String target = keyword.getLinkTarget();
						String alt = keyword.getLinkAlt();
						if (StringUtil.isEmpty(alt)) {
							alt = word;
						}
						String text = "<a href='" + url + "' target='" + target + "' title='" + alt + "'>" + word
								+ "</a>";
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

			docDataRow.insertColumn("FullContent", content);

			String[] pages = content.split(Constant.PAGE_BREAK, -1);
			if (pages.length > 0) {
				templateData.setPageCount(pages.length);
				templateData.setPageSize(1);
				templateData.setTotal(pages.length);
				templateData.setFirstFileName(fileName);

				int index = fileName.lastIndexOf(".");
				String otherPageName = null;
				if (index != -1) {
					otherPageName = fileName.substring(0, index) + "_@INDEX" + fileName.substring(index);
				} else {
					otherPageName += "_@INDEX";
				}
				templateData.setOtherFileName(otherPageName);
				for (int k = 0; k < pages.length; k++) {
					docDataRow.set("Content", pages[k]);
					templateData.setPageIndex(k);
					templateData.setDoc(docDataRow);

					currentDetailParser.define("TemplateData", templateData);
					currentDetailParser.define(docType.toLowerCase(), docDataRow);
					currentDetailParser.define("page", templateData.getPageRow());
					String pageName = fileName;
					if (k > 0) {
						pageName = otherPageName.replaceAll("@INDEX", String.valueOf(k + 1));
					}

					String statScript = "";
					if ("Y".equals(templateData.getSite().getString("AutoStatFlag"))) {
						// ͳ�ƴ���
						String serviceUrl = Config.getValue("ServicesContext");
						String statUrl = "SiteID=" + docDataRow.getString("SiteID") + "&Type=" + docType
								+ "&CatalogInnerCode=" + docDataRow.getString("CatalogInnerCode") + "&LeafID="
								+ docDataRow.getString("ID") + "&Dest=" + serviceUrl + "/Stat.jsp";
						statScript = getStatScript(statUrl);
					}

					statScript += getVersionInfo();
					if (!writeHTML(currentDetailParser, rootPath + "/" + pageName, statScript)) {
						return false;
					}
				}
			}
		} else {
			templateData.setDoc(docDataRow);
			currentDetailParser.define("TemplateData", templateData);
			currentDetailParser.define(docType.toLowerCase(), docDataRow);
			String pageName = fileName;

			String statScript = "";
			if ("Y".equals(templateData.getSite().getString("AutoStatFlag"))) {
				// ͳ�ƴ���
				String serviceUrl = Config.getValue("ServicesContext");
				String statUrl = "SiteID=" + docDataRow.getString("SiteID") + "&Type=" + docType + "&CatalogInnerCode="
						+ docDataRow.getString("CatalogInnerCode") + "&LeafID=" + docDataRow.getString("ID") + "&Dest="
						+ serviceUrl + "/Stat.jsp";
				statScript = getStatScript(statUrl);
			}

			statScript += getVersionInfo();

			if (!writeHTML(currentDetailParser, rootPath + "/" + pageName, statScript)) {
				return false;
			}
		}

		return true;
	}

	public boolean staticPageBlock(ZCPageBlockSet set) {
		ZCSiteSchema site = new ZCSiteSchema();
		site.setID(set.get(0).getSiteID());
		site.fill();

		Mapx catalogMap = new Mapx();

		for (int i = 0; i < set.size(); i++) {
			ZCCatalogSchema catalog = (ZCCatalogSchema) catalogMap.get(set.get(i).getCatalogID() + "");
			if (catalog == null) {
				catalog = new ZCCatalogSchema();
				catalog.setID(set.get(i).getCatalogID());
				if (!catalog.fill()) {
					catalog = null;
				}

				catalogMap.put(set.get(i).getCatalogID() + "", catalog);
			}

			if (!staticOnePageBlock(site, catalog, set.get(i))) {
				return false;
			}
		}
		return true;
	}

	public boolean staticPageBlock(ZCSiteSchema site, ZCCatalogSchema catalog, int type) {
		if (site == null) {
			return false;
		}
		QueryBuilder qb = null;
		String wherePart = (type == 4) ? "where type=4" : "where type<>4";
		if (catalog != null) {
			wherePart += " and catalogid=" + catalog.getID();
		} else {
			wherePart += " and catalogid is null and siteid=" + site.getID();
		}
		qb = new QueryBuilder(wherePart);
		ZCPageBlockSet set = new ZCPageBlockSchema().query(qb);
		if (set.size() > 0) {
			for (int i = 0; i < set.size(); i++) {
				ZCPageBlockSchema block = set.get(i);
				if (!staticOnePageBlock(site, catalog, block) && type != 4) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * ������Ŀ��Ӧ��ϵͳ�����ļ���������Ŀģ���ҵ���������Ŀ�еĶ�Ӧ���顣
	 * 
	 * @param site
	 * @param catalog
	 * @return
	 */
	private boolean staticInnerPageBlock(ZCSiteSchema site, ZCCatalogSchema catalog) {
		if (site == null) {
			return false;
		}
		String alias = site.getAlias();
		// ��Ŀ��ҳģ��
		String indexTemplate = catalog.getIndexTemplate();
		if (StringUtil.isNotEmpty(indexTemplate)) {
			indexTemplate = "/" + alias + indexTemplate + "_" + catalog.getTreeLevel();
			staticInnerPageBlock(site, catalog, indexTemplate);
		}
		// ��Ŀ�б�ģ��
		String listTemplate = catalog.getListTemplate();
		if (StringUtil.isNotEmpty(listTemplate)) {
			listTemplate = "/" + alias + listTemplate + "_" + catalog.getTreeLevel();
			staticInnerPageBlock(site, catalog, listTemplate);
		}

		// ��Ŀ��ϸҳģ��
		String detailTemplate = catalog.getDetailTemplate();
		if (StringUtil.isNotEmpty(detailTemplate)) {
			detailTemplate = "/" + alias + detailTemplate + "_" + CatalogUtil.getDetailLevel(catalog.getID());
			staticInnerPageBlock(site, catalog, detailTemplate);
		}

		return true;
	}

	private boolean staticInnerPageBlock(ZCSiteSchema site, ZCCatalogSchema catalog, String templateFile) {
		QueryBuilder qb = new QueryBuilder("where exists (select blockid from  ZCTemplateBlockRela where filename=?"
				+ " and blockid=zcpageblock.id)", templateFile);
		ZCPageBlockSet set = new ZCPageBlockSchema().query(qb);
		if (set.size() > 0) {
			for (int i = 0; i < set.size(); i++) {
				ZCPageBlockSchema block = set.get(i);
				String targetFile = templateDir + "/" + site.getAlias() + "/" + block.getFileName();
				File f = new File(targetFile);
				if (!f.exists()) {
					if (!staticOnePageBlock(site, catalog, block)) {
						return false;
					}
				}
			}
		}

		return true;
	}

	/**
	 * ��̬��ָ��������
	 * 
	 * @param block
	 * @param site
	 * @return
	 */
	public boolean staticOnePageBlock(ZCSiteSchema site, ZCCatalogSchema catalog, ZCPageBlockSchema block) {
		TemplateCache.clear();
		String templateName = block.getTemplate();

		String filename = block.getFileName().replace('\\', '/').replaceAll("/+", "/");
		if (filename.startsWith("/")) {
			filename = filename.substring(1);
		}
		int level = StringUtil.count(filename, "/");
		if (block.getType() == 4) {
			String code = block.getCode();
			if (code.lastIndexOf("_") != -1) {
				level = Integer.parseInt(code.substring(code.lastIndexOf("_") + 1));
			}
		}

		if (block.getType() != 3) {
			templateName = templateDir + "/" + site.getAlias() + "/" + block.getTemplate();
			templateName = templateName.replace('\\', '/').replaceAll("/+", "/");
		}

		String sitePath = SiteUtil.getAbsolutePath(block.getSiteID());
		if (block.getType() == 3) {
			File f = new File(sitePath);
			if (!f.exists()) {
				f.mkdirs();
			}
			String fullPath = sitePath + filename;
			FileUtil.writeText(fullPath, block.getContent());

			// ������������ҳ��ĵ���ҳ��
			String includeFile = staticDir + "/" + site.getAlias() + "/" + block.getFileName();
			includeFile = includeFile.replace('\\', '/').replaceAll("/+", "/");
			generateIncludeFiles(includeFile);
		} else {
			// ��������д������ö����Ŀ���ݣ��������鸴�Ƶ�������Ŀ��
			if (block.getType() != 4) {
				PreParser p = new PreParser();
				p.setSiteID(block.getSiteID());
				p.setTemplateFileName(templateName);
				ArrayList idList = p.parseList();
				ZCPageBlockSet blockSet = new ZCPageBlockSet();
				for (int i = 0; i < idList.size(); i++) {
					String id = (String) idList.get(i);
					if (catalog != null && (catalog.getID() + "").equals(id)) {
						continue;
					}

					// �ж��Ƿ��Ѿ����
					QueryBuilder qb = new QueryBuilder(
							"select count(*) from zcpageblock where catalogid=? and template=? and filename=?");
					qb.add(Long.parseLong(id));
					qb.add(block.getTemplate());
					qb.add(block.getFileName());
					int count = qb.executeInt();
					if (count > 0) {
						continue;
					}

					ZCPageBlockSchema blockCopy = (ZCPageBlockSchema) block.clone();
					long blockID = NoUtil.getMaxID("PageBlockID");
					blockCopy.setID(blockID);
					blockCopy.setCatalogID(id);
					blockCopy.setAddTime(new Date());
					blockCopy.setAddUser("admin");

					blockSet.add(blockCopy);
				}

				if (blockSet.size() > 0) {
					if (!blockSet.insert()) {
						return false;
					}
				}
			}

			TemplateParser parser = getParser(site.getID(), templateName, level, true);

			if (parser == null) {
				if (block.getType() == 4) {
					return true;
				}
				task.addError("û���ҵ���������" + block.getName() + "��Ӧ��ģ���ļ�" + templateName);
				Errorx.addError("û���ҵ���������" + block.getName() + "��Ӧ��ģ���ļ�" + templateName);
				return false;
			}

			CmsTemplateData templateData = new CmsTemplateData();
			templateData.setLevel(level);
			templateData.setSite(site);
			templateData.setBlock(block.toDataRow());

			if (catalog != null) {
				templateData.setCatalog(catalog);
				parser.define("catalog", templateData.getCatalog());
			}
			parser.define("site", templateData.getSite());
			parser.define("TemplateData", templateData);

			// ��ҳ����
			if (parser.getPageListPrams().size() > 0) {
				staticListPages(site, catalog, sitePath + filename, parser, templateData, false, -1);
			} else {
				task.setCurrentInfo("�����ļ���" + filename);

				if (!writeHTML(parser, sitePath + "/" + filename, "")) {
					if (block.getType() == 4) {
						return true;
					}
					task.addError("�������鷢�����������ļ���" + filename);
					return false;
				}

				// ������������ҳ��ĵ���ҳ��
				String includeFile = staticDir + "/" + site.getAlias() + "/" + block.getFileName();
				includeFile = includeFile.replace('\\', '/').replaceAll("/+", "/");
				generateIncludeFiles(includeFile);
			}
		}

		return true;
	}

	private TemplateParser getParser(long siteID, String template, int level) {
		return getParser(siteID, template, level, false);
	}

	private TemplateParser getParser(long siteID, String template, int level, boolean isPageBlock) {
		if (template == null || "".equals(template)) {
			return null;
		}
		TemplateParser parser = ParserCache.get(siteID, template, level, isPageBlock, fileList);
		return parser;
	}

	/**
	 * ���°����ļ��������ļ��������ļ���������Ӧ�İ����ļ�
	 * 
	 * @param includeFile
	 */
	private void generateIncludeFiles(String includeFile) {
		// ���°����ļ�
		String fileName = includeFile.substring(includeFile.lastIndexOf("/") + 1, includeFile.lastIndexOf("."));
		String includeDir = includeFile.substring(0, includeFile.lastIndexOf("/"));
		File files = new File(includeDir);
		Collection c = new ArrayList();
		if (files.exists()) {
			File[] fileList = files.listFiles();
			for (int i = 0; i < fileList.length; i++) {
				File tmpFile = fileList[i];
				String tmpFileName = tmpFile.getName();
				if (tmpFileName.startsWith(fileName + "_")) {
					if (tmpFileName.lastIndexOf("_") != -1) {
						String levelStr = "";
						if (tmpFileName.lastIndexOf(".") != -1) {
							levelStr = tmpFileName.substring(tmpFileName.lastIndexOf("_") + 1,
									tmpFileName.lastIndexOf("."));
						} else {
							levelStr = tmpFileName.substring(tmpFileName.lastIndexOf("_") + 1);
						}

						if (!StringUtil.isDigit(levelStr)) {
							continue;
						}

						int level = Integer.parseInt(levelStr);
						String levelString = "";
						for (int j = 0; j < level; j++) {
							levelString += "../";
						}
						TagParser parser = new TagParser();
						parser.setPageBlock(false);
						String includeContent = parser.dealResource(FileUtil.readText(includeFile), levelString);
						FileUtil.writeText(tmpFile.getAbsolutePath(), includeContent);
						c.add(tmpFile.getAbsolutePath());
					}
				}
			}
		}

		fileList.addAll(c);
	}

	public boolean writeHTML(TemplateParser tp, String fileName, String statScript) {
		long t = System.currentTimeMillis();
		try {
			tp.generate();
		} catch (Exception e) {
			String errorMessage = "ģ�������������ģ��" + tp.getFileName() + "�Ƿ���ȷ��������Ϣ��" + e.getMessage();
			LogUtil.info(tp.getScript());
			Errorx.addError(errorMessage);
			task.addError(errorMessage);
			LogUtil.info(errorMessage);
			return false;
		}
		LogUtil.info("����ҳ���ʱ��" + (System.currentTimeMillis() - t));

		String html = tp.getResult();
		html += statScript; // ���ͳ�ƴ���

		fileName = fileName.replaceAll("/+", "/");

		String filePath = fileName.substring(0, fileName.lastIndexOf('/'));
		File f = new File(filePath);
		if (!f.exists()) {
			f.mkdirs();
		}

		FileUtil.writeText(fileName, html);
		LogUtil.info("����" + fileName + " ҳ���ʱ��" + (System.currentTimeMillis() - t));
		fileList.add(fileName);

		convertBig5(html, fileName);

		return true;
	}

	/**
	 * ��htmlת��Ϊ��������
	 * 
	 * @param html
	 * @param fileName
	 */
	private void convertBig5(String html, String fileName) {
		if ("Y".equals(Config.getValue("Big5ConvertFlag"))) {
			String big5FileName = fileName;
			if (fileName.indexOf("cn/") != -1) {
				big5FileName = fileName.replaceAll("cn", "big5");
			} else if (fileName.indexOf("cache") != -1 || fileName.indexOf("include") != -1) {
				big5FileName = big5FileName.replaceAll(staticDir, staticDir + "/big5/");

				big5FileName = fileName.substring(0, fileName.lastIndexOf('.')) + "_big5"
						+ fileName.substring(fileName.lastIndexOf('.'));
			} else if (fileName.indexOf("index.shtml") != -1) {
				big5FileName = fileName.substring(0, fileName.lastIndexOf('.')) + "_big5"
						+ fileName.substring(fileName.lastIndexOf('.'));
			} else {
				return;
			}
			String big5Dir = big5FileName.substring(0, big5FileName.lastIndexOf("/"));
			File big5File = new File(big5Dir);
			if (!big5File.exists()) {
				big5File.mkdirs();
			}

			String big5Html = Big5Convert.toTradition(html);
			big5Html = big5Html.replaceAll("cn/", "big5/");

			Pattern includePattern = Pattern.compile("\\s(file|virtual)\\s*?=\\s*?(\\\"|\\\')(.*?)\\2",
					Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
			Matcher m = includePattern.matcher(big5Html);
			StringBuffer sb = new StringBuffer();
			int lastEndIndex = 0;
			while (m.find(lastEndIndex)) {
				sb.append(big5Html.substring(lastEndIndex, m.start()));
				lastEndIndex = m.end();
				String includeFile = m.group(3);
				includeFile = includeFile.substring(0, includeFile.lastIndexOf('.')) + "_big5"
						+ includeFile.substring(includeFile.lastIndexOf('.'));
				sb.append(" virtual=\"" + includeFile + "\"");
			}
			sb.append(big5Html.substring(lastEndIndex));
			big5Html = sb.toString();

			FileUtil.writeText(big5FileName, big5Html);
			LogUtil.info(big5FileName);
			fileList.add(big5FileName);
		}
	}

	public boolean writeListHTML(TemplateParser tp, CmsTemplateData templateData, String firstFileName, int pageIndex,
			String statScript) {
		firstFileName = firstFileName.replace('\\', '/').replaceAll("/+", "/");
		String fileDir = firstFileName.substring(0, firstFileName.lastIndexOf("/") + 1);
		firstFileName = firstFileName.substring(firstFileName.lastIndexOf("/") + 1);
		String otherFileName;
		int index = firstFileName.lastIndexOf(".");
		if (index != -1) {
			otherFileName = firstFileName.substring(0, index) + "_@INDEX" + firstFileName.substring(index);
		} else {
			otherFileName = firstFileName + "_@INDEX";
		}
		templateData.setFirstFileName(firstFileName);
		templateData.setOtherFileName(otherFileName);

		// ����ģ������
		tp.define("site", templateData.getSite());
		tp.define("catalog", templateData.getCatalog());
		tp.define("TemplateData", templateData);
		tp.define("page", templateData.getPageRow());

		long t = System.currentTimeMillis();
		try {
			tp.generate();
		} catch (Exception e) {
			LogUtil.info(tp.getScript());
			task.addError("ģ��" + tp.getFileName() + "������������ģ������Ƿ���ȷ");
			return false;
		}
		LogUtil.info("����ҳ�棺" + (System.currentTimeMillis() - t));

		String html = tp.getResult();
		html += statScript; // ���ͳ�ƴ���

		String fileName = null;
		if (pageIndex == 0) {
			fileName = fileDir + firstFileName;
		} else {
			fileName = fileDir + otherFileName.replaceAll("@INDEX", String.valueOf(pageIndex + 1));
		}

		String FilePath = fileName.substring(0, fileName.lastIndexOf('/'));
		File f = new File(FilePath);
		if (!f.exists()) {
			f.mkdirs();
		}

		html += getVersionInfo();

		FileUtil.writeText(fileName, html);
		LogUtil.info(fileName + " ��ʱ��" + (System.currentTimeMillis() - t));
		fileList.add(fileName);

		convertBig5(html, fileName);

		return true;
	}

	public String getStatScript(String statUrl) {
		String serviceUrl = Config.getValue("ServicesContext");
		String statScript = "\n<script src=\"" + serviceUrl + "/Stat.js\" type=\"text/javascript\"></script>\n";
		statScript += "<script>\n";
		statScript += "if(window._nswtp_stat)_nswtp_stat(\"" + statUrl + "\");\n";
		statScript += "</script>\n";
		return statScript;
	}

	public String getClickScript(String articleID) {
		String serviceUrl = Config.getValue("ServicesContext");
		String clickScript = "\n<script src=\"" + serviceUrl + "/Counter.jsp?Type=Article&ID=" + articleID
				+ "\" type=\"text/javascript\"></script>\n";
		return clickScript;
	}

	public String getVersionInfo() {
		StringBuffer sb = new StringBuffer();
		sb.append("\n<!-- Powered by ");
		sb.append(product.getAppCode());
		sb.append("(" + product.getAppName() + ") ");
		sb.append(product.getMainVersion());
		sb.append(".");
		sb.append(product.getMinorVersion());
		sb.append(" PublishDate ");
		sb.append(DateUtil.getCurrentDateTime());
		sb.append("-->");
		return sb.toString();
	}

	public ArrayList getFileList() {
		return fileList;
	}

	public void setFileList(ArrayList fileList) {
		this.fileList = fileList;
	}
}
