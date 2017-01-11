package com.nswt.search;

import org.apache.lucene.search.Similarity;

/**
 * @Author NSWT
 * @Date 2009-1-13
 * @Mail nswt@nswt.com.cn
 */
public class NswtSimilarity extends Similarity {
	private static final long serialVersionUID = 1L;

	/*
	 * ��ߴ�Ԫ���и��������ƶȱȽ��е�Ȩ��Ӱ�죬�������ж����Ԫ�õ�ƥ��ʱ���ĵ������ƶȽ���� (non-Javadoc)
	 * 
	 * @see org.apache.lucene.search.Similarity#coord(int, int)
	 */
	public float coord(int overlap, int maxOverlap) {
		float overlap2 = (float) Math.pow(2, overlap);
		float maxOverlap2 = (float) Math.pow(2, maxOverlap);
		return (overlap2 / maxOverlap2);
	}

	public float idf(int docFreq, int numDocs) {
		return (float) (Math.log(numDocs / (double) (docFreq + 1)) + 1.0);
	}

	/**
	 * �ı����Ȳ�Ӧ��Ӱ������
	 */
	public float lengthNorm(String fieldName, int numTokens) {
		return 1;
	}

	public float queryNorm(float sumOfSquaredWeights) {
		return (float) (1.0 / Math.sqrt(sumOfSquaredWeights));
	}

	public float sloppyFreq(int distance) {
		return 1.0f / (distance + 1);
	}

	public float tf(float freq) {
		return (float) Math.sqrt(freq);
	}

}
