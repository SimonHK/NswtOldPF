package com.nswt.workflow;

import com.nswt.framework.Config;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.Mapx;

/**
 * ������������
 * 
 * ���� : 2010-1-11 <br>
 * ����: NSWT <br>
 * ���䣺nswt@nswt.com.cn <br>
 */
public abstract class WorkflowAdapter {
	/**
	 * ��ʱ����ʱ�����˷���
	 */
	public void onTemporarySave(Context context) {
		// һ������²���Ҫ�ر���
	}

	/**
	 * �������ʱ�����˷���
	 */
	public void onStepCancel(Context context) {
		// һ������²���Ҫ�ر���
	}

	/**
	 * ���豻����ʱ�����˷���
	 */
	public void onStepCreate(Context context) {
		// һ������²���Ҫ�ر���
	}

	/**
	 * ����ִ��ʱ�����˷���
	 */
	public void onActionExecute(Context context,WorkflowAction action) {
		// һ������²���Ҫ�ر���
	}

	/**
	 * ֪ͨ��һ��������,������������Ϊ��֪ͨ��һ�������ˡ�ʱ�Ż���ñ�����
	 */
	public void notifyNextStep(Context context, String[] users) {
		// һ������²���Ҫ�ر���������������Ϊ��֪ͨ��һ�������ˡ�ʱ�Ż���ñ�����
		String className = Config.getValue("App.WorkflowAdapter");
		LogUtil.warn("IDΪ" + context.getWorkflow().getID() + "������������'֪ͨ��һ��������'ѡ���" + className
				+ "δʵ��notifyNextStep()����");
	}

	/**
	 * ���������屻ɾ��ʱ���ô˷���
	 */
	public abstract void onWorkflowDelete(Transaction tran, long workflowID);

	/**
	 * ��ȡ�빤�������������������
	 */
	public abstract Mapx getVariables(String dataID, String dataVersionID);

	/**
	 * �����빤���������������
	 */
	public abstract boolean saveVariables(Context context);
}
