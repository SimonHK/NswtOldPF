package com.nswt.workflow;

import com.nswt.framework.Config;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.Mapx;

/**
 * 工作流适配器
 * 
 * 日期 : 2010-1-11 <br>
 * 作者: NSWT <br>
 * 邮箱：nswt@nswt.com.cn <br>
 */
public abstract class WorkflowAdapter {
	/**
	 * 临时保存时触发此方法
	 */
	public void onTemporarySave(Context context) {
		// 一般情况下不需要特别处理
	}

	/**
	 * 步骤回退时触发此方法
	 */
	public void onStepCancel(Context context) {
		// 一般情况下不需要特别处理
	}

	/**
	 * 步骤被创建时触发此方法
	 */
	public void onStepCreate(Context context) {
		// 一般情况下不需要特别处理
	}

	/**
	 * 动作执行时触发此方法
	 */
	public void onActionExecute(Context context,WorkflowAction action) {
		// 一般情况下不需要特别处理
	}

	/**
	 * 通知下一步处理人,仅当工作设置为“通知下一步处理人”时才会调用本方法
	 */
	public void notifyNextStep(Context context, String[] users) {
		// 一般情况下不需要特别处理，仅当工作设置为“通知下一步处理人”时才会调用本方法
		String className = Config.getValue("App.WorkflowAdapter");
		LogUtil.warn("ID为" + context.getWorkflow().getID() + "的流程配置了'通知下一步处理人'选项，但" + className
				+ "未实现notifyNextStep()方法");
	}

	/**
	 * 工作流定义被删除时调用此方法
	 */
	public abstract void onWorkflowDelete(Transaction tran, long workflowID);

	/**
	 * 获取与工作流相关联的数据容器
	 */
	public abstract Mapx getVariables(String dataID, String dataVersionID);

	/**
	 * 保存与工作流相关联的数据
	 */
	public abstract boolean saveVariables(Context context);
}
