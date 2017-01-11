package com.nswt.cms.datachannel;

import com.nswt.framework.Config;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.utility.StringUtil;
import com.nswt.schema.ZCArticleSet;

/**
 * 发布任务监控 外部发布程序向队列中添加任务 由发布线程扫描任务进行处理
 * @author lanjun 2010-5-4
 */
public class PublishMonitor {
	/**
	 * 发布队列
	 */
	public static ZCArticleSet set = new ZCArticleSet();
	
	/**
	 * 发布线程
	 */
	public static PublishThread thread = null;
	
	/**
	 * 发布间隔，默认1s，可配置
	 */
	public static long interval = 1000;
	
	private static Object mutex = new Object();

	/**
	 * 初始化
	 */
    public void init(){
    	String publishInterval = Config.getValue("PublishInterval");
    	if(StringUtil.isDigit(publishInterval)){
    		interval = Long.parseLong(publishInterval);
    	}
    	synchronized(mutex){
            if(thread == null){
            	synchronized(mutex){
            		thread = new PublishThread();
            		thread.start();
            	}
            }
    	}
    }
 
	/**
	 * 添加发布队列
	 * @param type
	 * @param id
	 */
	public void addJob(SchemaSet newSet){
		init();
		
		synchronized(set){
			set.add(newSet);
		}
	}
	
	/**
	 * 清空发布队列
	 */
	public void clean(){
		synchronized(set){
			set.clear();
		}
	}
	
	/**
	 * 执行任务
	 * @param list
	 */
	public void execute(ZCArticleSet newSet){
		Publisher p = new Publisher();
		p.publishArticle(newSet);
	}
	
	/**
	 * 发布线程 单线程发布 每固定时间扫描一次
	 */
	class PublishThread extends Thread {
		public void run() {
			while(true){
				try {
					sleep(interval);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				ZCArticleSet newSet = new ZCArticleSet();
				synchronized(set){
					newSet.add(set);
					set.clear();
				}
				
				execute(newSet);
			}
		}
	}
}

