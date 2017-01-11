/**
 * ���ߣ�NSWT<br>
 * ���ڣ�2016-10-16<br>
 * �ʼ���nswt@nswt.com.cn<br>
 */
package com.nswt.framework.utility;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.nswt.framework.utility.gif.BmpUtil;
import com.nswt.framework.utility.gif.GifUtil;

public class ImageJDKUtil {
	/**
	 * �õ�һ��Image���󰴱������ź���µ�Image����
	 * 
	 * @param srcImage
	 * @param rate
	 * @return ��������֮���Image����
	 */
	public static BufferedImage scaleRate(BufferedImage srcImage, double rate) {
		return scaleRate(srcImage, rate, rate, null);
	}

	/**
	 * ����ͼ��ʹ������width���߲�����height��ͬʱ���ֱ�������
	 * 
	 * @param srcImage
	 * @param width
	 * @param height
	 * @return
	 */
	public static BufferedImage scaleRate(BufferedImage srcImage, int width, int height) {
		double w = srcImage.getWidth();
		double h = srcImage.getHeight();
		if (w < width && h < height) {
			return srcImage;
		}
		if (height == 0) {
			if (w <= width) {
				return srcImage;
			} else {
				return scaleRate(srcImage, width / w, width / w, null);
			}
		} else if (width == 0) {
			if (h <= height) {
				return srcImage;
			} else {
				return scaleRate(srcImage, height / h, height / h, null);
			}
		}
		if (w / h > width / height) {
			return scaleRate(srcImage, width / w, width / w, null);
		} else {
			return scaleRate(srcImage, height / h, height / h, null);
		}
	}

	/**
	 * ͼ��ҶȻ�
	 * 
	 * @param srcImage
	 * @return
	 */
	public static BufferedImage gray(BufferedImage srcImage) {
		BufferedImage dstImage = new BufferedImage(srcImage.getWidth(), srcImage.getHeight(), srcImage.getType());
		Graphics2D g2 = dstImage.createGraphics();
		RenderingHints hints = g2.getRenderingHints();
		g2.dispose();
		ColorSpace grayCS = ColorSpace.getInstance(ColorSpace.CS_GRAY);
		ColorConvertOp colorConvertOp = new ColorConvertOp(grayCS, hints);
		colorConvertOp.filter(srcImage, dstImage);
		return dstImage;
	}

	/**
	 * ������ֱ�ָ����������
	 * 
	 * @param srcImage
	 * @param xscale
	 * @param yscale
	 * @param hints
	 * @return
	 */
	public static BufferedImage scaleRate(BufferedImage srcImage, double xscale, double yscale, RenderingHints hints) {
		AffineTransform transform = AffineTransform.getScaleInstance(xscale, yscale);
		AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		return op.filter(srcImage, null);
	}

	/**
	 * ����ͼƬ���̶���С
	 */
	public static BufferedImage scaleFixed(BufferedImage srcImage, int width, int height, boolean keepRate) {
		int srcWidth = srcImage.getWidth(); // Դͼ���
		int srcHeight = srcImage.getHeight(); // Դͼ�߶�
		double wScale = width * 1.0 / srcWidth;
		double hScale = height * 1.0 / srcHeight;
		if (keepRate) {
			if (wScale > hScale && hScale != 0) {
				wScale = hScale;
			} else {
				hScale = wScale;
			}
		}
		ImageScale is = new ImageScale();
		return is.doScale(srcImage, (int) (srcWidth * wScale), (int) (srcHeight * hScale));
	}

	public static void scaleFixedImageFile(String srcFile, String destFile, int width, int height) throws IOException {
		scaleFixedImageFile(srcFile, destFile, width, height, true);
	}

	/**
	 * ��ͼ���ļ�srcFile�������̶���С��д��destFile
	 * 
	 * @param srcFile
	 * @param destFile
	 * @param width
	 * @param height
	 * @param keepRate
	 * @throws IOException
	 */
	public static void scaleFixedImageFile(String srcFile, String destFile, int width, int height, boolean keepRate)
			throws IOException {
		try {
			if (srcFile.toLowerCase().endsWith(".gif")) {// Ҫ�ر���JDK���ܴ���͸��GIF
				OutputStream os = new FileOutputStream(destFile);
				try {
					GifUtil.resizeByRate(srcFile, os, width, height, keepRate);
				} catch (Exception e) {
					e.printStackTrace();
				}
				os.close();
			} else {
				BufferedImage image = readImage(srcFile);
				BufferedImage newImage = scaleFixed(image, width, height, keepRate);
				writeImageFile(destFile, newImage);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static BufferedImage readImage(String srcFile) throws IOException {
		BufferedImage image = null;
		if (srcFile.toLowerCase().endsWith(".bmp")) {
			image = BmpUtil.read(srcFile);
		} else {
			image = ImageIO.read(new File(srcFile));
		}
		return image;
	}

	/**
	 * ��ͼ���ļ�srcFile���ź�д��destFile������ͼƬ�������䣬��������width,�߲�����height
	 * 
	 * @param fileName
	 * @param width
	 * @param height
	 * @throws IOException
	 */
	public static void scaleRateImageFile(String srcFile, String destFile, int width, int height) throws IOException {
		scaleFixedImageFile(srcFile, destFile, width, height, true);
	}

	/**
	 * ��������ͼ���ļ�srcFile���ź�д��destFile
	 * 
	 * @param fileName
	 * @param width
	 * @param height
	 * @throws IOException
	 */
	public static void scaleRateImageFile(String srcFile, String destFile, double rate) throws IOException {
		try {
			if (srcFile.toLowerCase().endsWith(".gif")) {// Ҫ�ر���JDK���ܴ���͸��GIF
				OutputStream os = new FileOutputStream(destFile);
				try {
					GifUtil.resizeByRate(srcFile, os, rate, rate);
				} catch (Exception e) {
					e.printStackTrace();
				}
				os.close();
			} else {
				BufferedImage image = readImage(srcFile);
				BufferedImage newImage = scaleRate(image, rate);
				writeImageFile(destFile, newImage);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ͼ���ļ�srcFile�ҶȻ���д��destFile
	 * 
	 * @param fileName
	 * @throws IOException
	 */
	public static void grayImageFile(String srcFile, String destFile) throws IOException {
		writeImageFile(destFile, gray(ImageIO.read(new File(srcFile))));
	}

	public static void writeImageFile(String fileName, BufferedImage image) throws IOException {
		FileOutputStream fos = new FileOutputStream(fileName);
		if (fileName.toLowerCase().endsWith(".gif")) {
			throw new RuntimeException("����δ֪���󣬱���ͨ��GifUtilдGif�ļ�!");// ����ִ�е��˴�
		}
		if (fileName.toLowerCase().endsWith(".png")) {
			ImageIO.write(image, "png", fos);
		}
		if (fileName.toLowerCase().endsWith(".jpg") || fileName.toLowerCase().endsWith(".jpeg")) {
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fos);
			JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(image);
			param.setQuality(1.0f, false);
			encoder.encode(image);
		}
		fos.flush();
		fos.close();
	}

	public static Dimension getDimension(String fileName) throws IOException {
		File f = new File(fileName);
		return getDimension(f);
	}

	public static Dimension getDimension(File f) throws IOException {
		BufferedImage image = readImage(f.getAbsolutePath());
		if (image == null) {
			return new Dimension(0, 0);
		} else {
			return new Dimension(image.getWidth(), image.getHeight());
		}

	}

	/**
	 * ��ͼƬӡˢ��ͼƬ��
	 * 
	 * @param targetImg
	 *            Ŀ���ļ�
	 * @param pressImg
	 *            ˮӡ�ļ�
	 * @param position
	 *            ˮӡλ�� ��1-9��ʾ��0�������
	 */
	public final static void pressImage(String targetImg, String pressImg, int position) {
		try {
			// Ŀ���ļ�
			File file = new File(targetImg);
			Image src = ImageIO.read(file);
			int wideth = src.getWidth(null);
			int height = src.getHeight(null);
			if (wideth <= 300 && height <= 300) {
				return;
			}
			BufferedImage image = new BufferedImage(wideth, height, BufferedImage.TYPE_INT_RGB);
			Graphics g = image.createGraphics();
			g.drawImage(src, 0, 0, wideth, height, null);

			// ˮӡ�ļ�
			File file_press = new File(pressImg);
			if (!file_press.exists()) {
				LogUtil.warn("ˮӡͼƬ�����ڣ�" + pressImg);
				return;
			}
			Image src_press = ImageIO.read(file_press);
			int wideth_press = src_press.getWidth(null);
			int height_press = src_press.getHeight(null);
			// ����positionλ��������x��y���꣬��֤ͼƬˮӡȫ��ͼƬ�ڣ����Ҳ�����ͼƬ�ı�Ե
			int x = 0, y = 0;
			int bianju = 20;
			int[][][] positions = new int[][][] {
					{ { bianju, bianju }, { (wideth - wideth_press) / 2, bianju },
							{ wideth - wideth_press - bianju, bianju } },
					{ { bianju, (height - height_press) / 2 },
							{ (wideth - wideth_press) / 2, (height - height_press) / 2 },
							{ wideth - wideth_press - bianju, (height - height_press) / 2 } },
					{ { bianju, height - height_press - bianju },
							{ (wideth - wideth_press) / 2, height - height_press - bianju },
							{ wideth - wideth_press - bianju, height - height_press - bianju } } };
			if (position == 0) {
				position = NumberUtil.getRandomInt(9) + 1;
			}
			x = positions[(position - 1) / 3][(position - 1) % 3][0];
			y = positions[(position - 1) / 3][(position - 1) % 3][1];

			g.drawImage(src_press, x, y, wideth_press, height_press, null);
			// ˮӡ�ļ�����
			g.dispose();
			FileOutputStream out = new FileOutputStream(targetImg);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(image);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ͼƬӡˢ��ͼƬ�ϣ�Ĭ��λ��ΪͼƬ���½�,ͼƬ�ֱ���С��300*300�Ĳ���ˮӡ
	 * 
	 * @param targetImg
	 *            Ŀ���ļ�
	 * @param pressImg
	 *            ˮӡ�ļ�
	 */
	public final static void pressImage(String targetImg, String pressImg) {
		pressImage(targetImg, pressImg, 9);
	}

	public static void pressText(String targetImg, String pressText, int color, int fontSize, int position) {
		pressText(targetImg, pressText, "����", Font.BOLD, color, fontSize, position);
	}

	/**
	 * ��ӡ����ˮӡͼƬ
	 * 
	 * @param targetImg
	 *            Ŀ��ͼƬ
	 * @param pressText
	 *            ����
	 * @param fontName
	 *            ������
	 * @param fontStyle
	 *            ������ʽ
	 * @param color
	 *            ������ɫ
	 * @param fontSize
	 *            �����С
	 * @param position
	 *            ˮӡλ�� ��1-9��ʾ��0�������
	 */

	public static void pressText(String targetImg, String pressText, String fontName, int fontStyle, int color,
			int fontSize, int position) {
		try {
			File _file = new File(targetImg);
			Image src = ImageIO.read(_file);
			int wideth = src.getWidth(null);
			int height = src.getHeight(null);
			if (wideth <= 300 && height <= 300) {
				return;
			}
			BufferedImage image = new BufferedImage(wideth, height, BufferedImage.TYPE_INT_RGB);
			Graphics g = image.createGraphics();
			g.drawImage(src, 0, 0, wideth, height, null);
			g.setColor(new Color(color));
			g.setFont(new Font(fontName, fontStyle, fontSize));
			// ����positionλ��������x��y���꣬��֤����ˮӡȫ��ͼƬ�ڣ����Ҳ�����ͼƬ�ı�Ե
			int x, y;
			if (position == 0) {
				x = NumberUtil.getRandomInt(wideth);
				x = x < fontSize * 2 ? fontSize * 2 : x;
				y = NumberUtil.getRandomInt(height);
				y = y < fontSize * 2 / 2 ? fontSize * 2 : y;
			} else {
				x = wideth * ((int) ((position - 1) % 3)) / 3 + fontSize * 2;
				y = height * (((int) ((position - 1) / 3))) / 3 + fontSize * 2;
			}
			if (x > wideth - fontSize * pressText.length() * 4 / 3) {
				x = wideth - fontSize * pressText.length() * 4 / 3;
			}
			if (y > height - fontSize) {
				y = height - fontSize;
			}

			g.drawString(pressText, x, y);
			g.dispose();
			FileOutputStream out = new FileOutputStream(targetImg);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(image);
			out.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void transform(File src, File dest) {
		transform(src, dest, 1600);
	}

	public static void transform(File src, File dest, int nw) {
		try {
			AffineTransform transform = new AffineTransform();
			BufferedImage bis = ImageIO.read(src);
			int w = bis.getWidth();
			int h = bis.getHeight();
			int nh = (nw * h) / w;
			double sx = (double) nw / w;
			double sy = (double) nh / h;
			transform.setToScale(sx, sy);
			AffineTransformOp ato = new AffineTransformOp(transform, null);
			BufferedImage bid = new BufferedImage(nw, nh, BufferedImage.TYPE_3BYTE_BGR);
			ato.filter(bis, bid);
			ImageIO.write(bid, "jpg", dest);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
