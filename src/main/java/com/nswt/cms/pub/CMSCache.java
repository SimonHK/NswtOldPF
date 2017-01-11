package com.nswt.cms.pub;

import com.nswt.framework.cache.CacheManager;
import com.nswt.framework.cache.CacheProvider;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.schema.ZCCatalogConfigSchema;
import com.nswt.schema.ZCCatalogConfigSet;
import com.nswt.schema.ZCCatalogSchema;
import com.nswt.schema.ZCCatalogSet;
import com.nswt.schema.ZCSiteSchema;
import com.nswt.schema.ZCSiteSet;
import com.nswt.schema.ZCTagSchema;
import com.nswt.schema.ZCTagSet;

/**
 * CMS��صĻ��������վ�㣬��Ŀ���ڲ����룬���ƣ���Ŀ��������
 * 
 * ���� : 2010-2-4 <br>
 * ����: NSWT <br>
 * ���䣺nswt@nswt.com.cn <br>
 */
public class CMSCache extends CacheProvider {

	private static final int CACHESIZE = 10000;// ��໺��һ�����Ŀ

	public static final String ProviderName = "CMS";

	public String getProviderName() {
		return ProviderName;
	}

	/*
	 * ��ZCCatalogSchema�����뻺��ʱ��ͬ������InnerCode��Name�Ļ���
	 */
	public void onKeySet(String type, Object key, Object value) {
		if (type.equals("Catalog")) {
			ZCCatalogSchema catalog = (ZCCatalogSchema) value;
			CacheManager.set(this.getProviderName(), "CatalogInnerCode", catalog.getInnerCode(), catalog.getID());

			// ���Ȼ���һ����Ŀ��������ĿID����Ϣ
			Mapx map = CacheManager.getMapx(this.getProviderName(), "CatalogName");
			if (catalog.getParentID() != 0) {

				// ����ĿΪ��һ����Ŀ���ж��Ƿ����л��棬��������򲻻��汾��Ŀ��Ϣ
				if (StringUtil.isEmpty(map.getString(catalog.getSiteID() + "|" + catalog.getName()))) {
					CacheManager.set(this.getProviderName(), "CatalogName", catalog.getSiteID() + "|"
							+ catalog.getName(), catalog.getID());
				}

				if (StringUtil.isEmpty(map.getString(catalog.getSiteID() + "|" + catalog.getName() + "|"
						+ catalog.getParentID()))) {
					CacheManager.set(this.getProviderName(), "CatalogName", catalog.getSiteID() + "|"
							+ catalog.getName() + "|" + catalog.getParentID(), catalog.getID());
				}
			} else {
				// ����ĿΪһ����Ŀ�����汾��Ŀ��Ϣ
				CacheManager.set(this.getProviderName(), "CatalogName", catalog.getSiteID() + "|" + catalog.getName(),
						catalog.getID());
				CacheManager.set(this.getProviderName(), "CatalogName", catalog.getSiteID() + "|" + catalog.getName()
						+ "|" + catalog.getParentID(), catalog.getID());
			}
		}
	}

	public void onKeyNotFound(String type, Object key) {
		if (key == null || StringUtil.isEmpty(String.valueOf(key)) || "null".equals(key)) {
			return;
		}
		// ����վ������
		if (type.equals("Site")) {
			ZCSiteSchema schema = new ZCSiteSchema();
			schema.setID(String.valueOf(key));
			if (schema.fill()) {
				CacheManager.set(this.getProviderName(), type, schema.getID(), schema);
			}
		}
		// ������Ŀ��չ����
		if (type.equals("CatalogConfig")) {
			ZCCatalogConfigSchema schema = new ZCCatalogConfigSchema();
			String id = String.valueOf(key);
			if (id.indexOf(',') > 0) {
				String[] arr = id.split(",");
				schema.setSiteID(arr[0]);
				schema.setCatalogID("0");
			} else {
				schema.setCatalogID(id);
			}
			ZCCatalogConfigSet set = schema.query();
			if (set.size() > 0) {
				schema = set.get(0);
			} else {
				LogUtil.warn("δ�ҵ�ID=" + id + "��ZCCatalogConfig��¼!");
				return;
			}
			CacheManager.set(this.getProviderName(), type, id, schema);
		}
		// ������ĿSchema
		if (type.equals("Catalog")) {
			ZCCatalogSchema schema = new ZCCatalogSchema();
			schema.setID(String.valueOf(key));
			if (schema.fill()) {
				CacheManager.set(this.getProviderName(), type, schema.getID(), schema);
			}
		}
		// ������Ŀ�ڲ�������ID��ӳ��
		if (type.equals("CatalogInnerCode")) {
			ZCCatalogSchema schema = new ZCCatalogSchema();
			schema.setInnerCode(String.valueOf(key));
			ZCCatalogSet set = schema.query();
			for (int i = 0; i < set.size(); i++) {
				schema = set.get(i);
				// ֻ�����Catalog������������InnerCode��Name
				CacheManager.set(this.getProviderName(), "Catalog", schema.getID(), schema);
			}
		}
		// ������Ŀ������ID��ӳ��
		if (type.equals("CatalogName")) {
			String[] arr = key.toString().split("\\|");
			String SiteID = arr[0];
			String Name = arr[1];
			String ParentID = null;
			if (arr.length > 2) {
				ParentID = arr[2];
			}
			QueryBuilder qb = new QueryBuilder("where SiteID=? and Name=?",Long.parseLong(SiteID), Name);
			if (StringUtil.isNotEmpty(ParentID)) {
				qb.append(" and ParentID=?", Long.parseLong(ParentID));
			}
			ZCCatalogSchema schema = new ZCCatalogSchema();
			ZCCatalogSet set = schema.query(qb);
			for (int i = 0; i < set.size(); i++) {
				schema = set.get(i);
				CacheManager.set(this.getProviderName(), "Catalog", schema.getID(), schema);
			}
		}
		// ��ǩ����
		if (type.equals("Tag")) {
			ZCTagSchema tag = new ZCTagSchema();
			String[] arr = key.toString().split("\\|");
			tag.setSiteID(arr[0]);
			tag.setTag(arr[1]);
			ZCTagSet set = tag.query();
			for (int i = 0; i < set.size(); i++) {
				tag = set.get(i);
				CacheManager.set(this.getProviderName(), "Tag", key, tag);
			}
		}
	}

	public void onTypeNotFound(String type) {
		if (type.equals("Site")) {
			ZCSiteSet set = new ZCSiteSchema().query();
			for (int i = 0; i < set.size(); i++) {
				CacheManager.set(this.getProviderName(), type, set.get(i).getID(), set.get(i));
			}
		}
		if (type.equals("CatalogConfig")) {
			CacheManager.setMapx(this.getProviderName(), type, new Mapx(CACHESIZE));// Ĭ�ϲ���������
		}
		if (type.equals("Catalog")) {
			CacheManager.setMapx(this.getProviderName(), type, new Mapx(CACHESIZE));// Ĭ�ϲ���������
		}
		if (type.equals("CatalogName")) {
			CacheManager.setMapx(this.getProviderName(), type, new Mapx(CACHESIZE));// Ĭ�ϲ���������
		}
		if (type.equals("CatalogInnerCode")) {
			CacheManager.setMapx(this.getProviderName(), type, new Mapx(CACHESIZE));// Ĭ�ϲ���������
		}
		// ��ǩ����
		if (type.equals("Tag")) {
			CacheManager.setMapx(this.getProviderName(), type, new Mapx(CACHESIZE));// ��Ҫ���û�������С
			ZCTagSchema tag = new ZCTagSchema();
			ZCTagSet set = tag.query();
			for (int i = 0; i < set.size(); i++) {
				tag = set.get(i);
				CacheManager.set(this.getProviderName(), "Tag", tag.getSiteID() + "|" + tag.getTag(), tag);
			}
		}
	}

	public static ZCSiteSchema getSite(String id) {
		return (ZCSiteSchema) CacheManager.get(ProviderName, "Site", id);
	}

	public static ZCSiteSchema getSite(long id) {
		return (ZCSiteSchema) CacheManager.get(ProviderName, "Site", id);
	}

	public static ZCCatalogSchema getCatalogByInnerCode(String innerCode) {
		return (ZCCatalogSchema) CacheManager.get(ProviderName, "Catalog", CacheManager.get(ProviderName,
				"CatalogInnerCode", innerCode));
	}

	public static ZCCatalogSchema getCatalog(String id) {
		return (ZCCatalogSchema) CacheManager.get(ProviderName, "Catalog", id);
	}

	public static ZCCatalogSchema getCatalog(long id) {
		return (ZCCatalogSchema) CacheManager.get(ProviderName, "Catalog", id);
	}

	public static ZCCatalogSchema getCatalog(long siteID, String name) {
		return (ZCCatalogSchema) CacheManager.get(ProviderName, "Catalog", CacheManager.get(ProviderName,
				"CatalogName", siteID + "|" + name));
	}

	public static ZCCatalogSchema getCatalog(String siteID, String name) {
		return (ZCCatalogSchema) CacheManager.get(ProviderName, "Catalog", CacheManager.get(ProviderName,
				"CatalogName", siteID + "|" + name));
	}

	public static ZCCatalogSchema getCatalog(long siteID, long parentID, String name) {
		return (ZCCatalogSchema) CacheManager.get(ProviderName, "Catalog", CacheManager.get(ProviderName,
				"CatalogName", siteID + "|" + name + "|" + parentID));
	}

	public static ZCCatalogSchema getCatalog(String siteID, long parentID, String name) {
		return (ZCCatalogSchema) CacheManager.get(ProviderName, "Catalog", CacheManager.get(ProviderName,
				"CatalogName", siteID + "|" + name + "|" + parentID));
	}

	public static ZCCatalogConfigSchema getCatalogConfig(String id) {
		return (ZCCatalogConfigSchema) CacheManager.get(ProviderName, "CatalogConfig", id);
	}

	public static ZCCatalogConfigSchema getCatalogConfig(long id) {
		return (ZCCatalogConfigSchema) CacheManager.get(ProviderName, "CatalogConfig", id);
	}

	public static ZCTagSchema getTag(long siteID, String tagName) {
		return (ZCTagSchema) CacheManager.get(ProviderName, "Tag", siteID + "|" + tagName);
	}

	public static void removeCatalog(ZCCatalogSchema catalog) {
		CacheManager.remove(ProviderName, "Catalog", catalog.getID());
		CacheManager.remove(ProviderName, "CatalogConfig", catalog.getID());
		CacheManager.remove(ProviderName, "CatalogInnerCode", catalog.getSiteID() + "|" + catalog.getName());
		CacheManager.remove(ProviderName, "CatalogInnerCode", catalog.getSiteID() + "|" + catalog.getName() + "|"
				+ catalog.getParentID());
	}

	public static void removeCatalogSet(ZCCatalogSet catalogSet) {
		for (int i = 0; i < catalogSet.size(); i++) {
			removeCatalog(catalogSet.get(i));
		}
	}
}
