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
import com.nswt.framework.utility.HtmlUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.NumberUtil;
import com.nswt.framework.utility.StringUtil;
import com.nswt.framework.utility.VideoUtil;
import com.nswt.platform.Application;
import com.nswt.platform.Priv;
import com.nswt.platform.UserLog;
import com.nswt.platform.pub.NoUtil;
import com.nswt.schema.ZCVideoRelaSchema;
import com.nswt.schema.ZCVideoRelaSet;
import com.nswt.schema.ZCVideoSchema;
import com.nswt.schema.ZCVideoSet;

/**
 *
 *@author huanglei
 *@mail huanglei@nswt.com
 *@date 2008-3-28
 */

public class Video extends Page{

	public static Mapx initEditDialog(Mapx params) {
		ZCVideoSchema Video = new ZCVideoSchema();
		String Alias = Config.getValue("UploadDir")+"/" + SiteUtil.getAlias(Application.getCurrentSiteID()) + "/";
		Video.setID(Long.parseLong(params.get("ID").toString()));
		Video.fill();
		params = Video.toMapx();
		params.put("Alias",Alias);
		params.put("IsOriginal", HtmlUtil.codeToRadios("IsOriginal", "YesOrNo",Video.getIsOriginal()));
		params.put("CustomColumn",ColumnUtil.getHtml(ColumnUtil.RELA_TYPE_CATALOG_COLUMN, Video.getCatalogID()+"",ColumnUtil.RELA_TYPE_DOCID, Video.getID() + ""));
		return params;
	}
	
	public static Mapx initPlayDialog(Mapx params){
		ZCVideoSchema Video = new ZCVideoSchema();
		String Alias = Config.getValue("UploadDir")+"/" + SiteUtil.getAlias(Application.getCurrentSiteID()) + "/";
		Video.setID(Long.parseLong(params.get("ID").toString()));
		Video.fill();
		String files = "";
		files+=".."+Alias+Video.getPath()+Video.getFileName();
		Mapx map = Video.toMapx();
		map.put("files", files);
		return map;
	}
	
	public void edit(){
		ZCVideoSchema video = new ZCVideoSchema();
		video.setID(Long.parseLong($V("ID")));
		video.fill();
		if (!Priv.getPriv(Priv.VIDEO, CatalogUtil.getInnerCode(video.getCatalogID()), Priv.VIDEO_MODIFY)) {
			Response.setLogInfo(0,"操作错误：您没有修改视频的权限，请到角色管理或者用户管理中设置图片栏目的权限！");
			return;
		}
		video.setValue(Request);
		video.setModifyTime(new Date());
		video.setModifyUser(User.getUserName());
		String StartSecond = $V("StartSecond");
		if(StringUtil.isNotEmpty(StartSecond)){
			StartSecond = StartSecond.trim();
		}
		if(StringUtil.isNotEmpty(StartSecond)){
			int ss = Integer.parseInt(StartSecond);
			VideoUtil.captureImage(Config.getContextRealPath()+Config.getValue("UploadDir")+"/"
					+SiteUtil.getAlias(video.getSiteID())+"/"+video.getPath()
					+video.getFileName(), Config.getContextRealPath()+Config.getValue("UploadDir")
					+"/"+SiteUtil.getAlias(video.getSiteID())+"/"+video.getPath() +video.getImageName(),ss);
		}
		DataTable columnDT = ColumnUtil.getColumnValue(ColumnUtil.RELA_TYPE_DOCID, video.getID());
		Transaction trans = new Transaction();
		if (columnDT.getRowCount() > 0) {
			trans.add(new QueryBuilder("delete from zdcolumnvalue where relatype=? and relaid = ?",
					ColumnUtil.RELA_TYPE_DOCID, video.getID()+""));
		}
		trans.add(ColumnUtil.getValueFromRequest(video.getCatalogID(), video.getID(), Request), Transaction.INSERT);
		trans.add(video,Transaction.UPDATE);
		if (trans.commit()) {
			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_EDITVIDEO, "编辑视频:"+video.getName()+" 成功", Request.getClientIP());
			Response.setLogInfo(1, "修改成功!");
		} else {
			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_EDITVIDEO, "编辑视频:"+video.getName()+" 失败", Request.getClientIP());
			Response.setLogInfo(0, "修改失败!");
		}
	}
	
	public static Mapx initUploadDialog(Mapx params) {
		params.put("IsOriginal", HtmlUtil.codeToRadios("IsOriginal", "YesOrNo","N"));
		DataTable dt = new QueryBuilder("select name,id from zccatalog ").executePagedDataTable(100, 0);
		params.put("Who", HtmlUtil.dataTableToOptions(dt));
		return params;
	}
	
	public void transfer(){
		long catalogID = Long.parseLong($V("CatalogID"));
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		Transaction trans = new Transaction();
		trans.add(new QueryBuilder("update ZCVideo set catalogid=? ,CatalogInnerCode=? where ID in (" + ids + ")",
				catalogID,CatalogUtil.getInnerCode(catalogID)));
		
		StringBuffer logs = new StringBuffer("转移视频:");
		DataTable dt = new QueryBuilder("select Name from ZCVideo where id in("+ids+")").executeDataTable();
		for (int i = 0; i < dt.getRowCount(); i++) {
			logs.append(dt.get(i, "Name")+",");
		}
		if (trans.commit()) {
			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_MOVEVIDEO, logs+"成功", Request.getClientIP());
			Response.setLogInfo(1, "转移成功");
		} else {
			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_MOVEVIDEO, logs+"失败", Request.getClientIP());
			Response.setLogInfo(0, "转移失败");
		}
	}

	public void copy() {
		long catalogID = Long.parseLong($V("CatalogID"));
		String Alias = Config.getValue("UploadDir")+"/" + SiteUtil.getAlias(Application.getCurrentSiteID()) + "/";
		String catalogPath = CatalogUtil.getPath(catalogID);
		String InnerCode = new QueryBuilder("select InnerCode from zccatalog where ID = ?",catalogID).executeString();
		String newPath = "upload/Video/" + catalogPath;
		File dir = new File(Config.getContextRealPath() + Alias + newPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String IDs = $V("IDs");
		if (!StringUtil.checkID(IDs)) {
			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_COPYVIDEO, "复制视频失败", Request.getClientIP());
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		ZCVideoSet videoSet = new ZCVideoSchema().query(new QueryBuilder(" where ID in (" + IDs + ")"));
		boolean flag = true;
		String msg = "";
		StringBuffer logs = new StringBuffer("复制图片:");
		for (int i = 0; i < videoSet.size(); i++) {
			ZCVideoSchema video = videoSet.get(i);
			String oldPath = Alias + video.getPath();
			String oldFileName = video.getFileName();
			String oldSrcFileName = video.getSrcFileName();
			String oldImageName = video.getImageName();
			video.setID(NoUtil.getMaxID("DocID"));
			video.setCatalogID(catalogID);
			video.setCatalogInnerCode(InnerCode);
			video.setPath(newPath);
			int random = NumberUtil.getRandomInt(10000);
			video.setFileName(video.getID()+""+random+".flv");
			video.setImageName(video.getID()+""+random+".jpg");
			video.setSrcFileName(video.getID()+""+NumberUtil.getRandomInt(10000)+"."+video.getSuffix());
//			视频缩略图
			if (!FileUtil.copy(Config.getContextRealPath() + oldPath + oldImageName, Config.getContextRealPath() + Alias + newPath
					+ video.getImageName())) {
				msg = "复制失败，原因是：'"+video.getName()+"'的视频缩略图复制失败";
				flag = false;
				break;
			}
			//源文件复制
			if (!FileUtil.copy(Config.getContextRealPath() + oldPath + oldSrcFileName, Config.getContextRealPath() + Alias + newPath
					+ video.getSrcFileName())) {
				msg = "复制失败，原因是：'"+video.getName()+"'的原文件复制失败";
				flag = false;
				break;
			}
			//flv文件
			if (!FileUtil.copy(Config.getContextRealPath() + oldPath+oldFileName, Config.getContextRealPath() + Alias + newPath + video.getFileName())) {
				msg = "复制失败，原因是：'"+video.getName()+"'的flv文件复制失败";
				flag = false;
				break;
			}
			if(!flag){
				break;
			}
			video.setAddTime(new Date());
			video.setAddUser(User.getUserName());
			logs.append( video.getName()+",");
		}
		if (flag && videoSet.insert()) {
			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_COPYVIDEO, "复制视频失败", Request.getClientIP());
			this.Response.setLogInfo(1, "复制视频成功");
		} else {
			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_COPYVIDEO, msg, Request.getClientIP());
			this.Response.setLogInfo(0, msg);
		}
	}
	
	public void del() {
		String IDs = $V("IDs");
		String Alias = Config.getValue("UploadDir")+"/" + SiteUtil.getAlias(Application.getCurrentSiteID()) + "/";
		if (!StringUtil.checkID(IDs)) {
			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_DELVIDEO, "删除视频失败", Request.getClientIP());
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		StringBuffer logs = new StringBuffer("删除视频");
		ZCVideoSet VideoSet = new ZCVideoSchema().query(new QueryBuilder(" where id in (" + IDs + ")"));
		if (!Priv.getPriv(Priv.VIDEO, CatalogUtil.getInnerCode(VideoSet.get(0).getCatalogID()), Priv.VIDEO_MODIFY)) {
			Response.setLogInfo(0,"操作错误：您没有修改视频的权限，请到角色管理或者用户管理中设置图片栏目的权限！");
			return;
		}
		ZCVideoRelaSet VideoRelaSet = new ZCVideoRelaSchema().query(new QueryBuilder(" where id in (" + IDs + ")"));
		Transaction trans = new Transaction();
		for (int i = 0; i < VideoSet.size(); i++) {
			//源文件
			FileUtil.delete(Config.getContextRealPath()+ Alias + VideoSet.get(i).getPath() + VideoSet.get(i).getSrcFileName());
			//缩略图
			FileUtil.delete(Config.getContextRealPath()+ Alias + VideoSet.get(i).getPath() + VideoSet.get(i).getImageName());
			//flv
			FileUtil.delete(Config.getContextRealPath()+ Alias + VideoSet.get(i).getPath()+VideoSet.get(i).getFileName());
			logs.append(VideoSet.get(i).getName()+",");
			trans.add(new QueryBuilder("delete from zdcolumnvalue where relatype=? and relaid=?",ColumnUtil.RELA_TYPE_DOCID, ""+VideoSet.get(i).getID()));
		}
		trans.add(VideoSet, Transaction.DELETE_AND_BACKUP);
		trans.add(VideoRelaSet, Transaction.DELETE_AND_BACKUP);
		trans.add(new QueryBuilder("update zccatalog set total=(select count(*) from zcvideo where catalogID=?) where ID=?"
				,VideoSet.get(0).getCatalogID(),VideoSet.get(0).getCatalogID()));
		if (trans.commit()) {
			// 删除发布文件，更新列表
			Publisher p = new Publisher();
			p.deleteFileTask(VideoSet);
			
			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_DELVIDEO, logs+"成功", Request.getClientIP());
			this.Response.setLogInfo(1, "删除视频成功");
		} else {
			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_DELVIDEO, logs+"失败", Request.getClientIP());
			this.Response.setLogInfo(0, "删除视频失败");
		}
	}
	
}
