package com.nswt.cms.document;

import java.util.Date;

import com.nswt.cms.datachannel.Publisher;
import com.nswt.cms.dataservice.ColumnUtil;
import com.nswt.cms.pub.CMSCache;
import com.nswt.cms.pub.CatalogUtil;
import com.nswt.cms.pub.PubFun;
import com.nswt.cms.site.Catalog;
import com.nswt.framework.Page;
import com.nswt.framework.User;
import com.nswt.framework.controls.DataGridAction;
import com.nswt.framework.controls.TreeAction;
import com.nswt.framework.data.DataRow;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.messages.LongTimeTask;
import com.nswt.framework.orm.SchemaUtil;
import com.nswt.framework.utility.DateUtil;
import com.nswt.framework.utility.HtmlUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringFormat;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.Application;
import com.nswt.platform.Priv;
import com.nswt.platform.UserList;
import com.nswt.platform.UserLog;
import com.nswt.platform.pub.NoUtil;
import com.nswt.platform.pub.OrderUtil;
import com.nswt.schema.BZCArticleSchema;
import com.nswt.schema.BZCArticleSet;
import com.nswt.schema.ZCArticleLogSchema;
import com.nswt.schema.ZCArticleLogSet;
import com.nswt.schema.ZCArticleSchema;
import com.nswt.schema.ZCArticleSet;
import com.nswt.schema.ZCAttachmentRelaSchema;
import com.nswt.schema.ZCAttachmentRelaSet;
import com.nswt.schema.ZCAudioRelaSchema;
import com.nswt.schema.ZCAudioRelaSet;
import com.nswt.schema.ZCCatalogConfigSchema;
import com.nswt.schema.ZCCommentSchema;
import com.nswt.schema.ZCCommentSet;
import com.nswt.schema.ZCImageRelaSchema;
import com.nswt.schema.ZCImageRelaSet;
import com.nswt.schema.ZCVideoRelaSchema;
import com.nswt.schema.ZCVideoRelaSet;
import com.nswt.schema.ZCVoteItemSchema;
import com.nswt.schema.ZDColumnValueSchema;
import com.nswt.schema.ZDColumnValueSet;
import com.nswt.workflow.Workflow;
import com.nswt.workflow.WorkflowAction;
import com.nswt.workflow.WorkflowStep;
import com.nswt.workflow.WorkflowUtil;
import com.nswt.workflow.Workflow.Node;

/**
 * ���¹���
 * 
 * @Author ����
 * @Date 2007-8-17
 * @Mail lanjun@nswt.com
 */
public class ArticleList extends Page {

	public static void magazineListDataBind(DataGridAction dga) {
		String catalogID = (String) dga.getParams().get("CatalogID");
		if (StringUtil.isEmpty(catalogID)) {
			catalogID = dga.getParams().getString("Cookie.DocList.LastMagazineCatalog");
			if (StringUtil.isEmpty(catalogID) || "null".equals(catalogID)) {
				catalogID = "0";
			}
			dga.getParams().put("CatalogID", catalogID);
		}
		dg1DataBind(dga);
	}

	public static void dg1DataBind(DataGridAction dga) {
		long catalogID = dga.getParams().getLong("CatalogID");
		if (catalogID == 0) {
			catalogID = dga.getParams().getLong("Cookie.DocList.LastCatalog");
			dga.getParams().put("CatalogID", catalogID);
		}

		if (catalogID != 0) {// ֻ���Ǳ�վ���µ���Ŀ
			if (!(Application.getCurrentSiteID() + "").equals(CatalogUtil.getSiteID(catalogID))) {
				catalogID = 0;
				dga.getParams().put("CatalogID", catalogID);
			}
		}

		// cookie���catalogID�п����û�û��Ȩ��
		if (!Priv.getPriv(User.getUserName(), Priv.ARTICLE, CatalogUtil.getInnerCode(catalogID), Priv.ARTICLE_BROWSE)) {
			dga.bindData(new DataTable());
			return;
		}

		String keyword = (String) dga.getParams().get("Keyword");
		String startDate = (String) dga.getParams().get("StartDate");
		String endDate = (String) dga.getParams().get("EndDate");
		String listType = (String) dga.getParams().get("Type");
		if (StringUtil.isEmpty(listType)) {
			listType = "ALL";
		}
		String Table = "";
		if ("ARCHIVE".equals(listType)) {// �鵵����
			Table = "BZCArticle";
		} else {
			Table = "ZCArticle";
		}
		QueryBuilder qb = new QueryBuilder(
				"select ID,Attribute,Title,AddUser,PublishDate,Addtime,Status,WorkFlowID,Type,"
						+ "TopFlag,OrderFlag,TitleStyle,TopDate,ReferTarget,ReferType,ReferSourceID from " + Table
						+ " where CatalogID=?");
		qb.add(catalogID);
		if (StringUtil.isNotEmpty(keyword)) {
			qb.append(" and title like ? ", "%" + keyword.trim() + "%");
		}
		if (StringUtil.isNotEmpty(startDate)) {
			startDate += " 00:00:00";
			qb.append(" and publishdate >= ? ", startDate);
		}
		if (StringUtil.isNotEmpty(endDate)) {
			endDate += " 23:59:59";
			qb.append(" and publishdate <= ? ", endDate);
		}

		if ("ADD".equals(listType)) { // ��ӵ��ĵ�
			qb.append(" and adduser=?", User.getUserName());
		} else if ("WORKFLOW".equals(listType)) {// ����ת
			qb.append(" and status=?", Article.STATUS_WORKFLOW);
		} else if ("TOPUBLISH".equals(listType)) {// ���������ĵ�
			qb.append(" and status=?", Article.STATUS_TOPUBLISH);
		} else if ("PUBLISHED".equals(listType)) {// �ѷ������ĵ�
			qb.append(" and status=?", Article.STATUS_PUBLISHED);
		} else if ("OFFLINE".equals(listType)) {// ��������
			qb.append(" and status=?", Article.STATUS_OFFLINE);
		} else if ("ARCHIVE".equals(listType)) {// �鵵����
			qb.append(" and BackUpMemo='Archive'");
		}else if("Editer".equals(listType)){
			qb.append(" and Prop4='Editer'");
		}else if("Member".equals(listType)){
			qb.append(" and Prop4='Member'");
		}
		qb.append(dga.getSortString());

		if (StringUtil.isNotEmpty(dga.getSortString())) {
			qb.append(" ,orderflag desc");
		} else {
			qb.append(" order by topflag desc,orderflag desc");
		}
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		if (dt != null && dt.getRowCount() > 0) {
			dt.decodeColumn("Status", Article.STATUS_MAP);
			dt.getDataColumn("PublishDate").setDateFormat("yy-MM-dd HH:mm");
		}
		setDetailWorkflowStatus(dt);// ϸ��������״̬

		Mapx attributemap = HtmlUtil.codeToMapx("ArticleAttribute");
		dt.insertColumn("Icon");
		if (dt.getRowCount() > 0) {
			for (int i = 0; i < dt.getRowCount(); i++) {
				if (StringUtil.isNotEmpty(dt.getString(i, "Attribute"))) {
					String[] array = dt.getString(i, "Attribute").split(",");
					String attributeName = "";
					for (int j = 0; j < array.length; j++) {
						if (j != array.length - 1) {
							attributeName = attributeName + attributemap.getString(array[j]) + ",";
						} else {
							attributeName = attributeName + attributemap.getString(array[j]);
						}
					}
					dt.set(i, "Title", StringUtil.htmlEncode(dt.getString(i, "Title")) + " <font class='lightred'> ["
							+ attributeName + "]</font>");
				}

				StringBuffer icons = new StringBuffer();

				String topFlag = dt.getString(i, "TopFlag");
				if ("1".equals(topFlag)) {
					String topdate = "�����ö�";
					if (StringUtil.isNotEmpty(dt.getString(i, "TopDate"))) {
						topdate = DateUtil.toString((Date) dt.get(i, "TopDate"), DateUtil.Format_DateTime);
					}
					icons.append("<img src='../Icons/icon13_stick.gif' title='��Ч����: " + topdate + "'/>");
				}

				if (StringUtil.isNotEmpty(dt.getString(i, "ReferSourceID"))) {
					int referType = dt.getInt(i, "ReferType");
					if (referType == 1) {
						icons.append("<img src='../Icons/icon13_copy.gif' title='����'/>");
					} else if (referType == 2) {
						icons.append("<img src='../Icons/icon13_refer.gif' title='����'/>");
					}
				}

				if (StringUtil.isNotEmpty(dt.getString(i, "ReferTarget"))) {
					icons.append("<img src='../Icons/icon13_copyout.gif' title='����Դ'/>");
				}

				dt.set(i, "Icon", icons.toString());
			}
		}
		dga.bindData(dt);
	}

	public static void setDetailWorkflowStatus(DataTable dt) {
		Mapx instanceIDMap = new Mapx();
		for (int i = 0; i < dt.getRowCount(); i++) {
			if (Article.STATUS_WORKFLOW == dt.getInt(i, "Status")) {
				instanceIDMap.put(dt.getString(i, "WorkflowID"), "1");
			}
		}
		String ids = StringUtil.join(instanceIDMap.keyArray());
		if (!StringUtil.checkID(ids) || instanceIDMap.size() == 0) {
			return;
		}
		QueryBuilder qb = new QueryBuilder(
				"select WorkflowID,NodeID,InstanceID,ActionID,State from ZWStep where (State=? or State=?) and InstanceID in ("
						+ ids + ") order by ID asc");
		qb.add(WorkflowStep.UNREAD);
		qb.add(WorkflowStep.UNDERWAY);
		DataTable stepTable = qb.executeDataTable();
		Mapx instanceNodeMap = new Mapx();
		Mapx actionMap = new Mapx();
		Mapx stateMap = new Mapx();

		for (int i = 0; i < stepTable.getRowCount(); i++) {
			int flowID = stepTable.getInt(i, "WorkflowID");
			int nodeID = stepTable.getInt(i, "NodeID");
			Node node = WorkflowUtil.findWorkflow(flowID).findNode(nodeID);
			instanceNodeMap.put(stepTable.getString(i, "InstanceID"), node);

			actionMap.put(stepTable.getString(i, "InstanceID"), stepTable.getString(i, "ActionID"));
			stateMap.put(stepTable.getString(i, "InstanceID"), stepTable.getString(i, "State"));
		}

		for (int i = 0; i < dt.getRowCount(); i++) {
			if (Article.STATUS_WORKFLOW == dt.getInt(i, "Status")) {
				String instanceID = dt.getString(i, "WorkflowID");
				if (instanceNodeMap.containsKey(instanceID)) {
					Node node = (Node) instanceNodeMap.get(instanceID);
					String nodeName = node.getName();
					String nodeType = node.getType();
					dt.set(i, "StatusName", nodeName);
					if (Workflow.STARTNODE.equals(nodeType)) {
						WorkflowAction action = WorkflowUtil.findAction(node.getWorkflow().getID(), actionMap
								.getInt(instanceID));
						if (action != null) {
							dt.set(i, "StatusName", action.getName());
						}
					} else if (WorkflowStep.UNREAD.equals(stateMap.getString(instanceID))) {
						dt.set(i, "StatusName", nodeName + "-δ��");
					} else if (WorkflowStep.UNDERWAY.equals(stateMap.getString(instanceID))) {
						dt.set(i, "StatusName", nodeName + "-������");
					}
				}
			}
		}
	}

	public static void dialogDg1DataBind(DataGridAction dga) {
		String catalogID = (String) dga.getParams().get("CatalogID");
		if (StringUtil.isEmpty(catalogID)) {
			catalogID = "0";
		}
		String keyword = (String) dga.getParams().get("Keyword");
		QueryBuilder qb = new QueryBuilder("select ID,Title,author,publishDate,Addtime,catalogID,topflag,SiteID "
				+ "from ZCArticle where catalogid=?", Long.parseLong(catalogID));
		if (StringUtil.isNotEmpty(keyword)) {
			qb.append(" and title like ? ", "%" + keyword.trim() + "%");
		}
		qb.append(dga.getSortString());

		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		int size = dt.getRowCount();
		String[] columnValue = new String[dt.getRowCount()];
		for (int i = 0; i < size; i++) {
			columnValue[i] = PubFun.getDocURL(dt.get(i));
		}

		dt.insertColumn("Link", columnValue);
		dga.bindData(dt);
	}

	/**
	 * �����ṩ��ʾ��Ŀ��
	 * 
	 * @param ta
	 */
	public static void treeDataBind(TreeAction ta) {
		Catalog.treeDataBind(ta);
	}

	public static Mapx init(Mapx params) {
		String catalogID = (String) params.get("CatalogID");
		if (catalogID == null) {
			return params;
		}
		DataTable dtCatalog = new QueryBuilder("select siteid from zccatalog where id=?", catalogID).executeDataTable();
		long siteID = ((Long) dtCatalog.get(0, "siteid")).longValue();
		params.put("SiteID", siteID + "");
		params.put("ListType", (String) params.get("Type"));
		return params;
	}

	public void add() {
	}

	public void up() {
		String ids = $V("ArticleIDs");
		if (!StringUtil.checkID(ids)) {
			UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_UPARTICLE, "��������ʧ��", Request.getClientIP());
			Response.setStatus(0);
			Response.setMessage("����Ĳ���!");
			return;
		}
		Transaction trans = new Transaction();
		dealArticleHistory(ids, trans, "UP", "���ߴ���");
		trans.add(new QueryBuilder("update zcarticle set Status =" + Article.STATUS_PUBLISHED
				+ ",PublishDate = ?,DownlineDate = '2999-12-31 23:59:59' where status = " + Article.STATUS_OFFLINE
				+ " and id in(" + ids + ")", new Date()));
		DataTable dt = new QueryBuilder("select Title from ZCArticle where status = " + Article.STATUS_OFFLINE
				+ " and id in (" + ids + ")").executeDataTable();
		if (trans.commit()) {
			upTask(ids);
			StringBuffer logs = new StringBuffer("����:");
			if (dt.getRowCount() > 0) {
				logs.append(dt.get(0, "Title"));
				if (dt.getRowCount() > 1) {
					logs.append(" �ȣ���" + dt.getRowCount() + "ƪ");
				}
			}
			UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_UPARTICLE, logs + "���߳ɹ�", Request.getClientIP());
			Response.setStatus(1);
		} else {

			dt = new QueryBuilder("select Title from ZCArticle where id in (" + ids + ")").executeDataTable();
			StringBuffer logs = new StringBuffer("����:");
			if (dt.getRowCount() > 0) {
				logs.append(dt.get(0, "Title"));
				if (dt.getRowCount() > 1) {
					logs.append(" �ȣ���" + dt.getRowCount() + "ƪ");
				}
			}
			UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_UPARTICLE, logs + "����ʧ��", Request.getClientIP());

			Response.setStatus(0);
		}
	}

	private long upTask(final String ids) {
		LongTimeTask ltt = new LongTimeTask() {
			public void execute() {
				Publisher p = new Publisher();
				ZCArticleSchema site = new ZCArticleSchema();
				ZCArticleSet set = site.query(new QueryBuilder("where status = " + Article.STATUS_PUBLISHED
						+ " and id in (" + ids + ")"));
				if (set != null && set.size() > 0) {
					p.publishArticle(set, false, this);
					p.publishCatalog(set.get(0).getCatalogID(), false, false);
					setPercent(100);
				}
			}
		};
		ltt.setUser(User.getCurrent());
		ltt.start();
		return ltt.getTaskID();
	}

	public static void dealArticleHistory(String ids, Transaction trans, String dealName, String dealDetail) {
		ZCArticleLogSchema articleLog = new ZCArticleLogSchema();
		String[] idarr = ids.split(",");
		for (int i = 0; i < idarr.length; i++) {
			articleLog.setID(NoUtil.getMaxID("ArticleLogID"));
			articleLog.setArticleID(idarr[i]);
			articleLog.setAction(dealName);
			articleLog.setActionDetail(dealDetail);
			articleLog.setAddUser(User.getUserName());
			articleLog.setAddTime(new Date());
			trans.add((ZCArticleLogSchema) articleLog.clone(), Transaction.INSERT);
		}
	}

	public void down() {
		String ids = $V("ArticleIDs");
		if (!StringUtil.checkID(ids)) {
			UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_DOWNARTICLE, "��������ʧ��", Request.getClientIP());
			Response.setStatus(0);
			Response.setMessage("����Ĳ���!");
			return;
		}
		Date now = new Date();

		ZCArticleSchema article = new ZCArticleSchema();
		ZCArticleSet set = article.query(new QueryBuilder("where id in(" + ids + ")"));
		for (int i = 0; i < set.size(); i++) {
			article = set.get(i);
			// �ж���Ŀ�Ƿ��Ǳ�������������
			ZCCatalogConfigSchema config = CMSCache.getCatalogConfig(article.getCatalogID());
			if ("Y".equals(config.getBranchManageFlag()) && !UserList.ADMINISTRATOR.equals(User.getUserName())) {
				String branchInnerCode = article.getBranchInnerCode();
				if (StringUtil.isNotEmpty(branchInnerCode) && !User.getBranchInnerCode().equals(branchInnerCode)) {
					Response.setStatus(0);
					Response.setMessage("����������û�в����ĵ���" + article.getTitle() + "����Ȩ�ޣ�");
					return;
				}
			}
		}

		Transaction trans = new Transaction();
		dealArticleHistory(ids, trans, "DOWN", "���ߴ���");
		trans.add(new QueryBuilder("update zcarticle set Status = " + Article.STATUS_OFFLINE
				+ ",TopFlag='0',DownlineDate = ?,modifyTime=? where status = " + Article.STATUS_PUBLISHED
				+ " and id in(" + ids + ")", now, now));
		if (trans.commit()) {
			DataTable dt = new QueryBuilder("select Title from ZCArticle where id in (" + ids + ")").executeDataTable();
			StringBuffer logs = new StringBuffer("����:");
			if (dt.getRowCount() > 0) {
				logs.append(dt.get(0, "Title"));
				if (dt.getRowCount() > 1) {
					logs.append(" �ȣ���" + dt.getRowCount() + "ƪ");
				}
			}
			UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_DOWNARTICLE, logs + "���߳ɹ�", Request.getClientIP());

			ZCArticleSchema site = new ZCArticleSchema();
			set = site.query(new QueryBuilder("where status = " + Article.STATUS_OFFLINE + " and id in (" + ids + ")"));
			downTask(set);

			Response.setStatus(1);
		} else {

			DataTable dt = new QueryBuilder("select Title from ZCArticle where id in (" + ids + ")").executeDataTable();
			StringBuffer logs = new StringBuffer("����:");
			if (dt.getRowCount() > 0) {
				logs.append(dt.get(0, "Title"));
				if (dt.getRowCount() > 1) {
					logs.append(" �ȣ���" + dt.getRowCount() + "ƪ");
				}
			}
			UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_DOWNARTICLE, logs + "����ʧ��", Request.getClientIP());

			Response.setStatus(0);
		}
	}

	private long downTask(final ZCArticleSet set) {
		LongTimeTask ltt = new LongTimeTask() {
			public void execute() {
				Publisher p = new Publisher();
				if (set != null && set.size() > 0) {
					p.deletePubishedFile(set);

					Mapx catalogMap = new Mapx();
					for (int k = 0; k < set.size(); k++) {
						catalogMap.put(set.get(k).getCatalogID() + "", set.get(k).getCatalogID() + "");
						String pid = CatalogUtil.getParentID(set.get(k).getCatalogID());
						while (StringUtil.isNotEmpty(pid) && !"null".equals(pid) && !"0".equals(pid)) {
							catalogMap.put(pid, pid);
							pid = CatalogUtil.getParentID(pid);
						}
					}

					// ���ɱ�����Ŀ
					Object[] vs = catalogMap.valueArray();
					for (int j = 0; j < catalogMap.size(); j++) {
						String listpage = CatalogUtil.getData(vs[j].toString()).getString("ListPage");
						if (StringUtil.isEmpty(listpage) || Integer.parseInt(listpage)<1) {
							listpage = "20"; // Ĭ��ֻ����ǰ��ʮҳ
						}
						p.publishCatalog(Long.parseLong(vs[j].toString()), false, false, Integer.parseInt(listpage));
						setPercent(getPercent() + 5);
						setCurrentInfo("������Ŀҳ��");
					}
				}
				setPercent(100);
			}
		};
		ltt.setUser(User.getCurrent());
		ltt.start();
		return ltt.getTaskID();
	}

	//У������״̬��ָ��״̬���ĵ�����ɾ��
	private boolean checkArticleStatus(ZCArticleSet set, String allowArticleStatus) {
		DataTable dt = set.toDataTable();
		dt.decodeColumn("Status", Article.STATUS_MAP);
		setDetailWorkflowStatus(dt);
		
		if (!allowArticleStatus.startsWith(",")) {
			allowArticleStatus = "," + allowArticleStatus;
		}
		
		if (!allowArticleStatus.endsWith(",")) {
			allowArticleStatus = allowArticleStatus + ",";
		}
		
		for (int i=0; dt!=null && i<dt.getRowCount(); i++) {
			if (!checkArticleStatus(dt.get(i), allowArticleStatus)) {
				return false;
			}
		}
		return true;
	}
	
	private boolean checkArticleStatus(DataRow dr, String notDeleteArticleStatus) {
		if (StringUtil.isNotEmpty(notDeleteArticleStatus) 
				&& notDeleteArticleStatus.indexOf(","+dr.getString("Status")+",")!=-1) {
			if (dr.getInt("Status") == Article.STATUS_PUBLISHED) {
				return false;
			} else if (UserList.ADMINISTRATOR.equalsIgnoreCase(User.getUserName())) {
				return true;
			} else if (dr.getInt("Status") == Article.STATUS_WORKFLOW) {
				if (WorkflowUtil.isStartStep(dr.getLong("WorkFlowID")) 
						&& dr.getString("AddUser").equals(User.getUserName())) {
					return true;
				}
				return false;
			} else {
				return false;
			}
		}
		
		return true;
	}

	/**
	 * �޸ļ�¼ 20160525,shb,ɾ������ͼƬ����,ɾ�����µ��������
	 * 
	 */
	public void del() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_DELARTICLE, "ɾ������ʧ��", Request.getClientIP());
			Response.setStatus(0);
			Response.setMessage("����Ĳ���!");
			return;
		}

		Transaction trans = new Transaction();
		ZCArticleSchema article = new ZCArticleSchema();
		ZCArticleSet set = article.query(new QueryBuilder("where id in (" + ids
				+ ") or id in (select id from zcarticle where refersourceid in (" + ids + ") )"));

		String notDeleteArticleStatus = Article.STATUS_PUBLISHED + "," + Article.STATUS_WORKFLOW + ","
				+ Article.STATUS_TOPUBLISH; // ���ڸ�״̬�µ��ĵ�������ɾ��
		if (!UserList.ADMINISTRATOR.equalsIgnoreCase(User.getUserName()) && !checkArticleStatus(set, notDeleteArticleStatus)) {
			UserLog
					.log(UserLog.ARTICLE, UserLog.ARTICLE_DELARTICLE, "�ѷ������ĵ�����ת�е��ĵ�����ɾ��,�����ߺ���ɾ��", Request
							.getClientIP());
			Response.setStatus(0);
			Response.setMessage("�ѷ������ĵ�����ת�е��ĵ�����ɾ��,�����ߺ���ɾ��!");
			return;
		}

		trans.add(set, Transaction.DELETE_AND_BACKUP);
		Mapx catalogMap = new Mapx();
		StringBuffer logs = new StringBuffer("ɾ������:");
		// ɾ�����µ�������۵�
		for (int i = 0; i < set.size(); i++) {
			article = set.get(i);
			// �ж���Ŀ�Ƿ��Ǳ�������������
			ZCCatalogConfigSchema config = CMSCache.getCatalogConfig(article.getCatalogID());
			if ("Y".equals(config.getBranchManageFlag()) && !UserList.ADMINISTRATOR.equals(User.getUserName())) {
				String branchInnerCode = article.getBranchInnerCode();
				if (StringUtil.isNotEmpty(branchInnerCode) && !User.getBranchInnerCode().equals(branchInnerCode)) {
					Response.setStatus(0);
					Response.setMessage("����������û�в����ĵ���" + article.getTitle() + "����Ȩ�ޣ�");
					return;
				}
			}

			String sqlArticleCount = "update zccatalog set " + "total = total-1,isdirty=1 where innercode in("
					+ CatalogUtil.getParentCatalogCode(article.getCatalogInnerCode()) + ")";
			trans.add(new QueryBuilder(sqlArticleCount));

			// ����ɾ����Ϣ
			StringFormat sf = new StringFormat("����Ϊ ? ���ĵ�����ɾ��");
			sf.add("<font class='red'>" + article.getTitle() + "</font>");
			String subject = sf.toString();

			sf = new StringFormat("�������ı���Ϊ ? ���ĵ������� ? �� ? ɾ����");
			sf.add("<font class='red'>" + article.getTitle() + "</font>");
			sf.add("<font class='red'>" + DateUtil.getCurrentDateTime() + "</font>");
			sf.add("<font class='red'>" + User.getUserName() + "</font>");

			MessageCache.addMessage(trans, subject, sf.toString(), new String[] { article.getAddUser() }, "SYSTEM",
					false);

			// ɾ��������ص��Զ����ֶ�
			ZDColumnValueSchema colValue = new ZDColumnValueSchema();
			colValue.setRelaID(article.getID() + "");
			colValue.setRelaType(ColumnUtil.RELA_TYPE_DOCID);// ����ӣ��п�������ID����ĿID�ظ�
			ZDColumnValueSet colValueSet = colValue.query();
			trans.add(colValueSet, Transaction.DELETE_AND_BACKUP);

			// ɾ������ͼƬ����
			ZCImageRelaSchema imageRela = new ZCImageRelaSchema();
			imageRela.setRelaID(article.getID() + "");
			imageRela.setRelaType(Article.RELA_IMAGE);// ����ӣ���Ҫ����ͼƬ������
			ZCImageRelaSet imageRelaSet = imageRela.query();
			trans.add(imageRelaSet, Transaction.DELETE_AND_BACKUP);

			// ɾ��������Ƶ����
			ZCVideoRelaSchema videoRela = new ZCVideoRelaSchema();
			videoRela.setRelaID(article.getID() + "");
			videoRela.setRelaType(Article.RELA_VIDEO);
			ZCVideoRelaSet videoRelaSet = videoRela.query();
			trans.add(videoRelaSet, Transaction.DELETE_AND_BACKUP);

			// ɾ�����¸�������
			ZCAttachmentRelaSchema attachmentRela = new ZCAttachmentRelaSchema();
			attachmentRela.setRelaID(article.getID() + "");
			attachmentRela.setRelaType(Article.RELA_ATTACH);
			ZCAttachmentRelaSet attachmentRelaSet = attachmentRela.query();
			trans.add(attachmentRelaSet, Transaction.DELETE_AND_BACKUP);

			// ɾ��������Ƶ����
			ZCAudioRelaSchema audioRela = new ZCAudioRelaSchema();
			audioRela.setRelaID(article.getID() + "");
			ZCAudioRelaSet audioRelaSet = audioRela.query();
			trans.add(audioRelaSet, Transaction.DELETE_AND_BACKUP);

			// ɾ�����µ��������
			ZCCommentSchema comment = new ZCCommentSchema();
			comment.setRelaID(article.getID() + "");
			ZCCommentSet commentSet = comment.query();
			trans.add(commentSet, Transaction.DELETE_AND_BACKUP);

			// ɾ��ͶƱ
			ZCVoteItemSchema voteitem = new ZCVoteItemSchema();
			voteitem.setVoteDocID(article.getID());
			trans.add(voteitem.query(), Transaction.DELETE_AND_BACKUP);

			// ������־
			ZCArticleLogSchema articleLog = new ZCArticleLogSchema();
			articleLog.setArticleID(article.getID());
			ZCArticleLogSet artilceLogSet = articleLog.query();

			articleLog.setID(NoUtil.getMaxID("ArticleLogID"));
			articleLog.setAction("DELETE");
			articleLog.setActionDetail("ɾ����ɾ��ԭ��" + $V("DeleteReason"));
			articleLog.setAddUser(User.getUserName());
			articleLog.setAddTime(new Date());
			artilceLogSet.add(articleLog);// �ȼ������һ��������־��Ȼ����ɾ������
			trans.add(artilceLogSet, Transaction.DELETE_AND_BACKUP);

			catalogMap.put(article.getCatalogID() + "", article.getCatalogInnerCode());

			if (article.getWorkFlowID() != 0) {
				WorkflowUtil.deleteInstance(trans, article.getWorkFlowID());
			}
		}

		if (set.size() > 0) {
			logs.append(set.get(0).getTitle());
			if (set.size() > 1) {
				logs.append(" �ȣ���" + set.size() + "ƪ");
			}
		}
		if (trans.commit()) {
			downTask(set);
			UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_DELARTICLE, logs + "�ɹ�", Request.getClientIP());
			Response.setStatus(1);
		} else {
			UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_DELARTICLE, logs + "ʧ��", Request.getClientIP());
			Response.setStatus(0);
			Response.setMessage("�������ݿ�ʱ��������!");
		}
	}

	public void topublish() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_TOPUBLISHARTICLE, "תΪ����������ʧ��,ids:" + ids, Request
					.getClientIP());
			Response.setStatus(0);
			Response.setMessage("����IDs��������!");
			return;
		}
		ZCArticleSchema article = new ZCArticleSchema();
		ZCArticleSet set = article.query(new QueryBuilder("where id in(" + ids
				+ ") or id in(select id from zcarticle where refersourceid in (" + ids + ") )"));
		String log = "תΪ�����������ɹ�";
		ZCArticleSet updateset = new ZCArticleSet();
		for (int i = 0; i < set.size(); i++) {
			article = set.get(i);
			if ((article.getStatus() == Article.STATUS_DRAFT || article.getStatus() == Article.STATUS_EDITING)
					&& article.getWorkFlowID() == 0) {
				article.setStatus(Article.STATUS_TOPUBLISH);
				updateset.add(article);
			} else if (article.getWorkFlowID() != 0) {
				log = "���ĵ��ڹ�����ת�У�����תΪ������";
			} else {
				log = "ֻ�С����塯�͡����±༭��������תΪ������״̬��";
			}
		}
		updateset.update();
		UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_TOPUBLISHARTICLE, "תΪ�����������ɹ�,ids:" + ids, Request.getClientIP());
		Response.setLogInfo(1, log);
	}

	public void publish() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_PUBLISHARTICLE, "���·���ʧ��", Request.getClientIP());
			Response.setStatus(0);
			Response.setMessage("����IDs��������!");
			return;
		}
		ZCArticleSchema article = new ZCArticleSchema();
		ZCArticleSet set = article.query(new QueryBuilder(" where id in (" + ids + ")"));

		ZCArticleSet referset = article.query(new QueryBuilder(" where refersourceid in (" + ids + ")"));
		if (referset.size() > 0) {
			for (int i = 0; i < referset.size(); i++) {
				String catalogInnerCode = referset.get(i).getCatalogInnerCode();
				boolean hasPriv = Priv.getPriv(User.getUserName(), Priv.ARTICLE, catalogInnerCode, Priv.ARTICLE_MANAGE);
				String workflow = CatalogUtil.getWorkflow(referset.get(i).getCatalogID());
				// �����������Ŀ����Ŀû�й��������û���Ŀ����Ŀ�����¹���Ȩ�ޣ��򷢲�ʱһ�����Ϊ����״̬
				if (hasPriv && StringUtil.isEmpty(workflow)) {
					set.add(referset.get(i));
				}
			}
		}
		StringBuffer logs = new StringBuffer("��������: ");
		if (set.size() > 0) {
			logs.append(set.get(0).getTitle());
			if (set.size() > 1) {
				logs.append(" �ȣ���" + set.size() + "ƪ");
			}
		}
		UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_PUBLISHARTICLE, logs + "�ɹ�", Request.getClientIP());

		Response.setStatus(1);
		long id = publishSetTask(set);
		$S("TaskID", "" + id);
	}

	public void changeToPublish() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("����IDs��������!");
			return;
		}
		BZCArticleSchema barticle = new BZCArticleSchema();
		BZCArticleSet bset = barticle.query(new QueryBuilder(" where id in (" + ids + ") and backupmemo='Archive' "));
		Transaction trans = new Transaction();
		for (int i = 0; i < bset.size(); i++) {
			barticle = bset.get(i);
			ZCArticleSchema article = new ZCArticleSchema();
			SchemaUtil.copyFieldValue(barticle, article);
			article.setStatus(Article.STATUS_PUBLISHED);
			article.setArchiveDate(CatalogUtil.getArchiveTime(barticle.getCatalogID()));
			trans.add(article, Transaction.INSERT);
			trans.add(barticle, Transaction.DELETE);
		}
		if (trans.commit()) {
			StringBuffer logs = new StringBuffer("�ӹ鵵����תΪ�ѷ�������: ");
			if (bset.size() > 0) {
				logs.append(bset.get(0).getTitle());
				if (bset.size() > 1) {
					logs.append(" �ȣ���" + bset.size() + "ƪ");
				}
			}
			UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_PUBLISHARTICLE, logs + "�ɹ�", Request.getClientIP());
			Response.setLogInfo(1, "תΪ�ѷ����ɹ�");
		} else {
			Response.setLogInfo(0, "תΪ�ѷ���ʧ��");
		}
	}

	private long publishSetTask(final ZCArticleSet set) {
		LongTimeTask ltt = new LongTimeTask() {
			public void execute() {
				Publisher p = new Publisher();
				setPercent(5);
				p.publishArticle(set, this);
				setPercent(100);
			}
		};
		ltt.setUser(User.getCurrent());
		ltt.start();
		return ltt.getTaskID();
	}

	/**
	 * ת������
	 * 
	 */
	public void move() {
		String articleIDs = $V("ArticleIDs");
		if (!StringUtil.checkID(articleIDs)) {
			Response.setError("�������ݿ�ʱ��������!");
			return;
		}

		String catalogID = $V("CatalogID");
		if (!StringUtil.checkID(catalogID)) {
			Response.setError("����CatalogIDʱ��������!");
			return;
		}

		Transaction trans = new Transaction();
		ZCArticleSchema srcArticle = new ZCArticleSchema();
		ZCArticleSet set = srcArticle.query(new QueryBuilder("where id in (" + articleIDs + ")"));
		long srcCatalogID = 0;

		String[] srcArticleIDs = null;
		if (set.size() > 0) {
			srcArticleIDs = new String[set.size()];
			for (int i = 0; i < set.size(); i++) {
				srcArticleIDs[i] = String.valueOf(set.get(i).getID());
			}
		}
		StringBuffer logs = new StringBuffer("ת������:");
		for (int i = 0; i < set.size(); i++) {
			ZCArticleSchema article = set.get(i);
			// �ж���Ŀ�Ƿ��Ǳ�������������
			ZCCatalogConfigSchema config = CMSCache.getCatalogConfig(article.getCatalogID());
			if ("Y".equals(config.getBranchManageFlag()) && !UserList.ADMINISTRATOR.equals(User.getUserName())) {
				String branchInnerCode = article.getBranchInnerCode();
				if (StringUtil.isNotEmpty(branchInnerCode) && !User.getBranchInnerCode().equals(branchInnerCode)) {
					Response.setStatus(0);
					Response.setMessage("����������û�в����ĵ���" + article.getTitle() + "����Ȩ�ޣ�");
					return;
				}
			}
			srcCatalogID = article.getCatalogID();
			String destCatalogID = catalogID;
			if (article.getStatus() == (long) Article.STATUS_WORKFLOW
					&& !UserList.ADMINISTRATOR.equals(User.getUserName())) {
				Response.setStatus(0);
				Response.setMessage("�ĵ�������ת�У����ܽ���ת�Ʋ�����");
				return;
			}
			String ReferTarget = article.getReferTarget();
			if (StringUtil.isNotEmpty(ReferTarget)) {// Ҫ��ֹ���Լ����Լ��ĸ��ƹ�ϵ
				ReferTarget = "," + ReferTarget + ",";
				ReferTarget = StringUtil.replaceEx(ReferTarget, "," + catalogID + ",", ",");
				ReferTarget = ReferTarget.substring(0, ReferTarget.length() - 1);
				article.setReferTarget(ReferTarget);
			}
			article.setClusterTarget(null);// ��Ŀ����,��վȺ���ƹ�ϵ���

			trans.add(new QueryBuilder("update zccatalog set total = total+1 where id=?", Long.parseLong(destCatalogID)));
			trans.add(new QueryBuilder("update zccatalog set total = total-1 where id=?", srcCatalogID));
			article.setCatalogInnerCode(CatalogUtil.getInnerCode(catalogID));
			article.setCatalogID(catalogID);
			article.setOrderFlag(OrderUtil.getDefaultOrder());
			String workflowID = CatalogUtil.getWorkflow(destCatalogID);
			if (StringUtil.isNotEmpty(workflowID)) {
				article.setWorkFlowID(null);
				trans.add(new QueryBuilder("delete from zwstep where exists (select * from zwinstance"
						+ " where dataid=? and id=zwstep.instanceID)", article.getID()));
				trans.add(new QueryBuilder("delete from zwinstance where dataid=?", article.getID()));
			}
			article.setStatus(Article.STATUS_DRAFT);

			trans.add(article, Transaction.UPDATE);

			// ������־
			ZCArticleLogSchema articleLog = new ZCArticleLogSchema();
			articleLog.setArticleID(article.getID());
			articleLog.setID(NoUtil.getMaxID("ArticleLogID"));
			articleLog.setAction("MOVE");
			articleLog.setActionDetail("ת�ơ���" + CatalogUtil.getName(srcCatalogID) + "" + "ת�Ƶ�"
					+ CatalogUtil.getName(destCatalogID) + "��");
			articleLog.setAddUser(User.getUserName());
			articleLog.setAddTime(new Date());
			trans.add(articleLog, Transaction.INSERT);
		}
		if (set.size() > 0) {
			logs.append(set.get(0).getTitle());
			if (set.size() > 1) {
				logs.append(" �ȣ���" + set.size() + "ƪ");
			}
		}
		if (trans.commit()) {
			Publisher p = new Publisher();
			// ɾ��ԭ������
			p.deletePubishedFile(set);
			// ȥ��ֱ�ӷ������߼���ת�ƺ�Ϊ����״̬
			UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_MOVEARTICLE, logs + "�ɹ�", Request.getClientIP());
			Response.setMessage("ת�Ƴɹ�");
		} else {
			UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_MOVEARTICLE, logs + "ʧ��", Request.getClientIP());
			Response.setError("�������ݿ�ʱ��������!");
		}
	}

	/**
	 * ��������
	 * 
	 */
	public void copy() {
		String articleIDs = $V("ArticleIDs");
		if (!StringUtil.checkID(articleIDs)) {
			UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_COPYARTICLE, "��������ʧ��", Request.getClientIP());
			Response.setStatus(0);
			Response.setMessage("����ArticleIDʱ��������!");
			return;
		}
		String catalogIDs = $V("CatalogIDs");
		if (!StringUtil.checkID(catalogIDs)) {
			UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_COPYARTICLE, "��������ʧ��", Request.getClientIP());
			Response.setStatus(0);
			Response.setMessage("����CatalogIDʱ��������!");
			return;
		}
		if (catalogIDs.indexOf("\"") >= 0 || catalogIDs.indexOf("\'") >= 0) {
			UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_COPYARTICLE, "��������ʧ��", Request.getClientIP());
			Response.setStatus(0);
			Response.setMessage("����CatalogIDʱ��������!");
			return;
		}
		Transaction trans = new Transaction();
		ZCArticleSchema article = new ZCArticleSchema();
		ZCArticleSet set = article.query(new QueryBuilder("where id in (" + articleIDs + ")"));

		// ��������
		for (int i = 0; i < set.size(); i++) {
			article = set.get(i);
			if (article.getStatus() == (long) Article.STATUS_WORKFLOW
					&& !UserList.ADMINISTRATOR.equals(User.getUserName())) {
				Response.setStatus(0);
				Response.setMessage("�ĵ�������ת�У����ܽ��и��Ʋ�����");
				return;
			}

			// �����Զ����ֶ�ֵ��Ϣ
			DataTable customData = new QueryBuilder("select ColumnCode,TextValue from zdcolumnvalue where relaid=?",
					article.getID() + "").executeDataTable();
			for (int j = 0; j < customData.getRowCount(); j++) {
				Request.put(ColumnUtil.PREFIX + customData.getString(j, "ColumnCode"), customData.getString(j,
						"TextValue"));
			}
			Request.put("ReferTarget", catalogIDs);
			Article.copy(Request, trans, article);

			article.setReferTarget(catalogIDs);
			article.setReferType($V("ReferType"));
		}

		StringBuffer logs = new StringBuffer("��������:");
		if (set.size() > 0) {
			logs.append(set.get(0).getTitle());
			if (set.size() > 1) {
				logs.append(" �ȣ���" + set.size() + "ƪ");
			}

			trans.add(set, Transaction.UPDATE);
		}
		if (trans.commit()) {
			UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_COPYARTICLE, logs + "�ɹ�", Request.getClientIP());
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_COPYARTICLE, logs + "ʧ��", Request.getClientIP());
			Response.setMessage("�������ݿ�ʱ��������!");
		}
	}

	/**
	 * �����½�������,����Ĭ������ֵ��ʱ���
	 */
	public void sortArticle() {
		String target = $V("Target");
		String orders = $V("Orders");
		String type = $V("Type");
		String catalogID = $V("CatalogID");
		boolean topFlag = "true".equals($V("TopFlag"));
		if (!StringUtil.checkID(target) && !StringUtil.checkID(orders)) {
			return;
		}
		Transaction tran = new Transaction();
		if (topFlag) {
			QueryBuilder qb = new QueryBuilder("update ZCArticle set TopFlag='1' where OrderFlag in (" + orders + ")");
			tran.add(qb);
		} else {
			QueryBuilder qb = new QueryBuilder("update ZCArticle set TopFlag='0' where OrderFlag in (" + orders + ")");
			tran.add(qb);
		}
		OrderUtil.updateOrder("ZCArticle", "OrderFlag", type, target, orders, null, tran);
		if (tran.commit()) {
			final String id = catalogID;
			LongTimeTask ltt = new LongTimeTask() {
				public void execute() {
					Publisher p = new Publisher();
					String listpage = CatalogUtil.getData(id).getString("ListPage");
					if (StringUtil.isEmpty(listpage) || Integer.parseInt(listpage)<1) {
						listpage = "20"; // Ĭ��ֻ����ǰ��ʮҳ
					}
					p.publishCatalog(Long.parseLong(id), false, false, Integer.parseInt(listpage));
					setPercent(100);
				}
			};
			ltt.setUser(User.getCurrent());
			ltt.start();

			Response.setMessage("�����ɹ�");
		} else {
			Response.setError("����ʧ��");
		}
	}

	public void setTop() {
		String ids = $V("IDs");
		String topDate = $V("TopDate");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("����Ĳ���!");
			return;
		}

		QueryBuilder qb = new QueryBuilder("update ZCArticle set TopFlag='1' where id in (" + ids + ")");
		if (StringUtil.isNotEmpty(topDate)) {
			if (new Date().compareTo(DateUtil.parseDateTime(topDate + " " + $V("TopTime"))) >= 0) {
				Response.setLogInfo(0, "�ö���Ч����Ӧ���ڵ�ǰʱ��!");
				return;
			}
			qb = new QueryBuilder("update ZCArticle set TopFlag='1',TopDate='" + topDate + " " + $V("TopTime")
					+ "' where id in (" + ids + ")");
		}
		qb.executeNoQuery();
		Transaction trans = new Transaction();
		dealArticleHistory(ids, trans, "SETTOP", "�ö�����");
		if (!trans.commit()) {
			Response.setMessage("�ö�����ʱ��¼������ʷ��Ϣ����");
		}
		DataTable dt = new QueryBuilder("select Title from ZCArticle where id in (" + ids + ")").executeDataTable();
		StringBuffer logs = new StringBuffer("�ö�����:");
		if (dt.getRowCount() > 0) {
			logs.append(dt.get(0, "Title"));
			if (dt.getRowCount() > 1) {
				logs.append(" �ȣ���" + dt.getRowCount() + "ƪ");
			}
		}
		UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_TOPARTICLE, logs + "�ɹ�", Request.getClientIP());
		Response.setLogInfo(1, "�ö��ɹ�");

		// �����߳�ִ�����ŷ�������
		ZCArticleSchema article = new ZCArticleSchema();
		final ZCArticleSet set = article.query(new QueryBuilder(" where id in (" + ids + ")"));
		LongTimeTask ltt = new LongTimeTask() {
			public void execute() {
				Publisher p = new Publisher();
				p.publishCatalog(set.get(0).getCatalogID(), false, false);
				setPercent(100);
			}
		};
		ltt.setUser(User.getCurrent());
		ltt.start();
	}

	public void setNotTop() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("����Ĳ���!");
			return;
		}
		QueryBuilder qb = new QueryBuilder("update ZCArticle set TopFlag='0' where id in (" + ids + ")");
		qb.executeNoQuery();
		Transaction trans = new Transaction();
		dealArticleHistory(ids, trans, "SETNOTTOP", "ȡ���ö�");
		if (!trans.commit()) {
			Response.setMessage("ȡ���ö�����ʱ��¼������ʷ��Ϣ����");
		}
		DataTable dt = new QueryBuilder("select Title from ZCArticle where id in (" + ids + ")").executeDataTable();
		StringBuffer logs = new StringBuffer("ȡ���ö�����:");
		if (dt.getRowCount() > 0) {
			logs.append(dt.get(0, "Title"));
			if (dt.getRowCount() > 1) {
				logs.append(" �ȣ���" + dt.getRowCount() + "ƪ");
			}
		}
		UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_NOTTOPARTICLE, logs + "�ɹ�", Request.getClientIP());
		Response.setLogInfo(1, "ȡ���ö��ɹ�");

		ZCArticleSchema article = new ZCArticleSchema();
		final ZCArticleSet set = article.query(new QueryBuilder(" where id in (" + ids + ")"));
		LongTimeTask ltt = new LongTimeTask() {
			public void execute() {
				Publisher p = new Publisher();
				p.publishCatalog(set.get(0).getCatalogID(), false, false);
				setPercent(100);
			}
		};
		ltt.setUser(User.getCurrent());
		ltt.start();
	}
}
