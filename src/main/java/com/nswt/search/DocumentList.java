package com.nswt.search;

import com.nswt.framework.utility.FileCachedMapx;
import com.nswt.framework.utility.FileUtil;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.StringUtil;
import com.nswt.framework.utility.FileCachedMapx.Entry;
import com.nswt.search.crawl.Crawler;

/**
 * @Author NSWT
 * @Date 2008-2-20
 * @Mail nswt@nswt.com.cn
 */
public class DocumentList {
	private FileCachedMapx docMap = null;

	private FileCachedMapx contentMap = null;

	public Entry lastEntry = null;

	public Entry lastEmptyEntry = null;

	private String path;

	private Crawler crawler;

	public Crawler getCrawler() {
		return crawler;
	}

	public void setCrawler(Crawler crawler) {
		this.crawler = crawler;
	}

	public DocumentList(String cacheDir) {
		path = cacheDir;
		if (!path.endsWith("/") && !path.endsWith("\\")) {
			path = path + "/";
		}
		docMap = new FileCachedMapx(path + "doc/", 1000000);
		docMap.setMaxItemInMemory(0);
		contentMap = new FileCachedMapx(path + "content/", 1000000);
		contentMap.setMaxItemInMemory(0);
	}

	/**
	 * 考虑到list在不断地变化，有这样的可能：当前己无下一个元素getNextDocument()返回null，将来添加了元素，
	 * 则getNextDocument不能返回null
	 */
	public synchronized WebDocument next() {
		Entry e = null;
		if (lastEntry == null) {
			e = lastEntry = docMap.firstEntry();
		} else {
			e = lastEntry.next();
			if (e != null) {
				lastEntry = e;
			}
		}
		if (e == null) {
			return null;
		}
		WebDocument doc = new WebDocument();
		doc.parseBytes((byte[]) e.getValue());
		if (doc.getUrl() == null) {
			LogUtil.info("DocumentList:发生致命错误：" + e.getKey());
		}
		doc.setList(this);
		return doc;
	}

	public synchronized WebDocument nextEmpty() {
		Entry e = null;
		if (lastEmptyEntry == null) {
			e = docMap.firstEntry();
		} else {
			e = lastEmptyEntry.next();
		}
		while (e != null) {
			if (!contentMap.containsKey(e.getKey())) {
				lastEmptyEntry = e;
				break;
			}
			e = e.next();
		}
		if (e == null) {
			return null;
		}
		WebDocument doc = new WebDocument();
		doc.parseBytes((byte[]) e.getValue());
		doc.setList(this);
		return doc;
	}

	public synchronized void moveFirst() {
		lastEntry = null;
	}

	public synchronized void put(WebDocument doc) {
		String url = dealUrl(doc.getUrl());
		doc.setUrl(url);
		docMap.put(url, doc.toBytes());
		doc.setList(this);
		if (doc.getContent() != null) {
			contentMap.put(url, doc.getContent());
		}
	}

	public synchronized boolean hasContent(String url) {
		return contentMap.containsKey(url);
	}

	public synchronized void remove(String url) {
		url = dealUrl(url);
		docMap.remove(url);
		contentMap.remove(url);
	}

	public WebDocument get(String url) {
		url = dealUrl(url);
		WebDocument doc = new WebDocument();
		byte[] bs = docMap.getByteArray(url);
		if (bs == null) {
			return null;
		}
		doc.parseBytes(bs);
		doc.setList(this);
		return doc;
	}

	public boolean containsKey(String url) {
		return docMap.containsKey(url);
	}

	public byte[] getContent(String url) {
		url = dealUrl(url);
		return (byte[]) contentMap.get(url);
	}

	public int size() {
		return docMap.size();
	}

	public void save() {
		docMap.save();
		contentMap.save();
	}

	public void close() {
		docMap.close();
		contentMap.close();
	}

	/**
	 * 转化成文件夹
	 */
	public void toDir(String path) {
		moveFirst();
		WebDocument doc = next();
		while (doc != null) {
			if (hasContent(doc.getUrl())) {
				String url = doc.getUrl();
				FileUtil.writeByte(path + "/" + StringUtil.urlEncode(url), doc.getContent());
			}
			doc = next();
		}
	}

	public boolean delete() {
		close();
		if (FileUtil.delete(path)) {
			docMap = null;
			contentMap = null;
			return true;
		} else {
			return false;
		}
	}

	private static String dealUrl(String url) {
		if (url.endsWith("/")) {
			url = url.substring(0, url.length() - 1);
		}
		if (url.indexOf("#") > 0) {
			url = url.substring(0, url.indexOf("#"));
		}
		url = SearchUtil.escapeUrl(url);
		return url;
	}
}
