package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;

/**
 * �����ƣ�IP��ַ������Χ��<br>
 * ����룺ZDIPRange<br>
 * ��������DistrictCode<br>
 */
public class ZDIPRangeSchema extends Schema {
	private String DistrictCode;

	private String IPRanges;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("DistrictCode", DataColumn.STRING, 0, 10 , 0 , true , true),
		new SchemaColumn("IPRanges", DataColumn.CLOB, 1, 0 , 0 , true , false)
	};

	public static final String _TableCode = "ZDIPRange";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into ZDIPRange values(?,?)";

	protected static final String _UpdateAllSQL = "update ZDIPRange set DistrictCode=?,IPRanges=? where DistrictCode=?";

	protected static final String _DeleteSQL = "delete from ZDIPRange  where DistrictCode=?";

	protected static final String _FillAllSQL = "select * from ZDIPRange  where DistrictCode=?";

	public ZDIPRangeSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[2];
	}

	protected Schema newInstance(){
		return new ZDIPRangeSchema();
	}

	protected SchemaSet newSet(){
		return new ZDIPRangeSet();
	}

	public ZDIPRangeSet query() {
		return query(null, -1, -1);
	}

	public ZDIPRangeSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZDIPRangeSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZDIPRangeSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZDIPRangeSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){DistrictCode = (String)v;return;}
		if (i == 1){IPRanges = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return DistrictCode;}
		if (i == 1){return IPRanges;}
		return null;
	}

	/**
	* ��ȡ�ֶ�DistrictCode��ֵ�����ֶε�<br>
	* �ֶ����� :DistrictCode<br>
	* �������� :varchar(10)<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public String getDistrictCode() {
		return DistrictCode;
	}

	/**
	* �����ֶ�DistrictCode��ֵ�����ֶε�<br>
	* �ֶ����� :DistrictCode<br>
	* �������� :varchar(10)<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setDistrictCode(String districtCode) {
		this.DistrictCode = districtCode;
    }

	/**
	* ��ȡ�ֶ�IPRanges��ֵ�����ֶε�<br>
	* �ֶ����� :IPRanges<br>
	* �������� :text<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getIPRanges() {
		return IPRanges;
	}

	/**
	* �����ֶ�IPRanges��ֵ�����ֶε�<br>
	* �ֶ����� :IPRanges<br>
	* �������� :text<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setIPRanges(String iPRanges) {
		this.IPRanges = iPRanges;
    }

}