package com.nswt.bbs.admin;

import java.util.Date;

import com.nswt.bbs.ForumUtil;
import com.nswt.framework.Page;
import com.nswt.framework.User;
import com.nswt.framework.cache.CacheManager;
import com.nswt.framework.controls.DataListAction;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.platform.pub.NoUtil;
import com.nswt.schema.ZCForumGroupSchema;

public class ForumManageGroup extends Page {
	public static void getListManageGroup(DataListAction dla) {
		long SiteID = ForumUtil.getCurrentBBSSiteID();
		QueryBuilder qb = new QueryBuilder("select f1.Name, f2.SystemName, f1.Type, a.* "
				+ "from ZCForumGroup f1, ZCForumGroup f2, ZCAdminGroup a where f1.SiteID=? "
				+ " and f1.ID=a.GroupID and f1.RadminID=f2.ID", SiteID);
		DataTable dt = qb.executeDataTable();
		for (int i = 0; i < dt.getRowCount(); i++) {
			if (dt.get(i, "Type").equals("2")) {
				dt.set(i, "Type", "����");
			} else {
				dt.set(i, "Type", "�Զ���");
			}
		}
		dla.setTotal(qb);
		dla.bindData(dt);
	}

	public void addManageGroup() {
		Transaction trans = new Transaction();
		ZCForumGroupSchema userGroup = new ZCForumGroupSchema();
		userGroup.setID(NoUtil.getMaxID("ForumGroupID"));
		userGroup.setType("2");
		userGroup.setValue(Request);
		userGroup.setAddUser(User.getUserName());
		userGroup.setAddTime(new Date());
		trans.add(userGroup, Transaction.INSERT);
		if (trans.commit()) {
			CacheManager.set("Forum", "Group", userGroup.getID(), userGroup);
			Response.setLogInfo(1, "��ӳɹ�");
		} else {
			Response.setLogInfo(0, "����ʧ��");
		}

	}
}
