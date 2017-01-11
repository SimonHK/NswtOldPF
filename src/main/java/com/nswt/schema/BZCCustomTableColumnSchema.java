package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * �����ƣ��Զ������ݱ�����Ϣ����<br>
 * ����룺BZCCustomTableColumn<br>
 * ��������ID, BackupNo<br>
 */
public class BZCCustomTableColumnSchema extends Schema {
	private Long ID;

	private Long TableID;

	private String Code;

	private String Name;

	private String DataType;

	private Long Length;

	private String isPrimaryKey;

	private String isMandatory;

	private String DefaultValue;

	private String InputType;

	private String CSSStyle;

	private Integer MaxLength;

	private String ListOptions;

	private String isAutoID;

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
		new SchemaColumn("TableID", DataColumn.LONG, 1, 0 , 0 , false , false),
		new SchemaColumn("Code", DataColumn.STRING, 2, 50 , 0 , true , false),
		new SchemaColumn("Name", DataColumn.STRING, 3, 100 , 0 , true , false),
		new SchemaColumn("DataType", DataColumn.STRING, 4, 50 , 0 , true , false),
		new SchemaColumn("Length", DataColumn.LONG, 5, 0 , 0 , false , false),
		new SchemaColumn("isPrimaryKey", DataColumn.STRING, 6, 2 , 0 , false , false),
		new SchemaColumn("isMandatory", DataColumn.STRING, 7, 2 , 0 , true , false),
		new SchemaColumn("DefaultValue", DataColumn.STRING, 8, 50 , 0 , false , false),
		new SchemaColumn("InputType", DataColumn.STRING, 9, 2 , 0 , false , false),
		new SchemaColumn("CSSStyle", DataColumn.STRING, 10, 200 , 0 , false , false),
		new SchemaColumn("MaxLength", DataColumn.INTEGER, 11, 0 , 0 , false , false),
		new SchemaColumn("ListOptions", DataColumn.CLOB, 12, 0 , 0 , false , false),
		new SchemaColumn("isAutoID", DataColumn.STRING, 13, 2 , 0 , false , false),
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

	public static final String _TableCode = "BZCCustomTableColumn";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into BZCCustomTableColumn values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZCCustomTableColumn set ID=?,TableID=?,Code=?,Name=?,DataType=?,Length=?,isPrimaryKey=?,isMandatory=?,DefaultValue=?,InputType=?,CSSStyle=?,MaxLength=?,ListOptions=?,isAutoID=?,Prop1=?,Prop2=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZCCustomTableColumn  where ID=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZCCustomTableColumn  where ID=? and BackupNo=?";

	public BZCCustomTableColumnSchema(){
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
		return new BZCCustomTableColumnSchema();
	}

	protected SchemaSet newSet(){
		return new BZCCustomTableColumnSet();
	}

	public BZCCustomTableColumnSet query() {
		return query(null, -1, -1);
	}

	public BZCCustomTableColumnSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZCCustomTableColumnSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZCCustomTableColumnSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZCCustomTableColumnSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){TableID = null;}else{TableID = new Long(v.toString());}return;}
		if (i == 2){Code = (String)v;return;}
		if (i == 3){Name = (String)v;return;}
		if (i == 4){DataType = (String)v;return;}
		if (i == 5){if(v==null){Length = null;}else{Length = new Long(v.toString());}return;}
		if (i == 6){isPrimaryKey = (String)v;return;}
		if (i == 7){isMandatory = (String)v;return;}
		if (i == 8){DefaultValue = (String)v;return;}
		if (i == 9){InputType = (String)v;return;}
		if (i == 10){CSSStyle = (String)v;return;}
		if (i == 11){if(v==null){MaxLength = null;}else{MaxLength = new Integer(v.toString());}return;}
		if (i == 12){ListOptions = (String)v;return;}
		if (i == 13){isAutoID = (String)v;return;}
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
		if (i == 1){return TableID;}
		if (i == 2){return Code;}
		if (i == 3){return Name;}
		if (i == 4){return DataType;}
		if (i == 5){return Length;}
		if (i == 6){return isPrimaryKey;}
		if (i == 7){return isMandatory;}
		if (i == 8){return DefaultValue;}
		if (i == 9){return InputType;}
		if (i == 10){return CSSStyle;}
		if (i == 11){return MaxLength;}
		if (i == 12){return ListOptions;}
		if (i == 13){return isAutoID;}
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
	* ��ȡ�ֶ�TableID��ֵ�����ֶε�<br>
	* �ֶ����� :��ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getTableID() {
		if(TableID==null){return 0;}
		return TableID.longValue();
	}

	/**
	* �����ֶ�TableID��ֵ�����ֶε�<br>
	* �ֶ����� :��ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setTableID(long tableID) {
		this.TableID = new Long(tableID);
    }

	/**
	* �����ֶ�TableID��ֵ�����ֶε�<br>
	* �ֶ����� :��ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setTableID(String tableID) {
		if (tableID == null){
			this.TableID = null;
			return;
		}
		this.TableID = new Long(tableID);
    }

	/**
	* ��ȡ�ֶ�Code��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getCode() {
		return Code;
	}

	/**
	* �����ֶ�Code��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setCode(String code) {
		this.Code = code;
    }

	/**
	* ��ȡ�ֶ�Name��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getName() {
		return Name;
	}

	/**
	* �����ֶ�Name��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setName(String name) {
		this.Name = name;
    }

	/**
	* ��ȡ�ֶ�DataType��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getDataType() {
		return DataType;
	}

	/**
	* �����ֶ�DataType��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setDataType(String dataType) {
		this.DataType = dataType;
    }

	/**
	* ��ȡ�ֶ�Length��ֵ�����ֶε�<br>
	* �ֶ����� :���ݳ���<br>
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
	* �ֶ����� :���ݳ���<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setLength(long length) {
		this.Length = new Long(length);
    }

	/**
	* �����ֶ�Length��ֵ�����ֶε�<br>
	* �ֶ����� :���ݳ���<br>
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
	* ��ȡ�ֶ�isPrimaryKey��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ�����<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getIsPrimaryKey() {
		return isPrimaryKey;
	}

	/**
	* �����ֶ�isPrimaryKey��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ�����<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setIsPrimaryKey(String isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
    }

	/**
	* ��ȡ�ֶ�isMandatory��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ����<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getIsMandatory() {
		return isMandatory;
	}

	/**
	* �����ֶ�isMandatory��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ����<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setIsMandatory(String isMandatory) {
		this.isMandatory = isMandatory;
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
	* ��ȡ�ֶ�InputType��ֵ�����ֶε�<br>
	* �ֶ����� :������ʽ<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getInputType() {
		return InputType;
	}

	/**
	* �����ֶ�InputType��ֵ�����ֶε�<br>
	* �ֶ����� :������ʽ<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setInputType(String inputType) {
		this.InputType = inputType;
    }

	/**
	* ��ȡ�ֶ�CSSStyle��ֵ�����ֶε�<br>
	* �ֶ����� :CSS��ʽ<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getCSSStyle() {
		return CSSStyle;
	}

	/**
	* �����ֶ�CSSStyle��ֵ�����ֶε�<br>
	* �ֶ����� :CSS��ʽ<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCSSStyle(String cSSStyle) {
		this.CSSStyle = cSSStyle;
    }

	/**
	* ��ȡ�ֶ�MaxLength��ֵ�����ֶε�<br>
	* �ֶ����� :��󳤶�<br>
	* �������� :integer<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public int getMaxLength() {
		if(MaxLength==null){return 0;}
		return MaxLength.intValue();
	}

	/**
	* �����ֶ�MaxLength��ֵ�����ֶε�<br>
	* �ֶ����� :��󳤶�<br>
	* �������� :integer<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setMaxLength(int maxLength) {
		this.MaxLength = new Integer(maxLength);
    }

	/**
	* �����ֶ�MaxLength��ֵ�����ֶε�<br>
	* �ֶ����� :��󳤶�<br>
	* �������� :integer<br>
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
	* ��ȡ�ֶ�ListOptions��ֵ�����ֶε�<br>
	* �ֶ����� :������ѡ��<br>
	* �������� :text<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getListOptions() {
		return ListOptions;
	}

	/**
	* �����ֶ�ListOptions��ֵ�����ֶε�<br>
	* �ֶ����� :������ѡ��<br>
	* �������� :text<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setListOptions(String listOptions) {
		this.ListOptions = listOptions;
    }

	/**
	* ��ȡ�ֶ�isAutoID��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ��Զ����<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getIsAutoID() {
		return isAutoID;
	}

	/**
	* �����ֶ�isAutoID��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ��Զ����<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setIsAutoID(String isAutoID) {
		this.isAutoID = isAutoID;
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