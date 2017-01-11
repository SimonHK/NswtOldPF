package com.nswt.cms.api;

import java.io.File;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;

//import com.nswt.cms.datachannel.Publisher;
import com.nswt.cms.datachannel.Publisher;
import com.nswt.cms.dataservice.ColumnUtil;
import com.nswt.cms.document.Article;
import com.nswt.cms.pub.CatalogUtil;
import com.nswt.cms.pub.PubFun;
import com.nswt.cms.pub.SiteUtil;
import com.nswt.cms.resource.uploader.ZUploaderServlet;
import com.nswt.framework.Config;
import com.nswt.framework.User;
import com.nswt.framework.User.UserData;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.utility.DateUtil;
import com.nswt.framework.utility.FileUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.NumberUtil;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.pub.NoUtil;
import com.nswt.platform.pub.OrderUtil;
import com.nswt.schema.ZCArticleLogSchema;
import com.nswt.schema.ZCArticleSchema;
import com.nswt.schema.ZCAttachmentSchema;
import com.nswt.schema.ZDColumnValueSchema;
import com.nswt.schema.ZDColumnValueSet;

public class ArticleAPI implements APIInterface {
	private ZCArticleSchema article;

	private Mapx customData;

	private Mapx params;

	public boolean setSchema(Schema schema) {
		this.article = (ZCArticleSchema) schema;
		return false;
	}

	/*
	 * 新建文章 新建成功 返回 1 失败返回 -1
	 */
	public long insert() {
		Transaction trans = new Transaction();
		if (insert(trans) > 0) {
			if (trans.commit()) {
				Publisher p=new Publisher();
				UserData ud = new UserData();
				ud.setUserName("SYSTEM");
				ud.setLogin(true);
				ud.setManager(true);
				User.setCurrent(new UserData());
				ZCArticleSchema article1=new ZCArticleSchema();
				article1.setID(article.getID());
				p.publishArticle(article1.query());
				return article.getID();
				
			} else
				return -1;
		} else {
			return -1;
		}
	}

	public long insert(Transaction trans) {

		long articleID = NoUtil.getMaxID("DocID");
		article.setID(articleID);
		if (article.getCatalogID() == 0L) {
			return -1;
		}
		article.setSiteID(CatalogUtil.getSiteID(article.getCatalogID()));

		String innerCode = CatalogUtil.getInnerCode(article.getCatalogID());
		article.setCatalogInnerCode(innerCode);
		if (article.getType() == null) {
			article.setType("1");
		}

		if (article.getTopFlag() == null) {
			article.setTopFlag("0");
		}

		if (article.getCommentFlag() == null) {
			article.setCommentFlag("1");
		}

		if (article.getContent() == null) {
			article.setContent("");
		}
		String[] attachNames=(String[])params.get("attachNams");
		byte[][] attachBytes=(byte[][])params.get("attachBytes");
		if(attachBytes!=null&&attachNames!=null&&attachBytes.length>0&&attachBytes.length==attachNames.length){
			DataTable attachCatalogDataTable=new QueryBuilder("SELECT * FROM zccatalog z where type='7'").executePagedDataTable(1, 0);
			if(attachCatalogDataTable.getRowCount()==0){
				return -1;
			}else if(StringUtil.isEmpty(attachCatalogDataTable.getString(0, "ID"))){
				return -1;
			}
			String content = article.getContent() ;
			for (int i = 0; i < attachBytes.length; i++) {
				String attachName=attachNames[i];
				byte[] attachByte=attachBytes[i];
				if (StringUtil.isNotEmpty(attachName)&&attachByte!=null&&attachByte.length>0) {
					String fileNameLong = attachName;
					fileNameLong = fileNameLong.replaceAll("\\\\", "/");
					String oldName = fileNameLong.substring(fileNameLong.lastIndexOf("/") + 1); // 原文件名
					String ext = ZUploaderServlet.getExtension(oldName); // 后缀名
					long attachID = NoUtil.getMaxID("DocID");
					int random = NumberUtil.getRandomInt(10000);
					String srcFileName = attachID + "" + random + "." + ext;
					String absolutePath=SiteUtil.getAbsolutePath(article.getSiteID())+"upload/Attach/mrbj/";
					File f=new File(SiteUtil.getAbsolutePath(article.getSiteID())+"upload/Attach/mrbj/");
					if(!f.exists()){
						f.mkdirs();
					}
					FileUtil.writeByte(absolutePath+srcFileName, attachByte);
					ZCAttachmentSchema attachment = new ZCAttachmentSchema();
					attachment.setID(attachID);
					attachment.setName(oldName.substring(0, oldName.lastIndexOf(".")));
					attachment.setOldName(oldName.substring(0, oldName.lastIndexOf(".")));
					attachment.setSiteID(article.getSiteID());
					attachment.setInfo("");
					attachment.setCatalogID(attachCatalogDataTable.getString(0, "ID"));
					attachment.setCatalogInnerCode(attachCatalogDataTable.getString(0, "InnerCode"));
					attachment.setBranchInnerCode(article.getBranchInnerCode());
					attachment.setPath("upload/Attach/mrbj/");
					attachment.setImagePath("upload/Image/nopicture.jpg");
					attachment.setFileName(srcFileName);
					attachment.setSrcFileName(srcFileName);
					attachment.setSuffix(ext);
					attachment.setSystem("CMS");
					attachment.setFileSize(FileUtils.byteCountToDisplaySize(attachByte.length));
					attachment.setAddTime(new Date());
					attachment.setAddUser("admin");
					attachment.setOrderFlag(OrderUtil.getDefaultOrder());
					attachment.setModifyTime(new Date());
					attachment.setModifyUser("SYSTEM");
					attachment.setIsLocked("null");
					attachment.setProp1("0");
					attachment.setStatus(Article.STATUS_TOPUBLISH);
					trans.add(attachment, Transaction.INSERT);
					String serverpath=Config.getValue("ServicesContext").replaceAll("//", "");
					content += ("<p><a href='" +serverpath.substring(serverpath.indexOf("/"))+"/AttachDownLoad.jsp?id=" +attachID+ "'>" + attachment.getName() + "</a></p>");
				}
			}
			article.setContent(content);
		}
		article.setStickTime(0);

		article.setPriority("1");
		article.setTemplateFlag("0");

		article.setPublishFlag("1");

		if (article.getOrderFlag() == 0) {
			if (article.getPublishDate() != null) {
				article.setOrderFlag(article.getPublishDate().getTime());
			} else {
				article.setOrderFlag(OrderUtil.getDefaultOrder());
			}
		}
		article.setHitCount(0);
		if (article.getStatus() == 0) {
			article.setStatus(Article.STATUS_DRAFT);
		}
		article.setAddTime(new Date(article.getOrderFlag()));

		if (article.getAddUser() == null) {
			article.setAddUser("admin");
		}

		trans.add(article, Transaction.INSERT);

		String sqlArticleCount = "update zccatalog set total = total+1,isdirty=1 where id=?";
		trans.add(new QueryBuilder(sqlArticleCount, article.getCatalogID()));

		if (customData != null) {
			addCustomData(trans);
		}

		ZCArticleLogSchema articleLog = new ZCArticleLogSchema();
		articleLog.setID(NoUtil.getMaxID("ArticleLogID"));
		articleLog.setAction("INSERT");
		articleLog.setActionDetail("添加新文章");
		articleLog.setArticleID(articleID);

		articleLog.setAddUser("admin");
		articleLog.setAddTime(new Date());
		trans.add(articleLog, Transaction.INSERT);

		return 1;
	}

	private void addCustomData (Transaction trans) {
		// 自定义字段
		ZDColumnValueSchema ColumnValue = null;
		ZDColumnValueSet ColumnValueSet = new ZDColumnValueSet();
		DataTable dt = new QueryBuilder(
				"select b.code code,b.listopt listopt,b.showmod showmod from zdcustomcolumnrela a, zdcustomcolumnitem b where a.type=2 and b.classcode='Sys_CMS' and a.customid=b.id and a.typeid=?",
				article.getCatalogID()).executeDataTable();
		if (dt.getRowCount() > 0) {
			for (int i = 0; i < dt.getRowCount(); i++) {
				String code = "";
				String showMode = "";
				String textValue = "";
				String index = ""; // 序号
				String[] list = null;

				code = dt.getString(i, "code");
				showMode = dt.getString(i, "ShowMod");
				if ("1".equals(showMode) || "2".equals(showMode)) { // 单行文本、多行文本
					textValue = customData.getString(code);
				}
				if ("3".equals(showMode) || "4".equals(showMode)) { // 下拉列表框
					list = dt.getString(i, "listopt").split("\n");
					textValue = customData.getString(code);
					index = ArrayUtils.indexOf(list, textValue) + "";
				}
				if ("5".equals(showMode)) { // 多选
					list = dt.getString(i, "listopt").split("\n");
					textValue = customData.getString(code);
					String[] values = textValue.split("\\|");
					for (int j = 0; j < values.length; j++) {
						if (j != values.length - 1) {
							index += ArrayUtils.indexOf(list, values[j]) + "|";
						} else {
							index += ArrayUtils.indexOf(list, values[j]);
						}

					}
				}
				ColumnValue = new ZDColumnValueSchema();
				ColumnValue.setID(NoUtil.getMaxID("ColumnValueID"));
				ColumnValue.setColumnCode(code);
				textValue = (textValue == null ? "" : textValue);
				ColumnValue.setTextValue(textValue);
				ColumnValue.setRelaID(article.getID() + "");
				ColumnValueSet.add(ColumnValue);
			}
		}
		trans.add(new QueryBuilder("delete from zdcolumnvalue where classcode='Sys_CMS' and articleid=?", article
				.getID()));
		trans.add(ColumnValueSet, Transaction.INSERT);
	}

	public boolean update() {
		String articleID = params.getString("DocID");

		ZCArticleSchema article1 = new ZCArticleSchema();
		article1.setID(articleID);
		if (!article1.fill()) {
			return false;
		}
		if (article1.getCatalogID() == 0L) {
			return false;
		}
		
		if(StringUtil.isNotEmpty(params.getString("CatalogID"))){
			article1.setCatalogID(params.getString("CatalogID"));
		}

		if (StringUtil.isNotEmpty(params.getString("Title"))) {
			article1.setTitle(params.getString("Title"));
		}

		if (StringUtil.isNotEmpty(params.getString("Author"))) {
			article1.setAuthor(params.getString("Author"));
		}
		String content = params.getString("Content");
		String publishDate = params.getString("PublishDate");

		if (StringUtil.isNotEmpty(content)) {
			article1.setContent(content);
		}

		if (StringUtil.isNotEmpty(publishDate)) {
			try {
				article1.setPublishDate(DateUtil.parse(publishDate, DateUtil.Format_Date));
			} catch (Exception e) {

			}
		}

		article1.setModifyTime(new Date(article1.getOrderFlag()));

		article1.setModifyUser("wsdl");

		Transaction trans = new Transaction();
		String[] attachNames=(String[])params.get("attachNams");
		byte[][] attachBytes=(byte[][])params.get("attachBytes");
		if(attachBytes!=null&&attachNames!=null&&attachBytes.length>0&&attachBytes.length==attachNames.length){
			DataTable attachCatalogDataTable=new QueryBuilder("SELECT * FROM zccatalog z where type='7' and SiteID=?",article1.getSiteID()).executePagedDataTable(1, 0);
			if(attachCatalogDataTable.getRowCount()==0){
				return false;
			}else if(StringUtil.isEmpty(attachCatalogDataTable.getString(0, "ID"))){
				return false;
			}
			for (int i = 0; i < attachBytes.length; i++) {
				String attachName=attachNames[i];
				byte[] attachByte=attachBytes[i];
				if (StringUtil.isNotEmpty(attachName)&&attachByte!=null&&attachByte.length>0) {
					String fileNameLong = attachName;
					fileNameLong = fileNameLong.replaceAll("\\\\", "/");
					String oldName = fileNameLong.substring(fileNameLong.lastIndexOf("/") + 1); // 原文件名
					String ext = ZUploaderServlet.getExtension(oldName); // 后缀名
					long attachID = NoUtil.getMaxID("DocID");
					int random = NumberUtil.getRandomInt(10000);
					String srcFileName = attachID + "" + random + "." + ext;
					String absolutePath=SiteUtil.getAbsolutePath(article1.getSiteID())+"upload/Attach/mrbj/";
					File f=new File(SiteUtil.getAbsolutePath(article1.getSiteID())+"upload/Attach/mrbj/");
					if(!f.exists()){
						f.mkdirs();
					}
					FileUtil.writeByte(absolutePath+srcFileName, attachByte);
					ZCAttachmentSchema attachment = new ZCAttachmentSchema();
					attachment.setID(attachID);
					attachment.setName(oldName.substring(0, oldName.lastIndexOf(".")));
					attachment.setOldName(oldName.substring(0, oldName.lastIndexOf(".")));
					attachment.setSiteID(article1.getSiteID());
					attachment.setInfo("");
					attachment.setCatalogID(attachCatalogDataTable.getString(0, "ID"));
					attachment.setCatalogInnerCode(attachCatalogDataTable.getString(0, "InnerCode"));
					attachment.setBranchInnerCode(article1.getBranchInnerCode());
					attachment.setPath("upload/Attach/mrbj/");
					attachment.setImagePath("upload/Image/nopicture.jpg");
					attachment.setFileName(srcFileName);
					attachment.setSrcFileName(srcFileName);
					attachment.setSuffix(ext);
					attachment.setSystem("CMS");
					attachment.setFileSize(FileUtils.byteCountToDisplaySize(attachByte.length));
					attachment.setAddTime(new Date());
					attachment.setAddUser("admin");
					attachment.setOrderFlag(OrderUtil.getDefaultOrder());
					attachment.setModifyTime(new Date());
					attachment.setModifyUser("SYSTEM");
					attachment.setProp1("0");
					attachment.setIsLocked("null");
					attachment.setStatus(Article.STATUS_TOPUBLISH);
					trans.add(attachment, Transaction.INSERT);
					String serverpath=Config.getValue("ServicesContext").replaceAll("//", "");
					content += ("<p><a href='" +serverpath.substring(serverpath.indexOf("/"))+"/AttachDownLoad.jsp?id=" +attachID+ "'>" + attachment.getName() + "</a></p>");
				}
			}
			article1.setContent(content);
		}

		trans.add(article1, Transaction.UPDATE);

		// //处理自定义字段
		// DataTable columnDT =
		// ColumnUtil.getColumnValue(ColumnUtil.RELA_TYPE_DOCID, articleID);
		// if (columnDT.getRowCount() > 0) {
		// trans.add(new
		// QueryBuilder("delete from zdcolumnvalue where relatype=? and relaid = ?",
		// ColumnUtil.RELA_TYPE_DOCID, articleID));
		// }
		// DataCollection dc=new DataCollection();
		// dc.putAll(params);
		// trans.add(ColumnUtil.getValueFromRequest(article.getCatalogID(),
		// Long.parseLong(articleID),dc),
		// Transaction.INSERT);

		ZCArticleLogSchema articleLog = new ZCArticleLogSchema();
		articleLog.setID(NoUtil.getMaxID("ArticleLogID"));
		articleLog.setAction("UPDATE");
		articleLog.setActionDetail("编辑文章");
		articleLog.setArticleID(articleID);

		articleLog.setAddUser("wsdl");
		articleLog.setAddTime(new Date());
		trans.add(articleLog, Transaction.INSERT);
		
		return trans.commit();
	}

	public boolean delete() {

		if (article == null) {
			return false;
		}

		long articleID = article.getID();
		Transaction trans = new Transaction();

		trans.add(article, Transaction.DELETE);

		ZDColumnValueSchema colValue = new ZDColumnValueSchema();
		colValue.setRelaID(articleID + "");
		ZDColumnValueSet colValueSet = colValue.query();
		if (colValueSet != null && !colValueSet.isEmpty()) {
			trans.add(colValueSet, Transaction.DELETE);
		}

		String sqlArticleCount = "update zccatalog set " + "total = total-1,isdirty=1 where innercode in("
				+ CatalogUtil.getParentCatalogCode(article.getCatalogInnerCode()) + ")";
		trans.add(new QueryBuilder(sqlArticleCount));

		// 操作日志
		ZCArticleLogSchema articleLog = new ZCArticleLogSchema();
		articleLog.setArticleID(articleID);
		articleLog.setID(NoUtil.getMaxID("ArticleLogID"));
		articleLog.setAction("DELETE");
		articleLog.setActionDetail("删除。删除原因：" + "wsdl删除");
		articleLog.setAddUser("wsdl");
		articleLog.setAddTime(new Date());
		trans.add(articleLog, Transaction.INSERT);

		if (trans.commit()) {
			// Publisher p = new Publisher();
			// p.deletePubishedFile(article);
			return true;
		} else {
			return false;
		}
	}

	public Mapx getCustomData() {
		return customData;
	}

	public void setCustomData(Mapx customData) {
		this.customData = customData;
	}

	public static DataTable getPagedDataTable(long catalogID, int pageSize, int pageIndex) {
		DataTable dt = new QueryBuilder("select * from zcarticle where catalogid=?", catalogID).executePagedDataTable(
				pageSize, pageIndex);
		ColumnUtil.extendDocColumnData(dt, catalogID);
		return dt;
	}

	public static DataTable getDataTable(long catalogID) {
		return getPagedDataTable(catalogID, -1, -1);
	}

	public static String getPreviewURL(long artilceID) {
		String url;
		ZCArticleSchema article = new ZCArticleSchema();
		article.setID(artilceID);
		if (!article.fill()) {
			return null;
		}

		url = Config.getValue("Statical.TargetDir") + "/" + SiteUtil.getAlias(article.getSiteID())
				+ PubFun.getArticleURL(article);
		url.replaceAll("//", "/");
		return url;
	}

	public static String getPublishedURL(long artilceID) {
		ZCArticleSchema article = new ZCArticleSchema();
		article.setID(artilceID);
		String url = null;
		if (article.fill()) {
			url = SiteUtil.getURL(article.getSiteID()) + "/" + PubFun.getArticleURL(article);
		} else {
			url = "#";
		}
		url.replaceAll("/+", "/");
		return url;
	}

	public static void main(String[] args) {
		DataTable dt = ArticleAPI.getPagedDataTable(5954, 2, 0);
		System.out.println(dt.toString());
	}

	public Mapx getParams() {
		return params;
	}

	public void setParams(Mapx params) {
		this.params = params;
		convertParams(params);
	}

	public void convertParams(Mapx params) {
		Iterator iter = params.keySet().iterator();
		while (iter.hasNext()) {
			Object key = iter.next();
			String value = params.getString(key);
			if (StringUtil.isEmpty(value) || "null".equalsIgnoreCase(value)) {
				params.put(key, "");
			}
		}
	}
}
