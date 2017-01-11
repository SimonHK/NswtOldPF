package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * �����ƣ�ͼƬ������<br>
 * �����룺BZCImage<br>
 * ��������ID, BackupNo<br>
 */
public class BZCImageSchema extends Schema {
	private Long ID;

	private String Name;

	private String OldName;

	private String Tag;

	private Long SiteID;

	private Long CatalogID;

	private String CatalogInnerCode;

	private String BranchInnerCode;

	private String Path;

	private String FileName;

	private String SrcFileName;

	private String Suffix;

	private Long Width;

	private Long Height;

	private Long Count;

	private String Info;

	private String IsOriginal;

	private String FileSize;

	private String System;

	private String LinkURL;

	private String LinkText;

	private Date ProduceTime;

	private String Author;

	private Date PublishDate;

	private Long Integral;

	private Long OrderFlag;

	private Long HitCount;

	private Long StickTime;

	private String SourceURL;

	private Long Status;

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
		new SchemaColumn("Name", DataColumn.STRING, 1, 100 , 0 , true , false),
		new SchemaColumn("OldName", DataColumn.STRING, 2, 100 , 0 , true , false),
		new SchemaColumn("Tag", DataColumn.STRING, 3, 100 , 0 , false , false),
		new SchemaColumn("SiteID", DataColumn.LONG, 4, 0 , 0 , true , false),
		new SchemaColumn("CatalogID", DataColumn.LONG, 5, 0 , 0 , true , false),
		new SchemaColumn("CatalogInnerCode", DataColumn.STRING, 6, 100 , 0 , true , false),
		new SchemaColumn("BranchInnerCode", DataColumn.STRING, 7, 50 , 0 , false , false),
		new SchemaColumn("Path", DataColumn.STRING, 8, 100 , 0 , true , false),
		new SchemaColumn("FileName", DataColumn.STRING, 9, 100 , 0 , true , false),
		new SchemaColumn("SrcFileName", DataColumn.STRING, 10, 100 , 0 , true , false),
		new SchemaColumn("Suffix", DataColumn.STRING, 11, 10 , 0 , true , false),
		new SchemaColumn("Width", DataColumn.LONG, 12, 0 , 0 , true , false),
		new SchemaColumn("Height", DataColumn.LONG, 13, 0 , 0 , true , false),
		new SchemaColumn("Count", DataColumn.LONG, 14, 0 , 0 , true , false),
		new SchemaColumn("Info", DataColumn.STRING, 15, 500 , 0 , false , false),
		new SchemaColumn("IsOriginal", DataColumn.STRING, 16, 1 , 0 , false , false),
		new SchemaColumn("FileSize", DataColumn.STRING, 17, 20 , 0 , false , false),
		new SchemaColumn("System", DataColumn.STRING, 18, 20 , 0 , false , false),
		new SchemaColumn("LinkURL", DataColumn.STRING, 19, 100 , 0 , false , false),
		new SchemaColumn("LinkText", DataColumn.STRING, 20, 100 , 0 , false , false),
		new SchemaColumn("ProduceTime", DataColumn.DATETIME, 21, 0 , 0 , false , false),
		new SchemaColumn("Author", DataColumn.STRING, 22, 50 , 0 , false , false),
		new SchemaColumn("PublishDate", DataColumn.DATETIME, 23, 0 , 0 , false , false),
		new SchemaColumn("Integral", DataColumn.LONG, 24, 0 , 0 , false , false),
		new SchemaColumn("OrderFlag", DataColumn.LONG, 25, 0 , 0 , true , false),
		new SchemaColumn("HitCount", DataColumn.LONG, 26, 0 , 0 , true , false),
		new SchemaColumn("StickTime", DataColumn.LONG, 27, 0 , 0 , true , false),
		new SchemaColumn("SourceURL", DataColumn.STRING, 28, 500 , 0 , false , false),
		new SchemaColumn("Status", DataColumn.LONG, 29, 0 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 30, 50 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 31, 50 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 32, 50 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 33, 50 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 34, 50 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 35, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 36, 50 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 37, 0 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 38, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 39, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 40, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 41, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZCImage";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into BZCImage values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZCImage set ID=?,Name=?,OldName=?,Tag=?,SiteID=?,CatalogID=?,CatalogInnerCode=?,BranchInnerCode=?,Path=?,FileName=?,SrcFileName=?,Suffix=?,Width=?,Height=?,Count=?,Info=?,IsOriginal=?,FileSize=?,System=?,LinkURL=?,LinkText=?,ProduceTime=?,Author=?,PublishDate=?,Integral=?,OrderFlag=?,HitCount=?,StickTime=?,SourceURL=?,Status=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZCImage  where ID=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZCImage  where ID=? and BackupNo=?";

	public BZCImageSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[42];
	}

	protected Schema newInstance(){
		return new BZCImageSchema();
	}

	protected SchemaSet newSet(){
		return new BZCImageSet();
	}

	public BZCImageSet query() {
		return query(null, -1, -1);
	}

	public BZCImageSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZCImageSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZCImageSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZCImageSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){Name = (String)v;return;}
		if (i == 2){OldName = (String)v;return;}
		if (i == 3){Tag = (String)v;return;}
		if (i == 4){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 5){if(v==null){CatalogID = null;}else{CatalogID = new Long(v.toString());}return;}
		if (i == 6){CatalogInnerCode = (String)v;return;}
		if (i == 7){BranchInnerCode = (String)v;return;}
		if (i == 8){Path = (String)v;return;}
		if (i == 9){FileName = (String)v;return;}
		if (i == 10){SrcFileName = (String)v;return;}
		if (i == 11){Suffix = (String)v;return;}
		if (i == 12){if(v==null){Width = null;}else{Width = new Long(v.toString());}return;}
		if (i == 13){if(v==null){Height = null;}else{Height = new Long(v.toString());}return;}
		if (i == 14){if(v==null){Count = null;}else{Count = new Long(v.toString());}return;}
		if (i == 15){Info = (String)v;return;}
		if (i == 16){IsOriginal = (String)v;return;}
		if (i == 17){FileSize = (String)v;return;}
		if (i == 18){System = (String)v;return;}
		if (i == 19){LinkURL = (String)v;return;}
		if (i == 20){LinkText = (String)v;return;}
		if (i == 21){ProduceTime = (Date)v;return;}
		if (i == 22){Author = (String)v;return;}
		if (i == 23){PublishDate = (Date)v;return;}
		if (i == 24){if(v==null){Integral = null;}else{Integral = new Long(v.toString());}return;}
		if (i == 25){if(v==null){OrderFlag = null;}else{OrderFlag = new Long(v.toString());}return;}
		if (i == 26){if(v==null){HitCount = null;}else{HitCount = new Long(v.toString());}return;}
		if (i == 27){if(v==null){StickTime = null;}else{StickTime = new Long(v.toString());}return;}
		if (i == 28){SourceURL = (String)v;return;}
		if (i == 29){if(v==null){Status = null;}else{Status = new Long(v.toString());}return;}
		if (i == 30){Prop1 = (String)v;return;}
		if (i == 31){Prop2 = (String)v;return;}
		if (i == 32){Prop3 = (String)v;return;}
		if (i == 33){Prop4 = (String)v;return;}
		if (i == 34){AddUser = (String)v;return;}
		if (i == 35){AddTime = (Date)v;return;}
		if (i == 36){ModifyUser = (String)v;return;}
		if (i == 37){ModifyTime = (Date)v;return;}
		if (i == 38){BackupNo = (String)v;return;}
		if (i == 39){BackupOperator = (String)v;return;}
		if (i == 40){BackupTime = (Date)v;return;}
		if (i == 41){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return Name;}
		if (i == 2){return OldName;}
		if (i == 3){return Tag;}
		if (i == 4){return SiteID;}
		if (i == 5){return CatalogID;}
		if (i == 6){return CatalogInnerCode;}
		if (i == 7){return BranchInnerCode;}
		if (i == 8){return Path;}
		if (i == 9){return FileName;}
		if (i == 10){return SrcFileName;}
		if (i == 11){return Suffix;}
		if (i == 12){return Width;}
		if (i == 13){return Height;}
		if (i == 14){return Count;}
		if (i == 15){return Info;}
		if (i == 16){return IsOriginal;}
		if (i == 17){return FileSize;}
		if (i == 18){return System;}
		if (i == 19){return LinkURL;}
		if (i == 20){return LinkText;}
		if (i == 21){return ProduceTime;}
		if (i == 22){return Author;}
		if (i == 23){return PublishDate;}
		if (i == 24){return Integral;}
		if (i == 25){return OrderFlag;}
		if (i == 26){return HitCount;}
		if (i == 27){return StickTime;}
		if (i == 28){return SourceURL;}
		if (i == 29){return Status;}
		if (i == 30){return Prop1;}
		if (i == 31){return Prop2;}
		if (i == 32){return Prop3;}
		if (i == 33){return Prop4;}
		if (i == 34){return AddUser;}
		if (i == 35){return AddTime;}
		if (i == 36){return ModifyUser;}
		if (i == 37){return ModifyTime;}
		if (i == 38){return BackupNo;}
		if (i == 39){return BackupOperator;}
		if (i == 40){return BackupTime;}
		if (i == 41){return BackupMemo;}
		return null;
	}

	/**
	* ��ȡ�ֶ�ID��ֵ�����ֶε�<br>
	* �ֶ����� :ͼƬID<br>
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
	* �ֶ����� :ͼƬID<br>
	* �������� :bigint<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setID(long iD) {
		this.ID = new Long(iD);
    }

	/**
	* �����ֶ�ID��ֵ�����ֶε�<br>
	* �ֶ����� :ͼƬID<br>
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
	* �ֶ����� :ͼƬ����<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getName() {
		return Name;
	}

	/**
	* �����ֶ�Name��ֵ�����ֶε�<br>
	* �ֶ����� :ͼƬ����<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setName(String name) {
		this.Name = name;
    }

	/**
	* ��ȡ�ֶ�OldName��ֵ�����ֶε�<br>
	* �ֶ����� :ԭʼ����<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getOldName() {
		return OldName;
	}

	/**
	* �����ֶ�OldName��ֵ�����ֶε�<br>
	* �ֶ����� :ԭʼ����<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setOldName(String oldName) {
		this.OldName = oldName;
    }

	/**
	* ��ȡ�ֶ�Tag��ֵ�����ֶε�<br>
	* �ֶ����� :��ǩ<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getTag() {
		return Tag;
	}

	/**
	* �����ֶ�Tag��ֵ�����ֶε�<br>
	* �ֶ����� :��ǩ<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setTag(String tag) {
		this.Tag = tag;
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
	* ��ȡ�ֶ�BranchInnerCode��ֵ�����ֶε�<br>
	* �ֶ����� :���������ڲ�����<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getBranchInnerCode() {
		return BranchInnerCode;
	}

	/**
	* �����ֶ�BranchInnerCode��ֵ�����ֶε�<br>
	* �ֶ����� :���������ڲ�����<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setBranchInnerCode(String branchInnerCode) {
		this.BranchInnerCode = branchInnerCode;
    }

	/**
	* ��ȡ�ֶ�Path��ֵ�����ֶε�<br>
	* �ֶ����� :·��<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getPath() {
		return Path;
	}

	/**
	* �����ֶ�Path��ֵ�����ֶε�<br>
	* �ֶ����� :·��<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setPath(String path) {
		this.Path = path;
    }

	/**
	* ��ȡ�ֶ�FileName��ֵ�����ֶε�<br>
	* �ֶ����� :�ļ���<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getFileName() {
		return FileName;
	}

	/**
	* �����ֶ�FileName��ֵ�����ֶε�<br>
	* �ֶ����� :�ļ���<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setFileName(String fileName) {
		this.FileName = fileName;
    }

	/**
	* ��ȡ�ֶ�SrcFileName��ֵ�����ֶε�<br>
	* �ֶ����� :ԭʼ�ļ�<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getSrcFileName() {
		return SrcFileName;
	}

	/**
	* �����ֶ�SrcFileName��ֵ�����ֶε�<br>
	* �ֶ����� :ԭʼ�ļ�<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setSrcFileName(String srcFileName) {
		this.SrcFileName = srcFileName;
    }

	/**
	* ��ȡ�ֶ�Suffix��ֵ�����ֶε�<br>
	* �ֶ����� :��׺��<br>
	* �������� :varchar(10)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getSuffix() {
		return Suffix;
	}

	/**
	* �����ֶ�Suffix��ֵ�����ֶε�<br>
	* �ֶ����� :��׺��<br>
	* �������� :varchar(10)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setSuffix(String suffix) {
		this.Suffix = suffix;
    }

	/**
	* ��ȡ�ֶ�Width��ֵ�����ֶε�<br>
	* �ֶ����� :ԭͼ����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	* ��ע��Ϣ :<br>
	��׼����ͼ��СΪ120*120<br>
	*/
	public long getWidth() {
		if(Width==null){return 0;}
		return Width.longValue();
	}

	/**
	* �����ֶ�Width��ֵ�����ֶε�<br>
	* �ֶ����� :ԭͼ����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	* ��ע��Ϣ :<br>
	��׼����ͼ��СΪ120*120<br>
	*/
	public void setWidth(long width) {
		this.Width = new Long(width);
    }

	/**
	* �����ֶ�Width��ֵ�����ֶε�<br>
	* �ֶ����� :ԭͼ����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	* ��ע��Ϣ :<br>
	��׼����ͼ��СΪ120*120<br>
	*/
	public void setWidth(String width) {
		if (width == null){
			this.Width = null;
			return;
		}
		this.Width = new Long(width);
    }

	/**
	* ��ȡ�ֶ�Height��ֵ�����ֶε�<br>
	* �ֶ����� :ԭͼ�߶�<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	* ��ע��Ϣ :<br>
	��׼����ͼ��СΪ120*120<br>
	*/
	public long getHeight() {
		if(Height==null){return 0;}
		return Height.longValue();
	}

	/**
	* �����ֶ�Height��ֵ�����ֶε�<br>
	* �ֶ����� :ԭͼ�߶�<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	* ��ע��Ϣ :<br>
	��׼����ͼ��СΪ120*120<br>
	*/
	public void setHeight(long height) {
		this.Height = new Long(height);
    }

	/**
	* �����ֶ�Height��ֵ�����ֶε�<br>
	* �ֶ����� :ԭͼ�߶�<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	* ��ע��Ϣ :<br>
	��׼����ͼ��СΪ120*120<br>
	*/
	public void setHeight(String height) {
		if (height == null){
			this.Height = null;
			return;
		}
		this.Height = new Long(height);
    }

	/**
	* ��ȡ�ֶ�Count��ֵ�����ֶε�<br>
	* �ֶ����� :����ͼ����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	* ��ע��Ϣ :<br>
	��׼����ͼ��СΪ120*120<br>
	*/
	public long getCount() {
		if(Count==null){return 0;}
		return Count.longValue();
	}

	/**
	* �����ֶ�Count��ֵ�����ֶε�<br>
	* �ֶ����� :����ͼ����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	* ��ע��Ϣ :<br>
	��׼����ͼ��СΪ120*120<br>
	*/
	public void setCount(long count) {
		this.Count = new Long(count);
    }

	/**
	* �����ֶ�Count��ֵ�����ֶε�<br>
	* �ֶ����� :����ͼ����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	* ��ע��Ϣ :<br>
	��׼����ͼ��СΪ120*120<br>
	*/
	public void setCount(String count) {
		if (count == null){
			this.Count = null;
			return;
		}
		this.Count = new Long(count);
    }

	/**
	* ��ȡ�ֶ�Info��ֵ�����ֶε�<br>
	* �ֶ����� :ͼƬ����<br>
	* �������� :varchar(500)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getInfo() {
		return Info;
	}

	/**
	* �����ֶ�Info��ֵ�����ֶε�<br>
	* �ֶ����� :ͼƬ����<br>
	* �������� :varchar(500)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setInfo(String info) {
		this.Info = info;
    }

	/**
	* ��ȡ�ֶ�IsOriginal��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ�ԭ��<br>
	* �������� :char(1)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getIsOriginal() {
		return IsOriginal;
	}

	/**
	* �����ֶ�IsOriginal��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ�ԭ��<br>
	* �������� :char(1)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setIsOriginal(String isOriginal) {
		this.IsOriginal = isOriginal;
    }

	/**
	* ��ȡ�ֶ�FileSize��ֵ�����ֶε�<br>
	* �ֶ����� :ͼƬ��С<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getFileSize() {
		return FileSize;
	}

	/**
	* �����ֶ�FileSize��ֵ�����ֶε�<br>
	* �ֶ����� :ͼƬ��С<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setFileSize(String fileSize) {
		this.FileSize = fileSize;
    }

	/**
	* ��ȡ�ֶ�System��ֵ�����ֶε�<br>
	* �ֶ����� :����ϵͳ<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	* ��ע��Ϣ :<br>
	CMS OA CRM<br>
	*/
	public String getSystem() {
		return System;
	}

	/**
	* �����ֶ�System��ֵ�����ֶε�<br>
	* �ֶ����� :����ϵͳ<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	* ��ע��Ϣ :<br>
	CMS OA CRM<br>
	*/
	public void setSystem(String system) {
		this.System = system;
    }

	/**
	* ��ȡ�ֶ�LinkURL��ֵ�����ֶε�<br>
	* �ֶ����� :ͼƬ����URL<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getLinkURL() {
		return LinkURL;
	}

	/**
	* �����ֶ�LinkURL��ֵ�����ֶε�<br>
	* �ֶ����� :ͼƬ����URL<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setLinkURL(String linkURL) {
		this.LinkURL = linkURL;
    }

	/**
	* ��ȡ�ֶ�LinkText��ֵ�����ֶε�<br>
	* �ֶ����� :ͼƬ�����ı�<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getLinkText() {
		return LinkText;
	}

	/**
	* �����ֶ�LinkText��ֵ�����ֶε�<br>
	* �ֶ����� :ͼƬ�����ı�<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setLinkText(String linkText) {
		this.LinkText = linkText;
    }

	/**
	* ��ȡ�ֶ�ProduceTime��ֵ�����ֶε�<br>
	* �ֶ����� :����ʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public Date getProduceTime() {
		return ProduceTime;
	}

	/**
	* �����ֶ�ProduceTime��ֵ�����ֶε�<br>
	* �ֶ����� :����ʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProduceTime(Date produceTime) {
		this.ProduceTime = produceTime;
    }

	/**
	* ��ȡ�ֶ�Author��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getAuthor() {
		return Author;
	}

	/**
	* �����ֶ�Author��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAuthor(String author) {
		this.Author = author;
    }

	/**
	* ��ȡ�ֶ�PublishDate��ֵ�����ֶε�<br>
	* �ֶ����� :����ʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public Date getPublishDate() {
		return PublishDate;
	}

	/**
	* �����ֶ�PublishDate��ֵ�����ֶε�<br>
	* �ֶ����� :����ʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setPublishDate(Date publishDate) {
		this.PublishDate = publishDate;
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
	* ��ȡ�ֶ�OrderFlag��ֵ�����ֶε�<br>
	* �ֶ����� :�����<br>
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
	* �ֶ����� :�����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setOrderFlag(long orderFlag) {
		this.OrderFlag = new Long(orderFlag);
    }

	/**
	* �����ֶ�OrderFlag��ֵ�����ֶε�<br>
	* �ֶ����� :�����<br>
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
	* ��ȡ�ֶ�SourceURL��ֵ�����ֶε�<br>
	* �ֶ����� :��ԴURL<br>
	* �������� :varchar(500)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	* ��ע��Ϣ :<br>
	�ɼ�ʱ���ô��ֶε�ֵ�������ж��Ƿ��Ѿ�������<br>
	*/
	public String getSourceURL() {
		return SourceURL;
	}

	/**
	* �����ֶ�SourceURL��ֵ�����ֶε�<br>
	* �ֶ����� :��ԴURL<br>
	* �������� :varchar(500)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	* ��ע��Ϣ :<br>
	�ɼ�ʱ���ô��ֶε�ֵ�������ж��Ƿ��Ѿ�������<br>
	*/
	public void setSourceURL(String sourceURL) {
		this.SourceURL = sourceURL;
    }

	/**
	* ��ȡ�ֶ�Status��ֵ�����ֶε�<br>
	* �ֶ����� :״̬<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getStatus() {
		if(Status==null){return 0;}
		return Status.longValue();
	}

	/**
	* �����ֶ�Status��ֵ�����ֶε�<br>
	* �ֶ����� :״̬<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setStatus(long status) {
		this.Status = new Long(status);
    }

	/**
	* �����ֶ�Status��ֵ�����ֶε�<br>
	* �ֶ����� :״̬<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setStatus(String status) {
		if (status == null){
			this.Status = null;
			return;
		}
		this.Status = new Long(status);
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
	* �ֶ����� :������<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getAddUser() {
		return AddUser;
	}

	/**
	* �����ֶ�AddUser��ֵ�����ֶε�<br>
	* �ֶ����� :������<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setAddUser(String addUser) {
		this.AddUser = addUser;
    }

	/**
	* ��ȡ�ֶ�AddTime��ֵ�����ֶε�<br>
	* �ֶ����� :����ʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public Date getAddTime() {
		return AddTime;
	}

	/**
	* �����ֶ�AddTime��ֵ�����ֶε�<br>
	* �ֶ����� :����ʱ��<br>
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