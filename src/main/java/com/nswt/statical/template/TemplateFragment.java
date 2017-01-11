package com.nswt.statical.template;

import com.nswt.framework.utility.Mapx;

/**
 * @Author NSWT
 * @Date 2016-5-29
 * @Mail nswt@nswt.com.cn
 */
public class TemplateFragment {
	/**
	 * ��ͨHTML
	 */
	public static final int FRAGMENT_HTML = 1;

	/**
	 * ��ǩ
	 */
	public static final int FRAGMENT_TAG = 2;

	/**
	 * ռλ��
	 */
	public static final int FRAGMENT_HOLDER = 3;

	/**
	 * �ű�
	 */
	public static final int FRAGMENT_SCRIPT = 4;

	public int Type;

	public String TagPrefix;// ֻ������ΪFRAGMENT_TAG��ֵ

	public String TagName;// ֻ������ΪFRAGMENT_TAG��ֵ

	public Mapx Attributes;// ֻ������ΪFRAGMENT_TAG��ֵ

	public String FragmentText;

	public int StartLineNo;

	public int StartCharIndex;

	public int EndCharIndex;

	public String toString() {
		StringBuffer sb = new StringBuffer();
		if (Type == FRAGMENT_HTML) {
			sb.append("HTML");
		}
		if (Type == FRAGMENT_TAG) {
			sb.append("TAG");
		}
		if (Type == FRAGMENT_SCRIPT) {
			sb.append("SCRIPT");
		}
		if (Type == FRAGMENT_HOLDER) {
			sb.append("HOLDER");
		}
		sb.append(":");
		if (Type == FRAGMENT_TAG) {
			sb.append("<" + this.TagPrefix + ":" + this.TagName);
		}
		if (Type == FRAGMENT_HOLDER) {
			sb.append("${");
		}
		if (FragmentText != null) {
			String str = FragmentText.replaceAll("[\\n\\r]+", "\\\\n");
			str = FragmentText.replaceAll("\\s+", " ");
			if (str.length() > 100) {
				str = str.substring(0, 100);
			}
			sb.append(str);
		}
		if (Type == FRAGMENT_HOLDER) {
			sb.append("}");
		}
		return sb.toString();
	}
}
