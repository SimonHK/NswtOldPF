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
 * @Author 王育春
 * @Date 2008-1-18
 * @Mail nswt@nswt.com.cn
 */
public class Application extends Page {

	/**
	 * 初始化以获取站点、菜单权限
	 * 
	 * @return Mapx
	 */
	public static Mapx init(Mapx params) {
		// 应用列表
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
		// 菜单
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
			params.put("Menu", "<font color='yellow'>对不起，你没有任何菜单权限，请联系'管理员'分配菜单权限后再登陆！</font>");
		}

		DataCollection privDC = Login.getAllPriv(new DataCollection());
		String priv = StringUtil.htmlEncode(privDC.toXML().replaceAll("\\s+", " "));
		params.put("Privileges", priv);
		return params;
	}

	/**
	 * 更改站点
	 */
	public void changeSite() {
		String SiteID = $V("SiteID");
		Application.setCurrentSiteID(SiteID);
	}

	/**
	 * 设置当前站点的站点ID
	 * 
	 * @param siteID
	 *            站点ID
	 */
	public static void setCurrentSiteID(String siteID) {
		if (StringUtil.isEmpty(siteID)) {
			User.setValue("_CurrentSiteID", "");
		} else {
			User.setValue("_CurrentSiteID", siteID);
		}

	}

	/**
	 * 获取当前站点的站点ID
	 * 
	 * @return siteID 站点ID
	 */
	public static long getCurrentSiteID() {
		String id = (String) User.getValue("_CurrentSiteID");
		if (StringUtil.isEmpty(id)) {
			if(UserList.ADMINISTRATOR.equals(User.getUserName())){
				LogUtil.error("请在应用管理->应用列表下先创建应用");
				return 0L;
			}else{
				LogUtil.error("用户：" + User.getUserName() + "没有任何应用的浏览权限，请先设置权限再登陆");
				return 0L;
			}
		}
		return Long.parseLong(id);
	}

	/**
	 * 获取当前应用的应用别名
	 * 
	 * @return siteAlias 应用别名
	 */
	public static String getCurrentSiteAlias() {
		return (String) SiteUtil.getAlias(getCurrentSiteID());
	}

	/**
	 * 修改密码
	 */
	public void changePassword() {
		String OldPassword = $V("OldPassword");
		String Password = $V("Password");
		QueryBuilder qb = new QueryBuilder("update ZDUser set Password=? where UserName=? and Password=?");
		qb.add(StringUtil.md5Hex(Password));
		qb.add(User.getUserName());
		qb.add(StringUtil.md5Hex(OldPassword));
		if (qb.executeNoQuery() > 0) {
			UserLog.log(UserLog.USER,UserLog.USER_EDITPASSWORD, "修改密码成功", Request.getClientIP());
			Response.setMessage("修改密码成功");
			Response.setStatus(1);
		} else {
			UserLog.log(UserLog.USER, UserLog.USER_EDITPASSWORD, "修改密码失败", Request.getClientIP());
			Response.setStatus(0);
			Response.setMessage("修改密码失败，旧密码不正确");
		}
	}

	/**
	 * 退出
	 */
	public void logout() {
		String logouturl = Config.getContextPath() + "Logout.jsp";

		Response.put("Status", 1);
		UserLog.log(UserLog.LOG, UserLog.LOGOUT, "正常退出系统", Request.getClientIP());

		redirect(logouturl);
	}

}
