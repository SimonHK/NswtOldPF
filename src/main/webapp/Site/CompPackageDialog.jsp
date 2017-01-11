<%--
  Created by IntelliJ IDEA.
  User: SimonKing
  Date: 16/11/1
  Time: 19:00
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
    <title></title>
    <script src="../Framework/Main.js"></script>
    <script type="text/javascript">
        function checkOrder(ele){
            var value = $V(ele);
            var re = /[\\\/\:\*\?\"\<\>\|]/g;
            if(re.test(value)){
                show();
                $S(ele,value.replace(re,""));
            }else{
                if($("div_hint").style.display==""){
                    setTimeout("hide()",3000);
                }
            }
        }

        function show(){
            $("div_hint").style.display="";
        }

        function hide(){
            $("div_hint").style.display="none";
        }
    </script>
    <link href="../Include/Default.css" rel="stylesheet" type="text/css" />
    <meta http-equiv="Content-Type" content="text/html; charset=gbk">
</head>
<body class="dialogBody">
<form id="form2">
    <table width="100%" cellpadding="2" cellspacing="0">
        <tr>
            <td width="24%" height="20" align="right"></td>
            <td width="76%"></td>
        </tr>
        <tr id="tr_file">
            <td align="right">命令：</td>
            <td><input name="mvnOrder" type="text" value="" id="mvnOrder"
                       size=30 onKeyUp="checkOrder(this)" /></td>
        </tr>
        <tr>
            <td></td>
            <td>
                <div id="div_hint" style="display:none"><font color="red">命令不能包含下列字符:\/:*?"<>|</font></div>
            </td>
        </tr>
    </table>
</form>
</body>
</html>

