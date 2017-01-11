package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * �����ƣ��������<br>
 * ����룺BZSOrderItem<br>
 * ��������OrderID, GoodsID, BackupNo<br>
 */
public class BZSOrderItemSchema extends Schema {
	private Long OrderID;

	private Long GoodsID;

	private Long SiteID;

	private String UserName;

	private String SN;

	private String Name;

	private Float Price;

	private Float Discount;

	private Float DiscountPrice;

	private Long Count;

	private Float Amount;

	private Long Score;

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
		new SchemaColumn("OrderID", DataColumn.LONG, 0, 0 , 0 , true , true),
		new SchemaColumn("GoodsID", DataColumn.LONG, 1, 0 , 0 , true , true),
		new SchemaColumn("SiteID", DataColumn.LONG, 2, 0 , 0 , true , false),
		new SchemaColumn("UserName", DataColumn.STRING, 3, 200 , 0 , false , false),
		new SchemaColumn("SN", DataColumn.STRING, 4, 50 , 0 , false , false),
		new SchemaColumn("Name", DataColumn.STRING, 5, 200 , 0 , false , false),
		new SchemaColumn("Price", DataColumn.FLOAT, 6, 12 , 2 , false , false),
		new SchemaColumn("Discount", DataColumn.FLOAT, 7, 12 , 2 , false , false),
		new SchemaColumn("DiscountPrice", DataColumn.FLOAT, 8, 12 , 2 , false , false),
		new SchemaColumn("Count", DataColumn.LONG, 9, 0 , 0 , false , false),
		new SchemaColumn("Amount", DataColumn.FLOAT, 10, 0 , 0 , false , false),
		new SchemaColumn("Score", DataColumn.LONG, 11, 0 , 0 , false , false),
		new SchemaColumn("Memo", DataColumn.STRING, 12, 200 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 13, 200 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 14, 200 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 15, 200 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 16, 200 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 17, 200 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 18, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 19, 200 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 20, 0 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 21, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 22, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 23, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 24, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZSOrderItem";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into BZSOrderItem values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZSOrderItem set OrderID=?,GoodsID=?,SiteID=?,UserName=?,SN=?,Name=?,Price=?,Discount=?,DiscountPrice=?,Count=?,Amount=?,Score=?,Memo=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where OrderID=? and GoodsID=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZSOrderItem  where OrderID=? and GoodsID=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZSOrderItem  where OrderID=? and GoodsID=? and BackupNo=?";

	public BZSOrderItemSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[25];
	}

	protected Schema newInstance(){
		return new BZSOrderItemSchema();
	}

	protected SchemaSet newSet(){
		return new BZSOrderItemSet();
	}

	public BZSOrderItemSet query() {
		return query(null, -1, -1);
	}

	public BZSOrderItemSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZSOrderItemSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZSOrderItemSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZSOrderItemSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){OrderID = null;}else{OrderID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){GoodsID = null;}else{GoodsID = new Long(v.toString());}return;}
		if (i == 2){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 3){UserName = (String)v;return;}
		if (i == 4){SN = (String)v;return;}
		if (i == 5){Name = (String)v;return;}
		if (i == 6){if(v==null){Price = null;}else{Price = new Float(v.toString());}return;}
		if (i == 7){if(v==null){Discount = null;}else{Discount = new Float(v.toString());}return;}
		if (i == 8){if(v==null){DiscountPrice = null;}else{DiscountPrice = new Float(v.toString());}return;}
		if (i == 9){if(v==null){Count = null;}else{Count = new Long(v.toString());}return;}
		if (i == 10){if(v==null){Amount = null;}else{Amount = new Float(v.toString());}return;}
		if (i == 11){if(v==null){Score = null;}else{Score = new Long(v.toString());}return;}
		if (i == 12){Memo = (String)v;return;}
		if (i == 13){Prop1 = (String)v;return;}
		if (i == 14){Prop2 = (String)v;return;}
		if (i == 15){Prop3 = (String)v;return;}
		if (i == 16){Prop4 = (String)v;return;}
		if (i == 17){AddUser = (String)v;return;}
		if (i == 18){AddTime = (Date)v;return;}
		if (i == 19){ModifyUser = (String)v;return;}
		if (i == 20){ModifyTime = (Date)v;return;}
		if (i == 21){BackupNo = (String)v;return;}
		if (i == 22){BackupOperator = (String)v;return;}
		if (i == 23){BackupTime = (Date)v;return;}
		if (i == 24){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return OrderID;}
		if (i == 1){return GoodsID;}
		if (i == 2){return SiteID;}
		if (i == 3){return UserName;}
		if (i == 4){return SN;}
		if (i == 5){return Name;}
		if (i == 6){return Price;}
		if (i == 7){return Discount;}
		if (i == 8){return DiscountPrice;}
		if (i == 9){return Count;}
		if (i == 10){return Amount;}
		if (i == 11){return Score;}
		if (i == 12){return Memo;}
		if (i == 13){return Prop1;}
		if (i == 14){return Prop2;}
		if (i == 15){return Prop3;}
		if (i == 16){return Prop4;}
		if (i == 17){return AddUser;}
		if (i == 18){return AddTime;}
		if (i == 19){return ModifyUser;}
		if (i == 20){return ModifyTime;}
		if (i == 21){return BackupNo;}
		if (i == 22){return BackupOperator;}
		if (i == 23){return BackupTime;}
		if (i == 24){return BackupMemo;}
		return null;
	}

	/**
	* ��ȡ�ֶ�OrderID��ֵ�����ֶε�<br>
	* �ֶ����� :����ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public long getOrderID() {
		if(OrderID==null){return 0;}
		return OrderID.longValue();
	}

	/**
	* �����ֶ�OrderID��ֵ�����ֶε�<br>
	* �ֶ����� :����ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setOrderID(long orderID) {
		this.OrderID = new Long(orderID);
    }

	/**
	* �����ֶ�OrderID��ֵ�����ֶε�<br>
	* �ֶ����� :����ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setOrderID(String orderID) {
		if (orderID == null){
			this.OrderID = null;
			return;
		}
		this.OrderID = new Long(orderID);
    }

	/**
	* ��ȡ�ֶ�GoodsID��ֵ�����ֶε�<br>
	* �ֶ����� :��ƷID<br>
	* �������� :bigint<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public long getGoodsID() {
		if(GoodsID==null){return 0;}
		return GoodsID.longValue();
	}

	/**
	* �����ֶ�GoodsID��ֵ�����ֶε�<br>
	* �ֶ����� :��ƷID<br>
	* �������� :bigint<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setGoodsID(long goodsID) {
		this.GoodsID = new Long(goodsID);
    }

	/**
	* �����ֶ�GoodsID��ֵ�����ֶε�<br>
	* �ֶ����� :��ƷID<br>
	* �������� :bigint<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setGoodsID(String goodsID) {
		if (goodsID == null){
			this.GoodsID = null;
			return;
		}
		this.GoodsID = new Long(goodsID);
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
	* ��ȡ�ֶ�SN��ֵ�����ֶε�<br>
	* �ֶ����� :���<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getSN() {
		return SN;
	}

	/**
	* �����ֶ�SN��ֵ�����ֶε�<br>
	* �ֶ����� :���<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setSN(String sN) {
		this.SN = sN;
    }

	/**
	* ��ȡ�ֶ�Name��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getName() {
		return Name;
	}

	/**
	* �����ֶ�Name��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setName(String name) {
		this.Name = name;
    }

	/**
	* ��ȡ�ֶ�Price��ֵ�����ֶε�<br>
	* �ֶ����� :�۸�<br>
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
	* �ֶ����� :�۸�<br>
	* �������� :float(12,2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setPrice(float price) {
		this.Price = new Float(price);
    }

	/**
	* �����ֶ�Price��ֵ�����ֶε�<br>
	* �ֶ����� :�۸�<br>
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
	* ��ȡ�ֶ�Discount��ֵ�����ֶε�<br>
	* �ֶ����� :�ۿ�<br>
	* �������� :float(12,2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public float getDiscount() {
		if(Discount==null){return 0;}
		return Discount.floatValue();
	}

	/**
	* �����ֶ�Discount��ֵ�����ֶε�<br>
	* �ֶ����� :�ۿ�<br>
	* �������� :float(12,2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setDiscount(float discount) {
		this.Discount = new Float(discount);
    }

	/**
	* �����ֶ�Discount��ֵ�����ֶε�<br>
	* �ֶ����� :�ۿ�<br>
	* �������� :float(12,2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setDiscount(String discount) {
		if (discount == null){
			this.Discount = null;
			return;
		}
		this.Discount = new Float(discount);
    }

	/**
	* ��ȡ�ֶ�DiscountPrice��ֵ�����ֶε�<br>
	* �ֶ����� :�ۿۼ�<br>
	* �������� :float(12,2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public float getDiscountPrice() {
		if(DiscountPrice==null){return 0;}
		return DiscountPrice.floatValue();
	}

	/**
	* �����ֶ�DiscountPrice��ֵ�����ֶε�<br>
	* �ֶ����� :�ۿۼ�<br>
	* �������� :float(12,2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setDiscountPrice(float discountPrice) {
		this.DiscountPrice = new Float(discountPrice);
    }

	/**
	* �����ֶ�DiscountPrice��ֵ�����ֶε�<br>
	* �ֶ����� :�ۿۼ�<br>
	* �������� :float(12,2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setDiscountPrice(String discountPrice) {
		if (discountPrice == null){
			this.DiscountPrice = null;
			return;
		}
		this.DiscountPrice = new Float(discountPrice);
    }

	/**
	* ��ȡ�ֶ�Count��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getCount() {
		if(Count==null){return 0;}
		return Count.longValue();
	}

	/**
	* �����ֶ�Count��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount(long count) {
		this.Count = new Long(count);
    }

	/**
	* �����ֶ�Count��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCount(String count) {
		if (count == null){
			this.Count = null;
			return;
		}
		this.Count = new Long(count);
    }

	/**
	* ��ȡ�ֶ�Amount��ֵ�����ֶε�<br>
	* �ֶ����� :С��<br>
	* �������� :float<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public float getAmount() {
		if(Amount==null){return 0;}
		return Amount.floatValue();
	}

	/**
	* �����ֶ�Amount��ֵ�����ֶε�<br>
	* �ֶ����� :С��<br>
	* �������� :float<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAmount(float amount) {
		this.Amount = new Float(amount);
    }

	/**
	* �����ֶ�Amount��ֵ�����ֶε�<br>
	* �ֶ����� :С��<br>
	* �������� :float<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAmount(String amount) {
		if (amount == null){
			this.Amount = null;
			return;
		}
		this.Amount = new Float(amount);
    }

	/**
	* ��ȡ�ֶ�Score��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
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
	* �Ƿ���� :false<br>
	*/
	public void setScore(long score) {
		this.Score = new Long(score);
    }

	/**
	* �����ֶ�Score��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setScore(String score) {
		if (score == null){
			this.Score = null;
			return;
		}
		this.Score = new Long(score);
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