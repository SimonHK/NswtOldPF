package com.nswt.cms.dataservice;

import java.util.Date;
import com.nswt.framework.Page;
import com.nswt.framework.User;
import com.nswt.framework.controls.DataGridAction;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.platform.pub.NoUtil;
import com.nswt.schema.ZDMemberLevelSchema;
import com.nswt.schema.ZDMemberLevelSet;

/**
 * @Author 谭喜才
 * @Date 2008-8-1
 * @Mail chq@nswt.com
 */

public class MemberLevel extends Page {
	public static void dg1DataBind(DataGridAction dga) {
		QueryBuilder qb = new QueryBuilder("select * from ZDMemberLevel order by TreeLevel asc");
		dga.bindData(qb);
	}

	public void add() {
		ZDMemberLevelSchema ZDmemberlevel = new ZDMemberLevelSchema();
		ZDmemberlevel.setValue(Request);
		ZDmemberlevel.setID(NoUtil.getMaxID("MemberLevelID"));
		ZDmemberlevel.setDiscount("1.0");
		ZDmemberlevel.setIsDefault("Y");
		ZDmemberlevel.setIsValidate("Y");
		ZDmemberlevel.setType("用户");
		ZDmemberlevel.setAddUser(User.getUserName());
		ZDmemberlevel.setAddTime(new Date());
		if (ZDmemberlevel.insert()) {
			Response.setStatus(1);
			Response.setMessage("新增会员项成功！");
		} else {
			Response.setStatus(0);
			Response.setMessage("发生错误!");
		}
	}

	public void del() {
		String ids = $V("IDs");
		if (ids.indexOf("\"") >= 0 || ids.indexOf("\'") >= 0) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		Transaction trans = new Transaction();
		ZDMemberLevelSchema ZDmemberlevel = new ZDMemberLevelSchema();
		ZDMemberLevelSet set = ZDmemberlevel.query(new QueryBuilder("where ID in (" + ids + ")"));
		trans.add(set, Transaction.DELETE);
		if (trans.commit()) {
			Response.setStatus(1);
			Response.setMessage("删除成功！");
		} else {
			Response.setStatus(0);
			Response.setMessage("操作数据库时发生错误!");
		}
	}

	public void save() {
		DataTable dt = (DataTable) Request.get("DT");
		Transaction trans = new Transaction();
		for (int i = 0; i < dt.getRowCount(); i++) {
			ZDMemberLevelSchema MemberLevel = new ZDMemberLevelSchema();
			MemberLevel.setValue(dt.getDataRow(i));
			MemberLevel.fill();
			MemberLevel.setValue(dt.getDataRow(i));
			trans.add(MemberLevel, Transaction.UPDATE);
		}
		if (trans.commit()) {
			Response.setLogInfo(1, "保存成功!");
		} else {
			Response.setLogInfo(0, "保存失败!");
		}
	}
}
