package com.nswt.cms.site;

import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;

import com.nswt.cms.document.Article;
import com.nswt.cms.pub.CatalogUtil;
import com.nswt.cms.pub.SiteUtil;
import com.nswt.framework.Page;
import com.nswt.framework.User;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.schedule.CronMonitor;
import com.nswt.framework.utility.DateUtil;
import com.nswt.framework.utility.HtmlUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.Application;
import com.nswt.platform.pub.NoUtil;
import com.nswt.schema.ZCCatalogConfigSchema;
import com.nswt.schema.ZCCatalogConfigSet;
import com.nswt.schema.ZCCatalogSchema;
import com.nswt.schema.ZCCatalogSet;
import com.nswt.schema.ZCSiteSchema;
import com.nswt.schema.ZCSiteSet;
import com.nswt.schema.ZDScheduleSchema;
import com.nswt.schema.ZDScheduleSet;

/**
 * 设置栏目定时发布及其他配置
 * 
 * @author Administrator
 * 
 */
public class CatalogConfig extends Page {

	public static void initCatalogConfig() {
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		ZCCatalogSet set = catalog.query(new QueryBuilder(
				" where not exists (select '' from zccatalogconfig where  zccatalogconfig.catalogid=zccatalog.id)"));
		Transaction trans = new Transaction();
		for (int i = 0; i < set.size(); i++) {
			Catalog.initCatalogConfig(set.get(i), trans);
		}
		ZCSiteSet siteSet = new ZCSiteSchema()
				.query(new QueryBuilder(
						" where not exists (select '' from zccatalogconfig where zccatalogconfig.catalogid=0 and zccatalogconfig.siteid=zcsite.id)"));
		for (int i = 0; i < siteSet.size(); i++) {
			Site.initSiteConfig(siteSet.get(i).getID(), trans);
		}
		if (trans.getOperateList().size() > 0) {
			trans.commit();
		}
	}

	public static Mapx init(Mapx params) {
		Mapx AllowStatus = (Mapx) Article.STATUS_MAP.clone();
		AllowStatus.remove(Article.STATUS_WORKFLOW + "");
		AllowStatus.remove(Article.STATUS_OFFLINE + "");
		AllowStatus.remove(Article.STATUS_ARCHIVE + "");

		Mapx AfterEditStatus = (Mapx) Article.STATUS_MAP.clone();
		AfterEditStatus.remove(Article.STATUS_WORKFLOW + "");
		AfterEditStatus.remove(Article.STATUS_OFFLINE + "");
		AfterEditStatus.remove(Article.STATUS_ARCHIVE + "");
		AfterEditStatus.remove(Article.STATUS_PUBLISHED + "");
		long CatalogID = params.getLong("CatalogID");
		String CatalogType = params.getString("Type");
		ZCCatalogConfigSchema config = new ZCCatalogConfigSchema();
		String SiteID = Application.getCurrentSiteID() + "";
		params.put("SiteID", SiteID);
		if (CatalogID != 0) {
			config = new ZCCatalogConfigSchema().query(new QueryBuilder("where CatalogID=?", CatalogID)).get(0);
			params.put("catalogdisplay", "");
			params.put("sitedisplay", "none");
		} else {
			config = new ZCCatalogConfigSchema().query(new QueryBuilder("where CatalogID=0 and SiteID = ?", Long.parseLong(SiteID)))
					.get(0);
			params.put("catalogdisplay", "none");
			params.put("sitedisplay", "");
		}
		String attachDownFlag = "Y";
		String branchManageFlag = "N";
		String allowInnerGather = "N";
		String allowInnerDeploy = "N";
		String syncCatalogInsert = "N";
		String SyncCatalogModify = "N";
		String syncArticleModify = "N";
		String afterSyncStatus = "" + Article.STATUS_DRAFT;
		String afterModifyStatus = "" + Article.STATUS_EDITING;
		String CommentVerify = "";
		if (SiteUtil.getCommentAuditFlag(SiteID)) {
			CommentVerify = "Y";
		} else {
			CommentVerify = "N";
		}
		if (config == null) {
			params.put("AllowStatusOptions", HtmlUtil.mapxToCheckboxes("AllowStatus", AllowStatus, new String[] {
					Article.STATUS_PUBLISHED + "", Article.STATUS_TOPUBLISH + "" },
					new String[] { Article.STATUS_PUBLISHED + "" }));
			params
					.put("AfterEditStatusOptions", HtmlUtil.mapxToOptions(AfterEditStatus, Article.STATUS_TOPUBLISH
							+ ""));
			params.put("PeriodCheck", "checked");
			Calendar c = Calendar.getInstance();
			c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), 22, 0, 0);
			int minute = c.get(Calendar.MINUTE);
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int day = c.get(Calendar.DAY_OF_MONTH);
			params.put("CronExpression", minute + " " + hour + " " + day + "-" + (day - 1) + "/1" + " * *");
			params.put("ArchiveTimeOptions", HtmlUtil.codeToOptions("ArchiveTime", "12"));
			params.put("IsUsingNCheck", "checked");
			params.put("KeyWordNCheck", "checked");
			params.put("AllowComment", HtmlUtil.codeToRadios("AllowComment", "YesOrNo", "Y"));
			params.put("CommentAnonymous", HtmlUtil.codeToRadios("CommentAnonymous", "YesOrNo", "N"));
			params.put("CommentVerify", HtmlUtil.codeToRadios("CommentVerify", "YesOrNo", CommentVerify));
		} else {
			String Time = DateUtil.toString(config.getStartTime(), "yyyy-MM-dd HH:mm:ss");
			params.putAll(config.toMapx());
			params.put("StartDate", Time.substring(0, 10));
			params.put("StartTime", Time.substring(11));

			if (config.getPlanType().equalsIgnoreCase("Period")) {
				params.put("PeriodCheck", "checked");
			} else {
				params.put("CronCheck", "checked");
			}
			params.put("ArchiveTimeOptions", HtmlUtil.codeToOptions("ArchiveTime", config.getArchiveTime()));
			if (config.getIsUsing().equalsIgnoreCase("Y")) {
				params.put("IsUsingYCheck", "checked");
			} else {
				params.put("IsUsingNCheck", "checked");
			}
			if (StringUtil.isNotEmpty(config.getAttachDownFlag())) {
				attachDownFlag = config.getAttachDownFlag();
			}
			if (StringUtil.isNotEmpty(config.getBranchManageFlag())) {
				branchManageFlag = config.getBranchManageFlag();
			}
			if (StringUtil.isNotEmpty(config.getAllowInnerGather())) {
				allowInnerGather = config.getAllowInnerGather();
			}
			if (StringUtil.isNotEmpty(config.getAllowInnerDeploy())) {
				allowInnerDeploy = config.getAllowInnerDeploy();
			}
			if (StringUtil.isNotEmpty(config.getSyncCatalogInsert())) {
				syncCatalogInsert = config.getSyncCatalogInsert();
			}
			if (StringUtil.isNotEmpty(config.getSyncCatalogModify())) {
				SyncCatalogModify = config.getSyncCatalogModify();
			}
			if (StringUtil.isNotEmpty(config.getSyncArticleModify())) {
				syncArticleModify = config.getSyncArticleModify();
			}
			if (StringUtil.isNotEmpty(config.getAllowComment())) {
				params.put("AllowComment", HtmlUtil.codeToRadios("AllowComment", "YesOrNo", config.getAllowComment()));
			} else {
				params.put("AllowComment", HtmlUtil.codeToRadios("AllowComment", "YesOrNo", "Y"));
			}
			if (StringUtil.isNotEmpty(config.getCommentAnonymous())) {
				params.put("CommentAnonymous", HtmlUtil.codeToRadios("CommentAnonymous", "YesOrNo", config
						.getCommentAnonymous()));
			} else {
				params.put("CommentAnonymous", HtmlUtil.codeToRadios("CommentAnonymous", "YesOrNo", "N"));
			}
			if (StringUtil.isNotEmpty(config.getCommentVerify())) {
				params.put("CommentVerify", HtmlUtil
						.codeToRadios("CommentVerify", "YesOrNo", config.getCommentVerify()));
			} else {
				params.put("CommentVerify", HtmlUtil.codeToRadios("CommentVerify", "YesOrNo", CommentVerify));
			}
			if (config.getAfterSyncStatus() != 0) {
				afterSyncStatus = "" + config.getAfterSyncStatus();
			}
			if (config.getAfterModifyStatus() != 0) {
				afterModifyStatus = "" + config.getAfterModifyStatus();
			}
		}

		params.put("AttachDownFlagRadios", HtmlUtil.codeToRadios("AttachDownFlag", "YesOrNo", attachDownFlag));
		params.put("BranchManageFlagRadios", HtmlUtil.codeToRadios("BranchManageFlag", "YesOrNo", branchManageFlag));
		params.put("AllowInnerGather", HtmlUtil.codeToRadios("AllowInnerGather", "YesOrNo", allowInnerGather));
		params.put("AllowInnerDeploy", HtmlUtil.codeToRadios("AllowInnerDeploy", "YesOrNo", allowInnerDeploy));
		params.put("SyncCatalogInsert", HtmlUtil.codeToRadios("SyncCatalogInsert", "YesOrNo", syncCatalogInsert));
		params.put("SyncCatalogModify", HtmlUtil.codeToRadios("SyncCatalogModify", "YesOrNo", SyncCatalogModify));
		params.put("SyncArticleModify", HtmlUtil.codeToRadios("SyncArticleModify", "YesOrNo", syncArticleModify));

		params.put("AfterSyncStatus", HtmlUtil.mapxToOptions(Article.STATUS_MAP, afterSyncStatus));
		params.put("AfterModifyStatus", HtmlUtil.mapxToOptions(Article.STATUS_MAP, afterModifyStatus));

		if (StringUtil.isNotEmpty(CatalogType) && !CatalogType.equals("1")) {
			params.put("display", "none");
		}
		return params;
	}

	public void save() {
		String ID = $V("ID");
		String StartDate = $V("StartDate");
		String StartTime = $V("StartTime");
		String Period = $V("Period");
		Date Time = new Date();
		if (StringUtil.isNotEmpty(StartDate)) {
			if (StringUtil.isNotEmpty(StartTime)) {
				Time = DateUtil.parseDateTime(StartDate + " " + StartTime);
			} else {
				Time = DateUtil.parseDateTime(StartDate + " " + "00:00:00");
			}
		}

		ZCCatalogConfigSchema config = new ZCCatalogConfigSchema();
		ZCCatalogConfigSet configSet = new ZCCatalogConfigSet();
		boolean exists = false;
		if (StringUtil.isEmpty(ID)) {
			config.setID(NoUtil.getMaxID("CatalogConfigID"));
			config.setAddTime(new Date());
			config.setAddUser(User.getUserName());
		} else {
			exists = true;
			config.setID(ID);
			config.fill();
			config.setModifyTime(new Date());
			config.setModifyUser(User.getUserName());
		}
		config.setValue(Request);
		if (StringUtil.isNotEmpty(config.getCatalogID() + "") && !"null".equalsIgnoreCase(config.getCatalogID() + "")
				&& !"0".equals(config.getCatalogID() + "")) {
			config.setCatalogInnerCode(CatalogUtil.getInnerCode(config.getCatalogID()));
		} else {
			config.setCatalogID(0);// 必须置0，不能置为null，依靠null作判断是一种不好的作法，在不同的数据库中有不同的表现
			config.setCatalogInnerCode("");
		}
		config.setStartTime(Time);

		Calendar c = Calendar.getInstance();
		c.setTime(Time);
		StringBuffer sb = new StringBuffer();
		if (config.getPlanType().equalsIgnoreCase("Period")) {
			if ($V("PeriodType").equalsIgnoreCase("Minute")) {
				int minute = c.get(Calendar.MINUTE);
				sb.append(minute);
				sb.append("-");
				if (minute == 0) {
					sb.append("59");
				} else {
					sb.append(minute - 1);
				}
				sb.append("/");
				sb.append(Period);
				sb.append(" * * * *");
			} else if ($V("PeriodType").equalsIgnoreCase("Hour")) {
				int minute = c.get(Calendar.MINUTE);
				int hour = c.get(Calendar.HOUR_OF_DAY);
				sb.append(minute);
				sb.append(" ");
				sb.append(hour);
				sb.append("-");
				if (hour == 0) {
					sb.append("23");
				} else {
					sb.append(hour - 1);
				}
				sb.append("/");
				sb.append(Period);
				sb.append(" * * *");
			} else if ($V("PeriodType").equalsIgnoreCase("Day")) {
				int minute = c.get(Calendar.MINUTE);
				int hour = c.get(Calendar.HOUR_OF_DAY);
				int day = c.get(Calendar.DAY_OF_MONTH);
				sb.append(minute);
				sb.append(" ");
				sb.append(hour);
				sb.append(" ");
				sb.append(day);
				sb.append("-");
				sb.append(day - 1);
				sb.append("/");
				sb.append(Period);
				sb.append(" * *");
			} else if ($V("PeriodType").equalsIgnoreCase("Month")) {
				int minute = c.get(Calendar.MINUTE);
				int hour = c.get(Calendar.HOUR_OF_DAY);
				int day = c.get(Calendar.DAY_OF_MONTH);
				int month = c.get(Calendar.MONTH);
				sb.append(minute);
				sb.append(" ");
				sb.append(hour);
				sb.append(" ");
				sb.append(day);
				sb.append(" ");
				sb.append(month);
				sb.append("-");
				sb.append(month - 1);
				sb.append("/");
				sb.append(Period);
				sb.append(" *");
			}
			config.setCronExpression(sb.toString());
		}

		Transaction trans = new Transaction();

		// 处理沿用设置
		if (StringUtil.isNotEmpty(config.getCatalogID() + "") && !"null".equalsIgnoreCase(config.getCatalogID() + "")
				&& !"0".equals(config.getCatalogID() + "")) {
			if ("2".equalsIgnoreCase($V("CatalogArchiveExtend"))) {
				trans.add(new QueryBuilder("update zccatalogconfig set ArchiveTime=? where cataloginnercode like ?",
						config.getArchiveTime(), config.getCatalogInnerCode() + "%"));
			} else if ("3".equalsIgnoreCase($V("CatalogArchiveExtend"))) {
				trans.add(new QueryBuilder("update zccatalogconfig set ArchiveTime=? where siteID=? and catalogID<>0",
						config.getArchiveTime(), config.getSiteID()));
			}
			if ("1".equalsIgnoreCase($V("CatalogHotWordExtend"))) {
				if (StringUtil.isNotEmpty($V("keywordType"))) {
					config.setHotWordType($V("keywordType"));
				} else {
					config.setHotWordType(0);
				}
			} else if ("2".equalsIgnoreCase($V("CatalogHotWordExtend"))) {
				configSet = new ZCCatalogConfigSchema().query(new QueryBuilder("where cataloginnercode like '"
						+ config.getCatalogInnerCode() + "%'"));
				for (int i = 0; i < configSet.size(); i++) {
					if (StringUtil.isNotEmpty($V("keywordType"))) {
						configSet.get(i).setHotWordType($V("keywordType"));
					} else {
						configSet.get(i).setHotWordType(0);
					}
				}
			}

		} else {
			if ("1".equalsIgnoreCase($V("SiteArchiveExtend"))) {
				trans.add(new QueryBuilder("update zccatalogconfig set ArchiveTime=? where siteID=?", config
						.getArchiveTime(), config.getSiteID()));
			}

			if ("2".equalsIgnoreCase($V("SiteHotWordExtend"))) {
				trans.add(new QueryBuilder("update zccatalogconfig set HotWordType=? where siteID=?", StringUtil
						.isNotEmpty($V("keywordType")) ? $V("keywordType") : "0", config.getSiteID()));
			}
		}

		if (exists) {
			trans.add(config, Transaction.UPDATE);
			trans.add(configSet, Transaction.UPDATE);
		} else {
			trans.add(config, Transaction.INSERT);
		}

		ZDScheduleSchema schedule = new ZDScheduleSchema();
		ZDScheduleSet scheduleSet = schedule.query(new QueryBuilder("where Prop1='Config' and SourceID = "
				+ config.getID()));

		if (scheduleSet == null || scheduleSet.size() < 1) {
			schedule.setID(NoUtil.getMaxID("ScheduleID"));
			schedule.setSourceID(config.getID());
			schedule.setTypeCode("Publisher");
			schedule.setProp1("Config");
			schedule.setAddTime(new Date());
			schedule.setAddUser(User.getUserName());
			schedule.setCronExpression(config.getCronExpression());
			schedule.setPlanType(config.getPlanType());
			schedule.setStartTime(config.getStartTime());
			schedule.setIsUsing(config.getIsUsing());
			trans.add(schedule, Transaction.INSERT);
		} else {
			schedule = scheduleSet.get(0);
			schedule.setCronExpression(config.getCronExpression());
			schedule.setPlanType(config.getPlanType());
			schedule.setStartTime(config.getStartTime());
			schedule.setIsUsing(config.getIsUsing());
			schedule.setModifyTime(new Date());
			schedule.setModifyUser(User.getUserName());
			trans.add(schedule, Transaction.UPDATE);
		}

		try {
			if (trans.commit()) {
				CronMonitor.getNextRunTime(schedule.getCronExpression());
				// 更新缓存
				if (StringUtil.isNotEmpty(config.getCatalogID() + "")
						&& !"null".equalsIgnoreCase(config.getCatalogID() + "")
						&& !"0".equals(config.getCatalogID() + "")) {
					CatalogUtil.update(config.getCatalogID());
				} else {
					SiteUtil.update(config.getSiteID());
				}

				Response.setLogInfo(1, "保存成功");
			} else {
				Response.setLogInfo(0, "发生错误");
			}
		} catch (Exception e) {
			Response.setError("发生错误:Cron表达式不正确!");
		}

	}

	/**
	 * 为系统内采集/分发自动生成密钥
	 */
	public void generatePassword() {
		String password = getPasswordString();
		$S("Password", password);
		Response.setMessage("生成成功!");
	}

	private static int PASSWORD_LENGTH = 32;

	private static char[] cs = ("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890").toCharArray();

	private static String toPrintable(byte[] b) {
		char[] out = new char[b.length];
		for (int i = 0; i < b.length; i++) {
			int index = b[i] % cs.length;
			if (index < 0) {
				index += cs.length;
			}
			out[i] = cs[index];
		}
		return new String(out);
	}

	private static String getPasswordString() {
		byte[] b = new byte[PASSWORD_LENGTH];
		SecureRandom sr = new SecureRandom();
		sr.nextBytes(b);
		return toPrintable(b);
	}

}
