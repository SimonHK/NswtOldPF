package com.nswt.project.avicit;

import java.util.Date;

import com.nswt.cms.pub.SiteUtil;
import com.nswt.framework.Ajax;
import com.nswt.framework.Config;
import com.nswt.framework.controls.DataListAction;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.DateUtil;
import com.nswt.framework.utility.HtmlUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.Application;
import com.nswt.platform.pub.NoUtil;
import com.nswt.schema.ZCCommEnterpriseSchema;
import com.nswt.schema.ZCCommEnterpriseSet;
import com.nswt.schema.ZCVoteRecorderSchema;
import com.nswt.schema.ZCVoteRecorderSet;

public class CommEnterPrise extends Ajax {

	/**
	 * 添加和修改企业对话框初始化
	 * 
	 * @param map
	 * @return
	 */
	public static Mapx init(Mapx map) {
		String ID = map.getString("ID");
		if (StringUtil.isNotEmpty(ID) && StringUtil.isDigit(ID)) {
			ZCCommEnterpriseSchema enterprise = new ZCCommEnterpriseSchema();
			enterprise.setID(ID);
			enterprise.fill();
			map.putAll(enterprise.toMapx());
			map.put("Place", HtmlUtil.codeToOptions("EnterprisePlace",
					enterprise.getPlace()));
			map.put("EnterpriseType", HtmlUtil.codeToOptions("EnterpriseType",
					enterprise.getEnterpriseType()));
			map.put("CommDate", DateUtil.toString(enterprise.getCommDate()));
			if (StringUtil.isNotEmpty(enterprise.getCommWord())) {
				map.put("CommWordFile", Config.getContextPath()
						+ Config.getValue("Statical.TargetDir") + "/"
						+ Application.getCurrentSiteAlias() + "/"
						+ enterprise.getCommWord());
			} else {
				map.put("CommWordFile", "");
			}
			return map;
		} else {
			map.put("Place", HtmlUtil.codeToOptions("EnterprisePlace",true));
			map.put("EnterpriseType", HtmlUtil.codeToOptions("EnterpriseType",true));
			map.put("CommWordFile", "");
		}
		return map;
	}

	/**
	 * 前端企业展示列表
	 * 
	 * @param dga
	 */
	public static void dg2DataBind(DataListAction dga) {
		String FullName = dga.getParams().getString("FullName");
		String BeginDate = dga.getParams().getString("BeginDate");
		String EndDate = dga.getParams().getString("EndDate");
		String EnterpriseType = dga.getParams().getString("EnterpriseType");
		String Place = dga.getParam("Place");
		QueryBuilder qb = new QueryBuilder(
				"select * From ZCCommEnterprise where 1=1 ");
		if (StringUtil.isNotEmpty(FullName)) {
			qb.append(" and FullName like ?", "%" + FullName + "%");
		}
		if (StringUtil.isNotEmpty(EnterpriseType)) {
			qb.append(" and EnterpriseType = ?", EnterpriseType);
		}
		if (StringUtil.isNotEmpty(Place)) {
			qb.append(" and Place =?", Place);
		}
		if (StringUtil.isNotEmpty(BeginDate)) {
			qb.append(" and CommDate > ?", BeginDate);
		}
		if (StringUtil.isNotEmpty(EndDate)) {
			qb.append(" and CommDate <=?", EndDate);
		}
		qb.append(" order by CommDate desc");
		DataTable dt = qb.executePagedDataTable(10, 0);
		dt.decodeColumn("Place", HtmlUtil.codeToMapx("EnterprisePlace"));
		dt
				.decodeColumn("EnterpriseType", HtmlUtil
						.codeToMapx("EnterpriseType"));
		dt.insertColumn("SiteUrl", SiteUtil.getURL(dga.getParam("SiteID")));
		if (dt != null && dt.getRowCount() > 0) {
			dt.getDataColumn("CommDate").setDateFormat("yyyy-MM-dd");
			dt.insertColumn("RowNumber");
			for (int i = 0; i < dt.getRowCount(); i++) {
				dt.set(i, "RowNumber", i + 1);
			}
		}
		dga.setTotal(qb);
		dga.bindData(dt);
	}

	/**
	 * 前端企业投票展示列表
	 * 
	 * @param dla
	 */
	public static void dg5DataList(DataListAction dla) {
		DataTable dt = new QueryBuilder(
				"select * from ZCCommEnterprise where siteID=? order by VoteCount desc",
				dla.getParam("SiteID")).executePagedDataTable(10, 0);
		dt.insertColumn("Rate");
		int maxScore = new QueryBuilder(
				"select Max(VoteCount) from ZCCommEnterprise where siteID=?",
				dla.getParam("SiteID")).executeInt();
		int maxCount = Math.round(new Double(Math.ceil(maxScore * 1.0 / 10))
				.intValue()) * 10;
		for (int i = 0; i < dt.getRowCount(); i++) {
			int rate = new Double(Math.ceil(dt.getInt(i, "VoteCount") * 100.0
					/ maxCount)).intValue();
			dt.set(i, "Rate", rate);
		}
		String[] color = new String[] { "#e79333", "#ddb732", "#93c02f",
				"#2b8d2a", "#3d6d7b", "#5d8ac3", "#37419a", "#605275",
				"#f03270", "#f03270" };
		dt.insertColumn("Color", color);
		dla.bindData(dt);
	}

	/**
	 * 前端企业投票展示列表
	 * 
	 * @param dla
	 */
	public static void dgVoteCountList(DataListAction dla) {
		DataTable dt = new DataTable();
		ZCCommEnterpriseSet set = new ZCCommEnterpriseSchema()
				.query(new QueryBuilder(" where SiteID=?", dla
						.getParam("SiteID")));
		if (set.size() > 0) {
			String[] value = new String[10];
			int maxScore = new QueryBuilder(
					"select Max(VoteCount) from ZCCommEnterprise").executeInt();
			int size = Math.round(new Double(Math.ceil(maxScore * 1.0 / 10))
					.intValue());
			for (int i = 0; i < 10; i++) {
				value[i] = (i + 1) * size + "";
			}
			dt.insertColumn("VoteBase", value);
			dla.bindData(dt);
		} else {
			dla.bindData(new DataTable());
		}
	}

	/**
	 * 企业投票
	 * 
	 */
	public void vote() {
		String ids = $V("ID");
		String ip = Request.getClientIP();
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		if("True".equals(Config.getValue("IPRestrict"))){
			if (!checkIp(ids,ip)) {
				Response.setStatus(0);
				Response.setMessage("投票失败,一天只能对一个企业投一票!");
				return;
			}
		}
		Transaction trans = new Transaction();
		ZCCommEnterpriseSchema enterpriseSchema = new ZCCommEnterpriseSchema();
		ZCCommEnterpriseSet set = enterpriseSchema.query(new QueryBuilder(
				"where id in (" + ids + ")"));
		if (set != null && set.size() > 0) {
			for (int i = 0; i < set.size(); i++) {
				ZCCommEnterpriseSchema Schema = set.get(i);
				Schema.setVoteCount(Schema.getVoteCount() + 1);
			}
		}
		trans.add(set, Transaction.UPDATE);
		if (trans.commit()) {
			saveVoteRecord(ids,ip);
			Response.setStatus(1);
			Response.setMessage("投票成功！");
			
		} else {
			Response.setStatus(0);
			Response.setMessage("投票失败,请重试!");
		}

	}

	/**
	 * 检查当前ip当天是否对 id对应企业投票
	 * 
	 * @param id
	 * @return
	 */
	public boolean checkIp(String ids,String ip) {
		QueryBuilder qb = new QueryBuilder("select ID from ZCVoteRecorder where");
		 qb.append(" VoteIP=?",ip);
		 qb.append(" and EnterPriseID in(?",ids);
		 qb.append(") and AddTime > ?",DateUtil.getCurrentDate());
		String ID = qb.executeString();
		return StringUtil.isEmpty(ID);
	}
	
	/**
	 * 实现对承诺企业的 投票记录
	 * @param ids
	 * @param ip
	 */
	public void saveVoteRecord(String ids,String ip){
		ZCVoteRecorderSet recorderSet = new ZCVoteRecorderSet();
		String[] id = ids.split(",");
		if(id.length > 0){
			for(int i= 0;i< id.length;i++){
				ZCVoteRecorderSchema recorderSchema = new ZCVoteRecorderSchema();
				recorderSchema.setID(NoUtil.getMaxNo("EnterPriseVoetID"));
				recorderSchema.setVoteIP(ip);
				recorderSchema.setEnterPriseID(id[i]);
				recorderSchema.setAddTime(new Date());
				recorderSet.add(recorderSchema);
			}
		}
		Transaction transaction = new Transaction();
		transaction.add(recorderSet,Transaction.INSERT);
		transaction.commit();
	}
}
