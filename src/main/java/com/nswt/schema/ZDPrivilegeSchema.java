package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;

/**
 * �����ƣ�Ȩ�ޱ�<br>
 * ����룺ZDPrivilege<br>
 * ��������OwnerType, Owner, PrivType, ID, Code<br>
 */
public class ZDPrivilegeSchema extends Schema {
	private String OwnerType;

	private String Owner;

	private String PrivType;

	private String ID;

	private String Code;

	private String Value;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("OwnerType", DataColumn.STRING, 0, 1 , 0 , true , true),
		new SchemaColumn("Owner", DataColumn.STRING, 1, 100 , 0 , true , true),
		new SchemaColumn("PrivType", DataColumn.STRING, 2, 40 , 0 , true , true),
		new SchemaColumn("ID", DataColumn.STRING, 3, 100 , 0 , true , true),
		new SchemaColumn("Code", DataColumn.STRING, 4, 40 , 0 , true , true),
		new SchemaColumn("Value", DataColumn.STRING, 5, 2 , 0 , true , false)
	};

	public static final String _TableCode = "ZDPrivilege";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into ZDPrivilege values(?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZDPrivilege set OwnerType=?,Owner=?,PrivType=?,ID=?,Code=?,Value=? where OwnerType=? and Owner=? and PrivType=? and ID=? and Code=?";

	protected static final String _DeleteSQL = "delete from ZDPrivilege  where OwnerType=? and Owner=? and PrivType=? and ID=? and Code=?";

	protected static final String _FillAllSQL = "select * from ZDPrivilege  where OwnerType=? and Owner=? and PrivType=? and ID=? and Code=?";

	public ZDPrivilegeSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[6];
	}

	protected Schema newInstance(){
		return new ZDPrivilegeSchema();
	}

	protected SchemaSet newSet(){
		return new ZDPrivilegeSet();
	}

	public ZDPrivilegeSet query() {
		return query(null, -1, -1);
	}

	public ZDPrivilegeSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZDPrivilegeSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZDPrivilegeSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZDPrivilegeSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){OwnerType = (String)v;return;}
		if (i == 1){Owner = (String)v;return;}
		if (i == 2){PrivType = (String)v;return;}
		if (i == 3){ID = (String)v;return;}
		if (i == 4){Code = (String)v;return;}
		if (i == 5){Value = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return OwnerType;}
		if (i == 1){return Owner;}
		if (i == 2){return PrivType;}
		if (i == 3){return ID;}
		if (i == 4){return Code;}
		if (i == 5){return Value;}
		return null;
	}

	/**
	* ��ȡ�ֶ�OwnerType��ֵ�����ֶε�<br>
	* �ֶ����� :ӵ��������<br>
	* �������� :char(1)<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public String getOwnerType() {
		return OwnerType;
	}

	/**
	* �����ֶ�OwnerType��ֵ�����ֶε�<br>
	* �ֶ����� :ӵ��������<br>
	* �������� :char(1)<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setOwnerType(String ownerType) {
		this.OwnerType = ownerType;
    }

	/**
	* ��ȡ�ֶ�Owner��ֵ�����ֶε�<br>
	* �ֶ����� :ӵ����<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public String getOwner() {
		return Owner;
	}

	/**
	* �����ֶ�Owner��ֵ�����ֶε�<br>
	* �ֶ����� :ӵ����<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setOwner(String owner) {
		this.Owner = owner;
    }

	/**
	* ��ȡ�ֶ�PrivType��ֵ�����ֶε�<br>
	* �ֶ����� :Ȩ������<br>
	* �������� :varchar(40)<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	* ��ע��Ϣ :<br>
	0-��ɫ<br>
	1-�û�<br>
	*/
	public String getPrivType() {
		return PrivType;
	}

	/**
	* �����ֶ�PrivType��ֵ�����ֶε�<br>
	* �ֶ����� :Ȩ������<br>
	* �������� :varchar(40)<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	* ��ע��Ϣ :<br>
	0-��ɫ<br>
	1-�û�<br>
	*/
	public void setPrivType(String privType) {
		this.PrivType = privType;
    }

	/**
	* ��ȡ�ֶ�ID��ֵ�����ֶε�<br>
	* �ֶ����� :����ID<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public String getID() {
		return ID;
	}

	/**
	* �����ֶ�ID��ֵ�����ֶε�<br>
	* �ֶ����� :����ID<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setID(String iD) {
		this.ID = iD;
    }

	/**
	* ��ȡ�ֶ�Code��ֵ�����ֶε�<br>
	* �ֶ����� :Ȩ�޴���<br>
	* �������� :varchar(40)<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	* ��ע��Ϣ :<br>
	��catalogid=-1��ʱ���������ݱ�ʾ��վ������õļ�¼<br>
	*/
	public String getCode() {
		return Code;
	}

	/**
	* �����ֶ�Code��ֵ�����ֶε�<br>
	* �ֶ����� :Ȩ�޴���<br>
	* �������� :varchar(40)<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	* ��ע��Ϣ :<br>
	��catalogid=-1��ʱ���������ݱ�ʾ��վ������õļ�¼<br>
	*/
	public void setCode(String code) {
		this.Code = code;
    }

	/**
	* ��ȡ�ֶ�Value��ֵ�����ֶε�<br>
	* �ֶ����� :Ȩ��ֵ<br>
	* �������� :char(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getValue() {
		return Value;
	}

	/**
	* �����ֶ�Value��ֵ�����ֶε�<br>
	* �ֶ����� :Ȩ��ֵ<br>
	* �������� :char(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setValue(String value) {
		this.Value = value;
    }

}