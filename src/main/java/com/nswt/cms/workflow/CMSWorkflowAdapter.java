package com.nswt.cms.workflow;

import java.util.Date;

import com.nswt.cms.dataservice.ColumnUtil;
import com.nswt.cms.document.Article;
import com.nswt.cms.document.MessageCache;
import com.nswt.framework.Config;
import com.nswt.framework.User;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.orm.SchemaUtil;
import com.nswt.framework.utility.DateUtil;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.Mail;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringFormat;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.pub.NoUtil;
import com.nswt.project.avicit.SynchronizationMessage;
import com.nswt.schema.BZCArticleSchema;
import com.nswt.schema.ZCArticleLogSchema;
import com.nswt.schema.ZCArticleSchema;
import com.nswt.workflow.Context;
import com.nswt.workflow.Workflow;
import com.nswt.workflow.WorkflowAction;
import com.nswt.workflow.WorkflowAdapter;
import com.nswt.workflow.WorkflowException;
import com.nswt.workflow.WorkflowUtil;

/**
 * ���� : 2010-1-12 <br>
 * ����: NSWT <br>
 * ���䣺nswt@nswt.com.cn <br>
 */
public class CMSWorkflowAdapter extends WorkflowAdapter {

	public void onActionExecute(Context context, WorkflowAction action) {
		// ������ʷ
		ZCArticleLogSchema articleLog = new ZCArticleLogSchema();
		articleLog.setID(NoUtil.getMaxID("ArticleLogID"));
		articleLog.setArticleID(context.getInstance().getDataID());
		articleLog.setAction("WORKFLOW");
		articleLog.setActionDetail(action.getName());
		articleLog.setAddUser(User.getUserName());
		articleLog.setAddTime(new Date());
		context.getTransaction().add(articleLog, Transaction.INSERT);
	}

	public void onStepCreate(Context context) {
		// ��������
		ZCArticleSchema article = new ZCArticleSchema();
		article.setID(context.getInstance().getDataID());
		if (!article.fill()) {
			return;
		}
		BZCArticleSchema barticle = new BZCArticleSchema();
		for (int i = 0; i < article.getColumnCount(); i++) {
			barticle.setV(i, article.getV(i));
		}
		barticle.setBackupMemo("���̱���");
		barticle.setBackupNo(SchemaUtil.getBackupNo());
		barticle.setBackupOperator(User.getUserName());
		barticle.setBackupTime(new Date());

		Workflow wf = WorkflowUtil.findWorkflow(context.getStep().getWorkflowID());
		String nodeName = wf.findNode(context.getStep().getNodeID()).getName();
		String actionName = WorkflowUtil.getActionNodeName(wf.getID(), context.getStep().getActionID());

		// ������ʷ
		ZCArticleLogSchema articleLog = new ZCArticleLogSchema();
		articleLog.setID(NoUtil.getMaxID("ArticleLogID"));
		articleLog.setArticleID(context.getInstance().getDataID());
		articleLog.setAction("WORKFLOW");
		articleLog.setActionDetail("�ĵ���ת������:" + nodeName);
		articleLog.setAddUser(User.getUserName());
		articleLog.setAddTime(new Date());
		context.getTransaction().add(articleLog, Transaction.INSERT);

		// ������Ϣ
		StringFormat sf = new StringFormat("����Ϊ ? ���ĵ�����ת������ ? ��");
		sf.add("<font class='lightred'>" + barticle.getTitle() + "</font>");
		sf.add("<font class='deepred'>" + nodeName + "</font>");
		String subject = sf.toString();

		sf = new StringFormat("�������ı���Ϊ ? ���ĵ������� ? �� ? ִ�� ?������ת������  ? ��");
		sf.add("<font class='lightred'>" + barticle.getTitle() + "</font>");
		sf.add("<font class='black'>" + DateUtil.getCurrentDateTime() + "</font>");
		sf.add("<font class='deepred'>" + context.getStep().getAddUser() + "</font>");
		sf.add("<font class='deepgreen'>" + actionName + "</font>");
		sf.add("<font class='deepred'>" + nodeName + "</font>");

		MessageCache.addMessage(context.getTransaction(), subject, sf.toString(), article.getAddUser(), "SYSTEM");

		context.getStep().setDataVersionID(barticle.getBackupNo());

		context.getTransaction().add(barticle, Transaction.INSERT);
		
		try {
			String[] users = WorkflowUtil.getPrivUsers(context.getStep());
			SynchronizationMessage.synchronizationMessage("����Ϊ"+ barticle.getTitle()+"���ĵ���Ҫ�����"
					, context.getStep().getAddUser(), users, context.getInstance().getDataID());
		} catch (WorkflowException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void notifyNextStep(Context context, String[] users) {
		Workflow wf = WorkflowUtil.findWorkflow(context.getStep().getWorkflowID());
		String stepName = wf.findNode(context.getStep().getNodeID()).getName();
		StringFormat sf = new StringFormat("����ת������ ?���ĵ�������");
		sf.add("<font class='lightred'>" + context.getValue("Title") + "</font>");
		sf.add("<font class='deepred'>" + stepName + "</font>");
		String subject = sf.toString();

		sf = new StringFormat("����Ϊ ? ���ĵ������� ? �� ? ִ�� ?������ת������  ? ,�����Կ�ʼ����");
		sf.add("<font class='lightred'>" + context.getValue("Title") + "</font>");
		sf.add("<font class='black'>" + DateUtil.getCurrentDateTime() + "</font>");
		sf.add("<font class='deepred'>" + context.getStep().getAddUser() + "</font>");
		String actionName = WorkflowUtil.getActionNodeName(wf.getID(), context.getStep().getActionID());
		sf.add("<font class='deepgreen'>" + actionName + "</font>");
		sf.add("<font class='deepred'>" + stepName + "</font>");

		StringFormat mailSubjectSF = new StringFormat("�ĵ�?��ת��?,��������");
		mailSubjectSF.add(context.getValue("Title"));
		mailSubjectSF.add(stepName);
		String subjectSubject = mailSubjectSF.toString();

		for (int i = 0; i < users.length; i++) {
			MessageCache.addMessage(context.getTransaction(), subject, sf.toString(), users[i], "SYSTEM");
			Mapx mailMap = new Mapx();

			// �����ʼ�
			if ("Y".equals(Config.getValue("Workflow.SendMail"))) {
				String userName = users[i];
				DataTable dt = new QueryBuilder("select Email,RealName from ZDUser where UserName=?", userName)
						.executeDataTable();
				if (dt.getRowCount() > 0) {
					String email = dt.getString(0, "email");
					if (StringUtil.isNotEmpty(email)) {
						mailMap.put("ToUser", email);
						mailMap.put("Subject", subjectSubject);
						mailMap.put("Content", sf.toString());
						String flag = Mail.sendSimpleEmail(mailMap);
						if (Mail.SUCCESS.equals(flag)) {
							LogUtil.info("�����ʼ��ɹ���" + subjectSubject);
						} else {
							LogUtil.info("�����ʼ�ʧ�ܣ�" + subjectSubject + " " + flag);
						}
					}
				}
			}
		}
	}

	public void onWorkflowDelete(Transaction tran, long workflowID) {
		// ���¹����Ĺ�������ɾ��ʱ��Ҫ����ص�����״̬��Ϊ������
		tran.add(new QueryBuilder("update ZCArticle set Status=? where exists (select 1 from ZWInstance "
				+ "where WorkflowID=? and ID=ZCArticle.WorkflowID)", Article.STATUS_TOPUBLISH, workflowID));
		// �����Ŀ�Ĺ�������Ϊnull
		tran.add(new QueryBuilder("update ZCCatalog set Workflow=? where Workflow=?", null, workflowID+""));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nswt.workflow.WorkflowAdapter#getVariables(java.lang.String,
	 * java.lang.String)
	 */
	public Mapx getVariables(String dataID, String dataVersionID) {
		// ����CMS���ԣ�����ҪdataVersionID����ȡZCArticle�еļ�¼
		ZCArticleSchema article = new ZCArticleSchema();
		article.setID(dataID);
		article.fill();
		Mapx map = article.toCaseIgnoreMapx();// ���Դ�Сд
		DataTable dt = ColumnUtil.getColumnValue(ColumnUtil.RELA_TYPE_DOCID, dataID);
		if (dt != null && dt.getRowCount() > 0) {
			map.putAll(dt.get(0).toCaseIgnoreMapx());
		}
		return map;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nswt.workflow.WorkflowAdapter#saveVariables(com.nswt.workflow.Context
	 * )
	 */
	public boolean saveVariables(Context context) {
		ZCArticleSchema article = new ZCArticleSchema();
		article.setValue(context.getVariables());
		article.fill();
		Article.saveCustomColumn(context.getTransaction(), context.getVariables(), article.getCatalogID(), article
				.getID(), false);
		context.getTransaction().add(article, Transaction.UPDATE);
		return true;
	}

}
