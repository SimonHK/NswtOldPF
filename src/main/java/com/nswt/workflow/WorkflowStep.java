package com.nswt.workflow;

import com.nswt.schema.ZWStepSchema;

/**
 * ���̲���
 * 
 * ���� : 2010-1-7 <br>
 * ����: NSWT <br>
 * ���䣺nswt@nswt.com.cn <br>
 */
public class WorkflowStep extends ZWStepSchema {
	private static final long serialVersionUID = 1L;

	/**
	 * ����δ������
	 */
	public static final String UNREAD = "Unread";

	/**
	 * ���账����
	 */
	public static final String UNDERWAY = "Underway";

	/**
	 * ���輺���
	 */
	public static final String FINISHED = "Finish";

	/**
	 * ���輺������
	 */
	public static final String CANCEL = "Cancel";

	/**
	 * ���輺������������ȴ�������֧�Ľ��
	 */
	public static final String WAIT = "Wait";
}
