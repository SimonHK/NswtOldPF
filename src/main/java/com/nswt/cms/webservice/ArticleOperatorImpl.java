/**
 * 
 */
package com.nswt.cms.webservice;

import com.nswt.cms.api.ArticleAPI;
import com.nswt.cms.pub.CatalogUtil;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.utility.Errorx;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.schema.ZCArticleSchema;
import com.nswt.schema.ZCCatalogSchema;

/**
 * @author Administrator
 *
 */
public class ArticleOperatorImpl implements ArticleOperator {
	public ArticleOperationResponse doArticleOperation(ArticleOperationRequest request) {
		String operationType = request.getOperationType();
		String catalogID = request.getCatalogID();
	    String articleID = request.getArticleID();
	    String title  = request.getTitle();
	    String content = request.getContent();
	    byte[][] attachBytes = request.getAttachBytes();
	    String[] attachNames = request.getAttachNames();
	    String author = request.getAuthor();
	    String branchID = request.getBranchID();
	    ArticleOperationResponse response  = new ArticleOperationResponse();
		int errorFlag = 0;
		int errorCode = 0;
		String  errorMsg  = "";
		if(CREATE.equals(operationType)){
			ZCArticleSchema article = new ZCArticleSchema();
			Mapx params=new Mapx();
			if (StringUtil.isNotEmpty(catalogID)) {
				ZCCatalogSchema catalog = CatalogUtil.getSchema(catalogID);
				if (catalog == null) {
					errorMsg = "�½�����ʧ�ܣ���Ŀ�����ڣ�";
					LogUtil.info(errorMsg);
					errorFlag = -1;
					errorCode = 1;
					response.setErrorCode(errorCode);
					response.setErrorFlag(errorFlag);
					response.setErrorMessage(errorMsg);
					return response;
				} else {
					article.setCatalogID(catalogID);
				}
			} else {
				errorMsg = "�½�����ʧ�ܣ���Ŀ�����ڣ�";
				LogUtil.info(errorMsg);
				errorFlag = -1;
				errorCode = 1;
				response.setErrorCode(errorCode);
				response.setErrorFlag(errorFlag);
				response.setErrorMessage(errorMsg);
				return response;
			}
			String branchInnerCode = "0001";
			if (StringUtil.isNotEmpty(branchID)) {
				branchInnerCode = new QueryBuilder("select BranchInnerCode from ZDBranch where Prop1=?"
						, branchID).executeString();
				if (StringUtil.isEmpty(branchInnerCode)) {
					branchInnerCode = "0001";
				}
			}
			
			article.setBranchInnerCode(branchInnerCode);
			if (StringUtil.isNotEmpty(title)) {
				article.setTitle(title);
			} else {
				errorMsg = "�½�����ʧ�ܣ�����Ϊ�գ�";
				LogUtil.info(errorMsg);
				errorFlag = -1;
				errorCode = 2;
				response.setErrorCode(errorCode);
				response.setErrorFlag(errorFlag);
				response.setErrorMessage(errorMsg);
				return response;
			}
			
			if (StringUtil.isEmpty(content)&&(attachBytes.length==0||attachNames.length==0)) {
				errorMsg = "�½�����ʧ�ܣ���������Ϊ�գ�";
				LogUtil.info(errorMsg);
				errorFlag = -1;
				errorCode = 2;
				response.setErrorCode(errorCode);
				response.setErrorFlag(errorFlag);
				response.setErrorMessage(errorMsg);
				return response;
			}else{
				article.setContent(content);
			}
			
			if (StringUtil.isNotEmpty(author)) {
				article.setAuthor(author);
			}
			
			ArticleAPI api = new ArticleAPI();
			if(attachBytes.length>0&&attachBytes.length==attachNames.length){
				params.put("attachNams", attachNames);
				params.put("attachBytes", attachBytes);
			}
			api.setParams(params);
			api.setSchema(article);
			long result=api.insert();
			if (result != -1) {
				LogUtil.info("�½����ųɹ���" + title);
				response.setArticleID(result+ "");
			} else {
				errorMsg = "�½�����ʧ�ܣ�"+Errorx.getMessages()[0];
				LogUtil.info(errorMsg);
				errorFlag = -1;
				errorCode = 1;
				response.setErrorCode(errorCode);
				response.setErrorFlag(errorFlag);
				response.setErrorMessage(errorMsg);
				return response;
			}
		} else if (UPDATE.equals(operationType)) {
			Mapx params = new Mapx();
			if (StringUtil.isEmpty(articleID)) {
				errorMsg = "�޸�����ʧ�ܣ���������ID����";
				LogUtil.info(errorMsg);
				errorFlag = -1;
				errorCode = 1;
				response.setErrorCode(errorCode);
				response.setErrorFlag(errorFlag);
				response.setErrorMessage(errorMsg);
				return response;
			}
			
			params.put("ArticleID", ""+articleID);
			params.put("DocID", ""+articleID);
			ZCArticleSchema article = new ZCArticleSchema();
			article.setID(articleID);
			if (!article.fill()) {
				errorMsg = "�޸�����ʧ�ܣ����Ų����ڣ�";
				LogUtil.info(errorMsg);
				errorFlag = -1;
				errorCode = 1;
				response.setErrorCode(errorCode);
				response.setErrorFlag(errorFlag);
				response.setErrorMessage(errorMsg);
				return response;
			}
			
			if (StringUtil.isNotEmpty(catalogID)) {
				ZCCatalogSchema catalog = CatalogUtil.getSchema(catalogID);
				if (catalog != null) {
					params.put("CatalogID", ""+catalogID);
				}
			}
			
			if (StringUtil.isNotEmpty(title)) {
				params.put("Title", ""+title);
			} else {
				title = article.getTitle();
			}
			
			if (StringUtil.isNotEmpty(content)) {
				params.put("Content", ""+content);
			}
			
			if (StringUtil.isNotEmpty(author)) {
				params.put("Author", ""+author);
			}
			
			ArticleAPI api = new ArticleAPI();
			if(attachBytes.length>0&&attachBytes.length==attachNames.length){
				params.put("attachNams", attachNames);
				params.put("attachBytes", attachBytes);
			}
			api.setParams(params);
			api.setParams(params);
			
			if(api.update()){
				LogUtil.info("�ɹ��޸����ţ�" + title);
				response.setArticleID(articleID);
			}else{
				errorMsg = "�޸�����ʧ�ܣ�"+title;
				LogUtil.info(errorMsg);
				errorFlag = -1;
				errorCode = 2;
			}
		} else if(DELETE.equals(operationType)) {
			if (StringUtil.isEmpty(articleID)) {
				errorMsg = "ɾ������ʧ�ܣ���������ID����";
				LogUtil.info(errorMsg);
				errorFlag = -1;
				errorCode = 1;
				response.setErrorCode(errorCode);
				response.setErrorFlag(errorFlag);
				response.setErrorMessage(errorMsg);
				return response;
			}
			
			ZCArticleSchema article = new ZCArticleSchema();
			article.setID(articleID);
			if (!article.fill()) {
				errorMsg = "ɾ������ʧ�ܣ����Ų�����";
				LogUtil.info(errorMsg);
				errorFlag = -1;
				errorCode = 1;
				response.setErrorCode(errorCode);
				response.setErrorFlag(errorFlag);
				response.setErrorMessage(errorMsg);
				return response;
			}
			ArticleAPI api = new ArticleAPI();
			api.setSchema(article);
			
			if(api.delete()){
				LogUtil.info("�ɹ�ɾ�����ţ�"+article.getTitle());
				response.setArticleID(articleID);
			}else{
				errorMsg = "ɾ������ʧ�ܣ�"+Errorx.getMessages()[0];
				LogUtil.info(errorMsg);
				errorFlag = -1;
				errorCode = 1;
			}
		} else {
			errorFlag = -1;
			errorCode = 5;
			errorMsg = "��֧�ֵĲ�������:"+operationType;
		}
		response.setErrorCode(errorCode);
		response.setErrorFlag(errorFlag);
		response.setErrorMessage(errorMsg);
		return response;
	}
}
