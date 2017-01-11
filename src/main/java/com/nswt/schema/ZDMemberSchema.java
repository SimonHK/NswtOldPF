package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * �����ƣ���Ա��<br>
 * ����룺ZDMember<br>
 * ��������UserName<br>
 */
public class ZDMemberSchema extends Schema {
	private String UserName;

	private String Password;

	private String Name;

	private String Email;

	private String Gender;

	private String Type;

	private Long SiteID;

	private String Logo;

	private String Status;

	private String Score;

	private String Rank;

	private String MemberLevel;

	private String PWQuestion;

	private String PWAnswer;

	private String LastLoginIP;

	private Date LastLoginTime;

	private Date RegTime;

	private String RegIP;

	private String LoginMD5;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	private String Prop4;

	private String Prop5;

	private String Prop6;

	private String Prop7;

	private String Prop8;

	private String Prop9;

	private String Prop10;

	private String Prop11;

	private String Prop12;

	private String Prop13;

	private String Prop14;

	private String Prop15;

	private String Prop16;

	private String Prop17;

	private String Prop18;

	private String Prop19;

	private String Prop20;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("UserName", DataColumn.STRING, 0, 50 , 0 , true , true),
		new SchemaColumn("Password", DataColumn.STRING, 1, 32 , 0 , true , false),
		new SchemaColumn("Name", DataColumn.STRING, 2, 100 , 0 , true , false),
		new SchemaColumn("Email", DataColumn.STRING, 3, 100 , 0 , true , false),
		new SchemaColumn("Gender", DataColumn.STRING, 4, 1 , 0 , false , false),
		new SchemaColumn("Type", DataColumn.STRING, 5, 10 , 0 , false , false),
		new SchemaColumn("SiteID", DataColumn.LONG, 6, 20 , 0 , false , false),
		new SchemaColumn("Logo", DataColumn.STRING, 7, 100 , 0 , false , false),
		new SchemaColumn("Status", DataColumn.STRING, 8, 1 , 0 , true , false),
		new SchemaColumn("Score", DataColumn.STRING, 9, 20 , 0 , false , false),
		new SchemaColumn("Rank", DataColumn.STRING, 10, 50 , 0 , false , false),
		new SchemaColumn("MemberLevel", DataColumn.STRING, 11, 10 , 0 , false , false),
		new SchemaColumn("PWQuestion", DataColumn.STRING, 12, 100 , 0 , false , false),
		new SchemaColumn("PWAnswer", DataColumn.STRING, 13, 100 , 0 , false , false),
		new SchemaColumn("LastLoginIP", DataColumn.STRING, 14, 16 , 0 , false , false),
		new SchemaColumn("LastLoginTime", DataColumn.DATETIME, 15, 0 , 0 , false , false),
		new SchemaColumn("RegTime", DataColumn.DATETIME, 16, 0 , 0 , false , false),
		new SchemaColumn("RegIP", DataColumn.STRING, 17, 16 , 0 , false , false),
		new SchemaColumn("LoginMD5", DataColumn.STRING, 18, 32 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 19, 100 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 20, 100 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 21, 100 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 22, 100 , 0 , false , false),
		new SchemaColumn("Prop5", DataColumn.STRING, 23, 100 , 0 , false , false),
		new SchemaColumn("Prop6", DataColumn.STRING, 24, 100 , 0 , false , false),
		new SchemaColumn("Prop7", DataColumn.STRING, 25, 100 , 0 , false , false),
		new SchemaColumn("Prop8", DataColumn.STRING, 26, 100 , 0 , false , false),
		new SchemaColumn("Prop9", DataColumn.STRING, 27, 100 , 0 , false , false),
		new SchemaColumn("Prop10", DataColumn.STRING, 28, 100 , 0 , false , false),
		new SchemaColumn("Prop11", DataColumn.STRING, 29, 100 , 0 , false , false),
		new SchemaColumn("Prop12", DataColumn.STRING, 30, 100 , 0 , false , false),
		new SchemaColumn("Prop13", DataColumn.STRING, 31, 100 , 0 , false , false),
		new SchemaColumn("Prop14", DataColumn.STRING, 32, 100 , 0 , false , false),
		new SchemaColumn("Prop15", DataColumn.STRING, 33, 100 , 0 , false , false),
		new SchemaColumn("Prop16", DataColumn.STRING, 34, 100 , 0 , false , false),
		new SchemaColumn("Prop17", DataColumn.STRING, 35, 100 , 0 , false , false),
		new SchemaColumn("Prop18", DataColumn.STRING, 36, 100 , 0 , false , false),
		new SchemaColumn("Prop19", DataColumn.STRING, 37, 100 , 0 , false , false),
		new SchemaColumn("Prop20", DataColumn.STRING, 38, 100 , 0 , false , false)
	};

	public static final String _TableCode = "ZDMember";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into ZDMember values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZDMember set UserName=?,Password=?,Name=?,Email=?,Gender=?,Type=?,SiteID=?,Logo=?,Status=?,Score=?,Rank=?,MemberLevel=?,PWQuestion=?,PWAnswer=?,LastLoginIP=?,LastLoginTime=?,RegTime=?,RegIP=?,LoginMD5=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,Prop5=?,Prop6=?,Prop7=?,Prop8=?,Prop9=?,Prop10=?,Prop11=?,Prop12=?,Prop13=?,Prop14=?,Prop15=?,Prop16=?,Prop17=?,Prop18=?,Prop19=?,Prop20=? where UserName=?";

	protected static final String _DeleteSQL = "delete from ZDMember  where UserName=?";

	protected static final String _FillAllSQL = "select * from ZDMember  where UserName=?";

	public ZDMemberSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[39];
	}

	protected Schema newInstance(){
		return new ZDMemberSchema();
	}

	protected SchemaSet newSet(){
		return new ZDMemberSet();
	}

	public ZDMemberSet query() {
		return query(null, -1, -1);
	}

	public ZDMemberSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZDMemberSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZDMemberSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZDMemberSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){UserName = (String)v;return;}
		if (i == 1){Password = (String)v;return;}
		if (i == 2){Name = (String)v;return;}
		if (i == 3){Email = (String)v;return;}
		if (i == 4){Gender = (String)v;return;}
		if (i == 5){Type = (String)v;return;}
		if (i == 6){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 7){Logo = (String)v;return;}
		if (i == 8){Status = (String)v;return;}
		if (i == 9){Score = (String)v;return;}
		if (i == 10){Rank = (String)v;return;}
		if (i == 11){MemberLevel = (String)v;return;}
		if (i == 12){PWQuestion = (String)v;return;}
		if (i == 13){PWAnswer = (String)v;return;}
		if (i == 14){LastLoginIP = (String)v;return;}
		if (i == 15){LastLoginTime = (Date)v;return;}
		if (i == 16){RegTime = (Date)v;return;}
		if (i == 17){RegIP = (String)v;return;}
		if (i == 18){LoginMD5 = (String)v;return;}
		if (i == 19){Prop1 = (String)v;return;}
		if (i == 20){Prop2 = (String)v;return;}
		if (i == 21){Prop3 = (String)v;return;}
		if (i == 22){Prop4 = (String)v;return;}
		if (i == 23){Prop5 = (String)v;return;}
		if (i == 24){Prop6 = (String)v;return;}
		if (i == 25){Prop7 = (String)v;return;}
		if (i == 26){Prop8 = (String)v;return;}
		if (i == 27){Prop9 = (String)v;return;}
		if (i == 28){Prop10 = (String)v;return;}
		if (i == 29){Prop11 = (String)v;return;}
		if (i == 30){Prop12 = (String)v;return;}
		if (i == 31){Prop13 = (String)v;return;}
		if (i == 32){Prop14 = (String)v;return;}
		if (i == 33){Prop15 = (String)v;return;}
		if (i == 34){Prop16 = (String)v;return;}
		if (i == 35){Prop17 = (String)v;return;}
		if (i == 36){Prop18 = (String)v;return;}
		if (i == 37){Prop19 = (String)v;return;}
		if (i == 38){Prop20 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return UserName;}
		if (i == 1){return Password;}
		if (i == 2){return Name;}
		if (i == 3){return Email;}
		if (i == 4){return Gender;}
		if (i == 5){return Type;}
		if (i == 6){return SiteID;}
		if (i == 7){return Logo;}
		if (i == 8){return Status;}
		if (i == 9){return Score;}
		if (i == 10){return Rank;}
		if (i == 11){return MemberLevel;}
		if (i == 12){return PWQuestion;}
		if (i == 13){return PWAnswer;}
		if (i == 14){return LastLoginIP;}
		if (i == 15){return LastLoginTime;}
		if (i == 16){return RegTime;}
		if (i == 17){return RegIP;}
		if (i == 18){return LoginMD5;}
		if (i == 19){return Prop1;}
		if (i == 20){return Prop2;}
		if (i == 21){return Prop3;}
		if (i == 22){return Prop4;}
		if (i == 23){return Prop5;}
		if (i == 24){return Prop6;}
		if (i == 25){return Prop7;}
		if (i == 26){return Prop8;}
		if (i == 27){return Prop9;}
		if (i == 28){return Prop10;}
		if (i == 29){return Prop11;}
		if (i == 30){return Prop12;}
		if (i == 31){return Prop13;}
		if (i == 32){return Prop14;}
		if (i == 33){return Prop15;}
		if (i == 34){return Prop16;}
		if (i == 35){return Prop17;}
		if (i == 36){return Prop18;}
		if (i == 37){return Prop19;}
		if (i == 38){return Prop20;}
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
	* ��ȡ�ֶ�Password��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :varchar(32)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getPassword() {
		return Password;
	}

	/**
	* �����ֶ�Password��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :varchar(32)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setPassword(String password) {
		this.Password = password;
    }

	/**
	* ��ȡ�ֶ�Name��ֵ�����ֶε�<br>
	* �ֶ����� :�ǳ�/��˾����<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getName() {
		return Name;
	}

	/**
	* �����ֶ�Name��ֵ�����ֶε�<br>
	* �ֶ����� :�ǳ�/��˾����<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setName(String name) {
		this.Name = name;
    }

	/**
	* ��ȡ�ֶ�Email��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getEmail() {
		return Email;
	}

	/**
	* �����ֶ�Email��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setEmail(String email) {
		this.Email = email;
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
	* ��ȡ�ֶ�Type��ֵ�����ֶε�<br>
	* �ֶ����� :��Ա����<br>
	* �������� :varchar(10)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getType() {
		return Type;
	}

	/**
	* �����ֶ�Type��ֵ�����ֶε�<br>
	* �ֶ����� :��Ա����<br>
	* �������� :varchar(10)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setType(String type) {
		this.Type = type;
    }

	/**
	* ��ȡ�ֶ�SiteID��ֵ�����ֶε�<br>
	* �ֶ����� :վ��ID<br>
	* �������� :bigint(20)<br>
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
	* �������� :bigint(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setSiteID(long siteID) {
		this.SiteID = new Long(siteID);
    }

	/**
	* �����ֶ�SiteID��ֵ�����ֶε�<br>
	* �ֶ����� :վ��ID<br>
	* �������� :bigint(20)<br>
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
	* ��ȡ�ֶ�Logo��ֵ�����ֶε�<br>
	* �ֶ����� :��Աͷ��<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getLogo() {
		return Logo;
	}

	/**
	* �����ֶ�Logo��ֵ�����ֶε�<br>
	* �ֶ����� :��Աͷ��<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setLogo(String logo) {
		this.Logo = logo;
    }

	/**
	* ��ȡ�ֶ�Status��ֵ�����ֶε�<br>
	* �ֶ����� :���״̬<br>
	* �������� :varchar(1)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getStatus() {
		return Status;
	}

	/**
	* �����ֶ�Status��ֵ�����ֶε�<br>
	* �ֶ����� :���״̬<br>
	* �������� :varchar(1)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setStatus(String status) {
		this.Status = status;
    }

	/**
	* ��ȡ�ֶ�Score��ֵ�����ֶε�<br>
	* �ֶ����� :��Ա����<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getScore() {
		return Score;
	}

	/**
	* �����ֶ�Score��ֵ�����ֶε�<br>
	* �ֶ����� :��Ա����<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setScore(String score) {
		this.Score = score;
    }

	/**
	* ��ȡ�ֶ�Rank��ֵ�����ֶε�<br>
	* �ֶ����� :��Ա����<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getRank() {
		return Rank;
	}

	/**
	* �����ֶ�Rank��ֵ�����ֶε�<br>
	* �ֶ����� :��Ա����<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setRank(String rank) {
		this.Rank = rank;
    }

	/**
	* ��ȡ�ֶ�MemberLevel��ֵ�����ֶε�<br>
	* �ֶ����� :��Ա�ȼ�<br>
	* �������� :varchar(10)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getMemberLevel() {
		return MemberLevel;
	}

	/**
	* �����ֶ�MemberLevel��ֵ�����ֶε�<br>
	* �ֶ����� :��Ա�ȼ�<br>
	* �������� :varchar(10)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setMemberLevel(String memberLevel) {
		this.MemberLevel = memberLevel;
    }

	/**
	* ��ȡ�ֶ�PWQuestion��ֵ�����ֶε�<br>
	* �ֶ����� :��ȫ����<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getPWQuestion() {
		return PWQuestion;
	}

	/**
	* �����ֶ�PWQuestion��ֵ�����ֶε�<br>
	* �ֶ����� :��ȫ����<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setPWQuestion(String pWQuestion) {
		this.PWQuestion = pWQuestion;
    }

	/**
	* ��ȡ�ֶ�PWAnswer��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getPWAnswer() {
		return PWAnswer;
	}

	/**
	* �����ֶ�PWAnswer��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setPWAnswer(String pWAnswer) {
		this.PWAnswer = pWAnswer;
    }

	/**
	* ��ȡ�ֶ�LastLoginIP��ֵ�����ֶε�<br>
	* �ֶ����� :����¼IP<br>
	* �������� :varchar(16)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getLastLoginIP() {
		return LastLoginIP;
	}

	/**
	* �����ֶ�LastLoginIP��ֵ�����ֶε�<br>
	* �ֶ����� :����¼IP<br>
	* �������� :varchar(16)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setLastLoginIP(String lastLoginIP) {
		this.LastLoginIP = lastLoginIP;
    }

	/**
	* ��ȡ�ֶ�LastLoginTime��ֵ�����ֶε�<br>
	* �ֶ����� :����¼ʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public Date getLastLoginTime() {
		return LastLoginTime;
	}

	/**
	* �����ֶ�LastLoginTime��ֵ�����ֶε�<br>
	* �ֶ����� :����¼ʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setLastLoginTime(Date lastLoginTime) {
		this.LastLoginTime = lastLoginTime;
    }

	/**
	* ��ȡ�ֶ�RegTime��ֵ�����ֶε�<br>
	* �ֶ����� :ע��ʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public Date getRegTime() {
		return RegTime;
	}

	/**
	* �����ֶ�RegTime��ֵ�����ֶε�<br>
	* �ֶ����� :ע��ʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setRegTime(Date regTime) {
		this.RegTime = regTime;
    }

	/**
	* ��ȡ�ֶ�RegIP��ֵ�����ֶε�<br>
	* �ֶ����� :ע��IP<br>
	* �������� :varchar(16)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getRegIP() {
		return RegIP;
	}

	/**
	* �����ֶ�RegIP��ֵ�����ֶε�<br>
	* �ֶ����� :ע��IP<br>
	* �������� :varchar(16)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setRegIP(String regIP) {
		this.RegIP = regIP;
    }

	/**
	* ��ȡ�ֶ�LoginMD5��ֵ�����ֶε�<br>
	* �ֶ����� :�Զ���¼<br>
	* �������� :varchar(32)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getLoginMD5() {
		return LoginMD5;
	}

	/**
	* �����ֶ�LoginMD5��ֵ�����ֶε�<br>
	* �ֶ����� :�Զ���¼<br>
	* �������� :varchar(32)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setLoginMD5(String loginMD5) {
		this.LoginMD5 = loginMD5;
    }

	/**
	* ��ȡ�ֶ�Prop1��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�1<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* �����ֶ�Prop1��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�1<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* ��ȡ�ֶ�Prop2��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�2<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* �����ֶ�Prop2��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�2<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

	/**
	* ��ȡ�ֶ�Prop3��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�3<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp3() {
		return Prop3;
	}

	/**
	* �����ֶ�Prop3��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�3<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp3(String prop3) {
		this.Prop3 = prop3;
    }

	/**
	* ��ȡ�ֶ�Prop4��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�4<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp4() {
		return Prop4;
	}

	/**
	* �����ֶ�Prop4��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�4<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp4(String prop4) {
		this.Prop4 = prop4;
    }

	/**
	* ��ȡ�ֶ�Prop5��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�5<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp5() {
		return Prop5;
	}

	/**
	* �����ֶ�Prop5��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�5<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp5(String prop5) {
		this.Prop5 = prop5;
    }

	/**
	* ��ȡ�ֶ�Prop6��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�6<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp6() {
		return Prop6;
	}

	/**
	* �����ֶ�Prop6��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�6<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp6(String prop6) {
		this.Prop6 = prop6;
    }

	/**
	* ��ȡ�ֶ�Prop7��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�7<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp7() {
		return Prop7;
	}

	/**
	* �����ֶ�Prop7��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�7<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp7(String prop7) {
		this.Prop7 = prop7;
    }

	/**
	* ��ȡ�ֶ�Prop8��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�8<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp8() {
		return Prop8;
	}

	/**
	* �����ֶ�Prop8��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�8<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp8(String prop8) {
		this.Prop8 = prop8;
    }

	/**
	* ��ȡ�ֶ�Prop9��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�9<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp9() {
		return Prop9;
	}

	/**
	* �����ֶ�Prop9��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�9<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp9(String prop9) {
		this.Prop9 = prop9;
    }

	/**
	* ��ȡ�ֶ�Prop10��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�10<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp10() {
		return Prop10;
	}

	/**
	* �����ֶ�Prop10��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�10<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp10(String prop10) {
		this.Prop10 = prop10;
    }

	/**
	* ��ȡ�ֶ�Prop11��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�11<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp11() {
		return Prop11;
	}

	/**
	* �����ֶ�Prop11��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�11<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp11(String prop11) {
		this.Prop11 = prop11;
    }

	/**
	* ��ȡ�ֶ�Prop12��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�12<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp12() {
		return Prop12;
	}

	/**
	* �����ֶ�Prop12��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�12<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp12(String prop12) {
		this.Prop12 = prop12;
    }

	/**
	* ��ȡ�ֶ�Prop13��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�13<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp13() {
		return Prop13;
	}

	/**
	* �����ֶ�Prop13��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�13<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp13(String prop13) {
		this.Prop13 = prop13;
    }

	/**
	* ��ȡ�ֶ�Prop14��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�14<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp14() {
		return Prop14;
	}

	/**
	* �����ֶ�Prop14��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�14<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp14(String prop14) {
		this.Prop14 = prop14;
    }

	/**
	* ��ȡ�ֶ�Prop15��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�15<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp15() {
		return Prop15;
	}

	/**
	* �����ֶ�Prop15��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�15<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp15(String prop15) {
		this.Prop15 = prop15;
    }

	/**
	* ��ȡ�ֶ�Prop16��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�16<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp16() {
		return Prop16;
	}

	/**
	* �����ֶ�Prop16��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�16<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp16(String prop16) {
		this.Prop16 = prop16;
    }

	/**
	* ��ȡ�ֶ�Prop17��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�17<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp17() {
		return Prop17;
	}

	/**
	* �����ֶ�Prop17��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�17<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp17(String prop17) {
		this.Prop17 = prop17;
    }

	/**
	* ��ȡ�ֶ�Prop18��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�18<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp18() {
		return Prop18;
	}

	/**
	* �����ֶ�Prop18��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�18<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp18(String prop18) {
		this.Prop18 = prop18;
    }

	/**
	* ��ȡ�ֶ�Prop19��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�19<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp19() {
		return Prop19;
	}

	/**
	* �����ֶ�Prop19��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�19<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp19(String prop19) {
		this.Prop19 = prop19;
    }

	/**
	* ��ȡ�ֶ�Prop20��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�20<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp20() {
		return Prop20;
	}

	/**
	* �����ֶ�Prop20��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�20<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp20(String prop20) {
		this.Prop20 = prop20;
    }

}