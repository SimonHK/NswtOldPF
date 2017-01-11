package com.nswt.framework.utility;

import java.util.ArrayList;

import org.apache.commons.lang.ArrayUtils;

/**
 * ����${A}��ʽ�ļ���������ʽ�������������˳���������ʽ��Ҫת��̫���ַ���ë��<br>
 * <br>
 * �ڼ��ױ��ʽ�У�<font color='red'>${T:Name}</font>��ʾһ��ռλ��������:<br>
 * <font color='red'> T </font>��ʾռλ������,<font color='red'> Name
 * </font>��ʾռλ�����������ƣ�����֮��õ�һ��Mapx������ЩMapx�п���ͨ��Name����ƥ�䵽��ֵ��
 * T����A,D,W,-D,-W�������ͣ�����A��ʾ.*?��D��ʾ\d*?��W��ʾ���ⳤ�ȵ�\w*?,-D��ʾ\D*?,-W��ʾ\W*?<br>
 * 
 * ���⻹����ʹ��<font color='red'> ${{Pattern:Name}} </font>�ķ�ʽֱ��ʹ�ü���������ʽ
 * ֧��${(AB|CD|E)3+:Name}��${[ab]+:Name}����д������һ��д������һ���ַ���֮��Ļ��ϵ<br>
 * �ڶ���д������һ���ַ��Ļ��ϵ
 * 
 * @Author ������
 * @Date 2007-9-6
 * @Mail nswt@nswt.com.cn
 */
public class RegexParser {
	private String regex;

	private int currentPos;

	private String text;

	private String orginalText;

	private ArrayList list = new ArrayList(16);

	private ArrayList groups = null;// ����ƥ����,����δ�����Ľ��

	private Mapx map = null;// ����ƥ�����ֵӳ��

	private int startPos = 0;

	private boolean caseIngore = true;

	private boolean lineWrapIngore = true;

	public RegexParser(String regex) {
		this(regex, true, true);
	}

	public RegexParser(String regex, boolean caseIngoreFlag, boolean lineWrapIngoreFlag) {
		this.regex = caseIngoreFlag ? regex.toLowerCase() : regex;
		caseIngore = caseIngoreFlag;
		lineWrapIngore = lineWrapIngoreFlag;
		parse();
	}

	public String getText() {
		return orginalText;
	}

	public synchronized void setText(String text) {
		orginalText = text;
		this.text = caseIngore ? text.toLowerCase() : text;
		currentPos = 0;// ���¿�ʼƥ��
	}

	private void parse() {
		if (StringUtil.isEmpty(regex)) {
			throw new RuntimeException("����������ʽ����Ϊ��!");
		}
		int lastIndex = 0;
		while (true) {
			int start = regex.indexOf("${", lastIndex);
			if (start < 0) {
				break;
			}
			int end = regex.indexOf("}", start);
			if (end < 0) {
				break;
			}
			String previous = regex.substring(lastIndex, start);
			if (StringUtil.isNotEmpty(previous)) {
				if (lineWrapIngore) {
					String[] arr = previous.split("\\n");
					for (int i = 0; i < arr.length; i++) {
						if (StringUtil.isNotEmpty(arr[i])) {
							list.add(arr[i].trim());
						}
					}
				} else {
					list.add(previous);
				}
			}
			String item = regex.substring(start + 2, end);
			list.add(new RegexItem(item));
			lastIndex = end + 1;
		}
		if (lastIndex != regex.length() - 1) {
			String str = regex.substring(lastIndex);
			if (lineWrapIngore) {
				String[] arr = str.split("\\n");
				for (int i = 0; i < arr.length; i++) {
					if (StringUtil.isNotEmpty(arr[i])) {
						list.add(arr[i].trim());
					}
				}
			} else {
				list.add(str);
			}
		}
	}

	public synchronized boolean match() {
		if (currentPos == text.length()) {
			return false;// �Ѿ����˾�ͷ
		}
		boolean matchFlag = true;
		map = new CaseIgnoreMapx();
		groups = new ArrayList(16);
		startPos = currentPos;
		for (int i = 0; i < list.size(); i++) {
			Object obj = list.get(i);
			if (obj instanceof String) {
				String item = (String) obj;
				int index = text.indexOf(item, currentPos);
				if (index < 0) {
					matchFlag = false;
					break;
				}
				if (i != 0 && index != currentPos) {// ����ʼʱ���⣬��������ƥ�����Ҫ��������һ��
					matchFlag = false;
					break;
				}
				if (i == 0) {
					startPos = index;
				}
				currentPos = index + item.length();
				if (lineWrapIngore) {
					for (int j = currentPos; j < text.length(); j++) {
						char c = text.charAt(j);
						if (c != '\n' && c != '\t' && c != '\r' && c != ' ' && c != '\b' && c != '\f') {
							currentPos = j;
							break;
						}
					}
				}
			} else {
				RegexItem item = (RegexItem) obj;
				int pos = currentPos;
				int count = 0;
				while (true) {
					if (i == 0 && count == 0) {// ȷ����ʼƥ��λ��
						while (true) {
							int index = item.match(text, pos, 0);
							if (pos == text.length() - 1) {
								return false;
							}
							if (index >= 0) {
								startPos = pos;
								pos = index;
								break;
							}
							pos++;
						}
					}
					pos = item.match(text, currentPos, count);// �˴�����ƥ�����λ��
					count++;
					if (pos < 0) {
						return false;
					}
					if (!item.greaterFlag || i == list.size() - 1) {// ָ���˴������������һ��
						break;
					} else {// ����ƥ����һ��
						obj = list.get(i + 1);
						if (obj instanceof String) {
							int index = text.indexOf(obj.toString(), pos);
							if (index == -1) {
								return false;
							}
							if (index != pos) {// ���δƥ�䣬���ٴγ���
								if (item.parts == null) {// ˵�������ַ�ƥ��
									count += index - pos - 1;
								}
								continue;
							} else {
								break;
							}
						} else {
							RegexItem nextItem = (RegexItem) obj;
							int nextPos = nextItem.match(text, pos, 0);// ��һ��ƥ����϶����ǿ�ʼ��
							if (nextPos < 0) {
								continue;
							} else {
								break;
							}
						}
					}
				}
				try {
					String str = this.orginalText.substring(currentPos, pos);
					if (StringUtil.isNotEmpty(item.getName())) {
						map.put(item.getName(), str);
					}
					groups.add(str);
				} catch (Exception e) {
					e.printStackTrace();
				}
				currentPos = pos;
			}
		}
		if (!matchFlag) {
			map = null;
			groups = null;
			if (currentPos > 0 && startPos != currentPos) {// ˵������ǰ����ռλ�������ļ��б�ƥ�䵽����Ҫ�������Ժ�����ı�
				return match();
			}
		}
		return matchFlag;
	}

	public String replace(String content, String replacement) {
		this.setText(content);
		StringBuffer sb = new StringBuffer();
		int lastIndex = 0;
		while (this.match()) {
			sb.append(this.orginalText.substring(lastIndex, startPos));
			sb.append(replacement);
			lastIndex = currentPos;
		}
		sb.append(this.orginalText.substring(lastIndex));
		return sb.toString();
	}

	public String[] getGroups() {
		if (groups == null) {
			return null;
		}
		String[] arr = new String[groups.size()];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (String) groups.get(i);
		}
		return arr;
	}

	public Mapx getMapx() {
		return map;
	}

	public String getMacthedText() {
		return this.orginalText.substring(startPos, currentPos);
	}

	static class RegexItem {
		private String name;

		private String expr;

		private char[] charArr;

		private boolean exclusive;// �ų�ģʽ

		private boolean greaterFlag = true;

		private int needCount = 0;

		private String[] parts;

		public RegexItem(String item) {
			int index = item.lastIndexOf(":");
			if (index >= 0) {// �п���û������
				name = item.substring(index + 1);
				expr = item.substring(0, index);
			} else {
				expr = item;
			}
			if (StringUtil.isEmpty(expr)) {
				throw new RuntimeException("����ȷ�ļ�������ʽռλ����" + item);
			}
			// �Ƚ����ּ���д�������һ��д��
			if (expr.equalsIgnoreCase("A")) {
				expr = "-[]*";
			} else if (expr.equalsIgnoreCase("D")) {
				expr = "[\\d]*";
			} else if (expr.equalsIgnoreCase("-D")) {
				expr = "-[\\d]*";
			} else if (expr.equalsIgnoreCase("W")) {
				expr = "[\\w]*";
			} else if (expr.equalsIgnoreCase("-W")) {
				expr = "-[\\w]*";
			}
			if (expr.startsWith("(")) {// �ַ�����ϣ��ַ���������в�֧�ֱ��ʽ
				index = expr.lastIndexOf(")");
				if (index < expr.length() - 1) {
					String tail = expr.substring(index + 1);
					if (tail.equals("")) {
						greaterFlag = false;
						needCount = 1;
					} else if (tail.equals("*")) {
						greaterFlag = true;
						needCount = 0;
					} else if (tail.equals("+")) {
						greaterFlag = true;
						needCount = 1;
					} else if (tail.endsWith("+")) {// ����3+,��ʾ3������
						greaterFlag = true;
						try {
							needCount = Integer.parseInt(tail.substring(0, tail.length() - 1));
						} catch (Exception e) {
							throw new RuntimeException("����ȷ�ļ�������ʽռλ����" + item);
						}
					} else {// ָ������
						greaterFlag = false;
						try {
							needCount = Integer.parseInt(tail.substring(0, tail.length()));
						} catch (Exception e) {
							throw new RuntimeException("����ȷ�ļ�������ʽռλ����" + expr);
						}
					}
				}
				expr = expr.substring(1, index);
				// ��ʼ���
				ArrayList list = new ArrayList();
				String[] arr = StringUtil.splitEx(expr, "|");
				for (int i = arr.length - 1; i >= 0; i--) {// ��Ҫȥ���յ�Ԫ���Ա����ж�ת��
					if (StringUtil.isEmpty(arr[i])) {
						ArrayUtils.remove(arr, i);
					}
				}
				for (int i = 0; i < arr.length; i++) {
					if (arr[i].endsWith("\\")) {// ˵����ת��
						if (i == arr.length - 1) {// �����ת����
							throw new RuntimeException("����ȷ�ļ�������ʽռλ����" + item);
						}
						String str = arr[i] + "|" + arr[i + 1];
						list.add(str);
						i++;
					} else {
						String str = arr[i];
						list.add(str);
					}
				}
				arr = new String[list.size()];
				for (int i = 0; i < list.size(); i++) {
					arr[i] = (String) list.get(i);
				}
				parts = arr;
			} else {// ���ʽ
				if (expr.startsWith("-")) {
					exclusive = true;
					expr = expr.substring(1);
				}
				int end = expr.lastIndexOf("]");
				int start = expr.indexOf("[");
				if (start != 0) {// �����Ǳ��ʽ
					throw new RuntimeException("����ȷ�ļ�������ʽռλ����" + expr);
				}
				if (start < 0 || end < 0 || end < start) {
					throw new RuntimeException("����ȷ�ļ�������ʽռλ����" + expr);
				}
				String content = expr.substring(start + 1, end);
				if (end < expr.length() - 1) {
					// ���½���ƥ�����
					String tail = expr.substring(end + 1);
					if (tail.equals("")) {
						greaterFlag = false;
						needCount = 1;
					} else if (tail.equals("*")) {
						greaterFlag = true;
						needCount = 0;
					} else if (tail.equals("+")) {
						greaterFlag = true;
						needCount = 1;
					} else if (tail.endsWith("+")) {// ����3+,��ʾ3������
						greaterFlag = true;
						try {
							needCount = Integer.parseInt(tail.substring(0, tail.length() - 1));
						} catch (Exception e) {
							throw new RuntimeException("����ȷ�ļ�������ʽռλ����" + expr);
						}
					} else {// ָ������
						greaterFlag = false;
						try {
							needCount = Integer.parseInt(tail.substring(0, tail.length()));
						} catch (Exception e) {
							throw new RuntimeException("����ȷ�ļ�������ʽռλ����" + expr);
						}
					}
				}
				StringBuffer sb = new StringBuffer(128);
				boolean flag = false;// ת���־
				for (int i = 0; i < content.length(); i++) {
					char c = content.charAt(i);
					if (flag) {
						if (c == 'd') {
							sb.append("0123456789");
						} else if (c == 'w') {
							sb.append("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_0123456789-");
						} else if (c == 's') {
							sb.append("\t\n\b\f\r");
						} else if (c == 't') {
							sb.append("\t");
						} else if (c == 'n') {
							sb.append("\n");
						} else if (c == 'b') {
							sb.append("\n");
						} else if (c == 'f') {
							sb.append("\f");
						} else if (c == 'r') {
							sb.append("\r");
						} else if (c == '\\') {
							sb.append("\\");
						} else {
							sb.append(c);// ���������⣬ת���������
						}
					} else if (c != '\\') {
						sb.append(c);
					}
					if (c == '\\') {
						flag = true;
					} else {
						flag = false;
					}
				}
				charArr = sb.toString().toCharArray();
			}
		}

		public int match(String text, int startPos, int extraCount) {
			int pos = startPos;
			int count = needCount + extraCount;
			for (int j = 0; j < count; j++) {
				if (pos >= text.length()) {
					return -1;
				}
				if (parts == null) {// ˵���Ǳ��ʽ
					char c = text.charAt(pos);
					boolean flag = false;
					for (int i = 0; i < charArr.length; i++) {
						if (charArr[i] == c) {
							if (this.exclusive) {// ˵��ƥ��ʧ��
								return -1;
							}
							flag = true;
							break;
						}
					}
					if (!exclusive && !flag) {
						return -1;
					}
					pos++;
				} else {
					boolean flag = false;
					for (int i = 0; i < parts.length; i++) {
						int index = text.indexOf(parts[i], pos);
						if (index == pos) {// ˵����ƥ��
							pos = index + parts[i].length();
							flag = true;
							break;
						}
					}
					if (!flag) {
						return -1;
					}
				}
			}
			return pos;
		}

		public String getName() {
			return name;
		}
	}
}
