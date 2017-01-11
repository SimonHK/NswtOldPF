package com.nswt.cms.dataservice;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nswt.framework.Ajax;
import com.nswt.framework.User;
import com.nswt.framework.controls.DataListAction;
import com.nswt.framework.controls.HtmlTD;
import com.nswt.framework.controls.HtmlTR;
import com.nswt.framework.data.DataAccess;
import com.nswt.framework.data.DataColumn;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.utility.DateUtil;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.pub.OrderUtil;
import com.nswt.schema.ZCCustomTableColumnSchema;
import com.nswt.schema.ZCCustomTableColumnSet;
import com.nswt.schema.ZCCustomTableSchema;
import com.nswt.schema.ZCCustomTableSet;

/**
 * ���� : 2009-12-18 <br>
 * ����: NSWT <br>
 * ���䣺nswt@nswt.com.cn <br>
 */
public class CustomTableAjax extends Ajax {
	public void dataBindAllColumns(DataListAction dla) {
		ZCCustomTableSchema table = new ZCCustomTableSchema();
		table.setSiteID($V("SiteID"));
		table.setCode($V("TableCode"));
		ZCCustomTableSet set = table.query();
		if (set == null || set.size() < 1) {
			LogUtil.warn("IDΪ" + table.getSiteID() + "��վ����û�д���Ϊ" + table.getCode() + "�ı�!");
			return;
		} else {
			table = set.get(0);
			if (!"Y".equals(table.getAllowView())) {
				LogUtil.warn("IDΪ" + table.getSiteID() + "��վ���´���Ϊ" + table.getCode() + "�ı�����ǰ̨�鿴!");
				return;
			}
			DataTable dt = CustomTableUtil.getData(set.get(0), null, dla.getPageSize(), dla.getPageIndex());
			ZCCustomTableColumnSet cset = new ZCCustomTableColumnSchema().query(new QueryBuilder("where TableID=?",
					table.getID()));
			HtmlTR tr = new HtmlTR();
			ArrayList list = new ArrayList();
			for (int i = 0; i < cset.size(); i++) {
				HtmlTD td = new HtmlTD();
				td.setInnerHTML(cset.get(i).getName());
				tr.addTD(td);
			}
			list.add(tr);
			for (int i = 0; i < dt.getRowCount(); i++) {
				tr = new HtmlTR();
				for (int j = 0; j < dt.getColCount(); j++) {
					HtmlTD td = new HtmlTD();
					td.setInnerHTML(dt.getString(i, j));
					tr.addTD(td);
				}
				list.add(tr);
			}
			dt = new DataTable();
			dt.insertColumn("RowHTML");
			for (int i = 0; i < list.size(); i++) {
				tr = (HtmlTR) list.get(i);
				dt.insertRow(new Object[] { tr.getOuterHtml() });
			}
			dla.setTotal(CustomTableUtil.getTotal(table, "where 1=1"));
			dla.bindData(dt);
		}
	}

	public void dataBindSpecifiedColumns(DataListAction dla) {
		ZCCustomTableSchema table = new ZCCustomTableSchema();
		table.setSiteID($V("SiteID"));
		table.setCode($V("TableCode"));
		ZCCustomTableSet set = table.query();
		if (set == null || set.size() < 1) {
			LogUtil.warn("IDΪ" + table.getSiteID() + "��վ����û�д���Ϊ" + table.getCode() + "�ı�!");
			return;
		} else {
			table = set.get(0);
			if (!"Y".equals(table.getAllowView())) {
				LogUtil.warn("IDΪ" + table.getSiteID() + "��վ���´���Ϊ" + table.getCode() + "�ı�����ǰ̨�鿴!");
				return;
			}
			DataTable dt = CustomTableUtil.getData(set.get(0), null, dla.getPageSize(), dla.getPageIndex());
			dla.setTotal(CustomTableUtil.getTotal(table, "where 1=1"));
			dla.bindData(dt);
		}
	}

	public void processSubmit() {
		ZCCustomTableSchema table = new ZCCustomTableSchema();
		table.setID($V("_TableID"));
		if (!table.fill()) {
			LogUtil.warn("û��IDΪ" + table.getCode() + "�ı�!");
			return;
		} else {
			if (!"Y".equals(table.getAllowModify()) && !User.isManager()) {
				LogUtil.warn("IDΪ" + table.getID() + "�ı�����ǰ̨�޸�!");
				return;
			}
		}
		DataAccess da = null;
		String code = table.getCode();
		if (table.getType().equals("Link")) {
			da = new DataAccess(OuterDatabase.getConnection(table.getDatabaseID()));
			code = table.getOldCode();
		} else {
			da = new DataAccess();
		}
		try {
			da.setAutoCommit(false);
			ZCCustomTableColumnSet set = new ZCCustomTableColumnSchema().query(new QueryBuilder("where TableID=?",
					table.getID()));
			StringBuffer sb = new StringBuffer("insert into " + code + "(");
			for (int j = 0; j < set.size(); j++) {
				if (j != 0) {
					sb.append(",");
				}
				sb.append(set.get(j).getCode());
			}
			sb.append(") values (");
			for (int j = 0; j < set.size(); j++) {
				if (j != 0) {
					sb.append(",");
				}
				sb.append("?");
			}
			sb.append(")");
			QueryBuilder qb = new QueryBuilder(sb.toString());
			StringBuffer messageSB = new StringBuffer();
			for (int j = 0; j < set.size(); j++) {
				ZCCustomTableColumnSchema column = set.get(j);
				String v = $V(Form.FieldPrefix + set.get(j).getCode());
				if (StringUtil.isEmpty(v)) {
					v = null;
					if ("Y".equals(set.get(j).getIsAutoID())) {
						v = String.valueOf(OrderUtil.getDefaultOrder());
					}
				}
				if ("Y".equals(column.getIsMandatory()) || "Y".equals(column.getIsPrimaryKey())) {
					if (StringUtil.isEmpty(v)) {
						messageSB.append(column.getName() + "����Ϊ��!\n");
					}
				}
				int dataType = Integer.parseInt(column.getDataType());
				if (v != null) {
					if (column.getMaxLength() != 0 && v.length() < column.getMaxLength()) {
						messageSB.append(column.getName() + "���ݹ������������" + column.getMaxLength() + "���ַ�!\n");
					}
					try {
						if (dataType == DataColumn.DATETIME) {
							v = DateUtil.toDateTimeString(DateUtil.parseDateTime(v));
							if (v == null) {
								throw new SQLException("����ʱ�����");
							}
						}
						if (dataType == DataColumn.INTEGER || dataType == DataColumn.SMALLINT) {
							v = String.valueOf(new Double(Double.parseDouble(v)).intValue());
						}
						if (dataType == DataColumn.LONG) {
							v = String.valueOf(new Double(Double.parseDouble(v)).longValue());
						}
						if (dataType == DataColumn.FLOAT) {
							v = String.valueOf(new Double(Double.parseDouble(v)).floatValue());
						}
						if (dataType == DataColumn.DECIMAL || dataType == DataColumn.DOUBLE
								|| dataType == DataColumn.BIGDECIMAL) {
							v = String.valueOf(Double.parseDouble(v));
						}
					} catch (Exception e) {
						messageSB.append(column.getName() + "���ݲ���ȷ!\n");
					}
				}
				if (dataType == DataColumn.LONG) {
					qb.add(Long.parseLong(v));
				} else if (dataType == DataColumn.INTEGER || dataType == DataColumn.SMALLINT) {
					qb.add(Integer.parseInt(v));
				} else if (dataType == DataColumn.FLOAT) {
					qb.add(new Float(v));
				} else if (dataType == DataColumn.DECIMAL || dataType == DataColumn.DOUBLE) {
					qb.add(new Double(v));
				} else {
					qb.add(v);
				}
			}
			if (messageSB.length() != 0) {
				Response.setError(messageSB.toString());
			} else {
				da.executeNoQuery(qb);
				da.commit();
				Response.setMessage("�ύ�ɹ�!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				da.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			Response.setMessage("�ύʧ��:" + e.getMessage());
		} finally {
			try {
				da.setAutoCommit(true);
				da.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void processSubmit(HttpServletRequest request, HttpServletResponse response) {
		ZCCustomTableSchema table = new ZCCustomTableSchema();
		table.setID(request.getParameter("_TableID"));
		if (!table.fill()) {
			LogUtil.warn("û��IDΪ" + table.getCode() + "�ı�!");
			return;
		} else {
			if (!"Y".equals(table.getAllowModify()) && !User.isManager()) {
				LogUtil.warn("IDΪ" + table.getID() + "�ı�����ǰ̨�޸�!");
				return;
			}
		}
		DataAccess da = null;
		String code = table.getCode();
		if (table.getType().equals("Link")) {
			da = new DataAccess(OuterDatabase.getConnection(table.getDatabaseID()));
			code = table.getOldCode();
		} else {
			da = new DataAccess();
		}
		boolean doDeleteQB = true;
		try {
			da.setAutoCommit(false);
			ZCCustomTableColumnSet set = new ZCCustomTableColumnSchema().query(new QueryBuilder("where TableID=?",
					table.getID()));
			StringBuffer insertSB = new StringBuffer("insert into " + code + "(");
			QueryBuilder deleteQB = new QueryBuilder("delete from  " + code + " where 1=1 ");
			for (int j = 0; j < set.size(); j++) {
				if (j != 0) {
					insertSB.append(",");
				}
				insertSB.append(set.get(j).getCode());
			}
			insertSB.append(") values (");
			for (int j = 0; j < set.size(); j++) {
				if (j != 0) {
					insertSB.append(",");
				}
				insertSB.append("?");
				if ("Y".equals(set.get(j).getIsPrimaryKey())) {
					String v = request.getParameter(Form.FieldPrefix + set.get(j).getCode() + "_Old");
					deleteQB.append(" and " + set.get(j).getCode() + "=?");
					if (v != null) {
						int dataType = Integer.parseInt(set.get(j).getDataType());
						if (dataType == DataColumn.LONG) {
							deleteQB.add(Long.parseLong(v));
						} else if (dataType == DataColumn.INTEGER || dataType == DataColumn.SMALLINT) {
							deleteQB.add(Integer.parseInt(v));
						} else if (dataType == DataColumn.FLOAT) {
							deleteQB.add(new Float(v));
						} else if (dataType == DataColumn.DECIMAL || dataType == DataColumn.DOUBLE) {
							deleteQB.add(new Double(v));
						} else {
							deleteQB.add(v);
						}
					} else {
						doDeleteQB = false;
					}
				}
			}
			insertSB.append(")");
			QueryBuilder qb = new QueryBuilder(insertSB.toString());
			StringBuffer messageSB = new StringBuffer();
			for (int j = 0; j < set.size(); j++) {
				ZCCustomTableColumnSchema column = set.get(j);
				String v = request.getParameter(Form.FieldPrefix + set.get(j).getCode());
				if (StringUtil.isEmpty(v)) {
					v = null;
					if ("Y".equals(set.get(j).getIsAutoID())) {
						v = String.valueOf(OrderUtil.getDefaultOrder());
					}
				}
				if ("Y".equals(column.getIsMandatory()) || "Y".equals(column.getIsPrimaryKey())) {
					if (StringUtil.isEmpty(v)) {
						messageSB.append(column.getName() + "����Ϊ��!\\n");
					}
				}
				int dataType = Integer.parseInt(column.getDataType());
				if (v != null) {
					if (column.getMaxLength() != 0 && v.length() < column.getMaxLength()) {
						messageSB.append(column.getName() + "���ݹ������������" + column.getMaxLength() + "���ַ�!\\n");
					}
					try {
						if (dataType == DataColumn.DATETIME) {
							v = DateUtil.toDateTimeString(DateUtil.parseDateTime(v));
							if (v == null) {
								throw new SQLException("����ʱ�����");
							}
						}
						if (dataType == DataColumn.INTEGER || dataType == DataColumn.SMALLINT) {
							v = String.valueOf(new Double(Double.parseDouble(v)).intValue());
						}
						if (dataType == DataColumn.LONG) {
							v = String.valueOf(new Double(Double.parseDouble(v)).longValue());
						}
						if (dataType == DataColumn.FLOAT) {
							v = String.valueOf(new Double(Double.parseDouble(v)).floatValue());
						}
						if (dataType == DataColumn.DECIMAL || dataType == DataColumn.DOUBLE
								|| dataType == DataColumn.BIGDECIMAL) {
							v = String.valueOf(Double.parseDouble(v));
						}
					} catch (Exception e) {
						messageSB.append(column.getName() + "���ݲ���ȷ!\\n");
					}
				}
				if (dataType == DataColumn.LONG) {
					qb.add(Long.parseLong(v));
				} else if (dataType == DataColumn.INTEGER || dataType == DataColumn.SMALLINT) {
					qb.add(Integer.parseInt(v));
				} else if (dataType == DataColumn.FLOAT) {
					qb.add(new Float(v));
				} else if (dataType == DataColumn.DECIMAL || dataType == DataColumn.DOUBLE) {
					qb.add(new Double(v));
				} else {
					qb.add(v);
				}
			}
			if (messageSB.length() != 0) {
				insertSB = new StringBuffer();
				insertSB.append("<script>");
				insertSB.append("alert(\"" + messageSB + "\");");
				insertSB.append("history.go(-1);");
				insertSB.append("</script>");
				response.getWriter().print(insertSB);
			} else {
				if(doDeleteQB) {
					da.executeNoQuery(deleteQB);
				}
				da.executeNoQuery(qb);
				da.commit();
				insertSB = new StringBuffer();
				insertSB.append("<script>");
				insertSB.append("alert(\"�ύ�ɹ�!\");");
				insertSB.append("window.location=\"" + request.getHeader("referer") + "\";");
				insertSB.append("</script>");
				response.getWriter().print(insertSB);
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				da.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			StringBuffer sb = new StringBuffer();
			sb.append("<script>");
			sb.append("alert(\"�ύʧ��!\");");
			sb.append("history.go(-1);");
			sb.append("</script>");
			try {
				response.getWriter().print(sb);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				da.setAutoCommit(true);
				da.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
