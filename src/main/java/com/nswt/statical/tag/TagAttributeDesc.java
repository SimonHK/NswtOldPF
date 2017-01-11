package com.nswt.statical.tag;

import com.nswt.framework.data.DataColumn;

/**
 * 标签属性描述类
 */
public class TagAttributeDesc {
	private String Name;
	private boolean isMandatory = false;
	private String Usage;
	private int DataType = DataColumn.STRING;

	/**
	 * 属性名称
	 */
	public String getName() {
		return Name;
	}

	/**
	 * 是否必填
	 */
	public boolean isMandatory() {
		return isMandatory;
	}

	/**
	 * 属性用法
	 */
	public String getUsage() {
		return Usage;
	}

	/**
	 * 数据类型，见DataColumn
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
