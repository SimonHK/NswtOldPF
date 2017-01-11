package com.nswt.search.crawl;

import java.io.File;

import org.apache.commons.lang.ArrayUtils;

import com.nswt.framework.Config;
import com.nswt.framework.messages.LongTimeTask;
import com.nswt.framework.script.EvalException;
import com.nswt.framework.script.ScriptEngine;
import com.nswt.framework.utility.Filter;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.StringUtil;
import com.nswt.search.DocumentList;
import com.nswt.search.WebDocument;

/**
 * @Author 王育春
 * @Date 2008-2-20
 * @Mail nswt@nswt.com.cn
 */
public class Crawler {

	private CrawlConfig config;

	private DocumentList list;// 当前文档列表

	private int startLevel;

	private FileDownloader fileDownloader = new FileDownloader();

	private UrlExtracter extracter = new UrlExtracter();

	private int currentLevel;

	private int currentLevelCount;

	private int currentLevelDownloadedCount;

	private Filter retryFilter;

	public LongTimeTask task;

	public LongTimeTask getTask() {
		return task;
	}

	public Crawler() {
		this(null);
	}

	public Crawler(LongTimeTask ltt) {
		task = ltt;
		if (ltt == null) {
			task = LongTimeTask.createEmptyInstance();
		}
	}

	private void prepareList() {
		if (list == null) {
			String path = Config.getContextRealPath() + CrawlConfig.getWebGatherDir();
			if (!path.endsWith("/") && !path.endsWith("\\")) {
				path = path + "/";
			}
			path = path + config.getID() + "/";
			File f = new File(path);
			if (!f.exists()) {
				f.mkdirs();
			}
			list = new DocumentList(path);
		}
	}

	public DocumentList crawl() {
		prepareList();
		try {
			list.setCrawler(this);
			fileDownloader
					.setDenyExtension(".gif.jpg.jpeg.swf.bmp.png.js.wmv.css.ico.avi.mpg.mpeg.mp3.mp4.wma.rm.rmvb.exe.tar.gz.zip.rar");
			fileDownloader.setThreadCount(config.getThreadCount());
			fileDownloader.setTimeout(config.getTimeout() * 1000);

			if (config.isProxyFlag()) {
				fileDownloader.setProxyFlag(config.isProxyFlag());
				fileDownloader.setProxyHost(config.getProxyHost());
				fileDownloader.setProxyPassword(config.getProxyPassword());
				fileDownloader.setProxyUserName(config.getProxyUserName());
				fileDownloader.setProxyPort(config.getProxyPort());
			} else if ("Y".equalsIgnoreCase(Config.getValue("Proxy.IsUseProxy"))) { // 如果系统设置了，则默认为系统配置的代理
				fileDownloader.setProxyFlag(true);
				fileDownloader.setProxyHost(Config.getValue("Proxy.Host"));
				fileDownloader.setProxyPassword(Config.getValue("Proxy.Password"));
				fileDownloader.setProxyUserName(Config.getValue("Proxy.UserName"));
				fileDownloader.setProxyPort(Integer.parseInt(Config.getValue("Proxy.Port")));
			}

			prepareScript();
			for (int i = 0; i < config.getUrlLevels().length && i <= config.getMaxLevel(); i++) {
				try {
					if (i < startLevel && !task.checkStop()) {
						continue;
					}
					task.setCurrentInfo("正在处理第" + (i + 1) + "层级URL");
					currentLevel = i;
					dealOneLevel();
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
			if (!task.checkStop()) {
				// 最后下载图片
				if (config.isCopyImageFlag()) {
					int maxPage = config.getMaxPageCount();
					config.setMaxPageCount(-1);
					fileDownloader
							.setDenyExtension(".html.htm.jsp.php.asp.shtml.swf.js.css.ico.avi.mpg.mpeg.mp3.mp4.wma.wmv.rm.rmvb");
					currentLevel++;
					task.setCurrentInfo("正在处理第" + (currentLevel + 1) + "层级URL");
					String[] urls = config.getUrlLevels();
					String AllowExtension = fileDownloader.getAllowExtension();
					urls = (String[]) ArrayUtils.add(urls, "${A}");
					config.setUrlLevels(urls);
					fileDownloader
							.setAllowExtension(".gif.jpg.jpeg.swf.bmp.png.doc.docx.xls.xlsx.ppt.pptx.rar.zip.exe.dat.gz.msi");
					dealOneLevel();
					config.setMaxPageCount(maxPage);
					fileDownloader
							.setDenyExtension(".gif.jpg.jpeg.swf.bmp.png.js.wmv.css.ico.avi.mpg.mpeg.mp3.mp4.wma.rm.rmvb.exe.tar.gz.zip.rar");
					fileDownloader.setAllowExtension(AllowExtension);
				} else {
					String[] urls = config.getUrlLevels();
					urls = (String[]) ArrayUtils.add(urls, "${A}");
					config.setUrlLevels(urls);// 不然ArticleWriter不会处理
				}
				retryWithFilter();
				writeArticle();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			return null;
		} finally {
			list.save();
			list.close();
		}
		return list;
	}

	public void writeArticle() {
		prepareList();
		ArticleWriter writer = new ArticleWriter();
		writer.setTask(this.task);
		writer.setList(this.list);
		writer.setConfig(this.config);
		writer.write();
	}

	public void retryWithFilter() {
		if (this.retryFilter != null) {
			LogUtil.getLogger().info("Retry Downloading URL ........");
			WebDocument doc = list.next();
			while (doc != null) {
				if (retryFilter.filter(doc)) {
					FileDownloader.downloadOne(doc);
					if (list.hasContent(doc.getUrl())) {
						list.put(doc);
					}
				}
				doc = list.next();
			}
		}
	}

	private ScriptEngine se;

	private void prepareScript() throws EvalException {
		StringBuffer sb = new StringBuffer();
		if (StringUtil.isNotEmpty(config.getScript())) {
			se = new ScriptEngine(config.getLanguage());
			se.importPackage("com.nswt.framework.data");
			se.importPackage("com.nswt.framework.utility");
			if (config.getLanguage() == ScriptEngine.LANG_JAVA) {
				sb.append("StringBuffer _Result = new StringBuffer();\n");
				sb.append("void write(String str){_Result.append(str);}\n");
				sb.append("void writeln(String str){_Result.append(str+\"\\n\");}\n");
				sb.append(config.getScript());
				sb.append("\nreturn _Result.toString();\n");
			} else {
				sb.append("var _Result = [];\n");
				sb.append("function write(str){_Result.push(str);}\n");
				sb.append("function writeln(str){_Result.push(str+\"\\n\");}\n");
				sb.append(config.getScript());
				sb.append("\nreturn _Result.join('');\n");
			}
			se.compileFunction("_EvalScript", sb.toString());
		}
	}

	private double total = 0;

	public void executeScript(String when, CrawlScriptUtil util) {
		currentLevelDownloadedCount++;
		if (when.equalsIgnoreCase("after")) {
			task.setCurrentInfo("正在抓取" + util.getCurrentUrl());
		}
		if (total == 0) {
			for (int i = 0; i < config.getUrlLevels().length + 1; i++) {
				total += (i + 1) * (i + 1) * 400;
			}
		}
		double t = (currentLevel + 1) * (currentLevel + 1) * 400;
		t = t / total + (currentLevel + 1) * (currentLevel + 2) / total
				* (currentLevelDownloadedCount * 1.0 / currentLevelCount);
		int percent = new Double(t * 100).intValue();
		task.setPercent(percent);
		if (StringUtil.isNotEmpty(config.getScript())) {
			se.setVar("Util", util);
			se.setVar("When", when);
			se.setVar("Level", new Integer(currentLevel));
			try {
				se.executeFunction("_EvalScript");
			} catch (EvalException e) {
				e.printStackTrace();
			}
		}
	}

	private void dealOneLevel() {
		String[] arr = config.getUrlLevels()[currentLevel].trim().split("\n");
		task.setCurrentInfo("正在生成第" + (currentLevel + 1) + "层级URL");
		currentLevelCount = 0;
		if (currentLevel != 0) {
			extracter.extract(list, currentLevel, fileDownloader);
		} else {
			for (int i = 0; i < arr.length; i++) {
				String url = arr[i];
				if (StringUtil.isEmpty(url)) {
					continue;
				}
				if (!list.containsKey(url)) {
					WebDocument doc = new WebDocument();
					doc.setUrl(url);
					doc.setLevel(currentLevel);
					list.put(doc);
				}
			}
		}
		currentLevelCount = list.size();
		fileDownloader.downloadList(list, currentLevel);
	}

	public long getTaskID() {
		return config.getID();
	}

	public int getStartLevel() {
		return startLevel;
	}

	public void setStartLevel(int startLevel) {
		this.startLevel = startLevel;
	}

	public Filter getRetryFilter() {
		return retryFilter;
	}

	public void setRetryFilter(Filter retryFilter) {
		this.retryFilter = retryFilter;
	}

	public DocumentList getList() {
		return list;
	}

	public void setList(DocumentList list) {
		this.list = list;
	}

	public FileDownloader getFileDownloader() {
		return fileDownloader;
	}

	public CrawlConfig getConfig() {
		return config;
	}

	public void setConfig(CrawlConfig config) {
		this.config = config;
	}
}
