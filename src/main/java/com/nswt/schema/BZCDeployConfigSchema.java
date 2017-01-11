package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * �����ƣ��������ñ���<br>
 * ����룺BZCDeployConfig<br>
 * ��������ID, BackupNo<br>
 */
public class BZCDeployConfigSchema extends Schema {
	private Long ID;

	private Long SiteID;

	private String SourceDir;

	private String TargetDir;

	private String Method;

	private String Host;

	private Long Port;

	private String UserName;

	private String Password;

	private Long UseFlag;

	private Date BeginTime;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	private String Prop4;

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
		new SchemaColumn("SiteID", DataColumn.LONG, 1, 0 , 0 , true , false),
		new SchemaColumn("SourceDir", DataColumn.STRING, 2, 255 , 0 , false , false),
		new SchemaColumn("TargetDir", DataColumn.STRING, 3, 255 , 0 , false , false),
		new SchemaColumn("Method", DataColumn.STRING, 4, 50 , 0 , false , false),
		new SchemaColumn("Host", DataColumn.STRING, 5, 255 , 0 , false , false),
		new SchemaColumn("Port", DataColumn.LONG, 6, 0 , 0 , false , false),
		new SchemaColumn("UserName", DataColumn.STRING, 7, 100 , 0 , false , false),
		new SchemaColumn("Password", DataColumn.STRING, 8, 100 , 0 , false , false),
		new SchemaColumn("UseFlag", DataColumn.LONG, 9, 0 , 0 , false , false),
		new SchemaColumn("BeginTime", DataColumn.DATETIME, 10, 0 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 11, 50 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 12, 50 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 13, 50 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 14, 50 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 15, 200 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 16, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 17, 200 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 18, 0 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 19, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 20, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 21, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 22, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZCDeployConfig";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into BZCDeployConfig values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZCDeployConfig set ID=?,SiteID=?,SourceDir=?,TargetDir=?,Method=?,Host=?,Port=?,UserName=?,Password=?,UseFlag=?,BeginTime=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZCDeployConfig  where ID=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZCDeployConfig  where ID=? and BackupNo=?";

	public BZCDeployConfigSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[23];
	}

	protected Schema newInstance(){
		return new BZCDeployConfigSchema();
	}

	protected SchemaSet newSet(){
		return new BZCDeployConfigSet();
	}

	public BZCDeployConfigSet query() {
		return query(null, -1, -1);
	}

	public BZCDeployConfigSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZCDeployConfigSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZCDeployConfigSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZCDeployConfigSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 2){SourceDir = (String)v;return;}
		if (i == 3){TargetDir = (String)v;return;}
		if (i == 4){Method = (String)v;return;}
		if (i == 5){Host = (String)v;return;}
		if (i == 6){if(v==null){Port = null;}else{Port = new Long(v.toString());}return;}
		if (i == 7){UserName = (String)v;return;}
		if (i == 8){Password = (String)v;return;}
		if (i == 9){if(v==null){UseFlag = null;}else{UseFlag = new Long(v.toString());}return;}
		if (i == 10){BeginTime = (Date)v;return;}
		if (i == 11){Prop1 = (String)v;return;}
		if (i == 12){Prop2 = (String)v;return;}
		if (i == 13){Prop3 = (String)v;return;}
		if (i == 14){Prop4 = (String)v;return;}
		if (i == 15){AddUser = (String)v;return;}
		if (i == 16){AddTime = (Date)v;return;}
		if (i == 17){ModifyUser = (String)v;return;}
		if (i == 18){ModifyTime = (Date)v;return;}
		if (i == 19){BackupNo = (String)v;return;}
		if (i == 20){BackupOperator = (String)v;return;}
		if (i == 21){BackupTime = (Date)v;return;}
		if (i == 22){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return SiteID;}
		if (i == 2){return SourceDir;}
		if (i == 3){return TargetDir;}
		if (i == 4){return Method;}
		if (i == 5){return Host;}
		if (i == 6){return Port;}
		if (i == 7){return UserName;}
		if (i == 8){return Password;}
		if (i == 9){return UseFlag;}
		if (i == 10){return BeginTime;}
		if (i == 11){return Prop1;}
		if (i == 12){return Prop2;}
		if (i == 13){return Prop3;}
		if (i == 14){return Prop4;}
		if (i == 15){return AddUser;}
		if (i == 16){return AddTime;}
		if (i == 17){return ModifyUser;}
		if (i == 18){return ModifyTime;}
		if (i == 19){return BackupNo;}
		if (i == 20){return BackupOperator;}
		if (i == 21){return BackupTime;}
		if (i == 22){return BackupMemo;}
		return null;
	}

	/**
	* ��ȡ�ֶ�ID��ֵ�����ֶε�<br>
	* �ֶ����� :����ID<br>
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
	* �ֶ����� :����ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setID(long iD) {
		this.ID = new Long(iD);
    }

	/**
	* �����ֶ�ID��ֵ�����ֶε�<br>
	* �ֶ����� :����ID<br>
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
	* �Ƿ���� :true<br>
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
	* �Ƿ���� :true<br>
	*/
	public void setSiteID(long siteID) {
		this.SiteID = new Long(siteID);
    }

	/**
	* �����ֶ�SiteID��ֵ�����ֶε�<br>
	* �ֶ����� :վ��ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setSiteID(String siteID) {
		if (siteID == null){
			this.SiteID = null;
			return;
		}
		this.SiteID = new Long(siteID);
    }

	/**
	* ��ȡ�ֶ�SourceDir��ֵ�����ֶε�<br>
	* �ֶ����� :ԴĿ¼<br>
	* �������� :varchar(255)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getSourceDir() {
		return SourceDir;
	}

	/**
	* �����ֶ�SourceDir��ֵ�����ֶε�<br>
	* �ֶ����� :ԴĿ¼<br>
	* �������� :varchar(255)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setSourceDir(String sourceDir) {
		this.SourceDir = sourceDir;
    }

	/**
	* ��ȡ�ֶ�TargetDir��ֵ�����ֶε�<br>
	* �ֶ����� :Ŀ��Ŀ¼<br>
	* �������� :varchar(255)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getTargetDir() {
		return TargetDir;
	}

	/**
	* �����ֶ�TargetDir��ֵ�����ֶε�<br>
	* �ֶ����� :Ŀ��Ŀ¼<br>
	* �������� :varchar(255)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setTargetDir(String targetDir) {
		this.TargetDir = targetDir;
    }

	/**
	* ��ȡ�ֶ�Method��ֵ�����ֶε�<br>
	* �ֶ����� :������ʽ<br>
	* �������� :VARCHAR (50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getMethod() {
		return Method;
	}

	/**
	* �����ֶ�Method��ֵ�����ֶε�<br>
	* �ֶ����� :������ʽ<br>
	* �������� :VARCHAR (50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setMethod(String method) {
		this.Method = method;
    }

	/**
	* ��ȡ�ֶ�Host��ֵ�����ֶε�<br>
	* �ֶ����� :��������ַ<br>
	* �������� :VARCHAR (255)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getHost() {
		return Host;
	}

	/**
	* �����ֶ�Host��ֵ�����ֶε�<br>
	* �ֶ����� :��������ַ<br>
	* �������� :VARCHAR (255)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setHost(String host) {
		this.Host = host;
    }

	/**
	* ��ȡ�ֶ�Port��ֵ�����ֶε�<br>
	* �ֶ����� :�˿�<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getPort() {
		if(Port==null){return 0;}
		return Port.longValue();
	}

	/**
	* �����ֶ�Port��ֵ�����ֶε�<br>
	* �ֶ����� :�˿�<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setPort(long port) {
		this.Port = new Long(port);
    }

	/**
	* �����ֶ�Port��ֵ�����ֶε�<br>
	* �ֶ����� :�˿�<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setPort(String port) {
		if (port == null){
			this.Port = null;
			return;
		}
		this.Port = new Long(port);
    }

	/**
	* ��ȡ�ֶ�UserName��ֵ�����ֶε�<br>
	* �ֶ����� :�û���<br>
	* �������� :VARCHAR (100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getUserName() {
		return UserName;
	}

	/**
	* �����ֶ�UserName��ֵ�����ֶε�<br>
	* �ֶ����� :�û���<br>
	* �������� :VARCHAR (100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setUserName(String userName) {
		this.UserName = userName;
    }

	/**
	* ��ȡ�ֶ�Password��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :VARCHAR (100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getPassword() {
		return Password;
	}

	/**
	* �����ֶ�Password��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :VARCHAR (100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setPassword(String password) {
		this.Password = password;
    }

	/**
	* ��ȡ�ֶ�UseFlag��ֵ�����ֶε�<br>
	* �ֶ����� :ʹ��״̬<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getUseFlag() {
		if(UseFlag==null){return 0;}
		return UseFlag.longValue();
	}

	/**
	* �����ֶ�UseFlag��ֵ�����ֶε�<br>
	* �ֶ����� :ʹ��״̬<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setUseFlag(long useFlag) {
		this.UseFlag = new Long(useFlag);
    }

	/**
	* �����ֶ�UseFlag��ֵ�����ֶε�<br>
	* �ֶ����� :ʹ��״̬<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setUseFlag(String useFlag) {
		if (useFlag == null){
			this.UseFlag = null;
			return;
		}
		this.UseFlag = new Long(useFlag);
    }

	/**
	* ��ȡ�ֶ�BeginTime��ֵ�����ֶε�<br>
	* �ֶ����� :��ʼʱ��<br>
	* �������� :date<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public Date getBeginTime() {
		return BeginTime;
	}

	/**
	* �����ֶ�BeginTime��ֵ�����ֶε�<br>
	* �ֶ����� :��ʼʱ��<br>
	* �������� :date<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setBeginTime(Date beginTime) {
		this.BeginTime = beginTime;
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
	* ��ȡ�ֶ�AddUser��ֵ�����ֶε�<br>
	* �ֶ����� :�����<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getAddUser() {
		return AddUser;
	}

	/**
	* �����ֶ�AddUser��ֵ�����ֶε�<br>
	* �ֶ����� :�����<br>
	* �������� :varchar(200)<br>
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
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getModifyUser() {
		return ModifyUser;
	}

	/**
	* �����ֶ�ModifyUser��ֵ�����ֶε�<br>
	* �ֶ����� :����޸���<br>
	* �������� :varchar(200)<br>
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