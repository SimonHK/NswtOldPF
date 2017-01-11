package com.nswt.bbs;

import java.util.Date;

import com.nswt.bbs.admin.ForumScore;
import com.nswt.framework.Ajax;
import com.nswt.framework.User;
import com.nswt.framework.cache.CacheManager;
import com.nswt.framework.controls.DataListAction;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.DateUtil;
import com.nswt.framework.utility.HtmlUtil;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.UserLog;
import com.nswt.schema.ZCForumMemberSchema;
import com.nswt.schema.ZCForumMemberSet;
import com.nswt.schema.ZCForumSchema;
import com.nswt.schema.ZCPostSchema;
import com.nswt.schema.ZCPostSet;
import com.nswt.schema.ZCThemeSchema;
import com.nswt.schema.ZCThemeSet;

public class Theme extends Ajax {

	/**
	 * ������ʵ�������б������
	 * 
	 * @param dga
	 */
	public static void getList(DataListAction dla) {
		String postType = dla.getParam("postType");
		String addTime = dla.getParam("addtime");
		String orderBy = dla.getParam("orderby");
		String ascdesc = dla.getParam("ascdesc");
		String ForumID = dla.getParam("ForumID");
		String SiteID = dla.getParam("SiteID");

		QueryBuilder qb = new QueryBuilder("select * from ZCTheme where Status='Y' and VerifyFlag='Y' and ForumID=?",
				ForumID);

		if (!StringUtil.isEmpty(addTime) && !"0".equals(addTime)) {
			Date date = new Date(System.currentTimeMillis() - Long.parseLong(addTime));
			qb.append(" and addTime >?", date);
		}

		if (!StringUtil.isEmpty(postType)) {
			if (postType.equals("best")) {
				qb.append(" and Best='Y'");
			}
		}

		if (!StringUtil.isEmpty(orderBy)) {
			if (!StringUtil.checkID(orderBy)) {
				LogUtil.warn("���ܵ�SQLע��,Theme.getList:" + orderBy);
				return;
			}
			qb.append(" order by TopTheme desc," + orderBy);
			if (!StringUtil.isEmpty(ascdesc)) {
				if ("DESC".equals(ascdesc))
					qb.append(" desc ");
			}
		} else {
			qb.append(" order by TopTheme desc,OrderTime desc");
		}
		DataTable dt = qb.executePagedDataTable(dla.getPageSize(), dla.getPageIndex());
		String[] types = { "ԭ��", "ͶƱ", "�", "��Ʒ" };
		for (int i = 0; i < dt.getRowCount(); i++) {
			if (dt.get(i, "TopTheme").equals("Y")) {
				dt.set(i, "TopTheme", "<img src='Images/top.gif' />");
			} else {
				dt.set(i, "TopTheme", "");
			}
			if (dt.get(i, "Best").equals("Y")) {
				dt.set(i, "Best", "<img src='Images/best.gif' />");
			} else {
				dt.set(i, "Best", "");
			}
			dt.set(i, "type", types[Integer.parseInt(dt.getString(i, "Type"))]);
		}
		dt.insertColumn("Box");
		if (ForumUtil.operateThemeButton(ForumID, SiteID).length() == 0) {
			for (int i = 0; i < dt.getRowCount(); i++) {
				dt.set(i, "Box", "none");
			}
		}
		dla.setTotal(qb);
		dla.bindData(dt);
	}

	public static Mapx init(Mapx params) {
		String SiteID = params.getString("SiteID");
		String ForumID = params.getString("ForumID");
		ZCForumSchema forum = ForumCache.getForum(ForumID);
		Mapx map = forum.toMapx();
		map.put("SiteID", SiteID);
		map.put("AddUser", User.getUserName());
		map.put("Operate", ForumUtil.operateThemeButton(ForumID, SiteID));
		map.put("Priv", ForumUtil.initPriv(ForumID, SiteID));

		if (ForumUtil.isSendTheme(SiteID, ForumID)) {
			map.put("NewThemeButton", "<a href='ThemeAdd.jsp?ForumID=" + ForumID + "&SiteID=" + SiteID + "'>�����»���</a>");
		}
		if (ForumUtil.operateThemeButton(ForumID, SiteID).length() == 0) {
			map.put("box", "none");
		}
		map.put("BBSName", ForumUtil.getBBSName(SiteID));
		return map;
	}

	public void del() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			LogUtil.warn("���ܵ�SQLע��,Theme.del:" + ids);
			return;
		}
		ForumScore forumScore = new ForumScore($V("SiteID"));
		ZCThemeSet set = new ZCThemeSchema().query(new QueryBuilder("where ID in (" + ids + ")"));
		ZCForumMemberSet userSet = new ZCForumMemberSet();
		ZCForumSchema forum = ForumCache.getForum(set.get(0).getForumID());
		Transaction trans = new Transaction();
		// ��־
		StringBuffer themeLog = new StringBuffer("ɾ�����⣺");
		for (int i = 0; i < set.size(); i++) {
			ZCForumMemberSchema user = new ZCForumMemberSchema();
			user.setUserName(set.get(i).getAddUser());
			int index = ForumUtil.getValueOfMemberSet(userSet, user);
			set.get(i).setStatus("N");
			if (index == -1) {
				user.fill();
				user.setForumScore(user.getForumScore() + forumScore.DeleteTheme);
				userSet.add(user);
			} else {
				userSet.get(index).setForumScore(userSet.get(index).getForumScore() + forumScore.DeleteTheme);
			}
			themeLog.append(set.get(i).getSubject() + ",");
		}
		forum.setThemeCount(forum.getThemeCount() - set.size());
		forum.setPostCount(forum.getPostCount() - set.size());
		ForumUtil.changeLastTheme(forum, ids);
		// ɾ��POST���и������µ����лظ�
		ZCPostSet postSet = new ZCPostSchema().query(new QueryBuilder("where ThemeID in (" + ids + ")"));
		for (int i = 0; i < postSet.size(); i++) {
			postSet.get(i).setInvisible("N");
		}

		ForumUtil.userGroupChange(userSet);
		trans.add(forum, Transaction.UPDATE);
		trans.add(userSet, Transaction.UPDATE);
		trans.add(set, Transaction.UPDATE);
		trans.add(postSet, Transaction.UPDATE);
		if (trans.commit()) {
			CacheManager.set("Forum", "Forum", forum.getID(), forum);
			UserLog.log(UserLog.FORUM, UserLog.FORUM_DELTHEME, themeLog + "�ɹ�", Request.getClientIP());
			Response.setLogInfo(1, "ɾ���ɹ�");
		} else {
			UserLog.log("UserLog.FORUM", UserLog.FORUM_DELTHEME, themeLog + "ʧ��", Request.getClientIP());
			Response.setLogInfo(0, "����ʧ��");
		}
	}

	public static Mapx initMoveDialog(Mapx params) {
		String ForumID = params.getString("ForumID");
		QueryBuilder qb = new QueryBuilder("select Name,ID from ZCForum where ParentID<>0 and SiteID=?", params
				.getString("SiteID"));
		DataTable dt = qb.executeDataTable();
		params.put("Forum", HtmlUtil.dataTableToOptions(dt, ForumID));
		return params;

	}

	public void move() {
		Transaction trans = new Transaction();
		ZCForumSchema originalForum = new ZCForumSchema();
		ZCForumSchema targetForum = new ZCForumSchema();
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			LogUtil.warn("���ܵ�SQLע��,Theme.move:" + ids);
			return;
		}
		ZCThemeSet themeSet = new ZCThemeSchema().query(new QueryBuilder("where ID in (" + ids + ")"));
		ZCPostSet postSet = new ZCPostSchema().query(new QueryBuilder("where ThemeID in(" + ids + ")"));
		originalForum = ForumCache.getForum(themeSet.get(0).getForumID());
		// ��־
		StringBuffer themeLog = new StringBuffer("�ƶ����⣺");
		for (int i = 0; i < themeSet.size(); i++) {
			themeSet.get(i).setForumID($V("ForumID"));
			themeLog.append(themeSet.get(i).getSubject() + ",");
		}
		for (int i = 0; i < postSet.size(); i++) {
			postSet.get(i).setForumID(themeSet.get(i).getForumID());
		}
		targetForum = ForumCache.getForum($V("ForumID"));
		ForumUtil.changeLastTheme(originalForum, targetForum, ids);
		originalForum.setThemeCount(originalForum.getThemeCount() - themeSet.size());
		originalForum.setPostCount(originalForum.getPostCount() - themeSet.size());
		targetForum.setThemeCount(targetForum.getThemeCount() + themeSet.size());
		targetForum.setPostCount(targetForum.getPostCount() + themeSet.size());
		trans.add(originalForum, Transaction.UPDATE);
		trans.add(targetForum, Transaction.UPDATE);
		trans.add(postSet, Transaction.UPDATE);
		trans.add(themeSet, Transaction.UPDATE);
		if (trans.commit()) {
			CacheManager.set("Forum", "Forum", originalForum.getID(), originalForum);
			CacheManager.set("Forum", "Forum", targetForum.getID(), targetForum);
			UserLog.log(UserLog.FORUM, UserLog.FORUM_MOVETHEME, themeLog + "�ɹ�", Request.getClientIP());
			Response.setLogInfo(1, "�ƶ��ɹ�");
		} else {
			UserLog.log(UserLog.FORUM, UserLog.FORUM_MOVETHEME, themeLog + "ʧ��", Request.getClientIP());
			Response.setLogInfo(0, "����ʧ��");
		}
	}

	/**
	 * ������ɫѡ��ҳ���ʼ��
	 * 
	 * @param params
	 * @return
	 */
	public static Mapx initBrightDialog(Mapx params) {
		Mapx map = new Mapx();
		map.put("blue", "��ɫ");
		map.put("yellow", "��ɫ");
		map.put("green", "��ɫ");
		map.put("red", "��ɫ");
		params.put("Color", HtmlUtil.mapxToRadios("Color", map));
		return params;
	}

	/**
	 * �����ӵĸ�����ʾ��ɫ�������ݿ�
	 * 
	 */
	public void brightSave() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			LogUtil.warn("���ܵ�SQLע��,Theme.brightSave:" + ids);
			return;
		}
		ForumScore forumScore = new ForumScore($V("SiteID"));
		ZCThemeSet set = new ZCThemeSchema().query(new QueryBuilder("where ID in (" + ids + ")"));
		ZCForumMemberSet userSet = new ZCForumMemberSet();
		Transaction trans = new Transaction();
		// ��־
		StringBuffer themeLog = new StringBuffer("���û�ȡ��������ʾ���⣺");
		for (int i = 0; i < set.size(); i++) {
			ZCForumMemberSchema user = new ZCForumMemberSchema();
			user.setUserName(set.get(i).getAddUser());
			// �ж��û��ǲ����Ѿ�������userSet�У�������ڽ����������Ѿ����ڵ�user��
			int index = ForumUtil.getValueOfMemberSet(userSet, user);
			if (index == -1) {
				user.fill();
				if (StringUtil.isEmpty(set.get(i).getBright()) && StringUtil.isNotEmpty($V("Color"))) {
					user.setForumScore(user.getForumScore() + forumScore.Bright);
				}
				userSet.add(user);
			} else {
				if (StringUtil.isEmpty(set.get(i).getBright()) && StringUtil.isNotEmpty($V("Color"))) {
					userSet.get(index).setForumScore(userSet.get(index).getForumScore() + forumScore.Bright);
				}
			}

			set.get(i).setBright($V("Color"));
			themeLog.append(set.get(i).getSubject() + ",");
		}
		ForumUtil.userGroupChange(userSet);
		trans.add(userSet, Transaction.UPDATE);
		trans.add(set, Transaction.UPDATE);
		if (trans.commit()) {
			UserLog.log(UserLog.FORUM, UserLog.FORUM_BRIGHTTHEME, themeLog + "�ɹ�", Request.getClientIP(), trans);
			Response.setLogInfo(1, "�����ɹ�");
		} else {
			UserLog.log(UserLog.FORUM, UserLog.FORUM_BRIGHTTHEME, themeLog + "ʧ��", Request.getClientIP(), trans);
			Response.setLogInfo(0, "����ʧ��");
		}
	}

	/**
	 * ѡ������/�³�ҳ��ĳ�ʼ��
	 * 
	 * @param params
	 * @return
	 */
	public static Mapx initUpOrDownDialog(Mapx params) {
		Mapx map = new Mapx();
		map.put("up", "����");
		map.put("down", "�³�");
		params.put("UpOrDown", HtmlUtil.mapxToRadios("UpOrDown", map));
		return params;
	}

	/**
	 * ����/�³�
	 * 
	 */
	public void upOrDownSave() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			LogUtil.warn("���ܵ�SQLע��,Theme.upOrDownSave:" + ids);
			return;
		}
		ForumScore forumScore = new ForumScore($V("SiteID"));
		ZCThemeSet set = new ZCThemeSchema().query(new QueryBuilder("where ID in (" + ids + ")"));
		ZCForumMemberSet userSet = new ZCForumMemberSet();
		Transaction trans = new Transaction();
		// ��־
		StringBuffer themeLog = new StringBuffer();
		for (int i = 0; i < set.size(); i++) {
			if (StringUtil.isEmpty($V("UpOrDown"))) {
				Response.setLogInfo(0, "��δѡ���ѡ��");
				return;
			}
			// �ж��û��ǲ����Ѿ�������userSet�У�������ڽ����������Ѿ����ڵ�user��
			ZCForumMemberSchema user = new ZCForumMemberSchema();
			user.setUserName(set.get(i).getAddUser());
			int index = ForumUtil.getValueOfMemberSet(userSet, user);
			if ($V("UpOrDown").equals("up")) {
				set.get(i).setOrderTime(new Date());
				if (index == -1) {
					user.fill();
					QueryBuilder qb = new QueryBuilder(
							"select ID from ZCTheme where ForumID=? order by OrderTime desc", set.get(i).getForumID());
					DataTable dt = qb.executePagedDataTable(1, 0);
					if (!(dt.getInt(0, 0) == set.get(i).getID())) {
						user.setForumScore(user.getForumScore() + forumScore.UpTheme);
					}

					userSet.add(user);
				} else {
					userSet.get(index).setForumScore(userSet.get(index).getForumScore() + forumScore.UpTheme);
				}

			} else if ($V("UpOrDown").equals("down")) {
				QueryBuilder qb = new QueryBuilder("select OrderTime from ZCTheme where ForumID=?"
						+ " order by OrderTime", set.get(i).getForumID());
				DataTable dt = qb.executePagedDataTable(1, 0);
				long time = DateUtil.parseDateTime(dt.getString(0, "OrderTime")).getTime() - 1000;
				set.get(i).setOrderTime(new Date(time));

				if (index == -1) {
					user.fill();
					user.setForumScore(user.getForumScore() + forumScore.DownTheme);
					userSet.add(user);
				} else {
					userSet.get(index).setForumScore(userSet.get(index).getForumScore() + forumScore.DownTheme);
				}
			}
			themeLog.append(set.get(i).getSubject() + ",");
		}

		ForumUtil.userGroupChange(userSet);
		trans.add(userSet, Transaction.UPDATE);
		trans.add(set, Transaction.UPDATE);
		if (trans.commit()) {
			if ($V("UpOrDown").equals("up")) {
				UserLog.log(UserLog.FORUM, UserLog.FORUM_UPTHEME, "�������⣺" + themeLog + "�ɹ�", Request.getClientIP());
			} else {
				UserLog.log(UserLog.FORUM, UserLog.FORUM_DOWNTHEME, "�³����⣺" + themeLog + "�ɹ�", Request.getClientIP());
			}
			Response.setLogInfo(1, "�����ɹ�");
		} else {
			if ($V("UpOrDown").equals("down")) {
				UserLog.log(UserLog.FORUM, UserLog.FORUM_UPTHEME, "�������⣺" + themeLog + "ʧ��", Request.getClientIP());
			} else {
				UserLog.log(UserLog.FORUM, UserLog.FORUM_DOWNTHEME, "�³����⣺" + themeLog + "ʧ��", Request.getClientIP());
			}
			Response.setLogInfo(0, "����ʧ��");
		}
	}

	public static Mapx initTopThemeDialog(Mapx params) {
		Mapx map = new Mapx();
		map.put("top", "�ö�");
		map.put("back", "����ö�");
		params.put("TopTheme", HtmlUtil.mapxToRadios("TopTheme", map));
		return params;
	}

	/**
	 * �ö�����
	 * 
	 */
	public void topTheme() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			LogUtil.warn("���ܵ�SQLע��,Theme.topTheme:" + ids);
			return;
		}
		ForumScore forumScore = new ForumScore($V("SiteID"));
		Transaction trans = new Transaction();
		ZCThemeSet set = new ZCThemeSchema().query(new QueryBuilder("where ID in (" + ids + ")"));
		ZCForumMemberSet userSet = new ZCForumMemberSet();
		// ��־
		StringBuffer themeLog = new StringBuffer();
		Loop: for (int i = 0; i < set.size(); i++) {
			if (StringUtil.isEmpty($V("TopTheme"))) {
				Response.setLogInfo(0, "��δѡ���ѡ��");
				return;
			}
			// �ж��û��ǲ����Ѿ�������userSet�У�������ڽ����������Ѿ����ڵ�user��
			ZCForumMemberSchema user = new ZCForumMemberSchema();
			user.setUserName(set.get(i).getAddUser());
			int index = ForumUtil.getValueOfMemberSet(userSet, user);
			if ($V("TopTheme").equals("top")) {
				if (index == -1) {
					user.fill();
					if (set.get(i).getTopTheme().equals("Y")) {
						continue Loop;
					}
					user.setForumScore(user.getForumScore() + forumScore.TopTheme);
					userSet.add(user);
				} else {
					userSet.get(index).setForumScore(userSet.get(index).getForumScore() + forumScore.TopTheme);
				}
				set.get(i).setTopTheme("Y");
			} else if ($V("TopTheme").equals("back")) {
				if (index == -1) {
					user.fill();
					if (set.get(i).getTopTheme().equals("N")) {
						continue Loop;
					}
					user.setForumScore(user.getForumScore() + forumScore.CancelTop);
					userSet.add(user);
				} else {
					userSet.get(index).setForumScore(userSet.get(index).getForumScore() + forumScore.CancelTop);
				}
				set.get(i).setTopTheme("N");
			}
			themeLog.append(set.get(i).getSubject() + ",");
		}
		ForumUtil.userGroupChange(userSet);
		trans.add(userSet, Transaction.UPDATE);
		trans.add(set, Transaction.UPDATE);
		if (trans.commit()) {
			if ($V("TopTheme").equals("top")) {
				UserLog.log("Forum", "TopTheme", "�ö����⣺" + themeLog + "�ɹ�", Request.getClientIP());
			} else {
				UserLog.log("Forum", "CancelTop", "ȡ���ö���" + themeLog + "�ɹ�", Request.getClientIP());
			}
			Response.setLogInfo(1, "�����ɹ�");
		} else {
			if ($V("TopTheme").equals("back")) {
				UserLog.log(UserLog.FORUM, UserLog.FORUM_TOPTHEME, "�ö����⣺" + themeLog + "ʧ��", Request.getClientIP());
			} else {
				UserLog.log(UserLog.FORUM, UserLog.FORUM_TOPCANCEL, "ȡ���ö���" + themeLog + "ʧ��", Request.getClientIP());
			}
			Response.setLogInfo(0, "����ʧ��");
		}
	}

	public static Mapx initBestThemeDialog(Mapx params) {
		Mapx map = new Mapx();
		map.put("best", "��Ϊ����");
		map.put("back", "�������");
		params.put("BestTheme", HtmlUtil.mapxToRadios("BestTheme", map));
		return params;
	}

	public void bestTheme() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			LogUtil.warn("���ܵ�SQLע��,Theme.bestTheme:" + ids);
			return;
		}
		ForumScore forumScore = new ForumScore($V("SiteID"));
		ZCThemeSet set = new ZCThemeSchema().query(new QueryBuilder("where ID in (" + ids + ")"));
		ZCForumMemberSet userSet = new ZCForumMemberSet();
		Transaction trans = new Transaction();
		// ��־
		StringBuffer themeLog = new StringBuffer();
		Loop: for (int i = 0; i < set.size(); i++) {
			if (StringUtil.isEmpty($V("BestTheme"))) {
				Response.setLogInfo(0, "��δѡ���ѡ��");
				return;
			}
			// �ж��û��ǲ����Ѿ�������userSet�У�������ڽ����������Ѿ����ڵ�user��
			ZCForumMemberSchema user = new ZCForumMemberSchema();
			user.setUserName(set.get(i).getAddUser());
			int index = ForumUtil.getValueOfMemberSet(userSet, user);
			if ($V("BestTheme").equals("best")) {
				if (index == -1) {
					user.fill();
					if (set.get(i).getBest().equals("Y")) {
						continue Loop;
					}
					user.setForumScore(user.getForumScore() + forumScore.Best);
					userSet.add(user);
				} else {
					userSet.get(index).setForumScore(userSet.get(index).getForumScore() + forumScore.Best);
				}
				set.get(i).setBest("Y");
			} else if ($V("BestTheme").equals("back")) {
				if (index == -1) {
					user.fill();
					if (set.get(i).getBest().equals("N")) {
						continue Loop;
					}
					user.setForumScore(user.getForumScore() + forumScore.CancelBest);
					userSet.add(user);
				} else {
					userSet.get(index).setForumScore(userSet.get(index).getForumScore() + forumScore.CancelBest);
				}
				set.get(i).setBest("N");
			}
			themeLog.append(set.get(i).getSubject() + ",");
		}

		ForumUtil.userGroupChange(userSet);
		trans.add(userSet, Transaction.UPDATE);
		trans.add(set, Transaction.UPDATE);
		if (trans.commit()) {
			if ($V("BestTheme").equals("best")) {
				UserLog.log(UserLog.FORUM, "BestTheme", "��Ϊ������" + themeLog + "�ɹ�", Request.getClientIP());
			} else {
				UserLog.log(UserLog.FORUM, "CancelBest", "ȡ��������" + themeLog + "�ɹ�", Request.getClientIP());
			}
			Response.setLogInfo(1, "�����ɹ�");
		} else {
			if ($V("BestTheme").equals("back")) {
				UserLog.log(UserLog.FORUM, UserLog.FORUM_BESTTHEME, "��Ϊ������" + themeLog + "ʧ��", Request.getClientIP());
			} else {
				UserLog.log(UserLog.FORUM, UserLog.FORUM_BESTCANCEL, "ȡ��������" + themeLog + "ʧ��", Request.getClientIP());
			}
			Response.setLogInfo(0, "����ʧ��");
		}
	}

}
