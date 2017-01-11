package com.nswt.framework;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.nswt.framework.utility.DateUtil;

/**
 * ���������
 * 
 * @Author NSWT
 * @Date 2016-10-2
 * @Mail wyuch@m165.com
 */
public class VerifyRule {
	/**
	 * �ַ�����ʽҪ���ַ����п������κ��ַ��������ܰ�����Ǳ��Σ���Ե�SQL����Script���
	 */
	public static final String F_String = "String";

	/**
	 * �ַ�����ʽҪ���ַ����п������κ��ַ���������Ǳ��Σ���Ե�SQL����Script���
	 */
	public static final String F_Any = "Any";

	/**
	 * �ַ�����ʽҪ���ַ����������һ����ֵ���������������߸��㣬�����Ǹ����������ǿ�ѧ����
	 */
	public static final String F_Number = "Number";

	/**
	 * �ַ�����ʽҪ���ַ����������һ����Ч���ڣ���ʽΪָ����ʽ��Ĭ��ΪYYYY-MM-DD
	 */
	public static final String F_Date = "Date";

	/**
	 * �ַ�����ʽҪ���ַ����������һ����Чʱ��,��ʽΪHH:MM:SS
	 */
	public static final String F_Time = "Time";

	/**
	 * �ַ�����ʽҪ���ַ����������һ����Ч����ʱ��,��ʽΪָ����ʽ��Ĭ��ΪYYYY-MM-DD HH:MM:SS
	 */
	public static final String F_DateTime = "DateTime";

	/**
	 * �ַ�����ʽҪ���ַ����������һ����Ч��ݣ�����ʹ��AD/DC��־
	 */
	public static final String F_Year = "Year";

	/**
	 * �ַ�����ʽҪ���ַ����������һ����Ч�·�
	 */
	public static final String F_Month = "Month";

	/**
	 * �ַ�����ʽҪ���ַ����������һ����Ч��
	 */
	public static final String F_Day = "Day";

	/**
	 * �ַ�����ʽҪ���ַ����������һ������������Ϊ����
	 */
	public static final String F_Int = "Int";

	/**
	 * �ַ�����ʽҪ���ַ����������ַ�����������
	 */
	public static final String F_DigitChar = "DigitChar";

	/**
	 * �ַ�����ʽҪ���ַ����������ַ�������Ascii�ַ�
	 */
	public static final String F_AsciiChar = "AsciiChar";

	/**
	 * �ַ�����ʽҪ���ַ����������ַ���������ĸ
	 */
	public static final String F_LetterChar = "LetterChar";

	/**
	 * �ַ�����ʽҪ���ַ�����������ĸ�ַ������Ǵ�д��ĸ
	 */
	public static final String F_UpperChar = "UpperChar";

	/**
	 * �ַ�����ʽҪ���ַ�����������ĸ�ַ�������Сд��ĸ
	 */
	public static final String F_LowerChar = "LowerChar";

	/**
	 * �ַ�����ʽҪ���ַ�������Ϊ��
	 */
	public static final String F_NotNull = "NotNull";

	/**
	 * �ַ�����ʽҪ�󣺱����ǺϷ��ĵ�������
	 */
	public static final String F_Email = "Email";

	/**
	 * �ַ�����ʽҪ���ַ���ֵӦ����CodeSelect��ĳһcode�ķ�Χ֮��
	 */
	public static final String F_Code = "Code";

	/**
	 * �ַ�����ʽҪ���ַ���ֵ��ÿһ���ַ���Ӧ���ǰ���ַ����ߺ���
	 */
	public static final String F_HalfChar = "HalfChar";

	/**
	 * �ַ�����ʽҪ���ַ���ֵ��ÿһ���ַ���Ӧ����ȫ���ַ����ߺ���
	 */
	public static final String F_FullChar = "FullChar";

	/**
	 * ��ʽ�߼�������������
	 */
	public static final String O_Add = "&&";

	/**
	 * ��ʽ�߼�������������
	 */
	public static final String O_Or = "||";

	/**
	 * ��ʽ�߼���������ǲ���
	 */
	public static final String O_Not = "!";

	/**
	 * ���ԣ��Զ����ʽ��֧��*��?����ͨ���
	 */
	public static final String A_Format = "Format";

	/**
	 * ���ԣ���ʽ�������ָ����������ʽ
	 */
	public static final String A_RegFormat = "RegFormat";

	/**
	 * ���ԣ����ֵ
	 */
	public static final String A_Max = "Max";

	/**
	 * ���ԣ���Сֵ
	 */
	public static final String A_Min = "Min";

	/**
	 * ���ԣ��ַ�������
	 */
	public static final String A_Len = "Length";

	private static final String regEmail = "^[_\\-a-z0-9A-Z]*?[\\._\\-a-z0-9]*?[a-z0-9]+@[a-z0-9]+[a-z0-9\\-]*?[a-z0-9]+\\.[\\.a-z0-9]*$";

	private static Pattern patternEmail = null;

	private String Rule;

	private String[] Features;

	private ArrayList Messages;

	/**
	 * ����һ���հ�У��������
	 */
	public VerifyRule() {
	}

	/**
	 * ��������ָ�������У��������
	 * 
	 * @param rule
	 */
	public VerifyRule(String rule) {
		Rule = rule;
	}

	/**
	 * У��ָ����ֵ�Ƿ���ϱ������У�����
	 * 
	 * @param value
	 * @return
	 */
	public boolean verify(String value) {
		Messages = new ArrayList();
		Features = Rule.split("\\&\\&");
		boolean sqlFlag = true;
		boolean verifyFlag = true;
		try {
			for (int i = 0; i < Features.length; i++) {
				String op = "=";
				if (Features[i].indexOf('>') > 0) {
					op = ">";
				} else if (Features[i].indexOf('<') > 0) {
					op = "<";
				}
				String[] f = Features[i].split("\\" + op);
				String fName = f[0];
				String fValue = null;
				if (f.length > 1) {
					fValue = f[1];
				}
				if (fName.equals(VerifyRule.F_Any)) {
					sqlFlag = false;
				} else if (fName.equals(VerifyRule.F_NotNull)) {
					if (value == null || value.equals("")) {
						Messages.add("����Ϊ��");
						return false;
					}
				} else if (fName.equals(VerifyRule.F_Code)) {
					if (value == null || value.equals("")) {
						continue;
					}
				} else if (fName.equals(VerifyRule.F_Date)) {
					if (value == null || value.equals("")) {
						continue;
					}
					if (!DateUtil.isDate(value)) {
						Messages.add("������ȷ������ֵ");
						verifyFlag = false;
					}
				} else if (fName.equals(VerifyRule.F_Time)) {
					if (value == null || value.equals("")) {
						continue;
					}
					if (!DateUtil.isTime(value)) {
						Messages.add("������ȷ������ֵ");
						verifyFlag = false;
					}
				} else if (fName.equals(VerifyRule.F_DateTime)) {
					if (value == null || value.equals("")) {
						continue;
					}
					String[] arr = value.split(" ");
					if (arr.length == 1 && !DateUtil.isDate(arr[0])) {
						Messages.add("������ȷ������ֵ");
						verifyFlag = false;
					} else if (arr.length == 2) {
						if (!DateUtil.isDate(arr[0]) || !DateUtil.isTime(arr[1])) {
							Messages.add("������ȷ������ֵ");
							verifyFlag = false;
						}
					} else {
						Messages.add("������ȷ������ֵ");
						verifyFlag = false;
					}
				} else if (fName.equals(VerifyRule.F_Number)) {
					if (value == null || value.equals("")) {
						continue;
					}
					try {
						Double.parseDouble(value);
					} catch (Exception e) {
						Messages.add("������ȷ����ֵ");
						verifyFlag = false;
					}
				} else if (fName.equals(VerifyRule.F_Int)) {
					if (value == null || value.equals("")) {
						continue;
					}
					try {
						Integer.parseInt(value);
					} catch (Exception e) {
						Messages.add("������ȷ������ֵ");
						verifyFlag = false;
					}
				} else if (fName.equals(VerifyRule.F_String)) {
					if (value == null || value.equals("")) {
						continue;
					}
					if (value.indexOf('\'') >= 0 || value.indexOf('\"') >= 0) {
						Messages.add("�����ǷǷ��ַ���");
						verifyFlag = false;
					}
				} else if (fName.equals(VerifyRule.F_Email)) {
					if (value == null || value.equals("")) {
						continue;
					}
					if (patternEmail == null) {
						patternEmail = Pattern.compile(regEmail);
					}
					Matcher m = patternEmail.matcher(value);
					if (!m.find()) {
						Messages.add("������ȷ�ĵ��������ַ");
						verifyFlag = false;
					}
				} else if (fName.equals(VerifyRule.A_Len)) {
					if (value == null || value.equals("")) {
						continue;
					}
					if (fValue == null || fValue.equals("")) {
						throw new RuntimeException("У��������Length�������������");
					} else {
						try {
							int len = Integer.parseInt(fValue);
							if (op.equals("=") && value.length() != len) {
								Messages.add("���ȱ�����" + len);
								verifyFlag = false;
							} else if (op.equals(">") && value.length() <= len) {
								Messages.add("���ȱ������" + len);
								verifyFlag = false;
							} else if (op.equals("<") && value.length() >= len) {
								Messages.add("���ȱ���С��" + len);
								verifyFlag = false;
							}
						} catch (Exception e) {
							e.printStackTrace();
							throw new RuntimeException("У��������Length�������������");
						}
					}
				}
			}
			if (sqlFlag) {// У��SQL����
				if (value != null) {
					if ((value.indexOf(" and ") > 0 || value.indexOf(" or ") > 0)
							&& (value.indexOf('!') > 0 || value.indexOf(" like ") > 0 || value.indexOf('=') > 0
									|| value.indexOf('>') > 0 || value.indexOf('<') > 0)) {
						Messages.add("�벻Ҫ��������SQL���!");
						verifyFlag = false;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("����ȷ��У�����:" + Rule);
		}
		if (sqlFlag) {
			if (!checkSQL(value)) {
				verifyFlag = false;
			}
		}
		return verifyFlag;
	}

	/**
	 * ����У����
	 * 
	 * @param fieldName
	 * @return
	 */
	public String getMessages(String fieldName) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < Messages.size(); i++) {
			sb.append(fieldName);
			sb.append(":");
			sb.append(Messages.get(i));
			sb.append("\n");
		}
		return sb.toString();
	}

	/**
	 * ���ָ��ֵ�Ƿ���һ��SQL��䣨δʵ�֣�
	 * 
	 * @param value
	 * @return
	 */
	private boolean checkSQL(String value) {
		return true;
	}

	/**
	 * ���ر������У�����
	 * 
	 * @return
	 */
	protected String getRule() {
		return Rule;
	}

	/**
	 * ���ñ������У�����
	 * 
	 * @param rule
	 */
	protected void setRule(String rule) {
		Rule = rule;
	}

	public static void main(String[] args) {
		VerifyRule rule = new VerifyRule();
		rule.setRule("Email");
		System.out.println(rule.verify("wyuch_.-2@m165-a.com"));
		System.out.println(rule.getMessages("��������"));
	}
}
