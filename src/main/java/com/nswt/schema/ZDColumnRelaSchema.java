package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * �����ƣ��Զ����ֶι���<br>
 * ����룺ZDColumnRela<br>
 * ��������ID<br>
 */
public class ZDColumnRelaSchema extends Schema {
	private Long ID;

	private Long ColumnID;

	private String ColumnCode;

	private String RelaType;

	private String RelaID;

	private String AddUser;

	private Date AddTime;

	private String ModifyUser;

	private Date ModifyTime;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 0 , 0 , true , true),
		new SchemaColumn("ColumnID", DataColumn.LONG, 1, 0 , 0 , true , false),
		new SchemaColumn("ColumnCode", DataColumn.STRING, 2, 100 , 0 , true , false),
		new SchemaColumn("RelaType", DataColumn.STRING, 3, 2 , 0 , false , false),
		new SchemaColumn("RelaID", DataColumn.STRING, 4, 100 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 5, 200 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 6, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 7, 200 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 8, 0 , 0 , false , false)
	};

	public static final String _TableCode = "ZDColumnRela";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into ZDColumnRela values(?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZDColumnRela set ID=?,ColumnID=?,ColumnCode=?,RelaType=?,RelaID=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";

	protected static final String _DeleteSQL = "delete from ZDColumnRela  where ID=?";

	protected static final String _FillAllSQL = "select * from ZDColumnRela  where ID=?";

	public ZDColumnRelaSchema(){
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
		return new ZDColumnRelaSchema();
	}

	protected SchemaSet newSet(){
		return new ZDColumnRelaSet();
	}

	public ZDColumnRelaSet query() {
		return query(null, -1, -1);
	}

	public ZDColumnRelaSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZDColumnRelaSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZDColumnRelaSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZDColumnRelaSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){ColumnID = null;}else{ColumnID = new Long(v.toString());}return;}
		if (i == 2){ColumnCode = (String)v;return;}
		if (i == 3){RelaType = (String)v;return;}
		if (i == 4){RelaID = (String)v;return;}
		if (i == 5){AddUser = (String)v;return;}
		if (i == 6){AddTime = (Date)v;return;}
		if (i == 7){ModifyUser = (String)v;return;}
		if (i == 8){ModifyTime = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return ColumnID;}
		if (i == 2){return ColumnCode;}
		if (i == 3){return RelaType;}
		if (i == 4){return RelaID;}
		if (i == 5){return AddUser;}
		if (i == 6){return AddTime;}
		if (i == 7){return ModifyUser;}
		if (i == 8){return ModifyTime;}
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

}