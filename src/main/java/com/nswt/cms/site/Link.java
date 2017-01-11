package com.nswt.cms.site;

import java.util.Date;

import com.nswt.cms.pub.SiteUtil;
import com.nswt.cms.template.PageGenerator;
import com.nswt.framework.Config;
import com.nswt.framework.Page;
import com.nswt.framework.User;
import com.nswt.framework.controls.DataGridAction;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.Application;
import com.nswt.platform.pub.NoUtil;
import com.nswt.platform.pub.OrderUtil;
import com.nswt.schema.ZCLinkSchema;
import com.nswt.schema.ZCLinkSet;
import com.nswt.schema.ZCPageBlockSchema;
import com.nswt.schema.ZCPageBlockSet;

/**
 * ���ӷ������
 * 
 * @author huanglei
 * @mail huanglei@nswt.com
 * @date 2007-8-24
 */

public class Link extends Page {

	public static void dg1DataBind(DataGridAction dga) {
		String groupID = dga.getParam("LinkGroupID");
		String sql = "select ZCLink.*,(select name from zclinkgroup where id=zclink.linkgroupID) as LinkGroupName from ZCLink ";
		QueryBuilder qb = new QueryBuilder(sql);
		if (StringUtil.isNotEmpty(groupID)) {
			qb.append(" where LinkGroupID=?", Long.parseLong(groupID));
		} else {
			qb.append(" where exists (select '' from ZCLinkGroup where ID=ZCLink.LinkGroupID and SiteID=?)");
			qb.add(Application.getCurrentSiteID());
		}
		qb.append(" order by OrderFlag desc,id desc");
		dga.bindData(qb);
	}

	public static Mapx initDialog(Mapx params) {
		String ID = params.getString("ID");
		String type = params.getString("Type");
		if (StringUtil.isNotEmpty(ID)) {
			ZCLinkSchema link = new ZCLinkSchema();
			link.setID(ID);
			link.fill();
			Mapx map = link.toMapx();
			map.put("Type", type);
			map.put("ImageSrc", (Config.getContextPath() + Config.getValue("UploadDir") + "/"
					+ SiteUtil.getAlias(Application.getCurrentSiteID()) + "/" + link.getImagePath()).replaceAll("//",
					"/"));
			return map;
		} else {
			params.put("LinkGroupID", params.getString("LinkGroupID"));
			params.put("Type", type);
			params.put("URL", "http://");
			params.put("ImageSrc", (Config.getContextPath() + Config.getValue("UploadDir") + "/"
					+ SiteUtil.getAlias(Application.getCurrentSiteID()) + "/upload/Image/nopicture.jpg").replaceAll(
					"//", "/"));
			return params;
		}
	}

	public void save() {
		DataTable dt = (DataTable) Request.get("DT");
		Transaction trans = new Transaction();
		for (int i = 0; i < dt.getRowCount(); i++) {
			QueryBuilder qb = new QueryBuilder("update ZCLink set Name=?,URL=?,ModifyUser=?,ModifyTime=? where ID=?");
			qb.add(dt.getString(i, "Name"));
			qb.add(dt.getString(i, "URL"));
			qb.add(User.getUserName());
			qb.add(new Date());
			qb.add(Long.parseLong(dt.getString(i, "ID")));
			trans.add(qb);
		}
		if (trans.commit()) {
			updateStatLink();
			Response.setLogInfo(1, "�޸ĳɹ�!");
		} else {
			Response.setLogInfo(0, "�޸�ʧ��!");
		}
	}

	public void edit() {
		Transaction trans = new Transaction();
		String ID = $V("ID");
		if (StringUtil.isEmpty(ID) || !StringUtil.isDigit(ID)) {
			Response.setLogInfo(0, "����ID����");
			return;
		}

		ZCLinkSchema link = new ZCLinkSchema();
		link.setID(ID);
		link.fill();
		link.setValue(Request);
		String ImageID = $V("ImageID");
		if (StringUtil.isNotEmpty(ImageID)) {
			DataTable dt = new QueryBuilder("select path,srcfilename from zcimage where id=?", ImageID)
					.executeDataTable();
			link.setImagePath((dt.get(0, "path").toString() + dt.get(0, "srcfilename")).replaceAll("//", "/")
					.toString());
		}
		link.setModifyUser(User.getUserName());
		link.setModifyTime(new Date());
		trans.add(link, Transaction.UPDATE);
		if (trans.commit()) {
			updateStatLink();
			Response.setLogInfo(1, "�޸ĳɹ�!");
		} else {
			Response.setLogInfo(0, "�޸�ʧ��!");
		}
	}

	public void add() {
		ZCLinkSchema link = new ZCLinkSchema();
		link.setID(NoUtil.getMaxID("LinkID"));
		link.setValue(Request);
		String ImageID = $V("ImageID");
		if (StringUtil.isNotEmpty(ImageID)) {
			DataTable dt = new QueryBuilder("select path,srcfilename from zcimage where id=?", Long.parseLong(ImageID)).executeDataTable();
			link.setImagePath((dt.get(0, "path").toString() + dt.get(0, "srcfilename")).replaceAll("//", "/").toString());
		} else {
			link.setImagePath("upload/Image/nopicture.jpg");
		}
		link.setOrderFlag(OrderUtil.getDefaultOrder());
		link.setSiteID(Application.getCurrentSiteID());
		link.setAddUser(User.getUserName());
		link.setAddTime(new Date());
		if (link.insert()) {
			Response.setLogInfo(1, "�����ɹ�!");
		} else {
			Response.setLogInfo(0, "����ʧ��!");
		}
	}

	public void del() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setLogInfo(0, "����IDʱ��������");
			return;
		}
		Transaction trans = new Transaction();
		ZCLinkSchema link = new ZCLinkSchema();
		ZCLinkSet set = link.query(new QueryBuilder("where id in (" + ids + ")"));
		trans.add(set, Transaction.DELETE);

		if (trans.commit()) {
			Response.setLogInfo(1, "ɾ���ɹ���");
		} else {
			Response.setLogInfo(0, "ɾ������ʱ��������!");
		}
	}

	public void getPicSrc() {
		String ID = $V("PicID");
		DataTable dt = new QueryBuilder("select path,srcfilename from zcimage where id=?", Long.parseLong(ID)).executeDataTable();
		if (dt.getRowCount() > 0) {
			this.Response.put("picSrc", (Config.getContextPath() + Config.getValue("UploadDir") + "/"
					+ SiteUtil.getAlias(Application.getCurrentSiteID()) + "/" + dt.get(0, "path").toString() + dt.get(
					0, "srcfilename")).replaceAll("//", "/").toString());
		}
	}

	public void sortColumn() {
		String target = $V("Target");
		String orders = $V("Orders");
		String type = $V("Type");
		String linkGroupID = $V("LinkGroupID");
		if (!StringUtil.checkID(target) || !StringUtil.checkID(orders)) {
			return;
		}
		if (OrderUtil.updateOrder("ZCLink", type, target, orders, " LinkGroupID = " + linkGroupID)) {
			Response.setMessage("����ɹ�");
		} else {
			Response.setError("����ʧ��");
		}
	}

	public static void updateStatLink() {
		ZCPageBlockSet set = new ZCPageBlockSchema().query(new QueryBuilder(
				" where SiteID = ? and code like '%friendlink%'", Application.getCurrentSiteID()));
		PageGenerator p = new PageGenerator();
		p.staticPageBlock(set);
	}

}
