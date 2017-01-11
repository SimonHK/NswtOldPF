package com.nswt.framework.orm;

import java.sql.SQLException;
import java.util.ArrayList;

import com.nswt.framework.data.DBConn;
import com.nswt.framework.data.DBConnConfig;
import com.nswt.framework.data.DataAccess;
import com.nswt.framework.data.DataColumn;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.LogUtil;

/**
 * �������������õ������SQL
 * 
 * ���� : 2016-11-19 <br>
 * ����: NSWT <br>
 * ���䣺nswt@nswt.com.cn <br>
 */
public class TableCreator {
	private ArrayList list = new ArrayList();

	private String DBType;

	public TableCreator(String dbType) {
		DBType = dbType;
	}

	public void createTable(SchemaColumn[] scs, String tableCode) throws Exception {
		createTable(scs, tableCode, true);
	}

	public void createTable(SchemaColumn[] scs, String tableCode, boolean create) throws Exception {
		if (!create) {
			list.add("delete from " + tableCode);
		} else {
			dropTable(tableCode);
			list.add(createTable(scs, tableCode, DBType));
		}
	}

	public static String createTable(SchemaColumn[] scs, String tableCode, String DBType) throws Exception {
		if (DBType.equals(DBConnConfig.MSSQL)) {
			return createTableMSSQL(scs, tableCode, DBType);
		}
		if (DBType.equals(DBConnConfig.MYSQL)) {
			return createTableMYSQL(scs, tableCode, DBType);
		}
		if (DBType.equals(DBConnConfig.ORACLE)) {
			return createTableOracle(scs, tableCode, DBType);
		}
		if (DBType.equals(DBConnConfig.DB2)) {
			return createTableDB2(scs, tableCode, DBType);
		}
		if (DBType.equals(DBConnConfig.SYBASE)) {
			return createTableSybase(scs, tableCode, DBType);
		}
		return null;
	}

	/**
	 * ִ�м�׼���õ�SQL�������SQL�б�
	 */
	public void executeAndClear() {
		Transaction tran = new Transaction();
		executeAndClear(tran);
		tran.commit();
	}

	/**
	 * ��������ִ�м�׼���õ�SQL�������SQL�б�
	 */
	public void executeAndClear(Transaction tran) {
		for (int i = 0; i < list.size(); i++) {
			QueryBuilder qb = new QueryBuilder(list.get(i).toString());
			tran.add(qb);
		}
		list.clear();
	}

	/**
	 * ʹ��ָ��������ִ�м�׼���õ�SQL�������SQL�б�
	 */
	public void executeAndClear(DBConn conn) {
		DataAccess da = new DataAccess(conn);
		for (int i = 0; i < list.size(); i++) {
			QueryBuilder qb = new QueryBuilder(list.get(i).toString());
			try {
				da.executeNoQuery(qb);
			} catch (SQLException e) {
				if (qb.getSQL().startsWith("drop")) {
					String table = qb.getSQL();
					table = table.substring(table.indexOf(" ", 8)).trim();
					// table = table.substring(0, table.indexOf(" "));
					LogUtil.warn("δ��ɾ�������ܲ����ڣ�" + table);
				} else {
					LogUtil.warn(qb.getSQL());
					e.printStackTrace();
				}
			}
		}
		list.clear();
	}

	/**
	 * ��������SQL������
	 */
	public String[] getSQLArray() {
		String[] arr = new String[list.size()];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = list.get(i).toString();
		}
		return arr;
	}

	/**
	 * ���ؿ���һ��ִ�еĴ�SQL���
	 */
	public String getAllSQL() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			sb.append(list.get(i));
			if (DBType.equals(DBConnConfig.MSSQL) || DBType.equals(DBConnConfig.SYBASE)) {
				sb.append("\ngo\n");
			} else {
				sb.append(";\n");
			}
		}
		return sb.toString();
	}

	/**
	 * ��DataColumn�е��ֶ�����תΪSQL�е��ֶ�����
	 */
	public static String toSQLType(int columnType, String DBType) {
		if (DBType.equals(DBConnConfig.MSSQL)) {
			if (columnType == DataColumn.BIGDECIMAL) {
				return "numeric";
			} else if (columnType == DataColumn.BLOB) {
				return "varbinary(MAX)";
			} else if (columnType == DataColumn.DATETIME) {
				return "datetime";
			} else if (columnType == DataColumn.DECIMAL) {
				return "decimal";
			} else if (columnType == DataColumn.DOUBLE) {
				return "numeric";
			} else if (columnType == DataColumn.FLOAT) {
				return "numeric";
			} else if (columnType == DataColumn.INTEGER) {
				return "int";
			} else if (columnType == DataColumn.LONG) {
				return "bigint";
			} else if (columnType == DataColumn.SMALLINT) {
				return "int";
			} else if (columnType == DataColumn.STRING) {
				return "varchar";
			} else if (columnType == DataColumn.CLOB) {
				return "text";
			}
		}
		if (DBType.equals(DBConnConfig.SYBASE)) {
			if (columnType == DataColumn.BIGDECIMAL) {
				return "numeric";
			} else if (columnType == DataColumn.BLOB) {
				return "varbinary(MAX)";
			} else if (columnType == DataColumn.DATETIME) {
				return "datetime";
			} else if (columnType == DataColumn.DECIMAL) {
				return "decimal";
			} else if (columnType == DataColumn.DOUBLE) {
				return "numeric";
			} else if (columnType == DataColumn.FLOAT) {
				return "numeric";
			} else if (columnType == DataColumn.INTEGER) {
				return "int";
			} else if (columnType == DataColumn.LONG) {
				return "numeric(20)";
			} else if (columnType == DataColumn.SMALLINT) {
				return "int";
			} else if (columnType == DataColumn.STRING) {
				return "varchar";
			} else if (columnType == DataColumn.CLOB) {
				return "text";
			}
		}
		if (DBType.equals(DBConnConfig.ORACLE)) {
			if (columnType == DataColumn.BIGDECIMAL) {
				return "DOUBLE PRECISION";
			} else if (columnType == DataColumn.BLOB) {
				return "BLOB";
			} else if (columnType == DataColumn.DATETIME) {
				return "DATE";
			} else if (columnType == DataColumn.DECIMAL) {
				return "DECIMAL";
			} else if (columnType == DataColumn.DOUBLE) {
				return "NUMBER";
			} else if (columnType == DataColumn.FLOAT) {
				return "NUMBER";
			} else if (columnType == DataColumn.INTEGER) {
				return "INTEGER";
			} else if (columnType == DataColumn.LONG) {
				return "INTEGER";
			} else if (columnType == DataColumn.SMALLINT) {
				return "INTEGER";
			} else if (columnType == DataColumn.STRING) {
				return "VARCHAR2";
			} else if (columnType == DataColumn.CLOB) {
				return "CLOB";
			}
		}
		if (DBType.equals(DBConnConfig.DB2)) {
			if (columnType == DataColumn.BIGDECIMAL) {
				return "DOUBLE PRECISION";
			} else if (columnType == DataColumn.BLOB) {
				return "BLOB";
			} else if (columnType == DataColumn.DATETIME) {
				return "TIMESTAMP";
			} else if (columnType == DataColumn.DECIMAL) {
				return "DECIMAL";
			} else if (columnType == DataColumn.DOUBLE) {
				return "NUMERIC";
			} else if (columnType == DataColumn.FLOAT) {
				return "NUMERIC";
			} else if (columnType == DataColumn.INTEGER) {
				return "INTEGER";
			} else if (columnType == DataColumn.LONG) {
				return "BIGINT";
			} else if (columnType == DataColumn.SMALLINT) {
				return "INTEGER";
			} else if (columnType == DataColumn.STRING) {
				return "VARCHAR";
			} else if (columnType == DataColumn.CLOB) {
				return "CLOB";
			}
		}
		if (DBType.equals(DBConnConfig.MYSQL)) {
			if (columnType == DataColumn.BIGDECIMAL) {
				return "double";
			} else if (columnType == DataColumn.BLOB) {
				return "binary varying(MAX)";
			} else if (columnType == DataColumn.DATETIME) {
				return "datetime";
			} else if (columnType == DataColumn.DECIMAL) {
				return "decimal";
			} else if (columnType == DataColumn.DOUBLE) {
				return "double";
			} else if (columnType == DataColumn.FLOAT) {
				return "float";
			} else if (columnType == DataColumn.INTEGER) {
				return "int";
			} else if (columnType == DataColumn.LONG) {
				return "bigint";
			} else if (columnType == DataColumn.SMALLINT) {
				return "int";
			} else if (columnType == DataColumn.STRING) {
				return "varchar";
			} else if (columnType == DataColumn.CLOB) {
				return "mediumtext";
			}
		}
		throw new RuntimeException("���ݿ����ʹ���:" + DBType + "�����ֶ�����δ֪:" + columnType);
	}

	public static String createTableMSSQL(SchemaColumn[] scs, String tableCode, String DBType) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("create table " + tableCode + "(\n");
		StringBuffer ksb = new StringBuffer();
		for (int i = 0; i < scs.length; i++) {
			SchemaColumn sc = scs[i];
			if (i != 0) {
				sb.append(",\n");
			}
			sb.append("\t" + sc.getColumnName() + " ");
			String sqlType = toSQLType(sc.getColumnType(), DBType);
			sb.append(sqlType + " ");
			if (sc.getColumnType() == DataColumn.STRING || sqlType.equalsIgnoreCase("NUMERIC")
					|| sqlType.equalsIgnoreCase("DECIMAL")) {// �������Ͳ����޶����ȣ�����ᱨ��
				if (sc.getLength() == 0 && sc.getColumnType() == DataColumn.STRING) {
					throw new RuntimeException("����Ϊvarchar�����趨���ȣ������ֶ�" + sc.getColumnName() + "������");
				}
				sb.append(getFieldExtDesc(sc.getLength(), sc.getPrecision()));
			}
			if (sc.isMandatory()) {
				sb.append("not null");
			}
			if (sc.isPrimaryKey()) {
				if (ksb.length() == 0) {
					ksb.append("\tconstraint PK_" + tableCode + " primary key nonclustered (");
				} else {
					ksb.append(",");
				}
				ksb.append(sc.getColumnName());
			}
		}
		if (ksb.length() != 0) {
			ksb.append(")");
			sb.append(",\n" + ksb);
		}
		sb.append("\n)");
		return sb.toString();
	}

	public static String createTableSybase(SchemaColumn[] scs, String tableCode, String DBType) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("create table " + tableCode + "(\n");
		StringBuffer ksb = new StringBuffer();
		for (int i = 0; i < scs.length; i++) {
			SchemaColumn sc = scs[i];
			if (i != 0) {
				sb.append(",\n");
			}
			if (sc.getColumnName().equalsIgnoreCase("Count")) {
				sb.append("\t\"" + sc.getColumnName() + "\" ");
			} else if (sc.getColumnName().equalsIgnoreCase("Scroll")) {
				sb.append("\t\"" + sc.getColumnName() + "\" ");
			} else {
				sb.append("\t" + sc.getColumnName() + " ");
			}
			String sqlType = toSQLType(sc.getColumnType(), DBType);
			sb.append(sqlType + " ");
			if (sc.getColumnType() == DataColumn.STRING || sqlType.equalsIgnoreCase("NUMERIC")
					|| sqlType.equalsIgnoreCase("DECIMAL")) {// �������Ͳ����޶����ȣ�����ᱨ��
				if (sc.getLength() == 0 && sc.getColumnType() == DataColumn.STRING) {
					throw new RuntimeException("����Ϊvarchar�����趨���ȣ������ֶ�" + sc.getColumnName() + "������");
				}
				sb.append(getFieldExtDesc(sc.getLength(), sc.getPrecision()));
			}
			if (sc.isMandatory()) {
				sb.append("not null");
			} else {
				sb.append("null");
			}
			if (sc.isPrimaryKey()) {
				if (ksb.length() == 0) {
					ksb.append("\tconstraint PK_" + tableCode + " primary key nonclustered (");
				} else {
					ksb.append(",");
				}
				ksb.append(sc.getColumnName());
			}
		}
		if (ksb.length() != 0) {
			ksb.append(")");
			sb.append(",\n" + ksb);
		}
		sb.append("\n)");
		return sb.toString();
	}

	public static String createTableOracle(SchemaColumn[] scs, String tableCode, String DBType) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("create table " + tableCode + "(\n");
		StringBuffer ksb = new StringBuffer();
		for (int i = 0; i < scs.length; i++) {
			SchemaColumn sc = scs[i];
			if (i != 0) {
				sb.append(",\n");
			}
			sb.append("\t" + sc.getColumnName() + " ");
			String sqlType = toSQLType(sc.getColumnType(), DBType);
			sb.append(sqlType + " ");
			if (sc.getColumnType() == DataColumn.STRING || sqlType.equalsIgnoreCase("NUMBER")
					|| sqlType.equalsIgnoreCase("DECIMAL")) {// �������Ͳ����޶����ȣ�����ᱨ��
				if (sc.getLength() == 0 && sc.getColumnType() == DataColumn.STRING) {
					throw new RuntimeException("����Ϊvarchar�����趨���ȣ������ֶ�" + sc.getColumnName() + "������");
				}
				sb.append(getFieldExtDesc(sc.getLength(), sc.getPrecision()));
			}
			if (sc.isMandatory()) {
				sb.append("not null");
			}
			if (sc.isPrimaryKey()) {
				if (ksb.length() == 0) {
					ksb.append("\tconstraint PK_" + tableCode + " primary key (");
				} else {
					ksb.append(",");
				}
				ksb.append(sc.getColumnName());
			}
		}
		if (ksb.length() != 0) {
			ksb.append(")");
			sb.append(",\n" + ksb);
		}
		sb.append("\n)");
		return sb.toString();
	}

	public static String createTableDB2(SchemaColumn[] scs, String tableCode, String DBType) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("create table " + tableCode + "(\n");
		StringBuffer ksb = new StringBuffer();
		for (int i = 0; i < scs.length; i++) {
			SchemaColumn sc = scs[i];
			if (i != 0) {
				sb.append(",\n");
			}
			sb.append("\t" + sc.getColumnName() + " ");
			String sqlType = toSQLType(sc.getColumnType(), DBType);
			sb.append(sqlType + " ");
			if (sc.getColumnType() == DataColumn.STRING || sqlType.equalsIgnoreCase("NUMERIC")
					|| sqlType.equalsIgnoreCase("DECIMAL")) {// �������Ͳ����޶����ȣ�����ᱨ��
				if (sc.getLength() == 0 && sc.getColumnType() == DataColumn.STRING) {
					throw new RuntimeException("����Ϊvarchar2�����趨���ȣ������ֶ�" + sc.getColumnName() + "������");
				}
				sb.append(getFieldExtDesc(sc.getLength(), sc.getPrecision()));
			}
			if (sc.isMandatory()) {
				sb.append("not null");
			}
			if (sc.isPrimaryKey()) {
				if (ksb.length() == 0) {
					String pkName = tableCode;
					if (pkName.length() > 15) {
						pkName = pkName.substring(0, 15);
					}
					ksb.append("\tconstraint PK_" + pkName + " primary key (");
				} else {
					ksb.append(",");
				}
				ksb.append(sc.getColumnName());
			}
		}
		if (ksb.length() != 0) {
			ksb.append(")");
			sb.append(",\n" + ksb);
		}
		sb.append("\n)");
		return sb.toString();
	}

	public static String createTableMYSQL(SchemaColumn[] scs, String tableCode, String DBType) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("create table " + tableCode + "\n(\n");
		StringBuffer ksb = new StringBuffer();
		for (int i = 0; i < scs.length; i++) {
			SchemaColumn sc = scs[i];
			if (i != 0) {
				sb.append(",\n");
			}
			sb.append("\t" + sc.getColumnName() + " ");
			String sqlType = toSQLType(sc.getColumnType(), DBType);
			sb.append(sqlType + " ");
			if (sc.getLength() == 0 && sc.getColumnType() == DataColumn.STRING) {
				throw new RuntimeException("����Ϊvarchar�����趨���ȣ������ֶ�" + sc.getColumnName() + "������");
			}
			sb.append(getFieldExtDesc(sc.getLength(), sc.getPrecision()));
			if (sc.isMandatory()) {
				sb.append("not null");
			}
			if (sc.isPrimaryKey()) {
				if (ksb.length() == 0) {
					ksb.append("\tprimary key (");
				} else {
					ksb.append(",");
				}
				ksb.append(sc.getColumnName());
			}
		}
		if (ksb.length() != 0) {
			ksb.append(")");
			sb.append(",\n" + ksb);
		}
		sb.append("\n)");
		return sb.toString();
	}

	/**
	 * ɾ����
	 */
	public void dropTable(String tableCode) {
		list.add(dropTable(tableCode, DBType));
	}

	public static String dropTable(String tableCode, String dbType) {
		String dropSQL = null;
		if (dbType.equals(DBConnConfig.MSSQL) || dbType.equals(DBConnConfig.SYBASE)) {
			dropSQL = "if exists (select 1 from  sysobjects where id = object_id('" + tableCode
					+ "') and type='U') drop table " + tableCode;
		}
		if (dbType.equals(DBConnConfig.ORACLE)) {
			dropSQL = "drop table " + tableCode + " cascade constraints";
		}
		if (dbType.equals(DBConnConfig.DB2)) {
			dropSQL = "drop table " + tableCode;
		}
		if (dbType.equals(DBConnConfig.MYSQL)) {
			dropSQL = "drop table if exists " + tableCode;
		}
		return dropSQL;
	}

	public static String getFieldExtDesc(int length, int precision) {
		if (length != 0) {
			StringBuffer sb = new StringBuffer();
			sb.append("(");
			sb.append(length);
			if (precision != 0) {
				sb.append(",");
				sb.append(precision);
			}
			sb.append(") ");
			return sb.toString();
		}
		return "";
	}
}
