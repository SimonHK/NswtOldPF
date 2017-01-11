package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * �����ƣ���������־����<br>
 * ����룺BZCAdVisitLog<br>
 * ��������ID, BackupNo<br>
 */
public class BZCAdVisitLogSchema extends Schema {
	private Long ID;

	private Long AdID;

	private Date ServerTime;

	private Date ClientTime;

	private String IP;

	private String Address;

	private String OS;

	private String Browser;

	private String Screen;

	private String Depth;

	private String Referer;

	private String CurrentPage;

	private String Visitor;

	private String BackupNo;

	private String BackupOperator;

	private Date BackupTime;

	private String BackupMemo;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 0 , 0 , true , true),
		new SchemaColumn("AdID", DataColumn.LONG, 1, 0 , 0 , true , false),
		new SchemaColumn("ServerTime", DataColumn.DATETIME, 2, 0 , 0 , false , false),
		new SchemaColumn("ClientTime", DataColumn.DATETIME, 3, 0 , 0 , false , false),
		new SchemaColumn("IP", DataColumn.STRING, 4, 50 , 0 , false , false),
		new SchemaColumn("Address", DataColumn.STRING, 5, 200 , 0 , false , false),
		new SchemaColumn("OS", DataColumn.STRING, 6, 50 , 0 , false , false),
		new SchemaColumn("Browser", DataColumn.STRING, 7, 50 , 0 , false , false),
		new SchemaColumn("Screen", DataColumn.STRING, 8, 50 , 0 , false , false),
		new SchemaColumn("Depth", DataColumn.STRING, 9, 50 , 0 , false , false),
		new SchemaColumn("Referer", DataColumn.STRING, 10, 250 , 0 , false , false),
		new SchemaColumn("CurrentPage", DataColumn.STRING, 11, 250 , 0 , false , false),
		new SchemaColumn("Visitor", DataColumn.STRING, 12, 50 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 13, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 14, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 15, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 16, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZCAdVisitLog";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into BZCAdVisitLog values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZCAdVisitLog set ID=?,AdID=?,ServerTime=?,ClientTime=?,IP=?,Address=?,OS=?,Browser=?,Screen=?,Depth=?,Referer=?,CurrentPage=?,Visitor=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZCAdVisitLog  where ID=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZCAdVisitLog  where ID=? and BackupNo=?";

	public BZCAdVisitLogSchema(){
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
		return new BZCAdVisitLogSchema();
	}

	protected SchemaSet newSet(){
		return new BZCAdVisitLogSet();
	}

	public BZCAdVisitLogSet query() {
		return query(null, -1, -1);
	}

	public BZCAdVisitLogSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZCAdVisitLogSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZCAdVisitLogSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZCAdVisitLogSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){AdID = null;}else{AdID = new Long(v.toString());}return;}
		if (i == 2){ServerTime = (Date)v;return;}
		if (i == 3){ClientTime = (Date)v;return;}
		if (i == 4){IP = (String)v;return;}
		if (i == 5){Address = (String)v;return;}
		if (i == 6){OS = (String)v;return;}
		if (i == 7){Browser = (String)v;return;}
		if (i == 8){Screen = (String)v;return;}
		if (i == 9){Depth = (String)v;return;}
		if (i == 10){Referer = (String)v;return;}
		if (i == 11){CurrentPage = (String)v;return;}
		if (i == 12){Visitor = (String)v;return;}
		if (i == 13){BackupNo = (String)v;return;}
		if (i == 14){BackupOperator = (String)v;return;}
		if (i == 15){BackupTime = (Date)v;return;}
		if (i == 16){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return AdID;}
		if (i == 2){return ServerTime;}
		if (i == 3){return ClientTime;}
		if (i == 4){return IP;}
		if (i == 5){return Address;}
		if (i == 6){return OS;}
		if (i == 7){return Browser;}
		if (i == 8){return Screen;}
		if (i == 9){return Depth;}
		if (i == 10){return Referer;}
		if (i == 11){return CurrentPage;}
		if (i == 12){return Visitor;}
		if (i == 13){return BackupNo;}
		if (i == 14){return BackupOperator;}
		if (i == 15){return BackupTime;}
		if (i == 16){return BackupMemo;}
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
	* ��ȡ�ֶ�AdID��ֵ�����ֶε�<br>
	* �ֶ����� :���ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public long getAdID() {
		if(AdID==null){return 0;}
		return AdID.longValue();
	}

	/**
	* �����ֶ�AdID��ֵ�����ֶε�<br>
	* �ֶ����� :���ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setAdID(long adID) {
		this.AdID = new Long(adID);
    }

	/**
	* �����ֶ�AdID��ֵ�����ֶε�<br>
	* �ֶ����� :���ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setAdID(String adID) {
		if (adID == null){
			this.AdID = null;
			return;
		}
		this.AdID = new Long(adID);
    }

	/**
	* ��ȡ�ֶ�ServerTime��ֵ�����ֶε�<br>
	* �ֶ����� :����ʱ��(��������)<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public Date getServerTime() {
		return ServerTime;
	}

	/**
	* �����ֶ�ServerTime��ֵ�����ֶε�<br>
	* �ֶ����� :����ʱ��(��������)<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setServerTime(Date serverTime) {
		this.ServerTime = serverTime;
    }

	/**
	* ��ȡ�ֶ�ClientTime��ֵ�����ֶε�<br>
	* �ֶ����� :����ʱ��(�ͻ���)<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public Date getClientTime() {
		return ClientTime;
	}

	/**
	* �����ֶ�ClientTime��ֵ�����ֶε�<br>
	* �ֶ����� :����ʱ��(�ͻ���)<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setClientTime(Date clientTime) {
		this.ClientTime = clientTime;
    }

	/**
	* ��ȡ�ֶ�IP��ֵ�����ֶε�<br>
	* �ֶ����� :����ip<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getIP() {
		return IP;
	}

	/**
	* �����ֶ�IP��ֵ�����ֶε�<br>
	* �ֶ����� :����ip<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setIP(String iP) {
		this.IP = iP;
    }

	/**
	* ��ȡ�ֶ�Address��ֵ�����ֶε�<br>
	* �ֶ����� :��ַ<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getAddress() {
		return Address;
	}

	/**
	* �����ֶ�Address��ֵ�����ֶε�<br>
	* �ֶ����� :��ַ<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAddress(String address) {
		this.Address = address;
    }

	/**
	* ��ȡ�ֶ�OS��ֵ�����ֶε�<br>
	* �ֶ����� :����ϵͳ<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getOS() {
		return OS;
	}

	/**
	* �����ֶ�OS��ֵ�����ֶε�<br>
	* �ֶ����� :����ϵͳ<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setOS(String oS) {
		this.OS = oS;
    }

	/**
	* ��ȡ�ֶ�Browser��ֵ�����ֶε�<br>
	* �ֶ����� :�����<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getBrowser() {
		return Browser;
	}

	/**
	* �����ֶ�Browser��ֵ�����ֶε�<br>
	* �ֶ����� :�����<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setBrowser(String browser) {
		this.Browser = browser;
    }

	/**
	* ��ȡ�ֶ�Screen��ֵ�����ֶε�<br>
	* �ֶ����� :�ֱ���<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getScreen() {
		return Screen;
	}

	/**
	* �����ֶ�Screen��ֵ�����ֶε�<br>
	* �ֶ����� :�ֱ���<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setScreen(String screen) {
		this.Screen = screen;
    }

	/**
	* ��ȡ�ֶ�Depth��ֵ�����ֶε�<br>
	* �ֶ����� :ɫ��<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getDepth() {
		return Depth;
	}

	/**
	* �����ֶ�Depth��ֵ�����ֶε�<br>
	* �ֶ����� :ɫ��<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setDepth(String depth) {
		this.Depth = depth;
    }

	/**
	* ��ȡ�ֶ�Referer��ֵ�����ֶε�<br>
	* �ֶ����� :���õ�ַ<br>
	* �������� :varchar(250)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getReferer() {
		return Referer;
	}

	/**
	* �����ֶ�Referer��ֵ�����ֶε�<br>
	* �ֶ����� :���õ�ַ<br>
	* �������� :varchar(250)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setReferer(String referer) {
		this.Referer = referer;
    }

	/**
	* ��ȡ�ֶ�CurrentPage��ֵ�����ֶε�<br>
	* �ֶ����� :��ǰҳ��<br>
	* �������� :varchar(250)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getCurrentPage() {
		return CurrentPage;
	}

	/**
	* �����ֶ�CurrentPage��ֵ�����ֶε�<br>
	* �ֶ����� :��ǰҳ��<br>
	* �������� :varchar(250)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCurrentPage(String currentPage) {
		this.CurrentPage = currentPage;
    }

	/**
	* ��ȡ�ֶ�Visitor��ֵ�����ֶε�<br>
	* �ֶ����� :������<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getVisitor() {
		return Visitor;
	}

	/**
	* �����ֶ�Visitor��ֵ�����ֶε�<br>
	* �ֶ����� :������<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setVisitor(String visitor) {
		this.Visitor = visitor;
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