package com.nswt.search.crawl;

import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.RegexParser;
import com.nswt.search.DocumentList;
import com.nswt.search.WebDocument;

/**
 * @Author NSWT
 * @Date 2008-2-21
 * @Mail nswt@nswt.com.cn
 */
public class CrawlScriptUtil {
	protected DocumentList list;

	protected WebDocument doc;

	protected Mapx map;

	public String getField(String content, String regex, String key) {
		RegexParser rp = new RegexParser(regex);
		rp.setText(content);
		if(rp.match()){
			return map.getString(key);
		}
		return null;		
	}

	public String getCurrentUrlField(String field) {
		return map.getString(field);
	}

	public String getCurrentUrl() {
		return doc.getUrl();
	}

	public int getCurrentLevel() {
		return doc.getLevel();
	}

	public void removeUrl(String url) {
		list.remove(url);
	}

	public void removeCurrentUrl() {
		list.remove(doc.getUrl());
	}

	public void addUrl(String url) {
		addUrl(url, doc.getLevel());
	}

	public String getCurrentContent() {
		return doc.getContentText();
	}

	public String getContent(String url) {
		WebDocument wd = list.get(url);
		if (wd != null) {
			return wd.getContentText();
		}
		return null;
	}

	public void addUrl(String url, int level) {
		WebDocument wd = new WebDocument();
		wd.setUrl(url);
		wd.setLevel(level);
		wd.setRefUrl(doc.getUrl());
		list.put(wd);
	}

	public void addDocument(String url, String content) {

	}
}
