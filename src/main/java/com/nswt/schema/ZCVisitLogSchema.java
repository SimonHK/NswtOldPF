package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * �����ƣ�ҳ�������־<br>
 * ����룺ZCVisitLog<br>
 * ��ע��<br>
����������ļ�¼����������ļ�¼<br>
<br>
 * ��������ID, AddTime<br>
 */
public class ZCVisitLogSchema extends Schema {
	private String ID;

	private Long SiteID;

	private String URL;

	private String IP;

	private String OS;

	private String Browser;

	private String Screen;

	private String Referer;

	private String ColorDepth;

	private String CookieEnabled;

	private String FlashEnabled;

	private String FlashVersion;

	private String JavaEnabled;

	private String Language;

	private String District;

	private Date AddTime;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.STRING, 0, 50 , 0 , true , true),
		new SchemaColumn("SiteID", DataColumn.LONG, 1, 0 , 0 , false , false),
		new SchemaColumn("URL", DataColumn.STRING, 2, 2000 , 0 , true , false),
		new SchemaColumn("IP", DataColumn.STRING, 3, 30 , 0 , false , false),
		new SchemaColumn("OS", DataColumn.STRING, 4, 50 , 0 , false , false),
		new SchemaColumn("Browser", DataColumn.STRING, 5, 50 , 0 , false , false),
		new SchemaColumn("Screen", DataColumn.STRING, 6, 10 , 0 , false , false),
		new SchemaColumn("Referer", DataColumn.STRING, 7, 2000 , 0 , false , false),
		new SchemaColumn("ColorDepth", DataColumn.STRING, 8, 10 , 0 , false , false),
		new SchemaColumn("CookieEnabled", DataColumn.STRING, 9, 10 , 0 , false , false),
		new SchemaColumn("FlashEnabled", DataColumn.STRING, 10, 10 , 0 , false , false),
		new SchemaColumn("FlashVersion", DataColumn.STRING, 11, 30 , 0 , false , false),
		new SchemaColumn("JavaEnabled", DataColumn.STRING, 12, 10 , 0 , false , false),
		new SchemaColumn("Language", DataColumn.STRING, 13, 30 , 0 , false , false),
		new SchemaColumn("District", DataColumn.STRING, 14, 100 , 0 , false , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 15, 0 , 0 , true , true)
	};

	public static final String _TableCode = "ZCVisitLog";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into ZCVisitLog values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZCVisitLog set ID=?,SiteID=?,URL=?,IP=?,OS=?,Browser=?,Screen=?,Referer=?,ColorDepth=?,CookieEnabled=?,FlashEnabled=?,FlashVersion=?,JavaEnabled=?,Language=?,District=?,AddTime=? where ID=? and AddTime=?";

	protected static final String _DeleteSQL = "delete from ZCVisitLog  where ID=? and AddTime=?";

	protected static final String _FillAllSQL = "select * from ZCVisitLog  where ID=? and AddTime=?";

	public ZCVisitLogSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[16];
	}

	protected Schema newInstance(){
		return new ZCVisitLogSchema();
	}

	protected SchemaSet newSet(){
		return new ZCVisitLogSet();
	}

	public ZCVisitLogSet query() {
		return query(null, -1, -1);
	}

	public ZCVisitLogSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZCVisitLogSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZCVisitLogSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZCVisitLogSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){ID = (String)v;return;}
		if (i == 1){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 2){URL = (String)v;return;}
		if (i == 3){IP = (String)v;return;}
		if (i == 4){OS = (String)v;return;}
		if (i == 5){Browser = (String)v;return;}
		if (i == 6){Screen = (String)v;return;}
		if (i == 7){Referer = (String)v;return;}
		if (i == 8){ColorDepth = (String)v;return;}
		if (i == 9){CookieEnabled = (String)v;return;}
		if (i == 10){FlashEnabled = (String)v;return;}
		if (i == 11){FlashVersion = (String)v;return;}
		if (i == 12){JavaEnabled = (String)v;return;}
		if (i == 13){Language = (String)v;return;}
		if (i == 14){District = (String)v;return;}
		if (i == 15){AddTime = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return SiteID;}
		if (i == 2){return URL;}
		if (i == 3){return IP;}
		if (i == 4){return OS;}
		if (i == 5){return Browser;}
		if (i == 6){return Screen;}
		if (i == 7){return Referer;}
		if (i == 8){return ColorDepth;}
		if (i == 9){return CookieEnabled;}
		if (i == 10){return FlashEnabled;}
		if (i == 11){return FlashVersion;}
		if (i == 12){return JavaEnabled;}
		if (i == 13){return Language;}
		if (i == 14){return District;}
		if (i == 15){return AddTime;}
		return null;
	}

	/**
	* ��ȡ�ֶ�ID��ֵ�����ֶε�<br>
	* �ֶ����� :ID<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public String getID() {
		return ID;
	}

	/**
	* �����ֶ�ID��ֵ�����ֶε�<br>
	* �ֶ����� :ID<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setID(String iD) {
		this.ID = iD;
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
	* ��ȡ�ֶ�URL��ֵ�����ֶε�<br>
	* �ֶ����� :����URL<br>
	* �������� :varchar(2000)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getURL() {
		return URL;
	}

	/**
	* �����ֶ�URL��ֵ�����ֶε�<br>
	* �ֶ����� :����URL<br>
	* �������� :varchar(2000)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setURL(String uRL) {
		this.URL = uRL;
    }

	/**
	* ��ȡ�ֶ�IP��ֵ�����ֶε�<br>
	* �ֶ����� :����ip<br>
	* �������� :varchar(30)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getIP() {
		return IP;
	}

	/**
	* �����ֶ�IP��ֵ�����ֶε�<br>
	* �ֶ����� :����ip<br>
	* �������� :varchar(30)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setIP(String iP) {
		this.IP = iP;
    }

	/**
	* ��ȡ�ֶ�OS��ֵ�����ֶε�<br>
	* �ֶ����� :����ϵͳ<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getOS() {
		return OS;
	}

	/**
	* �����ֶ�OS��ֵ�����ֶε�<br>
	* �ֶ����� :����ϵͳ<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setOS(String oS) {
		this.OS = oS;
    }

	/**
	* ��ȡ�ֶ�Browser��ֵ�����ֶε�<br>
	* �ֶ����� :�����<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getBrowser() {
		return Browser;
	}

	/**
	* �����ֶ�Browser��ֵ�����ֶε�<br>
	* �ֶ����� :�����<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setBrowser(String browser) {
		this.Browser = browser;
    }

	/**
	* ��ȡ�ֶ�Screen��ֵ�����ֶε�<br>
	* �ֶ����� :�ֱ���<br>
	* �������� :varchar(10)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getScreen() {
		return Screen;
	}

	/**
	* �����ֶ�Screen��ֵ�����ֶε�<br>
	* �ֶ����� :�ֱ���<br>
	* �������� :varchar(10)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setScreen(String screen) {
		this.Screen = screen;
    }

	/**
	* ��ȡ�ֶ�Referer��ֵ�����ֶε�<br>
	* �ֶ����� :����ҳ��<br>
	* �������� :varchar(2000)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getReferer() {
		return Referer;
	}

	/**
	* �����ֶ�Referer��ֵ�����ֶε�<br>
	* �ֶ����� :����ҳ��<br>
	* �������� :varchar(2000)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setReferer(String referer) {
		this.Referer = referer;
    }

	/**
	* ��ȡ�ֶ�ColorDepth��ֵ�����ֶε�<br>
	* �ֶ����� :��ʾ��ɫ��<br>
	* �������� :varchar(10)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getColorDepth() {
		return ColorDepth;
	}

	/**
	* �����ֶ�ColorDepth��ֵ�����ֶε�<br>
	* �ֶ����� :��ʾ��ɫ��<br>
	* �������� :varchar(10)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setColorDepth(String colorDepth) {
		this.ColorDepth = colorDepth;
    }

	/**
	* ��ȡ�ֶ�CookieEnabled��ֵ�����ֶε�<br>
	* �ֶ����� :����Cookie<br>
	* �������� :varchar(10)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getCookieEnabled() {
		return CookieEnabled;
	}

	/**
	* �����ֶ�CookieEnabled��ֵ�����ֶε�<br>
	* �ֶ����� :����Cookie<br>
	* �������� :varchar(10)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCookieEnabled(String cookieEnabled) {
		this.CookieEnabled = cookieEnabled;
    }

	/**
	* ��ȡ�ֶ�FlashEnabled��ֵ�����ֶε�<br>
	* �ֶ����� :֧��Flash<br>
	* �������� :varchar(10)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getFlashEnabled() {
		return FlashEnabled;
	}

	/**
	* �����ֶ�FlashEnabled��ֵ�����ֶε�<br>
	* �ֶ����� :֧��Flash<br>
	* �������� :varchar(10)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setFlashEnabled(String flashEnabled) {
		this.FlashEnabled = flashEnabled;
    }

	/**
	* ��ȡ�ֶ�FlashVersion��ֵ�����ֶε�<br>
	* �ֶ����� :Flash�汾<br>
	* �������� :varchar(30)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getFlashVersion() {
		return FlashVersion;
	}

	/**
	* �����ֶ�FlashVersion��ֵ�����ֶε�<br>
	* �ֶ����� :Flash�汾<br>
	* �������� :varchar(30)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setFlashVersion(String flashVersion) {
		this.FlashVersion = flashVersion;
    }

	/**
	* ��ȡ�ֶ�JavaEnabled��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ�֧��Applet<br>
	* �������� :varchar(10)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getJavaEnabled() {
		return JavaEnabled;
	}

	/**
	* �����ֶ�JavaEnabled��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ�֧��Applet<br>
	* �������� :varchar(10)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setJavaEnabled(String javaEnabled) {
		this.JavaEnabled = javaEnabled;
    }

	/**
	* ��ȡ�ֶ�Language��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :varchar(30)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getLanguage() {
		return Language;
	}

	/**
	* �����ֶ�Language��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :varchar(30)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setLanguage(String language) {
		this.Language = language;
    }

	/**
	* ��ȡ�ֶ�District��ֵ�����ֶε�<br>
	* �ֶ����� :���ڵ���<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getDistrict() {
		return District;
	}

	/**
	* �����ֶ�District��ֵ�����ֶε�<br>
	* �ֶ����� :���ڵ���<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setDistrict(String district) {
		this.District = district;
    }

	/**
	* ��ȡ�ֶ�AddTime��ֵ�����ֶε�<br>
	* �ֶ����� :����ʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public Date getAddTime() {
		return AddTime;
	}

	/**
	* �����ֶ�AddTime��ֵ�����ֶε�<br>
	* �ֶ����� :����ʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setAddTime(Date addTime) {
		this.AddTime = addTime;
    }

}