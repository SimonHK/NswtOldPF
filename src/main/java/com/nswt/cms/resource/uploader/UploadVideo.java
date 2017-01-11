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
import com.nswt.framework.utility.DateUtil;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.NumberUtil;
import com.nswt.framework.utility.StringUtil;
import com.nswt.framework.controls.UploadStatus;
import com.nswt.framework.utility.VideoUtil;
import com.nswt.platform.pub.NoUtil;
import com.nswt.platform.pub.OrderUtil;
import com.nswt.platform.pub.VideoUtilEx;
import com.nswt.schema.ZCCatalogSchema;
import com.nswt.schema.ZCVideoSchema;

public class UploadVideo {
	
	public static void upload(Mapx files, Mapx fields) throws Exception {
		String CatalogID = fields.getString("CatalogID");
		String AbsolutePath = fields.getString("AbsolutePath");
		
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(CatalogID);
		if (!catalog.fill()) {
			LogUtil.info("û���ҵ��ϴ���");
		}

		// ֧���ϴ�����ļ�
		ArrayList fileList = new ArrayList(); // �洢��������ļ�����ӵ��ַ�������
		Transaction trans = new Transaction();
		DataCollection dc = new DataCollection();
		dc.putAll(fields);
		// �洢�ļ���Ϣ�����ݿ���
		
		Iterator it = files.keySet().iterator();
		while (it.hasNext()) {
			FileItem uplFile = (FileItem) files.get(it.next());
			String fileNameLong = uplFile.getName();
			fileNameLong = fileNameLong.replaceAll("\\\\", "/");
			String oldName = fileNameLong.substring(fileNameLong.lastIndexOf("/") + 1); // ԭ�ļ���
			String ext = ZUploaderServlet.getExtension(oldName); // ��׺��

			long videoID = NoUtil.getMaxID("DocID");
			UploadStatus.setTask(fields.getString("taskID"), videoID+"", fields.getString("FileType"), fields.getString("PathData"), "������");
			int random = NumberUtil.getRandomInt(10000);
			String srcFileName = videoID + "" + random + "." + ext;
			uplFile.write(new File(AbsolutePath, srcFileName));

			if(StringUtil.isEmpty(fields.getString("FileName"))){
				fields.put("FileName",oldName.substring(0,oldName.lastIndexOf(".")));
			}
			
			ZCVideoSchema video = new ZCVideoSchema();
			video.setID(videoID);
			video.setName(fields.getString("FileName"));
			video.setOldName(oldName.substring(0, oldName.lastIndexOf(".")));
			video.setSiteID(fields.getString("SiteID"));
			video.setCatalogID(CatalogID);
			video.setCatalogInnerCode(catalog.getInnerCode());
			video.setBranchInnerCode(User.getBranchInnerCode());
			video.setPath(fields.getString("PathData"));
			if("flv".equalsIgnoreCase(ext)){
				video.setFileName(videoID + "" + random + ".flv");
				video.setImageName(videoID + "" + random + ".jpg");
			}else{
				int random1 = NumberUtil.getRandomInt(10000);
				video.setFileName(videoID + "" + random1 + ".flv");
				video.setImageName(videoID + "" + random1 + ".jpg");
			}
			video.setSrcFileName(srcFileName);
			video.setSuffix(ext);
			video.setTag(fields.getString("Tag"));
			video.setInfo(fields.getString("Info"));
			video.setIsOriginal(fields.getString("IsOriginal"));
			video.setFileSize(FileUtils.byteCountToDisplaySize(uplFile.getSize()));
			video.setHitCount(0);
			video.setStickTime(0);
			video.setSystem("CMS");
			video.setOrderFlag(OrderUtil.getDefaultOrder());
			video.setAddTime(new Date());
			video.setAddUser("admin");
			video.setModifyTime(new Date());
			video.setModifyUser(User.getUserName());
			video.setStatus(Article.STATUS_TOPUBLISH);
			String ProduceTime = (String) fields.get("ProduceTime");
			if (StringUtil.isNotEmpty(ProduceTime)) {
				video.setProduceTime(DateUtil.parse(ProduceTime));
			}
			String Integral = (String) fields.get("Integral");
			if (StringUtil.isNotEmpty(Integral)) {
				video.setIntegral(Integer.parseInt(Integral));
			}
			VideoUtil.videoid = videoID+"";
			VideoUtil.taskid=fields.getString("taskID");
			VideoUtil.srcPath=fields.getString("PathData")+srcFileName;
			if (!VideoUtilEx.afterUploadVideo(video, AbsolutePath, false)) {
				UploadStatus.setTask(fields.getString("taskID"), videoID+"", fields.getString("FileType"), fields.getString("PathData")+srcFileName, "��Ƶת������");
			}
			trans.add(video, Transaction.INSERT);
			trans.add(ColumnUtil.getValueFromRequest(Long.parseLong(CatalogID), video.getID(), dc),Transaction.INSERT);
			fileList.add(AbsolutePath+video.getFileName());
			fileList.add(AbsolutePath+video.getImageName());
			UploadStatus.setTask(fields.getString("taskID"), videoID+"", fields.getString("FileType"), fields.getString("PathData")+srcFileName, "��Ƶת�����");
		}
		trans.add(new QueryBuilder("update zccatalog set isdirty = 1,total = (select count(*) from zcvideo where catalogID =?) where ID =?",catalog.getID(), catalog.getID()));
		
		// �ַ�����
		Deploy d = new Deploy();
		trans.add(d.getJobs(Long.parseLong(fields.getString("SiteID")), fileList, Deploy.OPERATION_COPY), Transaction.INSERT);
		trans.commit();
	}
}
