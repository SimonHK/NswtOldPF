/**
 * @Author NSWT
 * @Date 2016-6-18
 * @Mail wyuch@m165.com
 */
package com.nswt.framework;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.nswt.framework.User.UserData;
import com.nswt.framework.data.BlockingTransaction;
import com.nswt.framework.extend.AfterMainFilterAction;
import com.nswt.framework.extend.ExtendManager;
import com.nswt.framework.utility.Errorx;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;

/**
 * ����JSPҳ����ʶ��ɴ�FilterԤ����ִ��ҳ����ַ��������á��Ự��顢�߳�ȫ�ֶ����ʼ���ȶ���
 */
public class MainFilter implements Filter {
	private long uptime = 0;

	private String[] NoFilterPaths;

	private boolean InitFlag = true;

	public void init(FilterConfig config) throws ServletException {
		LogUtil.info("----" + Config.getAppCode() + "(" + Config.getAppName() + "): MainFilter Initialized----");
		ServletContext sc = config.getServletContext();
		Config.configMap.put("System.ContainerInfo", sc.getServerInfo());// ���ӳ���Ҫ������ԣ���������
		Config.getJBossInfo();// ע�⣺Jboss�����ֵ��ApacheTomcat/5.x֮��ģ���MailFilter�����Configִ��
		Config.ServletMajorVersion = sc.getMajorVersion();
		Config.ServletMinorVersion = sc.getMinorVersion();

		uptime = System.currentTimeMillis();
		Config.setValue("App.Uptime", "" + uptime);

		String paths = Config.getValue("App.NoFilterPath");
		if (StringUtil.isNotEmpty(paths)) {
			String[] arr = paths.split(",");
			for (int i = 0; i < arr.length; i++) {
				String path = arr[i];
				if (!path.startsWith("/")) {
					path = "/" + path;
				}
				if (!path.endsWith("/")) {
					path = path + "/";
				}
			}
		}
	}

	public boolean isNoFilterPath(String url) {
		if (NoFilterPaths == null) {
			return false;
		}
		url = url + "/";
		for (int i = 0; i < NoFilterPaths.length; i++) {
			if (url.indexOf(NoFilterPaths[i]) >= 0) {
				return true;
			}
		}
		return false;
	}

	public void doFilter(ServletRequest req, ServletResponse rep, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) rep;
		String url = request.getServletPath();
		if (isNoFilterPath(url)) {
			chain.doFilter(request, response);
			return;
		}

		request.setCharacterEncoding(Constant.GlobalCharset);
		if (Config.ServletMajorVersion == 2 && Config.ServletMinorVersion == 3) {
			response.setContentType("text/html;charset=" + Constant.GlobalCharset);
		} else {
			response.setCharacterEncoding(Constant.GlobalCharset);
		}

		Current.clear();// ��Ҫ��֤ÿ��ҳ����������ͬһ��Pageʹ�õ���ͬһ��ʵ��

		// ��ʼ���û����ݡ�����������
		HttpSession session = ((HttpServletRequest) request).getSession();
		UserData u = (UserData) session.getAttribute(Constant.UserAttrName);
		if (u == null) {
			boolean flag = true;
			if (Config.isDebugMode()) {
				Cookie[] cs = request.getCookies();
				if (cs != null) {
					for (int i = 0; i < cs.length; i++) {
						if (cs[i].getName().equals(Constant.SessionIDCookieName)) {
							u = User.getCachedUser(cs[i].getValue());
							if (u != null) {
								flag = false;
								break;
							}
						}
					}
				}
			}
			if (flag) {
				u = new UserData();
			}
			u.setSessionID(session.getId());
		}
		User.setCurrent(u);

		// ��ʼ��·������Ҫ���Ǽ�Ⱥ���������Ҫ����������·����һ�µ����
		// ע������������������ʱ��cookie��ֵ�õ���path��������ͬ�����ط���·����http://IP/nswtp������������·����:http://����
		String contextPath = request.getContextPath();
		if (!contextPath.endsWith("/")) {
			contextPath = contextPath + "/";
		}
		if (Config.isComplexDepolyMode()) {
			User.setValue("App.ContextPath", contextPath);
		}
		if (InitFlag) {
			Config.setValue("App.ContextPath", contextPath);
			InitFlag = false;
		}

		if (!Config.isInstalled && url.indexOf("Install.jsp") < 0 && url.indexOf("MainServlet.jsp") < 0) {
			RequestDispatcher rd = request.getRequestDispatcher("/Install.jsp");
			rd.forward(req, rep);
			return;
		}

		Errorx.init();

		if (url != null && url.indexOf("/MainServlet.jsp") > 0 && !url.equals("/MainServlet.jsp")) {// ҳ���ʼ��ʱ�����������
			RequestDispatcher rd = request.getRequestDispatcher("/MainServlet.jsp");
			rd.forward(req, rep);
			return;
		} else {
			SessionCheck.check(request, response);// �û�Ȩ�޼��
		}
		if (!Errorx.hasDealed()) {
			LogUtil.getLogger().warn("���أ�����δ����Ĵ���");
			Errorx.printString();
		}

		if (ExtendManager.hasAction(AfterMainFilterAction.Type)) {
			ExtendManager.executeAll(AfterMainFilterAction.Type, new Object[] { request, response, chain });
		}
		try {
			chain.doFilter(request, response);
		} finally {
			BlockingTransaction.clearTransactionBinding();// ����Ƿ���δ���رյ���������������
		}
		try {
			session.setAttribute(Constant.UserAttrName, User.getCurrent());// �����ڴ����ã����User������ܱ�������
		} catch (Exception e) {
			// �п����Ѿ���invalidate��
		}
		if (Current.getPage() != null) {
			Current.getPage().getCookies().writeToResponse(request, response);
			// ����д��header
			ResponseImpl r = Current.getPage().getResponse();
			Mapx map = r.getHeaders();
			Object[] ks = map.keyArray();
			for (int i = 0; i < map.size(); i++) {
				Object k = ks[i];
				response.setHeader(k.toString(), map.getString(k));
			}
		}
	}

	public void destroy() {
	}
}
