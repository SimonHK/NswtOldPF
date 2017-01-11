package com.nswt.cms.resource;

import java.io.File;
import java.util.Date;

import com.nswt.cms.datachannel.Publisher;
import com.nswt.cms.dataservice.ColumnUtil;
import com.nswt.cms.pub.CMSCache;
import com.nswt.cms.pub.CatalogUtil;
import com.nswt.cms.pub.SiteUtil;
import com.nswt.cms.site.Catalog;
import com.nswt.cms.site.CatalogShowConfig;
import com.nswt.framework.Config;
import com.nswt.framework.Page;
import com.nswt.framework.User;
import com.nswt.framework.controls.DataGridAction;
import com.nswt.framework.controls.TreeAction;
import com.nswt.framework.data.DataRow;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.ChineseSpelling;
import com.nswt.framework.utility.FileUtil;
import com.nswt.framework.utility.Filter;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.Application;
import com.nswt.platform.Priv;
import com.nswt.platform.UserLog;
import com.nswt.platform.pub.NoUtil;
import com.nswt.platform.pub.OrderUtil;
import com.nswt.schema.ZCAttachmentRelaSchema;
import com.nswt.schema.ZCAttachmentRelaSet;
import com.nswt.schema.ZCAttachmentSchema;
import com.nswt.schema.ZCAttachmentSet;
import com.nswt.schema.ZCCatalogSchema;
import com.nswt.schema.ZCCatalogSet;
import com.nswt.schema.ZCSiteSchema;

/**
 * 
 * @author Xuzhe
 * 
 */
public class AttachmentLib extends Page {

	public static Mapx initEditDialog(Mapx params) {
		long ID = Long.parseLong(params.get("ID").toString());
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(ID);
		catalog.fill();
		params = catalog.toMapx();
		return params;
	}

	public void getPicSrc() {
		String ID = $V("PicID");
		String id = $V("ID");
		DataTable dt = new QueryBuilder("select path,filename from zcimage where id=?", Long.parseLong(ID)).executeDataTable();
		if (dt.getRowCount() > 0) {
			this.Response.put("picSrc", Config.getContextPath() + Config.getValue("UploadDir") + "/"
					+ SiteUtil.getAlias(Application.getCurrentSiteID()) + "/" + dt.get(0, "path").toString() + "s_"
					+ dt.get(0, "filename").toString());
			this.Response.put("ImagePath", dt.get(0, "path").toString() + "1_" + dt.get(0, "filename").toString());
		}
		Transaction trans = new Transaction();
		ZCAttachmentSchema attach = new ZCAttachmentSchema();
		if (StringUtil.isNotEmpty(id)) {
			attach.setID(id);
			attach.fill();
			attach.setValue(this.Request);
			attach.setImagePath((String) this.Response.get("ImagePath"));
			trans.add(attach, Transaction.UPDATE);
			trans.commit();
		} else {
			return;
		}
	}

	public static void saveCustomColumn(Transaction trans, Mapx map, long catalogID, long articleID, boolean newFlag) {
		DataTable columnDT = ColumnUtil.getColumnValue(ColumnUtil.RELA_TYPE_DOCID, articleID);
		if (columnDT.getRowCount() > 0) {
			trans.add(new QueryBuilder("delete from zdcolumnvalue where relatype=? and relaid = ?",
					ColumnUtil.RELA_TYPE_DOCID, articleID+""));
		}
		trans.add(ColumnUtil.getValueFromRequest(catalogID, articleID, map), Transaction.INSERT);
	}

	public void AttachmentLibEdit() {
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setValue(this.Request);
		catalog.fill();

		String name = $V("Name");
		if (!name.equals(catalog.getName()) && Catalog.checkNameExists(name, catalog.getParentID())) {
			Response.setLogInfo(0, "分类名称" + name + "已经存在。");
			return;
		}

		// String alias = $V("Alias");
		// String existsName = Catalog.checkAliasExists(alias,
		// catalog.getParentID());
		// if (!alias.equals(catalog.getAlias()) &&
		// StringUtil.isNotEmpty(existsName)) {
		// Response.setLogInfo(0, "分类“" + existsName + "”已使用了别名" + alias + "。");
		// return;
		// }

		catalog.setValue(this.Request);
		catalog.setAlias(StringUtil.getChineseFirstAlpha(catalog.getName()));
		if (catalog.update()) {
			CatalogUtil.update(catalog.getID());
			this.Response.setLogInfo(1, "修改附件分类成功！");
		} else {
			this.Response.setLogInfo(0, "修改附件分类失败！");
		}
	}

	public static void dg1DataBindBrowse(DataGridAction dga) {
		String catalogID = (String) dga.getParams().get("CatalogID");
		String Name = (String) dga.getParams().get("Name");
		QueryBuilder qb = new QueryBuilder("select * from ZCAttachment ");
		if ("".equals(catalogID) || "0".equals(catalogID)) {
			qb.append(" where SiteID=?", Application.getCurrentSiteID());
		} else {
			qb.append(" where CatalogID=?", Long.parseLong(catalogID));
		}
		if (StringUtil.isNotEmpty(Name)) {
			qb.append(" and Name like ?", "%" + Name.trim() + "%");
		}

		int pageSize = dga.getPageSize();
		int pageIndex = dga.getPageIndex();
		qb.append(" order by ID desc");
		DataTable dt = qb.executePagedDataTable(pageSize, pageIndex);
		dt.insertColumn("SuffixImage");
		dt.insertColumn("AttachmentLink");

		for (int i = 0; i < dt.getRowCount(); i++) {
			String suffix = dt.get(i, "Suffix") + "";
			String[] ext = { "jpg", "gif", "zip", "rar", "bmp", "png", "doc", "xls", "html", "js", "mov", "mp4", "flv",
					"rm", "wmv", "swf", "txt", "mp3", "avi", "ppt", "pdf", "pptx", "xlsx", "docx" };
			for (int j = 0; j < ext.length; j++) {
				if (ext[j].equalsIgnoreCase(suffix)) {
					dt.set(i, "SuffixImage", "<img src='../Framework/Images/FileType/" + ext[j]
							+ ".gif' width='16' height='16' title='" + suffix + "'/>");
					break;
				}
			}
			if (dt.get(i, "SuffixImage") == null) {
				dt.set(i, "SuffixImage",
						"<img src='../Framework/Images/FileType/unknown.gif' width='16' height='16' title='" + suffix
								+ "'/>");
			}

			if ("N".equals(SiteUtil.getAttachDownFlag(Application.getCurrentSiteID()))) {
				dt.set(i, "AttachmentLink", (Config.getContextPath() + Config.getValue("UploadDir") + "/"
						+ Application.getCurrentSiteAlias() + "/" + dt.getString(i, "Path") + dt.getString(i,
						"filename")).replaceAll("//", "/"));
			} else {
				dt.set(i, "AttachmentLink", (Config.getContextPath() + "/Services/AttachDownLoad.jsp?id=" + dt
						.getString(i, "ID")).replaceAll("//", "/"));
			}
		}
		dga.setTotal(qb);
		dga.bindData(dt);
	}

	// Flash浏览窗口
	public static void dg1DataBindFlashBrowse(DataGridAction dga) {
		String catalogID = (String) dga.getParams().get("CatalogID");
		String Name = (String) dga.getParams().get("Name");
		QueryBuilder qb = new QueryBuilder("select * from ZCAttachment ");
		if ("".equals(catalogID) || "0".equals(catalogID)) {
			qb.append(" where SiteID =?", Application.getCurrentSiteID());
		} else {
			qb.append(" where CatalogID = ?", Long.parseLong(catalogID));
		}
		if (StringUtil.isNotEmpty(Name)) {
			qb.append(" and Name like ?", "%" + Name.trim() + "%");
		}
		qb.append(" and Suffix='swf' order by ID desc");
		int pageSize = dga.getPageSize();
		int pageIndex = dga.getPageIndex();
		DataTable dt = qb.executePagedDataTable(pageSize, pageIndex);
		dt.insertColumn("SuffixImage");
		dt.insertColumn("AttachmentLink");

		for (int i = 0; i < dt.getRowCount(); i++) {
			String suffix = dt.get(i, "Suffix") + "";
			String[] ext = { "jpg", "gif", "zip", "rar", "bmp", "png", "doc", "xls", "html", "js", "mov", "mp4", "flv",
					"rm", "wmv", "swf", "txt", "mp3", "avi", "ppt", "pdf", "pptx", "xlsx", "docx" };
			for (int j = 0; j < ext.length; j++) {
				if (ext[j].equalsIgnoreCase(suffix)) {
					dt.set(i, "SuffixImage", "<img src='../Framework/Images/FileType/" + ext[j]
							+ ".gif' width='16' height='16' title='" + suffix + "'/>");
					break;
				}
			}
			if (dt.get(i, "SuffixImage") == null) {
				dt.set(i, "SuffixImage",
						"<img src='../Framework/Images/FileType/unknown.gif' width='16' height='16' title='" + suffix
								+ "'/>");
			}

			if ("N".equals(SiteUtil.getAttachDownFlag(Application.getCurrentSiteID()))) {
				dt.set(i, "AttachmentLink", (Config.getContextPath() + Config.getValue("UploadDir") + "/"
						+ Application.getCurrentSiteAlias() + "/" + dt.getString(i, "Path") + dt.getString(i,
						"filename")).replaceAll("//", "/"));
			} else {
				dt.set(i, "AttachmentLink", (Config.getContextPath() + "/Services/AttachDownLoad.jsp?id=" + dt
						.getString(i, "ID")).replaceAll("//", "/"));
			}
		}
		dga.setTotal(qb);
		dga.bindData(dt);
	}

	public static void treeDataBind(TreeAction ta) {
		long SiteID = Application.getCurrentSiteID();
		DataTable dt = null;
		Mapx params = ta.getParams();
		String parentLevel = (String) params.get("ParentLevel");
		String parentID = (String) params.get("ParentID");

		String IDs = ta.getParam("IDs");
		if (StringUtil.isEmpty(IDs)) {
			IDs = ta.getParam("Cookie.Resource.LastAttachLib");
		}
		String[] codes = Catalog.getSelectedCatalogList(IDs, CatalogShowConfig.getAttachLibShowLevel());

		if (ta.isLazyLoad()) {
			QueryBuilder qb = new QueryBuilder(
					"select ID,ParentID,TreeLevel,Name,SingleFlag,InnerCode,OrderFlag from ZCCatalog Where Type = "
							+ Catalog.ATTACHMENTLIB + " and SiteID =? and TreeLevel>? and innerCode like ?");
			qb.add(SiteID);
			qb.add(parentLevel);
			qb.add(CatalogUtil.getInnerCode(parentID) + "%");
			if (!CatalogShowConfig.isAttachLibLoadAllChild()) {
				qb.append(" and TreeLevel<?", Integer.parseInt(parentLevel) + 3);
				ta.setExpand(false);
			} else {
				ta.setExpand(true);
			}
			qb.append(" order by orderflag,innercode");
			dt = qb.executeDataTable();
		} else {
			ta.setLevel(CatalogShowConfig.getAttachLibShowLevel());
			QueryBuilder qb = new QueryBuilder(
					"select ID,ParentID,TreeLevel,Name,SingleFlag,InnerCode,OrderFlag from ZCCatalog Where Type =? and SiteID =? and TreeLevel-1 <=? order by orderflag,innercode");
			qb.add(Catalog.ATTACHMENTLIB);
			qb.add(SiteID);
			qb.add(ta.getLevel());
			dt = qb.executeDataTable();
			Catalog.prepareSelectedCatalogData(dt, codes, Catalog.ATTACHMENTLIB, SiteID, ta.getLevel());
		}
		ta.setRootText("附件库");
		dt = dt.filter(new Filter() {
			public boolean filter(Object obj) {
				DataRow dr = (DataRow) obj;
				return Priv.getPriv(User.getUserName(), Priv.ATTACH, dr.getString("InnerCode"), Priv.ATTACH_BROWSE);
			}
		});
		ta.bindData(dt);
		Catalog.addSelectedBranches(ta, codes);
	}

	public static void dg1DataBind(DataGridAction dga) {
		String CatalogID = dga.getParam("CatalogID");
		if (StringUtil.isEmpty(CatalogID)) {
			CatalogID = dga.getParams().getString("Cookie.Resource.LastAttachLib");
			if (StringUtil.isEmpty(CatalogID) || "null".equals(CatalogID)) {
				CatalogID = "0";
			}
			dga.getParams().put("CatalogID", CatalogID);
		}
		String Name = (String) dga.getParam("SearchName");
		String StartDate = (String) dga.getParam("StartDate");
		String EndDate = (String) dga.getParam("EndDate");
		QueryBuilder qb = new QueryBuilder("select * from ZCAttachment");
		qb.append(" where 1 = 1");
		if (StringUtil.isNotEmpty(CatalogID)) {
			qb.append(" and CatalogID=?", Long.parseLong(CatalogID));
		} else {
			dga.setTotal(0);
			dga.bindData(new DataTable());
			return;
		}
		if (StringUtil.isNotEmpty(Name)) {
			qb.append(" and Name like ?", "%" + Name.trim() + "%");
		}
		if (StringUtil.isNotEmpty(StartDate)) {
			qb.append(" and addtime>=? ", StartDate);
		}
		if (StringUtil.isNotEmpty(EndDate)) {
			qb.append(" and addtime<=? ", EndDate);
		}
		dga.setTotal(qb);
		qb.append(" order by orderflag desc");
		int pageSize = dga.getPageSize();
		int pageIndex = dga.getPageIndex();
		DataTable dt = qb.executePagedDataTable(pageSize, pageIndex);

		// dt.insertColumn("Size");
		dt.insertColumn("LockImage");
		dt.insertColumn("SuffixImage");
		dt.decodeDateColumn("AddTime");
		dt.insertColumn("AttachmentLink");

		for (int i = 0; i < dt.getRowCount(); i++) {
			String suffix = dt.get(i, "Suffix") + "";
			String[] ext = { "jpg", "gif", "zip", "rar", "bmp", "png", "doc", "xls", "html", "js", "mov", "mp4", "flv",
					"rm", "wmv", "swf", "txt", "mp3", "avi", "ppt", "pdf", "pptx", "xlsx", "docx" };
			if ("Y".equals(dt.get(i, "IsLocked"))) {
				dt.set(i, "LockImage", "<img src='../Icons/icon048a1.gif' width='20' height='20'/>");
			}
			for (int j = 0; j < ext.length; j++) {
				if (ext[j].equalsIgnoreCase(suffix)) {
					dt.set(i, "SuffixImage", "<img src='../Framework/Images/FileType/" + ext[j]
							+ ".gif' width='16' height='16' title='" + suffix + "'/>");
					break;
				}
			}
			if (dt.get(i, "SuffixImage") == null) {
				dt.set(i, "SuffixImage",
						"<img src='../Framework/Images/FileType/unknown.gif' width='16' height='16' title='" + suffix
								+ "'/>");
			}

			if ("N".equals(SiteUtil.getAttachDownFlag(Application.getCurrentSiteID()))) {
				dt.set(i, "AttachmentLink", (Config.getContextPath() + Config.getValue("UploadDir") + "/"
						+ Application.getCurrentSiteAlias() + "/" + dt.getString(i, "Path") + dt.getString(i,
						"filename")).replaceAll("//", "/"));
			} else {
				dt.set(i, "AttachmentLink", (Config.getContextPath() + "/Services/AttachDownLoad.jsp?id=" + dt
						.getString(i, "ID")).replaceAll("//", "/"));
			}
		}
		dga.bindData(dt);
	}

	public void edit() {
		ZCAttachmentSchema attach = new ZCAttachmentSchema();
		attach.setValue(this.Request);
		attach.fill();
		attach.setValue(this.Request);
		if (attach.update()) {
			this.Response.setLogInfo(1, "修改附件成功");
		} else {
			this.Response.setLogInfo(0, "修改音频失败");
		}
	}

	public void dg1Edit() {
		DataTable dt = (DataTable) Request.get("DT");
		ZCAttachmentSet set = new ZCAttachmentSet();
		StringBuffer logs = new StringBuffer("编辑附件:");
		for (int i = 0; i < dt.getRowCount(); i++) {
			ZCAttachmentSchema attach = new ZCAttachmentSchema();
			attach.setValue(dt.getDataRow(i));
			attach.setModifyTime(new Date());
			attach.setModifyUser(User.getUserName());
			set.add(attach);
			logs.append(attach.getName() + ",");
		}
		if (set.update()) {
			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_EDITATTACHMENT, logs + "成功", Request.getClientIP());
			Response.setLogInfo(1, "保存成功!");
		} else {
			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_EDITATTACHMENT, logs + "失败", Request.getClientIP());
			Response.setLogInfo(0, "保存失败!");
		}
	}

	public void add() {
		String name = $V("Name");
		String parentID = $V("ParentID");
		String IT = $V("IndexTemplate");
		String DT = $V("DetailTemplate");
		String LT = $V("ListTemplate");
		String imagePath = $V("ImagePath");
		Transaction trans = new Transaction();
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		ZCCatalogSchema pCatalog = new ZCCatalogSchema();

		catalog.setID(NoUtil.getMaxID("CatalogID"));
		catalog.setSiteID(Application.getCurrentSiteID());
		// parentID 为0表示上级节点是站点
		if (parentID.equals("0") || StringUtil.isEmpty(parentID)) {
			catalog.setParentID(0);
			catalog.setTreeLevel(1);
			ZCSiteSchema site = new ZCSiteSchema();
			site.setID(catalog.getSiteID());
			site.fill();
			site.setAttachmentLibCount(site.getAttachmentLibCount() + 1);
			trans.add(site, Transaction.UPDATE);
		} else {
			catalog.setParentID(Long.parseLong(parentID));

			pCatalog.setID(catalog.getParentID());
			pCatalog.fill();

			catalog.setTreeLevel(pCatalog.getTreeLevel() + 1);

			pCatalog.setChildCount(pCatalog.getChildCount() + 1);
			trans.add(pCatalog, Transaction.UPDATE);
		}

		String innerCode = CatalogUtil.createCatalogInnerCode(pCatalog.getInnerCode());
		catalog.setInnerCode(innerCode);

		catalog.setName(name);
		catalog.setURL(" ");

		String alias = ChineseSpelling.getFirstAlpha(name).toLowerCase();
		String existsName = Catalog.checkAliasExists(alias, catalog.getParentID());
		if (StringUtil.isNotEmpty(existsName)) {
			alias += NoUtil.getMaxID("AliasNo");
		}
		catalog.setAlias(alias);
		catalog.setType(Catalog.ATTACHMENTLIB);
		catalog.setIndexTemplate(IT);
		catalog.setListTemplate(LT);
		catalog.setListNameRule("");
		catalog.setDetailTemplate(DT);
		catalog.setDetailNameRule("");
		catalog.setChildCount(0);
		catalog.setIsLeaf(1);
		catalog.setTotal(0);
		catalog.setOrderFlag(Catalog.getCatalogOrderFlag(parentID, catalog.getType() + ""));
		catalog.setLogo("");
		catalog.setListPageSize(10);
		catalog.setListPage(-1);
		catalog.setPublishFlag("Y");
		catalog.setHitCount(0);
		catalog.setMeta_Keywords("");
		catalog.setMeta_Description("");
		catalog.setOrderColumn("");
		catalog.setImagePath(imagePath);
		catalog.setAddUser(User.getUserName());
		catalog.setAddTime(new Date());

		trans.add(catalog, Transaction.INSERT);

		// 初始化栏目发布设置
		Catalog.initCatalogConfig(catalog, trans);
		Catalog.initCatalogPriv(catalog, trans);// 初始化栏目权限
		if (trans.commit()) {
			CatalogUtil.update(catalog.getID());
			Priv.updateAllPriv(User.getUserName());
			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_ADDTYPEATTACHMENT, "新建附件分类" + catalog.getName() + " 成功",
					Request.getClientIP());
			this.Response.setLogInfo(1, "新建附件分类成功！");
		} else {
			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_ADDTYPEATTACHMENT, "新建附件分类" + catalog.getName() + " 成功",
					Request.getClientIP());
			this.Response.setLogInfo(0, "新建附件分类失败！");
		}
	}

	public void delLib() {
		String catalogID = $V("catalogID");
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(Long.parseLong(catalogID));
		if (!catalog.fill()) {
			this.Response.setLogInfo(0, "没有附件分类！");
			return;
		}

		if ("N".equals(catalog.getProp4())) {
			this.Response.setLogInfo(0, "不能删除该附件分类！");
			return;
		}
		QueryBuilder qb = new QueryBuilder("where InnerCode like ?", catalog.getInnerCode() + "%");
		ZCCatalogSet catalogSet = new ZCCatalogSchema().query(qb);
		Transaction trans = new Transaction();
		ZCAttachmentSet attachmentSet = new ZCAttachmentSchema().query(new QueryBuilder(
				"where CatalogInnerCode like ?", catalog.getInnerCode() + "%"));
		for (int i = 0; i < attachmentSet.size(); i++) {
			ZCAttachmentRelaSet AttachmentRelaSet = new ZCAttachmentRelaSchema().query(new QueryBuilder(" where id =?",
					attachmentSet.get(i).getID()));
			trans.add(AttachmentRelaSet, Transaction.DELETE_AND_BACKUP);
		}

		File file = new File(Config.getContextRealPath() + Config.getValue("UploadDir") + "/"
				+ SiteUtil.getAlias(Application.getCurrentSiteID()) + "/upload/Attachment/"
				+ CatalogUtil.getPath(Long.parseLong(catalogID)));
		FileUtil.delete(file);
		trans.add(attachmentSet, Transaction.DELETE_AND_BACKUP);
		trans.add(catalogSet, Transaction.DELETE_AND_BACKUP);
		if (trans.commit()) {
			CMSCache.removeCatalogSet(catalogSet);// 从缓存中剔除

			this.Response.setLogInfo(1, "删除附件分类成功！");

			// 删除发布文件，更新列表
			Publisher p = new Publisher();
			p.deleteFileTask(attachmentSet);
		} else {
			this.Response.setLogInfo(0, "删除附件分类失败！");
		}
	}

	public void publish() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("操作数据库时发生错误!");
			return;
		}
		ZCAttachmentSchema attachment = new ZCAttachmentSchema();
		ZCAttachmentSet set = attachment.query(new QueryBuilder("where id in(" + ids + ")"));

		Response.setStatus(1);
		Publisher p = new Publisher();
		long id = p.publishSetTask("Attachment", set);
		$S("TaskID", "" + id);
	}

	public void sortColumn() {
		String target = $V("Target");
		String orders = $V("Orders");
		String type = $V("Type");
		String catalogID = $V("CatalogID");
		if (!StringUtil.checkID(target) && !StringUtil.checkID(orders)) {
			return;
		}

		if (StringUtil.isNotEmpty(catalogID)) {
			if (OrderUtil.updateOrder("ZCAttachment", type, target, orders, " CatalogID = " + catalogID)) {
				Response.setLogInfo(1, "排序成功");
			} else {
				Response.setLogInfo(0, "排序失败");
			}
		} else {
			if (OrderUtil.updateOrder("ZCAttachment", type, target, orders, " SiteID = "
					+ Application.getCurrentSiteID())) {
				Response.setLogInfo(1, "排序成功");
			} else {
				Response.setLogInfo(0, "排序失败");
			}
		}
	}
}
