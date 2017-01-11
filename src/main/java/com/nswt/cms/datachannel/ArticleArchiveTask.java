package com.nswt.cms.datachannel;

import com.nswt.cms.document.Article;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.schedule.GeneralTask;
import com.nswt.framework.utility.DateUtil;
import com.nswt.framework.utility.LogUtil;
import com.nswt.schema.ZCArticleSchema;

public class ArticleArchiveTask extends GeneralTask {

	public static void main(String[] args) {
		ArticleArchiveTask t = new ArticleArchiveTask();
		t.execute();
	}
	public void execute() {
		LogUtil.getLogger().info("定时归档任务");
		Transaction trans = new Transaction();
		int size = 500;
		int count = new QueryBuilder("select count(*) from ZCArticle where ArchiveDate<=?",DateUtil.getCurrentDateTime()).executeInt();
		int page = count/size;
		if(count%size==0){
			page = page-1;
		}
		DataTable articleDt = new DataTable();
		for (int i = 0; i < page; i++) {
			articleDt = new QueryBuilder("select * from ZCArticle where ArchiveDate<=?",DateUtil.getCurrentDateTime()).executePagedDataTable(size, page);
			for (int j = 0; j < articleDt.getRowCount(); j++) {
				ZCArticleSchema article = new ZCArticleSchema();
				article.setID(articleDt.getString(j,"ID"));
				article.fill();
				article.setStatus(Article.STATUS_ARCHIVE);
				trans.add(article,Transaction.DELETE_AND_BACKUP);
			}
			//trans.setBackupOperator("CronTask");//手动执行的话需要设置，自动执行系统默认是置为 CronTask
			trans.setBackupMemo("Archive");
			trans.commit();
			trans.clear();
		}
		LogUtil.getLogger().info("扫描定时归档任务结束");
	}

	public long getID() {
		return 200912081905L;
	}

	public String getName() {
		return "对文章进行归档";
	}
}
