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

function uploadLogo(){
	if($V("LogoFile")!=""){
		$("LogoForm").submit();
	}
}

function afterUpload(path){
	$S("Logo",path);
	var dc = Form.getData("LogoForm");
	Server.sendRequest("com.nswt.member.MemberInfo.doSave",dc,function(response){
		if(response.Status!=1){
			Dialog.alert(response.Message);
		}else{
			$("LogoPic").src = path + "?" + new Date().getTime();
		}
	});
}
</script>
</head>
<body>
<%@ include file="../Include/Top.jsp" %>
<%@ include file="Verify.jsp"%>
<div class="wrap">
<div id="nav" style="margin-bottom:12px">
	��ҳ  &raquo; <a href="../Member/MemberInfo.jsp?cur=Menu_MI&SiteID=<%=request.getParameter("SiteID")%>">��Ա����</a>  &raquo; �ϴ�ͷ��
</div>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr valign="top">
      <td width="200"><%@ include file="../Include/Menu.jsp" %></td>
      <td width="20">&nbsp;</td>
      <td>
          <div class="forumbox"> <span class="fr">�� <span style=" font-family:'����'; color:#F60">*</span> ��ʾΪ������</span>
            <h4>�༭��������</h4>
            <ul class="tabs ">
              <li><a href="MemberInfo.jsp?SiteID=<%=request.getParameter("SiteID")%>">��������</a></li>
              <li><a href="MemberInfoDetail.jsp?SiteID=<%=request.getParameter("SiteID")%>">��ϸ����</a></li>
              <li class="current"><a href="#;">�ϴ�ͷ��</a></li>
            </ul>
            <div style="display:none"><iframe name="formTarget" src="javascript:void(0)"></iframe></div>
            <h5 style="border-bottom:1px dotted #ccc; color:#333; padding:3px; margin:5px;">����ͷ��</h5>
             <z:init method="com.nswt.member.MemberInfo.init">
              <form id="LogoForm" target="formTarget" method="POST" enctype="multipart/form-data" action="Upload.jsp?Path=">
              <table width="80%" border="0" cellspacing="0" cellpadding="0" class="forumTable">
              <tr>
                <td width="20%" align="right">�û�ͷ��</td>
                <td width="80%"><img id="LogoPic" src="${Logo}" width="100" height="100" style="border:#DFDFDF 1px solid"/>
                <input type="hidden" id="UserName" name="UserName" value="${UserName}"/>
                <input type="hidden" id="Logo" name="Logo" value="${Logo}"/></td>
              </tr>
              <tr>
                <td width="20%" align="right">ѡ��ͷ���ļ���</td>
                <td width="80%"><input type="file" id="LogoFile" name="LogoFile" /> �Ƽ�100x100����</td>
              </tr>
            </table>
        	<h5 style="border-bottom:1px dotted #ccc; color:#333; padding:3px; margin:5px;"></h5>
            <br>
            <div style="padding-left:15%;">
            <input type="button" onClick="uploadLogo()" name="tiajiao" value="�� ��" class="button" />
            </div>
            </form></z:init>
          </div></td>
    </tr>
  </table>
</div>
<%@ include file="../Include/Bottom.jsp" %>
</body>
</html>