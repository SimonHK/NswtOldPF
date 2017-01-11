<%@page import="com.nswt.framework.utility.ZipUtil"%>
<%@page import="com.nswt.framework.utility.FileUtil"%>
<%@page import="com.nswt.framework.Config"%>
<%@page import="java.io.File"%>
<%@page import="com.nswt.framework.controls.UploadStatus"%>
<%@page import="com.nswt.platform.pub.NoUtil"%>
<%@page import="com.nswt.framework.utility.StringUtil"%>
<%@page import="com.nswt.framework.Constant"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.nswt.framework.utility.Mapx"%><%@page import="org.apache.commons.fileupload.FileItem"%>
<%@page import="org.apache.commons.fileupload.disk.DiskFileItemFactory"%>
<%@page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>
<%@page import="org.apache.commons.fileupload.FileItemFactory"%>
<%

boolean isSucess = false;
FileItemFactory fileFactory = new DiskFileItemFactory();
ServletFileUpload fu = new ServletFileUpload(fileFactory);
List fileItems = fu.parseRequest(request);
Mapx fields = new Mapx();
Mapx files = new Mapx();

fu.setHeaderEncoding("UTF-8");
Iterator iter = fileItems.iterator();

while (iter.hasNext()) {
	FileItem item = (FileItem) iter.next();
	if (item.isFormField()) {
		fields.put(item.getFieldName(), item.getString("UTF-8"));
	} else {
		String OldFileName = item.getName();
		long size = item.getSize();
		if ((OldFileName == null || OldFileName.equals("")) && size == 0) {
			continue;
		} else {
			files.put(item.getFieldName(), item);
		}
	}
}

String styleID = fields.getString("ImagePlayerStyleID");
long ImagePlayerStyleID = 0;
String Name = fields.getString("Name");
String Memo = fields.getString("Memo");
String IsDefault = fields.getString("IsDefault");


boolean update = false;
if(StringUtil.isEmpty(styleID)){
	styleID = NoUtil.getMaxNo("ImagePlayerStyleID");
}
ImagePlayerStyleID = Long.parseLong(styleID);
String path = "/Upload/ImagePlayerStyles/"+ImagePlayerStyleID+"/";
UploadStatus.setTask(fields.getString("taskID"), ImagePlayerStyleID+"","playerStyle", path, "处理中");
File file = new File(Config.getContextRealPath()+"/Upload/ImagePlayerStyles/"+ImagePlayerStyleID+"/");
if(!file.exists()){
	file.mkdirs();
}
String uploadFile = "";
iter = files.keySet().iterator();
while (iter.hasNext()){
	FileItem item = (FileItem) files.get(iter.next());
	String oldFileName = item.getName();
	System.out.println("UploadFileName:"+oldFileName);
	long size = item.getSize();
	if((oldFileName==null||oldFileName.equals("")) && size==0){
		continue;
	}
	oldFileName = oldFileName.substring(oldFileName.lastIndexOf("\\")+1);
	String ext = oldFileName.substring(oldFileName.lastIndexOf(".")+1);
	if("zip".equalsIgnoreCase(ext)){
		String tempPath = ("/Upload/ImagePlayerStyles/Temp/");
		new File(Config.getContextRealPath()+tempPath).mkdirs();
		uploadFile = tempPath+ImagePlayerStyleID+"."+ext;
		
		try{
			  item.write(new File(Config.getContextRealPath()+uploadFile));
			  String filePath = Config.getContextRealPath()+path;
			  File dir = new File(filePath);
			  if(!dir.exists()){
				  new File(filePath).mkdirs();
			  }
			  FileUtil.deleteFromDir(filePath);
			  ZipUtil.unzip(Config.getContextRealPath()+uploadFile, filePath, false);
			  File[] f = dir.listFiles();
			  dir = new File(Config.getContextRealPath()+tempPath);
			  FileUtil.deleteFromDir(dir.getAbsolutePath());
			  dir.delete();
			  isSucess = true;
			}catch(Exception e){
				e.printStackTrace();
				return;
			}
	}else{
		UploadStatus.setTask(fields.getString("taskID"), ImagePlayerStyleID+"","playerStyle", path, "上传失败，格式不正确");
	    return;
	}
}
if(isSucess){
	UploadStatus.setTask(fields.getString("taskID"), ImagePlayerStyleID+"","playerStyle", path, "提交完成");
}else{
	UploadStatus.setTask(fields.getString("taskID"), ImagePlayerStyleID+"","playerStyle", path, "提交失败");
}
%>