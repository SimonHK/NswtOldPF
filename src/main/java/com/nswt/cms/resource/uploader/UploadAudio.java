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
import com.nswt.framework.controls.UploadStatus;
import com.nswt.platform.Application;
import com.nswt.platform.pub.NoUtil;
import com.nswt.platform.pub.OrderUtil;
import com.nswt.schema.ZCAudioSchema;
import com.nswt.schema.ZCCatalogSchema;

public class UploadAudio {
	
	public static void repeatUpload(Mapx files, Mapx fields) throws Exception {
		ZCAudioSchema audio = new ZCAudioSchema();
		audio.setID(fields.getString("RepeatID"));
		if (!audio.fill()) {
			UploadStatus.setTask(fields.getString("taskID"), fields.getString("RepeatID"), fields.getString("FileType"), "empty", "上传失败，未找到文件,完成");
			return;
		}
		Iterator it = files.keySet().iterator();
		ArrayList fileList = new ArrayList(); // 存储处理过的文件，添加到分发队列中
		Transaction trans = new Transaction();
		while (it.hasNext()) {
			FileItem uplFile = (FileItem) files.get(it.next());
			UploadStatus.setTask(fields.getString("taskID"), fields.getString("RepeatID"), fields.getString("FileType"), fields.getString("PathData"), "处理中");
			String AbsolutePath = fields.getString("AbsolutePath");
			uplFile.write(new File(AbsolutePath + audio.getSrcFileName()));
			audio.setFileSize(FileUtils.byteCountToDisplaySize(uplFile.getSize()));
			fileList.add(AbsolutePath + audio.getSrcFileName());
		}
		audio.setModifyTime(new Date());
		audio.setModifyUser(User.getUserName());
		audio.setStatus(Article.STATUS_EDITING);
		trans.add(audio, Transaction.UPDATE);
		
		// 分发任务
		Deploy d = new Deploy();
		trans.add(d.getJobs(Application.getCurrentSiteID(), fileList, Deploy.OPERATION_COPY), Transaction.INSERT);
        trans.commit();
		UploadStatus.setTask(fields.getString("taskID"), fields.getString("RepeatID"), fields.getString("FileType"), fields.getString("PathData")+ audio.getSrcFileName(), "文件上传完成");
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

			long audioID = NoUtil.getMaxID("DocID");
			UploadStatus.setTask(fields.getString("taskID"), audioID+"", fields.getString("FileType"), fields.getString("PathData"), "处理中");
			int random = NumberUtil.getRandomInt(10000);
			String srcFileName = audioID + "" + random + "." + ext;
			uplFile.write(new File(AbsolutePath, srcFileName));

			ZCAudioSchema audio = new ZCAudioSchema();
			audio.setID(audioID);
			audio.setName(oldName.substring(0, oldName.lastIndexOf(".")));
			audio.setOldName(oldName.substring(0, oldName.lastIndexOf(".")));
			audio.setSiteID(fields.getString("SiteID"));
			audio.setCatalogID(CatalogID);
			audio.setCatalogInnerCode(catalog.getInnerCode());
			audio.setBranchInnerCode(User.getBranchInnerCode());
			audio.setPath(fields.getString("PathData"));
			audio.setFileName(srcFileName);
			audio.setSrcFileName(srcFileName);
			audio.setSuffix(ext);
			// audio.setInfo(fields.get(uplFile.getFieldName() + "Info").toString());
			audio.setTag(fields.getString("FileTag"));
			audio.setIsOriginal("Y");
			audio.setFileSize(FileUtils.byteCountToDisplaySize(uplFile.getSize()));
			audio.setSystem("CMS");
			audio.setOrderFlag(OrderUtil.getDefaultOrder());
			audio.setAddTime(new Date());
			audio.setAddUser("admin");
			audio.setModifyTime(new Date());
			audio.setModifyUser(User.getUserName());
			audio.setStatus(Article.STATUS_TOPUBLISH);
			trans.add(audio, Transaction.INSERT);
			trans.add(ColumnUtil.getValueFromRequest(Long.parseLong(CatalogID), audio.getID(), dc),Transaction.INSERT);
			fileList.add(AbsolutePath + srcFileName);
			UploadStatus.setTask(fields.getString("taskID"), audioID+"", fields.getString("FileType"), fields.getString("PathData")+srcFileName, "上传完成");
		}
		trans.add(new QueryBuilder("update zccatalog set isdirty = 1,total = (select count(*) from zcaudio where catalogID =?) where ID =?",catalog.getID(), catalog.getID()));
		// 分发任务
		Deploy d = new Deploy();
		trans.add(d.getJobs(Application.getCurrentSiteID(), fileList, Deploy.OPERATION_COPY), Transaction.INSERT);

		trans.commit();
	}
}
