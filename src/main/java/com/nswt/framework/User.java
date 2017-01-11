package com.nswt.framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.nswt.framework.clustering.Clustering;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.Mapx;

/**
 * �û�����ȫ�ַ����࣬һ���߳��ڵ����д��붼����ֱ�ӷ����û�����<br>
 * ���������HttpSession�����ã������ڵ�Ԫ���ԡ�
 * 
 * @Author NSWT
 * @Date 2007-6-18
 * @Mail nswt@nswt.com.cn
 */
public class User {
	/**
	 * �û�״̬������
	 */
	public static final String ONLINE = "online";

	/**
	 * �û�״̬������
	 */
	public static final String OFFLINE = "offline";

	/**
	 * �û�״̬��æµ
	 */
	public static final String BUSY = "busy";

	/**
	 * �û�״̬���뿪
	 */
	public static final String AWAY = "away";

	/**
	 * �û�״̬������
	 */
	public static final String HIDDEN = "hidden";

	public static final Mapx USER_STATUS_MAP = new Mapx();

	static {
		USER_STATUS_MAP.put(ONLINE, "����");
		USER_STATUS_MAP.put(OFFLINE, "����");
		USER_STATUS_MAP.put(BUSY, "æµ");
		USER_STATUS_MAP.put(AWAY, "�뿪");
		USER_STATUS_MAP.put(HIDDEN, "����");
	}

	private static ThreadLocal UserLocal = new ThreadLocal();

	/**
	 * ��ȡ��ǰ�û���
	 * 
	 * @return
	 */
	public static String getUserName() {
		return getCurrent().UserName;
	}

	/**
	 * ���õ�ǰ�û���
	 * 
	 * @param UserName
	 */
	public static void setUserName(String UserName) {
		getCurrent().setUserName(UserName);
	}

	/**
	 * ��ȡ��ǰ�û�����ʵ����
	 * 
	 * @return
	 */
	public static String getRealName() {
		return getCurrent().RealName;
	}

	/**
	 * ���õ�ǰ�û�����ʵ����
	 * 
	 * @param RealName
	 */
	public static void setRealName(String RealName) {
		getCurrent().RealName = RealName;
		if (Config.isDebugMode()) {
			cacheUser(getCurrent());
		}
	}

	/**
	 * ��ȡ��ǰ�û��ķ�֧�����ڲ�����
	 * 
	 * @return
	 */
	public static String getBranchInnerCode() {
		return getCurrent().BranchInnerCode;
	}

	/**
	 * ���õ�ǰ�û��ķ�֧�����ڲ�����
	 * 
	 * @param BranchInnerCode
	 */
	public static void setBranchInnerCode(String BranchInnerCode) {
		getCurrent().BranchInnerCode = BranchInnerCode;
		if (Config.isDebugMode()) {
			cacheUser(getCurrent());
		}
	}

	/**
	 * ��ȡ��ǰ�û����û�����
	 * 
	 * @return
	 */
	public static String getType() {
		return getCurrent().Type;
	}

	/**
	 * ���õ�ǰ�û����û�����
	 * 
	 * @param Type
	 */
	public static void setType(String Type) {
		getCurrent().Type = Type;
		if (Config.isDebugMode()) {
			cacheUser(getCurrent());
		}
	}

	/**
	 * ��ȡ��ǰ�û����û�״̬
	 * 
	 * @return
	 */
	public static String getStatus() {
		return getCurrent().Status;
	}

	/**
	 * ���õ�ǰ�û����û�״̬
	 * 
	 * @param Status
	 */
	public static void setStatus(String Status) {
		getCurrent().Status = Status;
		if (Config.isDebugMode()) {
			cacheUser(getCurrent());
		}
	}

	/**
	 * ��ȡUser�����е���ֵ�Ը���
	 * 
	 * @return
	 */
	public static int getValueCount() {
		return getCurrent().Map.size();
	}

	/**
	 * ��key��ȡָ��������
	 * 
	 * @param key
	 * @return
	 */
	public static Object getValue(Object key) {
		return getCurrent().Map.get(key);
	}

	/**
	 * ��ȡ��ǰ�û�����ֵ������
	 * 
	 * @return
	 */
	public static Mapx getValues() {
		return getCurrent().Map;
	}

	/**
	 * ���õ�ǰ�û�ָ��������
	 * 
	 * @param key
	 * @param value
	 */
	public static void setValue(Object key, Object value) {
		Mapx map = getCurrent().Map;
		synchronized (map) {
			map.put(key, value);
		}
		if (Config.isDebugMode()) {
			cacheUser(getCurrent());
		}
	}

	/**
	 * ��ǰ�û��Ƿ񼺵�¼
	 * 
	 * @return
	 */
	public static boolean isLogin() {
		return getCurrent().isLogin;
	}

	/**
	 * ���õ�ǰ�û��ĵ�¼״̬
	 * 
	 * @param isLogin
	 */
	public static void setLogin(boolean isLogin) {
		if (isLogin) {
			if (!getCurrent().isLogin) {// ����ģʽ��δ��¼֮ǰSession�п��ܾ��Ѿ���User��ʵ����
				Config.LoginUserCount++;
			}

		} else {
			if (getCurrent().isLogin) {
				if (Config.LoginUserCount > 0) {
					Config.LoginUserCount--;
				}
			}
		}
		getCurrent().isLogin = isLogin;
		if (Config.isDebugMode()) {
			cacheUser(getCurrent());
		}
	}

	/**
	 * ���õ�ǰ�û�����
	 * 
	 * @param user
	 */
	public static void setCurrent(UserData user) {
		UserLocal.set(user);
		if (Config.isDebugMode()) {
			cacheUser(user);
		}
	}

	/**
	 * ��ȡ��ǰ�û�����
	 * 
	 * @return
	 */
	public static UserData getCurrent() {
		return (UserData) UserLocal.get();
	}

	/**
	 * ���浱ǰ�û������ļ�ϵͳ
	 * 
	 * @param u
	 */
	protected static void cacheUser(UserData u) {
		if (getCurrent() != u || getCurrent() == null) {
			return;// ��ǰUserData����δ����ΪCurrent
		}
		if (Clustering.isClustering()) {// ���Ȼ��浽��Ⱥ��
			Clustering.cacheUser(u);
		}
		if (Config.isDebugMode()) {
			try {
				File dir = new File(Config.getContextRealPath() + "WEB-INF/cache/");
				if (!dir.exists()) {
					dir.mkdirs();
				}
				if (u.getSessionID() == null) {
					return;
				}
				FileOutputStream f = new FileOutputStream(Config.getContextRealPath() + "WEB-INF/cache/"
						+ u.getSessionID());
				ObjectOutputStream s = new ObjectOutputStream(f);
				s.writeObject(u);
				s.flush();
				s.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * ���ݻỰID���ļ�ϵͳ�з��ؼ�������û��������û���򷵻�null
	 * 
	 * @param sessionID
	 * @return
	 */
	protected static UserData getCachedUser(String sessionID) {
		try {
			File in = new File(Config.getContextRealPath() + "WEB-INF/cache/" + sessionID);
			if (in.exists()) {
				ObjectInputStream s = new ObjectInputStream(new FileInputStream(in));
				Object o = s.readObject();
				if (UserData.class.isInstance(o)) {
					s.close();
					in.delete();
					UserData u = (UserData) o;
					if (u.isLogin()) {
						Config.LoginUserCount++;
					}
					return u;
				}
				s.close();
			}
		} catch (Exception e) {
			LogUtil.warn("δȡ�������User����");
		}
		return null;
	}

	/**
	 * ���ٵ�ǰ�û�����
	 */
	public static void destory() {
		User.setCurrent(new UserData());
	}

	/**
	 * ���ص�ǰ�û��ĻỰID
	 * 
	 * @return
	 */
	public static String getSessionID() {
		return getCurrent().SessionID;
	}

	/**
	 * ���õ�ǰ�û��ĻỰID
	 * 
	 * @param sessionID
	 */
	protected static void setSessionID(String sessionID) {
		getCurrent().SessionID = sessionID;
		if (Config.isDebugMode()) {
			cacheUser(getCurrent());
		}
	}

	/**
	 * ��ǰ�û��Ƿ��ǻ�Ա�û������ǻ�Ա����ʹ�ú�̨��
	 */
	public static boolean isMember() {
		return getCurrent().isMember;
	}

	/**
	 * ���õ�ǰ�û��Ƿ��ǻ�Ա
	 * 
	 * @param isMember
	 */
	public static void setMember(boolean isMember) {
		getCurrent().isMember = isMember;
		if (Config.isDebugMode()) {
			cacheUser(getCurrent());
		}
	}

	/**
	 * ��ǰ�û��Ƿ��Ǻ�̨������Ա������ʹ�ú�̨��
	 * 
	 * @return
	 */
	public static boolean isManager() {
		return getCurrent().isManager;
	}

	/**
	 * ���õ�ǰ�û��Ƿ��Ǻ�̨������Ա
	 * 
	 * @param isManager
	 */
	public static void setManager(boolean isManager) {
		getCurrent().isManager = isManager;
		if (Config.isDebugMode()) {
			cacheUser(getCurrent());
		}
	}

	/**
	 * �����û����� ���� : 2010-4-22 <br>
	 * ����: NSWT <br>
	 * ���䣺nswt@nswt.com.cn <br>
	 */
	public static class UserData implements Serializable {
		private static final long serialVersionUID = 1L;

		/**
		 * �û�����
		 */
		private String Type;

		/**
		 * �û�״̬
		 */
		private String Status;

		/**
		 * �û���
		 */
		private String UserName;

		/**
		 * �û���ʵ����
		 */
		private String RealName;

		/**
		 * ������֧����
		 */
		private String BranchInnerCode;

		/**
		 * �Ƿ񼺵�¼
		 */
		private boolean isLogin = false;

		/**
		 * �Ƿ��ǻ�Ա
		 */
		private boolean isMember = true;// Ĭ���ǻ�Ա

		/**
		 * �Ƿ��ǹ�����Ա������Ȩ��¼��̨����Ա�����Ե��ü̳�Page����
		 */
		private boolean isManager = false;

		/**
		 * �ỰID
		 */
		private String SessionID;

		/**
		 * ������ֵ����ʽ���û�����
		 */
		private Mapx Map = new Mapx();

		public String getType() {
			return Type;
		}

		public void setType(String type) {
			Type = type;
			cacheUser(getCurrent());
		}

		public String getStatus() {
			return Status;
		}

		public void setStatus(String status) {
			Status = status;
			cacheUser(getCurrent());
		}

		public String getUserName() {
			return UserName;
		}

		public void setUserName(String userName) {
			UserName = userName;
			cacheUser(getCurrent());
		}

		public String getRealName() {
			return RealName;
		}

		public void setRealName(String realName) {
			RealName = realName;
			cacheUser(getCurrent());
		}

		public String getBranchInnerCode() {
			return BranchInnerCode;
		}

		public void setBranchInnerCode(String branchInnerCode) {
			BranchInnerCode = branchInnerCode;
			cacheUser(getCurrent());
		}

		public boolean isLogin() {
			return isLogin;
		}

		public void setLogin(boolean isLogin) {
			this.isLogin = isLogin;
			cacheUser(getCurrent());
		}

		public boolean isMember() {
			return isMember;
		}

		public void setMember(boolean isMember) {
			this.isMember = isMember;
			cacheUser(getCurrent());
		}

		public boolean isManager() {
			return isManager;
		}

		public void setManager(boolean isManager) {
			this.isManager = isManager;
			cacheUser(getCurrent());
		}

		public String getSessionID() {
			return SessionID;
		}

		public void setSessionID(String sessionID) {
			SessionID = sessionID;
			cacheUser(getCurrent());
		}

		public Mapx getMap() {
			return Map;
		}

		public void setMap(Mapx map) {
			Map = map;
			cacheUser(getCurrent());
		}
	}
}
