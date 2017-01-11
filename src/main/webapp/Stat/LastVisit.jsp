<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>�ۺϱ���</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<style>
.visitList table { table-layout:fixed;}
.visitList td { color:#777; }
.visitList td a { color:#336699; }
.visitList td em {}
.visitList td .strong { color:#333; }
.visitList td .items { width:100%; overflow:hidden;  white-space:nowrap; padding:3px 0;}
.visitList td .buleBg { background-color:#F3F9FF; -moz-border-radius: 3px; -webkit-border-radius: 3px; padding:4px 0; }
.visitList td .item { margin:0 1em 0 0.5em; ; }
</style>
<script src="../Framework/Main.js"></script>
<script src="../Framework/Chart.js"></script>
<script>
Page.onLoad(function(){
	var path = window.location.pathname;
	path = path.substring(path.lastIndexOf("/")+1);
	Tree.select("tree1","target",path);
});
</script>
</head>
<body>
<input type="hidden" id="CatalogID">
<input type="hidden" id="ListType" value='${ListType}'>
<table width="100%" border="0" cellspacing="6" cellpadding="0"
	style="border-collapse: separate; border-spacing: 6px;">
  <tr valign="top">
    <td width="160"><%@include file="StatTypes.jsp"%></td>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0" class="blockTable" style="table-layout:fixed;">
        <tr>
          <td style="padding:4px 10px;"><div style="float:left;"><strong>������ʼ�¼:</strong></div></td>
        </tr>
        <tr>
          <td style="padding-top:2px;padding-left:6px;padding-right:6px;padding-bottom:8px;" class="visitList">
              <table width="100%" border="1" bordercolor="#dddddd" cellpadding="0" cellspacing="0">
          <z:datalist id="dg1" method="com.nswt.cms.stat.report.LastVisitReport.dg1DataBind" page="true" size="20">
                <tr>
                  <td width="24" align="center">${RowNo}</td>
                  <td style="padding:5px;"><div class="items"><span class="item strong">
                      <b>IP��${IP}</b> ( <b>${District}</b> ) </span> ������ҳ�� <a href="${URL}" target="_blank">
                      ${URL}
                      </a></div>
                    <div class="items"><span class="item">����ʱ�䣺<em>
                      ${AddTime}
                      </em></span>��ԴURL��<a href="${Referer}" target="_blank">
                      ${Referer}
                      </a></div>
                    <div class="items buleBg"><span class="item">�������<em>
                      ${Browser}
                      </em></span><span class="item">����ϵͳ��<em>
                      ${OS}
                      </em></span><span class="item">��Ļ��С��<em>
                      ${Screen}
                      </em></span><span class="item">���ԣ�<em>
                      ${Language}
                      </em></span>Flash�汾��<em>
                      ${FlashVersion}
                      </em></div></td>
                </tr>
            </z:datalist>
              </table>
            <z:pagebar target="dg1" /></td>
        </tr>
      </table></td>
  </tr>
</table>
</body>
</html>
