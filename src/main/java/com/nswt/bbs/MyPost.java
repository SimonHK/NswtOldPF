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
import com.nswt.schema.ZCPostSchema;

public class MyPost extends Ajax {

	public static void getMyPost(DataListAction dla) {
		String addTime = dla.getParams().getString("addtime");
		String ascdesc = dla.getParams().getString("ascdesc");

		QueryBuilder qb = new QueryBuilder("select p.*, t.Subject TSubject, f.Name "
				+ "from ZCPost p, ZCTheme t, ZCForum f where p.SiteID=?"
				+ " and p.ThemeID=t.ID and p.Invisible='Y' and t.ForumID=f.ID and p.first='N'"
				+ " and p.AddUser=? and t.status='Y' and t.VerifyFlag='Y' ", User.getUserName(), dla.getParam("SiteID"));

		ForumPriv priv = new ForumPriv(dla.getParam("SiteID"));
		if (!priv.hasPriv("AllowPanel")) {
			return;
		}
		if (!StringUtil.isEmpty(addTime) && !"0".equals(addTime)) {
			Date date = new Date((new Date().getTime() - Long.parseLong(addTime)));
			qb.append(" and p.addTime >?", date);
		}

		qb.append(" order by p.AddTime");

		if (!StringUtil.isEmpty(ascdesc)) {
			if ("DESC".equals(ascdesc))
				qb.append(" desc ");
		} else {
			qb.append(" desc");
		}
		DataTable dt = qb.executePagedDataTable(dla.getPageSize(), dla.getPageIndex());
		dt.insertColumn("AuditStatus");
		dt.insertColumn("Operation");
		for (int i = 0; i < dt.getRowCount(); i++) {
			if (dt.get(i, "VerifyFlag").equals("Y")) {
				dt.set(i, "AuditStatus", "Õý³£");
				if (dt.get(i, "ApplyDel") == null) {
					dt.set(i, "Operation", "<a href='javascript:applyDel(" + dt.get(i, "ID") + ")'>ÉêÇëÉ¾³ý</a>");
				} else {
					dt.set(i, "Operation", "ÒÑÉêÇëÉ¾³ý");
				}
			} else {
				dt.set(i, "AuditStatus", "´ýÉóºË");
				dt.set(i, "Operation", "<cite><a href='javascript:editPost(" + dt.get(i, "ID") + ","
						+ dt.get(i, "ForumID") + "," + dt.get(i, "ThemeID")
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
		String ID = $V("ID");
		Transaction trans = new Transaction();
		ZCPostSchema post = new ZCPostSchema();
		post.setID(ID);
		post.fill();
		post.setInvisible("N");
		trans.add(post, Transaction.UPDATE);
		if (trans.commit()) {
			Response.setLogInfo(1, "É¾³ý³É¹¦");
		} else {
			Response.setLogInfo(0, "É¾³ýÊ§°Ü!");
		}

	}
}
