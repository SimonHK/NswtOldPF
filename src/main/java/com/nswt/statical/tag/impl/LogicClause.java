package com.nswt.statical.tag.impl;

import java.util.ArrayList;

import com.nswt.statical.template.TemplateContext;

/**
 * 日期 : 2010-6-12 <br>
 * 作者: NSWT <br>
 * 邮箱：nswt@nswt.com.cn <br>
 */
public class LogicClause {
	/**
	 * 语句前有“非”修饰符
	 */
	public boolean isNot;

	/**
	 * 与上一子句是或关系
	 */
	public boolean isOr;

	/**
	 * 逻辑代码
	 */
	public String Clause;

	public boolean isNot() {
		return isNot;
	}

	public void setNot(boolean isNot) {
		this.isNot = isNot;
	}

	public boolean isOr() {
		return isOr;
	}

	public void setOr(boolean isOr) {
		this.isOr = isOr;
	}

	public String getClauseString() {
		return Clause;
	}

	public void setClauseString(String clause) {
		Clause = clause;
	}

	public boolean execute(TemplateContext context) {
		ArrayList list = new ArrayList();
		parse(Clause, list);
		for (int i = 0; i < list.size(); i++) {
			String str = list.get(i).toString();
			if (str.startsWith("VAR:")) {
				continue;
			}
			if (str.startsWith("FUNC:")) {
				continue;
			}
			if (str.startsWith("OP:")) {
				String op = str.substring(3);
				if(op.equals("+")){
					
				}
				continue;
			}
		}
		return false;
	}

	public boolean parse(String str, ArrayList list) {
		char[] cs = Clause.toCharArray();
		boolean isStringBegin = false;
		int varStart = 0;
		for (int i = 0; i < cs.length; i++) {
			char c = cs[i];
			if (c == '\"') {
				if (i == 0) {
					isStringBegin = true;
				} else if (cs[i - 1] != '\\') {
					if (isStringBegin) {
						isStringBegin = false;
					} else {
						isStringBegin = true;
					}
				}
			}
			if (isStringBegin) {
				continue;
			}
			if (c == '+' || c == '-' || c == '*' || c == '/' || c == '%' || c == '>' || c == '<' || c == '!'
					|| c == '=') {
				list.add("VAR:" + Clause.substring(varStart, i));
				String operator = Clause.substring(i, i + 1);// 操作符
				if (c == '>' || c == '<' || c == '!' || c == '=') {
					if (c != cs.length - 1 && cs[i + 1] == '=') {
						operator = Clause.substring(i, i + 2);
						i++;
					}
				}
				list.add("OP:" + operator);
				varStart = i + 1;
			}
			if (c == '.') {// 函数开始
				list.add("VAR:" + Clause.substring(varStart, i));
				varStart = -1;
				ArrayList args = new ArrayList();// 函数的参数列表
				int argStart = Clause.indexOf("(", i) + 1;
				for (int j = argStart; j < cs.length; j++) {
					c = cs[j];
					if (c == '\"') {
						if (c != '\\') {
							if (isStringBegin) {
								isStringBegin = false;
							} else {
								isStringBegin = true;
							}
						}
					}
					if (isStringBegin) {
						continue;
					}
					if (c == ')') {
						args.add(Clause.substring(argStart, j));
						break;
					}
					if (c == ',') {// 下一个参数
						args.add(Clause.substring(argStart, j));
						argStart = j + 1;
					}
				}
				list.add("FUNC:" + Clause.substring(i + 1, Clause.indexOf("(")));
				list.add(args);
			}
		}
		if (varStart > 0) {
			list.add("VAR:" + Clause.substring(varStart));
		}
		return false;
	}

	public String toString() {
		return (isNot ? "!" : "") + Clause + (isOr ? "[OR]" : "[AND]");
	}
}
