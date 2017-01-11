package com.nswt.cms.site;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

import org.apache.commons.io.filefilter.FileFilterUtils;

import com.nswt.cms.datachannel.Deploy;
import com.nswt.cms.pub.PubFun;
import com.nswt.cms.pub.SiteUtil;
import com.nswt.cms.template.PreParser;
import com.nswt.framework.Config;
import com.nswt.framework.Page;
import com.nswt.framework.controls.TreeAction;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.utility.FileUtil;
import com.nswt.framework.utility.StringUtil;
import com.nswt.framework.utility.ZipUtil;
import com.nswt.platform.Application;

/**
 * 模板管理
 *
 */

public class Template extends Page{
	public static void treeDataBind(TreeAction ta) {
		Object obj = ta.getParams().get("SiteID");
		String siteID = (obj != null) ? obj.toString() : Application.getCurrentSiteID()+"";
		DataTable dt = new QueryBuilder("select ID,ParentID,Level,Name from ZCCatalog Where SiteID=?",siteID).executeDataTable();
		String siteName = new QueryBuilder("select name from ZCSite where id=?",siteID).executeString();
		ta.setRootText(siteName);
		ta.bindData(dt);
	}
	
	public void dealUploadFile(){
		String IsZip = $V("IsZip");
		String FilePath = $V("FilePath");
		String siteCode = SiteUtil.getAlias($V("SiteID"));
		String uploadDir = FilePath.substring(0,FilePath.lastIndexOf("/"));
		boolean isSucess = false;
		if("true".equals(IsZip)){
			isSucess = unzipFile(FilePath, uploadDir, siteCode);
		}else{
			isSucess = processFile(FilePath,siteCode);
		}
		if(isSucess){
			Response.setLogInfo(1,"导入成功");
		}else{
			Response.setLogInfo(0,"导入失败");
		}
	}
	
	/**
	 * 解压缩文件，并替换模板中的资源路径
	 * 
	 * @param zipFileName
	 * @param siteCode
	 * @return
	 */
	public boolean unzipFile(String zipFileName, String upzipPath, String siteCode) {
		String copyToPath ="";
		if("avicit".equals(siteCode)){
			copyToPath = Config.getContextRealPath() + Config.getValue("Statical.TemplateDir") + "/" + siteCode;
		}else{
			copyToPath = Config.getContextRealPath() + Config.getValue("Statical.TemplateDir") + "/" + siteCode + Config.getValue("Statical.WebRootDir");
		}
		copyToPath = copyToPath.replace('\\', '/').replaceAll("/+", "/");
		upzipPath = upzipPath.replace('\\', '/').replaceAll("/+", "/");
		if (ZipUtil.unzip(zipFileName, upzipPath, true)) {
			FileUtil.delete(zipFileName);
		}
		
		ArrayList deployList = new ArrayList();
		File unzipFile = new File(upzipPath);
		ArrayList fileList =  FileList.getAllFiles(upzipPath);
		for (int i = 0; i < fileList.size(); i++) {
			String fileName = (String)fileList.get(i);
			fileName = fileName.replace('\\', '/').replaceAll("/+", "/");
			String ext = fileName.lastIndexOf(".") == -1 ? "" : fileName.substring(fileName.lastIndexOf(".") + 1);
			String destPath = fileName.replaceAll(upzipPath, copyToPath);
			if(!PubFun.isAllowExt(ext)){
				Response.setStatus(0);
				Response.setMessage("导入失败,不允许创建"+ext+"文件！");
				return false;
			}
			String destDirs = destPath.substring(0,destPath.lastIndexOf("/"));
			File dirs = new File(destDirs);
			if(!dirs.exists()){
				dirs.mkdirs();
			}

			FileUtil.copy(fileName, destPath);
			deployList.add(destPath);
		}

		FileUtil.delete(unzipFile);
		
		Deploy d = new Deploy();
	    d.addJobs(Application.getCurrentSiteID(), deployList);

		return true;
	}

	/**
	 * 处理单个文件，替换资源文件
	 * 
	 * @param fileFullName
	 * @param siteCode
	 * @return
	 */
	public boolean processFile(String fileFullName, String siteCode) {
		ArrayList deployList = new ArrayList();
		File file = new File(fileFullName);
		if (!file.exists()) {
			return false;
		}
		String fileName = file.getName();

		String copyToPath = Config.getContextRealPath() + Config.getValue("Statical.TemplateDir") + "/" + siteCode;

		String ext = fileName.lastIndexOf(".") == -1 ? "" : fileName.substring(fileName.lastIndexOf(".") + 1);
		if ("html".equalsIgnoreCase(ext) || "htm".equalsIgnoreCase(ext) || "jsp".equalsIgnoreCase(ext)
				|| "js".equalsIgnoreCase(ext) || "php".equalsIgnoreCase(ext) || "jsp".equalsIgnoreCase(ext)
				|| "asp".equalsIgnoreCase(ext)) {
			String fileText = FileUtil.readText(file);
			String tplPath = copyToPath + "/template/";
			FileUtil.mkdir(tplPath);
			FileUtil.writeText(tplPath + fileName, fileText);
			deployList.add(tplPath + fileName);
		} else {
			FileUtil.mkdir(copyToPath + "/images/");
			FileUtil.copy(file, copyToPath + "/images/" + fileName);
			deployList.add(copyToPath + "/images/" + fileName);
		}

		FileUtil.delete(file);
		
		Deploy d = new Deploy();
	    d.addJobs(Application.getCurrentSiteID(), deployList);
	    
		return true;
	}
	
	public void preParse(){
		String path = $V("Path");
		if(StringUtil.isEmpty(path)){
			Response.setStatus(0);
			Response.setMessage("模板路径为空!");
			return;
		}
		
		File file = new File(path);
		boolean flag = true;
		if(file.exists()){
			PreParser p = new PreParser();
			p.setSiteID(Application.getCurrentSiteID());
			File [] templates = file.listFiles((FileFilter)FileFilterUtils.makeSVNAware(FileFilterUtils.trueFileFilter()));
			for (int i = 0; i < templates.length; i++) {
				p.setTemplateFileName(templates[i].getPath());
				if(!p.parse()){
					flag = true;
				}
			}
		}else{
			Response.setStatus(0);
			Response.setMessage("文件不存在!");
			return ;
		}
		
		if(flag){
			Response.setStatus(0);
			Response.setMessage("处理成功!");
		}else{
			Response.setStatus(1);
			Response.setMessage("处理失败!");
		}
	}
}
