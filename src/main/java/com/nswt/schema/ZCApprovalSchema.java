package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * �����ƣ����������Ϣ��<br>
 * ����룺ZCApproval<br>
 * ��������ID<br>
 */
public class ZCApprovalSchema extends Schema {
	private Long ID;

	private String ApproveUser;

	private Long ArticleID;

	private String Memo;

	private Long Status;

	private Date ApprovalDate;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 0 , 0 , true , true),
		new SchemaColumn("ApproveUser", DataColumn.STRING, 1, 200 , 0 , true , false),
		new SchemaColumn("ArticleID", DataColumn.LONG, 2, 0 , 0 , true , false),
		new SchemaColumn("Memo", DataColumn.STRING, 3, 200 , 0 , false , false),
		new SchemaColumn("Status", DataColumn.LONG, 4, 0 , 0 , false , false),
		new SchemaColumn("ApprovalDate", DataColumn.DATETIME, 5, 0 , 0 , false , false)
	};

	public static final String _TableCode = "ZCApproval";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into ZCApproval values(?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZCApproval set ID=?,ApproveUser=?,ArticleID=?,Memo=?,Status=?,ApprovalDate=? where ID=?";

	protected static final String _DeleteSQL = "delete from ZCApproval  where ID=?";

	protected static final String _FillAllSQL = "select * from ZCApproval  where ID=?";

	public ZCApprovalSchema(){
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
		return new ZCApprovalSchema();
	}

	protected SchemaSet newSet(){
		return new ZCApprovalSet();
	}

	public ZCApprovalSet query() {
		return query(null, -1, -1);
	}

	public ZCApprovalSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZCApprovalSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZCApprovalSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZCApprovalSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){ApproveUser = (String)v;return;}
		if (i == 2){if(v==null){ArticleID = null;}else{ArticleID = new Long(v.toString());}return;}
		if (i == 3){Memo = (String)v;return;}
		if (i == 4){if(v==null){Status = null;}else{Status = new Long(v.toString());}return;}
		if (i == 5){ApprovalDate = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return ApproveUser;}
		if (i == 2){return ArticleID;}
		if (i == 3){return Memo;}
		if (i == 4){return Status;}
		if (i == 5){return ApprovalDate;}
		return null;
	}

	/**
	* ��ȡ�ֶ�ID��ֵ�����ֶε�<br>
	* �ֶ����� :���ID<br>
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
	* �ֶ����� :���ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setID(long iD) {
		this.ID = new Long(iD);
    }

	/**
	* �����ֶ�ID��ֵ�����ֶε�<br>
	* �ֶ����� :���ID<br>
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
	* ��ȡ�ֶ�ApproveUser��ֵ�����ֶε�<br>
	* �ֶ����� :�����<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getApproveUser() {
		return ApproveUser;
	}

	/**
	* �����ֶ�ApproveUser��ֵ�����ֶε�<br>
	* �ֶ����� :�����<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setApproveUser(String approveUser) {
		this.ApproveUser = approveUser;
    }

	/**
	* ��ȡ�ֶ�ArticleID��ֵ�����ֶε�<br>
	* �ֶ����� :����ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public long getArticleID() {
		if(ArticleID==null){return 0;}
		return ArticleID.longValue();
	}

	/**
	* �����ֶ�ArticleID��ֵ�����ֶε�<br>
	* �ֶ����� :����ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setArticleID(long articleID) {
		this.ArticleID = new Long(articleID);
    }

	/**
	* �����ֶ�ArticleID��ֵ�����ֶε�<br>
	* �ֶ����� :����ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setArticleID(String articleID) {
		if (articleID == null){
			this.ArticleID = null;
			return;
		}
		this.ArticleID = new Long(articleID);
    }

	/**
	* ��ȡ�ֶ�Memo��ֵ�����ֶε�<br>
	* �ֶ����� :������<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getMemo() {
		return Memo;
	}

	/**
	* �����ֶ�Memo��ֵ�����ֶε�<br>
	* �ֶ����� :������<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setMemo(String memo) {
		this.Memo = memo;
    }

	/**
	* ��ȡ�ֶ�Status��ֵ�����ֶε�<br>
	* �ֶ����� :״̬<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getStatus() {
		if(Status==null){return 0;}
		return Status.longValue();
	}

	/**
	* �����ֶ�Status��ֵ�����ֶε�<br>
	* �ֶ����� :״̬<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setStatus(long status) {
		this.Status = new Long(status);
    }

	/**
	* �����ֶ�Status��ֵ�����ֶε�<br>
	* �ֶ����� :״̬<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setStatus(String status) {
		if (status == null){
			this.Status = null;
			return;
		}
		this.Status = new Long(status);
    }

	/**
	* ��ȡ�ֶ�ApprovalDate��ֵ�����ֶε�<br>
	* �ֶ����� :�������<br>
	* �������� :Datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public Date getApprovalDate() {
		return ApprovalDate;
	}

	/**
	* �����ֶ�ApprovalDate��ֵ�����ֶε�<br>
	* �ֶ����� :�������<br>
	* �������� :Datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setApprovalDate(Date approvalDate) {
		this.ApprovalDate = approvalDate;
    }

}