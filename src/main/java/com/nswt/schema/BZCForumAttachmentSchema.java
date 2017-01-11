package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * �����ƣ���������<br>
 * ����룺BZCForumAttachment<br>
 * ��������ID, BackupNo<br>
 */
public class BZCForumAttachmentSchema extends Schema {
	private Long ID;

	private Long PostID;

	private Long SiteID;

	private String Name;

	private String Path;

	private String Type;

	private String Suffix;

	private Long AttSize;

	private Long DownCount;

	private String Note;

	private String prop1;

	private String prop2;

	private String prop3;

	private String prop4;

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
		new SchemaColumn("PostID", DataColumn.LONG, 1, 0 , 0 , true , false),
		new SchemaColumn("SiteID", DataColumn.LONG, 2, 0 , 0 , false , false),
		new SchemaColumn("Name", DataColumn.STRING, 3, 200 , 0 , true , false),
		new SchemaColumn("Path", DataColumn.STRING, 4, 200 , 0 , true , false),
		new SchemaColumn("Type", DataColumn.STRING, 5, 100 , 0 , true , false),
		new SchemaColumn("Suffix", DataColumn.STRING, 6, 50 , 0 , false , false),
		new SchemaColumn("AttSize", DataColumn.LONG, 7, 0 , 0 , false , false),
		new SchemaColumn("DownCount", DataColumn.LONG, 8, 0 , 0 , false , false),
		new SchemaColumn("Note", DataColumn.STRING, 9, 500 , 0 , false , false),
		new SchemaColumn("prop1", DataColumn.STRING, 10, 50 , 0 , false , false),
		new SchemaColumn("prop2", DataColumn.STRING, 11, 50 , 0 , false , false),
		new SchemaColumn("prop3", DataColumn.STRING, 12, 50 , 0 , false , false),
		new SchemaColumn("prop4", DataColumn.STRING, 13, 50 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 14, 100 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 15, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 16, 100 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 17, 0 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 18, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 19, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 20, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 21, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZCForumAttachment";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into BZCForumAttachment values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZCForumAttachment set ID=?,PostID=?,SiteID=?,Name=?,Path=?,Type=?,Suffix=?,AttSize=?,DownCount=?,Note=?,prop1=?,prop2=?,prop3=?,prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZCForumAttachment  where ID=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZCForumAttachment  where ID=? and BackupNo=?";

	public BZCForumAttachmentSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[22];
	}

	protected Schema newInstance(){
		return new BZCForumAttachmentSchema();
	}

	protected SchemaSet newSet(){
		return new BZCForumAttachmentSet();
	}

	public BZCForumAttachmentSet query() {
		return query(null, -1, -1);
	}

	public BZCForumAttachmentSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZCForumAttachmentSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZCForumAttachmentSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZCForumAttachmentSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){PostID = null;}else{PostID = new Long(v.toString());}return;}
		if (i == 2){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 3){Name = (String)v;return;}
		if (i == 4){Path = (String)v;return;}
		if (i == 5){Type = (String)v;return;}
		if (i == 6){Suffix = (String)v;return;}
		if (i == 7){if(v==null){AttSize = null;}else{AttSize = new Long(v.toString());}return;}
		if (i == 8){if(v==null){DownCount = null;}else{DownCount = new Long(v.toString());}return;}
		if (i == 9){Note = (String)v;return;}
		if (i == 10){prop1 = (String)v;return;}
		if (i == 11){prop2 = (String)v;return;}
		if (i == 12){prop3 = (String)v;return;}
		if (i == 13){prop4 = (String)v;return;}
		if (i == 14){AddUser = (String)v;return;}
		if (i == 15){AddTime = (Date)v;return;}
		if (i == 16){ModifyUser = (String)v;return;}
		if (i == 17){ModifyTime = (Date)v;return;}
		if (i == 18){BackupNo = (String)v;return;}
		if (i == 19){BackupOperator = (String)v;return;}
		if (i == 20){BackupTime = (Date)v;return;}
		if (i == 21){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return PostID;}
		if (i == 2){return SiteID;}
		if (i == 3){return Name;}
		if (i == 4){return Path;}
		if (i == 5){return Type;}
		if (i == 6){return Suffix;}
		if (i == 7){return AttSize;}
		if (i == 8){return DownCount;}
		if (i == 9){return Note;}
		if (i == 10){return prop1;}
		if (i == 11){return prop2;}
		if (i == 12){return prop3;}
		if (i == 13){return prop4;}
		if (i == 14){return AddUser;}
		if (i == 15){return AddTime;}
		if (i == 16){return ModifyUser;}
		if (i == 17){return ModifyTime;}
		if (i == 18){return BackupNo;}
		if (i == 19){return BackupOperator;}
		if (i == 20){return BackupTime;}
		if (i == 21){return BackupMemo;}
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
	* ��ȡ�ֶ�PostID��ֵ�����ֶε�<br>
	* �ֶ����� :����ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public long getPostID() {
		if(PostID==null){return 0;}
		return PostID.longValue();
	}

	/**
	* �����ֶ�PostID��ֵ�����ֶε�<br>
	* �ֶ����� :����ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setPostID(long postID) {
		this.PostID = new Long(postID);
    }

	/**
	* �����ֶ�PostID��ֵ�����ֶε�<br>
	* �ֶ����� :����ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setPostID(String postID) {
		if (postID == null){
			this.PostID = null;
			return;
		}
		this.PostID = new Long(postID);
    }

	/**
	* ��ȡ�ֶ�SiteID��ֵ�����ֶε�<br>
	* �ֶ����� :վ��ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
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
	* �Ƿ���� :false<br>
	*/
	public void setSiteID(long siteID) {
		this.SiteID = new Long(siteID);
    }

	/**
	* �����ֶ�SiteID��ֵ�����ֶε�<br>
	* �ֶ����� :վ��ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
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
	* ��ȡ�ֶ�Path��ֵ�����ֶε�<br>
	* �ֶ����� :������ַ<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getPath() {
		return Path;
	}

	/**
	* �����ֶ�Path��ֵ�����ֶε�<br>
	* �ֶ����� :������ַ<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setPath(String path) {
		this.Path = path;
    }

	/**
	* ��ȡ�ֶ�Type��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getType() {
		return Type;
	}

	/**
	* �����ֶ�Type��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setType(String type) {
		this.Type = type;
    }

	/**
	* ��ȡ�ֶ�Suffix��ֵ�����ֶε�<br>
	* �ֶ����� :�ļ�����<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getSuffix() {
		return Suffix;
	}

	/**
	* �����ֶ�Suffix��ֵ�����ֶε�<br>
	* �ֶ����� :�ļ�����<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setSuffix(String suffix) {
		this.Suffix = suffix;
    }

	/**
	* ��ȡ�ֶ�AttSize��ֵ�����ֶε�<br>
	* �ֶ����� :��С<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getAttSize() {
		if(AttSize==null){return 0;}
		return AttSize.longValue();
	}

	/**
	* �����ֶ�AttSize��ֵ�����ֶε�<br>
	* �ֶ����� :��С<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAttSize(long attSize) {
		this.AttSize = new Long(attSize);
    }

	/**
	* �����ֶ�AttSize��ֵ�����ֶε�<br>
	* �ֶ����� :��С<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAttSize(String attSize) {
		if (attSize == null){
			this.AttSize = null;
			return;
		}
		this.AttSize = new Long(attSize);
    }

	/**
	* ��ȡ�ֶ�DownCount��ֵ�����ֶε�<br>
	* �ֶ����� :���ش���<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getDownCount() {
		if(DownCount==null){return 0;}
		return DownCount.longValue();
	}

	/**
	* �����ֶ�DownCount��ֵ�����ֶε�<br>
	* �ֶ����� :���ش���<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setDownCount(long downCount) {
		this.DownCount = new Long(downCount);
    }

	/**
	* �����ֶ�DownCount��ֵ�����ֶε�<br>
	* �ֶ����� :���ش���<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setDownCount(String downCount) {
		if (downCount == null){
			this.DownCount = null;
			return;
		}
		this.DownCount = new Long(downCount);
    }

	/**
	* ��ȡ�ֶ�Note��ֵ�����ֶε�<br>
	* �ֶ����� :ע��<br>
	* �������� :varchar(500)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getNote() {
		return Note;
	}

	/**
	* �����ֶ�Note��ֵ�����ֶε�<br>
	* �ֶ����� :ע��<br>
	* �������� :varchar(500)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setNote(String note) {
		this.Note = note;
    }

	/**
	* ��ȡ�ֶ�prop1��ֵ�����ֶε�<br>
	* �ֶ����� :Ԥ���ֶ�1<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp1() {
		return prop1;
	}

	/**
	* �����ֶ�prop1��ֵ�����ֶε�<br>
	* �ֶ����� :Ԥ���ֶ�1<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp1(String prop1) {
		this.prop1 = prop1;
    }

	/**
	* ��ȡ�ֶ�prop2��ֵ�����ֶε�<br>
	* �ֶ����� :Ԥ���ֶ�2<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp2() {
		return prop2;
	}

	/**
	* �����ֶ�prop2��ֵ�����ֶε�<br>
	* �ֶ����� :Ԥ���ֶ�2<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp2(String prop2) {
		this.prop2 = prop2;
    }

	/**
	* ��ȡ�ֶ�prop3��ֵ�����ֶε�<br>
	* �ֶ����� :Ԥ���ֶ�3<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp3() {
		return prop3;
	}

	/**
	* �����ֶ�prop3��ֵ�����ֶε�<br>
	* �ֶ����� :Ԥ���ֶ�3<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp3(String prop3) {
		this.prop3 = prop3;
    }

	/**
	* ��ȡ�ֶ�prop4��ֵ�����ֶε�<br>
	* �ֶ����� :Ԥ���ֶ�4<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp4() {
		return prop4;
	}

	/**
	* �����ֶ�prop4��ֵ�����ֶε�<br>
	* �ֶ����� :Ԥ���ֶ�4<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp4(String prop4) {
		this.prop4 = prop4;
    }

	/**
	* ��ȡ�ֶ�AddUser��ֵ�����ֶε�<br>
	* �ֶ����� :�����<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getAddUser() {
		return AddUser;
	}

	/**
	* �����ֶ�AddUser��ֵ�����ֶε�<br>
	* �ֶ����� :�����<br>
	* �������� :varchar(100)<br>
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
	* �ֶ����� :�޸���<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getModifyUser() {
		return ModifyUser;
	}

	/**
	* �����ֶ�ModifyUser��ֵ�����ֶε�<br>
	* �ֶ����� :�޸���<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setModifyUser(String modifyUser) {
		this.ModifyUser = modifyUser;
    }

	/**
	* ��ȡ�ֶ�ModifyTime��ֵ�����ֶε�<br>
	* �ֶ����� :�޸�ʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public Date getModifyTime() {
		return ModifyTime;
	}

	/**
	* �����ֶ�ModifyTime��ֵ�����ֶε�<br>
	* �ֶ����� :�޸�ʱ��<br>
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