package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * �����ƣ��û���־����<br>
 * ����룺BZDUserLog<br>
 * ��������UserName, LogID, BackupNo<br>
 */
public class BZDUserLogSchema extends Schema {
	private String UserName;

	private Long LogID;

	private String IP;

	private String LogType;

	private String SubType;

	private String LogMessage;

	private String Memo;

	private Date AddTime;

	private String BackupNo;

	private String BackupOperator;

	private Date BackupTime;

	private String BackupMemo;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("UserName", DataColumn.STRING, 0, 200 , 0 , true , true),
		new SchemaColumn("LogID", DataColumn.LONG, 1, 0 , 0 , true , true),
		new SchemaColumn("IP", DataColumn.STRING, 2, 40 , 0 , false , false),
		new SchemaColumn("LogType", DataColumn.STRING, 3, 20 , 0 , true , false),
		new SchemaColumn("SubType", DataColumn.STRING, 4, 20 , 0 , false , false),
		new SchemaColumn("LogMessage", DataColumn.STRING, 5, 400 , 0 , false , false),
		new SchemaColumn("Memo", DataColumn.STRING, 6, 40 , 0 , false , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 7, 0 , 0 , true , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 8, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 9, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 10, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 11, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZDUserLog";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into BZDUserLog values(?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZDUserLog set UserName=?,LogID=?,IP=?,LogType=?,SubType=?,LogMessage=?,Memo=?,AddTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where UserName=? and LogID=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZDUserLog  where UserName=? and LogID=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZDUserLog  where UserName=? and LogID=? and BackupNo=?";

	public BZDUserLogSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[12];
	}

	protected Schema newInstance(){
		return new BZDUserLogSchema();
	}

	protected SchemaSet newSet(){
		return new BZDUserLogSet();
	}

	public BZDUserLogSet query() {
		return query(null, -1, -1);
	}

	public BZDUserLogSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZDUserLogSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZDUserLogSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZDUserLogSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){UserName = (String)v;return;}
		if (i == 1){if(v==null){LogID = null;}else{LogID = new Long(v.toString());}return;}
		if (i == 2){IP = (String)v;return;}
		if (i == 3){LogType = (String)v;return;}
		if (i == 4){SubType = (String)v;return;}
		if (i == 5){LogMessage = (String)v;return;}
		if (i == 6){Memo = (String)v;return;}
		if (i == 7){AddTime = (Date)v;return;}
		if (i == 8){BackupNo = (String)v;return;}
		if (i == 9){BackupOperator = (String)v;return;}
		if (i == 10){BackupTime = (Date)v;return;}
		if (i == 11){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return UserName;}
		if (i == 1){return LogID;}
		if (i == 2){return IP;}
		if (i == 3){return LogType;}
		if (i == 4){return SubType;}
		if (i == 5){return LogMessage;}
		if (i == 6){return Memo;}
		if (i == 7){return AddTime;}
		if (i == 8){return BackupNo;}
		if (i == 9){return BackupOperator;}
		if (i == 10){return BackupTime;}
		if (i == 11){return BackupMemo;}
		return null;
	}

	/**
	* ��ȡ�ֶ�UserName��ֵ�����ֶε�<br>
	* �ֶ����� :�û���<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public String getUserName() {
		return UserName;
	}

	/**
	* �����ֶ�UserName��ֵ�����ֶε�<br>
	* �ֶ����� :�û���<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setUserName(String userName) {
		this.UserName = userName;
    }

	/**
	* ��ȡ�ֶ�LogID��ֵ�����ֶε�<br>
	* �ֶ����� :��¼ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public long getLogID() {
		if(LogID==null){return 0;}
		return LogID.longValue();
	}

	/**
	* �����ֶ�LogID��ֵ�����ֶε�<br>
	* �ֶ����� :��¼ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setLogID(long logID) {
		this.LogID = new Long(logID);
    }

	/**
	* �����ֶ�LogID��ֵ�����ֶε�<br>
	* �ֶ����� :��¼ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setLogID(String logID) {
		if (logID == null){
			this.LogID = null;
			return;
		}
		this.LogID = new Long(logID);
    }

	/**
	* ��ȡ�ֶ�IP��ֵ�����ֶε�<br>
	* �ֶ����� :�û���ǰIP<br>
	* �������� :varchar(40)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getIP() {
		return IP;
	}

	/**
	* �����ֶ�IP��ֵ�����ֶε�<br>
	* �ֶ����� :�û���ǰIP<br>
	* �������� :varchar(40)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setIP(String iP) {
		this.IP = iP;
    }

	/**
	* ��ȡ�ֶ�LogType��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getLogType() {
		return LogType;
	}

	/**
	* �����ֶ�LogType��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setLogType(String logType) {
		this.LogType = logType;
    }

	/**
	* ��ȡ�ֶ�SubType��ֵ�����ֶε�<br>
	* �ֶ����� :�Ӳ�������<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getSubType() {
		return SubType;
	}

	/**
	* �����ֶ�SubType��ֵ�����ֶε�<br>
	* �ֶ����� :�Ӳ�������<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setSubType(String subType) {
		this.SubType = subType;
    }

	/**
	* ��ȡ�ֶ�LogMessage��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :varchar(400)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getLogMessage() {
		return LogMessage;
	}

	/**
	* �����ֶ�LogMessage��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :varchar(400)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setLogMessage(String logMessage) {
		this.LogMessage = logMessage;
    }

	/**
	* ��ȡ�ֶ�Memo��ֵ�����ֶε�<br>
	* �ֶ����� :��ע<br>
	* �������� :varchar(40)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getMemo() {
		return Memo;
	}

	/**
	* �����ֶ�Memo��ֵ�����ֶε�<br>
	* �ֶ����� :��ע<br>
	* �������� :varchar(40)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setMemo(String memo) {
		this.Memo = memo;
    }

	/**
	* ��ȡ�ֶ�AddTime��ֵ�����ֶε�<br>
	* �ֶ����� :����ʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public Date getAddTime() {
		return AddTime;
	}

	/**
	* �����ֶ�AddTime��ֵ�����ֶε�<br>
	* �ֶ����� :����ʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setAddTime(Date addTime) {
		this.AddTime = addTime;
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