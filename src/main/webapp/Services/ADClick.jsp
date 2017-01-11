<%
String Services = Config.getValue("ServicesContext");
String SiteID = request.getParameter("SiteID");
String ADID   = request.getParameter("ADID");
String URL   = request.getParameter("URL");
com.nswt.cms.stat.impl.LeafStat.dealADClick(SiteID,ADID);
%>
<%@page import="com.nswt.framework.Config"%>
<script>
window.location = "<%=URL%>";
</script>