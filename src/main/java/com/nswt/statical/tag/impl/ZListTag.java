package com.nswt.statical.tag.impl;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.utility.StringUtil;
import com.nswt.statical.tag.IterativeTag;
import com.nswt.statical.tag.TagAttributeDesc;
import com.nswt.statical.tag.TagBase;
import com.nswt.statical.template.TemplateContext;

/*
 * @Author NSWT
 * @Date 2016-7-22
 * @Mail nswt@nswt.com.cn
 */
public class ZListTag extends IterativeTag {
	public static final String ZListDataNameKey = "_NSWT_ZList_Data";
	public static final String ZListItemNameKey = "_NSWT_ZList_Item";
	private DataTable data;
	private int currentPos = 0;
	private int begin = 0;
	private int count = 0;
	private String item;

	/**
	 * 准备数据，给z:list子标签使用，其中name为加入到context中的数据变量的名称
	 */
	public static void prepareData(TemplateContext context, String name) {
		context.addDataVariable(ZListDataNameKey, name);
	}

	public int onTagEnd() {
		return TagBase.CONTINUE;
	}

	public TagAttributeDesc[] getAllAttributeDescs() {
		return new TagAttributeDesc[] { new TagAttributeDesc("count", DataColumn.INTEGER),
				new TagAttributeDesc("begin", DataColumn.INTEGER), new TagAttributeDesc("data"),
				new TagAttributeDesc("item") };
	}

	public int onTagStart() {
		String dataVar = attributes.getString("data");
		count = attributes.getInt("count");
		begin = currentPos = attributes.getInt("begin");
		if (StringUtil.isNotEmpty(dataVar)) {
			item = attributes.getString("item");
			Object obj = context.getHolderValue(dataVar);
			if (obj == null) {
				return TagBase.SKIP;
			}
			if (obj instanceof DataTable) {
				data = (DataTable) obj;
			} else {
				throw new RuntimeException("<z:list>的data参数必须是DataTable");
			}
		} else {
			data = (DataTable) context.getHolderValue(ZListDataNameKey);
			item = context.eval(ZListItemNameKey);
		}
		return TagBase.CONTINUE;
	}

	public boolean prepareNext() {
		if (data != null && data.getRowCount() > currentPos) {
			if (count == 0 || currentPos < count + begin) {
				context.addDataVariable(item, data.getDataRow(currentPos++));
				return true;
			}
		}
		return false;
	}

	public String getPrefix() {
		return "z";
	}

	public String getTagName() {
		return "list";
	}

}
