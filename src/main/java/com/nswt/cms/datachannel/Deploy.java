package com.nswt.cms.datachannel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import com.jcraft.jsch.JSchException;
import com.nswt.cms.pub.SiteUtil;
import com.nswt.cms.site.FileList;
import com.nswt.datachannel.CommonFtp;
import com.nswt.datachannel.SFtp;
import com.nswt.framework.Config;
import com.nswt.framework.User;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.messages.LongTimeTask;
import com.nswt.framework.utility.Errorx;
import com.nswt.framework.utility.FileUtil;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.Application;
import com.nswt.platform.pub.NoUtil;
import com.nswt.schema.ZCDeployConfigSchema;
import com.nswt.schema.ZCDeployConfigSet;
import com.nswt.schema.ZCDeployJobSchema;
import com.nswt.schema.ZCDeployJobSet;
import com.nswt.schema.ZCDeployLogSchema;

/**
 * @Author ���� * @Date 2008-3-12
 * @Mail lanjun@nswt.com
 */
public class Deploy {
	public static final int READY = 0; // ����

	public static final int EXECUTION = 1; // ִ����

	public static final int SUCCESS = 2; // �ɹ�

	public static final int FAIL = 3; // ʧ��

	public static final String TemplateDIR = "template";
	
	public static final String OPERATION_COPY = "copy"; // ����
	
	public static final String OPERATION_DELETE = "delete"; // ɾ��

	public final static Mapx depolyStatus = new Mapx();

	static {
		depolyStatus.put(READY + "", "����");
		depolyStatus.put(EXECUTION + "", "ִ����");
		depolyStatus.put(SUCCESS + "", "�ɹ�");
		depolyStatus.put(FAIL + "", "ʧ��");
	}

	/**
	 * ��������������ִ������
	 * 
	 * @param configID
	 *            ��������ID
	 * @param immediate
	 *            �Ƿ�����ִ��
	 * @return
	 */
	public boolean addOneJob(long configID, boolean immediate) {
		ZCDeployJobSchema job = new ZCDeployJobSchema();
		ZCDeployConfigSchema config = new ZCDeployConfigSchema();
		config.setID(configID);
		if (!config.fill()) {
			return false;
		}

		String staticDir = Config.getContextRealPath() + Config.getValue("Statical.TargetDir").replace('\\', '/');
		String sourcePath = staticDir + "/" + Application.getCurrentSiteAlias() + config.getSourceDir();
		job.setID(NoUtil.getMaxID("DeployJobID"));
		job.setConfigID(config.getID());
		job.setSource(sourcePath);
		job.setMethod(config.getMethod());

		String targetDir = config.getTargetDir();
		if (StringUtil.isEmpty(targetDir)) {
			targetDir = "/";
		} else {
			if (!targetDir.endsWith("/")) {
				targetDir += "/";
			}
		}

		job.setTarget(targetDir);
		job.setSiteID(config.getSiteID());
		job.setHost(config.getHost());
		job.setPort(config.getPort());
		job.setUserName(config.getUserName());
		job.setPassword(config.getPassword());
		job.setStatus(Deploy.READY);
		job.setAddTime(new Date());
		job.setAddUser(User.getUserName());

		Transaction trans = new Transaction();
		trans.add(job, Transaction.INSERT);
		if (trans.commit()) {
			if (immediate) {
				executeJob(config,job);
			}
			return true;
		} else {
			LogUtil.getLogger().info("��Ӳ�������ʱ�����ݿ����ʧ��");
			return false;
		}
	}
	
	public boolean addJobs(long siteID, ArrayList list) {
		return addJobs(siteID, list, OPERATION_COPY);
	}
	
	public boolean addJobs(long siteID, ArrayList list, String operation) {
		ZCDeployJobSet set = getJobs(siteID, list, operation);
		Transaction trans = new Transaction();
		trans.add(set, Transaction.INSERT);
		if (trans.commit()) {
			//copyOuterDir();
			return true;
		} else {
			LogUtil.getLogger().info("��Ӳ�������ʱ�����ݿ����ʧ��");
			return false;
		}
	}
	
	/**
	 * �����ַ�
	 * @param list
	 * @param operation
	 * @return
	 */
	public long copyOuterDir() {
		LongTimeTask ltt = new LongTimeTask() {
			public void execute() {
				ZCDeployConfigSet configSet = new ZCDeployConfigSchema().query();
				if (configSet.size()>0) {
					for (int i = 0; i < configSet.size(); i++) {
						ZCDeployJobSchema job = new ZCDeployJobSchema();
						ZCDeployJobSet jobs = job.query(new QueryBuilder("where RetryCount=0 and status<>? and configID=? order by id",
								Deploy.SUCCESS, configSet.get(i).getID()));
						if (jobs != null && jobs.size() > 0) {
							LogUtil.getLogger().info("ִ�зַ����� ��������" + jobs.size());
							executeBatchJob(configSet.get(i),jobs);
						}
					}
				}
			}
		};
		ltt.setUser(User.getCurrent());
		ltt.start();
		return ltt.getTaskID();
	}

    public ZCDeployJobSet getJobs(long siteID, ArrayList list, String operation) {
		ZCDeployJobSet jobSet = new ZCDeployJobSet();
		for (int j = 0; j < list.size(); j++) {
			String srcPath = (String) list.get(j);
			if (StringUtil.isEmpty(srcPath)) {
				continue;
			}
			srcPath = srcPath.replace('\\', '/').replaceAll("///", "/").replaceAll("//", "/");

			String baseDir = Config.getContextRealPath() + Config.getValue("Statical.TargetDir").replace('\\', '/');
			baseDir = baseDir + "/" + SiteUtil.getAlias(siteID);

			baseDir = baseDir.replaceAll("///", "/");
			baseDir = baseDir.replaceAll("//", "/");
			srcPath = srcPath.replaceAll(baseDir, "");

			ZCDeployConfigSchema config = new ZCDeployConfigSchema();

			QueryBuilder qb = new QueryBuilder(" where UseFlag =1 and siteid=? and ? like concat(sourcedir,'%')", siteID, srcPath);
			if (Config.isSQLServer()||Config.isSybase()) {
				qb.setSQL(" where siteid=? and charindex(sourcedir,?)=0");
			}
			if (Config.isDB2()) {
				qb.setSQL(" where siteid=? and locate(sourcedir,'" + srcPath + "')=0");
				qb.getParams().remove(qb.getParams().size() - 1);
			}

			ZCDeployConfigSet set = config.query(qb);

			for (int i = 0; i < set.size(); i++) {
				config = set.get(i);
				String target = config.getTargetDir();
				if (StringUtil.isEmpty(target)) {
					target = "/";
				} else {
					if (!target.endsWith("/")) {
						target += "/";
					}
				}

				String filePath = srcPath;
				if (!config.getSourceDir().equals("/")) {
					filePath = srcPath.replaceAll(config.getSourceDir(), "");
				}
				target = dealFileName(target, filePath);

				ZCDeployJobSchema job = new ZCDeployJobSchema();
				job.setID(NoUtil.getMaxID("DeployJobID"));
				job.setConfigID(config.getID());
				job.setSource(baseDir + srcPath);
				job.setMethod(config.getMethod());
				job.setTarget(target);
				job.setSiteID(config.getSiteID());
				job.setHost(config.getHost());
				job.setPort(config.getPort());
				job.setUserName(config.getUserName());
				job.setPassword(config.getPassword());
				job.setRetryCount(0);
				job.setStatus(Deploy.READY);
				job.setOperation(operation);
				job.setAddTime(new Date());
				if (User.getCurrent() != null) {
					job.setAddUser(User.getUserName());
				} else {
					job.setAddUser("SYS");
				}

				jobSet.add(job);
			}
		}

		return jobSet;
	}

	private String dealFileName(String part1, String part2) {
		if (part1.equals("") || part2.equals(""))
			return part1 + part2;
		if (!part1.endsWith("/") && !part2.startsWith("/"))
			return part1 + "/" + part2;
		if (part1.endsWith("/") && part2.startsWith("/"))
			return part1 + part2.substring(1);
		else
			return part1 + part2;
	}
	
	public boolean executeJob(ZCDeployJobSchema job) {
		ZCDeployConfigSchema config = new ZCDeployConfigSchema();
		config.setID(job.getConfigID());
		if(!config.fill()){
			return false;
		}
		
		return executeJob(config,job);
	}

	/**
	 * ִ��ĳ����������
	 * 
	 * @param jobID
	 * @return
	 */
	public boolean executeJob(long jobID) {
		ZCDeployJobSchema job = new ZCDeployJobSchema();
		job.setID(jobID);
		if (!job.fill()) {
			return false;
		}
		
		ZCDeployConfigSchema config = new ZCDeployConfigSchema();
		config.setID(job.getConfigID());
		if(!config.fill()){
			return false;
		}
		
		return executeJob(config,job);
	}
	
	public boolean executeJob(ZCDeployConfigSchema config,ZCDeployJobSchema job){
		ZCDeployJobSet set = new ZCDeployJobSet();
		set.add(job);
		return executeBatchJob(config,set);
	}
	
	/**
	 * �����б�����ִ������
	 * @param config
	 * @param jobs
	 * @return
	 */
	public boolean executeBatchJob(ZCDeployConfigSchema config,ZCDeployJobSet jobs) {
		if(config == null || jobs.size()<1){
			return false;
		}
		
		Transaction trans = new Transaction();
		String message = "";
		boolean connectFlag = true;
		
		String deployMethod = config.getMethod();
		if("DIR".equals(deployMethod)){
			for (int i = 0; i < jobs.size(); i++) {
				ZCDeployJobSchema job = jobs.get(i);
				//������־
				ZCDeployLogSchema jobLog = new ZCDeployLogSchema();
				jobLog.setID(NoUtil.getMaxID("DeployLogID"));
				jobLog.setSiteID(job.getSiteID());
				jobLog.setJobID(job.getID());
				jobLog.setBeginTime(new Date());

				if (job.getStatus() == Deploy.FAIL) {
					job.setRetryCount(job.getRetryCount() + 1);
				}
				// ��ȫ���ǣ�������ģ���ļ�
				String sourceFile = job.getSource();
				if (sourceFile.indexOf("template") != -1) {
					message = "ģ���ļ�" + sourceFile + "�����ƣ�����";
					LogUtil.getLogger().info("ģ���ļ�" + sourceFile + "�����ƣ�����");
					jobLog.setMessage(message);
					jobLog.setEndTime(new Date());
					job.setStatus(Deploy.SUCCESS);
					trans.add(jobLog, Transaction.INSERT);
					trans.add(job, Transaction.UPDATE);
					continue;
				}
				
				String target = job.getTarget();
				target = target.replace('\\', '/');
				String targetDir = target.substring(0, target.lastIndexOf("/"));
				File dir = new File(targetDir);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				if (!targetDir.endsWith("/template")) {// ���˵�ģ��Ŀ¼
					if (OPERATION_DELETE.equalsIgnoreCase(job.getOperation())) {
						if (FileUtil.delete(target)) {
							message = "�ɹ�ɾ���ļ�" + target;
							LogUtil.getLogger().info(message);
							job.setStatus(Deploy.SUCCESS);
						} else {
							message = "ʧ�ܣ�ɾ���ļ�" + target;
							LogUtil.getLogger().info(message);
							job.setStatus(Deploy.FAIL);
							Errorx.addError(message);
						}
					} else {
						if (FileUtil.copy(sourceFile, target)) {
							message = "�ɹ������ļ�" + sourceFile + "��" + target;
							LogUtil.getLogger().info(message);
							job.setStatus(Deploy.SUCCESS);
						} else {
							message = "ʧ�ܣ������ļ�" + sourceFile + "��" + target;
							LogUtil.getLogger().info(message);
							job.setStatus(Deploy.FAIL);
							Errorx.addError(message);
						}
					}
				}
				
				jobLog.setMessage(message);
				jobLog.setEndTime(new Date());
				LogUtil.getLogger().info(message);
				
				trans.add(jobLog, Transaction.INSERT);
				trans.add(job, Transaction.UPDATE);
			}
		}else if ("FTP".equals(deployMethod)) {
			CommonFtp ftp = new CommonFtp();
			try {
				ftp.connect(config.getHost(), (int)config.getPort(), config.getUserName(),config.getPassword());
				connectFlag = true;
			} catch (IOException e1) {
				e1.printStackTrace();
				//������־
				ZCDeployLogSchema jobLog = new ZCDeployLogSchema();
				jobLog.setID(NoUtil.getMaxID("DeployLogID"));
				jobLog.setSiteID(config.getSiteID());
				jobLog.setJobID(jobs.get(0).getID());
				jobLog.setBeginTime(new Date());
				jobLog.setEndTime(new Date());
				jobLog.setMessage(e1.getMessage());
				trans.add(jobLog, Transaction.INSERT);
				connectFlag = false;
			}
			if(connectFlag){
				for (int i = 0; i < jobs.size(); i++) {
					ZCDeployJobSchema job = jobs.get(i);
					//������־
					ZCDeployLogSchema jobLog = new ZCDeployLogSchema();
					jobLog.setID(NoUtil.getMaxID("DeployLogID"));
					jobLog.setSiteID(job.getSiteID());
					jobLog.setJobID(job.getID());
					jobLog.setBeginTime(new Date());

					if (job.getStatus() == Deploy.FAIL) {
						job.setRetryCount(job.getRetryCount() + 1);
					}
					
					String target = job.getTarget();
					target = target.replace('\\', '/');
					if (OPERATION_DELETE.equalsIgnoreCase(job.getOperation())) {
						try {
							target = target.replaceAll("///", "/");
							if (ftp.delete(target)) {
								message = "FTPɾ���ļ��ɹ�";
								job.setStatus(Deploy.SUCCESS);
							}else{
								message = "FTPɾ���ļ�ʧ��";
								job.setStatus(Deploy.FAIL);
							}
						} catch (Exception e) {
							job.setStatus(Deploy.FAIL);
							message = e.getMessage();
							Errorx.addError(message);
						}
					} else {
						try {
							String srcFile = job.getSource();
							srcFile = srcFile.replaceAll("///", "/");
							srcFile = srcFile.replaceAll("//", "/");
							String path = srcFile;
							File srcPath = new File(path);
							ArrayList list = FileList.getAllFiles(path);
							if(list.size()==0){
								job.setStatus(Deploy.FAIL);
								message = "�ļ������ڣ�"+path;
								Errorx.addError(message);
							} else {
								for (int j = 0; j < list.size(); j++) {
									String name = (String) list.get(j);
									if (name.indexOf(Deploy.TemplateDIR) != -1) {
										continue;
									}
									name = name.replace('\\', '/');
									String targetName = name.replaceAll(path, "");
									//���Դ�ļ�Ϊ�ļ�����ֱ��ȡ�ļ���
									if(srcPath.isFile()){
										targetName = srcPath.getName();
									}
									ftp.upload(name, target + targetName);
								}
								job.setStatus(Deploy.SUCCESS);
								message = "FTP�ϴ��ɹ�";
							}
						} catch (Exception e) {
							job.setStatus(Deploy.FAIL);
							message = e.getMessage();
							Errorx.addError(message);
						}
					}
					jobLog.setMessage(message);
					jobLog.setEndTime(new Date());
					LogUtil.getLogger().info(message);
					
					trans.add(jobLog, Transaction.INSERT);
					trans.add(job, Transaction.UPDATE);
			   }
		    }
			ftp.disconnect();
		}else if ("SFTP".equals(deployMethod)) {
			SFtp sftp = new SFtp();
			try {
				sftp.connect(config.getHost(), (int)config.getPort(), config.getUserName(),config.getPassword());
				connectFlag = true;
			} catch (JSchException e1) {
				e1.printStackTrace();
				//������־
				ZCDeployLogSchema jobLog = new ZCDeployLogSchema();
				jobLog.setID(NoUtil.getMaxID("DeployLogID"));
				jobLog.setSiteID(config.getSiteID());
				jobLog.setJobID(jobs.get(0).getID());
				jobLog.setBeginTime(new Date());
				jobLog.setEndTime(new Date());
				jobLog.setMessage(e1.getMessage());
				trans.add(jobLog, Transaction.INSERT);
				connectFlag = false;
			}
			if(connectFlag){
				for (int i = 0; i < jobs.size(); i++) {
					ZCDeployJobSchema job = jobs.get(i);
					//������־
					ZCDeployLogSchema jobLog = new ZCDeployLogSchema();
					jobLog.setID(NoUtil.getMaxID("DeployLogID"));
					jobLog.setSiteID(job.getSiteID());
					jobLog.setJobID(job.getID());
					jobLog.setBeginTime(new Date());

					if (job.getStatus() == Deploy.FAIL) {
						job.setRetryCount(job.getRetryCount() + 1);
					}
					
					String target = job.getTarget();
					target = target.replace('\\', '/');
					if (OPERATION_DELETE.equalsIgnoreCase(job.getOperation())) {
						try {
							target = target.replaceAll("///", "/");
							if (sftp.delete(target)) {
								message = "SFTPɾ���ļ��ɹ�";
								job.setStatus(Deploy.SUCCESS);
							}else{
								message = "SFTPɾ���ļ�ʧ��";
								job.setStatus(Deploy.FAIL);
							}
						} catch (Exception e) {
							job.setStatus(Deploy.FAIL);
							message = e.getMessage();
							Errorx.addError(message);
						}
					} else {
						try {
							String srcFile = job.getSource();
							srcFile = srcFile.replaceAll("///", "/");
							srcFile = srcFile.replaceAll("//", "/");
							String path = srcFile;
							File srcPath = new File(path);
							ArrayList list = FileList.getAllFiles(path);
							if(list.size() == 0){
								job.setStatus(Deploy.FAIL);
								message = "�ļ�������"+srcFile;
							}else{
								for (int j = 0; j < list.size(); j++) {
									String name = (String) list.get(j);
									if (name.indexOf(Deploy.TemplateDIR) != -1) {
										continue;
									}
									name = name.replace('\\', '/');
									String targetName = name.replaceAll(path, "");
									//���Դ�ļ�Ϊ�ļ�����ֱ��ȡ�ļ���
									if(srcPath.isFile()){
										targetName = srcPath.getName();
									}
									sftp.upload(name, target + targetName);
								}
								job.setStatus(Deploy.SUCCESS);
								message = "SFTP�ϴ��ɹ�";
							}
						} catch (Exception e) {
							job.setStatus(Deploy.FAIL);
							message = e.getMessage();
							Errorx.addError(message);
						}
					}
					jobLog.setMessage(message);
					jobLog.setEndTime(new Date());
					LogUtil.getLogger().info(message);
					
					trans.add(jobLog, Transaction.INSERT);
					trans.add(job, Transaction.UPDATE);
			    }
			}
			sftp.disconnect();
		}

		if (trans.commit()) {
			return true;
		} else {
			LogUtil.getLogger().info("��Ӳ�������ʱ�����ݿ����ʧ��");
			Errorx.addError(message);
			return false;
		}
	}
}
