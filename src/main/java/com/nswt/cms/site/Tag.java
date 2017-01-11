package com.nswt.cms.site;

import java.util.Date;

import com.nswt.framework.Page;
import com.nswt.framework.User;
import com.nswt.framework.controls.DataGridAction;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.Application;
import com.nswt.platform.pub.NoUtil;
import com.nswt.schema.ZCTagSchema;
import com.nswt.schema.ZCTagSet;

/**
 * 标签管理
 * 
 * @Author 谭喜才
 * @Date 2016-5-10
 * @Mail txc@nswt.com
 */
public class Tag extends Page {

	public static void dg1DataBind(DataGridAction dga) {
		String TagWord = dga.getParam("TagWord");
		QueryBuilder qb = new QueryBuilder("select * from ZCTag where siteID=?");
		qb.add(Application.getCurrentSiteID());
		if (StringUtil.isNotEmpty(TagWord)) {
			qb.append(" and Tag like ?", "%" + TagWord.trim() + "%");
		}
		if (StringUtil.isNotEmpty(dga.getSortString())) {
			qb.append(dga.getSortString());
		} else {
			qb.append(" order by ID desc");
		}
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.bindData(dt);
	}

	public void dg1Edit() {
		DataTable dt = (DataTable) Request.get("DT");
		ZCTagSet set = new ZCTagSet();
		for (int i = 0; i < dt.getRowCount(); i++) {
			ZCTagSchema tag = new ZCTagSchema();
			tag.setID(Integer.parseInt(dt.getString(i, "ID")));
			tag.fill();
			tag.setValue(dt.getDataRow(i));
			tag.setModifyTime(new Date());
			tag.setModifyUser(User.getUserName());
			if (!checkTagWord(tag.getID(), tag.getTag())) {
				Response.setStatus(0);
				Response.setMessage("更改Tag内容不允许和其他数据的Tag内容重复!");
				return;
			}
			set.add(tag);
		}
		if (set.update()) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("发生错误!");
		}
	}

	public static Mapx init(Mapx params) {
		return null;
	}

	public static boolean checkTagWord(long SiteID, String TagWord) {
		int count = new QueryBuilder("select count(1) from ZCTag where Tag=? and SiteID=?", TagWord, SiteID)
				.executeInt();
		return count == 0;
	}

	public void add() {
		ZCTagSchema tag = new ZCTagSchema();
		String TagWord = $V("Tag").trim();
		tag.setID(NoUtil.getMaxID("TagID"));
		tag.setValue(Request);
		tag.setSiteID(Application.getCurrentSiteID());
		tag.setAddTime(new Date());
		tag.setAddUser(User.getUserName());
		tag.setUsedCount(0);
		if (checkTagWord(Application.getCurrentSiteID(), TagWord)) {
			if (tag.insert()) {
				Response.setLogInfo(1, "新增成功");
			} else {
				Response.setLogInfo(0, "发生错误！");
			}
		} else {
			Response.setStatus(0);
			Response.setMessage("已经存在的Tag内容!");
		}
	}

	public void del() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		Transaction trans = new Transaction();
		ZCTagSchema Tag = new ZCTagSchema();
		QueryBuilder qb = new QueryBuilder("where id in (" + ids + ")");
		ZCTagSet set = Tag.query(qb);
		trans.add(set, Transaction.DELETE_AND_BACKUP);
		if (trans.commit()) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("操作数据库时发生错误!");
		}
	}
}
