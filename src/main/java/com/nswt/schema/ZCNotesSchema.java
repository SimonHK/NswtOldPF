package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * �����ƣ����˱�����<br>
 * ����룺ZCNotes<br>
 * ��������ID<br>
 */
public class ZCNotesSchema extends Schema {
	private Long ID;

	private String Title;

	private String Content;

	private Date NoteTime;

	private String Prop1;

	private String Prop2;

	private String AddUser;

	private Date AddTime;

	private Date ModifyTime;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 0 , 0 , true , true),
		new SchemaColumn("Title", DataColumn.STRING, 1, 100 , 0 , true , false),
		new SchemaColumn("Content", DataColumn.STRING, 2, 1000 , 0 , true , false),
		new SchemaColumn("NoteTime", DataColumn.DATETIME, 3, 0 , 0 , true , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 4, 50 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 5, 50 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 6, 200 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 7, 0 , 0 , true , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 8, 0 , 0 , false , false)
	};

	public static final String _TableCode = "ZCNotes";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into ZCNotes values(?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZCNotes set ID=?,Title=?,Content=?,NoteTime=?,Prop1=?,Prop2=?,AddUser=?,AddTime=?,ModifyTime=? where ID=?";

	protected static final String _DeleteSQL = "delete from ZCNotes  where ID=?";

	protected static final String _FillAllSQL = "select * from ZCNotes  where ID=?";

	public ZCNotesSchema(){
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
		return new ZCNotesSchema();
	}

	protected SchemaSet newSet(){
		return new ZCNotesSet();
	}

	public ZCNotesSet query() {
		return query(null, -1, -1);
	}

	public ZCNotesSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZCNotesSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZCNotesSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZCNotesSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){Title = (String)v;return;}
		if (i == 2){Content = (String)v;return;}
		if (i == 3){NoteTime = (Date)v;return;}
		if (i == 4){Prop1 = (String)v;return;}
		if (i == 5){Prop2 = (String)v;return;}
		if (i == 6){AddUser = (String)v;return;}
		if (i == 7){AddTime = (Date)v;return;}
		if (i == 8){ModifyTime = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return Title;}
		if (i == 2){return Content;}
		if (i == 3){return NoteTime;}
		if (i == 4){return Prop1;}
		if (i == 5){return Prop2;}
		if (i == 6){return AddUser;}
		if (i == 7){return AddTime;}
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
	* ��ȡ�ֶ�Title��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getTitle() {
		return Title;
	}

	/**
	* �����ֶ�Title��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setTitle(String title) {
		this.Title = title;
    }

	/**
	* ��ȡ�ֶ�Content��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :varchar(1000)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getContent() {
		return Content;
	}

	/**
	* �����ֶ�Content��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :varchar(1000)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setContent(String content) {
		this.Content = content;
    }

	/**
	* ��ȡ�ֶ�NoteTime��ֵ�����ֶε�<br>
	* �ֶ����� :����ʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public Date getNoteTime() {
		return NoteTime;
	}

	/**
	* �����ֶ�NoteTime��ֵ�����ֶε�<br>
	* �ֶ����� :����ʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setNoteTime(Date noteTime) {
		this.NoteTime = noteTime;
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