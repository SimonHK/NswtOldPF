package com.nswt.bbs.admin;

import com.nswt.bbs.ForumUtil;
import com.nswt.framework.Page;
import com.nswt.framework.cache.CacheManager;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.HtmlUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.schema.ZCAdminGroupSchema;
import com.nswt.schema.ZCForumGroupSchema;
import com.nswt.schema.ZCForumMemberSchema;
import com.nswt.schema.ZCForumMemberSet;

public class ForumUserGroupOption extends Page {
	public static Mapx init(Mapx params) {
		return params;
	}

	/**
	 * �û���������ó�ʼ��
	 * 
	 * @param params
	 * @return
	 */
	public static Mapx initBasic(Mapx params) {
		ZCForumGroupSchema userGroup = new ZCForumGroupSchema();
		userGroup.setID(params.getString("ID"));
		userGroup.fill();
		params = userGroup.toMapx();
		Mapx map = new Mapx();
		map.put("Y", "����");
		map.put("N", "������");
		if (StringUtil.isEmpty(userGroup.getSystemName()) || !userGroup.getSystemName().equals("�ο�")) {
			params.put("AllowHeadImage", HtmlUtil.mapxToRadios("AllowHeadImage", map, userGroup.getAllowHeadImage()));
			params.put("AllowNickName", HtmlUtil.mapxToRadios("AllowNickName", map, userGroup.getAllowNickName()));
			params.put("AllowPanel", HtmlUtil.mapxToRadios("AllowPanel", map, userGroup.getAllowPanel()));
		} else {
			params.put("AllowHeadImage", "������");
			params.put("AllowNickName", "������");
			params.put("AllowPanel", "������");
		}
		params.put("AllowUserInfo", HtmlUtil.mapxToRadios("AllowUserInfo", map, userGroup.getAllowUserInfo()));
		params.put("AllowVisit", HtmlUtil.mapxToRadios("AllowVisit", map, userGroup.getAllowVisit()));
		params.put("AllowSearch", HtmlUtil.mapxToRadios("AllowSearch", map, userGroup.getAllowSearch()));
		return params;
	}

	/**
	 * �û���������س�ʼ��
	 * 
	 * @param params
	 * @return
	 */
	public static Mapx initPostOption(Mapx params) {
		ZCForumGroupSchema userGroup = new ZCForumGroupSchema();
		userGroup.setID(params.getString("ID"));
		userGroup.fill();
		params = userGroup.toMapx();
		Mapx map = new Mapx();
		map.put("Y", "����");
		map.put("N", "������");
		if (StringUtil.isEmpty(userGroup.getSystemName()) || !userGroup.getSystemName().equals("�ο�")) {
			params.put("AllowTheme", HtmlUtil.mapxToRadios("AllowTheme", map, userGroup.getAllowTheme()));
			params.put("AllowReply", HtmlUtil.mapxToRadios("AllowReply", map, userGroup.getAllowReply()));
			params.put("Verify", HtmlUtil.mapxToRadios("Verify", map, userGroup.getVerify()));
		} else {
			params.put("AllowTheme", "������");
			params.put("AllowReply", "������");
			params.put("Verify", "������");
		}
		return params;
	}

	/**
	 * �û������ñ���
	 * 
	 */
	public void editSave() {
		Transaction trans = new Transaction();
		ZCForumGroupSchema userGroup = new ZCForumGroupSchema();
		userGroup.setID($V("ID"));
		userGroup.fill();
		userGroup.setValue(Request);
		trans.add(userGroup, Transaction.UPDATE);
		if (trans.commit()) {
			CacheManager.set("Forum", "Group", userGroup.getID(), userGroup);
			Response.setLogInfo(1, "���óɹ�!");
		} else {
			Response.setLogInfo(0, "����ʧ�ܣ�");
		}
	}

	public static Mapx initSpecailOption(Mapx params) {
		long SiteID = ForumUtil.getCurrentBBSSiteID();
		ZCForumGroupSchema userGroup = new ZCForumGroupSchema();
		userGroup.setID(params.getString("ID"));
		userGroup.fill();
		params = userGroup.toMapx();
		Mapx map = new Mapx();
		map.put("Y", "����");
		map.put("N", "������");
		params.put("AllowVisit", HtmlUtil.mapxToRadios("AllowVisit", map, userGroup.getAllowVisit()));
		params.put("AllowSearch", HtmlUtil.mapxToRadios("AllowSearch", map, userGroup.getAllowSearch()));
		params.put("AllowHeadImage", HtmlUtil.mapxToRadios("AllowHeadImage", map, userGroup.getAllowHeadImage()));
		params.put("AllowUserInfo", HtmlUtil.mapxToRadios("AllowUserInfo", map, userGroup.getAllowUserInfo()));
		params.put("AllowNickName", HtmlUtil.mapxToRadios("AllowNickName", map, userGroup.getAllowNickName()));
		String sql = "select a.Name,b.GroupID from ZCForumGroup a,ZCAdminGroup b where a.SiteID=? and a.ID=b.GroupID and a.type='2' and a.SystemName<>'ϵͳ����Ա'";
		DataTable dt = new QueryBuilder(sql, SiteID).executeDataTable();
		params.put("AdminGroup", HtmlUtil.dataTableToOptions(dt, userGroup.getRadminID() + ""));
		return params;
	}

	public void editSpecialSave() {
		long SiteID = ForumUtil.getCurrentBBSSiteID();
		Transaction trans = new Transaction();
		ZCForumGroupSchema userGroup = new ZCForumGroupSchema();
		userGroup.setID($V("ID"));
		userGroup.fill();
		userGroup.setValue(Request);
		ZCAdminGroupSchema newGroup = new ZCAdminGroupSchema();
		newGroup.setGroupID(userGroup.getID());
		// �ж��Ƿ�����ڹ������
		if (newGroup.fill()) {
			if ($V("RadminID").equals("0")) {
				trans.add(newGroup, Transaction.DELETE_AND_BACKUP);
				ZCForumMemberSet memberSet = new ZCForumMemberSchema().query(new QueryBuilder("where SiteID=?"
						+ " and UserGroupID=?", SiteID, newGroup.getGroupID()));
				for (int i = 0; i < memberSet.size(); i++) {
					memberSet.get(i).setAdminID(0);
				}
				trans.add(memberSet, Transaction.UPDATE);
			} else {
				ZCAdminGroupSchema adminGroup = new ZCAdminGroupSchema();
				adminGroup.setGroupID($V("RadminID"));
				adminGroup.fill();
				// ������������Ĺ�������ø��Ƹ��¼ӵ��Զ��������
				newGroup = adminGroup;
				newGroup.setGroupID(userGroup.getID());
				trans.add(newGroup, Transaction.UPDATE);
				// �������ڸ���������û������ڸ��µĹ�������Ȩ��
				ZCForumMemberSet memberSet = new ZCForumMemberSchema().query(new QueryBuilder("where SiteID=?"
						+ " and UserGroupID=?", SiteID, newGroup.getGroupID()));
				for (int i = 0; i < memberSet.size(); i++) {
					memberSet.get(i).setAdminID($V("RadminID"));
				}
				trans.add(memberSet, Transaction.UPDATE);
			}
		} else {
			if (!$V("RadminID").equals("0")) {
				ZCAdminGroupSchema adminGroup = new ZCAdminGroupSchema();
				adminGroup.setGroupID($V("RadminID"));
				adminGroup.fill();
				// ������������Ĺ�������ø��Ƹ��¼ӵ��Զ��������
				newGroup = adminGroup;
				newGroup.setGroupID(userGroup.getID());
				trans.add(newGroup, Transaction.INSERT);
				// �������ڸ���������û������ڸ��µĹ�������Ȩ��
				ZCForumMemberSet memberSet = new ZCForumMemberSchema().query(new QueryBuilder("where SiteID=?"
						+ " and UserGroupID=?", SiteID, newGroup.getGroupID()));
				for (int i = 0; i < memberSet.size(); i++) {
					memberSet.get(i).setAdminID($V("RadminID"));
				}
				trans.add(memberSet, Transaction.UPDATE);
			} else {

			}
		}
		trans.add(userGroup, Transaction.UPDATE);
		if (trans.commit()) {
			CacheManager.set("Forum", "Group", userGroup.getID(), userGroup);
			Response.setLogInfo(1, "���óɹ�!");
		} else {
			Response.setLogInfo(0, "����ʧ�ܣ�");
		}
	}

}
