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
function add(){
	var diag = new Dialog("Diag1");
	diag.Width = 540;
	diag.Height = 400;
	diag.Title = "�½��������";
	diag.URL = "DataService/FullTextSearchDialog.jsp";
	diag.onLoad = function(){
		$DW.onTypeChange();
	};
	diag.OKEvent = save;
	diag.show();
	diag.OKButton.value = "����";
}

function save(){
	if($DW.Verify.hasError()){
		return;
	}
	var dc = $DW.Form.getData("F1");
	if(!$DW.$NV("Catalog")){
		Dialog.alert("û��ѡ���κ���Ŀ!");
		return;
	}
	dc.add("RelaText",$DW.$NV("Catalog"));
	Server.sendRequest("com.nswt.cms.dataservice.FullText.add",dc,function(response){
		Dialog.alert(response.Message,function(){
			$D.close();
			DataGrid.loadData("dg1");
		});		
	});
}

function edit(dr){
	if(!dr){
		var dt = DataGrid.getSelectedData("dg1");
		if(dt.getRowCount()==0){
			Dialog.alert("����ѡ��Ҫ�޸ĵ���!");
			return;
		}
		dr = dt.getDataRow(0);
	}
	var diag = new Dialog("Diag1");
	diag.Width = 540;
	diag.Height = 400;
	diag.Title = "�޸��������";
	diag.URL = "DataService/FullTextSearchDialog.jsp?ID="+dr.get("ID");
	diag.onLoad = function(){
		$DW.Form.setValue(dr,"F1");
		$DW.RelaStr = dr.get("RelaText");
		$DW.onTypeChange();
	};
	diag.OKEvent = save;
	diag.show();
}

function del(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ��Ҫɾ�����У�");
		return;
	}
	Dialog.confirm("ȷ��ɾ����",function(){
		var dc = new DataCollection();
		dc.add("IDs",arr.join());
		Server.sendRequest("com.nswt.cms.dataservice.FullText.del",dc,function(response){
			Dialog.alert(response.Message,function(){
				DataGrid.loadData('dg1');
			});
		});
	})
}

function manual(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ���У�");
		return;
	}
	Dialog.confirm("ȷ����������������",function(){
		var dc = new DataCollection();
		dc.add("IDs",arr.join());
		Dialog.wait("������������...");
		Server.sendRequest("com.nswt.cms.dataservice.FullText.manual",dc,function(response){
			Dialog.closeEx();
			Dialog.alert(response.Message);
		});
	})
}
</script>
</head>
	<body>
	<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
      <tr valign="top">
        <td><table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
            <tr>
              <td valign="middle" class="blockTd"><img src="../Icons/icon033a15.gif" width="20" height="20" /> ȫ�ļ������� </td>
            </tr>
				<tr>
					<td style="padding:0 8px 4px;"><z:tbutton onClick="add()"><img src="../Icons/icon033a2.gif" width="20" height="20"/>���</z:tbutton>
				<z:tbutton onClick="edit()"><img src="../Icons/icon033a4.gif" width="20" height="20"/>�޸�</z:tbutton>
				<z:tbutton onClick="del()"><img src="../Icons/icon033a3.gif" width="20" height="20"/>ɾ��</z:tbutton>
				<z:tbutton onClick="manual()"><img src="../Icons/icon033a16.gif" width="20" height="20"/>��������</z:tbutton>
			  </td>
            </tr>
            <tr>
              <td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
			  <z:datagrid id="dg1" method="com.nswt.cms.dataservice.FullText.dg1DataBind" page="false">
                <table width="100%" cellpadding="2" cellspacing="0" class="dataTable" >
                  <tr ztype="head" class="dataTableHead">
                    <td  width="2%" ztype="rowno">&nbsp;</td>
                    <td width="3%" ztype="selector" field="id">&nbsp;</td>
                    <td width="6%">ID</td>
                    <td width="12%">����</td>
                    <td width="21%"><b>����</b></td>
                    <td width="12%"><b>����</b></td>
                    <td width="35%"><b>��ע</b></td>
                  </tr>
                  <tr onDblClick="edit()" style1="background-color:#FFFFFF" style2="background-color:#F7F8FF">
                    <td align="center">&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>${ID}</td>
                    <td>${Code}</td>
                    <td>${Name}</td>
                    <td>${TypeName}</td>
                    <td>${Memo}</td>
                  </tr>
                </table>
              </z:datagrid></td>
            </tr>
        </table></td>
      </tr>
    </table>
	</body>
</html>
