package com.nswt.cms.tag;

import com.nswt.cms.template.CmsTemplateData;
import com.nswt.framework.data.DataRow;
import com.nswt.framework.utility.StringUtil;
import com.nswt.statical.tag.SimpleTag;
import com.nswt.statical.tag.TagAttributeDesc;
import com.nswt.statical.tag.TagBase;

/*
 * @Author NSWT
 * @Date 2016-7-21
 * @Mail nswt@nswt.com.cn
 */
public class CmsImagePlayerTag extends SimpleTag {

	public int onTagStart() {
		String name = attributes.getString("name");
		if (StringUtil.isEmpty(name)) {
			name = attributes.getString("code");
		}
		String width = attributes.getString("width");
		String height = attributes.getString("height");
		String count = attributes.getString("count");
		String cw = attributes.getString("charwidth");

		CmsTemplateData templateData = new CmsTemplateData();
		templateData.setSite((DataRow) context.getHolderValue("${Site}"));
		templateData.setCatalog((DataRow) context.getHolderValue("${Catalog}"));
		templateData.setLevelStr(context.eval("Level"));
		String html = templateData.getImagePlayer(name, width, height, count, cw);
		template.getWriter().print(html);
		return TagBase.SKIP;
	}

	public TagAttributeDesc[] getAllAttributeDescs() {
		return new TagAttributeDesc[] { new TagAttributeDesc("code"), new TagAttributeDesc("name"),
				new TagAttributeDesc("width", true), new TagAttributeDesc("height", true),
				new TagAttributeDesc("count"), new TagAttributeDesc("charwidth") };
	}

	public String getPrefix() {
		return "cms";
	}

	public String getTagName() {
		return "imageplayer";
	}

}
