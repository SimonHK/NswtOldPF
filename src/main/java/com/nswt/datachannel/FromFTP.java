package com.nswt.datachannel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;

import com.nswt.cms.document.Article;
import com.nswt.cms.pub.CatalogUtil;
import com.nswt.framework.Page;
import com.nswt.framework.User;
import com.nswt.framework.cache.CacheManager;
import com.nswt.framework.controls.DataGridAction;
import com.nswt.framework.data.DataCollection;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.ssi.URLEncoder;
import com.nswt.framework.utility.DateUtil;
import com.nswt.framework.utility.HtmlUtil;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.Application;
import com.nswt.platform.pub.NoUtil;
import com.nswt.platform.pub.OrderUtil;
import com.nswt.schema.ZCArticleSchema;
import com.nswt.schema.ZCArticleSet;
import com.nswt.schema.ZCFtpGatherSchema;
import com.nswt.schema.ZCFtpGatherSet;

public class FromFTP extends Page {
	public static Mapx init(Mapx map) {
		String ID = map.getString("ID");
		if (ID != null && ID.trim().length() > 0) {
			ZCFtpGatherSchema ftpSchema = new ZCFtpGatherSchema();
			ftpSchema.setID(ID);
			ftpSchema.fill();
			map.putAll(ftpSchema.toMapx());
			String status = map.getString("Status");
			map.put("radioStatus", HtmlUtil.codeToRadios("Status", "YesOrNo", status));
			map.put("CataLogName", new QueryBuilder(
					"select Name from ZCCataLog where ID=?", ftpSchema
							.getCatalogID()).executeString());
			if(ftpSchema.getRedirectUrl() != null && ftpSchema.getRedirectUrl().trim().length() > 0 && !"http://".equals(ftpSchema.getRedirectUrl())){
				map.put("RedirectURLRadio", StringUtil.javaEncode("Y"));
				map.put("RedirectUrl", ftpSchema.getRedirectUrl());
			}else if(ftpSchema.getContent() != null && ftpSchema.getContent().trim().length() > 0 ){
				map.put("ContentRadio", StringUtil.javaEncode("Y"));
				map.put("Content", ftpSchema.getContent());
			}
			
		} else {
			map.put("RedirectURLRadio", StringUtil.javaEncode("Y"));
			map.put("RedirectUrl", "http://");
			map.put("radioStatus", HtmlUtil.codeToRadios("Status", "YesOrNo", "Y"));
		}
		return map;
	}

	public static Mapx initDialog(Mapx map) {
		String id = map.getString("ID");
		ZCFtpGatherSchema wg = new ZCFtpGatherSchema();
		wg.setID(Long.parseLong(id));
		wg.fill();
		return map;
	}

	public static void dg1DataBind(DataGridAction dga) {
		String sql = "select a.* , b.Name as CataLogName from ZCFTPGather a,ZCCatalog b where a.CatalogID=b.ID and a.SiteID=?";
		DataTable dt = new QueryBuilder(sql, Application.getCurrentSiteID())
				.executeDataTable();
		dt.decodeColumn("Status",  CacheManager.getMapx("Code", "YesOrNo"));
		dga.bindData(dt);
	}

	public void add() {
		String ID = $V("ID");
		String name = $V("Name");
		if (ID == null || ID.trim().length() == 0) {
			if(!checkExit(name)){
				Response.setStatus(0);
				Response.setMessage("任务名称已经存在!");
			}else{
				if(addSave(null,Request)){
					Response.setStatus(1);
					Response.setMessage("新建任务成功!");
				}else {
					Response.setStatus(0);
					Response.setMessage("新建任务失败!");
				}
			}
		}else{
			if(addSave(ID,Request)){
				Response.setStatus(1);
				Response.setMessage("修改任务成功!");
			}else {
				Response.setStatus(0);
				Response.setMessage("修改任务失败!");
			}
		}

	}

	public boolean addSave(String ID,DataCollection dc) {
		ZCFtpGatherSchema ftpSchema = new ZCFtpGatherSchema();
		if (ID != null && ID.trim().length() > 0) {
			ftpSchema.setID(ID);
			ftpSchema.fill();
			ftpSchema.setValue(dc);
			ftpSchema.setModifyTime(new Date());
			ftpSchema.setModifyUser(User.getUserName());
		} else {
			ftpSchema.setAddTime(new Date());
			ftpSchema.setAddUser(User.getUserName());
			ftpSchema.setValue(dc);
			ftpSchema.setSiteID(String.valueOf(Application.getCurrentSiteID()));
			ftpSchema.setID(NoUtil.getMaxID("FtpGatherID"));
		}
		if ($V("CatalogID") != null && $V("CatalogID").trim().length() > 0) {
			ftpSchema.setCatalogID($V("CatalogID"));
		}
		ftpSchema.setTitle($V("Title"));
		ftpSchema.setFtpHost($V("FtpHost"));
		ftpSchema.setFtpPort($V("FtpPort"));
		ftpSchema.setName($V("Name"));
		ftpSchema.setFtpUserName($V("FtpUserName"));
		ftpSchema.setFtpUserPassword($V("FtpUserPassword"));
		ftpSchema.setGatherDrectory($V("GatherDrectory"));
		ftpSchema.setGatherType($V("GatherType"));
		ftpSchema.setRedirectUrl($V("RedirectUrl"));
		ftpSchema.setContent($V("Content"));
		
		Transaction transaction = new Transaction();
		if (ID != null && ID.trim().length() > 0) {
			transaction.add(ftpSchema, Transaction.UPDATE);
		} else {
			transaction.add(ftpSchema, Transaction.INSERT);
		}
		if (transaction.commit()) {
			return true;
		}else {
			return false;
		}
	}

	public boolean checkExit(String Name) {
		String ISD = new QueryBuilder(
				"Select ID from ZCFTPGather where Name like ? ", Name)
				.executeString();
		if (ISD != null && ISD.trim().length() > 0) {
			return false;
		} else {
			return true;
		}
	}

	public void del() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		Transaction trans = new Transaction();
		ZCFtpGatherSchema ftpSchema = new ZCFtpGatherSchema();
		ZCFtpGatherSet set = ftpSchema.query(new QueryBuilder("where id in ("
				+ ids + ")"));
		trans.add(set, Transaction.DELETE_AND_BACKUP);
		trans.add(new QueryBuilder("delete from ZDSchedule where SourceID in ("
				+ ids + ") and TypeCode='FTPCrawl'"));
		if (trans.commit()) {
			Response.setStatus(1);
			Response.setMessage("删除成功！");
		} else {
			Response.setStatus(0);
			Response.setMessage("删除数据失败,请重试!");
		}
	}


	public void execute() {
		String ID = $V("ID");
		if (taskDeal(ID)) {
			Response.setStatus(1);
			Response.setMessage("采集成功");
		} else {
			Response.setStatus(0);
			Response.setMessage("采集失败，请检查FTP配置");
		}
	}

	/**
	 * 实现定时任务采集
	 * 
	 * @param ID
	 */
	public static boolean taskDeal(String ID) {
		FTPClient ftpClient = new FTPClient();
		ZCFtpGatherSchema ftpSchema = new ZCFtpGatherSchema();
		ftpSchema.setID(ID);
		ftpSchema.fill();
		if (ftpSchema.getGatherDrectory() == null
				|| ftpSchema.getGatherDrectory().trim().length() == 0) {
			ftpSchema.setGatherDrectory("/");
		}
		try {
			ftpClient.connect(ftpSchema.getFtpHost(), Integer
					.parseInt(ftpSchema.getFtpPort()));
			ftpClient.login(ftpSchema.getFtpUserName(), ftpSchema
					.getFtpUserPassword());
			FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
			conf.setServerLanguageCode("zh");
			ftpClient.setControlEncoding("utf-8");
			List nameList = getFileName(ftpSchema.getGatherDrectory(),
					ftpClient, ftpSchema.getGatherType(), ftpSchema.getType());
			ftpClient.disconnect();
			if (nameList != null && nameList.size() > 0) {
				addArticle(nameList, ftpSchema);
			}
		} catch (Exception e) {
			LogUtil.info("本次Ftp采集任务失败,连接FTP服务器失败!"
					+ DateUtil.getCurrentDate(DateUtil.Format_DateTime));
			e.printStackTrace();
			
			return false;
		}
		
		return true;
	}

	/**
	 * 递归得到当前目录下需要扫描的文件的名字
	 * 
	 * @param dir
	 * @param ftpClient
	 * @param gatherType
	 * @return
	 */
	public static List getFileName(String dir, FTPClient ftpClient,
			String gatherType, String type) {
		LogUtil.info("FTP文件采集,采集目录为:"+dir);
		FTPFile[] files;
		List nameList = new ArrayList();
		try {
			if (ftpClient.changeWorkingDirectory(new String(
					dir.getBytes("GBK"), "ISO-8859-1"))) {
				files = ftpClient.listFiles(new String(dir.getBytes("GBK"),
						"ISO-8859-1"));
				LogUtil.info("采集到文件数目为:"+ files.length);
				for (int i = 0; i < files.length; i++) {
					FTPFile file = files[i];
					String fileName = file.getName();
					if(".".equals(fileName)||"..".equals(fileName)){
						continue;
					}
					if (!file.isDirectory()){
						String filetype = fileName.substring(fileName
								.lastIndexOf(".") + 1, fileName.length());
						gatherType = gatherType.toLowerCase();
						if (gatherType.indexOf(filetype.toLowerCase()) >= 0) {
							if ("/".equals(dir)) {
								nameList.add(file.getName());
							} else {
								if ("2".equals(type)) {
									Date date = DateUtil.addDay(new Date(), -1);
									if (file.getTimestamp().after(date)) {
										nameList
												.add(dir + "/" + file.getName());
									}
								} else {
									nameList.add(dir + "/" + file.getName());
								}
							}
						}
					} else {
						if ("/".equals(dir)) {
							nameList.addAll(getFileName(dir + file.getName(),
									ftpClient, gatherType, type));
						} else {
							nameList.addAll(getFileName(dir + "/"
									+ file.getName(), ftpClient, gatherType,
									type));
						}
					}
				}
			} else {
				LogUtil.info("FTP文件采集,采集目录为:" + dir + "  连接Ftp服务器失败!");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return nameList;
	}

	/**
	 * 生成文章
	 * 
	 * @param nameList
	 * @param cataLogID
	 */
	public static void addArticle(List nameList, ZCFtpGatherSchema ftpSchema) {
		String cataLogID = ftpSchema.getCatalogID();
		ZCArticleSet articleSet = new ZCArticleSet();

		for (int i = 0; i < nameList.size(); i++) {
			String title = (String) nameList.get(i);
			if (title.startsWith("//")) {
				title = title.substring(1, title.length());
			} else if(title.indexOf("/") > 0 || title.indexOf("/") < 0) {
				title = "/" + title;
			}
			
			String name = title.substring(title.lastIndexOf("/")+1,title.lastIndexOf("."));
			String type = title.substring(title.lastIndexOf(".") + 1, title.length());
			String path = title.substring(0,title.lastIndexOf("/")+1);
			
			String id = new QueryBuilder(
					"Select ID from zcarticle where title like ? and cataLogID =?",
					title, cataLogID).executeString();
			if (id != null) {
				continue;
			}
			ZCArticleSchema article = new ZCArticleSchema();
			article.setID(NoUtil.getMaxID("DocID"));
			article.setSiteID(CatalogUtil.getSiteID(cataLogID));
			article.setCatalogID(cataLogID);
			article.setCatalogInnerCode(CatalogUtil.getInnerCode(cataLogID));
			String artTitle = ftpSchema.getTitle();
			article.setTitle(artTitle.replaceAll("\\$\\{Name\\}", name));
			article.setTopFlag("0");
			article.setTemplateFlag("0");
			article.setContent("");
			article.setCommentFlag("0");
			article.setOrderFlag(OrderUtil.getDefaultOrder());
			article.setHitCount(0);
			article.setStickTime(0);
			article.setPublishFlag("1");
			article.setOrderFlag(OrderUtil.getDefaultOrder());
			article.setStatus(Article.STATUS_DRAFT);
			article.setAddTime(new Date(article.getOrderFlag()));
			article.setAddTime(new Date());
			article.setAddUser("admin");
			String url = ftpSchema.getRedirectUrl();
			String content = ftpSchema.getContent();
			
			if(!"http://".equals(url)){
				URLEncoder encoder = new URLEncoder();
				url = url.replaceAll("\\$\\{Name\\}", encoder.encode(name));
				url = url.replaceAll("\\$\\{Path\\}", encoder.encode(path));
				url = url.replaceAll("\\$\\{Suffix\\}", type);
				article.setRedirectURL(url);
				article.setType("4");
			}else if(content != null && content.trim().length() > 0){
				content = content.replaceAll("\\$\\{Name\\}", name);
				content = content.replaceAll("\\$\\{Suffix\\}", type);
				content = content.replaceAll("\\$\\{Path\\}", path);
				article.setContent(content);
				article.setType("1");
			}
			articleSet.add(article);
		}
		Transaction transaction = new Transaction();
		transaction.add(articleSet, Transaction.INSERT);
		if (transaction.commit()) {
			LogUtil.info("生成文章成功!");
		} else {
			LogUtil.info("生成文章失败!");
		}
	}

	 public static void main(String[] args) {
		FTPClient ftpClient = new FTPClient();
		ZCFtpGatherSchema ftpSchema = new ZCFtpGatherSchema();
		ftpSchema.setID("70");
		ftpSchema.fill();
		if (ftpSchema.getGatherDrectory() == null
				|| ftpSchema.getGatherDrectory().trim().length() == 0) {
			ftpSchema.setGatherDrectory("/");
		}
		try {
			ftpClient.connect(ftpSchema.getFtpHost(), Integer
					.parseInt(ftpSchema.getFtpPort()));
			ftpClient.login(ftpSchema.getFtpUserName(), ftpSchema
					.getFtpUserPassword());
			ftpClient.setControlEncoding("utf-8");
			FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
			conf.setServerLanguageCode("zh");
			List nameList = getFileName(ftpSchema.getGatherDrectory(),
					ftpClient, ftpSchema.getGatherType(), ftpSchema.getType());
			ftpClient.disconnect();
			if (nameList != null && nameList.size() > 0) {
				addArticle(nameList, ftpSchema);
			}
		} catch (Exception e) {
			LogUtil.info("本次Ftp采集任务失败!"
					+ DateUtil.getCurrentDate(DateUtil.Format_DateTime));
			e.printStackTrace();
		}
	}
}
