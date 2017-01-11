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
					fields.getString("FileType"), "empty", "�ϴ�ʧ�ܣ�δ�ҵ��ļ�,���");
			return;
		}
		ArrayList fileList = new ArrayList(); // �洢��������ļ�����ӵ��ַ�������
		Transaction trans = new Transaction();
		Iterator it = files.keySet().iterator();
		while (it.hasNext()) {
			FileItem uplFile = (FileItem) files.get(it.next());
			UploadStatus.setTask(fields.getString("taskID"), fields.getString("RepeatID"),
					fields.getString("FileType"), fields.getString("PathData"), "������");
			String AbsolutePath = fields.getString("AbsolutePath");
			uplFile.write(new File(AbsolutePath + attach.getSrcFileName()));
			attach.setFileSize(FileUtils.byteCountToDisplaySize(uplFile.getSize()));
		}
		attach.setModifyTime(new Date());
		attach.setModifyUser(User.getUserName());
		attach.setStatus(Article.STATUS_EDITING);
		trans.add(attach,Transaction.UPDATE);
		
		// �ַ�����
		Deploy d = new Deploy();
		trans.add(d.getJobs(Application.getCurrentSiteID(), fileList, Deploy.OPERATION_COPY), Transaction.INSERT);
        trans.commit();
        
		UploadStatus.setTask(fields.getString("taskID"), fields.getString("RepeatID"), fields.getString("FileType"),
				fields.getString("PathData") + attach.getSrcFileName(), "�ļ��ϴ����");
	}

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

			long attachID = NoUtil.getMaxID("DocID");
			UploadStatus.setTask(fields.getString("taskID"), attachID + "", fields.getString("FileType"), fields
					.getString("PathData"), "������");
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
					+ srcFileName, "�ļ��ϴ����");
		}
		trans
				.add(new QueryBuilder(
						"update zccatalog set isdirty = 1,total = (select count(*) from zcattachment where catalogID =?) where ID =?",
						catalog.getID(), catalog.getID()));
		
		// �ַ�����
		Deploy d = new Deploy();
		trans.add(d.getJobs(Application.getCurrentSiteID(), fileList, Deploy.OPERATION_COPY), Transaction.INSERT);

		trans.commit();
	}

}
