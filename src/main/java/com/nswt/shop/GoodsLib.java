package com.nswt.shop;

import java.util.Date;

import com.nswt.cms.datachannel.Publisher;
import com.nswt.cms.dataservice.ColumnUtil;
import com.nswt.cms.document.Article;
import com.nswt.cms.pub.CatalogUtil;
import com.nswt.cms.pub.SiteUtil;
import com.nswt.cms.site.Catalog;
import com.nswt.framework.Config;
import com.nswt.framework.Page;
import com.nswt.framework.User;
import com.nswt.framework.controls.DataGridAction;
import com.nswt.framework.controls.TreeAction;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.messages.LongTimeTask;
import com.nswt.framework.utility.DateUtil;
import com.nswt.framework.utility.Errorx;
import com.nswt.framework.utility.HtmlUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.Application;
import com.nswt.platform.UserLog;
import com.nswt.platform.pub.NoUtil;
import com.nswt.platform.pub.OrderUtil;
import com.nswt.schema.ZCCatalogSchema;
import com.nswt.schema.ZCCatalogSet;
import com.nswt.schema.ZCImageSchema;
import com.nswt.schema.ZCSiteSchema;
import com.nswt.schema.ZSGoodsSchema;
import com.nswt.schema.ZSGoodsSet;

/**
 * @Author wyli
 * @Date 2016-01-21
 * @Mail lwy@nswt.com
 */
public class GoodsLib extends Page {
	public static Mapx initDialog(Mapx params) {
		params.put("goodsParentID", HtmlUtil.dataTableToOptions(CatalogUtil.getCatalogOptions((Catalog.SHOP_GOODS))));
		// params.put("brandParentID",
		// HtmlUtil.dataTableToOptions(CatalogUtil.getCatalogOptions((Catalog.SHOP_GOODS_BRAND),true));
		String catalogID = params.getString("CatalogID");
		params.put("CustomColumn", ColumnUtil.getHtml(ColumnUtil.RELA_TYPE_CATALOG_COLUMN, catalogID));
		return params;
	}

	public static Mapx initEditDialog(Mapx params) {
		ZSGoodsSchema goods = new ZSGoodsSchema();
		goods.setID(params.getString("ID"));
		goods.fill();
		Mapx map = goods.toMapx();
		String catalogID = goods.getCatalogID() + "";
		// �����Զ����ֶ�
		map.put("CustomColumn", ColumnUtil.getHtml(ColumnUtil.RELA_TYPE_CATALOG_COLUMN, catalogID,
				ColumnUtil.RELA_TYPE_DOCID, goods.getID() + ""));
		return map;
	}

	// �õ���Ʒ����ϸ��Ϣ
	public static Mapx initGoodsInfo(Mapx params) {
		ZSGoodsSchema goods = new ZSGoodsSchema();
		goods.setID(params.getString("ID"));
		goods.fill();
		Mapx map = goods.toMapx();
		map.put("imgpath", (Config.getContextPath() + Config.getValue("UploadDir") + "/"
				+ SiteUtil.getAlias(Application.getCurrentSiteID()) + "/" + goods.getImage0()).replaceAll("//", "/"));

		String catalogID = goods.getCatalogID() + "";
		String sql = null;
		// �õ���Ʒ����
		sql = "select name from zccatalog where id=?";
		String catalogName = new QueryBuilder(sql, goods.getCatalogID()).executeString();
		map.put("CatalogName", catalogName == null ? "û����������" : catalogName);
		// �õ���Ʒ����Ʒ��
		sql = "select name from zccatalog where exists (select 1 from zcdocrela where docid=? and catalogType=? and catalogid=zccatalog.id)";
		String brandName = new QueryBuilder(sql, goods.getID(), Catalog.SHOP_GOODS_BRAND).executeString();
		map.put("BrandCatalogName", brandName == null ? "û������Ʒ��" : brandName);
		// �����Զ����ֶ�
		map.put("CustomColumn", ColumnUtil.getText(ColumnUtil.RELA_TYPE_CATALOG_COLUMN, catalogID,
				ColumnUtil.RELA_TYPE_DOCID, goods.getID() + ""));
		return map;
	}

	public static Mapx initEditLibDialog(Mapx params) {
		long ID = Long.parseLong(params.get("ID").toString());
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(ID);
		catalog.fill();
		return catalog.toMapx();
	}

	public static void dg1DataBind(DataGridAction dga) {
		String CatalogID = dga.getParam("CatalogID");
		String SearchContent = dga.getParam("SearchContent");

		QueryBuilder qb = new QueryBuilder(
				"select zsgoods.*,(select name from zccatalog c where c.id=zsgoods.catalogid) as CatalogIDName from zsgoods  where ");
		if (StringUtil.isEmpty(CatalogID)) {
			qb.append(" SiteID = ?", Application.getCurrentSiteID());
		} else {
			qb.append(" CatalogID = ?", CatalogID);
		}
		if (StringUtil.isNotEmpty(SearchContent)) {
			qb.append(" and Name like ?", "%" + SearchContent.trim() + "%");
		}
		qb.append(" order by OrderFlag desc");
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dt.decodeColumn("Status", Article.STATUS_MAP);
		if (dt != null && dt.getRowCount() > 0) {
			dt.getDataColumn("PublishDate").setDateFormat(DateUtil.Format_Date);
		}
		ColumnUtil.extendCatalogColumnData(dt, CatalogID);
		dga.bindData(dt);
	}

	public void setImageCover() {
		ZCImageSchema image = new ZCImageSchema();
		image.setID($V("ID"));
		image.fill();
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(image.getCatalogID());
		catalog.fill();
		// catalog.setImageID($V("ID"));
		if (catalog.update()) {
			this.Response.setLogInfo(1, "����ר������ɹ���");
		} else {
			this.Response.setLogInfo(0, "����ר������ʧ�ܣ�");
		}
	}

	public void GoodsLibEdit() {
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setValue(this.Request);
		catalog.fill();
		catalog.setValue(this.Request);
		if (catalog.update()) {
			this.Response.setLogInfo(1, "�޸ķ���ɹ���");
		} else {
			this.Response.setLogInfo(0, "�޸ķ���ʧ�ܣ�");
		}
	}

	public void addLib() {
		String name = $V("Name");
		String parentID = $V("ParentID");
		String DT = $V("DetailTemplate");
		String LT = $V("ListTemplate");
		Transaction trans = new Transaction();
		ZCCatalogSchema catalog = new ZCCatalogSchema();

		catalog.setID(NoUtil.getMaxID("CatalogID"));
		catalog.setSiteID(Application.getCurrentSiteID());
		// parentID Ϊ0��ʾ�ϼ��ڵ���վ��
		if (StringUtil.isEmpty(parentID)) {
			catalog.setParentID(0);
			catalog.setInnerCode(NoUtil.getMaxNo("InnerCode", 4));
			catalog.setTreeLevel(1);
			ZCSiteSchema site = new ZCSiteSchema();
			site.setID(catalog.getSiteID());
			site.fill();
			site.setImageLibCount(site.getImageLibCount() + 1);
			trans.add(site, Transaction.UPDATE);
		} else {
			catalog.setParentID(Long.parseLong(parentID));
			ZCCatalogSchema pCatalog = new ZCCatalogSchema();
			pCatalog.setID(catalog.getParentID());
			pCatalog.fill();

			catalog.setInnerCode(NoUtil.getMaxNo("InnerCode", pCatalog.getInnerCode(), 4));
			catalog.setTreeLevel(pCatalog.getTreeLevel() + 1);

			pCatalog.setChildCount(pCatalog.getChildCount() + 1);
			trans.add(pCatalog, Transaction.UPDATE);
		}

		catalog.setName(name);
		catalog.setURL(" ");
		catalog.setAlias(StringUtil.getChineseFirstAlpha(name));
		catalog.setType(Catalog.SHOP_GOODS);
		catalog.setListTemplate(LT);
		catalog.setListNameRule("");
		catalog.setDetailTemplate(DT);
		catalog.setDetailNameRule("");
		catalog.setChildCount(0);
		catalog.setIsLeaf(1);
		catalog.setTotal(0);
		catalog.setOrderFlag(OrderUtil.getDefaultOrder());
		catalog.setLogo("");
		catalog.setListPageSize(10);
		catalog.setListPage(10);// ----
		catalog.setPublishFlag("Y");
		catalog.setHitCount(0);
		catalog.setMeta_Keywords("");
		catalog.setMeta_Description("");
		catalog.setOrderColumn("");
		catalog.setAddUser(User.getUserName());
		catalog.setAddTime(new Date());

		trans.add(catalog, Transaction.INSERT);
		if (trans.commit()) {
			this.Response.setLogInfo(1, "�½�����ɹ�!!!");
		} else {
			this.Response.setLogInfo(0, "�½�����ʧ��!!!");
		}
	}

	public void delLib() {
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		String catalogId = $V("catalogID");
		catalog.setID(catalogId);
		if (!catalog.fill()) {
			this.Response.setLogInfo(0, "û��������࣡");
			return;
		}
		// �õ���Ŀ�������е���Ʒ
		ZSGoodsSet goodsSet = new ZSGoodsSchema().query(new QueryBuilder("where CatalogInnerCode like '"
				+ catalog.getInnerCode() + "%'"));
		System.err.println(goodsSet.size());
		if (goodsSet.size() != 0) {
			this.Response.setLogInfo(1, "�÷�����������Ʒ������ɾ����Ʒ��");
			return;
		} else {
			ZCCatalogSet catalogSet = new ZCCatalogSchema().query(new QueryBuilder("where InnerCode like '"
					+ catalog.getInnerCode() + "%'"));
			Transaction trans = new Transaction();
			trans.add(catalogSet, Transaction.DELETE);
			if (trans.commit()) {
				this.Response.setLogInfo(1, "ɾ���÷���ɹ���");
			} else {
				this.Response.setLogInfo(0, "ɾ���÷���ʧ�ܣ�������!");
			}
		}
	}

	public void transfer() {
		String IDs = $V("IDs");
		if (!StringUtil.checkID(IDs)) {
			Response.setStatus(0);
			Response.setMessage("����IDʱ��������!");
			return;
		}
		long catalogID = Long.parseLong($V("CatalogID"));
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(catalogID);
		if (!catalog.fill()) {
			Response.setLogInfo(0, "The Catalog you selected is not existsed!");
			return;
		}
		Transaction trans = new Transaction();
		QueryBuilder qb = new QueryBuilder(
				"update zsgoods set CatalogID = ?,CatalogInnerCode = ?,BranchCode = ?,BranchInnerCode = ?,SiteID = ? where ID in ("
						+ IDs + ")");
		qb.add(catalogID);
		qb.add(catalog.getInnerCode());
		// qb.add(catalog.getBranchCode());
		qb.add(catalog.getBranchInnerCode());
		qb.add(Application.getCurrentSiteID());
		trans.add(qb);
		if (trans.commit()) {
			Response.setLogInfo(1, "ת�Ƴɹ�");
		} else {
			Response.setLogInfo(0, "ת��ʧ��");
		}
	}

	public static void treeDataBind(TreeAction ta) {
		long SiteID = Application.getCurrentSiteID();
		DataTable dt = null;
		Mapx params = ta.getParams();
		String parentLevel = params.getString("ParentLevel");
		String parentID = params.getString("ParentID");

		String IDs = ta.getParam("IDs");
		if (StringUtil.isEmpty(IDs)) {
			IDs = ta.getParam("Cookie.Resource.LastVideoLib");
		}
		String[] codes = Catalog.getSelectedCatalogList(IDs, ta.getLevel());
		if (ta.isLazyLoad()) {
			QueryBuilder qb = new QueryBuilder(
					"select ID,ParentID,TreeLevel,Name,prop1 from ZCCatalog Where Type =? and SiteID =? and TreeLevel>? and innerCode like ? order by orderflag,innercode");
			qb.add(Catalog.SHOP_GOODS);
			qb.add(SiteID);
			qb.add(parentLevel);
			qb.add(CatalogUtil.getInnerCode(parentID) + "%");
			dt = qb.executeDataTable();
		} else {
			QueryBuilder qb = new QueryBuilder(
					"select ID,ParentID,TreeLevel,Name,prop1 from ZCCatalog Where Type = ? and SiteID =? and TreeLevel-1 <=?  order by orderflag,innercode");
			qb.add(Catalog.SHOP_GOODS);
			qb.add(SiteID);
			qb.add(ta.getLevel());
			dt = qb.executeDataTable();
			Catalog.prepareSelectedCatalogData(dt, codes, Catalog.SHOP_GOODS, SiteID, ta.getLevel());
		}
		ta.setRootText("��Ʒ��");
		ta.bindData(dt);
		Catalog.addSelectedBranches(ta, codes);
	}

	public void publish() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			UserLog.log("Goods", "Publish_Goods", "��Ʒ����ʧ��", Request.getClientIP());
			Response.setStatus(0);
			Response.setMessage("�������ݿ�ʱ��������!");
			return;
		}
		ZSGoodsSchema goods = new ZSGoodsSchema();
		ZSGoodsSet set = goods.query(new QueryBuilder("where id in(" + ids + ")"));
		StringBuffer logs = new StringBuffer("������Ʒ:");
		for (int i = 0; i < set.size(); i++) {
			logs.append(set.get(i).getName() + ",");
		}
		UserLog.log("Goods", "Publish_Goods", logs + "�ɹ�", Request.getClientIP());

		Response.setStatus(1);
		long id = publishSetTask(set);
		$S("TaskID", "" + id);
	}

	private long publishSetTask(final ZSGoodsSet set) {
		LongTimeTask ltt = new LongTimeTask() {
			public void execute() {
				Publisher p = new Publisher();
				p.publishGoods(set, this);
				setPercent(100);
			}
		};
		ltt.setUser(User.getCurrent());
		ltt.start();
		return ltt.getTaskID();
	}

	/**
	 * ��Ʒ����
	 */
	public void down() {
		String ids = $V("GoodsIDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("����Ĳ���!");
			return;
		}
		Date now = new Date();
		Transaction trans = new Transaction();
		// dealGoodsHistory(ids, trans, "DOWN", "���ߴ���");
		trans.add(new QueryBuilder("update zsgoods set Status = " + Article.STATUS_OFFLINE
				+ ",TopFlag='0',DownlineDate = ?,modifyTime=? where id in(" + ids + ")", now, now));
		if (trans.commit()) {
			ZSGoodsSchema site = new ZSGoodsSchema();
			ZSGoodsSet set = site.query(new QueryBuilder("where id in (" + ids + ")"));
			downTask(set);
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
		}
	}

	private long downTask(final ZSGoodsSet set) {
		LongTimeTask ltt = new LongTimeTask() {
			public void execute() {
				Publisher p = new Publisher();
				if (set != null && set.size() > 0) {
					p.deletePubishedFile(set);

					Mapx catalogMap = new Mapx();
					for (int k = 0; k < set.size(); k++) {
						catalogMap.put(set.get(k).getCatalogID() + "", set.get(k).getCatalogID() + "");
						String pid = CatalogUtil.getParentID(set.get(k).getCatalogID());
						while (StringUtil.isNotEmpty(pid) && !"null".equals(pid) && !"0".equals(pid)) {
							catalogMap.put(pid, pid);
							pid = CatalogUtil.getParentID(pid);
						}
					}

					// ���ɱ�����Ŀ
					Object[] vs = catalogMap.valueArray();
					for (int j = 0; j < catalogMap.size(); j++) {
						String listpage = CatalogUtil.getData(vs[j].toString()).getString("ListPage");
						if (StringUtil.isEmpty(listpage) || Integer.parseInt(listpage)<1) {
							listpage = "20"; // Ĭ��ֻ����ǰ��ʮҳ
						}
						p.publishCatalog(Long.parseLong(vs[j].toString()), false, false, Integer.parseInt(listpage));
						setPercent(getPercent() + 5);
						setCurrentInfo("������Ŀҳ��");
					}
				}
				setPercent(100);
			}
		};
		ltt.setUser(User.getCurrent());
		ltt.start();
		return ltt.getTaskID();
	}

	/**
	 * ��Ʒ����
	 */
	public void up() {
		String ids = $V("GoodsIDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("����Ĳ���!");
			return;
		}
		Date now = new Date();
		Transaction trans = new Transaction();
		// dealArticleHistory(ids, trans, "UP", "���ߴ���");
		trans.add(new QueryBuilder("update zsgoods set Status =" + Article.STATUS_PUBLISHED
				+ ",PublishDate = ?,DownlineDate = '2999-12-31 23:59:59' where id in(" + ids + ")", now));
		if (trans.commit()) {
			upTask(ids);
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
		}
	}

	private long upTask(final String ids) {
		LongTimeTask ltt = new LongTimeTask() {
			public void execute() {
				Publisher p = new Publisher();
				ZSGoodsSchema site = new ZSGoodsSchema();
				ZSGoodsSet set = site.query(new QueryBuilder("where id in (" + ids + ")"));
				if (set != null && set.size() > 0) {
					p.publishGoods(set, false, this);
					p.publishCatalog(set.get(0).getCatalogID(), false, false);
					setPercent(100);
				}
			}
		};
		ltt.setUser(User.getCurrent());
		ltt.start();
		return ltt.getTaskID();
	}

	public static void treeDiagDataBind(TreeAction ta) {
		Object obj = ta.getParams().get("SiteID");
		long siteID = (obj != null) ? Long.parseLong(obj.toString()) : Application.getCurrentSiteID();
		Object typeObj = ta.getParams().get("CatalogType");
		int catalogType = (typeObj != null) ? Integer.parseInt(typeObj.toString()) : Catalog.SHOP_GOODS;
		String parentTreeLevel = ta.getParams().getString("ParentLevel");
		String parentID = ta.getParams().getString("ParentID");

		String IDs = ta.getParam("IDs");
		if (StringUtil.isEmpty(IDs)) {
			IDs = ta.getParam("Cookie.Resource.LastVideoLib");
		}
		String[] codes = Catalog.getSelectedCatalogList(IDs, ta.getLevel());

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
			Catalog.prepareSelectedCatalogData(dt, codes, Catalog.SHOP_GOODS, siteID, ta.getLevel());
		}
		ta.setRootText("��Ʒ��");

		String siteName = "APPӦ��";
		if (catalogType == Catalog.SHOP_GOODS) {
			siteName = "��Ʒ��";
		}
		if (catalogType == Catalog.SHOP_GOODS_BRAND) {
			siteName = "��ƷƷ�ƿ�";
		}
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
		// dt = dt.filter(new Filter() {
		// public boolean filter(Object obj) {
		// DataRow dr = (DataRow) obj;
		// return Priv.getPriv(User.getUserName(), Priv.ARTICLE,
		// dr.getString("InnerCode"), Priv.ARTICLE_MANAGE);
		// }
		// });
		ta.bindData(dt);
		Catalog.addSelectedBranches(ta, codes);
	}

	/**
	 * ת����Ʒ
	 */
	public void move() {
		String goodsIDs = $V("GoodsIDs");
		if (!StringUtil.checkID(goodsIDs)) {
			Response.setError("�������ݿ�ʱ��������!");
			return;
		}

		String catalogID = $V("CatalogID");
		if (!StringUtil.checkID(catalogID)) {
			Response.setError("����CatalogIDʱ��������!");
			return;
		}

		Transaction trans = new Transaction();
		ZSGoodsSchema srcGoods = new ZSGoodsSchema();
		ZSGoodsSet set = srcGoods.query(new QueryBuilder("where id in (" + goodsIDs + ")"));
		long srcCatalogID = 0;

		String[] srcGoodsIDs = null;
		if (set.size() > 0) {
			srcGoodsIDs = new String[set.size()];
			for (int i = 0; i < set.size(); i++) {
				srcGoodsIDs[i] = String.valueOf(set.get(i).getID());
			}
		}
		for (int i = 0; i < set.size(); i++) {
			ZSGoodsSchema goods = set.get(i);
			srcCatalogID = goods.getCatalogID();
			String destCatalogID = catalogID;
			trans.add(new QueryBuilder("update zccatalog set total = total+1 where id=?", destCatalogID));
			trans.add(new QueryBuilder("update zccatalog set total = total-1 where id=?", srcCatalogID));
			goods.setCatalogInnerCode(CatalogUtil.getInnerCode(catalogID));
			goods.setCatalogID(catalogID);
			goods.setOrderFlag(OrderUtil.getDefaultOrder());
			trans.add(goods, Transaction.UPDATE);
		}

		if (trans.commit()) {
			Publisher p = new Publisher();
			// ɾ��ԭ������
			p.deletePubishedFile(set);
			// �������������£���������ԭ��Ŀ¼���б�
			if (p.publishGoods(set, true, null) && p.publishCatalog(srcCatalogID, false, false)) {
				Response.setMessage("ת�Ƴɹ�");
			} else {
				Response.setError("ת�Ƴɹ�,����ʧ�ܡ�" + Errorx.printString());
			}
		} else {
			Response.setError("�������ݿ�ʱ��������!");
		}
	}

	public void copy() {
		String catalogIDs = $V("CatalogIDs");
		ZCCatalogSet catalogs = new ZCCatalogSchema().query(new QueryBuilder("where ID in (" + catalogIDs + ")"));
		for (int i = 0; i < catalogs.size(); i++) {
			String IDs = $V("GoodsIDs");
			if (!StringUtil.checkID(IDs)) {
				Response.setStatus(0);
				Response.setMessage("����IDʱ��������!");
				return;
			}
			ZSGoodsSet goodsSet = new ZSGoodsSchema().query(new QueryBuilder(" where ID in (" + IDs + ")"));
			for (int j = 0; j < goodsSet.size(); j++) {
				ZSGoodsSchema goods = goodsSet.get(j);
				goods.setID(NoUtil.getMaxID("ID"));
				goods.setCatalogID(catalogs.get(i).getID());
				goods.setCatalogInnerCode(catalogs.get(i).getInnerCode());
				goods.setBranchInnerCode(catalogs.get(i).getBranchInnerCode());
				goods.setSiteID(Application.getCurrentSiteID());
				goods.setAddTime(new Date());
				goods.setAddUser(User.getUserName());
			}
			if (goodsSet.insert()) {
				this.Response.setLogInfo(1, "���Ƴɹ�");
			} else {
				this.Response.setLogInfo(0, "����ʧ��");
			}
		}
	}

	public static void dg2DataBind(DataGridAction dga) {
		String catalogID = (String) dga.getParams().get("CatalogID");
		if (StringUtil.isEmpty(catalogID)) {
			catalogID = "0";
			dga.getParams().put("CatalogID", catalogID);
		}

		QueryBuilder qb = new QueryBuilder("select * from ZSGoods where CatalogID=?", catalogID);

		qb.append(dga.getSortString());
		if (StringUtil.isNotEmpty(dga.getSortString())) {
			qb.append(" ,orderflag desc");
		} else {
			qb.append(" order by topflag desc,orderflag desc");
		}
		dga.setTotal(qb);

		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		if (dt != null && dt.getRowCount() > 0) {
			dt.decodeColumn("Status", Article.STATUS_MAP);
			dt.getDataColumn("PublishDate").setDateFormat("yy-MM-dd");
		}

		dga.bindData(dt);
	}

	/**
	 * �����½�������,����Ĭ������ֵ��ʱ���
	 */
	public void sortGoods() {
		String target = $V("Target");
		String orders = $V("Orders");
		String type = $V("Type");
		String catalogID = $V("CatalogID");
		if (!StringUtil.checkID(target) && !StringUtil.checkID(orders)) {
			return;
		}
		Transaction tran = new Transaction();

		OrderUtil.updateOrder("ZSGoods", "OrderFlag", type, target, orders, null, tran);
		if (tran.commit()) {
			final String id = catalogID;
			LongTimeTask ltt = new LongTimeTask() {
				public void execute() {
					Publisher p = new Publisher();
					String listpage = CatalogUtil.getData(id).getString("ListPage");
					if (StringUtil.isEmpty(listpage) || Integer.parseInt(listpage)<1) {
						listpage = "20"; // Ĭ��ֻ����ǰ��ʮҳ
					}
					p.publishCatalog(Long.parseLong(id), false, false, Integer.parseInt(listpage));
					setPercent(100);
				}
			};
			ltt.setUser(User.getCurrent());
			ltt.start();

			Response.setMessage("�����ɹ�");
		} else {
			Response.setError("����ʧ��");
		}
	}

	/*
	 * public void cancellTopper(){ String IDs = $V("IDs"); String typeTop =
	 * $V("typeTop"); String[] ids = IDs.split(","); Transaction trans = new
	 * Transaction(); if ("Special".equalsIgnoreCase(typeTop)) { for (int i = 0;
	 * i < ids.length; i++) { ZSGoodsSchema goods = new ZSGoodsSchema();
	 * goods.setID(ids[i]); if(goods.fill()){ goods.setIsSpecial("N");
	 * trans.add(goods, Transaction.UPDATE); } } } if
	 * ("Hot".equalsIgnoreCase(typeTop)) { for (int i = 0; i < ids.length; i++)
	 * { ZSGoodsSchema goods = new ZSGoodsSchema(); goods.setID(ids[i]);
	 * if(goods.fill()){ goods.setIsHot("N"); trans.add(goods,
	 * Transaction.UPDATE); } } } if ("New".equalsIgnoreCase(typeTop)) { for
	 * (int i = 0; i < ids.length; i++) { ZSGoodsSchema goods = new
	 * ZSGoodsSchema(); goods.setID(ids[i]); if(goods.fill()){
	 * goods.setIsNew("N"); trans.add(goods, Transaction.UPDATE); } } } if
	 * ("Month".equalsIgnoreCase(typeTop)) { for (int i = 0; i < ids.length;
	 * i++) { ZSGoodsSchema goods = new ZSGoodsSchema(); goods.setID(ids[i]);
	 * if(goods.fill()){ goods.setIsSaleMonth("N"); trans.add(goods,
	 * Transaction.UPDATE); } } } if ("Week".equalsIgnoreCase(typeTop)) { for
	 * (int i = 0; i < ids.length; i++) { ZSGoodsSchema goods = new
	 * ZSGoodsSchema(); goods.setID(ids[i]); if(goods.fill()){
	 * goods.setIsSaleWeek("N"); trans.add(goods, Transaction.UPDATE); } } } if
	 * (trans.commit()) { Response.setLogInfo(1,"���óɹ�"); } else {
	 * Response.setLogInfo(0,"����ʧ��"); } }
	 */
}
