<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.nswt.framework.Config"%>
<%@ taglib uri="controls" prefix="z" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>��Ա����</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css">
<link href="../Include/front-end.css" rel="stylesheet" type="text/css" />
<script src="<%=Config.getContextPath()%>Framework/Main.js" type="text/javascript"></script>
<%@ include file="../Include/Head.jsp" %>
<script type="text/javascript">

function doLogout(){
	Dialog.confirm("��ȷ���˳���",function(){
		window.location = "Logout.jsp?SiteID="+<%=request.getParameter("SiteID")%>;											
	});
}

function preview(ID){
	var SiteID = $V("SiteID");
	if(SiteID==""){
		Dialog.alert("վ�����");	
		return;
	}else{
		window.open("Preview.jsp?ArticleID="+ID+"&SiteID="+SiteID);
	}
}

function del(ID){
	if(confirm("ȷ��ɾ����ƪ������")){
		var dc = new DataCollection();
		dc.add("ID",ID);
		Server.sendRequest("com.nswt.member.Article.del",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					window.location.reload();
				}						   
			});
		});
	}
}

</script>
</head>
<body>
<%@ include file="../Include/Top.jsp" %>
<div class="wrap">
<%@ include file="Verify.jsp"%>
<br>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr valign="top">
    <td width="200"><%@ include file="../Include/Menu.jsp" %></td>
    <td width="20">&nbsp;</td>
    <td>  <div class="forumbox">
    <h4>�ҷ��������</h4>
    <ul class="tabs">
	<li class="current"><a href="#;">�ҵ�����</a></li>
    <li style="float:right"><a href="WriteArticle.jsp">����������</a></li>
	</ul>
      <table width="100%" border="1" cellpadding="0" cellspacing="0" bordercolor="#eeeeee" class="forumTable">
      <z:init method="com.nswt.member.Article.initSiteID">
        <thead>
          <tr>
            <th width="12%">��Ŀ<input type="hidden" id="SiteID" name="SiteID" value="${SiteID}"/></th>
            <th width="50%">����</th>
            <th width="16%">����ʱ��</th>
            <th width="8%">״̬</th>
            <th width="14%">����</th>
          </tr>
        </thead>
       </z:init>
         <z:datalist id="dg1" method="com.nswt.member.Article.dg1DataList" size="10" page="true">
        <tbody>
          <tr>
            <td>${CatalogName}</td>
            <td ><span id="thread_6417"><a href="#;" onclick="preview(${ID});">${Title}</a></span></td>
            <td><em>${AddTime}</em></td>
            <td>${StatusName}</td>
            <td>${DO}</td>
          </tr>
        </tbody>
         </z:datalist>
         <tbody>
          <tr>
            <td colspan="5"><z:pagebar target="dg1" /></td>
          </tr>
        </tbody>
      </table>
  </div>
</td>
  </tr>
</table>
</div>
<%@ include file="../Include/Bottom.jsp" %>
</body>
</html>