/**
 * 作者: NSWT<br>
 * 日期：2016-9-26<br>
 * 邮件：nswt@nswt.com.cn<br>
 */
package com.nswt.framework.controls;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.nswt.framework.Constant;
import com.nswt.framework.Current;
import com.nswt.framework.utility.StringUtil;

public class DataListTag extends BodyTagSupport {
	private static final long serialVersionUID = 1L;

	private String method;

	private String id;

	private int size;

	private boolean page;

	public void setPageContext(PageContext pc) {
		super.setPageContext(pc);
		this.method = null;
		this.id = null;
		this.page = true;
		this.size = 0;
	}

	public int doAfterBody() throws JspException {
		BodyContent body = this.getBodyContent();
		String content = body.getString().trim();
		try {
			if (StringUtil.isEmpty(method)) {
				throw new RuntimeException("DataList未指定Method");
			}

			DataListAction dla = new DataListAction();
			dla.setTagBody(content);
			dla.setPage(this.page);
			dla.setMethod(method);

			dla.setID(id);
			HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
			HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
			dla.setPageSize(size);

			if (page) {
				dla.setPageIndex(0);
				if (StringUtil.isNotEmpty(dla.getParam(Constant.DataGridPageIndex))) {
					dla.setPageIndex(Integer.parseInt(dla.getParam((Constant.DataGridPageIndex))));
				}
				if (dla.getPageIndex() < 0) {
					dla.setPageIndex(0);
				}
				dla.setPageSize(size);
			}

			Current.init(request, response, method);
			dla.setParams(Current.getRequest());
			Current.invokeMethod(method, new Object[] { dla });

			pageContext.setAttribute(id + Constant.DataGridPageTotal, "" + dla.getTotal());
			pageContext.setAttribute(id + Constant.DataGridPageIndex, "" + dla.getPageIndex());
			pageContext.setAttribute(id + Constant.Size, "" + dla.getPageSize());

			getPreviousOut().write(dla.getHtml());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return EVAL_PAGE;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public boolean isPage() {
		return page;
	}

	public void setPage(boolean page) {
		this.page = page;
		/* ${_NSWT_LICENSE_CODE_} */
	}
}
