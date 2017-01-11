package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * �����ƣ����۱�<br>
 * ����룺ZCComment<br>
 * ��������ID<br>
 */
public class ZCCommentSchema extends Schema {
	private Long ID;

	private Long RelaID;

	private Long CatalogID;

	private Long CatalogType;

	private Long SiteID;

	private String Title;

	private String Content;

	private String AddUser;

	private String AddUserIP;

	private Date AddTime;

	private String VerifyFlag;

	private String VerifyUser;

	private Date VerifyTime;

	private String Prop1;

	private String Prop2;

	private Long SupporterCount;

	private Long AntiCount;

	private String SupportAntiIP;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 0 , 0 , true , true),
		new SchemaColumn("RelaID", DataColumn.LONG, 1, 0 , 0 , true , false),
		new SchemaColumn("CatalogID", DataColumn.LONG, 2, 0 , 0 , true , false),
		new SchemaColumn("CatalogType", DataColumn.LONG, 3, 0 , 0 , true , false),
		new SchemaColumn("SiteID", DataColumn.LONG, 4, 0 , 0 , true , false),
		new SchemaColumn("Title", DataColumn.STRING, 5, 100 , 0 , true , false),
		new SchemaColumn("Content", DataColumn.STRING, 6, 1000 , 0 , true , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 7, 200 , 0 , true , false),
		new SchemaColumn("AddUserIP", DataColumn.STRING, 8, 50 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 9, 0 , 0 , true , false),
		new SchemaColumn("VerifyFlag", DataColumn.STRING, 10, 1 , 0 , false , false),
		new SchemaColumn("VerifyUser", DataColumn.STRING, 11, 200 , 0 , false , false),
		new SchemaColumn("VerifyTime", DataColumn.DATETIME, 12, 0 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 13, 50 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 14, 50 , 0 , false , false),
		new SchemaColumn("SupporterCount", DataColumn.LONG, 15, 0 , 0 , false , false),
		new SchemaColumn("AntiCount", DataColumn.LONG, 16, 0 , 0 , false , false),
		new SchemaColumn("SupportAntiIP", DataColumn.CLOB, 17, 0 , 0 , false , false)
	};

	public static final String _TableCode = "ZCComment";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into ZCComment values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZCComment set ID=?,RelaID=?,CatalogID=?,CatalogType=?,SiteID=?,Title=?,Content=?,AddUser=?,AddUserIP=?,AddTime=?,VerifyFlag=?,VerifyUser=?,VerifyTime=?,Prop1=?,Prop2=?,SupporterCount=?,AntiCount=?,SupportAntiIP=? where ID=?";

	protected static final String _DeleteSQL = "delete from ZCComment  where ID=?";

	protected static final String _FillAllSQL = "select * from ZCComment  where ID=?";

	public ZCCommentSchema(){
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
		return new ZCCommentSchema();
	}

	protected SchemaSet newSet(){
		return new ZCCommentSet();
	}

	public ZCCommentSet query() {
		return query(null, -1, -1);
	}

	public ZCCommentSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZCCommentSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZCCommentSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZCCommentSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){RelaID = null;}else{RelaID = new Long(v.toString());}return;}
		if (i == 2){if(v==null){CatalogID = null;}else{CatalogID = new Long(v.toString());}return;}
		if (i == 3){if(v==null){CatalogType = null;}else{CatalogType = new Long(v.toString());}return;}
		if (i == 4){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 5){Title = (String)v;return;}
		if (i == 6){Content = (String)v;return;}
		if (i == 7){AddUser = (String)v;return;}
		if (i == 8){AddUserIP = (String)v;return;}
		if (i == 9){AddTime = (Date)v;return;}
		if (i == 10){VerifyFlag = (String)v;return;}
		if (i == 11){VerifyUser = (String)v;return;}
		if (i == 12){VerifyTime = (Date)v;return;}
		if (i == 13){Prop1 = (String)v;return;}
		if (i == 14){Prop2 = (String)v;return;}
		if (i == 15){if(v==null){SupporterCount = null;}else{SupporterCount = new Long(v.toString());}return;}
		if (i == 16){if(v==null){AntiCount = null;}else{AntiCount = new Long(v.toString());}return;}
		if (i == 17){SupportAntiIP = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return RelaID;}
		if (i == 2){return CatalogID;}
		if (i == 3){return CatalogType;}
		if (i == 4){return SiteID;}
		if (i == 5){return Title;}
		if (i == 6){return Content;}
		if (i == 7){return AddUser;}
		if (i == 8){return AddUserIP;}
		if (i == 9){return AddTime;}
		if (i == 10){return VerifyFlag;}
		if (i == 11){return VerifyUser;}
		if (i == 12){return VerifyTime;}
		if (i == 13){return Prop1;}
		if (i == 14){return Prop2;}
		if (i == 15){return SupporterCount;}
		if (i == 16){return AntiCount;}
		if (i == 17){return SupportAntiIP;}
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
	* ��ȡ�ֶ�RelaID��ֵ�����ֶε�<br>
	* �ֶ����� :���۹���ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public long getRelaID() {
		if(RelaID==null){return 0;}
		return RelaID.longValue();
	}

	/**
	* �����ֶ�RelaID��ֵ�����ֶε�<br>
	* �ֶ����� :���۹���ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setRelaID(long relaID) {
		this.RelaID = new Long(relaID);
    }

	/**
	* �����ֶ�RelaID��ֵ�����ֶε�<br>
	* �ֶ����� :���۹���ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setRelaID(String relaID) {
		if (relaID == null){
			this.RelaID = null;
			return;
		}
		this.RelaID = new Long(relaID);
    }

	/**
	* ��ȡ�ֶ�CatalogID��ֵ�����ֶε�<br>
	* �ֶ����� :��ĿID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public long getCatalogID() {
		if(CatalogID==null){return 0;}
		return CatalogID.longValue();
	}

	/**
	* �����ֶ�CatalogID��ֵ�����ֶε�<br>
	* �ֶ����� :��ĿID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setCatalogID(long catalogID) {
		this.CatalogID = new Long(catalogID);
    }

	/**
	* �����ֶ�CatalogID��ֵ�����ֶε�<br>
	* �ֶ����� :��ĿID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setCatalogID(String catalogID) {
		if (catalogID == null){
			this.CatalogID = null;
			return;
		}
		this.CatalogID = new Long(catalogID);
    }

	/**
	* ��ȡ�ֶ�CatalogType��ֵ�����ֶε�<br>
	* �ֶ����� :��Ŀ����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public long getCatalogType() {
		if(CatalogType==null){return 0;}
		return CatalogType.longValue();
	}

	/**
	* �����ֶ�CatalogType��ֵ�����ֶε�<br>
	* �ֶ����� :��Ŀ����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setCatalogType(long catalogType) {
		this.CatalogType = new Long(catalogType);
    }

	/**
	* �����ֶ�CatalogType��ֵ�����ֶε�<br>
	* �ֶ����� :��Ŀ����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setCatalogType(String catalogType) {
		if (catalogType == null){
			this.CatalogType = null;
			return;
		}
		this.CatalogType = new Long(catalogType);
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
	* ��ȡ�ֶ�Title��ֵ�����ֶε�<br>
	* �ֶ����� :���۱���<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getTitle() {
		return Title;
	}

	/**
	* �����ֶ�Title��ֵ�����ֶε�<br>
	* �ֶ����� :���۱���<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setTitle(String title) {
		this.Title = title;
    }

	/**
	* ��ȡ�ֶ�Content��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :varchar(1000)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getContent() {
		return Content;
	}

	/**
	* �����ֶ�Content��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :varchar(1000)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setContent(String content) {
		this.Content = content;
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
	* ��ȡ�ֶ�AddUserIP��ֵ�����ֶε�<br>
	* �ֶ����� :������IP<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getAddUserIP() {
		return AddUserIP;
	}

	/**
	* �����ֶ�AddUserIP��ֵ�����ֶε�<br>
	* �ֶ����� :������IP<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setAddUserIP(String addUserIP) {
		this.AddUserIP = addUserIP;
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
	* ��ȡ�ֶ�VerifyFlag��ֵ�����ֶε�<br>
	* �ֶ����� :��˱�־<br>
	* �������� :varchar(1)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	* ��ע��Ϣ :<br>
	0-δ���<br>
	1-�����<br>
	*/
	public String getVerifyFlag() {
		return VerifyFlag;
	}

	/**
	* �����ֶ�VerifyFlag��ֵ�����ֶε�<br>
	* �ֶ����� :��˱�־<br>
	* �������� :varchar(1)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	* ��ע��Ϣ :<br>
	0-δ���<br>
	1-�����<br>
	*/
	public void setVerifyFlag(String verifyFlag) {
		this.VerifyFlag = verifyFlag;
    }

	/**
	* ��ȡ�ֶ�VerifyUser��ֵ�����ֶε�<br>
	* �ֶ����� :�����<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getVerifyUser() {
		return VerifyUser;
	}

	/**
	* �����ֶ�VerifyUser��ֵ�����ֶε�<br>
	* �ֶ����� :�����<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setVerifyUser(String verifyUser) {
		this.VerifyUser = verifyUser;
    }

	/**
	* ��ȡ�ֶ�VerifyTime��ֵ�����ֶε�<br>
	* �ֶ����� :���ʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public Date getVerifyTime() {
		return VerifyTime;
	}

	/**
	* �����ֶ�VerifyTime��ֵ�����ֶε�<br>
	* �ֶ����� :���ʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setVerifyTime(Date verifyTime) {
		this.VerifyTime = verifyTime;
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
	* ��ȡ�ֶ�SupporterCount��ֵ�����ֶε�<br>
	* �ֶ����� :֧������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getSupporterCount() {
		if(SupporterCount==null){return 0;}
		return SupporterCount.longValue();
	}

	/**
	* �����ֶ�SupporterCount��ֵ�����ֶε�<br>
	* �ֶ����� :֧������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setSupporterCount(long supporterCount) {
		this.SupporterCount = new Long(supporterCount);
    }

	/**
	* �����ֶ�SupporterCount��ֵ�����ֶε�<br>
	* �ֶ����� :֧������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setSupporterCount(String supporterCount) {
		if (supporterCount == null){
			this.SupporterCount = null;
			return;
		}
		this.SupporterCount = new Long(supporterCount);
    }

	/**
	* ��ȡ�ֶ�AntiCount��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getAntiCount() {
		if(AntiCount==null){return 0;}
		return AntiCount.longValue();
	}

	/**
	* �����ֶ�AntiCount��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAntiCount(long antiCount) {
		this.AntiCount = new Long(antiCount);
    }

	/**
	* �����ֶ�AntiCount��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAntiCount(String antiCount) {
		if (antiCount == null){
			this.AntiCount = null;
			return;
		}
		this.AntiCount = new Long(antiCount);
    }

	/**
	* ��ȡ�ֶ�SupportAntiIP��ֵ�����ֶε�<br>
	* �ֶ����� :֧�ַ�����IP<br>
	* �������� :text<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getSupportAntiIP() {
		return SupportAntiIP;
	}

	/**
	* �����ֶ�SupportAntiIP��ֵ�����ֶε�<br>
	* �ֶ����� :֧�ַ�����IP<br>
	* �������� :text<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setSupportAntiIP(String supportAntiIP) {
		this.SupportAntiIP = supportAntiIP;
    }

}