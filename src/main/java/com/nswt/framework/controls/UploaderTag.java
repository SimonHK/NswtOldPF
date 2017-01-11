package com.nswt.framework.controls;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import com.nswt.framework.Config;
import com.nswt.framework.utility.StringUtil;

/**
 * @Author �솴
 * @Date 2016-03-12
 * @Mail xuzhe@nswt.com
 */
public class UploaderTag extends BodyTagSupport {
	private static final long serialVersionUID = 1L;

	private String id;
	
	private String name;

	private String action;
	
	private String barcolor;

	private String width;

	private String height;
	
	private String uploadtype;

	private String allowtype;
	
	private String onComplete;
	
	private String fileCount;
	
	private String fileMaxSize;
	
	public void setPageContext(PageContext pc) {
		super.setPageContext(pc);
		this.id = null;
		this.action = null;
		this.barcolor = null;
		this.width = null;
		this.height = null;
		this.uploadtype=null;
		this.allowtype = null;
		this.onComplete = null;
		this.fileCount = null;
		this.fileMaxSize = null;
	}

	public int doAfterBody() throws JspException {
		String content = this.getBodyContent().getString();
		try {
			this.getPreviousOut().print(getHtml(content));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

	public int doEndTag() throws JspException {// û��bodyʱҲҪִ��
		if (bodyContent == null || StringUtil.isEmpty(bodyContent.getString())) {
			try {
				this.pageContext.getOut().print(getHtml(""));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return EVAL_PAGE;
	}

	/**
	 * ������Java�ļ��е���
	 */
	public String getHtml(String content) {
		String FlashVars = "";
		String srcSWF = "ZUploader.swf";
		if (StringUtil.isEmpty(id)) {// �������ID
			id = "_NSWT_NOID_";
		}
		if (StringUtil.isEmpty(name)) {
			name = id;
		}
		
		if (StringUtil.isNotEmpty(action)) {
			FlashVars += "actionURL="+action;
		}
		
		if (StringUtil.isNotEmpty(uploadtype)) {
			String types = "";
			if(uploadtype.equals("Image")){
				types = Config.getValue("AllowImageExt");
			}else if(uploadtype.equals("Video")){
				srcSWF = "ZUploader_Video.swf";
				types = Config.getValue("AllowVideoExt");
			}else if(uploadtype.equals("Audio")){
				types = Config.getValue("AllowAudioExt");
			}else if(uploadtype.equals("Attach")){
				types = Config.getValue("AllowAttachExt");
			}
			if(StringUtil.isNotEmpty(types)){
				this.allowtype = types;
			}
		}else{
			uploadtype = "";
		}
		
		if (StringUtil.isNotEmpty(allowtype)) {
			FlashVars += "&fileType="+allowtype;
		}
		if (StringUtil.isNotEmpty(barcolor)) {
			FlashVars += "&barColor="+barcolor;
		}
		if(StringUtil.isNotEmpty(onComplete)){
			FlashVars += "&onComplete="+onComplete;
		}
		if(StringUtil.isNotEmpty(fileCount)&&fileCount!="0"){
			FlashVars += "&fileCount="+fileCount;
		}
		if(StringUtil.isNotEmpty(fileMaxSize)&&fileMaxSize!="0"){
			FlashVars += "&fileMaxSize="+fileMaxSize;
		}
		if(StringUtil.isEmpty(width)){
			width = "250";
		}
		if(uploadtype.equals("Video")&&Integer.parseInt(width)<400){
			width = "450";
		}
		if(StringUtil.isEmpty(height)){
			height = "25";
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append("<object id="+id+" classid='clsid:D27CDB6E-AE6D-11cf-96B8-444553540000' width='"+width+"' height='"+height+"' style='vertical-align:middle;'>\n");
		sb.append("<param name='movie' value='"+Config.getContextPath()+"Tools/"+srcSWF+"'>\n");
		sb.append("<param name='quality' value='high'>\n");
		sb.append("<param name='wmode' value='transparent'>\n");
		sb.append("<param name='FlashVars' value='"+FlashVars+"'>\n");
		sb.append("<embed name='"+name+"' src='"+Config.getContextPath()+"Tools/"+srcSWF+"' FlashVars='"+FlashVars+"' quality='high' wmode='transparent' width='"+width+"' height='"+height+"'></embed>\n");
		sb.append("</object>\n");
		sb.append("<script src='"+Config.getContextPath()+"Services/Stat.js' language='javascript'></script>\n");
		sb.append("<script type='text/javascript'>\n");
		sb.append("function getUploader(movieName) {return document[movieName];}\n");
		sb.append("function getZUploaderTaskStatus(taskID){\n");
		sb.append("var dc = new DataCollection();\n");
		sb.append("dc.add('taskID',taskID);\n");
		sb.append("Server.sendRequest('com.nswt.framework.controls.UploadStatus.getTaskStatus',dc,function(response){\n");
		sb.append("var arr = [response.get('FileIDs'),response.get('Types'),response.get('Paths'),response.get('Status')];\n");
		sb.append("getUploader('"+id+"').setStatusStr(arr);\n");
		sb.append("})}");
		if(!uploadtype.equals("Video")){
			sb.append("function doResize(fileLength,cCount,cType){\n");
			sb.append("if(fileLength>1&&fileLength<=6&&cType=='add'){\n");
			sb.append("if(fileLength==2){cCount=cCount+1;}\n");
			sb.append("if(getUploader('"+id+"').height>187){return;}\n");
			sb.append("getUploader('"+id+"').height=new Number(getUploader('"+id+"').height)+(27*cCount);\n");
			sb.append("}else if(fileLength<6&&cType=='del'){\n");
			sb.append("if(fileLength==1){cCount=cCount+1;}\n");
			sb.append("getUploader('"+id+"').height=new Number(getUploader('"+id+"').height)-(27*cCount);}}\n");
		}else{
			sb.append("function doResize(){\n");
			sb.append("getUploader('"+id+"').height=60;}\n");
		}
		sb.append("\n");
		sb.append("var versionOK = false;\n");
		sb.append("var flashVersion = _nswtp_stat.fv();\n");
		sb.append("if(flashVersion&&flashVersion!=''){\n");
		sb.append("flashVersion = flashVersion.substr(0,flashVersion.indexOf('.'));\n");
		sb.append("if(flashVersion>9){versionOK = true;}}\n");
		sb.append("if (!versionOK) {\n");
		sb.append("var alternateContent = \"<BR/>��⵽����Adobe Flash Player�汾����<BR/>���������߰�װ���°汾��Flash Player<a href='http://www.adobe.com/go/getflash/' target='_blank'>���߰�װ</a>\";\n");
		sb.append("document.write(alternateContent);\n");
		sb.append("}\n");
		sb.append("</script>\n");
		return sb.toString();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String width() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	public String getUploadtype() {
		return uploadtype;
	}

	public void setUploadtype(String uploadtype) {
		this.uploadtype = uploadtype;
	}
	
	public String getAllowtype() {
		return allowtype;
	}

	public void setAllowtype(String allowtype) {
		this.allowtype = allowtype;
	}
	
	public String getBarcolor() {
		return barcolor;
	}

	public void setBarcolor(String barcolor) {
		this.barcolor = barcolor;
	}
	
	public String getonComplete() {
		return onComplete;
	}

	public void setonComplete(String onComplete) {
		this.onComplete = onComplete;
	}
	
	public String getfileCount() {
		return fileCount;
	}

	public void setfileCount(String fileCount) {
		this.fileCount = fileCount;
	}

	public String getFileMaxSize() {
		return fileMaxSize;
	}

	public void setFileMaxSize(String fileMaxSize) {
		this.fileMaxSize = fileMaxSize;
	}
}
