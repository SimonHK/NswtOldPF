package com.nswt.search.crawl;

import java.util.ArrayList;

import com.nswt.framework.utility.RegexParser;
import com.nswt.framework.utility.ServletUtil;
import com.nswt.framework.utility.StringUtil;
import com.nswt.search.DocumentList;
import com.nswt.search.SearchUtil;

/**
 * @Author NSWT
 * @Date 2008-12-5
 * @Mail nswt@nswt.com.cn
 */
public class UrlExtracter extends Thread {

	private int threadCount = 1;// ֻ���ڶ���ʱ���ܴ���1

	protected int aliveThreadCount = 0;

	protected int busyThreadCount = 0;// ��Ҫ��Ϊ��afterScript

	protected FileDownloader fileDownloader;

	protected int size;

	protected int extractOutCount;// ���㼶������������һ�㼶��URL����

	protected int extractedCount;// ���㼶�Ѿ�������������

	ArrayList urlArr = new ArrayList();

	ArrayList rpArr = new ArrayList();

	ArrayList rpFilterArr = new ArrayList();

	CrawlConfig cc;

	protected void init(DocumentList list, int level, FileDownloader fileDownloader) {
		this.fileDownloader = fileDownloader;
		extractedCount = 0;
		cc = list.getCrawler().getConfig();
		String[] arr = cc.getUrlLevels()[level].trim().split("\n");
		urlArr.clear();
		rpArr.clear();
		for (int i = 0; i < arr.length; i++) {
			String url = arr[i].trim();
			if (StringUtil.isEmpty(url)) {
				continue;
			}
			url = url.trim();
			url = SearchUtil.escapeUrl(url);
			RegexParser rp = new RegexParser(url);
			urlArr.add(url);
			rpArr.add(rp);
		}
		if (cc.isFilterFlag()) {
			arr = cc.getFilterExpr().trim().split("\n");
			for (int i = 0; i < arr.length; i++) {
				String url = arr[i].trim();
				if (StringUtil.isEmpty(url)) {
					continue;
				}
				url = url.trim();
				RegexParser rp = new RegexParser(url);
				rpFilterArr.add(rp);
			}
		}
	}

	public void extract(DocumentList list, int level, FileDownloader fileDownloader) {
		init(list, level, fileDownloader);
		aliveThreadCount = threadCount;
		size = list.size();
		list.moveFirst();
		for (int i = 0; i < threadCount; i++) {
			UrlExtracterThread edt = new UrlExtracterThread();
			edt.setExtracter(this);
			edt.setList(list);
			edt.setLevel(level);
			edt.setThreadIndex(i);
			edt.start();
		}
		try {
			while (aliveThreadCount != 0) {
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public boolean isMatchedUrl(String url2, String refUrl) {
		// ���˵��ض���׺
		String ext = ServletUtil.getUrlExtension(url2);

		if (StringUtil.isNotEmpty(ext)) {
			if (StringUtil.isNotEmpty(fileDownloader.getAllowExtension())
					&& fileDownloader.getAllowExtension().indexOf(ext) < 0) {
				return false;
			} else if (fileDownloader.getDenyExtension().indexOf(ext) >= 0) {
				return false;
			}
		} else {
			if (StringUtil.isNotEmpty(fileDownloader.getAllowExtension())) {
				return false;
			}
		}
		boolean matchedFlag = false;
		for (int i = 0; i < rpFilterArr.size(); i++) {
			RegexParser rp = (RegexParser) rpFilterArr.get(i);
			// �ж��Ƿ���Ҫ����
			synchronized (rp) {
				rp.setText(url2);
				if (rp.match()) {
					return false;
				}
			}
		}
		for (int i = 0; i < rpArr.size(); i++) {
			String url = (String) urlArr.get(i);
			RegexParser rp = (RegexParser) rpArr.get(i);
			// �Ƚ�����URL��ǰ���Ƿ�һ�£������һ�£�����Ҫ�ж�
			if (url.indexOf('/', 8) > 0) {
				String prefix = url.substring(0, url.indexOf('/', 8));
				if (prefix.indexOf('$') < 0) {
					if (!url2.startsWith(prefix)) {
						continue;
					}
				}
			}

			// �ж��Ƿ���Ϲ���
			synchronized (rp) {
				rp.setText(url2);
				if (rp.match()) {
					matchedFlag = true;
				}
			}
		}
		return matchedFlag;
	}
}
