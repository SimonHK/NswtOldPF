package com.nswt.framework.data;

import com.nswt.framework.Config;
import com.nswt.framework.utility.Mapx;

/**
 * ���ӳ���,ͬʱ֧�ֶ�����ӳ�
 * 
 * @Author NSWT
 * @Date 2007-5-30
 * @Mail nswt@nswt.com.cn
 */
public class DBConnPool {
	protected static Mapx PoolMap = new Mapx();
	private static Object mutex = new Object();

	public static DBConn getConnection() {
		return getConnection(null, false);
	}

	public static DBConn getConnection(boolean bLongTimeFlag) {
		return getConnection(null, bLongTimeFlag);
	}

	public static DBConn getConnection(String poolName) {
		return getConnection(poolName, false);
	}

	public static DBConnConfig getDBConnConfig() {
		return getDBConnConfig(null);
	}

	public static DBConnConfig getDBConnConfig(String poolName) {
		if (poolName == null || poolName.equals("")) {
			poolName = "Default";
		}
		poolName = poolName + ".";
		Object o = PoolMap.get(poolName);
		DBConnPoolImpl pool = null;
		if (o == null) {
			synchronized (mutex) {
				if (Config.getValue("Database." + poolName + "Type") != null) {
					pool = new DBConnPoolImpl(poolName);
					PoolMap.put(poolName, pool);
				} else {
					throw new RuntimeException("ָ�������ӳز�����:" + poolName);
				}
			}
		} else {
			pool = (DBConnPoolImpl) o;
		}
		return pool.getDBConnConfig();
	}

	public static DBConn getConnection(String poolName, boolean bLongTimeFlag) {
		return getConnection(poolName, bLongTimeFlag, true);
	}

	public static DBConn getConnection(String poolName, boolean bLongTimeFlag, boolean bCurrentThreadConnectionFlag) {
		if (poolName == null || poolName.equals("")) {
			poolName = "Default";
		}
		poolName = poolName + ".";
		if (bCurrentThreadConnectionFlag) {
			DBConn conn = BlockingTransaction.getCurrentThreadConnection();
			if (conn != null && conn.DBConfig.PoolName.equals(poolName)) {
				return conn;// ����������������񣬲������е����ӵ����ӳ����͵�ǰ��������ӳ�����һ�£���ֱ�ӷ��ظ����ӣ��Ա�֤��������������ܹ���ѯ����ȷ�����ݡ�
			}
		}
		Object o = PoolMap.get(poolName);
		DBConnPoolImpl pool = null;
		if (o == null) {
			synchronized (mutex) {
				if (Config.getValue("Database." + poolName + "Type") != null) {
					pool = new DBConnPoolImpl(poolName);
					PoolMap.put(poolName, pool);
				} else {
					throw new RuntimeException("ָ�������ӳز�����:" + poolName);
				}
			}

		} else {
			pool = (DBConnPoolImpl) o;
		}
		return pool.getConnection(bLongTimeFlag);
	}

	public static Mapx getPoolMap() {
		return PoolMap;
	}
}
