package com.nswt.cms.dataservice;

import java.sql.SQLException;
import java.util.ArrayList;

import com.nswt.framework.data.DataAccess;
import com.nswt.framework.data.DataColumn;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.utility.DateUtil;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.pub.OrderUtil;
import com.nswt.schema.ZCCustomTableColumnSchema;
import com.nswt.schema.ZCCustomTableColumnSet;
import com.nswt.schema.ZCCustomTableSchema;
import com.nswt.schema.ZCCustomTableSet;

/**
 * 自定义数据操作类 日期 : 2009-11-20 <br>
 * 作者: NSWT <br>
 * 邮箱：nswt@nswt.com.cn <br>
 */
public class CustomTableUtil {

	public static int getTotal(ZCCustomTableSchema table, String wherePart) {
		if (wherePart != null) {
			if (!wherePart.trim().toLowerCase().startsWith("where")) {
				throw new RuntimeException("指定的wherePart不是以where开头的字符串");
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

		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) from ");
		sb.append(code);
		if (wherePart != null) {
			sb.append(" ");
			sb.append(wherePart);
		}

		try {
			return Integer.parseInt(da.executeOneValue(new QueryBuilder(sb.toString())).toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				da.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return -1;
	}

	/**
	 * 数据库采集用到了此方法
	 */
	public static int getTotal(String tableCode, long databaseID, String wherePart) {
		if (wherePart != null) {
			if (!wherePart.trim().toLowerCase().startsWith("where")) {
				throw new RuntimeException("指定的wherePart不是以where开头的字符串");
			}
		}
		DataAccess da = new DataAccess(OuterDatabase.getConnection(databaseID));
		StringBuffer sb = new StringBuffer();
		sb.append("select * from ");
		sb.append(tableCode);
		if (wherePart != null) {
			sb.append(" ");
			sb.append(wherePart);
		}
		try {
			return da.getCount(new QueryBuilder(sb.toString()));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				da.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return -1;
	}

	public static int getTotal(long tableID) {
		ZCCustomTableSchema table = new ZCCustomTableSchema();
		table.setID(tableID);
		table.fill();
		return getTotal(table, null);
	}

	public static int getTotal(String tableCode, String wherePart) {
		ZCCustomTableSchema table = new ZCCustomTableSchema();
		table.setCode(tableCode);
		ZCCustomTableSet set = table.query();
		if (set == null || set.size() < 1) {
			return -1;
		} else {
			return getTotal(set.get(0), wherePart);
		}
	}

	public static DataTable getData(long tableID) {
		ZCCustomTableSchema table = new ZCCustomTableSchema();
		table.setID(tableID);
		table.fill();
		return getData(table);
	}

	public static DataTable getData(String tableCode) {
		return getData(tableCode, null);
	}

	public static DataTable getData(String tableCode, QueryBuilder wherePart) {
		return getData(tableCode, wherePart, -1, -1);
	}

	public static DataTable getData(String tableCode, QueryBuilder wherePart, int pageSize, int pageIndex) {
		ZCCustomTableSchema table = new ZCCustomTableSchema();
		table.setCode(tableCode);
		ZCCustomTableSet set = table.query();
		if (set == null || set.size() < 1) {
			return null;
		} else {
			return getData(set.get(0), wherePart, pageSize, pageIndex);
		}
	}

	public static DataTable getData(ZCCustomTableSchema table) {
		return getData(table, null);
	}

	public static DataTable getData(ZCCustomTableSchema table, String wherePart) {
		return getData(table, null, -1, -1);
	}

	public static DataTable getData(long tableID, int pageSize, int pageIndex) {
		ZCCustomTableSchema table = new ZCCustomTableSchema();
		table.setID(tableID);
		table.fill();
		return getData(table, null, pageSize, pageIndex);
	}

	public static DataTable getData(long tableID, QueryBuilder wherePart, int pageSize, int pageIndex) {
		ZCCustomTableSchema table = new ZCCustomTableSchema();
		table.setID(tableID);
		table.fill();
		return getData(table, wherePart, pageSize, pageIndex);
	}

	public static DataTable getData(ZCCustomTableSchema table, QueryBuilder wherePart, int pageSize, int pageIndex) {
		if (wherePart != null) {
			if (!wherePart.getSQL().trim().toLowerCase().startsWith("where")) {
				throw new RuntimeException("指定的wherePart不是以where开头的字符串");
			}
		}

		DataAccess da = null;
		DataTable dt = null;
		String code = table.getCode();
		if (table.getType().equals("Link")) {
			da = new DataAccess(OuterDatabase.getConnection(table.getDatabaseID()));
			code = table.getOldCode();
		} else {
			da = new DataAccess();
		}

		if (wherePart != null) {
			wherePart.setSQL("select * from " + code + " " + wherePart.getSQL());
		} else {
			wherePart = new QueryBuilder("select * from " + code);
		}

		pageIndex = pageIndex < 0 ? 0 : pageIndex;
		try {
			if (pageSize > 0) {
				if (da.getConnection().getDBConfig().isSQLServer()) {// 必须有OrderBy，否则分页不能成功
					prepareForSQLServer(wherePart, table.getID());
				}
				dt = da.executePagedDataTable(wherePart, pageSize, pageIndex);
			} else {
				dt = da.executeDataTable(wherePart);
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
		return dt;
	}

	/**
	 * 数据库采集用到了此方法
	 */
	public static DataTable getData(String tableCode, long databaseID, QueryBuilder wherePart, int pageSize,
			int pageIndex) {
		if (wherePart != null) {
			if (!wherePart.getSQL().trim().toLowerCase().startsWith("where")) {
				throw new RuntimeException("指定的wherePart不是以where开头的字符串");
			}
		}

		DataAccess da = new DataAccess(OuterDatabase.getConnection(databaseID));
		DataTable dt = null;
		if (wherePart != null) {
			wherePart.setSQL("select * from " + tableCode + " " + wherePart.getSQL());
		} else {
			wherePart = new QueryBuilder("select * from " + tableCode);
		}

		pageIndex = pageIndex < 0 ? 0 : pageIndex;
		try {
			if (pageSize > 0) {
				if (da.getConnection().getDBConfig().isSQLServer()) {// 必须有OrderBy，否则分页不能成功
					long id = new QueryBuilder("select ID from ZCCustomTable where Code=?", tableCode).executeLong();
					prepareForSQLServer(wherePart, id);
				}
				dt = da.executePagedDataTable(wherePart, pageSize, pageIndex);
			} else {
				dt = da.executeDataTable(wherePart);
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
		return dt;
	}

	private static void prepareForSQLServer(QueryBuilder qb, long tableID) {
		if (qb.getSQL().toLowerCase().replaceAll("\\s+", " ").indexOf(" order by ") > 0) {
			return;
		}
		ZCCustomTableColumnSchema ctc = new ZCCustomTableColumnSchema();
		ctc.setTableID(tableID);
		ZCCustomTableColumnSet set = ctc.query();
		String orderColumn = null;
		for (int i = 0; i < set.size(); i++) {
			ctc = set.get(i);
			if ("Y".equals(ctc.getIsAutoID())) {
				orderColumn = ctc.getCode();
				break;
			}
		}
		if (set.size() > 0) {
			if (StringUtil.isEmpty(orderColumn)) {
				for (int i = 0; i < set.size(); i++) {
					ctc = set.get(i);
					if ("Y".equals(ctc.getIsPrimaryKey())) {
						orderColumn = ctc.getCode();
						break;
					}
				}
			}
			if (StringUtil.isEmpty(orderColumn)) {
				orderColumn = set.get(0).getCode();
			}
			qb.append(" order by " + orderColumn);
		}
	}

	public static DataTable executeDataTable(String tableCode, QueryBuilder qb) {
		ZCCustomTableSchema table = new ZCCustomTableSchema();
		table.setCode(tableCode);
		ZCCustomTableSet set = table.query();
		if (set == null || set.size() < 1) {
			return null;
		} else {
			return executeDataTable(set.get(0), qb);
		}
	}

	public static DataTable executeDataTable(ZCCustomTableSchema table, QueryBuilder qb) {
		DataAccess da = null;
		DataTable dt = null;
		String code = table.getCode();
		if (table.getType().equals("Link")) {
			da = new DataAccess(OuterDatabase.getConnection(table.getDatabaseID()));
			code = table.getOldCode();
		} else {
			da = new DataAccess();
		}
		StringBuffer sb = new StringBuffer();
		sb.append("select * from ");
		sb.append(code);
		qb.setSQL("select * from " + code + " " + qb.getSQL());
		try {
			dt = da.executeDataTable(qb);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				da.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return dt;
	}

	public static Object executeOneValue(String tableCode, QueryBuilder qb) {
		ZCCustomTableSchema table = new ZCCustomTableSchema();
		table.setCode(tableCode);
		ZCCustomTableSet set = table.query();
		if (set == null || set.size() < 1) {
			return null;
		} else {
			return executeOneValue(set.get(0), qb);
		}
	}

	public static Object executeOneValue(ZCCustomTableSchema table, QueryBuilder qb) {
		DataAccess da = null;
		Object dt = null;
		if (table.getType().equals("Link")) {
			da = new DataAccess(OuterDatabase.getConnection(table.getDatabaseID()));
		} else {
			da = new DataAccess();
		}

		try {
			dt = da.executeOneValue(qb);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				da.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return dt;
	}

	/**
	 * 更新数据，首先根据旧主键删除数据，再插入数据
	 */
	public static String updateData(ZCCustomTableSchema table, DataTable dt) {
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
			// 先删除
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
					String v = dt.getString(i, list.get(j).toString());
					if (dt.getDataColumn(list.get(j).toString() + "_Old") != null) {
						v = dt.getString(i, list.get(j).toString() + "_Old");// 主键值可能已经被修改，必须根据旧主键值删除
					}
					if (StringUtil.isEmpty(v)) {
						v = null;
					}
					qb.add(v);
				}
				qb.addBatch();
			}
			da.executeNoQuery(qb);

			// 再插入
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
			qb = new QueryBuilder(sb.toString());
			qb.setBatchMode(true);
			for (int i = 0; i < dt.getRowCount(); i++) {
				for (int j = 0; j < set.size(); j++) {
					ZCCustomTableColumnSchema column = set.get(j);
					String v = dt.getString(i, set.get(j).getName());
					if (StringUtil.isEmpty(v)) {
						v = null;
						if ("Y".equals(set.get(j).getIsAutoID())) {
							v = String.valueOf(OrderUtil.getDefaultOrder());
						}
					}
					if ("Y".equals(column.getIsMandatory()) || "Y".equals(column.getIsPrimaryKey())) {
						if (StringUtil.isEmpty(v)) {
							return column.getName() + "不能为空!\\n";
						}
					}
					int dataType = Integer.parseInt(column.getDataType());
					if (v != null) {
						if (column.getMaxLength() != 0 && v.length() < column.getMaxLength()) {
							return column.getName() + "数据过长，最大允许" + column.getMaxLength() + "个字符!\\n";
						}
						try {
							if (dataType == DataColumn.DATETIME) {
								v = DateUtil.toDateTimeString(DateUtil.parseDateTime(v));
								if (v == null) {
									throw new SQLException("日期时间错误");
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
							return column.getName() + "数据不正确!\\n";
						}
					}
					if (dataType == DataColumn.INTEGER || dataType == DataColumn.SMALLINT) {
						qb.add(Integer.parseInt(v));
					} else if (dataType == DataColumn.LONG) {
						qb.add(Long.parseLong(v));
					} else {
						qb.add(v);
					}
				}
				qb.addBatch();
			}
			da.executeNoQuery(qb);
			da.commit();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				da.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return e.getMessage();
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
