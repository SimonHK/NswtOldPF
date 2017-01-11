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
import com.nswt.framework.utility.HtmlUtil;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.schema.ZCForumGroupSchema;
import com.nswt.schema.ZCForumMemberSchema;
import com.nswt.schema.ZCForumMemberSet;
import com.nswt.schema.ZCForumSchema;
import com.nswt.schema.ZCForumSet;
import com.nswt.schema.ZCPostSchema;
import com.nswt.schema.ZCPostSet;
import com.nswt.schema.ZCThemeSchema;
import com.nswt.schema.ZCThemeSet;

public class PostAudit extends Ajax {

	public static Mapx init(Mapx params) {
		String SiteID = params.getString("SiteID");
		ZCForumMemberSchema member = new ZCForumMemberSchema();
		ZCForumGroupSchema group = new ZCForumGroupSchema();
		member.setUserName(User.getUserName());
		member.fill();
		group = ForumCache.getGroup(member.getAdminID());
		group.fill();
		DataTable dt = null;
		if (group.getSystemName().equals("��������")) {
			QueryBuilder qb = new QueryBuilder("select Name, ID from ZCForum where SiteID=? and ParentID<>0", SiteID);
			dt = qb.executeDataTable();
		} else {
			QueryBuilder qb = new QueryBuilder("select Name,ID from zcforum where SiteID=?" + " and (forumadmin like '%"
					+ User.getUserName() + ",%' and ParentID<>0) or ParentID in (select ID from zcforum where SiteID=?"
					+ " and forumadmin like '%" + User.getUserName() + ",%' and ParentID=0)", SiteID, SiteID);
			dt = qb.executeDataTable();
		}
		params.put("ForumOptions", HtmlUtil.dataTableToOptions(dt));
		params.put("BBSName", ForumUtil.getBBSName(params.getString("SiteID")));
		return params;
	}

	/**
	 * ��ʾδ��˻ظ��б�
	 * 
	 * @param dla
	 */
	public static void getUnauditedPost(DataListAction dla) {
		String SiteID = dla.getParam("SiteID");
		String auditFlag = dla.getParams().getString("auditFlag");
		String forumID = dla.getParams().getString("ForumID");
		String typeID = dla.getParams().getString("TypeID");
		String first = dla.getParams().getString("First");
		QueryBuilder qb = new QueryBuilder("select p.*, f.Name ForumName, '' as AuditFlag from ZCPost p, ZCForum f "
				+ "where p.SiteID=? and p.Invisible='Y' and p.ForumID=f.ID", SiteID);
		if (StringUtil.isNotEmpty(typeID)) {
			qb.append(" and p.VerifyFlag=?", typeID);
		} else {
			qb.append(" and p.VerifyFlag='N'");
		}

		if (StringUtil.isNotEmpty(forumID) && !forumID.equals("0")) {
			if (!StringUtil.checkID(forumID)) {
				LogUtil.warn("���ܵ�SQLע��,PostAudit.getUnauditedPost:" + forumID);
				return;
			}
			qb.append(" and p.ForumID in (" + forumID + ")");
		}

		if (StringUtil.isNotEmpty(first)) {
			qb.append(" and p.first=?", first);
		} else {
			qb.append(" and p.first='Y'");
		}
		qb.append(" order by ID");
		int pageSize = dla.getPageSize();
		int pageIndex = dla.getPageIndex();
		DataTable dt = qb.executePagedDataTable(pageSize, pageIndex);
		dt.insertColumn("StatusCount");
		for (int i = 0; i < dt.getRowCount(); i++) {
			Mapx map = new Mapx();
			map.put("Y", "ͨ��");
			map.put("N", "ɾ��");
			map.put("X", "����");
			if (StringUtil.isNotEmpty(auditFlag)) {
				dt.set(i, "AuditFlag", HtmlUtil.mapxToRadios("AuditFlag_" + dt.get(i, "ID"), map, auditFlag, true));
			} else {
				dt.set(i, "AuditFlag", HtmlUtil.mapxToRadios("AuditFlag_" + dt.get(i, "ID"), map, "Y", true));
			}
			dt.set(i, "StatusCount", i);
		}

		dla.setTotal(qb);
		dla.bindData(dt);
	}

	/**
	 * ִ�����(ͨ��/ɾ��/����)
	 * 
	 */
	public void exeAudit() {
		String SiteID = $V("SiteID");
		ForumScore forumScore = new ForumScore($V("SiteID"));
		Transaction trans = new Transaction();
		if (!StringUtil.checkID($V("ids"))) {
			LogUtil.warn("���ܵ�SQLע��,PostAudit.exeAudit:" + $V("ids"));
			return;
		}
		ZCPostSet postSet = new ZCPostSchema().query(new QueryBuilder("where SiteID=? and id in(" + $V("ids") + ")", SiteID));
		ZCThemeSet themeSet = new ZCThemeSet();
		ZCForumSet forumSet = new ZCForumSet();

		ZCForumMemberSet userSet = new ZCForumMemberSet();
		Mapx map = new Mapx();

		for (int i = 0; i < postSet.size(); i++) {
			ZCPostSchema post = postSet.get(i);
			ZCThemeSchema theme = new ZCThemeSchema();
			theme.setID(post.getThemeID());
			ZCForumSchema forum = new ZCForumSchema();
			forum.setID(post.getForumID());
			ZCForumMemberSchema user = new ZCForumMemberSchema();
			user.setUserName(post.getAddUser());
			int indexTheme = ForumUtil.getValueOfThemeSet(themeSet, theme);
			int indexForum = ForumUtil.getValueOfForumSet(forumSet, forum);
			int indexMember = ForumUtil.getValueOfMemberSet(userSet, user);
			String newFlag = $V("AuditFlag_" + post.getID());
			if (map.get("" + post.getThemeID()) == null) {
				long layer = getMAXLayer(post.getThemeID()) + 1;
				map.put("" + post.getThemeID(), layer);
			}
			if (StringUtil.isNotEmpty(newFlag)) {
				if (newFlag.equals("Y")) {
					post.setVerifyFlag("Y");

					if (post.getFirst().equals("Y")) {
						theme.fill();
						theme.setVerifyFlag("Y");
						theme.setOrderTime(new Date());
						themeSet.add(theme);
						// ����÷������Ѿ�������set�У�������ݸ��µ�set�����һ���ύ
						if (indexMember == -1) {
							user.fill();
							user.setThemeCount(user.getThemeCount() + 1);
							user.setForumScore(user.getForumScore() + forumScore.PublishTheme);
							userSet.add(user);
						} else {
							userSet.get(indexMember).setThemeCount(userSet.get(indexMember).getThemeCount() + 1);
							userSet.get(indexMember).setForumScore(userSet.get(indexMember).getForumScore() + forumScore.PublishTheme);
						}
						// ������������ڵİ���Ѿ�������set�У�������ݸ��µ�set�����һ���ύ
						if (indexForum == -1) {
							forum.fill();
							forum.setPostCount(forum.getPostCount() + 1);
							forum.setThemeCount(forum.getThemeCount() + 1);
							forum.setLastPost(post.getSubject());
							forum.setLastPoster(post.getAddUser());
							forum.setLastThemeID(theme.getID());
							forumSet.add(forum);
						} else {
							forumSet.get(indexForum).setPostCount(forumSet.get(indexForum).getPostCount() + 1);
							forumSet.get(indexForum).setThemeCount(forumSet.get(indexForum).getThemeCount() + 1);
							forumSet.get(indexForum).setLastPost(post.getSubject());
							forumSet.get(indexForum).setLastPoster(post.getAddUser());
							forumSet.get(indexForum).setLastThemeID(theme.getID());
						}

					} else {
						post.setLayer(map.getLong("" + post.getThemeID()));
						map.put("" + post.getThemeID(), post.getLayer() + 1);
						if (indexMember == -1) {
							user.fill();
							user.setReplyCount(user.getReplyCount() + 1);
							user.setForumScore(user.getForumScore() + forumScore.PublishPost);
							userSet.add(user);
						} else {
							userSet.get(indexMember).setReplyCount(userSet.get(indexMember).getThemeCount() + 1);
							userSet.get(indexMember).setForumScore(userSet.get(indexMember).getForumScore() + forumScore.PublishPost);
						}
						// ������������ڵİ���Ѿ�������set�У�������ݸ��µ�set�����һ���ύ
						if (indexForum == -1) {
							forum.fill();
							forum.setPostCount(forum.getPostCount() + 1);
							forumSet.add(forum);
						} else {
							forumSet.get(indexForum).setPostCount(forumSet.get(indexForum).getPostCount() + 1);
						}
						// ������������ڵ������Ѿ�������set�У�������ݸ��µ�set�����һ���ύ
						if (indexTheme == -1) {
							theme.fill();
							theme.setReplyCount(theme.getReplyCount() + 1);
							themeSet.add(theme);
						} else {
							themeSet.get(indexTheme).setReplyCount(themeSet.get(indexTheme).getReplyCount() + 1);
						}
						theme.setLastPostTime(new Date());
					}
				} else if (newFlag.equals("N")) {
					post.setInvisible("N");
					if (post.getFirst().equals("Y")) {
						theme.fill();
						theme.setStatus("N");
						themeSet.add(theme);
					}
				} else if (newFlag.equals("Z")) {
					post.setApplyDel("N");
					if (post.getFirst().equals("Y")) {
						theme.fill();
						theme.setApplydel("N");
						themeSet.add(theme);
					}
				} else {
					post.setVerifyFlag("X");
					if (post.getFirst().equals("Y")) {
						theme.fill();
						theme.setVerifyFlag("X");
						themeSet.add(theme);
					}
				}
			}
		}
		trans.add(postSet, Transaction.UPDATE);
		trans.add(userSet, Transaction.UPDATE);
		trans.add(forumSet, Transaction.UPDATE);
		trans.add(themeSet, Transaction.UPDATE);
		if (trans.commit()) {
			for (int i = 0; i < forumSet.size(); i++) {
				CacheManager.set("Forum", "Forum", forumSet.get(i).getID(), forumSet.get(i));
			}
			Response.setLogInfo(1, "�����ɹ�");
		} else {
			Response.setLogInfo(0, "����ʧ��!");
		}
	}

	/**
	 * �õ���Ӧ��������¥����
	 * 
	 * @param ThemeID
	 * @return
	 */
	private long getMAXLayer(long ThemeID) {
		QueryBuilder qb = new QueryBuilder("select Layer from ZCPost where Invisible='Y' and ThemeID=?" + " order by Layer desc", ThemeID);
		long layer = qb.executePagedDataTable(1, 0).getLong(0, 0);
		return layer;
	}

	public void applyDel() {
		Transaction trans = new Transaction();
		ZCPostSchema post = new ZCPostSchema();
		ZCThemeSchema theme = new ZCThemeSchema();
		post.setID($V("ID"));
		post.fill();
		post.setApplyDel("Y");
		if (post.getFirst().equals("Y")) {
			theme.setID(post.getThemeID());
			theme.fill();
			theme.setApplydel("Y");
			trans.add(theme, Transaction.UPDATE);
		}
		trans.add(post, Transaction.UPDATE);

		if (trans.commit()) {
			Response.setLogInfo(1, "����ɹ�!");
		} else {
			Response.setLogInfo(0, "����ʧ��!");
		}
	}

}
