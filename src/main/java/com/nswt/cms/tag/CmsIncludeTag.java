package com.nswt.cms.tag;

import com.nswt.statical.tag.impl.ZIncludeTag;

/**
 * ���� : 2010-6-12 <br>
 * ����: NSWT <br>
 * ���䣺nswt@nswt.com.cn <br>
 */
public class CmsIncludeTag extends ZIncludeTag {

	public String getPrefix() {
		return "cms";
	}

	public String getTagName() {
		return "include";
	}

}
