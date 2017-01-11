package com.nswt.cms.api;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import com.nswt.cms.datachannel.Deploy;
import com.nswt.cms.pub.CatalogUtil;
import com.nswt.cms.pub.SiteUtil;
import com.nswt.cms.site.Catalog;
import com.nswt.framework.Config;
import com.nswt.framework.User;
import com.nswt.framework.User.UserData;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.utility.FileUtil;
import com.nswt.framework.utility.NumberUtil;
import com.nswt.platform.pub.ImageUtilEx;
import com.nswt.platform.pub.NoUtil;
import com.nswt.schema.ZCImageSchema;

/**
 * 
 * @author huanglei
 * @mail huanglei@nswt.com
 * @date 2008-6-21
 */

public class ImageAPI implements APIInterface {

	private boolean isCreateSchema = false; // 标志是否把schema的值都值上了

	private String ImageHtml;

	private ZCImageSchema image;

	public static void main(String[] args) {
		UserData u = new UserData();
		User.setCurrent(u);
		User.setUserName("admin");
		User.setBranchInnerCode("86");
		// Transaction trans = new Transaction();

		ImageAPI image = new ImageAPI();
		image.setFileName("i:/1.jpg"); // 设置上传的图片绝对路径
		image.setCatalogID(5725); // 设置上传的图片专辑
		image.insert();

		image = new ImageAPI();
		image.setFileName("i:/2.jpg"); // 设置上传的图片绝对路径
		image.setCatalogID(6038); // 设置上传的图片专辑
		image.insert();
	}

	public ImageAPI() {
		this.image = new ZCImageSchema();
	}

	public ImageAPI(ZCImageSchema image) {
		this.image = image;
	}

	public void setFileName(String fileName) {
		image.setFileName(fileName);
	}

	public void setCatalogID(long catalogID) {
		image.setCatalogID(catalogID);
	}

	/**
	 * 返回一个图片路径，给文章中调用
	 * 
	 * @return
	 */
	public String getImageHtml() {
		if (!isCreateSchema) {
			createSchema();
		}
		return ImageHtml;
	}

	private void createSchema() {
		if (!isCreateSchema) {
			String fileName = image.getFileName();
			fileName = fileName.replaceAll("\\\\", "/");
			String name = fileName.substring(fileName.lastIndexOf("/") + 1, fileName.lastIndexOf("."));
			image.setFileName(name);
			String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);

			long imageID = NoUtil.getMaxID("DocID");
			image.setID(imageID);
			image.setName(name);
			image.setOldName(image.getName());
			if (image.getCatalogID() == 0) {
				System.out.println("必须CatalogID不能为空");
				return;
			}
			image.setSiteID(CatalogUtil.getSiteID(image.getCatalogID()));
			String innerCode = CatalogUtil.getInnerCode(image.getCatalogID());
			image.setCatalogInnerCode(innerCode);
			image.setPath("upload/Image/" + CatalogUtil.getPath(image.getCatalogID()));
			image.setFileName(imageID + "" + NumberUtil.getRandomInt(10000) + ".jpg");
			image.setSrcFileName(imageID + "" + NumberUtil.getRandomInt(10000) + ".jpg");
			image.setSuffix(suffix);
			image.setWidth(0);
			image.setHeight(0);
			image.setCount(2);
			image.setAddTime(new Date());
			image.setAddUser("admin");
			ImageHtml = ".." + Config.getValue("UploadDir") + SiteUtil.getAlias(image.getSiteID()) + "/"
					+ image.getPath() + image.getSrcFileName();
			isCreateSchema = true;
			dealImageFile(fileName);
		}
	}

	public long insert() {
		Transaction trans = new Transaction();
		if (insert(trans) > 0) {
			if (trans.commit()) {
				return 1;
			} else
				return -1;
		} else {
			return -1;
		}
	}

	public long insert(Transaction trans) {
		if (!isCreateSchema) {
			createSchema();
		}
		trans.add(image, Transaction.INSERT);
		trans
				.add(new QueryBuilder(
						"update zccatalog set total = (select count(*) from zcimage where catalogID = zccatalog.ID) where type=? and id =?",
						Catalog.IMAGELIB, image.getID()));
		return 1;
	}

	private boolean dealImageFile(String srcFileName) {
		String dir = Config.getContextRealPath() + Config.getValue("UploadDir") + SiteUtil.getAlias(image.getSiteID())
				+ "/" + image.getPath();
		new File(dir).mkdirs();
		if (FileUtil.copy(srcFileName, dir + image.getSrcFileName())) {
			try {
				ArrayList imageList = ImageUtilEx.afterUploadImage(image, dir);
				Deploy d = new Deploy();
				d.addJobs(image.getSiteID(), imageList);
				return true;
			} catch (Throwable e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}

	public boolean delete() {
		return false;
	}

	public boolean setSchema(Schema schema) {
		this.image = (ZCImageSchema) schema;
		return false;
	}

	public boolean update() {
		return false;
	}
}
