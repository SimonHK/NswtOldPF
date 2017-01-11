package com.nswt.statical.tag.impl;

import com.nswt.framework.utility.Errorx;
import com.nswt.statical.tag.SimpleTag;
import com.nswt.statical.tag.TagAttributeDesc;
import com.nswt.statical.tag.TagBase;

/*
 * @Author NSWT
 * @Date 2016-7-26
 * @Mail nswt@nswt.com.cn
 */
public class ZElseTag extends SimpleTag {

	public int onTagStart() {
		// 本标签不需要做任何操作，只供上级z:if标签用于分隔两部分语句
		if (getParentTag().getTagName().equals("if") && getParentTag().getPrefix().equals("z")) {
			context.clearParentOutput();
		} else {
			Errorx.addMessage("<z:else>必须处于<z:if>和</z:if>之中!");
			return TagBase.SKIP;
		}
		return TagBase.CONTINUE;
	}

	public TagAttributeDesc[] getAllAttributeDescs() {
		return null;
	}

	public String getPrefix() {
		return "z";
	}

	public String getTagName() {
		return "else";
	}

	public boolean canWithinTag(String prefix, String name) {
		if ("z".equals(prefix) && "if".equals(name)) {
			return true;
		}
		return false;
	}
}
