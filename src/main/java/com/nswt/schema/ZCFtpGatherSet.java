package com.nswt.schema;

import com.nswt.framework.orm.SchemaSet;

public class ZCFtpGatherSet extends SchemaSet {
	public ZCFtpGatherSet() {
		this(10,0);
	}

	public ZCFtpGatherSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCFtpGatherSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCFtpGatherSchema._TableCode;
		Columns = ZCFtpGatherSchema._Columns;
		NameSpace = ZCFtpGatherSchema._NameSpace;
		InsertAllSQL = ZCFtpGatherSchema._InsertAllSQL;
		UpdateAllSQL = ZCFtpGatherSchema._UpdateAllSQL;
		FillAllSQL = ZCFtpGatherSchema._FillAllSQL;
		DeleteSQL = ZCFtpGatherSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCFtpGatherSet();
	}

	public boolean add(ZCFtpGatherSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCFtpGatherSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCFtpGatherSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCFtpGatherSchema get(int index) {
		ZCFtpGatherSchema tSchema = (ZCFtpGatherSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCFtpGatherSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCFtpGatherSet aSet) {
		return super.set(aSet);
	}
}
 