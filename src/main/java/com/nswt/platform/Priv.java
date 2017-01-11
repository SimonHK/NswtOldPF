package com.nswt.platform;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.nswt.cms.pub.PubFun;
import com.nswt.framework.Config;
import com.nswt.framework.User;
import com.nswt.framework.data.DataRow;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;

/**
 * ����Ȩ���ࣺ�û���Ȩ�޳�פ�ڴ�
 * 
 * @author huanglei
 * @mail huanglei@nswt.com
 * @date 2007-11-21
 */

public class Priv {
	// �˵�Ȩ��
	public final static String MENU = "menu";

	public final static String MENU_BROWSE = "menu_browse";

	public final static Mapx MENU_MAP = new Mapx();

	static {
		MENU_MAP.put(MENU_BROWSE, "�˵����");
	}

	// վ��Ȩ��
	public final static String SITE = "site";

	public final static String SITE_BROWSE = "site_browse";

	public final static String SITE_MANAGE = "site_manage";

	public final static Mapx SITE_MAP = new Mapx();

	static {
		SITE_MAP.put(SITE_BROWSE, "վ�����");
		SITE_MAP.put(SITE_MANAGE, "Ӧ�ù���");
	}

	// ����Ȩ��
	public final static String ARTICLE = "article";

	public final static String ARTICLE_BROWSE = "article_browse";

	public final static String ARTICLE_MANAGE = "article_manage";

	public final static String ARTICLE_MODIFY = "article_modify";
	
	public final static String ARTICLE_AUDIT = "article_audit";

	public final static Mapx ARTICLE_MAP = new Mapx();

	static {
		ARTICLE_MAP.put(ARTICLE_BROWSE, "������Ŀ���");
		ARTICLE_MAP.put(ARTICLE_MANAGE, "������Ŀ����");
		ARTICLE_MAP.put(ARTICLE_MODIFY, "���¹���");
		ARTICLE_MAP.put(ARTICLE_AUDIT, "�������");
	}
	
//	 ͼƬȨ��
	public final static String IMAGE = "image";

	public final static String IMAGE_BROWSE = "image_browse";

	public final static String IMAGE_MANAGE = "image_manage";

	public final static String IMAGE_MODIFY = "image_modify";

	public final static Mapx IMAGE_MAP = new Mapx();

	static {
		IMAGE_MAP.put(IMAGE_BROWSE, "ͼƬ��Ŀ���");
		IMAGE_MAP.put(IMAGE_MANAGE, "ͼƬ��Ŀ����");
		IMAGE_MAP.put(IMAGE_MODIFY, "ͼƬ����");
	}
	
//	 ��ƵȨ��
	public final static String VIDEO = "video";

	public final static String VIDEO_BROWSE = "video_browse";

	public final static String VIDEO_MANAGE = "video_manage";

	public final static String VIDEO_MODIFY = "video_modify";

	public final static Mapx VIDEO_MAP = new Mapx();

	static {
		VIDEO_MAP.put(VIDEO_BROWSE, "��Ƶ��Ŀ���");
		VIDEO_MAP.put(VIDEO_MANAGE, "��Ƶ��Ŀ����");
		VIDEO_MAP.put(VIDEO_MODIFY, "��Ƶ����");
	}
	
//	 ��ƵȨ��
	public final static String AUDIO = "audio";

	public final static String AUDIO_BROWSE = "audio_browse";

	public final static String AUDIO_MANAGE = "audio_manage";

	public final static String AUDIO_MODIFY = "audio_modify";

	public final static Mapx AUDIO_MAP = new Mapx();

	static {
		AUDIO_MAP.put(AUDIO_BROWSE, "��Ƶ��Ŀ���");
		AUDIO_MAP.put(AUDIO_MANAGE, "��Ƶ��Ŀ����");
		AUDIO_MAP.put(AUDIO_MODIFY, "��Ƶ����");
	}
//	 ����Ȩ��
	public final static String ATTACH = "attach";

	public final static String ATTACH_BROWSE = "attach_browse";

	public final static String ATTACH_MANAGE = "attach_manage";

	public final static String ATTACH_MODIFY = "attach_modify";

	public final static Mapx ATTACH_MAP = new Mapx();

	static {
		ATTACH_MAP.put(ATTACH_BROWSE, "������Ŀ���");
		ATTACH_MAP.put(ATTACH_MANAGE, "������Ŀ����");
		ATTACH_MAP.put(ATTACH_MODIFY, "��������");
	}
	
	// ����Ȩ�޷��࣬�����Ȩ�޷���ע�ᵽPRIV_MAP
	public final static Mapx PRIV_MAP = new Mapx();

	static {
		PRIV_MAP.put(MENU, MENU_MAP);
		PRIV_MAP.put(SITE, SITE_MAP);
		PRIV_MAP.put(ARTICLE, ARTICLE_MAP);
		PRIV_MAP.put(IMAGE, IMAGE_MAP);
		PRIV_MAP.put(VIDEO, VIDEO_MAP);
		PRIV_MAP.put(AUDIO, AUDIO_MAP);
		PRIV_MAP.put(ATTACH, ATTACH_MAP);
	}
	
	/**
	 * Ȩ�����������-�û�
	 */
	public static final String OWNERTYPE_USER = "U";

	private static Map UserPrivMap = new Hashtable();

	public static void updateAllPriv(String UserName) {
		Object obj = new QueryBuilder("select UserName from ZDUser where UserName=?", UserName).executeOneValue();
		if (obj == null) {
			UserPrivMap.remove(UserName);
			return;
		}
		Object[] ks = PRIV_MAP.keyArray();
		for (int i = 0; i < PRIV_MAP.size(); i++) {
			updatePriv(UserName, (String) ks[i]);
		}
	}

	public static void updatePriv(String UserName, String PrivType) {
		String sql = "select ID,Code,Value from ZDPrivilege where OwnerType=? and Owner=? and PrivType=?";
		QueryBuilder qb = new QueryBuilder(sql);
		qb.add(Priv.OWNERTYPE_USER);
		qb.add(UserName);
		qb.add(PrivType);
		DataTable dt = qb.executeDataTable();
		Map PrivTypeMap = getPrivTypeMap(UserName, PrivType);
		RolePriv.getMapFromDataTable((Map) PrivTypeMap, dt);
	}

	private static Map getPrivTypeMap(String UserName, String PrivType) {
		Map UserNamePrivMap = (Map) UserPrivMap.get(UserName); // һ���û��µ�����Ȩ��
		if (UserNamePrivMap == null) {
			UserNamePrivMap = new Hashtable();
			UserPrivMap.put(UserName, UserNamePrivMap);
			updateAllPriv(UserName);
		}
		Map PrivTypeMap = (Map) UserNamePrivMap.get(PrivType);
		if (PrivTypeMap == null) {
			PrivTypeMap = new HashMap();
			UserNamePrivMap.put(PrivType, PrivTypeMap);
		}
		return PrivTypeMap;
	}

	public static boolean getPriv(String PrivType, String ID, String Code) {
		return getPriv(User.getUserName(), PrivType, ID, Code);
	}

	public static boolean getPriv(String UserName, String PrivType, String ID, String Code) {
		if (UserList.ADMINISTRATOR.equalsIgnoreCase(UserName)) {
			return true;
		}
		List roleCodeList = PubFun.getRoleCodesByUserName(UserName);
		if (roleCodeList != null && roleCodeList.size() != 0) {
			if(roleCodeList.contains("admin")){
				return true;
			}
		}
		String value = getUserPriv(UserName, PrivType, ID, Code);
		if ("1".equals(value)) {
			return true;
		} else if ("-1".equals(value)) {
			return false;
		} else {
//			List roleCodeList = PubFun.getRoleCodesByUserName(UserName);
			if (roleCodeList != null && roleCodeList.size() != 0) {
				return RolePriv.getRolePriv((String[]) roleCodeList.toArray(new String[roleCodeList.size()]), PrivType, ID, Code);
			} else {
				return false;
			}
		}
	}
	
	public static boolean getBrowsePriv(String UserName,String ID) {
		return getPriv(UserName, Priv.ARTICLE, ID, Priv.ARTICLE_BROWSE);
	}
	
	/**
	 * �����û���ĳ��Ȩ�ޣ����û�ӵ�е�����Ȩ��ʱֱ�ӷ���0��1�����û�е�����Ȩ�ޣ��Ǵӽ�ɫ�̳еĻ��ͷ���null��������Ҫ���Ų��ҽ�ɫ��Ȩ��
	 * 
	 * @param PrivType
	 * @param ID
	 * @param Code
	 * @return
	 */
	private static String getUserPriv(String UserName, String PrivType, String ID, String Code) {
		if (MENU.equals(PrivType)) {
			Map map = Priv.getPrivTypeMap(UserName, PrivType);
			map = (Map) map.get(ID);
			if (map != null) {
				return (String) map.get(Code);
			} else {
				return null;
			}
		} else if (SITE.equals(PrivType)) {
			Map map = Priv.getPrivTypeMap(UserName, PrivType);
			map = (Map) map.get(ID);
			if (map != null) {
				return (String) map.get(Code);
			} else {
				return null;
			}
		} else {
			Map map = Priv.getPrivTypeMap(UserName, PrivType);
			map = (Map) map.get(ID);
			if (map != null) {
				return (String) map.get(Code);
			}
			return null;
		}
	}
	
	public static DataTable getCatalogPrivDT(String userName, String siteID, String PrivType) {
		return getCatalogPrivDT(userName, siteID, PrivType, false);
	}
	
	public static DataTable getCatalogPrivDT(String userName, String siteID, String PrivType, boolean isWebMode) {
		StringBuffer sb = new StringBuffer();
		sb.append(",'" + userName + "' as UserName");
		Object[] ks = ((Mapx) Priv.PRIV_MAP.get(PrivType)).keyArray();
		for (int i = 0; i < ((Mapx) Priv.PRIV_MAP.get(PrivType)).size(); i++) {
			sb.append(",'' as " + ks[i]);
		}

		String sql = "select ID,Name,0 as TreeLevel ,'" + Priv.SITE + "' as PrivType"
				+ sb.toString().replaceAll("''", "''") + ",'' as ParentInnerCode from ZCSite a where a.ID = ?";
		DataTable siteDT = new QueryBuilder(sql, Long.parseLong(siteID)).executeDataTable();
		if(siteDT.getRowCount() == 0){
			return new DataTable();
		}

		// ��InnerCodeʹ�ò����ϼ�Ȩ�޵�ʱ��ǳ�����
		String catalogType = RoleTabCatalog.CatalogTypeMap.getString(PrivType);
		sql = "select InnerCode as ID,Name,TreeLevel ,'" + PrivType + "' as PrivType" + sb.toString()
				+ ", (select b.InnerCode from ZCCatalog b where a.parentid=b.id) as ParentInnerCode from ZCCatalog a where Type =" + catalogType + " and a.SiteID = ? order by orderflag,innercode ";
		DataTable catalogDT = new QueryBuilder(sql, Long.parseLong(siteID)).executeDataTable();

		DataRow dr = null;
		String value = "1";
		if(isWebMode){
			value = "��";
		}
		for (int i = 0; i < siteDT.getRowCount(); i++) {
			dr = siteDT.getDataRow(i);
			for (int j = 0; j < dr.getColumnCount(); j++) {
				String columnName = dr.getDataColumn(j).getColumnName().toLowerCase();
				if (columnName.indexOf("_") > 0) {
					dr.set(j, Priv.getPriv(userName, Priv.SITE, dr.getString("ID"), columnName) ? value : "");
				}
			}
		}
		for (int i = 0; i < catalogDT.getRowCount(); i++) {
			dr = catalogDT.getDataRow(i);
			for (int j = 0; j < dr.getColumnCount(); j++) {
				String columnName = dr.getDataColumn(j).getColumnName().toLowerCase();
				if (columnName.indexOf("_") > 0) {
					dr.set(j, Priv.getPriv(userName, PrivType, dr.getString("ID"), columnName) ? value : "");
				}
			}
		}
		catalogDT.insertRow(siteDT.getDataRow(0), 0);
		return catalogDT;
	}
	
	public static DataTable getSitePrivDT(String userName, String siteID, String PrivType){
		String s = "";
		StringBuffer sb = new StringBuffer();
		sb.append(",'" + userName + "' as UserName");
		Object[] ks = Priv.SITE_MAP.keyArray();
		for (int i = 0; i < Priv.SITE_MAP.size(); i++) {
			sb.append(",'" + s + "' as " + ks[i].toString());
		}
		String sql = "select ID,Name,0 as TreeLevel ,'" + Priv.SITE + "' as PrivType " + sb.toString()
				+ " from ZCSite a where id = ? order by orderflag ,id";
		DataTable siteDT = new QueryBuilder(sql,Long.parseLong(siteID)).executeDataTable();
		DataRow dr = null;
		for (int i = 0; i < siteDT.getRowCount(); i++) {
			dr = siteDT.getDataRow(i);
			for (int j = 0; j < dr.getColumnCount(); j++) {
				String columnName = dr.getDataColumn(j).getColumnName().toLowerCase().toLowerCase();
				if (columnName.indexOf("_") > 0) {
					dr.set(j, Priv.getPriv(userName, PrivType, dr.getString("ID"), columnName) ? "1" : "");
				}
			}
		}
		return siteDT;
	}

	public static boolean isValidURL(String URL) {
		if (StringUtil.isNotEmpty(URL)) {
			URL = URL.replaceAll("/+", "/");
			if (URL.startsWith("/")) {
				URL = URL.substring(1);
			}
		}
		if(!Config.isInstalled){
			return true;
		}
		String menuID = Menu.MenuCacheMap.getString(URL);
		if (StringUtil.isNotEmpty(menuID) && !Priv.getPriv(User.getUserName(), Priv.MENU, Application.getCurrentSiteID() +"-"+menuID, Priv.MENU_BROWSE)) {
			return false;
		}
		return true;
	}
}
