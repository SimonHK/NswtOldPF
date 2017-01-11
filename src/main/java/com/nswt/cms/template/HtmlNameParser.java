package com.nswt.cms.template;

import java.util.Date;
import java.util.regex.Matcher;

import com.nswt.cms.pub.CatalogUtil;
import com.nswt.framework.Constant;
import com.nswt.framework.data.DataColumn;
import com.nswt.framework.data.DataRow;
import com.nswt.framework.utility.DateUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;

/**
 * 文件命名规则
 * 在生成详细页面时，可以自定义路径命名规则
 * 主要的参数有：${Year}-年  ${Month}-月 ${Date}-日 ${Timestamp}-时间戳 ${Document.ID}-文章ID ${Catalog.ID}栏目ID
 * @author 兰军
 *
 */
public class HtmlNameParser {
	private String nameRule;

	private DataRow site;

	private DataRow catalog;

	private DataRow document;

	private static Mapx cacheMap = new Mapx(1000);

	public HtmlNameParser(DataRow site, DataRow catalog, DataRow document, String nameRule) {
		this.site = site;
		if (catalog == null && document != null) {
			long catalogID = document.getLong("CatalogID");
			DataColumn[] types = new DataColumn[2];
			types[0] = new DataColumn("SingleFlag", DataColumn.STRING);
			types[1] = new DataColumn("ID", DataColumn.LONG);

			Object[] values = { CatalogUtil.getSingleFlag(catalogID), catalogID + "" };
			this.catalog = new DataRow(types, values);
		} else {
			this.catalog = catalog;
		}

		this.document = document;
		this.nameRule = nameRule;
	}

	public HtmlNameRule getNameRule() {
		HtmlNameRule rule = (HtmlNameRule) cacheMap.get(catalog.getString("id") + nameRule);
		if (rule == null) {
			rule = parse();
			if (!StringUtil.isEmpty(nameRule)) {
				cacheMap.put(catalog.getString("id") + nameRule, rule);
			}
		}
		return rule;
	}

	private HtmlNameRule parse() {
		StringBuffer sb = new StringBuffer();
		String fullPath = null, dirPath = null, fileName = null;
	
		if (StringUtil.isNotEmpty(nameRule)) {
			Date date = null;
			long timestamp;

			if (document != null && document.get("PublishDate") != null) {
				date = document.getDate("PublishDate");
				timestamp = date.getTime();
			} else {
				date = new Date();
				timestamp = System.currentTimeMillis();
			}

			Matcher m = Constant.PatternField.matcher(nameRule);
			int lastEndIndex = 0;
			while (m.find(lastEndIndex)) {
				sb.append(nameRule.substring(lastEndIndex, m.start()));
				String field = m.group(1);

				if (field != null) {
					field = field.toLowerCase();
				}

				if ("year".equals(field)) {
					sb.append(DateUtil.toString(date, "yyyy"));
				} else if ("month".equals(field)) {
					sb.append(DateUtil.toString(date, "MM"));
				} else if ("day".equals(field)) {
					sb.append(DateUtil.toString(date, "dd"));
				} else if ("timestamp".equals(field)) {
					sb.append(timestamp);
				} else if ("timestamp".equals(field)) {
					sb.append(timestamp);
				} else if ("catalogpath".equals(field)) {
					if(document != null){
						sb.append(CatalogUtil.getPath(catalog.getString("id")));
					}else{
						sb.append(CatalogUtil.getFullPath(catalog.getString("id")));
					}
					
				}
				lastEndIndex = m.end();
			}
			sb.append(nameRule.substring(lastEndIndex));

			nameRule = sb.toString();
			sb = new StringBuffer();
			m = Constant.PatternPropField.matcher(nameRule);
			lastEndIndex = 0;
			while (m.find(lastEndIndex)) {
				sb.append(nameRule.substring(lastEndIndex, m.start()));
				String table = m.group(1);
				String field = m.group(2);

				if (table != null) {
					table = table.toLowerCase();
				}
				if (field != null) {
					field = field.toLowerCase();
				}

				DataRow row = null;
				if ("site".equals(table)) {
					row = site;
				} else if ("catalog".equals(table)) {
					row = catalog;
				} else if ("document".equals(table)) {
					row = document;
				}

				if (row != null) {
					sb.append(row.getString(field));
				}

				lastEndIndex = m.end();
			}
			sb.append(nameRule.substring(lastEndIndex));

			fullPath = sb.toString();
		} else {
			if(document != null){
				fullPath = CatalogUtil.getPath(catalog.getString("ID"))+document.getString("id")+".shtml";
			}else{
				fullPath = CatalogUtil.getFullPath(catalog.getString("ID"));
			}
		}
		
		//单页栏目中的文章路径
		if("Y".equals(catalog.getString("SingleFlag")) && document != null){
			fullPath = fullPath.substring(0,fullPath.lastIndexOf("/")+1)+"index.shtml";
		}

		fullPath = fullPath.replaceAll("/+", "/");
		if (fullPath.startsWith("/")) {
			fullPath = fullPath.substring(1);
		}

		int level = StringUtil.count(fullPath, "/");
		int index = fullPath.lastIndexOf("/");

		if (index != -1) {
			dirPath = fullPath.substring(0, index);
			fileName = fullPath.substring(index + 1);
		} else {
			fileName = fullPath;
		}

		HtmlNameRule h = new HtmlNameRule();
		h.setFullPath(fullPath);
		h.setLevel(level);
		h.setDirPath(dirPath);
		h.setFileName(fileName);

		return h;
	}
}
