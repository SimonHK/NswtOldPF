package com.nswt.framework.utility;

import java.util.ArrayList;

/**
 * ���κ��е����е�������Ϣ
 * 
 * @Author NSWT
 * @Date 2016-12-13
 * @Mail nswt@nswt.com.cn
 */
public class Errorx {
	protected ArrayList list = new ArrayList();

	protected boolean ErrorFlag = false;

	protected boolean ErrorDealedFlag = true;

	protected static ThreadLocal ErrorXLocal = new ThreadLocal();

	/**
	 * ����һ����ͨ��Ϣ
	 * 
	 * @param message
	 */
	public static void addMessage(String message) {
		add(message, false);
	}

	/**
	 * ����һ��������Ϣ
	 * 
	 * @param message
	 */
	public static void addError(String message) {
		add(message, true);
	}

	/**
	 * ����һ����Ϣ��isErrorΪtrue��ʾ�Ǵ�����Ϣ
	 * 
	 * @param message
	 * @param isError
	 */
	private static void add(String message, boolean isError) {
		Message msg = new Message();
		msg.isError = isError;
		msg.Message = message;
		if (isError) {
			getCurrent().ErrorFlag = true;
			getCurrent().ErrorDealedFlag = false;
			StackTraceElement stack[] = (new Throwable()).getStackTrace();
			StringBuffer sb = new StringBuffer();
			sb.append("com.nswt.framework.utility.Errorx : ");
			sb.append(message);
			sb.append("\n");
			for (int i = 2; i < stack.length; i++) {// ǰ������Errorx��ķ���������
				StackTraceElement ste = stack[i];
				if (ste.getClassName().indexOf("DBConnPool") == -1) {
					sb.append("\tat ");
					sb.append(ste.getClassName());
					sb.append(".");
					sb.append(ste.getMethodName());
					sb.append("(");
					sb.append(ste.getFileName());
					sb.append(":");
					sb.append(ste.getLineNumber());
					sb.append(")\n");
				}
			}
			msg.StackTrace = sb.toString();
		}
		getCurrent().list.add(msg);
	}

	/**
	 * ����������Ϣ
	 * 
	 * @return
	 */
	public static ArrayList getErrors() {
		return getCurrent().list;
	}

	/**
	 * �Ƿ��д�����Ϣ
	 * 
	 * @return
	 */
	public static boolean hasError() {
		return getCurrent().ErrorFlag;
	}

	/**
	 * �Ƿ����д�����Ϣ���Ѿ����������
	 * 
	 * @return
	 */
	public static boolean hasDealed() {
		return getCurrent().ErrorDealedFlag;
	}

	/**
	 * ���������Ϣ
	 */
	public void clear() {
		getCurrent().list.clear();
		getCurrent().ErrorFlag = false;
		getCurrent().ErrorDealedFlag = true;
	}

	/**
	 * ת�����д�����ϢΪ�ɶ�����ʽ
	 * 
	 * @return
	 */
	public static String printString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0, j = 1; i < getCurrent().list.size(); i++) {
			Message msg = (Message) getCurrent().list.get(i);
			if (msg.isError) {
				// sb.append(j);
				// sb.append(",");
				sb.append("Error:");
				sb.append(msg.Message);
				sb.append("<br>\n");
				// LogUtil.getLogger().warning(msg.StackTrace);
				j++;
			}
		}
		for (int i = 0; i < getCurrent().list.size(); i++) {
			Message msg = (Message) getCurrent().list.get(i);
			if (!msg.isError) {
				// sb.append(j);
				// sb.append(",");
				sb.append("Warning:");
				sb.append(msg.Message);
				sb.append("\n");
				// LogUtil.getLogger().warning(msg.StackTrace);
			}
		}
		// throw new RuntimeException("s");
		getCurrent().ErrorDealedFlag = true;
		return sb.toString();
	}

	/**
	 * �������е���Ϣ���ַ�������ʽ
	 * 
	 * @return
	 */
	public static String[] getMessages() {
		String[] arr = new String[getCurrent().list.size()];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = ((Message) getCurrent().list.get(i)).Message;
		}
		((Errorx) getCurrent()).clear();
		return arr;
	}

	/**
	 * ���ı���ʽ����������Ϣ
	 */
	public static String getAllMessage() {
		StringBuffer sb = new StringBuffer();
		int index = 1;
		for (int i = 0; i < getCurrent().list.size(); i++) {
			Message msg = ((Message) getCurrent().list.get(i));
			if (msg.isError) {
				sb.append("\n" + index + "��Error: " + msg.Message);
				index++;
			}
		}
		for (int i = 0; i < getCurrent().list.size(); i++) {
			Message msg = ((Message) getCurrent().list.get(i));
			if (!msg.isError) {
				sb.append("\n" + index + "��Warning: " + msg.Message);
				index++;
			}
		}
		((Errorx) getCurrent()).clear();
		return sb.toString();
	}

	/**
	 * ���ε���֮ǰ�ı����ʼ��
	 */
	public static void init() {
		Object t = ErrorXLocal.get();
		if (t == null || !Errorx.class.isInstance(t)) {
			t = new Errorx();
		} else {
			((Errorx) t).clear();
		}
	}

	/**
	 * ���ص�ǰ��Errorx����
	 * 
	 * @return
	 */
	public static Errorx getCurrent() {
		Object t = ErrorXLocal.get();
		if (t == null || !Errorx.class.isInstance(t)) {
			t = new Errorx();
			ErrorXLocal.set(t);
		}
		return (Errorx) t;
	}

	/**
	 * ��Ϣ������
	 * 
	 * @author NSWT
	 * @date 2009-11-15
	 * @email nswt@nswt.com.cn
	 */
	static class Message {
		/**
		 * �Ƿ��Ǵ���
		 */
		public boolean isError;

		/**
		 * ��Ϣ����
		 */
		public String Message;

		/**
		 * ��ջ��Ϣ
		 */
		public String StackTrace;
	}
}
