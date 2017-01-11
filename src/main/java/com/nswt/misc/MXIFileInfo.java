package com.nswt.misc;

import java.io.File;

/**
 * 输出Dreamweaver插件的文件列表信息
 * 
 * @Author NSWT
 * @Date 2009-3-31
 * @Mail nswt@nswt.com.cn
 */
public class MXIFileInfo {

	public static void main(String[] args) {
		File[] fs = new File("D:/Program Files/Adobe/Adobe Extension Manager CS4/Samples/Dreamweaver/nswtp").listFiles();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < fs.length; i++) {
			String name = fs[i].getName();
			String prefix = name.substring(0, name.indexOf('.'));
			System.out.println("<file source=\"" + name + "\" destination=\"$dreamweaver/configuration/Objects/nswtp\" />");
			if (name.endsWith(".htm")) {
				sb.append("<button file=\"nswtp/" + name + "\" id=\"nswtp_Template_" + prefix + "\" image=\"nswtp/" + prefix
						+ ".gif\" />\n");
			}
		}
		System.out.println("\n\n");
		System.out.println(sb);
	}
}
