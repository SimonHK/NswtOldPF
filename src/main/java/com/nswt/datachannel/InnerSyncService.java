package com.nswt.datachannel;

import java.io.File;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.nswt.cms.dataservice.ColumnUtil;
import com.nswt.cms.document.Article;
import com.nswt.cms.pub.CMSCache;
import com.nswt.cms.pub.CatalogUtil;
import com.nswt.cms.pub.SiteUtil;
import com.nswt.cms.site.Catalog;
import com.nswt.framework.Ajax;
import com.nswt.framework.Config;
import com.nswt.framework.Constant;
import com.nswt.framework.Framework;
import com.nswt.framework.RequestImpl;
import com.nswt.framework.data.BlockingTransaction;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.DataTableUtil;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.messages.LongTimeTask;
import com.nswt.framework.utility.CaseIgnoreMapx;
import com.nswt.framework.utility.FileUtil;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.NumberUtil;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.UserList;
import com.nswt.platform.pub.ImageUtilEx;
import com.nswt.platform.pub.NoUtil;
import com.nswt.platform.pub.OrderUtil;
import com.nswt.schema.ZCArticleSchema;
import com.nswt.schema.ZCArticleSet;
import com.nswt.schema.ZCCatalogConfigSchema;
import com.nswt.schema.ZCCatalogSchema;
import com.nswt.schema.ZCCatalogSet;
import com.nswt.schema.ZCImageRelaSchema;
import com.nswt.schema.ZCImageRelaSet;
import com.nswt.schema.ZCImageSchema;
import com.nswt.schema.ZCImageSet;
import com.nswt.schema.ZCInnerDeploySchema;
import com.nswt.schema.ZCInnerGatherSchema;
import com.nswt.schema.ZDColumnRelaSchema;
import com.nswt.schema.ZDColumnRelaSet;
import com.nswt.schema.ZDColumnSchema;
import com.nswt.schema.ZDColumnSet;
import com.nswt.schema.ZDColumnValueSchema;
import com.nswt.schema.ZDColumnValueSet;

/**
 * ��վȺ����ͬ������
 * 
 * ���� : 2010-5-20 <br>
 * ����: NSWT <br>
 * ���䣺nswt@nswt.com.cn <br>
 */
public class InnerSyncService extends Ajax {
	// ͼƬ�滻��������ʽ
	static Pattern imagePattern = Pattern.compile("<img\\s.*?nswtpimagerela=\"(.*?)\".*?>", Pattern.CASE_INSENSITIVE);
	
	/**
	 * ����վ��ӱ�վ��ɼ�����ʱ��վ���صĽ��
	 */
	public void sendData() {
		String CatalogID = $V("CatalogID");
		String Password = $V("Password");
		String LastTime = $V("LastTime");
		String SyncArticleModify = $V("SyncArticleModify");
		String SyncCatalogInsert = $V("SyncCatalogInsert");
		String SyncCatalogModify = $V("SyncCatalogModify");

		ZCCatalogConfigSchema config = CMSCache.getCatalogConfig(CatalogID);
		if ("Y".equals(config.getAllowInnerGather())) {
			if (StringUtil.isNotEmpty(config.getInnerGatherPassword())) {
				if (!config.getInnerGatherPassword().equals(Password)) {
					$S("Error", "�ɼ���Կ����ȷ!");
					return;
				}
			}
		} else {
			$S("Error", "Զ����Ŀ������ɼ�!");
		}
		String InnerCode = CatalogUtil.getInnerCode(CatalogID);

		prepareData(Response, InnerCode, new Date(Long.parseLong(LastTime)), "Y".equals(SyncArticleModify), "Y"
				.equals(SyncCatalogInsert), "Y".equals(SyncCatalogModify), false);
		$S("Success", "1");
	}

	/**
	 * ����վ��ӱ�վ��ȡͼƬ����
	 */
	public void sendImage() {
		String ID = $V("ID");
		String FileName = $V("FileName");
		ZCImageSchema image = new ZCImageSchema();
		image.setID(ID);
		if (!image.fill()) {
			$S("Error", "�����ͼƬID:" + ID);
			return;
		}
		if (!image.getFileName().equals(FileName)) {
			$S("Error", "�����ͼƬ�ļ���:" + FileName);
		}
		String path = Config.getContextRealPath() + Config.getValue("UploadDir") + "/"
				+ SiteUtil.getAlias(image.getSiteID()) + "/" + image.getPath() + "/" + image.getSrcFileName();
		path = path.replaceAll("\\/+", "/");
		byte[] bs = FileUtil.readByte(path);
		String data = StringUtil.base64Encode(bs);
		$S("Data", data);
		$S("Success", "1");
	}

	/**
	 * ����������վ�ַ�����������
	 */
	public void receiveData() {
		String CatalogID = $V("CatalogID");
		String SourceCatalogID = $V("SourceCatalogID");
		String Password = $V("Password");
		String ServerAddr = $V("ServerAddr");
		ZCCatalogConfigSchema config = CMSCache.getCatalogConfig(CatalogID);
		if ("Y".equals(config.getAllowInnerDeploy())) {
			if (StringUtil.isNotEmpty(config.getInnerDeployPassword())) {
				if (!config.getInnerDeployPassword().equals(Password)) {
					$S("Error", "�ַ���Կ����ȷ!");
					return;
				}
			}
		} else {
			$S("Error", "Զ����Ŀ������ַ�!");
		}
		dealData(Request, CatalogID, ServerAddr, SourceCatalogID, null);
		$S("Success", "1");
	}

	private static long ExecutingGatherID = 0;

	/**
	 * ���ñ�����ִ��һ����վȺ�ɼ�����
	 */
	public static void executeGather(ZCInnerGatherSchema gather, LongTimeTask task) {
		if (ExecutingGatherID == gather.getID()) {
			return;
		}
		ExecutingGatherID = gather.getID();
		if (task == null) {
			task = LongTimeTask.createEmptyInstance();
		}
		try {
			String data = gather.getTargetCatalog();
			DataTable dt = DataTableUtil.txtToDataTable(data, (String[]) null, "\t", "\n");
			for (int i = 0; i < dt.getRowCount(); i++) {
				String ServerAddr = dt.getString(i, "ServerAddr");
				String SiteID = dt.getString(i, "SiteID");
				String SiteName = dt.getString(i, "SiteName");
				String CatalogID = dt.getString(i, "CatalogID");
				String CatalogName = dt.getString(i, "CatalogName");
				String Password = dt.getString(i, "Password");
				String LastTime = "0";
				if (dt.getDataColumn("LastTime") != null) {
					LastTime = dt.getString(i, "LastTime");
				} else {
					dt.insertColumn("LastTime");
				}
				if (StringUtil.isEmpty(LastTime)) {
					LastTime = "0";
				}
				task.setCurrentInfo("���ڲɼ�վ�� [" + SiteName + "] �µ���Ŀ [" + CatalogName + "]");
				Mapx map = null;
				Date NextTime = null;
				if (ServerAddr.equalsIgnoreCase("localhost")) {
					map = new Mapx();
					NextTime = prepareData(map, CatalogUtil.getInnerCode(CatalogID),
							new Date(Long.parseLong(LastTime)), "Y".equals(gather.getSyncArticleModify()), "Y"
									.equals(gather.getSyncCatalogInsert()), "Y".equals(gather.getSyncCatalogModify()),
							false);
					map.put("Success", "1");
				} else {
					RequestImpl Request = new RequestImpl();
					Request.put("SiteID", SiteID);
					Request.put("CatalogID", CatalogID);
					Request.put("Password", Password);
					Request.put("SyncArticleModify", gather.getSyncArticleModify());
					Request.put("SyncCatalogInsert", gather.getSyncCatalogInsert());
					Request.put("SyncCatalogModify", gather.getSyncCatalogModify());
					Request.put("LastTime", LastTime);
					map = Framework.callRemoteMethod(ServerAddr, "com.nswt.datachannel.InnerSyncService.sendData",
							Request);
					NextTime = new Date(Long.parseLong(LastTime));
				}
				if (map == null) {// ��ȡʧ��
					task.setCurrentInfo("��վȺ�ɼ�����ִ��ʧ�ܣ��޷����ʷ�����" + ServerAddr);
					task.setPercent(100);
					return;
				}
				if (!"1".equals(map.get("Success"))) {// ��ȡʧ��
					task.setCurrentInfo("��վȺ�ɼ�����ִ��ʧ�ܣ�" + map.getString("Error"));
					task.setPercent(100);
					return;
				}
				if (task != null) {
					task.setCurrentInfo("���ڱ���վ�� [" + SiteName + "] �µ���Ŀ [" + CatalogName + "] �µ�����");
				}
				dealData(map, CatalogUtil.getIDByInnerCode(gather.getCatalogInnerCode()), ServerAddr, CatalogID, null);
				dt.set(i, "LastTime", NextTime.getTime());// ����Ҫ����
			}
			gather.setTargetCatalog(dt.toString());
			gather.update();
		} finally {
			ExecutingGatherID = 0;
		}
	}

	private static long ExecutingDeployID = 0;

	/**
	 * ���ñ�����ִ��һ����վȺ�ɼ�����
	 */
	public synchronized static void executeDeploy(ZCInnerDeploySchema deploy, LongTimeTask task) {
		if (ExecutingDeployID == deploy.getID()) {
			return;
		}
		ExecutingDeployID = deploy.getID();
		try {
			if (task == null) {
				task = LongTimeTask.createEmptyInstance();
			}
			String SyncArticleModify = deploy.getSyncArticleModify();
			String SyncCatalogInsert = deploy.getSyncCatalogInsert();
			String SyncCatalogModify = deploy.getSyncCatalogModify();

			String InnerCode = deploy.getCatalogInnerCode();
			String ServiceContext = Config.getValue("ServicesContext");
			if (ServiceContext.endsWith("/")) {
				ServiceContext = ServiceContext.substring(0, ServiceContext.length() - 1);
			}
			ServiceContext = ServiceContext.substring(0, ServiceContext.lastIndexOf("/") + 1);
			String SourceCatalogID = CatalogUtil.getIDByInnerCode(deploy.getCatalogInnerCode());
			String data = deploy.getTargetCatalog();
			DataTable dt = DataTableUtil.txtToDataTable(data, (String[]) null, "\t", "\n");
			for (int i = 0; i < dt.getRowCount(); i++) {
				String ServerAddr = dt.getString(i, "ServerAddr");
				String SiteID = dt.getString(i, "SiteID");
				String SiteName = dt.getString(i, "SiteName");
				String CatalogID = dt.getString(i, "CatalogID");
				String CatalogName = dt.getString(i, "CatalogName");
				String Password = dt.getString(i, "Password");
				task.setCurrentInfo("���ڷַ����ݵ�վ�� [" + SiteName + "] �µ���Ŀ [" + CatalogName + "]");

				RequestImpl Request = new RequestImpl();
				Request.put("SiteID", SiteID);
				Request.put("CatalogID", CatalogID);
				Request.put("SourceCatalogID", SourceCatalogID);
				Request.put("Password", Password);
				Request.put("ServerAddr", ServiceContext);// ������������ServerAddr�Ž�ȥ
				String LastTime = "0";
				if (dt.getDataColumn("LastTime") != null) {
					LastTime = dt.getString(i, "LastTime");
				} else {
					dt.insertColumn("LastTime");
				}
				if (StringUtil.isEmpty(LastTime) || LastTime.equalsIgnoreCase(Constant.Null)) {
					LastTime = "0";
				}
				Request.put("LastTime", LastTime);

				Date NextTime = prepareData(Request, InnerCode, new Date(Long.parseLong(LastTime)), "Y"
						.equals(SyncArticleModify), "Y".equals(SyncCatalogInsert), "Y".equals(SyncCatalogModify),
						deploy.getDeployType().equals("2"));

				// ��Ҫ���˵�һЩ����
				String md5 = StringUtil.md5Hex(ServerAddr + "," + SiteID + "," + CatalogID);
				DataTable articles = Request.getDataTable("ZCArticle-Add");
				if (articles != null) {
					for (int j = articles.getRowCount() - 1; j >= 0; j--) {
						if (deploy.getDeployType().equals("2")
								&& articles.getString(j, "ClusterTarget").indexOf(md5) < 0) {
							articles.deleteRow(j);
						}
					}
				}
				articles = Request.getDataTable("ZCArticle-Modify");
				if (articles != null) {
					for (int j = articles.getRowCount() - 1; j >= 0; j--) {
						if (deploy.getDeployType().equals("2")
								&& articles.getString(j, "ClusterTarget").indexOf(md5) < 0) {
							articles.deleteRow(j);
						}
					}
				}

				Mapx map = null;
				if (ServerAddr.equalsIgnoreCase("localhost")) {
					if (deploy.getAddUser().equals(UserList.ADMINISTRATOR)) {
						map = new Mapx();
						// ����ǹ���Ա��ӵ������������������Ϊ׼
						dealData(Request, CatalogID, ServerAddr, SourceCatalogID, deploy);
						map.put("Success", "1");
					} else {// ������ǹ���Ա��ӵ�����������Ŀ����Ϊ׼
						InnerSyncService ss = new InnerSyncService();
						ss.setRequest(Request);
						ss.receiveData();
						map = ss.getResponse();
					}
				} else {
					map = Framework.callRemoteMethod(ServerAddr, "com.nswt.datachannel.InnerSyncService.receiveData",
							Request);
				}
				if (map == null) {// ��ȡʧ��
					task.setCurrentInfo("��վȺ�ַ�����ִ��ʧ�ܣ��޷�����Զ�̷�����" + ServerAddr);
					task.setPercent(100);
					return;
				}
				if (!"1".equals(map.get("Success"))) {// �ַ�ʧ��
					task.setCurrentInfo("��վȺ�ַ�����ִ��ʧ�ܣ�" + map.getString("Error"));
					task.setPercent(100);
					return;
				}
				dt.set(i, "LastTime", NextTime.getTime());// ����Ҫ����
				deploy.setTargetCatalog(dt.toString());
				deploy.update();
			}
		} finally {
			ExecutingDeployID = 0;
		}
	}

	/**
	 * ׼������
	 */
	public synchronized static Date prepareData(Mapx map, String InnerCode, Date LastTime, boolean isSyncArticleModify,
			boolean isSyncCatalogInsert, boolean isSyncCatalogModify, boolean isManualDeploy) {
		//ԭʼվ���url
		String siteurl = SiteUtil.getURL(CatalogUtil.getSiteIDByInnerCode(InnerCode))+"/";
		if(!siteurl.endsWith("/")){
			siteurl += "/";
		}
		String uploadPath = Config.getContextPath() + Config.getValue("UploadDir") + "/"+ SiteUtil.getAlias(CatalogUtil.getSiteIDByInnerCode(InnerCode)) + "/";
		uploadPath = uploadPath.replaceAll("/+", "/");
		
		Date NextTime = new Date(LastTime.getTime());
		// �����½�������Ϣ
		QueryBuilder qb = new QueryBuilder(
				"select * from ZCArticle where (Status=? or Status=?) and CatalogInnerCode like ? and AddTime>?");
		qb.add(Article.STATUS_PUBLISHED);
		qb.add(Article.STATUS_TOPUBLISH);
		qb.add(InnerCode + "%");
		qb.add(LastTime);
		if (isManualDeploy) {
			qb.append(" and ClusterTarget is not null and ClusterTarget<>''");
		}
		qb.append(" order by id");
		DataTable dt = qb.executePagedDataTable(50, 0);// ���ֻȡǰ50�����������̫�࣬��Ҫ���ͬ��
		map.put("ZCArticle-Add", dt);
		for (int i = 0; i < dt.getRowCount(); i++) {// �����´ε�lastTime
			if (dt.getDate(i, "AddTime").getTime() > NextTime.getTime()) {
				NextTime = dt.getDate(i, "AddTime");
			}
			String content = dt.getString(i, "Content");
			content = content.replaceAll(uploadPath+"upload/Attach", siteurl+"upload/Attach");
			content = content.replaceAll(uploadPath+"upload/Audio", siteurl+"upload/Audio");
			content = content.replaceAll("../upload/Video", siteurl+"upload/Video");
			content = content.replaceAll("nswtpvideorela=\".*?\"", "");
			content = content.replaceAll("nswtpattachrela=\".*?\"", "");
			dt.set(i, "Content", content);
		}
		String ids = StringUtil.join(dt.getColumnValues("ID"));

		// ���������޸���Ϣ
		if (isSyncArticleModify) {
			qb = new QueryBuilder(
					"select * from ZCArticle where (Status=? or Status=?) and CatalogInnerCode like ? and ModifyTime>? and AddTime<?");
			qb.add(Article.STATUS_PUBLISHED);
			qb.add(Article.STATUS_TOPUBLISH);
			qb.add(InnerCode + "%");
			qb.add(LastTime);
			qb.add(LastTime);
			if (isManualDeploy) {
				qb.append(" and ClusterTarget is not null and ClusterTarget<>''");
			}
			qb.append(" order by id");
			dt = qb.executePagedDataTable(50, 0);// ��Զ��ֻȡǰ50�����������̫�࣬����Ҫͬ�����
			map.put("ZCArticle-Modify", dt);
			for (int i = 0; i < dt.getRowCount(); i++) {// �����´ε�lastTime
				if (dt.getDate(i, "ModifyTime").getTime() > NextTime.getTime()) {
					NextTime = dt.getDate(i, "ModifyTime");
				}
				String content = dt.getString(i, "Content");
				content = content.replaceAll(uploadPath+"upload/Attach", siteurl+"upload/Attach");
				content = content.replaceAll(uploadPath+"upload/Audio", siteurl+"upload/Audio");
				content = content.replaceAll("../upload/Video", siteurl+"upload/Video");
				content = content.replaceAll("nswtpvideorela=\".*?\"", "");
				content = content.replaceAll("nswtpattachrela=\".*?\"", "");
				dt.set(i, "Content", content);
			}
			if (StringUtil.isNotEmpty(ids)) {
				ids = ids + "," + StringUtil.join(dt.getColumnValues("ID"));
			} else {
				ids = StringUtil.join(dt.getColumnValues("ID"));
			}
			if (ids.endsWith(",")) {
				ids = ids.substring(0, ids.length() - 1);
			}

			// ��������ɾ����Ϣ
			qb = new QueryBuilder(
					"select distinct ID from BZCArticle where CatalogInnerCode like ? and not exists (select 1 from ZCArticle where ZCArticle.ID=BZCArticle.ID)");
			qb.add(InnerCode + "%");
			dt = qb.executeDataTable();
			map.put("DeletedArticles", StringUtil.join(dt.getColumnValues(0)));
		}

		if (StringUtil.isNotEmpty(ids)) {
			String relaIds = ids.replaceAll(",", "','");
			relaIds = "'" + relaIds + "'";
			// ���������Զ����ֶ�ֵ
			dt = new QueryBuilder("select * from ZDColumnValue where RelaID in (" + relaIds + ") and RelaType=?",
					ColumnUtil.RELA_TYPE_DOCID).executeDataTable();
			map.put("ZDColumnValue-Article", dt);

			// ��������ͼƬ����
			dt = new QueryBuilder("select * from ZCImageRela where RelaID in (" + ids + ") and RelaType=?",
					Article.RELA_IMAGE).executeDataTable();
			map.put("ZCImageRela", dt);

			// ����������ص�ͼƬ
			ids = StringUtil.join(dt.getColumnValues("ID"));
			if (StringUtil.isNotEmpty(ids)) {
				qb = new QueryBuilder("select * from ZCImage where Status=? and id in (" + ids + ") order by id");
				qb.add(Article.STATUS_PUBLISHED);
				dt = qb.executeDataTable();
				map.put("ZCImage", dt);
			}
		}

		if (isSyncCatalogInsert) {
			// ������Ŀ��Ϣ
			qb = new QueryBuilder("select * from ZCCatalog where InnerCode like ? and AddTime>? order by id");
			qb.add(InnerCode + "%");
			qb.add(LastTime);
			dt = qb.executePagedDataTable(50, 0);// ��Զ��ֻȡǰ50�����������̫�࣬����Ҫͬ�����
			map.put("ZCCatalog-Add", dt);
			ids = StringUtil.join(dt.getColumnValues("ID"));

			if (isSyncCatalogModify) {
				// ֻȡ��ָ��ʱ���֮ǰ�������޸Ĺ���
				qb = new QueryBuilder(
						"select * from ZCCatalog where InnerCode like ? and AddTime<? and ModifyTime>? order by id");
				qb.add(InnerCode + "%");
				qb.add(LastTime);
				qb.add(LastTime);
				dt = qb.executePagedDataTable(50, 0);// ��Զ��ֻȡǰ50�����������̫�࣬����Ҫͬ�����
				map.put("ZCCatalog-Modify", dt);
				if (StringUtil.isNotEmpty(ids)) {
					ids = ids + "," + StringUtil.join(dt.getColumnValues("ID"));
				} else {
					ids = StringUtil.join(dt.getColumnValues("ID"));
				}
				if (ids.endsWith(",")) {
					ids = ids.substring(0, ids.length() - 1);
				}
			}
			if (StringUtil.isNotEmpty(ids)) {
				String types = ColumnUtil.RELA_TYPE_CATALOG_COLUMN + "," + ColumnUtil.RELA_TYPE_CATALOG_EXTEND;
				// ������Ŀ��չ�ֶ�
				dt = new QueryBuilder("select * from ZDColumnRela where RelaType in (" + types + ") and RelaID in("
						+ ids + ")").executeDataTable();
				map.put("ZDColumnRela", dt);

				String columnIDs = StringUtil.join(dt.getColumnValues("ColumnID"));
				if (StringUtil.isNotEmpty(columnIDs)) {
					dt = new QueryBuilder("select * from ZDColumn where id in (" + columnIDs + ")").executeDataTable();
					map.put("ZDColumn", dt);
				}

				// ������Ŀ���������zdcolumnvalue����,��Ŀ����չ�ֶ�
				dt = new QueryBuilder("select * from ZDColumnValue where RelaType="
						+ ColumnUtil.RELA_TYPE_CATALOG_EXTEND + " and RelaID in(" + ids + ")").executeDataTable();
				map.put("ZDColumnValue-Catalog", dt);
			}
		}
		map.put("Success", "1");
		return NextTime;
	}

	/**
	 * ������յ������ݣ��任ID�ţ������뵽���ݿ�.<br>
	 * �����ͼƬ��Ҫ���£���Ҫ����Զ�̷���sendImage���base64���ͼƬ����
	 */
	public static void dealData(Mapx map, String catalogID, String ServerAddr, String sourceCatalogID,
			ZCInnerDeploySchema deploy) {
		long AfterSyncStatus = 0;
		long AfterModifyStatus = Article.STATUS_EDITING;
		boolean SyncCatalogInsert = false;
		boolean SyncCatalogModify = false;
		boolean SyncArticleModify = false;

		ZCCatalogConfigSchema config = CMSCache.getCatalogConfig(catalogID);
		if (deploy != null) {// ֻ�й���Ա���ӵ�����Żᴫdeploy�������Թ���Ա��ӵı�վ����������������Ϊ׼������������Ŀ���������Ϊ׼
			AfterSyncStatus = deploy.getAfterSyncStatus();
			AfterModifyStatus = deploy.getAfterModifyStatus();
			SyncCatalogInsert = "Y".equals(deploy.getSyncCatalogInsert());
			SyncCatalogModify = "Y".equals(deploy.getSyncCatalogModify());
			SyncArticleModify = "Y".equals(deploy.getSyncArticleModify());
		} else {
			AfterSyncStatus = config.getAfterSyncStatus();
			AfterModifyStatus = config.getAfterModifyStatus();
			SyncCatalogInsert = "Y".equals(config.getSyncCatalogInsert());
			SyncCatalogModify = "Y".equals(config.getSyncCatalogModify());
			SyncArticleModify = "Y".equals(config.getSyncArticleModify());
		}
		String ServiceContext = Config.getValue("ServicesContext");
		if (ServiceContext.endsWith("/")) {
			ServiceContext = ServiceContext.substring(0, ServiceContext.length() - 1);
		}
		ServiceContext = ServiceContext.substring(0, ServiceContext.lastIndexOf("/") + 1);
		if (ServerAddr.equalsIgnoreCase("localhost")) {
			ServerAddr = ServiceContext;
		}
		boolean isSameServer = false;
		if (ServerAddr.equalsIgnoreCase(ServiceContext)) {
			isSameServer = true;// ͬһ�������µ�ͬһվ�㲻��Ҫ�ٸ���ͼƬ
		}

		ZCCatalogSchema catalog = CMSCache.getCatalog(catalogID);
		if (catalog == null) {
			return;
		}
		ZCCatalogSet catalogSet = new ZCCatalogSchema().query(new QueryBuilder("where InnerCode like ?", catalog
				.getInnerCode()
				+ "%"));

		DataTable columns = (DataTable) map.get("ZDColumn");// ��Ŀ�µ��ĵ��Զ����ֶ�
		DataTable articleColumnValues = (DataTable) map.get("ZDColumnValue-Article");// ��Ŀ�µ��ĵ��Զ����ֶ�
		DataTable catalogColumnValues = (DataTable) map.get("ZDColumnValue-Catalog");// ��Ŀ�µĵ���չ����ֵ
		DataTable columnRela = (DataTable) map.get("ZDColumnRela");// ��Ŀ�µ��ĵ��Զ����ֶκ���չ����

		Mapx idmap = new Mapx();// ԭID/��ID�滻��

		// ������ĿID
		for (int i = 0; i < catalogSet.size(); i++) {
			String ClusterSourceID = catalogSet.get(i).getClusterSourceID();
			if (StringUtil.isNotEmpty(ClusterSourceID)) {
				String[] arr = ClusterSourceID.split(",");
				String OldCatalogID = arr[arr.length - 1];
				idmap.put("Catalog." + OldCatalogID, catalogSet.get(i).getID());
			}
		}
		idmap.put("Catalog." + sourceCatalogID, catalogID);

		Transaction tran = new BlockingTransaction();

		// �����޸ĵ���Ŀ�������ȴ����޸ĵ���Ŀ�������¼ӵ���Ŀ���ܻ��Ҳ���������Ŀ
		if (SyncCatalogModify) {
			DataTable dt = (DataTable) map.get("ZCCatalog-Modify");
			if (dt != null && dt.getRowCount() > 0) {// �п���Զ��վ�㲻�ַ���Ŀ�޸�
				dt.sort("ModifyTime");
				ZCCatalogSet set = new ZCCatalogSet();
				for (int i = 0; i < dt.getRowCount(); i++) {
					ZCCatalogSchema c2 = null;
					String ClusterSourceID = ServerAddr + "," + dt.getString(i, "SiteID") + "," + dt.getString(i, "ID");
					int index = 0;
					for (int j = 0; j < catalogSet.size(); j++) {
						if (StringUtil.isNotEmpty(catalogSet.get(j).getClusterSourceID())
								&& catalogSet.get(j).getClusterSourceID().equals(ClusterSourceID)) {
							c2 = catalogSet.get(j);
							index = j;
							break;
						}
					}
					if (c2 == null) {
						continue;
					}
					ZCCatalogSchema c = new ZCCatalogSchema();
					c.setValue(dt.getDataRow(i));
					c.setID(c2.getID());
					c.setParentID(idmap.getString("Catalog." + c.getParentID()));
					c.setSiteID(c2.getSiteID());
					String parentInnerCode = c2.getInnerCode();
					for (int k = 0; k < catalogSet.size(); k++) {
						if (catalogSet.get(k).getID() == c.getParentID()) {
							parentInnerCode = catalogSet.get(k).getInnerCode();
							break;
						}
					}
					c.setInnerCode(CatalogUtil.createCatalogInnerCode(parentInnerCode));
					String name = Catalog.checkAliasExists(c.getAlias(), catalog.getSiteID(), c.getParentID());
					if (StringUtil.isNotEmpty(name)) {
						LogUtil.warn("��վȺͬ����Ŀ���ʱ������Ŀ����[" + c.getAlias() + "]������Ŀ[" + name + "]ռ��!");
						continue;
					}
					c.setClusterSourceID(ClusterSourceID);// ��ԴID
					c.setOrderFlag(c2.getOrderFlag());

					set.add(c);
					catalogSet.set(index, c);// ����
				}
				tran.add(set, Transaction.UPDATE);
			}
		}

		// �����µ���Ŀ
		if (SyncCatalogInsert) {
			DataTable dt = (DataTable) map.get("ZCCatalog-Add");
			if (dt != null && dt.getRowCount() > 0) {// �п���Զ��վ�㲻�ַ���Ŀ���
				dt.sort("AddTime");
				ZCCatalogSet set = new ZCCatalogSet();
				for (int i = 0; i < dt.getRowCount(); i++) {
					if (sourceCatalogID.equals(dt.getString(i, "ID"))) {// Դ��Ŀֻ�޸ģ�������
						continue;
					}
					ZCCatalogSchema c = new ZCCatalogSchema();
					c.setValue(dt.getDataRow(i));
					c.setSiteID(catalog.getSiteID());
					c.setID(NoUtil.getMaxID("CatalogID"));
					c.setParentID(idmap.getString("Catalog." + c.getParentID()));
					String parentInnerCode = catalog.getInnerCode();
					String url = catalog.getURL();
					for (int k = 0; k < catalogSet.size(); k++) {
						if (catalogSet.get(k).getID() == c.getParentID()) {
							parentInnerCode = catalogSet.get(k).getInnerCode();
							url = catalogSet.get(k).getURL();
							break;
						}
					}
					if (!url.endsWith("/")) {
						url += "/";
					}
					url = url + c.getAlias() + "/";
					c.setURL(url);
					c.setInnerCode(CatalogUtil.createCatalogInnerCode(parentInnerCode));
					String name = Catalog.checkAliasExists(c.getAlias(), catalog.getSiteID(), c.getParentID());
					if (StringUtil.isNotEmpty(name)) {
						LogUtil.warn("��վȺͬ����Ŀ���ʱ������Ŀ����" + c.getAlias() + "������Ŀ" + name + "ռ��!");
						continue;
					}
					c.setClusterSourceID(ServerAddr + "," + dt.getString(i, "SiteID") + "," + dt.getString(i, "ID"));// ��ԴID
					set.add(c);
					catalogSet.add(c);// ���룬�Ա���������ʱ��ѯ
					idmap.put("Catalog." + dt.getString(i, "ID"), c.getID());
				}
				tran.add(set, Transaction.INSERT);
			}
		}

		// �����Զ����ֶκ���չ����
		if (columnRela != null && columnRela.getRowCount() > 0) {
			ZDColumnRelaSet crSet = new ZDColumnRelaSet();
			ZDColumnSet cSet = new ZDColumnSet();
			ZDColumnValueSet vSetUpdate = new ZDColumnValueSet();
			ZDColumnValueSet vSetInsert = new ZDColumnValueSet();
			for (int i = 0; i < columnRela.getRowCount(); i++) {
				ZDColumnRelaSchema cr = new ZDColumnRelaSchema();
				cr.setValue(columnRela.getDataRow(i));
				cr.setRelaID(idmap.getString("Catalog." + cr.getRelaID()));
				cr.setID(NoUtil.getMaxID("ColumnRelaID"));

				ZDColumnRelaSchema cr2 = new ZDColumnRelaSchema();
				cr2.setColumnCode(cr.getColumnCode());
				cr2.setRelaID(cr.getRelaID());
				cr2.setRelaType(cr2.getRelaType());
				ZDColumnRelaSet set = cr2.query();
				if (set.size() == 0) {
					crSet.add(cr);
					if (columns != null && columns.getRowCount() > 0) {
						for (int j = 0; j < columns.getRowCount(); j++) {
							if (columns.getLong(j, "ID") == cr.getColumnID()) {
								ZDColumnSchema column = new ZDColumnSchema();
								column.setValue(columns.getDataRow(j));
								column.setID(NoUtil.getMaxID("ColumnID"));
								column.setSiteID(config.getSiteID());
								cSet.add(column);
								idmap.put("Column." + columns.getString(j, "ID"), column.getID());
								cr.setColumnID(column.getID());
							}
						}
					}
				} else {// �Ѿ��еľͲ�����
					idmap.put("Column." + columnRela.getString(i, "ColumnID"), set.get(0).getColumnID());
				}
			}
			if (catalogColumnValues != null && catalogColumnValues.getRowCount() > 0) {
				for (int j = 0; j < catalogColumnValues.getRowCount(); j++) {
					ZDColumnValueSchema v = new ZDColumnValueSchema();
					v.setColumnID(idmap.getLong("Column." + catalogColumnValues.getString(j, "ColumnID")));
					v.setRelaID(idmap.getString("Catalog." + catalogColumnValues.getString(j, "RelaID")));
					v.setRelaType(catalogColumnValues.getString(j, "RelaType"));
					ZDColumnValueSet set2 = v.query();
					if (set2.size() > 0) {
						v = set2.get(0);
						v.setTextValue(catalogColumnValues.getString(j, "TextValue"));
						vSetUpdate.add(v);
					} else {
						v.setValue(catalogColumnValues.getDataRow(j));
						v.setID(NoUtil.getMaxID("ColumnValueID"));
						v.setColumnID(idmap.getLong("Column." + catalogColumnValues.getString(j, "ColumnID")));
						v.setRelaID(idmap.getString("Catalog." + catalogColumnValues.getString(j, "RelaID")));
						v.setRelaType(catalogColumnValues.getString(j, "RelaType"));
						vSetInsert.add(v);
					}
				}
			}
			tran.add(crSet, Transaction.INSERT);
			tran.add(cSet, Transaction.INSERT);
			tran.add(vSetUpdate, Transaction.UPDATE);
			tran.add(vSetInsert, Transaction.INSERT);
		}

		// ȡ��Ĭ�ϵ�ͼƬ���λ��
		String autoSaveLib = Config.getValue("AutoSaveImageLib");
		if (StringUtil.isEmpty(autoSaveLib)) {
			autoSaveLib = "Ĭ��ͼƬ";
		}
		String imageCatalogID = new QueryBuilder("select ID from ZCCatalog where type=" + Catalog.IMAGELIB
				+ " and Name =?  and siteid=?",autoSaveLib, catalog.getSiteID()).executeString();

		if (StringUtil.isEmpty(imageCatalogID)) {
			imageCatalogID = new QueryBuilder("select ID from ZCCatalog where type=" + Catalog.IMAGELIB
					+ " and siteid=?", catalog.getSiteID()).executeString();
		}
		ZCCatalogSchema imageCatalog = CatalogUtil.getSchema(imageCatalogID);

		DataTable imageRelas = (DataTable) map.get("ZCImageRela");
		DataTable images = (DataTable) map.get("ZCImage");
		ZCImageRelaSet rSet = new ZCImageRelaSet();
		ZCImageSet iSet = new ZCImageSet();

		// ���������Ŀ�µ��Զ����ֶ�

		ZCArticleSet aSet = new ZCArticleSet();
		ZDColumnValueSet vSet = new ZDColumnValueSet();
		ZDColumnValueSet vSetUpdate = new ZDColumnValueSet();
		if (SyncArticleModify) {
			// ɾ������
			String DeletedArticles = map.getString("DeletedArticles");
			if (!StringUtil.checkID(DeletedArticles)) {
				LogUtil.warn("InnerSyncService.dealData()���ܵ�SQLע�빥��:" + DeletedArticles);
				return;
			} else {
				if (StringUtil.isNotEmpty(DeletedArticles)) {
					ZCArticleSet set = new ZCArticleSchema().query(new QueryBuilder("where ID in (" + DeletedArticles
							+ ")"));
					tran.add(set, Transaction.DELETE_AND_BACKUP);
				}
			}
			// ���������޸�
			DataTable dt = (DataTable) map.get("ZCArticle-Modify");
			if (dt != null && dt.getRowCount() > 0) {
				for (int i = 0; i < dt.getRowCount(); i++) {
					ZCArticleSchema article = new ZCArticleSchema();
					String sourceID = ServerAddr + "," + dt.getString(i, "SiteID") + "," + dt.getString(i, "CatalogID");

					// ����Ƿ��Ѿ��д�������
					article.setCatalogID(config.getCatalogID());
					article.setSiteID(config.getSiteID());
					article.setClusterSource(sourceID + "," + dt.getString(i, "ID"));
					ZCArticleSet set = article.query();
					if (set.size() == 0) {
						continue;// û�У����Ǹ��£�������
					}
					ZCArticleSchema article2 = set.get(0);
					article.setValue(dt.getDataRow(i));
					article.setID(article2.getID());
					article.setCatalogID(article2.getCatalogID());
					article.setCatalogInnerCode(article2.getCatalogInnerCode());
					article.setSiteID(article2.getSiteID());
					article.setOrderFlag(article2.getOrderFlag());
					article.setStatus(AfterModifyStatus);
					article.setClusterSource(sourceID + "," + dt.getString(i, "ID"));
					article.setBranchInnerCode("0001");
					article.setReferTarget("");
					article.setClusterTarget("");
					aSet.add(article);

					// ��ɾ��ͼƬ����
					QueryBuilder qb = new QueryBuilder("delete from ZCImageRela where RelaType=? and RelaID=?");
					qb.add(Article.RELA_IMAGE);
					qb.add(article.getID());
					tran.add(qb);

					// �����Զ����ֶ�ֵ
					if (articleColumnValues != null) {
						for (int j = 0; j < articleColumnValues.getRowCount(); j++) {
							if (articleColumnValues.getString(j, "RelaType").equals(ColumnUtil.RELA_TYPE_DOCID)
									&& articleColumnValues.getString(j, "RelaID").equals(dt.getString(i, "ID"))) {
								ZDColumnValueSchema v = new ZDColumnValueSchema();
								v.setColumnID(idmap.getLong("Column." + articleColumnValues.getString(j, "ColumnID")));
								v.setRelaID("" + article.getID());
								v.setRelaType(articleColumnValues.getString(j, "RelaType"));
								ZDColumnValueSet set2 = v.query();
								if (set2.size() > 0) {
									v = set2.get(0);
									v.setTextValue(articleColumnValues.getString(j, "TextValue"));
									vSetUpdate.add(v);
								} else {
									v.setValue(articleColumnValues.getDataRow(j));
									v.setID(NoUtil.getMaxID("ColumnValueID"));
									v.setColumnID(idmap.getLong("Column."
											+ articleColumnValues.getString(j, "ColumnID")));
									v.setRelaID("" + article.getID());
									v.setRelaType(articleColumnValues.getString(j, "RelaType"));
									vSet.add(v);
								}
							}
						}
					}

					if (isSameServer && dt.getLong(i, "SiteID") == catalog.getSiteID()) {// ͬһ�������µ�ͬһվ�㲻����ͼƬ
						continue;
					}
					if (imageRelas == null || images == null) {
						continue;
					}

					// ����ͼƬ
					for (int j = 0; j < imageRelas.getRowCount(); j++) {
						if (imageRelas.getString(j, "RelaType").equals(Article.RELA_IMAGE)
								&& imageRelas.getString(j, "RelaID").equals(dt.getString(i, "ID"))) {
							ZCImageRelaSchema ir = new ZCImageRelaSchema();
							ir.setValue(imageRelas.getDataRow(j));
							for (int k = 0; k < images.getRowCount(); k++) {
								if (images.getLong(k, "ID") == ir.getID()) {
									ZCImageSchema image = new ZCImageSchema();
									image.setSiteID(catalog.getSiteID());
									image.setSourceURL(ServerAddr + "?ImageID=" + ir.getID());
									ZCImageSet set2 = image.query();
									if (set2.size() > 0) {
										image = set2.get(0);
										idmap.put("Image." + ir.getID(), image);// �����¾�ӳ��
										continue;
									}
									image.setValue(images.getDataRow(k));
									image.setID(NoUtil.getMaxID("DocID"));
									image.setSiteID(catalog.getSiteID());
									image.setCatalogID(imageCatalog.getID());
									image.setCatalogInnerCode(imageCatalog.getInnerCode());
									image.setStatus(Article.STATUS_TOPUBLISH);
									image.setPath("upload/Image/" + CatalogUtil.getPath(imageCatalog.getID()));
									image.setOrderFlag(OrderUtil.getDefaultOrder());
									image.setSourceURL(ServerAddr + "?ImageID=" + ir.getID());

									int random = NumberUtil.getRandomInt(10000);
									String ext = image.getFileName()
											.substring(image.getFileName().lastIndexOf(".") + 1);
									String newFileName = image.getID() + "" + random + "." + ext;

									RequestImpl request = new RequestImpl();
									request.put("ID", ir.getID());// �˴��Ǿ�ID
									request.put("FileName", image.getFileName());
									Mapx data = null;
									if (isSameServer) {
										InnerSyncService service = new InnerSyncService();
										service.setRequest(request);
										service.sendImage();
										data = service.getResponse();
									} else {
										data = Framework.callRemoteMethod(ServerAddr,
												"com.nswt.datachannel.InnerSyncService.sendImage", request);
									}
									if ("1".equals(data.getString("Success"))) {
										String str = data.getString("Data");
										byte[] bs = StringUtil.base64Decode(str);
										String path = SiteUtil.getAbsolutePath(catalog.getSiteID()) + image.getPath();
										new File(path).mkdirs();
										image.setFileName(newFileName);
										image.setSrcFileName(newFileName);
										newFileName = path + newFileName;
										FileUtil.writeByte(newFileName, bs);
										try {
											ImageUtilEx.afterUploadImage(image, path);
											idmap.put("Image." + ir.getID(), image);// �����¾�ӳ��
										} catch (Throwable e) {
											e.printStackTrace();
										}
									}

									ir.setID(image.getID());
									iSet.add(image);
									break;
								}
							}
							ir.setRelaID(article.getID());
							rSet.add(ir);
						}
					}

					// �滻����HTML�е�ͼƬ����
					String html = article.getContent();
					html = dealImageTag(html, idmap, catalog);
					article.setContent(html);
				}
				tran.add(aSet, Transaction.UPDATE);
				tran.add(vSetUpdate, Transaction.UPDATE);// vSet���������ټ�
				tran.add(vSet, Transaction.INSERT);// vSet���������ټ�
			}
		}

		// ���������
		DataTable dt = (DataTable) map.get("ZCArticle-Add");
		dt.sort("OrderFlag", "ASC");
		aSet = new ZCArticleSet();
		vSet = new ZDColumnValueSet();
		for (int i = 0; i < dt.getRowCount(); i++) {
			ZCArticleSchema article = new ZCArticleSchema();
			article.setValue(dt.getDataRow(i));
			String sourceID = ServerAddr + "," + article.getSiteID() + "," + article.getCatalogID();
			article.setID(NoUtil.getMaxID("DocID"));
			article.setSiteID(catalog.getSiteID());

			// ������Ŀ
			boolean flag = false;
			for (int j = 0; j < catalogSet.size(); j++) {
				if (sourceID.equals(catalogSet.get(j).getClusterSourceID())) {
					article.setCatalogID(catalogSet.get(j).getID());
					article.setCatalogInnerCode(catalogSet.get(j).getInnerCode());
					flag = true;
					break;
				}
			}
			if (!flag) {// ˵����������Ŀ�µ����£�ֱ�ӹ���Ŀ����Ŀ��
				article.setCatalogID(catalog.getID());
				article.setCatalogInnerCode(catalog.getInnerCode());
			}

			// ����Ƿ��Ѿ��д�������
			ZCArticleSchema article2 = new ZCArticleSchema();
			article2.setCatalogID(article.getCatalogID());
			article2.setSiteID(article.getSiteID());
			article2.setClusterSource(sourceID + "," + dt.getString(i, "ID"));
			if (article2.query().size() > 0) {
				continue;
			}
			article.setClusterSource(sourceID + "," + dt.getString(i, "ID"));

			article.setStatus(AfterSyncStatus);
			article.setOrderFlag(OrderUtil.getDefaultOrder());
			article.setBranchInnerCode("0001");
			aSet.add(article);

			// �����Զ����ֶ�ֵ
			if (articleColumnValues != null) {
				for (int j = 0; j < articleColumnValues.getRowCount(); j++) {
					if (articleColumnValues.getString(j, "RelaType").equals(ColumnUtil.RELA_TYPE_DOCID)
							&& articleColumnValues.getString(j, "RelaID").equals(dt.getString(i, "ID"))) {
						ZDColumnValueSchema v = new ZDColumnValueSchema();
						v.setValue(articleColumnValues.getDataRow(j));
						v.setID(NoUtil.getMaxID("ColumnValueID"));
						v.setColumnID(idmap.getLong("Column." + articleColumnValues.getString(j, "ColumnID")));
						v.setRelaID("" + article.getID());
						v.setRelaType(articleColumnValues.getString(j, "RelaType"));
						vSet.add(v);
					}
				}
			}

			if (isSameServer && dt.getLong(i, "SiteID") == catalog.getSiteID()) {// ͬһ�������µ�ͬһվ�㲻����ͼƬ
				continue;
			}
			if (imageRelas == null || images == null) {
				continue;
			}
			// ����ͼƬ
			for (int j = 0; j < imageRelas.getRowCount(); j++) {
				if (imageRelas.getString(j, "RelaType").equals(Article.RELA_IMAGE)
						&& imageRelas.getString(j, "RelaID").equals(dt.getString(i, "ID"))) {
					ZCImageRelaSchema ir = new ZCImageRelaSchema();
					ir.setValue(imageRelas.getDataRow(j));
					for (int k = 0; k < images.getRowCount(); k++) {
						if (images.getLong(k, "ID") == ir.getID()) {
							ZCImageSchema image = new ZCImageSchema();
							image.setSiteID(catalog.getSiteID());
							image.setSourceURL(ServerAddr + "?ImageID=" + ir.getID());
							ZCImageSet set2 = image.query();
							if (set2.size() > 0) {
								image = set2.get(0);
								idmap.put("Image." + ir.getID(), image);// �����¾�ӳ��
								continue;
							}
							image.setValue(images.getDataRow(k));
							image.setID(NoUtil.getMaxID("DocID"));
							image.setSiteID(catalog.getSiteID());
							image.setCatalogID(imageCatalog.getID());
							image.setCatalogInnerCode(imageCatalog.getInnerCode());
							image.setStatus(Article.STATUS_TOPUBLISH);
							image.setPath("upload/Image/" + CatalogUtil.getPath(imageCatalog.getID()));
							image.setOrderFlag(OrderUtil.getDefaultOrder());
							image.setSourceURL(ServerAddr + "?ImageID=" + ir.getID());

							int random = NumberUtil.getRandomInt(10000);
							String ext = image.getFileName().substring(image.getFileName().lastIndexOf(".") + 1);
							String newFileName = image.getID() + "" + random + "." + ext;

							RequestImpl request = new RequestImpl();
							request.put("ID", ir.getID());// �˴��Ǿ�ID
							request.put("FileName", image.getFileName());
							Mapx data = null;
							if (isSameServer) {
								InnerSyncService service = new InnerSyncService();
								service.setRequest(request);
								service.sendImage();
								data = service.getResponse();
							} else {
								data = Framework.callRemoteMethod(ServerAddr,
										"com.nswt.datachannel.InnerSyncService.sendImage", request);
							}
							if ("1".equals(data.getString("Success"))) {
								String str = data.getString("Data");
								byte[] bs = StringUtil.base64Decode(str);
								String path = SiteUtil.getAbsolutePath(catalog.getSiteID()) + image.getPath();
								new File(path).mkdirs();
								image.setFileName(newFileName);
								image.setSrcFileName(newFileName);
								newFileName = path + newFileName;
								FileUtil.writeByte(newFileName, bs);
								try {
									ImageUtilEx.afterUploadImage(image, path);
									idmap.put("Image." + ir.getID(), image);// �����¾�ӳ��
								} catch (Throwable e) {
									e.printStackTrace();
								}
							}

							ir.setID(image.getID());
							iSet.add(image);
							break;
						}
					}
					ir.setRelaID(article.getID());
					rSet.add(ir);
				}
			}
			String html = article.getContent();
			html = dealImageTag(html, idmap, catalog);
			article.setContent(html);
		}
		tran.add(aSet, Transaction.INSERT);
		tran.add(vSet, Transaction.INSERT);
		tran.add(rSet, Transaction.INSERT);
		tran.add(iSet, Transaction.INSERT);
		tran.commit();
	}
	
	public static String dealImageTag(String html, Mapx idmap, ZCCatalogSchema catalog) {
		// �滻����HTML�е�ͼƬ����
		Matcher m = imagePattern.matcher(html);
		StringBuffer sb = new StringBuffer();
		int lastIndex = 0;
		while (m.find(lastIndex)) {
			int start = m.start();
			sb.append(html.substring(lastIndex, start));
			String tag = m.group(0);
			tag = tag.substring(tag.indexOf(" "));// ��ǩ����Ҫ
			tag = tag.substring(0, tag.length() - 1).trim();
			if (tag.endsWith("/")) {
				tag = tag.substring(0, tag.length() - 1).trim();
			}
			tag = tag.replaceAll("\\s+", " ");
			Mapx attrs = StringUtil.splitToMapxNew(tag, " ", "=");
			attrs = new CaseIgnoreMapx(attrs);
			String oldID = attrs.getString("nswtpimagerela");
			oldID = oldID.substring(1, oldID.length() - 1);
			ZCImageSchema image = (ZCImageSchema) idmap.get("Image." + oldID);
			if (image != null) {
				attrs.put("nswtpimagerela", "\"" + image.getID() + "\"");
				String src = SiteUtil.getPath(catalog.getSiteID()) + "/" + image.getPath() + "/1_"
						+ image.getFileName();
				attrs.put("src", "\"" + src.replaceAll("\\/+", "/") + "\"");
				sb.append("<img");
				Object[] ks = attrs.keyArray();
				for (int k = 0; k < ks.length; k++) {
					sb.append(" ");
					sb.append(ks[k]);
					sb.append("=");
					sb.append(attrs.get(ks[k]));
				}
				sb.append(" />");
			} else {
				sb.append(m.group(0));
			}
			lastIndex = m.end();
		}
		sb.append(html.substring(lastIndex));
		return sb.toString();
	}
}
