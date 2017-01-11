package com.nswt.cms.dataservice;

import com.nswt.framework.Page;
import com.nswt.framework.controls.DataGridAction;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.HtmlUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.schema.ZDMemberCompanyInfoSchema;
import com.nswt.schema.ZDMemberPersonInfoSchema;
import com.nswt.schema.ZDMemberSchema;
import com.nswt.platform.Application;

public class Member extends Page {
	public static void dg1DataBind(DataGridAction dga) {
		String SearchUserName = dga.getParams().getString("SearchUserName");
		String Status = dga.getParam("Status");
		QueryBuilder qb = new QueryBuilder("select * from ZDMember where SiteID=?", Application.getCurrentSiteID());
		if (StringUtil.isNotEmpty(SearchUserName)) {
			qb.append(" and UserName like ?", "%" + SearchUserName.trim() + "%");
		}
		if (StringUtil.isNotEmpty(Status)) {
			qb.append(" and Status = ?", Status);
		}
		qb.append(" order by RegTime desc");
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dt.decodeColumn("Gender", HtmlUtil.codeToMapx("Gender"));
		dt.decodeColumn("Status", HtmlUtil.codeToMapx("Member.Status"));
		DataTable dc = new QueryBuilder("select Name,ID from ZDMemberLevel Order by ID").executeDataTable();
		dt.decodeColumn("MemberLevel", dc.toMapx("ID", "Name"));
		dt.decodeColumn("Type", HtmlUtil.codeToMapx("Member.Type"));
		dga.bindData(dt);
	}

	public void doCheck() {
		String UserNames = $V("UserNames");
		String Status = $V("Status");
		if (StringUtil.isNotEmpty(UserNames) && StringUtil.isNotEmpty(Status)) {
			String[] names = UserNames.split(",");
			Transaction trans = new Transaction();
			ZDMemberSchema member = new ZDMemberSchema();
			for (int i = 0; i < names.length; i++) {
				member = new ZDMemberSchema();
				member.setUserName(names[i]);
				member.fill();
				member.setStatus(Status);
				trans.add(member, Transaction.UPDATE);
			}
			if (trans.commit()) {
				Response.setLogInfo(1, "审核成功");
			} else {
				Response.setLogInfo(0, "审核失败");
			}
		}
	}

	public void checkName() {
		String UserName = Request.getString("UserName");
		DataTable dt = new QueryBuilder("select * from ZDMember where UserName=?", UserName).executeDataTable();
		if (dt.getRowCount() > 0) {
			Response.setStatus(0);
		} else {
			Response.setStatus(1);
		}
	}

	public void add() {
		ZDMemberSchema member = new ZDMemberSchema();
		String Password = $V("Password");
		String ConfirmPassword = $V("ConfirmPassword");
		String UserName = $V("UserName");
		if (Password.length() == 0) {
			Response.setLogInfo(0, "密码不能为空");
			return;
		}

		if (!(Password.equals(ConfirmPassword))) {
			Response.setLogInfo(0, "密码不一致");
			return;
		}
		if (member.query(new QueryBuilder("where UserName=?", UserName)).size() > 0) {
			Response.setLogInfo(0, "登录名已经存在，请更换登录名");
			return;
		} else {
			member = MemberField.setPropValues(member, Request,Application.getCurrentSiteID());
			member.setValue(Request);
			member.setUserName(UserName);
			member.setSiteID(Application.getCurrentSiteID());
			member.setPassword(StringUtil.md5Hex($V("Password")));
		}
		if(StringUtil.isEmpty(member.getName())){
			member.setName("注册用户");
		}
		Transaction trans = new Transaction();
		if (member.getType().equalsIgnoreCase("Person")) {
			ZDMemberPersonInfoSchema person = new ZDMemberPersonInfoSchema();
			person.setUserName(member.getUserName());
			person.setNickName(member.getName());
			trans.add(person, Transaction.INSERT);
		} else {
			ZDMemberCompanyInfoSchema company = new ZDMemberCompanyInfoSchema();
			company.setUserName(member.getUserName());
			company.setCompanyName(member.getName());
			company.setEmail(member.getEmail());
			trans.add(company, Transaction.INSERT);
		}
		trans.add(member, Transaction.INSERT);
		if (trans.commit()) {
			Response.setLogInfo(1, "新增成功");
		} else {
			Response.setLogInfo(0, "新增" + member.getUserName() + "失败!");
		}
	}

	public void dg1Edit() {
		String newPassword = $V("NewPassword");
		String ConfirmPassword = $V("ConfirmPassword");
		if (!(newPassword.equals("******") && ConfirmPassword.equals("******"))) {
			if (!(newPassword.equals(ConfirmPassword))) {
				Response.setLogInfo(0, "密码不一致");
				return;
			}
		}
		ZDMemberSchema member = new ZDMemberSchema();
		member.setValue(Request);
		member.fill();
		member = MemberField.setPropValues(member, Request,Application.getCurrentSiteID());
		member.setValue(Request);
		if (StringUtil.isNotEmpty(newPassword) && !newPassword.equals("******")) {
			member.setPassword(StringUtil.md5Hex(newPassword));
		}
		if (member.update()) {
			Response.setLogInfo(1, "修改成功");
		} else {
			Response.setLogInfo(0, "修改" + member.getUserName() + "失败!");
		}
	}

	public static Mapx initAddDialog(Mapx params) {
		params.put("Gender", HtmlUtil.codeToRadios("Gender", "Gender", "M"));
		params.put("Status", HtmlUtil.codeToOptions("Member.Status"));
		DataTable dt = new QueryBuilder("select Name,ID from ZDMemberLevel Order by ID").executeDataTable();
		params.put("MemberLevel", HtmlUtil.dataTableToOptions(dt));
		params.put("Type", HtmlUtil.codeToOptions("Member.Type"));
		params.put("Columns",MemberField.getColumns(Application.getCurrentSiteID()));
		return params;
	}

	public static Mapx initDialog(Mapx params) {
		String UserName = params.getString("UserName");
		if (StringUtil.isNotEmpty(UserName)) {
			ZDMemberSchema member = new ZDMemberSchema();
			member.setUserName(UserName);
			member.fill();
			Mapx map = member.toMapx();
			map.put("Gender", HtmlUtil.codeToRadios("Gender", "Gender", member.getGender()));
			map.put("Status", HtmlUtil.codeToOptions("Member.Status", member.getStatus()));
			DataTable dt = new QueryBuilder("select Name,ID from ZDMemberLevel Order by ID").executeDataTable();
			map.put("MemberLevel", HtmlUtil.dataTableToOptions(dt, map.getString("Level")));
			map.put("Type", HtmlUtil.codeToOptions("Member.Type", member.getType()));
			map.put("Columns",MemberField.getColumnAndValue(member));
			return map;
		} else {
			return params;
		}
	}

	public void del() {
		String UserNames = $V("UserNames");
		String[] names = UserNames.split(",");
		Transaction trans = new Transaction();
		ZDMemberSchema member = new ZDMemberSchema();
		ZDMemberPersonInfoSchema person = new ZDMemberPersonInfoSchema();
		ZDMemberCompanyInfoSchema company = new ZDMemberCompanyInfoSchema();
		for (int i = 0; i < names.length; i++) {
			trans.add(member.query(new QueryBuilder(" where UserName = ?", names[i])), Transaction.DELETE_AND_BACKUP);
			trans.add(person.query(new QueryBuilder(" where UserName = ?", names[i])), Transaction.DELETE_AND_BACKUP);
			trans.add(company.query(new QueryBuilder(" where UserName = ?", names[i])), Transaction.DELETE_AND_BACKUP);
		}
		if (trans.commit()) {
			Response.setLogInfo(1, "删除成功");
		} else {
			Response.setLogInfo(0, "删除失败");
		}
	}

}
