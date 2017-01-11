package com.nswt.bbs.admin;

import java.util.Date;

import com.nswt.bbs.ForumUtil;
import com.nswt.framework.Page;
import com.nswt.framework.User;
import com.nswt.framework.cache.CacheManager;
import com.nswt.framework.controls.DataGridAction;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.HtmlUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.pub.NoUtil;
import com.nswt.platform.pub.OrderUtil;
import com.nswt.schema.ZCForumSchema;
import com.nswt.schema.ZCForumSet;
import com.nswt.schema.ZCPostSchema;
import com.nswt.schema.ZCPostSet;
import com.nswt.schema.ZCThemeSchema;
import com.nswt.schema.ZCThemeSet;

public class ForumAdmin extends Page {

	public static Mapx init(Mapx params) {
		return params;
	}

	public static Mapx initAddDialog(Mapx params) {
		long SiteID = ForumUtil.getCurrentBBSSiteID();
		DataTable dt = new QueryBuilder("select Name,ID from ZCForum where SiteID=?" + " and ParentID=0 order by orderflag", SiteID)
				.executeDataTable();
		params.put("ParentForum", HtmlUtil.dataTableToOptions(dt));
		return params;
	}

	public static Mapx initEditDialog(Mapx params) {
		String ID = params.getString("ID");
		long SiteID = ForumUtil.getCurrentBBSSiteID();
		DataTable dt1 = new QueryBuilder("select ParentID from ZCForum where SiteID=? and ID=?", ID).executeDataTable();
		DataTable dt = new QueryBuilder("select Name,ID from ZCForum where SiteID=?" + " and ParentID=0 order by orderflag", SiteID)
				.executeDataTable();
		params.put("ParentForum", HtmlUtil.dataTableToOptions(dt, dt1.getString(0, "ParentID")));
		return params;
	}

	/**
	 * ��ʾ����б�
	 */
	public static void dg1DataBind(DataGridAction dga) {
		long SiteID = ForumUtil.getCurrentBBSSiteID();
		QueryBuilder qb = new QueryBuilder(
				"select ID,ParentID,Name,Info,Addtime,Type,ForumAdmin,ThemeCount,PostCount,'' as Expand,'' as TreeLevel,'' as EditButton from ZCForum where "
						+ " SiteID=? order by OrderFlag", SiteID);
		DataTable dt = qb.executeDataTable();
		for (int i = 0; i < dt.getRowCount(); i++) {
			if ("group".equals(dt.get(i, "Type"))) {
				dt.set(i, "Expand", "Y");
				dt.set(i, "TreeLevel", "0");
			} else {
				dt.set(i, "Expand", "N");
				dt.set(i, "TreeLevel", "1");
			}
			if (dt.getString(i, "ForumAdmin").endsWith(",")) {
				int index = dt.getString(i, "ForumAdmin").lastIndexOf(",");
				dt.set(i, "ForumAdmin", dt.getString(i, "ForumAdmin").substring(0, index));
			}
		}
		dga.bindData(dt);
	}

	public void add() {
		String name = $V("Name");
		ZCForumSchema forum = new ZCForumSchema();
		forum.setName(name);
		forum.setValue(Request);
		forum.setID(NoUtil.getMaxID("ForumID"));
		if (forum.getParentID() == 0) {
			forum.setType("group");
		} else {
			forum.setType("forum");
		}
		forum.setSiteID(ForumUtil.getCurrentBBSSiteID());
		forum.setStatus("0");
		forum.setThemeCount(0);
		forum.setPostCount(0);
		forum.setTodayPostCount(0);
		forum.setVisible("Y");
		forum.setLocked("N");
		forum.setUnLockID("0");
		forum.setReplyPost("Y");
		forum.setVerify("N");
		forum.setAllowTheme("Y");
		forum.setAnonyPost("N");
		forum.setEditPost("Y");
		forum.setRecycle("N");
		forum.setAllowHTML("N");
		forum.setAllowFace("Y");
		forum.setUnPasswordID("0");

		forum.setOrderFlag(OrderUtil.getDefaultOrder());
		forum.setAddTime(new Date());
		forum.setAddUser(User.getUserName());
		Transaction trans = new Transaction();
		trans.add(forum, Transaction.INSERT);
		if (trans.commit()) {
			CacheManager.set("Forum", "Forum", forum.getID(), forum);
			Response.setLogInfo(1, "�����ɹ�!");
		} else {
			Response.setLogInfo(0, "����ʧ��!");
		}
	}

	/**
	 * ɾ���������߰�飬��ͬ��������������������ȫ��ɾ��
	 * 
	 * @author wst
	 */
	public void del() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("����IDʱ��������!");
			return;
		}
		long SiteID = ForumUtil.getCurrentBBSSiteID();
		// ���ѡ�����ʱ��û��û��ѡ��İ��
		ZCForumSchema forumCheck = new ZCForumSchema();
		ZCForumSet set = forumCheck.query(new QueryBuilder("where id in(" + ids + ")"));
		for (int i = 0; i < set.size(); i++) {
			forumCheck = set.get(i);
			if (forumCheck.getParentID() == 0) {
				long count = new QueryBuilder(
						"select count(*) from ZCForum where SiteID=? and ParentID=?" + " and ID not in (" + ids + ")", SiteID, forumCheck
								.getID()).executeLong();
				if (count > 0) {
					Response.setStatus(0);
					Response.setMessage("����ɾ������\"" + forumCheck.getName() + "\",�÷����»���δ��ѡ�еİ��!");
					return;
				}
			}
		}

		String[] IDs = getForumIDs(ids);
		Transaction trans = new Transaction();
		for (int n = 0; n < IDs.length; n++) {
			ZCForumSchema forum = new ZCForumSchema();
			forum.setID(IDs[n]);
			forum.fill();
			trans.add(forum, Transaction.DELETE_AND_BACKUP);
			if (forum.getParentID() == 0) {// ���ɾ������һ������
				// ɾ������������а��
				ZCForumSet forumSet = forum.query(new QueryBuilder("where SiteID=? and ParentID=?", SiteID, forum.getID()));
				trans.add(forumSet, Transaction.DELETE_AND_BACKUP);
				// ɾ��ÿ������µ���������
				for (int i = 0; i < forumSet.size(); i++) {
					ZCThemeSchema theme = new ZCThemeSchema();
					theme.setID(forumSet.get(i).getID());
					ZCThemeSet themeSet = theme.query();
					trans.add(themeSet, Transaction.DELETE_AND_BACKUP);
					ZCPostSchema post = new ZCPostSchema();
					// ɾ��ÿ�������µ���������
					for (int j = 0; j < themeSet.size(); j++) {
						post.setID(themeSet.get(i).getID());
						ZCPostSet postSet = post.query();
						trans.add(postSet, Transaction.DELETE_AND_BACKUP);
					}
				}
			} else {// ���ɾ������һ�����
				// ɾ������µ���������
				ZCThemeSchema theme = new ZCThemeSchema();
				theme.setForumID(forum.getID());
				ZCThemeSet themeSet = theme.query();
				trans.add(themeSet, Transaction.DELETE_AND_BACKUP);
				ZCPostSchema post = new ZCPostSchema();
				// ɾ�������µ���������
				for (int i = 0; i < themeSet.size(); i++) {
					post.setThemeID(themeSet.get(i).getID());
					ZCPostSet postSet = post.query();
					trans.add(postSet, Transaction.DELETE_AND_BACKUP);
				}
			}
		}
		if (trans.commit()) {
			for (int n = 0; n < IDs.length; n++) {
				CacheManager.remove("Forum", "Forum", IDs[n]);
			}
			Response.setLogInfo(1, "ɾ���ɹ�!");
		} else {
			Response.setLogInfo(0, "����ʧ��!");
		}
	}

	/**
	 * �����޸�
	 * 
	 */
	public void dg1Edit() {
		Transaction trans = new Transaction();
		ZCForumSchema forum = new ZCForumSchema();
		forum.setID($V("ID"));
		forum.fill();
		forum.setName($V("Name"));
		// �ж�������û����ǲ��Ƕ�����
		if ($V("ForumAdmin").trim().length() == 0 || ForumUtil.isExistMember($V("ForumAdmin"))) {
			if ($V("ForumAdmin").trim().length() == 0 || isExistAdmin(trans, $V("ForumAdmin"), forum)) {
				forum.setForumAdmin($V("ForumAdmin") + ",");
				ForumUtil.addAdminID(trans, forum.getID() + "", forum.getForumAdmin());
			} else {
				Response.setLogInfo(0, "���𽫷��������ظ������ڰ����");
				return;
			}
		} else {
			Response.setLogInfo(0, "�����˲����ڵ��û���");
			return;
		}

		trans.add(forum, Transaction.UPDATE);
		if (trans.commit()) {
			CacheManager.set("Forum", "Forum", forum.getID(), forum);
			Response.setLogInfo(1, "�����ɹ���");
		} else {
			Response.setLogInfo(0, "����ʧ�ܣ�");
		}
	}

	public void editSave() {
		Transaction trans = new Transaction();
		ZCForumSchema forum = new ZCForumSchema();
		forum.setID($V("ID"));
		forum.fill();
		forum.setValue(Request);
		if ($V("ForumAdmin").trim().length() == 0 || isExistAdmin(trans, $V("ForumAdmin"), forum)) {
			forum.setForumAdmin($V("ForumAdmin") + ",");
			ForumUtil.addAdminID(trans, forum.getID() + "", forum.getForumAdmin());
		} else {
			Response.setLogInfo(0, "���𽫷��������ظ������ڰ����");
			return;
		}
		trans.add(forum, Transaction.UPDATE);
		if (trans.commit()) {
			CacheManager.set("Forum", "Forum", forum.getID(), forum);
			Response.setLogInfo(1, "�����ɹ���");
		} else {
			Response.setLogInfo(0, "����ʧ��");
		}
	}

	public void isGroup() {
		String id = $V("ID");
		ZCForumSchema forum = new ZCForumSchema();
		forum.setID(id);
		forum.fill();
		if (forum.getParentID() == 0) {
			Response.setStatus(0);
		} else {
			Response.setStatus(1);
		}
	}

	/**
	 * ͨ��һ��ID���ϣ�������ID�ʹ˷����µİ��ID������ʱ��ֻ��ȡ����ID���ų����ID��������ID������ʱȴ���ڴ˷����µİ��IDʱ��ȡ�����ID
	 * 
	 * @param ids
	 * @return
	 */
	private String[] getForumIDs(String ids) {
		long SiteID = ForumUtil.getCurrentBBSSiteID();
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("����IDʱ��������!");
			return null;
		}
		QueryBuilder qb = new QueryBuilder("select ID from ZCForum where SiteID=? and (ID in (" + ids + ") and ParentID not in (" + ids
				+ ")) or (ID in (" + ids + ") and ParentID=0)", SiteID);
		DataTable dt = qb.executeDataTable();
		String[] IDs = new String[dt.getRowCount()];
		for (int i = 0; i < dt.getRowCount(); i++) {
			IDs[i] = dt.getString(i, "ID");
		}
		return IDs;
	}

	/**
	 * �жϸ��û��Ƿ��Ѿ��Ƿ����İ���
	 * 
	 * @param forumAdmins
	 * @param forum
	 * @return
	 */
	private boolean isExistAdmin(Transaction trans, String forumAdmins, ZCForumSchema forum) {
		String[] admins = forumAdmins.split(",");
		if (forum.getParentID() == 0) {
			DataTable dt = new QueryBuilder("select ForumAdmin,ID from ZCForum where ParentID=?", forum.getID()).executeDataTable();
			for (int i = 0; i < dt.getRowCount(); i++) {
				for (int j = 0; j < admins.length; j++) {
					String ParentAdmin = dt.getString(i, "ForumAdmin");
					if (ParentAdmin.indexOf(admins[j] + ",") >= 0) {
						ParentAdmin = ParentAdmin.replaceAll(admins[j] + ",", "");
						trans.add(new QueryBuilder("update ZCForum set ForumAdmin=? where ID=?", ParentAdmin, dt.getString(i, "ID")));
					}
				}
			}
		} else {
			QueryBuilder qb = new QueryBuilder("select ForumAdmin from ZCForum where ID=?", forum.getParentID());
			String groupAdmin = qb.executeString();
			if (StringUtil.isEmpty(groupAdmin)) {
				return true;
			}
			for (int i = 0; i < admins.length; i++) {
				if (groupAdmin.indexOf(admins[i] + ",") >= 0) {
					return false;
				}
			}
		}
		return true;
	}

}
