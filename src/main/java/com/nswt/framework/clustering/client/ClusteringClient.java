package com.nswt.framework.clustering.client;

import com.nswt.framework.clustering.Clustering.Server;
import com.nswt.framework.clustering.server.ClusteringData;
import com.nswt.framework.utility.Mapx;

/**
 * ���� : 2010-4-21 <br>
 * ����: NSWT <br>
 * ���䣺nswt@nswt.com.cn <br>
 */
public abstract class ClusteringClient {
	Mapx clientMap = new Mapx();

	public static ClusteringClient getClient(Server server) {
		if (server.URL.startsWith("http")) {
			return new HttpClusteringClient(server);
		} else if (server.URL.startsWith("socket")) {
			return new SocketClusteringClient(server);
		} else if (server.URL.startsWith("memcached")) {
			return new MemcachedClusteringClient(server);
		}
		throw new RuntimeException("��֧�ֵļ�Ⱥ����������:" + server.URL);
	}

	public boolean containsKey(String key) {
		String result = executeMethod("Data", "Get", key, null);
		return result.startsWith(ClusteringData.RESULT_TRUE);
	}

	public String get(String key) {
		String result = executeMethod("Data", "Get", key, null);
		if (result.startsWith(ClusteringData.RESULT_NULL)) {
			return null;
		}
		return result.substring(result.indexOf('\n') + 1);
	}

	public void put(String key, String value) {
		executeMethod("Data", "Put", key, value);
	}

	public void remove(String key) {
		executeMethod("Data", "Remove", key, null);
	}

	public double add(String key, double number) {
		String result = executeMethod("Data", "Add", key, "" + number);
		return Double.parseDouble(result);
	}

	public double addAverage(String key, long number) {
		return addAverage(key, number, 1);
	}

	public double addAverage(String key, long total, int count) {
		String result = executeMethod("Data", "AddAverage", key, total + "|" + count);
		return Double.parseDouble(result);
	}

	public abstract String executeMethod(String type, String action, String key, String value);
}
