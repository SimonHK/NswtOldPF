package com.nswt.search.index;


/**
 * @Author NSWT
 * @Date 2008-12-12
 * @Mail nswt@nswt.com.cn
 */
public class IndexerThread extends Thread {
	private Indexer indexer;

	public void run() {
		try {
			if (indexer.isUpdateFlag()) {
				indexer.update();
			} else {
				indexer.create();
			}
		} finally {
			synchronized (indexer) {
				this.indexer.aliveThreadCount--;
			}
		}
	}

	public Indexer getIndexer() {
		return indexer;
	}

	public void setIndexer(Indexer indexer) {
		this.indexer = indexer;
	}
}
