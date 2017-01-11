<%@page import="com.nswt.framework.controls.UploadStatus"%>
<%@page import="com.nswt.framework.utility.Mapx"%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="org.apache.commons.fileupload.*"%>
<%@page import="org.apache.commons.fileupload.servlet.*"%>
<%@page import="org.apache.commons.fileupload.disk.*"%>
<%
String uploadPath = "",ext="";
FileItemFactory fileFactory = new DiskFileItemFactory();
ServletFileUpload fu = new ServletFileUpload(fileFactory);
List fileItems = fu.parseRequest(request);
String newFileName = "";
boolean isSucess = true;

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
UploadStatus.setTask(taskId, "0","keywords", "/", "处理中");
String oldFileName = fields.getString("Filename");
System.out.println("UploadFileName:"+oldFileName);
oldFileName = oldFileName.substring(oldFileName.lastIndexOf("\\")+1);
ext = oldFileName.substring(oldFileName.lastIndexOf("."));
String realDir = request.getRealPath("/");
uploadPath = realDir+"/Upload/Temp/";

File path = new File(uploadPath);
if(!path.exists()){
    path.mkdirs();
}

newFileName = "BadWordImport_"+System.currentTimeMillis()+ext;
uploadPath = uploadPath+newFileName;
uploadPath = uploadPath.replace('\\', '/');
System.out.println(uploadPath);
try{
  uploadItem.write(new File(uploadPath));
  isSucess = true;
}catch(Exception e){
	e.printStackTrace();
	isSucess = false;
}
		
if(isSucess){
	UploadStatus.setTask(taskId, "0","keywords", uploadPath, "上传完成");
}else{
	UploadStatus.setTask(taskId, "0","keywords", uploadPath, "上传失败");
}

%>
