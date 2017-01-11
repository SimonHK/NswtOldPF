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
	 * 模权类型<br>
	 * 内容核心自带的类型只有一个:Block，即生成单独文件 其他类型必须有相应TemplateDataProvider支持，例如ArticleDetail,ArticleList
	 */
	public String getType() {
		return Type;
	}

	/**
	 * 获取模板名称
	 */
	public String getName() {
		return Name;
	}

	/**
	 * 获取模板作者
	 */
	public String getAuthor() {
		return Author;
	}

	/**
	 * 获取模板版本
	 */
	public double getVersion() {
		return Version;
	}

	/**
	 * 获取模板描述
	 */
	public String getDescription() {
		return Description;
	}

	/**
	 * 模板中脚本开始标记
	 */
	public String getScriptStart() {
		return ScriptStart;
	}

	/**
	 * 模板中脚本结束标记
	 */
	public String getScriptEnd() {
		return ScriptEnd;
	}

	/**
	 * 模板文件最后修改时间
	 */
	public long getLastModified() {
		return LastModified;
	}

}
