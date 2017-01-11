<%@page import="java.io.File"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.nswt.framework.User"%>
<%@page import="com.nswt.framework.utility.Mapx"%>
<%@page import="com.nswt.framework.controls.UploadStatus"%>
<%@page import="com.nswt.framework.schedule.CronManager"%>
<%@page import="com.nswt.framework.orm.DBImport"%>
<%@page import="com.nswt.framework.SessionListener"%>
<%@page import="com.nswt.framework.messages.LongTimeTask"%>
<%@page import="com.nswt.framework.utility.DateUtil"%>
<%@page import="com.nswt.framework.Config"%>
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
	String OldFileName = fields.getString("Filename");
	LogUtil.info("Upload DB FileName:" + OldFileName);
	UploadStatus.setTask(taskId, "0","DBFile", "/", "处理中");
	String FileName = Config.getContextRealPath() + "WEB-INF/data/backup/DBUpload_"+ DateUtil.getCurrentDate("yyyyMMddHHmmss") + ".dat";
	uploadItem.write(new File(FileName));
	UploadStatus.setTask(taskId, "0","DBFile", FileName, "上传完成");
} catch (Exception e) {
	e.printStackTrace();
}
%>