package com.nswt.cms.tag;

import com.nswt.framework.Config;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.statical.exception.TemplateRuntimeException;
import com.nswt.statical.tag.AbstractListTag;
import com.nswt.statical.tag.TagAttributeDesc;

/*
 * @Author NSWT
 * @Date 2016-8-17
 * @Mail nswt@nswt.com.cn
 */
public class CmsSqlTag extends AbstractListTag {

	public void prepareData() throws TemplateRuntimeException {
		String sql = attributes.getString("sql");
		item = attributes.getString("item");
		if (!"Y".equalsIgnoreCase(Config.getValue("SQLTagEnable"))) {
			throw new TemplateRuntimeException("ϵͳ����Ϊ������ʹ��<cms:sql>��ǩ!");
		}
		sql = context.replaceHolder(sql);
		if (page) {
			data = new QueryBuilder(sql).executePagedDataTable(pageSize, template.getPageIndex());
		} else {
			data = new QueryBuilder(sql).executeDataTable();
		}
	}

	public TagAttributeDesc[] getChildAttributeDescs() {
		return new TagAttributeDesc[] { new TagAttributeDesc("item", true), new TagAttributeDesc("sql", true) };
	}

	public String getPrefix() {
		return "cms";
	}

	public String getTagName() {
		return "sql";
	}

}
