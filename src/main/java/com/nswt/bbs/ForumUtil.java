package com.nswt.bbs;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.nswt.bbs.admin.ForumScore;
import com.nswt.framework.Config;
import com.nswt.framework.User;
import com.nswt.framework.cache.CacheManager;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.Application;
import com.nswt.platform.UserList;
import com.nswt.schema.ZCForumConfigSchema;
import com.nswt.schema.ZCForumGroupSchema;
import com.nswt.schema.ZCForumMemberSchema;
import com.nswt.schema.ZCForumMemberSet;
import com.nswt.schema.ZCForumSchema;
import com.nswt.schema.ZCForumSet;
import com.nswt.schema.ZCThemeSchema;
import com.nswt.schema.ZCThemeSet;

/**
 * 
 * @author wst
 * 
 */

public class ForumUtil {
	private static final String YES = "Y";

	/**
	 * �ӻ����еõ���̳����״̬
	 * 
	 * @param SiteID
	 * @return
	 */
	public static boolean getForumStatus(String SiteID) {
		ZCForumConfigSchema config = ForumCache.getConfigBySiteID(SiteID);
		if (config == null) {
			return false;
		}
		return YES.equalsIgnoreCase(config.getTempCloseFlag());
	}

	/**
	 * �ӻ����еõ��������״̬
	 * 
	 * @param SiteID
	 * @return
	 */
	public static boolean getForumLock(String ForumID) {
		ZCForumSchema forum = ForumCache.getForum(ForumID);
		if (forum == null) {
			return false;
		}
		return YES.equalsIgnoreCase(forum.getLocked());
	}

	/**
	 * �õ�����Ƿ���������
	 * 
	 * @param SiteID
	 * @return
	 */
	public static boolean getForumPassword(String ForumID) {
		ZCForumSchema forum = ForumCache.getForum(ForumID);
		if (forum == null) {
			return false;
		}
		return StringUtil.isNotEmpty(forum.getPassword());
	}

	/**
	 * �õ�����Ƿ���ʾ
	 * 
	 * @param SiteID
	 * @return
	 */
	public static boolean getForumDisplay(String ForumID) {
		ZCForumSchema forum = ForumCache.getForum(ForumID);
		if (forum == null) {
			return false;
		}
		return YES.equalsIgnoreCase(forum.getVisible());
	}

	/**
	 * �ж���̳�Ƿ���й����ݳ�ʼ��
	 * 
	 * @param SiteID
	 * @return
	 */
	public static boolean isInitDB(String SiteID) {
		ZCForumConfigSchema config = ForumCache.getConfigBySiteID(SiteID);
		if (config == null) {
			return false;
		}
		return true;
	}

	/**
	 * ����û����û������������̳������User�з���Y
	 * 
	 * @param SiteID
	 */
	public static void allowVisit(String SiteID) {
		if (!isInitDB(SiteID)) {
			return;
		}
		createBBSUser(SiteID);
		ForumPriv priv = new ForumPriv(SiteID);
		if (priv.hasPriv("AllowVisit")) {
			User.setValue("AllowMemberVisit", "Y");
		} else {
			User.setValue("AllowMemberVisit", "N");
		}
	}

	public static void isUnLockGroup(String ForumID) {
		if (StringUtil.isEmpty(User.getUserName()) || StringUtil.isEmpty(ForumID)) {
			return;
		}
		ZCForumSchema forum = ForumCache.getForum(ForumID);
		if (StringUtil.isEmpty(forum.getUnLockID())) {
			return;
		}
		ZCForumMemberSchema member = new ZCForumMemberSchema();
		member.setUserName(User.getUserName());
		member.fill();
		String[] ids = forum.getUnLockID().split(",");
		for (int i = 0; i < ids.length; i++) {
			long id = Long.parseLong(ids[i]);
			if (id == member.getUserGroupID() || id == member.getAdminID() || id == member.getDefinedID()) {
				User.setValue("UnLockGroup", "Y");
				return;
			}
		}
		User.setValue("UnLockGroup", "N");
	}

	public static void isUnPasswordGroup(String ForumID) {
		if (StringUtil.isEmpty(User.getUserName())) {
			return;
		}
		ZCForumSchema forum = ForumCache.getForum(ForumID);
		ZCForumMemberSchema member = new ZCForumMemberSchema();
		member.setUserName(User.getUserName());
		member.fill();
		String[] ids = forum.getUnPasswordID().split(",");
		for (int i = 0; i < ids.length; i++) {
			long id = Long.parseLong(ids[i]);
			if (id == member.getUserGroupID() || id == member.getAdminID() || id == member.getDefinedID()) {
				User.setValue("pass_" + ForumID, "Y");
				return;
			}
		}
		User.setValue("pass_" + ForumID, "N");
	}

	/**
	 * ����һ���û���userName���жϵ�ǰ�û��Ƿ�Դ��û��в���Ȩ��
	 * 
	 * @author cjc
	 * @param userName
	 * @return
	 */
	public static boolean isOperateMember(String userName) {
		if (StringUtil.isNotEmpty(userName) && ForumUtil.isExistMember(userName)) {
			if (User.getUserName().equals(UserList.ADMINISTRATOR)) {
				return true;
			}
			if (User.getUserName().equals(userName)) {
				return true;
			}
			ZCForumMemberSchema member = new ZCForumMemberSchema();
			ZCForumGroupSchema group = null;
			ZCForumMemberSchema currentMember = new ZCForumMemberSchema();
			ZCForumGroupSchema currentGroup = null;
			member.setUserName(userName);
			member.fill();

			currentMember.setUserName(User.getUserName());
			currentMember.fill();

			group = ForumCache.getGroup(member.getAdminID());
			currentGroup = ForumCache.getGroup(currentMember.getAdminID());

			if (currentGroup.getSystemName().equals("ϵͳ����Ա")) {
				return true;
			}
			if (member.getAdminID() == 0) {
				return true;
			} else if (StringUtil.isNotEmpty(currentGroup.getSystemName())
					&& currentGroup.getSystemName().equals("��������")) {
				if (!group.getSystemName().equals("��������") && !group.getSystemName().equals("ϵͳ����Ա")) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * ���ݷ����������û�����ͨ�û���
	 * 
	 * @author wst
	 * @param trans
	 * @param user
	 */
	public static void userGroupChange(ZCForumMemberSchema member) {
		long SiteID = member.getSiteID();
		long ForumScore = member.getForumScore();
		QueryBuilder qb = new QueryBuilder(
				"select ID from ZCForumGroup where SiteID=? and LowerScore<=? and UpperScore>?", SiteID, ForumScore);
		qb.add(ForumScore);
		DataTable dt = qb.executeDataTable();
		if (dt.getRowCount() > 0) {
			member.setUserGroupID(dt.getLong(0, "ID"));
		}
	}

	/**
	 * ���ط���
	 * 
	 * @param memberSet
	 */
	public static void userGroupChange(ZCForumMemberSet memberSet) {
		for (int i = 0; i < memberSet.size(); i++) {
			userGroupChange(memberSet.get(i));
		}
	}

	/**
	 * ����û���һ�ν�����̳������û���̳��Ϣ��
	 * 
	 * @author wst
	 * @param trans
	 * @param userName
	 */
	public static void createBBSUser(Transaction trans, String userName, String SiteID) {
		if (!isInitDB(SiteID)) {
			return;
		}
		if (StringUtil.isEmpty(userName)) {
			return;
		}
		ZCForumMemberSchema forumMember = new ZCForumMemberSchema();
		forumMember.setUserName(userName);
		if (forumMember.fill()) {
			return;
		}
		forumMember.setThemeCount(0);
		forumMember.setReplyCount(0);
		forumMember.setAdminID(0);
		forumMember.setUserGroupID(0);
		if ("Y".equalsIgnoreCase(Config.getValue("PublicUseBBS"))) {
			forumMember.setSiteID(0);
			forumMember.setForumScore(new ForumScore(0).InitScore);
		} else {
			forumMember.setSiteID(SiteID);
			forumMember.setForumScore(new ForumScore(SiteID).InitScore);
		}
		forumMember.setUseSelfImage("N");
		forumMember.setHeadImage("Images/SystemHeadImage/HeadImage01.gif");
		forumMember.setAddTime(new Date());
		forumMember.setAddUser(userName);
		// ͨ����Ա�ĳ�ʼ���ָ���Աһ����ͨ�û���
		userGroupChange(forumMember);
		if (trans != null) {
			trans.add(forumMember, Transaction.INSERT);
		} else {
			forumMember.insert();
		}
	}

	public static void createBBSUser(String SiteID) {
		createBBSUser(null, User.getUserName(), SiteID);
	}

	/**
	 * ���ݰ��ID�õ���ǰ���İ����ͷ��������ַ�������
	 * 
	 * @author wst
	 * @param ThemeID
	 * @return ������UserName���ַ�������
	 */
	public static String[] getAdmins(String ForumID) {
		DataTable dt = new QueryBuilder(
				"select a.ForumAdmin,b.ForumAdmin ParentAdmin from ZCForum a,ZCForum b where a.ID=?"
						+ " and b.ID=a.ParentID", ForumID).executeDataTable();
		String ForumAdmins = "";
		if (dt.getRowCount() > 0) {
			String ForumAdmin = dt.getString(0, "ForumAdmin");
			if (ForumAdmin.endsWith(",")) {
				int index = ForumAdmin.lastIndexOf(",");
				ForumAdmin = ForumAdmin.substring(0, index);
			}
			String ParentAdmin = dt.getString(0, "ParentAdmin");
			if (ParentAdmin.endsWith(",")) {
				int index = ParentAdmin.lastIndexOf(",");
				ParentAdmin = ParentAdmin.substring(0, index);
			}
			if (StringUtil.isNotEmpty(ForumAdmin)) {
				ForumAdmins = ForumAdmin;
				if (StringUtil.isNotEmpty(ParentAdmin)) {
					ForumAdmins += "," + ParentAdmin;
				}
			} else {
				if (StringUtil.isNotEmpty(ParentAdmin)) {
					ForumAdmins = ParentAdmin;
				}
			}
		}
		return ForumAdmins.split(",");
	}

	/**
	 * �жϵ�ǰ��¼�û��ǲ�����Ӧ���İ����ͳ�������
	 * 
	 * @author wst
	 * @param ThemeID
	 * @return
	 */
	public static int isAdmin(String ForumID, String SiteID) {
		if (StringUtil.isNotEmpty(User.getUserName())) {

			if (User.getUserName().equals(UserList.ADMINISTRATOR)) {
				return 1;// ϵͳ����Ա
			}
			String sqlAdmin = "select count(*) from ZCForumMember a,ZCForumGroup b where a.SiteID=? and a.UserName='"
					+ User.getUserName() + "' and a.AdminID=b.ID and b.SystemName='ϵͳ����Ա'";
			int countAdmin = new QueryBuilder(sqlAdmin, SiteID).executeInt();
			if (countAdmin > 0) {
				return 1;// ϵͳ����Ա
			}
			/*
			 * String sqlSpecail = "select count(*) " +
			 * "from ZCForumMember a,ZCforumgroup b,ZCAdminGroup c,ZCforumgroup d "
			 * + "where a."
			 * +sqlSiteID+" and b."+sqlSiteID+" and c."+sqlSiteID+" and d."
			 * +sqlSiteID+ " and a.UserName='lolo' and a.userGroupID=b.ID and
			 * b.ID=c.groupID and b.RadminID=d.ID and d.SystemName='��������'" ; int
			 * countSpecail = new QueryBuilder(sqlSpecail).executeInt();
			 * if(countSpecail > 0){ return 2;//������������������� }
			 */
			String sql = "select count(*) from ZCForumMember a,ZCForumGroup b where a.SiteID=? and a.UserName='"
					+ User.getUserName() + "' and a.AdminID=b.ID and b.SystemName='��������'";
			int count = new QueryBuilder(sql, SiteID).executeInt();
			if (count > 0) {
				return 2;// ��������
			}
			String[] forumAdmins = getAdmins(ForumID);
			for (int i = 0; i < forumAdmins.length; i++) {
				if (forumAdmins[i].equals(User.getUserName())) {
					return 3;// ����
				}
			}
		}
		return 0;
	}

	/**
	 * �õ����û��Ƿ��Ǹð��İ���
	 */
	public static boolean isForumAdmin(String ForumID, String member) {
		String[] forumAdmins = getAdmins(ForumID);
		for (int i = 0; i < forumAdmins.length; i++) {
			if (forumAdmins[i].equals(member)) {
				return true;// ����
			}
		}
		return false;
	}

	public static boolean isForumAdmin(String ForumID) {
		return isForumAdmin(ForumID, User.getUserName());
	}

	/**
	 * �û��Ƿ��п��Բ��������Ȩ��
	 * 
	 * @param ForumID
	 * @param SiteID
	 * @return
	 */
	public static boolean isOperateTheme(String ForumID, String SiteID) {
		if (!User.isLogin()) {
			return false;
		}
		String sql = "select count(*) from ZCForumMember a,ZCForumGroup b where a.SiteID=? and a.UserName='"
				+ User.getUserName() + "' and a.AdminID=b.ID and b.SystemName='����'";
		int count = new QueryBuilder(sql, SiteID).executeInt();
		if (count > 0) {
			String[] forumAdmins = getAdmins(ForumID);
			for (int i = 0; i < forumAdmins.length; i++) {
				if (forumAdmins[i].equals(User.getUserName())) {
					return true;// ����
				}
			}
			return false;
		}
		return true;
	}

	public static String operateThemeButton(String ForumID, String SiteID) {
		StringBuffer sb = new StringBuffer();
		if (isOperateTheme(ForumID, SiteID)) {
			ForumPriv priv = new ForumPriv(SiteID, ForumID);
			if (priv.hasPriv("RemoveTheme")) {
				sb.append("<a href='#;' onclick='del()'>ɾ������</a>");
			}
			if (priv.hasPriv("MoveTheme")) {
				sb.append("<a href='#;' onclick='move()'>�ƶ�����</a>");
			}
			if (priv.hasPriv("BrightTheme")) {
				sb.append("<a href='#;' onclick='bright()'>����/ȡ����ʾ</a>");
			}
			if (priv.hasPriv("TopTheme")) {
				sb.append("<a href='#;' onclick='top()'>�ö�/����ö�</a>");
			}
			if (priv.hasPriv("UpOrDownTheme")) {
				sb.append("<a href='#;' onclick='upOrDown()'>����/�³�����</a>");
			}
			if (priv.hasPriv("BestTheme")) {
				sb.append("<a href='#;' onclick='best()'>��Ϊ/ȡ������</a>");
			}
		}
		return sb.toString();
	}

	/**
	 * ��ѯ���ݿ��ж��ǲ��������û���������
	 * 
	 * @author wst
	 * @param members
	 *            ��","�ָ���û����ַ���
	 * @return
	 */
	public static boolean isExistMember(String members) {
		if (!StringUtil.checkID(members)) {
			LogUtil.warn("���ܵ�SQLע��,ForumUtil.isExistMember:" + members);
			return true;
		}
		String[] ForumAdmins = members.trim().split(",");
		StringBuffer sql = new StringBuffer("select count(*) from ZDMember where UserName='" + ForumAdmins[0] + "'");
		for (int i = 1; i < ForumAdmins.length; i++) {
			sql.append(" or UserName='" + ForumAdmins[i] + "'");
		}
		int count = new QueryBuilder(sql.toString()).executeInt();
		if (count == ForumAdmins.length) {
			return true;
		}
		return false;
	}

	/**
	 * ���°��İ�������member����������
	 * 
	 * @author wst
	 * @param trans
	 * @param ForumAdmin
	 * @return
	 */
	public static void addAdminID(Transaction trans, String ForumID, String ForumAdmin) {
		String[] ForumAdmins = ForumAdmin.trim().split(",");
		ZCForumSchema forum = new ZCForumSchema();
		forum.setID(ForumID);
		forum.fill();
		checkDelAdmin(trans, ForumAdmins, forum);
		// �жϸ��°�����member�����Ѿ����ڰ������߸��ߵ���ݣ��������жϽ��и���
		long groupID = ForumCache.getGroupBySystemName(String.valueOf(getCurrentBBSSiteID()), "����").getID();
		for (int i = 0; i < ForumAdmins.length; i++) {
			ZCForumMemberSchema member = new ZCForumMemberSchema();
			member.setUserName(ForumAdmins[i]);
			member.fill();
			if (member.getAdminID() == 0) {
				member.setAdminID(groupID);
			} else {
				long id = ForumCache.getGroupBySystemName(String.valueOf(getCurrentBBSSiteID()), "��ֹ����").getID();
				if (member.getAdminID() == id) {
					member.setAdminID(groupID);
				}
			}
			trans.add(member, Transaction.UPDATE);
		}

	}

	public static String getBBSName(String SiteID) {
		ZCForumConfigSchema config = ForumCache.getConfigBySiteID(SiteID);
		if (config == null) {
			return null;
		}
		return config.getName();
	}

	/**
	 * ��ʼ��������Ȩ��
	 * 
	 * @author wst
	 * @param ForumID
	 * @return
	 */
	public static String initPriv(String ForumID, String SiteID) {
		StringBuffer sb = new StringBuffer();
		ForumPriv priv = new ForumPriv(SiteID);
		if (User.isLogin()) {
			sb.append("<a href='Logout.jsp?SiteID=" + SiteID + "'>�˳�</a>");
		}
		if (priv.hasPriv("AllowSearch")) {
			sb.append(" | <a href='ThemeSearch.jsp?SiteID=" + SiteID + "'>����</a>");
		}
		if (priv.hasPriv("AllowPanel")) {
			sb.append(" | <a href='ControlPanel.jsp?SiteID=" + SiteID + "'>�������</a> | <a href='MyThemes.jsp?SiteID="
					+ SiteID + "'>�ҵĻ���</a> ");
		}
		if (priv.hasPriv("Admin")) {
			sb.append(" | <a href='MasterPanel.jsp?SiteID=" + SiteID + "'>�����������</a>");
		}
		if (StringUtil.isNotEmpty(ForumID)) {
			ForumRule rule = new ForumRule(ForumID);
			if (rule.getRule("AllowTheme") || ForumUtil.isAdmin(ForumID, SiteID) > 0) {
				// if (priv.getPriv("AllowTheme")) { �κ�ʱ�̶�Ҫ��ʾ�����»��⣬���û��Ȩ�ޣ�������ȥע��
				sb.append(" |<a href='ThemeAdd.jsp?SiteID=" + SiteID + "&ForumID=" + ForumID + "'>�����»���</a>");
				// /}
			}
		}
		return sb.toString();
	}

	/**
	 * ���ط���
	 * 
	 * @return
	 */
	public static String initPriv(String SiteID) {
		return initPriv(null, SiteID);
	}

	/**
	 * ����������Ȩ��
	 * 
	 * @param map
	 */
	public static void adminPriv(Mapx map) {
		String SiteID = map.getString("SiteID");
		ForumPriv priv = new ForumPriv(SiteID);
		if (priv.hasPriv("AllowEditUser")) {
			map.put("AllowEditUser", "<a href='MasterPanel.jsp?SiteID=" + SiteID + "'>�༭�û�</a>");
		}
		/*
		 * if(priv.getPriv("AllowForbidUser")){ map.put("AllowForbidUser",
		 * "<a href='ForbidUser.jsp?SiteID="+SiteID+"'>��ֹ�û�</a>"); }
		 */
		if (priv.hasPriv("AllowEditForum")) {
			map.put("AllowEditForum", "<a href='ForumEdit.jsp?SiteID=" + SiteID + "'>���༭</a>");
		}
		if (priv.hasPriv("AllowVerfyPost")) {
			map.put("AllowVerfyPost", "<a href='PostAudit.jsp?SiteID=" + SiteID + "'>�������</a>");
		}
	}

	/**
	 * �����ƶ���ɾ������Ҫ�ж��ǲ��Ǹð���µ�������Ա���İ���������Ϣ
	 * 
	 * @author wst
	 * @param forum
	 * @param ids
	 */
	public static void changeLastTheme(ZCForumSchema originalForum, ZCForumSchema targetForum, String ids) {
		if (!StringUtil.checkID(ids)) {
			LogUtil.warn("���ܵ�SQLע��,ForumUtil.changeLastTheme:" + ids);
			return;
		}
		String sqlMin = "select ID from ZCTheme where status='Y' and ForumID=" + originalForum.getID() + " and ID in("
				+ ids + ") order by ID desc";
		DataTable dtMin = new QueryBuilder(sqlMin).executePagedDataTable(1, 0);
		String sqlNext = "select count(*) from ZCTheme where status='Y' and VerifyFlag='Y' and ForumID="
				+ originalForum.getID() + " and ID>" + dtMin.getString(0, "ID") + " and ID not in(" + ids + ")";
		int count = new QueryBuilder(sqlNext).executeInt();
		if (count == 0) {
			String sqlOriginal = "select Subject,AddUser,ID from ZCTheme where status='Y' and VerifyFlag='Y' and ForumID="
					+ originalForum.getID() + " and ID not in(" + ids + ") order by ID desc";
			DataTable dt = new QueryBuilder(sqlOriginal).executePagedDataTable(1, 0);
			if (dt.getRowCount() > 0) {
				originalForum.setLastPost(dt.getString(0, "Subject"));
				originalForum.setLastPoster(dt.getString(0, "AddUser"));
				originalForum.setLastThemeID(dt.getString(0, "ID"));
			} else {
				originalForum.setLastPost("");
				originalForum.setLastPoster("");
				originalForum.setLastThemeID("");
			}
		}
		if (targetForum != null) {
			String sqlTarget = "select count(*) from ZCTheme where status='Y' and VerifyFlag='Y' and ForumID="
					+ targetForum.getID() + " and ID>" + dtMin.getString(0, "ID");
			int countTarget = new QueryBuilder(sqlTarget).executeInt();
			if (countTarget == 0) {
				String sql = "select Subject,AddUser,ID from ZCTheme where ID=" + dtMin.getString(0, "ID");
				DataTable dt = new QueryBuilder(sql).executePagedDataTable(1, 0);
				if (dt.getRowCount() > 0) {
					targetForum.setLastPost(dt.getString(0, "Subject"));
					targetForum.setLastPoster(dt.getString(0, "AddUser"));
					targetForum.setLastThemeID(dt.getString(0, "ID"));
				} else {
					targetForum.setLastPost("");
					targetForum.setLastPoster("");
					targetForum.setLastThemeID("");
				}
			}
		}
	}

	public static void changeLastTheme(ZCForumSchema originalForum, String ids) {
		changeLastTheme(originalForum, null, ids);
	}

	/**
	 * �õ���ǰ��վ��ID
	 * 
	 * @return
	 */
	public static long getCurrentBBSSiteID() {
		if ("Y".equalsIgnoreCase(Config.getValue("PublicUseBBS"))) {
			return 0;
		} else {
			int count = new QueryBuilder("select ID from ZCSite").executeInt();
			if (count > 0) {
				return Application.getCurrentSiteID();
			} else {
				return 0;
			}
		}
	}

	/**
	 * ǰ̨�õ���ǰ��վ��ID
	 * 
	 * @return
	 */
	public static String getCurrentBBSSiteID(String SiteID) {
		if ("Y".equalsIgnoreCase(Config.getValue("PublicUseBBS"))) {
			SiteID = "0";
		} else {
			if (StringUtil.isNotEmpty(User.getUserName())) {
				if (User.isManager() && User.isLogin()) {
					SiteID = ForumUtil.getCurrentBBSSiteID() + "";
				} else {
					if (!UserList.ADMINISTRATOR.equals(User.getUserName())) {
						SiteID = new QueryBuilder("select SiteID from ZCForumMember where UserName='"
								+ User.getUserName() + "'").executeString();
					}
				}
			} else {
				if (StringUtil.isEmpty(SiteID)) {
					String sql = "select ID from ZCSite";
					DataTable dt = new QueryBuilder(sql).executePagedDataTable(1, 0);
					if (dt.getRowCount() > 0) {
						SiteID = dt.getString(0, "ID");
					} else {
						SiteID = "0";
					}
				}
			}
		}
		return SiteID;
	}

	/**
	 * �ж��û�����Ӧ������ǲ��ǲ��ܷ���
	 * 
	 * @param SiteID
	 * @param ForumID
	 * @return
	 */
	public static boolean isNotSendTheme(String SiteID, String ForumID) {
		return !isSendTheme(SiteID, ForumID);
	}

	/**
	 * ���û�����Ӧ������ǲ����ܷ���
	 * 
	 * @param SiteID
	 * @param ForumID
	 * @return
	 */
	public static boolean isSendTheme(String SiteID, String ForumID) {
		ForumPriv priv = new ForumPriv(SiteID);
		ForumRule rule = new ForumRule(ForumID);
		if (rule.getRule("AllowTheme") || isAdmin(ForumID, SiteID) > 0) {
			if (priv.hasPriv("AllowTheme")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * �ж��û�����Ӧ������ǲ��ǲ��ܻظ�
	 * 
	 * @param SiteID
	 * @param ForumID
	 * @return
	 */
	public static boolean isNotReplyPost(String SiteID, String ForumID) {
		return !isReplyPost(SiteID, ForumID);
	}

	/**
	 * �ж��û�����Ӧ������ǲ����ܻظ�
	 * 
	 * @param SiteID
	 * @param ForumID
	 * @return
	 */
	public static boolean isReplyPost(String SiteID, String ForumID) {
		ForumPriv priv = new ForumPriv(SiteID);
		ForumRule rule = new ForumRule(ForumID);
		if (rule.getRule("ReplyPost") || isAdmin(ForumID, SiteID) > 0) {
			if (priv.hasPriv("AllowReply")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * �ж��û�����Ӧ������ǲ����ܱ༭
	 * 
	 * @param SiteID
	 * @param ForumID
	 * @param UserName
	 * @return
	 */
	public static boolean isEditPost(String SiteID, String ForumID, String UserName) {
		ForumPriv priv = new ForumPriv(SiteID);
		ForumRule rule = new ForumRule(ForumID);
		if (isAdmin(ForumID, SiteID) > 0 && priv.hasPriv("AllowEdit")) {
			return true;
		} else if (rule.getRule("EditPost")) {
			if (StringUtil.isNotEmpty(User.getUserName()) && User.getUserName().equals(UserName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * �ҵ�member������set�е��������粻���ڷ���-1;
	 * 
	 * @param memberSet
	 * @param member
	 * @return
	 */
	public static int getValueOfMemberSet(ZCForumMemberSet memberSet, ZCForumMemberSchema member) {
		for (int j = 0; j < memberSet.size(); j++) {
			if (member.getUserName().equals(memberSet.get(j).getUserName())) {
				return j;
			}
		}
		return -1;
	}

	/**
	 * �ҵ�forum������set�е��������粻���ڷ���-1;
	 * 
	 * @param memberSet
	 * @param member
	 * @return
	 */
	public static int getValueOfForumSet(ZCForumSet forumSet, ZCForumSchema forum) {
		for (int j = 0; j < forumSet.size(); j++) {
			if (forum.getID() == forumSet.get(j).getID()) {
				return j;
			}
		}
		return -1;
	}

	/**
	 * �ҵ�theme������set�е��������粻���ڷ���-1;
	 * 
	 * @param memberSet
	 * @param member
	 * @return
	 */
	public static int getValueOfThemeSet(ZCThemeSet themeSet, ZCThemeSchema theme) {
		for (int j = 0; j < themeSet.size(); j++) {
			if (theme.getID() == themeSet.get(j).getID()) {
				return j;
			}
		}
		return -1;
	}

	/**
	 * ��鱻ɾ���İ���������������ǲ��ǰ���������������ڰѸ��û���member���µ�AdminID��Ϊ0�������¸��û����û���
	 * 
	 * @author wst
	 * @param ForumAdmins
	 * @param forum
	 */
	private static void checkDelAdmin(Transaction trans, String[] ForumAdmins, ZCForumSchema forum) {
		String admins = StringUtil.isEmpty(forum.getForumAdmin()) ? "" : forum.getForumAdmin();
		String[] oldAdmins = admins.split(",");
		String checkAdmin = "";
		Loop: for (int i = 0; i < oldAdmins.length; i++) {
			for (int j = 0; j < ForumAdmins.length; j++) {
				if (oldAdmins[i].equals(ForumAdmins[j])) {
					continue Loop;
				}
			}
			checkAdmin += oldAdmins[i] + ",";
		}
		if (checkAdmin.length() == 0) {
			return;
		}
		if (!StringUtil.checkID(checkAdmin)) {
			LogUtil.warn("���ܵ�SQLע��,ForumUtil.checkDelAdmin:" + checkAdmin);
			return;
		}
		String[] checkAdmins = checkAdmin.split(",");
		for (int i = 0; i < checkAdmins.length; i++) {
			String sql = "select count(*) from ZCForum where ForumAdmin like '%" + checkAdmins[i] + "," + "%'";
			int count = new QueryBuilder(sql).executeInt();
			if (count == 1) {
				ZCForumMemberSchema member = new ZCForumMemberSchema();
				member.setUserName(checkAdmins[i]);
				member.fill();
				QueryBuilder qb = new QueryBuilder("select SystemName from ZCForumGroup where ID=?", member
						.getAdminID());
				DataTable dtType = qb.executeDataTable();
				if (dtType.getString(0, "SystemName").equals("��������")
						|| dtType.getString(0, "SystemName").equals("ϵͳ����Ա")) {
					return;
				}
				member.setAdminID(0);
				// userGroupChange(member);
				trans.add(member, Transaction.UPDATE);
			}
		}
	}

	/**
	 * ��õ�ǰ��̳������
	 */
	public static String getCurrentName(HttpServletRequest request) {
		String siteID = request.getParameter("SiteID");
		if (StringUtil.isEmpty(siteID)) {
			siteID = request.getParameter("site");
			if (StringUtil.isEmpty(siteID)) {
				siteID = (String) User.getValue("SiteID");
				if (StringUtil.isEmpty(siteID)) {
					return null;
				}
			}
		}
		Mapx map = CacheManager.getMapx("Forum", "Config");
		Object[] vs = map.valueArray();
		for (int i = 0; i < vs.length; i++) {
			ZCForumConfigSchema config = (ZCForumConfigSchema) vs[i];
			if (config.getSiteID() == Long.parseLong(siteID)) {
				return config.getName();
			}
		}
		return "";
	}

}
