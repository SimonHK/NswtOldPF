package com.nswt.framework.utility;

import java.lang.reflect.Array;

/**
 * ���Զ����ʽ��������ַ���
 * 
 * @Author ������
 * @Date 2008-4-16
 * @Mail nswt@nswt.com.cn
 */
public abstract class Formatter {
	/**
	 * Ĭ�ϸ�ʽ���࣬���������Ԫ�ص�����
	 */
	public static Formatter DefaultFormatter = new Formatter() {
		public String format(Object o) {
			if (o == null) {
				return null;
			}
			if (o.getClass().isArray()) {
				StringBuffer sb = new StringBuffer();
				sb.append("{");
				for (int i = 0; i < Array.getLength(o); i++) {
					if (i != 0) {
						sb.append(",");
					}
					sb.append(Array.get(o, i));
				}
				sb.append("}");
				return sb.toString();
			}
			return o.toString();
		}
	};

	/**
	 * ��ʽ�����󣬷����ַ���
	 */
	public abstract String format(Object obj);
}
