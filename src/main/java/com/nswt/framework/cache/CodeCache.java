package com.nswt.framework.cache;

import com.nswt.framework.Config;
import com.nswt.framework.data.DataRow;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;

/**
 * @Author NSWT
 * @Date 2008-10-30
 * @Mail nswt@nswt.com.cn
 */
public class CodeCache extends CacheProvider {
	public String getProviderName() {
		return "Code";
	}

	public void onKeyNotFound(String type, Object key) {
		if (!"true".equals(Config.getValue("App.ExistPlatformDB"))) {
			return;
		}
		// 不需要做操作,单项代码不可能超过20000行
		Object value = new QueryBuilder("select CodeName from ZDCode where CodeType=? and CodeValue=? order by CodeOrder,CodeValue", type, key)
				.executeOneValue();
		CacheManager.set(this.getProviderName(), type, key, value);
	}

	public void onTypeNotFound(String type) {
		if (!"true".equals(Config.getValue("App.ExistPlatformDB"))) {
			return;
		}
		DataTable dt = new QueryBuilder("select CodeType,ParentCode,CodeName,CodeValue from ZDCode where CodeType=? order by CodeOrder,CodeValue",
				type).executeDataTable();
		for (int i = 0; i < dt.getRowCount(); i++) {
			DataRow dr = dt.getDataRow(i);
			String codetype = dr.getString("CodeType");
			String parentcode = dr.getString("ParentCode");
			if(parentcode.equals("System")){
				continue;
			}
			CacheManager.set(this.getProviderName(), codetype, dr.get("CodeValue"), dr.get("CodeName"));
		}
	}
}
