package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * �����ƣ����˻�Ա��ϸ��Ϣ����<br>
 * ����룺BZDMemberPersonInfo<br>
 * ��������UserName, BackupNo<br>
 */
public class BZDMemberPersonInfoSchema extends Schema {
	private String UserName;

	private String NickName;

	private String Birthday;

	private String QQ;

	private String MSN;

	private String Tel;

	private String Mobile;

	private String Address;

	private String ZipCode;

	private String BackupNo;

	private String BackupOperator;

	private Date BackupTime;

	private String BackupMemo;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("UserName", DataColumn.STRING, 0, 50 , 0 , true , true),
		new SchemaColumn("NickName", DataColumn.STRING, 1, 100 , 0 , false , false),
		new SchemaColumn("Birthday", DataColumn.STRING, 2, 20 , 0 , false , false),
		new SchemaColumn("QQ", DataColumn.STRING, 3, 10 , 0 , false , false),
		new SchemaColumn("MSN", DataColumn.STRING, 4, 50 , 0 , false , false),
		new SchemaColumn("Tel", DataColumn.STRING, 5, 20 , 0 , false , false),
		new SchemaColumn("Mobile", DataColumn.STRING, 6, 20 , 0 , false , false),
		new SchemaColumn("Address", DataColumn.STRING, 7, 100 , 0 , false , false),
		new SchemaColumn("ZipCode", DataColumn.STRING, 8, 10 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 9, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 10, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 11, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 12, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZDMemberPersonInfo";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into BZDMemberPersonInfo values(?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZDMemberPersonInfo set UserName=?,NickName=?,Birthday=?,QQ=?,MSN=?,Tel=?,Mobile=?,Address=?,ZipCode=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where UserName=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZDMemberPersonInfo  where UserName=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZDMemberPersonInfo  where UserName=? and BackupNo=?";

	public BZDMemberPersonInfoSchema(){
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
		return new BZDMemberPersonInfoSchema();
	}

	protected SchemaSet newSet(){
		return new BZDMemberPersonInfoSet();
	}

	public BZDMemberPersonInfoSet query() {
		return query(null, -1, -1);
	}

	public BZDMemberPersonInfoSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZDMemberPersonInfoSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZDMemberPersonInfoSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZDMemberPersonInfoSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){UserName = (String)v;return;}
		if (i == 1){NickName = (String)v;return;}
		if (i == 2){Birthday = (String)v;return;}
		if (i == 3){QQ = (String)v;return;}
		if (i == 4){MSN = (String)v;return;}
		if (i == 5){Tel = (String)v;return;}
		if (i == 6){Mobile = (String)v;return;}
		if (i == 7){Address = (String)v;return;}
		if (i == 8){ZipCode = (String)v;return;}
		if (i == 9){BackupNo = (String)v;return;}
		if (i == 10){BackupOperator = (String)v;return;}
		if (i == 11){BackupTime = (Date)v;return;}
		if (i == 12){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return UserName;}
		if (i == 1){return NickName;}
		if (i == 2){return Birthday;}
		if (i == 3){return QQ;}
		if (i == 4){return MSN;}
		if (i == 5){return Tel;}
		if (i == 6){return Mobile;}
		if (i == 7){return Address;}
		if (i == 8){return ZipCode;}
		if (i == 9){return BackupNo;}
		if (i == 10){return BackupOperator;}
		if (i == 11){return BackupTime;}
		if (i == 12){return BackupMemo;}
		return null;
	}

	/**
	* ��ȡ�ֶ�UserName��ֵ�����ֶε�<br>
	* �ֶ����� :��Ա�û���<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public String getUserName() {
		return UserName;
	}

	/**
	* �����ֶ�UserName��ֵ�����ֶε�<br>
	* �ֶ����� :��Ա�û���<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setUserName(String userName) {
		this.UserName = userName;
    }

	/**
	* ��ȡ�ֶ�NickName��ֵ�����ֶε�<br>
	* �ֶ����� :�ǳ�<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getNickName() {
		return NickName;
	}

	/**
	* �����ֶ�NickName��ֵ�����ֶε�<br>
	* �ֶ����� :�ǳ�<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setNickName(String nickName) {
		this.NickName = nickName;
    }

	/**
	* ��ȡ�ֶ�Birthday��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getBirthday() {
		return Birthday;
	}

	/**
	* �����ֶ�Birthday��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setBirthday(String birthday) {
		this.Birthday = birthday;
    }

	/**
	* ��ȡ�ֶ�QQ��ֵ�����ֶε�<br>
	* �ֶ����� :QQ��<br>
	* �������� :varchar(10)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getQQ() {
		return QQ;
	}

	/**
	* �����ֶ�QQ��ֵ�����ֶε�<br>
	* �ֶ����� :QQ��<br>
	* �������� :varchar(10)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setQQ(String qQ) {
		this.QQ = qQ;
    }

	/**
	* ��ȡ�ֶ�MSN��ֵ�����ֶε�<br>
	* �ֶ����� :MSN�˺�<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getMSN() {
		return MSN;
	}

	/**
	* �����ֶ�MSN��ֵ�����ֶε�<br>
	* �ֶ����� :MSN�˺�<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setMSN(String mSN) {
		this.MSN = mSN;
    }

	/**
	* ��ȡ�ֶ�Tel��ֵ�����ֶε�<br>
	* �ֶ����� :�̶��绰<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getTel() {
		return Tel;
	}

	/**
	* �����ֶ�Tel��ֵ�����ֶε�<br>
	* �ֶ����� :�̶��绰<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setTel(String tel) {
		this.Tel = tel;
    }

	/**
	* ��ȡ�ֶ�Mobile��ֵ�����ֶε�<br>
	* �ֶ����� :�ֻ�<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getMobile() {
		return Mobile;
	}

	/**
	* �����ֶ�Mobile��ֵ�����ֶε�<br>
	* �ֶ����� :�ֻ�<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setMobile(String mobile) {
		this.Mobile = mobile;
    }

	/**
	* ��ȡ�ֶ�Address��ֵ�����ֶε�<br>
	* �ֶ����� :��ϸ��ַ<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getAddress() {
		return Address;
	}

	/**
	* �����ֶ�Address��ֵ�����ֶε�<br>
	* �ֶ����� :��ϸ��ַ<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAddress(String address) {
		this.Address = address;
    }

	/**
	* ��ȡ�ֶ�ZipCode��ֵ�����ֶε�<br>
	* �ֶ����� :�ʱ�<br>
	* �������� :varchar(10)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getZipCode() {
		return ZipCode;
	}

	/**
	* �����ֶ�ZipCode��ֵ�����ֶε�<br>
	* �ֶ����� :�ʱ�<br>
	* �������� :varchar(10)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setZipCode(String zipCode) {
		this.ZipCode = zipCode;
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