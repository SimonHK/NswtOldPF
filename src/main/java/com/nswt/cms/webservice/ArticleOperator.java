/**
 * 
 */
package com.nswt.cms.webservice;

/**
 * @author Administrator
 *
 */
public interface ArticleOperator {
	//新增操作
	public static final String CREATE = "create";
	//修改新闻
	public static final String UPDATE = "update";
	//删除新闻
	public static final String DELETE = "delete";
		
	ArticleOperationResponse doArticleOperation(ArticleOperationRequest request);
}
