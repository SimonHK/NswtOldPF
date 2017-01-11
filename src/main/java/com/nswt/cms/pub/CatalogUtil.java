package com.nswt.cms.pub;

import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.nswt.cms.dataservice.ColumnUtil;
import com.nswt.cms.site.Catalog;
import com.nswt.cms.site.CatalogConfig;
import com.nswt.cms.template.HtmlNameParser;
import com.nswt.cms.template.HtmlNameRule;
import com.nswt.framework.User;
import com.nswt.framework.cache.CacheManager;
import com.nswt.framework.data.DataRow;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.utility.DateUtil;
import com.nswt.framework.utility.ServletUtil;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.Application;
import com.nswt.platform.Priv;
import com.nswt.platform.pub.NoUtil;
import com.nswt.schema.ZCCatalogConfigSchema;
import com.nswt.schema.ZCCatalogConfigSet;
import com.nswt.schema.ZCCatalogSchema;
import com.nswt.schema.ZCSiteSchema;

/**
 * ���������Ŀ�������Ϣ
 * 
 * @Author ��ά��
 * @Date 2016-1-15
 * @Mail wwj@nswt.com
 */

public class CatalogUtil {

	static {
		CatalogConfig.initCatalogConfig();
	}

	/**
	 * ����IDȡSchema
	 */
	public static ZCCatalogSchema getSchema(String catalogID) {
		return CMSCache.getCatalog(catalogID);
	}

	public static ZCCatalogSchema getSchema(long catalogID) {
		return getSchema("" + catalogID);
	}

	/**
	 * ����IDȡ��Ŀ����
	 */
	public static String getName(String catalogID) {
		ZCCatalogSchema catalog = CMSCache.getCatalog(catalogID);
		if (catalog == null) {
			return null;
		}
		return catalog.getName();
	}

	/**
	 * ����IDȡ��Ŀ����
	 */
	public static String getName(long catalogID) {
		return getName(catalogID + "");
	}
	
	public static String getFullName(String catalogID,String separete) {
		StringBuffer sb = new StringBuffer();
		ZCCatalogSchema catalog = CMSCache.getCatalog(catalogID);
		if (catalog == null) {
			return null;
		}
		
		if(catalog.getParentID() != 0){
			sb.append(getFullName(catalog.getParentID()+"",separete));
		}
		if(sb.toString().length()>0){
			sb.append(separete);
		}
		
		sb.append(catalog.getName());
		
		return sb.toString();
	}
	
	public static String getFullName(long catalogID,String separete){
		return getFullName(catalogID+"",separete);
	}
	
	public static String getFullName(long catalogID){
		return getFullName(catalogID+"","/");
	}

	/**
	 * ����InnerCodeȡ��Ŀ����
	 */
	public static String getNameByInnerCode(String catalogInnerCode) {
		ZCCatalogSchema catalog = CMSCache.getCatalogByInnerCode(catalogInnerCode);
		if (catalog == null) {
			return null;
		}
		return catalog.getName();
	}

	/**
	 * ȡ���� "���Ŷ�̬/��������/�ж�����"�� �ж����� ��Ŀ��id
	 */
	public static String getIDByNames(String names) {
		return getIDByNames(Application.getCurrentSiteID(), names);
	}

	/**
	 * ȡ���� "���Ŷ�̬/��������/�ж�����"�� �ж����� ��Ŀ��id
	 */
	public static String getIDByNames(long siteID, String names) {
		return getIDByNames(siteID + "", names);
	}

	/**
	 * ȡ���� "���Ŷ�̬/��������/�ж�����"�� �ж����� ��Ŀ��id
	 */
	public static String getIDByNames(String siteID, String names) {
		if (StringUtil.isEmpty(names)) {
			return null;
		}
		if (names.startsWith("/")) {
			names = names.substring(1);
		}
		if (names.endsWith("/")) {
			names = names.substring(0, names.length() - 1);
		}
		String[] catalogNames = names.split("/");
		int catalogLenth = catalogNames.length;
		String id = "";
		if (catalogLenth > 0) {
			if (catalogLenth > 1) {
				String catalogStr = StringUtil.join(catalogNames, "_");
				id = getCatalogIDByNames(siteID, catalogStr);
			} else {
				id = getCatalogIDByNames(siteID, catalogNames[0]);
			}
		}
		return id;
	}

	private static String getCatalogIDByNames(String siteID, String names) {
		if (StringUtil.isEmpty(names)) {
			return null;
		}
		if (names.startsWith("_")) {
			names = names.substring(1);
		}
		if (names.endsWith("_")) {
			names = names.substring(0, names.length() - 1);
		}
		long catalogID = 0;
		String[] catalogNames = names.split("_");
		if (catalogNames.length <= 0) {
			return null;
		} else if (catalogNames.length == 1) {
			ZCCatalogSchema catalog = null;
			if (StringUtil.isDigit(catalogNames[0])) {
				catalog = CMSCache.getCatalog(Long.parseLong(catalogNames[0]));
			}
			
			if (catalog == null) {
				catalog = CMSCache.getCatalog(siteID, catalogNames[0]);
			}
			
			if (catalog == null) {
				return null;
			}
			catalogID = catalog.getID();
		} else if (catalogNames.length > 1) {
			for (int i = 0; i < catalogNames.length; i++) {
				if (i == 0) {
					ZCCatalogSchema catalog = null;
					if (StringUtil.isDigit(catalogNames[i])) {
						catalog = CMSCache.getCatalog(Long.parseLong(catalogNames[i]));
					}
					
					if (catalog == null) {
						catalog = CMSCache.getCatalog(siteID, catalogNames[i]);
					}
					
					if (catalog == null) {
						return null;
					}
					catalogID = catalog.getID();
				} else {
					ZCCatalogSchema catalog = null;
					if (StringUtil.isDigit(catalogNames[i])) {
						catalog = CMSCache.getCatalog(Long.parseLong(catalogNames[i]));
					}
					
					if (catalog == null) {
						catalog = CMSCache.getCatalog(siteID, catalogID, catalogNames[i]);
					}
					
					if (catalog == null) {
						return null;
					}
					catalogID = catalog.getID();
				}
			}
		}
		return String.valueOf(catalogID);
	}

	/**
	 * ȡ���� "��������,��������"����Ŀ��id����,��������ID
	 */
	public static String getIDsByName(String names) {
		return getIDsByName(Application.getCurrentSiteID(), names);
	}

	/**
	 * ȡ���� "��������,��������"����Ŀ��id����,��������ID
	 */
	public static String getIDsByName(long siteID, String names) {
		return getIDsByName(siteID + "", names);
	}

	/**
	 * ȡ���� "��������,��������"����Ŀ��id����,��������ID
	 */
	public static String getIDsByName(String siteID, String names) {
		if (StringUtil.isEmpty(names)) {
			return null;
		}
		if (names.startsWith(",")) {
			names = names.substring(1);
		}
		if (names.endsWith(",")) {
			names = names.substring(0, names.length() - 1);
		}
		String[] catalogNames = names.split(",");
		int catalogLenth = catalogNames.length;
		StringBuffer sb = new StringBuffer(40);
		if (catalogLenth > 0) {
			for (int i = 0; i < catalogLenth; i++) {
				String catalogID = getIDByNames(siteID, catalogNames[i]);
				if(StringUtil.isEmpty(catalogID)){
					continue;
				}
				if (i != 0) {
					sb.append(",");
				}
				sb.append(catalogID);
			}
		}
		return sb.toString();
	}

	/**
	 * ȡ���� "������̬"����Ŀ��id
	 */
	public static String getIDByName(String catalogName) {
		return getIDByName(Application.getCurrentSiteID(), catalogName);
	}

	/**
	 * ȡ���� "������̬"����Ŀ��id
	 */
	public static String getIDByName(long siteID, String catalogName) {
		return getIDByName(siteID + "", catalogName);
	}

	/**
	 * ȡ���� "������̬"����Ŀ��id
	 */
	public static String getIDByName(String siteID, String catalogName) {
		ZCCatalogSchema catalog = null;
		if (StringUtil.isDigit(catalogName)) {
			catalog = CMSCache.getCatalog(Long.parseLong(catalogName));
		}
		if (catalog == null) {
			catalog = CMSCache.getCatalog(siteID, catalogName);
		}
		if (catalog == null) {
			return null;
		}
		return String.valueOf(catalog.getID());
	}

	/**
	 * ȡ��Ŀid
	 */
	public static String getIDByName(long siteID, long parentID, String catalogName) {
		return getIDByName(siteID + "", parentID + "", catalogName);
	}

	/**
	 * ȡ��Ŀid
	 */
	public static String getIDByName(long siteID, String parentID, String catalogName) {
		return getIDByName(siteID + "", parentID, catalogName);
	}

	/**
	 * ȡ��Ŀid
	 */
	public static String getIDByName(String siteID, long parentID, String catalogName) {
		return getIDByName(siteID, parentID + "", catalogName);
	}

	/**
	 * ȡ��Ŀid
	 */
	public static String getIDByName(String siteID, String parentID, String catalogName) {
		ZCCatalogSchema catalog = null;
		if (StringUtil.isDigit(catalogName)) {
			catalog = CMSCache.getCatalog(Long.parseLong(catalogName));
		}
		if (catalog == null) {
			catalog = CMSCache.getCatalog(siteID, Long.parseLong(parentID), catalogName);
		}
		if (catalog == null) {
			return null;
		}
		return String.valueOf(catalog.getID());
	}

	/**
	 * ������Ŀ��InnerCodeȡ����Ŀ��id
	 */

	public static String getIDByInnerCode(String catalogInnerCode) {
		ZCCatalogSchema catalog = CMSCache.getCatalogByInnerCode(catalogInnerCode);
		if (catalog != null) {
			return "" + catalog.getID();
		}
		return null;
	}

	/**
	 * ��ȡ��Ŀ·��
	 */
	public static String getPath(long catalogID) {
		return getPath(catalogID + "");
	}

	/**
	 * ����CatalogIDȡ����Ŀ�����·������·���ɱ�����ɻ�ʹ���Զ���·��
	 */
	public static String getPath(String catalogID) {
		ZCCatalogSchema catalog = CMSCache.getCatalog(catalogID);
		ZCSiteSchema site = SiteUtil.getSchema(catalog.getSiteID());
		String fullPath = "";
		String url = catalog.getURL();
		if(url != null){
			url = url.trim();
		}
		if(StringUtil.isNotEmpty(url)){
			if(url.startsWith("http://") || url.startsWith("https://")){
				fullPath = getFullPath(catalogID);
			}else if(url.startsWith("#")){//֧�֡�#�� 
				fullPath = getFullPath(catalogID);
			}else if(url.startsWith("/")){
				fullPath = getFullPath(catalogID);
			}else{
				HtmlNameParser h = new HtmlNameParser(site.toDataRow(),catalog.toDataRow(),null,url);
				HtmlNameRule rule = h.getNameRule();
				fullPath = rule.getFullPath()+"/";
				fullPath = fullPath.replaceAll("/+", "/");
			}
		}else{
			fullPath = getFullPath(catalogID);
		}
		
		return fullPath;
	}
	
	public static String getLink(long catalogID,String levelStr) {
		return getLink(catalogID + "",levelStr);
	}
	
	/**
	 * ȡ��Ŀ������·��������ǰλ�á���Ŀ��תʹ��
	 * @param catalogID
	 * @return
	 */
	public static String getLink(String catalogID,String levelStr) {
		ZCCatalogSchema catalog = CMSCache.getCatalog(catalogID);
		ZCSiteSchema site = SiteUtil.getSchema(catalog.getSiteID());
		String ext = ServletUtil.getUrlExtension(CatalogUtil.getSchema(catalogID).getListTemplate());
		String siteExt = SiteUtil.getExtensionType(catalog.getSiteID());
		String indexPage = "";
		if (ext.equals(".jsp") || siteExt.equals("jsp")) {
			indexPage += "index.jsp";
		} else {
			indexPage += "index.shtml";
		}
		String linkUrl = "";
		String url = catalog.getURL();
		if(StringUtil.isNotEmpty(url)){
			if(url.startsWith("http://") || url.startsWith("https://")){//�ⲿ����
				linkUrl = url;
			}else if(url.startsWith("#") || url.startsWith("/")){//֧�֡�#�� �ʹӸ�Ŀ¼��ʼ��·��
				linkUrl = url;
			}else {
				HtmlNameParser h = new HtmlNameParser(site.toDataRow(),catalog.toDataRow(),null,url);
				HtmlNameRule rule = h.getNameRule();
				linkUrl = levelStr + rule.getFullPath() +"/"+indexPage;
				linkUrl = linkUrl.replaceAll("/+", "/");
			}
		}else{
			linkUrl = levelStr + getFullPath(catalogID) + indexPage;
		}
		
		return linkUrl;
	}

	/**
	 * ����CatalogIDȡ����Ŀ���·��,��·������Ŀ��ID���
	 */
	public static String getCatalogIDPath(long catalogID) {
		return getCatalogIDPath(catalogID);
	}

	/**
	 * ����CatalogIDȡ����Ŀ���·��,��·������Ŀ��ID���
	 */
	public static String getCatalogIDPath(String catalogID) {
		return getIDPath(catalogID);
	}

	/**
	 * ȡ��ĿInnercode
	 */
	public static String getInnerCode(long catalogID) {
		return getInnerCode(catalogID + "");
	}

	/**
	 * ȡ��ĿInnerCode
	 */
	public static String getInnerCode(String catalogID) {
		if (StringUtil.isEmpty(catalogID) || catalogID.equals("0")) {
			return "";
		}
		ZCCatalogSchema catalog = CMSCache.getCatalog(catalogID);
		if (catalog != null) {
			return catalog.getInnerCode();
		}
		return null;
	}

	/**
	 * ȡ��ĿcatalogType
	 */
	public static long getCatalogType(long catalogID) {
		return getCatalogType(catalogID + "");
	}

	/**
	 * ȡ��ĿcatalogType
	 */
	public static long getCatalogType(String catalogID) {
		if (StringUtil.isEmpty(catalogID) || catalogID.equals("0")) {
			return 0;
		}
		ZCCatalogSchema catalog = CMSCache.getCatalog(catalogID);
		if (catalog != null) {
			return catalog.getType();
		}
		return 0;

	}

	/**
	 * ȡ��Ŀ����վ��siteID
	 */
	public static String getSiteID(long catalogID) {
		return getSiteID(catalogID + "");
	}

	/**
	 * ȡ��Ŀ����վ��siteID
	 */
	public static String getSiteID(String catalogID) {
		if (StringUtil.isEmpty(catalogID) || catalogID.equals("0")) {
			return "";
		}
		ZCCatalogSchema catalog = CMSCache.getCatalog(catalogID);
		if (catalog != null) {
			return "" + catalog.getSiteID();
		}
		return null;
	}

	/**
	 * ȡ��Ŀ����վ��siteID
	 */
	public static String getSiteIDByInnerCode(String catalogInnerCode) {
		ZCCatalogSchema catalog = CMSCache.getCatalogByInnerCode(catalogInnerCode);
		if (catalog != null) {
			return "" + catalog.getSiteID();
		}
		return null;
	}

	/**
	 * ��ȡ��Ŀ����·��
	 */
	public static String getAbsolutePath(long catalogID) {
		return getAbsolutePath(catalogID + "");
	}

	/**
	 * ��ȡ��Ŀ����·��
	 */
	public static String getAbsolutePath(String catalogID) {
		return SiteUtil.getAbsolutePath(getSiteID(catalogID)) + getPath(catalogID);
	}

	/**
	 * ��ȡ��id����ʽ���ɵ���Ŀ����·��
	 */
	public static String getAbsoluteIDPath(long catalogID) {
		return getAbsoluteIDPath(catalogID + "");
	}

	/**
	 * ��ȡ��id����ʽ���ɵ���Ŀ����·��
	 */
	public static String getAbsoluteIDPath(String catalogID) {
		return SiteUtil.getAbsolutePath(getSiteID(catalogID)) + getCatalogIDPath(catalogID);
	}

	/**
	 * ��ȡ��Ŀ����
	 */
	public static String getAlias(long catalogID) {
		return getAlias(catalogID + "");
	}

	/**
	 * ��ȡ��Ŀ����
	 */
	public static String getAlias(String catalogID) {
		if (StringUtil.isEmpty(catalogID) || catalogID.equals("0")) {
			return "";
		}
		ZCCatalogSchema catalog = CMSCache.getCatalog(catalogID);
		if (catalog != null) {
			return catalog.getAlias().toLowerCase();
		}
		return null;
	}

	/**
	 * ��ȡ��Ʒ���
	 */
	public static String getGoodsTypeID(long catalogID) {
		return getGoodsTypeID(catalogID + "");
	}

	/**
	 * ��ȡ��Ʒ���,����ZCCatalog��Prop4�ֶ�
	 */
	public static String getGoodsTypeID(String catalogID) {
		if (StringUtil.isEmpty(catalogID) || catalogID.equals("0")) {
			return "";
		}
		ZCCatalogSchema catalog = CMSCache.getCatalog(catalogID);
		if (catalog != null) {
			return catalog.getProp4();
		}
		return null;
	}

	/**
	 * ��ȡ�ϼ���ĿID
	 */
	public static String getParentID(long catalogID) {
		return getParentID(catalogID + "");
	}

	/**
	 * ��ȡ�ϼ���ĿID
	 */
	public static String getParentID(String catalogID) {
		if (StringUtil.isEmpty(catalogID) || catalogID.equals("0")) {
			return "0";
		}
		ZCCatalogSchema catalog = CMSCache.getCatalog(catalogID);
		if (catalog != null) {
			return "" + catalog.getParentID();
		}
		return null;
	}

	/**
	 * ��ȡ����Ŀ��
	 */
	public static String getChildCount(long catalogID) {
		if (catalogID == 0) {
			return "";
		}
		ZCCatalogSchema catalog = CMSCache.getCatalog(catalogID);
		if (catalog != null) {
			return "" + catalog.getChildCount();
		}
		return null;
	}

	/**
	 * ��ȡ����Ŀ��
	 */
	public static String getChildCount(String catalogID) {
		return getChildCount(Long.parseLong(catalogID));
	}

	/**
	 * �õ���Ŀ������
	 */
	public static DataTable getCatalogOptions(long type) {
		DataTable dt = new QueryBuilder(
				"select Name,ID,TreeLevel,ParentID from ZCCatalog where SiteID = ? and Type = ? order by ID",
				Application.getCurrentSiteID(), type).executeDataTable();
		PubFun.indentDataTable(dt, 0, 2, 0);
		return dt;
	}

	/**
	 * ȡ�ô���������Ŀ�б�,Ĭ�ϵ�һ�㼶Ϊ1. added by huanglei
	 */
	public static DataTable getList(int type) {
		return CatalogUtil.getList(type, 1);
	}

	/**
	 * ȡ�ô���������Ŀ�б���Ϊ�����ط���Ҫ����Ŀ����������added by huanglei
	 */
	public static DataTable getList(int type, int firstLevel) {
		String sql = "select Name,ID ,Level from ZCCatalog where Type=? and siteID =? order by InnerCode";
		DataTable dt = new QueryBuilder(sql, type, Application.getCurrentSiteID()).executeDataTable();
		PubFun.indentDataTable(dt, 0, 2, firstLevel);
		return dt;
	}

	/**
	 * ��ȡ������
	 */
	public static String getWorkflow(long catalogID) {
		return getWorkflow(catalogID + "");
	}

	/**
	 * ��ȡ������
	 */
	public static String getWorkflow(String catalogID) {
		if (StringUtil.isEmpty(catalogID) || catalogID.equals("0")) {
			return "";
		}
		ZCCatalogSchema catalog = CMSCache.getCatalog(catalogID);
		if (catalog != null) {
			return catalog.getWorkflow();
		}
		return null;
	}

	/**
	 * ��ȡ��Ŀ������������
	 */

	public static String getAttachDownFlag(long catalogID) {
		return getAttachDownFlag(catalogID + "");
	}

	/**
	 * ��ȡ��Ŀ������������
	 */
	public static String getAttachDownFlag(String catalogID) {
		ZCCatalogConfigSchema config = CMSCache.getCatalogConfig(catalogID);
		if (config != null) {
			return config.getAttachDownFlag();
		}
		return null;
	}

	/**
	 * ��ȡ��Ŀ������״̬
	 */

	public static Date getArchiveTime(long catalogID) {
		return getArchiveTime(catalogID + "");
	}

	/**
	 * ��ȡ��Ŀ�鵵ʱ��
	 */
	public static Date getArchiveTime(String catalogID) {
		if (StringUtil.isEmpty(catalogID) || catalogID.equals("0")) {
			return null;
		}
		ZCCatalogConfigSchema config = CMSCache.getCatalogConfig(catalogID);
		if (config != null) {
			String archive = config.getArchiveTime();
			if(archive.equals("0")){
				return null;
			}else{
				return DateUtil.addMonth(new Date(),Integer.parseInt(archive));
			}
		}
		return null;
	}


	/**
	 * ȡ����Ŀ��ҳ��Ŀ���
	 */
	public static String getSingleFlag(long catalogID) {
		return getSingleFlag(catalogID + "");
	}

	/**
	 * ȡ����Ŀ��ҳ��Ŀ���
	 */
	public static String getSingleFlag(String catalogID) {
		if (StringUtil.isEmpty(catalogID) || catalogID.equals("0")) {
			return "";
		}
		ZCCatalogSchema catalog = CMSCache.getCatalog(catalogID);
		if (catalog != null) {
			return catalog.getSingleFlag();
		}
		return null;
	}

	/**
	 * ��ֱ���Ŀ���ϼ���Ŀ�ڲ����봮 ������λ�������
	 */
	public static String getParentCatalogCode(String innerCode) {
		if (innerCode == null) {
			return "";
		}
		String[] arr = new String[innerCode.length() / 6];
		int i = 0;
		while (innerCode.length() >= 6) {
			arr[i++] = innerCode;
			innerCode = innerCode.substring(0, innerCode.length() - 6);
		}
		return "'" + StringUtil.join(arr, "','") + "'";
	}
	
	public static int getLevel(long catalogID){
		return getLevel(catalogID+"");
	}

	/**
	 * ��ȡ��Ŀ�㼶
	 */
	public static int getLevel(String catalogID) {
		if (StringUtil.isEmpty(catalogID) || catalogID.equals("0")) {
			return 0;
		}
		int level = 0;
		ZCCatalogSchema catalog = CMSCache.getCatalog(catalogID);
		if (catalog != null) {
			String url = catalog.getURL();
			if(StringUtil.isNotEmpty(url)){
				url = url+"/";
				Pattern p = Pattern.compile("\\$\\{CatalogPath\\}", Pattern.CASE_INSENSITIVE);
				Matcher matcher = p.matcher(url);
				
				if (matcher.find()) {
					level = (int)catalog.getTreeLevel();
				}

				url = matcher.replaceAll("").replaceAll("/+", "/");
				if (url.startsWith("/")) {
					url = url.substring(1);
				}
				level += StringUtil.count(url, "/");
			}else{
				level =  (int) catalog.getTreeLevel();
			}
		}
		return level;
	}
	
	public static int getDetailLevel(long catalogID){
		return getDetailLevel(catalogID+"");
	}
	
	/**
	 * ��ȡ��ϸҳ�����վ���Ŀ¼��·��
	 * @param catalogID
	 * @return
	 */
	public static int getDetailLevel(String catalogID) {
		int level = 0;
		ZCCatalogSchema catalog = CMSCache.getCatalog(catalogID);
		String detailTemplateNameRule = catalog.getDetailNameRule();
		if (StringUtil.isNotEmpty(detailTemplateNameRule)) {
			Pattern p = Pattern.compile("\\$\\{CatalogPath\\}", Pattern.CASE_INSENSITIVE);
			Matcher matcher = p.matcher(detailTemplateNameRule);
			if (matcher.find()) {
				level = CatalogUtil.getLevel(catalogID);
			}

			detailTemplateNameRule = matcher.replaceAll("");
			detailTemplateNameRule = detailTemplateNameRule.replaceAll("/+", "/");
			if (detailTemplateNameRule.startsWith("/")) {
				detailTemplateNameRule = detailTemplateNameRule.substring(1);
			}

			level += StringUtil.count(detailTemplateNameRule, "/");
		}else{
			level =  CatalogUtil.getLevel(catalogID);
		}
		return level;
	}

	/**
	 * ��ȡ��Ŀ·��
	 */
	public static String getFullPath(String catalogID) {
		if (StringUtil.isEmpty(catalogID) || catalogID.equals("0")) {
			return "";
		}
		String path = "";
		String parentID = getParentID(catalogID);
		if (StringUtil.isEmpty(parentID)) {
			return "";
		} else {
			path = getAlias(catalogID + "").toLowerCase() + "/";
			if (!"0".equals(parentID)) {
				path = getFullPath(parentID) + path;
			}
		}
		return path;
	}

	/**
	 * ��ȡ��ID��ʾ����Ŀ·��
	 */
	private static String getIDPath(String catalogID) {
		if (StringUtil.isEmpty(catalogID) || catalogID.equals("0")) {
			return "";
		}
		String path = "";
		String parentID = getParentID(catalogID);
		if (StringUtil.isEmpty(parentID)) {
			return "";
		} else {
			path = catalogID + "/";
			if (!"0".equals(parentID)) {
				path = getIDPath(parentID) + path;
			}
		}
		return path;
	}

	/**
	 * ��ȡ��Ŀ���丽����Ϣ
	 */
	public static DataRow getData(String catalogID) {
		if (StringUtil.isEmpty(catalogID) || catalogID.equals("0")) {
			return null;
		}
		ZCCatalogSchema catalog = CMSCache.getCatalog(catalogID);
		if (catalog != null) {
			DataRow dr = catalog.toDataRow();
			ColumnUtil.extendCatalogColumnData(dr, catalog.getSiteID(), "");
			return dr;
		} else {
			return null;
		}
	}

	/**
	 * ��ȡ����ID���ȵ�ʻ㴦���־����Ŀ���� ��Ŀ���丽����Ϣ
	 */
	public static String getHotWordType(String catalogID) {
		if (StringUtil.isEmpty(catalogID) || catalogID.equals("0")) {
			return "";
		}
		ZCCatalogConfigSchema config = CMSCache.getCatalogConfig(catalogID);
		if (config != null) {
			return "" + config.getHotWordType();
		}
		return null;
	}

	/**
	 * ��ʼ����Ŀ����
	 */
	public static void update(long catalogID) {
		update(catalogID + "");
	}

	/**
	 * ��ʼ����Ŀ����
	 */
	public static void update(String catalogID) {
		if (StringUtil.isEmpty(catalogID) || catalogID.equals("0")) {
			return;
		}
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(catalogID);

		if (catalog.fill()) {
			// ������Ŀ�������������Ϣ
			ZCCatalogConfigSchema config = new ZCCatalogConfigSchema();
			ZCCatalogConfigSet configSet = config.query(new QueryBuilder("where CatalogID=?", catalog.getID()));
			CacheManager.set("CMS", "Catalog", catalog.getID(), catalog);
			if (configSet != null && configSet.size() > 0) {
				config = configSet.get(0);
				if (config.getCatalogID() == 0) {
					CacheManager.set("CMS", "CatalogConfig", config.getSiteID() + ",0", config);
				} else {
					CacheManager.set("CMS", "CatalogConfig", config.getCatalogID(), config);
				}
			}
		} else {
			CacheManager.remove("CMS", "Catalog", catalogID);
		}
	}

	public static String createCatalogInnerCode(String parentCode) {
		if (StringUtil.isNotEmpty(parentCode)) {
			return NoUtil.getMaxNo("CatalogInnerCode", parentCode, 6);
		} else {
			return NoUtil.getMaxNo("CatalogInnerCode", 6);
		}
	}

	/**
	 * �����û���Ȩ�޵���Ŀid
	 */
	public static String getPrivCatalog(int type, String properties) {
		DataTable dt = new QueryBuilder("select * from zccatalog where siteid =? and type = ?", Application
				.getCurrentSiteID(), type).executeDataTable();
		ArrayList list = new ArrayList();
		String PrivType = Priv.ARTICLE;
		if (type == Catalog.CATALOG) {
			PrivType = Priv.ARTICLE;
		} else if (type == Catalog.IMAGELIB) {
			PrivType = Priv.IMAGE;
		} else if (type == Catalog.VIDEOLIB) {
			PrivType = Priv.VIDEO;
		} else if (type == Catalog.ATTACHMENTLIB) {
			PrivType = Priv.ATTACH;
		}
		for (int i = 0; i < dt.getRowCount(); i++) {
			if (Priv.getPriv(User.getUserName(), PrivType, dt.getString(i, "InnerCode"), Priv.ARTICLE_BROWSE)) {
				if (properties.equals("ID")) {
					list.add(dt.getString(i, "ID"));
				} else if (properties.equals("InnerCode")) {
					list.add(dt.getString(i, "InnerCode"));
				}
			}
		}
		if (!list.isEmpty()) {
			return StringUtil.join(list);
		}
		return "0";
	}

	/**
	 * ΪDataTable����CatalogName��
	 */
	public static void addCatalogName(DataTable dt, String catalogIDColumn) {
		dt.insertColumn("CatalogName");
		for (int i = 0; i < dt.getRowCount(); i++) {
			String name = CatalogUtil.getName(dt.getString(i, catalogIDColumn));
			dt.set(i, "CatalogName", name);
		}
	}
	
	public static void main(String[] args) {
		System.out.println(CatalogUtil.getFullName(10742));
	}
}
