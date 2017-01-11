package com.nswt.statical.tag.impl;

import java.util.ArrayList;

import com.nswt.framework.utility.StringUtil;
import com.nswt.framework.utility.Treex.TreeNode;
import com.nswt.framework.utility.Treex.TreeNodeList;
import com.nswt.statical.tag.SimpleTag;
import com.nswt.statical.tag.TagAttributeDesc;
import com.nswt.statical.tag.TagBase;
import com.nswt.statical.tag.TagManager;
import com.nswt.statical.template.TemplateCompiler;
import com.nswt.statical.template.TemplateFragment;
import com.nswt.statical.template.TemplatePrintWriter;

/**
 * 条件判断标签 日期 : 2010-6-12 <br>
 * 作者: NSWT <br>
 * 邮箱：nswt@nswt.com.cn <br>
 */
public class ZIfTag extends SimpleTag {

	public TagAttributeDesc[] getAllAttributeDescs() {
		return new TagAttributeDesc[] { new TagAttributeDesc("condition", true), new TagAttributeDesc("output") };
	}

	public boolean onCompileTime(TemplateCompiler compiler, String tagVarName) {
		TemplatePrintWriter out = compiler.getPrintWriter();
		String prefix = compiler.getCurrenPrefix();
		TreeNode node = compiler.getCurrentNode();
		TemplateFragment current = (TemplateFragment) node.getData();

		out.print(prefix + "if(" + replaceHolder(current.Attributes.getString("condition")) + "){\n");
		if (StringUtil.isNotEmpty(current.Attributes.getString("output"))) {
			out.print(prefix + "\tout.print(context.replaceHolder(\"" + current.Attributes.getString("output")
					+ "\"));\n");
		}
		TreeNodeList list = node.getChildren();
		int elseIndex = -1;
		for (int i = 0; i < list.size(); i++) {
			TemplateFragment tf = (TemplateFragment) list.get(i).getData();
			if (tf.Type == TemplateFragment.FRAGMENT_TAG) {
				TagBase tag = TagManager.getTagByName(tf.TagPrefix, tf.TagName);
				if (tag instanceof ZElseTag) {// 有可能是cms:else
					elseIndex = i + 1;
					break;
				}
			}
			compiler.compileNode(list.get(i), prefix + "\t");
		}
		if (elseIndex > 0) {
			out.print(prefix + "}else{\n");
			for (int i = elseIndex; i < list.size(); i++) {
				compiler.compileNode(list.get(i), prefix + "\t");
			}
		}
		out.print(prefix + "}\n");
		return true;
	}

	public int onTagStart() {
		return TagBase.CONTINUE;
	}

	public String getPrefix() {
		return "z";
	}

	public String getTagName() {
		return "if";
	}

	public String replaceHolder(String condition) {
		int lastIndex = 0;
		ArrayList list = new ArrayList();
		while (lastIndex >= 0) {
			int i1 = condition.indexOf("${", lastIndex);
			int i2 = condition.indexOf("}", i1);
			if (i1 < 0) {
				break;
			}
			list.add(condition.substring(lastIndex, i1));
			list.add(condition.substring(i1, i2 + 1));
			lastIndex = i2 + 1;
		}
		list.add(condition.substring(lastIndex));
		while (true) {
			boolean flag = false;
			for (int i = 0; i < list.size() - 1; i++) {
				String current = list.get(i).toString();
				if (!current.startsWith("${") || current.indexOf("}:") > 0) {// }:说明已经确定类型
					continue;
				}
				String op = list.get(i + 1).toString();
				// 日期函数
				if (op.startsWith(".getTime(") || op.startsWith(".after(") || op.startsWith(".before(")) {
					current = current + ":Date";
				} else if (op.startsWith(".charAt(") || op.startsWith(".endsWith(") || op.startsWith(".startsWith(")
						|| op.startsWith(".equalsIgnoreCase(") || op.startsWith(".continue(")
						|| op.startsWith(".match(") || op.startsWith(".toUpperCase(") || op.startsWith(".toLowerCase(")
						|| op.startsWith(".split(") || op.startsWith(".replace(") || op.startsWith(".replaceAll(")
						|| op.startsWith(".substring(") || op.startsWith(".indexOf(") || op.startsWith(".lastIndexOf(")) {
					current = current + ":String";
				} else if (op.startsWith(".")) {// 其他函数
					current = current + ":Object";
				}
				if (current.indexOf("}:") < 0) {// 如果不能确定,则向下找
					if (op.startsWith("==")) {
						if (op.startsWith("==\"")) {
							current = current + ":String";
						} else if (op.length() > 2 && Character.isDigit(op.charAt(2))) {
							current = current + ":Double";
						} else {
							if (i < list.size() - 1) {
								String next = list.get(i + 2).toString();
								if (next.startsWith("${") && next.indexOf("}:") > 0) {
									current = current + next.substring(next.indexOf("}:") + 1);
								}
							}
						}
					} else if (op.startsWith("+")) {
						if (op.startsWith("+\"")) {
							current = current + ":String";
						} else if (op.length() > 1 && Character.isDigit(op.charAt(1))) {
							current = current + ":Double";
						} else {
							if (i < list.size() - 1) {
								String next = list.get(i + 2).toString();
								if (next.startsWith("${") && next.indexOf("}:") > 0) {
									current = current + next.substring(next.indexOf("}:") + 1);
								}
							}
						}
					} else {
						if (!op.equals(")") && !op.equals("|") && !op.equals("&")) {// 其他操作符
							current = current + ":Double";
						}
					}
				}
				if (current.indexOf("}:") < 0) {// 如果还是不能确定,则向上找
					if (i != 0) {
						String opPrev = list.get(i - 1).toString();
						if (opPrev.endsWith("==")) {
							if (opPrev.endsWith("\"==")) {
								current = current + ":String";
							} else if (opPrev.length() > 2 && Character.isDigit(opPrev.charAt(opPrev.length() - 3))) {
								current = current + ":Double";
							} else {
								if (i > 1) {
									String previous = list.get(i - 2).toString();
									if (previous.startsWith("${") && previous.indexOf("}:") > 0) {
										current = current + previous.substring(previous.indexOf("}:") + 1);
									}
								}
							}
						} else if (opPrev.startsWith("+")) {
							if (opPrev.endsWith("\"+")) {
								current = current + ":String";
							} else if (opPrev.length() > 1 && Character.isDigit(opPrev.charAt(opPrev.length() - 2))) {
								current = current + ":Double";
							} else {
								if (i > 1) {
									String previous = list.get(i - 2).toString();
									if (previous.startsWith("${") && previous.indexOf("}:") > 0) {
										current = current + previous.substring(previous.indexOf("}:") + 1);
									}
								}
							}
						} else {
							if (!opPrev.equals("(") && !opPrev.equals("|") && !opPrev.equals("&")
									&& !opPrev.equals("!")) {// 其他操作符
								current = current + ":Double";
							}
						}
					}
				}
				if (!current.equals(list.get(i))) {
					list.set(i, current);
					flag = true;
				}
			}
			if (!flag) {// 说明还有未确定类型的占位符
				break;
			}
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			String current = list.get(i).toString();
			if (current.startsWith("${")) {
				String type = null;
				if (current.indexOf("}:") < 0) {// }:说明已经确定类型
					type = "Object";
				} else {
					type = current.substring(current.indexOf("}:") + 2);
					current = current.substring(2, current.indexOf("}:"));
				}
				if (type.equals("Double")) {
					sb.append("evalDouble(\"" + current + "\")");
				}
				if (type.equals("String")) {
					sb.append("eval(\"" + current + "\")");
					if (i < list.size() - 1) {
						String op = list.get(i + 1).toString();
						if (op.startsWith("==")) {
							sb.append(".equals(");
							list.set(i + 1, op.substring(2));
							insertEqualsEnd(list, i + 1);
						}
					}
				}
				if (type.equals("Object") || type.equals("Date")) {
					sb.append("eval" + type + "(\"" + current + "\")");
					if (i < list.size() - 1) {
						String op = list.get(i + 1).toString();
						if (op.equals("==")) {
							sb.append(".equals(");
							list.set(i + 1, op.substring(2));
							insertEqualsEnd(list, i + 1);
						}
					}
				}
			} else {
				sb.append(current);
			}
		}
		return sb.toString();
	}

	public void insertEqualsEnd(ArrayList list, int start) {
		boolean isStringBegin = false;
		int level = 0;
		for (int k = start; k < list.size(); k++) {
			String str = list.get(k).toString();
			for (int i = 0; i < str.length(); i++) {
				char c = str.charAt(i);
				if (c == '\"') {
					if (!isStringBegin) {
						isStringBegin = true;
					} else {
						isStringBegin = false;
					}
				}
				if (isStringBegin) {
					continue;
				}
				if (c == '(') {
					level++;
				}
				if (c == ')') {
					if (level == 0) {
						str = str.substring(0, i) + ")" + str.substring(i);
						list.set(k, str);
						return;
					}
					level--;
				}
				if (c == '|' || c == '&') {
					if (level == 0) {
						str = str.substring(0, i) + ")" + str.substring(i);
						list.set(k, str);
						return;
					}
				}
			}
		}
	}
}
