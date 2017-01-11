<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>��ʱ����</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function add(){
	var diag = new Dialog("Diag1");
	diag.Width = 600;
	diag.Height = 300;
	diag.Title = "�½���ʱ�ƻ�";
	diag.ShowMessageRow = true;
	diag.MessageTitle = "�½���ʱ�ƻ�";
	diag.Message = "ѡ���ִ�����񣬲�����ִ�мƻ�";
	diag.URL = "Platform/ScheduleDialog.jsp";
	diag.onLoad = function(){
	};
	diag.OKEvent = save;
	diag.show();
}

function save(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
		return;
	}
	Server.sendRequest("com.nswt.platform.Schedule.save",dc,function(response){
		if(response.Status==1){
			Dialog.alert(response.Message);
			$D.close();
			DataGrid.setParam("dg1",Constant.PageIndex,0);
		    DataGrid.loadData("dg1");
		}else{
			Dialog.alert(response.Message);
		}
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
	diag.Width = 600;
	diag.Height = 300;
	diag.Title = "�޸Ķ�ʱ�ƻ�";
	diag.ShowMessageRow = true;
	diag.MessageTitle = "�޸Ķ�ʱ�ƻ�";
	diag.Message = "ѡ���ִ�����񣬲�����ִ�мƻ�";
	diag.URL = "Platform/ScheduleDialog.jsp";
	diag.onLoad = function(){
		$DW.SourceID = dr.get("SourceID");
		$DW.Form.setValue(dr,"form2");
		var time = dr.get("StartTime");
		$DW.$S("StartDate",time.split(" ")[0]);
		$DW.$S("StartTime",time.split(" ")[1]);
		if(dr.get("PlanType")=="Period"){
			var expr = dr.get("CronExpression");
			var arr = expr.split(" ");
			if(arr[0].indexOf("/")>0){
				$DW.$S("PeriodType","Minute");
				$DW.$S("Period",arr[0].substring(arr[0].indexOf("/")+1));
			}
			if(arr[1].indexOf("/")>0){
				$DW.$S("PeriodType","Hour");
				$DW.$S("Period",arr[1].substring(arr[1].indexOf("/")+1));
			}
			if(arr[2].indexOf("/")>0){
				$DW.$S("PeriodType","Day");
				$DW.$S("Period",arr[2].substring(arr[2].indexOf("/")+1));
			}
			if(arr[3].indexOf("/")>0){
				$DW.$S("PeriodType","Month");
				$DW.$S("Period",arr[3].substring(arr[3].indexOf("/")+1));
			}
			if(arr[4].indexOf("/")>0){
				$DW.$S("PeriodType","Year");
				$DW.$S("Period",arr[4].substring(arr[4].indexOf("/")+1));
			}
		}		
		$DW.onPlanChange();
		$DW.$("TypeCode").disable();
		$DW.$("SourceID").disable();
	}
	diag.OKEvent = save;
	diag.show();
}

function execute(){
	var dt = DataGrid.getSelectedData("dg1");
	if(dt.getRowCount()==0){
		Dialog.alert("����ѡ��Ҫ�޸ĵ���!");
		return;
	}
	dr = dt.getDataRow(0);
	var dc = new DataCollection();
	dc.add("ID",dr.get("ID"));
	Server.sendRequest("com.nswt.platform.Schedule.execute",dc,function(response){
		Dialog.alert(response.Message);
	});
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
		Server.sendRequest("com.nswt.platform.Schedule.del",dc,function(response){
			if(response.Status==0){
				Dialog.alert(response.Message);
			}else{
				Dialog.alert("ɾ���ɹ�");
				DataGrid.setParam("dg1",Constant.PageIndex,0);
        DataGrid.loadData("dg1");
			}
		});
	});
} 
</script>
</head>
<z:init method="com.nswt.platform.Schedule.init">
	<body>
	<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
      <tr valign="top">
        <td><table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
            <tr>
              <td valign="middle" class="blockTd"><img src="../Icons/icon020a1.gif" width="20" height="20" /> ��ʱ�ƻ��б�</td>
            </tr>
				<tr>
					<td style="padding:0 8px 4px;"><z:tbutton onClick="add()"><img src="../Icons/icon020a2.gif" width="20" height="20" />�½�</z:tbutton>
                  <z:tbutton onClick="edit()"> <img src="../Icons/icon020a4.gif" width="20" height="20" />�޸�</z:tbutton>
                  <z:tbutton onClick="del()"> <img src="../Icons/icon020a3.gif" width="20" height="20" />ɾ��</z:tbutton>
                  <z:tbutton onClick="execute()"> <img src="../Icons/icon020a9.gif" width="20" height="20" />�ֶ�ִ��</z:tbutton>
			 </td>
            </tr>
            <tr>
              <td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
			  <z:datagrid id="dg1" method="com.nswt.platform.Schedule.dg1DataBind" size="15">
                <table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
                  <tr ztype="head" class="dataTableHead">
                    <td  width="3%" align="center" ztype="RowNo">&nbsp;</td>
                    <td width="3%" align="center" ztype="selector" field="id">&nbsp;</td>
                    <td width="12%"><b>���</b></td>
                    <td width="20%"><b>��������</b></td>
                    <td width="8%">�Ƿ�����</td>
                    <td width="16%"><b>�´�����ʱ��</b></td>
                    <td width="32%"><b>��������</b></td>
                  </tr>
                  <tr onDblClick="edit();">
                    <td align="center">&nbsp;</td>
                    <td align="center">&nbsp;</td>
                    <td>${TypeCodeName}</td>
                    <td>${SourceIDName}</td>
                    <td>${IsUsingName}</td>
                    <td>${NextRunTime}</td>
                    <td>${Description}</td>
                  </tr>
                  <tr ztype="pagebar">
                    <td colspan="7">${PageBar}</td>
                  </tr>
                </table>
              </z:datagrid></td>
            </tr>
        </table></td>
      </tr>
    </table>
	</body>
</z:init>
</html>
