/**
 * 
 */
package com.nswt.framework.controls;

import com.nswt.framework.Ajax;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.utility.Mapx;

/**
 * ÿ����Ŀ��Ҫ��һ����ʵ��CodeSource,��������������framework.xml
 * 
 */
public abstract class CodeSource extends Ajax {
	public abstract DataTable getCodeData(String codeType,
			Mapx params);
}
