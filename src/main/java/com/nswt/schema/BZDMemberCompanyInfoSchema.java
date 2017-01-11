package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * �����ƣ���ҵ��Ա��ϸ��Ϣ����<br>
 * ����룺BZDMemberCompanyInfo<br>
 * ��������UserName, BackupNo<br>
 */
public class BZDMemberCompanyInfoSchema extends Schema {
	private String UserName;

	private String CompanyName;

	private String Scale;

	private String BusinessType;

	private String Products;

	private String CompanySite;

	private String Tel;

	private String Fax;

	private String LinkMan;

	private String Mobile;

	private String Email;

	private String Address;

	private String ZipCode;

	private String Pic;

	private String Intro;

	private String BackupNo;

	private String BackupOperator;

	private Date BackupTime;

	private String BackupMemo;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("UserName", DataColumn.STRING, 0, 50 , 0 , true , true),
		new SchemaColumn("CompanyName", DataColumn.STRING, 1, 100 , 0 , false , false),
		new SchemaColumn("Scale", DataColumn.STRING, 2, 10 , 0 , false , false),
		new SchemaColumn("BusinessType", DataColumn.STRING, 3, 10 , 0 , false , false),
		new SchemaColumn("Products", DataColumn.STRING, 4, 50 , 0 , false , false),
		new SchemaColumn("CompanySite", DataColumn.STRING, 5, 50 , 0 , false , false),
		new SchemaColumn("Tel", DataColumn.STRING, 6, 20 , 0 , false , false),
		new SchemaColumn("Fax", DataColumn.STRING, 7, 20 , 0 , false , false),
		new SchemaColumn("LinkMan", DataColumn.STRING, 8, 20 , 0 , false , false),
		new SchemaColumn("Mobile", DataColumn.STRING, 9, 20 , 0 , false , false),
		new SchemaColumn("Email", DataColumn.STRING, 10, 50 , 0 , false , false),
		new SchemaColumn("Address", DataColumn.STRING, 11, 100 , 0 , false , false),
		new SchemaColumn("ZipCode", DataColumn.STRING, 12, 10 , 0 , false , false),
		new SchemaColumn("Pic", DataColumn.STRING, 13, 100 , 0 , false , false),
		new SchemaColumn("Intro", DataColumn.CLOB, 14, 0 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 15, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 16, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 17, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 18, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZDMemberCompanyInfo";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into BZDMemberCompanyInfo values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZDMemberCompanyInfo set UserName=?,CompanyName=?,Scale=?,BusinessType=?,Products=?,CompanySite=?,Tel=?,Fax=?,LinkMan=?,Mobile=?,Email=?,Address=?,ZipCode=?,Pic=?,Intro=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where UserName=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZDMemberCompanyInfo  where UserName=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZDMemberCompanyInfo  where UserName=? and BackupNo=?";

	public BZDMemberCompanyInfoSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[19];
	}

	protected Schema newInstance(){
		return new BZDMemberCompanyInfoSchema();
	}

	protected SchemaSet newSet(){
		return new BZDMemberCompanyInfoSet();
	}

	public BZDMemberCompanyInfoSet query() {
		return query(null, -1, -1);
	}

	public BZDMemberCompanyInfoSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZDMemberCompanyInfoSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZDMemberCompanyInfoSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZDMemberCompanyInfoSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){UserName = (String)v;return;}
		if (i == 1){CompanyName = (String)v;return;}
		if (i == 2){Scale = (String)v;return;}
		if (i == 3){BusinessType = (String)v;return;}
		if (i == 4){Products = (String)v;return;}
		if (i == 5){CompanySite = (String)v;return;}
		if (i == 6){Tel = (String)v;return;}
		if (i == 7){Fax = (String)v;return;}
		if (i == 8){LinkMan = (String)v;return;}
		if (i == 9){Mobile = (String)v;return;}
		if (i == 10){Email = (String)v;return;}
		if (i == 11){Address = (String)v;return;}
		if (i == 12){ZipCode = (String)v;return;}
		if (i == 13){Pic = (String)v;return;}
		if (i == 14){Intro = (String)v;return;}
		if (i == 15){BackupNo = (String)v;return;}
		if (i == 16){BackupOperator = (String)v;return;}
		if (i == 17){BackupTime = (Date)v;return;}
		if (i == 18){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return UserName;}
		if (i == 1){return CompanyName;}
		if (i == 2){return Scale;}
		if (i == 3){return BusinessType;}
		if (i == 4){return Products;}
		if (i == 5){return CompanySite;}
		if (i == 6){return Tel;}
		if (i == 7){return Fax;}
		if (i == 8){return LinkMan;}
		if (i == 9){return Mobile;}
		if (i == 10){return Email;}
		if (i == 11){return Address;}
		if (i == 12){return ZipCode;}
		if (i == 13){return Pic;}
		if (i == 14){return Intro;}
		if (i == 15){return BackupNo;}
		if (i == 16){return BackupOperator;}
		if (i == 17){return BackupTime;}
		if (i == 18){return BackupMemo;}
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
	* ��ȡ�ֶ�CompanyName��ֵ�����ֶε�<br>
	* �ֶ����� :��˾����<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getCompanyName() {
		return CompanyName;
	}

	/**
	* �����ֶ�CompanyName��ֵ�����ֶε�<br>
	* �ֶ����� :��˾����<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCompanyName(String companyName) {
		this.CompanyName = companyName;
    }

	/**
	* ��ȡ�ֶ�Scale��ֵ�����ֶε�<br>
	* �ֶ����� :��˾��ģ<br>
	* �������� :varchar(10)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getScale() {
		return Scale;
	}

	/**
	* �����ֶ�Scale��ֵ�����ֶε�<br>
	* �ֶ����� :��˾��ģ<br>
	* �������� :varchar(10)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setScale(String scale) {
		this.Scale = scale;
    }

	/**
	* ��ȡ�ֶ�BusinessType��ֵ�����ֶε�<br>
	* �ֶ����� :������ҵ<br>
	* �������� :varchar(10)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getBusinessType() {
		return BusinessType;
	}

	/**
	* �����ֶ�BusinessType��ֵ�����ֶε�<br>
	* �ֶ����� :������ҵ<br>
	* �������� :varchar(10)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setBusinessType(String businessType) {
		this.BusinessType = businessType;
    }

	/**
	* ��ȡ�ֶ�Products��ֵ�����ֶε�<br>
	* �ֶ����� :��Ӫ��Ʒ<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProducts() {
		return Products;
	}

	/**
	* �����ֶ�Products��ֵ�����ֶε�<br>
	* �ֶ����� :��Ӫ��Ʒ<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProducts(String products) {
		this.Products = products;
    }

	/**
	* ��ȡ�ֶ�CompanySite��ֵ�����ֶε�<br>
	* �ֶ����� :��˾��ַ<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getCompanySite() {
		return CompanySite;
	}

	/**
	* �����ֶ�CompanySite��ֵ�����ֶε�<br>
	* �ֶ����� :��˾��ַ<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCompanySite(String companySite) {
		this.CompanySite = companySite;
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
	* ��ȡ�ֶ�Fax��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getFax() {
		return Fax;
	}

	/**
	* �����ֶ�Fax��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setFax(String fax) {
		this.Fax = fax;
    }

	/**
	* ��ȡ�ֶ�LinkMan��ֵ�����ֶε�<br>
	* �ֶ����� :��ϵ��<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getLinkMan() {
		return LinkMan;
	}

	/**
	* �����ֶ�LinkMan��ֵ�����ֶε�<br>
	* �ֶ����� :��ϵ��<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setLinkMan(String linkMan) {
		this.LinkMan = linkMan;
    }

	/**
	* ��ȡ�ֶ�Mobile��ֵ�����ֶε�<br>
	* �ֶ����� :�ƶ��绰<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getMobile() {
		return Mobile;
	}

	/**
	* �����ֶ�Mobile��ֵ�����ֶε�<br>
	* �ֶ����� :�ƶ��绰<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setMobile(String mobile) {
		this.Mobile = mobile;
    }

	/**
	* ��ȡ�ֶ�Email��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getEmail() {
		return Email;
	}

	/**
	* �����ֶ�Email��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setEmail(String email) {
		this.Email = email;
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
	* ��ȡ�ֶ�Pic��ֵ�����ֶε�<br>
	* �ֶ����� :����ͼƬ<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getPic() {
		return Pic;
	}

	/**
	* �����ֶ�Pic��ֵ�����ֶε�<br>
	* �ֶ����� :����ͼƬ<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setPic(String pic) {
		this.Pic = pic;
    }

	/**
	* ��ȡ�ֶ�Intro��ֵ�����ֶε�<br>
	* �ֶ����� :��˾���<br>
	* �������� :text<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getIntro() {
		return Intro;
	}

	/**
	* �����ֶ�Intro��ֵ�����ֶε�<br>
	* �ֶ����� :��˾���<br>
	* �������� :text<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setIntro(String intro) {
		this.Intro = intro;
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