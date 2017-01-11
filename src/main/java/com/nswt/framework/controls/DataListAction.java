package com.nswt.framework.controls;

import java.util.regex.Matcher;

import com.nswt.framework.Constant;
import com.nswt.framework.data.DBUtil;
import com.nswt.framework.data.DataColumn;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.utility.HtmlUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;

/**
 * @Author NSWT
 * @Date 2008-1-23
 * @Mail nswt@nswt.com.cn
 */
public class DataListAction implements IControlAction {

	private DataTable DataSource;

	private String ID;

	private String TagBody;

	private boolean page;

	protected Mapx Params = new Mapx();

	private String method;

	private int total;

	private int pageIndex;

	private int pageSize;

	boolean TotalFlag = false;// 是否需要重新计算记录总数

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Mapx getParams() {
		return Params;
	}

	public void setParams(Mapx params) {
		Params = params;
	}

	public String getParam(String key) {
		return Params.getString(key);
	}

	public void bindData(DataTable dt) {
		this.DataSource = dt;
		try {
			this.bindData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void bindData(SchemaSet set) {
		bindData(set.toDataTable());
	}

	private void bindData() throws Exception {
		if (DataSource.getDataColumn("_RowNo") == null) {
			DataSource.insertColumn(new DataColumn("_RowNo", DataColumn.INTEGER));
		}
		for (int j = 0; j < DataSource.getRowCount(); j++) {
			int rowNo = this.pageIndex * this.pageSize + j + 1;
			DataSource.set(j, "_RowNo", new Integer(rowNo));
		}
		// HtmlUtil.replaceWithDataTable(DataSource, this.TagBody);
	}

	public void bindData(QueryBuilder qb) {
		bindData(qb, this.page);
	}

	public void bindData(QueryBuilder qb, boolean pageFlag) {
		if (pageFlag) {
			if (!TotalFlag) {// 需要重新计算总数
				setTotal(DBUtil.getCount(qb));
			}
			bindData(qb.executePagedDataTable(this.pageSize, this.pageIndex));
		} else {
			bindData(qb.executeDataTable());
		}
	}

	public String getHtml() {
		StringBuffer sb = new StringBuffer();
		sb.append("<!--_NSWT_DATALIST_START_" + ID + "-->");
		sb.append("<input type='hidden' id='" + ID + "' method='" + method + "'>");
		sb.append(HtmlUtil.replaceWithDataTable(DataSource, this.TagBody, false));
		HtmlScript script = new HtmlScript();
		script.setAttribute("ztype", "DataList");// 防止代码中有多段script
		script.setInnerHTML(getScript());
		sb.append(script.getOuterHtml());
		sb.append("<!--_NSWT_DATALIST_END_" + ID + "-->");
		return sb.toString();
	}

	public String getScript() {
		StringBuffer sb = new StringBuffer();
		// 加入标签本身
		sb.append("$('" + ID + "').TagBody = \"" + StringUtil.htmlEncode(getTagBody().replaceAll("\\s+", " ")) + "\";");
		Object[] ks = Params.keyArray();
		Object[] vs = Params.valueArray();
		for (int i = 0; i < Params.size(); i++) {
			Object key = ks[i];
			if (!key.equals(Constant.TagBody) && vs[i] != null) {
				sb.append("DataList.setParam('" + ID + "','" + key + "',\"" + StringUtil.javaEncode(vs[i].toString())
						+ "\");");
			}
		}

		// 加入分页条
		if (this.page) {
			int type = 1;
			if (StringUtil.isNotEmpty(Params.getString("PageBarType"))) {
				type = Integer.parseInt(Params.getString("PageBarType"));
			}
			String html = PageBarTag.getPageBarHtml(ID, type, total, pageSize, pageIndex);
			sb.append("\ntry{$('_PageBar_" + ID + "').outerHTML=\"" + StringUtil.javaEncode(html) + "\";}catch(ex){}\n");
		}

		sb.append("DataList.setParam('" + ID + "','" + Constant.DataGridPageIndex + "'," + this.pageIndex + ");");
		sb.append("DataList.setParam('" + ID + "','" + Constant.DataGridPageTotal + "'," + this.total + ");");
		sb.append("DataList.setParam('" + ID + "','" + Constant.Page + "'," + this.page + ");");
		sb.append("DataList.setParam('" + ID + "','" + Constant.Size + "'," + this.pageSize + ");");
		sb.append("");
		sb.append("DataList.init('" + ID + "');");
		String content = sb.toString();
		Matcher matcher = Constant.PatternField.matcher(content);
		sb = new StringBuffer();
		int lastEndIndex = 0;
		while (matcher.find(lastEndIndex)) {
			sb.append(content.substring(lastEndIndex, matcher.start()));
			sb.append("$\\{");
			sb.append(matcher.group(1));
			sb.append("}");
			lastEndIndex = matcher.end();
		}
		sb.append(content.substring(lastEndIndex));

		return sb.toString();

	}

	public String getTagBody() {
		return TagBody;
	}

	public void setTagBody(String tagBody) {
		TagBody = tagBody;
	}

	public DataTable getDataSource() {
		return DataSource;
	}

	public String getID() {
		return ID;
	}

	public void setID(String id) {
		ID = id;
	}

	public boolean isPage() {
		return page;
	}

	public void setPage(boolean page) {
		this.page = page;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
		if (pageIndex > Math.ceil(total * 1.0 / pageSize)) {
			pageIndex = new Double(Math.floor(total * 1.0 / pageSize)).intValue();
		}
		TotalFlag = true;
	}

	public void setTotal(QueryBuilder qb) {
		if (this.pageIndex == 0) {
			setTotal(DBUtil.getCount(qb));
		}
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
		/* ${_NSWT_LICENSE_CODE_} */
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}
