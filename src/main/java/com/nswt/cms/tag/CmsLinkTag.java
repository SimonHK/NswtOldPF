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
public class CmsLinkTag extends SimpleTag {
	public TagAttributeDesc[] getAllAttributeDescs() {
		return new TagAttributeDesc[] { new TagAttributeDesc("type", true), new TagAttributeDesc("name", true),
				new TagAttributeDesc("spliter"), new TagAttributeDesc("code"), new TagAttributeDesc("homePageName") };
	}

	public String getPrefix() {
		return "cms";
	}

	public String getTagName() {
		return "link";
	}

	public int onTagStart() {
		String type = attributes.getString("type");
		String code = attributes.getString("code");
		String name = attributes.getString("name");
		String homePageName = attributes.getString("homePageName");
		if (StringUtil.isNotEmpty(homePageName) && StringUtil.isEmpty(name)) {
			name = homePageName;
		}
		String spliter = attributes.getString("spliter");
		CmsTemplateData templateData = new CmsTemplateData();
		templateData.setSite((DataRow) context.getHolderValue("${Site}"));
		templateData.setCatalog((DataRow) context.getHolderValue("${Catalog}"));
		templateData.setLevelStr(context.eval("Level"));
		template.getWriter().print(templateData.getLinkURL(type, code, name, spliter));
		return TagBase.SKIP;
	}

}
