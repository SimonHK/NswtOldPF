package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * �����ƣ���̳������<br>
 * ����룺BZCForum<br>
 * ��������ID, BackupNo<br>
 */
public class BZCForumSchema extends Schema {
	private Long ID;

	private Long SiteID;

	private Long ParentID;

	private String Type;

	private String Name;

	private String Status;

	private String Pic;

	private String Visible;

	private String Info;

	private Integer ThemeCount;

	private String Verify;

	private String Locked;

	private String UnLockID;

	private String AllowTheme;

	private String EditPost;

	private String ReplyPost;

	private String Recycle;

	private String AllowHTML;

	private String AllowFace;

	private String AnonyPost;

	private String URL;

	private String Image;

	private String Password;

	private String UnPasswordID;

	private String Word;

	private Integer PostCount;

	private String ForumAdmin;

	private Integer TodayPostCount;

	private Long LastThemeID;

	private String LastPost;

	private String LastPoster;

	private Long OrderFlag;

	private String prop1;

	private String prop2;

	private String prop3;

	private String prop4;

	private String AddUser;

	private Date AddTime;

	private String ModifyUser;

	private Date ModifyTime;

	private String BackupNo;

	private String BackupOperator;

	private Date BackupTime;

	private String BackupMemo;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 0 , 0 , true , true),
		new SchemaColumn("SiteID", DataColumn.LONG, 1, 0 , 0 , false , false),
		new SchemaColumn("ParentID", DataColumn.LONG, 2, 0 , 0 , true , false),
		new SchemaColumn("Type", DataColumn.STRING, 3, 20 , 0 , true , false),
		new SchemaColumn("Name", DataColumn.STRING, 4, 100 , 0 , true , false),
		new SchemaColumn("Status", DataColumn.STRING, 5, 2 , 0 , true , false),
		new SchemaColumn("Pic", DataColumn.STRING, 6, 100 , 0 , false , false),
		new SchemaColumn("Visible", DataColumn.STRING, 7, 2 , 0 , false , false),
		new SchemaColumn("Info", DataColumn.STRING, 8, 1000 , 0 , false , false),
		new SchemaColumn("ThemeCount", DataColumn.INTEGER, 9, 0 , 0 , true , false),
		new SchemaColumn("Verify", DataColumn.STRING, 10, 2 , 0 , false , false),
		new SchemaColumn("Locked", DataColumn.STRING, 11, 2 , 0 , false , false),
		new SchemaColumn("UnLockID", DataColumn.STRING, 12, 300 , 0 , false , false),
		new SchemaColumn("AllowTheme", DataColumn.STRING, 13, 2 , 0 , false , false),
		new SchemaColumn("EditPost", DataColumn.STRING, 14, 2 , 0 , false , false),
		new SchemaColumn("ReplyPost", DataColumn.STRING, 15, 2 , 0 , false , false),
		new SchemaColumn("Recycle", DataColumn.STRING, 16, 2 , 0 , false , false),
		new SchemaColumn("AllowHTML", DataColumn.STRING, 17, 2 , 0 , false , false),
		new SchemaColumn("AllowFace", DataColumn.STRING, 18, 2 , 0 , false , false),
		new SchemaColumn("AnonyPost", DataColumn.STRING, 19, 2 , 0 , false , false),
		new SchemaColumn("URL", DataColumn.STRING, 20, 200 , 0 , false , false),
		new SchemaColumn("Image", DataColumn.STRING, 21, 200 , 0 , false , false),
		new SchemaColumn("Password", DataColumn.STRING, 22, 100 , 0 , false , false),
		new SchemaColumn("UnPasswordID", DataColumn.STRING, 23, 300 , 0 , false , false),
		new SchemaColumn("Word", DataColumn.STRING, 24, 200 , 0 , false , false),
		new SchemaColumn("PostCount", DataColumn.INTEGER, 25, 0 , 0 , true , false),
		new SchemaColumn("ForumAdmin", DataColumn.STRING, 26, 100 , 0 , false , false),
		new SchemaColumn("TodayPostCount", DataColumn.INTEGER, 27, 0 , 0 , false , false),
		new SchemaColumn("LastThemeID", DataColumn.LONG, 28, 0 , 0 , false , false),
		new SchemaColumn("LastPost", DataColumn.STRING, 29, 100 , 0 , false , false),
		new SchemaColumn("LastPoster", DataColumn.STRING, 30, 100 , 0 , false , false),
		new SchemaColumn("OrderFlag", DataColumn.LONG, 31, 0 , 0 , true , false),
		new SchemaColumn("prop1", DataColumn.STRING, 32, 50 , 0 , false , false),
		new SchemaColumn("prop2", DataColumn.STRING, 33, 50 , 0 , false , false),
		new SchemaColumn("prop3", DataColumn.STRING, 34, 50 , 0 , false , false),
		new SchemaColumn("prop4", DataColumn.STRING, 35, 50 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 36, 100 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 37, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 38, 100 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 39, 0 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 40, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 41, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 42, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 43, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZCForum";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into BZCForum values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZCForum set ID=?,SiteID=?,ParentID=?,Type=?,Name=?,Status=?,Pic=?,Visible=?,Info=?,ThemeCount=?,Verify=?,Locked=?,UnLockID=?,AllowTheme=?,EditPost=?,ReplyPost=?,Recycle=?,AllowHTML=?,AllowFace=?,AnonyPost=?,URL=?,Image=?,Password=?,UnPasswordID=?,Word=?,PostCount=?,ForumAdmin=?,TodayPostCount=?,LastThemeID=?,LastPost=?,LastPoster=?,OrderFlag=?,prop1=?,prop2=?,prop3=?,prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZCForum  where ID=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZCForum  where ID=? and BackupNo=?";

	public BZCForumSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[44];
	}

	protected Schema newInstance(){
		return new BZCForumSchema();
	}

	protected SchemaSet newSet(){
		return new BZCForumSet();
	}

	public BZCForumSet query() {
		return query(null, -1, -1);
	}

	public BZCForumSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZCForumSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZCForumSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZCForumSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 2){if(v==null){ParentID = null;}else{ParentID = new Long(v.toString());}return;}
		if (i == 3){Type = (String)v;return;}
		if (i == 4){Name = (String)v;return;}
		if (i == 5){Status = (String)v;return;}
		if (i == 6){Pic = (String)v;return;}
		if (i == 7){Visible = (String)v;return;}
		if (i == 8){Info = (String)v;return;}
		if (i == 9){if(v==null){ThemeCount = null;}else{ThemeCount = new Integer(v.toString());}return;}
		if (i == 10){Verify = (String)v;return;}
		if (i == 11){Locked = (String)v;return;}
		if (i == 12){UnLockID = (String)v;return;}
		if (i == 13){AllowTheme = (String)v;return;}
		if (i == 14){EditPost = (String)v;return;}
		if (i == 15){ReplyPost = (String)v;return;}
		if (i == 16){Recycle = (String)v;return;}
		if (i == 17){AllowHTML = (String)v;return;}
		if (i == 18){AllowFace = (String)v;return;}
		if (i == 19){AnonyPost = (String)v;return;}
		if (i == 20){URL = (String)v;return;}
		if (i == 21){Image = (String)v;return;}
		if (i == 22){Password = (String)v;return;}
		if (i == 23){UnPasswordID = (String)v;return;}
		if (i == 24){Word = (String)v;return;}
		if (i == 25){if(v==null){PostCount = null;}else{PostCount = new Integer(v.toString());}return;}
		if (i == 26){ForumAdmin = (String)v;return;}
		if (i == 27){if(v==null){TodayPostCount = null;}else{TodayPostCount = new Integer(v.toString());}return;}
		if (i == 28){if(v==null){LastThemeID = null;}else{LastThemeID = new Long(v.toString());}return;}
		if (i == 29){LastPost = (String)v;return;}
		if (i == 30){LastPoster = (String)v;return;}
		if (i == 31){if(v==null){OrderFlag = null;}else{OrderFlag = new Long(v.toString());}return;}
		if (i == 32){prop1 = (String)v;return;}
		if (i == 33){prop2 = (String)v;return;}
		if (i == 34){prop3 = (String)v;return;}
		if (i == 35){prop4 = (String)v;return;}
		if (i == 36){AddUser = (String)v;return;}
		if (i == 37){AddTime = (Date)v;return;}
		if (i == 38){ModifyUser = (String)v;return;}
		if (i == 39){ModifyTime = (Date)v;return;}
		if (i == 40){BackupNo = (String)v;return;}
		if (i == 41){BackupOperator = (String)v;return;}
		if (i == 42){BackupTime = (Date)v;return;}
		if (i == 43){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return SiteID;}
		if (i == 2){return ParentID;}
		if (i == 3){return Type;}
		if (i == 4){return Name;}
		if (i == 5){return Status;}
		if (i == 6){return Pic;}
		if (i == 7){return Visible;}
		if (i == 8){return Info;}
		if (i == 9){return ThemeCount;}
		if (i == 10){return Verify;}
		if (i == 11){return Locked;}
		if (i == 12){return UnLockID;}
		if (i == 13){return AllowTheme;}
		if (i == 14){return EditPost;}
		if (i == 15){return ReplyPost;}
		if (i == 16){return Recycle;}
		if (i == 17){return AllowHTML;}
		if (i == 18){return AllowFace;}
		if (i == 19){return AnonyPost;}
		if (i == 20){return URL;}
		if (i == 21){return Image;}
		if (i == 22){return Password;}
		if (i == 23){return UnPasswordID;}
		if (i == 24){return Word;}
		if (i == 25){return PostCount;}
		if (i == 26){return ForumAdmin;}
		if (i == 27){return TodayPostCount;}
		if (i == 28){return LastThemeID;}
		if (i == 29){return LastPost;}
		if (i == 30){return LastPoster;}
		if (i == 31){return OrderFlag;}
		if (i == 32){return prop1;}
		if (i == 33){return prop2;}
		if (i == 34){return prop3;}
		if (i == 35){return prop4;}
		if (i == 36){return AddUser;}
		if (i == 37){return AddTime;}
		if (i == 38){return ModifyUser;}
		if (i == 39){return ModifyTime;}
		if (i == 40){return BackupNo;}
		if (i == 41){return BackupOperator;}
		if (i == 42){return BackupTime;}
		if (i == 43){return BackupMemo;}
		return null;
	}

	/**
	* ��ȡ�ֶ�ID��ֵ�����ֶε�<br>
	* �ֶ����� :���ID<br>
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
	* �ֶ����� :���ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setID(long iD) {
		this.ID = new Long(iD);
    }

	/**
	* �����ֶ�ID��ֵ�����ֶε�<br>
	* �ֶ����� :���ID<br>
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
	* ��ȡ�ֶ�ParentID��ֵ�����ֶε�<br>
	* �ֶ����� :��ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public long getParentID() {
		if(ParentID==null){return 0;}
		return ParentID.longValue();
	}

	/**
	* �����ֶ�ParentID��ֵ�����ֶε�<br>
	* �ֶ����� :��ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setParentID(long parentID) {
		this.ParentID = new Long(parentID);
    }

	/**
	* �����ֶ�ParentID��ֵ�����ֶε�<br>
	* �ֶ����� :��ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setParentID(String parentID) {
		if (parentID == null){
			this.ParentID = null;
			return;
		}
		this.ParentID = new Long(parentID);
    }

	/**
	* ��ȡ�ֶ�Type��ֵ�����ֶε�<br>
	* �ֶ����� :�������<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getType() {
		return Type;
	}

	/**
	* �����ֶ�Type��ֵ�����ֶε�<br>
	* �ֶ����� :�������<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setType(String type) {
		this.Type = type;
    }

	/**
	* ��ȡ�ֶ�Name��ֵ�����ֶε�<br>
	* �ֶ����� :�������<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getName() {
		return Name;
	}

	/**
	* �����ֶ�Name��ֵ�����ֶε�<br>
	* �ֶ����� :�������<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setName(String name) {
		this.Name = name;
    }

	/**
	* ��ȡ�ֶ�Status��ֵ�����ֶε�<br>
	* �ֶ����� :״̬<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getStatus() {
		return Status;
	}

	/**
	* �����ֶ�Status��ֵ�����ֶε�<br>
	* �ֶ����� :״̬<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setStatus(String status) {
		this.Status = status;
    }

	/**
	* ��ȡ�ֶ�Pic��ֵ�����ֶε�<br>
	* �ֶ����� :�������ͼ<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getPic() {
		return Pic;
	}

	/**
	* �����ֶ�Pic��ֵ�����ֶε�<br>
	* �ֶ����� :�������ͼ<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setPic(String pic) {
		this.Pic = pic;
    }

	/**
	* ��ȡ�ֶ�Visible��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ���ʾ<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getVisible() {
		return Visible;
	}

	/**
	* �����ֶ�Visible��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ���ʾ<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setVisible(String visible) {
		this.Visible = visible;
    }

	/**
	* ��ȡ�ֶ�Info��ֵ�����ֶε�<br>
	* �ֶ����� :������<br>
	* �������� :varchar(1000)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getInfo() {
		return Info;
	}

	/**
	* �����ֶ�Info��ֵ�����ֶε�<br>
	* �ֶ����� :������<br>
	* �������� :varchar(1000)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setInfo(String info) {
		this.Info = info;
    }

	/**
	* ��ȡ�ֶ�ThemeCount��ֵ�����ֶε�<br>
	* �ֶ����� :������<br>
	* �������� :int<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public int getThemeCount() {
		if(ThemeCount==null){return 0;}
		return ThemeCount.intValue();
	}

	/**
	* �����ֶ�ThemeCount��ֵ�����ֶε�<br>
	* �ֶ����� :������<br>
	* �������� :int<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setThemeCount(int themeCount) {
		this.ThemeCount = new Integer(themeCount);
    }

	/**
	* �����ֶ�ThemeCount��ֵ�����ֶε�<br>
	* �ֶ����� :������<br>
	* �������� :int<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setThemeCount(String themeCount) {
		if (themeCount == null){
			this.ThemeCount = null;
			return;
		}
		this.ThemeCount = new Integer(themeCount);
    }

	/**
	* ��ȡ�ֶ�Verify��ֵ�����ֶε�<br>
	* �ֶ����� :�������<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getVerify() {
		return Verify;
	}

	/**
	* �����ֶ�Verify��ֵ�����ֶε�<br>
	* �ֶ����� :�������<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setVerify(String verify) {
		this.Verify = verify;
    }

	/**
	* ��ȡ�ֶ�Locked��ֵ�����ֶε�<br>
	* �ֶ����� :����Ƿ�����<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getLocked() {
		return Locked;
	}

	/**
	* �����ֶ�Locked��ֵ�����ֶε�<br>
	* �ֶ����� :����Ƿ�����<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setLocked(String locked) {
		this.Locked = locked;
    }

	/**
	* ��ȡ�ֶ�UnLockID��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :varchar(300)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getUnLockID() {
		return UnLockID;
	}

	/**
	* �����ֶ�UnLockID��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :varchar(300)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setUnLockID(String unLockID) {
		this.UnLockID = unLockID;
    }

	/**
	* ��ȡ�ֶ�AllowTheme��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ�������<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getAllowTheme() {
		return AllowTheme;
	}

	/**
	* �����ֶ�AllowTheme��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ�������<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAllowTheme(String allowTheme) {
		this.AllowTheme = allowTheme;
    }

	/**
	* ��ȡ�ֶ�EditPost��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ�����༭<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getEditPost() {
		return EditPost;
	}

	/**
	* �����ֶ�EditPost��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ�����༭<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setEditPost(String editPost) {
		this.EditPost = editPost;
    }

	/**
	* ��ȡ�ֶ�ReplyPost��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ�����ظ�<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getReplyPost() {
		return ReplyPost;
	}

	/**
	* �����ֶ�ReplyPost��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ�����ظ�<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setReplyPost(String replyPost) {
		this.ReplyPost = replyPost;
    }

	/**
	* ��ȡ�ֶ�Recycle��ֵ�����ֶε�<br>
	* �ֶ����� :�������վ<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getRecycle() {
		return Recycle;
	}

	/**
	* �����ֶ�Recycle��ֵ�����ֶε�<br>
	* �ֶ����� :�������վ<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setRecycle(String recycle) {
		this.Recycle = recycle;
    }

	/**
	* ��ȡ�ֶ�AllowHTML��ֵ�����ֶε�<br>
	* �ֶ����� :����HTML����<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getAllowHTML() {
		return AllowHTML;
	}

	/**
	* �����ֶ�AllowHTML��ֵ�����ֶε�<br>
	* �ֶ����� :����HTML����<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAllowHTML(String allowHTML) {
		this.AllowHTML = allowHTML;
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
	* ��ȡ�ֶ�AnonyPost��ֵ�����ֶε�<br>
	* �ֶ����� :������������<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getAnonyPost() {
		return AnonyPost;
	}

	/**
	* �����ֶ�AnonyPost��ֵ�����ֶε�<br>
	* �ֶ����� :������������<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAnonyPost(String anonyPost) {
		this.AnonyPost = anonyPost;
    }

	/**
	* ��ȡ�ֶ�URL��ֵ�����ֶε�<br>
	* �ֶ����� :URL<br>
	* �������� :varchar (200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getURL() {
		return URL;
	}

	/**
	* �����ֶ�URL��ֵ�����ֶε�<br>
	* �ֶ����� :URL<br>
	* �������� :varchar (200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setURL(String uRL) {
		this.URL = uRL;
    }

	/**
	* ��ȡ�ֶ�Image��ֵ�����ֶε�<br>
	* �ֶ����� :���ͼ��<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getImage() {
		return Image;
	}

	/**
	* �����ֶ�Image��ֵ�����ֶε�<br>
	* �ֶ����� :���ͼ��<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setImage(String image) {
		this.Image = image;
    }

	/**
	* ��ȡ�ֶ�Password��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getPassword() {
		return Password;
	}

	/**
	* �����ֶ�Password��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setPassword(String password) {
		this.Password = password;
    }

	/**
	* ��ȡ�ֶ�UnPasswordID��ֵ�����ֶε�<br>
	* �ֶ����� :�������������<br>
	* �������� :varchar(300)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getUnPasswordID() {
		return UnPasswordID;
	}

	/**
	* �����ֶ�UnPasswordID��ֵ�����ֶε�<br>
	* �ֶ����� :�������������<br>
	* �������� :varchar(300)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setUnPasswordID(String unPasswordID) {
		this.UnPasswordID = unPasswordID;
    }

	/**
	* ��ȡ�ֶ�Word��ֵ�����ֶε�<br>
	* �ֶ����� :�ؼ���<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getWord() {
		return Word;
	}

	/**
	* �����ֶ�Word��ֵ�����ֶε�<br>
	* �ֶ����� :�ؼ���<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setWord(String word) {
		this.Word = word;
    }

	/**
	* ��ȡ�ֶ�PostCount��ֵ�����ֶε�<br>
	* �ֶ����� :������<br>
	* �������� :int<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public int getPostCount() {
		if(PostCount==null){return 0;}
		return PostCount.intValue();
	}

	/**
	* �����ֶ�PostCount��ֵ�����ֶε�<br>
	* �ֶ����� :������<br>
	* �������� :int<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setPostCount(int postCount) {
		this.PostCount = new Integer(postCount);
    }

	/**
	* �����ֶ�PostCount��ֵ�����ֶε�<br>
	* �ֶ����� :������<br>
	* �������� :int<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setPostCount(String postCount) {
		if (postCount == null){
			this.PostCount = null;
			return;
		}
		this.PostCount = new Integer(postCount);
    }

	/**
	* ��ȡ�ֶ�ForumAdmin��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getForumAdmin() {
		return ForumAdmin;
	}

	/**
	* �����ֶ�ForumAdmin��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setForumAdmin(String forumAdmin) {
		this.ForumAdmin = forumAdmin;
    }

	/**
	* ��ȡ�ֶ�TodayPostCount��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :int<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public int getTodayPostCount() {
		if(TodayPostCount==null){return 0;}
		return TodayPostCount.intValue();
	}

	/**
	* �����ֶ�TodayPostCount��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :int<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setTodayPostCount(int todayPostCount) {
		this.TodayPostCount = new Integer(todayPostCount);
    }

	/**
	* �����ֶ�TodayPostCount��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :int<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setTodayPostCount(String todayPostCount) {
		if (todayPostCount == null){
			this.TodayPostCount = null;
			return;
		}
		this.TodayPostCount = new Integer(todayPostCount);
    }

	/**
	* ��ȡ�ֶ�LastThemeID��ֵ�����ֶε�<br>
	* �ֶ����� :������ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getLastThemeID() {
		if(LastThemeID==null){return 0;}
		return LastThemeID.longValue();
	}

	/**
	* �����ֶ�LastThemeID��ֵ�����ֶε�<br>
	* �ֶ����� :������ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setLastThemeID(long lastThemeID) {
		this.LastThemeID = new Long(lastThemeID);
    }

	/**
	* �����ֶ�LastThemeID��ֵ�����ֶε�<br>
	* �ֶ����� :������ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setLastThemeID(String lastThemeID) {
		if (lastThemeID == null){
			this.LastThemeID = null;
			return;
		}
		this.LastThemeID = new Long(lastThemeID);
    }

	/**
	* ��ȡ�ֶ�LastPost��ֵ�����ֶε�<br>
	* �ֶ����� :������<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getLastPost() {
		return LastPost;
	}

	/**
	* �����ֶ�LastPost��ֵ�����ֶε�<br>
	* �ֶ����� :������<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setLastPost(String lastPost) {
		this.LastPost = lastPost;
    }

	/**
	* ��ȡ�ֶ�LastPoster��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getLastPoster() {
		return LastPoster;
	}

	/**
	* �����ֶ�LastPoster��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setLastPoster(String lastPoster) {
		this.LastPoster = lastPoster;
    }

	/**
	* ��ȡ�ֶ�OrderFlag��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
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
	* �Ƿ���� :true<br>
	*/
	public void setOrderFlag(long orderFlag) {
		this.OrderFlag = new Long(orderFlag);
    }

	/**
	* �����ֶ�OrderFlag��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setOrderFlag(String orderFlag) {
		if (orderFlag == null){
			this.OrderFlag = null;
			return;
		}
		this.OrderFlag = new Long(orderFlag);
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