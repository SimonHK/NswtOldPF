package com.nswt.bbs.admin;

/**
 * 分数设置
 * @author wst
 * 
 */
import java.util.Date;

import com.nswt.bbs.ForumUtil;
import com.nswt.framework.Page;
import com.nswt.framework.User;
import com.nswt.framework.cache.CacheManager;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.pub.NoUtil;
import com.nswt.schema.ZCForumScoreSchema;
import com.nswt.schema.ZCForumScoreSet;

public class ForumScoreSetting extends Page {

	public static Mapx init(Mapx params) {
		ZCForumScoreSet set = new ZCForumScoreSchema().query(new QueryBuilder("where SiteID=?", ForumUtil.getCurrentBBSSiteID()));
		if (set.size() > 0) {
			params = set.get(0).toMapx();
		}
		return params;
	}

	public void save() {
		Transaction trans = new Transaction();
		ZCForumScoreSchema forumScore = new ZCForumScoreSchema();
		if(StringUtil.isNotEmpty($V("ID"))){
			forumScore.setID($V("ID"));
			forumScore.fill();
			forumScore.setValue(Request);
			trans.add(forumScore, Transaction.UPDATE);
		}else{
			forumScore.setID(NoUtil.getMaxID("ForumScoreID"));
			forumScore.setValue(Request);
			forumScore.setAddUser(User.getUserName());
			forumScore.setAddTime(new Date());
			trans.add(forumScore, Transaction.INSERT);
		}
		if (trans.commit()) {
			CacheManager.set("Forum", "Score", forumScore.getID(), forumScore);
			Response.setLogInfo(1, "操作成功！");
		} else {
			Response.setLogInfo(0, "操作失败");
		}
	}
}
