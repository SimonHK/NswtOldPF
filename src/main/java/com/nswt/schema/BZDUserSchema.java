package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * �����ƣ��û�����<br>
 * ����룺BZDUser<br>
 * ��������UserName, BackupNo<br>
 */
public class BZDUserSchema extends Schema {
	private String UserName;

	private String RealName;

	private String Password;

	private String BranchInnerCode;

	private String IsBranchAdmin;

	private String Status;

	private String Type;

	private String Email;

	private String Tel;

	private String Mobile;

	private String Prop1;

	private String Prop2;

	private String Prop6;

	private String Prop5;

	private String Prop4;

	private String Prop3;

	private String Memo;

	private Date AddTime;

	private String AddUser;

	private Date ModifyTime;

	private String ModifyUser;

	private String BackupNo;

	private String BackupOperator;

	private Date BackupTime;

	private String BackupMemo;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("UserName", DataColumn.STRING, 0, 200 , 0 , true , true),
		new SchemaColumn("RealName", DataColumn.STRING, 1, 200 , 0 , false , false),
		new SchemaColumn("Password", DataColumn.STRING, 2, 100 , 0 , true , false),
		new SchemaColumn("BranchInnerCode", DataColumn.STRING, 3, 100 , 0 , true , false),
		new SchemaColumn("IsBranchAdmin", DataColumn.STRING, 4, 1 , 0 , true , false),
		new SchemaColumn("Status", DataColumn.STRING, 5, 50 , 0 , true , false),
		new SchemaColumn("Type", DataColumn.STRING, 6, 1 , 0 , true , false),
		new SchemaColumn("Email", DataColumn.STRING, 7, 100 , 0 , false , false),
		new SchemaColumn("Tel", DataColumn.STRING, 8, 20 , 0 , false , false),
		new SchemaColumn("Mobile", DataColumn.STRING, 9, 50 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 10, 50 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 11, 50 , 0 , false , false),
		new SchemaColumn("Prop6", DataColumn.STRING, 12, 50 , 0 , false , false),
		new SchemaColumn("Prop5", DataColumn.STRING, 13, 50 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 14, 100 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 15, 100 , 0 , false , false),
		new SchemaColumn("Memo", DataColumn.STRING, 16, 100 , 0 , false , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 17, 0 , 0 , true , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 18, 200 , 0 , true , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 19, 0 , 0 , false , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 20, 200 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 21, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 22, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 23, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 24, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZDUser";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into BZDUser values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZDUser set UserName=?,RealName=?,Password=?,BranchInnerCode=?,IsBranchAdmin=?,Status=?,Type=?,Email=?,Tel=?,Mobile=?,Prop1=?,Prop2=?,Prop6=?,Prop5=?,Prop4=?,Prop3=?,Memo=?,AddTime=?,AddUser=?,ModifyTime=?,ModifyUser=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where UserName=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZDUser  where UserName=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZDUser  where UserName=? and BackupNo=?";

	public BZDUserSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[25];
	}

	protected Schema newInstance(){
		return new BZDUserSchema();
	}

	protected SchemaSet newSet(){
		return new BZDUserSet();
	}

	public BZDUserSet query() {
		return query(null, -1, -1);
	}

	public BZDUserSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZDUserSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZDUserSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZDUserSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){UserName = (String)v;return;}
		if (i == 1){RealName = (String)v;return;}
		if (i == 2){Password = (String)v;return;}
		if (i == 3){BranchInnerCode = (String)v;return;}
		if (i == 4){IsBranchAdmin = (String)v;return;}
		if (i == 5){Status = (String)v;return;}
		if (i == 6){Type = (String)v;return;}
		if (i == 7){Email = (String)v;return;}
		if (i == 8){Tel = (String)v;return;}
		if (i == 9){Mobile = (String)v;return;}
		if (i == 10){Prop1 = (String)v;return;}
		if (i == 11){Prop2 = (String)v;return;}
		if (i == 12){Prop6 = (String)v;return;}
		if (i == 13){Prop5 = (String)v;return;}
		if (i == 14){Prop4 = (String)v;return;}
		if (i == 15){Prop3 = (String)v;return;}
		if (i == 16){Memo = (String)v;return;}
		if (i == 17){AddTime = (Date)v;return;}
		if (i == 18){AddUser = (String)v;return;}
		if (i == 19){ModifyTime = (Date)v;return;}
		if (i == 20){ModifyUser = (String)v;return;}
		if (i == 21){BackupNo = (String)v;return;}
		if (i == 22){BackupOperator = (String)v;return;}
		if (i == 23){BackupTime = (Date)v;return;}
		if (i == 24){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return UserName;}
		if (i == 1){return RealName;}
		if (i == 2){return Password;}
		if (i == 3){return BranchInnerCode;}
		if (i == 4){return IsBranchAdmin;}
		if (i == 5){return Status;}
		if (i == 6){return Type;}
		if (i == 7){return Email;}
		if (i == 8){return Tel;}
		if (i == 9){return Mobile;}
		if (i == 10){return Prop1;}
		if (i == 11){return Prop2;}
		if (i == 12){return Prop6;}
		if (i == 13){return Prop5;}
		if (i == 14){return Prop4;}
		if (i == 15){return Prop3;}
		if (i == 16){return Memo;}
		if (i == 17){return AddTime;}
		if (i == 18){return AddUser;}
		if (i == 19){return ModifyTime;}
		if (i == 20){return ModifyUser;}
		if (i == 21){return BackupNo;}
		if (i == 22){return BackupOperator;}
		if (i == 23){return BackupTime;}
		if (i == 24){return BackupMemo;}
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
	* ��ȡ�ֶ�RealName��ֵ�����ֶε�<br>
	* �ֶ����� :�û���ʵ����<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getRealName() {
		return RealName;
	}

	/**
	* �����ֶ�RealName��ֵ�����ֶε�<br>
	* �ֶ����� :�û���ʵ����<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setRealName(String realName) {
		this.RealName = realName;
    }

	/**
	* ��ȡ�ֶ�Password��ֵ�����ֶε�<br>
	* �ֶ����� :�û�����<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getPassword() {
		return Password;
	}

	/**
	* �����ֶ�Password��ֵ�����ֶε�<br>
	* �ֶ����� :�û�����<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setPassword(String password) {
		this.Password = password;
    }

	/**
	* ��ȡ�ֶ�BranchInnerCode��ֵ�����ֶε�<br>
	* �ֶ����� :�����ڲ�����<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getBranchInnerCode() {
		return BranchInnerCode;
	}

	/**
	* �����ֶ�BranchInnerCode��ֵ�����ֶε�<br>
	* �ֶ����� :�����ڲ�����<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setBranchInnerCode(String branchInnerCode) {
		this.BranchInnerCode = branchInnerCode;
    }

	/**
	* ��ȡ�ֶ�IsBranchAdmin��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ�Ϊ��������Ա<br>
	* �������� :varchar(1)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getIsBranchAdmin() {
		return IsBranchAdmin;
	}

	/**
	* �����ֶ�IsBranchAdmin��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ�Ϊ��������Ա<br>
	* �������� :varchar(1)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setIsBranchAdmin(String isBranchAdmin) {
		this.IsBranchAdmin = isBranchAdmin;
    }

	/**
	* ��ȡ�ֶ�Status��ֵ�����ֶε�<br>
	* �ֶ����� :�û�״̬<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getStatus() {
		return Status;
	}

	/**
	* �����ֶ�Status��ֵ�����ֶε�<br>
	* �ֶ����� :�û�״̬<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setStatus(String status) {
		this.Status = status;
    }

	/**
	* ��ȡ�ֶ�Type��ֵ�����ֶε�<br>
	* �ֶ����� :�û�����<br>
	* �������� :varchar(1)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	* ��ע��Ϣ :<br>
	1-����Ա<br>
	*/
	public String getType() {
		return Type;
	}

	/**
	* �����ֶ�Type��ֵ�����ֶε�<br>
	* �ֶ����� :�û�����<br>
	* �������� :varchar(1)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	* ��ע��Ϣ :<br>
	1-����Ա<br>
	*/
	public void setType(String type) {
		this.Type = type;
    }

	/**
	* ��ȡ�ֶ�Email��ֵ�����ֶε�<br>
	* �ֶ����� :�����ַ<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getEmail() {
		return Email;
	}

	/**
	* �����ֶ�Email��ֵ�����ֶε�<br>
	* �ֶ����� :�����ַ<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setEmail(String email) {
		this.Email = email;
    }

	/**
	* ��ȡ�ֶ�Tel��ֵ�����ֶε�<br>
	* �ֶ����� :��ϵ�绰<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getTel() {
		return Tel;
	}

	/**
	* �����ֶ�Tel��ֵ�����ֶε�<br>
	* �ֶ����� :��ϵ�绰<br>
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
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getMobile() {
		return Mobile;
	}

	/**
	* �����ֶ�Mobile��ֵ�����ֶε�<br>
	* �ֶ����� :�ֻ�<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setMobile(String mobile) {
		this.Mobile = mobile;
    }

	/**
	* ��ȡ�ֶ�Prop1��ֵ�����ֶε�<br>
	* �ֶ����� :��������1<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* �����ֶ�Prop1��ֵ�����ֶε�<br>
	* �ֶ����� :��������1<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* ��ȡ�ֶ�Prop2��ֵ�����ֶε�<br>
	* �ֶ����� :��������2<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* �����ֶ�Prop2��ֵ�����ֶε�<br>
	* �ֶ����� :��������2<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

	/**
	* ��ȡ�ֶ�Prop6��ֵ�����ֶε�<br>
	* �ֶ����� :��������3<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp6() {
		return Prop6;
	}

	/**
	* �����ֶ�Prop6��ֵ�����ֶε�<br>
	* �ֶ����� :��������3<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp6(String prop6) {
		this.Prop6 = prop6;
    }

	/**
	* ��ȡ�ֶ�Prop5��ֵ�����ֶε�<br>
	* �ֶ����� :��������4<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp5() {
		return Prop5;
	}

	/**
	* �����ֶ�Prop5��ֵ�����ֶε�<br>
	* �ֶ����� :��������4<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp5(String prop5) {
		this.Prop5 = prop5;
    }

	/**
	* ��ȡ�ֶ�Prop4��ֵ�����ֶε�<br>
	* �ֶ����� :��������5<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp4() {
		return Prop4;
	}

	/**
	* �����ֶ�Prop4��ֵ�����ֶε�<br>
	* �ֶ����� :��������5<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp4(String prop4) {
		this.Prop4 = prop4;
    }

	/**
	* ��ȡ�ֶ�Prop3��ֵ�����ֶε�<br>
	* �ֶ����� :��������6<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp3() {
		return Prop3;
	}

	/**
	* �����ֶ�Prop3��ֵ�����ֶε�<br>
	* �ֶ����� :��������6<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp3(String prop3) {
		this.Prop3 = prop3;
    }

	/**
	* ��ȡ�ֶ�Memo��ֵ�����ֶε�<br>
	* �ֶ����� :��ע<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getMemo() {
		return Memo;
	}

	/**
	* �����ֶ�Memo��ֵ�����ֶε�<br>
	* �ֶ����� :��ע<br>
	* �������� :varchar(100)<br>
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
	* ��ȡ�ֶ�AddUser��ֵ�����ֶε�<br>
	* �ֶ����� :������<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getAddUser() {
		return AddUser;
	}

	/**
	* �����ֶ�AddUser��ֵ�����ֶε�<br>
	* �ֶ����� :������<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setAddUser(String addUser) {
		this.AddUser = addUser;
    }

	/**
	* ��ȡ�ֶ�ModifyTime��ֵ�����ֶε�<br>
	* �ֶ����� :�޸�ʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public Date getModifyTime() {
		return ModifyTime;
	}

	/**
	* �����ֶ�ModifyTime��ֵ�����ֶε�<br>
	* �ֶ����� :�޸�ʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setModifyTime(Date modifyTime) {
		this.ModifyTime = modifyTime;
    }

	/**
	* ��ȡ�ֶ�ModifyUser��ֵ�����ֶε�<br>
	* �ֶ����� :�޸���<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getModifyUser() {
		return ModifyUser;
	}

	/**
	* �����ֶ�ModifyUser��ֵ�����ֶε�<br>
	* �ֶ����� :�޸���<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setModifyUser(String modifyUser) {
		this.ModifyUser = modifyUser;
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