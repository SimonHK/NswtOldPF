package com.nswt.cms.datachannel;

import java.util.ArrayList;
import java.util.Date;

import com.nswt.cms.document.Article;
import com.nswt.cms.pub.SiteUtil;
import com.nswt.framework.Config;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.schedule.GeneralTask;
import com.nswt.framework.utility.FileUtil;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.schema.ZCArticleSchema;
import com.nswt.schema.ZCArticleSet;
import com.nswt.schema.ZCSiteSchema;
import com.nswt.schema.ZCSiteSet;

public class PublishTask extends GeneralTask {

	public void execute() {
		LogUtil.getLogger().info("PublishTask��ʱ��������");
		Publisher p = new Publisher();
		// ��������
		ZCArticleSchema article = new ZCArticleSchema();
		ZCArticleSet set = article.query(new QueryBuilder(
				" where publishdate<=? and status =" + Article.STATUS_TOPUBLISH ,new Date()));

		if (set != null && set.size() > 0) {
			LogUtil.getLogger().info("�跢������ƪ����" + set.size());
			p.publishArticle(set);
		}

		// ��������
		set = article.query(new QueryBuilder("where downlinedate<=? and status=?", 
				new Date(), Article.STATUS_PUBLISHED));
		if (set != null && set.size() > 0) {
			LogUtil.getLogger().info("����������ƪ����" + set.size());

			Mapx catalogMap = new Mapx();
			for (int i = 0; i < set.size(); i++) {
				set.get(i).setStatus(Article.STATUS_OFFLINE); // ����״̬
				set.get(i).setTopFlag("0");
				catalogMap.put(set.get(i).getCatalogID() + "", set.get(i).getCatalogID());
			}

			if (set.update()) {
				p.deletePubishedFile(set);
				Object[] vs = catalogMap.valueArray();
				for (int i = 0; i < catalogMap.size(); i++) {
					p.publishCatalog(Long.parseLong(vs[i].toString()), false, false);
				}
			}
		}
		
        //�鵵����
//		DataTable dt = new QueryBuilder("select distinct catalogid from zcarticle where status=?",Article.STATUS_ARCHIVE).executeDataTable();
//		if (dt != null && dt.getRowCount() > 0) {
//			for (int j = 0; j < dt.getRowCount(); j++) {
//				p.publishCatalog(dt.getLong(j, "catalogid"), false, false);
//			}
//		}
		
		//��վ����ҳ����Ϊindex.html
		if("Y".equals(Config.getValue("RewriteIndex"))){
			ZCSiteSet sites = new ZCSiteSchema().query();
			for (int i = 0; i < sites.size(); i++) {
				long siteID = sites.get(i).getID();
				String url = SiteUtil.getURL(siteID)+"/index.shtml";
				String content = FileUtil.readURLText(url);
				if(StringUtil.isNotEmpty(content)){
					String indexFileName = Config.getValue("RewriteFileName");
					if(StringUtil.isEmpty(indexFileName)){
						indexFileName = "index.html";
					}
					FileUtil.writeText(SiteUtil.getAbsolutePath(siteID)+"/"+indexFileName, content);
					ArrayList list = new ArrayList();
					list.add(SiteUtil.getAbsolutePath(siteID)+"/"+indexFileName);
					Deploy d = new Deploy();
					d.addJobs(siteID, list);
				}
			}
		}

		LogUtil.getLogger().info("PublishTask�����������");
	}

	public long getID() {
		return 200904241314L;
	}

	public String getName() {
		return "����ָ�����ڵ�����";
	}

	public static void main(String[] args) {
		PublishTask p = new PublishTask();
		p.execute();
	}
}
