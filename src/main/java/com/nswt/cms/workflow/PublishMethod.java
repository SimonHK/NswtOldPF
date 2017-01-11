package com.nswt.cms.workflow;

import com.nswt.cms.datachannel.Publisher;
import com.nswt.cms.document.Article;
import com.nswt.framework.utility.Executor;
import com.nswt.schema.ZCArticleSchema;
import com.nswt.schema.ZCArticleSet;
import com.nswt.workflow.Context;
import com.nswt.workflow.methods.NodeMethod;

/**
 * 日期 : 2010-1-12 <br>
 * 作者: NSWT <br>
 * 邮箱：nswt@nswt.com.cn <br>
 */
public class PublishMethod extends NodeMethod {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nswt.workflow.methods.NodeMethod#execute(com.nswt.workflow.Context)
	 */
	public void execute(Context context) {
		context.getTransaction().addExecutor(new Executor(context.getInstance().getDataID()) {
			public boolean execute() {
				ZCArticleSet set = new ZCArticleSet();
				ZCArticleSchema article = new ZCArticleSchema();
				article.setID(this.param.toString());
				article.fill();
				if (article.getPublishDate() != null && article.getPublishDate().getTime() > System.currentTimeMillis()) {
					article.setStatus(Article.STATUS_TOPUBLISH);// 末到时间则是待发布状态
				} else {
					article.setStatus(Article.STATUS_PUBLISHED);
				}
				set.add(article);
				Publisher p = new Publisher();
				p.publishArticle(set);
				return true;
			}
		});
	}
}
