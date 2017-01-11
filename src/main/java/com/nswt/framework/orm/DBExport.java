package com.nswt.framework.orm;

import java.io.IOException;

import com.nswt.framework.data.DBUtil;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.messages.LongTimeTask;
import com.nswt.framework.utility.BufferedRandomAccessFile;
import com.nswt.framework.utility.FileUtil;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.NumberUtil;
import com.nswt.framework.utility.ZipUtil;

/**
 * @Author ������
 * @Date 2008-10-13
 * @Mail nswt@nswt.com.cn
 */
public class DBExport {
	private final static int PageSize = 500;

	private BufferedRandomAccessFile braf;

	private LongTimeTask task;

	public void setTask(LongTimeTask task) {
		this.task = task;
	}

	public void exportDB(String file) {
		FileUtil.delete(file);
		try {
			braf = new BufferedRandomAccessFile(file, "rw");
			String[] arr = SchemaUtil.getAllSchemaClassName();
			for (int i = 0; i < arr.length; i++) {
				try {
					if (task != null) {
						task.setPercent(new Double(i * 100.0 / arr.length).intValue());
						task.setCurrentInfo("���ڵ�����" + arr[i]);
					}
					transferOneTable(arr[i]);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// �����Զ����
			try {
				DataTable dt = new QueryBuilder("select Code,ID from ZCCustomTable where Type='Custom'")
						.executeDataTable();
				for (int i = 0; i < dt.getRowCount(); i++) {
					transferCustomTable(dt.getString(i, "Code"), dt.getString(i, "ID"));
				}
			} catch (Throwable t) {
				LogUtil.warn("ϵͳ��û���Զ����");
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
	}

	private void transferOneTable(String schemaName) throws Exception {
		Schema schema = (Schema) Class.forName(schemaName).newInstance();
		int count = 0;
		try {
			count = new QueryBuilder("select count(*) from " + schema.TableCode).executeInt();
			for (int i = 0; i * PageSize < count || (i == 0 && count == 0); i++) {
				SchemaSet set = schema.querySet(null, PageSize, i);
				// ��д������
				byte[] bs = schemaName.getBytes();
				braf.write(NumberUtil.toBytes(bs.length));
				braf.write(bs);

				bs = FileUtil.serialize(set);
				bs = ZipUtil.zip(bs);
				braf.write(NumberUtil.toBytes(bs.length));
				braf.write(bs);
			}
		} catch (Exception e) {// û��Schema��Ӧ�ı�
			LogUtil.warn("Schema��Ӧ�ı����ڣ�" + schemaName);
			return;
		}
	}

	private void transferCustomTable(String table, String ID) throws Exception {
		int count = 0;
		try {
			String columnName = new QueryBuilder("select Code from ZCCustomTableColumn where TableID=?", ID)
					.executeString();
			QueryBuilder qb = new QueryBuilder("select * from " + table + " order by " + columnName);
			count = DBUtil.getCount(qb);
			for (int i = 0; i * PageSize < count || (i == 0 && count == 0); i++) {
				DataTable dt = qb.executePagedDataTable(PageSize, i);
				// ��д������
				byte[] bs = table.getBytes();
				braf.write(NumberUtil.toBytes(bs.length));
				braf.write(bs);

				bs = FileUtil.serialize(dt);
				bs = ZipUtil.zip(bs);
				braf.write(NumberUtil.toBytes(bs.length));
				braf.write(bs);
			}
		} catch (Exception e) {// û�ж�Ӧ�ı�
			LogUtil.warn("��Ӧ���Զ��������" + table + ":" + e.getMessage());
			return;
		}
	}
}
