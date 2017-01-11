package com.nswt.framework.extend;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 日期 : 2016-11-7 <br>
 * 作者: NSWT <br>
 * 邮箱：nswt@nswt.com.cn <br>
 */
public abstract class AfterMainFilterAction implements IExtendAction {
	public static final String Type = "AfterMainFilter";

	public String getTarget() {
		return Type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nswt.framework.extend.IExtendAction#execute(com.nswt.framework.
	 * utility.Mapx)
	 */
	public void execute(Object[] args) {
		HttpServletRequest request = (HttpServletRequest) args[0];
		HttpServletResponse response = (HttpServletResponse) args[1];
		FilterChain chain = (FilterChain) args[2];
		execute(request, response, chain);
	}

	public abstract void execute(HttpServletRequest request, HttpServletResponse response, FilterChain chain);
}
