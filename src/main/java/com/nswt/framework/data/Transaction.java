package com.nswt.framework.data;

import java.sql.SQLException;
import java.util.ArrayList;

import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.utility.Executor;

/**
 * �������������࣬ʹ�ñ��ദ�����񲻻�һ��ʼ��ռ�����ӣ�ֻ�������commit()ʱ<br>
 * �Ż�����ռ�����ݿ����ӣ��ڴ�֮ǰֻ�ǽ������������ڴ�֮�У��Ӷ���Լռ�����ݿ����ӵ�ʱ�䡣<br>
 * 1��һ�������Ҫ��ʹ�ñ��ദ�����񣬱������κ�����¶�����Ҫ�ֹ��������ӣ��������ܽ��š�<br>
 * 2�����໺�������ݿ������������ݿ�����漰���������ݵĲ���(����һ�β���100W������)��<br>
 * ����ܻᵼ���ڴ�����������������ʹ������������<br>
 * 3���ڱ��������������в�ѯ���ݿ⣬��ѯ����ֵ��Ȼ������δ��ʼ֮ǰ��ֵ(��Ϊ��δcommit()<br>
 * ֮ǰ��ʵ����δ�����ݿ��ύ�κβ���)�� <br>
 * 4�����������Ĺ�������Ҫ�����ݿ��ѯֵ������Ҫ���ѯ����ֵ�Ǳ��������ύ�Ĳ����Ľ����<br>
 * ����Ҫʹ�������������� <br>
 * 5����Ҫʹ��JDBCԭ����������ʹ��DataAccess�࣬һ������²��Ƽ�ʹ�á�<br>
 * 
 * @see com.nswt.framework.data.BlockingTransaction ����: NSWT<br>
 *      ���ڣ�2016-7-12<br>
 *      �ʼ���nswt@nswt.com.cn<br>
 */
public class Transaction {
	/**
	 * ��������
	 */
	public static final int INSERT = 1;

	/**
	 * ��������
	 */
	public static final int UPDATE = 2;

	/**
	 * ɾ������
	 */
	public static final int DELETE = 3;

	/**
	 * ��B��������
	 */
	public static final int BACKUP = 4;

	/**
	 * ɾ�����ұ�������
	 */
	public static final int DELETE_AND_BACKUP = 5;

	/**
	 * ��ɾ���ٲ�������
	 */
	public static final int DELETE_AND_INSERT = 6;

	/**
	 * SQL����
	 */
	public static final int SQL = 7;

	protected boolean outerConnFlag = false;// 1Ϊ���Ӵ��ⲿ���룬0Ϊδ��������

	protected DataAccess dataAccess;

	protected ArrayList list = new ArrayList();

	protected String backupOperator;

	protected String backupMemo;

	protected String exceptionMessage;// �쳣��Ϣ

	protected ArrayList executorList = new ArrayList(4);

	protected String poolName;

	public Transaction() {
	}

	public Transaction(String poolName) {
		this.poolName = poolName;
	}

	/**
	 * ���õ�ǰ����ʹ�õ�DataAccess����
	 */
	public void setDataAccess(DataAccess dAccess) {
		dataAccess = dAccess;
		outerConnFlag = true;
	}

	/**
	 * ����һ��SQL����
	 */
	public void add(QueryBuilder qb) {
		list.add(new Object[] { qb, new Integer(Transaction.SQL) });
	}

	/**
	 * ����һ��Schema����
	 */
	public void add(Schema schema, int type) {
		list.add(new Object[] { schema, new Integer(type) });
	}

	/**
	 * ����һ��SchemaSet����
	 */
	public void add(SchemaSet set, int type) {
		list.add(new Object[] { set, new Integer(type) });
	}

	/**
	 * �ύ�������ݿ�
	 */
	public boolean commit() {
		return commit(true);
	}

	/**
	 * �ύ������setAutoCommitStatusΪfalse����ʹ�õ����ⲿ��DataAccess����ֻ��SQL�ύ��DataAccess��
	 */
	public boolean commit(boolean setAutoCommitStatus) {
		if (!outerConnFlag) {
			dataAccess = new DataAccess(DBConnPool.getConnection(poolName));
		}
		boolean NoErrFlag = true;
		try {
			if (!outerConnFlag || setAutoCommitStatus) {
				dataAccess.setAutoCommit(false);
			}
			for (int i = 0; i < list.size(); i++) {
				Object[] arr = (Object[]) list.get(i);
				Object obj = arr[0];
				int type = ((Integer) arr[1]).intValue();
				if (!executeObject(obj, type)) {
					NoErrFlag = false;
					return false;
				}
			}
			dataAccess.commit();
			list.clear();
		} catch (Exception e) {
			e.printStackTrace();
			this.exceptionMessage = e.getMessage();
			NoErrFlag = false;
			return false;
		} finally {
			if (!NoErrFlag) {
				try {
					dataAccess.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			try {
				if (!outerConnFlag || setAutoCommitStatus) {
					dataAccess.setAutoCommit(true);
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			if (!outerConnFlag) {
				try {
					dataAccess.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		for (int i = 0; i < executorList.size(); i++) {
			Executor executor = (Executor) executorList.get(i);
			executor.execute();
		}
		return true;
	}

	protected boolean executeObject(Object obj, int type) throws SQLException {
		if (obj instanceof QueryBuilder) {
			dataAccess.executeNoQuery((QueryBuilder) obj);
		} else if (obj instanceof Schema) {
			Schema s = (Schema) obj;
			s.setDataAccess(dataAccess);
			if (type == Transaction.INSERT) {
				if (!s.insert()) {
					return false;
				}
			} else if (type == Transaction.UPDATE) {
				if (!s.update()) {
					return false;
				}
			} else if (type == Transaction.DELETE) {
				if (!s.delete()) {
					return false;
				}
			} else if (type == Transaction.BACKUP) {
				if (!s.backup(this.backupOperator, this.backupMemo)) {
					return false;
				}
			} else if (type == Transaction.DELETE_AND_BACKUP) {
				if (!s.deleteAndBackup(this.backupOperator, this.backupMemo)) {
					return false;
				}
			} else if (type == Transaction.DELETE_AND_INSERT) {
				if (!s.deleteAndInsert()) {
					return false;
				}
			}
		} else if (obj instanceof SchemaSet) {
			SchemaSet s = (SchemaSet) obj;
			s.setDataAccess(dataAccess);
			if (type == Transaction.INSERT) {
				if (!s.insert()) {
					return false;
				}
			} else if (type == Transaction.UPDATE) {
				if (!s.update()) {
					return false;
				}
			} else if (type == Transaction.DELETE) {
				if (!s.delete()) {
					return false;
				}
			} else if (type == Transaction.BACKUP) {
				if (!s.backup(this.backupOperator, this.backupMemo)) {
					return false;
				}
			} else if (type == Transaction.DELETE_AND_BACKUP) {
				if (!s.deleteAndBackup(this.backupOperator, this.backupMemo)) {
					return false;
				}
			} else if (type == Transaction.DELETE_AND_INSERT) {
				if (!s.deleteAndInsert()) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * ������еĲ���
	 */
	public void clear() {
		this.list.clear();
	}

	/**
	 * ��ȡִ�й����е�SQL�쳣��Ϣ
	 */
	public String getExceptionMessage() {
		return exceptionMessage;
	}

	/**
	 * ��ȡ��������ͳһ�ı��ݱ�ע��Ϣ
	 */
	public String getBackupMemo() {
		return backupMemo;
	}

	/**
	 * ���ñ�������ͳһ�ı��ݱ�ע��Ϣ
	 */
	public void setBackupMemo(String backupMemo) {
		this.backupMemo = backupMemo;
	}

	/**
	 * ��ȡ��������ͳһ�ı�������Ϣ
	 */
	public String getBackupOperator() {
		return backupOperator;
	}

	/**
	 * ���ñ�������ͳһ�ı�������Ϣ
	 */
	public void setBackupOperator(String backupOperator) {
		this.backupOperator = backupOperator;
	}

	/**
	 * ���ذ������в�����Map
	 */
	public ArrayList getOperateList() {
		return list;
	}

	/**
	 * ����һ��ִ������ִ�����е��߼�����commit()֮��ִ��
	 */
	public void addExecutor(Executor executor) {
		executorList.add(executor);
	}
}
