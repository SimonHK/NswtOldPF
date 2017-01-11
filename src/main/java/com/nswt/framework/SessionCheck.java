package com.nswt.framework;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ��鵱ǰ�����Ƿ�����
 * 
 * @author NSWT
 * @date 2016-11-15
 * @email nswt@nswt.com.cn
 */
public class SessionCheck {
	/**
	 * ��鵱ǰҳ���Ƿ������û�����
	 */
	public static void check(HttpServletRequest request, HttpServletResponse response) {
		/* ${_NSWT_LICENSE_CODE_} */
	}

	/**
	 * ����̨���Ƿ���������
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
