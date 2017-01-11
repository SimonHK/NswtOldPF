<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title>图片播放器样式</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
Page.onLoad(function(){
	var StyleID = $V("StyleID");
	if(!StyleID||StyleID==null||StyleID==""){
		StyleID = "1";
	}
	$("Player"+StyleID).onclick();
});

function selectStyle(styleID){
	var arr = $N("PlayerStyle");
	for(var i=0;i<arr.length;i++){
		if(arr[i].checked==true){
			arr[i].checked=false;
			arr[i].getParent('LI').removeClassName('selected');
		}
	}
	$("Player"+styleID).checked = true;
	$("Player"+styleID).getParent('LI').addClassName('selected');
}
</script>
</head>
<body>
<z:init>
	<form id="form2">
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
            <td>
            <ul class="img-wrapper" style="margin:5px 0 5px 5px;">
            <z:datalist id="dg1" method="com.nswt.cms.site.ImagePlayerStyles.dg1DataList" size="9" page="true">
                <li style="height:120px; width:auto;">
                <dl>
                    <dt><img src='${CmsPath}${ImagePath}' title="${Memo}" onClick="selectStyle(${ID})"  height="75"></dt>
                    <dd style="text-align:center"><label for="Player${ID}"><input type="checkbox" name="PlayerStyle" id="Player${ID}" onClick="selectStyle(${ID})" value="${ID}"/>${Name}</label></dd>
                </dl>
                </li>
            </z:datalist>
            </ul>
          </td>
          <tr>
          	<td align="center"><z:pagebar type="2" target="dg1" /></td>
          </tr>
        </tr>
	</table>
    <input type="hidden" id="StyleID" name="StyleID" value="${StyleID}"/>
	</form>
</z:init>
</body>
</html>