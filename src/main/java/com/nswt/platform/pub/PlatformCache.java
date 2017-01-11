package com.nswt.platform.pub;

import com.nswt.framework.cache.CacheManager;
import com.nswt.framework.cache.CacheProvider;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.schema.ZDBranchSchema;
import com.nswt.schema.ZDBranchSet;
import com.nswt.schema.ZDRoleSchema;
import com.nswt.schema.ZDRoleSet;
import com.nswt.schema.ZDUserRoleSchema;
import com.nswt.schema.ZDUserRoleSet;
import com.nswt.schema.ZDUserSchema;

/**
 * 平台相关的缓存项，包括用户、角色、用户角色关联 日期 : 2010-2-4 <br>
 * 作者: NSWT <br>
 * 邮箱：nswt@nswt.com.cn <br>
 */
public class PlatformCache extends CacheProvider {
	public static final String ProviderName = "Platform";

	public String getProviderName() {
		return ProviderName;
	}

	public void onKeyNotFound(String type, Object key) {
		if (type.equals("UserRole")) {
			ZDUserRoleSchema schema = new ZDUserRoleSchema();
			schema.setUserName(key.toString());
			ZDUserRoleSet set = schema.query();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < set.size(); i++) {
				if (i != 0) {
					sb.append(",");
				}
				sb.append(set.get(i).getRoleCode());
			}
			if (set.size() > 0) {
				CacheManager.set(this.getProviderName(), type, key, sb.toString());
			} else {
				CacheManager.set(this.getProviderName(), type, key, "");// 说明没有任何角色
			}
		}
		if (type.equals("User")) {
			ZDUserSchema schema = new ZDUserSchema();
			schema.setUserName(key.toString());
			if (schema.fill()) {
				CacheManager.set(this.getProviderName(), type, key, schema);
			}

		}
	}

	public void onTypeNotFound(String type) {
		// 最多缓存一万个
		if (type.equals("User")) {// 延迟到onKeyNotFound中加载
			CacheManager.setMapx(this.getProviderName(), "User", new Mapx(10000));
		}
		if (type.equals("Role")) {
			ZDRoleSet set = new ZDRoleSchema().query();
			for (int i = 0; i < set.size(); i++) {
				CacheManager.set(this.getProviderName(), type, set.get(i).getRoleCode(), set.get(i));
			}
		}
		// 最多缓存一万个
		if (type.equals("UserRole")) {// 延迟到onKeyNotFound中加载
			CacheManager.setMapx(this.getProviderName(), "UserRole", new Mapx(10000));
		}
		if (type.equals("Branch")) {
			ZDBranchSet set = new ZDBranchSchema().query();
			for (int i = 0; i < set.size(); i++) {
				CacheManager.set(this.getProviderName(), type, set.get(i).getBranchInnerCode(), set.get(i));
			}
		}
	}

	public static ZDUserSchema getUser(String userName) {
		return (ZDUserSchema) CacheManager.get(ProviderName, "User", userName);
	}

	public static ZDRoleSchema getRole(String roleCode) {
		return (ZDRoleSchema) CacheManager.get(ProviderName, "Role", roleCode);
	}

	public static String getUserRole(String userName) {
		return (String) CacheManager.get(ProviderName, "UserRole", userName);
	}

	public static ZDBranchSchema getBranch(String innerCode) {
		if (StringUtil.isEmpty(innerCode)) {
			innerCode = "0001";
		}
		return (ZDBranchSchema) CacheManager.get(ProviderName, "Branch", innerCode);
	}

	public static void removeRole(String roleCode) {
		CacheManager.remove(ProviderName, "Role", roleCode);
		Mapx map = CacheManager.getMapx(ProviderName, "UserRole");
		synchronized (map) {
			Object[] ks = map.keyArray();
			Object[] vs = map.valueArray();
			roleCode = "," + roleCode + ",";
			for (int i = 0; i < vs.length; i++) {
				String ur = "," + vs[i] + ",";
				if (ur.indexOf(roleCode) >= 0) {
					ur = StringUtil.replaceEx(ur, roleCode, ",");
				}
				ur = ur.substring(0, ur.length() - 1);
				map.put(ks[i], ur);
			}
		}
	}

	public static void addUserRole(String userName, String roleCode) {
		String roles = (String) CacheManager.get(ProviderName, "UserRole", userName);
		if (StringUtil.isEmpty(roles)) {
			CacheManager.set(ProviderName, "UserRole", userName, roleCode);
		} else {
			CacheManager.set(ProviderName, "UserRole", userName, roles + "," + roleCode);
		}
	}

	public static void removeUserRole(String userName, String roleCode) {
		String roles = (String) CacheManager.get(ProviderName, "UserRole", userName);
		if (StringUtil.isEmpty(roles)) {
			return;
		} else {
			String ur = "," + roles + ",";
			if (ur.indexOf(roleCode) >= 0) {
				ur = StringUtil.replaceEx(ur, roleCode, ",");
			}
			ur = ur.substring(0, ur.length() - 1);
			CacheManager.set(ProviderName, "UserRole", userName, ur);
		}
	}
}
