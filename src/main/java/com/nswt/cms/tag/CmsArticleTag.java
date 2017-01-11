package com.nswt.cms.tag;

import com.nswt.cms.pub.CatalogUtil;
import com.nswt.cms.template.CmsTemplateData;
import com.nswt.framework.data.DataColumn;
import com.nswt.framework.data.DataRow;
import com.nswt.framework.utility.StringUtil;
import com.nswt.statical.exception.TemplateRuntimeException;
import com.nswt.statical.tag.AbstractListTag;
import com.nswt.statical.tag.TagAttributeDesc;

/**
 * 日期 : 2010-7-8 <br>
 * 作者: NSWT <br>
 * 邮箱：nswt@nswt.com.cn <br>
 */
public class CmsArticleTag extends AbstractListTag {
	private String type;
	private String id;
	private String name;
	private String displayLevel;
	private String attribute;

	public TagAttributeDesc[] getChildAttributeDescs() {
		return new TagAttributeDesc[] { new TagAttributeDesc("type"), new TagAttributeDesc("catalog"),
				new TagAttributeDesc("catalogid", DataColumn.INTEGER), new TagAttributeDesc("hasattribute") };
	}

	public String getPrefix() {
		return "cms";
	}

	public String getTagName() {
		return "article";
	}

	public void editWrapStart() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < tagTreeIndex.length; i++) {
			if (i != 0) {
				sb.append("_");
			}
			sb.append(tagTreeIndex[i]);
		}
		String id = sb.toString();
		sb = new StringBuffer();
		Object[] ks = attributes.keyArray();
		for (int i = 0; i < attributes.size(); i++) {
			String v = attributes.getString(ks[i]);
			if (StringUtil.isEmpty(v)) {
				continue;
			}
			v = StringUtil.replaceEx(v, ";", "\\;");
			sb.append(ks[i] + ":" + v + ";");
		}
		String attr = sb.toString();
		template.getWriter().print("<!--_attribute_" + id + ":" + attr + "-->");
	}

	public void prepareData() throws TemplateRuntimeException {
		item = "article";
		type = attributes.getString("type");
		id = attributes.getString("catalogid");
		name = attributes.getString("catalog");
		displayLevel = attributes.getString("level");
		attribute = attributes.getString("hasattribute");
		if (StringUtil.isNotEmpty(displayLevel)) {
			displayLevel = displayLevel.toLowerCase();
		} else {
			displayLevel = "current";
		}
		CmsTemplateData templateData = new CmsTemplateData();
		templateData.setSite((DataRow) context.getHolderValue("${Site}"));
		templateData.setCatalog((DataRow) context.getHolderValue("${Catalog}"));
		templateData.setLevelStr(context.eval("Level"));
		String catalog = name;
		if (StringUtil.isNotEmpty(id)) {
			catalog = id;
		}
		String parent = attributes.getString("parent");
		// 兼容老式写法
		if (StringUtil.isEmpty(catalog) && StringUtil.isNotEmpty(parent)) {
			catalog = parent;
		}
		// 解析Name中的变量表达式
		if (StringUtil.isNotEmpty(catalog) && catalog.indexOf("${") != -1) {
			catalog = context.eval(catalog);
		}
		String conditionStr = null;
		if (StringUtil.isNotEmpty(condition) && condition.indexOf("${") != -1) {
			conditionStr = context.replaceHolder(condition);
		}
		if (StringUtil.isEmpty(catalog)) {// 没有设置则默认为当前栏目
			catalog = context.eval("Catalog.ID");
		}
		if (page) {
			if (template.getPageTotal() == 0) {
				template.setPageTotal(templateData.getPagedDocListTotal(item, catalog, displayLevel, attribute, type,
						conditionStr));
			}
			if (template.getPageSize() == 0) {
				template.setPageSize((int) CatalogUtil.getSchema(catalog).getListPageSize());
				if (template.getPageSize() == 0) {
					template.setPageSize(20);
				}
			}
			data = templateData.getPagedDocList(item, catalog, displayLevel, attribute, type, condition,
					template.getPageSize(), template.getPageIndex());
		} else {
			data = templateData.getDocList(item, catalog, displayLevel, attribute, type, "" + count, conditionStr);
		}
	}
}
