package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * �����ƣ��ȵ�ʻ����<br>
 * ����룺BZCKeyword<br>
 * ��������ID, BackupNo<br>
 */
public class BZCKeywordSchema extends Schema {
	private Long ID;

	private String Keyword;

	private Long SiteId;

	private String KeywordType;

	private String LinkUrl;

	private String LinkTarget;

	private String LinkAlt;

	private Long HintCount;

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
		new SchemaColumn("Keyword", DataColumn.STRING, 1, 200 , 0 , true , false),
		new SchemaColumn("SiteId", DataColumn.LONG, 2, 0 , 0 , true , false),
		new SchemaColumn("KeywordType", DataColumn.STRING, 3, 255 , 0 , false , false),
		new SchemaColumn("LinkUrl", DataColumn.STRING, 4, 200 , 0 , false , false),
		new SchemaColumn("LinkTarget", DataColumn.STRING, 5, 10 , 0 , false , false),
		new SchemaColumn("LinkAlt", DataColumn.STRING, 6, 200 , 0 , false , false),
		new SchemaColumn("HintCount", DataColumn.LONG, 7, 0 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 8, 50 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 9, 50 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 10, 50 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 11, 50 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 12, 200 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 13, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 14, 200 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 15, 0 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 16, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 17, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 18, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 19, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZCKeyword";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into BZCKeyword values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZCKeyword set ID=?,Keyword=?,SiteId=?,KeywordType=?,LinkUrl=?,LinkTarget=?,LinkAlt=?,HintCount=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZCKeyword  where ID=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZCKeyword  where ID=? and BackupNo=?";

	public BZCKeywordSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[20];
	}

	protected Schema newInstance(){
		return new BZCKeywordSchema();
	}

	protected SchemaSet newSet(){
		return new BZCKeywordSet();
	}

	public BZCKeywordSet query() {
		return query(null, -1, -1);
	}

	public BZCKeywordSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZCKeywordSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZCKeywordSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZCKeywordSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){Keyword = (String)v;return;}
		if (i == 2){if(v==null){SiteId = null;}else{SiteId = new Long(v.toString());}return;}
		if (i == 3){KeywordType = (String)v;return;}
		if (i == 4){LinkUrl = (String)v;return;}
		if (i == 5){LinkTarget = (String)v;return;}
		if (i == 6){LinkAlt = (String)v;return;}
		if (i == 7){if(v==null){HintCount = null;}else{HintCount = new Long(v.toString());}return;}
		if (i == 8){Prop1 = (String)v;return;}
		if (i == 9){Prop2 = (String)v;return;}
		if (i == 10){Prop3 = (String)v;return;}
		if (i == 11){Prop4 = (String)v;return;}
		if (i == 12){AddUser = (String)v;return;}
		if (i == 13){AddTime = (Date)v;return;}
		if (i == 14){ModifyUser = (String)v;return;}
		if (i == 15){ModifyTime = (Date)v;return;}
		if (i == 16){BackupNo = (String)v;return;}
		if (i == 17){BackupOperator = (String)v;return;}
		if (i == 18){BackupTime = (Date)v;return;}
		if (i == 19){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return Keyword;}
		if (i == 2){return SiteId;}
		if (i == 3){return KeywordType;}
		if (i == 4){return LinkUrl;}
		if (i == 5){return LinkTarget;}
		if (i == 6){return LinkAlt;}
		if (i == 7){return HintCount;}
		if (i == 8){return Prop1;}
		if (i == 9){return Prop2;}
		if (i == 10){return Prop3;}
		if (i == 11){return Prop4;}
		if (i == 12){return AddUser;}
		if (i == 13){return AddTime;}
		if (i == 14){return ModifyUser;}
		if (i == 15){return ModifyTime;}
		if (i == 16){return BackupNo;}
		if (i == 17){return BackupOperator;}
		if (i == 18){return BackupTime;}
		if (i == 19){return BackupMemo;}
		return null;
	}

	/**
	* ��ȡ�ֶ�ID��ֵ�����ֶε�<br>
	* �ֶ����� :�ؼ���ID<br>
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
	* �ֶ����� :�ؼ���ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setID(long iD) {
		this.ID = new Long(iD);
    }

	/**
	* �����ֶ�ID��ֵ�����ֶε�<br>
	* �ֶ����� :�ؼ���ID<br>
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
	* ��ȡ�ֶ�Keyword��ֵ�����ֶε�<br>
	* �ֶ����� :�ؼ���<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getKeyword() {
		return Keyword;
	}

	/**
	* �����ֶ�Keyword��ֵ�����ֶε�<br>
	* �ֶ����� :�ؼ���<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setKeyword(String keyword) {
		this.Keyword = keyword;
    }

	/**
	* ��ȡ�ֶ�SiteId��ֵ�����ֶε�<br>
	* �ֶ����� :վ��ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public long getSiteId() {
		if(SiteId==null){return 0;}
		return SiteId.longValue();
	}

	/**
	* �����ֶ�SiteId��ֵ�����ֶε�<br>
	* �ֶ����� :վ��ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setSiteId(long siteId) {
		this.SiteId = new Long(siteId);
    }

	/**
	* �����ֶ�SiteId��ֵ�����ֶε�<br>
	* �ֶ����� :վ��ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setSiteId(String siteId) {
		if (siteId == null){
			this.SiteId = null;
			return;
		}
		this.SiteId = new Long(siteId);
    }

	/**
	* ��ȡ�ֶ�KeywordType��ֵ�����ֶε�<br>
	* �ֶ����� :�ؼ������<br>
	* �������� :varchar(255)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getKeywordType() {
		return KeywordType;
	}

	/**
	* �����ֶ�KeywordType��ֵ�����ֶε�<br>
	* �ֶ����� :�ؼ������<br>
	* �������� :varchar(255)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setKeywordType(String keywordType) {
		this.KeywordType = keywordType;
    }

	/**
	* ��ȡ�ֶ�LinkUrl��ֵ�����ֶε�<br>
	* �ֶ����� :���ӵ�ַ<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getLinkUrl() {
		return LinkUrl;
	}

	/**
	* �����ֶ�LinkUrl��ֵ�����ֶε�<br>
	* �ֶ����� :���ӵ�ַ<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setLinkUrl(String linkUrl) {
		this.LinkUrl = linkUrl;
    }

	/**
	* ��ȡ�ֶ�LinkTarget��ֵ�����ֶε�<br>
	* �ֶ����� :����target<br>
	* �������� :varchar(10)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getLinkTarget() {
		return LinkTarget;
	}

	/**
	* �����ֶ�LinkTarget��ֵ�����ֶε�<br>
	* �ֶ����� :����target<br>
	* �������� :varchar(10)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setLinkTarget(String linkTarget) {
		this.LinkTarget = linkTarget;
    }

	/**
	* ��ȡ�ֶ�LinkAlt��ֵ�����ֶε�<br>
	* �ֶ����� :����Alt����<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getLinkAlt() {
		return LinkAlt;
	}

	/**
	* �����ֶ�LinkAlt��ֵ�����ֶε�<br>
	* �ֶ����� :����Alt����<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setLinkAlt(String linkAlt) {
		this.LinkAlt = linkAlt;
    }

	/**
	* ��ȡ�ֶ�HintCount��ֵ�����ֶε�<br>
	* �ֶ����� :�������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getHintCount() {
		if(HintCount==null){return 0;}
		return HintCount.longValue();
	}

	/**
	* �����ֶ�HintCount��ֵ�����ֶε�<br>
	* �ֶ����� :�������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setHintCount(long hintCount) {
		this.HintCount = new Long(hintCount);
    }

	/**
	* �����ֶ�HintCount��ֵ�����ֶε�<br>
	* �ֶ����� :�������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setHintCount(String hintCount) {
		if (hintCount == null){
			this.HintCount = null;
			return;
		}
		this.HintCount = new Long(hintCount);
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