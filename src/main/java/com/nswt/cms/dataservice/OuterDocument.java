package com.nswt.cms.dataservice;

import com.nswt.framework.Config;
import com.nswt.framework.Page;
import com.nswt.framework.controls.DataGridAction;
import com.nswt.framework.controls.TreeAction;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.utility.StringUtil;

/**
 * ��ȡ�ⲿ���� ��������CMSϵͳ��BBS��blog�� 2008-6-17
 * 
 * @author lanjun
 * 
 */
public class OuterDocument extends Page {

	public static void bbstreeDataBind(TreeAction ta) {
		DataTable dt = CustomTableUtil.getData("cdb_forums");
		ta.setIdentifierColumnName("fid");
		ta.setParentIdentifierColumnName("fup");
		ta.setRootText("��̳");
		ta.bindData(dt);
	}

	public static void bbsDataBind(DataGridAction dga) {
		String fid = (String) dga.getParams().get("fid");
		String keyword = (String) dga.getParams().get("Keyword");

		String condtion = "";
		if (StringUtil.isNotEmpty(keyword)) {
			condtion = " and subject like '%" + keyword.trim() + "%'";
		}
		String wherePart = "where fid=" + fid + condtion + " order by lastPost desc";
		DataTable dt = CustomTableUtil.getData("cdb_threads", new QueryBuilder(wherePart), dga.getPageSize(), dga
				.getPageIndex());
		dt.insertColumn("Title");
		dt.insertColumn("Link");
		String bbsURL = Config.getValue("BBS.URL");
		for (int i = 0; i < dt.getRowCount(); i++) {
			dt.set(i, "Title", dt.get(i, "Subject"));
			dt.set(i, "Link", bbsURL + "thread-" + dt.get(i, "tid") + "-1-1.html");
		}
		dga.setTotal(CustomTableUtil.getTotal("cdb_threads", wherePart));
		dga.bindData(dt);
	}

	public static void cmsTreeDataBind(TreeAction ta) {
		DataTable dt = CustomTableUtil.getData("cms_channel", new QueryBuilder(
				"where id in (199,218,229) or parent in (199,218,229)"));
		ta.setIdentifierColumnName("id");
		ta.setParentIdentifierColumnName("parent");
		ta.setRootText("CMS");
		ta.bindData(dt);
	}

	public static void cmsDataBind(DataGridAction dga) {
		String columnID = (String) dga.getParams().get("id");
		String keyword = (String) dga.getParams().get("Keyword");

		String condtion = "";
		if (StringUtil.isNotEmpty(keyword)) {
			condtion = "  and keyword like '%" + keyword.trim() + "%'";
		}
		String wherePart = "where (columnid=" + columnID + " or columnid in(select id from cms_channel where parent="
				+ columnID + "))" + condtion + " order by publishdate desc";
		DataTable dt = CustomTableUtil.getData("cms_content", new QueryBuilder(wherePart), dga.getPageSize(), dga
				.getPageIndex());
		// dt.insertColumn("Title");
		dt.insertColumn("Link");
		String cmsURL = Config.getValue("Uncars.URL");
		for (int i = 0; i < dt.getRowCount(); i++) {
			dt.set(i, "Link", cmsURL + dt.get(i, "url"));
		}
		dga.setTotal(CustomTableUtil.getTotal("cms_content", "where (columnid=" + columnID
				+ " or columnid in(select id from cms_channel where parent=" + columnID + "))" + condtion));
		dga.bindData(dt);
	}

}
