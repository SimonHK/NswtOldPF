package com.nswt.cms.document;

import java.util.ArrayList;
import java.util.Date;

import com.nswt.framework.User;
import com.nswt.framework.cache.CacheManager;
import com.nswt.framework.cache.CacheProvider;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.Executor;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringFormat;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.pub.NoUtil;
import com.nswt.schema.ZCMessageSchema;
import com.nswt.schema.ZCMessageSet;

/**
 * ����Ϣ�����࣬���ڽ���߲���ʱ����ϢƵ����ѯʱ��������
 * 
 * ���� : 2010-2-6 <br>
 * ����: NSWT <br>
 * ���䣺nswt@nswt.com.cn <br>
 */
public class MessageCache extends CacheProvider {
	public static final String ProviderName = "Message";

	/**
	 * ����һ������Ϣ��������Ϣ���У�Ĭ�Ϸ�����Ϊ��ǰ�û�
	 */
	public static boolean addMessage(String subject, String content, String toUser) {
		Transaction tran = new Transaction();
		addMessage(tran, subject, content, new String[] { toUser }, User.getUserName(), true);
		return tran.commit();
	}

	public static boolean addMessage(Transaction tran, String subject, String content, String toUser) {
		return addMessage(tran, subject, content, new String[] { toUser }, User.getUserName(), true);
	}

	public static boolean addMessage(Transaction tran, String subject, String content, String toUser, String formUser) {
		return addMessage(tran, subject, content, new String[] { toUser }, formUser, true);
	}

	/**
	 * ����һ������Ϣ��������Ϣ���У�ָ�������ˡ�
	 */
	public static boolean addMessage(String subject, String content, String[] userList, String fromUser) {
		Transaction tran = new Transaction();
		addMessage(tran, subject, content, userList, fromUser, true);
		return tran.commit();
	}

	public static boolean addMessage(Transaction tran, String subject, String content, String[] userList,
			String fromUser, final boolean popFlag) {
		ArrayList list = new ArrayList();
		for (int i = 0; i < userList.length; i++) {
			if (!userList[i].equals(fromUser) && StringUtil.isNotEmpty(userList[i])) {
				ZCMessageSchema message = new ZCMessageSchema();
				message.setID(NoUtil.getMaxID("MessageID"));
				message.setSubject(subject);
				message.setBox("outbox");
				message.setContent(content);
				message.setFromUser(fromUser);
				message.setToUser(userList[i]);
				message.setReadFlag(0);
				message.setPopFlag(popFlag ? 0 : 1);
				message.setAddTime(new Date());
				PopMessage pm = new PopMessage(message.getID(), getHtmlMessage(message.getID(), message.getSubject(),
						content), System.currentTimeMillis(), userList[i]);
				list.add(pm);
				tran.add(message, Transaction.INSERT);
			}
		}
		tran.addExecutor(new Executor(list) {
			public boolean execute() {
				ArrayList list = (ArrayList) param;
				for (int i = 0; i < list.size(); i++) {
					PopMessage pm = (PopMessage) list.get(i);
					PopMessageList pml = (PopMessageList) CacheManager.get(ProviderName, "LastMessage", pm.ToUser);
					synchronized (pml) {
						if (popFlag) {
							pml.list.add(pm);
						}
					}
					String count = (String) CacheManager.get(ProviderName, "Count", pm.ToUser);
					CacheManager.set(ProviderName, "Count", pm.ToUser, Integer.parseInt(count) + 1);
				}
				return true;
			}
		});
		return true;
	}

	public static void removeIDs(ZCMessageSet set) {
		PopMessageList pml = (PopMessageList) CacheManager.get(ProviderName, "LastMessage", User.getUserName());
		int NoReadCount = 0;
		synchronized (pml) {
			for (int j = 0; j < set.size(); j++) {
				for (int i = 0; i < pml.list.size(); i++) {
					PopMessage pm = (PopMessage) pml.list.get(i);
					if (set.get(j).getID() == pm.ID) {
						pml.list.remove(pm);
					}
				}
				if (set.get(j).getReadFlag() == 0) {
					NoReadCount++;
				}

			}
		}
		String count = (String) CacheManager.get(ProviderName, "Count", User.getUserName());
		CacheManager.set(ProviderName, "Count", User.getUserName(), Integer.parseInt(count) - NoReadCount);
	}

	public static int getNoReadCount() {
		String count = (String) CacheManager.get(ProviderName, "Count", User.getUserName());
		if (StringUtil.isEmpty(count)) {
			return 0;
		} else {
			int c = Integer.parseInt(count);
			if (c < 0) {
				CacheManager.set(ProviderName, "Count", User.getUserName(), "0");
				return 0;
			} else {
				return c;
			}
		}
	}

	/**
	 * ��ȡ��ǰ�û���һ��������Ϣ
	 */
	public static String getFirstPopMessage() {
		return getFirstPopMessage(User.getUserName());
	}

	/**
	 * ��ȡָ���û���һ��������Ϣ
	 */
	public static String getFirstPopMessage(String userName) {
		PopMessageList pml = (PopMessageList) CacheManager.get(ProviderName, "LastMessage", userName);
		if (pml == null) {
			return "";
		}
		return pml.getLastMessage();
	}

	public String getProviderName() {
		return ProviderName;
	}

	public void onKeyNotFound(String type, Object key) {
		if (type.equals("Count")) {// �����û�δ����Ϣ����
			QueryBuilder qb = new QueryBuilder("select count(1) from ZCMessage where ReadFlag=0 and ToUser=?", key);
			int count = qb.executeInt();
			CacheManager.set(ProviderName, type, key, count);
		}
		if (type.equals("LastMessage")) {// �����û����µ�δ��������Ϣ
			PopMessageList pml = new PopMessageList(key.toString());
			CacheManager.set(ProviderName, type, key, pml);
		}
	}

	public void onTypeNotFound(String type) {
		// �ӳټ���
		CacheManager.setMapx(ProviderName, type, new Mapx());
	}

	/**
	 * ���Ӵ����ԭ���ǣ�<br>
	 * 1���ж�̨������ͬһ���û���¼ʱ�����л����������յ�������Ϣ<br>
	 * 2���ڷ�������Ƶ������������£�Ҳ��ȷ������δ����������Ϣ���л��ᵯ��
	 */
	static class PopMessageList {
		private ArrayList list = new ArrayList();
		public static final int Interval = 10000;// ÿ��10��ǰ̨ȡһ����Ϣ

		public PopMessageList(String userName) {
			long current = System.currentTimeMillis();
			QueryBuilder qb = new QueryBuilder(
					"select * from ZCMessage where ReadFlag=0 and PopFlag=0 and ToUser=? order by AddTime asc",
					userName);
			DataTable dt = qb.executeDataTable();
			for (int i = 0; i < dt.getRowCount(); i++) {
				String html = MessageCache.getHtmlMessage(dt.getLong(i, "ID"), dt.getString(i, "Subject"), dt
						.getString(i, "Content"));
				list.add(new PopMessage(dt.getLong(i, "ID"), html, current, userName));
			}
		}

		public synchronized String getLastMessage() {
			if (list.size() == 0) {
				return null;
			} else {
				for (int i = list.size() - 1; i >= 0; i--) {
					PopMessage pm = (PopMessage) list.get(i);// ���һ��
					if (System.currentTimeMillis() - pm.LastTime > 30 * 60 * 1000) {// 30���Ӻ������
						list.remove(pm);
					}
					if (pm.SessionIDMap.containsKey(User.getSessionID())) {
						continue;
					}
					pm.SessionIDMap.put(User.getSessionID(), "1");
					if (!pm.PopedFlag) {// ��һ�ε������Ժ���������ͻ��˻�����ȡ
						QueryBuilder qb = new QueryBuilder("update ZCMessage set PopFlag=1 where ID=?", pm.ID);
						qb.executeNoQuery();
						pm.PopedFlag = true;
					}
					return pm.Message;
				}
			}
			return null;
		}
	}

	public static String getHtmlMessage(long id, String subject, String content) {
		StringFormat sf = new StringFormat("�����µĶ���Ϣ��<hr>?<hr>?<br><br>?");
		sf.add(subject);
		sf.add(content);
		sf.add("<p align='center' width='100%'><input type='button' class='inputButton' value='��֪����'"
				+ " onclick=\"Server.getOneValue('com.nswt.cms.document.Message.updateReadFlag'," + id
				+ ",function(){MsgPop.closeSelf();});\"></p>");
		return sf.toString();
	}

	static class PopMessage {
		public long ID;
		public String Message;
		public long LastTime;
		public boolean PopedFlag = false;
		public String ToUser;
		public Mapx SessionIDMap = new Mapx();

		public PopMessage(long id, String message, long lastTime, String toUser) {
			ID = id;
			Message = message;
			LastTime = lastTime;
			ToUser = toUser;
		}
	}
}
