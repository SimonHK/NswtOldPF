package com.nswt.cms.stat.impl;

import com.nswt.cms.stat.VisitCount.ItemValue;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.utility.ExitEventListener;

/**
 * @Author NSWT
 * @Date 2016-5-3
 * @Mail nswt@nswt.com.cn
 */
public class LeafExitEventListener extends ExitEventListener {
	protected String type;

	protected String subType;

	protected Object[][] arr = new Object[50][2];

	protected int index = 0;

	public LeafExitEventListener(String type, String subType) {
		this.type = type;
		this.subType = subType;
	}

	public void onExit(Object key, Object value) {
		synchronized (this) {
			arr[index][0] = key;
			arr[index][1] = value;
			if (index == 49) {
				update();
				index = 0;
			} else {
				index++;
			}
		}
	}

	public void update() {
		QueryBuilder qb = null;
		if (subType.equals("PV")) {
			qb = new QueryBuilder("update ZC" + type + " set HitCount=HitCount+? where ID=?");
		} else {
			qb = new QueryBuilder("update ZC" + type + " set StickTime=(HitCount*StickTime+?)/(HitCount+?) where ID=?");
		}
		qb.setBatchMode(true);
		for (int i = 0; i < arr.length; i++) {
			ItemValue v = (ItemValue) arr[i][1];
			if (subType.equals("PV")) {
				qb.add(v.Count);
				qb.add(arr[i][0]);
			} else {
				qb.add(v.Count);
				qb.add(v.Divisor);
				qb.add(arr[i][0]);
			}
			qb.addBatch();
		}
		qb.executeNoQuery();
	}
}
