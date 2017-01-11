package com.nswt.schema;

import com.nswt.framework.orm.SchemaSet;

public class ZCVoteRecorderSet extends SchemaSet {
	public ZCVoteRecorderSet() {
		this(10,0);
	}

	public ZCVoteRecorderSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCVoteRecorderSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCVoteRecorderSchema._TableCode;
		Columns = ZCVoteRecorderSchema._Columns;
		NameSpace = ZCVoteRecorderSchema._NameSpace;
		InsertAllSQL = ZCVoteRecorderSchema._InsertAllSQL;
		UpdateAllSQL = ZCVoteRecorderSchema._UpdateAllSQL;
		FillAllSQL = ZCVoteRecorderSchema._FillAllSQL;
		DeleteSQL = ZCVoteRecorderSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCVoteRecorderSet();
	}

	public boolean add(ZCVoteRecorderSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCVoteRecorderSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCVoteRecorderSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCVoteRecorderSchema get(int index) {
		ZCVoteRecorderSchema tSchema = (ZCVoteRecorderSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCVoteRecorderSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCVoteRecorderSet aSet) {
		return super.set(aSet);
	}
}
 