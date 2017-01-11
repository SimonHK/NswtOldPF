<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="controls" prefix="z" %>
<%@page import="com.nswt.framework.Config"%>
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

function save(){
	var dc = Form.getData("InfoForm");
	Server.sendRequest("com.nswt.member.MemberInfo.doDetailSave",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				window.location.reload();
			}								   
		});
	});
}

function UpdatePic(){
	if($V("PicFile")!=""){
		$("InfoForm").submit();
	}
}

function afterUpload(path){
	$S("Pic",path);
	$("companypic").src = path+"?time="+new Date().getTime();
}
</script>
</head>
<body>
<%@ include file="../Include/Top.jsp" %>
<div class="wrap">
<%@ include file="Verify.jsp"%>
<div id="nav" style="margin-bottom:12px">
	��ҳ  &raquo; <a href="../Member/MemberInfo.jsp?cur=Menu_MI&SiteID=<%=request.getParameter("SiteID")%>">��Ա����</a>  &raquo; �༭��������
</div>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr valign="top">
    	<td width="200"><%@ include file="../Include/Menu.jsp" %></td>
      <td width="20">&nbsp;</td>
      <td>
      <z:init method="com.nswt.member.MemberInfo.initDetail">
      <form id="InfoForm" target="formTarget" method="POST" enctype="multipart/form-data" action="Upload.jsp?Path=CompanyLogo/">
          <div class="forumbox"> <span class="fr">�� <span style=" font-family:'����'; color:#F60">*</span> ��ʾΪ������</span>
            <h4>�༭��������</h4>
            <ul class="tabs ">
              <li><a href="MemberInfo.jsp?SiteID=<%=request.getParameter("SiteID")%>">��������</a></li>
              <li class="current"><a href="#;">��ϸ����</a></li>
              <li><a href="Logo.jsp?SiteID=<%=request.getParameter("SiteID")%>">�ϴ�ͷ��</a></li>
            </ul>
            <h5 style="border-bottom:1px dotted #ccc; color:#333; padding:3px; margin:5px;">${MemberType}��ϸ��Ϣ
            <input type="hidden" id="UserName" name="UserName" value="${UserName}"/>
            <input type="hidden" id="Pic" name="Pic" value="${Pic}"/></h5>
            <div style="display:none"><iframe name="formTarget" src="javascript:void(0)"></iframe></div>
             <table width="100%" border="0" cellspacing="0" cellpadding="0" class="forumTable">
              <%if("Person".equals(User.getValue("Type"))){%>
              <tr>
                <td width="15%" align="right">���գ�</td>
                <td width="30%"><input id="Birthday" name="Birthday" type="text" class="textInput" size="30" value="${Birthday}"/></td>
                <td width="55%"><div class="paraphrastic">���ĳ�������</div></td>
              </tr>
              <tr>
                <td align="right">QQ��</td>
                <td><input id="QQ" name="QQ" type="text" class="textInput" size="30" value="${QQ}"/></td>
                <td><div class="paraphrastic">����QQ����</div></td>
              </tr>
              <tr>
                <td align="right">MSN��</td>
                <td><input id="MSN" name="MSN" type="text" class="textInput" size="30" value="${MSN}"/></td>
                <td><div class="paraphrastic">����MSN�˺�</div></td>
              </tr>
              <%}else{ %>
              <tr>
              	<td width="15%" align="right">��˾���ƣ�</td>
                <td width="30%"><input id="CompanyName" name="CompanyName" type="text" class="textInput" size="30" value="${CompanyName}"/></td>
                <td width="55%"><input id="PicFile" name="PicFile" type="File" class="textInput" size="25" value=""/>
                <input type="button" onClick="UpdatePic();" value="����������Ƭ"/></td>
              </tr>
              <tr>
              	<td width="11%" align="right">��˾��ģ��</td>
                <td width="34%"><input id="Scale" name="Scale" type="text" class="textInput" size="30" value="${Scale}"/></td>
                <td width="55%" rowspan="5" valign="middle">��ҵ������Ƭ:<br/>
                  <img id="companypic" width="240" height="180" src="${PicPath}" style="border:#DFDFDF 1px solid"/>&nbsp;����240*180</td>
              </tr>
              <tr>
              	<td width="11%" align="right">��Ӫҵ��</td>
                <td width="34%"><input id="BusinessType" name="BusinessType" type="text" class="textInput" size="30" value="${BusinessType}"/></td>
                </tr>
              <tr>
              	<td width="11%" align="right">��˾��Ʒ��</td>
                <td width="34%"><input id="Products" name="Products" type="text" class="textInput" size="30" value="${Products}"/></td>
                </tr>
              <tr>
              	<td width="11%" align="right">��˾��վ��</td>
                <td width="34%"><input id="CompanySite" name="CompanySite" type="text" class="textInput" size="30" value="${CompanySite}"/></td>
                </tr>
              <tr>
              	<td width="11%" align="right">���棺</td>
                <td width="34%"><input id="Fax" name="Fax" type="text" class="textInput" size="30" value="${Fax}"/></td>
                </tr>
              <tr>
              	<td width="11%" align="right">��ϵ�ˣ�</td>
                <td width="34%"><input id="LinkMan" name="LinkMan" type="text" class="textInput" size="30" value="${LinkMan}"/></td>
                 <td width="55%" rowspan="3">��˾��飺<br/><textarea id="Intro" name="Intro" cols="50" class="textInput">${Intro}</textarea></td>
                </tr>
              <%} %>
                <td align="right">�̶��绰��</td>
                <td><input id="Tel" name="Tel" type="text" class="textInput" size="30" value="${Tel}"/></td>
                </tr>
              <tr>
                <td align="right">�ֻ���</td>
                <td><input id="Mobile" name="Mobile" type="text" class="textInput" size="30" value="${Mobile}"/></td>
                </tr>
              <tr>
                <td align="right">��ϸ��ϵ��ַ��</td>
                <td><input id="Address" name="Address" type="text" class="textInput" size="30" value="${Address}"/></td>
                <td><div class="paraphrastic">������ϸ��ϵ��ַ</div></td>
              </tr>
              <tr>
                <td align="right">�������룺</td>
                <td><input id="ZipCode" name="ZipCode" type="text" class="textInput" size="30" value="${ZipCode}"/></td>
                <td><div class="paraphrastic">������������</div></td>
              </tr>
            </table>
            <h5 style="border-bottom:1px dotted #ccc; color:#333; padding:3px; margin:5px;"></h5>
            <div style=" padding-left:140px;">
              <button type="button" onClick="save()" name="tiajiao" value="true" class="submit">�ύ</button>
              &nbsp;
              <button type="reset" name="reset" value="false" class="button">�� ��</button>
            </div>
          </div>
        </form></z:init></td>
    </tr>
  </table>
</div>
<%@ include file="../Include/Bottom.jsp" %>
</body>
</html>