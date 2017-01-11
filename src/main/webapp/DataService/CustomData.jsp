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
	diag.Width = 900;
	diag.Height = 380;
	diag.Title = "�½����ݱ�";
	diag.URL = "DataService/CustomTableDialog.jsp";
	diag.onLoad = function(){
		var win = $DW.Tab.getChildTab("Info").contentWindow;
		win.onTypeChange();
	};
	diag.OKEvent = save;
	diag.show();
	diag.OKButton.value = "����";
}

function genDbSrc(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ��Ҫ���ɴ���ı�");
		return;
	}

	Dialog.confirm("ȷ��Ҫ���ɴ�����",function(){
		var dc = new DataCollection();
		dc.add("IDs",arr.join());
		Server.sendRequest("com.nswt.cms.dataservice.CustomTable.genDbSrc",dc,function(response){
			Dialog.alert(response.Message,function(){
				DataGrid.loadData('dg1');
			});
		});
	})

}

function conn(){
	var diag = new Dialog("DiagConn");
	diag.Width = 800;
	diag.Height = 400;
	diag.Title = "�½��ⲿ����";
	diag.URL = "DataService/OuterDatabase.jsp";
	diag.show();
	diag.OKButton.hide();
}

function save(){
	var iwin = $DW.Tab.getChildTab("Info").contentWindow;
	$DW.Tab.onChildTabClick("Info");
	if(iwin.Verify.hasError()){
		return;
	}
	var dc = iwin.Form.getData("F1");
	if(dc.get("Type")=="Custom"){
		$DW.Tab.onChildTabClick("Column");
		var cwin = $DW.Tab.getChildTab("Column").contentWindow;
		if(!cwin.$||!cwin.$("dg1")||!cwin.checkData()){
			return;
		}
		var dt = cwin.getDataTable();
		for(var i=0;i<dt.getRowCount();i++){
			if(!dt.get(i,"Code")){
				Dialog.alert("��"+(i+1)+"��û����д�д���!");
				return;
			}
			if(!dt.get(i,"DataType")){
				Dialog.alert("��"+(i+1)+"��û����д��������!");
				return;
			}
		}
		dc.add("Data",dt);
	}
	Server.sendRequest("com.nswt.cms.dataservice.CustomTable.add",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				DataGrid.loadData("dg1");
			}
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
	diag.Width = 900;
	diag.Height = 380;
	diag.Title = "�޸����ݱ�";
	diag.URL = "DataService/CustomTableDialog.jsp";
	diag.onLoad = function(){
		var win = $DW.Tab.getChildTab("Info").contentWindow;
		win.Form.setValue(dr,"F1");
		if(dr.get("Type")=="Link"){
			win.Selector.setValueEx("OldCode",dr.get("OldCode"),dr.get("OldCode"));
			win.loadTables(dr);			
		}else{
			var cwin = $DW.Tab.getChildTab("Column").contentWindow;
			cwin.DataGrid.setParam("dg1","TableID",dr.get("ID"));
			cwin.DataGrid.loadData("dg1");
		}		
		win.onTypeChange();
		win.$N("Type").each(function(ele){
			$(ele).disable();
		});
	};
	diag.OKEvent = save;
	diag.show();
	diag.OKButton.value = "����";
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
			Server.sendRequest("com.nswt.cms.dataservice.CustomTable.del",dc,function(response){
				Dialog.alert(response.Message,function(){
					DataGrid.loadData('dg1');
				});
			});
	})
}
function dataEdit(dr){
	if(!dr){
		var dt = DataGrid.getSelectedData("dg1");
		if(dt.getRowCount()==0){
			Dialog.alert("����ѡ���!");
			return;
		}
		dr = dt.getDataRow(0);
	}
	var diag = new Dialog("Diag1");
	diag.Width = 980;
	diag.Height = 510;
	diag.Title = dr.get("Code")+"("+dr.get("Name")+")";
	diag.URL = "DataService/CustomTableData.jsp?ID="+dr.get("ID");
	diag.ShowButtonRow = false;
	diag.show();
}

function editForm(){
	var dt = DataGrid.getSelectedData("dg1");
	if(dt.getRowCount()==0){
		Dialog.alert("����ѡ���!");
		return;
	}
	var dr = dt.getDataRow(0);
	var diag = new Dialog("DiagForm");
	diag.Width = 800;
	diag.Height = 400;
	diag.Title = "�༭��";
	diag.URL = "DataService/FormEditor.jsp?ID="+dr.get("ID");
	diag.OKEvent = saveForm;
	diag.show();
	diag.OKButton.value = "��    ��";
	diag.CancelButton.value = "ȡ    ��";
	diag.addButton("Preivew","���³�ʼ��",reInit);
}

function saveForm(){
	var dc = new DataCollection();
	dc.add("ID",$DW.$V("ID"));
	dc.add("HTML",$DW.getHTML());
	Server.sendRequest("com.nswt.cms.dataservice.Form.save",dc,function(response){
		Dialog.alert(response.Message);
	});
}

function reInit(){
	var dc = new DataCollection();
	dc.add("ID",$DW.$V("ID"));
	Server.sendRequest("com.nswt.cms.dataservice.Form.reInit",dc,function(response){
		$DW.setHTML(response.get("HTML"));
	});
}
</script>
</head>
	<body>
	<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
      <tr valign="top">
        <td><table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
            <tr>
              <td valign="middle" class="blockTd"><img src="../Icons/icon005a1.gif" width="20" height="20" /> �����б�</td>
            </tr>
				<tr>
					<td style="padding:0 8px 4px;"><z:tbutton onClick="add()"><img src="../Icons/icon005a2.gif" width="20" height="20"/>���</z:tbutton>
				<z:tbutton onClick="edit()"><img src="../Icons/icon005a4.gif" width="20" height="20"/>�޸�</z:tbutton>
				<z:tbutton onClick="del()"><img src="../Icons/icon005a3.gif" width="20" height="20"/>ɾ��</z:tbutton>
				<z:tbutton onClick="dataEdit()"><img src="../Icons/icon005a7.gif" width="20" height="20" />���ݹ���</z:tbutton>
				<z:tbutton onClick="editForm()"><img src="../Icons/icon002a1.gif" width="20" height="20" />��</z:tbutton>
				<z:tbutton onClick="conn()"><img src="../Icons/icon006a10.gif" width="20" height="20" />�ⲿ����</z:tbutton>
				<z:tbutton onClick="genDbSrc()"><img src="../Icons/icon015a2.gif" width="20" height="20" />��������</z:tbutton>
			 </td>
            </tr>
            <tr>
              <td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:0px;"><z:datagrid id="dg1" method="com.nswt.cms.dataservice.CustomTable.dg1DataBind" page="false">
                <table width="100%" cellpadding="2" cellspacing="0" class="dataTable" >
                  <tr ztype="head" class="dataTableHead">
                    <td  width="4%" ztype="rowno">&nbsp;</td>
                    <td width="5%" ztype="selector" field="id">&nbsp;</td>
                    <td width="15%"><b>�����</b></td>
                    <td width="15%"><b>������</b></td>
                    <td width="12%"><b>������</b></td>
                    <td width="24%"><b>�ⲿ��������</b></td>
                    <td width="25%"><b>��ע</b></td>
                  </tr>
                  <tr onDblClick="dataEdit();">
                    <td align="center">&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>${Code}</td>
                    <td>${Name}</td>
                    <td>${TypeName}</td>
                    <td>${DatabaseIDName}</td>
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
