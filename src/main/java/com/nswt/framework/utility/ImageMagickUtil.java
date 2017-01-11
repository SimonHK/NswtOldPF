package com.nswt.framework.utility;

import java.awt.Dimension;
import java.io.IOException;

import magick.CompositeOperator;
import magick.CompressionType;
import magick.DrawInfo;
import magick.ImageInfo;
import magick.MagickException;
import magick.MagickImage;
import magick.PixelPacket;
import magick.PreviewType;

/**
 * 
 * @author huanglei
 * @mail huanglei@nswt.com
 * @date 2008-5-7
 */

public class ImageMagickUtil {

	/**
	 * @param args
	 * @throws IOException
	 * @throws MagickException
	 */
	public static void main(String[] args) throws IOException, MagickException {
		scaleRateImageFile("G://cmyk.jpg", "g:\\cmyk1.jpg", 200, 33);
	}

	public static Dimension getDimension(String fileName) throws MagickException {
		System.setProperty("jmagick.systemclassloader", "no");
//		System.out.println(System.getProperty("java.library.path"));
		ImageInfo info = null;
		MagickImage fromImage = null;
		Dimension dim = null;
		try {
			info = new ImageInfo(fileName);
			fromImage = new MagickImage(info);
			dim = fromImage.getDimension();
		} finally {
			if (fromImage != null) {
				fromImage.destroyImages();
			}
		}
		return dim;
	}

	public static void scaleRateImageFile(String fromFileName, String toFileName, int toWidth, int toHeight)
			throws MagickException {
		System.setProperty("jmagick.systemclassloader", "no");
//		System.out.println(System.getProperty("java.library.path"));
		ImageInfo info = null;
		MagickImage fromImage = null;
		try {
			info = new ImageInfo(fromFileName);

			fromImage = new MagickImage(info);
			Dimension dim = fromImage.getDimension();
			double w = dim.getWidth();
			double h = dim.getHeight();
			if (w < toWidth && h < toHeight) {
				FileUtil.copy(fromFileName, toFileName);
				return;
			}
			if (toWidth == 0) {
				if (h <= toHeight) {
					FileUtil.copy(fromFileName, toFileName);
				} else {
					scaleRateImageFile(fromFileName, toFileName, toHeight / h);
				}
				return;
			} else if (toHeight == 0) {
				if (w <= toWidth) {
					FileUtil.copy(fromFileName, toFileName);
				} else {
					scaleRateImageFile(fromFileName, toFileName, toWidth / w);
				}
				return;
			}
			if (toWidth / w > toHeight / h) {
				scaleRateImageFile(fromFileName, toFileName, toHeight / h);
			} else {
				scaleRateImageFile(fromFileName, toFileName, toWidth / w);
			}
		} finally {
			if (fromImage != null) {
				fromImage.destroyImages();
			}
		}
	}

	public static void scaleRateImageFile(String fromFileName, String toFileName, double rate) throws MagickException {
		LogUtil.info("ImageMagickUtil������������ͼ--" + toFileName);
		System.setProperty("jmagick.systemclassloader", "no");
		MagickImage fromImage = null;
		try {
			ImageInfo info = new ImageInfo(fromFileName);
			fromImage = new MagickImage(info);
			Dimension dim = fromImage.getDimension();
			double w = dim.getWidth();
			double h = dim.getHeight();
			MagickImage toImage = fromImage.scaleImage((int) (w * rate), (int) (h * rate));// ���Ų���
			toImage.setFileName(toFileName);// ����������ļ���
			toImage.writeImage(new ImageInfo(fromImage.getFileName())); // ����
		} finally {
			if (fromImage != null) {
				fromImage.destroyImages();
			}
		}
		LogUtil.info("��������ͼ���");
	}

	public static void scaleFixedImageFile(String fromFileName, String toFileName, int toWidth, int toHeight)
			throws MagickException {
		LogUtil.info("ImageMagickUtil������������ͼ--" + toFileName);
		System.setProperty("jmagick.systemclassloader", "no");// ���ûʲô��˵�ģ��հ������
		MagickImage fromImage = null;
		MagickImage toImage = null;
		try {
			ImageInfo info = new ImageInfo(fromFileName);
			fromImage = new MagickImage(info);
			toImage = fromImage.scaleImage(toWidth, toHeight);// ���Ų���
			toImage.setFileName(toFileName);// ����������ļ���
			toImage.writeImage(info); // ����
		} finally {
			if (fromImage != null) {
				fromImage.destroyImages();
			}
			if (toImage != null) {
				toImage.destroyImages();
			}
		}
		LogUtil.info("��������ͼ���");
	}

	public static void pressText(String filePath, String pressText, int color, int fontSize, int position) {
		LogUtil.info("ImageMagickUtil���ڴ�����ˮӡ--" + filePath);
		System.setProperty("jmagick.systemclassloader", "no");
		MagickImage image = null;
		try {
			ImageInfo info = new ImageInfo(filePath);

			if (filePath.toUpperCase().endsWith("JPG") || filePath.toUpperCase().endsWith("JPEG")) {
				info.setCompression(CompressionType.JPEGCompression); // ѹ�����ΪJPEG��ʽ
				info.setPreviewType(PreviewType.JPEGPreview); // Ԥ����ʽΪJPEG��ʽ
				info.setQuality(95);
			}

			image = new MagickImage(info);
			Dimension dim = image.getDimension();
			int wideth = (int) dim.getWidth();
			int height = (int) dim.getHeight();
			if (wideth <= 300 && height <= 300) {
				return;
			}
			DrawInfo drawInfo = new DrawInfo(info);

			drawInfo.setFill(PixelPacket.queryColorDatabase("white"));
			// drawInfo.setUnderColor(PixelPacket.queryColorDatabase("white"));
			drawInfo.setOpacity(0);
			drawInfo.setPointsize(100);

			// drawInfo.setFont("Arial"); // Ӣ��ʹ�ô�����Ҳ��

			// // ע�������������������⣬���������вſ�������ʾ
			String fontPath = "C:/WINDOWS/Fonts/SIMSUN.TTC";
			// String fontPath = "SIMSUN.TTC";
			drawInfo.setFont(fontPath);
			drawInfo.setTextAntialias(true);

			// Step 3: Writing The Text
			drawInfo.setText(pressText);
			drawInfo.setGeometry("+1500+1000");

			// Step 4: Annotating
			image.annotateImage(drawInfo);

			// Step 5: Writing the new file
			image.setFileName(filePath);
			image.writeImage(info);
			image.destroyImages();
			image = null;

		} catch (MagickException e) {
			e.printStackTrace();
		} finally {
			if (image != null) {
				image.destroyImages();
			}
		}
		System.out.println("���");
	}

	public final static void pressImage(String targetImg, String pressImg, int position) {
		LogUtil.info("ImageMagickUtil���ڴ�ͼƬˮӡ--" + targetImg);
		System.setProperty("jmagick.systemclassloader", "no");
		MagickImage image = null;
		MagickImage press = null;
		try {
			ImageInfo imageInfo = new ImageInfo(targetImg);
			image = new MagickImage(imageInfo);
			Dimension dim = image.getDimension();
			int wideth = (int) dim.getWidth();
			int height = (int) dim.getHeight();
			if (wideth <= 300 && height <= 300) {
				return;
			}
			press = new MagickImage(new ImageInfo(pressImg));
			dim = press.getDimension();
			int wideth_press = (int) dim.getWidth();
			int height_press = (int) dim.getHeight();
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

			image.compositeImage(CompositeOperator.AtopCompositeOp, press, x, y);
			image.setFileName(targetImg);
			image.writeImage(imageInfo);
		} catch (MagickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (image != null) {
				image.destroyImages();
			}
			if (press != null) {
				press.destroyImages();
			}
		}
		System.out.println("���");
	}
}
