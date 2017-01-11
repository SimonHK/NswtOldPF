package com.nswt.cms.stat.report;

import java.util.Date;

import com.nswt.framework.Page;
import com.nswt.framework.controls.DataGridAction;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.utility.DateUtil;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.Application;

/**
 * @Author NSWT
 * @Date 2016-4-27
 * @Mail nswt@nswt.com.cn
 */
public class ClientReport extends Page {

	public void getChartData() {
		String type = $V("Type");
		Date start = DateUtil.parse("1970-01-01");
		Date end = new Date();
		DataTable dt = getClientData(Application.getCurrentSiteID(), type, start, end);
		String xml = ChartUtil.getPie3DChart(dt, 8, 1);
		$S("Data", xml);
	}

	public static void dgOSDataBind(DataGridAction dga) {
		dga.bindData(getDataGridData(Application.getCurrentSiteID(), "OS"));
	}

	public static void dgBrowserDataBind(DataGridAction dga) {
		dga.bindData(getDataGridData(Application.getCurrentSiteID(), "Browser"));
	}

	public static void dgLanguageDataBind(DataGridAction dga) {
		dga.bindData(getDataGridData(Application.getCurrentSiteID(), "Language"));
	}

	public static void dgColorDepthDataBind(DataGridAction dga) {
		dga.bindData(getDataGridData(Application.getCurrentSiteID(), "ColorDepth"));
	}

	public static void dgScreenDataBind(DataGridAction dga) {
		dga.bindData(getDataGridData(Application.getCurrentSiteID(), "Screen"));
	}

	public static void dgFlashVersionDataBind(DataGridAction dga) {
		dga.bindData(getDataGridData(Application.getCurrentSiteID(), "FlashVersion"));
	}

	public static void dgJavaEnabledDataBind(DataGridAction dga) {
		dga.bindData(getDataGridData(Application.getCurrentSiteID(), "JavaEnabled"));
	}

	public static void dgCookieEnabledDataBind(DataGridAction dga) {
		dga.bindData(getDataGridData(Application.getCurrentSiteID(), "CookieEnabled"));
	}

	private static DataTable getDataGridData(long siteID, String subtype) {
		Date start = DateUtil.parse("1970-01-01");
		Date end = new Date();
		DataTable dt = getClientData(Application.getCurrentSiteID(), subtype, start, end);
		ReportUtil.addTrend(dt, "Client", subtype);
		ReportUtil.computeRate(dt, subtype, "Rate");
		dt.sort("Rate");
		ReportUtil.addSuffix(dt, "Rate", "%");
		ReportUtil.addTotal(dt, new String[] { subtype });
		dt.set(0, "Rate", "100.00%");
		return dt;
	}

	private static DataTable getClientData(long siteID, String subtype, Date start, Date end) {
		if (!StringUtil.verify(subtype, "String")) {
			return null;
		}
		String period1 = DateUtil.toString(start, "yyyyMM");
		String period2 = DateUtil.toString(end, "yyyyMM");
		QueryBuilder qb = new QueryBuilder(
				"select * from ZCStatItem where SiteID=? and Type='Client' and SubType=? and Period>=? and Period<=? order by Period desc");
		qb.add(siteID);
		qb.add(subtype);
		qb.add(period1);
		qb.add(period2);
		DataTable dt = qb.executeDataTable();
		dt = ReportUtil.toItemTable(dt, start, end, true);
		if (subtype.equals("JavaEnabled")) {
			for (int i = 0; i < dt.getRowCount(); i++) {
				if (dt.getString(i, "Item").equals("true")) {
					dt.set(i, "Item", "֧��Applet");
				} else {
					dt.set(i, "Item", "��֧��Applet");
				}
			}
		}
		if (subtype.equals("CookieEnabled")) {
			for (int i = 0; i < dt.getRowCount(); i++) {
				if (dt.getString(i, "Item").equals("true")) {
					dt.set(i, "Item", "����Cookie");
				} else {
					dt.set(i, "Item", "������Cookie");
				}
			}
		}
		if (subtype.equals("Language") && dt.getRowCount() > 8) {// С����Ҫ�ϲ�
			ReportUtil.prepareForPie3D(dt, 8);
		}
		return dt;
	}
}
