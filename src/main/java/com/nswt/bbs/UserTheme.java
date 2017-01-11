package com.nswt.bbs;

import java.util.Date;

import com.nswt.framework.Ajax;
import com.nswt.framework.User;
import com.nswt.framework.controls.DataListAction;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;

public class UserTheme extends Ajax {

	public static void getList(DataListAction dla) {
		String addTime = dla.getParams().getString("addtime");
		String orderBy = dla.getParams().getString("orderby");
		String ascdesc = dla.getParams().getString("ascdesc");

		QueryBuilder qb = new QueryBuilder("select * from ZCTheme  where Status='Y' and VerifyFlag='Y' and AddUser=?",
				dla.getParam("LastPoster"));

		if (!StringUtil.isEmpty(addTime) && !"0".equals(addTime)) {
			Date date = new Date(new Date().getTime() - Long.parseLong(addTime));
			qb.append(" and addTime >?", date);
		}

		if (!StringUtil.isEmpty(orderBy)) {
			if (!StringUtil.checkID(orderBy)) {
				LogUtil.warn("可能的SQL注入,UserTheme.getList:" + orderBy);
				return;
			}
			qb.append(" order by " + orderBy);
			if (!StringUtil.isEmpty(ascdesc)) {
				if ("DESC".equals(ascdesc))
					qb.append(" desc ");
			}
		} else {
			qb.append(" order by OrderTime desc");
		}

		DataTable dt = qb.executePagedDataTable(dla.getPageSize(), dla.getPageIndex());
		dla.setTotal(qb);
		dla.bindData(dt);
	}

	public static Mapx init(Mapx params) {
		params.put("AddUser", User.getUserName());
		return params;
	}
}
