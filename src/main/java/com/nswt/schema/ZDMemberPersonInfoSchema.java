package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;

/**
 * �����ƣ����˻�Ա��ϸ��Ϣ��<br>
 * ����룺ZDMemberPersonInfo<br>
 * ��������UserName<br>
 */
public class ZDMemberPersonInfoSchema extends Schema {
	private String UserName;

	private String NickName;

	private String Birthday;

	private String QQ;

	private String MSN;

	private String Tel;

	private String Mobile;

	private String Address;

	private String ZipCode;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("UserName", DataColumn.STRING, 0, 50 , 0 , true , true),
		new SchemaColumn("NickName", DataColumn.STRING, 1, 100 , 0 , false , false),
		new SchemaColumn("Birthday", DataColumn.STRING, 2, 20 , 0 , false , false),
		new SchemaColumn("QQ", DataColumn.STRING, 3, 10 , 0 , false , false),
		new SchemaColumn("MSN", DataColumn.STRING, 4, 50 , 0 , false , false),
		new SchemaColumn("Tel", DataColumn.STRING, 5, 20 , 0 , false , false),
		new SchemaColumn("Mobile", DataColumn.STRING, 6, 20 , 0 , false , false),
		new SchemaColumn("Address", DataColumn.STRING, 7, 100 , 0 , false , false),
		new SchemaColumn("ZipCode", DataColumn.STRING, 8, 10 , 0 , false , false)
	};

	public static final String _TableCode = "ZDMemberPersonInfo";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into ZDMemberPersonInfo values(?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZDMemberPersonInfo set UserName=?,NickName=?,Birthday=?,QQ=?,MSN=?,Tel=?,Mobile=?,Address=?,ZipCode=? where UserName=?";

	protected static final String _DeleteSQL = "delete from ZDMemberPersonInfo  where UserName=?";

	protected static final String _FillAllSQL = "select * from ZDMemberPersonInfo  where UserName=?";

	public ZDMemberPersonInfoSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[9];
	}

	protected Schema newInstance(){
		return new ZDMemberPersonInfoSchema();
	}

	protected SchemaSet newSet(){
		return new ZDMemberPersonInfoSet();
	}

	public ZDMemberPersonInfoSet query() {
		return query(null, -1, -1);
	}

	public ZDMemberPersonInfoSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZDMemberPersonInfoSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZDMemberPersonInfoSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZDMemberPersonInfoSet)querySet(qb , pageSize , pageIndex);
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

}