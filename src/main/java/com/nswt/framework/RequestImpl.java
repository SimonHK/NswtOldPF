package com.nswt.framework;

import com.nswt.framework.data.DataCollection;
import com.nswt.framework.utility.CaseIgnoreMapx;
import com.nswt.framework.utility.Mapx;

/**
 * ��ServletRequest�ķ�װ
 * 
 * @Author ������
 * @Date 2007-6-19
 * @Mail nswt@nswt.com.cn
 */
public class RequestImpl extends DataCollection {
	private static final long serialVersionUID = 1L;

	private Mapx Headers = new CaseIgnoreMapx();

	private String URL;

	private String ClientIP;

	private String ClassName;

	private String ServerName;

	private int Port;

	private String Scheme;

	/**
	 * ���ص�ǰ����URL�е�����
	 * 
	 * @return
	 */
	public String getServerName() {
		return ServerName;
	}

	/**
	 * ���õ�ǰ���������������ܺͲ��Դ�����Ҫ���ñ�������
	 * 
	 * @param serverName
	 */
	public void setServerName(String serverName) {
		ServerName = serverName;
	}

	/**
	 * ���ص�ǰ����URL�еĶ˿�
	 * 
	 * @return
	 */
	public int getPort() {
		return Port;
	}

	/**
	 * ���õ�ǰ����Ķ˿ڣ�����ܺͲ��Դ�����Ҫ���ñ�������
	 * 
	 * @param port
	 */
	public void setPort(int port) {
		Port = port;
	}

	/**
	 * ���ص�ǰ�����е�Э�飬��http/https
	 * 
	 * @return
	 */
	public String getScheme() {
		return Scheme;
	}

	/**
	 * ���õ�ǰ�����Э�飬����ܺͲ��Դ�����Ҫ���ñ�������
	 * 
	 * @param scheme
	 */
	public void setScheme(String scheme) {
		Scheme = scheme;
	}

	/**
	 * ���ص�ǰPage�������
	 * 
	 * @return
	 */
	public String getClassName() {
		return ClassName;
	}

	/**
	 * ���õ�ǰ���������������ܺͲ��Դ�����Ҫ���ñ�������
	 * 
	 * @param className
	 */
	public void setClassName(String className) {
		ClassName = className;
	}

	/**
	 * ���ؿͻ���IP��ַ
	 * 
	 * @return
	 */
	public String getClientIP() {
		return ClientIP;
	}

	/**
	 * ���õ�ǰ����Ŀͻ���IP��ַ������ܺͲ��Դ�����Ҫ���ñ�������
	 * 
	 * @param clientIP
	 */
	public void setClientIP(String clientIP) {
		ClientIP = clientIP;
	}

	/**
	 * ���ص�ǰ�����URL
	 * 
	 * @return
	 */
	public String getURL() {
		return URL;
	}

	/**
	 * ���õ�ǰ�����URL������ܺͲ��Դ�����Ҫ���ñ�������
	 * 
	 * @param url
	 */
	public void setURL(String url) {
		URL = url;
	}

	public Mapx getHeaders() {
		return Headers;
	}

	public void setHeaders(Mapx headers) {
		Headers = headers;
	}
}
