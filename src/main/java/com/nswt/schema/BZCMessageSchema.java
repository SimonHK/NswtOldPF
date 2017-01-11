package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * �����ƣ�����Ϣ����<br>
 * ����룺BZCMessage<br>
 * ��������ID, BackupNo<br>
 */
public class BZCMessageSchema extends Schema {
	private Long ID;

	private String FromUser;

	private String ToUser;

	private String Box;

	private Long ReadFlag;

	private Long PopFlag;

	private String Subject;

	private String Content;

	private Date AddTime;

	private String BackupNo;

	private String BackupOperator;

	private Date BackupTime;

	private String BackupMemo;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 0 , 0 , true , true),
		new SchemaColumn("FromUser", DataColumn.STRING, 1, 50 , 0 , false , false),
		new SchemaColumn("ToUser", DataColumn.STRING, 2, 50 , 0 , false , false),
		new SchemaColumn("Box", DataColumn.STRING, 3, 10 , 0 , false , false),
		new SchemaColumn("ReadFlag", DataColumn.LONG, 4, 0 , 0 , false , false),
		new SchemaColumn("PopFlag", DataColumn.LONG, 5, 0 , 0 , false , false),
		new SchemaColumn("Subject", DataColumn.STRING, 6, 500 , 0 , false , false),
		new SchemaColumn("Content", DataColumn.CLOB, 7, 0 , 0 , false , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 8, 0 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 9, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 10, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 11, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 12, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZCMessage";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into BZCMessage values(?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZCMessage set ID=?,FromUser=?,ToUser=?,Box=?,ReadFlag=?,PopFlag=?,Subject=?,Content=?,AddTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZCMessage  where ID=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZCMessage  where ID=? and BackupNo=?";

	public BZCMessageSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[13];
	}

	protected Schema newInstance(){
		return new BZCMessageSchema();
	}

	protected SchemaSet newSet(){
		return new BZCMessageSet();
	}

	public BZCMessageSet query() {
		return query(null, -1, -1);
	}

	public BZCMessageSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZCMessageSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZCMessageSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZCMessageSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){FromUser = (String)v;return;}
		if (i == 2){ToUser = (String)v;return;}
		if (i == 3){Box = (String)v;return;}
		if (i == 4){if(v==null){ReadFlag = null;}else{ReadFlag = new Long(v.toString());}return;}
		if (i == 5){if(v==null){PopFlag = null;}else{PopFlag = new Long(v.toString());}return;}
		if (i == 6){Subject = (String)v;return;}
		if (i == 7){Content = (String)v;return;}
		if (i == 8){AddTime = (Date)v;return;}
		if (i == 9){BackupNo = (String)v;return;}
		if (i == 10){BackupOperator = (String)v;return;}
		if (i == 11){BackupTime = (Date)v;return;}
		if (i == 12){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return FromUser;}
		if (i == 2){return ToUser;}
		if (i == 3){return Box;}
		if (i == 4){return ReadFlag;}
		if (i == 5){return PopFlag;}
		if (i == 6){return Subject;}
		if (i == 7){return Content;}
		if (i == 8){return AddTime;}
		if (i == 9){return BackupNo;}
		if (i == 10){return BackupOperator;}
		if (i == 11){return BackupTime;}
		if (i == 12){return BackupMemo;}
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
	* ��ȡ�ֶ�FromUser��ֵ�����ֶε�<br>
	* �ֶ����� :�����û�<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getFromUser() {
		return FromUser;
	}

	/**
	* �����ֶ�FromUser��ֵ�����ֶε�<br>
	* �ֶ����� :�����û�<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setFromUser(String fromUser) {
		this.FromUser = fromUser;
    }

	/**
	* ��ȡ�ֶ�ToUser��ֵ�����ֶε�<br>
	* �ֶ����� :�����û�<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getToUser() {
		return ToUser;
	}

	/**
	* �����ֶ�ToUser��ֵ�����ֶε�<br>
	* �ֶ����� :�����û�<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setToUser(String toUser) {
		this.ToUser = toUser;
    }

	/**
	* ��ȡ�ֶ�Box��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :varchar(10)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	* ��ע��Ϣ :<br>
	inbox �ռ��� outbox ������ draft �ݸ�<br>
	*/
	public String getBox() {
		return Box;
	}

	/**
	* �����ֶ�Box��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :varchar(10)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	* ��ע��Ϣ :<br>
	inbox �ռ��� outbox ������ draft �ݸ�<br>
	*/
	public void setBox(String box) {
		this.Box = box;
    }

	/**
	* ��ȡ�ֶ�ReadFlag��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ��Ķ�<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	* ��ע��Ϣ :<br>
	0 δ�Ķ� 1 ���Ķ�<br>
	*/
	public long getReadFlag() {
		if(ReadFlag==null){return 0;}
		return ReadFlag.longValue();
	}

	/**
	* �����ֶ�ReadFlag��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ��Ķ�<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	* ��ע��Ϣ :<br>
	0 δ�Ķ� 1 ���Ķ�<br>
	*/
	public void setReadFlag(long readFlag) {
		this.ReadFlag = new Long(readFlag);
    }

	/**
	* �����ֶ�ReadFlag��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ��Ķ�<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	* ��ע��Ϣ :<br>
	0 δ�Ķ� 1 ���Ķ�<br>
	*/
	public void setReadFlag(String readFlag) {
		if (readFlag == null){
			this.ReadFlag = null;
			return;
		}
		this.ReadFlag = new Long(readFlag);
    }

	/**
	* ��ȡ�ֶ�PopFlag��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ񼺵�����ʾ��<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getPopFlag() {
		if(PopFlag==null){return 0;}
		return PopFlag.longValue();
	}

	/**
	* �����ֶ�PopFlag��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ񼺵�����ʾ��<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setPopFlag(long popFlag) {
		this.PopFlag = new Long(popFlag);
    }

	/**
	* �����ֶ�PopFlag��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ񼺵�����ʾ��<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setPopFlag(String popFlag) {
		if (popFlag == null){
			this.PopFlag = null;
			return;
		}
		this.PopFlag = new Long(popFlag);
    }

	/**
	* ��ȡ�ֶ�Subject��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :varchar(500)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getSubject() {
		return Subject;
	}

	/**
	* �����ֶ�Subject��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :varchar(500)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setSubject(String subject) {
		this.Subject = subject;
    }

	/**
	* ��ȡ�ֶ�Content��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :text<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getContent() {
		return Content;
	}

	/**
	* �����ֶ�Content��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :text<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setContent(String content) {
		this.Content = content;
    }

	/**
	* ��ȡ�ֶ�AddTime��ֵ�����ֶε�<br>
	* �ֶ����� :���ʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public Date getAddTime() {
		return AddTime;
	}

	/**
	* �����ֶ�AddTime��ֵ�����ֶε�<br>
	* �ֶ����� :���ʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
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