package com.nswt.cms.datachannel;

import java.util.Date;

import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.schedule.GeneralTask;
import com.nswt.schema.ZCArticleSchema;
import com.nswt.schema.ZCArticleSet;

public class ArticleCancelTopTask extends GeneralTask {

	public void execute() {
		QueryBuilder qb = new QueryBuilder("update ZCArticle set TopFlag='0' where topflag='1' " 
				+ "and topdate is not null and topdate<=?", new Date());
		qb.executeNoQuery();
		ZCArticleSchema article = new ZCArticleSchema();
		ZCArticleSet set = article.query(new QueryBuilder("where topflag='1' and topdate is not null and topdate<=?", new Date()));
		if (set!=null && set.size()>0) {
			Publisher p = new Publisher();
			p.publishArticle(set);
		}
	}

	public long getID() {
		return 200912091105L;
	}

	public String getName() {
		return "过期的置顶文章取消置顶";
	}
}
