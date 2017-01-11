package com.nswt.cms.document;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import com.nswt.cms.datachannel.Deploy;
import com.nswt.cms.pub.CatalogUtil;
import com.nswt.cms.site.Catalog;
import com.nswt.framework.Config;
import com.nswt.framework.User;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.NumberUtil;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.Application;
import com.nswt.platform.pub.ImageUtilEx;
import com.nswt.platform.pub.NoUtil;
import com.nswt.platform.pub.OrderUtil;
import com.nswt.schema.ZCCatalogSchema;
import com.nswt.schema.ZCImageSchema;

public class AutoUpload {
	public static ZCImageSchema dealImage(String path, String filename, Transaction trans) {
		String autoSaveLib = Config.getValue("AutoSaveImageLib");
		if (StringUtil.isEmpty(autoSaveLib)) {
			autoSaveLib = "Ĭ��ͼƬ";
		}
		String catalogID = new QueryBuilder("select ID from ZCCatalog where type=" + Catalog.IMAGELIB
				+ " and Name =?  and siteid=?", autoSaveLib, Application.getCurrentSiteID()).executeString();

		if (StringUtil.isEmpty(catalogID)) {
			catalogID = new QueryBuilder("select ID from ZCCatalog where type=" + Catalog.IMAGELIB + " and siteid=?",
					Application.getCurrentSiteID()).executeString();
		}

		boolean uploadFlag = true;
		String absolutePath = path +"defaultTemp/"+ filename;
		String ext = filename.substring(filename.lastIndexOf(".") + 1);
		filename = filename.substring(0, filename.lastIndexOf("."));
		long imageID = NoUtil.getMaxID("DocID");
		int random = NumberUtil.getRandomInt(10000);
		String newFileName = imageID + "" + random + "." + ext;
		new File(absolutePath).renameTo(new File(path +CatalogUtil.getPath(catalogID)+ newFileName));// ֱ��������

		ZCCatalogSchema catalog = CatalogUtil.getSchema(catalogID);

		ZCImageSchema image = new ZCImageSchema();
		image.setID(imageID);
		image.setCatalogID(catalogID);
		image.setCatalogInnerCode(catalog.getInnerCode());
		image.setName(filename);
		image.setOldName(filename);
		image.setSiteID(Application.getCurrentSiteID());
		image.setPath("upload/Image/" + CatalogUtil.getPath(catalogID));
		image.setFileName(newFileName);
		image.setSrcFileName(newFileName);
		image.setSuffix(ext);
		image.setCount(0);
		image.setWidth(0);
		image.setHeight(0);
		image.setHitCount(0);
		image.setStickTime(0);
		image.setAuthor("articleEditor");
		image.setSystem("CMS");
		image.setAddTime(new Date());
		image.setAddUser(User.getUserName());
		image.setOrderFlag(OrderUtil.getDefaultOrder());
		image.setStatus(Article.STATUS_TOPUBLISH);
		try {
			ImageUtilEx.afterUploadImage(image, path+CatalogUtil.getPath(catalogID));
			ArrayList imageList = ImageUtilEx.afterUploadImage(image, path+CatalogUtil.getPath(catalogID));
			Deploy d = new Deploy();
			d.addJobs(Application.getCurrentSiteID(), imageList, Deploy.OPERATION_COPY);
			uploadFlag = true;
		} catch (Throwable e) {
			e.printStackTrace();
			uploadFlag = false;
		}
		if (uploadFlag) {
			trans.add(image, Transaction.INSERT);
			return image;
		} else {
			return new ZCImageSchema();
		}
	}
}
