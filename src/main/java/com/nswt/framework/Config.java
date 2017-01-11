/**
 * ����: NSWT<br>
 * ���ڣ�2016-6-28<br>
 * �ʼ���nswt@nswt.com.cn<br>
 */
package com.nswt.framework;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;

import com.nswt.framework.ConfigLoader.NodeData;
import com.nswt.framework.data.DBConnConfig;
import com.nswt.framework.data.DBConnPool;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.extend.ExtendManager;
import com.nswt.framework.license.IProduct;
import com.nswt.framework.security.EncryptUtil;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;

public class Config {
	protected static Mapx configMap = new Mapx();// ������������Map

	public static int OnlineUserCount = 0;// ��ǰ����Session��

	public static int LoginUserCount = 0;// ��ǰ����¼����

	public static boolean isInstalled = false;// ���ݿ��Ƿ�����

	public static boolean isAllowLogin = true;// �Ƿ���ʱ��ֹ��¼

	private static String AppCode = null;// Ӧ�ô���

	private static String AppName = null;// Ӧ������

	private static float MainVersion = 1.0f;// Ӧ�����汾��

	private static float MinorVersion = 0;// Ӧ�ôΰ汾��

	public static boolean ComplexDepolyMode = false;// �Ƿ��Ǹ��Ӳ���ģʽ�����Ӳ���ģʽ��Ҫ���ǵ�һ��Ӧ���ж��·�������⣬����������

	public static int ServletMajorVersion;

	public static int ServletMinorVersion;

	private static long LastUpdateTime = 0;// ���һ�ζ�ȡ�����ļ���ʱ��

	private static long RefreshPeriod = 60000;// ���೤ʱ���ȡһ������

	private static Object mutex = new Object();

	/**
	 * ��ʼ��������
	 */
	protected static void init() {
		if (System.currentTimeMillis() - LastUpdateTime > RefreshPeriod) {
			if (!configMap.containsKey("System.JavaVersion")) {
				ConfigLoader.load();
				configMap.put("App.ContextRealPath", Config.getContextRealPath());
				configMap.put("System.JavaVersion", System.getProperty("java.version"));
				configMap.put("System.JavaVendor", System.getProperty("java.vendor"));
				configMap.put("System.JavaHome", System.getProperty("java.home"));
				configMap.put("System.OSPatchLevel", System.getProperty("sun.os.patch.level"));// ����JDK�Ժ󲹳�
				configMap.put("System.OSArch", System.getProperty("os.arch"));
				configMap.put("System.OSVersion", System.getProperty("os.version"));
				configMap.put("System.OSName", System.getProperty("os.name"));
				configMap.put("System.OSUserLanguage", System.getProperty("user.language"));
				configMap.put("System.OSUserName", System.getProperty("user.name"));
				configMap.put("System.LineSeparator", System.getProperty("line.separator"));
				configMap.put("System.FileSeparator", System.getProperty("file.separator"));
				configMap.put("System.FileEncoding", System.getProperty("file.encoding"));

				NodeData[] datas = ConfigLoader.getNodeDataList("framework.application.config");
				if (datas == null) {
					LogUtil.warn("�����ļ�framework.xmlδ�ҵ�!");
					isInstalled = false;
					return;
				}
				for (int i = 0; datas != null && i < datas.length; i++) {
					configMap.put("App." + datas[i].Attributes.getString("name"), datas[i].Body);
				}
				if (configMap.containsKey("App.Code")) {
					AppCode = configMap.getString("App.Code");
					AppName = configMap.getString("App.Name");
				}
				datas = ConfigLoader.getNodeDataList("nswtp.allowUploadExt.config");
				for (int i = 0; datas != null && i < datas.length; i++) {
					configMap.put(datas[i].Attributes.getString("name"), datas[i].Body);
				}
				datas = ConfigLoader.getNodeDataList("data.config");
				for (int i = 0; datas != null && i < datas.length; i++) {
					configMap.put(datas[i].Attributes.getString("name"), datas[i].Attributes.getString("value"));
				}
				datas = ConfigLoader.getNodeDataList("framework.databases.database");
				for (int i = 0; datas != null && i < datas.length; i++) {
					String dbname = datas[i].Attributes.getString("name");
					NodeData[] children = datas[i].getChildrenDataList();
					for (int k = 0; k < children.length; k++) {
						String attr = children[k].Attributes.getString("name");
						String value = children[k].getBody();
						if (attr.equalsIgnoreCase("Password")) {
							if (value.startsWith("$KEY")) {
								value = EncryptUtil.decrypt3DES(value.substring(4), EncryptUtil.DEFAULT_KEY);
							}
						}
						configMap.put("Database." + dbname + "." + attr, value);
					}
				}
				if (datas != null) {
					isInstalled = true;
				} else {
					isInstalled = false;
				}
				loadDBConfig();
				ExtendManager.executeAll("Config.AfterInit", null);
			} else {
				loadDBConfig();
			}
		}
	}

	public static void loadConfig() {
		configMap.remove("System.JavaVersion");
		ConfigLoader.reload();
		LastUpdateTime = 0;
		init();
	}

	/**
	 * �������ݿ������е�������
	 */
	public static void loadDBConfig() {
		synchronized (mutex) {
			LastUpdateTime = System.currentTimeMillis();
			if (configMap.containsKey("Database.Default.Type")) {
				if ("true".equals(configMap.get("App.ExistPlatformDB"))) {
					try {
						DataTable dt = new QueryBuilder("select type,value from zdconfig").executeDataTable();
						for (int i = 0; dt != null && i < dt.getRowCount(); i++) {
							configMap.put(dt.getString(i, 0), dt.getString(i, 1));
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * �ṩ���ⲿ���ø��� �ڹ����̨�޸ĺ�ֱ�ӵ��ø���
	 */
	public static void refresh() {
		if (System.currentTimeMillis() - LastUpdateTime > RefreshPeriod) {
			loadDBConfig();
		}
	}

	/**
	 * @deprecated
	 */
	public static void update() {
		refresh();
	}

	public static Mapx getMapx() {
		return configMap;
	}

	/**
	 * �����������ֵ
	 * 
	 * @param configName
	 * @return
	 */
	public static String getValue(String configName) {
		init();
		return (String) configMap.get(configName);
	}

	/**
	 * �����������ֵ
	 * 
	 * @param configName
	 * @param configValue
	 */
	public static void setValue(String configName, String configValue) {
		init();
		configMap.put(configName, configValue);
	}

	/**
	 * ����class����Ŀ¼��ʵ��·��
	 */
	public static String getClassesPath() {
		URL url = Thread.currentThread().getContextClassLoader().getResource("/");
		if (url == null) {
			throw new RuntimeException("δ�ҵ�/Ŀ¼");
		}
		try {
			String path = URLDecoder.decode(url.getPath(), Config.getFileEncode());
			if (System.getProperty("os.name").toLowerCase().indexOf("windows") >= 0) {
				if (path.startsWith("/")) {
					path = path.substring(1);
				} else if (path.startsWith("file:/")) {
					path = path.substring(6);
				}
			} else {
				if (path.startsWith("file:/")) {
					path = path.substring(5);
				}
			}
			return path.substring(0, path.lastIndexOf("/") + 1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * WEBӦ���·���Ӧ�õ�ʵ��·��
	 */
	public static String getContextRealPath() {
		if (configMap != null) {
			String str = (String) configMap.get("App.ContextRealPath");
			if (str != null) {
				return str;
			}
		}
		String path = getClassesPath();
		int index = path.indexOf("WEB-INF");
		if (index > 0) {
			path = path.substring(0, index);
		}
		return path;
	}

	/**
	 * ���ǵ�ͬһ��Ӧ�����������в�ͬ��·����������ô�������ÿһ�ν���Filter�󶼻���������
	 */
	public static String getContextPath() {
		if (ComplexDepolyMode) {
			String path = (String) User.getValue("App.ContextPath");
			if (StringUtil.isEmpty(path)) {
				path = Config.getValue("App.ContextPath");
			}
			return path;
		} else {
			return Config.getValue("App.ContextPath");
		}
	}

	/**
	 * ��ȡ��־����
	 * 
	 * @return
	 */
	public static String getLogLevel() {
		return Config.getValue("App.LogLevel");
	}

	private static void initProduct() {
		if (AppCode == null) {
			try {
				IProduct p = (IProduct) Class.forName("com.nswt.Product").newInstance();
				AppCode = p.getAppCode();
				AppName = p.getAppName();
				MainVersion = p.getMainVersion();
				MinorVersion = p.getMinorVersion();

				if (configMap.get("App.Code") != null) {
					AppCode = configMap.getString("App.Code");
					AppName = configMap.getString("App.Name");
				}
			} catch (Exception e) {
				AppCode = "ZPlatform";
				AppName = "���ſ���ƽ̨";
			}
		}
	}

	/**
	 * ��ȡӦ�ô���
	 * 
	 * @return
	 */
	public static String getAppCode() {
		initProduct();
		return AppCode;
	}

	/**
	 * ��ȡӦ������
	 * 
	 * @return
	 */
	public static String getAppName() {
		initProduct();
		return AppName;
	}

	/**
	 * ��ȡӦ�����汾��
	 * 
	 * @return
	 */
	public static float getMainVersion() {
		initProduct();
		return MainVersion;
	}

	/**
	 * ��ȡӦ�ôΰ汾��
	 * 
	 * @return
	 */
	public static float getMinorVersion() {
		initProduct();
		return MinorVersion;
	}

	/**
	 * �Ƿ��ǵ���ģʽ������ģʽ�����м�Session
	 * 
	 * @return
	 */
	public static boolean isDebugMode() {
		return "true".equalsIgnoreCase(Config.getValue("App.DebugMode"));
	}

	/**
	 * ��ȡJVM�İ汾��
	 * 
	 * @return
	 */
	public static String getJavaVersion() {
		return Config.getValue("System.JavaVersion");
	}

	/**
	 * ��ȡJVM����
	 * 
	 * @return
	 */
	public static String getJavaVendor() {
		return Config.getValue("System.JavaVendor");
	}

	/**
	 * ��ȡJVM��װĿ¼
	 * 
	 * @return
	 */
	public static String getJavaHome() {
		return Config.getValue("System.JavaHome");
	}

	/**
	 * ��ȡ�м��������Ϣ
	 * 
	 * @return
	 */
	public static String getContainerInfo() {
		return Config.getValue("System.ContainerInfo");
	}

	/**
	 * ��ȡ�м�������İ汾
	 * 
	 * @return
	 */
	public static String getContainerVersion() {
		String str = Config.getValue("System.ContainerInfo");
		if (str.indexOf("/") > 0) {
			return str.substring(str.lastIndexOf("/") + 1);
		}
		return "0";
	}

	/**
	 * ��ȡ����ϵͳ����
	 * 
	 * @return
	 */
	public static String getOSName() {
		return Config.getValue("System.OSName");
	}

	/**
	 * ��ȡ����ϵͳ�����汾
	 * 
	 * @return
	 */
	public static String getOSPatchLevel() {
		return Config.getValue("System.OSPatchLevel");
	}

	/**
	 * ��ȡ����ϵͳ�ܹ�
	 * 
	 * @return
	 */
	public static String getOSArch() {
		return Config.getValue("System.OSArch");
	}

	/**
	 * ��ȡ����ϵͳ�汾
	 * 
	 * @return
	 */
	public static String getOSVersion() {
		return Config.getValue("System.OSVersion");
	}

	/**
	 * ��ȡ�����м���Ĳ���ϵͳ�û���Ĭ������
	 * 
	 * @return
	 */
	public static String getOSUserLanguage() {
		return Config.getValue("System.OSUserLanguage");
	}

	/**
	 * ��ȡ�����м���Ĳ���ϵͳ�û���
	 * 
	 * @return
	 */
	public static String getOSUserName() {
		return Config.getValue("System.OSUserName");
	}

	/**
	 * ��ȡ�ı��ļ�Ĭ�Ϸָ���
	 * 
	 * @return
	 */
	public static String getLineSeparator() {
		return Config.getValue("System.LineSeparator");
	}

	/**
	 * ��ȡ�ļ����е�·���ָ���
	 * 
	 * @return
	 */
	public static String getFileSeparator() {
		return Config.getValue("System.FileSeparator");
	}

	/**
	 * ��ȡ����ϵͳ��Ĭ���ļ�����
	 * 
	 * @return
	 */
	public static String getFileEncode() {
		return System.getProperty("file.encoding");
	}

	/**
	 * ��ȡ����¼���û���
	 * 
	 * @return
	 */
	public static int getLoginUserCount() {
		return LoginUserCount;
	}

	/**
	 * ��ȡ�����û�������Session���û�����
	 * 
	 * @return
	 */
	public static int getOnlineUserCount() {
		return OnlineUserCount;
	}

	/**
	 * Ĭ�����ݿ��Ƿ���DB2
	 * 
	 * @return
	 */
	public static boolean isDB2() {
		return DBConnPool.getDBConnConfig().DBType.equals(DBConnConfig.DB2);
	}

	/**
	 * Ĭ�����ݿ��Ƿ���Oracle
	 * 
	 * @return
	 */
	public static boolean isOracle() {
		return DBConnPool.getDBConnConfig().DBType.equals(DBConnConfig.ORACLE);
	}

	/**
	 * Ĭ�����ݿ��Ƿ���Mysql
	 * 
	 * @return
	 */
	public static boolean isMysql() {
		return DBConnPool.getDBConnConfig().DBType.equals(DBConnConfig.MYSQL);
	}

	/**
	 * Ĭ�����ݿ��Ƿ���SQL Server
	 * 
	 * @return
	 */
	public static boolean isSQLServer() {
		return DBConnPool.getDBConnConfig().DBType.equals(DBConnConfig.MSSQL);
	}

	/**
	 * Ĭ�����ݿ��Ƿ���Sybase
	 * 
	 * @return
	 */
	public static boolean isSybase() {
		return DBConnPool.getDBConnConfig().DBType.equals(DBConnConfig.SYBASE);
	}

	/**
	 * �м���Ƿ���Tomcat
	 * 
	 * @return
	 */
	public static boolean isTomcat() {
		if (StringUtil.isEmpty(Config.getContainerInfo())) {
			getJBossInfo();
		}
		return Config.getContainerInfo().toLowerCase().indexOf("tomcat") >= 0;
	}

	/**
	 * JBoss��Ҫ�ر��� JBoss����ServletContext.getServerInfo()ʱ�᷵��Apache Tomcat 5.x֮��ģ� ��MainFilter�����Configִ�У���Ҫ�ر���
	 */
	protected static void getJBossInfo() {
		String jboss = System.getProperty("jboss.home.dir");
		if (StringUtil.isNotEmpty(jboss)) {
			try {
				Class c = Class.forName("org.jboss.Version");
				Method m = c.getMethod("getInstance", null);
				Object o = m.invoke(null, null);
				m = c.getMethod("getMajor", null);
				Object major = m.invoke(o, null);
				m = c.getMethod("getMinor", null);
				Object minor = m.invoke(o, null);
				m = c.getMethod("getRevision", null);
				Object revision = m.invoke(o, null);
				m = c.getMethod("getTag", null);
				Object tag = m.invoke(o, null);
				Config.configMap.put("System.ContainerInfo", "JBoss/" + major + "." + minor + "." + revision + "."
						+ tag);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * �м���Ƿ���JBoss
	 * 
	 * @return
	 */
	public static boolean isJboss() {
		if (StringUtil.isEmpty(Config.getContainerInfo())) {
			getJBossInfo();
		}
		return Config.getContainerInfo().toLowerCase().indexOf("jboss") >= 0;
	}

	/**
	 * �м���Ƿ���WebLogic
	 * 
	 * @return
	 */
	public static boolean isWeblogic() {
		return Config.getContainerInfo().toLowerCase().indexOf("weblogic") >= 0;
	}

	/**
	 * �м���Ƿ���WebSphere
	 * 
	 * @return
	 */
	public static boolean isWebSphere() {
		return Config.getContainerInfo().toLowerCase().indexOf("websphere") >= 0;
	}

	/**
	 * �Ƿ��Ǹ��Ӳ���ģʽ����ģʽ��һ��Ӧ�ÿ�����Ϊ���Ⱪ¶�ĵ�ַ��һ�����ж��ContextPath
	 * 
	 * @return
	 */
	public static boolean isComplexDepolyMode() {
		return ComplexDepolyMode;
	}

	/**
	 * �Ƿ��ǵ��Լ������־�����Ϊtrueʱ����SQL
	 */
	public static boolean isDebugLoglevel() {
		return "Debug".equalsIgnoreCase(Config.getLogLevel());
	}

	public static String getLoginPage() {
		String str = configMap.getString("App.LoginPage");
		if (StringUtil.isNotEmpty(str)) {// ������û�������ļ�
			return str;
		}
		return "Login.jsp";
	}
}
