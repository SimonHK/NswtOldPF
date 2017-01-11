package com.nswt.schema;

import com.nswt.framework.orm.SchemaSet;

public class BZCFtpGatherSet extends SchemaSet {
	public BZCFtpGatherSet() {
		this(10,0);
	}

	public BZCFtpGatherSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCFtpGatherSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCFtpGatherSchema._TableCode;
		Columns = BZCFtpGatherSchema._Columns;
		NameSpace = BZCFtpGatherSchema._NameSpace;
		InsertAllSQL = BZCFtpGatherSchema._InsertAllSQL;
		UpdateAllSQL = BZCFtpGatherSchema._UpdateAllSQL;
		FillAllSQL = BZCFtpGatherSchema._FillAllSQL;
		DeleteSQL = BZCFtpGatherSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCFtpGatherSet();
	}

	public boolean add(BZCFtpGatherSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCFtpGatherSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCFtpGatherSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCFtpGatherSchema get(int index) {
		BZCFtpGatherSchema tSchema = (BZCFtpGatherSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCFtpGatherSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCFtpGatherSet aSet) {
		return super.set(aSet);
	}
}
 