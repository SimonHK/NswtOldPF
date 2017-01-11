package com.nswt.platform;

import java.util.Date;

import com.nswt.cms.pub.PubFun;
import com.nswt.framework.Page;
import com.nswt.framework.User;
import com.nswt.framework.cache.CacheManager;
import com.nswt.framework.controls.DataGridAction;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.pub.PlatformCache;
import com.nswt.schema.ZDPrivilegeSchema;
import com.nswt.schema.ZDRoleSchema;
import com.nswt.schema.ZDUserRoleSchema;
import com.nswt.schema.ZDUserRoleSet;

/**
 * @Author ����
 * @Date 2007-8-6
 * @Mail huanglei@nswt.com
 */
public class RoleTabBasic extends Page {

	/**
	 * ��ʼ����ȡ��ɫ��Ϣ
	 */
	public static Mapx init(Mapx params) {
		String RoleCode = params.getString("RoleCode");
		if (RoleCode == null || "".equals(RoleCode)) {
			RoleCode = params.getString("Cookie.Role.LastRoleCode");
			if (RoleCode == null || "".equals(RoleCode)) {
				return params;
			}
		}
		ZDRoleSchema role = new ZDRoleSchema();
		role.setRoleCode(RoleCode);
		if (!role.fill()) {
			LogUtil.warn("��ѯ�����ý�ɫ������");
			return params;
		}
		Mapx map = role.toMapx();
		map.put("BranchName", new QueryBuilder("select name from zdbranch where BranchInnerCode=? Order by OrderFlag",
				role.getBranchInnerCode()).executeString());
		return map;
	}

	/**
	 * ��ʼ���޸ĶԻ�����Ϣ
	 */
	public static Mapx initEditDialog(Mapx params) {
		ZDRoleSchema role = new ZDRoleSchema();
		role.setRoleCode(params.get("RoleCode").toString());
		if (!role.fill()) {
			LogUtil.info("û�в�ѯ���ý�ɫ������");
			return params;
		}
		Mapx map = role.toMapx();
		return map;
	}

	/**
	 * ��ʼ����ӽ�ɫ�Ի�����Ϣ
	 */
	public static Mapx initDialog(Mapx params) {
		params.put("BranchInnerCode", PubFun.getBranchOptions());
		return params;
	}

	/**
	 * ��ӽ�ɫ
	 */
	public void add() {
		ZDRoleSchema role = new ZDRoleSchema();
		role.setValue(Request);
		role.setRoleCode(role.getRoleCode().toLowerCase());
		if (role.fill()) {
			Response.setLogInfo(0, "��ɫ����" + role.getRoleCode() + "�Ѿ������ˣ���ѡ������Ľ�ɫ���룡");
			return;
		}
		Date currentDate = new Date();
		String currentUserName = User.getUserName();
		role.setAddTime(currentDate);
		role.setAddUser(currentUserName);

		Transaction trans = new Transaction();
		trans.add(role, Transaction.INSERT);

		if (trans.commit()) {
			RolePriv.updateAllPriv(role.getRoleCode());
			CacheManager.set(PlatformCache.ProviderName, "Role", role.getRoleCode(), role);
			Response.setLogInfo(1, "�½��ɹ�");
		} else {
			Response.setLogInfo(0, "�½�ʧ��");
		}
	}

	/**
	 * �����޸���Ϣ
	 */
	public void save() {
		ZDRoleSchema role = new ZDRoleSchema();
		role.setRoleCode($V("RoleCode"));
		role.fill();

		role.setValue(Request);
		role.setModifyTime(new Date());
		role.setModifyUser(User.getUserName());

		if (role.update()) {
			CacheManager.set(PlatformCache.ProviderName, "Role", role.getRoleCode(), role);
			Response.setLogInfo(1, "�޸ĳɹ�");
		} else {
			Response.setLogInfo(0, "�޸�ʧ��");
		}
	}

	/**
	 * ɾ����ɫʱ��Ҫ���Ĳ��� ���»����еĽ�ɫ�� ɾ���û����ɫ�Ĺ�ϵ ɾ�������ɫ������Ȩ�޼�¼ ���������ɫ�µ������û���Ȩ�޼�¼
	 */
	public void del() {
		String RoleCode = Request.getString("RoleCode");
		Transaction trans = new Transaction();
		ZDRoleSchema role = new ZDRoleSchema();
		role.setRoleCode(RoleCode);
		role.fill();
		if (Role.EVERYONE.equalsIgnoreCase(RoleCode)) {
			Response.setLogInfo(0, Role.EVERYONE + "Ϊϵͳ�Դ��Ľ�ɫ������ɾ����");
			UserLog.log(UserLog.USER, UserLog.USER_DELROLE, "ɾ����ɫ:" + role.getRoleName() + "ʧ��", Request.getClientIP());
			return;
		}
		if ("admin".equalsIgnoreCase(RoleCode)) {
			Response.setLogInfo(0, "admin" + "Ϊϵͳ�Դ��Ľ�ɫ������ɾ����");
			UserLog.log(UserLog.USER, UserLog.USER_DELROLE, "ɾ����ɫ:" + role.getRoleName() + "ʧ��", Request.getClientIP());
			return;
		}
		// ɾ����ɫ
		trans.add(role, Transaction.DELETE_AND_BACKUP);

		ZDUserRoleSchema userRole = new ZDUserRoleSchema();
		ZDUserRoleSet userRoleSet = userRole.query(new QueryBuilder("where RoleCode =?", RoleCode));
		// ɾ����ɫ���û��Ĺ�ϵ
		trans.add(userRoleSet, Transaction.DELETE_AND_BACKUP);

		// ɾ����ɫ��Ȩ��
		trans.add(new ZDPrivilegeSchema().query(new QueryBuilder("where OwnerType=? and Owner=?",
				RolePriv.OWNERTYPE_ROLE, RoleCode)), Transaction.DELETE_AND_BACKUP);

		if (trans.commit()) {
			PlatformCache.removeRole(role.getRoleCode());

			RolePriv.updateAllPriv(RoleCode);
			UserLog.log(UserLog.USER, UserLog.USER_DELROLE, "ɾ����ɫ:" + role.getRoleName() + "�ɹ�", Request.getClientIP());
			Response.setLogInfo(1, "ɾ���ɹ�!");
		} else {
			UserLog.log(UserLog.USER, UserLog.USER_DELROLE, "ɾ����ɫ:" + role.getRoleName() + "ʧ��", Request.getClientIP());
			Response.setLogInfo(0, "ɾ��ʧ��!");
		}
	}

	/**
	 * ��ʾѡ�н�ɫ�µ������û���Ϣ
	 */
	public static void dg1DataBind(DataGridAction dga) {
		String RoleCode = dga.getParam("RoleCode");
		if (RoleCode == null || "".equals(RoleCode)) {
			RoleCode = dga.getParams().getString("Cookie.Role.LastRoleCode");
			if (RoleCode == null || "".equals(RoleCode)) {
				dga.bindData(new DataTable());
				return;
			}
		}
		QueryBuilder qb = new QueryBuilder(
				"select * from ZDUser where exists (select UserName from ZDUserRole b where b.UserName = ZDUser.UserName and b.RoleCode=?)",
				RoleCode);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dt.insertColumn("RoleNames");
		for (int i = 0; i < dt.getRowCount(); i++) {
			dt.set(i, "RoleNames", PubFun.getRoleNames(PubFun.getRoleCodesByUserName(dt.getString(i, "UserName"))));
		}
		dga.setTotal(qb);
		dga.bindData(dt);
	}

	/**
	 * ��ȡ�ý�ɫ�µ������û�
	 */
	public static void bindUserList(DataGridAction dga) {
		String roleCode = dga.getParam("RoleCode");
		String searchUserName = dga.getParam("SearchUserName");
		QueryBuilder qb = new QueryBuilder("select * from ZDUser");
		qb.append(" where BranchInnerCode like ?", User.getBranchInnerCode() + "%");
		qb.append(" and not exists (select '' from zduserrole where zduserrole.roleCode=?"
				+ " and zduserrole.userName=zduser.userName)", roleCode);
		if (StringUtil.isNotEmpty(searchUserName)) {
			qb.append(" and (UserName like ?", "%" + searchUserName.trim() + "%");
			// ��ѯ��ʵ����
			qb.append(" or realname like ?)", "%" + searchUserName.trim() + "%");
		}
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dt.decodeColumn("Status", UserList.STATUS_MAP);
		dga.bindData(dt);
	}

	/**
	 * ����û���һ����ɫ��
	 * 
	 */
	public void addUserToRole() {
		String RoleCode = $V("RoleCode");
		if (StringUtil.isEmpty(RoleCode)) {
			Response.setLogInfo(0, "��ɫ����Ϊ��");
			return;
		}
		String UserNameStr = $V("UserNames");
		String[] UserNames = UserNameStr.split(",");
		Date currentDate = new Date();
		String currentUserName = User.getUserName();
		Transaction trans = new Transaction();

		ZDUserRoleSet set = new ZDUserRoleSet();
		for (int i = 0; i < UserNames.length; i++) {
			if (StringUtil.isEmpty(UserNames[i])) {
				continue;
			}
			ZDUserRoleSchema userRole = new ZDUserRoleSchema();
			userRole.setUserName(UserNames[i]);
			userRole.setRoleCode(RoleCode);
			userRole.setAddTime(currentDate);
			userRole.setAddUser(currentUserName);
			set.add(userRole);
		}
		trans.add(set, Transaction.INSERT);
		if (trans.commit()) {
			for (int i = 0; i < set.size(); i++) {
				PlatformCache.addUserRole(set.get(i).getUserName(), set.get(i).getRoleCode());
			}
			Response.setLogInfo(1, "��ӳɹ�");
		} else {
			Response.setLogInfo(0, "���ʧ��");
		}
	}

	/**
	 * �ӽ�ɫ��ɾ���û�
	 * 
	 */
	public void delUserFromRole() {
		String RoleCode = $V("RoleCode");
		String UserNameStr = $V("UserNames");
		String[] UserNames = UserNameStr.split(",");
		Transaction trans = new Transaction();

		ZDUserRoleSet set = new ZDUserRoleSet();
		for (int i = 0; i < UserNames.length; i++) {
			DataTable dt = new QueryBuilder("select RoleCode from ZDUserRole where UserName=? and RoleCode!=?",
					UserNames[i], RoleCode).executeDataTable();
			String[] RoleCodes = new String[dt.getRowCount()];
			for (int j = 0; j < dt.getRowCount(); j++) {
				RoleCodes[j] = dt.getString(j, 0);
			}
			ZDUserRoleSchema userRole = new ZDUserRoleSchema();
			userRole.setUserName(UserNames[i]);
			userRole.setRoleCode(RoleCode);
			userRole.fill();
			set.add(userRole);
		}
		trans.add(set, Transaction.DELETE_AND_BACKUP);
		if (trans.commit()) {
			for (int i = 0; i < set.size(); i++) {
				PlatformCache.removeUserRole(set.get(i).getUserName(), set.get(i).getRoleCode());
			}
			Response.setLogInfo(1, "ɾ���ɹ�");
		} else {
			Response.setLogInfo(0, "ɾ��ʧ��");
		}
	}

}
