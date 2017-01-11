package com.nswt.cms.site;

import java.io.File;
import java.util.Date;

import com.nswt.framework.Config;
import com.nswt.framework.Page;
import com.nswt.framework.User;
import com.nswt.framework.controls.DataGridAction;
import com.nswt.framework.controls.DataListAction;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.FileUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.schema.ZCImagePlayerStyleSchema;
import com.nswt.schema.ZCImagePlayerStyleSet;

/**
 * 图片播放器样式管理
 * 
 * @Author 徐喆
 * @Date 2016-07-02
 * @Mail xuzhe@nswt.com
 */
public class ImagePlayerStyles extends Page {
	
	public static Mapx init(Mapx params) {
		String ImagePlayerStyleID = params.getString("ImagePlayerStyleID");
		params.put("AppPath",Config.getContextPath());
		String fileSize = Config.getValue("AllowAttachSize");
		if(StringUtil.isEmpty(fileSize)){
			fileSize = "20971520"; //默认20M
		}
		params.put("fileMaxSize", fileSize);
		if (StringUtil.isNotEmpty(ImagePlayerStyleID)) {
			ZCImagePlayerStyleSchema style = new ZCImagePlayerStyleSchema();
			style.setID(ImagePlayerStyleID);
			style.fill();
			params.putAll(style.toMapx());
		}else{
			params.put("IsDefault","N");
		}
		return params;
	}
	public static void dg1DataBind(DataGridAction dga) {
		String cmsPath = Config.getContextPath();
		if("/".equals(cmsPath)){
			cmsPath = "";
		}
		QueryBuilder qb = new QueryBuilder("select ZCImagePlayerStyle.*,'"+cmsPath+"' as CmsPath,case IsDefault when 'Y' then '<b>[默认]</b>' else '' end IsDefaultName from ZCImagePlayerStyle order by id");
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(),dga.getPageIndex());
		dga.bindData(dt);
	}
	
	public static void dg1DataList(DataListAction dla){
		String cmsPath = Config.getContextPath();
		if("/".equals(cmsPath)){
			cmsPath = "";
		}
		QueryBuilder qb = new QueryBuilder("select ZCImagePlayerStyle.*,'"+cmsPath+"' as CmsPath from ZCImagePlayerStyle order by id");
		dla.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dla.getPageSize(),dla.getPageIndex());
		dla.bindData(dt);
	}
	
	public void save(){
		String Name = $V("Name");
		String Memo = $V("Memo");
		String IsDefault = $V("IsDefault");
		String Path = $V("FilePath");
		String ImagePlayerStyleID = $V("ImagePlayerStyleID");
		if(StringUtil.isNotEmpty(ImagePlayerStyleID)){
			ZCImagePlayerStyleSchema style = new ZCImagePlayerStyleSchema();
			boolean update = false;
			boolean isSucess = false;
			style.setID(ImagePlayerStyleID);
			if(!style.fill()){
				style.setAddTime(new Date());
				style.setAddUser(User.getUserName());
			}else{
				style.setModifyTime(new Date());
				style.setModifyUser(User.getUserName());
				update = true;
			}
			style.setName(Name);
			style.setMemo(Memo);
			style.setIsDefault(IsDefault);
			if(StringUtil.isNotEmpty(Path)){
				File[] files = new File(Config.getContextRealPath()+Path).listFiles();
				for (int i = 0; i < files.length; i++) {
					File fi = files[i];
					if(fi.getName().substring(0,fi.getName().lastIndexOf(".")).equalsIgnoreCase("thumbIMG")){
						style.setImagePath(Path+fi.getName());
					}
					if(fi.getName().substring(fi.getName().lastIndexOf(".")).equalsIgnoreCase(".html")){
						style.setTemplate(Path+fi.getName());
					}
				}
			}
			if(update){
				if(style.update()){
					isSucess = true;
				}
			}else{
				if(style.insert()){
					isSucess = true;
				}
			}
			if(isSucess){
				Response.setLogInfo(1,"保存成功");
			}else{
				Response.setLogInfo(0,"保存失败");
			}
		}else{
			Response.setLogInfo(0,"保存失败");
		}
	}
	
	public void getPlayerImage(){
		String styleID = $V("StyleID");
		if(StringUtil.isEmpty(styleID)){
			styleID = "1";
		}
		String cmsPath = Config.getContextPath();
		if("/".equals(cmsPath)){
			cmsPath = "";
		}
		ZCImagePlayerStyleSchema style = new ZCImagePlayerStyleSchema();
		style.setID(styleID);
		style.fill();
		Response.put("Src",cmsPath+style.getImagePath());
	}

	public void del() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		Transaction trans = new Transaction();
		ZCImagePlayerStyleSchema style = new ZCImagePlayerStyleSchema();
		ZCImagePlayerStyleSet set = style.query(new QueryBuilder("where id in (" + ids + ") and id > 5"));
		trans.add(set, Transaction.DELETE_AND_BACKUP);
		if (trans.commit()) {
			for (int i = 0; i < set.size(); i++) {
				style = set.get(i);
				File file = new File(Config.getContextRealPath()+"/Upload/ImagePlayerStyles/"+style.getID()+"/");
				FileUtil.deleteFromDir(file.getAbsolutePath());
				if(file.exists()){
					file.delete();
				}
			}
			Response.setStatus(1);
			Response.setMessage("删除成功!");
		} else {
			Response.setStatus(0);
			Response.setMessage("操作数据库时发生错误!");
		}
	}
}
