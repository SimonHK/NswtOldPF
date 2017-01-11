package com.nswt.framework.utility;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import com.nswt.framework.utility.gif.GifEncoder;

/**
 * �����������࣬Ŀǰ��֧��39��
 * 
 * ���ߣ�NSWT<br>
 * ���ڣ�2016-8-8<br>
 * �ʼ���nswt@nswt.com.cn<br>
 */
public class BarCode {
	// ����ļ�����
	public static String FORMAT_JPEG = "jpg";

	public static String FORMAT_GIF = "gif";

	public static String FORMAT_PNG = "png";

	public static String FORMAT_BMP = "bmp";

	// �������������
	public static int TYPE_CODE39 = 1;

	private BufferedImage image = null;

	private String codeStr = ""; // Ҫ�����ԭʼ�ı�

	private String codeBinary = ""; // ת���ɱ�����01��

	private String m_fileFormat = FORMAT_JPEG;

	private int codeType = TYPE_CODE39;

	private boolean isTextVisable = true; // ԭʼ�ı��Ƿ�ɼ�

	private String bgColor = "#FFFFFF";

	private String fgColor = "000000";

	private int xMargin = 10; // �������ͼ���Եˮƽ����

	private int yMargin = 10; // �������ͼ���Ե��ֱ����

	private int barHeight = 40; // �������볤��

	private int barWidth = 1; // ���������

	private int barRatio = 3; // ��ϸ���ȱ�ֵ

	public BarCode(String code) {
		this.codeStr = code;
	}

	// ����һ��BufferedImage����
	public BufferedImage getImage() {
		generatedImage();
		return image;
	}

	// ������ͼƬд�뵽OutputStream��
	public void getOutputStream(OutputStream os) throws IOException {
		generatedImage();
		if (this.m_fileFormat.equals(FORMAT_GIF)) {
			try {
				GifEncoder gif = new GifEncoder(image);
				gif.Write(os);
			} catch (AWTException e) {
				e.printStackTrace();
			}
		} else {
			ImageIO.write(image, this.m_fileFormat, os);
		}
	}

	// ������ͼƬд�뵽ָ���ļ���
	public void writeToFile(String fileName) throws IOException {
		generatedImage();
		File f = new File(fileName);
		OutputStream os = new FileOutputStream(f);
		try {
			GifEncoder gif = new GifEncoder(image);
			gif.Write(os);
		} catch (AWTException e) {
			e.printStackTrace();
		}
		os.close();
	}

	// ������ͼƬת�����ֽ�����
	public byte[] getBytes() throws IOException {
		generatedImage();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			GifEncoder gif = new GifEncoder(image);
			gif.Write(bos);
		} catch (AWTException e) {
			e.printStackTrace();
		}
		return bos.toByteArray();
	}

	// ��������ͼ��
	public void generatedImage() {
		int w_Start = this.xMargin;
		int h_Start = this.yMargin;
		if (this.codeType == TYPE_CODE39) {
			this.codeBinary = Code39.transferCode(this.codeStr);
		}
		int barWidthTotal = 0;

		for (int i = 0; i < this.codeBinary.length(); i++) {
			if (this.codeBinary.charAt(i) == '1') {
				barWidthTotal += this.barRatio * this.barWidth;
			} else {
				barWidthTotal += this.barWidth;
			}
		}
		int imageWidth = this.xMargin * 2 + barWidthTotal;
		int imageHeight = h_Start + this.barHeight + (h_Start > 15 ? h_Start : 15);
		this.image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
		setImageBgColor(image, this.bgColor);
		Graphics g = image.getGraphics();
		Font font = new Font(null, Font.PLAIN, 11); // Ĭ�����壬11pt
		g.setFont(font);
		g.setColor(Color.decode(this.fgColor));
		for (int i = 0; i < this.codeBinary.length(); i++) {
			if (this.codeBinary.charAt(i) == '1') {
				if (i % 2 == 0) {
					g.fillRect(w_Start, h_Start, this.barRatio * this.barWidth, this.barHeight);
				}
				w_Start += this.barRatio * this.barWidth;
			} else {
				if (i % 2 == 0) {
					g.fillRect(w_Start, h_Start, this.barWidth, this.barHeight);
				}
				w_Start += this.barWidth;
			}
		}
		if (this.isTextVisable) { // ��ʾ��Ӧ�ı����������·�
			FontMetrics fonM = g.getFontMetrics();
			int yFont = fonM.getAscent() + fonM.getDescent();
			int xFont = fonM.stringWidth(this.codeStr);
			g.drawString(this.codeStr.toUpperCase(), imageWidth / 2 - xFont / 2, h_Start + yFont - 3 + this.barHeight);
		}
	}

	// ���ñ���
	public void setBgColor(String str) {
		this.bgColor = str;
	}

	// �����������ͣ�Ŀǰֻ֧��CODE39
	public void setCodeType(int type) {
		this.codeType = type;
	}

	// �������ͼ���ʽ
	public void setFormatType(String str) {
		this.m_fileFormat = str;
	}

	// ����������ɫ
	public void setForeColor(String str) {
		this.fgColor = str;
	}

	// ��������Ŀɶ������Ƿ���ʾ
	public void setTextVisable(boolean bFlag) {
		this.isTextVisable = bFlag;
	}

	// ����ˮƽ�߾�
	public void setXMargin(int x) {
		this.xMargin = x;
	}

	// ���ô�ֱ�߾�
	public void setYMargin(int y) {
		this.yMargin = y;
	}

	// �����������
	public void setBarWidth(int bw) {
		this.barWidth = bw;
	}

	// ���������볤��
	public void setBarHeight(int bh) {
		this.barHeight = bh;
	}

	// ���þ�ϸ��Ԫ�Ŀ��ȱ�
	public void setBarRatio(int br) {
		this.barRatio = br;
	}

	// ��������ͼ��ı���ɫ
	private static void setImageBgColor(BufferedImage image, String strColor) {
		Graphics g = image.getGraphics();
		String t_Str = new String(strColor);
		Color t_Color = g.getColor();
		if (!t_Str.startsWith("#")) {
			t_Str = "#" + t_Str;
		}
		if (t_Str.length() != 7) {
			LogUtil.getLogger().warn("BarCode:�������ɫֵ" + t_Str);
			return;
		}
		g.setColor(Color.decode(t_Str));
		g.fillRect(0, 0, image.getWidth(), image.getHeight());
		g.setColor(t_Color);
	}

	// ���Է���
	public static void main(String[] args) {
		String code = "SINO2345";
		System.out.println(Code39.transferCode(code));
		BarCode barcode = new BarCode(code);
		barcode.setFormatType(BarCode.FORMAT_GIF);
		try {
			// barcode.writeToFile("F:\\1.gif");
			System.out.println(barcode.getBytes().length);
		} catch (IOException ex) {
			System.out.println("�����빤��BarCode:д�ļ�����");
		}
	}
}

class Code39 {
	private static String codeSet[][] = { { "000110100", "0" }, { "100100001", "1" }, { "001100001", "2" },
			{ "101100000", "3" }, { "000110001", "4" }, { "100110000", "5" }, { "001110000", "6" },
			{ "000100101", "7" }, { "100100100", "8" }, { "001100100", "9" }, { "100001001", "A" },
			{ "001001001", "B" }, { "101001000", "C" }, { "000011001", "D" }, { "100011000", "E" },
			{ "001011000", "F" }, { "000001101", "G" }, { "100001100", "H" }, { "001001100", "I" },
			{ "000011100", "J" }, { "100000011", "K" }, { "001000011", "L" }, { "101000010", "M" },
			{ "000010011", "N" }, { "100010010", "O" }, { "001010010", "P" }, { "000000111", "Q" },
			{ "100000110", "R" }, { "001000110", "S" }, { "000010110", "T" }, { "110000001", "U" },
			{ "011000001", "V" }, { "111000000", "W" }, { "010010001", "X" }, { "110010000", "Y" },
			{ "011010000", "Z" }, { "010000101", "-" }, { "110000100", "." }, { "011000100", " " },
			{ "010101000", "$" }, { "010100010", "/" }, { "010001010", "+" }, { "000101010", "%" },
			{ "010010100", "*" } };

	public static String getCode(char c) {
		for (int i = 0; i < 44; i++) {
			if (codeSet[i][1].charAt(0) == c) {
				return codeSet[i][0];
			}
		}
		return "";
	}

	public static String transferCode(String str) {
		StringBuffer bf = new StringBuffer();
		String t_Str = "";
		char[] cArray = str.toUpperCase().toCharArray();

		bf.append(getCode('*'));
		bf.append('0');
		for (int i = 0; i < cArray.length; i++) {
			t_Str = getCode(cArray[i]);
			if (!t_Str.equals("")) {
				bf.append(t_Str);
				bf.append('0');
			} else {
				LogUtil.getLogger().warn("Code39:�Ƿ��ַ�");
				return "";
			}
		}
		bf.append(getCode('*'));
		return bf.toString();
	}
}