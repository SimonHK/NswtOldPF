?<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="java.net.*"%>
<%@page import="com.nswt.framework.*"%>
<%@page import="com.nswt.framework.utility.*"%>
<%@page import="com.nswt.framework.data.*"%>
<%@page import="com.nswt.framework.cache.*"%>
<%@page import="com.nswt.framework.orm.*"%>
<%@page import="com.nswt.framework.controls.*"%>
<%@page import="com.nswt.schema.*"%>
<%@page import="com.nswt.platform.*"%>
<%@page import="com.nswt.cms.site.*"%>
<%@page import="com.nswt.cms.document.*"%>
<%@page import="com.nswt.cms.dataservice.*"%>
<%@page import="com.nswt.cms.pub.*"%>
<%@page import="com.nswt.platform.pub.*"%>
<%
    String title = request.getParameter("Title");
	String articleID = request.getParameter("ArticleID");
	ZCArticleSchema article = new ZCArticleSchema();
	if (StringUtil.isNotEmpty(articleID) && StringUtil.isDigit(articleID) ) {
		article.setID(articleID);
		article.fill();
		ZCCatalogSchema catalog = CatalogUtil.getSchema(article.getCatalogID());

		ZCSiteSchema site = SiteUtil.getSchema(article.getSiteID() + "");

    	long id = article.getID();
		String path =  PubFun.getArticleURL(site,catalog,article);
		System.out.println(path);
    	out.write("getArticleParams('"+id+"','"+path+"')");
	} else {
		try {
			if(StringUtil.isNotEmpty(title)){
				title  = URLDecoder.decode(title,"utf-8");
			}
		} catch (UnsupportedEncodingException e) {
		}
			
		ZCArticleSet set = article.query(new QueryBuilder("where title=? and catalogid=233",title));
		if(set!=null && set.size()>0){
		  	article = set.get(0);
			ZCCatalogSchema catalog = CatalogUtil.getSchema(article.getCatalogID());

			ZCSiteSchema site = SiteUtil.getSchema(article.getSiteID() + "");

	    	long id = article.getID();
			String path =  PubFun.getArticleURL(site,catalog,article);
			System.out.println(path);
	    	out.write("getArticleParams('"+id+"','"+path+"')");
		}else{
			out.write("getArticleParams('','')");
		}
	}
%>