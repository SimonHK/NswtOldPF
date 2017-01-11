package com.nswt.platform.pub;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tools.ant.filters.StringInputStream;

import com.nswt.cms.api.SearchAPI;
import com.nswt.cms.template.PageGenerator;
import com.nswt.framework.Ajax;
import com.nswt.framework.Config;
import com.nswt.framework.Constant;
import com.nswt.framework.RequestImpl;
import com.nswt.framework.User;
import com.nswt.framework.User.UserData;
import com.nswt.framework.data.DBConn;
import com.nswt.framework.data.DBConnConfig;
import com.nswt.framework.data.DBConnPool;
import com.nswt.framework.data.DBConnPoolImpl;
import com.nswt.framework.data.DataAccess;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.messages.LongTimeTask;
import com.nswt.framework.orm.DBImport;
import com.nswt.framework.schedule.CronManager;
import com.nswt.framework.security.EncryptUtil;
import com.nswt.framework.utility.FileUtil;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.StringUtil;
import com.nswt.framework.utility.ZipUtil;
import com.nswt.platform.Application;
import com.nswt.platform.UserList;
import com.nswt.schema.ZCFullTextSchema;
import com.nswt.schema.ZCSiteSchema;

/*
 * ��ʼ�����ݿ�
 * 
 * ��������:2016-7-7
 * ����: NSWT
 * ����:nswt@nswt.com.cn
 */
public class Install extends Ajax {
	public void execute() {
		if (Config.isInstalled) {
			Response.setError("�Ѿ�Ϊ" + Config.getAppCode() + "��ʼ���ݿ���ϣ������ٴγ�ʼ��!");
			return;
		}

		final DBConnConfig dcc = new DBConnConfig();
		dcc.isJNDIPool = "1".equals($V("isJNDIPool"));
		dcc.isLatin1Charset = "1".equals($V("isLatin1Charset"));
		dcc.JNDIName = $V("JNDIName");
		dcc.DBName = $V("DBName");
		dcc.DBPassword = $V("Password");
		try {
			dcc.DBPort = Integer.parseInt($V("Port"));
		} catch (NumberFormatException e1) {
			// e1.printStackTrace();
		}
		dcc.DBServerAddress = $V("Address");
		dcc.DBType = $V("ServerType");
		dcc.DBUserName = $V("UserName");

		if (Config.isJboss()) {// JBoss��Ҫȥ��ǰ�ߵ�jdbc
			if (dcc.JNDIName.toLowerCase().startsWith("jdbc/")) {
				dcc.JNDIName = dcc.JNDIName.substring(5);
			}
		}

		DBConn conn = null;
		try {
			if (dcc.isMysql()) {
				try {
					conn = DBConnPoolImpl.createConnection(dcc, false);
				} catch (SQLException e) {// �п�������Ϊ���ݿⲻ����
					e.printStackTrace();
					if (conn != null) {
						conn.closeReally();
					}
					dcc.DBName = "mysql";
					try {
						conn = DBConnPoolImpl.createConnection(dcc, false);
						// �����Mysql,���������Сд
						DataAccess da = new DataAccess(conn);
						DataTable dt = da.executeDataTable(new QueryBuilder(
								"show variables like 'lower_case_table_names'"));
						if (dt.getRowCount() == 0 || dt.getInt(0, 1) == 0) {
							Response.setError("��鵽mysql���ݿ����ֱ�����Сд�����޸�my.cnf��my.ini:<br><font color=red>"
									+ "��[mysqld]�μ���һ������lower_case_table_names=1!</font>");
							conn.closeReally();
							return;
						}
						// ����ַ���
						dt = da.executeDataTable(new QueryBuilder("show variables like 'character_set_database'"));
						String charset = Constant.GlobalCharset.replaceAll("\\-", "");
						if (!charset.equalsIgnoreCase(dt.getString(0, 1))) {
							Response.setError("��鵽mysql���ַ���Ϊ" + dt.getString(0, 1) + "��������Ҫ����ַ���Ϊ"
									+ charset.toLowerCase() + "�����޸�my.cnf��my.ini:<br><font color=red>"
									+ "����default-character-set��ͷ���У����޸�Ϊdefault-character-set=" + charset.toLowerCase()
									+ "</font>");
							conn.closeReally();
							return;
						}
						if (!dcc.isJNDIPool) {
							// ������ݿ��Ƿ���ڣ���������ڣ����ȴ���һ��
							dt = da.executeDataTable(new QueryBuilder("show databases like ?", $V("DBName")));
							if (dt.getRowCount() == 0) {
								LogUtil.info("��װĿ�����ݿⲻ���ڣ����Զ�����Ŀ�����ݿ�!");
								da.executeNoQuery(new QueryBuilder("create schema " + $V("DBName")));
								dcc.DBName = $V("DBName");// ����Ļ�ȥ,dcc�е�ֵ��д��framework.xml
								conn.close();// ����رյ�mysql������
								conn = DBConnPoolImpl.createConnection(dcc, false);
							}
						}
					} catch (Exception e2) {// ���mysqlҲ�������ӣ����׳�ԭ�쳣
						if (conn != null) {
							conn.closeReally();
						}
						throw e;
					}
				} catch (Exception e) {// ��SQLException������쳣һ��������û�н�������
					if (conn != null) {
						try {
							conn.closeReally();
						} catch (Exception e2) {
						}
					}
					throw e;
				}
			} else if (dcc.isSQLServer() || dcc.isSybase()) {
				try {
					conn = DBConnPoolImpl.createConnection(dcc, false);
				} catch (SQLException e) {// �п�������Ϊ���ݿⲻ����
					e.printStackTrace();
					if (conn != null) {
						conn.closeReally();
					}
					if (dcc.isSQLServer() && !dcc.isJNDIPool) {
						dcc.DBName = "master";
						try {
							conn = DBConnPoolImpl.createConnection(dcc, false);
							DataAccess da = new DataAccess(conn);
							// ������ݿ��Ƿ���ڣ���������ڣ����ȴ���һ��
							DataTable dt = da.executeDataTable(new QueryBuilder(
									"select * from sysDatabases where name=?", $V("DBName")));
							if (dt.getRowCount() == 0) {
								if (Config.isSQLServer()) {
									LogUtil.info("��װĿ�����ݿⲻ���ڣ����Զ�����Ŀ�����ݿ�!");
									da.executeNoQuery(new QueryBuilder("create database " + $V("DBName")));
									dcc.DBName = $V("DBName");
									conn.closeReally();// ����رյ�master������
									conn = DBConnPoolImpl.createConnection(dcc, false);
								}
							} else {// �������ݿ⣬�ֲ������ӣ���˵��û��Ȩ��
								conn.closeReally();
								Response.setError("�û�" + dcc.DBUserName + "û�з������ݿ�" + $V("DBName") + "��Ȩ�ޣ�");
								return;
							}
						} catch (Exception e2) {// ���masterҲ�������ӣ����׳�ԭ�쳣
							throw e;
						}
					} else {
						throw e;
					}
				} catch (Exception e) {// ��SQLException������쳣һ��������û�н�������
					if (conn != null) {
						try {
							conn.closeReally();
						} catch (Exception e2) {
						}
					}
					throw e;
				}
				if (dcc.isSybase() && !dcc.isJNDIPool) {// Ҫ��ֹ���ݿⲻ����ֱ��д��master�������
					DataAccess da = new DataAccess(conn);
					try {
						da.executeNoQuery(new QueryBuilder("use master"));
						// ������ݿ��Ƿ���ڣ���������ڣ����ȴ���һ��
						DataTable dt = da.executeDataTable(new QueryBuilder("select * from sysdatabases where name=?",
								$V("DBName")));
						if (dt.getRowCount() == 0) {
							Response.setError("��װĿ�����ݿⲻ���ڣ����ֹ�����!<br>" + "ע�⣺<br>1��ע�����������ݿ�Ĵ洢�ռ䲻С��150M��"
									+ "<br>2��������ҳ���С����Ϊ16K" + "<br>3���ַ�������ΪUTF8���������Ϊnocase��");
							conn.closeReally();
							return;
						}
						da.executeNoQuery(new QueryBuilder("use " + $V("DBName")));
					} catch (Exception e) {
						e.printStackTrace();
						throw e;
						// ˵��û��master���Ȩ��
					}
				}
			} else {
				conn = DBConnPoolImpl.createConnection(dcc, false);
			}
			boolean importData = "1".equals($V("ImportData"));
			final DBConn conn2 = conn;
			final boolean autoCreate = "1".equals($V("AutoCreate"));
			final RequestImpl r = Request;

			if (importData) {
				LongTimeTask ltt = LongTimeTask.getInstanceByType("Install");
				if (ltt != null) {
					Response.setError("����������������У�������ֹ��");
					return;
				}
				ltt = new LongTimeTask() {
					public void execute() {
						try {
							DBImport di = new DBImport();
							di.setTask(this);
							Config.setValue("App.DebugMode", "true");
							if (di.importDB(Config.getContextRealPath() + "WEB-INF/data/installer/Install.dat", conn2,
									autoCreate)) {
								if (autoCreate) {
									setCurrentInfo("���ڽ�������");
									Install.createIndexes(conn2, null, conn2.getDBConfig().DBType);
								}
								setCurrentInfo("���ڳ�ʼ��ϵͳ����");
								Install.init(conn2, r);
								setPercent(33);
								generateFrameworkConfig(dcc);
								Config.loadConfig();// ��������framework.xml
								Config.isInstalled = false;
								Config.loadDBConfig();// ����������ݿ�����

								UserData user = new UserData();
								user.setLogin(true);
								user.setManager(true);
								user.setBranchInnerCode("0001");
								user.setUserName(UserList.ADMINISTRATOR);
								User.setCurrent(user);
								Application.setCurrentSiteID(new QueryBuilder("select ID from ZCSite").executeString());

								// ��ʼ�������ݿ�����
								setCurrentInfo("���ڽ���ȫ�ļ��������ļ������ʱԼ1��3���ӣ����Ե�");
								ZCFullTextSchema ft = new ZCFullTextSchema();
								ft = ft.query().get(0);
								SearchAPI.update(ft.getID());

								// ��ʼ����ȫվ
								setCurrentInfo("��������ȫվ��̬�ļ������ʱԼ2-5���ӣ����Ե�");
								PageGenerator pg = new PageGenerator(this);
								ZCSiteSchema site = new ZCSiteSchema();
								site = site.query().get(0);
								pg.staticSite(site.getID());

								setCurrentInfo("��װ��ɣ����ض��򵽵�¼ҳ��!");
								Config.isInstalled = true;// ��װ����
								// ��ʼ��ʱ����ϵͳ
								CronManager.getInstance().init();
							} else {
								addError("<font color=red>����ʧ�ܣ���鿴��������־! ȷ��������밴F5ˢ��ҳ�����µ��롣</font>");
							}
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							if (conn2 != null) {
								try {
									conn2.closeReally();
								} catch (SQLException e) {
									e.printStackTrace();
								}
							}
						}
					}
				};
				ltt.setType("Install");
				ltt.setUser(User.getCurrent());
				ltt.start();
				$S("TaskID", "" + ltt.getTaskID());
				Response.setStatus(1);
			} else {// ֻ��������
				Install.init(conn2, r);
				generateFrameworkConfig(dcc);
				Config.loadConfig();// ��������framework.xml
				CronManager.getInstance().init();
				Response.setLogInfo(2, Config.getAppCode() + "��ʼ�����!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Response.setLogInfo(3, "���ӵ����ݿ�ʱ��������:" + e.getMessage());
		}
	}

	public static void generateFrameworkConfig(DBConnConfig dcc) {
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		sb.append("<framework>\n");
		sb.append("	<application>\n");
		sb.append("		<config name=\"DebugMode\">false</config>\n");
		sb.append("		<config name=\"LogLevel\">Debug</config>\n");
		sb.append("		<config name=\"LoginClass\">com.nswt.platform.Login</config>\n");
		sb.append("		<config name=\"LoginPage\">Login.jsp</config>\n");
		sb.append("		<config name=\"MinimalMemory\">true</config>\n");
		sb.append("		<config name=\"CodeSource\">com.nswt.platform.pub.PlatformCodeSource</config>\n");
		sb.append("		<config name=\"WorkflowAdapter\">com.nswt.cms.workflow.CMSWorkflowAdapter</config>\n");
		sb.append("		<config name=\"ExistPlatformDB\">true</config>\n");
		sb.append("		<config name=\"PDM\">Platform,nswtp,WorkFlow</config>\n");
		sb.append("	</application>\n");

		sb.append("	<cache>\n");
		sb.append("		<provider class=\"com.nswt.bbs.ForumCache\" />\n");
		sb.append("		<provider class=\"com.nswt.platform.pub.PlatformCache\" />\n");
		sb.append("		<provider class=\"com.nswt.cms.pub.CMSCache\" />\n");
		sb.append("		<provider class=\"com.nswt.cms.document.MessageCache\" />\n");
		sb.append("	</cache>\n");

		sb.append("	<extend>\n");
		sb.append("		<action class=\"com.nswt.shop.extend.ShopMenuExtend\" />\n");
		sb.append("		<action class=\"com.nswt.shop.extend.ShopLoginExtend\" />\n");
		sb.append("		<action class=\"com.nswt.bbs.extend.BBSMenuExtend\" />\n");
		sb.append("		<action class=\"com.nswt.bbs.extend.BBSLoginExtend\" />\n");
		sb.append("	</extend>\n");

		sb.append("	<cron>\n");
		sb.append("		<config name=\"RefreshInterval\">30000</config>\n");
		sb.append("		<taskManager class=\"com.nswt.datachannel.WebCrawlTaskManager\" />\n");
		sb.append("		<taskManager class=\"com.nswt.datachannel.FTPCrawlTaskManager\" />\n");
		sb.append("		<taskManager class=\"com.nswt.cms.dataservice.FullTextTaskManager\" />\n");
		sb.append("		<taskManager class=\"com.nswt.cms.datachannel.PublishTaskManager\" />\n");
		sb.append("		<task class=\"com.nswt.framework.FrameworkTask\" time=\"30 10,16 * * *\" />\n");
		sb.append("		<task class=\"com.nswt.cms.dataservice.ADUpdating\" time=\"*/30 * * * *\" />\n");
		sb.append("		<task class=\"com.nswt.cms.datachannel.DeployTask\" time=\"* * * * *\" />\n");
		sb.append("		<task class=\"com.nswt.cms.datachannel.PublishTask\" time=\"* * * * *\" />\n");
		sb.append("		<task class=\"com.nswt.cms.stat.StatUpdateTask\" time=\"*/5 * * * *\" />\n");
		sb.append("		<task class=\"com.nswt.cms.datachannel.ArticleArchiveTask\" time=\"0 0 1 * *\" />\n");
		sb.append("		<task class=\"com.nswt.cms.datachannel.ArticleCancelTopTask\" time=\"*/5 * * * *\" />\n");
		sb.append("		<task class=\"com.nswt.cms.document.ArticleRelaTask\" time=\"*/2 * * * *\" />\n");
		sb.append("		<task class=\"com.nswt.cms.site.TagUpdateTask\" time=\"0 * * * *\" />\n");
		sb.append("		<task class=\"com.nswt.datachannel.InnerSyncTask\" time=\"* * * * *\" />\n");
		sb.append("		<task class=\"com.nswt.datachannel.DBSyncTask\" time=\"* * * * *\" />\n");
		sb.append("	</cron>\n");

		sb.append("	<databases>\n");
		sb.append("		<database name=\"Default\">\n");
		sb.append("			<config name=\"Type\">" + dcc.DBType + "</config>\n");
		if (dcc.isJNDIPool) {
			sb.append("			<config name=\"JNDIName\">" + dcc.JNDIName + "</config>\n");
		} else {
			sb.append("			<config name=\"ServerAddress\">" + dcc.DBServerAddress + "</config>\n");
			sb.append("			<config name=\"Port\">" + dcc.DBPort + "</config>\n");
			sb.append("			<config name=\"Name\">" + dcc.DBName + "</config>\n");
			sb.append("			<config name=\"UserName\">" + dcc.DBUserName + "</config>\n");
			sb.append("			<config name=\"Password\">$KEY"
					+ EncryptUtil.encrypt3DES(dcc.DBPassword, EncryptUtil.DEFAULT_KEY) + "</config>\n");
			sb.append("			<config name=\"MaxConnCount\">1000</config>\n");
			sb.append("			<config name=\"InitConnCount\">0</config>\n");
			sb.append("			<config name=\"TestTable\">ZDMaxNo</config>\n");
			if (dcc.isLatin1Charset) {
				sb.append("			<config name=\"isLatin1Charset\">true</config>\n");
			}
		}
		sb.append("		</database>\n");
		sb.append("	</databases>\n");
		sb.append("	<allowUploadExt>\n");
		sb
				.append("		<config name=\"AllowAttachExt\">doc,docx,xls,xlsx,ppt,pptx,pdf,swf,rar,zip,txt,xml,html,htm,css,js,db,dat</config>\n");
		sb.append("		<config name=\"AllowAudioExt\">mp3</config>\n");
		sb.append("		<config name=\"AllowImageExt\">jpg,gif,jpeg,png,bmp,tif,zip</config>\n");
		sb.append("		<config name=\"AllowVideoExt\">asx,flv,avi,wmv,mp4,mov,asf,mpg,rm,rmvb</config>\n");
		sb.append("		</allowUploadExt>\n");
		sb.append("	</framework>\n");
		FileUtil.writeText(Config.getContextRealPath() + "WEB-INF/classes/framework.xml", sb.toString(), "UTF-8");
	}

	public static void generateSQL(HttpServletRequest request, HttpServletResponse response) {
		String dbtype = request.getParameter("Type");
		String sql = new DBImport().getSQL(Config.getContextRealPath() + "WEB-INF/data/installer/Install.dat", dbtype);

		StringBuffer sb = new StringBuffer(sql);
		createIndexes(null, sb, dbtype);

		try {
			request.setCharacterEncoding(Constant.GlobalCharset);
			response.reset();
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment; filename=" + dbtype + ".zip");

			OutputStream os = response.getOutputStream();
			ZipUtil.zipStream(new StringInputStream(sb.toString()), response.getOutputStream(), dbtype + ".sql");
			os.flush();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		createIndexes(DBConnPool.getConnection(), new StringBuffer(), "MYSQL");
	}

	public static String[] IndexArray = new String[] { "zcarticle(CatalogID,Status)", "zcarticle(Orderflag)",
			"zcarticle(publishDate)", "zcarticle(addtime)", "zcarticle(modifytime)", "zcarticle(DownlineDate)",
			"zcarticle(CatalogInnercode)", "zcarticle(SiteID)", "zcarticle(refersourceid)", "zcarticle(keyword)",
			"zcarticle(ArchiveDate)", "bzcarticle(BackupMemo)", "bzcarticle(CatalogID)", "zcimage(CatalogID)",
			"zcimage(CatalogInnercode)", "zcimage(SiteID)", "zcimage(OrderFlag)", "zcimage(publishDate)",
			"zcimage(addtime)", "zcimage(modifytime)", "zcvideo(CatalogID)", "zcvideo(CatalogInnercode)",
			"zcvideo(SiteID)", "zcvideo(OrderFlag)", "zcvideo(publishDate)", "zcvideo(addtime)", "zcvideo(modifytime)",
			"zcaudio(CatalogID)", "zcaudio(CatalogInnercode)", "zcaudio(SiteID)", "zcaudio(OrderFlag)",
			"zcaudio(publishDate)", "zcaudio(addtime)", "zcaudio(modifytime)", "zcattachment(CatalogID)",
			"zcattachment(CatalogInnercode)", "zcattachment(SiteID)", "zcattachment(OrderFlag)",
			"zcattachment(publishDate)", "zcattachment(addtime)", "zcattachment(modifytime)",
			"zcarticlelog(articleID)", "zcarticlevisitlog(articleID)", "zccomment(relaid)", "zccomment(catalogid)",
			"zccatalog(SiteID,Type)", "zccatalog(InnerCode)", "zcpageblock(SiteID)", "zcpageblock(CatalogID)",
			"zdprivilege(owner)", "zdcolumnrela(relaid)", "zdcolumnvalue(relaid)", "zwinstance(workflowid)",
			"zwstep(InstanceID)", "zwstep(NodeID)", "zwstep(owner)", "zctag(tag,siteid)", "zcvisitlog(siteid)" };

	/**
	 * �������������䣬����i��ʾ�����������ڱ�ĵڼ�������
	 */
	public static String createIndex(String indexInfo, int i, String dbType) {
		int p1 = indexInfo.indexOf("(");
		if (p1 < 0) {
			return null;
		}
		String table = indexInfo.substring(0, p1);
		if (!indexInfo.endsWith(")")) {
			return null;
		}
		indexInfo = indexInfo.substring(p1 + 1, indexInfo.length() - 1);
		String[] cs = indexInfo.split(",");
		StringBuffer sb = new StringBuffer();
		String name = "idx" + i + "_" + table.substring(2);
		if (dbType.equals(DBConnConfig.ORACLE)) {
			if (name.length() > 15) {
				name = name.substring(0, 15);
			}
		}
		sb.append("create index " + name + " on " + table + " (");
		boolean first = true;
		for (int j = 0; j < cs.length; j++) {
			if (StringUtil.isEmpty(cs[j])) {
				continue;
			}
			if (!first) {
				sb.append(",");
			}
			sb.append(cs[j]);
			first = false;
		}
		sb.append(")");
		return sb.toString();
	}

	/**
	 * ����ĳ�ű��ϵ������������
	 */
	public static ArrayList getIndexSQLForTable(String tableName, String dbType) {
		ArrayList list = new ArrayList();
		for (int i = 0; i < IndexArray.length; i++) {
			String indexInfo = IndexArray[i];
			if (indexInfo.toLowerCase().startsWith(tableName.toLowerCase() + "(")) {
				String sql = createIndex(indexInfo, i, dbType);
				if (StringUtil.isNotEmpty(sql)) {
					list.add(sql);
				}
			}
		}
		return list;
	}

	/**
	 * ������������
	 */
	public static void createIndexes(DBConn conn, StringBuffer sbAll, String dbtype) {
		DataAccess da = null;
		if (conn == null) {
			da = new DataAccess();
		} else {
			da = new DataAccess(conn);
		}
		for (int i = 0; i < IndexArray.length; i++) {
			String indexInfo = IndexArray[i];
			int p1 = indexInfo.indexOf("(");
			if (p1 < 0) {
				continue;
			}
			String table = indexInfo.substring(0, p1);
			if (!indexInfo.endsWith(")")) {
				continue;
			}
			String name = "idx" + i + "_" + table.substring(2);
			String sql = createIndex(indexInfo, i, dbtype);
			if (conn != null) {
				try {
					if (dbtype.equals(DBConnConfig.MYSQL)) {
						da.executeNoQuery(new QueryBuilder("alter table " + table + " drop index " + name));
					} else if (dbtype.equals(DBConnConfig.MSSQL)) {
						da.executeNoQuery(new QueryBuilder("drop index " + name + " on " + table));
					} else {
						da.executeNoQuery(new QueryBuilder("drop index " + name));
					}
				} catch (SQLException e) {
					// ����Ҫ���
				}
				try {
					da.executeNoQuery(new QueryBuilder(sql));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else {
				sbAll.append(sql);
				if (dbtype.equals(DBConnConfig.MSSQL)) {
					sbAll.append("\n");
					sbAll.append("go\n");
				} else {
					sbAll.append(";\n");
				}
			}
		}
	}

	public static void init(DBConn conn, RequestImpl r) {
		DataAccess da = new DataAccess(conn);
		try {
			if (StringUtil.isNotEmpty(Config.getContextPath())) {
				String path = Config.getContextPath() + "Services";
				String prefix = r.getScheme() + "://" + r.getServerName();
				if (r.getScheme().equalsIgnoreCase("http") && r.getPort() != 80) {
					prefix = prefix + ":" + r.getPort();
				}
				if (r.getScheme().equalsIgnoreCase("https") && r.getPort() != 443) {
					prefix = ":" + r.getPort();
				}
				path = prefix + path;
				da.executeNoQuery(new QueryBuilder("update ZDConfig set Value=? where Type='ServicesContext'", path));
				Config.update();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
