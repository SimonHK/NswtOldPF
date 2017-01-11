<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="com.nswt.framework.Config"%>
<%@page import="com.nswt.framework.User"%>
<%@page import="com.nswt.framework.controls.UploadStatus"%>
<%@page import="com.nswt.cms.pub.SiteUtil"%>
<%@page import="com.nswt.framework.utility.Mapx"%>
<%@page import="org.apache.commons.fileupload.*"%>
<%@page import="org.apache.commons.fileupload.servlet.*"%>
<%@page import="org.apache.commons.fileupload.disk.*"%>
<%
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
String siteID = fields.getString("SiteID");
String taskId = fields.getString("taskID");
String siteCode = SiteUtil.getAlias(siteID);
String uploadPath = "",ext="",fileType="false";
UploadStatus.setTask(taskId, "0",fileType, "/", "处理中");
String oldFileName = fields.getString("Filename");
ext = oldFileName.substring(oldFileName.lastIndexOf("."));
String realDir = Config.getContextRealPath()+Config.getValue("Statical.TemplateDir")+"/";
if ("avicit".equals(siteCode)) {
	uploadPath = realDir+siteCode+"/";
}else{
	uploadPath = realDir+siteCode+Config.getValue("Statical.WebRootDir")+"/";
}

if(".zip".equals(ext.toLowerCase())){
    uploadPath = uploadPath+"temp/"+siteCode+"_"+System.currentTimeMillis()+"/";
    fileType = "true";
 }
uploadPath = uploadPath.replaceAll("//","/");
uploadPath = uploadPath.replaceAll("///","/");
File path = new File(uploadPath);
if(!path.exists()){
    path.mkdirs();
}

uploadPath = uploadPath+oldFileName;
try{
	uploadItem.write(new File(uploadPath));
  UploadStatus.setTask(taskId, "0",fileType, uploadPath, "上传完成");
}catch(Exception e){
	e.printStackTrace();
	return;
}
%>
