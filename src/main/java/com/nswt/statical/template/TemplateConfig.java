package com.nswt.statical.template;

/**
 * @Author NSWT
 * @Date 2016-5-29
 * @Mail nswt@nswt.com.cn
 */
public class TemplateConfig {
	private String Type;

	private String Name;

	private String Author;

	private double Version;

	private String Description;

	private String ScriptStart;

	private String ScriptEnd;

	private long LastModified;

	public TemplateConfig(String type, String name, String author, double version, String description,
			String scriptStart, String scriptEnd, long lastModified) {
		this.Type = type;
		this.Name = name;
		this.Author = author;
		this.Version = version;
		this.Description = description;
		this.ScriptEnd = scriptEnd;
		this.ScriptStart = scriptStart;
		this.LastModified = lastModified;
	}

	/**
	 * ģȨ����<br>
	 * ���ݺ����Դ�������ֻ��һ��:Block�������ɵ����ļ� �������ͱ�������ӦTemplateDataProvider֧�֣�����ArticleDetail,ArticleList
	 */
	public String getType() {
		return Type;
	}

	/**
	 * ��ȡģ������
	 */
	public String getName() {
		return Name;
	}

	/**
	 * ��ȡģ������
	 */
	public String getAuthor() {
		return Author;
	}

	/**
	 * ��ȡģ��汾
	 */
	public double getVersion() {
		return Version;
	}

	/**
	 * ��ȡģ������
	 */
	public String getDescription() {
		return Description;
	}

	/**
	 * ģ���нű���ʼ���
	 */
	public String getScriptStart() {
		return ScriptStart;
	}

	/**
	 * ģ���нű��������
	 */
	public String getScriptEnd() {
		return ScriptEnd;
	}

	/**
	 * ģ���ļ�����޸�ʱ��
	 */
	public long getLastModified() {
		return LastModified;
	}

}
