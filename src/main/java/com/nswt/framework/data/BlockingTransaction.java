package com.nswt.framework.data;

import java.sql.SQLException;

import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.utility.Executor;

/**
 * �������������࣬ʹ�ñ��ദ������ʱ����һ��ʼ��ռ�����ݿ����ӣ�<br>
 * �����ύʱ�Զ��ͷ����ӡ�<br>
 * ��������±���ʹ�ñ��ദ������<br>
 * 1�������к���������Ҫ��ѯǰ������Ľ����<br>
 * 2���������漰���������ݵĲ�����<br>
 * 3�������ύ����Զ��ر����ӣ����һֱδ�ر����ӣ�������˳�Servlet�߳�ʱ�Զ��رա�<br>
 * ��һ�β���һ����������Ϊ����ʹ�÷���������<br>
 * �����Ƿֳ�1��β�����һ�β���100����Ҳ�Ὣ100w������ȫ���������ڴ��У�<br>
 * �����Ҫ100�������ݵ��ڴ���������ʹ�ñ���ʱ,��ÿ�β��������ύ�����ݿ⣬<br>
 * ��ʼ��ֻ��Ҫ100�����ݵ��ڴ�������<br>
 * 
 * @see com.nswt.framework.data.Transaction
 * @since 1.3
 * @Author ������
 * @Date 2016-4-7
 * @Mail nswt@nswt.com.cn
 */
public class BlockingTransaction extends Transaction {
	/**
	 * �Ƿ���δ�����������Ƿ��в����Ѿ����͵����ݿ⵫��δ�ύ��δ�ع���
	 */
	private boolean isExistsOpeningOperate = false;

	private static ThreadLocal current = new ThreadLocal();

	private DBConn conn;

	public BlockingTransaction() {
		if (dataAccess == null) {
			conn = DBConnPool.getConnection();
			conn.isBlockingTransactionStarted = true;
			dataAccess = new DataAccess(conn);
			try {
				dataAccess.setAutoCommit(false);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			bindTransactionToThread();
		}
	}

	public BlockingTransaction(String poolName) {
		dataAccess = new DataAccess(DBConnPool.getConnection(poolName));
		conn = dataAccess.getConnection();
		conn.isBlockingTransactionStarted = true;
		try {
			dataAccess.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		bindTransactionToThread();
	}

	public BlockingTransaction(DataAccess da) {
		dataAccess = da;
		conn = dataAccess.getConnection();
		conn.isBlockingTransactionStarted = true;
		try {
			dataAccess.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		bindTransactionToThread();
	}

	/**
	 * ���õ�ǰ����ʹ�õ�DataAccess����
	 */
	public void setDataAccess(DataAccess dAccess) {
		if (dataAccess != null && !outerConnFlag) {//
			throw new RuntimeException("BlockingTransaction.setDataAccess():������룬����������ֻ��������ʼ֮ǰ����DataAccess");
		}
		super.setDataAccess(dAccess);
	}

	/**
	 * ����һ��SQL����,���������������͵����ݿ�
	 */
	public void add(QueryBuilder qb) {
		executeWithBlockedConnection(qb, Transaction.SQL, true);
	}

	/**
	 * ����һ��Schema����,���������������͵����ݿ�
	 */
	public void add(Schema schema, int type) {
		executeWithBlockedConnection(schema, type, true);
	}

	/**
	 * ����һ��SchemaSet����,���������������͵����ݿ�
	 */
	public void add(SchemaSet set, int type) {
		executeWithBlockedConnection(set, type, true);
	}

	/**
	 * ����һ��SQL����,���������������͵����ݿ�
	 */
	public void addWithException(QueryBuilder qb) throws Exception {
		executeWithBlockedConnection(qb, Transaction.SQL, false);
	}

	/**
	 * ����һ��Schema����,���������������͵����ݿ�
	 */
	public void addWithException(Schema schema, int type) throws Exception {
		executeWithException(schema, type);
	}

	/**
	 * ����һ��SchemaSet����,���������������͵����ݿ�
	 */
	public void addWithException(SchemaSet set, int type) throws Exception {
		executeWithException(set, type);
	}

	/**
	 * ִ�в�������ʼռ������,���ִ��������ֱ�ӻع�
	 */
	private void executeWithBlockedConnection(Object obj, int type, boolean rollBackFlag) {
		try {
			executeObject(obj, type);
			isExistsOpeningOperate = true;
		} catch (SQLException e) {
			if (!outerConnFlag && rollBackFlag) {
				try {
					dataAccess.rollback();
					conn.isBlockingTransactionStarted = false;
				} catch (SQLException e1) {
					e1.printStackTrace();
				} finally {
					try {
						dataAccess.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
			throw new RuntimeException(e);
		}
	}

	/**
	 * ִ�в�������ʼռ������,���ִ���������׳��쳣
	 */
	private void executeWithException(Object obj, int type) throws Exception {
		executeObject(obj, type);
		isExistsOpeningOperate = true;
	}

	/**
	 * �ύ�������ݿ�
	 */
	public boolean commit() {
		return commit(true);
	}

	/**
	 * �ύ���񣬲��ر�����<br>
	 * ��setAutoCommitStatusΪfalse����ʹ�õ����ⲿ��DataAccess�����ύ�󲢲��������ӵ�AutoCommit״̬��
	 */
	public boolean commit(boolean setAutoCommitStatus) {
		if (dataAccess != null) {
			try {
				dataAccess.commit();
			} catch (SQLException e) {
				e.printStackTrace();
				this.exceptionMessage = e.getMessage();
				if (!outerConnFlag) {
					try {
						dataAccess.rollback();// ����д���ع����п���add()ʱû�д��󣬵��ύʱ����
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				return false;
			} finally {
				try {
					if (!outerConnFlag || setAutoCommitStatus) {
						dataAccess.setAutoCommit(true);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				if (!outerConnFlag) {
					try {
						conn.isBlockingTransactionStarted = false;
						dataAccess.getConnection().close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				isExistsOpeningOperate = false;
				current.set(null);// �Ѿ��رգ������ٰ�
			}
			for (int i = 0; i < executorList.size(); i++) {
				Executor executor = (Executor) executorList.get(i);
				executor.execute();
			}
		}
		return true;
	}

	/**
	 * �ع�����,���ر�����
	 */
	public void rollback() {
		if (dataAccess != null) {
			try {
				dataAccess.rollback();
				isExistsOpeningOperate = false;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				conn.isBlockingTransactionStarted = false;
				dataAccess.getConnection().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			current.set(null);// �Ѿ��رգ������ٰ�
		}
	}

	/**
	 * ������󶨵���ǰ�߳�
	 */
	private void bindTransactionToThread() {
		Object obj = current.get();
		if (obj == null) {
			current.set(this);
		} else {
			throw new RuntimeException("ͬһ�߳���ֻ����һ������������!");
		}
	}

	/**
	 * ������������̵߳İ󶨣�����������Ƿ񼺱��رա�<br>
	 * �˷�������MainFilter���á�
	 */
	public static void clearTransactionBinding() {
		Object obj = current.get();
		if (obj == null) {
			return;
		}
		BlockingTransaction bt = (BlockingTransaction) obj;
		if (bt.isExistsOpeningOperate) {
			bt.rollback();
		}
		current.set(null);
	}

	/**
	 * ��ȡ��ǰ�߳��е��������������õ����ӡ�<br>
	 * �˷�����Ҫ��DBConnPool.getConnection()���ã�<br>
	 * �Ա�֤��������������ִ�еĲ�ѯ������ܹ������ȷ�Ľ����
	 */
	public static DBConn getCurrentThreadConnection() {
		Object obj = current.get();
		if (obj == null) {
			return null;
		}
		BlockingTransaction bt = (BlockingTransaction) obj;
		if (bt.dataAccess == null || bt.dataAccess.conn == null) {// ע�����û��dataAccess.conn�ļ�⣬�����ѭ��
			return null;
		}
		return bt.dataAccess.getConnection();
	}
}
