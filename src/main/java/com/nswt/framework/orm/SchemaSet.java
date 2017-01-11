package com.nswt.framework.orm;

import java.io.Serializable;
import java.sql.BatchUpdateException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

import com.nswt.framework.User;
import com.nswt.framework.data.DBConn;
import com.nswt.framework.data.DataAccess;
import com.nswt.framework.data.DataColumn;
import com.nswt.framework.data.DataRow;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.utility.Filter;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.StringUtil;

/**
 * SchemaSet�࣬��Ӧ�����ݿ���һ���¼<br>
 * 
 * ����: NSWT<br>
 * ���ڣ�2016-7-7<br>
 * �ʼ���nswt@nswt.com.cn<br>
 */
public abstract class SchemaSet implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	private Schema[] elementData;

	private int elementCount;

	private int capacityIncrement;

	protected String TableCode;

	protected String NameSpace;

	protected SchemaColumn[] Columns;

	protected String InsertAllSQL;

	protected String UpdateAllSQL;

	protected String FillAllSQL;

	protected String DeleteSQL;

	protected int bConnFlag = 0;// 1Ϊ���Ӵ��ⲿ���룬0Ϊδ��������

	protected boolean bOperateFlag = false;

	protected int[] operateColumnOrders;

	protected transient DataAccess mDataAccess;

	/**
	 * ��ʼ��һ��ָ�������������ʵ�SchemaSet
	 */
	protected SchemaSet(int initialCapacity, int capacityIncrement) {
		if (initialCapacity < 0) {
			throw new RuntimeException("SchemaSet�ĳ�ʼ��������С��0");
		}
		this.elementData = new Schema[initialCapacity];
		this.capacityIncrement = capacityIncrement;
		this.elementCount = 0;
	}

	/**
	 *��ʼ��һ��ָ��������SchemaSet
	 */
	protected SchemaSet(int initialCapacity) {
		this(initialCapacity, 0);
	}

	/**
	 * ��ʼ��һ��SchemaSet����ʼ����Ϊ10
	 */
	protected SchemaSet() {
		this(10);
	}

	/**
	 * ΪSchema����DataAccess������DataAccess֮��insert(),update()�Ȳ�������ʹ�ô�DataAccess����
	 */
	public void setDataAccess(DataAccess dAccess) {
		mDataAccess = dAccess;
		bConnFlag = 1;
	}

	/**
	 * ��Set������һ��Schema
	 */
	public boolean add(Schema s) {
		if (s == null || s.TableCode != this.TableCode) {
			LogUtil.warn("����Ĳ�������һ��" + this.TableCode + "Schema");
			return false;
		}
		ensureCapacityHelper(elementCount + 1);
		elementData[elementCount] = s;
		elementCount++;
		return true;
	}

	/**
	 * ��һ��SchemaSetȫ����뵽��һ��SchemaSet��ȥ
	 */
	public boolean add(SchemaSet aSet) {
		if (aSet == null) {
			return false;
		}
		int n = aSet.size();
		ensureCapacityHelper(elementCount + n);
		for (int i = 0; i < n; i++) {
			elementData[elementCount + i] = aSet.getObject(i);
		}
		elementCount += n;
		return true;
	}

	/**
	 * ��Set��ȥ��һ��Schema
	 */
	public boolean remove(Schema aSchema) {
		if (aSchema == null) {
			return false;
		}
		for (int i = 0; i < elementCount; i++) {
			if (aSchema.equals(elementData[i])) {
				int j = elementCount - i - 1;
				if (j > 0) {
					System.arraycopy(elementData, i + 1, elementData, i, j);
				}
				elementCount--;
				elementData[elementCount] = null;
				return true;
			}
		}
		return false;
	}

	/**
	 * ָ������Χȥ��һ��Schema
	 */
	public boolean removeRange(int index, int length) {
		if (index < 0 || length < 0 || index + length > elementCount) {
			return false;
		}
		if (elementCount > index + length) {
			System.arraycopy(elementData, index + length, elementData, index, length);
		}
		for (int i = 0; i < length; i++) {
			elementData[elementCount - i - 1] = null;
		}
		elementCount -= length;
		return true;
	}

	/**
	 * �����Set�е�����Schema
	 */
	public void clear() {
		for (int i = 0; i < elementCount; i++) {
			elementData[i] = null;
		}
		elementCount = 0;
	}

	/**
	 * �жϵ�ǰSet���Ƿ���Schema
	 */
	public boolean isEmpty() {
		return elementCount == 0;
	}

	/**
	 * ���ص�index��Schmea
	 */
	public Schema getObject(int index) {
		if (index > elementCount) {
			throw new RuntimeException("SchemaSet��������," + index);
		}
		return elementData[index];
	}

	/**
	 * ���õ�index��Schema
	 */
	public boolean set(int index, Schema aSchema) {
		if (index > elementCount) {
			throw new RuntimeException("SchemaSet��������," + index);
		}
		elementData[index] = aSchema;
		return true;
	}

	/**
	 * ����һ��Set����һ���ڲ�����
	 * 
	 * @deprecated
	 */
	public boolean set(SchemaSet aSet) {
		this.elementData = aSet.elementData;
		this.elementCount = aSet.elementCount;
		this.capacityIncrement = aSet.capacityIncrement;
		return true;
	}

	/**
	 * ����SchemaSet��Schema�ĸ���
	 */
	public int size() {
		return elementCount;
	}

	/**
	 * ������Ҫ����SchemaSet������
	 */
	private void ensureCapacityHelper(int minCapacity) {
		int oldCapacity = elementData.length;
		if (minCapacity > oldCapacity) {
			Object oldData[] = elementData;
			int newCapacity = (capacityIncrement > 0) ? (oldCapacity + capacityIncrement) : (oldCapacity * 2);
			if (newCapacity < minCapacity) {
				newCapacity = minCapacity;
			}
			elementData = new Schema[newCapacity];
			System.arraycopy(oldData, 0, elementData, 0, elementCount);
		}
	}

	/**
	 * ��SchemaSet�е��������ݲ��뵽���ݿ���
	 */
	public boolean insert() {
		if (bConnFlag == 0) {
			mDataAccess = new DataAccess();
		}
		PreparedStatement pstmt = null;
		try {
			DBConn conn = mDataAccess.getConnection();
			boolean autoComit = conn.getAutoCommit();
			if (bConnFlag == 0) {
				conn.setAutoCommit(false);
			}
			pstmt = conn.prepareStatement(InsertAllSQL, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			for (int k = 0; k < elementCount; k++) {
				for (int i = 0; i < Columns.length; i++) {
					Schema schema = elementData[k];
					SchemaColumn sc = Columns[i];
					if (sc.isMandatory()) {
						if (schema.getV(i) == null) {
							LogUtil.warn("��" + this.TableCode + "����" + sc.getColumnName() + "����Ϊ��");
							return false;
						}
					}
					Object v = schema.getV(i);
					SchemaUtil.setParam(sc, pstmt, conn, i, v);
				}
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			if (bConnFlag == 0) {
				conn.commit();
				conn.setAutoCommit(autoComit);
			}
			conn.setLastSuccessExecuteTime(System.currentTimeMillis());
			return true;
		} catch (Throwable e) {
			// ��������ʱ�����쳣���ѵ��ԣ������ֶγ�����������ͻ�ȣ���Ҫ�������SchemaSet����־��
			if (e instanceof BatchUpdateException) {
				LogUtil.warn(this.toDataTable());
			}
			LogUtil.warn("������" + this.TableCode + "ʱ��������:" + e.getMessage());
			throw new RuntimeException(e);
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				pstmt = null;
			}
			if (bConnFlag == 0) {
				try {
					mDataAccess.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * ��SchemaSet�е��������ݸ��µ����ݿ�
	 */
	public boolean update() {
		String sql = UpdateAllSQL;
		if (bConnFlag == 0) {
			mDataAccess = new DataAccess();
		}
		if (bOperateFlag) {
			StringBuffer sb = new StringBuffer("update " + TableCode + " set ");
			for (int i = 0; i < operateColumnOrders.length; i++) {
				if (i != 0) {
					sb.append(",");
				}
				sb.append(Columns[operateColumnOrders[i]].getColumnName());
				sb.append("=?");
			}
			sb.append(sql.substring(sql.indexOf(" where")));
			sql = sb.toString();
		}
		if (mDataAccess.getConnection().getDBConfig().isSybase()) {// Sybase�µ���ʱ��ʩ������Ҫ�ĵ�
			sql = StringUtil.replaceAllIgnoreCase(sql, " Count=", "\"Count\"=");
			sql = StringUtil.replaceAllIgnoreCase(sql, " Scroll=", "\"Scroll\"=");
			sql = StringUtil.replaceAllIgnoreCase(sql, ",Count=", ",\"Count\"=");
			sql = StringUtil.replaceAllIgnoreCase(sql, ",Scroll=", ",\"Scroll\"=");
		}
		PreparedStatement pstmt = null;
		try {
			DBConn conn = mDataAccess.getConnection();
			boolean autoComit = conn.getAutoCommit();
			if (bConnFlag == 0) {
				conn.setAutoCommit(false);
			}
			pstmt = conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			for (int k = 0; k < elementCount; k++) {
				Schema schema = elementData[k];
				if (bOperateFlag) {
					for (int i = 0; i < operateColumnOrders.length; i++) {
						Object v = schema.getV(operateColumnOrders[i]);
						SchemaUtil.setParam(Columns[operateColumnOrders[i]], pstmt, conn, operateColumnOrders[i], v);
					}
				} else {
					for (int i = 0; i < Columns.length; i++) {
						Object v = schema.getV(i);
						SchemaUtil.setParam(Columns[i], pstmt, conn, i, v);
					}
				}
				int pkIndex = 0;
				for (int i = 0, j = 0; i < Columns.length; i++) {
					SchemaColumn sc = Columns[i];
					if (sc.isPrimaryKey()) {
						Object v = schema.getV(sc.getColumnOrder());
						if (schema.OldKeys != null) {
							v = schema.OldKeys[pkIndex++];
						}
						if (v == null) {
							LogUtil.warn("������Update��������" + TableCode + "Schema��" + sc.getColumnName() + "Ϊ��");
							return false;
						} else {
							if (bOperateFlag) {
								SchemaUtil.setParam(Columns[i], pstmt, conn, j + operateColumnOrders.length, v);
							} else {
								SchemaUtil.setParam(Columns[i], pstmt, conn, j + Columns.length, v);
							}
						}
						j++;
					}
				}
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			if (bConnFlag == 0) {
				conn.commit();
				conn.setAutoCommit(autoComit);
			}
			conn.setLastSuccessExecuteTime(System.currentTimeMillis());
			return true;
		} catch (Throwable e) {
			LogUtil.warn("������" + this.TableCode + "ʱ��������:" + e.getMessage());
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				pstmt = null;
			}
			if (bConnFlag == 0) {
				try {
					mDataAccess.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	/**
	 * ������ɾ��SchemaSet�е����м�¼
	 */
	public boolean delete() {
		if (bConnFlag == 0) {
			mDataAccess = new DataAccess();
		}
		PreparedStatement pstmt = null;
		try {
			DBConn conn = mDataAccess.getConnection();
			boolean autoComit = conn.getAutoCommit();
			if (bConnFlag == 0) {
				conn.setAutoCommit(false);
			}
			pstmt = conn.prepareStatement(DeleteSQL, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			for (int k = 0; k < elementCount; k++) {
				Schema schema = elementData[k];
				int pkIndex = 0;
				for (int i = 0, j = 0; i < Columns.length; i++) {
					SchemaColumn sc = Columns[i];
					if (sc.isPrimaryKey()) {
						Object v = schema.getV(sc.getColumnOrder());
						if (schema.OldKeys != null) {
							v = schema.OldKeys[pkIndex++];
						}
						if (v == null) {
							LogUtil.warn("������delete��������" + TableCode + "Schema��" + sc.getColumnName() + "Ϊ��");
							return false;
						} else {
							SchemaUtil.setParam(Columns[i], pstmt, conn, j, v);
						}
						j++;
					}
				}
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			conn.setLastSuccessExecuteTime(System.currentTimeMillis());
			if (bConnFlag == 0) {
				conn.commit();
				conn.setAutoCommit(autoComit);
			}
			return true;
		} catch (Throwable e) {
			LogUtil.warn("������" + this.TableCode + "ʱ��������:" + e.getMessage());
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				pstmt = null;
			}
			if (bConnFlag == 0) {
				try {
					mDataAccess.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	/**
	 * ������ɾ��Set�е��������ݺ��ٲ��룬ĳЩ�����Ϊ��֤���벻��ʧ�ܶ�ʹ�ñ�����
	 */
	public boolean deleteAndInsert() {
		if (bConnFlag == 1) {
			if (!delete()) {
				return false;
			}
			return insert();
		} else {
			mDataAccess = new DataAccess();
			bConnFlag = 1;
			try {
				mDataAccess.setAutoCommit(false);
				delete();
				insert();
				mDataAccess.commit();
				return true;
			} catch (Throwable e) {
				e.printStackTrace();
				try {
					mDataAccess.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				return false;
			} finally {
				try {
					mDataAccess.setAutoCommit(true);
					mDataAccess.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				this.mDataAccess = null;
				this.bConnFlag = 0;
			}
		}
	}

	/**
	 * ɾ��Set�е����м�¼�����ݵ�B��
	 */
	public boolean deleteAndBackup() {
		return deleteAndBackup(null, null);
	}

	/**
	 * ɾ��Set�е����м�¼�����ݵ�B����ָ�������˺ͱ��ݱ�ע
	 */
	public boolean deleteAndBackup(String backupOperator, String backupMemo) {
		try {
			backupOperator = StringUtil.isEmpty(backupOperator) ? User.getUserName() : backupOperator;
			backupOperator = StringUtil.isEmpty(backupOperator) ? "SYSTEM" : backupOperator;

			Class c = Class.forName("com.nswt.schema.B" + this.TableCode + "Schema");
			Class s = Class.forName("com.nswt.schema.B" + this.TableCode + "Set");
			SchemaSet bSet = (SchemaSet) s.newInstance();
			Date now = new Date();
			for (int k = 0; k < elementCount; k++) {
				Schema schema = elementData[k];
				Schema bSchema = (Schema) c.newInstance();
				int i = 0;
				for (; i < this.Columns.length; i++) {
					bSchema.setV(i, schema.getV(i));
				}
				bSchema.setV(i, SchemaUtil.getBackupNo());
				bSchema.setV(i + 1, backupOperator);
				bSchema.setV(i + 2, now);
				bSchema.setV(i + 3, backupMemo);
				bSet.add(bSchema);
			}
			if (bConnFlag == 1) {
				bSet.setDataAccess(mDataAccess);
				if (!delete()) {
					return true;
				}
				bSet.insert();
				return true;
			} else {
				mDataAccess = new DataAccess();
				bConnFlag = 1;
				bSet.setDataAccess(mDataAccess);
				try {
					mDataAccess.setAutoCommit(false);
					delete();
					bSet.insert();
					mDataAccess.commit();
					return true;
				} catch (Throwable e) {
					e.printStackTrace();
					try {
						mDataAccess.rollback();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					return false;
				} finally {
					try {
						mDataAccess.setAutoCommit(true);
						mDataAccess.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					this.mDataAccess = null;
					this.bConnFlag = 0;
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * ��Set�е��������ݱ��ݵ�B��
	 */
	public boolean backup() {
		return backup(null, null);
	}

	/**
	 * ��Set�е����м�¼���ݵ�B����ָ�������˺ͱ��ݱ�ע
	 */
	public boolean backup(String backupOperator, String backupMemo) {
		try {
			backupOperator = StringUtil.isEmpty(backupOperator) ? User.getUserName() : backupOperator;
			backupOperator = StringUtil.isEmpty(backupOperator) ? "SYSTEM" : backupOperator;

			Class c = Class.forName("com.nswt.schema.B" + this.TableCode + "Schema");
			Class s = Class.forName("com.nswt.schema.B" + this.TableCode + "Set");
			SchemaSet bSet = (SchemaSet) s.newInstance();
			Date now = new Date();
			for (int k = 0; k < elementCount; k++) {
				Schema schema = elementData[k];
				Schema bSchema = (Schema) c.newInstance();
				int i = 0;
				for (; i < this.Columns.length; i++) {
					bSchema.setV(i, schema.getV(i));
				}
				bSchema.setV(i, SchemaUtil.getBackupNo());
				bSchema.setV(i + 1, backupOperator);
				bSchema.setV(i + 2, now);
				bSchema.setV(i + 3, backupMemo);
				bSet.add(bSchema);
			}
			if (bConnFlag == 1) {
				bSet.setDataAccess(mDataAccess);
			}
			return bSet.insert();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * ���ֶ���ָ��Ҫ�������У����ñ�������query()ʱֻ���ȡָ��,update()ʱֻ�����ָ����
	 */
	public void setOperateColumns(String[] colNames) {
		if (colNames == null || colNames.length == 0) {
			bOperateFlag = false;
			return;
		}
		operateColumnOrders = new int[colNames.length];
		for (int i = 0, k = 0; i < colNames.length; i++) {
			boolean flag = false;
			for (int j = 0; j < Columns.length; j++) {
				if (colNames[i].toString().toLowerCase().equals(Columns[j].getColumnName().toLowerCase())) {
					operateColumnOrders[k] = j;
					k++;
					flag = true;
					break;
				}
			}
			if (!flag) {
				throw new RuntimeException("ָ��������" + colNames[i] + "����ȷ");
			}
		}
		bOperateFlag = true;
	}

	/**
	 * ���ֶ�˳��ָ��Ҫ�������У����ñ�������query()ʱֻ���ȡָ��,update()ʱֻ�����ָ����
	 */
	public void setOperateColumns(int[] colOrder) {
		if (colOrder == null || colOrder.length == 0) {
			bOperateFlag = false;
			return;
		}
		for (int i = 0; i < elementCount; i++) {
			elementData[i].setOperateColumns(colOrder);
		}
		operateColumnOrders = colOrder;
		bOperateFlag = true;
	}

	/**
	 * ��SchemaSetת��ΪDataTable
	 */
	public DataTable toDataTable() {
		if (bOperateFlag) {
			DataColumn[] dcs = new DataColumn[operateColumnOrders.length];
			Object[][] values = new Object[elementCount][Columns.length];
			for (int i = 0; i < operateColumnOrders.length; i++) {
				DataColumn dc = new DataColumn();
				dc.setColumnName(Columns[operateColumnOrders[i]].getColumnName());
				dc.setColumnType(Columns[operateColumnOrders[i]].getColumnType());
				dcs[i] = dc;
			}
			for (int i = 0; i < elementCount; i++) {
				for (int j = 0; j < operateColumnOrders.length; j++) {
					values[i][j] = elementData[i].getV(operateColumnOrders[j]);
				}
			}
			DataTable dt = new DataTable(dcs, values);
			return dt;
		}
		DataColumn[] dcs = new DataColumn[Columns.length];
		Object[][] values = new Object[elementCount][Columns.length];
		for (int i = 0; i < Columns.length; i++) {
			DataColumn dc = new DataColumn();
			dc.setColumnName(Columns[i].getColumnName());
			dc.setColumnType(Columns[i].getColumnType());
			dcs[i] = dc;
		}
		for (int i = 0; i < elementCount; i++) {
			for (int j = 0; j < Columns.length; j++) {
				values[i][j] = elementData[i].getV(j);
			}
		}
		DataTable dt = new DataTable(dcs, values);
		return dt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	public Object clone() {
		SchemaSet set = this.newInstance();
		for (int i = 0; i < this.size(); i++) {
			set.add((Schema) elementData[i].clone());
		}
		return set;
	}

	/**
	 * ��ָ���ıȽ���������
	 */
	public void sort(Comparator c) {
		Schema[] newData = new Schema[elementCount];
		System.arraycopy(elementData, 0, newData, 0, elementCount);
		Arrays.sort(newData, c);
		elementData = newData;
	}

	/**
	 * ��ָ��������������
	 */
	public void sort(String columnName) {
		sort(columnName, "desc", false);
	}

	/**
	 * ��ָ�����С�����ʽ��desc��asc������
	 */
	public void sort(String columnName, String order) {
		sort(columnName, order, false);
	}

	/**
	 * ��ָ�����С�����ʽ��desc��asc���������isNumberΪ�գ���Ƚϴ�Сʱ�Ƚ��ֶ�ֵת��Ϊ��ֵ
	 */
	public void sort(String columnName, String order, final boolean isNumber) {
		final String cn = columnName;
		final String od = order;
		sort(new Comparator() {
			public int compare(Object obj1, Object obj2) {
				DataRow dr1 = ((Schema) obj1).toDataRow();
				DataRow dr2 = ((Schema) obj2).toDataRow();
				Object v1 = dr1.get(cn);
				Object v2 = dr2.get(cn);
				if (v1 instanceof Number && v2 instanceof Number) {
					double d1 = ((Number) v1).doubleValue();
					double d2 = ((Number) v2).doubleValue();
					if (d1 == d2) {
						return 0;
					} else if (d1 > d2) {
						return "asc".equalsIgnoreCase(od) ? 1 : -1;
					} else {
						return "asc".equalsIgnoreCase(od) ? -1 : 1;
					}
				} else if (v1 instanceof Date && v2 instanceof Date) {
					Date d1 = (Date) v1;
					Date d2 = (Date) v1;
					if ("asc".equalsIgnoreCase(od)) {
						return d1.compareTo(d2);
					} else {
						return -d1.compareTo(d2);
					}
				} else if (isNumber) {
					double d1 = 0, d2 = 0;
					try {
						d1 = Double.parseDouble(String.valueOf(v1));
						d2 = Double.parseDouble(String.valueOf(v2));
					} catch (Exception e) {
					}
					if (d1 == d2) {
						return 0;
					} else if (d1 > d2) {
						return "asc".equalsIgnoreCase(od) ? -1 : 1;
					} else {
						return "asc".equalsIgnoreCase(od) ? 1 : -1;
					}
				} else {
					int c = dr1.getString(cn).compareTo(dr2.getString(cn));
					if ("asc".equalsIgnoreCase(od)) {
						return c;
					} else {
						return -c;
					}
				}
			}
		});
	}

	/**
	 * ʹ��ָ���Ĺ��������˵�SchemaSet�е�Schema
	 */
	public SchemaSet filter(Filter filter) {
		SchemaSet set = this.newInstance();
		for (int i = 0; i < elementCount; i++) {
			if (filter.filter(elementData[i])) {
				set.add((Schema) elementData[i].clone());
			}
		}
		return set;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < elementCount; i++) {
			sb.append(elementData[i] + "\n");
		}
		return sb.toString();
	}

	/**
	 * ��ʼ��һ���µ�SchmeaSetʵ������������������ʵ��
	 */
	protected abstract SchemaSet newInstance();
}
