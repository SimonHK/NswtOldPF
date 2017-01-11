<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>����</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
Page.onLoad(function(){
	$("dg1").afterEdit = function(tr,dr){
		if(Verify.hasError()){
	 		return;
    	}
		dr.set("Type",$V("Type"));
		dr.set("Name",$V("Name"));
		dr.set("Value",$V("Value"));
		return true;
	}
});

function add(){
	var diag = new Dialog("Diag1");
	diag.Width = 400;
	diag.Height = 180;
	diag.Title = "�½�������";
	diag.URL = "Platform/ConfigDialog.jsp";
	diag.onLoad = function(){
		$DW.$("Type").focus();
	};
	diag.OKEvent = addSave;
	diag.show();
}

function addSave(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
	  return;
     }
	if(!/^[\w\-\.]+$/.test($DW.$V("Type"))){
		Dialog.alert("����������к��в������ַ�\",\^\"");
		return;
	}
	Server.sendRequest("com.nswt.platform.ConfigSys.add",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				DataGrid.loadData('dg1');
			}
		});
	});
}

function save(){
	DataGrid.save("dg1","com.nswt.platform.ConfigSys.dg1Edit",function(){DataGrid.loadData('dg1');});
}

function del(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ��Ҫɾ�����У�");
		return;
	}
	Dialog.confirm("ȷ��Ҫɾ������������",function(){
		var dc = new DataCollection();	
		dc.add("IDs",arr.join());
		Server.sendRequest("com.nswt.platform.ConfigSys.del",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					DataGrid.loadData('dg1');
				}
			});
		});
	});
}

function doSearch(){
	var searchType = "";
	if($V("SearchType") != "��������������������"){
		searchType = $V("SearchType").trim();
	}
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","SearchType",searchType);
	DataGrid.loadData("dg1");
}

document.onkeydown = function(event){
	event = getEvent(event);
	if(event.keyCode==13){
		var ele = event.srcElement || event.target;
		if(ele.id == 'SearchType'||ele.id == 'Submitbutton'){
			doSearch();
		}
	}
}

function delKeyWord() {
	if ($V("SearchType") == "��������������������") {
		$S("SearchType","");
	}
}
</script>
</head>
	<body>
	<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
      <tr valign="top">
        <td><table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
            <tr>
              <td valign="middle" class="blockTd"><img src="../Icons/icon018a6.gif" /> �������б�</td>
            </tr>
				<tr>
					<td style="padding:0 8px 4px;"><z:tbutton onClick="add()"><img src="../Icons/icon018a2.gif" />�½�</z:tbutton>
                  <z:tbutton onClick="save()"> <img src="../Icons/icon018a4.gif" />����</z:tbutton>
                  <z:tbutton onClick="del()"> <img src="../Icons/icon018a3.gif" />ɾ��</z:tbutton>
                  <div style="float: right; white-space: nowrap;"><input type="text" name="SearchType"
						id="SearchType" value="��������������������" onFocus="delKeyWord();" style="width:150px"> 
						<input type="button" name="Submitbutton" id="Submitbutton" value="��ѯ" onClick="doSearch()"></div>
			 </td>
            </tr>
            <tr>
              <td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
			  <z:datagrid id="dg1" method="com.nswt.platform.ConfigSys.dg1DataBind" size="15">
                <table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
                  <tr ztype="head" class="dataTableHead">
                    <td  width="4%" ztype="RowNo"><b>���</b></td>
                    <td width="3%" ztype="selector" field="type">&nbsp;</td>
                    <td width="24%"><b>���������</b></td>
                    <td width="25%"><b>����������</b></td>
                    <td width="38%"><b>������ֵ</b></td>
                  </tr>
                  <tr style1="background-color:#FFFFFF" style2="background-color:#F9FBFC">
                    <td align="center">&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>${type}</td>
                    <td>${name}</td>
                    <td>${value}</td>
                  </tr>
                  <tr ztype="edit" bgcolor="#E1F3FF">
                    <td align="center" bgcolor="#E1F3FF">&nbsp;</td>
                    <td>&nbsp;</td>
                    <td><input name="text" type="text" id="Type"  value="${type}" size="40"></td>
                    <td><input name="text2" type="text" id="Name" value="${name}" size="40"></td>
                    <td><input name="text2" type="text" id="Value" value="${value}" size="80"></td>
                  </tr>
                  <tr ztype="pagebar">
                    <td colspan="5">${PageBar}</td>
                  </tr>
                </table>
              </z:datagrid></td>
            </tr>
        </table></td>
      </tr>
    </table>
	</body>
</html>
