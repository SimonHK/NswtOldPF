package com.nswt.cms.webservice;

public class UserOperationResponse 
{
	//�û�����
	private String userCode;
	//�ɹ�ʧ��flag��1ʧ�ܣ�0�ɹ�
	private int errorFlag;
	//������룬CMS�Զ���
	private int errorCode;
	
	//������Ϣ
	//create 1.�û��Ѵ��� 2 ���������ڣ���ʱ���ԣ�
	//update 1.�û������� 
	//suspend 1.�û������� 2.��suspend 
	//restore 1.�û������� 2.δsuspend
	private String errorMessage;

	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public int getErrorFlag() {
		return errorFlag;
	}
	public void setErrorFlag(int errorFlag) {
		this.errorFlag = errorFlag;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
