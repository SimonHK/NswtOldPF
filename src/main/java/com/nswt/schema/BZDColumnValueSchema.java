package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * �����ƣ��Զ����ֶ�ֵ����<br>
 * ����룺BZDColumnValue<br>
 * ��������ID, BackupNo<br>
 */
public class BZDColumnValueSchema extends Schema {
	private Long ID;

	private Long ColumnID;

	private String ColumnCode;

	private String TextValue;

	private String RelaType;

	private String RelaID;

	private String BackupNo;

	private String BackupOperator;

	private Date BackupTime;

	private String BackupMemo;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 0 , 0 , true , true),
		new SchemaColumn("ColumnID", DataColumn.LONG, 1, 0 , 0 , true , false),
		new SchemaColumn("ColumnCode", DataColumn.STRING, 2, 100 , 0 , true , false),
		new SchemaColumn("TextValue", DataColumn.CLOB, 3, 0 , 0 , false , false),
		new SchemaColumn("RelaType", DataColumn.STRING, 4, 2 , 0 , false , false),
		new SchemaColumn("RelaID", DataColumn.STRING, 5, 100 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 6, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 7, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 8, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 9, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZDColumnValue";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into BZDColumnValue values(?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZDColumnValue set ID=?,ColumnID=?,ColumnCode=?,TextValue=?,RelaType=?,RelaID=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZDColumnValue  where ID=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZDColumnValue  where ID=? and BackupNo=?";

	public BZDColumnValueSchema(){
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
		return new BZDColumnValueSchema();
	}

	protected SchemaSet newSet(){
		return new BZDColumnValueSet();
	}

	public BZDColumnValueSet query() {
		return query(null, -1, -1);
	}

	public BZDColumnValueSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZDColumnValueSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZDColumnValueSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZDColumnValueSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){ColumnID = null;}else{ColumnID = new Long(v.toString());}return;}
		if (i == 2){ColumnCode = (String)v;return;}
		if (i == 3){TextValue = (String)v;return;}
		if (i == 4){RelaType = (String)v;return;}
		if (i == 5){RelaID = (String)v;return;}
		if (i == 6){BackupNo = (String)v;return;}
		if (i == 7){BackupOperator = (String)v;return;}
		if (i == 8){BackupTime = (Date)v;return;}
		if (i == 9){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return ColumnID;}
		if (i == 2){return ColumnCode;}
		if (i == 3){return TextValue;}
		if (i == 4){return RelaType;}
		if (i == 5){return RelaID;}
		if (i == 6){return BackupNo;}
		if (i == 7){return BackupOperator;}
		if (i == 8){return BackupTime;}
		if (i == 9){return BackupMemo;}
		return null;
	}

	/**
	* ��ȡ�ֶ�ID��ֵ�����ֶε�<br>
	* �ֶ����� :ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public long getID() {
		if(ID==null){return 0;}
		return ID.longValue();
	}

	/**
	* �����ֶ�ID��ֵ�����ֶε�<br>
	* �ֶ����� :ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setID(long iD) {
		this.ID = new Long(iD);
    }

	/**
	* �����ֶ�ID��ֵ�����ֶε�<br>
	* �ֶ����� :ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setID(String iD) {
		if (iD == null){
			this.ID = null;
			return;
		}
		this.ID = new Long(iD);
    }

	/**
	* ��ȡ�ֶ�ColumnID��ֵ�����ֶε�<br>
	* �ֶ����� :�ֶ�ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public long getColumnID() {
		if(ColumnID==null){return 0;}
		return ColumnID.longValue();
	}

	/**
	* �����ֶ�ColumnID��ֵ�����ֶε�<br>
	* �ֶ����� :�ֶ�ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setColumnID(long columnID) {
		this.ColumnID = new Long(columnID);
    }

	/**
	* �����ֶ�ColumnID��ֵ�����ֶε�<br>
	* �ֶ����� :�ֶ�ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setColumnID(String columnID) {
		if (columnID == null){
			this.ColumnID = null;
			return;
		}
		this.ColumnID = new Long(columnID);
    }

	/**
	* ��ȡ�ֶ�ColumnCode��ֵ�����ֶε�<br>
	* �ֶ����� :�ֶδ���<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getColumnCode() {
		return ColumnCode;
	}

	/**
	* �����ֶ�ColumnCode��ֵ�����ֶε�<br>
	* �ֶ����� :�ֶδ���<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setColumnCode(String columnCode) {
		this.ColumnCode = columnCode;
    }

	/**
	* ��ȡ�ֶ�TextValue��ֵ�����ֶε�<br>
	* �ֶ����� :�ֶ�ֵ<br>
	* �������� :text<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getTextValue() {
		return TextValue;
	}

	/**
	* �����ֶ�TextValue��ֵ�����ֶε�<br>
	* �ֶ����� :�ֶ�ֵ<br>
	* �������� :text<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setTextValue(String textValue) {
		this.TextValue = textValue;
    }

	/**
	* ��ȡ�ֶ�RelaType��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :char(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	* ��ע��Ϣ :<br>
	1-CMSվ�� 2-CMS��Ŀ<br>
	*/
	public String getRelaType() {
		return RelaType;
	}

	/**
	* �����ֶ�RelaType��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :char(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	* ��ע��Ϣ :<br>
	1-CMSվ�� 2-CMS��Ŀ<br>
	*/
	public void setRelaType(String relaType) {
		this.RelaType = relaType;
    }

	/**
	* ��ȡ�ֶ�RelaID��ֵ�����ֶε�<br>
	* �ֶ����� :����ID<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getRelaID() {
		return RelaID;
	}

	/**
	* �����ֶ�RelaID��ֵ�����ֶε�<br>
	* �ֶ����� :����ID<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setRelaID(String relaID) {
		this.RelaID = relaID;
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