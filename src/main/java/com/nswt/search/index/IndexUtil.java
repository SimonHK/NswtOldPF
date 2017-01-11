package com.nswt.search.index;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.wltea.analyzer.IKSegmentation;
import org.wltea.analyzer.Lexeme;
import org.wltea.analyzer.lucene.IKAnalyzer;
import org.wltea.analyzer.lucene.IKQueryParser;

import com.nswt.framework.utility.FileUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.NumberUtil;
import com.nswt.framework.utility.StringUtil;
import com.nswt.search.HtmlTextExtracter;

/**
 * ���� : 2009-11-2 <br>
 * ����: NSWT <br>
 * ���䣺nswt@nswt.com.cn <br>
 */
public class IndexUtil {

	public static String getTextFromHtml(String html) {
		String text = HtmlTextExtracter.getPureText(html);
		if (StringUtil.isEmpty(text)) {
			text = StringUtil.clearHtmlTag(html);
		}
		return text.replaceAll("[\\s\\u0020��]{2,}", " ");
	}

	public static String[] getKeyword(String title, String content) {
		int weight = 5;// ����Ȩ��Ϊ5
		for (int i = 0; i < weight; i++) {
			content = title + " " + content;
		}
		content = getTextFromHtml(content);
		IKSegmentation seg = new IKSegmentation(new StringReader(content), true);
		Mapx map = new Mapx();
		ArrayList list = new ArrayList();
		try {
			Lexeme word = seg.next();
			while (word != null) {// �������дʵĴ���
				String k = word.getLexemeText();
				if (k != null && k.length() != 1) {
					if (map.containsKey(k)) {
						map.put(k, new Integer(((Integer) map.get(word.getLexemeText())).intValue() + 1));
					} else {
						map.put(k, new Integer(1));
					}
				}
				word = seg.next();
			}
			// ĿǰIK��������С�зַ���һ���ʱ����ֳ��������������һ�Ǳ�������ɴʣ�����������һ�����ʵ�һ���֡�
			// ��ˣ���Ҫ��һ�����Ĵ����г��ļ����̴ʵĴ�������
			Object[] ks = map.keyArray();
			Object[] vs = map.valueArray();
			ArrayList arr = new ArrayList();
			for (int i = 0; i < ks.length; i++) {
				String k = ks[i].toString();
				if (!filter(k)) {
					continue;
				}
				int count = ((Integer) vs[i]).intValue();
				boolean notContainFlag = true;
				// ������ʰ��������ʣ��������һ
//				for (int j = 0; j < ks.length; j++) {
//					if (j != i) {
//						if (k.indexOf(ks[j].toString()) >= 0) {
//							int otherCount = ((Integer) vs[j]).intValue();
//							count = count + otherCount;
//						}
//					}
//				}
//				// ����������������г��ֹ����򲻼���
//				
//				for (int j = 0; j < ks.length; j++) {
//					if (j != i) {
//						if (ks[j].toString().indexOf(k) >= 0) {
//							notContainFlag = false;
//							break;
//						}
//					}
//				}
				if (notContainFlag) {
					arr.add(new Object[] { k, new Integer(count) });
				}
			}
			Collections.sort(arr, new Comparator() {
				public int compare(Object o1, Object o2) {
					Object[] arr1 = (Object[]) o1;
					Object[] arr2 = (Object[]) o2;
					Integer i1 = (Integer) arr1[1];
					Integer i2 = (Integer) arr2[1];
					return i2.intValue() - i1.intValue();
				}
			});
			for (int i = 0; i < arr.size(); i++) {
				Object[] wordArr = (Object[]) arr.get(i);
				String k = wordArr[0].toString();
				int count = ((Integer) wordArr[1]).intValue();
				if (count == 1 || list.contains(k)) {
					continue;
				}
				if (list.size() < 3) {// ���Ǿ���ֵ
					list.add(k);
				} else if (list.size() == 3) {
					if (count > 15) {
						list.add(k);
					}
				} else if (list.size() == 4) {
					if (count > 20) {
						list.add(k);
					}
				} else {
					break;
				}
				if (i < 10) {
				}
			}
			if (list.size() > 0 && list.size() <= 3 && arr.size() > list.size()) {
				int lastCount = ((Integer) ((Object[]) arr.get(list.size() - 1))[1]).intValue();
				for (int i = list.size(); i < 5 && i < arr.size(); i++) {
					Object[] wordArr = (Object[]) arr.get(i);
					int count = ((Integer) wordArr[1]).intValue();
					if (count >= lastCount - 1 && !list.contains(wordArr[0])) {
						list.add(wordArr[0]);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] arr = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			arr[i] = list.get(i).toString();
		}
		return arr;
	}

	private static String filterWords;
	private static String filterChars;

	public static boolean filter(String word) {// �ж��Ƿ���˫�ֽ��
		if (filterWords == null || filterChars == null) {
			try {
				filterWords = FileUtil.readText(IndexUtil.class.getResource("wordfilter.dic").openStream(), "UTF-8");
				filterChars = FileUtil.readText(IndexUtil.class.getResource("charfilter.dic").openStream(), "UTF-8");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		if (NumberUtil.isNumber(word)) {
			return false;
		}
		if (word == null || word.length() < 2) {
			return false;
		}
		if (filterWords.indexOf(word) >= 0) {
			return false;
		}
		String s = word.substring(0, 1);
		String e = word.substring(word.length() - 1);
		if (filterChars.indexOf(s) >= 0 || filterChars.indexOf(e) >= 0) {
			return false;
		}
		return true;
	}

	public static String getTextAbstract(String title, String content) {
		try {
			content = getTextFromHtml(content);
			Query q = IKQueryParser.parse("CONTENT", title);
			SimpleHTMLFormatter formatter = new SimpleHTMLFormatter("", "");
			Highlighter highlighter = new Highlighter(formatter, new QueryScorer(q));
			highlighter.setTextFragmenter(new SimpleFragmenter(200));
			TokenStream tokenStream = new IKAnalyzer().tokenStream("CONTENT", new StringReader(content));
			String tmp = highlighter.getBestFragment(tokenStream, content);
			if (StringUtil.isNotEmpty(tmp)) {
				content = tmp.trim();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		int start = 0, end = 0;
		boolean startFlag = true;
		for (int i = 0; i < content.length(); i++) {
			char c = content.charAt(i);
			if (startFlag) {
				if (Character.isWhitespace(c) || Character.isISOControl(c)) {
					continue;
				}
				if (c == ',' || c == '��' || c == '��' || c == '��' || c == '.' || c == '��' || c == '>' || c == '?'
						|| c == '��' || c == ' ' || c == '��' || c == '\u0020') {// ��ʼ��������Щ����
					continue;
				}
				if (c == '!' || c == '��' || c == ';' || c == '��' || c == ':' || c == '��' || c == ']' || c == '��') {// ��ʼ��������Щ����
					continue;
				}
				start = i;
				startFlag = false;
			}
			if (!startFlag) {
				if (c == '.' || c == '��' || c == '?' || c == '��' || c == '!' || c == '��' || c == ' ' || c == '��'
						|| c == '\u0020') {
					if (i < 8) {// ��һ����������С��8
						start = i + 1;
					}
					end = i;
					if (i != content.length() - 1) {
						if (content.charAt(i + 1) == '��' || content.charAt(i + 1) == '��') {// �����������ţ�Ӧ������һ��
							end = i + 1;
						}
					}
					continue;
				}
				if (c == ',' || c == '��' || c == '>' || c == '��' || c == '��') {
					if (i < 2) {// ����һ���ֺ������Щ����
						start = i + 1;
					}
				}
				if (c == '��' || c == '��') {
					if (i != content.length() - 1) {
						char next = content.charAt(i + 1);
						if (next != ',' && next == '��' && next == '��' && next == ';' && next == '��') {// ��������һ��
							end = i + 1;
						}
					} else {
						end = i;
					}
					continue;
				}
			}
		}
		if (end != 0 && end > start) {
			content = content.substring(start, end + 1).trim();
			// ȥ��ǰ�˼����ֺ�ӿո�����
			start = 0;
			for (int i = 0; i < content.length(); i++) {
				char c = content.charAt(i);
				if (c == '.' || c == '��' || c == '?' || c == '��' || c == '!' || c == '��' || c == ' ' || c == '��'
						|| c == '\u0020') {
					if (i < 8) {// �ո�֮ǰС�ڣ�
						start = i + 1;
					}
				}
			}
			if (start != 0) {
				content = content.substring(start);
			}
			end = 0;// ȥ���������������
			if (StringUtil.isNotEmpty(content)) {
				char c = content.charAt(content.length() - 1);
				if (c != '.' && c != '��' && c != '?' && c != '��' && c != '!' && c != '��') {// ִ�е��˴�����Ϊ���λ��еı�㶼�Ƕ��ţ�û�жϾ�
					for (int i = content.length() - 2; i > 0; i--) {
						c = content.charAt(i);
						if (c == ';' || c == '��' || c == ',' || c == '��' || c == '>' || c == '��') {
							end = i;
							break;
						}
					}
				}
			}

			if (end != 0) {
				content = content.substring(0, end);
			}
		}
		return content;
	}
}
