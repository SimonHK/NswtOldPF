package com.nswt.framework.orm;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

import javax.crypto.Cipher;

import org.bouncycastle.jce.provider.JDKX509CertificateFactory;

import com.nswt.framework.Config;
import com.nswt.framework.data.DBConn;
import com.nswt.framework.data.DBConnPool;
import com.nswt.framework.data.DataAccess;
import com.nswt.framework.data.DataColumn;
import com.nswt.framework.data.DataRow;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.license.SystemInfo;
import com.nswt.framework.messages.LongTimeTask;
import com.nswt.framework.security.ZRSACipher;
import com.nswt.framework.utility.BufferedRandomAccessFile;
import com.nswt.framework.utility.FileUtil;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.NumberUtil;
import com.nswt.framework.utility.StringUtil;
import com.nswt.framework.utility.ZipUtil;

/**
 * @Author NSWT
 * @Date 2008-10-13
 * @Mail nswt@nswt.com.cn
 */
public class DBImport {
	private DataAccess da;

	private LongTimeTask task;

	public void setTask(LongTimeTask task) {
		this.task = task;
	}

	public void importDB(String file) {
		importDB(file, "");
	}

	public String getSQL(String file, String dbtype) {
		TableCreator tc = new TableCreator(dbtype);
		BufferedRandomAccessFile braf = null;
		try {
			braf = new BufferedRandomAccessFile(file, "r");
			HashMap map = new HashMap();
			while (braf.getFilePointer() != braf.length()) {
				// �ȶ�ȡ����
				byte[] bs = new byte[4];
				braf.read(bs);
				int len = NumberUtil.toInt(bs);
				bs = new byte[len];
				braf.read(bs);

				// �ٶ�ȡ����
				bs = new byte[4];
				braf.read(bs);
				len = NumberUtil.toInt(bs);
				bs = new byte[len];
				braf.read(bs);
				bs = ZipUtil.unzip(bs);
				Object obj = FileUtil.unserialize(bs);
				if (obj == null) {
					continue;
				}
				if (obj instanceof SchemaSet) {
					SchemaSet set = (SchemaSet) obj;
					if (set != null && !map.containsKey(set.TableCode)) {
						tc.createTable(set.Columns, set.TableCode);
						map.put(set.TableCode, "");
					}
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			if (braf != null) {
				try {
					braf.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return tc.getAllSQL();
	}

	public boolean importDB(String file, String poolName) {
		DBConn conn = DBConnPool.getConnection(poolName);
		try {
			return importDB(file, conn, true);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean importDB(String file, DBConn conn, boolean autoCreate) {
		da = new DataAccess(conn);
		BufferedRandomAccessFile braf = null;
		try {
			braf = new BufferedRandomAccessFile(file, "r");
			HashMap map = new HashMap();
			int i = 0;
			TableCreator tc = new TableCreator(da.getConnection().getDBConfig().DBType);
			while (braf.getFilePointer() != braf.length()) {
				// �ȶ�ȡ����
				byte[] bs = new byte[4];
				braf.read(bs);
				int len = NumberUtil.toInt(bs);
				bs = new byte[len];
				braf.read(bs);
				String name = new String(bs);

				// �ٶ�ȡ����
				bs = new byte[4];
				braf.read(bs);
				len = NumberUtil.toInt(bs);
				bs = new byte[len];
				braf.read(bs);
				bs = ZipUtil.unzip(bs);
				try {
					Object obj = FileUtil.unserialize(bs);
					if (obj == null) {
						continue;
					}
					if (obj instanceof SchemaSet) {
						SchemaSet set = (SchemaSet) obj;
						try {
							if (!map.containsKey(set.TableCode)) {
								tc.createTable(set.Columns, set.TableCode, autoCreate);
								tc.executeAndClear(conn);
								map.put(set.TableCode, "");
							}
							if (task != null) {
								task.setPercent(new Double(i++ * 100.0 / 600).intValue());
								task.setCurrentInfo("���ڵ����" + set.TableCode);
							}
							if (!importOneSet(set)) {
								return false;
							}
						} catch (Exception e) {
							LogUtil.warn("δ�ɹ������" + set.TableCode);
							e.printStackTrace();
						}
					}
					if (obj instanceof DataTable) {// �Զ����
						try {
							QueryBuilder insertQB = null;
							if (!map.containsKey(name)) {
								QueryBuilder qb = new QueryBuilder(
										"select * from ZCCustomTableColumn where TableID in "
												+ "(select ID from ZCCustomTable where Code=? and Type='Custom')", name);
								DataTable cdt = da.executeDataTable(qb);
								SchemaColumn[] scs = new SchemaColumn[cdt.getRowCount()];
								for (int j = 0; j < scs.length; j++) {
									DataRow cdr = cdt.getDataRow(j);
									int type = Integer.parseInt(cdr.getString("DataType"));
									SchemaColumn sc = new SchemaColumn(cdr.getString("Code"), type, j, (int) cdr
											.getInt("Length"), 0, "Y".equals(cdr.getString("isMandatory")), "Y"
											.equals(cdr.getString("isPrimaryKey")));
									scs[j] = sc;
								}
								tc.createTable(scs, name, autoCreate);
								tc.executeAndClear(conn);
								map.put(name, "");
								StringBuffer sb = new StringBuffer("insert into " + name + "(");
								for (int j = 0; j < cdt.getRowCount(); j++) {
									if (j != 0) {
										sb.append(",");
									}
									sb.append(cdt.get(j, "Code"));
								}
								sb.append(") values (");
								for (int j = 0; j < cdt.getRowCount(); j++) {
									if (j != 0) {
										sb.append(",");
									}
									sb.append("?");
								}
								sb.append(")");
								insertQB = new QueryBuilder(sb.toString());
								insertQB.setBatchMode(true);
							}
							if (task != null) {
								task.setPercent(new Double(i++ * 100.0 / 600).intValue());
								task.setCurrentInfo("���ڵ����" + name);
							}
							importOneTable(name, (DataTable) obj, insertQB);
						} catch (Exception e) {
							LogUtil.warn("δ�ɹ������" + name);
							e.printStackTrace();
						}
					}
				} catch (Exception e) {// �����ļ��е�Schema�뱾�ص�Schema��һ��
					LogUtil.getLogger().warn("��������ʱ��������:" + e.getMessage());
					continue;
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			return false;
		} finally {
			if (braf != null) {
				try {
					braf.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return true;
	}

	private boolean importOneSet(SchemaSet set) throws Exception {
		set.setDataAccess(da);
		if (da.getConnection().getDBConfig().isOracle()) {// ��Ҫ��飬Mysql
			// NotNull����������ַ�������Oracle������
			for (int i = 0; i < set.size(); i++) {
				Schema schema = set.getObject(i);
				for (int j = 0; j < schema.Columns.length; j++) {
					Object v = schema.getV(j);
					if (schema.Columns[j].isMandatory() && (v == null || v.equals(""))) {
						LogUtil.warn("��" + schema.TableCode + "��" + schema.Columns[j].getColumnName() + "�в���Ϊ��!");
						set.remove(schema);
						i--;
						break;
					}
				}
			}
		}
		return set.insert();
	}

	/**
	 * �����Զ����
	 */
	private void importOneTable(String code, DataTable dt, QueryBuilder qb) throws Exception {
		// �п����Զ������ģ�鲻����
		try {
			qb.getParams().clear();
			for (int i = 0; i < dt.getRowCount(); i++) {
				for (int j = 0; j < dt.getColCount(); j++) {
					if (j == dt.getColCount() - 1
							&& (dt.getDataColumn(j).getColumnName().equalsIgnoreCase("RNM") || dt.getDataColumn(j)
									.getColumnName().equalsIgnoreCase("_RowNumber"))) {
						break;
					}
					String v = dt.getString(i, j);
					if (StringUtil.isEmpty(v)) {
						v = null;
					}
					if (v != null && dt.getDataColumn(j).getColumnType() == DataColumn.INTEGER) {
						qb.add(Integer.parseInt(v));
					} else {
						qb.add(v);
					}
				}
				qb.addBatch();
			}
			da.executeNoQuery(qb);
		} catch (Throwable t) {
			t.printStackTrace();
			return;// �п���û���Զ������ص�ģ��
		}
	}
}
