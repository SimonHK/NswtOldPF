package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * �����ƣ���̳������<br>
 * ����룺ZCPost<br>
 * ��������ID<br>
 */
public class ZCPostSchema extends Schema {
	private Long ID;

	private Long SiteID;

	private Long ForumID;

	private Long ThemeID;

	private String First;

	private String Subject;

	private String Message;

	private String IP;

	private String Status;

	private String IsHidden;

	private String Invisible;

	private String VerifyFlag;

	private Long Layer;

	private String ApplyDel;

	private String prop1;

	private String prop2;

	private String prop3;

	private String prop4;

	private String AddUser;

	private Date AddTime;

	private String ModifyUser;

	private Date ModifyTime;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 0 , 0 , true , true),
		new SchemaColumn("SiteID", DataColumn.LONG, 1, 0 , 0 , false , false),
		new SchemaColumn("ForumID", DataColumn.LONG, 2, 0 , 0 , true , false),
		new SchemaColumn("ThemeID", DataColumn.LONG, 3, 0 , 0 , true , false),
		new SchemaColumn("First", DataColumn.STRING, 4, 2 , 0 , false , false),
		new SchemaColumn("Subject", DataColumn.STRING, 5, 100 , 0 , false , false),
		new SchemaColumn("Message", DataColumn.CLOB, 6, 0 , 0 , false , false),
		new SchemaColumn("IP", DataColumn.STRING, 7, 20 , 0 , false , false),
		new SchemaColumn("Status", DataColumn.STRING, 8, 2 , 0 , false , false),
		new SchemaColumn("IsHidden", DataColumn.STRING, 9, 2 , 0 , false , false),
		new SchemaColumn("Invisible", DataColumn.STRING, 10, 2 , 0 , false , false),
		new SchemaColumn("VerifyFlag", DataColumn.STRING, 11, 2 , 0 , false , false),
		new SchemaColumn("Layer", DataColumn.LONG, 12, 0 , 0 , false , false),
		new SchemaColumn("ApplyDel", DataColumn.STRING, 13, 2 , 0 , false , false),
		new SchemaColumn("prop1", DataColumn.STRING, 14, 50 , 0 , false , false),
		new SchemaColumn("prop2", DataColumn.STRING, 15, 50 , 0 , false , false),
		new SchemaColumn("prop3", DataColumn.STRING, 16, 50 , 0 , false , false),
		new SchemaColumn("prop4", DataColumn.STRING, 17, 50 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 18, 100 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 19, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 20, 100 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 21, 0 , 0 , false , false)
	};

	public static final String _TableCode = "ZCPost";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into ZCPost values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZCPost set ID=?,SiteID=?,ForumID=?,ThemeID=?,First=?,Subject=?,Message=?,IP=?,Status=?,IsHidden=?,Invisible=?,VerifyFlag=?,Layer=?,ApplyDel=?,prop1=?,prop2=?,prop3=?,prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";

	protected static final String _DeleteSQL = "delete from ZCPost  where ID=?";

	protected static final String _FillAllSQL = "select * from ZCPost  where ID=?";

	public ZCPostSchema(){
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
		return new ZCPostSchema();
	}

	protected SchemaSet newSet(){
		return new ZCPostSet();
	}

	public ZCPostSet query() {
		return query(null, -1, -1);
	}

	public ZCPostSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZCPostSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZCPostSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZCPostSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 2){if(v==null){ForumID = null;}else{ForumID = new Long(v.toString());}return;}
		if (i == 3){if(v==null){ThemeID = null;}else{ThemeID = new Long(v.toString());}return;}
		if (i == 4){First = (String)v;return;}
		if (i == 5){Subject = (String)v;return;}
		if (i == 6){Message = (String)v;return;}
		if (i == 7){IP = (String)v;return;}
		if (i == 8){Status = (String)v;return;}
		if (i == 9){IsHidden = (String)v;return;}
		if (i == 10){Invisible = (String)v;return;}
		if (i == 11){VerifyFlag = (String)v;return;}
		if (i == 12){if(v==null){Layer = null;}else{Layer = new Long(v.toString());}return;}
		if (i == 13){ApplyDel = (String)v;return;}
		if (i == 14){prop1 = (String)v;return;}
		if (i == 15){prop2 = (String)v;return;}
		if (i == 16){prop3 = (String)v;return;}
		if (i == 17){prop4 = (String)v;return;}
		if (i == 18){AddUser = (String)v;return;}
		if (i == 19){AddTime = (Date)v;return;}
		if (i == 20){ModifyUser = (String)v;return;}
		if (i == 21){ModifyTime = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return SiteID;}
		if (i == 2){return ForumID;}
		if (i == 3){return ThemeID;}
		if (i == 4){return First;}
		if (i == 5){return Subject;}
		if (i == 6){return Message;}
		if (i == 7){return IP;}
		if (i == 8){return Status;}
		if (i == 9){return IsHidden;}
		if (i == 10){return Invisible;}
		if (i == 11){return VerifyFlag;}
		if (i == 12){return Layer;}
		if (i == 13){return ApplyDel;}
		if (i == 14){return prop1;}
		if (i == 15){return prop2;}
		if (i == 16){return prop3;}
		if (i == 17){return prop4;}
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
	* ��ȡ�ֶ�ForumID��ֵ�����ֶε�<br>
	* �ֶ����� :�������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public long getForumID() {
		if(ForumID==null){return 0;}
		return ForumID.longValue();
	}

	/**
	* �����ֶ�ForumID��ֵ�����ֶε�<br>
	* �ֶ����� :�������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setForumID(long forumID) {
		this.ForumID = new Long(forumID);
    }

	/**
	* �����ֶ�ForumID��ֵ�����ֶε�<br>
	* �ֶ����� :�������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setForumID(String forumID) {
		if (forumID == null){
			this.ForumID = null;
			return;
		}
		this.ForumID = new Long(forumID);
    }

	/**
	* ��ȡ�ֶ�ThemeID��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public long getThemeID() {
		if(ThemeID==null){return 0;}
		return ThemeID.longValue();
	}

	/**
	* �����ֶ�ThemeID��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setThemeID(long themeID) {
		this.ThemeID = new Long(themeID);
    }

	/**
	* �����ֶ�ThemeID��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setThemeID(String themeID) {
		if (themeID == null){
			this.ThemeID = null;
			return;
		}
		this.ThemeID = new Long(themeID);
    }

	/**
	* ��ȡ�ֶ�First��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ��һ��<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getFirst() {
		return First;
	}

	/**
	* �����ֶ�First��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ��һ��<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setFirst(String first) {
		this.First = first;
    }

	/**
	* ��ȡ�ֶ�Subject��ֵ�����ֶε�<br>
	* �ֶ����� :�ظ����ӱ���<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getSubject() {
		return Subject;
	}

	/**
	* �����ֶ�Subject��ֵ�����ֶε�<br>
	* �ֶ����� :�ظ����ӱ���<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setSubject(String subject) {
		this.Subject = subject;
    }

	/**
	* ��ȡ�ֶ�Message��ֵ�����ֶε�<br>
	* �ֶ����� :�ظ���������<br>
	* �������� :text<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getMessage() {
		return Message;
	}

	/**
	* �����ֶ�Message��ֵ�����ֶε�<br>
	* �ֶ����� :�ظ���������<br>
	* �������� :text<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setMessage(String message) {
		this.Message = message;
    }

	/**
	* ��ȡ�ֶ�IP��ֵ�����ֶε�<br>
	* �ֶ����� :�ظ���IP<br>
	* �������� :char(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getIP() {
		return IP;
	}

	/**
	* �����ֶ�IP��ֵ�����ֶε�<br>
	* �ֶ����� :�ظ���IP<br>
	* �������� :char(20)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setIP(String iP) {
		this.IP = iP;
    }

	/**
	* ��ȡ�ֶ�Status��ֵ�����ֶε�<br>
	* �ֶ����� :�ظ�����״̬<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getStatus() {
		return Status;
	}

	/**
	* �����ֶ�Status��ֵ�����ֶε�<br>
	* �ֶ����� :�ظ�����״̬<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setStatus(String status) {
		this.Status = status;
    }

	/**
	* ��ȡ�ֶ�IsHidden��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ�����<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getIsHidden() {
		return IsHidden;
	}

	/**
	* �����ֶ�IsHidden��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ�����<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setIsHidden(String isHidden) {
		this.IsHidden = isHidden;
    }

	/**
	* ��ȡ�ֶ�Invisible��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ���ʾ<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getInvisible() {
		return Invisible;
	}

	/**
	* �����ֶ�Invisible��ֵ�����ֶε�<br>
	* �ֶ����� :�Ƿ���ʾ<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setInvisible(String invisible) {
		this.Invisible = invisible;
    }

	/**
	* ��ȡ�ֶ�VerifyFlag��ֵ�����ֶε�<br>
	* �ֶ����� :��˱�־<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getVerifyFlag() {
		return VerifyFlag;
	}

	/**
	* �����ֶ�VerifyFlag��ֵ�����ֶε�<br>
	* �ֶ����� :��˱�־<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setVerifyFlag(String verifyFlag) {
		this.VerifyFlag = verifyFlag;
    }

	/**
	* ��ȡ�ֶ�Layer��ֵ�����ֶε�<br>
	* �ֶ����� :����¥��<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getLayer() {
		if(Layer==null){return 0;}
		return Layer.longValue();
	}

	/**
	* �����ֶ�Layer��ֵ�����ֶε�<br>
	* �ֶ����� :����¥��<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setLayer(long layer) {
		this.Layer = new Long(layer);
    }

	/**
	* �����ֶ�Layer��ֵ�����ֶε�<br>
	* �ֶ����� :����¥��<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setLayer(String layer) {
		if (layer == null){
			this.Layer = null;
			return;
		}
		this.Layer = new Long(layer);
    }

	/**
	* ��ȡ�ֶ�ApplyDel��ֵ�����ֶε�<br>
	* �ֶ����� :����ɾ��<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getApplyDel() {
		return ApplyDel;
	}

	/**
	* �����ֶ�ApplyDel��ֵ�����ֶε�<br>
	* �ֶ����� :����ɾ��<br>
	* �������� :varchar(2)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setApplyDel(String applyDel) {
		this.ApplyDel = applyDel;
    }

	/**
	* ��ȡ�ֶ�prop1��ֵ�����ֶε�<br>
	* �ֶ����� :Ԥ���ֶ�1<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp1() {
		return prop1;
	}

	/**
	* �����ֶ�prop1��ֵ�����ֶε�<br>
	* �ֶ����� :Ԥ���ֶ�1<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp1(String prop1) {
		this.prop1 = prop1;
    }

	/**
	* ��ȡ�ֶ�prop2��ֵ�����ֶε�<br>
	* �ֶ����� :Ԥ���ֶ�2<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp2() {
		return prop2;
	}

	/**
	* �����ֶ�prop2��ֵ�����ֶε�<br>
	* �ֶ����� :Ԥ���ֶ�2<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp2(String prop2) {
		this.prop2 = prop2;
    }

	/**
	* ��ȡ�ֶ�prop3��ֵ�����ֶε�<br>
	* �ֶ����� :Ԥ���ֶ�3<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp3() {
		return prop3;
	}

	/**
	* �����ֶ�prop3��ֵ�����ֶε�<br>
	* �ֶ����� :Ԥ���ֶ�3<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp3(String prop3) {
		this.prop3 = prop3;
    }

	/**
	* ��ȡ�ֶ�prop4��ֵ�����ֶε�<br>
	* �ֶ����� :Ԥ���ֶ�4<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getProp4() {
		return prop4;
	}

	/**
	* �����ֶ�prop4��ֵ�����ֶε�<br>
	* �ֶ����� :Ԥ���ֶ�4<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setProp4(String prop4) {
		this.prop4 = prop4;
    }

	/**
	* ��ȡ�ֶ�AddUser��ֵ�����ֶε�<br>
	* �ֶ����� :�����<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getAddUser() {
		return AddUser;
	}

	/**
	* �����ֶ�AddUser��ֵ�����ֶε�<br>
	* �ֶ����� :�����<br>
	* �������� :varchar(100)<br>
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
	* �ֶ����� :�޸���<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getModifyUser() {
		return ModifyUser;
	}

	/**
	* �����ֶ�ModifyUser��ֵ�����ֶε�<br>
	* �ֶ����� :�޸���<br>
	* �������� :varchar(100)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setModifyUser(String modifyUser) {
		this.ModifyUser = modifyUser;
    }

	/**
	* ��ȡ�ֶ�ModifyTime��ֵ�����ֶε�<br>
	* �ֶ����� :�޸�ʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public Date getModifyTime() {
		return ModifyTime;
	}

	/**
	* �����ֶ�ModifyTime��ֵ�����ֶε�<br>
	* �ֶ����� :�޸�ʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setModifyTime(Date modifyTime) {
		this.ModifyTime = modifyTime;
    }

}