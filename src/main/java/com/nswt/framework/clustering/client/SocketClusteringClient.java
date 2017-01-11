package com.nswt.framework.clustering.client;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import com.nswt.framework.clustering.Clustering.Server;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.StringUtil;

public class SocketClusteringClient extends ClusteringClient {
	private Server server;

	private Socket so;

	public SocketClusteringClient(Server server) {
		this.server = server;
		if (!server.URL.startsWith("socket://")) {
			throw new RuntimeException("����ļ�Ⱥ������URL��" + server.URL);
		}
	}

	private void init() {
		if (so == null) {
			synchronized (this) {
				if (so == null) {
					String url = server.URL;
					String address = url.substring("socket://".length());
					int index = address.indexOf(":");
					if (index < 1) {
						throw new RuntimeException("����ļ�Ⱥ������URL��" + server.URL);
					}
					String portStr = address.substring(index + 1);
					address = address.substring(0, index);
					int port = 0;
					try {
						port = Integer.parseInt(portStr);
					} catch (Exception e) {
						throw new RuntimeException("����ļ�Ⱥ������URL��" + server.URL);
					}
					try {
						so = new Socket(address, port);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public String executeMethod(String type, String key, String action, String value) {
		init();
		for (int i = 0; i < server.RetryCount; i++) {
			try {
				OutputStream outStream = so.getOutputStream();
				PrintWriter out = new PrintWriter(outStream);
				out.println("Type:Data");
				out.println("Action:Get");
				out.println("Key:" + StringUtil.javaEncode(key) + "");
				if (value != null) {
					out.println("Value:" + StringUtil.javaEncode(value));
				}
				out.println("End");
				InputStream is = so.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				StringBuffer sb = new StringBuffer();
				String line;
				while ((line = br.readLine()) != null) {
					if (line.equals("End")) {
						break;
					}
					sb.append(line);
					sb.append("\n");
				}
				return sb.toString();
			} catch (Exception e) {
				LogUtil.info("HttpClusteringClient.get()�����쳣:" + e.getMessage() + ";URL=" + server.URL);
			}
		}
		synchronized (server) {
			server.isAlive = false;
		}
		throw new RuntimeException("HttpClusteringClient.get()������������" + server.RetryCount + "�ν�ʧ��;URL=" + server.URL);
	}
}
