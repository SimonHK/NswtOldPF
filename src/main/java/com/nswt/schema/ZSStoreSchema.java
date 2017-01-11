package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * �����ƣ������ֵ��<br>
 * ����룺ZSStore<br>
 * ��������StoreCode<br>
 */
public class ZSStoreSchema extends Schema {
	private String StoreCode;

	private String ParentCode;

	private String Name;

	private String Alias;

	private Long TreeLevel;

	private Long SiteID;

	private Long OrderFlag;

	private String URL;

	private String Info;

	private String Country;

	private String Province;

	private String City;

	private String District;

	private String Address;

	private String ZipCode;

	private String Tel;

	private String Fax;

	private String Mobile;

	private String Contacter;

	private String ContacterEmail;

	private String ContacterTel;

	private String ContacterMobile;

	private String ContacterQQ;

	private String ContacterMSN;

	private String Memo;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	private String Prop4;

	private String AddUser;

	private Date AddTime;

	private String ModifyUser;

	private Date ModifyTime;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("StoreCode", DataColumn.STRING, 0, 100 , 0 , true , true),
		new SchemaColumn("ParentCode", DataColumn.STRING, 1, 100 , 0 , true , false),
		new SchemaColumn("Name", DataColumn.STRING, 2, 100 , 0 , true , false),
		new SchemaColumn("Alias", DataColumn.STRING, 3, 100 , 0 , false , false),
		new SchemaColumn("TreeLevel", DataColumn.LONG, 4, 0 , 0 , true , false),
		new SchemaColumn("SiteID", DataColumn.LONG, 5, 0 , 0 , true , false),
		new SchemaColumn("OrderFlag", DataColumn.LONG, 6, 0 , 0 , true , false),
		new SchemaColumn("URL", DataColumn.STRING, 7, 100 , 0 , false , false),
		new SchemaColumn("Info", DataColumn.STRING, 8, 2000 , 0 , false , false),
		new SchemaColumn("Country", DataColumn.STRING, 9, 30 , 0 , false , false),
		new SchemaColumn("Province", DataColumn.STRING, 10, 6 , 0 , false , false),
		new SchemaColumn("City", DataColumn.STRING, 11, 6 , 0 , false , false),
		new SchemaColumn("District", DataColumn.STRING, 12, 6 , 0 , false , false),
		new SchemaColumn("Address", DataColumn.STRING, 13, 400 , 0 , false , false),
		new SchemaColumn("ZipCode", DataColumn.STRING, 14, 10 , 0 , false , false),
		new SchemaColumn("Tel", DataColumn.STRING, 15, 20 , 0 , false , false),
		new SchemaColumn("Fax", DataColumn.STRING, 16, 20 , 0 , false , false),
		new SchemaColumn("Mobile", DataColumn.STRING, 17, 30 , 0 , false , false),
		new SchemaColumn("Contacter", DataColumn.STRING, 18, 40 , 0 , false , false),
		new SchemaColumn("ContacterEmail", DataColumn.STRING, 19, 100 , 0 , false , false),
		new SchemaColumn("ContacterTel", DataColumn.STRING, 20, 20 , 0 , false , false),
		new SchemaColumn("ContacterMobile", DataColumn.STRING, 21, 20 , 0 , false , false),
		new SchemaColumn("ContacterQQ", DataColumn.STRING, 22, 20 , 0 , false , false),
		new SchemaColumn("ContacterMSN", DataColumn.STRING, 23, 50 , 0 , false , false),
		new SchemaColumn("Memo", DataColumn.STRING, 24, 200 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 25, 200 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 26, 200 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 27, 200 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 28, 200 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 29, 200 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 30, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 31, 200 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 32, 0 , 0 , false , false)
	};

	public static final String _TableCode = "ZSStore";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into ZSStore values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZSStore set StoreCode=?,ParentCode=?,Name=?,Alias=?,TreeLevel=?,SiteID=?,OrderFlag=?,URL=?,Info=?,Country=?,Province=?,City=?,District=?,Address=?,ZipCode=?,Tel=?,Fax=?,Mobile=?,Contacter=?,ContacterEmail=?,ContacterTel=?,ContacterMobile=?,ContacterQQ=?,ContacterMSN=?,Memo=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where StoreCode=?";

	protected static final String _DeleteSQL = "delete from ZSStore  where StoreCode=?";

	protected static final String _FillAllSQL = "select * from ZSStore  where StoreCode=?";

	public ZSStoreSchema(){
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
		return new ZSStoreSchema();
	}

	protected SchemaSet newSet(){
		return new ZSStoreSet();
	}

	public ZSStoreSet query() {
		return query(null, -1, -1);
	}

	public ZSStoreSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZSStoreSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZSStoreSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZSStoreSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){StoreCode = (String)v;return;}
		if (i == 1){ParentCode = (String)v;return;}
		if (i == 2){Name = (String)v;return;}
		if (i == 3){Alias = (String)v;return;}
		if (i == 4){if(v==null){TreeLevel = null;}else{TreeLevel = new Long(v.toString());}return;}
		if (i == 5){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 6){if(v==null){OrderFlag = null;}else{OrderFlag = new Long(v.toString());}return;}
		if (i == 7){URL = (String)v;return;}
		if (i == 8){Info = (String)v;return;}
		if (i == 9){Country = (String)v;return;}
		if (i == 10){Province = (String)v;return;}
		if (i == 11){City = (String)v;return;}
		if (i == 12){District = (String)v;return;}
		if (i == 13){Address = (String)v;return;}
		if (i == 14){ZipCode = (String)v;return;}
		if (i == 15){Tel = (String)v;return;}
		if (i == 16){Fax = (String)v;return;}
		if (i == 17){Mobile = (String)v;return;}
		if (i == 18){Contacter = (String)v;return;}
		if (i == 19){ContacterEmail = (String)v;return;}
		if (i == 20){ContacterTel = (String)v;return;}
		if (i == 21){ContacterMobile = (String)v;return;}
		if (i == 22){ContacterQQ = (String)v;return;}
		if (i == 23){ContacterMSN = (String)v;return;}
		if (i == 24){Memo = (String)v;return;}
		if (i == 25){Prop1 = (String)v;return;}
		if (i == 26){Prop2 = (String)v;return;}
		if (i == 27){Prop3 = (String)v;return;}
		if (i == 28){Prop4 = (String)v;return;}
		if (i == 29){AddUser = (String)v;return;}
		if (i == 30){AddTime = (Date)v;return;}
		if (i == 31){ModifyUser = (String)v;return;}
		if (i == 32){ModifyTime = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return StoreCode;}
		if (i == 1){return ParentCode;}
		if (i == 2){return Name;}
		if (i == 3){return Alias;}
		if (i == 4){return TreeLevel;}
		if (i == 5){return SiteID;}
		if (i == 6){return OrderFlag;}
		if (i == 7){return URL;}
		if (i == 8){return Info;}
		if (i == 9){return Country;}
		if (i == 10){return Province;}
		if (i == 11){return City;}
		if (i == 12){return District;}
		if (i == 13){return Address;}
		if (i == 14){return ZipCode;}
		if (i == 15){return Tel;}
		if (i == 16){return Fax;}
		if (i == 17){return Mobile;}
		if (i == 18){return Contacter;}
		if (i == 19){return ContacterEmail;}
		if (i == 20){return ContacterTel;}
		if (i == 21){return ContacterMobile;}
		if (i == 22){return ContacterQQ;}
		if (i == 23){return ContacterMSN;}
		if (i == 24){return Memo;}
		if (i == 25){return Prop1;}
		if (i == 26){return Prop2;}
		if (i == 27){return Prop3;}
		if (i == 28){return Prop4;}
		if (i == 29){return AddUser;}
		if (i == 30){return AddTime;}
		if (i == 31){return ModifyUser;}
		if (i == 32){return ModifyTime;}
		return null;
	}

	/**
	* ��ȡ�ֶ�StoreCode��ֵ�����ֶε�<br>
	* �ֶ����� :�̼ұ���<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public String getStoreCode() {
		return StoreCode;
	}

	/**
	* �����ֶ�StoreCode��ֵ�����ֶε�<br>
	* �ֶ����� :�̼ұ���<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setStoreCode(String storeCode) {
		this.StoreCode = storeCode;
    }

	/**
	* ��ȡ�ֶ�ParentCode��ֵ�����ֶε�<br>
	* �ֶ����� :������<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getParentCode() {
		return ParentCode;
	}

	/**
	* �����ֶ�ParentCode��ֵ�����ֶε�<br>
	* �ֶ����� :������<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setParentCode(String parentCode) {
		this.ParentCode = parentCode;
    }

	/**
	* ��ȡ�ֶ�Name��ֵ�����ֶε�<br>
	* �ֶ����� :�̼�����<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getName() {
		return Name;
	}

	/**
	* �����ֶ�Name��ֵ�����ֶε�<br>
	* �ֶ����� :�̼�����<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setName(String name) {
		this.Name = name;
    }

	/**
	* ��ȡ�ֶ�Alias��ֵ�����ֶε�<br>
	* �ֶ����� :�̼ұ���<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getAlias() {
		return Alias;
	}

	/**
	* �����ֶ�Alias��ֵ�����ֶε�<br>
	* �ֶ����� :�̼ұ���<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAlias(String alias) {
		this.Alias = alias;
    }

	/**
	* ��ȡ�ֶ�TreeLevel��ֵ�����ֶε�<br>
	* �ֶ����� :�㼶<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public long getTreeLevel() {
		if(TreeLevel==null){return 0;}
		return TreeLevel.longValue();
	}

	/**
	* �����ֶ�TreeLevel��ֵ�����ֶε�<br>
	* �ֶ����� :�㼶<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setTreeLevel(long treeLevel) {
		this.TreeLevel = new Long(treeLevel);
    }

	/**
	* �����ֶ�TreeLevel��ֵ�����ֶε�<br>
	* �ֶ����� :�㼶<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setTreeLevel(String treeLevel) {
		if (treeLevel == null){
			this.TreeLevel = null;
			return;
		}
		this.TreeLevel = new Long(treeLevel);
    }

	/**
	* ��ȡ�ֶ�SiteID��ֵ�����ֶε�<br>
	* �ֶ����� :����վ��ID<br>
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
	* �ֶ����� :����վ��ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setSiteID(long siteID) {
		this.SiteID = new Long(siteID);
    }

	/**
	* �����ֶ�SiteID��ֵ�����ֶε�<br>
	* �ֶ����� :����վ��ID<br>
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
	* ��ȡ�ֶ�OrderFlag��ֵ�����ֶε�<br>
	* �ֶ����� :����Ȩֵ<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public long getOrderFlag() {
		if(OrderFlag==null){return 0;}
		return OrderFlag.longValue();
	}

	/**
	* �����ֶ�OrderFlag��ֵ�����ֶε�<br>
	* �ֶ����� :����Ȩֵ<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setOrderFlag(long orderFlag) {
		this.OrderFlag = new Long(orderFlag);
    }

	/**
	* �����ֶ�OrderFlag��ֵ�����ֶε�<br>
	* �ֶ����� :����Ȩֵ<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setOrderFlag(String orderFlag) {
		if (orderFlag == null){
			this.OrderFlag = null;
			return;
		}
		this.OrderFlag = new Long(orderFlag);
    }

	/**
	* ��ȡ�ֶ�URL��ֵ�����ֶε�<br>
	* �ֶ����� :�̼�URL<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getURL() {
		return URL;
	}

	/**
	* �����ֶ�URL��ֵ�����ֶε�<br>
	* �ֶ����� :�̼�URL<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setURL(String uRL) {
		this.URL = uRL;
    }

	/**
	* ��ȡ�ֶ�Info��ֵ�����ֶε�<br>
	* �ֶ����� :�̼ҽ���<br>
	* �������� :varchar(2000)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getInfo() {
		return Info;
	}

	/**
	* �����ֶ�Info��ֵ�����ֶε�<br>
	* �ֶ����� :�̼ҽ���<br>
	* �������� :varchar(2000)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setInfo(String info) {
		this.Info = info;
    }

	/**
	* ��ȡ�ֶ�Country��ֵ�����ֶε�<br>
	* �ֶ����� :�̼ҹ���<br>
	* �������� :varchar(30)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getCountry() {
		return Country;
	}

	/**
	* �����ֶ�Country��ֵ�����ֶε�<br>
	* �ֶ����� :�̼ҹ���<br>
	* �������� :varchar(30)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCountry(String country) {
		this.Country = country;
    }

	/**
	* ��ȡ�ֶ�Province��ֵ�����ֶε�<br>
	* �ֶ����� :�̼�ʡ��<br>
	* �������� :char(6)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProvince() {
		return Province;
	}

	/**
	* �����ֶ�Province��ֵ�����ֶε�<br>
	* �ֶ����� :�̼�ʡ��<br>
	* �������� :char(6)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProvince(String province) {
		this.Province = province;
    }

	/**
	* ��ȡ�ֶ�City��ֵ�����ֶε�<br>
	* �ֶ����� :�̼ҳ���<br>
	* �������� :char(6)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getCity() {
		return City;
	}

	/**
	* �����ֶ�City��ֵ�����ֶε�<br>
	* �ֶ����� :�̼ҳ���<br>
	* �������� :char(6)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCity(String city) {
		this.City = city;
    }

	/**
	* ��ȡ�ֶ�District��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :char(6)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getDistrict() {
		return District;
	}

	/**
	* �����ֶ�District��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :char(6)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setDistrict(String district) {
		this.District = district;
    }

	/**
	* ��ȡ�ֶ�Address��ֵ�����ֶε�<br>
	* �ֶ����� :�̼ҵ�ַ<br>
	* �������� :varchar(400)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getAddress() {
		return Address;
	}

	/**
	* �����ֶ�Address��ֵ�����ֶε�<br>
	* �ֶ����� :�̼ҵ�ַ<br>
	* �������� :varchar(400)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAddress(String address) {
		this.Address = address;
    }

	/**
	* ��ȡ�ֶ�ZipCode��ֵ�����ֶε�<br>
	* �ֶ����� :�̼��ʱ�<br>
	* �������� :varchar(10)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getZipCode() {
		return ZipCode;
	}

	/**
	* �����ֶ�ZipCode��ֵ�����ֶε�<br>
	* �ֶ����� :�̼��ʱ�<br>
	* �������� :varchar(10)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setZipCode(String zipCode) {
		this.ZipCode = zipCode;
    }

	/**
	* ��ȡ�ֶ�Tel��ֵ�����ֶε�<br>
	* �ֶ����� :�̼ҹ̶��绰<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getTel() {
		return Tel;
	}

	/**
	* �����ֶ�Tel��ֵ�����ֶε�<br>
	* �ֶ����� :�̼ҹ̶��绰<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setTel(String tel) {
		this.Tel = tel;
    }

	/**
	* ��ȡ�ֶ�Fax��ֵ�����ֶε�<br>
	* �ֶ����� :�̼Ҵ���<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getFax() {
		return Fax;
	}

	/**
	* �����ֶ�Fax��ֵ�����ֶε�<br>
	* �ֶ����� :�̼Ҵ���<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setFax(String fax) {
		this.Fax = fax;
    }

	/**
	* ��ȡ�ֶ�Mobile��ֵ�����ֶε�<br>
	* �ֶ����� :�̼��ƶ��绰<br>
	* �������� :varchar(30)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getMobile() {
		return Mobile;
	}

	/**
	* �����ֶ�Mobile��ֵ�����ֶε�<br>
	* �ֶ����� :�̼��ƶ��绰<br>
	* �������� :varchar(30)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setMobile(String mobile) {
		this.Mobile = mobile;
    }

	/**
	* ��ȡ�ֶ�Contacter��ֵ�����ֶε�<br>
	* �ֶ����� :��ϵ��<br>
	* �������� :varchar(40)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getContacter() {
		return Contacter;
	}

	/**
	* �����ֶ�Contacter��ֵ�����ֶε�<br>
	* �ֶ����� :��ϵ��<br>
	* �������� :varchar(40)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setContacter(String contacter) {
		this.Contacter = contacter;
    }

	/**
	* ��ȡ�ֶ�ContacterEmail��ֵ�����ֶε�<br>
	* �ֶ����� :��ϵ������<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getContacterEmail() {
		return ContacterEmail;
	}

	/**
	* �����ֶ�ContacterEmail��ֵ�����ֶε�<br>
	* �ֶ����� :��ϵ������<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setContacterEmail(String contacterEmail) {
		this.ContacterEmail = contacterEmail;
    }

	/**
	* ��ȡ�ֶ�ContacterTel��ֵ�����ֶε�<br>
	* �ֶ����� :��ϵ�˹̶��绰<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getContacterTel() {
		return ContacterTel;
	}

	/**
	* �����ֶ�ContacterTel��ֵ�����ֶε�<br>
	* �ֶ����� :��ϵ�˹̶��绰<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setContacterTel(String contacterTel) {
		this.ContacterTel = contacterTel;
    }

	/**
	* ��ȡ�ֶ�ContacterMobile��ֵ�����ֶε�<br>
	* �ֶ����� :��ϵ���ƶ��绰<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getContacterMobile() {
		return ContacterMobile;
	}

	/**
	* �����ֶ�ContacterMobile��ֵ�����ֶε�<br>
	* �ֶ����� :��ϵ���ƶ��绰<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setContacterMobile(String contacterMobile) {
		this.ContacterMobile = contacterMobile;
    }

	/**
	* ��ȡ�ֶ�ContacterQQ��ֵ�����ֶε�<br>
	* �ֶ����� :��ϵ��QQ<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getContacterQQ() {
		return ContacterQQ;
	}

	/**
	* �����ֶ�ContacterQQ��ֵ�����ֶε�<br>
	* �ֶ����� :��ϵ��QQ<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setContacterQQ(String contacterQQ) {
		this.ContacterQQ = contacterQQ;
    }

	/**
	* ��ȡ�ֶ�ContacterMSN��ֵ�����ֶε�<br>
	* �ֶ����� :��ϵ��MSN<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getContacterMSN() {
		return ContacterMSN;
	}

	/**
	* �����ֶ�ContacterMSN��ֵ�����ֶε�<br>
	* �ֶ����� :��ϵ��MSN<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setContacterMSN(String contacterMSN) {
		this.ContacterMSN = contacterMSN;
    }

	/**
	* ��ȡ�ֶ�Memo��ֵ�����ֶε�<br>
	* �ֶ����� :��ע<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getMemo() {
		return Memo;
	}

	/**
	* �����ֶ�Memo��ֵ�����ֶε�<br>
	* �ֶ����� :��ע<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setMemo(String memo) {
		this.Memo = memo;
    }

	/**
	* ��ȡ�ֶ�Prop1��ֵ�����ֶε�<br>
	* �ֶ����� :��������1<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* �����ֶ�Prop1��ֵ�����ֶε�<br>
	* �ֶ����� :��������1<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* ��ȡ�ֶ�Prop2��ֵ�����ֶε�<br>
	* �ֶ����� :��������2<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* �����ֶ�Prop2��ֵ�����ֶε�<br>
	* �ֶ����� :��������2<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

	/**
	* ��ȡ�ֶ�Prop3��ֵ�����ֶε�<br>
	* �ֶ����� :��������3<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp3() {
		return Prop3;
	}

	/**
	* �����ֶ�Prop3��ֵ�����ֶε�<br>
	* �ֶ����� :��������3<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp3(String prop3) {
		this.Prop3 = prop3;
    }

	/**
	* ��ȡ�ֶ�Prop4��ֵ�����ֶε�<br>
	* �ֶ����� :��������4<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp4() {
		return Prop4;
	}

	/**
	* �����ֶ�Prop4��ֵ�����ֶε�<br>
	* �ֶ����� :��������4<br>
	* �������� :varchar(200)<br>
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

}