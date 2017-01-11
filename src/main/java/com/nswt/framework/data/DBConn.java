/**
 * ����: NSWT<br>
 * ���ڣ�2016-6-29<br>
 * �ʼ���nswt@nswt.com.cn<br>
 */
package com.nswt.framework.data;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

import org.apache.commons.lang.ArrayUtils;

import com.nswt.framework.Config;
import com.nswt.framework.data.nativejdbc.CommonsDbcpNativeJdbcExtractor;
import com.nswt.framework.data.nativejdbc.JBossNativeJdbcExtractor;
import com.nswt.framework.data.nativejdbc.WebLogicNativeJdbcExtractor;
import com.nswt.framework.data.nativejdbc.WebSphereNativeJdbcExtractor;
import com.nswt.framework.utility.Mapx;

public class DBConn implements Connection {
	protected boolean LongTimeFlag = false;

	protected Connection Conn;

	protected long LastApplyTime;

	protected boolean isUsing = false;

	protected String CallerString = null;

	protected int ConnID = 0;

	protected long LastWarnTime;// �ϴξ���ʱ��'

	protected long LastSuccessExecuteTime = System.currentTimeMillis();

	protected boolean isBlockingTransactionStarted;// �Ƿ�������������֮��

	protected DBConnConfig DBConfig;

	protected DBConn() {
	}

	public Connection getPhysicalConnection() {
		if (DBConfig.isJNDIPool) {
			try {
				if (Config.isTomcat()) {
					return CommonsDbcpNativeJdbcExtractor.doGetNativeConnection(Conn);
				} else if (Config.isWeblogic()) {
					return WebLogicNativeJdbcExtractor.doGetNativeConnection(Conn);
				} else if (Config.isWebSphere()) {
					return WebSphereNativeJdbcExtractor.doGetNativeConnection(Conn);
				} else {// JBOSS
					return JBossNativeJdbcExtractor.doGetNativeConnection(Conn);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return Conn;
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see java.sql.Connection#getHoldability()
	 */
	public int getHoldability() throws SQLException {
		return Conn.getHoldability();
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see java.sql.Connection#getTransactionIsolation()
	 */
	public int getTransactionIsolation() throws SQLException {
		return Conn.getTransactionIsolation();
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see java.sql.Connection#clearWarnings()
	 */
	public void clearWarnings() throws SQLException {
		Conn.clearWarnings();
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see java.sql.Connection#close()
	 */
	public void close() throws SQLException {// �������ر�����
		isUsing = false;
		LastApplyTime = 0;
		setAutoCommit(true);
		removeConnID(ConnID);
		if (DBConfig.isJNDIPool) {
			this.Conn.close();
		}
	}

	public void closeReally() throws SQLException {// �����ر�
		DBConnPoolImpl pool = (DBConnPoolImpl) DBConnPool.PoolMap.get(DBConfig.DBName + ".");
		removeConnID(ConnID);
		if (pool != null) {
			ArrayUtils.removeElement(pool.conns, this);
		}
		Conn.close();
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see java.sql.Connection#commit()
	 */
	public void commit() throws SQLException {
		if(!Conn.getAutoCommit()){
			Conn.commit();
		}
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see java.sql.Connection#rollback()
	 */
	public void rollback() throws SQLException {
		Conn.rollback();
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see java.sql.Connection#getAutoCommit()
	 */
	public boolean getAutoCommit() throws SQLException {
		return Conn.getAutoCommit();
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see java.sql.Connection#isClosed()
	 */
	public boolean isClosed() throws SQLException {
		return Conn.isClosed();// ���Ǵ��ڴ�״̬
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see java.sql.Connection#isReadOnly()
	 */
	public boolean isReadOnly() throws SQLException {
		return Conn.isReadOnly();
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see java.sql.Connection#setHoldability(int)
	 */
	public void setHoldability(int holdability) throws SQLException {
		Conn.setHoldability(holdability);
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see java.sql.Connection#setTransactionIsolation(int)
	 */
	public void setTransactionIsolation(int level) throws SQLException {
		Conn.setTransactionIsolation(level);
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see java.sql.Connection#setAutoCommit(boolean)
	 */
	public void setAutoCommit(boolean autoCommit) throws SQLException {
		if (Conn.getAutoCommit() != autoCommit) {
			Conn.setAutoCommit(autoCommit);
			// ����Sybase�¶�ε���setAutoCommit����SET CHAINED command not allowed
			// within multi-statement transaction�Ĵ���
		}
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see java.sql.Connection#setReadOnly(boolean)
	 */
	public void setReadOnly(boolean readOnly) throws SQLException {
		Conn.setReadOnly(readOnly);
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see java.sql.Connection#getCatalog()
	 */
	public String getCatalog() throws SQLException {
		return Conn.getCatalog();
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see java.sql.Connection#setCatalog(java.lang.String)
	 */
	public void setCatalog(String catalog) throws SQLException {
		Conn.setCatalog(catalog);
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see java.sql.Connection#getMetaData()
	 */
	public DatabaseMetaData getMetaData() throws SQLException {
		return Conn.getMetaData();
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see java.sql.Connection#getWarnings()
	 */
	public SQLWarning getWarnings() throws SQLException {
		return Conn.getWarnings();
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see java.sql.Connection#setSavepoint()
	 */
	public Savepoint setSavepoint() throws SQLException {
		return Conn.setSavepoint();
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see java.sql.Connection#releaseSavepoint(java.sql.Savepoint)
	 */
	public void releaseSavepoint(Savepoint savepoint) throws SQLException {
		Conn.releaseSavepoint(savepoint);
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see java.sql.Connection#rollback(java.sql.Savepoint)
	 */
	public void rollback(Savepoint savepoint) throws SQLException {
		Conn.rollback(savepoint);
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see java.sql.Connection#createStatement()
	 */
	public Statement createStatement() throws SQLException {
		return Conn.createStatement();
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see java.sql.Connection#createStatement(int, int)
	 */
	public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
		return Conn.createStatement(resultSetType, resultSetConcurrency);
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see java.sql.Connection#createStatement(int, int, int)
	 */
	public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {
		return Conn.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see java.sql.Connection#getTypeMap()
	 */
	public Map getTypeMap() throws SQLException {
		return Conn.getTypeMap();
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see java.sql.Connection#setTypeMap(java.util.Map)
	 */
	public void setTypeMap(Map map) throws SQLException {
		Conn.setTypeMap(map);
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see java.sql.Connection#nativeSQL(java.lang.String)
	 */
	public String nativeSQL(String sql) throws SQLException {
		return Conn.nativeSQL(sql);
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see java.sql.Connection#prepareCall(java.lang.String)
	 */
	public CallableStatement prepareCall(String sql) throws SQLException {
		return Conn.prepareCall(sql);
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see java.sql.Connection#prepareCall(java.lang.String, int, int)
	 */
	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
		return Conn.prepareCall(sql, resultSetType, resultSetConcurrency);
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see java.sql.Connection#prepareCall(java.lang.String, int, int, int)
	 */
	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
			int resultSetHoldability) throws SQLException {
		return Conn.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see java.sql.Connection#prepareStatement(java.lang.String)
	 */
	public PreparedStatement prepareStatement(String sql) throws SQLException {
		return Conn.prepareStatement(sql);
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see java.sql.Connection#prepareStatement(java.lang.String, int)
	 */
	public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
		return Conn.prepareStatement(sql, autoGeneratedKeys);
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see java.sql.Connection#prepareStatement(java.lang.String, int, int)
	 */
	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
			throws SQLException {
		return Conn.prepareStatement(sql, resultSetType, resultSetConcurrency);
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see java.sql.Connection#prepareStatement(java.lang.String, int, int,
	 * int)
	 */
	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
			int resultSetHoldability) throws SQLException {
		return Conn.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see java.sql.Connection#prepareStatement(java.lang.String, int[])
	 */
	public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
		return Conn.prepareStatement(sql, columnIndexes);
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see java.sql.Connection#setSavepoint(java.lang.String)
	 */
	public Savepoint setSavepoint(String name) throws SQLException {
		return Conn.setSavepoint(name);
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see java.sql.Connection#prepareStatement(java.lang.String,
	 * java.lang.String[])
	 */
	public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
		return Conn.prepareStatement(sql, columnNames);
	}

	public long getLastSuccessExecuteTime() {
		return LastSuccessExecuteTime;
	}

	public void setLastSuccessExecuteTime(long lastSuccessExecuteTime) {
		LastSuccessExecuteTime = lastSuccessExecuteTime;
	}

	public DBConnConfig getDBConfig() {
		return DBConfig;
	}

	public void setPoolName(DBConnConfig dbcc) {
		DBConfig = dbcc;
	}

	/**
	 * ����1.2��ǰ��д��
	 * 
	 * @deprecated
	 */
	public String getDBType() {
		return DBConfig.DBType;
	}

	private static Mapx IDMap = new Mapx();

	private static Object mutex = new Object();

	/**
	 * ���ӵ�ΨһID����Щ���ݿ�����Sybase��Ҫ������������ʱ��
	 */
	public static int getConnID() {
		synchronized (mutex) {
			for (int i = 1; i <= 2000; i++) {// ���2000������
				if (!IDMap.containsKey(i + "")) {
					IDMap.put(i + "", "1");
					return i;
				}
			}
		}
		return 0;
	}

	public static void removeConnID(int id) {
		synchronized (mutex) {
			IDMap.remove(id + "");
		}
	}

	public Array createArrayOf(String arg0, Object[] arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Blob createBlob() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Clob createClob() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public NClob createNClob() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public SQLXML createSQLXML() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Struct createStruct(String arg0, Object[] arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSchema(String schema) throws SQLException {

	}

	@Override
	public String getSchema() throws SQLException {
		return null;
	}

	@Override
	public void abort(Executor executor) throws SQLException {

	}

	@Override
	public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {

	}

	@Override
	public int getNetworkTimeout() throws SQLException {
		return 0;
	}

	public Properties getClientInfo() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getClientInfo(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isValid(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public void setClientInfo(Properties arg0) throws SQLClientInfoException {
		// TODO Auto-generated method stub
		
	}

	public void setClientInfo(String arg0, String arg1)
			throws SQLClientInfoException {
		// TODO Auto-generated method stub
		
	}

	public boolean isWrapperFor(Class arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public Object unwrap(Class arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
}
