package com.nswt.cms.dataservice;

import com.nswt.framework.Config;
import com.nswt.framework.controls.SelectTag;
import com.nswt.framework.data.DataColumn;
import com.nswt.framework.data.DataRow;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.utility.HtmlUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.Application;
import com.nswt.platform.pub.NoUtil;
import com.nswt.schema.ZDColumnSchema;
import com.nswt.schema.ZDColumnValueSchema;
import com.nswt.schema.ZDColumnValueSet;

/**
 * @author nswt
 * @mail nswt@nswt.com
 * @date 2008-8-12
 */

public class ColumnUtil {
	public static final String PREFIX = "_C_"; // �Զ����ֶ�ǰ׺

	public static final String RELA_TYPE_CATALOG_EXTEND = "0"; // ��Ŀ��չ���Թ���

	public static final String RELA_TYPE_CATALOG_COLUMN = "1"; // ��Ŀ�Զ����ֶι���

	public static final String RELA_TYPE_DOCID = "2"; // �ĵ�����

	public static final String RELA_TYPE_GoodsTypeAttr = "3"; // ��Ʒ���͹���-->��Ʒ����

	public static final String RELA_TYPE_GoodsTypeParam = "4"; // ��Ʒ���������

	// ������ʽ-InputType
	public static String Input = "1";

	public static String Text = "2";

	public static String Select = "3";

	public static String Radio = "4";

	public static String Checkbox = "5";

	public static String DateInput = "6";

	public static String ImageInput = "7";

	public static String HTMLInput = "8";

	public static String TimeInput = "9";

	public static Mapx InputTypeMap = new Mapx();

	static {
		InputTypeMap.put(Input, "�ı���");
		InputTypeMap.put(Text, "�����ı���");
		InputTypeMap.put(Select, "������");
		InputTypeMap.put(Radio, "��ѡ��");
		InputTypeMap.put(Checkbox, "��ѡ��");
		InputTypeMap.put(DateInput, "���ڿ�");
		InputTypeMap.put(TimeInput, "ʱ���");
		InputTypeMap.put(ImageInput, "ý���ͼƬ��");
		InputTypeMap.put(HTMLInput, "HTML");
	}

	// У������-VerifyType
	public static String STRING = "1";

	public static String NUMBER = "2";

	public static String INT = "3";

	public static String NOTNULL = "4";

	public static String EMAIL = "5";

	public static Mapx VerifyTypeMap = new Mapx();

	static {
		VerifyTypeMap.put(STRING, "��");
		VerifyTypeMap.put(NUMBER, "����");
		VerifyTypeMap.put(INT, "����");
		VerifyTypeMap.put(NOTNULL, "�ǿ�");
		VerifyTypeMap.put(EMAIL, "����");
	}

	public static String[][] IsMandatoryArray = new String[][] { { "Y", "����" } };

	public static DataTable getColumn(String relaType, long relaID) {
		return getColumn(relaType, relaID + "");
	}

	public static DataTable getColumn(String relaType, String relaID) {
		return new QueryBuilder(
				"select * from zdcolumn where exists (select columnid from zdcolumnrela where relatype=? and relaid=? and columnid=zdcolumn.id) order by id asc",
				relaType, relaID).executeDataTable();
	}

	public static DataTable getColumn(String relaType, String relaID, String hidden) {
		return new QueryBuilder(
				"select * from zdcolumn where Prop1 != '1' and exists (select 1 from zdcolumnrela where relatype=? and relaid=? and columnid=zdcolumn.id) order by id asc",
				relaType, relaID).executeDataTable();
	}

	public static DataTable getColumnValue(String relaType, long relaID) {
		return getColumnValue(relaType, relaID + "");
	}

	public static DataTable getColumnValue(String relaType, String relaID) {
		return new QueryBuilder("select * from zdcolumnvalue where relatype=? and relaid = ?", relaType, relaID)
				.executeDataTable();
	}

	public static SchemaSet getValueFromRequest(long catalogID, long docID, Mapx Request) {
		DataTable dt = ColumnUtil.getColumn(ColumnUtil.RELA_TYPE_CATALOG_COLUMN, catalogID);
		ZDColumnValueSet set = new ZDColumnValueSet();
		for (int i = 0; i < dt.getRowCount(); i++) {
			ZDColumnValueSchema value = new ZDColumnValueSchema();
			value.setID(NoUtil.getMaxID("ColumnValueID"));
			value.setColumnID(dt.getString(i, "ID"));
			value.setColumnCode(dt.getString(i, "Code"));
			value.setRelaType(ColumnUtil.RELA_TYPE_DOCID);
			value.setRelaID(docID + "");
			ZDColumnSchema column = new ZDColumnSchema();
			column.setID(dt.getString(i, "ID"));
			column.fill();
			if (ColumnUtil.ImageInput.equals(column.getInputType().trim())) {
				String textvalue = Request.getString(ColumnUtil.PREFIX + value.getColumnCode());
				if (StringUtil.isNotEmpty(textvalue) && textvalue.indexOf("upload") > 0) {
					textvalue = textvalue.substring(textvalue.indexOf("upload"));
				}
				value.setTextValue(textvalue);
			} else {
				value.setTextValue(Request.getString(ColumnUtil.PREFIX + value.getColumnCode()));
			}
			set.add(value);
		}
		return set;
	}

	public static void extendDocColumnData(DataTable dt, long catalogID) {
		extendDocColumnData(dt, catalogID + "");
	}

	/**
	 * �õ�DataTable���Զ�������
	 * 
	 * @param dt
	 * @param catalogID
	 */
	public static void extendDocColumnData(DataTable dt, String catalogID) {
		DataTable columnDT = new QueryBuilder(
				"select * from zdcolumn where exists (select * from zdcolumnrela where relatype='"
						+ ColumnUtil.RELA_TYPE_CATALOG_COLUMN + "' and relaid = ? and ColumnID=zdcolumn.ID)", catalogID)
				.executeDataTable();
		String[] columnNames = null;
		String[] verifyColumns = null;
		String[] inputTypeColumns = null;
		if (columnDT.getRowCount() > 0) {
			columnNames = new String[columnDT.getRowCount()];
			verifyColumns = new String[columnDT.getRowCount()];
			inputTypeColumns = new String[columnDT.getRowCount()];
			for (int i = 0; i < columnDT.getRowCount(); i++) {
				columnNames[i] = columnDT.getString(i, "Code");
				if (StringUtil.isNotEmpty(columnDT.getString(i, "VerifyType"))) {
					verifyColumns[i] = columnDT.getString(i, "VerifyType");
				}

				if (StringUtil.isNotEmpty(columnDT.getString(i, "InputType"))) {
					inputTypeColumns[i] = columnDT.getString(i, "InputType");
				} else {

				}
			}
		} else {
			return;
		}
		int colCount = dt.getColCount();
		StringBuffer relaidsb = new StringBuffer();
		for (int i = 0; i < dt.getRowCount(); i++) {
			if (i == 0) {
				relaidsb.append("'");
			} else {
				relaidsb.append(",'");
			}
			relaidsb.append(dt.getString(i, "ID"));
			relaidsb.append("'");
		}

		if (StringUtil.isEmpty(relaidsb.toString())) {
			return;
		}

		for (int i = 0; i < columnNames.length; i++) {
			if (dt.getDataColumn(columnNames[i]) == null) {
				if (StringUtil.isNotEmpty(inputTypeColumns[i].toString())) {
					dt.insertColumn(new DataColumn(columnNames[i], DataColumn.STRING));
				} else if (ColumnUtil.DateInput.equals(inputTypeColumns[i].toString())
						|| ColumnUtil.TimeInput.equals(inputTypeColumns[i].toString())) {
					dt.insertColumn(new DataColumn(columnNames[i], DataColumn.DATETIME));
				} else if (ColumnUtil.Input.equals(inputTypeColumns[i].toString())
						|| ColumnUtil.Text.equals(inputTypeColumns[i].toString())) {
					if (StringUtil.isNotEmpty(verifyColumns[i].toString())
							&& ColumnUtil.INT.equals(verifyColumns[i].toString())) {
						dt.insertColumn(new DataColumn(columnNames[i], DataColumn.INTEGER));
					} else {
						dt.insertColumn(new DataColumn(columnNames[i], DataColumn.STRING));
					}
				} else {
					dt.insertColumn(new DataColumn(columnNames[i], DataColumn.STRING));
				}
			}
		}

		DataTable valueDT = new QueryBuilder("select * from zdcolumnvalue where relatype='"
				+ ColumnUtil.RELA_TYPE_DOCID + "' and relaid in (" + relaidsb + ")").executeDataTable();
		if (valueDT.getRowCount() == 0) {
			return;
		}
		for (int j = 0; j < dt.getRowCount(); j++) {
			for (int k = colCount; k < dt.getColCount(); k++) {
				for (int index = 0; index < valueDT.getRowCount(); index++) {
					if (dt.getString(j, "ID").equals(valueDT.getString(index, "RelaID"))
							&& dt.getDataColumn(k).getColumnName().toLowerCase()
									.equalsIgnoreCase(valueDT.getString(index, "ColumnCode").toLowerCase())) {
						dt.set(j, k, valueDT.getString(index, "TextValue"));
						break;
					}
				}
			}
		}
	}

	public static void extendCatalogColumnData(DataTable dt, String levelStr) {
		for (int i = 0; i < dt.getRowCount(); i++) {
			DataRow dr = dt.getDataRow(i);
			extendCatalogColumnData(dr, levelStr);
		}
	}

	public static void extendCatalogColumnData(DataTable dt, long siteID, String levelStr) {
		if (dt == null || dt.getRowCount() == 0) {
			return;
		}
		DataTable valueDT = new QueryBuilder("select a.InputType,b.ColumnCode,b.TextValue,b.RelaID from zdcolumn a,"
				+ "zdcolumnvalue b where a.ID = b.ColumnID and b.relatype='" + ColumnUtil.RELA_TYPE_CATALOG_EXTEND
				+ "' and b.relaid in (" + StringUtil.join(dt.getColumnValues("ID")) + ")").executeDataTable();
		if (valueDT.getRowCount() == 0) {
			return;
		}
		for (int j = 0; j < valueDT.getRowCount(); j++) {
			for (int i = 0; i < dt.getRowCount(); i++) {
				if (dt.getLong(i, "ID") == valueDT.getLong(j, "RelaID")) {
					String column = valueDT.getString(j, "ColumnCode");
					if (dt.getDataColumn(column) == null) {
						dt.insertColumn(column);
					}
					if (ImageInput.equals(valueDT.getString(j, "InputType"))) {
						dt.set(i, column, levelStr + valueDT.getString(j, "TextValue"));
					} else {
						dt.set(i, column, valueDT.getString(j, "TextValue"));
					}
				}
			}
		}
	}

	public static void extendCatalogColumnData(DataRow dr, String levelStr) {
		extendCatalogColumnData(dr, Application.getCurrentSiteID(), levelStr);
	}

	public static void extendCatalogColumnData(DataRow dr, long siteID, String levelStr) {
		DataTable valueDT = new QueryBuilder("select a.InputType,b.ColumnCode,b.TextValue from zdcolumn a,"
				+ "zdcolumnvalue b where a.ID = b.ColumnID and b.relatype='" + ColumnUtil.RELA_TYPE_CATALOG_EXTEND
				+ "' and b.relaid ='" + dr.getString("ID") + "'").executeDataTable();
		if (valueDT.getRowCount() == 0) {
			return;
		}
		for (int j = 0; j < valueDT.getRowCount(); j++) {
			if (ImageInput.equals(valueDT.getString(j, "InputType"))) {
				dr.insertColumn(valueDT.getString(j, "ColumnCode"), levelStr + valueDT.getString(j, "TextValue"));
			} else {
				dr.insertColumn(valueDT.getString(j, "ColumnCode"), valueDT.getString(j, "TextValue"));
			}

		}
	}

	public static String getHtml(String relaType, String relaID) {
		return getHtml(getColumn(relaType, relaID));
	}

	public static String getHtml(DataTable dt) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < dt.getRowCount(); i++) {
			sb.append(getHtml(dt.getDataRow(i)));
		}
		return sb.toString();
	}

	private static String getHtml(DataRow dr) {
		return getHtml(dr, null);
	}

	/**
	 * ����һ���Զ������ݵ�Html����
	 * 
	 * @param relaType
	 * @param relaID
	 * @return
	 */
	public static String getHtml(String relaType, String relaID, String valueRelaType, String valueRelaID) {
		return getHtml(getColumn(relaType, relaID), getColumnValue(valueRelaType, valueRelaID));
	}

	public static String getHtml(String relaType, String relaID, String valueRelaType, String valueRelaID, String hidden) {
		return getHtml(getColumn(relaType, relaID, hidden), getColumnValue(valueRelaType, valueRelaID));
	}

	public static String getHtml(String relaType, String relaID, String hidden) {
		return getHtml(getColumn(relaType, relaID, hidden));
	}

	public static String getHtml(DataTable dt, DataTable valueDT) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < dt.getRowCount(); i++) {
			sb.append(getHtml(dt.getDataRow(i), valueDT));
		}
		return sb.toString();
	}

	private static String getHtml(DataRow dr, DataTable valueDT) {
		String columnName = dr.getString("Name");
		String columnCode = dr.getString("Code");
		String inputType = dr.getString("inputType");
		String verifyType = dr.getString("verifyType");
		String listOption = dr.getString("listOption");
		String defaultValue = dr.getString("defaultValue");
		String isMandatory = dr.getString("IsMandatory");
		String maxLength = dr.getString("maxLength");
		String HTML = dr.getString("HTML");
		String verifyStr = "verify='" + columnName + "|";
		if ("Y".equals(isMandatory)) {
			verifyStr += "NotNull";
			if (ColumnUtil.ImageInput.equals(inputType)) {
				verifyStr += "&&ͼƬ����Ϊ��|Script=checkMandatory(\"" + ColumnUtil.PREFIX + columnCode + "\")";
			}
		}
		if (ColumnUtil.STRING.equals(verifyType)) {

		} else if (ColumnUtil.NUMBER.equals(verifyType)) {
			verifyStr += "&&Number";
		} else if (ColumnUtil.INT.equals(verifyType)) {
			verifyStr += "&&Int";
		} else if (ColumnUtil.EMAIL.equals(verifyType)) {
			verifyStr += "&&Email";
		}
		if (StringUtil.isNotEmpty(maxLength) && !"0".equals(maxLength)) {
			verifyStr += "&&Length<" + maxLength + "'";
		} else {
			verifyStr += "'";
		}

		if (valueDT != null) {
			for (int i = 0; i < valueDT.getRowCount(); i++) {
				DataRow r = valueDT.getDataRow(i);
				if (columnCode.equalsIgnoreCase(r.getString("columnCode")) && r.getString("TextValue") != null) {
					defaultValue = r.getString("TextValue");
				}
			}
		}

		columnCode = ColumnUtil.PREFIX + columnCode;
		StringBuffer sb = new StringBuffer();
		if (inputType.equals(ColumnUtil.HTMLInput)) {
			sb.append("<tr><td colspan='4' >");
		} else {
			sb.append("<tr><td height='25' align='right' >");
			sb.append(columnName);
			sb.append("��</td><td align='left' colspan='3'>");
		}
		// 1 �����ı�
		if (inputType.equals(ColumnUtil.Input)) {
			sb.append("<input type='text' size='40' id='" + columnCode + "' name='" + columnCode + "' value='"
					+ defaultValue + "' " + verifyStr + " />");
		}
		// 2 �����ı�
		if (inputType.equals(ColumnUtil.Text)) {
			sb.append("<textarea style='width:" + dr.getString("ColSize") + "px;height:" + dr.getString("RowSize")
					+ "px' id='" + columnCode + "' name='" + columnCode + "' " + verifyStr + ">" + defaultValue
					+ "</textarea>");
		}
		// 3 �����б��
		if (inputType.equals(ColumnUtil.Select)) {
			SelectTag select = new SelectTag();
			select.setId(columnCode);
			if ("Y".equals(isMandatory)) {
				select.setVerify(columnName + "|NotNull");
			}
			String[] array = listOption.split("\\n");
			sb.append(select.getHtml(HtmlUtil.arrayToOptions(array, defaultValue, true)));
		}
		// 4 ��ѡ��
		if (inputType.equals(ColumnUtil.Radio)) {
			String[] array = listOption.split("\\n");
			if (StringUtil.isEmpty(defaultValue) && array.length > 0) {
				defaultValue = array[0];
			}
			sb.append(HtmlUtil.arrayToRadios(columnCode, array, defaultValue));
		}
		// 5 ��ѡ��
		if (inputType.equals(ColumnUtil.Checkbox)) {
			String[] array = listOption.split("\\n");
			defaultValue = defaultValue.replaceAll("����", ",");
			defaultValue = defaultValue.replaceAll("��", ",");
			defaultValue = defaultValue.replaceAll("  ", ",");
			defaultValue = defaultValue.replaceAll(" ", ",");
			defaultValue = defaultValue.replaceAll(",,", ",");
			defaultValue = defaultValue.replaceAll("����", ",");
			defaultValue = defaultValue.replaceAll("��", ",");
			String[] checkedArray = defaultValue.split(",");
			sb.append(HtmlUtil.arrayToCheckboxes(columnCode, array, checkedArray));
		}
		// 6 ���ڿ�
		if (inputType.equals(ColumnUtil.DateInput)) {
			sb.append("<input name='" + columnCode + "' id='" + columnCode + "' value='" + defaultValue
					+ "' type='text' size='14' ztype='Date' " + verifyStr + " />");
		}
		//
		if (inputType.equals(ColumnUtil.TimeInput)) {
			sb.append("<input name='" + columnCode + "' id='" + columnCode + "' value='" + defaultValue
					+ "' type='text' size='10' ztype='Time' " + verifyStr + " />");
		}
		// 7 ͼƬ��
		if (inputType.equals(ColumnUtil.ImageInput)) {
			defaultValue = dr.getString("defaultValue");
			if (StringUtil.isEmpty(defaultValue)) {
				defaultValue = (Config.getContextPath() + Config.getValue("UploadDir") + "/"
						+ Application.getCurrentSiteAlias() + "/upload/Image/nopicture.jpg").replaceAll("//", "/");

			}
			if (valueDT != null) {
				for (int i = 0; i < valueDT.getRowCount(); i++) {
					DataRow r = valueDT.getDataRow(i);
					if (columnCode.equalsIgnoreCase(ColumnUtil.PREFIX + r.getString("columnCode"))
							&& r.getString("TextValue") != null) {
						defaultValue = (Config.getContextPath() + Config.getValue("UploadDir") + "/"
								+ Application.getCurrentSiteAlias() + "/" + r.getString("TextValue")).replaceAll("///",
								"/").replaceAll("//", "/");
					}
				}
			}
			sb.append("<img src='" + defaultValue.replaceAll("1_", "s_") + "' name='Img" + columnCode + "' id='Img"
					+ columnCode + "'><input name='button' type='button' onClick=\"custom_img_upload('" + columnCode
					+ "');\" value='���...' /> ͼƬ·�� <input type='text' id='" + columnCode + "' name='" + columnCode
					+ "' size='40' onblur='document.getElementById(\"Img" + columnCode + "\").src=this.value;' value='"
					+ defaultValue + "' " + verifyStr + "/>");
		}
		// 8 HTML
		if (inputType.equals(ColumnUtil.HTMLInput)) {
			sb.append(HTML);
		}
		sb.append("</td></tr>");
		return sb.toString();
	}

	public static String getText(String relaType, String relaID, String valueRelaType, String valueRelaID) {
		return getText(getColumn(relaType, relaID), getColumnValue(valueRelaType, valueRelaID));
	}

	public static String getText(DataTable dt, DataTable valueDT) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < dt.getRowCount(); i++) {
			sb.append(getText(dt.getDataRow(i), valueDT));
		}
		return sb.toString();
	}

	private static String getText(DataRow dr, DataTable valueDT) {
		String columnName = dr.getString("Name");
		String columnCode = dr.getString("Code");
		String inputType = dr.getString("inputType");
		String defaultValue = dr.getString("defaultValue");
		for (int i = 0; i < valueDT.getRowCount(); i++) {
			DataRow r = valueDT.getDataRow(i);
			if (columnCode.equalsIgnoreCase(r.getString("columnCode")) && r.getString("TextValue") != null) {
				defaultValue = r.getString("TextValue");
			}
		}
		columnCode = ColumnUtil.PREFIX + columnCode;
		StringBuffer sb = new StringBuffer();
		sb.append("<tr><td height='25' align='right' >");
		sb.append(columnName);
		sb.append("��</td><td>");
		// 1 �����ı�
		if (inputType.equals("1")) {
			sb.append(defaultValue);
		}
		// 2 �����ı�
		if (inputType.equals("2")) {
			sb.append(defaultValue);
		}
		// 3 �����б��
		if (inputType.equals("3")) {
			sb.append(defaultValue);
		}
		// 4 ��ѡ��
		if (inputType.equals("4")) {
			sb.append(defaultValue);
		}
		// 5 ��ѡ��
		if (inputType.equals("5")) {
			sb.append(defaultValue);
		}
		// 6 ͼƬ��
		if (inputType.equals("6")) {
			// ��Ҫ��һ��д
			// sb.append(defaultValue);
		}
		// 7 ������
		if (inputType.equals("7")) {
			// ��Ҫ��һ��д
			// sb.append(defaultValue);
		}
		sb.append("</td></tr>");
		return sb.toString();
	}
}
