package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * �����ƣ���ŵ��ҵͶƱ��¼<br>
 * �����룺ZCVoteRecorder<br>
 * ��������ID<br>
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
	* ��ȡ�ֶ�ID��ֵ�����ֶε�<br>
	* �ֶ����� :ID<br>
	* �������� :int<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public int getID() {
		if(ID==null){return 0;}
		return ID.intValue();
	}

	/**
	* �����ֶ�ID��ֵ�����ֶε�<br>
	* �ֶ����� :ID<br>
	* �������� :int<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setID(int iD) {
		this.ID = new Integer(iD);
    }

	/**
	* �����ֶ�ID��ֵ�����ֶε�<br>
	* �ֶ����� :ID<br>
	* �������� :int<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setID(String iD) {
		if (iD == null){
			this.ID = null;
			return;
		}
		this.ID = new Integer(iD);
    }

	/**
	* ��ȡ�ֶ�EnterPriseID��ֵ�����ֶε�<br>
	* �ֶ����� :��ҵID<br>
	* �������� :int<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public int getEnterPriseID() {
		if(EnterPriseID==null){return 0;}
		return EnterPriseID.intValue();
	}

	/**
	* �����ֶ�EnterPriseID��ֵ�����ֶε�<br>
	* �ֶ����� :��ҵID<br>
	* �������� :int<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setEnterPriseID(int enterPriseID) {
		this.EnterPriseID = new Integer(enterPriseID);
    }

	/**
	* �����ֶ�EnterPriseID��ֵ�����ֶε�<br>
	* �ֶ����� :��ҵID<br>
	* �������� :int<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setEnterPriseID(String enterPriseID) {
		if (enterPriseID == null){
			this.EnterPriseID = null;
			return;
		}
		this.EnterPriseID = new Integer(enterPriseID);
    }

	/**
	* ��ȡ�ֶ�VoteIP��ֵ�����ֶε�<br>
	* �ֶ����� :ͶƱIP<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getVoteIP() {
		return VoteIP;
	}

	/**
	* �����ֶ�VoteIP��ֵ�����ֶε�<br>
	* �ֶ����� :ͶƱIP<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setVoteIP(String voteIP) {
		this.VoteIP = voteIP;
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
	* ��ȡ�ֶ�Prop5��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�5<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp5() {
		return Prop5;
	}

	/**
	* �����ֶ�Prop5��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�5<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp5(String prop5) {
		this.Prop5 = prop5;
    }

	/**
	* ��ȡ�ֶ�Prop6��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�6<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp6() {
		return Prop6;
	}

	/**
	* �����ֶ�Prop6��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�6<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp6(String prop6) {
		this.Prop6 = prop6;
    }

	/**
	* ��ȡ�ֶ�AddTime��ֵ�����ֶε�<br>
	* �ֶ����� :����ʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public Date getAddTime() {
		return AddTime;
	}

	/**
	* �����ֶ�AddTime��ֵ�����ֶε�<br>
	* �ֶ����� :����ʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAddTime(Date addTime) {
		this.AddTime = addTime;
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