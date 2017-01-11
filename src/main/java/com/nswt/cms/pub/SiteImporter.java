package com.nswt.cms.pub;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Date;

import com.nswt.cms.pub.SiteTableRela.NoType;
import com.nswt.cms.pub.SiteTableRela.TableRela;
import com.nswt.cms.resource.ConfigImageLib;
import com.nswt.cms.site.Site;
import com.nswt.framework.Config;
import com.nswt.framework.Constant;
import com.nswt.framework.User;
import com.nswt.framework.User.UserData;
import com.nswt.framework.data.BlockingTransaction;
import com.nswt.framework.data.DataAccess;
import com.nswt.framework.data.DataRow;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
//import com.nswt.framework.license.LicenseInfo;
import com.nswt.framework.messages.LongTimeTask;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaColumn;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.orm.SchemaUtil;
import com.nswt.framework.orm.TableCreator;
import com.nswt.framework.utility.CharsetConvert;
import com.nswt.framework.utility.FileUtil;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.NumberUtil;
import com.nswt.framework.utility.StringUtil;
import com.nswt.framework.utility.ZipUtil;
import com.nswt.platform.pub.NoUtil;
import com.nswt.platform.pub.OrderUtil;
import com.nswt.schema.ZCSiteSchema;

/**
 * @Author NSWT
 * @Date 2016-3-22
 * @Mail nswt@nswt.com.cn
 */
public class SiteImporter {
	private long siteID;

	private long newSiteID;

	private DataAccess da;

	private String ExportCharset;

	private String file;

	private LongTimeTask task;

	private Mapx map = new Mapx();// վ����ص���Ϣ,��ǰ̨����

	private Mapx customTableMap = new Mapx();// �洢�Ѿ���������Զ������

	private boolean isNewSite;

	private NoType[] NoRelas;

	private TableRela[] TableRelas;

	public SiteImporter(String file) {
		this.file = file;
		this.task = LongTimeTask.createEmptyInstance();
	}

	public SiteImporter(String file, LongTimeTask task) {
		this.file = file;
		this.task = task;
	}

	public boolean importSite() {
		return importSite(null);
	}

	public boolean importSite(String poolName) {
		UserData user = new UserData();
		user.setUserName("admin");
		user.setBranchInnerCode("86");
		user.setLogin(true);
		user.setManager(true);
		User.setCurrent(user);

		isNewSite = "0".equals(map.getString("ID")) || StringUtil.isEmpty(map.getString("ID"));
		NoRelas = SiteTableRela.getNoRelaArray();
		TableRelas = SiteTableRela.getRelas();
		da = new DataAccess();
		FileInputStream fin = null;
		try {
			fin = new FileInputStream(file);
			da.setAutoCommit(false);
			ExportCharset = fin.read() == 1 ? "GBK" : "UTF-8";
			byte[] bs = new byte[8];
			fin.read(bs);
			siteID = NumberUtil.toLong(bs);
			boolean flag = true;
			int i = 0;
			while (1 == 1) {
				// ��ȡ����
				bs = new byte[4];
				if (!bufferRead(bs, fin)) {
					break;
				}
				int len = NumberUtil.toInt(bs);
				bs = new byte[len];
				if (!bufferRead(bs, fin)) {
					flag = false;
					break;
				}
				Object obj = FileUtil.unserialize(bs);

				// ��ȡDataTable
				bs = new byte[4];
				if (!bufferRead(bs, fin)) {
					flag = false;
					break;
				}
				len = NumberUtil.toInt(bs);
				bs = new byte[len];
				if (!bufferRead(bs, fin)) {
					flag = false;
					break;
				}
				bs = ZipUtil.unzip(bs);
				task.setPercent(i * 100 / 200);
				dealOneEntry(bs, obj);
				i++;
			}
			if (flag) {
				da.commit();
			} else {
				LogUtil.error("��ȡվ�㵼���ļ�ʱ��������!");
				da.rollback();
			}
			da.setAutoCommit(true);
			Site.updatePrivAndFile(map.getString("Alias"));
			return flag;
		} catch (Exception e1) {
			e1.printStackTrace();
			try {
				da.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return false;
		} finally {
			try {
				da.setAutoCommit(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				da.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				fin.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * �����ж�ȡ���ݵ��ַ�������
	 */
	private static boolean bufferRead(byte[] bs, FileInputStream fin) {
		try {
			if (fin.read(bs) == -1) {
				return false;
			}
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	private QueryBuilder insertQB = null;

	/**
	 * ����һ������������
	 */
	public void dealOneEntry(byte[] bs, Object obj) throws Exception {
		if (bs == null || obj == null) {
			return;
		}
		if (obj instanceof String) {
			String name = obj.toString();
			if (name.startsWith("File:")) {// ģ���ļ�
				dealFile(name, bs);
			} else if (name.startsWith("CustomTable:")) {// �Զ����
				name = name.substring("CustomTable:".length());
				task.setCurrentInfo("���ڵ����Զ����:" + name);
				DataTable dt = (DataTable) FileUtil.unserialize(bs);
				try {
					if (!customTableMap.containsKey(name)) {
						QueryBuilder qb = new QueryBuilder(
								"select * from ZCCustomTableColumn where exists "
										+ "(select ID from ZCCustomTable where SiteID=? and Code=? and Type='Custom' and ZCCustomTableColumn.TableID=ID)",
								newSiteID, name);
						DataTable cdt = da.executeDataTable(qb);
						SchemaColumn[] scs = new SchemaColumn[cdt.getRowCount()];
						for (int j = 0; j < scs.length; j++) {
							DataRow cdr = cdt.getDataRow(j);
							int type = Integer.parseInt(cdr.getString("DataType"));
							SchemaColumn sc = new SchemaColumn(cdr.getString("Code"), type, j, (int) cdr
									.getInt("Length"), 0, "Y".equals(cdr.getString("isMandatory")), "Y".equals(cdr
									.getString("isPrimaryKey")));
							scs[j] = sc;
						}
						TableCreator tc = new TableCreator(da.getConnection().getDBConfig().DBType);
						tc.createTable(scs, name, true);
						tc.executeAndClear(da.getConnection());
						customTableMap.put(name, "");
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
					dealCustomTable(dt, insertQB);
				} catch (Exception e) {
					LogUtil.warn("δ�ɹ������" + name);
					e.printStackTrace();
				}
			} else {
				dealSiteIDTable(name, (DataTable) FileUtil.unserialize(bs));
			}
		} else {
			dealRelaTable((TableRela) obj, (DataTable) FileUtil.unserialize(bs));
		}
	}

	/**
	 * �����Զ����
	 */
	private void dealCustomTable(DataTable dt, QueryBuilder qb) throws Exception {
		try {
			qb.clearBatches();
			qb.getParams().clear();
			for (int i = 0; i < dt.getRowCount(); i++) {
				for (int j = 0; j < dt.getColCount() - 1; j++) {// ���һ�����к�
					String v = dt.getString(i, j);
					if (StringUtil.isEmpty(v)) {
						v = null;
					}
					qb.add(v);
				}
				qb.addBatch();
			}
			da.executeNoQuery(qb);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	/**
	 * ����ģ���ļ�
	 */
	public void dealFile(String fileName, byte[] bs) {
		try {
			bs = convertCharset(bs);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		fileName = fileName.substring("File:".length());
		task.setCurrentInfo("���ڵ���վ���ļ�:" + fileName);
		String root = Config.getContextRealPath() + Config.getValue("Statical.TargetDir") + "/"
				+ map.getString("Alias");
		root = root + "/";
		fileName = root + fileName;
		fileName = fileName.replaceAll("[\\\\/]+", "/");
		String dirName = fileName.substring(0, fileName.lastIndexOf('/'));
		File dir = new File(dirName);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		FileUtil.writeByte(fileName, bs);
	}

	/**
	 * ����ZCSite��¼
	 */
	public void dealZCSite(DataRow dr) {
		if (isNewSite) {
//			if (LicenseInfo.getName().equals("TrailUser")
//					&& new QueryBuilder("select count(*) from ZCSite").executeInt() >= 1) {
//				throw new RuntimeException("վ�����������ƣ�����ϵ�����������License��");
//			}
			newSiteID = NoUtil.getMaxID("SiteID");
			ZCSiteSchema site = new ZCSiteSchema();
			site.setValue(dr);
			site.setID(newSiteID);
			site.setName(map.getString("Name"));
			site.setAlias(map.getString("Alias"));
			site.setURL(map.getString("URL"));
			site.setHitCount(0);
			site.setChannelCount(0);
			site.setSpecialCount(0);
			site.setMagzineCount(0);
			site.setArticleCount(0);
			site.setImageLibCount(1);
			site.setVideoLibCount(1);
			site.setAudioLibCount(1);
			site.setAttachmentLibCount(1);
			site.setBranchInnerCode(User.getBranchInnerCode());
			site.setAddTime(new Date());
			site.setAddUser(User.getUserName());
			site.setConfigXML(ConfigImageLib.imageLibConfigDefault);
			site.setOrderFlag(OrderUtil.getDefaultOrder());//�����µ�����ֵ
			da.insert(site);
			Transaction trans = new Transaction();
			Site.addDefaultPriv(newSiteID, trans);
			trans.setDataAccess(da);
			trans.commit(false);
			addIDMapping("ZCSite", String.valueOf(siteID), String.valueOf(newSiteID));
		} else {
			ZCSiteSchema site = new ZCSiteSchema();
			site.setID(map.getString("ID"));
			newSiteID = site.getID();
			site.fill();

			task.setCurrentInfo("���ڱ������ݣ�������Ҫ�ϳ�ʱ�䣬�����ĵȴ�");
			// �ȱ�������
			SiteExporter se = new SiteExporter(site.getID());
			se.exportSite(Config.getContextRealPath() + "WEB-INF/data/backup/" + site.getAlias() + "_"
					+ System.currentTimeMillis() + ".dat");

			// ����ɾ����վ������
			addIDMapping("ZCSite", dr.getString("ID"), String.valueOf(site.getID()));// ����IDӳ��
			da.deleteAndBackup(site);// ɾ����վ��
			BlockingTransaction tran = new BlockingTransaction(da);
			Site.delSiteRela(site.getID() + "", tran);

			// ɾ���ļ�
			String sitePath = Config.getContextRealPath() + Config.getValue("Statical.TemplateDir") + "/"
					+ site.getAlias();
			FileUtil.delete(sitePath);

			site = new ZCSiteSchema();
			site.setValue(dr);

			da.insert(site);// ������վ��
		}
	}

	/**
	 * ������SiteID�ֶεı�
	 */
	public void dealSiteIDTable(String tableName, DataTable dt) throws Exception {
		task.setCurrentInfo("�������" + tableName + "����վ������");
		if (tableName.equalsIgnoreCase("ZCSite")) {
			dealZCSite(dt.getDataRow(0));// ZCSite���ر���
		} else {
			SchemaSet set = (SchemaSet) Class.forName("com.nswt.schema." + tableName + "Set").newInstance();
			if (!tableName.equalsIgnoreCase("ZDMember")) {// ZDMember��UserNameΪΨһ���������ر���
				for (int i = 0; i < dt.getRowCount(); i++) {
					Schema schema = (Schema) Class.forName("com.nswt.schema." + tableName + "Schema").newInstance();
					DataRow dr = dt.getDataRow(i);
					setSiteIDTableMaxNo(tableName, dr, true);
					schema.setValue(dt.getDataRow(i));
					set.add(schema);
				}
				// if (dt.getRowCount() == 0) {// û�м�¼ҲҪ����ӳ��
				// setSiteIDTableMaxNo(tableName, new
				// DataRow(dt.getDataColumns(), null), false);
				// }
				da.insert(set);
			} else {
				Schema schema = (Schema) Class.forName("com.nswt.schema." + tableName + "Schema").newInstance();
				SchemaColumn[] scs = SchemaUtil.getColumns(schema);
				QueryBuilder qb = new QueryBuilder("select count(1) from " + tableName + " where 1=1 ");
				for (int i = 0; i < scs.length; i++) {
					if (scs[i].isPrimaryKey()) {
						qb.append(" and " + scs[i].getColumnName() + "=?");
					}
				}
				for (int i = 0; i < dt.getRowCount(); i++) {
					schema = (Schema) Class.forName("com.nswt.schema." + tableName + "Schema").newInstance();
					DataRow dr = dt.getDataRow(i);
					// Ҫ�Ȳ�ѯ��û�м�¼
					qb.getParams().clear();
					for (int j = 0; j < scs.length; j++) {
						if (scs[j].isPrimaryKey()) {
							qb.add(dr.get(scs[j].getColumnName()));
						}
					}
					int count = qb.executeInt();
					if (count > 0) {
						setSiteIDTableMaxNo(tableName, dr, false);
					} else {
						setSiteIDTableMaxNo(tableName, dr, true);
					}
					schema.setValue(dt.getDataRow(i));
					set.add(schema);
				}
				// if (dt.getRowCount() == 0) {// û�м�¼ҲҪ����ӳ��
				// setSiteIDTableMaxNo(tableName, new
				// DataRow(dt.getDataColumns(), null), false);
				// }
				da.deleteAndInsert(set);
			}
		}
	}

	/**
	 * �û�����SiteID�ı��е������ֶ�,newIDFlag=falseʱ����������
	 */
	public void setSiteIDTableMaxNo(String tableName, DataRow dr, boolean newIDFlag) {
		try {
			if (dr.getDataColumn("SiteID") != null) {
				dr.set("SiteID", this.getIDMapping("ZCSite", String.valueOf(siteID)));
			}
		} catch (Throwable t) {
			t.printStackTrace();
			LogUtil.warn(tableName);
		}
		for (int i = 0; i < NoRelas.length; i++) {
			NoType nr = NoRelas[i];
			if (nr.TableCode.equalsIgnoreCase(tableName)) {
				String id = dr.getString(nr.FieldName);
				if (newIDFlag) {
					if (nr.TableCode.equals("ZCCatalog") && nr.FieldName.equals("InnerCode")) {
						if (id.length() == 6) {// һ����Ŀ
							id = CatalogUtil.createCatalogInnerCode(null);
						} else {
							String parent = id.substring(0, id.length() - 6);
							parent = this.getIDMapping(nr.TableCode + "." + nr.FieldName, parent);// �ϼ������Ѿ����ɹ���
							id = CatalogUtil.createCatalogInnerCode(parent);
						}
					} else {
						id = String.valueOf(NoUtil.getMaxID(nr.NoType));
					}
				}
				String type = nr.TableCode;
				if (type.equals("ZCArticle") || type.equals("ZCAttachment") || type.equals("ZCImage")
						|| type.equals("ZCAudio") || type.equals("ZCVideo")) {
					if (nr.NoType.equals("DocID")) {
						type = "Document";
					}
				}
				this.addIDMapping(type + "." + nr.FieldName, dr.getString(nr.FieldName), id);
				dr.set(nr.FieldName, id);
			}
		}
		for (int i = 0; i < TableRelas.length; i++) {
			if (TableRelas[i].TableCode.equals(tableName)) {
				String type = TableRelas[i].RelaTable;
				if (type.equals("ZCArticle") || type.equals("ZCAttachment") || type.equals("ZCImage")
						|| type.equals("ZCAudio") || type.equals("ZCVideo")) {
					if (TableRelas[i].RelaField.equals("ID")) {
						type = "Document";
					}
				}
				String id = this.getIDMapping(type + "." + TableRelas[i].RelaField, dr
						.getString(TableRelas[i].KeyField));
				if (StringUtil.isNotEmpty(id)) {
					dr.set(TableRelas[i].KeyField, id);
				}
			}
		}
	}

	/**
	 * �û��������е������ֶ�
	 */
	public void setRelaTableMaxNo(TableRela tr, DataRow dr, boolean newIDFlag) {
		for (int i = 0; i < NoRelas.length; i++) {
			NoType nr = NoRelas[i];
			if (nr.TableCode.equalsIgnoreCase(tr.TableCode)) {
				String id = dr.getString(nr.FieldName);
				if (newIDFlag) {
					id = String.valueOf(NoUtil.getMaxID(nr.NoType));
				}
				this.addIDMapping(nr.TableCode + "." + nr.FieldName, dr.getString(nr.FieldName), id);
				dr.set(nr.FieldName, id);
			}
		}
		for (int i = 0; i < TableRelas.length; i++) {
			if (TableRelas[i].TableCode.equals(tr.TableCode)) {
				String type = TableRelas[i].RelaTable;
				if (type.equals("ZCArticle") || type.equals("ZCAttachment") || type.equals("ZCImage")
						|| type.equals("ZCAudio") || type.equals("ZCVideo")) {
					if (TableRelas[i].RelaField.equals("ID")) {
						type = "Document";
					}
				}
				String id = this.getIDMapping(type + "." + TableRelas[i].RelaField, dr
						.getString(TableRelas[i].KeyField));
				if (StringUtil.isNotEmpty(id)) {
					dr.set(TableRelas[i].KeyField, id);
				}
			}
		}
	}

	/**
	 * ���������
	 */
	public void dealRelaTable(TableRela tr, DataTable dt) throws Exception {
		LogUtil.info("�������" + tr.TableCode + "����վ������");
		if (!tr.isExportData) {// δ��������
			return;
		}
		SchemaSet set = (SchemaSet) Class.forName("com.nswt.schema." + tr.TableCode + "Set").newInstance();
		for (int i = 0; i < dt.getRowCount(); i++) {
			Schema schema = (Schema) Class.forName("com.nswt.schema." + tr.TableCode + "Schema").newInstance();
			DataRow dr = dt.getDataRow(i);
			setRelaTableMaxNo(tr, dr, true);
			schema.setValue(dt.getDataRow(i));
			set.add(schema);
		}
		da.insert(set);
	}

	/**
	 * ��ȡ�����ļ��е�վ���¼��Ϣ����ת��ΪMapx
	 */
	public static Mapx getSiteInfo(String file) throws Exception {
		FileInputStream fin = null;
		try {
			fin = new FileInputStream(file);
			fin.read();
			byte[] bs = new byte[8];
			fin.read(bs);

			// ��ȡ����
			bs = new byte[4];
			if (!bufferRead(bs, fin)) {
				return null;
			}
			int len = NumberUtil.toInt(bs);
			bs = new byte[len];
			if (!bufferRead(bs, fin)) {
				return null;
			}

			// ��ȡDataTable
			bs = new byte[4];
			if (!bufferRead(bs, fin)) {
				return null;
			}
			len = NumberUtil.toInt(bs);
			bs = new byte[len];
			if (!bufferRead(bs, fin)) {
				return null;
			}
			bs = ZipUtil.unzip(bs);
			DataTable dt = (DataTable) FileUtil.unserialize(bs);
			return dt.getDataRow(0).toCaseIgnoreMapx();
		} finally {
			if (fin != null) {
				try {
					fin.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void setSiteInfo(Mapx map) {
		this.map = map;
	}

	/**
	 * ת���ַ�����ʹͬһ��ģ���ļ���ͬʱ��Ӧ��ͬ�ַ�����������
	 */
	public byte[] convertCharset(byte[] bs) throws UnsupportedEncodingException {
		if (Constant.GlobalCharset.equals("GBK") && ExportCharset.equals("UTF-8")) {
			return CharsetConvert.webFileUTF8ToGBK(bs);
		}
		if (Constant.GlobalCharset.equals("UTF-8") && ExportCharset.equals("GBK")) {
			return CharsetConvert.webFileGBKToUTF8(bs);
		}
		return bs;
	}

	private Mapx idMap = new Mapx();

	/**
	 * �¾�IDӳ��
	 */
	public String getIDMapping(String type, String oldID) {
		Object obj = idMap.get(type);
		if (StringUtil.isEmpty(oldID)) {
			return null;
		}
		if (obj == null) {
			LogUtil.info("վ�㵼��ʱδ�ҵ�IDӳ���ϵ��Type=" + type + ",OldID=" + oldID);
			return null;
		}
		Mapx map = (Mapx) obj;
		return map.getString(oldID);
	}

	/**
	 * �¼�һ���¾�IDӳ��
	 */
	public void addIDMapping(String type, String oldID, String newID) {
		Object obj = idMap.get(type);
		if (obj == null) {
			obj = new Mapx();
			idMap.put(type, obj);
		}
		Mapx map = (Mapx) obj;
		map.put(oldID, newID);
	}

	public static void main(String[] args) {
		Mapx map = new Mapx();
		map.put("ID", "0");
		map.put("Name", "�������");
		map.put("Alias", "ImportTest");
		map.put("URL", "http://import.test.com");
		SiteImporter si = new SiteImporter("G:/nswt.dat");
		si.setSiteInfo(map);
		si.importSite();
	}

}
