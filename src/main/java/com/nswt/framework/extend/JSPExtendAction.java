package com.nswt.framework.extend;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import com.nswt.framework.Ajax;
import com.nswt.framework.CookieImpl;
import com.nswt.framework.Current;
import com.nswt.framework.RequestImpl;

/**
 * 日期 : 2016-11-7 <br>
 * 作者: NSWT <br>
 * 邮箱：nswt@nswt.com.cn <br>
 */
public abstract class JSPExtendAction extends Ajax implements IExtendAction {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nswt.framework.extend.IExtendAction#execute(java.lang.Object[])
	 */
	public void execute(Object[] args) {
		PageContext pageContext = (PageContext) args[0];
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
		Current.init(request, response, this.getClass().getName() + ".execute");
		RequestImpl requestImpl = Current.getRequest();
		CookieImpl cookie = Current.getPage().getCookies();
		String html = execute(requestImpl, cookie);
		try {
			pageContext.getOut().print(html);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public abstract String execute(RequestImpl request, CookieImpl cookie);
}
