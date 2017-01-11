package com.nswt.framework.orm;

/**
 * Schema�е����ֶε�������Ϣ<br>
 * 
 * ����: NSWT<br>
 * ���ڣ�2016-7-3<br>
 * �ʼ���nswt@nswt.com.cn<br>
 */
public class SchemaColumn implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private int ColumnType;

	private String ColumnName;

	private int ColumnOrder;

	private int Length;

	private int Precision;

	private boolean Mandatory;

	private boolean isPrimaryKey;

	/**
	 * name: �ֶ���<br>
	 * type: �ֶ�����<br>
	 * order: �ֶ��ڱ��е�˳��<br>
	 * length: �ֶγ���<br>
	 * precision: �ֶξ���<br>
	 * mandatory: �Ƿ����<br>
	 * ispk: �Ƿ�������<br>
	 */
	public SchemaColumn(String name, int type, int order, int length, int precision, boolean mandatory, boolean ispk) {
		ColumnType = type;
		ColumnName = name;
		ColumnOrder = order;
		Length = length;
		Precision = precision;
		Mandatory = mandatory;
		isPrimaryKey = ispk;
	}

	/**
	 * ��ȡ�ֶ���
	 */
	public String getColumnName() {
		return ColumnName;
	}

	/**
	 * ��ȡ�ֶ�˳��
	 */
	public int getColumnOrder() {
		return ColumnOrder;
	}

	/**
	 * ��ȡ�ֶ�����
	 */
	public int getColumnType() {
		return ColumnType;
	}

	/**
	 * �Ƿ�������
	 */
	public boolean isPrimaryKey() {
		return isPrimaryKey;
	}

	/**
	 * ��ȡ�ֶγ���
	 */
	public int getLength() {
		return Length;
	}

	/**
	 * ��ȡ�ֶξ���
	 */
	public int getPrecision() {
		return Precision;
	}

	/**
	 * �Ƿ��Ƿǿ��ֶ�
	 */
	public boolean isMandatory() {
		return Mandatory;
	}
}
