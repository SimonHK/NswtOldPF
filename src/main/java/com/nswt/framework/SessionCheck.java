package com.nswt.framework;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 检查当前访问是否被允许
 * 
 * @author NSWT
 * @date 2016-11-15
 * @email nswt@nswt.com.cn
 */
public class SessionCheck {
	/**
	 * 检查当前页面是否允许用户访问
	 */
	public static void check(HttpServletRequest request, HttpServletResponse response) {
		/* ${_NSWT_LICENSE_CODE_} */
	}

	/**
	 * 检查后台类是否允许被访问
	 */
	public static boolean check(Class c) {
		if (!Ajax.class.isAssignableFrom(c)) {
			if (!User.isLogin()) {
				return false;
			}
			if (!User.isManager()) {
				return false;
			}
		}
		return true;
	}
}
