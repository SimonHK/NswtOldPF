package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * �����ƣ���Ʒ����<br>
 * ����룺BZSGoods<br>
 * ��������ID, BackupNo<br>
 */
public class BZSGoodsSchema extends Schema {
	private Long ID;

	private Long SiteID;

	private Long CatalogID;

	private String CatalogInnerCode;

	private Long BrandID;

	private String BranchInnerCode;

	private String Type;

	private String SN;

	private String Name;

	private String Alias;

	private String BarCode;

	private Long WorkFlowID;

	private String Status;

	private String Factory;

	private Long OrderFlag;

	private Float MarketPrice;

	private Float Price;

	private Float Discount;

	private Float OfferPrice;

	private Float MemberPrice;

	private Float VIPPrice;

	private Date EffectDate;

	private Long Store;

	private String Standard;

	private String Unit;

	private Long Score;

	private Long CommentCount;

	private Long SaleCount;

	private Long HitCount;

	private String Image0;

	private String Image1;

	private String Image2;

	private String Image3;

	private String RelativeGoods;

	private String Keyword;

	private Date TopDate;

	private String TopFlag;

	private String Content;

	private String Summary;

	private String RedirectURL;

	private String Attribute;

	private String RecommendGoods;

	private Long StickTime;

	private Date PublishDate;

	private Date DownlineDate;

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
		new SchemaColumn("CatalogID", DataColumn.LONG, 2, 0 , 0 , true , false),
		new SchemaColumn("CatalogInnerCode", DataColumn.STRING, 3, 100 , 0 , true , false),
		new SchemaColumn("BrandID", DataColumn.LONG, 4, 0 , 0 , false , false),
		new SchemaColumn("BranchInnerCode", DataColumn.STRING, 5, 100 , 0 , false , false),
		new SchemaColumn("Type", DataColumn.STRING, 6, 2 , 0 , true , false),
		new SchemaColumn("SN", DataColumn.STRING, 7, 50 , 0 , false , false),
		new SchemaColumn("Name", DataColumn.STRING, 8, 100 , 0 , true , false),
		new SchemaColumn("Alias", DataColumn.STRING, 9, 100 , 0 , false , false),
		new SchemaColumn("BarCode", DataColumn.STRING, 10, 128 , 0 , false , false),
		new SchemaColumn("WorkFlowID", DataColumn.LONG, 11, 0 , 0 , false , false),
		new SchemaColumn("Status", DataColumn.STRING, 12, 20 , 0 , false , false),
		new SchemaColumn("Factory", DataColumn.STRING, 13, 100 , 0 , false , false),
		new SchemaColumn("OrderFlag", DataColumn.LONG, 14, 0 , 0 , false , false),
		new SchemaColumn("MarketPrice", DataColumn.FLOAT, 15, 12 , 2 , false , false),
		new SchemaColumn("Price", DataColumn.FLOAT, 16, 12 , 2 , false , false),
		new SchemaColumn("Discount", DataColumn.FLOAT, 17, 12 , 2 , false , false),
		new SchemaColumn("OfferPrice", DataColumn.FLOAT, 18, 12 , 2 , false , false),
		new SchemaColumn("MemberPrice", DataColumn.FLOAT, 19, 12 , 2 , false , false),
		new SchemaColumn("VIPPrice", DataColumn.FLOAT, 20, 12 , 2 , false , false),
		new SchemaColumn("EffectDate", DataColumn.DATETIME, 21, 0 , 0 , false , false),
		new SchemaColumn("Store", DataColumn.LONG, 22, 0 , 0 , true , false),
		new SchemaColumn("Standard", DataColumn.STRING, 23, 100 , 0 , false , false),
		new SchemaColumn("Unit", DataColumn.STRING, 24, 10 , 0 , false , false),
		new SchemaColumn("Score", DataColumn.LONG, 25, 0 , 0 , true , false),
		new SchemaColumn("CommentCount", DataColumn.LONG, 26, 0 , 0 , true , false),
		new SchemaColumn("SaleCount", DataColumn.LONG, 27, 0 , 0 , true , false),
		new SchemaColumn("HitCount", DataColumn.LONG, 28, 0 , 0 , true , false),
		new SchemaColumn("Image0", DataColumn.STRING, 29, 200 , 0 , false , false),
		new SchemaColumn("Image1", DataColumn.STRING, 30, 200 , 0 , false , false),
		new SchemaColumn("Image2", DataColumn.STRING, 31, 200 , 0 , false , false),
		new SchemaColumn("Image3", DataColumn.STRING, 32, 200 , 0 , false , false),
		new SchemaColumn("RelativeGoods", DataColumn.STRING, 33, 100 , 0 , false , false),
		new SchemaColumn("Keyword", DataColumn.STRING, 34, 100 , 0 , false , false),
		new SchemaColumn("TopDate", DataColumn.DATETIME, 35, 0 , 0 , false , false),
		new SchemaColumn("TopFlag", DataColumn.STRING, 36, 2 , 0 , true , false),
		new SchemaColumn("Content", DataColumn.CLOB, 37, 0 , 0 , false , false),
		new SchemaColumn("Summary", DataColumn.STRING, 38, 2000 , 0 , false , false),
		new SchemaColumn("RedirectURL", DataColumn.STRING, 39, 200 , 0 , false , false),
		new SchemaColumn("Attribute", DataColumn.STRING, 40, 100 , 0 , false , false),
		new SchemaColumn("RecommendGoods", DataColumn.STRING, 41, 100 , 0 , false , false),
		new SchemaColumn("StickTime", DataColumn.LONG, 42, 0 , 0 , true , false),
		new SchemaColumn("PublishDate", DataColumn.DATETIME, 43, 0 , 0 , false , false),
		new SchemaColumn("DownlineDate", DataColumn.DATETIME, 44, 0 , 0 , false , false),
		new SchemaColumn("Memo", DataColumn.STRING, 45, 200 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 46, 200 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 47, 200 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 48, 200 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 49, 200 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 50, 200 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 51, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 52, 200 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 53, 0 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 54, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 55, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 56, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 57, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZSGoods";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into BZSGoods values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZSGoods set ID=?,SiteID=?,CatalogID=?,CatalogInnerCode=?,BrandID=?,BranchInnerCode=?,Type=?,SN=?,Name=?,Alias=?,BarCode=?,WorkFlowID=?,Status=?,Factory=?,OrderFlag=?,MarketPrice=?,Price=?,Discount=?,OfferPrice=?,MemberPrice=?,VIPPrice=?,EffectDate=?,Store=?,Standard=?,Unit=?,Score=?,CommentCount=?,SaleCount=?,HitCount=?,Image0=?,Image1=?,Image2=?,Image3=?,RelativeGoods=?,Keyword=?,TopDate=?,TopFlag=?,Content=?,Summary=?,RedirectURL=?,Attribute=?,RecommendGoods=?,StickTime=?,PublishDate=?,DownlineDate=?,Memo=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZSGoods  where ID=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZSGoods  where ID=? and BackupNo=?";

	public BZSGoodsSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[58];
	}

	protected Schema newInstance(){
		return new BZSGoodsSchema();
	}

	protected SchemaSet newSet(){
		return new BZSGoodsSet();
	}

	public BZSGoodsSet query() {
		return query(null, -1, -1);
	}

	public BZSGoodsSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZSGoodsSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZSGoodsSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZSGoodsSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 2){if(v==null){CatalogID = null;}else{CatalogID = new Long(v.toString());}return;}
		if (i == 3){CatalogInnerCode = (String)v;return;}
		if (i == 4){if(v==null){BrandID = null;}else{BrandID = new Long(v.toString());}return;}
		if (i == 5){BranchInnerCode = (String)v;return;}
		if (i == 6){Type = (String)v;return;}
		if (i == 7){SN = (String)v;return;}
		if (i == 8){Name = (String)v;return;}
		if (i == 9){Alias = (String)v;return;}
		if (i == 10){BarCode = (String)v;return;}
		if (i == 11){if(v==null){WorkFlowID = null;}else{WorkFlowID = new Long(v.toString());}return;}
		if (i == 12){Status = (String)v;return;}
		if (i == 13){Factory = (String)v;return;}
		if (i == 14){if(v==null){OrderFlag = null;}else{OrderFlag = new Long(v.toString());}return;}
		if (i == 15){if(v==null){MarketPrice = null;}else{MarketPrice = new Float(v.toString());}return;}
		if (i == 16){if(v==null){Price = null;}else{Price = new Float(v.toString());}return;}
		if (i == 17){if(v==null){Discount = null;}else{Discount = new Float(v.toString());}return;}
		if (i == 18){if(v==null){OfferPrice = null;}else{OfferPrice = new Float(v.toString());}return;}
		if (i == 19){if(v==null){MemberPrice = null;}else{MemberPrice = new Float(v.toString());}return;}
		if (i == 20){if(v==null){VIPPrice = null;}else{VIPPrice = new Float(v.toString());}return;}
		if (i == 21){EffectDate = (Date)v;return;}
		if (i == 22){if(v==null){Store = null;}else{Store = new Long(v.toString());}return;}
		if (i == 23){Standard = (String)v;return;}
		if (i == 24){Unit = (String)v;return;}
		if (i == 25){if(v==null){Score = null;}else{Score = new Long(v.toString());}return;}
		if (i == 26){if(v==null){CommentCount = null;}else{CommentCount = new Long(v.toString());}return;}
		if (i == 27){if(v==null){SaleCount = null;}else{SaleCount = new Long(v.toString());}return;}
		if (i == 28){if(v==null){HitCount = null;}else{HitCount = new Long(v.toString());}return;}
		if (i == 29){Image0 = (String)v;return;}
		if (i == 30){Image1 = (String)v;return;}
		if (i == 31){Image2 = (String)v;return;}
		if (i == 32){Image3 = (String)v;return;}
		if (i == 33){RelativeGoods = (String)v;return;}
		if (i == 34){Keyword = (String)v;return;}
		if (i == 35){TopDate = (Date)v;return;}
		if (i == 36){TopFlag = (String)v;return;}
		if (i == 37){Content = (String)v;return;}
		if (i == 38){Summary = (String)v;return;}
		if (i == 39){RedirectURL = (String)v;return;}
		if (i == 40){Attribute = (String)v;return;}
		if (i == 41){RecommendGoods = (String)v;return;}
		if (i == 42){if(v==null){StickTime = null;}else{StickTime = new Long(v.toString());}return;}
		if (i == 43){PublishDate = (Date)v;return;}
		if (i == 44){DownlineDate = (Date)v;return;}
		if (i == 45){Memo = (String)v;return;}
		if (i == 46){Prop1 = (String)v;return;}
		if (i == 47){Prop2 = (String)v;return;}
		if (i == 48){Prop3 = (String)v;return;}
		if (i == 49){Prop4 = (String)v;return;}
		if (i == 50){AddUser = (String)v;return;}
		if (i == 51){AddTime = (Date)v;return;}
		if (i == 52){ModifyUser = (String)v;return;}
		if (i == 53){ModifyTime = (Date)v;return;}
		if (i == 54){BackupNo = (String)v;return;}
		if (i == 55){BackupOperator = (String)v;return;}
		if (i == 56){BackupTime = (Date)v;return;}
		if (i == 57){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return SiteID;}
		if (i == 2){return CatalogID;}
		if (i == 3){return CatalogInnerCode;}
		if (i == 4){return BrandID;}
		if (i == 5){return BranchInnerCode;}
		if (i == 6){return Type;}
		if (i == 7){return SN;}
		if (i == 8){return Name;}
		if (i == 9){return Alias;}
		if (i == 10){return BarCode;}
		if (i == 11){return WorkFlowID;}
		if (i == 12){return Status;}
		if (i == 13){return Factory;}
		if (i == 14){return OrderFlag;}
		if (i == 15){return MarketPrice;}
		if (i == 16){return Price;}
		if (i == 17){return Discount;}
		if (i == 18){return OfferPrice;}
		if (i == 19){return MemberPrice;}
		if (i == 20){return VIPPrice;}
		if (i == 21){return EffectDate;}
		if (i == 22){return Store;}
		if (i == 23){return Standard;}
		if (i == 24){return Unit;}
		if (i == 25){return Score;}
		if (i == 26){return CommentCount;}
		if (i == 27){return SaleCount;}
		if (i == 28){return HitCount;}
		if (i == 29){return Image0;}
		if (i == 30){return Image1;}
		if (i == 31){return Image2;}
		if (i == 32){return Image3;}
		if (i == 33){return RelativeGoods;}
		if (i == 34){return Keyword;}
		if (i == 35){return TopDate;}
		if (i == 36){return TopFlag;}
		if (i == 37){return Content;}
		if (i == 38){return Summary;}
		if (i == 39){return RedirectURL;}
		if (i == 40){return Attribute;}
		if (i == 41){return RecommendGoods;}
		if (i == 42){return StickTime;}
		if (i == 43){return PublishDate;}
		if (i == 44){return DownlineDate;}
		if (i == 45){return Memo;}
		if (i == 46){return Prop1;}
		if (i == 47){return Prop2;}
		if (i == 48){return Prop3;}
		if (i == 49){return Prop4;}
		if (i == 50){return AddUser;}
		if (i == 51){return AddTime;}
		if (i == 52){return ModifyUser;}
		if (i == 53){return ModifyTime;}
		if (i == 54){return BackupNo;}
		if (i == 55){return BackupOperator;}
		if (i == 56){return BackupTime;}
		if (i == 57){return BackupMemo;}
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
	* ��ȡ�ֶ�CatalogID��ֵ�����ֶε�<br>
	* �ֶ����� :������Ŀ<br>
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
	* �ֶ����� :������Ŀ<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setCatalogID(long catalogID) {
		this.CatalogID = new Long(catalogID);
    }

	/**
	* �����ֶ�CatalogID��ֵ�����ֶε�<br>
	* �ֶ����� :������Ŀ<br>
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
	* ��ȡ�ֶ�CatalogInnerCode��ֵ�����ֶε�<br>
	* �ֶ����� :��Ŀ�ڲ�����<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getCatalogInnerCode() {
		return CatalogInnerCode;
	}

	/**
	* �����ֶ�CatalogInnerCode��ֵ�����ֶε�<br>
	* �ֶ����� :��Ŀ�ڲ�����<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setCatalogInnerCode(String catalogInnerCode) {
		this.CatalogInnerCode = catalogInnerCode;
    }

	/**
	* ��ȡ�ֶ�BrandID��ֵ�����ֶε�<br>
	* �ֶ����� :����Ʒ��<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getBrandID() {
		if(BrandID==null){return 0;}
		return BrandID.longValue();
	}

	/**
	* �����ֶ�BrandID��ֵ�����ֶε�<br>
	* �ֶ����� :����Ʒ��<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setBrandID(long brandID) {
		this.BrandID = new Long(brandID);
    }

	/**
	* �����ֶ�BrandID��ֵ�����ֶε�<br>
	* �ֶ����� :����Ʒ��<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setBrandID(String brandID) {
		if (brandID == null){
			this.BrandID = null;
			return;
		}
		this.BrandID = new Long(brandID);
    }

	/**
	* ��ȡ�ֶ�BranchInnerCode��ֵ�����ֶε�<br>
	* �ֶ����� :������������<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getBranchInnerCode() {
		return BranchInnerCode;
	}

	/**
	* �����ֶ�BranchInnerCode��ֵ�����ֶε�<br>
	* �ֶ����� :������������<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setBranchInnerCode(String branchInnerCode) {
		this.BranchInnerCode = branchInnerCode;
    }

	/**
	* ��ȡ�ֶ�Type��ֵ�����ֶε�<br>
	* �ֶ����� :��Ʒ����<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	* ��ע��Ϣ :<br>
	1 ��ͨ���� 2 ͼƬ���� 3 ��Ƶ���� 4 ��������<br>
	*/
	public String getType() {
		return Type;
	}

	/**
	* �����ֶ�Type��ֵ�����ֶε�<br>
	* �ֶ����� :��Ʒ����<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	* ��ע��Ϣ :<br>
	1 ��ͨ���� 2 ͼƬ���� 3 ��Ƶ���� 4 ��������<br>
	*/
	public void setType(String type) {
		this.Type = type;
    }

	/**
	* ��ȡ�ֶ�SN��ֵ�����ֶε�<br>
	* �ֶ����� :���<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getSN() {
		return SN;
	}

	/**
	* �����ֶ�SN��ֵ�����ֶε�<br>
	* �ֶ����� :���<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setSN(String sN) {
		this.SN = sN;
    }

	/**
	* ��ȡ�ֶ�Name��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getName() {
		return Name;
	}

	/**
	* �����ֶ�Name��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setName(String name) {
		this.Name = name;
    }

	/**
	* ��ȡ�ֶ�Alias��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getAlias() {
		return Alias;
	}

	/**
	* �����ֶ�Alias��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAlias(String alias) {
		this.Alias = alias;
    }

	/**
	* ��ȡ�ֶ�BarCode��ֵ�����ֶε�<br>
	* �ֶ����� :BarCode<br>
	* �������� :varchar(128)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getBarCode() {
		return BarCode;
	}

	/**
	* �����ֶ�BarCode��ֵ�����ֶε�<br>
	* �ֶ����� :BarCode<br>
	* �������� :varchar(128)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setBarCode(String barCode) {
		this.BarCode = barCode;
    }

	/**
	* ��ȡ�ֶ�WorkFlowID��ֵ�����ֶε�<br>
	* �ֶ����� :������ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getWorkFlowID() {
		if(WorkFlowID==null){return 0;}
		return WorkFlowID.longValue();
	}

	/**
	* �����ֶ�WorkFlowID��ֵ�����ֶε�<br>
	* �ֶ����� :������ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setWorkFlowID(long workFlowID) {
		this.WorkFlowID = new Long(workFlowID);
    }

	/**
	* �����ֶ�WorkFlowID��ֵ�����ֶε�<br>
	* �ֶ����� :������ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setWorkFlowID(String workFlowID) {
		if (workFlowID == null){
			this.WorkFlowID = null;
			return;
		}
		this.WorkFlowID = new Long(workFlowID);
    }

	/**
	* ��ȡ�ֶ�Status��ֵ�����ֶε�<br>
	* �ֶ����� :״̬<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getStatus() {
		return Status;
	}

	/**
	* �����ֶ�Status��ֵ�����ֶε�<br>
	* �ֶ����� :״̬<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setStatus(String status) {
		this.Status = status;
    }

	/**
	* ��ȡ�ֶ�Factory��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getFactory() {
		return Factory;
	}

	/**
	* �����ֶ�Factory��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setFactory(String factory) {
		this.Factory = factory;
    }

	/**
	* ��ȡ�ֶ�OrderFlag��ֵ�����ֶε�<br>
	* �ֶ����� :����ʱ��<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getOrderFlag() {
		if(OrderFlag==null){return 0;}
		return OrderFlag.longValue();
	}

	/**
	* �����ֶ�OrderFlag��ֵ�����ֶε�<br>
	* �ֶ����� :����ʱ��<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setOrderFlag(long orderFlag) {
		this.OrderFlag = new Long(orderFlag);
    }

	/**
	* �����ֶ�OrderFlag��ֵ�����ֶε�<br>
	* �ֶ����� :����ʱ��<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setOrderFlag(String orderFlag) {
		if (orderFlag == null){
			this.OrderFlag = null;
			return;
		}
		this.OrderFlag = new Long(orderFlag);
    }

	/**
	* ��ȡ�ֶ�MarketPrice��ֵ�����ֶε�<br>
	* �ֶ����� :�г��۸�<br>
	* �������� :float(12,2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public float getMarketPrice() {
		if(MarketPrice==null){return 0;}
		return MarketPrice.floatValue();
	}

	/**
	* �����ֶ�MarketPrice��ֵ�����ֶε�<br>
	* �ֶ����� :�г��۸�<br>
	* �������� :float(12,2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setMarketPrice(float marketPrice) {
		this.MarketPrice = new Float(marketPrice);
    }

	/**
	* �����ֶ�MarketPrice��ֵ�����ֶε�<br>
	* �ֶ����� :�г��۸�<br>
	* �������� :float(12,2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setMarketPrice(String marketPrice) {
		if (marketPrice == null){
			this.MarketPrice = null;
			return;
		}
		this.MarketPrice = new Float(marketPrice);
    }

	/**
	* ��ȡ�ֶ�Price��ֵ�����ֶε�<br>
	* �ֶ����� :�۸�<br>
	* �������� :float(12,2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public float getPrice() {
		if(Price==null){return 0;}
		return Price.floatValue();
	}

	/**
	* �����ֶ�Price��ֵ�����ֶε�<br>
	* �ֶ����� :�۸�<br>
	* �������� :float(12,2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setPrice(float price) {
		this.Price = new Float(price);
    }

	/**
	* �����ֶ�Price��ֵ�����ֶε�<br>
	* �ֶ����� :�۸�<br>
	* �������� :float(12,2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setPrice(String price) {
		if (price == null){
			this.Price = null;
			return;
		}
		this.Price = new Float(price);
    }

	/**
	* ��ȡ�ֶ�Discount��ֵ�����ֶε�<br>
	* �ֶ����� :�ۿ�<br>
	* �������� :float(12,2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public float getDiscount() {
		if(Discount==null){return 0;}
		return Discount.floatValue();
	}

	/**
	* �����ֶ�Discount��ֵ�����ֶε�<br>
	* �ֶ����� :�ۿ�<br>
	* �������� :float(12,2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setDiscount(float discount) {
		this.Discount = new Float(discount);
    }

	/**
	* �����ֶ�Discount��ֵ�����ֶε�<br>
	* �ֶ����� :�ۿ�<br>
	* �������� :float(12,2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setDiscount(String discount) {
		if (discount == null){
			this.Discount = null;
			return;
		}
		this.Discount = new Float(discount);
    }

	/**
	* ��ȡ�ֶ�OfferPrice��ֵ�����ֶε�<br>
	* �ֶ����� :�Żݼ۸�<br>
	* �������� :float(12,2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public float getOfferPrice() {
		if(OfferPrice==null){return 0;}
		return OfferPrice.floatValue();
	}

	/**
	* �����ֶ�OfferPrice��ֵ�����ֶε�<br>
	* �ֶ����� :�Żݼ۸�<br>
	* �������� :float(12,2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setOfferPrice(float offerPrice) {
		this.OfferPrice = new Float(offerPrice);
    }

	/**
	* �����ֶ�OfferPrice��ֵ�����ֶε�<br>
	* �ֶ����� :�Żݼ۸�<br>
	* �������� :float(12,2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setOfferPrice(String offerPrice) {
		if (offerPrice == null){
			this.OfferPrice = null;
			return;
		}
		this.OfferPrice = new Float(offerPrice);
    }

	/**
	* ��ȡ�ֶ�MemberPrice��ֵ�����ֶε�<br>
	* �ֶ����� :��Ա�۸�<br>
	* �������� :float(12,2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public float getMemberPrice() {
		if(MemberPrice==null){return 0;}
		return MemberPrice.floatValue();
	}

	/**
	* �����ֶ�MemberPrice��ֵ�����ֶε�<br>
	* �ֶ����� :��Ա�۸�<br>
	* �������� :float(12,2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setMemberPrice(float memberPrice) {
		this.MemberPrice = new Float(memberPrice);
    }

	/**
	* �����ֶ�MemberPrice��ֵ�����ֶε�<br>
	* �ֶ����� :��Ա�۸�<br>
	* �������� :float(12,2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setMemberPrice(String memberPrice) {
		if (memberPrice == null){
			this.MemberPrice = null;
			return;
		}
		this.MemberPrice = new Float(memberPrice);
    }

	/**
	* ��ȡ�ֶ�VIPPrice��ֵ�����ֶε�<br>
	* �ֶ����� :VIP�۸�<br>
	* �������� :float(12,2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public float getVIPPrice() {
		if(VIPPrice==null){return 0;}
		return VIPPrice.floatValue();
	}

	/**
	* �����ֶ�VIPPrice��ֵ�����ֶε�<br>
	* �ֶ����� :VIP�۸�<br>
	* �������� :float(12,2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setVIPPrice(float vIPPrice) {
		this.VIPPrice = new Float(vIPPrice);
    }

	/**
	* �����ֶ�VIPPrice��ֵ�����ֶε�<br>
	* �ֶ����� :VIP�۸�<br>
	* �������� :float(12,2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setVIPPrice(String vIPPrice) {
		if (vIPPrice == null){
			this.VIPPrice = null;
			return;
		}
		this.VIPPrice = new Float(vIPPrice);
    }

	/**
	* ��ȡ�ֶ�EffectDate��ֵ�����ֶε�<br>
	* �ֶ����� :�۸���Ч����<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public Date getEffectDate() {
		return EffectDate;
	}

	/**
	* �����ֶ�EffectDate��ֵ�����ֶε�<br>
	* �ֶ����� :�۸���Ч����<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setEffectDate(Date effectDate) {
		this.EffectDate = effectDate;
    }

	/**
	* ��ȡ�ֶ�Store��ֵ�����ֶε�<br>
	* �ֶ����� :���<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public long getStore() {
		if(Store==null){return 0;}
		return Store.longValue();
	}

	/**
	* �����ֶ�Store��ֵ�����ֶε�<br>
	* �ֶ����� :���<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setStore(long store) {
		this.Store = new Long(store);
    }

	/**
	* �����ֶ�Store��ֵ�����ֶε�<br>
	* �ֶ����� :���<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setStore(String store) {
		if (store == null){
			this.Store = null;
			return;
		}
		this.Store = new Long(store);
    }

	/**
	* ��ȡ�ֶ�Standard��ֵ�����ֶε�<br>
	* �ֶ����� :���<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getStandard() {
		return Standard;
	}

	/**
	* �����ֶ�Standard��ֵ�����ֶε�<br>
	* �ֶ����� :���<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setStandard(String standard) {
		this.Standard = standard;
    }

	/**
	* ��ȡ�ֶ�Unit��ֵ�����ֶε�<br>
	* �ֶ����� :��λ<br>
	* �������� :varchar(10)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getUnit() {
		return Unit;
	}

	/**
	* �����ֶ�Unit��ֵ�����ֶε�<br>
	* �ֶ����� :��λ<br>
	* �������� :varchar(10)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setUnit(String unit) {
		this.Unit = unit;
    }

	/**
	* ��ȡ�ֶ�Score��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public long getScore() {
		if(Score==null){return 0;}
		return Score.longValue();
	}

	/**
	* �����ֶ�Score��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setScore(long score) {
		this.Score = new Long(score);
    }

	/**
	* �����ֶ�Score��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setScore(String score) {
		if (score == null){
			this.Score = null;
			return;
		}
		this.Score = new Long(score);
    }

	/**
	* ��ȡ�ֶ�CommentCount��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public long getCommentCount() {
		if(CommentCount==null){return 0;}
		return CommentCount.longValue();
	}

	/**
	* �����ֶ�CommentCount��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setCommentCount(long commentCount) {
		this.CommentCount = new Long(commentCount);
    }

	/**
	* �����ֶ�CommentCount��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setCommentCount(String commentCount) {
		if (commentCount == null){
			this.CommentCount = null;
			return;
		}
		this.CommentCount = new Long(commentCount);
    }

	/**
	* ��ȡ�ֶ�SaleCount��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public long getSaleCount() {
		if(SaleCount==null){return 0;}
		return SaleCount.longValue();
	}

	/**
	* �����ֶ�SaleCount��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setSaleCount(long saleCount) {
		this.SaleCount = new Long(saleCount);
    }

	/**
	* �����ֶ�SaleCount��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setSaleCount(String saleCount) {
		if (saleCount == null){
			this.SaleCount = null;
			return;
		}
		this.SaleCount = new Long(saleCount);
    }

	/**
	* ��ȡ�ֶ�HitCount��ֵ�����ֶε�<br>
	* �ֶ����� :�����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
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
	* �Ƿ���� :true<br>
	*/
	public void setHitCount(long hitCount) {
		this.HitCount = new Long(hitCount);
    }

	/**
	* �����ֶ�HitCount��ֵ�����ֶε�<br>
	* �ֶ����� :�����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setHitCount(String hitCount) {
		if (hitCount == null){
			this.HitCount = null;
			return;
		}
		this.HitCount = new Long(hitCount);
    }

	/**
	* ��ȡ�ֶ�Image0��ֵ�����ֶε�<br>
	* �ֶ����� :����ͼ0<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getImage0() {
		return Image0;
	}

	/**
	* �����ֶ�Image0��ֵ�����ֶε�<br>
	* �ֶ����� :����ͼ0<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setImage0(String image0) {
		this.Image0 = image0;
    }

	/**
	* ��ȡ�ֶ�Image1��ֵ�����ֶε�<br>
	* �ֶ����� :����ͼ1<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getImage1() {
		return Image1;
	}

	/**
	* �����ֶ�Image1��ֵ�����ֶε�<br>
	* �ֶ����� :����ͼ1<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setImage1(String image1) {
		this.Image1 = image1;
    }

	/**
	* ��ȡ�ֶ�Image2��ֵ�����ֶε�<br>
	* �ֶ����� :����ͼ2<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getImage2() {
		return Image2;
	}

	/**
	* �����ֶ�Image2��ֵ�����ֶε�<br>
	* �ֶ����� :����ͼ2<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setImage2(String image2) {
		this.Image2 = image2;
    }

	/**
	* ��ȡ�ֶ�Image3��ֵ�����ֶε�<br>
	* �ֶ����� :����ͼ3<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getImage3() {
		return Image3;
	}

	/**
	* �����ֶ�Image3��ֵ�����ֶε�<br>
	* �ֶ����� :����ͼ3<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setImage3(String image3) {
		this.Image3 = image3;
    }

	/**
	* ��ȡ�ֶ�RelativeGoods��ֵ�����ֶε�<br>
	* �ֶ����� :�����Ʒ<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getRelativeGoods() {
		return RelativeGoods;
	}

	/**
	* �����ֶ�RelativeGoods��ֵ�����ֶε�<br>
	* �ֶ����� :�����Ʒ<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setRelativeGoods(String relativeGoods) {
		this.RelativeGoods = relativeGoods;
    }

	/**
	* ��ȡ�ֶ�Keyword��ֵ�����ֶε�<br>
	* �ֶ����� :�ؼ���<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getKeyword() {
		return Keyword;
	}

	/**
	* �����ֶ�Keyword��ֵ�����ֶε�<br>
	* �ֶ����� :�ؼ���<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setKeyword(String keyword) {
		this.Keyword = keyword;
    }

	/**
	* ��ȡ�ֶ�TopDate��ֵ�����ֶε�<br>
	* �ֶ����� :�ö���Ч����<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public Date getTopDate() {
		return TopDate;
	}

	/**
	* �����ֶ�TopDate��ֵ�����ֶε�<br>
	* �ֶ����� :�ö���Ч����<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setTopDate(Date topDate) {
		this.TopDate = topDate;
    }

	/**
	* ��ȡ�ֶ�TopFlag��ֵ�����ֶε�<br>
	* �ֶ����� :�ö����<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	* ��ע��Ϣ :<br>
	0-���ö� 1-�ö�<br>
	*/
	public String getTopFlag() {
		return TopFlag;
	}

	/**
	* �����ֶ�TopFlag��ֵ�����ֶε�<br>
	* �ֶ����� :�ö����<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	* ��ע��Ϣ :<br>
	0-���ö� 1-�ö�<br>
	*/
	public void setTopFlag(String topFlag) {
		this.TopFlag = topFlag;
    }

	/**
	* ��ȡ�ֶ�Content��ֵ�����ֶε�<br>
	* �ֶ����� :��Ʒ����<br>
	* �������� :mediumtext<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getContent() {
		return Content;
	}

	/**
	* �����ֶ�Content��ֵ�����ֶε�<br>
	* �ֶ����� :��Ʒ����<br>
	* �������� :mediumtext<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setContent(String content) {
		this.Content = content;
    }

	/**
	* ��ȡ�ֶ�Summary��ֵ�����ֶε�<br>
	* �ֶ����� :ժҪ<br>
	* �������� :varchar(2000)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getSummary() {
		return Summary;
	}

	/**
	* �����ֶ�Summary��ֵ�����ֶε�<br>
	* �ֶ����� :ժҪ<br>
	* �������� :varchar(2000)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setSummary(String summary) {
		this.Summary = summary;
    }

	/**
	* ��ȡ�ֶ�RedirectURL��ֵ�����ֶε�<br>
	* �ֶ����� :ת��URL<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getRedirectURL() {
		return RedirectURL;
	}

	/**
	* �����ֶ�RedirectURL��ֵ�����ֶε�<br>
	* �ֶ����� :ת��URL<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setRedirectURL(String redirectURL) {
		this.RedirectURL = redirectURL;
    }

	/**
	* ��ȡ�ֶ�Attribute��ֵ�����ֶε�<br>
	* �ֶ����� :��Ʒ����<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	* ��ע��Ϣ :<br>
	�����������ԣ����Ƽ����ȵ㡢ͼƬ����Ƶ����Ƶ�������ȣ�������չ<br>
	*/
	public String getAttribute() {
		return Attribute;
	}

	/**
	* �����ֶ�Attribute��ֵ�����ֶε�<br>
	* �ֶ����� :��Ʒ����<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	* ��ע��Ϣ :<br>
	�����������ԣ����Ƽ����ȵ㡢ͼƬ����Ƶ����Ƶ�������ȣ�������չ<br>
	*/
	public void setAttribute(String attribute) {
		this.Attribute = attribute;
    }

	/**
	* ��ȡ�ֶ�RecommendGoods��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƽ���Ʒ<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getRecommendGoods() {
		return RecommendGoods;
	}

	/**
	* �����ֶ�RecommendGoods��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƽ���Ʒ<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setRecommendGoods(String recommendGoods) {
		this.RecommendGoods = recommendGoods;
    }

	/**
	* ��ȡ�ֶ�StickTime��ֵ�����ֶε�<br>
	* �ֶ����� :ƽ��ͣ��ʱ��<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public long getStickTime() {
		if(StickTime==null){return 0;}
		return StickTime.longValue();
	}

	/**
	* �����ֶ�StickTime��ֵ�����ֶε�<br>
	* �ֶ����� :ƽ��ͣ��ʱ��<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setStickTime(long stickTime) {
		this.StickTime = new Long(stickTime);
    }

	/**
	* �����ֶ�StickTime��ֵ�����ֶε�<br>
	* �ֶ����� :ƽ��ͣ��ʱ��<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setStickTime(String stickTime) {
		if (stickTime == null){
			this.StickTime = null;
			return;
		}
		this.StickTime = new Long(stickTime);
    }

	/**
	* ��ȡ�ֶ�PublishDate��ֵ�����ֶε�<br>
	* �ֶ����� :����ʱ��<br>
	* �������� :Datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public Date getPublishDate() {
		return PublishDate;
	}

	/**
	* �����ֶ�PublishDate��ֵ�����ֶε�<br>
	* �ֶ����� :����ʱ��<br>
	* �������� :Datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setPublishDate(Date publishDate) {
		this.PublishDate = publishDate;
    }

	/**
	* ��ȡ�ֶ�DownlineDate��ֵ�����ֶε�<br>
	* �ֶ����� :����ʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public Date getDownlineDate() {
		return DownlineDate;
	}

	/**
	* �����ֶ�DownlineDate��ֵ�����ֶε�<br>
	* �ֶ����� :����ʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setDownlineDate(Date downlineDate) {
		this.DownlineDate = downlineDate;
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