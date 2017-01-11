<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.nswt.framework.Config"%>
<%@ taglib uri="controls" prefix="z" %>
<%@page import="com.nswt.member.Member"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>��Ա����</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css">
<link href="../Include/front-end.css" rel="stylesheet" type="text/css" />
<script src="<%=Config.getContextPath()%>Framework/Main.js" type="text/javascript"></script>
<script src="<%=Config.getContextPath()%>Editor/fckeditor.js"></script>
<%@ include file="../Include/Head.jsp" %>
<script type="text/javascript">

function changeCatalog(){
	var CatalogID = $V("CatalogID");
	$S("Catalog",CatalogID);
}

function doLogout(){
	Dialog.confirm("��ȷ���˳���",function(){
		window.location = "Logout.jsp?SiteID="+<%=request.getParameter("SiteID")%>;												
	});
}

function afterUploadAttach(AttachID,FileName){
	addFujianDiv(AttachID,FileName);
	var AttachIDs = $V("AttachIDs");
	if(AttachIDs==""){
		$S("AttachIDs",AttachID);
	}else{
		$S("AttachIDs",AttachIDs+","+AttachID);
	}
}

function addFujianDiv(AttachID,FileName){
	$("AttachDiv").innerHTML = "<div id='"+AttachID+"' class='fujian' title='"+FileName+"' name='"+FileName+"'><div><img src='../Icons/icon403a11.gif' onClick=\"delFujian('"+AttachID+"');\"/></div>"+FileName+"&nbsp;&nbsp;</div>"+ $("AttachDiv").innerHTML;
}

function delFujian(id){
	$(id).style.display="none";
	var AttachArr = $V("AttachIDs").split(",");
	for(var i=0;i<AttachArr.length;i++){
		if(AttachArr[i]==id){
			AttachArr.splice(i,1);
		}
	}
	$S("AttachIDs",AttachArr.join(","));
}

var editor;
function doSubmit(){
	editor = FCKeditorAPI.GetInstance('Content');
    var content = editor.GetXHTML(false);
	var Catalog = $V("Catalog");
	if(Catalog.trim()==""){
		Dialog.alert("��ѡ����Ŀ");	
		return;
	}
	if($V("Title").trim()==""||content.trim()==""){
		Dialog.alert("����δ��������");	
		return;
	}
	var dc = Form.getData("ArticleForm");
	dc.add("Content",content);
	Server.sendRequest("com.nswt.member.Article.doSave",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				window.location = "MemberArticles.jsp";
			}						   
		});
	});
}

function uploadAttach(){
	if($V("UploadFile")!=""){
		$("UploadForm").submit();
	}
}

function UploadImage(path){
	editor = FCKeditorAPI.GetInstance('Content');
	editor.InsertHtml("<img src='"+path+"' height='200' />"|| "" ) ;
	FCKeditorAPI.GetInstance("Content").Focus();
}

</script>
<script type="text/javascript">
<%
boolean canWrite = false;
if(!User.isManager()&&User.isLogin()){
	Member m = new Member(User.getUserName());
	if(m.fill()){
		if(m.getStatus().equalsIgnoreCase("Y")){
			canWrite = true;
		}
	}
}
%>

function check(){
<%if(canWrite){%>
	var Catalog = $V("Catalog");
	var AttachIDs = $V("AttachIDs");
	var AttachNames = $V("AttachNames");
	if($V("ArticleID")!=""){
		$("CataName").innerHTML = $V("CatalogName");
		$("selectCata").style.display = "none";	
	}
	if(AttachIDs!=""){
		var AttachArr = AttachIDs.split(",");
		var NamesArr  = AttachNames.split(",");
		for(var i=0;i<AttachArr.length;i++){
			addFujianDiv(AttachArr[i],NamesArr[i]);
		}
	}
<%}%>
}
</script>
<style>
.fujian{
	float:left;margin:2px 6px 2px 0;padding:0 2px 0 6px; border:#CCC 1px solid; width:40%;overflow:hidden; height:20px;cursor:default;
}

.fujian img{
	padding-bottom:1px; cursor:pointer;
}

.fujian div{
	float:right;
}
</style>
</head>
<body onLoad="check();">
<%@ include file="../Include/Top.jsp" %>
<div class="wrap">
<%@ include file="Verify.jsp"%>
  <div id="nav"><a href="#">��ҳ</a> &raquo; ��վ����Ͷ��</div>
  <br>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr valign="top">
 	    <td width="200"><%@ include file="../Include/Menu.jsp" %></td>
      <td width="20">&nbsp;</td>
      <td><z:init method="com.nswt.member.Article.init">
          <div class="forumbox">
            <h4>����������</h4>
            <%if(canWrite){%>
            <table width="100%" border="1" cellpadding="0" cellspacing="0" bordercolor="#eeeeee" class="forumTable">
              <form id="ArticleForm">
                <thead>
                  <tr>
                    <th align="right" style="text-align:right" width="130">�û���</th>
                    <td width="582"><%=User.getValue("Name")%> [<a href="#;" onClick="doLogout();">�˳���¼</a>]
                      <input type="hidden" id="UserName" name="UserName" value="${UserName}"/>
                      <input type="hidden" id="ArticleID" name="ArticleID" value="${ArticleID}"/>
                      <input type="hidden" id="CatalogName" name="CatalogName" value="${CatalogName}"/>
                      <input type="hidden" id="Catalog" name="Catalog" value="${Catalog}"/>
                      <input type="hidden" id="AttachIDs" name="AttachIDs" value="${AttachIDs}"/>
                      <input type="hidden" id="AttachNames" name="AttachNames" value="${AttachNames}"/>
                    </td>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <th align="right" style="text-align:right" width="130">������Ŀ</th>
                    <td><span id="selectCata" style="margin-right:-195px;">
                      <z:select id="CatalogID" style=" width:193px" listWidth="200" listHeight="300" onChange="changeCatalog();" listURL="Member/CatalogSelectList.jsp"></z:select>
                      </span><b style="position:relative;background-color:#F7FAFC;" id="CataName"></b></td>
                  </tr>
                  <tr>
                    <th align="right" style="text-align:right">����</th>
                    <td><input type="text" name="Title" id="Title" size="45" style="width:45%" value="${Title}"></td>
                  </tr>
                ${CustomColumn}
                <tr>
                  <td align="right" valign="top"><b>����</b></td>
                  <td valign="top"><div id="posteditor">
                      <div id="Toolbar" style="height:52px;"></div>
                      <textarea id="_Content" name="_Content" style=" display:none;">${Content}</textarea>
       <script type="text/javascript">
		  var sBasePath = <%=Config.getContextPath()%>+"Editor/" ;
		  var oFCKeditor = new FCKeditor( 'Content' ) ;
		  oFCKeditor.BasePath	= sBasePath ;
		  oFCKeditor.ToolbarSet="MemberArticle"
		  oFCKeditor.Width  = '100%';
		  oFCKeditor.Height = 300;
		  oFCKeditor.Config['EditorAreaCSS'] = '${CssPath}';
		  oFCKeditor.Config[ 'ToolbarLocation' ] = 'Out:Toolbar' ;
		  oFCKeditor.Value = $V("_Content");
		  oFCKeditor.Create() ;
	  </script>
                      <table align="right" cellpadding="0" cellspacing="0" width="100%">
                        <tr>
                          <td width="67%"  valign="top" align="left"><div id="AttachDiv" style="width:100%;"></div></td>
                        </tr>
                      </table>
                    </div></td>
                </tr>
              </form>
              <form id="UploadForm" target="formTarget" method="POST" enctype="multipart/form-data" action="UploadMemberAttach.jsp?UserName=${UserName}">
                <tr>
                  <td>&nbsp;</td>
                  <td><fieldset class="fields2" style="clear:both;">
                    <legend>�ϴ�����</legend>
                    �ļ�:
                    <input type="file" value="" size="45" id="UploadFile" name="UploadFile">
                    &nbsp;&nbsp;
                    <button type="button" onClick="uploadAttach();" class="button2" name="add_file">�ϴ��ļ�</button>
                    </fieldset></td>
                </tr>
              </form>
              <tr>
                <td><div style="display:none">
                    <iframe name="formTarget" src="javascript:void(0)"></iframe>
                  </div></td>
                <td><button type="button" name="publish" id="publish" onclick="doSubmit();">��������</button></td>
              </tr>
              </tbody>
              
            </table>
            <%}else{%>
            <table width="100%" border="1" cellpadding="0" cellspacing="0" bordercolor="#eeeeee" class="forumTable">
              <tr>
                <td align="center" height="200"><b>�Բ�������δͨ������Ա��ˣ����ܷ���</b></td>
              </tr>
            </table>
            <%} %>
          </div>
        </z:init></td>
    </tr>
  </table>
</div>
<%@ include file="../Include/Bottom.jsp" %>
</body>
</html>
