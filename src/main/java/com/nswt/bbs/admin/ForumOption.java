package com.nswt.bbs.admin;

import com.nswt.bbs.ForumUtil;
import com.nswt.framework.Page;
import com.nswt.framework.controls.DataGridAction;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.utility.HtmlUtil;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.schema.ZCForumSchema;

public class ForumOption extends Page {
	/**
	 * @author wst
	 * @param params
	 * @return
	 */
	public static Mapx basicInit(Mapx params) {
		long SiteID = ForumUtil.getCurrentBBSSiteID();
		String ID = params.getString("ID");
		ZCForumSchema forum = new ZCForumSchema();
		forum.setID(ID);
		forum.fill();
		if (StringUtil.isNotEmpty(forum.getForumAdmin()) && forum.getForumAdmin().endsWith(",")) {
			int index = forum.getForumAdmin().lastIndexOf(",");
			forum.setForumAdmin(forum.getForumAdmin().substring(0, index));
		}
		params = forum.toMapx();
		if (forum.getParentID() != 0) {
			DataTable dt = new QueryBuilder("select Name,ID from ZCForum where SiteID=?"
					+ " and ParentID=0 order by orderflag", SiteID).executeDataTable();
			params.put("ParentForum", HtmlUtil.dataTableToOptions(dt, forum.getParentID() + "", false));
		}

		Mapx map1 = new Mapx();
		map1.put("Y", "��ʾ");
		map1.put("N", "����ʾ");
		params.put("Visible", HtmlUtil.mapxToRadios("Visible", map1, forum.getVisible()));
		if (forum.getLocked().equals("Y")) {
			params.put("checkY", "checked");
		} else {
			params.put("checkN", "checked");
		}

		if (StringUtil.isNotEmpty(forum.getUnLockID())) {
			DataTable dtLock = new QueryBuilder("select Name from ZCForumGroup where id in(" + forum.getUnLockID()
					+ ")").executeDataTable();
			Object[] valuesLock = dtLock.getColumnValues("Name");
			params.put("GroupUnLock", StringUtil.join(valuesLock));
		}

		if (StringUtil.isNotEmpty(forum.getUnPasswordID())) {
			DataTable dtPassword = new QueryBuilder("select Name from ZCForumGroup where id in("
					+ forum.getUnPasswordID() + ")").executeDataTable();
			Object[] valuesPassword = dtPassword.getColumnValues("Name");
			params.put("GroupUnPassword", StringUtil.join(valuesPassword));
		}
		params.put("UnLockID", forum.getUnLockID());
		params.put("UnPasswordID", forum.getUnPasswordID());
		if (forum.getParentID() == 0) {
			params.put("forumType", "group");
		}
		return params;
	}

	/**
	 * @author cjc
	 * @param params
	 * @return
	 */
	public static Mapx postOptionInit(Mapx params) {
		long SiteID = ForumUtil.getCurrentBBSSiteID();
		String ID = params.getString("ID");
		ZCForumSchema forum = new ZCForumSchema();
		forum.setID(ID);
		forum.fill();
		params = forum.toMapx();
		DataTable dt = new QueryBuilder("select Name,ID from ZCForum where SiteID=?"
				+ " and ParentID=0 order by orderflag", SiteID).executeDataTable();
		params.put("ParentForum", HtmlUtil.dataTableToOptions(dt, forum.getParentID() + ""));
		Mapx map = new Mapx();
		map.put("N", "��");
		map.put("T", "���������");
		map.put("Y", "�����������»ظ�");
		params.put("Verify", HtmlUtil.mapxToRadios("Verify", map, forum.getVerify()));
		map.clear();
		map.put("Y", "��");
		map.put("N", "��");
		params.put("EditPost", HtmlUtil.mapxToRadios("EditPost", map, forum.getEditPost()));
		params.put("AllowTheme", HtmlUtil.mapxToRadios("AllowTheme", map, forum.getAllowTheme()));
		params.put("Recycle", HtmlUtil.mapxToRadios("Recycle", map, forum.getEditPost()));
		params.put("AllowHTML", HtmlUtil.mapxToRadios("AllowHTML", map, forum.getEditPost()));
		params.put("AllowFace", HtmlUtil.mapxToRadios("AllowFace", map, forum.getEditPost()));
		params.put("ReplyPost", HtmlUtil.mapxToRadios("ReplyPost", map, forum.getReplyPost()));

		return params;
	}

	public static Mapx initOption(Mapx params) {
		return params;
	}

	public static void selectGroup(DataGridAction dla) {
		long SiteID = ForumUtil.getCurrentBBSSiteID();
		DataTable dtGroup = new QueryBuilder("select Name,ID from ZCForumGroup where SiteID=?"
				+ " and (SystemName!='�ο�' or SystemName is null) order by ID", SiteID).executeDataTable();
		dla.bindData(dtGroup);
	}

	public void saveGroup() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			LogUtil.warn("���ܵ�SQLע��,ForumOption.saveGroup:" + ids);
			return;
		}
		DataTable dt = new QueryBuilder("select Name from ZCForumGroup where id in(" + ids + ")").executeDataTable();

		Object[] values = dt.getColumnValues("Name");
		Response.put("Group", StringUtil.join(values));
	}
}
