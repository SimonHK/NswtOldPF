package com.nswt.search.index;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

import com.nswt.framework.Config;
import com.nswt.framework.utility.DateUtil;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.StringUtil;
import com.nswt.search.DocumentList;
import com.nswt.search.HtmlTextExtracter;
import com.nswt.search.WebDocument;

/**
 * @Author NSWT
 * @Date 2008-12-12
 * @Mail nswt@nswt.com.cn
 */
public class DefaultIndexer extends Indexer {
	private long id;

	public DefaultIndexer(long id) {
		setPath(Config.getContextRealPath() + "WEB-INF/data/index/Crawl" + id);
		this.id = id;
		setSingleThreadMode(false);
	}

	public void update() {

	}

	public void create() {
		try {
			DocumentList list = new DocumentList(Config.getContextRealPath() + "WEB-INF/data/" + id + "/");
			WebDocument wdoc = list.next();
			while (wdoc != null) {
				if (wdoc.isTextContent()) {
					String content = wdoc.getContentText();
					HtmlTextExtracter hte = new HtmlTextExtracter();
					hte.setHtml(content);
					hte.setUrl(wdoc.getUrl());
					String text = hte.getContentText();
					if (StringUtil.isNotEmpty(text)) {
						String title = StringUtil.getHtmlTitle(content);
						if (StringUtil.isNotEmpty(title) && text.length() > 100) {
							Document idoc = new Document();
							idoc.add(new Field("CONTENT", text, Field.Store.YES, Field.Index.ANALYZED));
							idoc.add(new Field("TITLE", StringUtil.getHtmlTitle(content), Field.Store.YES,
									Field.Index.ANALYZED));
							idoc.add(new Field("URL", wdoc.getUrl(), Field.Store.YES, Field.Index.NOT_ANALYZED));
							idoc.add(new Field("DATE", DateUtil.toDateTimeString(wdoc.getLastmodifiedDate()),
									Field.Store.YES, Field.Index.NOT_ANALYZED));
							idoc.add(new Field("DATE", DateUtil.toDateTimeString(wdoc.getLastmodifiedDate()),
									Field.Store.YES, Field.Index.NOT_ANALYZED));
							double k1 = 2 * Math.pow(1.0 / wdoc.getLevel(), 0.5);
							double k2 = Math.pow(0.01, Math.pow(1 / text.length(), 0.5));
							idoc.setBoost((float) (k1 + k2));
							addDocument(idoc);
						}
					} else {
						LogUtil.info("未能提取正文:" + wdoc.getUrl());
					}
				}
				wdoc = list.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		DefaultIndexer indexer = new DefaultIndexer(8);
		indexer.start();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
