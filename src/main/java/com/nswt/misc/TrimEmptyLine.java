package com.nswt.misc;

import java.io.File;

import com.nswt.framework.utility.FileUtil;
import com.nswt.framework.utility.StringUtil;

/**
 * ȥ��JSPҳ���ж������.(�ļ��ӷ������ϴ�����ȥʱ���ܻ��г�����������.)
 * 
 * @Author ������
 * @Date 2009-4-22
 * @Mail nswt@nswt.com.cn
 */
public class TrimEmptyLine {

	public static void main(String[] args) {
		String path = "F:/Workspace_Platform/Platform/UI/Search/";
		File[] fs = new File(path).listFiles();
		for (int i = 0; i < fs.length; i++) {
			File f = fs[i];
			if (!f.getName().endsWith("jsp")) {
				continue;
			}
			String content = FileUtil.readText(f);
			String[] arr = content.split("\\n");
			StringBuffer sb = new StringBuffer();
			for (int j = 0; j < arr.length; j++) {
				if (StringUtil.isEmpty(arr[j].trim())) {
					if (j < arr.length - 1 && StringUtil.isEmpty(arr[j + 1].trim())) {
						sb.append(arr[j].trim());
						sb.append("\n");
					}
				} else {
					sb.append(StringUtil.rightTrim(arr[j]));
					sb.append("\n");
				}
			}
			FileUtil.writeText(f.getAbsolutePath(), sb.toString());
		}
	}
}
