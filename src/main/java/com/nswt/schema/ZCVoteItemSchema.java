package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;

/**
 * �����ƣ�ͶƱ��ϸ��<br>
 * ����룺ZCVoteItem<br>
 * ��������ID<br>
 */
public class ZCVoteItemSchema extends Schema {
	private Long ID;

	private Long SubjectID;

	private Long VoteID;

	private String Item;

	private Long Score;

	private String ItemType;

	private Long VoteDocID;

	private Long OrderFlag;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 0 , 0 , true , true),
		new SchemaColumn("SubjectID", DataColumn.LONG, 1, 0 , 0 , true , false),
		new SchemaColumn("VoteID", DataColumn.LONG, 2, 0 , 0 , true , false),
		new SchemaColumn("Item", DataColumn.STRING, 3, 100 , 0 , true , false),
		new SchemaColumn("Score", DataColumn.LONG, 4, 0 , 0 , true , false),
		new SchemaColumn("ItemType", DataColumn.STRING, 5, 1 , 0 , true , false),
		new SchemaColumn("VoteDocID", DataColumn.LONG, 6, 0 , 0 , false , false),
		new SchemaColumn("OrderFlag", DataColumn.LONG, 7, 0 , 0 , false , false)
	};

	public static final String _TableCode = "ZCVoteItem";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into ZCVoteItem values(?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZCVoteItem set ID=?,SubjectID=?,VoteID=?,Item=?,Score=?,ItemType=?,VoteDocID=?,OrderFlag=? where ID=?";

	protected static final String _DeleteSQL = "delete from ZCVoteItem  where ID=?";

	protected static final String _FillAllSQL = "select * from ZCVoteItem  where ID=?";

	public ZCVoteItemSchema(){
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
		return new ZCVoteItemSchema();
	}

	protected SchemaSet newSet(){
		return new ZCVoteItemSet();
	}

	public ZCVoteItemSet query() {
		return query(null, -1, -1);
	}

	public ZCVoteItemSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZCVoteItemSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZCVoteItemSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZCVoteItemSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){SubjectID = null;}else{SubjectID = new Long(v.toString());}return;}
		if (i == 2){if(v==null){VoteID = null;}else{VoteID = new Long(v.toString());}return;}
		if (i == 3){Item = (String)v;return;}
		if (i == 4){if(v==null){Score = null;}else{Score = new Long(v.toString());}return;}
		if (i == 5){ItemType = (String)v;return;}
		if (i == 6){if(v==null){VoteDocID = null;}else{VoteDocID = new Long(v.toString());}return;}
		if (i == 7){if(v==null){OrderFlag = null;}else{OrderFlag = new Long(v.toString());}return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return SubjectID;}
		if (i == 2){return VoteID;}
		if (i == 3){return Item;}
		if (i == 4){return Score;}
		if (i == 5){return ItemType;}
		if (i == 6){return VoteDocID;}
		if (i == 7){return OrderFlag;}
		return null;
	}

	/**
	* ��ȡ�ֶ�ID��ֵ�����ֶε�<br>
	* �ֶ����� :ͶƱ��ϸID<br>
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
	* �ֶ����� :ͶƱ��ϸID<br>
	* �������� :bigint<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setID(long iD) {
		this.ID = new Long(iD);
    }

	/**
	* �����ֶ�ID��ֵ�����ֶε�<br>
	* �ֶ����� :ͶƱ��ϸID<br>
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
	* ��ȡ�ֶ�SubjectID��ֵ�����ֶε�<br>
	* �ֶ����� :��ĿID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public long getSubjectID() {
		if(SubjectID==null){return 0;}
		return SubjectID.longValue();
	}

	/**
	* �����ֶ�SubjectID��ֵ�����ֶε�<br>
	* �ֶ����� :��ĿID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setSubjectID(long subjectID) {
		this.SubjectID = new Long(subjectID);
    }

	/**
	* �����ֶ�SubjectID��ֵ�����ֶε�<br>
	* �ֶ����� :��ĿID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setSubjectID(String subjectID) {
		if (subjectID == null){
			this.SubjectID = null;
			return;
		}
		this.SubjectID = new Long(subjectID);
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
	* ��ȡ�ֶ�Item��ֵ�����ֶε�<br>
	* �ֶ����� :ͶƱ��<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getItem() {
		return Item;
	}

	/**
	* �����ֶ�Item��ֵ�����ֶε�<br>
	* �ֶ����� :ͶƱ��<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setItem(String item) {
		this.Item = item;
    }

	/**
	* ��ȡ�ֶ�Score��ֵ�����ֶε�<br>
	* �ֶ����� :ͶƱ��Ʊ��<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public long getScore() {
		if(Score==null){return 0;}
		return Score.longValue();
	}

	/**
	* �����ֶ�Score��ֵ�����ֶε�<br>
	* �ֶ����� :ͶƱ��Ʊ��<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setScore(long score) {
		this.Score = new Long(score);
    }

	/**
	* �����ֶ�Score��ֵ�����ֶε�<br>
	* �ֶ����� :ͶƱ��Ʊ��<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setScore(String score) {
		if (score == null){
			this.Score = null;
			return;
		}
		this.Score = new Long(score);
    }

	/**
	* ��ȡ�ֶ�ItemType��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :char(1)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getItemType() {
		return ItemType;
	}

	/**
	* �����ֶ�ItemType��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :char(1)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setItemType(String itemType) {
		this.ItemType = itemType;
    }

	/**
	* ��ȡ�ֶ�VoteDocID��ֵ�����ֶε�<br>
	* �ֶ����� :ͶƱ�ĵ�<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getVoteDocID() {
		if(VoteDocID==null){return 0;}
		return VoteDocID.longValue();
	}

	/**
	* �����ֶ�VoteDocID��ֵ�����ֶε�<br>
	* �ֶ����� :ͶƱ�ĵ�<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setVoteDocID(long voteDocID) {
		this.VoteDocID = new Long(voteDocID);
    }

	/**
	* �����ֶ�VoteDocID��ֵ�����ֶε�<br>
	* �ֶ����� :ͶƱ�ĵ�<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setVoteDocID(String voteDocID) {
		if (voteDocID == null){
			this.VoteDocID = null;
			return;
		}
		this.VoteDocID = new Long(voteDocID);
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

}