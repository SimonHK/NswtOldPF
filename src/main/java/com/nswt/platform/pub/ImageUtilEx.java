package com.nswt.platform.pub;

import java.awt.Dimension;
import java.util.ArrayList;

import com.nswt.cms.pub.SiteUtil;
import com.nswt.cms.resource.ConfigImageLib;
import com.nswt.framework.Config;
import com.nswt.framework.utility.FileUtil;
import com.nswt.framework.utility.ImageUtil;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.platform.Application;
import com.nswt.schema.ZCImageSchema;

/**
 * @Author ������
 * @Date 2009-4-16
 * @Mail nswt@nswt.com.cn
 */
public class ImageUtilEx {

	public static ArrayList afterUploadImage(ZCImageSchema image, String absolutePath) throws Throwable {
		return afterUploadImage(image, absolutePath, null);
	}

	public static ArrayList afterUploadImage(ZCImageSchema image, String absolutePath, Mapx fields) throws Throwable {
		long t = System.currentTimeMillis();
		ArrayList imageList = new ArrayList();
		String imageFile = absolutePath + image.getSrcFileName();
		Dimension dim = null;
		try {
			dim = ImageUtil.getDimension(imageFile);
		} catch (Throwable ex) {
			throw ex;// ���벶�����׳�������ǰ̨���ܽ��յ���ʾ��Ϣ
		}
		image.setWidth((int) dim.getWidth());
		image.setHeight((int) dim.getHeight());

		// BMP��Ҫ�ı��׺Ϊjpg
		String destFile = image.getFileName();
		destFile = destFile.substring(0, destFile.lastIndexOf(".")) + ".jpg";
		image.setFileName(destFile);

		Mapx configFields = new Mapx();
		configFields.putAll(ConfigImageLib.getImageLibConfig(image.getSiteID()));
		if (fields != null) {
			configFields.putAll(fields);
		}

		// ����ͼ
		int count = Integer.parseInt(configFields.get("Count").toString());

		for (int i = 1; i <= count; i++) {
			if (configFields == null || "1".equals(configFields.get("HasAbbrImage" + i))) {
				String SizeType = (String) configFields.get("SizeType" + i);
				int Width = Integer.parseInt((String) configFields.get("Width" + i));
				int Height = Integer.parseInt((String) configFields.get("Height" + i));
				if ("1".equals(SizeType)) {
					Height = 0;
				} else if ("2".equals(SizeType)) {
					Width = 0;
				}
				String abbrImage = absolutePath + i + "_" + image.getFileName();
				// �̶���С
				if ("3".equals(SizeType)) {
					ImageUtil.scaleFixedImageFile(imageFile, abbrImage, Width, Height);
				} else {
					ImageUtil.scaleRateImageFile(imageFile, abbrImage, Width, Height);
				}
				image.setCount(count);

				if (i == 1) {
					// ���õ�һ������ͼ���Զ�����ϵͳ����ͼ��
					String thumbFileName = absolutePath + "s_" + image.getFileName();
					ImageUtil.scaleRateImageFile(absolutePath + "1_" + image.getFileName(), thumbFileName, 120, 120);
					imageList.add(thumbFileName);
				}

				if ("1".equals(configFields.get("HasWaterMark" + i))) {
					if ("Image".equals(configFields.get("WaterMarkType" + i))) {// ��ˮӡ�޸�2009.03.27
						ImageUtil.pressImage(
								abbrImage,
								Config.getContextRealPath() + Config.getValue("UploadDir") + "/"
										+ SiteUtil.getAlias(Application.getCurrentSiteID()) + "/"
										+ configFields.get("Image" + i),
								Integer.parseInt(configFields.get("Position" + i).toString()));
					} else {
						ImageUtil.pressText(abbrImage, (String) configFields.get("Text" + i),
								Integer.parseInt(configFields.get("FontColor" + i).toString()),
								Integer.parseInt(configFields.get("FontSize" + i).toString()),
								Integer.parseInt(configFields.get("Position" + i).toString()));
					}
				}

				imageList.add(abbrImage);
			}
		}
		// ���һ������ͼ��û�����ɣ��򿽱�һ��ԭͼ��Ϊ����ͼ
		if (image.getCount() == 0) {
			if (image.getSrcFileName().toLowerCase().endsWith(".bmp")) {
				//����ֱ�ӿ������������ţ�������ɫ���ܻ᲻����
				ImageUtil.scaleRateImageFile(absolutePath + image.getSrcFileName(),
						absolutePath + "1_" + image.getFileName(), (int) dim.width, (int) dim.getHeight());
			} else {
				FileUtil.copy(imageFile, absolutePath + "1_" + image.getFileName());
			}
			image.setCount(1);
			imageList.add(absolutePath + "1_" + image.getFileName());

			// ���õ�һ������ͼ���Զ�����ϵͳ����ͼ��
			String thumbFileName = absolutePath + "s_" + image.getFileName();
			ImageUtil.scaleRateImageFile(absolutePath + "1_" + image.getFileName(), thumbFileName, 120, 120);
			imageList.add(thumbFileName);
		}

		// ��ԭͼ��ˮӡ
		if (configFields == null || "1".equals(configFields.get("HasWaterMark"))) {
			if ("Image".equals(configFields.get("WaterMarkType"))) {// ��ˮӡ�޸�2009.03.27
				ImageUtil.pressImage(
						absolutePath + image.getSrcFileName(),
						Config.getContextRealPath() + Config.getValue("UploadDir") + "/"
								+ SiteUtil.getAlias(Application.getCurrentSiteID()) + "/" + configFields.get("Image"),
						Integer.parseInt(configFields.get("Position").toString()));
			} else {
				ImageUtil.pressText(absolutePath + image.getSrcFileName(), (String) configFields.get("Text"),
						Integer.parseInt(configFields.get("FontColor").toString()),
						Integer.parseInt(configFields.get("FontSize").toString()),
						Integer.parseInt(configFields.get("Position").toString()));
			}
		}

		// if (bakFlag) {
		// new File(imageFile).delete();
		// }
		LogUtil.info("�ϴ�ͼƬ�����ѣ�" + (System.currentTimeMillis() - t) + "����");
		return imageList;
	}
}
