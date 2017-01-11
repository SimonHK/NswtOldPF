package com.nswt.framework.clustering.client;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;

import com.nswt.framework.clustering.Clustering.Server;
import com.nswt.framework.utility.LogUtil;

/**
 * 日期 : 2010-4-21 <br>
 * 作者: NSWT <br>
 * 邮箱：nswt@nswt.com.cn <br>
 */
public class HttpClusteringClient extends ClusteringClient {
	private HttpClient httpClient;
	private Server server;

	public HttpClusteringClient(Server server) {
		this.server = server;
		if (!server.URL.startsWith("http://")) {
			throw new RuntimeException("错误的集群服务器URL：" + server.URL);
		}
	}

	private void init() {
		if (httpClient == null) {
			SimpleHttpConnectionManager cm = new SimpleHttpConnectionManager();
			HttpConnectionManagerParams hcmp = new HttpConnectionManagerParams();
			hcmp.setDefaultMaxConnectionsPerHost(1);
			hcmp.setConnectionTimeout(3000);// 三秒之内必须返回
			hcmp.setSoTimeout(3000);
			cm.setParams(hcmp);
			httpClient = new HttpClient(cm);
		}
	}

	public String executeMethod(String type, String key, String action, String value) {
		init();
		for (int i = 0; i < server.RetryCount; i++) {
			try {
				PostMethod pm = new PostMethod(server.URL);
				pm.addParameter("Type", type);
				pm.addParameter("Action", action);
				pm.addParameter("Key", key);
				if (value != null) {
					pm.addParameter("Value", value);
				}
				httpClient.executeMethod(pm);
				if (pm.getStatusCode() != 200) {
					LogUtil.info("HttpClusteringClient.get()状态代码异常:" + pm.getStatusCode() + ";URL=" + server.URL);
					continue;
				}
				String result = pm.getResponseBodyAsString();
				return result.trim();
			} catch (Exception e) {
				LogUtil.info("HttpClusteringClient.get()发生异常:" + e.getMessage() + ";URL=" + server.URL);
			}
		}
		synchronized (server) {
			server.isAlive = false;
		}
		throw new RuntimeException("HttpClusteringClient.get()发生错误，重试" + server.RetryCount + "次皆失败;URL=" + server.URL);
	}
}
