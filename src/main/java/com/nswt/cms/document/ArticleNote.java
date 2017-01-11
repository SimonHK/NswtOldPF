package com.nswt.cms.document;

import java.util.Date;

import com.nswt.framework.Page;
import com.nswt.framework.User;
import com.nswt.framework.controls.DataGridAction;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.DateUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.pub.NoUtil;
import com.nswt.schema.ZCArticleLogSchema;
import com.nswt.schema.ZCArticleLogSet;

/**
 * 文章批注管理
 * 
 * @Author 兰军
 * @Date 2008-1-21
 * @Mail lanjun@nswt.com
 */
public class ArticleNote extends Page {
	
	public static Mapx init(Mapx params) {
		if (StringUtil.isNotEmpty(params.getString("ID")) && StringUtil.isDigit(params.getString("ID"))) {
			ZCArticleLogSchema articleLog = new ZCArticleLogSchema();
			articleLog.setID(params.getString("ID"));
			articleLog.fill();
			params.putAll(articleLog.toCaseIgnoreMapx());
		}
		return params;
	}
	
	public static void noteDataBind(DataGridAction dga) {
		String articleID = dga.getParam("ArticleID");
		QueryBuilder qb = new QueryBuilder(
				"select * from ZCArticlelog where action='NOTE' and articleID=? order by ID desc", Long.parseLong(articleID));
		dga.bindData(qb);
	}

	public static void logDataBind(DataGridAction dga) {
		String articleID = (String) dga.getParams().get("ArticleID");
		String userName = (String) dga.getParams().get("UserName");
		String startDate = (String) dga.getParams().get("startDate");
		String endDate = (String) dga.getParams().get("endDate");
		QueryBuilder qb = new QueryBuilder("select * from ZCArticlelog where action<>'NOTE' and articleID=?"
				+" order by addtime", Long.parseLong(articleID));
		if (StringUtil.isNotEmpty(userName)) {
			qb.append(" and AddUser=?", userName);
		}
		if (StringUtil.isNotEmpty(startDate)) {
			qb.append(" and AddTime>=?", startDate);
		}
		if (StringUtil.isNotEmpty(endDate)) {
			qb.append(" and AddTime<?", DateUtil.addDay(DateUtil.parse(endDate), 1));
		}
		dga.bindData(qb);
	}

	public void del() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("操作数据库时发生错误!");
			return;
		}
		Transaction trans = new Transaction();
		ZCArticleLogSchema log = new ZCArticleLogSchema();
		ZCArticleLogSet set = log.query(new QueryBuilder("where id in (" + ids + ")"));
		trans.add(set, Transaction.DELETE_AND_BACKUP);

		if (trans.commit()) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("操作数据库时发生错误!");
		}
	}
	
	public void add() {
		Transaction trans = new Transaction();
		if (StringUtil.isNotEmpty($V("Content")) && $V("Content").length()>400) {
			Response.setStatus(0);
			Response.setMessage("批注内容不能超过400个字!");
			return;
		}
		
		add(trans, $V("ArticleID"), $V("Content"));
		if (trans.commit()) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("操作数据库时发生错误!");
		}
	}

	public static void add(Transaction trans, String articleID, String content) {
		long id = Long.parseLong(articleID);
		// 操作日志
		ZCArticleLogSchema articleLog = new ZCArticleLogSchema();
		articleLog.setID(NoUtil.getMaxID("ArticleLogID"));
		articleLog.setArticleID(id);
		articleLog.setAction("NOTE");
		articleLog.setActionDetail(content);
		articleLog.setAddUser(User.getUserName());
		articleLog.setAddTime(new Date());
		trans.add(articleLog, Transaction.INSERT);
	}
}
