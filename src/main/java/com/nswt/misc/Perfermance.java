package com.nswt.misc;

import com.nswt.framework.utility.ServletUtil;

/**
 * 日期 : 2010-5-11 <br>
 * 作者: NSWT <br>
 * 邮箱：nswt@nswt.com.cn <br>
 */
public class Perfermance {
	public static void main(String[] args) {
		final int threadCount = 4;
		for (int k = 0; k < threadCount; k++) {
			new Thread() {
				public void run() {
					long start = System.currentTimeMillis();
					long last = System.currentTimeMillis();
					for (int i = 0; i < 10000 / threadCount; i++) {
						if (i % 100 == 0) {
							System.out.println(i + ",百次耗时:" + (System.currentTimeMillis() - last));
							last = System.currentTimeMillis();
						}
						try {
							// ServletUtil
							// .getURLContent("http://localhost:8080/nswtp/Site/CatalogBasic.jsp?CatalogID=9937&InnerCode=002800");
							ServletUtil.getURLContent("http://localhost:8080/nswtp/Platform/Code.jsp");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					System.out.println(System.currentTimeMillis() - start);
				}
			}.start();
		}
	}
}
