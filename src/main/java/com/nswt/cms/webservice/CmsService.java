package com.nswt.cms.webservice;

/**
 * webservice�ӿ� �ṩ���¡���Ŀ���û��ӿ�
 * @author lanjun lanjun@nswt.com
 * 2016-5-4
 *
 */
public interface CmsService {
	/**
	 * �½�����
	 * @param catalogID ��Ŀid
	 * @param title ����
	 * @param author ����
	 * @param content ��������
	 * @param publishDate ��������
	 * @return
	 */
	public long addArticle(long catalogID,String title,String author,String content,String publishDate);
	
	/**
	 * �༭����
	 * @param articleID ����ID
	 * @param title ���±���
	 * @param author ����
	 * @param content ��������
	 * @param publishDate ��������
	 * @return
	 */
	public boolean editArticle(long articleID,String title,String author,String content,String publishDate);
	
	/**
	 * ɾ������
	 * @param articleID ����ID
	 * @return
	 */
	public boolean deleteArticle(long articleID);

	/**
	 * ��������
	 * @param articleID ����ID
	 * @return
	 */
	public boolean publishArticle(long articleID);
	
	/**
	 * �½���Ŀ
	 * @param parentID �ϼ���ĿID
	 * @param name ��Ŀ����
	 * @param type ��Ŀ����
	 * @param alias ����
	 * @return
	 */
	public long addCatalog(long parentID,String name,int type,String alias);
	
	/**
	 * �޸���Ŀ
	 * @param ID ��ĿID
	 * @param name ��Ŀ����
	 * @param alias ��Ŀ����
	 * @return
	 */
	public boolean editCatalog(long ID,String name,String alias);
	
	/**
	 * ɾ����Ŀ
	 * @param ID ��ĿID
	 * @return
	 */
	public boolean deleteCatalog(long ID);
	
	/**
	 * ������Ŀ
	 * @param ID ��ĿID
	 * @return
	 */
	public boolean publishCatalog(long ID);
	
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
	public long addUser(String userName, String realName, String password, String departID, String email);
	
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
	public boolean editUser(String userName, String realName, String password, String departID, String email);
	
	/**
	 * ɾ���û�
	 * @param userName
	 * @return
	 */
	public boolean deleteUser(String userName);
	
	/**
	 * ���ӻ�Ա
	 * @param UserName
	 * @param PassWord
	 * @param Email
	 * @return
	 */
	public long addMember(String UserName, String RealName, String PassWord, String Email);
	
	/**
	 * �޸Ļ�Ա
	 * @param UserName
	 * @param PassWord
	 * @param Email
	 * @return
	 */
	public boolean editMember(String UserName, String RealName, String PassWord, String Email);
	
	/**
	 * ɾ����Ա
	 * @param UserName
	 * @return
	 */
	public boolean deleteMember(String UserName);
}
