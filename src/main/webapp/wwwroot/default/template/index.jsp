<%--
  Created by IntelliJ IDEA.
  User: SimonKing
  Date: 16/11/3
  Time: 14:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
    <title>${site.name}</title>
    <link href="../images/style.css" type="text/css" rel="stylesheet" />
    <script language="javascript" type="text/javascript"> function mod_Change(sname)
    {
        var objSelect= document.getElementById(sname);
        for(var i=0;i<objSelect.options.length;i++)
        {
            if(objSelect.options[i].selected)
            {
                window.open(objSelect.options[i].value);
                break;
            }
        }
    }
    </script>
</head>
<body>
<table class="Table" border="0" cellspacing="0" cellpadding="0" align="center">
    <tr>
        <td><img src="../images/logo.gif" align="left" /></td>
        <td width="252" valign="bottom" >
            <form action="${System.SearchAction}">
                <div class="Div_search"><input type="text" size="22" name="query" id="query" /> <input type="hidden" name="site" value="${Site.ID}" /></div>
                <div align="right"><input type="submit" class="sea" value="&nbsp;" /></div>
            </form>
        </td>
    </tr>
</table>
<table class="Table" border="0" cellspacing="0" cellpadding="0" align="center">
    <tr>
        <td class="Nav_bg">
            <table width="90%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td align="center"><a href="<cms:link type="catalog" name="关于我们/机构设置"></cms:link>" class="navTxt02">关于我们</a></td>
                    <td align="center"><a href="<cms:link type="catalog" name="科研力量/科研机构"></cms:link>" class="navTxt02">科研力量</a></td>
                    <td align="center"><a href="<cms:link type="catalog" name="文化建设/形象建设"></cms:link>" class="navTxt02">文化建设</a></td>
                    <td align="center"><a href="<cms:link type="catalog" name="人力资源/招聘信息"></cms:link>" class="navTxt02">人力资源</a></td>
                    <td align="center"><a href="<cms:link type="catalog" name="新闻中心"></cms:link>" class="navTxt02">新闻中心</a></td>
                    <td align="center"><a href="<cms:link type="catalog" name="联系我们"></cms:link>" class="navTxt02">联系我们</a></td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td><img src="../images/Img01.jpg" /></td>
    </tr>
</table>
<table class="Table" border="0" cellspacing="0" cellpadding="0" align="center">
    <tr>
        <td width="35%" align="left" valign="top">
            <table width="100%" border="0" cellspacing="3" cellpadding="3">
                <tr>
                    <td colspan="2"><img src="../images/Img_txt.gif"  /></td>
                </tr>
                <tr>
                    <td colspan="2">
                        <select class="select"  onchange="mod_Change('one')" id="one">
                            <option>基础院成员单位网站链接</option>
                            <cms:list item="friendlink" name="基础院成员单位网站链接">
                                <option value="${friendlink.url}">${friendlink.name}</option>
                            </cms:list>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td width="60%">
                        <select class="select"  onchange="mod_Change('two')" id="two">
                            <option>集团其他网站链接</option>
                            <cms:list item="friendlink" name="集团其他网站链接">
                                <option value="${friendlink.url}">${friendlink.name}</option>
                            </cms:list>
                        </select>
                    </td>
                    <td width="40%"><a href="<cms:link type="catalog" name="联系我们"></cms:link>"><img src="../images/Btn_more.gif" border="0" /></a></td>
                </tr>
            </table>

        </td>
        <td width="65%"  valign="top">
            <table width="98%"  border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td align="left" class="TitleTxt01" style="padding-top:10px; padding-left:12px;">最新新闻</td>
                </tr>
                <tr>
                    <td class="Td_border" style="border:none; padding:0;">
                        <table width="100%" border="0" cellspacing="2" cellpadding="2">
                            <cms:list item="article" count="1" type="recent" name="新闻中心/集团新闻,新闻中心/基础院新闻">

                                <tr>
                                    <td colspan="2" class="TitleTxt02"><a href="${article.link}">${article.title|charwidth=30}</a></td>
                                </tr>
                            </cms:list>
                            <cms:list item="article" count="2" type="recent" begin="1" name="新闻中心/集团新闻,新闻中心/基础院新闻">
                                <tr>
                                    <td align="left" class="td_dashed list"><a href="${article.link}" class="Txt01">${article.title|charwidth=28}</a></td>
                                    <td align="right" class="td_dashed">[${article.publishdate|format=yyyy-MM-dd}]</td>
                                </tr>
                            </cms:list>
                            <tr>
                                <td colspan="2" align="right" style="padding-top:7px;"><a class="Txt02" href="<cms:link type="catalog" name="新闻中心"></cms:link>" >&gt;&gt;了解更多</a></td>
                            </tr>

                        </table>
                    </td>
                </tr>
            </table>

        </td>
    </tr>
</table>
<cms:include file="include/footer.html"></cms:include>
</body>
</html>
