package com.nswt.framework;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nswt.framework.data.DataCollection;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.ServletUtil;
import com.nswt.framework.utility.StringUtil;

/**
 * 提供全局访问当前请求中的相关对象的方法
 * 
 * @author NSWT
 * @date 2016-9-30
 * @email nswt@nswt.com.cn
 */
public class Current {
	private static ThreadLocal current = new ThreadLocal();

	/**
	 * ThreadLocal中的Mapx中对应于Page对象的键名
	 */
	private static final String PageKey = "_NSWT_PAGE_KEY";

	protected static void clear() {
		if (current.get() != null) {
			current.set(null);
		}
	}

	/**
	 * 设置线程上下文有效的变量
	 */
	public static void setVariable(String key, Object value) {
		Mapx map = (Mapx) current.get();
		if (map == null) {
			map = new Mapx();
			current.set(map);
		}
		if (value instanceof Mapx) {
			Mapx vmap = (Mapx) value;
			Object[] ks = vmap.keyArray();
			Object[] vs = vmap.valueArray();
			for (int i = 0; i < ks.length; i++) {
				map.put(key + "." + ks[i], vs[i]);
			}
		}
		map.put(key, value);
	}

	/**
	 * 获得线程上下文有效的变量
	 */
	public static Object getVariable(String key) {
		Mapx map = (Mapx) current.get();
		if (map == null) {
			return null;
		}
		return map.get(key);
	}

	/**
	 * 获得线程上下文有效的变量，返回int变量
	 */
	public static int getInt(String key) {
		Mapx map = (Mapx) current.get();
		if (map == null) {
			return 0;
		}
		return map.getInt(key);
	}

	/**
	 * 获得线程上下文有效的变量，返回long变量
	 */
	public static long getLong(String key) {
		Mapx map = (Mapx) current.get();
		if (map == null) {
			return 0;
		}
		return map.getLong(key);
	}

	/**
	 * 获得线程上下文有效的变量，返回String变量
	 */
	public static String getString(String key) {
		Mapx map = (Mapx) current.get();
		if (map == null) {
			return null;
		}
		return map.getString(key);
	}

	/**
	 * 根据Servlet环境安装始化当前的Current对象
	 * 
	 * @param request
	 * @param response
	 * @param method
	 */
	public static void init(HttpServletRequest request, HttpServletResponse response, String method) {
		if (StringUtil.isEmpty(method)) {
			return;
		}
		try {
			int index = method.lastIndexOf('.');
			String className = method.substring(0, index);

			Class c = Class.forName(className);
			Object o = c.newInstance();
			Page page = (Page) o;

			String data = request.getParameter(Constant.Data);
			RequestImpl dc = new RequestImpl();
			if (StringUtil.isNotEmpty(data)) {
				data = StringUtil.htmlDecode(data);
				dc.setURL(request.getParameter(Constant.URL));
				dc.putAll(ServletUtil.getParameterMap(request));
				dc.remove(Constant.Data);
				dc.remove(Constant.URL);
				dc.remove(Constant.Method);
				dc.parseXML(data);
			} else {
				dc.setURL(request.getRequestURL() + "?" + request.getQueryString());
				dc.putAll(ServletUtil.getParameterMap(request));
			}
			dc.setClientIP(ServletUtil.getRealIP(request));
			dc.setClassName(className);
			dc.setServerName(request.getServerName());
			dc.setPort(request.getServerPort());
			dc.setScheme(request.getScheme());

			CookieImpl cookie = new CookieImpl(request);
			page.setCookies(cookie);

			// header部分
			Enumeration e = request.getHeaderNames();
			while (e.hasMoreElements()) {
				String k = e.nextElement().toString();
				dc.getHeaders().put(k, request.getHeader(k));
			}

			page.setRequest(dc);
			Mapx map = (Mapx) current.get();
			if (map == null) {
				map = new Mapx();
				current.set(map);
			}
			map.put(PageKey, page);
		} catch (Exception e) {
			DataCollection dcResponse = new DataCollection();
			dcResponse.put(Constant.ResponseStatusAttrName, 0);
			String msg = "系统发生内部错误，操作失败:" + method;
			LogUtil.getLogger().warn(msg);
			e.printStackTrace();
			dcResponse.put(Constant.ResponseMessageAttrName, msg);
			try {
				response.getWriter().write(dcResponse.toXML());
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}
	}

	/**
	 * 以当前请求中的对象为上下文调用指定方法
	 * 
	 * @param method
	 * @param args
	 * @return
	 */
	public static Object invokeMethod(String method, Object[] args) {
		try {
			int index = method.lastIndexOf('.');
			String className = method.substring(0, index);
			method = method.substring(index + 1);

			Page p = getPage();
			if (!p.getClass().getName().equals(className)) {// 同一个页面下方法不在同一个类里
				Class c = Class.forName(className);
				Page p2 = (Page) c.newInstance();
				p2.Request = p.Request;
				p2.Response = p.Response;
				p2.Cookies = p.Cookies;
				p = p2;
			}

			if (!SessionCheck.check(p.getClass())) {
				throw new RuntimeException("非法访问，前台用户不能访问Page类!" + className + "#" + method);
			}

			Method[] ms = p.getClass().getMethods();
			Method m = null;
			boolean flag = false;
			for (int i = 0; i < ms.length; i++) {
				m = ms[i];
				if (m.getName().equals(method)) {
					Class[] cs = m.getParameterTypes();
					if (args != null) {
						if (args.length == cs.length) {
							for (int j = 0; j < cs.length; j++) {
								if (!cs[j].isInstance(args[j])) {
									break;
								}
							}
							flag = true;
							break;
						}
					}
					if (args == null && (cs == null || cs.length == 0)) {
						flag = true;
						break;
					}
				}
			}
			if (!flag) {
				throw new RuntimeException("没有找到合适的方法，请检查参数是否正确!" + className + "#" + method);
			}

			if (!Modifier.isStatic(m.getModifiers())) {// 非静态方法，需要初始化Page对象
				return m.invoke(p, args);
			} else {
				return m.invoke(null, args);
			}
		} catch (Throwable e) {// 必须捕获，不然可能会导致DataGird在前台消失
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取当前请求中的Page对象
	 * 
	 * @return
	 */
	public static Page getPage() {
		Mapx map = (Mapx) current.get();
		if (map == null) {
			return null;
		}
		return (Page) map.get(PageKey);
	}

	/**
	 * 获取当前请求中的RequestImpl对象
	 * 
	 * @return
	 */
	public static RequestImpl getRequest() {
		Page p = getPage();
		if (p == null) {
			return null;
		}
		return p.Request;
	}

	/**
	 * 获取当前请求中的ResponseImpl对象
	 * 
	 * @return
	 */
	public static ResponseImpl getResponse() {
		Page p = getPage();
		if (p == null) {
			return null;
		}
		return p.Response;
	}

	/**
	 * 获取当前请求中的CookieImpl对象
	 * 
	 * @return
	 */
	public static CookieImpl getCookies() {
		Page p = getPage();
		if (p == null) {
			return null;
		}
		return p.getCookies();
	}

	/**
	 * 获取当前请求中的单个Cookie值
	 * 
	 * @return
	 */
	public static String getCookie(String name) {
		Page p = getPage();
		if (p == null) {
			return null;
		}
		return p.getCookies().getCookie(name);
	}
}
