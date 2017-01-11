package com.nswt.framework.controls;

import java.lang.reflect.Method;

import com.nswt.framework.Ajax;
import com.nswt.framework.Constant;
import com.nswt.framework.utility.StringUtil;

/**
 * @Author NSWT
 * @Date 2008-1-28
 * @Mail nswt@nswt.com.cn
 */
public class DataListPage extends Ajax {
	public void doWork() {
		try {
			DataListAction dla = new DataListAction();

			dla.setTagBody(StringUtil.htmlDecode($V(Constant.TagBody)));
			dla.setPage("true".equalsIgnoreCase($V(Constant.Page)));
			String method = $V(Constant.Method);
			dla.setMethod(method);

			dla.setID($V(Constant.ID));
			dla.setParams(Request);
			dla.setPageSize(Integer.parseInt($V(Constant.Size)));
			if (dla.isPage()) {
				dla.setPageIndex(0);
				if (Request.get(Constant.DataGridPageIndex) != null
						&& !Request.get(Constant.DataGridPageIndex).equals("")) {
					dla.setPageIndex(Integer.parseInt(Request.get(Constant.DataGridPageIndex).toString()));
				}
				if (dla.getPageIndex() < 0) {
					dla.setPageIndex(0);
				}
				if (Request.get(Constant.DataGridPageTotal) != null
						&& !Request.get(Constant.DataGridPageTotal).equals("")) {
					dla.setTotal(Integer.parseInt(Request.get(Constant.DataGridPageTotal).toString()));
					if (dla.getPageIndex() > Math.ceil(dla.getTotal() * 1.0 / dla.getPageSize())) {
						dla.setPageIndex(new Double(Math.floor(dla.getTotal() * 1.0 / dla.getPageSize())).intValue());
					}
				}
			}
			int index = method.lastIndexOf('.');
			String className = method.substring(0, index);
			method = method.substring(index + 1);
			Class c = Class.forName(className);
			Method m = c.getMethod(method, new Class[] { DataListAction.class });
			m.invoke(null, new Object[] { dla });

			$S("HTML", dla.getHtml());
		} catch (Exception e) {
			e.printStackTrace();
		}

		/* ${_NSWT_LICENSE_CODE_} */
	}
}
