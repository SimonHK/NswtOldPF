package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * 表名称：图片播放器表备份<br>
 * 表代码：BZCImagePlayer<br>
 * 表主键：ID, BackupNo<br>
 */
public class BZCImagePlayerSchema extends Schema {
	private Long ID;

	private String Name;

	private String Code;

	private Long SiteID;

	private Long StyleID;

	private String ImageSource;

	private String RelaCatalogInnerCode;

	private Long Height;

	private Long Width;

	private Long DisplayCount;

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
		new SchemaColumn("Name", DataColumn.STRING, 1, 100 , 0 , true , false),
		new SchemaColumn("Code", DataColumn.STRING, 2, 100 , 0 , true , false),
		new SchemaColumn("SiteID", DataColumn.LONG, 3, 0 , 0 , true , false),
		new SchemaColumn("StyleID", DataColumn.LONG, 4, 0 , 0 , true , false),
		new SchemaColumn("ImageSource", DataColumn.STRING, 5, 20 , 0 , true , false),
		new SchemaColumn("RelaCatalogInnerCode", DataColumn.STRING, 6, 100 , 0 , false , false),
		new SchemaColumn("Height", DataColumn.LONG, 7, 0 , 0 , true , false),
		new SchemaColumn("Width", DataColumn.LONG, 8, 0 , 0 , true , false),
		new SchemaColumn("DisplayCount", DataColumn.LONG, 9, 0 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 10, 200 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 11, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 12, 200 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 13, 0 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 14, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 15, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 16, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 17, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZCImagePlayer";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into BZCImagePlayer values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZCImagePlayer set ID=?,Name=?,Code=?,SiteID=?,StyleID=?,ImageSource=?,RelaCatalogInnerCode=?,Height=?,Width=?,DisplayCount=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZCImagePlayer  where ID=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZCImagePlayer  where ID=? and BackupNo=?";

	public BZCImagePlayerSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[18];
	}

	protected Schema newInstance(){
		return new BZCImagePlayerSchema();
	}

	protected SchemaSet newSet(){
		return new BZCImagePlayerSet();
	}

	public BZCImagePlayerSet query() {
		return query(null, -1, -1);
	}

	public BZCImagePlayerSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZCImagePlayerSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZCImagePlayerSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZCImagePlayerSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){Name = (String)v;return;}
		if (i == 2){Code = (String)v;return;}
		if (i == 3){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 4){if(v==null){StyleID = null;}else{StyleID = new Long(v.toString());}return;}
		if (i == 5){ImageSource = (String)v;return;}
		if (i == 6){RelaCatalogInnerCode = (String)v;return;}
		if (i == 7){if(v==null){Height = null;}else{Height = new Long(v.toString());}return;}
		if (i == 8){if(v==null){Width = null;}else{Width = new Long(v.toString());}return;}
		if (i == 9){if(v==null){DisplayCount = null;}else{DisplayCount = new Long(v.toString());}return;}
		if (i == 10){AddUser = (String)v;return;}
		if (i == 11){AddTime = (Date)v;return;}
		if (i == 12){ModifyUser = (String)v;return;}
		if (i == 13){ModifyTime = (Date)v;return;}
		if (i == 14){BackupNo = (String)v;return;}
		if (i == 15){BackupOperator = (String)v;return;}
		if (i == 16){BackupTime = (Date)v;return;}
		if (i == 17){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return Name;}
		if (i == 2){return Code;}
		if (i == 3){return SiteID;}
		if (i == 4){return StyleID;}
		if (i == 5){return ImageSource;}
		if (i == 6){return RelaCatalogInnerCode;}
		if (i == 7){return Height;}
		if (i == 8){return Width;}
		if (i == 9){return DisplayCount;}
		if (i == 10){return AddUser;}
		if (i == 11){return AddTime;}
		if (i == 12){return ModifyUser;}
		if (i == 13){return ModifyTime;}
		if (i == 14){return BackupNo;}
		if (i == 15){return BackupOperator;}
		if (i == 16){return BackupTime;}
		if (i == 17){return BackupMemo;}
		return null;
	}

	/**
	* 获取字段ID的值，该字段的<br>
	* 字段名称 :图片播放器ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public long getID() {
		if(ID==null){return 0;}
		return ID.longValue();
	}

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :图片播放器ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(long iD) {
		this.ID = new Long(iD);
    }

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :图片播放器ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(String iD) {
		if (iD == null){
			this.ID = null;
			return;
		}
		this.ID = new Long(iD);
    }

	/**
	* 获取字段Name的值，该字段的<br>
	* 字段名称 :图片播放器名称<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getName() {
		return Name;
	}

	/**
	* 设置字段Name的值，该字段的<br>
	* 字段名称 :图片播放器名称<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setName(String name) {
		this.Name = name;
    }

	/**
	* 获取字段Code的值，该字段的<br>
	* 字段名称 :图片播放器代码<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getCode() {
		return Code;
	}

	/**
	* 设置字段Code的值，该字段的<br>
	* 字段名称 :图片播放器代码<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setCode(String code) {
		this.Code = code;
    }

	/**
	* 获取字段SiteID的值，该字段的<br>
	* 字段名称 :所属站点ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getSiteID() {
		if(SiteID==null){return 0;}
		return SiteID.longValue();
	}

	/**
	* 设置字段SiteID的值，该字段的<br>
	* 字段名称 :所属站点ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setSiteID(long siteID) {
		this.SiteID = new Long(siteID);
    }

	/**
	* 设置字段SiteID的值，该字段的<br>
	* 字段名称 :所属站点ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setSiteID(String siteID) {
		if (siteID == null){
			this.SiteID = null;
			return;
		}
		this.SiteID = new Long(siteID);
    }

	/**
	* 获取字段StyleID的值，该字段的<br>
	* 字段名称 :样式ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getStyleID() {
		if(StyleID==null){return 0;}
		return StyleID.longValue();
	}

	/**
	* 设置字段StyleID的值，该字段的<br>
	* 字段名称 :样式ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setStyleID(long styleID) {
		this.StyleID = new Long(styleID);
    }

	/**
	* 设置字段StyleID的值，该字段的<br>
	* 字段名称 :样式ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setStyleID(String styleID) {
		if (styleID == null){
			this.StyleID = null;
			return;
		}
		this.StyleID = new Long(styleID);
    }

	/**
	* 获取字段ImageSource的值，该字段的<br>
	* 字段名称 :图片来源<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	0-上传 1-来自栏目<br>
	*/
	public String getImageSource() {
		return ImageSource;
	}

	/**
	* 设置字段ImageSource的值，该字段的<br>
	* 字段名称 :图片来源<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	0-上传 1-来自栏目<br>
	*/
	public void setImageSource(String imageSource) {
		this.ImageSource = imageSource;
    }

	/**
	* 获取字段RelaCatalogInnerCode的值，该字段的<br>
	* 字段名称 :关联栏目<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRelaCatalogInnerCode() {
		return RelaCatalogInnerCode;
	}

	/**
	* 设置字段RelaCatalogInnerCode的值，该字段的<br>
	* 字段名称 :关联栏目<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRelaCatalogInnerCode(String relaCatalogInnerCode) {
		this.RelaCatalogInnerCode = relaCatalogInnerCode;
    }

	/**
	* 获取字段Height的值，该字段的<br>
	* 字段名称 :显示高度<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getHeight() {
		if(Height==null){return 0;}
		return Height.longValue();
	}

	/**
	* 设置字段Height的值，该字段的<br>
	* 字段名称 :显示高度<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setHeight(long height) {
		this.Height = new Long(height);
    }

	/**
	* 设置字段Height的值，该字段的<br>
	* 字段名称 :显示高度<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setHeight(String height) {
		if (height == null){
			this.Height = null;
			return;
		}
		this.Height = new Long(height);
    }

	/**
	* 获取字段Width的值，该字段的<br>
	* 字段名称 :显示宽度<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getWidth() {
		if(Width==null){return 0;}
		return Width.longValue();
	}

	/**
	* 设置字段Width的值，该字段的<br>
	* 字段名称 :显示宽度<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setWidth(long width) {
		this.Width = new Long(width);
    }

	/**
	* 设置字段Width的值，该字段的<br>
	* 字段名称 :显示宽度<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setWidth(String width) {
		if (width == null){
			this.Width = null;
			return;
		}
		this.Width = new Long(width);
    }

	/**
	* 获取字段DisplayCount的值，该字段的<br>
	* 字段名称 :最多显示图片张数<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	最多只播放数字代表的张数<br>
	*/
	public long getDisplayCount() {
		if(DisplayCount==null){return 0;}
		return DisplayCount.longValue();
	}

	/**
	* 设置字段DisplayCount的值，该字段的<br>
	* 字段名称 :最多显示图片张数<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	最多只播放数字代表的张数<br>
	*/
	public void setDisplayCount(long displayCount) {
		this.DisplayCount = new Long(displayCount);
    }

	/**
	* 设置字段DisplayCount的值，该字段的<br>
	* 字段名称 :最多显示图片张数<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	最多只播放数字代表的张数<br>
	*/
	public void setDisplayCount(String displayCount) {
		if (displayCount == null){
			this.DisplayCount = null;
			return;
		}
		this.DisplayCount = new Long(displayCount);
    }

	/**
	* 获取字段AddUser的值，该字段的<br>
	* 字段名称 :添加者<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getAddUser() {
		return AddUser;
	}

	/**
	* 设置字段AddUser的值，该字段的<br>
	* 字段名称 :添加者<br>
	* 数据类型 :varchar(200)<br>
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
	* 字段名称 :最后修改人<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getModifyUser() {
		return ModifyUser;
	}

	/**
	* 设置字段ModifyUser的值，该字段的<br>
	* 字段名称 :最后修改人<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyUser(String modifyUser) {
		this.ModifyUser = modifyUser;
    }

	/**
	* 获取字段ModifyTime的值，该字段的<br>
	* 字段名称 :最后修改时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getModifyTime() {
		return ModifyTime;
	}

	/**
	* 设置字段ModifyTime的值，该字段的<br>
	* 字段名称 :最后修改时间<br>
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