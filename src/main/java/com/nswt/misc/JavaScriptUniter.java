package com.nswt.misc;

import java.io.File;
import java.util.regex.Pattern;

import com.nswt.framework.Config;
import com.nswt.framework.utility.FileUtil;
import com.nswt.framework.utility.StringUtil;

/**
 * @Author ������
 * @Date 2008-5-17
 * @Mail nswt@nswt.com.cn
 */
public class JavaScriptUniter {

	public static void main(String[] args) {
		String path1 = Config.getContextRealPath() + "Framework";
		String path2 = Config.getContextRealPath() + "Framework/Controls";
		File f = new File(path1);
		File[] fs = f.listFiles();
		StringBuffer sb = new StringBuffer();
		sb.append(FileUtil.readText(path1 + "/_Main.js"));
		for (int i = 0; i < fs.length; i++) {
			if (fs[i].getName().endsWith(".js") && !fs[i].getName().endsWith("Main.js") && !fs[i].getName().equals("Spell.js")
					&& !fs[i].getName().equals("District.js") && !fs[i].getName().equals("Chart.js")) {
				sb.append(FileUtil.readText(fs[i]));
			}
		}
		f = new File(path2);
		fs = f.listFiles();
		for (int i = 0; i < fs.length; i++) {
			if (fs[i].getName().endsWith(".js") && !fs[i].getName().equals("StyleToolbar.js")) {
				sb.append(FileUtil.readText(fs[i]));
			}
		}
		String str = sb.toString();
		Pattern p1 = Pattern.compile("\\/\\*.*?\\*\\/", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
		str = p1.matcher(str).replaceAll("");
		String[] arr = str.split("\\n");
		sb = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			String line = arr[i];
			char lastStringChar = 0;
			for (int j = 0; j < line.length(); j++) {
				char c = line.charAt(j);
				if (c == '\'' || c == '\"') {
					if (lastStringChar == 0) {
						lastStringChar = c;
					} else if (lastStringChar == c) {
						lastStringChar = 0;
					}
				}
				if (c == '/' && line.charAt(j + 1) == '/') {
					line = line.substring(0, j);
					break;
				}
			}
			line = line.trim();
			if (StringUtil.isEmpty(line)) {
				continue;
			}
			sb.append(line + "\n");
		}
		str = sb.toString();
		str.replaceAll("\\s{2,}", " ");
		FileUtil.writeText(path1 + "/Main.js", str);
	}
}
