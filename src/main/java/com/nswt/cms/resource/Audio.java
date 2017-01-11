package com.nswt.cms.resource;

import java.io.File;
import java.util.Date;

import com.nswt.cms.datachannel.Publisher;
import com.nswt.cms.dataservice.ColumnUtil;
import com.nswt.cms.pub.CatalogUtil;
import com.nswt.cms.pub.SiteUtil;
import com.nswt.framework.Config;
import com.nswt.framework.Page;
import com.nswt.framework.User;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.FileUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.Application;
import com.nswt.platform.UserLog;
import com.nswt.platform.pub.NoUtil;
import com.nswt.schema.ZCAudioRelaSchema;
import com.nswt.schema.ZCAudioRelaSet;
import com.nswt.schema.ZCAudioSchema;
import com.nswt.schema.ZCAudioSet;
import com.nswt.schema.ZCCatalogSchema;

/**
 * 
 * @author huanglei
 * @mail huanglei@nswt.com
 * @date 2008-3-28
 */

public class Audio extends Page {

	public static Mapx initEditDialog(Mapx params) {
		ZCAudioSchema Audio = new ZCAudioSchema();
		Audio.setID(params.getString("ID"));
		Audio.fill();
		params.putAll(Audio.toMapx());
		params.put("CustomColumn", ColumnUtil.getHtml(ColumnUtil.RELA_TYPE_CATALOG_COLUMN, Audio.getCatalogID() + "",
				ColumnUtil.RELA_TYPE_DOCID, Audio.getID() + ""));
		return params;
	}

	public static Mapx initUploadDialog(Mapx params) {
		String CatalogID = params.getString("CatalogID");
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(CatalogID);
		catalog.fill();
		params.putAll(catalog.toMapx());
		params.put("allowType", Config.getValue("AllowAudioExt"));
		return params;
	}

	public void dialogEdit() {
		String AudioID = $V("ID");
		Transaction trans = new Transaction();
		ZCAudioSchema Audio = new ZCAudioSchema();
		Audio.setID(AudioID);
		Audio.fill();
		Audio.setValue(Request);
		DataTable columnDT = ColumnUtil.getColumnValue(ColumnUtil.RELA_TYPE_DOCID, Audio.getID());
		if (columnDT.getRowCount() > 0) {
			trans.add(new QueryBuilder("delete from zdcolumnvalue where relatype=? and relaid = ?",
					ColumnUtil.RELA_TYPE_DOCID, Audio.getID()));
		}
		trans.add(ColumnUtil.getValueFromRequest(Audio.getCatalogID(), Audio.getID(), Request), Transaction.INSERT);
		trans.add(Audio, Transaction.UPDATE);
		if (trans.commit()) {
			this.Response.setLogInfo(1, "�༭�ɹ���");
		} else {
			this.Response.setLogInfo(1, "�༭ʧ�ܣ�");
		}
	}

	public void copy() {
		long catalogID = Long.parseLong($V("CatalogID"));
		String Alias = Config.getValue("UploadDir") + "/" + SiteUtil.getAlias(Application.getCurrentSiteID()) + "/";
		String catalogPath = CatalogUtil.getPath(catalogID);
		String InnerCode = new QueryBuilder("select InnerCode from zccatalog where ID = ?", catalogID).executeString();
		String newPath = "Audio/" + catalogPath;
		File dir = new File(Config.getContextRealPath() + Alias + newPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String IDs = $V("IDs");
		if (!StringUtil.checkID(IDs)) {
			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_COPYAUDIO, "������Ƶʧ��", Request.getClientIP());
			Response.setStatus(0);
			Response.setMessage("����IDʱ��������!");
			return;
		}
		StringBuffer logs = new StringBuffer("������Ƶ:");
		ZCAudioSet AudioSet = new ZCAudioSchema().query(new QueryBuilder(" where ID in (" + IDs + ")"));
		boolean flag = true;
		for (int i = 0; i < AudioSet.size(); i++) {
			ZCAudioSchema Audio = AudioSet.get(i);
			String oldPath = Alias + Audio.getPath();
			String oldFileName = oldPath + Audio.getFileName();
			Audio.setID(NoUtil.getMaxID("DocID"));
			Audio.setCatalogID(catalogID);
			Audio.setCatalogInnerCode(InnerCode);
			Audio.setPath(newPath);
			Audio.setFileName(Audio.getID() + Audio.getFileName().substring(Audio.getFileName().lastIndexOf(".")));
			Audio.setAddTime(new Date());
			Audio.setAddUser(User.getUserName());
			File directory = new File(Config.getContextRealPath() + Alias + newPath);
			if (!directory.exists()) {
				directory.mkdirs();
			}
			if (!FileUtil.copy(Config.getContextRealPath() + oldFileName, Config.getContextRealPath() + Alias + newPath
					+ Audio.getFileName())) {
				flag = false;
				break;
			}
			logs.append(Audio.getName() + ",");
		}
		if (flag && AudioSet.insert()) {
			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_EDITAUDIO, logs + "�ɹ�", Request.getClientIP());
			this.Response.setLogInfo(1, "������Ƶ�ɹ�");
		} else {
			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_EDITAUDIO, logs + "ʧ��", Request.getClientIP());
			this.Response.setLogInfo(0, "������Ƶʧ��");
		}
	}

	public void transfer() {
		long catalogID = Long.parseLong($V("CatalogID"));
		String IDs = $V("IDs");
		if (!StringUtil.checkID(IDs)) {
			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_MOVEAUDIO, "��Ƶת��ʧ��", Request.getClientIP());
			Response.setStatus(0);
			Response.setMessage("����IDʱ��������!");
			return;
		}
		Transaction trans = new Transaction();
		trans.add(new QueryBuilder("update ZCAudio set catalogid=? ,CatalogInnerCode=? where ID in (" + IDs + ")",
				catalogID, CatalogUtil.getInnerCode(catalogID)));

		StringBuffer logs = new StringBuffer("ת����Ƶ:");
		DataTable dt = new QueryBuilder("select Name from ZCAudio where id in(" + IDs + ")").executeDataTable();
		for (int i = 0; i < dt.getRowCount(); i++) {
			logs.append(dt.get(i, "Name") + ",");
		}
		if (trans.commit()) {
			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_MOVEAUDIO, logs + "�ɹ�", Request.getClientIP());
			Response.setLogInfo(1, "ת�Ƴɹ�");
		} else {
			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_MOVEAUDIO, logs + "ʧ��", Request.getClientIP());
			Response.setLogInfo(0, "ת��ʧ��");
		}
	}

	public void del() {
		String IDs = $V("IDs");
		if (!StringUtil.checkID(IDs)) {
			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_DELAUDIO, "ɾ����Ƶʧ��", Request.getClientIP());
			Response.setStatus(0);
			Response.setMessage("����IDʱ��������!");
			return;
		}
		StringBuffer logs = new StringBuffer("ɾ����Ƶ:");
		String Alias = Config.getValue("UploadDir") + "/" + SiteUtil.getAlias(Application.getCurrentSiteID()) + "/";
		ZCAudioSet AudioSet = new ZCAudioSchema().query(new QueryBuilder(" where id in (" + IDs + ")"));
		ZCAudioRelaSet AudioRelaSet = new ZCAudioRelaSchema().query(new QueryBuilder(" where id in (" + IDs + ")"));
		Transaction trans = new Transaction();
		for (int i = 0; i < AudioSet.size(); i++) {
			FileUtil.delete(Config.getContextRealPath() + Alias + AudioSet.get(i).getPath()
					+ AudioSet.get(i).getFileName());
			trans.add(new QueryBuilder("delete from zdcolumnvalue where relatype=? and relaid=?",
					ColumnUtil.RELA_TYPE_DOCID, "" + AudioSet.get(i).getID()));
			logs.append(AudioSet.get(i).getName() + ",");
		}
		trans.add(AudioSet, Transaction.DELETE_AND_BACKUP);
		trans.add(AudioRelaSet, Transaction.DELETE_AND_BACKUP);
		trans.add(new QueryBuilder(
				"update zccatalog set total=(select count(*) from zcaudio where catalogID=?) where ID=?", AudioSet.get(
						0).getCatalogID(), AudioSet.get(0).getCatalogID()));
		if (trans.commit()) {
			// ɾ�������ļ��������б�
			Publisher p = new Publisher();
			p.deleteFileTask(AudioSet);

			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_DELAUDIO, logs + "�ɹ�", Request.getClientIP());
			this.Response.setLogInfo(1, "ɾ����Ƶ�ɹ�");
		} else {
			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_DELAUDIO, logs + "�ɹ�", Request.getClientIP());
			this.Response.setLogInfo(0, "ɾ����Ƶʧ��");
		}
	}

}
