package com.nswt.search;

import java.util.Date;

import org.apache.lucene.search.SortField;

import com.nswt.cms.api.SearchAPI;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.utility.DateUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;

/**
 * 文章搜索类
 * 
 * @Author 王育春
 * @Date 2008-12-9
 * @Mail nswt@nswt.com.cn
 */
public class ArticleSearcher {
	/**
	 * 普通搜索，只传入站点或索引ID/查询关键字/时间/是否按时间逆序
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
			keyword = query;// 和旧版本保持一致
		}
		String page = map.getString("page");
		String size = map.getString("size");

		if (StringUtil.isEmpty(id)) {
			id = SearchAPI.getIndexIDBySiteID(site);
		}

		// 检索关键字
		if (StringUtil.isNotEmpty(keyword)) {
			sps.addFulltextField("Title", keyword, false);
			sps.addFulltextField("Content", keyword, false);
			sps.addFulltextField("_Keyword", keyword, true);
		}

		// 排序
		if ("time".equalsIgnoreCase(order)) {
			sps.setSortField("PublishDate", SortField.STRING, true);
		}

		// 时间范围
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
			keyword = query;// 和旧版本保持一致
		}
		String page = map.getString("page");
		String size = map.getString("size");


		// 检索关键字
		if (StringUtil.isNotEmpty(keyword)) {
			sps.addFulltextField("Title", keyword, false);
			sps.addFulltextField("Content", keyword, false);
			sps.addFulltextField("_Keyword", keyword, true);
		}

		// 排序
		if ("time".equalsIgnoreCase(order)) {
			sps.setSortField("PublishDate", SortField.STRING, true);
		}

		// 时间范围
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
	 * 标签搜索
	 */
	public static SearchResult tagSearch(Mapx map) {
		SearchParameters sps = new SearchParameters();
		String site = map.getString("site");
		String order = map.getString("order");
		String keyword = map.getString("keyword");
		String query = map.getString("query");
		if (StringUtil.isEmpty(keyword)) {
			keyword = query;// 和旧版本保持一致
		}
		String page = map.getString("page");
		String size = map.getString("size");

		// 检索关键字
		if (StringUtil.isNotEmpty(keyword)) {
			sps.addLikeField("Tag", keyword, false);
		}

		// 排序
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
	 * 高级检索，传入多项搜索条件
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
			keyword = query;// 和旧版本保持一致
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
			sps.addFulltextField("Title", keyword, false);// 标题中有的排前面
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
		// map.put("keyword", "天文 流星 ");
		// map.put("ArticleImage", num)
		// //SearchResult sr = advanceSearch(map);
		// }

		SearchParameters sps = new SearchParameters();
		sps.setIndexID(66);
		// sps.addLikeField("ArticleImage", "upload");
		// sps.addEqualField("ID", "224792");
		sps.addFulltextField("Title", "科技");
		SearchResult sr = ArticleIndexer.search(sps);
		System.out.println(sr.Data);
	}

}
