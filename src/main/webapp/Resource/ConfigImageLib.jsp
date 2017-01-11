<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<%@page import="org.dom4j.*"%>
<%@page import="org.dom4j.io.*"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<style>
#hotarea{width:160px; height:120px; border:#147 1px solid; background:#ca6 url(../Platform/Images/picture.jpg) no-repeat; position:relative}
#hotarea  a{ position:absolute; display:block; width:35px; height:25px; border:#fff 1px dashed; text-align:center; line-height:24px; color:#fff;}
#hotarea  a:hover{ text-decoration:none; border:#fff 1px solid; color:#fff}
</style>
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
var count = 0;
Page.onLoad(function(){
	setHotArea();
});

function clickWaterMarkType(index){
	if(!index){
		index ="";
	}
	if($NV("WaterMarkType"+index)=="Image"){
		$E.hide($('tr_Text'+index));
		$E.hide($('tr_FontSize'+index));
		$E.hide($('tr_FontColor'+index));
		$E.show($('tr_ImageFile'+index));
	}else 	if($NV("WaterMarkType"+index)=="Text"){
		$E.show($('tr_Text'+index));
		$E.show($('tr_FontSize'+index));
		$E.show($('tr_FontColor'+index));
		$E.hide($('tr_ImageFile'+index));
	}
}

function clickSizeType(index){
	if($V("SizeType"+index)=="0"||$V("SizeType"+index)=="3"){
		$("div_Width"+index).show();
		$("div_Height"+index).show();
	}else if($V("SizeType"+index)=="1"){
		$("div_Width"+index).show();
		$("div_Height"+index).hide();
	}else if($V("SizeType"+index)=="2"){
		$("div_Width"+index).hide();
		$("div_Height"+index).show();
	}
}

function selectposition(ele){
	var arr=$("hotarea").getElementsByTagName("a");
	for(i=0;i< arr.length;i++){
		arr[i].style.backgroundColor='';
	}
	ele.style.backgroundColor='#fff';
	$S("Position",ele.getAttribute("value"));
}

function setHotArea(){
	var arr=$("hotarea").getElementsByTagName("a");
	var p = $V("Position");
	for(i=0;i< arr.length;i++){
		if(p==arr[i].getAttribute("value")){
			arr[i].style.backgroundColor='#fff';
		}else{
			arr[i].style.backgroundColor='';
		}
	}
}

function save(mess){
	var dc = Form.getData("myform");
	var HasAbbrImages = $N("HasAbbrImage");
	if(HasAbbrImages){
		dc.add("Count",HasAbbrImages.length+"");
		for(var i= 1;i<=HasAbbrImages.length;i++){
			dc.add("AbbrImageIndex"+i,HasAbbrImages[i-1].$A("index"));
			dc.add(HasAbbrImages[i-1].id,HasAbbrImages[i-1].checked? "1":"0");
		}
	}else{
		dc.add("Count","0");
	}
	Server.sendRequest("com.nswt.cms.resource.ConfigImageLib.save",dc,function(response){
		if(mess){
			Dialog.alert(mess,function(){
				window.location.reload();
			});
		}else{
			Dialog.alert(response.Message);		
		}
	});
}

function upload(index){
	if(!index){
		index ="";
	}
	if($V("WaterMarkImage"+index)!=null&&$V("WaterMarkImage"+index).trim()!=""){
		$F("myform").method="POST";
		$F("myform").enctype="multipart/form-data";
		$F("myform").action="./ConfigImageLibUploadSave.jsp";
		$F("myform").submit();
	}else{
		Dialog.alert("�������ͼƬ!");
	}
}

function afterUpload(ImageFileIndex,ImageSrc,Image){
	$(ImageFileIndex.replace("WaterMarkImage","ImageFile")).src = ImageSrc+"?x="+new Date().getMilliseconds();
	$S(ImageFileIndex.replace("WaterMarkImage","Image"),Image);
	save('�ϴ��ɹ�');
}

function delAbbrImage(ele){
	Dialog.confirm("��ȷ��Ҫɾ���������ͼ��������Ϣ��",function(){
		var e = $(ele).getParent("tr",$(ele));
		$("ConfigTable").deleteRow(e.rowIndex);
		count--;
	});
}

function addAbbrImage(ele){
	var arr =[];
	count = count + 1;
	arr.push("<fieldset style=' margin:0 0 0 10px; height:99%'>");
	arr.push("<legend>");
	arr.push("<label><input type='checkbox' id ='HasAbbrImage"+count+"' name='HasAbbrImage' index='"+count+"' value='1' checked >����ͼ("+count+")</label>");
	arr.push("<input type='image' src='../Framework/Images/icon_cancel.gif' title='ɾ��' onclick='delAbbrImage(this);' />");
	arr.push("</legend>");
	arr.push("<table width='100%' cellpadding='2' cellspacing='0' class='tableborder' id='AbbrImageTable"+count+"' style=''>");
	arr.push("<tr>");
	arr.push("<td width='24%' align='right'><label>���Գߴ磺</label></td>");
	arr.push("<td width='76%' align='left'><select id = 'SizeType"+count+"' name='SizeType"+count+"'  onchange='clickSizeType("+count+")'>");
	arr.push("<option value='0' >��Ӧ�ڿ�Ⱥ͸߶�</option>");
	arr.push("<option value='1' >����Ӧ�ڿ��</option>");
	arr.push("<option value='2' >����Ӧ�ڸ߶�</option>");
	arr.push("<option value='3' >�̶���Ⱥ͸߶�</option>");
	arr.push("</select></td>");
	arr.push("</tr>");
	arr.push("<tr>");
	arr.push("<td>&nbsp;</td>");
	arr.push("<td align='left'><div id='div_Width"+count+"'>");
	arr.push("��");
	arr.push("<input id='Width"+count+"' name='Width"+count+"' type='text' value='500' maxlength='10' style='width:60px'></div>");
	arr.push("<div id='div_Height"+count+"'>�ߣ�");
	arr.push("<input id='Height"+count+"' name='Height"+count+"' type='text' value='500' maxlength='10' style='width:60px'>");
	arr.push("</div></td>");
	arr.push("</tr>");
	arr.push("<tr>");
	arr.push("<tr>");
	arr.push("<td width='27%' align='right'><label>�Ƿ�ˮӡ��</label></td>");
	arr.push("<td width='73%' align='left'>");
	arr.push("<label><input id='HasWaterMark"+count+"' name='HasWaterMark"+count+"' type='checkbox' value='1' checked ></label>");
	arr.push("</td>");
	arr.push("</tr>");
	arr.push("<td colspan='2'>");
	arr.push("<table width='100%' cellpadding='2' cellspacing='0' class='tableborder' id ='WaterMarkTable"+count+"'>");
	arr.push("<tr>");
	arr.push("<td width='27%' align='right'><div align='right'>ˮӡλ�ã�</div></td>");
	arr.push("<td width='73%' align='left'><select id = 'Position"+count+"' name='Position"+count+"' style='width:60px'>");
	arr.push("<option value='1' >����</option>");
	arr.push("<option value='2' >����</option>");
	arr.push("<option value='3' >����</option>");
	arr.push("<option value='4' >����</option>");
	arr.push("<option value='5' >��</option>");
	arr.push("<option value='6' >����</option>");
	arr.push("<option value='7' >����</option>");
	arr.push("<option value='8' >����</option>");
	arr.push("<option value='9' selected>����</option>");
	arr.push("<option value='0' >���</option>");
	arr.push("</select></td>");
	arr.push("</tr>");
	arr.push("<tr>");
	arr.push("<td width='27%' align='right'>ˮӡ���ͣ�</td>");
	arr.push("<td width='73%' align='left'><label>");
	arr.push("<input name='WaterMarkType"+count+"' type='radio' onClick='clickWaterMarkType("+count+")' value='Image' checked>ͼƬ</label>");
	arr.push("<label>");
	arr.push("<input name='WaterMarkType"+count+"' onClick='clickWaterMarkType("+count+")' type='radio' value='Text'> ����</label></td>");
	arr.push("</tr>");
	arr.push("<tr id='tr_Text"+count+"'  style='display:none'>");
	arr.push("<td width='27%' align='right'>ˮӡ���֣�</td>");
	arr.push("<td width='73%' align='left'><input name='Text"+count+"' id='Text"+count+"' type='text' value=''></td>");
	arr.push("</tr>");
	arr.push("<tr id='tr_FontSize"+count+"' style='display:none'>");
	arr.push("<td align='right'>���ִ�С��</td>");
	arr.push("<td align='left'><select id = 'FontSize"+count+"' name='FontSize"+count+"' style='width:60px'>");
	arr.push("<option value='9' >С��</option>");
	arr.push("<option value='10' >���</option>");
	arr.push("<option value='12' >С��</option>");
	arr.push("<option value='14' >�ĺ�</option>");
	arr.push("<option value='15' >С��</option>");
	arr.push("<option value='16' >����</option>");
	arr.push("<option value='18' >С��</option>");
	arr.push("<option value='22' >����</option>");
	arr.push("<option value='24' >Сһ</option>");
	arr.push("<option value='26' >һ��</option>");
	arr.push("<option value='36' >С��</option>");
	arr.push("<option value='42' >����</option>");
	arr.push("</select></td>");
	arr.push("</tr>");
	arr.push("<tr id='tr_FontColor"+count+"' style='display:none' >");
	arr.push("<td align='right'>������ɫ��</td>");
	arr.push("<td align='left'><select id = 'FontColor"+count+"' name='FontColor"+count+"' style='width:60px'>");
	arr.push("<option value='-1' >��</option>");
	arr.push("<option value='-16711936' >��</option>");
	arr.push("<option value='-16776961' >��</option>");
	arr.push("<option value='-16777216' >��</option>");
	arr.push("<option value='-256' >��</option>");
	arr.push("<option value='-65536' >��</option>");
	arr.push("<option value='-8355712' >��</option>");
	arr.push("<option value='System' >ˮӡ���ִ�С</option>");
	arr.push("</select></td>");
	arr.push("</tr>");
	arr.push("<tr id='tr_ImageFile"+count+"' style=''>");
	arr.push("<td width='27%' align='right'>ˮӡͼƬ��</td>");
	arr.push("<td width='73%' align='left'>");
	arr.push("<input id='Image"+count+"' name='Image"+count+"' type='hidden' value=''>");
	arr.push("<input id='ImageFile"+count+"' name='ImageFile"+count+"' type='image' src=''><br>");
	arr.push("<input name='WaterMarkImage"+count+"' id='WaterMarkImage"+count+"' type='file' value='' size='20'>");
	arr.push("<input onClick='upload(\""+count+"\");' type='button' value='�ϴ�' >");
	arr.push("</td>");
	arr.push("</tr>");
	arr.push("</table></td></tr></table>");
	arr.push("</fieldset>");
	
	var tableEle = $("ConfigTable");
	var e = $(ele).getParent("tr",$(ele));
	var row = tableEle.insertRow(tableEle.rows.length);
	var cell = row.insertCell(0);
	cell.innerHTML = arr.join('');
	cell.setAttribute("height","270px");
	cell.setAttribute("align","center");
	cell.setAttribute("valign","top");
}
</script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
</head>
<body style="background-color:transparent;">
<div style="display:none"><iframe name="formTarget"
	src="javascript:void(0)"></iframe></div>
<form id="myform" target="formTarget" enctype="multipart/form-data">
<%
    SAXReader sax = new SAXReader();
	Document doc = null;
	try {
		String configXML = new QueryBuilder("select configXML from zcsite where id = ?",Application.getCurrentSiteID()).executeString();
		StringReader reader = new StringReader(configXML);
		doc = sax.read(reader);
	} catch (DocumentException e1) {
		e1.printStackTrace();
	}
	Element root = doc.getRootElement();
	Element ImageLibConfig = root.element("ImageLibConfig");
	%>
<table width="100%" border='0' cellpadding='10' cellspacing='0'>
	<tr>
		<td width="50%" valign="top">
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="0" id="ConfigTable">

			<%
	    List elements = ImageLibConfig.element("AbbrImages").elements();
		for (int i = 0; i < elements.size(); i++) {
			Element AbbrImage = (Element) elements.get(i);
			String ID = AbbrImage.attributeValue("ID");
			String checked = "";
			if("1".equals(AbbrImage.attributeValue("HasAbbrImage"))){
				checked = "checked";
			}
		%>
			<tr>
				<td valign="top">
				<fieldset><legend> <label> <input
					type="checkbox" id="HasAbbrImage<%=ID%>" name="HasAbbrImage"
					index="<%=ID%>" value="1" <%=checked %>> ����ͼ(<%=ID%>)</label> <input
					type='image' src='../Framework/Images/icon_cancel.gif' title='ɾ��'
					onClick='delAbbrImage(this);' /> </legend>
				<table width="100%" cellpadding="5" cellspacing="0"
					id="AbbrImageTable<%=ID%>" name="AbbrImageTable">
					<tr>
						<td width="28%" align="right" nowrap>���Գߴ磺</td>
						<td width="73%" align="left">
	<select id = 'SizeType<%=ID%>' name='SizeType<%=ID%>'  onchange='clickSizeType(<%=ID%>)'>
	<option value='0' <% if("0".equals(AbbrImage.attributeValue("SizeType"))){out.print("selected");}%>>��Ӧ�ڿ�Ⱥ͸߶�</option>
	<option value='1' <% if("1".equals(AbbrImage.attributeValue("SizeType"))){out.print("selected");}%>>����Ӧ�ڿ��</option>
	<option value='2' <% if("2".equals(AbbrImage.attributeValue("SizeType"))){out.print("selected");}%>>����Ӧ�ڸ߶�</option>
	<option value='3' <% if("3".equals(AbbrImage.attributeValue("SizeType"))){out.print("selected");}%>>�̶���Ⱥ͸߶�</option>
	</select>
						</td>
					</tr>
					<tr>
						<td align="right">&nbsp;</td>
						<td align="left">
						<div id="div_Width<%=ID%>">�� <input id="Width<%=ID%>"
							name="Width<%=ID%>" type="text"
							value="<%=AbbrImage.attributeValue("Width")%>" maxlength="10"
							style="width:60px"></div>
						<div id="div_Height<%=ID%>">�ߣ� <input id="Height<%=ID%>"
							name="Height<%=ID%>" type="text"
							value="<%=AbbrImage.attributeValue("Height")%>" maxlength="10"
							style="width:60px"></div>
						</td>
					</tr>
					<%
                  Element WaterMark = AbbrImage.element("WaterMark"); 
                  checked = "";
	              if("1".equals(WaterMark.attributeValue("HasWaterMark"))){
	              	  checked = "checked";
	              }
                  %>
					<tr>
						<td width="27%" align="right">�Ƿ�ˮӡ��</td>
						<td width="73%" align="left"><label><input
							id="HasWaterMark<%=ID%>" name="HasWaterMark<%=ID%>"
							type="checkbox" value="1" <%=checked %>></label></td>
					</tr>
					<tr>
						<td colspan="2">
						<table width="100%" cellpadding="4" cellspacing="0"
							id="WaterMarkTable<%=ID%>">
							<tr>
								<td width="27%" align="right" nowrap>ˮӡλ�ã�</td>
								<td width="73%" align="left">
								<z:select >
									<select id="Position<%=ID%>" name="Position<%=ID%>" style="width:60px">
									<%=HtmlUtil.codeToOptions("WaterMark.Position", WaterMark.attributeValue("Position"))%>
									</select>
								</z:select>
								</td>
							</tr>
							<tr>
								<td align="right">ˮӡ���ͣ�</td>
								<td align="left">
								<%
                            String TextStyle="";
                            String ImageStyle = "";
				              if("Image".equals(WaterMark.attributeValue("WaterMarkType"))){
				            	  TextStyle="display:none";
				              %> <label><input name="WaterMarkType<%=ID%>"
									type="radio" onClick="clickWaterMarkType(<%=ID%>)"
									value="Image" checked>ͼƬ</label> <label><input
									name="WaterMarkType<%=ID%>" type="radio"
									onClick="clickWaterMarkType(<%=ID%>)" value="Text">����</label> <%}else{
				            	  ImageStyle="display:none";
				              %> <label><input name="WaterMarkType<%=ID%>"
									type="radio" onClick="clickWaterMarkType(<%=ID%>)"
									value="Image">ͼƬ</label> <label><input
									name="WaterMarkType<%=ID%>" type="radio"
									onClick="clickWaterMarkType(<%=ID%>)" value="Text" checked>����</label>
								<%}%>
								</td>
							</tr>
							<% 
                        Element Text = WaterMark.element("Text"); %>
							<tr id="tr_Text<%=ID%>" style="<%=TextStyle %>">
								<td width="27%" align="right">ˮӡ���֣�</td>
								<td width="73%" align="left"><input name="Text<%=ID%>"
									id="Text<%=ID%>" type="text" value="<%=Text.getText() %>"></td>
							</tr>
							<tr id="tr_FontSize<%=ID%>" style="<%=TextStyle %>">
								<td align="right">���ִ�С��</td>
								<td align="left">
								<z:select>
								<select id="FontSize<%=ID%>" name="FontSize<%=ID%>" style="width:60px">
								<%=HtmlUtil.codeToOptions("WaterMark.FontSize", Text.attributeValue("FontSize"))%>
								</select>
								</z:select>
								</td>
							</tr>
							<tr id="tr_FontColor<%=ID%>" style="<%=TextStyle %>">
								<td align="right">������ɫ��</td>
								<td align="left">
								<z:select>
									<select id="FontColor<%=ID%>" name="FontColor<%=ID%>" style="width:60px">
									<%=HtmlUtil.codeToOptions("WaterMark.FontColor", Text.attributeValue("FontColor"))%>
									</select>
								</z:select>
								</td>
							</tr>
							<% 
                        Element Image = WaterMark.element("Image"); %>
							<tr id="tr_ImageFile<%=ID%>" style="<%=ImageStyle %>">
								<td width="27%" align="right" valign="top">ˮӡͼƬ��</td>
								<td width="73%" align="left"><input id="Image<%=ID%>"
									type="hidden" value="<%=Image.attributeValue("src")%>">
								<img id="ImageFile<%=ID%>"
									src="<%=(Config.getContextPath()+Config.getValue("UploadDir")+"/"
                        		  +Application.getCurrentSiteAlias()+"/"+Image.attributeValue("src")).replaceAll("//","/")+"?x="+System.currentTimeMillis()%>" /><br>
								<input name='WaterMarkImage<%=ID%>' id='WaterMarkImage<%=ID%>'
									type='file' value='' size='20'> <input
									onClick="upload('<%=ID%>');" type='button' value='�ϴ�'>
								</td>
							</tr>
						</table>
						</td>
					</tr>
				</table>
				</fieldset>
				</td>
			</tr>
			<%} %>
		</table>
		</td>
		<td width="50%" valign="top">
		<%
	Element WaterMark = ImageLibConfig.element("WaterMark"); 
	String checked = "";
	if("1".equals(WaterMark.attributeValue("HasWaterMark"))){
		checked = "checked";
	}
	%>
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td>
				<fieldset><legend> <label> <input
					type="checkbox" id="HasWaterMark" name="HasWaterMark" value="1"
					<%=checked %>> ԭͼˮӡ</label> </legend>
				<table width="100%" cellpadding="5" cellspacing="0"
					id="WaterMarkTable">
					<tr>
						<td width="27%" align="right" valign="top" nowrap>ˮӡλ�ã�</td>
						<td width="73%" align="left"><input id="Position"
							name="Position" type="hidden"
							value="<%=WaterMark.attributeValue("Position")%>">
						<div id="hotarea"><a value="1"
							onClick="selectposition(this);" href="#;"
							style="left:5px; top:5px;">����</a> <a value="2"
							onClick="selectposition(this);" href="#;"
							style="left:40%; top:5px;">��</a> <a value="3"
							onClick="selectposition(this);" href="#;"
							style="right:5px; top:5px;">����</a> <a value="4"
							onClick="selectposition(this);" href="#;"
							style="left:5px; top:40%">��</a> <a value="5"
							onClick="selectposition(this);" href="#;"
							style="left:40%; top:40%">��</a> <a value="6"
							onClick="selectposition(this);" href="#;"
							style="top:40%; right:5px;">��</a> <a value="7"
							onClick="selectposition(this);" href="#;"
							style=" left:5px; bottom:5px;">����</a> <a value="8"
							onClick="selectposition(this);" href="#;"
							style="left:40%; bottom:5px;">��</a> <a value="9"
							onClick="selectposition(this);" href="#;"
							style="right:5px; bottom:5px;">����</a></div>
						</td>
					</tr>
					<tr>
						<td align="right">ˮӡ���ͣ�</td>
						<td align="left">
						<%
              String TextStyle="";
              String ImageStyle = "";
              if("Image".equals(WaterMark.attributeValue("WaterMarkType"))){
            	  TextStyle = "display:none";
              %> <label><input name="WaterMarkType" type="radio"
							onClick="clickWaterMarkType()" value="Image" checked>ͼƬ</label> <label><input
							name="WaterMarkType" type="radio" onClick="clickWaterMarkType()"
							value="Text">����</label> <%}else{
            	  ImageStyle = "display:none";
              %> <label><input name="WaterMarkType" type="radio"
							onClick="clickWaterMarkType()" value="Image">ͼƬ</label> <label><input
							name="WaterMarkType" type="radio" onClick="clickWaterMarkType()"
							value="Text" checked>����</label> <%}%>
						</td>
					</tr>
					<% Element Text = WaterMark.element("Text"); %>
					<tr id="tr_Text" style="<%=TextStyle %>">
						<td align="right">ˮӡ���֣�</td>
						<td align="left"><input name="Text" id="Text" type="text"
							value="<%=Text.getText()%>"></td>
					</tr>
					<tr id="tr_FontSize" style="<%=TextStyle %>">
						<td align="right">���ִ�С��</td>
						<td align="left">
						<z:select id="FontSize" name="FontSize" style="width:60px">
							<%=HtmlUtil.codeToOptions("WaterMark.FontSize", Text.attributeValue("FontSize"))%>
						</z:select>
						</td>
					</tr>
					<tr id="tr_FontColor" style="<%=TextStyle %>">
						<td align="right">������ɫ��</td>
						<td align="left">
						<z:select id="FontColor" name="FontColor"
							style="width:60px"><%=HtmlUtil.codeToOptions("WaterMark.FontColor", Text.attributeValue("FontColor"))%>
						</z:select>
						</td>
					</tr>
					<%Element Image = WaterMark.element("Image"); %>
					<tr id="tr_ImageFile" style="<%=ImageStyle %>">
						<td align="right" valign="top">ˮӡͼƬ��</td>
						<td align="left"><input id="Image" type="hidden"
							value="<%=Image.attributeValue("src")%>"> <img
							id="ImageFile"
							src="<%=(Config.getContextPath()+Config.getValue("UploadDir")+"/"+Application.getCurrentSiteAlias()+"/"+Image.attributeValue("src")).replaceAll("//","/")+"?x="+System.currentTimeMillis()%>" /><br>
						<input name='WaterMarkImage' id='WaterMarkImage' type='file'
							value='' size='20'> <input onClick="upload();"
							type='button' value='�ϴ�'></td>
					</tr>
				</table>
				</fieldset>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
<table width="100%" border='0' cellpadding='0' cellspacing='0'>
	<tr>
		<td align="center" height="25px"></td>
	</tr>
	<tr>
		<td align="center"><input id="add" name="add"
			onClick="addAbbrImage(this);" type='button' value='�������ͼ'>&nbsp;&nbsp;&nbsp;
		<input id="savebtn" name="savebtn" onClick="save();" type='button'
			value='  ����  ' /></td>
	</tr>
</table>
<script type="text/javascript">
	count=<%=elements.size()%>;
	</script></form>
</body>
</html>
