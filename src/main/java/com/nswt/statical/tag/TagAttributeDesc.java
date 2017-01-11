package com.nswt.statical.tag;

import com.nswt.framework.data.DataColumn;

/**
 * ��ǩ����������
 */
public class TagAttributeDesc {
	private String Name;
	private boolean isMandatory = false;
	private String Usage;
	private int DataType = DataColumn.STRING;

	/**
	 * ��������
	 */
	public String getName() {
		return Name;
	}

	/**
	 * �Ƿ����
	 */
	public boolean isMandatory() {
		return isMandatory;
	}

	/**
	 * �����÷�
	 */
	public String getUsage() {
		return Usage;
	}

	/**
	 * �������ͣ���DataColumn
	 */
	public int getDataType() {
		return DataType;
	}

	public TagAttributeDesc(String name) {
		this.Name = name;
	}

	public TagAttributeDesc(String name, boolean mandatory) {
		this.Name = name;
		this.isMandatory = mandatory;
	}

	public TagAttributeDesc(String name, int dataType) {
		this.Name = name;
		this.DataType = dataType;
	}

	public TagAttributeDesc(String name, boolean mandatory, int dataType, String usage) {
		this.Name = name;
		this.isMandatory = mandatory;
		this.DataType = dataType;
		this.Usage = usage;
	}

}
