package com.nswt.workflow;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.nswt.cms.pub.PubFun;
import com.nswt.framework.Config;
import com.nswt.framework.User;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.UserList;
import com.nswt.platform.pub.NoUtil;
import com.nswt.schema.ZWInstanceSchema;
import com.nswt.schema.ZWInstanceSet;
import com.nswt.schema.ZWStepSchema;
import com.nswt.schema.ZWStepSet;
import com.nswt.schema.ZWWorkflowSchema;
import com.nswt.workflow.Workflow.Node;

/**
 * ������������
 * 
 * ���� : 2010-1-7 <br>
 * ����: NSWT <br>
 * ���䣺nswt@nswt.com.cn <br>
 */
public class WorkflowUtil {
	private static Mapx WorkflowMap = new Mapx();
	private static Object mutex = new Object();

	/**
	 * ���ҹ���������
	 */
	public static Workflow findWorkflow(long workflowID) {
		Long key = new Long(workflowID);
		if (WorkflowMap.containsKey(key)) {
			return (Workflow) WorkflowMap.get(key);
		} else {
			Workflow wf = new Workflow();
			wf.setID(workflowID);
			if (!wf.fill()) {
				return null;
			}
			wf.init();
			synchronized (mutex) {
				WorkflowMap.put(key, wf);
			}
			return wf;
		}
	}

	/**
	 * ����һ��������ʵ��
	 */
	public static Context createInstance(Transaction tran, long workflowID, String name, String dataID,
			String dataVersionID) throws Exception {
		WorkflowInstance instance = new WorkflowInstance();
		instance.setWorkflowID(workflowID);
		instance.setAddTime(new Date());
		instance.setAddUser(User.getUserName());
		instance.setDataID(dataID);
		instance.setName(name);
		instance.setState(WorkflowInstance.ACTIVATED);
		instance.setID(NoUtil.getMaxID("WorkflowInstanceID"));
		tran.add(instance, Transaction.INSERT);

		ZWStepSchema newStep = new ZWStepSchema();
		newStep.setID(NoUtil.getMaxID("WorkflowStepID"));
		newStep.setWorkflowID(instance.getWorkflowID());
		newStep.setNodeID(findWorkflow(instance.getWorkflowID()).getStartNode().getID());
		newStep.setActionID(WorkflowAction.START_ACTIONID);// ����ʱ�Ķ������ݴ�
		newStep.setPreviousStepID(-1);// û����һ��
		newStep.setInstanceID(instance.getID());
		newStep.setDataVersionID(dataVersionID);
		newStep.setOwner(User.getUserName());
		newStep.setStartTime(new Date());
		newStep.setMemo("");
		newStep.setOperators(User.getUserName());
		newStep.setState(WorkflowStep.UNDERWAY);
		newStep.setAddTime(new Date());
		newStep.setAddUser(User.getUserName());
		tran.add(newStep, Transaction.INSERT);

		Context context = new Context(tran, instance, newStep);
		findAdapter().onStepCreate(context);

		WorkflowAction.executeMethod(context, findWorkflow(instance.getWorkflowID()).getStartNode(), "Post");// ִ�п�ʼ�ڵ�ĺ��÷���
		return context;
	}

	/**
	 * ���ݹ�����ID������ID������ʵ��
	 */
	public static WorkflowInstance findInstance(long workflowID, String dataID) throws WorkflowException {
		ZWInstanceSchema wi = new ZWInstanceSchema();
		wi.setWorkflowID(workflowID);
		wi.setDataID(dataID);
		ZWInstanceSet set = wi.query();
		if (set.size() == 0) {
			throw new WorkflowException("δ���ҵ�ָ���Ĺ�����ʵ����WorkflowID=" + workflowID + ",DataID=" + dataID);
		}
		if (set.size() > 1) {
			throw new WorkflowException("�ҵ��Ĺ�����ʵ����������һ��WorkflowID=" + workflowID + ",DataID=" + dataID);
		}
		wi = set.get(0);
		WorkflowInstance wfi = new WorkflowInstance();
		for (int i = 0; i < wfi.getColumnCount(); i++) {
			wfi.setV(i, wi.getV(i));
		}
		return wfi;
	}

	/**
	 * ����ID����ʵ��
	 */
	public static WorkflowInstance findInstance(long instanceID) throws WorkflowException {
		ZWInstanceSchema wi = new ZWInstanceSchema();
		wi.setID(instanceID);
		if (!wi.fill()) {
			throw new WorkflowException("δ���ҵ�ָ���Ĺ�����ʵ����ID=" + instanceID);
		}
		WorkflowInstance wfi = new WorkflowInstance();
		for (int i = 0; i < wfi.getColumnCount(); i++) {
			wfi.setV(i, wi.getV(i));
		}
		return wfi;
	}

	/**
	 * ����ID���Ҳ���
	 */
	public static WorkflowStep findStep(long stepID) throws WorkflowException {
		ZWStepSchema step = new ZWStepSchema();
		step.setID(stepID);
		if (!step.fill()) {
			throw new WorkflowException("δ���ҵ�ָ���Ĺ��������裺ID=" + stepID);
		}
		WorkflowStep wfs = new WorkflowStep();
		for (int i = 0; i < wfs.getColumnCount(); i++) {
			wfs.setV(i, step.getV(i));
		}
		return wfs;
	}

	/**
	 * ��ȡ��ǰ�û����ڴ���Ĳ���
	 */
	public static ZWStepSchema findCurrentStep(long instanceID) throws WorkflowException {
		QueryBuilder qb = new QueryBuilder("where InstanceID=? and (State=? or State=?)", instanceID,
				WorkflowStep.UNDERWAY);
		qb.add(WorkflowStep.UNREAD);
		ZWStepSet set = new WorkflowStep().query(qb);
		for (int i = 0; i < set.size(); i++) {
			ZWStepSchema step = set.get(i);
			if (step.getState().equals(WorkflowStep.UNDERWAY) && step.getOwner().equals(User.getUserName())) {
				return step;
			}
		}
		for (int i = 0; i < set.size(); i++) {
			ZWStepSchema step = set.get(i);
			if (step.getState().equals(WorkflowStep.UNREAD)
					&& hasPriv(step.getAllowOrgan(), step.getAllowRole(), step.getAllowUser())) {
				if ("1".equals(findWorkflow(step.getWorkflowID()).getData().get("NotNeedApply"))) {
					return step;
				}
			}
		}
		throw new WorkflowException("δ���ҵ���ǰ����:InstanceID=" + instanceID + ",User=" + User.getUserName());
	}

	/**
	 * ��ȡ���̵Ŀ�ʼ�ڵ����ִ�еĶ���
	 */
	public static WorkflowAction[] findInitActions(long workflowID) throws Exception {
		Workflow wf = WorkflowUtil.findWorkflow(workflowID);
		Node[] nodes = wf.getNodes();
		for (int i = 0; i < nodes.length; i++) {
			if (nodes[i].getType().equals(Workflow.STARTNODE)) {
				ArrayList list = new ArrayList(4);
				// ������ʱ����Action
				list.add(WorkflowAction.getTemporarySaveAction());
				Context ctx = new Context(workflowID);
				WorkflowTransition[] tss = nodes[i].getTransitions();
				for (int j = 0; j < tss.length; j++) {
					if (tss[j].validate(ctx)) {
						list.add(new WorkflowAction(tss[j].getTargetNode()));
					}
				}
				WorkflowAction[] actions = new WorkflowAction[list.size()];
				for (int j = 0; j < list.size(); j++) {
					actions[j] = (WorkflowAction) list.get(j);
				}
				return actions;
			}
		}
		return null;
	}

	/**
	 * ��ȡ��ǰ�û�����ִ�еĶ���
	 */
	public static WorkflowAction[] findAvaiableActions(long instanceID) throws Exception {
		WorkflowInstance instance = findInstance(instanceID);
		return findAvaiableActions(instance);
	}

	/**
	 * ��ȡ��ǰ�û�����ִ�еĶ���
	 */
	public static WorkflowAction[] findAvaiableActions(WorkflowInstance instance) throws Exception {
		if (instance.getState().equals(WorkflowInstance.COMPLETED)) {
			return new WorkflowAction[0];// ����ɵ�����
		}
		ZWStepSchema step = new ZWStepSchema();
		QueryBuilder qb = new QueryBuilder("where InstanceID=? and (State=? or State=?)");
		qb.add(instance.getID());
		qb.add(WorkflowStep.UNDERWAY);
		qb.add(WorkflowStep.UNREAD);
		ZWStepSet set = step.query(qb);
		ArrayList list = new ArrayList(4);
		Workflow flow = WorkflowUtil.findWorkflow(instance.getWorkflowID());

		// �ȼ����û�����ڴ����
		boolean underwayFlag = false;
		for (int i = 0; i < set.size(); i++) {
			step = set.get(i);
			Transaction tran = new Transaction();
			Context ctx = new Context(tran, step);
			if (step.getOwner() != null && step.getOwner().equals(User.getUserName())) {// ��ǰ���ڴ��������
				Node node = flow.findNode(step.getNodeID());

				// ������ʱ����Action
				list.add(WorkflowAction.getTemporarySaveAction());

				WorkflowTransition[] tss = node.getTransitions();
				for (int j = 0; j < tss.length; j++) {
					if (tss[j].validate(ctx)) {
						list.add(new WorkflowAction(tss[j].getTargetNode()));
					}
				}
				underwayFlag = true;
				break;
			}
		}
		if (!underwayFlag) {// �����ڴ���Ĳ��������������²���
			for (int i = 0; i < set.size(); i++) {
				step = set.get(i);
				if (step.getState().equals(WorkflowStep.UNREAD)) {
					// ����Ƿ����Ȩ��
					if (hasPriv(step.getAllowOrgan(), step.getAllowRole(), step.getAllowUser())) {
						if (!"1".equals(flow.getData().get("NotNeedApply"))) {
							list.add(WorkflowAction.getApplyAction(step.getNodeID()));// ��������Action
						} else {
							// ���ֻ��һ���˿������룬��ֱ����ʾ������ť
							Transaction tran = new Transaction();
							Context ctx = new Context(tran, step);
							Node node = flow.findNode(step.getNodeID());

							// ������ʱ����Action
							list.add(WorkflowAction.getTemporarySaveAction());

							WorkflowTransition[] tss = node.getTransitions();
							for (int j = 0; j < tss.length; j++) {
								if (tss[j].validate(ctx)) {
									list.add(new WorkflowAction(tss[j].getTargetNode()));
								}
							}
							underwayFlag = true;
						}
					}
				}
			}
		}
		// ȥ���ظ���Action
		for (int i = list.size() - 1; i >= 0; i--) {
			WorkflowAction a1 = (WorkflowAction) list.get(i);
			for (int j = i - 1; j >= 0; j--) {
				WorkflowAction a2 = (WorkflowAction) list.get(j);
				if (a1.getID() == a2.getID()) {
					list.remove(i);
				}
			}
		}
		WorkflowAction[] actions = new WorkflowAction[list.size()];
		for (int i = 0; i < list.size(); i++) {
			actions[i] = (WorkflowAction) list.get(i);
		}
		return actions;
	}

	/**
	 * ����ʵ��ID�ͽڵ�ID����δ������
	 */
	public static ZWStepSchema findUnreadStep(long instanceID, int nodeID) throws WorkflowException {
		ZWStepSchema step = new ZWStepSchema();
		step.setInstanceID(instanceID);
		step.setNodeID(nodeID);
		step.setState(WorkflowStep.UNREAD);
		ZWStepSet set = step.query();
		if (set.size() > 0) {
			step = set.get(0);
			return step;
		}
		throw new WorkflowException("�Ҳ�����������Ĳ���!InstanceID=" + instanceID + ",NodeID=" + nodeID);
	}

	/**
	 * ���빤��������
	 */
	public static void applyStep(long instanceID, int nodeID) throws Exception {
		ZWStepSchema step = findUnreadStep(instanceID, nodeID);
		if (!hasPriv(step.getAllowOrgan(), step.getAllowRole(), step.getAllowUser())) {
			throw new WorkflowException("�û�û���������̲���" + step.getNodeID() + "��Ȩ�ޣ�WorkflowID=" + step.getWorkflowID());
		}
		QueryBuilder qb = new QueryBuilder(
				"update ZWStep set State=?,Owner=? where State=? and InstanceID=? and NodeID=? and ID=?");
		qb.add(WorkflowStep.UNDERWAY);
		qb.add(User.getUserName());
		qb.add(WorkflowStep.UNREAD);
		qb.add(instanceID);
		qb.add(nodeID);
		qb.add(step.getID());// ����Ҫ��StepID����ǩģʽ�¿��ܻ��ж��ͬһNodeID�Ĳ���
		if (qb.executeNoQuery() == 0) {
			throw new WorkflowException("����ʧ�ܣ����ܼ�������û�����!");
		}
	}

	/**
	 * ǿ�ƽ�������
	 */
	public static void forceEnd(long instanceID, int nodeID) throws Exception {
		Transaction tran = new Transaction();
		forceEnd(tran, instanceID, nodeID);
		if (!tran.commit()) {
			throw new WorkflowException(tran.getExceptionMessage());
		}
	}

	public static void forceEnd(Transaction tran, long instanceID, int nodeID) throws Exception {
		if (!UserList.ADMINISTRATOR.equals(User.getUserName())) {
			throw new WorkflowException("��ǰ�û�û��ǿ�ƽ������̵�Ȩ��!");
		}

		WorkflowInstance instance = findInstance(instanceID);
		Workflow flow = findWorkflow(instance.getWorkflowID());
		if (flow == null) {// ���������强��ɾ��
			LogUtil.warn("ǿ�ƽ�������ʱ����IDΪ" + instance.getWorkflowID() + "�����̲�����!");
		} else {

			// ���������ڵ�
			ZWStepSchema newStep = new ZWStepSchema();
			newStep.setID(NoUtil.getMaxID("WorkflowStepID"));
			newStep.setWorkflowID(instance.getWorkflowID());
			newStep.setNodeID(flow.getEndNode().getID());
			newStep.setActionID(WorkflowAction.FORCEEND_ACTIONID);
			newStep.setPreviousStepID(0);
			newStep.setInstanceID(instance.getID());
			newStep.setDataVersionID("0");
			newStep.setOwner(User.getUserName());
			newStep.setStartTime(new Date());
			newStep.setMemo("ǿ�ƽ�������");
			newStep.setOperators(User.getUserName());
			newStep.setState(WorkflowStep.FINISHED);
			newStep.setAddTime(new Date());
			newStep.setAddUser(User.getUserName());

			tran.add(newStep, Transaction.INSERT);
			Context context = new Context(tran, instance, newStep);
			findAdapter().onStepCreate(context);
			WorkflowAction.executeMethod(context, findWorkflow(instance.getWorkflowID()).getEndNode(), "Pre");// ִ�п�ʼ�ڵ�ĺ��÷���
		}

		instance.setState(WorkflowInstance.COMPLETED);
		tran.add(instance, Transaction.UPDATE);
		tran.add(new QueryBuilder("update ZWStep set State=? where InstanceID=?", WorkflowStep.FINISHED, instanceID));
	}

	/**
	 * �ж�һ���û��Ƿ�����ָ���Ľ�ɫ���Ż�һ���û��б���
	 */
	private static boolean hasPriv(String[] branchInnerCodes, String[] roleCodes, String[] userNames) {
		if (userNames != null) {
			for (int i = 0; i < userNames.length; i++) {
				if (userNames[i].equals(User.getUserName())) {
					return true;
				}
			}
		}
		boolean flag = false;
		if (branchInnerCodes != null) {// �������ɫ֮������Ĺ�ϵ
			String innerCode = User.getBranchInnerCode();
			if (StringUtil.isNotEmpty(innerCode)) {
				for (int i = 0; i < branchInnerCodes.length; i++) {
					if (innerCode.equals((branchInnerCodes[i]))) {
						flag = true;
						break;
					}
				}
			}
		} else {
			flag = true;
		}
		if (flag) {
			if (roleCodes != null) {
				List list = PubFun.getRoleCodesByUserName(User.getUserName());
				for (int i = 0; i < roleCodes.length; i++) {
					if (list.contains(roleCodes[i])) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private static boolean hasPriv(String branchInnerCodes, String roleCodes, String userNames) {
		String[] p1 = null;
		String[] p2 = null;
		String[] p3 = null;
		if (StringUtil.isNotEmpty(branchInnerCodes)) {
			p1 = StringUtil.splitEx(branchInnerCodes, ",");
		}
		if (StringUtil.isNotEmpty(roleCodes)) {
			p2 = StringUtil.splitEx(roleCodes, ",");
		}
		if (StringUtil.isNotEmpty(userNames)) {
			p3 = StringUtil.splitEx(userNames, ",");
		}
		return hasPriv(p1, p2, p3);
	}

	/**
	 * ���ݹ�����ID�Ͷ���ID���Ҷ���
	 */
	public static WorkflowAction findAction(long workflowID, int actionID) {
		Workflow wf = findWorkflow(workflowID);
		if (actionID == WorkflowAction.TEMPORARYSAVE_ACTIONID) {
			return WorkflowAction.getTemporarySaveAction();
		}
		if (actionID == WorkflowAction.RESTART_ACTIONID) {
			return WorkflowAction.getRestartAction();
		}
		if (actionID == WorkflowAction.START_ACTIONID) {
			return new WorkflowAction(actionID, "��ʼ��ת", new Mapx());
		}
		if (actionID == WorkflowAction.SCRIPT_ACTIONID) {
			return new WorkflowAction(actionID, "�ű���ת", new Mapx());
		}
		if (actionID == WorkflowAction.FORCEEND_ACTIONID) {
			return new WorkflowAction(actionID, "ǿ�ƽ���", new Mapx());
		}
		Node node = wf.findNode(actionID);
		if (node.getType().equals(Workflow.ACTIONNODE)) {
			return new WorkflowAction(node);
		}
		return null;
	}

	private static WorkflowAdapter adapter;

	/**
	 * ��ȡ������������
	 */
	public static WorkflowAdapter findAdapter() {
		if (adapter == null) {
			synchronized (mutex) {
				if (adapter == null) {
					String className = Config.getValue("App.WorkflowAdapter");
					if (StringUtil.isEmpty(className)) {
						throw new RuntimeException("δ���幤����������!");
					}
					try {
						Class c = Class.forName(className);
						if (!WorkflowAdapter.class.isAssignableFrom(c)) {
							throw new RuntimeException("��" + className + "δ�̳�WorkflowAdapter!");
						}
						adapter = (WorkflowAdapter) c.newInstance();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return adapter;
	}

	/**
	 * ��ȡ��������
	 */
	public static Mapx getDataVariables(String dataID, String dataVersionID) {
		return findAdapter().getVariables(dataID, dataVersionID);
	}

	/**
	 * ������������
	 */
	public static boolean saveDataVariables(Context context) {
		return findAdapter().saveVariables(context);
	}

	/**
	 * ��ȡ��������
	 */
	public static String getStepName(long workflowID, int nodeID) {
		Workflow flow = WorkflowUtil.findWorkflow(workflowID);
		if (flow == null) {
			return "δ�ҵ�������";
		}
		Node node = flow.findNode(nodeID);
		if (node == null) {
			return "δ�ҵ�����";
		}
		return node.getName();
	}

	/**
	 * ��ȡ��������
	 */
	public static String getActionNodeName(long workflowID, int actionID) {
		return findAction(workflowID, actionID).getName();
	}

	/**
	 * ɾ��ʵ��
	 */
	public static void deleteInstance(Transaction tran, long instanceID) {
		ZWInstanceSet iset = new ZWInstanceSchema().query(new QueryBuilder("where ID=?", instanceID));
		ZWStepSet sset = new ZWStepSchema().query(new QueryBuilder("where InstanceID=?", instanceID));
		tran.add(iset, Transaction.DELETE_AND_BACKUP);
		tran.add(sset, Transaction.DELETE_AND_BACKUP);
	}

	/**
	 * ���»�������
	 */
	public static void updateCache(ZWWorkflowSchema schema) {
		synchronized (mutex) {
			WorkflowMap.put(new Long(schema.getID()), Workflow.convert(schema));
		}
	}

	/**
	 * ɾ����������
	 */
	public static void deleteCache(ZWWorkflowSchema schema) {
		synchronized (mutex) {
			WorkflowMap.remove(new Long(schema.getID()));
		}
	}

	private static boolean checkPrivString(String str) {
		if (str.indexOf('\'') > 0 || str.indexOf('\"') > 0) {
			return false;
		}
		if (str.indexOf('(') > 0 || str.indexOf(')') > 0) {
			return false;
		}
		return true;
	}

	/**
	 * �жϱ��ڵ��Ƿ����û���Ȩ�����
	 */
	public static boolean hasPrivUser(ZWStepSchema step) throws WorkflowException {
		if (StringUtil.isNotEmpty(step.getAllowUser())) {
			return true;
		}
		QueryBuilder qb = new QueryBuilder("select count(1) from ZDUser where 1=1");
		if (StringUtil.isNotEmpty(step.getAllowOrgan())) {
			if (!checkPrivString(step.getAllowOrgan())) {
				throw new WorkflowException("AllowOrgan�к��зǷ��ַ�,StepID=" + step.getID());
			}
			qb.append(" and BranchInnerCode in ('" + step.getAllowOrgan().replaceAll(",", "','") + "')");
		}
		if (StringUtil.isNotEmpty(step.getAllowRole())) {
			if (!checkPrivString(step.getAllowRole())) {
				throw new WorkflowException("AllowRole�к��зǷ��ַ�,StepID=" + step.getID());
			}
			qb.append(" and exists (select 1 from ZDUserRole where UserName=ZDUser.UserName and RoleCode in ('"
					+ step.getAllowRole().replaceAll(",", "','") + "'))");
		}
		if (StringUtil.isNotEmpty(step.getAllowOrgan()) || StringUtil.isNotEmpty(step.getAllowRole())) {
			return qb.executeInt() > 0;
		}
		return false;
	}

	public static String[] getPrivUsers(ZWStepSchema step) throws WorkflowException {
		Mapx map = new Mapx();
		if (StringUtil.isNotEmpty(step.getAllowUser())) {
			if (checkPrivString(step.getAllowUser())) {
				String[] p1 = step.getAllowUser().split(",");
				for (int i = 0; i < p1.length; i += 2) {// ��ɫ��������ƶ���
					map.put(p1[i], "1");
				}
			}
		}
		QueryBuilder qb = new QueryBuilder("select username,1 from ZDUser where 1=1");
		if (StringUtil.isNotEmpty(step.getAllowOrgan())) {
			if (!checkPrivString(step.getAllowOrgan())) {
				throw new WorkflowException("AllowOrgan�к��зǷ��ַ�,StepID=" + step.getID());
			}
			qb.append(" and BranchInnerCode in ('" + step.getAllowOrgan().replaceAll(",", "','") + "')");
		}
		if (StringUtil.isNotEmpty(step.getAllowRole())) {
			if (!checkPrivString(step.getAllowRole())) {
				throw new WorkflowException("AllowRole�к��зǷ��ַ�,StepID=" + step.getID());
			}
			qb.append(" and exists (select 1 from ZDUserRole where UserName=ZDUser.UserName and RoleCode in ('"
					+ step.getAllowRole().replaceAll(",", "','") + "'))");
		}
		if (StringUtil.isNotEmpty(step.getAllowOrgan()) || StringUtil.isNotEmpty(step.getAllowRole())) {
			map.putAll(qb.executeDataTable().toMapx(0, 1));
		}
		String[] users = new String[map.size()];
		Object[] ks = map.keyArray();
		for (int i = 0; i < map.size(); i++) {
			users[i] = ks[i].toString();
		}
		return users;
	}

	/**
	 * �жϵ�ǰ�����Ƿ�����ʼ����
	 */
	public static boolean isStartStep(long instanceID) {
		QueryBuilder qb = new QueryBuilder("where InstanceID=? and (State=? or State=?)", instanceID);
		qb.add(WorkflowStep.UNDERWAY);
		qb.add(WorkflowStep.UNREAD);
		ZWStepSet set = new ZWStepSchema().query(qb);
		for (int i = 0; i < set.size(); i++) {
			if (WorkflowUtil.findWorkflow(set.get(i).getWorkflowID()).getStartNode().getID() == set.get(i).getNodeID()) {
				return true;
			}
		}
		return false;
	}
}
