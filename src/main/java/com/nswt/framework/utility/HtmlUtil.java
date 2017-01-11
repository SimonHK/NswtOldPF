package com.nswt.framework.utility;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.nswt.framework.Constant;
import com.nswt.framework.cache.CacheManager;
import com.nswt.framework.controls.HtmlTable;
import com.nswt.framework.controls.SelectTag;
import com.nswt.framework.data.DataRow;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;

/**
 * HTML������
 * 
 * @Author NSWT
 * @Date 2007-6-23
 * @Mail nswt@nswt.com.cn
 */
public class HtmlUtil {
	/**
	 * ��ZDCode�еĻ�������ת��ΪMapx
	 */
	public static Mapx codeToMapx(String CodeType) {
		return CacheManager.getMapx("Code", CodeType);
	}

	/**
	 * ��ZDCode�еĻ�������ת��Ϊ������
	 */
	public static String codeToOptions(String CodeType) {
		return codeToOptions(CodeType, null);
	}

	/**
	 * ��ZDCode�еĻ�������ת��Ϊ������
	 */
	public static String codeToOptions(String CodeType, Object checkedValue) {
		return mapxToOptions(CacheManager.getMapx("Code", CodeType), checkedValue);
	}

	/**
	 * ��ZDCode�еĻ�������ת��Ϊ�����򣬲��Զ�����һ����ѡ��
	 */
	public static String codeToSelectOptions(String CodeType, boolean addBlankOptionFlag) {
		return mapxToOptions(CacheManager.getMapx("Code", CodeType),"option", null, addBlankOptionFlag);
	}
	
	/**
	 * ��ZDCode�еĻ�������ת��Ϊ������
	 */
	public static String codeToSelectOptions(String CodeType, Object checkedValue) {
		return mapxToOptions(CacheManager.getMapx("Code", CodeType),"option", checkedValue,false);
	}

	/**
	 * ��ZDCode�еĻ�������ת��Ϊ�����򣬲��Զ�����һ����ѡ��
	 */
	public static String codeToOptions(String CodeType, boolean addBlankOptionFlag) {
		return mapxToOptions(CacheManager.getMapx("Code", CodeType),"span", null, addBlankOptionFlag);
	}

	/**
	 * ��ZDCode�еĻ�������ת��Ϊ������
	 */
	public static String codeToOptions(String CodeType, Object checkedValue, boolean addBlankOptionFlag) {
		return mapxToOptions(CacheManager.getMapx("Code", CodeType),"span", checkedValue, addBlankOptionFlag);
	}

	/**
	 * ��ZDCode�еĻ�������ת��Ϊ��ѡ��
	 */
	public static String codeToRadios(String name, String CodeType) {
		return codeToRadios(name, CodeType, null, false);
	}

	/**
	 * ��ZDCode�еĻ�������ת��Ϊ��ѡ��,�����ó�ֵ
	 */
	public static String codeToRadios(String name, String CodeType, Object checkedValue) {
		return codeToRadios(name, CodeType, checkedValue, false);
	}

	/**
	 * ��ZDCode�еĻ�������ת��Ϊ��ѡ��
	 */
	public static String codeToRadios(String name, String CodeType, boolean direction) {
		return codeToRadios(name, CodeType, null, direction);
	}

	/**
	 * ��ZDCode�еĻ�������ת��Ϊ��ѡ��
	 */
	public static String codeToRadios(String name, String CodeType, Object checkedValue, boolean direction) {
		return mapxToRadios(name, CacheManager.getMapx("Code", CodeType), checkedValue, direction);
	}

	/**
	 * ��ZDCode�еĻ�������ת��Ϊ��ѡ��
	 */
	public static String codeToCheckboxes(String name, String CodeType) {
		return mapxToCheckboxes(name, CacheManager.getMapx("Code", CodeType));
	}

	/**
	 * ��ZDCode�еĻ�������ת��Ϊ��ѡ��,����������ʾ����
	 */
	public static String codeToCheckboxes(String name, String CodeType, boolean direction) {
		return codeToCheckboxes(name, CodeType, new String[0], direction);
	}

	/**
	 * ��ZDCode�еĻ�������ת��Ϊ��ѡ��,�����ó�ʼѡ�е�ֵ
	 */
	public static String codeToCheckboxes(String name, String CodeType, DataTable checkedDT) {
		return codeToCheckboxes(name, CodeType, checkedDT, false);
	}

	/**
	 * ��ZDCode�еĻ�������ת��Ϊ��ѡ��
	 */
	public static String codeToCheckboxes(String name, String CodeType, DataTable checkedDT, boolean direction) {
		return mapxToCheckboxes(name, CacheManager.getMapx("Code", CodeType), checkedDT, null, direction);
	}

	public static String codeToCheckboxes(String name, String CodeType, String[] checkedArray) {
		return codeToCheckboxes(name, CodeType, checkedArray, false);
	}

	/**
	 * ��ZDCode�еĻ�������ת��Ϊ��ѡ��
	 */
	public static String codeToCheckboxes(String name, String CodeType, String[] checkedArray, boolean direction) {
		return mapxToCheckboxes(name, CacheManager.getMapx("Code", CodeType), checkedArray, null, direction);
	}

	/**
	 * ��ZDCode�еĻ�������ת��Ϊ��ѡ��,�Ա��ķ�ʽչ��
	 * 
	 * @param map
	 * @return
	 */

	public static String mapxToCheckboxesTable(String name, Mapx map, Object[] checkedArray, Object[] disabledValue,
			int colNum) {
		if (colNum < 1) {
			colNum = 1;
		}
		StringBuffer sb = new StringBuffer();
		sb.append("<table width='100%'>");
		Object[] keys = map.keyArray();
		for (int k = 0; k < keys.length; k++) {
			if (k % colNum == 0) {
				sb.append("<tr>");
			}
			Object value = keys[k];
			sb.append("<td width='" + (100 / colNum) + "%'><input type='checkbox'");
			sb.append(" name='" + name);
			sb.append("' id='" + name + "_" + k + "' value='");
			sb.append(value);
			sb.append("'");
			if (disabledValue != null) {
				for (int j = 0; j < disabledValue.length; j++) {
					if (value.equals(disabledValue[j])) {
						sb.append(" disabled");
						break;
					}
				}
			}

			if (checkedArray != null) {
				for (int j = 0; j < checkedArray.length; j++) {
					if (value.equals(checkedArray[j])) {
						sb.append(" checked");
						break;
					}
				}
			}

			sb.append(" >");
			sb.append("<label for='" + name + "_" + k + "'>");
			sb.append(map.get(value));
			sb.append("</label>&nbsp;</td>");
			if (k % colNum == colNum - 1) {
				sb.append("</tr>");
			}
		}
		if (keys.length % colNum != 0) {
			for (int i = 0; i < colNum - keys.length % colNum; i++) {
				sb.append("<td>&nbsp;</td>");
			}
			sb.append("</tr>");
		}
		sb.append("</table>");
		return sb.toString();
	}

	public static String mapxToCheckboxesTable(String name, Mapx map, DataTable checkedDT, Object[] disabledValue,
			int colNum) {
		String[] checkedArray = new String[checkedDT.getRowCount()];
		for (int i = 0; i < checkedDT.getRowCount(); i++) {
			checkedArray[i] = checkedDT.getString(i, 0);
		}
		return mapxToCheckboxesTable(name, map, checkedArray, disabledValue, colNum);
	}

	public static String mapxToCheckboxesTable(String name, String CodeType, DataTable checkedDT,
			Object[] disabledValue, int colNum) {
		return mapxToCheckboxesTable(name, CacheManager.getMapx("Code", CodeType), checkedDT, disabledValue, colNum);
	}
	
	public static String codeToCheckboxesTable(String name,String CodeType, DataTable checkedDT,int colNum){
		return codeToCheckboxesTable(name, CodeType ,checkedDT, null,colNum);
	}
	
	public static String codeToCheckboxesTable(String name,String CodeType,DataTable checkedDT,Object[] disabledValue,int colNum){
		return mapxToCheckboxesTable(name, CodeType ,checkedDT,disabledValue, colNum);
	}

	public static String mapxToCheckboxesTable(String name, String CodeType, Object[] checkedArray,
			Object[] disabledValue, int colNum) {
		return mapxToCheckboxesTable(name, CacheManager.getMapx("Code", CodeType), checkedArray, disabledValue, colNum);
	}

	public static String mapxToCheckboxesTable(String name, String CodeType, Object[] checkedArray, int colNum) {
		return mapxToCheckboxesTable(name, CacheManager.getMapx("Code", CodeType), checkedArray, null, colNum);
	}

	public static String codeToCheckboxesTable(String name, String CodeType, int colNum) {
		return mapxToCheckboxesTable(name, CodeType, null, colNum);
	}

	public static String codeToCheckboxesTable(String name, String CodeType, Object[] checkedArray, int colNum) {
		return mapxToCheckboxesTable(name, CodeType, checkedArray, new String[0], colNum);
	}

	public static String mapxToOptions(Map map) {
		return mapxToOptions(map, null);
	}

	public static String mapxToOptions(Map map, Object checkedValue) {
		return mapxToOptions(map, checkedValue, false);
	}

	public static String mapxToOptions(Map map, boolean addBlankOptionFlag) {
		return mapxToOptions(map, null, addBlankOptionFlag);
	}
	
	public static String mapxToOptions(Map map,Object checkedValue, boolean addBlankOptionFlag) {
		return mapxToOptions(map, "span",null, addBlankOptionFlag);
	}

	public static String mapxToOptions(Map map,String type, Object checkedValue, boolean addBlankOptionFlag) {
		StringBuffer sb = new StringBuffer();
		if (addBlankOptionFlag) {
			sb.append("<"+type+" value=''></"+type+">");
		}
		if (map == null) {
			return sb.toString();
		}
		Object[] keys = map.keySet().toArray();
		for (int i = 0; i < keys.length; i++) {
			sb.append("<"+type+" value=\"");
			Object v = keys[i];
			sb.append(v);
			if (v != null && v.equals(checkedValue)) {
				sb.append("\" selected='true' >");
			} else {
				sb.append("\">");
			}
			sb.append(map.get(v));
			sb.append("</"+type+">");
		}
		return sb.toString();
	}

	public static String mapxToRadios(String name, Map map) {
		return mapxToRadios(name, map, null, false);
	}

	public static String mapxToRadios(String name, Map map, Object checkedValue) {
		return mapxToRadios(name, map, checkedValue, false);
	}

	public static String mapxToRadios(String name, Map map, Object checkedValue, boolean direction) {
		StringBuffer sb = new StringBuffer();
		Object[] keys = map.keySet().toArray();
		for (int i = 0; i < keys.length; i++) {
			Object value = keys[i];
			sb.append("<input type='radio' name='" + name);
			sb.append("' id='" + name + "_" + i + "' value='");
			sb.append(value);
			if (value.equals(checkedValue)) {
				sb.append("' checked >");
			} else {
				sb.append("' >");
			}
			sb.append("<label for='" + name + "_" + i + "'>");
			sb.append(map.get(value));
			sb.append("</label>");
			if (direction) {
				sb.append("<br>");
			}
		}
		return sb.toString();
	}

	public static String mapxToCheckboxes(String name, Mapx map) {
		return mapxToCheckboxes(name, map, (Object[]) null, null);
	}

	public static String mapxToCheckboxes(String name, Mapx map, Object[] checkedArray) {
		return mapxToCheckboxes(name, map, checkedArray, null);
	}

	// ���ò���ѡ��ѡ��
	public static String mapxToCheckboxes(String name, Mapx map, DataTable checkedDT, Object[] disabledValue) {
		return mapxToCheckboxes(name, map, checkedDT, disabledValue, false);
	}

	public static String mapxToCheckboxes(String name, Mapx map, DataTable checkedDT, Object[] disabledValue,
			boolean direction) {
		String[] checkedArray = new String[checkedDT.getRowCount()];
		for (int i = 0; i < checkedDT.getRowCount(); i++) {
			checkedArray[i] = checkedDT.getString(i, 0);
		}
		return mapxToCheckboxes(name, map, checkedArray, disabledValue, direction);
	}

	public static String mapxToCheckboxes(String name, Mapx map, Object[] checkedArray, Object[] disabledValue) {
		return mapxToCheckboxes(name, map, checkedArray, disabledValue, false);
	}

	public static String mapxToCheckboxes(String name, Mapx map, Object[] checkedArray, Object[] disabledValue,
			boolean direction) {
		StringBuffer sb = new StringBuffer();
		Object[] keys = map.keyArray();
		for (int k = 0; k < keys.length; k++) {
			Object value = keys[k];
			sb.append("<input type='checkbox'");
			sb.append(" name='" + name);
			sb.append("' id='" + name + "_" + k + "' value='");
			sb.append(value);
			sb.append("'");
			if (disabledValue != null) {
				for (int j = 0; j < disabledValue.length; j++) {
					if (value.equals(disabledValue[j])) {
						sb.append(" disabled");
						break;
					}
				}
			}

			if (checkedArray != null) {
				for (int j = 0; j < checkedArray.length; j++) {
					if (value.equals(checkedArray[j])) {
						sb.append(" checked");
						break;
					}
				}
			}

			sb.append(" >");
			sb.append("<label for='" + name + "_" + k + "'>");
			sb.append(map.get(value));
			sb.append("</label>&nbsp;");
			if (direction) {
				sb.append("<br>");
			}
		}
		return sb.toString();
	}

	public static String arrayToOptions(String[] array) {
		return arrayToOptions(array, null);
	}

	public static String arrayToOptions(String[] array, Object checkedValue) {
		return arrayToOptions(array, checkedValue, false);
	}

	public static String arrayToOptions(String[] array, boolean addBlankOptionFlag) {
		return arrayToOptions(array, null, addBlankOptionFlag);
	}

	public static String arrayToOptions(String[] array, Object checkedValue, boolean addBlankOptionFlag) {
		StringBuffer sb = new StringBuffer();
		if (addBlankOptionFlag) {
			sb.append("<span value=''></span>");
		}

		for (int i = 0; i < array.length; i++) {
			sb.append("<span value=\"");
			Object v = array[i];
			String value = (String) v;
			String[] arr = value.split(",");
			String name = value;
			if (arr.length > 1) {
				name = arr[0];
				value = arr[1];
			}
			sb.append(value);
			if (value != null && value.equals((String) checkedValue)) {
				sb.append("\" selected='true' >");
			} else {
				sb.append("\">");
			}
			sb.append(name);
			sb.append("</span>");
		}
		return sb.toString();
	}

	public static String queryToOptions(QueryBuilder qb) {
		return dataTableToOptions(qb.executeDataTable(), null);
	}

	public static String queryToOptions(QueryBuilder qb, Object checkedValue) {
		return dataTableToOptions(qb.executeDataTable(), checkedValue);
	}

	public static String queryToOptions(QueryBuilder qb, boolean addBlankOptionFlag) {
		return dataTableToOptions(qb.executeDataTable(), addBlankOptionFlag);
	}

	public static String queryToOptions(QueryBuilder qb, Object checkedValue, boolean addBlankOptionFlag) {
		return dataTableToOptions(qb.executeDataTable(), checkedValue, addBlankOptionFlag);
	}

	/**
	 * ��dt�е����ݱ��һ��������û��Ĭ��ѡ�е���
	 * 
	 * @param dt
	 * @return
	 */
	public static String dataTableToOptions(DataTable dt) {
		return dataTableToOptions(dt, null);
	}

	/**
	 * ��dt�е����ݱ��һ�������򣬲��ҳ�ʼ��ѡ�е���
	 * 
	 * @param dt
	 * @param checkedValue
	 * @return
	 */
	public static String dataTableToOptions(DataTable dt, Object checkedValue) {
		return dataTableToOptions(dt, checkedValue, false);
	}

	public static String dataTableToOptions(DataTable dt, boolean addBlankOptionFlag) {
		return dataTableToOptions(dt, null, addBlankOptionFlag);
	}

	public static String dataTableToOptions(DataTable dt, Object checkedValue, boolean addBlankOptionFlag) {
		StringBuffer sb = new StringBuffer();
		if (addBlankOptionFlag) {
			sb.append(SelectTag.getOptionHtml("", "", false));
		}
		if (dt == null) {
			return null;
		}
		for (int i = 0; i < dt.getRowCount(); i++) {
			String value = dt.getString(i, 1);
			if (value.equals(checkedValue)) {
				sb.append(SelectTag.getOptionHtml(dt.getString(i, 0), value, true));
			} else {
				sb.append(SelectTag.getOptionHtml(dt.getString(i, 0), value, false));
			}
		}
		return sb.toString();
	}

	/**
	 * ��ѡ��
	 * 
	 * @param name
	 * @param dt
	 * @return
	 */
	public static String dataTableToRadios(String name, DataTable dt) {
		return dataTableToRadios(name, dt, null, false);
	}

	/**
	 * @param name
	 * @param dt
	 * @param checkedValue ���Ʊ�ѡ�е���
	 * @return
	 */
	public static String dataTableToRadios(String name, DataTable dt, String checkedValue) {
		return dataTableToRadios(name, dt, checkedValue, false);
	}

	/**
	 * @param name
	 * @param dt
	 * @param direction ������չ����ʽ��false ˮƽ��true ��ֱ
	 * @return
	 */
	public static String dataTableToRadios(String name, DataTable dt, boolean direction) {
		return dataTableToRadios(name, dt, null, direction);
	}

	/**
	 * @param name
	 * @param dt
	 * @param checkedValue
	 * @return
	 */
	public static String dataTableToRadios(String name, DataTable dt, Object checkedValue, boolean direction) {
		StringBuffer sb = new StringBuffer();
		for (int k = 0; k < dt.getRowCount(); k++) {
			String value = dt.getString(k, 1);
			sb.append("<input type='radio' name='" + name);
			sb.append("' id='" + name + "_" + k + "' value='");
			sb.append(value);
			if (value.equals(checkedValue)) {
				sb.append("' checked >");
			} else {
				sb.append("' >");
			}
			sb.append("<label for='" + name + "_" + k + "'>");
			sb.append(dt.getString(k, 0));
			sb.append("</label>");
			if (direction) {
				sb.append("<br>");
			}
		}
		return sb.toString();
	}

	public static String arrayToRadios(String name, String[] array) {
		return arrayToRadios(name, array, null, false);
	}

	public static String arrayToRadios(String name, String[] array, String checkedValue) {
		return arrayToRadios(name, array, checkedValue, false);
	}

	public static String arrayToRadios(String name, String[] array, boolean direction) {
		return arrayToRadios(name, array, null, direction);
	}

	public static String arrayToRadios(String name, String[] array, Object checkedValue, boolean direction) {
		StringBuffer sb = new StringBuffer();
		for (int k = 0; k < array.length; k++) {
			String value = array[k];
			sb.append("<input type='radio' name='" + name);
			sb.append("' id='" + name + "_" + k + "' value='");
			sb.append(value);
			if (value.equals(checkedValue)) {
				sb.append("' checked >");
			} else {
				sb.append("' >");
			}
			sb.append("<label for='" + name + "_" + k + "'>");
			sb.append(value);
			sb.append("</label>");
			if (direction) {
				sb.append("<br>");
			}
		}
		return sb.toString();
	}

	/**
	 * �Ա��ķ�ʽչ�ֵ�ѡ��ť
	 */

	public static String mapxToRadiosTable(String name, Map map, Object checkedValue, int colNum) {
		if (colNum < 1) {
			colNum = 1;
		}
		StringBuffer sb = new StringBuffer();
		sb.append("<table width='100%'>");
		Object[] keys = map.keySet().toArray();
		for (int i = 0; i < keys.length; i++) {
			if (i % colNum == 0) {
				sb.append("<tr>");
			}
			Object value = keys[i];
			sb.append("<td width='" + (100 / colNum) + "%'><input type='radio' name='" + name);
			sb.append("' id='" + name + "_" + i + "' value='");
			sb.append(value);
			if (value.equals(checkedValue)) {
				sb.append("' checked >");
			} else {
				sb.append("' >");
			}
			sb.append("<label for='" + name + "_" + i + "'>");
			sb.append(map.get(value));
			sb.append("</label></td>");
			if (i % colNum == colNum - 1) {
				sb.append("</tr>");
			}
		}
		if (keys.length % colNum != 0) {
			for (int i = 0; i < colNum - keys.length % colNum; i++) {
				sb.append("<td>&nbsp;</td>");
			}
			sb.append("</tr>");
		}
		sb.append("</table>");
		return sb.toString();
	}

	public static String codeToRadiosTable(String name, String CodeType, Object checkedValue, int colNum) {
		return mapxToRadiosTable(name, CacheManager.getMapx("Code", CodeType), checkedValue, colNum);
	}

	public static String codeToRadiosTable(String name, String CodeType, int colNum) {
		return mapxToRadiosTable(name, CacheManager.getMapx("Code", CodeType), null, colNum);
	}

	public static String codeToRadiosTable(String name, String CodeType) {
		return mapxToRadiosTable(name, CacheManager.getMapx("Code", CodeType), null, 0);
	}

	/**
	 * ˮƽ��ѡ��
	 * 
	 * @param name
	 * @param dt
	 * @return
	 */
	public static String dataTableToCheckboxes(String name, DataTable dt) {
		return dataTableToCheckboxes(name, dt, false);
	}

	/**
	 * @param name
	 * @param dt
	 * @param direction ������չ����ʽ��false ˮƽ��true ��ֱ
	 * @return
	 */
	public static String dataTableToCheckboxes(String name, DataTable dt, boolean direction) {
		return dataTableToCheckboxes(name, dt, new String[0], direction);
	}

	/**
	 * @param name
	 * @param dt
	 * @param checkedDT ���Ʊ�ѡ�е���
	 * @return
	 */
	public static String dataTableToCheckboxes(String name, DataTable dt, DataTable checkedDT) {
		return dataTableToCheckboxes(name, dt, checkedDT, false);
	}

	/**
	 * @param name
	 * @param dt
	 * @param checkedDT ���Ʊ�ѡ�е���
	 * @param direction ������չ����ʽ��false ˮƽ��true ��ֱ
	 * @return
	 */
	public static String dataTableToCheckboxes(String name, DataTable dt, DataTable checkedDT, boolean direction) {
		String[] checkedArray = new String[checkedDT.getRowCount()];
		for (int i = 0; i < checkedDT.getRowCount(); i++) {
			checkedArray[i] = checkedDT.getString(i, 0);
		}
		return dataTableToCheckboxes(name, dt, checkedArray, direction);
	}

	/**
	 * @param name
	 * @param dt
	 * @param checkedArray ���Ʊ�ѡ�е���
	 * @return
	 */
	public static String dataTableToCheckboxes(String name, DataTable dt, String[] checkedArray) {
		return dataTableToCheckboxes(name, dt, checkedArray, false);
	}

	/**
	 * @param name
	 * @param dt
	 * @param checkedArray ���Ʊ�ѡ�е���
	 * @param direction ������չ����ʽ��false ˮƽ��true ��ֱ
	 * @return
	 */
	public static String dataTableToCheckboxes(String name, DataTable dt, String[] checkedArray, boolean direction) {
		StringBuffer sb = new StringBuffer();
		for (int k = 0; k < dt.getRowCount(); k++) {
			String value = dt.getString(k, 1);
			sb.append("<input type='checkbox' name='" + name);
			sb.append("' id='" + name + "_" + k + "' value='");
			sb.append(value);
			boolean flag = false;
			if (checkedArray != null) {
				for (int j = 0; j < checkedArray.length; j++) {
					if (value.equals(checkedArray[j])) {
						sb.append("' checked >");
						flag = true;
						break;
					}
				}
			}

			if (!flag) {
				sb.append("' >");
			}
			sb.append("<label for='" + name + "_" + k + "'>");
			sb.append(dt.getString(k, 0));
			sb.append("</label>&nbsp;");
			if (direction) {
				sb.append("<br>");
			}
		}
		return sb.toString();
	}

	public static String arrayToCheckboxes(String name, String[] array) {
		return arrayToCheckboxes(name, array, null, null, false);
	}

	public static String arrayToCheckboxes(String name, String[] array, String[] checkedArray) {
		return arrayToCheckboxes(name, array, checkedArray, null, false);
	}

	public static String arrayToCheckboxes(String name, String[] array, String onclick) {
		return arrayToCheckboxes(name, array, null, onclick, false);
	}

	public static String arrayToCheckboxes(String name, String[] array, String[] checkedArray, String onclick,
			boolean direction) {
		StringBuffer sb = new StringBuffer();
		for (int k = 0; k < array.length; k++) {
			String value = array[k];
			sb.append("<label><input type='checkbox'");
			if (StringUtil.isNotEmpty(onclick)) {
				sb.append("onclick='" + onclick + "'");
			}
			sb.append(" name='" + name);
			sb.append("' id='" + name + "_" + k + "'value='");
			sb.append(value);
			boolean flag = false;
			for (int j = 0; checkedArray != null && j < checkedArray.length; j++) {
				if (value.equals(checkedArray[j])) {
					sb.append("' checked >");
					flag = true;
					break;
				}
			}
			if (!flag) {
				sb.append("' >");
			}
			sb.append("<label for='" + name + "_" + k + "'>");
			sb.append(value);
			sb.append("</label>");
			if (direction) {
				sb.append("<br>");
			}
		}
		return sb.toString();
	}

	/**
	 * ��һ��html������${Field}��ռλ����map�ж�Ӧ�ļ�ֵ�滻;<br>
	 * blankFlagΪtrueʱ��δ��map���ж�Ӧ��ֵ��ռλ���滻Ϊ���ַ���,Ϊfalseʱ���滻<br>
	 */
	public static String replacePlaceHolder(String html, HashMap map, boolean blankFlag) {
		return replacePlaceHolder(html, map, blankFlag, false);
	}

	/**
	 * ��һ��html������${Field}��ռλ����map�ж�Ӧ�ļ�ֵ�滻;<br>
	 * blankFlagΪtrueʱ��δ��map���ж�Ӧ��ֵ��ռλ���滻Ϊ���ַ���,Ϊfalseʱ���滻<br>
	 * spaceFlagΪtrueʱ��map�м�ֵΪnull���߿��ַ������滻Ϊ&nbsp;
	 */
	public static String replacePlaceHolder(String html, HashMap map, boolean blankFlag, boolean spaceFlag) {
		Matcher matcher = Constant.PatternField.matcher(html);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		String blank = "";
		if (spaceFlag) {
			blank = "&nbsp;";
		}
		map = new CaseIgnoreMapx(map);
		while (matcher.find(lastEndIndex)) {
			sb.append(html.substring(lastEndIndex, matcher.start()));
			String key = matcher.group(1).toLowerCase();
			if (map.containsKey(key)) {
				Object o = map.get(key);
				if (o == null || o.equals("")) {
					sb.append(blank);
				} else {
					sb.append(o);
				}
			} else if (blankFlag) {
				sb.append("");
			} else {
				sb.append(html.substring(matcher.start(), matcher.end()));
			}
			lastEndIndex = matcher.end();
		}
		sb.append(html.substring(lastEndIndex));
		return sb.toString();
	}

	/**
	 * ��DataTable�е��н�һ��HTML����滻
	 */
	public static String replaceWithDataTable(DataTable dt, String html) {
		return replaceWithDataTable(dt, html, true);
	}

	/**
	 * ��DataTable�е��н�һ��HTML����滻,spaceFlagΪtrue��ʾ��DataTable��δ�ж�Ӧ��ʱ�滻�ɿո�
	 */
	public static String replaceWithDataTable(DataTable dt, String html, boolean spaceFlag) {
		if (html == null || dt == null) {
			return "";
		}
		Matcher matcher = Constant.PatternField.matcher(html);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		String blank = "";
		if (spaceFlag) {
			blank = "&nbsp;";
		}
		ArrayList arr = new ArrayList();
		ArrayList key = new ArrayList();

		while (matcher.find(lastEndIndex)) {
			arr.add(html.substring(lastEndIndex, matcher.start()));
			String str = matcher.group(1);
			key.add(str);
			lastEndIndex = matcher.end();
		}
		arr.add(html.substring(lastEndIndex));

		for (int i = 0; i < dt.getRowCount(); i++) {
			for (int j = 0; j < arr.size(); j++) {
				sb.append(arr.get(j));
				if (j != key.size()) {
					String str = dt.getString(i, key.get(j).toString());
					if (StringUtil.isEmpty(str)) {
						sb.append(blank);
					} else {
						sb.append(str);
					}
				}
			}
		}
		return sb.toString();
	}

	/**
	 * ��DataRow��һ��HTML�滻,ƥ������${FieldName}���ַ���
	 */
	public static String replaceWithDataRow(DataRow dr, String html) {
		return replaceWithDataRow(dr, html, true);
	}

	/**
	 * ��DataRow��һ��HTML�滻,spaceFlagΪtrue��ʾ��DataTable��δ�ж�Ӧ��ʱ�滻�ɿո�,ƥ������${FieldName} ���ַ���
	 */
	public static String replaceWithDataRow(DataRow dr, String html, boolean spaceFlag) {
		if (html == null || dr == null) {
			return "";
		}
		Matcher matcher = Constant.PatternField.matcher(html);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		String blank = "";
		if (spaceFlag) {
			blank = "&nbsp;";
		}
		ArrayList arr = new ArrayList();
		ArrayList key = new ArrayList();

		while (matcher.find(lastEndIndex)) {
			arr.add(html.substring(lastEndIndex, matcher.start()));
			String str = matcher.group(1);
			key.add(str);
			lastEndIndex = matcher.end();
		}
		arr.add(html.substring(lastEndIndex));

		for (int j = 0; j < arr.size(); j++) {
			sb.append(arr.get(j));
			if (j != key.size()) {
				String str = dr.getString(key.get(j).toString());
				if (StringUtil.isEmpty(str)) {
					sb.append(blank);
				} else {
					sb.append(str);
				}
			}
		}
		return sb.toString();
	}

	/**
	 * ��һ��HTML���ת����Excel�ļ���������������ָ������<br>
	 * widths��ʾ���еĿ�indexes��ʾҪ������У�rows��ʾҪ������С� *
	 */
	public static void htmlTableToExcel(OutputStream os, HtmlTable table, String[] widths, String[] indexes,
			String[] rows) {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("First");
		try {
			HSSFFont fontBold = wb.createFont();
			fontBold.setFontHeightInPoints((short) 10);
			fontBold.setFontName("����");
			fontBold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

			HSSFFont fontNormal = wb.createFont();
			fontNormal.setFontHeightInPoints((short) 10);
			fontNormal.setFontName("����");
			fontNormal.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);

			HSSFCellStyle styleBorderBold = wb.createCellStyle();
			styleBorderBold.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			styleBorderBold.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			styleBorderBold.setBorderRight(HSSFCellStyle.BORDER_THIN);
			styleBorderBold.setBorderTop(HSSFCellStyle.BORDER_THIN);
			styleBorderBold.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			styleBorderBold.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			styleBorderBold.setWrapText(true);
			// styleBorderBold.setFillBackgroundColor((short)0);
			styleBorderBold.setFont(fontBold);

			HSSFCellStyle styleBorderNormal = wb.createCellStyle();
			styleBorderNormal.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			styleBorderNormal.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			styleBorderNormal.setBorderRight(HSSFCellStyle.BORDER_THIN);
			styleBorderNormal.setBorderTop(HSSFCellStyle.BORDER_THIN);
			styleBorderBold.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			styleBorderNormal.setFont(fontNormal);

			HSSFCellStyle styleBold = wb.createCellStyle();
			styleBold.setFont(fontBold);

			HSSFCellStyle styleNormal = wb.createCellStyle();
			styleNormal.setFont(fontNormal);

			// ת��ͷ��
			HSSFRow row = sheet.getRow(0);
			if (row == null) {
				row = sheet.createRow(0);
			}
			for (int i = 0; i < indexes.length; i++) {
				HSSFCell cell = row.getCell((short) i);
				if (cell == null) {
					cell = row.createCell((short) i);
				}
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellStyle(styleBorderBold);
				cell.setEncoding(HSSFCell.ENCODING_UTF_16);

				String html = table.getTR(0).getTD(Integer.parseInt(indexes[i])).getInnerHTML();
				html = html.replaceAll("<.*?>", "");
				html = StringUtil.htmlDecode(html);
				cell.setCellValue(html.trim());
				row.setHeightInPoints(23);
				if (widths != null && widths.length > i) {
					double w = Double.parseDouble(widths[i]);
					if (w < 100) {
						w = 100;
					}
					sheet.setColumnWidth((short) i, (short) (w * 35.7));
				}
			}

			for (int i = 0; i < indexes.length; i++) {
				int j = Integer.parseInt(indexes[i]);
				if (rows != null) {
					for (int k = 0; k < rows.length; k++) {
						int n = Integer.parseInt(rows[k]);
						String ztype = table.getTR(n).getAttribute("ztype");
						if (k == table.getChildren().size() - 1) {
							if ((StringUtil.isNotEmpty(ztype) && ztype.equalsIgnoreCase("pagebar"))) {
								break;
							}
							String html = table.getTR(n).getInnerHTML();
							if (StringUtil.isEmpty(html) || html.indexOf("PageBarIndex") > 0) {
								break;
							}
						}

						row = sheet.getRow(k + 1);
						if (row == null) {
							row = sheet.createRow(k + 1);
							row.setHeightInPoints(18);
						}
						HSSFCell cell = row.getCell((short) i);
						if (cell == null) {
							cell = row.createCell((short) i);
						}
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						cell.setCellStyle(styleBorderNormal);
						cell.setEncoding(HSSFCell.ENCODING_UTF_16);

						String html = table.getTR(n).getTD(j).getOuterHtml();
						html = html.replaceAll("<.*?>", "");
						html = StringUtil.htmlDecode(html);
						cell.setCellValue(html.trim());
					}
				} else {
					for (int k = 1; k < table.getChildren().size(); k++) {
						String ztype = table.getTR(k).getAttribute("ztype");
						if (k == table.getChildren().size() - 1) {
							if ((StringUtil.isNotEmpty(ztype) && ztype.equalsIgnoreCase("pagebar"))) {
								break;
							}
							String html = table.getTR(k).getInnerHTML();
							if (StringUtil.isEmpty(html) || html.indexOf("PageBarIndex") > 0) {
								break;
							}
						}

						row = sheet.getRow(k);
						if (row == null) {
							row = sheet.createRow(k);
							row.setHeightInPoints(18);
						}
						HSSFCell cell = row.getCell((short) i);
						if (cell == null) {
							cell = row.createCell((short) i);
						}
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						cell.setCellStyle(styleBorderNormal);
						cell.setEncoding(HSSFCell.ENCODING_UTF_16);

						String html = "";
						if (table.getTR(k).getChildren().size() > j) {// �п��ܵ�Ԫ����JAVA�б��ϲ���
							html = table.getTR(k).getTD(j).getOuterHtml();
							html = html.replaceAll("<.*?>", "");
							html = StringUtil.htmlDecode(html);
						}
						cell.setCellValue(html.trim());
					}
				}
			}
			wb.write(os);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Mapx map = new Mapx();
		for (int i = 0; i < 21; i++) {
			map.put(i + "", "name_" + i);
		}
		System.out.println(HtmlUtil.codeToRadiosTable("BenefiyType", "Project.BenefiyType", 2));
	}
}
