package com.nswt.newui;

import java.util.Date;

import com.nswt.cms.document.Article;
import com.nswt.cms.document.ArticleList;
import com.nswt.cms.pub.CatalogUtil;
import com.nswt.framework.Page;
import com.nswt.framework.User;
import com.nswt.framework.controls.DataGridAction;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.utility.DateUtil;
import com.nswt.framework.utility.HtmlUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.Application;
import com.nswt.platform.Priv;

/*
 * @Author NSWT
 * @Date 2016-8-10
 * @Mail nswt@nswt.com.cn
 */
public class ArticleList2 extends Page {

	public static void dg1DataBind(DataGridAction dga) {
		long catalogID = dga.getParams().getLong("CatalogID");
		if (catalogID == 0) {
			catalogID = dga.getParams().getLong("Cookie.DocList.LastCatalog");
			dga.getParams().put("CatalogID", catalogID);
		}

		if (catalogID != 0) {// 只能是本站点下的栏目
			if (!(Application.getCurrentSiteID() + "").equals(CatalogUtil.getSiteID(catalogID))) {
				catalogID = 0;
				dga.getParams().put("CatalogID", catalogID);
			}
		}

		// cookie存的catalogID有可能用户没有权限
		if (!Priv.getPriv(User.getUserName(), Priv.ARTICLE, CatalogUtil.getInnerCode(catalogID), Priv.ARTICLE_BROWSE)) {
			dga.bindData(new DataTable());
			return;
		}

		String keyword = (String) dga.getParams().get("Keyword");
		String startDate = (String) dga.getParams().get("StartDate");
		String endDate = (String) dga.getParams().get("EndDate");
		String listType = (String) dga.getParams().get("Type");
		if (StringUtil.isEmpty(listType)) {
			listType = "ALL";
		}
		String Table = "";
		if ("ARCHIVE".equals(listType)) {// 归档文章
			Table = "BZCArticle";
		} else {
			Table = "ZCArticle";
		}
		QueryBuilder qb = new QueryBuilder(
				"select ID,Attribute,Title,AddUser,PublishDate,Addtime,Status,WorkFlowID,Type,"
						+ "TopFlag,OrderFlag,TitleStyle,TopDate,ReferTarget,ReferType,ReferSourceID from " + Table
						+ " where CatalogID=?");
		qb.add(catalogID);
		if (StringUtil.isNotEmpty(keyword)) {
			qb.append(" and title like ? ", "%" + keyword.trim() + "%");
		}
		if (StringUtil.isNotEmpty(startDate)) {
			startDate += " 00:00:00";
			qb.append(" and publishdate >= ? ", startDate);
		}
		if (StringUtil.isNotEmpty(endDate)) {
			endDate += " 23:59:59";
			qb.append(" and publishdate <= ? ", endDate);
		}

		if ("ADD".equals(listType)) { // 添加的文档
			qb.append(" and adduser=?", User.getUserName());
		} else if ("WORKFLOW".equals(listType)) {// 待流转
			qb.append(" and status=?", Article.STATUS_WORKFLOW);
		} else if ("TOPUBLISH".equals(listType)) {// 待发布的文档
			qb.append(" and status=?", Article.STATUS_TOPUBLISH);
		} else if ("PUBLISHED".equals(listType)) {// 已发布的文档
			qb.append(" and status=?", Article.STATUS_PUBLISHED);
		} else if ("OFFLINE".equals(listType)) {// 下线文章
			qb.append(" and status=?", Article.STATUS_OFFLINE);
		} else if ("ARCHIVE".equals(listType)) {// 归档文章
			qb.append(" and BackUpMemo='Archive'");
		} else if ("Editer".equals(listType)) {
			qb.append(" and Prop4='Editer'");
		} else if ("Member".equals(listType)) {
			qb.append(" and Prop4='Member'");
		}
		// qb.append(dga.getSortString());
		String orderStr = "";

		if (StringUtil.isNotEmpty(orderStr)) {
			qb.append(" ,orderflag desc");
		} else {
			qb.append(" order by topflag desc,orderflag desc");
		}
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		if (dt != null && dt.getRowCount() > 0) {
			dt.decodeColumn("Status", Article.STATUS_MAP);
			dt.getDataColumn("PublishDate").setDateFormat("yy-MM-dd HH:mm");
		}
		ArticleList.setDetailWorkflowStatus(dt);// 细化工作流状态

		Mapx attributemap = HtmlUtil.codeToMapx("ArticleAttribute");
		dt.insertColumn("Icon");
		if (dt.getRowCount() > 0) {
			for (int i = 0; i < dt.getRowCount(); i++) {
				if (StringUtil.isNotEmpty(dt.getString(i, "Attribute"))) {
					String[] array = dt.getString(i, "Attribute").split(",");
					String attributeName = "";
					for (int j = 0; j < array.length; j++) {
						if (j != array.length - 1) {
							attributeName = attributeName + attributemap.getString(array[j]) + ",";
						} else {
							attributeName = attributeName + attributemap.getString(array[j]);
						}
					}
					dt.set(i, "Title", StringUtil.htmlEncode(dt.getString(i, "Title")) + " <font class='lightred'> ["
							+ attributeName + "]</font>");
				}

				StringBuffer icons = new StringBuffer();

				String topFlag = dt.getString(i, "TopFlag");
				if ("1".equals(topFlag)) {
					String topdate = "永久置顶";
					if (StringUtil.isNotEmpty(dt.getString(i, "TopDate"))) {
						topdate = DateUtil.toString((Date) dt.get(i, "TopDate"), DateUtil.Format_DateTime);
					}
					icons.append("<img src='../Icons/icon13_stick.gif' title='有效期限: " + topdate + "'/>");
				}

				if (StringUtil.isNotEmpty(dt.getString(i, "ReferSourceID"))) {
					int referType = dt.getInt(i, "ReferType");
					if (referType == 1) {
						icons.append("<img src='../Icons/icon13_copy.gif' title='复制'/>");
					} else if (referType == 2) {
						icons.append("<img src='../Icons/icon13_refer.gif' title='引用'/>");
					}
				}

				if (StringUtil.isNotEmpty(dt.getString(i, "ReferTarget"))) {
					icons.append("<img src='../Icons/icon13_copyout.gif' title='复制源'/>");
				}

				dt.set(i, "Icon", icons.toString());
			}
		}
		dga.bindData(dt);
	}

	public void getArticleContent() {
		String id = $V("ID");
		String content = new QueryBuilder("select content from ZCArticle where ID=?", id).executeString();
		$S("Content", content);
	}
}
