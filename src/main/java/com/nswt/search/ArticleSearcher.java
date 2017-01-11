package com.nswt.search;

import java.util.Date;

import org.apache.lucene.search.SortField;

import com.nswt.cms.api.SearchAPI;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.utility.DateUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;

/**
 * ����������
 * 
 * @Author ������
 * @Date 2008-12-9
 * @Mail nswt@nswt.com.cn
 */
public class ArticleSearcher {
	/**
	 * ��ͨ������ֻ����վ�������ID/��ѯ�ؼ���/ʱ��/�Ƿ�ʱ������
	 * 
	 * @param map
	 * @param pageSize
	 * @return
	 */
	public static SearchResult search(Mapx map) {
		SearchParameters sps = new SearchParameters();
		String site = map.getString("site");
		String id = map.getString("id");
		String catalog = map.getString("catalog");
		if (StringUtil.isEmpty(catalog)) {
			catalog = map.getString("Catalog");
		}
		String order = map.getString("order");
		String time = map.getString("time");
		String keyword = map.getString("keyword");
		String query = map.getString("query");
		if (StringUtil.isEmpty(keyword)) {
			keyword = query;// �;ɰ汾����һ��
		}
		String page = map.getString("page");
		String size = map.getString("size");

		if (StringUtil.isEmpty(id)) {
			id = SearchAPI.getIndexIDBySiteID(site);
		}

		// �����ؼ���
		if (StringUtil.isNotEmpty(keyword)) {
			sps.addFulltextField("Title", keyword, false);
			sps.addFulltextField("Content", keyword, false);
			sps.addFulltextField("_Keyword", keyword, true);
		}

		// ����
		if ("time".equalsIgnoreCase(order)) {
			sps.setSortField("PublishDate", SortField.STRING, true);
		}

		// ʱ�䷶Χ
		if (StringUtil.isNotEmpty(time)) {
			Date today = new Date();
			String StartDate = DateUtil.toString(DateUtil.addDay(today, -36500));
			if (time.equals("week")) {
				StartDate = DateUtil.toString(DateUtil.addDay(today, -7));
			} else if (time.equals("month")) {
				StartDate = DateUtil.toString(DateUtil.addDay(today, -30));
			} else if (time.equals("quarter")) {
				StartDate = DateUtil.toString(DateUtil.addDay(today, -90));
			}
			String EndDate = "2999-01-01";
			sps.setDateRange("PublishDate", StartDate, EndDate);
		}
		if (StringUtil.isNotEmpty(catalog)) {
			sps.addLeftLikeField("CatalogInnerCode", catalog);
		}
		if (StringUtil.isNotEmpty(page)) {
			sps.setPageIndex(Integer.parseInt(page) - 1);
		}
		if (StringUtil.isNotEmpty(size)) {
			sps.setPageSize(Integer.parseInt(size));
		}
		if (StringUtil.isEmpty(id)) {
			SearchResult sr = new SearchResult();
			sr.Data = new DataTable();
			return sr;
		}
		sps.setIndexID(Long.parseLong(id));
		return ArticleIndexer.search(sps);
	}
	
	public static SearchResult multiSearch(Mapx map,String[] index) {
		
		SearchParameters sps = new SearchParameters();
		String catalog = map.getString("catalog");
		if (StringUtil.isEmpty(catalog)) {
			catalog = map.getString("Catalog");
		}
		String order = map.getString("order");
		String time = map.getString("time");
		String keyword = map.getString("keyword");
		String query = map.getString("query");
		if (StringUtil.isEmpty(keyword)) {
			keyword = query;// �;ɰ汾����һ��
		}
		String page = map.getString("page");
		String size = map.getString("size");


		// �����ؼ���
		if (StringUtil.isNotEmpty(keyword)) {
			sps.addFulltextField("Title", keyword, false);
			sps.addFulltextField("Content", keyword, false);
			sps.addFulltextField("_Keyword", keyword, true);
		}

		// ����
		if ("time".equalsIgnoreCase(order)) {
			sps.setSortField("PublishDate", SortField.STRING, true);
		}

		// ʱ�䷶Χ
		if (StringUtil.isNotEmpty(time)) {
			Date today = new Date();
			String StartDate = DateUtil.toString(DateUtil.addDay(today, -36500));
			if (time.equals("week")) {
				StartDate = DateUtil.toString(DateUtil.addDay(today, -7));
			} else if (time.equals("month")) {
				StartDate = DateUtil.toString(DateUtil.addDay(today, -30));
			} else if (time.equals("quarter")) {
				StartDate = DateUtil.toString(DateUtil.addDay(today, -90));
			}
			String EndDate = "2999-01-01";
			sps.setDateRange("PublishDate", StartDate, EndDate);
		}
		if (StringUtil.isNotEmpty(catalog)) {
			sps.addLeftLikeField("CatalogInnerCode", catalog);
		}
		if (StringUtil.isNotEmpty(page)) {
			sps.setPageIndex(Integer.parseInt(page) - 1);
		}
		if (StringUtil.isNotEmpty(size)) {
			sps.setPageSize(Integer.parseInt(size));
		}
		return ArticleIndexer.multiSearch(sps, index);
	}


	/**
	 * ��ǩ����
	 */
	public static SearchResult tagSearch(Mapx map) {
		SearchParameters sps = new SearchParameters();
		String site = map.getString("site");
		String order = map.getString("order");
		String keyword = map.getString("keyword");
		String query = map.getString("query");
		if (StringUtil.isEmpty(keyword)) {
			keyword = query;// �;ɰ汾����һ��
		}
		String page = map.getString("page");
		String size = map.getString("size");

		// �����ؼ���
		if (StringUtil.isNotEmpty(keyword)) {
			sps.addLikeField("Tag", keyword, false);
		}

		// ����
		if ("time".equalsIgnoreCase(order)) {
			sps.setSortField("PublishDate", SortField.STRING, true);
		}
		if (StringUtil.isNotEmpty(page)) {
			sps.setPageIndex(Integer.parseInt(page) - 1);
		}
		if (StringUtil.isNotEmpty(size)) {
			sps.setPageSize(Integer.parseInt(size));
		}
		String id = SearchAPI.getIndexIDBySiteID(site);
		sps.setIndexID(Long.parseLong(id));
		return ArticleIndexer.search(sps);
	}

	/**
	 * �߼����������������������
	 * 
	 * @param map
	 * @return
	 */
	public static SearchResult advanceSearch(Mapx map) {
		SearchParameters sps = new SearchParameters();

		String site = map.getString("site");
		String id = map.getString("id");
		String startDate = map.getString("startdate");
		String endDate = map.getString("enddate");
		String catalog = map.getString("catalog");
		String author = map.getString("author");
		String title = map.getString("title");
		String content = map.getString("content");
		String keyword = map.getString("keyword");
		String query = map.getString("query");
		if (StringUtil.isEmpty(keyword)) {
			keyword = query;// �;ɰ汾����һ��
		}
		String orderField = map.getString("orderfield");
		String descFlag = map.getString("descflag");
		String page = map.getString("page");
		String size = map.getString("size");

		if (StringUtil.isEmpty(id)) {
			id = SearchAPI.getIndexIDBySiteID(site);
		}

		if (StringUtil.isNotEmpty(startDate) && StringUtil.isEmpty(endDate)) {
			endDate = "2099-01-01";
		}
		if (StringUtil.isNotEmpty(endDate) && StringUtil.isEmpty(startDate)) {
			startDate = "1900-01-01";
		}
		if (StringUtil.isNotEmpty(startDate)) {
			sps.setDateRange("PublishDate", startDate, endDate);
		}
		if (StringUtil.isNotEmpty(catalog)) {
			sps.addLeftLikeField("CatalogInnerCode", catalog, true);
		}
		if (StringUtil.isNotEmpty(title)) {
			sps.addFulltextField("Title", title);
		}
		if (StringUtil.isNotEmpty(content)) {
			sps.addFulltextField("Content", content);
		}
		if (StringUtil.isNotEmpty(keyword)) {
			sps.addFulltextField("Title", keyword, false);// �������е���ǰ��
			sps.addFulltextField("Content", keyword, false);
			sps.addFulltextField("_Keyword", keyword, true);
		}
		if (StringUtil.isNotEmpty(orderField)) {
			boolean isDesc = "true".equals(descFlag);
			sps.setSortField(orderField, SortField.STRING, isDesc);
		}
		if (StringUtil.isNotEmpty(author)) {
			sps.addEqualField("Author", author);
		}
		if (StringUtil.isNotEmpty(page)) {
			sps.setPageIndex(Integer.parseInt(page) - 1);
		}
		if (StringUtil.isNotEmpty(size)) {
			sps.setPageSize(Integer.parseInt(size));
		}
		if (StringUtil.isEmpty(id)) {
			SearchResult sr = new SearchResult();
			sr.Data = new DataTable();
			return sr;
		}

		sps.setIndexID(Long.parseLong(id));
		return ArticleIndexer.search(sps);
	}

	public static void main(String[] args) {
		// String file = Config.getContextRealPath() + "WEB-INF/data/index/" +
		// 12 + "/time.lock";
		// FileUtil.delete(file);
		// SearchAPI.update(12);
		//
		// long t = System.currentTimeMillis();
		// for (int i = 0; i < 100; i++) {
		// Mapx map = new Mapx();
		// map.put("id", "12");
		// // map.put("time", "all");
		// // map.put("catalog", "2049");
		// map.put("page", "0");
		// map.put("size", "10");
		// // map.put("orderfield", "PublishDate");
		// // map.put("descflag", "true");
		// map.put("keyword", "���� ���� ");
		// map.put("ArticleImage", num)
		// //SearchResult sr = advanceSearch(map);
		// }

		SearchParameters sps = new SearchParameters();
		sps.setIndexID(66);
		// sps.addLikeField("ArticleImage", "upload");
		// sps.addEqualField("ID", "224792");
		sps.addFulltextField("Title", "�Ƽ�");
		SearchResult sr = ArticleIndexer.search(sps);
		System.out.println(sr.Data);
	}

}
