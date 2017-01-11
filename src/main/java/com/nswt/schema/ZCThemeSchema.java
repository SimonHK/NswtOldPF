package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * �����ƣ���̳�����<br>
 * ����룺ZCTheme<br>
 * ��������ID<br>
 */
public class ZCThemeSchema extends Schema {
	private Long ID;

	private Long SiteID;

	private Long ForumID;

	private String Subject;

	private String IPAddress;

	private String Type;

	private String Status;

	private String LastPost;

	private String LastPoster;

	private Integer ViewCount;

	private Integer ReplyCount;

	private Long OrderFlag;

	private String VerifyFlag;

	private String TopTheme;

	private String Best;

	private String Bright;

	private String Applydel;

	private Date LastPostTime;

	private Date OrderTime;

	private String prop1;

	private String prop2;

	private String prop3;

	private String prop4;

	private String AddUser;

	private Date AddTime;

	private String ModifyUser;

	private Date ModifyTime;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 0 , 0 , true , true),
		new SchemaColumn("SiteID", DataColumn.LONG, 1, 0 , 0 , false , false),
		new SchemaColumn("ForumID", DataColumn.LONG, 2, 0 , 0 , true , false),
		new SchemaColumn("Subject", DataColumn.STRING, 3, 100 , 0 , false , false),
		new SchemaColumn("IPAddress", DataColumn.STRING, 4, 20 , 0 , false , false),
		new SchemaColumn("Type", DataColumn.STRING, 5, 2 , 0 , false , false),
		new SchemaColumn("Status", DataColumn.STRING, 6, 2 , 0 , false , false),
		new SchemaColumn("LastPost", DataColumn.STRING, 7, 100 , 0 , false , false),
		new SchemaColumn("LastPoster", DataColumn.STRING, 8, 100 , 0 , false , false),
		new SchemaColumn("ViewCount", DataColumn.INTEGER, 9, 0 , 0 , false , false),
		new SchemaColumn("ReplyCount", DataColumn.INTEGER, 10, 0 , 0 , false , false),
		new SchemaColumn("OrderFlag", DataColumn.LONG, 11, 0 , 0 , true , false),
		new SchemaColumn("VerifyFlag", DataColumn.STRING, 12, 2 , 0 , false , false),
		new SchemaColumn("TopTheme", DataColumn.STRING, 13, 2 , 0 , false , false),
		new SchemaColumn("Best", DataColumn.STRING, 14, 2 , 0 , false , false),
		new SchemaColumn("Bright", DataColumn.STRING, 15, 100 , 0 , false , false),
		new SchemaColumn("Applydel", DataColumn.STRING, 16, 2 , 0 , false , false),
		new SchemaColumn("LastPostTime", DataColumn.DATETIME, 17, 0 , 0 , false , false),
		new SchemaColumn("OrderTime", DataColumn.DATETIME, 18, 0 , 0 , false , false),
		new SchemaColumn("prop1", DataColumn.STRING, 19, 50 , 0 , false , false),
		new SchemaColumn("prop2", DataColumn.STRING, 20, 50 , 0 , false , false),
		new SchemaColumn("prop3", DataColumn.STRING, 21, 50 , 0 , false , false),
		new SchemaColumn("prop4", DataColumn.STRING, 22, 50 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 23, 100 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 24, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 25, 100 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 26, 0 , 0 , false , false)
	};

	public static final String _TableCode = "ZCTheme";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into ZCTheme values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZCTheme set ID=?,SiteID=?,ForumID=?,Subject=?,IPAddress=?,Type=?,Status=?,LastPost=?,LastPoster=?,ViewCount=?,ReplyCount=?,OrderFlag=?,VerifyFlag=?,TopTheme=?,Best=?,Bright=?,Applydel=?,LastPostTime=?,OrderTime=?,prop1=?,prop2=?,prop3=?,prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";

	protected static final String _DeleteSQL = "delete from ZCTheme  where ID=?";

	protected static final String _FillAllSQL = "select * from ZCTheme  where ID=?";

	public ZCThemeSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[27];
	}

	protected Schema newInstance(){
		return new ZCThemeSchema();
	}

	protected SchemaSet newSet(){
		return new ZCThemeSet();
	}

	public ZCThemeSet query() {
		return query(null, -1, -1);
	}

	public ZCThemeSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZCThemeSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZCThemeSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZCThemeSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 2){if(v==null){ForumID = null;}else{ForumID = new Long(v.toString());}return;}
		if (i == 3){Subject = (String)v;return;}
		if (i == 4){IPAddress = (String)v;return;}
		if (i == 5){Type = (String)v;return;}
		if (i == 6){Status = (String)v;return;}
		if (i == 7){LastPost = (String)v;return;}
		if (i == 8){LastPoster = (String)v;return;}
		if (i == 9){if(v==null){ViewCount = null;}else{ViewCount = new Integer(v.toString());}return;}
		if (i == 10){if(v==null){ReplyCount = null;}else{ReplyCount = new Integer(v.toString());}return;}
		if (i == 11){if(v==null){OrderFlag = null;}else{OrderFlag = new Long(v.toString());}return;}
		if (i == 12){VerifyFlag = (String)v;return;}
		if (i == 13){TopTheme = (String)v;return;}
		if (i == 14){Best = (String)v;return;}
		if (i == 15){Bright = (String)v;return;}
		if (i == 16){Applydel = (String)v;return;}
		if (i == 17){LastPostTime = (Date)v;return;}
		if (i == 18){OrderTime = (Date)v;return;}
		if (i == 19){prop1 = (String)v;return;}
		if (i == 20){prop2 = (String)v;return;}
		if (i == 21){prop3 = (String)v;return;}
		if (i == 22){prop4 = (String)v;return;}
		if (i == 23){AddUser = (String)v;return;}
		if (i == 24){AddTime = (Date)v;return;}
		if (i == 25){ModifyUser = (String)v;return;}
		if (i == 26){ModifyTime = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return SiteID;}
		if (i == 2){return ForumID;}
		if (i == 3){return Subject;}
		if (i == 4){return IPAddress;}
		if (i == 5){return Type;}
		if (i == 6){return Status;}
		if (i == 7){return LastPost;}
		if (i == 8){return LastPoster;}
		if (i == 9){return ViewCount;}
		if (i == 10){return ReplyCount;}
		if (i == 11){return OrderFlag;}
		if (i == 12){return VerifyFlag;}
		if (i == 13){return TopTheme;}
		if (i == 14){return Best;}
		if (i == 15){return Bright;}
		if (i == 16){return Applydel;}
		if (i == 17){return LastPostTime;}
		if (i == 18){return OrderTime;}
		if (i == 19){return prop1;}
		if (i == 20){return prop2;}
		if (i == 21){return prop3;}
		if (i == 22){return prop4;}
		if (i == 23){return AddUser;}
		if (i == 24){return AddTime;}
		if (i == 25){return ModifyUser;}
		if (i == 26){return ModifyTime;}
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
	* ��ȡ�ֶ�ForumID��ֵ�����ֶε�<br>
	* �ֶ����� :�������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public long getForumID() {
		if(ForumID==null){return 0;}
		return ForumID.longValue();
	}

	/**
	* �����ֶ�ForumID��ֵ�����ֶε�<br>
	* �ֶ����� :�������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setForumID(long forumID) {
		this.ForumID = new Long(forumID);
    }

	/**
	* �����ֶ�ForumID��ֵ�����ֶε�<br>
	* �ֶ����� :�������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setForumID(String forumID) {
		if (forumID == null){
			this.ForumID = null;
			return;
		}
		this.ForumID = new Long(forumID);
    }

	/**
	* ��ȡ�ֶ�Subject��ֵ�����ֶε�<br>
	* �ֶ����� :�������<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getSubject() {
		return Subject;
	}

	/**
	* �����ֶ�Subject��ֵ�����ֶε�<br>
	* �ֶ����� :�������<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setSubject(String subject) {
		this.Subject = subject;
    }

	/**
	* ��ȡ�ֶ�IPAddress��ֵ�����ֶε�<br>
	* �ֶ����� :���ⷢ��IP<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getIPAddress() {
		return IPAddress;
	}

	/**
	* �����ֶ�IPAddress��ֵ�����ֶε�<br>
	* �ֶ����� :���ⷢ��IP<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setIPAddress(String iPAddress) {
		this.IPAddress = iPAddress;
    }

	/**
	* ��ȡ�ֶ�Type��ֵ�����ֶε�<br>
	* �ֶ����� :�������<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getType() {
		return Type;
	}

	/**
	* �����ֶ�Type��ֵ�����ֶε�<br>
	* �ֶ����� :�������<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setType(String type) {
		this.Type = type;
    }

	/**
	* ��ȡ�ֶ�Status��ֵ�����ֶε�<br>
	* �ֶ����� :����״̬<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getStatus() {
		return Status;
	}

	/**
	* �����ֶ�Status��ֵ�����ֶε�<br>
	* �ֶ����� :����״̬<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setStatus(String status) {
		this.Status = status;
    }

	/**
	* ��ȡ�ֶ�LastPost��ֵ�����ֶε�<br>
	* �ֶ����� :������<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getLastPost() {
		return LastPost;
	}

	/**
	* �����ֶ�LastPost��ֵ�����ֶε�<br>
	* �ֶ����� :������<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setLastPost(String lastPost) {
		this.LastPost = lastPost;
    }

	/**
	* ��ȡ�ֶ�LastPoster��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getLastPoster() {
		return LastPoster;
	}

	/**
	* �����ֶ�LastPoster��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setLastPoster(String lastPoster) {
		this.LastPoster = lastPoster;
    }

	/**
	* ��ȡ�ֶ�ViewCount��ֵ�����ֶε�<br>
	* �ֶ����� :�鿴��<br>
	* �������� :int<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public int getViewCount() {
		if(ViewCount==null){return 0;}
		return ViewCount.intValue();
	}

	/**
	* �����ֶ�ViewCount��ֵ�����ֶε�<br>
	* �ֶ����� :�鿴��<br>
	* �������� :int<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setViewCount(int viewCount) {
		this.ViewCount = new Integer(viewCount);
    }

	/**
	* �����ֶ�ViewCount��ֵ�����ֶε�<br>
	* �ֶ����� :�鿴��<br>
	* �������� :int<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setViewCount(String viewCount) {
		if (viewCount == null){
			this.ViewCount = null;
			return;
		}
		this.ViewCount = new Integer(viewCount);
    }

	/**
	* ��ȡ�ֶ�ReplyCount��ֵ�����ֶε�<br>
	* �ֶ����� :�ظ���<br>
	* �������� :int<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public int getReplyCount() {
		if(ReplyCount==null){return 0;}
		return ReplyCount.intValue();
	}

	/**
	* �����ֶ�ReplyCount��ֵ�����ֶε�<br>
	* �ֶ����� :�ظ���<br>
	* �������� :int<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setReplyCount(int replyCount) {
		this.ReplyCount = new Integer(replyCount);
    }

	/**
	* �����ֶ�ReplyCount��ֵ�����ֶε�<br>
	* �ֶ����� :�ظ���<br>
	* �������� :int<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setReplyCount(String replyCount) {
		if (replyCount == null){
			this.ReplyCount = null;
			return;
		}
		this.ReplyCount = new Integer(replyCount);
    }

	/**
	* ��ȡ�ֶ�OrderFlag��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�<br>
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
	* �ֶ����� :�����ֶ�<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setOrderFlag(long orderFlag) {
		this.OrderFlag = new Long(orderFlag);
    }

	/**
	* �����ֶ�OrderFlag��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�<br>
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
	* ��ȡ�ֶ�VerifyFlag��ֵ�����ֶε�<br>
	* �ֶ����� :��˱�־<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getVerifyFlag() {
		return VerifyFlag;
	}

	/**
	* �����ֶ�VerifyFlag��ֵ�����ֶε�<br>
	* �ֶ����� :��˱�־<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setVerifyFlag(String verifyFlag) {
		this.VerifyFlag = verifyFlag;
    }

	/**
	* ��ȡ�ֶ�TopTheme��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ��ö�<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getTopTheme() {
		return TopTheme;
	}

	/**
	* �����ֶ�TopTheme��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ��ö�<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setTopTheme(String topTheme) {
		this.TopTheme = topTheme;
    }

	/**
	* ��ȡ�ֶ�Best��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ񾫻���<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getBest() {
		return Best;
	}

	/**
	* �����ֶ�Best��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ񾫻���<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setBest(String best) {
		this.Best = best;
    }

	/**
	* ��ȡ�ֶ�Bright��ֵ�����ֶε�<br>
	* �ֶ����� :������ɫ<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getBright() {
		return Bright;
	}

	/**
	* �����ֶ�Bright��ֵ�����ֶε�<br>
	* �ֶ����� :������ɫ<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setBright(String bright) {
		this.Bright = bright;
    }

	/**
	* ��ȡ�ֶ�Applydel��ֵ�����ֶε�<br>
	* �ֶ����� :����ɾ��<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getApplydel() {
		return Applydel;
	}

	/**
	* �����ֶ�Applydel��ֵ�����ֶε�<br>
	* �ֶ����� :����ɾ��<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setApplydel(String applydel) {
		this.Applydel = applydel;
    }

	/**
	* ��ȡ�ֶ�LastPostTime��ֵ�����ֶε�<br>
	* �ֶ����� :������ʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public Date getLastPostTime() {
		return LastPostTime;
	}

	/**
	* �����ֶ�LastPostTime��ֵ�����ֶε�<br>
	* �ֶ����� :������ʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setLastPostTime(Date lastPostTime) {
		this.LastPostTime = lastPostTime;
    }

	/**
	* ��ȡ�ֶ�OrderTime��ֵ�����ֶε�<br>
	* �ֶ����� :����ʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public Date getOrderTime() {
		return OrderTime;
	}

	/**
	* �����ֶ�OrderTime��ֵ�����ֶε�<br>
	* �ֶ����� :����ʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setOrderTime(Date orderTime) {
		this.OrderTime = orderTime;
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

}