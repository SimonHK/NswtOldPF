package com.nswt.framework.data;

/**
 * @Author NSWT
 * @Date 2007-4-20
 * @Mail nswt@nswt.com.cn
 */
public class DBConnConfig {
	public static final String ORACLE = "ORACLE";

	public static final String DB2 = "DB2";

	public static final String MYSQL = "MYSQL";

	public static final String MSSQL = "MSSQL";

	public static final String MSSQL2000 = "MSSQL2000";
	
	public static final String SYBASE = "SYBASE";

	public String JNDIName = null;

	public boolean isJNDIPool = false;

	public int MaxConnCount = 1000;

	public int InitConnCount = 0;

	public int ConnCount;

	public int MaxConnUsingTime = 300000;// �Ժ���Ϊ��λ

	public int RefershPeriod = 60000;// һ���Ӽ��һ�������Ƿ�ʧЧ�����ݿ�������ԭ����ɣ�

	public String DBType;

	public String DBServerAddress;

	public int DBPort;

	public String DBName;

	public String DBUserName;

	public String DBPassword;

	public String TestTable;

	public String PoolName;

	public boolean isLatin1Charset;// �Ƿ���latin1�ַ����������Oracle���Ǵ��ַ�������SQL�����ؽ������ת��

	public boolean isOracle() {
		return DBType.equalsIgnoreCase(ORACLE);
	}

	public boolean isDB2() {
		return DBType.equalsIgnoreCase(DB2);
	}

	public boolean isMysql() {
		return DBType.equalsIgnoreCase(MYSQL);
	}

	public boolean isSQLServer() {
		return DBType.equalsIgnoreCase(MSSQL);
	}

	public boolean isSQLServer2000() {
		return DBType.equalsIgnoreCase(MSSQL2000);
	}
	
	public boolean isSybase() {
		return DBType.equalsIgnoreCase(SYBASE);
	}
}
