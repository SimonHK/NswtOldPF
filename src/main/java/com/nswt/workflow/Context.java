package com.nswt.workflow;

import java.util.Date;

import com.nswt.framework.User;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.Mapx;
import com.nswt.platform.pub.NoUtil;
import com.nswt.platform.pub.PlatformCache;
import com.nswt.schema.ZWStepSchema;
import com.nswt.workflow.Workflow.Node;

/**
 * ������ִ��ʱ��������
 * 
 * ���� : 2009-9-3 <br>
 * ����: NSWT <br>
 * ���䣺nswt@nswt.com.cn <br>
 */
public class Context {
	private WorkflowInstance instance;

	private ZWStepSchema step;

	private Mapx variables;

	private Workflow flow;

	private Transaction tran;

	private boolean variableFlag = false;// ������Ϣ�Ƿ񱻸Ķ�

	public Context(Transaction tran, WorkflowStep step) throws WorkflowException {
		this(tran, (ZWStepSchema) step);
	}

	public Context(Transaction tran, ZWStepSchema step) throws WorkflowException {
		this.tran = tran;
		this.flow = WorkflowUtil.findWorkflow(step.getWorkflowID());
		this.instance = WorkflowUtil.findInstance(step.getInstanceID());
		this.step = step;
		this.variables = WorkflowUtil.getDataVariables(instance.getDataID(), step.getDataVersionID());
	}

	public WorkflowInstance getInstance() {
		return instance;
	}

	public ZWStepSchema getStep() {
		return step;
	}

	public Mapx getVariables() {
		return variables;
	}

	public Workflow getWorkflow() {
		return flow;
	}

	public Transaction getTransaction() {
		return tran;
	}

	public Context(Transaction tran, WorkflowInstance instance, WorkflowStep step) {
		this(tran, instance, (ZWStepSchema) step);
	}

	public Context(Transaction tran, WorkflowInstance instance, ZWStepSchema step) {
		this.tran = tran;
		this.flow = WorkflowUtil.findWorkflow(step.getWorkflowID());
		this.instance = instance;
		this.step = step;
		if (step.getID() != 1) {
			this.variables = WorkflowUtil.getDataVariables(instance.getDataID(), step.getDataVersionID());
		}
	}

	/**
	 * ����������WorkflowUtil.findInitAction()��ʹ��
	 */
	protected Context(long workflowID) {
		this.flow = WorkflowUtil.findWorkflow(workflowID);
	}

	/**
	 * ����ִ�е�ǰ�ڵ㡢�������û���
	 */
	public String getOwner() {
		return User.getUserName();
	}

	/**
	 * ����ִ�е�ǰ�ڵ㡢�������û����ڵĻ���
	 */
	public String getOwnerOrgan() {
		return User.getBranchInnerCode();
	}

	/**
	 * �������ڴ����Ĳ�����������������ڽڵ��PreAction/PreScript����Ч��
	 */
	public void setStepOrgan(String organNames) {
		step.setAllowOrgan(organNames);
	}

	/**
	 * �������ڴ����Ĳ����������ɫ�����ڽڵ��PreAction/PreScript����Ч��
	 */
	public void setStepRole(String roleNames) {
		step.setAllowRole(roleNames);
	}

	/**
	 * �������ڴ����Ĳ���������û������ڽڵ��PreAction/PreScript����Ч��
	 */
	public void setStepUser(String userNames) {
		step.setAllowUser(userNames);
	}

	/**
	 * ��ȡ����ֵ
	 */
	public Object getValue(String fieldName) {
		return variables.get(fieldName);
	}

	/**
	 * ���ñ�����ֵ
	 */
	public void setValue(String fieldName, String value) {
		variableFlag = true;
		variables.put(fieldName, value);
	}

	/**
	 * ���ñ�����ֵ
	 */
	public void setValue(String fieldName, long value) {
		variableFlag = true;
		variables.put(fieldName, value);
	}

	/**
	 * ���ñ�����ֵ
	 */
	public void setValue(String fieldName, int value) {
		variableFlag = true;
		variables.put(fieldName, value);
	}

	/**
	 * ��������ݺͲ����������޸�
	 */
	public void save() {
		tran.add(step, Transaction.UPDATE);
		if (variableFlag) {
			WorkflowUtil.saveDataVariables(this);
		}
	}

	/**
	 * �жϵ�ǰ�û��Ƿ�����ĳ����ɫ
	 */
	public boolean isOwnRole(String roleName) {
		String roles = "," + PlatformCache.getUserRole(User.getUserName()) + ",";
		roleName = "," + roleName + ",";
		return roles.indexOf(roleName) >= 0;
	}

	/**
	 * ���ص�ǰ����ʵ��������
	 */
	public String getFlowName() {
		return instance.getName();
	}

	/**
	 * ���ص�ǰ���̵�����
	 */
	public String getWorkflowName() {
		return WorkflowUtil.findWorkflow(instance.getWorkflowID()).getName();
	}

	/**
	 * ���ص�ǰ���������
	 */
	public String getStepName() {
		return WorkflowUtil.getStepName(flow.getID(), step.getNodeID());
	}

	public void gotoStep(String stepName) throws Exception {
		Node[] nodes = flow.getNodes();
		for (int i = 0; i < nodes.length; i++) {
			Node node = nodes[i];
			if (!node.getType().equals(Workflow.ACTIONNODE) && node.getName().equalsIgnoreCase(stepName)) {
				WorkflowStep newStep = new WorkflowStep();
				newStep.setID(NoUtil.getMaxID("WorkflowStepID"));
				newStep.setWorkflowID(flow.getID());

				// �������и���Ȩ����Ϣ���Ա��ڽű����޸�
				newStep.setAllowOrgan(node.getData().getString("Organ"));
				newStep.setAllowRole(node.getData().getString("Role"));
				newStep.setAllowUser(node.getData().getString("User"));

				newStep.setNodeID(node.getID());
				newStep.setActionID(WorkflowAction.SCRIPT_ACTIONID);
				newStep.setInstanceID(instance.getID());

				newStep.setPreviousStepID(step.getID());
				newStep.setStartTime(new Date());
				newStep.setOperators(null);
				newStep.setState(WorkflowStep.UNREAD);
				newStep.setAddTime(new Date());
				newStep.setAddUser(User.getUserName());

				if (node.getType().equals(Workflow.STARTNODE)) {// ��ʼ�ڵ����
					newStep.setAllowUser(instance.getAddUser());
					newStep.setState(WorkflowStep.UNDERWAY);
					newStep.setOwner(instance.getAddUser());
				}

				Context context = new Context(tran, instance, newStep);// �����µĲ������Ҫ����������
				// onStepCreate()����ǰ���¼�ִ��
				WorkflowUtil.findAdapter().onStepCreate(context);
				WorkflowAction.executeMethod(this, flow.findNode(newStep.getNodeID()), "Pre");// ��һ�ڵ��ǰ�÷���
				context.save();

				// ��鹤�����Ƿ����
				if (Workflow.ENDNODE.equalsIgnoreCase(node.getType())) {
					// ������ǰ����
					newStep.setState(WorkflowStep.FINISHED);
					newStep.setFinishTime(new Date());
					newStep.setOwner("SYSTEM");

					// ��������������
					instance.setState(WorkflowInstance.COMPLETED);
					tran.add(context.getInstance(), Transaction.UPDATE);
				}
				tran.add(newStep, Transaction.INSERT);
			}
		}
	}
}
