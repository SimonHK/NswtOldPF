package com.nswt.search.crawl;

import com.nswt.framework.utility.LogUtil;
import com.nswt.search.DocumentList;
import com.nswt.search.SearchUtil;
import com.nswt.search.WebDocument;

/**
 * @Author NSWT
 * @Date 2008-12-5
 * @Mail nswt@nswt.com.cn
 */
public class UrlExtracterThread extends Thread {
	private DocumentList list;

	private int level;

	private boolean isBusy;

	private UrlExtracter extracter;

	private int threadIndex;

	private WebDocument doc;

	public void run() {
		doc = list.next();
		int maxPageCount = list.getCrawler().getConfig().getMaxPageCount();
		int maxListCount = list.getCrawler().getConfig().getMaxListCount();
		int maxUrlLevel = list.getCrawler().getConfig().getUrlLevels().length;
		while (doc != null) {
			if (!list.getCrawler().task.checkStop()) {
				if (!isBusy) {
					synchronized (extracter) {
						this.extracter.busyThreadCount++;
					}
				}
				isBusy = true;
				if (doc.getLevel() == level - 1 && doc.isTextContent()) {
					String[] urls = SearchUtil.getUrlFromHtml(doc.getContentText());
					for (int k = 0; k < urls.length; k++) {
						if (maxPageCount > 0 && maxUrlLevel == doc.getLevel() + 2
								&& extracter.extractOutCount >= maxPageCount) {
							break;// ֻ����һ���㼶�ж���������ҳ����
						}
						if (maxListCount > 0 && maxUrlLevel == doc.getLevel() + 3
								&& extracter.extractOutCount >= maxListCount) {
							break;// ֻ�ڵ����ڶ����㼶�ж������б�ҳ����
						}
						String url2 = urls[k];
						url2 = SearchUtil.normalizeUrl(url2, doc.getUrl());
						url2 = SearchUtil.escapeUrl(url2);
						if (extracter.isMatchedUrl(url2, doc.getUrl())) {
							System.out.println(url2);
							if (!list.containsKey(url2)) {
								WebDocument doc2 = new WebDocument();
								doc2.setUrl(url2);
								doc2.setLevel(level);
								doc2.setRefUrl(doc.getUrl());
								list.put(doc2);
								CrawlScriptUtil util = new CrawlScriptUtil();
								util.doc = doc2;
								util.list = list;
								list.getCrawler().executeScript("before", util);
								extracter.extractOutCount++;
							} else {
								if (list.get(url2).getContent() != null) {// ����Ѿ������ˣ�ҲҪ��1
									extracter.extractOutCount++;
								}
							}
						}
					}
					extracter.extractedCount++;
					long percent = Math.round(extracter.extractedCount * 10000.0 / extracter.size);
					long p = percent % 100;
					String pStr = "" + p;
					if (p < 10) {
						pStr = "0" + p;
					}
					LogUtil.getLogger().info(
							("Extracting,Thread " + this.threadIndex + "\tL" + doc.getLevel() + "\t" + percent / 100
									+ "." + pStr + "%"));
				} else if (doc.getLevel() == level) {// Ҳ��Ҫ���ͬ���������������Ҫɾ��
					if (!extracter.isMatchedUrl(doc.getUrl(), doc.getRefUrl())) {
						list.remove(doc.getUrl());
					}
				}
				doc = list.next();
			} else {
				if (isBusy) {
					synchronized (extracter) {
						this.extracter.busyThreadCount--;
					}
				}
				doc = null;// �����ᵼ��ѭ���˳������Բ���Ҫbreak;
			}
		}
		synchronized (extracter) {
			isBusy = false;
			this.extracter.busyThreadCount--;
			this.extracter.aliveThreadCount--;
		}
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public DocumentList getList() {
		return list;
	}

	public void setList(DocumentList list) {
		this.list = list;
	}

	public UrlExtracter getExtracter() {
		return extracter;
	}

	public void setExtracter(UrlExtracter extracter) {
		this.extracter = extracter;
	}

	public int getThreadIndex() {
		return threadIndex;
	}

	public void setThreadIndex(int threadIndex) {
		this.threadIndex = threadIndex;
	}
}
