package com.nswt.shop;

import java.util.Date;

import com.nswt.cms.pub.SiteUtil;
import com.nswt.framework.Config;
import com.nswt.framework.Page;
import com.nswt.framework.User;
import com.nswt.framework.controls.DataGridAction;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.HtmlUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.Application;
import com.nswt.platform.pub.NoUtil;
import com.nswt.schema.ZSBrandSchema;
import com.nswt.schema.ZSBrandSet;

/**
 * @Author wyli
 * @Date 2016-01-21
 * @Mail lwy@nswt.com
 */
public class Brand extends Page {

	public static Mapx init(Mapx params) {
		return null;
	}

	public static Mapx initDialog(Mapx params) {
		String id = params.getString("ID");
		if (StringUtil.isNotEmpty(id)) {
			ZSBrandSchema brand = new ZSBrandSchema();
			brand.setID(id);
			if (!brand.fill()) {
				return params;
			}

			Mapx brandMap = brand.toMapx();
			brandMap.put("Name", brand.getName());
			brandMap.put("Alias", brand.getAlias());
			brandMap.put("URL", brand.getURL());
			brandMap.put("Info", brand.getInfo());
			brandMap.put("PicSrc1", brand.getImagePath());
			brandMap.put("PicSrc", (Config.getContextPath() + Config.getValue("UploadDir") + "/"
					+ SiteUtil.getAlias(Application.getCurrentSiteID()) + "/" + brand.getImagePath()).replaceAll("//",
					"/"));
			DataTable dt = new QueryBuilder(
					"select Name, BranchInnerCode, TreeLevel from ZDBranch where BranchInnerCode like '"
							+ User.getBranchInnerCode() + "%'").executeDataTable();
			com.nswt.cms.pub.PubFun.indentDataTable(dt);
			brandMap.put("BranchInnerCodeOptions", HtmlUtil.dataTableToOptions(dt, brand.getBranchInnerCode()));
			return brandMap;
		} else {
			params.put("PicSrc", (Config.getContextPath() + Config.getValue("UploadDir") + "/"
					+ SiteUtil.getAlias(Application.getCurrentSiteID()) + "/" + "upload/Image/nopicture.jpg")
					.replaceAll("//", "/"));
			DataTable dt = new QueryBuilder(
					"select Name, BranchInnerCode, TreeLevel from ZDBranch where BranchInnerCode like '"
							+ User.getBranchInnerCode() + "%'").executeDataTable();
			com.nswt.cms.pub.PubFun.indentDataTable(dt);
			params.put("BranchInnerCodeOptions", HtmlUtil.dataTableToOptions(dt, User.getBranchInnerCode()));
			return params;
		}
	}

	public static void dg1DataBind(DataGridAction dga) {
		String searchWord = dga.getParam("SearchWord");
		QueryBuilder qb = new QueryBuilder("select * from ZSBrand where SiteID = ?");
		qb.add(Application.getCurrentSiteID());
		if (StringUtil.isNotEmpty(searchWord)) {
			qb.append(" and Name like ?", "%" + searchWord.trim() + "%");
		}
		dga.bindData(qb);
	}

	public void add() {
		String ImageID = $V("ImageID");
		ZSBrandSchema brand = new ZSBrandSchema();
		brand.setID(NoUtil.getMaxID("ZSBrandID"));
		if (!brand.fill()) {
			brand.setValue(Request);
			brand.setSiteID(Application.getCurrentSiteID());
			DataTable dt = new QueryBuilder("select * from ZSBrand order by orderflag").executeDataTable();
			long orderflag = 0;
			if (dt != null && dt.getRowCount() > 0) {
				orderflag = dt.getLong(dt.getRowCount() - 1, "OrderFlag");
			}

			if (StringUtil.isNotEmpty(ImageID)) {
				DataTable imageDT = new QueryBuilder("select path,srcfilename from zcimage where id = ? ", ImageID)
						.executeDataTable();
				brand.setImagePath((imageDT.get(0, "path").toString() + imageDT.get(0, "srcfilename")).replaceAll("//",
						"/").toString());
			} else {
				brand.setImagePath("upload/Image/nopicture.jpg");
			}

			brand.setOrderFlag(orderflag + 1);
			brand.setPublishFlag("0");
			brand.setAddTime(new Date());
			brand.setAddUser(User.getUserName());

			if (brand.insert()) {
				Response.setStatus(1);
				Response.setMessage("新增成功!");
			} else {
				Response.setStatus(0);
				Response.setMessage("发生错误!");
			}
		} else {
			Response.setStatus(0);
			Response.setMessage("发生错误!");
		}
	}

	public void edit() {
		String ImageID = $V("ImageID");
		String imagePath = $V("PicSrc1");
		ZSBrandSchema brand = new ZSBrandSchema();
		brand.setID($V("ID"));
		if (brand.fill()) {
			brand.setValue(Request);
			brand.setModifyTime(new Date());
			brand.setModifyUser(User.getUserName());

			if (StringUtil.isNotEmpty(ImageID)) {
				DataTable imageDT = new QueryBuilder("select path,srcfilename from zcimage where id = ? ", ImageID)
						.executeDataTable();
				String path = (imageDT.get(0, "path").toString() + imageDT.get(0, "srcfilename")).replaceAll("//", "/")
						.toString();
				brand.setImagePath(path);
			} else {
				brand.setImagePath(imagePath);
			}

			if (brand.update()) {
				Response.setStatus(1);
				Response.setMessage("修改成功!");
			} else {
				Response.setStatus(0);
				Response.setMessage("发生错误!");
			}
		} else {
			Response.setStatus(0);
			Response.setMessage("发生错误!不存在的商品品牌!");
		}
	}

	public void del() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		Transaction trans = new Transaction();
		ZSBrandSchema brand = new ZSBrandSchema();
		ZSBrandSet set = brand.query(new QueryBuilder(" where id in (" + ids + ")"));
		trans.add(set, Transaction.DELETE_AND_BACKUP);

		if (trans.commit()) {
			Response.setStatus(1);
			Response.setMessage("删除成功!");
		} else {
			Response.setStatus(0);
			Response.setMessage("操作数据库时发生错误!");
		}
	}

	public void getPicSrc() {
		String ID = $V("PicID");
		DataTable dt = new QueryBuilder("select path,srcfilename from zcimage where id=?", Long.parseLong(ID)).executeDataTable();
		if (dt.getRowCount() > 0) {
			this.Response.put("picSrc", (Config.getContextPath() + Config.getValue("UploadDir") + "/"
					+ SiteUtil.getAlias(Application.getCurrentSiteID()) + "/" + dt.get(0, "path").toString() + dt.get(
					0, "srcfilename")).replaceAll("//", "/").toString());
		}
	}
}
