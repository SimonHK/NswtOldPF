package com.nswt.cms.tag;

import com.nswt.cms.template.CmsTemplateData;
import com.nswt.framework.data.DataColumn;
import com.nswt.framework.data.DataRow;
import com.nswt.framework.data.DataTable;
import com.nswt.statical.exception.TemplateRuntimeException;
import com.nswt.statical.tag.IterativeTag;
import com.nswt.statical.tag.TagAttributeDesc;
import com.nswt.statical.tag.TagBase;

/*
 * @Author NSWT
 * @Date 2016-8-9
 * @Mail nswt@nswt.com.cn
 */
public class CmsFriendLinkTag extends IterativeTag {
	private DataTable data;
	private int currentPos = 0;
	private String item = "friendlink";
	private String name;
	private int count;
	private int begin;
	private String condition;

	public int onTagStart() throws TemplateRuntimeException {
		name = attributes.getString("name");
		int count = attributes.getInt("count");
		begin = attributes.getInt("begin");
		condition = attributes.getString("condition");
		if (count == 0) {
			count = 20;
		}
		CmsTemplateData templateData = new CmsTemplateData();
		templateData.setSite((DataRow) context.getHolderValue("${Site}"));
		templateData.setCatalog((DataRow) context.getHolderValue("${Catalog}"));
		templateData.setLevelStr(context.eval("Level"));
		currentPos = begin;
		data = templateData.getFriendLinkList(item, name, "" + count, condition);
		return TagBase.CONTINUE;
	}

	public TagAttributeDesc[] getAllAttributeDescs() {
		return new TagAttributeDesc[] { new TagAttributeDesc("name", true),
				new TagAttributeDesc("count", DataColumn.INTEGER), new TagAttributeDesc("begin", DataColumn.INTEGER),
				new TagAttributeDesc("condition") };
	}

	public int onTagEnd() {
		return 0;
	}

	public boolean prepareNext() throws TemplateRuntimeException {
		if (data != null && data.getRowCount() > currentPos) {
			if (count == 0 || currentPos < count + begin) {
				context.addDataVariable(item, data.getDataRow(currentPos++));
				return true;
			}
		}
		return false;
	}

	public String getPrefix() {
		return "cms";
	}

	public String getTagName() {
		return "friendlink";
	}

}
