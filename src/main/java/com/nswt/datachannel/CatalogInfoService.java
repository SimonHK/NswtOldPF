package com.nswt.datachannel;

import com.nswt.cms.site.Catalog;
import com.nswt.framework.Ajax;
import com.nswt.framework.Framework;
import com.nswt.framework.RequestImpl;
import com.nswt.framework.User;
import com.nswt.framework.controls.DataGridAction;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.utility.Mapx;

/**
 * 向其他nswtp应用提供本应用下允许采集/分发的站点和栏目列表
 * 
 * 日期 : 2010-5-4 <br>
 * 作者: NSWT <br>
 * 邮箱：nswt@nswt.com.cn <br>
 */
public class CatalogInfoService extends Ajax {
	public void getRemoteSiteInfo() {
		String url = $V("ServerAddr");
		if (url.equalsIgnoreCase("localhost")) {
			getSiteInfo();
		} else {
			Mapx map = Framework.callRemoteMethod(url, "com.nswt.datachannel.CatalogInfoService.getSiteInfo", Request);
			if (map == null) {
				return;
			}
			Object[] ks = map.keyArray();
			for (int i = 0; i < ks.length; i++) {
				$S(ks[i].toString(), map.get(ks[i]));
			}
		}
	}

	public void getRemoteCatalogInfo() {
		String url = $V("ServerAddr");
		if (url.equalsIgnoreCase("localhost")) {
			getCatalogInfo();
		} else {
			Mapx map = Framework.callRemoteMethod(url, "com.nswt.datachannel.CatalogInfoService.getCatalogInfo",
					Request);
			if (map == null) {
				return;
			}
			Object[] ks = map.keyArray();
			for (int i = 0; i < ks.length; i++) {
				$S(ks[i].toString(), map.get(ks[i]));
			}
		}
	}

	public void getSiteInfo() {
		String type = $V("Type");
		if ("Gather".equalsIgnoreCase(type)) {
			type = "Gather";
		} else {
			type = "Deploy";
		}
		if ("admin".equalsIgnoreCase(User.getUserName()) && User.isLogin()) {
			DataTable dt = new QueryBuilder("select ID,Name from ZCSite order by orderFlag").executeDataTable();
			$S("SiteTable", dt);
		} else {
			DataTable dt = new QueryBuilder("select ID,Name from ZCSite where "
					+ "exists (select 1 from ZCCatalogConfig where SiteID=ZCSite.ID and AllowInner" + type
					+ "='Y')  order by orderFlag").executeDataTable();
			$S("SiteTable", dt);
		}
	}

	public void getCatalogInfo() {
		String type = $V("Type");
		if ("Gather".equalsIgnoreCase(type)) {
			type = "Gather";
		} else {
			type = "Deploy";
		}
		long siteID = Long.parseLong($V("SiteID"));
		DataTable dt = null;
		if ("admin".equalsIgnoreCase(User.getUserName()) && User.isLogin()) {
			dt = new QueryBuilder(
					"select ID,Name,ParentID,TreeLevel from ZCCatalog where SiteID=? and Type=?  order by orderFlag,innercode",
					siteID, Catalog.CATALOG).executeDataTable();
		} else {
			dt = new QueryBuilder("select ID,Name,ParentID,TreeLevel from ZCCatalog where SiteID=? and Type=? and "
					+ "exists (select 1 from ZCCatalogConfig where CatalogID=ZCCatalog.ID and AllowInner" + type
					+ "='Y')  order by orderFlag,innercode", siteID, Catalog.CATALOG).executeDataTable();
		}
		dt = DataGridAction.sortTreeDataTable(dt, "ID", "ParentID");
		for (int i = 0; i < dt.getRowCount(); i++) {
			String prefix = "";
			for (int j = 1; j < dt.getInt(i, "TreeLevel"); j++) {
				prefix += "　";
			}
			dt.set(i, "Name", prefix + dt.getString(i, "Name"));
		}
		$S("CatalogTable", dt);

	}

	public static DataTable getLocalSites(Mapx params) {
		String type = params.getString("Type");
		CatalogInfoService info = new CatalogInfoService();
		RequestImpl request = new RequestImpl();
		request.put("Type", type);
		info.setRequest(request);
		info.getSiteInfo();
		DataTable dt = info.getResponse().getDataTable("SiteTable");
		return dt;
	}
}
