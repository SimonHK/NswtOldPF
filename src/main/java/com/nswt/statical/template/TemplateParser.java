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
			Errorx.addError("待解析的模板不能为空!");
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
			Errorx.addMessage("第1行不是模板配置信息!" + fileName);
			String name = fileName.substring(fileName.lastIndexOf("/") + 1, fileName.lastIndexOf("."));
			config = new TemplateConfig("Unknown", name, null, 1.0, null, "<%", "%>", 0);// 兼容1.x的模板
		}
		char[] cs = content.toCharArray();
		int currentLineNo = 1;
		int holderStartIndex = -1;// 当前占位符起始位置
		int htmlStartIndex = 0;// 当前HTML段起始位置
		int htmlStartLineNo = 0;// 当前HTML段起始位置
		int scriptStartIndex = -1;// 当前脚本起始位置
		int scriptStartLineNo = -1;// 当前脚本起始行数
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
							- config.getScriptEnd().length() + 1) {// 脚本结束
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
				continue;// 脚本之中要忽略标签和占位符
			}
			if (c == '$' && i < cs.length - 1 && cs[i + 1] == '{') {
				if (holderStartIndex >= 0) {// 说明前一个占位符未正确结束
					Errorx.addMessage("第" + currentLineNo + "行可能有错误，疑似占位符未正常结束!");
					htmlStartIndex = holderStartIndex;// 必须重置
					htmlStartLineNo = currentLineNo;
				}
				if (htmlStartIndex != -1 && htmlStartIndex != i) {// 前面有一段长度不为零纯HTML
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
			if (c == '}' || c == ' ' || c == '\n') {// ${}之中不允许有换行、空格和大括号
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
					} else {// 不符合条件的说明不是正确的占位符
						Errorx.addMessage("第" + currentLineNo + "行可能有错误，疑似占位符未正常结束!");
						htmlStartIndex = holderStartIndex;// 必须重置
						htmlStartLineNo = currentLineNo;
					}
				}
				holderStartIndex = -1;
				continue;
			}
			if (c == '<') {
				// 判断是否是注释
				if (lowerTemplate.indexOf("<!--", i) == i && lowerTemplate.indexOf(config.getScriptStart(), i) != i) {
					int end = lowerTemplate.indexOf("-->", i);
					for (int k = i; k < end; k++) {
						if (cs[k] == '\n') {
							currentLineNo++;// 要注意行号加减
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
						|| lowerTemplate.indexOf("<custom:", i) == i) {// 置标开始
					int tagEnd = getTagEnd(cs, i + 1);
					if (tagEnd < 0) {
						Errorx.addError("第" + currentLineNo + "行有错误，置标未正确结束!");
						return;
					}
					if (htmlStartIndex != -1 && htmlStartIndex != i) {// 前面有一段长度不为零纯HTML
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
					if (tag.endsWith("/")) {// 没有body的置标
						tf.StartLineNo = currentLineNo;
						tf.Type = TemplateFragment.FRAGMENT_TAG;
						tf.StartCharIndex = i;
						tf.EndCharIndex = tagEnd;
						tf.FragmentText = null;
						currentParent.addChild(tf);
					} else {// 有body的置标
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
						|| lowerTemplate.indexOf("</custom:", i) == i) {// 置标结束
					String tagEnd = content.substring(i, content.indexOf(">", i) + 1);
					TemplateFragment tf = (TemplateFragment) currentParent.getData();
					if (tf == null) {
						Errorx.addError("第" + currentLineNo + "行有错误，发现置标结束标记:" + tagEnd + "，但没有找到相应的置标开始标记!");
						return;
					}
					String prefix = tagEnd.substring(2, tagEnd.indexOf(":"));
					String tagName = tagEnd.substring(tagEnd.indexOf(":") + 1, tagEnd.length() - 1);
					if (!prefix.equalsIgnoreCase(tf.TagPrefix) || !tagName.equalsIgnoreCase(tf.TagName)) {
						Errorx.addError("第" + currentLineNo + "行有错误，发现置标结束标记:" + tagEnd + "，但对应的置标开始标记是:<"
								+ tf.TagPrefix + ":" + tf.TagName + ">");
						return;
					}
					if (htmlStartIndex != -1 && htmlStartIndex != i) {// 前面有一段长度不为零纯HTML
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
				if (content.indexOf(config.getScriptStart(), i) == i) {// 脚本开始
					if (htmlStartIndex != -1 && htmlStartIndex != i) {// 前面有一段长度不为零纯HTML
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
		if (htmlStartIndex != -1 && htmlStartIndex != cs.length - 1) {// 最后面有一段长度不为零纯HTML
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
				if (i > 0 && cs[i - 1] == '\\') {// 转义
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
			if (c == '<' && lastStringChar == 0) {// 说明置标未正确结束
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
			ScriptStart = "<!--%";// 2.x中的默认脚本开始标识
		}
		if (StringUtil.isEmpty(ScriptEnd)) {
			ScriptEnd = "%-->";// 2.x中的默认脚本开始标识
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
		if (nameEnd > 0) {// 说明没有任何属性
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
				if (c == ' ' && lastStringChar == 0) {// 空格表明一个新的名值对开始
					nameStartIndex = i + 1;
				}
				if (c == '\"' || c == '\'') {
					if (i > 0 && cs[i - 1] == '\\') {// 转义
						continue;
					}
					if (lastStringChar == c) {
						lastStringChar = 0;
						map.put(key, tag.subSequence(valueStartIndex, i));
					} else if (lastStringChar != '\"') {// 可能是双引号中有单引号
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
		// 遍历树，生成待编译的文件
		Treex tree = parser.getTree();
		System.out.println(tree);
	}
}
