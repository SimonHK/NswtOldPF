<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
Page.onLoad(function(){
	if(!Cookie.get("Ask.InnerCode")){
		var tree = $("tree1");
		var arr = tree.getElementsByTagName("p");
		for(var i=0;i<arr.length;i++){
			var p = arr[i];
			if(i==1){
				p.className = "cur";
				Tree.CurrentItem = p;
				p.onclick.apply(p);
				break;
			}
		}
	}else{
		if(!Cookie.get("Ask.InnerCode")||Cookie.get("Ask.InnerCode")=="null"){
			Tree.select("tree1","cid",null);
		}else{
			Tree.select("tree1","cid",Cookie.get("Ask.InnerCode"));
		}
	}
});


Page.onClick(function(){
	var div = $("_DivContextMenu");
	if(div){
			$E.hide(div);
	}
});

function showMenu(event,ele){
	var cid = ele.getAttribute("cid");
	var cname=ele.getAttribute("cname");
	var cmsg=[cid,cname];
	var menu = new Menu();
	menu.setEvent(event);
	menu.setParam(cmsg);
	menu.addItem("�½�",addTeam,"/Icons/icon018a2.gif");
	menu.addItem("�޸�",showEditTeamDialog,"/Icons/icon018a4.gif");
	menu.addItem("ɾ��",delTeam,"/Icons/icon018a3.gif");
	menu.show();
}
function addTeam(param){
	var diag = new Dialog("Diag1");
	diag.Width = 350;
	diag.Height = 200;
	diag.Title = "�½��������";
	var ParentInnerCode=Tree.CurrentItem.getAttribute("cid");
	diag.URL = "DataService/QuestionTeamDialog.jsp?ParentInnerCode="+ParentInnerCode;
	diag.onLoad = function(){
		$DW.$("Title").focus();
	};
	diag.OKEvent = addSaveTeam;
	diag.show();
}

function addSaveTeam(){
	var dc = Form.getData($DW.$F("form1"));
	if($DW.Verify.hasError()){
	  return;
     }
	Server.sendRequest("com.nswt.cms.dataservice.QuestionTeam.add",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				window.parent.parent.location.reload();
			}
		});
	});
}

function delTeam(param){
	var cmsg=param;
	var Name =cmsg[1] ;
	var InnerCode=cmsg[0];
	if(!InnerCode){
		Dialog.alert("����ѡ��һ�����飡");
		return ;
	}
	Dialog.confirm("ȷ��ɾ�� <b style='color:#F00'>"+Name+"</b> ������",function(){
		var dc = new DataCollection();
		dc.add("InnerCode",InnerCode);
		Server.sendRequest("com.nswt.cms.dataservice.QuestionTeam.del",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					window.parent.parent.location.reload();
				}
			});
		});
	});
}

function showEditTeamDialog(param){
	var cmsg=param;
	var InnerCode=Tree.CurrentItem.getAttribute("cid");
	if(!InnerCode){
		Dialog.alert("����ѡ��һ�����飡");
		return ;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 350;
	diag.Height = 140;
	diag.Title = "�޸ķ���";
	diag.URL = "DataService/QuestionTeamDialog.jsp?InnerCode="+InnerCode;
	diag.onLoad = function(){
		$DW.$("Name").focus();
	};
	diag.OKEvent = editSaveTeam;
	diag.show();
}

function editSaveTeam(){
	var dc = Form.getData($DW.$F("form1"));
	if($DW.Verify.hasError()){
	  return;
     }
	Server.sendRequest("com.nswt.cms.dataservice.QuestionTeam.edit",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				window.parent.location.reload();
			}
		});
	});
}

function add(){
	var diag = new Dialog("Diag1"); 
	var QuestionInnerCode=Tree.CurrentItem.getAttribute("cid");
	if(!QuestionInnerCode){
		Dialog.alert("����ѡ���������");
		return;
	}
	diag.Width = 350;
	diag.Height = 200;
	diag.Title = "�½�������Ϣ";
	diag.URL = "DataService/QuestionDialog.jsp?QuestionInnerCode="+QuestionInnerCode;
	diag.onLoad = function(){
		$DW.$("Title").focus();
	};
	diag.OKEvent = addSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "�½�������Ϣ";
	diag.show();
}

function addSave(){
	var dc = Form.getData($DW.$F("form1"));
	if($DW.Verify.hasError()){
		return;
	}
	Server.sendRequest("com.nswt.cms.dataservice.Question.add",dc,function(response){
		Dialog.alert(response.Message);
		if(response.Status==1){
				$D.close();
				var TeamID = Tree.CurrentItem.getAttribute("cid");
				DataGrid.setParam("dg1","EmpType","");
				DataGrid.setParam("dg1","TeamID",TeamID);
				DataGrid.loadData('dg1');
		}
	});
}

function edit(){
  	var dt = DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	if(!drs||drs.length==0){
		Dialog.alert("����ѡ��Ҫ�޸ĵ���Ϣ��");
		return;
	}else if(drs.length>1){
		Dialog.alert("һ��ֻ���޸�һ����Ϣ��");
		return;
	}
	dr = drs[0]; 
  editDialog(dr);
}

function editDialog(dr){
	var diag = new Dialog("Diag1");
	diag.Width = 350;
	diag.Height = 200;
	diag.Title = "�޸�������Ϣ";
	var QuestionInnerCode = Tree.CurrentItem.getAttribute("cid");
	diag.URL = "DataService/QuestionDialog.jsp?ID="+dr.get("ID")+"&QuestionInnerCode="+QuestionInnerCode;
	diag.onLoad = function(){
		$DW.$("Title").focus();
	};
	diag.OKEvent = editSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "�޸�������Ϣ";
	diag.Message = "�޸�������Ϣ";
	diag.show();
}

function editSave(){
	var dc = Form.getData($DW.$F("form1"));
	dc.add("ID",$DW.$V("ID"));
	if($DW.Verify.hasError()){
		return;
	}
	Server.sendRequest("com.nswt.cms.dataservice.Question.edit",dc,function(response){
		Dialog.alert(response.Message);
		if(response.Status==1){
				$D.close();
				var QuestionInnerCode = Tree.CurrentItem.getAttribute("cid");
				DataGrid.setParam("dg1","QuestionInnerCode",QuestionInnerCode);
				DataGrid.loadData('dg1');
		}
	});
}

function del(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ��Ҫɾ�����У�");
		return;
	}
	Dialog.confirm("�˲�����ɾ�������µ�������Ϣ,ȷ��Ҫɾ����",function(){
		var dc = new DataCollection();	
		dc.add("IDs",arr.join());
		Server.sendRequest("com.nswt.cms.dataservice.Question.del",dc,function(response){
			if(response.Status==0){
				Dialog.alert(response.Message);
			}else{
				Dialog.alert(response.Message);
				DataGrid.setParam("dg1",Constant.PageIndex,0);
				var QuestionInnerCode = Tree.CurrentItem.getAttribute("cid");
				DataGrid.setParam("dg1","EmpType","");
				DataGrid.setParam("dg1","QuestionInnerCode",QuestionInnerCode);
				DataGrid.loadData("dg1");
			}
		});
	});
} 


function search(){
	var SearchEmpName=$V("SearchEmpName");
	var searchType=$V("searchType");
	if (SearchEmpName== "�������ѯȫ����Ա") {
		SearchEmpName="";
	}
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	var TeamID = Tree.CurrentItem.getAttribute("cid");
	DataGrid.setParam("dg1","TeamID",TeamID);
	DataGrid.setParam("dg1","SearchEmpName",SearchEmpName);
	DataGrid.setParam("dg1","searchType",searchType);
	DataGrid.loadData("dg1");
}

function exportExcel(){
	DataGrid.loadData("dg1");
	DataGrid.toExcel("dg1",1);
}

function delKeyWord() {
	if ($V("SearchEmpName") == "�������ѯȫ����Ա") {
		$S("SearchEmpName","");
	}
}

function onTreeClick(ele){
	var QuestionInnerCode = Tree.CurrentItem.getAttribute("cid");
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","QuestionInnerCode",QuestionInnerCode);
	DataGrid.setParam("dg1","SearchEmpName","");
	DataGrid.loadData("dg1");
	$("SearchEmpName").value="�������ѯȫ����Ա";
	Cookie.set("Ask.InnerCode",QuestionInnerCode,"2100-01-01");
}

function SelectSeachType(){
	var searchType=$V("searchType");
	if(searchType=="0"){
		$("SearchEmpName").style.display="none";
	}else{
		$("SearchEmpName").style.display="";
	}
}

function searchEmpType(){
	var EmpType=$V("EmpType");
	$("searchType").value="0";
	$("SearchEmpName").value="";
	$("SearchEmpName").style.display="none";
	if(EmpType=="3"){
		$("add").disabled=true;
		$("edit").disabled=true;
		//$("dim").disabled=true;
		$("ex").disabled=true;
	}else{
		$("add").disabled=false;
		$("edit").disabled=false;
		//$("dim").disabled=false;
		$("ex").disabled=false;
	}
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","EmpType",EmpType);
	DataGrid.loadData("dg1");
}

function AccountList(EmpID,EmpName){
	var diag = new Dialog("Diag1");
	diag.Width = 800;
	diag.Height = 380;
	diag.Title = "Ա��"+EmpName+"��������";
	diag.URL = "Manage/EmployeeWorkHistory.jsp?EmpID="+EmpID+"&EmpName="+EmpName;
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value="�ر�";
}

</script>
</head>
<body>
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
  <tr valign="top">
    <td width="180"><table oncontextmenu="return false;" width="180" border="0" cellspacing="0" cellpadding="6"
			class="blockTable">
        <tr>
          <td style="padding:6px;" class="blockTd"><z:tree id="tree1"
					style="height:430px" method="com.nswt.cms.dataservice.QuestionTeam.treeDataBind">
              <p cid='${InnerCode}'  cname='${Name}'
						onClick="onTreeClick(this);" oncontextmenu="showMenu(event,this);">&nbsp;${Name}</p>
            </z:tree></td>
        </tr>
      </table></td>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
        <tr>
          <td valign="middle" class="blockTd"><img src="../Icons/icon021a1.gif" />�ʴ���Ϣ�б�</td>
        </tr>
        <tr>
          <td style="padding:4px 5px;"><z:tbutton id="add" onClick="add()"> <img src="../Icons/icon021a2.gif" />�½�����</z:tbutton>
            <z:tbutton id="edit" onClick="edit()"> <img src="../Icons/icon021a4.gif" />�޸�����</z:tbutton>
            <z:tbutton id="del" onClick="del()"> <img src="../Icons/icon021a3.gif" />ɾ������</z:tbutton>
            <z:tbutton id="export" onClick="exportExcel()"> <img src="../Icons/icon021a7.gif" />����</z:tbutton>
            <div style="float: right; white-space: nowrap;">
			<span>
				<span id="boxEmpType" style="display:none">
					<div ztype='select' id="EmpType" onChange="searchEmpType()">
						<span value="0">ȫ��</span><span value="1">��ְ</span>
						<span value="2">����</span> <span value="3">��ְ</span>
					</div>
				</span>
				<span>
				<div ztype='select' id="searchType" onChange="SelectSeachType()">
					<span value="0"></span>
					<span value="1">����</span>
					<span value="2">���</span>
				</div>
				</span>
				<span>
				<input name="SearchEmpName" style="display:none" type="text" value="" onFocus="delKeyWord();" id="SearchEmpName" size="15">
				</span>
				<input type="button" name="Submit" id="Submit" value="��ѯ" onClick="search()">
			</span>
			</div></td>
        </tr>
        <tr>
          <td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
		  <z:datagrid id="dg1" method="com.nswt.cms.dataservice.Question.dg1DataBind" size="15">
              <table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
                <tr ztype="head" class="dataTableHead">
                  <td width="6%" ztype="RowNo"><strong>���</strong></td>
                  <td width="4%" ztype="selector" field="ID">&nbsp;</td>
                  <td width="15%"><b>������Ŀ</b></td>
                  <td width="40%"><b>����</b></td>
                  <td width="15%"><b>���ʱ��</b></td>
                  <td width="20%"><b>�ش�</b></td>
                </tr>
                <tr style1="background-color:#FFFFFF" style2="background-color:#F9FBFC">
                  <td width="6%">&nbsp;</td>
                  <td>&nbsp;</td>
                  <td>${Title}</td>
                  <td style="white-space:normal">${Content}</td>
                  <td>${AddTime}</td>
                  <td><a href="#">�ش�</a></td>
                </tr>
                <tr ztype="pagebar">
                  <td colspan="12">${PageBar}</td>
                </tr>
              </table>
            </z:datagrid></td>
        </tr>
      </table></td>
  </tr>
</table>
</body>
</html>
