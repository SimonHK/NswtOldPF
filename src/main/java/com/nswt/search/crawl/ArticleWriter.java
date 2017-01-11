package com.nswt.search.crawl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;

import com.nswt.cms.api.ArticleAPI;
import com.nswt.cms.datachannel.Deploy;
import com.nswt.cms.document.Article;
import com.nswt.cms.pub.CatalogUtil;
import com.nswt.cms.pub.SiteUtil;
import com.nswt.cms.site.Catalog;
import com.nswt.framework.Config;
import com.nswt.framework.User;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.extend.ExtendManager;
import com.nswt.framework.messages.LongTimeTask;
import com.nswt.framework.utility.DateUtil;
import com.nswt.framework.utility.FileUtil;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.NumberUtil;
import com.nswt.framework.utility.RegexParser;
import com.nswt.framework.utility.ServletUtil;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.pub.ImageUtilEx;
import com.nswt.platform.pub.NoUtil;
import com.nswt.platform.pub.OrderUtil;
import com.nswt.schema.ZCArticleSchema;
import com.nswt.schema.ZCArticleSet;
import com.nswt.schema.ZCAttachmentSchema;
import com.nswt.schema.ZCAttachmentSet;
import com.nswt.schema.ZCImageSchema;
import com.nswt.schema.ZCImageSet;
import com.nswt.search.DocumentList;
import com.nswt.search.SearchUtil;
import com.nswt.search.WebDocument;

/*
 * @Author NSWT
 * @Date 2016-8-5
 * @Mail nswt@nswt.com.cn
 */
public class ArticleWriter {
	private DocumentList list;

	private LongTimeTask task;

	private CrawlConfig config;

	Pattern framePattern = Pattern.compile("<iframe.*?<\\/iframe>", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);

	Pattern stylePattern = Pattern.compile("<style.*?><\\/style>", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);

	Pattern scriptPattern = Pattern.compile("<script.*?<\\/script>", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);

	Pattern linkPattern = Pattern.compile("<a .*?>(.*?)<\\/a>", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);

	Pattern tagPattern = Pattern.compile("<.*?>", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);

	public void setList(DocumentList list) {
		this.list = list;
	}

	public void setTask(LongTimeTask task) {
		this.task = task;
	}

	public void setConfig(CrawlConfig config) {
		this.config = config;
	}

	public void write() {
		if (config.getType() == CrawlConfig.TYPE_DOCUMENT) {
			if (StringUtil.isEmpty(CatalogUtil.getSiteID(config.getCatalogID()))) {
				LogUtil.getLogger().warn("文档采集的目的栏目不存在：ID=" + config.getCatalogID());
				return;
			}
			QueryBuilder imageQB = new QueryBuilder("select id from zccatalog where type=" + Catalog.IMAGELIB
					+ " and siteid=?", Long.parseLong(CatalogUtil.getSiteID(config.getCatalogID())));
			String imageCatalogID = imageQB.executeString();
			String sitePath = SiteUtil.getAbsolutePath(CatalogUtil.getSiteID(config.getCatalogID()));
			String imagePath = "upload/Image/" + CatalogUtil.getAlias(imageCatalogID) + "/";

			QueryBuilder attachQB = new QueryBuilder("select id from zccatalog where type=" + Catalog.ATTACHMENTLIB
					+ " and siteid=?", Long.parseLong(CatalogUtil.getSiteID(config.getCatalogID())));
			String attachCatalogID = attachQB.executeString();
			String attachPath = "upload/Attach/" + CatalogUtil.getAlias(attachCatalogID) + "/";

			RegexParser rp = config.getTemplate("Ref1");// 第一个,文档采集时只有第一个
			RegexParser[] filters = config.getFilterBlocks();
			list.moveFirst();
			WebDocument doc = null;
			int cSuccess = 0;// 成功数
			int cFailure = 0;// 失败数
			int cLost = 0;// 未匹配数

			boolean publishDateFlag = false;// 是否解析出了publishDate
			ZCArticleSet set = new ZCArticleSet();
			while ((doc = list.next()) != null) {
				if (task.checkStop()) {
					return;
				}
				if (doc.getLevel() != config.getUrlLevels().length - 2) {// 只有最后一级是详细页
					continue;
				}
				int percent = (100 - task.getPercent()) * (cSuccess + cFailure + cLost) / list.size();
				task.setPercent(task.getPercent() + percent);
				if (doc.isTextContent() && doc.getContent() != null) {
					String text = doc.getContentText();
					rp.setText(text);
					if (rp.match()) {
						Mapx map = rp.getMapx();
						Object[] ks = map.keyArray();
						Object[] vs = map.valueArray();
						for (int i = 0; i < map.size(); i++) {// 处理提取回来的数据，除内容外都要去掉标签并进行HTML解码
							String key = ks[i].toString();
							String value = vs[i].toString();
							if (!key.equalsIgnoreCase("Content")) {
								value = tagPattern.matcher(value).replaceAll("");
							}
							value = StringUtil.htmlDecode(value);
							value = value.trim();
							map.put(key, value);
						}
						String title = map.getString("Title");
						String content = map.getString("Content");
						String author = map.getString("Author");
						String source = map.getString("Source");
						String strDate = map.getString("PublishDate");
						Date publishDate = doc.getLastmodifiedDate();
						if (StringUtil.isNotEmpty(strDate) && StringUtil.isNotEmpty(config.getPublishDateFormat())) {
							try {
								// 先替换中文日期，如：二零零九年十一月一十一日，二○○九年
								strDate = DateUtil.convertChineseNumber(strDate);
								publishDate = DateUtil.parse(strDate, config.getPublishDateFormat());
							} catch (Exception e) {
								task.addError("日期" + strDate + "不符合指定格式" + doc.getUrl());
							}
							publishDateFlag = true;
						}
						if (publishDate.getTime() > System.currentTimeMillis()) {
							publishDate = new Date();// 不能晚于当前日期
						}
						ArticleAPI api = new ArticleAPI();
						try {
							ZCArticleSchema article = new ZCArticleSchema();
							if (StringUtil.isNotEmpty(title)) {
								article.setTitle(title);
							} else {
								cLost++;
								continue;
							}
							if (StringUtil.isNotEmpty(content)) {
								content = content.trim();
								while (rp.match()) {// 如果还有能匹配的，说明可能是文章内分页
									String html = rp.getMapx().getString("Content");
									content += html;
								}
								if (config.isCleanLinkFlag()) {
									content = framePattern.matcher(content).replaceAll("");
									content = stylePattern.matcher(content).replaceAll("");
									content = scriptPattern.matcher(content).replaceAll("");
									content = linkPattern.matcher(content).replaceAll("$1");
								}
								if (filters != null) {
									for (int k = 0; k < filters.length; k++) {
										content = filters[k].replace(content, "");
									}
								}
								// 需要处理图片
								String str = dealImage(content, doc.getUrl(), sitePath, imagePath, imageCatalogID);
								str = dealAttach(str, doc.getUrl(), sitePath, attachPath, attachCatalogID);
								article.setContent(str);
							} else {
								cLost++;
								continue;
							}
							if (StringUtil.isNotEmpty(author)) {
								article.setAuthor(author);
							}
							if (StringUtil.isNotEmpty(source)) {
								article.setReferName(source);
							}
							article.setReferURL(doc.getUrl());
							article.setPublishDate(publishDate);
							article.setCatalogID(config.getCatalogID());
							article.setBranchInnerCode("0001");
							article.setProp2("FromWeb");// 标明是Web采集而来

							if (ExtendManager.hasAction("FromWeb.BeforeSave")) {
								ExtendManager.executeAll("FromWeb.BeforeSave", new Object[] { article });
							}

							Date date = (Date) new QueryBuilder(
									"select PublishDate from ZCArticle where ReferURL=? and CatalogID=?", doc.getUrl(),
									config.getCatalogID()).executeOneValue();
							if (date != null) {
								if (date.getTime() < doc.getLastDownloadTime()) {
									QueryBuilder qb = new QueryBuilder(
											"update ZCArticle set Title=?,Content=? where CatalogID=? and ReferURL=?");
									qb.add(article.getTitle());
									qb.add(article.getContent());
									qb.add(config.getCatalogID());
									qb.add(doc.getUrl());
									qb.executeNoQuery();
								}
								cSuccess++;
							} else {
								api.setSchema(article);
								set.add(article);
								if (api.insert() > 0) {
									cSuccess++;
								} else {
									cFailure++;
								}
							}
						} catch (Exception e) {
							cFailure++;
							e.printStackTrace();
						}
					} else {
						LogUtil.getLogger().info("未能匹配" + doc.getUrl());
						task.addError("未能匹配" + doc.getUrl());
						cLost++;
					}
					task.setCurrentInfo("正在转换文档, <font class='green'>" + cSuccess + "</font> 个成功, <font class='red'>"
							+ cFailure + "</font> 个失败, <font class='green'>" + cLost + "</font> 个未匹配");
				}
			}

			if (!publishDateFlag) {
				String[] lastURLs = config.getUrlLevels()[config.getUrlLevels().length - 1].split("\\\n", -1);
				if (lastURLs.length != 1) {// 只有一个才按URL排序
					return;
				}
				RegexParser rpUrl = new RegexParser(lastURLs[0]);
				boolean numberFlag = true;
				for (int i = 0; i < set.size(); i++) {
					String url = set.get(i).getReferURL();
					rpUrl.setText(url);
					if (rpUrl.match()) {
						String v = rpUrl.getMapx().getString("SortID");
						set.get(i).setProp2(v);
						if (!NumberUtil.isLong(v)) {
							numberFlag = false;
						}
					}
				}
				set.sort("Prop2", "asc", numberFlag);
				for (int i = set.size() - 1; i >= 0; i--) {
					set.get(i).setOrderFlag(OrderUtil.getDefaultOrder());
					set.get(i).setProp2(null);
				}
				set.update();
			}
		}
	}

	public String dealAttach(String content, String baseUrl, String sitePath, String attachPath, String attachCatalogID) {
		Matcher m = SearchUtil.resourcePattern1.matcher(content);
		int lastEndIndex = 0;
		StringBuffer sb = new StringBuffer();
		while (m.find(lastEndIndex)) {
			String url = m.group(2);
			String ext = ServletUtil.getUrlExtension(url);
			if (SearchUtil.isRightUrl(url) && StringUtil.isNotEmpty(ext)
					&& ".doc.docx.xls.xlsx.ppt.pptx.rar.zip.exe.dat.gz.msi".indexOf(ext) >= 0) {// 有引号，不需要判断是否是处于htmlEncode段中
				String fullUrl = SearchUtil.normalizeUrl(url, baseUrl);
				WebDocument tdoc = list.get(fullUrl);
				if (tdoc != null && !tdoc.isTextContent()) {
					byte[] data = tdoc.getContent();// 文件内容
					sb.append(content.substring(lastEndIndex, m.start()));
					ZCAttachmentSchema attach = saveAttach(data, sitePath, attachPath, attachCatalogID, ext, fullUrl);
					String attachFilePath = (Config.getValue("ServicesContext") + "/AttachDownLoad.jsp?id=" + attach
							.getID()).replaceAll("//", "/");
					sb.append(StringUtil.replaceEx(m.group(0), url, attachFilePath));
					sb.append(" nswtpattachrela=\"" + attach.getID() + "\"");
				} else {
					sb.append(content.substring(lastEndIndex, m.end()));
				}
			} else {
				sb.append(content.substring(lastEndIndex, m.end()));
			}
			lastEndIndex = m.end();
		}
		sb.append(content.substring(lastEndIndex));
		content = sb.toString();

		sb = new StringBuffer();
		m = SearchUtil.resourcePattern2.matcher(content);
		lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			String url = m.group(3);
			String ext = ServletUtil.getUrlExtension(url);
			if (SearchUtil.isRightUrl2(url) && StringUtil.isNotEmpty(ext)
					&& ".doc.docx.xls.xlsx.ppt.pptx.rar.zip.exe.dat.gz.msi".indexOf(ext) >= 0) {// 没引号，需要判断是否是处于htmlEncode段中
				String fullUrl = SearchUtil.normalizeUrl(url, baseUrl);
				WebDocument tdoc = list.get(fullUrl);
				if (tdoc != null && !tdoc.isTextContent()) {
					byte[] data = tdoc.getContent();// 文件内容
					sb.append(content.substring(lastEndIndex, m.start()));
					ZCAttachmentSchema attach = saveAttach(data, sitePath, attachPath, attachCatalogID, ext, fullUrl);
					String attachFilePath = (Config.getContextPath() + "Services/AttachDownLoad.jsp?id=" + attach
							.getID()).replaceAll("//", "/");
					sb.append(StringUtil.replaceEx(m.group(0), url, attachFilePath));
					sb.append(" nswtpattachrela=\"" + attach.getID() + "\"");
				} else {
					sb.append(content.substring(lastEndIndex, m.end()));
				}
			} else {
				sb.append(content.substring(lastEndIndex, m.end()));
			}
			lastEndIndex = m.end();
		}
		sb.append(content.substring(lastEndIndex));
		return sb.toString();
	}

	public String dealImage(String content, String baseUrl, String sitePath, String imagePath, String imageCatalogID) {
		Matcher m = SearchUtil.resourcePattern1.matcher(content);
		int lastEndIndex = 0;
		StringBuffer sb = new StringBuffer();
		while (m.find(lastEndIndex)) {
			String url = m.group(2);
			String ext = ServletUtil.getUrlExtension(url);
			if (SearchUtil.isRightUrl(url) && StringUtil.isNotEmpty(ext) && ".gif.jpg.jpeg.bmp.png".indexOf(ext) >= 0) {// 有引号，不需要判断是否是处于htmlEncode段中
				String fullUrl = SearchUtil.normalizeUrl(url, baseUrl);
				WebDocument tdoc = list.get(fullUrl);
				if (tdoc != null && !tdoc.isTextContent()) {
					byte[] data = tdoc.getContent();// 文件内容
					sb.append(content.substring(lastEndIndex, m.start()));
					ZCImageSchema image = saveImage(data, sitePath, imagePath, imageCatalogID, ext, fullUrl);
					String imageFilePath = (Config.getContextPath() + Config.getValue("UploadDir") + "/"
							+ SiteUtil.getAlias(image.getSiteID()) + "/" + image.getPath() + "1_" + image.getFileName())
							.replaceAll("//", "/");
					sb.append(StringUtil.replaceEx(m.group(0), url, imageFilePath));
					sb.append(" nswtpimagerela=\"" + image.getID() + "\"");
				} else {
					sb.append(content.substring(lastEndIndex, m.end()));
				}
			} else {
				sb.append(content.substring(lastEndIndex, m.end()));
			}
			lastEndIndex = m.end();
		}
		sb.append(content.substring(lastEndIndex));
		content = sb.toString();

		sb = new StringBuffer();
		m = SearchUtil.resourcePattern2.matcher(content);
		lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			String url = m.group(3);
			String ext = ServletUtil.getUrlExtension(url);
			if (SearchUtil.isRightUrl2(url) && StringUtil.isNotEmpty(ext) && ".gif.jpg.jpeg.bmp.png".indexOf(ext) >= 0) {// 没引号，需要判断是否是处于htmlEncode段中
				String fullUrl = SearchUtil.normalizeUrl(url, baseUrl);
				WebDocument tdoc = list.get(fullUrl);
				if (tdoc != null && !tdoc.isTextContent()) {
					byte[] data = tdoc.getContent();// 文件内容
					sb.append(content.substring(lastEndIndex, m.start()));
					ZCImageSchema image = saveImage(data, sitePath, imagePath, imageCatalogID, ext, fullUrl);
					String imageFilePath = (Config.getContextPath() + Config.getValue("UploadDir") + "/"
							+ SiteUtil.getAlias(image.getSiteID()) + "/" + image.getPath() + "1_" + image.getFileName())
							.replaceAll("//", "/");
					sb.append(StringUtil.replaceEx(m.group(0), url, imageFilePath));
					sb.append(" nswtpimagerela=\"" + image.getID() + "\"");
				} else {
					sb.append(content.substring(lastEndIndex, m.end()));
				}
			} else {
				sb.append(content.substring(lastEndIndex, m.end()));
			}
			lastEndIndex = m.end();
		}
		sb.append(content.substring(lastEndIndex));
		return sb.toString();
	}

	public static ZCImageSchema saveImage(byte[] data, String path1, String path2, String catalogID, String ext,
			String imageURL) {
		ZCImageSchema image = new ZCImageSchema();
		image.setSourceURL(imageURL);
		boolean flag = true;
		ZCImageSet set = image.query();
		if (set.size() > 0) {
			image = set.get(0);
			File f = new File(path1 + path2 + image.getSrcFileName());
			if (f.exists()) {
				if (f.length() == data.length) {// 字节数相同而且文件相同的机率忽略不计
					flag = false;
				}
				FileUtil.writeByte(f, data);
			} else {
				// bug#558 新建web采集任务时,勾选下载远程图片,含图片的文章采集过来后,图片显示不出来
				// 如果本地不存在该文件，写入到本地文件夹下
				File imageDir = new File(path1 + path2);
				if (!imageDir.exists()) {
					imageDir.mkdirs();
				}
				FileUtil.writeByte(f, data);
			}
		} else {
			long imageID = NoUtil.getMaxID("DocID");
			int random = NumberUtil.getRandomInt(10000);
			String fileName = imageID + "" + random + ext;
			FileUtil.writeByte(path1 + path2 + fileName, data);
			image.setID(imageID);
			image.setCatalogID(catalogID);
			image.setCatalogInnerCode(CatalogUtil.getInnerCode(catalogID));
			String srcFileName = imageURL.substring(imageURL.lastIndexOf("/") + 1);
			image.setName(srcFileName.substring(0, srcFileName.lastIndexOf(".")));
			image.setOldName(image.getName());
			image.setSiteID(CatalogUtil.getSiteID(catalogID));
			image.setPath(path2);
			image.setFileName(imageID + "" + NumberUtil.getRandomInt(10000) + ".jpg");
			image.setSrcFileName(fileName);
			image.setSuffix(ext);
			image.setCount(1);
			image.setWidth(0);
			image.setHeight(0);
			try {
				BufferedImage img = ImageIO.read(new File(path1 + path2 + fileName));
				image.setWidth(img.getWidth());
				image.setHeight(img.getHeight());
			} catch (IOException e) {
				e.printStackTrace();
			}
			image.setHitCount(0);
			image.setStickTime(0);
			image.setAuthor("Crawler");
			image.setSystem("CMS");
			image.setOrderFlag(OrderUtil.getDefaultOrder());
			image.setAddTime(new Date());
			image.setAddUser("SYS");
			image.setModifyTime(image.getAddTime());
			image.setModifyUser("SYS");
			image.setSiteID(CatalogUtil.getSiteID(image.getCatalogID()));
			image.insert();
		}
		if (flag) {
			ArrayList imageList = null;
			try {
				imageList = ImageUtilEx.afterUploadImage(image, path1 + path2);
			} catch (Throwable e) {
				e.printStackTrace();
				return null;
			}
			Deploy d = new Deploy();
			d.addJobs(image.getSiteID(), imageList);
		}
		return image;
	}

	public static ZCAttachmentSchema saveAttach(byte[] data, String path1, String path2, String catalogID, String ext,
			String attachURL) {
		ZCAttachmentSchema attach = new ZCAttachmentSchema();
		attach.setSourceURL(attachURL);
		boolean flag = true;
		ZCAttachmentSet set = attach.query();
		if (set.size() > 0) {
			attach = set.get(0);
			File f = new File(path1 + path2 + attach.getSrcFileName());
			if (f.exists()) {
				if (f.length() == data.length) {// 字节数相同而且文件相同的机率忽略不计
					flag = false;
				}
				FileUtil.writeByte(f, data);
			} else {
				// bug#558 新建web采集任务时,勾选下载远程图片,含图片的文章采集过来后,图片显示不出来
				// 如果本地不存在该文件，写入到本地文件夹下
				File attachDir = new File(path1 + path2);
				if (!attachDir.exists()) {
					attachDir.mkdirs();
				}
				FileUtil.writeByte(f, data);
			}
		} else {
			long attachID = NoUtil.getMaxID("DocID");
			int random = NumberUtil.getRandomInt(10000);
			String fileName = attachID + "" + random + ext;
			FileUtil.writeByte(path1 + path2 + fileName, data);

			attach.setID(attachID);
			attach.setSiteID(CatalogUtil.getSiteID(catalogID));
			attach.setInfo("");
			String srcFileName = attachURL.substring(attachURL.lastIndexOf("/") + 1);
			attach.setName(srcFileName.substring(0, srcFileName.lastIndexOf(".")));
			attach.setOldName(attach.getName());
			attach.setCatalogID(catalogID);
			attach.setCatalogInnerCode(CatalogUtil.getInnerCode(catalogID));
			attach.setBranchInnerCode(User.getBranchInnerCode());
			attach.setPath(path2);
			attach.setImagePath("");
			attach.setFileName(fileName);
			attach.setSrcFileName(fileName);
			attach.setSuffix(ext);
			attach.setIsLocked("N");
			attach.setSystem("CMS");
			attach.setFileSize(FileUtils.byteCountToDisplaySize(data.length));
			attach.setAddTime(new Date());
			attach.setAddUser("admin");
			attach.setOrderFlag(OrderUtil.getDefaultOrder());
			attach.setModifyTime(new Date());
			attach.setModifyUser(User.getUserName());
			attach.setProp1("0");
			attach.setStatus(Article.STATUS_TOPUBLISH);
			attach.insert();
		}
		if (flag) {
			ArrayList fileList = new ArrayList();
			fileList.add(path1 + path2 + attach.getFileName());
			Deploy d = new Deploy();
			d.addJobs(attach.getSiteID(), fileList);
		}
		return attach;
	}
}
