package com.nswt.cms.site;

import java.util.Date;

import com.nswt.cms.pub.SiteUtil;
import com.nswt.framework.Config;
import com.nswt.framework.Page;
import com.nswt.framework.User;
import com.nswt.framework.controls.DataGridAction;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.Application;
import com.nswt.platform.pub.OrderUtil;
import com.nswt.schema.ZCImageRelaSchema;
import com.nswt.schema.ZCImageSchema;

/**
 * 
 * @author huanglei
 * @mail huanglei@nswt.com
 * @date 2008-3-24
 */

public class ImagePlayerRela extends Page {

	public final static String RELATYPE_IMAGEPLAYER = "ImagePlayerImage";

	public static void dg1DataBind(DataGridAction dga) {
		String ImagePlayerID = (String) dga.getParams().get("ImagePlayerID");
		if (ImagePlayerID == null || "".equals(ImagePlayerID)) {
			dga.bindData(new DataTable());
			return;
		}
		String Alias = Config.getValue("UploadDir") + "/" + SiteUtil.getAlias(Application.getCurrentSiteID()) + "/";
		QueryBuilder qb = new QueryBuilder("select a.Name as Name,a.Info as Info,a.LinkURL as LinkURL,a.LinkText as LinkText,a.ID as ID,a.Path as Path,a.FileName as FileName,'" + Alias + "' as Alias,b.orderflag,b.relaid as ImagePlayerID " 
				+ "from ZCImage a,ZCImageRela b where  a.id=b.id and  b.RelaType = ? and b.RelaID = ? order by b.orderflag desc, " 
				+ "b.addtime desc", RELATYPE_IMAGEPLAYER, Long.parseLong(ImagePlayerID));
		dga.bindData(qb);
	}

	public void relaImage() {
		String imagePlayerIDStr = Request.getString("ImagePlayerID");
		if(StringUtil.isEmpty(imagePlayerIDStr)){
			Response.setLogInfo(0, "���ȱ��������Ϣ��Ȼ�����ͼƬ��");
			return;
		}
		long imagePlayerID = Long.parseLong(imagePlayerIDStr);
		String oldsum = new QueryBuilder("select count(*) from zcimagerela where relaid = ? and relatype = ?"
				, imagePlayerID, RELATYPE_IMAGEPLAYER).executeString();
		String ImageIDs = (String) this.Request.get("ImageIDs");
		String[] Relas = ImageIDs.split(",");
		int checksum = 0;
		if (Relas.length>6) {
			this.Response.setLogInfo(0, "����ӵ�ͼƬ���࣬ͼƬ���������ֻ֧��6��ͼƬ��");
			return;
		} 
		
		Transaction trans = new Transaction();
		String imageNames = "";
		boolean isExists = false;
		for (int i = 0; i < Relas.length; i++) {
			ZCImageRelaSchema imageRela = new ZCImageRelaSchema();
			imageRela.setID(Long.parseLong(Relas[i]));
			imageRela.setRelaID(imagePlayerID);
			imageRela.setRelaType(RELATYPE_IMAGEPLAYER);
			if (imageRela.fill()) {
				checksum += 1;
				imageRela.setOrderFlag(OrderUtil.getDefaultOrder());
				trans.add(imageRela, Transaction.UPDATE);
				
				ZCImageSchema image = new ZCImageSchema();
				image.setID(Relas[i]);
				image.fill();
				if (!isExists) {
					imageNames = image.getName();
					isExists = true;
				} else {
					imageNames += ", " + image.getName();
				}
			} else {
				imageRela.setOrderFlag(OrderUtil.getDefaultOrder());
				imageRela.setAddUser(User.getUserName());
				imageRela.setAddTime(new Date());
				trans.add(imageRela, Transaction.INSERT);
			}
		}
		
		if (Relas.length+Integer.parseInt(oldsum)-checksum>6) {
			this.Response.setLogInfo(0, "����ӵ�ͼƬ���࣬ͼƬ���������ֻ֧��6��ͼƬ��");
			return;
		}
		
		String message = "����ɹ�,������ȥ'Ԥ��'�鿴����ͼƬ���Ч��!";
		if (checksum>0) {
			message = "����ɹ�,ͼƬ<b style='color:#F00'> " + imageNames + "</b> �Ѿ����ڣ�������ȥ'Ԥ��'�鿴����ͼƬ���Ч��!";
		}
		if (trans.commit()) {
			this.Response.setLogInfo(1, message);
		} else {
			this.Response.setLogInfo(0, "����ʧ��");
		}
	}

	/**
	 * ����ͼƬ�������е�ͼƬ��ÿ�θ���һ��, ɾ��ԭ����ͼƬ��ͼƬ�������Ĺ�����Ȼ������µĹ��� �����µ�ͼƬ��LinkURL,LinkText
	 */
	public void editImage() {
		long ImagePlayerID = Long.parseLong(this.Request.get("ImagePlayerID").toString());
		String oldID = Request.getString("OldImageID");
		String ImageIDs = (String) this.Request.get("ImageIDs");
		String[] Relas = ImageIDs.split(",");
		String ID = Relas[0];
		Transaction trans = new Transaction();

		ZCImageSchema oldImage = new ZCImageSchema();
		oldImage.setID(oldID);
		oldImage.fill();

		ZCImageSchema newImage = new ZCImageSchema();
		newImage.setID(ID);
		newImage.fill();
		newImage.setLinkURL(oldImage.getLinkURL());
		newImage.setLinkText(oldImage.getLinkText());
		trans.add(newImage, Transaction.UPDATE);

		ZCImageRelaSchema oldRela = new ZCImageRelaSchema();
		oldRela.setID(oldID);
		oldRela.setRelaID(ImagePlayerID);
		oldRela.setRelaType(RELATYPE_IMAGEPLAYER);
		oldRela.fill();
		trans.add(oldRela, Transaction.DELETE_AND_BACKUP);

		ZCImageRelaSchema newRela = new ZCImageRelaSchema();
		newRela.setID(ID);
		newRela.setRelaID(ImagePlayerID);
		newRela.setRelaType(RELATYPE_IMAGEPLAYER);
		newRela.setOrderFlag(oldRela.getOrderFlag());
		newRela.setAddTime(oldRela.getAddTime());
		newRela.setAddUser(oldRela.getAddUser());
		newRela.setModifyTime(new Date());
		newRela.setModifyUser(User.getUserName());
		trans.add(newRela, Transaction.INSERT);
		
		if (trans.commit()) {
			this.Response.setLogInfo(1, "����ͼƬ�ɹ�!");
		} else {
			this.Response.setLogInfo(0, "����ͼƬʧ��!");
		}
	}

	public void dg1Edit() {
		DataTable dt = (DataTable) Request.get("DT");
		Transaction trans = new Transaction();
		for (int i = 0; i < dt.getRowCount(); i++) {
			ZCImageSchema image = new ZCImageSchema();
			image.setID(Integer.parseInt(dt.getString(i, "ID")));
			image.fill();
			image.setValue(dt.getDataRow(i));
			image.setModifyTime(new Date());
			image.setModifyUser(User.getUserName());
			trans.add(image, Transaction.UPDATE);

			ZCImageRelaSchema rela = new ZCImageRelaSchema();
			rela.setID(image.getID());
			rela.setRelaID(dt.getString(i, "ImagePlayerID"));
			rela.setRelaType(RELATYPE_IMAGEPLAYER);
			rela.fill();
			rela.setOrderFlag(dt.getString(i, "OrderFlag"));
			rela.setModifyTime(new Date());
			rela.setModifyUser(User.getUserName());
			trans.add(rela, Transaction.UPDATE);
		}
		if (trans.commit()) {
			Response.setLogInfo(1, "�����ɹ�!");
		} else {
			Response.setLogInfo(0,"��������!");
		}
	}

	public void del() {
		String ImagePlayerID = (String) this.Request.get("ImagePlayerID");
		String IDs = $V("IDs");
		if (!StringUtil.checkID(IDs)) {
			Response.setLogInfo(0,"����IDʱ��������!");
			return;
		}
		if (new ZCImageRelaSchema().query(new QueryBuilder(" where ID in (" + IDs + ") and RelaID=? and RelaType=?"
				,Long.parseLong(ImagePlayerID), RELATYPE_IMAGEPLAYER)).deleteAndBackup()) {
			this.Response.setLogInfo(1, "ɾ���ɹ�");
		} else {
			this.Response.setLogInfo(0, "ɾ��ʧ��");
		}
	}
	
	public void sortColumn() {
		String target = $V("Target");
		String orders = $V("Orders");
		String type = $V("Type");
		String imagePlayerID = $V("ImagePlayerID");
		if (!StringUtil.checkID(target) && !StringUtil.checkID(orders)) {
			return;
		}
		if (OrderUtil.updateOrder("ZCImageRela", type, target, orders, " RelaID = " + imagePlayerID)) {
			Response.setMessage("����ɹ�");
		} else {
			Response.setError("����ʧ��");
		}
	}
}
