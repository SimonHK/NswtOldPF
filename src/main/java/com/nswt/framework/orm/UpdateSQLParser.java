package com.nswt.framework.orm;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.orm.TableUpdater.AlterKeyInfo;
import com.nswt.framework.orm.TableUpdater.AlterTableInfo;
import com.nswt.framework.orm.TableUpdater.CommentInfo;
import com.nswt.framework.orm.TableUpdater.CreateTableInfo;
import com.nswt.framework.orm.TableUpdater.DropTableInfo;
import com.nswt.framework.orm.TableUpdater.SQLInfo;
import com.nswt.framework.utility.CaseIgnoreMapx;
import com.nswt.framework.utility.CharsetConvert;
import com.nswt.framework.utility.FileUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;

/**
 * ���ݿ����SQL��������Ŀǰֻ֧��MYSQL ���� : 2010-5-3 <br>
 * ����: NSWT <br>
 * ���䣺nswt@nswt.com.cn <br>
 */
public class UpdateSQLParser {
	private String fileName;

	private ArrayList list = new ArrayList();

	public UpdateSQLParser(String fileName) {
		this.fileName = fileName;
	}

	public String[] convertToSQLArray(String dbType) {
		parse();
		// ��Ҫ�ϲ��Ա�Ķ���޸�
		// ����޸ı����drop�����޸���������
		ArrayList sqlList = new ArrayList();
		TableUpdateInfo[] is = parse();
		for (int i = 0; i < is.length; i++) {
			TableUpdateInfo info = is[i];
			String[] arr = null;
			if (info instanceof AlterTableInfo) {// ��������û��drop����
				AlterTableInfo aInfo = (AlterTableInfo) info;
				boolean dropTableFlag = false;
				for (int j = i + 1; j < is.length; j++) {// ���������drop������֮ǰ���в���������Ҫ��
					TableUpdateInfo info2 = is[j];
					if (info2 instanceof DropTableInfo) {
						DropTableInfo dInfo2 = (DropTableInfo) info2;
						if (dInfo2.TableName.equalsIgnoreCase(aInfo.TableName)) {// ��ͬһ������޸�
							dropTableFlag = true;
							break;
						}
					}
				}
				if (dropTableFlag) {
					continue;
				}
				Mapx map = new CaseIgnoreMapx();
				for (int j = i + 1; j < is.length; j++) {
					TableUpdateInfo info2 = is[j];
					if (info2 instanceof AlterTableInfo) {
						AlterTableInfo aInfo2 = (AlterTableInfo) info2;
						if (aInfo.TableName.equalsIgnoreCase(aInfo2.TableName)) {// ��ͬһ������޸�
							if (aInfo2.Action.equalsIgnoreCase("add")) {
								map.put(aInfo2.OldColumnName, "1");
							}
						}
					}
				}
				if (!aInfo.Action.equalsIgnoreCase("add")) {
					arr = aInfo.toSQLArray(dbType, new ArrayList(), map);
				} else {
					ArrayList togetherList = new ArrayList();
					for (int j = i + 1; j < is.length; j++) {
						TableUpdateInfo info2 = is[j];
						if (info2 instanceof AlterTableInfo) {
							AlterTableInfo aInfo2 = (AlterTableInfo) info2;
							if (aInfo.TableName.equalsIgnoreCase(aInfo2.TableName)
									&& aInfo2.Action.equalsIgnoreCase("add")) {// ��ͬһ����һ�����Ӷ���ֶ�
								togetherList.add(info2);
							} else {
								i = j - 1;
								break;
							}
						} else {
							i = j - 1;
							break;
						}
						if (j == is.length - 1) {
							i = j;
						}
					}
					arr = aInfo.toSQLArray(dbType, togetherList, map);
				}
			} else {
				arr = info.toSQLArray(dbType);
			}
			for (int j = 0; j < arr.length; j++) {
				sqlList.add(arr[j]);
			}

		}
		String[] arr = new String[sqlList.size()];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (String) sqlList.get(i);
		}
		return arr;
	}

	public TableUpdateInfo[] parse() {
		list.clear();
		byte[] bs = FileUtil.readByte(fileName);
		String sql = null;
		try {
			if (StringUtil.isUTF8(bs)) {
				bs = CharsetConvert.webFileUTF8ToGBK(bs);
			}
			sql = new String(bs, "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String[] lines = sql.split("\\n");
		for (int i = 0; i < lines.length; i++) {
			String line = lines[i];
			line = line.replaceAll("\\s+", " ").trim();
			if (StringUtil.isEmpty(line) || line.trim().startsWith("/*")) {
				CommentInfo info = new CommentInfo();
				info.Comment = line;
				list.add(info);
				continue;
			}
			if (line.endsWith(";")) {
				line = line.substring(0, line.length() - 1).trim();
			}
			if (line.toLowerCase().startsWith("alter")) {
				if (line.toLowerCase().indexOf(" primary key") > 0) {
					parseKey(line);
				} else {
					parseAlter(line);
				}
			} else if (line.toLowerCase().startsWith("drop")) {
				parseDrop(line);
			} else if (line.toLowerCase().startsWith("create")) {
				i = parseCreate(i, lines);
			} else {// ��ͨupdate,insert,delete,truncate
				SQLInfo info = new SQLInfo();
				info.SQL = line;
				list.add(info);
			}
		}
		TableUpdateInfo[] is = new TableUpdateInfo[list.size()];
		for (int i = 0; i < list.size(); i++) {
			is[i] = (TableUpdateInfo) list.get(i);
		}
		return is;
	}

	private void parseAlter(String line) {
		String[] arr = line.split("\\s");
		if (arr.length >= 6 && StringUtil.isNotEmpty(arr[2])) {
			AlterTableInfo info = new AlterTableInfo();
			info.TableName = arr[2];
			info.Action = arr[3];
			if (info.Action.equalsIgnoreCase("change")) {
				info.Action = "modify";
			}
			int index = line.toLowerCase().indexOf(" after ");
			if (index > 0) {
				info.AfterColumn = line.substring(index + 7).trim();
				if (info.AfterColumn.endsWith(",")) {
					info.AfterColumn = info.AfterColumn.substring(0, info.AfterColumn.length() - 1).trim();
				}
				if (info.AfterColumn.endsWith(";")) {
					info.AfterColumn = info.AfterColumn.substring(0, info.AfterColumn.length() - 1).trim();
				}
			} else {
				index = line.length();
			}
			int actionIndex = line.toLowerCase().indexOf(" column ");
			if (actionIndex < 0) {
				actionIndex = line.toLowerCase().indexOf(" change ");// change�²���Ҫcolumn�ؼ���
				if (actionIndex < 0) {
					actionIndex = line.toLowerCase().indexOf(" add ");// add�²���Ҫcolumn�ؼ���
					if (actionIndex < 0) {
						actionIndex = line.toLowerCase().indexOf(" modify ") + 8;// modify�²���Ҫcolumn�ؼ���
					} else {
						actionIndex += 5;
					}
				} else {
					actionIndex += 8;
				}
			} else {
				actionIndex += 8;
			}
			line = line.substring(actionIndex, index);
			if (line.endsWith(",")) {
				line = line.substring(0, line.length() - 1).trim();
			}
			index = line.toLowerCase().indexOf(" not null");
			if (index > 0) {
				info.Mandatory = true;
				line = line.substring(0, index);
			}
			if (line.trim().toLowerCase().endsWith(" default")) {
				line = line.substring(0, line.length() - " default".length());
			}
			line = line.trim();
			if (line.endsWith(",")) {
				line = line.substring(0, line.length() - 1);
			}
			// ȡ�ֶ�����
			index = line.indexOf("(");
			String length = "0";
			String precision = "0";
			if (index > 0) {
				String str = line.substring(0, index).trim();
				arr = str.split("\\s");
				if (arr.length == 3) {// ˵���ֶ������б仯
					info.OldColumnName = arr[0];
					info.NewColumnName = arr[1];
					info.ColumnType = convertType(arr[2]);
				} else {
					info.OldColumnName = arr[0];
					info.ColumnType = convertType(arr[1]);
				}
				String fieldExt = line.substring(index + 1, line.lastIndexOf(")"));
				if (fieldExt.indexOf(",") > 0) {
					length = fieldExt.substring(0, fieldExt.indexOf(","));
					precision = fieldExt.substring(fieldExt.indexOf(",") + 1);
				} else {
					length = fieldExt;
				}
			} else {
				arr = line.split("\\s");
				if (arr.length == 3) {// ˵���ֶ������б仯
					info.OldColumnName = arr[0];
					info.NewColumnName = arr[1];
					String columnType = arr[2];
					if (columnType.endsWith(";")) {
						columnType = columnType.substring(0, columnType.length() - 1);
					}
					info.ColumnType = convertType(columnType);
				} else if (arr.length == 2) {
					info.OldColumnName = arr[0];
					String columnType = arr[1];
					if (columnType.endsWith(";")) {
						columnType = columnType.substring(0, columnType.length() - 1);
					}
					info.ColumnType = convertType(columnType);
				} else {// ֻ��dropʱ������
					info.OldColumnName = arr[0];
				}
			}
			info.Length = Integer.parseInt(length);
			info.Precision = Integer.parseInt(precision);
			list.add(info);
		}
	}

	private void parseKey(String line) {
		AlterKeyInfo info = new AlterKeyInfo();
		String[] arr = line.split("\\s");
		if (line.toLowerCase().indexOf(" drop ") > 0) {
			info.TableName = arr[2];
			info.DropFlag = true;
			list.add(info);
		} else {
			int index1 = line.indexOf("(");
			if (index1 < 0) {
				return;
			}
			int index2 = line.indexOf(")");
			String pks = line.substring(index1 + 1, index2);
			info.TableName = arr[2];
			info.DropFlag = false;
			info.NewKeys = pks;
			list.add(info);
		}
	}

	private void parseDrop(String line) {
		String[] arr = line.split("\\s");
		if (arr.length >= 5 && StringUtil.isNotEmpty(arr[4])) {
			DropTableInfo info = new DropTableInfo();
			info.TableName = arr[4].trim();
			if (info.TableName.endsWith(";")) {
				info.TableName = info.TableName.substring(0, info.TableName.length() - 1);
			}
			list.add(info);
		}
	}

	private int parseCreate(int start, String[] lines) {
		String line = lines[start];
		line = line.replaceAll("\\s+", " ").trim();
		String[] arr = line.split("\\s");
		CreateTableInfo info = new CreateTableInfo();
		list.add(info);
		if (arr.length >= 3 && StringUtil.isNotEmpty(arr[2])) {
			info.TableName = arr[2];
			if (info.TableName.indexOf("(") > 0) {
				info.TableName = info.TableName.substring(0, info.TableName.indexOf("("));
			}
		} else {
			return start;
		}
		String pk = null;
		for (int i = start + 1; i < lines.length; i++) {
			line = lines[i];
			line = line.replaceAll("\\s+", " ").trim();
			if (line.startsWith("(")) {
				line = line.substring(1);
			}
			if (StringUtil.isEmpty(line)) {
				continue;
			}
			if (line.equals(",")) {
				continue;
			}
			if (line.toLowerCase().startsWith("primary key")) {// �������
				pk = line.substring(line.indexOf("(") + 1, line.lastIndexOf(")"));
				// ��������
				pk = "," + pk + ",";
				pk = pk.replaceAll("\\s", "").toLowerCase();
				for (int j = 0; j < info.Columns.size(); j++) {
					SchemaColumn sc = (SchemaColumn) info.Columns.get(j);
					if (pk.indexOf("," + sc.getColumnName().toLowerCase() + ",") >= 0) {
						sc = new SchemaColumn(sc.getColumnName(), sc.getColumnType(), sc.getColumnOrder(),
								sc.getLength(), sc.getPrecision(), sc.isMandatory(), true);
						info.Columns.set(j, sc);
					}
				}
				continue;
			}
			if (line.toLowerCase().indexOf(" comment '") > 0) {// ȥ��ע��
				int index = line.toLowerCase().indexOf(" comment '") + " comment '".length();
				boolean EndFlag = false;
				for (int j = index; j < line.length(); j++) {
					char c = line.charAt(j);
					if (c == '\'') {
						if (j != line.length() - 1) {
							if (line.charAt(j + 1) != '\'') {
								EndFlag = true;
								break;
							}
						} else {
							EndFlag = true;
							break;
						}
					}
				}
				if (!EndFlag) {// ˵��ע��δ�ڱ��н���
					for (int k = i + 1; k < lines.length; k++) {
						if (EndFlag) {
							break;
						}
						String str = lines[k];
						for (int j = 0; j < str.length(); j++) {
							char c = str.charAt(j);
							if (c == '\'') {
								if (j != str.length() - 1) {
									if (str.charAt(j + 1) != '\'') {
										lines[k] = str.substring(j + 1);
										i = k - 1;
										EndFlag = true;
										break;
									}
								} else {
									EndFlag = true;
									i = k;
									break;
								}
							}
						}
					}
				}
				if (!EndFlag) {
					throw new RuntimeException("δ�������ֶ�ע�ͣ�" + line);
				}
				line = line.substring(0, index);
			}
			boolean mandatory = false;
			int index = line.toLowerCase().indexOf(" not null");
			if (index > 0) {
				mandatory = true;
				line = line.substring(0, index);
			}
			if (line.trim().toLowerCase().endsWith(" default")) {
				line = line.substring(0, line.length() - " default".length());
			}
			line = line.trim();
			if (line.endsWith(",")) {
				line = line.substring(0, line.length() - 1);
			}
			if (line.startsWith(")")) {// ����������
				line = line.replaceAll("\\s", "");
				if (line.equals(")") || line.equals(");")) {
					return i;
				}
			}

			// ȡ���ֶ���
			String name = line.substring(0, line.indexOf(" "));

			// ȡ�ֶ�����
			index = line.indexOf("(");
			String columnType = null;
			String length = "0";
			String precision = "0";
			if (index > 0) {
				columnType = line.substring(line.indexOf(" ") + 1, index);
				String fieldExt = line.substring(index + 1, line.lastIndexOf(")"));
				if (fieldExt.indexOf(",") > 0) {
					length = fieldExt.substring(0, fieldExt.indexOf(","));
					precision = fieldExt.substring(fieldExt.indexOf(",") + 1);
				} else {
					length = fieldExt;
				}
			} else {
				columnType = line.substring(line.indexOf(" ") + 1);
			}
			columnType = columnType.trim();
			int type = convertType(columnType);
			SchemaColumn sc = new SchemaColumn(name, type, info.Columns.size(), Integer.parseInt(length),
					Integer.parseInt(precision), mandatory, false);// �Ƿ�������ʱδ��
			info.Columns.add(sc);
		}
		return start;
	}

	public static int convertType(String columnType) {
		int type = -1;
		if ("varchar".equalsIgnoreCase(columnType)) {
			type = DataColumn.STRING;
		}
		if ("char".equalsIgnoreCase(columnType)) {
			type = DataColumn.STRING;
		}
		if ("integer".equalsIgnoreCase(columnType)) {
			type = DataColumn.INTEGER;
		}
		if ("int".equalsIgnoreCase(columnType)) {
			type = DataColumn.INTEGER;
		}
		if ("smallint".equalsIgnoreCase(columnType)) {
			type = DataColumn.INTEGER;
		}
		if ("bigint".equalsIgnoreCase(columnType)) {
			type = DataColumn.LONG;
		}
		if ("text".equalsIgnoreCase(columnType)) {
			type = DataColumn.CLOB;
		}
		if ("mediumtext".equalsIgnoreCase(columnType)) {
			type = DataColumn.CLOB;
		}
		if ("date".equalsIgnoreCase(columnType)) {
			type = DataColumn.DATETIME;
		}
		if ("datetime".equalsIgnoreCase(columnType)) {
			type = DataColumn.DATETIME;
		}
		if ("decimal".equalsIgnoreCase(columnType)) {
			type = DataColumn.DOUBLE;
		}
		if ("double".equalsIgnoreCase(columnType)) {
			type = DataColumn.DOUBLE;
		}
		if ("float".equalsIgnoreCase(columnType)) {
			type = DataColumn.FLOAT;
		}
		if (columnType.startsWith("binrary")) {
			type = DataColumn.BLOB;
		}
		return type;
	}
}
