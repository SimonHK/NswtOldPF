package com.nswt.cms.document;

import java.util.List;

import com.nswt.cms.pub.CMSCache;
import com.nswt.cms.pub.PubFun;
import com.nswt.framework.Config;
import com.nswt.framework.Page;
import com.nswt.framework.User;
import com.nswt.framework.controls.DataGridAction;
import com.nswt.framework.data.DataRow;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.utility.Filter;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.Application;
import com.nswt.platform.Priv;
import com.nswt.platform.UserList;
import com.nswt.schema.ZCArticleSchema;
import com.nswt.schema.ZCCatalogConfigSchema;
import com.nswt.schema.ZWInstanceSchema;
import com.nswt.workflow.WorkflowStep;
import com.nswt.workflow.WorkflowUtil;

/**
 * ����������
 * 
 * @Author ����
 * @Date 2007-8-17
 * @Mail lanjun@nswt.com
 */
public class WorkList extends Page {

	public static void dg1DataBind(DataGridAction dga) {
		QueryBuilder qb = null;
		DataTable dt = null;
		String listType = dga.getParam("Type");
		if (UserList.ADMINISTRATOR.equals(User.getUserName()) && StringUtil.isEmpty(listType)) {
			listType = "ALL";// ����ԱĬ����������ת�е��ĵ�
		}
		if (StringUtil.isEmpty(listType) || "TOME".equals(listType)) {
			// ���Ҵ�����ĵ�
			qb = new QueryBuilder(
					"select (select name from zccatalog d where d.id=a.catalogid) as CatalogIDName,"
							+ " a.id,a.catalogid,a.cataloginnercode,a.title,a.workflowid,a.adduser,a.addtime,a.status,"
							+ " b.workflowid as workflowconfigid,b.AllowOrgan,b.AllowRole,b.AllowUser,b.Owner,b.State,'' as StateName,'' as StepName,b.NodeID,b.ActionID,'' as ActionName"
							+ " from ZCArticle a,ZWStep b,ZWInstance c");
			if (Config.isSybase()) {
				qb.append(" where convert(varchar,a.ID)=c.DataID");
			} else {
				qb.append(" where a.ID=c.DataID");
			}
			qb.append(" and b.InstanceID=c.ID and a.siteID=? and a.status = " + Article.STATUS_WORKFLOW);
			qb.append(" and (b.State=? or (b.State=? and Owner=?))", Application.getCurrentSiteID());
			qb.add(WorkflowStep.UNREAD);
			qb.add(WorkflowStep.UNDERWAY);
			qb.add(User.getUserName());

			String keyword = dga.getParam("Keyword");
			if (StringUtil.isNotEmpty(keyword)) {
				qb.append(" and a.title like ? ", "%" + keyword.trim() + "%");
			}
			qb.append(" order by a.ID desc");
			dt = qb.executeDataTable();

			final List roleList = PubFun.getRoleCodesByUserName(User.getUserName());
			dt = dt.filter(new Filter() {
				public boolean filter(Object obj) {
					DataRow dr = (DataRow) obj;
					if (!Priv.getPriv(User.getUserName(), Priv.ARTICLE, dr.getString("cataloginnercode"),
							Priv.ARTICLE_BROWSE)
							|| !Priv.getPriv(User.getUserName(), Priv.ARTICLE, dr.getString("cataloginnercode"),
									Priv.ARTICLE_AUDIT)) {// ����Ҫ�����Ȩ��
						return false;
					}
					String state = dr.getString("State");
					String allowUser = "," + dr.getString("AllowUser") + ",";
					String allowRole = "," + dr.getString("AllowRole") + ",";
					String allowOrgan = "," + dr.getString("AllowOrgan") + ",";
					if (WorkflowStep.UNREAD.equals(state)) {
						dr.set("ActionName", WorkflowUtil.getActionNodeName(dr.getLong("workflowconfigid"), dr
								.getInt("ActionID")));
						dr.set("StepName", WorkflowUtil
								.getStepName(dr.getLong("workflowconfigid"), dr.getInt("nodeid")));
						dr.set("StateName", "δ��");
						if (allowUser.indexOf("," + User.getUserName() + ",") >= 0) {
							return true;
						}
						if (StringUtil.isNotEmpty(dr.getString("AllowOrgan"))
								&& allowOrgan.indexOf("," + User.getBranchInnerCode() + ",") != 0) {
							return false;
						}
						String[] roles = allowRole.split(",");
						for (int i = 0; i < roles.length; i++) {
							if (roleList.contains(roles[i])) {
								return true;
							}
						}
						return false;
					} else {
						if (dr.getInt("ActionID") != 0) {
							dr.set("ActionName", WorkflowUtil.getActionNodeName(dr.getLong("workflowconfigid"), dr
									.getInt("ActionID")));
						} else {
							dr.set("ActionName", "�½�");
						}
						dr.set("StepName", WorkflowUtil
								.getStepName(dr.getLong("workflowconfigid"), dr.getInt("nodeid")));
						dr.set("StateName", "������");
					}
					return true;
				}
			});
			DataTable newdt = new DataTable(dt.getDataColumns(), null);
			for (int i = dga.getPageIndex() * dga.getPageSize(); i < dt.getRowCount()
					&& i < (dga.getPageIndex() + 1) * dga.getPageSize(); i++) {
				newdt.insertRow(dt.getDataRow(i));
			}
			newdt.decodeColumn("Status", Article.STATUS_MAP);
			dga.setTotal(dt.getRowCount());
			dga.bindData(newdt);
		} else if ("ALL".equals(listType)) {
			// ������ת�е��ĵ�,ֻ�й���Ա�ܿ���
			qb = new QueryBuilder(
					"select (select name from zccatalog d where d.id=a.catalogid) as CatalogIDName,"
							+ " a.id,a.catalogid,a.cataloginnercode,a.title,a.workflowid,a.adduser,a.addtime,a.status,"
							+ " b.workflowid as workflowconfigid,b.AllowOrgan,b.AllowRole,b.AllowUser,b.Owner,b.State,'' as StateName,'' as StepName,b.NodeID,b.ActionID,'' as ActionName"
							+ " from ZCArticle a,ZWStep b,ZWInstance c");
			if (Config.isSybase()) {
				qb.append(" where convert(varchar,a.ID)=c.DataID");
			} else {
				qb.append(" where a.ID=c.DataID");
			}
			qb.append(" and b.InstanceID=c.ID and a.siteID=? and a.status=" + Article.STATUS_WORKFLOW);
			qb.append(" and b.State in('" + WorkflowStep.UNREAD + "','" + WorkflowStep.UNDERWAY + "')", Application
					.getCurrentSiteID());
			String keyword = dga.getParam("Keyword");
			if (StringUtil.isNotEmpty(keyword)) {
				qb.append(" and a.title like ? ", "%" + keyword.trim() + "%");
			}
			qb.append(" order by a.ID desc");
			dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
			dt.decodeColumn("Status", Article.STATUS_MAP);
			dga.setTotal(qb);
			for (int i = 0; i < dt.getRowCount(); i++) {
				DataRow dr = dt.getDataRow(i);
				if (dr.getInt("ActionID") != 0) {
					dr.set("ActionName", WorkflowUtil.getActionNodeName(dr.getLong("workflowconfigid"), dr
							.getInt("ActionID")));
				} else {
					dr.set("ActionName", "�½�");
				}
				dr.set("StepName", WorkflowUtil.getStepName(dr.getLong("workflowconfigid"), dr.getInt("nodeid")));
				String state = dr.getString("State");
				if (WorkflowStep.UNREAD.equals(state)) {
					dr.set("StateName", "δ��");
				} else {
					dr.set("StateName", "������");
				}
				if (dr.getString("State").equals(WorkflowStep.UNREAD)) {
					// �����δ��������allowUser���򽫴�������ΪAllowUser���ڻ�ǩʱ����
					String allowUser = dr.getString("AllowUser");
					String allowOrgan = dr.getString("AllowOrgan");
					String allowRole = dr.getString("AllowRole");
					if (StringUtil.isEmpty(allowRole) && StringUtil.isEmpty(allowOrgan)) {
						if (StringUtil.isNotEmpty(allowUser) && allowUser.indexOf(",") < 0) {
							dr.set("Owner", allowUser);
						}
					}
				}
			}
			dga.bindData(dt);
		} else if ("HANDLED".equals(listType)) {
			// �Ҵ�������ĵ�
			qb = new QueryBuilder(
					"select (select name from zccatalog d where d.id=a.catalogid) as CatalogIDName,"
							+ " a.id,a.catalogid,a.title,a.workflowid,a.adduser,a.addtime,a.status,"
							+ " (select workflowid from zwinstance  where zwinstance.id = a.workflowid) as workflowconfigid"
							+ " from ZCArticle a where exists (select '' from zwstep s where s.instanceid=a.workflowid and s.owner=?)"
							+ " and a.siteID=? and a.status<>" + Article.STATUS_WORKFLOW, User.getUserName(),
					Application.getCurrentSiteID());

			String keyword = dga.getParam("Keyword");
			if (StringUtil.isNotEmpty(keyword)) {
				qb.append(" and a.title like ? ", "%" + keyword.trim() + "%");
			}
			qb.append(" order by a.ID desc");
			dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
			dt.decodeColumn("Status", Article.STATUS_MAP);
			dga.setTotal(qb);
			dga.bindData(dt);
		}
	}

	public void applyStep() {
		DataTable dt = Request.getDataTable("Data");
		int failCount = 0;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < dt.getRowCount(); i++) {
			try {
				// �ж���Ŀ�Ƿ��Ǳ�������������
				String id = dt.getString(i, "ID");
				ZCArticleSchema article = new ZCArticleSchema();
				article.setID(id);
				if (!article.fill()) {
					Response.setStatus(0);
					Response.setMessage("��������δ�ҵ���Ӧ�����£�ID��" + id);
					return;
				}
				ZCCatalogConfigSchema config = CMSCache.getCatalogConfig(article.getCatalogID());
				if ("Y".equals(config.getBranchManageFlag()) && !UserList.ADMINISTRATOR.equals(User.getUserName())) {
					String branchInnerCode = article.getBranchInnerCode();
					if (StringUtil.isNotEmpty(branchInnerCode) && !User.getBranchInnerCode().equals(branchInnerCode)) {
						Response.setStatus(0);
						Response.setMessage("����������û�в����ĵ���" + article.getTitle() + "����Ȩ�ޣ�");
						return;
					}
				}

				WorkflowUtil.applyStep(dt.getLong(i, "WorkflowID"), dt.getInt(i, "NodeID"));
			} catch (Exception e) {
				e.printStackTrace();
				failCount++;
				sb.append(failCount + "��" + e.getMessage() + "<br>");
			}
		}
		if (failCount == 0) {
			Response.setMessage("����ɹ�");
		} else {
			Response.setMessage((dt.getRowCount() - failCount) + "������ɹ���" + failCount + "������ʧ��!<br><br>" + sb);
		}
	}

	public void forceEnd() {
		DataTable dt = Request.getDataTable("Data");
		int failCount = 0;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < dt.getRowCount(); i++) {
			try {
				long instanceID = dt.getLong(i, "WorkflowID");
				ZWInstanceSchema instance=new ZWInstanceSchema();
				instance.setID(instanceID);
				if(!instance.fill()){
					sb.append("��" + (i + 1) + "�С�δ�ҵ�������ʵ���������ļ�״̬תΪ������<br>");
				}else{
					long flowID=instance.getWorkflowID();
					if (WorkflowUtil.findWorkflow(flowID) == null) {// û�����̣���ֱ�ӽ��ĵ�״̬תΪ������
						new QueryBuilder("update ZCArticle set Status=? where ID=?", Article.STATUS_TOPUBLISH, dt.getLong(
								i, "ID")).executeNoQuery();
						sb.append("��" + (i + 1) + "�С�δ�ҵ����������壬�����ļ�״̬תΪ������<br>");
					}
					WorkflowUtil.forceEnd(instanceID, dt.getInt(i, "NodeID"));
				}
			} catch (Exception e) {
				e.printStackTrace();
				failCount++;
				sb.append("��" + (i + 1) + "�С�" + e.getMessage() + "<br>");
			}
		}
		if (failCount == 0) {
			Response.setMessage("ǿ�ƽ������̳ɹ�<br><br>" + sb);
		} else {
			Response.setMessage((dt.getRowCount() - failCount) + "���ɹ���" + failCount + "��ʧ��!<br><br>" + sb);
		}
	}
}
