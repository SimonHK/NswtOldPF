package com.nswt.cms.webservice;

public class UserOperationRequest 
{
	//�������ͣ��ο�UserOperator�µĳ���
	private String operationType;
	//�û�������
	private String userCode;
	//�û���
	private String userName;
	//��������
	private String orgCode;
	//������
	private String orgName;
	
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	public String getOperationType() {
		return operationType;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserName() {
		return userName;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getOrgName() {
		return orgName;
	}
}
