<%@ page contentType="text/html; charset=GBK"%> 
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="com.nswt.framework.*"%>
<%@page import="com.nswt.framework.utility.*"%>
<%@page import="com.nswt.framework.data.*"%>
<%@page import="com.nswt.framework.cache.*"%>
<%@page import="com.nswt.framework.orm.*"%>
<%@page import="com.nswt.framework.controls.*"%>
<%@page import="com.nswt.schema.*"%>
<%@page import="com.nswt.platform.*"%>
<%@page import="com.nswt.cms.site.*"%>
<%@page import="com.nswt.cms.document.*"%>
<%@page import="com.nswt.cms.dataservice.*"%>
<%@page import="com.nswt.cms.pub.*"%>
<%@page import="com.nswt.platform.pub.*"%>
<%@page import="com.nswt.bbs.ForumUtil"%>
<%
if(!com.nswt.framework.User.isLogin()||!com.nswt.framework.User.isManager()){
	out.println("<script>alert('�û��Ự��ʧЧ�������µ�½!');window.parent.location=\""+Config.getContextPath()+Config.getValue("App.LoginPage")+"\";</script>");
	return;
}else if(!com.nswt.platform.Priv.isValidURL(request.getServletPath())){
	out.println("<script>alert('��û�з��ʴ�ҳ���Ȩ��!����ϵ����Ա.');window.parent.location=\""+Config.getContextPath()+Config.getValue("App.LoginPage")+"\";</script>");
	return;
}
response.setHeader("Pragma","No-Cache");
response.setHeader("Cache-Control","No-Cache");
response.setDateHeader("Expires", 0);
%>
