package com.nswt.search;

import java.io.IOException;
import java.io.Reader;

import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 * 
 * @Author NSWT
 * @Date 2007-9-13
 * @Mail nswt@nswt.com.cn
 */
public class NswtTokenizer extends Tokenizer {
	public TokenStream st;

	public Token currentToken;

	public boolean splitingFlag = false;// ��־�Ƿ����ڲ��

	public int offSet;

	public StringBuffer sb = new StringBuffer();

	public NswtTokenizer(String fieldName, Reader reader) {
		st = new IKAnalyzer().tokenStream(fieldName, reader);
		this.input = reader;
	}

	public Token next() throws IOException {
		if (!splitingFlag) {
			currentToken = st.next();
			if (currentToken == null) {
				splitingFlag = true;
			} else {
				sb.append(new String(currentToken.termBuffer(), 0, currentToken.termLength()));
				return currentToken;
			}
		}
		if (splitingFlag) {
			if (offSet < sb.length()) {
				return new Token(sb.substring(offSet, offSet + 1), offSet, ++offSet);
			}
		}
		return null;
	}
}
