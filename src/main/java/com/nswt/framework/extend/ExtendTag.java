package com.nswt.framework.extend;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.nswt.framework.utility.LogUtil;

/**
 * ���� : 2016-11-7 <br>
 * ����: NSWT <br>
 * ���䣺nswt@nswt.com.cn <br>
 */
public class ExtendTag extends TagSupport {
	private static final long serialVersionUID = 1L;

	private String target;

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public int doStartTag() throws JspException {
		try {
			if (ExtendManager.hasAction(target)) {
				IExtendAction actions[] = ExtendManager.find(target);
				for (int i = 0; i < actions.length; i++) {
					if (!(actions[i] instanceof JSPExtendAction)) {
						LogUtil.getLogger().warn("��" + actions[i].getClass().getName() + "����̳�JSPExtendAction!");
						continue;
					}
					actions[i].execute(new Object[] { pageContext });
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SKIP_BODY;
	}
}
