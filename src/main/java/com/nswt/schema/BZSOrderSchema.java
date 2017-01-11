package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * �����ƣ���������<br>
 * ����룺BZSOrder<br>
 * ��������ID, BackupNo<br>
 */
public class BZSOrderSchema extends Schema {
	private Long ID;

	private Long SiteID;

	private String UserName;

	private String IsValid;

	private String Status;

	private Float Amount;

	private Float SendFee;

	private Float OrderAmount;

	private Long Score;

	private String Name;

	private String Province;

	private String City;

	private String District;

	private String Address;

	private String ZipCode;

	private String Tel;

	private String Mobile;

	private String HasInvoice;

	private String InvoiceTitle;

	private Date SendBeginDate;

	private Date SendEndDate;

	private String SendTimeSlice;

	private String SendInfo;

	private String SendType;

	private String PaymentType;

	private String Memo;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	private String Prop4;

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
		new SchemaColumn("UserName", DataColumn.STRING, 2, 200 , 0 , false , false),
		new SchemaColumn("IsValid", DataColumn.STRING, 3, 1 , 0 , false , false),
		new SchemaColumn("Status", DataColumn.STRING, 4, 40 , 0 , false , false),
		new SchemaColumn("Amount", DataColumn.FLOAT, 5, 12 , 2 , true , false),
		new SchemaColumn("SendFee", DataColumn.FLOAT, 6, 12 , 2 , false , false),
		new SchemaColumn("OrderAmount", DataColumn.FLOAT, 7, 12 , 2 , false , false),
		new SchemaColumn("Score", DataColumn.LONG, 8, 0 , 0 , true , false),
		new SchemaColumn("Name", DataColumn.STRING, 9, 30 , 0 , false , false),
		new SchemaColumn("Province", DataColumn.STRING, 10, 6 , 0 , false , false),
		new SchemaColumn("City", DataColumn.STRING, 11, 6 , 0 , false , false),
		new SchemaColumn("District", DataColumn.STRING, 12, 6 , 0 , false , false),
		new SchemaColumn("Address", DataColumn.STRING, 13, 255 , 0 , false , false),
		new SchemaColumn("ZipCode", DataColumn.STRING, 14, 10 , 0 , false , false),
		new SchemaColumn("Tel", DataColumn.STRING, 15, 20 , 0 , false , false),
		new SchemaColumn("Mobile", DataColumn.STRING, 16, 20 , 0 , false , false),
		new SchemaColumn("HasInvoice", DataColumn.STRING, 17, 1 , 0 , true , false),
		new SchemaColumn("InvoiceTitle", DataColumn.STRING, 18, 100 , 0 , false , false),
		new SchemaColumn("SendBeginDate", DataColumn.DATETIME, 19, 0 , 0 , false , false),
		new SchemaColumn("SendEndDate", DataColumn.DATETIME, 20, 0 , 0 , false , false),
		new SchemaColumn("SendTimeSlice", DataColumn.STRING, 21, 40 , 0 , false , false),
		new SchemaColumn("SendInfo", DataColumn.STRING, 22, 200 , 0 , false , false),
		new SchemaColumn("SendType", DataColumn.STRING, 23, 40 , 0 , false , false),
		new SchemaColumn("PaymentType", DataColumn.STRING, 24, 40 , 0 , false , false),
		new SchemaColumn("Memo", DataColumn.STRING, 25, 200 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 26, 200 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 27, 200 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 28, 200 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 29, 200 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 30, 200 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 31, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 32, 200 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 33, 0 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 34, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 35, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 36, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 37, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZSOrder";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into BZSOrder values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZSOrder set ID=?,SiteID=?,UserName=?,IsValid=?,Status=?,Amount=?,SendFee=?,OrderAmount=?,Score=?,Name=?,Province=?,City=?,District=?,Address=?,ZipCode=?,Tel=?,Mobile=?,HasInvoice=?,InvoiceTitle=?,SendBeginDate=?,SendEndDate=?,SendTimeSlice=?,SendInfo=?,SendType=?,PaymentType=?,Memo=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZSOrder  where ID=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZSOrder  where ID=? and BackupNo=?";

	public BZSOrderSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[38];
	}

	protected Schema newInstance(){
		return new BZSOrderSchema();
	}

	protected SchemaSet newSet(){
		return new BZSOrderSet();
	}

	public BZSOrderSet query() {
		return query(null, -1, -1);
	}

	public BZSOrderSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZSOrderSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZSOrderSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZSOrderSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 2){UserName = (String)v;return;}
		if (i == 3){IsValid = (String)v;return;}
		if (i == 4){Status = (String)v;return;}
		if (i == 5){if(v==null){Amount = null;}else{Amount = new Float(v.toString());}return;}
		if (i == 6){if(v==null){SendFee = null;}else{SendFee = new Float(v.toString());}return;}
		if (i == 7){if(v==null){OrderAmount = null;}else{OrderAmount = new Float(v.toString());}return;}
		if (i == 8){if(v==null){Score = null;}else{Score = new Long(v.toString());}return;}
		if (i == 9){Name = (String)v;return;}
		if (i == 10){Province = (String)v;return;}
		if (i == 11){City = (String)v;return;}
		if (i == 12){District = (String)v;return;}
		if (i == 13){Address = (String)v;return;}
		if (i == 14){ZipCode = (String)v;return;}
		if (i == 15){Tel = (String)v;return;}
		if (i == 16){Mobile = (String)v;return;}
		if (i == 17){HasInvoice = (String)v;return;}
		if (i == 18){InvoiceTitle = (String)v;return;}
		if (i == 19){SendBeginDate = (Date)v;return;}
		if (i == 20){SendEndDate = (Date)v;return;}
		if (i == 21){SendTimeSlice = (String)v;return;}
		if (i == 22){SendInfo = (String)v;return;}
		if (i == 23){SendType = (String)v;return;}
		if (i == 24){PaymentType = (String)v;return;}
		if (i == 25){Memo = (String)v;return;}
		if (i == 26){Prop1 = (String)v;return;}
		if (i == 27){Prop2 = (String)v;return;}
		if (i == 28){Prop3 = (String)v;return;}
		if (i == 29){Prop4 = (String)v;return;}
		if (i == 30){AddUser = (String)v;return;}
		if (i == 31){AddTime = (Date)v;return;}
		if (i == 32){ModifyUser = (String)v;return;}
		if (i == 33){ModifyTime = (Date)v;return;}
		if (i == 34){BackupNo = (String)v;return;}
		if (i == 35){BackupOperator = (String)v;return;}
		if (i == 36){BackupTime = (Date)v;return;}
		if (i == 37){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return SiteID;}
		if (i == 2){return UserName;}
		if (i == 3){return IsValid;}
		if (i == 4){return Status;}
		if (i == 5){return Amount;}
		if (i == 6){return SendFee;}
		if (i == 7){return OrderAmount;}
		if (i == 8){return Score;}
		if (i == 9){return Name;}
		if (i == 10){return Province;}
		if (i == 11){return City;}
		if (i == 12){return District;}
		if (i == 13){return Address;}
		if (i == 14){return ZipCode;}
		if (i == 15){return Tel;}
		if (i == 16){return Mobile;}
		if (i == 17){return HasInvoice;}
		if (i == 18){return InvoiceTitle;}
		if (i == 19){return SendBeginDate;}
		if (i == 20){return SendEndDate;}
		if (i == 21){return SendTimeSlice;}
		if (i == 22){return SendInfo;}
		if (i == 23){return SendType;}
		if (i == 24){return PaymentType;}
		if (i == 25){return Memo;}
		if (i == 26){return Prop1;}
		if (i == 27){return Prop2;}
		if (i == 28){return Prop3;}
		if (i == 29){return Prop4;}
		if (i == 30){return AddUser;}
		if (i == 31){return AddTime;}
		if (i == 32){return ModifyUser;}
		if (i == 33){return ModifyTime;}
		if (i == 34){return BackupNo;}
		if (i == 35){return BackupOperator;}
		if (i == 36){return BackupTime;}
		if (i == 37){return BackupMemo;}
		return null;
	}

	/**
	* ��ȡ�ֶ�ID��ֵ�����ֶε�<br>
	* �ֶ����� :����ID<br>
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
	* �ֶ����� :����ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setID(long iD) {
		this.ID = new Long(iD);
    }

	/**
	* �����ֶ�ID��ֵ�����ֶε�<br>
	* �ֶ����� :����ID<br>
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
	* �ֶ����� :����վ��ID<br>
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
	* �ֶ����� :����վ��ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setSiteID(long siteID) {
		this.SiteID = new Long(siteID);
    }

	/**
	* �����ֶ�SiteID��ֵ�����ֶε�<br>
	* �ֶ����� :����վ��ID<br>
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
	* ��ȡ�ֶ�UserName��ֵ�����ֶε�<br>
	* �ֶ����� :��Ա����<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getUserName() {
		return UserName;
	}

	/**
	* �����ֶ�UserName��ֵ�����ֶε�<br>
	* �ֶ����� :��Ա����<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setUserName(String userName) {
		this.UserName = userName;
    }

	/**
	* ��ȡ�ֶ�IsValid��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ���Ч<br>
	* �������� :varchar(1)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getIsValid() {
		return IsValid;
	}

	/**
	* �����ֶ�IsValid��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ���Ч<br>
	* �������� :varchar(1)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setIsValid(String isValid) {
		this.IsValid = isValid;
    }

	/**
	* ��ȡ�ֶ�Status��ֵ�����ֶε�<br>
	* �ֶ����� :����״̬<br>
	* �������� :varchar(40)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getStatus() {
		return Status;
	}

	/**
	* �����ֶ�Status��ֵ�����ֶε�<br>
	* �ֶ����� :����״̬<br>
	* �������� :varchar(40)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setStatus(String status) {
		this.Status = status;
    }

	/**
	* ��ȡ�ֶ�Amount��ֵ�����ֶε�<br>
	* �ֶ����� :��Ʒ���<br>
	* �������� :float(12,2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public float getAmount() {
		if(Amount==null){return 0;}
		return Amount.floatValue();
	}

	/**
	* �����ֶ�Amount��ֵ�����ֶε�<br>
	* �ֶ����� :��Ʒ���<br>
	* �������� :float(12,2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setAmount(float amount) {
		this.Amount = new Float(amount);
    }

	/**
	* �����ֶ�Amount��ֵ�����ֶε�<br>
	* �ֶ����� :��Ʒ���<br>
	* �������� :float(12,2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setAmount(String amount) {
		if (amount == null){
			this.Amount = null;
			return;
		}
		this.Amount = new Float(amount);
    }

	/**
	* ��ȡ�ֶ�SendFee��ֵ�����ֶε�<br>
	* �ֶ����� :���� ���<br>
	* �������� :float(12,2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public float getSendFee() {
		if(SendFee==null){return 0;}
		return SendFee.floatValue();
	}

	/**
	* �����ֶ�SendFee��ֵ�����ֶε�<br>
	* �ֶ����� :���� ���<br>
	* �������� :float(12,2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setSendFee(float sendFee) {
		this.SendFee = new Float(sendFee);
    }

	/**
	* �����ֶ�SendFee��ֵ�����ֶε�<br>
	* �ֶ����� :���� ���<br>
	* �������� :float(12,2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setSendFee(String sendFee) {
		if (sendFee == null){
			this.SendFee = null;
			return;
		}
		this.SendFee = new Float(sendFee);
    }

	/**
	* ��ȡ�ֶ�OrderAmount��ֵ�����ֶε�<br>
	* �ֶ����� :�������<br>
	* �������� :float(12,2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public float getOrderAmount() {
		if(OrderAmount==null){return 0;}
		return OrderAmount.floatValue();
	}

	/**
	* �����ֶ�OrderAmount��ֵ�����ֶε�<br>
	* �ֶ����� :�������<br>
	* �������� :float(12,2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setOrderAmount(float orderAmount) {
		this.OrderAmount = new Float(orderAmount);
    }

	/**
	* �����ֶ�OrderAmount��ֵ�����ֶε�<br>
	* �ֶ����� :�������<br>
	* �������� :float(12,2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setOrderAmount(String orderAmount) {
		if (orderAmount == null){
			this.OrderAmount = null;
			return;
		}
		this.OrderAmount = new Float(orderAmount);
    }

	/**
	* ��ȡ�ֶ�Score��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public long getScore() {
		if(Score==null){return 0;}
		return Score.longValue();
	}

	/**
	* �����ֶ�Score��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setScore(long score) {
		this.Score = new Long(score);
    }

	/**
	* �����ֶ�Score��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setScore(String score) {
		if (score == null){
			this.Score = null;
			return;
		}
		this.Score = new Long(score);
    }

	/**
	* ��ȡ�ֶ�Name��ֵ�����ֶε�<br>
	* �ֶ����� :�ջ�������<br>
	* �������� :varchar(30)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getName() {
		return Name;
	}

	/**
	* �����ֶ�Name��ֵ�����ֶε�<br>
	* �ֶ����� :�ջ�������<br>
	* �������� :varchar(30)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setName(String name) {
		this.Name = name;
    }

	/**
	* ��ȡ�ֶ�Province��ֵ�����ֶε�<br>
	* �ֶ����� :ʡ��<br>
	* �������� :varchar(6)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProvince() {
		return Province;
	}

	/**
	* �����ֶ�Province��ֵ�����ֶε�<br>
	* �ֶ����� :ʡ��<br>
	* �������� :varchar(6)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProvince(String province) {
		this.Province = province;
    }

	/**
	* ��ȡ�ֶ�City��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :varchar(6)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getCity() {
		return City;
	}

	/**
	* �����ֶ�City��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :varchar(6)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCity(String city) {
		this.City = city;
    }

	/**
	* ��ȡ�ֶ�District��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :varchar(6)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getDistrict() {
		return District;
	}

	/**
	* �����ֶ�District��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :varchar(6)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setDistrict(String district) {
		this.District = district;
    }

	/**
	* ��ȡ�ֶ�Address��ֵ�����ֶε�<br>
	* �ֶ����� :��ַ<br>
	* �������� :varchar(255)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getAddress() {
		return Address;
	}

	/**
	* �����ֶ�Address��ֵ�����ֶε�<br>
	* �ֶ����� :��ַ<br>
	* �������� :varchar(255)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAddress(String address) {
		this.Address = address;
    }

	/**
	* ��ȡ�ֶ�ZipCode��ֵ�����ֶε�<br>
	* �ֶ����� :�ʱ�<br>
	* �������� :varchar(10)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getZipCode() {
		return ZipCode;
	}

	/**
	* �����ֶ�ZipCode��ֵ�����ֶε�<br>
	* �ֶ����� :�ʱ�<br>
	* �������� :varchar(10)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setZipCode(String zipCode) {
		this.ZipCode = zipCode;
    }

	/**
	* ��ȡ�ֶ�Tel��ֵ�����ֶε�<br>
	* �ֶ����� :�̶��绰<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getTel() {
		return Tel;
	}

	/**
	* �����ֶ�Tel��ֵ�����ֶε�<br>
	* �ֶ����� :�̶��绰<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setTel(String tel) {
		this.Tel = tel;
    }

	/**
	* ��ȡ�ֶ�Mobile��ֵ�����ֶε�<br>
	* �ֶ����� :�ƶ��绰<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getMobile() {
		return Mobile;
	}

	/**
	* �����ֶ�Mobile��ֵ�����ֶε�<br>
	* �ֶ����� :�ƶ��绰<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setMobile(String mobile) {
		this.Mobile = mobile;
    }

	/**
	* ��ȡ�ֶ�HasInvoice��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ񿪷�Ʊ<br>
	* �������� :varchar(1)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getHasInvoice() {
		return HasInvoice;
	}

	/**
	* �����ֶ�HasInvoice��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ񿪷�Ʊ<br>
	* �������� :varchar(1)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setHasInvoice(String hasInvoice) {
		this.HasInvoice = hasInvoice;
    }

	/**
	* ��ȡ�ֶ�InvoiceTitle��ֵ�����ֶε�<br>
	* �ֶ����� :��Ʊ̧ͷ<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getInvoiceTitle() {
		return InvoiceTitle;
	}

	/**
	* �����ֶ�InvoiceTitle��ֵ�����ֶε�<br>
	* �ֶ����� :��Ʊ̧ͷ<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setInvoiceTitle(String invoiceTitle) {
		this.InvoiceTitle = invoiceTitle;
    }

	/**
	* ��ȡ�ֶ�SendBeginDate��ֵ�����ֶε�<br>
	* �ֶ����� :�ͻ���ʼʱ��<br>
	* �������� :date<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public Date getSendBeginDate() {
		return SendBeginDate;
	}

	/**
	* �����ֶ�SendBeginDate��ֵ�����ֶε�<br>
	* �ֶ����� :�ͻ���ʼʱ��<br>
	* �������� :date<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setSendBeginDate(Date sendBeginDate) {
		this.SendBeginDate = sendBeginDate;
    }

	/**
	* ��ȡ�ֶ�SendEndDate��ֵ�����ֶε�<br>
	* �ֶ����� :�ͻ�����ʱ��<br>
	* �������� :date<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public Date getSendEndDate() {
		return SendEndDate;
	}

	/**
	* �����ֶ�SendEndDate��ֵ�����ֶε�<br>
	* �ֶ����� :�ͻ�����ʱ��<br>
	* �������� :date<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setSendEndDate(Date sendEndDate) {
		this.SendEndDate = sendEndDate;
    }

	/**
	* ��ȡ�ֶ�SendTimeSlice��ֵ�����ֶε�<br>
	* �ֶ����� :�ͻ�ʱ���<br>
	* �������� :varchar(40)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getSendTimeSlice() {
		return SendTimeSlice;
	}

	/**
	* �����ֶ�SendTimeSlice��ֵ�����ֶε�<br>
	* �ֶ����� :�ͻ�ʱ���<br>
	* �������� :varchar(40)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setSendTimeSlice(String sendTimeSlice) {
		this.SendTimeSlice = sendTimeSlice;
    }

	/**
	* ��ȡ�ֶ�SendInfo��ֵ�����ֶε�<br>
	* �ֶ����� :�ͻ�����˵��<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getSendInfo() {
		return SendInfo;
	}

	/**
	* �����ֶ�SendInfo��ֵ�����ֶε�<br>
	* �ֶ����� :�ͻ�����˵��<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setSendInfo(String sendInfo) {
		this.SendInfo = sendInfo;
    }

	/**
	* ��ȡ�ֶ�SendType��ֵ�����ֶε�<br>
	* �ֶ����� :���ͷ�ʽ<br>
	* �������� :varchar(40)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getSendType() {
		return SendType;
	}

	/**
	* �����ֶ�SendType��ֵ�����ֶε�<br>
	* �ֶ����� :���ͷ�ʽ<br>
	* �������� :varchar(40)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setSendType(String sendType) {
		this.SendType = sendType;
    }

	/**
	* ��ȡ�ֶ�PaymentType��ֵ�����ֶε�<br>
	* �ֶ����� :֧����ʽ<br>
	* �������� :varchar(40)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getPaymentType() {
		return PaymentType;
	}

	/**
	* �����ֶ�PaymentType��ֵ�����ֶε�<br>
	* �ֶ����� :֧����ʽ<br>
	* �������� :varchar(40)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setPaymentType(String paymentType) {
		this.PaymentType = paymentType;
    }

	/**
	* ��ȡ�ֶ�Memo��ֵ�����ֶε�<br>
	* �ֶ����� :��ע<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getMemo() {
		return Memo;
	}

	/**
	* �����ֶ�Memo��ֵ�����ֶε�<br>
	* �ֶ����� :��ע<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setMemo(String memo) {
		this.Memo = memo;
    }

	/**
	* ��ȡ�ֶ�Prop1��ֵ�����ֶε�<br>
	* �ֶ����� :��������1<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* �����ֶ�Prop1��ֵ�����ֶε�<br>
	* �ֶ����� :��������1<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* ��ȡ�ֶ�Prop2��ֵ�����ֶε�<br>
	* �ֶ����� :��������2<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* �����ֶ�Prop2��ֵ�����ֶε�<br>
	* �ֶ����� :��������2<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

	/**
	* ��ȡ�ֶ�Prop3��ֵ�����ֶε�<br>
	* �ֶ����� :��������3<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp3() {
		return Prop3;
	}

	/**
	* �����ֶ�Prop3��ֵ�����ֶε�<br>
	* �ֶ����� :��������3<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp3(String prop3) {
		this.Prop3 = prop3;
    }

	/**
	* ��ȡ�ֶ�Prop4��ֵ�����ֶε�<br>
	* �ֶ����� :��������4<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp4() {
		return Prop4;
	}

	/**
	* �����ֶ�Prop4��ֵ�����ֶε�<br>
	* �ֶ����� :��������4<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp4(String prop4) {
		this.Prop4 = prop4;
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