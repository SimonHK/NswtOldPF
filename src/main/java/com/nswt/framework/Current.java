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
 * �ṩȫ�ַ��ʵ�ǰ�����е���ض���ķ���
 * 
 * @author NSWT
 * @date 2016-9-30
 * @email nswt@nswt.com.cn
 */
public class Current {
	private static ThreadLocal current = new ThreadLocal();

	/**
	 * ThreadLocal�е�Mapx�ж�Ӧ��Page����ļ���
	 */
	private static final String PageKey = "_NSWT_PAGE_KEY";

	protected static void clear() {
		if (current.get() != null) {
			current.set(null);
		}
	}

	/**
	 * �����߳���������Ч�ı���
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
	 * ����߳���������Ч�ı���
	 */
	public static Object getVariable(String key) {
		Mapx map = (Mapx) current.get();
		if (map == null) {
			return null;
		}
		return map.get(key);
	}

	/**
	 * ����߳���������Ч�ı���������int����
	 */
	public static int getInt(String key) {
		Mapx map = (Mapx) current.get();
		if (map == null) {
			return 0;
		}
		return map.getInt(key);
	}

	/**
	 * ����߳���������Ч�ı���������long����
	 */
	public static long getLong(String key) {
		Mapx map = (Mapx) current.get();
		if (map == null) {
			return 0;
		}
		return map.getLong(key);
	}

	/**
	 * ����߳���������Ч�ı���������String����
	 */
	public static String getString(String key) {
		Mapx map = (Mapx) current.get();
		if (map == null) {
			return null;
		}
		return map.getString(key);
	}

	/**
	 * ����Servlet������װʼ����ǰ��Current����
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

			// header����
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
			String msg = "ϵͳ�����ڲ����󣬲���ʧ��:" + method;
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
	 * �Ե�ǰ�����еĶ���Ϊ�����ĵ���ָ������
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
			if (!p.getClass().getName().equals(className)) {// ͬһ��ҳ���·�������ͬһ������
				Class c = Class.forName(className);
				Page p2 = (Page) c.newInstance();
				p2.Request = p.Request;
				p2.Response = p.Response;
				p2.Cookies = p.Cookies;
				p = p2;
			}

			if (!SessionCheck.check(p.getClass())) {
				throw new RuntimeException("�Ƿ����ʣ�ǰ̨�û����ܷ���Page��!" + className + "#" + method);
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
				throw new RuntimeException("û���ҵ����ʵķ�������������Ƿ���ȷ!" + className + "#" + method);
			}

			if (!Modifier.isStatic(m.getModifiers())) {// �Ǿ�̬��������Ҫ��ʼ��Page����
				return m.invoke(p, args);
			} else {
				return m.invoke(null, args);
			}
		} catch (Throwable e) {// ���벶�񣬲�Ȼ���ܻᵼ��DataGird��ǰ̨��ʧ
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ȡ��ǰ�����е�Page����
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
	 * ��ȡ��ǰ�����е�RequestImpl����
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
	 * ��ȡ��ǰ�����е�ResponseImpl����
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
	 * ��ȡ��ǰ�����е�CookieImpl����
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
	 * ��ȡ��ǰ�����еĵ���Cookieֵ
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
