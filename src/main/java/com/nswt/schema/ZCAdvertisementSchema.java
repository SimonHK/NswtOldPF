package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * �����ƣ�����<br>
 * ����룺ZCAdvertisement<br>
 * ��������ID<br>
 */
public class ZCAdvertisementSchema extends Schema {
	private Long ID;

	private Long PositionID;

	private String PositionCode;

	private Long SiteID;

	private String AdName;

	private String AdType;

	private String AdContent;

	private Long OrderFlag;

	private String IsHitCount;

	private Long HitCount;

	private Long StickTime;

	private String IsOpen;

	private Date StartTime;

	private Date EndTime;

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
		new SchemaColumn("PositionID", DataColumn.LONG, 1, 0 , 0 , true , false),
		new SchemaColumn("PositionCode", DataColumn.STRING, 2, 50 , 0 , true , false),
		new SchemaColumn("SiteID", DataColumn.LONG, 3, 0 , 0 , true , false),
		new SchemaColumn("AdName", DataColumn.STRING, 4, 100 , 0 , true , false),
		new SchemaColumn("AdType", DataColumn.STRING, 5, 20 , 0 , true , false),
		new SchemaColumn("AdContent", DataColumn.CLOB, 6, 0 , 0 , false , false),
		new SchemaColumn("OrderFlag", DataColumn.LONG, 7, 0 , 0 , false , false),
		new SchemaColumn("IsHitCount", DataColumn.STRING, 8, 2 , 0 , false , false),
		new SchemaColumn("HitCount", DataColumn.LONG, 9, 0 , 0 , false , false),
		new SchemaColumn("StickTime", DataColumn.LONG, 10, 0 , 0 , false , false),
		new SchemaColumn("IsOpen", DataColumn.STRING, 11, 2 , 0 , false , false),
		new SchemaColumn("StartTime", DataColumn.DATETIME, 12, 0 , 0 , false , false),
		new SchemaColumn("EndTime", DataColumn.DATETIME, 13, 0 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 14, 50 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 15, 50 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 16, 50 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 17, 50 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 18, 200 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 19, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 20, 200 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 21, 0 , 0 , false , false)
	};

	public static final String _TableCode = "ZCAdvertisement";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into ZCAdvertisement values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZCAdvertisement set ID=?,PositionID=?,PositionCode=?,SiteID=?,AdName=?,AdType=?,AdContent=?,OrderFlag=?,IsHitCount=?,HitCount=?,StickTime=?,IsOpen=?,StartTime=?,EndTime=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";

	protected static final String _DeleteSQL = "delete from ZCAdvertisement  where ID=?";

	protected static final String _FillAllSQL = "select * from ZCAdvertisement  where ID=?";

	public ZCAdvertisementSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[22];
	}

	protected Schema newInstance(){
		return new ZCAdvertisementSchema();
	}

	protected SchemaSet newSet(){
		return new ZCAdvertisementSet();
	}

	public ZCAdvertisementSet query() {
		return query(null, -1, -1);
	}

	public ZCAdvertisementSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZCAdvertisementSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZCAdvertisementSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZCAdvertisementSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){PositionID = null;}else{PositionID = new Long(v.toString());}return;}
		if (i == 2){PositionCode = (String)v;return;}
		if (i == 3){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 4){AdName = (String)v;return;}
		if (i == 5){AdType = (String)v;return;}
		if (i == 6){AdContent = (String)v;return;}
		if (i == 7){if(v==null){OrderFlag = null;}else{OrderFlag = new Long(v.toString());}return;}
		if (i == 8){IsHitCount = (String)v;return;}
		if (i == 9){if(v==null){HitCount = null;}else{HitCount = new Long(v.toString());}return;}
		if (i == 10){if(v==null){StickTime = null;}else{StickTime = new Long(v.toString());}return;}
		if (i == 11){IsOpen = (String)v;return;}
		if (i == 12){StartTime = (Date)v;return;}
		if (i == 13){EndTime = (Date)v;return;}
		if (i == 14){Prop1 = (String)v;return;}
		if (i == 15){Prop2 = (String)v;return;}
		if (i == 16){Prop3 = (String)v;return;}
		if (i == 17){Prop4 = (String)v;return;}
		if (i == 18){AddUser = (String)v;return;}
		if (i == 19){AddTime = (Date)v;return;}
		if (i == 20){ModifyUser = (String)v;return;}
		if (i == 21){ModifyTime = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return PositionID;}
		if (i == 2){return PositionCode;}
		if (i == 3){return SiteID;}
		if (i == 4){return AdName;}
		if (i == 5){return AdType;}
		if (i == 6){return AdContent;}
		if (i == 7){return OrderFlag;}
		if (i == 8){return IsHitCount;}
		if (i == 9){return HitCount;}
		if (i == 10){return StickTime;}
		if (i == 11){return IsOpen;}
		if (i == 12){return StartTime;}
		if (i == 13){return EndTime;}
		if (i == 14){return Prop1;}
		if (i == 15){return Prop2;}
		if (i == 16){return Prop3;}
		if (i == 17){return Prop4;}
		if (i == 18){return AddUser;}
		if (i == 19){return AddTime;}
		if (i == 20){return ModifyUser;}
		if (i == 21){return ModifyTime;}
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
	* ��ȡ�ֶ�PositionID��ֵ�����ֶε�<br>
	* �ֶ����� :λ��ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public long getPositionID() {
		if(PositionID==null){return 0;}
		return PositionID.longValue();
	}

	/**
	* �����ֶ�PositionID��ֵ�����ֶε�<br>
	* �ֶ����� :λ��ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setPositionID(long positionID) {
		this.PositionID = new Long(positionID);
    }

	/**
	* �����ֶ�PositionID��ֵ�����ֶε�<br>
	* �ֶ����� :λ��ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setPositionID(String positionID) {
		if (positionID == null){
			this.PositionID = null;
			return;
		}
		this.PositionID = new Long(positionID);
    }

	/**
	* ��ȡ�ֶ�PositionCode��ֵ�����ֶε�<br>
	* �ֶ����� :λ�ô���<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getPositionCode() {
		return PositionCode;
	}

	/**
	* �����ֶ�PositionCode��ֵ�����ֶε�<br>
	* �ֶ����� :λ�ô���<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setPositionCode(String positionCode) {
		this.PositionCode = positionCode;
    }

	/**
	* ��ȡ�ֶ�SiteID��ֵ�����ֶε�<br>
	* �ֶ����� :վ��ID<br>
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
	* �ֶ����� :վ��ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setSiteID(long siteID) {
		this.SiteID = new Long(siteID);
    }

	/**
	* �����ֶ�SiteID��ֵ�����ֶε�<br>
	* �ֶ����� :վ��ID<br>
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
	* ��ȡ�ֶ�AdName��ֵ�����ֶε�<br>
	* �ֶ����� :�������<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getAdName() {
		return AdName;
	}

	/**
	* �����ֶ�AdName��ֵ�����ֶε�<br>
	* �ֶ����� :�������<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setAdName(String adName) {
		this.AdName = adName;
    }

	/**
	* ��ȡ�ֶ�AdType��ֵ�����ֶε�<br>
	* �ֶ����� :�������<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getAdType() {
		return AdType;
	}

	/**
	* �����ֶ�AdType��ֵ�����ֶε�<br>
	* �ֶ����� :�������<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setAdType(String adType) {
		this.AdType = adType;
    }

	/**
	* ��ȡ�ֶ�AdContent��ֵ�����ֶε�<br>
	* �ֶ����� :�������<br>
	* �������� :mediumtext<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getAdContent() {
		return AdContent;
	}

	/**
	* �����ֶ�AdContent��ֵ�����ֶε�<br>
	* �ֶ����� :�������<br>
	* �������� :mediumtext<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAdContent(String adContent) {
		this.AdContent = adContent;
    }

	/**
	* ��ȡ�ֶ�OrderFlag��ֵ�����ֶε�<br>
	* �ֶ����� :����Ȩ��<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getOrderFlag() {
		if(OrderFlag==null){return 0;}
		return OrderFlag.longValue();
	}

	/**
	* �����ֶ�OrderFlag��ֵ�����ֶε�<br>
	* �ֶ����� :����Ȩ��<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setOrderFlag(long orderFlag) {
		this.OrderFlag = new Long(orderFlag);
    }

	/**
	* �����ֶ�OrderFlag��ֵ�����ֶε�<br>
	* �ֶ����� :����Ȩ��<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setOrderFlag(String orderFlag) {
		if (orderFlag == null){
			this.OrderFlag = null;
			return;
		}
		this.OrderFlag = new Long(orderFlag);
    }

	/**
	* ��ȡ�ֶ�IsHitCount��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ�ͳ�Ƶ����<br>
	* �������� :char(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getIsHitCount() {
		return IsHitCount;
	}

	/**
	* �����ֶ�IsHitCount��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ�ͳ�Ƶ����<br>
	* �������� :char(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setIsHitCount(String isHitCount) {
		this.IsHitCount = isHitCount;
    }

	/**
	* ��ȡ�ֶ�HitCount��ֵ�����ֶε�<br>
	* �ֶ����� :�����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getHitCount() {
		if(HitCount==null){return 0;}
		return HitCount.longValue();
	}

	/**
	* �����ֶ�HitCount��ֵ�����ֶε�<br>
	* �ֶ����� :�����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setHitCount(long hitCount) {
		this.HitCount = new Long(hitCount);
    }

	/**
	* �����ֶ�HitCount��ֵ�����ֶε�<br>
	* �ֶ����� :�����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setHitCount(String hitCount) {
		if (hitCount == null){
			this.HitCount = null;
			return;
		}
		this.HitCount = new Long(hitCount);
    }

	/**
	* ��ȡ�ֶ�StickTime��ֵ�����ֶε�<br>
	* �ֶ����� :ͣ��ʱ��<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getStickTime() {
		if(StickTime==null){return 0;}
		return StickTime.longValue();
	}

	/**
	* �����ֶ�StickTime��ֵ�����ֶε�<br>
	* �ֶ����� :ͣ��ʱ��<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setStickTime(long stickTime) {
		this.StickTime = new Long(stickTime);
    }

	/**
	* �����ֶ�StickTime��ֵ�����ֶε�<br>
	* �ֶ����� :ͣ��ʱ��<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setStickTime(String stickTime) {
		if (stickTime == null){
			this.StickTime = null;
			return;
		}
		this.StickTime = new Long(stickTime);
    }

	/**
	* ��ȡ�ֶ�IsOpen��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ񿪷�<br>
	* �������� :char(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getIsOpen() {
		return IsOpen;
	}

	/**
	* �����ֶ�IsOpen��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ񿪷�<br>
	* �������� :char(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setIsOpen(String isOpen) {
		this.IsOpen = isOpen;
    }

	/**
	* ��ȡ�ֶ�StartTime��ֵ�����ֶε�<br>
	* �ֶ����� :��ʼʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public Date getStartTime() {
		return StartTime;
	}

	/**
	* �����ֶ�StartTime��ֵ�����ֶε�<br>
	* �ֶ����� :��ʼʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setStartTime(Date startTime) {
		this.StartTime = startTime;
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
	* ��ȡ�ֶ�Prop3��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�3<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp3() {
		return Prop3;
	}

	/**
	* �����ֶ�Prop3��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�3<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp3(String prop3) {
		this.Prop3 = prop3;
    }

	/**
	* ��ȡ�ֶ�Prop4��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�4<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp4() {
		return Prop4;
	}

	/**
	* �����ֶ�Prop4��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�4<br>
	* �������� :varchar(50)<br>
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