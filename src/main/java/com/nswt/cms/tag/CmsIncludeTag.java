package com.nswt.cms.tag;

import com.nswt.statical.tag.impl.ZIncludeTag;

/**
 * 日期 : 2010-6-12 <br>
 * 作者: NSWT <br>
 * 邮箱：nswt@nswt.com.cn <br>
 */
public class CmsIncludeTag extends ZIncludeTag {

	public String getPrefix() {
		return "cms";
	}

	public String getTagName() {
		return "include";
	}

}
