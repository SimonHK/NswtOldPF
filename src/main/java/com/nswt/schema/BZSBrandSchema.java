package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * �����ƣ�Ʒ�Ʊ���<br>
 * ����룺BZSBrand<br>
 * ��������ID, BackupNo<br>
 */
public class BZSBrandSchema extends Schema {
	private Long ID;

	private Long SiteID;

	private String Name;

	private String BranchInnerCode;

	private String Alias;

	private String URL;

	private String ImagePath;

	private String Info;

	private String IndexTemplate;

	private String ListTemplate;

	private String ListNameRule;

	private String DetailTemplate;

	private String DetailNameRule;

	private String RssTemplate;

	private String RssNameRule;

	private Long OrderFlag;

	private Long ListPageSize;

	private Long ListPage;

	private String PublishFlag;

	private String SingleFlag;

	private Long HitCount;

	private String Meta_Keywords;

	private String Meta_Description;

	private String KeywordFlag;

	private String KeywordSetting;

	private String Memo;

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
		new SchemaColumn("Name", DataColumn.STRING, 2, 100 , 0 , true , false),
		new SchemaColumn("BranchInnerCode", DataColumn.STRING, 3, 100 , 0 , false , false),
		new SchemaColumn("Alias", DataColumn.STRING, 4, 100 , 0 , true , false),
		new SchemaColumn("URL", DataColumn.STRING, 5, 100 , 0 , false , false),
		new SchemaColumn("ImagePath", DataColumn.STRING, 6, 50 , 0 , false , false),
		new SchemaColumn("Info", DataColumn.STRING, 7, 1024 , 0 , false , false),
		new SchemaColumn("IndexTemplate", DataColumn.STRING, 8, 200 , 0 , false , false),
		new SchemaColumn("ListTemplate", DataColumn.STRING, 9, 200 , 0 , false , false),
		new SchemaColumn("ListNameRule", DataColumn.STRING, 10, 200 , 0 , false , false),
		new SchemaColumn("DetailTemplate", DataColumn.STRING, 11, 200 , 0 , false , false),
		new SchemaColumn("DetailNameRule", DataColumn.STRING, 12, 200 , 0 , false , false),
		new SchemaColumn("RssTemplate", DataColumn.STRING, 13, 200 , 0 , false , false),
		new SchemaColumn("RssNameRule", DataColumn.STRING, 14, 200 , 0 , false , false),
		new SchemaColumn("OrderFlag", DataColumn.LONG, 15, 0 , 0 , true , false),
		new SchemaColumn("ListPageSize", DataColumn.LONG, 16, 0 , 0 , false , false),
		new SchemaColumn("ListPage", DataColumn.LONG, 17, 0 , 0 , false , false),
		new SchemaColumn("PublishFlag", DataColumn.STRING, 18, 2 , 0 , true , false),
		new SchemaColumn("SingleFlag", DataColumn.STRING, 19, 2 , 0 , false , false),
		new SchemaColumn("HitCount", DataColumn.LONG, 20, 0 , 0 , false , false),
		new SchemaColumn("Meta_Keywords", DataColumn.STRING, 21, 200 , 0 , false , false),
		new SchemaColumn("Meta_Description", DataColumn.STRING, 22, 200 , 0 , false , false),
		new SchemaColumn("KeywordFlag", DataColumn.STRING, 23, 2 , 0 , false , false),
		new SchemaColumn("KeywordSetting", DataColumn.STRING, 24, 50 , 0 , false , false),
		new SchemaColumn("Memo", DataColumn.STRING, 25, 200 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 26, 50 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 27, 50 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 28, 50 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 29, 50 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 30, 200 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 31, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 32, 200 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 33, 0 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 34, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 35, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 36, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 37, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZSBrand";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into BZSBrand values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZSBrand set ID=?,SiteID=?,Name=?,BranchInnerCode=?,Alias=?,URL=?,ImagePath=?,Info=?,IndexTemplate=?,ListTemplate=?,ListNameRule=?,DetailTemplate=?,DetailNameRule=?,RssTemplate=?,RssNameRule=?,OrderFlag=?,ListPageSize=?,ListPage=?,PublishFlag=?,SingleFlag=?,HitCount=?,Meta_Keywords=?,Meta_Description=?,KeywordFlag=?,KeywordSetting=?,Memo=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZSBrand  where ID=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZSBrand  where ID=? and BackupNo=?";

	public BZSBrandSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[38];
	}

	protected Schema newInstance(){
		return new BZSBrandSchema();
	}

	protected SchemaSet newSet(){
		return new BZSBrandSet();
	}

	public BZSBrandSet query() {
		return query(null, -1, -1);
	}

	public BZSBrandSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZSBrandSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZSBrandSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZSBrandSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 2){Name = (String)v;return;}
		if (i == 3){BranchInnerCode = (String)v;return;}
		if (i == 4){Alias = (String)v;return;}
		if (i == 5){URL = (String)v;return;}
		if (i == 6){ImagePath = (String)v;return;}
		if (i == 7){Info = (String)v;return;}
		if (i == 8){IndexTemplate = (String)v;return;}
		if (i == 9){ListTemplate = (String)v;return;}
		if (i == 10){ListNameRule = (String)v;return;}
		if (i == 11){DetailTemplate = (String)v;return;}
		if (i == 12){DetailNameRule = (String)v;return;}
		if (i == 13){RssTemplate = (String)v;return;}
		if (i == 14){RssNameRule = (String)v;return;}
		if (i == 15){if(v==null){OrderFlag = null;}else{OrderFlag = new Long(v.toString());}return;}
		if (i == 16){if(v==null){ListPageSize = null;}else{ListPageSize = new Long(v.toString());}return;}
		if (i == 17){if(v==null){ListPage = null;}else{ListPage = new Long(v.toString());}return;}
		if (i == 18){PublishFlag = (String)v;return;}
		if (i == 19){SingleFlag = (String)v;return;}
		if (i == 20){if(v==null){HitCount = null;}else{HitCount = new Long(v.toString());}return;}
		if (i == 21){Meta_Keywords = (String)v;return;}
		if (i == 22){Meta_Description = (String)v;return;}
		if (i == 23){KeywordFlag = (String)v;return;}
		if (i == 24){KeywordSetting = (String)v;return;}
		if (i == 25){Memo = (String)v;return;}
		if (i == 26){Prop1 = (String)v;return;}
		if (i == 27){Prop2 = (String)v;return;}
		if (i == 28){Prop3 = (String)v;return;}
		if (i == 29){Prop4 = (String)v;return;}
		if (i == 30){AddUser = (String)v;return;}
		if (i == 31){AddTime = (Date)v;return;}
		if (i == 32){ModifyUser = (String)v;return;}
		if (i == 33){ModifyTime = (Date)v;return;}
		if (i == 34){BackupNo = (String)v;return;}
		if (i == 35){BackupOperator = (String)v;return;}
		if (i == 36){BackupTime = (Date)v;return;}
		if (i == 37){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return SiteID;}
		if (i == 2){return Name;}
		if (i == 3){return BranchInnerCode;}
		if (i == 4){return Alias;}
		if (i == 5){return URL;}
		if (i == 6){return ImagePath;}
		if (i == 7){return Info;}
		if (i == 8){return IndexTemplate;}
		if (i == 9){return ListTemplate;}
		if (i == 10){return ListNameRule;}
		if (i == 11){return DetailTemplate;}
		if (i == 12){return DetailNameRule;}
		if (i == 13){return RssTemplate;}
		if (i == 14){return RssNameRule;}
		if (i == 15){return OrderFlag;}
		if (i == 16){return ListPageSize;}
		if (i == 17){return ListPage;}
		if (i == 18){return PublishFlag;}
		if (i == 19){return SingleFlag;}
		if (i == 20){return HitCount;}
		if (i == 21){return Meta_Keywords;}
		if (i == 22){return Meta_Description;}
		if (i == 23){return KeywordFlag;}
		if (i == 24){return KeywordSetting;}
		if (i == 25){return Memo;}
		if (i == 26){return Prop1;}
		if (i == 27){return Prop2;}
		if (i == 28){return Prop3;}
		if (i == 29){return Prop4;}
		if (i == 30){return AddUser;}
		if (i == 31){return AddTime;}
		if (i == 32){return ModifyUser;}
		if (i == 33){return ModifyTime;}
		if (i == 34){return BackupNo;}
		if (i == 35){return BackupOperator;}
		if (i == 36){return BackupTime;}
		if (i == 37){return BackupMemo;}
		return null;
	}

	/**
	* ��ȡ�ֶ�ID��ֵ�����ֶε�<br>
	* �ֶ����� :Ʒ��ID<br>
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
	* �ֶ����� :Ʒ��ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setID(long iD) {
		this.ID = new Long(iD);
    }

	/**
	* �����ֶ�ID��ֵ�����ֶε�<br>
	* �ֶ����� :Ʒ��ID<br>
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
	* �ֶ����� :Ʒ������<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getName() {
		return Name;
	}

	/**
	* �����ֶ�Name��ֵ�����ֶε�<br>
	* �ֶ����� :Ʒ������<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setName(String name) {
		this.Name = name;
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
	* �ֶ����� :Ʒ��Ӣ����<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getAlias() {
		return Alias;
	}

	/**
	* �����ֶ�Alias��ֵ�����ֶε�<br>
	* �ֶ����� :Ʒ��Ӣ����<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setAlias(String alias) {
		this.Alias = alias;
    }

	/**
	* ��ȡ�ֶ�URL��ֵ�����ֶε�<br>
	* �ֶ����� :Ʒ��URL<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getURL() {
		return URL;
	}

	/**
	* �����ֶ�URL��ֵ�����ֶε�<br>
	* �ֶ����� :Ʒ��URL<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setURL(String uRL) {
		this.URL = uRL;
    }

	/**
	* ��ȡ�ֶ�ImagePath��ֵ�����ֶε�<br>
	* �ֶ����� :Ʒ������ͼ<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getImagePath() {
		return ImagePath;
	}

	/**
	* �����ֶ�ImagePath��ֵ�����ֶε�<br>
	* �ֶ����� :Ʒ������ͼ<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setImagePath(String imagePath) {
		this.ImagePath = imagePath;
    }

	/**
	* ��ȡ�ֶ�Info��ֵ�����ֶε�<br>
	* �ֶ����� :Ʒ������<br>
	* �������� :varchar(1024)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getInfo() {
		return Info;
	}

	/**
	* �����ֶ�Info��ֵ�����ֶε�<br>
	* �ֶ����� :Ʒ������<br>
	* �������� :varchar(1024)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setInfo(String info) {
		this.Info = info;
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
	* �ֶ����� :�б�ҳ���??ҳ��<br>
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
	* �ֶ����� :�б�ҳ���??ҳ��<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setListPage(long listPage) {
		this.ListPage = new Long(listPage);
    }

	/**
	* �����ֶ�ListPage��ֵ�����ֶε�<br>
	* �ֶ����� :�б�ҳ���??ҳ��<br>
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
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getMeta_Keywords() {
		return Meta_Keywords;
	}

	/**
	* �����ֶ�Meta_Keywords��ֵ�����ֶε�<br>
	* �ֶ����� :meta�ؼ���<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setMeta_Keywords(String meta_Keywords) {
		this.Meta_Keywords = meta_Keywords;
    }

	/**
	* ��ȡ�ֶ�Meta_Description��ֵ�����ֶε�<br>
	* �ֶ����� :Meta����<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getMeta_Description() {
		return Meta_Description;
	}

	/**
	* �����ֶ�Meta_Description��ֵ�����ֶε�<br>
	* �ֶ����� :Meta����<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setMeta_Description(String meta_Description) {
		this.Meta_Description = meta_Description;
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