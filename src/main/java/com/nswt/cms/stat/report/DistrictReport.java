package com.nswt.cms.stat.report;

import java.util.Date;

import com.nswt.framework.Page;
import com.nswt.framework.controls.DataGridAction;
import com.nswt.framework.data.DataRow;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.utility.DateUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.Application;

/**
 * @Author NSWT
 * @Date 2016-4-27
 * @Mail nswt@nswt.com.cn
 */
public class DistrictReport extends Page {
	public void getChartData() {
		String startDate = $V("StartDate");
		String endDate = $V("EndDate");
		Date start = null;
		Date end = null;
		if (StringUtil.isEmpty(startDate)) {
			start = new Date(System.currentTimeMillis() - 30 * 24 * 60 * 60 * 1000L);
			end = new Date();
		} else {
			start = DateUtil.parse(startDate);
			end = DateUtil.parse(endDate);
		}
		String code = $V("Code");
		String type = $V("Type");
		DataTable dt = null;
		if ("C".equals(type)) {
			dt = getCountryTable(Application.getCurrentSiteID(), start, end, false);
		} else {
			if (StringUtil.isEmpty(code) || code.equals("null") || code.equals("000000")) {
				dt = getProvinceTable(Application.getCurrentSiteID(), start, end, false);
			} else {
				dt = getCityTable(Application.getCurrentSiteID(), code, start, end, false);
			}
		}
		dt.sort("Item");
		dt.deleteColumn("Trend");
		dt.deleteColumn("UV");
		dt.deleteColumn("IP");
		String xml = ChartUtil.getPie3DChart(dt, 8, 1);
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
		String code = dga.getParam("Code");
		String type = dga.getParam("Type");
		DataTable dt = null;
		if ("C".equals(type)) {
			dt = getCountryTable(Application.getCurrentSiteID(), start, end, true);
		} else {
			if (StringUtil.isEmpty(code) || code.equals("null") || code.equals("000000")) {
				dt = getProvinceTable(Application.getCurrentSiteID(), start, end, true);
			} else {
				dt = getCityTable(Application.getCurrentSiteID(), code, start, end, true);
			}
		}
		ReportUtil.computeRate(dt, "PV", "Rate");
		dt.sort("Rate");
		ReportUtil.addSuffix(dt, "Rate", "%");
		dga.bindData(dt);

	}

	/**
	 * ��ȡ����ֲ�����
	 */
	public static DataTable getDistrictTable(long siteID, Date start, Date end) {
		String period1 = DateUtil.toString(start, "yyyyMM");
		String period2 = DateUtil.toString(end, "yyyyMM");
		QueryBuilder qb = new QueryBuilder(
				"select * from ZCStatItem where SiteID=? and Type='District' and Period>=? and Period<=?");
		qb.add(siteID);
		qb.add(period1);
		qb.add(period2);
		DataTable dt = qb.executeDataTable();
		dt = ReportUtil.toItemTable(dt, start, end, true);
		if(dt.getDataColumn("UV")==null){
			dt.insertColumn("UV");
		}
		if(dt.getDataColumn("IP")==null){
			dt.insertColumn("IP");
		}
		return dt;
	}

	public static DataTable getCountryTable(long siteID, Date start, Date end, boolean chartFlag) {
		DataTable src = getDistrictTable(siteID, start, end);
		Mapx pvMap = src.toMapx("District", "PV");
		Mapx uvMap = src.toMapx("District", "UV");
		Mapx ipMap = src.toMapx("District", "IP");
		Mapx[] maps = new Mapx[] { pvMap, uvMap, ipMap };
		for (int k = 0; k < maps.length; k++) {
			Mapx map = maps[k];
			Object[] ks = map.keyArray();
			for (int i = map.size() - 1; i >= 0; i--) {
				String district = ks[i].toString();
				if (!district.equals("000000") && !district.startsWith("000")) {
					map.put("000000", map.getInt("000000") + map.getInt(district));// ���ӵ��й���������
					map.remove(district);
				}
			}
		}
		DataTable dt = new DataTable(src.getDataColumns(), null);
		Object[] ks = pvMap.keyArray();
		Object[] vs = pvMap.valueArray();
		for (int i = 0; i < pvMap.size(); i++) {
			dt.insertRow((Object[]) null);// ��3��Ϊʱ������
			DataRow dr = dt.getDataRow(dt.getRowCount() - 1);
			dr.set("Item", ks[i]);
			dr.set("PV", vs[i]);
			dr.set("UV", uvMap.getInt(ks[i]));
			dr.set("IP", ipMap.getInt(ks[i]));
		}
		ReportUtil.addTrend(dt, "District", "PV");
		Mapx map = new QueryBuilder("select code,name from ZDDistrict where TreeLevel<3").executeDataTable().toMapx(0,
				1);
		map.put("000999", "δ֪����");
		for (int i = 0; i < dt.getRowCount(); i++) {
			String item = dt.getString(i, "Item");
			if (chartFlag && item.equals("000000")) {
				item = "<a href='District.jsp?Type=P&Code=000000'>" + map.getString(item) + "</a>";
			} else {
				item = map.getString(item);
			}
			dt.set(i, "Item", item);
		}
		return dt;
	}

	public static DataTable getProvinceTable(long siteID, Date start, Date end, boolean chartFlag) {
		DataTable src = getDistrictTable(siteID, start, end);
		Mapx pvMap = src.toMapx("District", "PV");
		Mapx uvMap = src.toMapx("District", "UV");
		Mapx ipMap = src.toMapx("District", "IP");
		Mapx[] maps = new Mapx[] { pvMap, uvMap, ipMap };
		for (int k = 0; k < maps.length; k++) {
			Mapx map = maps[k];
			Object[] ks = map.keyArray();
			for (int i = map.size() - 1; i >= 0; i--) {
				String district = ks[i].toString();
				if (!district.startsWith("000")) {
					int count = map.getInt(district);
					String code = district.substring(0, 2) + "0000";
					if(!code.equals(district)){
						map.put(code, count + map.getInt(code));// ���ӵ�����ʡ������
					}
				}
				if (!district.equals("000000") && (district.startsWith("000") || !district.endsWith("0000"))) {
					map.remove(district);
				}
			}
			if (map.getInt("000000") > 0) {
				map.put("000999", map.getInt("000000"));
			}
		}
		DataTable dt = new DataTable(src.getDataColumns(), null);
		Object[] ks = pvMap.keyArray();
		Object[] vs = pvMap.valueArray();
		for (int i = 0; i < pvMap.size(); i++) {
			dt.insertRow((Object[]) null);// ��3��Ϊʱ������
			DataRow dr = dt.getDataRow(dt.getRowCount() - 1);
			dr.set("Item", ks[i]);
			dr.set("PV", vs[i]);
			dr.set("UV", uvMap.getInt(ks[i]));
			dr.set("IP", ipMap.getInt(ks[i]));
		}
		ReportUtil.addTrend(dt, "District", "PV");
		Mapx map = new QueryBuilder(
				"select code,name from ZDDistrict where code like '11%' or code like '12%' or code like '31%' "
						+ "or code like '50%' or TreeLevel<3").executeDataTable().toMapx(0, 1);
		map.put("000999", "δ֪����");
		for (int i = 0; i < dt.getRowCount(); i++) {
			String item = dt.getString(i, "Item");
			if (chartFlag) {
				item = "<a href='District.jsp?Type=P&Code=" + item + "'>" + map.getString(item) + "</a>";
			} else {
				item = map.getString(item);
			}
			dt.set(i, "Item", item);
		}
		return dt;
	}

	public static DataTable getCityTable(long siteID, String province, Date start, Date end, boolean chartFlag) {
		DataTable src = getDistrictTable(siteID, start, end);
		Mapx pvMap = src.toMapx("District", "PV");
		Mapx uvMap = src.toMapx("District", "UV");
		Mapx ipMap = src.toMapx("District", "IP");
		Mapx[] maps = new Mapx[] { pvMap, uvMap, ipMap };
		String prefix = province.substring(0, 2);
		for (int k = 0; k < maps.length; k++) {
			Mapx map = maps[k];
			Object[] ks = map.keyArray();
			for (int i = map.size() - 1; i >= 0; i--) {
				String district = ks[i].toString();
				if (!district.startsWith(prefix)) {
					map.remove(district);
				}
			}
			map.put("000999", map.getInt(prefix + "0000"));
			map.remove(prefix + "0000");
		}
		DataTable dt = new DataTable(src.getDataColumns(), null);
		Object[] ks = pvMap.keyArray();
		Object[] vs = pvMap.valueArray();
		for (int i = 0; i < pvMap.size(); i++) {
			dt.insertRow((Object[]) null);// ��3��Ϊʱ������
			DataRow dr = dt.getDataRow(dt.getRowCount() - 1);
			dr.set("Item", ks[i]);
			dr.set("PV", vs[i]);
			dr.set("UV", uvMap.getInt(ks[i]));
			dr.set("IP", ipMap.getInt(ks[i]));
		}
		ReportUtil.addTrend(dt, "District", "PV");
		Mapx map = new QueryBuilder(
				"select code,name from ZDDistrict where code like '11%' or code like '12%' or code like '31%' "
						+ "or code like '50%' or TreeLevel<3").executeDataTable().toMapx(0, 1);
		map.put("000999", "δ֪����");
		for (int i = 0; i < dt.getRowCount(); i++) {
			String item = dt.getString(i, "Item");
			item = map.getString(item);
			dt.set(i, "Item", item);
		}
		return dt;
	}
}
