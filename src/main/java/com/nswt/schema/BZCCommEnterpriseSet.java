package com.nswt.schema;

import com.nswt.framework.orm.SchemaSet;

public class BZCCommEnterpriseSet extends SchemaSet {
	public BZCCommEnterpriseSet() {
		this(10,0);
	}

	public BZCCommEnterpriseSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCCommEnterpriseSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCCommEnterpriseSchema._TableCode;
		Columns = BZCCommEnterpriseSchema._Columns;
		NameSpace = BZCCommEnterpriseSchema._NameSpace;
		InsertAllSQL = BZCCommEnterpriseSchema._InsertAllSQL;
		UpdateAllSQL = BZCCommEnterpriseSchema._UpdateAllSQL;
		FillAllSQL = BZCCommEnterpriseSchema._FillAllSQL;
		DeleteSQL = BZCCommEnterpriseSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCCommEnterpriseSet();
	}

	public boolean add(BZCCommEnterpriseSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCCommEnterpriseSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCCommEnterpriseSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCCommEnterpriseSchema get(int index) {
		BZCCommEnterpriseSchema tSchema = (BZCCommEnterpriseSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCCommEnterpriseSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCCommEnterpriseSet aSet) {
		return super.set(aSet);
	}
}
 