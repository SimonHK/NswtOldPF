package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * �����ƣ���Ŀ����<br>
 * ����룺BZCCatalog<br>
 * ��������ID, BackupNo<br>
 */
public class BZCCatalogSchema extends Schema {
	private Long ID;

	private Long ParentID;

	private Long SiteID;

	private String Name;

	private String InnerCode;

	private String BranchInnerCode;

	private String Alias;

	private String URL;

	private String ImagePath;

	private Long Type;

	private String IndexTemplate;

	private String ListTemplate;

	private String ListNameRule;

	private String DetailTemplate;

	private String DetailNameRule;

	private String RssTemplate;

	private String RssNameRule;

	private String Workflow;

	private Long TreeLevel;

	private Long ChildCount;

	private Long IsLeaf;

	private Long IsDirty;

	private Long Total;

	private Long OrderFlag;

	private String Logo;

	private Long ListPageSize;

	private Long ListPage;

	private String PublishFlag;

	private String SingleFlag;

	private Long HitCount;

	private String Meta_Keywords;

	private String Meta_Description;

	private String OrderColumn;

	private Long Integral;

	private String KeywordFlag;

	private String KeywordSetting;

	private String AllowContribute;

	private String ClusterSourceID;

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
		new SchemaColumn("ParentID", DataColumn.LONG, 1, 0 , 0 , true , false),
		new SchemaColumn("SiteID", DataColumn.LONG, 2, 0 , 0 , true , false),
		new SchemaColumn("Name", DataColumn.STRING, 3, 100 , 0 , true , false),
		new SchemaColumn("InnerCode", DataColumn.STRING, 4, 100 , 0 , true , false),
		new SchemaColumn("BranchInnerCode", DataColumn.STRING, 5, 100 , 0 , false , false),
		new SchemaColumn("Alias", DataColumn.STRING, 6, 100 , 0 , true , false),
		new SchemaColumn("URL", DataColumn.STRING, 7, 100 , 0 , false , false),
		new SchemaColumn("ImagePath", DataColumn.STRING, 8, 50 , 0 , false , false),
		new SchemaColumn("Type", DataColumn.LONG, 9, 0 , 0 , true , false),
		new SchemaColumn("IndexTemplate", DataColumn.STRING, 10, 200 , 0 , false , false),
		new SchemaColumn("ListTemplate", DataColumn.STRING, 11, 200 , 0 , false , false),
		new SchemaColumn("ListNameRule", DataColumn.STRING, 12, 200 , 0 , false , false),
		new SchemaColumn("DetailTemplate", DataColumn.STRING, 13, 200 , 0 , false , false),
		new SchemaColumn("DetailNameRule", DataColumn.STRING, 14, 200 , 0 , false , false),
		new SchemaColumn("RssTemplate", DataColumn.STRING, 15, 200 , 0 , false , false),
		new SchemaColumn("RssNameRule", DataColumn.STRING, 16, 200 , 0 , false , false),
		new SchemaColumn("Workflow", DataColumn.STRING, 17, 100 , 0 , false , false),
		new SchemaColumn("TreeLevel", DataColumn.LONG, 18, 0 , 0 , true , false),
		new SchemaColumn("ChildCount", DataColumn.LONG, 19, 0 , 0 , true , false),
		new SchemaColumn("IsLeaf", DataColumn.LONG, 20, 0 , 0 , true , false),
		new SchemaColumn("IsDirty", DataColumn.LONG, 21, 0 , 0 , false , false),
		new SchemaColumn("Total", DataColumn.LONG, 22, 0 , 0 , true , false),
		new SchemaColumn("OrderFlag", DataColumn.LONG, 23, 0 , 0 , true , false),
		new SchemaColumn("Logo", DataColumn.STRING, 24, 100 , 0 , false , false),
		new SchemaColumn("ListPageSize", DataColumn.LONG, 25, 0 , 0 , false , false),
		new SchemaColumn("ListPage", DataColumn.LONG, 26, 0 , 0 , false , false),
		new SchemaColumn("PublishFlag", DataColumn.STRING, 27, 2 , 0 , true , false),
		new SchemaColumn("SingleFlag", DataColumn.STRING, 28, 2 , 0 , false , false),
		new SchemaColumn("HitCount", DataColumn.LONG, 29, 0 , 0 , false , false),
		new SchemaColumn("Meta_Keywords", DataColumn.STRING, 30, 1000 , 0 , false , false),
		new SchemaColumn("Meta_Description", DataColumn.STRING, 31, 1000 , 0 , false , false),
		new SchemaColumn("OrderColumn", DataColumn.STRING, 32, 20 , 0 , false , false),
		new SchemaColumn("Integral", DataColumn.LONG, 33, 0 , 0 , false , false),
		new SchemaColumn("KeywordFlag", DataColumn.STRING, 34, 2 , 0 , false , false),
		new SchemaColumn("KeywordSetting", DataColumn.STRING, 35, 50 , 0 , false , false),
		new SchemaColumn("AllowContribute", DataColumn.STRING, 36, 2 , 0 , false , false),
		new SchemaColumn("ClusterSourceID", DataColumn.STRING, 37, 50 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 38, 50 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 39, 50 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 40, 50 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 41, 50 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 42, 200 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 43, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 44, 200 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 45, 0 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 46, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 47, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 48, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 49, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZCCatalog";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into BZCCatalog values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZCCatalog set ID=?,ParentID=?,SiteID=?,Name=?,InnerCode=?,BranchInnerCode=?,Alias=?,URL=?,ImagePath=?,Type=?,IndexTemplate=?,ListTemplate=?,ListNameRule=?,DetailTemplate=?,DetailNameRule=?,RssTemplate=?,RssNameRule=?,Workflow=?,TreeLevel=?,ChildCount=?,IsLeaf=?,IsDirty=?,Total=?,OrderFlag=?,Logo=?,ListPageSize=?,ListPage=?,PublishFlag=?,SingleFlag=?,HitCount=?,Meta_Keywords=?,Meta_Description=?,OrderColumn=?,Integral=?,KeywordFlag=?,KeywordSetting=?,AllowContribute=?,ClusterSourceID=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZCCatalog  where ID=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZCCatalog  where ID=? and BackupNo=?";

	public BZCCatalogSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[50];
	}

	protected Schema newInstance(){
		return new BZCCatalogSchema();
	}

	protected SchemaSet newSet(){
		return new BZCCatalogSet();
	}

	public BZCCatalogSet query() {
		return query(null, -1, -1);
	}

	public BZCCatalogSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZCCatalogSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZCCatalogSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZCCatalogSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){ParentID = null;}else{ParentID = new Long(v.toString());}return;}
		if (i == 2){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 3){Name = (String)v;return;}
		if (i == 4){InnerCode = (String)v;return;}
		if (i == 5){BranchInnerCode = (String)v;return;}
		if (i == 6){Alias = (String)v;return;}
		if (i == 7){URL = (String)v;return;}
		if (i == 8){ImagePath = (String)v;return;}
		if (i == 9){if(v==null){Type = null;}else{Type = new Long(v.toString());}return;}
		if (i == 10){IndexTemplate = (String)v;return;}
		if (i == 11){ListTemplate = (String)v;return;}
		if (i == 12){ListNameRule = (String)v;return;}
		if (i == 13){DetailTemplate = (String)v;return;}
		if (i == 14){DetailNameRule = (String)v;return;}
		if (i == 15){RssTemplate = (String)v;return;}
		if (i == 16){RssNameRule = (String)v;return;}
		if (i == 17){Workflow = (String)v;return;}
		if (i == 18){if(v==null){TreeLevel = null;}else{TreeLevel = new Long(v.toString());}return;}
		if (i == 19){if(v==null){ChildCount = null;}else{ChildCount = new Long(v.toString());}return;}
		if (i == 20){if(v==null){IsLeaf = null;}else{IsLeaf = new Long(v.toString());}return;}
		if (i == 21){if(v==null){IsDirty = null;}else{IsDirty = new Long(v.toString());}return;}
		if (i == 22){if(v==null){Total = null;}else{Total = new Long(v.toString());}return;}
		if (i == 23){if(v==null){OrderFlag = null;}else{OrderFlag = new Long(v.toString());}return;}
		if (i == 24){Logo = (String)v;return;}
		if (i == 25){if(v==null){ListPageSize = null;}else{ListPageSize = new Long(v.toString());}return;}
		if (i == 26){if(v==null){ListPage = null;}else{ListPage = new Long(v.toString());}return;}
		if (i == 27){PublishFlag = (String)v;return;}
		if (i == 28){SingleFlag = (String)v;return;}
		if (i == 29){if(v==null){HitCount = null;}else{HitCount = new Long(v.toString());}return;}
		if (i == 30){Meta_Keywords = (String)v;return;}
		if (i == 31){Meta_Description = (String)v;return;}
		if (i == 32){OrderColumn = (String)v;return;}
		if (i == 33){if(v==null){Integral = null;}else{Integral = new Long(v.toString());}return;}
		if (i == 34){KeywordFlag = (String)v;return;}
		if (i == 35){KeywordSetting = (String)v;return;}
		if (i == 36){AllowContribute = (String)v;return;}
		if (i == 37){ClusterSourceID = (String)v;return;}
		if (i == 38){Prop1 = (String)v;return;}
		if (i == 39){Prop2 = (String)v;return;}
		if (i == 40){Prop3 = (String)v;return;}
		if (i == 41){Prop4 = (String)v;return;}
		if (i == 42){AddUser = (String)v;return;}
		if (i == 43){AddTime = (Date)v;return;}
		if (i == 44){ModifyUser = (String)v;return;}
		if (i == 45){ModifyTime = (Date)v;return;}
		if (i == 46){BackupNo = (String)v;return;}
		if (i == 47){BackupOperator = (String)v;return;}
		if (i == 48){BackupTime = (Date)v;return;}
		if (i == 49){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return ParentID;}
		if (i == 2){return SiteID;}
		if (i == 3){return Name;}
		if (i == 4){return InnerCode;}
		if (i == 5){return BranchInnerCode;}
		if (i == 6){return Alias;}
		if (i == 7){return URL;}
		if (i == 8){return ImagePath;}
		if (i == 9){return Type;}
		if (i == 10){return IndexTemplate;}
		if (i == 11){return ListTemplate;}
		if (i == 12){return ListNameRule;}
		if (i == 13){return DetailTemplate;}
		if (i == 14){return DetailNameRule;}
		if (i == 15){return RssTemplate;}
		if (i == 16){return RssNameRule;}
		if (i == 17){return Workflow;}
		if (i == 18){return TreeLevel;}
		if (i == 19){return ChildCount;}
		if (i == 20){return IsLeaf;}
		if (i == 21){return IsDirty;}
		if (i == 22){return Total;}
		if (i == 23){return OrderFlag;}
		if (i == 24){return Logo;}
		if (i == 25){return ListPageSize;}
		if (i == 26){return ListPage;}
		if (i == 27){return PublishFlag;}
		if (i == 28){return SingleFlag;}
		if (i == 29){return HitCount;}
		if (i == 30){return Meta_Keywords;}
		if (i == 31){return Meta_Description;}
		if (i == 32){return OrderColumn;}
		if (i == 33){return Integral;}
		if (i == 34){return KeywordFlag;}
		if (i == 35){return KeywordSetting;}
		if (i == 36){return AllowContribute;}
		if (i == 37){return ClusterSourceID;}
		if (i == 38){return Prop1;}
		if (i == 39){return Prop2;}
		if (i == 40){return Prop3;}
		if (i == 41){return Prop4;}
		if (i == 42){return AddUser;}
		if (i == 43){return AddTime;}
		if (i == 44){return ModifyUser;}
		if (i == 45){return ModifyTime;}
		if (i == 46){return BackupNo;}
		if (i == 47){return BackupOperator;}
		if (i == 48){return BackupTime;}
		if (i == 49){return BackupMemo;}
		return null;
	}

	/**
	* ��ȡ�ֶ�ID��ֵ�����ֶε�<br>
	* �ֶ����� :��ĿID<br>
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
	* �ֶ����� :��ĿID<br>
	* �������� :bigint<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setID(long iD) {
		this.ID = new Long(iD);
    }

	/**
	* �����ֶ�ID��ֵ�����ֶε�<br>
	* �ֶ����� :��ĿID<br>
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
	* ��ȡ�ֶ�ParentID��ֵ�����ֶε�<br>
	* �ֶ����� :������ĿID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public long getParentID() {
		if(ParentID==null){return 0;}
		return ParentID.longValue();
	}

	/**
	* �����ֶ�ParentID��ֵ�����ֶε�<br>
	* �ֶ����� :������ĿID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setParentID(long parentID) {
		this.ParentID = new Long(parentID);
    }

	/**
	* �����ֶ�ParentID��ֵ�����ֶε�<br>
	* �ֶ����� :������ĿID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setParentID(String parentID) {
		if (parentID == null){
			this.ParentID = null;
			return;
		}
		this.ParentID = new Long(parentID);
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
	* �ֶ����� :��Ŀ����<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getName() {
		return Name;
	}

	/**
	* �����ֶ�Name��ֵ�����ֶε�<br>
	* �ֶ����� :��Ŀ����<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setName(String name) {
		this.Name = name;
    }

	/**
	* ��ȡ�ֶ�InnerCode��ֵ�����ֶε�<br>
	* �ֶ����� :��Ŀ�ڲ�����<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getInnerCode() {
		return InnerCode;
	}

	/**
	* �����ֶ�InnerCode��ֵ�����ֶε�<br>
	* �ֶ����� :��Ŀ�ڲ�����<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setInnerCode(String innerCode) {
		this.InnerCode = innerCode;
    }

	/**
	* ��ȡ�ֶ�BranchInnerCode��ֵ�����ֶε�<br>
	* �ֶ����� :���������ڲ�����<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getBranchInnerCode() {
		return BranchInnerCode;
	}

	/**
	* �����ֶ�BranchInnerCode��ֵ�����ֶε�<br>
	* �ֶ����� :���������ڲ�����<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setBranchInnerCode(String branchInnerCode) {
		this.BranchInnerCode = branchInnerCode;
    }

	/**
	* ��ȡ�ֶ�Alias��ֵ�����ֶε�<br>
	* �ֶ����� :��ĿӢ����<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getAlias() {
		return Alias;
	}

	/**
	* �����ֶ�Alias��ֵ�����ֶε�<br>
	* �ֶ����� :��ĿӢ����<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setAlias(String alias) {
		this.Alias = alias;
    }

	/**
	* ��ȡ�ֶ�URL��ֵ�����ֶε�<br>
	* �ֶ����� :��ĿURL<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getURL() {
		return URL;
	}

	/**
	* �����ֶ�URL��ֵ�����ֶε�<br>
	* �ֶ����� :��ĿURL<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setURL(String uRL) {
		this.URL = uRL;
    }

	/**
	* ��ȡ�ֶ�ImagePath��ֵ�����ֶε�<br>
	* �ֶ����� :��Ŀ����ͼ<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getImagePath() {
		return ImagePath;
	}

	/**
	* �����ֶ�ImagePath��ֵ�����ֶε�<br>
	* �ֶ����� :��Ŀ����ͼ<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setImagePath(String imagePath) {
		this.ImagePath = imagePath;
    }

	/**
	* ��ȡ�ֶ�Type��ֵ�����ֶε�<br>
	* �ֶ����� :��Ŀ����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	* ��ע��Ϣ :<br>
	1 ��ͨ��Ŀ 2 ר�� 3 �ڿ�<br>
	*/
	public long getType() {
		if(Type==null){return 0;}
		return Type.longValue();
	}

	/**
	* �����ֶ�Type��ֵ�����ֶε�<br>
	* �ֶ����� :��Ŀ����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	* ��ע��Ϣ :<br>
	1 ��ͨ��Ŀ 2 ר�� 3 �ڿ�<br>
	*/
	public void setType(long type) {
		this.Type = new Long(type);
    }

	/**
	* �����ֶ�Type��ֵ�����ֶε�<br>
	* �ֶ����� :��Ŀ����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	* ��ע��Ϣ :<br>
	1 ��ͨ��Ŀ 2 ר�� 3 �ڿ�<br>
	*/
	public void setType(String type) {
		if (type == null){
			this.Type = null;
			return;
		}
		this.Type = new Long(type);
    }

	/**
	* ��ȡ�ֶ�IndexTemplate��ֵ�����ֶε�<br>
	* �ֶ����� :��ҳģ��<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getIndexTemplate() {
		return IndexTemplate;
	}

	/**
	* �����ֶ�IndexTemplate��ֵ�����ֶε�<br>
	* �ֶ����� :��ҳģ��<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setIndexTemplate(String indexTemplate) {
		this.IndexTemplate = indexTemplate;
    }

	/**
	* ��ȡ�ֶ�ListTemplate��ֵ�����ֶε�<br>
	* �ֶ����� :�б�ҳģ��<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getListTemplate() {
		return ListTemplate;
	}

	/**
	* �����ֶ�ListTemplate��ֵ�����ֶε�<br>
	* �ֶ����� :�б�ҳģ��<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setListTemplate(String listTemplate) {
		this.ListTemplate = listTemplate;
    }

	/**
	* ��ȡ�ֶ�ListNameRule��ֵ�����ֶε�<br>
	* �ֶ����� :�б�ҳ��������<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getListNameRule() {
		return ListNameRule;
	}

	/**
	* �����ֶ�ListNameRule��ֵ�����ֶε�<br>
	* �ֶ����� :�б�ҳ��������<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setListNameRule(String listNameRule) {
		this.ListNameRule = listNameRule;
    }

	/**
	* ��ȡ�ֶ�DetailTemplate��ֵ�����ֶε�<br>
	* �ֶ����� :������ϸҳģ��<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getDetailTemplate() {
		return DetailTemplate;
	}

	/**
	* �����ֶ�DetailTemplate��ֵ�����ֶε�<br>
	* �ֶ����� :������ϸҳģ��<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setDetailTemplate(String detailTemplate) {
		this.DetailTemplate = detailTemplate;
    }

	/**
	* ��ȡ�ֶ�DetailNameRule��ֵ�����ֶε�<br>
	* �ֶ����� :����ҳ��������<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getDetailNameRule() {
		return DetailNameRule;
	}

	/**
	* �����ֶ�DetailNameRule��ֵ�����ֶε�<br>
	* �ֶ����� :����ҳ��������<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setDetailNameRule(String detailNameRule) {
		this.DetailNameRule = detailNameRule;
    }

	/**
	* ��ȡ�ֶ�RssTemplate��ֵ�����ֶε�<br>
	* �ֶ����� :Rssģ��<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getRssTemplate() {
		return RssTemplate;
	}

	/**
	* �����ֶ�RssTemplate��ֵ�����ֶε�<br>
	* �ֶ����� :Rssģ��<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setRssTemplate(String rssTemplate) {
		this.RssTemplate = rssTemplate;
    }

	/**
	* ��ȡ�ֶ�RssNameRule��ֵ�����ֶε�<br>
	* �ֶ����� :Rss��������<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getRssNameRule() {
		return RssNameRule;
	}

	/**
	* �����ֶ�RssNameRule��ֵ�����ֶε�<br>
	* �ֶ����� :Rss��������<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setRssNameRule(String rssNameRule) {
		this.RssNameRule = rssNameRule;
    }

	/**
	* ��ȡ�ֶ�Workflow��ֵ�����ֶε�<br>
	* �ֶ����� :����������<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getWorkflow() {
		return Workflow;
	}

	/**
	* �����ֶ�Workflow��ֵ�����ֶε�<br>
	* �ֶ����� :����������<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setWorkflow(String workflow) {
		this.Workflow = workflow;
    }

	/**
	* ��ȡ�ֶ�TreeLevel��ֵ�����ֶε�<br>
	* �ֶ����� :��Ŀ�㼶<br>
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
	* �ֶ����� :��Ŀ�㼶<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setTreeLevel(long treeLevel) {
		this.TreeLevel = new Long(treeLevel);
    }

	/**
	* �����ֶ�TreeLevel��ֵ�����ֶε�<br>
	* �ֶ����� :��Ŀ�㼶<br>
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
	* ��ȡ�ֶ�ChildCount��ֵ�����ֶε�<br>
	* �ֶ����� :�ӽӵ���<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public long getChildCount() {
		if(ChildCount==null){return 0;}
		return ChildCount.longValue();
	}

	/**
	* �����ֶ�ChildCount��ֵ�����ֶε�<br>
	* �ֶ����� :�ӽӵ���<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setChildCount(long childCount) {
		this.ChildCount = new Long(childCount);
    }

	/**
	* �����ֶ�ChildCount��ֵ�����ֶε�<br>
	* �ֶ����� :�ӽӵ���<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setChildCount(String childCount) {
		if (childCount == null){
			this.ChildCount = null;
			return;
		}
		this.ChildCount = new Long(childCount);
    }

	/**
	* ��ȡ�ֶ�IsLeaf��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ���ĩ���ڵ�<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	* ��ע��Ϣ :<br>
	1-�� 0-����<br>
	*/
	public long getIsLeaf() {
		if(IsLeaf==null){return 0;}
		return IsLeaf.longValue();
	}

	/**
	* �����ֶ�IsLeaf��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ���ĩ���ڵ�<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	* ��ע��Ϣ :<br>
	1-�� 0-����<br>
	*/
	public void setIsLeaf(long isLeaf) {
		this.IsLeaf = new Long(isLeaf);
    }

	/**
	* �����ֶ�IsLeaf��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ���ĩ���ڵ�<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	* ��ע��Ϣ :<br>
	1-�� 0-����<br>
	*/
	public void setIsLeaf(String isLeaf) {
		if (isLeaf == null){
			this.IsLeaf = null;
			return;
		}
		this.IsLeaf = new Long(isLeaf);
    }

	/**
	* ��ȡ�ֶ�IsDirty��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ��Ѿ�����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	* ��ע��Ϣ :<br>
	1-�� 0-����<br>
	*/
	public long getIsDirty() {
		if(IsDirty==null){return 0;}
		return IsDirty.longValue();
	}

	/**
	* �����ֶ�IsDirty��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ��Ѿ�����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	* ��ע��Ϣ :<br>
	1-�� 0-����<br>
	*/
	public void setIsDirty(long isDirty) {
		this.IsDirty = new Long(isDirty);
    }

	/**
	* �����ֶ�IsDirty��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ��Ѿ�����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	* ��ע��Ϣ :<br>
	1-�� 0-����<br>
	*/
	public void setIsDirty(String isDirty) {
		if (isDirty == null){
			this.IsDirty = null;
			return;
		}
		this.IsDirty = new Long(isDirty);
    }

	/**
	* ��ȡ�ֶ�Total��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	* ��ע��Ϣ :<br>
	1-�� 0-����<br>
	*/
	public long getTotal() {
		if(Total==null){return 0;}
		return Total.longValue();
	}

	/**
	* �����ֶ�Total��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	* ��ע��Ϣ :<br>
	1-�� 0-����<br>
	*/
	public void setTotal(long total) {
		this.Total = new Long(total);
    }

	/**
	* �����ֶ�Total��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	* ��ע��Ϣ :<br>
	1-�� 0-����<br>
	*/
	public void setTotal(String total) {
		if (total == null){
			this.Total = null;
			return;
		}
		this.Total = new Long(total);
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
	* ��ȡ�ֶ�Logo��ֵ�����ֶε�<br>
	* �ֶ����� :��ĿLogo<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getLogo() {
		return Logo;
	}

	/**
	* �����ֶ�Logo��ֵ�����ֶε�<br>
	* �ֶ����� :��ĿLogo<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setLogo(String logo) {
		this.Logo = logo;
    }

	/**
	* ��ȡ�ֶ�ListPageSize��ֵ�����ֶε�<br>
	* �ֶ����� :�б�ҳ������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getListPageSize() {
		if(ListPageSize==null){return 0;}
		return ListPageSize.longValue();
	}

	/**
	* �����ֶ�ListPageSize��ֵ�����ֶε�<br>
	* �ֶ����� :�б�ҳ������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setListPageSize(long listPageSize) {
		this.ListPageSize = new Long(listPageSize);
    }

	/**
	* �����ֶ�ListPageSize��ֵ�����ֶε�<br>
	* �ֶ����� :�б�ҳ������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setListPageSize(String listPageSize) {
		if (listPageSize == null){
			this.ListPageSize = null;
			return;
		}
		this.ListPageSize = new Long(listPageSize);
    }

	/**
	* ��ȡ�ֶ�ListPage��ֵ�����ֶε�<br>
	* �ֶ����� :�б�ҳ����ҳ��<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getListPage() {
		if(ListPage==null){return 0;}
		return ListPage.longValue();
	}

	/**
	* �����ֶ�ListPage��ֵ�����ֶε�<br>
	* �ֶ����� :�б�ҳ����ҳ��<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setListPage(long listPage) {
		this.ListPage = new Long(listPage);
    }

	/**
	* �����ֶ�ListPage��ֵ�����ֶε�<br>
	* �ֶ����� :�б�ҳ����ҳ��<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setListPage(String listPage) {
		if (listPage == null){
			this.ListPage = null;
			return;
		}
		this.ListPage = new Long(listPage);
    }

	/**
	* ��ȡ�ֶ�PublishFlag��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ񿪷���ʾ<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	* ��ע��Ϣ :<br>
	0-�� 1-��<br>
	*/
	public String getPublishFlag() {
		return PublishFlag;
	}

	/**
	* �����ֶ�PublishFlag��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ񿪷���ʾ<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	* ��ע��Ϣ :<br>
	0-�� 1-��<br>
	*/
	public void setPublishFlag(String publishFlag) {
		this.PublishFlag = publishFlag;
    }

	/**
	* ��ȡ�ֶ�SingleFlag��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ�ƪ����<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getSingleFlag() {
		return SingleFlag;
	}

	/**
	* �����ֶ�SingleFlag��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ�ƪ����<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setSingleFlag(String singleFlag) {
		this.SingleFlag = singleFlag;
    }

	/**
	* ��ȡ�ֶ�HitCount��ֵ�����ֶε�<br>
	* �ֶ����� :�����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getHitCount() {
		if(HitCount==null){return 0;}
		return HitCount.longValue();
	}

	/**
	* �����ֶ�HitCount��ֵ�����ֶε�<br>
	* �ֶ����� :�����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setHitCount(long hitCount) {
		this.HitCount = new Long(hitCount);
    }

	/**
	* �����ֶ�HitCount��ֵ�����ֶε�<br>
	* �ֶ����� :�����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setHitCount(String hitCount) {
		if (hitCount == null){
			this.HitCount = null;
			return;
		}
		this.HitCount = new Long(hitCount);
    }

	/**
	* ��ȡ�ֶ�Meta_Keywords��ֵ�����ֶε�<br>
	* �ֶ����� :meta�ؼ���<br>
	* �������� :varchar(1000)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getMeta_Keywords() {
		return Meta_Keywords;
	}

	/**
	* �����ֶ�Meta_Keywords��ֵ�����ֶε�<br>
	* �ֶ����� :meta�ؼ���<br>
	* �������� :varchar(1000)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setMeta_Keywords(String meta_Keywords) {
		this.Meta_Keywords = meta_Keywords;
    }

	/**
	* ��ȡ�ֶ�Meta_Description��ֵ�����ֶε�<br>
	* �ֶ����� :Meta����<br>
	* �������� :varchar(1000)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getMeta_Description() {
		return Meta_Description;
	}

	/**
	* �����ֶ�Meta_Description��ֵ�����ֶε�<br>
	* �ֶ����� :Meta����<br>
	* �������� :varchar(1000)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setMeta_Description(String meta_Description) {
		this.Meta_Description = meta_Description;
    }

	/**
	* ��ȡ�ֶ�OrderColumn��ֵ�����ֶε�<br>
	* �ֶ����� :���������ֶ�<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getOrderColumn() {
		return OrderColumn;
	}

	/**
	* �����ֶ�OrderColumn��ֵ�����ֶε�<br>
	* �ֶ����� :���������ֶ�<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setOrderColumn(String orderColumn) {
		this.OrderColumn = orderColumn;
    }

	/**
	* ��ȡ�ֶ�Integral��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getIntegral() {
		if(Integral==null){return 0;}
		return Integral.longValue();
	}

	/**
	* �����ֶ�Integral��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setIntegral(long integral) {
		this.Integral = new Long(integral);
    }

	/**
	* �����ֶ�Integral��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setIntegral(String integral) {
		if (integral == null){
			this.Integral = null;
			return;
		}
		this.Integral = new Long(integral);
    }

	/**
	* ��ȡ�ֶ�KeywordFlag��ֵ�����ֶε�<br>
	* �ֶ����� :�ؼ��ʱ��<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getKeywordFlag() {
		return KeywordFlag;
	}

	/**
	* �����ֶ�KeywordFlag��ֵ�����ֶε�<br>
	* �ֶ����� :�ؼ��ʱ��<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setKeywordFlag(String keywordFlag) {
		this.KeywordFlag = keywordFlag;
    }

	/**
	* ��ȡ�ֶ�KeywordSetting��ֵ�����ֶε�<br>
	* �ֶ����� :��ȡ���¹ؼ���<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getKeywordSetting() {
		return KeywordSetting;
	}

	/**
	* �����ֶ�KeywordSetting��ֵ�����ֶε�<br>
	* �ֶ����� :��ȡ���¹ؼ���<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setKeywordSetting(String keywordSetting) {
		this.KeywordSetting = keywordSetting;
    }

	/**
	* ��ȡ�ֶ�AllowContribute��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ�����Ͷ��<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getAllowContribute() {
		return AllowContribute;
	}

	/**
	* �����ֶ�AllowContribute��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ�����Ͷ��<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAllowContribute(String allowContribute) {
		this.AllowContribute = allowContribute;
    }

	/**
	* ��ȡ�ֶ�ClusterSourceID��ֵ�����ֶε�<br>
	* �ֶ����� :��վȺ��ԴID<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getClusterSourceID() {
		return ClusterSourceID;
	}

	/**
	* �����ֶ�ClusterSourceID��ֵ�����ֶε�<br>
	* �ֶ����� :��վȺ��ԴID<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setClusterSourceID(String clusterSourceID) {
		this.ClusterSourceID = clusterSourceID;
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