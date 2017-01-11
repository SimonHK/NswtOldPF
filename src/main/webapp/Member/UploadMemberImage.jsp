<%@page import="com.nswt.platform.pub.ImageUtilEx"%>
<%@page import="com.nswt.schema.ZCImageSchema"%>
<%@page import="com.nswt.cms.pub.PubFun"%>
<%@page import="com.nswt.framework.User"%>
<%@page import="com.nswt.cms.pub.CatalogUtil"%>
<%@page import="com.nswt.platform.pub.OrderUtil"%>
<%@page import="org.apache.commons.io.FileUtils"%>
<%@page import="com.nswt.framework.utility.NumberUtil"%>
<%@page import="com.nswt.framework.utility.ChineseSpelling"%>
<%@page import="com.nswt.schema.ZCCatalogSchema"%>
<%@page import="com.nswt.member.Member"%>
<%@page import="com.nswt.cms.site.Catalog"%>
<%@page import="com.nswt.framework.data.QueryBuilder"%>
<%@page import="com.nswt.framework.utility.StringUtil"%>
<%@page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>
<%@page import="org.apache.commons.fileupload.disk.DiskFileItemFactory"%>
<%@page import="com.nswt.platform.pub.NoUtil"%>
<%@page import="com.nswt.framework.Config"%>
<%@page import="org.apache.commons.fileupload.*"%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%
String UserName = request.getParameter("UserName");
String Path = "";
String CatalogID = "";
String innerCode = "";
String SiteID    = "";
String SiteAlias = "";
if(StringUtil.isEmpty(UserName)){
	UserName = User.getValue("UserName")+"";
}
Member m = new Member(UserName);
m.fill();
SiteID = m.getSiteID()+"";
SiteAlias = new QueryBuilder("select Alias from ZCSite where ID = ?",m.getSiteID()).executeString();
if(new QueryBuilder("select count(*) from zccatalog where SiteID = "+m.getSiteID()+" and Type= "+Catalog.IMAGELIB+" and Name = '��Ա�ϴ�'").executeInt()>0){
	CatalogID = new QueryBuilder("select ID from ZCCatalog where SiteID = "+m.getSiteID()+" and Type = "+Catalog.IMAGELIB+" and Name = '��Ա�ϴ�'").executeOneValue()+"";
	Path = "upload/Image/"+new QueryBuilder("select Alias from ZCCatalog where ID = "+CatalogID).executeString()+"/";
	innerCode = CatalogUtil.getName(CatalogID);
}else{
	CatalogID = NoUtil.getMaxID("CatalogID")+"";
	ZCCatalogSchema catalog = new ZCCatalogSchema();
	catalog.setID(CatalogID);
	catalog.setSiteID(m.getSiteID());
	catalog.setParentID(0);
	catalog.setInnerCode(NoUtil.getMaxNo("CatalogInnerCode", 4));
	catalog.setTreeLevel(1);
	catalog.setName("��Ա�ϴ�");
	catalog.setURL("");
	String alias = ChineseSpelling.getFirstAlpha("��Ա�ϴ�").toLowerCase();
	if(StringUtil.isEmpty(Catalog.checkAliasExists(alias,catalog.getParentID()))){
		alias +=NoUtil.getMaxID("AliasNo");
	}
	catalog.setAlias(alias);
	catalog.setType(Catalog.IMAGELIB);
	catalog.setListTemplate("");
	catalog.setListNameRule("");
	catalog.setDetailTemplate("");
	catalog.setDetailNameRule("");
	catalog.setChildCount(0);
	catalog.setIsLeaf(1);
	catalog.setTotal(0);
	catalog.setOrderFlag(Catalog.getCatalogOrderFlag(0, catalog.getType()+""));
	catalog.setLogo("");
	catalog.setListPageSize(10);
	catalog.setPublishFlag("Y");
	catalog.setHitCount(0);
	catalog.setMeta_Keywords("");
	catalog.setMeta_Description("");
	catalog.setOrderColumn("");
	catalog.setImagePath("");
	catalog.setAddUser(m.getUserName());
	catalog.setAddTime(new Date());
	catalog.insert();
	Path = "upload/Image/"+catalog.getAlias()+"/";
	innerCode = catalog.getInnerCode();
}
File f = new File(Config.getContextRealPath()+Config.getValue("UploadDir") + "/" + SiteAlias + "/"+Path);
if(!f.exists()){
	f.mkdirs();
}
FileItemFactory fileFactory = new DiskFileItemFactory();
ServletFileUpload fu = new ServletFileUpload(fileFactory);
List fileItems = fu.parseRequest(request);
String oldFileName = "";
String ext = "";
String AliasName = Config.getAppCode();
fu.setHeaderEncoding("GBK");
Iterator iter = fileItems.iterator();
FileItem uploadItem = null;
while (iter.hasNext()){
	FileItem item = (FileItem) iter.next();
	if (!item.isFormField()){
		oldFileName = item.getName();
		System.out.println("-----UploadFileName:-----" + oldFileName);
		long size = item.getSize();
		if((oldFileName==null||oldFileName.equals("")) && size==0){
			continue;
		} else {
			if(size>2000000){
				out.println("<script>alert('�ļ�̫�����ϴ����ʴ�С���ļ�(������2MB)');</script>");
				return;
			}
			uploadItem = item;
			oldFileName = oldFileName.replaceAll("\\\\", "/");
			oldFileName = oldFileName.substring(oldFileName.lastIndexOf("/") + 1);
			ext = oldFileName.substring(oldFileName.lastIndexOf(".")+1);
			ext = ext.toLowerCase();
			if(PubFun.isAllowExt(ext,"Image")&&!ext.equalsIgnoreCase("zip")){
				long imageID = NoUtil.getMaxID("DocID");
				int random = NumberUtil.getRandomInt(10000);
				String srcFileName = imageID + "" + random + "." + ext;

				ZCImageSchema image = new ZCImageSchema();
				image.setID(imageID);
				image.setName(oldFileName.substring(0,oldFileName.lastIndexOf(".")));
				image.setOldName(oldFileName);
				image.setSiteID(SiteID);
				image.setCatalogID(Long.parseLong(CatalogID));
				image.setCatalogInnerCode(innerCode);
				image.setPath(Path);
				image.setFileName(oldFileName);
				image.setSrcFileName(srcFileName);
				image.setSuffix(ext);
				image.setWidth(0);
				image.setHeight(0);
				image.setCount(0);
				image.setOrderFlag(OrderUtil.getDefaultOrder());
				image.setFileSize(FileUtils.byteCountToDisplaySize(uploadItem.getSize()));
				image.setHitCount(0);
				image.setStickTime(0);
				image.setSystem("CMS");
				image.setAddTime(new Date());
				image.setAddUser(User.getUserName());
				image.setModifyTime(new Date());
				image.setModifyUser(User.getUserName());
				if(uploadItem !=null){
					uploadItem.write(new File(Config.getContextRealPath()+Config.getValue("UploadDir") + "/" + SiteAlias + "/"+Path+srcFileName));
					try {
						image = (ZCImageSchema)ImageUtilEx.afterUploadImage(image,Config.getContextRealPath()+Config.getValue("UploadDir") + "/" + SiteAlias + "/"+Path).get(0);
					} catch (Exception e) {
						System.out.println("------------- " + image.getName() + " ��ͼƬ�ѱ��𻵣�-------------");
					}catch (Throwable t) {
						System.out.println(t);
					}
					image.insert();
					out.println("<script>window.parent.afterUpload('/"+AliasName+Config.getValue("UploadDir") + "/" + SiteAlias + "/"+Path+srcFileName+"');</script>");
				}
			}else{
				out.println("<script>alert('ͼƬ��ʽ����');</script>");
				return;
			}
		}
	}
}

%>