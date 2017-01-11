<%@page import="com.nswt.framework.*"%>
<% 
	session.invalidate();
	response.sendRedirect(Config.getContextPath() + Config.getLoginPage());
%>