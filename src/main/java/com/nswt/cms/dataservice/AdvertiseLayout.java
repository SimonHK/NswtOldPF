package com.nswt.cms.dataservice;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.nswt.cms.pub.CatalogUtil;
import com.nswt.cms.pub.SiteUtil;
import com.nswt.cms.site.Catalog;
import com.nswt.framework.Config;
import com.nswt.framework.Page;
import com.nswt.framework.User;
import com.nswt.framework.controls.DataGridAction;
import com.nswt.framework.controls.TreeAction;
import com.nswt.framework.controls.TreeItem;
import com.nswt.framework.data.DataRow;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.FileUtil;
import com.nswt.framework.utility.Filter;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.Application;
import com.nswt.platform.Priv;
import com.nswt.platform.pub.NoUtil;
import com.nswt.schema.ZCAdPositionSchema;
import com.nswt.schema.ZCAdPositionSet;
import com.nswt.schema.ZCAdvertisementSchema;
import com.nswt.schema.ZCAdvertisementSet;

/**
 * @Author �º�ǿ
 * @Edit �솴
 * @Date 2007-8-15
 * @Mail chq@nswt.com
 */

public class AdvertiseLayout extends Page {

	private static ArrayList linnerCodeList = new ArrayList();
	
	public static Mapx PosTypes = new Mapx();
	static {
		PosTypes.put("banner", "���κ��");
		PosTypes.put("fixure", "�̶�λ��");
		PosTypes.put("float", "Ư���ƶ�");
		PosTypes.put("couplet", "�������");
		PosTypes.put("imagechange", "ͼƬ�ֻ����");
		PosTypes.put("imagelist", "ͼƬ�б���");
		PosTypes.put("text", "���ֹ��");
		PosTypes.put("code", "�������");
	}

	public static void dg1DataBind(DataGridAction dga) {
		String RelaCatalogID = dga.getParam("CatalogID");
		if (StringUtil.isEmpty(RelaCatalogID) || RelaCatalogID == null || RelaCatalogID.equalsIgnoreCase("null")) {
			RelaCatalogID = "0";
		}
		String SearchContent = (String) dga.getParam("SearchContent");
		QueryBuilder qb = new QueryBuilder(
				"select a.id id,a.SiteID SiteID,a.PositionName PositionName,a.PositionType PositionType,a.Description Description,a.RelaCatalogID RelaCatalogID,"
						+ "'' as AdType,a.JSName,a.PositionWidth PositionSizeWidth,"
						+ "a.PositionHeight PositionSizeHeight,(SELECT Name from ZCCatalog where ZCCatalog.ID = a.RelaCatalogID) as CatalogName from zcadposition a ");
		qb.append(" where SiteID=?", Application.getCurrentSiteID());
		if (StringUtil.isNotEmpty(SearchContent)) {
			qb.append(" and PositionName like ?", "%" + SearchContent.trim() + "%");
		}
		if (!RelaCatalogID.equals("0")) {
			qb.append(" and RelaCatalogID=?  order by a.id desc", Long.parseLong(RelaCatalogID));
		} else {
			qb.append("  order by a.RelaCatalogID asc");
		}
		DataTable dt = qb.executeDataTable();
		dt = dt.filter(new Filter() {
			public boolean filter(Object obj) {
				if (Priv.getPriv(User.getUserName(), Priv.SITE, Application.getCurrentSiteID() + "", Priv.SITE_MANAGE)) {
					return true;
				}
				DataRow dr = (DataRow) obj;
				String RelaCatalogID = dr.getString("RelaCatalogID");
				if ("0".equals(RelaCatalogID)) {
					return Priv.getPriv(User.getUserName(), Priv.SITE, Application.getCurrentSiteID() + "",
							Priv.ARTICLE_MANAGE);
				} else {
					return Priv.getPriv(User.getUserName(), Priv.ARTICLE, CatalogUtil.getInnerCode(RelaCatalogID),
							Priv.ARTICLE_MODIFY);
				}
			}
		});
		DataTable newdt = new DataTable(dt.getDataColumns(), null);
		for (int i = dga.getPageIndex() * dga.getPageSize(); i < dt.getRowCount()
				&& i < (dga.getPageIndex() + 1) * dga.getPageSize(); i++) {
			newdt.insertRow(dt.getDataRow(i));
		}
		for (int i = 0; i < newdt.getRowCount(); i++) {
			if (StringUtil.isEmpty(newdt.getString(i, "CatalogName"))) {
				newdt.set(i, "CatalogName", "APPӦ��");
			}
			String AdType = new QueryBuilder("select AdType from zcadvertisement  where positionid=? and isopen='Y'",
					dt.getLong(i, "ID")).executeOneValue()
					+ "";
			if (AdType.equalsIgnoreCase("null")) {
				AdType = "";
			}
			newdt.set(i, "ADType", AdType);
		}
		newdt.decodeColumn("ADType", Advertise.ADTypes);
		newdt.decodeColumn("PositionType", PosTypes);
		dga.setTotal(dt.getRowCount());
		dga.bindData(newdt);
	}

	public static void treeDataBind(TreeAction ta) {
		linnerCodeList = new ArrayList();
		long siteID = Application.getCurrentSiteID();
		Object typeObj = ta.getParams().get("CatalogType");
		int catalogType = (typeObj != null) ? Integer.parseInt(typeObj.toString()) : Catalog.CATALOG;
		String parentTreeLevel = ta.getParams().getString("ParentLevel");
		String parentID = ta.getParams().getString("ParentID");
		DataTable dt = null;
		if (ta.isLazyLoad()) {
			QueryBuilder qb = new QueryBuilder(
					"select ID,ParentID,TreeLevel,Name,SingleFlag,InnerCode,OrderFlag from ZCCatalog"
							+ " Where Type = ? and SiteID = ? and TreeLevel>? and innerCode like ? order by orderflag,innercode");
			qb.add(catalogType);
			qb.add(siteID);
			qb.add(parentTreeLevel);
			qb.add(CatalogUtil.getInnerCode(parentID) + "%");
			dt = qb.executeDataTable();
		} else {
			QueryBuilder qb = new QueryBuilder(
					"select ID,ParentID,TreeLevel,Name,SingleFlag,InnerCode,OrderFlag from ZCCatalog"
							+ " Where Type = ? and SiteID = ? and TreeLevel-1 <=? order by orderflag,innercode");
			qb.add(catalogType);
			qb.add(siteID);
			qb.add(ta.getLevel());
			dt = qb.executeDataTable();
		}
		DataTable relaDt = new QueryBuilder("select RelaCatalogID from ZCAdposition where SiteID = ? and RelaCatalogID != 0",siteID).executeDataTable();
		for (int i = 0; i <relaDt.getRowCount() ; i++) {
			String innerCode = CatalogUtil.getInnerCode(relaDt.getString(i,0));
			if(StringUtil.isNotEmpty(innerCode)){
				innerCode = innerCode.substring(0,6);
				boolean has = false;
				for (int j = 0; j < linnerCodeList.size(); j++) {
					if(linnerCodeList.get(j).equals(innerCode)){
						has = true;
					}
				}
				if(!has){
					linnerCodeList.add(innerCode);
				}
			}
		}
		dt = dt.filter(new Filter() {
			public boolean filter(Object obj) {
				DataRow dr = (DataRow) obj;
				String innerCode = dr.getString("InnerCode");
				boolean flag = false;
				for (int i = 0; i < linnerCodeList.size(); i++) {
					if(innerCode.indexOf(linnerCodeList.get(i)+"")==0){
						flag = true;
						break;
					}
				}
				return flag;
			}
		});
		String siteName = "APPӦ��";
		dt = dt.filter(new Filter() {
			public boolean filter(Object obj) {
				DataRow dr = (DataRow) obj;
				return Priv.getPriv(User.getUserName(), Priv.ARTICLE, dr.getString("InnerCode"), Priv.ARTICLE_BROWSE);
			}
		});
		ta.setRootText(siteName);
		ta.bindData(dt);
		List items = ta.getItemList();
		for (int i = 1; i < items.size(); i++) {
			TreeItem item = (TreeItem) items.get(i);
			if ("Y".equals(item.getData().getString("SingleFlag"))) {
				item.setIcon("Icons/treeicon11.gif");
			}
		}
	}

	public static Mapx DialogInit(Mapx params) {
		String id = (String) params.get("ID");
		// �޸�
		if (StringUtil.isNotEmpty(id)) {
			ZCAdPositionSchema position = new ZCAdPositionSchema();
			position.setID(id);
			position.fill();
			params.putAll(position.toMapx());
			if (position.getPositionType().equals("text")) {
				params.put("PositionWidth", "0");
				params.put("PositionHeight", "0");
			}
		} else {
			params.put("Align", "Y");
			params.put("Scroll", "Y");
		}
		return params;
	}

	public void add() {
		ZCAdPositionSchema position = new ZCAdPositionSchema();
		String id = $V("ID");
		String PositionName = $V("PositionName");
		// �޸�
		if (StringUtil.isNotEmpty(id)) {
			position.setID(id);
			position.fill();
			if (!PositionName.equals(position.getPositionName())) {
				int NameCount = new QueryBuilder("select count(*) from zcadposition where PositionName = ? and SiteID = ?",PositionName,Application.getCurrentSiteID()).executeInt();
				if (NameCount > 0) {
					Response.setLogInfo(0, "�Ѿ���ͬ���İ�λ������������д��λ��");
					return;
				}
			}
			position.setCode(id);
			position.setModifyUser(User.getUserName());
			position.setModifyTime(new Date());
			position.setJsName(createJS("modify", position)); // �޸�js
		} else { // ����
			int NameCount = new QueryBuilder("select count(*) from zcadposition where PositionName = ? and SiteID = ? ", PositionName,Application.getCurrentSiteID()).executeInt();
			if (NameCount > 0) {
				Response.setLogInfo(0, "�Ѿ���ͬ���İ�λ������������д��λ��");
				return;
			}
			position.setID(NoUtil.getMaxID("AdPositionID"));
			position.setCode(position.getID() + "");
			position.setAddUser(User.getUserName());
			position.setAddTime(new Date());
			position.setJsName(createJS("add", position)); // ����js
		}
		position.setSiteID(Application.getCurrentSiteID());
		position.setValue(Request);
		position.setRelaCatalogID($V("RelaCatalogID"));
		if (position.getAlign().equals("Y")) {
			position.setPaddingLeft(0);
			position.setPaddingTop(0);
		}
		if (position.getPositionType().equals("text")) {
			position.setPositionWidth("0");
			position.setPositionHeight("0");
		}

		// ����޸İ�λʱ���������ɹ�棬�����ɹ��jscode
		if (StringUtil.isNotEmpty(id)) {
			ZCAdvertisementSchema adv = new ZCAdvertisementSchema();
			if ($V("hPositionType").equals($V("PositionType"))) {
				ZCAdvertisementSet advset = new ZCAdvertisementSchema().query(new QueryBuilder("where positionid=? and isopen='Y'",Long.parseLong(id)));
				// ���û����ӹ�棬����Ҫ����js
				if (advset.size() != 0) {
					adv = advset.get(0);
					if (!Advertise.CreateJSCode(adv, position)) {
						Response.setStatus(0);
						Response.setMessage("���ɹ��js��������!");
					}
				}
			} else {
				ZCAdvertisementSet advset = new ZCAdvertisementSchema().query(new QueryBuilder("where positionid=?", Long.parseLong(id)));
				if (advset.size() > 0) {
					Transaction trans = new Transaction();
					trans.add(advset, Transaction.DELETE_AND_BACKUP);
					trans.commit();
				}
			}
		}

		// �޸�
		if (StringUtil.isNotEmpty(id)) {
			if (position.update()) {
				Response.setLogInfo(1, "�޸ĳɹ�");
			} else {
				Response.setLogInfo(0, "��������");
			}
		} else { // ����
			if (position.insert()) {
				Response.setLogInfo(1, "�����ɹ�");
			} else {
				Response.setLogInfo(0, "��������");
			}
		}
	}

	public void del() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("����IDʱ��������!");
			return;
		}
		Transaction trans = new Transaction();
		ZCAdPositionSchema ad = new ZCAdPositionSchema();
		ZCAdvertisementSchema adv = new ZCAdvertisementSchema();
		ZCAdPositionSet set = ad.query(new QueryBuilder("where id in (" + ids + ")"));
		ZCAdvertisementSet adSet = adv.query(new QueryBuilder("where PositionID in (" + ids + ")"));
		trans.add(set, Transaction.DELETE);
		trans.add(adSet, Transaction.DELETE);
		if (trans.commit()) {
			for (int i = 0; i < set.size(); i++) {
				ZCAdPositionSchema position = set.get(i);
				String JSPath = Config.getContextRealPath() + Config.getValue("UploadDir") + "/"
						+ SiteUtil.getAlias(position.getSiteID()) + "/" + position.getJsName();
				File file = new File(JSPath);
				if (file.exists()) {
					file.delete();
				}
			}
			Response.setLogInfo(1, "ɾ����λ�ɹ���");
		} else {
			Response.setLogInfo(0, "�������ݿ�ʱ��������!");
		}
	}

	public void copy() {
		String id = $V("ID");
		ZCAdPositionSchema ad = new ZCAdPositionSchema();
		ad.setID(Long.parseLong(id));
		ad.fill();
		String PositionName = ad.getPositionName();
		PositionName = "����  " + PositionName;
		ad.setID(NoUtil.getMaxID("AdPositionID"));
		int count = 0;
		String Code = ad.getCode();
		do {
			Code = "CopyOf" + Code;
			count = new QueryBuilder("select count(*) from zcadposition where Code = ?", Code).executeInt();
		} while (count > 0);
		ad.setCode(Code);
		ad.setAddUser(User.getUserName());
		ad.setAddTime(new Date());
		ad.setJsName(createJS("copy", ad));
		ad.setPositionName(PositionName);
		if (ad.insert()) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("��������!");
		}
	}

	/**
	 * @param type
	 *            =add ������type=modify �޸ģ�type=copy ���� @return js·��
	 */
	public static String createJS(String type, ZCAdPositionSchema adp) {
		String path = Config.getContextRealPath() + Config.getValue("UploadDir") + "/"
				+ Application.getCurrentSiteAlias() + "/js/";
		String oldPath = adp.getJsName();
		File f = new File(path);
		if (!f.exists()) {
			f.mkdir();
		}
		String filename = "";
		// add
		if (type.equalsIgnoreCase("add")) {
			filename = adp.getCode() + ".js";
			path += filename;
			File file = new File(path);
			if (file.exists()) {
				file.delete();
			}
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			path = "js/" + filename;
		} else if (type.equalsIgnoreCase("modify")) { // modify
			if ("".equals(oldPath)) {
				filename = adp.getCode() + ".js";
				path += filename;
				File file = new File(path);
				if (file.exists()) {
					file.delete();
				}
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
				path = "js/" + filename;
			} else {
				path = Config.getContextRealPath() + Config.getValue("UploadDir") + "/"
						+ Application.getCurrentSiteAlias() + "/" + oldPath;
				FileUtil.delete(path);
				File file = new File(path);
				if (!file.exists()) {
					try {
						file.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				path = oldPath;
			}
		} else if (type.equalsIgnoreCase("copy")) { // copy
			filename = adp.getCode() + ".js";
			path += filename;
			File file = new File(path);
			if (file.exists()) {
				file.delete();
			}
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			path = "js/" + filename;
		}
		return path;
	}
}
