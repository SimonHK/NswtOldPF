package com.nswt.shop;

import java.util.Date;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import com.nswt.cms.dataservice.ColumnUtil;
import com.nswt.cms.document.Article;
import com.nswt.cms.pub.SiteUtil;
import com.nswt.framework.Config;
import com.nswt.framework.Page;
import com.nswt.framework.User;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.utility.DateUtil;
import com.nswt.framework.utility.HtmlUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.member.Member;
import com.nswt.platform.Application;
import com.nswt.platform.pub.NoUtil;
import com.nswt.platform.pub.OrderUtil;
import com.nswt.schema.ZCCatalogSchema;
import com.nswt.schema.ZDColumnValueSchema;
import com.nswt.schema.ZSFavoriteSchema;
import com.nswt.schema.ZSFavoriteSet;
import com.nswt.schema.ZSGoodsSchema;
import com.nswt.schema.ZSGoodsSet;

/**
 * @Author wyli
 * @Date 2016-01-21
 * @Mail lwy@nswt.com
 */
public class Goods extends Page {

	public static Mapx initDialog(Mapx params) {
		if (StringUtil.isNotEmpty(params.getString("ID"))) {
			ZSGoodsSchema goods = new ZSGoodsSchema();
			goods.setID(params.getLong("ID"));
			if (!goods.fill()) {
				return params;
			}

			params.put("GoodsLibID", goods.getCatalogID());
			params.put("CatalogName", new QueryBuilder("select Name from ZCCatalog where ID = ?", goods.getCatalogID())
					.executeString());
			params.putAll(goods.toMapx());
			params.put("PublishDate", DateUtil.toString(goods.getPublishDate()));
			params.put("PicSrc1", goods.getImage0());
			params.put("PicSrc", (Config.getContextPath() + Config.getValue("UploadDir") + "/"
					+ SiteUtil.getAlias(Application.getCurrentSiteID()) + "/" + goods.getImage0())
					.replaceAll("//", "/"));
			DataTable dt = new QueryBuilder("select Name, ID from ZSBrand where SiteID = ? order by ID", Application
					.getCurrentSiteID()).executeDataTable();
			params.put("BrandOptions", HtmlUtil.dataTableToOptions(dt, String.valueOf(goods.getBrandID()), false));

			// params.put("CustomColumn",
			// ColumnUtil.getHtml(ColumnUtil.RELA_TYPE_CATALOG_COLUMN,
			// String.valueOf(goods.getCatalogID())));
			params.put("CustomColumn", ColumnUtil.getHtml(ColumnUtil.RELA_TYPE_CATALOG_COLUMN, String.valueOf(goods
					.getCatalogID()), ColumnUtil.RELA_TYPE_DOCID, goods.getID() + ""));
		} else {
			params.put("PicSrc", (Config.getContextPath() + Config.getValue("UploadDir") + "/"
					+ SiteUtil.getAlias(Application.getCurrentSiteID()) + "/" + "upload/Image/nopicture.jpg")
					.replaceAll("//", "/"));
			DataTable dt = new QueryBuilder("select Name, ID from ZSBrand where SiteID = ? order by ID", Application
					.getCurrentSiteID()).executeDataTable();
			params.put("BrandOptions", HtmlUtil.dataTableToOptions(dt, false));
			String catalogID = params.getString("CatalogID");
			params.put("CustomColumn", ColumnUtil.getHtml(ColumnUtil.RELA_TYPE_CATALOG_COLUMN, catalogID));
			params.put("UpTime", DateUtil.getCurrentDate());
		}
		return params;
	}

	public void add() {
		Transaction trans = new Transaction();
		long ID = NoUtil.getMaxID("GoodsID");
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		// String good = $V("goodsParentID");
		String good = $V("CatalogID");

		if (good.equals(null) && good.length() > 0) {
			Response.setLogInfo(0, "��ѡ����Ʒ���");
			return;
		}
		catalog.setID(good);
		catalog.fill();
		ZSGoodsSchema goods = new ZSGoodsSchema();
		goods.setCatalogID(good);
		goods.setCatalogInnerCode(catalog.getInnerCode());
		goods.setType("1");
		goods.setTopFlag("1");
		goods.setStickTime(123213);
		goods.setBranchInnerCode(catalog.getBranchInnerCode());
		goods.setID(ID);
		goods.setStatus(Article.STATUS_DRAFT + "");
		goods.setSiteID(Application.getCurrentSiteID());
		goods.setCommentCount(0);
		goods.setHitCount(0);

		String ImageID = $V("ImageID");
		if (StringUtil.isNotEmpty(ImageID)) {
			DataTable imageDT = new QueryBuilder("select path,srcfilename from zcimage where id = ? ", ImageID)
					.executeDataTable();
			goods.setImage0((imageDT.get(0, "path").toString() + imageDT.get(0, "srcfilename")).replaceAll("//", "/")
					.toString());
		} else {
			goods.setImage0("upload/Image/nopicture.jpg");
		}
		goods.setAddTime(new Date());
		goods.setAddUser(User.getUserName());
		goods.setValue(Request);
		goods.setOrderFlag(OrderUtil.getDefaultOrder());

		SchemaSet ss = ColumnUtil.getValueFromRequest(goods.getCatalogID(), goods.getID(), this.Request);

		trans.add(ss, Transaction.INSERT);
		trans.add(goods, Transaction.INSERT);

		// �����Զ����ֶ�
		if (trans.commit()) {
			Response.setLogInfo(1, "�½��ɹ�");
		} else {
			Response.setLogInfo(0, "�½�ʧ��");
		}
	}

	public void save() {
		DataTable dt = (DataTable) Request.get("DT");
		Transaction trans = new Transaction();
		for (int i = 0; i < dt.getRowCount(); i++) {
			ZSGoodsSchema goods = new ZSGoodsSchema();
			goods.setID(dt.getString(i, "ID"));
			if (!goods.fill()) {
				Response.setLogInfo(0, "��Ҫ�޸ĵ���" + goods.getID() + "������!");
				return;
			}
			goods.setValue(dt.getDataRow(i));
			goods.setModifyUser(User.getUserName());
			goods.setModifyTime(new Date());
			trans.add(goods, Transaction.UPDATE);
		}
		if (trans.commit()) {
			Response.setLogInfo(1, "����ɹ�!");
		} else {
			Response.setLogInfo(0, "����ʧ��!");
		}
	}

	public void dg1Edit() {
		String ImageID = $V("ImageID");
		String imagePath = $V("PicSrc1");
		ZSGoodsSchema goods = new ZSGoodsSchema();
		String ID = $V("ID");
		goods.setID(ID);
		if (!goods.fill()) {
			Response.setLogInfo(0, "��Ҫ�޸ĵ���Ʒ" + goods.getName() + "������!");
			return;
		}
		Transaction trans = new Transaction();
		if(goods.getPrice() > Double.parseDouble(Request.getString("Price"))) {
			//��������
			DataTable dt = new QueryBuilder("select a.Name, b.UserName from ZSFavorite b, ZSGoods a where b.GoodsID = ? and b.GoodsID = a.ID and b.SiteID = ? and a.SiteID = b.SiteID and b.PriceNoteFlag = 'Y'", 
					ID, Application.getCurrentSiteID()).executeDataTable();
			
			for(int i = 0; i < dt.getRowCount(); i++) {
				Member member = new Member(dt.getString(i, "UserName"));
				if(!member.fill()) {
					continue;
				}
				SimpleEmail email = new SimpleEmail();
				email.setHostName("smtp.163.com");
				try {
					String siteName = SiteUtil.getName(Application.getCurrentSiteID());
					StringBuffer sb = new StringBuffer();
					sb.append("�𾴵�" + siteName + "�û���<br/>");
					sb.append("��ã�<br/>");
					sb.append("����ע����Ʒ" + dt.getString(i, "Name") + "�Ѿ����ۣ�����һ�����Ӳ鿴�����Ϣ��<br/>");
//					sb.append();
					sb.append("<br/><br/>ע�����ʼ�Ϊϵͳ�Զ����ͣ�����ظ���<br/>");
					sb.append("������������������������������������������������������" + siteName);
					email.setAuthentication("0871huhu@163.com","08715121182");
					email.addTo(member.getEmail(),member.getUserName());
					email.setFrom("0871huhu@163.com", siteName);
					email.setSubject(siteName + "����Ʒ�������ѣ�");
					email.setContent(sb.toString(), "text/html;charset=utf-8");
					
				} catch (EmailException e) {
					Response.setLogInfo(0,"�ʼ����ʹ���");
					e.printStackTrace();
				}
			}
		}
		
		goods.setValue(Request);
		goods.setModifyUser(User.getUserName());
		goods.setModifyTime(new Date());
		goods.setStatus(Article.STATUS_EDITING + "");

		if (StringUtil.isNotEmpty(ImageID)) {
			DataTable imageDT = new QueryBuilder("select path,srcfilename from zcimage where id = ? ", ImageID)
					.executeDataTable();
			String path = (imageDT.get(0, "path").toString() + imageDT.get(0, "srcfilename")).replaceAll("//", "/")
					.toString();
			goods.setImage0(path);
		} else {
			goods.setImage0(imagePath);
		}

		trans.add(goods, Transaction.UPDATE);
		// �����Զ����ֶ�
		DataTable dt = ColumnUtil.getColumnValue(ColumnUtil.RELA_TYPE_DOCID, goods.getID());
		for (int i = 0; i < dt.getRowCount(); i++) {
			ZDColumnValueSchema value = new ZDColumnValueSchema();
			value.setValue(dt.getDataRow(i));
			value.setTextValue($V(ColumnUtil.PREFIX + value.getColumnCode()));
			trans.add(value, Transaction.UPDATE);
		}

		if (trans.commit()) {
			Response.setLogInfo(1, "�޸ĳɹ�");
		} else {
			Response.setLogInfo(0, "�޸�" + goods.getID() + "ʧ��!");
		}
	}

	public void del() {
		String IDs = $V("IDs");
		if (!StringUtil.checkID(IDs)) {
			return;// ���SQLע��
		}
		Transaction trans = new Transaction();
		ZSGoodsSchema goods = new ZSGoodsSchema();
		ZSGoodsSet set = goods.query(new QueryBuilder("where id in (" + IDs + ")"));
		trans.add(set, Transaction.DELETE_AND_BACKUP);
		
		//��������Ʒ����
		ZSFavoriteSchema fav = new ZSFavoriteSchema();
		ZSFavoriteSet favs = fav.query(new QueryBuilder("where GoodsID in (" + IDs + ")"));
		trans.add(favs, Transaction.DELETE);
		
		if (trans.commit()) {
			Response.setLogInfo(1, "ɾ���ɹ�");
		} else {
			Response.setLogInfo(0, "ɾ��ʧ��");
		}
	}

	public boolean checkSN() {
		String SN = $V("SN");
		if (StringUtil.isEmpty(SN)) {
			Response.setLogInfo(0, "����Ϊ��");
			return false;
		}
		int count = new QueryBuilder("select count(1) from ZSGoods where SN=?", SN).executeInt();
		if (count > 0) {
			Response.setLogInfo(0, "�Ѿ����ڴ����ŵ�ҩƷ:" + $V("SN"));
			return false;
		}
		return true;
	}
	/*
	 * public void setNewTopper(){ ZSGoodsSchema goods = new ZSGoodsSchema();
	 * goods.setID($V("ID")); goods.fill(); goods.setIsNew("Y");
	 * goods.setOrderNew(0); if(goods.update()){
	 * this.Response.setLogInfo(1,"�ö��ɹ���"); }else{
	 * this.Response.setLogInfo(0,"�ö�ʧ�ܣ�"); } }
	 * 
	 * public void setSpecialTopper(){ ZSGoodsSchema goods = new
	 * ZSGoodsSchema(); goods.setID($V("ID")); goods.fill();
	 * goods.setIsSpecial("Y"); goods.setOrderSpecial(0); if(goods.update()){
	 * this.Response.setLogInfo(1,"�ö��ɹ���"); }else{
	 * this.Response.setLogInfo(0,"�ö�ʧ�ܣ�"); } }
	 * 
	 * public void setHotTopper(){ ZSGoodsSchema goods = new ZSGoodsSchema();
	 * goods.setID($V("ID")); goods.fill(); goods.setIsHot("Y");
	 * goods.setOrderHot(0); if(goods.update()){
	 * this.Response.setLogInfo(1,"�ö��ɹ���"); }else{
	 * this.Response.setLogInfo(0,"�ö�ʧ�ܣ�"); } }
	 * 
	 * public void setOrderTopper(){ ZSGoodsSchema goods = new ZSGoodsSchema();
	 * goods.setID($V("ID")); goods.fill(); goods.setIsSale("Y");
	 * goods.setOrderSale(0); if(goods.update()){
	 * this.Response.setLogInfo(1,"�ö��ɹ���"); }else{
	 * this.Response.setLogInfo(0,"�ö�ʧ�ܣ�"); } }
	 */
}
