package com.nswt.bbs.admin;

import java.util.Date;

import com.nswt.bbs.ForumUtil;
import com.nswt.framework.Ajax;
import com.nswt.framework.User;
import com.nswt.framework.cache.CacheManager;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.HtmlUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.pub.NoUtil;
import com.nswt.schema.ZCForumConfigSchema;
import com.nswt.schema.ZCForumConfigSet;

public class ForumSetting extends Ajax {
	
	public static Mapx init(Mapx params){
		long siteID = ForumUtil.getCurrentBBSSiteID();
		ZCForumConfigSet set = new ZCForumConfigSchema().query(new QueryBuilder("where SiteID = ?", siteID));
		if(set.size()==1){
			params = set.get(0).toMapx();
		}
		DataTable dt = new QueryBuilder("select TempCloseFlag from ZCForumConfig where SiteID = ?", siteID).executeDataTable();
		Mapx map = new Mapx();
		map.put("Y", "是");
		map.put("N", "否");
		params.put("TempCloseFlag", HtmlUtil.mapxToRadios("TempCloseFlag",map, dt.getRowCount()>0?dt.getString(0,"TempCloseFlag"):"N"));
		return params;
	}
	
	public void add() {
		Transaction trans=new Transaction();
		
		ZCForumConfigSchema config = new ZCForumConfigSchema();
		if(StringUtil.isNotEmpty($V("ID"))){
			config.setID($V("ID"));
			config.fill();
			config.setModifyTime(new Date());
			config.setModifyUser(User.getUserName());
		}else{
			config.setID(NoUtil.getMaxID("ForumConfigID"));
			config.setAddTime(new Date());
			config.setAddUser(User.getUserName());
		}
		config.setSiteID(ForumUtil.getCurrentBBSSiteID());
		config.setValue(Request);
		trans.add(config, Transaction.DELETE_AND_INSERT);

		if(trans.commit()){
			CacheManager.set("Forum", "Config", config.getID(), config);
			Response.setLogInfo(1, "操作成功!");
		}else{
			Response.setLogInfo(0, "操作失败!");
		}
		
	}
}
