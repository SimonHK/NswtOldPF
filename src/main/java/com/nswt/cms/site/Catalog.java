package com.nswt.cms.site;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.nswt.cms.datachannel.Publisher;
import com.nswt.cms.dataservice.ColumnUtil;
import com.nswt.cms.document.Article;
import com.nswt.cms.document.ArticleRelaIndexer;
import com.nswt.cms.pub.CatalogUtil;
import com.nswt.cms.pub.PubFun;
import com.nswt.cms.pub.SiteUtil;
import com.nswt.cms.resource.AttachmentLib;
import com.nswt.cms.resource.AudioLib;
import com.nswt.cms.resource.ImageLib;
import com.nswt.cms.resource.VideoLib;
import com.nswt.cms.stat.impl.CatalogStat;
import com.nswt.framework.Config;
import com.nswt.framework.Page;
import com.nswt.framework.User;
import com.nswt.framework.controls.DataGridAction;
import com.nswt.framework.controls.TreeAction;
import com.nswt.framework.controls.TreeItem;
import com.nswt.framework.data.DataCollection;
import com.nswt.framework.data.DataColumn;
import com.nswt.framework.data.DataRow;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.messages.LongTimeTask;
import com.nswt.framework.utility.ChineseSpelling;
import com.nswt.framework.utility.DateUtil;
import com.nswt.framework.utility.FileUtil;
import com.nswt.framework.utility.Filter;
import com.nswt.framework.utility.HtmlUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.Application;
import com.nswt.platform.Priv;
import com.nswt.platform.UserList;
import com.nswt.platform.UserLog;
import com.nswt.platform.pub.NoUtil;
import com.nswt.schema.ZCArticleSchema;
import com.nswt.schema.ZCAttachmentSchema;
import com.nswt.schema.ZCAudioSchema;
import com.nswt.schema.ZCCatalogConfigSchema;
import com.nswt.schema.ZCCatalogSchema;
import com.nswt.schema.ZCCatalogSet;
import com.nswt.schema.ZCImageSchema;
import com.nswt.schema.ZCMagazineIssueSchema;
import com.nswt.schema.ZCMagazineSchema;
import com.nswt.schema.ZCPageBlockItemSchema;
import com.nswt.schema.ZCPageBlockItemSet;
import com.nswt.schema.ZCPageBlockSchema;
import com.nswt.schema.ZCPageBlockSet;
import com.nswt.schema.ZCSiteSchema;
import com.nswt.schema.ZCVideoSchema;
import com.nswt.schema.ZDColumnRelaSchema;
import com.nswt.schema.ZDColumnRelaSet;
import com.nswt.schema.ZDColumnValueSchema;
import com.nswt.schema.ZDColumnValueSet;
import com.nswt.schema.ZDPrivilegeSchema;

public class Catalog extends Page {

	/**
	 * ��ͨ��Ŀ
	 */
	public final static int CATALOG = 1;

	/**
	 * ר��
	 */
	public final static int SUBJECT = 2;

	/**
	 * �ڿ�
	 */
	public final static int MAGAZINE = 3;

	/**
	 * ͼƬ��
	 */
	public final static int IMAGELIB = 4;

	/**
	 * ��Ƶ��
	 */
	public final static int VIDEOLIB = 5;

	/**
	 * ��Ƶ��
	 */
	public final static int AUDIOLIB = 6;

	/**
	 * ������
	 */
	public final static int ATTACHMENTLIB = 7;

	/**
	 * ��ֽ
	 */
	public final static int NEWSPAPER = 8;

	/**
	 * �̳�-��ƷĬ�Ϸ���
	 */
	public final static int SHOP_GOODS = 9;

	/**
	 * �̳�-��ƷƷ�Ʒ���
	 */
	public final static int SHOP_GOODS_BRAND = 10;

	/**
	 * �̳�-��Ʒ��������
	 */
	public final static int SHOP_GOODS_TYPE = 11;

	public final static Mapx CatalogTypeMap = new Mapx();

	static {
		CatalogTypeMap.put(CATALOG + "", "��Ŀ");
		CatalogTypeMap.put(SUBJECT + "", "ר��");
		CatalogTypeMap.put(MAGAZINE + "", "��־");
		CatalogTypeMap.put(IMAGELIB + "", "ͼƬ");
		CatalogTypeMap.put(VIDEOLIB + "", "��Ƶ");
		CatalogTypeMap.put(AUDIOLIB + "", "��Ƶ");
		CatalogTypeMap.put(ATTACHMENTLIB + "", "����");
		CatalogTypeMap.put(SHOP_GOODS + "", "��Ʒ");
		CatalogTypeMap.put(SHOP_GOODS_BRAND + "", "Ʒ��");
		CatalogTypeMap.put(SHOP_GOODS_TYPE + "", "��Ʒ��������");
	}

	public static Mapx init(Mapx params) {
		Object o1 = params.get("CatalogID");
		if (o1 == null) {
			o1 = params.get("Cookie.DocList.LastCatalog");
		}
		if (o1 != null) {
			long ID = Long.parseLong(o1.toString());
			ZCCatalogSchema catalog = new ZCCatalogSchema();
			catalog.setID(ID);
			if (catalog.fill()) {
				Mapx map = catalog.toMapx();

				String publishFlag = catalog.getPublishFlag();
				if (StringUtil.isEmpty(publishFlag)) {
					publishFlag = "Y";
				}
				map.put("PublishFlag", HtmlUtil.codeToRadios("PublishFlag", "YesOrNo", publishFlag));

				String allowContribute = catalog.getAllowContribute();
				if (StringUtil.isEmpty(allowContribute)) {
					allowContribute = "N";
				}
				map.put("AllowContribute", HtmlUtil.codeToRadios("AllowContribute", "YesOrNo", allowContribute));

				String singleFlag = catalog.getSingleFlag();
				if (StringUtil.isEmpty(singleFlag) || singleFlag.equals("N")) {
					map.put("SingleFlagN", "checked");
				} else {
					map.put("SingleFlagY", "checked");
				}
				map.put("SingleFlag", HtmlUtil.codeToRadios("SingleFlag", "YesOrNo", singleFlag));
				DataTable workflowDT = new QueryBuilder("select name,ID from ZWWorkflow").executeDataTable();
				map.put("Workflow", HtmlUtil.dataTableToOptions(workflowDT, catalog.getWorkflow(), true));

				String keywordFlag = catalog.getKeywordFlag();
				if (StringUtil.isEmpty(keywordFlag)) {
					keywordFlag = "N";
				}

				if (StringUtil.isNotEmpty(catalog.getIndexTemplate())) {
					String s1 = "<a href='javascript:void(0);' onclick=\"openEditor('" + catalog.getIndexTemplate()
							+ "')\"><img src='../Platform/Images/icon_edit.gif'" + " width='14' height='14'></a>";
					map.put("edit", s1);
				}
				if (StringUtil.isNotEmpty(catalog.getListTemplate())) {
					String s1 = "<a href='javascript:void(0);' onclick=\"openEditor('" + catalog.getListTemplate()
							+ "')\"><img src='../Platform/Images/icon_edit.gif'" + " width='14' height='14'></a>";
					map.put("edit1", s1);
				}
				if (StringUtil.isNotEmpty(catalog.getDetailTemplate())) {
					String s1 = "<a href='javascript:void(0);' onclick=\"openEditor('" + catalog.getDetailTemplate()
							+ "')\"><img src='../Platform/Images/icon_edit.gif'" + " width='14' height='14'></a>";
					map.put("edit2", s1);
				}
				if (StringUtil.isNotEmpty(catalog.getRssTemplate())) {
					String s1 = "<a href='javascript:void(0);' onclick=\"openEditor('" + catalog.getRssTemplate()
							+ "')\"><img src='../Platform/Images/icon_edit.gif'" + " width='14' height='14'></a>";
					map.put("edit3", s1);
				}

				if (StringUtil.isEmpty(catalog.getDetailNameRule())) {
					map.put("DetailNameRule", "/${catalogpath}/${document.id}.shtml");
				}

				if (StringUtil.isEmpty(catalog.getListNameRule())) {
					map.put("ListNameRule", "/${catalogpath}/${year}/${month}/");
				}

				if ("Y".equals(catalog.getSingleFlag())) {
					map.put("IsDisplay", "none");
				}

				String imagePath = catalog.getImagePath();
				if (StringUtil.isNotEmpty(imagePath)) {
					imagePath = Config.getContextPath() + Config.getValue("UploadDir") + "/"
							+ SiteUtil.getAlias(Application.getCurrentSiteID()) + "/" + catalog.getImagePath();
				} else {
					imagePath = "../Images/addpicture.jpg";
				}
				map.put("ImageFullPath", imagePath);
				return map;
			}
		}
		return null;
	}

	public static Mapx initStructure(Mapx params) {
		String id = params.getString("ID");
		QueryBuilder qb = new QueryBuilder("select Name,ID,TreeLevel,ParentID from ZCCatalog ");
		if (StringUtil.isEmpty(id)) {
			qb.append(" where SiteID=?", Application.getCurrentSiteID());
		} else {
			String innerCode = CatalogUtil.getInnerCode(id);
			qb.append(" where InnerCode like ?", innerCode + "%");
		}
		qb.append(" and Type=?", Long.parseLong(params.getString("Type")));
		DataTable dt = qb.executeDataTable();
		dt = DataGridAction.sortTreeDataTable(dt, "ID", "ParentID");
		PubFun.indentDataTable(dt);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < dt.getRowCount(); i++) {
			sb.append(dt.getString(i, "Name") + "\n");
		}
		String structure = sb.toString();
		structure = StringUtil.replaceEx(structure, PubFun.INDENT, "  ");
		params.put("Structure", structure);
		return params;
	}

	public static Mapx initDialog(Mapx params) {
		params.put("SiteID", Application.getCurrentSiteID() + "");
		params.put("PublishFlag", HtmlUtil.codeToRadios("PublishFlag", "YesOrNo", "Y"));
		params.put("SingleFlagN", "checked");
		params.put("AllowContribute", HtmlUtil.codeToRadios("AllowContribute", "YesOrNo", "N"));
		params.put("CatalogType", HtmlUtil.codeToOptions("CatalogType"));
		params.put("DetailNameRule", "/${catalogpath}/${document.id}.shtml");
		params.put("ListNameRule", "/${catalogpath}/${year}/${month}/");
		params.put("ListTemplate", "/template/list.html");
		params.put("DetailTemplate", "/template/detail.html");
		params.put("ListPage", "-1");
		DataTable workflowDT = new QueryBuilder("select name,ID from ZWWorkflow").executeDataTable();
		params.put("Workflow", HtmlUtil.dataTableToOptions(workflowDT, true));
		return params;
	}

	public void getPicSrc() {
		String ID = $V("PicID");
		QueryBuilder qb = new QueryBuilder("select path,filename from zcimage where id=?", Long.parseLong(ID));
		DataTable dt = qb.executeDataTable();
		if (dt.getRowCount() > 0) {
			this.Response.put("picSrc", dt.get(0, "path").toString() + "1_" + dt.get(0, "filename").toString());
			this.Response.put("ImagePath", dt.get(0, "path").toString() + "1_" + dt.get(0, "filename").toString());
		}
	}

	public static void treeDataBind(TreeAction ta) {
		Object obj = ta.getParams().get("SiteID");
		long siteID = (obj != null) ? Long.parseLong(obj.toString()) : Application.getCurrentSiteID();
		Object typeObj = ta.getParams().get("CatalogType");
		int catalogType = (typeObj != null) ? Integer.parseInt(typeObj.toString()) : Catalog.CATALOG;
		String parentTreeLevel = ta.getParams().getString("ParentLevel");
		String parentID = ta.getParams().getString("ParentID");

		String IDs = ta.getParam("IDs");
		if (StringUtil.isEmpty(IDs)) {
			IDs = ta.getParam("Cookie.DocList.LastCatalog");
		}
		String[] codes = getSelectedCatalogList(IDs, CatalogShowConfig.getArticleCatalogShowLevel());

		DataTable dt = null;
		if (ta.isLazyLoad()) {
			QueryBuilder qb = new QueryBuilder(
					"select ID,ParentID,TreeLevel,Name,SingleFlag,InnerCode,OrderFlag from ZCCatalog"
							+ " Where Type = ? and SiteID = ? and TreeLevel>? and innerCode like ?");
			qb.add(catalogType);
			qb.add(siteID);
			qb.add(Long.parseLong(parentTreeLevel));
			qb.add(CatalogUtil.getInnerCode(parentID) + "%");
			if (!CatalogShowConfig.isArticleCatalogLoadAllChild()) {
				qb.append(" and TreeLevel<?", Integer.parseInt(parentTreeLevel) + 3);
				ta.setExpand(false);
			} else {
				ta.setExpand(true);
			}
			qb.append(" order by orderflag,innercode");
			dt = qb.executeDataTable();
		} else {
			ta.setLevel(CatalogShowConfig.getArticleCatalogShowLevel());
			QueryBuilder qb = new QueryBuilder(
					"select ID,ParentID,TreeLevel,Name,SingleFlag,InnerCode,OrderFlag from ZCCatalog"
							+ " Where Type = ? and SiteID = ? and TreeLevel-1 <=? order by orderflag,innercode");
			qb.add(catalogType);
			qb.add(siteID);
			qb.add(ta.getLevel());
			dt = qb.executeDataTable();
			prepareSelectedCatalogData(dt, codes, catalogType, siteID, ta.getLevel());
		}

		String siteName = "APPӦ��";
		if (catalogType == Catalog.SHOP_GOODS) {
			siteName = "��Ʒ��";
		} else if (catalogType == Catalog.SHOP_GOODS_BRAND) {
			siteName = "��ƷƷ�ƿ�";
		}
		dt = dt.filter(new Filter() {
			public boolean filter(Object obj) {
				DataRow dr = (DataRow) obj;
				return Priv.getPriv(User.getUserName(), Priv.ARTICLE, dr.getString("InnerCode"), Priv.ARTICLE_BROWSE);
			}
		});

		String inputType = (String) ta.getParams().get("Type");
		if ("3".equals(inputType)) {// ��ѡ
			ta.setRootText("<input type='radio' name=CatalogID id='_site' value='" + siteID + "'><label for='_site'>"
					+ siteName + "</label>");
		} else if ("2".equals(inputType)) {// ��ѡ��ȫѡ
			ta.setRootText("<input type='CheckBox' name=CatalogID id='_site' value='" + siteID
					+ "' onclick='selectAll()'><label for='_site'>" + siteName + "</label>");
		} else {
			ta.setRootText(siteName);
		}
		ta.bindData(dt);
		addSelectedBranches(ta, codes);
		List items = ta.getItemList();
		for (int i = 1; i < items.size(); i++) {
			TreeItem item = (TreeItem) items.get(i);
			if ("Y".equals(item.getData().getString("SingleFlag"))) {
				item.setIcon("Icons/treeicon11.gif");
			}
		}
	}

	/**
	 * �༭ʱҪ����IDԤ��ѡ�����ε�ĳ����ڵ�
	 */
	public static String[] getSelectedCatalogList(String IDs, int level) {
		Mapx map = new Mapx();
		if (StringUtil.isNotEmpty(IDs)) {
			String[] arr = IDs.split("\\,");
			for (int i = 0; i < arr.length; i++) {
				if (StringUtil.isNotEmpty(arr[i])) {
					String innerCode = CatalogUtil.getInnerCode(arr[i]);
					if (StringUtil.isNotEmpty(innerCode)) {
						if (innerCode.length() > level * 6) {
							innerCode = innerCode.substring(0, level * 6);
							map.put(innerCode, "1");
						}
					}
				}
			}
		}
		String[] codes = new String[map.size()];
		Object[] ks = map.keyArray();
		for (int i = 0; i < map.size(); i++) {
			codes[i] = ks[i].toString();
		}
		return codes;
	}

	/**
	 * �༭ʱҪ����IDԤ��ѡ�����ε�ĳ����ڵ�
	 */
	public static void prepareSelectedCatalogData(DataTable dt, String[] codes, int catalogType, long siteID, int level) {
		for (int i = 0; i < codes.length; i++) {
			String innerCode = codes[i];
			QueryBuilder qb = new QueryBuilder(
					"select ID,ParentID,TreeLevel,Name,SingleFlag,InnerCode,OrderFlag from ZCCatalog"
							+ " Where Type=? and SiteID=? and TreeLevel>? and InnerCode like ? "
							+ "order by orderflag,innercode");
			qb.add(catalogType);
			qb.add(siteID);
			qb.add(level + 1);
			qb.add(innerCode + "%");
			DataTable dt2 = qb.executeDataTable();
			dt.union(dt2);
		}
	}

	/**
	 * �༭ʱҪ����IDԤ��ѡ�����ε�ĳ����ڵ�
	 */
	public static void addSelectedBranches(TreeAction ta, String[] codes) {
		List list = ta.getItemList();
		for (int i = 0; i < list.size(); i++) {
			TreeItem item = (TreeItem) list.get(i);
			if (item.isRoot()) {
				continue;
			}
			for (int j = 0; j < codes.length; j++) {
				if (item.getData().getString("InnerCode").equals(codes[j].toString())) {
					item.setLazy(false);
					item.setExpanded(true);
					try {
						int level = ta.getLevel();
						ta.setLevel(1000);
						ta.addChild(item);
						ta.setLevel(level);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	// ��Ŀת��ʱѡ�����������ʾ��ҳ��Ŀ
	public static void treeDiagDataBind(TreeAction ta) {
		Object obj = ta.getParams().get("SiteID");
		long siteID = (obj != null) ? Long.parseLong(obj.toString()) : Application.getCurrentSiteID();
		Object typeObj = ta.getParams().get("CatalogType");
		int catalogType = (typeObj != null) ? Integer.parseInt(typeObj.toString()) : Catalog.CATALOG;
		String parentTreeLevel = ta.getParams().getString("ParentLevel");
		String parentID = ta.getParams().getString("ParentID");
		DataTable dt = null;

		String IDs = ta.getParam("IDs");
		String[] codes = getSelectedCatalogList(IDs, CatalogShowConfig.getArticleCatalogShowLevel());

		if (ta.isLazyLoad()) {
			QueryBuilder qb = new QueryBuilder(
					"select ID,ParentID,TreeLevel,Name,SingleFlag,InnerCode,OrderFlag from ZCCatalog"
							+ " Where Type=? and SiteID=? and TreeLevel>? and innerCode like ?");
			qb.add(catalogType);
			qb.add(siteID);
			qb.add(Long.parseLong(parentTreeLevel));
			qb.add(CatalogUtil.getInnerCode(parentID) + "%");
			if (!CatalogShowConfig.isArticleCatalogLoadAllChild()) {
				qb.append(" and TreeLevel<?", Integer.parseInt(parentTreeLevel) + 3);
				ta.setExpand(false);
			} else {
				ta.setExpand(true);
			}
			qb.append(" order by orderflag,innercode");
			dt = qb.executeDataTable();
		} else {
			ta.setLevel(CatalogShowConfig.getArticleCatalogShowLevel());
			QueryBuilder qb = new QueryBuilder(
					"select ID,ParentID,TreeLevel,Name,SingleFlag,InnerCode,OrderFlag from ZCCatalog"
							+ " Where Type=? and SiteID=? and TreeLevel-1<=? order by orderflag,innercode");
			qb.add(catalogType);
			qb.add(siteID);
			qb.add(ta.getLevel());
			dt = qb.executeDataTable();
			prepareSelectedCatalogData(dt, codes, catalogType, siteID, ta.getLevel());
		}
		dt = DataGridAction.sortTreeDataTable(dt, "ID", "ParentID");

		String siteName = "APPӦ��";
		if (catalogType == Catalog.SHOP_GOODS) {
			siteName = "��Ʒ��";
		}
		if (catalogType == Catalog.SHOP_GOODS_BRAND) {
			siteName = "��ƷƷ�ƿ�";
		}
		String inputType = (String) ta.getParams().get("Type");
		if ("3".equals(inputType)) {// ��ѡ
			ta.setRootText("<input type='radio' name=CatalogID id='_site' value='" + siteID + "'><label for='_site'>"
					+ siteName + "</label>");
		} else if ("2".equals(inputType)) {// ��ѡ��ȫѡ
			ta.setRootText("<input type='CheckBox' name=CatalogID id='_site' value='" + siteID
					+ "' onclick='selectAll()'><label for='_site'>" + siteName + "</label>");
		} else {
			ta.setRootText(siteName);
		}
		dt = dt.filter(new Filter() {
			public boolean filter(Object obj) {
				DataRow dr = (DataRow) obj;
				// ����ת�Ƹ���ʱ�����Կ��������������Ŀ
				return Priv.getPriv(User.getUserName(), Priv.ARTICLE, dr.getString("InnerCode"), Priv.ARTICLE_BROWSE);
			}
		});
		ta.bindData(dt);
		addSelectedBranches(ta, codes);
	}

	public static void treeResourceDataBind(TreeAction ta) {
		String siteID = ta.getParam("SiteID");
		siteID = (siteID != null) ? siteID.toString() : Application.getCurrentSiteID() + "";
		String catalogTypeStr = ta.getParam("CatalogType");
		if (catalogTypeStr == null) {
			catalogTypeStr = ta.getParams().getString("Cookie.ResourceCatalog.CatalogType");
		}
		int catalogType = (catalogTypeStr != null) ? Integer.parseInt(catalogTypeStr.toString()) : Catalog.IMAGELIB;
		if (Catalog.IMAGELIB == catalogType) {
			ImageLib.treeDataBind(ta);
		} else if (Catalog.VIDEOLIB == catalogType) {
			VideoLib.treeDataBind(ta);
		} else if (Catalog.AUDIOLIB == catalogType) {
			AudioLib.treeDataBind(ta);
		} else if (Catalog.ATTACHMENTLIB == catalogType) {
			AttachmentLib.treeDataBind(ta);
		}
	}

	public void dg1Edit() {
		DataTable dt = (DataTable) Request.get("DT");
		int count = dt.getRowCount();
		QueryBuilder qb = new QueryBuilder("update ZCCatalog set ");
		for (int i = 0; i < count; i++) {
			qb.append(dt.get(i, "InnerCode") + "=?", dt.get(i, "Value"));
		}
		if (count != 0) {
			qb.append(",");
		}
		qb.append("modifyuser = ?", User.getUserName());
		qb.append(",modifytime = ?", new Date());
		qb.append(" where ID = ?", dt.get(0, "ID_key"));
		if (qb.executeNoQuery() != -1) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("��������!");
		}
	}

	/**
	 * ����ֻ��������Ŀ,����Ҫע������һ����Ŀ�ͷ�һ����Ŀ�Ĳ�𣬸��µ��ϼ���Ϣ��һ��
	 */
	public void add() {
		Transaction trans = new Transaction();
		ZCCatalogSchema catalog = add(Request, trans);
		if (catalog != null && trans.commit()) {
			// ���»���
			CatalogUtil.update(catalog.getID());
			Priv.updateAllPriv(User.getUserName());
			UserLog.log(UserLog.CATALOG, UserLog.CATALOG_ADDCATALOG, "������Ŀ:" + catalog.getName() + "�ɹ���",
					Request.getClientIP());
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			UserLog.log(UserLog.CATALOG, UserLog.CATALOG_ADDCATALOG, "������Ŀ:" + Request.getString("Name") + "ʧ�ܣ�",
					Request.getClientIP());
			Response.setMessage("�����Ŀ��������" + Request.getString("ErrorMessage"));
		}
	}

	/**
	 * ������Ŀ
	 * 
	 * @param dc
	 * @param trans
	 */
	public ZCCatalogSchema add(DataCollection dc, Transaction trans) {
		// �����Ƿ����
		String alias = dc.getString("Alias").trim();
		String name = dc.getString("Name").trim();
		int treeLevel = 1;

		ZCCatalogSchema pCatalog = new ZCCatalogSchema();
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		String type = dc.getString("Type"); // Ƶ��,ר�⣬�ڿ�
		if (StringUtil.isEmpty(type) || "null".equals(type)) {
			type = Catalog.CATALOG + "";
		}

		catalog.setValue(dc);
		catalog.setAlias(alias);
		catalog.setName(name);
		String parentID = dc.getString("ParentID");
		// û�д���parentID��ʾ����һ����Ŀ�������Ƿ�һ����Ŀ
		if (parentID != null && !"0".equals(parentID) && !"null".equals(parentID)) {
			pCatalog.setID(Integer.parseInt(parentID));
			pCatalog.fill();

			treeLevel = (int) pCatalog.getTreeLevel() + 1;
			String existsName = checkAliasExists(alias, pCatalog.getID());
			if (StringUtil.isNotEmpty(existsName)) {
				dc.put("ErrorMessage", "��Ŀ��" + existsName + "���Ѿ�ʹ���˱���" + alias);
				return null;
			}

			if (checkNameExists(name, pCatalog.getID())) {
				dc.put("ErrorMessage", "��Ŀ���Ѿ���ʹ��");
				return null;
			}

			catalog.setParentID(pCatalog.getID());
			catalog.setSiteID(pCatalog.getSiteID());
			catalog.setTreeLevel(treeLevel);
			pCatalog.setChildCount(pCatalog.getChildCount() + 1);
			pCatalog.setIsLeaf(0);
			trans.add(pCatalog, Transaction.UPDATE);
		} else {
			ZCSiteSchema site = new ZCSiteSchema();
			site.setID(Application.getCurrentSiteID());
			site.fill();

			catalog.setParentID(0);
			catalog.setSiteID(site.getID());

			String existsName = checkAliasExists(alias, pCatalog.getID());
			if (StringUtil.isNotEmpty(existsName)) {
				dc.put("ErrorMessage", "��Ŀ��" + existsName + "���Ѿ�ʹ���˱���" + alias);
				return null;
			}

			if (checkNameExists(name, pCatalog.getID())) {
				dc.put("ErrorMessage", "��Ŀ���Ѿ���ʹ��");
				return null;
			}

			catalog.setTreeLevel(treeLevel);
			parentID = "0";

			if ("1".equals(type)) {
				site.setChannelCount(site.getChannelCount() + 1);
			} else if ("2".equals(type)) {
				site.setSpecialCount(site.getSpecialCount() + 1);
			} else if ("3".equals(type)) {
				site.setMagzineCount(site.getMagzineCount() + 1);
			}
			trans.add(site, Transaction.UPDATE);
		}

		String url = dc.getString("URL");
		if (url == null || "".equals(url) || "http://".equals($V("URL")) || "https://".equals($V("URL"))) {
			if (url == null) {
				url = "";// �����ڿ�URLǰ����null��bug
			}
			if (parentID != null && !"0".equals(parentID) && !"null".equals(parentID)) {
				url += CatalogUtil.getPath(catalog.getParentID());
			}
			url += alias + "/";
		}
		long catalogID = NoUtil.getMaxID("CatalogID");
		catalog.setID(catalogID);

		String innerCode = CatalogUtil.createCatalogInnerCode(pCatalog.getInnerCode());
		catalog.setInnerCode(innerCode);
		catalog.setBranchInnerCode(User.getBranchInnerCode());
		catalog.setURL(url);
		catalog.setType(type);
		catalog.setChildCount(0);
		catalog.setIsLeaf(1);
		catalog.setTotal(0);
		catalog.setHitCount(0);

		String orderFlag = getCatalogOrderFlag(parentID, type);
		catalog.setOrderFlag(Long.parseLong(orderFlag) + 1);

		String listPageSize = dc.getString("ListPageSize");
		if (StringUtil.isEmpty(listPageSize)) {
			catalog.setListPageSize(20);
		} else {
			catalog.setListPageSize(Integer.parseInt(listPageSize));
		}

		String listPage = dc.getString("ListPage");
		if (StringUtil.isEmpty(listPage)) {
			catalog.setListPage("-1");
		} else {
			catalog.setListPage(Integer.parseInt(listPage));
		}

		String publishFlag = dc.getString("PublishFlag");
		if (publishFlag != null && !"".equals(publishFlag)) {
			catalog.setPublishFlag(publishFlag);
		} else {
			catalog.setPublishFlag("Y");
		}

		if ("Y".equals(dc.get("SingleFlag"))) {
			catalog.setSingleFlag("Y");
		} else {
			catalog.setSingleFlag("N");
		}

		if ("Y".equals(dc.get("AllowContribute"))) {
			catalog.setAllowContribute("Y");
		} else {
			catalog.setAllowContribute("N");
		}

		catalog.setAddUser(User.getUserName());
		catalog.setAddTime(new Date());

		trans.add(catalog, Transaction.INSERT);
		trans.add(new QueryBuilder("update zccatalog set orderflag=orderflag+1 where orderflag>? and type=? and siteid=" 
				+ Application.getCurrentSiteID(), Long.parseLong(orderFlag), Long.parseLong(type)));

		initCatalogConfig(catalog, trans);
		initCatalogPriv(catalog, trans);
		return catalog;
	}

	public long add(ZCCatalogSchema parent, ZCCatalogSchema catalog, Transaction trans) {
		String type = catalog.getType() + ""; // Ƶ��,ר�⣬�ڿ�
		if (StringUtil.isEmpty(type) || "null".equals(type)) {
			type = Catalog.CATALOG + "";
		}
		// û�д���parentID��ʾ����һ����Ŀ�������Ƿ�һ����Ŀ
		if (parent != null) {
			catalog.setParentID(parent.getID());
			catalog.setSiteID(parent.getSiteID());
			catalog.setTreeLevel(parent.getTreeLevel() + 1);
			parent.setChildCount(parent.getChildCount() + 1);
			parent.setIsLeaf(0);
			trans.add((ZCCatalogSchema) parent.clone(), Transaction.UPDATE);
		} else {
			ZCSiteSchema site = new ZCSiteSchema();
			site.setID(Application.getCurrentSiteID());
			site.fill();

			catalog.setParentID(0);
			catalog.setSiteID(site.getID());
			catalog.setTreeLevel(1);

			if ("1".equals(type)) {
				site.setChannelCount(site.getChannelCount() + 1);
			} else if ("2".equals(type)) {
				site.setSpecialCount(site.getSpecialCount() + 1);
			} else if ("3".equals(type)) {
				site.setMagzineCount(site.getMagzineCount() + 1);
			}
			trans.add(site, Transaction.UPDATE);
		}

		String orderFlag = getCatalogOrderFlag(parent.getID(), type);
		catalog.setOrderFlag(Long.parseLong(orderFlag) + 1);

		long catalogID = NoUtil.getMaxID("CatalogID");
		catalog.setID(catalogID);

		String innerCode = CatalogUtil.createCatalogInnerCode(parent.getInnerCode());
		catalog.setInnerCode(innerCode);
		catalog.setBranchInnerCode(User.getBranchInnerCode());
		catalog.setAddUser(User.getUserName());
		catalog.setAddTime(new Date());

		trans.add(catalog, Transaction.INSERT);
		initCatalogConfig(catalog, trans);
		initCatalogPriv(catalog, trans);
		return catalogID;
	}

	public static void initCatalogConfig(ZCCatalogSchema catalog, Transaction trans) {
		ZCCatalogConfigSchema config = new ZCCatalogConfigSchema();
		config.setID(NoUtil.getMaxID("CatalogConfigID"));
		config.setAddTime(new Date());
		if (User.getCurrent() != null) {
			config.setAddUser(User.getUserName());
		} else {
			config.setAddUser("admin");
		}

		config.setStartTime(new Date());
		config.setArchiveTime("12");
		config.setAttachDownFlag("Y");
		config.setSiteID(catalog.getSiteID());
		config.setCatalogID(catalog.getID());
		config.setCatalogInnerCode(catalog.getInnerCode());
		config.setIsUsing("N");
		config.setPlanType("Period");
		String CommentVerify = "";
		if (SiteUtil.getCommentAuditFlag(catalog.getSiteID())) {
			CommentVerify = "Y";
		} else {
			CommentVerify = "N";
		}
		config.setCommentVerify(CommentVerify);
		config.setCommentAnonymous("N");
		config.setAllowComment("Y");
		Calendar c = Calendar.getInstance();
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), 22, 0, 0);
		StringBuffer sb = new StringBuffer();
		int minute = c.get(Calendar.MINUTE);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int day = c.get(Calendar.DAY_OF_MONTH);
		sb.append(minute);
		sb.append(" ");
		sb.append(hour);
		sb.append(" ");
		sb.append(day);
		sb.append("-");
		sb.append(day - 1);
		sb.append("/1");
		sb.append(" * *");
		config.setCronExpression(sb.toString());
		trans.add(config, Transaction.INSERT);
	}

	/**
	 * �û��½���Ŀʱ�Զ����и���Ŀ����ɾ�Ĳ�Ȩ��
	 * 
	 * @param catalog
	 * @param trans
	 */
	public static void initCatalogPriv(ZCCatalogSchema catalog, Transaction trans) {
		Mapx map = null;
		String type = "";
		if (catalog.getType() == Catalog.CATALOG || catalog.getType() == Catalog.MAGAZINE
				|| catalog.getType() == Catalog.NEWSPAPER) {
			map = Priv.ARTICLE_MAP;
			type = Priv.ARTICLE;
		} else if (catalog.getType() == Catalog.IMAGELIB) {
			map = Priv.IMAGE_MAP;
			type = Priv.IMAGE;
		} else if (catalog.getType() == Catalog.VIDEOLIB) {
			map = Priv.VIDEO_MAP;
			type = Priv.VIDEO;
		} else if (catalog.getType() == Catalog.AUDIOLIB) {
			map = Priv.AUDIO_MAP;
			type = Priv.AUDIO;
		} else if (catalog.getType() == Catalog.ATTACHMENTLIB) {
			map = Priv.ATTACH_MAP;
			type = Priv.ATTACH;
		} else {
			map = Priv.ARTICLE_MAP;
			type = Priv.ARTICLE;
		}
		Object[] keys = map.keyArray();
		for (int i = 0; i < keys.length; i++) {
			if (Priv.ARTICLE_AUDIT.equals(keys[i].toString()) || UserList.ADMINISTRATOR.equals(User.getUserName())) {
				continue;
			}
			ZDPrivilegeSchema priv = new ZDPrivilegeSchema();
			priv.setOwnerType(Priv.OWNERTYPE_USER);
			priv.setOwner(User.getUserName());
			priv.setID(catalog.getInnerCode());
			priv.setPrivType(type);
			priv.setCode(keys[i].toString());
			priv.setValue("1");
			trans.add(priv, Transaction.INSERT);
		}
	}

	public void save() {
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(Long.parseLong($V("ID")));
		catalog.fill();

		String name = $V("Name");
		if (!name.equals(catalog.getName()) && checkNameExists(name, catalog.getParentID())) {
			Response.setLogInfo(0, "��Ŀ����" + name + "�Ѿ����ڡ�");
			UserLog.log(UserLog.CATALOG, UserLog.CATALOG_UPDATECATALOG, "�޸���Ŀ:" + catalog.getName() + "ʧ�ܣ�",
					Request.getClientIP());
			return;
		}

		String alias = $V("Alias").trim();
		String existsName = checkAliasExists(alias, catalog.getParentID());
		if (!alias.equals(catalog.getAlias()) && StringUtil.isNotEmpty(existsName)) {
			UserLog.log(UserLog.CATALOG, UserLog.CATALOG_UPDATECATALOG, "�޸���Ŀ:" + catalog.getName() + "ʧ�ܣ�",
					Request.getClientIP());
			Response.setLogInfo(0, "��Ŀ��" + existsName + "����ʹ���˱���" + alias);
			return;
		}

		String oldWorkflow = catalog.getWorkflow();
		String keywordSetting = catalog.getKeywordSetting();
		catalog.setValue(Request);
		catalog.setModifyUser(User.getUserName());
		catalog.setModifyTime(new Date());

		if (StringUtil.isEmpty($V("ListPageSize"))) {
			catalog.setListPageSize(-1);
		}

		Transaction trans = new Transaction();

		// ��ĿΪ�ڿ�ʱ����������ͼͬʱ�����ںŵķ���ͼƬ
		if (catalog.getType() == Catalog.MAGAZINE) {
			ZCMagazineIssueSchema issue = new ZCMagazineIssueSchema();
			issue.setID($V("ID"));
			issue.fill();
			issue.setCoverImage(catalog.getImagePath());
			trans.add(issue, Transaction.UPDATE);
		}

		trans.add(catalog, Transaction.UPDATE);

		final String extend = $V("Extend");
		if ("1".equals(extend)) {
			// ����Ŀ

		} else if ("2".equals(extend)) { // ����Ŀ
			String innerCode = catalog.getInnerCode();
			QueryBuilder qb = new QueryBuilder(
					"update zccatalog set IndexTemplate=?,ListTemplate=?,DetailTemplate=?,rssTemplate=?"
							+ " where innercode like ? and TreeLevel>?");
			qb.add($V("IndexTemplate"));
			qb.add($V("ListTemplate"));
			qb.add($V("DetailTemplate"));
			qb.add($V("RssTemplate"));
			qb.add(innerCode + "%");
			qb.add(catalog.getTreeLevel());

			trans.add(qb);
		}

		// ����������
		final String wfExtend = $V("WorkFlowExtend");
		if ("1".equals(wfExtend)) {
			// ����Ŀ
			DataTable tempDT = new QueryBuilder("select distinct catalogid from zcarticle a where a.status="
					+ Article.STATUS_WORKFLOW + " and a.catalogid= ?", catalog.getID()).executeDataTable();
			if (tempDT.getRowCount() > 0) {
				if (StringUtil.isNotEmpty(oldWorkflow) && !oldWorkflow.equalsIgnoreCase(catalog.getWorkflow())) {
					Response.setLogInfo(0, "����Ŀ�»����ڡ���ת�С�������,���ܸ��Ĺ�����!");
					return;
				}
			}
		} else if ("2".equals(wfExtend)) {
			// ��������Ŀ
			DataTable tempDT = new QueryBuilder("select distinct catalogid from zcarticle a where a.status="
					+ Article.STATUS_WORKFLOW + " and a.cataloginnercode like ?", catalog.getInnerCode() + "%")
					.executeDataTable();
			if (tempDT.getRowCount() > 0) {
				String tempStr = "";
				for (int i = 0; i < tempDT.getRowCount(); i++) {
					if (!CatalogUtil.getWorkflow(tempDT.getString(i, 0)).equalsIgnoreCase(catalog.getWorkflow())) {
						tempStr += CatalogUtil.getName(tempDT.getString(i, 0)) + " ";
					}
				}
				if (StringUtil.isNotEmpty(tempStr)) {
					Response.setLogInfo(0, tempStr + "��Ŀ�»����ڡ���ת�С�������,���ܸ��Ĺ�����!");
					return;
				}
			}
			trans.add(new QueryBuilder("update zccatalog set workflow =? where innercode like ?",
					catalog.getWorkflow(), catalog.getInnerCode() + "%"));
		}

		if (trans.commit()) {
			final ZCCatalogSchema c = catalog;
			final String setting = keywordSetting;
			new Thread() {
				public void run() {
					//���»���
					CatalogUtil.update(c.getID());
					// ������������иı䣬��Ҫ����������Ŀ�����µ�����
					if ((setting == null && c.getKeywordSetting() != null) || !setting.equals(c.getKeywordSetting())) {
						ArticleRelaIndexer ari = new ArticleRelaIndexer();
						ari.updateCatalog(c.getID());
					}
					
					//���������Ŀ����ģ��͹��������ã�ͬʱ��������Ŀ����
					if ("2".equals(extend) || "2".equals(wfExtend)){
						QueryBuilder qb = new QueryBuilder(
								"select id from zccatalog where innercode like ? and TreeLevel>?",c.getInnerCode()+"%",c.getTreeLevel());
					    DataTable dt = qb.executeDataTable();
					    for (int i = 0; i < dt.getRowCount(); i++) {
					    	CatalogUtil.update(dt.getLong(i, "id"));
						}
					}
				}
			}.start();
			Response.setLogInfo(1, "����ɹ�!");
			UserLog.log(UserLog.CATALOG, UserLog.CATALOG_UPDATECATALOG, "�޸���Ŀ:" + catalog.getName() + "�ɹ���",
					Request.getClientIP());
		} else {
			UserLog.log(UserLog.CATALOG, UserLog.CATALOG_UPDATECATALOG, "�޸���Ŀ:" + catalog.getName() + "ʧ�ܣ�",
					Request.getClientIP());
			Response.setLogInfo(0, "����ʧ��!");
		}
	}

	public void saveTemplate() {
		Transaction trans = new Transaction();
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(Long.parseLong($V("ID")));
		catalog.fill();
		catalog.setValue(Request);
		catalog.setModifyUser(User.getUserName());
		catalog.setModifyTime(new Date());

		trans.add(catalog, Transaction.UPDATE);

		if (trans.commit()) {
			Response.setLogInfo(1, "����ģ��ɹ�");
		} else {
			Response.setLogInfo(0, "����ģ��ʧ��");
		}
	}

	/**
	 * ����������Ŀ���ɵ�Ԥ����Ŀ��,���û�и���Ŀ,���ڵ�Ϊxx��,����и���Ŀ,���ڵ�Ϊ����Ŀ����
	 * 
	 * @param ta
	 */
	public static void importTreeDataBind(TreeAction ta) {
		Object obj = ta.getParams().get("ParentID");
		long parentID = (obj != null) ? Long.parseLong(obj.toString()) : 0;

		String type = ta.getParams().get("Type") == null ? null : ta.getParams().get("Type").toString();
		String rootText = null;
		// �õ����ڵ�����
		if (parentID == 0L) {// ���û�и���Ŀ
			rootText = "APPӦ��";
			if (type != null && !"".equals(type) && !"null".equals(type)) {
				rootText = CatalogTypeMap.getString(type) + "��";
			}
		} else {// ����и���Ŀ
			rootText = new QueryBuilder("select Name from ZCCatalog where ID=?", parentID).executeString();
			rootText = rootText == null ? "" : rootText;
		}
		ta.setRootText(rootText);
		DataTable dt = getTreeDataTable(ta.getParam("BatchColumn"));
		ta.bindData(dt);
	}

	private static DataTable getTreeDataTable(String text) {
		String[] catalogs = text.split("\n");
		DataTable dt = new DataTable();
		DataColumn ID = new DataColumn("ID", DataColumn.BIGDECIMAL);
		DataColumn ParentID = new DataColumn("ParentID", DataColumn.BIGDECIMAL);
		DataColumn Level = new DataColumn("TreeLevel", DataColumn.INTEGER);
		DataColumn Name = new DataColumn("Name", DataColumn.STRING);
		dt.insertColumn(ID);
		dt.insertColumn(ParentID);
		dt.insertColumn(Level);
		dt.insertColumn(Name);
		for (int i = 0; i < catalogs.length; i++) {
			String catalog = catalogs[i];

			// �����ǰ��Ϊ�գ����˳�����ѭ��
			if (StringUtil.isEmpty(catalog.trim())) {
				continue;
			}
			catalog = StringUtil.toDBC(catalog);
			catalog = StringUtil.rightTrim(catalog);
			int length = 0;
			for (int k = 0; k < catalog.length(); k++) {
				if (catalog.charAt(k) != ' ') {
					length = k;
					break;
				}
			}
			int currentLevel = length / 2;
			Object[] catalogRow = { new Long(i + 1), new Long(0), new Integer(currentLevel), catalog.trim() };
			dt.insertRow(catalogRow);
		}
		for (int i = 0; i < dt.getRowCount(); i++) {
			int level = dt.getInt(i, "TreeLevel");
			int id = dt.getInt(i, "ID");
			for (int j = i + 1; j < dt.getRowCount(); j++) {
				if (dt.getInt(j, "TreeLevel") <= level) {
					break;
				}
				if (dt.getInt(j, "TreeLevel") == level + 1) {
					dt.set(j, "ParentID", id);
				}
			}
		}
		return dt;
	}

	/**
	 * ����������Ŀ�嵥
	 */
	public void importCatalog() {
		long parentID = Long.parseLong($V("ParentID"));
		String text = $V("BatchColumn");
		String type = $V("Type"); // Ƶ��,ר�⣬�ڿ�
		if (StringUtil.isEmpty(type) || "null".equals(type)) {
			type = Catalog.CATALOG + "";
		}
		Transaction trans = new Transaction();
		DataTable dt = getTreeDataTable(text);
		Mapx catalogMap = new Mapx();
		ZCSiteSchema site = new ZCSiteSchema();
		site.setID(Application.getCurrentSiteID());
		site.fill();
		if (parentID != 0) {
			ZCCatalogSchema catalog = new ZCCatalogSchema();
			catalog.setID(parentID);
			catalog.fill();
			catalogMap.put("0", catalog);
			trans.add(catalog, Transaction.UPDATE);
		} else {
			trans.add(site, Transaction.UPDATE);
		}
		String orderFlag = getCatalogOrderFlag(parentID, type);
		if (StringUtil.isEmpty(orderFlag)) {
			orderFlag = "0";
		}
		trans.add(new QueryBuilder("update zccatalog set orderflag=orderflag+" 
				+ dt.getRowCount() + " where orderflag>? and type=? and siteid=" 
				+ Application.getCurrentSiteID(), Long.parseLong(orderFlag), Long.parseLong(type)));

		for (int i = 0; i < dt.getRowCount(); i++) {
			ZCCatalogSchema catalog = new ZCCatalogSchema();
			String pid = dt.getString(i, "ParentID");
			ZCCatalogSchema pCatalog = (ZCCatalogSchema) catalogMap.get(pid);
			if (pCatalog == null) {
				pCatalog = new ZCCatalogSchema();// �ϼ���Ŀ��վ���Ŀ¼
			}
			String catalogName = dt.getString(i, "Name");
			// �����Ŀ�����Ƿ����
			if (checkNameExists(catalogName, pCatalog.getID())) {
				continue;
			}
			String alias = ChineseSpelling.getFirstAlpha(catalogName).toLowerCase();
			String existsName = checkAliasExists(alias, pCatalog.getID());
			if (StringUtil.isNotEmpty(existsName)) {
				alias += NoUtil.getMaxID("AliasNo");
			}
			String innerCode = null;
			catalog.setParentID(pCatalog.getID());
			catalog.setSiteID(site.getID());
			catalog.setTreeLevel(pCatalog.getTreeLevel() + 1);
			pCatalog.setChildCount(pCatalog.getChildCount() + 1);
			pCatalog.setIsLeaf(0);
			innerCode = pCatalog.getInnerCode();
			if (parentID == 0) {
				if ("1".equals(type)) {
					site.setChannelCount(site.getChannelCount() + 1);
				} else if ("2".equals(type)) {
					site.setSpecialCount(site.getSpecialCount() + 1);
				} else if ("3".equals(type)) {
					site.setMagzineCount(site.getMagzineCount() + 1);
				} else if ("4".equals(type)) {
					site.setImageLibCount(site.getMagzineCount() + 1);
				}
			}
			innerCode = CatalogUtil.createCatalogInnerCode(innerCode);
			catalog.setInnerCode(innerCode);

			catalog.setID(NoUtil.getMaxID("CatalogID"));
			catalog.setName(catalogName);
			catalog.setAlias(alias);
			String url = pCatalog.getURL();
			if (StringUtil.isEmpty(url)) {
				url = alias + "/";
			} else {
				if (!url.endsWith("/")) {
					url += "/";
				}
				url = url + alias + "/";
			}
			catalog.setURL(url);
			if (StringUtil.isNotEmpty(type) && !"null".equals(type)) {
				catalog.setType(Integer.parseInt(type));
			} else {
				catalog.setType(Catalog.CATALOG);
			}

			catalog.setListTemplate($V("ListTemplate"));
			catalog.setDetailTemplate($V("DetailTemplate"));
			catalog.setChildCount(0);
			catalog.setIsLeaf(1);
			catalog.setTotal(0);
			catalog.setLogo($V("Logo"));
			catalog.setListPageSize(20);
			catalog.setPublishFlag("Y");
			catalog.setOrderFlag(orderFlag);
			catalog.setSingleFlag("N");
			catalog.setKeywordFlag("N");
			catalog.setAllowContribute("N");
			catalog.setHitCount(0);
			catalog.setMeta_Keywords($V("Meta_Keywords"));
			catalog.setMeta_Description($V("Meta_Description"));
			catalog.setOrderColumn($V("OrderColumn"));
			catalog.setKeywordSetting("NO");
			catalog.setListPage("-1");
			catalog.setListPageSize(20);
			catalog.setProp1($V("Prop1"));
			catalog.setProp2($V("Prop2"));
			catalog.setProp3($V("Prop3"));
			catalog.setProp4($V("Prop4"));
			catalog.setAddUser(User.getUserName());
			catalog.setAddTime(new Date());

			catalogMap.put(dt.getString(i, "ID"), catalog);
			trans.add(catalog, Transaction.INSERT);
			initCatalogConfig(catalog, trans);
			initCatalogPriv(catalog, trans);
		}
		if (trans.commit()) {
			Priv.updateAllPriv(User.getUserName());
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("�������ݷ�������!");
		}
	}

	/**
	 * �ƶ���Ŀ��Ŀ����Ŀ
	 */
	public void move() {
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		long catalogID = Long.parseLong((String) $V("CatalogID"));
		long parentID = Long.parseLong((String) $V("ParentID"));
		catalog.setID(catalogID);
		catalog.fill();
		Transaction tran = new Transaction();

		long TreeLevel = 0;
		String innerCode, parentInnerCode = "";
		if (parentID == 0) {
			TreeLevel = 1;
			if (catalog.getTreeLevel() == 1) {
				Response.setStatus(0);
				Response.setMessage("ѡ�����Ŀ��Ŀ����Ŀ����ͬһ��������ת�ơ�");
				return;
			}

			ZCSiteSchema site = new ZCSiteSchema();
			site.setID(Application.getCurrentSiteID());
			site.fill();

			long type = catalog.getType(); // Ƶ��,ר�⣬�ڿ�
			if (type == Catalog.CATALOG) {
				site.setChannelCount(site.getChannelCount() + 1);
			} else if (type == Catalog.SUBJECT) {
				site.setSpecialCount(site.getSpecialCount() + 1);
			} else if (type == Catalog.MAGAZINE) {
				site.setMagzineCount(site.getMagzineCount() + 1);
			}
			tran.add(site, Transaction.UPDATE);
		} else {
			ZCCatalogSchema pCatalog = new ZCCatalogSchema();
			pCatalog.setID(parentID);
			pCatalog.fill();

			parentInnerCode = pCatalog.getInnerCode();
			TreeLevel = pCatalog.getTreeLevel() + 1;

			if (catalog.getTreeLevel() == TreeLevel && catalog.getParentID() == parentID) {
				Response.setStatus(0);
				Response.setMessage("ѡ�����Ŀ��Ŀ����Ŀ����ͬһ��������ת�ơ�");
				return;
			}

			pCatalog.setChildCount(pCatalog.getChildCount() + 1);
			pCatalog.setIsLeaf(0);
			tran.add(pCatalog, Transaction.UPDATE);
		}

		// �����ϼ�Ŀ¼��childcount
		long oldParentID = catalog.getParentID();
		if (oldParentID == 0) {
			ZCSiteSchema site = new ZCSiteSchema();
			site.setID(Application.getCurrentSiteID());
			site.fill();

			long type = catalog.getType(); // Ƶ��,ר�⣬�ڿ�
			if (type == Catalog.CATALOG) {
				site.setChannelCount(site.getChannelCount() - 1);
			} else if (type == Catalog.SUBJECT) {
				site.setSpecialCount(site.getSpecialCount() - 1);
			} else if (type == Catalog.MAGAZINE) {
				site.setMagzineCount(site.getMagzineCount() - 1);
			}
			tran.add(site, Transaction.UPDATE);
		} else {
			tran.add(new QueryBuilder("update zccatalog set childcount=childcount-1 where id=?", oldParentID));
			tran.add(new QueryBuilder("update zccatalog set isLeaf=1 where childcount=0 and id=?", oldParentID));
		}

		String orderflag = getCatalogOrderFlag(parentID, catalog.getType());
		innerCode = (TreeLevel == 1) ? CatalogUtil.createCatalogInnerCode("") : CatalogUtil
				.createCatalogInnerCode(parentInnerCode);

		// ����ͳ�Ʊ��е��ڲ�����
		CatalogStat.updateInnerCode(tran, catalog.getSiteID(), catalog.getInnerCode(), innerCode);

		catalog.setInnerCode(innerCode);
		catalog.setTreeLevel(TreeLevel);

		catalog.setParentID(parentID);
		catalog.setOrderFlag(getCatalogOrderFlag(parentID, catalog.getType()));
		catalog.setModifyUser(User.getUserName());
		catalog.setModifyTime(new Date());

		tran.add(catalog, Transaction.UPDATE);
		tran.add(new QueryBuilder("update zccatalog set orderflag = " + orderflag + 1 + " where id =?", catalogID));
		tran.add(new QueryBuilder("update zcarticle set CatalogInnerCode=? where catalogid=?", innerCode, catalogID));
		tran.add(new QueryBuilder("update bzcarticle set CatalogInnerCode=? where catalogid=?", innerCode, catalogID));

		Mapx map = new Mapx();
		if (catalog.getChildCount() > 0) {
			ZCCatalogSet childCatalogSet = new ZCCatalogSchema().query(new QueryBuilder("where parentid=?", catalogID));
			for (int i = 0; i < childCatalogSet.size(); i++) {
				Mapx childMap = moveChild(childCatalogSet.get(i), TreeLevel, innerCode, orderflag + 2 + i);
				map.putAll(childMap);
			}
		}

		Object[] ks = map.keyArray();
		for (int i = 0; i < map.size(); i++) {
			String sql = ks[i].toString();
			tran.add(new QueryBuilder(sql));
		}

		String count = new QueryBuilder("select count(*) from zccatalog where innercode like '"
				+ CatalogUtil.getInnerCode(catalogID) + "%' and type=" + catalog.getType()).executeString();
		tran.add(new QueryBuilder("update zccatalog set orderflag=" + orderflag + count + " where orderflag > "
				+ orderflag + " and type=" + catalog.getType() + " and innercode not like '"
				+ CatalogUtil.getInnerCode(catalogID) + "%'"));

		if (tran.commit()) {
			CatalogUtil.update(catalogID);
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("�������ݷ�������!");
		}
	}

	/*******************************************************************************************************************
	 * �ƶ���ǰ��Ŀ���ӽڵ�
	 * 
	 * @param catalog
	 * @param TreeLevel
	 * @param parentInnerCode
	 * @return
	 */
	public Mapx moveChild(ZCCatalogSchema catalog, long TreeLevel, String parentInnerCode, String orderflag) {
		Mapx map = new Mapx();
		long catalogID = catalog.getID();
		String innerCode;

		TreeLevel = TreeLevel + 1;

		innerCode = CatalogUtil.createCatalogInnerCode(parentInnerCode);

		map.put("update zccatalog set TreeLevel=" + TreeLevel + ",innercode='" + innerCode + "',modifyuser='"
				+ User.getUserName() + "',modifytime='" + DateUtil.getCurrentDate() + "',OrderFlag=" + orderflag
				+ " where id=" + catalogID, new Integer(Transaction.SQL));

		map.put("update zcarticle set CatalogInnerCode='" + innerCode + "' where catalogid=" + catalogID, new Integer(
				Transaction.SQL));
		map.put("update bzcarticle set CatalogInnerCode='" + innerCode + "' where catalogid=" + catalogID, new Integer(
				Transaction.SQL));

		if (catalog.getChildCount() > 0) {
			ZCCatalogSet childCatalogSet = new ZCCatalogSchema().query(new QueryBuilder("where parentid=?", catalogID));
			for (int i = 0; i < childCatalogSet.size(); i++) {
				Mapx childMap = moveChild(childCatalogSet.get(i), TreeLevel, innerCode, orderflag + 1 + i);
				map.putAll(childMap);
			}
		}

		return map;
	}

	public void publish() {
		long id;
		// վ��
		if ("0".equals($V("type"))) {
			id = publishAllTask(Application.getCurrentSiteID());
			Response.setStatus(1);
			$S("TaskID", "" + id);
		} else {
			long catalogID = Long.parseLong($V("CatalogID"));
			boolean childFlag = "Y".equals($V("ChildFlag"));
			boolean detailFlag = "Y".equals($V("DetailFlag"));
			id = publishTask(catalogID, childFlag, detailFlag);
			Response.setStatus(1);
			$S("TaskID", "" + id);
		}
	}

	private long publishTask(final long catalogID, final boolean child, final boolean detail) {
		LongTimeTask ltt = new LongTimeTask() {
			public void execute() {
				Publisher p = new Publisher();
				p.publishCatalog(catalogID, child, detail, this);
				setPercent(100);
			}
		};
		ltt.setUser(User.getCurrent());
		ltt.start();
		return ltt.getTaskID();
	}

	public long publishAllTask(final long siteID) {
		LongTimeTask ltt = new LongTimeTask() {
			public void execute() {
				Publisher p = new Publisher();
				p.publishAll(siteID, this);
				setPercent(100);
			}
		};
		ltt.setUser(User.getCurrent());
		ltt.start();
		return ltt.getTaskID();
	}

	/**
	 * ����ֻ��ɾ����Ŀ,����Ҫע��ɾ��һ����Ŀ�ͷ�һ����Ŀ�Ĳ�𣬸��µ��ϼ���Ϣ��һ��
	 */
	public void del() {
		Transaction trans = new Transaction();
		String ID = $V("CatalogID");
		String name = CatalogUtil.getName(ID);
		Catalog.deleteCatalog(trans, Long.parseLong(ID));
		if (trans.commit()) {
			CatalogUtil.update(ID);
			UserLog.log(UserLog.SITE, UserLog.CATALOG_DELCATALOG, "ɾ����Ŀ�ɹ�:" + name, Request.getClientIP());

			Response.setStatus(1);
		} else {
			UserLog.log(UserLog.SITE, UserLog.CATALOG_DELCATALOG, "ɾ����Ŀʧ��:" + name, Request.getClientIP());
			Response.setStatus(0);
			Response.setMessage("ɾ����Ŀʧ��");
		}
	}

	public void batchDel() {
		String IDs = $V("IDs");
		if (StringUtil.isEmpty(IDs) || IDs.equals("0")) {
			Response.setMessage("����ɾ��APPӦ�ø��ڵ�!");
			return;
		}
		String[] arr = IDs.split(",");
		Mapx map = new Mapx();
		for (int i = 0; i < arr.length; i++) {
			String id = arr[i];
			if (StringUtil.isNotEmpty(id) && !id.equals("0")) {
				String innerCode = CatalogUtil.getInnerCode(id);
				map.put(innerCode, id);
			}
		}
		Object[] ks = map.keyArray();
		for (int i = 0; i < ks.length; i++) {
			String innerCode = ks[i].toString();
			while (innerCode.length() > 6) {
				innerCode = innerCode.substring(0, innerCode.length() - 6);
				if (map.containsKey(innerCode)) {
					map.remove(ks[i]);
					break;
				}
			}
		}
		Transaction trans = new Transaction();
		Object[] catalogs = map.valueArray();
		for (int i = 0; i < catalogs.length; i++) {
			Catalog.deleteCatalog(trans, Long.parseLong(String.valueOf(catalogs[i])));
		}
		if (trans.commit()) {
			for (int i = 0; i < catalogs.length; i++) {
				String name = CatalogUtil.getName(String.valueOf(catalogs[i]));
				UserLog.log(UserLog.SITE, UserLog.CATALOG_DELCATALOG, "ɾ����Ŀ�ɹ�:" + name, Request.getClientIP());
				CatalogUtil.update(String.valueOf(catalogs[i]));
			}
			Response.setStatus(1);
			Response.setMessage("����ɾ����Ŀ�ɹ�");
		} else {
			UserLog.log(UserLog.SITE, UserLog.CATALOG_DELCATALOG, "����ɾ����Ŀʧ��", Request.getClientIP());
			Response.setStatus(0);
			Response.setMessage("����ɾ����Ŀʧ��");
		}
	}

	/**
	 * �����ĵ� ��ʱ�������£�δ����ͼƬ����Ƶ��
	 */
	public void clean() {
		Transaction trans = new Transaction();
		long catalogID = Long.parseLong($V("CatalogID"));
		trans.add(new QueryBuilder(
				"delete from zcarticlelog where exists(select id from zcarticle where id=zcarticlelog.articleid and catalogid=?)",
				catalogID));
		trans.add(new QueryBuilder("delete from zcarticle where catalogid=?", catalogID));

		// ɾ���Զ����ֶ�
		if (Config.isSybase()) {
			trans.add(new QueryBuilder(
					"delete from zdcolumnvalue where relatype=? "
							+ "and exists(select id from zcarticle where catalogid=? and convert(varchar,id)=zdcolumnvalue.relaid)",
					ColumnUtil.RELA_TYPE_DOCID, catalogID));
		} else {
			trans.add(new QueryBuilder("delete from zdcolumnvalue where relatype=? "
					+ "and exists(select id from zcarticle where catalogid=? and id=zdcolumnvalue.relaid)",
					ColumnUtil.RELA_TYPE_DOCID, catalogID));
		}

		// ɾ��ͼƬ����
		trans.add(new QueryBuilder("delete from zcimagerela where RelaType=? "
				+ "and exists(select id from zcarticle where catalogid=? and id=zcimagerela.RelaID)",
				Article.RELA_IMAGE, catalogID));

		// ɾ����������
		trans.add(new QueryBuilder("delete from ZCAttachmentRela where RelaType=? "
				+ "and exists(select id from zcarticle where catalogid=? and id=ZCAttachmentRela.RelaID)",
				Article.RELA_ATTACH, catalogID));

		// ɾ����Ƶ����
		trans.add(new QueryBuilder("delete from ZCVideoRela where RelaType=? "
				+ "and exists(select id from zcarticle where catalogid=? and id=ZCVideoRela.RelaID)",
				Article.RELA_VIDEO, catalogID));

		// ɾ����Ƶ����
		trans.add(new QueryBuilder("delete from ZCAudioRela where exists(select id from zcarticle "
				+ "where catalogid=? and id=ZCAudioRela.RelaID)", catalogID));

		// ɾ��ͶƱ
		trans.add(new QueryBuilder("delete from ZCVoteItem where exists(select id from zcarticle "
				+ "where catalogid=? and id=ZCVoteItem.VoteDocID)", catalogID));

		// ɾ������
		trans.add(new QueryBuilder("delete from ZCComment where exists(select id from zcarticle "
				+ "where catalogid=? and id=ZCComment.RelaID)", catalogID));

		if (trans.commit()) {
			Publisher p = new Publisher();
			FileUtil.delete(CatalogUtil.getAbsolutePath(catalogID));
			p.publishCatalog(catalogID, false, true);
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("�����Ŀʧ��");
		}
	}

	public static void deleteCatalog(Transaction trans, long ID) {
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(ID);
		catalog.fill();

		ZCCatalogSchema pCatalog = new ZCCatalogSchema();
		pCatalog.setID(catalog.getParentID());
		if (pCatalog.fill()) {
			pCatalog.setChildCount(pCatalog.getChildCount() - 1);
			if (pCatalog.getChildCount() == 0) {
				pCatalog.setIsLeaf(1);
			} else {
				pCatalog.setIsLeaf(0);
			}
			trans.add(pCatalog, Transaction.UPDATE);
		}

		ZCCatalogSet catalogSet = catalog.query(new QueryBuilder(" where InnerCode like ?", catalog.getInnerCode()
				+ "%"));
		trans.add(catalogSet, Transaction.DELETE_AND_BACKUP);
		trans.add(
				new ZCArticleSchema().query(new QueryBuilder(" where CatalogInnerCode like ?", catalog.getInnerCode()
						+ "%")), Transaction.DELETE_AND_BACKUP);
		trans.add(
				new ZCImageSchema().query(new QueryBuilder(" where CatalogInnerCode like ?", catalog.getInnerCode()
						+ "%")), Transaction.DELETE_AND_BACKUP);
		trans.add(
				new ZCVideoSchema().query(new QueryBuilder(" where CatalogInnerCode like ?", catalog.getInnerCode()
						+ "%")), Transaction.DELETE_AND_BACKUP);
		trans.add(
				new ZCAudioSchema().query(new QueryBuilder(" where CatalogInnerCode like ?", catalog.getInnerCode()
						+ "%")), Transaction.DELETE_AND_BACKUP);
		trans.add(
				new ZCAttachmentSchema().query(new QueryBuilder(" where CatalogInnerCode like ?", catalog
						.getInnerCode() + "%")), Transaction.DELETE_AND_BACKUP);
		trans.add(new ZCCatalogConfigSchema().query(new QueryBuilder(" where siteID=? and CatalogInnerCode like ?",
				catalog.getSiteID(), catalog.getInnerCode() + "%")), Transaction.DELETE_AND_BACKUP);

		String ids = "";
		for (int i = 0; i < catalogSet.size(); i++) {
			ids += catalogSet.get(i).getID();
			if (i != catalogSet.size() - 1) {
				ids += ",";
			}

			// ɾ��Ŀ¼
			FileUtil.delete(CatalogUtil.getAbsolutePath(catalogSet.get(i).getID()));
		}
		ZCPageBlockSet blockSet = new ZCPageBlockSchema().query(new QueryBuilder(" where catalogid in (" + ids + ")"));
		for (int i = 0; i < blockSet.size(); i++) {
			ZCPageBlockItemSet itemSet = new ZCPageBlockItemSchema().query(new QueryBuilder(" where blockID=?",
					blockSet.get(i).getID()));
			trans.add(itemSet, Transaction.DELETE_AND_BACKUP);
		}
		trans.add(blockSet, Transaction.DELETE_AND_BACKUP);

		// ɾ����Ŀ��������Ŀ���Զ����ֶ�zdcolumnrela,zdcolumnvalue
		String idsStr = "'" + ids.replaceAll(",", "','") + "'";// relaIDΪ�ַ����ֶ�
		// ɾ����Ŀ������zdcolumnrela������,��Ŀ���ĵ��Զ����ֶ�
		ZDColumnRelaSet columnRelaSet = new ZDColumnRelaSchema().query(new QueryBuilder(
				" where RelaType=? and RelaID in(" + idsStr + ")", ColumnUtil.RELA_TYPE_CATALOG_COLUMN));
		trans.add(columnRelaSet, Transaction.DELETE_AND_BACKUP);
		// ɾ����Ŀ���������zdcolumnvalue����,��Ŀ����չ�ֶ�
		ZDColumnValueSet columnValueSet1 = new ZDColumnValueSchema().query(new QueryBuilder(
				" where RelaType=? and RelaID in(" + idsStr + ")", ColumnUtil.RELA_TYPE_CATALOG_COLUMN));
		trans.add(columnValueSet1, Transaction.DELETE_AND_BACKUP);
		// ɾ����Ŀ�����¹�����zdcolumnvalue����
		String wherepart = " where exists (select ID from zcarticle where cataloginnercode like '"
				+ catalog.getInnerCode() + "%' and ID=zdcolumnvalue.relaID )";
		if (Config.isDB2()) {
			wherepart = " where exists (select ID from zcarticle where cataloginnercode like '"
					+ catalog.getInnerCode() + "%' and char(ID)=zdcolumnvalue.relaID )";
		} else if (Config.isSybase()) {
			wherepart = " where exists (select ID from zcarticle where cataloginnercode like '"
					+ catalog.getInnerCode() + "%' and convert(varchar,ID)=zdcolumnvalue.relaID )";
		}
		ZDColumnValueSet columnValueSet2 = new ZDColumnValueSchema().query(new QueryBuilder(wherepart));
		trans.add(columnValueSet2, Transaction.DELETE_AND_BACKUP);

		// ����ɾ���ڿ��ںŶ�Ӧ����Ŀʱ��ZCMagazine��Ӧ�ı�û��ɾ��������
		if (catalog.getType() == Catalog.MAGAZINE) {
			// ɾ���ڿ����ں�ʱ��ͬʱ�޸ĸ��ڿ����ֺ������͵�ǰ�ں�
			DataTable delids = new QueryBuilder("select * from ZCMagazineIssue where ID not in (" + ids
					+ ") order by Year desc,PeriodNum desc").executeDataTable();
			if (delids != null && delids.getRowCount() > 0) {
				String issueid = delids.getString(0, "ID");

				String[] idsarry = ids.split(",");
				boolean curentyearflag = false;
				ZCMagazineIssueSchema issue = new ZCMagazineIssueSchema();
				issue.setID(idsarry[0]);
				issue.fill();

				ZCMagazineSchema magazine = new ZCMagazineSchema();
				magazine.setID(issue.getMagazineID());
				magazine.fill();

				String currentyear = magazine.getCurrentYear();
				String periodnum = magazine.getCurrentPeriodNum();
				String coverimage = magazine.getCoverImage();
				if (magazine.fill()) {
					ZCMagazineIssueSchema issues = new ZCMagazineIssueSchema();
					issues.setID(issueid);
					issues.fill();

					if (currentyear.equalsIgnoreCase(issues.getYear())
							&& periodnum.equalsIgnoreCase(issues.getPeriodNum())) {
						curentyearflag = true;
					} else {
						currentyear = issues.getYear();
						periodnum = issues.getPeriodNum();
						coverimage = issues.getCoverImage();
					}

					if (curentyearflag) {
						magazine.setTotal(magazine.getTotal() - idsarry.length);
						magazine.setModifyTime(new Date());
						magazine.setModifyUser(User.getUserName());
						trans.add(magazine, Transaction.UPDATE);
					} else {
						magazine.setTotal(magazine.getTotal() - idsarry.length);
						magazine.setCurrentYear(currentyear);
						magazine.setCurrentPeriodNum(periodnum);
						magazine.setCoverImage(coverimage);
						magazine.setModifyTime(new Date());
						magazine.setModifyUser(User.getUserName());
						trans.add(magazine, Transaction.UPDATE);
					}
				}
				trans.add(new ZCMagazineSchema().query(new QueryBuilder("where ID in (" + ids + ")")),
						Transaction.DELETE_AND_BACKUP);
				trans.add(new ZCMagazineIssueSchema().query(new QueryBuilder("where ID in (" + ids + ")")),
						Transaction.DELETE_AND_BACKUP);
			}
		}
	}

	public static void createDefaultCatalog(String siteID, int type) {
		createDefaultCatalog(Long.parseLong(siteID), type);
	}

	public static void createDefaultCatalog(long siteID, int type) {
		DataTable dt = new QueryBuilder("select id from zccatalog where siteid =?  and type=?", siteID, type)
				.executePagedDataTable(1, 0);
		if (dt != null && dt.getRowCount() > 0) {
			return;
		}
		Transaction trans = new Transaction();
		// ��ý��⽨Ĭ��ר��
		ZCCatalogSchema defaultCatalog = new ZCCatalogSchema();
		defaultCatalog.setID(NoUtil.getMaxID("CatalogID"));
		defaultCatalog.setSiteID(siteID);
		defaultCatalog.setParentID(0);
		defaultCatalog.setInnerCode(CatalogUtil.createCatalogInnerCode(""));
		defaultCatalog.setTreeLevel(1);
		String Name = "";
		switch (type) {
		case 4:
			Name = "Ĭ��ͼƬ";
			break;
		case 5:
			Name = "Ĭ����Ƶ";
			break;
		case 6:
			Name = "Ĭ����Ƶ";
			break;
		case 7:
			Name = "Ĭ�ϸ���";
			break;
		default:
			Name = "Ĭ��ͼƬ";
			break;
		}
		defaultCatalog.setName(Name);
		defaultCatalog.setURL("");
		defaultCatalog.setAlias(StringUtil.getChineseFirstAlpha(Name));
		defaultCatalog.setType(type);
		defaultCatalog.setListTemplate("");
		defaultCatalog.setListNameRule("/${catalogpath}/${year}/${month}/");
		defaultCatalog.setDetailTemplate("");
		defaultCatalog.setDetailNameRule("");
		defaultCatalog.setChildCount(0);
		defaultCatalog.setIsLeaf(1);
		defaultCatalog.setTotal(0);
		defaultCatalog.setOrderFlag(getCatalogOrderFlag(0, type));
		defaultCatalog.setLogo("");
		defaultCatalog.setListPageSize(20);
		defaultCatalog.setListPage(-1);
		defaultCatalog.setPublishFlag("Y");
		defaultCatalog.setHitCount(0);
		defaultCatalog.setMeta_Keywords("");
		defaultCatalog.setMeta_Description("");
		defaultCatalog.setOrderColumn("");
		defaultCatalog.setProp4("Y");
		defaultCatalog.setAddUser(User.getUserName());
		defaultCatalog.setAddTime(new Date());
		trans.add(defaultCatalog, Transaction.INSERT);
		trans.commit();
	}

	/**
	 * ����Ŀ��������
	 */

	public void sortCatalog() {
		String CatalogType = Request.get("CatalogType").toString();
		int Move = Integer.parseInt(Request.get("Move").toString());
		String CatalogID = Request.get("CatalogID").toString();
		if (sortCatalog(CatalogID, Move, CatalogType)) {
			this.Response.setLogInfo(1, "����ɹ���");
		} else {
			this.Response.setLogInfo(0, "����ʧ�ܣ�");
		}
	}

	public static boolean sortCatalog(String CatalogID, int Move, String CatalogType) {
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(CatalogID);
		if (!catalog.fill()) {
			return true;
		}

		String TreeLevel = catalog.getTreeLevel() + "";
		//ƽ����Ŀ��
		
		// ������Ŀ��
		DataTable allDT = new QueryBuilder("select * from zccatalog where siteID = ? and Type =?"
				+ " order by orderflag,innercode", Application.getCurrentSiteID(), Long.parseLong(CatalogType))
				.executeDataTable();
		// ������Ŀ��
		int selfSize = new QueryBuilder(
				"select count(*) from zccatalog where siteID = " + Application.getCurrentSiteID()
						+ " and innercode like '" + catalog.getInnerCode() + "%' and Type = ?",
				Long.parseLong(CatalogType)).executeInt();
		List catalogList = new ArrayList();
		if (Move > 0) {
			int count = 0;
			int index = 0;
			boolean isMove = false;
			for (int i = allDT.getRowCount() - 1; i >= 0; i--) {
				if (isMove && allDT.getString(i, "ParentID").equals(catalog.getParentID() + "")) {
					count++;
				}
				
				if (allDT.getString(i, "ID").equals(CatalogID)) {
					index = i;
					isMove = true;
				} else if (allDT.getString(i, "InnerCode").startsWith(catalog.getInnerCode())) {
					continue;
				} else {
					catalogList.add(allDT.getDataRow(i));
				}
				// ���������1����������2��û�����굫�������ϼ��ڵ�3��û�����굫�����˸��ڵ�
				if (count == Move
						|| (count != 0 && i>0 && (count < Move) && Integer.parseInt(allDT.getString(i - 1,
								"TreeLevel")) < Integer.parseInt(TreeLevel))
						|| (count != 0 && (count < Move) && i == 0)) {
					for (int j = index + selfSize - 1; j >= index; j--) {
						catalogList.add(allDT.getDataRow(j));
					}
					Move = -1;
				}
			}
			if (count > 0) {
				Transaction trans = new Transaction();
				for (int i = 0; i < catalogList.size(); i++) {
					DataRow dr = (DataRow) catalogList.get(i);
					trans.add(new QueryBuilder("update zccatalog set orderflag = ? where ID = ?", catalogList.size()
							- i, dr.getLong("ID")));
				}
				return trans.commit();
			}
		} else {
			Move = -Move;
			int count = 0;
			int index = 0;
			boolean isMove = false;
			for (int i = 0; i < allDT.getRowCount(); i++) {
				if (isMove && allDT.getString(i, "ParentID").equals(catalog.getParentID() + "")) {
					count++;
				}
				
				if (allDT.getString(i, "ID").equals(CatalogID)) {
					index = i;
					i = i - 1 + selfSize;
					isMove = true;
				} else {
					catalogList.add(allDT.getDataRow(i));
				}
				// ���������1����������2��û�����굫��������һ���߼���Ľڵ�3��û�����겢��ȫ��ѭ������
				if (count == Move
						|| (count != 0 && ((i + 1) < allDT.getRowCount()) && (count < Move) && Integer.parseInt(allDT
								.getString(i + 1, "TreeLevel")) < Integer.parseInt(TreeLevel))
						|| (count != 0 && (count < Move) && i == (allDT.getRowCount() - 1))) {
					for (int j = index; j < index + selfSize; j++) {
						catalogList.add(allDT.getDataRow(j));
					}
					Move = -1;
				}
			}
			if (count > 0) {
				Transaction trans = new Transaction();
				for (int i = 0; i < catalogList.size(); i++) {
					DataRow dr = (DataRow) catalogList.get(i);
					trans.add(new QueryBuilder("update zccatalog set orderflag = ? where ID = ?", i + 1, dr
							.getLong("ID")));
				}
				return trans.commit();
			}
		}
		return true;
	}

	public static String getCatalogOrderFlag(long ParentID, long CatalogType) {
		return getCatalogOrderFlag(ParentID + "", CatalogType + "");
	}

	public static String getCatalogOrderFlag(String ParentID, long CatalogType) {
		return getCatalogOrderFlag(ParentID, CatalogType + "");
	}

	public static String getCatalogOrderFlag(long ParentID, String CatalogType) {
		return getCatalogOrderFlag(ParentID + "", CatalogType);
	}

	/**
	 * ����Ƿ������ͬ��alias����ֹͬ��Ŀ¼
	 * 
	 * @param alias
	 * @return
	 */
	public static String checkAliasExists(String alias, long parentID) {
		return checkAliasExists(alias, Application.getCurrentSiteID(), parentID);
	}

	public static String checkAliasExists(String alias, long siteID, long parentID) {
		QueryBuilder qb = new QueryBuilder("select name from zccatalog where alias=? and parentID=? and siteID=?",
				alias, parentID);
		qb.add(siteID);
		return qb.executeString();
	}

	/**
	 * ���ͬ����Ŀ�Ƿ������ͬ���Ƶ���Ŀ
	 * 
	 * @param Name
	 * @param parentID
	 * @return
	 */
	public static boolean checkNameExists(String Name, long parentID) {
		int count = new QueryBuilder("select count(*) from zccatalog where name=? and parentID=? and siteID="
				+ Application.getCurrentSiteID(), Name, parentID).executeInt();
		return count > 0 ? true : false;
	}

	public static String getCatalogOrderFlag(String ParentID, String CatalogType) {
		DataTable parentDT = null;
		if (StringUtil.isEmpty(ParentID) || "0".equals(ParentID)) {
			parentDT = new QueryBuilder("select * from zccatalog where siteID =? and type =? and TreeLevel = 1 "
					+ "order by orderflag,innercode", Application.getCurrentSiteID(), Long.parseLong(CatalogType))
					.executeDataTable();
		} else {
			String innercode = CatalogUtil.getInnerCode(ParentID);
			parentDT = new QueryBuilder("select * from zccatalog where siteID =? and type =? and innercode like '"
					+ innercode + "%' order by orderflag,innercode", Application.getCurrentSiteID(),
					Long.parseLong(CatalogType)).executeDataTable();
		}
		if (parentDT != null && parentDT.getRowCount() > 0) {
			return parentDT.getString(parentDT.getRowCount() - 1, "OrderFlag");
		} else {
			return "0";
		}
	}

	public static void main(String[] args) {
		ZCCatalogSet set = new ZCCatalogSchema().query(new QueryBuilder("where siteid=14 order by id"));
		for (int k = 1; k < 30; k++) {
			for (int i = 0; i < set.size(); i++) {
				ZCCatalogSchema schema = (ZCCatalogSchema) set.get(i).clone();
				if (schema.getTreeLevel() == 1) {
					schema.setInnerCode(StringUtil.leftPad(
							(String.valueOf(Integer.parseInt(schema.getInnerCode().substring(0, 4)) + set.size() * k
									/ 3)), '0', 4));
				} else if (schema.getTreeLevel() == 2) {
					schema.setInnerCode((StringUtil.leftPad(
							(String.valueOf(Integer.parseInt(schema.getInnerCode().substring(0, 4)) + set.size() * k
									/ 3)), '0', 4))
							+ "0001");
				} else if (schema.getTreeLevel() == 3) {
					schema.setInnerCode((StringUtil.leftPad(
							(String.valueOf(Integer.parseInt(schema.getInnerCode().substring(0, 4)) + set.size() * k
									/ 3)), '0', 4))
							+ "00010001");
				}

				schema.setID(schema.getID() + set.size() * k);
				if (schema.getParentID() != 0) {
					schema.setParentID(schema.getParentID() + set.size() * k);
				}
				schema.insert();
			}
		}
	}

}
