<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title>��Ŀ</title>
<script src="../Framework/Main.js"></script>
<script> 
//�������ǰҳ��ʱ�������Ҽ��˵�
var iFrame =parent.parent;
Page.onClick(function(){
	var div = iFrame.$("_DivContextMenu")
	if(div){
			$E.hide(div);
	}
});

var topFrame = window.parent;
function add(){
	topFrame.add();	
}


</script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
</head>
<body>
<div>
<table width="100%" cellpadding='0' cellspacing='0'>
	<tr>
		<td><z:tbutton onClick="add();">
			<img src="../Icons/icon002a2.gif" width="20" height="20" />�½��ڿ�</z:tbutton> <z:tbutton
			onClick="publish();">
			<img src="../Icons/icon002a1.gif" />����</z:tbutton></td>
	</tr>
</table>
</div>
</body>
</html>
