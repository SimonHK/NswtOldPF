<%@page import="com.nswt.framework.*"%>
<%@page import="com.nswt.framework.utility.*"%>
<%
	String domain = com.nswt.member.Login.getMainDomain();

	Cookie cookie = new Cookie("VerifyCookie", "");
	cookie.setMaxAge(0);
	cookie.setDomain(domain);
	cookie.setPath("/");
	response.addCookie(cookie);
		
	cookie = new Cookie("UserName", "");
	cookie.setMaxAge(0);
	cookie.setDomain(domain);
	cookie.setPath("/");
	response.addCookie(cookie);
	
	String SiteID = request.getParameter("SiteID");
	session.invalidate();
	String Referer = request.getParameter("Referer");
	if (StringUtil.isEmpty(Referer)) {
		Referer = request.getHeader("referer");
	}
	response.sendRedirect(Referer);
%>