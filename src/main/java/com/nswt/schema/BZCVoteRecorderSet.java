package com.nswt.schema;

import com.nswt.framework.orm.SchemaSet;

public class BZCVoteRecorderSet extends SchemaSet {
	public BZCVoteRecorderSet() {
		this(10,0);
	}

	public BZCVoteRecorderSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCVoteRecorderSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCVoteRecorderSchema._TableCode;
		Columns = BZCVoteRecorderSchema._Columns;
		NameSpace = BZCVoteRecorderSchema._NameSpace;
		InsertAllSQL = BZCVoteRecorderSchema._InsertAllSQL;
		UpdateAllSQL = BZCVoteRecorderSchema._UpdateAllSQL;
		FillAllSQL = BZCVoteRecorderSchema._FillAllSQL;
		DeleteSQL = BZCVoteRecorderSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCVoteRecorderSet();
	}

	public boolean add(BZCVoteRecorderSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCVoteRecorderSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCVoteRecorderSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCVoteRecorderSchema get(int index) {
		BZCVoteRecorderSchema tSchema = (BZCVoteRecorderSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCVoteRecorderSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCVoteRecorderSet aSet) {
		return super.set(aSet);
	}
}
 