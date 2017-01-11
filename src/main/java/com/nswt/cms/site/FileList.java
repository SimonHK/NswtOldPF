package com.nswt.cms.site;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import com.nswt.MavenUtil;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;

import com.nswt.cms.datachannel.Deploy;
import com.nswt.cms.pub.PubFun;
import com.nswt.framework.Config;
import com.nswt.framework.Page;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.FileUtil;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.StringUtil;
import com.nswt.framework.utility.ZipUtil;
import com.nswt.platform.Application;
import com.nswt.schema.ZCDeployJobSet;

/**
 * 
 * @author huanglei
 * @mail huanglei@nswt.com
 * @date 2007-9-10
 */

public class FileList extends Page {

	public void add() {
		String baseDir = $V("baseDir"); // 根路径
		String currentPath = $V("currentPath");// 相对路径
		baseDir = baseDir.replace('\\', '/');
		currentPath = currentPath.replace('\\', '/');

		String fileName = $V("fileName");
		String directoryName = $V("directoryName");
		String mess = "文件";
		String filestr = "";
		if (fileName != null && !"".equals(fileName)) {
			filestr = baseDir + "/" + currentPath + "/" + fileName;
			mess = "文件";
		} else if (directoryName != null && !"".equals(directoryName)) {
			filestr = baseDir + "/" + currentPath + "/" + directoryName;
			mess = "目录";
		}
		if (fileName.indexOf(".") != 0) {
			String ext = fileName.substring(fileName.indexOf(".") + 1);
			if (mess.equals("文件") && !PubFun.isAllowExt(ext)) {
				Response.setStatus(0);
				Response.setMessage("创建" + mess + fileName + "失败,不允许创建" + ext + "文件！");
				return;
			}
		}
		File file = new File(filestr);
		if (!file.exists()) {
			try {
				if (mess.equals("文件")) {
					if (file.createNewFile()) {
						Response.setStatus(1);
						Response.setMessage("创建" + mess + fileName + "成功！");
					} else {
						Response.setStatus(0);
						Response.setMessage("创建" + mess + fileName + "失败！");
					}
				} else {
					if (file.mkdir()) {
						Response.setStatus(1);
						Response.setMessage("创建" + mess + fileName + "成功！");
					} else {
						Response.setStatus(0);
						Response.setMessage("创建" + mess + fileName + "失败！");
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
				Response.setStatus(0);
				Response.setMessage("创建" + mess + fileName + "失败！");
			}
		} else {
			Response.setStatus(0);
			Response.setMessage(mess + fileName + "已经存在了！");
		}
	}

	public void edit() {
		String fileName = $V("FileName");
		String content = $V("Content");
		content = StringUtil.htmlDecode(content);
		//if (FileUtil.writeText(fileName, content)) {
		if (FileUtil.writeTextAuto(fileName, content)) {
			Response.setMessage("保存成功");
			Response.setStatus(1);
		} else {
			Response.setMessage("保存失败");
			Response.setStatus(0);
		}
	}

	public void paste() {
		String baseDir = $V("baseDir"); // 根路径
		String currentPath = $V("currentPath");// 相对路径
		String copyFile = $V("copyFile");
		String cutFile = $V("cutFile");

		baseDir = baseDir.replace('\\', '/');
		currentPath = currentPath.replace('\\', '/');
		copyFile = copyFile.replace('\\', '/');
		cutFile = cutFile.replace('\\', '/');

		String oldPath = "";
		String fileName = "";
		if (!"".equals(copyFile)) {
			oldPath = baseDir + "/" + copyFile;
			fileName = copyFile.substring(copyFile.lastIndexOf("/") + 1);
		} else {
			oldPath = baseDir + "/" + cutFile;
			fileName = cutFile.substring(cutFile.lastIndexOf("/") + 1);
		}
		File oldFile = new File(oldPath);
		if (!oldFile.exists()) {
			Response.setMessage(copyFile + "文件不存在");
			Response.setStatus(0);
			return;
		}
		String newPath = baseDir + "/" + currentPath + "/" + fileName;
		File newFile = new File(newPath);
		if (newFile.exists()) {
			newPath = baseDir + "/" + currentPath + "/" + "复件 " + fileName;
		}
		if (!"".equals(copyFile)) {
			FileUtil.copy(oldFile, newPath);
		} else {
			FileUtil.move(oldFile, newPath);
		}

		Response.setMessage("粘贴成功");
		Response.setStatus(1);
	}

	public void del() {
		String baseDir = $V("baseDir"); // 根路径
		String currentPath = $V("currentPath");// 相对路径
		String delFile = $V("delFile");

		baseDir = baseDir.replace('\\', '/');
		currentPath = currentPath.replace('\\', '/');
		String path = baseDir + "/" + currentPath + "/" + delFile;
		File file = new File(path);
		if (!file.exists()) {
			Response.setMessage(delFile + "文件不存在");
			Response.setStatus(0);
			return;
		}

		if (FileUtil.delete(file)) {
			Response.setMessage("删除成功");
			Response.setStatus(1);
		} else {
			Response.setMessage("删除失败");
			Response.setStatus(0);
		}
	}

	public void rename() {
		String baseDir = $V("baseDir"); // 根路径
		String currentPath = $V("currentPath");// 相对路径
		String oldFileName = $V("oldFileName");
		String newFileName = $V("newFileName");
		baseDir = baseDir.replace('\\', '/');
		currentPath = currentPath.replace('\\', '/');

		File file = new File(baseDir + "/" + currentPath + "/" + oldFileName);
		if (file.exists()) {
			String ext = newFileName.substring(newFileName.lastIndexOf(".") + 1);
			if (!PubFun.isAllowExt(ext)) {
				Response.setStatus(0);
				Response.setMessage("重命名失败,不允许命名为" + ext + "文件！");
				return;
			}
			// 如果是模板文件
			if (file.isFile() && (baseDir + "/" + currentPath + "/" + oldFileName).indexOf("template") != -1) {
				// 判断文件名
				// 如果没有后缀,加上.html
				if (newFileName.lastIndexOf(".") == -1) {
					newFileName += ".html";
				}
				if (newFileName.endsWith(".jsp")) {
					Response.setStatus(0);
					Response.setMessage("重命名文件" + oldFileName + "失败,不能重命名为jsp文件！");
					return;
				}
			}

			if (file.renameTo(new File(baseDir + "/" + currentPath + "/" + newFileName))) {
				Response.setStatus(1);
				Response.setMessage("重命名保存成功！");
			} else {
				Response.setStatus(0);
				Response.setMessage("重命名文件" + oldFileName + "失败！");
			}
		} else {
			Response.setStatus(0);
			Response.setMessage(oldFileName + "不存在！");
		}

	}

	public void export() {
		String baseDir = $V("baseDir"); // 根路径
		String currentPath = $V("currentPath");// 相对路径
		String fileName = $V("filename");
		if(fileName==null){
			fileName = "";
		}

		baseDir = baseDir.replace('\\', '/');

		currentPath = currentPath.replace('\\', '/');

		String srcPath = "";
		String destPath = "";

		String tmpPath = Config.getContextRealPath()+ "/Temp";
		tmpPath = tmpPath.replaceAll("/+", "/");
		File tmpFolder = new File(tmpPath);
		if (!tmpFolder.exists()) {
			tmpFolder.mkdirs();
		}

		if (StringUtil.isNotEmpty(currentPath)) {
			srcPath = (baseDir + "/" + currentPath + "/" + fileName).replaceAll("/+", "/");
		} else {
			srcPath = (baseDir + "/" + fileName).replaceAll("/+", "/");
		}
		if(StringUtil.isEmpty(fileName)){
			fileName = "all_export_"+System.currentTimeMillis();
		}
		destPath = (tmpPath + "/" + fileName + ".zip").replaceAll("/+", "/");

		if (StringUtil.isNotEmpty(srcPath)) {
			try {
				File file = new File(srcPath);
				OutputStream output = new FileOutputStream(destPath);
				ZipUtil.zip(file, output);
				String path = Config.getContextPath() + destPath.substring(Config.getContextRealPath().length());
				path = path.replaceAll("/+", "/");
				Response.put("path", path);
				Response.setStatus(1);
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		Response.setStatus(0);

	}

	public void deploy() {
		String baseDir = $V("baseDir"); // 根路径
		String currentPath = $V("currentPath");// 相对路径
		String FileName = $V("filename");
		baseDir = baseDir.replace('\\', '/');
		currentPath = currentPath.replace('\\', '/');
		String srcPath = "";
		if (StringUtil.isNotEmpty(currentPath)) {
			srcPath = "/" + currentPath + "/" + FileName;
		} else {
			srcPath = "/" + FileName;
		}

		Deploy d = new Deploy();
		ArrayList list = new ArrayList();
		list.add(srcPath);
		ZCDeployJobSet jobs = d.getJobs(Application.getCurrentSiteID(), list, Deploy.OPERATION_COPY);
		if (jobs.size() > 0) {
			Transaction trans = new Transaction();
			trans.add(jobs, Transaction.INSERT);
			if (trans.commit()) {
				Response.setStatus(1);
			} else {
				Response.setStatus(0);
				Response.setMessage("添加任务时操作数据库失败。");
			}
		} else {
			Response.setStatus(0);
			Response.setMessage("没有分发任务添加到队列中，请在＂采集与分发->分发到目录＂配置分发任务。");
			LogUtil.getLogger().info("没有添加分发任务");
		}

	}

	public static ArrayList getAllFiles(String filePath) {
		ArrayList list = new ArrayList();
		File file = new File(filePath);

		if (!file.exists()) {
			return list;
		}

		if (file.isFile()) {
			list.add(file.getPath());
			return list;
		}

		// 显示目录，过滤掉.db文件，过滤掉svn文件
		IOFileFilter dirFilter = FileFilterUtils.directoryFileFilter();
		IOFileFilter suffixFilter = FileFilterUtils.notFileFilter(new SuffixFileFilter("db"));
		IOFileFilter allFilter = FileFilterUtils.orFileFilter(dirFilter, suffixFilter);
		File[] files = file.listFiles((FileFilter) FileFilterUtils.makeSVNAware(allFilter));

		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					ArrayList subList = getAllFiles(files[i].getPath());
					for (int j = 0; j < subList.size(); j++) {
						list.add(subList.get(j));
					}
				} else {
					list.add(files[i].getPath());
				}
			}
		}

		return list;
	}
	
	public static ArrayList getAllDirs(String filePath) {
		ArrayList list = new ArrayList();
		File file = new File(filePath);

		if (!file.exists()||file.isFile()) {
			return list;
		}

		// 显示目录
		IOFileFilter dirFilter = FileFilterUtils.directoryFileFilter();
		File[] files = file.listFiles((FileFilter) FileFilterUtils.makeSVNAware(dirFilter));

		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					list.add(files[i].getPath());
					ArrayList subList = getAllDirs(files[i].getPath());
					for (int j = 0; j < subList.size(); j++) {
						list.add(subList.get(j));
					}
				}
			}
		}

		return list;
	}

	public void goOrder(){
		String baseDir = $V("baseDir"); // 根路径
		baseDir = baseDir.replace('\\', '/').replaceAll("///", "/").replaceAll("//", "/");
		String mvnOrder = $V("mvnOrder");
		String shellCommond = Config.getClassesPath()+"mvnCommond.sh";
		//给模板赋执行权限在mac系统和linux系统中使用
		String systemName = Config.getValue("System.OSName").substring(0,1).toLowerCase();
		switch (systemName){
			case "m" : //MAC系统
				MavenUtil.setpermission(shellCommond);
				break;
			case "l" : //类Linux系统
				MavenUtil.setpermission(shellCommond);
				break;
			case "r" : //RedHat系统
				MavenUtil.setpermission(shellCommond);
				break;
			case "c" : //CentOS系统
				MavenUtil.setpermission(shellCommond);
				break;
			default:
				break;
		}

		if (mvnOrder != null && !"".equals(mvnOrder)) {
			try {
				MavenUtil.callMacShell(shellCommond,baseDir, mvnOrder);
				Response.setStatus(1);
				Response.setMessage("执行" + mvnOrder + "成功！");
			} catch (IOException e) {
				Response.setStatus(0);
				Response.setMessage("执行" + mvnOrder + "失败！");
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
				Response.setStatus(0);
				Response.setMessage("执行" + mvnOrder + "失败！");
			}
		}

	}

	
	/**
	 * 仅包含当前文件夹下的文件，不包含子文件夹的文件
	 * @param filePath
	 * @return
	 */
	public static ArrayList getFiles(String filePath) {
		ArrayList list = new ArrayList();
		File file = new File(filePath);

		if (!file.exists()) {
			return list;
		}

		if (file.isFile()) {
			list.add(file.getPath());
			return list;
		}

		// 显示目录，过滤掉.db文件，过滤掉svn文件
		IOFileFilter dirFilter = FileFilterUtils.directoryFileFilter();
		IOFileFilter suffixFilter = FileFilterUtils.notFileFilter(new SuffixFileFilter("db"));
		IOFileFilter allFilter = FileFilterUtils.orFileFilter(dirFilter, suffixFilter);
		File[] files = file.listFiles((FileFilter) FileFilterUtils.makeSVNAware(allFilter));

		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile()) {
					list.add(files[i].getPath());
				}
			}
		}

		return list;
	}

}
