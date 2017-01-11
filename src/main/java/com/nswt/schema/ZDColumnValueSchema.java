package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;

/**
 * �����ƣ��Զ����ֶ�ֵ<br>
 * ����룺ZDColumnValue<br>
 * ��������ID<br>
 */
public class ZDColumnValueSchema extends Schema {
	private Long ID;

	private Long ColumnID;

	private String ColumnCode;

	private String TextValue;

	private String RelaType;

	private String RelaID;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 0 , 0 , true , true),
		new SchemaColumn("ColumnID", DataColumn.LONG, 1, 0 , 0 , true , false),
		new SchemaColumn("ColumnCode", DataColumn.STRING, 2, 100 , 0 , true , false),
		new SchemaColumn("TextValue", DataColumn.CLOB, 3, 0 , 0 , false , false),
		new SchemaColumn("RelaType", DataColumn.STRING, 4, 2 , 0 , false , false),
		new SchemaColumn("RelaID", DataColumn.STRING, 5, 100 , 0 , false , false)
	};

	public static final String _TableCode = "ZDColumnValue";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into ZDColumnValue values(?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZDColumnValue set ID=?,ColumnID=?,ColumnCode=?,TextValue=?,RelaType=?,RelaID=? where ID=?";

	protected static final String _DeleteSQL = "delete from ZDColumnValue  where ID=?";

	protected static final String _FillAllSQL = "select * from ZDColumnValue  where ID=?";

	public ZDColumnValueSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[6];
	}

	protected Schema newInstance(){
		return new ZDColumnValueSchema();
	}

	protected SchemaSet newSet(){
		return new ZDColumnValueSet();
	}

	public ZDColumnValueSet query() {
		return query(null, -1, -1);
	}

	public ZDColumnValueSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZDColumnValueSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZDColumnValueSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZDColumnValueSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){ColumnID = null;}else{ColumnID = new Long(v.toString());}return;}
		if (i == 2){ColumnCode = (String)v;return;}
		if (i == 3){TextValue = (String)v;return;}
		if (i == 4){RelaType = (String)v;return;}
		if (i == 5){RelaID = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return ColumnID;}
		if (i == 2){return ColumnCode;}
		if (i == 3){return TextValue;}
		if (i == 4){return RelaType;}
		if (i == 5){return RelaID;}
		return null;
	}

	/**
	* ��ȡ�ֶ�ID��ֵ�����ֶε�<br>
	* �ֶ����� :ID<br>
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
	* �ֶ����� :ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setID(long iD) {
		this.ID = new Long(iD);
    }

	/**
	* �����ֶ�ID��ֵ�����ֶε�<br>
	* �ֶ����� :ID<br>
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
	* ��ȡ�ֶ�ColumnID��ֵ�����ֶε�<br>
	* �ֶ����� :�ֶ�ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public long getColumnID() {
		if(ColumnID==null){return 0;}
		return ColumnID.longValue();
	}

	/**
	* �����ֶ�ColumnID��ֵ�����ֶε�<br>
	* �ֶ����� :�ֶ�ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setColumnID(long columnID) {
		this.ColumnID = new Long(columnID);
    }

	/**
	* �����ֶ�ColumnID��ֵ�����ֶε�<br>
	* �ֶ����� :�ֶ�ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setColumnID(String columnID) {
		if (columnID == null){
			this.ColumnID = null;
			return;
		}
		this.ColumnID = new Long(columnID);
    }

	/**
	* ��ȡ�ֶ�ColumnCode��ֵ�����ֶε�<br>
	* �ֶ����� :�ֶδ���<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getColumnCode() {
		return ColumnCode;
	}

	/**
	* �����ֶ�ColumnCode��ֵ�����ֶε�<br>
	* �ֶ����� :�ֶδ���<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setColumnCode(String columnCode) {
		this.ColumnCode = columnCode;
    }

	/**
	* ��ȡ�ֶ�TextValue��ֵ�����ֶε�<br>
	* �ֶ����� :�ֶ�ֵ<br>
	* �������� :text<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getTextValue() {
		return TextValue;
	}

	/**
	* �����ֶ�TextValue��ֵ�����ֶε�<br>
	* �ֶ����� :�ֶ�ֵ<br>
	* �������� :text<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setTextValue(String textValue) {
		this.TextValue = textValue;
    }

	/**
	* ��ȡ�ֶ�RelaType��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :char(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	* ��ע��Ϣ :<br>
	1-CMSվ��<br>
	2-CMS��Ŀ<br>
	*/
	public String getRelaType() {
		return RelaType;
	}

	/**
	* �����ֶ�RelaType��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :char(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	* ��ע��Ϣ :<br>
	1-CMSվ��<br>
	2-CMS��Ŀ<br>
	*/
	public void setRelaType(String relaType) {
		this.RelaType = relaType;
    }

	/**
	* ��ȡ�ֶ�RelaID��ֵ�����ֶε�<br>
	* �ֶ����� :����ID<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getRelaID() {
		return RelaID;
	}

	/**
	* �����ֶ�RelaID��ֵ�����ֶε�<br>
	* �ֶ����� :����ID<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setRelaID(String relaID) {
		this.RelaID = relaID;
    }

}