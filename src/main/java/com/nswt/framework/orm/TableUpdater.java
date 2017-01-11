package com.nswt.framework.orm;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.apache.commons.lang.ArrayUtils;

import com.nswt.framework.data.DBConnConfig;
import com.nswt.framework.data.DataColumn;
import com.nswt.framework.utility.CaseIgnoreMapx;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;

/**
 * ���� : 2010-5-3 <br>
 * ����: NSWT <br>
 * ���䣺nswt@nswt.com.cn <br>
 */
public class TableUpdater {

	/**
	 * ������SQL���ת����Oracle���ݵ��﷨
	 */
	public static String toOracleSQL(String fileName) {
		UpdateSQLParser usp = new UpdateSQLParser(fileName);
		String[] arr = usp.convertToSQLArray(DBConnConfig.ORACLE);
		StringBuffer sb = new StringBuffer();
		sb.append("alter session set nls_date_format = 'YYYY-MM-DD HH24:MI:SS';\n");
		for (int i = 0; i < arr.length; i++) {
			String line = arr[i];
			if (StringUtil.isEmpty(line) || line.startsWith("/*")) {
				sb.append(line + "\n");
				continue;
			}
			sb.append(arr[i] + ";\n");
		}
		return sb.toString();
	}

	/**
	 * ������SQL���ת����SQLServer 2005���ݵ��﷨
	 */
	public static String toSQLServerSQL(String fileName) {
		UpdateSQLParser usp = new UpdateSQLParser(fileName);
		String[] arr = usp.convertToSQLArray(DBConnConfig.MSSQL);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			String line = arr[i];
			if (StringUtil.isEmpty(line) || line.startsWith("/*")) {
				sb.append(line + "\n");
				continue;
			}
			sb.append(arr[i] + "\ngo\n");
		}
		return sb.toString();
	}

	/**
	 * ������SQL���ת����Mysql���ݵ��﷨
	 */
	public static String toMysqlrSQL(String fileName) {
		UpdateSQLParser usp = new UpdateSQLParser(fileName);
		String[] arr = usp.convertToSQLArray(DBConnConfig.MYSQL);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			String line = arr[i];
			if (StringUtil.isEmpty(line) || line.startsWith("/*")) {
				sb.append(line + "\n");
				continue;
			}
			sb.append(arr[i] + ";\n");
		}
		return sb.toString();
	}

	/**
	 * ������SQL���ת����DB2���ݵ��﷨
	 */
	public static String toDB2SQL(String fileName) {
		UpdateSQLParser usp = new UpdateSQLParser(fileName);
		String[] arr = usp.convertToSQLArray(DBConnConfig.DB2);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			String line = arr[i];
			if (StringUtil.isEmpty(line) || line.startsWith("/*")) {
				sb.append(line + "\n");
				continue;
			}
			sb.append(arr[i] + ";\n");
		}
		return sb.toString();
	}

	public static class DropTableInfo extends TableUpdateInfo implements Serializable {
		private static final long serialVersionUID = 1L;
		public String TableName;

		public String[] toSQLArray(String dbType) {
			return new String[] { TableCreator.dropTable(TableName, dbType) };
		}
	}

	public static class CreateTableInfo extends TableUpdateInfo implements Serializable {
		private static final long serialVersionUID = 1L;
		public String TableName;
		public ArrayList Columns = new ArrayList();

		public String[] toSQLArray(String dbType) {
			try {
				SchemaColumn[] scs = new SchemaColumn[Columns.size()];
				for (int i = 0; i < scs.length; i++) {
					scs[i] = (SchemaColumn) Columns.get(i);
				}
				return new String[] { TableCreator.createTable(scs, TableName, dbType) };
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	public static class AlterKeyInfo extends TableUpdateInfo implements Serializable {
		private static final long serialVersionUID = 1L;
		public String TableName;
		public String NewKeys;
		public boolean DropFlag;

		public String[] toSQLArray(String dbType) {

			if (DropFlag) {// Mysql �� Oracle���Թ��ˣ�ͨ��
				String sql = "alter table " + TableName + " drop primary key";
				if (DBConnConfig.MSSQL.equals(dbType)) {
					sql = "alter table " + TableName + " drop constraint PK_" + TableName;
				}
				return new String[] { sql };
			} else {
				String sql = "alter table " + TableName + " add primary key (" + NewKeys + ")";
				if (DBConnConfig.MSSQL.equals(dbType)) {
					sql = "alter table " + TableName + " add constraint PK_" + TableName
							+ " primary key  NONCLUSTERED(" + NewKeys + ")";
				}
				return new String[] { sql };
			}
		}
	}

	public static class AlterTableInfo extends TableUpdateInfo implements Serializable {
		private static final long serialVersionUID = 1L;
		public String TableName;
		public String Action;// add ,drop ,moidfy
		public int ColumnType;
		public String OldColumnName;
		public String NewColumnName;
		public String AfterColumn;
		public int Length;
		public int Precision;
		public boolean Mandatory;

		public String[] toSQLArray(String dbType) {
			return toSQLArray(dbType, new ArrayList(), new CaseIgnoreMapx());
		}

		public String[] toSQLArray(String dbType, ArrayList togetherColumns, Mapx exclusiveMapx) {
			exclusiveMapx.put(OldColumnName, "1");// ����oracle�³�������
			if (!togetherColumns.contains(this)) {
				togetherColumns.add(0, this);// ����ֶ�һ������
			}
			if (dbType.equals(DBConnConfig.MYSQL)) {
				if (Action.equalsIgnoreCase("add")) {
					String[] arr = new String[togetherColumns.size()];
					for (int i = 0; i < togetherColumns.size(); i++) {
						AlterTableInfo info = (AlterTableInfo) togetherColumns.get(i);
						String fieldExt = TableCreator.getFieldExtDesc(info.Length, info.Precision).trim();
						String sql = "alter table " + info.TableName + " add column " + info.OldColumnName + " "
								+ TableCreator.toSQLType(info.ColumnType, dbType) + fieldExt;
						if (info.Mandatory) {
							sql += " not null";
						}
						if (StringUtil.isNotEmpty(info.AfterColumn)) {
							sql += " after " + info.AfterColumn;
						}
						arr[i] = sql;
					}
					return arr;
				}
				if (Action.equalsIgnoreCase("drop")) {
					String sql = "alter table " + TableName + " drop column " + OldColumnName;
					return new String[] { sql };
				}
				if (Action.equalsIgnoreCase("modify") || Action.equalsIgnoreCase("change")) {
					String sql = "alter table " + TableName + " change column " + OldColumnName;
					if (StringUtil.isNotEmpty(NewColumnName)) {
						sql += " " + NewColumnName;
					} else {
						sql += " " + OldColumnName;
					}
					if (ColumnType == -1) {
						System.out.println(1);
					}
					sql += " " + TableCreator.toSQLType(ColumnType, dbType);
					String fieldExt = TableCreator.getFieldExtDesc(Length, Precision).trim();
					sql += fieldExt;
					if (Mandatory) {
						sql += " not null";
					}
					return new String[] { sql };
				}
			}
			if (dbType.equals(DBConnConfig.ORACLE)) {
				if (Action.equalsIgnoreCase("add")) {
					StringBuffer sb = new StringBuffer();
					// if (TableName.equalsIgnoreCase("zcsite")) {
					// System.out.println(1);
					// }
					if (StringUtil.isNotEmpty(AfterColumn)) {
						Schema schema = SchemaUtil.findSchema(TableName);
						SchemaColumn[] scs = (SchemaColumn[]) schema.Columns.clone();
						ArrayList list = new ArrayList();
						for (int i = 0; i < scs.length; i++) {
							if (exclusiveMapx == null || !exclusiveMapx.containsKey(scs[i].getColumnName())) {
								list.add(scs[i].getColumnName());
							}
							for (int j = togetherColumns.size() - 1; j >= 0; j--) {// after��ͬ�ģ���ӵ�Ҫ����ǰ��
								AlterTableInfo info = (AlterTableInfo) togetherColumns.get(j);
								String columnName = info.AfterColumn;
								if (scs[i].getColumnName().equalsIgnoreCase(columnName)) {
									list.add("'0' as " + info.OldColumnName);
								}
							}
						}
						// sb.append(TableCreator.dropTable(TableName + "_TMP",
						// dbType) + "\n");
						sb.append("create table " + TableName + "_TMP as select " + StringUtil.join(list) + " from "
								+ TableName + "\n");
						for (int j = 0; j < togetherColumns.size(); j++) {
							AlterTableInfo info = (AlterTableInfo) togetherColumns.get(j);
							String fieldExt = "";
							if (info.ColumnType == DataColumn.STRING) {
								fieldExt = TableCreator.getFieldExtDesc(Length, Precision).trim();
							}
							if (Mandatory) {// �ǿվ͵�����ֵ
								if (info.ColumnType == DataColumn.STRING) {
									sb.append("update " + info.TableName + "_TMP set " + info.OldColumnName + "='0'\n");
								} else if (info.ColumnType == DataColumn.DATETIME) {// ������Ҫ����null���޸����ͣ�����notnull
									sb.append("update " + info.TableName + "_TMP set " + info.OldColumnName + "=null\n");
									sb.append("alter table " + info.TableName + "_TMP modify " + info.OldColumnName
											+ " " + TableCreator.toSQLType(info.ColumnType, dbType) + fieldExt + "\n");
									sb.append("update " + info.TableName + "_TMP set " + info.OldColumnName
											+ "='1970-01-01 00:00:00'\n");
								} else {// ����������Ҫ����null���޸����ͣ�����notnull
									sb.append("update " + info.TableName + "_TMP set " + info.OldColumnName + "=null\n");
									sb.append("alter table " + info.TableName + "_TMP modify " + info.OldColumnName
											+ " " + TableCreator.toSQLType(info.ColumnType, dbType) + fieldExt + "\n");
									sb.append("update " + info.TableName + "_TMP set " + info.OldColumnName + "=0\n");
								}
							} else {
								sb.append("update " + info.TableName + "_TMP set " + info.OldColumnName + "=null\n");
							}
							sb.append("alter table " + info.TableName + "_TMP modify " + info.OldColumnName + " "
									+ TableCreator.toSQLType(info.ColumnType, dbType) + fieldExt
									+ (info.Mandatory ? " not null" : "") + "\n");
						}
						sb.append("drop table " + TableName + "\n");
						sb.append("rename " + TableName + "_TMP to " + TableName + "\n");
						addPrimaryKey(sb, TableName, dbType);// ���¼�����
						addIndexes(sb, TableName, dbType);// ���¼�����
					} else {
						for (int j = 0; j < togetherColumns.size(); j++) {
							AlterTableInfo info = (AlterTableInfo) togetherColumns.get(j);
							String fieldExt = "";
							if (info.ColumnType == DataColumn.STRING) {
								fieldExt = TableCreator.getFieldExtDesc(info.Length, info.Precision).trim();
							}
							sb.append("alter table " + info.TableName + " add " + info.OldColumnName + " "
									+ TableCreator.toSQLType(info.ColumnType, dbType) + fieldExt);
							if (info.Mandatory) {
								sb.append(" not null");
							}
							sb.append("\\n");
						}
					}
					return sb.toString().split("\\n");
				}
				if (Action.equalsIgnoreCase("drop")) {
					String sql = "alter table " + TableName + " drop column " + OldColumnName;
					return new String[] { sql };
				}
				if (Action.equalsIgnoreCase("modify") || Action.equalsIgnoreCase("change")) {
					if (StringUtil.isNotEmpty(NewColumnName) && !NewColumnName.equalsIgnoreCase(OldColumnName)) {
						Schema schema = SchemaUtil.findSchema(TableName);
						SchemaColumn[] scs = (SchemaColumn[]) schema.Columns.clone();
						ArrayList list = new ArrayList();
						for (int i = 0; i < scs.length; i++) {
							if (exclusiveMapx == null || !exclusiveMapx.containsKey(scs[i].getColumnName())) {
								if (scs[i].getColumnName().equalsIgnoreCase(NewColumnName)) {
									list.add(OldColumnName + " as " + NewColumnName);
								} else {
									list.add(scs[i].getColumnName());
								}
							}
						}
						StringBuffer sb = new StringBuffer();
						sb.append("create table " + TableName + "_TMP as select " + StringUtil.join(list) + " from "
								+ TableName + "\n");
						sb.append("drop table " + TableName + "\n");
						sb.append("rename " + TableName + "_TMP to " + TableName + "\n");
						addPrimaryKey(sb, TableName, dbType);// ���¼�����
						addIndexes(sb, TableName, dbType);// ���¼�����
						return sb.toString().split("\\n");
					}
					String sql = "alter table " + TableName + " modify " + OldColumnName;
					sql += " " + TableCreator.toSQLType(ColumnType, dbType);
					if (ColumnType == DataColumn.STRING) {
						String fieldExt = TableCreator.getFieldExtDesc(Length, Precision).trim();
						sql += fieldExt;
					}
					if (Mandatory) {
						sql += " not null";
					}
					return new String[] { sql };
				}
			}
			if (dbType.equals(DBConnConfig.MSSQL)) {
				if (Action.equalsIgnoreCase("add")) {
					ArrayList sqlList = new ArrayList();
					if (StringUtil.isNotEmpty(AfterColumn)) {
						Schema schema = SchemaUtil.findSchema(TableName);
						SchemaColumn[] scs = (SchemaColumn[]) schema.Columns.clone();
						ArrayList listInsert = new ArrayList();
						ArrayList listSelect = new ArrayList();
						ArrayList pkList = new ArrayList();
						for (int i = scs.length - 1; i >= 0; i--) {
							if (exclusiveMapx == null || !exclusiveMapx.containsKey(scs[i].getColumnName())) {
								listInsert.add(scs[i].getColumnName());
								listSelect.add(scs[i].getColumnName());
							}
							if (scs[i].isPrimaryKey()) {
								pkList.add(scs[i].getColumnName());
							}
							for (int j = 0; j < togetherColumns.size(); j++) {
								AlterTableInfo info = (AlterTableInfo) togetherColumns.get(j);
								String columnName = info.AfterColumn;
								if (scs[i].getColumnName().equalsIgnoreCase(columnName)) {
									SchemaColumn sc = new SchemaColumn(info.OldColumnName, info.ColumnType, i,
											info.Length, info.Precision, info.Mandatory, false);
									scs = (SchemaColumn[]) ArrayUtils.add(scs, i + 1, sc);
									// ����Ǳ��� �ģ�����Ҫ����Ϊ0
									if (info.Mandatory) {
										listInsert.add(info.OldColumnName);
										listSelect.add("''0'' as " + info.OldColumnName);
									}
								}
							}
						}
						// ȥ���ظ�����
						for (int i = scs.length - 1; i > 0; i--) {
							boolean duplicateFlag = false;
							for (int j = i - 1; j >= 0; j--) {
								if (scs[i].getColumnName().equalsIgnoreCase(scs[j].getColumnName())) {
									scs = (SchemaColumn[]) ArrayUtils.remove(scs, i);
									duplicateFlag = true;
									break;
								}
							}
							if (duplicateFlag) {
								continue;
							}
							if (exclusiveMapx != null && exclusiveMapx.containsKey(scs[i].getColumnName())) {
								boolean removeFlag = true;
								for (int j = 0; j < togetherColumns.size(); j++) {
									AlterTableInfo info = (AlterTableInfo) togetherColumns.get(j);
									String columnName = info.OldColumnName;
									if (scs[i].getColumnName().equalsIgnoreCase(columnName)) {
										removeFlag = false;
									}
								}
								if (removeFlag) {
									scs = (SchemaColumn[]) ArrayUtils.remove(scs, i);
								}
							}
						}
						try {
							sqlList.add(TableCreator.dropTable(TableName + "_TMP", dbType));
							String sql = TableCreator.createTable(scs, TableName + "_TMP", dbType);
							// ȥ������
							int index = sql.indexOf(",\n\tconstraint");
							sql = sql.substring(0, index) + ")";
							sqlList.add(sql);
						} catch (Exception e) {
							e.printStackTrace();
						}
						// ת������
						sqlList.add("if exists(select * from " + TableName + ") exec ('insert into " + TableName
								+ "_TMP (" + StringUtil.join(listInsert) + ") select " + StringUtil.join(listSelect)
								+ " from " + TableName + "')");
						sqlList.add("drop table " + TableName);// ɾ���ɱ�
						sqlList.add("sp_rename '" + TableName + "_TMP', '" + TableName + "', 'OBJECT'");// ��������ʱ��Ϊ�ɱ���
						StringBuffer sb = new StringBuffer();
						addPrimaryKey(sb, TableName, dbType);// ���¼�����
						addIndexes(sb, TableName, dbType);// ���¼�����
						String[] arr = sb.toString().split("\\n");
						for (int i = 0; i < arr.length; i++) {
							sqlList.add(arr[i]);
						}
					} else {
						for (int j = 0; j < togetherColumns.size(); j++) {
							AlterTableInfo info = (AlterTableInfo) togetherColumns.get(j);
							String fieldExt = "";
							if (info.ColumnType == DataColumn.STRING) {
								fieldExt = TableCreator.getFieldExtDesc(info.Length, info.Precision).trim();
							}
							String sql = "alter table " + info.TableName + " add " + info.OldColumnName + " "
									+ TableCreator.toSQLType(info.ColumnType, dbType) + fieldExt;
							if (info.Mandatory) {
								sql += " not null";
							}
							sqlList.add(sql);
						}
					}
					String[] arr = new String[sqlList.size()];
					for (int i = 0; i < arr.length; i++) {
						arr[i] = (String) sqlList.get(i);
					}
					return arr;
				}
				if (Action.equalsIgnoreCase("drop")) {
					String sql = "alter table " + TableName + " drop column " + OldColumnName;
					return new String[] { sql };
				}
				if (Action.equalsIgnoreCase("modify") || Action.equalsIgnoreCase("change")) {
					ArrayList sqlList = new ArrayList();
					SchemaColumn column = SchemaUtil.findColumn(TableName, OldColumnName);
					if (column == null) {
						column = SchemaUtil.findColumn(TableName, NewColumnName);// �ֶ����޸�ʱ��Ҫ����
					}
					if (column == null) {
						System.out.println(1);
					}
					if (column.isPrimaryKey()) {// ��ȥ������
						sqlList.add("alter table " + TableName + " drop constraint PK_" + TableName);
					}
					String sql = "alter table " + TableName + " alter column " + OldColumnName;
					sql += " " + TableCreator.toSQLType(ColumnType, dbType);
					String fieldExt = "";
					if (ColumnType == DataColumn.STRING) {
						fieldExt = TableCreator.getFieldExtDesc(Length, Precision).trim();
					}
					sql += fieldExt;
					if (Mandatory) {
						sql += " not null";
					}
					sqlList.add(sql);
					if (StringUtil.isNotEmpty(NewColumnName) && !NewColumnName.equalsIgnoreCase(OldColumnName)) {
						sqlList.add(" sp_rename '" + TableName + "." + OldColumnName + "','" + NewColumnName
								+ "','column'");
					}
					if (column.isPrimaryKey()) {// �ټ�������
						SchemaColumn[] scs = SchemaUtil.findSchema(TableName).Columns;
						ArrayList pkList = SchemaUtil.getPrimaryKeyColumns(scs);
						sqlList.add("alter table " + TableName + " add constraint PK_" + TableName
								+ " primary key NONCLUSTERED(" + StringUtil.join(pkList) + ")");
					}
					String[] arr = new String[sqlList.size()];
					for (int i = 0; i < arr.length; i++) {
						arr[i] = (String) sqlList.get(i);
					}
					return arr;
				}
			}
			return null;
		}
	}

	public static class SQLInfo extends TableUpdateInfo implements Serializable {
		private static final long serialVersionUID = 1L;
		public String SQL;

		public String[] toSQLArray(String dbType) {
			if (SQL.trim().endsWith(";")) {
				SQL = SQL.substring(0, SQL.length() - 1).trim();
			}
			return new String[] { SQL };
		}
	}

	public static class CommentInfo extends TableUpdateInfo implements Serializable {
		private static final long serialVersionUID = 1L;
		public String Comment;

		public String[] toSQLArray(String dbType) {
			return new String[] { Comment };
		}
	}

	public static void addIndexes(StringBuffer sb, String tableName, String dbType) {
		try {
			Class c = Class.forName("com.nswt.platform.pub.Install");
			Method m = c.getMethod("getIndexSQLForTable", new Class[] { String.class, String.class });
			Object obj = m.invoke(null, new Object[] { tableName, dbType });
			ArrayList list = (ArrayList) obj;
			for (int i = 0; i < list.size(); i++) {
				sb.append(list.get(i) + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void addPrimaryKey(StringBuffer sb, String tableName, String dbType) {
		try {
			SchemaColumn[] scs = SchemaUtil.findSchema(tableName).Columns;
			ArrayList pkList = SchemaUtil.getPrimaryKeyColumns(scs);
			AlterKeyInfo info = new AlterKeyInfo();
			info.TableName = tableName;
			info.NewKeys = StringUtil.join(pkList);
			String[] arr = info.toSQLArray(dbType);
			for (int i = 0; i < arr.length; i++) {
				sb.append(arr[i] + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
