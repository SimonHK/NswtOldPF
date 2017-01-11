package com.nswt.search;

import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;

/**
 * @Author NSWT
 * @Date 2007-9-13
 * @Mail nswt@nswt.com.cn
 */
public class NswtAnalyzer extends Analyzer {
	public TokenStream tokenStream(String fieldName, Reader reader) {
		return new NswtTokenizer(fieldName, reader);
	}
}
