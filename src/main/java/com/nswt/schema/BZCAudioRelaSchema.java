package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * �����ƣ���Ƶ��������<br>
 * ����룺BZCAudioRela<br>
 * ��������ID, RelaID, RelaType, BackupNo<br>
 */
public class BZCAudioRelaSchema extends Schema {
	private Long ID;

	private Long RelaID;

	private String RelaType;

	private String BackupNo;

	private String BackupOperator;

	private Date BackupTime;

	private String BackupMemo;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 0 , 0 , true , true),
		new SchemaColumn("RelaID", DataColumn.LONG, 1, 0 , 0 , true , true),
		new SchemaColumn("RelaType", DataColumn.STRING, 2, 20 , 0 , true , true),
		new SchemaColumn("BackupNo", DataColumn.STRING, 3, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 4, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 5, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 6, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZCAudioRela";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into BZCAudioRela values(?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZCAudioRela set ID=?,RelaID=?,RelaType=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and RelaID=? and RelaType=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZCAudioRela  where ID=? and RelaID=? and RelaType=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZCAudioRela  where ID=? and RelaID=? and RelaType=? and BackupNo=?";

	public BZCAudioRelaSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[7];
	}

	protected Schema newInstance(){
		return new BZCAudioRelaSchema();
	}

	protected SchemaSet newSet(){
		return new BZCAudioRelaSet();
	}

	public BZCAudioRelaSet query() {
		return query(null, -1, -1);
	}

	public BZCAudioRelaSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZCAudioRelaSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZCAudioRelaSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZCAudioRelaSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){RelaID = null;}else{RelaID = new Long(v.toString());}return;}
		if (i == 2){RelaType = (String)v;return;}
		if (i == 3){BackupNo = (String)v;return;}
		if (i == 4){BackupOperator = (String)v;return;}
		if (i == 5){BackupTime = (Date)v;return;}
		if (i == 6){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return RelaID;}
		if (i == 2){return RelaType;}
		if (i == 3){return BackupNo;}
		if (i == 4){return BackupOperator;}
		if (i == 5){return BackupTime;}
		if (i == 6){return BackupMemo;}
		return null;
	}

	/**
	* ��ȡ�ֶ�ID��ֵ�����ֶε�<br>
	* �ֶ����� :��ƵID<br>
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
	* �ֶ����� :��ƵID<br>
	* �������� :bigint<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setID(long iD) {
		this.ID = new Long(iD);
    }

	/**
	* �����ֶ�ID��ֵ�����ֶε�<br>
	* �ֶ����� :��ƵID<br>
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
	* ��ȡ�ֶ�RelaID��ֵ�����ֶε�<br>
	* �ֶ����� :��Ƶ����ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public long getRelaID() {
		if(RelaID==null){return 0;}
		return RelaID.longValue();
	}

	/**
	* �����ֶ�RelaID��ֵ�����ֶε�<br>
	* �ֶ����� :��Ƶ����ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setRelaID(long relaID) {
		this.RelaID = new Long(relaID);
    }

	/**
	* �����ֶ�RelaID��ֵ�����ֶε�<br>
	* �ֶ����� :��Ƶ����ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setRelaID(String relaID) {
		if (relaID == null){
			this.RelaID = null;
			return;
		}
		this.RelaID = new Long(relaID);
    }

	/**
	* ��ȡ�ֶ�RelaType��ֵ�����ֶε�<br>
	* �ֶ����� :��Ƶ�������<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	* ��ע��Ϣ :<br>
	-1-�������Ƶ�������κ�ID������ֻ�ǰ���Ƶ�ϴ��˶��� 0-�����е���Ƶ<br>
	*/
	public String getRelaType() {
		return RelaType;
	}

	/**
	* �����ֶ�RelaType��ֵ�����ֶε�<br>
	* �ֶ����� :��Ƶ�������<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	* ��ע��Ϣ :<br>
	-1-�������Ƶ�������κ�ID������ֻ�ǰ���Ƶ�ϴ��˶��� 0-�����е���Ƶ<br>
	*/
	public void setRelaType(String relaType) {
		this.RelaType = relaType;
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