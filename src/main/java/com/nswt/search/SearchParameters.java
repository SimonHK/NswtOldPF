package com.nswt.search;

import java.util.ArrayList;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeFilter;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.search.BooleanClause.Occur;
import org.wltea.analyzer.lucene.IKQueryParser;

import com.nswt.framework.utility.LogUtil;

/**
 * ���� : 2009-10-28 <br>
 * ����: NSWT <br>
 * ���䣺nswt@nswt.com.cn <br>
 */
public class SearchParameters {
	private BooleanQuery booleanQuery = new BooleanQuery();

	private TermRangeFilter filter = null;

	private int pageSize = 10;

	private int pageIndex = 0;

	private long indexID;

	private ArrayList sortFieldList = new ArrayList(4);

	/**
	 * ���һ��ȫ�ļ�����������ָ���ֶ���ִ��ȫ�ļ���
	 */
	public void addFulltextField(String field, String query) {
		addFulltextField(field, query, true);
	}

	/**
	 * ���һ��ȫ�ļ�����������ָ���ֶ���ִ��ȫ�ļ���
	 */
	public void addFulltextField(String field, String query, boolean isMust) {
		try {
			Query q = IKQueryParser.parse(field.toUpperCase(), query);
			if (q == null) {
				LogUtil.info("�����������Ĺؼ���:" + query);
				return;
			}
			if (field.equalsIgnoreCase("Title")) {
				q.setBoost(10000);// �����еĹؼ��ַ���Ӧ�ýϸ�
			}
			booleanQuery.add(q, isMust ? Occur.MUST : Occur.SHOULD);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addNotEqualField(String field, String query) {
		TermQuery q = new TermQuery(new Term(field.toUpperCase(), query));
		booleanQuery.add(q, Occur.MUST_NOT);
	}

	/**
	 * ���һ����ѯ������������SQL�е�field like '%query'<br>
	 * ���ʹ�ñ���������ע������������ܡ�
	 */
	public void addRightLikeField(String field, String query) {
		addRightLikeField(field, query, true);
	}

	/**
	 * ���һ����ѯ������������SQL�е�field like '%query'<br>
	 * ���ʹ�ñ���������ע������������ܡ�
	 */
	public void addRightLikeField(String field, String query, boolean isMust) {
		WildcardQuery q = new WildcardQuery(new Term(field.toUpperCase(), "*" + query));
		booleanQuery.add(q, isMust ? Occur.MUST : Occur.SHOULD);
	}

	/**
	 * ���һ����ѯ������������SQL�е�field like 'query%'
	 */
	public void addLeftLikeField(String field, String query) {
		addLeftLikeField(field, query, true);
	}

	/**
	 * ���һ����ѯ������������SQL�е�field like 'query%'
	 */
	public void addLeftLikeField(String field, String query, boolean isMust) {
		PrefixQuery q = new PrefixQuery(new Term(field.toUpperCase(), query));
		booleanQuery.add(q, isMust ? Occur.MUST : Occur.SHOULD);
	}

	/**
	 * ���һ����ѯ������������SQL�е�field like '%query%'<br>
	 * ���ʹ�ñ���������ע������������ܡ�
	 */
	public void addLikeField(String field, String query) {
		addLikeField(field, query, true);
	}

	/**
	 * ���һ����ѯ������������SQL�е�field like '%query%'<br>
	 * ���ʹ�ñ���������ע������������ܡ�
	 */
	public void addLikeField(String field, String query, boolean isMust) {
		WildcardQuery q = new WildcardQuery(new Term(field.toUpperCase(), "*" + query + "*"));
		booleanQuery.add(q, isMust ? Occur.MUST : Occur.SHOULD);
	}

	/**
	 * ���һ����ѯ������Ҫ���ֶ�ֵ��ȫ����ָ��ֵ
	 */
	public void addEqualField(String field, String query) {
		addEqualField(field, query, true);
	}

	/**
	 * ���һ����ѯ������Ҫ���ֶ�ֵ��ȫ����ָ��ֵ
	 */
	public void addEqualField(String field, String query, boolean isMust) {
		TermQuery q = new TermQuery(new Term(field.toUpperCase(), query));
		booleanQuery.add(q, isMust ? Occur.MUST : Occur.SHOULD);
	}

	/**
	 * ���һ����ѯ������Ҫ���ֶ�ֵ��ָ���ķ�Χ��,�������������Χ�ı߽�ֵ
	 */
	public void addRangeField(String field, String valueBegin, String valueEnd) {
		addRangeField(field, valueBegin, valueEnd, true);
	}

	/**
	 * ���һ����ѯ������Ҫ���ֶ�ֵ��ָ���ķ�Χ��,�������������Χ�ı߽�ֵ
	 */
	public void addRangeField(String field, String valueBegin, String valueEnd, boolean isMust) {
		TermRangeQuery q = new TermRangeQuery(field, valueBegin, valueEnd, true, true);
		booleanQuery.add(q, isMust ? Occur.MUST : Occur.SHOULD);
	}

	/**
	 * ���һ���Զ���Ĳ�ѯ����
	 */
	public void addQuery(Query q) {
		addQuery(q, true);
	}

	/**
	 * ���һ���Զ���Ĳ�ѯ����
	 */
	public void addQuery(Query q, boolean isMust) {
		booleanQuery.add(q, isMust ? Occur.MUST : Occur.SHOULD);
	}

	/**
	 * ��ָ���ֶ�����
	 * 
	 * @deprecated
	 */
	public void setSortField(String field, int dataType, boolean descFlag) {
		sortFieldList.clear();
		sortFieldList.add(new SortField(field.toUpperCase(), dataType, descFlag));
	}

	/**
	 * ���һ�������ֶ�
	 */
	public void addSortField(String field, int dataType, boolean descFlag) {
		sortFieldList.add(new SortField(field.toUpperCase(), dataType, descFlag));
	}

	/**
	 * �趨ָ�������ֶε����䣬������ΪLucene�ڴ������ڵ�RangeQueryʱ���������������ȡ�ı�ͨ��ʩ
	 */
	public void setDateRange(String field, String startDate, String endDate) {
		filter = new TermRangeFilter(field.toUpperCase(), startDate, endDate, true, true);
	}

	public BooleanQuery getBooleanQuery() {
		return booleanQuery;
	}

	public TermRangeFilter getDateRangeFilter() {
		return filter;
	}

	public Sort getSort() {
		if (sortFieldList.size() == 0) {
			return null;
		}
		Sort sort = new Sort();
		SortField[] sfs = new SortField[sortFieldList.size()];
		for (int i = 0; i < sortFieldList.size(); i++) {
			sfs[i] = (SortField) sortFieldList.get(i);
		}
		sort.setSort(sfs);
		return sort;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		if (pageIndex < 0) {
			pageIndex = 0;
		}
		this.pageIndex = pageIndex;
	}

	public long getIndexID() {
		return indexID;
	}

	public void setIndexID(long indexID) {
		this.indexID = indexID;
	}
}
