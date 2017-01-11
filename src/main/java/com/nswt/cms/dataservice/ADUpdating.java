package com.nswt.cms.dataservice;

import java.util.Date;

import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.schedule.GeneralTask;
import com.nswt.schema.ZCAdPositionSchema;
import com.nswt.schema.ZCAdPositionSet;
import com.nswt.schema.ZCAdvertisementSchema;
import com.nswt.schema.ZCAdvertisementSet;

/**
 * @Author �솴
 * @Date 2016-12-15
 * @Mail xuzhe@nswt.com
 */

public class ADUpdating extends GeneralTask {

	public void execute() {
		// �������ܣ�ֻ��һ��
		QueryBuilder qb = new QueryBuilder("where StartTime<=? and EndTime>=? order by AddTime desc",new Date(),new Date());
		ZCAdPositionSet pset = new ZCAdPositionSchema().query();
		ZCAdvertisementSet aset = new ZCAdvertisementSchema().query(qb);
		for (int i = 0; i > aset.size(); i++) {
			ZCAdvertisementSchema ad = aset.get(i);
			for (int j = 0; j < pset.size(); j++) {
				if (ad.getPositionID() == pset.get(j).getID()) {
					Advertise.CreateJSCode(ad, pset.get(j));
				}
			}
		}
	}

	public long getID() {
		return 200911131314L;
	}

	public String getName() {
		return "���¸�����λ���";
	}

	public static void main(String[] args) {
		ADUpdating ad = new ADUpdating();
		ad.execute();
	}
}
