package com.nswt.framework.controls;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.nswt.framework.utility.Mapx;

/**
 * @Author NSWT
 * @Date 2007-6-21
 * @Mail nswt@nswt.com.cn
 */
public abstract class HtmlElement implements Cloneable {

	public static final Pattern PAttr = Pattern.compile("\\s+?(\\w+?)\\s*?=\\s*?(\\\"|\\\')(.*?)\\2",
			Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	public static final Pattern PAttr2 = Pattern.compile("\\s+?(\\w+?)\\s*?=\\s*?([\\S&&[^\\\"\\\']]*?)(\\s|>)",
			Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	public static final String TABLE = "TABLE";

	public static final String TR = "TR";

	public static final String TD = "TD";

	public static final String SCRIPT = "SCRIPT";

	public static final String DIV = "DIV";

	public static final String SPAN = "SPAN";

	public static final String P = "P";

	public static final String SELECT = "SELECT";

	protected String ElementType;

	protected String TagName;

	public Mapx Attributes = new Mapx();

	protected HtmlElement ParentElement;

	public ArrayList Children = new ArrayList();

	public String InnerHTML;

	public String getOuterHtml() {
		StringBuffer sb = new StringBuffer();
		sb.append("<");
		sb.append(TagName);
		Object[] ks = Attributes.keyArray();
		Object[] vs = Attributes.valueArray();
		for (int i = 0; i < Attributes.size(); i++) {
			if (vs[i] != null) {
				sb.append(" ");
				sb.append(ks[i]);
				sb.append("=\"");
				sb.append(vs[i]);
				sb.append("\"");
			}
		}
		sb.append(">");

		if (InnerHTML != null) {
			sb.append(InnerHTML);
		} else {
			sb.append("\n");
			for (int i = 0; i < Children.size(); i++) {
				sb.append(((HtmlElement) Children.get(i)).getOuterHtml());
			}
		}

		sb.append("</");
		sb.append(TagName);
		sb.append(">\n");
		return sb.toString();
	}

	public String getAttribute(String attrName) {
		return Attributes.getString(attrName.toLowerCase());
	}

	public void setAttribute(String attrName, String attrValue) {
		Attributes.put(attrName.toLowerCase(), attrValue);
	}

	public void removeAttribute(String attrName) {
		Attributes.remove(attrName.toLowerCase());
	}

	public Mapx getAttributes() {
		return Attributes;
	}

	public void setAttributes(Mapx attributes) {
		Attributes.clear();
		Attributes.putAll(attributes);
	}

	public ArrayList getChildren() {
		return Children;
	}

	public void addChild(HtmlElement child) {
		Children.add(child);
		child.setParentElement(this);
	}

	public String getClassName() {
		return Attributes.getString("class");
	}

	public void setClassName(String className) {
		Attributes.put("class", className);
	}

	public String getElementType() {
		return ElementType;
	}

	public String getID() {
		return Attributes.getString("id");
	}

	public void setID(String id) {
		Attributes.put("id", id);
	}

	public HtmlElement getParentElement() {
		return ParentElement;
	}

	protected void setParentElement(HtmlElement parentElement) {
		ParentElement = parentElement;
	}

	public String getStyle() {
		return Attributes.getString("style");
	}

	public void setStyle(String style) {
		Attributes.put("style", style);
	}

	public String getTagName() {
		return TagName;
	}

	public String getInnerHTML() {
		if (this.Children.size() > 0) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < this.Children.size(); i++) {
				sb.append(((HtmlElement) this.Children.get(i)).getOuterHtml());
			}
			return sb.toString();
		} else {
			return InnerHTML;
		}
	}

	public void setInnerHTML(String innerHTML) {
		InnerHTML = innerHTML;
	}

	public static Mapx parseAttr(String attrs) {
		// ��������
		Matcher m = PAttr.matcher(attrs);
		int lastEndIndex = 0;
		Mapx map = new Mapx();
		while (m.find(lastEndIndex)) {
			map.put(m.group(1).toLowerCase(), m.group(3));
			lastEndIndex = m.end();
		}
		return map;
	}

	protected static Mapx PatternMap = new Mapx();

	private Pattern getParrtenByTagName() {
		Object o = PatternMap.get(this.TagName);
		if (o == null) {
			Pattern pattern = Pattern.compile("<" + this.TagName + "(.*?)>(.*?)</" + this.TagName + ">",
					Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
			PatternMap.put(this.TagName, pattern);
			return pattern;
		}
		return (Pattern) o;
	}

	public void parseHtml(String html) throws Exception {
		Pattern pattern = getParrtenByTagName();
		Matcher m = pattern.matcher(html);
		if (!m.find()) {
			throw new Exception(TagName + "����htmlʱ��������");
		}
		String attrs = m.group(1);

		// ���ԭ�����Ժ���Ԫ��
		Attributes.clear();
		Children.clear();

		Attributes = parseAttr(attrs);
		InnerHTML = m.group(2).trim();
	}

	public Object clone() {
		HtmlElement ele = null;
		try {
			ele = (HtmlElement) getClass().newInstance();
			ele.setAttributes((Mapx) Attributes.clone());
			for (int i = 0; i < Children.size(); i++) {
				ele.addChild((HtmlElement) Children.get(i));
			}
			ele.InnerHTML = this.InnerHTML;
			ele.TagName = this.TagName;
			ele.ElementType = this.ElementType;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ele;
	}

	public String toString() {
		return getOuterHtml();
	}
}
