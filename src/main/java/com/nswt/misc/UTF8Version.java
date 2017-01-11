package com.nswt.misc;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import com.nswt.framework.utility.FileUtil;
import com.nswt.framework.utility.StringUtil;

/**
 * ��GBK�汾ת��UTF�汾
 */
public class UTF8Version {
	static Pattern p1 = Pattern.compile("charset\\s*\\=\\s*gbk", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	static Pattern p2 = Pattern.compile("charset\\s*\\=\\s*gb2312", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	private String target = null;

	private ArrayList list = new ArrayList();

	private long lastConvertTime = 0;// ���ת��ʱ��

	private String currentBase;// ��ǰת����Ŀ¼��׼·��

	private String currentDest;// ��ǰת����Ŀ��Ŀ¼

	public static void main(String[] args) {
		long t = System.currentTimeMillis();
		UTF8Version uv = new UTF8Version();
		uv.setTarget("F:/WorkSpace_Product/nswtp1.3UTF8/");

		uv.addDir("F:/WorkSpace_Product/nswtp/Java", "Java");
		uv.addDir("F:/WorkSpace_Product/nswtp/UI", "UI");
		uv.addDir("F:/WorkSpace_Product/nswtp/UI/wwwroot/nswtpDemo", "UI/wwwroot/nswtpDemo");
		uv.addDir("F:/WorkSpace_Platform/Framework/Java", "Java");

		uv.convert();
		System.out.println("ɨ��Ķ�����ʱ:" + (System.currentTimeMillis() - t));
	}

	/**
	 * ����Ŀ��Ŀ¼
	 */
	public void setTarget(String target) {
		this.target = target;
	}

	/**
	 * ����һ����ת����Ŀ¼��������Ŀ¼�µ������ļ�ת����Ŀ��Ŀ¼��ȥ
	 */
	public void addDir(String src, String dest) {
		list.add(new String[] { src, dest });
	}

	/**
	 * ���ԴĿ¼���Ѿ�ɾ������Ŀ��Ŀ¼�л����ڵ��ļ�
	 */
	private void cleanDeleted(String path) {
		File f = new File(path);
		File[] fs = f.listFiles();
		for (int i = 0; fs != null && i < fs.length; i++) {

		}
	}

	/**
	 * ת��Ŀ¼ΪUTF8��ʽ
	 */
	public void convert() {
		long current = System.currentTimeMillis();
		File f = new File(target + "/convert.lock");
		if (f.exists()) {
			lastConvertTime = Long.parseLong(FileUtil.readText(f).trim());
		}
		for (int i = 0; i < list.size(); i++) {
			String[] arr = (String[]) list.get(i);
			currentBase = StringUtil.replaceEx(arr[0], "\\", "/");
			currentDest = arr[1];
			convertDir(arr[0]);
		}
		FileUtil.writeText(target + "/convert.lock", "" + current);
		cleanDeleted(target);
	}

	private void convertDir(String src) {
		File f = new File(src);
		File[] fs = f.listFiles();

		for (int i = 0; fs != null && i < fs.length; i++) {
			f = fs[i];
			String path = StringUtil.replaceEx(f.getAbsolutePath(), "\\", "/");
			path = path.substring(currentBase.length());
			new File(target + currentDest).mkdirs();
			String dest = target + currentDest + path;
			String name = f.getName().toLowerCase();
			if (name.equals(".svn")) {
				continue;
			}
			if (f.isDirectory()) {
				FileUtil.mkdir(dest);
				if (name.equals("classes")) {
					continue;
				}
				if (name.equals("classes")) {
					continue;
				}
				if (dest.indexOf("wwwroot") >= 0 && currentBase.indexOf("wwwroot") < 0) {
					continue;
				}
				if (dest.indexOf("WEB-INF/cache") >= 0) {
					continue;
				}
				if (dest.indexOf("WEB-INF/backup") >= 0) {
					continue;
				}
				if (f.getName().equals(".svn")) {
					continue;
				}
				if (f.getName().equals("classes")) {
					continue;
				}
				if (f.getName().toLowerCase().equals("tools")) {
					continue;
				}
				if (f.getName().toLowerCase().equals("test")) {
					continue;
				}
				if (f.getName().toLowerCase().equals("project")) {
					continue;
				}
				if (dest.indexOf("WEB-INF/data/index") >= 0) {
					continue;
				}
				if (f.getName().equals("logs")) {
					continue;
				}
				convertDir(f.getAbsolutePath());
				continue;
			}
			if (f.isFile()) {
				File destFile = new File(dest);
				if (destFile.exists() && f.lastModified() < lastConvertTime) {// �ļ�����������Ҫ����
					continue;
				}
			}
			if (dest.indexOf("wwwroot") > 0 && dest.endsWith(".shtml")) {
				continue;
			}
			if (name.endsWith(".java") || name.endsWith(".xml")) {
				String txt = FileUtil.readText(f, "GBK");
				if (name.endsWith(".xml")) {// xml�ļ��󲿷���utf-8
					if (txt.indexOf("UTF-8") > 0) {
						txt = FileUtil.readText(f, "UTF-8");
					}
				}
				if (name.equals("web.xml")) {
					txt = StringUtil.replaceEx(txt, "<page-encoding>GBK</page-encoding>",
							"<page-encoding>UTF-8</page-encoding>");
				}
				if (name.equals("constant.java")) {// ��Ҫ���⴦��Constant.java
					txt = StringUtil.replaceEx(txt, "GlobalCharset = \"GBK\";", "GlobalCharset = \"UTF-8\";");
				}
				try {
					txt = new String(StringUtil.GBKToUTF8(txt), "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				FileUtil.writeText(dest, txt, "UTF-8", name.endsWith(".java"));
			} else if (name.endsWith(".html") || name.endsWith(".shtml") || name.endsWith(".htm")
					|| name.endsWith(".jsp") || name.endsWith(".js") || name.endsWith(".css")) {
				byte[] bs = FileUtil.readByte(f);
				if (StringUtil.isUTF8(bs)) {
					FileUtil.copy(f, dest);
				} else {
					String txt = null;
					try {
						txt = new String(bs, "GBK");
					} catch (UnsupportedEncodingException e1) {
						e1.printStackTrace();
					}
					txt = p1.matcher(txt).replaceAll("charset=utf-8");
					txt = p2.matcher(txt).replaceAll("charset=utf-8");
					txt = StringUtil.replaceEx(txt, "\"GBK\"", "\"UTF-8\"");// Search/Result.jsp
					try {
						txt = new String(StringUtil.GBKToUTF8(txt), "UTF-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					FileUtil.writeText(dest, txt, "UTF-8");
				}
			} else if (name.equalsIgnoreCase("charset.config")) {
				String txt = FileUtil.readText(f, "UTF-8");
				txt = StringUtil.replaceEx(txt, "GBK", "UTF-8");
				FileUtil.writeText(dest, txt, "UTF-8");
			} else {
				FileUtil.copy(f, dest);
			}
			System.out.println(dest);
		}
	}
}
