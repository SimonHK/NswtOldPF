package com.nswt.framework;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.nswt.framework.User.UserData;
import com.nswt.framework.extend.AfterSessionCreateAction;
import com.nswt.framework.extend.BeforeSessionDestroyAction;
import com.nswt.framework.extend.ExtendManager;
import com.nswt.framework.utility.FileUtil;
import com.nswt.framework.utility.Mapx;

/**
 * Session�������������û���������������ṩ�û���ص����ݡ�
 * 
 * @Author NSWT
 * @Date 2007-7-16
 * @Mail nswt@nswt.com.cn
 */
public class SessionListener implements HttpSessionListener {
	private static Mapx map = new Mapx();
	private static Object mutex = new Object();

	/**
	 * �Ự����ʱִ�б�����
	 * 
	 * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionCreated(HttpSessionEvent arg0) {
		Config.OnlineUserCount++;
		synchronized (mutex) {
			HttpSession hs = arg0.getSession();
			map.put(hs.getId(), hs);
			if (ExtendManager.hasAction(AfterSessionCreateAction.Type)) {
				ExtendManager.executeAll(AfterSessionCreateAction.Type, new Object[] { hs });
			}
		}
	}

	/**
	 * �ỰʧЧʱִ�б�����
	 * 
	 * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionDestroyed(HttpSessionEvent arg0) {
		Config.OnlineUserCount--;
		UserData u = (UserData) arg0.getSession().getAttribute(Constant.UserAttrName);
		if (u != null) {
			if (u.isLogin()) {
				if (Config.LoginUserCount > 0) {
					Config.LoginUserCount--;
				}
			}
			if (Config.isDebugMode()) {
				FileUtil.delete(Config.getContextRealPath() + "WEB-INF/cache/" + u.getSessionID());
			}
		}
		if (ExtendManager.hasAction(BeforeSessionDestroyAction.Type)) {
			ExtendManager.executeAll(BeforeSessionDestroyAction.Type, new Object[] { arg0.getSession() });
		}
		synchronized (mutex) {
			map.remove(arg0.getSession().getId());
		}
	}

	/**
	 * ������еĻỰ�������
	 */
	public static void clear() {
		synchronized (mutex) {
			map.clear();
		}
	}

	/**
	 * �߳����Լ���������������û�
	 */
	public static void forceExit() {
		synchronized (mutex) {
			Object[] ks = map.keyArray();
			Object[] vs = map.valueArray();
			HttpSession session = null;
			for (int i = 0; i < map.size(); i++) {
				if (ks[i].equals(User.getSessionID())) {
					continue;
				}
				session = (HttpSession) vs[i];
				session.invalidate();
			}
		}
	}

	/**
	 * �߳�ָ��SessionID���û�
	 * 
	 * @param sid
	 */
	public static void forceExit(String sid) {
		synchronized (mutex) {
			HttpSession session = (HttpSession) map.get(sid);
			session.invalidate();
		}
	}

	/**
	 * ��ȡ����״̬���û�
	 * 
	 * @return
	 */
	public static UserData[] getUsers() {
		Object[] vs = map.keyArray();
		UserData[] arr = new UserData[vs.length];
		HttpSession session = null;
		for (int i = 0; i < vs.length; i++) {
			session = (HttpSession) map.get(vs[i]);
			UserData u = (UserData) session.getAttribute(Constant.UserAttrName);
			arr[i] = u;
		}
		return arr;
	}

	/**
	 * ��ȡָ��״̬���û�
	 * 
	 * @param status
	 *            �û�״̬
	 * @return
	 */
	public static UserData[] getUsers(String status) {
		Object[] vs = map.keyArray();
		ArrayList arr = new ArrayList(vs.length);
		HttpSession session = null;
		for (int i = 0; i < vs.length; i++) {
			session = (HttpSession) map.get(vs[i]);
			UserData u = (UserData) session.getAttribute(Constant.UserAttrName);
			if (u != null) {
				if (status.equalsIgnoreCase(u.getStatus())) {
					arr.add(u);
				}
			}
		}
		return (UserData[]) arr.toArray(new UserData[arr.size()]);
	}

	/**
	 * ��ȡָ��״̬���û����б�
	 * 
	 * @param status
	 *            �û�״̬
	 * @return
	 */
	public static List getUserNames(String status) {
		UserData[] arr = getUsers(status);
		List userNameArr = new ArrayList(arr.length);
		for (int i = 0; i < arr.length; i++) {
			userNameArr.add(arr[i].getUserName());
		}
		return userNameArr;
	}

	/**
	 * ��ȡָ���û�����User����
	 * 
	 * @param userName
	 *            �û���
	 * @return
	 */
	public static UserData getUser(String userName) {
		UserData[] users = getUsers();
		for (int i = 0; i < users.length; i++) {
			if (userName.equals(users[i].getUserName())) {
				return users[i];
			}
		}
		return null;
	}

}
