package com.nswt.search.crawl;

import java.util.Arrays;
import java.util.Date;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.HeadMethod;
import org.apache.commons.lang.ArrayUtils;

import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.ServletUtil;
import com.nswt.framework.utility.StringUtil;
import com.nswt.search.DocumentList;
import com.nswt.search.SearchUtil;
import com.nswt.search.WebDocument;
import com.nswt.search.index.IndexUtil;

/**
 * @Author NSWT
 * @Date 2008-2-23
 * @Mail nswt@nswt.com.cn
 */
public class FileDownloadThread extends Thread {
	private HttpClient httpClient;

	private int threadIndex;

	private WebDocument doc;

	private DocumentList list;

	private long startTime;

	private FileDownloader downloader;

	private boolean isCheckEmpty;

	private boolean isBusy;

	private CrawlConfig config;

	private int maxListCount;

	private int maxPageCount;

	public FileDownloader getDownloader() {
		return downloader;
	}

	public void setDownloader(FileDownloader downloader) {
		this.downloader = downloader;
	}

	public FileDownloadThread() {
		setName("FileDownloadThread " + this.threadIndex);
	}

	public int getThreadIndex() {
		return threadIndex;
	}

	public void setThreadIndex(int threadIndex) {
		this.threadIndex = threadIndex;
	}

	private void waitOtherThread() {
		if (isBusy) {
			synchronized (downloader) {
				this.downloader.busyThreadCount--;
			}
		}
		isBusy = false;
		try {
			Thread.sleep(500);
			for (int i = 0; i < 100; i++) {
				if (doc == null && this.downloader.busyThreadCount > 0) {// afterScript�п��ܻ�ִ��addUrl
					Thread.sleep(500);
					doc = isCheckEmpty ? list.nextEmpty() : list.next();
				} else {
					break;
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		config = list.getCrawler().getConfig();
		maxListCount = config.getMaxListCount();
		maxPageCount = config.getMaxPageCount();

		startTime = System.currentTimeMillis() - 100;// ���߳̿��ܻ���ʱ���
		doc = isCheckEmpty ? list.nextEmpty() : list.next();
		if (doc == null) {
			waitOtherThread();
		}
		while (doc != null) {
			if (!list.getCrawler().task.checkStop()) {
				if (!isBusy) {
					synchronized (downloader) {
						this.downloader.busyThreadCount++;
					}
				}
				if (maxPageCount > 0 && config.getUrlLevels().length == doc.getLevel() + 1) {
					if (downloader.levelDownloadCount >= maxPageCount || threadIndex > maxPageCount) {
						break;// ֻ����һ���㼶�ж���������ҳ����
					}
				}
				if (maxListCount > 0 && config.getUrlLevels().length == downloader.getCurrentLevel() + 2) {
					if (downloader.levelDownloadCount >= maxListCount || threadIndex > maxListCount) {
						break;// �����ڶ������Ҫ�����б�ҳ����
					}
				}
				isBusy = true;
				if (doc.getLevel() == downloader.getCurrentLevel()) {
					String statusCode = "200";
					if (doc.getLastDownloadTime() < startTime && doc.getLevel() == this.downloader.getCurrentLevel()) {
						if (isNeedDownload()) {
							statusCode = "" + dealOneUrl();
						} else {
							statusCode = "---";// ---��ʾ����Ҫ����
						}
					}
					long percent = Math.round(10000.0 * downloader.downloadCount / list.size());
					long p = percent % 100;
					String pStr = "" + p;
					if (p < 10) {
						pStr = "0" + p;
					}
					LogUtil.getLogger().info(
							(threadIndex < 0 ? "" : threadIndex + "\t") + statusCode + "\tL" + doc.getLevel() + "\t"
									+ percent / 100 + "." + pStr + "% \t" + doc.getUrl());
				}
				doc = isCheckEmpty ? list.nextEmpty() : list.next();
				if (doc == null) {
					waitOtherThread();
				}
			} else {// �̱߳��ֹ���ֹ
				if (isBusy) {
					synchronized (downloader) {
						this.downloader.busyThreadCount--;
					}
				}
				doc = null;
				isBusy = false;
			}
		}
		synchronized (downloader) {
			this.downloader.aliveThreadCount--;
		}
	}

	public int dealOneUrl() {
		String url = doc.getUrl();
		setName("FileDownloadThread " + url);
		int level = doc.getLevel();
		String ref = doc.getRefUrl();
		int statusCode = FileDownloader.downloadOne(httpClient, doc, threadIndex);
		if (doc.getContent() == null) {// �˴�������δд��FileCachedMapx
			return statusCode;
		}

		// ����ҳ���ڲ����ض��򣬿��ܻ��ж���ض���
		while (doc.getContent().length < 1024) {
			String redirectURL = SearchUtil.getRedirectURL(doc);
			if (StringUtil.isNotEmpty(redirectURL)) {
				redirectURL = SearchUtil.normalizeUrl(redirectURL, doc.getUrl());
				WebDocument rdoc = new WebDocument();
				rdoc.setLevel(doc.getLevel());
				rdoc.setRefUrl(doc.getUrl());
				rdoc.setUrl(redirectURL);
				FileDownloader.downloadOne(httpClient, rdoc, threadIndex);
				doc.setContent(rdoc.getContent());
				doc.setCharset(rdoc.getCharset());
			} else {
				break;
			}
		}
		doc.setRefUrl(ref);
		list.put(doc);
		downloader.levelAddedCount++;

		CrawlScriptUtil util = new CrawlScriptUtil();
		util.doc = doc;
		util.list = list;
		list.getCrawler().executeScript("after", util);

		synchronized (downloader) {
			downloader.downloadCount++;
			downloader.levelDownloadCount++;
		}

		if (doc.isTextContent() && !isStopUrl(doc)) {
			String[] urls = SearchUtil.getPageURL(doc);
			// ����ȥ��
			for (int i = 0; i < urls.length; i++) {
				if (urls[i].startsWith("?")) {// ������Ҫ����
					String fileName = ServletUtil.getFileName(url);
					urls[i] = fileName + urls[i];
				}
				urls[i] = SearchUtil.normalizeUrl(urls[i], url);
			}
			Arrays.sort(urls);
			for (int i = urls.length - 1; i > 0; i--) {
				if (urls[i].equals(urls[i - 1])) {
					urls[i] = null;
				}
			}
			for (int i = 0; i < urls.length; i++) {
				if (StringUtil.isEmpty(urls[i])) {
					continue;
				}
				String url2 = urls[i];
				// asp.net
				Mapx form = null;
				if (url2.toLowerCase().indexOf("javascript:") > 0) {
					form = SearchUtil.getDotNetForm(doc.getContentText(), url2);
					StringBuffer sb = new StringBuffer();
					Object[] vs = form.valueArray();
					for (int j = 0; j < form.size(); j++) {
						sb.append(",");
						sb.append(vs[j]);
					}
					if (url.indexOf("?") < 0) {
						url2 = url + "?__MD5=" + StringUtil.md5Hex(sb.toString());
					} else {
						if (url.indexOf("__MD5") > 0) {
							url2 = url.substring(0, url.indexOf("__MD5")) + "__MD5=" + StringUtil.md5Hex(sb.toString());
						} else {
							url2 = url + "&__MD5=" + StringUtil.md5Hex(sb.toString());
						}
					}
				}
				if (doc.getLevel() != 0 && !downloader.isMatchedUrl(url2, url)) {
					continue;
				}
				if (list.containsKey(url2)) {
					continue;
				}
				WebDocument doc2 = new WebDocument();
				doc2 = new WebDocument();
				doc2.setUrl(url2);
				doc2.setLevel(level);
				doc2.setRefUrl(url);
				doc2.setForm(form);
				doc2.setPageUrl(true);

				// ��������һ�㣬����Ҫ�Զ��ϲ���������
				if (config.getUrlLevels().length == doc.getLevel() + 1) {
					FileDownloader.downloadOne(httpClient, doc2, threadIndex);
					if (IndexUtil.getTextFromHtml(doc.getContentText()).indexOf(
							IndexUtil.getTextFromHtml(doc2.getContentText())) >= 0) {
						continue;
					}
					byte[] bs = doc2.getContent();
					if (bs != null) {
						doc.setContent(ArrayUtils.addAll(doc.getContent(), bs));
						list.put(doc);
					}
				} else {
					synchronized (downloader) {// ���߳���ֻ������һ���߳̽���˴�
						if (maxListCount > 0 && config.getUrlLevels().length == downloader.getCurrentLevel() + 2
								&& downloader.levelAddedCount >= maxListCount) {
							break;// �����ڶ������Ҫ�����б�ҳ����
						}
						list.put(doc2);// �����ڶ������Ҫ�����б�ҳ����
						downloader.levelAddedCount++;
					}
				}
			}
		}
		return statusCode;
	}

	/**
	 * �������޷�ҳ�����,��ҲҪ��ֹ����
	 */
	public boolean isStopUrl(WebDocument doc) {
		if (doc.isPageUrl()) {
			WebDocument refDoc = doc.getList().get(doc.getRefUrl());
			if (refDoc.isPageUrl()) {
				String str1 = doc.getContentText();
				String str2 = refDoc.getContentText();
				if (Math.abs(str1.length() - str2.length()) < 5) {
					int is = 0, ie = 0;
					for (int i = 0; i < str1.length() && i < str2.length(); i++) {
						if (str1.charAt(i) != str2.charAt(i)) {
							is = i;
							break;
						}
					}
					for (int i = 1; i <= str1.length() && i <= str2.length(); i++) {
						if (str1.charAt(str1.length() - i) != str2.charAt(str2.length() - i)) {
							ie = i;
							break;
						}
					}
					if (ie < is) {// �������
						return false;
					}
					String str = str2.substring(is, ie);
					if (str.length() < 5000) {
						if (str.indexOf('>') < 0) {
							return false;
						}
						str = str.substring(str.indexOf('>'));
						if (str.indexOf('<') < 0) {
							return false;
						}
						str = str.substring(0, str.lastIndexOf('<'));
						str = str.replaceAll("<.*?>", "");
						if (str.length() < 20) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public boolean isNeedDownload() {
		String url = doc.getUrl();
		String ext = ServletUtil.getUrlExtension(url);
		if (!ext.equals(".gif") && !ext.equals(".jpg") && !ext.equals(".png") && !ext.equals(".jpeg")) {
			if (doc.getLastmodifiedDate() == null) {
				return true;
			} else if (!list.hasContent(url)) {
				return true;
			}
		}
		Date date = doc.getLastmodifiedDate();
		HeadMethod hm = new HeadMethod(url);
		String lastModified = null;
		try {
			httpClient.executeMethod(hm);
			Header h = hm.getRequestHeader("Content-Length");
			if (h != null) {
				int len = Integer.parseInt(h.getValue().trim());
				if (list.getCrawler().getConfig().getType() == CrawlConfig.TYPE_CUSTOMTABLE) {
					if (ext.equals(".gif") || ext.equals(".jpg") || ext.equals(".png") || ext.equals(".jpeg")) {
						if (len < 20000) {
							return false;
						}
					}
				}
				if (len == doc.getContent().length) {
					return false;
				}
			}
			h = hm.getResponseHeader("Last-Modified");
			if (h == null) {
				h = hm.getResponseHeader("Date");
			}
			if (h == null) {
				return true;
			}
			lastModified = h.getValue();
		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			hm.releaseConnection();
		}
		try {
			if (lastModified != null && date.equals(SearchUtil.parseLastModifedDate(lastModified))) {
				return false;
			}
		} catch (Exception e) {
		}
		return true;
	}

	public HttpClient getHttpClient() {
		return httpClient;
	}

	public void setHttpClient(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	public DocumentList getList() {
		return list;
	}

	public void setList(DocumentList list) {
		this.list = list;
	}

	public boolean isCheckEmpty() {
		return isCheckEmpty;
	}

	public void setCheckEmpty(boolean isCheckEmpty) {
		this.isCheckEmpty = isCheckEmpty;
	}

}