package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * 表名称：企业承诺备份<br>
 * 表代码：BZCCommEnterprise<br>
 * 表主键：ID, BackupNo<br>
 */
public class BZCCommEnterpriseSchema extends Schema {
	private Long ID;

	private String FullName;

	private String ShortName;

	private String EnterpriseUrl;

	private String Place;

	private String LegalPerson;

	private String EnterpriseType;

	private Date CommDate;

	private String CommWord;

	private String CommWordUrl;

	private Long SiteID;

	private Long VoteCount;

	private String Prop1;

	private String prop2;

	private String Prop3;

	private String Prop4;

	private String Prop5;

	private String Prop6;

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
		new SchemaColumn("FullName", DataColumn.STRING, 1, 100 , 0 , true , false),
		new SchemaColumn("ShortName", DataColumn.STRING, 2, 50 , 0 , false , false),
		new SchemaColumn("EnterpriseUrl", DataColumn.STRING, 3, 500 , 0 , false , false),
		new SchemaColumn("Place", DataColumn.STRING, 4, 50 , 0 , false , false),
		new SchemaColumn("LegalPerson", DataColumn.STRING, 5, 50 , 0 , false , false),
		new SchemaColumn("EnterpriseType", DataColumn.STRING, 6, 50 , 0 , false , false),
		new SchemaColumn("CommDate", DataColumn.DATETIME, 7, 0 , 0 , true , false),
		new SchemaColumn("CommWord", DataColumn.STRING, 8, 100 , 0 , false , false),
		new SchemaColumn("CommWordUrl", DataColumn.STRING, 9, 500 , 0 , false , false),
		new SchemaColumn("SiteID", DataColumn.LONG, 10, 0 , 0 , true , false),
		new SchemaColumn("VoteCount", DataColumn.LONG, 11, 0 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 12, 100 , 0 , false , false),
		new SchemaColumn("prop2", DataColumn.STRING, 13, 100 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 14, 100 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 15, 100 , 0 , false , false),
		new SchemaColumn("Prop5", DataColumn.STRING, 16, 100 , 0 , false , false),
		new SchemaColumn("Prop6", DataColumn.STRING, 17, 100 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 18, 50 , 0 , false , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 19, 0 , 0 , false , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 20, 50 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 21, 0 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 22, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 23, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 24, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 25, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZCCommEnterprise";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into BZCCommEnterprise values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZCCommEnterprise set ID=?,FullName=?,ShortName=?,EnterpriseUrl=?,Place=?,LegalPerson=?,EnterpriseType=?,CommDate=?,CommWord=?,CommWordUrl=?,SiteID=?,VoteCount=?,Prop1=?,prop2=?,Prop3=?,Prop4=?,Prop5=?,Prop6=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZCCommEnterprise  where ID=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZCCommEnterprise  where ID=? and BackupNo=?";

	public BZCCommEnterpriseSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[26];
	}

	protected Schema newInstance(){
		return new BZCCommEnterpriseSchema();
	}

	protected SchemaSet newSet(){
		return new BZCCommEnterpriseSet();
	}

	public BZCCommEnterpriseSet query() {
		return query(null, -1, -1);
	}

	public BZCCommEnterpriseSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZCCommEnterpriseSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZCCommEnterpriseSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZCCommEnterpriseSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){FullName = (String)v;return;}
		if (i == 2){ShortName = (String)v;return;}
		if (i == 3){EnterpriseUrl = (String)v;return;}
		if (i == 4){Place = (String)v;return;}
		if (i == 5){LegalPerson = (String)v;return;}
		if (i == 6){EnterpriseType = (String)v;return;}
		if (i == 7){CommDate = (Date)v;return;}
		if (i == 8){CommWord = (String)v;return;}
		if (i == 9){CommWordUrl = (String)v;return;}
		if (i == 10){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 11){if(v==null){VoteCount = null;}else{VoteCount = new Long(v.toString());}return;}
		if (i == 12){Prop1 = (String)v;return;}
		if (i == 13){prop2 = (String)v;return;}
		if (i == 14){Prop3 = (String)v;return;}
		if (i == 15){Prop4 = (String)v;return;}
		if (i == 16){Prop5 = (String)v;return;}
		if (i == 17){Prop6 = (String)v;return;}
		if (i == 18){AddUser = (String)v;return;}
		if (i == 19){AddTime = (Date)v;return;}
		if (i == 20){ModifyUser = (String)v;return;}
		if (i == 21){ModifyTime = (Date)v;return;}
		if (i == 22){BackupNo = (String)v;return;}
		if (i == 23){BackupOperator = (String)v;return;}
		if (i == 24){BackupTime = (Date)v;return;}
		if (i == 25){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return FullName;}
		if (i == 2){return ShortName;}
		if (i == 3){return EnterpriseUrl;}
		if (i == 4){return Place;}
		if (i == 5){return LegalPerson;}
		if (i == 6){return EnterpriseType;}
		if (i == 7){return CommDate;}
		if (i == 8){return CommWord;}
		if (i == 9){return CommWordUrl;}
		if (i == 10){return SiteID;}
		if (i == 11){return VoteCount;}
		if (i == 12){return Prop1;}
		if (i == 13){return prop2;}
		if (i == 14){return Prop3;}
		if (i == 15){return Prop4;}
		if (i == 16){return Prop5;}
		if (i == 17){return Prop6;}
		if (i == 18){return AddUser;}
		if (i == 19){return AddTime;}
		if (i == 20){return ModifyUser;}
		if (i == 21){return ModifyTime;}
		if (i == 22){return BackupNo;}
		if (i == 23){return BackupOperator;}
		if (i == 24){return BackupTime;}
		if (i == 25){return BackupMemo;}
		return null;
	}

	/**
	* 获取字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public long getID() {
		if(ID==null){return 0;}
		return ID.longValue();
	}

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(long iD) {
		this.ID = new Long(iD);
    }

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(String iD) {
		if (iD == null){
			this.ID = null;
			return;
		}
		this.ID = new Long(iD);
    }

	/**
	* 获取字段FullName的值，该字段的<br>
	* 字段名称 :企业全称<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getFullName() {
		return FullName;
	}

	/**
	* 设置字段FullName的值，该字段的<br>
	* 字段名称 :企业全称<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setFullName(String fullName) {
		this.FullName = fullName;
    }

	/**
	* 获取字段ShortName的值，该字段的<br>
	* 字段名称 :企业简称<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getShortName() {
		return ShortName;
	}

	/**
	* 设置字段ShortName的值，该字段的<br>
	* 字段名称 :企业简称<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setShortName(String shortName) {
		this.ShortName = shortName;
    }

	/**
	* 获取字段EnterpriseUrl的值，该字段的<br>
	* 字段名称 :企业网址<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getEnterpriseUrl() {
		return EnterpriseUrl;
	}

	/**
	* 设置字段EnterpriseUrl的值，该字段的<br>
	* 字段名称 :企业网址<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setEnterpriseUrl(String enterpriseUrl) {
		this.EnterpriseUrl = enterpriseUrl;
    }

	/**
	* 获取字段Place的值，该字段的<br>
	* 字段名称 :企业所在省市<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPlace() {
		return Place;
	}

	/**
	* 设置字段Place的值，该字段的<br>
	* 字段名称 :企业所在省市<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPlace(String place) {
		this.Place = place;
    }

	/**
	* 获取字段LegalPerson的值，该字段的<br>
	* 字段名称 :企业法人<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getLegalPerson() {
		return LegalPerson;
	}

	/**
	* 设置字段LegalPerson的值，该字段的<br>
	* 字段名称 :企业法人<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setLegalPerson(String legalPerson) {
		this.LegalPerson = legalPerson;
    }

	/**
	* 获取字段EnterpriseType的值，该字段的<br>
	* 字段名称 :所属行业<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getEnterpriseType() {
		return EnterpriseType;
	}

	/**
	* 设置字段EnterpriseType的值，该字段的<br>
	* 字段名称 :所属行业<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setEnterpriseType(String enterpriseType) {
		this.EnterpriseType = enterpriseType;
    }

	/**
	* 获取字段CommDate的值，该字段的<br>
	* 字段名称 :承诺时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public Date getCommDate() {
		return CommDate;
	}

	/**
	* 设置字段CommDate的值，该字段的<br>
	* 字段名称 :承诺时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setCommDate(Date commDate) {
		this.CommDate = commDate;
    }

	/**
	* 获取字段CommWord的值，该字段的<br>
	* 字段名称 :承诺书<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCommWord() {
		return CommWord;
	}

	/**
	* 设置字段CommWord的值，该字段的<br>
	* 字段名称 :承诺书<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCommWord(String commWord) {
		this.CommWord = commWord;
    }

	/**
	* 获取字段CommWordUrl的值，该字段的<br>
	* 字段名称 :承诺书地址<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCommWordUrl() {
		return CommWordUrl;
	}

	/**
	* 设置字段CommWordUrl的值，该字段的<br>
	* 字段名称 :承诺书地址<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCommWordUrl(String commWordUrl) {
		this.CommWordUrl = commWordUrl;
    }

	/**
	* 获取字段SiteID的值，该字段的<br>
	* 字段名称 :站点ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getSiteID() {
		if(SiteID==null){return 0;}
		return SiteID.longValue();
	}

	/**
	* 设置字段SiteID的值，该字段的<br>
	* 字段名称 :站点ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setSiteID(long siteID) {
		this.SiteID = new Long(siteID);
    }

	/**
	* 设置字段SiteID的值，该字段的<br>
	* 字段名称 :站点ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setSiteID(String siteID) {
		if (siteID == null){
			this.SiteID = null;
			return;
		}
		this.SiteID = new Long(siteID);
    }

	/**
	* 获取字段VoteCount的值，该字段的<br>
	* 字段名称 :票数<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getVoteCount() {
		if(VoteCount==null){return 0;}
		return VoteCount.longValue();
	}

	/**
	* 设置字段VoteCount的值，该字段的<br>
	* 字段名称 :票数<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setVoteCount(long voteCount) {
		this.VoteCount = new Long(voteCount);
    }

	/**
	* 设置字段VoteCount的值，该字段的<br>
	* 字段名称 :票数<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setVoteCount(String voteCount) {
		if (voteCount == null){
			this.VoteCount = null;
			return;
		}
		this.VoteCount = new Long(voteCount);
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段prop2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return prop2;
	}

	/**
	* 设置字段prop2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.prop2 = prop2;
    }

	/**
	* 获取字段Prop3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp3() {
		return Prop3;
	}

	/**
	* 设置字段Prop3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp3(String prop3) {
		this.Prop3 = prop3;
    }

	/**
	* 获取字段Prop4的值，该字段的<br>
	* 字段名称 :备用字段4<br>
	* 数据类型 :varchar(0100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp4() {
		return Prop4;
	}

	/**
	* 设置字段Prop4的值，该字段的<br>
	* 字段名称 :备用字段4<br>
	* 数据类型 :varchar(0100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp4(String prop4) {
		this.Prop4 = prop4;
    }

	/**
	* 获取字段Prop5的值，该字段的<br>
	* 字段名称 :备用字段5<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp5() {
		return Prop5;
	}

	/**
	* 设置字段Prop5的值，该字段的<br>
	* 字段名称 :备用字段5<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp5(String prop5) {
		this.Prop5 = prop5;
    }

	/**
	* 获取字段Prop6的值，该字段的<br>
	* 字段名称 :备用字段6<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp6() {
		return Prop6;
	}

	/**
	* 设置字段Prop6的值，该字段的<br>
	* 字段名称 :备用字段6<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp6(String prop6) {
		this.Prop6 = prop6;
    }

	/**
	* 获取字段AddUser的值，该字段的<br>
	* 字段名称 :添加人<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAddUser() {
		return AddUser;
	}

	/**
	* 设置字段AddUser的值，该字段的<br>
	* 字段名称 :添加人<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAddUser(String addUser) {
		this.AddUser = addUser;
    }

	/**
	* 获取字段AddTime的值，该字段的<br>
	* 字段名称 :添加的时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getAddTime() {
		return AddTime;
	}

	/**
	* 设置字段AddTime的值，该字段的<br>
	* 字段名称 :添加的时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAddTime(Date addTime) {
		this.AddTime = addTime;
    }

	/**
	* 获取字段ModifyUser的值，该字段的<br>
	* 字段名称 :修改人<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getModifyUser() {
		return ModifyUser;
	}

	/**
	* 设置字段ModifyUser的值，该字段的<br>
	* 字段名称 :修改人<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyUser(String modifyUser) {
		this.ModifyUser = modifyUser;
    }

	/**
	* 获取字段ModifyTime的值，该字段的<br>
	* 字段名称 :修改时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getModifyTime() {
		return ModifyTime;
	}

	/**
	* 设置字段ModifyTime的值，该字段的<br>
	* 字段名称 :修改时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyTime(Date modifyTime) {
		this.ModifyTime = modifyTime;
    }

	/**
	* 获取字段BackupNo的值，该字段的<br>
	* 字段名称 :备份编号<br>
	* 数据类型 :varchar(15)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getBackupNo() {
		return BackupNo;
	}

	/**
	* 设置字段BackupNo的值，该字段的<br>
	* 字段名称 :备份编号<br>
	* 数据类型 :varchar(15)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setBackupNo(String backupNo) {
		this.BackupNo = backupNo;
    }

	/**
	* 获取字段BackupOperator的值，该字段的<br>
	* 字段名称 :备份人<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getBackupOperator() {
		return BackupOperator;
	}

	/**
	* 设置字段BackupOperator的值，该字段的<br>
	* 字段名称 :备份人<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setBackupOperator(String backupOperator) {
		this.BackupOperator = backupOperator;
    }

	/**
	* 获取字段BackupTime的值，该字段的<br>
	* 字段名称 :备份时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public Date getBackupTime() {
		return BackupTime;
	}

	/**
	* 设置字段BackupTime的值，该字段的<br>
	* 字段名称 :备份时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setBackupTime(Date backupTime) {
		this.BackupTime = backupTime;
    }

	/**
	* 获取字段BackupMemo的值，该字段的<br>
	* 字段名称 :备份备注<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getBackupMemo() {
		return BackupMemo;
	}

	/**
	* 设置字段BackupMemo的值，该字段的<br>
	* 字段名称 :备份备注<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBackupMemo(String backupMemo) {
		this.BackupMemo = backupMemo;
    }

}