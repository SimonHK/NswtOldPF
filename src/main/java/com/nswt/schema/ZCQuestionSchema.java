package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * �����ƣ������<br>
 * ����룺ZCQuestion<br>
 * ��������ID<br>
 */
public class ZCQuestionSchema extends Schema {
	private Long ID;

	private String QuestionInnerCode;

	private String Title;

	private String Content;

	private Integer ReplyCount;

	private String Status;

	private String IsCommend;

	private Date EndTime;

	private String Memo;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	private String Prop4;

	private Date AddTime;

	private String AddUser;

	private Date ModifyTime;

	private String ModifyUser;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 0 , 0 , true , true),
		new SchemaColumn("QuestionInnerCode", DataColumn.STRING, 1, 100 , 0 , false , false),
		new SchemaColumn("Title", DataColumn.STRING, 2, 250 , 0 , false , false),
		new SchemaColumn("Content", DataColumn.CLOB, 3, 0 , 0 , false , false),
		new SchemaColumn("ReplyCount", DataColumn.INTEGER, 4, 11 , 0 , false , false),
		new SchemaColumn("Status", DataColumn.STRING, 5, 1 , 0 , false , false),
		new SchemaColumn("IsCommend", DataColumn.STRING, 6, 1 , 0 , false , false),
		new SchemaColumn("EndTime", DataColumn.DATETIME, 7, 0 , 0 , false , false),
		new SchemaColumn("Memo", DataColumn.STRING, 8, 100 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 9, 100 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 10, 100 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 11, 100 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 12, 100 , 0 , false , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 13, 0 , 0 , true , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 14, 100 , 0 , true , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 15, 0 , 0 , false , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 16, 100 , 0 , false , false)
	};

	public static final String _TableCode = "ZCQuestion";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into ZCQuestion values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZCQuestion set ID=?,QuestionInnerCode=?,Title=?,Content=?,ReplyCount=?,Status=?,IsCommend=?,EndTime=?,Memo=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddTime=?,AddUser=?,ModifyTime=?,ModifyUser=? where ID=?";

	protected static final String _DeleteSQL = "delete from ZCQuestion  where ID=?";

	protected static final String _FillAllSQL = "select * from ZCQuestion  where ID=?";

	public ZCQuestionSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[17];
	}

	protected Schema newInstance(){
		return new ZCQuestionSchema();
	}

	protected SchemaSet newSet(){
		return new ZCQuestionSet();
	}

	public ZCQuestionSet query() {
		return query(null, -1, -1);
	}

	public ZCQuestionSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZCQuestionSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZCQuestionSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZCQuestionSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){QuestionInnerCode = (String)v;return;}
		if (i == 2){Title = (String)v;return;}
		if (i == 3){Content = (String)v;return;}
		if (i == 4){if(v==null){ReplyCount = null;}else{ReplyCount = new Integer(v.toString());}return;}
		if (i == 5){Status = (String)v;return;}
		if (i == 6){IsCommend = (String)v;return;}
		if (i == 7){EndTime = (Date)v;return;}
		if (i == 8){Memo = (String)v;return;}
		if (i == 9){Prop1 = (String)v;return;}
		if (i == 10){Prop2 = (String)v;return;}
		if (i == 11){Prop3 = (String)v;return;}
		if (i == 12){Prop4 = (String)v;return;}
		if (i == 13){AddTime = (Date)v;return;}
		if (i == 14){AddUser = (String)v;return;}
		if (i == 15){ModifyTime = (Date)v;return;}
		if (i == 16){ModifyUser = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return QuestionInnerCode;}
		if (i == 2){return Title;}
		if (i == 3){return Content;}
		if (i == 4){return ReplyCount;}
		if (i == 5){return Status;}
		if (i == 6){return IsCommend;}
		if (i == 7){return EndTime;}
		if (i == 8){return Memo;}
		if (i == 9){return Prop1;}
		if (i == 10){return Prop2;}
		if (i == 11){return Prop3;}
		if (i == 12){return Prop4;}
		if (i == 13){return AddTime;}
		if (i == 14){return AddUser;}
		if (i == 15){return ModifyTime;}
		if (i == 16){return ModifyUser;}
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
	* ��ȡ�ֶ�QuestionInnerCode��ֵ�����ֶε�<br>
	* �ֶ����� :����������<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getQuestionInnerCode() {
		return QuestionInnerCode;
	}

	/**
	* �����ֶ�QuestionInnerCode��ֵ�����ֶε�<br>
	* �ֶ����� :����������<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setQuestionInnerCode(String questionInnerCode) {
		this.QuestionInnerCode = questionInnerCode;
    }

	/**
	* ��ȡ�ֶ�Title��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :varchar(250)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getTitle() {
		return Title;
	}

	/**
	* �����ֶ�Title��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :varchar(250)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setTitle(String title) {
		this.Title = title;
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
	* ��ȡ�ֶ�ReplyCount��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :int(11)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public int getReplyCount() {
		if(ReplyCount==null){return 0;}
		return ReplyCount.intValue();
	}

	/**
	* �����ֶ�ReplyCount��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :int(11)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setReplyCount(int replyCount) {
		this.ReplyCount = new Integer(replyCount);
    }

	/**
	* �����ֶ�ReplyCount��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :int(11)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setReplyCount(String replyCount) {
		if (replyCount == null){
			this.ReplyCount = null;
			return;
		}
		this.ReplyCount = new Integer(replyCount);
    }

	/**
	* ��ȡ�ֶ�Status��ֵ�����ֶε�<br>
	* �ֶ����� :����״̬<br>
	* �������� :char(1)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getStatus() {
		return Status;
	}

	/**
	* �����ֶ�Status��ֵ�����ֶε�<br>
	* �ֶ����� :����״̬<br>
	* �������� :char(1)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setStatus(String status) {
		this.Status = status;
    }

	/**
	* ��ȡ�ֶ�IsCommend��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ��Ƽ�<br>
	* �������� :char(1)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getIsCommend() {
		return IsCommend;
	}

	/**
	* �����ֶ�IsCommend��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ��Ƽ�<br>
	* �������� :char(1)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setIsCommend(String isCommend) {
		this.IsCommend = isCommend;
    }

	/**
	* ��ȡ�ֶ�EndTime��ֵ�����ֶε�<br>
	* �ֶ����� :����ʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public Date getEndTime() {
		return EndTime;
	}

	/**
	* �����ֶ�EndTime��ֵ�����ֶε�<br>
	* �ֶ����� :����ʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setEndTime(Date endTime) {
		this.EndTime = endTime;
    }

	/**
	* ��ȡ�ֶ�Memo��ֵ�����ֶε�<br>
	* �ֶ����� :��ע<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getMemo() {
		return Memo;
	}

	/**
	* �����ֶ�Memo��ֵ�����ֶε�<br>
	* �ֶ����� :��ע<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setMemo(String memo) {
		this.Memo = memo;
    }

	/**
	* ��ȡ�ֶ�Prop1��ֵ�����ֶε�<br>
	* �ֶ����� :��������1<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* �����ֶ�Prop1��ֵ�����ֶε�<br>
	* �ֶ����� :��������1<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* ��ȡ�ֶ�Prop2��ֵ�����ֶε�<br>
	* �ֶ����� :��������2<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* �����ֶ�Prop2��ֵ�����ֶε�<br>
	* �ֶ����� :��������2<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

	/**
	* ��ȡ�ֶ�Prop3��ֵ�����ֶε�<br>
	* �ֶ����� :��������3<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp3() {
		return Prop3;
	}

	/**
	* �����ֶ�Prop3��ֵ�����ֶε�<br>
	* �ֶ����� :��������3<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp3(String prop3) {
		this.Prop3 = prop3;
    }

	/**
	* ��ȡ�ֶ�Prop4��ֵ�����ֶε�<br>
	* �ֶ����� :��������4<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp4() {
		return Prop4;
	}

	/**
	* �����ֶ�Prop4��ֵ�����ֶε�<br>
	* �ֶ����� :��������4<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp4(String prop4) {
		this.Prop4 = prop4;
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
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getAddUser() {
		return AddUser;
	}

	/**
	* �����ֶ�AddUser��ֵ�����ֶε�<br>
	* �ֶ����� :������<br>
	* �������� :varchar(100)<br>
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
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getModifyUser() {
		return ModifyUser;
	}

	/**
	* �����ֶ�ModifyUser��ֵ�����ֶε�<br>
	* �ֶ����� :�޸���<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setModifyUser(String modifyUser) {
		this.ModifyUser = modifyUser;
    }

}