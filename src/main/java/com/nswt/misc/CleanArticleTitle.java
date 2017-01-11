package com.nswt.misc;

import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.utility.StringUtil;

/**
 * @Author ������
 * @Date 2008-11-5
 * @Mail nswt@nswt.com.cn
 */
public class CleanArticleTitle {

	public static void main(String[] args) {
		DataTable dt = new QueryBuilder("select id,title from ZCArticle").executeDataTable();
		QueryBuilder qb = new QueryBuilder("update ZCArticle set Title=? where id=?");
		qb.setBatchMode(true);
		for (int i = 0; i < dt.getRowCount(); i++) {
			String id = dt.getString(i, 0);
			String title = dt.getString(i, 1);
			title = StringUtil.htmlDecode(title);
			title = title.replaceAll("\\<.*?\\>", "");
			qb.add(title);
			qb.add(id);
			qb.addBatch();
		}
		qb.executeNoQuery();
	}
}