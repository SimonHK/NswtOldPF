package com.nswt.misc;

import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.orm.TableUpdateInfo;
import com.nswt.framework.orm.TableUpdater.AlterKeyInfo;
import com.nswt.framework.orm.TableUpdater.AlterTableInfo;
import com.nswt.framework.orm.TableUpdater.CommentInfo;
import com.nswt.framework.orm.TableUpdater.CreateTableInfo;
import com.nswt.framework.orm.TableUpdater.DropTableInfo;
import com.nswt.framework.orm.TableUpdater.SQLInfo;
import com.nswt.framework.orm.UpdateSQLParser;
import com.nswt.framework.utility.StringUtil;

/*
 * @Author ÍõÓý´º
 * @Date 2016-7-29
 * @Mail nswt@nswt.com.cn
 */
public class UpdateSQLToJavaCode {

	public static void main(String[] args) {
		String fileName = "F:/Workspace_Product/nswtp/DB/update/1.3.2.sql";
		UpdateSQLParser usp = new UpdateSQLParser(fileName);
		TableUpdateInfo[] infos = usp.parse();
		StringBuffer sb = new StringBuffer();
		sb.append("TableUpdateInfo[] infos = new TableUpdateInfo[" + infos.length + "];\n");
		for (int i = 0; i < infos.length; i++) {
			TableUpdateInfo info = infos[i];
			String varName = "info" + i;
			if (info instanceof SQLInfo) {
				SQLInfo info2 = (SQLInfo) info;
				sb.append("SQLInfo " + varName + " = new SQLInfo();\n");
				sb.append(varName + ".SQL = \"" + StringUtil.replaceEx(info2.SQL, "\"", "\\\"") + "\";\n");
			}
			if (info instanceof DropTableInfo) {
				DropTableInfo info2 = (DropTableInfo) info;
				sb.append("DropTableInfo " + varName + " = new DropTableInfo();\n");
				sb.append(varName + ".TableName = \"" + info2.TableName + "\";\n");
			}
			if (info instanceof CreateTableInfo) {
				CreateTableInfo info2 = (CreateTableInfo) info;
				sb.append("CreateTableInfo " + varName + " = new CreateTableInfo();\n");
				sb.append(varName + ".TableName = \"" + info2.TableName + "\";\n");
				sb.append("ArrayList scs" + i + " = new ArrayList(); \n");
				for (int j = 0; j < info2.Columns.size(); j++) {
					SchemaColumn sc = (SchemaColumn) info2.Columns.get(j);
					sb.append("scs" + i + ".add(new SchemaColumn(\"" + sc.getColumnName() + "\", " + sc.getColumnType()
							+ ", " + sc.getColumnOrder() + ", " + sc.getLength() + ", " + sc.getPrecision() + ", "
							+ sc.isMandatory() + ", " + sc.isPrimaryKey() + "));\n");
				}
				sb.append(varName + ".Columns = scs" + i + ";\n");
			}
			if (info instanceof CommentInfo) {
				CommentInfo info2 = (CommentInfo) info;
				sb.append(info2.Comment + "\n");
				sb.append("CommentInfo " + varName + " = new CommentInfo();\n");
				sb.append(varName + ".Comment = \"" + StringUtil.replaceEx(info2.Comment, "\"", "\\\"") + "\";\n");
			}
			if (info instanceof AlterTableInfo) {
				AlterTableInfo info2 = (AlterTableInfo) info;
				sb.append("AlterTableInfo " + varName + " = new AlterTableInfo();\n");
				sb.append(varName + ".Action = \"" + info2.Action + "\";\n");
				sb.append(varName + ".AfterColumn = \"" + info2.AfterColumn + "\";\n");
				sb.append(varName + ".ColumnType = " + info2.ColumnType + ";\n");
				sb.append(varName + ".Length = " + info2.Length + ";\n");
				sb.append(varName + ".NewColumnName = \"" + info2.NewColumnName + "\";\n");
				sb.append(varName + ".OldColumnName = \"" + info2.OldColumnName + "\";\n");
				sb.append(varName + ".Precision = " + info2.Precision + ";\n");
				sb.append(varName + ".Action = \"" + info2.TableName + "\";\n");
				sb.append(varName + ".Action = \"" + info2.Mandatory + "\";\n");
			}
			if (info instanceof AlterKeyInfo) {
				AlterKeyInfo info2 = (AlterKeyInfo) info;
				sb.append("AlterKeyInfo " + varName + " = new AlterKeyInfo();\n");
				sb.append(varName + ".NewKeys = \"" + info2.NewKeys + "\";\n");
				sb.append(varName + ".TableName = \"" + info2.TableName + "\";\n");
				sb.append(varName + ".DropFlag = \"" + info2.DropFlag + "\";\n");
			}
			sb.append("infos[" + i + "] = " + varName + ";\n");
			sb.append("\n");
		}
		System.out.println(sb);
	}
}
