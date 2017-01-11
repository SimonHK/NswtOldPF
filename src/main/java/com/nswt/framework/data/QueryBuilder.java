package com.nswt.framework.data;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * SQL��ѯ�����������ڹ��������SQL��ִ�У��Ա���SQLע�롣֧������ģʽ
 * 
 * @Author NSWT
 * @Date 2008-7-29
 * @Mail nswt@nswt.com.cn
 */
public class QueryBuilder {
	private ArrayList list = new ArrayList();

	private ArrayList batches;

	/**
	 * ��������ʱ�������������������Ĳ����б�
	 */
	protected ArrayList getBatches() {
		return batches;
	}

	private StringBuffer sql = new StringBuffer();

	/**
	 * ����һ���յĲ�ѯ���ȴ�ʹ��setSQL()��������SQL���
	 */
	public QueryBuilder() {
	}

	/**
	 * ���ݴ����SQL�ַ�������һ��SQL��ѯ
	 */
	public QueryBuilder(String sql) {
		setSQL(sql);
	}

	/**
	 * ���ݴ���Ĳ�����SQL�ַ����Ͳ���������һ��SQL��ѯ
	 */
	public QueryBuilder(String sql, int param) {
		setSQL(sql);
		add(param);
	}

	/**
	 * ���ݴ���Ĳ�����SQL�ַ����Ͳ���������һ��SQL��ѯ
	 */
	public QueryBuilder(String sql, long param) {
		setSQL(sql);
		add(param);
	}

	/**
	 * ���ݴ���Ĳ�����SQL�ַ����Ͳ���������һ��SQL��ѯ
	 */
	public QueryBuilder(String sql, Object param) {
		setSQL(sql);
		add(param);
	}

	/**
	 * ���ݴ���Ĳ�����SQL�ַ����Ͳ���������һ��SQL��ѯ
	 */
	public QueryBuilder(String sql, int param1, Object param2) {
		setSQL(sql);
		add(param1);
		add(param2);
	}

	/**
	 * ���ݴ���Ĳ�����SQL�ַ����Ͳ���������һ��SQL��ѯ
	 */
	public QueryBuilder(String sql, long param1, Object param2) {
		setSQL(sql);
		add(param1);
		add(param2);
	}

	/**
	 * ���ݴ���Ĳ�����SQL�ַ����Ͳ���������һ��SQL��ѯ
	 */
	public QueryBuilder(String sql, Object param1, int param2) {
		setSQL(sql);
		add(param1);
		add(param2);
	}

	/**
	 * ���ݴ���Ĳ�����SQL�ַ����Ͳ���������һ��SQL��ѯ
	 */
	public QueryBuilder(String sql, Object param1, long param2) {
		setSQL(sql);
		add(param1);
		add(param2);
	}

	/**
	 * ���ݴ���Ĳ�����SQL�ַ����Ͳ���������һ��SQL��ѯ
	 */
	public QueryBuilder(String sql, int param1, int param2) {
		setSQL(sql);
		add(param1);
		add(param2);
	}

	/**
	 * ���ݴ���Ĳ�����SQL�ַ����Ͳ���������һ��SQL��ѯ
	 */
	public QueryBuilder(String sql, long param1, long param2) {
		setSQL(sql);
		add(param1);
		add(param2);
	}

	/**
	 * ���ݴ���Ĳ�����SQL�ַ����Ͳ���������һ��SQL��ѯ
	 */
	public QueryBuilder(String sql, Object param1, Object param2) {
		setSQL(sql);
		add(param1);
		add(param2);
	}

	private boolean batchMode;

	/**
	 * ��ǰSQL�����Ƿ�������ģʽ
	 */
	public boolean isBatchMode() {
		return batchMode;
	}

	/**
	 * ��������ģʽ
	 */
	public void setBatchMode(boolean batchMode) {
		if (batchMode && batches == null) {
			batches = new ArrayList();
		}
		this.batchMode = batchMode;
	}

	/**
	 * ����һ������
	 */
	public void addBatch() {
		if (!batchMode) {
			throw new RuntimeException("��������ģʽ�²���ʹ��addBatch()");
		}
		batches.add(list);
		list = new ArrayList();
	}

	/**
	 * ����һ��SQL����
	 */
	public void add(int param) {
		list.add(new Integer(param));
	}

	/**
	 * ����һ��SQL����
	 */
	public void add(long param) {
		list.add(new Long(param));
	}

	/**
	 * ����һ��SQL����
	 */
	public void add(Object param) {
		list.add(param);
	}

	/**
	 * ����ָ��λ�õ�SQL����
	 */
	public void set(int index, int param) {
		list.set(index, new Integer(param));
	}

	/**
	 * ����ָ��λ�õ�SQL����
	 */
	public void set(int index, long param) {
		list.set(index, new Long(param));
	}

	/**
	 * ����ָ��λ�õ�SQL����
	 */
	public void set(int index, Object param) {
		list.set(index, param);
	}

	/**
	 * ����SQL���
	 */
	public void setSQL(String sql) {
		this.sql = new StringBuffer(sql);
	}

	/**
	 * ���Ϊʹ��append(String sqlPart)����
	 * 
	 * @deprecated
	 */
	public void appendSQLPart(String sqlPart) {
		sql.append(sqlPart);
	}

	/**
	 * ׷�Ӳ���SQL��䣬��Ҫ����׷��where����
	 */
	public QueryBuilder append(String sqlPart) {
		sql.append(sqlPart);
		return this;
	}

	/**
	 * ׷�Ӳ���SQL��䣬ͬʱ׷��SQL����
	 */
	public QueryBuilder append(String sqlPart, int param) {
		sql.append(sqlPart);
		add(param);
		return this;
	}

	/**
	 * ׷�Ӳ���SQL��䣬ͬʱ׷��SQL����
	 */
	public QueryBuilder append(String sqlPart, long param) {
		sql.append(sqlPart);
		add(param);
		return this;
	}

	/**
	 * ׷�Ӳ���SQL��䣬ͬʱ׷��SQL����
	 */
	public QueryBuilder append(String sqlPart, Object param) {
		sql.append(sqlPart);
		add(param);
		return this;
	}

	/**
	 * ִ�в�ѯ������DataTable
	 */
	public DataTable executeDataTable() {
		return executeDataTable(null);
	}

	/**
	 * ��ִ�����ӳ���ִ�в�ѯ������DataTable
	 */
	public DataTable executeDataTable(String poolName) {
		DataAccess da = new DataAccess(DBConnPool.getConnection(poolName));
		DataTable dt = null;
		try {
			dt = da.executeDataTable(this);
		} catch (Throwable e) {
			throw new RuntimeException(e);
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
	 * �Է�ҳ��ʽִ�в�ѯ�������ش���ָ��ҳ��DataTable
	 */
	public DataTable executePagedDataTable(int pageSize, int pageIndex) {
		return executePagedDataTable(null, pageSize, pageIndex);
	}

	public DataTable executePagedDataTable(String poolName, int pageSize, int pageIndex) {
		DataAccess da = new DataAccess(DBConnPool.getConnection(poolName));
		DataTable dt = null;
		try {
			dt = da.executePagedDataTable(this, pageSize, pageIndex);
		} catch (Throwable e) {
			throw new RuntimeException(e);
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
	 * ִ�в�ѯ�������ص�һ����¼�ĵ�һ���ֶε�ֵ�����û�м�¼���򷵻�null
	 */
	public Object executeOneValue() {
		return executeOneValue(null);
	}

	public Object executeOneValue(String poolName) {
		DataAccess da = new DataAccess(DBConnPool.getConnection(poolName));
		Object t = null;
		try {
			t = da.executeOneValue(this);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		} finally {
			try {
				da.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return t;
	}

	/**
	 * ִ�в�ѯ�������ص�һ����¼�ĵ�һ���ֶε�ֵ��ת��ΪString�����û�м�¼���򷵻�null
	 */
	public String executeString() {
		return executeString(null);
	}

	public String executeString(String poolName) {
		Object o = executeOneValue(poolName);
		if (o == null) {
			return null;
		} else {
			return o.toString();
		}
	}

	/**
	 * ִ�в�ѯ�������ص�һ����¼�ĵ�һ���ֶε�ֵ��ת��Ϊint�����û�м�¼���򷵻�null
	 */
	public int executeInt() {
		return executeInt(null);
	}

	public int executeInt(String poolName) {
		Object o = executeOneValue(poolName);
		if (o == null) {
			return 0;
		} else {
			return Integer.parseInt(o.toString());
		}
	}

	/**
	 * ִ�в�ѯ�������ص�һ����¼�ĵ�һ���ֶε�ֵ��ת��Ϊlong�����û�м�¼���򷵻�null
	 */
	public long executeLong() {
		return executeLong(null);
	}

	public long executeLong(String poolName) {
		Object o = executeOneValue(poolName);
		if (o == null) {
			return 0;
		} else {
			return Long.parseLong(o.toString());
		}
	}

	/**
	 * ִ�в�ѯ�������ز�ѯӰ��ļ�¼�����������SQL�쳣�����׳��쳣
	 */
	public int executeNoQueryWithException() throws SQLException {
		return executeNoQueryWithException(null);
	}

	public int executeNoQueryWithException(String poolName) throws SQLException {
		DataAccess da = new DataAccess(DBConnPool.getConnection(poolName));
		int t = -1;
		try {
			t = da.executeNoQuery(this);
		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				da.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return t;
	}

	/**
	 * ִ�в�ѯ�������ز�ѯӰ��ļ�¼��
	 */
	public int executeNoQuery() {
		return executeNoQuery(null);
	}

	public int executeNoQuery(String poolName) {
		try {
			return executeNoQueryWithException(poolName);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * ��ñ���ѯʹ�õĲ�����SQL
	 */
	public String getSQL() {
		return sql.toString();
	}

	/**
	 * ���ص�ǰ����SQL����
	 */
	public ArrayList getParams() {
		return list;
	}

	/**
	 * һ������������SQL����
	 */
	public void setParams(ArrayList list) {
		this.list = list;
	}

	/**
	 * ����ģʽ�������������
	 */
	public void clearBatches() {
		if (batchMode) {
			if (batches != null) {
				batches.clear();
			}
			batches = new ArrayList();
		}
	}

	/**
	 * ��������SQL�е��ʺŸ�����SQL���������Ƿ����
	 */
	public boolean checkParams() {
		char[] arr = sql.toString().toCharArray();
		boolean StringCharFlag = false;
		int count = 0;
		for (int i = 0; i < arr.length; i++) {
			char c = arr[i];
			if (c == '\'') {
				if (!StringCharFlag) {
					StringCharFlag = true;
				} else {
					StringCharFlag = false;
				}
			} else if (c == '?') {
				if (!StringCharFlag) {
					count++;
				}
			}
		}
		if (count != list.size()) {
			throw new RuntimeException("SQL�к���" + count + "������������" + list.size() + "��������ֵ!");
		}
		return true;
	}

	/*
	 * ת�ɿɶ���SQL���
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(sql);
		sb.append("\t{");
		for (int i = 0; i < list.size(); i++) {
			if (i != 0) {
				sb.append(",");
			}
			Object o = list.get(i);
			if (o == null) {
				sb.append("null");
				continue;
			}
			String str = list.get(i).toString();
			if (str.length() > 40) {
				str = str.substring(0, 37);
				sb.append(str);
				sb.append("...");
			} else {
				sb.append(str);
			}
		}
		sb.append("}");
		return sb.toString();
	}
}
