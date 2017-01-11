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
 * 监听Servlet上下文
 * 
 * @Author NSWT
 * @Date 2008-7-22
 * @Mail nswt@nswt.com.cn
 */
public class MainContextListener implements ServletContextListener {
	private CronManager manager;

	/**
	 * 上下文销毁时清除掉某些全局对象
	 * 
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		try {
			if (manager != null) {
				manager.destory();
			}
			SessionListener.clear();
			
			// 以下试图关闭SocketClusteringServer
			SocketClusteringServer.stop();

			// 以下为防止ThreadDeath异常
			LogManager.shutdown();
			LogFactory.releaseAll();
			Introspector.flushCaches();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	/**
	 * 上下文初始化时同时初始化一些全局对象
	 * 
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent arg0) {
		// 以下两行代码MainFilter中也有，有可能是MainFilter先初始化，也有可能是MainContextListener先初始化（考虑WebSphere）
		ServletContext sc = arg0.getServletContext();
		Config.configMap.put("System.ContainerInfo", sc.getServerInfo());// 连接池需要这个属性，所以先置
		Config.getJBossInfo();// 考虑JBoss
		Config.init();
//		manager = CronManager.getInstance();
		ExtendManager.loadConfig();
	}




}
