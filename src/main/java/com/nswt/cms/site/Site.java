package com.nswt.cms.site;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nswt.MavenUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.nswt.cms.dataservice.FullText;
import com.nswt.cms.pub.CatalogUtil;
import com.nswt.cms.pub.SiteExporter;
import com.nswt.cms.pub.SiteImporter;
import com.nswt.cms.pub.SiteTableRela;
import com.nswt.cms.pub.SiteUtil;
import com.nswt.cms.pub.SiteTableRela.TableRela;
import com.nswt.cms.resource.ConfigImageLib;
import com.nswt.framework.Config;
import com.nswt.framework.Page;
import com.nswt.framework.User;
import com.nswt.framework.controls.DataGridAction;
import com.nswt.framework.data.BlockingTransaction;
import com.nswt.framework.data.DataRow;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
//import com.nswt.framework.license.LicenseInfo;
import com.nswt.framework.messages.LongTimeTask;
import com.nswt.framework.utility.DateUtil;
import com.nswt.framework.utility.FileUtil;
import com.nswt.framework.utility.Filter;
import com.nswt.framework.utility.HtmlUtil;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.Application;
import com.nswt.platform.Priv;
import com.nswt.platform.RolePriv;
import com.nswt.platform.UserLog;
import com.nswt.platform.pub.NoUtil;
import com.nswt.platform.pub.OrderUtil;
import com.nswt.schema.ZCCatalogConfigSchema;
import com.nswt.schema.ZCCatalogSchema;
import com.nswt.schema.ZCPageBlockSchema;
import com.nswt.schema.ZCSiteSchema;
import com.nswt.schema.ZCSiteSet;
import com.nswt.schema.ZDPrivilegeSchema;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

/**
 * Ӧ�ù���
 */
public class Site extends Page {

	public static Mapx init(Mapx params) {
		ZCSiteSchema site = new ZCSiteSchema();
		site.setID(Application.getCurrentSiteID());
		site.fill();

		return site.toMapx();
	}

	public static Mapx initDialog(Mapx params) {
		Object o1 = params.get("ID");
		if (o1 != null) {
			long ID = Long.parseLong(params.get("ID").toString());
			ZCSiteSchema site = new ZCSiteSchema();
			site.setID(ID);
			site.fill();

			params.putAll(site.toMapx());

			String indexFlag = site.getAutoIndexFlag();
			if (StringUtil.isEmpty(indexFlag)) {
				indexFlag = "Y";
			}
			params.put("radioAutoIndexFlag", HtmlUtil.codeToRadios("AutoIndexFlag", "YesOrNo", indexFlag));

			String statFlag = site.getAutoStatFlag();
			if (StringUtil.isEmpty(statFlag)) {
				statFlag = "Y";
			}
			params.put("radioAutoStatFlag", HtmlUtil.codeToRadios("AutoStatFlag", "YesOrNo", statFlag));

			params
					.put("radioBBSEnableFlag", HtmlUtil.codeToRadios("BBSEnableFlag", "YesOrNo", site
							.getBBSEnableFlag()));
			params.put("radioShopEnableFlag", HtmlUtil.codeToRadios("ShopEnableFlag", "YesOrNo", site
					.getShopEnableFlag()));

			String auditFlag = site.getCommentAuditFlag();
			params.put("radioCommentAuditFlag", HtmlUtil.codeToRadios("CommentAuditFlag", "YesOrNo", auditFlag));
			params.put("radioAllowContribute", HtmlUtil.codeToRadios("AllowContribute", "YesOrNo", site
					.getAllowContribute()));
			return params;
		} else {
			params.put("Prop1", "shtml");//��չ��
			params.put("URL", "http://");
			params.put("radioAutoIndexFlag", HtmlUtil.codeToRadios("AutoIndexFlag", "YesOrNo", "Y"));
			params.put("radioAutoStatFlag", HtmlUtil.codeToRadios("AutoStatFlag", "YesOrNo", "Y"));
			params.put("radioCommentAuditFlag", HtmlUtil.codeToRadios("CommentAuditFlag", "YesOrNo", "Y"));
			params.put("radioAllowContribute", HtmlUtil.codeToRadios("AllowContribute", "YesOrNo", "N"));
			return params;
		}
	}

	public void saveTemplate() {
		Transaction trans = new Transaction();
		ZCSiteSchema site = new ZCSiteSchema();
		site.setID(Long.parseLong($V("ID")));
		site.fill();
		site.setValue(Request);
		site.setModifyUser(User.getUserName());
		site.setModifyTime(new Date());

		trans.add(site, Transaction.UPDATE);

		if (trans.commit()) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("�������ݷ�������!");
		}
	}

	public static void dg1DataBind(DataGridAction dga) {
		QueryBuilder qb = new QueryBuilder("select * from ZCSite order by orderflag");
		dga.bindData(qb);
	}

	/**
	 * �������ʱʹ�ñ�����
	 */
	public void add() {
		Transaction trans = new Transaction();
		long id = NoUtil.getMaxID("SiteID");
		if (new QueryBuilder("select count(*) from zcsite where Alias = ?", $V("Alias")).executeInt() > 0) {
			Response.setLogInfo(0, "�½�Ӧ��ʧ��,�ظ���Ŀ¼��");
			return;
		}
		if (!add(trans, id) || !trans.commit()) {
			Response.setLogInfo(0, "�½�Ӧ��ʧ��");
			UserLog.log(UserLog.SITE, UserLog.SITE_ADDSITE, "����Ӧ��ʧ�ܣ�", Request.getClientIP());
		} else {

			ZCSiteSchema site = SiteUtil.getSchema(id + "");
			String path = SiteUtil.getAbsolutePath(site.getID());

			MavenObject mavenObject = new MavenObject();
			String workpath = Config.getContextRealPath()+(Config.getValue("Statical.TargetDir").substring(1));
			String shellFilePath = Config.getClassesPath()+"createApp.sh";
			String shellFilePath2 = Config.getClassesPath()+"createAppAllparm.sh";
			//��ģ�帳ִ��Ȩ����macϵͳ��linuxϵͳ��ʹ��
			String systemName = Config.getValue("System.OSName").substring(0,1).toLowerCase();
			switch (systemName){
				case "m" : //MACϵͳ
					MavenUtil.setpermission(shellFilePath2);
					break;
				case "l" : //��Linuxϵͳ
					MavenUtil.setpermission(shellFilePath2);
					break;
				case "r" : //RedHatϵͳ
					MavenUtil.setpermission(shellFilePath2);
					break;
				case "c" : //CentOSϵͳ
					MavenUtil.setpermission(shellFilePath2);
					break;
				default:
					break;
			}
			mavenObject.setAppName($V("Alias"));
			mavenObject.setShellpath(shellFilePath2);
			mavenObject.setWorkPath(workpath);
			mavenObject.setArchetype("generate");
			mavenObject.setArchetypeCatalog("local");
			mavenObject.setGroupId("com.nswt");
			mavenObject.setVersion($V("AppVersion"));
			mavenObject.setArchetypeGroupId("com.nswt");
			mavenObject.setArchetypeArtifactId("nswt-web-archetype");
			mavenObject.setArchetypeVersion("1.0");
			mavenObject.setArchetypeRepository("http://124.200.181.126:9091/nexus/content/groups/public/archetype-catalog.xml");
			mavenObject.setPackagename($V("PackageName"));
			mavenObject.setInteractiveMode("false");
			updatePrivAndFile($V("Alias"));
			try {
				MavenUtil.callMacShell(mavenObject);
				UserLog.log(UserLog.SITE, UserLog.SITE_ADDSITE, "Maven����Ӧ�÷���ֵ������,������Ҫ��鿴�ű�ִ����MavenUtil���жϷ���ֵ�����������!", Request.getClientIP());

			} catch (IOException e){
				e.printStackTrace();
			} catch (XmlPullParserException e) {
				e.printStackTrace();
			}
			if (StringUtil.isNotEmpty(site.getHeaderTemplate())) {
				addDefaultPageBlock(site.getID() + "", path, site.getHeaderTemplate(), "��̬Ӧ��ͷ������", "include");
			}
			if (StringUtil.isNotEmpty(site.getTopTemplate())) {
				addDefaultPageBlock(site.getID() + "", path, site.getTopTemplate(), "��̬Ӧ�ö���", "top");
			}
			if (StringUtil.isNotEmpty(site.getBottomTemplate())) {
				addDefaultPageBlock(site.getID() + "", path, site.getBottomTemplate(), "��̬Ӧ�õײ�", "bottom");
			}

			// ����Ӧ�û���
			SiteUtil.update(id);
			Response.setLogInfo(1, "�½�Ӧ�óɹ�");
			UserLog.log(UserLog.SITE, UserLog.SITE_ADDSITE, "����Ӧ��:" + $V("Name") + ",�ɹ���", Request.getClientIP());
		}
	}


	public boolean addDefaultPageBlock(String SiteID, String AbsolutePath, String template, String PageBlockName,
			String PageBlockCode) {
		File file = new File(AbsolutePath + template);
		if (!file.exists()) {
			return false;
		}
		if (new QueryBuilder("select count(*) from ZCPageBlock where SiteID = " + SiteID + " and Name=? and Code =?",
				PageBlockName, PageBlockCode).executeInt() > 0) {
			return true;
		} else {
			ZCPageBlockSchema block = new ZCPageBlockSchema();
			block.setID(NoUtil.getMaxID("PageBlockID"));
			block.setSiteID(SiteID);
			block.setName(PageBlockName);
			block.setCode(PageBlockCode);
			block.setFileName("/include/" + PageBlockCode + ".html");
			block.setTemplate(template);
			block.setType(1);
			block.setAddTime(new Date());
			block.setAddUser(User.getUserName());
			return block.insert();
		}
	}

	/**
	 * Ӧ�õ���ʱҪʹ�ñ�����
	 */
	public boolean add(Transaction trans, long siteID) {
//		if (LicenseInfo.getName().equals("TrailUser")
//				&& new QueryBuilder("select count(*) from ZCSite").executeInt() >= 1) {
//			Response.setError("Ӧ�����������ƣ�����ϵ�����������License��");
//			return false;
//		}
		if (new QueryBuilder("select count(*) from zcsite where Alias = ?", $V("Alias")).executeInt() > 0) {
			Response.setLogInfo(0, "�½�Ӧ��ʧ��,�ظ���Ŀ¼��");
			Response.setError("�½�Ӧ��ʧ��,�Ѵ��ڵ�Ŀ¼��");
			return false;
		}
		ZCSiteSchema site = new ZCSiteSchema();
		site.setValue(Request);
		site.setID(siteID);
		site.setHitCount(0);
		site.setChannelCount(0);
		site.setSpecialCount(0);
		site.setMagzineCount(0);
		site.setArticleCount(0);
		site.setImageLibCount(1);
		site.setVideoLibCount(1);
		site.setAudioLibCount(1);
		site.setAttachmentLibCount(1);
		site.setOrderFlag(OrderUtil.getDefaultOrder());
		site.setBranchInnerCode(User.getBranchInnerCode());
		site.setAddTime(new Date());
		site.setAddUser(User.getUserName());
		site.setConfigXML(ConfigImageLib.imageLibConfigDefault);
		trans.add(site, Transaction.INSERT);

		// �½�Ӧ��Ĭ����Ŀ
		addDefaultResourceLib(site.getID(), trans);
		//����Ӧ�ýṹ��ԭʼ�������Maven�ű���ʽ����
		MavenUtil.callCmd("/Users/lijinyan/MyWork/NSWT/creditloan2/src/main/resources/callshell.sh");
		// ����Ӧ��Ĭ��Ȩ��
		addDefaultPriv(site.getID(), trans);
		// Ӧ�÷������ó�ʼ��
		initSiteConfig(site.getID(), trans);
		return true;
	}

	/**
	 * �����ڴ��е�Ȩ����Ϣ������Ӧ���µı�ҪĿ¼�����Ʊ�Ҫ���ļ�
	 */
	public static void updatePrivAndFile(String alias) {
		RolePriv.updateAllPriv("admin");

		// ����һЩ��Ҫ��ͼƬ
		String oldPath = Config.getContextRealPath() + "Images";
		String path = (Config.getContextRealPath() + Config.getValue("UploadDir") + "/" + alias + Config.getValue("Statical.WebRootDir")+"/upload/Image")
				.replaceAll("//", "/");
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		FileUtil.copy(oldPath + "/nocover.jpg", path + "/nocover.jpg");
		FileUtil.copy(oldPath + "/nophoto.jpg", path + "/nophoto.jpg");
		FileUtil.copy(oldPath + "/nopicture.jpg", path + "/nopicture.jpg");
		FileUtil.copy(oldPath + "/WaterMarkImage1.png", path + "/WaterMarkImage1.png");
		FileUtil.copy(oldPath + "/WaterMarkImage.png", path + "/WaterMarkImage.png");

		// ����ģ��Ŀ¼
		String templatePath = Config.getContextRealPath() + Config.getValue("Statical.TemplateDir") + "/" + alias
				+Config.getValue("Statical.WebRootDir")+"/template";
		dir = new File(templatePath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		//��������ģ��
		//String templateDefaultPath = Config.getContextRealPath()+  Config.getValue("Statical.TemplateDir") + "/" +"default"+"/"+"template";
		//FileUtil.copy(templateDefaultPath +"/index.html",templatePath+"/index.html");

		// ����ͼƬĿ¼
		String imagePath = Config.getContextRealPath() + Config.getValue("Statical.TemplateDir") + "/" + alias
				+Config.getValue("Statical.WebRootDir")+"/images";
		dir = new File(imagePath);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		// ͼƬ����������������
		FileUtil.copy(Config.getContextRealPath() + "/Tools/ImagePlayer.swf", imagePath + "/ImagePlayer.swf");
		FileUtil.copy(Config.getContextRealPath() + "/Tools/player.swf", imagePath + "/player.swf");
		FileUtil.copy(Config.getContextRealPath() + "/Tools/Swfobject.js", imagePath + "/Swfobject.js");

		FileUtil.copy(Config.getContextRealPath() + "/Tools/vote.js", imagePath + "/vote.js");
		FileUtil.copy(Config.getContextRealPath() + "/Tools/vote.css", imagePath + "/vote.css");

	}

	public static void addDefaultCatalogLib(long siteID, Transaction trans, String catalogType, String[] catalogNames) {
		for (int i = 0; i < catalogNames.length; i++) {
			ZCCatalogSchema catalog = new ZCCatalogSchema();
			catalog.setID(NoUtil.getMaxID("CatalogID"));
			catalog.setSiteID(siteID);
			catalog.setParentID(0);
			catalog.setInnerCode(CatalogUtil.createCatalogInnerCode(""));
			catalog.setTreeLevel(1);
			catalog.setName(catalogNames[i]);
			catalog.setURL("");
			catalog.setAlias(StringUtil.getChineseFirstAlpha(catalogNames[i]));
			catalog.setType(catalogType);
			catalog.setListTemplate("");
			catalog.setListNameRule("");
			catalog.setDetailTemplate("");
			catalog.setDetailNameRule("");
			catalog.setChildCount(0);
			catalog.setIsLeaf(1);
			catalog.setTotal(0);
			catalog.setOrderFlag(Catalog.getCatalogOrderFlag(0, catalog.getType()));
			catalog.setLogo("");
			catalog.setListPageSize(10);
			catalog.setPublishFlag("Y");
			catalog.setHitCount(0);
			catalog.setMeta_Keywords("");
			catalog.setMeta_Description("");
			catalog.setOrderColumn("");
			catalog.setAddUser(User.getUserName());
			catalog.setAddTime(new Date());
			trans.add(catalog, Transaction.INSERT);
		}
	}

	public static void addDefaultResourceLib(long siteID, Transaction trans) {
		// ��ý��⽨Ĭ��ר��
		ZCCatalogSchema imageCatalog = new ZCCatalogSchema();
		imageCatalog.setID(NoUtil.getMaxID("CatalogID"));
		imageCatalog.setSiteID(siteID);
		imageCatalog.setParentID(0);
		imageCatalog.setInnerCode(CatalogUtil.createCatalogInnerCode(""));
		imageCatalog.setTreeLevel(1);
		imageCatalog.setName("Ĭ��ͼƬ");
		imageCatalog.setURL("");
		imageCatalog.setAlias(StringUtil.getChineseFirstAlpha(imageCatalog.getName()));
		imageCatalog.setType(Catalog.IMAGELIB);
		imageCatalog.setListTemplate("");
		imageCatalog.setListNameRule("");
		imageCatalog.setDetailTemplate("");
		imageCatalog.setDetailNameRule("");
		imageCatalog.setChildCount(0);
		imageCatalog.setIsLeaf(1);
		imageCatalog.setTotal(0);
		imageCatalog.setOrderFlag(Catalog.getCatalogOrderFlag(0, imageCatalog.getType()));
		imageCatalog.setLogo("");
		imageCatalog.setListPageSize(10);
		imageCatalog.setListPage(-1);
		imageCatalog.setPublishFlag("Y");
		imageCatalog.setHitCount(0);
		imageCatalog.setMeta_Keywords("");
		imageCatalog.setMeta_Description("");
		imageCatalog.setOrderColumn("");
		imageCatalog.setAddUser(User.getUserName());
		imageCatalog.setAddTime(new Date());
		trans.add(imageCatalog, Transaction.INSERT);

		ZCCatalogSchema videoCatalog = (ZCCatalogSchema) imageCatalog.clone();
		videoCatalog.setID(NoUtil.getMaxID("CatalogID"));
		videoCatalog.setInnerCode(CatalogUtil.createCatalogInnerCode(""));
		videoCatalog.setType(Catalog.VIDEOLIB);
		videoCatalog.setName("Ĭ����Ƶ");
		videoCatalog.setAlias(StringUtil.getChineseFirstAlpha(videoCatalog.getName()));
		trans.add(videoCatalog, Transaction.INSERT);

		ZCCatalogSchema audioCatalog = (ZCCatalogSchema) imageCatalog.clone();
		audioCatalog.setID(NoUtil.getMaxID("CatalogID"));
		audioCatalog.setInnerCode(CatalogUtil.createCatalogInnerCode(""));
		audioCatalog.setType(Catalog.AUDIOLIB);
		audioCatalog.setName("Ĭ����Ƶ");
		audioCatalog.setAlias(StringUtil.getChineseFirstAlpha(audioCatalog.getName()));
		trans.add(audioCatalog, Transaction.INSERT);

		ZCCatalogSchema attachCatalog = (ZCCatalogSchema) imageCatalog.clone();
		attachCatalog.setID(NoUtil.getMaxID("CatalogID"));
		attachCatalog.setInnerCode(CatalogUtil.createCatalogInnerCode(""));
		attachCatalog.setType(Catalog.ATTACHMENTLIB);
		attachCatalog.setName("Ĭ�ϸ���");
		attachCatalog.setAlias(StringUtil.getChineseFirstAlpha(attachCatalog.getName()));
		trans.add(attachCatalog, Transaction.INSERT);
	}

	public static void addDefaultPriv(long siteID, Transaction trans) {
		// ���adminӦ��Ȩ��
		Set set = Priv.SITE_MAP.keySet();
		for (Iterator iter = set.iterator(); iter.hasNext();) {
			String code = (String) iter.next();
			ZDPrivilegeSchema priv = new ZDPrivilegeSchema();
			priv.setOwnerType(RolePriv.OWNERTYPE_ROLE);
			priv.setOwner("admin");
			priv.setID(siteID + "");
			priv.setPrivType(Priv.SITE);
			priv.setCode(code);
			priv.setValue("1");
			trans.add(priv, Transaction.INSERT);
		}
		// ���admin�ĵ�Ȩ�ޡ�
		set = Priv.ARTICLE_MAP.keySet();
		for (Iterator iter = set.iterator(); iter.hasNext();) {
			String code = (String) iter.next();
			ZDPrivilegeSchema priv = new ZDPrivilegeSchema();
			priv.setOwnerType(RolePriv.OWNERTYPE_ROLE);
			priv.setOwner("admin");
			priv.setID(siteID + "");
			priv.setPrivType(Priv.SITE);
			priv.setCode(code);
			priv.setValue("1");
			trans.add(priv, Transaction.INSERT);
		}
	}

	public static void initSiteConfig(long siteID, Transaction trans) {
		ZCCatalogConfigSchema config = new ZCCatalogConfigSchema();
		config.setID(NoUtil.getMaxID("CatalogConfigID"));
		config.setAddTime(new Date());
		config.setAddUser(User.getUserName());
		config.setStartTime(new Date());
		config.setArchiveTime("12");
		config.setAttachDownFlag("Y");
		config.setSiteID(siteID);
		config.setCatalogID(0);
		config.setIsUsing("N");
		config.setPlanType("Period");
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

	public void save() {
		ZCSiteSchema site = new ZCSiteSchema();
		site.setValue(Request);
		site.fill();
		String oldAlias = site.getAlias();
		if (new QueryBuilder("select count(*) from zcsite where ID != ? and Alias = ?", site.getID(), $V("Alias"))
				.executeInt() > 0) {
			Response.setLogInfo(0, "����ʧ��,�ظ���Ŀ¼��");
			return;
		}
		site.setValue(Request);
		site.setModifyUser(User.getUserName());
		site.setModifyTime(new Date());

		if (site.update()) {
			String path = SiteUtil.getAbsolutePath(site.getID());
			if (StringUtil.isNotEmpty(site.getHeaderTemplate())) {
				addDefaultPageBlock(site.getID() + "", path, site.getHeaderTemplate(), "��̬Ӧ��ͷ������", "include");
			}
			if (StringUtil.isNotEmpty(site.getTopTemplate())) {
				addDefaultPageBlock(site.getID() + "", path, site.getTopTemplate(), "��̬Ӧ�ö���", "top");
			}
			if (StringUtil.isNotEmpty(site.getBottomTemplate())) {
				addDefaultPageBlock(site.getID() + "", path, site.getBottomTemplate(), "��̬Ӧ�õײ�", "bottom");
			}
			SiteUtil.update(site.getID());
			Response.setLogInfo(1, "����ɹ�");
			UserLog.log(UserLog.SITE, UserLog.SITE_UPDATESITE, "�޸�Ӧ��:" + site.getName() + "�ɹ���", Request.getClientIP());
			final String indexFlag = site.getAutoIndexFlag();
			final long siteID = site.getID();
			new Thread() {
				public void run() {
					FullText.dealAutoIndex(siteID, "Y".equals(indexFlag));
				}
			}.start();

			// �����޸��ļ�������
			String oldPath = Config.getContextRealPath() + Config.getValue("Statical.TargetDir") + "/" + oldAlias + "/";
			String newPath = Config.getContextRealPath() + Config.getValue("Statical.TargetDir") + "/"
					+ site.getAlias() + "/";
			if (!oldAlias.equals(site.getAlias())) {// �������޸ģ���Ҫ�������ļ���
				// �޸����������еı���
				updateSiteAlias(siteID, oldAlias, site.getAlias());
				File f = new File(oldPath);
				if (!f.renameTo(new File(newPath))) {
					Response.setLogInfo(1, "���ݱ���ɹ���������Ӧ��Ӣ����������Ӧ���ļ���ʧ�ܣ����ֹ��޸�Ӧ���ļ���!");
				}
			}
		} else {
			UserLog.log(UserLog.SITE, UserLog.SITE_UPDATESITE, "�޸�Ӧ��:" + site.getName() + "ʧ�ܣ�", Request.getClientIP());
			Response.setLogInfo(0, "����ʧ��");
		}
	}

	public static void updateSiteAlias(long siteID, String oldAlias, String newAlias) {
		int total = new QueryBuilder("select count(1) from ZCArticle where SiteID=?", siteID).executeInt();
		int size = 200;
		QueryBuilder qb = new QueryBuilder("select id,content from ZCArticle where SiteID=?", siteID);
		for (int i = 0; i <= total * 1.0 / size; i++) {
			DataTable dt = qb.executePagedDataTable(size, i);
			QueryBuilder qb2 = new QueryBuilder("update ZCArticle set Content=? where ID=?");
			qb2.setBatchMode(true);
			for (int j = 0; j < dt.getRowCount(); j++) {
				String content = dt.getString(j, "Content");
				content = StringUtil.replaceEx(content, "/" + oldAlias + "/", "/" + newAlias + "/");
				qb2.add(content);
				qb2.add(dt.getString(j, "ID"));
				qb2.addBatch();
			}
			qb2.executeNoQuery();
		}
	}

	public void del() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("����IDʱ��������!");
			return;
		}

		ZCSiteSchema site = new ZCSiteSchema();
		ZCSiteSet set = site.query(new QueryBuilder("where id in (" + ids + ")"));
		StringBuffer siteLog = new StringBuffer("ɾ��Ӧ��:");
		for (int i = 0; i < set.size(); i++) {
			site = set.get(i);
			siteLog.append(site.getName() + ",");
			// ����б��ݱ�ǣ����ȱ������ݺ��ļ�
			if ("Y".equals($V("BackupFlag"))) {
				SiteExporter se = new SiteExporter(site.getID());
				se.exportSite(Config.getContextRealPath() + "WEB-INF/data/backup/" + site.getAlias() + "_"
						+ System.currentTimeMillis() + ".dat");
			}
		}

		BlockingTransaction trans = new BlockingTransaction();
		trans.add(set, Transaction.DELETE_AND_BACKUP);// ɾ��Ӧ�ñ���
		delSiteRela(ids, trans);// ɾ�������ͱ�Ӧ����صı�

		if (trans.commit()) {
			for (int i = 0; i < set.size(); i++) {
				site = set.get(i);
				SiteUtil.update(site.getID());
				if (ids.indexOf(Application.getCurrentSiteID() + "") >= 0) {// �����ǰӦ�ñ�ɾ����
					DataTable dt = new QueryBuilder(
							"select name,id from zcsite order by BranchInnerCode ,orderflag ,id").executeDataTable();
					dt = dt.filter(new Filter() {
						public boolean filter(Object obj) {
							DataRow dr = (DataRow) obj;
							return Priv.getPriv(User.getUserName(), Priv.SITE, dr.getString("ID"), Priv.SITE_BROWSE);
						}
					});
					if (dt.getRowCount() > 0) {
						Application.setCurrentSiteID(dt.getString(0, 1));
					} else {
						Application.setCurrentSiteID("");
					}
				}
				// ɾ���ļ�
				String sitePath = Config.getContextRealPath() + Config.getValue("Statical.TemplateDir") + "/"
						+ site.getAlias();
				FileUtil.delete(sitePath);
			}
			UserLog.log(UserLog.SITE, UserLog.SITE_DELSITE, siteLog + "�ɹ�", Request.getClientIP());
			Response.setLogInfo(1, "ɾ���ɹ�");
		} else {
			UserLog.log(UserLog.SITE, UserLog.SITE_DELSITE, siteLog + "ʧ��", Request.getClientIP());
			Response.setLogInfo(0, "ɾ��ʧ��");
		}
	}

	/**
	 * ɾ������SiteID�ֶεı��е�����,�Լ���������Щ��ı��е�����
	 * 
	 * @param siteIDs
	 * @param trans
	 */
	public static void delSiteRela(String siteIDs, BlockingTransaction trans) {
		if (!StringUtil.checkID(siteIDs)) {
			return;
		}
		String[] tables = SiteTableRela.getSiteIDTables();
		for (int i = 0; i < tables.length; i++) {
			deleteTableData(tables[i], siteIDs, trans);
		}
		TableRela[] trs = SiteTableRela.getRelas();
		for (int i = 0; i < trs.length; i++) {
			TableRela tr = trs[i];
			if (tr.isExportData) {
				deleteRelaTableData(tr.TableCode, tr.KeyField, tr.RelaTable, tr.RelaField, siteIDs, trans);
			}
		}
	}

	/**
	 * ɾ������SiteID�ֶεı��е�����
	 */
	public static void deleteTableData(String tableName, String siteIDs, BlockingTransaction trans) {
		String backupNO = String.valueOf(System.currentTimeMillis()).substring(1, 11);
		String backupOperator = User.getUserName();
		String backupTime = DateUtil.toString(new Date(), "yyyy-MM-dd HH:mm:ss");
		String backup = "insert into b" + tableName + " select " + tableName + ".*,'" + backupNO + "','"
				+ backupOperator + "','" + backupTime + "',null from " + tableName + " where SiteID in(" + siteIDs
				+ ")";
		String delete = "delete from " + tableName + " where SiteID in (" + siteIDs + ")";
		try {
			trans.addWithException(new QueryBuilder(backup));
			trans.addWithException(new QueryBuilder(delete));
		} catch (Exception e) {
			LogUtil.warn(e.getMessage());// �п��ܱ��ֹ�ɾ����
		}
	}

	/**
	 * ɾ�������� ����SiteID�ֶεı��е�����ID�������ֶε� ���е�����
	 */
	public static void deleteRelaTableData(String tableName, String foreinKey, String relaTableName, String relaField,
			String siteIDs, BlockingTransaction trans) {
		String wherePart = " where exists(select '' from " + relaTableName + " where " + relaField + "=" + tableName
				+ "." + foreinKey + " and SiteID in (" + siteIDs + "))";
		if (Config.isSybase() && foreinKey.equalsIgnoreCase("RelaID")
				&& (tableName.equalsIgnoreCase("ZDColumnRela") || tableName.equalsIgnoreCase("ZDColumnValue"))) {
			wherePart = " where exists(select '' from " + relaTableName + " where " + relaField + "=convert(bigint,"
					+ tableName + "." + foreinKey + ") and SiteID in (" + siteIDs + "))";
		}
		String backupNO = String.valueOf(System.currentTimeMillis()).substring(1, 11);
		String backupOperator = User.getUserName();
		String backupTime = DateUtil.toString(new Date(), "yyyy-MM-dd HH:mm:ss");
		String backup = "insert into b" + tableName + " select " + tableName + ".*,'" + backupNO + "','"
				+ backupOperator + "','" + backupTime + "',null from " + tableName + wherePart;
		String delete = "delete from " + tableName + wherePart;
		try {
			trans.addWithException(new QueryBuilder(backup));
			trans.addWithException(new QueryBuilder(delete));
		} catch (Exception e) {
			LogUtil.warn(e.getMessage());// �п��ܱ��ֹ�ɾ����
		}
	}

	/**
	 * �ϴ������ļ�
	 */
	public static void uploadSite(HttpServletRequest request, HttpServletResponse response) {
		try {
			DiskFileItemFactory fileFactory = new DiskFileItemFactory();
			ServletFileUpload fu = new ServletFileUpload(fileFactory);
			List fileItems = fu.parseRequest(request);
			fu.setHeaderEncoding("UTF-8");
			Iterator iter = fileItems.iterator();
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				if (!item.isFormField()) {
					String OldFileName = item.getName();
					LogUtil.info("Upload Site FileName:" + OldFileName);
					long size = item.getSize();
					if ((OldFileName == null || OldFileName.equals("")) && size == 0) {
						continue;
					}
					OldFileName = OldFileName.substring(OldFileName.lastIndexOf("\\") + 1);
					String ext = OldFileName.substring(OldFileName.lastIndexOf("."));
					if (!ext.toLowerCase().equals(".dat")) {
						response.sendRedirect("SiteImportStep1.jsp?Error=1");
						return;
					}
					String FileName = "SiteUpload_" + DateUtil.getCurrentDate("yyyyMMddHHmmss") + ".dat";
					String Path = Config.getContextRealPath() + "WEB-INF/data/backup/";
					File dir = new File(Path);
					if(!dir.exists()){
						dir.mkdirs();
					}
					item.write(new File(Path + FileName));
					response.sendRedirect("SiteImportStep2.jsp?FileName=" + FileName);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ����Ӧ�õڶ���ѡ���Ǹ���ĳӦ�û����½�Ӧ��ʱչ�ֵ�DataGrid
	 */
	public void importStep2DataBind(DataGridAction dga) {
		String sql = "select * from ZCSite order by orderflag ";
		dga.setTotal(new QueryBuilder("select * from ZCSite"));
		DataTable dt = new QueryBuilder(sql).executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dt.insertColumn("Type");// ���뷽ʽ
		for (int i = 0; i < dt.getRowCount(); i++) {
			dt.set(i, "Type", "<font class='red'>�滻Ӧ��</font>");
		}
		if (dga.getPageIndex() == 0) {
			dt.insertRow((Object[]) null, 0);
			String Path = Config.getContextRealPath() + "WEB-INF/data/backup/";
			Mapx map = new Mapx();
			try {
				map = SiteImporter.getSiteInfo(Path + $V("FileName"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			dt.set(0, "ID", "0");
			dt.set(0, "Type", "<font class='green'>�½�Ӧ��</font>");
			dt.set(0, "Name", "<input class='inputText' style='width:150px' id='Name' value='" + map.getString("Name")
					+ "'>");
			dt.set(0, "Alias", "<input class='inputText' style='width:100px' id='Alias' value='"
					+ map.getString("Alias") + "'>");
			dt.set(0, "URL", "<input class='inputText' style='width:250px' id='URL' value='" + map.getString("URL")
					+ "'>");
		}
		dga.bindData(dt);
	}

	/**
	 * ����Ӧ��ʱ����½�Ӧ���Ƿ�������Ӧ�ñ���/Ӧ�������ظ���������ظ�����ʼ����
	 */
	public void checkImportSite() {
		String ID = $V("ID");
		String name = $V("Name").trim();
		String alias = $V("Alias").trim();
		QueryBuilder qb = new QueryBuilder("select count(1) from ZCSite where Alias=? and ID<>?", alias, Long.parseLong(ID));
		if (qb.executeInt() != 0) {
			Response.setError("���� <font class='red'>" + alias + "</font> �Ѿ���ռ�ã����޸�!");
			return;
		}
		qb = new QueryBuilder("select count(1) from ZCSite where Name=? and ID<>?", name, Long.parseLong(ID));
		if (qb.executeInt() != 0) {
			Response.setError("Ӧ������ <font class='red'>" + name + "</font> �Ѿ���ռ�ã����޸ĳ�!");
			return;
		}
		LongTimeTask ltt = LongTimeTask.getInstanceByType("SiteImport");
		if (ltt != null) {
			Response.setError("Ŀǰ��Ӧ�õ����������������У���ȴ���");
			return;
		}
		final String Path = Config.getContextRealPath() + "WEB-INF/data/backup/" + $V("FileName");
		final Mapx map = Request;
		ltt = new LongTimeTask() {
			public void execute() {
				SiteImporter si = new SiteImporter(Path, this);
				si.setSiteInfo(map);
				si.importSite();
				setPercent(100);
			}
		};
		ltt.setType("SiteImport");
		ltt.setUser(User.getCurrent());
		ltt.start();
		$S("TaskID", "" + ltt.getTaskID());

	}

	public void sortColumn() {
		String target = $V("Target");
		String orders = $V("Orders");
		String type = $V("Type");
		if (!StringUtil.checkID(target) && !StringUtil.checkID(orders)) {
			return;
		}
		if (OrderUtil.updateOrder("ZCSite", type, target, orders, "1 = 1")) {
			Response.setMessage("����ɹ�");
		} else {
			Response.setError("����ʧ��");
		}
	}
}
