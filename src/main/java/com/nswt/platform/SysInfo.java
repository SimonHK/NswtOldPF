package com.nswt.platform;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nswt.framework.Config;
import com.nswt.framework.Constant;
import com.nswt.framework.Page;
import com.nswt.framework.SessionListener;
import com.nswt.framework.User;
import com.nswt.framework.controls.DataListAction;
import com.nswt.framework.data.DBConnConfig;
import com.nswt.framework.data.DBConnPool;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.license.LicenseInfo;
import com.nswt.framework.messages.LongTimeTask;
import com.nswt.framework.orm.DBExport;
import com.nswt.framework.orm.DBImport;
import com.nswt.framework.schedule.CronManager;
import com.nswt.framework.utility.DateUtil;
import com.nswt.project.avicit.ImportData;
import com.nswt.project.avicit.SynchronizationData;

/**
 * @Author NSWT
 * @Date 2007-7-13
 * @Mail nswt@nswt.com.cn
 */
public class SysInfo extends Page {

	/**
	 * ��ȡ��½״̬
	 * 
	 * @return �ָ������¼ ��ʱ��ֹ��¼;
	 */
	public static String getLoginStatus() {
		if (!Config.isAllowLogin) {
			return "�ָ������¼";
		} else {
			return "��ʱ��ֹ��¼";
		}
	}

	/**
	 * �ı��½״̬
	 */
	public void changeLoginStatus() {
		Config.isAllowLogin = !Config.isAllowLogin;
		if (!Config.isAllowLogin) {
			$S("LoginStatus", "�ָ������¼");
		} else {
			$S("LoginStatus", "��ʱ��ֹ��¼");
		}
	}

	/**
	 * ǿ���˳�
	 */
	public void forceExit() {
		SessionListener.forceExit();
		Response.setStatus(1);
	}

	/**
	 * ��ȡϵͳ��Ϣ
	 */
	public static void list1DataBind(DataListAction dla) {
		DataTable dt = new DataTable();
		dt.insertColumn("Name");
		dt.insertColumn("Value");
		dt.insertRow(new Object[] { "Ӧ�ó�������", Config.getAppCode() + "(" + Config.getAppName() + ")" });
		dt.insertRow(new Object[] { "Ӧ�ó���汾", Config.getMainVersion() + "." + Config.getMinorVersion() });
		dt.insertRow(new Object[] { "��������ʱ��",
				DateUtil.toString(new Date(Long.parseLong(Config.getValue("App.Uptime"))), "yyyy-MM-dd HH:mm:ss") });
		dt.insertRow(new Object[] { "��ǰ����¼�û���", new Long(Config.getLoginUserCount()) });
		dt.insertRow(new Object[] { "�Ƿ��ǵ���ģʽ", Config.getValue("App.DebugMode") });
		dt.insertRow(new Object[] { "����ϵͳ����", Config.getValue("System.OSName") });
		dt.insertRow(new Object[] { "����ϵͳ�汾", Config.getValue("System.OSVersion") });
		dt.insertRow(new Object[] { "����ϵͳ����", Config.getValue("System.OSPatchLevel") });// ����JDK�Ժ󲹳�
		dt.insertRow(new Object[] { "JDK����", Config.getValue("System.JavaVendor") });
		dt.insertRow(new Object[] { "JDK�汾", Config.getValue("System.JavaVersion") });
		dt.insertRow(new Object[] { "JDK��Ŀ¼", Config.getValue("System.JavaHome") });
		dt.insertRow(new Object[] { "Servlet��������", Config.getValue("System.ContainerInfo") });
		dt.insertRow(new Object[] { "����Servlet�������û���", Config.getValue("System.OSUserName") });
		dt.insertRow(new Object[] {
				"JDK�����ڴ���/��������",
				Runtime.getRuntime().totalMemory() / 1024 / 1024 + "M/" + Runtime.getRuntime().maxMemory() / 1024
						/ 1024 + "M" });
		dt.insertRow(new Object[] { "�ļ�����", Config.getFileEncode() });
		dla.bindData(dt);
	}

	/**
	 * ��ȡ���ݿ���Ϣ
	 */
	public static void list2DataBind(DataListAction dla) {
		DataTable dt = new DataTable();
		dt.insertColumn("Name");
		dt.insertColumn("Value");
		DBConnConfig dcc = DBConnPool.getDBConnConfig();
		dt.insertRow(new Object[] { "���ݿ�����", dcc.DBType });
		if (dcc.isJNDIPool) {
			dt.insertRow(new Object[] { "JNDI����", dcc.JNDIName });
		} else {
			dt.insertRow(new Object[] { "���ݿ��������ַ", dcc.DBServerAddress });
			dt.insertRow(new Object[] { "���ݿ�������˿�", "" + dcc.DBPort });
			dt.insertRow(new Object[] { "���ݿ�����", dcc.DBName });
			dt.insertRow(new Object[] { "�û���", dcc.DBUserName });
		}
		dla.bindData(dt);
	}

	/**
	 * ��ȡ��Ȩ��Ϣ
	 */
	public static void list3DataBind(DataListAction dla) {
//		DataTable dt = new DataTable();
//		dt.insertColumn("Name");
//		dt.insertColumn("Value");
//		dt.insertRow(new Object[] { "��Ȩ��", LicenseInfo.getName() });
//		dt.insertRow(new Object[] { "��Ч����", DateUtil.toString(LicenseInfo.getEndDate(), "yyyy-MM-dd HH:mm:ss") });
//		dt.insertRow(new Object[] { "��Ȩ�û���", new Long(LicenseInfo.getUserLimit()) });
//		dt.insertRow(new Object[] { "��Ȩ��Ʒ����", Config.getAppCode() });
//		dt.insertRow(new Object[] { "��ȨMAC��ַ", LicenseInfo.getMacAddress() });
//		dla.bindData(dt);
	}

	/**
	 * �������ݿ�
	 */
	public static void downloadDB(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding(Constant.GlobalCharset);
			response.reset();
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment; filename=DB_"
					+ DateUtil.getCurrentDate("yyyyMMddHHmmss") + ".dat");
			OutputStream os = response.getOutputStream();

			String path = Config.getContextRealPath() + "WEB-INF/data/backup/DB_"
					+ DateUtil.getCurrentDate("yyyyMMddHHmmss") + ".dat";
			new DBExport().exportDB(path);

			byte[] buffer = new byte[1024];
			int read = -1;
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(path);
				while ((read = fis.read(buffer)) != -1) {
					if (read > 0) {
						byte[] chunk = null;
						if (read == 1024) {
							chunk = buffer;
						} else {
							chunk = new byte[read];
							System.arraycopy(buffer, 0, chunk, 0, read);
						}
						os.write(chunk);
						os.flush();
					}
				}
			} finally {
				if (fis != null) {
					fis.close();
				}
			}
			os.flush();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * �������ݿ�
	 */
	public void uploadDB() {
			final String FileName = $V("DBFile");
			LongTimeTask ltt = LongTimeTask.getInstanceByType("Install");
			if (ltt != null) {
				Response.setLogInfo(0, "����������������У�������ֹ��");
				return;
			}
			SessionListener.forceExit();
			Config.isAllowLogin = false;
			ltt = new LongTimeTask() {
				public void execute() {
					DBImport di = new DBImport();
					di.setTask(this);
					di.importDB(FileName, "Default");
					setPercent(100);
					Config.loadConfig();// ��������framework.xml
					CronManager.getInstance().init();
				}
			};
			ltt.setType("Install");
			ltt.setUser(User.getCurrent());
			ltt.start();
			Config.isAllowLogin = true;
			Response.setLogInfo(1, ltt.getTaskID()+"");
	}
	
	public void inportUser() {
		if (ImportData.excute()) {
			Response.setLogInfo(1, "����ɹ���");
		} else {
			Response.setLogInfo(0, "����ʧ�ܣ�");
		}
	}
	
	public void synchronizationUser() {
		if (SynchronizationData.excute()) {
			Response.setLogInfo(1, "ͬ�����ݳɹ���");
		} else {
			Response.setLogInfo(0, "ͬ������ʧ�ܣ�");
		}
	}
}