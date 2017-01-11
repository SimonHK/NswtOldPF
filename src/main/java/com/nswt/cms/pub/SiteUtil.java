package com.nswt.cms.pub;

import com.nswt.cms.site.CatalogConfig;
import com.nswt.framework.Config;
import com.nswt.framework.cache.CacheManager;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.utility.StringUtil;
import com.nswt.schema.ZCCatalogConfigSchema;
import com.nswt.schema.ZCCatalogConfigSet;
import com.nswt.schema.ZCSiteSchema;
import com.nswt.schema.ZCTagSchema;

/**
 * �������վ��������Ϣ
 * 
 * @Author ��ά��
 * @Date 2016-1-16
 * @Mail wwj@nswt.com
 */

public class SiteUtil {

	static {
		CatalogConfig.initCatalogConfig();
	}

	/**
	 * ��ȡվ�����·��
	 */
	public static String getPath(long siteID) {
		return getPath(siteID + "");
	}

	/**
	 * ��ȡվ�����·��
	 */
	public static String getPath(String siteID) {
		String path = Config.getContextPath() + Config.getValue("Statical.TargetDir") + "/" + getAlias(siteID) + "/";
		return path.replaceAll("/+", "/");
	}

	/**
	 * ��ȡվ�����·��
	 */
	public static String getAbsolutePath(long siteID) {
		return getAbsolutePath(siteID + "");
	}

	/**
	 * ��ȡվ�����·��
	 */
	public static String getAbsolutePath(String siteID) {
		String path = Config.getContextRealPath() + Config.getValue("Statical.TargetDir") + "/" + getAlias(siteID)
				+ "/";
		return path.replaceAll("/+", "/");
	}

	/**
	 * ��ȡӦ������
	 */
	public static String getName(long siteID) {
		return getName(siteID + "");
	}

	/**
	 * ��ȡӦ������
	 */
	public static String getName(String siteID) {
		if (StringUtil.isEmpty(siteID)) {
			return null;
		}
		ZCSiteSchema site = getSchema(siteID);
		if (site == null) {
			return null;
		}
		return site.getName();
	}

	public static synchronized ZCSiteSchema getSchema(String siteID) {
		return CMSCache.getSite(siteID);
	}

	public static synchronized ZCSiteSchema getSchema(long siteID) {
		return CMSCache.getSite(siteID + "");
	}

	/**
	 * ��ȡվ�����
	 */
	public static String getAlias(long siteID) {
		return getAlias(siteID + "");
	}

	/**
	 * ��ȡվ�����
	 */
	public static String getAlias(String siteID) {
		if (StringUtil.isEmpty(siteID)) {
			return null;
		}
		ZCSiteSchema site = getSchema(siteID);
		if (site == null) {
			return null;
		}
		return site.getAlias();
	}

	/**
	 * ��ȡվ�����
	 */
	public static String getCode(long siteID) {
		return getAlias(siteID + "");
	}

	/**
	 * ��ȡվ�����
	 */
	public static String getCode(String siteID) {
		return getAlias(siteID + "");
	}

	/**
	 * ��ȡ��վ���ӵ�ַ
	 */
	public static String getURL(long siteID) {
		return getURL(siteID + "");
	}

	/**
	 * ��ȡ��վ���ӵ�ַ
	 */
	public static String getURL(String siteID) {
		ZCSiteSchema site = getSchema(siteID);
		if (site == null) {
			return "";
		}
		String url = site.getURL();
		if (StringUtil.isEmpty(url)) {
			return "";
		}
		// ���ʹ�õ�Ĭ�ϵ�http://������ҲΪ��
		if ("http://".equals(url)) {
			return "";
		}
		if (!url.startsWith("http://")) {
			url = "http://" + url;
		}
		return url;
	}

	/**
	 * ��ȡ��վ����Ŀ�鵵����
	 */
	public static String getArchiveTime(long siteID) {
		return getArchiveTime(siteID + "");
	}

	/**
	 * ��ȡ��վ����Ŀ�鵵����
	 */
	public synchronized static String getArchiveTime(String siteID) {
		return CMSCache.getCatalogConfig(siteID + ",0").getArchiveTime();
	}

	/**
	 * ��ȡ��վ������������
	 */
	public static String getAttachDownFlag(long siteID) {
		return getAttachDownFlag(siteID + "");
	}

	/**
	 * ��ȡ��վ������������
	 */
	public synchronized static String getAttachDownFlag(String siteID) {
		return CMSCache.getCatalogConfig(siteID + ",0").getAttachDownFlag();
	}

	public static void update(long siteID) {
		update(siteID + "");
	}

	/**
	 * ��ȡվ�������Ƿ���Ҫ���
	 */
	public synchronized static boolean getCommentAuditFlag(String siteID) {
		ZCSiteSchema site = getSchema(siteID);
		if (site == null) {
			return false;
		}
		String commentAuditFlag = site.getCommentAuditFlag();
		return "Y".equalsIgnoreCase(commentAuditFlag);
	}

	public static boolean getCommentAuditFlag(long siteID) {
		return getCommentAuditFlag(siteID + "");
	}

	public synchronized static void update(String siteID) {
		ZCSiteSchema site = new ZCSiteSchema();
		site.setID(siteID);
		if (site.fill()) {
			// ����վ�㷢�����������Ϣ
			ZCCatalogConfigSchema config = new ZCCatalogConfigSchema();
			ZCCatalogConfigSet configSet = config.query(new QueryBuilder(" where CatalogID=0 and SiteID=?", Long
					.parseLong(siteID)));
			if (configSet != null && configSet.size() > 0) {
				config = configSet.get(0);
				CacheManager.set("CMS", "Config", siteID + ",0", config);
			}
			CacheManager.set("CMS", "Site", siteID, site);
		}
	}

	public static boolean isBBSEnable(String siteID) {
		ZCSiteSchema site = getSchema(siteID);
		if (site == null) {
			return false;
		}
		String flag = site.getBBSEnableFlag();
		return "Y".equalsIgnoreCase(flag);
	}

	public static boolean isShopEnable(String siteID) {
		ZCSiteSchema site = getSchema(siteID);
		if (site == null) {
			return false;
		}
		String flag = site.getShopEnableFlag();
		return "Y".equalsIgnoreCase(flag);
	}

	public static ZCTagSchema getTag(long siteID, String tag) {
		return CMSCache.getTag(siteID, tag);
	}
	
	public static String getExtensionType(String siteID){
		String ext = "shtml";
		ZCSiteSchema site =  getSchema(siteID);
		if(site != null){
			ext = site.getProp1();
			if(StringUtil.isEmpty(ext)){
				ext = "shtml";
			}
		}
		
		return ext;
	}
	
	public static String getExtensionType(long siteID){
		return getExtensionType(siteID+"");
	}
}
