package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * �����ƣ���Ա��ַ����<br>
 * ����룺BZDMemberAddr<br>
 * ��������ID, BackupNo<br>
 */
public class BZDMemberAddrSchema extends Schema {
	private Long ID;

	private String UserName;

	private String RealName;

	private String Country;

	private String Province;

	private String City;

	private String District;

	private String Address;

	private String ZipCode;

	private String Tel;

	private String Mobile;

	private String Email;

	private String IsDefault;

	private String AddUser;

	private Date AddTime;

	private String ModifyUser;

	private Date ModifyTime;

	private String BackupNo;

	private String BackupOperator;

	private Date BackupTime;

	private String BackupMemo;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 20 , 0 , true , true),
		new SchemaColumn("UserName", DataColumn.STRING, 1, 200 , 0 , true , false),
		new SchemaColumn("RealName", DataColumn.STRING, 2, 100 , 0 , false , false),
		new SchemaColumn("Country", DataColumn.STRING, 3, 30 , 0 , false , false),
		new SchemaColumn("Province", DataColumn.STRING, 4, 6 , 0 , false , false),
		new SchemaColumn("City", DataColumn.STRING, 5, 6 , 0 , false , false),
		new SchemaColumn("District", DataColumn.STRING, 6, 6 , 0 , false , false),
		new SchemaColumn("Address", DataColumn.STRING, 7, 255 , 0 , false , false),
		new SchemaColumn("ZipCode", DataColumn.STRING, 8, 10 , 0 , false , false),
		new SchemaColumn("Tel", DataColumn.STRING, 9, 20 , 0 , false , false),
		new SchemaColumn("Mobile", DataColumn.STRING, 10, 20 , 0 , false , false),
		new SchemaColumn("Email", DataColumn.STRING, 11, 100 , 0 , false , false),
		new SchemaColumn("IsDefault", DataColumn.STRING, 12, 2 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 13, 200 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 14, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 15, 200 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 16, 0 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 17, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 18, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 19, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 20, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZDMemberAddr";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into BZDMemberAddr values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZDMemberAddr set ID=?,UserName=?,RealName=?,Country=?,Province=?,City=?,District=?,Address=?,ZipCode=?,Tel=?,Mobile=?,Email=?,IsDefault=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZDMemberAddr  where ID=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZDMemberAddr  where ID=? and BackupNo=?";

	public BZDMemberAddrSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[21];
	}

	protected Schema newInstance(){
		return new BZDMemberAddrSchema();
	}

	protected SchemaSet newSet(){
		return new BZDMemberAddrSet();
	}

	public BZDMemberAddrSet query() {
		return query(null, -1, -1);
	}

	public BZDMemberAddrSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZDMemberAddrSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZDMemberAddrSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZDMemberAddrSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){UserName = (String)v;return;}
		if (i == 2){RealName = (String)v;return;}
		if (i == 3){Country = (String)v;return;}
		if (i == 4){Province = (String)v;return;}
		if (i == 5){City = (String)v;return;}
		if (i == 6){District = (String)v;return;}
		if (i == 7){Address = (String)v;return;}
		if (i == 8){ZipCode = (String)v;return;}
		if (i == 9){Tel = (String)v;return;}
		if (i == 10){Mobile = (String)v;return;}
		if (i == 11){Email = (String)v;return;}
		if (i == 12){IsDefault = (String)v;return;}
		if (i == 13){AddUser = (String)v;return;}
		if (i == 14){AddTime = (Date)v;return;}
		if (i == 15){ModifyUser = (String)v;return;}
		if (i == 16){ModifyTime = (Date)v;return;}
		if (i == 17){BackupNo = (String)v;return;}
		if (i == 18){BackupOperator = (String)v;return;}
		if (i == 19){BackupTime = (Date)v;return;}
		if (i == 20){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return UserName;}
		if (i == 2){return RealName;}
		if (i == 3){return Country;}
		if (i == 4){return Province;}
		if (i == 5){return City;}
		if (i == 6){return District;}
		if (i == 7){return Address;}
		if (i == 8){return ZipCode;}
		if (i == 9){return Tel;}
		if (i == 10){return Mobile;}
		if (i == 11){return Email;}
		if (i == 12){return IsDefault;}
		if (i == 13){return AddUser;}
		if (i == 14){return AddTime;}
		if (i == 15){return ModifyUser;}
		if (i == 16){return ModifyTime;}
		if (i == 17){return BackupNo;}
		if (i == 18){return BackupOperator;}
		if (i == 19){return BackupTime;}
		if (i == 20){return BackupMemo;}
		return null;
	}

	/**
	* ��ȡ�ֶ�ID��ֵ�����ֶε�<br>
	* �ֶ����� :��ַID<br>
	* �������� :bigint(20)<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public long getID() {
		if(ID==null){return 0;}
		return ID.longValue();
	}

	/**
	* �����ֶ�ID��ֵ�����ֶε�<br>
	* �ֶ����� :��ַID<br>
	* �������� :bigint(20)<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setID(long iD) {
		this.ID = new Long(iD);
    }

	/**
	* �����ֶ�ID��ֵ�����ֶε�<br>
	* �ֶ����� :��ַID<br>
	* �������� :bigint(20)<br>
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
	* ��ȡ�ֶ�UserName��ֵ�����ֶε�<br>
	* �ֶ����� :������Ա<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getUserName() {
		return UserName;
	}

	/**
	* �����ֶ�UserName��ֵ�����ֶε�<br>
	* �ֶ����� :������Ա<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setUserName(String userName) {
		this.UserName = userName;
    }

	/**
	* ��ȡ�ֶ�RealName��ֵ�����ֶε�<br>
	* �ֶ����� :�ջ�������<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getRealName() {
		return RealName;
	}

	/**
	* �����ֶ�RealName��ֵ�����ֶε�<br>
	* �ֶ����� :�ջ�������<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setRealName(String realName) {
		this.RealName = realName;
    }

	/**
	* ��ȡ�ֶ�Country��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :varchar(30)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getCountry() {
		return Country;
	}

	/**
	* �����ֶ�Country��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :varchar(30)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCountry(String country) {
		this.Country = country;
    }

	/**
	* ��ȡ�ֶ�Province��ֵ�����ֶε�<br>
	* �ֶ����� :ʡ��<br>
	* �������� :varchar(6)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProvince() {
		return Province;
	}

	/**
	* �����ֶ�Province��ֵ�����ֶε�<br>
	* �ֶ����� :ʡ��<br>
	* �������� :varchar(6)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProvince(String province) {
		this.Province = province;
    }

	/**
	* ��ȡ�ֶ�City��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :varchar(6)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getCity() {
		return City;
	}

	/**
	* �����ֶ�City��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :varchar(6)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCity(String city) {
		this.City = city;
    }

	/**
	* ��ȡ�ֶ�District��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :varchar(6)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getDistrict() {
		return District;
	}

	/**
	* �����ֶ�District��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :varchar(6)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setDistrict(String district) {
		this.District = district;
    }

	/**
	* ��ȡ�ֶ�Address��ֵ�����ֶε�<br>
	* �ֶ����� :��ϸ��ַ<br>
	* �������� :varchar(255)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getAddress() {
		return Address;
	}

	/**
	* �����ֶ�Address��ֵ�����ֶε�<br>
	* �ֶ����� :��ϸ��ַ<br>
	* �������� :varchar(255)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAddress(String address) {
		this.Address = address;
    }

	/**
	* ��ȡ�ֶ�ZipCode��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :varchar(10)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getZipCode() {
		return ZipCode;
	}

	/**
	* �����ֶ�ZipCode��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :varchar(10)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setZipCode(String zipCode) {
		this.ZipCode = zipCode;
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
	* ��ȡ�ֶ�Email��ֵ�����ֶε�<br>
	* �ֶ����� :�����ʼ�<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getEmail() {
		return Email;
	}

	/**
	* �����ֶ�Email��ֵ�����ֶε�<br>
	* �ֶ����� :�����ʼ�<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setEmail(String email) {
		this.Email = email;
    }

	/**
	* ��ȡ�ֶ�IsDefault��ֵ�����ֶε�<br>
	* �ֶ����� :Ĭ��<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getIsDefault() {
		return IsDefault;
	}

	/**
	* �����ֶ�IsDefault��ֵ�����ֶε�<br>
	* �ֶ����� :Ĭ��<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setIsDefault(String isDefault) {
		this.IsDefault = isDefault;
    }

	/**
	* ��ȡ�ֶ�AddUser��ֵ�����ֶε�<br>
	* �ֶ����� :�����<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getAddUser() {
		return AddUser;
	}

	/**
	* �����ֶ�AddUser��ֵ�����ֶε�<br>
	* �ֶ����� :�����<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setAddUser(String addUser) {
		this.AddUser = addUser;
    }

	/**
	* ��ȡ�ֶ�AddTime��ֵ�����ֶε�<br>
	* �ֶ����� :���ʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public Date getAddTime() {
		return AddTime;
	}

	/**
	* �����ֶ�AddTime��ֵ�����ֶε�<br>
	* �ֶ����� :���ʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setAddTime(Date addTime) {
		this.AddTime = addTime;
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