package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * �����ƣ��������<br>
 * ����룺BZDCode<br>
 * ��������CodeType, ParentCode, CodeValue, BackupNo<br>
 */
public class BZDCodeSchema extends Schema {
	private String CodeType;

	private String ParentCode;

	private String CodeValue;

	private String CodeName;

	private Long CodeOrder;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	private String Prop4;

	private String Memo;

	private Date AddTime;

	private String AddUser;

	private Date ModifyTime;

	private String ModifyUser;

	private String BackupNo;

	private String BackupOperator;

	private Date BackupTime;

	private String BackupMemo;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("CodeType", DataColumn.STRING, 0, 40 , 0 , true , true),
		new SchemaColumn("ParentCode", DataColumn.STRING, 1, 40 , 0 , true , true),
		new SchemaColumn("CodeValue", DataColumn.STRING, 2, 40 , 0 , true , true),
		new SchemaColumn("CodeName", DataColumn.STRING, 3, 100 , 0 , true , false),
		new SchemaColumn("CodeOrder", DataColumn.LONG, 4, 0 , 0 , true , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 5, 40 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 6, 40 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 7, 40 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 8, 40 , 0 , false , false),
		new SchemaColumn("Memo", DataColumn.STRING, 9, 400 , 0 , false , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 10, 0 , 0 , true , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 11, 200 , 0 , true , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 12, 0 , 0 , false , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 13, 200 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 14, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 15, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 16, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 17, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZDCode";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into BZDCode values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZDCode set CodeType=?,ParentCode=?,CodeValue=?,CodeName=?,CodeOrder=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,Memo=?,AddTime=?,AddUser=?,ModifyTime=?,ModifyUser=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where CodeType=? and ParentCode=? and CodeValue=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZDCode  where CodeType=? and ParentCode=? and CodeValue=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZDCode  where CodeType=? and ParentCode=? and CodeValue=? and BackupNo=?";

	public BZDCodeSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[18];
	}

	protected Schema newInstance(){
		return new BZDCodeSchema();
	}

	protected SchemaSet newSet(){
		return new BZDCodeSet();
	}

	public BZDCodeSet query() {
		return query(null, -1, -1);
	}

	public BZDCodeSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZDCodeSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZDCodeSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZDCodeSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){CodeType = (String)v;return;}
		if (i == 1){ParentCode = (String)v;return;}
		if (i == 2){CodeValue = (String)v;return;}
		if (i == 3){CodeName = (String)v;return;}
		if (i == 4){if(v==null){CodeOrder = null;}else{CodeOrder = new Long(v.toString());}return;}
		if (i == 5){Prop1 = (String)v;return;}
		if (i == 6){Prop2 = (String)v;return;}
		if (i == 7){Prop3 = (String)v;return;}
		if (i == 8){Prop4 = (String)v;return;}
		if (i == 9){Memo = (String)v;return;}
		if (i == 10){AddTime = (Date)v;return;}
		if (i == 11){AddUser = (String)v;return;}
		if (i == 12){ModifyTime = (Date)v;return;}
		if (i == 13){ModifyUser = (String)v;return;}
		if (i == 14){BackupNo = (String)v;return;}
		if (i == 15){BackupOperator = (String)v;return;}
		if (i == 16){BackupTime = (Date)v;return;}
		if (i == 17){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return CodeType;}
		if (i == 1){return ParentCode;}
		if (i == 2){return CodeValue;}
		if (i == 3){return CodeName;}
		if (i == 4){return CodeOrder;}
		if (i == 5){return Prop1;}
		if (i == 6){return Prop2;}
		if (i == 7){return Prop3;}
		if (i == 8){return Prop4;}
		if (i == 9){return Memo;}
		if (i == 10){return AddTime;}
		if (i == 11){return AddUser;}
		if (i == 12){return ModifyTime;}
		if (i == 13){return ModifyUser;}
		if (i == 14){return BackupNo;}
		if (i == 15){return BackupOperator;}
		if (i == 16){return BackupTime;}
		if (i == 17){return BackupMemo;}
		return null;
	}

	/**
	* ��ȡ�ֶ�CodeType��ֵ�����ֶε�<br>
	* �ֶ����� :�������<br>
	* �������� :varchar(40)<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public String getCodeType() {
		return CodeType;
	}

	/**
	* �����ֶ�CodeType��ֵ�����ֶε�<br>
	* �ֶ����� :�������<br>
	* �������� :varchar(40)<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setCodeType(String codeType) {
		this.CodeType = codeType;
    }

	/**
	* ��ȡ�ֶ�ParentCode��ֵ�����ֶε�<br>
	* �ֶ����� :���븸��<br>
	* �������� :varchar(40)<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public String getParentCode() {
		return ParentCode;
	}

	/**
	* �����ֶ�ParentCode��ֵ�����ֶε�<br>
	* �ֶ����� :���븸��<br>
	* �������� :varchar(40)<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setParentCode(String parentCode) {
		this.ParentCode = parentCode;
    }

	/**
	* ��ȡ�ֶ�CodeValue��ֵ�����ֶε�<br>
	* �ֶ����� :����ֵ<br>
	* �������� :varchar(40)<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public String getCodeValue() {
		return CodeValue;
	}

	/**
	* �����ֶ�CodeValue��ֵ�����ֶε�<br>
	* �ֶ����� :����ֵ<br>
	* �������� :varchar(40)<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setCodeValue(String codeValue) {
		this.CodeValue = codeValue;
    }

	/**
	* ��ȡ�ֶ�CodeName��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getCodeName() {
		return CodeName;
	}

	/**
	* �����ֶ�CodeName��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setCodeName(String codeName) {
		this.CodeName = codeName;
    }

	/**
	* ��ȡ�ֶ�CodeOrder��ֵ�����ֶε�<br>
	* �ֶ����� :����˳��<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public long getCodeOrder() {
		if(CodeOrder==null){return 0;}
		return CodeOrder.longValue();
	}

	/**
	* �����ֶ�CodeOrder��ֵ�����ֶε�<br>
	* �ֶ����� :����˳��<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setCodeOrder(long codeOrder) {
		this.CodeOrder = new Long(codeOrder);
    }

	/**
	* �����ֶ�CodeOrder��ֵ�����ֶε�<br>
	* �ֶ����� :����˳��<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setCodeOrder(String codeOrder) {
		if (codeOrder == null){
			this.CodeOrder = null;
			return;
		}
		this.CodeOrder = new Long(codeOrder);
    }

	/**
	* ��ȡ�ֶ�Prop1��ֵ�����ֶε�<br>
	* �ֶ����� :��������1<br>
	* �������� :varchar(40)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* �����ֶ�Prop1��ֵ�����ֶε�<br>
	* �ֶ����� :��������1<br>
	* �������� :varchar(40)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* ��ȡ�ֶ�Prop2��ֵ�����ֶε�<br>
	* �ֶ����� :��������2<br>
	* �������� :varchar(40)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* �����ֶ�Prop2��ֵ�����ֶε�<br>
	* �ֶ����� :��������2<br>
	* �������� :varchar(40)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

	/**
	* ��ȡ�ֶ�Prop3��ֵ�����ֶε�<br>
	* �ֶ����� :��������3<br>
	* �������� :varchar(40)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp3() {
		return Prop3;
	}

	/**
	* �����ֶ�Prop3��ֵ�����ֶε�<br>
	* �ֶ����� :��������3<br>
	* �������� :varchar(40)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp3(String prop3) {
		this.Prop3 = prop3;
    }

	/**
	* ��ȡ�ֶ�Prop4��ֵ�����ֶε�<br>
	* �ֶ����� :��������4<br>
	* �������� :varchar(40)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	* ��ע��Ϣ :<br>
	'S' ������봦��<br>
	*/
	public String getProp4() {
		return Prop4;
	}

	/**
	* �����ֶ�Prop4��ֵ�����ֶε�<br>
	* �ֶ����� :��������4<br>
	* �������� :varchar(40)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	* ��ע��Ϣ :<br>
	'S' ������봦��<br>
	*/
	public void setProp4(String prop4) {
		this.Prop4 = prop4;
    }

	/**
	* ��ȡ�ֶ�Memo��ֵ�����ֶε�<br>
	* �ֶ����� :��ע<br>
	* �������� :varchar(400)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getMemo() {
		return Memo;
	}

	/**
	* �����ֶ�Memo��ֵ�����ֶε�<br>
	* �ֶ����� :��ע<br>
	* �������� :varchar(400)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setMemo(String memo) {
		this.Memo = memo;
    }

	/**
	* ��ȡ�ֶ�AddTime��ֵ�����ֶε�<br>
	* �ֶ����� :����ʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public Date getAddTime() {
		return AddTime;
	}

	/**
	* �����ֶ�AddTime��ֵ�����ֶε�<br>
	* �ֶ����� :����ʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setAddTime(Date addTime) {
		this.AddTime = addTime;
    }

	/**
	* ��ȡ�ֶ�AddUser��ֵ�����ֶε�<br>
	* �ֶ����� :������<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getAddUser() {
		return AddUser;
	}

	/**
	* �����ֶ�AddUser��ֵ�����ֶε�<br>
	* �ֶ����� :������<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setAddUser(String addUser) {
		this.AddUser = addUser;
    }

	/**
	* ��ȡ�ֶ�ModifyTime��ֵ�����ֶε�<br>
	* �ֶ����� :�޸�ʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public Date getModifyTime() {
		return ModifyTime;
	}

	/**
	* �����ֶ�ModifyTime��ֵ�����ֶε�<br>
	* �ֶ����� :�޸�ʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setModifyTime(Date modifyTime) {
		this.ModifyTime = modifyTime;
    }

	/**
	* ��ȡ�ֶ�ModifyUser��ֵ�����ֶε�<br>
	* �ֶ����� :�޸���<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getModifyUser() {
		return ModifyUser;
	}

	/**
	* �����ֶ�ModifyUser��ֵ�����ֶε�<br>
	* �ֶ����� :�޸���<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setModifyUser(String modifyUser) {
		this.ModifyUser = modifyUser;
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