package com.nswt.cms.site;

import java.io.File;

import com.nswt.cms.document.Article;
import com.nswt.framework.Config;
import com.nswt.framework.data.DBUtil;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.schedule.GeneralTask;
import com.nswt.framework.utility.FileUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;

/**
 * ���� : 2010-5-15 <br>
 * ����: NSWT <br>
 * ���䣺nswt@nswt.com.cn <br>
 */
public class TagUpdateTask extends GeneralTask {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nswt.framework.schedule.GeneralTask#execute()
	 */
	public void execute() {
		new Thread() {// ��ʱ���ܻ�ܳ�����Ҫ�����̣߳�����Ӱ��������ʱ�����ִ��
			public void run() {
				// ��վ��ִ��
				Object[] sites = new QueryBuilder("select ID from ZCSite").executeDataTable().getColumnValues(0);
				for (int m = 0; m < sites.length; m++) {
					Mapx countMap = new Mapx();// ��Ÿ���ǩ��ʹ�ô���
					QueryBuilder qb = new QueryBuilder("select Tag from ZCArticle where Status=? and SiteID=?",
							Article.STATUS_PUBLISHED, sites[m]);
					int total = DBUtil.getCount(qb);
					int pageSize = 20000;
					// �������
					for (int i = 0; i * pageSize < total; i++) {
						DataTable dt = qb.executePagedDataTable(pageSize, i);
						cache(dt, i);
						for (int j = 0; j < dt.getRowCount(); j++) {
							String tag = dt.getString(j, "Tag");
							String[] arr = tag.split("\\s");
							for (int k = 0; k < arr.length; k++) {
								if (StringUtil.isNotEmpty(arr[k])) {
									countMap.put(arr[k], countMap.getInt(arr[k]) + 1);
								}
							}
						}
					}
					Object[] tags = countMap.keyArray();

					// �������ô��������ݿ�
					QueryBuilder updater = new QueryBuilder("Update ZCTag set UsedCount=? where SiteID=? and Tag=?");
					updater.setBatchMode(true);
					for (int i = 0; i < tags.length; i++) {
						updater.add(countMap.getInt(tags[i]));
						updater.add(sites[m]);
						updater.add(tags[i]);
						updater.addBatch();
					}
					updater.executeNoQuery();

					// ���������ϵ
					int TagPageSize = 100;// ���ǵ��ڴ����ģ�ÿ��ֻ����100��Tag�Ĺ�����ϵ
					Mapx[] relas = new Mapx[TagPageSize];
					for (int p = 0; p < tags.length; p += TagPageSize) {
						for (int i = 0; i * pageSize < total; i++) {
							DataTable dt = getCache(i);
							for (int j = 0; j < dt.getRowCount(); j++) {
								String articleTag = " " + dt.getString(j, "Tag") + " ";
								for (int k = p; k < tags.length && k < p + TagPageSize; k++) {
									Mapx map = relas[k - p];
									if (map == null) {
										relas[k - p] = map = new Mapx(1000);// �������У�ֻ��ǰ1000��
									}
									if (articleTag.indexOf(" " + tags[k] + " ") >= 0) {
										String[] arr = articleTag.split("\\s");
										for (int q = 0; q < arr.length; q++) {
											if (StringUtil.isNotEmpty(arr[q]) && !arr[q].equals(tags[k])) {
												map.put(arr[q], map.getInt(arr[q]) + 1);
											}
										}
									}
								}
							}
						}
						// ����100��������ϵ
						updater = new QueryBuilder("Update ZCTag set RelaTag=? where SiteID=? and Tag=?");
						updater.setBatchMode(true);
						for (int i = 0; i < relas.length; i++) {
							Mapx map = relas[i];
							if (map == null) {
								break;
							}
							DataTable dt = map.toDataTable();
							dt.sort("Value", "desc");
							StringBuffer sb = new StringBuffer();
							for (int j = 0; j < 30 && j < dt.getRowCount(); j++) {// ֻ��ǰ30������TAG
								if (j != 0) {
									sb.append(" ");
								}
								sb.append(dt.getString(j, "Key"));
							}
							updater.add(sb.toString());
							updater.add(sites[m]);
							updater.add(tags[i]);
							updater.addBatch();
						}
						updater.executeNoQuery();
					}
					for (int i = 0; i * pageSize < total; i++) {
						FileUtil.deleteEx(Config.getContextRealPath() + "WEB-INF/cache/TagCache" + i + ".dat");
					}
				}
			}
		}.start();
	}

	private void cache(DataTable dt, int i) {
		FileUtil.serialize(dt, Config.getContextRealPath() + "WEB-INF/cache/TagCache" + i + ".dat");
	}

	private DataTable getCache(int i) {
		File f = new File(Config.getContextRealPath() + "WEB-INF/cache/TagCache" + i + ".dat");
		if (f.exists()) {
			return (DataTable) FileUtil.unserialize(f.getAbsolutePath());
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nswt.framework.schedule.AbstractTask#getID()
	 */
	public long getID() {
		return 201005151955L;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nswt.framework.schedule.AbstractTask#getName()
	 */
	public String getName() {
		return "Tag�ȶ�/�����Լ���";
	}

	public static void main(String[] args) {
		TagUpdateTask tut = new TagUpdateTask();
		tut.execute();
	}
}
