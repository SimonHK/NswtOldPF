package com.nswt.cms.document;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.nswt.cms.datachannel.PublishMonitor;
import com.nswt.cms.dataservice.ColumnUtil;
import com.nswt.cms.dataservice.Vote;
import com.nswt.cms.pub.CMSCache;
import com.nswt.cms.pub.CatalogUtil;
import com.nswt.cms.pub.PubFun;
import com.nswt.cms.pub.SiteUtil;
import com.nswt.cms.site.BadWord;
import com.nswt.cms.site.ImagePlayerRela;
import com.nswt.cms.site.Tag;
import com.nswt.framework.Config;
import com.nswt.framework.Constant;
import com.nswt.framework.Page;
import com.nswt.framework.User;
import com.nswt.framework.controls.DataGridAction;
import com.nswt.framework.controls.DataListAction;
import com.nswt.framework.controls.TButtonTag;
import com.nswt.framework.data.DataCollection;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.DataTableUtil;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.extend.ExtendManager;
import com.nswt.framework.utility.DateUtil;
import com.nswt.framework.utility.HtmlUtil;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.NumberUtil;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.Application;
import com.nswt.platform.Priv;
import com.nswt.platform.UserList;
import com.nswt.platform.UserLog;
import com.nswt.platform.pub.NoUtil;
import com.nswt.platform.pub.OrderUtil;
import com.nswt.schema.ZCArticleLogSchema;
import com.nswt.schema.ZCArticleSchema;
import com.nswt.schema.ZCArticleSet;
import com.nswt.schema.ZCAttachmentRelaSchema;
import com.nswt.schema.ZCCatalogConfigSchema;
import com.nswt.schema.ZCCatalogSchema;
import com.nswt.schema.ZCImageRelaSchema;
import com.nswt.schema.ZCImageSchema;
import com.nswt.schema.ZCImageSet;
import com.nswt.schema.ZCInnerDeploySchema;
import com.nswt.schema.ZCInnerDeploySet;
import com.nswt.schema.ZCTagSchema;
import com.nswt.schema.ZCVideoRelaSchema;
import com.nswt.schema.ZCVoteItemSchema;
import com.nswt.schema.ZCVoteSchema;
import com.nswt.schema.ZCVoteSet;
import com.nswt.search.SearchResult;
import com.nswt.search.index.IndexUtil;
import com.nswt.workflow.Context;
import com.nswt.workflow.WorkflowAction;
import com.nswt.workflow.WorkflowUtil;

/**
 * ���¹���
 * 
 * @Author ����
 * @Date 2007-8-10
 * @Mail lanjun@nswt.com
 */
public class Article extends Page {

	public static final String TYPE_COMMON = "1"; // ��ͨ����

	public static final String TYPE_IMAGE = "2"; // ͼƬ

	public static final String TYPE_VIDEO = "3"; // ��Ƶ

	public static final String TYPE_URL = "4"; // ��ת����

	public static final String TYPE_ATTACH = "5"; // ����

	public static final String RELA_ATTACH = "ArticleAttach"; // ��������

	public static final String RELA_VIDEO = "ArticleVideo"; // ��Ƶ����

	public static final String RELA_IMAGE = "ArticleImage"; // ͼƬ����

	// ����״̬
	public static final int STATUS_DRAFT = 0; // ����

	public static final int STATUS_WORKFLOW = 10; // ��ת��

	public static final int STATUS_TOPUBLISH = 20; // ������

	public static final int STATUS_PUBLISHED = 30; // �ѷ���

	public static final int STATUS_OFFLINE = 40; // ������

	public static final int STATUS_ARCHIVE = 50; // �ѹ鵵

	public static final int STATUS_EDITING = 60; // ���±༭

	public final static Mapx STATUS_MAP = new Mapx();

	static {
		STATUS_MAP.put(STATUS_DRAFT + "", "����");
		STATUS_MAP.put(STATUS_WORKFLOW + "", "��ת��");
		STATUS_MAP.put(STATUS_TOPUBLISH + "", "������");
		STATUS_MAP.put(STATUS_PUBLISHED + "", "�ѷ���");
		STATUS_MAP.put(STATUS_OFFLINE + "", "������");
		STATUS_MAP.put(STATUS_ARCHIVE + "", "�ѹ鵵");
		STATUS_MAP.put(STATUS_EDITING + "", "���±༭");
	}

	private String siteAlias = SiteUtil.getAlias(Application.getCurrentSiteID());

	// �༭��ҳ���ʼ��
	public static Mapx init(Mapx params) {
		Mapx map = new Mapx();
		String catalogID = (String) params.get("CatalogID");
		String issueID = (String) params.get("IssueID");
		String articleID = (String) params.get("ArticleID");
		String title = (String) params.get("Title");
		try {
			if (StringUtil.isNotEmpty(title)) {
				title = URLDecoder.decode(title, "utf-8");
			}
		} catch (UnsupportedEncodingException e) {
		}
		if (articleID != null && !"".equals(articleID) && !"null".equals(articleID)) {
			ZCArticleSchema article = new ZCArticleSchema();
			article.setID(Integer.parseInt(articleID));
			ZCArticleSet set = article.query();
			if (set.size() < 1) {
				return null;
			}
			article = set.get(0);
			catalogID = article.getCatalogID() + "";
			map.putAll(article.toMapx());

			String content = article.getContent();
			if (content == null) {
				content = "";
			}
			String[] pages = content.split(Constant.PAGE_BREAK, -1);

			map.put("Pages", new Integer(pages.length));
			map.put("Content", pages[0]);
			map.put("Contents", content);

			String pageTitle = article.getPageTitle();
			if (pageTitle == null) {
				pageTitle = "";
			}
			String[] pageTitles = pageTitle.split("\\|", -1);
			map.put("PageTitle", pageTitles[0]);
			map.put("PageTitles", pageTitle);

			// �Զ����ֶ�
			map.put("CustomColumn", ColumnUtil.getHtml(ColumnUtil.RELA_TYPE_CATALOG_COLUMN, catalogID,
					ColumnUtil.RELA_TYPE_DOCID, article.getID() + ""));

			// �ĵ�����
			if (article.getAttribute() != null) {
				map.put("Attribute",
						HtmlUtil.codeToCheckboxes("Attribute", "ArticleAttribute", article.getAttribute().split(",")));
			} else {
				map.put("Attribute", HtmlUtil.codeToCheckboxes("Attribute", "ArticleAttribute"));
			}

			Date publishDate = article.getPublishDate();
			Date downlineDate = article.getDownlineDate();
			Date archiveDate = article.getArchiveDate();
			if (publishDate != null) {
				map.put("PublishDate", DateUtil.toString(publishDate, DateUtil.Format_Date));
				map.put("PublishTime", DateUtil.toString(publishDate, DateUtil.Format_Time));
			}

			if (downlineDate != null) {
				map.put("DownlineDate", DateUtil.toString(downlineDate, DateUtil.Format_Date));
				map.put("DownlineTime", DateUtil.toString(downlineDate, DateUtil.Format_Time));
			}

			if (archiveDate != null) {
				map.put("ArchiveDate", DateUtil.toString(archiveDate, DateUtil.Format_Date));
				map.put("ArchiveTime", DateUtil.toString(archiveDate, DateUtil.Format_Time));
			}

			// ����޸�ʱ��
			Date lastModify = article.getModifyTime() == null ? article.getAddTime() : article.getModifyTime();
			map.put("LastModify", lastModify);

			map.put("Method", "UPDATE");

			// ������
			boolean publishFlag = Article.STATUS_PUBLISHED == article.getStatus()
					|| Article.STATUS_TOPUBLISH == article.getStatus();
			String html = getInitButtons(Long.parseLong(catalogID), article.getWorkFlowID(), publishFlag);
			map.put("buttonDiv", html);

			// ͼƬ������
			DataTable imgDt = new QueryBuilder(
					"select ID from ZCImageRela where RelaType = 'ArticleImage' and RelaID = ?", article.getID())
					.executeDataTable();
			String imageIDs = "";
			for (int i = 0; i < imgDt.getRowCount(); i++) {
				if (i != 0) {
					imageIDs += ",";
				}
				imageIDs += imgDt.getString(i, 0);
			}
			String relaImagePlayerID = article.getProp3();
			String relaImageIDs = "";
			if (StringUtil.isEmpty(relaImagePlayerID)) {
				relaImagePlayerID = "";
			} else if (StringUtil.isNotEmpty(imageIDs)) {
				imgDt = new QueryBuilder(
						"select ID from ZCImageRela where RelaType='ImagePlayerImage' and RelaID=? and ID in ("
								+ imageIDs + ")", relaImagePlayerID).executeDataTable();
				for (int i = 0; i < imgDt.getRowCount(); i++) {
					if (i != 0) {
						relaImageIDs += ",";
					}
					relaImageIDs += imgDt.getString(i, 0);
				}
			}
			map.put("ImageIDs", imageIDs);
			map.put("RelaImageIDs", relaImageIDs);
			map.put("RelaImagePlayerID", relaImagePlayerID);

			// ��ʼ����������ͼ
			String imgLogo = "../Images/addpicture.jpg";
			String sitePath = Config.getContextPath() + Config.getValue("UploadDir") + "/"
					+ SiteUtil.getAlias(article.getSiteID());
			sitePath = sitePath.replaceAll("/+", "/");
			String logo = article.getLogo();
			if (StringUtil.isNotEmpty(logo)) {
				logo = sitePath + "/" + logo;
				imgLogo = logo;
			}
			map.put("Logo", logo);
			map.put("ImgLogo", imgLogo);

			// ��վ�ڸ���
			if (StringUtil.isNotEmpty(article.getReferTarget())) {
				map.put("ReferTargetCount", StringUtil.count(article.getReferTarget(), ",") + 1);
			} else {
				map.put("ReferTargetCount", 0);
			}
			// �������ͣ�ͨ����Ӧ��Ŀ���ļ����ж�
			int referType = new QueryBuilder("select referType from zcarticle where ReferSourceID=?", article.getID())
					.executeInt();
			if (referType != 0) {
				map.put("ReferType", referType);
			} else {
				map.put("ReferType", "");
			}

			// վ��Ⱥ����
			if (StringUtil.isNotEmpty(article.getClusterTarget())) {
				map.put("ClusterTargetCount", StringUtil.count(article.getClusterTarget(), ",") + 1);
				map.put("ClusterTarget", article.getClusterTarget());
			} else {
				map.put("ClusterTargetCount", 0);
			}

			// �������Ϊ���ƻ����ù����ģ���ʾ��Դ��Ŀ
			long referCatalogID = new QueryBuilder("select catalogID from zcarticle where id=?",
					article.getReferSourceID()).executeLong();
			if (referCatalogID != 0L) {
				map.put("ReferDisplay", "style='display:'");
				map.put("ReferSourceName", CatalogUtil.getFullName(referCatalogID));
			} else {
				map.put("ReferDisplay", "style='display:none'");
			}
		} else {
			map.put("CatalogID", catalogID);
			articleID = NoUtil.getMaxID("DocID") + "";
			map.put("ID", articleID);
			map.put("Method", "ADD");
			map.put("CatalogID", catalogID);
			map.put("CommentFlag", "1");

			map.put("DownlineDate", "2099-12-31");
			map.put("DownlineTime", "23:59:59");

			String archiveTime = "";
			if (CatalogUtil.getArchiveTime(catalogID) != null) {
				archiveTime = DateUtil.toDateTimeString(CatalogUtil.getArchiveTime(catalogID));
			}

			if (StringUtil.isNotEmpty(archiveTime)) {
				map.put("ArchiveDate", archiveTime.substring(0, 10));
				map.put("ArchiveTime", archiveTime.substring(11));
			}

			map.put("Pages", new Integer(1));
			map.put("ContentPages", "''");
			map.put("Title", title);

			map.put("ReferTarget", "");
			map.put("ReferType", "1");

			// ����Ĭ����Ϊ��ʵ����
			map.put("Author", User.getRealName());

			// ������
			String html = getInitButtons(Long.parseLong(catalogID), 0, false);
			map.put("buttonDiv", html);

			// �Զ����ֶ�
			map.put("CustomColumn", ColumnUtil.getHtml(ColumnUtil.RELA_TYPE_CATALOG_COLUMN, catalogID));

			// �ĵ�����
			map.put("Attribute", HtmlUtil.codeToCheckboxes("Attribute", "ArticleAttribute"));

			// ��ʼ����������ͼ
			String imageLogo = "../Images/addpicture.jpg";
			map.put("Logo", "");
			map.put("ImgLogo", imageLogo);

			map.put("ReferTargetCount", 0);
			map.put("ClusterTargetCount", 0);

			map.put("ReferDisplay", "style='display:none'");
		}
		ZCInnerDeploySchema deploy = new ZCInnerDeploySchema();
		deploy.setCatalogInnerCode(CatalogUtil.getInnerCode(catalogID));
		ZCInnerDeploySet set = deploy.query();
		if (set.size() > 0) {
			DataTable dt = DataTableUtil.txtToDataTable(set.get(0).getTargetCatalog(), (String[]) null, "\t", "\n");
			if (dt.getDataColumn("MD5") == null) {
				dt.insertColumn("MD5");
			}
			// ������Զ��ַ�����ȫѡ��
			if ("1".equals(set.get(0).getDeployType())) {
				map.put("ClusterTargetCount", dt.getRowCount() + "/" + dt.getRowCount());
				for (int i = 0; i < dt.getRowCount(); i++) {
					dt.set(i,
							"MD5",
							StringUtil.md5Hex(dt.getString(i, "ServerAddr") + "," + dt.getString(i, "SiteID") + ","
									+ dt.getString(i, "CatalogID")));
				}
				map.put("ClusterTarget", StringUtil.join(dt.getColumnValues("MD5")));
			} else {
				map.put("ClusterTargetCount", "0/" + dt.getRowCount());
			}
		}

		long siteID = Long.parseLong(CatalogUtil.getSiteID(catalogID));
		map.put("SiteID", CatalogUtil.getSiteID(catalogID));
		map.put("CatalogName", CatalogUtil.getName(catalogID));
		map.put("IssueID", issueID);
		map.put("InnerCode", CatalogUtil.getInnerCode(catalogID));
		map.put("URL", CatalogUtil.getPath(catalogID) + articleID + ".shtml");

		// css��ʽ
		String cssFile = new QueryBuilder("select editorcss from zcsite where id=?", siteID).executeString();
		if (StringUtil.isNotEmpty(cssFile)) {
			String staticDir = Config.getContextPath() + Config.getValue("Statical.TargetDir").replace('\\', '/');
			staticDir = staticDir + "/" + Application.getCurrentSiteAlias() + "/" + cssFile;
			staticDir = staticDir.replaceAll("/+", "/");
			map.put("CssPath", staticDir);
		} else {
			map.put("CssPath", Config.getContextPath() + "Editor/editor/css/fck_editorarea.css");
		}

		String defaultImageValue = Config.getContextPath() + Config.getValue("UploadDir") + "/"
				+ Application.getCurrentSiteAlias() + "/upload/Image/nopicture.jpg";
		defaultImageValue = defaultImageValue.replaceAll("/+", "/");
		map.put("defaultImageValue", defaultImageValue);
		map.put("Title", StringUtil.htmlEncode(map.getString("Title")));
		return map;
	}

	public void applyStep() {
		long instanceID = Long.parseLong($V("InstanceID"));
		int nodeID = Integer.parseInt($V("NodeID"));
		String catalogID = $V("CatalogID");
		try {
			WorkflowUtil.applyStep(instanceID, nodeID);
			WorkflowAction[] actions;
			try {
				actions = WorkflowUtil.findAvaiableActions(instanceID);
			} catch (Exception e) {
				e.printStackTrace();
				Response.setMessage("����ʧ�ܣ�" + e.getMessage());
				return;
			}
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < actions.length; i++) {
				WorkflowAction action = actions[i];
				if (action.getID() == WorkflowAction.TEMPORARYSAVE_ACTIONID) {
					if (Priv.getPriv(Priv.ARTICLE, CatalogUtil.getInnerCode(catalogID), Priv.ARTICLE_MODIFY)) {
						sb.append(TButtonTag.getHtml("ClickMethod='" + action.getName() + "';save('" + instanceID
								+ "','" + action.getID() + "')", null, "<img src='../Icons/icon003a16.gif'/>",
								action.getName()));
					}
				} else {
					boolean allowSelectUser = "1".equals(action.getData().get("AllowSelectUser"));
					sb.append(TButtonTag.getHtml("ClickMethod='" + action.getName() + "';save('" + instanceID + "','"
							+ action.getID() + "'," + allowSelectUser + ")", null,
							"<img src='../Icons/icon003a16.gif'/>", action.getName()));
				}
			}
			$S("buttonDiv", sb.toString());
			Response.setMessage("����ɹ�");
		} catch (Exception e) {
			e.printStackTrace();
			Response.setMessage("����ʧ��:" + e.getMessage());
		}
	}

	private static String getInitButtons(long catalogID, long instanceID, boolean publishFlag) {
		String workflowID = CatalogUtil.getWorkflow(catalogID);
		if (StringUtil.isNotEmpty(workflowID)) {
			if (WorkflowUtil.findWorkflow(Long.parseLong(workflowID)) == null) {
				workflowID = null;// û���ҵ�����Ϊû�й�����
			}
		}
		if (publishFlag) {// �Ѿ����ڷ����������״̬�ˣ�����ֱ����ʾ������ť
			String buttonDiv = null;
			if (StringUtil.isNotEmpty(workflowID)) {
				if (UserList.ADMINISTRATOR.equals(User.getUserName())) {
					buttonDiv = TButtonTag.getHtml("publish(true)", null, "<img src='../Icons/icon003a13.gif'/>", "����");
				} else {
					buttonDiv = "";
				}
				if (Priv.getPriv(User.getUserName(), Priv.ARTICLE, CatalogUtil.getInnerCode(catalogID),
						Priv.ARTICLE_MODIFY)) {
					buttonDiv += TButtonTag.getHtml("ClickMethod='�����޸�';save('" + instanceID + "','"
							+ WorkflowAction.RESTART_ACTIONID + "')", null, "<img src='../Icons/icon003a16.gif'/>",
							"�����޸�");
				}
			} else {
				if (Priv.getPriv(User.getUserName(), Priv.ARTICLE, CatalogUtil.getInnerCode(catalogID),
						Priv.ARTICLE_MODIFY)) {
					buttonDiv = TButtonTag.getHtml("save()", "article_modify", "<img src='../Icons/icon003a16.gif'/>",
							"����")
							+ TButtonTag.getHtml("topublish()", "article_modify",
									"<img src='../Icons/icon003a6.gif'/>", "������")
							+ TButtonTag.getHtml("publish()", "article_modify", "<img src='../Icons/icon003a13.gif'/>",
									"����");
				} else {
					buttonDiv = "";
				}
			}
			return buttonDiv;
		}

		if (StringUtil.isNotEmpty(workflowID)) {
			// ָ���˹�����������������ʽ����
			WorkflowAction[] actions = null;
			if (instanceID != 0) {
				try {
					if (WorkflowUtil.isStartStep(instanceID)
							|| Priv.getPriv(User.getUserName(), Priv.ARTICLE, CatalogUtil.getInnerCode(catalogID),
									Priv.ARTICLE_AUDIT)) {
						actions = WorkflowUtil.findAvaiableActions(instanceID);
					}
				} catch (Exception e) {
					LogUtil.warn("Article.init:" + e.getMessage());
					return "";
				}
			} else {
				try {
					actions = WorkflowUtil.findInitActions(Long.parseLong(workflowID));
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
					return "<script>Page.onLoad(function(){Dialog.alert('��ʼ����������ť����" + e.getMessage()
							+ "');});</script>";
				}
			}
			StringBuffer sb = new StringBuffer();
			for (int i = 0; actions != null && i < actions.length; i++) {
				WorkflowAction action = actions[i];
				if (action.getID() == WorkflowAction.APPLY_ACTIONID) {
					sb.append(TButtonTag.getHtml("ClickMethod='" + action.getName() + "';applyStep('" + instanceID
							+ "','" + action.getData().getInt("NodeID") + "')", null,
							"<img src='../Icons/icon003a16.gif'/>", action.getName()));
				} else if (action.getID() == WorkflowAction.TEMPORARYSAVE_ACTIONID) {
					if (instanceID == 0 // �½�δ����
							|| Priv.getPriv(Priv.ARTICLE, CatalogUtil.getInnerCode(catalogID), Priv.ARTICLE_MODIFY)) {
						sb.append(TButtonTag.getHtml("ClickMethod='" + action.getName() + "';save('" + instanceID
								+ "','" + action.getID() + "')", null, "<img src='../Icons/icon003a16.gif'/>",
								action.getName()));
					}
				} else {
					boolean allowSelectUser = "1".equals(action.getData().get("AllowSelectUser"));
					sb.append(TButtonTag.getHtml("ClickMethod='" + action.getName() + "';save('" + instanceID + "','"
							+ action.getID() + "'," + allowSelectUser + ")", null,
							"<img src='../Icons/icon003a16.gif'/>", action.getName()));

				}
			}
			return sb.toString();
		} else {
			// û�й�����
			if (Priv.getPriv(User.getUserName(), Priv.ARTICLE, CatalogUtil.getInnerCode(catalogID), Priv.ARTICLE_MODIFY)) {
				String buttonDiv = TButtonTag.getHtml("save()", "article_modify",
						"<img src='../Icons/icon003a16.gif'/>", "����")
						+ TButtonTag.getHtml("topublish()", "article_modify", "<img src='../Icons/icon003a6.gif'/>",
								"������")
						+ TButtonTag.getHtml("publish()", "article_modify", "<img src='../Icons/icon003a13.gif'/>",
								"����");
				return buttonDiv;
			}
		}
		return "";
	}

	public static Mapx initPreview(Mapx params) {
		String articleID = (String) params.get("ArticleID");
		ZCArticleSchema article = new ZCArticleSchema();
		article.setID(Integer.parseInt(articleID));
		article.fill();
		params = article.toDataRow().toCaseIgnoreMapx();
		return params;
	}

	/**
	 * ��ʼ��������¼��Ƽ������б�
	 */
	public static void relativeDg1DataBind(DataGridAction dga) {
		String relaIDs = new QueryBuilder("select relativearticle from ZCArticle where id=?", dga.getParam("ArticleID"))
				.executeString();
		if (!StringUtil.checkID(relaIDs)) {
			return;
		}
		if (StringUtil.isEmpty(relaIDs)) {
			dga.bindData(new DataTable());
			return;
		}
		DataTable dt = new QueryBuilder("select title,id from zcarticle where id in (" + relaIDs + ")")
				.executeDataTable();

		// ��ID˳������ʾ
		String[] ids = relaIDs.split("\\,");
		DataTable result = new DataTable(dt.getDataColumns(), null);
		for (int i = 0; i < ids.length; i++) {
			for (int j = 0; j < dt.getRowCount(); j++) {
				if (dt.getString(j, "ID").equals(ids[i])) {
					result.insertRow(dt.getDataRow(j));
					break;
				}
			}
		}

		dga.bindData(result);
	}

	public static Mapx initPlayerList(Mapx params) {
		String catalogID = params.getString("CatalogID");
		DataTable dt = new DataTable();
		String defaultPlayer = params.getString("ImagePlayerID");
		if (StringUtil.isNotEmpty(catalogID)) {
			String tempCode = "";
			ZCCatalogSchema catalog = new ZCCatalogSchema();
			catalog.setID(catalogID);
			catalog.fill();
			tempCode = "'" + catalog.getInnerCode() + "'";
			if (StringUtil.isEmpty(defaultPlayer)) {
				defaultPlayer = new QueryBuilder(
						"select ID from ZCImagePlayer where SiteID = ? and RelaCatalogInnerCode = ?",
						Application.getCurrentSiteID(), catalog.getInnerCode()).executeString();
			}
			while (catalog.getParentID() != 0) {
				String temp = catalog.getParentID() + "";
				catalog = new ZCCatalogSchema();
				catalog.setID(temp);
				catalog.fill();
				tempCode += ",'" + catalog.getInnerCode() + "'";
			}
			tempCode += ",'0'";
			dt = new QueryBuilder("select Name,ID from ZCImagePlayer where SiteID = ? and RelaCatalogInnerCode in("
					+ tempCode + ")", Application.getCurrentSiteID()).executeDataTable();
		}
		if (StringUtil.isEmpty(defaultPlayer)) {
			defaultPlayer = "";
		}
		params.put("ImagePlayers", HtmlUtil.dataTableToOptions(dt, defaultPlayer));
		return params;
	}

	public void saveRelaImage() {
		String articleID = $V("ArticleID");
		String imageIDs = $V("ImageIDs");
		String imagePlayerID = $V("ImagePlayerID");
		String[] ids = imageIDs.split(",");
		Transaction trans = new Transaction();
		int count = new QueryBuilder(
				"select count(*) from ZCImageRela where RelaType = 'ImagePlayerImage' and RelaID = ?", imagePlayerID)
				.executeInt();
		count = 6 - count;
		String tempID = new QueryBuilder(
				"select ID from ZCImageRela where RelaType = 'ImagePlayerImage' and RelaID = ? order by OrderFlag asc",
				imagePlayerID).executeOneValue()
				+ "";
		if (count == 0) {
			trans.add(new QueryBuilder("delete from ZCImageRela where RelaType = 'ImagePlayerImage' and ID=?", tempID));
			count = 1;
		}
		ZCImageSchema image = new ZCImageSchema();
		ZCArticleSchema article = new ZCArticleSchema();
		article.setID(articleID);
		article.fill();
		for (int i = 0; i < ids.length; i++) {
			if (i >= count) {
				break;
			}
			trans.add(new QueryBuilder(
					"delete from ZCImageRela where RelaType = 'ImagePlayerImage' and RelaID = ? and ID=?",
					imagePlayerID, ids[i]));
			image = new ZCImageSchema();
			image.setID(ids[i]);
			image.fill();
			String isSingle = CatalogUtil.getSingleFlag(article.getCatalogID());
			String path = "";
			if ("Y".equals(isSingle)) {
				path = (SiteUtil.getURL(Application.getCurrentSiteID()) + "/" + CatalogUtil.getPath(article
						.getCatalogID())).replaceAll("/+", "/");
			} else {
				path = (SiteUtil.getURL(Application.getCurrentSiteID()) + "/" + PubFun.getArticleURL(article))
						.replaceAll("/+", "/");
			}
			image.setLinkURL(path);
			image.setLinkText(article.getTitle());
			trans.add(image, Transaction.UPDATE);
			ZCImageRelaSchema imageRela = new ZCImageRelaSchema();
			imageRela.setID(image.getID());
			imageRela.setRelaID(imagePlayerID);
			imageRela.setRelaType(ImagePlayerRela.RELATYPE_IMAGEPLAYER);
			imageRela.setOrderFlag(OrderUtil.getDefaultOrder());
			imageRela.setAddUser(User.getUserName());
			imageRela.setAddTime(new Date());
			trans.add(imageRela, Transaction.INSERT);
		}
		if (trans.commit()) {
			Response.setLogInfo(1, "������ͼƬ�������ɹ�");
		} else {
			Response.setLogInfo(0, "����ʧ��");
		}
	}

	public static void relaImageDataBind(DataListAction dla) {
		String ImageIDs = dla.getParam("ImageIDs");
		String RelaImageIDs = dla.getParam("RelaImageIDs");
		if (!StringUtil.checkID(ImageIDs) || !StringUtil.checkID(RelaImageIDs)) {
			return;
		}
		DataTable dt = new DataTable();
		if (StringUtil.isNotEmpty(ImageIDs)) {
			String path = Config.getValue("UploadDir") + "/" + SiteUtil.getAlias(Application.getCurrentSiteID()) + "/";
			dt = new QueryBuilder("select * from zcimage where ID in(" + ImageIDs + ")").executeDataTable();
			dt.insertColumn("Alias");
			dt.insertColumn("checkStatus");
			String[] IDs = RelaImageIDs.split(",");
			for (int i = 0; i < dt.getRowCount(); i++) {
				dt.set(i, "Alias", path);
				for (int j = 0; j < IDs.length; j++) {
					if (IDs[j].equals(dt.getString(i, "ID"))) {
						dt.set(i, "checkStatus", "checked");
					}
				}
			}
		}
		dla.bindData(dt);
	}

	public static void dealVote(ZCArticleSchema article, Transaction trans) {
		DataTable vote = new QueryBuilder("select * from zcvote where siteid=? and votecatalogid = ?",
				article.getSiteID(), article.getCatalogID()).executeDataTable();
		if (vote.getRowCount() > 0) {
			DataTable subject = new QueryBuilder("select * from zcvotesubject where voteid=? and votecatalogid = ?",
					vote.getString(0, "ID"), article.getCatalogID()).executeDataTable();
			if (subject.getRowCount() > 0) {
				ZCVoteItemSchema item = new ZCVoteItemSchema();
				item.setID(NoUtil.getMaxID("VoteItemID"));
				item.setVoteID(subject.getString(0, "VoteID"));
				item.setSubjectID(subject.getString(0, "ID"));
				item.setItem(article.getTitle());
				item.setScore(0);
				item.setItemType("0");
				item.setVoteDocID(article.getID());
				item.setOrderFlag(OrderUtil.getDefaultOrder());
				trans.add(item, Transaction.INSERT);
			}
		}
	}

	/**
	 * ϵͳ�Զ��Ƽ����������
	 */
	public static void autoRelativeDataBind(DataGridAction dga) {
		String relaIDs = (String) dga.getParams().get("IDs");
		if (!StringUtil.checkID(relaIDs)) {
			return;
		}
		if ("".equals(relaIDs)) {
			relaIDs = "''";
		}
		DataTable dt = new QueryBuilder("select title,id from zcarticle where id in (" + relaIDs + ")")
				.executeDataTable();
		dga.bindData(dt);

	}

	public static void recommendDg1DataBind(DataGridAction dga) {
		String recIDs = (String) dga.getParams().get("RecommendArticle");
		if (!StringUtil.checkID(recIDs)) {
			return;
		}
		if ("".equals(recIDs)) {
			dga.bindData(new DataTable());
			return;
		}
		DataTable dt = new QueryBuilder("select title,id,addtime,author from zcarticle where id in (" + recIDs + ")")
				.executeDataTable();

		// ��ID˳������ʾ
		String[] ids = recIDs.split("\\,");
		DataTable result = new DataTable(dt.getDataColumns(), null);
		for (int i = 0; i < ids.length; i++) {
			for (int j = 0; j < dt.getRowCount(); j++) {
				if (dt.getString(j, "ID").equals(ids[i])) {
					result.insertRow(dt.getDataRow(j));
					break;
				}
			}
		}

		dga.bindData(result);
	}

	/**
	 * ��������
	 */
	public boolean save() {
		String clickMethod = $V("ClickMethod");
		String logMessage = "";
		try {
			Transaction trans = new Transaction();

			ZCArticleSchema article = new ZCArticleSchema();
			long articleID = Long.parseLong((String) Request.get("ArticleID"));
			article.setID(articleID);

			String method = $V("Method");

			if ("UPDATE".equals(method)) {
				article.fill();
			}
			long catalogID = Long.parseLong($V("CatalogID"));
			article.setCatalogID(catalogID);
			String siteID = CatalogUtil.getSiteID(catalogID);
			article.setSiteID(siteID);

			// �ж���Ŀ�Ƿ��Ǳ�������������
			ZCCatalogConfigSchema config = CMSCache.getCatalogConfig(catalogID);
			if ("Y".equals(config.getBranchManageFlag()) && !UserList.ADMINISTRATOR.equals(User.getUserName())) {
				String branchInnerCode = article.getBranchInnerCode();
				if (StringUtil.isNotEmpty(branchInnerCode) && !User.getBranchInnerCode().equals(branchInnerCode)) {
					Response.setStatus(0);
					Response.setMessage("����������û�в������ĵ�Ȩ�ޣ�");
					return false;
				}
			}

			String innerCode = CatalogUtil.getInnerCode(catalogID);
			article.setCatalogInnerCode(innerCode);
			article.setType($V("Type"));
			String title = $V("Title");
			article.setTitle(title);
			article.setTitleStyle($V("TitleStyle"));
			article.setShortTitle($V("ShortTitle"));
			article.setShortTitleStyle($V("ShortTitleStyle"));
			article.setSubTitle($V("SubTitle"));
			article.setReferURL($V("ReferURL"));
			article.setReferName($V("ReferName"));
			article.setRelativeArticle($V("RelativeArticle"));
			article.setRecommendArticle($V("RecommendArticle"));
			article.setTopFlag($V("TopFlag"));
			article.setCommentFlag($V("CommentFlag"));
			article.setPriority($V("Priority"));
			article.setAttribute($V("Attribute"));

			// ��������ͼ���洢�����վ��Ŀ¼��·��,��upload/default/1_84938439.jpg
			String sitePath = Config.getContextPath() + Config.getValue("UploadDir") + "/" + siteAlias + "/";
			sitePath = sitePath.replaceAll("/+", "/");
			String logo = $V("Logo");
			if (StringUtil.isNotEmpty(logo)) {
				logo = logo.replaceAll(sitePath, "");
			}
			article.setLogo(logo);

			// վ��Ⱥ�ַ�
			article.setClusterTarget($V("ClusterTarget").trim());

			// ���¸���
			article.setReferTarget($V("ReferTarget").trim());

			String author = $V("Author");
			article.setAuthor(author);
			article.setSummary($V("Summary"));

			String content = $V("Content");
			if (StringUtil.isEmpty(content)) {
				content = " ";
			}

			String pageTitles = $V("PageTitles");
			if (StringUtil.isEmpty(pageTitles)) {
				pageTitles = " ";
			}
			article.setPageTitle(pageTitles);

			// ����Զ��ͼƬ������
			if ("1".equals($V("LocalImageFlag"))) {
				String tempContent = content;
				if (Priv.getPriv(Priv.ARTICLE, innerCode, Priv.ARTICLE_MODIFY)) {
					content = copyRemoteFiles(content, trans, articleID);
				}
				if (!tempContent.equals(content)) {
					$S("ContentChanged", "Y");
					$S("Content", content);// ��Ҫ���滻������ݸ���ǰ̨�༭���������´α���ʱ�ٴ��ϴ��ļ�
				}
				article.setContent(content);
				article.setCopyImageFlag("Y");
			} else {
				article.setContent(content);
				article.setCopyImageFlag("N");
			}
			if (Priv.getPriv(Priv.ARTICLE, innerCode, Priv.ARTICLE_MODIFY)) {
				// ����ؼ���
				String keyword = $V("Keyword");
				dealKeyword(article, keyword);

				// ����Tag����
				dealTag(trans, article);

				// �������ͼƬ
				dealRelaImage(trans, article);

				// �����������
				dealRelaAttach(trans, article);

				// ���������Ƶ
				dealRelaVideo(trans, article);
			}

			$S("Keyword", article.getKeyword()); // ���ظ�ǰ̨
			$S("TagWord", article.getTag());
			// ����ʱ��
			if (StringUtil.isNotEmpty($V("PublishDate"))) {
				String publishTime = $V("PublishTime");
				if (StringUtil.isEmpty(publishTime)) {
					publishTime = "00:00:00";
				}
				article.setPublishDate(DateUtil.parse($V("PublishDate") + " " + publishTime, DateUtil.Format_DateTime));
			}

			// ����ʱ��
			if (StringUtil.isEmpty($V("DownlineDate"))) {
				article.setDownlineDate(DateUtil.parse("2099-12-31 23:59:59", DateUtil.Format_DateTime));
			} else {
				String downTime = $V("DownlineTime");
				if (StringUtil.isEmpty(downTime)) {
					downTime = "00:00:00";
				}
				article.setDownlineDate(DateUtil.parse($V("DownlineDate") + " " + downTime, DateUtil.Format_DateTime));
			}

			// �鵵ʱ��
			if (StringUtil.isEmpty($V("ArchiveDate"))) {
				article.setArchiveDate(null);
			} else {
				String archiveTime = $V("ArchiveTime");
				if (StringUtil.isEmpty(archiveTime)) {
					archiveTime = "00:00:00";
				}
				article.setArchiveDate(DateUtil.parse($V("ArchiveDate") + " " + archiveTime, DateUtil.Format_DateTime));
			}

			article.setURL(PubFun.getArticleURL(article));
			article.setRedirectURL($V("RedirectURL"));
			article.setTemplateFlag($V("TemplateFlag"));
			article.setTemplate($V("Template"));

			String issueStr = $V("IssueID");
			if (issueStr != null && !"".equals(issueStr)) {
				article.setIssueID(Long.parseLong($V("IssueID")));
			}

			ZCArticleLogSchema articleLog = new ZCArticleLogSchema();
			articleLog.setID(NoUtil.getMaxID("ArticleLogID"));
			articleLog.setArticleID(articleID);

			String entryId = $V("entryId");// ������ʵ��ID
			String actionId = $V("actionId");// ����������ID

			String workflowID = CatalogUtil.getWorkflow(catalogID);
			Context context = null;
			if ("UPDATE".equals(method)) { // ����
				article.setModifyTime(new Date());
				article.setModifyUser(User.getUserName());
				articleLog.setAction("UPDATE");
				if (StringUtil.isNotEmpty(clickMethod)) {
					articleLog.setActionDetail(clickMethod);
				} else {
					articleLog.setActionDetail("��������");
				}
				if (Priv.getPriv(Priv.ARTICLE, innerCode, Priv.ARTICLE_MODIFY)) {
					trans.add(article, Transaction.UPDATE);

					QueryBuilder qb = new QueryBuilder("update zccatalog set isdirty=1 where id=?", catalogID);
					trans.add(qb);
				}

				// ��������
				if (StringUtil.isNotEmpty(workflowID)) {
					trans.add(new QueryBuilder("update zcarticle set Status=" + STATUS_WORKFLOW + " where id=?",
							article.getID()));
					if ((StringUtil.isEmpty(entryId) || entryId.equals("0"))) {
						// ��������ʱ�����ǰû�й�������entryId=0
						context = WorkflowUtil.createInstance(trans, Long.parseLong(workflowID), "CMS",
								"" + article.getID(), "0");
						article.setWorkFlowID(context.getInstance().getID());
					} else {
						if (StringUtil.isEmpty(actionId)) {
							Response.setError("������ִ�д���entryId=" + entryId + ",actionId=" + actionId);
							return false;
						} else {
							WorkflowAction action = WorkflowUtil.findAction(Long.parseLong(workflowID),
									Integer.parseInt(actionId));
							action.execute(trans, Long.parseLong(entryId), $V("NextStepUser"), $V("Memo"));
						}
					}
				} else {
					if (article.getStatus() == STATUS_PUBLISHED || article.getStatus() == STATUS_TOPUBLISH) {
						article.setStatus(STATUS_EDITING);
					}
				}
				logMessage = "����";
			} else if ("ADD".equals(method)) { // ����
				// ����ͶƱ
				dealVote(article, trans);

				article.setPublishFlag("1");
				article.setOrderFlag(OrderUtil.getDefaultOrder());
				article.setHitCount(0);
				article.setStickTime(0);
				article.setStatus(Article.STATUS_DRAFT);
				article.setAddTime(new Date(article.getOrderFlag()));

				articleLog.setAction("INSERT");
				articleLog.setActionDetail("�½�����");

				article.setAddUser(User.getUserName());
				article.setBranchInnerCode(User.getBranchInnerCode());
				if (StringUtil.isNotEmpty(workflowID)) {
					// ���빤����
					context = WorkflowUtil.createInstance(trans, Long.parseLong(workflowID), "CMS",
							"" + article.getID(), "0");
					article.setWorkFlowID(context.getInstance().getID());
					// ���Ϊ�ݴ棬��д�빤����״̬����Ϊ��Ȼ�ǳ���
					if (StringUtil.isNotEmpty(actionId)
							&& !(WorkflowAction.TEMPORARYSAVE_ACTIONID + "").equals(actionId)) {
						article.setStatus(STATUS_WORKFLOW);
					}
				}

				trans.add(article, Transaction.INSERT);
				String sqlArticleCount = "update zccatalog set total = total+1,isdirty=1 where id=?";
				trans.add(new QueryBuilder(sqlArticleCount, catalogID));
				logMessage = "����";
			}

			articleLog.setAddUser(User.getUserName());
			articleLog.setAddTime(new Date());
			if (StringUtil.isEmpty(workflowID)) {// �й�����ʱ����Ҫ����������Adapter��Ӵ�����ʷ��¼
				trans.add(articleLog, Transaction.INSERT);
			}

			// �������ע����������ע��¼
			if (StringUtil.isNotEmpty($V("NoteContent"))) {
				ArticleNote.add(trans, "" + articleID, $V("NoteContent"));
			}

			if (Priv.getPriv(Priv.ARTICLE, innerCode, Priv.ARTICLE_MODIFY)) {
				// �����Զ����ֶ�
				Article.saveCustomColumn(trans, Request, catalogID, articleID, "ADD".equals(method));

				// ���ơ���������
				copy(Request, trans, article);
			}
			article.setProp3($V("RelaImagePlayerID"));
			if (ExtendManager.hasAction("Article.BeforeSave")) {
				ExtendManager.executeAll("Article.BeforeSave", new Object[] { trans, article });
			}
			if (trans.commit()) {
				Transaction relaImgTrans = new Transaction();
				String relaChange = $V("RelaChange");
				if (StringUtil.isNotEmpty(relaChange) && relaChange.equals("true")) {
					String relaImageIDs = $V("RelaImageIDs");
					long relaImagePlayerID = Long.parseLong($V("RelaImagePlayerID"));
					String[] ids = relaImageIDs.split(",");
					int count = new QueryBuilder(
							"select count(*) from ZCImageRela where RelaType = 'ImagePlayerImage' and RelaID = ?",
							relaImagePlayerID).executeInt();
					int delCount = 0;
					delCount = count - (6 - ids.length);
					String[] tempID = new QueryBuilder(
							"select ID from ZCImageRela where RelaType = 'ImagePlayerImage' and RelaID = ? order by OrderFlag asc",
							relaImagePlayerID).executeDataTable().toString().split("\n");
					if (delCount > 0) {
						String temp = "";
						for (int i = 0; i < delCount; i++) {
							if (i != 0) {
								temp += ",";
							}
							temp += tempID[i + 1];
						}
						relaImgTrans
								.add(new QueryBuilder(
										"delete from ZCImageRela where RelaType = 'ImagePlayerImage' and ID in ("
												+ temp + ")"));
					}
					ZCImageSchema image = new ZCImageSchema();
					for (int i = 0; i < ids.length; i++) {
						relaImgTrans.add(new QueryBuilder(
								"delete from ZCImageRela where RelaType = 'ImagePlayerImage' and RelaID = ? and ID=?",
								relaImagePlayerID, Long.parseLong(ids[i])));
						image = new ZCImageSchema();
						image.setID(ids[i]);
						image.fill();
						String isSingle = CatalogUtil.getSingleFlag(article.getCatalogID());
						if ("Y".equals(isSingle)) {
							sitePath = SiteUtil.getURL(Application.getCurrentSiteID());
							if (!sitePath.endsWith("/")) {
								sitePath += "/";
							}
							sitePath += CatalogUtil.getPath(article.getCatalogID());
						} else {
							sitePath = SiteUtil.getURL(Application.getCurrentSiteID());
							if (!sitePath.endsWith("/")) {
								sitePath += "/";
							}
							sitePath += PubFun.getArticleURL(article);
						}
						image.setLinkURL(sitePath);
						image.setLinkText(article.getTitle());
						relaImgTrans.add(image, Transaction.UPDATE);
						ZCImageRelaSchema imageRela = new ZCImageRelaSchema();
						imageRela.setID(image.getID());
						imageRela.setRelaID(relaImagePlayerID);
						imageRela.setRelaType(ImagePlayerRela.RELATYPE_IMAGEPLAYER);
						imageRela.setOrderFlag(OrderUtil.getDefaultOrder());
						imageRela.setAddUser(User.getUserName());
						imageRela.setAddTime(new Date());
						relaImgTrans.add(imageRela, Transaction.INSERT);
					}
				}
				relaImgTrans.commit();
				Response.put("SaveTime", DateUtil.getCurrentDateTime());
				UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_SAVEARTICLE, logMessage + "����:" + article.getTitle()
						+ "�ɹ���", Request.getClientIP());

				/*
				 * ����������ж�������entryID=0��������δ����ʵ���������ύ��ִ�У��ύǰִ�л��Ҳ������ݣ� �м�����������г�����������1���½�����֮����Ϊ��Ŀ���Ϲ�����
				 * 2���½�����ʱ���ݴ�ֱ���ύ����3��������;������ϵͳ�����£���WebService
				 */
				if (StringUtil.isNotEmpty(workflowID) && (StringUtil.isEmpty(entryId) || "0".equals(entryId))) {
					if (StringUtil.isNotEmpty(actionId)
							&& Integer.parseInt(actionId) != WorkflowAction.TEMPORARYSAVE_ACTIONID) {
						WorkflowAction action = WorkflowUtil.findAction(Long.parseLong(workflowID),
								Integer.parseInt(actionId));
						action.execute(context, $V("NextStepUser"), $V("Memo"));
						context.getTransaction().commit();
					}
				}

				// �������״̬
				String buttonDiv = "";
				QueryBuilder qb = new QueryBuilder("select Status from ZCArticle where ID=?", article.getID());
				int status = qb.executeInt();
				if (status == Article.STATUS_TOPUBLISH || status == Article.STATUS_PUBLISHED) {
					if (StringUtil.isNotEmpty(workflowID)) {
						buttonDiv = "";
						if (Priv.getPriv(Priv.ARTICLE, CatalogUtil.getInnerCode(catalogID), Priv.ARTICLE_MODIFY)) {
							buttonDiv += TButtonTag.getHtml("publish(true)", null,
									"<img src='../Icons/icon003a13.gif'/>", "����");
							buttonDiv += TButtonTag.getHtml("ClickMethod='�����޸�';save('" + article.getWorkFlowID()
									+ "','" + WorkflowAction.RESTART_ACTIONID + "')", null,
									"<img src='../Icons/icon003a16.gif'/>", "�����޸�");
						}
					} else { // û�й�����
						buttonDiv += TButtonTag.getHtml("save()", "article_modify",
								"<img src='../Icons/icon003a16.gif'/>", "����")
								+ TButtonTag.getHtml("publish()", "article_modify",
										"<img src='../Icons/icon003a13.gif'/>", "����");
						Response.put("buttonDiv", buttonDiv);
					}

					Response.put("buttonDiv", buttonDiv);
				} else {
					if (StringUtil.isNotEmpty(workflowID)) {
						// ָ���˹�����������������ʽ����
						WorkflowAction[] actions = null;
						actions = WorkflowUtil.findAvaiableActions(article.getWorkFlowID());
						StringBuffer sb = new StringBuffer();
						for (int i = 0; i < actions.length; i++) {
							WorkflowAction action = actions[i];
							if (action.getID() == WorkflowAction.APPLY_ACTIONID) {
								sb.append(TButtonTag.getHtml("ClickMethod='" + action.getName() + "';applyStep('"
										+ article.getWorkFlowID() + "','" + action.getData().getInt("NodeID") + "')",
										null, "<img src='../Icons/icon003a16.gif'/>", action.getName()));
							} else {
								boolean allowSelectUser = "1".equals(action.getData().get("AllowSelectUser"));
								sb.append(TButtonTag.getHtml(
										"ClickMethod='" + action.getName() + "';save('" + article.getWorkFlowID()
												+ "','" + action.getID() + "'," + allowSelectUser + ")", null,
										"<img src='../Icons/icon003a16.gif'/>", action.getName()));
							}
						}
						Response.put("buttonDiv", sb.toString());
					} else { // û�й�����
						buttonDiv = TButtonTag.getHtml("save()", "article_modify",
								"<img src='../Icons/icon003a16.gif'/>", "����")
								+ TButtonTag.getHtml("topublish()", "article_modify",
										"<img src='../Icons/icon003a6.gif'/>", "������")
								+ TButtonTag.getHtml("publish()", "article_modify",
										"<img src='../Icons/icon003a13.gif'/>", "����");
						Response.put("buttonDiv", buttonDiv);
					}
				}
			} else {
				Response.setStatus(0);
				UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_SAVEARTICLE, logMessage + "����:" + article.getTitle()
						+ "ʧ�ܣ�", Request.getClientIP());
				Response.setMessage("���������ĵ�����ʧ�ܣ�");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			Response.setStatus(0);
			UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_SAVEARTICLE, logMessage + "����ʧ�ܣ�", Request.getClientIP());
			Response.setMessage("��������" + e.getMessage());
			return false;
		}
		return true;
	}

	public static void saveCustomColumn(Transaction trans, Mapx map, long catalogID, long articleID, boolean newFlag) {
		if (!newFlag) {
			DataTable columnDT = ColumnUtil.getColumnValue(ColumnUtil.RELA_TYPE_DOCID, articleID);
			if (columnDT.getRowCount() > 0) {
				trans.add(new QueryBuilder("delete from zdcolumnvalue where relatype=? and relaid = ?",
						ColumnUtil.RELA_TYPE_DOCID, articleID + ""));
			}
			trans.add(ColumnUtil.getValueFromRequest(catalogID, articleID, map), Transaction.INSERT);
		} else {
			trans.add(ColumnUtil.getValueFromRequest(catalogID, articleID, map), Transaction.INSERT);
		}
	}

	public void checkBadWord() {
		String priority = $V("Priority");
		String title = $V("Title");

		String badMsg = "";
		String badTitle = BadWord.checkBadWord(title, priority);
		if (StringUtil.isNotEmpty(badTitle)) {
			badMsg += "����:" + badTitle + ",";
		}

		String shortTitle = $V("ShortTitle");
		String badShortTitle = BadWord.checkBadWord(shortTitle, priority);
		if (StringUtil.isNotEmpty(badShortTitle)) {
			badMsg += "�̱���:" + badShortTitle + ",";
		}

		String badSubTitle = BadWord.checkBadWord($V("SubTitle"), priority);
		if (StringUtil.isNotEmpty(badSubTitle)) {
			badMsg += "������:" + badSubTitle + ",";
		}

		String badAuthor = BadWord.checkBadWord($V("Author"), priority);
		if (StringUtil.isNotEmpty(badAuthor)) {
			badMsg += "����:" + badAuthor + ",";
		}

		String badKeyword = BadWord.checkBadWord($V("Keyword"), priority);
		if (StringUtil.isNotEmpty(badKeyword)) {
			badMsg += "�ؼ���:" + badKeyword + ",";
		}

		String badReferName = BadWord.checkBadWord($V("ReferName"), priority);
		if (StringUtil.isNotEmpty(badReferName)) {
			badMsg += "��Դ:" + badReferName + ",";
		}

		String badContent = BadWord.checkBadWord($V("Content"), priority);
		if (StringUtil.isNotEmpty(badContent)) {
			badMsg += "����:" + badContent + ",";
		}

		String badSummary = BadWord.checkBadWord($V("Summary"), priority);
		if (StringUtil.isNotEmpty(badSummary)) {
			badMsg += "ժҪ:" + badSummary + ",";
		}

		if (StringUtil.isNotEmpty(badMsg)) {
			Response.setStatus(1);
			Response.put("BadMsg", badMsg);
		} else {
			Response.setStatus(0);
		}
	}

	private static boolean isKeywordFlag(long catalogID) {
		ZCCatalogSchema catalog = CatalogUtil.getSchema(catalogID);
		boolean auto = false;
		while (true) {
			if ("Y".equals(catalog.getKeywordFlag())) {
				auto = true;
				break;
			}
			if (catalog.getParentID() == 0) {
				break;
			}
			catalog = CatalogUtil.getSchema(catalog.getParentID());
		}
		return auto;
	}

	private static void dealKeyword(ZCArticleSchema article, String keyword) {
		if (StringUtil.isNotEmpty(keyword)) {
			keyword = keyword.trim().replaceAll("\\s+", " ");
			keyword = keyword.replaceAll("��", " ");
			keyword = keyword.replaceAll("��", " ");
			keyword = keyword.replaceAll(";", " ");
			keyword = keyword.replaceAll("\\,+", " ");
			if (",".equals(keyword) || StringUtil.isEmpty(keyword)) {
				keyword = "";
			} else {
				if (keyword.indexOf(",") == 0) {
					keyword = keyword.substring(1);
				}
				if (keyword.lastIndexOf(",") == keyword.length() - 1) {
					keyword = keyword.substring(0, keyword.length() - 1);
				}
			}
		} else {
			if (isKeywordFlag(article.getCatalogID())) {
				keyword = StringUtil.join(IndexUtil.getKeyword(article.getTitle(), article.getContent()), " ");
			}
		}
		article.setKeyword(keyword);
	}

	private void dealTag(Transaction trans, ZCArticleSchema article) {
		String tagword = $V("TagWord");
		if (StringUtil.isNotEmpty(tagword)) {
			tagword = tagword.trim().replaceAll("\\s+", " ");
			tagword = tagword.replaceAll("��", " ");
			tagword = tagword.replaceAll("��", " ");
			tagword = tagword.replaceAll(";", " ");
			tagword = tagword.replaceAll("\\,+", " ");
			if (",".equals(tagword) || StringUtil.isEmpty(tagword)) {
				tagword = "";
			} else {
				if (tagword.indexOf(",") == 0) {
					tagword = tagword.substring(1);
				}
				if (tagword.lastIndexOf(",") == tagword.length() - 1) {
					tagword = tagword.substring(0, tagword.length() - 1);
				}
			}
			if (StringUtil.isNotEmpty(tagword)) {
				String[] tagwords = tagword.split(" ");
				for (int i = 0; i < tagwords.length; i++) {
					if (Tag.checkTagWord(Application.getCurrentSiteID(), tagwords[i])) {
						ZCTagSchema tag = new ZCTagSchema();
						tag.setID(NoUtil.getMaxID("TagID"));
						tag.setTag(tagwords[i]);
						tag.setAddTime(new Date());
						tag.setAddUser(User.getUserName());
						tag.setUsedCount(1);
						tag.setSiteID(Application.getCurrentSiteID());
						trans.add(tag, Transaction.INSERT);
					}
				}
			}
		}

		article.setTag(tagword);

	}

	/**
	 * ������/��������
	 * 
	 * @param trans
	 * @param article
	 */
	public static void copy(DataCollection dc, Transaction trans, ZCArticleSchema article) {
		String catalogIDs = dc.getString("ReferTarget");
		String referTypeStr = dc.getString("ReferType");
		// �Զ����ֶ�DestCatalog ���ڸ��Ƶ�ָ���б���Ŀ��
		String otherCatalogID = dc.getString(ColumnUtil.PREFIX + "DestCatalog");
		if (StringUtil.isNotEmpty(otherCatalogID)) {
			if (StringUtil.isEmpty(catalogIDs)) {
				catalogIDs = otherCatalogID;
				referTypeStr = "2";
			} else {
				catalogIDs = catalogIDs + "," + otherCatalogID;
			}
		}

		// 1.����ȫ�� 2 ����
		if (StringUtil.isEmpty(referTypeStr)) {
			referTypeStr = "1";
		}

		int referType = Integer.parseInt(referTypeStr);
		if (StringUtil.isNotEmpty(catalogIDs)) {
			String[] catalogArr = catalogIDs.split(",");
			String copySiteID = null;
			for (int j = 0; j < catalogArr.length; j++) {
				if (StringUtil.isEmpty(catalogArr[j].trim())) {
					continue;
				}
				long toCatalogID = Long.parseLong(catalogArr[j]);
				if (toCatalogID == article.getCatalogID()) {
					continue;
				}
				ZCArticleSchema articleRefer = new ZCArticleSchema();
				articleRefer.setReferSourceID(article.getID());
				articleRefer.setCatalogID(toCatalogID);
				ZCArticleSet set = articleRefer.query();

				// �������Ŀ���ڴ�ƪ���µ����ã����������������,�����½�����
				if (set != null && set.size() > 0) {
					articleRefer = set.get(0);
					articleRefer.setTitle(article.getTitle());
					articleRefer.setShortTitle(article.getShortTitle());
					articleRefer.setSubTitle(article.getSubTitle());
					articleRefer.setAuthor(article.getAuthor());
					articleRefer.setKeyword(article.getKeyword());
					articleRefer.setTag(article.getTag());
					articleRefer.setSummary(articleRefer.getSummary());
					articleRefer.setReferTarget("");

					if (referType == 1) {
						articleRefer.setContent(article.getContent());
					}

					// �������¹�������
					if (Priv.getPriv(Priv.ARTICLE, CatalogUtil.getInnerCode(toCatalogID), Priv.ARTICLE_MODIFY)) {
						// �������ͼƬ
						dealRelaImage(trans, articleRefer);

						// �����������
						dealRelaAttach(trans, articleRefer);

						// ���������Ƶ
						dealRelaVideo(trans, articleRefer);

						// ����ͶƱ
						dealVote(articleRefer, trans);

						// �������ע����������ע��¼
						if (StringUtil.isNotEmpty(dc.getString("NoteContent"))) {
							ArticleNote.add(trans, "" + articleRefer.getID(), dc.getString("NoteContent"));
						}

						// �����Զ����ֶ�
						Article.saveCustomColumn(trans, dc, toCatalogID, articleRefer.getID(), false);
					}

					trans.add(articleRefer, Transaction.UPDATE);
				} else {
					ZCArticleSchema articleColone = (ZCArticleSchema) article.clone();
					articleColone.setID(NoUtil.getMaxID("DocID"));

					articleColone.setCatalogID(toCatalogID);
					articleColone.setCatalogInnerCode(CatalogUtil.getInnerCode(toCatalogID));
					articleColone.setReferType(referType);
					articleColone.setReferSourceID(article.getID());
					articleColone.setReferTarget("");
					// ����Bug #570 ���й���������Ŀ���¸��Ƶ�û�й���������Ŀ����,��״̬Ӧ��Ϊ����״̬
					// ��ת�е����²������ƣ����Ϊ����Ա����Ը��ƣ������Ҫ����Ϊ����
					articleColone.setStatus(Article.STATUS_DRAFT);
					articleColone.setWorkFlowID(0);
					articleColone.setPublishDate(null);
					articleColone.setOrderFlag(OrderUtil.getDefaultOrder());

					// �����վ�㸴��
					if (StringUtil.isEmpty(copySiteID)) {
						copySiteID = CatalogUtil.getSiteID(catalogArr[j]);
					}
					articleColone.setSiteID(copySiteID);

					// �ÿ��ö��ֶΡ�����ģ����
					articleColone.setTopFlag("0");
					articleColone.setTemplateFlag("0");
					articleColone.setTemplate("");

					// ��������
					if (referType == 2) {
						articleColone.setType("4");
						String referURL = PubFun.getArticleURL(article);
						// ���Դ������ת���������£�������Դ���µ�RedirectURL
						if ("4".equals(article.getType())) {
							referURL = article.getRedirectURL();
						}
						articleColone.setRedirectURL(referURL);
						articleColone.setContent("");
					}

					// �������¹�������
					if (Priv.getPriv(Priv.ARTICLE, CatalogUtil.getInnerCode(toCatalogID), Priv.ARTICLE_MODIFY)) {
						// �������ͼƬ
						dealRelaImage(trans, articleColone);

						// �����������
						dealRelaAttach(trans, articleColone);

						// ���������Ƶ
						dealRelaVideo(trans, articleColone);

						// ����ͶƱ
						dealVote(articleColone, trans);

						// �������ע����������ע��¼
						if (StringUtil.isNotEmpty(dc.getString("NoteContent"))) {
							ArticleNote.add(trans, "" + articleColone.getID(), dc.getString("NoteContent"));
						}

						// �����Զ����ֶ�
						Article.saveCustomColumn(trans, dc, toCatalogID, articleColone.getID(), true);
					}

					trans.add(articleColone, Transaction.INSERT);
				}

				String sqlArticleCount = "update zccatalog set total=total+1 where id=?";
				trans.add(new QueryBuilder(sqlArticleCount, Long.parseLong(catalogArr[j])));

				// ������־
				ZCArticleLogSchema articleLog = new ZCArticleLogSchema();
				articleLog.setID(NoUtil.getMaxID("ArticleLogID"));
				articleLog.setArticleID(article.getID());
				articleLog.setAction("COPY");
				String action = null;
				if (referType == 1) {
					action = "����";
				} else if (referType == 2) {
					action = "����";
				}
				articleLog.setActionDetail(action + "����" + CatalogUtil.getName(article.getCatalogID()) + action + "��"
						+ CatalogUtil.getName(toCatalogID) + "��");
				articleLog.setAddUser(User.getUserName());
				articleLog.setAddTime(new Date());
				trans.add(articleLog, Transaction.INSERT);
			}
		}
	}

	/**
	 * ��Publisher���ã�����ʱ����Ƿ���Ҫ�����������
	 */
	public static void dealRelaArticle(ZCArticleSchema article) {
		if (StringUtil.isEmpty(article.getRelativeArticle())) {
			if (StringUtil.isEmpty(article.getKeyword())) {
				String keyword = StringUtil.join(IndexUtil.getKeyword(article.getTitle(), article.getContent()), " ");
				article.setKeyword(keyword);
			}
			String ids = getAutoRelaIDs(article.getCatalogID(), article.getID(), article.getKeyword());
			String[] arr = ids.split("\\,");
			if (!"N".equals(Config.getValue("ComplementRelativeArticle"))) {
				String RelativeArticleCount = Config.getValue("RelativeArticleCount");
				int count = 15;
				if (NumberUtil.isNumber(RelativeArticleCount)) {
					count = Integer.parseInt(RelativeArticleCount);
				}
				if (arr.length < count) {
					QueryBuilder qb = new QueryBuilder(
							"select id from ZCArticle where CatalogID=? and Status=? and ID<>? order by orderflag desc",
							article.getCatalogID(), Article.STATUS_PUBLISHED);
					qb.add(article.getID());
					DataTable dt = qb.executePagedDataTable(count - arr.length, 0);
					if (dt.getRowCount() > 0) {
						Object[] vs = dt.getColumnValues(0);
						String str = StringUtil.join(vs);
						if (StringUtil.isEmpty(ids)) {
							ids = str;
						} else {
							ids = ids + "," + str;
						}
					}
				}
			}
			article.setRelativeArticle(ids);
		}
	}

	/**
	 * �������ͼƬ �����ͼƬ���뵽����ͼƬ��������
	 * 
	 * @param content
	 * @param articleID
	 * @param trans
	 */
	public static void dealRelaImage(Transaction trans, ZCArticleSchema article) {
		// ���������е�ͼƬ,�������º�ͼƬ
		String regex = "nswtpimagerela=\"(.*?)\"";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(article.getContent());
		int imgIndex = 0;
		Mapx map = new Mapx();
		boolean flag = false;

		// �õ�����ͼƬ��fileName,ƴ�ӳ��ַ���
		while (matcher.find(imgIndex)) {
			int s = matcher.start();
			int e = matcher.end();
			imgIndex = e;
			String picSrc = article.getContent().substring(s, e);
			String imageID = picSrc.substring("nswtpimagerela=".length() + 1, picSrc.length() - 1);
			if (StringUtil.isDigit(imageID)) {
				map.put(imageID, imageID);
			}
			flag = true;
		}

		// �����������ͼƬ
		if (flag && map.size() > 0) {
			String str = StringUtil.join(map.keyArray(), ",");
			if (!StringUtil.checkID(str)) {
				LogUtil.warn("Article.dealRelaImage:���ͼƬID���ж����ַ�");
				return;
			}
			trans.add(new QueryBuilder("delete from zcimagerela where relaid=? and relaType=?", article.getID(),
					RELA_IMAGE));

			// ����ͼƬ���linkUrl��linkText
			String siteUrl = SiteUtil.getURL(article.getSiteID());
			if (siteUrl.endsWith("shtml")) {
				siteUrl = siteUrl.substring(0, siteUrl.lastIndexOf("/"));
			}

			if (!siteUrl.endsWith("/")) {
				siteUrl = siteUrl + "/";
			}
			trans.add(new QueryBuilder("update zcimage set linkurl='" + siteUrl + PubFun.getArticleURL(article)
					+ "',linktext='" + article.getTitle() + "' where ID in (" + str + ")"));
			String[] IDs = str.split(",");
			for (int i = 0; IDs != null && i < IDs.length; i++) {
				if (StringUtil.isEmpty(IDs[i]) || !StringUtil.isDigit(IDs[i])) {
					continue;
				}
				ZCImageRelaSchema rela = new ZCImageRelaSchema();
				rela.setID(IDs[i]);
				rela.setRelaID(article.getID());
				rela.setRelaType(RELA_IMAGE);
				rela.setOrderFlag(OrderUtil.getDefaultOrder());
				rela.setAddUser(User.getUserName());
				rela.setAddTime(new Date());
				trans.add(rela, Transaction.INSERT);
			}
		} else {
			trans.add(new QueryBuilder("delete from zcimagerela where relaid=? and relaType=?", article.getID(),
					RELA_IMAGE));
		}
	}

	/**
	 * ������ظ��� ����ظ������뵽���¸�����������
	 * 
	 * @param content
	 * @param articleID
	 * @param trans
	 */
	public static void dealRelaAttach(Transaction trans, ZCArticleSchema article) {
		if (StringUtil.isEmpty(article.getContent())) {
			return;
		}

		// ���������еĸ���,�������º͸���
		String regex = "nswtpattachrela=\"(.*?)\"";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(article.getContent());
		int imgIndex = 0;
		Mapx map = new Mapx();
		boolean flag = false;

		// �õ����и�����ID,ƴ�ӳ��ַ���
		while (matcher.find(imgIndex)) {
			int s = matcher.start();
			int e = matcher.end();
			imgIndex = e;
			String picSrc = article.getContent().substring(s, e);
			String attachID = picSrc.substring("nswtpattachrela=".length() + 1, picSrc.length() - 1);
			if (StringUtil.isDigit(attachID)) {
				map.put(attachID, attachID);
			}
			flag = true;
		}

		// ����������и���
		if (flag) {
			String str = StringUtil.join(map.keyArray(), ",");
			if (!StringUtil.checkID(str)) {
				LogUtil.warn("Article.dealRelaVideo:��ظ���ID���ж����ַ�");
				return;
			}
			if (StringUtil.isEmpty(str)) {
				str = "''";
			}
			trans.add(new QueryBuilder("delete from zcattachmentrela where relaid=? and relaType=? and ID not in ("
					+ str + ")", article.getID(), RELA_ATTACH));
			String[] IDs = str.split(",");
			for (int i = 0; IDs != null && i < IDs.length; i++) {
				ZCAttachmentRelaSchema rela = new ZCAttachmentRelaSchema();
				rela.setID(IDs[i]);
				rela.setRelaID(article.getID());
				rela.setRelaType(RELA_ATTACH);
				if (!rela.fill()) {
					trans.add(rela, Transaction.INSERT);
				}
			}
		} else {
			trans.add(new QueryBuilder("delete from ZCAttachmentRela where relaid=? and relaType=?", article.getID(),
					RELA_ATTACH));
		}
	}

	/**
	 * ���������Ƶ ����ظ������뵽������Ƶ��������
	 * 
	 * @param content
	 * @param articleID
	 * @param trans
	 */
	public static void dealRelaVideo(Transaction trans, ZCArticleSchema article) {
		if (StringUtil.isEmpty(article.getContent())) {
			return;
		}

		// ���������е���Ƶ,�������º���Ƶ
		String regex = "nswtpvideorela=\"(.*?)\"";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(article.getContent());
		int imgIndex = 0;
		Mapx map = new Mapx();
		boolean flag = false;

		// �õ�������Ƶ��ID,ƴ�ӳ��ַ���
		while (matcher.find(imgIndex)) {
			int s = matcher.start();
			int e = matcher.end();
			imgIndex = e;
			String picSrc = article.getContent().substring(s, e);
			String videoID = picSrc.substring("nswtpvideorela=".length() + 1, picSrc.length() - 1);
			if (StringUtil.isDigit(videoID)) {
				map.put(videoID, videoID);
			}
			flag = true;
		}

		// �������������Ƶ
		if (flag) {
			String str = StringUtil.join(map.keyArray(), ",");
			if (!StringUtil.checkID(str)) {
				LogUtil.warn("Article.dealRelaVideo:��ظ���ID���ж����ַ�");
				return;
			}
			if (StringUtil.isEmpty(str)) {
				str = "''";
			}
			trans.add(new QueryBuilder("delete from zcvideorela where relaid=? and relaType=? and ID not in (" + str
					+ ")", article.getID(), RELA_VIDEO));
			String[] IDs = str.split(",");
			for (int i = 0; IDs != null && i < IDs.length; i++) {
				ZCVideoRelaSchema rela = new ZCVideoRelaSchema();
				rela.setID(IDs[i]);
				rela.setRelaID(article.getID());
				rela.setRelaType(RELA_VIDEO);
				if (!rela.fill()) {
					trans.add(rela, Transaction.INSERT);
				}
			}
		} else {
			trans.add(new QueryBuilder("delete from zcvideorela where relaid=? and relaType=?", article.getID(),
					RELA_VIDEO));
		}
	}

	/**
	 * ����Զ���ļ�
	 * 
	 * @param destUrl
	 * @param fileName
	 * @return
	 */
	private boolean getRemoteFile(String destUrl, String fileName) {
		// ʹ�ô�������ͼƬ
		if ("Y".equalsIgnoreCase(Config.getValue("Proxy.IsUseProxy"))) {
			System.setProperty("http.proxyHost", Config.getValue("Proxy.Host"));
			System.setProperty("http.proxyPort", Config.getValue("Proxy.Port"));
			Authenticator.setDefault(new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(Config.getValue("Proxy.UserName"), new String(Config
							.getValue("Proxy.Password")).toCharArray());
				}
			});
		}

		try {
			byte buf[] = new byte[8096];
			int size = 0;
			URL url;
			url = new URL(destUrl);

			BufferedInputStream bis = new BufferedInputStream(url.openStream());
			bis.toString();
			FileOutputStream fos;
			fos = new FileOutputStream(fileName);
			while ((size = bis.read(buf)) != -1) {
				fos.write(buf, 0, size);
			}

			fos.close();
			bis.close();
		} catch (MalformedURLException e) {
			LogUtil.info("�Ƿ���ַ");
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			LogUtil.info("û���ҵ���Ӧ���ļ�");
			e.printStackTrace();
			return false;
		}
		return true;

	}

	private String copyRemoteFiles(String content, Transaction trans, long articleID) {
		Pattern varPattern = Pattern.compile("src=([\"|\'| ])*?(http.*?\\.(gif|jpg|jpeg|bmp|png))\\1",
				Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

		Matcher m = varPattern.matcher(content);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			int s = m.start();
			int e = m.end();
			sb.append(content.substring(lastEndIndex, s));
			String imagePath = m.group(2);
			String fileName = imagePath.substring(imagePath.lastIndexOf("/") + 1);
			String tempDir = "defaultTemp/";
			String local = Config.getContextRealPath() + Config.getValue("UploadDir") + "/" + siteAlias
					+ "/upload/Image/";
			File file = new File(local + tempDir);
			if (!file.exists()) {
				file.mkdirs();
			}

			String imageID = "";
			if (getRemoteFile(imagePath, local + tempDir + fileName)) {
				ZCImageSchema image = AutoUpload.dealImage(local, fileName, trans);
				if (StringUtil.isNotEmpty(image.getFileName())) {
					String uploadPath = Config.getContextPath() + Config.getValue("UploadDir") + "/" + siteAlias + "/"
							+ image.getPath();
					imagePath = uploadPath.replaceAll("//", "/") + image.getFileName();
					LogUtil.info("���Ƴɹ�" + m.group(2));
				} else {
					LogUtil.info("����ʧ��" + imagePath);
				}
				imageID = image.getID() + "";
			} else {
				LogUtil.info("����ʧ��" + imagePath);
			}
			sb.append("nswtpimagerela=\"" + imageID + "\" src=\"" + imagePath + "\"");
			lastEndIndex = e;
		}
		sb.append(content.substring(lastEndIndex));
		return sb.toString();
	}

	/**
	 * ����������ʷ�汾���Զ�����
	 */
	public void saveVersion() {
		String articleID = $V("ArticleID");
		long id = Long.parseLong(articleID);
		ZCArticleSchema article = new ZCArticleSchema();
		article.setID(id);

		if (!article.fill()) {
			article.setID(articleID);
			article.setValue(Request);
			long catalogID = Long.parseLong($V("CatalogID"));
			article.setCatalogID(catalogID);

			String siteID = CatalogUtil.getSiteID(catalogID);
			article.setSiteID(siteID);

			article.setCatalogInnerCode(CatalogUtil.getInnerCode(catalogID));
			article.setHitCount(0);
			article.setPublishFlag("Y");
			article.setStickTime(0);

			article.setOrderFlag(OrderUtil.getDefaultOrder());
			article.setAddTime(new Date());
			article.setAddUser(User.getUserName());
		}

		Transaction trans = new Transaction();
		trans.add(article, Transaction.BACKUP);

		if (trans.commit()) {
			Response.setStatus(1);
			Response.put("SaveTime", DateUtil.getCurrentDateTime());
		} else {
			Response.setStatus(0);
			Response.setMessage("�������ݿ�ʱ��������!");
		}
	}

	public void topublish() {
		String ArticleID = $V("ArticleID");
		ZCArticleSchema article = new ZCArticleSchema();
		article.setID(ArticleID);
		if (!article.fill()) {
			Response.setLogInfo(0, "���ȱ����ĵ����ٽ��д������");
			return;
		}
		if ((article.getStatus() == Article.STATUS_DRAFT || article.getStatus() == Article.STATUS_EDITING)
				&& article.getWorkFlowID() == 0) {
			ZCArticleSet set = article.query(new QueryBuilder("where workflowid is null and  status in ("
					+ Article.STATUS_DRAFT + "," + Article.STATUS_EDITING + ") and id =" + ArticleID
					+ " or id in(select id from zcarticle where refersourceid =" + ArticleID + " )"));
			for (int i = 0; i < set.size(); i++) {
				set.get(i).setStatus(Article.STATUS_TOPUBLISH);
			}
			set.update();
			UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_TOPUBLISHARTICLE, "תΪ�����������ɹ�,id:" + ArticleID,
					Request.getClientIP());
			Response.setLogInfo(1, "תΪ�����������ɹ���");
		} else if (article.getWorkFlowID() != 0) {
			Response.setLogInfo(0, "����ʧ�ܣ������µ�״̬Ϊ��" + STATUS_MAP.getString(article.getStatus() + "")
					+ "�����ĵ��ڹ�����ת�У�����תΪ������");
		} else {
			Response.setLogInfo(0, "����ʧ�ܣ������µ�״̬Ϊ��" + STATUS_MAP.getString(article.getStatus() + "")
					+ "��ֻ�С����塯�͡����±༭��������תΪ������״̬��");
		}
	}

	/**
	 * ����
	 */
	public boolean publish() {
		ZCArticleSchema article = new ZCArticleSchema();
		int articleID = Integer.parseInt((String) Request.get("ArticleID"));

		ZCArticleSet set = new ZCArticleSet();
		article.setID(articleID);
		if (article.fill()) {
			set.add(article);
		}
		ZCArticleSet referset = article.query(new QueryBuilder(" where refersourceid=? ", articleID));
		if (referset.size() > 0) {
			for (int i = 0; i < referset.size(); i++) {
				String catalogInnerCode = referset.get(i).getCatalogInnerCode();
				boolean hasPriv = Priv.getPriv(User.getUserName(), Priv.ARTICLE, catalogInnerCode, Priv.ARTICLE_MANAGE);
				String workflow = CatalogUtil.getWorkflow(referset.get(i).getCatalogID());
				// ���Ŀ����Ŀû�й��������û���Ŀ����Ŀ�����¹���Ȩ�ޣ��򷢲�ʱһ�����Ϊ����״̬
				if (hasPriv && StringUtil.isEmpty(workflow)) {
					set.add(referset.get(i));
				}
			}
		}

		// ���·���״̬
		Transaction trans = new Transaction();
		for (int i = 0; i < set.size(); i++) {
			Date date = new Date();// �������modifyTime��ȫ�ļ����Դ�Ϊ��ǲ��Ҹ���
			ZCArticleSchema article2 = set.get(i);
			QueryBuilder qb = new QueryBuilder("update zcarticle set modifyuser=?,modifytime=?,status=? where id=?");
			if (article2.getPublishDate() == null) {
				qb = new QueryBuilder(
						"update zcarticle set publishdate=?,modifyuser=?,modifytime=?,status=? where id=?");
				qb.add(date);
				article2.setPublishDate(date);
			}
			qb.add(User.getUserName());
			qb.add(date);
			if (article2.getPublishDate().getTime() > System.currentTimeMillis()) {
				qb.add(Article.STATUS_TOPUBLISH);
			} else {
				qb.add(Article.STATUS_PUBLISHED);
			}
			qb.add(article2.getID());
			qb.executeNoQuery();
			article2.setModifyUser(User.getUserName());
			article.setModifyTime(date);
			article.setStatus(Article.STATUS_PUBLISHED);
		}

		UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_PUBLISHARTICLE, "��������:" + article.getTitle() + "�ɹ���",
				Request.getClientIP(), trans);

		if (trans.commit()) {
			// �������ļ��Ķ���������̨����ִ��
			PublishMonitor m = new PublishMonitor();
			m.addJob(set);

			Response.setStatus(1);
			return true;
		} else {
			Response.setStatus(0);
			return false;
		}
	}

	/**
	 * �������²�����
	 */
	public void saveAndPublish() {
		if (save()) {
			if (!publish()) {
				Response.setStatus(1);
				Response.setMessage("�����ɹ���" + Response.Message);
			}
		}
	}

	public void getArticle() {
		ZCArticleSchema article = new ZCArticleSchema();
		long articleID = Long.parseLong((String) Request.get("ArticleID"));
		article.setID(articleID);
		if (article.fill()) {
			String content = article.getContent();
			String[] pages = content.split(Constant.PAGE_BREAK, -1);
			StringBuffer pageStr = new StringBuffer();
			for (int i = 0; i < pages.length; i++) {
				pageStr.append("'" + StringUtil.htmlEncode(pages[i]) + "'");
				if (i != pages.length - 1) {
					pageStr.append(",");
				}
			}

			Response.setStatus(1);
			Response.put("NewsType", article.getType());
			Response.put("TopFlag", article.getTopFlag());
			Response.put("CommentFlag", article.getCommentFlag());
			Response.put("Priority", article.getPriority());
			Response.put("TemplateFlag", article.getTemplate());
			Response.put("ShortTitle", article.getShortTitle());
			Response.put("Title", article.getTitle());
			Response.put("SubTitle", article.getSubTitle());
			Response.put("Keyword", article.getKeyword());
			Response.put("Summary", article.getSummary());
			Response.put("Template", article.getTemplate());
			Response.put("Author", article.getAuthor());
			Response.put("RedirectURL", article.getRedirectURL());
			Response.put("TagWord", article.getTag());

			Response.put("Pages", new Integer(pages.length));
			Response.put("ContentPages", pageStr.toString());
		}
	}

	public void getPlayerName() {
		String relaImagePlayerID = $V("RelaImagePlayerID");
		String PlayerName = new QueryBuilder("select Name from zcimageplayer where ID = ?",
				Long.parseLong(relaImagePlayerID)).executeOneValue()
				+ "";
		PlayerName = "[" + PlayerName + "]";
		Response.put("PlayerName", PlayerName);
	}

	/**
	 * �༭���в���ͼƬ
	 */
	public void getPicSrc() {
		String ids = $V("PicID");
		String customFlag = $V("Custom");
		String isRela = $V("isRela");
		String catalogID = $V("CatalogID");
		if (!StringUtil.checkID(ids) || !StringUtil.checkID(catalogID)) {
			return;
		}
		if (StringUtil.isEmpty(ids)) {
			ids = "''";
		}
		ZCImageSet imageSet = new ZCImageSchema()
				.query(new QueryBuilder(" where id in (" + ids + ") order by id desc"));

		String path = Config.getContextPath() + Config.getValue("UploadDir") + "/" + siteAlias;
		path = path.replaceAll("/+", "/");
		StringBuffer images = new StringBuffer();
		// ���Զ���ͼƬ�����������е�ͼƬ
		if (StringUtil.isNotEmpty(customFlag) && imageSet.size() > 0) {
			images.append(path + "/" + imageSet.get(0).getPath() + "1_" + imageSet.get(0).getFileName());
			this.Response.put("1_picSrc", images.toString());
			images = new StringBuffer();
			images.append(path + "/" + imageSet.get(0).getPath() + "s_" + imageSet.get(0).getFileName());
			this.Response.put("s_picSrc", images.toString());
		} else {
			for (int i = 0; i < imageSet.size(); i++) {
				if (StringUtil.isEmpty(isRela)) {
					images.append("<p style='text-align: center;'>");
					if ((StringUtil.isNotEmpty(Config.getValue("ArticleImageWidth")) && imageSet.get(i).getWidth() > Integer
							.parseInt(Config.getValue("ArticleImageWidth")))
							|| (StringUtil.isNotEmpty(Config.getValue("ArticleImageHeight")) && imageSet.get(i)
									.getHeight() > Integer.parseInt(Config.getValue("ArticleImageHeight")))) {
						images.append("<a alt='����鿴��ͼ' href='" + path + "/" + imageSet.get(i).getPath()
								+ imageSet.get(i).getSrcFileName() + "' target='_blank' >");
					}
					images.append("<img border=0 nswtpimagerela='" + imageSet.get(i).getID() + "' src='" + path + "/"
							+ imageSet.get(i).getPath() + "1_" + imageSet.get(i).getFileName() + "'>");
					if ((StringUtil.isNotEmpty(Config.getValue("ArticleImageWidth")) && imageSet.get(i).getWidth() > Integer
							.parseInt(Config.getValue("ArticleImageWidth")))
							|| (StringUtil.isNotEmpty(Config.getValue("ArticleImageHeight")) && imageSet.get(i)
									.getHeight() > Integer.parseInt(Config.getValue("ArticleImageHeight")))) {
						images.append("</a>");
					}
					images.append("</p>");
					images.append("<p style='text-align: center;'>" + imageSet.get(i).getName() + "</p>");
				} else {
					images.append("<img src='" + path + "/" + imageSet.get(i).getPath() + "s_"
							+ imageSet.get(i).getFileName()
							+ "' width='80' onload=\"if(this.width>80)this.width='80';\" >");
				}
			}
			this.Response.put("1_picSrc", images.toString());
		}
	}

	/**
	 * �༭���в����ļ�
	 */
	public void getFilePath() {
		String ids = $V("FileID");
		String catalogID = $V("CatalogID");
		if (!StringUtil.checkID(ids) || !StringUtil.checkID(catalogID)) {
			return;
		}
		if (StringUtil.isEmpty(ids)) {
			ids = "''";
		}
		DataTable dt = new QueryBuilder(
				"select id,name,suffix,path,filename,srcfilename from zcattachment where id in (" + ids + ")")
				.executeDataTable();

		StringBuffer filePath = new StringBuffer();
		for (int i = 0; i < dt.getRowCount(); i++) {
			String path = Config.getContextPath() + Config.getValue("UploadDir") + "/"
					+ Application.getCurrentSiteAlias();
			path = path.replaceAll("/+", "/");
			if (StringUtil.isNotEmpty(catalogID) && "N".equals(CatalogUtil.getAttachDownFlag(catalogID))) {
				filePath.append("<a href='"
						+ (path + "/" + dt.getString(i, "path") + dt.getString(i, "filename")).replaceAll("/+", "/")
						+ "' nswtpattachrela='" + dt.getString(i, "id") + "'>" + dt.getString(i, "name") + "."
						+ dt.getString(i, "suffix") + "</a>");
			} else if ("N".equals(SiteUtil.getAttachDownFlag(Application.getCurrentSiteID()))) {
				filePath.append("<a href='"
						+ (path + "/" + dt.getString(i, "path") + dt.getString(i, "filename")).replaceAll("/+", "/")
						+ "' nswtpattachrela='" + dt.getString(i, "id") + "'>" + dt.getString(i, "name") + "."
						+ dt.getString(i, "suffix") + "</a>");
			} else {
				filePath.append("<a href='" + Config.getContextPath() + "Services/AttachDownLoad.jsp?id="
						+ dt.getString(i, "id") + "' nswtpattachrela='" + dt.getString(i, "id") + "'>"
						+ dt.getString(i, "name") + "." + dt.getString(i, "suffix") + "</a>");
			}
		}
		this.Response.put("FilePath", filePath.toString());
	}

	/**
	 * �༭���в���Flash
	 */
	public void getFlashPath() {
		String ids = $V("FlashID");
		String catalogID = $V("CatalogID");
		if (!StringUtil.checkID(ids) || !StringUtil.checkID(catalogID)) {
			return;
		}
		if (StringUtil.isEmpty(ids)) {
			ids = "''";
		}
		String path = Config.getContextPath() + Config.getValue("UploadDir") + "/" + Application.getCurrentSiteAlias();
		path = path.replaceAll("/+", "/");
		DataTable dt = new QueryBuilder(
				"select id,name,suffix,path,filename,srcfilename from zcattachment where id in (" + ids + ")")
				.executeDataTable();

		StringBuffer flashs = new StringBuffer();
		for (int i = 0; i < dt.getRowCount(); i++) {
			flashs.append("<embed src='"
					+ (path + "/" + dt.get(i, "Path") + dt.get(i, "FileName")).replaceAll("/+", "/")
					+ "' type='application/x-shockwave-flash' width='320' height='240' play='true' loop='true' menu='true'></embed>");
		}
		this.Response.put("FlashPath", flashs.toString());
	}

	/**
	 * �༭���в�����Ƶ
	 */
	public void getVideoPath() {
		String ids = $V("VideoID");
		String catalogID = $V("CatalogID");
		if (!StringUtil.checkID(ids) || !StringUtil.checkID(catalogID)) {
			return;
		}
		if (StringUtil.isEmpty(ids)) {
			ids = "''";
		}
		DataTable dt = new QueryBuilder(
				"select id,name,suffix,path,filename,srcfilename,imageName from zcvideo where id in (" + ids + ")")
				.executeDataTable();

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < dt.getRowCount(); i++) {
			String fileName = "../" + dt.getString(i, "path") + dt.getString(i, "filename");
			String imageName = "../" + dt.getString(i, "path") + dt.getString(i, "imageName");
			String playerPath = SiteUtil.getURL(CatalogUtil.getSiteID(catalogID)) + "/images/player.swf";
			fileName = fileName.replaceAll("/+", "/");
			imageName = imageName.replaceAll("/+", "/");
			playerPath = playerPath.replaceAll("http://", StringUtil.md5Hex("http://"));
			playerPath = playerPath.replaceAll("/+", "/");
			playerPath = playerPath.replaceAll(StringUtil.md5Hex("http://"), "http://");
			sb.append("<div style='text-align: center;'><embed wmode=\"transparent\" ");
			sb.append("flashvars=\"file=" + fileName + "&image=" + imageName + "&stretching=fill\"");
			sb.append(" nswtpvideorela=\"" + dt.getString(i, "id") + "\" src=\"" + playerPath
					+ "\" quality=\"high\" allowfullscreen=\"true\" type=\"application/x-shockwave-flash\"");
			sb.append(" width=\"480\" height=\"360\"></embed><br/>");
			sb.append(dt.getString(i, "Name") + "<br/></div>");
		}
		this.Response.put("VideoPath", sb.toString());
	}

	public void getKeywordOrSummary() {
		String type = $V("Type");
		String title = $V("Title");
		String content = $V("Content");
		if ("Keyword".equals(type)) {
			$S("Text", StringUtil.join(IndexUtil.getKeyword(title, content), " "));
		} else {
			$S("Text", IndexUtil.getTextAbstract(title, content));
		}
	}

	public static String getKeywordSetting(long catalogID) {
		StringBuffer sb = new StringBuffer(",");
		String innerCode = CatalogUtil.getInnerCode(catalogID);
		while (innerCode.length() > 0) {
			String setting = CatalogUtil.getSchema(CatalogUtil.getIDByInnerCode(innerCode)).getKeywordSetting();
			if (StringUtil.isNotEmpty(setting)) {
				sb.append("," + setting);
			}
			innerCode = innerCode.substring(0, innerCode.length() - 6);
		}
		return sb.toString().substring(1);
	}

	/**
	 * ���ͨ��ȫ�ļ����������������µ�ID����
	 */
	public void getAutoRelaIDs() {
		long catalogID = Request.getLong("CatalogID");
		long articleID = Request.getLong("ArticleID");
		String keyword = $V("Keyword");
		if (StringUtil.isEmpty(keyword)) {
			String Content = $V("Content");
			String Title = $V("Title");
			keyword = StringUtil.join(IndexUtil.getKeyword(Title, Content));
		}
		$S("IDs", getAutoRelaIDs(catalogID, articleID, keyword));
	}

	private static String getAutoRelaIDs(long catalogID, long articleID, String keyword) {
		if (keyword == null) {// �����ǿ��ַ���
			return null;
		}
		long siteID = CatalogUtil.getSchema(catalogID).getSiteID();
		String setting = getKeywordSetting(catalogID);
		SearchResult sr = null;
		if (StringUtil.isEmpty(setting)) {// û���������ֻ�ڱ���Ŀ��ƥ��
			sr = ArticleRelaIndexer.getRelaArticles(siteID, catalogID, articleID, keyword);
		} else {
			sr = ArticleRelaIndexer.getRelaArticles(siteID, articleID, setting, keyword);
		}
		StringBuffer sb = new StringBuffer();
		if (sr != null && sr.Data != null) {
			for (int i = 0; i < sr.Data.getRowCount(); i++) {
				if (i != 0) {
					sb.append(",");
				}
				sb.append(sr.Data.getString(i, "ID"));
			}
		}
		return sb.toString();
	}

	public void getJSCode() {
		String ids = $V("ID");
		String catalogID = $V("CatalogID");
		if (!StringUtil.checkID(ids) || !StringUtil.checkID(catalogID)) {
			return;
		}
		if (StringUtil.isEmpty(ids)) {
			ids = "''";
		}
		ZCVoteSet set = new ZCVoteSchema().query(new QueryBuilder("where id in (" + ids + ")"));
		StringBuffer jsCodes = new StringBuffer();
		String levelStr = PubFun.getLevelStr(CatalogUtil.getDetailLevel(catalogID));
		for (int i = 0; i < set.size(); i++) {
			ZCVoteSchema vote = set.get(i);
			String code = "";
			code += "<div>���飺" + vote.getTitle() + "\n";
			code += "<!--" + vote.getTitle() + "-->\n";
			code += "<link href=\"" + levelStr + "images/vote.css\" type=\"text/css\" rel=\"stylesheet\" />";
			code += "<script language='javascript' src='" + levelStr + "images/vote.js'></script>";
			code += "<script language='javascript' src='"
					+ (levelStr + "js/vote_" + vote.getID()).replaceAll("/+", "/") + ".js'></script>";
			code += "\n</div>";
			jsCodes.append(code);
			if (!new File((Config.getContextRealPath() + Config.getValue("Statical.TargetDir") + "/"
					+ SiteUtil.getAlias(vote.getSiteID()) + "/js/").replaceAll("/+", "/")
					+ "vote_" + vote.getID() + ".js").exists()) {
				Vote.generateJS(vote.getID());
			}
		}
		$S("JSCode", jsCodes.toString());
	}

	public void verifySameTitle() {
		String Title = $V("Title");
		String CatalogID = $V("CatalogID");
		QueryBuilder qb = new QueryBuilder("select count(*) from ZCArticle where CatalogID=? and Title=? and ID<>?",
				Long.parseLong(CatalogID), Title);
		qb.add(Long.parseLong($V("ID")));
		int Count = qb.executeInt();
		Response.setStatus(Count);
	}
}
