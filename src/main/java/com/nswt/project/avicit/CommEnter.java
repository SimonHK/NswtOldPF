package com.nswt.project.avicit;

import java.util.Date;

import com.nswt.cms.pub.SiteUtil;
import com.nswt.framework.Config;
import com.nswt.framework.Page;
import com.nswt.framework.User;
import com.nswt.framework.controls.DataGridAction;
import com.nswt.framework.data.DataCollection;
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

public class CommEnter extends Page {
	
	/**
	 * 添加和修改企业对话框初始化
	 * @param map
	 * @return
	 */
	public static Mapx init(Mapx map) {
		String ID = map.getString("ID");
		if(StringUtil.isNotEmpty(ID) && StringUtil.isDigit(ID)){
			ZCCommEnterpriseSchema enterprise = new ZCCommEnterpriseSchema();
			enterprise.setID(ID);
			enterprise.fill();
			map.putAll(enterprise.toMapx());
			map.put("Place", HtmlUtil.codeToOptions("EnterprisePlace", enterprise.getPlace()));
			map.put("EnterpriseType", HtmlUtil.codeToOptions("EnterpriseType", enterprise.getEnterpriseType()));
			map.put("CommDate",DateUtil.toString(enterprise.getCommDate()));
			return map;
		} else {
			map.put("Place", HtmlUtil.codeToOptions("EnterprisePlace"));
			map.put("EnterpriseType", HtmlUtil.codeToOptions("EnterpriseType"));
		}
		return map;
	}
	
	/**
	 * 后台企业列表
	 * @param dga
	 */
	public static void dg1DataBind(DataGridAction dga) {
		QueryBuilder qb = new QueryBuilder("select * From ZCCommEnterprise order by CommDate desc");
		DataTable dt = qb.executePagedDataTable(dga.getCacheSize(),dga.getPageIndex());
		dt.decodeColumn("Place",HtmlUtil.codeToMapx("EnterprisePlace"));
		dt.decodeColumn("EnterpriseType",HtmlUtil.codeToMapx("EnterpriseType"));
		dt.insertColumn("SiteUrl", SiteUtil.getURL(2));
		if (dt != null && dt.getRowCount() > 0) {
			dt.getDataColumn("CommDate").setDateFormat("yyyy-MM-dd");
		}
		dga.setTotal(qb);
		dga.bindData(dt);
	}

	/**
	 * 后台企业和修改方法
	 *
	 */
	public void add() {
		String ID = $V("ID");
		if (ID == null || ID.trim().length() == 0) {
			if(addSave(null,Request)){
				Response.setStatus(1);
				Response.setMessage("新建企业成功!");
			}else {
				Response.setStatus(0);
				Response.setMessage("新建企业失败!");
			}
		}else{
			if(addSave(ID,Request)){
				Response.setStatus(1);
				Response.setMessage("修改企业成功!");
			}else {
				Response.setStatus(0);
				Response.setMessage("修改企业失败!");
			}
		}

	}

	/**
	 * 实现添加和修改企业的数据持久化
	 * @param ID
	 * @param dc
	 * @return
	 */
	public boolean addSave(String ID,DataCollection dc) {
		ZCCommEnterpriseSchema enterpriseSchema = new ZCCommEnterpriseSchema();
		if (StringUtil.isNotEmpty(ID) && StringUtil.isDigit(ID)) {
			enterpriseSchema.setID(ID);
			enterpriseSchema.fill();
			enterpriseSchema.setValue(dc);
			enterpriseSchema.setModifyTime(new Date());
			enterpriseSchema.setModifyUser(User.getUserName());
		} else {
			enterpriseSchema.setID(NoUtil.getMaxNo("CommEnterID"));
			enterpriseSchema.setValue(dc);
			enterpriseSchema.setVoteCount("1");
			enterpriseSchema.setSiteID(String.valueOf(Application.getCurrentSiteID()));
			enterpriseSchema.setAddTime(new Date());
			enterpriseSchema.setAddUser(User.getUserName());
		}
		Transaction transaction = new Transaction();
		if (ID != null && ID.trim().length() > 0) {
			transaction.add(enterpriseSchema, Transaction.UPDATE);
		} else {
			transaction.add(enterpriseSchema, Transaction.INSERT);
		}
		if (transaction.commit()) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 获取文件上传的信息
	 *
	 */
	public void getFilePath() {
		String ids = $V("FileID");
		if (StringUtil.isEmpty(ids)) {
			return;
		}
		DataTable dt = new QueryBuilder(
				"select id,name,suffix,path,filename,srcfilename from zcattachment where id = ?"
				, ids).executeDataTable();

		String attachPath = "";
		String fileName = "";
		if (dt != null && dt.getRowCount() > 0) {
			String path = Config.getContextPath() + Config.getValue("UploadDir") + "/"
					+ Application.getCurrentSiteAlias();
			path = path.replaceAll("/+", "/");
			attachPath = dt.getString(0, "path") + dt.getString(0, "filename");
			fileName = dt.getString(0, "name") + "." + dt.getString(0, "suffix");
		}
		this.Response.put("CommWord", fileName);
		this.Response.put("CommWordUrl", attachPath);
	}
	/**
	 * 删除企业
	 *
	 */
	public void del() {
		String ids = $V("ID");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		Transaction trans = new Transaction();
		ZCCommEnterpriseSchema ftpSchema = new ZCCommEnterpriseSchema();
		ZCCommEnterpriseSet set = ftpSchema.query(new QueryBuilder("where id in ("
				+ ids + ")"));
		trans.add(set, Transaction.DELETE);
		if (trans.commit()) {
			Response.setStatus(1);
			Response.setMessage("删除成功！");
		} else {
			Response.setStatus(0);
			Response.setMessage("删除数据失败,请重试!");
		}
	}

}
