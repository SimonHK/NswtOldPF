package com.nswt.framework.clustering.client;

import com.nswt.framework.clustering.Clustering.Server;

/**
 * ����Memcached�ļ�Ⱥ����δʵ��
 * 
 * ���� : 2010-4-22 <br>
 * ����: NSWT <br>
 * ���䣺nswt@nswt.com.cn <br>
 */
public class MemcachedClusteringClient extends ClusteringClient {
	private Server server;

	public MemcachedClusteringClient(Server server) {
		this.server = server;
	}

	public String executeMethod(String type, String action, String key, String value) {
		return null;
	}
}
