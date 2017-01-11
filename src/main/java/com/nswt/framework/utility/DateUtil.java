package com.nswt.framework.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * ���ڹ�����
 * 
 * ����: NSWT<br>
 * ���ڣ�2016-7-6<br>
 * �ʼ���nswt@nswt.com.cn<br>
 */
public class DateUtil {
	/**
	 * ȫ��Ĭ�����ڸ�ʽ
	 */
	public static final String Format_Date = "yyyy-MM-dd";

	/**
	 * ȫ��Ĭ��ʱ���ʽ
	 */
	public static final String Format_Time = "HH:mm:ss";

	/**
	 * ȫ��Ĭ������ʱ���ʽ
	 */
	public static final String Format_DateTime = "yyyy-MM-dd HH:mm:ss";

	/**
	 * �õ���yyyy-MM-dd��ʽ��ʾ�ĵ�ǰ�����ַ���
	 */
	public static String getCurrentDate() {
		return new SimpleDateFormat(Format_Date).format(new Date());
	}

	/**
	 * �õ���format��ʽ��ʾ�ĵ�ǰ�����ַ���
	 */
	public static String getCurrentDate(String format) {
		SimpleDateFormat t = new SimpleDateFormat(format);
		return t.format(new Date());
	}

	/**
	 * �õ���HH:mm:ss��ʾ�ĵ�ǰʱ���ַ���
	 */
	public static String getCurrentTime() {
		return new SimpleDateFormat(Format_Time).format(new Date());
	}

	/**
	 * �õ���format��ʽ��ʾ�ĵ�ǰʱ���ַ���
	 */
	public static String getCurrentTime(String format) {
		SimpleDateFormat t = new SimpleDateFormat(format);
		return t.format(new Date());
	}

	/**
	 * �õ���yyyy-MM-dd HH:mm:ss��ʾ�ĵ�ǰʱ���ַ���
	 */
	public static String getCurrentDateTime() {
		String format = DateUtil.Format_Date + " " + DateUtil.Format_Time;
		return getCurrentDateTime(format);
	}

	/**
	 * ���������ڼ�
	 * 
	 * @return
	 */
	public static int getDayOfWeek() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * ָ�����������ڼ�
	 * 
	 * @param date
	 * @return
	 */
	public static int getDayOfWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * �����Ǳ��µĵڼ���
	 * 
	 * @return
	 */
	public static int getDayOfMonth() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * ָ�������ǵ��µĵڼ���
	 * 
	 * @param date
	 * @return
	 */
	public static int getDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * ��ȡĳһ���µ�����
	 * 
	 * @param date
	 * @return
	 */
	public static int getMaxDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.getActualMaximum(Calendar.DATE);
	}

	/**
	 * ��yyyy-MM-dd��ʽ��ȡĳ�µĵ�һ��
	 * 
	 * @param date
	 * @return
	 */
	public static String getFirstDayOfMonth(String date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(parse(date));
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return new SimpleDateFormat(Format_Date).format(cal.getTime());
	}

	/**
	 * �����Ǳ���ĵڼ���
	 * 
	 * @return
	 */
	public static int getDayOfYear() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.DAY_OF_YEAR);
	}

	/**
	 * ָ�������ǵ���ĵڼ���
	 * 
	 * @param date
	 * @return
	 */
	public static int getDayOfYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_YEAR);
	}

	/**
	 * ��yyyy-MM-dd�����ַ���date�����������ʾ���������ܼ�
	 * 
	 * @param date
	 * @return
	 */
	public static int getDayOfWeek(String date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(parse(date));
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * ��yyyy-MM-dd�����ַ���date�����������ʾ�������ǵ��µڼ���
	 * 
	 * @param date
	 * @return
	 */
	public static int getDayOfMonth(String date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(parse(date));
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * ��yyyy-MM-dd�����ַ���date�����������ʾ�������ǵ���ڼ���
	 * 
	 * @param date
	 * @return
	 */
	public static int getDayOfYear(String date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(parse(date));
		return cal.get(Calendar.DAY_OF_YEAR);
	}

	/**
	 * ��ָ���ĸ�ʽ���ص�ǰ����ʱ����ַ���
	 * 
	 * @param format
	 * @return
	 */
	public static String getCurrentDateTime(String format) {
		SimpleDateFormat t = new SimpleDateFormat(format);
		return t.format(new Date());
	}

	/**
	 * ��yyyy-MM-dd��ʽ���ֻ�����ڵ��ַ���
	 */
	public static String toString(Date date) {
		if (date == null) {
			return "";
		}
		return new SimpleDateFormat(Format_Date).format(date);
	}

	/**
	 * ��yyyy-MM-dd HH:mm:ss����������ں�ʱ����ַ���
	 */
	public static String toDateTimeString(Date date) {
		if (date == null) {
			return "";
		}
		return new SimpleDateFormat(Format_DateTime).format(date);
	}

	/**
	 * ��ָ����format��������ַ���
	 */
	public static String toString(Date date, String format) {
		SimpleDateFormat t = new SimpleDateFormat(format);
		return t.format(date);
	}

	/**
	 * ��HH:mm:ss���ֻ��ʱ����ַ���
	 */
	public static String toTimeString(Date date) {
		if (date == null) {
			return "";
		}
		return new SimpleDateFormat(Format_Time).format(date);
	}

	/**
	 * ��yyyy-MM-dd���������ַ��������Ƚϵõ����������ڵĴ�С
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int compare(String date1, String date2) {
		return compare(date1, date2, Format_Date);
	}

	/**
	 * ��HH:mm:ss���������ַ��������Ƚϵõ�������ʱ��Ĵ�С
	 * 
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static int compareTime(String time1, String time2) {
		return compareTime(time1, time2, Format_Time);
	}

	/**
	 * ��ָ����ʽ���������ַ��������Ƚϵõ����������ڵĴ�С
	 * 
	 * @param date1
	 * @param date2
	 * @param format
	 * @return
	 */
	public static int compare(String date1, String date2, String format) {
		Date d1 = parse(date1, format);
		Date d2 = parse(date2, format);
		return d1.compareTo(d2);
	}

	/**
	 * ��ָ�����������ַ��������Ƚϵõ�������ʱ��Ĵ�С
	 * 
	 * @param time1
	 * @param time2
	 * @param format
	 * @return
	 */
	public static int compareTime(String time1, String time2, String format) {
		String[] arr1 = time1.split(":");
		String[] arr2 = time2.split(":");
		if (arr1.length < 2) {
			throw new RuntimeException("�����ʱ��ֵ:" + time1);
		}
		if (arr2.length < 2) {
			throw new RuntimeException("�����ʱ��ֵ:" + time2);
		}
		int h1 = Integer.parseInt(arr1[0]);
		int m1 = Integer.parseInt(arr1[1]);
		int h2 = Integer.parseInt(arr2[0]);
		int m2 = Integer.parseInt(arr2[1]);
		int s1 = 0, s2 = 0;
		if (arr1.length == 3) {
			s1 = Integer.parseInt(arr1[2]);
		}
		if (arr2.length == 3) {
			s2 = Integer.parseInt(arr2[2]);
		}
		if (h1 < 0 || h1 > 23 || m1 < 0 || m1 > 59 || s1 < 0 || s1 > 59) {
			throw new RuntimeException("�����ʱ��ֵ:" + time1);
		}
		if (h2 < 0 || h2 > 23 || m2 < 0 || m2 > 59 || s2 < 0 || s2 > 59) {
			throw new RuntimeException("�����ʱ��ֵ:" + time2);
		}
		if (h1 != h2) {
			return h1 > h2 ? 1 : -1;
		} else {
			if (m1 == m2) {
				if (s1 == s2) {
					return 0;
				} else {
					return s1 > s2 ? 1 : -1;
				}
			} else {
				return m1 > m2 ? 1 : -1;
			}
		}
	}

	/**
	 * �ж�ָ�����ַ����Ƿ����HH:mm:ss��ʽ�����ж�����ֵ�Ƿ���������Χ
	 * 
	 * @param time
	 * @return
	 */
	public static boolean isTime(String time) {
		String[] arr = time.split(":");
		if (arr.length < 2) {
			return false;
		}
		try {
			int h = Integer.parseInt(arr[0]);
			int m = Integer.parseInt(arr[1]);
			int s = 0;
			if (arr.length == 3) {
				s = Integer.parseInt(arr[2]);
			}
			if (h < 0 || h > 23 || m < 0 || m > 59 || s < 0 || s > 59) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * �ж�ָ�����ַ����Ƿ����yyyy:MM:ss��ʽ�����ж�������ֵ��Χ�Ƿ�����
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isDate(String date) {
		String[] arr = date.split("-");
		if (arr.length < 3) {
			return false;
		}
		try {
			int y = Integer.parseInt(arr[0]);
			int m = Integer.parseInt(arr[1]);
			int d = Integer.parseInt(arr[2]);
			if (y < 0 || m > 12 || m < 0 || d < 0 || d > 31) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * �ж��Ƿ������ڻ��ߴ�ʱ������ڣ����ڱ�����ϸ�ʽyy-MM-dd��yy-MM-dd HH:mm:ss
	 */
	public static boolean isDateTime(String str) {
		if (StringUtil.isEmpty(str)) {
			return false;
		}
		if (str.indexOf(" ") > 0) {
			String[] arr = str.split(" ");
			if (arr.length == 2) {
				return isDate(arr[0]) && isTime(arr[1]);
			} else {
				return false;
			}
		} else {
			return isDate(str);
		}
	}

	/**
	 * �ж�ָ�������Ƿ�����ĩ
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isWeekend(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int t = cal.get(Calendar.DAY_OF_WEEK);
		if (t == Calendar.SATURDAY || t == Calendar.SUNDAY) {
			return true;
		}
		return false;
	}

	/**
	 * ��yyyy-MM-dd����ָ���ַ��������ж���Ӧ�������Ƿ�����ĩ
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isWeekend(String str) {
		return isWeekend(parse(str));
	}

	/**
	 * ��yyyy-MM-dd����ָ���ַ�����������Ӧjava.util.Date����
	 * 
	 * @param str
	 * @return
	 */
	public static Date parse(String str) {
		if (StringUtil.isEmpty(str)) {
			return null;
		}
		try {
			return new SimpleDateFormat(Format_Date).parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * ��ָ����ʽ�����ַ�������������Ӧ��java.util.Date����
	 * 
	 * @param str
	 * @param format
	 * @return
	 */
	public static Date parse(String str, String format) {
		if (StringUtil.isEmpty(str)) {
			return null;
		}
		try {
			SimpleDateFormat t = new SimpleDateFormat(format);
			return t.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * ��yyyy-MM-dd HH:mm:ss��ʽ�����ַ�������������Ӧ��java.util.Date����
	 * 
	 * @param str
	 * @return
	 */
	public static Date parseDateTime(String str) {
		if (StringUtil.isEmpty(str)) {
			return null;
		}
		if (str.length() <= 10) {
			return parse(str);
		}
		try {
			return new SimpleDateFormat(Format_DateTime).parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * ��ָ����ʽ�����ַ�������������Ӧ��java.util.Date����
	 * 
	 * @param str
	 * @param format
	 * @return
	 */
	public static Date parseDateTime(String str, String format) {
		if (StringUtil.isEmpty(str)) {
			return null;
		}
		try {
			SimpleDateFormat t = new SimpleDateFormat(format);
			return t.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * ����date�ϼ�count���ӣ�countΪ����ʾ��
	 */
	public static Date addMinute(Date date, int count) {
		return new Date(date.getTime() + 60000L * count);
	}

	/**
	 * ����date�ϼ�countСʱ��countΪ����ʾ��
	 */
	public static Date addHour(Date date, int count) {
		return new Date(date.getTime() + 3600000L * count);
	}

	/**
	 * ����date�ϼ�count�죬countΪ����ʾ��
	 */
	public static Date addDay(Date date, int count) {
		return new Date(date.getTime() + 86400000L * count);
	}

	/**
	 * ����date�ϼ�count���ڣ�countΪ����ʾ��
	 */
	public static Date addWeek(Date date, int count) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.WEEK_OF_YEAR, count);
		return c.getTime();
	}

	/**
	 * ����date�ϼ�count�£�countΪ����ʾ��
	 */
	public static Date addMonth(Date date, int count) {
		/* ${_NSWT_LICENSE_CODE_} */

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, count);
		return c.getTime();
	}

	/**
	 * ����date�ϼ�count�꣬countΪ����ʾ��
	 */
	public static Date addYear(Date date, int count) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.YEAR, count);
		return c.getTime();
	}

	/**
	 * ���Ի���ʾʱ������,date��ʽΪ��yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String toDisplayDateTime(String date) {
		if (StringUtil.isEmpty(date)) {
			return null;
		}
		try {
			if (isDate(date)) {
				return toDisplayDateTime(parse(date));
			} else {
				SimpleDateFormat t = new SimpleDateFormat(Format_DateTime);
				Date d = t.parse(date);
				return toDisplayDateTime(d);
			}

		} catch (ParseException e) {
			e.printStackTrace();
			return "���Ǳ�׼��ʽʱ��!";
		}
	}

	/**
	 * �������е���������ת���ɰ��������֣�������ʹ��ָ����ʽ����
	 */
	public static String convertChineseNumber(String strDate) {
		strDate = StringUtil.replaceEx(strDate, "һʮһ", "11");
		strDate = StringUtil.replaceEx(strDate, "һʮ��", "12");
		strDate = StringUtil.replaceEx(strDate, "һʮ��", "13");
		strDate = StringUtil.replaceEx(strDate, "һʮ��", "14");
		strDate = StringUtil.replaceEx(strDate, "һʮ��", "15");
		strDate = StringUtil.replaceEx(strDate, "һʮ��", "16");
		strDate = StringUtil.replaceEx(strDate, "һʮ��", "17");
		strDate = StringUtil.replaceEx(strDate, "һʮ��", "18");
		strDate = StringUtil.replaceEx(strDate, "һʮ��", "19");
		strDate = StringUtil.replaceEx(strDate, "��ʮһ", "21");
		strDate = StringUtil.replaceEx(strDate, "��ʮ��", "22");
		strDate = StringUtil.replaceEx(strDate, "��ʮ��", "23");
		strDate = StringUtil.replaceEx(strDate, "��ʮ��", "24");
		strDate = StringUtil.replaceEx(strDate, "��ʮ��", "25");
		strDate = StringUtil.replaceEx(strDate, "��ʮ��", "26");
		strDate = StringUtil.replaceEx(strDate, "��ʮ��", "27");
		strDate = StringUtil.replaceEx(strDate, "��ʮ��", "28");
		strDate = StringUtil.replaceEx(strDate, "��ʮ��", "29");
		strDate = StringUtil.replaceEx(strDate, "ʮһ", "11");
		strDate = StringUtil.replaceEx(strDate, "ʮ��", "12");
		strDate = StringUtil.replaceEx(strDate, "ʮ��", "13");
		strDate = StringUtil.replaceEx(strDate, "ʮ��", "14");
		strDate = StringUtil.replaceEx(strDate, "ʮ��", "15");
		strDate = StringUtil.replaceEx(strDate, "ʮ��", "16");
		strDate = StringUtil.replaceEx(strDate, "ʮ��", "17");
		strDate = StringUtil.replaceEx(strDate, "ʮ��", "18");
		strDate = StringUtil.replaceEx(strDate, "ʮ��", "19");
		strDate = StringUtil.replaceEx(strDate, "ʮ", "10");
		strDate = StringUtil.replaceEx(strDate, "��ʮ", "20");
		strDate = StringUtil.replaceEx(strDate, "��ʮ", "20");
		strDate = StringUtil.replaceEx(strDate, "��ʮһ", "31");
		strDate = StringUtil.replaceEx(strDate, "��", "0");
		strDate = StringUtil.replaceEx(strDate, "��", "0");
		strDate = StringUtil.replaceEx(strDate, "һ", "1");
		strDate = StringUtil.replaceEx(strDate, "��", "2");
		strDate = StringUtil.replaceEx(strDate, "��", "3");
		strDate = StringUtil.replaceEx(strDate, "��", "4");
		strDate = StringUtil.replaceEx(strDate, "��", "5");
		strDate = StringUtil.replaceEx(strDate, "��", "6");
		strDate = StringUtil.replaceEx(strDate, "��", "7");
		strDate = StringUtil.replaceEx(strDate, "��", "8");
		strDate = StringUtil.replaceEx(strDate, "��", "9");
		return strDate;
	}

	/**
	 * ���Ի���ʾʱ������
	 * 
	 * @param date
	 * @return
	 */
	public static String toDisplayDateTime(Date date) {
		long minite = (System.currentTimeMillis() - date.getTime()) / 60000L;
		if (minite < 60) {
			return toString(date, "MM-dd") + " " + minite + "����ǰ";
		}
		if (minite < 60 * 24) {
			return toString(date, "MM-dd") + " " + minite / 60L + "Сʱǰ";
		} else {
			return toString(date, "MM-dd") + " " + minite / 1440L + "��ǰ";
		}
	}
}
