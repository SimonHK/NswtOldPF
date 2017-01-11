package com.nswt.platform;

import com.nswt.cms.pub.SiteUtil;
import com.nswt.framework.Config;
import com.nswt.framework.Page;
import com.nswt.framework.User;
import com.nswt.framework.controls.HtmlScript;
import com.nswt.framework.data.DataCollection;
import com.nswt.framework.data.DataRow;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.utility.Filter;
import com.nswt.framework.utility.HtmlUtil;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;

/**
 * @Author ������
 * @Date 2008-1-18
 * @Mail nswt@nswt.com.cn
 */
public class Application extends Page {

	/**
	 * ��ʼ���Ի�ȡվ�㡢�˵�Ȩ��
	 * 
	 * @return Mapx
	 */
	public static Mapx init(Mapx params) {
		// Ӧ���б�
		DataTable dt = null;
		dt = new QueryBuilder("select name,id from zcsite order by orderflag").executeDataTable();
		dt = dt.filter(new Filter() {
			public boolean filter(Object obj) {
				DataRow dr = (DataRow) obj;
				return Priv.getPriv(User.getUserName(), Priv.SITE, dr.getString("ID"), Priv.SITE_BROWSE);
			}
		});
		if(Application.getCurrentSiteID() == 0&&dt.getRowCount()>0){
			Application.setCurrentSiteID(dt.getString(0, "ID"));
		}
		params.put("Sites", HtmlUtil.dataTableToOptions(dt, Application.getCurrentSiteID() + ""));
		DataTable dtsite=new QueryBuilder("select ID,Name from zcsite order by orderflag").executeDataTable();
		StringBuffer sitestb=new StringBuffer();
		for (int i = 0; i < dtsite.getRowCount(); i++) {
			 if( dtsite.getString(i,"ID").equals(Application.getCurrentSiteID()+"")){
				 sitestb.append("<a value=\""+dtsite.getString(i,"ID")+"\" class=\"ahover\" hidefocus href=\"javascript:void(0);\">"+dtsite.getString(i, "Name")+"</a>");
				 params.put("CurrentSiteName", dtsite.getString(i, "Name"));
				 params.put("CurrentSiteId", dtsite.getString(i, "ID"));
			 }else{
				 sitestb.append("<a value=\""+dtsite.getString(i,"ID")+"\" hidefocus href=\"javascript:void(0);\">"+dtsite.getString(i, "Name")+"</a>");
			 }
		}
		params.put("ZCSites", sitestb.toString());
		// �˵�
		dt = new QueryBuilder("select name,id from zdmenu where  visiable='Y' and parentID=0 order by OrderFlag")
				.executeDataTable();
		dt = dt.filter(new Filter() {
			public boolean filter(Object obj) {
				DataRow dr = (DataRow) obj;
				return Priv.getPriv(User.getUserName(), Priv.MENU, Application.getCurrentSiteID() + "-"
						+ dr.getString("id"), Priv.MENU_BROWSE);
			}
		});
		boolean hasMenu = false;
		String template = "<a id='_Menu_${ID}' onclick='Application.onMainMenuClick(this);return false;' hidefocus='true'><b>${Name}</b></a>";
		String menuHtml = HtmlUtil.replaceWithDataTable(dt, template);
		if (dt.getRowCount() > 0) {
			hasMenu = true;
		}

		StringBuffer sb = new StringBuffer();
		// Script
		template = "arr.push([${ID},\"${Name}\",\"${URL}\",\"${Icon}\"]);";
		sb.append("var arr;");
		for (int i = 0; i < dt.getRowCount(); i++) {
			long id = dt.getLong(i, "ID");
			sb.append("arr = [];");
			DataTable dt2 = new QueryBuilder(
					"select name,id,url,icon from zdmenu where visiable='Y' and parentID=? order by OrderFlag", id)
					.executeDataTable();
			dt2 = dt2.filter(new Filter() {
				public boolean filter(Object obj) {
					DataRow dr = (DataRow) obj;
					return Priv.getPriv(User.getUserName(), Priv.MENU, Application.getCurrentSiteID() + "-"
							+ dr.getString("id"), Priv.MENU_BROWSE);
				}
			});
			sb.append(HtmlUtil.replaceWithDataTable(dt2, template));
			sb.append("$('_Menu_" + id + "').ChildArray = arr;");
			if (dt2.getRowCount() > 0) {
				hasMenu = true;
			}
		}

		HtmlScript script = new HtmlScript();
		script.setInnerHTML(sb.toString());
		if (hasMenu) {
			params.put("Menu", menuHtml + script.getOuterHtml());
		} else {
			params.put("Menu", "<font color='yellow'>�Բ�����û���κβ˵�Ȩ�ޣ�����ϵ'����Ա'����˵�Ȩ�޺��ٵ�½��</font>");
		}

		DataCollection privDC = Login.getAllPriv(new DataCollection());
		String priv = StringUtil.htmlEncode(privDC.toXML().replaceAll("\\s+", " "));
		params.put("Privileges", priv);
		return params;
	}

	/**
	 * ����վ��
	 */
	public void changeSite() {
		String SiteID = $V("SiteID");
		Application.setCurrentSiteID(SiteID);
	}

	/**
	 * ���õ�ǰվ���վ��ID
	 * 
	 * @param siteID
	 *            վ��ID
	 */
	public static void setCurrentSiteID(String siteID) {
		if (StringUtil.isEmpty(siteID)) {
			User.setValue("_CurrentSiteID", "");
		} else {
			User.setValue("_CurrentSiteID", siteID);
		}

	}

	/**
	 * ��ȡ��ǰվ���վ��ID
	 * 
	 * @return siteID վ��ID
	 */
	public static long getCurrentSiteID() {
		String id = (String) User.getValue("_CurrentSiteID");
		if (StringUtil.isEmpty(id)) {
			if(UserList.ADMINISTRATOR.equals(User.getUserName())){
				LogUtil.error("����Ӧ�ù���->Ӧ���б����ȴ���Ӧ��");
				return 0L;
			}else{
				LogUtil.error("�û���" + User.getUserName() + "û���κ�Ӧ�õ����Ȩ�ޣ���������Ȩ���ٵ�½");
				return 0L;
			}
		}
		return Long.parseLong(id);
	}

	/**
	 * ��ȡ��ǰӦ�õ�Ӧ�ñ���
	 * 
	 * @return siteAlias Ӧ�ñ���
	 */
	public static String getCurrentSiteAlias() {
		return (String) SiteUtil.getAlias(getCurrentSiteID());
	}

	/**
	 * �޸�����
	 */
	public void changePassword() {
		String OldPassword = $V("OldPassword");
		String Password = $V("Password");
		QueryBuilder qb = new QueryBuilder("update ZDUser set Password=? where UserName=? and Password=?");
		qb.add(StringUtil.md5Hex(Password));
		qb.add(User.getUserName());
		qb.add(StringUtil.md5Hex(OldPassword));
		if (qb.executeNoQuery() > 0) {
			UserLog.log(UserLog.USER,UserLog.USER_EDITPASSWORD, "�޸�����ɹ�", Request.getClientIP());
			Response.setMessage("�޸�����ɹ�");
			Response.setStatus(1);
		} else {
			UserLog.log(UserLog.USER, UserLog.USER_EDITPASSWORD, "�޸�����ʧ��", Request.getClientIP());
			Response.setStatus(0);
			Response.setMessage("�޸�����ʧ�ܣ������벻��ȷ");
		}
	}

	/**
	 * �˳�
	 */
	public void logout() {
		String logouturl = Config.getContextPath() + "Logout.jsp";

		Response.put("Status", 1);
		UserLog.log(UserLog.LOG, UserLog.LOGOUT, "�����˳�ϵͳ", Request.getClientIP());

		redirect(logouturl);
	}

}
