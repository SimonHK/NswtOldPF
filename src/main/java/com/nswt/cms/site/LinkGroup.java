package com.nswt.cms.site;

import java.util.ArrayList;
import java.util.Date;

import com.nswt.framework.Page;
import com.nswt.framework.User;
import com.nswt.framework.controls.DataGridAction;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.HtmlUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.Application;
import com.nswt.platform.pub.NoUtil;
import com.nswt.platform.pub.OrderUtil;
import com.nswt.schema.ZCLinkGroupSchema;
import com.nswt.schema.ZCLinkGroupSet;
import com.nswt.schema.ZCLinkSchema;
import com.nswt.schema.ZCLinkSet;

/**
 * 链接分组管理-------新宇联安软件平台(nswtp)样板程序
 * 
 * @author huanglei
 * @mail huanglei@nswt.com
 * @date 2007-8-24
 */

public class LinkGroup extends Page {

	// 链接分类类型
	public static final String TYPE_TEXT = "text";

	public static final String TYPE_IMAGE = "image";

	public final static Mapx TYPE_MAP = new Mapx();

	static {
		TYPE_MAP.put(TYPE_TEXT, "文字链接");
		TYPE_MAP.put(TYPE_IMAGE, "图片链接");
	}

	public static void dg1DataBind(DataGridAction dga) {
		QueryBuilder qb = new QueryBuilder("select * from ZCLinkGroup where SiteID=? order by OrderFlag asc,id desc",
				Application.getCurrentSiteID());
		DataTable dt = qb.executeDataTable();
		dt.decodeColumn("Type", TYPE_MAP);
		dga.bindData(dt);
	}

	public static Mapx initDialog(Mapx params) {
		params.put("Type", HtmlUtil.mapxToRadios("Type", TYPE_MAP, TYPE_TEXT));
		return params;
	}

	public void save() {
		DataTable dt = (DataTable) Request.get("DT");
		Transaction trans = new Transaction();
		String logMessage = "保存成功！";
		ArrayList list = new ArrayList();
		for (int i = 0; i < dt.getRowCount(); i++) {
			DataTable checkdt = new QueryBuilder("select * from zclinkgroup where name=?", dt.getString(i, "Name"))
					.executeDataTable();
			if (checkdt.getRowCount() > 0) {
				list.add(dt.getString(i, "Name"));
				continue;
			}
			QueryBuilder qb = new QueryBuilder("update ZCLinkGroup set Name=?,ModifyUser=?,ModifyTime=? where ID=?");
			qb.add(dt.getString(i, "Name"));
			qb.add(User.getUserName());
			qb.add(new Date());
			qb.add(Long.parseLong(dt.getString(i, "ID")));
			trans.add(qb);
		}
		if (list.size() > 0) {
			logMessage += "链接分类名称";
			logMessage += StringUtil.join(list.toArray(), "、");
			logMessage += "已存在,请更换...";
		}
		if (trans.commit()) {
			Response.setLogInfo(1, logMessage);
		} else {
			Response.setLogInfo(0, "保存失败！");
		}
	}

	public void add() {
		String name = $V("Name");
		DataTable dt = new QueryBuilder("select * from zclinkgroup where name=? and SiteID = ?", name,Application.getCurrentSiteID()).executeDataTable();
		if (dt.getRowCount() > 0) {
			Response.setLogInfo(0, "该链接分类名称已存在，请更换...");
			return;
		}
		ZCLinkGroupSchema linkGroup = new ZCLinkGroupSchema();
		linkGroup.setValue(Request);
		linkGroup.setID(NoUtil.getMaxID("LinkGroupID"));
		linkGroup.setOrderFlag(OrderUtil.getDefaultOrder());
		linkGroup.setSiteID(Application.getCurrentSiteID());
		linkGroup.setAddTime(new Date());
		linkGroup.setAddUser(User.getUserName());
		if (linkGroup.insert()) {
			Response.setLogInfo(1, "新增成功");
		} else {
			Response.setLogInfo(0, "新增" + linkGroup.getName() + "失败!");
		}
	}

	public void del() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setLogInfo(0, "传入ID时发生错误!");
			return;
		}
		Transaction trans = new Transaction();
		ZCLinkGroupSchema linkGroup = new ZCLinkGroupSchema();
		ZCLinkGroupSet set = linkGroup.query(new QueryBuilder("where id in (" + ids + ")"));
		trans.add(set, Transaction.DELETE);

		// 删除分组下的所有链接
		for (int i = 0; i < set.size(); i++) {
			linkGroup = set.get(i);
			ZCLinkSchema link = new ZCLinkSchema();
			link.setLinkGroupID(linkGroup.getID());
			ZCLinkSet LinkSet = link.query();
			trans.add(LinkSet, Transaction.DELETE);
		}
		if (trans.commit()) {
			Response.setLogInfo(1, "删除成功");
		} else {
			Response.setLogInfo(0, "删除失败");
		}
	}
}
