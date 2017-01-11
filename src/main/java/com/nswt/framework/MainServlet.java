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

/**
 * 所有JavaScript中的Server.sendRequest()请求都将发送到本Servlet。<br>
 * 本Servlet将根据ServletRequest初始化相关对象，并通过反射执行指定方法。<br>
 * 未登录的用户调用继承Page的类时，本Servlet将会重定向到指定的登录页面。
 * 
 * @author shuguang
 * @date 2016-11-15
 * @email sheshuguang@163.com
 */
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			response.setHeader("Pragma", "No-Cache");
			response.setHeader("Cache-Control", "No-Cache");
			response.setDateHeader("Expires", 0);

			response.setContentType("text/xml");

			if (Config.ServletMajorVersion == 2 && Config.ServletMinorVersion == 3) {
				response.setContentType("text/xml;charset=utf-8");
			} else {
				response.setCharacterEncoding("UTF-8");// 从js中传入的编码默认是utf-8
			}
			request.setCharacterEncoding("UTF-8");

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
									"此操作被拒绝!");
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
