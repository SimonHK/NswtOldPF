package com.nswt.statical.tag.modifiers;

import com.nswt.statical.tag.IModifierHandler;

/*
 * @Author NSWT
 * @Date 2016-7-22
 * @Mail nswt@nswt.com.cn
 */
public class Replace implements IModifierHandler {

	public String getName() {
		return "Replace";
	}

	public String getUsage() {
		return "�滻��ֵ�еĲ����ַ���֧��������ʽ";
	}

	public Object deal(Object value, String[] args) {
		if (args.length < 2) {
			throw new RuntimeException("Replace���η�Ҫ������������!");
		}
		return value.toString().replaceAll(args[0], args[1]);
	}

}
