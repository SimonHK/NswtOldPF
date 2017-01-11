<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.nswt.framework.User"%>
<%@page import="com.nswt.member.Login"%>
<%@taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
<title>质量承诺</title>
<link type="text/css" rel="stylesheet" href="../images/common.css" />
<link type="text/css" rel="stylesheet" href="../images/zlcn.css" />
<link type="text/css" rel="stylesheet" href="../images/common.css" />
<link type="text/css" rel="stylesheet" href="../images/zlcn.css" />
<link href="../../Include/Default.css" rel="stylesheet" type="text/css" />
<link href="../../Include/front-end.css" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<style type="text/css">
.news li {
	height:14px;
	line-height:14px;
}
</style>
<script src="../../Framework/Main.js"></script>
<script src="../../Framework/Spell.js"></script>
<script src="../../Framework/District.js"></script>
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
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要投票的企业！");
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
			DataGrid.loadData("dg1");
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
</head>
<body>
<div class="headerCon">
<div class="header"><!-- 页头 -->
<div class="top">
<div class="link"><a href="javascript:void(0);"
	onclick="this.style.behavior='url(#default#homepage)';this.setHomePage('http://www.quality.org.cn/');">设为首页</a>&nbsp;|&nbsp;<a
	href="#"
	onclick="window.external.AddFavorite(location.href,document.title);">加入收藏</a>&nbsp;|&nbsp;<a
	href="<cms:link type="catalog" name="联系方式">
        </cms:link>
        ">联系方式</a>&nbsp;|&nbsp;<a
	href="#">站点地图</a></div>
<div id="datetime"></div>
<script type="text/javascript">
			//单个数字配零
				function getDouble(number){
					var numbers=["0","1","2","3","4","5","6","7","8","9"];
					for(var i=0;i<numbers.length;i++){
						if(numbers[i]==number){
							return "0"+numbers[i];
						}else if(i==9){
							return number;
						}
						
					}
				}
			//得到当天时间
				function getTodayTime(){
					var days=["星期日","星期一","星期二","星期三","星期四","星期五","星期六"];
					var today=new Date();
					var str= (today.getYear()<1900?1900+today.getYear():today.getYear())+"年" + getDouble([today.getMonth()+1])+"月" +getDouble(today.getDate()) +"日 &nbsp;&nbsp;"+days[today.getDay()];
					document.getElementById('datetime').innerHTML=str;
					
				}
			//每隔一秒刷新一次
				setInterval("getTodayTime()",1000);
			</script></div>
<div style="position:relative; height:215px;"><object width="940"
	height="175"
	codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0"
	classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000">
	<param value="../images/banner.swf" name="movie" />
	<param value="transparent" name="wmode" />
	<embed width="940" height="175" type="application/x-shockwave-flash"
		wmode="transparent" src="../images/banner.swf" /></object>
<div class="navibar">
<ul class="nav">
	<li id="first"><a href="index.shtml">首页</a></li>
	<li><a href="#">质量达标</a></li>
	<li><a href="#">品牌建设</a></li>
	<li><a href="#">信誉建设</a></li>
	<li><a href="#">示范工程</a></li>
	<li><a href="#">检测机构</a></li>
	<li><a href="#">法律法规</a></li>
	<!-- <li id="<cms:var type='Catalog' name='技术攻关'>${catalog.InnerCode}</cms:var>"><a href="<cms:link type='Catalog' name='技术攻关'/>" >技术攻关</a></li>-->
	<li><a href="#">管理方法</a></li>
</ul>
</div>
</div>
<!-- 页头结束 --></div>

<div class="conmain">
<div class="content">
<div class="position pst"><a href="#">首页</a> > <a href="#">信誉建设</a>
> <a href="#">质量承诺</a></div>
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
				<td width="4%" ztype="RowNo"><strong>序号</strong></td>
				<td width="16%"><b>企业名称</b></td>
				<td width="18%"><b>省市</b></td>
				<td width="13%"><b>法人</b></td>
				<td width="12%"><b>承诺时间</b></td>
				<td width="8%"><b>承诺书</b></td>
			</tr>
			<z:datalist id="dg1"
				method="com.nswt.project.avicit.CommEnterPrise.dg2DataBind"
				page="false">
			<tr>
				<td>&nbsp;</td>
				<td><a href="${EnterpriseUrl}" target="_blank">${FullName}</a></td>
				<td>${PlaceName}</td>
				<td>${LegalPerson}</td>
				<td>${CommDate}</td>
				<td><a href="${CommWordUrl}">${CommWord}</a></td>
				<td width="4%">&nbsp;</td>
			</tr>
			</z:datalist>
		</table>
	<div style="margin-bottom: 8px; text-align: right; width: 910px;">
	<input type="button" onClick="vote()" value="投票" /></div>

	<div style="margin:0 auto; margin-top:10px;"><img
		src="../images/pminfo.jpg" /></div>
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
		action="${ServicesContext}${MessageActionURL}" method="post">
	<div class="subcomment">留言标题： <input type="text"
		class="commentin" style="width:280px;" id="Title" name="Title"
		value="" /> &nbsp;&nbsp;&nbsp;&nbsp; E-Mail: <input type="text"
		class="commentin" id="Email" name="Email" verify="Email" value="" />
	&nbsp;&nbsp;&nbsp;&nbsp; QQ: <input type="text" class="commentin"
		id="QQ" name="QQ" verify="Number" value="" /></div>
	<div style="margin-top:3px; padding-left:3px;"><textarea
		name="MsgContent" id="MsgContent" rows="7" class="commentin"
		style="width:900px;"></textarea></div>
	 <%
		if(!User.isLogin()){
		%>
        <div id="textLogin" style="margin-bottom:10px;">
        &nbsp;&nbsp;登录名：<input type="text" name="UserName" id="UserName" class="txt" /> 
        密码：<input type="password" name="PassWord" id="PassWord" class="txt" /> <input type="checkbox" name="HiddenUserName" id="HiddenUserName" checked/>匿名留言
        </div>
    	<%}%>
		&nbsp;&nbsp;<input type="button" name="MessageSubmit" id="MessageSubmit" value="提交留言" class="btn" onClick="checkContent();"/>
        <input type="hidden" id="BoardID" name="BoardID" value="${BoardID}" />
	</form>
	</div>
</z:init></div>
</div>
</div>
<div class="footerCon">
<div class="footer">
<div class="end">主办单位：工业和信息化部&nbsp;&nbsp;|&nbsp;&nbsp;承办单位：工业产品质量研究中心
</div>
</div>
</div>
</div>
</body>
</html>
