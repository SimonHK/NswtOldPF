package com.nswt.cms.webservice;

public interface UserOperator
{
	//��������
	public static final String CREATE = "create";
	//�����û�
	public static final String SUSPEND = "suspend";
	//�ָ������û�
	public static final String RESTORE = "restore";
	//����û�
	public static final String UPDATE = "update";
	
	UserOperationResponse doUserOperation(UserOperationRequest request);
}
