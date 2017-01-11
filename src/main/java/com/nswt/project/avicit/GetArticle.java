/**
 * 
 */
package com.nswt.project.avicit;

import com.nswt.cms.document.Article;
import com.nswt.cms.pub.CatalogUtil;
import com.nswt.cms.pub.PubFun;
import com.nswt.cms.pub.SiteUtil;
import com.nswt.framework.Config;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.utility.HtmlUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.Priv;
import com.nswt.schema.ZDUserSchema;

/**
 * @author Administrator
 *
 */
public class GetArticle {

	public static DataTable getArticle(String catalogID, String userName, String password, String count) {
		if (StringUtil.isEmpty(catalogID) || StringUtil.isEmpty(userName) || StringUtil.isEmpty(password)) {
			return null;
		}
		
		ZDUserSchema user = new ZDUserSchema();
		user.setUserName(userName);
		user.setPassword(password);
		if (user.query() == null || user.query().size() < 1) {
			return null;
		}
		
		if (!Priv.getPriv(userName, Priv.ARTICLE, CatalogUtil.getInnerCode(catalogID), Priv.ARTICLE_BROWSE)) {
			return null;
		}
		
		QueryBuilder qb = new QueryBuilder("select * from zcarticle where cataloginnercode like ? and status=?"
				, CatalogUtil.getInnerCode(catalogID), Article.STATUS_PUBLISHED);
		qb.append(" order by topflag desc,orderflag desc,publishdate desc, id desc");
		DataTable dt = qb.executePagedDataTable(Integer.parseInt(count), 0);
		if (dt != null && dt.getRowCount() > 0) {
			dt.getDataColumn("PublishDate").setDateFormat("yyyy-MM-dd");
		}
		
		dt.insertColumn("AttributeName");
		Mapx mapattri=HtmlUtil.codeToMapx("ArticleAttribute");
		StringBuffer sb=new StringBuffer();
		for (int i = 0; i < dt.getRowCount(); i++) {
			String att=dt.getString(i, "Attribute");
			if(StringUtil.isNotEmpty(att)){
				String[] atts=att.split(",");
				for (int j = 0; j < atts.length; j++) {
					sb.append(mapattri.get(atts[j])+",");
				}
				dt.set(i, "AttributeName", sb.toString().substring(0,sb.length()-1));
				sb=new StringBuffer();
			}
		}
		
		dt.insertColumns(new String[] {"_RowNo","ArticleLink"});
		String levelString = SiteUtil.getURL(CatalogUtil.getSiteID(catalogID));
		String[] links = new String[dt.getRowCount()];
		for (int i=0; i<dt.getRowCount(); i++) {
			links[i] = levelString + PubFun.getDocURL(dt.get(i));

			String contextServices = Config.getValue("ServicesContext");
			String context = contextServices.substring(0, contextServices.lastIndexOf("/"));
			String imagePath = Config.getContextPath() + Config.getValue("UploadDir") + "/"
					+ SiteUtil.getAlias(CatalogUtil.getSiteID(catalogID)) + "/";
			imagePath = imagePath.replaceAll("/+", "/");

			String attachPath = Config.getContextPath() + "/Services/AttachDownLoad.jsp";
			attachPath = imagePath.replaceAll("/+", "/");

			String content = dt.getString(i, "Content");
			content = content.replaceAll(imagePath, PubFun
					.getLevelStr(CatalogUtil.getDetailLevel(catalogID)));
			content = content.replaceAll(attachPath, Config.getValue("ServicesContext") + "AttachDownLoad.jsp");
		}
		return dt;
	}
}
