package com.nswt.cms.stat;

/**
 * @Author NSWT
 * @Date 2008-11-20
 * @Mail nswt@nswt.com.cn
 */
public class Visit {
	/**
	 * �����ʵ�URL
	 */
	public String URL;

	/**
	 * ������ԴURL
	 */
	public String Referer;

	/**
	 * �ͻ��˵�ַ
	 */
	public String IP;

	/**
	 * �Ƿ��Ƕ����û�;
	 */
	public boolean UVFlag;

	/**
	 * �Ƿ��ǻ�ͷ��;
	 */
	public boolean RVFlag;

	/**
	 * Ψһ�ÿͱ�־;
	 */
	public String UniqueID;

	/**
	 * �Ƿ�����IP;
	 */
	public boolean IPFlag;

	/**
	 * ��Ļ��С
	 */
	public String Screen;

	/**
	 * ��ʾ��ɫ��
	 */
	public String ColorDepth;

	/**
	 * ����
	 */
	public String Language;

	/**
	 * �������Ϣ
	 */
	public String UserAgent;

	/**
	 * ����ϵͳ
	 */
	public String OS;

	/**
	 * �Ƿ�֧��Java
	 */
	public boolean JavaEnabled;

	/**
	 * �Ƿ�֧��Flash
	 */
	public boolean FlashEnabled;

	/**
	 * Flash�汾
	 */
	public String FlashVersion;

	/**
	 * �Ƿ�֧��Cookie
	 */
	public boolean CookieEnabled;

	/**
	 * �������
	 */
	public String Type;

	/**
	 * �����ڲ�����
	 */
	public String CatalogInnerCode;

	/**
	 * Ҷ�ӽڵ�ID(����ID����ƷID��ͼƬID��)
	 */
	public long LeafID;

	/**
	 * վ��ID
	 */
	public long SiteID;

	/**
	 * ����ʱ��
	 */
	public long VisitTime;

	/**
	 * ��������
	 */
	public String Host;

	/**
	 * �¼�����,��ҳ��رյ���ͳ�ƺ�̨ʱֵΪUnload
	 */
	public String Event;

	/**
	 * ���������
	 */
	public String Browser;

	/**
	 * ����
	 */
	public String District;

	/**
	 * ҳ��ͣ��ʱ��
	 */
	public int StickTime;

	/**
	 * ����Ƶ��
	 */
	public int Frequency;
}
