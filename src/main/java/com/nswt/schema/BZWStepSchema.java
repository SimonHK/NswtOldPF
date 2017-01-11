package com.nswt.schema;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.data.QueryBuilder;
import java.util.Date;

/**
 * �����ƣ����̲������<br>
 * ����룺BZWStep<br>
 * ��������ID, BackupNo<br>
 */
public class BZWStepSchema extends Schema {
	private Long ID;

	private Long WorkflowID;

	private Long InstanceID;

	private String DataVersionID;

	private Integer NodeID;

	private Integer ActionID;

	private Long PreviousStepID;

	private String Owner;

	private Date StartTime;

	private Date FinishTime;

	private String State;

	private String Operators;

	private String AllowUser;

	private String AllowOrgan;

	private String AllowRole;

	private String Memo;

	private Date AddTime;

	private String AddUser;

	private String BackupNo;

	private String BackupOperator;

	private Date BackupTime;

	private String BackupMemo;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 0 , 0 , true , true),
		new SchemaColumn("WorkflowID", DataColumn.LONG, 1, 0 , 0 , true , false),
		new SchemaColumn("InstanceID", DataColumn.LONG, 2, 0 , 0 , true , false),
		new SchemaColumn("DataVersionID", DataColumn.STRING, 3, 30 , 0 , false , false),
		new SchemaColumn("NodeID", DataColumn.INTEGER, 4, 0 , 0 , false , false),
		new SchemaColumn("ActionID", DataColumn.INTEGER, 5, 0 , 0 , false , false),
		new SchemaColumn("PreviousStepID", DataColumn.LONG, 6, 0 , 0 , false , false),
		new SchemaColumn("Owner", DataColumn.STRING, 7, 50 , 0 , false , false),
		new SchemaColumn("StartTime", DataColumn.DATETIME, 8, 0 , 0 , false , false),
		new SchemaColumn("FinishTime", DataColumn.DATETIME, 9, 0 , 0 , false , false),
		new SchemaColumn("State", DataColumn.STRING, 10, 10 , 0 , false , false),
		new SchemaColumn("Operators", DataColumn.STRING, 11, 400 , 0 , false , false),
		new SchemaColumn("AllowUser", DataColumn.STRING, 12, 4000 , 0 , false , false),
		new SchemaColumn("AllowOrgan", DataColumn.STRING, 13, 4000 , 0 , false , false),
		new SchemaColumn("AllowRole", DataColumn.STRING, 14, 4000 , 0 , false , false),
		new SchemaColumn("Memo", DataColumn.STRING, 15, 400 , 0 , false , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 16, 0 , 0 , true , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 17, 50 , 0 , true , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 18, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 19, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 20, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 21, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZWStep";

	public static final String _NameSpace = "com.nswt.schema";

	protected static final String _InsertAllSQL = "insert into BZWStep values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZWStep set ID=?,WorkflowID=?,InstanceID=?,DataVersionID=?,NodeID=?,ActionID=?,PreviousStepID=?,Owner=?,StartTime=?,FinishTime=?,State=?,Operators=?,AllowUser=?,AllowOrgan=?,AllowRole=?,Memo=?,AddTime=?,AddUser=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZWStep  where ID=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZWStep  where ID=? and BackupNo=?";

	public BZWStepSchema(){
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
		return new BZWStepSchema();
	}

	protected SchemaSet newSet(){
		return new BZWStepSet();
	}

	public BZWStepSet query() {
		return query(null, -1, -1);
	}

	public BZWStepSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZWStepSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZWStepSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZWStepSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){WorkflowID = null;}else{WorkflowID = new Long(v.toString());}return;}
		if (i == 2){if(v==null){InstanceID = null;}else{InstanceID = new Long(v.toString());}return;}
		if (i == 3){DataVersionID = (String)v;return;}
		if (i == 4){if(v==null){NodeID = null;}else{NodeID = new Integer(v.toString());}return;}
		if (i == 5){if(v==null){ActionID = null;}else{ActionID = new Integer(v.toString());}return;}
		if (i == 6){if(v==null){PreviousStepID = null;}else{PreviousStepID = new Long(v.toString());}return;}
		if (i == 7){Owner = (String)v;return;}
		if (i == 8){StartTime = (Date)v;return;}
		if (i == 9){FinishTime = (Date)v;return;}
		if (i == 10){State = (String)v;return;}
		if (i == 11){Operators = (String)v;return;}
		if (i == 12){AllowUser = (String)v;return;}
		if (i == 13){AllowOrgan = (String)v;return;}
		if (i == 14){AllowRole = (String)v;return;}
		if (i == 15){Memo = (String)v;return;}
		if (i == 16){AddTime = (Date)v;return;}
		if (i == 17){AddUser = (String)v;return;}
		if (i == 18){BackupNo = (String)v;return;}
		if (i == 19){BackupOperator = (String)v;return;}
		if (i == 20){BackupTime = (Date)v;return;}
		if (i == 21){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return WorkflowID;}
		if (i == 2){return InstanceID;}
		if (i == 3){return DataVersionID;}
		if (i == 4){return NodeID;}
		if (i == 5){return ActionID;}
		if (i == 6){return PreviousStepID;}
		if (i == 7){return Owner;}
		if (i == 8){return StartTime;}
		if (i == 9){return FinishTime;}
		if (i == 10){return State;}
		if (i == 11){return Operators;}
		if (i == 12){return AllowUser;}
		if (i == 13){return AllowOrgan;}
		if (i == 14){return AllowRole;}
		if (i == 15){return Memo;}
		if (i == 16){return AddTime;}
		if (i == 17){return AddUser;}
		if (i == 18){return BackupNo;}
		if (i == 19){return BackupOperator;}
		if (i == 20){return BackupTime;}
		if (i == 21){return BackupMemo;}
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
	* ��ȡ�ֶ�WorkflowID��ֵ�����ֶε�<br>
	* �ֶ����� :����ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public long getWorkflowID() {
		if(WorkflowID==null){return 0;}
		return WorkflowID.longValue();
	}

	/**
	* �����ֶ�WorkflowID��ֵ�����ֶε�<br>
	* �ֶ����� :����ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setWorkflowID(long workflowID) {
		this.WorkflowID = new Long(workflowID);
    }

	/**
	* �����ֶ�WorkflowID��ֵ�����ֶε�<br>
	* �ֶ����� :����ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setWorkflowID(String workflowID) {
		if (workflowID == null){
			this.WorkflowID = null;
			return;
		}
		this.WorkflowID = new Long(workflowID);
    }

	/**
	* ��ȡ�ֶ�InstanceID��ֵ�����ֶε�<br>
	* �ֶ����� :ʵ��ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public long getInstanceID() {
		if(InstanceID==null){return 0;}
		return InstanceID.longValue();
	}

	/**
	* �����ֶ�InstanceID��ֵ�����ֶε�<br>
	* �ֶ����� :ʵ��ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setInstanceID(long instanceID) {
		this.InstanceID = new Long(instanceID);
    }

	/**
	* �����ֶ�InstanceID��ֵ�����ֶε�<br>
	* �ֶ����� :ʵ��ID<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setInstanceID(String instanceID) {
		if (instanceID == null){
			this.InstanceID = null;
			return;
		}
		this.InstanceID = new Long(instanceID);
    }

	/**
	* ��ȡ�ֶ�DataVersionID��ֵ�����ֶε�<br>
	* �ֶ����� :���ݰ汾ID<br>
	* �������� :varchar(30)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getDataVersionID() {
		return DataVersionID;
	}

	/**
	* �����ֶ�DataVersionID��ֵ�����ֶε�<br>
	* �ֶ����� :���ݰ汾ID<br>
	* �������� :varchar(30)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setDataVersionID(String dataVersionID) {
		this.DataVersionID = dataVersionID;
    }

	/**
	* ��ȡ�ֶ�NodeID��ֵ�����ֶε�<br>
	* �ֶ����� :�ڵ�ID<br>
	* �������� :integer<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public int getNodeID() {
		if(NodeID==null){return 0;}
		return NodeID.intValue();
	}

	/**
	* �����ֶ�NodeID��ֵ�����ֶε�<br>
	* �ֶ����� :�ڵ�ID<br>
	* �������� :integer<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setNodeID(int nodeID) {
		this.NodeID = new Integer(nodeID);
    }

	/**
	* �����ֶ�NodeID��ֵ�����ֶε�<br>
	* �ֶ����� :�ڵ�ID<br>
	* �������� :integer<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setNodeID(String nodeID) {
		if (nodeID == null){
			this.NodeID = null;
			return;
		}
		this.NodeID = new Integer(nodeID);
    }

	/**
	* ��ȡ�ֶ�ActionID��ֵ�����ֶε�<br>
	* �ֶ����� :�����ڵ�ID<br>
	* �������� :integer<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public int getActionID() {
		if(ActionID==null){return 0;}
		return ActionID.intValue();
	}

	/**
	* �����ֶ�ActionID��ֵ�����ֶε�<br>
	* �ֶ����� :�����ڵ�ID<br>
	* �������� :integer<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setActionID(int actionID) {
		this.ActionID = new Integer(actionID);
    }

	/**
	* �����ֶ�ActionID��ֵ�����ֶε�<br>
	* �ֶ����� :�����ڵ�ID<br>
	* �������� :integer<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setActionID(String actionID) {
		if (actionID == null){
			this.ActionID = null;
			return;
		}
		this.ActionID = new Integer(actionID);
    }

	/**
	* ��ȡ�ֶ�PreviousStepID��ֵ�����ֶε�<br>
	* �ֶ����� :ǰһ����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public long getPreviousStepID() {
		if(PreviousStepID==null){return 0;}
		return PreviousStepID.longValue();
	}

	/**
	* �����ֶ�PreviousStepID��ֵ�����ֶε�<br>
	* �ֶ����� :ǰһ����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setPreviousStepID(long previousStepID) {
		this.PreviousStepID = new Long(previousStepID);
    }

	/**
	* �����ֶ�PreviousStepID��ֵ�����ֶε�<br>
	* �ֶ����� :ǰһ����<br>
	* �������� :bigint<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setPreviousStepID(String previousStepID) {
		if (previousStepID == null){
			this.PreviousStepID = null;
			return;
		}
		this.PreviousStepID = new Long(previousStepID);
    }

	/**
	* ��ȡ�ֶ�Owner��ֵ�����ֶε�<br>
	* �ֶ����� :������<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getOwner() {
		return Owner;
	}

	/**
	* �����ֶ�Owner��ֵ�����ֶε�<br>
	* �ֶ����� :������<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setOwner(String owner) {
		this.Owner = owner;
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
	* ��ȡ�ֶ�FinishTime��ֵ�����ֶε�<br>
	* �ֶ����� :���ʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public Date getFinishTime() {
		return FinishTime;
	}

	/**
	* �����ֶ�FinishTime��ֵ�����ֶε�<br>
	* �ֶ����� :���ʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setFinishTime(Date finishTime) {
		this.FinishTime = finishTime;
    }

	/**
	* ��ȡ�ֶ�State��ֵ�����ֶε�<br>
	* �ֶ����� :����״̬<br>
	* �������� :varchar(10)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getState() {
		return State;
	}

	/**
	* �����ֶ�State��ֵ�����ֶε�<br>
	* �ֶ����� :����״̬<br>
	* �������� :varchar(10)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setState(String state) {
		this.State = state;
    }

	/**
	* ��ȡ�ֶ�Operators��ֵ�����ֶε�<br>
	* �ֶ����� :������<br>
	* �������� :varchar(400)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getOperators() {
		return Operators;
	}

	/**
	* �����ֶ�Operators��ֵ�����ֶε�<br>
	* �ֶ����� :������<br>
	* �������� :varchar(400)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setOperators(String operators) {
		this.Operators = operators;
    }

	/**
	* ��ȡ�ֶ�AllowUser��ֵ�����ֶε�<br>
	* �ֶ����� :������û�<br>
	* �������� :varchar(4000)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getAllowUser() {
		return AllowUser;
	}

	/**
	* �����ֶ�AllowUser��ֵ�����ֶε�<br>
	* �ֶ����� :������û�<br>
	* �������� :varchar(4000)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAllowUser(String allowUser) {
		this.AllowUser = allowUser;
    }

	/**
	* ��ȡ�ֶ�AllowOrgan��ֵ�����ֶε�<br>
	* �ֶ����� :����Ļ���<br>
	* �������� :varchar(4000)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getAllowOrgan() {
		return AllowOrgan;
	}

	/**
	* �����ֶ�AllowOrgan��ֵ�����ֶε�<br>
	* �ֶ����� :����Ļ���<br>
	* �������� :varchar(4000)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAllowOrgan(String allowOrgan) {
		this.AllowOrgan = allowOrgan;
    }

	/**
	* ��ȡ�ֶ�AllowRole��ֵ�����ֶε�<br>
	* �ֶ����� :����Ľ�ɫ<br>
	* �������� :varchar(4000)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public String getAllowRole() {
		return AllowRole;
	}

	/**
	* �����ֶ�AllowRole��ֵ�����ֶε�<br>
	* �ֶ����� :����Ľ�ɫ<br>
	* �������� :varchar(4000)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :false<br>
	*/
	public void setAllowRole(String allowRole) {
		this.AllowRole = allowRole;
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
	* ��ȡ�ֶ�AddTime��ֵ�����ֶε�<br>
	* �ֶ����� :����ʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public Date getAddTime() {
		return AddTime;
	}

	/**
	* �����ֶ�AddTime��ֵ�����ֶε�<br>
	* �ֶ����� :����ʱ��<br>
	* �������� :datetime<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setAddTime(Date addTime) {
		this.AddTime = addTime;
    }

	/**
	* ��ȡ�ֶ�AddUser��ֵ�����ֶε�<br>
	* �ֶ����� :������<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public String getAddUser() {
		return AddUser;
	}

	/**
	* �����ֶ�AddUser��ֵ�����ֶε�<br>
	* �ֶ����� :������<br>
	* �������� :varchar(50)<br>
	* �Ƿ����� :false<br>
	* �Ƿ���� :true<br>
	*/
	public void setAddUser(String addUser) {
		this.AddUser = addUser;
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