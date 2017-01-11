package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * �����ƣ�ͶƱ��־��<br>
 * ����룺ZCVoteLog<br>
 * ��������ID<br>
 */
public class ZCVoteLogSchema extends Schema {
	private Long ID;

	private Long VoteID;

	private String IP;

	private String Result;

	private String Prop1;

	private String Prop2;

	private String addUser;

	private Date addTime;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 0 , 0 , true , true),
		new SchemaColumn("VoteID", DataColumn.LONG, 1, 0 , 0 , true , false),
		new SchemaColumn("IP", DataColumn.STRING, 2, 20 , 0 , true , false),
		new SchemaColumn("Result", DataColumn.STRING, 3, 2000 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 4, 50 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 5, 50 , 0 , false , false),
		new SchemaColumn("addUser", DataColumn.STRING, 6, 50 , 0 , false , false),
		new SchemaColumn("addTime", DataColumn.DATETIME, 7, 0 , 0 , true , false)
	};

	public static final String _TableCode = "ZCVoteLog";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into ZCVoteLog values(?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZCVoteLog set ID=?,VoteID=?,IP=?,Result=?,Prop1=?,Prop2=?,addUser=?,addTime=? where ID=?";

	protected static final String _DeleteSQL = "delete from ZCVoteLog  where ID=?";

	protected static final String _FillAllSQL = "select * from ZCVoteLog  where ID=?";

	public ZCVoteLogSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[8];
	}

	protected Schema newInstance(){
		return new ZCVoteLogSchema();
	}

	protected SchemaSet newSet(){
		return new ZCVoteLogSet();
	}

	public ZCVoteLogSet query() {
		return query(null, -1, -1);
	}

	public ZCVoteLogSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZCVoteLogSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZCVoteLogSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZCVoteLogSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){VoteID = null;}else{VoteID = new Long(v.toString());}return;}
		if (i == 2){IP = (String)v;return;}
		if (i == 3){Result = (String)v;return;}
		if (i == 4){Prop1 = (String)v;return;}
		if (i == 5){Prop2 = (String)v;return;}
		if (i == 6){addUser = (String)v;return;}
		if (i == 7){addTime = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return VoteID;}
		if (i == 2){return IP;}
		if (i == 3){return Result;}
		if (i == 4){return Prop1;}
		if (i == 5){return Prop2;}
		if (i == 6){return addUser;}
		if (i == 7){return addTime;}
		return null;
	}

	/**
	* ��ȡ�ֶ�ID��ֵ�����ֶε�<br>
	* �ֶ����� :��־ID<br>
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
	* �ֶ����� :��־ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setID(long iD) {
		this.ID = new Long(iD);
    }

	/**
	* �����ֶ�ID��ֵ�����ֶε�<br>
	* �ֶ����� :��־ID<br>
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
	* ��ȡ�ֶ�VoteID��ֵ�����ֶε�<br>
	* �ֶ����� :ͶƱID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public long getVoteID() {
		if(VoteID==null){return 0;}
		return VoteID.longValue();
	}

	/**
	* �����ֶ�VoteID��ֵ�����ֶε�<br>
	* �ֶ����� :ͶƱID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setVoteID(long voteID) {
		this.VoteID = new Long(voteID);
    }

	/**
	* �����ֶ�VoteID��ֵ�����ֶε�<br>
	* �ֶ����� :ͶƱID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setVoteID(String voteID) {
		if (voteID == null){
			this.VoteID = null;
			return;
		}
		this.VoteID = new Long(voteID);
    }

	/**
	* ��ȡ�ֶ�IP��ֵ�����ֶε�<br>
	* �ֶ����� :ͶƱIP<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getIP() {
		return IP;
	}

	/**
	* �����ֶ�IP��ֵ�����ֶε�<br>
	* �ֶ����� :ͶƱIP<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setIP(String iP) {
		this.IP = iP;
    }

	/**
	* ��ȡ�ֶ�Result��ֵ�����ֶε�<br>
	* �ֶ����� :ͶƱ���<br>
	* �������� :varchar(2000)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getResult() {
		return Result;
	}

	/**
	* �����ֶ�Result��ֵ�����ֶε�<br>
	* �ֶ����� :ͶƱ���<br>
	* �������� :varchar(2000)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setResult(String result) {
		this.Result = result;
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
	* ��ȡ�ֶ�addUser��ֵ�����ֶε�<br>
	* �ֶ����� :ͶƱ��<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getAddUser() {
		return addUser;
	}

	/**
	* �����ֶ�addUser��ֵ�����ֶε�<br>
	* �ֶ����� :ͶƱ��<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAddUser(String addUser) {
		this.addUser = addUser;
    }

	/**
	* ��ȡ�ֶ�addTime��ֵ�����ֶε�<br>
	* �ֶ����� :ͶƱʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public Date getAddTime() {
		return addTime;
	}

	/**
	* �����ֶ�addTime��ֵ�����ֶε�<br>
	* �ֶ����� :ͶƱʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
    }

}