package com.nswt.cms.stat;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nswt.cms.stat.impl.CatalogStat;
import com.nswt.cms.stat.impl.ClientStat;
import com.nswt.cms.stat.impl.DistrictStat;
import com.nswt.cms.stat.impl.GlobalStat;
import com.nswt.cms.stat.impl.HourStat;
import com.nswt.cms.stat.impl.LeafStat;
import com.nswt.cms.stat.impl.SourceStat;
import com.nswt.cms.stat.impl.URLStat;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.DateUtil;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.ServletUtil;
import com.nswt.framework.utility.StringUtil;

/**
 * 统计模块入口，前台会调用本类的deal()方法
 * 
 * @Author NSWT
 * @Date 2008-11-20
 * @Mail nswt@nswt.com.cn
 */
public class VisitHandler {
	private static AbstractStat[] items;// 各个统计项

	private static long lastTime = System.currentTimeMillis();// 上次访问时间

	public static final long HOUR = 60 * 60 * 1000;

	public static final long DAY = 24 * HOUR;

	private static boolean isUpdating;// 是否正在更新中

	private static boolean isSimulating;//

	private static Object mutex = new Object();

	/**
	 * 被StatUpdateTask定时调用
	 */
	public static void update(final long current, final boolean isNewPeriod, boolean waitFlag) {// Wait标记只有在模拟产生数据时才有用
		VisitCount vc = null;
		while (waitFlag && isUpdating) {
			try {
				vc = (VisitCount) VisitCount.getInstance().clone();
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (!isUpdating) {
			synchronized (mutex) {
				if (!isUpdating) {
					if (vc == null) {
						vc = (VisitCount) VisitCount.getInstance().clone();
					}
					isUpdating = true;
					final VisitCount vc2 = vc;
					final boolean[] mArr = new boolean[items.length];
					for (int i = 0; i < items.length; i++) {
						mArr[i] = items[i].isNewMonth;
					}
					new Thread() {
						public void run() {
							LogUtil.getLogger().info("正在更新统计信息......");
							long current = System.currentTimeMillis();
							Transaction tran = new Transaction();
							try {
								for (int i = 0; i < items.length; i++) {
									items[i].update(tran, vc2, current, mArr[i], isNewPeriod);// 必须传入isNewMonth，因为原值可以己改变
								}
								tran.commit();
							} catch (Throwable t) {
								t.printStackTrace();
							} finally {
								isUpdating = false;
							}
							LogUtil.getLogger().info("更新统计信息耗时 " + (System.currentTimeMillis() - current) + " 毫秒.");
						}
					}.start();
				}
			}
		}
	}

	private static String CurrentPeriod;

	/**
	 * 更换统计时段
	 */
	public static void changePeriod(int type, long current) {
		synchronized (mutex) {
			String str = DateUtil.toString(new Date(current));
			if (str.equals(CurrentPeriod)) {
				return;
			}
			CurrentPeriod = str;
			if (isSimulating) {
				update(current, true, true);
			} else {
				update(current, true, false);
			}
			for (int i = 0; i < items.length; i++) {
				items[i].onPeriodChange(type, current);
			}
		}
	}

	/**
	 * 初始化统计项
	 */
	public static void init(long current) {
		if (items == null) {
			synchronized (mutex) {
				if (items == null) {
					AbstractStat[] arr = new AbstractStat[] { new GlobalStat(), new ClientStat(), new LeafStat(),
							new HourStat(), new SourceStat(), new CatalogStat(), new URLStat(), new DistrictStat() };
					for (int i = 0; i < arr.length; i++) {
						arr[i].init();
					}
					lastTime = current;
					items = arr;// 不能一开始就给items赋值，否则一部分线程会在统计项未初始化完毕之前开始调用统计项的deal()方法lastTime
					// = v.VisitTime;
				}
			}
		}
	}

	/**
	 * 处理一次点击,本方法仅用于测试,以产生历史数据
	 */
	public static void deal(Visit v) {
		init(v.VisitTime);
		long current = v.VisitTime;
		synchronized (mutex) {
			if (!DateUtil.toString(new Date(current)).equals(DateUtil.toString(new Date(lastTime)))) {
				changePeriod(AbstractStat.PERIOD_DAY, current);
			}
			lastTime = current;
		}
		for (int i = 0; i < items.length; i++) {
			items[i].deal(v);
		}
	}

	/**
	 * 处理一次HTTP请求
	 */
	public static void deal(HttpServletRequest request, HttpServletResponse response) {
		long current = System.currentTimeMillis();
		if (items == null) {
			synchronized (mutex) {
				if (items == null) {
					init(current);
				}
			}
		}
		synchronized (mutex) {
			if (!DateUtil.toString(new Date(current)).equals(DateUtil.toString(new Date(lastTime)))) {
				changePeriod(AbstractStat.PERIOD_DAY, current);
			}
			lastTime = current;
		}

		Mapx map = ServletUtil.getParameterMap(request);
		map.put("IP", StatUtil.getIP(request));
		map.put("UserAgent", request.getHeader("User-Agent"));

		Visit v = new Visit();
		v.UserAgent = map.getString("UserAgent");
		try {
			v.SiteID = Long.parseLong(map.getString("SiteID"));
		} catch (Exception e) {
			try {
				response.getWriter().println("alert('统计时发生错误:" + map.getString("SiteID") + "不是正确的SiteID')");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return;
		}

		v.UniqueID = ServletUtil.getCookieValue(request, "UniqueID");

		if ("KeepAlive".equalsIgnoreCase(map.getString("Event"))) {
			GlobalStat.keepAlive(v.SiteID, v.UniqueID, current);
			return;// 仅仅是表明本次会话尚未结束
		}
		v.CatalogInnerCode = map.getString("CatalogInnerCode");
		v.Type = map.getString("Type");
		if (StringUtil.isEmpty(v.Type)) {
			v.Type = "Other";
		}
		v.Event = map.getString("Event");
		v.LeafID = StringUtil.isNotEmpty(map.getString("LeafID")) ? Long.parseLong(map.getString("LeafID")) : 0;
		v.IP = map.getString("IP");
		// 可能有双IP的情况
		if (StringUtil.isNotEmpty(v.IP) && v.IP.indexOf(",") > 0) {
			String[] arr = v.IP.split("\\,");
			for (int i = 0; i < arr.length; i++) {
				if (StringUtil.isNotEmpty(arr[i]) && !arr[i].trim().startsWith("10.")
						&& !arr[i].trim().startsWith("192.")) {
					v.IP = arr[i].trim();
					break;
				}
			}
		}
		v.VisitTime = current;
		v.Referer = map.getString("Referer");
		v.URL = map.getString("URL");

		if (StringUtil.isNotEmpty(v.URL)) {
			v.URL = v.URL.replace('\'', '0').replace('\\', '0');
			String prefix = v.URL.substring(0, 8);
			String tail = v.URL.substring(8);
			tail = tail.replaceAll("/+", "/");
			v.URL = prefix + tail;
		}

		// 此处必须特别处理，不然攻击者可能构造特别的Referer和URL来进行SQL注入
		if (StringUtil.isNotEmpty(v.Referer)) {
			v.Referer = v.Referer.replace('\'', '0').replace('\\', '0');
		}
		if (StringUtil.isEmpty(v.UserAgent)) {
			v.UserAgent = "Unknow";
		}

		if (!"Unload".equalsIgnoreCase(map.getString("Event"))) {// 正常点击
			try {
				if (StringUtil.isEmpty(v.URL)) {
					return;
				}
				String sites = ServletUtil.getCookieValue(request, "Sites");
				if (StringUtil.isEmpty(v.UniqueID)) {
					v.UniqueID = StatUtil.getUniqueID();
					v.RVFlag = false;
					ServletUtil.setCookieValue(request, response, "UniqueID", -1, v.UniqueID);
					ServletUtil.setCookieValue(request, response, "Sites", -1, "_" + v.SiteID);
				} else {
					if (StringUtil.isNotEmpty(sites) && sites.indexOf("_" + v.SiteID) >= 0) {
						v.RVFlag = true;
					} else {
						v.RVFlag = false;
						ServletUtil.setCookieValue(request, response, "Sites", -1, sites + "_" + v.SiteID);
					}
				}
				GlobalStat.dealUniqueID(v);

				v.Host = map.getString("Host");
				if (StringUtil.isNotEmpty(v.Host)) {
					v.Host = v.Host.toLowerCase();
				} else {
					v.Host = "无";
				}
				v.CookieEnabled = "1".equals(map.getString("ce")) ? true : false;
				v.FlashVersion = map.getString("fv");
				v.FlashEnabled = StringUtil.isEmpty(v.FlashVersion);
				v.JavaEnabled = "1".equals(map.getString("je")) ? true : false;
				v.Language = StatUtil.getLanguage(map.getString("la"));
				if (v.Language.equals("其他")) {
					v.Language = StatUtil.getLanguage("; " + request.getHeader("accept-language") + ";");
				}
				v.OS = StatUtil.getOS(v.UserAgent);
				v.Browser = StatUtil.getBrowser(v.UserAgent);
				v.Referer = map.getString("Referer");
				v.Screen = map.getString("sr");
				v.Screen = StatUtil.getScreen(v.Screen);
				v.ColorDepth = map.getString("cd");
				v.District = StatUtil.getDistrictCode(v.IP);
				v.IPFlag = !GlobalStat.isTodayVisited(String.valueOf(v.SiteID), v.IP, v.VisitTime);
				v.Frequency = Integer.parseInt(map.getString("vq"));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			try {
				v.StickTime = new Double(Math.ceil(Double.parseDouble(map.getString("StickTime").trim()))).intValue();
			} catch (Exception e) {
				LogUtil.info(request.getQueryString() + " " + request.getHeader("User-Agent"));
			}
			if (v.StickTime <= 0) {
				v.StickTime = 1;
			}
		}
		// 输出Visit的值
		// LogUtil.info(printVisit(v));
		for (int i = 0; i < items.length; i++) {
			try {
				items[i].deal(v);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static String printVisit(Visit v) {
		Mapx map = new Mapx();
		map.put("URL", v.URL);
		map.put("Frequency", v.Frequency);
		map.put("LeafID", v.LeafID);
		map.put("SiteID", v.SiteID);
		map.put("StickTime", v.StickTime);
		map.put("VisitTime", v.VisitTime);
		map.put("Browser", v.Browser);
		map.put("CatalogInnerCode", v.CatalogInnerCode);
		map.put("ColorDepth", v.ColorDepth);
		map.put("District", v.District);
		map.put("Event", v.Event);
		map.put("FlashVersion", v.FlashVersion);
		map.put("Host", v.Host);
		map.put("IP", v.IP);
		map.put("Language", v.Language);
		map.put("OS", v.OS);
		map.put("Referer", v.Referer);
		map.put("Screen", v.Screen);
		map.put("Type", v.Type);
		map.put("UniqueID", v.UniqueID);
		map.put("UserAgent", v.UserAgent);
		map.put("CookieEnabled", "" + v.CookieEnabled);
		map.put("FlashEnabled", "" + v.FlashEnabled);
		map.put("IPFlag", "" + v.IPFlag);
		map.put("RVFlag", "" + v.RVFlag);
		map.put("UVFlag", "" + v.UVFlag);
		return map.toString();
	}

	public static void setSimulating(boolean isSimulating) {
		VisitHandler.isSimulating = isSimulating;
	}
}
