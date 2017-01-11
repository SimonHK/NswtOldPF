package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * �����ƣ���Ա�����<br>
 * ����룺ZDMemberLevel<br>
 * ��������ID<br>
 */
public class ZDMemberLevelSchema extends Schema {
	private Long ID;

	private String Name;

	private String Type;

	private Float Discount;

	private String IsDefault;

	private Integer TreeLevel;

	private Long Score;

	private String IsValidate;

	private String AddUser;

	private Date AddTime;

	private String ModifyUser;

	private Date ModifyTime;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 0 , 0 , true , true),
		new SchemaColumn("Name", DataColumn.STRING, 1, 100 , 0 , true , false),
		new SchemaColumn("Type", DataColumn.STRING, 2, 10 , 0 , true , false),
		new SchemaColumn("Discount", DataColumn.FLOAT, 3, 3 , 1 , true , false),
		new SchemaColumn("IsDefault", DataColumn.STRING, 4, 1 , 0 , true , false),
		new SchemaColumn("TreeLevel", DataColumn.INTEGER, 5, 0 , 0 , true , false),
		new SchemaColumn("Score", DataColumn.LONG, 6, 0 , 0 , true , false),
		new SchemaColumn("IsValidate", DataColumn.STRING, 7, 1 , 0 , true , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 8, 200 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 9, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 10, 200 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 11, 0 , 0 , false , false)
	};

	public static final String _TableCode = "ZDMemberLevel";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into ZDMemberLevel values(?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZDMemberLevel set ID=?,Name=?,Type=?,Discount=?,IsDefault=?,TreeLevel=?,Score=?,IsValidate=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";

	protected static final String _DeleteSQL = "delete from ZDMemberLevel  where ID=?";

	protected static final String _FillAllSQL = "select * from ZDMemberLevel  where ID=?";

	public ZDMemberLevelSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[12];
	}

	protected Schema newInstance(){
		return new ZDMemberLevelSchema();
	}

	protected SchemaSet newSet(){
		return new ZDMemberLevelSet();
	}

	public ZDMemberLevelSet query() {
		return query(null, -1, -1);
	}

	public ZDMemberLevelSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZDMemberLevelSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZDMemberLevelSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZDMemberLevelSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){Name = (String)v;return;}
		if (i == 2){Type = (String)v;return;}
		if (i == 3){if(v==null){Discount = null;}else{Discount = new Float(v.toString());}return;}
		if (i == 4){IsDefault = (String)v;return;}
		if (i == 5){if(v==null){TreeLevel = null;}else{TreeLevel = new Integer(v.toString());}return;}
		if (i == 6){if(v==null){Score = null;}else{Score = new Long(v.toString());}return;}
		if (i == 7){IsValidate = (String)v;return;}
		if (i == 8){AddUser = (String)v;return;}
		if (i == 9){AddTime = (Date)v;return;}
		if (i == 10){ModifyUser = (String)v;return;}
		if (i == 11){ModifyTime = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return Name;}
		if (i == 2){return Type;}
		if (i == 3){return Discount;}
		if (i == 4){return IsDefault;}
		if (i == 5){return TreeLevel;}
		if (i == 6){return Score;}
		if (i == 7){return IsValidate;}
		if (i == 8){return AddUser;}
		if (i == 9){return AddTime;}
		if (i == 10){return ModifyUser;}
		if (i == 11){return ModifyTime;}
		return null;
	}

	/**
	* ��ȡ�ֶ�ID��ֵ�����ֶε�<br>
	* �ֶ����� :��Ա����ID<br>
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
	* �ֶ����� :��Ա����ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setID(long iD) {
		this.ID = new Long(iD);
    }

	/**
	* �����ֶ�ID��ֵ�����ֶε�<br>
	* �ֶ����� :��Ա����ID<br>
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
	* ��ȡ�ֶ�Name��ֵ�����ֶε�<br>
	* �ֶ����� :��Ա��������<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getName() {
		return Name;
	}

	/**
	* �����ֶ�Name��ֵ�����ֶε�<br>
	* �ֶ����� :��Ա��������<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setName(String name) {
		this.Name = name;
    }

	/**
	* ��ȡ�ֶ�Type��ֵ�����ֶε�<br>
	* �ֶ����� :��Ա��������<br>
	* �������� :varchar(10)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getType() {
		return Type;
	}

	/**
	* �����ֶ�Type��ֵ�����ֶε�<br>
	* �ֶ����� :��Ա��������<br>
	* �������� :varchar(10)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setType(String type) {
		this.Type = type;
    }

	/**
	* ��ȡ�ֶ�Discount��ֵ�����ֶε�<br>
	* �ֶ����� :�ۿ�<br>
	* �������� :float(3,1)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public float getDiscount() {
		if(Discount==null){return 0;}
		return Discount.floatValue();
	}

	/**
	* �����ֶ�Discount��ֵ�����ֶε�<br>
	* �ֶ����� :�ۿ�<br>
	* �������� :float(3,1)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setDiscount(float discount) {
		this.Discount = new Float(discount);
    }

	/**
	* �����ֶ�Discount��ֵ�����ֶε�<br>
	* �ֶ����� :�ۿ�<br>
	* �������� :float(3,1)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setDiscount(String discount) {
		if (discount == null){
			this.Discount = null;
			return;
		}
		this.Discount = new Float(discount);
    }

	/**
	* ��ȡ�ֶ�IsDefault��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ�Ĭ��<br>
	* �������� :char(1)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getIsDefault() {
		return IsDefault;
	}

	/**
	* �����ֶ�IsDefault��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ�Ĭ��<br>
	* �������� :char(1)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setIsDefault(String isDefault) {
		this.IsDefault = isDefault;
    }

	/**
	* ��ȡ�ֶ�TreeLevel��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :smallint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public int getTreeLevel() {
		if(TreeLevel==null){return 0;}
		return TreeLevel.intValue();
	}

	/**
	* �����ֶ�TreeLevel��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :smallint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setTreeLevel(int treeLevel) {
		this.TreeLevel = new Integer(treeLevel);
    }

	/**
	* �����ֶ�TreeLevel��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :smallint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setTreeLevel(String treeLevel) {
		if (treeLevel == null){
			this.TreeLevel = null;
			return;
		}
		this.TreeLevel = new Integer(treeLevel);
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
	* ��ȡ�ֶ�IsValidate��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ���Ч<br>
	* �������� :char(1)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getIsValidate() {
		return IsValidate;
	}

	/**
	* �����ֶ�IsValidate��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ���Ч<br>
	* �������� :char(1)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setIsValidate(String isValidate) {
		this.IsValidate = isValidate;
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