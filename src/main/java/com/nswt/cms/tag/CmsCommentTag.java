package com.nswt.cms.tag;

import com.nswt.cms.template.CmsTemplateData;
import com.nswt.framework.data.DataColumn;
import com.nswt.framework.data.DataRow;
import com.nswt.statical.tag.SimpleTag;
import com.nswt.statical.tag.TagAttributeDesc;
import com.nswt.statical.tag.TagBase;

/**
 * 日期 : 2010-7-8 <br>
 * 作者: NSWT <br>
 * 邮箱：nswt@nswt.com.cn <br>
 */
public class CmsCommentTag extends SimpleTag {

	public TagAttributeDesc[] getAllAttributeDescs() {
		return new TagAttributeDesc[] { new TagAttributeDesc("count", DataColumn.INTEGER) };
	}

	public String getPrefix() {
		return "cms";
	}

	public String getTagName() {
		return "comment";
	}

	public int onTagStart() {
		String count = attributes.getString("count");
		CmsTemplateData templateData = new CmsTemplateData();
		templateData.setSite((DataRow) context.getHolderValue("${Site}"));
		templateData.setCatalog((DataRow) context.getHolderValue("${Catalog}"));
		templateData.setDoc((DataRow) context.getHolderValue("${Article}"));
		templateData.setLevelStr(context.eval("Level"));
		if ("1".equals(templateData.getDoc().getString("CommentFlag"))) {
			template.getWriter().print(templateData.getComment(count));
		}
		return TagBase.SKIP;
	}

}
