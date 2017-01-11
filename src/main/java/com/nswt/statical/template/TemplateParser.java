package com.nswt.statical.template;

import com.nswt.framework.Config;
import com.nswt.framework.utility.Errorx;
import com.nswt.framework.utility.FileUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.framework.utility.Treex;
import com.nswt.framework.utility.Treex.TreeNode;

/**
 * @Author NSWT
 * @Date 2016-5-29
 * @Mail nswt@nswt.com.cn
 */
public class TemplateParser {
	private String fileName;
	private String content;
	private Treex tree;
	private TreeNode currentParent;
	private TemplateConfig config;

	public TemplateParser(String templateFileName) {
		fileName = templateFileName;
		content = FileUtil.readText(fileName);
	}

	public String getFileName() {
		return fileName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void parse() {
		if (tree != null) {
			return;
		}
		if (content == null) {
			Errorx.addError("��������ģ�岻��Ϊ��!");
			return;
		}
		content = content.trim();
		String head = "";
		if (content.indexOf("\n") > 0) {
			head = content.substring(0, content.indexOf("\n"));
		}
		if (head.toLowerCase().startsWith("<z:config")) {
			parseConfig(head);
			content = content.substring(content.indexOf("\n") + 1);
		} else {
			Errorx.addMessage("��1�в���ģ��������Ϣ!" + fileName);
			String name = fileName.substring(fileName.lastIndexOf("/") + 1, fileName.lastIndexOf("."));
			config = new TemplateConfig("Unknown", name, null, 1.0, null, "<%", "%>", 0);// ����1.x��ģ��
		}
		char[] cs = content.toCharArray();
		int currentLineNo = 1;
		int holderStartIndex = -1;// ��ǰռλ����ʼλ��
		int htmlStartIndex = 0;// ��ǰHTML����ʼλ��
		int htmlStartLineNo = 0;// ��ǰHTML����ʼλ��
		int scriptStartIndex = -1;// ��ǰ�ű���ʼλ��
		int scriptStartLineNo = -1;// ��ǰ�ű���ʼ����
		tree = new Treex();
		currentParent = tree.getRoot();
		String lowerTemplate = content.toLowerCase();
		for (int i = 0; i < cs.length; i++) {
			char c = cs[i];
			if (c == '\n') {
				currentLineNo++;
			}
			if (scriptStartIndex > 0) {
				if (c == '>') {
					if (content.indexOf(config.getScriptEnd(), i - config.getScriptEnd().length() + 1) == i
							- config.getScriptEnd().length() + 1) {// �ű�����
						TemplateFragment tf = new TemplateFragment();
						tf.Type = TemplateFragment.FRAGMENT_SCRIPT;
						tf.FragmentText = content.substring(scriptStartIndex + config.getScriptStart().length(), i
								- config.getScriptEnd().length() + 1);
						tf.StartLineNo = scriptStartLineNo;
						currentParent.addChild(tf);
						htmlStartIndex = i + 1;
						htmlStartLineNo = currentLineNo;
						scriptStartIndex = -1;
					}
				}
				continue;// �ű�֮��Ҫ���Ա�ǩ��ռλ��
			}
			if (c == '$' && i < cs.length - 1 && cs[i + 1] == '{') {
				if (holderStartIndex >= 0) {// ˵��ǰһ��ռλ��δ��ȷ����
					Errorx.addMessage("��" + currentLineNo + "�п����д�������ռλ��δ��������!");
					htmlStartIndex = holderStartIndex;// ��������
					htmlStartLineNo = currentLineNo;
				}
				if (htmlStartIndex != -1 && htmlStartIndex != i) {// ǰ����һ�γ��Ȳ�Ϊ�㴿HTML
					TemplateFragment tf = new TemplateFragment();
					tf.Type = TemplateFragment.FRAGMENT_HTML;
					tf.FragmentText = content.substring(htmlStartIndex, i);
					tf.StartLineNo = htmlStartLineNo;
					currentParent.addChild(tf);
					htmlStartIndex = -1;
				}
				holderStartIndex = i;
				continue;
			}
			if (c == '}' || c == ' ' || c == '\n') {// ${}֮�в������л��С��ո�ʹ�����
				if (holderStartIndex >= 0) {
					if (c == '}' && holderStartIndex + 2 < i) {
						TemplateFragment tf = new TemplateFragment();
						tf.Type = TemplateFragment.FRAGMENT_HOLDER;
						tf.FragmentText = content.substring(holderStartIndex + 2, i);
						tf.StartLineNo = currentLineNo;
						tf.StartCharIndex = holderStartIndex;
						tf.EndCharIndex = i;
						currentParent.addChild(tf);
						htmlStartIndex = i + 1;
						htmlStartLineNo = currentLineNo;
					} else {// ������������˵��������ȷ��ռλ��
						Errorx.addMessage("��" + currentLineNo + "�п����д�������ռλ��δ��������!");
						htmlStartIndex = holderStartIndex;// ��������
						htmlStartLineNo = currentLineNo;
					}
				}
				holderStartIndex = -1;
				continue;
			}
			if (c == '<') {
				// �ж��Ƿ���ע��
				if (lowerTemplate.indexOf("<!--", i) == i && lowerTemplate.indexOf(config.getScriptStart(), i) != i) {
					int end = lowerTemplate.indexOf("-->", i);
					for (int k = i; k < end; k++) {
						if (cs[k] == '\n') {
							currentLineNo++;// Ҫע���кżӼ�
						}
					}
					i = end + 3;
					continue;
				}
				int index = content.indexOf(":", i);
				boolean tagStartFlag = true;
				if (index > 0 && index - i < 30) {
					String str = content.substring(i + 1, index);
					for (int k = 0; k < str.length(); k++) {
						if (str.charAt(k) != '/' && !Character.isLetterOrDigit(str.charAt(k))) {
							tagStartFlag = false;
							break;
						}
					}
					if (tagStartFlag && str.startsWith("/")) {
						tagStartFlag = false;
					}
				}
				if (lowerTemplate.indexOf("<z:", i) == i || lowerTemplate.indexOf("<cms:", i) == i
						|| lowerTemplate.indexOf("<custom:", i) == i) {// �ñ꿪ʼ
					int tagEnd = getTagEnd(cs, i + 1);
					if (tagEnd < 0) {
						Errorx.addError("��" + currentLineNo + "���д����ñ�δ��ȷ����!");
						return;
					}
					if (htmlStartIndex != -1 && htmlStartIndex != i) {// ǰ����һ�γ��Ȳ�Ϊ�㴿HTML
						TemplateFragment tf = new TemplateFragment();
						tf.Type = TemplateFragment.FRAGMENT_HTML;
						tf.FragmentText = content.substring(htmlStartIndex, i);
						tf.StartLineNo = htmlStartLineNo;
						currentParent.addChild(tf);
						htmlStartIndex = -1;
					}
					String tag = content.substring(i + 1, tagEnd).trim();
					TemplateFragment tf = new TemplateFragment();
					parseTagAttributes(tf, tag);
					if (tag.endsWith("/")) {// û��body���ñ�
						tf.StartLineNo = currentLineNo;
						tf.Type = TemplateFragment.FRAGMENT_TAG;
						tf.StartCharIndex = i;
						tf.EndCharIndex = tagEnd;
						tf.FragmentText = null;
						currentParent.addChild(tf);
					} else {// ��body���ñ�
						tf.StartLineNo = currentLineNo;
						tf.Type = TemplateFragment.FRAGMENT_TAG;
						tf.StartCharIndex = i;
						TreeNode tn = currentParent.addChild(tf);
						currentParent = tn;
					}
					i = tagEnd;
					htmlStartIndex = tagEnd + 1;
					continue;
				}
				if (lowerTemplate.indexOf("</z:", i) == i || lowerTemplate.indexOf("</cms:", i) == i
						|| lowerTemplate.indexOf("</custom:", i) == i) {// �ñ����
					String tagEnd = content.substring(i, content.indexOf(">", i) + 1);
					TemplateFragment tf = (TemplateFragment) currentParent.getData();
					if (tf == null) {
						Errorx.addError("��" + currentLineNo + "���д��󣬷����ñ�������:" + tagEnd + "����û���ҵ���Ӧ���ñ꿪ʼ���!");
						return;
					}
					String prefix = tagEnd.substring(2, tagEnd.indexOf(":"));
					String tagName = tagEnd.substring(tagEnd.indexOf(":") + 1, tagEnd.length() - 1);
					if (!prefix.equalsIgnoreCase(tf.TagPrefix) || !tagName.equalsIgnoreCase(tf.TagName)) {
						Errorx.addError("��" + currentLineNo + "���д��󣬷����ñ�������:" + tagEnd + "������Ӧ���ñ꿪ʼ�����:<"
								+ tf.TagPrefix + ":" + tf.TagName + ">");
						return;
					}
					if (htmlStartIndex != -1 && htmlStartIndex != i) {// ǰ����һ�γ��Ȳ�Ϊ�㴿HTML
						TemplateFragment tf2 = new TemplateFragment();
						tf2.Type = TemplateFragment.FRAGMENT_HTML;
						tf2.FragmentText = content.substring(htmlStartIndex, i);
						tf2.StartLineNo = htmlStartLineNo;
						currentParent.addChild(tf2);
						htmlStartIndex = -1;
					}
					tf.FragmentText = content.substring(getTagEnd(cs, tf.StartCharIndex + 1) + 1, i);
					tf.EndCharIndex = i = content.indexOf('>', i);
					currentParent = currentParent.getParent();
					htmlStartIndex = i + 1;
					htmlStartLineNo = currentLineNo;
					continue;
				}
				if (content.indexOf(config.getScriptStart(), i) == i) {// �ű���ʼ
					if (htmlStartIndex != -1 && htmlStartIndex != i) {// ǰ����һ�γ��Ȳ�Ϊ�㴿HTML
						TemplateFragment tf = new TemplateFragment();
						tf.Type = TemplateFragment.FRAGMENT_HTML;
						tf.FragmentText = content.substring(htmlStartIndex, i);
						tf.StartLineNo = htmlStartLineNo;
						currentParent.addChild(tf);
						htmlStartIndex = -1;
						scriptStartIndex = i;
						scriptStartLineNo = currentLineNo;
					}
					continue;
				}
			}
		}
		if (htmlStartIndex != -1 && htmlStartIndex != cs.length - 1) {// �������һ�γ��Ȳ�Ϊ�㴿HTML
			TemplateFragment tf = new TemplateFragment();
			tf.Type = TemplateFragment.FRAGMENT_HTML;
			tf.FragmentText = content.substring(htmlStartIndex);
			tf.StartLineNo = htmlStartLineNo;
			currentParent.addChild(tf);
			htmlStartIndex = -1;
		}
	}

	public int getTagEnd(char[] cs, int start) {
		char lastStringChar = 0;
		for (int i = start; i < cs.length; i++) {
			char c = cs[i];
			if (c == '\"' || c == '\'') {
				if (i > 0 && cs[i - 1] == '\\') {// ת��
					continue;
				}
				if (lastStringChar == c) {
					lastStringChar = 0;
				} else if (lastStringChar == 0) {
					lastStringChar = c;
				}
			}
			if (c == '>' && lastStringChar == 0) {
				return i;
			}
			if (c == '<' && lastStringChar == 0) {// ˵���ñ�δ��ȷ����
				return -1;
			}
		}
		return -1;
	}

	public void parseConfig(String head) {
		head = head.trim();
		head = head.substring(head.indexOf(" "), head.length() - 1).trim();
		if (head.endsWith("/")) {
			head = head.substring(0, head.length() - 1).trim();
		}
		head = head.replaceAll("\\s+", " ");
		Mapx map = StringUtil.splitToMapxNew(head, " ", "=");
		String Name = map.getString("Name");
		String Type = map.getString("Type");
		String Author = map.getString("Author");
		String Version = map.getString("Version");
		String Description = map.getString("Description");
		String ScriptStart = map.getString("ScriptStart");
		String ScriptEnd = map.getString("ScriptEnd");
		if (StringUtil.isEmpty(ScriptStart)) {
			ScriptStart = "<!--%";// 2.x�е�Ĭ�Ͻű���ʼ��ʶ
		}
		if (StringUtil.isEmpty(ScriptEnd)) {
			ScriptEnd = "%-->";// 2.x�е�Ĭ�Ͻű���ʼ��ʶ
		}
		if (StringUtil.isEmpty(Version)) {
			Version = "1.0";
		}
		config = new TemplateConfig(Name, Type, Author, Double.parseDouble(Version), Description, ScriptStart,
				ScriptEnd, 0);
	}

	public void parseTagAttributes(TemplateFragment tf, String tag) {
		String prefix = tag.substring(0, tag.indexOf(":")).trim();
		int nameEnd = tag.indexOf(" ");
		String tagName = null;
		Mapx map = new Mapx();
		if (nameEnd > 0) {// ˵��û���κ�����
			tagName = tag.substring(tag.indexOf(":") + 1, nameEnd).trim();
			tag = tag.substring(nameEnd + 1).trim();
			if (tag.endsWith("/")) {
				tag = tag.substring(0, tag.length() - 1).trim();
			}
			tag = tag.replaceAll("\\s+", " ");
			char lastStringChar = 0;
			int nameStartIndex = 0;
			int valueStartIndex = -1;
			String key = null;
			char[] cs = tag.toCharArray();
			for (int i = 0; i < cs.length; i++) {
				char c = cs[i];
				if (c == '=' && lastStringChar == 0) {
					key = tag.substring(nameStartIndex, i);
					nameStartIndex = 0;
				}
				if (c == ' ' && lastStringChar == 0) {// �ո����һ���µ���ֵ�Կ�ʼ
					nameStartIndex = i + 1;
				}
				if (c == '\"' || c == '\'') {
					if (i > 0 && cs[i - 1] == '\\') {// ת��
						continue;
					}
					if (lastStringChar == c) {
						lastStringChar = 0;
						map.put(key, tag.subSequence(valueStartIndex, i));
					} else if (lastStringChar != '\"') {// ������˫�������е�����
						lastStringChar = c;
						valueStartIndex = i + 1;
					}
				}
			}
		} else {
			tagName = tag.substring(tag.indexOf(":") + 1).trim();
		}
		tf.TagPrefix = prefix;
		tf.TagName = tagName;
		tf.Attributes = map;
	}

	public TemplateConfig getConfig() {
		return config;
	}

	public Treex getTree() {
		return tree;
	}

	public static void main(String[] args) {
		TemplateParser parser = new TemplateParser(Config.getContextRealPath()
				+ "wwwroot/nswtpDemo/template/detail.html");
		parser.parse();
		if (Errorx.hasError()) {
			System.out.println(StringUtil.join(Errorx.getMessages(), "\n"));
			return;
		}
		// �����������ɴ�������ļ�
		Treex tree = parser.getTree();
		System.out.println(tree);
	}
}
