package com.nswt.framework;

import java.beans.Introspector;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.LogFactory;
import org.apache.log4j.LogManager;

import com.nswt.framework.clustering.server.SocketClusteringServer;
import com.nswt.framework.extend.ExtendManager;
import com.nswt.framework.schedule.CronManager;

/**
 * ����Servlet������
 * 
 * @Author NSWT
 * @Date 2008-7-22
 * @Mail nswt@nswt.com.cn
 */
public class MainContextListener implements ServletContextListener {
	private CronManager manager;

	/**
	 * ����������ʱ�����ĳЩȫ�ֶ���
	 * 
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		try {
			if (manager != null) {
				manager.destory();
			}
			SessionListener.clear();
			
			// ������ͼ�ر�SocketClusteringServer
			SocketClusteringServer.stop();

			// ����Ϊ��ֹThreadDeath�쳣
			LogManager.shutdown();
			LogFactory.releaseAll();
			Introspector.flushCaches();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	/**
	 * �����ĳ�ʼ��ʱͬʱ��ʼ��һЩȫ�ֶ���
	 * 
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent arg0) {
		// �������д���MainFilter��Ҳ�У��п�����MainFilter�ȳ�ʼ����Ҳ�п�����MainContextListener�ȳ�ʼ��������WebSphere��
		ServletContext sc = arg0.getServletContext();
		Config.configMap.put("System.ContainerInfo", sc.getServerInfo());// ���ӳ���Ҫ������ԣ���������
		Config.getJBossInfo();// ����JBoss
		Config.init();
//		manager = CronManager.getInstance();
		ExtendManager.loadConfig();
	}




}
