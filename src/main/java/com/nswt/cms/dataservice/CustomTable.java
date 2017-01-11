package com.nswt.cms.dataservice;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nswt.generator.GenUtil;
import com.nswt.generator.MyBGenerator;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.nswt.framework.Config;
import com.nswt.framework.Constant;
import com.nswt.framework.Page;
import com.nswt.framework.User;
import com.nswt.framework.controls.DataGridAction;
import com.nswt.framework.controls.DataListAction;
import com.nswt.framework.controls.HtmlTD;
import com.nswt.framework.controls.HtmlTR;
import com.nswt.framework.controls.HtmlTable;
import com.nswt.framework.data.DBConnConfig;
import com.nswt.framework.data.DBConnPool;
import com.nswt.framework.data.DBUtil;
import com.nswt.framework.data.DataAccess;
import com.nswt.framework.data.DataColumn;
import com.nswt.framework.data.DataRow;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.DataTableUtil;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.orm.TableCreator;
import com.nswt.framework.utility.CaseIgnoreMapx;
import com.nswt.framework.utility.DateUtil;
import com.nswt.framework.utility.FileUtil;
import com.nswt.framework.utility.HtmlUtil;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.Application;
import com.nswt.platform.pub.NoUtil;
import com.nswt.schema.ZCCustomTableColumnSchema;
import com.nswt.schema.ZCCustomTableColumnSet;
import com.nswt.schema.ZCCustomTableSchema;
import com.nswt.schema.ZCCustomTableSet;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

/**
 * @Author NSWT
 * @Date 2008-4-23
 * @Mail nswt@nswt.com.cn
 */
public class CustomTable extends Page {
	public static final Mapx DBTypeMap = new Mapx();

	static {
		DBTypeMap.put("" + DataColumn.STRING, "�ı���");
		DBTypeMap.put("" + DataColumn.INTEGER, "����");
		DBTypeMap.put("" + DataColumn.SMALLINT, "������");
		DBTypeMap.put("" + DataColumn.LONG, "������");
		DBTypeMap.put("" + DataColumn.DATETIME, "����");
		DBTypeMap.put("" + DataColumn.FLOAT, "������");
		DBTypeMap.put("" + DataColumn.DOUBLE, "˫����");
		DBTypeMap.put("" + DataColumn.CLOB, "���ı�");
	}

	public static Mapx init(Mapx map) {
		return map;
	}

	public static Mapx initColumn(Mapx map) {
		String html = HtmlUtil.mapxToOptions(DBTypeMap);
		map.put("DataTypeOptions", html);

		Mapx imap = new Mapx();
		imap.put("T", "�ı���");
		imap.put("S", "������");
		imap.put("R", "��ѡ��");
		imap.put("C", "��ѡ��");
		imap.put("D", "����ѡ���");
		imap.put("E", "���ں�ʱ��ѡ���");
		imap.put("A", "�����ı���");

		map.put("InputTypeOptions", HtmlUtil.mapxToOptions(imap));
		return map;
	}

	public static void dg1DataBind(DataGridAction dga) {
		Mapx dbMap = new QueryBuilder("select ID,Name from ZCDataBase where SiteID=?", Application.getCurrentSiteID())
				.executeDataTable().toMapx("ID", "Name");
		DataTable dt = new QueryBuilder("select * from ZCCustomTable where SiteID=?", Application.getCurrentSiteID())
				.executeDataTable();
		Mapx map = new Mapx();
		map.put("Link", "�ⲿ���ر�");
		map.put("Custom", "�Զ����");
		dt.decodeColumn("Type", map);
		dt.decodeColumn("DataBaseID", dbMap);
		dga.bindData(dt);
	}

	public static void columnDataBind(DataGridAction dga) {
		String TableID = dga.getParam("TableID");
		if (StringUtil.isEmpty(TableID)) {
			DataTable dt = new QueryBuilder(
					"select Code,Code as OldCode,Name,DataType,ListOptions,isAutoID,InputType,Length,isMandatory,isPrimaryKey "
							+ "from ZCCustomTableColumn where TableID=0").executeDataTable();
			dga.bindData(dt);
			return;
		}
		DataTable dt = null;
		dt = new QueryBuilder(
				"select Code,Code as OldCode,Name,DataType,ListOptions,isAutoID,InputType,Length,isMandatory,isPrimaryKey "
						+ "from ZCCustomTableColumn where TableID=?", Long.parseLong(TableID)).executeDataTable();
		for (int i = 0; i < dt.getRowCount(); i++) {
			String isMandatory = dt.getString(i, "isMandatory");
			String isPrimaryKey = dt.getString(i, "isPrimaryKey");
			String isAutoID = dt.getString(i, "isAutoID");
			if ("Y".equals(isMandatory)) {
				dt.set(i, "isMandatory", "checked='true'");
			}
			if ("Y".equals(isPrimaryKey)) {
				dt.set(i, "isPrimaryKey", "checked='true'");
			}
			if ("Y".equals(isAutoID)) {
				dt.set(i, "isAutoID", "checked='true'");
			}
		}
		dga.bindData(dt);
	}

	public void del() {
		String ids = $V("IDs");
		if (ids.indexOf("\"") >= 0 || ids.indexOf("\'") >= 0) {
			Response.setStatus(0);
			Response.setMessage("����IDʱ��������!");
			return;
		}
		Transaction tran = new Transaction();
		ZCCustomTableSet set = new ZCCustomTableSchema().query(new QueryBuilder("where SiteID=? and ID in (" + ids
				+ ")", Application.getCurrentSiteID()));
		tran.add(new QueryBuilder("delete from ZCCustomTable where SiteID=? and ID in (" + ids + ")", Application
				.getCurrentSiteID()));
		tran.add(new QueryBuilder("delete from ZCCustomTableColumn where TableID in (" + ids + ")"));
		if (tran.commit()) {
			Response.setMessage("ɾ���ɹ�");
			for (int i = 0; i < set.size(); i++) {
				if (set.get(i).getType().equals("Custom")) {
					QueryBuilder qb = new QueryBuilder(TableCreator.dropTable(set.get(i).getCode(),
							DBConnPool.getDBConnConfig().DBType));
					try {
						qb.executeNoQueryWithException();
					} catch (Exception e) {
						LogUtil.warn("ɾ���Զ����ʱ�������󣬱���ܲ�����!");
					}
				}
			}
		} else {
			Response.setError("��������,ɾ��ʧ��");
		}
	}

	public void add() {
		DataAccess da = new DataAccess();
		try {
			da.setAutoCommit(false);
			ZCCustomTableSchema table = new ZCCustomTableSchema();
			if (StringUtil.isEmpty($V("ID"))) {
				QueryBuilder qb = new QueryBuilder("select count(1) from ZCCustomTable where Code=?", $V("Code"));
				if (qb.executeInt() > 0) {
					Response.setError("�Ѿ�������ͬ���Զ����" + $V("Code"));
					return;
				}
				table.setValue(Request);
				table.setID(NoUtil.getMaxID("CustomTableID"));
				String allowView = table.getAllowView();
				if (StringUtil.isEmpty(allowView)) {
					table.setAllowView("N");
				}
				String allowModify = table.getAllowModify();
				if (StringUtil.isEmpty(allowModify)) {
					table.setAllowModify("N");
				}
				table.setAddTime(new Date());
				table.setAddUser(User.getUserName());
				table.setSiteID(Application.getCurrentSiteID());
				table.setDataAccess(da);
				table.insert();
			} else {
				table.setID(Long.parseLong($V("ID")));
				table.fill();
				table.setValue(Request);
				String allowView = table.getAllowView();
				if (StringUtil.isEmpty(allowView)) {
					table.setAllowView("N");
				}
				String allowModify = table.getAllowModify();
				if (StringUtil.isEmpty(allowModify)) {
					table.setAllowModify("N");
				}
				table.setModifyTime(new Date());
				table.setModifyUser(User.getUserName());
				table.setDataAccess(da);
				table.update();
			}
			ZCCustomTableColumnSet set = new ZCCustomTableColumnSet();
			if ($V("Type").equals("Link")) {
				DBConnConfig dcc = OuterDatabase.getDBConnConfig(table.getDatabaseID());
				DataTable dt = DBUtil.getColumnInfo(dcc, table.getOldCode());
				for (int i = 0; i < dt.getRowCount(); i++) {
					ZCCustomTableColumnSchema column = new ZCCustomTableColumnSchema();
					DataRow dr = dt.getDataRow(i);
					column.setID(NoUtil.getMaxID("CustomTableColumnID"));
					column.setAddTime(new Date());
					column.setAddUser(User.getUserName());
					column.setTableID(table.getID());
					column.setCode(dr.getString("Column_Name"));
					column.setName(dr.getString("Column_Name"));
					column.setDataType(dr.getString("Type_Name"));
					if (StringUtil.isNotEmpty(dr.getString("Column_Size"))) {
						column.setLength(Integer.parseInt(dr.getString("Column_Size")));
					}
					if (column.getDataType().equals(DataColumn.CLOB + "")) {
						column.setInputType("A");
					} else if (column.getDataType().equals(DataColumn.STRING + "") && column.getLength() > 1000) {
						column.setInputType("A");
					} else {
						column.setInputType("T");
					}
					column.setIsAutoID("N");
					column.setIsMandatory(dr.getString("Nullable").equals("0") ? "Y" : "N");
					column.setIsPrimaryKey(dr.getString("isKey"));
					set.add(column);
				}
			} else {
				DataTable dt = Request.getDataTable("Data");
				for (int i = 0; i < dt.getRowCount(); i++) {
					ZCCustomTableColumnSchema column = new ZCCustomTableColumnSchema();
					DataRow dr = dt.getDataRow(i);
					column.setID(NoUtil.getMaxID("CustomTableColumnID"));
					column.setAddTime(new Date());
					column.setAddUser(User.getUserName());
					column.setTableID(table.getID());
					column.setCode(dr.getString("Code"));
					column.setName(dr.getString("Name"));
					column.setDataType(dr.getString("DataType"));
					column.setInputType(dr.getString("InputType"));
					if (StringUtil.isNotEmpty(dr.getString("Length"))) {
						column.setLength(Integer.parseInt(dr.getString("Length")));
					}
					if (Integer.parseInt(column.getDataType()) != DataColumn.STRING) {
						column.setLength(null);
					}
					column.setIsMandatory("Y".equals(dr.getString("isMandatory")) ? "Y" : "N");
					column.setIsPrimaryKey("Y".equals(dr.getString("isPrimaryKey")) ? "Y" : "N");
					column.setIsAutoID("Y".equals(dr.getString("isAutoID")) ? "Y" : "N");
					if (column.getIsAutoID().equals("Y")) {
						column.setDataType("" + DataColumn.LONG);// �Զ���������Ϊ������
					}
					if (column.getIsPrimaryKey().equals("Y")) {
						column.setIsMandatory("Y");// ��������Ϊ��
					}
					if (column.getInputType().equals("T")) {
						if (column.getDataType().equals(DataColumn.CLOB + "")) {
							column.setInputType("A");
						} else if (column.getDataType().equals(DataColumn.STRING + "") && column.getLength() > 1000) {
							column.setInputType("A");
						}
					}
					column.setListOptions(dr.getString("ListOptions"));
					set.add(column);
				}
				if (StringUtil.isEmpty($V("ID"))) {
					createTable(da, table.getCode(), set);
				} else {
					ZCCustomTableColumnSchema ctc = new ZCCustomTableColumnSchema();
					ctc.setTableID(table.getID());
					ZCCustomTableColumnSet oldSet = ctc.query();
					oldSet.setDataAccess(da);
					oldSet.delete();

					// ��ȡӳ���ϵ
					CaseIgnoreMapx map = new CaseIgnoreMapx();
					for (int i = 0; i < dt.getRowCount(); i++) {
						if (StringUtil.isNotEmpty(dt.getString(i, "OldCode"))) {
							map.put(dt.getString(i, "Code"), dt.getString(i, "OldCode"));
						}
					}
					// ���ɾ���б�
					ArrayList deletedList = new ArrayList();
					for (int i = 0; i < set.size(); i++) {
						boolean flag = false;
						for (int j = 0; j < dt.getRowCount(); j++) {
							if (set.get(i).getCode().equals(dt.getString(j, "OldCode"))) {
								flag = true;
								break;
							}
						}
						if (!flag) {
							deletedList.add(set.get(i).getCode());

						}
					}
					try {
						modifyTable(da, table.getCode(), map, deletedList, set);
					} catch (Exception e) {
						e.printStackTrace();
						Response.setError("�޸ı�ʱ��������,����ʧ��:" + e.getMessage());
						return;
					}
				}
			}
			da.executeNoQuery(new QueryBuilder("delete from ZCCustomTableColumn where TableID=?", table.getID()));
			set.setDataAccess(da);
			set.insert();
			da.commit();
			Response.setMessage("����ɹ�");
		} catch (Exception e) {
			Response.setError("��������,����ʧ��:" + e.getMessage());
			try {
				da.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				da.setAutoCommit(false);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				da.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void modifyTable(DataAccess da, String tableCode, Mapx map, ArrayList deletedList,
			ZCCustomTableColumnSet set) throws Exception {
		SchemaColumn[] scs = toSchemaColumns(set);
		int count = 0;
		int size = 500;
		if ("true".equals(Config.getValue("App.MinimalMemory"))) {
			size = 100;
		}
		try {
			QueryBuilder qb = new QueryBuilder("select * from " + tableCode);
			count = DBUtil.getCount(qb);
			for (int i = 0; i < count * 1.0 / size; i++) {
				DataTable dt = qb.executePagedDataTable(size, i);
				FileUtil.serialize(dt, Config.getContextRealPath() + "/WEB-INF/data/_tmp_" + tableCode + "_" + i
						+ ".dat");
			}
		} catch (Exception e) {
			// �˴��������������ܲ�����
		}
		createTable(da, tableCode, set);
		for (int i = 0; i < count * 1.0 / size; i++) {
			String fileName = Config.getContextRealPath() + "/WEB-INF/data/_tmp_" + tableCode + "_" + i + ".dat";
			DataTable dt = (DataTable) FileUtil.unserialize(fileName);
			FileUtil.delete(fileName);
			StringBuffer sb = new StringBuffer("insert into " + tableCode + "(");
			for (int j = 0; j < scs.length; j++) {
				if (j != 0) {
					sb.append(",");
				}
				sb.append(scs[j].getColumnName());
			}
			sb.append(") values (");
			for (int j = 0; j < scs.length; j++) {
				if (j != 0) {
					sb.append(",");
				}
				sb.append("?");
			}
			sb.append(")");
			QueryBuilder qb = new QueryBuilder(sb.toString());
			qb.setBatchMode(true);
			for (int j = 0; j < dt.getRowCount(); j++) {
				for (int k = 0; k < scs.length; k++) {
					SchemaColumn sc = scs[k];
					String v = null;
					if (map.containsKey(sc.getColumnName())) {
						v = dt.getString(j, map.getString(sc.getColumnName()));
					} else {
						v = dt.getString(j, sc.getColumnName());
					}
					if (StringUtil.isEmpty(v)) {
						v = null;
					}
					// �������
					if (v != null) {
						if (sc.getColumnType() == DataColumn.DATETIME) {
							if (!StringUtil.verify(v, "DateTime")) {
								throw new RuntimeException("�޸��Զ�����ֶ�ʱ��������,�ֶ�ֵ������ȷ������:" + dt.getDataRow(j));
							}
						}
						if (sc.getColumnType() == DataColumn.INTEGER || sc.getColumnType() == DataColumn.SMALLINT) {
							v = String.valueOf(new Double(Double.parseDouble(v)).intValue());
						}
						if (sc.getColumnType() == DataColumn.LONG) {
							v = String.valueOf(new Double(Double.parseDouble(v)).longValue());
						}
						if (sc.getColumnType() == DataColumn.FLOAT) {
							v = String.valueOf(new Double(Double.parseDouble(v)).floatValue());
						}
						if (sc.getColumnType() == DataColumn.DECIMAL || sc.getColumnType() == DataColumn.DOUBLE
								|| sc.getColumnType() == DataColumn.BIGDECIMAL) {
							v = String.valueOf(Double.parseDouble(v));
						}
					}
					qb.add(v);
				}
				qb.addBatch();
			}
			da.executeNoQuery(qb);
		}
	}

	public static void createTable(DataAccess da, String tableName, ZCCustomTableColumnSet set) {
		TableCreator tc = new TableCreator(DBConnPool.getDBConnConfig().DBType);
		try {
			tc.createTable(toSchemaColumns(set), tableName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] sqlArr = tc.getSQLArray();
		for (int i = 0; i < sqlArr.length; i++) {
			try {
				da.executeNoQuery(new QueryBuilder(sqlArr[i]));
			} catch (Exception e) {
				LogUtil.warn(e.getMessage());// ���ж�ִ�У����ܽ���֮ǰ��drop��ʧ��
			}
		}
	}

	private static SchemaColumn[] toSchemaColumns(ZCCustomTableColumnSet set) {
		SchemaColumn[] scs = new SchemaColumn[set.size()];
		for (int i = 0; i < scs.length; i++) {
			ZCCustomTableColumnSchema ctc = set.get(i);
			int type = Integer.parseInt(ctc.getDataType());
			if (type == 12) {// ʱ��
				type = DataColumn.DATETIME;
			}
			SchemaColumn sc = new SchemaColumn(ctc.getCode(), type, i, (int) ctc.getLength(), 0, "Y".equals(ctc
					.getIsMandatory()), "Y".equals(ctc.getIsPrimaryKey()));
			scs[i] = sc;
		}
		return scs;
	}

	/**
	 * �Ա���ʽ���ʱ������
	 */
	public static void dataListDataBind(DataListAction dla) {
		String ID = dla.getParam("ID");
		ZCCustomTableSchema table = new ZCCustomTableSchema();
		table.setID(ID);
		if (!table.fill()) {
			return;
		}
		String html = Form.getViewFormContent(table, dla.getPageIndex());
		DataTable dt = new DataTable();
		dt.insertColumn("HTML");
		dt.insertRow(new Object[] { html });
		dla.bindData(dt);
		dla.setTotal(CustomTableUtil.getTotal(table, "where 1=1"));
	}

	public static Mapx initDataEditGrid(Mapx map) {
		ZCCustomTableColumnSet set = new ZCCustomTableColumnSchema().query(new QueryBuilder("where TableID=?", Long
				.parseLong(map.getString("ID"))));

		StringBuffer sb = new StringBuffer();
		sb.append("<table align='center' cellpadding=\"2\" cellspacing=\"0\" class=\"dataTable\" >");
		sb.append("<tr ztype=\"head\" class=\"dataTableHead\">");
		sb.append("<td  width=\"40\" ztype=\"rowno\">&nbsp;</td>");
		sb.append("<td  width=\"40\" ztype=\"Selector\" field='Code' align='center'>&nbsp;</td>");
		for (int i = 0; i < set.size(); i++) {
			ZCCustomTableColumnSchema column = set.get(i);
			int type = Integer.parseInt(column.getDataType());
			if (type == DataColumn.STRING && column.getLength() > 1000) {
				type = DataColumn.CLOB;
			}
			String str = getVerifyAndDefaultWidth(type, "");
			sb.append("<td " + str + ">" + column.getName() + "</td>");
		}
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td align=\"center\">&nbsp;</td>");
		sb.append("<td align=\"center\">&nbsp;</td>");
		for (int i = 0; i < set.size(); i++) {
			sb.append("<td>");
			ZCCustomTableColumnSchema column = set.get(i);
			String verify = "";
			if ("Y".equals(column.getIsMandatory())) {
				verify = "NotNull";
			}
			if ("Y".equals(column.getIsPrimaryKey())) {// ��Ҫ���������ľ�ֵ
				sb.append("<input type='hidden' value=\"${" + column.getCode() + "}\" name='" + column.getCode()
						+ "_Old'>");
			}
			int type = Integer.parseInt(column.getDataType());
			if (type == DataColumn.STRING && column.getLength() > 1000) {
				type = DataColumn.CLOB;
			}
			String str = getVerifyAndDefaultWidth(type, verify);
			sb.append("<input class='inputText' " + str + " value=\"${" + column.getCode() + "}\" name='"
					+ column.getCode() + "'></td>");
		}
		sb.append("</tr>");
		sb.append("<tr ztype=\"pagebar\">");
		sb.append("<td height=\"25\" colspan=\"" + (set.size() + 2) + "\" align=\"center\">${PageBar}</td>");
		sb.append("</tr>");
		sb.append("</table>");

		DataGridAction dga = new DataGridAction();

		dga.setTagBody(sb.toString());
		String method = "com.nswt.cms.dataservice.CustomTable.dataEditGridBind";
		dga.setMethod(method);

		dga.setID("dg1");
		dga.setPageFlag(true);
		dga.setPageSize(15);
		dga.setPageIndex(0);
		dga.setParams(map);
		try {
			HtmlTable table = new HtmlTable();
			table.parseHtml(dga.getTagBody());
			dga.setTemplate(table);
			dga.parse();
			dataEditGridBind(dga);
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("HTML", dga.getHtml());
		if ("Form".equals(map.getString("Type"))) {
			map.put("Checked", "checked");
		}
		return map;
	}

	public static String getVerifyAndDefaultWidth(int type, String verify) {
		if (type == DataColumn.DATETIME) {
			if (StringUtil.isNotEmpty(verify)) {
				verify = verify + "&&DateTime";
			} else {
				verify = "DateTime";
			}
			return "verify=\"" + verify + "\" style='width:100px'";
		} else if (type == DataColumn.INTEGER || type == DataColumn.LONG || type == DataColumn.SMALLINT
				|| type == DataColumn.BIT) {
			if (StringUtil.isNotEmpty(verify)) {
				verify = verify + "&&Int";
			} else {
				verify = "Int";
			}
			if (type == DataColumn.BIT) {
				return "verify=\"" + verify + "\" style='width:40px'";
			}
			return "verify=\"" + verify + "\" style='width:60px'";
		} else if (type == DataColumn.FLOAT || type == DataColumn.DECIMAL || type == DataColumn.DOUBLE
				|| type == DataColumn.BIGDECIMAL) {
			if (StringUtil.isNotEmpty(verify)) {
				verify = verify + "&&Number";
			} else {
				verify = "Number";
			}
			return "verify=\"" + verify + "\" style='width:70px'";
		} else if (type == DataColumn.CLOB) {
			return "verify=\"" + verify + "\" style='width:240px'";
		} else {
			return "verify=\"" + verify + "\" style='width:120px'";
		}
	}

	public static void dataEditGridBind(DataGridAction dga) {
		ZCCustomTableSchema table = new ZCCustomTableSchema();
		table.setID(Long.parseLong(dga.getParam("ID")));
		table.fill();
		if (table.getSiteID() != Application.getCurrentSiteID()) {// ��ֹԽȨ����
			return;
		}
		ZCCustomTableColumnSet set = new ZCCustomTableColumnSchema().query(new QueryBuilder("where TableID=?", table
				.getID()));
		DataAccess da = null;
		DataTable dt = null;
		String code = table.getCode();
		if (table.getType().equals("Link")) {
			da = new DataAccess(OuterDatabase.getConnection(table.getDatabaseID()));
			code = table.getOldCode();
		} else {
			da = new DataAccess();
		}
		try {
			int total = Integer.parseInt(da.executeOneValue(new QueryBuilder("select count(1) from " + code))
					.toString());
			dga.setTotal(total);
			String sql = "select * from " + code;
			boolean firstPK = true;
			for (int i = 0; i < set.size(); i++) {
				if (set.get(i).getIsPrimaryKey().equals("Y")) {
					if (firstPK) {
						sql += " order by ";
						firstPK = false;
					}
					sql += set.get(i).getCode() + ",";
				}
			}
			if (set.size() > 0 && sql.indexOf(" order by ") < 0) {
				sql += " order by " + set.get(0).getCode();
			}
			if (sql.endsWith(",")) {
				sql = sql.substring(0, sql.length() - 1);// ȥ�����һ������
			}
			QueryBuilder qb = new QueryBuilder(sql);
			dt = da.executePagedDataTable(qb, dga.getPageSize(), dga.getPageIndex());
			for (int i = 0; i < dt.getRowCount(); i++) {
				for (int j = 0; j < dt.getColCount(); j++) {
					if (dt.getDataColumn(j).getColumnType() != DataColumn.DATETIME) {
						String v = dt.getString(i, j);
						if (StringUtil.isEmpty(v)) {
							v = "";
						}
						v = StringUtil.htmlEncode(v);
						dt.set(i, j, v);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				da.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		dga.bindData(dt);
	}

	public void saveData() {
		DataTable dt = (DataTable) Request.get("Data");
		ZCCustomTableSchema table = new ZCCustomTableSchema();
		table.setID(Long.parseLong($V("ID")));
		table.fill();
		if (table.getSiteID() != Application.getCurrentSiteID()) {// ��ֹԽȨ����
			return;
		}
		String msg = CustomTableUtil.updateData(table, dt);
		if (StringUtil.isNotEmpty(msg)) {
			Response.setError("����ʧ��:" + msg);
		} else {
			Response.setMessage("����ɹ�!");
		}
	}

	public void delData() {
		DataTable dt = (DataTable) Request.get("Data");
		ZCCustomTableSchema table = new ZCCustomTableSchema();
		table.setID(Long.parseLong($V("ID")));
		table.fill();
		if (table.getSiteID() != Application.getCurrentSiteID()) {// ��ֹԽȨ����
			return;
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
					$V("ID")));
			QueryBuilder qb = new QueryBuilder("delete from " + code + " where 1=1 ");
			qb.setBatchMode(true);
			ArrayList list = new ArrayList(4);
			for (int i = 0; i < set.size(); i++) {
				if ("Y".equals(set.get(i).getIsPrimaryKey())) {
					qb.append(" and " + set.get(i).getCode() + "=?");
					list.add(set.get(i).getCode());
				}
			}
			for (int i = 0; i < dt.getRowCount(); i++) {
				for (int j = 0; j < list.size(); j++) {
					String v = dt.getString(i, list.get(j).toString());// ��������ֵɾ��
					if (StringUtil.isEmpty(v)) {
						v = null;
					}
					qb.add(v);
				}
				qb.addBatch();
			}
			da.executeNoQuery(qb);
			da.commit();
			Response.setMessage("ɾ���ɹ�!");
		} catch (Exception e) {
			Response.setError("ɾ��ʧ��:" + e.getMessage());
			try {
				da.rollback();
			} catch (SQLException e1) {
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

	public static void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding(Constant.GlobalCharset);
		response.reset();
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition",
				"attachment; filename=CustomData_" + DateUtil.getCurrentDateTime("yyyyMMddhhmmss") + ".xls");

		String ID = request.getParameter("ID");
		ZCCustomTableSchema table = new ZCCustomTableSchema();
		table.setID(Long.parseLong(ID));
		table.fill();
		if (table.getSiteID() != Application.getCurrentSiteID()) {// ��ֹԽȨ����
			return;
		}
		int total = CustomTableUtil.getTotal(table, "where 1=1");
		if (total > 60000) {
			total = 60000;
		}
		ZCCustomTableColumnSet set = new ZCCustomTableColumnSchema().query(new QueryBuilder("where TableID=?", Long
				.parseLong(ID)));
		HtmlTable ht = new HtmlTable();
		HtmlTR tr = new HtmlTR();
		ht.addTR(tr);
		for (int i = 0; i < set.size(); i++) {
			HtmlTD td = new HtmlTD();
			td.InnerHTML = set.get(i).getName();
			tr.addTD(td);
		}
		for (int i = 0; i * 500 < total; i++) {
			DataTable dt = CustomTableUtil.getData(table, new QueryBuilder("where 1=1"), 500, i);
			for (int k = 0; k < dt.getRowCount(); k++) {
				tr = new HtmlTR();
				ht.addTR(tr);
				for (int j = 0; j < set.size(); j++) {
					HtmlTD td = new HtmlTD();
					td.InnerHTML = dt.getString(k, j);
					tr.addTD(td);
				}
			}
		}
		String[] widths = new String[set.size()];
		for (int i = 0; i < set.size(); i++) {
			widths[i] = "150";
		}
		String[] indexes = new String[set.size()];
		for (int i = 0; i < set.size(); i++) {
			indexes[i] = "" + i;
		}
		HtmlUtil.htmlTableToExcel(response.getOutputStream(), ht, widths, indexes, null);
	}

	/**
	 * �ϴ������ļ�
	 */
	public static void uploadData(HttpServletRequest request, HttpServletResponse response) {
		try {
			DiskFileItemFactory fileFactory = new DiskFileItemFactory();
			ServletFileUpload fu = new ServletFileUpload(fileFactory);
			List fileItems = fu.parseRequest(request);
			fu.setHeaderEncoding("UTF-8");
			Iterator iter = fileItems.iterator();
			FileItem fileItem = null, idItem = null;
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				if (item.getFieldName().equals("ID")) {
					idItem = item;
				}
				if (!item.isFormField()) {
					String OldFileName = item.getName();
					if ((OldFileName != null && !OldFileName.equals("")) && item.getSize() != 0) {
						fileItem = item;
					}
				}
			}
			String OldFileName = fileItem.getName();
			LogUtil.info("Upload CustomData FileName:" + OldFileName);
			OldFileName = OldFileName.substring(OldFileName.lastIndexOf("\\") + 1);
			String ext = OldFileName.substring(OldFileName.lastIndexOf("."));
			if (!ext.toLowerCase().equals(".xls")) {
				response.sendRedirect("CustomTableDataImportStep1.jsp?Error=1");
				return;
			}
			String FileName = "CustomTableUpload_" + DateUtil.getCurrentDate("yyyyMMddHHmmss") + ".xls";
			String Path = Config.getContextRealPath() + "WEB-INF/data/backup/";
			fileItem.write(new File(Path + FileName));
			response.sendRedirect("CustomTableDataImportStep2.jsp?FileName=" + FileName + "&ID="
					+ idItem.getString(Constant.GlobalCharset));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��鵼���ļ��Ƿ��д���
	 */
	public static Mapx initImportStep2(Mapx params) {
		String FileName = params.getString("FileName");
		String ID = params.getString("ID");
		ZCCustomTableSchema table = new ZCCustomTableSchema();
		table.setID(Long.parseLong(ID));
		table.fill();
		if (table.getSiteID() != Application.getCurrentSiteID()) {// ��ֹԽȨ����
			return null;
		}
		try {
			ArrayList list = getErrorInfo(ID, FileName);
			if (list.size() == 0) {
				params.put("Message", "�ļ����δ���ִ���ȷ��Ҫ����?");
			} else {
				params.put("Message", "�ļ�����з��ִ��� <font class='red'>" + list.size()
						+ "</font> ����<br><a href='CustomTableImportError.jsp?ID=" + ID + "&FileName=" + FileName
						+ "'>����˴�</a>���ش�����Ϣ��ȷ��Ҫ����?");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return params;
	}

	public static ArrayList getErrorInfo(String ID, String FileName) {
		DataTable dt = null;
		try {
			String Path = Config.getContextRealPath() + "WEB-INF/data/backup/";
			dt = DataTableUtil.xlsToDataTable(Path + FileName);
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.error("��ȡ���ݵ����ļ�ʱ��������:" + e.getMessage());
			return null;
		}
		ArrayList list = new ArrayList();
		try {
			ZCCustomTableColumnSet set = new ZCCustomTableColumnSchema().query(new QueryBuilder("where TableID=?", Long
					.parseLong(ID)));
			int ErrorCount = 0;
			for (int i = 0; i < dt.getRowCount(); i++) {
				for (int j = 0; j < dt.getColCount(); j++) {
					ZCCustomTableColumnSchema ctc = set.get(j);
					String v = dt.getString(i, j);
					int dataType = Integer.parseInt(ctc.getDataType());
					if ("Y".equals(ctc.getIsMandatory()) || "Y".equals(ctc.getIsPrimaryKey())) {
						if (StringUtil.isEmpty(v)) {
							ErrorCount++;
							list.add("��" + (i + 1) + "�У���" + (j + 1) + "�У��ֶβ���Ϊ��!");
						}
					}
					if (v != null) {
						if (ctc.getMaxLength() != 0 && v.length() < ctc.getMaxLength()) {
							list.add("��" + (i + 1) + "�У���" + (j + 1) + "�У����ݳ������������" + ctc.getMaxLength() + "���ַ�!");
							ErrorCount++;
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
							list.add("��" + (i + 1) + "�У���" + (j + 1) + "�У���������������!");
							ErrorCount++;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * ���ش�����Ϣ
	 */
	public static void downloadErrorList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding(Constant.GlobalCharset);
		response.reset();
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition",
				"attachment; filename=Error_" + DateUtil.getCurrentDateTime("yyyyMMddhhmmss") + ".txt");

		String ID = request.getParameter("ID");
		String FileName = request.getParameter("FileName");
		ZCCustomTableSchema table = new ZCCustomTableSchema();
		table.setID(Long.parseLong(ID));
		table.fill();
		if (table.getSiteID() != Application.getCurrentSiteID()) {// ��ֹԽȨ����
			return;
		}
		ArrayList list = getErrorInfo(ID, FileName);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			sb.append(list.get(i));
			sb.append("\n");
		}
		response.getOutputStream().write(sb.toString().getBytes());
	}

	/**
	 * ��ʼ��������
	 */
	public void importData() {
		String FileName = $V("FileName");
		String ID = $V("ID");
		ZCCustomTableSchema table = new ZCCustomTableSchema();
		table.setID(Long.parseLong(ID));
		table.fill();
		if (table.getSiteID() != Application.getCurrentSiteID()) {// ��ֹԽȨ����
			return;
		}
		DataTable dt = null;
		try {
			String Path = Config.getContextRealPath() + "WEB-INF/data/backup/";
			dt = DataTableUtil.xlsToDataTable(Path + FileName);
		} catch (Exception e) {
			e.printStackTrace();
			Response.setError("��ȡ���ݵ����ļ�ʱ��������:" + e.getMessage());
			return;
		}
		String msg = CustomTableUtil.updateData(table, dt);
		if (StringUtil.isNotEmpty(msg)) {
			Response.setError("����ʧ��:" + msg);
		} else {
			Response.setMessage("����ɹ�!");
		}
	}

	/**
	 *  ����mybatis�־ò�mapping����,
	 * */
	public void genDbSrc(){
		String ids = $V("IDs");
		if (ids.indexOf("\"") >= 0 || ids.indexOf("\'") >= 0) {
			Response.setStatus(0);
			Response.setMessage("�����IDʱ��������!");
			return;
		}
		MyBGenerator myBGenerator = new MyBGenerator();

		//mysql
		if(Config.getValue("Aa_Gen_DBTYPE").equalsIgnoreCase("MYSQL")){
			myBGenerator.setClassPathEntry_location(Config.getContextRealPath()+"WEB-INF/lib/mysql-connector-java-5.1.30.jar");
			myBGenerator.setContext_id("HkDbTest");
			myBGenerator.setJDBCCLASS("com.mysql.jdbc.Driver");
			myBGenerator.setJDBCCONNECTIONURL("jdbc:mysql://124.200.181.126:9306/HKTest?jdbcCompliantTruncation=false&amp;characterEncoding=UTF-8&amp;allowMultiQueries=true");
			myBGenerator.setJDBCUSER("root");
			myBGenerator.setJDBCPASSWORD("root");

			myBGenerator.setPO_PACKAGE("com.hk.domain");
			myBGenerator.setPO_OUT_PATH(Config.getContextRealPath()+"/wwwroot/"+Application.getCurrentSiteAlias()+"/src/main/java");

			myBGenerator.setMAPPER_PACKAGE("com.hk.mapper");
			myBGenerator.setMAPPER_OUT_PATH(Config.getContextRealPath()+"/wwwroot/"+Application.getCurrentSiteAlias()+"/src/main/java");

			myBGenerator.setDAO_PACKAGE("com.hk.dao");
			myBGenerator.setDAO_OUT_PATH(Config.getContextRealPath()+"/wwwroot/"+Application.getCurrentSiteAlias()+"/src/main/java");

			String tablename = ((new ZCCustomTableSchema().query(new QueryBuilder("where SiteID=? and ID in (" + ids + ")",Application.getCurrentSiteID()))).get(0)).getCode();
			myBGenerator.setTABLE_NAME("table1");
			myBGenerator.setSCHEMA("HKTest");
			myBGenerator.setGENKEY_COLUMN("idnew_table");

		}

		if(GenUtil.generatorFileInit(myBGenerator)){
			List<String> warnings = new ArrayList<String>();
			boolean overwrite = true;
			File configFile = new File(Config.getContextRealPath() + "WEB-INF/classes/generatorConfig.xml");
			//File configFile = new File("/Users/lijinyan/MyWork/NSWT/creditloan2/target/classes/generatorConfig.xml");
			ConfigurationParser cp = new ConfigurationParser(warnings);
			Configuration config = null;
			try {
				config = cp.parseConfiguration(configFile);
				DefaultShellCallback callback = new DefaultShellCallback(overwrite);
				MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
				myBatisGenerator.generate(null);
				Response.setMessage("���봴���ɹ�!");
				LogUtil.info("����"+Application.getCurrentSiteID()+":"+ids.toString()+"����ɹ�!");
			} catch (IOException e) {
				e.printStackTrace();
				LogUtil.warn("����"+Application.getCurrentSiteID()+":"+ids.toString()+"�������!");
				Response.setError("���봴��ʧ��!");
			} catch (XMLParserException e) {
				e.printStackTrace();
				LogUtil.warn("����"+Application.getCurrentSiteID()+":"+ids.toString()+"�������!");
				Response.setError("���봴��ʧ��!");
			} catch (InterruptedException e) {
				e.printStackTrace();
				LogUtil.warn("����"+Application.getCurrentSiteID()+":"+ids.toString()+"�������!");
				Response.setError("���봴��ʧ��!");
			} catch (InvalidConfigurationException e) {
				e.printStackTrace();
				LogUtil.warn("����"+Application.getCurrentSiteID()+":"+ids.toString()+"�������!");
				Response.setError("���봴��ʧ��!");
			} catch (SQLException e) {
				e.printStackTrace();
				LogUtil.warn("����"+Application.getCurrentSiteID()+":"+ids.toString()+"�������!");
				Response.setError("���봴��ʧ��!");
			}
		}
	}
}
