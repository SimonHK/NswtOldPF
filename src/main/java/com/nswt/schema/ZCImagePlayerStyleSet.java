package com.nswt.schema;

import com.nswt.framework.orm.SchemaSet;

public class ZCImagePlayerStyleSet extends SchemaSet {
	public ZCImagePlayerStyleSet() {
		this(10,0);
	}

	public ZCImagePlayerStyleSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCImagePlayerStyleSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCImagePlayerStyleSchema._TableCode;
		Columns = ZCImagePlayerStyleSchema._Columns;
		NameSpace = ZCImagePlayerStyleSchema._NameSpace;
		InsertAllSQL = ZCImagePlayerStyleSchema._InsertAllSQL;
		UpdateAllSQL = ZCImagePlayerStyleSchema._UpdateAllSQL;
		FillAllSQL = ZCImagePlayerStyleSchema._FillAllSQL;
		DeleteSQL = ZCImagePlayerStyleSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCImagePlayerStyleSet();
	}

	public boolean add(ZCImagePlayerStyleSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCImagePlayerStyleSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCImagePlayerStyleSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCImagePlayerStyleSchema get(int index) {
		ZCImagePlayerStyleSchema tSchema = (ZCImagePlayerStyleSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCImagePlayerStyleSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCImagePlayerStyleSet aSet) {
		return super.set(aSet);
	}
}
 