<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html style=" overflow:hidden;">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>ͼƬ��</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function onImageOver(ele){
	ele.style.backgroundColor='#fffabf';
}
function onImageOut(ele){
	ele.style.backgroundColor='';
}

function clickImage(ele){
	var box = $("ImageID_box"+$(ele).$A("ImageID"));
	if(box.checked){
			if(box.type=="checkbox"){
				box.checked = false;
			}else{
				box.checked = true;	
			}
	}else{
			box.checked = true;
	}
}

function onReturnBack(){
	var arr = $NV("ImageID");
	if(!arr||arr.length==0){
		alert("����ѡ������Ҫ��ͼƬ��");
		return;
	}
	if(window.parent.Parent.onReturnBack){
		window.parent.Parent.onReturnBack(arr.join());
	}
	window.parent.Dialog.close();
}
</script>
</head>
<body>
<div>
<table width="99%" align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td>
        <%if(StringUtil.isEmpty(request.getParameter("ImageIDs"))){%>
        	<div style="height:500px;text-align:center;"><table width="100%" height="300" border="0"><tr><td valign="middle" align="center"><b style="color:#F00;">������û��ͼƬ</b></td></tr></table></div>
        <%}else{%>
		<ul class="img-wrapper"
			style="height:340px; overflow:hidden; display:block;">
			<z:datalist id="dg1" method="com.nswt.cms.document.Article.relaImageDataBind">
				<li style="height:160px">
						<dl>
							<dt><a href='#;' hidefocus><em><img imageid='${id}' src='..${Alias}${Path}s_${FileName}?${ModifyTime}' title='${name}' onClick='clickImage(this)'></em></a></dt>
							<dd style="text-align:center"><em><label><input id='ImageID_box${id}' name='ImageID' type='radio' value='${id}' ${checkStatus}>${name}</label></em></dd>
						</dl>
						</li>
			</z:datalist>
		</ul>
        <%}%>
		</td>
	</tr>
</table>
</div>
</body>
</html>