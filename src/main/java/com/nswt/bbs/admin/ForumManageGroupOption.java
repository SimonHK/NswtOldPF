package com.nswt.bbs.admin;

import com.nswt.framework.Page;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.HtmlUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.schema.ZCAdminGroupSchema;
import com.nswt.schema.ZCForumGroupSchema;

public class ForumManageGroupOption extends Page {
	/**
	 * ������Ȩ�����ó�ʼ��
	 * 
	 * @param params
	 * @return
	 */
	public static Mapx init(Mapx params) {
		String ID = params.getString("ID");
		ZCForumGroupSchema group = new ZCForumGroupSchema();
		group.setID(ID);
		group.fill();
		Mapx map = new Mapx();
		map.put("Y", "����");
		map.put("N", "������");
		if (StringUtil.isEmpty(group.getSystemName()) || !group.getSystemName().equals("�ο�")) {
			params.put("AllowEditUser", HtmlUtil.mapxToRadios("AllowEditUser", map, group.getAllowEditUser()));
			params.put("AllowForbidUser", HtmlUtil.mapxToRadios("AllowForbidUser", map, group.getAllowForbidUser()));
			params.put("AllowEditForum", HtmlUtil.mapxToRadios("AllowEditForum", map, group.getAllowEditForum()));
			params.put("AllowVerfyPost", HtmlUtil.mapxToRadios("AllowVerfyPost", map, group.getAllowVerfyPost()));
			params.put("AllowEdit", HtmlUtil.mapxToRadios("AllowEdit", map, group.getAllowEdit()));
			params.put("AllowDel", HtmlUtil.mapxToRadios("AllowDel", map, group.getAllowDel()));
			params.put("Hide", HtmlUtil.mapxToRadios("Hide", map, group.getHide()));
		} else {
			params.put("AllowEditUser", "������");
			params.put("AllowForbidUser", "������");
			params.put("AllowEditForum", "������");
			params.put("AllowVerfyPost", "������");
			params.put("AllowEdit", "������");
			params.put("AllowDel", "������");
			params.put("Hide", "������");
		}

		return params;
	}

	public static Mapx initThemeOption(Mapx params) {
		ZCForumGroupSchema group = new ZCForumGroupSchema();
		group.setID(params.getString("ID"));
		group.fill();
		Mapx map = new Mapx();
		map.put("Y", "����");
		map.put("N", "������");
		if (StringUtil.isEmpty(group.getSystemName()) || !group.getSystemName().equals("�ο�")) {
			params.put("RemoveTheme", HtmlUtil.mapxToRadios("RemoveTheme", map, group.getRemoveTheme()));
			params.put("MoveTheme", HtmlUtil.mapxToRadios("MoveTheme", map, group.getMoveTheme()));
			params.put("TopTheme", HtmlUtil.mapxToRadios("TopTheme", map, group.getTopTheme()));
			params.put("BrightTheme", HtmlUtil.mapxToRadios("BrightTheme", map, group.getBrightTheme()));
			params.put("UpOrDownTheme", HtmlUtil.mapxToRadios("UpOrDownTheme", map, group.getUpOrDownTheme()));
			params.put("BestTheme", HtmlUtil.mapxToRadios("BestTheme", map, group.getBestTheme()));
		} else {
			params.put("RemoveTheme", "������");
			params.put("MoveTheme", "������");
			params.put("TopTheme", "������");
			params.put("BrightTheme", "������");
			params.put("UpOrDownTheme", "������");
			params.put("BestTheme", "������");
		}
		return params;
	}

	public void editSave() {
		Transaction trans = new Transaction();
		ZCAdminGroupSchema manageGroup = new ZCAdminGroupSchema();
		manageGroup.setGroupID($V("GroupID"));
		manageGroup.fill();
		manageGroup.setValue(Request);
		manageGroup.setAllowEditUser($V("AllowEditUser"));

		trans.add(manageGroup, Transaction.UPDATE);
		if (trans.commit()) {
			Response.setLogInfo(1, "���óɹ�!");
		} else {
			Response.setLogInfo(0, "����ʧ�ܣ�");
		}
	}

}
