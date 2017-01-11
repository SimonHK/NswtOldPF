package com.nswt.framework;

import com.nswt.framework.data.DataCollection;
import com.nswt.framework.utility.CaseIgnoreMapx;
import com.nswt.framework.utility.Mapx;

/**
 * ��ServletResponse�ķ�װ
 * 
 * @Author NSWT
 * @Date 2007-6-19
 * @Mail nswt@nswt.com.cn
 */
public class ResponseImpl extends DataCollection {
	private static final long serialVersionUID = 1L;

	private Mapx Headers = new CaseIgnoreMapx();

	public int Status = 1;

	public String Message = "";

	/**
	 * ��ȡ���κ�̨�������ظ�JavaScript����Ϣ
	 * 
	 * @return
	 */
	public String getMessage() {
		return Message;
	}

	/**
	 * ���ñ��κ�̨�������ظ�JavaScript����Ϣ������״̬��Ϊ0
	 * 
	 * @param message
	 */
	public void setError(String message) {
		setMessage(message);
		setStatus(0);
	}

	/**
	 * ���ñ��κ�̨�������ظ�JavaScript����Ϣ
	 * 
	 * @param message
	 */
	public void setMessage(String message) {
		Message = message;
		this.put(Constant.ResponseMessageAttrName, Message);
	}

	/**
	 * ��ȡ���κ�̨�������ظ�JavaScript��״̬�룬һ�������0��ʾִ���쳣��1��ʾִ�гɹ�
	 * 
	 * @return
	 */
	public int getStatus() {
		return Status;
	}

	/**
	 * ���ñ��κ�̨�������ظ�JavaScript��״̬��
	 * 
	 * @param status
	 */
	public void setStatus(int status) {
		Status = status;
		this.put(Constant.ResponseStatusAttrName, Status);
	}

	/**
	 * ���ñ��κ�̨�������ظ�JavaScript����Ϣ��״̬��
	 * 
	 * @param status
	 * @param message
	 */
	public void setLogInfo(int status, String message) {
		Status = status;
		this.put(Constant.ResponseStatusAttrName, Status);
		Message = message;
		this.put(Constant.ResponseMessageAttrName, Message);
	}

	/**
	 * ��ResponseImplת����XML
	 * 
	 * @see com.nswt.framework.data.DataCollection#toXML()
	 */
	public String toXML() {
		this.put(Constant.ResponseStatusAttrName, Status);
		return super.toXML();
	}

	public Mapx getHeaders() {
		return Headers;
	}

	public void setHeaders(Mapx headers) {
		Headers = headers;
	}

}
