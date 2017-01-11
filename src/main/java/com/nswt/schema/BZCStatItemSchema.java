package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * �����ƣ�����ͳ�Ʊ���<br>
 * ����룺BZCStatItem<br>
 * ��������SiteID, Period, Type, SubType, Item, BackupNo<br>
 */
public class BZCStatItemSchema extends Schema {
	private Long SiteID;

	private String Period;

	private String Type;

	private String SubType;

	private String Item;

	private Long Count1;

	private Long Count2;

	private Long Count3;

	private Long Count4;

	private Long Count5;

	private Long Count6;

	private Long Count7;

	private Long Count8;

	private Long Count9;

	private Long Count10;

	private Long Count11;

	private Long Count12;

	private Long Count13;

	private Long Count14;

	private Long Count15;

	private Long Count16;

	private Long Count17;

	private Long Count18;

	private Long Count19;

	private Long Count20;

	private Long Count21;

	private Long Count22;

	private Long Count23;

	private Long Count24;

	private Long Count25;

	private Long Count26;

	private Long Count27;

	private Long Count28;

	private Long Count29;

	private Long Count30;

	private Long Count31;

	private String Memo;

	private String BackupNo;

	private String BackupOperator;

	private Date BackupTime;

	private String BackupMemo;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("SiteID", DataColumn.LONG, 0, 0 , 0 , true , true),
		new SchemaColumn("Period", DataColumn.STRING, 1, 10 , 0 , true , true),
		new SchemaColumn("Type", DataColumn.STRING, 2, 50 , 0 , true , true),
		new SchemaColumn("SubType", DataColumn.STRING, 3, 50 , 0 , true , true),
		new SchemaColumn("Item", DataColumn.STRING, 4, 150 , 0 , true , true),
		new SchemaColumn("Count1", DataColumn.LONG, 5, 0 , 0 , false , false),
		new SchemaColumn("Count2", DataColumn.LONG, 6, 0 , 0 , false , false),
		new SchemaColumn("Count3", DataColumn.LONG, 7, 0 , 0 , false , false),
		new SchemaColumn("Count4", DataColumn.LONG, 8, 0 , 0 , false , false),
		new SchemaColumn("Count5", DataColumn.LONG, 9, 0 , 0 , false , false),
		new SchemaColumn("Count6", DataColumn.LONG, 10, 0 , 0 , false , false),
		new SchemaColumn("Count7", DataColumn.LONG, 11, 0 , 0 , false , false),
		new SchemaColumn("Count8", DataColumn.LONG, 12, 0 , 0 , false , false),
		new SchemaColumn("Count9", DataColumn.LONG, 13, 0 , 0 , false , false),
		new SchemaColumn("Count10", DataColumn.LONG, 14, 0 , 0 , false , false),
		new SchemaColumn("Count11", DataColumn.LONG, 15, 0 , 0 , false , false),
		new SchemaColumn("Count12", DataColumn.LONG, 16, 0 , 0 , false , false),
		new SchemaColumn("Count13", DataColumn.LONG, 17, 0 , 0 , false , false),
		new SchemaColumn("Count14", DataColumn.LONG, 18, 0 , 0 , false , false),
		new SchemaColumn("Count15", DataColumn.LONG, 19, 0 , 0 , false , false),
		new SchemaColumn("Count16", DataColumn.LONG, 20, 0 , 0 , false , false),
		new SchemaColumn("Count17", DataColumn.LONG, 21, 0 , 0 , false , false),
		new SchemaColumn("Count18", DataColumn.LONG, 22, 0 , 0 , false , false),
		new SchemaColumn("Count19", DataColumn.LONG, 23, 0 , 0 , false , false),
		new SchemaColumn("Count20", DataColumn.LONG, 24, 0 , 0 , false , false),
		new SchemaColumn("Count21", DataColumn.LONG, 25, 0 , 0 , false , false),
		new SchemaColumn("Count22", DataColumn.LONG, 26, 0 , 0 , false , false),
		new SchemaColumn("Count23", DataColumn.LONG, 27, 0 , 0 , false , false),
		new SchemaColumn("Count24", DataColumn.LONG, 28, 0 , 0 , false , false),
		new SchemaColumn("Count25", DataColumn.LONG, 29, 0 , 0 , false , false),
		new SchemaColumn("Count26", DataColumn.LONG, 30, 0 , 0 , false , false),
		new SchemaColumn("Count27", DataColumn.LONG, 31, 0 , 0 , false , false),
		new SchemaColumn("Count28", DataColumn.LONG, 32, 0 , 0 , false , false),
		new SchemaColumn("Count29", DataColumn.LONG, 33, 0 , 0 , false , false),
		new SchemaColumn("Count30", DataColumn.LONG, 34, 0 , 0 , false , false),
		new SchemaColumn("Count31", DataColumn.LONG, 35, 0 , 0 , false , false),
		new SchemaColumn("Memo", DataColumn.STRING, 36, 4000 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 37, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 38, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 39, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 40, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZCStatItem";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into BZCStatItem values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZCStatItem set SiteID=?,Period=?,Type=?,SubType=?,Item=?,Count1=?,Count2=?,Count3=?,Count4=?,Count5=?,Count6=?,Count7=?,Count8=?,Count9=?,Count10=?,Count11=?,Count12=?,Count13=?,Count14=?,Count15=?,Count16=?,Count17=?,Count18=?,Count19=?,Count20=?,Count21=?,Count22=?,Count23=?,Count24=?,Count25=?,Count26=?,Count27=?,Count28=?,Count29=?,Count30=?,Count31=?,Memo=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where SiteID=? and Period=? and Type=? and SubType=? and Item=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZCStatItem  where SiteID=? and Period=? and Type=? and SubType=? and Item=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZCStatItem  where SiteID=? and Period=? and Type=? and SubType=? and Item=? and BackupNo=?";

	public BZCStatItemSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[41];
	}

	protected Schema newInstance(){
		return new BZCStatItemSchema();
	}

	protected SchemaSet newSet(){
		return new BZCStatItemSet();
	}

	public BZCStatItemSet query() {
		return query(null, -1, -1);
	}

	public BZCStatItemSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZCStatItemSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZCStatItemSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZCStatItemSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 1){Period = (String)v;return;}
		if (i == 2){Type = (String)v;return;}
		if (i == 3){SubType = (String)v;return;}
		if (i == 4){Item = (String)v;return;}
		if (i == 5){if(v==null){Count1 = null;}else{Count1 = new Long(v.toString());}return;}
		if (i == 6){if(v==null){Count2 = null;}else{Count2 = new Long(v.toString());}return;}
		if (i == 7){if(v==null){Count3 = null;}else{Count3 = new Long(v.toString());}return;}
		if (i == 8){if(v==null){Count4 = null;}else{Count4 = new Long(v.toString());}return;}
		if (i == 9){if(v==null){Count5 = null;}else{Count5 = new Long(v.toString());}return;}
		if (i == 10){if(v==null){Count6 = null;}else{Count6 = new Long(v.toString());}return;}
		if (i == 11){if(v==null){Count7 = null;}else{Count7 = new Long(v.toString());}return;}
		if (i == 12){if(v==null){Count8 = null;}else{Count8 = new Long(v.toString());}return;}
		if (i == 13){if(v==null){Count9 = null;}else{Count9 = new Long(v.toString());}return;}
		if (i == 14){if(v==null){Count10 = null;}else{Count10 = new Long(v.toString());}return;}
		if (i == 15){if(v==null){Count11 = null;}else{Count11 = new Long(v.toString());}return;}
		if (i == 16){if(v==null){Count12 = null;}else{Count12 = new Long(v.toString());}return;}
		if (i == 17){if(v==null){Count13 = null;}else{Count13 = new Long(v.toString());}return;}
		if (i == 18){if(v==null){Count14 = null;}else{Count14 = new Long(v.toString());}return;}
		if (i == 19){if(v==null){Count15 = null;}else{Count15 = new Long(v.toString());}return;}
		if (i == 20){if(v==null){Count16 = null;}else{Count16 = new Long(v.toString());}return;}
		if (i == 21){if(v==null){Count17 = null;}else{Count17 = new Long(v.toString());}return;}
		if (i == 22){if(v==null){Count18 = null;}else{Count18 = new Long(v.toString());}return;}
		if (i == 23){if(v==null){Count19 = null;}else{Count19 = new Long(v.toString());}return;}
		if (i == 24){if(v==null){Count20 = null;}else{Count20 = new Long(v.toString());}return;}
		if (i == 25){if(v==null){Count21 = null;}else{Count21 = new Long(v.toString());}return;}
		if (i == 26){if(v==null){Count22 = null;}else{Count22 = new Long(v.toString());}return;}
		if (i == 27){if(v==null){Count23 = null;}else{Count23 = new Long(v.toString());}return;}
		if (i == 28){if(v==null){Count24 = null;}else{Count24 = new Long(v.toString());}return;}
		if (i == 29){if(v==null){Count25 = null;}else{Count25 = new Long(v.toString());}return;}
		if (i == 30){if(v==null){Count26 = null;}else{Count26 = new Long(v.toString());}return;}
		if (i == 31){if(v==null){Count27 = null;}else{Count27 = new Long(v.toString());}return;}
		if (i == 32){if(v==null){Count28 = null;}else{Count28 = new Long(v.toString());}return;}
		if (i == 33){if(v==null){Count29 = null;}else{Count29 = new Long(v.toString());}return;}
		if (i == 34){if(v==null){Count30 = null;}else{Count30 = new Long(v.toString());}return;}
		if (i == 35){if(v==null){Count31 = null;}else{Count31 = new Long(v.toString());}return;}
		if (i == 36){Memo = (String)v;return;}
		if (i == 37){BackupNo = (String)v;return;}
		if (i == 38){BackupOperator = (String)v;return;}
		if (i == 39){BackupTime = (Date)v;return;}
		if (i == 40){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return SiteID;}
		if (i == 1){return Period;}
		if (i == 2){return Type;}
		if (i == 3){return SubType;}
		if (i == 4){return Item;}
		if (i == 5){return Count1;}
		if (i == 6){return Count2;}
		if (i == 7){return Count3;}
		if (i == 8){return Count4;}
		if (i == 9){return Count5;}
		if (i == 10){return Count6;}
		if (i == 11){return Count7;}
		if (i == 12){return Count8;}
		if (i == 13){return Count9;}
		if (i == 14){return Count10;}
		if (i == 15){return Count11;}
		if (i == 16){return Count12;}
		if (i == 17){return Count13;}
		if (i == 18){return Count14;}
		if (i == 19){return Count15;}
		if (i == 20){return Count16;}
		if (i == 21){return Count17;}
		if (i == 22){return Count18;}
		if (i == 23){return Count19;}
		if (i == 24){return Count20;}
		if (i == 25){return Count21;}
		if (i == 26){return Count22;}
		if (i == 27){return Count23;}
		if (i == 28){return Count24;}
		if (i == 29){return Count25;}
		if (i == 30){return Count26;}
		if (i == 31){return Count27;}
		if (i == 32){return Count28;}
		if (i == 33){return Count29;}
		if (i == 34){return Count30;}
		if (i == 35){return Count31;}
		if (i == 36){return Memo;}
		if (i == 37){return BackupNo;}
		if (i == 38){return BackupOperator;}
		if (i == 39){return BackupTime;}
		if (i == 40){return BackupMemo;}
		return null;
	}

	/**
	* ��ȡ�ֶ�SiteID��ֵ�����ֶε�<br>
	* �ֶ����� :վ��ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :true<br>
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
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setSiteID(long siteID) {
		this.SiteID = new Long(siteID);
    }

	/**
	* �����ֶ�SiteID��ֵ�����ֶε�<br>
	* �ֶ����� :վ��ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :true<br>
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
	* ��ȡ�ֶ�Period��ֵ�����ֶε�<br>
	* �ֶ����� :ʱ��<br>
	* �������� :varchar(10)<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	* ��ע��Ϣ :<br>
	����ʱ�ε��ַ�����ȫ�������֡� ���ʱ�������ȫ��������0 ���ʱ��������ܣ�����7λ������2008901 ���ʱ��������£�����6λ������200811 ���ʱ��������գ�����8λ������20081120 ���ʱ�������ʱ������10λ������2008112015<br>
	*/
	public String getPeriod() {
		return Period;
	}

	/**
	* �����ֶ�Period��ֵ�����ֶε�<br>
	* �ֶ����� :ʱ��<br>
	* �������� :varchar(10)<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	* ��ע��Ϣ :<br>
	����ʱ�ε��ַ�����ȫ�������֡� ���ʱ�������ȫ��������0 ���ʱ��������ܣ�����7λ������2008901 ���ʱ��������£�����6λ������200811 ���ʱ��������գ�����8λ������20081120 ���ʱ�������ʱ������10λ������2008112015<br>
	*/
	public void setPeriod(String period) {
		this.Period = period;
    }

	/**
	* ��ȡ�ֶ�Type��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ�������<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public String getType() {
		return Type;
	}

	/**
	* �����ֶ�Type��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ�������<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setType(String type) {
		this.Type = type;
    }

	/**
	* ��ȡ�ֶ�SubType��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ���������<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public String getSubType() {
		return SubType;
	}

	/**
	* �����ֶ�SubType��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ���������<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setSubType(String subType) {
		this.SubType = subType;
    }

	/**
	* ��ȡ�ֶ�Item��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ����<br>
	* �������� :varchar(150)<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public String getItem() {
		return Item;
	}

	/**
	* �����ֶ�Item��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ����<br>
	* �������� :varchar(150)<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setItem(String item) {
		this.Item = item;
    }

	/**
	* ��ȡ�ֶ�Count1��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ1<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getCount1() {
		if(Count1==null){return 0;}
		return Count1.longValue();
	}

	/**
	* �����ֶ�Count1��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ1<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount1(long count1) {
		this.Count1 = new Long(count1);
    }

	/**
	* �����ֶ�Count1��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ1<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount1(String count1) {
		if (count1 == null){
			this.Count1 = null;
			return;
		}
		this.Count1 = new Long(count1);
    }

	/**
	* ��ȡ�ֶ�Count2��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ2<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getCount2() {
		if(Count2==null){return 0;}
		return Count2.longValue();
	}

	/**
	* �����ֶ�Count2��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ2<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount2(long count2) {
		this.Count2 = new Long(count2);
    }

	/**
	* �����ֶ�Count2��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ2<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount2(String count2) {
		if (count2 == null){
			this.Count2 = null;
			return;
		}
		this.Count2 = new Long(count2);
    }

	/**
	* ��ȡ�ֶ�Count3��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ3<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getCount3() {
		if(Count3==null){return 0;}
		return Count3.longValue();
	}

	/**
	* �����ֶ�Count3��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ3<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount3(long count3) {
		this.Count3 = new Long(count3);
    }

	/**
	* �����ֶ�Count3��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ3<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount3(String count3) {
		if (count3 == null){
			this.Count3 = null;
			return;
		}
		this.Count3 = new Long(count3);
    }

	/**
	* ��ȡ�ֶ�Count4��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ4<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getCount4() {
		if(Count4==null){return 0;}
		return Count4.longValue();
	}

	/**
	* �����ֶ�Count4��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ4<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount4(long count4) {
		this.Count4 = new Long(count4);
    }

	/**
	* �����ֶ�Count4��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ4<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount4(String count4) {
		if (count4 == null){
			this.Count4 = null;
			return;
		}
		this.Count4 = new Long(count4);
    }

	/**
	* ��ȡ�ֶ�Count5��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ5<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getCount5() {
		if(Count5==null){return 0;}
		return Count5.longValue();
	}

	/**
	* �����ֶ�Count5��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ5<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount5(long count5) {
		this.Count5 = new Long(count5);
    }

	/**
	* �����ֶ�Count5��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ5<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount5(String count5) {
		if (count5 == null){
			this.Count5 = null;
			return;
		}
		this.Count5 = new Long(count5);
    }

	/**
	* ��ȡ�ֶ�Count6��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ6<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getCount6() {
		if(Count6==null){return 0;}
		return Count6.longValue();
	}

	/**
	* �����ֶ�Count6��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ6<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount6(long count6) {
		this.Count6 = new Long(count6);
    }

	/**
	* �����ֶ�Count6��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ6<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount6(String count6) {
		if (count6 == null){
			this.Count6 = null;
			return;
		}
		this.Count6 = new Long(count6);
    }

	/**
	* ��ȡ�ֶ�Count7��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ7<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getCount7() {
		if(Count7==null){return 0;}
		return Count7.longValue();
	}

	/**
	* �����ֶ�Count7��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ7<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount7(long count7) {
		this.Count7 = new Long(count7);
    }

	/**
	* �����ֶ�Count7��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ7<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount7(String count7) {
		if (count7 == null){
			this.Count7 = null;
			return;
		}
		this.Count7 = new Long(count7);
    }

	/**
	* ��ȡ�ֶ�Count8��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ8<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getCount8() {
		if(Count8==null){return 0;}
		return Count8.longValue();
	}

	/**
	* �����ֶ�Count8��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ8<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount8(long count8) {
		this.Count8 = new Long(count8);
    }

	/**
	* �����ֶ�Count8��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ8<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount8(String count8) {
		if (count8 == null){
			this.Count8 = null;
			return;
		}
		this.Count8 = new Long(count8);
    }

	/**
	* ��ȡ�ֶ�Count9��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ9<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getCount9() {
		if(Count9==null){return 0;}
		return Count9.longValue();
	}

	/**
	* �����ֶ�Count9��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ9<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount9(long count9) {
		this.Count9 = new Long(count9);
    }

	/**
	* �����ֶ�Count9��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ9<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount9(String count9) {
		if (count9 == null){
			this.Count9 = null;
			return;
		}
		this.Count9 = new Long(count9);
    }

	/**
	* ��ȡ�ֶ�Count10��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ10<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getCount10() {
		if(Count10==null){return 0;}
		return Count10.longValue();
	}

	/**
	* �����ֶ�Count10��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ10<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount10(long count10) {
		this.Count10 = new Long(count10);
    }

	/**
	* �����ֶ�Count10��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ10<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount10(String count10) {
		if (count10 == null){
			this.Count10 = null;
			return;
		}
		this.Count10 = new Long(count10);
    }

	/**
	* ��ȡ�ֶ�Count11��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ11<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getCount11() {
		if(Count11==null){return 0;}
		return Count11.longValue();
	}

	/**
	* �����ֶ�Count11��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ11<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount11(long count11) {
		this.Count11 = new Long(count11);
    }

	/**
	* �����ֶ�Count11��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ11<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount11(String count11) {
		if (count11 == null){
			this.Count11 = null;
			return;
		}
		this.Count11 = new Long(count11);
    }

	/**
	* ��ȡ�ֶ�Count12��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ12<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getCount12() {
		if(Count12==null){return 0;}
		return Count12.longValue();
	}

	/**
	* �����ֶ�Count12��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ12<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount12(long count12) {
		this.Count12 = new Long(count12);
    }

	/**
	* �����ֶ�Count12��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ12<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount12(String count12) {
		if (count12 == null){
			this.Count12 = null;
			return;
		}
		this.Count12 = new Long(count12);
    }

	/**
	* ��ȡ�ֶ�Count13��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ13<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getCount13() {
		if(Count13==null){return 0;}
		return Count13.longValue();
	}

	/**
	* �����ֶ�Count13��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ13<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount13(long count13) {
		this.Count13 = new Long(count13);
    }

	/**
	* �����ֶ�Count13��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ13<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount13(String count13) {
		if (count13 == null){
			this.Count13 = null;
			return;
		}
		this.Count13 = new Long(count13);
    }

	/**
	* ��ȡ�ֶ�Count14��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ14<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getCount14() {
		if(Count14==null){return 0;}
		return Count14.longValue();
	}

	/**
	* �����ֶ�Count14��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ14<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount14(long count14) {
		this.Count14 = new Long(count14);
    }

	/**
	* �����ֶ�Count14��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ14<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount14(String count14) {
		if (count14 == null){
			this.Count14 = null;
			return;
		}
		this.Count14 = new Long(count14);
    }

	/**
	* ��ȡ�ֶ�Count15��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ15<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getCount15() {
		if(Count15==null){return 0;}
		return Count15.longValue();
	}

	/**
	* �����ֶ�Count15��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ15<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount15(long count15) {
		this.Count15 = new Long(count15);
    }

	/**
	* �����ֶ�Count15��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ15<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount15(String count15) {
		if (count15 == null){
			this.Count15 = null;
			return;
		}
		this.Count15 = new Long(count15);
    }

	/**
	* ��ȡ�ֶ�Count16��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ16<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getCount16() {
		if(Count16==null){return 0;}
		return Count16.longValue();
	}

	/**
	* �����ֶ�Count16��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ16<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount16(long count16) {
		this.Count16 = new Long(count16);
    }

	/**
	* �����ֶ�Count16��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ16<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount16(String count16) {
		if (count16 == null){
			this.Count16 = null;
			return;
		}
		this.Count16 = new Long(count16);
    }

	/**
	* ��ȡ�ֶ�Count17��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ17<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getCount17() {
		if(Count17==null){return 0;}
		return Count17.longValue();
	}

	/**
	* �����ֶ�Count17��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ17<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount17(long count17) {
		this.Count17 = new Long(count17);
    }

	/**
	* �����ֶ�Count17��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ17<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount17(String count17) {
		if (count17 == null){
			this.Count17 = null;
			return;
		}
		this.Count17 = new Long(count17);
    }

	/**
	* ��ȡ�ֶ�Count18��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ18<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getCount18() {
		if(Count18==null){return 0;}
		return Count18.longValue();
	}

	/**
	* �����ֶ�Count18��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ18<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount18(long count18) {
		this.Count18 = new Long(count18);
    }

	/**
	* �����ֶ�Count18��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ18<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount18(String count18) {
		if (count18 == null){
			this.Count18 = null;
			return;
		}
		this.Count18 = new Long(count18);
    }

	/**
	* ��ȡ�ֶ�Count19��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ19<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getCount19() {
		if(Count19==null){return 0;}
		return Count19.longValue();
	}

	/**
	* �����ֶ�Count19��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ19<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount19(long count19) {
		this.Count19 = new Long(count19);
    }

	/**
	* �����ֶ�Count19��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ19<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount19(String count19) {
		if (count19 == null){
			this.Count19 = null;
			return;
		}
		this.Count19 = new Long(count19);
    }

	/**
	* ��ȡ�ֶ�Count20��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ20<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getCount20() {
		if(Count20==null){return 0;}
		return Count20.longValue();
	}

	/**
	* �����ֶ�Count20��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ20<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount20(long count20) {
		this.Count20 = new Long(count20);
    }

	/**
	* �����ֶ�Count20��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ20<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount20(String count20) {
		if (count20 == null){
			this.Count20 = null;
			return;
		}
		this.Count20 = new Long(count20);
    }

	/**
	* ��ȡ�ֶ�Count21��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ21<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getCount21() {
		if(Count21==null){return 0;}
		return Count21.longValue();
	}

	/**
	* �����ֶ�Count21��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ21<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount21(long count21) {
		this.Count21 = new Long(count21);
    }

	/**
	* �����ֶ�Count21��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ21<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount21(String count21) {
		if (count21 == null){
			this.Count21 = null;
			return;
		}
		this.Count21 = new Long(count21);
    }

	/**
	* ��ȡ�ֶ�Count22��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ22<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getCount22() {
		if(Count22==null){return 0;}
		return Count22.longValue();
	}

	/**
	* �����ֶ�Count22��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ22<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount22(long count22) {
		this.Count22 = new Long(count22);
    }

	/**
	* �����ֶ�Count22��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ22<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount22(String count22) {
		if (count22 == null){
			this.Count22 = null;
			return;
		}
		this.Count22 = new Long(count22);
    }

	/**
	* ��ȡ�ֶ�Count23��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ23<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getCount23() {
		if(Count23==null){return 0;}
		return Count23.longValue();
	}

	/**
	* �����ֶ�Count23��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ23<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount23(long count23) {
		this.Count23 = new Long(count23);
    }

	/**
	* �����ֶ�Count23��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ23<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount23(String count23) {
		if (count23 == null){
			this.Count23 = null;
			return;
		}
		this.Count23 = new Long(count23);
    }

	/**
	* ��ȡ�ֶ�Count24��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ24<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getCount24() {
		if(Count24==null){return 0;}
		return Count24.longValue();
	}

	/**
	* �����ֶ�Count24��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ24<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount24(long count24) {
		this.Count24 = new Long(count24);
    }

	/**
	* �����ֶ�Count24��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ24<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount24(String count24) {
		if (count24 == null){
			this.Count24 = null;
			return;
		}
		this.Count24 = new Long(count24);
    }

	/**
	* ��ȡ�ֶ�Count25��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ25<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getCount25() {
		if(Count25==null){return 0;}
		return Count25.longValue();
	}

	/**
	* �����ֶ�Count25��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ25<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount25(long count25) {
		this.Count25 = new Long(count25);
    }

	/**
	* �����ֶ�Count25��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ25<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount25(String count25) {
		if (count25 == null){
			this.Count25 = null;
			return;
		}
		this.Count25 = new Long(count25);
    }

	/**
	* ��ȡ�ֶ�Count26��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ26<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getCount26() {
		if(Count26==null){return 0;}
		return Count26.longValue();
	}

	/**
	* �����ֶ�Count26��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ26<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount26(long count26) {
		this.Count26 = new Long(count26);
    }

	/**
	* �����ֶ�Count26��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ26<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount26(String count26) {
		if (count26 == null){
			this.Count26 = null;
			return;
		}
		this.Count26 = new Long(count26);
    }

	/**
	* ��ȡ�ֶ�Count27��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ27<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getCount27() {
		if(Count27==null){return 0;}
		return Count27.longValue();
	}

	/**
	* �����ֶ�Count27��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ27<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount27(long count27) {
		this.Count27 = new Long(count27);
    }

	/**
	* �����ֶ�Count27��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ27<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount27(String count27) {
		if (count27 == null){
			this.Count27 = null;
			return;
		}
		this.Count27 = new Long(count27);
    }

	/**
	* ��ȡ�ֶ�Count28��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ28<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getCount28() {
		if(Count28==null){return 0;}
		return Count28.longValue();
	}

	/**
	* �����ֶ�Count28��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ28<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount28(long count28) {
		this.Count28 = new Long(count28);
    }

	/**
	* �����ֶ�Count28��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ28<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount28(String count28) {
		if (count28 == null){
			this.Count28 = null;
			return;
		}
		this.Count28 = new Long(count28);
    }

	/**
	* ��ȡ�ֶ�Count29��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ29<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getCount29() {
		if(Count29==null){return 0;}
		return Count29.longValue();
	}

	/**
	* �����ֶ�Count29��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ29<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount29(long count29) {
		this.Count29 = new Long(count29);
    }

	/**
	* �����ֶ�Count29��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ29<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount29(String count29) {
		if (count29 == null){
			this.Count29 = null;
			return;
		}
		this.Count29 = new Long(count29);
    }

	/**
	* ��ȡ�ֶ�Count30��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ30<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getCount30() {
		if(Count30==null){return 0;}
		return Count30.longValue();
	}

	/**
	* �����ֶ�Count30��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ30<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount30(long count30) {
		this.Count30 = new Long(count30);
    }

	/**
	* �����ֶ�Count30��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ30<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount30(String count30) {
		if (count30 == null){
			this.Count30 = null;
			return;
		}
		this.Count30 = new Long(count30);
    }

	/**
	* ��ȡ�ֶ�Count31��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ31<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getCount31() {
		if(Count31==null){return 0;}
		return Count31.longValue();
	}

	/**
	* �����ֶ�Count31��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ31<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount31(long count31) {
		this.Count31 = new Long(count31);
    }

	/**
	* �����ֶ�Count31��ֵ�����ֶε�<br>
	* �ֶ����� :ͳ��ֵ31<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount31(String count31) {
		if (count31 == null){
			this.Count31 = null;
			return;
		}
		this.Count31 = new Long(count31);
    }

	/**
	* ��ȡ�ֶ�Memo��ֵ�����ֶε�<br>
	* �ֶ����� :��ע<br>
	* �������� :varchar(4000)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getMemo() {
		return Memo;
	}

	/**
	* �����ֶ�Memo��ֵ�����ֶε�<br>
	* �ֶ����� :��ע<br>
	* �������� :varchar(4000)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setMemo(String memo) {
		this.Memo = memo;
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