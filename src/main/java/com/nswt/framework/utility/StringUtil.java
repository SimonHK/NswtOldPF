package com.nswt.framework.utility;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.net.URLCodec;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringEscapeUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.nswt.framework.Constant;
import com.nswt.framework.VerifyRule;
import com.nswt.framework.license.SystemInfo;

/*
 * �������� 2005-7-6 
 * ����: NSWT
 * ����:nswt@nswt.com.cn
 */
public class StringUtil {
	/**
	 * UTF-8�������ֽڵ�BOM
	 */
	public static final byte[] BOM = new byte[] { (byte) 239, (byte) 187, (byte) 191 };

	/**
	 * ʮ�������ַ�
	 */
	public static final char HexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
			'f' };

	/**
	 * ��ȡָ���ַ�����MD5ժҪ
	 */
	public static byte[] md5(String src) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] md = md5.digest(src.getBytes());
			return md;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * ��ȡָ�������������MD5ժҪ
	 */
	public static byte[] md5(byte[] src) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] md = md5.digest(src);
			return md;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * ���ַ�������md5ժҪ��Ȼ�������ʮ��������ʽ
	 */
	public static String md5Hex(String src) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] md = md5.digest(src.getBytes());
			return hexEncode(md);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * ���ַ�������sh1ժҪ��Ȼ�������ʮ��������ʽ
	 */
	public static String sha1Hex(String src) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("SHA1");
			byte[] md = md5.digest(src.getBytes());
			return hexEncode(md);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * ���ַ�������MD5ժҪ�������BASE64��ʽ
	 */
	public static String md5Base64(String str) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			return base64Encode(md5.digest(str.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ʮ���ƽ���ʾMD5ժҪת����BASE64��ʽ
	 */
	public static String md5Base64FromHex(String md5str) {
		char[] cs = md5str.toCharArray();
		byte[] bs = new byte[16];
		for (int i = 0; i < bs.length; i++) {
			char c1 = cs[i * 2];
			char c2 = cs[i * 2 + 1];
			byte m1 = 0;
			byte m2 = 0;
			for (byte k = 0; k < 16; k++) {
				if (HexDigits[k] == c1) {
					m1 = k;
				}
				if (HexDigits[k] == c2) {
					m2 = k;
				}
			}
			bs[i] = (byte) (m1 << 4 | 0x0 + m2);

		}
		String newstr = base64Encode(bs);
		return newstr;
	}

	/**
	 * ��ʮ���ƽ���ʾMD5ժҪת����BASE64��ʽ
	 */
	public static String md5HexFromBase64(String base64str) {
		return hexEncode(base64Decode(base64str));
	}

	/**
	 * ������������ת����ʮ�����Ʊ�ʾ
	 */
	public static String hexEncode(byte[] bs) {
		return new String(new Hex().encode(bs));
	}

	/**
	 * ���ַ���ת����ʮ�����Ʊ�ʾ
	 */
	public static byte[] hexDecode(String str) {
		try {
			if (str.endsWith("\n")) {
				str = str.substring(0, str.length() - 1);
			}
			char[] cs = str.toCharArray();
			return Hex.decodeHex(cs);
		} catch (DecoderException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ���ֽ�����ת���ɶ�����ʽ�ַ���
	 */
	public static String byteToBin(byte[] bs) {
		char[] cs = new char[bs.length * 9];
		for (int i = 0; i < bs.length; i++) {
			byte b = bs[i];
			int j = i * 9;
			cs[j] = (b >>> 7 & 1) == 1 ? '1' : '0';
			cs[j + 1] = (b >>> 6 & 1) == 1 ? '1' : '0';
			cs[j + 2] = (b >>> 5 & 1) == 1 ? '1' : '0';
			cs[j + 3] = (b >>> 4 & 1) == 1 ? '1' : '0';
			cs[j + 4] = (b >>> 3 & 1) == 1 ? '1' : '0';
			cs[j + 5] = (b >>> 2 & 1) == 1 ? '1' : '0';
			cs[j + 6] = (b >>> 1 & 1) == 1 ? '1' : '0';
			cs[j + 7] = (b & 1) == 1 ? '1' : '0';
			cs[j + 8] = ',';
		}
		return new String(cs);
	}

	/**
	 * ת���ֽ�����Ϊʮ�������ִ�
	 */

	public static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
			resultSb.append(" ");
		}
		return resultSb.toString();
	}

	/**
	 * �ֽ�ת��Ϊʮ�������ַ���
	 */
	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0) {
			n = 256 + n;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return HexDigits[d1] + "" + HexDigits[d2];
	}

	/**
	 * �ж�ָ���Ķ����������Ƿ���һ��UTF-8�ַ���
	 */
	public static boolean isUTF8(byte[] bs) {
		if (StringUtil.hexEncode(ArrayUtils.subarray(bs, 0, 3)).equals("efbbbf")) {// BOM��־
			return true;
		}
		int lLen = bs.length;
		for (int i = 0; i < lLen;) {
			byte b = bs[i++];
			if (b >= 0) {
				continue;// >=0 is normal ascii
			}
			if (b < (byte) 0xc0 || b > (byte) 0xfd) {
				return false;
			}
			int c = b > (byte) 0xfc ? 5 : b > (byte) 0xf8 ? 4 : b > (byte) 0xf0 ? 3 : b > (byte) 0xe0 ? 2 : 1;
			if (i + c > lLen) {
				return false;
			}
			for (int j = 0; j < c; ++j, ++i) {
				if (bs[i] >= (byte) 0xc0) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * ���ض����������BASE64������
	 */
	public static String base64Encode(byte[] b) {
		if (b == null) {
			return null;
		}
		return (new BASE64Encoder()).encode(b);
	}

	/**
	 * �� BASE64 ������ַ������н���
	 */
	public static byte[] base64Decode(String s) {
		if (s != null) {
			BASE64Decoder decoder = new BASE64Decoder();
			try {
				return decoder.decodeBuffer(s);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * ���ַ���ת���ɿ�����JAVA���ʽ��ֱ��ʹ�õ��ַ���������һЩת���ַ�
	 */
	public static String javaEncode(String txt) {
		if (txt == null || txt.length() == 0) {
			return txt;
		}
		txt = replaceEx(txt, "\\", "\\\\");
		txt = replaceEx(txt, "\r\n", "\n");
		txt = replaceEx(txt, "\r", "\\r");
		txt = replaceEx(txt, "\t", "\\t");
		txt = replaceEx(txt, "\n", "\\n");
		txt = replaceEx(txt, "\"", "\\\"");
		txt = replaceEx(txt, "\'", "\\\'");
		return txt;
	}

	/**
	 * ��StringUtil.javaEncode()��������ַ���ԭ
	 */
	public static String javaDecode(String txt) {
		if (txt == null || txt.length() == 0) {
			return txt;
		}
		StringBuffer sb = new StringBuffer();
		int lastIndex = 0;
		while (true) {
			int index = txt.indexOf("\\", lastIndex);
			if (index < 0) {
				break;
			}
			sb.append(txt.substring(lastIndex, index));
			if (index < txt.length() - 1) {
				char c = txt.charAt(index + 1);
				if (c == 'n') {
					sb.append("\n");
				} else if (c == 'r') {
					sb.append("\r");
				} else if (c == '\'') {
					sb.append("\'");
				} else if (c == '\"') {
					sb.append("\"");
				} else if (c == '\\') {
					sb.append("\\");
				}
				lastIndex = index + 2;
				continue;
			} else {
				sb.append(txt.substring(index, index + 1));
			}
			lastIndex = index + 1;
		}
		sb.append(txt.substring(lastIndex));
		return sb.toString();
	}

	/**
	 * ��һ���ַ�������ָ�µķָ��ַ����ָ�����顣�ָ��ַ�������������ʽ����<br>
	 * String���split����Ҫ����������ʽ�ָ��ַ�������ʱ��Ϊ���㣬����תΪ���ñ�������
	 */
	public static String[] splitEx(String str, String spilter) {
		if (str == null) {
			return null;
		}
		if (spilter == null || spilter.equals("") || str.length() < spilter.length()) {
			String[] t = { str };
			return t;
		}
		ArrayList al = new ArrayList();
		char[] cs = str.toCharArray();
		char[] ss = spilter.toCharArray();
		int length = spilter.length();
		int lastIndex = 0;
		for (int i = 0; i <= str.length() - length;) {
			boolean notSuit = false;
			for (int j = 0; j < length; j++) {
				if (cs[i + j] != ss[j]) {
					notSuit = true;
					break;
				}
			}
			if (!notSuit) {
				al.add(str.substring(lastIndex, i));
				i += length;
				lastIndex = i;
			} else {
				i++;
			}
		}
		if (lastIndex <= str.length()) {
			al.add(str.substring(lastIndex, str.length()));
		}
		String[] t = new String[al.size()];
		for (int i = 0; i < al.size(); i++) {
			t[i] = (String) al.get(i);
		}
		return t;
	}

	/**
	 * ��һ���ַ����е�ָ��Ƭ��ȫ���滻���滻�����в�����������<br>
	 * ʹ��String���replaceAllʱҪ��Ƭ����������ʽ��ʽ��������ʱ��Ϊ���㣬����תΪ���ñ�������
	 */
	public static String replaceEx(String str, String subStr, String reStr) {
		if (str == null) {
			return null;
		}
		if (subStr == null || subStr.equals("") || subStr.length() > str.length() || reStr == null) {
			return str;
		}
		StringBuffer sb = new StringBuffer();
		int lastIndex = 0;
		while (true) {
			int index = str.indexOf(subStr, lastIndex);
			if (index < 0) {
				break;
			} else {
				sb.append(str.substring(lastIndex, index));
				sb.append(reStr);
			}
			lastIndex = index + subStr.length();
		}
		sb.append(str.substring(lastIndex));
		return sb.toString();
	}

	/**
	 * �����ִ�Сд��ȫ���滻���滻ʱʹ����������ʽ��
	 */
	public static String replaceAllIgnoreCase(String source, String oldstring, String newstring) {
		Pattern p = Pattern.compile(oldstring, Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(source);
		return m.replaceAll(newstring);
	}

	/**
	 * ��ȫ�ֱ������URL����
	 */
	public static String urlEncode(String str) {
		return urlEncode(str, Constant.GlobalCharset);
	}

	/**
	 * ��ȫ�ֱ������URL����
	 */
	public static String urlDecode(String str) {
		return urlDecode(str, Constant.GlobalCharset);
	}

	/**
	 * ��ָ���������URL����
	 */
	public static String urlEncode(String str, String charset) {
		try {
			return new URLCodec().encode(str, charset);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ָ���������URL����
	 */
	public static String urlDecode(String str, String charset) {
		try {
			return new URLCodec().decode(str, charset);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ���ַ�������HTML����
	 */
	public static String htmlEncode(String txt) {
		return StringEscapeUtils.escapeHtml(txt);
	}

	/**
	 * ���ַ�������HTML����
	 */
	public static String htmlDecode(String txt) {
		txt = replaceEx(txt, "&#8226;", "��");
		return StringEscapeUtils.unescapeHtml(txt);
	}

	/**
	 * �滻�ַ����е�˫����
	 */
	public static String quotEncode(String txt) {
		if (txt == null || txt.length() == 0) {
			return txt;
		}
		txt = replaceEx(txt, "&", "&amp;");
		txt = replaceEx(txt, "\"", "&quot;");
		return txt;
	}

	/**
	 * ��ԭͨ��StringUtil.quotEncode()������ַ���
	 */
	public static String quotDecode(String txt) {
		if (txt == null || txt.length() == 0) {
			return txt;
		}
		txt = replaceEx(txt, "&quot;", "\"");
		txt = replaceEx(txt, "&amp;", "&");
		return txt;
	}

	/**
	 * Javascript��escape��JAVAʵ��
	 */
	public static String escape(String src) {
		char j;
		StringBuffer sb = new StringBuffer();
		sb.ensureCapacity(src.length() * 6);
		for (int i = 0; i < src.length(); i++) {
			j = src.charAt(i);
			if (Character.isDigit(j) || Character.isLowerCase(j) || Character.isUpperCase(j)) {
				sb.append(j);
			} else if (j < 256) {
				sb.append("%");
				if (j < 16) {
					sb.append("0");
				}
				sb.append(Integer.toString(j, 16));
			} else {
				sb.append("%u");
				sb.append(Integer.toString(j, 16));
			}
		}
		return sb.toString();
	}

	/**
	 * Javascript��unescape��JAVAʵ��
	 */
	public static String unescape(String src) {
		StringBuffer sb = new StringBuffer();
		sb.ensureCapacity(src.length());
		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
					sb.append(ch);
					lastPos = pos + 6;
				} else {
					ch = (char) Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
					sb.append(ch);
					lastPos = pos + 3;
				}
			} else {
				if (pos == -1) {
					sb.append(src.substring(lastPos));
					lastPos = src.length();
				} else {
					sb.append(src.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}
		return sb.toString();
	}

	/**
	 * ��һ�ַ�������������ָ���ַ���ʹ�䳤�ȴﵽָ������
	 */
	public static String leftPad(String srcString, char c, int length) {
		if (srcString == null) {
			srcString = "";
		}
		int tLen = srcString.length();
		int i, iMax;
		if (tLen >= length)
			return srcString;
		iMax = length - tLen;
		StringBuffer sb = new StringBuffer();
		for (i = 0; i < iMax; i++) {
			sb.append(c);
		}
		sb.append(srcString);
		return sb.toString();
	}

	/**
	 * �����ȳ���length���ַ�����ȡlength���ȣ������㣬�򷵻�ԭ��
	 */
	public static String subString(String src, int length) {
		if (src == null) {
			return null;
		}
		int i = src.length();
		if (i > length) {
			return src.substring(0, length);
		} else {
			return src;
		}
	}

	/**
	 * �����ȳ���length���ַ�����ȡlength���ȣ������㣬�򷵻�ԭ����<br>
	 * ����ASCII�ַ�ֻ�������ȵ�λ��
	 */
	public static String subStringEx(String src, int length) {
		length = length * 2;
		if (src == null) {
			return null;
		}
		int k = lengthEx(src);
		if (k > length) {
			int m = 0;
			boolean unixFlag = false;
			String osname = System.getProperty("os.name").toLowerCase();
			if (osname.indexOf("sunos") > 0 || osname.indexOf("solaris") > 0 || osname.indexOf("aix") > 0) {
				unixFlag = true;
			}
			try {
				byte[] b = src.getBytes("Unicode");
				for (int i = 2; i < b.length; i += 2) {
					byte flag = b[i + 1];
					if (unixFlag) {
						flag = b[i];
					}
					if (flag == 0) {
						m++;
					} else {
						m += 2;
					}
					if (m > length) {
						return src.substring(0, (i - 2) / 2);
					}
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				throw new RuntimeException("ִ�з���getBytes(\"Unicode\")ʱ����");
			}
		}
		return src;
	}

	/**
	 * ����ַ����ĳ��ȣ�����ASCII�ַ���1�����ȵ�λ,��ASCII�ַ����������ȵ�λ
	 */
	public static int lengthEx(String src) {
		int length = 0;
		boolean unixFlag = false;
		String osname = System.getProperty("os.name").toLowerCase();
		if (osname.indexOf("sunos") > 0 || osname.indexOf("solaris") > 0 || osname.indexOf("aix") > 0) {
			unixFlag = true;
		}
		try {
			byte[] b = src.getBytes("Unicode");
			for (int i = 2; i < b.length; i += 2) {
				byte flag = b[i + 1];
				if (unixFlag) {
					flag = b[i];
				}
				if (flag == 0) {
					length++;
				} else {
					length += 2;
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException("ִ�з���getBytes(\"Unicode\")ʱ����");
		}
		return length;
	}

	/**
	 * ��һ�ַ����ұ��������ָ���ַ���ʹ�䳤�ȴﵽָ������
	 */
	public static String rightPad(String srcString, char c, int length) {
		if (srcString == null) {
			srcString = "";
		}
		int tLen = srcString.length();
		int i, iMax;
		if (tLen >= length)
			return srcString;
		iMax = length - tLen;
		StringBuffer sb = new StringBuffer();
		sb.append(srcString);
		for (i = 0; i < iMax; i++) {
			sb.append(c);
		}
		return sb.toString();
	}

	/**
	 * ��ָ����У�����У���ַ���
	 */
	public static boolean verify(String value, String rule) {
		VerifyRule vr = new VerifyRule(rule);
		boolean flag = vr.verify(value);
		return flag;
	}

	/**
	 * ����ַ��ұߵĿո�
	 */
	public static String rightTrim(String src) {
		if (src != null) {
			char[] chars = src.toCharArray();
			for (int i = chars.length - 1; i >= 0; i--) {
				if (chars[i] == ' ' || chars[i] == '\t' || chars[i] == '\r') {
					continue;
				} else {
					return src.substring(0, i + 1);
				}
			}
			return "";// ˵��ȫ�ǿո�
		}
		return src;
	}

	/**
	 * ���������ַ������������ַ����¿�����ȷת��
	 */
	public static void printStringWithAnyCharset(String str) {
		Map map = Charset.availableCharsets();
		Object[] keys = map.keySet().toArray();
		for (int i = 0; i < keys.length; i++) {
			LogUtil.info(keys[i]);
			for (int j = 0; j < keys.length; j++) {
				System.out.print("\t");
				try {
					System.out.println("From " + keys[i] + " To " + keys[j] + ":"
							+ new String(str.getBytes(keys[i].toString()), keys[j].toString()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * ���תȫ�ǣ�ת��Ӣ����ĸ֮����ַ�
	 */
	public static String toSBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 32) {
				c[i] = (char) 12288;
				continue;
			}
			if ((c[i] > 64 && c[i] < 91) || (c[i] > 96 && c[i] < 123)) {
				continue;
			}

			if (c[i] < 127)
				c[i] = (char) (c[i] + 65248);
		}
		return new String(c);
	}

	/**
	 * ���תȫ�ǣ�ת������תΪȫ�ǵ��ַ�������Ӣ����ĸ
	 */
	public static String toNSBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 32) {
				c[i] = (char) 12288;
				continue;
			}

			if (c[i] < 127)
				c[i] = (char) (c[i] + 65248);
		}
		return new String(c);
	}

	/**
	 * ȫ��ת��ǵĺ��� ȫ�ǿո�Ϊ12288����ǿո�Ϊ32 <br>
	 * �����ַ����(33-126)��ȫ��(65281-65374)�Ķ�Ӧ��ϵ�ǣ������65248
	 */
	public static String toDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}

	/**
	 * �����ַ�����ȫƴ,�Ǻ���ת��Ϊȫƴ,�����ַ�������ת��
	 * 
	 * @param cnStr String �ַ���
	 * @return String ת����ȫƴ����ַ���
	 */
	public static String getChineseFullSpell(String cnStr) {
		if (null == cnStr || "".equals(cnStr.trim())) {
			return cnStr;
		}
		return ChineseSpelling.convert(cnStr);
	}

	/**
	 * �����ַ����ĵ�һ�����ֵ�ȫƴ
	 * 
	 * @param cnStr
	 * @return
	 */
	public static String getChineseFamilyNameSpell(String cnStr) {
		if (null == cnStr || "".equals(cnStr.trim())) {
			return cnStr;
		}
		return ChineseSpelling.convertName(cnStr);
	}

	public static String getChineseFirstAlpha(String cnStr) {
		if (null == cnStr || "".equals(cnStr.trim())) {
			return cnStr;
		}
		return ChineseSpelling.getFirstAlpha(cnStr);
	}

	public static final Pattern PTitle = Pattern.compile("<title>(.+?)</title>", Pattern.CASE_INSENSITIVE
			| Pattern.DOTALL);

	/**
	 * ��һ��html�ı�����ȡ��<title>��ǩ����
	 */
	public static String getHtmlTitle(File f) {
		String html = FileUtil.readText(f);
		String title = getHtmlTitle(html);
		return title;
	}

	/**
	 * ��һ��html�ı�����ȡ��<title>��ǩ����
	 */
	public static String getHtmlTitle(String html) {
		Matcher m = PTitle.matcher(html);
		if (m.find()) {
			return m.group(1).trim();
		}
		return null;
	}

	public static Pattern patternHtmlTag = Pattern.compile("<[^<>]+>", Pattern.DOTALL);

	/**
	 * ���HTML�ı������б�ǩ
	 */
	public static String clearHtmlTag(String html) {
		String text = patternHtmlTag.matcher(html).replaceAll("");
		if (isEmpty(text)) {
			return "";
		}
		text = StringUtil.htmlDecode(text);
		return text.replaceAll("[\\s��]{2,}", " ");
	}

	/**
	 * ����ĸ��д
	 */
	public static String capitalize(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return str;
		}
		return new StringBuffer(strLen).append(Character.toTitleCase(str.charAt(0))).append(str.substring(1))
				.toString();
	}

	/**
	 * �ַ����Ƿ�Ϊ�գ�null����ַ���ʱ����true,�����������false
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	/**
	 * �ַ����Ƿ�Ϊ�գ�null����ַ���ʱ����false,�����������true
	 */
	public static boolean isNotEmpty(String str) {
		return !StringUtil.isEmpty(str);
	}

	/**
	 * �ַ���Ϊ��ʱ����defaultString�����򷵻�ԭ��
	 */
	public static final String noNull(String string, String defaultString) {
		return isEmpty(string) ? defaultString : string;
	}

	/**
	 * �ַ���Ϊ��ʱ����defaultString�����򷵻ؿ��ַ���
	 */
	public static final String noNull(String string) {
		return noNull(string, "");
	}

	/**
	 * ��һ������ƴ��һ���ַ�����������֮���Զ��ŷָ�
	 */
	public static String join(Object[] arr) {
		return join(arr, ",");
	}

	/**
	 * ��һ����ά����ƴ��һ���ַ������ڶ�ά�Զ��ŷָ�����һά�Ի��зָ�
	 */
	public static String join(Object[][] arr) {
		return join(arr, "\n", ",");
	}

	/**
	 * ��һ��������ָ���ķָ���ƴ��һ���ַ���
	 */
	public static String join(Object[] arr, String spliter) {
		if (arr == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			if (i != 0) {
				sb.append(spliter);
			}
			sb.append(arr[i]);
		}
		return sb.toString();
	}

	/**
	 * ��һ����ά����ƴ��һ���ַ������ڶ�ά��ָ����spliter2�����ָ�����һά�Ի���spliter1�ָ�
	 */
	public static String join(Object[][] arr, String spliter1, String spliter2) {
		if (arr == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			if (i != 0) {
				sb.append(spliter2);
			}
			sb.append(join(arr[i], spliter2));
		}
		return sb.toString();
	}

	/**
	 * ��һ��Listƴ��һ���ַ�����������֮���Զ��ŷָ�
	 */
	public static String join(List list) {
		return join(list, ",");
	}

	/**
	 * ��һ��Listƴ��һ���ַ�����������֮����ָ���Ĳ���spliter�ָ�
	 */
	public static String join(List list, String spliter) {
		if (list == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			if (i != 0) {
				sb.append(spliter);
			}
			sb.append(list.get(i));
		}
		return sb.toString();
	}

	/**
	 * ����һ���ַ�����ĳһ�Ӵ����ֵĴ���
	 */
	public static int count(String str, String findStr) {
		int lastIndex = 0;
		int length = findStr.length();
		int count = 0;
		int start = 0;
		while ((start = str.indexOf(findStr, lastIndex)) >= 0) {
			lastIndex = start + length;
			count++;
		}
		return count;
	}

	public static final Pattern PLetterOrDigit = Pattern.compile("^\\w*$", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	public static final Pattern PLetter = Pattern.compile("^[A-Za-z]*$", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	public static final Pattern PDigit = Pattern.compile("^\\d*$", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	/**
	 * �ж��ַ����Ƿ�ȫ�������ֻ���ĸ���
	 */
	public static boolean isLetterOrDigit(String str) {
		return PLetterOrDigit.matcher(str).find();
	}

	/**
	 * �ж��ַ����Ƿ�ȫ����ĸ���
	 */
	public static boolean isLetter(String str) {
		return PLetter.matcher(str).find();
	}

	/**
	 * �ж��ַ����Ƿ�ȫ�����������
	 */
	public static boolean isDigit(String str) {
		if (StringUtil.isEmpty(str)) {
			return false;
		}
		return PDigit.matcher(str).find();
	}

	private static Pattern chinesePattern = Pattern.compile("[^\u4e00-\u9fa5]+", Pattern.CASE_INSENSITIVE
			| Pattern.DOTALL);

	/**
	 * �ж��ַ������Ƿ��������ַ�
	 */
	public static boolean containsChinese(String str) {
		if (!chinesePattern.matcher(str).matches()) {
			return true;
		}
		return false;
	}

	private static Pattern idPattern = Pattern.compile("[\\w\\s\\_\\.\\,]*", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	/**
	 * ���ID����ֹSQLע�룬��Ҫ����ɾ��ʱ������IDʱʹ��
	 */
	public static boolean checkID(String str) {
		if (StringUtil.isEmpty(str)) {
			return true;
		}
		if (idPattern.matcher(str).matches()) {
			return true;
		}
		return false;
	}

	/**
	 * ��һ��������Name=John,Age=18,Gender=3���ַ������һ��Mapx
	 */
	public static Mapx splitToMapx(String str, String entrySpliter, String keySpliter) {
		Mapx map = new Mapx();
		
		map.put("Product", "zoanswtpnswtp-czshop");
		map.put("UserLimit", "999999");
		map.put("MacAddress",SystemInfo.getMacAddress());
		map.put("Name", "TrialUser");
		map.put("TimeEnd", "92489617331483");
		
		return map;
	}

	/**
	 * �õ�URL���ļ���չ��
	 */
	public static String getURLExtName(String url) {
		if (isEmpty(url)) {
			return null;
		}
		int index1 = url.indexOf('?');
		if (index1 == -1) {
			index1 = url.length();
		}
		int index2 = url.lastIndexOf('.', index1);
		if (index2 == -1) {
			return null;
		}
		int index3 = url.indexOf('/', 8);
		if (index3 == -1) {
			return null;
		}
		String ext = url.substring(index2 + 1, index1);
		if (ext.matches("[^\\/\\\\]*")) {
			return ext;
		}
		return null;
	}

	/**
	 * �õ�URL���ļ���
	 */
	public static String getURLFileName(String url) {
		if (isEmpty(url)) {
			return null;
		}
		int index1 = url.indexOf('?');
		if (index1 == -1) {
			index1 = url.length();
		}
		int index2 = url.lastIndexOf('/', index1);
		if (index2 == -1 || index2 < 8) {
			return null;
		}
		String ext = url.substring(index2 + 1, index1);
		return ext;
	}

	/**
	 * ��һ��GBK������ַ���ת��UTF-8����Ķ��������飬ת����û��BOMλ
	 */
	public static byte[] GBKToUTF8(String chinese) {
		return GBKToUTF8(chinese, false);
	}

	/**
	 * ��һ��GBK������ַ���ת��UTF-8����Ķ��������飬�������bomFlagΪtrue����ת������BOMλ
	 */
	public static byte[] GBKToUTF8(String chinese, boolean bomFlag) {
		return CharsetConvert.GBKToUTF8(chinese, bomFlag);
	}

	/**
	 * ��UTF-8������ַ���ת��GBK������ַ���
	 */
	public static byte[] UTF8ToGBK(String chinese) {
		return CharsetConvert.UTF8ToGBK(chinese);
	}

	/**
	 * ȥ��XML�ַ����еķǷ��ַ�, ��XML��0x00-0x20 ��������һ��������
	 */
	public static String clearForXML(String str) {
		char[] cs = str.toCharArray();
		char[] ncs = new char[cs.length];
		int j = 0;
		for (int i = 0; i < cs.length; i++) {
			if (cs[i] > 0xFFFD) {
				continue;
			} else if (cs[i] < 0x20 && cs[i] != '\t' & cs[i] != '\n' & cs[i] != '\r') {
				continue;
			}
			ncs[j++] = cs[i];
		}
		ncs = ArrayUtils.subarray(ncs, 0, j);
		return new String(ncs);
	}

	public static Mapx splitToMapxNew(String str, String entrySpliter, String keySpliter) {
		Mapx map = new Mapx();
		String[] arr = StringUtil.splitEx(str, entrySpliter);
		for (int i = 0; i < arr.length; i++) {
			String[] arr2 = StringUtil.splitEx(arr[i], keySpliter);
			String key = arr2[0];
			if (StringUtil.isEmpty(key)) {
				continue;
			}
			key = key.trim();
			String value = null;
			if (arr2.length > 1) {
				value = arr2[1];
			}
			map.put(key, value);
		}
		return map;
	}
}
