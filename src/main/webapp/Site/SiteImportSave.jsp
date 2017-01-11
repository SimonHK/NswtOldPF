<%@page import="java.io.File"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="com.nswt.framework.controls.UploadStatus"%>
<%@page import="com.nswt.framework.utility.Mapx"%>
<%@page import="com.nswt.framework.Config"%>
<%@page import="com.nswt.framework.utility.DateUtil"%>
<%@page import="com.nswt.framework.utility.LogUtil"%>
<%@page import="org.apache.commons.fileupload.FileItem"%>
<%@page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>
<%@page import="org.apache.commons.fileupload.disk.DiskFileItemFactory"%>
<%
try {
	DiskFileItemFactory fileFactory = new DiskFileItemFactory();
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
	UploadStatus.setTask(taskId, "0","siteFile", "/", "处理中");
	String OldFileName = fields.getString("Filename");
	LogUtil.info("Upload Site FileName:" + OldFileName);
	String FileName = "SiteUpload_" + DateUtil.getCurrentDate("yyyyMMddHHmmss") + ".dat";
	String Path = Config.getContextRealPath() + "WEB-INF/data/backup/";
	File dir = new File(Path);
	if(!dir.exists()){
		dir.mkdirs();
	}
	uploadItem.write(new File(Path + FileName));
	UploadStatus.setTask(taskId, "0","siteFile", FileName, "上传完成");
} catch (Exception e) {
	e.printStackTrace();
}
%>