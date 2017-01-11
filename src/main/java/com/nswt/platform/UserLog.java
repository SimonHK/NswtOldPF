package com.nswt.platform;

import java.util.Date;

import com.nswt.cms.pub.PubFun;
import com.nswt.framework.Page;
import com.nswt.framework.User;
import com.nswt.framework.controls.DataGridAction;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.DateUtil;
import com.nswt.framework.utility.HtmlUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.pub.NoUtil;
import com.nswt.schema.ZDUserLogSchema;
import com.nswt.schema.ZDUserLogSet;

/**
 * @Author ������
 * @Date 2007-7-18
 * @Mail nswt@nswt.com.cn
 */
public class UserLog extends Page {

	// ��¼״̬
	public static final String LOGIN = "Login";
	public static final String LOGOUT = "Logout";
	public static final String LOG = "Log";
	public static final Mapx USERLOG_LOGTYPE_MAP = new Mapx();

	static {
		USERLOG_LOGTYPE_MAP.put("0", "");
		USERLOG_LOGTYPE_MAP.put(LOGIN, "��½");
		USERLOG_LOGTYPE_MAP.put(LOGOUT, "�˳�");
	}
	// ��̳����
	public static final Mapx USERLOG_FORUM_MAP = new Mapx();
	public static final String FORUM = "Forum";
	public static final String FORUM_TOPTHEME = "TopTheme";
	public static final String FORUM_TOPCANCEL = "TopCancel";
	public static final String FORUM_DELTHEME = "DelTheme";
	public static final String FORUM_BESTTHEME = "BestTheme";
	public static final String FORUM_BESTCANCEL = "BestCancel";
	public static final String FORUM_BRIGHTTHEME = "BrightTheme";
	public static final String FORUM_UPTHEME = "UpTheme";
	public static final String FORUM_DOWNTHEME = "DownTheme";
	public static final String FORUM_MOVETHEME = "MoveTheme";
	public static final String FORUM_EDITPOST = "EditPost";
	public static final String FORUM_DELPOST = "DelPost";
	public static final String FORUM_HIDEPOST = "HidePost";

	static {
		USERLOG_FORUM_MAP.put("0", "");
		USERLOG_FORUM_MAP.put(FORUM_TOPTHEME, "�ö�����");
		USERLOG_FORUM_MAP.put(FORUM_TOPCANCEL, "ȡ���ö�");
		USERLOG_FORUM_MAP.put(FORUM_DELTHEME, "ɾ������");
		USERLOG_FORUM_MAP.put(FORUM_BESTTHEME, "���þ���");
		USERLOG_FORUM_MAP.put(FORUM_BESTCANCEL, "ȡ������");
		USERLOG_FORUM_MAP.put(FORUM_UPTHEME, "��������");
		USERLOG_FORUM_MAP.put(FORUM_DOWNTHEME, "�³�����");
		USERLOG_FORUM_MAP.put(FORUM_MOVETHEME, "�ƶ�����");
		USERLOG_FORUM_MAP.put(FORUM_EDITPOST, "�༭����");
		USERLOG_FORUM_MAP.put(FORUM_DELPOST, "ɾ������");
		USERLOG_FORUM_MAP.put(FORUM_HIDEPOST, "��������");
	}

	// վ�����
	public static final Mapx USERLOG_SITE_MAP = new Mapx();
	public static final String SITE = "Site";
	public static final String SITE_ADDSITE = "AddSite";
	public static final String SITE_DELSITE = "DelSite";
	public static final String SITE_UPDATESITE = "UpdateSite";
	static {
		USERLOG_SITE_MAP.put("0", "");
		USERLOG_SITE_MAP.put(SITE_ADDSITE, "����վ��");
		USERLOG_SITE_MAP.put(SITE_DELSITE, "ɾ��վ��");
		USERLOG_SITE_MAP.put(SITE_UPDATESITE, "�޸�վ��");
	}
	// ��Ŀ����
	public static final Mapx USERLOG_CATALOG_MAP = new Mapx();
	public static final String CATALOG = "Catalog";
	public static final String CATALOG_ADDCATALOG = "AddCataLog";
	public static final String CATALOG_DELCATALOG = "DelCataLog";
	public static final String CATALOG_UPDATECATALOG = "UpdateCataLog";
	static {
		USERLOG_CATALOG_MAP.put("0", "");
		USERLOG_CATALOG_MAP.put(CATALOG_ADDCATALOG, "������Ŀ");
		USERLOG_CATALOG_MAP.put(CATALOG_DELCATALOG, "ɾ����Ŀ");
		USERLOG_CATALOG_MAP.put(CATALOG_UPDATECATALOG, "�޸���Ŀ");
	}
	// ���²���
	public static final Mapx USERLOG_ARTICLE_MAP = new Mapx();
	public static final String ARTICLE = "Article";
	public static final String ARTICLE_SAVEARTICLE = "AddArticle";
	public static final String ARTICLE_DELARTICLE = "DelArticle";
	public static final String ARTICLE_PUBLISHARTICLE = "PublishArticle";
	public static final String ARTICLE_TOPUBLISHARTICLE = "ToPublishArticle";
	public static final String ARTICLE_MOVEARTICLE = "MoveArticle";
	public static final String ARTICLE_TOPARTICLE = "TopArticle";
	public static final String ARTICLE_NOTTOPARTICLE = "NotTopArticle";
	public static final String ARTICLE_UPARTICLE = "UpArticle";
	public static final String ARTICLE_DOWNARTICLE = "DownArticle";
	public static final String ARTICLE_COPYARTICLE = "CopyArticle";
	static {
		USERLOG_ARTICLE_MAP.put("0", "");
		USERLOG_ARTICLE_MAP.put(ARTICLE_SAVEARTICLE, "��������");
		USERLOG_ARTICLE_MAP.put(ARTICLE_DELARTICLE, "ɾ������");
		USERLOG_ARTICLE_MAP.put(ARTICLE_PUBLISHARTICLE, "��������");
		USERLOG_ARTICLE_MAP.put(ARTICLE_TOPUBLISHARTICLE, "תΪ����������");
		USERLOG_ARTICLE_MAP.put(ARTICLE_MOVEARTICLE, "ת������");
		USERLOG_ARTICLE_MAP.put(ARTICLE_TOPARTICLE, "�ö�����");
		USERLOG_ARTICLE_MAP.put(ARTICLE_NOTTOPARTICLE, "ȡ���ö�����");
		USERLOG_ARTICLE_MAP.put(ARTICLE_UPARTICLE, "��������");
		USERLOG_ARTICLE_MAP.put(ARTICLE_DOWNARTICLE, "��������");
		USERLOG_ARTICLE_MAP.put(ARTICLE_COPYARTICLE, "��������");

	}
	// ý������
	public static final Mapx USERLOG_RESOURCE_MAP = new Mapx();
	public static final String RESOURCE = "Resource";
	public static final String RESOURCE_ADDTYPEIMAGE = "AddTypeImage";
	public static final String RESOURCE_ADDIMAGE = "AddImage";
	public static final String RESOURCE_EDITIMAGE = "EditImage";
	public static final String RESOURCE_PUBLISTHIMAGE = "PublishImage";
	public static final String RESOURCE_COPYIMAGE = "CopyImage";
	public static final String RESOURCE_MOVEIMAGE = "MoveImage";
	public static final String RESOURCE_DELIMAGE = "DelImage";

	public static final String RESOURCE_ADDTYPEVIDEO = "AddTypeVideo";
	public static final String RESOURCE_ADDVIDEO = "AddVideo";
	public static final String RESOURCE_EDITVIDEO = "EditVideo";
	public static final String RESOURCE_PUBLISTHVIDEO = "PublishVideo";
	public static final String RESOURCE_COPYVIDEO = "CopyVideo";
	public static final String RESOURCE_MOVEVIDEO = "MoveVideo";
	public static final String RESOURCE_DELVIDEO = "DelVideo";

	public static final String RESOURCE_ADDTYPEAUDIO = "AddTypeAudio";
	public static final String RESOURCE_ADDAUDIO = "AddAudio";
	public static final String RESOURCE_EDITAUDIO = "EditAudio";
	public static final String RESOURCE_PUBLISTHAUDIO = "PublishAudio";
	public static final String RESOURCE_COPYAUDIO = "CopyAudio";
	public static final String RESOURCE_MOVEAUDIO = "MoveAudio";
	public static final String RESOURCE_DELAUDIO = "DelAudio";

	public static final String RESOURCE_ADDTYPEATTACHMENT = "AddTypeAttachment";
	public static final String RESOURCE_ADDATTACHMENT = "AddAttachment";
	public static final String RESOURCE_EDITATTACHMENT = "EditAttachment";
	public static final String RESOURCE_PUBLISTHATTACHMENT = "PublishAttachment";
	public static final String RESOURCE_COPYATTACHMENT = "CopyAttachment";
	public static final String RESOURCE_MOVEATTACHMENT = "MoveAttachment";
	public static final String RESOURCE_DELATTACHMENT = "DelAttachment";
	static {
		USERLOG_RESOURCE_MAP.put("0", "");
		USERLOG_RESOURCE_MAP.put(RESOURCE_ADDTYPEIMAGE, "����ͼƬ����");
		USERLOG_RESOURCE_MAP.put(RESOURCE_ADDIMAGE, "����ͼƬ");
		USERLOG_RESOURCE_MAP.put(RESOURCE_EDITIMAGE, "�༭ͼƬ");
		USERLOG_RESOURCE_MAP.put(RESOURCE_PUBLISTHIMAGE, "����ͼƬ");
		USERLOG_RESOURCE_MAP.put(RESOURCE_COPYIMAGE, "����ͼƬ");
		USERLOG_RESOURCE_MAP.put(RESOURCE_MOVEIMAGE, "�ƶ�ͼƬ");
		USERLOG_RESOURCE_MAP.put(RESOURCE_DELIMAGE, "ɾ��ͼƬ");
		USERLOG_RESOURCE_MAP.put(RESOURCE_ADDTYPEVIDEO, "������Ƶ����");
		USERLOG_RESOURCE_MAP.put(RESOURCE_ADDVIDEO, "������Ƶ");
		USERLOG_RESOURCE_MAP.put(RESOURCE_EDITVIDEO, "�༭��Ƶ");
		USERLOG_RESOURCE_MAP.put(RESOURCE_PUBLISTHVIDEO, "������Ƶ");
		USERLOG_RESOURCE_MAP.put(RESOURCE_COPYVIDEO, "������Ƶ");
		USERLOG_RESOURCE_MAP.put(RESOURCE_MOVEVIDEO, "�ƶ���Ƶ");
		USERLOG_RESOURCE_MAP.put(RESOURCE_DELVIDEO, "ɾ����Ƶ");
		USERLOG_RESOURCE_MAP.put(RESOURCE_ADDTYPEAUDIO, "������Ƶ����");
		USERLOG_RESOURCE_MAP.put(RESOURCE_ADDAUDIO, "������Ƶ");
		USERLOG_RESOURCE_MAP.put(RESOURCE_EDITAUDIO, "�༭��Ƶ");
		USERLOG_RESOURCE_MAP.put(RESOURCE_PUBLISTHAUDIO, "������Ƶ");
		USERLOG_RESOURCE_MAP.put(RESOURCE_COPYAUDIO, "������Ƶ");
		USERLOG_RESOURCE_MAP.put(RESOURCE_MOVEAUDIO, "�ƶ���Ƶ");
		USERLOG_RESOURCE_MAP.put(RESOURCE_DELAUDIO, "ɾ����Ƶ");
		USERLOG_RESOURCE_MAP.put(RESOURCE_ADDTYPEATTACHMENT, "������������");
		USERLOG_RESOURCE_MAP.put(RESOURCE_ADDATTACHMENT, "���Ӹ���");
		USERLOG_RESOURCE_MAP.put(RESOURCE_EDITATTACHMENT, "�༭����");
		USERLOG_RESOURCE_MAP.put(RESOURCE_PUBLISTHATTACHMENT, "��������");
		USERLOG_RESOURCE_MAP.put(RESOURCE_COPYATTACHMENT, "���Ƹ���");
		USERLOG_RESOURCE_MAP.put(RESOURCE_MOVEATTACHMENT, "�ƶ�����");
		USERLOG_RESOURCE_MAP.put(RESOURCE_DELATTACHMENT, "ɾ������");
	}

	// �û���ɫ����
	public static final Mapx USERLOG_USER_MAP = new Mapx();
	public static final String USER = "User";
	public static final String USER_DELUSER = "DelUser";
	public static final String USER_DELROLE = "DelROLE";
	public static final String USER_EDITPASSWORD = "EditPassword";
	static {
		USERLOG_USER_MAP.put("0", "");
		USERLOG_USER_MAP.put(USER_DELUSER, "ɾ���û�");
		USERLOG_USER_MAP.put(USER_DELROLE, "ɾ����ɫ");
		USERLOG_USER_MAP.put(USER_EDITPASSWORD, "�޸�����");

	}
	// ϵͳ����
	public static final Mapx USERLOG_SYSTEM_MAP = new Mapx();
	public static final String SYSTEM = "System";
	public static final String SYSTEM_DELBRANCH = "DelBranch";
	public static final String SYSTEM_DELCODE = "DelCode";
	public static final String SYSTEM_DELCONFIG = "DelConfig";
	public static final String SYSTEM_DELSCHEDULE = "DelSchedule";
	public static final String SYSTEM_DELMENU = "DelMenu";
	static {
		USERLOG_SYSTEM_MAP.put("0", "");
		USERLOG_SYSTEM_MAP.put(SYSTEM_DELBRANCH, "ɾ������");
		USERLOG_SYSTEM_MAP.put(SYSTEM_DELCODE, "ɾ������");
		USERLOG_SYSTEM_MAP.put(SYSTEM_DELCONFIG, "ɾ��������");
		USERLOG_SYSTEM_MAP.put(SYSTEM_DELSCHEDULE, "ɾ����ʱ����");
		USERLOG_SYSTEM_MAP.put(SYSTEM_DELMENU, "ɾ���˵�");

	}

	public static final Mapx USERLOG_MAP = new Mapx();
	static {
		USERLOG_MAP.put(LOG, USERLOG_LOGTYPE_MAP);
		USERLOG_MAP.put(FORUM, USERLOG_FORUM_MAP);
		USERLOG_MAP.put(SITE, USERLOG_SITE_MAP);
		USERLOG_MAP.put(CATALOG, USERLOG_CATALOG_MAP);
		USERLOG_MAP.put(ARTICLE, USERLOG_ARTICLE_MAP);
		USERLOG_MAP.put(USER, USERLOG_USER_MAP);
		USERLOG_MAP.put(SYSTEM, USERLOG_SYSTEM_MAP);
		USERLOG_MAP.put(RESOURCE, USERLOG_RESOURCE_MAP);
	}
	/**
	 * ҳ����ʾ�������б�MAP
	 */
	public static final Mapx USERLOG_SELECT_MAP = new Mapx();
	static {
		USERLOG_SELECT_MAP.put(LOG, "��¼״̬");
		USERLOG_SELECT_MAP.put(FORUM, "��̳����");
		USERLOG_SELECT_MAP.put(SITE, "վ�����");
		USERLOG_SELECT_MAP.put(CATALOG, "��Ŀ����");
		USERLOG_SELECT_MAP.put(ARTICLE, "���²���");
		USERLOG_SELECT_MAP.put(USER, "�û���ɫ����");
		USERLOG_SELECT_MAP.put(SYSTEM, "ϵͳ����");
		USERLOG_SELECT_MAP.put(RESOURCE, "ý������");
	}

	public void menuVisit() {
		String id = Request.valueArray()[0].toString();
		if (!StringUtil.verify(id, "Int")) {
			return;
		}
		DataTable dt = new QueryBuilder(
				"select Name,(select Name from ZDMenu where id=a.ParentID) from ZDMenu a where id=?", id)
				.executeDataTable();
		String menu = dt.getString(0, 1) + "->" + dt.getString(0, 0);
		ZDUserLogSchema userlog = new ZDUserLogSchema();
		userlog.setUserName(User.getUserName());
		userlog.setIP(Request.getClientIP());
		userlog.setAddTime(new Date());
		userlog.setLogID(NoUtil.getMaxID("LogID"));
		userlog.setLogType("Menu");
		userlog.setLogMessage("���ʲ˵���" + menu);
		userlog.insert();
		Response.setStatus(1);
	}

	public void logout() {
		if (log("Menu", "", "�˳�ϵͳ", Request.getClientIP())) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
		}

	}

	public static void dg1DataBind(DataGridAction dga) {
		String searchUser = dga.getParams().getString("SearchUser");
		String ip = dga.getParams().getString("IP");
		String logMessage = dga.getParams().getString("LogMessage");
		String startDate = dga.getParams().getString("StartDate");
		String endDate = dga.getParams().getString("EndDate");
		String logType = dga.getParams().getString("LogType");
		String subType = dga.getParams().getString("SubType");

		QueryBuilder qb = new QueryBuilder("select a.*,(select b.RealName from zduser b where b.UserName = a.UserName) as RealName from ZDUserLog a where 1=1");
		if (StringUtil.isNotEmpty(searchUser)) {
			qb.append(" and UserName like ? ", "%" + searchUser + "%");
		}
		if (StringUtil.isNotEmpty(ip)) {
			qb.append(" and IP like ? ", "%" + ip + "%");
		}
		if (StringUtil.isNotEmpty(logType)) {
			qb.append(" and LogType like ? ", "%" + logType + "%");
		}
		if (StringUtil.isNotEmpty(subType) && !"0".equals(subType)) {
			qb.append(" and SubType like ? ", "%" + subType + "%");
		}
		if (StringUtil.isNotEmpty(logMessage)) {
			qb.append(" and LogMessage like ? ", "%" + logMessage + "%");
		}
		if (StringUtil.isNotEmpty(startDate) && StringUtil.isNotEmpty(endDate)) {
			qb.append(" and AddTime >=?", startDate);
			qb.append(" and AddTime<=?", endDate);
		}
		qb.append(" order by addtime desc");
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dt.decodeColumn("LogType", USERLOG_SELECT_MAP);
		dga.setTotal(qb);
		dga.bindData(dt);
	}

	public static Mapx init(Mapx params) {
		Date date = new Date();
		String str = DateUtil.toString(date);
		params.put("Time", str);
		params.put("LogType", HtmlUtil.mapxToOptions(USERLOG_SELECT_MAP, true));
		return params;
	}
	
	public static Mapx initDialog(Mapx params) {
		ZDUserLogSchema userLog = new ZDUserLogSchema();
		userLog.setUserName(params.getString("UserName"));
		userLog.setLogID(params.getString("LogID"));
		userLog.fill();
		params = userLog.toMapx();
		params.put("LogType", USERLOG_SELECT_MAP.getString(userLog.getLogType()));
		params.put("RealName", PubFun.getUserRealName(userLog.getUserName()));
		return params;
	}

	public void del() {
		String IDs = $V("ids");
		if (!StringUtil.checkID(IDs)) {
			Response.setStatus(0);
			Response.setMessage("����IDʱ��������!");
			return;
		}
		ZDUserLogSet set = new ZDUserLogSchema().query(new QueryBuilder("where LogID in (" + IDs + ")"));
		if (set.deleteAndBackup()) {
			Response.setLogInfo(1, "ɾ���ɹ�");
		} else {
			Response.setLogInfo(0, "ɾ��ʧ��");
		}
	}

	public void delAll() {
		QueryBuilder qb = new QueryBuilder("delete from ZDUserLog");
		if (qb.executeNoQuery() > 0) {
			Response.setLogInfo(1, "��־��ճɹ�");
		} else {
			Response.setLogInfo(0, "����ʧ��");
		}
	}
	
	public void delByTime(){
		int month = Integer.parseInt($V("month"));
		long timeNow = new Date().getTime();
		long time = timeNow-((long)1000*60*60*24*30*month);
		Date date = new Date(time); 
		String sql = "delete from zdUserlog where addtime < ?";
		QueryBuilder qb = new QueryBuilder(sql,date);
		if(qb.executeNoQuery()>=0){
			Response.setLogInfo(1, "ɾ���ɹ�");
		}else{
			Response.setLogInfo(0, "����ʧ��");
		}

	}

	public static boolean log(String logType, String subType, String logMessage, String ip) {
		return log(logType, subType, logMessage, ip, User.getUserName(), null);
	}

	public static boolean log(String logType, String subType, String logMessage, String ip, Transaction trans) {
		return log(logType, subType, logMessage, ip, User.getUserName(), trans);
	}

	public static boolean log(String logType, String subType, String logMessage, String ip, String userName) {
		return log(logType, subType, logMessage, ip, userName, null);
	}

	/**
	 * @param trans
	 *            ��ǰ�����û���־���������
	 * @param logType
	 *            ��Ϣ����
	 * @param logMessage
	 *            ��Ϣ����
	 * @param ip
	 *            �û�IP
	 */
	public static boolean log(String logType, String subType, String logMessage, String ip, String userName,
			Transaction trans) {
		ZDUserLogSchema userlog = new ZDUserLogSchema();
		userlog.setUserName(userName);
		userlog.setIP(ip);
		userlog.setAddTime(new Date());
		userlog.setLogID(NoUtil.getMaxID("LogID"));
		userlog.setLogType(logType);
		userlog.setSubType(subType);
		userlog.setLogMessage(logMessage);
		if (trans == null) {
			return userlog.insert();
		} else {
			trans.add(userlog, Transaction.INSERT);
		}
		return true;
	}

	public static DataTable getSubType(Mapx params) {
		String logType = params.getString("LogType");
		if (StringUtil.isEmpty(logType) || "0".equals(logType)) {
			return null;
		}
		Mapx map = (Mapx) USERLOG_MAP.get(logType);
		return map.toDataTable();
	}
}
