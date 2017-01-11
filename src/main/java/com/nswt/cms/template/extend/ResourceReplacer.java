package com.nswt.cms.template.extend;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.nswt.framework.utility.FileUtil;

/*
 * @Author 王育春
 * @Date 2016-8-4
 * @Mail nswt@nswt.com.cn
 */
public class ResourceReplacer {
	// 匹配形如 href=../images/test.gif考虑等号前后有空格的情况
	public static final Pattern ResourcePattern1 = Pattern.compile(
			"\\s(src|href|background|file|virtual|action|name=\"movie\" value)\\s*?=\\s*?([^\\\"\\\'\\s]+?)(\\s|>|/>)",
			Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	// 匹配形如 href="../images/test.gif" 或者 href="../images/test.gif"
	public static final Pattern ResourcePattern2 = Pattern.compile(
			"\\s(src|href|background|file|virtual|action|name=\"movie\" value)\\s*?=\\s*?(\\\"|\\\')(.*?)\\2",
			Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	// 匹配css中的 url(images/test.gif)
	public static final Pattern ResourcePatternCss1 = Pattern.compile("(:\\s*?url)\\s*?\\(\\s*?([^\\\"\\\'\\s]+)(\\))",
			Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	// 匹配css中的 url('images/test.gif')或url("images/test.gif")
	public static final Pattern ResourcePatternCss2 = Pattern.compile(
			"(:\\s*?url)\\s*?\\(\\s*?(\\\"|\\\')(.*?)\\2(\\))", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	/**
	 * 处理模板文件中的相对路径
	 */
	public static String dealResource(String base, String fileName, String content) {
		if (content == null) {
			return "";
		}
		Matcher m = ResourcePattern1.matcher(content);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			String path = dealPath(base, fileName, m.group(2));
			sb.append(content.substring(lastEndIndex, m.start()));
			sb.append(" " + m.group(1) + "=" + path + m.group(3));
			lastEndIndex = m.end();
		}
		sb.append(content.substring(lastEndIndex));

		content = sb.toString();
		sb = new StringBuffer();
		m = ResourcePattern2.matcher(content);
		lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			String path = dealPath(base, fileName, m.group(3));
			sb.append(content.substring(lastEndIndex, m.start()));
			String separator = m.group(2);
			sb.append(" " + m.group(1) + "=" + separator + path + separator);
			lastEndIndex = m.end();
		}
		sb.append(content.substring(lastEndIndex));

		content = sb.toString();
		sb = new StringBuffer();
		m = ResourcePatternCss1.matcher(content);
		lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			String path = dealPath(base, fileName, m.group(2));
			sb.append(content.substring(lastEndIndex, m.start()));
			sb.append(m.group(1) + "(" + path + ")");
			lastEndIndex = m.end();
		}
		sb.append(content.substring(lastEndIndex));

		content = sb.toString();
		sb = new StringBuffer();
		m = ResourcePatternCss2.matcher(content);
		lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			String path = dealPath(base, fileName, m.group(3));
			sb.append(content.substring(lastEndIndex, m.start()));
			sb.append(m.group(1) + "(" + path + ")");
			lastEndIndex = m.end();
		}
		sb.append(content.substring(lastEndIndex));
		return sb.toString();
	}

	private static String dealPath(String base, String templateFileName, String path) {
		if (path.startsWith("${")) {
			return path;
		}
		if (path.startsWith("/") || path.indexOf(":") > 0) {
			return path;
		}
		File parent = new File(templateFileName.substring(0, templateFileName.lastIndexOf("/")));
		while (true) {
			if (path.startsWith("./")) {
				path = path.substring(2);
			} else if (path.startsWith("../")) {
				parent = parent.getParentFile();
				path = path.substring(3);
			} else {
				break;
			}
		}
		String fileName = parent.getAbsolutePath() + "/" + path;
		fileName = FileUtil.normalizePath(fileName);
		if (!fileName.startsWith(base)) {
			return path;
		}
		fileName = fileName.substring(base.length());
		if (fileName.startsWith("/")) {
			fileName = fileName.substring(1);
		}
		fileName = "${Level}" + fileName;
		return fileName;
	}
}
