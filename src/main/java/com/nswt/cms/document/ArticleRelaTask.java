package com.nswt.cms.document;

import com.nswt.framework.schedule.GeneralTask;

/**
 * 日期 : 2016-11-10 <br>
 * 作者: NSWT <br>
 * 邮箱：nswt@nswt.com.cn <br>
 */
public class ArticleRelaTask extends GeneralTask {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nswt.framework.schedule.GeneralTask#execute()
	 */
	public void execute() {
		ArticleRelaIndexer ari = new ArticleRelaIndexer();
		ari.start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nswt.framework.schedule.AbstractTask#getCronExpression()
	 */
	public String getCronExpression() {
		return "* * * * *";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nswt.framework.schedule.AbstractTask#getID()
	 */
	public long getID() {
		return 200911102214L;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nswt.framework.schedule.AbstractTask#getName()
	 */
	public String getName() {
		return "相关文章索引";
	}

}
