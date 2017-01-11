package com.nswt.workflow;

/**
 * 普通的工作流异常
 * 
 * 日期 : 2010-3-24 <br>
 * 作者: NSWT <br>
 * 邮箱：nswt@nswt.com.cn <br>
 */
public class WorkflowException extends Exception {

	private static final long serialVersionUID = 1L;

	public WorkflowException(String message) {
		super(message);
	}
}
