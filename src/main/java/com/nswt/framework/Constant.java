package com.nswt.framework;

import java.util.regex.Pattern;

/**
 * @Author shuguang
 * @Date 2007-7-16
 * @Mail sheshuguang@gmail.com
 */
public class Constant {
	/**
	 * ������ʽ��ƥ������${FieldName}���ַ���
	 */
	public static final Pattern PatternField = Pattern.compile("\\$\\{(\\w+?)\\}");

	/**
	 * ������ʽ��ƥ������${@FieldName}���ַ���
	 */
	public static final Pattern PatternSpeicalField = Pattern.compile("\\$\\{[@#](\\w+?)\\}");

	/**
	 * ������ʽ��ƥ������${Table.FieldName}���ַ���
	 */
	public static final Pattern PatternPropField = Pattern.compile("\\$\\{(\\w+?)\\.(\\w+?)(\\|(.*?))??\\}");

	/**
	 * Session�е�User�����������
	 */
	public static final String UserAttrName = "_NSWT_USER";

	/**
	 * User��������֤���ַ�����������
	 */
	public static final String DefaultAuthKey = "_NSWT_AUTHKEY";

	/**
	 * Cookie��SessionID��Ӧ��Cookie�����ƣ���ͬ���м������������ͬ
	 */
	public static final String SessionIDCookieName = "JSESSIONID";

	/**
	 * Response��������Ҫ������ִ�е�JS��
	 */
	public static final String ResponseScriptAttr = "_NSWT_SCRIPT";

	/**
	 * Response��������Ҫ�������ʾ����Ϣ�ı�
	 */
	public static final String ResponseMessageAttrName = "_NSWT_MESSAGE";//

	/**
	 * Response�����з�������������״ֵ̬��һ��0��ʾ�д���1��ʾִ�гɹ�
	 */
	public static final String ResponseStatusAttrName = "_NSWT_STATUS";

	/**
	 * DataGrid�б�ʾSQL��������
	 */
	public static final String DataGridSQL = "_NSWT_DATAGRID_SQL";

	/**
	 * DataGrid�еı�ʾ��ǰҳ��������������0��ʼ��0��ʾ��һҳ
	 */
	public static final String DataGridPageIndex = "_NSWT_PAGEINDEX";

	/**
	 * DataGrid�б�ʾ��¼������������
	 */
	public static final String DataGridPageTotal = "_NSWT_PAGETOTAL";

	/**
	 * DataGrid�б�ʾ����ʽ������������ֵ����id desc,name asc
	 */
	public static final String DataGridSortString = "_NSWT_SORTSTRING";

	/**
	 * DataGrid�б�ʾ��ǰ�����ǲ���հ��е�������
	 */
	public static final String DataGridInsertRow = "_NSWT_INSERTROW";

	/**
	 * DataGrid�б�ʾ�����ѡ��������
	 */
	public static final String DataGridMultiSelect = "_NSWT_MULTISELECT";

	/**
	 * DataGrid�б�ʾҪ���Զ����հ����Ա���DataGrid�߶ȵ�������
	 */
	public static final String DataGridAutoFill = "_NSWT_AUTOFILL";

	/**
	 * DataGrid�б�ʾ�������ݹ���������
	 */
	public static final String DataGridScroll = "_NSWT_SCROLL";

	/**
	 * ��ʾ����ֵ��һ��DataTable
	 */
	public static final String DataTable = "_NSWT_DataTable";

	/**
	 * ��ʾ����ֵ��ΨһID
	 */
	public static final String ID = "_NSWT_ID";

	/**
	 * ��ʾ����ֵ��һ����̨Page��ķ���
	 */
	public static final String Method = "_NSWT_METHOD";

	/**
	 * ��ʾ�Ƿ������ҳ��ֵΪ�� ����true��false
	 */
	public static final String Page = "_NSWT_PAGE";

	/**
	 * ��ʾ��С���������������ҳ��С
	 */
	public static final String Size = "_NSWT_SIZE";

	/**
	 * ��ʾ�ؼ���ǩ������HTML���ݵ�������
	 */
	public static final String TagBody = "_NSWT_TAGBODY";

	/**
	 * ��ʾ���νṹ�в㼶��������
	 */
	public static final String TreeLevel = "_NSWT_TREE_LEVEL";

	/**
	 * ���οؼ��б�ʾ�Ƿ��ӳټ��ص�������
	 */
	public static final String TreeLazy = "_NSWT_TREE_LAZY"; // �ӳټ���

	/**
	 * ���οؼ��б�ʾ�Ƿ�ȫ��չ����������
	 */
	public static final String TreeExpand = "_NSWT_TREE_EXPAND"; // �Ƿ����ӳټ�����ȫ��չ��

	/**
	 * ���οؼ��б�ʾcss��������
	 */
	public static final String TreeStyle = "_NSWT_TREE_STYLE";

	/**
	 * ��ʾ����ֵ��һ��DataCollection����
	 */
	public static final String Data = "_NSWT_DATA";

	/**
	 * ��ʾ����ֵ��һ��URL
	 */
	public static final String URL = "_NSWT_URL";

	/**
	 * ��ʾһ�����ַ�������ĳЩ���������ֱ�Ӵ����ַ������ᱻ���˵�����URL�У�
	 */
	public static final String Null = "_NSWT_NULL";

	/**
	 * ���·�ҳ�ָ���������һ�������ɶ��������ɵĳ��ϣ���CMS�еĶ�ҳ����
	 */
	public static final String PAGE_BREAK = "<!--_NSWT_PAGE_BREAK_-->";

	/**
	 * ȫ���ַ������ã���UTF-8���Ӧ����ֵΪ��UTF-8������GBK���Ӧ����ֵΪ��GBK��
	 */
	public static String GlobalCharset = "GBK";

	/**
	 * ��GBK�汾�е����⴦����UTF-B
	 * */
	public static String UTF8Charset = "UTF8";
}
