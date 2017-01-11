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
		String baseDir = $V("baseDir"); // ��·��
		String currentPath = $V("currentPath");// ���·��
		baseDir = baseDir.replace('\\', '/');
		currentPath = currentPath.replace('\\', '/');

		String fileName = $V("fileName");
		String directoryName = $V("directoryName");
		String mess = "�ļ�";
		String filestr = "";
		if (fileName != null && !"".equals(fileName)) {
			filestr = baseDir + "/" + currentPath + "/" + fileName;
			mess = "�ļ�";
		} else if (directoryName != null && !"".equals(directoryName)) {
			filestr = baseDir + "/" + currentPath + "/" + directoryName;
			mess = "Ŀ¼";
		}
		if (fileName.indexOf(".") != 0) {
			String ext = fileName.substring(fileName.indexOf(".") + 1);
			if (mess.equals("�ļ�") && !PubFun.isAllowExt(ext)) {
				Response.setStatus(0);
				Response.setMessage("����" + mess + fileName + "ʧ��,��������" + ext + "�ļ���");
				return;
			}
		}
		File file = new File(filestr);
		if (!file.exists()) {
			try {
				if (mess.equals("�ļ�")) {
					if (file.createNewFile()) {
						Response.setStatus(1);
						Response.setMessage("����" + mess + fileName + "�ɹ���");
					} else {
						Response.setStatus(0);
						Response.setMessage("����" + mess + fileName + "ʧ�ܣ�");
					}
				} else {
					if (file.mkdir()) {
						Response.setStatus(1);
						Response.setMessage("����" + mess + fileName + "�ɹ���");
					} else {
						Response.setStatus(0);
						Response.setMessage("����" + mess + fileName + "ʧ�ܣ�");
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
				Response.setStatus(0);
				Response.setMessage("����" + mess + fileName + "ʧ�ܣ�");
			}
		} else {
			Response.setStatus(0);
			Response.setMessage(mess + fileName + "�Ѿ������ˣ�");
		}
	}

	public void edit() {
		String fileName = $V("FileName");
		String content = $V("Content");
		content = StringUtil.htmlDecode(content);
		//if (FileUtil.writeText(fileName, content)) {
		if (FileUtil.writeTextAuto(fileName, content)) {
			Response.setMessage("����ɹ�");
			Response.setStatus(1);
		} else {
			Response.setMessage("����ʧ��");
			Response.setStatus(0);
		}
	}

	public void paste() {
		String baseDir = $V("baseDir"); // ��·��
		String currentPath = $V("currentPath");// ���·��
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
			Response.setMessage(copyFile + "�ļ�������");
			Response.setStatus(0);
			return;
		}
		String newPath = baseDir + "/" + currentPath + "/" + fileName;
		File newFile = new File(newPath);
		if (newFile.exists()) {
			newPath = baseDir + "/" + currentPath + "/" + "���� " + fileName;
		}
		if (!"".equals(copyFile)) {
			FileUtil.copy(oldFile, newPath);
		} else {
			FileUtil.move(oldFile, newPath);
		}

		Response.setMessage("ճ���ɹ�");
		Response.setStatus(1);
	}

	public void del() {
		String baseDir = $V("baseDir"); // ��·��
		String currentPath = $V("currentPath");// ���·��
		String delFile = $V("delFile");

		baseDir = baseDir.replace('\\', '/');
		currentPath = currentPath.replace('\\', '/');
		String path = baseDir + "/" + currentPath + "/" + delFile;
		File file = new File(path);
		if (!file.exists()) {
			Response.setMessage(delFile + "�ļ�������");
			Response.setStatus(0);
			return;
		}

		if (FileUtil.delete(file)) {
			Response.setMessage("ɾ���ɹ�");
			Response.setStatus(1);
		} else {
			Response.setMessage("ɾ��ʧ��");
			Response.setStatus(0);
		}
	}

	public void rename() {
		String baseDir = $V("baseDir"); // ��·��
		String currentPath = $V("currentPath");// ���·��
		String oldFileName = $V("oldFileName");
		String newFileName = $V("newFileName");
		baseDir = baseDir.replace('\\', '/');
		currentPath = currentPath.replace('\\', '/');

		File file = new File(baseDir + "/" + currentPath + "/" + oldFileName);
		if (file.exists()) {
			String ext = newFileName.substring(newFileName.lastIndexOf(".") + 1);
			if (!PubFun.isAllowExt(ext)) {
				Response.setStatus(0);
				Response.setMessage("������ʧ��,����������Ϊ" + ext + "�ļ���");
				return;
			}
			// �����ģ���ļ�
			if (file.isFile() && (baseDir + "/" + currentPath + "/" + oldFileName).indexOf("template") != -1) {
				// �ж��ļ���
				// ���û�к�׺,����.html
				if (newFileName.lastIndexOf(".") == -1) {
					newFileName += ".html";
				}
				if (newFileName.endsWith(".jsp")) {
					Response.setStatus(0);
					Response.setMessage("�������ļ�" + oldFileName + "ʧ��,����������Ϊjsp�ļ���");
					return;
				}
			}

			if (file.renameTo(new File(baseDir + "/" + currentPath + "/" + newFileName))) {
				Response.setStatus(1);
				Response.setMessage("����������ɹ���");
			} else {
				Response.setStatus(0);
				Response.setMessage("�������ļ�" + oldFileName + "ʧ�ܣ�");
			}
		} else {
			Response.setStatus(0);
			Response.setMessage(oldFileName + "�����ڣ�");
		}

	}

	public void export() {
		String baseDir = $V("baseDir"); // ��·��
		String currentPath = $V("currentPath");// ���·��
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
		String baseDir = $V("baseDir"); // ��·��
		String currentPath = $V("currentPath");// ���·��
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
				Response.setMessage("�������ʱ�������ݿ�ʧ�ܡ�");
			}
		} else {
			Response.setStatus(0);
			Response.setMessage("û�зַ�������ӵ������У����ڣ��ɼ���ַ�->�ַ���Ŀ¼�����÷ַ�����");
			LogUtil.getLogger().info("û����ӷַ�����");
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

		// ��ʾĿ¼�����˵�.db�ļ������˵�svn�ļ�
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

		// ��ʾĿ¼
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
		String baseDir = $V("baseDir"); // ��·��
		baseDir = baseDir.replace('\\', '/').replaceAll("///", "/").replaceAll("//", "/");
		String mvnOrder = $V("mvnOrder");
		String shellCommond = Config.getClassesPath()+"mvnCommond.sh";
		//��ģ�帳ִ��Ȩ����macϵͳ��linuxϵͳ��ʹ��
		String systemName = Config.getValue("System.OSName").substring(0,1).toLowerCase();
		switch (systemName){
			case "m" : //MACϵͳ
				MavenUtil.setpermission(shellCommond);
				break;
			case "l" : //��Linuxϵͳ
				MavenUtil.setpermission(shellCommond);
				break;
			case "r" : //RedHatϵͳ
				MavenUtil.setpermission(shellCommond);
				break;
			case "c" : //CentOSϵͳ
				MavenUtil.setpermission(shellCommond);
				break;
			default:
				break;
		}

		if (mvnOrder != null && !"".equals(mvnOrder)) {
			try {
				MavenUtil.callMacShell(shellCommond,baseDir, mvnOrder);
				Response.setStatus(1);
				Response.setMessage("ִ��" + mvnOrder + "�ɹ���");
			} catch (IOException e) {
				Response.setStatus(0);
				Response.setMessage("ִ��" + mvnOrder + "ʧ�ܣ�");
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
				Response.setStatus(0);
				Response.setMessage("ִ��" + mvnOrder + "ʧ�ܣ�");
			}
		}

	}

	
	/**
	 * ��������ǰ�ļ����µ��ļ������������ļ��е��ļ�
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

		// ��ʾĿ¼�����˵�.db�ļ������˵�svn�ļ�
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
