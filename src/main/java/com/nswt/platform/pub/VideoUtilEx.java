package com.nswt.platform.pub;

import com.nswt.framework.utility.ImageUtil;
import com.nswt.framework.utility.VideoUtil;
import com.nswt.schema.ZCVideoSchema;

/**
 * @Author ÍõÓý´º
 * @Date 2009-4-16
 * @Mail nswt@nswt.com.cn
 */
public class VideoUtilEx {
	public static boolean afterUploadVideo(ZCVideoSchema video, String AbsolutePath, boolean hasImage) throws NumberFormatException, Exception {
		int[] WidthHeight = VideoUtil.getWidthHeight(AbsolutePath + video.getSrcFileName());
		video.setWidth(WidthHeight[0]);
		video.setHeight(WidthHeight[1]);
		if (!"flv".equalsIgnoreCase(video.getSuffix())) {
			VideoUtil.convert2Flv(AbsolutePath + video.getSrcFileName(), AbsolutePath + video.getFileName());
		}

		video.setDuration(VideoUtil.getDuration(AbsolutePath + video.getFileName()));
		video.setCount(1);

		if (hasImage) {
			ImageUtil.scaleRateImageFile(AbsolutePath + video.getImageName(), AbsolutePath + video.getImageName(), 240, 240);
		} else {
			VideoUtil.captureDefaultImage(AbsolutePath + video.getFileName(), AbsolutePath + video.getImageName(),(int)video.getDuration());
		}
		return true;
	}

}
