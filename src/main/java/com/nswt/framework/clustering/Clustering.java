package com.nswt.framework.clustering;

import com.nswt.framework.Config;
import com.nswt.framework.ConfigLoader;
import com.nswt.framework.ConfigLoader.NodeData;
import com.nswt.framework.User.UserData;
import com.nswt.framework.clustering.server.SocketClusteringServer;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.Mapx;

/**
 * ������еļ�Ⱥ����ֻ�������нڵ㹲��һ�����ݿ�����<br>
 * �������ݿ����Ҳ��һ����Ⱥ�������ݿ��Ʒ�����ṩ��Ⱥ���ܣ��� ���� : 2010-2-4 <br>
 * ����: NSWT <br>
 * ���䣺nswt@nswt.com.cn <br>
 * 
 * @since 1.3
 */
public class Clustering {
	private static Mapx configMap;

	private static Server[] Servers;

	/**
	 * ���ؼ�Ⱥ���ã���clustering.xml���أ��ᶨʱ����Ƿ��иı�
	 */
	private static void init() {
		if (configMap == null) {
			try {
				configMap = new Mapx();
				NodeData[] datas = ConfigLoader.getNodeDataList("clustering.config");
				for (int i = 0; datas != null && i < datas.length; i++) {
					configMap.put(datas[i].getAttributes().getString("name"), datas[i].getBody());
				}
				datas = ConfigLoader.getNodeDataList("clustering.servers.server");
				if (datas != null) {
					Server[] arr = new Server[datas.length];
					for (int i = 0; i < datas.length; i++) {
						Server server = new Server();
						server.URL = datas[i].getAttributes().getString("URL");
						server.Weight = datas[i].getAttributes().getInt("weight");
						server.RetryCount = datas[i].getAttributes().getInt("retrycount");
						server.Timeout = datas[i].getAttributes().getInt("timeout");
						arr[i] = server;
					}
				}
				tryInitServer();
				LogUtil.info("----" + Config.getAppCode() + "(" + Config.getAppName() + "): Clustering Initialized----");
			} catch (Exception e) {
				e.printStackTrace();
				LogUtil.info("----" + Config.getAppCode() + "(" + Config.getAppName() + "): Clustering Failure----");
			}
		}
	}

	/**
	 * �����SocketServer,���Դ򿪶˿�
	 */
	private static void tryInitServer() {
		String flag = configMap.getString("UserAsClusteringServer");
		if (!"true".equalsIgnoreCase(flag)) {
			return;
		}
		String type = configMap.getString("ClusteringServerType");
		String portStr = configMap.getString("SocketPort");
		if ("Socket".equalsIgnoreCase(type)) {
			try {
				final int port = Integer.parseInt(portStr);
				new Thread() {
					public void run() {
						SocketClusteringServer.start(port);
					}
				}.start();
			} catch (Exception e) {
				throw new RuntimeException("clustering.xml�����õ�SocketPort�������֣�" + portStr);
			}
		}
	}

	/**
	 * ��ǰӦ���Ƿ��ڼ�Ⱥ֮��
	 */
	public static boolean isClustering() {
		init();
		if (configMap == null) {
			return false;
		}
		return "true".equals(configMap.getString("ClusteringEnable"));
	}

	/**
	 * ��ǰӦ���Ƿ���һ����Ⱥ�����ṩ��
	 */
	public static boolean isClusteringServer() {
		init();
		if (configMap == null) {
			return false;
		}
		return "true".equals(configMap.getString("UserAsClusteringServer"));
	}

	public static void put(String key, String value) {
		init();
	}

	public static void putObject(String key, Object value) {
		init();
	}

	public static String get(String key) {
		init();
		return null;
	}

	public static Object getObject(String key) {
		init();
		return null;
	}

	public static boolean containsKey(String key) {
		init();
		return false;
	}

	public static void remove(String key) {
		init();
	}

	public static void putMapx(String key, Mapx map) {
		init();
	}

	public static Mapx getMapx(String key) {
		init();
		return null;
	}

	public static String[] getAllKeys() {
		init();
		return null;
	}

	public static void cacheUser(UserData user) {
		init();
	}

	public static class Server {
		/**
		 * ��Ⱥ������URL
		 */
		public String URL;

		/**
		 * ����ʧ��ʱ���Դ���
		 */
		public int RetryCount;

		/**
		 * �೤ʱ�����Ϊ������崻�
		 */
		public int Timeout;

		/**
		 * ��Ⱥ������Ȩ�أ�Ȩ�ش�Ľ��洢��������
		 */
		public int Weight;

		/**
		 * �Ƿ�����Ϊfalse��ServerValidteTask�̻߳ᶨ�ڳ�������
		 */
		public boolean isAlive;
	}

}
