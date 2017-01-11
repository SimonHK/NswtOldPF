package com.nswt.bbs;

import com.nswt.framework.Ajax;
import com.nswt.framework.User;
import com.nswt.framework.cache.CacheManager;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.HtmlUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.schema.ZCForumGroupSchema;
import com.nswt.schema.ZCForumMemberSchema;
import com.nswt.schema.ZCForumSchema;

public class MasterPanel extends Ajax {
	public static Mapx init(Mapx params) {
		params.put("Priv", ForumUtil.initPriv(params.getString("SiteID")));
		ForumUtil.adminPriv(params);
		params.put("BBSName", ForumUtil.getBBSName(params.getString("SiteID")));
		return params;
	}

	public void perInfoSave() {
		ZCForumMemberSchema forumUser = new ZCForumMemberSchema();
		forumUser.setUserName($V("UserName"));
		forumUser.fill();
		forumUser.setNickName($V("NickName"));

		Transaction trans = new Transaction();
		trans.add(forumUser, Transaction.UPDATE);
		if (trans.commit()) {
			Response.setLogInfo(1, "�޸ĳɹ�!");
		} else {
			Response.setLogInfo(0, "�޸�ʧ��!");
		}
	}

	public static Mapx searchUserInit(Mapx params) {

		String userName = params.getString("UserName");
		if (StringUtil.isNotEmpty(userName) && !ForumUtil.isExistMember(userName)) {
			params.remove("UserName");
			params.put("Message", "�û��������ڣ�");
			return params;
		}

		if (StringUtil.isNotEmpty(userName)) {
			if (ForumUtil.isOperateMember(userName)) {
				ZCForumMemberSchema member = new ZCForumMemberSchema();
				member.setUserName(userName);
				member.fill();
				params.putAll(member.toMapx());
			} else {
				params.remove("UserName");
				params.put("Message", "����Ȩ�༭���û���");
			}
		}

		return params;
	}

	public void changeForum() {
		String forumID = $V("ForumID");
		if (StringUtil.isEmpty(forumID)) {
			Response.put("Info", "");
			return;
		}
		ZCForumSchema forum = new ZCForumSchema();
		forum.setID(forumID);

		if (forum.fill()) {
			Response.put("Info", forum.getInfo());
		} else {
			Response.setLogInfo(0, "�ð�鲻����!");
		}
	}

	public static Mapx forumEditInit(Mapx params) {
		ZCForumMemberSchema member = new ZCForumMemberSchema();
		ZCForumGroupSchema group = new ZCForumGroupSchema();
		member.setUserName(User.getUserName());
		member.fill();
		group.setID(member.getAdminID());
		group.fill();
		DataTable dt = null;
		if (group.getSystemName().equals("��������")) {
			dt = new QueryBuilder("select Name, ID from ZCForum where SiteID=?", params.getString("SiteID"))
					.executeDataTable();

		} else {
			QueryBuilder qb = new QueryBuilder("select Name,ID from zcforum where SiteID=? and (forumadmin like '%"
					+ User.getUserName()
					+ ",%' and ParentID<>0) or ParentID in (select ID from zcforum where SiteID=? "
					+ " and forumadmin like '%" + User.getUserName() + ",%' and ParentID=0)", params
					.getString("SiteID"), params.getString("SiteID"));
			dt = qb.executeDataTable();
		}

		params.put("ForumOptions", HtmlUtil.dataTableToOptions(dt));
		return params;
	}

	public void editInfoSave() {
		ZCForumMemberSchema member = new ZCForumMemberSchema();

		member.setUserName($V("UName"));
		member.fill();
		member.setNickName($V("NickName"));
		member.setForumSign($V("ForumSign"));

		Transaction trans = new Transaction();
		trans.add(member, Transaction.UPDATE);
		if (trans.commit()) {
			Response.setLogInfo(1, "�޸ĳɹ�!");
		} else {
			Response.setLogInfo(0, "�޸�ʧ��!");
		}
	}

	public void editForumSave() {
		ZCForumSchema forum = new ZCForumSchema();

		forum.setID($V("ForumID"));
		forum.fill();
		forum.setInfo($V("Info"));

		Transaction trans = new Transaction();
		trans.add(forum, Transaction.UPDATE);
		if (trans.commit()) {
			CacheManager.set("Forum", "Forum", forum.getID(), forum);
			Response.setLogInfo(1, "�޸ĳɹ�!");
		} else {
			Response.setLogInfo(0, "�޸�ʧ��!");
		}
	}
}
