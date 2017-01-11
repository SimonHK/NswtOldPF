package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * �����ƣ���̳�û������<br>
 * ����룺BZCForumGroup<br>
 * ��������ID, BackupNo<br>
 */
public class BZCForumGroupSchema extends Schema {
	private Long ID;

	private Long SiteID;

	private Long RadminID;

	private String Name;

	private String SystemName;

	private String Type;

	private String Color;

	private String Image;

	private Long LowerScore;

	private Long UpperScore;

	private String AllowTheme;

	private String AllowReply;

	private String AllowSearch;

	private String AllowUserInfo;

	private String AllowPanel;

	private String AllowNickName;

	private String AllowVisit;

	private String AllowHeadImage;

	private String AllowFace;

	private String AllowAutograph;

	private String Verify;

	private String AllowEditUser;

	private String AllowForbidUser;

	private String AllowEditForum;

	private String AllowVerfyPost;

	private String AllowDel;

	private String AllowEdit;

	private String Hide;

	private String RemoveTheme;

	private String MoveTheme;

	private String TopTheme;

	private String BrightTheme;

	private String UpOrDownTheme;

	private String BestTheme;

	private String prop1;

	private String prop2;

	private String prop3;

	private String prop4;

	private String AddUser;

	private Date AddTime;

	private String ModifyUser;

	private Date ModifyTime;

	private Long OrderFlag;

	private String BackupNo;

	private String BackupOperator;

	private Date BackupTime;

	private String BackupMemo;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 0 , 0 , true , true),
		new SchemaColumn("SiteID", DataColumn.LONG, 1, 0 , 0 , false , false),
		new SchemaColumn("RadminID", DataColumn.LONG, 2, 0 , 0 , false , false),
		new SchemaColumn("Name", DataColumn.STRING, 3, 100 , 0 , true , false),
		new SchemaColumn("SystemName", DataColumn.STRING, 4, 100 , 0 , false , false),
		new SchemaColumn("Type", DataColumn.STRING, 5, 100 , 0 , true , false),
		new SchemaColumn("Color", DataColumn.STRING, 6, 100 , 0 , false , false),
		new SchemaColumn("Image", DataColumn.STRING, 7, 100 , 0 , false , false),
		new SchemaColumn("LowerScore", DataColumn.LONG, 8, 0 , 0 , false , false),
		new SchemaColumn("UpperScore", DataColumn.LONG, 9, 0 , 0 , false , false),
		new SchemaColumn("AllowTheme", DataColumn.STRING, 10, 2 , 0 , false , false),
		new SchemaColumn("AllowReply", DataColumn.STRING, 11, 2 , 0 , false , false),
		new SchemaColumn("AllowSearch", DataColumn.STRING, 12, 2 , 0 , false , false),
		new SchemaColumn("AllowUserInfo", DataColumn.STRING, 13, 2 , 0 , false , false),
		new SchemaColumn("AllowPanel", DataColumn.STRING, 14, 2 , 0 , false , false),
		new SchemaColumn("AllowNickName", DataColumn.STRING, 15, 2 , 0 , false , false),
		new SchemaColumn("AllowVisit", DataColumn.STRING, 16, 2 , 0 , false , false),
		new SchemaColumn("AllowHeadImage", DataColumn.STRING, 17, 2 , 0 , false , false),
		new SchemaColumn("AllowFace", DataColumn.STRING, 18, 2 , 0 , false , false),
		new SchemaColumn("AllowAutograph", DataColumn.STRING, 19, 2 , 0 , false , false),
		new SchemaColumn("Verify", DataColumn.STRING, 20, 2 , 0 , false , false),
		new SchemaColumn("AllowEditUser", DataColumn.STRING, 21, 2 , 0 , false , false),
		new SchemaColumn("AllowForbidUser", DataColumn.STRING, 22, 2 , 0 , false , false),
		new SchemaColumn("AllowEditForum", DataColumn.STRING, 23, 2 , 0 , false , false),
		new SchemaColumn("AllowVerfyPost", DataColumn.STRING, 24, 2 , 0 , false , false),
		new SchemaColumn("AllowDel", DataColumn.STRING, 25, 2 , 0 , false , false),
		new SchemaColumn("AllowEdit", DataColumn.STRING, 26, 2 , 0 , false , false),
		new SchemaColumn("Hide", DataColumn.STRING, 27, 2 , 0 , false , false),
		new SchemaColumn("RemoveTheme", DataColumn.STRING, 28, 2 , 0 , false , false),
		new SchemaColumn("MoveTheme", DataColumn.STRING, 29, 2 , 0 , false , false),
		new SchemaColumn("TopTheme", DataColumn.STRING, 30, 2 , 0 , false , false),
		new SchemaColumn("BrightTheme", DataColumn.STRING, 31, 2 , 0 , false , false),
		new SchemaColumn("UpOrDownTheme", DataColumn.STRING, 32, 2 , 0 , false , false),
		new SchemaColumn("BestTheme", DataColumn.STRING, 33, 2 , 0 , false , false),
		new SchemaColumn("prop1", DataColumn.STRING, 34, 50 , 0 , false , false),
		new SchemaColumn("prop2", DataColumn.STRING, 35, 50 , 0 , false , false),
		new SchemaColumn("prop3", DataColumn.STRING, 36, 50 , 0 , false , false),
		new SchemaColumn("prop4", DataColumn.STRING, 37, 50 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 38, 100 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 39, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 40, 100 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 41, 0 , 0 , false , false),
		new SchemaColumn("OrderFlag", DataColumn.LONG, 42, 0 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 43, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 44, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 45, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 46, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZCForumGroup";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into BZCForumGroup values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZCForumGroup set ID=?,SiteID=?,RadminID=?,Name=?,SystemName=?,Type=?,Color=?,Image=?,LowerScore=?,UpperScore=?,AllowTheme=?,AllowReply=?,AllowSearch=?,AllowUserInfo=?,AllowPanel=?,AllowNickName=?,AllowVisit=?,AllowHeadImage=?,AllowFace=?,AllowAutograph=?,Verify=?,AllowEditUser=?,AllowForbidUser=?,AllowEditForum=?,AllowVerfyPost=?,AllowDel=?,AllowEdit=?,Hide=?,RemoveTheme=?,MoveTheme=?,TopTheme=?,BrightTheme=?,UpOrDownTheme=?,BestTheme=?,prop1=?,prop2=?,prop3=?,prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,OrderFlag=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZCForumGroup  where ID=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZCForumGroup  where ID=? and BackupNo=?";

	public BZCForumGroupSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[47];
	}

	protected Schema newInstance(){
		return new BZCForumGroupSchema();
	}

	protected SchemaSet newSet(){
		return new BZCForumGroupSet();
	}

	public BZCForumGroupSet query() {
		return query(null, -1, -1);
	}

	public BZCForumGroupSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZCForumGroupSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZCForumGroupSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZCForumGroupSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 2){if(v==null){RadminID = null;}else{RadminID = new Long(v.toString());}return;}
		if (i == 3){Name = (String)v;return;}
		if (i == 4){SystemName = (String)v;return;}
		if (i == 5){Type = (String)v;return;}
		if (i == 6){Color = (String)v;return;}
		if (i == 7){Image = (String)v;return;}
		if (i == 8){if(v==null){LowerScore = null;}else{LowerScore = new Long(v.toString());}return;}
		if (i == 9){if(v==null){UpperScore = null;}else{UpperScore = new Long(v.toString());}return;}
		if (i == 10){AllowTheme = (String)v;return;}
		if (i == 11){AllowReply = (String)v;return;}
		if (i == 12){AllowSearch = (String)v;return;}
		if (i == 13){AllowUserInfo = (String)v;return;}
		if (i == 14){AllowPanel = (String)v;return;}
		if (i == 15){AllowNickName = (String)v;return;}
		if (i == 16){AllowVisit = (String)v;return;}
		if (i == 17){AllowHeadImage = (String)v;return;}
		if (i == 18){AllowFace = (String)v;return;}
		if (i == 19){AllowAutograph = (String)v;return;}
		if (i == 20){Verify = (String)v;return;}
		if (i == 21){AllowEditUser = (String)v;return;}
		if (i == 22){AllowForbidUser = (String)v;return;}
		if (i == 23){AllowEditForum = (String)v;return;}
		if (i == 24){AllowVerfyPost = (String)v;return;}
		if (i == 25){AllowDel = (String)v;return;}
		if (i == 26){AllowEdit = (String)v;return;}
		if (i == 27){Hide = (String)v;return;}
		if (i == 28){RemoveTheme = (String)v;return;}
		if (i == 29){MoveTheme = (String)v;return;}
		if (i == 30){TopTheme = (String)v;return;}
		if (i == 31){BrightTheme = (String)v;return;}
		if (i == 32){UpOrDownTheme = (String)v;return;}
		if (i == 33){BestTheme = (String)v;return;}
		if (i == 34){prop1 = (String)v;return;}
		if (i == 35){prop2 = (String)v;return;}
		if (i == 36){prop3 = (String)v;return;}
		if (i == 37){prop4 = (String)v;return;}
		if (i == 38){AddUser = (String)v;return;}
		if (i == 39){AddTime = (Date)v;return;}
		if (i == 40){ModifyUser = (String)v;return;}
		if (i == 41){ModifyTime = (Date)v;return;}
		if (i == 42){if(v==null){OrderFlag = null;}else{OrderFlag = new Long(v.toString());}return;}
		if (i == 43){BackupNo = (String)v;return;}
		if (i == 44){BackupOperator = (String)v;return;}
		if (i == 45){BackupTime = (Date)v;return;}
		if (i == 46){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return SiteID;}
		if (i == 2){return RadminID;}
		if (i == 3){return Name;}
		if (i == 4){return SystemName;}
		if (i == 5){return Type;}
		if (i == 6){return Color;}
		if (i == 7){return Image;}
		if (i == 8){return LowerScore;}
		if (i == 9){return UpperScore;}
		if (i == 10){return AllowTheme;}
		if (i == 11){return AllowReply;}
		if (i == 12){return AllowSearch;}
		if (i == 13){return AllowUserInfo;}
		if (i == 14){return AllowPanel;}
		if (i == 15){return AllowNickName;}
		if (i == 16){return AllowVisit;}
		if (i == 17){return AllowHeadImage;}
		if (i == 18){return AllowFace;}
		if (i == 19){return AllowAutograph;}
		if (i == 20){return Verify;}
		if (i == 21){return AllowEditUser;}
		if (i == 22){return AllowForbidUser;}
		if (i == 23){return AllowEditForum;}
		if (i == 24){return AllowVerfyPost;}
		if (i == 25){return AllowDel;}
		if (i == 26){return AllowEdit;}
		if (i == 27){return Hide;}
		if (i == 28){return RemoveTheme;}
		if (i == 29){return MoveTheme;}
		if (i == 30){return TopTheme;}
		if (i == 31){return BrightTheme;}
		if (i == 32){return UpOrDownTheme;}
		if (i == 33){return BestTheme;}
		if (i == 34){return prop1;}
		if (i == 35){return prop2;}
		if (i == 36){return prop3;}
		if (i == 37){return prop4;}
		if (i == 38){return AddUser;}
		if (i == 39){return AddTime;}
		if (i == 40){return ModifyUser;}
		if (i == 41){return ModifyTime;}
		if (i == 42){return OrderFlag;}
		if (i == 43){return BackupNo;}
		if (i == 44){return BackupOperator;}
		if (i == 45){return BackupTime;}
		if (i == 46){return BackupMemo;}
		return null;
	}

	/**
	* ��ȡ�ֶ�ID��ֵ�����ֶε�<br>
	* �ֶ����� :��ID<br>
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
	* �ֶ����� :��ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setID(long iD) {
		this.ID = new Long(iD);
    }

	/**
	* �����ֶ�ID��ֵ�����ֶε�<br>
	* �ֶ����� :��ID<br>
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
	* ��ȡ�ֶ�RadminID��ֵ�����ֶε�<br>
	* �ֶ����� :������ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getRadminID() {
		if(RadminID==null){return 0;}
		return RadminID.longValue();
	}

	/**
	* �����ֶ�RadminID��ֵ�����ֶε�<br>
	* �ֶ����� :������ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setRadminID(long radminID) {
		this.RadminID = new Long(radminID);
    }

	/**
	* �����ֶ�RadminID��ֵ�����ֶε�<br>
	* �ֶ����� :������ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setRadminID(String radminID) {
		if (radminID == null){
			this.RadminID = null;
			return;
		}
		this.RadminID = new Long(radminID);
    }

	/**
	* ��ȡ�ֶ�Name��ֵ�����ֶε�<br>
	* �ֶ����� :��ͷ��<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getName() {
		return Name;
	}

	/**
	* �����ֶ�Name��ֵ�����ֶε�<br>
	* �ֶ����� :��ͷ��<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setName(String name) {
		this.Name = name;
    }

	/**
	* ��ȡ�ֶ�SystemName��ֵ�����ֶε�<br>
	* �ֶ����� :ϵͳͷ��<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getSystemName() {
		return SystemName;
	}

	/**
	* �����ֶ�SystemName��ֵ�����ֶε�<br>
	* �ֶ����� :ϵͳͷ��<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setSystemName(String systemName) {
		this.SystemName = systemName;
    }

	/**
	* ��ȡ�ֶ�Type��ֵ�����ֶε�<br>
	* �ֶ����� :�û�������<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getType() {
		return Type;
	}

	/**
	* �����ֶ�Type��ֵ�����ֶε�<br>
	* �ֶ����� :�û�������<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setType(String type) {
		this.Type = type;
    }

	/**
	* ��ȡ�ֶ�Color��ֵ�����ֶε�<br>
	* �ֶ����� :�û�����ʾ��ɫ<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getColor() {
		return Color;
	}

	/**
	* �����ֶ�Color��ֵ�����ֶε�<br>
	* �ֶ����� :�û�����ʾ��ɫ<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setColor(String color) {
		this.Color = color;
    }

	/**
	* ��ȡ�ֶ�Image��ֵ�����ֶε�<br>
	* �ֶ����� :�û���ͷ��<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getImage() {
		return Image;
	}

	/**
	* �����ֶ�Image��ֵ�����ֶε�<br>
	* �ֶ����� :�û���ͷ��<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setImage(String image) {
		this.Image = image;
    }

	/**
	* ��ȡ�ֶ�LowerScore��ֵ�����ֶε�<br>
	* �ֶ����� :��ͻ���<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getLowerScore() {
		if(LowerScore==null){return 0;}
		return LowerScore.longValue();
	}

	/**
	* �����ֶ�LowerScore��ֵ�����ֶε�<br>
	* �ֶ����� :��ͻ���<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setLowerScore(long lowerScore) {
		this.LowerScore = new Long(lowerScore);
    }

	/**
	* �����ֶ�LowerScore��ֵ�����ֶε�<br>
	* �ֶ����� :��ͻ���<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setLowerScore(String lowerScore) {
		if (lowerScore == null){
			this.LowerScore = null;
			return;
		}
		this.LowerScore = new Long(lowerScore);
    }

	/**
	* ��ȡ�ֶ�UpperScore��ֵ�����ֶε�<br>
	* �ֶ����� :��߻���<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getUpperScore() {
		if(UpperScore==null){return 0;}
		return UpperScore.longValue();
	}

	/**
	* �����ֶ�UpperScore��ֵ�����ֶε�<br>
	* �ֶ����� :��߻���<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setUpperScore(long upperScore) {
		this.UpperScore = new Long(upperScore);
    }

	/**
	* �����ֶ�UpperScore��ֵ�����ֶε�<br>
	* �ֶ����� :��߻���<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setUpperScore(String upperScore) {
		if (upperScore == null){
			this.UpperScore = null;
			return;
		}
		this.UpperScore = new Long(upperScore);
    }

	/**
	* ��ȡ�ֶ�AllowTheme��ֵ�����ֶε�<br>
	* �ֶ����� :������<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getAllowTheme() {
		return AllowTheme;
	}

	/**
	* �����ֶ�AllowTheme��ֵ�����ֶε�<br>
	* �ֶ����� :������<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAllowTheme(String allowTheme) {
		this.AllowTheme = allowTheme;
    }

	/**
	* ��ȡ�ֶ�AllowReply��ֵ�����ֶε�<br>
	* �ֶ����� :����ظ�<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getAllowReply() {
		return AllowReply;
	}

	/**
	* �����ֶ�AllowReply��ֵ�����ֶε�<br>
	* �ֶ����� :����ظ�<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAllowReply(String allowReply) {
		this.AllowReply = allowReply;
    }

	/**
	* ��ȡ�ֶ�AllowSearch��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getAllowSearch() {
		return AllowSearch;
	}

	/**
	* �����ֶ�AllowSearch��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAllowSearch(String allowSearch) {
		this.AllowSearch = allowSearch;
    }

	/**
	* ��ȡ�ֶ�AllowUserInfo��ֵ�����ֶε�<br>
	* �ֶ����� :����鿴�û���Ϣ<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getAllowUserInfo() {
		return AllowUserInfo;
	}

	/**
	* �����ֶ�AllowUserInfo��ֵ�����ֶε�<br>
	* �ֶ����� :����鿴�û���Ϣ<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAllowUserInfo(String allowUserInfo) {
		this.AllowUserInfo = allowUserInfo;
    }

	/**
	* ��ȡ�ֶ�AllowPanel��ֵ�����ֶε�<br>
	* �ֶ����� :����ʹ�ÿ������<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getAllowPanel() {
		return AllowPanel;
	}

	/**
	* �����ֶ�AllowPanel��ֵ�����ֶε�<br>
	* �ֶ����� :����ʹ�ÿ������<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAllowPanel(String allowPanel) {
		this.AllowPanel = allowPanel;
    }

	/**
	* ��ȡ�ֶ�AllowNickName��ֵ�����ֶε�<br>
	* �ֶ����� :����ʹ���ǳ�<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getAllowNickName() {
		return AllowNickName;
	}

	/**
	* �����ֶ�AllowNickName��ֵ�����ֶε�<br>
	* �ֶ����� :����ʹ���ǳ�<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAllowNickName(String allowNickName) {
		this.AllowNickName = allowNickName;
    }

	/**
	* ��ȡ�ֶ�AllowVisit��ֵ�����ֶε�<br>
	* �ֶ����� :���������̳<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getAllowVisit() {
		return AllowVisit;
	}

	/**
	* �����ֶ�AllowVisit��ֵ�����ֶε�<br>
	* �ֶ����� :���������̳<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAllowVisit(String allowVisit) {
		this.AllowVisit = allowVisit;
    }

	/**
	* ��ȡ�ֶ�AllowHeadImage��ֵ�����ֶε�<br>
	* �ֶ����� :�����Զ���ͷ��<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getAllowHeadImage() {
		return AllowHeadImage;
	}

	/**
	* �����ֶ�AllowHeadImage��ֵ�����ֶε�<br>
	* �ֶ����� :�����Զ���ͷ��<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAllowHeadImage(String allowHeadImage) {
		this.AllowHeadImage = allowHeadImage;
    }

	/**
	* ��ȡ�ֶ�AllowFace��ֵ�����ֶε�<br>
	* �ֶ����� :����ʹ�ñ���<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getAllowFace() {
		return AllowFace;
	}

	/**
	* �����ֶ�AllowFace��ֵ�����ֶε�<br>
	* �ֶ����� :����ʹ�ñ���<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAllowFace(String allowFace) {
		this.AllowFace = allowFace;
    }

	/**
	* ��ȡ�ֶ�AllowAutograph��ֵ�����ֶε�<br>
	* �ֶ����� :����ʹ��ǩ��<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getAllowAutograph() {
		return AllowAutograph;
	}

	/**
	* �����ֶ�AllowAutograph��ֵ�����ֶε�<br>
	* �ֶ����� :����ʹ��ǩ��<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAllowAutograph(String allowAutograph) {
		this.AllowAutograph = allowAutograph;
    }

	/**
	* ��ȡ�ֶ�Verify��ֵ�����ֶε�<br>
	* �ֶ����� :����ֱ�ӷ���<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getVerify() {
		return Verify;
	}

	/**
	* �����ֶ�Verify��ֵ�����ֶε�<br>
	* �ֶ����� :����ֱ�ӷ���<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setVerify(String verify) {
		this.Verify = verify;
    }

	/**
	* ��ȡ�ֶ�AllowEditUser��ֵ�����ֶε�<br>
	* �ֶ����� :����༭�û�<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getAllowEditUser() {
		return AllowEditUser;
	}

	/**
	* �����ֶ�AllowEditUser��ֵ�����ֶε�<br>
	* �ֶ����� :����༭�û�<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAllowEditUser(String allowEditUser) {
		this.AllowEditUser = allowEditUser;
    }

	/**
	* ��ȡ�ֶ�AllowForbidUser��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֹ�û�<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getAllowForbidUser() {
		return AllowForbidUser;
	}

	/**
	* �����ֶ�AllowForbidUser��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֹ�û�<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAllowForbidUser(String allowForbidUser) {
		this.AllowForbidUser = allowForbidUser;
    }

	/**
	* ��ȡ�ֶ�AllowEditForum��ֵ�����ֶε�<br>
	* �ֶ����� :����༭���<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getAllowEditForum() {
		return AllowEditForum;
	}

	/**
	* �����ֶ�AllowEditForum��ֵ�����ֶε�<br>
	* �ֶ����� :����༭���<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAllowEditForum(String allowEditForum) {
		this.AllowEditForum = allowEditForum;
    }

	/**
	* ��ȡ�ֶ�AllowVerfyPost��ֵ�����ֶε�<br>
	* �ֶ����� :�����������<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getAllowVerfyPost() {
		return AllowVerfyPost;
	}

	/**
	* �����ֶ�AllowVerfyPost��ֵ�����ֶε�<br>
	* �ֶ����� :�����������<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAllowVerfyPost(String allowVerfyPost) {
		this.AllowVerfyPost = allowVerfyPost;
    }

	/**
	* ��ȡ�ֶ�AllowDel��ֵ�����ֶε�<br>
	* �ֶ����� :����ɾ��<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getAllowDel() {
		return AllowDel;
	}

	/**
	* �����ֶ�AllowDel��ֵ�����ֶε�<br>
	* �ֶ����� :����ɾ��<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAllowDel(String allowDel) {
		this.AllowDel = allowDel;
    }

	/**
	* ��ȡ�ֶ�AllowEdit��ֵ�����ֶε�<br>
	* �ֶ����� :����༭<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getAllowEdit() {
		return AllowEdit;
	}

	/**
	* �����ֶ�AllowEdit��ֵ�����ֶε�<br>
	* �ֶ����� :����༭<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAllowEdit(String allowEdit) {
		this.AllowEdit = allowEdit;
    }

	/**
	* ��ȡ�ֶ�Hide��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getHide() {
		return Hide;
	}

	/**
	* �����ֶ�Hide��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setHide(String hide) {
		this.Hide = hide;
    }

	/**
	* ��ȡ�ֶ�RemoveTheme��ֵ�����ֶε�<br>
	* �ֶ����� :ɾ������<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getRemoveTheme() {
		return RemoveTheme;
	}

	/**
	* �����ֶ�RemoveTheme��ֵ�����ֶε�<br>
	* �ֶ����� :ɾ������<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setRemoveTheme(String removeTheme) {
		this.RemoveTheme = removeTheme;
    }

	/**
	* ��ȡ�ֶ�MoveTheme��ֵ�����ֶε�<br>
	* �ֶ����� :�ƶ�����<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getMoveTheme() {
		return MoveTheme;
	}

	/**
	* �����ֶ�MoveTheme��ֵ�����ֶε�<br>
	* �ֶ����� :�ƶ�����<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setMoveTheme(String moveTheme) {
		this.MoveTheme = moveTheme;
    }

	/**
	* ��ȡ�ֶ�TopTheme��ֵ�����ֶε�<br>
	* �ֶ����� :�ö�����<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getTopTheme() {
		return TopTheme;
	}

	/**
	* �����ֶ�TopTheme��ֵ�����ֶε�<br>
	* �ֶ����� :�ö�����<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setTopTheme(String topTheme) {
		this.TopTheme = topTheme;
    }

	/**
	* ��ȡ�ֶ�BrightTheme��ֵ�����ֶε�<br>
	* �ֶ����� :������ʾ<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getBrightTheme() {
		return BrightTheme;
	}

	/**
	* �����ֶ�BrightTheme��ֵ�����ֶε�<br>
	* �ֶ����� :������ʾ<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setBrightTheme(String brightTheme) {
		this.BrightTheme = brightTheme;
    }

	/**
	* ��ȡ�ֶ�UpOrDownTheme��ֵ�����ֶε�<br>
	* �ֶ����� :����/�³�����<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getUpOrDownTheme() {
		return UpOrDownTheme;
	}

	/**
	* �����ֶ�UpOrDownTheme��ֵ�����ֶε�<br>
	* �ֶ����� :����/�³�����<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setUpOrDownTheme(String upOrDownTheme) {
		this.UpOrDownTheme = upOrDownTheme;
    }

	/**
	* ��ȡ�ֶ�BestTheme��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getBestTheme() {
		return BestTheme;
	}

	/**
	* �����ֶ�BestTheme��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setBestTheme(String bestTheme) {
		this.BestTheme = bestTheme;
    }

	/**
	* ��ȡ�ֶ�prop1��ֵ�����ֶε�<br>
	* �ֶ����� :Ԥ���ֶ�1<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp1() {
		return prop1;
	}

	/**
	* �����ֶ�prop1��ֵ�����ֶε�<br>
	* �ֶ����� :Ԥ���ֶ�1<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp1(String prop1) {
		this.prop1 = prop1;
    }

	/**
	* ��ȡ�ֶ�prop2��ֵ�����ֶε�<br>
	* �ֶ����� :Ԥ���ֶ�2<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp2() {
		return prop2;
	}

	/**
	* �����ֶ�prop2��ֵ�����ֶε�<br>
	* �ֶ����� :Ԥ���ֶ�2<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp2(String prop2) {
		this.prop2 = prop2;
    }

	/**
	* ��ȡ�ֶ�prop3��ֵ�����ֶε�<br>
	* �ֶ����� :Ԥ���ֶ�3<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp3() {
		return prop3;
	}

	/**
	* �����ֶ�prop3��ֵ�����ֶε�<br>
	* �ֶ����� :Ԥ���ֶ�3<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp3(String prop3) {
		this.prop3 = prop3;
    }

	/**
	* ��ȡ�ֶ�prop4��ֵ�����ֶε�<br>
	* �ֶ����� :Ԥ���ֶ�4<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp4() {
		return prop4;
	}

	/**
	* �����ֶ�prop4��ֵ�����ֶε�<br>
	* �ֶ����� :Ԥ���ֶ�4<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp4(String prop4) {
		this.prop4 = prop4;
    }

	/**
	* ��ȡ�ֶ�AddUser��ֵ�����ֶε�<br>
	* �ֶ����� :�����<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getAddUser() {
		return AddUser;
	}

	/**
	* �����ֶ�AddUser��ֵ�����ֶε�<br>
	* �ֶ����� :�����<br>
	* �������� :varchar(100)<br>
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
	* ��ȡ�ֶ�OrderFlag��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getOrderFlag() {
		if(OrderFlag==null){return 0;}
		return OrderFlag.longValue();
	}

	/**
	* �����ֶ�OrderFlag��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setOrderFlag(long orderFlag) {
		this.OrderFlag = new Long(orderFlag);
    }

	/**
	* �����ֶ�OrderFlag��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setOrderFlag(String orderFlag) {
		if (orderFlag == null){
			this.OrderFlag = null;
			return;
		}
		this.OrderFlag = new Long(orderFlag);
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