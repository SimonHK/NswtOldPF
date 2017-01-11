package com.nswt.platform;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.nswt.framework.Page;
import com.nswt.framework.User;
import com.nswt.framework.controls.DataGridAction;
import com.nswt.framework.data.DataRow;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.HtmlUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.pub.NoUtil;
import com.nswt.schema.ZDBranchSchema;

/**
 * @Author NSWT
 * @Date 2007-7-20
 * @Mail nswt@nswt.com.cn
 */
public class Branch extends Page {

	public static Mapx initDialog(Mapx params) {
		String branchInnerCode = params.getString("BranchInnerCode");
		if (StringUtil.isNotEmpty(branchInnerCode)) {
			ZDBranchSchema branch = new ZDBranchSchema();
			branch.setBranchInnerCode(branchInnerCode);
			branch.fill();
			params = branch.toMapx();
			params.put("ParentName",
					new QueryBuilder("select name from zdbranch where branchInnercode=?", branch.getParentInnerCode())
							.executeString());
		} else {
			params.put("ParentInnerCode",
					HtmlUtil.dataTableToOptions(getBranchTable(), params.getString("ParentInnerCode")));
		}
		return params;
	}

	public static DataTable getBranchTable() {
		DataTable dt = new QueryBuilder("select Name,BranchInnerCode,TreeLevel,ParentInnerCode"
				+ " from zdbranch order by orderflag").executeDataTable();
		dt = DataGridAction.sortTreeDataTable(dt, "BranchInnerCode", "ParentInnerCode");
		com.nswt.cms.pub.PubFun.indentDataTable(dt);
		return dt;
	}

	public static void dg1DataBind(DataGridAction dga) {
		String parentInnerCode = (String) dga.getParam("ParentInnerCode");
		QueryBuilder qb = new QueryBuilder("select * from zdbranch order by orderflag");
		DataTable dt = qb.executeDataTable();
		dt = DataGridAction.sortTreeDataTable(dt, "BranchInnerCode", "ParentInnerCode");
		DataTable result = new DataTable(dt.getDataColumns(), null);
		if (StringUtil.isNotEmpty(parentInnerCode)) {
			for (int i = 0; i < dt.getRowCount(); i++) {
				if (parentInnerCode.startsWith(dt.getString(i, "BranchInnerCode"))) {
					result.insertRow(dt.getDataRow(i));
					dt.set(i,
							"Name",
							"<a href='Branch.jsp?ParentInnerCode=" + dt.get(i, "BranchInnerCode") + "'>"
									+ dt.get(i, "Name") + "</a>");
				}
				if (dt.getString(i, "BranchInnerCode").equals(parentInnerCode)) {
					int level = dt.getInt(i, "TreeLevel");
					for (int j = i + 1; j < dt.getRowCount(); j++) {
						int level2 = dt.getInt(j, "TreeLevel");
						if (level2 <= level) {
							break;
						}
						if (level2 <= level + 1) {
							result.insertRow(dt.getDataRow(j));
							if (j != dt.getRowCount() - 1 && dt.getInt(j + 1, "TreeLevel") > level2) {
								dt.set(j, "Name", "<a href='Branch.jsp?ParentInnerCode=" + dt.get(j, "BranchInnerCode")
										+ "'>" + dt.get(j, "Name") + "</a>");
							}
						}
					}
					break;
				}
			}
		} else {
			for (int i = 0; i < dt.getRowCount(); i++) {
				int level = dt.getInt(i, "TreeLevel");
				if (level <= 2) {
					result.insertRow(dt.getDataRow(i));
					if (i != dt.getRowCount() - 1 && dt.getInt(i + 1, "TreeLevel") > level) {
						dt.set(i, "Name", "<a href='Branch.jsp?ParentInnerCode=" + dt.get(i, "BranchInnerCode") + "'>"
								+ dt.get(i, "Name") + "</a>");
					}
				}
			}
		}
		dga.bindData(result);
	}

	public void add() {
		String parentInnerCode = $V("ParentInnerCode");
		Transaction trans = new Transaction();
		if (StringUtil.isEmpty(parentInnerCode)) {
			parentInnerCode = "0";
			ZDBranchSchema branch = new ZDBranchSchema();
			branch.setValue(Request);
			branch.setBranchInnerCode(NoUtil.getMaxNo("BranchInnerCode", 4));
			branch.setParentInnerCode(parentInnerCode);
			branch.setTreeLevel(1);
			branch.setType("0");
			branch.setIsLeaf("Y");

			DataTable dt = new QueryBuilder("select * from zdbranch order by orderflag").executeDataTable();
			String orderflag = "";
			if (dt != null && dt.getRowCount() > 0) {
				orderflag = dt.getString(dt.getRowCount() - 1, "OrderFlag");
			} else {
				orderflag = "0";
			}
			branch.setOrderFlag(orderflag + 1);
			branch.setAddTime(new Date());
			branch.setAddUser(User.getUserName());
			trans.add(branch, Transaction.INSERT);

			trans.add(new QueryBuilder("update zdbranch set orderflag = orderflag+1 where orderflag>?", orderflag));
			if (trans.commit()) {
				Response.setLogInfo(1, "�½��ɹ�");
			} else {
				Response.setLogInfo(0, "�½�ʧ��");
			}
		} else {
			ZDBranchSchema pBranch = new ZDBranchSchema();
			pBranch.setBranchInnerCode(parentInnerCode);
			pBranch.fill();
			long pTreeLevel = pBranch.getTreeLevel();

			ZDBranchSchema branch = new ZDBranchSchema();
			branch.setValue(Request);
			branch.setBranchInnerCode(NoUtil.getMaxNo("BranchInnerCode", pBranch.getBranchInnerCode(), 4));
			branch.setParentInnerCode(pBranch.getBranchInnerCode());
			branch.setTreeLevel(pTreeLevel + 1);
			branch.setType("0");
			branch.setIsLeaf("Y");
			branch.setAddTime(new Date());
			branch.setAddUser(User.getUserName());

			DataTable dt = new QueryBuilder("select * from zdbranch where BranchInnerCode like '"
					+ pBranch.getBranchInnerCode() + "%' order by orderflag").executeDataTable();
			long orderflag = Long.parseLong(dt.getString(dt.getRowCount() - 1, "OrderFlag"));
			branch.setOrderFlag(orderflag + 1);

			trans.add(new QueryBuilder("update zdbranch set orderflag = orderflag+1 where orderflag>?", orderflag));
			trans.add(branch, Transaction.INSERT);

			trans.add(new QueryBuilder("update zdbranch set IsLeaf='N' where branchInnerCode =?", pBranch
					.getBranchInnerCode()));
			if (trans.commit()) {
				Response.setLogInfo(1, "�½��ɹ�");
			} else {
				Response.setLogInfo(0, "�½�ʧ��");
			}
		}
	}

	public void save() {
		String branchInnerCode = $V("BranchInnerCode");
		Transaction trans = new Transaction();
		if (StringUtil.isEmpty(branchInnerCode)) {
			Response.setLogInfo(0, "�������ݴ���");
			return;
		}
		ZDBranchSchema branch = new ZDBranchSchema();
		branch.setBranchInnerCode(branchInnerCode);
		if (!branch.fill()) {
			Response.setLogInfo(0, branchInnerCode + "���������ڣ�");
			return;
		}

		branch.setValue(Request);
		branch.setModifyUser(User.getUserName());
		branch.setModifyTime(new Date());
		trans.add(branch, Transaction.UPDATE);
		if (trans.commit()) {
			Response.setLogInfo(1, "����ɹ�!");
		} else {
			Response.setLogInfo(0, "����ʧ��!");
		}
	}

	public void del() {
		String IDs = $V("IDs");
		String[] ids = IDs.split(",");
		Transaction trans = new Transaction();
		ZDBranchSchema branch = new ZDBranchSchema();
		for (int i = 0; i < ids.length; i++) {
			branch.setBranchInnerCode(ids[i]);
			if (branch.fill()) {
				if ("0".equals(branch.getParentInnerCode())) {
					Response.setLogInfo(0, "ɾ��ʧ�ܣ�����ɾ����������");
					UserLog.log(UserLog.SYSTEM, UserLog.SYSTEM_DELBRANCH, "ɾ������:" + branch.getName() + "ʧ��",
							Request.getClientIP());
					return;
				}
				QueryBuilder qb = new QueryBuilder("where BranchInnerCode like ?", branch.getBranchInnerCode() + "%");
				trans.add(branch.query(qb), Transaction.DELETE_AND_BACKUP);
			}
		}

		if (trans.commit()) {
			UserLog.log(UserLog.SYSTEM, UserLog.SYSTEM_DELBRANCH, "ɾ�������ɹ�", Request.getClientIP());
			Response.setLogInfo(1, "ɾ���ɹ�");
		} else {
			UserLog.log(UserLog.SYSTEM, UserLog.SYSTEM_DELBRANCH, "ɾ������ʧ��", Request.getClientIP());
			Response.setLogInfo(0, "ɾ��ʧ��");
		}
	}

	public void sortBranch() {
		String orderBranch = $V("OrderBranch");
		String nextBranch = $V("NextBranch");
		String ordertype = $V("OrderType");
		if (StringUtil.isEmpty(orderBranch) || StringUtil.isEmpty(nextBranch) || StringUtil.isEmpty(ordertype)) {
			Response.setLogInfo(0, "������������");
			return;
		}

		Transaction trans = new Transaction();
		DataTable DT = new QueryBuilder("select * from zdbranch order by orderflag").executeDataTable();

		List branchList = new ArrayList();

		// ��Ҫ����Ļ������ڵ������û��������ӻ�����
		DataTable orderDT = new QueryBuilder("select * from zdbranch where branchinnercode like '" + orderBranch
				+ "%' order by orderflag").executeDataTable();

		// Ҫ���ã�����ǰ������󣩻�������Ӧ����
		DataTable nextDT = new QueryBuilder("select * from zdbranch where branchinnercode like '" + nextBranch
				+ "%' order by orderflag").executeDataTable();

		// ����������
		if ("before".equalsIgnoreCase(ordertype)) {
			for (int i = 0; i < DT.getRowCount(); i++) {
				if (DT.getString(i, "BranchInnerCode").equals(nextBranch)) {
					for (int m = 0; orderDT != null && m < orderDT.getRowCount(); m++) {
						branchList.add(orderDT.getDataRow(m));
					}
				} else if (DT.getString(i, "BranchInnerCode").equals(orderBranch)) {
					// �������������
					i = i - 1 + orderDT.getRowCount();
					continue;
				}
				branchList.add(DT.getDataRow(i));
			}

			// ����������
		} else if ("after".equalsIgnoreCase(ordertype)) {
			for (int i = 0; DT != null && i < DT.getRowCount(); i++) {
				if (DT.getString(i, "BranchInnerCode").equals(orderBranch)) {
					// �������������
					i = i - 1 + orderDT.getRowCount();
					continue;
				} else if (DT.getString(i, "BranchInnerCode").equals(nextBranch)) {

					// ���� ѡ���������� ���������
					for (int m = 0; nextDT != null && m < nextDT.getRowCount(); m++) {
						branchList.add(nextDT.getDataRow(m));
					}

					for (int j = 0; orderDT != null && j < orderDT.getRowCount(); j++) {
						branchList.add(orderDT.getDataRow(j));
					}

					// ����ѭ������
					i = i - 1 + nextDT.getRowCount();
				} else {
					branchList.add(DT.getDataRow(i));
				}
			}
		}

		for (int i = 0; branchList != null && i < branchList.size(); i++) {
			DataRow dr = (DataRow) branchList.get(i);
			trans.add(new QueryBuilder("update zdbranch set orderflag = ? where BranchInnerCode = ?", i, dr
					.getString("BranchInnerCode")));
		}
		if (trans.commit()) {
			Response.setLogInfo(1, "����ɹ���");
		} else {
			Response.setLogInfo(0, "����ʧ�ܣ�");
		}
	}
}
