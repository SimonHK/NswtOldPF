package com.nswt.cms.resource.uploader;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;

import com.nswt.cms.datachannel.Deploy;
import com.nswt.cms.dataservice.ColumnUtil;
import com.nswt.cms.document.Article;
import com.nswt.framework.User;
import com.nswt.framework.data.DataCollection;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.NumberUtil;
import com.nswt.framework.utility.StringUtil;
import com.nswt.framework.controls.UploadStatus;
import com.nswt.platform.Application;
import com.nswt.platform.pub.NoUtil;
import com.nswt.platform.pub.OrderUtil;
import com.nswt.schema.ZCAttachmentSchema;
import com.nswt.schema.ZCCatalogSchema;

public class UploadAttachment {

	public static void repeatUpload(Mapx files, Mapx fields) throws Exception {
		ZCAttachmentSchema attach = new ZCAttachmentSchema();
		attach.setID(fields.getString("RepeatID"));
		if (!attach.fill()) {
			UploadStatus.setTask(fields.getString("taskID"), fields.getString("RepeatID"),
					fields.getString("FileType"), "empty", "上传失败，未找到文件,完成");
			return;
		}
		ArrayList fileList = new ArrayList(); // 存储处理过的文件，添加到分发队列中
		Transaction trans = new Transaction();
		Iterator it = files.keySet().iterator();
		while (it.hasNext()) {
			FileItem uplFile = (FileItem) files.get(it.next());
			UploadStatus.setTask(fields.getString("taskID"), fields.getString("RepeatID"),
					fields.getString("FileType"), fields.getString("PathData"), "处理中");
			String AbsolutePath = fields.getString("AbsolutePath");
			uplFile.write(new File(AbsolutePath + attach.getSrcFileName()));
			attach.setFileSize(FileUtils.byteCountToDisplaySize(uplFile.getSize()));
		}
		attach.setModifyTime(new Date());
		attach.setModifyUser(User.getUserName());
		attach.setStatus(Article.STATUS_EDITING);
		trans.add(attach,Transaction.UPDATE);
		
		// 分发任务
		Deploy d = new Deploy();
		trans.add(d.getJobs(Application.getCurrentSiteID(), fileList, Deploy.OPERATION_COPY), Transaction.INSERT);
        trans.commit();
        
		UploadStatus.setTask(fields.getString("taskID"), fields.getString("RepeatID"), fields.getString("FileType"),
				fields.getString("PathData") + attach.getSrcFileName(), "文件上传完成");
	}

	public static void upload(Mapx files, Mapx fields) throws Exception {
		String CatalogID = fields.getString("CatalogID");
		String AbsolutePath = fields.getString("AbsolutePath");

		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(CatalogID);
		if (!catalog.fill()) {
			LogUtil.info("没有找到上传库");
		}

		// 支持上传多个文件
		ArrayList fileList = new ArrayList(); // 存储处理过的文件，添加到分发队列中
		Transaction trans = new Transaction();
		DataCollection dc = new DataCollection();
		dc.putAll(fields);
		// 存储文件信息到数据库中
		Iterator it = files.keySet().iterator();
		while (it.hasNext()) {
			FileItem uplFile = (FileItem) files.get(it.next());
			String fileNameLong = uplFile.getName();
			fileNameLong = fileNameLong.replaceAll("\\\\", "/");
			String oldName = fileNameLong.substring(fileNameLong.lastIndexOf("/") + 1); // 原文件名
			String ext = ZUploaderServlet.getExtension(oldName); // 后缀名

			long attachID = NoUtil.getMaxID("DocID");
			UploadStatus.setTask(fields.getString("taskID"), attachID + "", fields.getString("FileType"), fields
					.getString("PathData"), "处理中");
			int random = NumberUtil.getRandomInt(10000);
			String srcFileName = attachID + "" + random + "." + ext;
			uplFile.write(new File(AbsolutePath, srcFileName));

			fileList.add(AbsolutePath + srcFileName);
			if (StringUtil.isEmpty(fields.getString("FileName"))) {
				fields.put("FileName", oldName.substring(0, oldName.lastIndexOf(".")));
			}

			ZCAttachmentSchema attachment = new ZCAttachmentSchema();
			attachment.setID(attachID);
			attachment.setName(fields.getString("FileName"));
			attachment.setOldName(oldName.substring(0, oldName.lastIndexOf(".")));
			attachment.setSiteID(fields.getString("SiteID"));
			attachment.setInfo(fields.getString("Info"));
			attachment.setCatalogID(Long.parseLong(CatalogID));
			attachment.setCatalogInnerCode(catalog.getInnerCode());
			attachment.setBranchInnerCode(User.getBranchInnerCode());
			attachment.setPath(fields.getString("PathData"));
			attachment.setImagePath(fields.getString("ImagePath"));
			attachment.setFileName(srcFileName);
			attachment.setSrcFileName(srcFileName);
			attachment.setSuffix(ext);
			attachment.setIsLocked(fields.getString(uplFile.getFieldName() + "Locked") + "");
			if ("Y".equals(attachment.getIsLocked())) {
				attachment.setPassword(StringUtil.md5Hex(fields.getString(uplFile.getFieldName() + "Password")));
			}
			attachment.setSystem("CMS");
			attachment.setFileSize(FileUtils.byteCountToDisplaySize(uplFile.getSize()));
			attachment.setAddTime(new Date());
			attachment.setAddUser("admin");
			attachment.setOrderFlag(OrderUtil.getDefaultOrder());
			attachment.setModifyTime(new Date());
			attachment.setModifyUser(User.getUserName());
			attachment.setProp1("0");
			attachment.setStatus(Article.STATUS_TOPUBLISH);
			trans.add(attachment, Transaction.INSERT);
			trans.add(ColumnUtil.getValueFromRequest(Long.parseLong(CatalogID), attachment.getID(), dc),Transaction.INSERT);
			UploadStatus.setTask(fields.getString("taskID"), attachID + "", fields.getString("FileType"), fields
					.getString("PathData")
					+ srcFileName, "文件上传完成");
		}
		trans
				.add(new QueryBuilder(
						"update zccatalog set isdirty = 1,total = (select count(*) from zcattachment where catalogID =?) where ID =?",
						catalog.getID(), catalog.getID()));
		
		// 分发任务
		Deploy d = new Deploy();
		trans.add(d.getJobs(Application.getCurrentSiteID(), fileList, Deploy.OPERATION_COPY), Transaction.INSERT);

		trans.commit();
	}

}
