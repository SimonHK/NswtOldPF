package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * �����ƣ����ű���<br>
 * ����룺BZDMaxNo<br>
 * ��������NoType, NoSubType, BackupNo<br>
 */
public class BZDMaxNoSchema extends Schema {
	private String NoType;

	private String NoSubType;

	private Long MxValue;

	private Long Length;

	private String BackupNo;

	private String BackupOperator;

	private Date BackupTime;

	private String BackupMemo;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("NoType", DataColumn.STRING, 0, 20 , 0 , true , true),
		new SchemaColumn("NoSubType", DataColumn.STRING, 1, 255 , 0 , true , true),
		new SchemaColumn("MxValue", DataColumn.LONG, 2, 0 , 0 , true , false),
		new SchemaColumn("Length", DataColumn.LONG, 3, 0 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 4, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 5, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 6, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 7, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZDMaxNo";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into BZDMaxNo values(?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZDMaxNo set NoType=?,NoSubType=?,MxValue=?,Length=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where NoType=? and NoSubType=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZDMaxNo  where NoType=? and NoSubType=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZDMaxNo  where NoType=? and NoSubType=? and BackupNo=?";

	public BZDMaxNoSchema(){
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
		return new BZDMaxNoSchema();
	}

	protected SchemaSet newSet(){
		return new BZDMaxNoSet();
	}

	public BZDMaxNoSet query() {
		return query(null, -1, -1);
	}

	public BZDMaxNoSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZDMaxNoSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZDMaxNoSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZDMaxNoSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){NoType = (String)v;return;}
		if (i == 1){NoSubType = (String)v;return;}
		if (i == 2){if(v==null){MxValue = null;}else{MxValue = new Long(v.toString());}return;}
		if (i == 3){if(v==null){Length = null;}else{Length = new Long(v.toString());}return;}
		if (i == 4){BackupNo = (String)v;return;}
		if (i == 5){BackupOperator = (String)v;return;}
		if (i == 6){BackupTime = (Date)v;return;}
		if (i == 7){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return NoType;}
		if (i == 1){return NoSubType;}
		if (i == 2){return MxValue;}
		if (i == 3){return Length;}
		if (i == 4){return BackupNo;}
		if (i == 5){return BackupOperator;}
		if (i == 6){return BackupTime;}
		if (i == 7){return BackupMemo;}
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