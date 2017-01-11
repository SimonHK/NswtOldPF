package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * �����ƣ���Ŀ���ñ�<br>
 * ����룺ZCCatalogConfig<br>
 * ��������ID<br>
 */
public class ZCCatalogConfigSchema extends Schema {
	private Long ID;

	private Long SiteID;

	private Long CatalogID;

	private String CatalogInnerCode;

	private String CronExpression;

	private String PlanType;

	private Date StartTime;

	private String IsUsing;

	private Long HotWordType;

	private String AllowStatus;

	private String AfterEditStatus;

	private String Encoding;

	private String ArchiveTime;

	private String AttachDownFlag;

	private String BranchManageFlag;

	private String AllowInnerDeploy;

	private String InnerDeployPassword;

	private String SyncCatalogInsert;

	private String SyncCatalogModify;

	private String SyncArticleModify;

	private Long AfterSyncStatus;

	private Long AfterModifyStatus;

	private String AllowInnerGather;

	private String InnerGatherPassword;

	private String AllowComment;

	private String CommentAnonymous;

	private String CommentVerify;

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
		new SchemaColumn("CatalogID", DataColumn.LONG, 2, 0 , 0 , false , false),
		new SchemaColumn("CatalogInnerCode", DataColumn.STRING, 3, 100 , 0 , false , false),
		new SchemaColumn("CronExpression", DataColumn.STRING, 4, 100 , 0 , false , false),
		new SchemaColumn("PlanType", DataColumn.STRING, 5, 10 , 0 , false , false),
		new SchemaColumn("StartTime", DataColumn.DATETIME, 6, 0 , 0 , false , false),
		new SchemaColumn("IsUsing", DataColumn.STRING, 7, 2 , 0 , true , false),
		new SchemaColumn("HotWordType", DataColumn.LONG, 8, 0 , 0 , false , false),
		new SchemaColumn("AllowStatus", DataColumn.STRING, 9, 50 , 0 , false , false),
		new SchemaColumn("AfterEditStatus", DataColumn.STRING, 10, 50 , 0 , false , false),
		new SchemaColumn("Encoding", DataColumn.STRING, 11, 20 , 0 , false , false),
		new SchemaColumn("ArchiveTime", DataColumn.STRING, 12, 10 , 0 , false , false),
		new SchemaColumn("AttachDownFlag", DataColumn.STRING, 13, 2 , 0 , false , false),
		new SchemaColumn("BranchManageFlag", DataColumn.STRING, 14, 2 , 0 , false , false),
		new SchemaColumn("AllowInnerDeploy", DataColumn.STRING, 15, 2 , 0 , false , false),
		new SchemaColumn("InnerDeployPassword", DataColumn.STRING, 16, 255 , 0 , false , false),
		new SchemaColumn("SyncCatalogInsert", DataColumn.STRING, 17, 2 , 0 , false , false),
		new SchemaColumn("SyncCatalogModify", DataColumn.STRING, 18, 2 , 0 , false , false),
		new SchemaColumn("SyncArticleModify", DataColumn.STRING, 19, 2 , 0 , false , false),
		new SchemaColumn("AfterSyncStatus", DataColumn.LONG, 20, 0 , 0 , false , false),
		new SchemaColumn("AfterModifyStatus", DataColumn.LONG, 21, 0 , 0 , false , false),
		new SchemaColumn("AllowInnerGather", DataColumn.STRING, 22, 2 , 0 , false , false),
		new SchemaColumn("InnerGatherPassword", DataColumn.STRING, 23, 255 , 0 , false , false),
		new SchemaColumn("AllowComment", DataColumn.STRING, 24, 2 , 0 , false , false),
		new SchemaColumn("CommentAnonymous", DataColumn.STRING, 25, 2 , 0 , false , false),
		new SchemaColumn("CommentVerify", DataColumn.STRING, 26, 2 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 27, 50 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 28, 50 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 29, 50 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 30, 50 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 31, 200 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 32, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 33, 200 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 34, 0 , 0 , false , false)
	};

	public static final String _TableCode = "ZCCatalogConfig";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into ZCCatalogConfig values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZCCatalogConfig set ID=?,SiteID=?,CatalogID=?,CatalogInnerCode=?,CronExpression=?,PlanType=?,StartTime=?,IsUsing=?,HotWordType=?,AllowStatus=?,AfterEditStatus=?,Encoding=?,ArchiveTime=?,AttachDownFlag=?,BranchManageFlag=?,AllowInnerDeploy=?,InnerDeployPassword=?,SyncCatalogInsert=?,SyncCatalogModify=?,SyncArticleModify=?,AfterSyncStatus=?,AfterModifyStatus=?,AllowInnerGather=?,InnerGatherPassword=?,AllowComment=?,CommentAnonymous=?,CommentVerify=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";

	protected static final String _DeleteSQL = "delete from ZCCatalogConfig  where ID=?";

	protected static final String _FillAllSQL = "select * from ZCCatalogConfig  where ID=?";

	public ZCCatalogConfigSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[35];
	}

	protected Schema newInstance(){
		return new ZCCatalogConfigSchema();
	}

	protected SchemaSet newSet(){
		return new ZCCatalogConfigSet();
	}

	public ZCCatalogConfigSet query() {
		return query(null, -1, -1);
	}

	public ZCCatalogConfigSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZCCatalogConfigSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZCCatalogConfigSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZCCatalogConfigSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 2){if(v==null){CatalogID = null;}else{CatalogID = new Long(v.toString());}return;}
		if (i == 3){CatalogInnerCode = (String)v;return;}
		if (i == 4){CronExpression = (String)v;return;}
		if (i == 5){PlanType = (String)v;return;}
		if (i == 6){StartTime = (Date)v;return;}
		if (i == 7){IsUsing = (String)v;return;}
		if (i == 8){if(v==null){HotWordType = null;}else{HotWordType = new Long(v.toString());}return;}
		if (i == 9){AllowStatus = (String)v;return;}
		if (i == 10){AfterEditStatus = (String)v;return;}
		if (i == 11){Encoding = (String)v;return;}
		if (i == 12){ArchiveTime = (String)v;return;}
		if (i == 13){AttachDownFlag = (String)v;return;}
		if (i == 14){BranchManageFlag = (String)v;return;}
		if (i == 15){AllowInnerDeploy = (String)v;return;}
		if (i == 16){InnerDeployPassword = (String)v;return;}
		if (i == 17){SyncCatalogInsert = (String)v;return;}
		if (i == 18){SyncCatalogModify = (String)v;return;}
		if (i == 19){SyncArticleModify = (String)v;return;}
		if (i == 20){if(v==null){AfterSyncStatus = null;}else{AfterSyncStatus = new Long(v.toString());}return;}
		if (i == 21){if(v==null){AfterModifyStatus = null;}else{AfterModifyStatus = new Long(v.toString());}return;}
		if (i == 22){AllowInnerGather = (String)v;return;}
		if (i == 23){InnerGatherPassword = (String)v;return;}
		if (i == 24){AllowComment = (String)v;return;}
		if (i == 25){CommentAnonymous = (String)v;return;}
		if (i == 26){CommentVerify = (String)v;return;}
		if (i == 27){Prop1 = (String)v;return;}
		if (i == 28){Prop2 = (String)v;return;}
		if (i == 29){Prop3 = (String)v;return;}
		if (i == 30){Prop4 = (String)v;return;}
		if (i == 31){AddUser = (String)v;return;}
		if (i == 32){AddTime = (Date)v;return;}
		if (i == 33){ModifyUser = (String)v;return;}
		if (i == 34){ModifyTime = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return SiteID;}
		if (i == 2){return CatalogID;}
		if (i == 3){return CatalogInnerCode;}
		if (i == 4){return CronExpression;}
		if (i == 5){return PlanType;}
		if (i == 6){return StartTime;}
		if (i == 7){return IsUsing;}
		if (i == 8){return HotWordType;}
		if (i == 9){return AllowStatus;}
		if (i == 10){return AfterEditStatus;}
		if (i == 11){return Encoding;}
		if (i == 12){return ArchiveTime;}
		if (i == 13){return AttachDownFlag;}
		if (i == 14){return BranchManageFlag;}
		if (i == 15){return AllowInnerDeploy;}
		if (i == 16){return InnerDeployPassword;}
		if (i == 17){return SyncCatalogInsert;}
		if (i == 18){return SyncCatalogModify;}
		if (i == 19){return SyncArticleModify;}
		if (i == 20){return AfterSyncStatus;}
		if (i == 21){return AfterModifyStatus;}
		if (i == 22){return AllowInnerGather;}
		if (i == 23){return InnerGatherPassword;}
		if (i == 24){return AllowComment;}
		if (i == 25){return CommentAnonymous;}
		if (i == 26){return CommentVerify;}
		if (i == 27){return Prop1;}
		if (i == 28){return Prop2;}
		if (i == 29){return Prop3;}
		if (i == 30){return Prop4;}
		if (i == 31){return AddUser;}
		if (i == 32){return AddTime;}
		if (i == 33){return ModifyUser;}
		if (i == 34){return ModifyTime;}
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
	* ��ȡ�ֶ�CatalogID��ֵ�����ֶε�<br>
	* �ֶ����� :��ĿID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
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
	* �Ƿ���� :false<br>
	*/
	public void setCatalogID(long catalogID) {
		this.CatalogID = new Long(catalogID);
    }

	/**
	* �����ֶ�CatalogID��ֵ�����ֶε�<br>
	* �ֶ����� :��ĿID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
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
	* �ֶ����� :��ĿInnerCode<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getCatalogInnerCode() {
		return CatalogInnerCode;
	}

	/**
	* �����ֶ�CatalogInnerCode��ֵ�����ֶε�<br>
	* �ֶ����� :��ĿInnerCode<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCatalogInnerCode(String catalogInnerCode) {
		this.CatalogInnerCode = catalogInnerCode;
    }

	/**
	* ��ȡ�ֶ�CronExpression��ֵ�����ֶε�<br>
	* �ֶ����� :ִ�б��ʽ<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getCronExpression() {
		return CronExpression;
	}

	/**
	* �����ֶ�CronExpression��ֵ�����ֶε�<br>
	* �ֶ����� :ִ�б��ʽ<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCronExpression(String cronExpression) {
		this.CronExpression = cronExpression;
    }

	/**
	* ��ȡ�ֶ�PlanType��ֵ�����ֶε�<br>
	* �ֶ����� :�ƻ�����<br>
	* �������� :varchar(10)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getPlanType() {
		return PlanType;
	}

	/**
	* �����ֶ�PlanType��ֵ�����ֶε�<br>
	* �ֶ����� :�ƻ�����<br>
	* �������� :varchar(10)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setPlanType(String planType) {
		this.PlanType = planType;
    }

	/**
	* ��ȡ�ֶ�StartTime��ֵ�����ֶε�<br>
	* �ֶ����� :��ʼʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public Date getStartTime() {
		return StartTime;
	}

	/**
	* �����ֶ�StartTime��ֵ�����ֶε�<br>
	* �ֶ����� :��ʼʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setStartTime(Date startTime) {
		this.StartTime = startTime;
    }

	/**
	* ��ȡ�ֶ�IsUsing��ֵ�����ֶε�<br>
	* �ֶ����� :��ʱ�����Ƿ�����<br>
	* �������� :char(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getIsUsing() {
		return IsUsing;
	}

	/**
	* �����ֶ�IsUsing��ֵ�����ֶε�<br>
	* �ֶ����� :��ʱ�����Ƿ�����<br>
	* �������� :char(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setIsUsing(String isUsing) {
		this.IsUsing = isUsing;
    }

	/**
	* ��ȡ�ֶ�HotWordType��ֵ�����ֶε�<br>
	* �ֶ����� :�ȵ�ʻ���������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getHotWordType() {
		if(HotWordType==null){return 0;}
		return HotWordType.longValue();
	}

	/**
	* �����ֶ�HotWordType��ֵ�����ֶε�<br>
	* �ֶ����� :�ȵ�ʻ���������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setHotWordType(long hotWordType) {
		this.HotWordType = new Long(hotWordType);
    }

	/**
	* �����ֶ�HotWordType��ֵ�����ֶε�<br>
	* �ֶ����� :�ȵ�ʻ���������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setHotWordType(String hotWordType) {
		if (hotWordType == null){
			this.HotWordType = null;
			return;
		}
		this.HotWordType = new Long(hotWordType);
    }

	/**
	* ��ȡ�ֶ�AllowStatus��ֵ�����ֶε�<br>
	* �ֶ����� :��������״̬<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getAllowStatus() {
		return AllowStatus;
	}

	/**
	* �����ֶ�AllowStatus��ֵ�����ֶε�<br>
	* �ֶ����� :��������״̬<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAllowStatus(String allowStatus) {
		this.AllowStatus = allowStatus;
    }

	/**
	* ��ȡ�ֶ�AfterEditStatus��ֵ�����ֶε�<br>
	* �ֶ����� :�����ĵ��༭��״̬<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getAfterEditStatus() {
		return AfterEditStatus;
	}

	/**
	* �����ֶ�AfterEditStatus��ֵ�����ֶε�<br>
	* �ֶ����� :�����ĵ��༭��״̬<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAfterEditStatus(String afterEditStatus) {
		this.AfterEditStatus = afterEditStatus;
    }

	/**
	* ��ȡ�ֶ�Encoding��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getEncoding() {
		return Encoding;
	}

	/**
	* �����ֶ�Encoding��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setEncoding(String encoding) {
		this.Encoding = encoding;
    }

	/**
	* ��ȡ�ֶ�ArchiveTime��ֵ�����ֶε�<br>
	* �ֶ����� :�鵵ʱ��<br>
	* �������� :varchar(10)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getArchiveTime() {
		return ArchiveTime;
	}

	/**
	* �����ֶ�ArchiveTime��ֵ�����ֶε�<br>
	* �ֶ����� :�鵵ʱ��<br>
	* �������� :varchar(10)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setArchiveTime(String archiveTime) {
		this.ArchiveTime = archiveTime;
    }

	/**
	* ��ȡ�ֶ�AttachDownFlag��ֵ�����ֶε�<br>
	* �ֶ����� :������������<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getAttachDownFlag() {
		return AttachDownFlag;
	}

	/**
	* �����ֶ�AttachDownFlag��ֵ�����ֶε�<br>
	* �ֶ����� :������������<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAttachDownFlag(String attachDownFlag) {
		this.AttachDownFlag = attachDownFlag;
    }

	/**
	* ��ȡ�ֶ�BranchManageFlag��ֵ�����ֶε�<br>
	* �ֶ����� :��������������<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getBranchManageFlag() {
		return BranchManageFlag;
	}

	/**
	* �����ֶ�BranchManageFlag��ֵ�����ֶε�<br>
	* �ֶ����� :��������������<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setBranchManageFlag(String branchManageFlag) {
		this.BranchManageFlag = branchManageFlag;
    }

	/**
	* ��ȡ�ֶ�AllowInnerDeploy��ֵ�����ֶε�<br>
	* �ֶ����� :����ϵͳ�ڷַ�<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getAllowInnerDeploy() {
		return AllowInnerDeploy;
	}

	/**
	* �����ֶ�AllowInnerDeploy��ֵ�����ֶε�<br>
	* �ֶ����� :����ϵͳ�ڷַ�<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAllowInnerDeploy(String allowInnerDeploy) {
		this.AllowInnerDeploy = allowInnerDeploy;
    }

	/**
	* ��ȡ�ֶ�InnerDeployPassword��ֵ�����ֶε�<br>
	* �ֶ����� :ϵͳ�ڷַ���Կ<br>
	* �������� :varchar(255)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getInnerDeployPassword() {
		return InnerDeployPassword;
	}

	/**
	* �����ֶ�InnerDeployPassword��ֵ�����ֶε�<br>
	* �ֶ����� :ϵͳ�ڷַ���Կ<br>
	* �������� :varchar(255)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setInnerDeployPassword(String innerDeployPassword) {
		this.InnerDeployPassword = innerDeployPassword;
    }

	/**
	* ��ȡ�ֶ�SyncCatalogInsert��ֵ�����ֶε�<br>
	* �ֶ����� :�ַ�ʱ������������Ŀ<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getSyncCatalogInsert() {
		return SyncCatalogInsert;
	}

	/**
	* �����ֶ�SyncCatalogInsert��ֵ�����ֶε�<br>
	* �ֶ����� :�ַ�ʱ������������Ŀ<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setSyncCatalogInsert(String syncCatalogInsert) {
		this.SyncCatalogInsert = syncCatalogInsert;
    }

	/**
	* ��ȡ�ֶ�SyncCatalogModify��ֵ�����ֶε�<br>
	* �ֶ����� :�ַ�ʱ�����޸���Ŀ����������Ŀ<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getSyncCatalogModify() {
		return SyncCatalogModify;
	}

	/**
	* �����ֶ�SyncCatalogModify��ֵ�����ֶε�<br>
	* �ֶ����� :�ַ�ʱ�����޸���Ŀ����������Ŀ<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setSyncCatalogModify(String syncCatalogModify) {
		this.SyncCatalogModify = syncCatalogModify;
    }

	/**
	* ��ȡ�ֶ�SyncArticleModify��ֵ�����ֶε�<br>
	* �ֶ����� :����ַ����¸Ķ�<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getSyncArticleModify() {
		return SyncArticleModify;
	}

	/**
	* �����ֶ�SyncArticleModify��ֵ�����ֶε�<br>
	* �ֶ����� :����ַ����¸Ķ�<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setSyncArticleModify(String syncArticleModify) {
		this.SyncArticleModify = syncArticleModify;
    }

	/**
	* ��ȡ�ֶ�AfterSyncStatus��ֵ�����ֶε�<br>
	* �ֶ����� :���ηַ�������״̬<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getAfterSyncStatus() {
		if(AfterSyncStatus==null){return 0;}
		return AfterSyncStatus.longValue();
	}

	/**
	* �����ֶ�AfterSyncStatus��ֵ�����ֶε�<br>
	* �ֶ����� :���ηַ�������״̬<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAfterSyncStatus(long afterSyncStatus) {
		this.AfterSyncStatus = new Long(afterSyncStatus);
    }

	/**
	* �����ֶ�AfterSyncStatus��ֵ�����ֶε�<br>
	* �ֶ����� :���ηַ�������״̬<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAfterSyncStatus(String afterSyncStatus) {
		if (afterSyncStatus == null){
			this.AfterSyncStatus = null;
			return;
		}
		this.AfterSyncStatus = new Long(afterSyncStatus);
    }

	/**
	* ��ȡ�ֶ�AfterModifyStatus��ֵ�����ֶε�<br>
	* �ֶ����� :�Ķ���ַ�����״̬<br>
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
	* �ֶ����� :�Ķ���ַ�����״̬<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAfterModifyStatus(long afterModifyStatus) {
		this.AfterModifyStatus = new Long(afterModifyStatus);
    }

	/**
	* �����ֶ�AfterModifyStatus��ֵ�����ֶε�<br>
	* �ֶ����� :�Ķ���ַ�����״̬<br>
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
	* ��ȡ�ֶ�AllowInnerGather��ֵ�����ֶε�<br>
	* �ֶ����� :����ϵͳ�ڲɼ�<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getAllowInnerGather() {
		return AllowInnerGather;
	}

	/**
	* �����ֶ�AllowInnerGather��ֵ�����ֶε�<br>
	* �ֶ����� :����ϵͳ�ڲɼ�<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAllowInnerGather(String allowInnerGather) {
		this.AllowInnerGather = allowInnerGather;
    }

	/**
	* ��ȡ�ֶ�InnerGatherPassword��ֵ�����ֶε�<br>
	* �ֶ����� :ϵͳ�ڲɼ���Կ<br>
	* �������� :varchar(255)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getInnerGatherPassword() {
		return InnerGatherPassword;
	}

	/**
	* �����ֶ�InnerGatherPassword��ֵ�����ֶε�<br>
	* �ֶ����� :ϵͳ�ڲɼ���Կ<br>
	* �������� :varchar(255)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setInnerGatherPassword(String innerGatherPassword) {
		this.InnerGatherPassword = innerGatherPassword;
    }

	/**
	* ��ȡ�ֶ�AllowComment��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getAllowComment() {
		return AllowComment;
	}

	/**
	* �����ֶ�AllowComment��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAllowComment(String allowComment) {
		this.AllowComment = allowComment;
    }

	/**
	* ��ȡ�ֶ�CommentAnonymous��ֵ�����ֶε�<br>
	* �ֶ����� :��ֹ��������<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getCommentAnonymous() {
		return CommentAnonymous;
	}

	/**
	* �����ֶ�CommentAnonymous��ֵ�����ֶε�<br>
	* �ֶ����� :��ֹ��������<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCommentAnonymous(String commentAnonymous) {
		this.CommentAnonymous = commentAnonymous;
    }

	/**
	* ��ȡ�ֶ�CommentVerify��ֵ�����ֶε�<br>
	* �ֶ����� :���������<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getCommentVerify() {
		return CommentVerify;
	}

	/**
	* �����ֶ�CommentVerify��ֵ�����ֶε�<br>
	* �ֶ����� :���������<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCommentVerify(String commentVerify) {
		this.CommentVerify = commentVerify;
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