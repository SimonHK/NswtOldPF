package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * �����ƣ����ݿ�ɼ���<br>
 * ����룺ZCDBGather<br>
 * ��������ID<br>
 */
public class ZCDBGatherSchema extends Schema {
	private Long ID;

	private Long SiteID;

	private String Name;

	private Long DatabaseID;

	private String TableName;

	private Long CatalogID;

	private Long ArticleStatus;

	private String PathReplacePartOld;

	private String PathReplacePartNew;

	private String NewRecordRule;

	private String SQLCondition;

	private String Status;

	private String MappingConfig;

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
		new SchemaColumn("Name", DataColumn.STRING, 2, 200 , 0 , true , false),
		new SchemaColumn("DatabaseID", DataColumn.LONG, 3, 0 , 0 , true , false),
		new SchemaColumn("TableName", DataColumn.STRING, 4, 50 , 0 , true , false),
		new SchemaColumn("CatalogID", DataColumn.LONG, 5, 0 , 0 , true , false),
		new SchemaColumn("ArticleStatus", DataColumn.LONG, 6, 0 , 0 , true , false),
		new SchemaColumn("PathReplacePartOld", DataColumn.STRING, 7, 200 , 0 , false , false),
		new SchemaColumn("PathReplacePartNew", DataColumn.STRING, 8, 200 , 0 , false , false),
		new SchemaColumn("NewRecordRule", DataColumn.STRING, 9, 200 , 0 , false , false),
		new SchemaColumn("SQLCondition", DataColumn.STRING, 10, 200 , 0 , false , false),
		new SchemaColumn("Status", DataColumn.STRING, 11, 2 , 0 , false , false),
		new SchemaColumn("MappingConfig", DataColumn.CLOB, 12, 0 , 0 , true , false),
		new SchemaColumn("Memo", DataColumn.STRING, 13, 400 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 14, 50 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 15, 50 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 16, 50 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 17, 50 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 18, 200 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 19, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 20, 200 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 21, 0 , 0 , false , false)
	};

	public static final String _TableCode = "ZCDBGather";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into ZCDBGather values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZCDBGather set ID=?,SiteID=?,Name=?,DatabaseID=?,TableName=?,CatalogID=?,ArticleStatus=?,PathReplacePartOld=?,PathReplacePartNew=?,NewRecordRule=?,SQLCondition=?,Status=?,MappingConfig=?,Memo=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";

	protected static final String _DeleteSQL = "delete from ZCDBGather  where ID=?";

	protected static final String _FillAllSQL = "select * from ZCDBGather  where ID=?";

	public ZCDBGatherSchema(){
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
		return new ZCDBGatherSchema();
	}

	protected SchemaSet newSet(){
		return new ZCDBGatherSet();
	}

	public ZCDBGatherSet query() {
		return query(null, -1, -1);
	}

	public ZCDBGatherSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZCDBGatherSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZCDBGatherSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZCDBGatherSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 2){Name = (String)v;return;}
		if (i == 3){if(v==null){DatabaseID = null;}else{DatabaseID = new Long(v.toString());}return;}
		if (i == 4){TableName = (String)v;return;}
		if (i == 5){if(v==null){CatalogID = null;}else{CatalogID = new Long(v.toString());}return;}
		if (i == 6){if(v==null){ArticleStatus = null;}else{ArticleStatus = new Long(v.toString());}return;}
		if (i == 7){PathReplacePartOld = (String)v;return;}
		if (i == 8){PathReplacePartNew = (String)v;return;}
		if (i == 9){NewRecordRule = (String)v;return;}
		if (i == 10){SQLCondition = (String)v;return;}
		if (i == 11){Status = (String)v;return;}
		if (i == 12){MappingConfig = (String)v;return;}
		if (i == 13){Memo = (String)v;return;}
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
		if (i == 2){return Name;}
		if (i == 3){return DatabaseID;}
		if (i == 4){return TableName;}
		if (i == 5){return CatalogID;}
		if (i == 6){return ArticleStatus;}
		if (i == 7){return PathReplacePartOld;}
		if (i == 8){return PathReplacePartNew;}
		if (i == 9){return NewRecordRule;}
		if (i == 10){return SQLCondition;}
		if (i == 11){return Status;}
		if (i == 12){return MappingConfig;}
		if (i == 13){return Memo;}
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
	* ��ȡ�ֶ�Name��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getName() {
		return Name;
	}

	/**
	* �����ֶ�Name��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setName(String name) {
		this.Name = name;
    }

	/**
	* ��ȡ�ֶ�DatabaseID��ֵ�����ֶε�<br>
	* �ֶ����� :�ⲿ���ݿ�ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public long getDatabaseID() {
		if(DatabaseID==null){return 0;}
		return DatabaseID.longValue();
	}

	/**
	* �����ֶ�DatabaseID��ֵ�����ֶε�<br>
	* �ֶ����� :�ⲿ���ݿ�ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setDatabaseID(long databaseID) {
		this.DatabaseID = new Long(databaseID);
    }

	/**
	* �����ֶ�DatabaseID��ֵ�����ֶε�<br>
	* �ֶ����� :�ⲿ���ݿ�ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setDatabaseID(String databaseID) {
		if (databaseID == null){
			this.DatabaseID = null;
			return;
		}
		this.DatabaseID = new Long(databaseID);
    }

	/**
	* ��ȡ�ֶ�TableName��ֵ�����ֶε�<br>
	* �ֶ����� :�ⲿ���ݱ�����<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getTableName() {
		return TableName;
	}

	/**
	* �����ֶ�TableName��ֵ�����ֶε�<br>
	* �ֶ����� :�ⲿ���ݱ�����<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setTableName(String tableName) {
		this.TableName = tableName;
    }

	/**
	* ��ȡ�ֶ�CatalogID��ֵ�����ֶε�<br>
	* �ֶ����� :�ɼ�����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public long getCatalogID() {
		if(CatalogID==null){return 0;}
		return CatalogID.longValue();
	}

	/**
	* �����ֶ�CatalogID��ֵ�����ֶε�<br>
	* �ֶ����� :�ɼ�����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setCatalogID(long catalogID) {
		this.CatalogID = new Long(catalogID);
    }

	/**
	* �����ֶ�CatalogID��ֵ�����ֶε�<br>
	* �ֶ����� :�ɼ�����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setCatalogID(String catalogID) {
		if (catalogID == null){
			this.CatalogID = null;
			return;
		}
		this.CatalogID = new Long(catalogID);
    }

	/**
	* ��ȡ�ֶ�ArticleStatus��ֵ�����ֶε�<br>
	* �ֶ����� :�ɼ�����ĵ�״̬<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public long getArticleStatus() {
		if(ArticleStatus==null){return 0;}
		return ArticleStatus.longValue();
	}

	/**
	* �����ֶ�ArticleStatus��ֵ�����ֶε�<br>
	* �ֶ����� :�ɼ�����ĵ�״̬<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setArticleStatus(long articleStatus) {
		this.ArticleStatus = new Long(articleStatus);
    }

	/**
	* �����ֶ�ArticleStatus��ֵ�����ֶε�<br>
	* �ֶ����� :�ɼ�����ĵ�״̬<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setArticleStatus(String articleStatus) {
		if (articleStatus == null){
			this.ArticleStatus = null;
			return;
		}
		this.ArticleStatus = new Long(articleStatus);
    }

	/**
	* ��ȡ�ֶ�PathReplacePartOld��ֵ�����ֶε�<br>
	* �ֶ����� :�ļ�·�������滻��ֵ<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getPathReplacePartOld() {
		return PathReplacePartOld;
	}

	/**
	* �����ֶ�PathReplacePartOld��ֵ�����ֶε�<br>
	* �ֶ����� :�ļ�·�������滻��ֵ<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setPathReplacePartOld(String pathReplacePartOld) {
		this.PathReplacePartOld = pathReplacePartOld;
    }

	/**
	* ��ȡ�ֶ�PathReplacePartNew��ֵ�����ֶε�<br>
	* �ֶ����� :�ļ�·�������滻��ֵ<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getPathReplacePartNew() {
		return PathReplacePartNew;
	}

	/**
	* �����ֶ�PathReplacePartNew��ֵ�����ֶε�<br>
	* �ֶ����� :�ļ�·�������滻��ֵ<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setPathReplacePartNew(String pathReplacePartNew) {
		this.PathReplacePartNew = pathReplacePartNew;
    }

	/**
	* ��ȡ�ֶ�NewRecordRule��ֵ�����ֶε�<br>
	* �ֶ����� :��¼���¹���<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getNewRecordRule() {
		return NewRecordRule;
	}

	/**
	* �����ֶ�NewRecordRule��ֵ�����ֶε�<br>
	* �ֶ����� :��¼���¹���<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setNewRecordRule(String newRecordRule) {
		this.NewRecordRule = newRecordRule;
    }

	/**
	* ��ȡ�ֶ�SQLCondition��ֵ�����ֶε�<br>
	* �ֶ����� :��ѯ����<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getSQLCondition() {
		return SQLCondition;
	}

	/**
	* �����ֶ�SQLCondition��ֵ�����ֶε�<br>
	* �ֶ����� :��ѯ����<br>
	* �������� :varchar(200)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setSQLCondition(String sQLCondition) {
		this.SQLCondition = sQLCondition;
    }

	/**
	* ��ȡ�ֶ�Status��ֵ�����ֶε�<br>
	* �ֶ����� :״̬<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getStatus() {
		return Status;
	}

	/**
	* �����ֶ�Status��ֵ�����ֶε�<br>
	* �ֶ����� :״̬<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setStatus(String status) {
		this.Status = status;
    }

	/**
	* ��ȡ�ֶ�MappingConfig��ֵ�����ֶε�<br>
	* �ֶ����� :�ֶ�ӳ������<br>
	* �������� :text<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getMappingConfig() {
		return MappingConfig;
	}

	/**
	* �����ֶ�MappingConfig��ֵ�����ֶε�<br>
	* �ֶ����� :�ֶ�ӳ������<br>
	* �������� :text<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setMappingConfig(String mappingConfig) {
		this.MappingConfig = mappingConfig;
    }

	/**
	* ��ȡ�ֶ�Memo��ֵ�����ֶε�<br>
	* �ֶ����� :��ע<br>
	* �������� :varchar(400)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getMemo() {
		return Memo;
	}

	/**
	* �����ֶ�Memo��ֵ�����ֶε�<br>
	* �ֶ����� :��ע<br>
	* �������� :varchar(400)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setMemo(String memo) {
		this.Memo = memo;
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