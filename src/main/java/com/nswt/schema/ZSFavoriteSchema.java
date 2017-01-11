package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * �����ƣ���Ա��Ʒ�ղؼб�<br>
 * ����룺ZSFavorite<br>
 * ��������UserName, GoodsID<br>
 */
public class ZSFavoriteSchema extends Schema {
	private String UserName;

	private Long GoodsID;

	private Long SiteID;

	private String PriceNoteFlag;

	private String AddUser;

	private Date AddTime;

	private String ModifyUser;

	private Date ModifyTime;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("UserName", DataColumn.STRING, 0, 200 , 0 , true , true),
		new SchemaColumn("GoodsID", DataColumn.LONG, 1, 0 , 0 , true , true),
		new SchemaColumn("SiteID", DataColumn.LONG, 2, 0 , 0 , false , false),
		new SchemaColumn("PriceNoteFlag", DataColumn.STRING, 3, 2 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 4, 200 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 5, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 6, 200 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 7, 0 , 0 , false , false)
	};

	public static final String _TableCode = "ZSFavorite";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into ZSFavorite values(?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZSFavorite set UserName=?,GoodsID=?,SiteID=?,PriceNoteFlag=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where UserName=? and GoodsID=?";

	protected static final String _DeleteSQL = "delete from ZSFavorite  where UserName=? and GoodsID=?";

	protected static final String _FillAllSQL = "select * from ZSFavorite  where UserName=? and GoodsID=?";

	public ZSFavoriteSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[8];
	}

	protected Schema newInstance(){
		return new ZSFavoriteSchema();
	}

	protected SchemaSet newSet(){
		return new ZSFavoriteSet();
	}

	public ZSFavoriteSet query() {
		return query(null, -1, -1);
	}

	public ZSFavoriteSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZSFavoriteSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZSFavoriteSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZSFavoriteSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){UserName = (String)v;return;}
		if (i == 1){if(v==null){GoodsID = null;}else{GoodsID = new Long(v.toString());}return;}
		if (i == 2){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 3){PriceNoteFlag = (String)v;return;}
		if (i == 4){AddUser = (String)v;return;}
		if (i == 5){AddTime = (Date)v;return;}
		if (i == 6){ModifyUser = (String)v;return;}
		if (i == 7){ModifyTime = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return UserName;}
		if (i == 1){return GoodsID;}
		if (i == 2){return SiteID;}
		if (i == 3){return PriceNoteFlag;}
		if (i == 4){return AddUser;}
		if (i == 5){return AddTime;}
		if (i == 6){return ModifyUser;}
		if (i == 7){return ModifyTime;}
		return null;
	}

	/**
	* ��ȡ�ֶ�UserName��ֵ�����ֶε�<br>
	* �ֶ����� :��Ա����<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public String getUserName() {
		return UserName;
	}

	/**
	* �����ֶ�UserName��ֵ�����ֶε�<br>
	* �ֶ����� :��Ա����<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setUserName(String userName) {
		this.UserName = userName;
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
	* �ֶ����� :վ��ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
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
	* �Ƿ���� :false<br>
	*/
	public void setSiteID(long siteID) {
		this.SiteID = new Long(siteID);
    }

	/**
	* �����ֶ�SiteID��ֵ�����ֶε�<br>
	* �ֶ����� :վ��ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setSiteID(String siteID) {
		if (siteID == null){
			this.SiteID = null;
			return;
		}
		this.SiteID = new Long(siteID);
    }

	/**
	* ��ȡ�ֶ�PriceNoteFlag��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ񽵼�����<br>
	* �������� :char(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getPriceNoteFlag() {
		return PriceNoteFlag;
	}

	/**
	* �����ֶ�PriceNoteFlag��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ񽵼�����<br>
	* �������� :char(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setPriceNoteFlag(String priceNoteFlag) {
		this.PriceNoteFlag = priceNoteFlag;
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