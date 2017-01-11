package com.nswt.framework.data;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Method;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/*
 * ��������:2016-7-2
 * ����: NSWT
 * ����:nswt@nswt.com.cn
 */
public class LobUtil {

	public static String clobToString(Clob clob) {
		if (clob == null) {
			return null;
		}
		try {
			Reader r = clob.getCharacterStream();
			StringWriter sw = new StringWriter();
			char[] cs = new char[(int) clob.length()];
			try {
				r.read(cs);
				sw.write(cs);
				return sw.toString();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] blobToBytes(Blob blob) {
		if (blob == null) {
			return null;
		}
		try {
			return blob.getBytes(1L, (int) blob.length());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void setClob(DBConn conn, PreparedStatement ps, int i, Object v) throws SQLException {
		if (conn.getDBConfig().isOracle()) {
			Class clobClass = null;
			try {
				clobClass = Class.forName("oracle.sql.CLOB");
			} catch (ClassNotFoundException e2) {
				e2.printStackTrace();
				return;
			}
			Object clob = null;
			Object oc = conn.getPhysicalConnection();
			try {
				Method m = clobClass.getMethod("createTemporary", new Class[] { java.sql.Connection.class,
						boolean.class, int.class });
				clob = m.invoke(null, new Object[] { oc, new Boolean(true), new Integer(1) });
				// �൱��clob=CLOB.createTemporary(oc,true,1);
				// Oracle9i����1,10G�б����10�������ǻὫ1�Զ�תΪ10

				m = clobClass.getMethod("open", new Class[] { int.class });
				m.invoke(clob, new Object[] { new Integer(1) });// �൱��clob.open(1);

				m = clobClass.getMethod("setCharacterStream", new Class[] { long.class });
				Writer writer = (Writer) m.invoke(clob, new Object[] { new Long(0) });
				// �൱��Writer writer = clob.setCharacterStream(0L);
				writer.write(String.valueOf(v));
				writer.close();

				clobClass.getMethod("close", (Class[]) null).invoke(clob, null);// �൱��clob.close();
				ps.setClob(i, (Clob) clob);
			} catch (Exception e) {
				try {
					if (clob != null) {
						Method m = clobClass.getMethod("freeTemporary", new Class[] { clobClass });
						m.invoke(null, new Object[] { clob });// �൱��CLOB.freeTemporary(clob);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
		} else {
			ps.setObject(i, v);
		}
	}

	public static void setBlob(DBConn conn, PreparedStatement ps, int i, byte[] v) throws SQLException {
		if (conn.getDBConfig().isOracle()) {
			Class blobClass = null;
			try {
				blobClass = Class.forName("oracle.sql.BLOB");
			} catch (ClassNotFoundException e2) {
				e2.printStackTrace();
				return;
			}
			Object blob = null;
			Object oc = conn.getPhysicalConnection();
			try {
				Method m = blobClass.getMethod("createTemporary", new Class[] { java.sql.Connection.class,
						boolean.class, int.class });
				blob = m.invoke(null, new Object[] { oc, new Boolean(true), new Integer(1) });
				// �൱��blob=BLOB.createTemporary(oc,true,1);
				// Oracle9i����1,10G�б����10�������ǻὫ1�Զ�תΪ10

				m = blobClass.getMethod("open", new Class[] { int.class });
				m.invoke(blob, new Object[] { new Integer(1) });// �൱��blob.open(1);

				m = blobClass.getMethod("getBinaryOutputStream", new Class[] { long.class });
				OutputStream out = (OutputStream) m.invoke(blob, new Object[] { new Long(0) });
				out.write(v);
				out.close();

				blobClass.getMethod("close", (Class[]) null).invoke(blob, null);// �൱��blob.close();
				ps.setBlob(i, (Blob) blob);
			} catch (Exception e) {
				try {
					if (blob != null) {
						Method m = blobClass.getMethod("freeTemporary", new Class[] { blobClass });
						m.invoke(null, new Object[] { blob });// �൱��BLOB.freeTemporary(clob);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
		} else {
			ps.setObject(i, v);
		}
	}
}
