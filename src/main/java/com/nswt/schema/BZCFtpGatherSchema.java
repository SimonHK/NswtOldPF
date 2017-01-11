package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * �����ƣ�FTP�ɼ���Ŀ����<br>
 * ����룺BZCFtpGather<br>
 * ��������ID, BackupNo<br>
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
	* ��ȡ�ֶ�Name��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getName() {
		return Name;
	}

	/**
	* �����ֶ�Name��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setName(String name) {
		this.Name = name;
    }

	/**
	* ��ȡ�ֶ�FtpHost��ֵ�����ֶε�<br>
	* �ֶ����� :ftp������<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getFtpHost() {
		return FtpHost;
	}

	/**
	* �����ֶ�FtpHost��ֵ�����ֶε�<br>
	* �ֶ����� :ftp������<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setFtpHost(String ftpHost) {
		this.FtpHost = ftpHost;
    }

	/**
	* ��ȡ�ֶ�FtpPort��ֵ�����ֶε�<br>
	* �ֶ����� :ftp�������˿�<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getFtpPort() {
		return FtpPort;
	}

	/**
	* �����ֶ�FtpPort��ֵ�����ֶε�<br>
	* �ֶ����� :ftp�������˿�<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setFtpPort(String ftpPort) {
		this.FtpPort = ftpPort;
    }

	/**
	* ��ȡ�ֶ�GatherDrectory��ֵ�����ֶε�<br>
	* �ֶ����� :�ɼ�Ŀ¼<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getGatherDrectory() {
		return GatherDrectory;
	}

	/**
	* �����ֶ�GatherDrectory��ֵ�����ֶε�<br>
	* �ֶ����� :�ɼ�Ŀ¼<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setGatherDrectory(String gatherDrectory) {
		this.GatherDrectory = gatherDrectory;
    }

	/**
	* ��ȡ�ֶ�FtpUserName��ֵ�����ֶε�<br>
	* �ֶ����� :ftp�������û���<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getFtpUserName() {
		return FtpUserName;
	}

	/**
	* �����ֶ�FtpUserName��ֵ�����ֶε�<br>
	* �ֶ����� :ftp�������û���<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setFtpUserName(String ftpUserName) {
		this.FtpUserName = ftpUserName;
    }

	/**
	* ��ȡ�ֶ�FtpUserPassword��ֵ�����ֶε�<br>
	* �ֶ����� :ftp����������<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getFtpUserPassword() {
		return FtpUserPassword;
	}

	/**
	* �����ֶ�FtpUserPassword��ֵ�����ֶε�<br>
	* �ֶ����� :ftp����������<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setFtpUserPassword(String ftpUserPassword) {
		this.FtpUserPassword = ftpUserPassword;
    }

	/**
	* ��ȡ�ֶ�GatherType��ֵ�����ֶε�<br>
	* �ֶ����� :�ɼ��ļ�����<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getGatherType() {
		return GatherType;
	}

	/**
	* �����ֶ�GatherType��ֵ�����ֶε�<br>
	* �ֶ����� :�ɼ��ļ�����<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setGatherType(String gatherType) {
		this.GatherType = gatherType;
    }

	/**
	* ��ȡ�ֶ�Title��ֵ�����ֶε�<br>
	* �ֶ����� :�������±���<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getTitle() {
		return Title;
	}

	/**
	* �����ֶ�Title��ֵ�����ֶε�<br>
	* �ֶ����� :�������±���<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setTitle(String title) {
		this.Title = title;
    }

	/**
	* ��ȡ�ֶ�RedirectUrl��ֵ�����ֶε�<br>
	* �ֶ����� :��תURL<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getRedirectUrl() {
		return RedirectUrl;
	}

	/**
	* �����ֶ�RedirectUrl��ֵ�����ֶε�<br>
	* �ֶ����� :��תURL<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setRedirectUrl(String redirectUrl) {
		this.RedirectUrl = redirectUrl;
    }

	/**
	* ��ȡ�ֶ�Content��ֵ�����ֶε�<br>
	* �ֶ����� :������������<br>
	* �������� :mediumtext<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getContent() {
		return Content;
	}

	/**
	* �����ֶ�Content��ֵ�����ֶε�<br>
	* �ֶ����� :������������<br>
	* �������� :mediumtext<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setContent(String content) {
		this.Content = content;
    }

	/**
	* ��ȡ�ֶ�SiteID��ֵ�����ֶε�<br>
	* �ֶ����� :վ��ID<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getSiteID() {
		return SiteID;
	}

	/**
	* �����ֶ�SiteID��ֵ�����ֶε�<br>
	* �ֶ����� :վ��ID<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setSiteID(String siteID) {
		this.SiteID = siteID;
    }

	/**
	* ��ȡ�ֶ�CatalogID��ֵ�����ֶε�<br>
	* �ֶ����� :��ĿID<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getCatalogID() {
		return CatalogID;
	}

	/**
	* �����ֶ�CatalogID��ֵ�����ֶε�<br>
	* �ֶ����� :��ĿID<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCatalogID(String catalogID) {
		this.CatalogID = catalogID;
    }

	/**
	* ��ȡ�ֶ�Type��ֵ�����ֶε�<br>
	* �ֶ����� :�ɼ����<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getType() {
		return Type;
	}

	/**
	* �����ֶ�Type��ֵ�����ֶε�<br>
	* �ֶ����� :�ɼ����<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setType(String type) {
		this.Type = type;
    }

	/**
	* ��ȡ�ֶ�ConfigXML��ֵ�����ֶε�<br>
	* �ֶ����� :�ɼ�����<br>
	* �������� :text<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getConfigXML() {
		return ConfigXML;
	}

	/**
	* �����ֶ�ConfigXML��ֵ�����ֶε�<br>
	* �ֶ����� :�ɼ�����<br>
	* �������� :text<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setConfigXML(String configXML) {
		this.ConfigXML = configXML;
    }

	/**
	* ��ȡ�ֶ�ProxyFlag��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ�ʹ��Http����<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	* ��ע��Ϣ :<br>
	0-��ʹ�� 1-�Զ������� 2-ȫ�ִ���<br>
	*/
	public String getProxyFlag() {
		return ProxyFlag;
	}

	/**
	* �����ֶ�ProxyFlag��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ�ʹ��Http����<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	* ��ע��Ϣ :<br>
	0-��ʹ�� 1-�Զ������� 2-ȫ�ִ���<br>
	*/
	public void setProxyFlag(String proxyFlag) {
		this.ProxyFlag = proxyFlag;
    }

	/**
	* ��ȡ�ֶ�ProxyHost��ֵ�����ֶε�<br>
	* �ֶ����� :�����������ַ<br>
	* �������� :VARCHAR (255)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProxyHost() {
		return ProxyHost;
	}

	/**
	* �����ֶ�ProxyHost��ֵ�����ֶε�<br>
	* �ֶ����� :�����������ַ<br>
	* �������� :VARCHAR (255)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProxyHost(String proxyHost) {
		this.ProxyHost = proxyHost;
    }

	/**
	* ��ȡ�ֶ�ProxyPort��ֵ�����ֶε�<br>
	* �ֶ����� :�˿�<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getProxyPort() {
		if(ProxyPort==null){return 0;}
		return ProxyPort.longValue();
	}

	/**
	* �����ֶ�ProxyPort��ֵ�����ֶε�<br>
	* �ֶ����� :�˿�<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProxyPort(long proxyPort) {
		this.ProxyPort = new Long(proxyPort);
    }

	/**
	* �����ֶ�ProxyPort��ֵ�����ֶε�<br>
	* �ֶ����� :�˿�<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProxyPort(String proxyPort) {
		if (proxyPort == null){
			this.ProxyPort = null;
			return;
		}
		this.ProxyPort = new Long(proxyPort);
    }

	/**
	* ��ȡ�ֶ�Status��ֵ�����ֶε�<br>
	* �ֶ����� :״̬<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getStatus() {
		return Status;
	}

	/**
	* �����ֶ�Status��ֵ�����ֶε�<br>
	* �ֶ����� :״̬<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setStatus(String status) {
		this.Status = status;
    }

	/**
	* ��ȡ�ֶ�Prop1��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�1<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* �����ֶ�Prop1��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�1<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* ��ȡ�ֶ�Prop2��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�2<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* �����ֶ�Prop2��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�2<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

	/**
	* ��ȡ�ֶ�Prop3��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�3<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp3() {
		return Prop3;
	}

	/**
	* �����ֶ�Prop3��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�3<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp3(String prop3) {
		this.Prop3 = prop3;
    }

	/**
	* ��ȡ�ֶ�Prop4��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�4<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp4() {
		return Prop4;
	}

	/**
	* �����ֶ�Prop4��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�4<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp4(String prop4) {
		this.Prop4 = prop4;
    }

	/**
	* ��ȡ�ֶ�Prop5��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�5<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp5() {
		return Prop5;
	}

	/**
	* �����ֶ�Prop5��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�5<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp5(String prop5) {
		this.Prop5 = prop5;
    }

	/**
	* ��ȡ�ֶ�Prop6��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�6<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp6() {
		return Prop6;
	}

	/**
	* �����ֶ�Prop6��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�6<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp6(String prop6) {
		this.Prop6 = prop6;
    }

	/**
	* ��ȡ�ֶ�AddUser��ֵ�����ֶε�<br>
	* �ֶ����� :�����<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getAddUser() {
		return AddUser;
	}

	/**
	* �����ֶ�AddUser��ֵ�����ֶε�<br>
	* �ֶ����� :�����<br>
	* �������� :varchar(50)<br>
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
	* �ֶ����� :����޸���<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getModifyUser() {
		return ModifyUser;
	}

	/**
	* �����ֶ�ModifyUser��ֵ�����ֶε�<br>
	* �ֶ����� :����޸���<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setModifyUser(String modifyUser) {
		this.ModifyUser = modifyUser;
    }

	/**
	* ��ȡ�ֶ�ModifyTime��ֵ�����ֶε�<br>
	* �ֶ����� :����޸�ʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public Date getModifyTime() {
		return ModifyTime;
	}

	/**
	* �����ֶ�ModifyTime��ֵ�����ֶε�<br>
	* �ֶ����� :����޸�ʱ��<br>
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