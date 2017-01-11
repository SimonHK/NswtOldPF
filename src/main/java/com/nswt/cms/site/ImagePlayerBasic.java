package com.nswt.cms.site;

import java.util.Date;

import com.nswt.cms.pub.CatalogUtil;
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
import com.nswt.schema.ZCImagePlayerSchema;
import com.nswt.schema.ZCImagePlayerSet;
import com.nswt.schema.ZCImagePlayerStyleSchema;

/**
 * ͼƬ������
 * 
 * @Author ����
 * @Date 2007-8-10
 * @Mail huanglei@nswt.com
 */
public class ImagePlayerBasic extends Page {

	public final static String IMAGESOURCE_LOCAL = "local";
	public final static String IMAGESOURCE_CATALOG_FIRST = "catalog_first";
	public final static String IMAGESOURCE_CATALOG_SELECT = "catalog_select";

	public final static Mapx IMAGESOURCE_MAP = new Mapx();

	static {
		IMAGESOURCE_MAP.put(IMAGESOURCE_LOCAL, "�����ϴ�");
		IMAGESOURCE_MAP.put(IMAGESOURCE_CATALOG_FIRST, "������Ŀ�����е�ͼƬ(�Զ�ȡ��һ��)");
		IMAGESOURCE_MAP.put(IMAGESOURCE_CATALOG_SELECT, "������Ŀ�����е�ͼƬ(�༭�ֹ�ѡ��)");
	}

	public static Mapx init(Mapx params) {
		String imagePlayerID = params.getString("ImagePlayerID");
		long StyleID = 1;
		if (StringUtil.isNotEmpty(imagePlayerID)) {
			long ID = Long.parseLong(imagePlayerID);
			ZCImagePlayerSchema ImagePlayer = new ZCImagePlayerSchema();
			ImagePlayer.setID(ID);
			ImagePlayer.fill();
			StyleID = ImagePlayer.getStyleID();
			params.putAll(ImagePlayer.toMapx());
			params.put("ImagePlayerID", ImagePlayer.getID() + "");
			params.put("radiosImageSource", HtmlUtil.mapxToRadios("ImageSource", IMAGESOURCE_MAP, ImagePlayer.getImageSource()));
		} else {
			params.put("radiosImageSource", HtmlUtil.mapxToRadios("ImageSource", IMAGESOURCE_MAP, IMAGESOURCE_LOCAL));
			params.put("DisplayCount", 6);
			params.put("StyleID", StyleID);
		}
		ZCImagePlayerStyleSchema style = new ZCImagePlayerStyleSchema();
		style.setID(StyleID);
		style.fill();
		params.put("StyleImgSrc", style.getImagePath());
		return params;
	}

	public static void dg1DataBind(DataGridAction dga) {
		QueryBuilder qb = new QueryBuilder(
				"select ID,Name,Code,SiteID,DisplayType,ImageSource,Height,Width,Displaycount from ZCImagePlayer order by ID ");
		dga.bindData(qb);
	}

	public void add() {
		String imagePlayerID = $V("ImagePlayerID");
		ZCImagePlayerSchema ImagePlayer = new ZCImagePlayerSchema();
		if (StringUtil.isNotEmpty(imagePlayerID)) {
			// �༭
			ImagePlayer.setID(imagePlayerID);
			ImagePlayer.fill();
			ImagePlayer.setValue(Request);
			ImagePlayer.setModifyTime(new Date());
			ImagePlayer.setModifyUser(User.getUserName());
			if (StringUtil.isNotEmpty($V("RelaCatalogID"))) {
				ImagePlayer.setRelaCatalogInnerCode(CatalogUtil.getInnerCode($V("RelaCatalogID")));
			} else {
				ImagePlayer.setRelaCatalogInnerCode("0");
			}
			if (ImagePlayer.update()) {
				Response.setStatus(1);
				Response.put("ImagePlayerUrl", "ImagePlayerID=" + ImagePlayer.getID() + "&ImageSource="
						+ ImagePlayer.getImageSource() + "&RelaCatalog=" + ImagePlayer.getRelaCatalogInnerCode()+"&DisPlayCount="+ImagePlayer.getDisplayCount());
				Response.setMessage("����ɹ�,������ȥ��Ԥ�����鿴�޸ĺ��Ч��!");
			} else {
				Response.setStatus(0);
				Response.setMessage("��������!");
			}
		} else {
			// �½�
			DataTable checkDT = new QueryBuilder("select * from zcimageplayer where code=? and siteID=?", $V("Code"),
					Application.getCurrentSiteID()).executeDataTable();
			if (checkDT.getRowCount() > 0) {
				Response.setLogInfo(0, "�Ѿ����ڴ���Ϊ�� <b style='color:#F00'>" + $V("Code") + "</b>�� ��ͼƬ����������������������룡");
				return;
			}
			ImagePlayer.setID(NoUtil.getMaxID("ImagePlayerID"));
			ImagePlayer.setValue(Request);
			ImagePlayer.setSiteID(Application.getCurrentSiteID());
			ImagePlayer.setAddTime(new Date());
			ImagePlayer.setAddUser(User.getUserName());
			if (StringUtil.isNotEmpty($V("RelaCatalogID"))) {
				ImagePlayer.setRelaCatalogInnerCode(CatalogUtil.getInnerCode($V("RelaCatalogID")));
			} else {
				ImagePlayer.setRelaCatalogInnerCode("0");
			}
			if (ImagePlayer.insert()) {
				Response.put("ImagePlayerUrl", "ImagePlayerID=" + ImagePlayer.getID() + "&ImageSource="
						+ ImagePlayer.getImageSource() + "&RelaCatalog=" + ImagePlayer.getRelaCatalogInnerCode()+"&DisplayCount="+ImagePlayer.getDisplayCount());
				Response.setStatus(1);
				Response.setMessage("�½��ɹ�,�����ڿ��Թ���ͼƬ��!");
			} else {
				Response.setStatus(0);
				Response.setMessage("��������!");
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
		ZCImagePlayerSchema ImagePlayer = new ZCImagePlayerSchema();
		ZCImagePlayerSet set = ImagePlayer.query(new QueryBuilder("where id in (" + ids + ")"));
		trans.add(set, Transaction.DELETE_AND_BACKUP);

		if (trans.commit()) {
			Response.setMessage("ɾ���ɹ�,������ȥ��Ԥ�����鿴ɾ�����Ч��!");
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("�������ݿ�ʱ��������!");
		}
	}
}
