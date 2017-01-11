package com.nswt.cms.site;

import java.util.Date;

import com.nswt.cms.datachannel.Deploy;
import com.nswt.cms.pub.CatalogUtil;
import com.nswt.cms.template.PageGenerator;
import com.nswt.framework.Config;
import com.nswt.framework.Page;
import com.nswt.framework.User;
import com.nswt.framework.controls.DataGridAction;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.Errorx;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.Application;
import com.nswt.platform.pub.NoUtil;
import com.nswt.schema.ZCPageBlockItemSchema;
import com.nswt.schema.ZCPageBlockItemSet;
import com.nswt.schema.ZCPageBlockSchema;
import com.nswt.schema.ZCPageBlockSet;

/**
 * ҳ���������
 * 
 * @Author ����
 * @Date 2007-9-27
 * @Mail lanjun@nswt.com
 */
public class PageBlock extends Page {

	/**
	 * �����б�
	 * 
	 * @param dga
	 */
	public static void dg1DataBind(DataGridAction dga) {// type=4��ҳ��Ƭ��Ϊϵͳ�Զ�����
		QueryBuilder qb = new QueryBuilder("select a.* from ZCPageBlock a");
		String catalogID = dga.getParam("CatalogID");
		if (StringUtil.isNotEmpty(catalogID)) {
			qb.append(",zccatalog b where a.catalogid=b.id and b.innercode like ?", CatalogUtil.getInnerCode(catalogID)
					+ "%");
		} else {
			qb.append(" where a.siteid=?", Application.getCurrentSiteID());
		}
		if (!Config.isDebugMode()) {
			qb.append(" and a.type<>4");
		}
		qb.append(" order by a.type asc");
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dt.insertColumn("CatalogIDName");
		dt.insertColumn("CatalogPath");
		for (int i = 0; i < dt.getRowCount(); i++) {
			if (StringUtil.isNotEmpty(dt.getString(i, "CatalogID"))&&!"0".equals(dt.getString(i, "CatalogID"))) {
				dt.set(i, "CatalogIDName", CatalogUtil.getName(dt.getString(i, "CatalogID")));
				String str = "/";
				String id = dt.getString(i, "CatalogID");
				String parentid = "";
				do{
					if(StringUtil.isNotEmpty(parentid)){
						id = parentid;
					}
					parentid = CatalogUtil.getParentID(id);
					str = "/"+CatalogUtil.getName(id)+str;
				}while(!parentid.equals("0"));
				dt.set(i, "CatalogPath", str);
			} else {
				dt.set(i, "CatalogIDName", "ȫվ");
				dt.set(i, "CatalogPath", "");
			}
		}
		dga.setTotal(qb);
		dga.bindData(dt);

	}

	/**
	 * ��ʾ�������б����Ϣ
	 * 
	 * @param dga
	 */
	public static void dialogDataBind(DataGridAction dga) {
		String id = (String) dga.getParams().get("ID");
		// �޸�
		DataTable dt = null;
		if (StringUtil.isNotEmpty(id)) {
			dt = new QueryBuilder("select title,URL from zcpageblockItem where blockid=?", id).executeDataTable();
		} else {
			dt = new QueryBuilder("select '' as title,'' as URL from zcpageblockItem").executePagedDataTable(1, 0);
		}
		dga.bindData(dt);
	}

	public static void blockItemDataBind(DataGridAction dga) {
		String id = dga.getParam("ID");
		DataTable dt = null;
		if (StringUtil.isNotEmpty(id)) {
			dt = new QueryBuilder("select id,title,URL from zcpageblockItem where blockid=?", id).executeDataTable();
		}
		if (dt == null || dt.getRowCount() == 0) {
			String sql = "select 0 as id,'' as title,'' as URL from zcpageblockItem";
			dt = new QueryBuilder(sql).executePagedDataTable(1, 0);
		}
		dga.setTotal(dt.getRowCount());
		dga.bindData(dt);
	}

	public void edit() {
		Transaction trans = new Transaction();

		ZCPageBlockSchema block = new ZCPageBlockSchema();
		long blockID = Long.parseLong($V("ID"));
		block.setID(blockID);
		if (!block.fill()) {
			Response.setStatus(0);
			Response.setMessage("û���ҵ���Ӧ������!");
		}
		block.setName($V("Name"));
		block.setCode($V("Code"));
		block.setFileName($V("FileName"));
		;
		block.setTemplate($V("Template"));
		block.setSortField($V("SortField"));
		;

		block.setType(Integer.parseInt($V("Type")));
		block.setContent($V("Content"));
		block.setModifyTime(new Date());
		block.setModifyUser(User.getUserName());

		trans.add(block, Transaction.UPDATE);

		if (Integer.parseInt($V("Type")) == 2) {
			trans.add(new QueryBuilder("delete from ZCPageBlockItem where blockid=?", blockID));
			String[] title = $V("ItemTitle").split("\\^");
			String[] url = $V("ItemURL").split("\\^");
			for (int i = 0; i < title.length; i++) {
				ZCPageBlockItemSchema item = new ZCPageBlockItemSchema();
				item.setID(NoUtil.getMaxID("PageBlockID"));
				item.setBlockID(blockID);
				item.setTitle(title[i]);
				item.setURL(url[i]);
				item.setImage($V("Image"));
				item.setAddTime(new Date());
				item.setAddUser(User.getUserName());
				trans.add(item, Transaction.INSERT);
			}
		}

		if (trans.commit()) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("��������!");
		}
	}

	public static Mapx init(Mapx params) {
		if (params.get("ID") != null && !"".equals(params.get("ID"))) {
			long ID = Long.parseLong(params.get("ID").toString());
			ZCPageBlockSchema block = new ZCPageBlockSchema();
			block.setID(ID);
			block.fill();
			Mapx mapx = block.toMapx();
			mapx.put("BlockType", block.getType() + "");
			return mapx;
		} else {
			return params;
		}
	}

	public static Mapx initParam(Mapx params) {
		return params;
	}

	public void add() {
		Transaction trans = new Transaction();

		ZCPageBlockSchema block = new ZCPageBlockSchema();
		long blockID = NoUtil.getMaxID("PageBlockID");
		block.setID(blockID);
		block.setSiteID(Application.getCurrentSiteID());
		String obj = $V("CatalogID");
		if (StringUtil.isNotEmpty(obj)) {
			block.setCatalogID(Long.parseLong(obj.toString()));
		}

		block.setName($V("Name"));
		block.setCode($V("Code"));
		block.setFileName($V("FileName"));
		block.setTemplate($V("Template"));
		block.setSortField($V("SortField"));

		block.setType(Integer.parseInt($V("Type")));
		block.setContent($V("Content"));
		block.setAddTime(new Date());
		block.setAddUser(User.getUserName());

		trans.add(block, Transaction.INSERT);

		if (Integer.parseInt($V("Type")) == 2) {
			String[] title = $V("ItemTitle").split("\\^");
			String[] url = $V("ItemURL").split("\\^");
			for (int i = 0; i < title.length; i++) {
				ZCPageBlockItemSchema item = new ZCPageBlockItemSchema();
				item.setID(NoUtil.getMaxID("PageBlockID"));// ��ӦΪPageBlockItemID����Ŀǰ����д�ˣ����Ҳ�Ӱ������ʹ�ã����Բ��޸�
				item.setBlockID(blockID);
				item.setTitle(title[i]);
				item.setURL(url[i]);
				item.setImage($V("Image"));
				item.setAddTime(new Date());
				item.setAddUser(User.getUserName());
				trans.add(item, Transaction.INSERT);
			}
		}

		if (trans.commit()) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("��������!");
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
		ZCPageBlockSchema block = new ZCPageBlockSchema();
		ZCPageBlockSet set = block.query(new QueryBuilder("where id in (" + ids + ")"));
		trans.add(set, Transaction.DELETE_AND_BACKUP);

		ZCPageBlockItemSchema blockItem = new ZCPageBlockItemSchema();
		ZCPageBlockItemSet itemSet = blockItem.query(new QueryBuilder("where blockID in (" + ids + ")"));
		trans.add(itemSet, Transaction.DELETE_AND_BACKUP);

		if (trans.commit()) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("�������ݿ�ʱ��������!");
		}
	}

	public void generate() {
		// boolean result = true;
		PageGenerator p = new PageGenerator();
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("����IDʱ��������!");
			return;
		}
		ZCPageBlockSchema block = new ZCPageBlockSchema();
		ZCPageBlockSet set = block.query(new QueryBuilder("where id in (" + ids + ")"));
		if (p.staticPageBlock(set)) {
			Deploy d = new Deploy();
			d.addJobs(Application.getCurrentSiteID(), p.getFileList());
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("��������ʧ�ܡ�" + Errorx.printString());
		}
	}

	public void copy() {
		String BlockID = $V("ID");
		if (!StringUtil.checkID(BlockID)) {
			Response.setStatus(0);
			Response.setMessage("����BlockIDʱ��������!");
			return;
		}
		String catalogIDs = $V("CatalogIDs");
		if (!StringUtil.checkID(catalogIDs)) {
			Response.setStatus(0);
			Response.setMessage("����CatalogIDʱ��������!");
			return;
		}
		Transaction trans = new Transaction();
		ZCPageBlockSchema site = new ZCPageBlockSchema();
		ZCPageBlockSet set = site.query(new QueryBuilder("where id in (" + BlockID + ")"));
		String copySiteID = "";
		// ����ҳ��Ƭ��
		// Todo: ��Ҫע�⾲̬��������
		String[] catalogArr = catalogIDs.split(",");
		for (int i = 0; i < set.size(); i++) {
			ZCPageBlockSchema block = set.get(i);
			for (int j = 0; j < catalogArr.length; j++) {
				ZCPageBlockSchema blockClone = (ZCPageBlockSchema) block.clone();
				blockClone.setID(NoUtil.getMaxID("PageBlockID"));
				blockClone.setCatalogID(catalogArr[j]);
				if ("".equals(copySiteID)) {
					copySiteID = CatalogUtil.getSiteID(catalogArr[j]);
				}
				blockClone.setSiteID(copySiteID);
				trans.add(blockClone, Transaction.INSERT);

				String sqlPageBlockCount = "update zccatalog set " + "total = total+1 where id=?";
				trans.add(new QueryBuilder(sqlPageBlockCount, catalogArr[j]));
			}
		}
		if (trans.commit()) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("�������ݿ�ʱ��������!");
		}
	}
}
