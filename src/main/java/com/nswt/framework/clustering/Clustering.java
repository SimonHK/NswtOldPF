package com.nswt.framework.clustering;

import com.nswt.framework.Config;
import com.nswt.framework.ConfigLoader;
import com.nswt.framework.ConfigLoader.NodeData;
import com.nswt.framework.User.UserData;
import com.nswt.framework.clustering.server.SocketClusteringServer;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.Mapx;

/**
 * 本框架中的集群功能只限于所有节点共用一个数据库的情况<br>
 * （此数据库可能也是一个集群，由数据库产品本身提供集群功能）。 日期 : 2010-2-4 <br>
 * 作者: NSWT <br>
 * 邮箱：nswt@nswt.com.cn <br>
 * 
 * @since 1.3
 */
public class Clustering {
	private static Mapx configMap;

	private static Server[] Servers;

	/**
	 * 加载集群配置，从clustering.xml加载，会定时检测是否有改变
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
	 * 如果是SocketServer,则尝试打开端口
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
				throw new RuntimeException("clustering.xml中配置的SocketPort不是数字：" + portStr);
			}
		}
	}

	/**
	 * 当前应用是否处于集群之中
	 */
	public static boolean isClustering() {
		init();
		if (configMap == null) {
			return false;
		}
		return "true".equals(configMap.getString("ClusteringEnable"));
	}

	/**
	 * 当前应用是否是一个集群服务提供者
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
		 * 集群服务器URL
		 */
		public String URL;

		/**
		 * 操作失败时重试次数
		 */
		public int RetryCount;

		/**
		 * 多长时间后即认为服务器宕机
		 */
		public int Timeout;

		/**
		 * 集群服务器权重，权重大的将存储更多数据
		 */
		public int Weight;

		/**
		 * 是否存活，如果为false则ServerValidteTask线程会定期尝试连接
		 */
		public boolean isAlive;
	}

}
