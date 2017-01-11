package com.nswt.framework.utility;

import java.math.BigDecimal;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * ���ֹ�����
 * 
 * @Author NSWT
 * @Date 2016-12-20
 * @Mail nswt@nswt.com.cn
 */
public class NumberUtil {
	private static Pattern numberPatter = Pattern.compile("^[\\d\\.E\\,]*$");

	/**
	 * �Ƿ�������
	 */
	public static boolean isNumber(String str) {
		if (StringUtil.isEmpty(str)) {
			return false;
		}
		return numberPatter.matcher(str).find();
	}

	/**
	 * �Ƿ�����������
	 */
	public static boolean isInt(String str) {
		if (StringUtil.isEmpty(str)) {
			return false;
		}
		try {
			Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	/**
	 * �Ƿ�����������
	 */
	public static boolean isInteger(String str) {
		return isInt(str);
	}

	/**
	 * �Ƿ��ǳ���������
	 */
	public static boolean isLong(String str) {
		if (StringUtil.isEmpty(str)) {
			return false;
		}
		try {
			Long.parseLong(str);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	/**
	 * ��������
	 */
	public static double round(double v, int scale) {
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	private static long Seed = System.currentTimeMillis();

	private static Random rand = new Random();

	/**
	 * ��0-max��Χ�ڻ�ȡ�������
	 */
	public static int getRandomInt(int max) {
		rand.setSeed(Seed);
		Seed++;
		return rand.nextInt(max);
	}

	/**
	 * ������תΪ����
	 */
	public static int toInt(byte[] bs) {
		return toInt(bs, 0);
	}

	/**
	 * ��ָ��λ�ÿ�ʼ��ȡ4λ�����ƣ�ת��Ϊ����
	 */
	public static int toInt(byte[] bs, int start) {
		int i = 0;
		i += (bs[start] & 255) << 24;
		i += (bs[start + 1] & 255) << 16;
		i += (bs[start + 2] & 255) << 8;
		i += (bs[start + 3] & 255);
		return i;
	}

	/**
	 * ����תΪ������
	 */
	public static byte[] toBytes(int i) {
		byte[] bs = new byte[4];
		bs[0] = (byte) (i >> 24);
		bs[1] = (byte) (i >> 16);
		bs[2] = (byte) (i >> 8);
		bs[3] = (byte) (i & 255);
		return bs;
	}

	/**
	 * ����תΪ4λ����������д�뵽ָ�������ָ��λ��
	 */
	public static void toBytes(int i, byte[] bs, int start) {
		bs[start] = (byte) (i >> 24);
		bs[start + 1] = (byte) (i >> 16);
		bs[start + 2] = (byte) (i >> 8);
		bs[start + 3] = (byte) (i & 255);
	}

	/**
	 * ��ȡ2λ�����ƣ�תΪ������
	 */
	public static short toShort(byte[] bs) {
		return toShort(bs, 0);
	}

	/**
	 * ��ָ�������ָ��λ�ÿ�ʼ����ȡ2λ�����ƣ�תΪ������
	 */
	public static short toShort(byte[] bs, int start) {
		short i = 0;
		i += (bs[start + 0] & 255) << 8;
		i += (bs[start + 1] & 255);
		return i;
	}

	/**
	 * ������תΪ������
	 */
	public static byte[] toBytes(short i) {
		byte[] bs = new byte[2];
		bs[0] = (byte) (i >> 8);
		bs[1] = (byte) (i & 255);
		return bs;
	}

	/**
	 * ������תΪ2λ����������д�뵽ָ�������ָ��λ��
	 */
	public static void toBytes(short i, byte[] bs, int start) {
		bs[start + 0] = (byte) (i >> 8);
		bs[start + 1] = (byte) (i & 255);
	}

	/**
	 * ������תΪ8λ������
	 */
	public static byte[] toBytes(long i) {
		byte[] bs = new byte[8];
		bs[0] = (byte) (i >> 56);
		bs[1] = (byte) (i >> 48);
		bs[2] = (byte) (i >> 40);
		bs[3] = (byte) (i >> 32);
		bs[4] = (byte) (i >> 24);
		bs[5] = (byte) (i >> 16);
		bs[6] = (byte) (i >> 8);
		bs[7] = (byte) (i & 255);
		return bs;
	}

	/**
	 * ������תΪ8λ����������д�뵽ָ�������ָ��λ��
	 */
	public static void toBytes(long l, byte[] bs, int start) {
		byte[] arr = toBytes(l);
		for (int i = 0; i < 8; i++) {
			bs[start + i] = arr[i];
		}
	}

	/**
	 * ������ת������
	 */
	public static long toLong(byte[] bs) {
		return toLong(bs, 0);
	}

	/**
	 * ��ָ�����ݵ�ָ��λ�ÿ�ʼ����ȡ8λ�����ƣ�תΪ������
	 */
	public static long toLong(byte[] bs, int index) {
		return ((((long) bs[index] & 0xff) << 56) | (((long) bs[index + 1] & 0xff) << 48)
				| (((long) bs[index + 2] & 0xff) << 40) | (((long) bs[index + 3] & 0xff) << 32)
				| (((long) bs[index + 4] & 0xff) << 24) | (((long) bs[index + 5] & 0xff) << 16)
				| (((long) bs[index + 6] & 0xff) << 8) | (((long) bs[index + 7] & 0xff) << 0));

	}

	public static void main(String[] args) {
		byte[] bs = NumberUtil.toBytes(Long.MAX_VALUE);
		System.out.println(toLong(bs));
		System.out.println(Long.MAX_VALUE);
	}
}
