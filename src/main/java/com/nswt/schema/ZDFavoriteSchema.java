package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * �����ƣ��ղؼ�<br>
 * ����룺ZDFavorite<br>
 * ��������UserName, DocID<br>
 */
public class ZDFavoriteSchema extends Schema {
	private String UserName;

	private Long DocID;

	private String AddUser;

	private Date AddTime;

	private String ModifyUser;

	private Date ModifyTime;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("UserName", DataColumn.STRING, 0, 200 , 0 , true , true),
		new SchemaColumn("DocID", DataColumn.LONG, 1, 0 , 0 , true , true),
		new SchemaColumn("AddUser", DataColumn.STRING, 2, 200 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 3, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 4, 200 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 5, 0 , 0 , false , false)
	};

	public static final String _TableCode = "ZDFavorite";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into ZDFavorite values(?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZDFavorite set UserName=?,DocID=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where UserName=? and DocID=?";

	protected static final String _DeleteSQL = "delete from ZDFavorite  where UserName=? and DocID=?";

	protected static final String _FillAllSQL = "select * from ZDFavorite  where UserName=? and DocID=?";

	public ZDFavoriteSchema(){
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
		return new ZDFavoriteSchema();
	}

	protected SchemaSet newSet(){
		return new ZDFavoriteSet();
	}

	public ZDFavoriteSet query() {
		return query(null, -1, -1);
	}

	public ZDFavoriteSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZDFavoriteSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZDFavoriteSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZDFavoriteSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){UserName = (String)v;return;}
		if (i == 1){if(v==null){DocID = null;}else{DocID = new Long(v.toString());}return;}
		if (i == 2){AddUser = (String)v;return;}
		if (i == 3){AddTime = (Date)v;return;}
		if (i == 4){ModifyUser = (String)v;return;}
		if (i == 5){ModifyTime = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return UserName;}
		if (i == 1){return DocID;}
		if (i == 2){return AddUser;}
		if (i == 3){return AddTime;}
		if (i == 4){return ModifyUser;}
		if (i == 5){return ModifyTime;}
		return null;
	}

	/**
	* ��ȡ�ֶ�UserName��ֵ�����ֶε�<br>
	* �ֶ����� :��Ա����<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public String getUserName() {
		return UserName;
	}

	/**
	* �����ֶ�UserName��ֵ�����ֶε�<br>
	* �ֶ����� :��Ա����<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setUserName(String userName) {
		this.UserName = userName;
    }

	/**
	* ��ȡ�ֶ�DocID��ֵ�����ֶε�<br>
	* �ֶ����� :�ĵ�ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public long getDocID() {
		if(DocID==null){return 0;}
		return DocID.longValue();
	}

	/**
	* �����ֶ�DocID��ֵ�����ֶε�<br>
	* �ֶ����� :�ĵ�ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setDocID(long docID) {
		this.DocID = new Long(docID);
    }

	/**
	* �����ֶ�DocID��ֵ�����ֶε�<br>
	* �ֶ����� :�ĵ�ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setDocID(String docID) {
		if (docID == null){
			this.DocID = null;
			return;
		}
		this.DocID = new Long(docID);
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