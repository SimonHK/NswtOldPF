package com.nswt.cms.datachannel;

import java.util.ArrayList;
import java.util.Date;

import com.nswt.cms.document.Article;
import com.nswt.cms.pub.CatalogUtil;
import com.nswt.cms.pub.PubFun;
import com.nswt.cms.pub.SiteUtil;
import com.nswt.cms.resource.ConfigImageLib;
import com.nswt.cms.template.PageGenerator;
import com.nswt.framework.Config;
import com.nswt.framework.User;
import com.nswt.framework.data.DataRow;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.messages.LongTimeTask;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.orm.SchemaSet;
import com.nswt.framework.utility.Errorx;
import com.nswt.framework.utility.FileUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.UserList;
import com.nswt.platform.pub.NoUtil;
import com.nswt.schema.ZCArticleLogSchema;
import com.nswt.schema.ZCArticleSchema;
import com.nswt.schema.ZCArticleSet;
import com.nswt.schema.ZCAttachmentSchema;
import com.nswt.schema.ZCAudioSchema;
import com.nswt.schema.ZCCatalogSchema;
import com.nswt.schema.ZCImageSchema;
import com.nswt.schema.ZCVideoSchema;
import com.nswt.schema.ZSGoodsSchema;
import com.nswt.schema.ZSGoodsSet;

/**
 * @Author ����
 * @Date 2008-5-12
 * @Mail lanjun@nswt.com
 */
public class Publisher {

	/**
	 * ����ȫվ����ҳ�棬����ԭ�������ļ�
	 * 
	 * @param siteID
	 * @return
	 */
	public boolean publishAll(long siteID) {
		PageGenerator p = new PageGenerator();
		if (p.staticSite(siteID)) {
			Deploy d = new Deploy();
			d.addJobs(siteID, p.getFileList());
			return true;
		} else {
			return false;
		}
	}

	/**
	 * ����վ������ҳ�棬ͬʱ�ṩ����������task
	 * 
	 * @param siteID
	 * @param task
	 * @return
	 */
	public boolean publishAll(long siteID, LongTimeTask task) {
		PageGenerator p = new PageGenerator(task);
		if (p.staticSite(siteID)) {
			Deploy d = new Deploy();
			d.addJobs(siteID, p.getFileList());
			return true;
		} else {
			return false;
		}
	}

	/**
	 * ����վ����ҳ
	 * 
	 * @param siteID
	 * @return
	 */
	public boolean publishIndex(long siteID, LongTimeTask task) {
		PageGenerator p = new PageGenerator(task);
		if (p.staticSiteIndex(siteID)) {
			Deploy d = new Deploy();
			d.addJobs(siteID, p.getFileList());
			return true;
		} else {
			return false;
		}
	}

	public boolean publishIndex(long siteID) {
		PageGenerator p = new PageGenerator();
		if (p.staticSiteIndex(siteID)) {
			Deploy d = new Deploy();
			d.addJobs(siteID, p.getFileList());
			return true;
		} else {
			return false;
		}
	}

	/**
	 * �������� ͬʱ�������¶�Ӧ���б�ҳ
	 * 
	 * @param articleSet
	 * @return
	 */
	public boolean publishArticle(ZCArticleSet articleSet, boolean generateCatalog, LongTimeTask task) {
		if (articleSet == null || articleSet.size() == 0) {
			return true;
		}
		Transaction trans = new Transaction();
		if (publishArticle(trans, articleSet, generateCatalog, task)) {
			if (!trans.commit()) {
				Errorx.addError("��������״̬ʧ�ܡ�");
				return false;
			}
			return true;
		}
		return false;
	}

	/**
	 * �������� ͬʱ�������¶�Ӧ���б�ҳ
	 * 
	 * @param articleSet
	 * @return
	 */
	public boolean publishArticle(Transaction trans, ZCArticleSet articleSet, boolean generateCatalog, LongTimeTask task) {
		if (articleSet == null || articleSet.size() <= 0) {
			return true;
		}
		PageGenerator p = new PageGenerator(task);
		if (task != null) {
			task.setCurrentInfo("��ʼ��������");
			task.setPercent(15);
		}
		Deploy d = new Deploy();
		Date date = new Date();
		for (int i = 0; i < articleSet.size(); i++) {
			ZCArticleSchema article = articleSet.get(i);
			ZCArticleLogSchema articleLog = new ZCArticleLogSchema();

			if (article.getStatus() == Article.STATUS_OFFLINE) {
				String msg = "�ĵ���������״̬�����ܷ��������跢�����������ߺ��ٲ�����";
				if (task != null) {
					task.addError(msg);
				}
				Errorx.addError(msg);
				continue;
			}

			String workflowName = CatalogUtil.getWorkflow(article.getCatalogID());
			// �й�����ʱ���岻�ܷ���
			if (StringUtil.isNotEmpty(workflowName) && article.getStatus() == Article.STATUS_WORKFLOW) {
				String msg = "�ĵ�����ˣ����ܷ�����";
				if (task != null) {
					task.addError(msg);
				}
				Errorx.addError(msg);
				continue;
			} else if (StringUtil.isNotEmpty(workflowName) && article.getStatus() == Article.STATUS_DRAFT
					&& !UserList.ADMINISTRATOR.equals(article.getAddUser())) {
				String msg = "�ĵ�����ˣ����ܷ�����";
				if (task != null) {
					task.addError(msg);
				}
				Errorx.addError(msg);
				continue;
			}

			// ����ʱ����ڵ�ǰʱ���򲻷����ĵ�
			if (article.getPublishDate() != null && article.getPublishDate().getTime() > new Date().getTime()) {
				String msg = "δ������ʱ�䣬�ĵ����ܷ�����";
				if (task != null) {
					task.addError(msg);
				}
				Errorx.addError(msg);
				continue;
			}

			QueryBuilder qb = new QueryBuilder("update zcarticle set modifyuser=?,modifytime=?,status=?");
			if (User.getCurrent() == null) {
				qb.add("System");
				article.setModifyUser("System");
			} else {
				if(StringUtil.isNotEmpty(User.getUserName())){
					qb.add(User.getUserName());
					article.setModifyUser(User.getUserName());
				}else{
					qb.add("System");
					article.setModifyUser("System");
				}
			}
			qb.add(date);
			qb.add(Article.STATUS_PUBLISHED);
			if (StringUtil.isEmpty(article.getRelativeArticle())) {
				Article.dealRelaArticle(article);
				qb.append(",relativearticle=?", article.getRelativeArticle());
			}
			if (article.getPublishDate() == null) {
				qb.append(",publishdate=?", date);
				article.setPublishDate(date);
			}
			qb.append(" where id=?", article.getID());

			qb.executeNoQuery();
			article.setModifyTime(date);
			article.setStatus(Article.STATUS_PUBLISHED);

			if (!p.staticDoc("Article", article)) {
				continue;
			}

			d.addJobs(articleSet.get(0).getSiteID(), p.getFileList());

			if (task != null) {
				task.setPercent(30 + i / articleSet.size() * 50);
				task.setCurrentInfo("���ڷ���:" + article.getTitle());
			}
			articleLog.setID(NoUtil.getMaxID("ArticleLogID"));
			articleLog.setArticleID(article.getID());
			articleLog.setAction("PUBLISH");
			articleLog.setActionDetail("��������");
			if (User.getCurrent() == null) {
				articleLog.setAddUser("SYSTEM");
			} else {
				if(StringUtil.isNotEmpty(User.getUserName())){
					articleLog.setAddUser(User.getUserName());
				}else{
					articleLog.setAddUser("SYSTEM");
				}
				
			}

			articleLog.setAddTime(date);
			trans.add(articleLog, Transaction.INSERT);

			// �������¹�����ý���ļ�
			trans.add(new QueryBuilder("update zcimage set status=" + Article.STATUS_PUBLISHED
					+ " where status<>? and exists (select 1 from zcimagerela where relaid=? and id=zcimage.id)",
					Article.STATUS_PUBLISHED, article.getID()));
			trans.add(new QueryBuilder("update zcvideo set status=" + Article.STATUS_PUBLISHED
					+ " where status<>? and exists (select id from zcvideorela where relaid=? and id=zcvideo.id)",
					Article.STATUS_PUBLISHED, article.getID()));
			trans.add(new QueryBuilder(
					"update zcattachment set status="
							+ Article.STATUS_PUBLISHED
							+ " where status<>? and exists (select id from zcattachmentrela where relaid=? and id=zcattachment.id)",
					Article.STATUS_PUBLISHED, article.getID()));
			trans.add(new QueryBuilder("update zcaudio set status=" + Article.STATUS_PUBLISHED
					+ " where status<>? and exists (select id from zcaudiorela where relaid=? and id=zcaudio.id)",
					Article.STATUS_PUBLISHED, article.getID()));
		}

		if (generateCatalog) {
			// �������漰����ĿID��������������ʱ����
			Mapx catalogMap = new Mapx();
			for (int k = 0; k < articleSet.size(); k++) {
				catalogMap.put(articleSet.get(k).getCatalogID() + "", articleSet.get(k).getCatalogID() + "");
				String pid = CatalogUtil.getParentID(articleSet.get(k).getCatalogID());
				while (StringUtil.isNotEmpty(pid) && !"null".equals(pid) && !"0".equals(pid)) {
					catalogMap.put(pid, pid);
					pid = CatalogUtil.getParentID(pid);
				}
			}

			// ���ɱ�����Ŀ
			Object[] vs = catalogMap.valueArray();
			for (int j = 0; j < catalogMap.size(); j++) {
				String listpage = CatalogUtil.getData(vs[j].toString()).getString("ListPage");
				if (StringUtil.isEmpty(listpage) || Integer.parseInt(listpage) < 1) {
					listpage = "20"; // Ĭ��ֻ����ǰ��ʮҳ
				}
				publishCatalog(Long.parseLong(vs[j].toString()), false, false, Integer.parseInt(listpage));
				if (task != null) {
					task.setPercent(task.getPercent() + 5);
					task.setCurrentInfo("������Ŀҳ��");
				}
			}
		}
		return true;
	}

	public boolean publishDocs(String docType, SchemaSet docSet, boolean generateCatalog, LongTimeTask task) {
		if (docSet == null || docSet.size() <= 0) {
			return true;
		}
		PageGenerator p = new PageGenerator(task);
		if (task != null) {
			task.setCurrentInfo("��ʼ����");
		}

		Transaction trans = new Transaction();
		Mapx catalogMap = new Mapx();
		long siteID = 0;
		Deploy d = new Deploy();
		for (int i = 0; i < docSet.size(); i++) {
			Schema doc = (Schema) docSet.getObject(i);

			if (!p.staticDoc(docType, (Schema) doc.clone())) {
				continue;
			}

			// ���·���ʱ��
			DataRow row = doc.toDataRow();

			// ��ӷ����б�
			siteID = row.getLong("SiteID");
			d.addJobs(siteID, p.getFileList());

			Date publishDate = row.getDate("PublishDate");
			if (publishDate == null) {
				publishDate = new Date();
			}
			String modifyUser = null;
			if (User.getCurrent() == null) {
				modifyUser = "sys";
			} else {
				modifyUser = User.getUserName();
			}

			QueryBuilder qb = new QueryBuilder("update zc" + docType
					+ " set publishdate=?,modifytime=?,modifyuser=?,status=? where id=?");
			qb.add(publishDate);
			qb.add(new Date());
			qb.add(modifyUser);
			qb.add(Article.STATUS_PUBLISHED);
			qb.add(row.get("ID"));
			trans.add(qb);

			catalogMap.put(row.getString("catalogid"), row.getString("catalogid"));

			if (task != null) {
				task.setPercent(30 + i / docSet.size() * 50);
				task.setCurrentInfo("���ڷ���:" + row.getString("Name"));
			}
		}

		if (!trans.commit()) {
			Errorx.addError("�����ĵ�״̬ʧ�ܡ�");
			return false;
		}

		if (generateCatalog) {
			// ���ɱ�����Ŀ
			Object[] vs = catalogMap.valueArray();
			for (int j = 0; j < catalogMap.size(); j++) {
				String listpage = CatalogUtil.getData(vs[j].toString()).getString("ListPage");
				if (StringUtil.isEmpty(listpage) || Integer.parseInt(listpage) < 1) {
					listpage = "20"; // Ĭ��ֻ����ǰ��ʮҳ
				}
				publishCatalog(Long.parseLong(vs[j].toString()), false, false, Integer.parseInt(listpage));
				if (task != null) {
					task.setPercent(task.getPercent() + 5);
					task.setCurrentInfo("������Ŀҳ��");
				}
			}
		}

		if (Errorx.hasError()) {
			return false;
		}

		return true;
	}

	public boolean publishArticle(ZCArticleSet articleSet) {
		return publishArticle(articleSet, true, null);
	}

	public boolean publishArticle(ZCArticleSet articleSet, LongTimeTask task) {
		return publishArticle(articleSet, true, task);
	}

	/**
	 * ������Ʒ ͬʱ������Ʒ��Ӧ���б�ҳ
	 * 
	 * @param articleSet
	 * @return
	 */
	public boolean publishGoods(ZSGoodsSet goodsSet, LongTimeTask task) {
		return publishGoods(goodsSet, true, task);
	}

	public boolean publishGoods(ZSGoodsSet goodsSet, boolean generateCatalog, LongTimeTask task) {
		Transaction trans = new Transaction();
		if (publishGoods(trans, goodsSet, generateCatalog, task)) {
			if (!trans.commit()) {
				Errorx.addError("������Ʒ״̬ʧ�ܡ�");
				return false;
			}
			return true;
		}
		return false;
	}

	public boolean publishGoods(Transaction trans, ZSGoodsSet goodsSet, boolean generateCatalog, LongTimeTask task) {
		PageGenerator p = new PageGenerator(task);
		if (task != null) {
			task.setCurrentInfo("��ʼ������Ʒ");
		}
		Deploy d = new Deploy();
		Date date = new Date();
		for (int i = 0; i < goodsSet.size(); i++) {
			ZSGoodsSchema goods = goodsSet.get(i);

			if (goods.getStatus().equals(Article.STATUS_OFFLINE + "")) {
				String msg = "��Ʒ��������״̬�����ܷ��������跢�����������ߺ��ٲ�����";
				if (task != null) {
					task.addError(msg);
				}
				Errorx.addError(msg);
				continue;
			}

			String workflowName = CatalogUtil.getWorkflow(goods.getCatalogID());
			if (StringUtil.isNotEmpty(workflowName) && goods.getStatus().equals(Article.STATUS_WORKFLOW + "")) {
				String msg = "��Ʒ����ˣ����ܷ�����";
				if (task != null) {
					task.addError(msg);
				}
				Errorx.addError(msg);
				continue;
			}

			QueryBuilder qb = new QueryBuilder("update zsgoods set modifyuser=?,modifytime=?,status=? where id=?");
			if (goods.getPublishDate() == null) {
				qb = new QueryBuilder("update zsgoods set publishdate=?,modifyuser=?,modifytime=?,status=? where id=?");
				qb.add(date);
				goods.setPublishDate(date);
			}

			if (User.getCurrent() == null) {
				qb.add("System");
				goods.setModifyUser("System");
			} else {
				qb.add(User.getUserName());
				goods.setModifyUser(User.getUserName());
			}
			qb.add(date);
			qb.add(Article.STATUS_PUBLISHED);
			qb.add(goods.getID());
			qb.executeNoQuery();
			goods.setModifyTime(date);
			goods.setStatus(Article.STATUS_PUBLISHED + "");

			if (!p.staticDoc("Article", goods)) {
				continue;
			}

			d.addJobs(goodsSet.get(0).getSiteID(), p.getFileList());

			if (task != null) {
				task.setPercent(30 + i / goodsSet.size() * 50);
				task.setCurrentInfo("���ڷ���:" + goods.getName());
			}
		}

		if (generateCatalog) {
			// �������漰����ĿID��������������ʱ����
			Mapx catalogMap = new Mapx();
			for (int k = 0; k < goodsSet.size(); k++) {
				catalogMap.put(goodsSet.get(k).getCatalogID() + "", goodsSet.get(k).getCatalogID() + "");
				String pid = CatalogUtil.getParentID(goodsSet.get(k).getCatalogID());
				while (StringUtil.isNotEmpty(pid) && !"null".equals(pid) && !"0".equals(pid)) {
					catalogMap.put(pid, pid);
					pid = CatalogUtil.getParentID(pid);
				}
			}

			// ���ɱ�����Ŀ
			Object[] vs = catalogMap.valueArray();
			for (int j = 0; j < catalogMap.size(); j++) {
				String listpage = CatalogUtil.getData(vs[j].toString()).getString("ListPage");
				if (StringUtil.isEmpty(listpage) || Integer.parseInt(listpage) < 1) {
					listpage = "20"; // Ĭ��ֻ����ǰ��ʮҳ
				}
				publishCatalog(Long.parseLong(vs[j].toString()), false, false, Integer.parseInt(listpage));
				if (task != null) {
					task.setPercent(task.getPercent() + 5);
					task.setCurrentInfo("������Ŀҳ��");
				}
			}
		}
		return true;
	}

	/**
	 * ������Ŀ
	 * 
	 * @param catalogID
	 * @param child
	 * @param detail
	 * @return
	 */
	public boolean publishCatalog(long catalogID, boolean child, boolean detail) {
		return publishCatalog(catalogID, child, detail, 0, null);
	}

	public boolean publishCatalog(long catalogID, boolean child, boolean detail, int publishSize) {
		return publishCatalog(catalogID, child, detail, publishSize, null);
	}

	public boolean publishCatalog(long catalogID, boolean child, boolean detail, LongTimeTask task) {
		return publishCatalog(catalogID, child, detail, 0, task);
	}

	/**
	 * ������Ŀ�������ļ���ȫ�����ɣ�����ԭ���ļ�
	 * 
	 * @param catalogID
	 * @param child �Ƿ���������Ŀ
	 * @param detail �Ƿ�������ϸҳ
	 * @param publishPages ������ҳ��
	 * @param task ��ʱ������ ������������
	 * @return
	 */
	public boolean publishCatalog(long catalogID, boolean child, boolean detail, int publishPages, LongTimeTask task) {
		PageGenerator p = new PageGenerator(task);
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(catalogID);
		catalog.fill();

		if (publishPages == 0 && catalog.getListPage() > 0) {
			publishPages = (int) catalog.getListPage();// �б�ҳ����ҳ��
		}

		// ���б�ҳ����ҳ��С��ָ��ҳ��ʱ�����б�ҳ����ҳ��Ϊ׼
		if (catalog.getListPage() > 0 && catalog.getListPage() < publishPages) {
			publishPages = (int) catalog.getListPage();
		}

		if (task != null) {
			task.setPercent(30);
		}

		if (!p.staticCatalog(catalog, detail, publishPages)) {
			return false;
		}

		if (task != null) {
			task.setPercent(45);
		}

		if (child) {
			if (!p.staticChildCatalog(catalogID, detail, publishPages)) {
				return false;
			}
		}

		if (task != null) {
			task.setPercent(75);
		}

		if (detail) {
			Transaction trans = new Transaction();
			String wherePart = null;
			String catalogWherePart = " CatalogID=" + catalog.getID();

			if (child) {
				catalogWherePart = " CatalogInnerCode like '" + catalog.getInnerCode() + "%' ";
			}
			wherePart = " where " + catalogWherePart + " and status in(" + Article.STATUS_TOPUBLISH + ","
					+ Article.STATUS_PUBLISHED + ") and publishdate is null";

			trans.add(new QueryBuilder("update zcarticle set publishDate = ?,status=" + Article.STATUS_PUBLISHED
					+ wherePart, new Date()));
			if (!trans.commit()) {
				return false;
			}
		}

		Deploy d = new Deploy();
		d.addJobs(catalog.getSiteID(), p.getFileList());

		if (task != null) {
			task.setPercent(100);
		}

		return true;
	}

	public void deletePubishedFile(ZCImageSchema image) {
		String contextRealPath = Config.getContextRealPath();
		String staticDir = contextRealPath + Config.getValue("Statical.TargetDir").replace('\\', '/');
		String filePath = staticDir + "/" + SiteUtil.getAlias(image.getSiteID()) + "/upload/"
				+ CatalogUtil.getPath(image.getCatalogID()) + "/" + image.getID() + ".shtml";
		FileUtil.delete(filePath);
	}

	/**
	 * ɾ���ĵ���������ļ������ͼƬ��ص�����ͼ�����ɵ�shtml�ļ�
	 * 
	 * @param set
	 */
	public void deletePubishedFile(SchemaSet set) {
		String contextRealPath = Config.getContextRealPath();
		String staticDir = contextRealPath + Config.getValue("Statical.TargetDir").replace('\\', '/') + "/";
		ArrayList fileList = new ArrayList();
		long siteID = 0;
		for (int i = 0; i < set.size(); i++) {
			Schema doc = set.getObject(i);
			siteID = doc.toDataRow().getLong("SiteID");
			if (doc instanceof ZCArticleSchema) {
				ZCArticleSchema article = (ZCArticleSchema) doc;
				String filePath = staticDir + SiteUtil.getAlias(article.getSiteID()) + "/"
						+ PubFun.getArticleURL(article);
				fileList.add(filePath);
			} else if (doc instanceof ZCImageSchema) {
				ZCImageSchema image = (ZCImageSchema) doc;
				String basePath = staticDir + SiteUtil.getAlias(image.getSiteID()) + "/" + image.getPath();
				Mapx configFields = new Mapx();
				configFields.putAll(ConfigImageLib.getImageLibConfig(image.getSiteID()));
				int count = Integer.parseInt(configFields.get("Count").toString());
				for (int j = 1; j <= count; j++) {
					fileList.add(basePath + j + "_" + image.getFileName());
				}
				fileList.add(basePath + "s_" + image.getFileName());
				fileList.add(basePath + image.getSrcFileName());
				fileList.add(staticDir + SiteUtil.getAlias(image.getSiteID()) + "/"
						+ CatalogUtil.getPath(image.getCatalogID()) + "/" + image.getID() + ".shtml");
			} else if (doc instanceof ZCAttachmentSchema) {
				ZCAttachmentSchema attach = (ZCAttachmentSchema) doc;
				String basePath = staticDir + SiteUtil.getAlias(attach.getSiteID()) + "/" + attach.getPath();
				fileList.add(basePath + attach.getSrcFileName());
				if (StringUtil.isNotEmpty(attach.getImagePath())
						&& attach.getImagePath().indexOf("nopicture.jpg") == -1) {
					String coverImage = staticDir + "/" + attach.getImagePath();
					fileList.add(coverImage);
				}
				fileList.add(staticDir + SiteUtil.getAlias(attach.getSiteID()) + "/"
						+ CatalogUtil.getPath(attach.getCatalogID()) + "/" + attach.getID() + ".shtml");
			} else if (doc instanceof ZCVideoSchema) {
				ZCVideoSchema video = (ZCVideoSchema) doc;
				String basePath = staticDir + SiteUtil.getAlias(video.getSiteID()) + "/" + video.getPath();
				fileList.add(basePath + video.getSrcFileName());
				fileList.add(basePath + video.getImageName());
				fileList.add(basePath + video.getFileName());
				fileList.add(staticDir + SiteUtil.getAlias(video.getSiteID()) + "/"
						+ CatalogUtil.getPath(video.getCatalogID()) + "/" + video.getID() + ".shtml");
			} else if (doc instanceof ZCAudioSchema) {
				ZCAudioSchema audio = (ZCAudioSchema) doc;
				String basePath = staticDir + SiteUtil.getAlias(audio.getSiteID()) + "/" + audio.getPath()
						+ audio.getSrcFileName();
				fileList.add(basePath + audio.getSrcFileName());
				fileList.add(basePath + audio.getFileName());
				fileList.add(staticDir + SiteUtil.getAlias(audio.getSiteID()) + "/"
						+ CatalogUtil.getPath(audio.getCatalogID()) + "/" + audio.getID() + ".shtml");
			}
		}

		// ɾ�������ļ�
		for (int i = 0; i < fileList.size(); i++) {
			FileUtil.delete((String) fileList.get(i));
		}

		// ɾ������������ϵ��ļ�
		Deploy deploy = new Deploy();
		if (fileList.size() > 0 && siteID != 0) {
			deploy.addJobs(siteID, fileList, Deploy.OPERATION_DELETE);
		}

	}

	public long publishSetTask(final String type, final SchemaSet set) {
		LongTimeTask ltt = new LongTimeTask() {
			public void execute() {
				Publisher p = new Publisher();
				p.publishDocs(type, set, true, this);
				setPercent(100);
			}
		};
		ltt.setUser(User.getCurrent());
		ltt.start();
		return ltt.getTaskID();
	}

	public long deleteFileTask(final SchemaSet set) {
		LongTimeTask ltt = new LongTimeTask() {
			public void execute() {
				Publisher p = new Publisher();
				if (set != null && set.size() > 0) {
					p.deletePubishedFile(set);
					String listpage = CatalogUtil.getData(set.getObject(0).toDataRow().getString("CatalogID"))
							.getString("ListPage");
					if (StringUtil.isEmpty(listpage) || Integer.parseInt(listpage) < 1) {
						listpage = "20"; // Ĭ��ֻ����ǰ��ʮҳ
					}
					p.publishCatalog(set.getObject(0).toDataRow().getLong("CatalogID"), false, false,
							Integer.parseInt(listpage));
					setPercent(100);
				}
			}
		};
		ltt.setUser(User.getCurrent());
		ltt.start();
		return ltt.getTaskID();
	}

}
