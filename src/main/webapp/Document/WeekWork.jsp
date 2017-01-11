<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title><%=Config.getValue("App.Name")%></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>

<script>
function editDialog(ID){
	var diag = new Dialog("Diag1");
	diag.Width = 500;
	diag.Height = 320;
	diag.Title = "���������޸�";
	diag.URL = "Document/WeekWorkDialog.jsp?ID="+ID;
	diag.onLoad = function(){
		$DW.$("Content").focus();
	};
	diag.OKEvent = editSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "���������޸�";
	diag.show();
}

function editSave(){
	var dc = $DW.Form.getData("form2");
	
	if($DW.Verify.hasError()){
		return;
	}
	Server.sendRequest("com.nswt.cms.document.Weekwork.save",dc,function(response){
		Dialog.alert(response.Message);
		if(response.Status==1){
				$D.close();
				DataGrid.loadData('dg1');
		}
	});
}
function add(ID){
	var diag = new Dialog("Diag1");
	diag.Width = 800;
	diag.Height = 400;
	diag.Title = "�½���������";
	diag.URL = "Document/AddWeekWorkDialog.jsp?ID="+ID;
	diag.onLoad = function(){
	//	$DW.$("").focus();
	};
	diag.OKEvent = addSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "�½���������";
	diag.show();
}
function addSave(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
		return;
	}
	Server.sendRequest("com.nswt.cms.document.Weekwork.addSave",dc,function(response){
		Dialog.alert(response.Message);
		if(response.Status==1){
				$D.close();
				DataGrid.loadData('dg1');
		}
	});
}
function nextorpre(day){
	window.location = "./WeekWork.jsp?dayTime="+day;
}
function editNote(ID){

	if(!ID){
		Dialog.alert("������Ӹ��ܹ������ţ�");
		return;
	}
	var dc = new DataCollection();
	dc.add("ID",ID);
	Server.sendRequest("com.nswt.cms.document.Weekwork.edit",dc,function(response){
		if(response.Status==1){
			$("textarea").innerHTML="<textarea id='text' style='border:0px solid #ccc; width:600px; height:100px; padding:5px;' >"+response.get('memo')+"</textarea><input type='button' value='�ύ' onclick='editNoteSave("+ID+")'><input type='button' value='ȡ��' onclick='cancle("+ID+")'>";	
		}
	});
}
function editNoteSave(ID){
	var dc = new DataCollection();
	var text = $V("text");
	dc.add("ID",ID);
	dc.add("memo",text);
	Server.sendRequest("com.nswt.cms.document.Weekwork.editSave",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$("textarea").innerHTML = text;	
			}else{
				Dialog.alert(response.Message);		
			}
		});
	});
}
function cancle(ID){
	var dc = new DataCollection();
	dc.add("ID",ID);
	Server.sendRequest("com.nswt.cms.document.Weekwork.edit",dc,function(response){
		if(response.Status==1){
			$("textarea").innerHTML=response.get("memo");
		}
	});
}
</script>

</head>
	<body>
<z:init method="com.nswt.cms.document.Weekwork.init">
<input type="button" value="��һ��" onclick="nextorpre('${preDay}')">
<input type="button" value="��һ��" onclick="nextorpre('${nextDay}')">

			<table width="100%" border="0" cellspacing="0" cellpadding="6"
				class="blockTable">
				<tr>
					<td valign="middle" class="blockTd" colspan="2"><img
						src="../Icons/icon018a1.gif" />���ܹ�������</td>
				</tr>
				<tr>
					<td style="padding:0 8px 4px;">
                    	 <z:tbutton onClick="add()">
						<img src="../Icons/icon048a16.gif" />�½�</z:tbutton></td>
                   		 <td><input type="button" value="��һ��" onclick="nextorpre('${preDay}')">
						 <input type="button" value="��һ��" onclick="nextorpre('${nextDay}')"></td>
                        
				</tr>
				<tr>
					<td
						style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;" colspan="2">
					<z:datagrid id="dg1"
								method="com.nswt.cms.document.Weekwork.dg1DataBind" size="9">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable">
							<tr ztype="head" class="dataTableHead ">
								<td width="4%" height="30" ztype="RowNo">���</td>
								<td width="3%" ztype="selector" field="id">&nbsp;</td>
                                <td width="5%">����</td>
                                <td width="8%">����</td>
								<td width="43%" >����</td>
								<td width="20%">�μ���Ա</td>
								<td width="17%" >�ص�</td>
							</tr>
                            <tr onDblClick="editDialog(${ID})" style="height:30px;">
								<td  bgcolor="#E1F3FF">&nbsp;</td>
								<td  bgcolor="#E1F3FF">&nbsp;</td>
								<td>${Wdate}</td>
                                <td>${Dayweek}</td>
								<td>${Content}</td>
								<td>${Partner}</td>
								<td>${Workplace}</td>
							</tr>
						</table>
					</z:datagrid></td>
				</tr>
				
                <tr>
					<td ondblclick="editNote(${ID})" >��ע��
				    <!--textarea name="textarea" cols="100" rows="10" id="textarea" >${Notes}</textarea-->
				    <div id="textarea" style="border:1px solid #ccc; width:600px; height:100px; padding:5px;">${Notes}</div></td>
				    
				</tr>
			</table>
		</z:init>
	</body>
</html>
