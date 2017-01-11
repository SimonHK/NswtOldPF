package com.nswt.cms.tag;

import com.nswt.statical.tag.impl.ZIfTag;

/*
 * @Author NSWT
 * @Date 2016-7-29
 * @Mail nswt@nswt.com.cn
 */
public class CmsIfTag extends ZIfTag {
	public String getPrefix() {
		return "cms";
	}

	public String getTagName() {
		return "if";
	}
}
