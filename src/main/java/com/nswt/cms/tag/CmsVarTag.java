package com.nswt.cms.tag;

import com.nswt.cms.template.CmsTemplateData;
import com.nswt.framework.data.DataRow;
import com.nswt.framework.utility.StringUtil;
import com.nswt.statical.tag.SimpleTag;
import com.nswt.statical.tag.TagAttributeDesc;
import com.nswt.statical.tag.TagBase;

/**
 * 日期 : 2010-7-8 <br>
 * 作者: NSWT <br>
 * 邮箱：nswt@nswt.com.cn <br>
 */
public class CmsVarTag extends SimpleTag {

	public TagAttributeDesc[] getAllAttributeDescs() {
		return new TagAttributeDesc[] { new TagAttributeDesc("type"), new TagAttributeDesc("name", true),
				new TagAttributeDesc("title") };
	}

	public String getPrefix() {
		return "cms";
	}

	public String getTagName() {
		return "var";
	}

	public int onTagStart() {
		String type = attributes.getString("type");
		if (StringUtil.isNotEmpty(type)) {
			type = type.toLowerCase();
		}
		String name = attributes.getString("name");
		String title = attributes.getString("title");
		if (StringUtil.isEmpty(name)) {
			name = title;
		}
		CmsTemplateData templateData = new CmsTemplateData();
		templateData.setSite((DataRow) context.getHolderValue("${Site}"));
		templateData.setCatalog((DataRow)context.getHolderValue("${Catalog}"));
		templateData.setLevelStr(context.eval("Level"));
		if ("article".equalsIgnoreCase(type) || "image".equalsIgnoreCase(type) || "video".equalsIgnoreCase(type)
				|| "audio".equalsIgnoreCase(type) || "attachment".equalsIgnoreCase(type)) {
			DataRow doc = templateData.getDocument(type, name);
			context.addDataVariable(type, doc);
		} else if ("catalog".equalsIgnoreCase(type)) {
			DataRow doc = templateData.getCatalog(type, name);
			context.addDataVariable(type, doc);
		}
		return TagBase.CONTINUE;
	}

}
