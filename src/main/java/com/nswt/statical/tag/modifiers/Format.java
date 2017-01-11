package com.nswt.statical.tag.modifiers;

import java.text.DecimalFormat;
import java.util.Date;

import com.nswt.framework.utility.DateUtil;
import com.nswt.statical.tag.IModifierHandler;

/*
 * @Author NSWT
 * @Date 2016-7-21
 * @Mail nswt@nswt.com.cn
 */
public class Format implements IModifierHandler {

	public String getName() {
		return "Format";
	}

	public String getUsage() {
		return "格式化日期和数字";
	}

	public Object deal(Object value, String[] args) {
		String format = args[0];
		if (value instanceof Date) {
			return DateUtil.toString((Date) value, format);
		}
		if (value instanceof Number) {
			return new DecimalFormat(format).format((Number) value);
		}
		return value;
	}

}
