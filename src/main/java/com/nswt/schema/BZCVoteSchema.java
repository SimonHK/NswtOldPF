package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * �����ƣ�ͶƱ����<br>
 * ����룺BZCVote<br>
 * ��������ID, BackupNo<br>
 */
public class BZCVoteSchema extends Schema {
	private Long ID;

	private String Code;

	private Long SiteID;

	private String Title;

	private Long Total;

	private Date StartTime;

	private Date EndTime;

	private String IPLimit;

	private String VerifyFlag;

	private Integer Width;

	private Long RelaCatalogID;

	private Long VoteCatalogID;

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
		new SchemaColumn("Code", DataColumn.STRING, 1, 100 , 0 , false , false),
		new SchemaColumn("SiteID", DataColumn.LONG, 2, 0 , 0 , true , false),
		new SchemaColumn("Title", DataColumn.STRING, 3, 100 , 0 , true , false),
		new SchemaColumn("Total", DataColumn.LONG, 4, 0 , 0 , true , false),
		new SchemaColumn("StartTime", DataColumn.DATETIME, 5, 0 , 0 , true , false),
		new SchemaColumn("EndTime", DataColumn.DATETIME, 6, 0 , 0 , false , false),
		new SchemaColumn("IPLimit", DataColumn.STRING, 7, 1 , 0 , true , false),
		new SchemaColumn("VerifyFlag", DataColumn.STRING, 8, 1 , 0 , true , false),
		new SchemaColumn("Width", DataColumn.INTEGER, 9, 0 , 0 , false , false),
		new SchemaColumn("RelaCatalogID", DataColumn.LONG, 10, 0 , 0 , false , false),
		new SchemaColumn("VoteCatalogID", DataColumn.LONG, 11, 0 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 12, 50 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 13, 50 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 14, 50 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 15, 50 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 16, 200 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 17, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 18, 200 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 19, 0 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 20, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 21, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 22, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 23, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZCVote";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into BZCVote values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZCVote set ID=?,Code=?,SiteID=?,Title=?,Total=?,StartTime=?,EndTime=?,IPLimit=?,VerifyFlag=?,Width=?,RelaCatalogID=?,VoteCatalogID=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZCVote  where ID=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZCVote  where ID=? and BackupNo=?";

	public BZCVoteSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[24];
	}

	protected Schema newInstance(){
		return new BZCVoteSchema();
	}

	protected SchemaSet newSet(){
		return new BZCVoteSet();
	}

	public BZCVoteSet query() {
		return query(null, -1, -1);
	}

	public BZCVoteSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZCVoteSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZCVoteSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZCVoteSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){Code = (String)v;return;}
		if (i == 2){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 3){Title = (String)v;return;}
		if (i == 4){if(v==null){Total = null;}else{Total = new Long(v.toString());}return;}
		if (i == 5){StartTime = (Date)v;return;}
		if (i == 6){EndTime = (Date)v;return;}
		if (i == 7){IPLimit = (String)v;return;}
		if (i == 8){VerifyFlag = (String)v;return;}
		if (i == 9){if(v==null){Width = null;}else{Width = new Integer(v.toString());}return;}
		if (i == 10){if(v==null){RelaCatalogID = null;}else{RelaCatalogID = new Long(v.toString());}return;}
		if (i == 11){if(v==null){VoteCatalogID = null;}else{VoteCatalogID = new Long(v.toString());}return;}
		if (i == 12){Prop1 = (String)v;return;}
		if (i == 13){Prop2 = (String)v;return;}
		if (i == 14){Prop3 = (String)v;return;}
		if (i == 15){Prop4 = (String)v;return;}
		if (i == 16){AddUser = (String)v;return;}
		if (i == 17){AddTime = (Date)v;return;}
		if (i == 18){ModifyUser = (String)v;return;}
		if (i == 19){ModifyTime = (Date)v;return;}
		if (i == 20){BackupNo = (String)v;return;}
		if (i == 21){BackupOperator = (String)v;return;}
		if (i == 22){BackupTime = (Date)v;return;}
		if (i == 23){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return Code;}
		if (i == 2){return SiteID;}
		if (i == 3){return Title;}
		if (i == 4){return Total;}
		if (i == 5){return StartTime;}
		if (i == 6){return EndTime;}
		if (i == 7){return IPLimit;}
		if (i == 8){return VerifyFlag;}
		if (i == 9){return Width;}
		if (i == 10){return RelaCatalogID;}
		if (i == 11){return VoteCatalogID;}
		if (i == 12){return Prop1;}
		if (i == 13){return Prop2;}
		if (i == 14){return Prop3;}
		if (i == 15){return Prop4;}
		if (i == 16){return AddUser;}
		if (i == 17){return AddTime;}
		if (i == 18){return ModifyUser;}
		if (i == 19){return ModifyTime;}
		if (i == 20){return BackupNo;}
		if (i == 21){return BackupOperator;}
		if (i == 22){return BackupTime;}
		if (i == 23){return BackupMemo;}
		return null;
	}

	/**
	* ��ȡ�ֶ�ID��ֵ�����ֶε�<br>
	* �ֶ����� :ͶƱID<br>
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
	* �ֶ����� :ͶƱID<br>
	* �������� :bigint<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setID(long iD) {
		this.ID = new Long(iD);
    }

	/**
	* �����ֶ�ID��ֵ�����ֶε�<br>
	* �ֶ����� :ͶƱID<br>
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
	* ��ȡ�ֶ�Code��ֵ�����ֶε�<br>
	* �ֶ����� :ͶƱ����<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getCode() {
		return Code;
	}

	/**
	* �����ֶ�Code��ֵ�����ֶε�<br>
	* �ֶ����� :ͶƱ����<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCode(String code) {
		this.Code = code;
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
	* ��ȡ�ֶ�Title��ֵ�����ֶε�<br>
	* �ֶ����� :ͶƱ����<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getTitle() {
		return Title;
	}

	/**
	* �����ֶ�Title��ֵ�����ֶε�<br>
	* �ֶ����� :ͶƱ����<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setTitle(String title) {
		this.Title = title;
    }

	/**
	* ��ȡ�ֶ�Total��ֵ�����ֶε�<br>
	* �ֶ����� :ͶƱ�ܴ���<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public long getTotal() {
		if(Total==null){return 0;}
		return Total.longValue();
	}

	/**
	* �����ֶ�Total��ֵ�����ֶε�<br>
	* �ֶ����� :ͶƱ�ܴ���<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setTotal(long total) {
		this.Total = new Long(total);
    }

	/**
	* �����ֶ�Total��ֵ�����ֶε�<br>
	* �ֶ����� :ͶƱ�ܴ���<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setTotal(String total) {
		if (total == null){
			this.Total = null;
			return;
		}
		this.Total = new Long(total);
    }

	/**
	* ��ȡ�ֶ�StartTime��ֵ�����ֶε�<br>
	* �ֶ����� :ͶƱ��ʼʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public Date getStartTime() {
		return StartTime;
	}

	/**
	* �����ֶ�StartTime��ֵ�����ֶε�<br>
	* �ֶ����� :ͶƱ��ʼʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setStartTime(Date startTime) {
		this.StartTime = startTime;
    }

	/**
	* ��ȡ�ֶ�EndTime��ֵ�����ֶε�<br>
	* �ֶ����� :ͶƱ����ʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public Date getEndTime() {
		return EndTime;
	}

	/**
	* �����ֶ�EndTime��ֵ�����ֶε�<br>
	* �ֶ����� :ͶƱ����ʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setEndTime(Date endTime) {
		this.EndTime = endTime;
    }

	/**
	* ��ȡ�ֶ�IPLimit��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ�����IP<br>
	* �������� :char(1)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getIPLimit() {
		return IPLimit;
	}

	/**
	* �����ֶ�IPLimit��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ�����IP<br>
	* �������� :char(1)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setIPLimit(String iPLimit) {
		this.IPLimit = iPLimit;
    }

	/**
	* ��ȡ�ֶ�VerifyFlag��ֵ�����ֶε�<br>
	* �ֶ����� :��֤���־<br>
	* �������� :char(1)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getVerifyFlag() {
		return VerifyFlag;
	}

	/**
	* �����ֶ�VerifyFlag��ֵ�����ֶε�<br>
	* �ֶ����� :��֤���־<br>
	* �������� :char(1)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setVerifyFlag(String verifyFlag) {
		this.VerifyFlag = verifyFlag;
    }

	/**
	* ��ȡ�ֶ�Width��ֵ�����ֶε�<br>
	* �ֶ����� :��ʾ���<br>
	* �������� :smallint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public int getWidth() {
		if(Width==null){return 0;}
		return Width.intValue();
	}

	/**
	* �����ֶ�Width��ֵ�����ֶε�<br>
	* �ֶ����� :��ʾ���<br>
	* �������� :smallint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setWidth(int width) {
		this.Width = new Integer(width);
    }

	/**
	* �����ֶ�Width��ֵ�����ֶε�<br>
	* �ֶ����� :��ʾ���<br>
	* �������� :smallint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setWidth(String width) {
		if (width == null){
			this.Width = null;
			return;
		}
		this.Width = new Integer(width);
    }

	/**
	* ��ȡ�ֶ�RelaCatalogID��ֵ�����ֶε�<br>
	* �ֶ����� :������Ŀ<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getRelaCatalogID() {
		if(RelaCatalogID==null){return 0;}
		return RelaCatalogID.longValue();
	}

	/**
	* �����ֶ�RelaCatalogID��ֵ�����ֶε�<br>
	* �ֶ����� :������Ŀ<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setRelaCatalogID(long relaCatalogID) {
		this.RelaCatalogID = new Long(relaCatalogID);
    }

	/**
	* �����ֶ�RelaCatalogID��ֵ�����ֶε�<br>
	* �ֶ����� :������Ŀ<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setRelaCatalogID(String relaCatalogID) {
		if (relaCatalogID == null){
			this.RelaCatalogID = null;
			return;
		}
		this.RelaCatalogID = new Long(relaCatalogID);
    }

	/**
	* ��ȡ�ֶ�VoteCatalogID��ֵ�����ֶε�<br>
	* �ֶ����� :ͶƱ��Ŀ<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getVoteCatalogID() {
		if(VoteCatalogID==null){return 0;}
		return VoteCatalogID.longValue();
	}

	/**
	* �����ֶ�VoteCatalogID��ֵ�����ֶε�<br>
	* �ֶ����� :ͶƱ��Ŀ<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setVoteCatalogID(long voteCatalogID) {
		this.VoteCatalogID = new Long(voteCatalogID);
    }

	/**
	* �����ֶ�VoteCatalogID��ֵ�����ֶε�<br>
	* �ֶ����� :ͶƱ��Ŀ<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setVoteCatalogID(String voteCatalogID) {
		if (voteCatalogID == null){
			this.VoteCatalogID = null;
			return;
		}
		this.VoteCatalogID = new Long(voteCatalogID);
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