package com.nswt.framework.controls;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * @Author NSWT
 * @Date 2007-7-20
 * @Mail nswt@nswt.com.cn
 */
public class TButtonTag extends BodyTagSupport {
	private static final long serialVersionUID = 1L;

	private String id;

	private String onClick;

	private String priv;
	
	private boolean disabled;

	public static final Pattern PImg = Pattern.compile("<img .*?src\\=.*?>",
			Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	public int doAfterBody() throws JspException {
		String content = this.getBodyContent().getString();
		try {
			Matcher matcher = PImg.matcher(content);
			String img = null;
			String text = null;
			if (matcher.find()) {
				img = content.substring(matcher.start(), matcher.end());
				text = content.substring(matcher.end());
			}
			this.getPreviousOut().print(
					getHtml(id, onClick, priv, img, text + "&nbsp;",disabled));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

	public static String getHtml(String onclick, String img, String text,boolean disabled) {
		return getHtml(null, onclick, null, img, text, disabled);
	}

	public static String getHtml(String onclick, String priv, String img, String text) {
		return getHtml(null, onclick, priv, img, text, false);
	}
	
	public static String getHtml(String onclick, String priv, String img, String text,boolean disabled) {
		return getHtml(null, onclick, priv, img, text, disabled);
	}

	/**
	 * ������Java�ļ��е���
	 */
	public static String getHtml(String id, String onclick, String priv,
			String img, String text, boolean disabled) {
		StringBuffer sb = new StringBuffer();
		sb.append("<a href='javascript:void(0);' ztype='zPushBtn' class='zPushBtn"
				+(disabled==true ? " zPushBtnDisabled" : "")
				+"' hidefocus='true' tabindex='-1' onselectstart='return false' id='"
				+ (id == null ? "" : id) + "'");
		if (onclick != null) {
			if(disabled == true){
				sb.append(" onclickbak=\"" + onclick + ";return false;");
			}else{
				sb.append(" onClick=\"" + onclick + ";return false;");
			}
		}
		if (priv != null) {
			sb.append("\" priv=\"" + priv);
		}
		sb.append("\" >");
		sb.append(img);
		sb.append("<b>" + text + "</b></a>");
		return sb.toString();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOnClick() {
		return onClick;
	}

	public void setOnClick(String onClick) {
		this.onClick = onClick;
	}

	public String getPriv() {
		return priv;
	}

	public void setPriv(String priv) {
		this.priv = priv;
	}
	
	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
}
