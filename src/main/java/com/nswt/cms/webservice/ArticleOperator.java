/**
 * 
 */
package com.nswt.cms.webservice;

/**
 * @author Administrator
 *
 */
public interface ArticleOperator {
	//��������
	public static final String CREATE = "create";
	//�޸�����
	public static final String UPDATE = "update";
	//ɾ������
	public static final String DELETE = "delete";
		
	ArticleOperationResponse doArticleOperation(ArticleOperationRequest request);
}
