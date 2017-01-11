/**
 * 
 */
package com.nswt.cms.webservice;

/**
 * @author Administrator
 *
 */
public class ArticleOperationResponse {

	//新闻ID
	private String articleID;
	//成功失败flag，-1失败，0成功
	private int errorFlag;
	//错误代码，CMS自定义
	private int errorCode;
	
	//错误消息
	//create 1.栏目不存在 2.标题为空 3.内容为空
	//update 1.新闻不存在
	//delete 1.新闻不存在
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
