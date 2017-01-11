package com.nswt.cms.document;

import org.apache.commons.lang.ArrayUtils;

import com.nswt.framework.Config;
import com.nswt.framework.Page;
import com.nswt.framework.User;
import com.nswt.framework.cache.CacheManager;
import com.nswt.framework.controls.DataGridAction;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.UserList;
import com.nswt.schema.ZCMessageSchema;
import com.nswt.schema.ZCMessageSet;

/**
 * @Author ����
 * @Date 2008-01-29
 * @Mail lanjun@nswt.com
 * @edit xuzhe
 */

public class Message extends Page {
	public static Mapx init(Mapx params) {
		return null;
	}

	public static Mapx initDetailDialog(Mapx params) {
		String id = params.getString("ID");
		String Type = params.getString("Type");
		if (StringUtil.isEmpty(id)) {
			return null;
		}
		DataTable dt = new QueryBuilder("select * from ZCMessage where ID=?", Long.parseLong(id)).executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			params.putAll(dt.getDataRow(0).toMapx());
			if ("history".equals(Type)) {
				params.put("UserType", "��");
				params.put("FromUser", "");
			} else {
				params.put("UserType", "��");
				params.put("ToUser", "");
				// ���¶�ȡ���
				int readFlag = Integer.parseInt(dt.getDataRow(0).getString("ReadFlag"));
				if (readFlag == 0) {
					new QueryBuilder("update ZCMessage set ReadFlag = 1 where ID=?", Long.parseLong(id)).executeNoQuery();
					QueryBuilder qb = new QueryBuilder("select count(1) from ZCMessage where ReadFlag=0 and ToUser=?",
							User.getUserName());
					CacheManager.set("Message", "Count", User.getUserName(), qb.executeInt());
				}
			}
		}
		return params;
	}

	public static Mapx initReplyDialog(Mapx params) {
		String id = params.getString("ID");
		if (StringUtil.isEmpty(id)) {
			return null;
		}
		DataTable dt = new QueryBuilder("select * from ZCMessage where ID=?", Long.parseLong(id)).executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			return dt.getDataRow(0).toMapx();
		} else {
			return null;
		}
	}

	public static void dg1DataBind(DataGridAction dga) {
		QueryBuilder qb = new QueryBuilder(
				"select ZCMessage.*,case readFlag when 1 then '�Ѷ�' else 'δ��' end as ReadFlagStr,"
						+ "case readFlag when 1 then '' else 'red' end as color from ZCMessage where touser=?", User
						.getUserName());
		qb.append(dga.getSortString());
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dt.insertColumn("ReadFlagIcon");
		for (int i = 0; i < dt.getRowCount(); i++) {
			String flag = dt.getString(i, "ReadFlag");
			if (!"1".equals(flag)) {
				dt.set(i, "ReadFlagIcon", "<img src='../Icons/icon037a7.gif'>");
			} else {
				dt.set(i, "ReadFlagIcon", "<img src='../Icons/icon037a17.gif'>");
			}
		}
		dga.setTotal(qb);
		dga.bindData(dt);
	}

	public static void historyDataBind(DataGridAction dga) {
		QueryBuilder qb = new QueryBuilder(
				"select ZCMessage.*,case readFlag when 1 then '�Ѷ�' else 'δ��' end as ReadFlagStr,"
						+ "case readFlag when 1 then '' else 'red' end as color from ZCMessage where fromuser=?", User
						.getUserName());
		qb.append(dga.getSortString());
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dt.insertColumn("ReadFlagIcon");
		for (int i = 0; i < dt.getRowCount(); i++) {
			String flag = dt.getString(i, "ReadFlag");
			if (!"1".equals(flag)) {
				dt.set(i, "ReadFlagIcon", "<img src='../Icons/icon037a7.gif'>");
			} else {
				dt.set(i, "ReadFlagIcon", "<img src='../Icons/icon037a17.gif'>");
			}
		}
		dga.setTotal(qb);
		dga.bindData(dt);
	}

	public void getNewMessage() {
		if (!Config.isInstalled) {// �п��������°�װ��
			this.redirect(Config.getContextPath() + "Install.jsp");
			return;
		}
		Response.put("Count", MessageCache.getNoReadCount());
		String message = MessageCache.getFirstPopMessage();
		if (StringUtil.isEmpty(message)) {
			Response.put("PopFlag", "0");
		} else {
			Response.put("Message", message);
			Response.put("PopFlag", "1");
		}
	}

	/**
	 * ������Ϣʱ�������֪���ˡ����ñ�����
	 */
	public void updateReadFlag() {
		QueryBuilder qb = new QueryBuilder("update ZCMessage set ReadFlag=1 where ID=?", Long.parseLong($V("_Param0")));
		qb.executeNoQuery();
		String count = (String) CacheManager.get("Message", "Count", User.getUserName());
		CacheManager.set("Message", "Count", User.getUserName(), Integer.parseInt(count) - 1);
	}

	public void add() {
		String toUser = $V("ToUser");
		if (!StringUtil.checkID(toUser)) {
			Response.setLogInfo(0, "�����������");
			return;
		}
		String[] userList = toUser.split(",");

		String toRole = $V("ToRole");
		if (!StringUtil.checkID(toRole)) {
			Response.setLogInfo(0, "�����������");
			return;
		}
		String[] roleList = toRole.split(",");

		if (roleList.length > 0) {
			String roleStr = "";
			for (int j = 0; j < roleList.length; j++) {
				if (StringUtil.isNotEmpty(roleList[j])) {
					if (j == 0) {
						roleStr += "'" + roleList[j] + "'";
					} else {
						roleStr += ",'" + roleList[j] + "'";
					}
				}
			}
			if (StringUtil.isNotEmpty(roleStr)) {
				DataTable dt = new QueryBuilder("select UserName from zduserRole where rolecode in (" + roleStr + ")")
						.executeDataTable();
				for (int k = 0; k < dt.getRowCount(); k++) {
					String userName = dt.getString(k, "UserName");
					if (!(User.getUserName().equals(userName) || ArrayUtils.contains(userList, userName))) {
						userList = (String[]) ArrayUtils.add(userList, userName);
					}
				}
			}
		}

		if (MessageCache.addMessage($V("Subject"), $V("Content"), userList, User.getUserName())) {
			Response.setLogInfo(1, "�½��ɹ���");
		} else {
			Response.setLogInfo(0, "�½�ʧ�ܣ�");
		}
	}

	public void reply() {
		String toUser = $V("ToUser");
		if (!StringUtil.checkID(toUser)) {
			Response.setLogInfo(0, "�����������");
			return;
		}
		if (MessageCache.addMessage($V("Subject"), $V("Content"), toUser)) {
			Response.setLogInfo(1, "��ӻظ��ɹ���");
		} else {
			Response.setLogInfo(0, "��ӻظ�ʧ�ܣ�");
		}
	}

	public void del() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setLogInfo(0, "����IDʱ��������");
			return;
		}
		Transaction trans = new Transaction();

		ZCMessageSchema message = new ZCMessageSchema();
		ZCMessageSet set = message.query(new QueryBuilder("where id in (" + ids + ")"));
		trans.add(set, Transaction.DELETE_AND_BACKUP);

		if (trans.commit()) {
			MessageCache.removeIDs(set);
			QueryBuilder qb = new QueryBuilder("select count(1) from ZCMessage where ReadFlag=0 and ToUser=?", User
					.getUserName());
			CacheManager.set("Message", "Count", User.getUserName(), qb.executeInt());
			Response.setLogInfo(1, "ɾ���ɹ�");
		} else {
			Response.setLogInfo(0, "ɾ��ʧ��");
		}
	}

	public void setReadFlag() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setLogInfo(0, "����IDʱ��������");
			return;
		}
		ZCMessageSet set = new ZCMessageSchema().query(new QueryBuilder("where ReadFlag=0 and id in (" + ids + ")"));
		QueryBuilder qb = new QueryBuilder("update ZCMessage set ReadFlag=1 where id in (" + ids + ")");
		qb.executeNoQuery();
		Response.setLogInfo(1, "��ǳɹ�");
		MessageCache.removeIDs(set);
		qb = new QueryBuilder("select count(1) from ZCMessage where ReadFlag=0 and ToUser=?", User.getUserName());
		CacheManager.set("Message", "Count", User.getUserName(), qb.executeInt());
	}

	public static void bindUserList(DataGridAction dga) {
		String searchUserName = dga.getParam("SearchUserName");
		QueryBuilder qb = new QueryBuilder("select * from ZDUser");
		qb.append(" where BranchInnerCode like ?", User.getBranchInnerCode() + "%");
		qb.append(" and UserName <> ?", User.getUserName());
		if (StringUtil.isNotEmpty(searchUserName)) {
			qb.append(" and (UserName like ?", "%" + searchUserName.trim() + "%");
			// ��ѯ��ʵ����
			qb.append(" or realname like ?)", "%" + searchUserName.trim() + "%");
		}
		qb.append(" order by AddTime desc");
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dt.decodeColumn("Status", UserList.STATUS_MAP);
		dga.bindData(dt);
	}

	public static void bindRoleList(DataGridAction dga) {
		String searchRoleName = dga.getParam("SearchRoleName");
		QueryBuilder qb = new QueryBuilder("select * from ZDRole");
		qb.append(" where BranchInnerCode like ?", User.getBranchInnerCode() + "%");
		if (StringUtil.isNotEmpty(searchRoleName)) {
			qb.append(" and (RoleCode like ?", "%" + searchRoleName.trim() + "%");
			// ��ѯ��ɫ��
			qb.append(" or RoleName like ?)", "%" + searchRoleName.trim() + "%");
		}
		qb.append(" order by AddTime desc");
		dga.bindData(qb);
	}
}
