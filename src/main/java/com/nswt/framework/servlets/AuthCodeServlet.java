/**
 * ���ߣ�NSWT<br>
 * ���ڣ�2016-8-8<br>
 * �ʼ���nswt@nswt.com.cn
 */
package com.nswt.framework.servlets;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.nswt.framework.Constant;
import com.nswt.framework.User;
import com.nswt.framework.User.UserData;

public class AuthCodeServlet extends HttpServlet {
	private static final long serialVersionUID = 20060808L;

	private static final String CONTENT_TYPE = "image/jpeg";

	private static final int DEFAULT_WIDTH = 50;

	private static final int DEFAULT_HEIGHT = 14;

	private static final int DEFAULT_LENGTH = 4;

	public static final String DEFAULT_CODETYPE = "2";// Ĭ��Ϊ��׼ģʽ

	private static String CodeType;

	private static String AuthKey;

	private static int Width;

	private static int Height;

	private static int Length;

	private static OutputStream out;

	private static Random rand = new Random(System.currentTimeMillis());

	private static String seed;

	private static BufferedImage image;
	
	private static Object mutex = new Object();

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		generate(request, response);
	}

	public static void generate(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		synchronized (mutex) {
			try {
				CodeType = request.getParameter("CodeType");
				AuthKey = request.getParameter("AuthKEY");
				String tWidth = request.getParameter("Width");
				String tHeight = request.getParameter("Height");
				String tLength = request.getParameter("Length");
				if (CodeType == null || CodeType.equals("")) {
					CodeType = DEFAULT_CODETYPE;
				}
				if (AuthKey == null || AuthKey.equals("")) {
					AuthKey = Constant.DefaultAuthKey;
				}
				if (tWidth == null || tWidth.equals("")) {
					Width = DEFAULT_WIDTH;
				}
				if (tHeight == null || tHeight.equals("")) {
					Height = DEFAULT_HEIGHT;
				}
				if (tLength == null || tLength.equals("")) {
					Length = DEFAULT_LENGTH;
				}
				try {
					Width = Integer.parseInt(tWidth);
				} catch (Exception ex) {
					Width = DEFAULT_WIDTH;
				}
				try {
					Height = Integer.parseInt(tHeight);
				} catch (Exception ex) {
					Height = DEFAULT_HEIGHT;
				}
				try {
					Length = Integer.parseInt(tLength);
				} catch (Exception ex) {
					Length = DEFAULT_LENGTH;
				}
				response.setContentType(CONTENT_TYPE);
				response.setHeader("Pragma", "No-cache");
				response.setHeader("Cache-Control", "no-cache");
				response.setDateHeader("Expires", 0);

				out = response.getOutputStream();
				seed = getSeed();
				Object o = request.getSession().getAttribute(Constant.UserAttrName);
				if (o != null) {
					User.setCurrent((UserData) o);
					User.setValue(AuthKey, seed);
				} else {
					UserData u = new UserData();
					User.setCurrent(u);
					User.setValue(AuthKey, seed);
					request.getSession().setAttribute(Constant.UserAttrName, u);
				}

				if (CodeType.equals("1")) {
					code1(request, response);
				} else if (CodeType.equals("2")) {
					code2(request, response);
				} else if (CodeType.equals("3")) {
					code3(request, response);
				}
				try {
					JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
					JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(image);
					param.setQuality(1.0f, false);
					encoder.setJPEGEncodeParam(param);
					encoder.encode(image);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * ����ģʽ
	 */
	private static BufferedImage code1(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		image = new BufferedImage(Width, Height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		g.setColor(new Color(245, 245, 245));
		g.fillRect(0, 0, Width, Height);
		g.setColor(Color.DARK_GRAY);
		g.setFont(new Font("Arial", Font.PLAIN, 12));

		g.drawString(seed, 3, 11);
		g.dispose();
		return image;
	}

	/**
	 * ��׼ģʽ
	 */
	private static BufferedImage code2(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		image = new BufferedImage(Width, Height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, Width, Height);
		g.setFont(new Font("Arial", Font.PLAIN, new Double(Height * 1.0 / 14 * 12).intValue()));
		// �������155�������ߣ�ʹͼ���е���֤�벻�ױ���������̽�⵽
		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < 80; i++) {
			// int x = rand.nextInt(Width);
			// int y = rand.nextInt(Height);
			// int xl = rand.nextInt(12);
			// int yl = rand.nextInt(12);
			// g.drawLine(x, y, x + xl, y + yl);
		}
		// ȡ�����������֤��
		for (int i = 0; i < Length; i++) {
			String c = seed.substring(i, i + 1);
			g.setColor(new Color(20 + rand.nextInt(110), 20 + rand.nextInt(110), 20 + rand.nextInt(110)));// ���ú�����������ɫ��ͬ����������Ϊ����̫�ӽ�������ֻ��ֱ������

			g.drawString(c, new Double(Width * 1.0 / 50 * 11).intValue() * i
					+ new Double(Width * 1.0 / 50 * 3).intValue(), new Double(Height * 1.0 / 14 * 11).intValue());
		}
		g.dispose();
		return image;
	}

	/**
	 * ����ģʽ���ı������б
	 */
	private static BufferedImage code3(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		image = new BufferedImage(Width, Height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) image.getGraphics();
		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, Width, Height);
		g.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		// �������155�������ߣ�ʹͼ���е���֤�벻�ױ���������̽�⵽
		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < 155; i++) {
			int x = rand.nextInt(Width);
			int y = rand.nextInt(Height);
			int xl = rand.nextInt(12);
			int yl = rand.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}
		// ȡ�����������֤��
		AffineTransform fontAT = new AffineTransform();
		for (int i = 0; i < Length; i++) {
			String c = seed.substring(i, i + 1);
			g.setColor(new Color(20 + rand.nextInt(110), 20 + rand.nextInt(110), 20 + rand.nextInt(110)));// ���ú�����������ɫ��ͬ����������Ϊ����̫�ӽ�������ֻ��ֱ������
			fontAT.shear(rand.nextFloat() * 0.6 - 0.3, 0.0);
			FontRenderContext frc = g.getFontRenderContext();
			Font theDerivedFont = g.getFont().deriveFont(fontAT);
			TextLayout tstring2 = new TextLayout(c, theDerivedFont, frc);// ������ͨ�ַ���
			tstring2.draw(g, (float) (7 * i + 2), (float) 11);// ������б�����ַ���
		}
		g.dispose();
		return image;
	}

	static char[] arr = "23456789qwertyuipasdfghjkzxcvbnm".toCharArray();

	private static String getSeed() {
		StringBuffer sb = new StringBuffer(Length);
		for (int i = 0; i < Length; i++) {
			sb.append(arr[rand.nextInt(arr.length)]);
		}
		// System.out.println(sb);
		return sb.toString();
	}

	private static Color getRandColor(int fc, int bc) {// ������Χ��������ɫ
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + rand.nextInt(bc - fc);
		int g = fc + rand.nextInt(bc - fc);
		int b = fc + rand.nextInt(bc - fc);

		/* ${_NSWT_LICENSE_CODE_} */
		return new Color(r, g, b);
	}
}
