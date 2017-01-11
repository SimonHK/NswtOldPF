/**
 * 作者：NSWT<br>
 * 日期：2016-7-5<br>
 * 邮件：nswt@nswt.com.cn<br>
 */
package com.nswt.platform.pub;

import java.sql.SQLException;

import com.nswt.framework.Config;
import com.nswt.framework.data.DBConn;
import com.nswt.framework.data.DBConnPool;
import com.nswt.framework.data.DataAccess;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.utility.StringUtil;
import com.nswt.schema.ZDMaxNoSchema;
import com.nswt.schema.ZDMaxNoSet;

public class NoUtil {
	private static ZDMaxNoSet MaxNoSet;
	private static Object mutex = new Object();

	public static String getMaxNo(String noType) {
		return getMaxNo(noType, "SN");
	}

	public static String getMaxNoLoal(String noType) {
		return getMaxNoLocal(noType, "SN");
	}

	public static long getMaxID(String noType, String subType) {
		// 注释掉本段逻辑，统一使用lock方式。在本地模式下，如果有多个应用同时部署在生产环境中，会导致主键冲突
		// if (Config.isDebugMode()) {
		return getMaxIDUseLock(noType, subType);
		// }
		// return getMaxIDLocal(noType, subType);
	}

	public synchronized static long getMaxIDUseLock(String noType, String subType) {
		DBConn conn = DBConnPool.getConnection("Default", false, false);// 不能使用阻塞型事务中的连接
		DataAccess da = new DataAccess(conn);
		try {
			da.setAutoCommit(false);
			QueryBuilder qb = new QueryBuilder("select MxValue from ZDMaxNo where NoType=? and NoSubType=?", noType,
					subType);
			if (Config.isOracle()) {
				qb.append(" for update");
			}
			Object mxValue = da.executeOneValue(qb);
			if (mxValue != null) {
				long t = Long.parseLong(mxValue.toString()) + 1;
				qb = new QueryBuilder("update ZDMaxNo set MxValue=? where NoType=? and NoSubType=?", t, noType);
				qb.add(subType);
				da.executeNoQuery(qb);
				da.commit();
				return t;
			} else {
				ZDMaxNoSchema maxno = new ZDMaxNoSchema();
				maxno.setNoType(noType);
				maxno.setNoSubType(subType);
				maxno.setMxValue(1);
				maxno.setLength(10);
				maxno.setDataAccess(da);
				if (maxno.insert()) {
					da.commit();
					return 1;
				} else {
					throw new RuntimeException("获取最大号时发生错误!");
				}
			}
		} catch (Exception e) {
			try {
				da.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new RuntimeException("获取最大号时发生错误:" + e.getMessage());
		} finally {
			try {
				da.setAutoCommit(true);
				da.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 得到类型为noType位长为length的编码
	 */
	public static String getMaxNo(String noType, int length) {
		long t = getMaxID(noType, "SN");
		String no = String.valueOf(t);
		if (no.length() > length) {
			return no.substring(0, length);
		}
		return StringUtil.leftPad(no, '0', length);
	}

	/**
	 * 得到类型为noType位长为length且前缀为prefix的编码
	 */
	public static String getMaxNo(String noType, String prefix, int length) {
		long t = getMaxID(noType, prefix);
		String no = String.valueOf(t);
		if (no.length() > length) {
			return no.substring(0, length);
		}
		return prefix + StringUtil.leftPad(no, '0', length);
	}

	public synchronized static String getMaxNoUseLock(String noType, String subType) {
		DataAccess da = new DataAccess();
		try {
			da.setAutoCommit(false);
			QueryBuilder qb = new QueryBuilder("select MxValue,Length from ZDMaxNo where NoType=? and NoSubType=?",
					noType, subType);
			if (Config.isOracle()) {
				qb.append(" for update");
			}
			DataTable dt = qb.executeDataTable();
			if (dt.getRowCount() > 0) {
				long t = Long.parseLong(dt.getString(0, "MxValue")) + 1;
				int length = Integer.parseInt(dt.getString(0, "Length"));
				String no = String.valueOf(t);
				if (length > 0) {
					no = StringUtil.leftPad(no, '0', length);
				}
				qb = new QueryBuilder("update ZDMaxNo set MxValue=? where NoType=? and NoSubType=?", t, noType);
				qb.add(subType);
				da.executeNoQuery(qb);
				da.commit();
				return no;
			} else {
				ZDMaxNoSchema maxno = new ZDMaxNoSchema();
				maxno.setNoType(noType);
				maxno.setNoSubType(subType);
				maxno.setMxValue(1);
				maxno.setLength(10);
				maxno.setDataAccess(da);
				if (maxno.insert()) {
					da.commit();
					return "0000000001";
				} else {
					throw new RuntimeException("获取最大号时发生错误!");
				}
			}
		} catch (Exception e) {
			try {
				da.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new RuntimeException("获取最大号时发生错误:" + e.getMessage());
		} finally {
			try {
				da.setAutoCommit(true);
				da.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public synchronized static long getMaxIDLocal(String noType, String subType) {
		if (MaxNoSet == null) {
			init();
		}
		ZDMaxNoSchema maxno = null;
		if (MaxNoSet != null) {
			for (int i = 0; i < MaxNoSet.size(); i++) {
				maxno = MaxNoSet.get(i);
				if (maxno.getNoType().equals(noType) && maxno.getNoSubType().equals(subType)) {
					synchronized (mutex) {
						maxno.setMxValue(maxno.getMxValue() + 1);
						if (!maxno.update()) {
							throw new RuntimeException("生成最大号错误,MaxNoType=" + noType + ",MaxSubType=" + subType);
						}
						return maxno.getMxValue();
					}
				}
			}
		} else {
			synchronized (mutex) {
				MaxNoSet = new ZDMaxNoSet();
				maxno = new ZDMaxNoSchema();
				maxno.setNoType(noType);
				maxno.setNoSubType(subType);
				maxno.setLength(0);
				maxno.setMxValue(1);
				maxno.insert();
				MaxNoSet.add(maxno);
				return 1;
			}
		}
		// 运行到此处是因为这是新加的MaxNoType
		synchronized (mutex) {
			maxno = new ZDMaxNoSchema();
			maxno.setNoType(noType);
			maxno.setNoSubType(subType);
			maxno.setLength(10);
			maxno.setMxValue(1);
			maxno.insert();
			MaxNoSet.add(maxno);
			return 1;
		}
	}

	public static long getMaxID(String noType) {
		return getMaxID(noType, "ID");
	}

	public static long getMaxIDLocal(String noType) {
		return getMaxIDLocal(noType, "ID");
	}

	public static String getMaxNo(String noType, String subType) {
		if (Config.isDebugMode()) {
			return getMaxNoUseLock(noType, subType);
		}
		return getMaxNoLocal(noType, subType);
	}

	public synchronized static String getMaxNoLocal(String noType, String subType) {
		if (MaxNoSet == null) {
			init();
		}
		ZDMaxNoSchema maxno = null;
		if (MaxNoSet != null) {
			for (int i = 0; i < MaxNoSet.size(); i++) {
				maxno = MaxNoSet.get(i);
				if (maxno.getNoType().equals(noType) && maxno.getNoSubType().equals(subType)) {
					synchronized (mutex) {
						maxno.setMxValue(maxno.getMxValue() + 1);
						if (!maxno.update()) {
							throw new RuntimeException("生成最大号错误,NoType=" + noType + ",MaxSubType=" + subType);
						}
						if (maxno.getLength() <= 0) {
							return String.valueOf(maxno.getMxValue());
						}
						return StringUtil.leftPad(String.valueOf(maxno.getMxValue()), '0', (int) maxno.getLength());
					}
				}
			}
		} else {
			synchronized (mutex) {
				MaxNoSet = new ZDMaxNoSet();
				maxno = new ZDMaxNoSchema();
				maxno.setNoType(noType);
				maxno.setNoSubType(subType);
				maxno.setLength(10);
				maxno.setMxValue(1);
				maxno.insert();
				MaxNoSet.add(maxno);
				return "0000000001";
			}
		}
		// 运行到此处是因为这是新加的NoType
		synchronized (mutex) {
			maxno = new ZDMaxNoSchema();
			maxno.setNoType(noType);
			maxno.setNoSubType(subType);
			maxno.setLength(10);
			maxno.setMxValue(1);
			maxno.insert();
			MaxNoSet.add(maxno);
			return "0000000001";
		}
	}

	private static synchronized void init() {
		if (MaxNoSet != null) {
			return;
		}
		ZDMaxNoSchema maxno = new ZDMaxNoSchema();
		MaxNoSet = maxno.query();
	}
}
