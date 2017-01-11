package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * 表名称：FTP采集项目表备份<br>
 * 表代码：BZCFtpGather<br>
 * 表主键：ID, BackupNo<br>
 */
public class BZCFtpGatherSchema extends Schema {
	private Long ID;

	private String Name;

	private String FtpHost;

	private String FtpPort;

	private String GatherDrectory;

	private String FtpUserName;

	private String FtpUserPassword;

	private String GatherType;

	private String Title;

	private String RedirectUrl;

	private String Content;

	private String SiteID;

	private String CatalogID;

	private String Type;

	private String ConfigXML;

	private String ProxyFlag;

	private String ProxyHost;

	private Long ProxyPort;

	private String Status;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	private String Prop4;

	private String Prop5;

	private String Prop6;

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
		new SchemaColumn("Name", DataColumn.STRING, 1, 100 , 0 , false , false),
		new SchemaColumn("FtpHost", DataColumn.STRING, 2, 20 , 0 , false , false),
		new SchemaColumn("FtpPort", DataColumn.STRING, 3, 50 , 0 , true , false),
		new SchemaColumn("GatherDrectory", DataColumn.STRING, 4, 200 , 0 , false , false),
		new SchemaColumn("FtpUserName", DataColumn.STRING, 5, 50 , 0 , true , false),
		new SchemaColumn("FtpUserPassword", DataColumn.STRING, 6, 50 , 0 , false , false),
		new SchemaColumn("GatherType", DataColumn.STRING, 7, 200 , 0 , false , false),
		new SchemaColumn("Title", DataColumn.STRING, 8, 100 , 0 , false , false),
		new SchemaColumn("RedirectUrl", DataColumn.STRING, 9, 200 , 0 , false , false),
		new SchemaColumn("Content", DataColumn.CLOB, 10, 0 , 0 , false , false),
		new SchemaColumn("SiteID", DataColumn.STRING, 11, 100 , 0 , false , false),
		new SchemaColumn("CatalogID", DataColumn.STRING, 12, 20 , 0 , false , false),
		new SchemaColumn("Type", DataColumn.STRING, 13, 2 , 0 , false , false),
		new SchemaColumn("ConfigXML", DataColumn.CLOB, 14, 0 , 0 , false , false),
		new SchemaColumn("ProxyFlag", DataColumn.STRING, 15, 2 , 0 , false , false),
		new SchemaColumn("ProxyHost", DataColumn.STRING, 16, 255 , 0 , false , false),
		new SchemaColumn("ProxyPort", DataColumn.LONG, 17, 0 , 0 , false , false),
		new SchemaColumn("Status", DataColumn.STRING, 18, 2 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 19, 50 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 20, 50 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 21, 50 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 22, 50 , 0 , false , false),
		new SchemaColumn("Prop5", DataColumn.STRING, 23, 50 , 0 , false , false),
		new SchemaColumn("Prop6", DataColumn.STRING, 24, 50 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 25, 50 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 26, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 27, 50 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 28, 0 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 29, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 30, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 31, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 32, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZCFtpGather";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into BZCFtpGather values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZCFtpGather set ID=?,Name=?,FtpHost=?,FtpPort=?,GatherDrectory=?,FtpUserName=?,FtpUserPassword=?,GatherType=?,Title=?,RedirectUrl=?,Content=?,SiteID=?,CatalogID=?,Type=?,ConfigXML=?,ProxyFlag=?,ProxyHost=?,ProxyPort=?,Status=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,Prop5=?,Prop6=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZCFtpGather  where ID=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZCFtpGather  where ID=? and BackupNo=?";

	public BZCFtpGatherSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[33];
	}

	protected Schema newInstance(){
		return new BZCFtpGatherSchema();
	}

	protected SchemaSet newSet(){
		return new BZCFtpGatherSet();
	}

	public BZCFtpGatherSet query() {
		return query(null, -1, -1);
	}

	public BZCFtpGatherSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZCFtpGatherSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZCFtpGatherSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZCFtpGatherSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){Name = (String)v;return;}
		if (i == 2){FtpHost = (String)v;return;}
		if (i == 3){FtpPort = (String)v;return;}
		if (i == 4){GatherDrectory = (String)v;return;}
		if (i == 5){FtpUserName = (String)v;return;}
		if (i == 6){FtpUserPassword = (String)v;return;}
		if (i == 7){GatherType = (String)v;return;}
		if (i == 8){Title = (String)v;return;}
		if (i == 9){RedirectUrl = (String)v;return;}
		if (i == 10){Content = (String)v;return;}
		if (i == 11){SiteID = (String)v;return;}
		if (i == 12){CatalogID = (String)v;return;}
		if (i == 13){Type = (String)v;return;}
		if (i == 14){ConfigXML = (String)v;return;}
		if (i == 15){ProxyFlag = (String)v;return;}
		if (i == 16){ProxyHost = (String)v;return;}
		if (i == 17){if(v==null){ProxyPort = null;}else{ProxyPort = new Long(v.toString());}return;}
		if (i == 18){Status = (String)v;return;}
		if (i == 19){Prop1 = (String)v;return;}
		if (i == 20){Prop2 = (String)v;return;}
		if (i == 21){Prop3 = (String)v;return;}
		if (i == 22){Prop4 = (String)v;return;}
		if (i == 23){Prop5 = (String)v;return;}
		if (i == 24){Prop6 = (String)v;return;}
		if (i == 25){AddUser = (String)v;return;}
		if (i == 26){AddTime = (Date)v;return;}
		if (i == 27){ModifyUser = (String)v;return;}
		if (i == 28){ModifyTime = (Date)v;return;}
		if (i == 29){BackupNo = (String)v;return;}
		if (i == 30){BackupOperator = (String)v;return;}
		if (i == 31){BackupTime = (Date)v;return;}
		if (i == 32){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return Name;}
		if (i == 2){return FtpHost;}
		if (i == 3){return FtpPort;}
		if (i == 4){return GatherDrectory;}
		if (i == 5){return FtpUserName;}
		if (i == 6){return FtpUserPassword;}
		if (i == 7){return GatherType;}
		if (i == 8){return Title;}
		if (i == 9){return RedirectUrl;}
		if (i == 10){return Content;}
		if (i == 11){return SiteID;}
		if (i == 12){return CatalogID;}
		if (i == 13){return Type;}
		if (i == 14){return ConfigXML;}
		if (i == 15){return ProxyFlag;}
		if (i == 16){return ProxyHost;}
		if (i == 17){return ProxyPort;}
		if (i == 18){return Status;}
		if (i == 19){return Prop1;}
		if (i == 20){return Prop2;}
		if (i == 21){return Prop3;}
		if (i == 22){return Prop4;}
		if (i == 23){return Prop5;}
		if (i == 24){return Prop6;}
		if (i == 25){return AddUser;}
		if (i == 26){return AddTime;}
		if (i == 27){return ModifyUser;}
		if (i == 28){return ModifyTime;}
		if (i == 29){return BackupNo;}
		if (i == 30){return BackupOperator;}
		if (i == 31){return BackupTime;}
		if (i == 32){return BackupMemo;}
		return null;
	}

	/**
	* 获取字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
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
	* 字段名称 :ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(long iD) {
		this.ID = new Long(iD);
    }

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
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
	* 字段名称 :任务名称<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getName() {
		return Name;
	}

	/**
	* 设置字段Name的值，该字段的<br>
	* 字段名称 :任务名称<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setName(String name) {
		this.Name = name;
    }

	/**
	* 获取字段FtpHost的值，该字段的<br>
	* 字段名称 :ftp服务器<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getFtpHost() {
		return FtpHost;
	}

	/**
	* 设置字段FtpHost的值，该字段的<br>
	* 字段名称 :ftp服务器<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setFtpHost(String ftpHost) {
		this.FtpHost = ftpHost;
    }

	/**
	* 获取字段FtpPort的值，该字段的<br>
	* 字段名称 :ftp服务器端口<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getFtpPort() {
		return FtpPort;
	}

	/**
	* 设置字段FtpPort的值，该字段的<br>
	* 字段名称 :ftp服务器端口<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setFtpPort(String ftpPort) {
		this.FtpPort = ftpPort;
    }

	/**
	* 获取字段GatherDrectory的值，该字段的<br>
	* 字段名称 :采集目录<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getGatherDrectory() {
		return GatherDrectory;
	}

	/**
	* 设置字段GatherDrectory的值，该字段的<br>
	* 字段名称 :采集目录<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setGatherDrectory(String gatherDrectory) {
		this.GatherDrectory = gatherDrectory;
    }

	/**
	* 获取字段FtpUserName的值，该字段的<br>
	* 字段名称 :ftp服务器用户名<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getFtpUserName() {
		return FtpUserName;
	}

	/**
	* 设置字段FtpUserName的值，该字段的<br>
	* 字段名称 :ftp服务器用户名<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setFtpUserName(String ftpUserName) {
		this.FtpUserName = ftpUserName;
    }

	/**
	* 获取字段FtpUserPassword的值，该字段的<br>
	* 字段名称 :ftp服务器密码<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getFtpUserPassword() {
		return FtpUserPassword;
	}

	/**
	* 设置字段FtpUserPassword的值，该字段的<br>
	* 字段名称 :ftp服务器密码<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setFtpUserPassword(String ftpUserPassword) {
		this.FtpUserPassword = ftpUserPassword;
    }

	/**
	* 获取字段GatherType的值，该字段的<br>
	* 字段名称 :采集文件类型<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getGatherType() {
		return GatherType;
	}

	/**
	* 设置字段GatherType的值，该字段的<br>
	* 字段名称 :采集文件类型<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setGatherType(String gatherType) {
		this.GatherType = gatherType;
    }

	/**
	* 获取字段Title的值，该字段的<br>
	* 字段名称 :生成文章标题<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getTitle() {
		return Title;
	}

	/**
	* 设置字段Title的值，该字段的<br>
	* 字段名称 :生成文章标题<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setTitle(String title) {
		this.Title = title;
    }

	/**
	* 获取字段RedirectUrl的值，该字段的<br>
	* 字段名称 :跳转URL<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRedirectUrl() {
		return RedirectUrl;
	}

	/**
	* 设置字段RedirectUrl的值，该字段的<br>
	* 字段名称 :跳转URL<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRedirectUrl(String redirectUrl) {
		this.RedirectUrl = redirectUrl;
    }

	/**
	* 获取字段Content的值，该字段的<br>
	* 字段名称 :生成文章内容<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getContent() {
		return Content;
	}

	/**
	* 设置字段Content的值，该字段的<br>
	* 字段名称 :生成文章内容<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setContent(String content) {
		this.Content = content;
    }

	/**
	* 获取字段SiteID的值，该字段的<br>
	* 字段名称 :站点ID<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSiteID() {
		return SiteID;
	}

	/**
	* 设置字段SiteID的值，该字段的<br>
	* 字段名称 :站点ID<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSiteID(String siteID) {
		this.SiteID = siteID;
    }

	/**
	* 获取字段CatalogID的值，该字段的<br>
	* 字段名称 :栏目ID<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCatalogID() {
		return CatalogID;
	}

	/**
	* 设置字段CatalogID的值，该字段的<br>
	* 字段名称 :栏目ID<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCatalogID(String catalogID) {
		this.CatalogID = catalogID;
    }

	/**
	* 获取字段Type的值，该字段的<br>
	* 字段名称 :采集类别<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getType() {
		return Type;
	}

	/**
	* 设置字段Type的值，该字段的<br>
	* 字段名称 :采集类别<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setType(String type) {
		this.Type = type;
    }

	/**
	* 获取字段ConfigXML的值，该字段的<br>
	* 字段名称 :采集配置<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getConfigXML() {
		return ConfigXML;
	}

	/**
	* 设置字段ConfigXML的值，该字段的<br>
	* 字段名称 :采集配置<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setConfigXML(String configXML) {
		this.ConfigXML = configXML;
    }

	/**
	* 获取字段ProxyFlag的值，该字段的<br>
	* 字段名称 :是否使用Http代理<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	0-不使用 1-自定义配置 2-全局代理<br>
	*/
	public String getProxyFlag() {
		return ProxyFlag;
	}

	/**
	* 设置字段ProxyFlag的值，该字段的<br>
	* 字段名称 :是否使用Http代理<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	0-不使用 1-自定义配置 2-全局代理<br>
	*/
	public void setProxyFlag(String proxyFlag) {
		this.ProxyFlag = proxyFlag;
    }

	/**
	* 获取字段ProxyHost的值，该字段的<br>
	* 字段名称 :代理服务器地址<br>
	* 数据类型 :VARCHAR (255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProxyHost() {
		return ProxyHost;
	}

	/**
	* 设置字段ProxyHost的值，该字段的<br>
	* 字段名称 :代理服务器地址<br>
	* 数据类型 :VARCHAR (255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProxyHost(String proxyHost) {
		this.ProxyHost = proxyHost;
    }

	/**
	* 获取字段ProxyPort的值，该字段的<br>
	* 字段名称 :端口<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getProxyPort() {
		if(ProxyPort==null){return 0;}
		return ProxyPort.longValue();
	}

	/**
	* 设置字段ProxyPort的值，该字段的<br>
	* 字段名称 :端口<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProxyPort(long proxyPort) {
		this.ProxyPort = new Long(proxyPort);
    }

	/**
	* 设置字段ProxyPort的值，该字段的<br>
	* 字段名称 :端口<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProxyPort(String proxyPort) {
		if (proxyPort == null){
			this.ProxyPort = null;
			return;
		}
		this.ProxyPort = new Long(proxyPort);
    }

	/**
	* 获取字段Status的值，该字段的<br>
	* 字段名称 :状态<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getStatus() {
		return Status;
	}

	/**
	* 设置字段Status的值，该字段的<br>
	* 字段名称 :状态<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setStatus(String status) {
		this.Status = status;
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

	/**
	* 获取字段Prop3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp3() {
		return Prop3;
	}

	/**
	* 设置字段Prop3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp3(String prop3) {
		this.Prop3 = prop3;
    }

	/**
	* 获取字段Prop4的值，该字段的<br>
	* 字段名称 :备用字段4<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp4() {
		return Prop4;
	}

	/**
	* 设置字段Prop4的值，该字段的<br>
	* 字段名称 :备用字段4<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp4(String prop4) {
		this.Prop4 = prop4;
    }

	/**
	* 获取字段Prop5的值，该字段的<br>
	* 字段名称 :备用字段5<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp5() {
		return Prop5;
	}

	/**
	* 设置字段Prop5的值，该字段的<br>
	* 字段名称 :备用字段5<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp5(String prop5) {
		this.Prop5 = prop5;
    }

	/**
	* 获取字段Prop6的值，该字段的<br>
	* 字段名称 :备用字段6<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp6() {
		return Prop6;
	}

	/**
	* 设置字段Prop6的值，该字段的<br>
	* 字段名称 :备用字段6<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp6(String prop6) {
		this.Prop6 = prop6;
    }

	/**
	* 获取字段AddUser的值，该字段的<br>
	* 字段名称 :添加者<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getAddUser() {
		return AddUser;
	}

	/**
	* 设置字段AddUser的值，该字段的<br>
	* 字段名称 :添加者<br>
	* 数据类型 :varchar(50)<br>
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
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getModifyUser() {
		return ModifyUser;
	}

	/**
	* 设置字段ModifyUser的值，该字段的<br>
	* 字段名称 :最后修改人<br>
	* 数据类型 :varchar(50)<br>
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