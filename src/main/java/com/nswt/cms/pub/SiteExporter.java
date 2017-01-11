package com.nswt.cms.pub;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

import javax.servlet.http.HttpServletResponse;

import com.nswt.cms.pub.SiteTableRela.TableRela;
import com.nswt.framework.Constant;
import com.nswt.framework.data.DBUtil;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.utility.FileUtil;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.NumberUtil;
import com.nswt.framework.utility.ZipUtil;

/**
 * ���뵼������վ��,����ģ��.<br>
 * Ĭ�Ͻ���schemaĿ¼�º���SiteID�ֶεı���
 * 
 * @Author NSWT
 * @Date 2016-3-20
 * @Mail nswt@nswt.com.cn
 */
public class SiteExporter {
	private int PageSize = 1000;

	private OutputStream os;

	private Mapx relaMap = new Mapx();

	private long siteID;

	private boolean isExportMediaFile = true;

	public SiteExporter(long siteID) {
		this.siteID = siteID;
	}

	public SiteExporter(long siteID, boolean isExportMediaFile) {
		this.siteID = siteID;
		this.isExportMediaFile = isExportMediaFile;
	}

	/**
	 * ���ӹ�����ĵ���SQL.
	 */
	public void addRelaTable(String sql) {
		relaMap.put(sql, "");
	}

	public boolean exportSite(String file) {
		try {
			FileUtil.delete(file);
			os = new FileOutputStream(file);
			return exportSite();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean exportSite(HttpServletResponse response) {
		try {
			os = response.getOutputStream();
			return exportSite();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean exportSite() {
		try {
			os.write(Constant.GlobalCharset.equals("GBK") ? 1 : 2);
			byte[] bs = NumberUtil.toBytes(siteID);
			os.write(bs);
		} catch (IOException e1) {
			e1.printStackTrace();
			return false;
		}
		boolean flag = true;

		TableRela[] trs = SiteTableRela.getRelas();
		String[] tables = SiteTableRela.getSiteIDTables();
		// ����˳��ZCSite->��SiteID�ı�->����������ʱ�����������˳��
		QueryBuilder qb = new QueryBuilder("select * from ZCSite where ID=?", siteID);
		try {
			transferSQL(qb, "ZCSite");
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		if (!flag) {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		for (int i = 0; i < tables.length; i++) {
			if (tables[i].toLowerCase().startsWith("b")) {
				continue;
			}
			if (tables[i].toLowerCase().startsWith("zcdeploy")) {
				continue;
			}
			if (tables[i].toLowerCase().startsWith("zcstatitem")) {
				continue;
			}
			if (tables[i].toLowerCase().startsWith("zcvisitlog")) {
				continue;
			}
			qb = SiteTableRela.getSelectQuery(siteID, tables[i]);
			if (tables[i].toLowerCase().equals("zccatalog")) {
				qb.append(" order by innercode");
			}
			if (tables[i].equalsIgnoreCase("ZCArticle")) {
				PageSize = 100;
			} else {
				PageSize = 1000;
			}
			try {
				transferSQL(qb, tables[i]);
			} catch (Exception e) {
				flag = false;
				e.printStackTrace();
			}
		}
		for (int i = 0; i < trs.length; i++) {
			if (trs[i].TableCode.toLowerCase().startsWith("b")) {
				continue;
			}
			if (!trs[i].isExportData) {
				continue;
			}
			if (!SiteTableRela.hasSiteIDField(trs[i].RelaTable)) {// ��Щ������û��SiteID���й�����ϵ����ZDColumn
				continue;
			}
			qb = SiteTableRela.getSelectQuery(siteID, trs[i]);
			try {
				transferSQL(qb, trs[i]);
			} catch (Exception e) {
				flag = false;
				e.printStackTrace();
			}
		}
		// �����Զ����
		DataTable dt = new QueryBuilder("select Code from ZCCustomTable where Type='Custom' and SiteID=?", siteID)
				.executeDataTable();
		for (int i = 0; i < dt.getRowCount(); i++) {
			String code = dt.getString(i, 0);
			transferCustomTable(code);
		}
		if (!flag) {
			return false;
		}
		if (this.isExportMediaFile) {
			flag = exportFile();
		}
		return flag;
		// ����ģ���ļ�,����
	}

	private void transferCustomTable(String table) {
		int count = 0;
		try {
			QueryBuilder qb = new QueryBuilder("select * from " + table);
			count = DBUtil.getCount(qb);
			for (int i = 0; i * PageSize < count || (i == 0 && count == 0); i++) {
				DataTable dt = qb.executePagedDataTable(PageSize, i);
				// ��д������
				String name = "CustomTable:" + table;
				byte[] bs = FileUtil.serialize(name);
				os.write(NumberUtil.toBytes(bs.length));
				os.write(bs);

				bs = FileUtil.serialize(dt);
				bs = ZipUtil.zip(bs);
				os.write(NumberUtil.toBytes(bs.length));
				os.write(bs);
			}
		} catch (Exception e) {// û��Schema��Ӧ�ı�
			LogUtil.warn("��Ӧ���Զ�������ڣ�" + table);
			return;
		}
	}

	private boolean exportFile() {
		String path = SiteUtil.getAbsolutePath(siteID);
		try {
			exportDir(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private void exportDir(File parent) throws IOException {
		String root = SiteUtil.getAbsolutePath(siteID);
		root = root.replaceAll("[\\\\/]+", "/");
		File[] fs = parent.listFiles();
		//Ŀ¼���ܲ�����
		if(fs != null){
			for (int i = 0; i < fs.length; i++) {
				File f = fs[i];
				if (f.getName().equals(".svn")) {
					continue;
				}
				if (f.getName().equals(".shtml")) {
					continue;
				}
				if (f.isDirectory()) {
					exportDir(f);
				} else {
					byte[] bs = FileUtil.readByte(f);
					if (bs != null) {
						bs = ZipUtil.zip(bs);
						String path = f.getAbsolutePath();
						path = path.replaceAll("[\\\\/]+", "/");
						path = path.substring(root.length());
						path = "File:" + path;// ��ǰ׺�������ڱ���
						byte[] nbs = FileUtil.serialize(path);
						os.write(NumberUtil.toBytes(nbs.length));
						os.write(nbs);
						os.write(NumberUtil.toBytes(bs.length));
						os.write(bs);
						os.flush();
					}
				}
			}
		}
	}

	private void transferSQL(QueryBuilder qb, Serializable obj) throws Exception {
		int count = DBUtil.getCount(qb);
		for (int i = 0; i * PageSize < count || (i == 0 && count == 0); i++) {
			DataTable dt = qb.executePagedDataTable(PageSize, i);
			// д���ϵ����
			byte[] bs = FileUtil.serialize(obj);
			os.write(NumberUtil.toBytes(bs.length));
			os.write(bs);

			// д��DataTable
			bs = FileUtil.serialize(dt);
			bs = ZipUtil.zip(bs);
			os.write(NumberUtil.toBytes(bs.length));
			os.write(bs);
			os.flush();
		}
	}

	public static void main(String[] args) {
		SiteExporter se = new SiteExporter(206);
		se.exportSite("G:/nswt.dat");
	}
}
