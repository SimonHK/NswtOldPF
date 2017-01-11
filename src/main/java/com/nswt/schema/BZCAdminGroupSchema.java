package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * 表名称：管理组表备份<br>
 * 表代码：BZCAdminGroup<br>
 * 表主键：GroupID, BackupNo<br>
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
	* 获取字段GroupID的值，该字段的<br>
	* 字段名称 :GroupID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public long getGroupID() {
		if(GroupID==null){return 0;}
		return GroupID.longValue();
	}

	/**
	* 设置字段GroupID的值，该字段的<br>
	* 字段名称 :GroupID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setGroupID(long groupID) {
		this.GroupID = new Long(groupID);
    }

	/**
	* 设置字段GroupID的值，该字段的<br>
	* 字段名称 :GroupID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setGroupID(String groupID) {
		if (groupID == null){
			this.GroupID = null;
			return;
		}
		this.GroupID = new Long(groupID);
    }

	/**
	* 获取字段SiteID的值，该字段的<br>
	* 字段名称 :站点ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getSiteID() {
		if(SiteID==null){return 0;}
		return SiteID.longValue();
	}

	/**
	* 设置字段SiteID的值，该字段的<br>
	* 字段名称 :站点ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSiteID(long siteID) {
		this.SiteID = new Long(siteID);
    }

	/**
	* 设置字段SiteID的值，该字段的<br>
	* 字段名称 :站点ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSiteID(String siteID) {
		if (siteID == null){
			this.SiteID = null;
			return;
		}
		this.SiteID = new Long(siteID);
    }

	/**
	* 获取字段AllowEditUser的值，该字段的<br>
	* 字段名称 :允许编辑用户<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAllowEditUser() {
		return AllowEditUser;
	}

	/**
	* 设置字段AllowEditUser的值，该字段的<br>
	* 字段名称 :允许编辑用户<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAllowEditUser(String allowEditUser) {
		this.AllowEditUser = allowEditUser;
    }

	/**
	* 获取字段AllowForbidUser的值，该字段的<br>
	* 字段名称 :允许禁止用户<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAllowForbidUser() {
		return AllowForbidUser;
	}

	/**
	* 设置字段AllowForbidUser的值，该字段的<br>
	* 字段名称 :允许禁止用户<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAllowForbidUser(String allowForbidUser) {
		this.AllowForbidUser = allowForbidUser;
    }

	/**
	* 获取字段AllowEditForum的值，该字段的<br>
	* 字段名称 :允许编辑板块<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAllowEditForum() {
		return AllowEditForum;
	}

	/**
	* 设置字段AllowEditForum的值，该字段的<br>
	* 字段名称 :允许编辑板块<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAllowEditForum(String allowEditForum) {
		this.AllowEditForum = allowEditForum;
    }

	/**
	* 获取字段AllowVerfyPost的值，该字段的<br>
	* 字段名称 :允许审核帖子<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAllowVerfyPost() {
		return AllowVerfyPost;
	}

	/**
	* 设置字段AllowVerfyPost的值，该字段的<br>
	* 字段名称 :允许审核帖子<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAllowVerfyPost(String allowVerfyPost) {
		this.AllowVerfyPost = allowVerfyPost;
    }

	/**
	* 获取字段AllowDel的值，该字段的<br>
	* 字段名称 :允许删帖<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAllowDel() {
		return AllowDel;
	}

	/**
	* 设置字段AllowDel的值，该字段的<br>
	* 字段名称 :允许删帖<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAllowDel(String allowDel) {
		this.AllowDel = allowDel;
    }

	/**
	* 获取字段AllowEdit的值，该字段的<br>
	* 字段名称 :允许编辑<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAllowEdit() {
		return AllowEdit;
	}

	/**
	* 设置字段AllowEdit的值，该字段的<br>
	* 字段名称 :允许编辑<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAllowEdit(String allowEdit) {
		this.AllowEdit = allowEdit;
    }

	/**
	* 获取字段Hide的值，该字段的<br>
	* 字段名称 :允许屏蔽<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getHide() {
		return Hide;
	}

	/**
	* 设置字段Hide的值，该字段的<br>
	* 字段名称 :允许屏蔽<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setHide(String hide) {
		this.Hide = hide;
    }

	/**
	* 获取字段RemoveTheme的值，该字段的<br>
	* 字段名称 :删除主题<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRemoveTheme() {
		return RemoveTheme;
	}

	/**
	* 设置字段RemoveTheme的值，该字段的<br>
	* 字段名称 :删除主题<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRemoveTheme(String removeTheme) {
		this.RemoveTheme = removeTheme;
    }

	/**
	* 获取字段MoveTheme的值，该字段的<br>
	* 字段名称 :移动主题<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMoveTheme() {
		return MoveTheme;
	}

	/**
	* 设置字段MoveTheme的值，该字段的<br>
	* 字段名称 :移动主题<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMoveTheme(String moveTheme) {
		this.MoveTheme = moveTheme;
    }

	/**
	* 获取字段TopTheme的值，该字段的<br>
	* 字段名称 :置顶主题<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getTopTheme() {
		return TopTheme;
	}

	/**
	* 设置字段TopTheme的值，该字段的<br>
	* 字段名称 :置顶主题<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setTopTheme(String topTheme) {
		this.TopTheme = topTheme;
    }

	/**
	* 获取字段BrightTheme的值，该字段的<br>
	* 字段名称 :高亮显示<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getBrightTheme() {
		return BrightTheme;
	}

	/**
	* 设置字段BrightTheme的值，该字段的<br>
	* 字段名称 :高亮显示<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBrightTheme(String brightTheme) {
		this.BrightTheme = brightTheme;
    }

	/**
	* 获取字段UpOrDownTheme的值，该字段的<br>
	* 字段名称 :提升/下沉主题<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getUpOrDownTheme() {
		return UpOrDownTheme;
	}

	/**
	* 设置字段UpOrDownTheme的值，该字段的<br>
	* 字段名称 :提升/下沉主题<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setUpOrDownTheme(String upOrDownTheme) {
		this.UpOrDownTheme = upOrDownTheme;
    }

	/**
	* 获取字段BestTheme的值，该字段的<br>
	* 字段名称 :精华主题<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getBestTheme() {
		return BestTheme;
	}

	/**
	* 设置字段BestTheme的值，该字段的<br>
	* 字段名称 :精华主题<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBestTheme(String bestTheme) {
		this.BestTheme = bestTheme;
    }

	/**
	* 获取字段prop1的值，该字段的<br>
	* 字段名称 :预留字段1<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return prop1;
	}

	/**
	* 设置字段prop1的值，该字段的<br>
	* 字段名称 :预留字段1<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.prop1 = prop1;
    }

	/**
	* 获取字段prop2的值，该字段的<br>
	* 字段名称 :预留字段2<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return prop2;
	}

	/**
	* 设置字段prop2的值，该字段的<br>
	* 字段名称 :预留字段2<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.prop2 = prop2;
    }

	/**
	* 获取字段prop3的值，该字段的<br>
	* 字段名称 :预留字段3<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp3() {
		return prop3;
	}

	/**
	* 设置字段prop3的值，该字段的<br>
	* 字段名称 :预留字段3<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp3(String prop3) {
		this.prop3 = prop3;
    }

	/**
	* 获取字段prop4的值，该字段的<br>
	* 字段名称 :预留字段4<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp4() {
		return prop4;
	}

	/**
	* 设置字段prop4的值，该字段的<br>
	* 字段名称 :预留字段4<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp4(String prop4) {
		this.prop4 = prop4;
    }

	/**
	* 获取字段AddUser的值，该字段的<br>
	* 字段名称 :添加人<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getAddUser() {
		return AddUser;
	}

	/**
	* 设置字段AddUser的值，该字段的<br>
	* 字段名称 :添加人<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setAddUser(String addUser) {
		this.AddUser = addUser;
    }

	/**
	* 获取字段AddTime的值，该字段的<br>
	* 字段名称 :添加时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public Date getAddTime() {
		return AddTime;
	}

	/**
	* 设置字段AddTime的值，该字段的<br>
	* 字段名称 :添加时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setAddTime(Date addTime) {
		this.AddTime = addTime;
    }

	/**
	* 获取字段ModifyUser的值，该字段的<br>
	* 字段名称 :修改人<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getModifyUser() {
		return ModifyUser;
	}

	/**
	* 设置字段ModifyUser的值，该字段的<br>
	* 字段名称 :修改人<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyUser(String modifyUser) {
		this.ModifyUser = modifyUser;
    }

	/**
	* 获取字段ModifyTime的值，该字段的<br>
	* 字段名称 :修改时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getModifyTime() {
		return ModifyTime;
	}

	/**
	* 设置字段ModifyTime的值，该字段的<br>
	* 字段名称 :修改时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyTime(Date modifyTime) {
		this.ModifyTime = modifyTime;
    }

	/**
	* 获取字段BackupNo的值，该字段的<br>
	* 字段名称 :备份编号<br>
	* 数据类型 :varchar(15)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getBackupNo() {
		return BackupNo;
	}

	/**
	* 设置字段BackupNo的值，该字段的<br>
	* 字段名称 :备份编号<br>
	* 数据类型 :varchar(15)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setBackupNo(String backupNo) {
		this.BackupNo = backupNo;
    }

	/**
	* 获取字段BackupOperator的值，该字段的<br>
	* 字段名称 :备份人<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getBackupOperator() {
		return BackupOperator;
	}

	/**
	* 设置字段BackupOperator的值，该字段的<br>
	* 字段名称 :备份人<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setBackupOperator(String backupOperator) {
		this.BackupOperator = backupOperator;
    }

	/**
	* 获取字段BackupTime的值，该字段的<br>
	* 字段名称 :备份时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public Date getBackupTime() {
		return BackupTime;
	}

	/**
	* 设置字段BackupTime的值，该字段的<br>
	* 字段名称 :备份时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setBackupTime(Date backupTime) {
		this.BackupTime = backupTime;
    }

	/**
	* 获取字段BackupMemo的值，该字段的<br>
	* 字段名称 :备份备注<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getBackupMemo() {
		return BackupMemo;
	}

	/**
	* 设置字段BackupMemo的值，该字段的<br>
	* 字段名称 :备份备注<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBackupMemo(String backupMemo) {
		this.BackupMemo = backupMemo;
    }

}