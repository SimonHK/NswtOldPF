
<%@page import="com.nswt.framework.controls.UploadStatus"%>
<%@page import="com.nswt.framework.utility.Mapx"%><%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="org.apache.commons.fileupload.*"%>
<%@page import="org.apache.commons.fileupload.servlet.*"%>
<%@page import="org.apache.commons.fileupload.disk.*"%>
<%
boolean isSucess = false;
FileItemFactory fileFactory = new DiskFileItemFactory();
ServletFileUpload fu = new ServletFileUpload(fileFactory);
List fileItems = fu.parseRequest(request);
fu.setHeaderEncoding("UTF-8");
Iterator iter = fileItems.iterator();
Mapx fields = new Mapx();
FileItem uploadItem = null;
while (iter.hasNext()){
	FileItem item = (FileItem) iter.next();
	if (item.isFormField()){
		fields.put(item.getFieldName(), item.getString("UTF-8"));
	}else{
		uploadItem = item;
	}
}
String taskId = fields.getString("taskID");
String siteID = fields.getString("SiteID");
String Path = fields.getString("Path");
String oldFileName = fields.getString("Filename");
UploadStatus.setTask(taskId, "0","File", "", "处理中");
String uploadPath = Path+"/";
String ext="";
System.out.println("UploadFileName:"+oldFileName);
File path = new File(uploadPath);
if(!path.exists()){
    path.mkdirs();
}

uploadPath = uploadPath+oldFileName;
System.out.println(uploadPath);
try{
	uploadItem.write(new File(uploadPath));
	UploadStatus.setTask(taskId, "0","File", uploadPath, "上传完成");
}catch(Exception e){
	e.printStackTrace();
	return;
}
%>
