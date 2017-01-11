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
 * 所有JSP页面访问都由此Filter预处理，执行页面的字符集串设置、会话检查、线程全局对象初始化等动作
 */
public class MainFilter implements Filter {
	private long uptime = 0;

	private String[] NoFilterPaths;

	private boolean InitFlag = true;

	public void init(FilterConfig config) throws ServletException {
		LogUtil.info("----" + Config.getAppCode() + "(" + Config.getAppName() + "): MainFilter Initialized----");
		ServletContext sc = config.getServletContext();
		Config.configMap.put("System.ContainerInfo", sc.getServerInfo());// 连接池需要这个属性，所以先置
		Config.getJBossInfo();// 注意：Jboss下这个值是ApacheTomcat/5.x之类的，且MailFilter会后于Config执行
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

		Current.clear();// 需要保证每次页面生命周期同一个Page使用的是同一个实例

		// 初始化用户数据、错误处理容器
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

		// 初始化路径，需要考虑集群的情况，需要考虑内外网路径不一致的情况
		// 注意如果遇到以下情况的时候cookie设值得到的path会有所不同：本地访问路径是http://IP/nswtp，而外网访问路径是:http://域名
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

		if (url != null && url.indexOf("/MainServlet.jsp") > 0 && !url.equals("/MainServlet.jsp")) {// 页面初始化时会有这种情况
			RequestDispatcher rd = request.getRequestDispatcher("/MainServlet.jsp");
			rd.forward(req, rep);
			return;
		} else {
			SessionCheck.check(request, response);// 用户权限检查
		}
		if (!Errorx.hasDealed()) {
			LogUtil.getLogger().warn("严重，发现未处理的错误！");
			Errorx.printString();
		}

		if (ExtendManager.hasAction(AfterMainFilterAction.Type)) {
			ExtendManager.executeAll(AfterMainFilterAction.Type, new Object[] { request, response, chain });
		}
		try {
			chain.doFilter(request, response);
		} finally {
			BlockingTransaction.clearTransactionBinding();// 检测是否有未被关闭的阻塞型事务连接
		}
		try {
			session.setAttribute(Constant.UserAttrName, User.getCurrent());// 必须在此重置，因此User对象可能被重置了
		} catch (Exception e) {
			// 有可能已经被invalidate了
		}
		if (Current.getPage() != null) {
			Current.getPage().getCookies().writeToResponse(request, response);
			// 以下写入header
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
