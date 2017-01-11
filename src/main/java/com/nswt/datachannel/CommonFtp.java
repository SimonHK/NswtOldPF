package com.nswt.datachannel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import com.nswt.framework.utility.LogUtil;

/**
 * ftp封装类 对外提供服务
 * @author lanjun
 * 2016-4-27
 */
public class CommonFtp {
	private static boolean DEBUG = false;

	FTPClient ftp;

	public CommonFtp() {
	}

	public synchronized void connect(String host) throws IOException {
		connect(host, 21);
	}

	public synchronized void connect(String host, int port) throws IOException {
		connect(host, port, "anonymous", "anonymous@a.com");
	}

	public synchronized void connect(String host, int port, String user, String pass) throws IOException {
		try {
			ftp = new FTPClient();
			int reply;
			ftp.connect(host);
			LogUtil.info("Connected to " + host + ".");
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				System.err.println("FTP server refused connection.");
				throw new IOException("FTP server refused connection.");
			}

			if (!ftp.login(user, pass)) {
				ftp.logout();
				throw new IOException("FTP server refused connection.");
			}

		} catch (IOException ex) {
			throw new IOException("Connect to " + host + ":" + port + " Error:  " + ex);
		}
	}

	public synchronized void disconnect(){
		if (ftp == null) {
			return;
		}

		if (ftp.isConnected()) {
			try {
				ftp.disconnect();
			} catch (IOException f) {
			}
		}

	}

	public synchronized boolean mkdir(String path) throws Exception {
		boolean flag = true;
		try {
			if(!ftp.changeWorkingDirectory(path)){
				if(!ftp.makeDirectory(path)){
					String parentPath = path.substring(0, path.lastIndexOf("/"));
					mkdir(parentPath);
					ftp.makeDirectory(path);
					LogUtil.info("FTP创建文件夹："+path);
				}else{
					LogUtil.info("FTP创建文件夹："+path);
				}
			}
		} catch (Exception e) {

		}
		return flag;
	}
	
	public synchronized boolean upload(String srcFile, String tarFile) throws Exception {
		boolean retval = true;
		String path = tarFile.substring(0, tarFile.lastIndexOf("/"));
		mkdir(path);
		ftp.changeWorkingDirectory(path);
		try {
			File file_in = new File(srcFile);
			if (file_in.isDirectory())
				throw new IOException("FTP cannot upload a directory.");
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			ftp.enterLocalPassiveMode();

			InputStream input;
			input = new FileInputStream(srcFile);
			ftp.storeFile(tarFile, input);
			LogUtil.info("上传文件:" + srcFile + " to " + tarFile);
			input.close();

		} catch (Exception ex) {
			retval = false;
			throw new Exception("上传文件失败：" + srcFile + " to " + tarFile + " Failure! " + ex);
		}
		return retval;
	}
	
	/**删除下线的静态页面
	 * 
	 * @param filePath
	 * @return true 成功;
	 * 			false 失败;
	 * @throws Exception
	 */
	public synchronized boolean delete(String filePath) throws Exception {
		ftp.setFileType(FTP.BINARY_FILE_TYPE);
		ftp.enterLocalPassiveMode();
		return ftp.deleteFile(filePath);
	}
	
	public static void main(String[] args) {
		CommonFtp ftp = new CommonFtp();
		try {
			ftp.connect("192.168.1.100", 21, "test", "nswt10301");
			ftp.upload("F:/Xuzhe/NSWT/UI/wwwroot/nswtpDemo/upload/","/");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
