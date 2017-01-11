package com.nswt.cms.site;

import java.util.Date;
import java.util.List;

import com.nswt.cms.datachannel.Publisher;
import com.nswt.cms.pub.CatalogUtil;
import com.nswt.cms.pub.SiteUtil;
import com.nswt.framework.Config;
import com.nswt.framework.Page;
import com.nswt.framework.User;
import com.nswt.framework.controls.TreeAction;
import com.nswt.framework.controls.TreeItem;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.messages.LongTimeTask;
import com.nswt.framework.utility.HtmlUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.Application;
import com.nswt.schema.ZCCatalogSchema;
import com.nswt.schema.ZCMagazineCatalogRelaSchema;
import com.nswt.schema.ZCMagazineIssueSchema;
import com.nswt.schema.ZCMagazineSchema;


public class Magazine extends Page {
	public static void treeDataBind(TreeAction ta) {
		long siteID = Application.getCurrentSiteID();
		int catalogType = Catalog.MAGAZINE;
		String parentLevel = (String) ta.getParams().get("ParentLevel");
		String parentID = (String) ta.getParams().get("ParentID");
		DataTable dt = null;
		if (ta.isLazyLoad()) {
			QueryBuilder qb = new QueryBuilder("select ID,ParentID,TreeLevel,Name from ZCCatalog Where Type = ?" 
					+ " and SiteID = ? and TreeLevel>? and innerCode like ? order by orderflag,innercode");
			qb.add(Long.parseLong(String.valueOf(catalogType)));
			qb.add(siteID);
			qb.add(Long.parseLong(parentLevel));
			qb.add(CatalogUtil.getInnerCode(parentID)+"%");
			dt= qb.executeDataTable();
		} else {
			QueryBuilder qb = new QueryBuilder("select ID,ParentID,TreeLevel,Name from ZCCatalog Where Type = ?" 
					+ " and SiteID = ? and TreeLevel-1 <=? order by orderflag,innercode");
			qb.add(catalogType);
			qb.add(siteID);
			qb.add(ta.getLevel());
			dt = qb.executeDataTable();
		}

//		DataTable dt1 = new QueryBuilder("select ID,0 as ParentID,1 as TreeLevel,Name from zcmagazine").executeDataTable();
		// dt.union(dt1);

		String siteName = "期刊库";

		String inputType = (String) ta.getParams().get("Type");
		if ("3".equals(inputType)) {// 单选
			ta.setRootText("<input type='radio' name=CatalogID id='_site' value='" + siteID + "'><label for='_site'>"
					+ siteName + "</label>");
		} else if ("2".equals(inputType)) {// 多选、全选
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
				item.setIcon("Icons/icon008a19.gif");
			}
			if(item.getLevel()==2){
				item.setIcon("Icons/icon_magazaine.gif");
			}
		}
	}

	public static Mapx initDialog(Mapx params) {
		Object o1 = params.get("MagazineID");
		if (o1 != null) {
			long ID = Long.parseLong(o1.toString());
			ZCMagazineSchema magazine = new ZCMagazineSchema();
			magazine.setID(ID);
			if (magazine.fill()) {
				Mapx map = magazine.toMapx();
				String imagePath = magazine.getCoverImage();
				if (imagePath == null) {
					imagePath = "../Images/nopicture.jpg";
				} else {
					imagePath = Config.getContextPath() + Config.getValue("UploadDir")+"/"
							+ SiteUtil.getAlias(magazine.getSiteID()) + "/" + imagePath;
				}

				map.put("PicSrc", imagePath);
				return map;
			}
			return null;
		} else {
			params.put("SiteID", Application.getCurrentSiteID()+"");
			params.put("PicSrc", "../Images/nocover.jpg");
			params.put("CoverTemplate", "/template/magazine.html");
			return params;
		}
	}

	public static Mapx initIssue(Mapx params) {
		String sql = "select concat(year,'年',periodNum,'期') as Name,ID from zcmagazineissue"
				+ " where MagazineID=(select min(id) from zcmagazine where siteid=" + Application.getCurrentSiteID()
				+ ") order by id desc";
		DataTable dt1 = new QueryBuilder(sql).executeDataTable();
		params.put("optionIssue", HtmlUtil.dataTableToOptions(dt1));
		return params;
	}

	public static Mapx init(Mapx params) {
		Object o1 = params.get("MagazineID");
		if (o1 != null) {
			long ID = Long.parseLong(o1.toString());
			ZCMagazineSchema magazine = new ZCMagazineSchema();
			magazine.setID(ID);
			magazine.fill();
			
		    Mapx map = magazine.toMapx();
				return map;
		}
		return params;
	}

	// 添加期刊，在期刊同级添加一个栏目
	public void add() {
		String siteID=$V("SiteID");
		String magazineID = $V("MagazineID");
		long parentID=Long.parseLong(CatalogUtil.getParentID(magazineID));
		Transaction trans = new Transaction();

		Catalog catalog = new Catalog();
		String existsName=Catalog.checkAliasExists($V("Alias"),parentID);
		if (Catalog.checkNameExists($V("Name"), parentID)) {
			Response.setMessage("期刊库下已经存在该期刊！");
			Response.setStatus(0);
			return ;
		}
		String magazineName=new QueryBuilder("select name from zcmagazine where alias=? and siteid=?",$V("Alias"),siteID).executeString();
		if (StringUtil.isNotEmpty(existsName)) {
			Response.setMessage("期刊“"+magazineName+"”已经使用了别名“"+$V("Alias")+"”");
			Response.setStatus(0);
			return;
		}
		ZCCatalogSchema scheme = catalog.add(Request, trans);
		long catalogID = scheme.getID();
				
		Request.put("IndexTemplate", $V("CoverTemplate"));//封面模板为期刊的首页
		ZCMagazineSchema magazine = new ZCMagazineSchema();
		magazine.setID(catalogID);
		magazine.setValue(Request);
		String imageid=$V("CoverImage");
		DataTable imagedt = null;
		if(StringUtil.isEmpty(imageid)) {
			imagedt = new QueryBuilder("select path,filename from zcimage where id=0").executeDataTable();
		} else {
			imagedt = new QueryBuilder("select path,filename from zcimage where id=?", Long.parseLong(imageid)).executeDataTable();
		}
		if(imagedt.getRowCount()>0){
			magazine.setCoverImage(imagedt.getString(0, "path")+"1_"+imagedt.getString(0, "filename"));
		}
		magazine.setAddTime(new Date());
		magazine.setAddUser(User.getUserName());
		
		trans.add(magazine, Transaction.INSERT);
		if (trans.commit()) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("插入数据发生错误!");
		}
	}

	public void edit() {
		Transaction trans = new Transaction();
		ZCMagazineSchema magazine = new ZCMagazineSchema();
		magazine.setID(Long.parseLong($V("MagazineID")));
		if (!magazine.fill()) {
			Response.setStatus(0);
			Response.setMessage("修改数据发生错误!");
			return;
		}
		magazine.setValue(Request);
		String imageid=$V("CoverImage");
		DataTable imagedt=new QueryBuilder("select path,filename from zcimage where id=?",imageid).executeDataTable();
		if(imagedt.getRowCount()>0){
			magazine.setCoverImage(imagedt.getString(0, "path")+"1_"+imagedt.getString(0, "filename"));
		}
		magazine.setModifyTime(new Date());
		magazine.setModifyUser(User.getUserName());
		trans.add(magazine, Transaction.UPDATE);

		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(Long.parseLong($V("MagazineID")));
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
			Response.setMessage("修改数据发生错误!");
		}
	}

	public void del() {
		Transaction trans = new Transaction();
		String ID = $V("CatalogID");

		// 删除相关栏目
		Catalog.deleteCatalog(trans, Long.parseLong(ID));

		ZCMagazineSchema magazine = new ZCMagazineSchema();
		magazine.setID(Long.parseLong(ID));
		magazine.fill();

		// 期刊
		trans.add(magazine, Transaction.DELETE_AND_BACKUP);
		// 期刊期数
		trans.add(new ZCMagazineIssueSchema().query(new QueryBuilder(" where magazineID =?",ID)), Transaction.DELETE_AND_BACKUP);
		// 期刊栏目关联
		trans.add(new ZCMagazineCatalogRelaSchema().query(new QueryBuilder(" where magazineID =?",ID)), Transaction.DELETE_AND_BACKUP);

		if (trans.commit()) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("删除期刊失败");
		}
	}
	
	
	public void publish() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("操作数据库时发生错误!");
			return;
		}
		if (ids.indexOf("\"") >= 0 || ids.indexOf("\'") >= 0) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}

		long id;
		
		String[] arr=ids.split(",");
		
		for(int i=0;i<arr.length;i++){
			String temp=arr[i];
			long catalogID=Long.parseLong(temp);
		    id = publishTask(catalogID, true, true);
			Response.setStatus(1);
			$S("TaskID", "" + id);
		}
	}

	private long publishTask(final long catalogID, final boolean child, final boolean detail) {
		LongTimeTask ltt = new LongTimeTask() {
			public void execute() {
				Publisher p = new Publisher();
				p.publishCatalog(catalogID, child, detail, this);
				setPercent(100);
			}
		};
		ltt.setUser(User.getCurrent());
		ltt.start();
		return ltt.getTaskID();
	}

	
	
	
	

}
