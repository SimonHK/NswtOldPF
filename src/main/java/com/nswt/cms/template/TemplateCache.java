package com.nswt.cms.template;

import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;

/**
 * �����ǩ�ļ�������DataTable���� ������Ե��η�����Ч��
 * 
 * @Author ����
 * @Date 2008-12-26
 * @Mail lanjun@nswt.com
 */
public class TemplateCache {
	private static ThreadLocal mapLocal = new ThreadLocal(); //�Ե�ǰ�߳�
	
	public static DataTable getDataTable(String key){
		if(StringUtil.isEmpty(key)){
			return null;
		}
		
		Mapx map = getCurrent();
		if(map == null){
			map = new Mapx();
		}
		Object obj = map.get(key);
		DataTable dt = null;
		if(obj!=null){
			dt =(DataTable) obj;
		}
		return dt;
	}
	
	public static void setDataTable(String key,DataTable dt){
		Mapx map = getCurrent();
		if(map == null){
			map = new Mapx();
			setCurrent(map);
		}
		map.put(key, dt);
	}
	
	public static QueryBuilder getQueryBuilder(String key){
		if(StringUtil.isEmpty(key)){
			return null;
		}
		
		Mapx map = getCurrent();
		if(map == null){
			map = new Mapx();
		}
		Object obj = map.get(key);
		QueryBuilder qb = null;
		if(obj!=null){
			qb =(QueryBuilder) obj;
		}
		return qb;
	}
	
	public static void setQueryBuilder(String key,QueryBuilder qb){
		Mapx map = getCurrent();
		if(map == null){
			map = new Mapx();
			setCurrent(map);
		}
		map.put(key, qb);
	}
	
	public static void clear(){
		Mapx map = getCurrent();
		if(map != null){
			map.clear();
			setCurrent(map);
		}
	}
	
	public static void setCurrent(Mapx map) {
		mapLocal.set(map);
	}

	public static Mapx getCurrent() {
		return (Mapx) mapLocal.get();
	}
}
