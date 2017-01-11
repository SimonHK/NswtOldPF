package com.nswt.cms.stat;

import java.util.Date;

import com.nswt.cms.stat.impl.URLStat;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.DateUtil;
import com.nswt.framework.utility.StringUtil;
import com.nswt.schema.ZCStatItemSchema;
import com.nswt.schema.ZCStatItemSet;

/**
 * @Author NSWT
 * @Date 2008-11-20
 * @Mail nswt@nswt.com.cn
 */
public abstract class AbstractStat {
	public static final int PERIOD_DAY = 1;

	public static final int PERIOD_HOUR = 2;

	/**
	 * �Ƿ���һ���µ�ʱ��Σ���ʱ�����Ҫ�����¼
	 */
	protected boolean isNewMonth = false;

	/**
	 * ��ʼ��ͳ�����Ҫ�Ǵӳ־ò��ȡͳ�����ֵ
	 */
	public void init() {
		String period = DateUtil.toString(new Date(), "yyyyMM");
		DataTable dt = new QueryBuilder("select * from ZCStatItem where Period=? and Type=?", period, getStatType())
				.executeDataTable();
		if (dt.getRowCount() == 0) {// ������δ��ʼͳ��
			isNewMonth = true;
		} else {
			int day = Integer.parseInt(DateUtil.getCurrentDate("dd")) + 5 - 1;// ǰ����SiteID,Period,Type,SubType,Item����
			for (int i = 0; i < dt.getRowCount(); i++) {
				String str = dt.getString(i, day);
				if (StringUtil.isEmpty(str)) {
					str = "0";
				}
				long siteID = Long.parseLong(dt.getString(i, "SiteID"));
				String type = dt.getString(i, "Type");
				String subtype = dt.getString(i, "SubType");
				String item = dt.getString(i, "Item");

				String[] avgSubTypes = getAverageSubTypes();
				boolean flag = false;
				if (avgSubTypes != null) {
					for (int j = 0; j < avgSubTypes.length; j++) {
						if (avgSubTypes[j].equals(subtype)) {
							VisitCount.getInstance().addAverage(siteID, type, subtype, item, 0, false);
							flag = true;
							break;
						}
					}
				}
				if (!flag) {
					VisitCount.getInstance().add(siteID, type, subtype, item, 0, false);
				}
			}
		}
	}

	/**
	 * ����һ�η��ʵĲ���Map�����±�ͳ�����ֵ
	 */
	public abstract void deal(Visit v);

	/**
	 * ����һ�η��ʵĲ���Map�����±�ͳ�����ֵ
	 */
	public void onPeriodChange(int type, long current) {
		if (type == AbstractStat.PERIOD_DAY) {
			String period = DateUtil.toString(new Date(current), "yyyyMMdd");
			if (period.endsWith("01")) {
				VisitCount.getInstance().clearType(getStatType(), true);
				isNewMonth = true;
			} else {
				VisitCount.getInstance().clearType(getStatType(), false);
			}
		}
	}

	/**
	 * ����ͳ�����ֵд��־ò�
	 */
	public void update(Transaction tran, VisitCount vc, long current, boolean newMonthFlag, boolean isNewPeriod) {
		Date today = new Date(current);
		if (isNewPeriod) {
			today = DateUtil.addDay(today, -1);// ����������ǰ�����ݣ���ʱisNewMonth��δ��Ϊtrue
		}
		String period = DateUtil.toString(today, "yyyyMM");
		int day = Integer.parseInt(DateUtil.toString(today, "dd"));
		String type = getStatType();
		QueryBuilder qbAdd = new QueryBuilder("update ZCStatItem set Count" + day + "=Count" + day
				+ "+? where SiteID=? and " + "Period=? and Type=? and SubType=? and Item=?");
		QueryBuilder qbAverage = new QueryBuilder("update ZCStatItem set Count" + day + "=? where SiteID=? and "
				+ "Period=? and Type=? and SubType=? and Item=?");
		qbAdd.setBatchMode(true);
		qbAverage.setBatchMode(true);
		if (newMonthFlag) {
			ZCStatItemSet set = new ZCStatItemSet();
			long[] sites = vc.getSites();
			for (int i = 0; i < sites.length; i++) {
				String[] subtypes = vc.getSubTypes(sites[i], type);
				for (int j = 0; j < subtypes.length; j++) {
					String[] items = vc.getItems(sites[i], type, subtypes[j]);
					for (int k = 0; k < items.length; k++) {
						if (StringUtil.isEmpty(items[k])) {
							continue;
						}
						long count = vc.get(sites[i], type, subtypes[j], items[k]);
						if (count == 0) {
							continue;
						}
						String item = items[k];
						if (type.equalsIgnoreCase(URLStat.Type)) {
							item = StringUtil.md5Hex(item);
						}
						ZCStatItemSchema si = new ZCStatItemSchema();
						si.setSiteID(sites[i]);
						si.setPeriod(period);
						si.setType(type);
						si.setSubType(subtypes[j]);
						si.setItem(item);
						if (!si.fill()) {
							for (int m = 5; m < si.getColumnCount() - 1; m++) {
								si.setV(m, new Integer(0));
							}
							si.setV(day + 4, new Long(count));
							if (type.equalsIgnoreCase(URLStat.Type)) {
								si.setMemo(items[k]);// URLͳ�ƱȽ����⣬Item��ܳ�
							}
							set.add(si);
							if (!vc.isAverageValue(sites[i], type, subtypes[j], item)) {
								VisitCount.getInstance().set(sites[i], type, subtypes[j], items[k], 0, false);
							}else{
								VisitCount.getInstance().setNotNeedInsert(sites[i], type, subtypes[j], items[k]);
							}
						} else {// ˵���Ѿ�����
							QueryBuilder qb = null;
							if (vc.isAverageValue(sites[i], type, subtypes[j], item)) {
								qb = qbAverage;
							} else {
								qb = qbAdd;
								VisitCount.getInstance().set(sites[i], type, subtypes[j], items[k], 0, false);
							}
							qb.add(count);
							qb.add(sites[i]);
							qb.add(period);
							qb.add(type);
							qb.add(subtypes[j]);
							qb.add(item);
							qb.addBatch();
						}
					}
				}
			}
			tran.add(set, Transaction.DELETE_AND_INSERT);
			this.isNewMonth = false;
		} else {
			long[] sites = vc.getSites();
			ZCStatItemSet set = new ZCStatItemSet();
			for (int i = 0; i < sites.length; i++) {
				String[] subtypes = vc.getSubTypes(sites[i], type);
				for (int j = 0; j < subtypes.length; j++) {
					String[] items = vc.getItems(sites[i], type, subtypes[j]);
					for (int k = 0; k < items.length; k++) {
						long count = vc.get(sites[i], type, subtypes[j], items[k]);
						boolean isNeedInsert = vc.isNeedInsert(sites[i], type, subtypes[j], items[k]);
						if (count == 0) {
							continue;// ������Ϊ0�Ĳ���Ҫ����
						}
						if (StringUtil.isEmpty(items[k])) {
							continue;
						}
						String item = items[k];
						if (type.equalsIgnoreCase(URLStat.Type)) {
							item = StringUtil.md5Hex(item);
						}
						boolean updateFlag = true;
						if (isNeedInsert) {
							ZCStatItemSchema si = new ZCStatItemSchema();
							si.setSiteID(sites[i]);
							si.setPeriod(period);
							si.setType(type);
							si.setSubType(subtypes[j]);
							si.setItem(item);
							if (type.equalsIgnoreCase(URLStat.Type)) {
								si.setMemo(items[k]);// URLͳ�ƱȽ����⣬Item��ܳ�
							}
							if (!si.fill()) {// ˵��������
								updateFlag = false;
								for (int m = 5; m < si.getColumnCount() - 1; m++) {
									si.setV(m, new Integer(0));
								}
								si.setV(day + 4, new Long(count));
								set.add(si);
								if (!vc.isAverageValue(sites[i], type, subtypes[j], item)) {
									VisitCount.getInstance().set(sites[i], type, subtypes[j], items[k], 0, false);
								}else{
									VisitCount.getInstance().setNotNeedInsert(sites[i], type, subtypes[j], items[k]);
								}
							}
						}
						if (updateFlag) {
							QueryBuilder qb = null;
							if (vc.isAverageValue(sites[i], type, subtypes[j], item)) {
								qb = qbAverage;
							} else {
								qb = qbAdd;
								VisitCount.getInstance().set(sites[i], type, subtypes[j], items[k], 0, false);
							}
							qb.add(count);
							qb.add(sites[i]);
							qb.add(period);
							qb.add(type);
							qb.add(subtypes[j]);
							qb.add(item);
							qb.addBatch();
						}
					}
				}
			}
			tran.add(set, Transaction.DELETE_AND_INSERT);
		}
		tran.add(qbAdd);
		tran.add(qbAverage);
	}

	/**
	 * ����ͳ������
	 */
	public abstract String getStatType();

	/**
	 * ����ͳ��ֵΪƽ��ֵ������
	 */
	public String[] getAverageSubTypes() {
		return null;
	}
}
