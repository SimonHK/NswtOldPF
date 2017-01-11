<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.nswt.cms.pub.SiteUtil"%>
<%@taglib uri="controls" prefix="z"%>

<%@page import="com.nswt.framework.Config"%><html>
<%  
	String siteUrl = SiteUtil.getURL(request.getParameter("SiteID"));
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
<title>质量承诺</title>
<link type="text/css" rel="stylesheet" href="images/common.css" />
<link type="text/css" rel="stylesheet" href="images/zlcn.css" />
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<link href="../Include/front-end.css" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<style type="text/css">
.news li {
	height:14px;
	line-height:14px;
}
</style>
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
function doSearch(){
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","FullName",$V("FullName"));
	DataGrid.setParam("dg1","Place",$V("Place"));
	DataGrid.setParam("dg1","EnterpriseType",$V("EnterpriseType"));
	DataGrid.setParam("dg1","BeginDate",$V("BeginDate"));
	DataGrid.setParam("dg1","EndDate",$V("EndDate"));
	DataGrid.loadData("dg1");
}
function vote(){
	var arr = $NV("EnterpiseID");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要投票的企业！");
		return;
	}
	if(arr.length>1){
		Dialog.alert("一次只能给一个企业投票！");
		return;
	}
	var dc = new DataCollection();	
	dc.add("ID",arr.join());
	Server.sendRequest("com.nswt.project.avicit.CommEnterPrise.vote",dc,function(){
		var response = Server.getResponse();
		if(response.Status==0){
			Dialog.alert(response.Message);
		}else{
			Dialog.alert(response.Message);
			DataList.loadData("dg1");
		}
	});
} 

function checkContent(){
  var content = document.getElementById("MsgContent");
   if(!content.value||content.value.replace(/(^\s*)|(\s*$)/g,"") == ""){
     alert("留言内容不能为空!");
     content.focus();
     return;
   }else{
   	 document.getElementById("MegForm").submit();
   }
}
</script>
<%@ include file="../Include/Head.jsp" %>
</head>
<body>
<div class="headerCon">
<%@ include file="../Include/Top.jsp" %>
<div class="conmain">
<div class="content">
<div class="position pst"><a href="<%=siteUrl%>">首页</a> > <a href="<%=siteUrl%>syjs/index.shtml">信誉建设</a> > 质量承诺</div>
<div class="middcontent">
<h2 class="titlecn titlebg">承诺企业</h2>
<z:init method="com.nswt.project.avicit.CommEnterPrise.init">
	<div class="infoqy">
	<div class="sear">企业名称:&nbsp; <input type="text"
		style="width:430px;" id="FullName" />
	&nbsp;&nbsp;&nbsp;&nbsp;省市:&nbsp; <z:select style="background:white"
		id="Place" listHeight="300">${Place}</z:select></div>
	<div class="sear" style="margin-bottom:8px;">行业类别:&nbsp; <z:select
		id="EnterpriseType" style="background:white">${EnterpriseType}</z:select>
	&nbsp;&nbsp;承诺时间:&nbsp;从&nbsp; <span class="tdgrey2"> <input
		name="BeginDate" type="text" class="inputText" id="BeginDate"
		ztype="Date" size=14 /> </span> &nbsp;到&nbsp; <span class="tdgrey2">
	<input name="EndDate" type="text" class="inputText" id="EndDate"
		ztype="Date" size=14 /> </span>&nbsp;&nbsp; <input type="button"
		class=" searchConbtn" value="搜 索" style="cursor:pointer;"
		onclick="doSearch()" /></div>
	
		<table width="100%" cellpadding="2" cellspacing="0" class="tbinfo">
			<tr ztype="head" class="dataTableHead">
				<td ><b>序号</b></td>
				<td width="16%"><b>企业名称</b></td>
				<td width="18%"><b>省市</b></td>
				<td width="13%"><b>法人</b></td>
				<td width="12%"><b>承诺时间</b></td>
				<td width="8%"><b>承诺书</b></td>
				<td width="8%"><b>投票</b></td>
			</tr>
			<z:datalist id="dg1"
				method="com.nswt.project.avicit.CommEnterPrise.dg2DataBind"
				page="false">
			<tr>
				<td>${RowNumber}</td>
				<td>${FullName}</td>
				<td>${PlaceName}</td>
				<td>${LegalPerson}</td>
				<td>${CommDate}</td>
				<td><a href="${SiteUrl}${CommWordUrl}">${CommWord}</a></td>
				<td><input id='EnterpiseID_box${ID}' name='EnterpiseID' type='checkbox' value='${ID}'></td>
			</tr>
			</z:datalist>
		</table>
	<div style="margin-bottom: 8px; text-align: right; width: 910px;">
	<input type="button" onClick="vote()" value="投票" /></div>

	<div class="votephoto">
            <div style="color:#034883; padding-top:10px; padding-left:15px; text-align:left; font-size:14px;">投票排名</div>
            <table border="0" cellpadding="0" cellspacing="0" align="right" class="tbvote">
              <tr height="8" style="font-size:0px;">
                <td align="right" style="padding-right:10px;">&nbsp;</td>
                <td width="680" valign="middle" style="background:url(images/votetopline.jpg) no-repeat left bottom;"></td>
              </tr>
              <z:datalist id="dg5" method="com.nswt.project.avicit.CommEnterPrise.dg5DataList" page="false">
              <tr>
                <td align="right" style="padding-right:10px;"><span title="${FullName}">${ShortName}</span></td>
                <td width="680" valign="middle" class="line">
                <div class="mh" style="width:${Rate}%; background-color:${Color};">
                </div>
                </td>
              </tr>
              </z:datalist>
              <tr height="30">
               <td align="right" style="padding-right:10px;">&nbsp;</td>
                <td width="680" class="line1" style="vertical-align:top; text-align:left;" align="right">
                <div class="votefl votel">0</div>
                <z:datalist id="dg6" method="com.nswt.project.avicit.CommEnterPrise.dgVoteCountList" page="false">
                <div class="votefl votebom">${VoteBase}</div>
                </z:datalist>
                     <div class="votefl"  style="width:30px; padding-left:6px;">票数</div>
                
                </td>
              </tr>
            </table>
          </div>
        </div>
</z:init> <z:init method="com.nswt.cms.dataservice.MessageService.avicitinit">
	<div style="background-color:#ffffff;">
	<h2 class="comment">评论区</h2>
	<z:datalist id="dg2"
		method="com.nswt.cms.dataservice.MessageService.avicitdg1DataBind"
		size="10" page="true">
		<div style="line-height:20px;padding:4px 0 0 5px;">${Title}
		<div class="time" style="float:right">${AddUser}
		&nbsp;${AddTime}&nbsp; [IP:${IP}]</div>
		</div>
		<div class="message">${Content}</div>
		<div class="comment" style="background:none;height:8px;"></div>
	</z:datalist>
	<h2 class="comment"></h2>
	<div class="page"><z:pagebar target="dg2" type="1" /></div>
	<form id="MegForm" style="padding:0px; margin:0px; padding-top:6px;"
		action="<%=(Config.getValue("ServicesContext") + Config.getValue("MessageActionURL"))%>" method="post">
	<div class="subcomment">留言标题： <input type="text"
		class="commentin" style="width:280px;" id="Title" name="Title"
		value="" /> &nbsp;&nbsp;&nbsp;&nbsp; E-Mail: <input type="text"
		class="commentin" id="Email" name="Email" verify="Email" value="" />
	&nbsp;&nbsp;&nbsp;&nbsp; QQ: <input type="text" class="commentin"
		id="QQ" name="QQ" verify="Number" value="" /></div>
	<div style="margin-top:3px; padding-left:3px;"><textarea
		name="MsgContent" id="MsgContent" rows="7" class="commentin"
		style="width:900px;"></textarea></div>
		&nbsp;&nbsp;<input type="hidden" name="HiddenUserName" id="HiddenUserName" value="0"/>
		<input type="hidden" id="BoardID" name="BoardID" value="${BoardID}" />
		<input type="button" name="MessageSubmit" id="MessageSubmit" value="提交留言" class="btn" onClick="checkContent();"/>
	</form>
	</div>
</z:init></div>
</div>
</div>
<%@ include file="../Include/Bottom.jsp" %>
</div>
</body>
</html>
