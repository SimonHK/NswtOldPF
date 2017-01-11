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
 * ǰ̨���ύ��ҳ����תͳһActionӳ����
 * ��������com.nswt.PageClass.methodName.action��URL������ض��򵽴�Servlet
 * 
 * @Author ������
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
							"method:" + method + ",������" + getServletConfig().getInitParameter(method)
									+ "�˲������ܾ�!<br>ϵͳ��ʾ��Ϊ��֤�������Demoվ���ȶ����У�Demoվ�в���ɾ�������ѱ�����.");
					DataCollection dcResponse = new DataCollection();
					dcResponse.put(Constant.ResponseStatusAttrName, "0");
					dcResponse
							.put(Constant.ResponseMessageAttrName,
									"�˲������ܾ�!<br>ϵͳ��ʾ��Ϊ��֤�������Demoվ���ȶ����У�Demoվ�в���ɾ�������ѱ�����.����Ҫ�����ذ�װ���򵽱���������.<br>���ص�ַ��<a href='http://www.nswt.com/download/program/index.shtml' target='_blank'>����nswtp</a>");
					response.getWriter().write(dcResponse.toXML());
					return;
				}
			}

			Current.init(request, response, method);

			if (StringUtil.isEmpty(method)) {
				LogUtil.warn("�����Server.sendRequest()���ã�QueryString=" + request.getQueryString() + "��Referer="
						+ request.getHeader("referer"));
				return;
			}
			String className = method.substring(0, method.lastIndexOf("."));
			Class c = Class.forName(className);

			String LoginClass = Config.getValue("App.LoginClass");
			// δ��¼���û��ض���
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
				dcResponse.put(Constant.ResponseMessageAttrName, "������ԽȨ����!");
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
