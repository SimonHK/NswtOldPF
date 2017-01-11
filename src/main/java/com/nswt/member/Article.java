package com.nswt.member;

import java.util.Date;
import com.nswt.cms.dataservice.ColumnUtil;
import com.nswt.cms.pub.CatalogUtil;
import com.nswt.cms.site.Catalog;
import com.nswt.framework.Ajax;
import com.nswt.framework.Config;
import com.nswt.framework.User;
import com.nswt.framework.controls.DataListAction;
import com.nswt.framework.controls.TreeAction;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.pub.NoUtil;
import com.nswt.platform.pub.OrderUtil;
import com.nswt.schema.ZCArticleSchema;
import com.nswt.schema.ZCAttachmentRelaSchema;
import com.nswt.schema.ZCAttachmentSchema;

public class Article extends Ajax {
	
	public static Mapx init(Mapx params) {
		String UserName = User.getUserName();
		String ID = params.getString("ID");
		String CatalogID = "";
		String MemberType = "";
		String CatalogName = "";
		if(StringUtil.isNotEmpty(UserName)){
			params.put("UserName",UserName);
			Member m = new Member(UserName);
			m.fill();
			MemberType = new QueryBuilder("select CodeName from ZDCode where CodeType='Member.Type' and CodeValue='"+m.getType()+"'").executeString();
			params.put("MemberType",MemberType);
			params.put("Status",m.getStatus());
			if(StringUtil.isNotEmpty(ID)){
				ZCArticleSchema article = new ZCArticleSchema();
				article.setID(ID);
				if(article.fill()){
					CatalogID = article.getCatalogID()+"";
					CatalogName = CatalogUtil.getName(CatalogID);
					String AttachIDs = "";
					String AttachNames = "";
					DataTable dt = new QueryBuilder("select ID from ZCAttachmentRela where RelaID = "+article.getID()+" and RelaType = 'MemberArticle'").executeDataTable();
					if(dt!=null&&dt.getRowCount()>0){
						ZCAttachmentSchema attach = new ZCAttachmentSchema();
						for (int i = 0; i < dt.getRowCount(); i++) {
							attach = new ZCAttachmentSchema();
							attach.setID(dt.getString(i,0));
							if(attach.fill()){
								AttachIDs += attach.getID();
								AttachNames += attach.getOldName();
							}
							if(i!=dt.getRowCount()-1){
								AttachIDs += ",";
								AttachNames += ",";
							}
						}
					}
					params.put("Catalog",CatalogID);
					params.put("CatalogName",CatalogName);
					params.put("AttachIDs",AttachIDs);
					params.put("AttachNames",AttachNames);
					params.put("ArticleID",article.getID());
					params.put("Title",article.getTitle());
					params.put("Content",article.getContent());
					params.put("CustomColumn",ColumnUtil.getHtml(ColumnUtil.RELA_TYPE_CATALOG_COLUMN,CatalogID, ColumnUtil.RELA_TYPE_DOCID, article.getID() + "","hidden"));
				}
			}else{
				params.put("CustomColumn", ColumnUtil.getHtml(ColumnUtil.RELA_TYPE_CATALOG_COLUMN,CatalogID,"hidden"));
			}
		}
		return params;
	}
	
	public static Mapx initSiteID(Mapx params){
		String UserName = User.getUserName();
		Member member = new Member(UserName);
		member.fill();
		String siteID = member.getSiteID()+"";
		if(StringUtil.isEmpty(siteID)||siteID.equals("0")){
			siteID = new QueryBuilder("select ID from ZCSite order by AddTime desc").executeOneValue()+"";
		}
		params.put("SiteID",siteID);
		return params;
	}
	
	public static void initCatalogTree(TreeAction ta) {
		String UserName = User.getUserName();
		Member member = new Member(UserName);
		member.fill();
		String siteID = member.getSiteID()+"";
		if(StringUtil.isEmpty(siteID)){
			siteID = new QueryBuilder("select ID from ZCSite order by AddTime desc").executeOneValue()+"";
		}
		int catalogType = Catalog.CATALOG;
		DataTable dt = null;
		QueryBuilder qb = new QueryBuilder("select ID,ParentID,TreeLevel,Name,SingleFlag,InnerCode,OrderFlag from ZCCatalog" +
		" Where Type = ? and SiteID = ? and TreeLevel-1 <=? and AllowContribute = 'Y' order by orderflag,innercode");
		qb.add(catalogType);
		qb.add(siteID);
		qb.add(ta.getLevel());
		dt = qb.executeDataTable();
		ta.setRootText("Ͷ��������Ŀ");
		ta.bindData(dt);
	}
	
	public void doSave(){
		String UserName = $V("UserName");
		String ArticleID = $V("ArticleID");
		String CatalogID = $V("Catalog");
		String AttachIDs = $V("AttachIDs");
		String Content   = $V("Content");
		ZCArticleSchema article = new ZCArticleSchema();
		Member member = new Member(UserName);
		member.fill();
		String SiteID = member.getSiteID()+"";
		if(StringUtil.isEmpty(SiteID)){
			SiteID = new QueryBuilder("select ID from ZCSite order by AddTime desc").executeOneValue()+"";
		}
		boolean flag = false;
		Transaction trans = new Transaction();
		if(StringUtil.isEmpty(ArticleID)){
			flag = true;
			String CatalogInnerCode = CatalogUtil.getInnerCode(CatalogID);
			article.setID(NoUtil.getMaxID("DocID"));
			article.setCatalogID(CatalogID);
			article.setCatalogInnerCode(CatalogInnerCode);
			article.setSiteID(SiteID);
			if(StringUtil.isNotEmpty(member.getName())){
				article.setAuthor(member.getName());
			}else{
				article.setAuthor(member.getUserName());
			}
			article.setType("1");
			article.setTopFlag("0");
			article.setTemplateFlag("0");
			article.setOrderFlag(OrderUtil.getDefaultOrder());
			article.setCommentFlag("0");
			article.setStatus(com.nswt.cms.document.Article.STATUS_DRAFT);
			article.setHitCount(0);
			article.setStickTime(0);
			article.setPublishFlag("0");
			article.setAddTime(new Date());
			article.setAddUser(User.getUserName());
			article.setProp4("Member");
		}else{
			article.setID(ArticleID);
			article.fill();
		}
		article.setTitle($V("Title"));
		if(Content.indexOf("<!-- Attach -->")!=-1){
			Content = Content.substring(0,Content.indexOf("<!-- Attach -->"));
		}
		
		new QueryBuilder("delete from ZCAttachmentrela where RelaID = "+article.getID()+" and RelaType = 'MemberArticle'").executeNoQuery();
		if(StringUtil.isNotEmpty(AttachIDs)){
			Content += "<!-- Attach -->";
			Content += "<div id='attach'>";
			String[] AttachArr = AttachIDs.split(",");
			ZCAttachmentSchema attach = new ZCAttachmentSchema();
			ZCAttachmentRelaSchema attachrela = new ZCAttachmentRelaSchema();
			for (int i = 0; i < AttachArr.length; i++) {
				attach = new ZCAttachmentSchema();
				attachrela = new ZCAttachmentRelaSchema();
				attach.setID(AttachArr[i]);
				if(attach.fill()){
					Content += "<a href='#;' onClick=\"javascript:window.open('"+Config.getContextPath()+"Services/AttachDownLoad.jsp?id="+attach.getID()+"&SiteID="+SiteID+"', '_blank')"+"\">���ظ�����"+attach.getOldName()+"</a><br/>";
					attachrela.setID(attach.getID());
					attachrela.setRelaID(article.getID());
					attachrela.setRelaType("MemberArticle");
					trans.add(attachrela,Transaction.INSERT);
				}
			}
			Content += "</div>";
		}
		article.setContent(Content);
		
		if(flag){
			trans.add(ColumnUtil.getValueFromRequest(Long.parseLong(CatalogID),article.getID(), this.Request), Transaction.INSERT);
			trans.add(article,Transaction.INSERT);
		}else{
			DataTable columnDT = ColumnUtil.getColumnValue(ColumnUtil.RELA_TYPE_DOCID, ArticleID);
			if (columnDT.getRowCount() > 0) {
				trans.add(new QueryBuilder("delete from zdcolumnvalue where relatype=? and relaid = ?", ColumnUtil.RELA_TYPE_DOCID, ArticleID));
			}
			trans.add(ColumnUtil.getValueFromRequest(Long.parseLong(CatalogID),Long.parseLong(ArticleID), this.Request), Transaction.INSERT);
			trans.add(article,Transaction.UPDATE);
		}
		if(trans.commit()){
			Response.setLogInfo(1,"����ɹ�");
		}else{
			Response.setLogInfo(0,"��������");
		}
	}
	
	public static void dg1DataList(DataListAction dla) {

		String sql = "select ID,CatalogID as Catalog,Title,Addtime,Status from ZCArticle where Prop4 = 'Member' and AddUser = '"+User.getUserName()+"' Order by AddTime Desc";
		String sql2 = "select count(*) from ZCArticle where Prop4 = 'Member' and AddUser = '"+User.getUserName()+"'";
		QueryBuilder qb = new QueryBuilder();
		qb.setSQL(sql2);
		dla.setTotal(qb.executeInt());

		qb.setSQL(sql);
		DataTable dt = qb.executePagedDataTable(dla.getPageSize(), dla.getPageIndex());
		dt.insertColumn("DO");
		dt.insertColumn("CatalogName");
		if (dt != null && dt.getRowCount() > 0) {
			dt.decodeColumn("Status", com.nswt.cms.document.Article.STATUS_MAP);
			dt.getDataColumn("AddTime").setDateFormat("yyyy-MM-dd HH:mm");
		}
		for (int i = 0; i < dt.getRowCount(); i++) {
			dt.set(i,"CatalogName",CatalogUtil.getName(dt.getString(i,"Catalog")));
			if(dt.getString(i,"StatusName").equals("�ѷ���")){
				dt.set(i,"DO","<cite><a href='#;' onclick='preview("+dt.getString(i,"ID")+");'>���</a></cite>");
			}else{
				dt.set(i,"DO","<cite><a href='#;' onclick='preview("+dt.getString(i,"ID")+");'>Ԥ��</a></cite>&nbsp;<cite><a href='WriteArticle.jsp?ID="+dt.getString(i,"ID")+"'>�޸�</a></cite>&nbsp;<em><a href='#;' onclick='del("+dt.getString(i,"ID")+")'>ɾ��</a></em>");
			}
		}
		dla.bindData(dt);
	}
	
	public void del(){
		String ID = $V("ID");
		if(StringUtil.isNotEmpty(ID)){
			ZCArticleSchema article = new ZCArticleSchema();
			article.setID(ID);
			article.fill();
			if(article.deleteAndBackup()){
				Response.setLogInfo(1,"ɾ���ɹ�");
			}else{
				Response.setLogInfo(0,"ɾ��ʧ��");
			}
		}
	}
	
}
