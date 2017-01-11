package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * �����ƣ��Զ����ֶα���<br>
 * ����룺BZDColumn<br>
 * ��������ID, BackupNo<br>
 */
public class BZDColumnSchema extends Schema {
	private Long ID;

	private Long SiteID;

	private String Name;

	private String Code;

	private String VerifyType;

	private Integer MaxLength;

	private String InputType;

	private String DefaultValue;

	private String ListOption;

	private String HTML;

	private String IsMandatory;

	private Long OrderFlag;

	private Integer RowSize;

	private Integer ColSize;

	private String Prop1;

	private String Prop2;

	private String AddUser;

	private Date AddTime;

	private String ModifyUser;

	private Date ModifyTime;

	private String BackupNo;

	private String BackupOperator;

	private Date BackupTime;

	private String BackupMemo;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 0 , 0 , true , true),
		new SchemaColumn("SiteID", DataColumn.LONG, 1, 0 , 0 , true , false),
		new SchemaColumn("Name", DataColumn.STRING, 2, 100 , 0 , true , false),
		new SchemaColumn("Code", DataColumn.STRING, 3, 100 , 0 , true , false),
		new SchemaColumn("VerifyType", DataColumn.STRING, 4, 2 , 0 , true , false),
		new SchemaColumn("MaxLength", DataColumn.INTEGER, 5, 0 , 0 , false , false),
		new SchemaColumn("InputType", DataColumn.STRING, 6, 2 , 0 , true , false),
		new SchemaColumn("DefaultValue", DataColumn.STRING, 7, 50 , 0 , false , false),
		new SchemaColumn("ListOption", DataColumn.STRING, 8, 1000 , 0 , false , false),
		new SchemaColumn("HTML", DataColumn.CLOB, 9, 0 , 0 , false , false),
		new SchemaColumn("IsMandatory", DataColumn.STRING, 10, 1 , 0 , true , false),
		new SchemaColumn("OrderFlag", DataColumn.LONG, 11, 0 , 0 , false , false),
		new SchemaColumn("RowSize", DataColumn.INTEGER, 12, 0 , 0 , false , false),
		new SchemaColumn("ColSize", DataColumn.INTEGER, 13, 0 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 14, 50 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 15, 50 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 16, 200 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 17, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 18, 200 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 19, 0 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 20, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 21, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 22, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 23, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZDColumn";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into BZDColumn values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZDColumn set ID=?,SiteID=?,Name=?,Code=?,VerifyType=?,MaxLength=?,InputType=?,DefaultValue=?,ListOption=?,HTML=?,IsMandatory=?,OrderFlag=?,RowSize=?,ColSize=?,Prop1=?,Prop2=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZDColumn  where ID=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZDColumn  where ID=? and BackupNo=?";

	public BZDColumnSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[24];
	}

	protected Schema newInstance(){
		return new BZDColumnSchema();
	}

	protected SchemaSet newSet(){
		return new BZDColumnSet();
	}

	public BZDColumnSet query() {
		return query(null, -1, -1);
	}

	public BZDColumnSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZDColumnSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZDColumnSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZDColumnSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 2){Name = (String)v;return;}
		if (i == 3){Code = (String)v;return;}
		if (i == 4){VerifyType = (String)v;return;}
		if (i == 5){if(v==null){MaxLength = null;}else{MaxLength = new Integer(v.toString());}return;}
		if (i == 6){InputType = (String)v;return;}
		if (i == 7){DefaultValue = (String)v;return;}
		if (i == 8){ListOption = (String)v;return;}
		if (i == 9){HTML = (String)v;return;}
		if (i == 10){IsMandatory = (String)v;return;}
		if (i == 11){if(v==null){OrderFlag = null;}else{OrderFlag = new Long(v.toString());}return;}
		if (i == 12){if(v==null){RowSize = null;}else{RowSize = new Integer(v.toString());}return;}
		if (i == 13){if(v==null){ColSize = null;}else{ColSize = new Integer(v.toString());}return;}
		if (i == 14){Prop1 = (String)v;return;}
		if (i == 15){Prop2 = (String)v;return;}
		if (i == 16){AddUser = (String)v;return;}
		if (i == 17){AddTime = (Date)v;return;}
		if (i == 18){ModifyUser = (String)v;return;}
		if (i == 19){ModifyTime = (Date)v;return;}
		if (i == 20){BackupNo = (String)v;return;}
		if (i == 21){BackupOperator = (String)v;return;}
		if (i == 22){BackupTime = (Date)v;return;}
		if (i == 23){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return SiteID;}
		if (i == 2){return Name;}
		if (i == 3){return Code;}
		if (i == 4){return VerifyType;}
		if (i == 5){return MaxLength;}
		if (i == 6){return InputType;}
		if (i == 7){return DefaultValue;}
		if (i == 8){return ListOption;}
		if (i == 9){return HTML;}
		if (i == 10){return IsMandatory;}
		if (i == 11){return OrderFlag;}
		if (i == 12){return RowSize;}
		if (i == 13){return ColSize;}
		if (i == 14){return Prop1;}
		if (i == 15){return Prop2;}
		if (i == 16){return AddUser;}
		if (i == 17){return AddTime;}
		if (i == 18){return ModifyUser;}
		if (i == 19){return ModifyTime;}
		if (i == 20){return BackupNo;}
		if (i == 21){return BackupOperator;}
		if (i == 22){return BackupTime;}
		if (i == 23){return BackupMemo;}
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
	* ��ȡ�ֶ�SiteID��ֵ�����ֶε�<br>
	* �ֶ����� :վ��ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public long getSiteID() {
		if(SiteID==null){return 0;}
		return SiteID.longValue();
	}

	/**
	* �����ֶ�SiteID��ֵ�����ֶε�<br>
	* �ֶ����� :վ��ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setSiteID(long siteID) {
		this.SiteID = new Long(siteID);
    }

	/**
	* �����ֶ�SiteID��ֵ�����ֶε�<br>
	* �ֶ����� :վ��ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setSiteID(String siteID) {
		if (siteID == null){
			this.SiteID = null;
			return;
		}
		this.SiteID = new Long(siteID);
    }

	/**
	* ��ȡ�ֶ�Name��ֵ�����ֶε�<br>
	* �ֶ����� :�ֶ�����<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getName() {
		return Name;
	}

	/**
	* �����ֶ�Name��ֵ�����ֶε�<br>
	* �ֶ����� :�ֶ�����<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setName(String name) {
		this.Name = name;
    }

	/**
	* ��ȡ�ֶ�Code��ֵ�����ֶε�<br>
	* �ֶ����� :�ֶδ���<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getCode() {
		return Code;
	}

	/**
	* �����ֶ�Code��ֵ�����ֶε�<br>
	* �ֶ����� :�ֶδ���<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setCode(String code) {
		this.Code = code;
    }

	/**
	* ��ȡ�ֶ�VerifyType��ֵ�����ֶε�<br>
	* �ֶ����� :У������<br>
	* �������� :char(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	* ��ע��Ϣ :<br>
	int-���� long-������ string-�ַ��� float-���� double-˫�ֽڸ��� datetime-���� text-�ı�<br>
	*/
	public String getVerifyType() {
		return VerifyType;
	}

	/**
	* �����ֶ�VerifyType��ֵ�����ֶε�<br>
	* �ֶ����� :У������<br>
	* �������� :char(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	* ��ע��Ϣ :<br>
	int-���� long-������ string-�ַ��� float-���� double-˫�ֽڸ��� datetime-���� text-�ı�<br>
	*/
	public void setVerifyType(String verifyType) {
		this.VerifyType = verifyType;
    }

	/**
	* ��ȡ�ֶ�MaxLength��ֵ�����ֶε�<br>
	* �ֶ����� :�������<br>
	* �������� :smallint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public int getMaxLength() {
		if(MaxLength==null){return 0;}
		return MaxLength.intValue();
	}

	/**
	* �����ֶ�MaxLength��ֵ�����ֶε�<br>
	* �ֶ����� :�������<br>
	* �������� :smallint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setMaxLength(int maxLength) {
		this.MaxLength = new Integer(maxLength);
    }

	/**
	* �����ֶ�MaxLength��ֵ�����ֶε�<br>
	* �ֶ����� :�������<br>
	* �������� :smallint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setMaxLength(String maxLength) {
		if (maxLength == null){
			this.MaxLength = null;
			return;
		}
		this.MaxLength = new Integer(maxLength);
    }

	/**
	* ��ȡ�ֶ�InputType��ֵ�����ֶε�<br>
	* �ֶ����� :������ʽ<br>
	* �������� :char(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getInputType() {
		return InputType;
	}

	/**
	* �����ֶ�InputType��ֵ�����ֶε�<br>
	* �ֶ����� :������ʽ<br>
	* �������� :char(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setInputType(String inputType) {
		this.InputType = inputType;
    }

	/**
	* ��ȡ�ֶ�DefaultValue��ֵ�����ֶε�<br>
	* �ֶ����� :Ĭ��ֵ<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getDefaultValue() {
		return DefaultValue;
	}

	/**
	* �����ֶ�DefaultValue��ֵ�����ֶε�<br>
	* �ֶ����� :Ĭ��ֵ<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setDefaultValue(String defaultValue) {
		this.DefaultValue = defaultValue;
    }

	/**
	* ��ȡ�ֶ�ListOption��ֵ�����ֶε�<br>
	* �ֶ����� :�б�ѡ��<br>
	* �������� :varchar(1000)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getListOption() {
		return ListOption;
	}

	/**
	* �����ֶ�ListOption��ֵ�����ֶε�<br>
	* �ֶ����� :�б�ѡ��<br>
	* �������� :varchar(1000)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setListOption(String listOption) {
		this.ListOption = listOption;
    }

	/**
	* ��ȡ�ֶ�HTML��ֵ�����ֶε�<br>
	* �ֶ����� :HTML����<br>
	* �������� :text<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getHTML() {
		return HTML;
	}

	/**
	* �����ֶ�HTML��ֵ�����ֶε�<br>
	* �ֶ����� :HTML����<br>
	* �������� :text<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setHTML(String hTML) {
		this.HTML = hTML;
    }

	/**
	* ��ȡ�ֶ�IsMandatory��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ����<br>
	* �������� :char(1)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getIsMandatory() {
		return IsMandatory;
	}

	/**
	* �����ֶ�IsMandatory��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ����<br>
	* �������� :char(1)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setIsMandatory(String isMandatory) {
		this.IsMandatory = isMandatory;
    }

	/**
	* ��ȡ�ֶ�OrderFlag��ֵ�����ֶε�<br>
	* �ֶ����� :�����<br>
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
	* �ֶ����� :�����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setOrderFlag(long orderFlag) {
		this.OrderFlag = new Long(orderFlag);
    }

	/**
	* �����ֶ�OrderFlag��ֵ�����ֶε�<br>
	* �ֶ����� :�����<br>
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
	* ��ȡ�ֶ�RowSize��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :smallint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public int getRowSize() {
		if(RowSize==null){return 0;}
		return RowSize.intValue();
	}

	/**
	* �����ֶ�RowSize��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :smallint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setRowSize(int rowSize) {
		this.RowSize = new Integer(rowSize);
    }

	/**
	* �����ֶ�RowSize��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :smallint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setRowSize(String rowSize) {
		if (rowSize == null){
			this.RowSize = null;
			return;
		}
		this.RowSize = new Integer(rowSize);
    }

	/**
	* ��ȡ�ֶ�ColSize��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :smallint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public int getColSize() {
		if(ColSize==null){return 0;}
		return ColSize.intValue();
	}

	/**
	* �����ֶ�ColSize��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :smallint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setColSize(int colSize) {
		this.ColSize = new Integer(colSize);
    }

	/**
	* �����ֶ�ColSize��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :smallint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setColSize(String colSize) {
		if (colSize == null){
			this.ColSize = null;
			return;
		}
		this.ColSize = new Integer(colSize);
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

	/**
	* ��ȡ�ֶ�AddUser��ֵ�����ֶε�<br>
	* �ֶ����� :�����<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getAddUser() {
		return AddUser;
	}

	/**
	* �����ֶ�AddUser��ֵ�����ֶε�<br>
	* �ֶ����� :�����<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setAddUser(String addUser) {
		this.AddUser = addUser;
    }

	/**
	* ��ȡ�ֶ�AddTime��ֵ�����ֶε�<br>
	* �ֶ����� :���ʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public Date getAddTime() {
		return AddTime;
	}

	/**
	* �����ֶ�AddTime��ֵ�����ֶε�<br>
	* �ֶ����� :���ʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setAddTime(Date addTime) {
		this.AddTime = addTime;
    }

	/**
	* ��ȡ�ֶ�ModifyUser��ֵ�����ֶε�<br>
	* �ֶ����� :����޸���<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getModifyUser() {
		return ModifyUser;
	}

	/**
	* �����ֶ�ModifyUser��ֵ�����ֶε�<br>
	* �ֶ����� :����޸���<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setModifyUser(String modifyUser) {
		this.ModifyUser = modifyUser;
    }

	/**
	* ��ȡ�ֶ�ModifyTime��ֵ�����ֶε�<br>
	* �ֶ����� :����޸�ʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public Date getModifyTime() {
		return ModifyTime;
	}

	/**
	* �����ֶ�ModifyTime��ֵ�����ֶε�<br>
	* �ֶ����� :����޸�ʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setModifyTime(Date modifyTime) {
		this.ModifyTime = modifyTime;
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