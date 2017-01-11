package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * �����ƣ����������<br>
 * ����룺BZCAdminGroup<br>
 * ��������GroupID, BackupNo<br>
 */
public class BZCAdminGroupSchema extends Schema {
	private Long GroupID;

	private Long SiteID;

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

	private String BackupNo;

	private String BackupOperator;

	private Date BackupTime;

	private String BackupMemo;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("GroupID", DataColumn.LONG, 0, 0 , 0 , true , true),
		new SchemaColumn("SiteID", DataColumn.LONG, 1, 0 , 0 , false , false),
		new SchemaColumn("AllowEditUser", DataColumn.STRING, 2, 2 , 0 , false , false),
		new SchemaColumn("AllowForbidUser", DataColumn.STRING, 3, 2 , 0 , false , false),
		new SchemaColumn("AllowEditForum", DataColumn.STRING, 4, 2 , 0 , false , false),
		new SchemaColumn("AllowVerfyPost", DataColumn.STRING, 5, 2 , 0 , false , false),
		new SchemaColumn("AllowDel", DataColumn.STRING, 6, 2 , 0 , false , false),
		new SchemaColumn("AllowEdit", DataColumn.STRING, 7, 2 , 0 , false , false),
		new SchemaColumn("Hide", DataColumn.STRING, 8, 2 , 0 , false , false),
		new SchemaColumn("RemoveTheme", DataColumn.STRING, 9, 2 , 0 , false , false),
		new SchemaColumn("MoveTheme", DataColumn.STRING, 10, 2 , 0 , false , false),
		new SchemaColumn("TopTheme", DataColumn.STRING, 11, 2 , 0 , false , false),
		new SchemaColumn("BrightTheme", DataColumn.STRING, 12, 2 , 0 , false , false),
		new SchemaColumn("UpOrDownTheme", DataColumn.STRING, 13, 2 , 0 , false , false),
		new SchemaColumn("BestTheme", DataColumn.STRING, 14, 2 , 0 , false , false),
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

	public static final String _TableCode = "BZCAdminGroup";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into BZCAdminGroup values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZCAdminGroup set GroupID=?,SiteID=?,AllowEditUser=?,AllowForbidUser=?,AllowEditForum=?,AllowVerfyPost=?,AllowDel=?,AllowEdit=?,Hide=?,RemoveTheme=?,MoveTheme=?,TopTheme=?,BrightTheme=?,UpOrDownTheme=?,BestTheme=?,prop1=?,prop2=?,prop3=?,prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where GroupID=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZCAdminGroup  where GroupID=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZCAdminGroup  where GroupID=? and BackupNo=?";

	public BZCAdminGroupSchema(){
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
		return new BZCAdminGroupSchema();
	}

	protected SchemaSet newSet(){
		return new BZCAdminGroupSet();
	}

	public BZCAdminGroupSet query() {
		return query(null, -1, -1);
	}

	public BZCAdminGroupSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZCAdminGroupSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZCAdminGroupSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZCAdminGroupSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){GroupID = null;}else{GroupID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 2){AllowEditUser = (String)v;return;}
		if (i == 3){AllowForbidUser = (String)v;return;}
		if (i == 4){AllowEditForum = (String)v;return;}
		if (i == 5){AllowVerfyPost = (String)v;return;}
		if (i == 6){AllowDel = (String)v;return;}
		if (i == 7){AllowEdit = (String)v;return;}
		if (i == 8){Hide = (String)v;return;}
		if (i == 9){RemoveTheme = (String)v;return;}
		if (i == 10){MoveTheme = (String)v;return;}
		if (i == 11){TopTheme = (String)v;return;}
		if (i == 12){BrightTheme = (String)v;return;}
		if (i == 13){UpOrDownTheme = (String)v;return;}
		if (i == 14){BestTheme = (String)v;return;}
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
		if (i == 0){return GroupID;}
		if (i == 1){return SiteID;}
		if (i == 2){return AllowEditUser;}
		if (i == 3){return AllowForbidUser;}
		if (i == 4){return AllowEditForum;}
		if (i == 5){return AllowVerfyPost;}
		if (i == 6){return AllowDel;}
		if (i == 7){return AllowEdit;}
		if (i == 8){return Hide;}
		if (i == 9){return RemoveTheme;}
		if (i == 10){return MoveTheme;}
		if (i == 11){return TopTheme;}
		if (i == 12){return BrightTheme;}
		if (i == 13){return UpOrDownTheme;}
		if (i == 14){return BestTheme;}
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
	* ��ȡ�ֶ�GroupID��ֵ�����ֶε�<br>
	* �ֶ����� :GroupID<br>
	* �������� :bigint<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public long getGroupID() {
		if(GroupID==null){return 0;}
		return GroupID.longValue();
	}

	/**
	* �����ֶ�GroupID��ֵ�����ֶε�<br>
	* �ֶ����� :GroupID<br>
	* �������� :bigint<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setGroupID(long groupID) {
		this.GroupID = new Long(groupID);
    }

	/**
	* �����ֶ�GroupID��ֵ�����ֶε�<br>
	* �ֶ����� :GroupID<br>
	* �������� :bigint<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setGroupID(String groupID) {
		if (groupID == null){
			this.GroupID = null;
			return;
		}
		this.GroupID = new Long(groupID);
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