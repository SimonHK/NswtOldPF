package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;

/**
 * �����ƣ���Ƶ������<br>
 * ����룺ZCAudioRela<br>
 * ��������ID, RelaID, RelaType<br>
 */
public class ZCAudioRelaSchema extends Schema {
	private Long ID;

	private Long RelaID;

	private String RelaType;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 0 , 0 , true , true),
		new SchemaColumn("RelaID", DataColumn.LONG, 1, 0 , 0 , true , true),
		new SchemaColumn("RelaType", DataColumn.STRING, 2, 20 , 0 , true , true)
	};

	public static final String _TableCode = "ZCAudioRela";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into ZCAudioRela values(?,?,?)";

	protected static final String _UpdateAllSQL = "update ZCAudioRela set ID=?,RelaID=?,RelaType=? where ID=? and RelaID=? and RelaType=?";

	protected static final String _DeleteSQL = "delete from ZCAudioRela  where ID=? and RelaID=? and RelaType=?";

	protected static final String _FillAllSQL = "select * from ZCAudioRela  where ID=? and RelaID=? and RelaType=?";

	public ZCAudioRelaSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[3];
	}

	protected Schema newInstance(){
		return new ZCAudioRelaSchema();
	}

	protected SchemaSet newSet(){
		return new ZCAudioRelaSet();
	}

	public ZCAudioRelaSet query() {
		return query(null, -1, -1);
	}

	public ZCAudioRelaSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZCAudioRelaSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZCAudioRelaSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZCAudioRelaSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){RelaID = null;}else{RelaID = new Long(v.toString());}return;}
		if (i == 2){RelaType = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return RelaID;}
		if (i == 2){return RelaType;}
		return null;
	}

	/**
	* ��ȡ�ֶ�ID��ֵ�����ֶε�<br>
	* �ֶ����� :��ƵID<br>
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
	* �ֶ����� :��ƵID<br>
	* �������� :bigint<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setID(long iD) {
		this.ID = new Long(iD);
    }

	/**
	* �����ֶ�ID��ֵ�����ֶε�<br>
	* �ֶ����� :��ƵID<br>
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
	* ��ȡ�ֶ�RelaID��ֵ�����ֶε�<br>
	* �ֶ����� :��Ƶ����ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public long getRelaID() {
		if(RelaID==null){return 0;}
		return RelaID.longValue();
	}

	/**
	* �����ֶ�RelaID��ֵ�����ֶε�<br>
	* �ֶ����� :��Ƶ����ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setRelaID(long relaID) {
		this.RelaID = new Long(relaID);
    }

	/**
	* �����ֶ�RelaID��ֵ�����ֶε�<br>
	* �ֶ����� :��Ƶ����ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setRelaID(String relaID) {
		if (relaID == null){
			this.RelaID = null;
			return;
		}
		this.RelaID = new Long(relaID);
    }

	/**
	* ��ȡ�ֶ�RelaType��ֵ�����ֶε�<br>
	* �ֶ����� :��Ƶ�������<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	* ��ע��Ϣ :<br>
	-1-�������Ƶ�������κ�ID������ֻ�ǰ���Ƶ�ϴ��˶���<br>
	0-�����е���Ƶ<br>
	*/
	public String getRelaType() {
		return RelaType;
	}

	/**
	* �����ֶ�RelaType��ֵ�����ֶε�<br>
	* �ֶ����� :��Ƶ�������<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	* ��ע��Ϣ :<br>
	-1-�������Ƶ�������κ�ID������ֻ�ǰ���Ƶ�ϴ��˶���<br>
	0-�����е���Ƶ<br>
	*/
	public void setRelaType(String relaType) {
		this.RelaType = relaType;
    }

}