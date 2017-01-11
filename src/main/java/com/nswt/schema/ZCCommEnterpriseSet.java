package com.nswt.schema;

import com.nswt.framework.orm.SchemaSet;

public class ZCCommEnterpriseSet extends SchemaSet {
	public ZCCommEnterpriseSet() {
		this(10,0);
	}

	public ZCCommEnterpriseSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCCommEnterpriseSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCCommEnterpriseSchema._TableCode;
		Columns = ZCCommEnterpriseSchema._Columns;
		NameSpace = ZCCommEnterpriseSchema._NameSpace;
		InsertAllSQL = ZCCommEnterpriseSchema._InsertAllSQL;
		UpdateAllSQL = ZCCommEnterpriseSchema._UpdateAllSQL;
		FillAllSQL = ZCCommEnterpriseSchema._FillAllSQL;
		DeleteSQL = ZCCommEnterpriseSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCCommEnterpriseSet();
	}

	public boolean add(ZCCommEnterpriseSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCCommEnterpriseSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCCommEnterpriseSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCCommEnterpriseSchema get(int index) {
		ZCCommEnterpriseSchema tSchema = (ZCCommEnterpriseSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCCommEnterpriseSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCCommEnterpriseSet aSet) {
		return super.set(aSet);
	}
}
 