package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * �����ƣ���ֽ��������<br>
 * ����룺BZCPaperIssue<br>
 * ��������ID, BackupNo<br>
 */
public class BZCPaperIssueSchema extends Schema {
	private Long ID;

	private Long PaperID;

	private String Year;

	private String PeriodNum;

	private String CoverImage;

	private String CoverTemplate;

	private Date PublishDate;

	private Long Status;

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
		new SchemaColumn("PaperID", DataColumn.LONG, 1, 0 , 0 , true , false),
		new SchemaColumn("Year", DataColumn.STRING, 2, 50 , 0 , false , false),
		new SchemaColumn("PeriodNum", DataColumn.STRING, 3, 50 , 0 , true , false),
		new SchemaColumn("CoverImage", DataColumn.STRING, 4, 100 , 0 , false , false),
		new SchemaColumn("CoverTemplate", DataColumn.STRING, 5, 100 , 0 , false , false),
		new SchemaColumn("PublishDate", DataColumn.DATETIME, 6, 0 , 0 , true , false),
		new SchemaColumn("Status", DataColumn.LONG, 7, 0 , 0 , false , false),
		new SchemaColumn("Memo", DataColumn.STRING, 8, 1000 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 9, 50 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 10, 50 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 11, 50 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 12, 50 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 13, 200 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 14, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 15, 200 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 16, 0 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 17, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 18, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 19, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 20, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZCPaperIssue";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into BZCPaperIssue values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZCPaperIssue set ID=?,PaperID=?,Year=?,PeriodNum=?,CoverImage=?,CoverTemplate=?,PublishDate=?,Status=?,Memo=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZCPaperIssue  where ID=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZCPaperIssue  where ID=? and BackupNo=?";

	public BZCPaperIssueSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[21];
	}

	protected Schema newInstance(){
		return new BZCPaperIssueSchema();
	}

	protected SchemaSet newSet(){
		return new BZCPaperIssueSet();
	}

	public BZCPaperIssueSet query() {
		return query(null, -1, -1);
	}

	public BZCPaperIssueSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZCPaperIssueSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZCPaperIssueSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZCPaperIssueSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){PaperID = null;}else{PaperID = new Long(v.toString());}return;}
		if (i == 2){Year = (String)v;return;}
		if (i == 3){PeriodNum = (String)v;return;}
		if (i == 4){CoverImage = (String)v;return;}
		if (i == 5){CoverTemplate = (String)v;return;}
		if (i == 6){PublishDate = (Date)v;return;}
		if (i == 7){if(v==null){Status = null;}else{Status = new Long(v.toString());}return;}
		if (i == 8){Memo = (String)v;return;}
		if (i == 9){Prop1 = (String)v;return;}
		if (i == 10){Prop2 = (String)v;return;}
		if (i == 11){Prop3 = (String)v;return;}
		if (i == 12){Prop4 = (String)v;return;}
		if (i == 13){AddUser = (String)v;return;}
		if (i == 14){AddTime = (Date)v;return;}
		if (i == 15){ModifyUser = (String)v;return;}
		if (i == 16){ModifyTime = (Date)v;return;}
		if (i == 17){BackupNo = (String)v;return;}
		if (i == 18){BackupOperator = (String)v;return;}
		if (i == 19){BackupTime = (Date)v;return;}
		if (i == 20){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return PaperID;}
		if (i == 2){return Year;}
		if (i == 3){return PeriodNum;}
		if (i == 4){return CoverImage;}
		if (i == 5){return CoverTemplate;}
		if (i == 6){return PublishDate;}
		if (i == 7){return Status;}
		if (i == 8){return Memo;}
		if (i == 9){return Prop1;}
		if (i == 10){return Prop2;}
		if (i == 11){return Prop3;}
		if (i == 12){return Prop4;}
		if (i == 13){return AddUser;}
		if (i == 14){return AddTime;}
		if (i == 15){return ModifyUser;}
		if (i == 16){return ModifyTime;}
		if (i == 17){return BackupNo;}
		if (i == 18){return BackupOperator;}
		if (i == 19){return BackupTime;}
		if (i == 20){return BackupMemo;}
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
	* ��ȡ�ֶ�PaperID��ֵ�����ֶε�<br>
	* �ֶ����� :�ڿ�ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public long getPaperID() {
		if(PaperID==null){return 0;}
		return PaperID.longValue();
	}

	/**
	* �����ֶ�PaperID��ֵ�����ֶε�<br>
	* �ֶ����� :�ڿ�ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setPaperID(long paperID) {
		this.PaperID = new Long(paperID);
    }

	/**
	* �����ֶ�PaperID��ֵ�����ֶε�<br>
	* �ֶ����� :�ڿ�ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setPaperID(String paperID) {
		if (paperID == null){
			this.PaperID = null;
			return;
		}
		this.PaperID = new Long(paperID);
    }

	/**
	* ��ȡ�ֶ�Year��ֵ�����ֶε�<br>
	* �ֶ����� :��<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getYear() {
		return Year;
	}

	/**
	* �����ֶ�Year��ֵ�����ֶε�<br>
	* �ֶ����� :��<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setYear(String year) {
		this.Year = year;
    }

	/**
	* ��ȡ�ֶ�PeriodNum��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getPeriodNum() {
		return PeriodNum;
	}

	/**
	* �����ֶ�PeriodNum��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setPeriodNum(String periodNum) {
		this.PeriodNum = periodNum;
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
	* ��ȡ�ֶ�CoverTemplate��ֵ�����ֶε�<br>
	* �ֶ����� :����ģ��<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getCoverTemplate() {
		return CoverTemplate;
	}

	/**
	* �����ֶ�CoverTemplate��ֵ�����ֶε�<br>
	* �ֶ����� :����ģ��<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCoverTemplate(String coverTemplate) {
		this.CoverTemplate = coverTemplate;
    }

	/**
	* ��ȡ�ֶ�PublishDate��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :date<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public Date getPublishDate() {
		return PublishDate;
	}

	/**
	* �����ֶ�PublishDate��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :date<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setPublishDate(Date publishDate) {
		this.PublishDate = publishDate;
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