/**
 * 
 */
package com.nswt.cms.webservice;

/**
 * @author Administrator
 *
 */
public class ArticleOperationRequest {
	//�������ͣ��ο�ArticleOperator�µĳ���
	private String operationType;
	//����ID
	private String articleID;
	//��ĿID
	private String catalogID;
	//���ű���
	private String title;
	//��������
	private String content;
	//�����ӽ�����
	private byte[][] attachBytes;
	//������������
	private String[] attachNames;
	//����
	private String author;
	//����
	private String branchID;
	
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	
	public String getOperationType() {
		return operationType;
	}
	
	public void setCatalogID(String catalogID) {
		this.catalogID = catalogID;
	}
	
	public String getCatalogID() {
		return catalogID;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public void setAttachBytes(byte[][] attachBytes) {
		this.attachBytes = attachBytes;
	}
	
	public byte[][] getAttachBytes() {
		return attachBytes;
	}
	
	public void setAttachNames(String[] attachNames) {
		this.attachNames = attachNames;
	}
	
	public String[] getAttachNames() {
		return attachNames;
	}
	
	public void setArticleID(String articleID) {
		this.articleID = articleID;
	}
	
	public String getArticleID() {
		return articleID;
	}
	
	public void setBranchID(String branchID) {
		this.branchID = branchID;
	}
	
	public String getBranchID() {
		return branchID;
	}
}
