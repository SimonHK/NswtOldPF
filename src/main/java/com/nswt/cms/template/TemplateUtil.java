package com.nswt.cms.template;

import com.nswt.cms.pub.CatalogUtil;
import com.nswt.cms.pub.SiteUtil;

/**
 * 日期 : 2010-6-29 <br>
 * 作者: NSWT <br>
 * 邮箱：nswt@nswt.com.cn <br>
 */
public class TemplateUtil {
	public static String getCatalogNames(String innerCode) {
		return getCatalogNames(innerCode, "/");
	}

	public static String getCatalogNames(String innerCode, String spliter) {
		return getCatalogNames(innerCode, spliter, true, true);
	}

	public static String getCatalogNames(String innerCode, String spliter, boolean descFlag, boolean siteNameFlag) {
		StringBuffer sb = new StringBuffer();
		if (descFlag) {
			while (innerCode.length() > 0) {
				sb.append(CatalogUtil.getNameByInnerCode(innerCode));
				sb.append(spliter);
				innerCode = innerCode.substring(0, innerCode.length() - 6);
			}
		} else {
			int length = 6;
			while (length <= innerCode.length()) {
				sb.append(CatalogUtil.getNameByInnerCode(innerCode.substring(0, length)));
				sb.append(spliter);
				length += 6;
			}
		}
		if (siteNameFlag) {
			sb.append(SiteUtil.getName(CatalogUtil.getSiteIDByInnerCode(innerCode)));
			return sb.toString();
		} else {
			return sb.substring(0, sb.length() - spliter.length());
		}
	}
}
