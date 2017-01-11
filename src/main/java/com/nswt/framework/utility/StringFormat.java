package com.nswt.framework.utility;

import java.util.ArrayList;

/**
 * �ַ�����ʽ���࣬�����ڲ�����SQL�����Ա���������ַ���ƴ�ӡ�<br>
 * ���磺
 * 
 * <pre>
 * StringFormat sf = new StringFormat(&quot;��ӭ?��?���ʱ�վ.&quot;);
 * sf.add(&quot;wyuch&quot;);
 * sf.add(&quot;2016-10-11&quot;);
 * System.out.println(sf);
 * </pre>
 * 
 * ִ�к��������ӭwyuch��2016-10-11���ʱ�վ.
 * 
 * ����: NSWT<br>
 * ���ڣ�2016-9-30<br>
 * �ʼ���nswt@nswt.com.cn<br>
 */
public class StringFormat {
	private String formatStr;

	private ArrayList params = new ArrayList();

	public StringFormat(String str) {
		this.formatStr = str;
	}

	public void add(String v) {
		params.add(v);
	}

	public void add(long v) {
		add(String.valueOf(v));
	}

	public void add(int v) {
		add(String.valueOf(v));
	}

	public void add(float v) {
		add(String.valueOf(v));
	}

	public void add(double v) {
		add(String.valueOf(v));
	}

	public void add(Object v) {
		add(String.valueOf(v));
	}

	public String toString() {
		String[] arr = StringUtil.splitEx(formatStr, "?");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length - 1; i++) {
			sb.append(arr[i]);
			sb.append(params.get(i));
		}
		sb.append(arr[arr.length - 1]);
		return sb.toString();
	}

	public static void main(String[] args) {
		StringFormat sf = new StringFormat("c ? b ? a ");
		sf.add(1);
		sf.add(2);
		sf.add(3);
		System.out.println(sf);
	}
}
