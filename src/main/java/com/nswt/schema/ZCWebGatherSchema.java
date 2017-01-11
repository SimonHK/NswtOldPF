package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * �����ƣ�Web�ɼ���Ŀ��<br>
 * ����룺ZCWebGather<br>
 * ��������ID<br>
 */
public class ZCWebGatherSchema extends Schema {
	private Long ID;

	private Long SiteID;

	private String Name;

	private String Intro;

	private String Type;

	private String ConfigXML;

	private String ProxyFlag;

	private String ProxyHost;

	private Long ProxyPort;

	private String ProxyUserName;

	private String ProxyPassword;

	private String Status;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	private String Prop4;

	private String AddUser;

	private Date AddTime;

	private String ModifyUser;

	private Date ModifyTime;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 0 , 0 , true , true),
		new SchemaColumn("SiteID", DataColumn.LONG, 1, 0 , 0 , true , false),
		new SchemaColumn("Name", DataColumn.STRING, 2, 50 , 0 , true , false),
		new SchemaColumn("Intro", DataColumn.STRING, 3, 255 , 0 , false , false),
		new SchemaColumn("Type", DataColumn.STRING, 4, 2 , 0 , true , false),
		new SchemaColumn("ConfigXML", DataColumn.CLOB, 5, 0 , 0 , true , false),
		new SchemaColumn("ProxyFlag", DataColumn.STRING, 6, 2 , 0 , false , false),
		new SchemaColumn("ProxyHost", DataColumn.STRING, 7, 255 , 0 , false , false),
		new SchemaColumn("ProxyPort", DataColumn.LONG, 8, 0 , 0 , false , false),
		new SchemaColumn("ProxyUserName", DataColumn.STRING, 9, 50 , 0 , false , false),
		new SchemaColumn("ProxyPassword", DataColumn.STRING, 10, 100 , 0 , false , false),
		new SchemaColumn("Status", DataColumn.STRING, 11, 2 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 12, 50 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 13, 50 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 14, 50 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 15, 50 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 16, 50 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 17, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 18, 50 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 19, 0 , 0 , false , false)
	};

	public static final String _TableCode = "ZCWebGather";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into ZCWebGather values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZCWebGather set ID=?,SiteID=?,Name=?,Intro=?,Type=?,ConfigXML=?,ProxyFlag=?,ProxyHost=?,ProxyPort=?,ProxyUserName=?,ProxyPassword=?,Status=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";

	protected static final String _DeleteSQL = "delete from ZCWebGather  where ID=?";

	protected static final String _FillAllSQL = "select * from ZCWebGather  where ID=?";

	public ZCWebGatherSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[20];
	}

	protected Schema newInstance(){
		return new ZCWebGatherSchema();
	}

	protected SchemaSet newSet(){
		return new ZCWebGatherSet();
	}

	public ZCWebGatherSet query() {
		return query(null, -1, -1);
	}

	public ZCWebGatherSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZCWebGatherSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZCWebGatherSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZCWebGatherSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 2){Name = (String)v;return;}
		if (i == 3){Intro = (String)v;return;}
		if (i == 4){Type = (String)v;return;}
		if (i == 5){ConfigXML = (String)v;return;}
		if (i == 6){ProxyFlag = (String)v;return;}
		if (i == 7){ProxyHost = (String)v;return;}
		if (i == 8){if(v==null){ProxyPort = null;}else{ProxyPort = new Long(v.toString());}return;}
		if (i == 9){ProxyUserName = (String)v;return;}
		if (i == 10){ProxyPassword = (String)v;return;}
		if (i == 11){Status = (String)v;return;}
		if (i == 12){Prop1 = (String)v;return;}
		if (i == 13){Prop2 = (String)v;return;}
		if (i == 14){Prop3 = (String)v;return;}
		if (i == 15){Prop4 = (String)v;return;}
		if (i == 16){AddUser = (String)v;return;}
		if (i == 17){AddTime = (Date)v;return;}
		if (i == 18){ModifyUser = (String)v;return;}
		if (i == 19){ModifyTime = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return SiteID;}
		if (i == 2){return Name;}
		if (i == 3){return Intro;}
		if (i == 4){return Type;}
		if (i == 5){return ConfigXML;}
		if (i == 6){return ProxyFlag;}
		if (i == 7){return ProxyHost;}
		if (i == 8){return ProxyPort;}
		if (i == 9){return ProxyUserName;}
		if (i == 10){return ProxyPassword;}
		if (i == 11){return Status;}
		if (i == 12){return Prop1;}
		if (i == 13){return Prop2;}
		if (i == 14){return Prop3;}
		if (i == 15){return Prop4;}
		if (i == 16){return AddUser;}
		if (i == 17){return AddTime;}
		if (i == 18){return ModifyUser;}
		if (i == 19){return ModifyTime;}
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
	* ��ȡ�ֶ�Name��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getName() {
		return Name;
	}

	/**
	* �����ֶ�Name��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setName(String name) {
		this.Name = name;
    }

	/**
	* ��ȡ�ֶ�Intro��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :varchar(255)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getIntro() {
		return Intro;
	}

	/**
	* �����ֶ�Intro��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :varchar(255)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setIntro(String intro) {
		this.Intro = intro;
    }

	/**
	* ��ȡ�ֶ�Type��ֵ�����ֶε�<br>
	* �ֶ����� :�ɼ����<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getType() {
		return Type;
	}

	/**
	* �����ֶ�Type��ֵ�����ֶε�<br>
	* �ֶ����� :�ɼ����<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setType(String type) {
		this.Type = type;
    }

	/**
	* ��ȡ�ֶ�ConfigXML��ֵ�����ֶε�<br>
	* �ֶ����� :�ɼ�����<br>
	* �������� :text<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getConfigXML() {
		return ConfigXML;
	}

	/**
	* �����ֶ�ConfigXML��ֵ�����ֶε�<br>
	* �ֶ����� :�ɼ�����<br>
	* �������� :text<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
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
	0-��ʹ��<br>
	1-�Զ�������<br>
	2-ȫ�ִ���<br>
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
	0-��ʹ��<br>
	1-�Զ�������<br>
	2-ȫ�ִ���<br>
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
	* ��ȡ�ֶ�ProxyUserName��ֵ�����ֶε�<br>
	* �ֶ����� :�û���<br>
	* �������� :VARCHAR (50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProxyUserName() {
		return ProxyUserName;
	}

	/**
	* �����ֶ�ProxyUserName��ֵ�����ֶε�<br>
	* �ֶ����� :�û���<br>
	* �������� :VARCHAR (50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProxyUserName(String proxyUserName) {
		this.ProxyUserName = proxyUserName;
    }

	/**
	* ��ȡ�ֶ�ProxyPassword��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :VARCHAR (100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProxyPassword() {
		return ProxyPassword;
	}

	/**
	* �����ֶ�ProxyPassword��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :VARCHAR (100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProxyPassword(String proxyPassword) {
		this.ProxyPassword = proxyPassword;
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

}