package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * �����ƣ���ֽ����<br>
 * ����룺BZCPaper<br>
 * ��������ID, BackupNo<br>
 */
public class BZCPaperSchema extends Schema {
	private Long ID;

	private Long SiteID;

	private String Name;

	private String Alias;

	private String CoverImage;

	private String Period;

	private Long OpenFlag;

	private Long Total;

	private String CurrentYear;

	private String CurrentPeriodNum;

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
		new SchemaColumn("Alias", DataColumn.STRING, 3, 100 , 0 , true , false),
		new SchemaColumn("CoverImage", DataColumn.STRING, 4, 100 , 0 , false , false),
		new SchemaColumn("Period", DataColumn.STRING, 5, 10 , 0 , false , false),
		new SchemaColumn("OpenFlag", DataColumn.LONG, 6, 0 , 0 , false , false),
		new SchemaColumn("Total", DataColumn.LONG, 7, 0 , 0 , false , false),
		new SchemaColumn("CurrentYear", DataColumn.STRING, 8, 50 , 0 , false , false),
		new SchemaColumn("CurrentPeriodNum", DataColumn.STRING, 9, 50 , 0 , false , false),
		new SchemaColumn("Memo", DataColumn.STRING, 10, 1000 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 11, 50 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 12, 50 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 13, 50 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 14, 50 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 15, 200 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 16, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 17, 200 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 18, 0 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 19, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 20, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 21, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 22, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZCPaper";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into BZCPaper values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZCPaper set ID=?,SiteID=?,Name=?,Alias=?,CoverImage=?,Period=?,OpenFlag=?,Total=?,CurrentYear=?,CurrentPeriodNum=?,Memo=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZCPaper  where ID=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZCPaper  where ID=? and BackupNo=?";

	public BZCPaperSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[23];
	}

	protected Schema newInstance(){
		return new BZCPaperSchema();
	}

	protected SchemaSet newSet(){
		return new BZCPaperSet();
	}

	public BZCPaperSet query() {
		return query(null, -1, -1);
	}

	public BZCPaperSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZCPaperSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZCPaperSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZCPaperSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 2){Name = (String)v;return;}
		if (i == 3){Alias = (String)v;return;}
		if (i == 4){CoverImage = (String)v;return;}
		if (i == 5){Period = (String)v;return;}
		if (i == 6){if(v==null){OpenFlag = null;}else{OpenFlag = new Long(v.toString());}return;}
		if (i == 7){if(v==null){Total = null;}else{Total = new Long(v.toString());}return;}
		if (i == 8){CurrentYear = (String)v;return;}
		if (i == 9){CurrentPeriodNum = (String)v;return;}
		if (i == 10){Memo = (String)v;return;}
		if (i == 11){Prop1 = (String)v;return;}
		if (i == 12){Prop2 = (String)v;return;}
		if (i == 13){Prop3 = (String)v;return;}
		if (i == 14){Prop4 = (String)v;return;}
		if (i == 15){AddUser = (String)v;return;}
		if (i == 16){AddTime = (Date)v;return;}
		if (i == 17){ModifyUser = (String)v;return;}
		if (i == 18){ModifyTime = (Date)v;return;}
		if (i == 19){BackupNo = (String)v;return;}
		if (i == 20){BackupOperator = (String)v;return;}
		if (i == 21){BackupTime = (Date)v;return;}
		if (i == 22){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return SiteID;}
		if (i == 2){return Name;}
		if (i == 3){return Alias;}
		if (i == 4){return CoverImage;}
		if (i == 5){return Period;}
		if (i == 6){return OpenFlag;}
		if (i == 7){return Total;}
		if (i == 8){return CurrentYear;}
		if (i == 9){return CurrentPeriodNum;}
		if (i == 10){return Memo;}
		if (i == 11){return Prop1;}
		if (i == 12){return Prop2;}
		if (i == 13){return Prop3;}
		if (i == 14){return Prop4;}
		if (i == 15){return AddUser;}
		if (i == 16){return AddTime;}
		if (i == 17){return ModifyUser;}
		if (i == 18){return ModifyTime;}
		if (i == 19){return BackupNo;}
		if (i == 20){return BackupOperator;}
		if (i == 21){return BackupTime;}
		if (i == 22){return BackupMemo;}
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
	* �ֶ����� :����վ��<br>
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
	* �ֶ����� :����վ��<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setSiteID(long siteID) {
		this.SiteID = new Long(siteID);
    }

	/**
	* �����ֶ�SiteID��ֵ�����ֶε�<br>
	* �ֶ����� :����վ��<br>
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
	* �ֶ����� :Ӣ�Ĵ���<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getAlias() {
		return Alias;
	}

	/**
	* �����ֶ�Alias��ֵ�����ֶε�<br>
	* �ֶ����� :Ӣ�Ĵ���<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setAlias(String alias) {
		this.Alias = alias;
    }

	/**
	* ��ȡ�ֶ�CoverImage��ֵ�����ֶε�<br>
	* �ֶ����� :����ͼƬ<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getCoverImage() {
		return CoverImage;
	}

	/**
	* �����ֶ�CoverImage��ֵ�����ֶε�<br>
	* �ֶ����� :����ͼƬ<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCoverImage(String coverImage) {
		this.CoverImage = coverImage;
    }

	/**
	* ��ȡ�ֶ�Period��ֵ�����ֶε�<br>
	* �ֶ����� :�������<br>
	* �������� :varchar(10)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	* ��ע��Ϣ :<br>
	Y-�� S-���� M-�� H-���� W-�� D-��<br>
	*/
	public String getPeriod() {
		return Period;
	}

	/**
	* �����ֶ�Period��ֵ�����ֶε�<br>
	* �ֶ����� :�������<br>
	* �������� :varchar(10)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	* ��ע��Ϣ :<br>
	Y-�� S-���� M-�� H-���� W-�� D-��<br>
	*/
	public void setPeriod(String period) {
		this.Period = period;
    }

	/**
	* ��ȡ�ֶ�OpenFlag��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ񿪷�<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getOpenFlag() {
		if(OpenFlag==null){return 0;}
		return OpenFlag.longValue();
	}

	/**
	* �����ֶ�OpenFlag��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ񿪷�<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setOpenFlag(long openFlag) {
		this.OpenFlag = new Long(openFlag);
    }

	/**
	* �����ֶ�OpenFlag��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ񿪷�<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setOpenFlag(String openFlag) {
		if (openFlag == null){
			this.OpenFlag = null;
			return;
		}
		this.OpenFlag = new Long(openFlag);
    }

	/**
	* ��ȡ�ֶ�Total��ֵ�����ֶε�<br>
	* �ֶ����� :������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getTotal() {
		if(Total==null){return 0;}
		return Total.longValue();
	}

	/**
	* �����ֶ�Total��ֵ�����ֶε�<br>
	* �ֶ����� :������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setTotal(long total) {
		this.Total = new Long(total);
    }

	/**
	* �����ֶ�Total��ֵ�����ֶε�<br>
	* �ֶ����� :������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setTotal(String total) {
		if (total == null){
			this.Total = null;
			return;
		}
		this.Total = new Long(total);
    }

	/**
	* ��ȡ�ֶ�CurrentYear��ֵ�����ֶε�<br>
	* �ֶ����� :��ǰ���<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getCurrentYear() {
		return CurrentYear;
	}

	/**
	* �����ֶ�CurrentYear��ֵ�����ֶε�<br>
	* �ֶ����� :��ǰ���<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCurrentYear(String currentYear) {
		this.CurrentYear = currentYear;
    }

	/**
	* ��ȡ�ֶ�CurrentPeriodNum��ֵ�����ֶε�<br>
	* �ֶ����� :��ǰ����<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getCurrentPeriodNum() {
		return CurrentPeriodNum;
	}

	/**
	* �����ֶ�CurrentPeriodNum��ֵ�����ֶε�<br>
	* �ֶ����� :��ǰ����<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCurrentPeriodNum(String currentPeriodNum) {
		this.CurrentPeriodNum = currentPeriodNum;
    }

	/**
	* ��ȡ�ֶ�Memo��ֵ�����ֶε�<br>
	* �ֶ����� :��ע<br>
	* �������� :varchar(1000)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getMemo() {
		return Memo;
	}

	/**
	* �����ֶ�Memo��ֵ�����ֶε�<br>
	* �ֶ����� :��ע<br>
	* �������� :varchar(1000)<br>
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