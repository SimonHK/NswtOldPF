package com.nswt.cms.api;

import com.nswt.framework.data.Transaction;
import com.nswt.framework.orm.Schema;

public interface APIInterface {
	public long insert();
	
	public long insert(Transaction trans);
	
	public boolean update();
	
	public boolean delete();
	
	public boolean setSchema(Schema schema);

}
