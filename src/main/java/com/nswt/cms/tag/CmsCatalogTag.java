package com.nswt.cms.tag;

import com.nswt.cms.dataservice.ColumnUtil;
import com.nswt.cms.pub.CatalogUtil;
import com.nswt.framework.data.DataColumn;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.utility.StringUtil;
import com.nswt.statical.exception.TemplateRuntimeException;
import com.nswt.statical.tag.AbstractListTag;
import com.nswt.statical.tag.TagAttributeDesc;

/*
 * @Author NSWT
 * @Date 2016-8-17
 * @Mail nswt@nswt.com.cn
 */
public class CmsCatalogTag extends AbstractListTag {

	public void prepareData() throws TemplateRuntimeException {
		item = "catalog";
		String name = attributes.getString("name");
		String id = attributes.getString("id");
		String level = attributes.getString("level");
		if (StringUtil.isEmpty(level)) {
			level = "Child";// Current,Child,Root
		}
		QueryBuilder qb = new QueryBuilder("select * from ZCCatalog where 1=1");
		if ("Root".equalsIgnoreCase(level)) {
			qb.append(" and ParentID=0");
		} else {
			if (StringUtil.isNotEmpty(id)) {
				id = context.replaceHolder(id);
			} else {
				if (StringUtil.isNotEmpty(name)) {
					name = context.replaceHolder(name);
					id = CatalogUtil.getIDByName(template.evalLong("Site.ID"), name);
				} else {
					id = template.eval("Catalog.ID");
					if (StringUtil.isEmpty(id)) {
						throw new TemplateRuntimeException("<cms:catalog>标签不能确定数据源!");
					}
				}
			}
			context.addDataVariable("Catalog", CatalogUtil.getSchema(id).toDataRow());
			if (level.equals("Current")) {
				qb.append(" and ParentID=?", Long.parseLong(CatalogUtil.getParentID(id)));
			} else {
				qb.append(" and ParentID=?", Long.parseLong(id));
			}
		}
		if (page) {
			data = qb.executePagedDataTable(pageSize, template.getPageIndex());
		} else {
			data = qb.executeDataTable();
		}
		data.insertColumn("Link");
		data.insertColumn("_RowNo");
		for (int i = 0; i < data.getRowCount(); i++) {
			data.set(i, "_RowNo", i + 1);
			data.set(i, "ImagePath", context.eval("Level") + data.getString(i, "ImagePath"));
			String url = CatalogUtil.getLink(data.getString(i, "ID"), context.eval("Level"));
			data.set(i, "Link", url);
		}
		ColumnUtil.extendCatalogColumnData(data, context.evalLong("Site.ID"), context.eval("Level"));

	}

	public String getPrefix() {
		return "cms";
	}

	public String getTagName() {
		return "catalog";
	}

	public TagAttributeDesc[] getChildAttributeDescs() {
		return new TagAttributeDesc[] { new TagAttributeDesc("name"), new TagAttributeDesc("id", DataColumn.INTEGER),
				new TagAttributeDesc("level") };
	}

}
