package com.nswt.cms.template;

public class HtmlNameRule {
	private int level; //����·����������ļ�Ŀ¼�㼶
		
	private String nameRuleStr; //ԭʼ�ַ��� ���� /${Year}/${Month}/${Day}/${catalog.ID}-${document.ID}.shtml
		
	private String fullPath; //�������ȫ·��
		
	private String dirPath; //�������ļ�����·��
	
	private String fileName; //�ļ���

	public String getDirPath() {
		return dirPath;
	}

	public void setDirPath(String dirPath) {
		this.dirPath = dirPath;
	}

	public String getFullPath() {
		return fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getNameRuleStr() {
		return nameRuleStr;
	}

	public void setNameRuleStr(String nameRuleStr) {
		this.nameRuleStr = nameRuleStr;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
