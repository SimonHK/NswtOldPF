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
 * @Date 2016-4-28
 * @Mail nswt@nswt.com.cn
 */
public class HourReport extends Page {

	public void getChartData() {
		Date start = DateUtil.parse($V("StartDate"));
		Date end = DateUtil.parse($V("EndDate"));
		DataTable dt = getHourData(Application.getCurrentSiteID(), start, end);
		dt.deleteColumn("NewVisitor");
		dt.deleteColumn("ReturnVisitor");
		dt.deleteColumn("StickTime");
		dt.sort("Item","asc");
		String xml = ChartUtil.getLine2DChart(dt, 24);
		$S("Data", xml);
	}

	public static void dg1DataBind(DataGridAction dga) {
		String startDate = dga.getParam("StartDate");
		String endDate = dga.getParam("EndDate");
		Date start = null;
		Date end = null;
		if (StringUtil.isEmpty(startDate)) {
			start = new Date(System.currentTimeMillis() - 30 * 24 * 60 * 60 * 1000L);
			end = new Date();
		} else {
			start = DateUtil.parse(startDate);
			end = DateUtil.parse(endDate);
		}
		DataTable dt = getHourData(Application.getCurrentSiteID(), start, end);
		dt.sort("Item","asc");
		for (int i = 0; i < dt.getRowCount(); i++) {
			String str = dt.getString(i, "Item");
			int item = Integer.parseInt(str);
			str = item + ":00 �� " + (item + 1) + ":00";
			dt.set(i, "Item", str);
		}
		if (dt.getRowCount() > 0) {
			ReportUtil.computeRate(dt, "PV", "Rate");
			ReportUtil.addSuffix(dt, "Rate", "%");
			ReportUtil.addTotal(dt, new String[] { "PV", "UV", "IP", "ReturnVisitor" });
			dt.set(0, "Rate", "100.00%");
		}
		dga.bindData(dt);
	}

	public static DataTable getHourData(long siteID, Date start, Date end) {
		String period1 = DateUtil.toString(start, "yyyyMM");
		String period2 = DateUtil.toString(end, "yyyyMM");
		QueryBuilder qb =
				new QueryBuilder("select * from ZCStatItem where SiteID=? and Type='Hour' and SubType in ('PV','IP','UV','ReturnVisitor') and Period>=? and Period<=?");
		qb.add(siteID);
		qb.add(period1);
		qb.add(period2);
		DataTable dt = qb.executeDataTable();
		return ReportUtil.toItemTable(dt, start, end);
	}

	public static void main(String[] args) {
		Date start = new Date(System.currentTimeMillis() - 30 * 24 * 60 * 60 * 1000L);
		Date end = new Date();
		System.out.println(getHourData(206, start, end));
	}
}
