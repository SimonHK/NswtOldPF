package com.nswt.framework.utility;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import org.apache.commons.lang.ArrayUtils;

/**
 * ���ļ������Mapx,��Ҫ���ڴ洢������URL��������<br>
 * ԭ��:<br>
 * һ��HashMap������Ϊ10������Ϊ100000ʱͨ��Key��ȡһ��Ԫ�ص����ܻ����൱��<br>
 * ��˿�����HashMap�Ļ�����ʵ��һ�������ļ�ϵͳ��FileMap��<br>
 * ʵ�ֲ���:<br>
 * ��һ��������ֱ���ճ�HashMap�е�ɢ���㷨: Java����
 * 
 * <pre>
 * public static int hash(Object x, int length) {
 * 	int h = x.hashCode();
 * 	h += &tilde;(h &lt;&lt; 9);
 * 	h &circ;= (h &gt;&gt;&gt; 14);
 * 	h += (h &lt;&lt; 4);
 * 	h &circ;= (h &gt;&gt;&gt; 10);
 * 	return h &amp; (length - 1);
 * }
 * </pre>
 * 
 * ����length����10000����ô����һ��URLͨ��hash()�㷨���ص�ֵ������ƽ���ֲ�����1��10000��<br>
 * ��������һ��URL�ľ������ݵ�����ʲô��URL֮��Ҫ�в��죬���ܶ���ͬ��󲿷���ͬ��<br>
 * ��������ʵ����Ϊ�ؼ��ĵط�����ʵ��Ӧ����length��ֵ�������������������仯�ģ�<br>
 * ÿ�����ݺ���Ҫ������URL���¼���ɢ��ֵ�����Բο�HashMap�е�ʵ�֡�<br>
 * 
 * �ڶ���������ļ�����<br>
 * ʵ�ִ�����ݵķ���������������Ҫ���һ��URL���������ݣ�<br>
 * ��ô������value.dat�����д�����ݳ��Ⱥ����ݱ������value.dat�����ڣ�����Ҫ�Ƚ���һ������<br>
 * ���ҷ���һ�����ݳ�����ʼ�ֽ���value.dat�е���ʼ��ַ��
 * 
 * ����������ż�ֵ<br>
 * ʵ�ִ�ż�ֵ���㷨���õ����ݵ���ʼ��ַ������[��ʼ��ַ+URL]�ĳ��ȣ�<br>
 * ���ó��Ⱥ�[��ʼ��ַ+URL]д���ֵ�����ļ�key.dat��������key.dat�����ڣ�����Ҫ�Ƚ���һ������<br>
 * ���ҷ��ظó�����ʼ�ֽ���key.dat�ĵ�ַ��
 * 
 * ���Ĳ������ɢ��ֵ���ֵ��ַ�Ķ�Ӧ��ϵ<br>
 * ʵ�ִ��ɢ��ֵ���ֵ��ַ��Ӧ��ϵ���㷨���õ���ֵ����ʼ��ַ�󣨵�ַ����Ϊ4�ֽ�,��Ϊlong���͵ĳ��ȣ���<br>
 * ͨ��hash()����URL��ɢ��ֵ������ɢ��ֵΪ3000�Ļ���<br>
 * �򽫸õ�ַд���ַ�����ļ�address.idx�ĵ�12000-12004���ֽڡ�<br>
 * 
 * ���岽��ȡURL����<br>
 * ʵ��ȡURL���ݵ��㷨������URL�Ѿ�����FileMap,����Ҫͨ��URLȡ����ʱ���������£�<br>
 * ͨ��hash()����URL��ɢ��ֵ��ͨ��ɢ��ֵ��address.idx��ȡ��ֵ��key.dat�еĵ�ַ��<br>
 * ͨ����ֵ��������value.dat�еĵ�ַ������ȡ��URL��Ӧ�������ˡ�
 * 
 * �����������ɢ�г�ͻ<br>
 * hash()�ܽ�һ��URL����ƽ���طֲ���һ���ַ�ϣ������ɱ���ػ����ɢ�г�ͻ�������<br>
 * �������ͬ��URL���ͬһ��ɢ��ֵ���������ʱ���һ�������URL��ֱ��д��address.idx��ɢ��ֵ��Ӧ�ĵ�ַ��<br>
 * ������URL����ʱ��Ҫ������ļ�ֵ��ַд���һ��URL��key.dat�ļ�¼��ĩβ��<br>
 * �Ա��ȡʱ�ܹ�ͨ����һ��URL�ҵ�����ɢ��ֵ��ͬ��URL��������ɢ�г�ͻ�����⡣
 * 
 * ���������ǱȽϼ��Ĺ��̣�ʵ���л���Ҫ���value.dat��������⣨��ʱ����ϵͳ���ļ���С�����ƣ�<br>
 * �����γ�value0.data,value1.data,value2.data��һ��value�ļ����Ӷ�ʹ��Ѱַ��һ�����ӣ���<br>
 * �������ɢ�е����⣬���ѹ����ȡ�����⡣
 * 
 * ��Ȼ��ȡһ��URLʹ����3���ļ�������address.idx��key.dat���������С��ʹ��ʱ�ֶ���ֱ�Ӷ�λ��
 * ������Ƶ����ʹ�ñ����̵�Cache�Լ�����ϵͳ��Cache���棬ʱ�����������Ƿǳ�С�ġ�
 * 
 * @Author NSWT
 * @Date 2008-2-27
 * @Mail nswt@nswt.com.cn
 */
public class FileCachedMapx {
	private Mapx map;// ����Key,ͨ��keyȡֵ

	private boolean compressible;

	private int total;// ���п���λ����

	private int size;// ���м���λ����

	private int modCount;// ���м��޸�λ����

	private short maxFileIndex;

	private int maxItemInMemory = 1000;

	private String cacheDirectory;

	private BufferedRandomAccessFile[] addressFiles = null;

	private BufferedRandomAccessFile[] keyFiles = null;

	private BufferedRandomAccessFile[] valueFiles = null;

	private static final int AddressCountInOneFile = 268435456;// 1024*1024*256,ÿ���ļ��е��������key����

	private static final int MaxFileSize = 2097152000;// 2097152000;

	private static final int DefaultCountInMemory = 100;

	private int addressFileCount;

	/**
	 * ����һ��ʵ�����ļ���������cacheDir��
	 * 
	 * @param cacheDir
	 */
	public FileCachedMapx(String cacheDir) {
		this(cacheDir, 65536, DefaultCountInMemory);
	}

	/**
	 * ����һ��ʵ�����ļ����澭zipѹ��������cacheDir��
	 * 
	 * @param cacheDir
	 * @param compressiable
	 */
	public FileCachedMapx(String cacheDir, boolean compressiable) {
		this(cacheDir, 65536, compressiable, DefaultCountInMemory);
	}

	/**
	 * ����һ��ʵ�����ļ���������cacheDir�У�Ĭ�ϴ���initEntrySize������ַ
	 * 
	 * @param cacheDir
	 * @param initEntrySize
	 */
	public FileCachedMapx(String cacheDir, int initEntrySize) {
		this(cacheDir, initEntrySize, true, DefaultCountInMemory);
	}

	/**
	 * ����һ��ʵ�����ļ����澭zipѹ��������cacheDir�У�����ʹ���ڴ滺��
	 * 
	 * @param cacheDir
	 * @param compressiable
	 * @param maxItemInMemory
	 */
	public FileCachedMapx(String cacheDir, boolean compressiable, int maxItemInMemory) {
		this(cacheDir, 65536, compressiable, maxItemInMemory);
	}

	/**
	 * ����һ��ʵ�����ļ���������cacheDir�У�Ĭ�ϴ���initEntrySize������ַ������ʹ���ڴ滺��
	 * 
	 * @param cacheDir
	 * @param initEntrySize
	 * @param maxItemInMemory
	 */
	public FileCachedMapx(String cacheDir, int initEntrySize, int maxItemInMemory) {
		this(cacheDir, initEntrySize, true, maxItemInMemory);
	}

	/**
	 * ����һ��ʵ�����ļ����澭zipѹ��������cacheDir�У�Ĭ�ϴ���initEntrySize������ַ������ʹ���ڴ滺��
	 * 
	 * @param cacheDir
	 * @param initEntrySize
	 * @param compressiable
	 * @param maxItemInMemory
	 */
	public FileCachedMapx(String cacheDir, int initEntrySize, boolean compressiable, int maxItemInMemory) {
		this.cacheDirectory = cacheDir;
		this.total = new Double(Math.pow(2, Math.ceil(Math.log(initEntrySize) / Math.log(2)))).intValue();
		this.compressible = compressiable;
		this.maxItemInMemory = maxItemInMemory;
		map = new Mapx(maxItemInMemory);
		loadInfo();
	}

	/**
	 * �����ļ�����ַ�ļ��������ļ������û���򴴽�֮
	 */
	private void initFiles() {
		try {
			// ������������
			addressFileCount = new Double(Math.ceil(total * 1.0 / AddressCountInOneFile)).intValue();
			addressFiles = new BufferedRandomAccessFile[addressFileCount];
			int prefix = new Double(Math.log(total / 16) / Math.log(2)).intValue();
			for (int i = 0; i < addressFileCount; i++) {
				addressFiles[i] = new BufferedRandomAccessFile(cacheDirectory + prefix + "key" + i + ".idx", "rw");
				if (i == addressFileCount - 1) {
					addressFiles[i].setLength((total - (addressFileCount - 1) * AddressCountInOneFile) * 9);
				}
			}
			keyFiles = new BufferedRandomAccessFile[maxFileIndex + 1];
			valueFiles = new BufferedRandomAccessFile[maxFileIndex + 1];
			for (int i = 0; i <= maxFileIndex; i++) {
				keyFiles[i] = new BufferedRandomAccessFile(cacheDirectory + "key" + i + ".dat", "rw");
				valueFiles[i] = new BufferedRandomAccessFile(cacheDirectory + "value" + i + ".dat", "rw");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ����ֵ��д���ļ�
	 * 
	 * @param k
	 * @param v
	 */
	private void writeData(String k, Object v) {
		int index = hash(k);
		int c = index % AddressCountInOneFile;// ���ļ��е�λ��
		index = index / AddressCountInOneFile;// �ڵڼ����ļ���
		if (addressFiles == null) {
			initFiles();
		}
		try {
			BufferedRandomAccessFile f = addressFiles[index];
			f.seek(c * 9);
			Key key = getKey(f);
			if (key == null) {// δ��ռ�ù�
				// дkey.idx
				f.seek(c * 9);
				writeFile(f, k, v);
			} else {
				if (key.KeyString.equals(k)) {
					updateByKey(key, v);
					return;
				}
				// �������ͻ,��ͻ�ļ�����key.idx�У�����key.dat��
				while (true) {
					f = keyFiles[key.KeyFileIndex];
					int pos = key.keyAddress + key.KeyLength - 9;
					f.seek(pos);
					key = getKey(f);
					if (key == null) {// �����ĳ�ͻ��
						// дǰһ�����ĺ�9λ
						f.seek(pos);
						writeFile(f, k, v);
						break;
					} else {
						if (key.KeyString.equals(k)) {
							updateByKey(key, v);
							break;
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ����ֵ��д��ָ���ļ�
	 * 
	 * @param f
	 * @param k
	 * @param v
	 * @throws IOException
	 */
	private void writeFile(BufferedRandomAccessFile f, String k, Object v) throws IOException {
		f.writeByte(1);// ��������ռ��
		f.writeShort(maxFileIndex);

		byte[] bk = k.getBytes();
		byte[] bv = toBytes(v);// ��������

		f.writeShort(22 + bk.length);
		BufferedRandomAccessFile kf = keyFiles[maxFileIndex];
		BufferedRandomAccessFile vf = valueFiles[maxFileIndex];
		int kpos = (int) kf.length();
		// �ж��Ƿ���Ҫ�½�key.dat�ļ�
		long kNewSize = kpos + 22 + bk.length;
		if (kNewSize > MaxFileSize) {// 2^31-1
			maxFileIndex++;
			BufferedRandomAccessFile fk = new BufferedRandomAccessFile(cacheDirectory + "key" + maxFileIndex + ".dat",
					"rw");
			BufferedRandomAccessFile fv = new BufferedRandomAccessFile(
					cacheDirectory + "value" + maxFileIndex + ".dat", "rw");
			keyFiles = (BufferedRandomAccessFile[]) ArrayUtils.add(keyFiles, fk);
			valueFiles = (BufferedRandomAccessFile[]) ArrayUtils.add(valueFiles, fv);
			kf = keyFiles[maxFileIndex];
			kNewSize = 22 + bk.length;
			kpos = 0;
		}
		// �ж��Ƿ���Ҫ�½�value.dat�ļ�
		int vpos = (int) vf.length();
		int vNewSize = vpos + 9 + bk.length + bv.length;
		if (vNewSize > MaxFileSize) {// 2^31-2
			maxFileIndex++;
			BufferedRandomAccessFile fk = new BufferedRandomAccessFile(cacheDirectory + "key" + maxFileIndex + ".dat",
					"rw");
			BufferedRandomAccessFile fv = new BufferedRandomAccessFile(
					cacheDirectory + "value" + maxFileIndex + ".dat", "rw");
			keyFiles = (BufferedRandomAccessFile[]) ArrayUtils.add(keyFiles, fk);
			valueFiles = (BufferedRandomAccessFile[]) ArrayUtils.add(valueFiles, fv);
			vf = valueFiles[maxFileIndex];
			vNewSize = 9 + bk.length + bv.length;
			vpos = 0;
		}
		f.writeInt(kpos);// ��ַ

		// дkey.dat
		kf.setLength(kNewSize);
		kf.seek(kpos);
		kf.writeByte(1);// ��ʶ�Ƿ�ɾ��
		kf.writeShort(maxFileIndex);// �ĸ������ļ�

		kf.writeInt(9 + bk.length + bv.length);
		kf.writeInt(vpos);// �������ļ��е�λ��
		kf.writeShort(bk.length);
		kf.write(bk);
		// 9���ֽڵĳ�ͻ������Ϊ��,��������

		// д��value.dat
		vf.setLength(vNewSize);
		vf.seek(vpos);
		vf.writeByte(1);// ��ʶ�Ƿ�ɾ��
		vf.writeInt(bk.length);// ������
		vf.writeInt(bv.length);// ���ݳ���
		vf.write(bk);
		vf.write(bv);
	}

	/**
	 * ���¼���ֵ
	 * 
	 * @param key
	 * @param v
	 * @throws IOException
	 */
	private void updateByKey(Key key, Object v) throws IOException {
		byte[] bv = toBytes(v);// ��������
		BufferedRandomAccessFile f = valueFiles[key.DataFileIndex];
		int pos = (int) f.length();
		int newDataLength = key.KeyLength - 13 + bv.length;
		if (newDataLength > key.DataLength) {// ��Ҫ���¿��ٿռ�
			if (key.DataFileIndex < maxFileIndex) {// ˵����д��
				key.DataFileIndex = maxFileIndex;
				f = valueFiles[maxFileIndex];
				pos = (int) f.length();
			}
			// �ж��Ƿ���Ҫ�½�value.dat�ļ�
			int newSize = pos + newDataLength;
			if (newSize > MaxFileSize) {// 2^31-2
				// ���ļ�������ݱ��Ϊɾ��
				f.seek(key.DataAddress);
				f.writeByte(0);
				maxFileIndex++;
				BufferedRandomAccessFile fk = new BufferedRandomAccessFile(cacheDirectory + "key" + maxFileIndex
						+ ".dat", "rw");
				BufferedRandomAccessFile fv = new BufferedRandomAccessFile(cacheDirectory + "value" + maxFileIndex
						+ ".dat", "rw");
				keyFiles = (BufferedRandomAccessFile[]) ArrayUtils.add(keyFiles, fk);
				valueFiles = (BufferedRandomAccessFile[]) ArrayUtils.add(valueFiles, fv);
				f = valueFiles[maxFileIndex];
				key.DataFileIndex = maxFileIndex;
				pos = 0;
				f.setLength(newDataLength);
			} else {
				pos = (int) f.length();
				f.setLength(newSize);
			}
			// ����key.dat
			f = keyFiles[key.KeyFileIndex];
			f.seek(key.keyAddress + 1);
			f.writeShort(maxFileIndex);// �ĸ������ļ�
			f.writeInt(newDataLength);
			f.writeInt(pos);// �������ļ��е�λ��
			modCount++;
		} else {// ����Ҫ���¿��ٿռ�
			f = keyFiles[key.KeyFileIndex];
			f.seek(key.keyAddress + 3);
			f.writeInt(newDataLength);// �������ݳ���
			pos = key.DataAddress;
		}

		// д����������
		f = valueFiles[key.DataFileIndex];
		f.seek(pos);
		f.writeByte(1);// ��ʶ�Ƿ�ɾ��
		byte[] bk = key.KeyString.getBytes();
		f.writeInt(bk.length);// ������
		f.writeInt(bv.length);// ���ݳ���
		f.write(bk);
		f.write(bv);
	}

	/**
	 * �Ӽ��ļ��ĵ�ǰλ�ö�ȡһ����
	 * 
	 * @param f
	 * @return
	 * @throws IOException
	 */
	private Key getKey(BufferedRandomAccessFile f) throws IOException {
		if (f.length() == 0 || f.readByte() == 0) {
			return null;// û�д˼�
		}
		Key key = new Key();
		key.KeyFileIndex = f.readShort();// �ڼ������ļ���
		key.KeyLength = f.readShort();// ������
		key.keyAddress = f.readInt();// ����ַ

		f = keyFiles[key.KeyFileIndex];
		f.seek(key.keyAddress);
		if (f.readByte() == 0) {// ��һ��byte��ʾ�Ƿ�ɾ��
			return null;
		}

		key.DataFileIndex = f.readShort();// �ڵڼ��������ļ���
		key.DataLength = f.readInt();// ���ݳ���
		key.DataAddress = f.readInt();// ���ݵ�ַ
		f.readShort();// ���ַ������ȣ��˴�����Ҫ�ٶ�
		byte[] bs = null;
		try {
			bs = new byte[key.KeyLength - 22];
		} catch (Exception e) {
			e.printStackTrace();
		}
		f.read(bs);
		key.KeyString = new String(bs);
		return key;
	}

	/**
	 * ��ָ��index�ļ��ļ���ȡָ��������Ϣ
	 * 
	 * @param index
	 * @param k
	 * @return
	 */
	private Key readKey(int index, String k) {
		int c = index % AddressCountInOneFile;// ���ļ��е�λ��
		index = index / AddressCountInOneFile;
		if (addressFiles == null) {
			initFiles();
		}
		try {
			BufferedRandomAccessFile f = addressFiles[index];
			f.seek(c * 9);
			Key key = getKey(f);
			if (key == null || key.KeyString.equals(k)) {
				return key;
			}
			// �������ͻ,��ͻ�ļ�����key.idx�У�����key.dat��
			while (true) {
				f = keyFiles[key.KeyFileIndex];
				int pos = key.keyAddress + key.KeyLength - 9;
				f.seek(pos);
				key = getKey(f);
				if (key == null || key.KeyString.equals(k)) {
					return key;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ָ������ȡ����
	 * 
	 * @param k
	 * @return
	 * @throws IOException
	 */
	private Object readData(String k) throws IOException {
		int i = hash(k);
		Key key = readKey(i, k);
		if (key == null) {
			return null;
		}
		BufferedRandomAccessFile f = valueFiles[key.DataFileIndex];
		f.seek(key.DataAddress + 9 + key.KeyLength - 22);
		byte[] bv = new byte[key.DataLength - 9 - key.KeyLength + 22];
		f.read(bv);
		return toObject(bv);
	}

	/**
	 * ת������������Ϊ����
	 * 
	 * @param bs
	 * @return
	 */
	private Object toObject(byte[] bs) {
		if (bs == null || bs.length == 0) {
			return null;
		}
		if (compressible) {
			try {
				bs = ZipUtil.unzip(bs);
			} catch (Exception e) {
				return null;
			}
		}
		if (bs.length == 0) {
			return null;
		}
		byte type = bs[0];
		bs = ArrayUtils.subarray(bs, 1, bs.length);
		if (type == 1) {
			return new Integer(NumberUtil.toInt(bs, 0));
		}
		if (type == 2) {
			return new Long(NumberUtil.toLong(bs));
		}
		if (type == 3) {
			return new String(bs);
		}
		if (type == 4) {
			return bs;
		}
		if (type == 5) {
			int[] arr = new int[bs.length / 4];
			for (int i = 0; i < arr.length; i++) {
				arr[i] = NumberUtil.toInt(bs, i * 4);
			}
			return arr;
		}
		if (type == 6) {
			long[] arr = new long[bs.length / 8];
			for (int i = 0; i < arr.length; i++) {
				arr[i] = NumberUtil.toInt(bs, i * 8);
			}
			return arr;
		}
		if (type == 7) {
			ByteBuffer bb = ByteBuffer.allocate(bs.length);
			bb.put(bs);
			bb.flip();
			ArrayList arr = new ArrayList();
			int index = 0;
			while (true) {
				int length = bb.getInt();
				byte[] t = new byte[length];
				bb.get(t);
				arr.add(new String(t));
				index += 4 + length;
				if (index == bs.length) {
					break;
				}
			}
			String[] r = new String[arr.size()];
			for (int i = 0; i < r.length; i++) {
				r[i] = (String) arr.get(i);
			}
			return r;
		}
		if (type == 8) {
			return FileUtil.unserialize(bs);
		}
		return null;
	}

	/**
	 * ������ת��Ϊ����������
	 * 
	 * @param v
	 * @return
	 */
	private byte[] toBytes(Object v) {
		byte type = 0;
		byte[] bs = null;
		if (v instanceof Integer) {
			type = 1;
			bs = NumberUtil.toBytes(((Integer) v).intValue());
		} else if (v instanceof Long) {
			type = 2;
			bs = NumberUtil.toBytes(((Long) v).longValue());
		} else if (v instanceof String) {
			type = 3;
			bs = ((String) v).getBytes();
		} else if (v instanceof byte[]) {
			type = 4;
			bs = (byte[]) v;
		} else if (v instanceof int[]) {
			type = 5;
			int[] arr = (int[]) v;
			bs = new byte[4 * arr.length];
			for (int i = 0; i < arr.length; i++) {
				NumberUtil.toBytes(arr[i], bs, i * 4);
			}
		} else if (v instanceof long[]) {
			type = 6;
			long[] arr = (long[]) v;
			bs = new byte[8 * arr.length];
			for (int i = 0; i < arr.length; i++) {
				NumberUtil.toBytes(arr[i], bs, i * 8);
			}
		} else if (v instanceof String[]) {
			type = 7;
			String[] arr = (String[]) v;
			bs = new byte[0];
			for (int i = 0; i < arr.length; i++) {
				byte[] b = null;
				b = arr[i].getBytes();
				bs = ArrayUtils.addAll(bs, NumberUtil.toBytes(b.length));
				bs = ArrayUtils.addAll(bs, b);
			}
		} else if (v instanceof Serializable) {
			type = 8;
			bs = FileUtil.serialize((Serializable) v);
		}
		if (compressible) {
			return ZipUtil.zip(ArrayUtils.add(bs, 0, type));
		} else {
			return ArrayUtils.add(bs, 0, type);
		}
	}

	/**
	 * ����Map�Ļ�����Ϣ
	 */
	private synchronized void loadInfo() {
		File f = new File(cacheDirectory);
		if (!f.exists()) {
			f.mkdirs();
		}
		// ��ȡfcm.dat
		f = new File(cacheDirectory + "meta.dat");
		if (f.exists()) {
			byte[] bs = FileUtil.readByte(f);
			size = NumberUtil.toInt(bs, 0);
			total = NumberUtil.toInt(bs, 4);
			modCount = NumberUtil.toInt(bs, 8);
			maxFileIndex = NumberUtil.toShort(bs, 12);
			compressible = NumberUtil.toShort(bs, 14) == 1;
		} else {
			size = 0;
			// total = 65536;// 16;// 1048576;
			maxFileIndex = 0;
			modCount = 0;
		}
	}

	/**
	 * ���������Ϣ
	 */
	public synchronized void save() {
		if (cacheDirectory != null) {
			File f = new File(cacheDirectory + "meta.dat");
			byte[] bs = new byte[16];
			NumberUtil.toBytes(size, bs, 0);// 1-4�ֽڴ�ż�¼����
			NumberUtil.toBytes(total, bs, 4);// 5-8�ֽڴ�ſ���λ����
			NumberUtil.toBytes(modCount, bs, 8);// 9-12�ֽڴ�����ϴ���������ɾ���ļ�¼��
			NumberUtil.toBytes(maxFileIndex, bs, 12);// 13-14�ֽڴ�������ļ�������
			NumberUtil.toBytes((short) (compressible ? 1 : 0), bs, 14);// 15-16�ֽڴ����ѹ����־
			FileUtil.writeByte(f, bs);
		}
	}

	/**
	 * �ر�Map�������������Ϣ
	 */
	public synchronized void close() {
		save();
		if (keyFiles != null) {
			for (int i = 0; i < keyFiles.length; i++) {
				try {
					keyFiles[i].close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if (addressFiles != null) {
			for (int i = 0; i < addressFiles.length; i++) {
				try {
					addressFiles[i].close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if (valueFiles != null) {
			for (int i = 0; i < valueFiles.length; i++) {
				try {
					valueFiles[i].close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Map�Ƿ���ָ����
	 * 
	 * @param k
	 * @return
	 */
	public synchronized boolean containsKey(String k) {
		if (map.containsKey(k)) {
			return true;
		}
		int i = hash(k);
		if (readKey(i, k) != null) {
			return true;
		}
		return false;
	}

	/**
	 * ��ȡ��һ����ֵ��
	 * 
	 * @return
	 */
	public synchronized Entry firstEntry() {
		return Entry.first(this);
	}

	/**
	 * ͬ���ؽ�ָ����ֵ�Դ���Map
	 * 
	 * @param k
	 * @param value
	 */
	private synchronized void put2(String k, Object value) {
		Object o = null;
		if (maxItemInMemory != 0) {
			o = map.put(k, value);
		}
		try {
			if (o == null) {// ������ֵ
				int i = hash(k);
				Key key = readKey(i, k);
				if (key == null) {
					writeData(k, value);
					size++;
					if (size > total * 0.75) {
						resize();
					}
				} else {
					writeData(k, value);
				}
			} else {// ����ԭֵ
				writeData(k, value);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		save();
	}

	/**
	 * �����ֵ��
	 * 
	 * @param k
	 * @param v
	 */
	public void put(String k, Serializable v) {
		put2(k, v);
	}

	/**
	 * �����ֵ��
	 * 
	 * @param k
	 * @param v
	 */
	public void put(String k, int v) {
		put2(k, new Integer(v));
	}

	/**
	 * �����ֵ��
	 * 
	 * @param k
	 * @param v
	 */
	public void put(String k, long v) {
		put2(k, new Long(v));
	}

	/**
	 * �����ֵ��
	 * 
	 * @param k
	 * @param v
	 */
	public void put(String k, String v) {
		put2(k, v);
	}

	/**
	 * �����ֵ��
	 * 
	 * @param k
	 * @param v
	 */
	public void put(String k, byte[] v) {
		put2(k, v);
	}

	/**
	 * �����ֵ��
	 * 
	 * @param k
	 * @param v
	 */
	public void put(String k, int[] v) {
		put2(k, v);
	}

	/**
	 * �����ֵ��
	 * 
	 * @param k
	 * @param v
	 */
	public void put(String k, long[] v) {
		put2(k, v);
	}

	/**
	 * �����ֵ��
	 * 
	 * @param k
	 * @param v
	 */
	public void put(String k, String[] v) {
		put2(k, v);
	}

	/**
	 * ��ȡ��ֵ����ת��Ϊint
	 * 
	 * @param key
	 * @return
	 */
	public int getInt(String key) {
		Object o = get(key);
		if (o == null) {
			return Integer.MIN_VALUE;
		}
		if (o instanceof Integer) {
			return ((Integer) o).intValue();
		}
		throw new RuntimeException("Key��Ӧ�����ݲ���ָ������!");
	}

	/**
	 * ��ȡ��ֵ����ת��Ϊlong
	 * 
	 * @param key
	 * @return
	 */
	public long getLong(String key) {
		Object o = get(key);
		if (o == null) {
			return Long.MIN_VALUE;
		}
		if (o instanceof Long) {
			return ((Long) o).longValue();
		}
		throw new RuntimeException("Key��Ӧ�����ݲ���ָ������!");
	}

	/**
	 * ��ȡ��ֵ����ת��ΪString
	 * 
	 * @param key
	 * @return
	 */
	public String getString(String key) {
		Object o = get(key);
		if (o == null) {
			return null;
		}
		if (o instanceof String) {
			return (String) o;
		}
		throw new RuntimeException("Key��Ӧ�����ݲ���ָ������!");
	}

	/**
	 * ��ȡ��ֵ����ת��Ϊbyte[]
	 * 
	 * @param key
	 * @return
	 */
	public byte[] getByteArray(String key) {
		Object o = get(key);
		if (o == null) {
			return null;
		}
		if (o instanceof byte[]) {
			return (byte[]) o;
		}
		throw new RuntimeException("Key��Ӧ�����ݲ���ָ������:" + key);
	}

	/**
	 * ��ȡ��ֵ����ת��Ϊint[]
	 * 
	 * @param key
	 * @return
	 */
	public int[] getIntArray(String key) {
		Object o = get(key);
		if (o == null) {
			return null;
		}
		if (o instanceof int[]) {
			return (int[]) o;
		}
		throw new RuntimeException("Key��Ӧ�����ݲ���ָ������!");
	}

	public long[] getLongArray(String key) {
		Object o = get(key);
		if (o == null) {
			return null;
		}
		if (o instanceof long[]) {
			return (long[]) o;
		}
		throw new RuntimeException("Key��Ӧ�����ݲ���ָ������!");
	}

	/**
	 * ��ȡ��ֵ����ת��ΪString[]
	 * 
	 * @param key
	 * @return
	 */
	public String[] getStringArray(String key) {
		Object o = get(key);
		if (o == null) {
			return null;
		}
		if (o instanceof String[]) {
			return (String[]) o;
		}
		throw new RuntimeException("Key��Ӧ�����ݲ���ָ������!");
	}

	/**
	 * �������λ��
	 */
	private synchronized void resize() throws IOException {
		if (size < total * 0.75) {// �����߳�ͬʱ����
			return;
		}
		int total2 = total * 2;
		int fileCount = new Double(Math.ceil(total2 * 1.0 / AddressCountInOneFile)).intValue();
		BufferedRandomAccessFile[] files = new BufferedRandomAccessFile[fileCount];
		int prefix = new Double(Math.log(total2 / 16) / Math.log(2)).intValue();
		for (int i = 0; i < fileCount; i++) {
			files[i] = new BufferedRandomAccessFile(cacheDirectory + prefix + "key" + i + ".idx", "rw");
			if (i == addressFileCount - 1) {
				files[i].setLength((total2 - (addressFileCount - 1) * AddressCountInOneFile) * 9);
			} else {
				files[i].setLength(AddressCountInOneFile * 9);
			}
		}
		// key.idx����λ�ñ���ȫ����������
		byte[] empty = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		for (int i = 0; i < keyFiles.length; i++) {
			BufferedRandomAccessFile f = keyFiles[i];
			int pos = 0;
			while (f.length() > pos) {
				f.seek(pos);
				byte deleted = f.readByte();
				f.skipBytes(10);
				short len = f.readShort();
				// System.out.println(len);
				if (deleted == 1) {// =0ʱ��ɾ������Ҫ�˹�
					byte[] bs = new byte[len];
					f.read(bs);
					f.seek(pos + 13 + len);
					f.write(empty);// ���9λ������
					String k = new String(bs);
					int index = hash(k, total2);
					int c = index % AddressCountInOneFile;// ���ļ��е�λ��
					index = index / AddressCountInOneFile;
					BufferedRandomAccessFile af = files[index];
					af.seek(c * 9);
					if (af.readByte() == 0) {
						af.seek(c * 9);
						af.writeByte(1);
						af.writeShort(i);
						af.writeShort(22 + bs.length);
						af.writeInt(pos);
					} else {// ����ռ�ã����������ǳ�ͻ��
						af.seek(c * 9);
						Key key = getKey(af);
						if (key == null) {// δ��ռ�ù�
							throw new RuntimeException("������������Ӧ����Key��λ��δ�ҵ�Key.");
						} else {
							// �������ͻ
							while (true) {
								BufferedRandomAccessFile kf = keyFiles[key.KeyFileIndex];
								int pos2 = key.keyAddress + key.KeyLength - 9;
								kf.seek(pos2);
								key = getKey(kf);
								if (key == null) {// �����ĳ�ͻ��
									// дǰһ�����ĺ�9λ
									kf.seek(pos2);
									kf.writeByte(1);
									kf.writeShort(i);
									kf.writeShort(22 + bs.length);
									kf.writeInt(pos);
									break;
								}
							}
						}
					}
				}
				pos += len + 22;

			}
		}
		BufferedRandomAccessFile[] tmp = addressFiles;
		addressFiles = files;
		total = total2;
		for (int i = 0; i < tmp.length; i++) {
			tmp[i].delete();
		}
	}

	/**
	 * ��ȡ��ֵ�����ؿ����л��Ķ���
	 * 
	 * @param k
	 * @return
	 */
	public synchronized Serializable get(String k) {
		Object o = null;
		if (maxItemInMemory != 0) {
			o = map.get(k);
		}
		if (o != null) {
			return (Serializable) o;
		}
		try {
			return (Serializable) readData(k);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ɾ����ֵ��
	 * 
	 * @param k
	 * @return
	 */
	public synchronized boolean remove(String k) {
		map.remove(k);
		int index = hash(k);
		int c = index % AddressCountInOneFile;// ���ļ��е�λ��
		index = index / AddressCountInOneFile;// �ڵڼ����ļ���
		try {
			if (addressFiles == null) {
				initFiles();
			}
			BufferedRandomAccessFile f = addressFiles[index];
			f.seek(c * 9);
			Key key = getKey(f);
			if (key == null) {
				return false;
			}
			if (key.KeyString.equals(k)) {// ��Ҫ����Ƿ��г�ͻ�ļ�
				f = keyFiles[key.KeyFileIndex];
				int pos = key.keyAddress + key.KeyLength - 9;
				f.seek(pos);
				if (f.readByte() == 0) {// û�г�ͻ����ֱ�Ӹ�дkey.idx
					f = addressFiles[index];
					f.seek(c * 9);
					f.writeByte(0);
				} else {// �г�ͻ������Ҫ�������9λ��key.idx
					f.seek(pos);
					byte[] bs = new byte[9];
					f.read(bs);
					f = addressFiles[index];
					f.seek(c * 9);
					f.write(bs);
				}
				f = keyFiles[key.KeyFileIndex];
				f.seek(key.keyAddress);
				f.writeByte(0);
				// д����������
				f = valueFiles[key.DataFileIndex];
				f.seek(key.DataAddress);
				f.writeByte(0);// ��ʶ����ɾ��
				size--;
				modCount++;
				save();
				return true;
			}
			// �������ͻ,��ͻ�ļ�����key.idx�У�����key.dat��
			while (true) {
				int index2 = key.KeyFileIndex;
				f = keyFiles[index2];
				int pos2 = key.keyAddress + key.KeyLength - 9;
				f.seek(pos2);
				key = getKey(f);
				if (key == null) {
					return false;
				}
				if (key.KeyString.equals(k)) {// �������Ƿ��г�ͻ�ļ�
					f = keyFiles[key.KeyFileIndex];
					f.seek(key.keyAddress);
					f.writeByte(0);
					int pos = key.keyAddress + key.KeyLength - 9;
					f.seek(pos);
					if (f.readByte() == 1) {// =0ʱû�г�ͻ����ֱ���õ�һ���ֽ�Ϊ0
						// �г�ͻ������Ҫ�������9λ����һ���������9λ
						f.seek(pos);
						byte[] bs = new byte[9];
						f.read(bs);
						f = keyFiles[index2];
						f.seek(pos2);
						f.write(bs);
					}
					// д����������
					f = valueFiles[key.DataFileIndex];
					f.seek(key.DataAddress);
					f.writeByte(0);// ��ʶ����ɾ��
					size--;
					modCount++;
					save();
					return true;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * ���modCount�Ƿ񼺴ﵽ�ٽ�ֵ,������ﵽ����������֯�ļ�
	 */
	public synchronized void refactor() {
		if (total * 0.5 > modCount) {
			return;
		}
		// todo:������֯�ļ�,δʵ��
	}

	/**
	 * ����key����key��ַ��key.idx�е�λ�á��㷨����HashMap.hash();
	 */
	private int hash(Object x) {
		return hash(x, total);
	}

	/**
	 * hash�㷨������HashMap��
	 * 
	 * @param x
	 * @param length
	 * @return
	 */
	public static int hash(Object x, int length) {
		int h = x.hashCode();
		h += ~(h << 9);
		h ^= (h >>> 14);
		h += (h << 4);
		h ^= (h >>> 10);
		return h & (length - 1);
	}

	/**
	 * ��ֵ������
	 * 
	 * @return
	 */
	public int size() {
		return size;
	}

	/**
	 * ��ȡMap�����ļ���
	 * 
	 * @return
	 */
	public String getCacheDirectory() {
		return cacheDirectory;
	}

	/**
	 * ����Map�����ļ���
	 * 
	 * @param cacheDirectory
	 */
	public void setCacheDirectory(String cacheDirectory) {
		if (cacheDirectory.endsWith("/") || cacheDirectory.endsWith("\\")) {
			cacheDirectory += "/";
		}
		this.cacheDirectory = cacheDirectory;
	}

	public int getMaxItemInMemory() {
		return maxItemInMemory;
	}

	public void setMaxItemInMemory(int maxItemInMemory) {
		this.maxItemInMemory = maxItemInMemory;
	}

	public static class Key {
		short KeyFileIndex;

		short KeyLength;

		int keyAddress;

		String KeyString;

		short DataFileIndex;

		int DataLength;

		int DataAddress;
	}

	/**
	 * ��ʾһ����ֵ��
	 * 
	 * @author NSWT
	 * @date 2009-11-15
	 * @email nswt@nswt.com.cn
	 */
	public static class Entry {
		private FileCachedMapx fcm;

		private Key key;

		protected Entry(FileCachedMapx fcm, Key key) {
			this.fcm = fcm;
			this.key = key;
		}

		/**
		 * ��ȡ��
		 * 
		 * @return
		 */
		public String getKey() {
			return key.KeyString;
		}

		/**
		 * ��ȡֵ
		 * 
		 * @return
		 */
		public Object getValue() {
			synchronized (fcm) {
				BufferedRandomAccessFile f = fcm.valueFiles[key.DataFileIndex];
				try {
					f.seek(key.DataAddress + 9 + key.KeyLength - 22);
					byte[] bv = new byte[key.DataLength - 9 - key.KeyLength + 22];
					f.read(bv);
					return fcm.toObject(bv);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		/**
		 * ����Map�ĵ�һ����ֵ��
		 * 
		 * @param fcm
		 * @return
		 */
		protected static Entry first(FileCachedMapx fcm) {
			synchronized (fcm) {
				try {
					if (fcm.addressFiles == null) {
						fcm.initFiles();
					}
					BufferedRandomAccessFile f = fcm.keyFiles[0];
					int pos = 0;
					Key key = new Key();
					while (f.length() > pos) {
						f.seek(pos);
						byte deleted = f.readByte();
						key.DataFileIndex = f.readShort();// �ڵڼ��������ļ���
						key.DataLength = f.readInt();// ���ݳ���
						key.DataAddress = f.readInt();// ���ݵ�ַ
						short len = f.readShort();
						key.KeyLength = (short) (len + 22);
						if (deleted == 1) {// =0ʱ��ɾ������Ҫ�˹�
							byte[] bs = new byte[len];
							f.read(bs);
							key.KeyString = new String(bs);
							key.keyAddress = pos;
							return new Entry(fcm, key);
						}
						pos += len + 22;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		/**
		 * ������һ����ֵ��
		 * 
		 * @return
		 */
		public Entry next() {
			synchronized (fcm) {
				try {
					BufferedRandomAccessFile f = fcm.keyFiles[key.KeyFileIndex];
					int pos = key.keyAddress + key.KeyLength;
					Key nextKey = new Key();
					nextKey.KeyFileIndex = key.KeyFileIndex;
					if (pos == f.length()) {
						if (key.KeyFileIndex == fcm.maxFileIndex) {// ��Ҳû�м���
							return null;
						}
						nextKey.KeyFileIndex++;
						nextKey.keyAddress = 0;
						f = fcm.keyFiles[nextKey.KeyFileIndex];
						pos = 0;
					}
					nextKey.keyAddress = pos;
					while (f.length() > pos) {
						f.seek(pos);
						byte deleted = f.readByte();
						nextKey.DataFileIndex = f.readShort();// �ڵڼ��������ļ���
						nextKey.DataLength = f.readInt();// ���ݳ���
						nextKey.DataAddress = f.readInt();// ���ݵ�ַ
						short len = f.readShort();
						if (deleted == 1) {// =0ʱ��ɾ������Ҫ�˹�
							byte[] bs = new byte[len];
							f.read(bs);
							nextKey.KeyString = new String(bs);
							nextKey.KeyLength = (short) (len + 22);
							nextKey.keyAddress = pos;
							return new Entry(fcm, nextKey);
						}
						pos += len + 22;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return null;
		}
	}

	/**
	 * ��ֵ�����Ƿ�ѹ��
	 * 
	 * @return
	 */
	public boolean isCompressible() {
		return compressible;
	}
}
