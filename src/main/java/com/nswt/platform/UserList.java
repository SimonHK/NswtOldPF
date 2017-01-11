package com.nswt.platform;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import com.nswt.cms.pub.PubFun;
import com.nswt.framework.Page;
import com.nswt.framework.RequestImpl;
import com.nswt.framework.User;
import com.nswt.framework.cache.CacheManager;
import com.nswt.framework.controls.DataGridAction;
import com.nswt.framework.controls.TreeAction;
import com.nswt.framework.data.DataCollection;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
//import com.nswt.framework.license.LicenseInfo;
import com.nswt.framework.utility.Errorx;
import com.nswt.framework.utility.HtmlUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.pub.PlatformCache;
import com.nswt.schema.ZDPrivilegeSchema;
import com.nswt.schema.ZDUserRoleSchema;
import com.nswt.schema.ZDUserRoleSet;
import com.nswt.schema.ZDUserSchema;
import com.nswt.schema.ZDUserSet;

/**
 * @Author ����
 * @Date 2007-8-6
 * @Mail huanglei@nswt.com
 */
public class UserList extends Page {

	public final static String ADMINISTRATOR = "admin";

	public final static String STATUS_NORMAL = "N";

	public final static String STATUS_STOP = "S";

	public final static Mapx STATUS_MAP = new Mapx();

	static {
		STATUS_MAP.put(STATUS_NORMAL, "����");
		STATUS_MAP.put(STATUS_STOP, "ͣ��");
	}

	public static Mapx init(Mapx params) {
		params.put("IsBranchAdmin", HtmlUtil.codeToRadios("IsBranchAdmin", "YesOrNo", "N"));
		params.put("Status", HtmlUtil.mapxToRadios("Status", STATUS_MAP, "N"));
		params.put("BranchInnerCode", PubFun.getBranchOptions());
		return params;
	}

	static String Password = "zvingzving";

	public static Mapx initEditDialog(Mapx params) {
		String userName = params.getString("UserName");
		ZDUserSchema user = new ZDUserSchema();
		user.setUserName(userName);
		user.fill();
		params = user.toMapx();
		params.put("IsBranchAdmin", HtmlUtil.codeToRadios("IsBranchAdmin", "YesOrNo", user.getIsBranchAdmin()));
		params.put("Status", HtmlUtil.mapxToRadios("Status", STATUS_MAP, user.getStatus()));
		params.put("BranchInnerCode", PubFun.getBranchOptions(user.getBranchInnerCode()));
		params.put("Password", Password);
		return params;
	}

	public static void dg1DataBind(DataGridAction dga) {
		String searchUserName = (String) dga.getParam("SearchUserName");
		QueryBuilder qb = new QueryBuilder("select * from ZDUser where 1=1 ");
		List roleCodeList = PubFun.getRoleCodesByUserName(User.getUserName());
		if (roleCodeList != null && roleCodeList.size() != 0) {
			if(!roleCodeList.contains("admin")){
				qb.append(" and BranchInnerCode like ? ", User.getBranchInnerCode() + "%");
			}
		}
		if (StringUtil.isNotEmpty(searchUserName)) {
			qb.append(" and (UserName like ? ", "%" + searchUserName.trim() + "%");

			// ��ѯ��ʵ����
			qb.append(" or realname like ? )", "%" + searchUserName.trim() + "%");
		}
		qb.append(" order by AddTime desc,UserName");
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dt.decodeColumn("BranchInnerCode", new QueryBuilder("select BranchInnerCode,Name from ZDBranch")
				.executeDataTable().toMapx(0, 1));
		dt.decodeColumn("Status", STATUS_MAP);
		dt.insertColumn("RoleNames");
		for (int i = 0; i < dt.getRowCount(); i++) {
			dt.set(i, "RoleNames", PubFun.getRoleNames(PubFun.getRoleCodesByUserName(dt.getString(i, "UserName"))));
		}
		dga.bindData(dt);
	}

	public static void initRoleTree(TreeAction ta) {
		DataTable dt = new QueryBuilder(
				"select RoleCode,'' as ParentID,'1' as TreeLevel,RoleName,'' as Checked from zdrole ")
				.executeDataTable();
		for (int i = 0; i < dt.getRowCount(); i++) {
			if (Role.EVERYONE.equalsIgnoreCase(dt.getString(i, "RoleCode"))) {
				dt.set(i, "Checked", "Checked");
			}
		}
		ta.setRootText("��ɫ");
		ta.setIdentifierColumnName("RoleCode");
		ta.bindData(dt);
	}

	public static void initEditRoleTree(TreeAction ta) {
		String userName = ta.getParam("UserName");
		DataTable dt = new QueryBuilder(
				"select RoleCode,'' as ParentID,'1' as TreeLevel,RoleName,(select 'Checked' from ZDUserRole b where b.RoleCode=ZDRole.RoleCode and UserName=?) as Checked from zdrole ",
				userName).executeDataTable();
		ta.setRootText("��ɫ");
		ta.setIdentifierColumnName("RoleCode");
		ta.bindData(dt);
	}

	public void add() {
		Transaction trans = new Transaction();
		if (!add(trans, Request)) {
			Response.setLogInfo(0, Errorx.printString());
			return;
		}
		if (trans.commit()) {
			Response.setLogInfo(1, "�½��û��ɹ�!");
			Priv.updateAllPriv($V("UserName"));
		} else {
			Response.setLogInfo(0, "�½��û�ʧ��!");
		}
	}

	private static Pattern userPattern = Pattern.compile("[\\w@\\.\u4e00-\u9fa5]{1,20}", Pattern.CASE_INSENSITIVE
			| Pattern.DOTALL);

	// ���ⲿ����
	public static boolean add(Transaction trans, DataCollection dc) {
		String userName = dc.getString("UserName");
		if (!userPattern.matcher(userName).matches()) {
			Errorx.addError("�û������20λ������Ӣ����ĸ�����֣����֣���ǡ�.������@��");
			return false;
		}
		ZDUserSchema user = new ZDUserSchema();
		user.setValue(dc);
		user.setUserName(user.getUserName().toLowerCase());
		if (user.fill()) {
			Errorx.addError(dc.getString("UserName") + "�û��Ѿ�����!");
			return false;
		}

		user.setPassword(StringUtil.md5Hex(dc.getString("Password")));
		if (dc.getString("Type") == null || "".equals(dc.getString("Type"))) {
			user.setType("0");
		} else {
			user.setType(dc.getString("Type"));
		}
		user.setProp1(dc.getString("Prop1"));
		user.setAddTime(new Date());
		user.setAddUser(User.getUserName());
		trans.add(user, Transaction.INSERT);
		// ForumUtil.createBBSUser(trans, user.getUserName());

		// ��ɫ
		String roleCodes = dc.getString("RoleCode");
		if (StringUtil.isEmpty(roleCodes)) {
			return true;
		}
		String[] RoleCodes = roleCodes.split(",");
		String currentUserName = User.getUserName();

		CacheManager.set(PlatformCache.ProviderName, "User", user.getUserName(), user);
		CacheManager.set(PlatformCache.ProviderName, "UserRole", user.getUserName(), roleCodes);

		for (int i = 0; i < RoleCodes.length; i++) {
			if (StringUtil.isEmpty(RoleCodes[i]) || StringUtil.isEmpty(user.getUserName())) {
				continue;
			}
			ZDUserRoleSchema userRole = new ZDUserRoleSchema();
			userRole.setUserName(user.getUserName());
			userRole.setRoleCode(RoleCodes[i]);
			userRole.setAddTime(new Date());
			userRole.setAddUser(currentUserName);
			trans.add(userRole, Transaction.INSERT);
		}
		return true;
	}

	public void save() {
		Transaction trans = new Transaction();
		if (!save(trans, Request)) {
			return;
		}
		if (trans.commit()) {
			Response.setLogInfo(1, "�޸ĳɹ�");
			Priv.updateAllPriv($V("UserName"));
		} else {
			Response.setLogInfo(0, "�޸�ʧ��");
		}
	}

	public boolean save(Transaction trans, DataCollection dc) {
		ZDUserSchema user = new ZDUserSchema();
		String newPassword = dc.getString("NewPassword");
		String newConfirmPassword = dc.getString("NewConfirmPassword");
		if (!newPassword.equals(newConfirmPassword)) {
			Response.setLogInfo(0, "���벻һ��!");
			return false;
		}
		user.setUserName(dc.getString("UserName"));
		if (!user.fill()) {
			return false;
		}
		user.setValue(dc);
		if (ADMINISTRATOR.equalsIgnoreCase(user.getUserName()) && STATUS_STOP.equalsIgnoreCase(user.getStatus())) {
			Response.setLogInfo(0, ADMINISTRATOR + "Ϊϵͳ�Դ����û���ӵ����߹���Ȩ�ޣ�����ͣ��!");
			return false;
		}
		user.setModifyTime(new Date());
		user.setModifyUser(User.getUserName());
		if (StringUtil.isNotEmpty(newPassword) && !Password.equals(newPassword)) {
			user.setPassword(StringUtil.md5Hex(newPassword));
		}
		trans.add(user, Transaction.UPDATE);

		// ��ɫ
		ZDUserRoleSchema userRole = new ZDUserRoleSchema();
		userRole.setUserName(user.getUserName());
		trans.add(userRole.query(), Transaction.DELETE_AND_BACKUP);

		String roleCodes = dc.getString("RoleCode");
		if (StringUtil.isEmpty(roleCodes)) {
			return true;
		}
		String[] RoleCodes = roleCodes.split(",");
		String currentUserName = User.getUserName();

		CacheManager.set(PlatformCache.ProviderName, "User", user.getUserName(), user);
		CacheManager.set(PlatformCache.ProviderName, "UserRole", user.getUserName(), roleCodes);

		for (int i = 0; i < RoleCodes.length; i++) {
			if (StringUtil.isEmpty(RoleCodes[i]) || StringUtil.isEmpty(user.getUserName())) {
				continue;
			}
			userRole = new ZDUserRoleSchema();
			userRole.setUserName(user.getUserName());
			userRole.setRoleCode(RoleCodes[i]);
			userRole.setAddTime(new Date());
			userRole.setAddUser(currentUserName);
			trans.add(userRole, Transaction.INSERT);

		}
		return true;
	}

	private static Pattern idPattern = Pattern.compile("[\\w@\\.\\,\u4e00-\u9fa5]*", Pattern.CASE_INSENSITIVE
			| Pattern.DOTALL);

	public void del() {
		String UserNames = $V("UserNames");
		if (!idPattern.matcher(UserNames).matches()) {
			Response.setLogInfo(0, "�����û�����ʱ��������!");
			return;
		}
		Transaction trans = new Transaction();
		if (!del(trans, Request)) {
			Response.setLogInfo(0, Errorx.printString());
			return;
		}
		if (trans.commit()) {
			UserLog.log("User", "DelUser", "ɾ���û�:" + UserNames + "�ɹ�", Request.getClientIP());
			Response.setLogInfo(1, "ɾ���û��ɹ�!");
		} else {
			UserLog.log("User", "DelUser", "ɾ���û�:" + UserNames + "ʧ��", Request.getClientIP());
			Response.setLogInfo(0, "ɾ���û�ʧ��!");
		}
	}

	public void stopUser() {
		String UserNames = $V("UserNames");
		if (!idPattern.matcher(UserNames).matches()) {
			Response.setLogInfo(0, "�����û�����ʱ��������!");
			return;
		}
		ZDUserSchema user = new ZDUserSchema();
		ZDUserSet userSet = user.query(new QueryBuilder(" where UserName in ('" + UserNames.replaceAll(",", "','")
				+ "')"));
		for (int i = 0; i < userSet.size(); i++) {
			if (ADMINISTRATOR.equalsIgnoreCase(userSet.get(i).getUserName())) {
				Response.setLogInfo(0, ADMINISTRATOR + "Ϊϵͳ�Դ����û���ӵ����߹���Ȩ�ޣ�����ͣ��!");
				return;
			}
			userSet.get(i).setStatus(STATUS_STOP);
		}
		if (userSet.update()) {
			Response.setLogInfo(1, "ͣ���û��ɹ�!");
		} else {
			Response.setLogInfo(0, "ͣ���û�ʧ��!");
		}
	}

	/**
	 * ɾ��һ���û���Ҫ���Ĳ��裺 ���»����е��û��� ɾ���û����ɫ�Ĺ�ϵ ���½�ɫ�е��û��� ɾ������û�������Ȩ�޼�¼
	 */
	public static boolean del(Transaction trans, DataCollection dc) {
		String UserNames = dc.getString("UserNames");
		ZDUserSchema user = new ZDUserSchema();
		ZDUserSet userSet = user.query(new QueryBuilder(" where UserName in ('" + UserNames.replaceAll(",", "','")
				+ "')"));
		trans.add(userSet, Transaction.DELETE_AND_BACKUP);

		for (int i = 0; i < userSet.size(); i++) {
			user = userSet.get(i);
			if (User.getUserName().equals(user.getUserName())) {
				Errorx.addError("��ǰ�û�Ϊ��" + User.getUserName() + ",����ɾ�������û�!");
				UserLog.log("User", "DelUser", "ɾ���û�:" + user.getUserName() + "ʧ��", ((RequestImpl) dc).getClientIP());
				return false;
			}
			if (ADMINISTRATOR.equalsIgnoreCase(user.getUserName())) {
				Errorx.addError(ADMINISTRATOR + "Ϊϵͳ�Դ����û���ӵ����߹���Ȩ�ޣ�����ɾ��!");
				UserLog.log("User", "DelUser", "ɾ���û�:" + user.getUserName() + "ʧ��", ((RequestImpl) dc).getClientIP());
				return false;
			}

			CacheManager.remove(PlatformCache.ProviderName, "User", user.getUserName());
			CacheManager.remove(PlatformCache.ProviderName, "UserRole", user.getUserName());

			// ɾ���������û�������Ĺ�ϵ
			ZDUserRoleSchema userRole = new ZDUserRoleSchema();
			userRole.setUserName(user.getUserName());
			ZDUserRoleSet userRoleSet = userRole.query();
			trans.add(userRoleSet, Transaction.DELETE_AND_BACKUP);
			// ɾ���û���Ȩ��
			trans.add(new ZDPrivilegeSchema().query(new QueryBuilder("where OwnerType=? and Owner=?",
					Priv.OWNERTYPE_USER, user.getUserName())), Transaction.DELETE_AND_BACKUP);
		}

		return true;
	}
}
