package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;

/**
 * �����ƣ�ͶƱ��Ŀ��<br>
 * ����룺ZCVoteSubject<br>
 * ��������ID<br>
 */
public class ZCVoteSubjectSchema extends Schema {
	private Long ID;

	private Long VoteID;

	private String Type;

	private String Subject;

	private Long VoteCatalogID;

	private Long OrderFlag;

	private String Prop1;

	private String Prop2;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 0 , 0 , true , true),
		new SchemaColumn("VoteID", DataColumn.LONG, 1, 0 , 0 , true , false),
		new SchemaColumn("Type", DataColumn.STRING, 2, 1 , 0 , true , false),
		new SchemaColumn("Subject", DataColumn.STRING, 3, 100 , 0 , true , false),
		new SchemaColumn("VoteCatalogID", DataColumn.LONG, 4, 0 , 0 , false , false),
		new SchemaColumn("OrderFlag", DataColumn.LONG, 5, 0 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 6, 50 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 7, 50 , 0 , false , false)
	};

	public static final String _TableCode = "ZCVoteSubject";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into ZCVoteSubject values(?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZCVoteSubject set ID=?,VoteID=?,Type=?,Subject=?,VoteCatalogID=?,OrderFlag=?,Prop1=?,Prop2=? where ID=?";

	protected static final String _DeleteSQL = "delete from ZCVoteSubject  where ID=?";

	protected static final String _FillAllSQL = "select * from ZCVoteSubject  where ID=?";

	public ZCVoteSubjectSchema(){
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
		return new ZCVoteSubjectSchema();
	}

	protected SchemaSet newSet(){
		return new ZCVoteSubjectSet();
	}

	public ZCVoteSubjectSet query() {
		return query(null, -1, -1);
	}

	public ZCVoteSubjectSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZCVoteSubjectSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZCVoteSubjectSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZCVoteSubjectSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){VoteID = null;}else{VoteID = new Long(v.toString());}return;}
		if (i == 2){Type = (String)v;return;}
		if (i == 3){Subject = (String)v;return;}
		if (i == 4){if(v==null){VoteCatalogID = null;}else{VoteCatalogID = new Long(v.toString());}return;}
		if (i == 5){if(v==null){OrderFlag = null;}else{OrderFlag = new Long(v.toString());}return;}
		if (i == 6){Prop1 = (String)v;return;}
		if (i == 7){Prop2 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return VoteID;}
		if (i == 2){return Type;}
		if (i == 3){return Subject;}
		if (i == 4){return VoteCatalogID;}
		if (i == 5){return OrderFlag;}
		if (i == 6){return Prop1;}
		if (i == 7){return Prop2;}
		return null;
	}

	/**
	* ��ȡ�ֶ�ID��ֵ�����ֶε�<br>
	* �ֶ����� :ͶƱ����ID<br>
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
	* �ֶ����� :ͶƱ����ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setID(long iD) {
		this.ID = new Long(iD);
    }

	/**
	* �����ֶ�ID��ֵ�����ֶε�<br>
	* �ֶ����� :ͶƱ����ID<br>
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
	* ��ȡ�ֶ�Type��ֵ�����ֶε�<br>
	* �ֶ����� :��ѡ��ѡ<br>
	* �������� :char(1)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getType() {
		return Type;
	}

	/**
	* �����ֶ�Type��ֵ�����ֶε�<br>
	* �ֶ����� :��ѡ��ѡ<br>
	* �������� :char(1)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setType(String type) {
		this.Type = type;
    }

	/**
	* ��ȡ�ֶ�Subject��ֵ�����ֶε�<br>
	* �ֶ����� :ͶƱ��Ŀ<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getSubject() {
		return Subject;
	}

	/**
	* �����ֶ�Subject��ֵ�����ֶε�<br>
	* �ֶ����� :ͶƱ��Ŀ<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setSubject(String subject) {
		this.Subject = subject;
    }

	/**
	* ��ȡ�ֶ�VoteCatalogID��ֵ�����ֶε�<br>
	* �ֶ����� :ͶƱ��Ŀ<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getVoteCatalogID() {
		if(VoteCatalogID==null){return 0;}
		return VoteCatalogID.longValue();
	}

	/**
	* �����ֶ�VoteCatalogID��ֵ�����ֶε�<br>
	* �ֶ����� :ͶƱ��Ŀ<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setVoteCatalogID(long voteCatalogID) {
		this.VoteCatalogID = new Long(voteCatalogID);
    }

	/**
	* �����ֶ�VoteCatalogID��ֵ�����ֶε�<br>
	* �ֶ����� :ͶƱ��Ŀ<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setVoteCatalogID(String voteCatalogID) {
		if (voteCatalogID == null){
			this.VoteCatalogID = null;
			return;
		}
		this.VoteCatalogID = new Long(voteCatalogID);
    }

	/**
	* ��ȡ�ֶ�OrderFlag��ֵ�����ֶε�<br>
	* �ֶ����� :����Ȩֵ<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getOrderFlag() {
		if(OrderFlag==null){return 0;}
		return OrderFlag.longValue();
	}

	/**
	* �����ֶ�OrderFlag��ֵ�����ֶε�<br>
	* �ֶ����� :����Ȩֵ<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setOrderFlag(long orderFlag) {
		this.OrderFlag = new Long(orderFlag);
    }

	/**
	* �����ֶ�OrderFlag��ֵ�����ֶε�<br>
	* �ֶ����� :����Ȩֵ<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setOrderFlag(String orderFlag) {
		if (orderFlag == null){
			this.OrderFlag = null;
			return;
		}
		this.OrderFlag = new Long(orderFlag);
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

}