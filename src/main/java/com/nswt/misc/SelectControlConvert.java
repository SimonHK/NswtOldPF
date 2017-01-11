package com.nswt.misc;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.nswt.framework.Config;
import com.nswt.framework.utility.FileUtil;
import com.nswt.framework.utility.StringUtil;

/**
 * ע�⣺FreeBSD�� <z:select>${Value}</z:select>�е�${��������ʧ��������${Value}ǰ��ӿո�<br>
 * ���༴ɨ������δ�ӿո��д����Ȼ���ֹ��޸�֮��
 * 
 * @Author ������
 * @Date 2008-5-14
 * @Mail nswt@nswt.com.cn
 */
public class SelectControlConvert {
	static Pattern pattern = Pattern.compile("<z\\:[^>]*?> (\\$\\{.*?\\})<\\/z\\:.*?>", Pattern.CASE_INSENSITIVE
			| Pattern.DOTALL);

	public static void main(String[] args) {
		long t = System.currentTimeMillis();

		File f = new File(Config.getContextRealPath());
		dealPath(f);

		System.out.println(System.currentTimeMillis() - t);
	}

	public static void dealPath(File parent) {
		if (parent.isFile()) {
			if (!parent.getName().toLowerCase().endsWith(".jsp")) {
				return;
			}
			String txt = FileUtil.readText(parent);
			int lastIndex = 0;
			Matcher m = pattern.matcher(txt);
			while (m.find(lastIndex)) {
				lastIndex = m.end();
				System.out.println(parent.getName());
				System.out.println(m.group(0));
				System.out.println("\n\n");
				String tag = m.group(0);
				tag = StringUtil.replaceEx(tag, ">$", "> $");
				txt = StringUtil.replaceEx(txt, m.group(0), tag);
				String fileName = parent.getAbsolutePath();
				fileName = fileName.replaceAll("UI", "Patch");
				String path = fileName.substring(0, fileName.lastIndexOf("\\"));
				FileUtil.mkdir(path);
				FileUtil.writeText(fileName, txt);
			}

		} else {
			File[] fs = parent.listFiles();
			for (int i = 0; i < fs.length; i++) {
				dealPath(fs[i]);
			}
		}
	}
}
