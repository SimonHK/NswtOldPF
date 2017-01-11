package com.nswt.search.index;

import java.io.File;
import java.util.Date;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.FSDirectory;

import com.nswt.framework.Config;
import com.nswt.framework.utility.DateUtil;
import com.nswt.framework.utility.FileUtil;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.StringUtil;
import com.nswt.search.NswtAnalyzer;
import com.nswt.search.NswtSimilarity;

/**
 * @Author NSWT
 * @Date 2008-12-12
 * @Mail nswt@nswt.com.cn
 */
public abstract class Indexer {
	protected String indexPath;

	protected IndexWriter writer;

	protected Date lastDate;

	protected Date nextLastDate;// 下一次以此时间为依据扫描数据改动

	protected int threadCount = 8;

	protected int aliveThreadCount;

	private boolean updateFlag;

	private boolean singleThreadMode = true;

	private static Object mutex = new Object();

	public void setPath(String path) {
		indexPath = path;
		try {
			if (!new File(path).exists()) {
				nextLastDate = lastDate = DateUtil.parse("1970-01-01");
				updateFlag = false;
			} else {
				if (!new File(path + "/time.lock").exists()) {
					FileUtil.deleteFromDir(path);
					nextLastDate = lastDate = new Date();
					updateFlag = false;
				} else {
					// 存在文件内容为空的情况
					String lastDateStr = FileUtil.readText(path + "/time.lock").trim();
					if (StringUtil.isEmpty(lastDateStr)) {
						nextLastDate = lastDate = new Date();
					} else {
						nextLastDate = lastDate = DateUtil.parseDateTime(lastDateStr);
					}
					updateFlag = true;
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void prepareWriter() throws Exception {
		// 尝试清除write.lock
		try {
			if (new File(indexPath + "/write.lock").exists()) {
				FileUtil.delete(indexPath + "/write.lock");
			}
		} catch (Exception e) {
		}
		if (updateFlag) {
			writer = new IndexWriter(FSDirectory.open(new File(indexPath)), new NswtAnalyzer(), false,
					IndexWriter.MaxFieldLength.UNLIMITED);
		} else {
			writer = new IndexWriter(FSDirectory.open(new File(indexPath)), new NswtAnalyzer(), true,
					IndexWriter.MaxFieldLength.UNLIMITED);
		}
		writer.setSimilarity(new NswtSimilarity());
		// writer.setMergeFactor(100);
		if ("true".equals(Config.getValue("App.MinimalMemory"))) {
			writer.setMaxBufferedDocs(10);
		} else {
			writer.setMaxBufferedDocs(50);
		}
	}

	public void releaseWriter() {
		try {
			if (writer != null) {
				if (new File(indexPath + "/optimize.lock").exists()) {
					String lastDateStr = FileUtil.readText(indexPath + "/optimize.lock").trim();
					if (StringUtil.isNotEmpty(lastDateStr)) {
						Date date = DateUtil.parseDateTime(lastDateStr);
						if (date.getTime() - System.currentTimeMillis() >= 24 * 60 * 1000) {
							writer.optimize(true);// 间隔一天才优化
						}
					} else {
						writer.optimize(true);
					}
				} else {
					writer.optimize(true);
				}
				writer.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 开始索引
	 */
	public void start() {
		synchronized (mutex) {// 同一时间只允许有一个实例在运行,以免发生索引冲突
			if (StringUtil.isEmpty(indexPath)) {
				throw new RuntimeException("没有指定索引存放的目录!");
			}
			LogUtil.getLogger().info("开始建立索引" + indexPath + "......");
			long startTime = System.currentTimeMillis();
			try {
				prepareWriter();
				if (this.singleThreadMode) {
					threadCount = 1;
				}
				aliveThreadCount = threadCount;
				for (int i = 0; i < threadCount; i++) {
					IndexerThread t = new IndexerThread();
					t.setIndexer(this);
					t.start();
				}
				try {
					while (aliveThreadCount != 0) {
						Thread.sleep(10);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (nextLastDate == null || lastDate == nextLastDate) {
					nextLastDate = new Date();
				}
				FileUtil.writeText(indexPath + "/time.lock", DateUtil.toDateTimeString(nextLastDate));
				LogUtil.getLogger().info("建立索引共耗时 " + (System.currentTimeMillis() - startTime) + " 毫秒.");
			} catch (Throwable e) {
				e.printStackTrace();
				LogUtil.getLogger().warn("建立索引失败," + indexPath);
			} finally {
				releaseWriter();
			}
		}
	}

	public abstract void create();

	public abstract void update();

	protected void addDocument(Document doc) throws Exception {
		writer.addDocument(doc);
	}

	public int getThreadCount() {
		return threadCount;
	}

	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}

	public Date getLastDate() {
		return lastDate;
	}

	public boolean isUpdateFlag() {
		return updateFlag;
	}

	public void setUpdateFlag(boolean updateFlag) {
		this.updateFlag = updateFlag;
	}

	public boolean isSingleThreadMode() {
		return singleThreadMode;
	}

	public void setSingleThreadMode(boolean singleThreadMode) {
		this.singleThreadMode = singleThreadMode;
	}
}
