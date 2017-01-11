package com.nswt.cms.api;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import com.nswt.cms.site.Catalog;
import com.nswt.framework.controls.DataGridAction;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.ServletUtil;
import com.nswt.framework.utility.StringUtil;
import com.nswt.schema.ZCFullTextSchema;
import com.nswt.schema.ZCFullTextSet;
import com.nswt.search.ArticleIndexer;
import com.nswt.search.ArticleSearcher;
import com.nswt.search.ResourceIndexer;
import com.nswt.search.SearchParameters;
import com.nswt.search.SearchResult;

/**
 * @Author NSWT
 * @Date 2008-6-24
 * @Mail nswt@nswt.com.cn
 */
public class SearchAPI {
	private static Mapx siteMap = new Mapx();

	public static String getIndexIDBySiteID(String site) {
		if (StringUtil.isEmpty(site) || !StringUtil.isDigit(site)) {
			return null;
		} else {
			String id = siteMap.getString(site);
			if (id == null) {
				ZCFullTextSchema ft = new ZCFullTextSchema();
				ft.setSiteID(site);
				ft.setProp1("AutoIndex");
				ZCFullTextSet set = ft.query();
				if (set.size() > 0) {
					id = "" + set.get(0).getID();
					siteMap.put(site, id);
				} else {
					return null;
				}
			}
			return id;
		}
	}

	public static void update(long id) {
		ZCFullTextSchema ft = new ZCFullTextSchema();
		ft.setID(id);
		if (ft.fill()) {
			if (ft.getType().equals("Article")) {
				updateArticleIndex(id);
			} else {
				updateResourceIndex(id);
			}
		} else {
			LogUtil.getLogger().warn("没有ID=" + id + "的全文检索!");
		}
	}

	/**
	 * 更新文章索引
	 */
	public static void updateArticleIndex(long id) {
		ArticleIndexer ai = new ArticleIndexer(id);
		ai.start();
	}

	/**
	 * 更新资源文件索引
	 */
	public static void updateResourceIndex(long id) {
		ResourceIndexer ri = new ResourceIndexer(id);
		ri.start();
	}

	public static SearchResult searchArticle(long id, String keyword, int pageSize, int pageIndex) {
		SearchParameters sps = new SearchParameters();
		sps.addFulltextField("_KeyWord", keyword);
		sps.setPageIndex(pageIndex);
		sps.setPageSize(pageSize);
		sps.setIndexID(id);
		return ArticleIndexer.search(sps);
	}

	public static SearchResult searchArticle(String keyword, int pageSize, int pageIndex, Mapx map) {
		map.put("keyword", keyword);
		map.put("page", "" + pageIndex);
		map.put("size", "" + pageSize);
		return ArticleSearcher.search(map);
	}

	public static SearchResult searchArticle(long id, String keyword, int pageSize, int pageIndex, String catalog) {
		Mapx map = new Mapx();
		map.put("catalog", catalog);
		map.put("keyword", keyword);
		map.put("page", "" + pageIndex);
		map.put("size", "" + pageSize);
		return ArticleSearcher.search(map);
	}

	public static SearchResult search(long id, String keyword, int pageSize, int pageIndex, Mapx map) {
		map.put("keyword", keyword);
		map.put("page", "" + pageIndex);
		map.put("size", "" + pageSize);
		return ArticleSearcher.search(map);
	}

	public static String getPageURL(Mapx map, int pageNo) {
		map.put("page", "" + pageNo);
		return ServletUtil.getQueryStringFromMap(map, true);
	}

	public static String getURL(Mapx map, String text, String type, String value) {
		Mapx map2 = (Mapx) map.clone();// 必须要先克隆，否则会影响分页
		if (value != null && value.equals(map2.get(type))) {
			return " ·" + text;
		} else {
			map2.put(type, value);
			return " ·<a href='" + ServletUtil.getQueryStringFromMap(map2, true) + "'>" + text + "</a>";
		}
	}

	public static String getParameter(HttpServletRequest request, String key) {
		String queryString = request.getQueryString();
		return getParameter(queryString, key);
	}

	public static String getParameter(String url, String key) {
		if (url.indexOf('?') > 0) {
			url = url.substring(url.indexOf('?') + 1);
		}
		LogUtil.info("搜索时输入的URL：" + url);
		Mapx map = StringUtil.splitToMapxNew(url, "&", "=");
		String keyword = map.getString(key);
		if (StringUtil.isNotEmpty(keyword)) {
			if (keyword.indexOf('%') < 0) {
				return keyword;
			}
			if (keyword.startsWith("%00")) {
				keyword = keyword.substring(3);
				keyword = StringUtil.urlDecode(keyword, "UTF-8");
				return keyword;
			}
			if (keyword.indexOf("%") >= 0) {
				keyword = StringUtil.replaceEx(keyword, "?", "");
				keyword = StringUtil.replaceEx(keyword, "+", "%20");
				// 必须逐字判断，有可能有未编码的字符和编码的字符混排的情况，例如<script>alert(1);</script>
				StringBuffer sb = new StringBuffer();
				int escapeIndex = -1;
				for (int i = 0; i < keyword.length(); i++) {
					char c = keyword.charAt(i);
					if (c == '%') {
						escapeIndex = i;
					} else {
						if (escapeIndex >= 0 && i - escapeIndex == 3) {
							if (c == '%') {
								escapeIndex = i;
							} else {
								escapeIndex = -1;
							}
						}
					}
					if (escapeIndex < 0) {
						sb.append("%" + StringUtil.hexEncode(new byte[] { (byte) c }));
					} else {
						sb.append(c);
					}
				}
				keyword = sb.toString();
				byte[] bs = StringUtil.hexDecode(StringUtil.replaceEx(keyword, "%", ""));
				try {
					String result = null;
					if (bs.length >= 3 && StringUtil.isUTF8(bs)) {
						result = new String(bs, "UTF-8");
						if (result.indexOf("?") >= 0) {
							result = new String(bs, "GBK");
						}
					} else {
						result = new String(bs, "GBK");
						if (result.indexOf("?") >= 0) {
							result = new String(bs, "UTF-8");
						}
					}
					return result;
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			return keyword;
		}
		return null;
	}

	public static String showCatalogList(String siteID, String catalogInnerCode) {
		if (StringUtil.isEmpty(siteID)) {
			return "";
		}
		DataTable dt = new QueryBuilder(
				"select ID,Name,ParentID,TreeLevel,InnerCode from ZCCatalog where SiteID=? and Type=? order by orderflag,innercode",
				siteID, Catalog.CATALOG).executeDataTable();
		dt = DataGridAction.sortTreeDataTable(dt, "ID", "ParentID");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < dt.getRowCount(); i++) {
			String prefix = "";
			for (int j = 1; j < dt.getInt(i, "TreeLevel"); j++) {
				prefix += "　";
			}
			String name = prefix + dt.getString(i, "Name");
			String innerCode = dt.getString(i, "InnerCode");
			String checked = "";
			if (innerCode.equals(catalogInnerCode)) {
				checked = "selected";
			}
			sb.append("<option value=\"" + innerCode + "\" " + checked + ">" + name + "</option>");
		}
		return sb.toString();
	}
}
