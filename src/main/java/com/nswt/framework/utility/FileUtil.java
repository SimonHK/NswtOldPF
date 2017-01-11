package com.nswt.framework.utility;

import java.io.*;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Date;
import java.util.regex.Pattern;

import org.apache.commons.lang.ArrayUtils;

import com.nswt.framework.Constant;

/**
 * �ļ�����������
 * 
 * ����: NSWT<br>
 * ���ڣ�2016-7-12<br>
 * �ʼ���nswt@nswt.com.cn<br>
 */
public class FileUtil {
	/**
	 * ���ļ�·�����򻯣�ȥ�����ж����/��\��ȥ����������ļ���Ϣй©��../
	 */
	public static String normalizePath(String path) {
		path = path.replace('\\', '/');
		path = StringUtil.replaceEx(path, "../", "/");
		path = StringUtil.replaceEx(path, "./", "/");
		if (path.endsWith("..")) {
			path = path.substring(0, path.length() - 2);
		}
		path = path.replaceAll("/+", "/");
		return path;
	}

	public static File normalizeFile(File f) {
		String path = f.getAbsolutePath();
		path = normalizePath(path);
		return new File(path);
	}

	/**
	 * ��ȫ�ֱ��뽫ָ������д��ָ���ļ�
	 */
	public static boolean writeText(String fileName, String content) {
		fileName = normalizePath(fileName);
		return writeText(fileName, content, Constant.GlobalCharset);
	}

	/**
	 * ����ԭ�ı�������б���д��
	 * */
	public static boolean writeTextAuto(String fileName, String content) {
		fileName = normalizePath(fileName);
		try {
			return writeText(fileName,content,codeString(fileName));
		} catch (Exception e) {
			LogUtil.getLogger().warn("��̬�ж��ļ�����д���ļ���������ȫ�ֱ������д�룺" + e);
			return writeText(fileName, content, Constant.GlobalCharset);
		}

	}

	/**
	 * ��ָ�����뽫ָ������д��ָ���ļ�
	 */
	public static boolean writeText(String fileName, String content, String encoding) {
		fileName = normalizePath(fileName);
		return writeText(fileName, content, encoding, false);
	}

	/**
	 * ��ָ�����뽫ָ������д��ָ���ļ����������ΪUTF-8��bomFlagΪtrue,�����ļ�ͷ������3�ֽڵ�BOM
	 */
	public static boolean writeText(String fileName, String content, String encoding, boolean bomFlag) {
		fileName = normalizePath(fileName);
		try {
			byte[] bs = content.getBytes(encoding);
			if (encoding.equalsIgnoreCase("UTF-8") && bomFlag) {
				bs = ArrayUtils.addAll(StringUtil.BOM, bs);
			}
			writeByte(fileName, bs);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * �Զ����Ʒ�ʽ��ȡ�ļ�
	 */
	public static byte[] readByte(String fileName) {
		fileName = normalizePath(fileName);
		try {
			FileInputStream fis = new FileInputStream(fileName);
			byte[] r = new byte[fis.available()];
			fis.read(r);
			fis.close();
			return r;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * �Զ����Ʒ�ʽ��ȡ�ļ�
	 */
	public static byte[] readByte(File f) {
		f = normalizeFile(f);
		try {

			FileInputStream fis = new FileInputStream(f);
			byte[] r = readByte(fis);
			fis.close();
			return r;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ȡָ��������ת��Ϊ����������
	 */
	public static byte[] readByte(InputStream is) {
		try {
			byte[] r = new byte[is.available()];
			is.read(r);
			return r;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ������������д��ָ���ļ�
	 */
	public static boolean writeByte(String fileName, byte[] b) {
		fileName = normalizePath(fileName);
		try {
			BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(fileName));
			fos.write(b);
			fos.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * ������������д��ָ���ļ�
	 */
	public static boolean writeByte(File f, byte[] b) {
		f = normalizeFile(f);
		try {
			BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(f));
			fos.write(b);
			fos.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * ��ȫ�ֱ����ȡָ���ļ��е��ı�
	 */
	public static String readText(File f) {
		f = normalizeFile(f);
		return readText(f, Constant.GlobalCharset);
	}

	/**
	 * ��ָ�������ȡָ���ļ��е��ı�
	 */
	public static String readText(File f, String encoding) {
		f = normalizeFile(f);
		try {
			InputStream is = new FileInputStream(f);
			String str = readText(is, encoding);
			is.close();
			return str;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ָ�������ȡ���е��ı�
	 */
	public static String readText(InputStream is, String encoding) {
		try {
			byte[] bs = readByte(is);
			if (encoding.equalsIgnoreCase("utf-8")) {// �����UTF8��Ҫ�ж���û��BOM
				if (StringUtil.hexEncode(ArrayUtils.subarray(bs, 0, 3)).equals("efbbbf")) {// BOM��־
					bs = ArrayUtils.subarray(bs, 3, bs.length);
				}
			}
			return new String(bs, encoding);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ȫ�ֱ����ȡָ���ļ��е��ı�
	 */
	public static String readText(String fileName) {
		fileName = normalizePath(fileName);
		return readText(fileName, Constant.GlobalCharset);
	}

	/**
	 * ��̬��ȡ�ļ�����
	 * */
	public static String readTextAuto(String fileName){
		fileName = normalizePath(fileName);
		try {
			return readText(fileName,codeString(fileName));
		} catch (Exception e) {
			LogUtil.getLogger().warn("��̬��ȡ�ļ��������󽫲�ȡȫ�ֱ����ȡ��" + e);
			return readText(fileName, Constant.GlobalCharset);
		}
	}

	/**
	 * ��ָ�������ȡָ���ļ��е��ı�
	 */
	public static String readText(String fileName, String encoding) {
		fileName = normalizePath(fileName);
		try {
			InputStream is = new FileInputStream(fileName);
			String str = readText(is, encoding);
			is.close();
			return str;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ȫ�ֱ����ȡָ��URL�е��ı�
	 */
	public static String readURLText(String urlPath) {
		return readURLText(urlPath, Constant.GlobalCharset);
	}

	/**
	 * ��ָ�������ȡָ��URL�е��ı�
	 */
	public static String readURLText(String urlPath, String encoding) {
		try {
			URL url = new URL(urlPath);
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), encoding));
			String line;
			StringBuffer sb = new StringBuffer();
			while ((line = in.readLine()) != null) {
				sb.append(line + "\n");
			}
			in.close();
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ɾ���ļ�������·�����ļ������ļ��У���ɾ����<br>
	 * ɾ���ļ���ʱ���Զ�ɾ�����ļ��С�
	 */
	public static boolean delete(String path) {
		path = normalizePath(path);
		File file = new File(path);
		return delete(file);
	}

	/**
	 * ɾ���ļ�������·�����ļ������ļ��У���ɾ����<br>
	 * ɾ���ļ���ʱ���Զ�ɾ�����ļ��С�
	 */
	public static boolean delete(File f) {
		f = normalizeFile(f);
		if (!f.exists()) {
			LogUtil.getLogger().warn("�ļ����ļ��в����ڣ�" + f);
			return false;
		}
		if (f.isFile()) {
			return f.delete();
		} else {
			return FileUtil.deleteDir(f);
		}
	}

	/**
	 * ɾ���ļ��м������ļ���
	 */
	private static boolean deleteDir(File dir) {
		dir = normalizeFile(dir);
		try {
			return deleteFromDir(dir) && dir.delete(); // ��ɾ������������������ɾ�����ļ���
		} catch (Exception e) {
			LogUtil.getLogger().warn("ɾ���ļ��в�������");
			// e.printStackTrace();
			return false;
		}
	}

	/**
	 * �����ļ���
	 */
	public static boolean mkdir(String path) {
		path = normalizePath(path);
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return true;
	}

	/**
	 * ͨ�����ʽɾ��ָ��Ŀ¼�µ��ļ����ļ��С�<br>
	 * �ļ���֧��ʹ��������ʽ���ļ�·����֧��������ʽ��
	 */
	public static boolean deleteEx(String fileName) {
		fileName = normalizePath(fileName);
		int index1 = fileName.lastIndexOf("\\");
		int index2 = fileName.lastIndexOf("/");
		index1 = index1 > index2 ? index1 : index2;
		String path = fileName.substring(0, index1);
		String name = fileName.substring(index1 + 1);
		File f = new File(path);
		if (f.exists() && f.isDirectory()) {
			File[] files = f.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (Pattern.matches(name, files[i].getName())) {
					files[i].delete();
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * ɾ���ļ�������������ļ�,����ɾ���Լ�����
	 */
	public static boolean deleteFromDir(String dirPath) {
		dirPath = normalizePath(dirPath);
		File file = new File(dirPath);
		return deleteFromDir(file);
	}

	/**
	 * ɾ���ļ�������������ļ������ļ���,����ɾ���Լ�����
	 * 
	 * @param dir
	 * @return
	 */
	public static boolean deleteFromDir(File dir) {
		dir = normalizeFile(dir);
		if (!dir.exists()) {
			LogUtil.getLogger().warn("�ļ��в����ڣ�" + dir);
			return false;
		}
		if (!dir.isDirectory()) {
			LogUtil.getLogger().warn(dir + "�����ļ���");
			return false;
		}
		File[] tempList = dir.listFiles();
		for (int i = 0; i < tempList.length; i++) {
			if (!delete(tempList[i])) {
				return false;
			}
		}
		return true;
	}

	/**
	 * ��ָ��λ�ø����ļ�����һ���ļ��У�����ʱ������filter�����Ĳ�����
	 */
	public static boolean copy(String oldPath, String newPath, FileFilter filter) {
		oldPath = normalizePath(oldPath);
		newPath = normalizePath(newPath);
		File oldFile = new File(oldPath);
		File[] oldFiles = oldFile.listFiles(filter);
		boolean flag = true;
		if (oldFiles != null) {
			for (int i = 0; i < oldFiles.length; i++) {
				if (!copy(oldFiles[i], newPath + "/" + oldFiles[i].getName())) {
					flag = false;
				}
			}
		}
		return flag;
	}

	/**
	 * ��ָ��λ�ø����ļ�����һ���ļ���
	 */
	public static boolean copy(String oldPath, String newPath) {
		oldPath = normalizePath(oldPath);
		newPath = normalizePath(newPath);
		File oldFile = new File(oldPath);
		return copy(oldFile, newPath);
	}

	public static boolean copy(File oldFile, String newPath) {
		oldFile = normalizeFile(oldFile);
		newPath = normalizePath(newPath);
		if (!oldFile.exists()) {
			LogUtil.getLogger().warn("�ļ������ļ��в����ڣ�" + oldFile);
			return false;
		}
		if (oldFile.isFile()) {
			return copyFile(oldFile, newPath);
		} else {
			return copyDir(oldFile, newPath);
		}
	}

	/**
	 * ���Ƶ����ļ�
	 */
	private static boolean copyFile(File oldFile, String newPath) {
		oldFile = normalizeFile(oldFile);
		newPath = normalizePath(newPath);
		if (!oldFile.exists()) { // �ļ�����ʱ
			LogUtil.getLogger().warn("�ļ������ڣ�" + oldFile);
			return false;
		}
		if (!oldFile.isFile()) { // �ļ�����ʱ
			LogUtil.getLogger().warn(oldFile + "�����ļ�");
			return false;
		}
		if(oldFile.getName().equalsIgnoreCase("Thumbs.db")){
			LogUtil.getLogger().warn(oldFile + "���Դ��ļ�");
			return true;
		}
		
		try {
			int byteread = 0;
			InputStream inStream = new FileInputStream(oldFile); // ����ԭ�ļ�
			File newFile = new File(newPath);
			//������ļ���һ��Ŀ¼���򴴽��µ�File����
			if(newFile.isDirectory()){
				newFile = new File(newPath,oldFile.getName());
			}
			FileOutputStream fs = new FileOutputStream(newFile);
			byte[] buffer = new byte[1024];
			while ((byteread = inStream.read(buffer)) != -1) {
				fs.write(buffer, 0, byteread);
			}
			fs.close();
			inStream.close();
		} catch (Exception e) {
			LogUtil.getLogger().warn("���Ƶ����ļ�" + oldFile.getPath() + "������������ԭ��:" + e.getMessage());
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * ���������ļ�������
	 */
	private static boolean copyDir(File oldDir, String newPath) {
		oldDir = normalizeFile(oldDir);
		newPath = normalizePath(newPath);
		if (!oldDir.exists()) { // �ļ�����ʱ
			LogUtil.info("�ļ��в����ڣ�" + oldDir);
			return false;
		}
		if (!oldDir.isDirectory()) { // �ļ�����ʱ
			LogUtil.info(oldDir + "�����ļ���");
			return false;
		}
		try {
			(new File(newPath)).mkdirs(); // ����ļ��в����� �������ļ���
			File[] files = oldDir.listFiles();
			File temp = null;
			for (int i = 0; i < files.length; i++) {
				temp = files[i];
				if (temp.isFile()) {
					if (!FileUtil.copyFile(temp, newPath + "/" + temp.getName())) {
						return false;
					}
				} else if (temp.isDirectory()) {// ��������ļ���
					if (!FileUtil.copyDir(temp, newPath + "/" + temp.getName())) {
						return false;
					}
				}
			}
			return true;
		} catch (Exception e) {
			LogUtil.getLogger().info("���������ļ������ݲ�����������ԭ��:" + e.getMessage());
			// e.printStackTrace();
			return false;
		}
	}

	/**
	 * �ƶ��ļ���ָ��Ŀ¼
	 */
	public static boolean move(String oldPath, String newPath) {
		oldPath = normalizePath(oldPath);
		newPath = normalizePath(newPath);
		return copy(oldPath, newPath) && delete(oldPath);
	}

	/**
	 * �ƶ��ļ���ָ��Ŀ¼
	 */
	public static boolean move(File oldFile, String newPath) {
		oldFile = normalizeFile(oldFile);
		newPath = normalizePath(newPath);
		return copy(oldFile, newPath) && delete(oldFile);
	}

	/**
	 * �������л��������л���д��ָ���ļ�
	 */
	public static void serialize(Serializable obj, String fileName) {
		fileName = normalizePath(fileName);
		try {
			FileOutputStream f = new FileOutputStream(fileName);
			ObjectOutputStream s = new ObjectOutputStream(f);
			s.writeObject(obj);
			s.flush();
			s.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * �������л��������л������ض���������
	 */
	public static byte[] serialize(Serializable obj) {
		try {
			ByteArrayOutputStream b = new ByteArrayOutputStream();
			ObjectOutputStream s = new ObjectOutputStream(b);
			s.writeObject(obj);
			s.flush();
			s.close();
			return b.toByteArray();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * ��ָ���ļ��з����л�����
	 */
	public static Object unserialize(String fileName) {
		fileName = normalizePath(fileName);
		try {
			FileInputStream in = new FileInputStream(fileName);
			ObjectInputStream s = new ObjectInputStream(in);
			Object o = s.readObject();
			s.close();
			return o;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * �Ӷ����������з����л�����
	 */
	public static Object unserialize(byte[] bs) {
		try {
			ByteArrayInputStream in = new ByteArrayInputStream(bs);
			ObjectInputStream s = new ObjectInputStream(in);
			Object o = s.readObject();
			s.close();
			return o;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * ��һ��Mapx���������л�,��ֵֻ��Ϊ�ַ���<br>
	 */
	public static byte[] mapToBytes(Mapx map) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			Object[] ks = map.keyArray();
			Object[] vs = map.valueArray();
			for (int i = 0; i < map.size(); i++) {
				String k = String.valueOf(ks[i]);
				Object v = vs[i];
				if (v == null) {
					bos.write(new byte[] { 0 });
				} else if (v instanceof String) {
					bos.write(new byte[] { 1 });
				} else if (v instanceof Long) {
					bos.write(new byte[] { 2 });
				} else if (v instanceof Integer) {
					bos.write(new byte[] { 3 });
				} else if (v instanceof Boolean) {
					bos.write(new byte[] { 4 });
				} else if (v instanceof Date) {
					bos.write(new byte[] { 5 });
				} else if (v instanceof Mapx) {
					bos.write(new byte[] { 6 });
				} else if (v instanceof Serializable) {
					bos.write(new byte[] { 7 });
				} else {
					throw new RuntimeException("δ֪����������:" + v.getClass().getName());
				}
				byte[] bs = k.getBytes();
				bos.write(NumberUtil.toBytes(bs.length));
				bos.write(bs);
				if (v == null) {
					continue;
				} else if (v instanceof String) {
					bs = v.toString().getBytes();
					bos.write(NumberUtil.toBytes(bs.length));
					bos.write(bs);
				} else if (v instanceof Long) {
					bos.write(NumberUtil.toBytes(((Long) v).longValue()));
				} else if (v instanceof Integer) {
					bos.write(NumberUtil.toBytes(((Integer) v).intValue()));
				} else if (v instanceof Boolean) {
					bos.write(((Boolean) v).booleanValue() ? 1 : 0);
				} else if (v instanceof Date) {
					bos.write(NumberUtil.toBytes(((Date) v).getTime()));
				} else if (v instanceof Mapx) {
					byte[] arr = mapToBytes((Mapx) v);
					bos.write(NumberUtil.toBytes(arr.length));
					bos.write(arr);
				} else if (v instanceof Serializable) {
					byte[] arr = serialize((Serializable) v);
					bos.write(NumberUtil.toBytes(arr.length));
					bos.write(arr);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bos.toByteArray();
	}

	/**
	 * ��һ�����������鷴���л�ΪMapx
	 */
	public static Mapx bytesToMap(byte[] arr) {
		ByteArrayInputStream bis = new ByteArrayInputStream(arr);
		int b = -1;
		Mapx map = new Mapx();
		byte[] kbs = new byte[4];
		byte[] vbs = null;
		try {
			while ((b = bis.read()) != -1) {
				bis.read(kbs);
				int len = NumberUtil.toInt(kbs);
				vbs = new byte[len];
				bis.read(vbs);
				String k = new String(vbs);
				Object v = null;
				if (b == 1) {
					bis.read(kbs);
					len = NumberUtil.toInt(kbs);
					vbs = new byte[len];
					bis.read(vbs);
					v = new String(vbs);
				} else if (b == 2) {
					vbs = new byte[8];
					bis.read(vbs);
					v = new Long(NumberUtil.toLong(vbs));
				} else if (b == 3) {
					vbs = new byte[4];
					bis.read(vbs);
					v = new Integer(NumberUtil.toInt(vbs));
				} else if (b == 4) {
					int i = bis.read();
					v = new Boolean(i == 1 ? true : false);
				} else if (b == 5) {
					vbs = new byte[8];
					bis.read(vbs);
					v = new Date(NumberUtil.toLong(vbs));
				} else if (b == 6) {
					bis.read(kbs);
					len = NumberUtil.toInt(kbs);
					vbs = new byte[len];
					bis.read(vbs);
					v = bytesToMap(vbs);
				} else if (b == 7) {
					bis.read(kbs);
					len = NumberUtil.toInt(kbs);
					vbs = new byte[len];
					bis.read(vbs);
					v = unserialize(vbs);
				}
				map.put(k, v);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * �ж��ļ��ı����ʽ
	 * @param fileName :file
	 * @return �ļ������ʽ
	 * @throws Exception
	 */
	public static String codeString(String fileName) throws Exception{
		String code = null;
		if(isUTF8(toByteArray(fileName))){
			code = "UTF-8";
		}else{
			code = "GBK";
		}
		return code;
	}

	/**
	 * Mapped File way MappedByteBuffer �����ڴ�����ļ�ʱ����������
	 *
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public static byte[] toByteArray(String filename) throws IOException {

		FileChannel fc = null;
		try {
			fc = new RandomAccessFile(filename, "r").getChannel();
			MappedByteBuffer byteBuffer = fc.map(FileChannel.MapMode.READ_ONLY, 0,
					fc.size()).load();
			//System.out.println(byteBuffer.isLoaded());
			byte[] result = new byte[(int) fc.size()];
			if (byteBuffer.remaining() > 0) {
				// System.out.println("remain");
				byteBuffer.get(result, 0, byteBuffer.remaining());
			}
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				fc.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	//UTF-8����淶 ������ж��ı���UTF-8�����
//UTF-8�ı������ܼ򵥣�ֻ�ж�����
//1�����ڵ��ֽڵķ��ţ��ֽڵĵ�һλ��Ϊ0������7λΪ������ŵ�unicode�롣��˶���Ӣ����ĸ��UTF-8�����ASCII������ͬ�ġ�
//2������n�ֽڵķ��ţ�n>1������һ���ֽڵ�ǰnλ����Ϊ1����n+1λ��Ϊ0�������ֽڵ�ǰ��λһ����Ϊ10��ʣ�µ�û���ἰ�Ķ�����λ��ȫ��Ϊ������ŵ�unicode�롣
//��������˵�� �������һ��java�����ж�UTF-8��ʽ
	/**
	 * UTF-8�����ʽ�ж�
	 *
	 * @param rawtext
	 *��Ҫ����������
	 * @return �Ƿ�ΪUTF-8�����ʽ
	 */
	public static boolean isUTF8(byte[] rawtext) {
		int score = 0;
		int i, rawtextlen = 0;
		int goodbytes = 0, asciibytes = 0;
		rawtextlen = rawtext.length;
		for (i = 0; i < rawtextlen; i++) {
			if ((rawtext[i] & (byte) 0x7F) == rawtext[i]) {
				// ���λ��0��ASCII�ַ�
				asciibytes++;
				// Ignore ASCII, can throw off count
			} else if (-64 <= rawtext[i] && rawtext[i] <= -33 && i + 1 < rawtextlen && -128 <= rawtext[i + 1] && rawtext[i + 1] <= -65)
			{
				goodbytes += 2;
				i++;
			} else if (-32 <= rawtext[i] && rawtext[i] <= -17 && i + 2 < rawtextlen && -128 <= rawtext[i + 1] && rawtext[i + 1] <= -65 && -128 <= rawtext[i + 2] && rawtext[i + 2] <= -65)
			{
				goodbytes += 3;
				i += 2;
			}
		}
		if (asciibytes == rawtextlen) {
			return false;
		}
		score = 100 * goodbytes / (rawtextlen - asciibytes);
		if (score > 98) {
			return true;
		} else if (score > 95 && goodbytes > 30) {
			return true;
		} else {
			return false;
		}
	}

	public static void main(String[] args) {
		File f = new File("F:/Workspace_Product\\nswtp\\UI\\Framework\\Controls/../../..");
		System.out.println(f.list().length);
		System.out.println(f.getAbsolutePath());
	}
}
