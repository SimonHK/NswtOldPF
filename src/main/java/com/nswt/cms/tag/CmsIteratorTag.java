package com.nswt.cms.tag;

import com.nswt.statical.tag.impl.ZListTag;

/*
 * @Author NSWT
 * @Date 2016-7-29
 * @Mail nswt@nswt.com.cn
 */
public class CmsIteratorTag extends ZListTag {
	public String getPrefix() {
		return "cms";
	}

	public String getTagName() {
		return "iterator";
	}
}
