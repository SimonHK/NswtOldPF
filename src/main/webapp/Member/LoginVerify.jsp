<%@page import="com.nswt.platform.Application"%>
<%@page import="com.nswt.framework.User"%>
<%@page import="com.nswt.framework.data.QueryBuilder"%>
<%@page import="com.nswt.framework.utility.StringUtil"%>
<%@page import="com.nswt.cms.pub.SiteUtil"%>
<%
//�ж��Ƿ��¼
String cSiteID = request.getParameter("SiteID");
if(StringUtil.isEmpty(cSiteID)){
	out.println("document.write('δ����SiteID')");
	return;
}
if(User.isManager()||!User.isLogin()){
	StringBuffer sb = new StringBuffer();
	sb.append("document.write(\"<form id='formLoginVerify' name='formLoginVerify'>\");\n");
	sb.append("document.write(\"�û�����<input type='text' name='name' id='name' size='10'/>\");\n");
	sb.append("document.write(\"���룺<input type='password' name='code' id='code' size='10'/>&nbsp;\");\n");
	sb.append("document.write(\"<input type='button' class='btn' value='��¼' onClick='doTopLogin()'/>&nbsp;\");\n");
	sb.append("document.write(\"<input type='button' value='ע��' onClick='doTopRegister()' class='btn' />&nbsp;\");\n");
	//sb.append("document.write(\"[<a href='javascript:void(0);' onClick='goShopCart();'>���ﳵ</a>]&nbsp;\");\n");
	sb.append("document.write(\"</form>\");\n");
	out.println(sb.toString());
} else {
	StringBuffer sb = new StringBuffer();
	sb.append("document.write(\"<div id='loginMenu2'>\");\n");
	sb.append("document.write(\"<b>��ӭ " + User.getRealName() + "</b>&nbsp;\");\n");
	sb.append("document.write(\"[<a href='javascript:void(0);' onClick='goMemberCenter();'>��Ա����</a>]&nbsp;\");\n");
	/*if(SiteUtil.isShopEnable(cSiteID)){
		sb.append("document.write(\"[<a href='javascript:void(0);' onClick='goShopCart();'>���ﳵ</a>]&nbsp;\");\n");
		sb.append("document.write(\"[<a href='javascript:void(0);' onClick='goFavorite();'>�ղؼ�</a>]&nbsp;</form>\");\n");
	}*/
	sb.append("document.write(\"[<a href='javascript:void(0);' onClick='doTopLogout();'>�˳�</a>]\");\n");
	sb.append("document.write(\"</div>\");\n");
	out.println(sb.toString());
}
%>