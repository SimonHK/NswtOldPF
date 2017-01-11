package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * �����ƣ������˱���<br>
 * ����룺BZCApply<br>
 * ��������ID, BackupNo<br>
 */
public class BZCApplySchema extends Schema {
	private Long ID;

	private Long SiteID;

	private String Name;

	private String Gender;

	private Date BirthDate;

	private String Picture;

	private String Ethnicity;

	private String NativePlace;

	private String Political;

	private String CertNumber;

	private String Phone;

	private String Mobile;

	private String Address;

	private String Postcode;

	private String Email;

	private String ForeignLanguage;

	private String LanguageLevel;

	private String Authentification;

	private String PersonIntro;

	private String Honour;

	private String PracticeExperience;

	private String RegisteredPlace;

	private String EduLevel;

	private String University;

	private String Speacility;

	private String WillPosition;

	private String AuditUser;

	private String AuditStatus;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	private String Prop4;

	private Date AddTime;

	private String AddUser;

	private Date ModifyTime;

	private String ModifyUser;

	private String BackupNo;

	private String BackupOperator;

	private Date BackupTime;

	private String BackupMemo;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 0 , 0 , true , true),
		new SchemaColumn("SiteID", DataColumn.LONG, 1, 0 , 0 , false , false),
		new SchemaColumn("Name", DataColumn.STRING, 2, 25 , 0 , false , false),
		new SchemaColumn("Gender", DataColumn.STRING, 3, 1 , 0 , false , false),
		new SchemaColumn("BirthDate", DataColumn.DATETIME, 4, 0 , 0 , false , false),
		new SchemaColumn("Picture", DataColumn.STRING, 5, 50 , 0 , false , false),
		new SchemaColumn("Ethnicity", DataColumn.STRING, 6, 3 , 0 , false , false),
		new SchemaColumn("NativePlace", DataColumn.STRING, 7, 10 , 0 , false , false),
		new SchemaColumn("Political", DataColumn.STRING, 8, 3 , 0 , false , false),
		new SchemaColumn("CertNumber", DataColumn.STRING, 9, 20 , 0 , false , false),
		new SchemaColumn("Phone", DataColumn.STRING, 10, 20 , 0 , false , false),
		new SchemaColumn("Mobile", DataColumn.STRING, 11, 20 , 0 , false , false),
		new SchemaColumn("Address", DataColumn.STRING, 12, 200 , 0 , false , false),
		new SchemaColumn("Postcode", DataColumn.STRING, 13, 10 , 0 , false , false),
		new SchemaColumn("Email", DataColumn.STRING, 14, 100 , 0 , false , false),
		new SchemaColumn("ForeignLanguage", DataColumn.STRING, 15, 50 , 0 , false , false),
		new SchemaColumn("LanguageLevel", DataColumn.STRING, 16, 50 , 0 , false , false),
		new SchemaColumn("Authentification", DataColumn.STRING, 17, 200 , 0 , false , false),
		new SchemaColumn("PersonIntro", DataColumn.STRING, 18, 1500 , 0 , false , false),
		new SchemaColumn("Honour", DataColumn.STRING, 19, 1500 , 0 , false , false),
		new SchemaColumn("PracticeExperience", DataColumn.STRING, 20, 2000 , 0 , false , false),
		new SchemaColumn("RegisteredPlace", DataColumn.STRING, 21, 10 , 0 , false , false),
		new SchemaColumn("EduLevel", DataColumn.STRING, 22, 3 , 0 , false , false),
		new SchemaColumn("University", DataColumn.STRING, 23, 40 , 0 , false , false),
		new SchemaColumn("Speacility", DataColumn.STRING, 24, 100 , 0 , false , false),
		new SchemaColumn("WillPosition", DataColumn.STRING, 25, 50 , 0 , false , false),
		new SchemaColumn("AuditUser", DataColumn.STRING, 26, 50 , 0 , false , false),
		new SchemaColumn("AuditStatus", DataColumn.STRING, 27, 5 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 28, 100 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 29, 100 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 30, 100 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 31, 100 , 0 , false , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 32, 0 , 0 , true , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 33, 100 , 0 , true , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 34, 0 , 0 , false , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 35, 100 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 36, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 37, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 38, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 39, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZCApply";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into BZCApply values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZCApply set ID=?,SiteID=?,Name=?,Gender=?,BirthDate=?,Picture=?,Ethnicity=?,NativePlace=?,Political=?,CertNumber=?,Phone=?,Mobile=?,Address=?,Postcode=?,Email=?,ForeignLanguage=?,LanguageLevel=?,Authentification=?,PersonIntro=?,Honour=?,PracticeExperience=?,RegisteredPlace=?,EduLevel=?,University=?,Speacility=?,WillPosition=?,AuditUser=?,AuditStatus=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddTime=?,AddUser=?,ModifyTime=?,ModifyUser=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZCApply  where ID=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZCApply  where ID=? and BackupNo=?";

	public BZCApplySchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[40];
	}

	protected Schema newInstance(){
		return new BZCApplySchema();
	}

	protected SchemaSet newSet(){
		return new BZCApplySet();
	}

	public BZCApplySet query() {
		return query(null, -1, -1);
	}

	public BZCApplySet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZCApplySet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZCApplySet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZCApplySet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 2){Name = (String)v;return;}
		if (i == 3){Gender = (String)v;return;}
		if (i == 4){BirthDate = (Date)v;return;}
		if (i == 5){Picture = (String)v;return;}
		if (i == 6){Ethnicity = (String)v;return;}
		if (i == 7){NativePlace = (String)v;return;}
		if (i == 8){Political = (String)v;return;}
		if (i == 9){CertNumber = (String)v;return;}
		if (i == 10){Phone = (String)v;return;}
		if (i == 11){Mobile = (String)v;return;}
		if (i == 12){Address = (String)v;return;}
		if (i == 13){Postcode = (String)v;return;}
		if (i == 14){Email = (String)v;return;}
		if (i == 15){ForeignLanguage = (String)v;return;}
		if (i == 16){LanguageLevel = (String)v;return;}
		if (i == 17){Authentification = (String)v;return;}
		if (i == 18){PersonIntro = (String)v;return;}
		if (i == 19){Honour = (String)v;return;}
		if (i == 20){PracticeExperience = (String)v;return;}
		if (i == 21){RegisteredPlace = (String)v;return;}
		if (i == 22){EduLevel = (String)v;return;}
		if (i == 23){University = (String)v;return;}
		if (i == 24){Speacility = (String)v;return;}
		if (i == 25){WillPosition = (String)v;return;}
		if (i == 26){AuditUser = (String)v;return;}
		if (i == 27){AuditStatus = (String)v;return;}
		if (i == 28){Prop1 = (String)v;return;}
		if (i == 29){Prop2 = (String)v;return;}
		if (i == 30){Prop3 = (String)v;return;}
		if (i == 31){Prop4 = (String)v;return;}
		if (i == 32){AddTime = (Date)v;return;}
		if (i == 33){AddUser = (String)v;return;}
		if (i == 34){ModifyTime = (Date)v;return;}
		if (i == 35){ModifyUser = (String)v;return;}
		if (i == 36){BackupNo = (String)v;return;}
		if (i == 37){BackupOperator = (String)v;return;}
		if (i == 38){BackupTime = (Date)v;return;}
		if (i == 39){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return SiteID;}
		if (i == 2){return Name;}
		if (i == 3){return Gender;}
		if (i == 4){return BirthDate;}
		if (i == 5){return Picture;}
		if (i == 6){return Ethnicity;}
		if (i == 7){return NativePlace;}
		if (i == 8){return Political;}
		if (i == 9){return CertNumber;}
		if (i == 10){return Phone;}
		if (i == 11){return Mobile;}
		if (i == 12){return Address;}
		if (i == 13){return Postcode;}
		if (i == 14){return Email;}
		if (i == 15){return ForeignLanguage;}
		if (i == 16){return LanguageLevel;}
		if (i == 17){return Authentification;}
		if (i == 18){return PersonIntro;}
		if (i == 19){return Honour;}
		if (i == 20){return PracticeExperience;}
		if (i == 21){return RegisteredPlace;}
		if (i == 22){return EduLevel;}
		if (i == 23){return University;}
		if (i == 24){return Speacility;}
		if (i == 25){return WillPosition;}
		if (i == 26){return AuditUser;}
		if (i == 27){return AuditStatus;}
		if (i == 28){return Prop1;}
		if (i == 29){return Prop2;}
		if (i == 30){return Prop3;}
		if (i == 31){return Prop4;}
		if (i == 32){return AddTime;}
		if (i == 33){return AddUser;}
		if (i == 34){return ModifyTime;}
		if (i == 35){return ModifyUser;}
		if (i == 36){return BackupNo;}
		if (i == 37){return BackupOperator;}
		if (i == 38){return BackupTime;}
		if (i == 39){return BackupMemo;}
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
	* ��ȡ�ֶ�SiteID��ֵ�����ֶε�<br>
	* �ֶ����� :վ��ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getSiteID() {
		if(SiteID==null){return 0;}
		return SiteID.longValue();
	}

	/**
	* �����ֶ�SiteID��ֵ�����ֶε�<br>
	* �ֶ����� :վ��ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setSiteID(long siteID) {
		this.SiteID = new Long(siteID);
    }

	/**
	* �����ֶ�SiteID��ֵ�����ֶε�<br>
	* �ֶ����� :վ��ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setSiteID(String siteID) {
		if (siteID == null){
			this.SiteID = null;
			return;
		}
		this.SiteID = new Long(siteID);
    }

	/**
	* ��ȡ�ֶ�Name��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :varchar(25)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getName() {
		return Name;
	}

	/**
	* �����ֶ�Name��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :varchar(25)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setName(String name) {
		this.Name = name;
    }

	/**
	* ��ȡ�ֶ�Gender��ֵ�����ֶε�<br>
	* �ֶ����� :�Ա�<br>
	* �������� :varchar(1)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getGender() {
		return Gender;
	}

	/**
	* �����ֶ�Gender��ֵ�����ֶε�<br>
	* �ֶ����� :�Ա�<br>
	* �������� :varchar(1)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setGender(String gender) {
		this.Gender = gender;
    }

	/**
	* ��ȡ�ֶ�BirthDate��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public Date getBirthDate() {
		return BirthDate;
	}

	/**
	* �����ֶ�BirthDate��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setBirthDate(Date birthDate) {
		this.BirthDate = birthDate;
    }

	/**
	* ��ȡ�ֶ�Picture��ֵ�����ֶε�<br>
	* �ֶ����� :ͷ����Ƭ<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getPicture() {
		return Picture;
	}

	/**
	* �����ֶ�Picture��ֵ�����ֶε�<br>
	* �ֶ����� :ͷ����Ƭ<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setPicture(String picture) {
		this.Picture = picture;
    }

	/**
	* ��ȡ�ֶ�Ethnicity��ֵ�����ֶε�<br>
	* �ֶ����� :�������<br>
	* �������� :varchar(3)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getEthnicity() {
		return Ethnicity;
	}

	/**
	* �����ֶ�Ethnicity��ֵ�����ֶε�<br>
	* �ֶ����� :�������<br>
	* �������� :varchar(3)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setEthnicity(String ethnicity) {
		this.Ethnicity = ethnicity;
    }

	/**
	* ��ȡ�ֶ�NativePlace��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :varchar(10)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getNativePlace() {
		return NativePlace;
	}

	/**
	* �����ֶ�NativePlace��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :varchar(10)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setNativePlace(String nativePlace) {
		this.NativePlace = nativePlace;
    }

	/**
	* ��ȡ�ֶ�Political��ֵ�����ֶε�<br>
	* �ֶ����� :������ò����<br>
	* �������� :varchar(3)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getPolitical() {
		return Political;
	}

	/**
	* �����ֶ�Political��ֵ�����ֶε�<br>
	* �ֶ����� :������ò����<br>
	* �������� :varchar(3)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setPolitical(String political) {
		this.Political = political;
    }

	/**
	* ��ȡ�ֶ�CertNumber��ֵ�����ֶε�<br>
	* �ֶ����� :���֤����<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getCertNumber() {
		return CertNumber;
	}

	/**
	* �����ֶ�CertNumber��ֵ�����ֶε�<br>
	* �ֶ����� :���֤����<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCertNumber(String certNumber) {
		this.CertNumber = certNumber;
    }

	/**
	* ��ȡ�ֶ�Phone��ֵ�����ֶε�<br>
	* �ֶ����� :�̶��绰<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getPhone() {
		return Phone;
	}

	/**
	* �����ֶ�Phone��ֵ�����ֶε�<br>
	* �ֶ����� :�̶��绰<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setPhone(String phone) {
		this.Phone = phone;
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
	* ��ȡ�ֶ�Address��ֵ�����ֶε�<br>
	* �ֶ����� :��ϵ��ַ<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getAddress() {
		return Address;
	}

	/**
	* �����ֶ�Address��ֵ�����ֶε�<br>
	* �ֶ����� :��ϵ��ַ<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAddress(String address) {
		this.Address = address;
    }

	/**
	* ��ȡ�ֶ�Postcode��ֵ�����ֶε�<br>
	* �ֶ����� :�ʱ�<br>
	* �������� :varchar(10)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getPostcode() {
		return Postcode;
	}

	/**
	* �����ֶ�Postcode��ֵ�����ֶε�<br>
	* �ֶ����� :�ʱ�<br>
	* �������� :varchar(10)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setPostcode(String postcode) {
		this.Postcode = postcode;
    }

	/**
	* ��ȡ�ֶ�Email��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getEmail() {
		return Email;
	}

	/**
	* �����ֶ�Email��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setEmail(String email) {
		this.Email = email;
    }

	/**
	* ��ȡ�ֶ�ForeignLanguage��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getForeignLanguage() {
		return ForeignLanguage;
	}

	/**
	* �����ֶ�ForeignLanguage��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setForeignLanguage(String foreignLanguage) {
		this.ForeignLanguage = foreignLanguage;
    }

	/**
	* ��ȡ�ֶ�LanguageLevel��ֵ�����ֶε�<br>
	* �ֶ����� :����ˮƽ<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getLanguageLevel() {
		return LanguageLevel;
	}

	/**
	* �����ֶ�LanguageLevel��ֵ�����ֶε�<br>
	* �ֶ����� :����ˮƽ<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setLanguageLevel(String languageLevel) {
		this.LanguageLevel = languageLevel;
    }

	/**
	* ��ȡ�ֶ�Authentification��ֵ�����ֶε�<br>
	* �ֶ����� :�ʸ���֤<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getAuthentification() {
		return Authentification;
	}

	/**
	* �����ֶ�Authentification��ֵ�����ֶε�<br>
	* �ֶ����� :�ʸ���֤<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAuthentification(String authentification) {
		this.Authentification = authentification;
    }

	/**
	* ��ȡ�ֶ�PersonIntro��ֵ�����ֶε�<br>
	* �ֶ����� :���˼��<br>
	* �������� :varchar(1500)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getPersonIntro() {
		return PersonIntro;
	}

	/**
	* �����ֶ�PersonIntro��ֵ�����ֶε�<br>
	* �ֶ����� :���˼��<br>
	* �������� :varchar(1500)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setPersonIntro(String personIntro) {
		this.PersonIntro = personIntro;
    }

	/**
	* ��ȡ�ֶ�Honour��ֵ�����ֶε�<br>
	* �ֶ����� :�����<br>
	* �������� :varchar(1500)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getHonour() {
		return Honour;
	}

	/**
	* �����ֶ�Honour��ֵ�����ֶε�<br>
	* �ֶ����� :�����<br>
	* �������� :varchar(1500)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setHonour(String honour) {
		this.Honour = honour;
    }

	/**
	* ��ȡ�ֶ�PracticeExperience��ֵ�����ֶε�<br>
	* �ֶ����� :ʵϰ����<br>
	* �������� :varchar(2000)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getPracticeExperience() {
		return PracticeExperience;
	}

	/**
	* �����ֶ�PracticeExperience��ֵ�����ֶε�<br>
	* �ֶ����� :ʵϰ����<br>
	* �������� :varchar(2000)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setPracticeExperience(String practiceExperience) {
		this.PracticeExperience = practiceExperience;
    }

	/**
	* ��ȡ�ֶ�RegisteredPlace��ֵ�����ֶε�<br>
	* �ֶ����� :�������ڵ�<br>
	* �������� :varchar(10)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getRegisteredPlace() {
		return RegisteredPlace;
	}

	/**
	* �����ֶ�RegisteredPlace��ֵ�����ֶε�<br>
	* �ֶ����� :�������ڵ�<br>
	* �������� :varchar(10)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setRegisteredPlace(String registeredPlace) {
		this.RegisteredPlace = registeredPlace;
    }

	/**
	* ��ȡ�ֶ�EduLevel��ֵ�����ֶε�<br>
	* �ֶ����� :ѧ��<br>
	* �������� :varchar(3)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getEduLevel() {
		return EduLevel;
	}

	/**
	* �����ֶ�EduLevel��ֵ�����ֶε�<br>
	* �ֶ����� :ѧ��<br>
	* �������� :varchar(3)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setEduLevel(String eduLevel) {
		this.EduLevel = eduLevel;
    }

	/**
	* ��ȡ�ֶ�University��ֵ�����ֶε�<br>
	* �ֶ����� :ѧУ<br>
	* �������� :varchar(40)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getUniversity() {
		return University;
	}

	/**
	* �����ֶ�University��ֵ�����ֶε�<br>
	* �ֶ����� :ѧУ<br>
	* �������� :varchar(40)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setUniversity(String university) {
		this.University = university;
    }

	/**
	* ��ȡ�ֶ�Speacility��ֵ�����ֶε�<br>
	* �ֶ����� :רҵ<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getSpeacility() {
		return Speacility;
	}

	/**
	* �����ֶ�Speacility��ֵ�����ֶε�<br>
	* �ֶ����� :רҵ<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setSpeacility(String speacility) {
		this.Speacility = speacility;
    }

	/**
	* ��ȡ�ֶ�WillPosition��ֵ�����ֶε�<br>
	* �ֶ����� :ӦƸ��λ<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getWillPosition() {
		return WillPosition;
	}

	/**
	* �����ֶ�WillPosition��ֵ�����ֶε�<br>
	* �ֶ����� :ӦƸ��λ<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setWillPosition(String willPosition) {
		this.WillPosition = willPosition;
    }

	/**
	* ��ȡ�ֶ�AuditUser��ֵ�����ֶε�<br>
	* �ֶ����� :�����<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getAuditUser() {
		return AuditUser;
	}

	/**
	* �����ֶ�AuditUser��ֵ�����ֶε�<br>
	* �ֶ����� :�����<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAuditUser(String auditUser) {
		this.AuditUser = auditUser;
    }

	/**
	* ��ȡ�ֶ�AuditStatus��ֵ�����ֶε�<br>
	* �ֶ����� :���״̬<br>
	* �������� :varchar(5)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getAuditStatus() {
		return AuditStatus;
	}

	/**
	* �����ֶ�AuditStatus��ֵ�����ֶε�<br>
	* �ֶ����� :���״̬<br>
	* �������� :varchar(5)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAuditStatus(String auditStatus) {
		this.AuditStatus = auditStatus;
    }

	/**
	* ��ȡ�ֶ�Prop1��ֵ�����ֶε�<br>
	* �ֶ����� :��������1<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* �����ֶ�Prop1��ֵ�����ֶε�<br>
	* �ֶ����� :��������1<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* ��ȡ�ֶ�Prop2��ֵ�����ֶε�<br>
	* �ֶ����� :��������2<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* �����ֶ�Prop2��ֵ�����ֶε�<br>
	* �ֶ����� :��������2<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

	/**
	* ��ȡ�ֶ�Prop3��ֵ�����ֶε�<br>
	* �ֶ����� :��������3<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp3() {
		return Prop3;
	}

	/**
	* �����ֶ�Prop3��ֵ�����ֶε�<br>
	* �ֶ����� :��������3<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp3(String prop3) {
		this.Prop3 = prop3;
    }

	/**
	* ��ȡ�ֶ�Prop4��ֵ�����ֶε�<br>
	* �ֶ����� :��������4<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp4() {
		return Prop4;
	}

	/**
	* �����ֶ�Prop4��ֵ�����ֶε�<br>
	* �ֶ����� :��������4<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp4(String prop4) {
		this.Prop4 = prop4;
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
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getAddUser() {
		return AddUser;
	}

	/**
	* �����ֶ�AddUser��ֵ�����ֶε�<br>
	* �ֶ����� :������<br>
	* �������� :varchar(100)<br>
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
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getModifyUser() {
		return ModifyUser;
	}

	/**
	* �����ֶ�ModifyUser��ֵ�����ֶε�<br>
	* �ֶ����� :�޸���<br>
	* �������� :varchar(100)<br>
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