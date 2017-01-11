package com.nswt.shop.web;

import com.nswt.cms.pub.SiteUtil;
import com.nswt.framework.Ajax;
import com.nswt.framework.Config;
import com.nswt.framework.User;
import com.nswt.framework.controls.DataGridAction;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.Application;
import com.nswt.schema.ZSFavoriteSchema;
import com.nswt.schema.ZSFavoriteSet;

/**
 * @Author ��ΰ��
 * @Date 2016-1-26
 * @Mail lwy@nswt.com
 */

public class MemberFavorite extends Ajax {

	public static void dg1DataBind(DataGridAction dga) {

		if (dga.getTotal() == 0) {
			String sql2 = "select count(*) from ZSGoods a, ZSFavorite b where a.SiteID = ? and a.ID = b.GoodsID and b.UserName = ?";
			QueryBuilder qb = new QueryBuilder(sql2);
			qb.add(Application.getCurrentSiteID());
			qb.add(User.getUserName()); // ǰ̨Ӧ����User.getValue("Name");��ȡ��Ա����
			dga.setTotal(qb.executeInt());
		}

		String sql1 = "select a.ID, a.Name, a.Image0, a.Price, b.AddTime, b.PriceNoteFlag from ZSGoods a, ZSFavorite b where a.SiteID = ? and a.ID = b.GoodsID and b.UserName = ? order by AddTime";
		QueryBuilder qb = new QueryBuilder(sql1);
		qb.add(dga.getParam("SiteID"));
		qb.add(User.getUserName()); // ǰ̨Ӧ����User.getValue("Name");��ȡ��Ա����
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dt.insertColumn("Image");
		for (int i = 0; i < dt.getRowCount(); i++) {
			dt.set(i,
					"Image",
					(Config.getContextPath() + Config.getValue("UploadDir") + "/"
							+ SiteUtil.getAlias(dga.getParam("SiteID")) + "/" + dt.getString(i, "Image0")).replaceAll(
							"//", "/"));
		}

		dga.bindData(dt);
	}

	/**
	 * ��Ʒ��������
	 */
	public void priceNote() {
		String ids = $V("IDs");
		String siteID = $V("SiteID");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("����IDʱ��������!");
			return;
		}
		Transaction trans = new Transaction();
		ZSFavoriteSchema favorite = new ZSFavoriteSchema();
		ZSFavoriteSet set = favorite.query(new QueryBuilder(" where SiteID=? and GoodsID in (" + ids
				+ ") and UserName = '" + User.getValue("UserName") + "'", siteID));
		for (int i = 0; i < set.size(); i++) {
			set.get(i).setPriceNoteFlag("Y");
		}
		trans.add(set, Transaction.UPDATE);

		if (trans.commit()) {
			Response.setStatus(1);
			Response.setMessage("�����������óɹ�!");
		} else {
			Response.setStatus(0);
			Response.setMessage("�Բ��𣬷�������!������ϵ�ͷ�!");
		}
	}

	/**
	 * ���ղؼ��Ƴ�ָ����Ʒ
	 */
	public void removeFromFavorite() {
		String ids = $V("IDs");
		String siteID = $V("SiteID");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("����IDʱ��������!");
			return;
		}
		Transaction trans = new Transaction();
		ZSFavoriteSchema favorite = new ZSFavoriteSchema();
		// ǰ̨Ӧ����User.getValue("Name");��ȡ��Ա����
		ZSFavoriteSet set = favorite.query(new QueryBuilder(" where SiteID=? and GoodsID in (" + ids
				+ ") and UserName = '" + User.getUserName() + "'", siteID));
		trans.add(set, Transaction.DELETE);

		if (trans.commit()) {
			Response.setStatus(1);
			Response.setMessage("�Ƴ���Ʒ�ɹ�!");
		} else {
			Response.setStatus(0);
			Response.setMessage("�Բ��𣬷�������!������ϵ�ͷ�!");
		}
	}

	/**
	 * ����ղؼ���Ʒ�����ﳵ
	 */
	public void addToCart() {
		String GoodsID = Request.getString("GoodsID");
		String buyNowFlag = Request.getString("BuyNowFlag");
		Mapx map = new Mapx();
		String shopCartCookie = this.Cookies.getCookie("ShopCart");
		if (StringUtil.isNotEmpty(shopCartCookie)) {
			String[] goodsArray = shopCartCookie.split("X");
			for (int i = 0; i < goodsArray.length; i++) {
				map.put(goodsArray[i].split("-")[0], goodsArray[i].split("-")[1]);
			}
		}
		for (int i = 0; i < GoodsID.split("-").length; i++) {
			if (map.containsKey(GoodsID.split("-")[i])) {
				map.put(GoodsID.split("-")[i], map.getInt(GoodsID.split("-")[i]) + 1);
			} else {
				map.put(GoodsID.split("-")[i], 1);
			}
		}

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < map.keyArray().length; i++) {
			sb.append(map.keyArray()[i]);
			sb.append("-");
			sb.append(map.getString(map.keyArray()[i]));
			if (i < (map.keyArray().length - 1)) {
				sb.append("X");
			}
		}
		this.Cookies.setCookie("ShopCart", sb.toString());
		if ("1".equals(buyNowFlag)) {
			Response.setLogInfo(1, "�ɹ����빺�ﳵ!");
		} else {
			Response.setLogInfo(2, "�ɹ����빺�ﳵ!");
		}
	}
}
