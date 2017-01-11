package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * 表名称：承诺企业投票记录<br>
 * 表代码：ZCVoteRecorder<br>
 * 表主键：ID<br>
 */
public class ZCVoteRecorderSchema extends Schema {
	private Integer ID;

	private Integer EnterPriseID;

	private String VoteIP;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	private String Prop4;

	private String Prop5;

	private String Prop6;

	private Date AddTime;

	private Date ModifyTime;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.INTEGER, 0, 0 , 0 , true , true),
		new SchemaColumn("EnterPriseID", DataColumn.INTEGER, 1, 0 , 0 , false , false),
		new SchemaColumn("VoteIP", DataColumn.STRING, 2, 20 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 3, 50 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 4, 50 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 5, 50 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 6, 50 , 0 , false , false),
		new SchemaColumn("Prop5", DataColumn.STRING, 7, 50 , 0 , false , false),
		new SchemaColumn("Prop6", DataColumn.STRING, 8, 50 , 0 , false , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 9, 0 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 10, 0 , 0 , false , false)
	};

	public static final String _TableCode = "ZCVoteRecorder";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into ZCVoteRecorder values(?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZCVoteRecorder set ID=?,EnterPriseID=?,VoteIP=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,Prop5=?,Prop6=?,AddTime=?,ModifyTime=? where ID=?";

	protected static final String _DeleteSQL = "delete from ZCVoteRecorder  where ID=?";

	protected static final String _FillAllSQL = "select * from ZCVoteRecorder  where ID=?";

	public ZCVoteRecorderSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[11];
	}

	protected Schema newInstance(){
		return new ZCVoteRecorderSchema();
	}

	protected SchemaSet newSet(){
		return new ZCVoteRecorderSet();
	}

	public ZCVoteRecorderSet query() {
		return query(null, -1, -1);
	}

	public ZCVoteRecorderSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZCVoteRecorderSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZCVoteRecorderSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZCVoteRecorderSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Integer(v.toString());}return;}
		if (i == 1){if(v==null){EnterPriseID = null;}else{EnterPriseID = new Integer(v.toString());}return;}
		if (i == 2){VoteIP = (String)v;return;}
		if (i == 3){Prop1 = (String)v;return;}
		if (i == 4){Prop2 = (String)v;return;}
		if (i == 5){Prop3 = (String)v;return;}
		if (i == 6){Prop4 = (String)v;return;}
		if (i == 7){Prop5 = (String)v;return;}
		if (i == 8){Prop6 = (String)v;return;}
		if (i == 9){AddTime = (Date)v;return;}
		if (i == 10){ModifyTime = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return EnterPriseID;}
		if (i == 2){return VoteIP;}
		if (i == 3){return Prop1;}
		if (i == 4){return Prop2;}
		if (i == 5){return Prop3;}
		if (i == 6){return Prop4;}
		if (i == 7){return Prop5;}
		if (i == 8){return Prop6;}
		if (i == 9){return AddTime;}
		if (i == 10){return ModifyTime;}
		return null;
	}

	/**
	* 获取字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :int<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public int getID() {
		if(ID==null){return 0;}
		return ID.intValue();
	}

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :int<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(int iD) {
		this.ID = new Integer(iD);
    }

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :int<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(String iD) {
		if (iD == null){
			this.ID = null;
			return;
		}
		this.ID = new Integer(iD);
    }

	/**
	* 获取字段EnterPriseID的值，该字段的<br>
	* 字段名称 :企业ID<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getEnterPriseID() {
		if(EnterPriseID==null){return 0;}
		return EnterPriseID.intValue();
	}

	/**
	* 设置字段EnterPriseID的值，该字段的<br>
	* 字段名称 :企业ID<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setEnterPriseID(int enterPriseID) {
		this.EnterPriseID = new Integer(enterPriseID);
    }

	/**
	* 设置字段EnterPriseID的值，该字段的<br>
	* 字段名称 :企业ID<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setEnterPriseID(String enterPriseID) {
		if (enterPriseID == null){
			this.EnterPriseID = null;
			return;
		}
		this.EnterPriseID = new Integer(enterPriseID);
    }

	/**
	* 获取字段VoteIP的值，该字段的<br>
	* 字段名称 :投票IP<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getVoteIP() {
		return VoteIP;
	}

	/**
	* 设置字段VoteIP的值，该字段的<br>
	* 字段名称 :投票IP<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setVoteIP(String voteIP) {
		this.VoteIP = voteIP;
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

	/**
	* 获取字段Prop3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp3() {
		return Prop3;
	}

	/**
	* 设置字段Prop3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp3(String prop3) {
		this.Prop3 = prop3;
    }

	/**
	* 获取字段Prop4的值，该字段的<br>
	* 字段名称 :备用字段4<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp4() {
		return Prop4;
	}

	/**
	* 设置字段Prop4的值，该字段的<br>
	* 字段名称 :备用字段4<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp4(String prop4) {
		this.Prop4 = prop4;
    }

	/**
	* 获取字段Prop5的值，该字段的<br>
	* 字段名称 :备用字段5<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp5() {
		return Prop5;
	}

	/**
	* 设置字段Prop5的值，该字段的<br>
	* 字段名称 :备用字段5<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp5(String prop5) {
		this.Prop5 = prop5;
    }

	/**
	* 获取字段Prop6的值，该字段的<br>
	* 字段名称 :备用字段6<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp6() {
		return Prop6;
	}

	/**
	* 设置字段Prop6的值，该字段的<br>
	* 字段名称 :备用字段6<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp6(String prop6) {
		this.Prop6 = prop6;
    }

	/**
	* 获取字段AddTime的值，该字段的<br>
	* 字段名称 :添加时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getAddTime() {
		return AddTime;
	}

	/**
	* 设置字段AddTime的值，该字段的<br>
	* 字段名称 :添加时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAddTime(Date addTime) {
		this.AddTime = addTime;
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

}