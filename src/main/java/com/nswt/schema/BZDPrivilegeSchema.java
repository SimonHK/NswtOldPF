package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * �����ƣ�Ȩ�ޱ���<br>
 * ����룺BZDPrivilege<br>
 * ��������OwnerType, Owner, PrivType, ID, Code, BackupNo<br>
 */
public class BZDPrivilegeSchema extends Schema {
	private String OwnerType;

	private String Owner;

	private String PrivType;

	private String ID;

	private String Code;

	private String Value;

	private String BackupNo;

	private String BackupOperator;

	private Date BackupTime;

	private String BackupMemo;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("OwnerType", DataColumn.STRING, 0, 1 , 0 , true , true),
		new SchemaColumn("Owner", DataColumn.STRING, 1, 100 , 0 , true , true),
		new SchemaColumn("PrivType", DataColumn.STRING, 2, 40 , 0 , true , true),
		new SchemaColumn("ID", DataColumn.STRING, 3, 100 , 0 , true , true),
		new SchemaColumn("Code", DataColumn.STRING, 4, 40 , 0 , true , true),
		new SchemaColumn("Value", DataColumn.STRING, 5, 2 , 0 , true , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 6, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 7, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 8, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 9, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZDPrivilege";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into BZDPrivilege values(?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZDPrivilege set OwnerType=?,Owner=?,PrivType=?,ID=?,Code=?,Value=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where OwnerType=? and Owner=? and PrivType=? and ID=? and Code=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZDPrivilege  where OwnerType=? and Owner=? and PrivType=? and ID=? and Code=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZDPrivilege  where OwnerType=? and Owner=? and PrivType=? and ID=? and Code=? and BackupNo=?";

	public BZDPrivilegeSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[10];
	}

	protected Schema newInstance(){
		return new BZDPrivilegeSchema();
	}

	protected SchemaSet newSet(){
		return new BZDPrivilegeSet();
	}

	public BZDPrivilegeSet query() {
		return query(null, -1, -1);
	}

	public BZDPrivilegeSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZDPrivilegeSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZDPrivilegeSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZDPrivilegeSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){OwnerType = (String)v;return;}
		if (i == 1){Owner = (String)v;return;}
		if (i == 2){PrivType = (String)v;return;}
		if (i == 3){ID = (String)v;return;}
		if (i == 4){Code = (String)v;return;}
		if (i == 5){Value = (String)v;return;}
		if (i == 6){BackupNo = (String)v;return;}
		if (i == 7){BackupOperator = (String)v;return;}
		if (i == 8){BackupTime = (Date)v;return;}
		if (i == 9){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return OwnerType;}
		if (i == 1){return Owner;}
		if (i == 2){return PrivType;}
		if (i == 3){return ID;}
		if (i == 4){return Code;}
		if (i == 5){return Value;}
		if (i == 6){return BackupNo;}
		if (i == 7){return BackupOperator;}
		if (i == 8){return BackupTime;}
		if (i == 9){return BackupMemo;}
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
	0-��ɫ 1-�û�<br>
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
	0-��ɫ 1-�û�<br>
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

	/**
	* ��ȡ�ֶ�BackupNo��ֵ�����ֶε�<br>
	* �ֶ����� :���ݱ��<br>
	* �������� :varchar(15)<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public String getBackupNo() {
		return BackupNo;
	}

	/**
	* �����ֶ�BackupNo��ֵ�����ֶε�<br>
	* �ֶ����� :���ݱ��<br>
	* �������� :varchar(15)<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setBackupNo(String backupNo) {
		this.BackupNo = backupNo;
    }

	/**
	* ��ȡ�ֶ�BackupOperator��ֵ�����ֶε�<br>
	* �ֶ����� :������<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getBackupOperator() {
		return BackupOperator;
	}

	/**
	* �����ֶ�BackupOperator��ֵ�����ֶε�<br>
	* �ֶ����� :������<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setBackupOperator(String backupOperator) {
		this.BackupOperator = backupOperator;
    }

	/**
	* ��ȡ�ֶ�BackupTime��ֵ�����ֶε�<br>
	* �ֶ����� :����ʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public Date getBackupTime() {
		return BackupTime;
	}

	/**
	* �����ֶ�BackupTime��ֵ�����ֶε�<br>
	* �ֶ����� :����ʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setBackupTime(Date backupTime) {
		this.BackupTime = backupTime;
    }

	/**
	* ��ȡ�ֶ�BackupMemo��ֵ�����ֶε�<br>
	* �ֶ����� :���ݱ�ע<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getBackupMemo() {
		return BackupMemo;
	}

	/**
	* �����ֶ�BackupMemo��ֵ�����ֶε�<br>
	* �ֶ����� :���ݱ�ע<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setBackupMemo(String backupMemo) {
		this.BackupMemo = backupMemo;
    }

}