package com.nswt.cms.resource;

import java.io.File;
import java.util.Date;

import com.nswt.cms.datachannel.Publisher;
import com.nswt.cms.dataservice.ColumnUtil;
import com.nswt.cms.pub.CatalogUtil;
import com.nswt.cms.pub.SiteUtil;
import com.nswt.cms.site.Catalog;
import com.nswt.framework.Config;
import com.nswt.framework.Page;
import com.nswt.framework.User;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.FileUtil;
import com.nswt.framework.utility.HtmlUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.NumberUtil;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.Application;
import com.nswt.platform.UserLog;
import com.nswt.platform.pub.NoUtil;
import com.nswt.schema.ZCAttachmentRelaSchema;
import com.nswt.schema.ZCAttachmentRelaSet;
import com.nswt.schema.ZCAttachmentSchema;
import com.nswt.schema.ZCAttachmentSet;
import com.nswt.schema.ZCCatalogSchema;

/**
 * 
 * @author Xuzhe
 * 
 */

public class Attachment extends Page {

	public static Mapx init(Mapx params) {
		String CatalogID = params.getString("CatalogID");
		if (StringUtil.isEmpty(CatalogID)) {
			CatalogID = new QueryBuilder("select ID from ZCCatalog where Type = " + Catalog.ATTACHMENTLIB
					+ " and SiteID =? and Name = '默认附件'", Application.getCurrentSiteID()).executeOneValue()
					+ "";
		}
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(CatalogID);
		catalog.fill();
		DataTable dt = new QueryBuilder("select name,id from zccatalog ").executePagedDataTable(100, 0);
		params.put("Who", HtmlUtil.dataTableToOptions(dt));
		params.putAll(catalog.toMapx());

		String imagePath = "upload/Image/nopicture.jpg";
		params.put("ImagePath", imagePath);
		params.put("PicSrc", Config.getContextPath() + Config.getValue("UploadDir") + "/"
				+ SiteUtil.getAlias(Application.getCurrentSiteID()) + "/" + imagePath);
		params.put("allowType", Config.getValue("AllowAttachExt"));
		
		//允许上传附件大小设置
		String fileSize = Config.getValue("AllowAttachSize");
		if(StringUtil.isEmpty(fileSize)){
			fileSize = "20971520"; //默认20M
		}
		params.put("fileMaxSize", fileSize);
		long size = Long.parseLong(fileSize);
		String fileMaxSizeHuman = "";
		if (size < 1048576) {
			fileMaxSizeHuman = NumberUtil.round(size * 1.0 / 1024, 1) + "K";
		} else if (size < 1073741824) {
			fileMaxSizeHuman = NumberUtil.round(size * 1.0 / 1048576, 1) + "M";
		}
		params.put("fileMaxSizeHuman", fileMaxSizeHuman);
		return params;
	}
	
	/**
	 * 初始化附件对话框
	 * 
	 * @param params
	 * @return
	 */
	public static Mapx initDialog(Mapx params) {
		long attachID = Long.parseLong(params.getString("ID"));
		String imagePath = "upload/Image/nopicture.jpg";
		DataTable dt = new QueryBuilder("select imagepath from ZCAttachment where id=?", attachID).executeDataTable();
		if (dt.getRowCount() == 0 || StringUtil.isEmpty((String) dt.get(0, 0))) {
			params.put("Name", new QueryBuilder("select Name from ZCAttachment where id=?", attachID).executeString());
			params.put("Info", new QueryBuilder("select Info from ZCAttachment where id=?", attachID).executeString());
			params.put("ImagePath", imagePath);
			params.put("PicSrc", Config.getContextPath() + Config.getValue("UploadDir") + "/"
					+ SiteUtil.getAlias(Application.getCurrentSiteID()) + "/" + imagePath);
			return params;
		}
		ZCAttachmentSchema attach = new ZCAttachmentSchema();
		attach.setID(attachID);
		attach.fill();
		params = attach.toMapx();
		imagePath = attach.getImagePath();
		params.put("PicSrc", Config.getContextPath() + Config.getValue("UploadDir") + "/" + SiteUtil.getAlias(Application.getCurrentSiteID()) + "/" + imagePath);
		params.put("CustomColumn",ColumnUtil.getHtml(ColumnUtil.RELA_TYPE_CATALOG_COLUMN, attach.getCatalogID()+"",ColumnUtil.RELA_TYPE_DOCID, attach.getID() + ""));
		params.put("AttachmentLink", (Config.getContextPath() + "/Services/AttachDownLoad.jsp?id=" + attach.getID()).replaceAll("//", "/"));
		return params;
	}

	public static Mapx initEditDialog(Mapx params) {
		ZCAttachmentSchema Attachment = new ZCAttachmentSchema();
		Attachment.setID(params.getString("ID"));
		Attachment.fill();
		params.putAll(Attachment.toMapx());
		
		//允许上传附件大小设置
		String fileSize = Config.getValue("AllowAttachSize");
		if(StringUtil.isEmpty(fileSize)){
			fileSize = "20971520"; //默认20M
		}
		params.put("fileMaxSize", fileSize);
		return params;
	}
	
	public void dialogEdit() {
		String attachID = $V("ID");
		Transaction trans = new Transaction();
		ZCAttachmentSchema attach = new ZCAttachmentSchema();
		attach.setID(attachID);
		attach.fill();
		attach.setValue(Request);
		DataTable columnDT = ColumnUtil.getColumnValue(ColumnUtil.RELA_TYPE_DOCID, attach.getID());
		if (columnDT.getRowCount() > 0) {
			trans.add(new QueryBuilder("delete from zdcolumnvalue where relatype=? and relaid = ?",
					ColumnUtil.RELA_TYPE_DOCID, attach.getID()+""));
		}
		trans.add(ColumnUtil.getValueFromRequest(attach.getCatalogID(), attach.getID(), Request), Transaction.INSERT);
		trans.add(attach, Transaction.UPDATE);
		if (trans.commit()) {
			this.Response.setLogInfo(1, "编辑成功！");
		} else {
			this.Response.setLogInfo(1, "编辑失败！");
		}

	}

	public void copy() {
		long catalogID = Long.parseLong($V("CatalogID"));
		String Alias = Config.getValue("UploadDir") + "/" + SiteUtil.getAlias(Application.getCurrentSiteID()) + "/";
		String catalogPath = CatalogUtil.getPath(catalogID);
		String newPath = "Attachment/" + catalogPath;
		String InnerCode = new QueryBuilder("select InnerCode from zccatalog where ID = ?", catalogID).executeString();
		File dir = new File(Config.getContextRealPath() + Alias + newPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String IDs = $V("IDs");
		if (!StringUtil.checkID(IDs)) {
			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_COPYATTACHMENT, "复制附件失败", Request.getClientIP());
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		ZCAttachmentSet AttachmentSet = new ZCAttachmentSchema().query(new QueryBuilder(" where ID in (" + IDs + ")"));
		boolean flag = true;
		StringBuffer logs = new StringBuffer("复制附件:");
		for (int i = 0; i < AttachmentSet.size(); i++) {
			ZCAttachmentSchema Attachment = AttachmentSet.get(i);
			String oldFileName = Alias + Attachment.getPath() + Attachment.getFileName();
			Attachment.setID(NoUtil.getMaxID("DocID"));
			Attachment.setCatalogID(catalogID);
			Attachment.setCatalogInnerCode(InnerCode);
			Attachment.setPath(newPath);
			Attachment.setFileName(Attachment.getID()
					+ Attachment.getFileName().substring(Attachment.getFileName().lastIndexOf(".")));
			Attachment.setAddTime(new Date());
			Attachment.setAddUser(User.getUserName());
			File directory = new File(Config.getContextRealPath() + Alias + newPath);
			if (!directory.exists()) {
				directory.mkdirs();
			}
			if (!FileUtil.copy(Config.getContextRealPath() + oldFileName, Config.getContextRealPath() + Alias + newPath
					+ Attachment.getFileName())) {
				flag = false;
				break;
			}
			logs.append(Attachment.getName() + ",");
		}
		if (flag && AttachmentSet.insert()) {
			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_COPYATTACHMENT, logs + "成功", Request.getClientIP());
			this.Response.setLogInfo(1, "复制附件成功！");
		} else {
			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_COPYATTACHMENT, logs + "失败", Request.getClientIP());
			this.Response.setLogInfo(0, "复制附件失败！");
		}
	}
	
	public void getAttachSrc() {
		String ID = $V("AttachID");
		DataTable dt = new QueryBuilder("select name,siteid,path,srcfilename from zcattachment where id=?", ID).executeDataTable();
		if (dt.getRowCount() > 0) {
			this.Response.put("AttachPath",(Config.getContextPath() + Config.getValue("UploadDir") + "/"
					+ SiteUtil.getAlias(dt.getLong(0,"siteid")) + "/" + dt.get(0, "path").toString()
					+ dt.get(0, "srcfilename").toString()+"|"+dt.getString(0, "name")).replaceAll("//", "/"));
		}
	}

	public void transfer() {
		long catalogID = Long.parseLong($V("CatalogID"));
		String IDs = $V("IDs");
		if (!StringUtil.checkID(IDs)) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		Transaction trans = new Transaction();
		trans.add(new QueryBuilder("update ZCAttachment set catalogid=? ,CatalogInnerCode=? where ID in (" + IDs + ")",
				catalogID, CatalogUtil.getInnerCode(catalogID)));
		StringBuffer logs = new StringBuffer("转移附件:");
		DataTable dt = new QueryBuilder("select Name from ZCAttachment where id in(" + IDs + ")").executeDataTable();
		for (int i = 0; i < dt.getRowCount(); i++) {
			logs.append(dt.get(i, "Name") + ",");
		}
		if (trans.commit()) {
			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_MOVEATTACHMENT, logs + "成功", Request.getClientIP());
			Response.setLogInfo(1, "转移成功");
		} else {
			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_MOVEATTACHMENT, logs + "成功", Request.getClientIP());
			Response.setLogInfo(0, "转移失败");
		}
	}

	public void del() {
		String IDs = $V("IDs");
		if (!StringUtil.checkID(IDs)) {
			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_DELATTACHMENT, "删除附件失败", Request.getClientIP());
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		StringBuffer logs = new StringBuffer("删除附件:");
		String Alias = Config.getValue("UploadDir") + "/" + SiteUtil.getAlias(Application.getCurrentSiteID()) + "/";
		ZCAttachmentSet AttachmentSet = new ZCAttachmentSchema().query(new QueryBuilder(" where id in (" + IDs + ")"));
		ZCAttachmentRelaSet AttachmentRelaSet = new ZCAttachmentRelaSchema().query(new QueryBuilder(" where id in ("
				+ IDs + ")"));
		Transaction trans = new Transaction();
		for (int i = 0; i < AttachmentSet.size(); i++) {
			FileUtil.delete(Config.getContextRealPath() + Alias + AttachmentSet.get(i).getPath()
					+ AttachmentSet.get(i).getFileName());
			trans.add(new QueryBuilder("delete from zdcolumnvalue where relatype=? and relaid = ?",ColumnUtil.RELA_TYPE_DOCID, AttachmentSet.get(i).getID()+""));
			logs.append(AttachmentSet.get(i).getName() + ",");
		}
		trans.add(AttachmentSet, Transaction.DELETE_AND_BACKUP);
		trans.add(AttachmentRelaSet, Transaction.DELETE_AND_BACKUP);
		trans.add(new QueryBuilder(
				"update zccatalog set total = (select count(*) from zcattachment where catalogID =?) where ID =?",
				AttachmentSet.get(0).getCatalogID(), AttachmentSet.get(0).getCatalogID()));
		if (trans.commit()) {
			// 删除发布文件，更新列表
			Publisher p = new Publisher();
			p.deleteFileTask(AttachmentSet);
			
			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_DELATTACHMENT, logs + "成功", Request.getClientIP());
			this.Response.setLogInfo(1, "删除附件成功！");
		} else {
			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_DELATTACHMENT, logs + "失败", Request.getClientIP());
			this.Response.setLogInfo(0, "删除附件失败！");
		}
	}

}