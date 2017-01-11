package com.nswt.cms.dataservice;

import com.nswt.framework.Ajax;
import com.nswt.framework.Config;
import com.nswt.framework.controls.DataListAction;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;

/**
 * @author xuzhe
 * @mail xuzhe@nswt.com
 * @Date 2016-1-1
 */

public class MessageService extends Ajax {

	public static Mapx init(Mapx params) {
		String BoardID = params.getString("BoardID");
		if (StringUtil.isNotEmpty(BoardID)) {
			params.put("BoardName", new QueryBuilder("select Name from ZCMessageBoard where ID = ?", Long.parseLong(BoardID)).executeString());
		}
		params.put("ServicesContext", Config.getValue("ServicesContext"));
		params.put("MessageActionURL", Config.getValue("MessageActionURL"));
		return params;
	}

	public static Mapx avicitinit(Mapx params) {
		params.put("BoardID","2");
		String BoardID = params.getString("BoardID");
		if (StringUtil.isNotEmpty(BoardID)) {
			params.put("BoardName", new QueryBuilder("select Name from ZCMessageBoard where ID = ?", Long.parseLong(BoardID)).executeString());
		}
		params.put("ServicesContext", Config.getValue("ServicesContext"));
		params.put("MessageActionURL", Config.getValue("MessageActionURL"));
		return params;
	}

	public static void avicitdg1DataBind(DataListAction dla) {
		String BoardID = "2";
		QueryBuilder qb = new QueryBuilder("select * from ZCBoardMessage where BoardID = ? and PublishFlag = 'Y' order by ID desc", Long.parseLong(BoardID));
		dla.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dla.getPageSize(), dla.getPageIndex());
		dt.insertColumn("Reply");
		dt.insertColumn("Prefix");
		for (int i = 0; i < dt.getRowCount(); i++) {
			if (dt.getString(i, "ReplyFlag").equals("Y")) {
				dt.set(i,"Prefix","<font color='#9B0D17'>[»Ø¸´]£º</font>");
				dt.set(i, "Reply", "<font color='#9B0D17'>"+ dt.getString(i, "ReplyContent") + "</font>");
			}
		}
		dla.bindData(dt);
	}
	
	public static void dg1DataBind(DataListAction dla) {
		String BoardID = dla.getParam("BoardID");
		QueryBuilder qb = new QueryBuilder("select * from ZCBoardMessage where BoardID = ? and PublishFlag = 'Y' order by ID desc", Long.parseLong(BoardID));
		dla.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dla.getPageSize(), dla.getPageIndex());
		dt.insertColumn("Reply");
		dt.insertColumn("Prefix");
		for (int i = 0; i < dt.getRowCount(); i++) {
			if (dt.getString(i, "ReplyFlag").equals("Y")) {
				dt.set(i,"Prefix","<font color='#9B0D17'>[»Ø¸´]£º</font>");
				dt.set(i, "Reply", "<font color='#9B0D17'>"+ dt.getString(i, "ReplyContent") + "</font>");
			}
		}
		dla.bindData(dt);
	}
}