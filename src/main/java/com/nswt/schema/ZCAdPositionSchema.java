package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * �����ƣ�����λ��<br>
 * ����룺ZCAdPosition<br>
 * ��������ID<br>
 */
public class ZCAdPositionSchema extends Schema {
	private Long ID;

	private Long SiteID;

	private String PositionName;

	private String Code;

	private String Description;

	private String PositionType;

	private Long PaddingTop;

	private Long PaddingLeft;

	private Long PositionWidth;

	private Long PositionHeight;

	private String Align;

	private String Scroll;

	private String JsName;

	private Long RelaCatalogID;

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
		new SchemaColumn("PositionName", DataColumn.STRING, 2, 100 , 0 , true , false),
		new SchemaColumn("Code", DataColumn.STRING, 3, 50 , 0 , true , false),
		new SchemaColumn("Description", DataColumn.CLOB, 4, 0 , 0 , false , false),
		new SchemaColumn("PositionType", DataColumn.STRING, 5, 20 , 0 , false , false),
		new SchemaColumn("PaddingTop", DataColumn.LONG, 6, 0 , 0 , false , false),
		new SchemaColumn("PaddingLeft", DataColumn.LONG, 7, 0 , 0 , false , false),
		new SchemaColumn("PositionWidth", DataColumn.LONG, 8, 0 , 0 , false , false),
		new SchemaColumn("PositionHeight", DataColumn.LONG, 9, 0 , 0 , false , false),
		new SchemaColumn("Align", DataColumn.STRING, 10, 2 , 0 , false , false),
		new SchemaColumn("Scroll", DataColumn.STRING, 11, 2 , 0 , false , false),
		new SchemaColumn("JsName", DataColumn.STRING, 12, 100 , 0 , false , false),
		new SchemaColumn("RelaCatalogID", DataColumn.LONG, 13, 0 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 14, 50 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 15, 50 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 16, 50 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 17, 50 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 18, 200 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 19, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 20, 200 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 21, 0 , 0 , false , false)
	};

	public static final String _TableCode = "ZCAdPosition";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into ZCAdPosition values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZCAdPosition set ID=?,SiteID=?,PositionName=?,Code=?,Description=?,PositionType=?,PaddingTop=?,PaddingLeft=?,PositionWidth=?,PositionHeight=?,Align=?,Scroll=?,JsName=?,RelaCatalogID=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";

	protected static final String _DeleteSQL = "delete from ZCAdPosition  where ID=?";

	protected static final String _FillAllSQL = "select * from ZCAdPosition  where ID=?";

	public ZCAdPositionSchema(){
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
		return new ZCAdPositionSchema();
	}

	protected SchemaSet newSet(){
		return new ZCAdPositionSet();
	}

	public ZCAdPositionSet query() {
		return query(null, -1, -1);
	}

	public ZCAdPositionSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZCAdPositionSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZCAdPositionSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZCAdPositionSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 2){PositionName = (String)v;return;}
		if (i == 3){Code = (String)v;return;}
		if (i == 4){Description = (String)v;return;}
		if (i == 5){PositionType = (String)v;return;}
		if (i == 6){if(v==null){PaddingTop = null;}else{PaddingTop = new Long(v.toString());}return;}
		if (i == 7){if(v==null){PaddingLeft = null;}else{PaddingLeft = new Long(v.toString());}return;}
		if (i == 8){if(v==null){PositionWidth = null;}else{PositionWidth = new Long(v.toString());}return;}
		if (i == 9){if(v==null){PositionHeight = null;}else{PositionHeight = new Long(v.toString());}return;}
		if (i == 10){Align = (String)v;return;}
		if (i == 11){Scroll = (String)v;return;}
		if (i == 12){JsName = (String)v;return;}
		if (i == 13){if(v==null){RelaCatalogID = null;}else{RelaCatalogID = new Long(v.toString());}return;}
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
		if (i == 1){return SiteID;}
		if (i == 2){return PositionName;}
		if (i == 3){return Code;}
		if (i == 4){return Description;}
		if (i == 5){return PositionType;}
		if (i == 6){return PaddingTop;}
		if (i == 7){return PaddingLeft;}
		if (i == 8){return PositionWidth;}
		if (i == 9){return PositionHeight;}
		if (i == 10){return Align;}
		if (i == 11){return Scroll;}
		if (i == 12){return JsName;}
		if (i == 13){return RelaCatalogID;}
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
	* �ֶ����� :����λID<br>
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
	* �ֶ����� :����λID<br>
	* �������� :bigint<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setID(long iD) {
		this.ID = new Long(iD);
    }

	/**
	* �����ֶ�ID��ֵ�����ֶε�<br>
	* �ֶ����� :����λID<br>
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
	* ��ȡ�ֶ�PositionName��ֵ�����ֶε�<br>
	* �ֶ����� :λ������<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getPositionName() {
		return PositionName;
	}

	/**
	* �����ֶ�PositionName��ֵ�����ֶε�<br>
	* �ֶ����� :λ������<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setPositionName(String positionName) {
		this.PositionName = positionName;
    }

	/**
	* ��ȡ�ֶ�Code��ֵ�����ֶε�<br>
	* �ֶ����� :����λ����<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getCode() {
		return Code;
	}

	/**
	* �����ֶ�Code��ֵ�����ֶε�<br>
	* �ֶ����� :����λ����<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setCode(String code) {
		this.Code = code;
    }

	/**
	* ��ȡ�ֶ�Description��ֵ�����ֶε�<br>
	* �ֶ����� :���<br>
	* �������� :text<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getDescription() {
		return Description;
	}

	/**
	* �����ֶ�Description��ֵ�����ֶε�<br>
	* �ֶ����� :���<br>
	* �������� :text<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setDescription(String description) {
		this.Description = description;
    }

	/**
	* ��ȡ�ֶ�PositionType��ֵ�����ֶε�<br>
	* �ֶ����� :��λ����<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getPositionType() {
		return PositionType;
	}

	/**
	* �����ֶ�PositionType��ֵ�����ֶε�<br>
	* �ֶ����� :��λ����<br>
	* �������� :varchar(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setPositionType(String positionType) {
		this.PositionType = positionType;
    }

	/**
	* ��ȡ�ֶ�PaddingTop��ֵ�����ֶε�<br>
	* �ֶ����� :���λ��(��)<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getPaddingTop() {
		if(PaddingTop==null){return 0;}
		return PaddingTop.longValue();
	}

	/**
	* �����ֶ�PaddingTop��ֵ�����ֶε�<br>
	* �ֶ����� :���λ��(��)<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setPaddingTop(long paddingTop) {
		this.PaddingTop = new Long(paddingTop);
    }

	/**
	* �����ֶ�PaddingTop��ֵ�����ֶε�<br>
	* �ֶ����� :���λ��(��)<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setPaddingTop(String paddingTop) {
		if (paddingTop == null){
			this.PaddingTop = null;
			return;
		}
		this.PaddingTop = new Long(paddingTop);
    }

	/**
	* ��ȡ�ֶ�PaddingLeft��ֵ�����ֶε�<br>
	* �ֶ����� :���λ��(��)<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getPaddingLeft() {
		if(PaddingLeft==null){return 0;}
		return PaddingLeft.longValue();
	}

	/**
	* �����ֶ�PaddingLeft��ֵ�����ֶε�<br>
	* �ֶ����� :���λ��(��)<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setPaddingLeft(long paddingLeft) {
		this.PaddingLeft = new Long(paddingLeft);
    }

	/**
	* �����ֶ�PaddingLeft��ֵ�����ֶε�<br>
	* �ֶ����� :���λ��(��)<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setPaddingLeft(String paddingLeft) {
		if (paddingLeft == null){
			this.PaddingLeft = null;
			return;
		}
		this.PaddingLeft = new Long(paddingLeft);
    }

	/**
	* ��ȡ�ֶ�PositionWidth��ֵ�����ֶε�<br>
	* �ֶ����� :���<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getPositionWidth() {
		if(PositionWidth==null){return 0;}
		return PositionWidth.longValue();
	}

	/**
	* �����ֶ�PositionWidth��ֵ�����ֶε�<br>
	* �ֶ����� :���<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setPositionWidth(long positionWidth) {
		this.PositionWidth = new Long(positionWidth);
    }

	/**
	* �����ֶ�PositionWidth��ֵ�����ֶε�<br>
	* �ֶ����� :���<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setPositionWidth(String positionWidth) {
		if (positionWidth == null){
			this.PositionWidth = null;
			return;
		}
		this.PositionWidth = new Long(positionWidth);
    }

	/**
	* ��ȡ�ֶ�PositionHeight��ֵ�����ֶε�<br>
	* �ֶ����� :�߶�<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getPositionHeight() {
		if(PositionHeight==null){return 0;}
		return PositionHeight.longValue();
	}

	/**
	* �����ֶ�PositionHeight��ֵ�����ֶε�<br>
	* �ֶ����� :�߶�<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setPositionHeight(long positionHeight) {
		this.PositionHeight = new Long(positionHeight);
    }

	/**
	* �����ֶ�PositionHeight��ֵ�����ֶε�<br>
	* �ֶ����� :�߶�<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setPositionHeight(String positionHeight) {
		if (positionHeight == null){
			this.PositionHeight = null;
			return;
		}
		this.PositionHeight = new Long(positionHeight);
    }

	/**
	* ��ȡ�ֶ�Align��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getAlign() {
		return Align;
	}

	/**
	* �����ֶ�Align��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAlign(String align) {
		this.Align = align;
    }

	/**
	* ��ȡ�ֶ�Scroll��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getScroll() {
		return Scroll;
	}

	/**
	* �����ֶ�Scroll��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setScroll(String scroll) {
		this.Scroll = scroll;
    }

	/**
	* ��ȡ�ֶ�JsName��ֵ�����ֶε�<br>
	* �ֶ����� :����js·��<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getJsName() {
		return JsName;
	}

	/**
	* �����ֶ�JsName��ֵ�����ֶε�<br>
	* �ֶ����� :����js·��<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setJsName(String jsName) {
		this.JsName = jsName;
    }

	/**
	* ��ȡ�ֶ�RelaCatalogID��ֵ�����ֶε�<br>
	* �ֶ����� :������Ŀ<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getRelaCatalogID() {
		if(RelaCatalogID==null){return 0;}
		return RelaCatalogID.longValue();
	}

	/**
	* �����ֶ�RelaCatalogID��ֵ�����ֶε�<br>
	* �ֶ����� :������Ŀ<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setRelaCatalogID(long relaCatalogID) {
		this.RelaCatalogID = new Long(relaCatalogID);
    }

	/**
	* �����ֶ�RelaCatalogID��ֵ�����ֶε�<br>
	* �ֶ����� :������Ŀ<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setRelaCatalogID(String relaCatalogID) {
		if (relaCatalogID == null){
			this.RelaCatalogID = null;
			return;
		}
		this.RelaCatalogID = new Long(relaCatalogID);
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