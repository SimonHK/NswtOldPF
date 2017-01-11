package com.nswt.cms.site;

import java.util.Date;
import java.util.List;

import com.nswt.cms.pub.PubFun;
import com.nswt.cms.pub.SiteUtil;
import com.nswt.framework.Config;
import com.nswt.framework.Page;
import com.nswt.framework.User;
import com.nswt.framework.controls.TreeAction;
import com.nswt.framework.controls.TreeItem;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.HtmlUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.platform.Application;
import com.nswt.schema.ZCCatalogSchema;
import com.nswt.schema.ZCPaperIssueSchema;
import com.nswt.schema.ZCPaperSchema;

public class Newspaper extends Page {
	public static void treeDataBind(TreeAction ta) {
		Object obj = ta.getParams().get("SiteID");
		String siteID = (obj != null) ? obj.toString() : Application.getCurrentSiteID()+"";
		int catalogType = Catalog.NEWSPAPER;
		DataTable dt = null;

		QueryBuilder qb = new QueryBuilder("select ID,ParentID,TreeLevel,Name from ZCCatalog Where Type = ? and SiteID = ? and TreeLevel<3",catalogType,siteID);
	    dt = qb.executeDataTable();
	    
		String siteName = "��ֽ��";
		String inputType = (String) ta.getParams().get("Type");
		if ("3".equals(inputType)) {// ��ѡ
			ta.setRootText("<input type='radio' name=CatalogID id='_site' value='" + siteID + "'><label for='_site'>"
					+ siteName + "</label>");
		} else if ("2".equals(inputType)) {// ��ѡ��ȫѡ
			ta.setRootText("<input type='CheckBox' name=CatalogID id='_site' value='" + siteID
					+ "' onclick='selectAll()'><label for='_site'>" + siteName + "</label>");
		} else {
			ta.setRootText(siteName);
		}

		ta.bindData(dt);
		List items = ta.getItemList();
		for (int i = 0; i < items.size(); i++) {
			TreeItem item = (TreeItem)items.get(i);
			if (item.getLevel() == 1)
				item.setIcon("Icons/icon008a2.gif");
		}
	}
	
	public static void docTreeDataBind(TreeAction ta) {
		String siteID = Application.getCurrentSiteID()+"";
		int catalogType = Catalog.NEWSPAPER;
		DataTable dt = null;

	    dt = new QueryBuilder("select ID,ParentID,TreeLevel,Name from ZCCatalog Where Type = ? and SiteID = ?",catalogType,siteID).executeDataTable();
		String siteName = "��ֽ��";

		String inputType = (String) ta.getParams().get("Type");
		if ("3".equals(inputType)) {// ��ѡ
			ta.setRootText("<input type='radio' name=CatalogID id='_site' value='" + siteID + "'><label for='_site'>"
					+ siteName + "</label>");
		} else if ("2".equals(inputType)) {// ��ѡ��ȫѡ
			ta.setRootText("<input type='CheckBox' name=CatalogID id='_site' value='" + siteID
					+ "' onclick='selectAll()'><label for='_site'>" + siteName + "</label>");
		} else {
			ta.setRootText(siteName);
		}

		ta.bindData(dt);
		List items = ta.getItemList();
		for (int i = 0; i < items.size(); i++) {
			TreeItem item = (TreeItem)items.get(i);
			if (item.getLevel() == 1){
				item.setIcon("Icons/icon008a1.gif");
			}else if(item.getLevel() == 2){
				item.setIcon("Icons/icon018a11.gif");
			}else if(item.getLevel() == 3){
				item.setIcon("Icons/icon5.gif");
			}
				
		}
	}

	public static Mapx initDialog(Mapx params) {
		String sql = "select CodeName,CodeValue from ZDCode where ParentCode !='System' and CodeType ='Period' Order by CodeOrder";
		DataTable dt1 = new QueryBuilder(sql).executeDataTable();
		
		Object o1 = params.get("NewspaperID");
		if (o1 != null) {
			long ID = Long.parseLong(o1.toString());
			ZCPaperSchema paper = new ZCPaperSchema();
			paper.setID(ID);
			if (paper.fill()) {
				Mapx map = paper.toMapx();
				String imagePath = PubFun.getImagePath((String) map.get("CoverImage"));
				if (imagePath == null) {
					imagePath = "../Images/nopicture.gif";
				} else {
					imagePath = Config.getContextPath() + Config.getValue("UploadDir")
							+ SiteUtil.getAlias(paper.getSiteID()) + "/" + imagePath;
				}

				map.put("PicSrc", imagePath);
				map.put("optionPeriod", HtmlUtil.dataTableToOptions(dt1));
				return map;
			}
			return null;
		} else {
			params.put("SiteID", Application.getCurrentSiteID()+"");
			params.put("PicSrc", "../Images/nopicture.gif");
			params.put("CoverTemplate", "/template/Paper.html");
			params.put("optionPeriod", HtmlUtil.dataTableToOptions(dt1));
			return params;
		}
	}

	public static Mapx initIssue(Mapx params) {
		String sql = "select concat(year,'��',periodNum,'��') as Name,ID from zcPaperissue"
				+ " where PaperID=(select min(id) from zcPaper where siteid=" + Application.getCurrentSiteID()
				+ ") order by id desc";
		DataTable dt1 = new QueryBuilder(sql).executeDataTable();
		params.put("optionIssue", HtmlUtil.dataTableToOptions(dt1));
		return params;
	}

	public static Mapx init(Mapx params) {
		Object o1 = params.get("NewspaperID");
		if (o1 != null) {
			long ID = Long.parseLong(o1.toString());
			ZCPaperSchema Paper = new ZCPaperSchema();
			Paper.setID(ID);
			if (Paper.fill()) {
				Mapx map = Paper.toMapx();
				return map;
			}
			return params;
		}
		return params;
	}

	// ����ڿ������ڿ�ͬ�����һ����Ŀ
	public void add() {
		Transaction trans = new Transaction();
		Catalog catalog = new Catalog();
		long catalogID = (catalog.add(Request, trans)).getID();

		ZCPaperSchema Paper = new ZCPaperSchema();
		Paper.setID(catalogID);
		Paper.setValue(Request);
		Paper.setAddTime(new Date());
		Paper.setAddUser(User.getUserName());
		trans.add(Paper, Transaction.INSERT);
		if (trans.commit()) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("�������ݷ�������!");
		}
	}

	public void edit() {
		Transaction trans = new Transaction();
		ZCPaperSchema Paper = new ZCPaperSchema();
		Paper.setID(Long.parseLong($V("NewspaperID")));
		if (!Paper.fill()) {
			Response.setStatus(0);
			Response.setMessage("�޸����ݷ�������!");
			return;
		}
		Paper.setValue(Request);
		Paper.setModifyTime(new Date());
		Paper.setModifyUser(User.getUserName());
		trans.add(Paper, Transaction.UPDATE);

		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(Long.parseLong($V("NewspaperID")));
		catalog.fill();
		catalog.setValue(Request);
		catalog.setIndexTemplate($V("CoverTemplate"));
		catalog.setModifyUser(User.getUserName());
		catalog.setModifyTime(new Date());
		trans.add(catalog, Transaction.UPDATE);

		if (trans.commit()) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("�޸����ݷ�������!");
		}
	}

	public void del() {
		Transaction trans = new Transaction();
		String ID = $V("CatalogID");

		// ɾ�������Ŀ
		Catalog.deleteCatalog(trans, Long.parseLong(ID));

		ZCPaperSchema Paper = new ZCPaperSchema();
		Paper.setID(Long.parseLong(ID));
		Paper.fill();

		// �ڿ�
		trans.add(Paper, Transaction.DELETE_AND_BACKUP);
		// �ڿ�����
		trans.add(new ZCPaperIssueSchema().query(new QueryBuilder(" where PaperID =?",ID)), Transaction.DELETE_AND_BACKUP);
		
		if (trans.commit()) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("ɾ���ڿ�ʧ��");
		}
	}

}
