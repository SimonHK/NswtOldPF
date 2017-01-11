package com.nswt.cms.tag;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.data.DataTable;
import com.nswt.statical.tag.TagAttributeDesc;
import com.nswt.statical.tag.impl.ZListTag;

/**
 * 日期 : 2010-7-8 <br>
 * 作者: NSWT <br>
 * 邮箱：nswt@nswt.com.cn <br>
 */
public class CmsListTag extends ZListTag {
	private DataTable data;
	private int currentPos = 0;
	private String count;
	private String begin;

	public TagAttributeDesc[] getAllAttributeDescs() {
		return new TagAttributeDesc[] { new TagAttributeDesc("count", DataColumn.INTEGER),
				new TagAttributeDesc("begin", DataColumn.INTEGER), new TagAttributeDesc("var") };
	}

	public String getPrefix() {
		return "cms";
	}

	public String getTagName() {
		return "list";
	}
}
