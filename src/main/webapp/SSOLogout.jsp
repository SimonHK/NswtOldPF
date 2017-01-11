<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="com.nswt.zas.*"%>
<%@page import="com.nswt.zas.client.*"%>
<%@page import="com.nswt.framework.*"%>

<%
if(session.getAttribute(com.nswt.zas.Constant.UserSessionAttrName)!=null){
	UserData user = (UserData) session.getAttribute(com.nswt.zas.Constant.UserSessionAttrName);
	//PGTUtil.removePGT(user.getUserName());
	session.invalidate();
	String renew = ClientConfig.isNeedNewLogin() ? "&renew=" + ClientConfig.isNeedNewLogin() : "";
	response.sendRedirect(ClientConfig.getServerURL() + com.nswt.zas.Constant.LogoutPage + "?service=" + ZASFilter.getReferer(request) + renew);
}else{
	session.invalidate();
	response.sendRedirect(Config.getContextPath() + Config.getValue("App.LoginPage"));
}
%>
