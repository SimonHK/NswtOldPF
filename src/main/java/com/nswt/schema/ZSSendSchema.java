package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * �����ƣ����ͷ�ʽ��<br>
 * ����룺ZSSend<br>
 * ��������ID<br>
 */
public class ZSSendSchema extends Schema {
	private Long ID;

	private Long SiteID;

	private String Name;

	private String SendInfo;

	private String ArriveInfo;

	private String Info;

	private Float Price;

	private String Memo;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	private String Prop4;

	private String AddUser;

	private Date AddTime;

	private String ModifyUser;

	private Date ModifyTime;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 0 , 0 , true , true),
		new SchemaColumn("SiteID", DataColumn.LONG, 1, 0 , 0 , true , false),
		new SchemaColumn("Name", DataColumn.STRING, 2, 200 , 0 , false , false),
		new SchemaColumn("SendInfo", DataColumn.STRING, 3, 200 , 0 , false , false),
		new SchemaColumn("ArriveInfo", DataColumn.STRING, 4, 200 , 0 , false , false),
		new SchemaColumn("Info", DataColumn.STRING, 5, 200 , 0 , false , false),
		new SchemaColumn("Price", DataColumn.FLOAT, 6, 12 , 2 , false , false),
		new SchemaColumn("Memo", DataColumn.STRING, 7, 200 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 8, 200 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 9, 200 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 10, 200 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 11, 200 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 12, 200 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 13, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 14, 200 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 15, 0 , 0 , false , false)
	};

	public static final String _TableCode = "ZSSend";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into ZSSend values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZSSend set ID=?,SiteID=?,Name=?,SendInfo=?,ArriveInfo=?,Info=?,Price=?,Memo=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";

	protected static final String _DeleteSQL = "delete from ZSSend  where ID=?";

	protected static final String _FillAllSQL = "select * from ZSSend  where ID=?";

	public ZSSendSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[16];
	}

	protected Schema newInstance(){
		return new ZSSendSchema();
	}

	protected SchemaSet newSet(){
		return new ZSSendSet();
	}

	public ZSSendSet query() {
		return query(null, -1, -1);
	}

	public ZSSendSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZSSendSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZSSendSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZSSendSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 2){Name = (String)v;return;}
		if (i == 3){SendInfo = (String)v;return;}
		if (i == 4){ArriveInfo = (String)v;return;}
		if (i == 5){Info = (String)v;return;}
		if (i == 6){if(v==null){Price = null;}else{Price = new Float(v.toString());}return;}
		if (i == 7){Memo = (String)v;return;}
		if (i == 8){Prop1 = (String)v;return;}
		if (i == 9){Prop2 = (String)v;return;}
		if (i == 10){Prop3 = (String)v;return;}
		if (i == 11){Prop4 = (String)v;return;}
		if (i == 12){AddUser = (String)v;return;}
		if (i == 13){AddTime = (Date)v;return;}
		if (i == 14){ModifyUser = (String)v;return;}
		if (i == 15){ModifyTime = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return SiteID;}
		if (i == 2){return Name;}
		if (i == 3){return SendInfo;}
		if (i == 4){return ArriveInfo;}
		if (i == 5){return Info;}
		if (i == 6){return Price;}
		if (i == 7){return Memo;}
		if (i == 8){return Prop1;}
		if (i == 9){return Prop2;}
		if (i == 10){return Prop3;}
		if (i == 11){return Prop4;}
		if (i == 12){return AddUser;}
		if (i == 13){return AddTime;}
		if (i == 14){return ModifyUser;}
		if (i == 15){return ModifyTime;}
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
	* ��ȡ�ֶ�Name��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getName() {
		return Name;
	}

	/**
	* �����ֶ�Name��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setName(String name) {
		this.Name = name;
    }

	/**
	* ��ȡ�ֶ�SendInfo��ֵ�����ֶε�<br>
	* �ֶ����� :����˵��<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getSendInfo() {
		return SendInfo;
	}

	/**
	* �����ֶ�SendInfo��ֵ�����ֶε�<br>
	* �ֶ����� :����˵��<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setSendInfo(String sendInfo) {
		this.SendInfo = sendInfo;
    }

	/**
	* ��ȡ�ֶ�ArriveInfo��ֵ�����ֶε�<br>
	* �ֶ����� :����˵��<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getArriveInfo() {
		return ArriveInfo;
	}

	/**
	* �����ֶ�ArriveInfo��ֵ�����ֶε�<br>
	* �ֶ����� :����˵��<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setArriveInfo(String arriveInfo) {
		this.ArriveInfo = arriveInfo;
    }

	/**
	* ��ȡ�ֶ�Info��ֵ�����ֶε�<br>
	* �ֶ����� :����˵��<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getInfo() {
		return Info;
	}

	/**
	* �����ֶ�Info��ֵ�����ֶε�<br>
	* �ֶ����� :����˵��<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setInfo(String info) {
		this.Info = info;
    }

	/**
	* ��ȡ�ֶ�Price��ֵ�����ֶε�<br>
	* �ֶ����� :���ͷ���<br>
	* �������� :float(12,2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public float getPrice() {
		if(Price==null){return 0;}
		return Price.floatValue();
	}

	/**
	* �����ֶ�Price��ֵ�����ֶε�<br>
	* �ֶ����� :���ͷ���<br>
	* �������� :float(12,2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setPrice(float price) {
		this.Price = new Float(price);
    }

	/**
	* �����ֶ�Price��ֵ�����ֶε�<br>
	* �ֶ����� :���ͷ���<br>
	* �������� :float(12,2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setPrice(String price) {
		if (price == null){
			this.Price = null;
			return;
		}
		this.Price = new Float(price);
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

}