package com.nswt.datachannel;

/**
 * @User SimonKing
 * @Date 16/9/25.
 * @Time 17:07.
 * @Mail yuhongkai@nswt.com.cn
 */

import java.io.*;

import com.nswt.framework.utility.LogUtil;

import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPClient;

public class SimpleJavaFtp {
	private String server;

	private String userName;

	private String password;

	private int port;

	private String fileName;

	private String ftpDir;

	public SimpleJavaFtp(String server, String userName, String password, int port) {
		this.server = server;
		this.userName = userName;
		this.password = password;
		this.port = port;
	}



//    public boolean uploadFile(String filePath, String ftpDir) {
//        FtpClient ftp = null;
//        try {
//            ftp = new FtpClient(server, port);
//            ftp.login(userName, password);
//            ftp.binary();
//        } catch (Exception ex) {
//            LogUtil.info("FtpServer错误：" + ex.toString());
//            ex.printStackTrace();
//            return false;
//        }
//
//        if (ftpDir != null && !"".equals(ftpDir)) {
//            try {
//                ftp.cd(ftpDir);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        try {
//            RandomAccessFile sendFile = new RandomAccessFile(filePath, "r");
//            sendFile.seek(0);
//
//            int ch;
//            TelnetOutputStream outs = ftp.put(filePath.substring(filePath.lastIndexOf("/") + 1));
//            DataOutputStream outputs = new DataOutputStream(outs);
//            while (sendFile.getFilePointer() < sendFile.length()) {
//                ch = sendFile.read();
//                outputs.write(ch);
//            }
//
//            outs.close();
//            sendFile.close();
//
//            ftp.closeServer();
//        } catch (Exception ex) {
//            LogUtil.info("FTP上传文件失败..." + ex.toString());
//            ex.printStackTrace();
//            return false;
//        }
//        return true;
//    }

	/**
	 * 原方法FtpClient上传大文件无响应问题
	 * ftp上传方法重写
	 * */
	public boolean uploadFile(String filePath, String ftpDir) {

		try {
			FileInputStream input =new FileInputStream(new File(filePath));
			String filename = filePath.substring(filePath.lastIndexOf("/")+1);
			return uploadFile(server,port,userName, password,ftpDir,filename, input);
		} catch (Exception ex) {
			LogUtil.info("FTP上传文件失败..." + ex.toString());
			ex.printStackTrace();
			return false;
		}
	}


	/**
	 * Description: 向FTP服务器上传文件
	 * @Version1.0 Jul 27, 2008 4:31:09 PM by 崔红保（cuihongbao@d-heaven.com）创建
	 * @param server FTP服务器hostname
	 * @param port FTP服务器端口
	 * @param userName FTP登录账号
	 * @param password FTP登录密码
	 * @param ftpDir FTP服务器保存目录
	 * @param filename 上传到FTP服务器上的文件名
	 * @param input 输入流
	 * @return 成功返回true，否则返回false
	 */
	public static boolean uploadFile(String server,int port,String userName, String password, String ftpDir, String filename, InputStream input) {
		boolean success = false;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(server, port);//连接FTP服务器
			//如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.login(userName, password);//登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return success;
			}
			ftp.changeWorkingDirectory(ftpDir);
			ftp.storeFile(filename, input);

			input.close();
			ftp.logout();
			success = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return success;
	}

	public static void main(String[] args) {
		SimpleJavaFtp ftp = new SimpleJavaFtp("127.0.0.1", "root", "1234", 21);
		ftp.uploadFile("E:/RECEIVE_MMS/2008-08-30/13951900000_19-12-24-171/xxxx.txt", "mms");

	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFtpDir() {
		return ftpDir;
	}

	public void setFtpDir(String ftpDir) {
		this.ftpDir = ftpDir;
	}
}

