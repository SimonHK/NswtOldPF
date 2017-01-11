package com.nswt.framework.utility;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;

import org.apache.commons.lang.ArrayUtils;

/**
 * ����ת��������
 */
public class CharsetConvert {
	static Pattern GBKPattern1 = Pattern.compile("charset\\s*\\=\\s*gbk", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	static Pattern GBKPattern2 = Pattern.compile("charset\\s*\\=\\s*gb2312", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	static Pattern UTF8Pattern = Pattern
			.compile("charset\\s*\\=\\s*utf\\-8", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	/**
	 * ��һ��GBK������ַ���ת��UTF-8����Ķ��������飬�������bomFlagΪtrue����ת������BOMλ
	 */
	public static byte[] GBKToUTF8(String chinese, boolean bomFlag) {
		char c[] = chinese.toCharArray();
		byte[] fullByte = new byte[3 * c.length];
		for (int i = 0; i < c.length; i++) {
			int m = (int) c[i];
			String word = Integer.toBinaryString(m);
			StringBuffer sb = new StringBuffer();
			int len = 16 - word.length();
			for (int j = 0; j < len; j++) {
				sb.append("0");
			}
			sb.append(word);
			sb.insert(0, "1110");
			sb.insert(8, "10");
			sb.insert(16, "10");
			String s1 = sb.substring(0, 8);
			String s2 = sb.substring(8, 16);
			String s3 = sb.substring(16);
			byte b0 = Integer.valueOf(s1, 2).byteValue();
			byte b1 = Integer.valueOf(s2, 2).byteValue();
			byte b2 = Integer.valueOf(s3, 2).byteValue();
			byte[] bf = new byte[3];
			bf[0] = b0;
			fullByte[i * 3] = bf[0];
			bf[1] = b1;
			fullByte[i * 3 + 1] = bf[1];
			bf[2] = b2;
			fullByte[i * 3 + 2] = bf[2];
		}
		if (bomFlag) {
			return ArrayUtils.addAll(StringUtil.BOM, fullByte);
		}
		return fullByte;
	}

	/**
	 * ��UTF-8������ַ���ת��GBK������ַ���
	 */
	public static byte[] UTF8ToGBK(String str) {
		try {
			byte[] bs = str.getBytes("UTF-8");
			if (StringUtil.hexEncode(ArrayUtils.subarray(bs, 0, 3)).equals("efbbbf")) {// BOM��־
				bs = ArrayUtils.subarray(bs, 3, bs.length);
			}
			str = new String(bs, "UTF-8");
			return new String(str.getBytes(), "GBK").getBytes();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ������Ŀ¼������ָ����׺���ı��ļ�������GBKתΪUTF8��<br>
	 * ��׺����shtml,html,htm,jsp,js,css���Լ���ص�xml,template�ļ�
	 */
	public static void dirGBKToUTF8(String src) {
		File f = new File(src);
		File[] fs = f.listFiles();

		for (int i = 0; fs != null && i < fs.length; i++) {
			f = fs[i];
			String name = f.getName().toLowerCase();
			String dest = f.getAbsolutePath();
			if (name.equals(".svn")) {
				continue;
			}
			if (name.equals("WEB-INF")) {// ��һ��������ı��ļ��������ȷ�������������ֱ���
				continue;
			}
			if (f.isDirectory()) {
				dirGBKToUTF8(f.getAbsolutePath());
				continue;
			}
			if (name.endsWith(".template") || name.endsWith(".xml")) {
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
				if (name.equals("framework.xml")) {
					txt = StringUtil.replaceEx(txt, "<config name=\"Charset\">GBK</config>",
							"<config name=\"Charset\">UTF-8</config>");
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
				FileUtil.writeByte(f, webFileGBKToUTF8(bs));
			} else {// Java�ļ��Լ������ļ�
				if (!StringUtil.isUTF8(FileUtil.readByte(f))) {
					try {
						String txt = new String(StringUtil.GBKToUTF8(FileUtil.readText(f, "GBK")), "UTF-8");
						FileUtil.writeText(dest, txt, "UTF-8", name.endsWith(".java"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * ������Ŀ¼������ָ����׺���ı��ļ�������UTF8תΪGBK��<br>
	 * ��׺����shtml,html,htm,jsp,js,css���Լ���ص�xml,template�ļ�
	 */
	public static void dirUTF8ToGBK(String src) {
		File f = new File(src);
		File[] fs = f.listFiles();

		for (int i = 0; fs != null && i < fs.length; i++) {
			f = fs[i];
			String name = f.getName().toLowerCase();
			String dest = f.getAbsolutePath();
			if (name.equals(".svn")) {
				continue;
			}
			if (f.isDirectory()) {
				dirUTF8ToGBK(f.getAbsolutePath());
				continue;
			}
			if (name.endsWith(".template") || name.endsWith(".xml")) {
				String txt = FileUtil.readText(f, "UTF-8");
				if (name.endsWith(".xml")) {// xml�ļ��󲿷���utf-8
					if (name.equals("web.xml")) {
						txt = StringUtil.replaceEx(txt, "<page-encoding>UTF-8</page-encoding>",
								"<page-encoding>GBK</page-encoding>");
					}
					if (name.equals("framework.xml")) {
						txt = StringUtil.replaceEx(txt, "<config name=\"Charset\">UTF-8</config>",
								"<config name=\"Charset\">GBK</config>");
					}
					FileUtil.writeText(dest, txt, "UTF-8");
					continue;
				}
				try {
					txt = new String(StringUtil.UTF8ToGBK(txt), "GBK");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				FileUtil.writeText(dest, txt, "UTF-8");
			} else if (name.endsWith(".html") || name.endsWith(".shtml") || name.endsWith(".htm")
					|| name.endsWith(".jsp") || name.endsWith(".js") || name.endsWith(".css")) {
				byte[] bs = FileUtil.readByte(f);
				FileUtil.writeByte(f, webFileUTF8ToGBK(bs));
			} else {// Java�ļ��Լ������ļ�
				if (StringUtil.isUTF8(FileUtil.readByte(f))) {
					try {
						String txt = new String(StringUtil.UTF8ToGBK(FileUtil.readText(f, "UTF-8")), "GBK");
						FileUtil.writeText(dest, txt, "GBK");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * ��Webҳ�漰���õ��ļ������GBKתΪUTF8
	 */
	public static byte[] webFileGBKToUTF8(byte[] bs) {
		if (!StringUtil.isUTF8(bs)) {
			String txt = null;
			try {
				txt = new String(bs, "GBK");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			txt = GBKPattern1.matcher(txt).replaceAll("charset=utf-8");
			txt = GBKPattern2.matcher(txt).replaceAll("charset=utf-8");
			txt = StringUtil.replaceEx(txt, "\"GBK\"", "\"UTF-8\"");// Search/Result.jsp
			try {
				txt = new String(StringUtil.GBKToUTF8(txt), "UTF-8");
				return txt.getBytes("UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return bs;
	}

	/**
	 * ��Webҳ�漰���õ��ļ������UTF8תΪGBK
	 */
	public static byte[] webFileUTF8ToGBK(byte[] bs) {
		if (StringUtil.isUTF8(bs)) {
			String txt = null;
			try {
				txt = new String(bs, "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			txt = UTF8Pattern.matcher(txt).replaceAll("charset=GBK");
			txt = StringUtil.replaceEx(txt, "\"UTF-8\"", "\"GBK\"");// Search/Result.jsp
			try {
				txt = new String(StringUtil.UTF8ToGBK(txt), "GBK");
				return txt.getBytes("GBK");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return bs;
	}
}
