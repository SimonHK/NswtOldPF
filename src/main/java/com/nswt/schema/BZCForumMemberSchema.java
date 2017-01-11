package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * �����ƣ���̳��Ա����<br>
 * ����룺BZCForumMember<br>
 * ��������UserName, BackupNo<br>
 */
public class BZCForumMemberSchema extends Schema {
	private String UserName;

	private Long SiteID;

	private Long AdminID;

	private Long UserGroupID;

	private Long DefinedID;

	private String NickName;

	private Long ThemeCount;

	private Long ReplyCount;

	private String HeadImage;

	private String UseSelfImage;

	private String Status;

	private Long ForumScore;

	private String ForumSign;

	private Date LastLoginTime;

	private Date LastLogoutTime;

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
		new SchemaColumn("UserName", DataColumn.STRING, 0, 50 , 0 , true , true),
		new SchemaColumn("SiteID", DataColumn.LONG, 1, 0 , 0 , false , false),
		new SchemaColumn("AdminID", DataColumn.LONG, 2, 0 , 0 , false , false),
		new SchemaColumn("UserGroupID", DataColumn.LONG, 3, 0 , 0 , false , false),
		new SchemaColumn("DefinedID", DataColumn.LONG, 4, 0 , 0 , false , false),
		new SchemaColumn("NickName", DataColumn.STRING, 5, 100 , 0 , false , false),
		new SchemaColumn("ThemeCount", DataColumn.LONG, 6, 0 , 0 , false , false),
		new SchemaColumn("ReplyCount", DataColumn.LONG, 7, 0 , 0 , false , false),
		new SchemaColumn("HeadImage", DataColumn.STRING, 8, 500 , 0 , false , false),
		new SchemaColumn("UseSelfImage", DataColumn.STRING, 9, 2 , 0 , false , false),
		new SchemaColumn("Status", DataColumn.STRING, 10, 2 , 0 , false , false),
		new SchemaColumn("ForumScore", DataColumn.LONG, 11, 0 , 0 , false , false),
		new SchemaColumn("ForumSign", DataColumn.STRING, 12, 1000 , 0 , false , false),
		new SchemaColumn("LastLoginTime", DataColumn.DATETIME, 13, 0 , 0 , false , false),
		new SchemaColumn("LastLogoutTime", DataColumn.DATETIME, 14, 0 , 0 , false , false),
		new SchemaColumn("prop1", DataColumn.STRING, 15, 50 , 0 , false , false),
		new SchemaColumn("prop2", DataColumn.STRING, 16, 50 , 0 , false , false),
		new SchemaColumn("prop3", DataColumn.STRING, 17, 50 , 0 , false , false),
		new SchemaColumn("prop4", DataColumn.STRING, 18, 50 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 19, 100 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 20, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 21, 100 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 22, 0 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 23, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 24, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 25, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 26, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZCForumMember";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into BZCForumMember values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZCForumMember set UserName=?,SiteID=?,AdminID=?,UserGroupID=?,DefinedID=?,NickName=?,ThemeCount=?,ReplyCount=?,HeadImage=?,UseSelfImage=?,Status=?,ForumScore=?,ForumSign=?,LastLoginTime=?,LastLogoutTime=?,prop1=?,prop2=?,prop3=?,prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where UserName=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZCForumMember  where UserName=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZCForumMember  where UserName=? and BackupNo=?";

	public BZCForumMemberSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[27];
	}

	protected Schema newInstance(){
		return new BZCForumMemberSchema();
	}

	protected SchemaSet newSet(){
		return new BZCForumMemberSet();
	}

	public BZCForumMemberSet query() {
		return query(null, -1, -1);
	}

	public BZCForumMemberSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZCForumMemberSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZCForumMemberSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZCForumMemberSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){UserName = (String)v;return;}
		if (i == 1){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 2){if(v==null){AdminID = null;}else{AdminID = new Long(v.toString());}return;}
		if (i == 3){if(v==null){UserGroupID = null;}else{UserGroupID = new Long(v.toString());}return;}
		if (i == 4){if(v==null){DefinedID = null;}else{DefinedID = new Long(v.toString());}return;}
		if (i == 5){NickName = (String)v;return;}
		if (i == 6){if(v==null){ThemeCount = null;}else{ThemeCount = new Long(v.toString());}return;}
		if (i == 7){if(v==null){ReplyCount = null;}else{ReplyCount = new Long(v.toString());}return;}
		if (i == 8){HeadImage = (String)v;return;}
		if (i == 9){UseSelfImage = (String)v;return;}
		if (i == 10){Status = (String)v;return;}
		if (i == 11){if(v==null){ForumScore = null;}else{ForumScore = new Long(v.toString());}return;}
		if (i == 12){ForumSign = (String)v;return;}
		if (i == 13){LastLoginTime = (Date)v;return;}
		if (i == 14){LastLogoutTime = (Date)v;return;}
		if (i == 15){prop1 = (String)v;return;}
		if (i == 16){prop2 = (String)v;return;}
		if (i == 17){prop3 = (String)v;return;}
		if (i == 18){prop4 = (String)v;return;}
		if (i == 19){AddUser = (String)v;return;}
		if (i == 20){AddTime = (Date)v;return;}
		if (i == 21){ModifyUser = (String)v;return;}
		if (i == 22){ModifyTime = (Date)v;return;}
		if (i == 23){BackupNo = (String)v;return;}
		if (i == 24){BackupOperator = (String)v;return;}
		if (i == 25){BackupTime = (Date)v;return;}
		if (i == 26){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return UserName;}
		if (i == 1){return SiteID;}
		if (i == 2){return AdminID;}
		if (i == 3){return UserGroupID;}
		if (i == 4){return DefinedID;}
		if (i == 5){return NickName;}
		if (i == 6){return ThemeCount;}
		if (i == 7){return ReplyCount;}
		if (i == 8){return HeadImage;}
		if (i == 9){return UseSelfImage;}
		if (i == 10){return Status;}
		if (i == 11){return ForumScore;}
		if (i == 12){return ForumSign;}
		if (i == 13){return LastLoginTime;}
		if (i == 14){return LastLogoutTime;}
		if (i == 15){return prop1;}
		if (i == 16){return prop2;}
		if (i == 17){return prop3;}
		if (i == 18){return prop4;}
		if (i == 19){return AddUser;}
		if (i == 20){return AddTime;}
		if (i == 21){return ModifyUser;}
		if (i == 22){return ModifyTime;}
		if (i == 23){return BackupNo;}
		if (i == 24){return BackupOperator;}
		if (i == 25){return BackupTime;}
		if (i == 26){return BackupMemo;}
		return null;
	}

	/**
	* ��ȡ�ֶ�UserName��ֵ�����ֶε�<br>
	* �ֶ����� :�û���<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public String getUserName() {
		return UserName;
	}

	/**
	* �����ֶ�UserName��ֵ�����ֶε�<br>
	* �ֶ����� :�û���<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setUserName(String userName) {
		this.UserName = userName;
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
	* ��ȡ�ֶ�AdminID��ֵ�����ֶε�<br>
	* �ֶ����� :ϵͳ��ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getAdminID() {
		if(AdminID==null){return 0;}
		return AdminID.longValue();
	}

	/**
	* �����ֶ�AdminID��ֵ�����ֶε�<br>
	* �ֶ����� :ϵͳ��ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAdminID(long adminID) {
		this.AdminID = new Long(adminID);
    }

	/**
	* �����ֶ�AdminID��ֵ�����ֶε�<br>
	* �ֶ����� :ϵͳ��ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAdminID(String adminID) {
		if (adminID == null){
			this.AdminID = null;
			return;
		}
		this.AdminID = new Long(adminID);
    }

	/**
	* ��ȡ�ֶ�UserGroupID��ֵ�����ֶε�<br>
	* �ֶ����� :������ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getUserGroupID() {
		if(UserGroupID==null){return 0;}
		return UserGroupID.longValue();
	}

	/**
	* �����ֶ�UserGroupID��ֵ�����ֶε�<br>
	* �ֶ����� :������ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setUserGroupID(long userGroupID) {
		this.UserGroupID = new Long(userGroupID);
    }

	/**
	* �����ֶ�UserGroupID��ֵ�����ֶε�<br>
	* �ֶ����� :������ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setUserGroupID(String userGroupID) {
		if (userGroupID == null){
			this.UserGroupID = null;
			return;
		}
		this.UserGroupID = new Long(userGroupID);
    }

	/**
	* ��ȡ�ֶ�DefinedID��ֵ�����ֶε�<br>
	* �ֶ����� :�Զ�����ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getDefinedID() {
		if(DefinedID==null){return 0;}
		return DefinedID.longValue();
	}

	/**
	* �����ֶ�DefinedID��ֵ�����ֶε�<br>
	* �ֶ����� :�Զ�����ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setDefinedID(long definedID) {
		this.DefinedID = new Long(definedID);
    }

	/**
	* �����ֶ�DefinedID��ֵ�����ֶε�<br>
	* �ֶ����� :�Զ�����ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setDefinedID(String definedID) {
		if (definedID == null){
			this.DefinedID = null;
			return;
		}
		this.DefinedID = new Long(definedID);
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
	* ��ȡ�ֶ�ThemeCount��ֵ�����ֶε�<br>
	* �ֶ����� :������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getThemeCount() {
		if(ThemeCount==null){return 0;}
		return ThemeCount.longValue();
	}

	/**
	* �����ֶ�ThemeCount��ֵ�����ֶε�<br>
	* �ֶ����� :������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setThemeCount(long themeCount) {
		this.ThemeCount = new Long(themeCount);
    }

	/**
	* �����ֶ�ThemeCount��ֵ�����ֶε�<br>
	* �ֶ����� :������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setThemeCount(String themeCount) {
		if (themeCount == null){
			this.ThemeCount = null;
			return;
		}
		this.ThemeCount = new Long(themeCount);
    }

	/**
	* ��ȡ�ֶ�ReplyCount��ֵ�����ֶε�<br>
	* �ֶ����� :�ظ���<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getReplyCount() {
		if(ReplyCount==null){return 0;}
		return ReplyCount.longValue();
	}

	/**
	* �����ֶ�ReplyCount��ֵ�����ֶε�<br>
	* �ֶ����� :�ظ���<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setReplyCount(long replyCount) {
		this.ReplyCount = new Long(replyCount);
    }

	/**
	* �����ֶ�ReplyCount��ֵ�����ֶε�<br>
	* �ֶ����� :�ظ���<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setReplyCount(String replyCount) {
		if (replyCount == null){
			this.ReplyCount = null;
			return;
		}
		this.ReplyCount = new Long(replyCount);
    }

	/**
	* ��ȡ�ֶ�HeadImage��ֵ�����ֶε�<br>
	* �ֶ����� :��̳ͷ��<br>
	* �������� :varchar(500)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getHeadImage() {
		return HeadImage;
	}

	/**
	* �����ֶ�HeadImage��ֵ�����ֶε�<br>
	* �ֶ����� :��̳ͷ��<br>
	* �������� :varchar(500)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setHeadImage(String headImage) {
		this.HeadImage = headImage;
    }

	/**
	* ��ȡ�ֶ�UseSelfImage��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ�ʹ���Զ���ͷ��<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getUseSelfImage() {
		return UseSelfImage;
	}

	/**
	* �����ֶ�UseSelfImage��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ�ʹ���Զ���ͷ��<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setUseSelfImage(String useSelfImage) {
		this.UseSelfImage = useSelfImage;
    }

	/**
	* ��ȡ�ֶ�Status��ֵ�����ֶε�<br>
	* �ֶ����� :�û�״̬<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getStatus() {
		return Status;
	}

	/**
	* �����ֶ�Status��ֵ�����ֶε�<br>
	* �ֶ����� :�û�״̬<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setStatus(String status) {
		this.Status = status;
    }

	/**
	* ��ȡ�ֶ�ForumScore��ֵ�����ֶε�<br>
	* �ֶ����� :��̳����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getForumScore() {
		if(ForumScore==null){return 0;}
		return ForumScore.longValue();
	}

	/**
	* �����ֶ�ForumScore��ֵ�����ֶε�<br>
	* �ֶ����� :��̳����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setForumScore(long forumScore) {
		this.ForumScore = new Long(forumScore);
    }

	/**
	* �����ֶ�ForumScore��ֵ�����ֶε�<br>
	* �ֶ����� :��̳����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setForumScore(String forumScore) {
		if (forumScore == null){
			this.ForumScore = null;
			return;
		}
		this.ForumScore = new Long(forumScore);
    }

	/**
	* ��ȡ�ֶ�ForumSign��ֵ�����ֶε�<br>
	* �ֶ����� :��̳ǩ��<br>
	* �������� :varchar(1000)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getForumSign() {
		return ForumSign;
	}

	/**
	* �����ֶ�ForumSign��ֵ�����ֶε�<br>
	* �ֶ����� :��̳ǩ��<br>
	* �������� :varchar(1000)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setForumSign(String forumSign) {
		this.ForumSign = forumSign;
    }

	/**
	* ��ȡ�ֶ�LastLoginTime��ֵ�����ֶε�<br>
	* �ֶ����� :����½ʱ��<br>
	* �������� :dateTime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public Date getLastLoginTime() {
		return LastLoginTime;
	}

	/**
	* �����ֶ�LastLoginTime��ֵ�����ֶε�<br>
	* �ֶ����� :����½ʱ��<br>
	* �������� :dateTime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setLastLoginTime(Date lastLoginTime) {
		this.LastLoginTime = lastLoginTime;
    }

	/**
	* ��ȡ�ֶ�LastLogoutTime��ֵ�����ֶε�<br>
	* �ֶ����� :����˳�ʱ��<br>
	* �������� :dateTime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public Date getLastLogoutTime() {
		return LastLogoutTime;
	}

	/**
	* �����ֶ�LastLogoutTime��ֵ�����ֶε�<br>
	* �ֶ����� :����˳�ʱ��<br>
	* �������� :dateTime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setLastLogoutTime(Date lastLogoutTime) {
		this.LastLogoutTime = lastLogoutTime;
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