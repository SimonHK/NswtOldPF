package com.nswt.framework;

/**
 * һ��ҳ���ģ���е����к�̨�����ļ��ϡ�<br>
 * ������ӦJavaScript��Server.sendRequest()�����ĺ�̨�඼����̳б���<br>
 * 
 * @author NSWT
 * @date 2016-11-15
 * @email nswt@nswt.com.cn
 */
public abstract class Page {
	/**
	 * ��Ӧ�����������������������Response�е�������JavaScript�п�����Response.get()��ȡ��
	 */
	protected ResponseImpl Response = new ResponseImpl();

	/**
	 * ������������в���������URL�ͱ��������Լ�����Httpͷ
	 */
	protected RequestImpl Request;

	/**
	 * ���������͵�����Cookie
	 */
	protected CookieImpl Cookies;

	/**
	 * ��������������󣬳�ʼ��֮��������ִ��
	 */
	public void setRequest(RequestImpl r) {
		Request = r;
	}

	/**
	 * ��ȡ������������JavaScript�е�$V���൱��Request.getString()
	 */
	public String $V(String id) {
		return Request.getString(id);
	}

	/**
	 * ���ò�����������JavaScript�е�$S���൱��Response.put()
	 */
	public void $S(String id, Object value) {
		Response.put(id, value);
	}

	/**
	 * ��ȡ��ǰ��Ӧ����
	 */
	public ResponseImpl getResponse() {
		return Response;
	}

	/**
	 * ��ȡCookie����
	 */
	public CookieImpl getCookies() {
		return Cookies;
	}

	/**
	 * ����Cookie����
	 */
	public void setCookies(CookieImpl c) {
		this.Cookies = c;
	}

	/**
	 * �ض���ָ��URL
	 */
	public void redirect(String url) {
		Response.put(Constant.ResponseScriptAttr, "window.location=\"" + url + "\";");
	}

	public void forward(String url) {

	}
}
