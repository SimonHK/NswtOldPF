package com.nswt.cms.pub;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.nswt.cms.document.Article;
import com.nswt.cms.site.ImagePlayerBasic;
import com.nswt.cms.site.ImagePlayerRela;
import com.nswt.cms.template.HtmlNameParser;
import com.nswt.cms.template.HtmlNameRule;
import com.nswt.framework.Config;
import com.nswt.framework.User;
import com.nswt.framework.cache.CacheManager;
import com.nswt.framework.data.DataRow;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.utility.FileUtil;
import com.nswt.framework.utility.Filter;
import com.nswt.framework.utility.HtmlUtil;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.Application;
import com.nswt.platform.pub.PlatformCache;
import com.nswt.schema.ZCArticleSchema;
import com.nswt.schema.ZCCatalogSchema;
import com.nswt.schema.ZCImagePlayerSchema;
import com.nswt.schema.ZCImagePlayerSet;
import com.nswt.schema.ZCImagePlayerStyleSchema;
import com.nswt.schema.ZCSiteSchema;
import com.nswt.schema.ZDBranchSchema;
import com.nswt.schema.ZDDistrictSchema;
import com.nswt.schema.ZDRoleSchema;
import com.nswt.schema.ZDUserSchema;

/**
 * cms公共函数
 * 
 * @Author 兰军
 * @Date 2007-8-20
 * @Mail lanjun@nswt.com
 */
public class PubFun {

	public static final String INDENT = "　"; // 缩进

	public static final String SEPARATE = "|";

	public static String getBranchOptions() {
		return getBranchOptions(null);
	}

	/**
	 * 得到机构下拉框，供别的地方调用
	 * 
	 * @return
	 */
	public static String getBranchOptions(Object checkedValue) {
		QueryBuilder qb=new QueryBuilder("select Name,BranchInnerCode,TreeLevel from ZDBranch ");
		List roleCodeList = PubFun.getRoleCodesByUserName(User.getUserName());
		if (roleCodeList != null && roleCodeList.size() != 0) {
			if(!roleCodeList.contains("admin")){
				qb.append(" where BranchInnerCode like ? order by OrderFlag",User.getBranchInnerCode() + "%");
			}
		}
		DataTable dt = qb.executeDataTable();
		PubFun.indentDataTable(dt);
		return HtmlUtil.dataTableToOptions(dt, checkedValue);
	}
	public static List getRoleCodesByUserName(String userName) {
		String roles = (String) CacheManager.get(PlatformCache.ProviderName, "UserRole", userName);
		if (roles == null) {
			return null;
		}
		String[] arr = roles.split(",");
		ArrayList list = new ArrayList();
		for (int i = 0; i < arr.length; i++) {
			if (StringUtil.isNotEmpty(arr[i])) {
				list.add(arr[i]);
			}
		}
		return list;
	}

	public static String getRoleName(String roleCode) {
		ZDRoleSchema role = (ZDRoleSchema) CacheManager.get(PlatformCache.ProviderName, "Role", roleCode);
		if (role == null) {
			return null;
		}
		return role.getRoleName();
	}

	public static ZDBranchSchema getBranch(String innerCode) {
		return PlatformCache.getBranch(innerCode);
	}

	public static String getBranchName(String innerCode) {
		ZDBranchSchema branch = PlatformCache.getBranch(innerCode);
		if (branch == null) {
			branch = PlatformCache.getBranch("0001");
		}
		if (branch != null) {
			return branch.getName();
		}
		return null;
	}

	public static String getRoleNames(List roleCodes) {
		if (roleCodes == null || roleCodes.size() == 0) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		boolean first = false;
		for (int i = 0; i < roleCodes.size(); i++) {
			String roleName = getRoleName((String) roleCodes.get(i));
			if (StringUtil.isNotEmpty(roleName)) {
				if (first) {
					sb.append(",");
				}
				sb.append(roleName);
				first = true;
			}
		}
		return sb.toString();
	}

	/**
	 * 缩进dataTable中的第一列
	 * 
	 * @param dt
	 *            默认形式类似于 select name,id,level from table
	 */
	public static void indentDataTable(DataTable dt) {
		indentDataTable(dt, 0, 2, 1);
	}

	/**
	 * @param dt
	 * @param n
	 *            哪一列需要缩进
	 * @param m
	 *            根据哪一列缩进
	 * @param firstLevel
	 *            第一层级
	 * @return
	 */
	public static void indentDataTable(DataTable dt, int n, int m, int firstLevel) {
		for (int i = 0; i < dt.getRowCount(); i++) {
			int level = Integer.parseInt(dt.getString(i, m));
			StringBuffer sb = new StringBuffer();
			for (int j = firstLevel; j < level; j++) {
				sb.append(PubFun.INDENT);
			}
			dt.set(i, n, sb.toString() + dt.getString(i, n));
		}
	}

	/**
	 * 当前页面路径
	 * 
	 * @param catalogID
	 * @return
	 */
	public static String getCurrentPage(long catalogID, int level, String name, String separator, String target) {
		String levelString = (level == 0) ? "./" : "";
		for (int i = 0; i < level; i++) {
			levelString += "../";
		}
		ZCCatalogSchema catalog = CMSCache.getCatalog(catalogID);
		long parentID = catalog.getParentID();

		String linkText = catalog.getName();
		String href = CatalogUtil.getLink(catalogID, levelString);
		String text = "";
		if (!"N".equals(catalog.getPublishFlag())) {
			text = " " + separator + " <a href='" + href + "' target='" + target + "'>" + linkText + "</a>";
		}
		if (parentID == 0) {
			text = "<a href='" + levelString + "' target='" + target + "'>首页</a>" + text;
		} else {
			text = getCurrentPage(parentID, level, name, separator, target) + text;
		}
		return text;
	}

	/**
	 * 
	 * @param article
	 * @return
	 */
	public static String getArticleURL(ZCArticleSchema article) {
		ZCCatalogSchema catalog = CatalogUtil.getSchema(article.getCatalogID());
		ZCSiteSchema site = SiteUtil.getSchema("" + article.getSiteID());
		return getArticleURL(site, catalog, article);
	}

	public static String getDocURL(DataRow doc) {
		ZCCatalogSchema catalog = CatalogUtil.getSchema(doc.getLong("CatalogID"));
		ZCSiteSchema site = SiteUtil.getSchema(doc.getLong("SiteID"));
		HtmlNameParser nameParser = new HtmlNameParser(site.toDataRow(), catalog.toDataRow(), doc, catalog
				.getDetailNameRule());
		HtmlNameRule h = nameParser.getNameRule();
		String url = h.getFullPath();
		if(url.indexOf(".")!=-1){
			url = url.substring(0, url.indexOf(".")+1)+SiteUtil.getExtensionType(site.getID());
		}
		return url;
	}

	public static String getArticleURL(ZCSiteSchema site, ZCCatalogSchema catalog, ZCArticleSchema article) {
		HtmlNameParser nameParser = new HtmlNameParser(site.toDataRow(), catalog.toDataRow(), article.toDataRow(),
				catalog.getDetailNameRule());
		HtmlNameRule h = nameParser.getNameRule();
		String url = h.getFullPath();
		if(url.indexOf(".")!=-1){
			url = url.substring(0, url.indexOf(".")+1)+SiteUtil.getExtensionType(site.getID());
		}
		return url;
	}

	public static String getGoodsURL(String ID) {
		DataTable dt = new QueryBuilder("select catalogID from zsgoods where ID=?", ID).executeDataTable();
		long catalogID = Long.parseLong(dt.get(0, "catalogID").toString());
		String url = CatalogUtil.getPath(catalogID) + ID + ".shtml";
		return url;
	}

	/**
	 * 根据图片ID得到图片的路径
	 * 
	 * @param imageID
	 * @return
	 */
	public static String getImagePath(String imageID, String imageIndex) {
		String imagePath = null;
		if (StringUtil.isEmpty(imageIndex)) {
			imageIndex = "src";
		}
		if (StringUtil.isEmpty(imageID)) {
			imageID = "0";
		}
		String imageSql = "select id,path,catalogID,filename,SrcFileName from zcimage where id=?";
		DataTable dtImage = new QueryBuilder(imageSql, imageID).executeDataTable();
		if (dtImage != null && dtImage.getRowCount() > 0) {
			if ("src".equals(imageIndex)) {
				imagePath = dtImage.getString(0, "path") + dtImage.getString(0, "SrcFileName");
			} else {
				imagePath = dtImage.getString(0, "path") + imageIndex + "_" + dtImage.getString(0, "fileName");
			}
		}
		return imagePath;
	}

	public static String getImagePath(long imageID, String imageIndex) {
		return getImagePath(imageID + "", imageIndex);
	}

	public static String getImagePath(long imageID) {
		return getImagePath(imageID + "", null);
	}

	public static String getUserRealName(String userName) {
		ZDUserSchema user = PlatformCache.getUser(userName);
		if (user == null) {
			return "";
		} else {
			return user.getRealName();
		}
	}

	public static String getImagePath(String imageID) {
		return getImagePath(imageID, null);
	}

	public static String getImagePlayer(String ImagePlayCode) {
		ZCImagePlayerSchema imagePlayer = new ZCImagePlayerSchema();
		imagePlayer.setCode(ImagePlayCode);
		ZCImagePlayerSet set = imagePlayer.query();
		if (set.size() > 0) {
			imagePlayer = set.get(0);
		} else {
			LogUtil.info("没有" + ImagePlayCode + "对应的图片播放器，请检查" + ImagePlayCode + "是否正确。");
		}
		return getImagePlayerHTML(imagePlayer);
	}

	/**
	 * 获取图片播放器html代码
	 * 
	 * @param imagePlayer
	 * @return
	 */
	
	public static String getImagePlayerHTML(ZCImagePlayerSchema imagePlayer) {
		
		Pattern playerImg = Pattern.compile("<playerImgArea>(.*?)</playerImgArea>",Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		Pattern playerNav = Pattern.compile("<playerNavArea>(.*?)</playerNavArea>",Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		
		String Alias = Config.getValue("UploadDir") + "/" + SiteUtil.getAlias(Application.getCurrentSiteID()) + "/";
		Alias = Alias.replaceAll("///", "/");
		Alias = Alias.replaceAll("//", "/");


		ArrayList pics = new ArrayList();// 图片
		ArrayList links = new ArrayList();// 链接
		ArrayList texts = new ArrayList();// 显示文本
		ArrayList summarys = new ArrayList();// 新闻摘要
		DataTable dt = null;
		
		int imageCount = (int)imagePlayer.getDisplayCount();
		
		// 和栏目关联，取文章里面的图片
		if (ImagePlayerBasic.IMAGESOURCE_CATALOG_FIRST.equals(imagePlayer.getImageSource())) {
			String catalogStr = " and cataloginnercode like '%'";
			if (StringUtil.isNotEmpty(imagePlayer.getRelaCatalogInnerCode())
					&& !"null".equalsIgnoreCase(imagePlayer.getRelaCatalogInnerCode())) {
				catalogStr = " and cataloginnercode like '" + imagePlayer.getRelaCatalogInnerCode() + "%'";
			}

			String attributeSql = " and attribute like '%image%'";
			String typeStr = " order by publishdate desc,orderflag desc, id desc";
			dt = new QueryBuilder("select * from zcarticle where siteID=?" + catalogStr + " and status in("
					+ Article.STATUS_TOPUBLISH + "," + Article.STATUS_PUBLISHED
					+ ") and (publishdate<=? or publishdate is null) " + attributeSql + typeStr, imagePlayer
					.getSiteID(), new Date()).executePagedDataTable(imageCount, 0);
			dt.insertColumns(new String[] { "FirstImagePath" });
			dealArticleMedia(dt, null, "image");

			for (int i = 0; i < dt.getRowCount(); i++) {
				String imagePath = "";
				if (StringUtil.isNotEmpty(dt.getString(i, "FirstImagePath"))) {
					imagePath = dt.getString(i, "FirstImagePath").substring(
							dt.getString(i, "FirstImagePath").indexOf("upload/"));
				}
				pics.add(".." + Alias + imagePath);
				String siteUrl = SiteUtil.getURL(dt.getString(i, "SiteID"));
				if (siteUrl.endsWith("shtml")) {
					siteUrl = siteUrl.substring(0, siteUrl.lastIndexOf("/"));
				}

				if (!siteUrl.endsWith("/")) {
					siteUrl = siteUrl + "/";
				}

				links.add(siteUrl + getDocURL(dt.getDataRow(i)));
				if (StringUtil.isNotEmpty(dt.getString(i, "ShortTitle"))) {
					texts.add(dt.getString(i, "ShortTitle"));
				} else {
					texts.add(dt.getString(i, "Title"));
				}
				if (StringUtil.isNotEmpty(dt.getString(i, "Summary"))) {
					summarys.add(dt.getString(i, "Summary"));
				} else {
					summarys.add("");
				}
			}

		} else {
			String sql = "select b.* from ZCImageRela a,zcimage b where a.id = b.id  and a.RelaID="
					+ imagePlayer.getID() + " and a.RelaType='" + ImagePlayerRela.RELATYPE_IMAGEPLAYER
					+ "' order by a.orderflag desc, a.addtime desc";
			dt = new QueryBuilder(sql).executePagedDataTable(imageCount, 0);

			for (int i = 0; i < dt.getRowCount(); i++) {
				pics.add(".." + Alias + dt.getString(i, "path") + "1_" + dt.getString(i, "FileName"));
				if (StringUtil.isNotEmpty(Alias + dt.getString(i, "LinkURL"))) {
					links.add(dt.getString(i, "LinkURL"));
				}else{
					links.add("#;");
				}
				String text = dt.getString(i, "LinkText");
				if (StringUtil.isNotEmpty(text)) {
					texts.add(text);
				}else{
					texts.add("");
				}
				if (StringUtil.isNotEmpty(dt.getString(i, "Info"))) {
					summarys.add(dt.getString(i, "Info"));
				}else{
					summarys.add("");
				}
			}
		}

		int Width = (int) imagePlayer.getWidth();
		int Height = (int) imagePlayer.getHeight();
		
		//获取播放器样式
		ZCImagePlayerStyleSchema style = new ZCImagePlayerStyleSchema();
		style.setID(imagePlayer.getStyleID());
		style.fill();
		String StyleID = style.getID()+"";
		String template = style.getTemplate();
		String content = FileUtil.readText(Config.getContextRealPath()+template);
		if(StringUtil.isNotEmpty(content)){
			content = StringUtil.replaceEx(content,"${PlayerID}", imagePlayer.getID()+"");
			content = StringUtil.replaceEx(content,"${StyleDirPath}", "../Upload/ImagePlayerStyles/"+style.getID()+"/");
			content = StringUtil.replaceEx(content,"${StyleID}", StyleID);
		}
		Matcher m = playerImg.matcher(content);
		String picArea = "";
		String pic_before = "";
		String pic_after = "";
		String navArea = "";
		String nav_before = "";
		String nav_after = "";
		if (m.find(0)) {
			pic_before = content.substring(0,m.start());
			pic_after = content.substring(m.end());
			picArea = m.group(1);
		}
		if(StringUtil.isNotEmpty(pic_before)){
			pic_before = StringUtil.replaceEx(pic_before, "${PlayerID}", imagePlayer.getID()+"");
			pic_before = StringUtil.replaceEx(pic_before, "${Height}", Height+"");
			pic_before = StringUtil.replaceEx(pic_before, "${Width}", Width+"");
		}
		if(StringUtil.isNotEmpty(nav_before)){
			nav_before = StringUtil.replaceEx(nav_before, "${PlayerID}", imagePlayer.getID()+"");
		}
		if(StringUtil.isNotEmpty(pic_after)){
			pic_after = StringUtil.replaceEx(pic_after, "${PlayerID}", imagePlayer.getID()+"");
			pic_after = StringUtil.replaceEx(pic_after, "${Height}", Height+"");
			pic_after = StringUtil.replaceEx(pic_after, "${Width}", Width+"");
		}
		//解析生成图片列表
		StringBuffer strB = new StringBuffer();
		for (int i = 0; i < pics.size(); i++) {
			if(StringUtil.isNotEmpty(picArea)){
				String temp = StringUtil.replaceEx(picArea, "${Width}", Width+"");
				temp = StringUtil.replaceEx(temp, "${Height}", Height+"");
				temp = StringUtil.replaceEx(temp, "${Alt}", texts.get(i).toString());
				temp = StringUtil.replaceEx(temp, "${URL}", links.get(i).toString());
				temp = StringUtil.replaceEx(temp, "${ImgSrc}", pics.get(i).toString());
				temp = StringUtil.replaceEx(temp, "${Summary}", summarys.get(i).toString());
				strB.append(temp);
			}
		}
		content = pic_before + strB.toString() + pic_after;
		
		Matcher m_Nav = playerNav.matcher(content);
		if(m_Nav.find(0)){
			nav_before = content.substring(0,m_Nav.start());
			nav_after = content.substring(m_Nav.end());
			navArea = m_Nav.group(1);
		}
		if(StringUtil.isNotEmpty(nav_after)){
			nav_after = StringUtil.replaceEx(nav_after, "${PlayerID}", imagePlayer.getID()+"");
		}
		//解析生成导航列表
		if(StringUtil.isNotEmpty(navArea)){
			strB = new StringBuffer();
			for (int i = 0; i < pics.size(); i++) {
				String temp = StringUtil.replaceEx(navArea, "${PicNo}", (i+1)+"");
				temp = StringUtil.replaceEx(temp, "${thumbImgSrc}", StringUtil.replaceEx(pics.get(i).toString(),"1_","s_"));
				strB.append(temp);
			}
			content = nav_before+strB.toString()+nav_after;
		}
		return content;
	}

	/**
	 * 处理文章相关附件
	 * 
	 * @param dt
	 */

	public static void dealArticleMedia(DataTable dt) {
		dealArticleMedia(dt, null, "image,attchment,video");
	}

	/**
	 * 处理文章相关附件
	 * 
	 * @param dt
	 * @param levelString
	 * @param hasAttribute
	 */

	public static void dealArticleMedia(DataTable dt, String levelString, String hasAttribute) {
		for (int i = 0; i < dt.getRowCount(); i++) {
			dealArticleMedia(dt.getDataRow(i), levelString, hasAttribute);
		}
	}

	/**
	 * 处理文章相关附件
	 * 
	 * @param dr
	 */

	public static void dealArticleMedia(DataRow dr) {
		dealArticleMedia(dr, null, "image,attchment,video");
	}

	/**
	 * 处理文章相关附件
	 * 
	 * @param dr
	 * @param hasAttribute
	 */

	public static void dealArticleMedia(DataRow dr, String hasAttribute) {
		dealArticleMedia(dr, null, hasAttribute);
	}

	/**
	 * 处理文章相关附件
	 * 
	 * @param dr
	 * @param levelString
	 * @param hasAttribute
	 */

	public static void dealArticleMedia(DataRow dr, String levelString, String hasAttribute) {
		String attribute = dr.getString("Attribute");

		if (levelString == null || "null".equalsIgnoreCase(levelString)) {
			levelString = PubFun.getLevelStr(CatalogUtil.getDetailLevel(dr.getString("CatalogID")));
		}

		if (StringUtil.isNotEmpty(attribute) && StringUtil.isNotEmpty(hasAttribute)) {
			// 处理图片新闻
			if (hasAttribute.indexOf("image") != -1) {
				if (attribute.indexOf("image") != -1) {
					DataTable imageRelaDT = new QueryBuilder(
							"select b.path,b.filename from zcimagerela a,zcimage b where "
									+ "a.relaID=? and a.relatype=? and a.ID=b.ID order by a.orderflag", dr
									.getLong("ID"), Article.RELA_IMAGE).executeDataTable();
					if (imageRelaDT != null && imageRelaDT.getRowCount() > 0) {
						dr.set("FirstImagePath", levelString + imageRelaDT.getString(0, "path") + "1_"
								+ imageRelaDT.getString(0, "filename"));
					} else {
						dr.set("FirstImagePath", levelString + "upload/Image/nopicture.jpg");
					}
				} else {
					dr.set("FirstImagePath", levelString + "upload/Image/nopicture.jpg");
				}
			}

			// 处理附件新闻
			// if (hasAttribute.indexOf("attchment")!=-1) {
			// if (attribute.indexOf("attchment")!=-1) {
			// DataTable attachRelaDT = new
			// QueryBuilder("select b.ID,b.Name,b.suffix,b.path,b.filename,b.imagepath from "
			// +
			// "zcattachmentrela a,zcattachment b where a.relaID=? and a.relatype=? and a.ID=b.ID"
			// , dr.getString("ID"), Article.RELA_ATTACH).executeDataTable();
			// if (attachRelaDT!=null && attachRelaDT.getRowCount()>0) {
			// dr.set("RelaAttach", attachRelaDT.getString(0, "name") + "." +
			// attachRelaDT.getString(0, "suffix"));
			// dr.set("RelaAttachImage", levelString + attachRelaDT.getString(0,
			// "imagepath"));
			// if (StringUtil.isNotEmpty(dr.getString("catalogID"))
			// &&
			// "N".equals(CatalogUtil.getAttachDownFlag(dr.getString("catalogID"))))
			// {
			// dr.set("RelaAttachPath", levelString + attachRelaDT.getString(0,
			// "path")
			// + attachRelaDT.getString(0, "filename"));
			// } else if
			// ("N".equals(SiteUtil.getAttachDownFlag(dr.getString("siteID"))))
			// {
			// dr.set("RelaAttachPath", levelString + attachRelaDT.getString(0,
			// "path")
			// + attachRelaDT.getString(0, "filename"));
			// } else {
			// dr.set("RelaAttachPath", Config.getValue("ServicesContext") +
			// "/AttachDownLoad.jsp?id="
			// + attachRelaDT.getString(0, "id"));
			// }
			// }
			// } else {
			// dr.set("RelaAttach", "");
			// dr.set("RelaAttachPath", "");
			// dr.set("RelaAttachImage", "");
			// }
			// }

			// 处理视频新闻
			if (hasAttribute.indexOf("video") != -1) {
				if (attribute.indexOf("video") != -1) {
					DataTable videoRelaDT = new QueryBuilder(
							"select ID from zcvideorela where relaID=? and relatype=?", dr.getLong("ID"),
							Article.RELA_VIDEO).executeDataTable();
					if (videoRelaDT != null && videoRelaDT.getRowCount() > 0) {
						DataTable videoDT = new QueryBuilder(
								"select id,name,suffix,path,filename,srcfilename,imageName "
										+ "from zcvideo where id=?", videoRelaDT.getString(0, "ID")).executeDataTable();
						if (videoDT != null && videoDT.getRowCount() > 0) {
							dr.set("FirstVideoImage", levelString + videoDT.getString(0, "path")
									+ videoDT.getString(0, "imageName"));
							dr.set("FirstVideoHtml", getVideoHtml(videoDT.getString(0, "ID"), levelString));
						}
					}
				} else {
					dr.set("FirstVideoImage", "");
					dr.set("FirstVideoHtml", "");
				}
			}
		} else {
			dr.set("FirstImagePath", "");
			// dr.set("RelaAttach", "");
			// dr.set("RelaAttachPath", "");
			// dr.set("RelaAttachImage", "");
			dr.set("FirstVideoImage", "");
			dr.set("FirstVideoHtml", "");
		}
	}

	/**
	 * 根据视频ID获取播放视频的html代码
	 * 
	 * @param videoID
	 * @return
	 */

	public static String getVideoHtml(long videoID) {
		return getVideoHtml(videoID, null);
	}

	/**
	 * 根据视频ID获取播放视频的html代码
	 * 
	 * @param videoID
	 * @param levelString
	 * @return
	 */

	public static String getVideoHtml(long videoID, String levelString) {
		return getVideoHtml(videoID + "", levelString);
	}

	/**
	 * 根据视频ID获取播放视频的html代码
	 * 
	 * @param videoID
	 * @param levelString
	 * @return relaVideoHtml
	 */

	public static String getVideoHtml(String videoID, String levelString) {
		DataTable videoDT = new QueryBuilder("select id,catalogID,name,suffix,path,filename,srcfilename,imageName "
				+ "from zcvideo where id=?", videoID).executeDataTable();
		if (videoDT != null && videoDT.getRowCount() > 0) {
			return getVideoHtml(videoDT.getDataRow(0), levelString);
		} else {
			return "";
		}
	}

	public static String getVideoHtml(DataRow dr) {
		return getVideoHtml(dr, null);
	}

	public static String getVideoHtml(DataRow dr, String levelString) {
		if (StringUtil.isEmpty(levelString) || "null".equalsIgnoreCase(levelString)) {
			levelString = getLevelStr(CatalogUtil.getDetailLevel(dr.getString("CatalogID")));
		}

		String files = "../" + dr.getString("path") + dr.getString("filename");
		String relaVideoHtml = "<script type='text/javascript' src='" + levelString
				+ "images/Swfobject.js'></script><script type='text/javascript'>" + "var s1 = new SWFObject('"
				+ levelString + "images/player.swf','player','270','225','9','#FFFFFF');"
				+ "s1.addParam('allowfullscreen','true');" + "s1.addParam('allowscriptaccess','always');"
				+ "s1.addParam('flashvars','file=" + files + "');" + "s1.write('container');</script>";
		return relaVideoHtml;
	}

	/**
	 * 根据地区代码得到省市区的下拉列表
	 */
	public static DataTable District = null;

	private static Mapx DistrictMap = null;
	
	private static Object mutex = new Object();

	public static void initDistrict() {
		District = new QueryBuilder("select Name,Code,TreeLevel,Type from zddistrict").executeDataTable();
		Mapx map = new Mapx();
		for (int i = 0; i < District.getRowCount(); i++) {
			map.put(District.get(i, 1), District.get(i, 0));
		}
		DistrictMap = map;
	}

	public static Mapx getDistrictMap() {
		if (DistrictMap == null) {
			synchronized (mutex) {
				if (DistrictMap == null) {
					initDistrict();
				}
			}
		}
		return DistrictMap;
	}

	public static DataTable getProvince() {
		if (District == null) {
			initDistrict();
		}
		return District.filter(new Filter() {
			public boolean filter(Object o) {
				DataRow dr = (DataRow) o;
				if ("1".equals(dr.get("TreeLevel"))) {
					return true;
				}
				return false;
			}
		});
	}

	public static DataTable getCity(String Province) {
		if (District == null) {
			initDistrict();
		}
		if (StringUtil.isNotEmpty(Province)) {
			ZDDistrictSchema district = new ZDDistrictSchema();
			district.setCode(Province);
			district.fill();
			if ("0".equals(district.getType())) {
				return District.filter(new Filter(Province) {
					public boolean filter(Object o) {
						DataRow dr = (DataRow) o;
						if (((String) this.Param).equals(dr.getString("Code"))) {
							return true;
						}
						return false;
					}
				});
			} else {
				return District.filter(new Filter(Province) {
					public boolean filter(Object o) {
						DataRow dr = (DataRow) o;
						if ("2".equals(dr.getString("TreeLevel"))
								&& dr.getString("Code").substring(0, 2).equals(((String) this.Param).substring(0, 2))) {
							return true;
						}
						return false;
					}
				});
			}
		} else {
			return new DataTable();
		}
	}

	public static DataTable getDistrict(String City) {
		if (District == null) {
			initDistrict();
		}
		if (StringUtil.isNotEmpty(City)) {
			ZDDistrictSchema district = new ZDDistrictSchema();
			district.setCode(City);
			district.fill();
			if ("0".equalsIgnoreCase(district.getType())) {
				return District.filter(new Filter(City) {
					public boolean filter(Object o) {
						DataRow dr = (DataRow) o;
						if ("3".equals(dr.get("TreeLevel").toString())
								&& dr.get("Code").toString().substring(0, 3).equals(
										((String) this.Param).substring(0, 3))) {
							return true;
						}
						return false;
					}
				});
			} else {
				return District.filter(new Filter(City) {
					public boolean filter(Object o) {
						DataRow dr = (DataRow) o;
						if ("3".equals(dr.getString("TreeLevel"))
								&& dr.get("Code").toString().substring(0, 4).equals(
										((String) this.Param).substring(0, 4))) {
							return true;
						}
						return false;
					}
				});
			}
		} else {
			return new DataTable();
		}
	}

	/**
	 * @param ext
	 *            文件后缀名
	 * @return
	 */
	public static boolean isAllowExt(String ext) {
		return isAllowExt(ext, "All");
	}

	/**
	 * @param ext
	 *            文件后缀名
	 * @param extType
	 *            后缀名类型 附件-Attach,图片-Image,视频-Video,音频-Audio,所有-All
	 * @return
	 */
	public static boolean isAllowExt(String ext, String fileType) {
		boolean allowed = false;
		if (StringUtil.isNotEmpty(ext)) {
			String[] extArr = null;
			ArrayList allowList = new ArrayList();
			String[] typeArr = new String[1];
			if (fileType.equalsIgnoreCase("All")) {
				typeArr = "Attach,Image,Video,Audio,All".split(",");
			} else {
				typeArr[0] = fileType;
			}
			for (int i = 0; i < typeArr.length; i++) {
				String allowExt = Config.getValue("Allow" + typeArr[i] + "Ext");
				if (StringUtil.isNotEmpty(allowExt)) {
					extArr = allowExt.split(",");
					for (int j = 0; j < extArr.length; j++) {
						allowList.add(extArr[j].toLowerCase());
					}
				}
			}
			allowed = allowList.contains(ext.toLowerCase());
		}
		return allowed;
	}

	public static String getLevelStr(long level) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < level; i++) {
			sb.append("../");
		}
		return sb.toString();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String s = PubFun.getCurrentPage(11001, 2, "", ">", "_self");
		System.out.println(s);
	}

}
