package com.nswt.cms.site;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nswt.cms.datachannel.Publisher;
import com.nswt.cms.pub.CatalogUtil;
import com.nswt.framework.Config;
import com.nswt.framework.CookieImpl;
import com.nswt.framework.Page;
import com.nswt.framework.User;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.messages.LongTimeTask;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.Application;
import com.nswt.schema.ZCSiteSchema;

/**
 * վ������
 *
 */

public class CatalogSite extends Page {
	public static Mapx init(Mapx params) {
		ZCSiteSchema site = new ZCSiteSchema();
		site.setID(Application.getCurrentSiteID());
		site.fill();
		Mapx map = site.toMapx();
		if (StringUtil.isEmpty(site.getIndexTemplate())) {
			map.put("edit", "<a id='editLink' href='javascript:void(0);' onclick=\"openEditor('')\">" +
					"<img src='../Platform/Images/icon_edit.gif' width='14' height='14' style='display:none'></a>");
		} else {
			String s1 =
					"<a id='editLink' href='javascript:void(0);' onclick=\"openEditor('"+site.getIndexTemplate()
					+"')\"><img src='../Platform/Images/icon_edit.gif' width='14' height='14'></a>";
			map.put("edit", s1);
		}
		DataTable dt=new QueryBuilder("select * from zcmagazine where siteid=?",site.getID()).executeDataTable();
		map.put("Count", dt.getRowCount());
		params.putAll(map);
		return params;
	}

	// ����վ����ҳ
	public void publishIndex() {
		long id = publishTask(Application.getCurrentSiteID());
		Response.setStatus(1);
		$S("TaskID", "" + id);
	}
	
	private long publishTask(final long siteID) {
		LongTimeTask ltt = new LongTimeTask() {
			public void execute() {
				Publisher p = new Publisher();
				p.publishIndex(siteID,this);
				setPercent(100);
			}
		};
		ltt.setUser(User.getCurrent());
		ltt.start();
		return ltt.getTaskID();
	}
	
	/**
	 * ����վ��ģ��
	 *
	 */
	public void changeTemplate(){
		ZCSiteSchema site=new ZCSiteSchema();
		site.setID(Application.getCurrentSiteID());
		if(!site.fill()){
			Response.setLogInfo(0, "������ҳģ��ʧ��!");
			return;
		}
		String indexTemplate=$V("IndexTemplate");
		if(indexTemplate==null){
			Response.setLogInfo(0, "��ѡ��ģ��!");
			return;
		}
		String fileName = "";
		if ("avicit".equals(site.getAlias())){
			fileName = Config.getContextRealPath() +
					Config.getValue("Statical.TemplateDir") +
					"/" + Application.getCurrentSiteAlias() +
					indexTemplate;
		}else{
			fileName = Config.getContextRealPath() +
					Config.getValue("Statical.TemplateDir") +
					"/" + Application.getCurrentSiteAlias() +
					Config.getValue("Statical.WebRootDir")+
					indexTemplate;
		}
		fileName=fileName.replaceAll("///", "/");
		fileName=fileName.replaceAll("//", "/");
		File file=new File(fileName);
		if(!file.exists()){
			Response.setLogInfo(0, "��ģ���ļ�������!");
			return;
		}
		site.setIndexTemplate(indexTemplate);
		Transaction trans=new Transaction();
		trans.add(site, Transaction.UPDATE);
		if(trans.commit()){
			Response.setLogInfo(1, "������ҳģ��ɹ�!");
		}else{
			Response.setLogInfo(0, "������ҳģ��ʧ��!");
		}
	}

	/**
	 * ����Cookie�Զ��ض�����Ӧ��Ŀ��ҳ��
	 */
	public static void onRequestBegin(HttpServletRequest request, HttpServletResponse response) {
		String type = request.getParameter("Type");
		if (StringUtil.isEmpty(type)) {
			CookieImpl ci = new CookieImpl(request);
			String id = ci.getCookie("DocList.LastCatalog");
			if (StringUtil.isNotEmpty(id)) {
				String siteID = Application.getCurrentSiteID()+"";
				if(siteID.equals(CatalogUtil.getSiteID(id))){
					try {
						request.getRequestDispatcher("CatalogBasic.jsp?CatalogID=" + id).forward(request, response);
					} catch (ServletException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
