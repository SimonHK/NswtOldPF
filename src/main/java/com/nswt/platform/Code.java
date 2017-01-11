package com.nswt.platform;

import java.util.Date;

import com.nswt.framework.Page;
import com.nswt.framework.User;
import com.nswt.framework.cache.CacheManager;
import com.nswt.framework.controls.DataGridAction;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.pub.OrderUtil;
import com.nswt.schema.ZDCodeSchema;
import com.nswt.schema.ZDCodeSet;

/**
 * @Author NSWT
 * @Date 2007-6-19
 * @Mail nswt@nswt.com.cn
 */
public class Code extends Page {

	public static void dg1BindCode(DataGridAction dga) {
		// ������봦�� �磺�������� ���˵������������
		String SearchCodeType = dga.getParam("SearchCodeType");
		QueryBuilder qb = new QueryBuilder(
				"select CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Memo,CodeType "
						+ "as CodeType_key,ParentCode as ParentCode_key,CodeValue as CodeValue_key from ZDCode where ParentCode ='System' ");
		qb.append(" and (prop4<>'S' or prop4 is null)");
		if (StringUtil.isNotEmpty(SearchCodeType)) {
			qb.append(" and (CodeType like ? ", "%" + SearchCodeType + "%");
			qb.append(" or CodeName like ? )", "%" + SearchCodeType + "%");
		}
		qb.append(" order by CodeType,ParentCode");
		dga.bindData(qb);
	}

	public static void dg1BindCodeList(DataGridAction dga) {
		// Type_key ����������
		QueryBuilder qb = new QueryBuilder("select CodeType,ParentCode,CodeValue,CodeName,CodeOrder ,Memo,CodeType "
				+ "as CodeType_key,ParentCode as ParentCode_key,CodeValue as CodeValue_key "
				+ "from ZDCode where ParentCode =?", dga.getParam("CodeType"));
		qb.append(" order by CodeOrder,CodeType,ParentCode");
		dga.bindData(qb);
	}

	public void dg1Edit() {
		DataTable dt = (DataTable) Request.get("DT");
		Transaction trans = new Transaction();
		for (int i = 0; i < dt.getRowCount(); i++) {
			QueryBuilder qb = new QueryBuilder(
					"update ZDCode set Codetype=?,ParentCode =?,CodeName=?,CodeValue=?,Memo=? where CodeType=? and ParentCode=? and CodeValue=?");
			qb.add(dt.getString(i, "CodeType"));
			qb.add(dt.getString(i, "ParentCode"));
			qb.add(dt.getString(i, "CodeName"));
			qb.add(dt.getString(i, "CodeValue"));
			qb.add(dt.getString(i, "Memo"));
			qb.add(dt.getString(i, "CodeType_Key"));
			qb.add(dt.getString(i, "ParentCode_Key"));
			qb.add(dt.getString(i, "CodeValue_Key"));
			trans.add(qb);
			// ���CodeType�����ˣ�����Ҫ�������������������еĴ���
			if ("System".equals(dt.getString(i, "ParentCode"))
					&& !dt.getString(i, "CodeType").equals(dt.getString(i, "CodeType_Key"))) {
				qb = new QueryBuilder("update ZDCode set Codetype=?,ParentCode =? where CodeType=? and ParentCode=?");
				qb.add(dt.getString(i, "CodeType"));
				qb.add(dt.getString(i, "CodeType"));
				qb.add(dt.getString(i, "CodeType_Key"));
				qb.add(dt.getString(i, "CodeType_Key"));
				trans.add(qb);
			}
		}
		if (trans.commit()) {
			for (int i = 0; i < dt.getRowCount(); i++) {
				CacheManager.set("Code", dt.getString(i, "CodeType"), dt.getString(i, "CodeValue"), dt.getString(i,
						"CodeName"));
			}
			Response.setLogInfo(1, "�޸ĳɹ�!");
		} else {
			Response.setLogInfo(0, "�޸�ʧ��!");
		}
	}

	public static Mapx init(Mapx params) {
		return params;
	}

	public static Mapx initList(Mapx params) {
		String codeType = params.getString("CodeType");
		ZDCodeSchema code = new ZDCodeSchema();
		code.setCodeType(codeType);
		code.setParentCode("System");
		code.setCodeValue("System");
		code.fill();
		return code.toMapx();
	}

	public void add() {
		ZDCodeSchema code = new ZDCodeSchema();
		code.setValue(Request);
		if (code.fill()) {
			Response.setLogInfo(0, "����ֵ" + code.getCodeValue() + "�Ѿ�������!");
			return;
		}
		code.setCodeOrder(System.currentTimeMillis());
		code.setAddTime(new Date());
		code.setAddUser(User.getUserName());
		if (code.insert()) {
			CacheManager.set("Code", code.getCodeType(), code.getCodeValue(), code.getCodeName());
			Response.setLogInfo(1, "�½�����ɹ�!");
		} else {
			Response.setLogInfo(0, "�½�����ʧ��!");
		}
	}

	public void del() {
		DataTable dt = (DataTable) Request.get("DT");
		ZDCodeSet set = new ZDCodeSet();
		for (int i = 0; i < dt.getRowCount(); i++) {
			ZDCodeSchema code = new ZDCodeSchema();
			code.setValue(dt.getDataRow(i));
			code.fill();
			set.add(code);
			if ("System".equals(code.getParentCode())) {
				ZDCodeSchema childCode = new ZDCodeSchema();
				childCode.setParentCode(code.getCodeType());
				set.add(childCode.query());
			}
		}
		// ��־
		StringBuffer codeLog = new StringBuffer("ɾ������:");
		if (set.size() > 0) {
			codeLog.append(set.get(0).getCodeName() + " ��");
		}
		if (set.deleteAndBackup()) {
			UserLog.log(UserLog.SYSTEM, UserLog.SYSTEM_DELCODE, codeLog + "�ɹ�", Request.getClientIP());
			for (int i = 0; i < set.size(); i++) {
				CacheManager.remove("Code", set.get(i).getCodeType(), set.get(i).getCodeValue());
				if ("System".equals(set.get(i).getParentCode())) {
					CacheManager.removeType("Code", set.get(i).getCodeType());
				}
			}
			Response.setLogInfo(1, "ɾ������ɹ�!");
		} else {
			UserLog.log(UserLog.SYSTEM, UserLog.SYSTEM_DELCODE, codeLog + "ʧ��", Request.getClientIP());
			Response.setLogInfo(0, "ɾ������ʧ��!");
		}
	}

	public void sortColumn() {
		String target = $V("Target");
		String orders = $V("Orders");
		String type = $V("Type");
		String parentCode = $V("ParentCode");
		if (!StringUtil.checkID(target) && !StringUtil.checkID(orders)) {
			return;
		}
		if (OrderUtil.updateOrder("ZDCode", "CodeOrder", type, target, orders, " ParentCode = '" + parentCode + "'")) {
			Response.setMessage("����ɹ�");
		} else {
			Response.setError("����ʧ��");
		}
	}
}
