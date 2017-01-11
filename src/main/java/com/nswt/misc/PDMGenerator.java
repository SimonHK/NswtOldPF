package com.nswt.misc;

import com.nswt.framework.orm.SchemaGenerator;

/**
 * @Author NSWT
 * @Date 2009-4-23
 * @Mail nswt@nswt.com.cn
 */
public class PDMGenerator {

	public static void main(String[] args) {
//		SchemaGenerator.execute("com.nswt.schema");
		SchemaGenerator.dealFile("DB\\nswtp.pdm","com.nswt.schema","Java\\com\\nswt\\schema");
	}

}
