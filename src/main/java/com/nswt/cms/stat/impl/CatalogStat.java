package com.nswt.cms.stat.impl;

import java.util.ArrayList;

import com.nswt.cms.pub.CatalogUtil;
import com.nswt.cms.stat.AbstractStat;
import com.nswt.cms.stat.Visit;
import com.nswt.cms.stat.VisitCount;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.StringUtil;
import com.nswt.schema.ZCStatItemSchema;
import com.nswt.schema.ZCStatItemSet;

/**
 * ͳ�Ƹ���Ŀ�ĵ������ƽ��ͣ��ʱ��
 * 
 * @Author NSWT
 * @Date 2008-11-20
 * @Mail nswt@nswt.com.cn
 */
public class CatalogStat extends AbstractStat {
	private static final String[] avgSubTypes = new String[] { "StickTime" };

	private static final String Type = "Catalog";

	public String getStatType() {
		return Type;
	}

	public String[] getAverageSubTypes() {
		return avgSubTypes;
	}

	public void deal(Visit v) {
		if (StringUtil.isEmpty(v.Type) || "AD".equals(v.Type) || "Other".equals(v.Type)) {
			return;
		}
		String code = v.CatalogInnerCode;
		if (code == null || code.length() % 6 != 0) {
			code = "";
		}
		if (StringUtil.isNotEmpty(code) && CatalogUtil.getSiteIDByInnerCode(v.CatalogInnerCode) == null) {
			return;// ������Ŀ�Ѿ���ɾ���˻��������ƶ���
		}
		if (v.LeafID == 0) {// �����ǰURL����Ŀ��ҳ���������Ŀ��ҳ�ķ�����
			if ("Unload".equals(v.Event)) {
				VisitCount.getInstance().addAverage(v.SiteID, Type, "StickTime", code + "Index", v.StickTime);
			} else {
				VisitCount.getInstance().add(v.SiteID, Type, "PV", code + "Index");
			}
		}
		String[] codes = new String[code.length() / 6];
		for (int i = 0; i < codes.length; i++) {
			codes[i] = code.substring(0, i * 6 + 6);
		}
		for (int i = 0; i < codes.length; i++) {
			code = codes[i];
			if ("Unload".equals(v.Event)) {
				VisitCount.getInstance().addAverage(v.SiteID, Type, "StickTime", code, v.StickTime);// ���½���ҳ��ͣ��ʱ��
			} else {
				VisitCount.getInstance().add(v.SiteID, Type, "PV", code);
			}
			code = code.substring(0, code.length() - 6);
		}
	}

	/**
	 * ��Ŀת��ʱ����ͳ�Ʊ��е��ڲ�����
	 */
	public static void updateInnerCode(Transaction tran, long siteID, String oldInnerCode, String newInnerCode) {
		// ����ͳ�Ʊ��еı���
		QueryBuilder qb = new QueryBuilder("where SiteID=? and type=? and item like ?");
		qb.add(siteID);
		qb.add("Catalog");
		qb.add(oldInnerCode + "%");// ���ڲ�����
		ZCStatItemSet statSet = new ZCStatItemSchema().query(qb);
		ZCStatItemSet childSet = new ZCStatItemSet();
		for (int i = 0; i < statSet.size(); i++) {
			String item = statSet.get(i).getItem();
			if (item.equals(oldInnerCode)) {
				childSet.add(statSet.get(i));
			}
			item = StringUtil.replaceEx(item, oldInnerCode, newInnerCode);// �滻���µ��ڲ�����
			statSet.get(i).setItem(item);
		}

		// ԭ���ĸ�����Ŀ
		ArrayList parentList = new ArrayList();
		ZCStatItemSet parentSet = null;
		String code = oldInnerCode;
		while (code.length() > 6) {
			code = code.substring(0, code.length() - 6);
			parentList.add(code);
		}
		if (parentList.size() > 0) {
			qb = new QueryBuilder("where SiteID=? and type=? and item in ('"
					+ StringUtil.join(parentList).replaceAll(",", "','") + "')");
			qb.add(siteID);
			qb.add("Catalog");
			parentSet = new ZCStatItemSchema().query(qb);
		}

		// �µĸ�����Ŀ
		parentList = new ArrayList();
		ZCStatItemSet newParentSet = null;
		code = newInnerCode;
		while (code.length() > 6) {
			code = code.substring(0, code.length() - 6);
			parentList.add(code);
		}
		if (parentList.size() > 0) {
			qb = new QueryBuilder("where SiteID=? and type=? and item in ('"
					+ StringUtil.join(parentList).replaceAll(",", "','") + "')");
			qb.add(siteID);
			qb.add("Catalog");
			newParentSet = new ZCStatItemSchema().query(qb);
		}
		// �ɵĸ�����Ŀ��ͳ�����б��������Ӧ��ֵ
		if (parentSet != null) {
			for (int i = 0; i < parentSet.size(); i++) {
				ZCStatItemSchema parent = parentSet.get(i);
				for (int j = 0; j < childSet.size(); j++) {
					ZCStatItemSchema child = childSet.get(j);
					if (child.getItem().endsWith("Index")) {
						continue;
					}
					if (child.getPeriod().equals(parent.getPeriod()) && child.getSubType().equals(parent.getSubType())) {
						for (int k = 5; k < child.getColumnCount() - 1; k++) {
							Long n1 = (Long) child.getV(k);
							Long n2 = (Long) parent.getV(k);
							long v = n2.longValue() - n1.longValue();
							if (v < 0) {
								v = 0;
							}
							parent.setV(k, new Long(v));// ��ȥ�����ߵ���Ŀ��ͳ��ֵ
						}
					}
				}
			}
		}
		// �µĸ�����Ŀ��ͳ�����б��������Ӧ��ֵ
		ZCStatItemSet noExistsParentSet = new ZCStatItemSet();
		if (newParentSet != null) {
			for (int j = 0; j < childSet.size(); j++) {
				ZCStatItemSchema child = childSet.get(j);
				if (child.getItem().endsWith("Index")) {
					continue;
				}
				ArrayList list = (ArrayList) parentList.clone();
				for (int i = 0; i < newParentSet.size(); i++) {
					ZCStatItemSchema parent = newParentSet.get(i);
					if (child.getPeriod().equals(parent.getPeriod()) && child.getSubType().equals(parent.getSubType())) {
						for (int k = 5; k < child.getColumnCount() - 1; k++) {
							Long n1 = (Long) child.getV(k);
							Long n2 = (Long) parent.getV(k);
							parent.setV(k, new Long(n2.longValue() + n1.longValue()));// ������������Ŀ��ͳ��ֵ
						}
						String item = parent.getItem();
						list.remove(item);
					}
				}
				for (int i = 0; i < list.size(); i++) {
					code = (String) list.get(i);
					ZCStatItemSchema si = (ZCStatItemSchema) child.clone();
					si.setItem(code);
					noExistsParentSet.add(si);
				}
			}
		}
		tran.add(noExistsParentSet, Transaction.DELETE_AND_INSERT);
		tran.add(parentSet, Transaction.UPDATE);
		tran.add(newParentSet, Transaction.UPDATE);
		tran.add(statSet, Transaction.UPDATE);
	}
}
