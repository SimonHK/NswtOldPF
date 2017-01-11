package com.nswt.framework.controls;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.nswt.framework.utility.StringFormat;
import com.nswt.framework.utility.StringUtil;

/**
 * @Author »ÆÀ×
 * @Date 2007-8-23
 * @Mail huanglei@nswt.com
 */
public class ChildTabTag extends BodyTagSupport {
	private static final long serialVersionUID = 1L;

	private String id;

	private String onClick;

	private String afterClick;

	private String src;

	private boolean selected;

	private boolean disabled;

	private boolean visible;

	private boolean lazy;

	private static int No = 0;

	public void setPageContext(PageContext pc) {
		super.setPageContext(pc);
		this.id = null;
		this.onClick = null;
		this.afterClick = null;
		this.src = null;
		this.selected = false;
		this.disabled = false;
		this.visible = true;
		this.lazy = false;
	}

	public int doAfterBody() throws JspException {
		String content = this.getBodyContent().getString();
		try {
			if (StringUtil.isEmpty(id)) {
				id = "" + No++;
			}
			if (onClick == null) {
				onClick = "";
			}
			if (StringUtil.isNotEmpty(onClick) && !onClick.trim().endsWith(";")) {
				onClick = onClick.trim() + ";";
			}
			if (afterClick == null) {
				afterClick = "";
			}
			String type = "";
			if (selected) {
				type = "Current";
			} else if (disabled) {
				type = "Disabled";
			}
			StringBuffer sb = new StringBuffer();
			String vStr = "";
			if (!visible) {
				vStr = "style='display:none'";
			}
			sb.append("<div style='-moz-user-select:none;' onselectstart='return false' ");
			if ("Disabled".equalsIgnoreCase(type)) {
				sb.append("id='" + id + "' " + vStr + " targetURL='" + src + "' class='divchildtab" + type + "' >");
			} else {
				if (lazy) {
					src = "src='javascript:void(0);' targetURL='" + src + "'";
				} else {
					src = "src='" + src + "'";
				}
				StringFormat sf = new StringFormat(
						"id='?' ? class='divchildtab?' ? onclick=\"?Tab.onChildTabClick(this);?\" "
								+ "onMouseOver='Tab.onChildTabMouseOver(this)' onMouseOut='Tab.onChildTabMouseOut(this)'>");
				sf.add(id);
				sf.add(vStr);
				sf.add(type);
				sf.add(src);
				sf.add(onClick);
				sf.add(afterClick);
				sb.append(sf.toString());
			}
			sb.append(content);
			sb.append("</div>");
			this.getPreviousOut().print(sb);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
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

	public String getAfterClick() {
		return afterClick;
	}

	public void setAfterClick(String afterClick) {
		this.afterClick = afterClick;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isLazy() {
		return lazy;
	}

	public void setLazy(boolean lazy) {
		this.lazy = lazy;
	}
}
