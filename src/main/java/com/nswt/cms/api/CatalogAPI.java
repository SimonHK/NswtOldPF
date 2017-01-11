package com.nswt.cms.api;

import java.util.Date;
import java.util.Iterator;

import com.nswt.cms.pub.CatalogUtil;
import com.nswt.cms.site.Catalog;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.utility.Errorx;
import com.nswt.framework.utility.FileUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.Application;
import com.nswt.platform.pub.NoUtil;
import com.nswt.schema.ZCArticleSchema;
import com.nswt.schema.ZCAttachmentSchema;
import com.nswt.schema.ZCAudioSchema;
import com.nswt.schema.ZCCatalogSchema;
import com.nswt.schema.ZCCatalogSet;
import com.nswt.schema.ZCImageSchema;
import com.nswt.schema.ZCPageBlockItemSchema;
import com.nswt.schema.ZCPageBlockItemSet;
import com.nswt.schema.ZCPageBlockSchema;
import com.nswt.schema.ZCPageBlockSet;
import com.nswt.schema.ZCSiteSchema;
import com.nswt.schema.ZCVideoSchema;
import com.nswt.schema.ZDColumnRelaSchema;
import com.nswt.schema.ZDColumnRelaSet;
import com.nswt.schema.ZDColumnValueSchema;
import com.nswt.schema.ZDColumnValueSet;

public class CatalogAPI implements APIInterface {

	private Mapx params;

	public Mapx getParams() {
		return params;
	}

	public void setParams(Mapx params) {
		convertParams(params);
		this.params = params;
	}
		/*	�½���Ŀ
		 *  �½���Ŀ�ɹ�       ���� 1
		 *  	  ʧ��        ����-1
		 *  �����Ŀ�Ѿ�����   �����Ի�����ʾ�Ѿ����ڣ�������0
		 */
	public long insert() {
		return insert(new Transaction());
	}

	public long insert(Transaction trans) {
		String parentID = params.getString("ParentID");
		String name = params.getString("Name");
		String type = params.getString("Type");
		String alias = params.getString("Alias");
		String siteID = params.getString("SiteID");
		String url = params.getString("URL");
		
		DataTable dt = new QueryBuilder("select id from ZCCatalog where parentID='"+parentID+"' and name='"+name+"' and siteID='"+siteID+"'").executeDataTable();
		if(dt.getRowCount()>0){
			Errorx.addError(name + "��Ŀ�Ѿ�����!");
			return -1;
		}
		
		int aliasCount = new QueryBuilder("select count(1) from zccatalog where alias=? and siteid=?",alias,siteID).executeInt();
		if(aliasCount>0){
			Errorx.addError(alias + "�����Ѿ�����!");
			return -1;
		}
		
		ZCCatalogSchema pCatalog = new ZCCatalogSchema();
		ZCCatalogSchema catalog = new ZCCatalogSchema();

		if (StringUtil.isEmpty(type)) {
			type = Catalog.CATALOG + "";
		}

		// û�д���parentID��ʾ����һ����Ŀ�������Ƿ�һ����Ŀ
		if (StringUtil.isNotEmpty(parentID)) {
			pCatalog.setID(Long.parseLong(parentID));
			if(!pCatalog.fill()){
				return -1;
			}
			catalog.setParentID(pCatalog.getID());
			catalog.setSiteID(pCatalog.getSiteID());
			
			catalog.setTreeLevel(pCatalog.getTreeLevel() + 1);
			pCatalog.setChildCount(pCatalog.getChildCount() + 1);
			pCatalog.setIsLeaf(0);
			trans.add(pCatalog, Transaction.UPDATE);
		}
		else {
			// ���û�и���ĿID����,Ҳû��SiteID����,�����ʧ��
			if (StringUtil.isEmpty(siteID)) {
				return -1;
			}

			ZCSiteSchema site = new ZCSiteSchema();
			site.setID(siteID);
			if (!site.fill()) {
				return -1;
			}
			catalog.setParentID(0);
			catalog.setSiteID(site.getID());
			catalog.setTreeLevel(1);
			parentID = "0";

			if ("1".equals(type)) {
				site.setChannelCount(site.getChannelCount() + 1);
			}
			else if ("2".equals(type)) {
				site.setSpecialCount(site.getSpecialCount() + 1);
			}
			else if ("3".equals(type)) {
				site.setMagzineCount(site.getMagzineCount() + 1);
			}
			trans.add(site, Transaction.UPDATE);
		}

		if (StringUtil.isEmpty(url)) {
			url="";
			if (StringUtil.isNotEmpty(parentID)) {
				url += CatalogUtil.getPath(parentID);
			}
			url += alias + "/";
		}
		
		long catalogID = NoUtil.getMaxID("CatalogID");
		catalog.setID(catalogID);
		
		String innerCode = CatalogUtil.createCatalogInnerCode(pCatalog.getInnerCode());
		catalog.setInnerCode(innerCode);
		
		catalog.setName(name.trim());
		catalog.setAlias(alias);
		catalog.setURL(url);
		catalog.setType(Integer.parseInt(type));

		catalog.setListTemplate(params.getString("Template"));
		catalog.setListNameRule(params.getString("ListNameRule"));
		catalog.setDetailTemplate(params.getString("DetailTemplate"));
		catalog.setDetailNameRule(params.getString("DetailNameRule"));
		catalog.setChildCount(0);
		catalog.setIsLeaf(1);
		catalog.setTotal(0);

		String orderFlag = getCatalogOrderFlag(parentID, type);
		catalog.setOrderFlag(Integer.parseInt(orderFlag) + 1);
		catalog.setLogo(params.getString("Logo"));
		catalog.setListPageSize(20);

		if (StringUtil.isNotEmpty(params.getString("PublishFlag"))) {
			catalog.setPublishFlag("N");
		}
		else {
			catalog.setPublishFlag("Y");
		}

		if ("Y".equals(params.getString("SingleFlag"))) {
			catalog.setSingleFlag("Y");
		}
		else {
			catalog.setSingleFlag("N");
		}

		catalog.setImagePath(params.getString("Imagepath"));
		catalog.setHitCount(0);
		catalog.setMeta_Keywords(params.getString("MetaKeyWords"));
		catalog.setMeta_Description(params.getString("MetaDescription"));
		catalog.setOrderColumn(params.getString("OrderColumn"));
		catalog.setProp1(params.getString("Prop1"));
		catalog.setProp2(params.getString("Prop2"));
		catalog.setProp3(params.getString("Prop3"));
		catalog.setProp4(params.getString("Prop4"));
		catalog.setAddUser("wsdl");
		catalog.setAddTime(new Date());

		trans.add(catalog, Transaction.INSERT);
		trans.add(new QueryBuilder("update zccatalog set orderflag=orderflag+1 where orderflag>" + orderFlag + " and type="
				+ type));

		if (trans.commit()) {
			CatalogUtil.update(catalog.getID());
			return 1;
		} 
		return -1;
	}

	public boolean delete() {
		String catalogID = params.getString("CatalogID");
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(catalogID);
		if (!catalog.fill()) {
			return false;
		}

		Transaction trans = new Transaction();

		ZCCatalogSchema pCatalog = new ZCCatalogSchema();
		pCatalog.setID(catalog.getParentID());
		pCatalog.fill();
		pCatalog.setChildCount(pCatalog.getChildCount() - 1);
		if (pCatalog.getChildCount() == 0) {
			pCatalog.setIsLeaf(1);
		}
		else {
			pCatalog.setIsLeaf(0);
		}
		trans.add(pCatalog, Transaction.UPDATE);

		ZCCatalogSet catalogSet = catalog.query(new QueryBuilder(" where InnerCode like ?", catalog.getInnerCode() + "%"));
		trans.add(catalogSet, Transaction.DELETE);
		trans.add(new ZCArticleSchema().query(new QueryBuilder(" where CatalogInnerCode like ?", catalog.getInnerCode() + "%")), Transaction.DELETE);
		trans.add(new ZCImageSchema().query(new QueryBuilder(" where CatalogInnerCode like ?", catalog.getInnerCode() + "%")), Transaction.DELETE);
		trans.add(new ZCVideoSchema().query(new QueryBuilder(" where CatalogInnerCode like ?", catalog.getInnerCode() + "%")), Transaction.DELETE);
		trans.add(new ZCAudioSchema().query(new QueryBuilder(" where CatalogInnerCode like ?", catalog.getInnerCode() + "%")), Transaction.DELETE);
		trans.add(new ZCAttachmentSchema().query(new QueryBuilder(" where CatalogInnerCode like ?", catalog.getInnerCode()
						+ "%")), Transaction.DELETE);

		String ids = "";
		for (int i = 0; i < catalogSet.size(); i++) {
			ids += catalogSet.get(i).getID();
			if (i != catalogSet.size() - 1) {
				ids += ",";
			}
			// ɾ��Ŀ¼
			FileUtil.delete(CatalogUtil.getAbsolutePath(catalogSet.get(i).getID()));
		}
		ZCPageBlockSet blockSet = new ZCPageBlockSchema().query(new QueryBuilder(" where catalogid in (" + ids + ")"));
		for (int i = 0; i < blockSet.size(); i++) {
			ZCPageBlockItemSet itemSet = new ZCPageBlockItemSchema().query(new QueryBuilder(" where blockID=?", blockSet.get(i)
					.getID()));
			trans.add(itemSet, Transaction.DELETE);
		}
		trans.add(blockSet, Transaction.DELETE);

		// ɾ����Ŀ��������Ŀ���Զ����ֶ�zdcolumnrela,zdcolumnvalue
		String idsStr = "'" + ids.replaceAll(",", "','") + "'";// relaIDΪ�ַ����ֶ�
		// ɾ����Ŀ������zdcolumnrela������,��Ŀ���ĵ��Զ����ֶ�
		ZDColumnRelaSet columnRelaSet = new ZDColumnRelaSchema().query(new QueryBuilder(" where RelaID in(" + idsStr + ")"));
		trans.add(columnRelaSet, Transaction.DELETE);
		// ɾ����Ŀ���������zdcolumnvalue����,��Ŀ����չ�ֶ�
		ZDColumnValueSet columnValueSet1 = new ZDColumnValueSchema().query(new QueryBuilder(" where RelaID in(" + idsStr + ")"));
		trans.add(columnValueSet1, Transaction.DELETE);
		// ɾ����Ŀ�����¹�����zdcolumnvalue����
		String wherepart = " where exists (select ID from zcarticle where cataloginnercode like '" + catalog.getInnerCode()
				+ "%' and ID=zdcolumnvalue.relaID )";
		ZDColumnValueSet columnValueSet2 = new ZDColumnValueSchema().query(new QueryBuilder(wherepart));
		trans.add(columnValueSet2, Transaction.DELETE);

		if (trans.commit()) {
			CatalogUtil.update(catalog.getID());
			return true;
		}
		return false;
	}

	public boolean setSchema(Schema schema) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean update() {

		String ID = params.getString("CatalogID");
//		String name = params.getString("Name");
//		String alias = params.getString("Alias");
		String extend = params.getString("Extend");

		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(ID);
		if (!catalog.fill()) {
			return false;
		}

		catalog.setName(params.getString("Name"));
		catalog.setAlias(params.getString("Alias"));
		catalog.setModifyUser("wsdl");
		catalog.setModifyTime(new Date());

		Transaction trans = new Transaction();
		trans.add(catalog, Transaction.UPDATE);

		// ����ģ������
		if (StringUtil.isNotEmpty(extend)) {
			if ("1".equals(extend)) {
				// ����Ŀ
				// ���ô���catalog���Ѿ���ֵ��
			}
			else if ("2".equals(extend)) { // ����Ŀ

				String IndexTemplate = params.getString("IndexTemplate");
				String ListTemplate = params.getString("ListTemplate");
				String DetailTemplate = params.getString("DetailTemplate");
				String RssTemplate = params.getString("RssTemplate");

				if (StringUtil.isNotEmpty(IndexTemplate) && StringUtil.isNotEmpty(ListTemplate)
						&& StringUtil.isNotEmpty(DetailTemplate) && StringUtil.isNotEmpty(RssTemplate)) {
					String innerCode = catalog.getInnerCode();
					QueryBuilder qb = new QueryBuilder(
							"update zccatalog set IndexTemplate=?,ListTemplate=?,DetailTemplate=?,rssTemplate=?"
									+ " where innercode like ? and TreeLevel>?");
					qb.add(IndexTemplate);
					qb.add(ListTemplate);
					qb.add(DetailTemplate);
					qb.add(RssTemplate);
					qb.add(innerCode + "%");
					qb.add(catalog.getTreeLevel());
					trans.add(qb);
				}

			}
			else if ("3".equals(extend)) {// ȫ����Ŀ
				String IndexTemplate = params.getString("IndexTemplate");
				String ListTemplate = params.getString("ListTemplate");
				String DetailTemplate = params.getString("DetailTemplate");
				String RssTemplate = params.getString("RssTemplate");
				if (StringUtil.isNotEmpty(IndexTemplate) && StringUtil.isNotEmpty(ListTemplate)
						&& StringUtil.isNotEmpty(DetailTemplate) && StringUtil.isNotEmpty(RssTemplate)) {
					QueryBuilder qb = new QueryBuilder(
							"update zccatalog set IndexTemplate=?,ListTemplate=?,DetailTemplate=? ,rssTemplate=?"
									+ " where siteID=? and Type=?");
					qb.add(IndexTemplate);
					qb.add(ListTemplate);
					qb.add(DetailTemplate);
					qb.add(RssTemplate);
					qb.add(catalog.getSiteID());
					qb.add(catalog.getType());
					trans.add(qb);
				}
			}
			else if ("4".equals(extend)) {// ͬ����Ŀ
				String IndexTemplate = params.getString("IndexTemplate");
				String ListTemplate = params.getString("ListTemplate");
				String DetailTemplate = params.getString("DetailTemplate");
				String RssTemplate = params.getString("RssTemplate");
				if (StringUtil.isNotEmpty(IndexTemplate) && StringUtil.isNotEmpty(ListTemplate)
						&& StringUtil.isNotEmpty(DetailTemplate) && StringUtil.isNotEmpty(RssTemplate)) {
					String part = "";
					String innerCode = catalog.getInnerCode();
					if (StringUtil.isNotEmpty(innerCode) && innerCode.length() > 4) {
						part = " and innercode like '" + innerCode.substring(0, innerCode.length() - 4) + "%'";
					}
					QueryBuilder qb = new QueryBuilder(
							"update zccatalog set IndexTemplate=?,ListTemplate=?,DetailTemplate=?,rssTemplate=?"
									+ " where siteID=? and Type=? and TreeLevel=?" + part);
					qb.add(IndexTemplate);
					qb.add(ListTemplate);
					qb.add(DetailTemplate);
					qb.add(RssTemplate);
					qb.add(catalog.getSiteID());
					qb.add(catalog.getType());
					qb.add(catalog.getTreeLevel());
					trans.add(qb);
				}
			}
		}

		// ������������
		String wfExtend = params.getString("WorkFlowExtend");
		if (StringUtil.isNotEmpty(wfExtend)) {
			if ("1".equals(wfExtend)) {
				// ����Ŀ
				// ���ô���catalog���Ѿ���ֵ��
			}
			else if ("2".equals(wfExtend)) {
				// ��������Ŀ
				trans.add(new QueryBuilder("update zccatalog set workflow =? where innercode like ?", catalog.getWorkflow(),
						catalog.getInnerCode() + "%"));
			}
			else if ("3".equals(wfExtend)) {
				// վ������ͬ����Ŀ
				trans.add(new QueryBuilder("update zccatalog set workflow =? where siteID =" + Application.getCurrentSiteID()
						+ " and Type=? ", catalog.getWorkflow(), catalog.getType()));
			}
			else if ("4".equals(wfExtend)) {
				// ͬ����Ŀ
				trans.add(new QueryBuilder("update zccatalog set workflow =? where siteID =" + Application.getCurrentSiteID()
						+ " and Type=? and TreeLevel=" + catalog.getTreeLevel(), catalog.getWorkflow(), catalog.getType()));
			}
		}

		if (trans.commit()) {
			CatalogUtil.update(catalog.getID());
			return true;
		}
		return false;
	}

	public static String getCatalogOrderFlag(String ParentID, String CatalogType) {

		DataTable parentDT = null;

		if (StringUtil.isEmpty(ParentID) || "0".equals(ParentID)) {
			parentDT = new QueryBuilder("select * from zccatalog where siteID = " + Application.getCurrentSiteID()
					+ " and type = " + CatalogType + " order by orderflag").executeDataTable();
		}
		else {
			String innercode = CatalogUtil.getInnerCode(ParentID);
			parentDT = new QueryBuilder("select * from zccatalog where siteID = " + CatalogUtil.getSiteID(ParentID)
					+ " and type = " + CatalogType + " and innercode like '" + innercode + "%' order by orderflag")
					.executeDataTable();
		}
		if (parentDT != null && parentDT.getRowCount() > 0) {
			return parentDT.getString(parentDT.getRowCount() - 1, "OrderFlag");
		}
		else {
			return "0";
		}
	}

	public void convertParams(Mapx params) {
		Iterator iter = params.keySet().iterator();
		while (iter.hasNext()) {
			Object key = iter.next();
			String value = params.getString(key);
			if (StringUtil.isEmpty(value) || "null".equalsIgnoreCase(value)) {
				params.put(key, "");
			}
		}
	}

}
