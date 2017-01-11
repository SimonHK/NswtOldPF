package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * �����ƣ���ʱ�����<br>
 * ����룺ZDSchedule<br>
 * ��������ID<br>
 */
public class ZDScheduleSchema extends Schema {
	private Long ID;

	private Long SourceID;

	private String TypeCode;

	private String CronExpression;

	private String PlanType;

	private Date StartTime;

	private String Description;

	private String IsUsing;

	private String OrderFlag;

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
		new SchemaColumn("SourceID", DataColumn.LONG, 1, 0 , 0 , true , false),
		new SchemaColumn("TypeCode", DataColumn.STRING, 2, 30 , 0 , true , false),
		new SchemaColumn("CronExpression", DataColumn.STRING, 3, 100 , 0 , true , false),
		new SchemaColumn("PlanType", DataColumn.STRING, 4, 10 , 0 , true , false),
		new SchemaColumn("StartTime", DataColumn.DATETIME, 5, 0 , 0 , false , false),
		new SchemaColumn("Description", DataColumn.STRING, 6, 255 , 0 , false , false),
		new SchemaColumn("IsUsing", DataColumn.STRING, 7, 2 , 0 , true , false),
		new SchemaColumn("OrderFlag", DataColumn.STRING, 8, 50 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 9, 50 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 10, 50 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 11, 50 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 12, 50 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 13, 200 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 14, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 15, 200 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 16, 0 , 0 , false , false)
	};

	public static final String _TableCode = "ZDSchedule";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into ZDSchedule values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZDSchedule set ID=?,SourceID=?,TypeCode=?,CronExpression=?,PlanType=?,StartTime=?,Description=?,IsUsing=?,OrderFlag=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";

	protected static final String _DeleteSQL = "delete from ZDSchedule  where ID=?";

	protected static final String _FillAllSQL = "select * from ZDSchedule  where ID=?";

	public ZDScheduleSchema(){
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
		return new ZDScheduleSchema();
	}

	protected SchemaSet newSet(){
		return new ZDScheduleSet();
	}

	public ZDScheduleSet query() {
		return query(null, -1, -1);
	}

	public ZDScheduleSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZDScheduleSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZDScheduleSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZDScheduleSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){SourceID = null;}else{SourceID = new Long(v.toString());}return;}
		if (i == 2){TypeCode = (String)v;return;}
		if (i == 3){CronExpression = (String)v;return;}
		if (i == 4){PlanType = (String)v;return;}
		if (i == 5){StartTime = (Date)v;return;}
		if (i == 6){Description = (String)v;return;}
		if (i == 7){IsUsing = (String)v;return;}
		if (i == 8){OrderFlag = (String)v;return;}
		if (i == 9){Prop1 = (String)v;return;}
		if (i == 10){Prop2 = (String)v;return;}
		if (i == 11){Prop3 = (String)v;return;}
		if (i == 12){Prop4 = (String)v;return;}
		if (i == 13){AddUser = (String)v;return;}
		if (i == 14){AddTime = (Date)v;return;}
		if (i == 15){ModifyUser = (String)v;return;}
		if (i == 16){ModifyTime = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return SourceID;}
		if (i == 2){return TypeCode;}
		if (i == 3){return CronExpression;}
		if (i == 4){return PlanType;}
		if (i == 5){return StartTime;}
		if (i == 6){return Description;}
		if (i == 7){return IsUsing;}
		if (i == 8){return OrderFlag;}
		if (i == 9){return Prop1;}
		if (i == 10){return Prop2;}
		if (i == 11){return Prop3;}
		if (i == 12){return Prop4;}
		if (i == 13){return AddUser;}
		if (i == 14){return AddTime;}
		if (i == 15){return ModifyUser;}
		if (i == 16){return ModifyTime;}
		return null;
	}

	/**
	* ��ȡ�ֶ�ID��ֵ�����ֶε�<br>
	* �ֶ����� :��ʱID<br>
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
	* �ֶ����� :��ʱID<br>
	* �������� :bigint<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setID(long iD) {
		this.ID = new Long(iD);
    }

	/**
	* �����ֶ�ID��ֵ�����ֶε�<br>
	* �ֶ����� :��ʱID<br>
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
	* ��ȡ�ֶ�SourceID��ֵ�����ֶε�<br>
	* �ֶ����� :ԴID(��������������)<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	* ��ע��Ϣ :<br>
	SYSTEM Ϊϵͳ����������ɾ��<br>
	USER Ϊ�û��Զ�������<br>
	*/
	public long getSourceID() {
		if(SourceID==null){return 0;}
		return SourceID.longValue();
	}

	/**
	* �����ֶ�SourceID��ֵ�����ֶε�<br>
	* �ֶ����� :ԴID(��������������)<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	* ��ע��Ϣ :<br>
	SYSTEM Ϊϵͳ����������ɾ��<br>
	USER Ϊ�û��Զ�������<br>
	*/
	public void setSourceID(long sourceID) {
		this.SourceID = new Long(sourceID);
    }

	/**
	* �����ֶ�SourceID��ֵ�����ֶε�<br>
	* �ֶ����� :ԴID(��������������)<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	* ��ע��Ϣ :<br>
	SYSTEM Ϊϵͳ����������ɾ��<br>
	USER Ϊ�û��Զ�������<br>
	*/
	public void setSourceID(String sourceID) {
		if (sourceID == null){
			this.SourceID = null;
			return;
		}
		this.SourceID = new Long(sourceID);
    }

	/**
	* ��ȡ�ֶ�TypeCode��ֵ�����ֶε�<br>
	* �ֶ����� :�������ʹ���<br>
	* �������� :varchar(30)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getTypeCode() {
		return TypeCode;
	}

	/**
	* �����ֶ�TypeCode��ֵ�����ֶε�<br>
	* �ֶ����� :�������ʹ���<br>
	* �������� :varchar(30)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setTypeCode(String typeCode) {
		this.TypeCode = typeCode;
    }

	/**
	* ��ȡ�ֶ�CronExpression��ֵ�����ֶε�<br>
	* �ֶ����� :ִ�б��ʽ<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getCronExpression() {
		return CronExpression;
	}

	/**
	* �����ֶ�CronExpression��ֵ�����ֶε�<br>
	* �ֶ����� :ִ�б��ʽ<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setCronExpression(String cronExpression) {
		this.CronExpression = cronExpression;
    }

	/**
	* ��ȡ�ֶ�PlanType��ֵ�����ֶε�<br>
	* �ֶ����� :�ƻ�����<br>
	* �������� :varchar(10)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getPlanType() {
		return PlanType;
	}

	/**
	* �����ֶ�PlanType��ֵ�����ֶε�<br>
	* �ֶ����� :�ƻ�����<br>
	* �������� :varchar(10)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setPlanType(String planType) {
		this.PlanType = planType;
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
	* ��ȡ�ֶ�Description��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :varchar(255)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getDescription() {
		return Description;
	}

	/**
	* �����ֶ�Description��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :varchar(255)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setDescription(String description) {
		this.Description = description;
    }

	/**
	* ��ȡ�ֶ�IsUsing��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ�����<br>
	* �������� :char(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getIsUsing() {
		return IsUsing;
	}

	/**
	* �����ֶ�IsUsing��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ�����<br>
	* �������� :char(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setIsUsing(String isUsing) {
		this.IsUsing = isUsing;
    }

	/**
	* ��ȡ�ֶ�OrderFlag��ֵ�����ֶε�<br>
	* �ֶ����� :������<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getOrderFlag() {
		return OrderFlag;
	}

	/**
	* �����ֶ�OrderFlag��ֵ�����ֶε�<br>
	* �ֶ����� :������<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setOrderFlag(String orderFlag) {
		this.OrderFlag = orderFlag;
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