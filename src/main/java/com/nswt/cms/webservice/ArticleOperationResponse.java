/**
 * 
 */
package com.nswt.cms.webservice;

/**
 * @author Administrator
 *
 */
public class ArticleOperationResponse {

	//����ID
	private String articleID;
	//�ɹ�ʧ��flag��-1ʧ�ܣ�0�ɹ�
	private int errorFlag;
	//������룬CMS�Զ���
	private int errorCode;
	
	//������Ϣ
	//create 1.��Ŀ������ 2.����Ϊ�� 3.����Ϊ��
	//update 1.���Ų�����
	//delete 1.���Ų�����
	private String errorMessage;

	public String getArticleID() {
		return articleID;
	}
	
	public void setArticleID(String articleID) {
		this.articleID = articleID;
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
