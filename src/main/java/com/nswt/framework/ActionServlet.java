package com.nswt.framework;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nswt.framework.data.DataCollection;
import com.nswt.framework.extend.AfterPageMethodInvokeAction;
import com.nswt.framework.extend.BeforePageMethodInvokeAction;
import com.nswt.framework.extend.ExtendManager;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.StringUtil;

/*
 * 前台表单提交、页面跳转统一Action映射类
 * 凡类似于com.nswt.PageClass.methodName.action的URL都会变重定向到此Servlet
 * 
 * @Author 王育春
 * @Date 2016-9-14
 * @Mail nswt@nswt.com.cn
 */
public class ActionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			response.setHeader("Pragma", "No-Cache");
			response.setHeader("Cache-Control", "No-Cache");
			response.setDateHeader("Expires", 0);

			String method = request.getParameter(Constant.Method);
			String url = request.getParameter(Constant.URL);
			if ("".equals(url) || "/".equals(url)) {
				url = "/Index.jsp";
			}

			if ("www.nswt.com".equalsIgnoreCase(request.getServerName())
					&& "/demo".equalsIgnoreCase(request.getContextPath())) {
				if (!"admin".equalsIgnoreCase(User.getUserName())
						&& getServletConfig().getInitParameter(method) != null) {
					LogUtil.getLogger().warn(
							"method:" + method + ",操作：" + getServletConfig().getInitParameter(method)
									+ "此操作被拒绝!<br>系统提示：为保证博雅软件Demo站的稳定运行，Demo站中部分删除功能已被屏蔽.");
					DataCollection dcResponse = new DataCollection();
					dcResponse.put(Constant.ResponseStatusAttrName, "0");
					dcResponse
							.put(Constant.ResponseMessageAttrName,
									"此操作被拒绝!<br>系统提示：为保证博雅软件Demo站的稳定运行，Demo站中部分删除功能已被屏蔽.如需要可下载安装程序到本地来试用.<br>下载地址：<a href='http://www.nswt.com/download/program/index.shtml' target='_blank'>下载nswtp</a>");
					response.getWriter().write(dcResponse.toXML());
					return;
				}
			}

			Current.init(request, response, method);

			if (StringUtil.isEmpty(method)) {
				LogUtil.warn("错误的Server.sendRequest()调用，QueryString=" + request.getQueryString() + "，Referer="
						+ request.getHeader("referer"));
				return;
			}
			String className = method.substring(0, method.lastIndexOf("."));
			Class c = Class.forName(className);

			String LoginClass = Config.getValue("App.LoginClass");
			// 未登录的用户重定向
			if (!Ajax.class.isAssignableFrom(c) && !className.equals("com.nswt.framework.Framework")
					&& !className.equals(LoginClass) && !User.isLogin()) {
				DataCollection dcResponse = new DataCollection();
				dcResponse.put(Constant.ResponseScriptAttr,
						"window.top.location='" + Config.getContextPath() + Config.getLoginPage() + "';");
				response.getWriter().write(dcResponse.toXML());
				return;
			}

			if (!className.equals(LoginClass) && !SessionCheck.check(c)) {
				DataCollection dcResponse = new DataCollection();
				dcResponse.put(Constant.ResponseMessageAttrName, "不允许越权访问!");
				response.getWriter().write(dcResponse.toXML());
				return;
			}

			if (ExtendManager.hasAction(BeforePageMethodInvokeAction.Type)) {
				ExtendManager.executeAll(BeforePageMethodInvokeAction.Type, new Object[] { method });
			}

			Current.invokeMethod(method, (Object[]) null);

			if (ExtendManager.hasAction(AfterPageMethodInvokeAction.Type)) {
				ExtendManager.executeAll(AfterPageMethodInvokeAction.Type, new Object[] { method });
			}

			response.getWriter().write(Current.getResponse().toXML());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
