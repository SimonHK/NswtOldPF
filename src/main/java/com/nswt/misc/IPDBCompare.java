package com.nswt.misc;

import com.nswt.cms.stat.StatUtil;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.utility.Mapx;

/**
 * @Author 王育春
 * @Date 2009-5-20
 * @Mail nswt@nswt.com.cn
 */
public class IPDBCompare {

	public static void main(String[] args) {
		String district = StatUtil.getDistrictCode("219.234.128.126");
		System.out.println(district);
		Mapx map =
				new QueryBuilder("select code,name from ZDDistrict where code like '11%' or code like '12%' or code like '31%' "
						+ "or code like '50%' or TreeLevel<3").executeDataTable().toMapx(0, 1);

		if (!district.startsWith("00") && !district.endsWith("0000")) {
			String prov = map.getString(district.substring(0, 2) + "0000");
			if (prov.startsWith("黑龙江") || prov.startsWith("内蒙古")) {
				prov = prov.substring(0, 3);
			} else {
				prov = prov.substring(0, 2);
			}
			String city = map.getString(district);
			city = city == null ? "" : city;
			district = prov + city;
		}
		System.out.println(district);
	}
}
