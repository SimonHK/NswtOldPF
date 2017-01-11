<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<z:init method="com.nswt.cms.site.ImagePlayerBasic.init">
<html>
<head>
<%
	String catalogInnerCode = request.getParameter("RelaCatalog");
	String value = "";
	String text = "APP应用";
	if (StringUtil.isNotEmpty(catalogInnerCode)&&!catalogInnerCode.equalsIgnoreCase("null")&&!catalogInnerCode.equals("0")) {
		value = CatalogUtil.getIDByInnerCode(catalogInnerCode);
		text = CatalogUtil.getNameByInnerCode(catalogInnerCode);
	}
%>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title>图片播放器</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
Page.onLoad(function(){
	Selector.setValueEx("RelaCatalogID",'<%=value%>','<%=text%>');
	var attr = $N("ImageSource");
	for (var i=0;i<attr.length;i++) {
		var ele = attr[i];
		$(ele).attachEvent("onchange",changeSource);
	}
});

function changeSource(){
	
}

function add(){
	var dc = Form.getData($F("form2"));
	if(Verify.hasError()){
		return;
	}
	dc.add("Prop1",$NV("Prop1"));
	Server.sendRequest("com.nswt.cms.site.ImagePlayerBasic.add",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				window.parent.Parent.DataGrid.loadData("dg1");
				if(window.parent.Parent.currentTreeItem){
					window.parent.Parent.Tree.loadData("tree1",function(){Tree.select("tree1","cid",window.parent.Parent.currentTreeItem.getAttribute("cid"))});
				}else{
					window.parent.Parent.Tree.loadData("tree1");
				}
				window.parent.location = Server.ContextPath+"Site/ImagePlayerDialog.jsp?"+response.get("ImagePlayerUrl");
			}
		});
	});
}

function selectPlayerStyle(){
	var diag = new Dialog("DiagStyle");
	diag.Width = 550;
	diag.Height = 300;
	diag.Title = "选择图片播放器样式";
	diag.URL = "Site/ImagePlayerSelectStyle.jsp?StyleID="+$V("StyleID");
	diag.OKEvent = afterSelect;
	diag.show();
}

function afterSelect(){
	var PlayerStyle = $DW.$NV("PlayerStyle");
	$S("StyleID",PlayerStyle);
	var dc = new DataCollection();
	dc.add("StyleID",PlayerStyle);
	Server.sendRequest("com.nswt.cms.site.ImagePlayerStyles.getPlayerImage",dc,function(response){
		$("StyleImg").src = response.get("Src");
		$D.close();																	 
	});
}

</script>
</head>
<body>
	<form id="form2" method="post">
	<table width="100%" border="0" cellpadding="2" cellspacing="2">
    	<tr>
			<td style="padding:4px 5px;" colspan="4"><z:tbutton onClick="add();"><img src="../Icons/icon039a16.gif" />保存</z:tbutton></td>
		</tr>
    	<tr>
    		<td width="17%">图片播放器名称：</td>
            <td width="47%"><input name="Name" type="text" value="${Name}" style="width:200px" class="input1" id="Name" verify="图片播放器名称|NotNull"/>
            <input name="ImagePlayerID" type="hidden" id="ImagePlayerID" value="${ImagePlayerID}" /></td>
            <td width="20%">&nbsp;</td>
            <td width="16%">&nbsp;</td>
        </tr>
        <tr>
    		<td width="17%">图片播放器代码：</td>
            <td width="47%">
            <input name="Code" type="text" value="${Code}" class="input1" id="Code" verify="图片播放器代码|NotNull" style="width:200px"/>
            <span style=" padding-left:30px;"><a href="#;" onClick="selectPlayerStyle();" >更换样式</a></span>
            </td>
            <td width="20%"></td>
            <td width="16%">&nbsp;</td>
        </tr>
        <tr>
    		<td>图片播放器样式：</td>
            <td><img id="StyleImg" name="StyleImg" src="../${StyleImgSrc}" border="0" onClick="selectPlayerStyle();" style="cursor:pointer" title="点击更换样式"/>
          <input type="hidden" id="StyleID" name="StyleID" value="${StyleID}"/>
          </td>
            <td colspan="2" valign="bottom">
            <table width="100%" border="0">
            	<tr>
                	<td width="21%" align="right">图片数：</td>
                    <td width="79%"><input name="DisplayCount" type="text" value="${DisplayCount}" size="5" class="input1" id="DisplayCount" verify="显示张数|NotNull&&Int" /></td>
                </tr>
            	<tr>
                	<td width="21%" align="right">宽度：</td>
                    <td width="79%"><input name="Width" type="text" value="${Width}" size="5" class="input1" id="Width" verify="宽度|NotNull&&Int" />像素(px)</td>
                </tr>
                <tr>
                	<td align="right">高度：</td>
                    <td><input name="Height" type="text" value="${Height}" class="input1" size="5" id="Height" verify="高度|NotNull&&Int" />像素(px)</td>
                </tr>
            </table>
            </td>
        </tr>
        <tr>
    		<td>图片来源：</td>
            <td colspan="3">${radiosImageSource}</td>
        </tr>
        <tr>
    		<td>所属栏目：</td>
            <td><z:select id="RelaCatalogID" listWidth="200" listHeight="300" listURL="Site/CatalogSelectList.jsp"></z:select></td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
        </tr>
	</table>
	</form>
</body>
</html>
</z:init>
