package com.nswt.cms.webservice;

import com.nswt.cms.api.ArticleAPI;
import com.nswt.cms.api.CatalogAPI;
import com.nswt.cms.api.MemberAPI;
import com.nswt.cms.api.UserAPI;
import com.nswt.cms.datachannel.Publisher;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.messages.LongTimeTask;
import com.nswt.framework.utility.DateUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.schema.ZCArticleSchema;
import com.nswt.schema.ZCArticleSet;

/**
 * webservice�ӿ�ʵ��
 * @author lanjun
 *
 */



public class CmsServiceImpl implements CmsService {
	/**
	 * �½�����
	 * @param catalogID ��Ŀid
	 * @param title ����
	 * @param author ����
	 * @param content ��������
	 * @param publishDate ��������
	 * @return
	 */
	
	public long addArticle(long catalogID, String title, String author, String content, String publishDate) {
		ArticleAPI p = new ArticleAPI();
		ZCArticleSchema article = new ZCArticleSchema();
		article.setCatalogID(catalogID);
		article.setTitle(title);
		article.setAuthor(author);
		article.setContent(content);
		article.setPublishDate(DateUtil.parse(publishDate));
		p.setSchema(article);
		if(p.insert()>0){
			return article.getID();
		}else{
			return -1;
		}
	}
	
	
	
	
	/**
	 * �½���Ŀ
	 * @param parentID �ϼ���ĿID
	 * @param name ��Ŀ����
	 * @param type ��Ŀ����
	 * @param alias ����
	 * @return
	 */
	public long addCatalog(long parentID, String name, int type, String alias) {
		Mapx params=new Mapx();
		params.put("ParentID", ""+parentID);
		params.put("Name", name);
		params.put("Type", ""+type);
		params.put("Alias", alias);
		CatalogAPI c=new CatalogAPI();
		c.setParams(params);
		
		if(c.insert()>0){
			return  1;
		}else return -1;
	}

	/**
	 * �½��û�
	 * @param userName �û���
	 * @param realName ��ʵ����
	 * @param password ����
	 * @param md5pass �Ƿ���Ҫ����
	 * @param departID ��������
	 * @param email ����
	 * 
	 * @return -1 ʧ��
	 * 			0 �û��Ѵ���
	 * 			1 �ɹ�
	 */
	public long addUser(String userName, String realName, String password, String departID, String email) {
		Mapx params=new Mapx();
		params.put("Username", ""+userName);
		params.put("RealName", realName);
		params.put("Password", ""+password);
		params.put("Email", email);
		params.put("BranchInnerCode", departID);
		if (StringUtil.isEmpty(departID)) {
			params.put("BranchInnerCode", "0001");
		}
		UserAPI u=new UserAPI();
		u.setParams(params);
		return u.insert();
		
	}

	/**
	 * ɾ������
	 * @param articleID ����ID
	 * @return
	 */
	public boolean deleteArticle(long articleID) {
		ZCArticleSchema article=new ZCArticleSchema();
		article.setID(articleID);
		if(article.fill()){
			ArticleAPI p = new ArticleAPI();
			p.setSchema(article);
			return p.delete();
		} else {
			return false;
		}
	}

	/**
	 * ɾ����Ŀ
	 * @param ID ��ĿID
	 * @return
	 */
	public boolean deleteCatalog(long ID) {
		Mapx params=new Mapx();
		params.put("CatalogID", ""+ID);
		CatalogAPI c=new CatalogAPI();
		c.setParams(params);
		return c.delete();
	}

	/**
	 * ɾ���û�
	 * @param userName
	 * @return
	 */
	public boolean deleteUser(String userName) {
		Mapx params=new Mapx();
		params.put("Username", userName);
		UserAPI u=new UserAPI();
		u.setParams(params);
		return u.delete();
	}

	/**
	 * �༭����
	 * @param articleID ����ID
	 * @param title ���±���
	 * @param author ����
	 * @param content ��������
	 * @param publishDate ��������
	 * @return
	 */
	public boolean editArticle(long articleID, String title, String author, String content, String publishDate) {
		Mapx params=new Mapx();
		params.put("DocID", ""+articleID);
		params.put("Title", title);
		params.put("Author", author);
		params.put("Cotent", content);
		params.put("PublishDate", publishDate);
		ArticleAPI a=new ArticleAPI();
		a.setParams(params);
		return a.update();
	}

	/**
	 * �޸���Ŀ
	 * @param ID ��ĿID
	 * @param name ��Ŀ����
	 * @param alias ��Ŀ����
	 * @return
	 */
	public boolean editCatalog(long ID, String name, String alias) {
		Mapx params=new Mapx();
		params.put("CatalogID", ""+ID);
		params.put("Name", name);
		params.put("Alias", alias);
		CatalogAPI c=new CatalogAPI();
		c.setParams(params);
		return c.update();
		
	}

	/**
	 * �༭�û�
	 * @param userName �û���
	 * @param realName ��ʵ����
	 * @param password ����
	 * @param md5pass �Ƿ���Ҫ����
	 * @param departID ��������
	 * @param email ����
	 * 
	 * @return true �ɹ�
	 * 			false ʧ��
	 */
	public boolean editUser(String userName, String realName, String password, String departID, String email) {
		Mapx params = new Mapx();
		params.put("Username", ""+userName);
		params.put("RealName", realName);
		params.put("Password", ""+password);
		params.put("BranchInnerCode", departID);
		if (StringUtil.isEmpty(departID)) {
			params.put("BranchInnerCode", "0001");
		}
		params.put("Email", email);
		UserAPI u = new UserAPI();
		u.setParams(params);
		return u.update();
	}

	/**
	 * ��������
	 * @param articleID ����ID
	 * @return
	 */
	public boolean publishArticle(long articleID) {
		
		ZCArticleSchema article = new ZCArticleSchema();
		ZCArticleSet set = article.query(new QueryBuilder("where id =? or id in(select id from zcarticle where refersourceid=? )", articleID, articleID));
		if(set.size()>0){
			Publisher p = new Publisher();
			return p.publishArticle(set);
		}
		return false;
	}
	
	/**
	 * ������Ŀ
	 * @param ID ��ĿID
	 * @return
	 */
	public boolean publishCatalog(final long ID) {
		try{
			LongTimeTask ltt = new LongTimeTask() {
				public void execute() {
					Publisher p = new Publisher();
					p.publishCatalog(ID, true, true, this);
					setPercent(100);
				}
			};
			ltt.start();
		}catch(Exception e){
			return false;
		}
		return true;
	}

	public long addMember(String UserName, String RealName, String PassWord, String Email) {
		Mapx params=new Mapx();
		params.put("UserName", ""+UserName);
		params.put("PassWord", PassWord);
		params.put("RealName", RealName);
		params.put("Email", ""+Email);
		MemberAPI m=new MemberAPI();
		m.setParams(params);
		return m.insert();
	}

	public boolean deleteMember(String UserName) {
		Mapx params=new Mapx();
		params.put("UserName", UserName);
		MemberAPI m=new MemberAPI();
		m.setParams(params);
		return m.delete();
	}

	public boolean editMember(String UserName, String RealName, String PassWord, String Email) {
		Mapx params=new Mapx();
		params.put("UserName", ""+UserName);
		params.put("RealName", ""+RealName);
		params.put("PassWord", PassWord);
		params.put("Email", ""+Email);
		MemberAPI m=new MemberAPI();
		m.setParams(params);
		return m.update();
	}

}
