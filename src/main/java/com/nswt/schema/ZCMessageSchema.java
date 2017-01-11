package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * �����ƣ�����Ϣ<br>
 * ����룺ZCMessage<br>
 * ��������ID<br>
 */
public class ZCMessageSchema extends Schema {
	private Long ID;

	private String FromUser;

	private String ToUser;

	private String Box;

	private Long ReadFlag;

	private Long PopFlag;

	private String Subject;

	private String Content;

	private Date AddTime;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 0 , 0 , true , true),
		new SchemaColumn("FromUser", DataColumn.STRING, 1, 50 , 0 , false , false),
		new SchemaColumn("ToUser", DataColumn.STRING, 2, 50 , 0 , false , false),
		new SchemaColumn("Box", DataColumn.STRING, 3, 10 , 0 , false , false),
		new SchemaColumn("ReadFlag", DataColumn.LONG, 4, 0 , 0 , false , false),
		new SchemaColumn("PopFlag", DataColumn.LONG, 5, 0 , 0 , false , false),
		new SchemaColumn("Subject", DataColumn.STRING, 6, 500 , 0 , false , false),
		new SchemaColumn("Content", DataColumn.CLOB, 7, 0 , 0 , false , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 8, 0 , 0 , false , false)
	};

	public static final String _TableCode = "ZCMessage";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into ZCMessage values(?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZCMessage set ID=?,FromUser=?,ToUser=?,Box=?,ReadFlag=?,PopFlag=?,Subject=?,Content=?,AddTime=? where ID=?";

	protected static final String _DeleteSQL = "delete from ZCMessage  where ID=?";

	protected static final String _FillAllSQL = "select * from ZCMessage  where ID=?";

	public ZCMessageSchema(){
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
		return new ZCMessageSchema();
	}

	protected SchemaSet newSet(){
		return new ZCMessageSet();
	}

	public ZCMessageSet query() {
		return query(null, -1, -1);
	}

	public ZCMessageSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZCMessageSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZCMessageSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZCMessageSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){FromUser = (String)v;return;}
		if (i == 2){ToUser = (String)v;return;}
		if (i == 3){Box = (String)v;return;}
		if (i == 4){if(v==null){ReadFlag = null;}else{ReadFlag = new Long(v.toString());}return;}
		if (i == 5){if(v==null){PopFlag = null;}else{PopFlag = new Long(v.toString());}return;}
		if (i == 6){Subject = (String)v;return;}
		if (i == 7){Content = (String)v;return;}
		if (i == 8){AddTime = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return FromUser;}
		if (i == 2){return ToUser;}
		if (i == 3){return Box;}
		if (i == 4){return ReadFlag;}
		if (i == 5){return PopFlag;}
		if (i == 6){return Subject;}
		if (i == 7){return Content;}
		if (i == 8){return AddTime;}
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
	* ��ȡ�ֶ�FromUser��ֵ�����ֶε�<br>
	* �ֶ����� :�����û�<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getFromUser() {
		return FromUser;
	}

	/**
	* �����ֶ�FromUser��ֵ�����ֶε�<br>
	* �ֶ����� :�����û�<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setFromUser(String fromUser) {
		this.FromUser = fromUser;
    }

	/**
	* ��ȡ�ֶ�ToUser��ֵ�����ֶε�<br>
	* �ֶ����� :�����û�<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getToUser() {
		return ToUser;
	}

	/**
	* �����ֶ�ToUser��ֵ�����ֶε�<br>
	* �ֶ����� :�����û�<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setToUser(String toUser) {
		this.ToUser = toUser;
    }

	/**
	* ��ȡ�ֶ�Box��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :varchar(10)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	* ��ע��Ϣ :<br>
	inbox �ռ���<br>
	outbox ������<br>
	draft �ݸ�<br>
	*/
	public String getBox() {
		return Box;
	}

	/**
	* �����ֶ�Box��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :varchar(10)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	* ��ע��Ϣ :<br>
	inbox �ռ���<br>
	outbox ������<br>
	draft �ݸ�<br>
	*/
	public void setBox(String box) {
		this.Box = box;
    }

	/**
	* ��ȡ�ֶ�ReadFlag��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ��Ķ�<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	* ��ע��Ϣ :<br>
	0 δ�Ķ�<br>
	1 ���Ķ�<br>
	*/
	public long getReadFlag() {
		if(ReadFlag==null){return 0;}
		return ReadFlag.longValue();
	}

	/**
	* �����ֶ�ReadFlag��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ��Ķ�<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	* ��ע��Ϣ :<br>
	0 δ�Ķ�<br>
	1 ���Ķ�<br>
	*/
	public void setReadFlag(long readFlag) {
		this.ReadFlag = new Long(readFlag);
    }

	/**
	* �����ֶ�ReadFlag��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ��Ķ�<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	* ��ע��Ϣ :<br>
	0 δ�Ķ�<br>
	1 ���Ķ�<br>
	*/
	public void setReadFlag(String readFlag) {
		if (readFlag == null){
			this.ReadFlag = null;
			return;
		}
		this.ReadFlag = new Long(readFlag);
    }

	/**
	* ��ȡ�ֶ�PopFlag��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ񼺵�����ʾ��<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getPopFlag() {
		if(PopFlag==null){return 0;}
		return PopFlag.longValue();
	}

	/**
	* �����ֶ�PopFlag��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ񼺵�����ʾ��<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setPopFlag(long popFlag) {
		this.PopFlag = new Long(popFlag);
    }

	/**
	* �����ֶ�PopFlag��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ񼺵�����ʾ��<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setPopFlag(String popFlag) {
		if (popFlag == null){
			this.PopFlag = null;
			return;
		}
		this.PopFlag = new Long(popFlag);
    }

	/**
	* ��ȡ�ֶ�Subject��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :varchar(500)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getSubject() {
		return Subject;
	}

	/**
	* �����ֶ�Subject��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :varchar(500)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setSubject(String subject) {
		this.Subject = subject;
    }

	/**
	* ��ȡ�ֶ�Content��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :text<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getContent() {
		return Content;
	}

	/**
	* �����ֶ�Content��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :text<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setContent(String content) {
		this.Content = content;
    }

	/**
	* ��ȡ�ֶ�AddTime��ֵ�����ֶε�<br>
	* �ֶ����� :���ʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public Date getAddTime() {
		return AddTime;
	}

	/**
	* �����ֶ�AddTime��ֵ�����ֶε�<br>
	* �ֶ����� :���ʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAddTime(Date addTime) {
		this.AddTime = addTime;
    }

}