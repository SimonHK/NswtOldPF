package com.nswt;

import com.nswt.framework.license.IProduct;

/*
 * ��������:2016-7-8
 * ����: NSWT
 * ����:nswt@nswt.com.cn
 */
public class Product implements IProduct {
	public String getAppCode() {
		return "nswtp";
	}

	public String getAppName() {
		return "��������Ӧ����ƽ̨";
	}

	public float getMainVersion() {
		return 1.3f;
	}

	public float getMinorVersion() {
		return 2f;
	}
}
