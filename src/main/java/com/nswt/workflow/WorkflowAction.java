package com.nswt.workflow;

import java.util.Date;

import com.nswt.framework.User;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.pub.NoUtil;
import com.nswt.schema.ZWStepSchema;
import com.nswt.schema.ZWStepSet;
import com.nswt.workflow.Workflow.Node;
import com.nswt.workflow.methods.ConditionMethod;
import com.nswt.workflow.methods.ConditionScript;
import com.nswt.workflow.methods.MethodScript;
import com.nswt.workflow.methods.NodeMethod;

/**
 * ��Ӧ��һ�������ڵ�
 * 
 * ���� : 2010-1-11 <br>
 * ����: NSWT <br>
 * ���䣺nswt@nswt.com.cn <br>
 */
public class WorkflowAction {
	/**
	 * ���¿�ʼ���������������������´ӿ�ʼ�ڵ�ִ��ʱ��ִ�д˶���
	 */
	public static final int RESTART_ACTIONID = -5;

	/**
	 * ��ʼ����������������ʱʹ�ô˶���.
	 */
	public static final int START_ACTIONID = -2;

	/**
	 * ��ʱ��������ִ�д˶���ֻ�ᱣ�����ݣ���Ӱ�칤������<br>
	 * �˶��������WorkflowAdapter.onTemporarySave()����
	 */
	public static final int TEMPORARYSAVE_ACTIONID = -1;

	/**
	 * ���봦������ֻ�����δ�����衣
	 */
	public static final int APPLY_ACTIONID = -3;

	/**
	 * �ű�������ͨ��Context.gotoStep()ʱ��ת�Ĳ���ὫActionID��ΪSCRIPT_ACTIONID
	 */
	public static final int SCRIPT_ACTIONID = -4;

	/**
	 * ǿ�ƽ���������ͨ��Workflow.forceEndʱǿ�ƽ�������ʱ��ʹ�ô�ID
	 */
	public static final int FORCEEND_ACTIONID = -6;

	private int id;

	private String name;

	private Mapx data;

	private Node node;

	private Context context;

	protected WorkflowAction(int id, String name, Mapx data) {
		this.id = id;
		this.name = name;
		this.data = data;
	}

	public WorkflowAction(Node node) {
		if (!node.getType().equals(Workflow.ACTIONNODE)) {
			throw new RuntimeException("WorkflowAction�����Ƕ����ڵ�!");
		}
		this.id = node.getID();
		this.name = node.getName();
		this.data = node.getData();
		this.node = node;
	}

	public int getID() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Mapx getData() {
		return data;
	}

	/**
	 * ִ�ж����ڵ㣬ת�Ƶ���һ����
	 */
	public void execute(Transaction tran, long instanceID, String memo) throws Exception {
		execute(tran, WorkflowUtil.findInstance(instanceID), memo);
	}

	public void execute(Transaction tran, long instanceID, String selectedUser, String memo) throws Exception {
		execute(tran, WorkflowUtil.findInstance(instanceID), selectedUser, memo);
	}

	public void execute(Context context, String selectedUser, String memo) throws Exception {
		execute(context.getTransaction(), context.getInstance(), context.getStep(), selectedUser, memo);
	}

	/**
	 *ִ�ж����ڵ㣬ת�Ƶ���һ����
	 */
	public void execute(Transaction tran, WorkflowInstance instance, String memo) throws Exception {
		execute(tran, instance, null, memo);
	}

	public void execute(Transaction tran, WorkflowInstance instance, String selectedUser, String memo) throws Exception {
		if (this.id == WorkflowAction.RESTART_ACTIONID) {// ���¿�ʼ
			restartInstance(tran, instance, memo);
			return;
		}
		ZWStepSchema step = WorkflowUtil.findCurrentStep(instance.getID());
		execute(tran, instance, step, selectedUser, memo);
	}

	public void execute(Transaction tran, WorkflowInstance instance, ZWStepSchema step, String selectedUser, String memo)
			throws Exception {
		if (this.id == WorkflowAction.RESTART_ACTIONID) {// ���¿�ʼ
			restartInstance(tran, instance, memo);
			return;
		}
		context = new Context(tran, instance, step);
		step.setMemo(memo);
		if (this.id == WorkflowAction.TEMPORARYSAVE_ACTIONID) {// ��ʱ����
			WorkflowUtil.findAdapter().onTemporarySave(context);
			context.save();
		} else {// һ�㶯��
			step.setState(WorkflowStep.FINISHED);
			step.setFinishTime(new Date());
			step.setOwner(User.getUserName());// ��Ҫ��һ�£����û������ֱ�Ӵ�������Ҫ��Owner
			Workflow wf = WorkflowUtil.findWorkflow(context.getStep().getWorkflowID());
			executeMethod(context, wf.findNode(step.getNodeID()), "Post");// ��һ����ĺ��÷���
			if (!context.getInstance().getState().equals(WorkflowInstance.COMPLETED)) {// �п�����һ������÷������Ѿ��������ս�
				executeMethod(context, wf.findNode(this.id), "Post");// ��������
			}
			WorkflowUtil.findAdapter().onActionExecute(context, this);
			context.save();
			if (!context.getInstance().getState().equals(WorkflowInstance.COMPLETED)) {// �п�����һ������÷������Ѿ��������ս�
				tryCreateNextStep(selectedUser);
			}
		}
	}

	/**
	 * ���¿�ʼ����
	 */
	public void restartInstance(Transaction tran, WorkflowInstance instance, String memo) {
		instance.setState(WorkflowInstance.ACTIVATED);
		tran.add(instance, Transaction.UPDATE);
		Workflow flow = WorkflowUtil.findWorkflow(instance.getWorkflowID());

		WorkflowStep newStep = new WorkflowStep();
		newStep.setID(NoUtil.getMaxID("WorkflowStepID"));
		newStep.setWorkflowID(instance.getWorkflowID());
		newStep.setNodeID(flow.getStartNode().getID());
		newStep.setActionID(WorkflowAction.RESTART_ACTIONID);
		newStep.setPreviousStepID(flow.getEndNode().getID());// ���¿�ʼ����һ���ǽ���
		newStep.setInstanceID(instance.getID());
		newStep.setDataVersionID("0");
		newStep.setOwner(User.getUserName());
		newStep.setStartTime(new Date());
		newStep.setMemo("���¿�ʼ����");
		newStep.setOperators(User.getUserName());
		newStep.setState(WorkflowStep.UNDERWAY);
		newStep.setAddTime(new Date());
		newStep.setAddUser(User.getUserName());
		tran.add(newStep, Transaction.INSERT);

		Context context = new Context(tran, instance, newStep);
		WorkflowUtil.findAdapter().onStepCreate(context);
		context.save();
	}

	/**
	 * ����ת�Ƶ���һ����
	 */
	private void tryCreateNextStep(String selectedUser) throws Exception {// ���ܷ���ֵ�����ܻ��ж����֧
		if (node == null) {
			throw new WorkflowException("����ȷ��WorkflowAction����nodeδ��ֵ");
		}
		WorkflowTransition[] wts = node.getTransitions();
		for (int i = 0; i < wts.length; i++) {
			WorkflowTransition wt = wts[i];
			// ������һ����
			if (checkConditions(wt)) {// �˴������ȼ�飬��Ϊ�����������֧�Ļ���������֧���������˴��������߼�
				if (!checkOtherLine(wt)) {// ���������֧�Ƿ���δ������Ľڵ�,����У��򽫵�ǰ����״̬Ϊ�ȴ�
					context.getStep().setState(WorkflowStep.WAIT);// �ȴ�������֧
					context.save();
					return;// �Ȳ�����
				}

				// ����ǻ�ǩ�����һ�������ˣ�����Ҫ��Ҫ��NodeID��ͬ�Ĳ����¼״̬�޸�ΪFinish
				if ("1".equals(node.getWorkflow().findNode(context.getStep().getNodeID()).getData().get("SignJointly"))) {
					QueryBuilder qb = new QueryBuilder("update ZWStep set State=? where InstanceID=? and State=?");
					qb.add(WorkflowStep.FINISHED);
					qb.add(context.getStep().getInstanceID());
					qb.add(WorkflowStep.WAIT);
				}

				ZWStepSchema newStep = new WorkflowStep();
				newStep.setID(NoUtil.getMaxID("WorkflowStepID"));
				newStep.setWorkflowID(context.getInstance().getWorkflowID());

				// �������и���Ȩ����Ϣ���Ա��ڽű����޸�
				newStep.setAllowOrgan(wt.getTargetNode().getData().getString("Organ"));
				newStep.setAllowRole(wt.getTargetNode().getData().getString("Role"));
				newStep.setAllowUser(wt.getTargetNode().getData().getString("User"));

				newStep.setNodeID(wt.getTargetNode().getID());
				newStep.setActionID(this.getID());// ��Ϊ��ǰ�Ķ���ID
				newStep.setInstanceID(context.getInstance().getID());

				newStep.setPreviousStepID(context.getStep().getID());
				newStep.setStartTime(new Date());
				newStep.setOperators(null);
				newStep.setState(WorkflowStep.UNREAD);
				newStep.setAddTime(new Date());
				newStep.setAddUser(User.getUserName());

				if ("1".equals(wt.getTargetNode().getData().get("SignJointly"))) {
					// Ϊÿ���˶�����һ������
					String[] users = WorkflowUtil.getPrivUsers(newStep);
					if (users == null || users.length == 0) {
						throw new WorkflowException("û�п���ִ����һ������û�������ϵͳ����Ա��ϵ��InstanceID=" + newStep.getInstanceID());
					}
					for (int j = 0; j < users.length; j++) {
						if (j == 0) {// ����ǰһ����ֵ
							newStep.setAllowUser(users[j]);
							newStep.setAllowOrgan(null);
							newStep.setAllowRole(null);
							continue;
						}
						ZWStepSchema step = (ZWStepSchema) newStep.clone();
						step.setAllowUser(users[j]);
						step.setAllowOrgan(null);
						step.setAllowRole(null);
						step.setID(NoUtil.getMaxID("WorkflowStepID"));
						context.getTransaction().add(step, Transaction.INSERT);
					}
				} else {
					if (StringUtil.isNotEmpty(selectedUser)) {
						if (!StringUtil.checkID(selectedUser)) {
							throw new RuntimeException("���ܵ�SQLע�룺" + selectedUser);
						}
						newStep.setAllowUser(selectedUser);
						newStep.setAllowOrgan(null);
						newStep.setAllowRole(null);
					}
				}

				if (wt.getTargetNode().getType().equals(Workflow.STARTNODE)) {// ��ʼ�ڵ����
					newStep.setAllowUser(context.getInstance().getAddUser());
					newStep.setState(WorkflowStep.UNDERWAY);
					newStep.setOwner(context.getInstance().getAddUser());
				}

				context = new Context(context.getTransaction(), context.getInstance(), newStep);// �����µĲ������Ҫ����������

				// ��鶯��ִ�к�AllowOrgan,AllowRole,AllowUser�Ľ�������û���˿���ִ����һ���裬����ʾʧ��
				if (!Workflow.ENDNODE.equalsIgnoreCase(wt.getTargetNode().getType())) {
					if ("1".equals(context.getWorkflow().getData().get("NotifyNextStep"))) {
						String[] users = WorkflowUtil.getPrivUsers(newStep);
						if (users == null || users.length == 0) {
							throw new WorkflowException("û�п���ִ����һ������û�������ϵͳ����Ա��ϵ��InstanceID=" + newStep.getInstanceID());
						}
						WorkflowUtil.findAdapter().notifyNextStep(context, users);// ֪ͨ��һ���账����
					} else {
						if (!WorkflowUtil.hasPrivUser(newStep)) {// �˷�����getPrivUser()���ܽ���
							throw new WorkflowException("û�п���ִ����һ������û�������ϵͳ����Ա��ϵ��InstanceID=" + newStep.getInstanceID());
						}
					}
				}

				// onStepCreate()����ǰ���¼�ִ��
				WorkflowUtil.findAdapter().onStepCreate(context);
				executeMethod(context,
						WorkflowUtil.findWorkflow(newStep.getWorkflowID()).findNode(newStep.getNodeID()), "Pre");// ��һ�ڵ��ǰ�÷���

				if (!context.getInstance().getState().equals(WorkflowInstance.COMPLETED)) {
					// ��鹤�����Ƿ����
					if (Workflow.ENDNODE.equalsIgnoreCase(wt.getTargetNode().getType())) {
						// ������ǰ����
						newStep.setState(WorkflowStep.FINISHED);
						newStep.setFinishTime(new Date());
						newStep.setOwner("SYSTEM");

						// ��������������
						context.getInstance().setState(WorkflowInstance.COMPLETED);
						context.getTransaction().add(context.getInstance(), Transaction.UPDATE);
					}
					context.getTransaction().add(newStep, Transaction.INSERT);
				}
				context.save();
			}
		}
	}

	/**
	 * ���ת�������Ƿ�����
	 */
	public boolean checkConditions(WorkflowTransition wt) throws Exception {
		Object[] keyArray = wt.getData().keyArray();
		boolean flag = true;
		for (int i = 0; i < keyArray.length; i++) {
			String key = (String) keyArray[i];
			String value = wt.getData().getString(key);
			if ("Script".equalsIgnoreCase(key) && StringUtil.isNotEmpty(value)) {
				ConditionScript cs = new ConditionScript();
				cs.setScript(value);
				flag = cs.validate(context);
			} else if ("Method".equalsIgnoreCase(key) && StringUtil.isNotEmpty(value)) {
				Object o = Class.forName(value).newInstance();
				ConditionMethod cm = null;
				if (o instanceof ConditionMethod) {
					cm = (ConditionMethod) o;
				} else {
					throw new WorkflowException(value + "û��ʵ��ConditionMethod������");
				}
				flag = cm.validate(context);
			}
		}
		return flag;
	}

	/**
	 * ���������֧�Ƿ��Ѿ�����<br>
	 * �����ǰʵ��δ��ɵĲ������п����ߵ���ǰ�����·������˵����ǰ����Ϊ���ս�
	 */
	private boolean checkOtherLine(WorkflowTransition wt) {
		QueryBuilder qb = new QueryBuilder("where InstanceID=? and ID<>? and (State=? or State=?)", context
				.getInstance().getID(), context.getStep().getID());// ��ѯ���������������δ��ɵĲ��裬������ǩ�Ĳ���
		qb.add(WorkflowStep.UNDERWAY);
		qb.add(WorkflowStep.UNREAD);
		ZWStepSet set = new ZWStepSchema().query(qb);
		for (int i = 0; i < set.size(); i++) {
			int nodeID = set.get(i).getNodeID();
			if (isLinked(nodeID, wt.getTargetNode().getID(), 1)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * ��������ڵ�֮���Ƿ������ͨ
	 */
	private boolean isLinked(int src, int target, int level) {// �ж��Ƿ���Դ�src�ߵ�target
		if (level > 50) {// ��������ѭ��
			return false;
		}
		Workflow wf = WorkflowUtil.findWorkflow(context.getInstance().getWorkflowID());
		WorkflowTransition[] wts = wf.findNode(src).getTransitions();
		for (int j = 0; j < wts.length; j++) {
			if (wts[j].getFromNode().getID() == wts[j].getTargetNode().getID()) {
				continue;
			}
			if (wts[j].getTargetNode().getID() == target) {
				return true;
			} else {
				if (isLinked(wts[j].getTargetNode().getID(), target, level + 1)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * ִ��ǰ�û���÷�����type=Pre��Post
	 */
	public static void executeMethod(Context context, Node node, String type) throws Exception {
		String method = node.getData().getString(type + "Action");
		String script = node.getData().getString(type + "Script");
		if (StringUtil.isNotEmpty(method)) {
			Object o = Class.forName(method).newInstance();
			NodeMethod nm = null;
			if (o instanceof NodeMethod) {
				nm = (NodeMethod) o;
			} else {
				throw new WorkflowException(method + "û��ʵ��NodeMethod������");
			}
			nm.execute(context);
		}
		if (StringUtil.isNotEmpty(script)) {
			MethodScript sa = new MethodScript(script);
			sa.execute(context);
		}
	}

	/**
	 * �����ʱ�����Ӧ��Action
	 */
	public static WorkflowAction getTemporarySaveAction() {
		return new WorkflowAction(TEMPORARYSAVE_ACTIONID, "�ݴ�", new Mapx());
	}

	/**
	 * ������¿�ʼ��Ӧ��Action
	 */
	public static WorkflowAction getRestartAction() {
		return new WorkflowAction(RESTART_ACTIONID, "���´���", new Mapx());
	}

	/**
	 * ������봦���Ӧ��Action
	 */
	public static WorkflowAction getApplyAction(int nodeID) {
		Mapx map = new Mapx();
		map.put("NodeID", "" + nodeID);
		return new WorkflowAction(APPLY_ACTIONID, "���봦��", map);
	}
}
