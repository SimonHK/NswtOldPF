package com.nswt.platform;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nswt.cms.pub.PubFun;
import com.nswt.framework.Ajax;
import com.nswt.framework.Config;
import com.nswt.framework.Constant;
import com.nswt.framework.User;
import com.nswt.framework.data.DataCollection;
import com.nswt.framework.data.DataRow;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.utility.Filter;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.StringUtil;
import com.nswt.schema.ZDUserSchema;
import com.nswt.schema.ZDUserSet;

/**
 * @Author ������
 * @Date 2007-6-18
 * @Mail nswt@nswt.com.cn
 */
public class Login extends Ajax {
	private static ArrayList wrongList = new ArrayList();

	public static void ssoLogin(HttpServletRequest request, HttpServletResponse response, String username) {
		if (username == null) {
			return;
		}
		ZDUserSchema user = new ZDUserSchema();
		user.setUserName(username);
		ZDUserSet userSet = user.query();

		if (!Config.isAllowLogin && !username.equalsIgnoreCase(UserList.ADMINISTRATOR)) {
			UserLog.log(UserLog.LOG, UserLog.LOGIN, "��ʱ��ֹ��¼.�û�����" + username, request.getRemoteAddr(), username);
			return;
		}

		if (userSet == null || userSet.size() < 1) {
			UserLog.log(UserLog.LOG, UserLog.LOGIN, "SSO��½ʧ��.�û�����" + username, request.getRemoteAddr(), username);

		} else {
			user = userSet.get(0);
			User.setUserName(user.getUserName());
			User.setRealName(user.getRealName());
			User.setBranchInnerCode(user.getBranchInnerCode());
			User.setType(user.getType());
			User.setValue("Prop1", user.getProp1());
			User.setValue("Prop2", user.getProp2());
			User.setValue("Prop3", user.getProp3());
			User.setValue("Prop4", user.getProp4());
			User.setManager(true);
			User.setLogin(true);

			UserLog.log(UserLog.LOG, UserLog.LOGIN, "��¼�ɹ�", request.getRemoteAddr());

			DataTable dt = new QueryBuilder("select name,id from zcsite order by BranchInnerCode ,orderflag ,id")
					.executeDataTable();
			dt = dt.filter(new Filter() {
				public boolean filter(Object obj) {
					DataRow dr = (DataRow) obj;
					return Priv.getPriv(User.getUserName(), Priv.SITE, dr.getString("ID"), Priv.SITE_BROWSE);
				}
			});

			if (dt.getRowCount() > 0) {
				Application.setCurrentSiteID(dt.getString(0, 1));
			} else {
				Application.setCurrentSiteID("");
			}

			try {
				String path = request.getParameter("Referer");
				LogUtil.info("SSOLogin,Referer:" + path);
				if (StringUtil.isNotEmpty(path)) {
					if (StringUtil.isNotEmpty(request.getParameter("t"))
							&& !"null".equalsIgnoreCase(request.getParameter("t"))) {
						response.sendRedirect(path + "?t=" + request.getParameter("t"));
					} else {
						response.sendRedirect(path);
					}
				} else {
					response.sendRedirect("Application.jsp");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void submit() {
		//LicenseInfo.verifyLicense(FileUtil.readText(Config.getContextRealPath() + "WEB-INF/classes/license.dat"));
		String userName = $V("UserName").toLowerCase();
		if (wrongList.contains(userName)) {
			Object authCode = User.getValue(Constant.DefaultAuthKey);
			if (authCode == null || !authCode.equals($V("VerifyCode"))) {
				Response.setStatus(0);
				Response.setMessage("��֤���������");
				return;
			}
		}
		String Password = StringUtil.md5Hex($V("Password"));
		ZDUserSchema user = new ZDUserSchema();
		user.setUserName(userName);
		ZDUserSet userSet = user.query();

		if (!Config.isAllowLogin && !user.getUserName().equalsIgnoreCase(UserList.ADMINISTRATOR)) {
			UserLog.log(UserLog.LOG, UserLog.LOGIN, "��ʱ��ֹ��¼.�û���" + $V("UserName"), Request.getClientIP(),
					$V("UserName"));
			Response.setStatus(0);
			Response.setMessage("��ʱ��ֹ��¼������ϵͳ����Ա��ϵ!");
			return;
		}

		if (userSet == null || userSet.size() < 1) {
			UserLog.log(UserLog.LOG, UserLog.LOGIN, "��½ʧ��.�û�����" + $V("UserName"), Request.getClientIP(), $V("UserName"));
			Response.setStatus(0);
			Response.setMessage("�û����������������");
			// �û�������������Ҫ���뵽wrongList
		} else {
			user = userSet.get(0);
			if (!user.getPassword().equalsIgnoreCase(Password)) {
				Response.setStatus(0);
				Response.setMessage("�û����������������");
				if (!wrongList.contains(userName)) {
					synchronized (wrongList) {
						if (wrongList.size() > 20000) {// ������û�������2����Ҫ��������ⱻ���⹥�������ڴ�й©
							wrongList.clear();
						}
						wrongList.add(userName);
					}
				}
				return;
			}
			if (!UserList.ADMINISTRATOR.equalsIgnoreCase(user.getUserName())
					&& UserList.STATUS_STOP.equals(user.getStatus())) {
				UserLog.log(UserLog.LOG, UserLog.LOGIN, "��½ʧ��.�û�����" + $V("UserName") + "��ͣ��", Request.getClientIP(),
						$V("UserName"));

				Response.setStatus(0);
				Response.setMessage("���û�����ͣ��״̬������ϵ����Ա��");
				return;
			}
			User.setUserName(user.getUserName());
			User.setRealName(user.getRealName());
			User.setBranchInnerCode(user.getBranchInnerCode());
			User.setType(user.getType());
			User.setValue("Prop1", user.getProp1());
			User.setValue("Prop2", user.getProp2());
			User.setValue("Prop3", user.getProp3());
			User.setValue("Prop4", user.getProp4());
			User.setManager(true);
			User.setLogin(true);

			UserLog.log(UserLog.LOG, UserLog.LOGIN, "��½�ɹ�", Request.getClientIP());

			DataTable dt = new QueryBuilder("select name,id from zcsite order by orderflag").executeDataTable();
			dt = dt.filter(new Filter() {
				public boolean filter(Object obj) {
					DataRow dr = (DataRow) obj;
					return Priv.getPriv(User.getUserName(), Priv.SITE, dr.getString("ID"), Priv.SITE_BROWSE);
				}
			});
			String siteID = this.getCookies().getCookie("SiteID");
			if (StringUtil.isNotEmpty(siteID) && Priv.getPriv(User.getUserName(), Priv.SITE, siteID, Priv.SITE_BROWSE)) {
				Application.setCurrentSiteID(siteID);
			} else {
				if (dt.getRowCount() > 0) {
					Application.setCurrentSiteID(dt.getString(0, 1));
				} else {
					Application.setCurrentSiteID("");
				}
			}

			Response.setStatus(1);
			synchronized (wrongList) {
				wrongList.remove(userName);
			}
			redirect("Application.jsp");
		}
	}

	public void getAllPriv() {
		getAllPriv(this.Response);
	}

	public static DataCollection getAllPriv(DataCollection Response) {
		if (UserList.ADMINISTRATOR.equalsIgnoreCase(User.getUserName())) {
			Response.put("isBranchAdmin", "Y");
		} else {
			List roleCodeList = PubFun.getRoleCodesByUserName(User.getUserName());
			if (roleCodeList != null && roleCodeList.size() != 0) {
				if (roleCodeList.contains("admin")) {
					Response.put("isBranchAdmin", "Y");
					return Response;
				}
			}
			Response.put("isBranchAdmin", "N");
			StringBuffer privTypes = new StringBuffer();
			Object[] ks = Priv.PRIV_MAP.keyArray();
			for (int i = 0; i < Priv.PRIV_MAP.size(); i++) {
				if (Priv.MENU.equals(ks[i])) {
					continue;
				}
				privTypes.append(ks[i].toString());
				privTypes.append(",");
			}
			privTypes.deleteCharAt(privTypes.length() - 1);
			Response.put("privTypes", privTypes.toString());

			Response.put(Priv.SITE + "DT",
					Priv.getSitePrivDT(User.getUserName(), Application.getCurrentSiteID() + "", Priv.SITE));

			for (int i = 0; i < Priv.PRIV_MAP.size(); i++) {
				if (Priv.MENU.equals(ks[i])) {
					continue;
				} else if (Priv.SITE.equals(ks[i])) {

				} else {
					Response.put(ks[i] + "DT", Priv.getCatalogPrivDT(User.getUserName(), Application.getCurrentSiteID()
							+ "", (String) ks[i]));
				}
			}
		}
		return Response;
	}
}
