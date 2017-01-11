package com.nswt.framework.controls;

import java.util.ArrayList;
import java.util.regex.Matcher;

import com.nswt.framework.utility.StringUtil;

/**
 * @Author NSWT
 * @Date 2007-6-21
 * @Mail nswt@nswt.com.cn
 */
public class HtmlTR extends HtmlElement {
	protected HtmlTable parent;

	protected ArrayList pList = null;

	public HtmlTR() {
		this(null);
	}

	public HtmlTR(HtmlTable parent) {
		this.parent = parent;
		ElementType = HtmlElement.TR;
		TagName = "tr";
	}

	public void addTD(HtmlTD td) {
		addChild(td);
	}

	public HtmlTD getTD(int index) {
		return (HtmlTD) Children.get(index);
	}

	public void removeTD(int index) {
		if (index < 0 || index > Children.size()) {
			throw new RuntimeException("���������");
		}
		Children.remove(index);
	}

	public void setHeight(int height) {
		Attributes.put("height", new Integer(height));
	}

	public int getHeight() {
		return ((Integer) Attributes.get("height")).intValue();
	}

	public void setAlign(String align) {
		Attributes.put("align", align);
	}

	public String getAlign() {
		return (String) Attributes.get("align");
	}

	public void setBgColor(String bgColor) {
		Attributes.put("bgColor", bgColor);
	}

	public String getVAlign() {
		return (String) Attributes.get("vAlign");
	}

	public void setVAlign(String vAlign) {
		Attributes.put("vAlign", vAlign);
	}

	public int getRowIndex() {
		for (int i = 0; i < ParentElement.Children.size(); i++) {
			if (ParentElement.Children.get(i).equals(this)) {
				return i;
			}
		}
		throw new RuntimeException("�õ�RowIndexʱ��������");
	}

	public void parseHtml(String html) throws Exception {
		Matcher m = HtmlTable.PTR.matcher(html);
		if (!m.find()) {
			throw new Exception("TR����htmlʱ��������");
		}
		String attrs = m.group(1);
		String tds = m.group(2).trim();

		// ���ԭ�����Ժ���Ԫ��
		Attributes.clear();
		Children.clear();

		// ������Ԫ�����Table
		m = HtmlTable.PInnerTable.matcher(tds);
		int lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			if (pList == null) {
				pList = new ArrayList();
			}
			pList.add(m.group(0));
			lastEndIndex = m.end();
		}
		if (pList != null) {
			for (int i = 0; i < pList.size(); i++) {
				tds = StringUtil.replaceEx(tds, pList.get(i).toString(), HtmlTable.ProtectedTableStart + i + "-->");
			}
		}

		Attributes = parseAttr(attrs);

		// ������Ԫ��
		m = HtmlTable.PTDPre.matcher(tds);
		lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			String t = tds.substring(m.start(), m.end());
			HtmlTD td = new HtmlTD(this);
			td.parseHtml(t);
			addTD(td);
			lastEndIndex = m.end();
		}
	}

	public String restoreInnerTable(String html) {
		if (pList == null || pList.size() == 0) {
			return html;
		}
		String[] arr = StringUtil.splitEx(html, HtmlTable.ProtectedTableStart);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			if (StringUtil.isNotEmpty(arr[i])) {
				if (i != 0) {
					int index = Integer.parseInt(arr[i].substring(0, arr[i].indexOf("-")));
					sb.append(pList.get(index).toString());
					arr[i] = arr[i].substring(arr[i].indexOf(">") + 1);
				}
				sb.append(arr[i]);
			}
		}
		return sb.toString();
	}

	public HtmlTable getParent() {
		return parent;
	}
}
