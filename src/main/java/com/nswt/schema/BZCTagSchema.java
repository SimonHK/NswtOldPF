package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * �����ƣ���ǩ����<br>
 * ����룺BZCTag<br>
 * ��������ID, BackupNo<br>
 */
public class BZCTagSchema extends Schema {
	private Long ID;

	private String Tag;

	private Long SiteID;

	private String LinkURL;

	private String Type;

	private String RelaTag;

	private Long UsedCount;

	private String TagText;

	private String Prop1;

	private String Prop2;

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
		new SchemaColumn("Tag", DataColumn.STRING, 1, 100 , 0 , true , false),
		new SchemaColumn("SiteID", DataColumn.LONG, 2, 0 , 0 , true , false),
		new SchemaColumn("LinkURL", DataColumn.STRING, 3, 500 , 0 , false , false),
		new SchemaColumn("Type", DataColumn.STRING, 4, 20 , 0 , false , false),
		new SchemaColumn("RelaTag", DataColumn.STRING, 5, 4000 , 0 , false , false),
		new SchemaColumn("UsedCount", DataColumn.LONG, 6, 0 , 0 , false , false),
		new SchemaColumn("TagText", DataColumn.STRING, 7, 400 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 8, 50 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 9, 50 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 10, 200 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 11, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 12, 200 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 13, 0 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 14, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 15, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 16, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 17, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZCTag";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into BZCTag values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZCTag set ID=?,Tag=?,SiteID=?,LinkURL=?,Type=?,RelaTag=?,UsedCount=?,TagText=?,Prop1=?,Prop2=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZCTag  where ID=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZCTag  where ID=? and BackupNo=?";

	public BZCTagSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[18];
	}

	protected Schema newInstance(){
		return new BZCTagSchema();
	}

	protected SchemaSet newSet(){
		return new BZCTagSet();
	}

	public BZCTagSet query() {
		return query(null, -1, -1);
	}

	public BZCTagSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZCTagSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZCTagSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZCTagSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){Tag = (String)v;return;}
		if (i == 2){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 3){LinkURL = (String)v;return;}
		if (i == 4){Type = (String)v;return;}
		if (i == 5){RelaTag = (String)v;return;}
		if (i == 6){if(v==null){UsedCount = null;}else{UsedCount = new Long(v.toString());}return;}
		if (i == 7){TagText = (String)v;return;}
		if (i == 8){Prop1 = (String)v;return;}
		if (i == 9){Prop2 = (String)v;return;}
		if (i == 10){AddUser = (String)v;return;}
		if (i == 11){AddTime = (Date)v;return;}
		if (i == 12){ModifyUser = (String)v;return;}
		if (i == 13){ModifyTime = (Date)v;return;}
		if (i == 14){BackupNo = (String)v;return;}
		if (i == 15){BackupOperator = (String)v;return;}
		if (i == 16){BackupTime = (Date)v;return;}
		if (i == 17){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return Tag;}
		if (i == 2){return SiteID;}
		if (i == 3){return LinkURL;}
		if (i == 4){return Type;}
		if (i == 5){return RelaTag;}
		if (i == 6){return UsedCount;}
		if (i == 7){return TagText;}
		if (i == 8){return Prop1;}
		if (i == 9){return Prop2;}
		if (i == 10){return AddUser;}
		if (i == 11){return AddTime;}
		if (i == 12){return ModifyUser;}
		if (i == 13){return ModifyTime;}
		if (i == 14){return BackupNo;}
		if (i == 15){return BackupOperator;}
		if (i == 16){return BackupTime;}
		if (i == 17){return BackupMemo;}
		return null;
	}

	/**
	* ��ȡ�ֶ�ID��ֵ�����ֶε�<br>
	* �ֶ����� :��ǩID<br>
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
	* �ֶ����� :��ǩID<br>
	* �������� :bigint<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setID(long iD) {
		this.ID = new Long(iD);
    }

	/**
	* �����ֶ�ID��ֵ�����ֶε�<br>
	* �ֶ����� :��ǩID<br>
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
	* ��ȡ�ֶ�Tag��ֵ�����ֶε�<br>
	* �ֶ����� :Tag����<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getTag() {
		return Tag;
	}

	/**
	* �����ֶ�Tag��ֵ�����ֶε�<br>
	* �ֶ����� :Tag����<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
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
	* ��ȡ�ֶ�LinkURL��ֵ�����ֶε�<br>
	* �ֶ����� :Tag����<br>
	* �������� :varchar(500)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getLinkURL() {
		return LinkURL;
	}

	/**
	* �����ֶ�LinkURL��ֵ�����ֶε�<br>
	* �ֶ����� :Tag����<br>
	* �������� :varchar(500)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setLinkURL(String linkURL) {
		this.LinkURL = linkURL;
    }

	/**
	* ��ȡ�ֶ�Type��ֵ�����ֶε�<br>
	* �ֶ����� :Tag���<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getType() {
		return Type;
	}

	/**
	* �����ֶ�Type��ֵ�����ֶε�<br>
	* �ֶ����� :Tag���<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setType(String type) {
		this.Type = type;
    }

	/**
	* ��ȡ�ֶ�RelaTag��ֵ�����ֶε�<br>
	* �ֶ����� :������ǩ<br>
	* �������� :varchar(4000)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getRelaTag() {
		return RelaTag;
	}

	/**
	* �����ֶ�RelaTag��ֵ�����ֶε�<br>
	* �ֶ����� :������ǩ<br>
	* �������� :varchar(4000)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setRelaTag(String relaTag) {
		this.RelaTag = relaTag;
    }

	/**
	* ��ȡ�ֶ�UsedCount��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getUsedCount() {
		if(UsedCount==null){return 0;}
		return UsedCount.longValue();
	}

	/**
	* �����ֶ�UsedCount��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setUsedCount(long usedCount) {
		this.UsedCount = new Long(usedCount);
    }

	/**
	* �����ֶ�UsedCount��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setUsedCount(String usedCount) {
		if (usedCount == null){
			this.UsedCount = null;
			return;
		}
		this.UsedCount = new Long(usedCount);
    }

	/**
	* ��ȡ�ֶ�TagText��ֵ�����ֶε�<br>
	* �ֶ����� :Tag����<br>
	* �������� :varchar(400)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getTagText() {
		return TagText;
	}

	/**
	* �����ֶ�TagText��ֵ�����ֶε�<br>
	* �ֶ����� :Tag����<br>
	* �������� :varchar(400)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setTagText(String tagText) {
		this.TagText = tagText;
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