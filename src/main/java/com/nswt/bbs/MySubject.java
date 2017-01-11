package com.nswt.bbs;

import java.util.Date;

import com.nswt.framework.Ajax;
import com.nswt.framework.User;
import com.nswt.framework.controls.DataListAction;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.schema.ZCThemeSchema;

public class MySubject extends Ajax {
	public static void getList(DataListAction dla) {
		String addTime = dla.getParams().getString("addtime");
		String orderBy = dla.getParams().getString("orderby");
		String ascdesc = dla.getParams().getString("ascdesc");

		QueryBuilder qb = new QueryBuilder(
				"select t.*, f.Name, p.ID PID from ZCTheme t, ZCForum f, ZCPost p "
						+ " where p.ThemeID=t.ID and p.first='Y' and t.status='Y' and t.ForumID=f.ID and t.AddUser=?",
				User.getUserName());

		if (!StringUtil.isEmpty(addTime) && !"0".equals(addTime)) {
			Date date = new Date((new Date().getTime() - Long.parseLong(addTime)));
			qb.append(" and t.addTime >?", date);
		}

		if (!StringUtil.isEmpty(orderBy)) {
			qb.append(" order by " + orderBy);
		} else {
			qb.append(" order by OrderTime");
		}

		if (!StringUtil.isEmpty(ascdesc)) {
			if ("DESC".equals(ascdesc))
				qb.append(" desc ");
		}

		DataTable dt = qb.executePagedDataTable(dla.getPageSize(), dla.getPageIndex());
		dt.insertColumn("AuditStatus");
		dt.insertColumn("Operation");
		for (int i = 0; i < dt.getRowCount(); i++) {
			if (dt.get(i, "VerifyFlag").equals("Y")) {
				dt.set(i, "AuditStatus", "Õý³£");
				if (dt.get(i, "ApplyDel") == null) {
					dt.set(i, "Operation", "<a href='javascript:applyDel(" + dt.get(i, "PID") + ")'>ÉêÇëÉ¾³ý</a>");
				} else {
					dt.set(i, "Operation", "ÒÑÉêÇëÉ¾³ý");
				}
			} else {
				dt.set(i, "AuditStatus", "´ýÉóºË");
				dt.set(i, "Operation", "<cite><a href='javascript:editTheme(" + dt.get(i, "PID") + ","
						+ dt.get(i, "ForumID") + "," + dt.get(i, "ID")
						+ ")'>ÐÞ¸Ä</a></cite> <em><a href='javascript:del(" + dt.get(i, "ID") + ")' >É¾³ý</a></em>");
			}
		}
		dla.setTotal(qb);
		dla.bindData(dt);
	}

	public static Mapx init(Mapx params) {
		params.put("AddUser", User.getUserName());
		params.put("BBSName", ForumUtil.getBBSName(params.getString("SiteID")));
		return params;
	}

	public void del() {
		Transaction trans = new Transaction();
		ZCThemeSchema theme = new ZCThemeSchema();
		theme.setID($V("ID"));
		theme.fill();
		trans.add(theme, Transaction.DELETE_AND_BACKUP);
		trans.add(new QueryBuilder("delete from zcpost where themeID=?", theme.getID()));
		/*
		 * ZCForumSchema forum = new ZCForumSchema();
		 * forum.setID(theme.getForumID()); ForumUtil.changeLastTheme(forum,
		 * theme.getID()+""); trans.add(forum, Transaction.UPDATE);
		 */
		if (trans.commit()) {
			Response.setLogInfo(1, "É¾³ý³É¹¦");
		} else {
			Response.setLogInfo(1, "²Ù×÷Ê§°Ü");
		}

	}
}
