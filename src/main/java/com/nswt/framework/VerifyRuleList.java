package com.nswt.framework;

import java.util.ArrayList;

/**
 * У����������
 * 
 * ���ߣ�NSWT<br>
 * ���ڣ�2016-9-27<br>
 * �ʼ���nswt@nswt.com.cn<br>
 */
public class VerifyRuleList {
	private ArrayList array = new ArrayList();

	private String Message;

	private RequestImpl Request;

	private ResponseImpl Response;

	/**
	 * ����һ��У���ֶμ���У�����
	 * 
	 * @param fieldID
	 * @param fieldName
	 * @param rule
	 */
	public void add(String fieldID, String fieldName, String rule) {
		array.add(new String[] { fieldID, fieldName, rule });
	}

	/**
	 * У�������ֶ�
	 * 
	 * @return
	 */
	public boolean doVerify() {
		VerifyRule rule = new VerifyRule();
		boolean flag = true;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < array.size(); i++) {
			String[] f = (String[]) array.get(i);
			rule.setRule(f[2]);
			if (!rule.verify(Request.getString(f[0]))) {
				sb.append(rule.getMessages(f[1]));
				flag = false;
			}
		}
		if (!flag) {
			Response.setStatus(0);
			Message = sb.toString();
			Response.setMessage(sb.toString());
		}
		return flag;
	}

	public String getMessage() {
		return Message;
	}

	public RequestImpl getRequest() {
		return Request;
	}

	public void setRequest(RequestImpl request) {
		Request = request;
	}

	public ResponseImpl getResponse() {
		return Response;
	}

	public void setResponse(ResponseImpl response) {
		Response = response;
	}
}
