package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * �����ƣ�IP��ַ������Χ����<br>
 * ����룺BZDIPRange<br>
 * ��������DistrictCode, BackupNo<br>
 */
public class BZDIPRangeSchema extends Schema {
	private String DistrictCode;

	private String IPRanges;

	private String BackupNo;

	private String BackupOperator;

	private Date BackupTime;

	private String BackupMemo;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("DistrictCode", DataColumn.STRING, 0, 10 , 0 , true , true),
		new SchemaColumn("IPRanges", DataColumn.CLOB, 1, 0 , 0 , true , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 2, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 3, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 4, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 5, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZDIPRange";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into BZDIPRange values(?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZDIPRange set DistrictCode=?,IPRanges=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where DistrictCode=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZDIPRange  where DistrictCode=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZDIPRange  where DistrictCode=? and BackupNo=?";

	public BZDIPRangeSchema(){
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
		return new BZDIPRangeSchema();
	}

	protected SchemaSet newSet(){
		return new BZDIPRangeSet();
	}

	public BZDIPRangeSet query() {
		return query(null, -1, -1);
	}

	public BZDIPRangeSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZDIPRangeSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZDIPRangeSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZDIPRangeSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){DistrictCode = (String)v;return;}
		if (i == 1){IPRanges = (String)v;return;}
		if (i == 2){BackupNo = (String)v;return;}
		if (i == 3){BackupOperator = (String)v;return;}
		if (i == 4){BackupTime = (Date)v;return;}
		if (i == 5){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return DistrictCode;}
		if (i == 1){return IPRanges;}
		if (i == 2){return BackupNo;}
		if (i == 3){return BackupOperator;}
		if (i == 4){return BackupTime;}
		if (i == 5){return BackupMemo;}
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

	/**
	* ��ȡ�ֶ�BackupNo��ֵ�����ֶε�<br>
	* �ֶ����� :���ݱ��<br>
	* �������� :varchar(15)<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public String getBackupNo() {
		return BackupNo;
	}

	/**
	* �����ֶ�BackupNo��ֵ�����ֶε�<br>
	* �ֶ����� :���ݱ��<br>
	* �������� :varchar(15)<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setBackupNo(String backupNo) {
		this.BackupNo = backupNo;
    }

	/**
	* ��ȡ�ֶ�BackupOperator��ֵ�����ֶε�<br>
	* �ֶ����� :������<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getBackupOperator() {
		return BackupOperator;
	}

	/**
	* �����ֶ�BackupOperator��ֵ�����ֶε�<br>
	* �ֶ����� :������<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setBackupOperator(String backupOperator) {
		this.BackupOperator = backupOperator;
    }

	/**
	* ��ȡ�ֶ�BackupTime��ֵ�����ֶε�<br>
	* �ֶ����� :����ʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public Date getBackupTime() {
		return BackupTime;
	}

	/**
	* �����ֶ�BackupTime��ֵ�����ֶε�<br>
	* �ֶ����� :����ʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setBackupTime(Date backupTime) {
		this.BackupTime = backupTime;
    }

	/**
	* ��ȡ�ֶ�BackupMemo��ֵ�����ֶε�<br>
	* �ֶ����� :���ݱ�ע<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getBackupMemo() {
		return BackupMemo;
	}

	/**
	* �����ֶ�BackupMemo��ֵ�����ֶε�<br>
	* �ֶ����� :���ݱ�ע<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setBackupMemo(String backupMemo) {
		this.BackupMemo = backupMemo;
    }

}