<%@page import="java.io.*"%>
<%@page import="com.nswt.framework.utility.*"%>
<%@page import="com.nswt.framework.data.*"%>
<%@page import="com.nswt.platform.Application" %>
<%
	String ids = request.getParameter("ids");
	String selectedCID = request.getParameter("selectedCID");
	String sql = "";
	DataTable dt = null;
	if(StringUtil.isNotEmpty(ids)){
		sql = "select KeyWord,LinkUrl,LinkAlt,LinkTarget,KeywordType from ZCKeyWord where id in ("+ids+") order by Keyword";
		dt = new QueryBuilder(sql).executeDataTable();
	}else if(StringUtil.isNotEmpty(selectedCID)&&!selectedCID.equals("null")){
		sql = "select KeyWord,LinkUrl,LinkAlt,LinkTarget,KeywordType from ZCKeyWord where KeywordType like '%,"+selectedCID+",%' order by Keyword";
		dt = new QueryBuilder(sql).executeDataTable();
	}else{
		sql = "select KeyWord,LinkUrl,LinkAlt,LinkTarget,KeywordType from ZCKeyWord where siteID = ? order by Keyword";
		dt = new QueryBuilder(sql,Application.getCurrentSiteID()).executeDataTable();
	}
	
	for(int i=0;i<dt.getRowCount();i++){
		if(dt.getString(i,"LinkTarget").equals("_self")){
			dt.set(i,"LinkTarget",1);
		}else if(dt.getString(i,"LinkTarget").equals("_blank")){
			dt.set(i,"LinkTarget",2);
		}else if(dt.getString(i,"LinkTarget").equals("_parent")){
			dt.set(i,"LinkTarget",3);
		}else{
			dt.set(i,"LinkTarget",1);
		}
		
		DataTable keywordTypeDT = new QueryBuilder("select TypeName from ZCKeywordType where ID in ("+dt.getString(i,"KeywordType").substring(1,dt.getString(i,"KeywordType").length()-1) +")").executeDataTable();
		dt.set(i,"KeywordType",StringUtil.join(keywordTypeDT.getColumnValues("TypeName")).replaceAll(",","/"));
	}
	String[] columnNames = new String[]{"�ؼ���","���ӵ�ַ","������ʾ","�򿪷�ʽ","�����ȵ�ʷ���"};
	String[] widths = new String[]{"100","200","120","70","400"};
	String now = DateUtil.getCurrentDate();
	try {
		OutputStream os = response.getOutputStream();
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename="+ new String(("�ȵ�ʵ���"+now+".xls").getBytes("GBK"),"ISO-8859-1"));
		//DataTableUtil.dataTableToTxt(dt,os,new String[0]);
		DataTableUtil.dataTableToExcel(dt,os,columnNames,widths);
		os.flush();
		os.close();
	} catch (IOException e) {
		e.printStackTrace();
	}	
%>
