package com.nswt.search.crawl;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.CircularRedirectException;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NTCredentials;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;

import com.nswt.framework.Config;
import com.nswt.framework.utility.IOUtil;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.ServletUtil;
import com.nswt.framework.utility.StringUtil;
import com.nswt.framework.utility.ZipUtil;
import com.nswt.search.DocumentList;
import com.nswt.search.SearchUtil;
import com.nswt.search.WebDocument;

/**
 * @Author ������
 * @Date 2007-9-19
 * @Mail nswt@nswt.com.cn
 */
public class FileDownloader {
	private String allowExtension;

	private String denyExtension;

	private HttpClient client;

	private int threadCount = 10;

	protected int aliveThreadCount = 0;

	protected int busyThreadCount = 0;// ��Ҫ��Ϊ��afterScript

	private boolean proxyFlag;

	private String proxyHost;

	private int proxyPort;

	private String proxyUserName;

	private String proxyPassword;

	private int timeout;

	private int currentLevel;

	protected int downloadCount;// ȫ������������

	protected int levelDownloadCount;// ���㼶����������

	protected int levelAddedCount;// ���㼶�Ѿ�����list������

	private UrlExtracter extracter;

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public void downloadList(DocumentList list, int level) {
		this.currentLevel = level;
		downloadCount = 0;
		levelDownloadCount = 0;
		levelAddedCount = 0;
		LogUtil.getLogger().info("===Level Download Start====\t Total " + list.size() + " URL");
		try {
			MultiThreadedHttpConnectionManager cm = new MultiThreadedHttpConnectionManager();
			HttpConnectionManagerParams hcmp = new HttpConnectionManagerParams();
			hcmp.setDefaultMaxConnectionsPerHost(threadCount);
			hcmp.setConnectionTimeout(timeout);
			hcmp.setSoTimeout(timeout);
			cm.setParams(hcmp);
			client = new HttpClient(cm);
			if (proxyFlag) {
				NTCredentials creds = new NTCredentials(this.proxyUserName, this.proxyPassword, this.proxyHost, "*");
				client.getState().setProxyCredentials(AuthScope.ANY, creds);
				client.getHostConfiguration().setProxy(this.proxyHost, this.proxyPort);
			}
			list.moveFirst();
			aliveThreadCount = threadCount;
			extracter = new UrlExtracter();
			extracter.init(list, level, this);
			for (int i = 0; i < threadCount; i++) {
				FileDownloadThread fdt = new FileDownloadThread();
				fdt.setDownloader(this);
				fdt.setList(list);
				fdt.setThreadIndex(i);
				fdt.setHttpClient(client);
				fdt.start();
			}
			try {
				while (aliveThreadCount != 0) {
					Thread.sleep(1000);
				}
				checkEmpty(list);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int downloadOne(WebDocument doc) {
		return downloadOne(null, doc, false, -1);
	}

	public static int downloadOne(HttpClient httpClient, WebDocument doc) {
		return downloadOne(httpClient, doc, false, -1);
	}

	public static int downloadOne(HttpClient httpClient, WebDocument doc, int threadIndex) {
		return downloadOne(httpClient, doc, false, threadIndex);
	}

	public static int downloadOne(HttpClient httpClient, WebDocument doc, boolean second, int threadIndex) {
		if (httpClient == null) {
			SimpleHttpConnectionManager cm = new SimpleHttpConnectionManager();
			HttpConnectionManagerParams hcmp = new HttpConnectionManagerParams();
			hcmp.setDefaultMaxConnectionsPerHost(1);
			hcmp.setConnectionTimeout(30000);
			hcmp.setSoTimeout(30000);
			cm.setParams(hcmp);
			httpClient = new HttpClient(cm);
		}
		String url = doc.getUrl();
		int statusCode = 200;
		HttpMethodBase gm = null;
		String charset = null;

		try {
			String host = ServletUtil.getHost(url);
			if (doc.getForm() != null) {
				Mapx form = doc.getForm();
				PostMethod pm = new PostMethod(url);
				Object[] ks = form.keyArray();
				Object[] vs = form.valueArray();
				for (int i = 0; i < form.size(); i++) {
					pm.addParameter(ks[i].toString(), vs[i].toString());
				}
				gm = pm;
			} else {
				gm = new GetMethod(url);
			}

			gm.setRequestHeader("Host", host);
			gm.setRequestHeader("User-Agent",
					"Mozilla/5.0 (Windows; U; Windows NT 5.2; zh-CN; rv:1.8.1.11) Gecko/20071127 Firefox/3.0.1 QQDownload");
			gm.setRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			// gm.setRequestHeader("Referer", doc.getRefUrl());
			httpClient.executeMethod(gm);
			// gm.getStatusCode() + ":\t" + url);
			statusCode = gm.getStatusCode();
			InputStream is = gm.getResponseBodyAsStream();
			String maxStr = Config.getValue("FileDownloader.MaxSize");
			int max = 100 * 1024 * 1024;
			if (StringUtil.isNotEmpty(maxStr)) {
				max = Integer.parseInt(maxStr);
			}
			byte[] body = IOUtil.getBytesFromStream(is, max);// ���100M
			is.close();
			String contentType = null;
			if (gm.getResponseHeader("Content-Type") != null) {
				contentType = gm.getResponseHeader("Content-Type").getValue().toLowerCase();
			} else {
				contentType = "text/html";
			}
			if (gm.getResponseHeader("Content-Encoding") != null) {
				String contentEncoding = gm.getResponseHeader("Content-Encoding").getValue().toLowerCase();
				if (contentEncoding.equals("gzip")) {
					body = ZipUtil.ungzip(body);
				} else if (contentEncoding.equals("zip")) {
					body = ZipUtil.unzip(body);
				}
			}
			Header h = gm.getResponseHeader("Last-Modified");
			if (h == null) {
				h = gm.getResponseHeader("Date");
			}
			Date lastModified = null;
			if (h != null) {
				lastModified = SearchUtil.parseLastModifedDate(h.getValue());
				if (lastModified == null) {
					LogUtil.getLogger().info(
							"Error on Get lastModified��" + h.getValue() + "\t" + url + "\t RefURL��" + doc.getRefUrl());
				}
			} else {
				lastModified = new Date();
			}
			doc.setLastDownloadTime(System.currentTimeMillis());
			doc.setContent(body);
			doc.setLastmodifiedDate(lastModified);
			doc.setErrorMessage(null);
			if (contentType.indexOf("text") < 0 && contentType.indexOf("html") < 0) {// �������ļ�
				doc.setTextContent(false);
			} else {
				doc.setTextContent(true);
				charset = gm.getResponseCharSet();
				try {
					Charset.forName(charset);
				} catch (Exception e) {
					charset = "utf-8";
				}
				if ((charset = getCharSet(body, charset)) == null) {
					if ((charset = getCharSet(body, "utf-8")) == null) {
						if ((charset = getCharSet(body, "unicode")) == null) {
							charset = getCharSet(body, "ISO-8859-1");
						}
					}
				}
				try {
					Charset.forName(charset);
				} catch (Exception e) {
					charset = "utf-8";
				}
				if (charset.equalsIgnoreCase("utf-8") && !StringUtil.isUTF8(body)) {
					charset = "gbk";
				}
				if (charset.equalsIgnoreCase("gb2312")) {// ����ΪGB2312����ʵ���Ͽ�����GBK,Ϊ��������ʺţ�ȫ��ͳһ����ΪGBK
					charset = "gbk";
				}
				doc.setCharset(charset);
			}
		} catch (UnsupportedEncodingException e) {
			doc.setErrorMessage("�޷�������ļ�����:" + charset);
			LogUtil.getLogger().info(
					"Error Charset,URL not been Downloaded��" + charset + "\t" + url + "\t RefURL��" + doc.getRefUrl());
		} catch (IllegalArgumentException e) {
			if (second) {
				e.printStackTrace();
				doc.setErrorMessage("URL�﷨����ȷ");
				LogUtil.getLogger().info(
						"URL Syntax Error,URL not been Downloaded��\t" + url + "\t RefURL��" + doc.getRefUrl());
			} else {
				statusCode = downloadOne(httpClient, doc, true, threadIndex);
			}
		} catch (CircularRedirectException e) {
			doc.setErrorMessage("URLѭ���ض���");
			LogUtil.getLogger().info(
					gm.getStatusCode() + ":Circular Redirect,URL not been Downloaded��\t" + url + "\t RefURL��"
							+ doc.getRefUrl());
		} catch (HttpException e) {
			if (second) {
				e.printStackTrace();
				doc.setErrorMessage("HttpException,�ڶ�������ʧ��.");
				LogUtil.getLogger().info(
						"HttpException,Failed With 2 Times Retry��\t" + url + "\t RefURL��" + doc.getRefUrl());
			} else {
				statusCode = downloadOne(httpClient, doc, true, threadIndex);
			}
		} catch (IOException e) {
			if (second) {
				doc.setErrorMessage("IOException,�������Խ�ʧ��");
				LogUtil.getLogger().info(
						"IOException,Failed With 2 Times Retry��\t" + url + "\t RefURL��" + doc.getRefUrl());
			} else {
				statusCode = downloadOne(httpClient, doc, true, threadIndex);
			}
		} finally {
			if (gm != null) {
				gm.releaseConnection();
			}
		}
		return statusCode;
	}

	public static final Pattern charsetPattern = Pattern
			.compile(
					"<meta\\s*?http\\-equiv.*?content-type.*?content\\s*?=.*?charset\\s*?=\\s*?([^\\\"\\\'\\s]+?)(\\\"|\\\'|\\s|>|(/>))",
					Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	public static String getCharSet(byte[] body, String charset) {
		String tmp = null;
		try {
			tmp = new String(body, charset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Matcher m = charsetPattern.matcher(tmp);
		if (m.find()) {
			return m.group(1);
		}
		return null;
	}

	public boolean isMatchedUrl(String url, String refUrl) {
		return extracter.isMatchedUrl(url, refUrl);
	}

	public void checkEmpty(DocumentList list) {
		LogUtil.getLogger().info("===Check Content Empty URL====\tTotal " + list.size() + " URL");
		int t = threadCount / 3;
		if (t == 0) {
			t = 1;
		}
		this.aliveThreadCount = t;
		for (int i = 0; i < t; i++) {
			FileDownloadThread fdt = new FileDownloadThread();
			fdt.setDownloader(this);
			fdt.setList(list);
			fdt.setThreadIndex(i);
			fdt.setHttpClient(client);
			fdt.setCheckEmpty(true);
			fdt.start();
		}
		try {
			while (aliveThreadCount != 0) {
				Thread.sleep(1000);
			}
			return;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public String getDenyExtension() {
		return denyExtension;
	}

	public void setDenyExtension(String denyExtension) {
		this.denyExtension = denyExtension;
	}

	public String getAllowExtension() {
		return allowExtension;
	}

	public void setAllowExtension(String allowExtension) {
		this.allowExtension = allowExtension;
	}

	public int getThreadCount() {
		return threadCount;
	}

	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}

	public String getProxyHost() {
		return proxyHost;
	}

	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}

	public String getProxyPassword() {
		return proxyPassword;
	}

	public void setProxyPassword(String proxyPassword) {
		this.proxyPassword = proxyPassword;
	}

	public int getProxyPort() {
		return proxyPort;
	}

	public void setProxyPort(int proxyPort) {
		this.proxyPort = proxyPort;
	}

	public String getProxyUserName() {
		return proxyUserName;
	}

	public void setProxyUserName(String proxyUserName) {
		this.proxyUserName = proxyUserName;
	}

	public int getCurrentLevel() {
		return currentLevel;
	}

	public void setCurrentLevel(int currentLevel) {
		this.currentLevel = currentLevel;
	}

	public boolean isProxyFlag() {
		return proxyFlag;
	}

	public void setProxyFlag(boolean proxyFlag) {
		this.proxyFlag = proxyFlag;
	}
}
