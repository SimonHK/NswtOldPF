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
		return "替换掉值中的部分字符，支持正则表达式";
	}

	public Object deal(Object value, String[] args) {
		if (args.length < 2) {
			throw new RuntimeException("Replace修饰符要求有两个参数!");
		}
		return value.toString().replaceAll(args[0], args[1]);
	}

}
