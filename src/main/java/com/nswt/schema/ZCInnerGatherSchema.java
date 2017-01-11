package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * �����ƣ���վȺ�ɼ����ñ�<br>
 * ����룺ZCInnerGather<br>
 * ��������ID<br>
 */
public class ZCInnerGatherSchema extends Schema {
	private Long ID;

	private Long SiteID;

	private String Name;

	private String CatalogInnerCode;

	private String TargetCatalog;

	private String SyncCatalogInsert;

	private String SyncCatalogModify;

	private String SyncArticleModify;

	private Long AfterInsertStatus;

	private Long AfterModifyStatus;

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
		new SchemaColumn("Name", DataColumn.STRING, 2, 200 , 0 , true , false),
		new SchemaColumn("CatalogInnerCode", DataColumn.STRING, 3, 200 , 0 , true , false),
		new SchemaColumn("TargetCatalog", DataColumn.STRING, 4, 4000 , 0 , true , false),
		new SchemaColumn("SyncCatalogInsert", DataColumn.STRING, 5, 2 , 0 , false , false),
		new SchemaColumn("SyncCatalogModify", DataColumn.STRING, 6, 2 , 0 , false , false),
		new SchemaColumn("SyncArticleModify", DataColumn.STRING, 7, 2 , 0 , false , false),
		new SchemaColumn("AfterInsertStatus", DataColumn.LONG, 8, 0 , 0 , false , false),
		new SchemaColumn("AfterModifyStatus", DataColumn.LONG, 9, 0 , 0 , false , false),
		new SchemaColumn("Status", DataColumn.STRING, 10, 2 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 11, 50 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 12, 50 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 13, 50 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 14, 50 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 15, 200 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 16, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 17, 200 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 18, 0 , 0 , false , false)
	};

	public static final String _TableCode = "ZCInnerGather";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into ZCInnerGather values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZCInnerGather set ID=?,SiteID=?,Name=?,CatalogInnerCode=?,TargetCatalog=?,SyncCatalogInsert=?,SyncCatalogModify=?,SyncArticleModify=?,AfterInsertStatus=?,AfterModifyStatus=?,Status=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";

	protected static final String _DeleteSQL = "delete from ZCInnerGather  where ID=?";

	protected static final String _FillAllSQL = "select * from ZCInnerGather  where ID=?";

	public ZCInnerGatherSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[19];
	}

	protected Schema newInstance(){
		return new ZCInnerGatherSchema();
	}

	protected SchemaSet newSet(){
		return new ZCInnerGatherSet();
	}

	public ZCInnerGatherSet query() {
		return query(null, -1, -1);
	}

	public ZCInnerGatherSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZCInnerGatherSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZCInnerGatherSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZCInnerGatherSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 2){Name = (String)v;return;}
		if (i == 3){CatalogInnerCode = (String)v;return;}
		if (i == 4){TargetCatalog = (String)v;return;}
		if (i == 5){SyncCatalogInsert = (String)v;return;}
		if (i == 6){SyncCatalogModify = (String)v;return;}
		if (i == 7){SyncArticleModify = (String)v;return;}
		if (i == 8){if(v==null){AfterInsertStatus = null;}else{AfterInsertStatus = new Long(v.toString());}return;}
		if (i == 9){if(v==null){AfterModifyStatus = null;}else{AfterModifyStatus = new Long(v.toString());}return;}
		if (i == 10){Status = (String)v;return;}
		if (i == 11){Prop1 = (String)v;return;}
		if (i == 12){Prop2 = (String)v;return;}
		if (i == 13){Prop3 = (String)v;return;}
		if (i == 14){Prop4 = (String)v;return;}
		if (i == 15){AddUser = (String)v;return;}
		if (i == 16){AddTime = (Date)v;return;}
		if (i == 17){ModifyUser = (String)v;return;}
		if (i == 18){ModifyTime = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return SiteID;}
		if (i == 2){return Name;}
		if (i == 3){return CatalogInnerCode;}
		if (i == 4){return TargetCatalog;}
		if (i == 5){return SyncCatalogInsert;}
		if (i == 6){return SyncCatalogModify;}
		if (i == 7){return SyncArticleModify;}
		if (i == 8){return AfterInsertStatus;}
		if (i == 9){return AfterModifyStatus;}
		if (i == 10){return Status;}
		if (i == 11){return Prop1;}
		if (i == 12){return Prop2;}
		if (i == 13){return Prop3;}
		if (i == 14){return Prop4;}
		if (i == 15){return AddUser;}
		if (i == 16){return AddTime;}
		if (i == 17){return ModifyUser;}
		if (i == 18){return ModifyTime;}
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
	* ��ȡ�ֶ�Name��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getName() {
		return Name;
	}

	/**
	* �����ֶ�Name��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setName(String name) {
		this.Name = name;
    }

	/**
	* ��ȡ�ֶ�CatalogInnerCode��ֵ�����ֶε�<br>
	* �ֶ����� :Ŀ����Ŀ����<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getCatalogInnerCode() {
		return CatalogInnerCode;
	}

	/**
	* �����ֶ�CatalogInnerCode��ֵ�����ֶε�<br>
	* �ֶ����� :Ŀ����Ŀ����<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setCatalogInnerCode(String catalogInnerCode) {
		this.CatalogInnerCode = catalogInnerCode;
    }

	/**
	* ��ȡ�ֶ�TargetCatalog��ֵ�����ֶε�<br>
	* �ֶ����� :Դ��Ŀ����<br>
	* �������� :varchar(4000)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getTargetCatalog() {
		return TargetCatalog;
	}

	/**
	* �����ֶ�TargetCatalog��ֵ�����ֶε�<br>
	* �ֶ����� :Դ��Ŀ����<br>
	* �������� :varchar(4000)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setTargetCatalog(String targetCatalog) {
		this.TargetCatalog = targetCatalog;
    }

	/**
	* ��ȡ�ֶ�SyncCatalogInsert��ֵ�����ֶε�<br>
	* �ֶ����� :ͬ����Ŀ������־<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getSyncCatalogInsert() {
		return SyncCatalogInsert;
	}

	/**
	* �����ֶ�SyncCatalogInsert��ֵ�����ֶε�<br>
	* �ֶ����� :ͬ����Ŀ������־<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setSyncCatalogInsert(String syncCatalogInsert) {
		this.SyncCatalogInsert = syncCatalogInsert;
    }

	/**
	* ��ȡ�ֶ�SyncCatalogModify��ֵ�����ֶε�<br>
	* �ֶ����� :ͬ����Ŀ�޸�/ɾ����־<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getSyncCatalogModify() {
		return SyncCatalogModify;
	}

	/**
	* �����ֶ�SyncCatalogModify��ֵ�����ֶε�<br>
	* �ֶ����� :ͬ����Ŀ�޸�/ɾ����־<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setSyncCatalogModify(String syncCatalogModify) {
		this.SyncCatalogModify = syncCatalogModify;
    }

	/**
	* ��ȡ�ֶ�SyncArticleModify��ֵ�����ֶε�<br>
	* �ֶ����� :�����ٴ��޸�ͬ����־<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getSyncArticleModify() {
		return SyncArticleModify;
	}

	/**
	* �����ֶ�SyncArticleModify��ֵ�����ֶε�<br>
	* �ֶ����� :�����ٴ��޸�ͬ����־<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setSyncArticleModify(String syncArticleModify) {
		this.SyncArticleModify = syncArticleModify;
    }

	/**
	* ��ȡ�ֶ�AfterInsertStatus��ֵ�����ֶε�<br>
	* �ֶ����� :���βɼ�������״̬<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getAfterInsertStatus() {
		if(AfterInsertStatus==null){return 0;}
		return AfterInsertStatus.longValue();
	}

	/**
	* �����ֶ�AfterInsertStatus��ֵ�����ֶε�<br>
	* �ֶ����� :���βɼ�������״̬<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAfterInsertStatus(long afterInsertStatus) {
		this.AfterInsertStatus = new Long(afterInsertStatus);
    }

	/**
	* �����ֶ�AfterInsertStatus��ֵ�����ֶε�<br>
	* �ֶ����� :���βɼ�������״̬<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAfterInsertStatus(String afterInsertStatus) {
		if (afterInsertStatus == null){
			this.AfterInsertStatus = null;
			return;
		}
		this.AfterInsertStatus = new Long(afterInsertStatus);
    }

	/**
	* ��ȡ�ֶ�AfterModifyStatus��ֵ�����ֶε�<br>
	* �ֶ����� :�Ķ���ɼ�����״̬<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getAfterModifyStatus() {
		if(AfterModifyStatus==null){return 0;}
		return AfterModifyStatus.longValue();
	}

	/**
	* �����ֶ�AfterModifyStatus��ֵ�����ֶε�<br>
	* �ֶ����� :�Ķ���ɼ�����״̬<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAfterModifyStatus(long afterModifyStatus) {
		this.AfterModifyStatus = new Long(afterModifyStatus);
    }

	/**
	* �����ֶ�AfterModifyStatus��ֵ�����ֶε�<br>
	* �ֶ����� :�Ķ���ɼ�����״̬<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAfterModifyStatus(String afterModifyStatus) {
		if (afterModifyStatus == null){
			this.AfterModifyStatus = null;
			return;
		}
		this.AfterModifyStatus = new Long(afterModifyStatus);
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