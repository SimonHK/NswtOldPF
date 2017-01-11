package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;

/**
 * �����ƣ����ű�<br>
 * ����룺ZDMaxNo<br>
 * ��������NoType, NoSubType<br>
 */
public class ZDMaxNoSchema extends Schema {
	private String NoType;

	private String NoSubType;

	private Long MxValue;

	private Long Length;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("NoType", DataColumn.STRING, 0, 20 , 0 , true , true),
		new SchemaColumn("NoSubType", DataColumn.STRING, 1, 255 , 0 , true , true),
		new SchemaColumn("MxValue", DataColumn.LONG, 2, 0 , 0 , true , false),
		new SchemaColumn("Length", DataColumn.LONG, 3, 0 , 0 , false , false)
	};

	public static final String _TableCode = "ZDMaxNo";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into ZDMaxNo values(?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZDMaxNo set NoType=?,NoSubType=?,MxValue=?,Length=? where NoType=? and NoSubType=?";

	protected static final String _DeleteSQL = "delete from ZDMaxNo  where NoType=? and NoSubType=?";

	protected static final String _FillAllSQL = "select * from ZDMaxNo  where NoType=? and NoSubType=?";

	public ZDMaxNoSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[4];
	}

	protected Schema newInstance(){
		return new ZDMaxNoSchema();
	}

	protected SchemaSet newSet(){
		return new ZDMaxNoSet();
	}

	public ZDMaxNoSet query() {
		return query(null, -1, -1);
	}

	public ZDMaxNoSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZDMaxNoSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZDMaxNoSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZDMaxNoSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){NoType = (String)v;return;}
		if (i == 1){NoSubType = (String)v;return;}
		if (i == 2){if(v==null){MxValue = null;}else{MxValue = new Long(v.toString());}return;}
		if (i == 3){if(v==null){Length = null;}else{Length = new Long(v.toString());}return;}
	}

	public Object getV(int i) {
		if (i == 0){return NoType;}
		if (i == 1){return NoSubType;}
		if (i == 2){return MxValue;}
		if (i == 3){return Length;}
		return null;
	}

	/**
	* ��ȡ�ֶ�NoType��ֵ�����ֶε�<br>
	* �ֶ����� :�������<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public String getNoType() {
		return NoType;
	}

	/**
	* �����ֶ�NoType��ֵ�����ֶε�<br>
	* �ֶ����� :�������<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setNoType(String noType) {
		this.NoType = noType;
    }

	/**
	* ��ȡ�ֶ�NoSubType��ֵ�����ֶε�<br>
	* �ֶ����� :���������<br>
	* �������� :varchar(255)<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public String getNoSubType() {
		return NoSubType;
	}

	/**
	* �����ֶ�NoSubType��ֵ�����ֶε�<br>
	* �ֶ����� :���������<br>
	* �������� :varchar(255)<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setNoSubType(String noSubType) {
		this.NoSubType = noSubType;
    }

	/**
	* ��ȡ�ֶ�MxValue��ֵ�����ֶε�<br>
	* �ֶ����� :��ǰ����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public long getMxValue() {
		if(MxValue==null){return 0;}
		return MxValue.longValue();
	}

	/**
	* �����ֶ�MxValue��ֵ�����ֶε�<br>
	* �ֶ����� :��ǰ����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setMxValue(long mxValue) {
		this.MxValue = new Long(mxValue);
    }

	/**
	* �����ֶ�MxValue��ֵ�����ֶε�<br>
	* �ֶ����� :��ǰ����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setMxValue(String mxValue) {
		if (mxValue == null){
			this.MxValue = null;
			return;
		}
		this.MxValue = new Long(mxValue);
    }

	/**
	* ��ȡ�ֶ�Length��ֵ�����ֶε�<br>
	* �ֶ����� :���볤��<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getLength() {
		if(Length==null){return 0;}
		return Length.longValue();
	}

	/**
	* �����ֶ�Length��ֵ�����ֶε�<br>
	* �ֶ����� :���볤��<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setLength(long length) {
		this.Length = new Long(length);
    }

	/**
	* �����ֶ�Length��ֵ�����ֶε�<br>
	* �ֶ����� :���볤��<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setLength(String length) {
		if (length == null){
			this.Length = null;
			return;
		}
		this.Length = new Long(length);
    }

}