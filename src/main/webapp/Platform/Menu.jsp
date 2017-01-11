<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>�˵�</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
Page.onLoad(initGrid);

function showMenu(){
		$("dg1").onContextMenu = function(tr,evt){
		evt = getEvent(evt);
		var menu = new Menu();
		menu.Width = 150;
		menu.setEvent(evt);
		menu.setParam([]);
			
		menu.addItem("�½�",add,"/Icons/icon022a2.gif");
		menu.addItem("����",save,"/Icons/icon022a16.gif");
		menu.addItem("����",discard,"/Icons/icon400a8.gif");
		menu.addItem("ɾ��",del,"/Icons/icon022a3.gif");
		
		menu.show();
	}
}


function initGrid(){
	showMenu();
	$("dg1").beforeEdit = function(tr,dr){
		if(dr.get("TreeLevel")==0){		
			$("dg1_expand_Checkbox"+tr.rowIndex).disabled = false;
		}else{
			hideExpand(tr.rowIndex);
		}
	}
	$("dg1").cancelEdit = function(tr,dr){
		if(dr.get("TreeLevel")==0){
			$("dg1_expand_Checkbox"+tr.rowIndex).disabled = true;
		}else{
			hideExpand(tr.rowIndex);
		}
	}
	$("dg1").afterEdit = function(tr,dr){
		dr.set("Name",$V("Name"));
		if(dr.get("TreeLevel")!=0){
			if($V("URL")==""){
				alert("URL����Ϊ��");				
				return false;
			}else {
				dr.set("URL",$V("URL"));
			}
		}else{
			hideExpand(tr.rowIndex);
		}
		dr.set("Memo",$V("Memo"));
		dr.set("Icon",$("iconFile").src.substring($("iconFile").src.indexOf("Icons/")));
		dr.set("Visiable",$V("dg1_visiable_Checkbox"+tr.rowIndex)=="Y"?"Y":"N");
		dr.set("Expand",$V("dg1_expand_Checkbox"+tr.rowIndex)=="Y"?"Y":"N");
		return true;
	}
	var ds = $("dg1").DataSource;
	for(var i=0;i<ds.Rows.length;i++){
		if(ds.Rows[i].get("TreeLevel")!=0){
			hideExpand(i+1);
		}
	}
}

function hideExpand(index){
	var ele = $("dg1_expand_Checkbox"+index);
	ele.insertAdjacentHTML("afterEnd","&nbsp;");
	$E.hide(ele);
}

function save(){
	DataGrid.save("dg1","com.nswt.platform.Menu.dg1Edit",function(){DataGrid.loadData("dg1",initGrid);});
}

function add(){
	var diag = new Dialog("Diag1");
	diag.Width = 450;
	diag.Height = 250;
	diag.Title = "�½��˵�";
	diag.URL = "Platform/MenuDialog.jsp";
	diag.onLoad = function(){
		$DW.$("Name").focus();
	};
	diag.OKEvent = addSave;
	diag.show();
}

function addSave(){
	var dc = $DW.Form.getData("form2");
	dc.add("Visiable",$DW.$NV("Visiable"));
	dc.add("Icon",$DW.$("Icon").src);
	if($DW.Verify.hasError()){
		return;
	}
	Server.sendRequest("com.nswt.platform.Menu.add",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				DataGrid.loadData("dg1",initGrid);
			}
		});
	});
}

function selectIcon(){
	var diag = new Dialog("Diag2");
	diag.Width = 600;
	diag.Height = 300;
	diag.Title = "ѡ��ͼ��";
	diag.URL = "Platform/Icon.jsp";
	diag.onLoad = function(){
		//$DW.$S("ID",ele.value);
	};
	diag.OKEvent=afterSelectIcon;
	diag.show();
}

function afterSelectIcon(){
	if($DW.Icon){
		$("iconFile").src = $DW.Icon;
	}else{
		$("iconFile").src = $DW.lastEle.src;
	}
	$D.close();
}

function del(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("����ѡ��Ҫɾ�����У�");
		return;
	}
	Dialog.confirm("ɾ���󲻿ɻָ���ȷ��Ҫɾ����",function(){
		var dc = new DataCollection();
		dc.add("IDs",arr.join());
		Server.sendRequest("com.nswt.platform.Menu.del",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					DataGrid.loadData("dg1",initGrid);
				}
			});
		});
	});
}

function discard(){
	DataGrid.discard("dg1",initGrid);
}

function treeCheckBoxClick(ele){
	var id = ele.id;
	var index = id.substring(id.lastIndexOf("_")+1);
	var checked = ele.checked;
	var level = ele.getAttribute("level");
	var arr = $N(ele.name);
	var length = arr.length;
	// ѡ��
	if(checked){
		for(var i=index-2;i>=0;i--){
			if(arr[i].getAttribute("level")<level){
				arr[i].checked = true;
				level = arr[i].getAttribute("level");
				if(level==0){
					break;
				}
			}
		}
		level = ele.getAttribute("level");
		for(var i=index;i<length;i++){
			if(arr[i].getAttribute("level")>level){
				arr[i].checked = true;
			}else {
				break;
			}
		}
	}else{
	// ȡ��ѡ��
		for(var i=index-2;i>=0;i--){
			if(arr[i].getAttribute("level")<level){
				var check_flag = false;
				var tmp_index = arr[i].id.substring(arr[i].id.lastIndexOf("_")+1);
				for(var j=tmp_index;j<length;j++){
					if(level<=arr[j].getAttribute("level")){
						if(arr[j].checked){
							check_flag = true;
							break;
						}
					}else{
						break;
					}
				}
				arr[i].checked = check_flag;
				
				level = arr[i].getAttribute("level");
				if(level==0){
					break;
				}
			}
		}
		level = ele.getAttribute("level");
		for(var i=index;i<length;i++){
			if(arr[i].getAttribute("level")>level){
				arr[i].checked = false;
			}else{
				break;
			}
		}
	}
}

function sortMenu(type,targetDr,sourceDr,rowIndex,oldIndex){
	if(rowIndex==oldIndex){
		return;
	}
	
	var ds = $("dg1").DataSource;
	var type = "";
	var orderMenu = "";
	var nextMenu = "";
	
	if (ds.get(rowIndex-1,"ParentID") == ds.get(rowIndex,"ParentID")) {
		type = "Before";
		orderMenu = ds.get(rowIndex-1,"ID");
		nextMenu = ds.get(rowIndex,"ID");
	} else if (rowIndex-1 !=0 && ds.get(rowIndex-1,"ParentID") == ds.get(rowIndex-2,"ParentID")) {
		type = "After";
		orderMenu = ds.get(rowIndex-1,"ID");
		nextMenu = ds.get(rowIndex-2,"ID");
	} else {
		alert("����ͬһ�˵��µ��Ӳ˵���������");
		DataGrid.loadData("dg1");
		return;
	}
	var dc = new DataCollection();
	dc.add("OrderType",type);
	dc.add("OrderMenu",orderMenu);
	dc.add("NextMenu",nextMenu);
	DataGrid.showLoading("dg1");
	Server.sendRequest("com.nswt.platform.Menu.sortMenu",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				DataGrid.loadData("dg1");
			}
		});
	});
}

</script>
</head>
<body>
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
    <tr valign="top">
      <td><table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
          <tr>
            <td valign="middle" class="blockTd"><img src="../Icons/icon022a1.gif" width="20" height="20" />�˵��б�</td>
          </tr>
				<tr>
					<td style="padding:0 8px 4px;"><z:tbutton onClick="add()"><img src="../Icons/icon022a2.gif" width="20" height="20"/>�½�</z:tbutton>
              <z:tbutton onClick="DataGrid.edit(event,'dg1')"><img src="../Icons/icon022a4.gif" width="20" height="20"/>�༭</z:tbutton>
              <z:tbutton onClick="save()"><img src="../Icons/icon022a16.gif" width="20" height="20"/>����</z:tbutton>
              <z:tbutton onClick="discard()"><img src="../Icons/icon400a8.gif"/>����</z:tbutton>
              <z:tbutton onClick="del()"><img src="../Icons/icon022a3.gif" width="20" height="20"/>ɾ��</z:tbutton>
            </td>
          </tr>
          <tr>   <td style="padding:0px 5px;">
<z:datagrid id="dg1" method="com.nswt.platform.Menu.dg1DataBind" page="false">
              <table width="100%" cellpadding="2" cellspacing="0" class="dataTable" afterdrag="sortMenu">
                <tr ztype="head" class="dataTableHead">
                  <td width="3%" ztype="RowNo" drag="true"><img src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
                  <td width="3%" ztype="selector" field="id">&nbsp;</td>
                  <td width="18%" ztype="tree" level="treelevel" ><b>�˵�����</b></td>
                  <td width="7%" ztype="checkbox" checkedvalue="Y" field="visiable"><b>�Ƿ���ʾ</b></td>
                  <td width="7%" ztype="checkbox" checkedvalue="Y" field="expand"><b>�Ƿ�չ��</b></td>
                  <td width="28%"><strong>URL</strong></td>
                  <td width="17%"><strong>���ʱ��</strong></td>
                  <td width="17%"><strong>��ע</strong></td>
                </tr>
                <tr style1="background-color:#FFFFFF" style2="background-color:#F9FBFC">
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                  <td><img src="../${Icon}" align="absmiddle"/>&nbsp;${Name}</td>
                  <td></td>
                  <td>${Expand}</td>
                  <td>${URL} </td>
                  <td>${AddTime}</td>
                  <td>${Memo}</td>
                </tr>
                <tr ztype="edit" bgcolor="#E1F3FF">
                  <td>&nbsp;</td>
                  <td bgcolor="#E1F3FF">&nbsp;</td>
                  <td bgcolor="#E1F3FF"><img id="iconFile" src="../${Icon}" align="absmiddle" border="1" onClick="selectIcon()"/>&nbsp;<input name="text" type="text" class="input1" id="Name" value="${Name}" size="16"></td>
                  <td bgcolor="#E1F3FF"></td>
                  <td bgcolor="#E1F3FF">${Expand}</td>
                  <td><input name="text2" type="text" class="input1" id="URL" value="${URL}" size="40"></td>
                  <td>${AddTime}</td>
                  <td><input name="text2" type="text" class="input1" id="Memo" value="${Memo}" size="10"></td>
                </tr>
              </table>
            </z:datagrid></td>
          </tr>
      </table></td>
    </tr>
  </table>
</body>
</html>
