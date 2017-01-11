<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>�Զ������ݱ�</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function add(){
	var diag = new Dialog("Diag2");
	diag.Width = 420;
	diag.Height = 280;
	diag.Title = "�½����ݿ�����";
	diag.URL = "DataService/OuterDatabaseDialog.jsp";
	diag.onLoad = function(){
		$DW.$("Name").focusEx();
	};
	diag.OKEvent = Save;
	diag.show();
	diag.addButton("ConnTest","��������",connTest);
	diag.OKButton.value = "����";
}


function connTest(){
	if($DW.Verify.hasError(["Name"])){
		return;
	}
	var dc = $DW.Form.getData("F1");
	Dialog.wait("���ڳ����������ݿ�...");
	Server.sendRequest("com.nswt.cms.dataservice.OuterDatabase.connTest",dc,function(rdc){
		Dialog.closeEx();
		Dialog.alert(rdc.Message);
	});
}

function Save(){
	if($DW.Verify.hasError()){
		return;
	}
	var dc = $DW.Form.getData("F1");
	Server.sendRequest("com.nswt.cms.dataservice.OuterDatabase.save",dc,function(response){
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
	var diag = new Dialog("Diag2");
	diag.Width = 420;
	diag.Height = 280;
	diag.Title = "�޸����ݿ�����";
	diag.URL = "DataService/OuterDatabaseDialog.jsp";
	diag.onLoad = function(){
		$DW.Form.setValue(dr,"F1");
	};
	diag.OKEvent = Save;
	diag.show();
	diag.addButton("ConnTest","��������",connTest);
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
			Server.sendRequest("com.nswt.cms.dataservice.OuterDatabase.del",dc,function(response){
				Dialog.alert(response.Message,function(){
					DataGrid.loadData('dg1');
				});
			});
	})
}
</script>
</head>
<body>
<table width="100%" border="0" cellpadding="6" cellspacing="0">
    <tr>
      <td><z:tbutton onClick="add()"><img src="../Icons/icon006a2.gif" width="20" height="20"/>�½�</z:tbutton>
        <z:tbutton onClick="edit()"><img src="../Icons/icon006a4.gif" width="20" height="20"/>�޸�</z:tbutton>
        <z:tbutton onClick="del()"><img src="../Icons/icon006a3.gif" width="20" height="20"/>ɾ��</z:tbutton>
        <z:tbutton onClick="dataEdit()"></z:tbutton><z:tbutton onClick="add();"></z:tbutton></td>
    </tr>
    <tr>
      <td>
        <z:datagrid id="dg1" method="com.nswt.cms.dataservice.OuterDatabase.dg1DataBind" page="false">
          <table width="100%" cellpadding="2" cellspacing="0" class="dataTable" >
            <tr ztype="head" class="dataTableHead">
              <td  width="5%" ztype="rowno">&nbsp;</td>
              <td width="5%" ztype="selector" field="id">&nbsp;</td>
              <td width="17%"><b>����</b></td>
              <td width="16%"><b>ԭʼ����</b></td>
              <td width="15%"><b>����������</b></td>
              <td width="29%">��������ַ</td>
              <td width="13%"><b>��ע</b></td>
            </tr>
            <tr style1="background-color:#FFFFFF" style2="background-color:#F7F8FF" onDblClick="edit()">
              <td align="center">&nbsp;</td>
              <td>&nbsp;</td>
              <td>${Name}</td>
              <td>${DBName}</td>
              <td>${ServerTypeName}</td>
              <td>${Address}</td>
              <td>${Memo}</td>
            </tr>
          </table>
      </z:datagrid></td>
    </tr>
</table>
</body>
</html>
