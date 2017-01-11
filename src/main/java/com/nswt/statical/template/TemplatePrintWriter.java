package com.nswt.statical.template;

import java.io.PrintWriter;
import java.io.StringWriter;

/*
 * 两个作用：
 * 1、能够通过getResult()得到写入的字符串
 * 2、能够建立多级Writer
 * 
 * @Author NSWT
 * @Date 2016-7-26
 * @Mail nswt@nswt.com.cn
 */
public class TemplatePrintWriter extends PrintWriter {
	TemplateContext context;

	public TemplatePrintWriter(TemplateContext context) {
		super(new StringWriter());
		this.context = context;
	}

	public String getResult() {
		return out.toString();
	}

	public void clear() {
		out = new StringWriter();
	}

	public void write(int c) {
		if (context == null) {
			super.write(c);
			return;
		}
		PrintWriter writer = context.getCurrentPrintWriter();
		if (writer != null && writer != this) {
			writer.write(c);
		} else {
			super.write(c);
		}
	}

	public void write(char buf[], int off, int len) {
		if (context == null) {
			super.write(buf, off, len);
			return;
		}
		PrintWriter writer = context.getCurrentPrintWriter();
		if (writer != null && writer != this) {
			writer.write(buf, off, len);
		} else {
			super.write(buf, off, len);
		}
	}

	public void write(String s, int off, int len) {
		if (context == null) {
			super.write(s, off, len);
			return;
		}
		PrintWriter writer = context.getCurrentPrintWriter();
		if (writer != null && writer != this) {
			writer.write(s, off, len);
		} else {
			super.write(s, off, len);

		}
	}

	public void println() {
		if (context == null) {
			super.println();
			return;
		}
		PrintWriter writer = context.getCurrentPrintWriter();
		if (writer != null && writer != this) {
			writer.println();
		} else {
			super.println();
		}
	}
}
