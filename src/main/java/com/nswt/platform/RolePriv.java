package com.nswt.platform;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;

/**
 * ����Ȩ���ࣺȨ�޳�פ�ڴ�
 * 
 * @author huanglei
 * @mail huanglei@nswt.com
 * @date 2007-11-21
 */

public class RolePriv {
	/**
	 * Ȩ�����������ͣ���ɫ
	 */
	public static final String OWNERTYPE_ROLE = "R";

	private static Map RolePrivMap = new Hashtable();

	public static void updateAllPriv(String RoleCode) {
		Object obj = new QueryBuilder("select RoleCode from ZDRole where RoleCode = ?", RoleCode).executeOneValue();
		if (obj == null) {
			RolePrivMap.remove(RoleCode);
			return;
		}
		Object[] ks = Priv.PRIV_MAP.keyArray();
		for (int i = 0; i < Priv.PRIV_MAP.size(); i++) {
			RolePriv.updatePriv(RoleCode, (String) ks[i]);
		}
	}

	public static void updatePriv(String RoleCode, String PrivType) {
		// ��ʼ���û�����Ŀ�ϵ�Ȩ��
		String sql = "select ID,Code,Value from ZDPrivilege where OwnerType=? and Owner=? and PrivType=?";
		QueryBuilder qb = new QueryBuilder(sql);
		qb.add(RolePriv.OWNERTYPE_ROLE);
		qb.add(RoleCode);
		qb.add(PrivType);
		DataTable dt = qb.executeDataTable();
		Map PrivTypeMap = getPrivTypeMap(RoleCode, PrivType);
		RolePriv.getMapFromDataTable((Map) PrivTypeMap, dt);
	}

	/**
	 * ����һ���ɫ��ĳ��Ȩ�޵Ľ����򲢼�
	 * 
	 * @param RoleCodes
	 * @param PrivType
	 * @param ID
	 * @param Code
	 * @return
	 */
	public static boolean getRolePriv(String[] RoleCodes, String PrivType, String ID, String Code) {
		for (int i = 0; i < RoleCodes.length; i++) {
			if("admin".equalsIgnoreCase(RoleCodes[i])){
				return true;
			}
		}
		String value = null;
		if (Priv.MENU.equals(PrivType)) {
			for (int i = 0; i < RoleCodes.length; i++) {
				Map map = RolePriv.getPrivTypeMap(RoleCodes[i], PrivType);
				map = (Map) map.get(ID);
				if (map != null) {
					value = (String) map.get(Code);
					if ("1".equals(value)) {
						return true;
					}
				}
			}
			return false;
		} else if (Priv.SITE.equals(PrivType)) {
			for (int i = 0; i < RoleCodes.length; i++) {
				Map map = RolePriv.getPrivTypeMap(RoleCodes[i], PrivType);
				map = (Map) map.get(ID);
				if (map != null) {
					value = (String) map.get(Code);
					if ("1".equals(value)) {
						return true;
					}
				}
			}
			return false;
		} else {
			for (int i = 0; i < RoleCodes.length; i++) {
				Map map = RolePriv.getPrivTypeMap(RoleCodes[i], PrivType);
				map = (Map) map.get(ID);
				if (map != null) {
					value = (String) map.get(Code);
					if ("1".equals(value)) {
						return true;
					}
				}
			}
			if (value != null) {
				return "1".equals(value);
			}
			return false;
		}
	}

	public static Map getPrivTypeMap(String RoleCode, String PrivType) {
		Map RoleCodePrivMap = (Map) RolePrivMap.get(RoleCode); // һ����ɫ�µ�����Ȩ��
		if (RoleCodePrivMap == null) {
			RoleCodePrivMap = new Hashtable();
			RolePrivMap.put(RoleCode, RoleCodePrivMap);
			updateAllPriv(RoleCode);
		}
		Map PrivTypeMap = (Map) RoleCodePrivMap.get(PrivType); // ��ɫ��ĳ�����͵�Ȩ��
		if (PrivTypeMap == null) {
			PrivTypeMap = new HashMap();
			RoleCodePrivMap.put(PrivType, PrivTypeMap);
		}
		return PrivTypeMap;
	}

	public static void getMapFromDataTable(Map map, DataTable dt) {
		map.clear();
		if (dt == null || dt.getRowCount() <= 0) {
			return;
		}
		for (int i = 0; i < dt.getRowCount(); i++) {
			Map childMap = (Map) map.get(dt.getString(i, "ID"));
			if (childMap == null) {
				childMap = new HashMap();
				map.put(dt.getString(i, "ID"), childMap);
			}
			childMap.put(dt.getString(i, "Code"), dt.getString(i, "Value"));
		}
	}
}
