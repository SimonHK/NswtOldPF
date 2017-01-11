package com.nswt.schema;

import com.nswt.framework.orm.SchemaSet;

public class BZCImagePlayerStyleSet extends SchemaSet {
	public BZCImagePlayerStyleSet() {
		this(10,0);
	}

	public BZCImagePlayerStyleSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCImagePlayerStyleSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCImagePlayerStyleSchema._TableCode;
		Columns = BZCImagePlayerStyleSchema._Columns;
		NameSpace = BZCImagePlayerStyleSchema._NameSpace;
		InsertAllSQL = BZCImagePlayerStyleSchema._InsertAllSQL;
		UpdateAllSQL = BZCImagePlayerStyleSchema._UpdateAllSQL;
		FillAllSQL = BZCImagePlayerStyleSchema._FillAllSQL;
		DeleteSQL = BZCImagePlayerStyleSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCImagePlayerStyleSet();
	}

	public boolean add(BZCImagePlayerStyleSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCImagePlayerStyleSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCImagePlayerStyleSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCImagePlayerStyleSchema get(int index) {
		BZCImagePlayerStyleSchema tSchema = (BZCImagePlayerStyleSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCImagePlayerStyleSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCImagePlayerStyleSet aSet) {
		return super.set(aSet);
	}
}
 