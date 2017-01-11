package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * �����ƣ��������ñ���<br>
 * ����룺BZCForumScore<br>
 * ��������ID, BackupNo<br>
 */
public class BZCForumScoreSchema extends Schema {
	private Long ID;

	private Long SiteID;

	private Long InitScore;

	private Long PublishTheme;

	private Long DeleteTheme;

	private Long PublishPost;

	private Long DeletePost;

	private Long Best;

	private Long CancelBest;

	private Long Bright;

	private Long CancelBright;

	private Long TopTheme;

	private Long CancelTop;

	private Long UpTheme;

	private Long DownTheme;

	private Long Upload;

	private Long Download;

	private Long Search;

	private Long Vote;

	private String prop1;

	private String prop2;

	private String prop3;

	private String prop4;

	private String AddUser;

	private Date AddTime;

	private String ModifyUser;

	private Date ModifyTime;

	private String BackupNo;

	private String BackupOperator;

	private Date BackupTime;

	private String BackupMemo;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 0 , 0 , true , true),
		new SchemaColumn("SiteID", DataColumn.LONG, 1, 0 , 0 , false , false),
		new SchemaColumn("InitScore", DataColumn.LONG, 2, 0 , 0 , false , false),
		new SchemaColumn("PublishTheme", DataColumn.LONG, 3, 0 , 0 , false , false),
		new SchemaColumn("DeleteTheme", DataColumn.LONG, 4, 0 , 0 , false , false),
		new SchemaColumn("PublishPost", DataColumn.LONG, 5, 0 , 0 , false , false),
		new SchemaColumn("DeletePost", DataColumn.LONG, 6, 0 , 0 , false , false),
		new SchemaColumn("Best", DataColumn.LONG, 7, 0 , 0 , false , false),
		new SchemaColumn("CancelBest", DataColumn.LONG, 8, 0 , 0 , false , false),
		new SchemaColumn("Bright", DataColumn.LONG, 9, 0 , 0 , false , false),
		new SchemaColumn("CancelBright", DataColumn.LONG, 10, 0 , 0 , false , false),
		new SchemaColumn("TopTheme", DataColumn.LONG, 11, 0 , 0 , false , false),
		new SchemaColumn("CancelTop", DataColumn.LONG, 12, 0 , 0 , false , false),
		new SchemaColumn("UpTheme", DataColumn.LONG, 13, 0 , 0 , false , false),
		new SchemaColumn("DownTheme", DataColumn.LONG, 14, 0 , 0 , false , false),
		new SchemaColumn("Upload", DataColumn.LONG, 15, 0 , 0 , false , false),
		new SchemaColumn("Download", DataColumn.LONG, 16, 0 , 0 , false , false),
		new SchemaColumn("Search", DataColumn.LONG, 17, 0 , 0 , false , false),
		new SchemaColumn("Vote", DataColumn.LONG, 18, 0 , 0 , false , false),
		new SchemaColumn("prop1", DataColumn.STRING, 19, 50 , 0 , false , false),
		new SchemaColumn("prop2", DataColumn.STRING, 20, 50 , 0 , false , false),
		new SchemaColumn("prop3", DataColumn.STRING, 21, 50 , 0 , false , false),
		new SchemaColumn("prop4", DataColumn.STRING, 22, 50 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 23, 100 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 24, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 25, 100 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 26, 0 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 27, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 28, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 29, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 30, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZCForumScore";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into BZCForumScore values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZCForumScore set ID=?,SiteID=?,InitScore=?,PublishTheme=?,DeleteTheme=?,PublishPost=?,DeletePost=?,Best=?,CancelBest=?,Bright=?,CancelBright=?,TopTheme=?,CancelTop=?,UpTheme=?,DownTheme=?,Upload=?,Download=?,Search=?,Vote=?,prop1=?,prop2=?,prop3=?,prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZCForumScore  where ID=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZCForumScore  where ID=? and BackupNo=?";

	public BZCForumScoreSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[31];
	}

	protected Schema newInstance(){
		return new BZCForumScoreSchema();
	}

	protected SchemaSet newSet(){
		return new BZCForumScoreSet();
	}

	public BZCForumScoreSet query() {
		return query(null, -1, -1);
	}

	public BZCForumScoreSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZCForumScoreSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZCForumScoreSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZCForumScoreSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 2){if(v==null){InitScore = null;}else{InitScore = new Long(v.toString());}return;}
		if (i == 3){if(v==null){PublishTheme = null;}else{PublishTheme = new Long(v.toString());}return;}
		if (i == 4){if(v==null){DeleteTheme = null;}else{DeleteTheme = new Long(v.toString());}return;}
		if (i == 5){if(v==null){PublishPost = null;}else{PublishPost = new Long(v.toString());}return;}
		if (i == 6){if(v==null){DeletePost = null;}else{DeletePost = new Long(v.toString());}return;}
		if (i == 7){if(v==null){Best = null;}else{Best = new Long(v.toString());}return;}
		if (i == 8){if(v==null){CancelBest = null;}else{CancelBest = new Long(v.toString());}return;}
		if (i == 9){if(v==null){Bright = null;}else{Bright = new Long(v.toString());}return;}
		if (i == 10){if(v==null){CancelBright = null;}else{CancelBright = new Long(v.toString());}return;}
		if (i == 11){if(v==null){TopTheme = null;}else{TopTheme = new Long(v.toString());}return;}
		if (i == 12){if(v==null){CancelTop = null;}else{CancelTop = new Long(v.toString());}return;}
		if (i == 13){if(v==null){UpTheme = null;}else{UpTheme = new Long(v.toString());}return;}
		if (i == 14){if(v==null){DownTheme = null;}else{DownTheme = new Long(v.toString());}return;}
		if (i == 15){if(v==null){Upload = null;}else{Upload = new Long(v.toString());}return;}
		if (i == 16){if(v==null){Download = null;}else{Download = new Long(v.toString());}return;}
		if (i == 17){if(v==null){Search = null;}else{Search = new Long(v.toString());}return;}
		if (i == 18){if(v==null){Vote = null;}else{Vote = new Long(v.toString());}return;}
		if (i == 19){prop1 = (String)v;return;}
		if (i == 20){prop2 = (String)v;return;}
		if (i == 21){prop3 = (String)v;return;}
		if (i == 22){prop4 = (String)v;return;}
		if (i == 23){AddUser = (String)v;return;}
		if (i == 24){AddTime = (Date)v;return;}
		if (i == 25){ModifyUser = (String)v;return;}
		if (i == 26){ModifyTime = (Date)v;return;}
		if (i == 27){BackupNo = (String)v;return;}
		if (i == 28){BackupOperator = (String)v;return;}
		if (i == 29){BackupTime = (Date)v;return;}
		if (i == 30){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return SiteID;}
		if (i == 2){return InitScore;}
		if (i == 3){return PublishTheme;}
		if (i == 4){return DeleteTheme;}
		if (i == 5){return PublishPost;}
		if (i == 6){return DeletePost;}
		if (i == 7){return Best;}
		if (i == 8){return CancelBest;}
		if (i == 9){return Bright;}
		if (i == 10){return CancelBright;}
		if (i == 11){return TopTheme;}
		if (i == 12){return CancelTop;}
		if (i == 13){return UpTheme;}
		if (i == 14){return DownTheme;}
		if (i == 15){return Upload;}
		if (i == 16){return Download;}
		if (i == 17){return Search;}
		if (i == 18){return Vote;}
		if (i == 19){return prop1;}
		if (i == 20){return prop2;}
		if (i == 21){return prop3;}
		if (i == 22){return prop4;}
		if (i == 23){return AddUser;}
		if (i == 24){return AddTime;}
		if (i == 25){return ModifyUser;}
		if (i == 26){return ModifyTime;}
		if (i == 27){return BackupNo;}
		if (i == 28){return BackupOperator;}
		if (i == 29){return BackupTime;}
		if (i == 30){return BackupMemo;}
		return null;
	}

	/**
	* ��ȡ�ֶ�ID��ֵ�����ֶε�<br>
	* �ֶ����� :ID<br>
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
	* �ֶ����� :ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :true<br>
	* �Ƿ���� :true<br>
	*/
	public void setID(long iD) {
		this.ID = new Long(iD);
    }

	/**
	* �����ֶ�ID��ֵ�����ֶε�<br>
	* �ֶ����� :ID<br>
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
	* ��ȡ�ֶ�InitScore��ֵ�����ֶε�<br>
	* �ֶ����� :��ʼ����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getInitScore() {
		if(InitScore==null){return 0;}
		return InitScore.longValue();
	}

	/**
	* �����ֶ�InitScore��ֵ�����ֶε�<br>
	* �ֶ����� :��ʼ����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setInitScore(long initScore) {
		this.InitScore = new Long(initScore);
    }

	/**
	* �����ֶ�InitScore��ֵ�����ֶε�<br>
	* �ֶ����� :��ʼ����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setInitScore(String initScore) {
		if (initScore == null){
			this.InitScore = null;
			return;
		}
		this.InitScore = new Long(initScore);
    }

	/**
	* ��ȡ�ֶ�PublishTheme��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getPublishTheme() {
		if(PublishTheme==null){return 0;}
		return PublishTheme.longValue();
	}

	/**
	* �����ֶ�PublishTheme��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setPublishTheme(long publishTheme) {
		this.PublishTheme = new Long(publishTheme);
    }

	/**
	* �����ֶ�PublishTheme��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setPublishTheme(String publishTheme) {
		if (publishTheme == null){
			this.PublishTheme = null;
			return;
		}
		this.PublishTheme = new Long(publishTheme);
    }

	/**
	* ��ȡ�ֶ�DeleteTheme��ֵ�����ֶε�<br>
	* �ֶ����� :ɾ������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getDeleteTheme() {
		if(DeleteTheme==null){return 0;}
		return DeleteTheme.longValue();
	}

	/**
	* �����ֶ�DeleteTheme��ֵ�����ֶε�<br>
	* �ֶ����� :ɾ������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setDeleteTheme(long deleteTheme) {
		this.DeleteTheme = new Long(deleteTheme);
    }

	/**
	* �����ֶ�DeleteTheme��ֵ�����ֶε�<br>
	* �ֶ����� :ɾ������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setDeleteTheme(String deleteTheme) {
		if (deleteTheme == null){
			this.DeleteTheme = null;
			return;
		}
		this.DeleteTheme = new Long(deleteTheme);
    }

	/**
	* ��ȡ�ֶ�PublishPost��ֵ�����ֶε�<br>
	* �ֶ����� :����ظ�<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getPublishPost() {
		if(PublishPost==null){return 0;}
		return PublishPost.longValue();
	}

	/**
	* �����ֶ�PublishPost��ֵ�����ֶε�<br>
	* �ֶ����� :����ظ�<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setPublishPost(long publishPost) {
		this.PublishPost = new Long(publishPost);
    }

	/**
	* �����ֶ�PublishPost��ֵ�����ֶε�<br>
	* �ֶ����� :����ظ�<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setPublishPost(String publishPost) {
		if (publishPost == null){
			this.PublishPost = null;
			return;
		}
		this.PublishPost = new Long(publishPost);
    }

	/**
	* ��ȡ�ֶ�DeletePost��ֵ�����ֶε�<br>
	* �ֶ����� :ɾ���ظ�<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getDeletePost() {
		if(DeletePost==null){return 0;}
		return DeletePost.longValue();
	}

	/**
	* �����ֶ�DeletePost��ֵ�����ֶε�<br>
	* �ֶ����� :ɾ���ظ�<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setDeletePost(long deletePost) {
		this.DeletePost = new Long(deletePost);
    }

	/**
	* �����ֶ�DeletePost��ֵ�����ֶε�<br>
	* �ֶ����� :ɾ���ظ�<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setDeletePost(String deletePost) {
		if (deletePost == null){
			this.DeletePost = null;
			return;
		}
		this.DeletePost = new Long(deletePost);
    }

	/**
	* ��ȡ�ֶ�Best��ֵ�����ֶε�<br>
	* �ֶ����� :��Ϊ����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getBest() {
		if(Best==null){return 0;}
		return Best.longValue();
	}

	/**
	* �����ֶ�Best��ֵ�����ֶε�<br>
	* �ֶ����� :��Ϊ����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setBest(long best) {
		this.Best = new Long(best);
    }

	/**
	* �����ֶ�Best��ֵ�����ֶε�<br>
	* �ֶ����� :��Ϊ����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setBest(String best) {
		if (best == null){
			this.Best = null;
			return;
		}
		this.Best = new Long(best);
    }

	/**
	* ��ȡ�ֶ�CancelBest��ֵ�����ֶε�<br>
	* �ֶ����� :ȡ������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getCancelBest() {
		if(CancelBest==null){return 0;}
		return CancelBest.longValue();
	}

	/**
	* �����ֶ�CancelBest��ֵ�����ֶε�<br>
	* �ֶ����� :ȡ������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCancelBest(long cancelBest) {
		this.CancelBest = new Long(cancelBest);
    }

	/**
	* �����ֶ�CancelBest��ֵ�����ֶε�<br>
	* �ֶ����� :ȡ������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCancelBest(String cancelBest) {
		if (cancelBest == null){
			this.CancelBest = null;
			return;
		}
		this.CancelBest = new Long(cancelBest);
    }

	/**
	* ��ȡ�ֶ�Bright��ֵ�����ֶε�<br>
	* �ֶ����� :������ʾ<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getBright() {
		if(Bright==null){return 0;}
		return Bright.longValue();
	}

	/**
	* �����ֶ�Bright��ֵ�����ֶε�<br>
	* �ֶ����� :������ʾ<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setBright(long bright) {
		this.Bright = new Long(bright);
    }

	/**
	* �����ֶ�Bright��ֵ�����ֶε�<br>
	* �ֶ����� :������ʾ<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setBright(String bright) {
		if (bright == null){
			this.Bright = null;
			return;
		}
		this.Bright = new Long(bright);
    }

	/**
	* ��ȡ�ֶ�CancelBright��ֵ�����ֶε�<br>
	* �ֶ����� :ȡ������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getCancelBright() {
		if(CancelBright==null){return 0;}
		return CancelBright.longValue();
	}

	/**
	* �����ֶ�CancelBright��ֵ�����ֶε�<br>
	* �ֶ����� :ȡ������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCancelBright(long cancelBright) {
		this.CancelBright = new Long(cancelBright);
    }

	/**
	* �����ֶ�CancelBright��ֵ�����ֶε�<br>
	* �ֶ����� :ȡ������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCancelBright(String cancelBright) {
		if (cancelBright == null){
			this.CancelBright = null;
			return;
		}
		this.CancelBright = new Long(cancelBright);
    }

	/**
	* ��ȡ�ֶ�TopTheme��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getTopTheme() {
		if(TopTheme==null){return 0;}
		return TopTheme.longValue();
	}

	/**
	* �����ֶ�TopTheme��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setTopTheme(long topTheme) {
		this.TopTheme = new Long(topTheme);
    }

	/**
	* �����ֶ�TopTheme��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setTopTheme(String topTheme) {
		if (topTheme == null){
			this.TopTheme = null;
			return;
		}
		this.TopTheme = new Long(topTheme);
    }

	/**
	* ��ȡ�ֶ�CancelTop��ֵ�����ֶε�<br>
	* �ֶ����� :ȡ������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getCancelTop() {
		if(CancelTop==null){return 0;}
		return CancelTop.longValue();
	}

	/**
	* �����ֶ�CancelTop��ֵ�����ֶε�<br>
	* �ֶ����� :ȡ������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCancelTop(long cancelTop) {
		this.CancelTop = new Long(cancelTop);
    }

	/**
	* �����ֶ�CancelTop��ֵ�����ֶε�<br>
	* �ֶ����� :ȡ������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setCancelTop(String cancelTop) {
		if (cancelTop == null){
			this.CancelTop = null;
			return;
		}
		this.CancelTop = new Long(cancelTop);
    }

	/**
	* ��ȡ�ֶ�UpTheme��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getUpTheme() {
		if(UpTheme==null){return 0;}
		return UpTheme.longValue();
	}

	/**
	* �����ֶ�UpTheme��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setUpTheme(long upTheme) {
		this.UpTheme = new Long(upTheme);
    }

	/**
	* �����ֶ�UpTheme��ֵ�����ֶε�<br>
	* �ֶ����� :��������<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setUpTheme(String upTheme) {
		if (upTheme == null){
			this.UpTheme = null;
			return;
		}
		this.UpTheme = new Long(upTheme);
    }

	/**
	* ��ȡ�ֶ�DownTheme��ֵ�����ֶε�<br>
	* �ֶ����� :�³�����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getDownTheme() {
		if(DownTheme==null){return 0;}
		return DownTheme.longValue();
	}

	/**
	* �����ֶ�DownTheme��ֵ�����ֶε�<br>
	* �ֶ����� :�³�����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setDownTheme(long downTheme) {
		this.DownTheme = new Long(downTheme);
    }

	/**
	* �����ֶ�DownTheme��ֵ�����ֶε�<br>
	* �ֶ����� :�³�����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setDownTheme(String downTheme) {
		if (downTheme == null){
			this.DownTheme = null;
			return;
		}
		this.DownTheme = new Long(downTheme);
    }

	/**
	* ��ȡ�ֶ�Upload��ֵ�����ֶε�<br>
	* �ֶ����� :�ϴ�����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getUpload() {
		if(Upload==null){return 0;}
		return Upload.longValue();
	}

	/**
	* �����ֶ�Upload��ֵ�����ֶε�<br>
	* �ֶ����� :�ϴ�����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setUpload(long upload) {
		this.Upload = new Long(upload);
    }

	/**
	* �����ֶ�Upload��ֵ�����ֶε�<br>
	* �ֶ����� :�ϴ�����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setUpload(String upload) {
		if (upload == null){
			this.Upload = null;
			return;
		}
		this.Upload = new Long(upload);
    }

	/**
	* ��ȡ�ֶ�Download��ֵ�����ֶε�<br>
	* �ֶ����� :���ظ���<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getDownload() {
		if(Download==null){return 0;}
		return Download.longValue();
	}

	/**
	* �����ֶ�Download��ֵ�����ֶε�<br>
	* �ֶ����� :���ظ���<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setDownload(long download) {
		this.Download = new Long(download);
    }

	/**
	* �����ֶ�Download��ֵ�����ֶε�<br>
	* �ֶ����� :���ظ���<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setDownload(String download) {
		if (download == null){
			this.Download = null;
			return;
		}
		this.Download = new Long(download);
    }

	/**
	* ��ȡ�ֶ�Search��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getSearch() {
		if(Search==null){return 0;}
		return Search.longValue();
	}

	/**
	* �����ֶ�Search��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setSearch(long search) {
		this.Search = new Long(search);
    }

	/**
	* �����ֶ�Search��ֵ�����ֶε�<br>
	* �ֶ����� :����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setSearch(String search) {
		if (search == null){
			this.Search = null;
			return;
		}
		this.Search = new Long(search);
    }

	/**
	* ��ȡ�ֶ�Vote��ֵ�����ֶε�<br>
	* �ֶ����� :����ͶƱ<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getVote() {
		if(Vote==null){return 0;}
		return Vote.longValue();
	}

	/**
	* �����ֶ�Vote��ֵ�����ֶε�<br>
	* �ֶ����� :����ͶƱ<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setVote(long vote) {
		this.Vote = new Long(vote);
    }

	/**
	* �����ֶ�Vote��ֵ�����ֶε�<br>
	* �ֶ����� :����ͶƱ<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setVote(String vote) {
		if (vote == null){
			this.Vote = null;
			return;
		}
		this.Vote = new Long(vote);
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