package com.nswt.bbs.admin;

import com.nswt.bbs.ForumUtil;
import com.nswt.framework.Page;
import com.nswt.framework.controls.DataGridAction;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.HtmlUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.UserList;
import com.nswt.schema.ZCForumGroupSchema;
import com.nswt.schema.ZCForumMemberSchema;

public class ForumUserInfo extends Page {

	public static Mapx init(Mapx params) {
		long SiteID = ForumUtil.getCurrentBBSSiteID();
		DataTable dt = new QueryBuilder("select Name,ID from ZCForumGroup where SiteID=? and type=?", SiteID,
				ForumUserGroup.USERGROUP).executeDataTable();
		params.put("UserGroup", HtmlUtil.dataTableToOptions(dt));

		DataTable dtSystem = new QueryBuilder("select Name,ID from ZCForumGroup where SiteID=? and type=?", SiteID,
				ForumUserGroup.SYSTEMGROUP).executeDataTable();

		params.put("UserSystemGroup", HtmlUtil.dataTableToOptions(dtSystem));

		DataTable dtDefined = new QueryBuilder("select Name,ID from ZCForumGroup where SiteID=? and type=?", SiteID,
				ForumUserGroup.DEFINEDGROUP).executeDataTable();

		params.put("UserDefinedGroup", HtmlUtil.dataTableToOptions(dtDefined));
		return params;
	}

	public static void getList(DataGridAction dga) {
		String UserGroupID = dga.getParam("UserGroup");
		String UserSystemGroup = dga.getParam("UserSystemGroup");
		String UserDefinedGroup = dga.getParam("UserDefinedGroup");
		String LowerScore = dga.getParam("LowerScore");
		String UpperScore = dga.getParam("UpperScore");
		String UserName = dga.getParam("ForumUserName");
		long SiteID = ForumUtil.getCurrentBBSSiteID();

		QueryBuilder qb = new QueryBuilder(
				"select a.UserName,b.Name,c.Name AdminName,d.Name DefinedName,a.ThemeCount,a.ForumScore "
						+ "from ZCForumMember a join ZCForumGroup b on(b.ID=a.UserGroupID)"
						+ " left join ZCForumGroup c on(a.AdminID=c.ID) left join ZCForumGroup d on(a.DefinedID=d.ID) "
						+ "where a.SiteID=?", SiteID);

		if (StringUtil.isNotEmpty(UserName)) {
			qb.append(" and UserName like ?", "%" + UserName + "%");
		}
		if (StringUtil.isNotEmpty(LowerScore)) {
			qb.append(" and ForumScore>=?", LowerScore);
		}
		if (StringUtil.isNotEmpty(UpperScore)) {
			qb.append(" and ForumScore<=?", UpperScore);
		}
		if (StringUtil.isNotEmpty(UserSystemGroup) && !UserSystemGroup.equals("0")) {
			qb.append(" and a.AdminID=?", UserSystemGroup);
		}
		if (StringUtil.isNotEmpty(UserDefinedGroup) && !UserDefinedGroup.equals("0")) {
			qb.append(" and a.DefinedID=?", UserDefinedGroup);
		}
		if (StringUtil.isNotEmpty(UserGroupID) && !UserGroupID.equals("0")) {
			qb.append(" and a.UserGroupID=?", UserGroupID);
		}
		dga.bindData(qb);

	}

	public static Mapx initEditDialog(Mapx params) {
		long SiteID = ForumUtil.getCurrentBBSSiteID();
		ZCForumMemberSchema member = new ZCForumMemberSchema();
		String userName = params.getString("UserName");
		member.setUserName(userName);
		member.fill();
		ZCForumGroupSchema group = new ZCForumGroupSchema();
		group.setID(member.getUserGroupID());
		group.fill();
		params.put("UserGroup", group.getName());
		QueryBuilder qb1 = new QueryBuilder("select Name,ID from ZCForumGroup where SiteID=? and Type=?", SiteID,
				ForumUserGroup.DEFINEDGROUP);
		QueryBuilder qb2 = new QueryBuilder("select Name,ID from ZCForumGroup where SiteID=?"
				+ " and (SystemName='����' or SystemName='��������' or SystemName='��ֹ����')", SiteID);
		DataTable dtDefinedGroup = qb1.executeDataTable();
		DataTable dtSystemGroup = qb2.executeDataTable();
		params.put("DefinedGroup", HtmlUtil.dataTableToOptions(dtDefinedGroup, member.getDefinedID() + ""));
		params.put("SystemGroup", HtmlUtil.dataTableToOptions(dtSystemGroup, member.getAdminID() + ""));

		DataTable dt1 = new QueryBuilder("select Name from ZCForum where  Forumadmin like ?", "%" + userName + ",%")
				.executeDataTable();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < dt1.getRowCount(); i++) {
			sb.append(dt1.get(i, "Name"));
			if (i != dt1.getRowCount() - 1) {
				sb.append(",");
			}
		}
		params.put("Forums", sb.toString());
		params.put("ForumAdminID", dtSystemGroup.getString(0, "ID"));
		params.put("ForumVisit", dtSystemGroup.getString(2, "ID"));
		params.put("ThemeCount", member.getThemeCount());
		params.put("ForumScore", member.getForumScore());

		return params;
	}

	public void editUserGroup() {
		if ($V("UserName").equals(UserList.ADMINISTRATOR)) {
			Response.setLogInfo(0, "ϵͳ����Ա���ܸ��ģ�");
			return;
		}
		long SiteID = ForumUtil.getCurrentBBSSiteID();
		Transaction trans = new Transaction();
		ZCForumMemberSchema member = new ZCForumMemberSchema();
		member.setUserName($V("UserName"));
		member.fill();
		member.setForumScore($V("ForumScore"));
		member.setThemeCount($V("ThemeCount"));
		member.setDefinedID($V("DefinedID"));
		if (!$V("AdminID").equals("0")) {
			member.setAdminID($V("AdminID"));
		} else {
			int count = new QueryBuilder("select count(*) from ZCForum where ForumAdmin like ?", "%"
					+ member.getUserName() + ",%").executeInt();
			if (count > 0) {
				long adminID = new QueryBuilder("select ID from ZCForumGroup where SystemName='����' and SiteID=?",
						SiteID).executeLong();
				member.setAdminID(adminID);
			} else {
				member.setAdminID(0);
			}
		}

		ForumUtil.userGroupChange(member);
		trans.add(member, Transaction.UPDATE);
		if (trans.commit()) {
			Response.setLogInfo(1, "�����ɹ�");
		} else {
			Response.setLogInfo(0, "����ʧ��");
		}
	}
}
