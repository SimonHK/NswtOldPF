package com.nswt.cms.workflow;

import com.nswt.cms.document.Article;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.workflow.Context;
import com.nswt.workflow.methods.NodeMethod;

/**
 * 日期 : 2010-1-12 <br>
 * 作者: NSWT <br>
 * 邮箱：nswt@nswt.com.cn <br>
 */
public class WaitPublishMethod extends NodeMethod {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nswt.workflow.methods.NodeMethod#execute(com.nswt.workflow.Context)
	 */
	public void execute(Context context) {
		QueryBuilder qb = new QueryBuilder("update ZCArticle set Status=? where ID=?", Article.STATUS_TOPUBLISH, context
				.getInstance().getDataID());
		context.getTransaction().add(qb);
	}

}
