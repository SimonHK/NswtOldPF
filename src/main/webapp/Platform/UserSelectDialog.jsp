<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>选择用户</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript">

function checkAll(){
	var UserNames = $N("UserNames");
	for(var i=0;i<UserNames.length;i++){
		UserNames[i].checked = $("selectAll").checked;
	}
}

function onTreeClick(ele){
	var BranchInnerCode =  ele.getAttribute("BranchInnerCode");
	var ParentInnerCode = ele.getAttribute("ParentInnerCode");
	$S("BranchInnerCode",BranchInnerCode);
	$S("ParentInnerCode",ParentInnerCode);
	DataList.setParam("dg1","BranchInnerCode",BranchInnerCode);
	DataList.setParam("dg1","ParentInnerCode",ParentInnerCode);
	DataList.setParam("dg1","SearchContent","");
	DataList.setParam("dg1", Constant.PageIndex, 0);
	DataList.loadData("dg1");
}

function getSelectUser(){
	var returnEle = [];
	var UserNames = $NV("UserNames");
	var RealNames = [];
	if(UserNames==null){
		UserNames = [];
	}else{
		for(var i=0;i<UserNames.length;i++){
			RealNames.push($V(UserNames[i]+"_RealName"));
		}
	}
	returnEle.push(UserNames.join(","));
	returnEle.push(RealNames.join(","));
	return returnEle;
}

function doSearch(){
	DataList.setParam("dg1","BranchInnerCode",$V("BranchInnerCode"));
	DataList.setParam("dg1","ParentInnerCode",$V("ParentInnerCode"));
	DataList.setParam("dg1","SearchContent",$V("SearchContent"));
	DataList.setParam("dg1", Constant.PageIndex, 0);
	DataList.loadData("dg1");
}
</script>
</head>
<body>
<table width="100%" border="0" cellspacing="6" cellpadding="0"
	style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td>
		<table id="test" width="100%" border="0" cellspacing="0" cellpadding="6">
			<tr>
				<td align="right">
                <input type="hidden" id="BranchInnerCode" name="BranchInnerCode"/>
                <input type="hidden" id="ParentInnerCode" name="ParentInnerCode"/>
                <input type="text" id="SearchContent" name="SearchContent" value=""/><input type="button" value="查找" onclick="doSearch();" /></td>
			</tr>
			<tr>
				<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;" colspan="2">
					<table width="99%" border="1" cellpadding="2" cellspacing="0" bordercolor="#eeeeee">
						<tr bgcolor="#f6f6f6">
							<td width="10%" align="center"><b>机构</b></td>
							<td width="90%" align="center"><span style="float:left; vertical-align:middle; padding-left:10px;">
                            <label for="selectAll" hidefocus><input type="checkbox" id="selectAll" onClick="checkAll();"/>&nbsp;全选</label></span>
                            <b>用户</b></td>
						</tr>
						<tr valign="top">
							<td><z:tree id="tree1" style="height:270px;width:160px;" method="com.nswt.platform.UserSelect.treeDataBind" level="2" lazy="true" resizeable="false">
					<p cid='${BranchInnerCode}' BranchInnerCode='${BranchInnerCode}' ParentInnerCode='${ParentInnerCode}' onClick="onTreeClick(this);" oncontextmenu="showMenu(event,this);">${Name}</p></z:tree></td>
							<td>
                            <table width="100%" cellpadding="0" cellspacing="0" border="0">
                            <z:datalist id="dg1" method="com.nswt.platform.UserSelect.dg1DataBind" size="15">
                            	<tr>
                                	<td style="border-bottom:#CCC 1px dotted" align="left"><span style="padding-left:10px;">
                                  <input type="checkbox" name="UserNames" id="${UserName}" value="${UserName}"/>&nbsp;${UserName}&nbsp;(<b>${RealName}</b>)
                                  <input type="hidden" name="UserNames_RealName" id="${UserName}_RealName" value="${RealName}"/>
                                    </span></td>
                                </tr>
                            </z:datalist>
                            	<tr>
                                    <td align="center"><z:pagebar type="2" target="dg1" /></td>
                                </tr>
                            </table>
                            </td>
						</tr>
			  </table></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</body>
</html>
