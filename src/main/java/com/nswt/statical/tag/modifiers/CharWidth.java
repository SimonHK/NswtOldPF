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
		return "��ȡָ����ȵ��ַ������ֺ�ȫ�Ƿ��Ű�2���ַ���ȼ��㣬Ӣ����ĸ�����ְ�1���ַ���ȼ���";
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
