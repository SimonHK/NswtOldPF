<%@page import="com.nswt.framework.Config"%>
<%@page import="com.nswt.framework.User"%>
<%@page import="com.nswt.framework.data.QueryBuilder"%>
<%@page import="com.nswt.framework.utility.StringUtil"%>
<%
//�ж��Ƿ��¼
if(!User.isMember()||!User.isLogin()){
	String cSiteID = request.getParameter("SiteID");
	if(StringUtil.isEmpty(cSiteID)){
		cSiteID = new QueryBuilder("select ID from ZCSite order by AddTime desc").executeOneValue()+"";
	}
	out.println("<script>alert('��δ��¼�����½!');window.parent.location='"+Config.getContextPath()+"/Member/Login.jsp?SiteID="+cSiteID+"';</script>");
	return;
}
%>