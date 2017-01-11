package com.nswt.shop;

import java.util.Date;

import com.nswt.cms.pub.SiteUtil;
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
import com.nswt.schema.ZSSendSchema;
import com.nswt.schema.ZSSendSet;

/**
 * @Author wyli
 * @Date 2016-01-20
 * @Mail lwy@nswt.com
 */

public class SendType extends Page {
	public static void dg1DataBind(DataGridAction dga) {
		String sql = "select * from ZSSend where siteid = ?";
		QueryBuilder qb = new QueryBuilder(sql, Application.getCurrentSiteID());
		dga.bindData(qb);
	}

	public void add() {
		ZSSendSchema send = new ZSSendSchema();
		send.setValue(Request);
		send.setID(NoUtil.getMaxNo("SendID"));
		send.setSiteID(Application.getCurrentSiteID());
		send.setAddUser(User.getUserName());
		send.setAddTime(new Date());

		String ImageID = $V("ImageID");
		if (StringUtil.isNotEmpty(ImageID)) {
			DataTable imageDT = new QueryBuilder("select path,srcfilename from zcimage where id = ? ", ImageID)
					.executeDataTable();
			send.setProp1((imageDT.get(0, "path").toString() + imageDT.get(0, "srcfilename")).replaceAll("//", "/")
					.toString());
		} else {
			send.setProp1("upload/Image/nopicture.jpg");
		}

		if (send.insert()) {
			Response.setStatus(1);
			Response.setMessage("����������ɹ���");
		} else {
			Response.setStatus(0);
			Response.setMessage("��������!");
		}
	}

	public void dg1Edit() {
		ZSSendSchema send = new ZSSendSchema();
		String ID = $V("ID");
		send.setID(ID);
		send.fill();
		send.setValue(Request);
		send.setModifyTime(new Date());
		send.setModifyUser(User.getUserName());

		String ImageID = $V("ImageID");
		String imagePath = $V("PicSrc1");
		if (StringUtil.isNotEmpty(ImageID)) {
			DataTable imageDT = new QueryBuilder("select path,srcfilename from zcimage where id = ? ", ImageID)
					.executeDataTable();
			String path = (imageDT.get(0, "path").toString() + imageDT.get(0, "srcfilename")).replaceAll("//", "/")
					.toString();
			send.setProp1(path);
		} else {
			send.setProp1(imagePath);
		}

		if (send.update()) {
			Response.setLogInfo(1, "�޸ĳɹ�");
		} else {
			Response.setLogInfo(0, "����ʧ��");
		}
	}

	public void del() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("����IDʱ��������!");
			return;
		}
		ids = ids.replaceAll(",", "','");
		Transaction trans = new Transaction();
		ZSSendSchema send = new ZSSendSchema();
		ZSSendSet set = send.query(new QueryBuilder("where ID in ('" + ids + "')"));
		trans.add(set, Transaction.DELETE);
		if (trans.commit()) {
			Response.setStatus(1);
			Response.setMessage("ɾ���ɹ���");
		} else {
			Response.setStatus(0);
			Response.setMessage("�������ݿ�ʱ��������!");
		}
	}

	public static Mapx initEditDialog(Mapx params) {
		String ID = params.getString("ID");
		ZSSendSchema send = new ZSSendSchema();
		if (StringUtil.isNotEmpty(ID)) {
			send.setID(ID);
			send.fill();
			Mapx map = send.toMapx();
			map.put("PicSrc1", send.getProp1());
			map.put("Prop1", (Config.getContextPath() + Config.getValue("UploadDir") + "/"
					+ SiteUtil.getAlias(Application.getCurrentSiteID()) + "/" + send.getProp1()).replaceAll("//", "/"));
			return map;
		} else {
			params.put("Prop1", (Config.getContextPath() + Config.getValue("UploadDir") + "/"
					+ SiteUtil.getAlias(Application.getCurrentSiteID()) + "/" + "upload/Image/nopicture.jpg")
					.replaceAll("//", "/"));
			return params;
		}

	}
}
