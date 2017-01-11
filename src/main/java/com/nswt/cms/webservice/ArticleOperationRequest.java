/**
 * 
 */
package com.nswt.cms.webservice;

/**
 * @author Administrator
 *
 */
public class ArticleOperationRequest {
	//操作类型，参看ArticleOperator下的常数
	private String operationType;
	//新闻ID
	private String articleID;
	//栏目ID
	private String catalogID;
	//新闻标题
	private String title;
	//新闻内容
	private String content;
	//附件子节流组
	private byte[][] attachBytes;
	//附件名称数组
	private String[] attachNames;
	//作者
	private String author;
	//机构
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
