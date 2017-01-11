package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * �����ƣ���������<br>
 * ����룺BZDDistrict<br>
 * ��������Code, BackupNo<br>
 */
public class BZDDistrictSchema extends Schema {
	private String Code;

	private String Name;

	private String CodeOrder;

	private Integer TreeLevel;

	private String Type;

	private String BackupNo;

	private String BackupOperator;

	private Date BackupTime;

	private String BackupMemo;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("Code", DataColumn.STRING, 0, 6 , 0 , true , true),
		new SchemaColumn("Name", DataColumn.STRING, 1, 100 , 0 , false , false),
		new SchemaColumn("CodeOrder", DataColumn.STRING, 2, 20 , 0 , false , false),
		new SchemaColumn("TreeLevel", DataColumn.INTEGER, 3, 0 , 0 , false , false),
		new SchemaColumn("Type", DataColumn.STRING, 4, 2 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 5, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 6, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 7, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 8, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZDDistrict";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into BZDDistrict values(?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZDDistrict set Code=?,Name=?,CodeOrder=?,TreeLevel=?,Type=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where Code=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZDDistrict  where Code=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZDDistrict  where Code=? and BackupNo=?";

	public BZDDistrictSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[9];
	}

	protected Schema newInstance(){
		return new BZDDistrictSchema();
	}

	protected SchemaSet newSet(){
		return new BZDDistrictSet();
	}

	public BZDDistrictSet query() {
		return query(null, -1, -1);
	}

	public BZDDistrictSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZDDistrictSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZDDistrictSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZDDistrictSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){Code = (String)v;return;}
		if (i == 1){Name = (String)v;return;}
		if (i == 2){CodeOrder = (String)v;return;}
		if (i == 3){if(v==null){TreeLevel = null;}else{TreeLevel = new Integer(v.toString());}return;}
		if (i == 4){Type = (String)v;return;}
		if (i == 5){BackupNo = (String)v;return;}
		if (i == 6){BackupOperator = (String)v;return;}
		if (i == 7){BackupTime = (Date)v;return;}
		if (i == 8){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return Code;}
		if (i == 1){return Name;}
		if (i == 2){return CodeOrder;}
		if (i == 3){return TreeLevel;}
		if (i == 4){return Type;}
		if (i == 5){return BackupNo;}
		if (i == 6){return BackupOperator;}
		if (i == 7){return BackupTime;}
		if (i == 8){return BackupMemo;}
		return null;
	}

	/**
	* ��ȡ�ֶ�Code��ֵ�����ֶε�<br>
	* �ֶ����� :Code<br>
	* �������� :char(6)<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public String getCode() {
		return Code;
	}

	/**
	* �����ֶ�Code��ֵ�����ֶε�<br>
	* �ֶ����� :Code<br>
	* �������� :char(6)<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setCode(String code) {
		this.Code = code;
    }

	/**
	* ��ȡ�ֶ�Name��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getName() {
		return Name;
	}

	/**
	* �����ֶ�Name��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setName(String name) {
		this.Name = name;
    }

	/**
	* ��ȡ�ֶ�CodeOrder��ֵ�����ֶε�<br>
	* �ֶ����� :����˳��<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getCodeOrder() {
		return CodeOrder;
	}

	/**
	* �����ֶ�CodeOrder��ֵ�����ֶε�<br>
	* �ֶ����� :����˳��<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCodeOrder(String codeOrder) {
		this.CodeOrder = codeOrder;
    }

	/**
	* ��ȡ�ֶ�TreeLevel��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :smallint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public int getTreeLevel() {
		if(TreeLevel==null){return 0;}
		return TreeLevel.intValue();
	}

	/**
	* �����ֶ�TreeLevel��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :smallint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setTreeLevel(int treeLevel) {
		this.TreeLevel = new Integer(treeLevel);
    }

	/**
	* �����ֶ�TreeLevel��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :smallint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setTreeLevel(String treeLevel) {
		if (treeLevel == null){
			this.TreeLevel = null;
			return;
		}
		this.TreeLevel = new Integer(treeLevel);
    }

	/**
	* ��ȡ�ֶ�Type��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :char(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getType() {
		return Type;
	}

	/**
	* �����ֶ�Type��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :char(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setType(String type) {
		this.Type = type;
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