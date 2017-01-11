package com.nswt.cms.document;

import com.nswt.framework.Page;
import com.nswt.framework.controls.DataGridAction;
import com.nswt.framework.controls.HtmlTable;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.DataTableUtil;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.utility.StringUtil;

public class ArticleDeployCatalog extends Page {
	public static void articleDialogDataBind(DataGridAction dga) {
		String innerCode = dga.getParam("CatalogInnerCode");
		String data = new QueryBuilder("select targetCatalog from ZCInnerDeploy where CatalogInnerCode=?", innerCode)
				.executeString();
		DataTable dt = null;
		if (StringUtil.isEmpty(data)) {
			dt = new DataTable();
			dt.insertColumn("ServerAddr");
			dt.insertColumn("SiteID");
			dt.insertColumn("SiteName");
			dt.insertColumn("CatalogID");
			dt.insertColumn("CatalogName");
			dt.insertColumn("Password");
		} else {
			dt = DataTableUtil.txtToDataTable(data, (String[]) null, "\t", "\n");
		}
		if (dt.getDataColumn("MD5") == null) {
			dt.insertColumn("MD5");
		}
		for (int i = 0; i < dt.getRowCount(); i++) {
			dt.set(i, "MD5", StringUtil.md5Hex(dt.getString(i, "ServerAddr") + "," + dt.getString(i, "SiteID") + ","
					+ dt.getString(i, "CatalogID")));
		}
		dga.bindData(dt);
		HtmlTable table = dga.getTable();
		for (int i = 1; i < table.getChildren().size(); i++) {
			table.getTR(i).removeAttribute("onclick");// Ĭ�ϵ���һ�������е�ѡ��״̬ȡ������Ҫȥ��
		}
	}
}
