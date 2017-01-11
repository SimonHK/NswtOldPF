package com.nswt.misc;

import java.io.File;

import com.nswt.framework.utility.CharsetConvert;
import com.nswt.framework.utility.FileUtil;
import com.nswt.framework.utility.StringUtil;
import com.nswt.framework.utility.ZipUtil;

/**
 * 1.3发版工具类
 * 
 * 日期 : 2010-6-4 <br>
 * 作者: NSWT <br>
 * 邮箱：nswt@nswt.com.cn <br>
 */
public class VersionBuilder {

	public static void main(String[] args) {
		String prefix = "F:/Workspace_Product/nswtp1.3UTF8/";
		// 删除不需要的文件夹
		FileUtil.delete(prefix + "UI/Test");
		FileUtil.delete(prefix + "UI/Project");
		FileUtil.delete(prefix + "UI/WEB-INF/classes/framework.xml");
		FileUtil.delete(prefix + "UI/WEB-INF/classes/com/nswt/project");
		File[] fs = new File(prefix + "UI/wwwroot/nswtpDemo").listFiles();
		String[] names = new String[] { "cache", "include", "template", "SpryAssets", "js", "images", "upload",
				"index.shtml", "form.shtml" };
		for (int i = 0; i < fs.length; i++) {
			boolean flag = false;
			for (int j = 0; j < names.length; j++) {
				if (names[j].equalsIgnoreCase(fs[i].getName())) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				FileUtil.delete(fs[i]);
			}
		}
		generateWar(prefix + "UI/", prefix + "nswtp_1.3_final_utf8.war");

		// 以下打包GBK版war文件
		FileUtil.delete(prefix + "GBK");
		FileUtil.copy(prefix + "UI", prefix + "GBK");
		String txt = FileUtil.readText(prefix + "GBK/WEB-INF/classes/charset.config", "UTF-8");
		txt = StringUtil.replaceEx(txt, "UTF-8", "GBK");
		FileUtil.writeText(prefix + "GBK/WEB-INF/classes/charset.config", txt, "UTF-8");
		CharsetConvert.dirUTF8ToGBK(prefix + "GBK");
		generateWar(prefix + "GBK/", prefix + "nswtp_1.3_final_gbk.war");
	}

	public static void generateWar(String src, String dest) {
		try {
			File[] fs = new File(src).listFiles();
			String[] arr = new String[fs.length];
			for (int i = 0; i < arr.length; i++) {
				arr[i] = fs[i].getAbsolutePath();
			}
			ZipUtil.zipBatch(arr, dest);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
