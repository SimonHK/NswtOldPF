package com.nswt.cms.tag;

import com.nswt.cms.template.CmsTemplateData;
import com.nswt.framework.data.DataRow;
import com.nswt.statical.tag.SimpleTag;
import com.nswt.statical.tag.TagAttributeDesc;
import com.nswt.statical.tag.TagBase;

/**
 * 日期 : 2010-7-8 <br>
 * 作者: NSWT <br>
 * 邮箱：nswt@nswt.com.cn <br>
 */
public class CmsAdTag extends SimpleTag {

	public TagAttributeDesc[] getAllAttributeDescs() {
		return new TagAttributeDesc[] { new TagAttributeDesc("name"), new TagAttributeDesc("size"),
				new TagAttributeDesc("type"), };
	}

	public String getPrefix() {
		return "cms";
	}

	public String getTagName() {
		return "ad";
	}

	public int onTagStart() {
		String name = attributes.getString("name");
		String type = attributes.getString("type");
		CmsTemplateData templateData = new CmsTemplateData();
		templateData.setSite((DataRow) context.getHolderValue("${Site}"));
		templateData.setCatalog((DataRow) context.getHolderValue("${Catalog}"));
		templateData.setLevelStr(context.eval("Level"));
		template.getWriter().print(templateData.getAD(name, type));
		return TagBase.SKIP;
	}

}
