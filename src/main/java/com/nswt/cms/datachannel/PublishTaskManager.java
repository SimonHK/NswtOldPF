package com.nswt.cms.datachannel;

import java.util.Date;

import com.nswt.cms.document.Article;
import com.nswt.cms.pub.CatalogUtil;
import com.nswt.cms.site.Catalog;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.pub.ConfigEanbleTaskManager;
import com.nswt.schema.ZCArticleSchema;
import com.nswt.schema.ZCArticleSet;
import com.nswt.schema.ZCAttachmentSchema;
import com.nswt.schema.ZCAttachmentSet;
import com.nswt.schema.ZCAudioSchema;
import com.nswt.schema.ZCAudioSet;
import com.nswt.schema.ZCCatalogConfigSchema;
import com.nswt.schema.ZCCatalogSchema;
import com.nswt.schema.ZCImageSchema;
import com.nswt.schema.ZCImageSet;
import com.nswt.schema.ZCVideoSchema;
import com.nswt.schema.ZCVideoSet;

public class PublishTaskManager extends ConfigEanbleTaskManager {

	public void execute(long id) {
		LogUtil.getLogger().info("PublishTaskManager开始发布内容");

		ZCCatalogConfigSchema config = new ZCCatalogConfigSchema();
		config.setID(id);
		if (config.fill()) {
			ZCCatalogSchema catalog = new ZCCatalogSchema();
			catalog.setID(config.getCatalogID());
			if(!catalog.fill()){
				return;
			}
			Publisher p = new Publisher();
			if(catalog.getType() == Catalog.CATALOG){
				ZCArticleSet set = new ZCArticleSet();
				if (StringUtil.isNotEmpty(config.getCatalogID()+"")) {
					set = new ZCArticleSchema().query(new QueryBuilder(" where status =" + Article.STATUS_TOPUBLISH 
							+ " and (publishdate<=? or publishdate is null) and CatalogInnerCode like ?",new Date()
							, CatalogUtil.getInnerCode(config.getCatalogID()) + "%"));
				} else {
					set = new ZCArticleSchema().query(new QueryBuilder(" where status =" + Article.STATUS_TOPUBLISH  
							+ " and (publishdate<=? or publishdate is null) and SiteID=?" ,new Date(),config.getSiteID()));
				}
				p.publishArticle(set);
			} else if(catalog.getType() == Catalog.VIDEOLIB){
				ZCVideoSet set = new ZCVideoSet();
				if (StringUtil.isNotEmpty(config.getCatalogID()+"")) {
					set = new ZCVideoSchema().query(new QueryBuilder(" where status =" + Article.STATUS_TOPUBLISH 
							+ " and (publishdate<=? or publishdate is null) and CatalogInnerCode like ?"
							,new Date(), CatalogUtil.getInnerCode(config.getCatalogID()) + "%"));
				} else {
					set = new ZCVideoSchema().query(new QueryBuilder(" where status =" + Article.STATUS_TOPUBLISH  
							+ " and (publishdate<=? or publishdate is null) and SiteID=?",new Date(), config.getSiteID()));
				}
				p.publishDocs("video", set, true,null);
			} else if(catalog.getType() == Catalog.IMAGELIB){
				ZCImageSet set = new ZCImageSet();
				if (StringUtil.isNotEmpty(config.getCatalogID()+"")) {
					set = new ZCImageSchema().query(new QueryBuilder(" where status =" + Article.STATUS_TOPUBLISH 
							+ " and (publishdate<=? or publishdate is null) and CatalogInnerCode like ?"
							,new Date(), CatalogUtil.getInnerCode(config.getCatalogID()) + "%"));
				} else {
					set = new ZCImageSchema().query(new QueryBuilder(" where status =" + Article.STATUS_TOPUBLISH  
							+ " and (publishdate<=? or publishdate is null) and SiteID=?",new Date(), config.getSiteID()));
				}
				p.publishDocs("image", set, true,null);
			} else if(catalog.getType() == Catalog.AUDIOLIB){
				ZCAudioSet set = new ZCAudioSet();
				if (StringUtil.isNotEmpty(config.getCatalogID()+"")) {
					set = new ZCAudioSchema().query(new QueryBuilder(" where status =" + Article.STATUS_TOPUBLISH 
							+ " and (publishdate<=? or publishdate is null) and CatalogInnerCode like ?"
							,new Date(), CatalogUtil.getInnerCode(config.getCatalogID()) + "%"));
				} else {
					set = new ZCAudioSchema().query(new QueryBuilder(" where status =" + Article.STATUS_TOPUBLISH  
							+ " and (publishdate<=? or publishdate is null) and SiteID=?",new Date(), config.getSiteID()));
				}
				p.publishDocs("audio", set, true,null);
			} else if(catalog.getType() == Catalog.ATTACHMENTLIB){
				ZCAttachmentSet set = new ZCAttachmentSet();
				if (StringUtil.isNotEmpty(config.getCatalogID()+"")) {
					set = new ZCAttachmentSchema().query(new QueryBuilder(" where status =" + Article.STATUS_TOPUBLISH 
							+ " and (publishdate<=? or publishdate is null) and CatalogInnerCode like ?"
							,new Date(), CatalogUtil.getInnerCode(config.getCatalogID()) + "%"));
				} else {
					set = new ZCAttachmentSchema().query(new QueryBuilder(" where status =" + Article.STATUS_TOPUBLISH  
							+ " and (publishdate<=? or publishdate is null) and SiteID=?",new Date(), config.getSiteID()));
				}
				p.publishDocs("attachment", set, true,null);
			}

		}
		LogUtil.getLogger().info("PublishTaskManager结束发布内容");
	}

	public String getCode() {
		return "Publisher";
	}

	public String getName() {
		return "内容发布任务";
	}

	public Mapx getConfigEnableTasks() {
		Mapx map = new Mapx();
		map.put("-1", "发布全部文档");
		map.put("0", "全局区块发布");
		return map;
	}

	public boolean isRunning(long arg0) {
		return false;
	}

}
