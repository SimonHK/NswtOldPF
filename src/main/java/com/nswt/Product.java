package com.nswt;

import com.nswt.framework.license.IProduct;

/*
 * 创建日期:2016-7-8
 * 作者: NSWT
 * 邮箱:nswt@nswt.com.cn
 */
public class Product implements IProduct {
	public String getAppCode() {
		return "nswtp";
	}

	public String getAppName() {
		return "新宇联安应用云平台";
	}

	public float getMainVersion() {
		return 1.3f;
	}

	public float getMinorVersion() {
		return 2f;
	}
}
