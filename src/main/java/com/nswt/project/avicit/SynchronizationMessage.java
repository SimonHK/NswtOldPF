package com.nswt.project.avicit;

import java.security.SecureRandom;
import java.sql.SQLException;

import com.nswt.framework.Config;
import com.nswt.framework.data.DBConn;
import com.nswt.framework.data.DBConnPool;
import com.nswt.framework.data.DataAccess;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.utility.DateUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;

public class SynchronizationMessage {

	public static boolean synchronizationMessage(String subject, String adduser, String[] users, String articleID) {
		DataTable dt = new QueryBuilder("select * from zduser").executeDataTable();
		Mapx userMap = dt.toMapx("UserName", "Prop1");
		if (users == null) {
			return false;
		}
		
		DBConn conn = DBConnPool.getConnection("OracleUser");
		DataAccess da = new DataAccess(conn);
		try {
			da.setAutoCommit(false);
			StringBuffer sb = new StringBuffer("insert into SYS_IM(ID,IM_CODE,IM_TYPE,IM_TITLE,IM_URL,IM_CREATE_TIME"
					+ ",IM_START_SHOW_TIME,IM_INVALIDATION_TIME,IM_FROM_USER_ID,IM_TO_USER_ID,IM_EVENT_NAME,IM_EVENT_FUNC"
					+ ",IM_EVENT_ARGS,IM_IS_READ,IM_REMARK) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			QueryBuilder qb = new QueryBuilder(sb.toString());
			qb.setBatchMode(true);
			
			for (int i=0; i<users.length; i++) {
				qb.add(getPasswordString());
				qb.add("CMSContentAudit");
				qb.add("ÄÚÈÝ¹ÜÀí");
				qb.add(subject);
				String key = "WIU%&*DJAJKL%^*W(DLJIST";
				long time = System.currentTimeMillis();
				String url = Config.getValue("ServicesContext").substring(0, Config.getValue("ServicesContext"
						).indexOf("Services")) + "SSO.jsp?u=" + users[i] + "&t=" 
						+ time + "&s=" + StringUtil.md5Hex(users[i] + time + key) + "&Referer=" 
						+ Config.getValue("ServicesContext").substring(0, Config.getValue("ServicesContext"
						).indexOf("Services")) + "Document/ArticleAudit.jsp?ArticleID=" + articleID;
				qb.add(url);
				qb.add(DateUtil.getCurrentDateTime());
				qb.add("");
				qb.add("");
				qb.add(userMap.getString(adduser));
				qb.add(userMap.getString(users[i]));
				qb.add("");
				qb.add("");
				qb.add("");
				qb.add("0");
				qb.add("");
				qb.addBatch();
			}
			
			da.executeNoQuery(qb);
			da.commit();
		} catch (NumberFormatException e) {
			try {
				da.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (SQLException e) {
			try {
				da.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				if (da != null) {
					da.setAutoCommit(true);
					da.close();
				}
				
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	private static int PASSWORD_LENGTH = 32;

	private static char[] cs = ("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890").toCharArray();
	
	private static String toPrintable(byte[] b) {
		char[] out = new char[b.length];
		for (int i = 0; i < b.length; i++) {
			int index = b[i] % cs.length;
			if (index < 0) {
				index += cs.length;
			}
			out[i] = cs[index];
		}
		return new String(out);
	}
	
	private static String getPasswordString() {
		byte[] b = new byte[PASSWORD_LENGTH];
		SecureRandom sr = new SecureRandom();
		sr.nextBytes(b);
		return toPrintable(b);
	}
}