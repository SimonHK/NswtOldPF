package com.nswt.cms.datachannel;

import com.nswt.framework.Config;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.utility.StringUtil;
import com.nswt.schema.ZCArticleSet;

/**
 * ���������� �ⲿ���������������������� �ɷ����߳�ɨ��������д���
 * @author lanjun 2010-5-4
 */
public class PublishMonitor {
	/**
	 * ��������
	 */
	public static ZCArticleSet set = new ZCArticleSet();
	
	/**
	 * �����߳�
	 */
	public static PublishThread thread = null;
	
	/**
	 * ���������Ĭ��1s��������
	 */
	public static long interval = 1000;
	
	private static Object mutex = new Object();

	/**
	 * ��ʼ��
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
	 * ��ӷ�������
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
	 * ��շ�������
	 */
	public void clean(){
		synchronized(set){
			set.clear();
		}
	}
	
	/**
	 * ִ������
	 * @param list
	 */
	public void execute(ZCArticleSet newSet){
		Publisher p = new Publisher();
		p.publishArticle(newSet);
	}
	
	/**
	 * �����߳� ���̷߳��� ÿ�̶�ʱ��ɨ��һ��
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

