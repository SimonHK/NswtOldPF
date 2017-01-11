package com.nswt.cms.tag;

import com.nswt.statical.tag.impl.ZElseTag;

/*
 * @Author NSWT
 * @Date 2016-7-29
 * @Mail nswt@nswt.com.cn
 */
public class CmsElseTag extends ZElseTag {
	public String getPrefix() {
		return "cms";
	}

	public String getTagName() {
		return "else";
	}
}
