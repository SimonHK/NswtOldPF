package com.nswt.framework.clustering.client;

import com.nswt.framework.clustering.Clustering.Server;

/**
 * 基于Memcached的集群，暂未实现
 * 
 * 日期 : 2010-4-22 <br>
 * 作者: NSWT <br>
 * 邮箱：nswt@nswt.com.cn <br>
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
