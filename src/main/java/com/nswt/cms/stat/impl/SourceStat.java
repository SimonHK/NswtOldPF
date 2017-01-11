package com.nswt.cms.stat.impl;

import java.util.Date;

import com.nswt.cms.api.SearchAPI;
import com.nswt.cms.stat.AbstractStat;
import com.nswt.cms.stat.StatUtil;
import com.nswt.cms.stat.Visit;
import com.nswt.cms.stat.VisitCount;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.DateUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.ServletUtil;
import com.nswt.framework.utility.StringUtil;

/**
 * @Author NSWT
 * @Date 2008-11-20
 * @Mail nswt@nswt.com.cn
 */
public class SourceStat extends AbstractStat {
	private static final String Type = "Source";

	private Mapx siteMap = new Mapx();// �����ж���Ӧվ���Ƿ��Ѿ���ʼ����

	public String getStatType() {
		return Type;
	}

	public void deal(Visit v) {
		if ("Unload".equals(v.Event)) {
			return;
		}
		if (!siteMap.containsKey(new Long(v.SiteID))) {
			VisitCount.getInstance().initLRUMap(v.SiteID, Type, "Keyword", 1000, null);
			VisitCount.getInstance().initLRUMap(v.SiteID, Type, "Referer", 1000, null);
			siteMap.put(new Long(v.SiteID), "");
		}
		VisitCount.getInstance().add(v.SiteID, Type, "Host", v.Host);
		if (v.UVFlag) {
			if (StringUtil.isEmpty(v.Referer) && v.URL.indexOf("Result.jsp") < 0) {
				VisitCount.getInstance().add(v.SiteID, Type, "Direct", "0");
			} else {
				String[] se = getSearchEngine(v);
				if (se == null) {
					String domain = StatUtil.getDomain(v.Referer);
					if (!domain.equalsIgnoreCase(v.Host)) {
						VisitCount.getInstance().add(v.SiteID, Type, "Referer", domain);
					} else {
						VisitCount.getInstance().add(v.SiteID, Type, "Direct", "0");
					}
				} else {
					VisitCount.getInstance().add(v.SiteID, Type, "SearchEngine", se[0]);
					VisitCount.getInstance().add(v.SiteID, Type, "Keyword", se[1]);
				}
			}
		}
	}

	public void onPeriodChange(int type, long current) {
		if (type == AbstractStat.PERIOD_DAY) {
			String period = DateUtil.toString(new Date(current), "yyyyMMdd");
			if (period.endsWith("01")) {
				isNewMonth = true;
				VisitCount.getInstance().clearType(getStatType(), true);
			} else {
				VisitCount.getInstance().clearType(getStatType(), false);
				VisitCount.getInstance().clearSubType(getStatType(), "Keyword", true);
				VisitCount.getInstance().clearSubType(getStatType(), "Referer", true);
			}
		}
	}

	public void update(Transaction tran, VisitCount vc, long current, boolean newMonthFlag, boolean isNewPeriod) {
		if (!newMonthFlag) {
			Date today = new Date(current);
			if (isNewPeriod) {
				today = DateUtil.addDay(today, -1);// ����������ǰ�����ݣ���ʱisNewMonth��δ��Ϊture
			}
			String period = DateUtil.toString(today, "yyyyMM");
			long[] sites = vc.getSites();
			for (int i = 0; i < sites.length; i++) {
				URLStat.dealNotNeedInsertItem(vc, period, sites[i], Type, "Referer");
				URLStat.dealNotNeedInsertItem(vc, period, sites[i], Type, "Keyword");
			}
		}
		super.update(tran, vc, current, newMonthFlag, isNewPeriod);
	}

	public static String[] getSearchEngine(Visit v) {
		String url = v.URL;
		if (StringUtil.isEmpty(url)) {
			return null;
		}
		// �ȴ���վ����
		if (url.indexOf("Result.jsp") > 0) {
			String keyword = SearchAPI.getParameter(url, "query");
			return new String[] { "վ������", keyword };
		}
		url = v.Referer;
		String domain = StatUtil.getDomain(url);
		Mapx map = ServletUtil.getMapFromQueryString(url);
		String name = null;
		String keyword = null;
		if (domain.indexOf("baidu.") > 0) {
			keyword = StringUtil.urlDecode(map.getString("wd"), "GBK");
			name = "�ٶ�";
		} else if (domain.indexOf("google.") > 0) {
			String charset = map.getString("ie");
			if (StringUtil.isEmpty(charset)) {
				charset = "UTF-8";
			}
			keyword = StringUtil.urlDecode(map.getString("q"), charset);
			name = "�ȸ�";
		} else if (domain.indexOf("yahoo.") > 0) {
			String charset = map.getString("ei");
			if (StringUtil.isEmpty(charset)) {
				charset = "UTF-8";
			}
			keyword = StringUtil.urlDecode(map.getString("p"), charset);
			name = "�Ż�";
		} else if (domain.indexOf("msn.") > 0) {
			keyword = StringUtil.urlDecode(map.getString("q"), "UTF-8");
			name = "MSN";
		} else if (domain.indexOf("soso.") > 0) {
			keyword = StringUtil.urlDecode(map.getString("w"), "GBK");
			name = "����";
		} else if (domain.indexOf("sogou.") > 0) {
			keyword = StringUtil.urlDecode(map.getString("query"), "GBK");
			name = "�ѹ�";
		} else if (domain.indexOf("zhongsou.") > 0) {
			keyword = StringUtil.urlDecode(map.getString("word"), "GBK");
			name = "����";
		} else if (domain.indexOf("youdao.") > 0) {
			String charset = map.getString("ue");
			if (StringUtil.isEmpty(charset)) {
				charset = "UTF-8";
			}
			keyword = StringUtil.urlDecode(map.getString("q"), charset);
			name = "�е�";
		} else if (domain.indexOf("live.") > 0) {
			keyword = StringUtil.urlDecode(map.getString("q"), "UTF-8");
			name = "Live.com";
		}
		if (StringUtil.isNotEmpty(keyword)) {
			return new String[] { name, keyword };
		}
		return null;
	}

	public static void main(String[] args) {
		System.out.println(StatUtil.getUniqueID());
	}
}
