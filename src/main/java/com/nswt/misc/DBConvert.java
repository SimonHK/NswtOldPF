package com.nswt.misc;

import com.nswt.framework.Config;
import com.nswt.framework.orm.DBExport;

/**
 * @Author NSWT
 * @Date 2009-5-26
 * @Mail nswt@nswt.com.cn
 */
public class DBConvert {

	public static void main(String[] args) {
		new DBExport().exportDB(Config.getContextRealPath() + "WEB-INF/data/installer/Install.dat");
		// new DBImport().importDB(Config.getContextRealPath() +
		// "WEB-INF/data/installer/Install.dat");
	}

}
