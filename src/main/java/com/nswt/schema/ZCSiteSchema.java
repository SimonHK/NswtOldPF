package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * �����ƣ�վ���<br>
 * ����룺ZCSite<br>
 * ��������ID<br>
 */
public class ZCSiteSchema extends Schema {
	private Long ID;

	private String Name;

	private String Alias;

	private String Info;

	private String BranchInnerCode;

	private String URL;

	private String RootPath;

	private String IndexTemplate;

	private String ListTemplate;

	private String DetailTemplate;

	private String EditorCss;

	private String Workflow;

	private Long OrderFlag;

	private String LogoFileName;

	private String MessageBoardFlag;

	private String CommentAuditFlag;

	private Long ChannelCount;

	private Long MagzineCount;

	private Long SpecialCount;

	private Long ImageLibCount;

	private Long VideoLibCount;

	private Long AudioLibCount;

	private Long AttachmentLibCount;

	private Long ArticleCount;

	private Long HitCount;

	private String ConfigXML;

	private String AutoIndexFlag;

	private String AutoStatFlag;

	private String HeaderTemplate;

	private String TopTemplate;

	private String BottomTemplate;

	private String AllowContribute;

	private String BBSEnableFlag;

	private String ShopEnableFlag;

	private String Meta_Keywords;

	private String Meta_Description;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	private String Prop4;

	private String Prop5;

	private String Prop6;

	private String AddUser;

	private Date AddTime;

	private String ModifyUser;

	private Date ModifyTime;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 0 , 0 , true , true),
		new SchemaColumn("Name", DataColumn.STRING, 1, 100 , 0 , true , false),
		new SchemaColumn("Alias", DataColumn.STRING, 2, 100 , 0 , true , false),
		new SchemaColumn("Info", DataColumn.STRING, 3, 100 , 0 , false , false),
		new SchemaColumn("BranchInnerCode", DataColumn.STRING, 4, 100 , 0 , true , false),
		new SchemaColumn("URL", DataColumn.STRING, 5, 100 , 0 , true , false),
		new SchemaColumn("RootPath", DataColumn.STRING, 6, 100 , 0 , false , false),
		new SchemaColumn("IndexTemplate", DataColumn.STRING, 7, 100 , 0 , false , false),
		new SchemaColumn("ListTemplate", DataColumn.STRING, 8, 100 , 0 , false , false),
		new SchemaColumn("DetailTemplate", DataColumn.STRING, 9, 100 , 0 , false , false),
		new SchemaColumn("EditorCss", DataColumn.STRING, 10, 100 , 0 , false , false),
		new SchemaColumn("Workflow", DataColumn.STRING, 11, 100 , 0 , false , false),
		new SchemaColumn("OrderFlag", DataColumn.LONG, 12, 0 , 0 , false , false),
		new SchemaColumn("LogoFileName", DataColumn.STRING, 13, 100 , 0 , false , false),
		new SchemaColumn("MessageBoardFlag", DataColumn.STRING, 14, 2 , 0 , false , false),
		new SchemaColumn("CommentAuditFlag", DataColumn.STRING, 15, 1 , 0 , false , false),
		new SchemaColumn("ChannelCount", DataColumn.LONG, 16, 0 , 0 , true , false),
		new SchemaColumn("MagzineCount", DataColumn.LONG, 17, 0 , 0 , true , false),
		new SchemaColumn("SpecialCount", DataColumn.LONG, 18, 0 , 0 , true , false),
		new SchemaColumn("ImageLibCount", DataColumn.LONG, 19, 0 , 0 , true , false),
		new SchemaColumn("VideoLibCount", DataColumn.LONG, 20, 0 , 0 , true , false),
		new SchemaColumn("AudioLibCount", DataColumn.LONG, 21, 0 , 0 , true , false),
		new SchemaColumn("AttachmentLibCount", DataColumn.LONG, 22, 0 , 0 , true , false),
		new SchemaColumn("ArticleCount", DataColumn.LONG, 23, 0 , 0 , true , false),
		new SchemaColumn("HitCount", DataColumn.LONG, 24, 0 , 0 , true , false),
		new SchemaColumn("ConfigXML", DataColumn.CLOB, 25, 0 , 0 , false , false),
		new SchemaColumn("AutoIndexFlag", DataColumn.STRING, 26, 2 , 0 , false , false),
		new SchemaColumn("AutoStatFlag", DataColumn.STRING, 27, 2 , 0 , false , false),
		new SchemaColumn("HeaderTemplate", DataColumn.STRING, 28, 100 , 0 , false , false),
		new SchemaColumn("TopTemplate", DataColumn.STRING, 29, 100 , 0 , false , false),
		new SchemaColumn("BottomTemplate", DataColumn.STRING, 30, 100 , 0 , false , false),
		new SchemaColumn("AllowContribute", DataColumn.STRING, 31, 2 , 0 , false , false),
		new SchemaColumn("BBSEnableFlag", DataColumn.STRING, 32, 2 , 0 , false , false),
		new SchemaColumn("ShopEnableFlag", DataColumn.STRING, 33, 2 , 0 , false , false),
		new SchemaColumn("Meta_Keywords", DataColumn.STRING, 34, 1000 , 0 , false , false),
		new SchemaColumn("Meta_Description", DataColumn.STRING, 35, 1000 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 36, 100 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 37, 100 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 38, 100 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 39, 100 , 0 , false , false),
		new SchemaColumn("Prop5", DataColumn.STRING, 40, 100 , 0 , false , false),
		new SchemaColumn("Prop6", DataColumn.STRING, 41, 100 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 42, 200 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 43, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 44, 200 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 45, 0 , 0 , false , false)
	};

	public static final String _TableCode = "ZCSite";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into ZCSite values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZCSite set ID=?,Name=?,Alias=?,Info=?,BranchInnerCode=?,URL=?,RootPath=?,IndexTemplate=?,ListTemplate=?,DetailTemplate=?,EditorCss=?,Workflow=?,OrderFlag=?,LogoFileName=?,MessageBoardFlag=?,CommentAuditFlag=?,ChannelCount=?,MagzineCount=?,SpecialCount=?,ImageLibCount=?,VideoLibCount=?,AudioLibCount=?,AttachmentLibCount=?,ArticleCount=?,HitCount=?,ConfigXML=?,AutoIndexFlag=?,AutoStatFlag=?,HeaderTemplate=?,TopTemplate=?,BottomTemplate=?,AllowContribute=?,BBSEnableFlag=?,ShopEnableFlag=?,Meta_Keywords=?,Meta_Description=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,Prop5=?,Prop6=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";

	protected static final String _DeleteSQL = "delete from ZCSite  where ID=?";

	protected static final String _FillAllSQL = "select * from ZCSite  where ID=?";

	public ZCSiteSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[46];
	}

	protected Schema newInstance(){
		return new ZCSiteSchema();
	}

	protected SchemaSet newSet(){
		return new ZCSiteSet();
	}

	public ZCSiteSet query() {
		return query(null, -1, -1);
	}

	public ZCSiteSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZCSiteSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZCSiteSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZCSiteSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){Name = (String)v;return;}
		if (i == 2){Alias = (String)v;return;}
		if (i == 3){Info = (String)v;return;}
		if (i == 4){BranchInnerCode = (String)v;return;}
		if (i == 5){URL = (String)v;return;}
		if (i == 6){RootPath = (String)v;return;}
		if (i == 7){IndexTemplate = (String)v;return;}
		if (i == 8){ListTemplate = (String)v;return;}
		if (i == 9){DetailTemplate = (String)v;return;}
		if (i == 10){EditorCss = (String)v;return;}
		if (i == 11){Workflow = (String)v;return;}
		if (i == 12){if(v==null){OrderFlag = null;}else{OrderFlag = new Long(v.toString());}return;}
		if (i == 13){LogoFileName = (String)v;return;}
		if (i == 14){MessageBoardFlag = (String)v;return;}
		if (i == 15){CommentAuditFlag = (String)v;return;}
		if (i == 16){if(v==null){ChannelCount = null;}else{ChannelCount = new Long(v.toString());}return;}
		if (i == 17){if(v==null){MagzineCount = null;}else{MagzineCount = new Long(v.toString());}return;}
		if (i == 18){if(v==null){SpecialCount = null;}else{SpecialCount = new Long(v.toString());}return;}
		if (i == 19){if(v==null){ImageLibCount = null;}else{ImageLibCount = new Long(v.toString());}return;}
		if (i == 20){if(v==null){VideoLibCount = null;}else{VideoLibCount = new Long(v.toString());}return;}
		if (i == 21){if(v==null){AudioLibCount = null;}else{AudioLibCount = new Long(v.toString());}return;}
		if (i == 22){if(v==null){AttachmentLibCount = null;}else{AttachmentLibCount = new Long(v.toString());}return;}
		if (i == 23){if(v==null){ArticleCount = null;}else{ArticleCount = new Long(v.toString());}return;}
		if (i == 24){if(v==null){HitCount = null;}else{HitCount = new Long(v.toString());}return;}
		if (i == 25){ConfigXML = (String)v;return;}
		if (i == 26){AutoIndexFlag = (String)v;return;}
		if (i == 27){AutoStatFlag = (String)v;return;}
		if (i == 28){HeaderTemplate = (String)v;return;}
		if (i == 29){TopTemplate = (String)v;return;}
		if (i == 30){BottomTemplate = (String)v;return;}
		if (i == 31){AllowContribute = (String)v;return;}
		if (i == 32){BBSEnableFlag = (String)v;return;}
		if (i == 33){ShopEnableFlag = (String)v;return;}
		if (i == 34){Meta_Keywords = (String)v;return;}
		if (i == 35){Meta_Description = (String)v;return;}
		if (i == 36){Prop1 = (String)v;return;}
		if (i == 37){Prop2 = (String)v;return;}
		if (i == 38){Prop3 = (String)v;return;}
		if (i == 39){Prop4 = (String)v;return;}
		if (i == 40){Prop5 = (String)v;return;}
		if (i == 41){Prop6 = (String)v;return;}
		if (i == 42){AddUser = (String)v;return;}
		if (i == 43){AddTime = (Date)v;return;}
		if (i == 44){ModifyUser = (String)v;return;}
		if (i == 45){ModifyTime = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return Name;}
		if (i == 2){return Alias;}
		if (i == 3){return Info;}
		if (i == 4){return BranchInnerCode;}
		if (i == 5){return URL;}
		if (i == 6){return RootPath;}
		if (i == 7){return IndexTemplate;}
		if (i == 8){return ListTemplate;}
		if (i == 9){return DetailTemplate;}
		if (i == 10){return EditorCss;}
		if (i == 11){return Workflow;}
		if (i == 12){return OrderFlag;}
		if (i == 13){return LogoFileName;}
		if (i == 14){return MessageBoardFlag;}
		if (i == 15){return CommentAuditFlag;}
		if (i == 16){return ChannelCount;}
		if (i == 17){return MagzineCount;}
		if (i == 18){return SpecialCount;}
		if (i == 19){return ImageLibCount;}
		if (i == 20){return VideoLibCount;}
		if (i == 21){return AudioLibCount;}
		if (i == 22){return AttachmentLibCount;}
		if (i == 23){return ArticleCount;}
		if (i == 24){return HitCount;}
		if (i == 25){return ConfigXML;}
		if (i == 26){return AutoIndexFlag;}
		if (i == 27){return AutoStatFlag;}
		if (i == 28){return HeaderTemplate;}
		if (i == 29){return TopTemplate;}
		if (i == 30){return BottomTemplate;}
		if (i == 31){return AllowContribute;}
		if (i == 32){return BBSEnableFlag;}
		if (i == 33){return ShopEnableFlag;}
		if (i == 34){return Meta_Keywords;}
		if (i == 35){return Meta_Description;}
		if (i == 36){return Prop1;}
		if (i == 37){return Prop2;}
		if (i == 38){return Prop3;}
		if (i == 39){return Prop4;}
		if (i == 40){return Prop5;}
		if (i == 41){return Prop6;}
		if (i == 42){return AddUser;}
		if (i == 43){return AddTime;}
		if (i == 44){return ModifyUser;}
		if (i == 45){return ModifyTime;}
		return null;
	}

	/**
	* ��ȡ�ֶ�ID��ֵ�����ֶε�<br>
	* �ֶ����� :վ��ID<br>
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
	* �ֶ����� :վ��ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setID(long iD) {
		this.ID = new Long(iD);
    }

	/**
	* �����ֶ�ID��ֵ�����ֶε�<br>
	* �ֶ����� :վ��ID<br>
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
	* �ֶ����� :Ӧ������<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getName() {
		return Name;
	}

	/**
	* �����ֶ�Name��ֵ�����ֶε�<br>
	* �ֶ����� :Ӧ������<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setName(String name) {
		this.Name = name;
    }

	/**
	* ��ȡ�ֶ�Alias��ֵ�����ֶε�<br>
	* �ֶ����� :վ�����<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getAlias() {
		return Alias;
	}

	/**
	* �����ֶ�Alias��ֵ�����ֶε�<br>
	* �ֶ����� :վ�����<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setAlias(String alias) {
		this.Alias = alias;
    }

	/**
	* ��ȡ�ֶ�Info��ֵ�����ֶε�<br>
	* �ֶ����� :վ������<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getInfo() {
		return Info;
	}

	/**
	* �����ֶ�Info��ֵ�����ֶε�<br>
	* �ֶ����� :վ������<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setInfo(String info) {
		this.Info = info;
    }

	/**
	* ��ȡ�ֶ�BranchInnerCode��ֵ�����ֶε�<br>
	* �ֶ����� :���������ڲ�����<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getBranchInnerCode() {
		return BranchInnerCode;
	}

	/**
	* �����ֶ�BranchInnerCode��ֵ�����ֶε�<br>
	* �ֶ����� :���������ڲ�����<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setBranchInnerCode(String branchInnerCode) {
		this.BranchInnerCode = branchInnerCode;
    }

	/**
	* ��ȡ�ֶ�URL��ֵ�����ֶε�<br>
	* �ֶ����� :վ��URL<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getURL() {
		return URL;
	}

	/**
	* �����ֶ�URL��ֵ�����ֶε�<br>
	* �ֶ����� :վ��URL<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setURL(String uRL) {
		this.URL = uRL;
    }

	/**
	* ��ȡ�ֶ�RootPath��ֵ�����ֶε�<br>
	* �ֶ����� :��Ŀ¼<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getRootPath() {
		return RootPath;
	}

	/**
	* �����ֶ�RootPath��ֵ�����ֶε�<br>
	* �ֶ����� :��Ŀ¼<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setRootPath(String rootPath) {
		this.RootPath = rootPath;
    }

	/**
	* ��ȡ�ֶ�IndexTemplate��ֵ�����ֶε�<br>
	* �ֶ����� :��ҳģ��<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getIndexTemplate() {
		return IndexTemplate;
	}

	/**
	* �����ֶ�IndexTemplate��ֵ�����ֶε�<br>
	* �ֶ����� :��ҳģ��<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setIndexTemplate(String indexTemplate) {
		this.IndexTemplate = indexTemplate;
    }

	/**
	* ��ȡ�ֶ�ListTemplate��ֵ�����ֶε�<br>
	* �ֶ����� :Ĭ���б�ģ��<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getListTemplate() {
		return ListTemplate;
	}

	/**
	* �����ֶ�ListTemplate��ֵ�����ֶε�<br>
	* �ֶ����� :Ĭ���б�ģ��<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setListTemplate(String listTemplate) {
		this.ListTemplate = listTemplate;
    }

	/**
	* ��ȡ�ֶ�DetailTemplate��ֵ�����ֶε�<br>
	* �ֶ����� :Ĭ����ϸҳģ��<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getDetailTemplate() {
		return DetailTemplate;
	}

	/**
	* �����ֶ�DetailTemplate��ֵ�����ֶε�<br>
	* �ֶ����� :Ĭ����ϸҳģ��<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setDetailTemplate(String detailTemplate) {
		this.DetailTemplate = detailTemplate;
    }

	/**
	* ��ȡ�ֶ�EditorCss��ֵ�����ֶε�<br>
	* �ֶ����� :�༭����ʽ<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getEditorCss() {
		return EditorCss;
	}

	/**
	* �����ֶ�EditorCss��ֵ�����ֶε�<br>
	* �ֶ����� :�༭����ʽ<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setEditorCss(String editorCss) {
		this.EditorCss = editorCss;
    }

	/**
	* ��ȡ�ֶ�Workflow��ֵ�����ֶε�<br>
	* �ֶ����� :����������<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getWorkflow() {
		return Workflow;
	}

	/**
	* �����ֶ�Workflow��ֵ�����ֶε�<br>
	* �ֶ����� :����������<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setWorkflow(String workflow) {
		this.Workflow = workflow;
    }

	/**
	* ��ȡ�ֶ�OrderFlag��ֵ�����ֶε�<br>
	* �ֶ����� :����Ȩֵ<br>
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
	* �ֶ����� :����Ȩֵ<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setOrderFlag(long orderFlag) {
		this.OrderFlag = new Long(orderFlag);
    }

	/**
	* �����ֶ�OrderFlag��ֵ�����ֶε�<br>
	* �ֶ����� :����Ȩֵ<br>
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
	* ��ȡ�ֶ�LogoFileName��ֵ�����ֶε�<br>
	* �ֶ����� :վ��Logo<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getLogoFileName() {
		return LogoFileName;
	}

	/**
	* �����ֶ�LogoFileName��ֵ�����ֶε�<br>
	* �ֶ����� :վ��Logo<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setLogoFileName(String logoFileName) {
		this.LogoFileName = logoFileName;
    }

	/**
	* ��ȡ�ֶ�MessageBoardFlag��ֵ�����ֶε�<br>
	* �ֶ����� :���԰��־<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	* ��ע��Ϣ :<br>
	0-�����԰�<br>
	1-�����԰�<br>
	*/
	public String getMessageBoardFlag() {
		return MessageBoardFlag;
	}

	/**
	* �����ֶ�MessageBoardFlag��ֵ�����ֶε�<br>
	* �ֶ����� :���԰��־<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	* ��ע��Ϣ :<br>
	0-�����԰�<br>
	1-�����԰�<br>
	*/
	public void setMessageBoardFlag(String messageBoardFlag) {
		this.MessageBoardFlag = messageBoardFlag;
    }

	/**
	* ��ȡ�ֶ�CommentAuditFlag��ֵ�����ֶε�<br>
	* �ֶ����� :�����Ƿ������<br>
	* �������� :varchar(1)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getCommentAuditFlag() {
		return CommentAuditFlag;
	}

	/**
	* �����ֶ�CommentAuditFlag��ֵ�����ֶε�<br>
	* �ֶ����� :�����Ƿ������<br>
	* �������� :varchar(1)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCommentAuditFlag(String commentAuditFlag) {
		this.CommentAuditFlag = commentAuditFlag;
    }

	/**
	* ��ȡ�ֶ�ChannelCount��ֵ�����ֶε�<br>
	* �ֶ����� :Ƶ����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public long getChannelCount() {
		if(ChannelCount==null){return 0;}
		return ChannelCount.longValue();
	}

	/**
	* �����ֶ�ChannelCount��ֵ�����ֶε�<br>
	* �ֶ����� :Ƶ����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setChannelCount(long channelCount) {
		this.ChannelCount = new Long(channelCount);
    }

	/**
	* �����ֶ�ChannelCount��ֵ�����ֶε�<br>
	* �ֶ����� :Ƶ����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setChannelCount(String channelCount) {
		if (channelCount == null){
			this.ChannelCount = null;
			return;
		}
		this.ChannelCount = new Long(channelCount);
    }

	/**
	* ��ȡ�ֶ�MagzineCount��ֵ�����ֶε�<br>
	* �ֶ����� :�ڿ���<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public long getMagzineCount() {
		if(MagzineCount==null){return 0;}
		return MagzineCount.longValue();
	}

	/**
	* �����ֶ�MagzineCount��ֵ�����ֶε�<br>
	* �ֶ����� :�ڿ���<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setMagzineCount(long magzineCount) {
		this.MagzineCount = new Long(magzineCount);
    }

	/**
	* �����ֶ�MagzineCount��ֵ�����ֶε�<br>
	* �ֶ����� :�ڿ���<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setMagzineCount(String magzineCount) {
		if (magzineCount == null){
			this.MagzineCount = null;
			return;
		}
		this.MagzineCount = new Long(magzineCount);
    }

	/**
	* ��ȡ�ֶ�SpecialCount��ֵ�����ֶε�<br>
	* �ֶ����� :ר����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public long getSpecialCount() {
		if(SpecialCount==null){return 0;}
		return SpecialCount.longValue();
	}

	/**
	* �����ֶ�SpecialCount��ֵ�����ֶε�<br>
	* �ֶ����� :ר����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setSpecialCount(long specialCount) {
		this.SpecialCount = new Long(specialCount);
    }

	/**
	* �����ֶ�SpecialCount��ֵ�����ֶε�<br>
	* �ֶ����� :ר����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setSpecialCount(String specialCount) {
		if (specialCount == null){
			this.SpecialCount = null;
			return;
		}
		this.SpecialCount = new Long(specialCount);
    }

	/**
	* ��ȡ�ֶ�ImageLibCount��ֵ�����ֶε�<br>
	* �ֶ����� :ͼƬ����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public long getImageLibCount() {
		if(ImageLibCount==null){return 0;}
		return ImageLibCount.longValue();
	}

	/**
	* �����ֶ�ImageLibCount��ֵ�����ֶε�<br>
	* �ֶ����� :ͼƬ����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setImageLibCount(long imageLibCount) {
		this.ImageLibCount = new Long(imageLibCount);
    }

	/**
	* �����ֶ�ImageLibCount��ֵ�����ֶε�<br>
	* �ֶ����� :ͼƬ����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setImageLibCount(String imageLibCount) {
		if (imageLibCount == null){
			this.ImageLibCount = null;
			return;
		}
		this.ImageLibCount = new Long(imageLibCount);
    }

	/**
	* ��ȡ�ֶ�VideoLibCount��ֵ�����ֶε�<br>
	* �ֶ����� :��Ƶ����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public long getVideoLibCount() {
		if(VideoLibCount==null){return 0;}
		return VideoLibCount.longValue();
	}

	/**
	* �����ֶ�VideoLibCount��ֵ�����ֶε�<br>
	* �ֶ����� :��Ƶ����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setVideoLibCount(long videoLibCount) {
		this.VideoLibCount = new Long(videoLibCount);
    }

	/**
	* �����ֶ�VideoLibCount��ֵ�����ֶε�<br>
	* �ֶ����� :��Ƶ����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setVideoLibCount(String videoLibCount) {
		if (videoLibCount == null){
			this.VideoLibCount = null;
			return;
		}
		this.VideoLibCount = new Long(videoLibCount);
    }

	/**
	* ��ȡ�ֶ�AudioLibCount��ֵ�����ֶε�<br>
	* �ֶ����� :��Ƶ����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public long getAudioLibCount() {
		if(AudioLibCount==null){return 0;}
		return AudioLibCount.longValue();
	}

	/**
	* �����ֶ�AudioLibCount��ֵ�����ֶε�<br>
	* �ֶ����� :��Ƶ����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setAudioLibCount(long audioLibCount) {
		this.AudioLibCount = new Long(audioLibCount);
    }

	/**
	* �����ֶ�AudioLibCount��ֵ�����ֶε�<br>
	* �ֶ����� :��Ƶ����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setAudioLibCount(String audioLibCount) {
		if (audioLibCount == null){
			this.AudioLibCount = null;
			return;
		}
		this.AudioLibCount = new Long(audioLibCount);
    }

	/**
	* ��ȡ�ֶ�AttachmentLibCount��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public long getAttachmentLibCount() {
		if(AttachmentLibCount==null){return 0;}
		return AttachmentLibCount.longValue();
	}

	/**
	* �����ֶ�AttachmentLibCount��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setAttachmentLibCount(long attachmentLibCount) {
		this.AttachmentLibCount = new Long(attachmentLibCount);
    }

	/**
	* �����ֶ�AttachmentLibCount��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setAttachmentLibCount(String attachmentLibCount) {
		if (attachmentLibCount == null){
			this.AttachmentLibCount = null;
			return;
		}
		this.AttachmentLibCount = new Long(attachmentLibCount);
    }

	/**
	* ��ȡ�ֶ�ArticleCount��ֵ�����ֶε�<br>
	* �ֶ����� :������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	* ��ע��Ϣ :<br>
	1-��<br>
	0-����<br>
	*/
	public long getArticleCount() {
		if(ArticleCount==null){return 0;}
		return ArticleCount.longValue();
	}

	/**
	* �����ֶ�ArticleCount��ֵ�����ֶε�<br>
	* �ֶ����� :������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	* ��ע��Ϣ :<br>
	1-��<br>
	0-����<br>
	*/
	public void setArticleCount(long articleCount) {
		this.ArticleCount = new Long(articleCount);
    }

	/**
	* �����ֶ�ArticleCount��ֵ�����ֶε�<br>
	* �ֶ����� :������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	* ��ע��Ϣ :<br>
	1-��<br>
	0-����<br>
	*/
	public void setArticleCount(String articleCount) {
		if (articleCount == null){
			this.ArticleCount = null;
			return;
		}
		this.ArticleCount = new Long(articleCount);
    }

	/**
	* ��ȡ�ֶ�HitCount��ֵ�����ֶε�<br>
	* �ֶ����� :�����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
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
	* �Ƿ���� :true<br>
	*/
	public void setHitCount(long hitCount) {
		this.HitCount = new Long(hitCount);
    }

	/**
	* �����ֶ�HitCount��ֵ�����ֶε�<br>
	* �ֶ����� :�����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setHitCount(String hitCount) {
		if (hitCount == null){
			this.HitCount = null;
			return;
		}
		this.HitCount = new Long(hitCount);
    }

	/**
	* ��ȡ�ֶ�ConfigXML��ֵ�����ֶε�<br>
	* �ֶ����� :վ������<br>
	* �������� :text<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getConfigXML() {
		return ConfigXML;
	}

	/**
	* �����ֶ�ConfigXML��ֵ�����ֶε�<br>
	* �ֶ����� :վ������<br>
	* �������� :text<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setConfigXML(String configXML) {
		this.ConfigXML = configXML;
    }

	/**
	* ��ȡ�ֶ�AutoIndexFlag��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ��Զ���������<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getAutoIndexFlag() {
		return AutoIndexFlag;
	}

	/**
	* �����ֶ�AutoIndexFlag��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ��Զ���������<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAutoIndexFlag(String autoIndexFlag) {
		this.AutoIndexFlag = autoIndexFlag;
    }

	/**
	* ��ȡ�ֶ�AutoStatFlag��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ��Զ����ͳ��<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getAutoStatFlag() {
		return AutoStatFlag;
	}

	/**
	* �����ֶ�AutoStatFlag��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ��Զ����ͳ��<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAutoStatFlag(String autoStatFlag) {
		this.AutoStatFlag = autoStatFlag;
    }

	/**
	* ��ȡ�ֶ�HeaderTemplate��ֵ�����ֶε�<br>
	* �ֶ����� :ͷ��ģ��<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getHeaderTemplate() {
		return HeaderTemplate;
	}

	/**
	* �����ֶ�HeaderTemplate��ֵ�����ֶε�<br>
	* �ֶ����� :ͷ��ģ��<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setHeaderTemplate(String headerTemplate) {
		this.HeaderTemplate = headerTemplate;
    }

	/**
	* ��ȡ�ֶ�TopTemplate��ֵ�����ֶε�<br>
	* �ֶ����� :����ģ��<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getTopTemplate() {
		return TopTemplate;
	}

	/**
	* �����ֶ�TopTemplate��ֵ�����ֶε�<br>
	* �ֶ����� :����ģ��<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setTopTemplate(String topTemplate) {
		this.TopTemplate = topTemplate;
    }

	/**
	* ��ȡ�ֶ�BottomTemplate��ֵ�����ֶε�<br>
	* �ֶ����� :�ײ�ģ��<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getBottomTemplate() {
		return BottomTemplate;
	}

	/**
	* �����ֶ�BottomTemplate��ֵ�����ֶε�<br>
	* �ֶ����� :�ײ�ģ��<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setBottomTemplate(String bottomTemplate) {
		this.BottomTemplate = bottomTemplate;
    }

	/**
	* ��ȡ�ֶ�AllowContribute��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ�����Ͷ��<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getAllowContribute() {
		return AllowContribute;
	}

	/**
	* �����ֶ�AllowContribute��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ�����Ͷ��<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAllowContribute(String allowContribute) {
		this.AllowContribute = allowContribute;
    }

	/**
	* ��ȡ�ֶ�BBSEnableFlag��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ�������̳<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getBBSEnableFlag() {
		return BBSEnableFlag;
	}

	/**
	* �����ֶ�BBSEnableFlag��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ�������̳<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setBBSEnableFlag(String bBSEnableFlag) {
		this.BBSEnableFlag = bBSEnableFlag;
    }

	/**
	* ��ȡ�ֶ�ShopEnableFlag��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ������̳�<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getShopEnableFlag() {
		return ShopEnableFlag;
	}

	/**
	* �����ֶ�ShopEnableFlag��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ������̳�<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setShopEnableFlag(String shopEnableFlag) {
		this.ShopEnableFlag = shopEnableFlag;
    }

	/**
	* ��ȡ�ֶ�Meta_Keywords��ֵ�����ֶε�<br>
	* �ֶ����� :meta�ؼ���<br>
	* �������� :varchar(1000)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getMeta_Keywords() {
		return Meta_Keywords;
	}

	/**
	* �����ֶ�Meta_Keywords��ֵ�����ֶε�<br>
	* �ֶ����� :meta�ؼ���<br>
	* �������� :varchar(1000)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setMeta_Keywords(String meta_Keywords) {
		this.Meta_Keywords = meta_Keywords;
    }

	/**
	* ��ȡ�ֶ�Meta_Description��ֵ�����ֶε�<br>
	* �ֶ����� :Meta����<br>
	* �������� :varchar(1000)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getMeta_Description() {
		return Meta_Description;
	}

	/**
	* �����ֶ�Meta_Description��ֵ�����ֶε�<br>
	* �ֶ����� :Meta����<br>
	* �������� :varchar(1000)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setMeta_Description(String meta_Description) {
		this.Meta_Description = meta_Description;
    }

	/**
	* ��ȡ�ֶ�Prop1��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�1<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* �����ֶ�Prop1��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�1<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* ��ȡ�ֶ�Prop2��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�2<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* �����ֶ�Prop2��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�2<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

	/**
	* ��ȡ�ֶ�Prop3��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�3<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp3() {
		return Prop3;
	}

	/**
	* �����ֶ�Prop3��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�3<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp3(String prop3) {
		this.Prop3 = prop3;
    }

	/**
	* ��ȡ�ֶ�Prop4��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�4<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp4() {
		return Prop4;
	}

	/**
	* �����ֶ�Prop4��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�4<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp4(String prop4) {
		this.Prop4 = prop4;
    }

	/**
	* ��ȡ�ֶ�Prop5��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�5<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp5() {
		return Prop5;
	}

	/**
	* �����ֶ�Prop5��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�5<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp5(String prop5) {
		this.Prop5 = prop5;
    }

	/**
	* ��ȡ�ֶ�Prop6��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�6<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp6() {
		return Prop6;
	}

	/**
	* �����ֶ�Prop6��ֵ�����ֶε�<br>
	* �ֶ����� :�����ֶ�6<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp6(String prop6) {
		this.Prop6 = prop6;
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