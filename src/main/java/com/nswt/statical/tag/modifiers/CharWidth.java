package com.nswt.statical.tag.modifiers;

import com.nswt.framework.utility.StringUtil;
import com.nswt.statical.tag.IModifierHandler;

/*
 * @Author NSWT
 * @Date 2016-7-21
 * @Mail nswt@nswt.com.cn
 */
public class CharWidth implements IModifierHandler {

	public String getName() {
		return "CharWidth";
	}

	public String getUsage() {
		return "截取指定宽度的字符，汉字和全角符号按2个字符宽度计算，英文字母和数字按1个字符宽度计算";
	}

	public Object deal(Object value, String[] args) {
		String charWidth = args[0];
		if (StringUtil.isDigit(charWidth)) {
			if (StringUtil.lengthEx(value.toString()) > Integer.parseInt(charWidth) * 2) {
				return StringUtil.subStringEx(value.toString(), Integer.parseInt(charWidth) - 1) + "...";
			}
		}
		return value;
	}

}
