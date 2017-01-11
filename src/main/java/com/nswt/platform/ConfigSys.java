package com.nswt.platform;

import java.util.Date;

import com.nswt.framework.Config;
import com.nswt.framework.Page;
import com.nswt.framework.User;
import com.nswt.framework.controls.DataGridAction;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.StringUtil;
import com.nswt.schema.ZDConfigSchema;
import com.nswt.schema.ZDConfigSet;

/**
 * @Author �º�ǿ
 * @Date 2007-8-6
 * @Mail chq@nswt.com
 */

public class ConfigSys extends Page {
	public static void dg1DataBind(DataGridAction dga) {
		String SearchType = (String) dga.getParams().get("SearchType");
		QueryBuilder qb = new QueryBuilder(
				"select type,name,value,type as type_key from zdconfig where type not like ? ");
		qb.add("System.%");// ����ʾSystem.��ͷ��������
		if (StringUtil.isNotEmpty(SearchType)) {
			qb.append(" and (type like ? or name like ?)");
			qb.add("%" + SearchType + "%");
			qb.add("%" + SearchType + "%");
		}
		qb.append("order by type");
		dga.bindData(qb);
	}

	public void add() {
		ZDConfigSchema zdconfig = new ZDConfigSchema();
		zdconfig.setValue(Request);
		zdconfig.setAddTime(new Date());
		zdconfig.setAddUser(User.getUserName());
		if (zdconfig.getType().startsWith("System.")) {
			Response.setStatus(0);
			Response.setMessage("����������ԡ�System.����ͷ�������");
			return;
		}
		if (zdconfig.insert()) {
			Config.update();
			Response.setStatus(1);
			Response.setMessage("�������ɹ���");
		} else {
			Response.setStatus(0);
			Response.setMessage("��������!");
		}
	}

	public void del() {
		String ids = $V("IDs");
		ids = ids.replaceAll(",", "','");
		Transaction trans = new Transaction();
		ZDConfigSchema zdconfig = new ZDConfigSchema();
		ZDConfigSet set = zdconfig.query(new QueryBuilder("where type in ('" + ids + "')"));
		trans.add(set, Transaction.DELETE_AND_BACKUP);
		// ��־
		StringBuffer configLog = new StringBuffer("ɾ��������:");
		if (set.size() > 0) {
			configLog.append(set.get(0).getName() + " ��");
		}
		if (trans.commit()) {
			Config.update();
			UserLog.log(UserLog.SYSTEM, UserLog.SYSTEM_DELCONFIG, configLog + "�ɹ�", Request.getClientIP());
			Response.setStatus(1);
			Response.setMessage("ɾ���ɹ���");
		} else {
			UserLog.log(UserLog.SYSTEM, UserLog.SYSTEM_DELCONFIG, configLog + "ʧ��", Request.getClientIP());
			Response.setStatus(0);
			Response.setMessage("�������ݿ�ʱ��������!");
		}
	}

	public void dg1Edit() {
		DataTable dt = (DataTable) Request.get("DT");
		Transaction trans = new Transaction();
		for (int i = 0; i < dt.getRowCount(); i++) {
			QueryBuilder qb = new QueryBuilder("update ZDConfig set type=?,name=?,value=? where type=?");
			qb.add(dt.getString(i, "type"));
			qb.add(dt.getString(i, "name"));
			qb.add(dt.getString(i, "value"));
			qb.add(dt.getString(i, "type_key"));
			trans.add(qb);
		}
		if (trans.commit()) {
			Config.update();
			Response.setStatus(1);
			Response.setMessage("�޸ĳɹ�!");
		} else {
			Response.setStatus(0);
			Response.setMessage("�޸�ʧ��!");
		}
	}
}
