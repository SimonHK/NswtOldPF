package com.nswt.platform.pub;

import java.util.Date;

import com.nswt.framework.Config;
import com.nswt.framework.data.BlockingTransaction;
import com.nswt.framework.data.DBConnPool;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.extend.IExtendAction;
import com.nswt.framework.orm.UpdateSQLParser;
import com.nswt.framework.utility.FileUtil;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.StringUtil;
import com.nswt.framework.utility.ZipUtil;
import com.nswt.schema.ZDCodeSet;
import com.nswt.schema.ZDConfigSchema;
import com.nswt.schema.ZDIPRangeSet;
import com.nswt.schema.ZDMenuSchema;

/**
 * 日期 : 2010-6-21 <br>
 * 作者: NSWT <br>
 * 邮箱：nswt@nswt.com.cn <br>
 */
public class Patch implements IExtendAction {
	private static Object mutex = new Object();
	private static boolean isPatching = false;

	private synchronized static void checkUpdate() {
		if (!Config.isInstalled) {
			return;
		}
//		CheckMenu.check();
		synchronized (mutex) {
			if (isPatching) {
				return;
			}
			isPatching = true;
		}
		BlockingTransaction tran = null;
		try {
			tran = new BlockingTransaction();
			String version = new QueryBuilder("select Value from ZDConfig where Type=?", "System.Version")
					.executeString();
			float mainVersion = 0;
			float minorVersion = 0;
			if (StringUtil.isEmpty(version)) {
				mainVersion = 1.3f;
				minorVersion = 0.5f;
			} else {
				String[] arr = version.split("\\.");
				mainVersion = Float.parseFloat(arr[0] + "." + arr[1]);
				if (arr.length == 4) {
					minorVersion = Float.parseFloat(arr[2] + "." + arr[3]);
				} else {
					minorVersion = Float.parseFloat(arr[2]);
				}
			}
			if (mainVersion < 1.3f && minorVersion < 1.0f) {// 1.3.1之下需要更新菜单
				updateTo1_3_1(tran);
			}
			if (mainVersion < 1.3f || (mainVersion == 1.3f && minorVersion < 2.0f)) {// 1.3.2需要更新表结构
				updateTo1_3_2(tran);
			}
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (tran != null) {
				tran.rollback();
			}
		} finally {
			isPatching = false;
		}
	}

	public static void updateTo1_3_1(BlockingTransaction tran) {
		try {
			tran.addWithException(new QueryBuilder(
					"INSERT INTO zdconfig  VALUES ('RewriteIndex','是否将站点首页重新生成index.html','N',NULL,NULL,NULL,NULL,NULL,'2010-01-18 14:35:52','admin',NULL,NULL)"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		/* 投票验证码标志 2010-06-18 */
		try {
			tran.addWithException(new QueryBuilder(
					"update zdcode set codevalue ='Y' where codetype = 'VerifyFlag' and parentcode ='VerifyFlag' and codevalue ='S'"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		/* 栏目树设置功能和文章来源管理功能增加数据 2010-06-22 */
		try {
			tran.addWithException(new QueryBuilder(
					"INSERT INTO zdconfig (Type,Name,Value,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('ArticleCatalogLoadType','文章栏目树延迟加载方式','AllChild',NULL,NULL,NULL,NULL,'TreeConfig','2010-06-22 15:03:06','admin',NULL,NULL)"));
			tran.addWithException(new QueryBuilder(
					"INSERT INTO zdconfig (Type,Name,Value,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('ArticleCatalogShowLevel','文章栏目树默认展开层级','2',NULL,NULL,NULL,NULL,'TreeConfig','2010-06-22 15:01:26','admin',NULL,NULL)"));
			tran.addWithException(new QueryBuilder(
					"INSERT INTO zdconfig (Type,Name,Value,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('AttachLibLoadType','附件库栏目树延迟加载方式','AllChild',NULL,NULL,NULL,NULL,'TreeConfig','2010-06-22 15:20:52','admin',NULL,NULL)"));
			tran.addWithException(new QueryBuilder(
					"INSERT INTO zdconfig (Type,Name,Value,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('AttachLibShowLevel','附件库栏目树默认展开层级','2',NULL,NULL,NULL,NULL,'TreeConfig','2010-06-22 15:19:39','admin',NULL,NULL)"));
			tran.addWithException(new QueryBuilder(
					"INSERT INTO zdconfig (Type,Name,Value,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('AudioLibLoadType','音频库栏目树延迟加载方式','AllChild',NULL,NULL,NULL,NULL,'TreeConfig','2010-06-22 15:22:32','admin',NULL,NULL)"));
			tran.addWithException(new QueryBuilder(
					"INSERT INTO zdconfig (Type,Name,Value,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('AudioLibShowLevel','音频库栏目树默认展开层级','2',NULL,NULL,NULL,NULL,'TreeConfig','2010-06-22 15:22:11','admin',NULL,NULL)"));
			tran.addWithException(new QueryBuilder(
					"INSERT INTO zdconfig (Type,Name,Value,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('ImageLibLoadType','图片库栏目树延迟加载方式','AllChild',NULL,NULL,NULL,NULL,'TreeConfig','2010-06-22 15:14:16','admin',NULL,NULL)"));
			tran.addWithException(new QueryBuilder(
					"INSERT INTO zdconfig (Type,Name,Value,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('ImageLibShowLevel','图片库栏目树默认展开层级','2',NULL,NULL,NULL,NULL,'TreeConfig','2010-06-22 15:06:30','admin',NULL,NULL)"));
			tran.addWithException(new QueryBuilder(
					"INSERT INTO zdconfig (Type,Name,Value,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('VideoLibLoadType','视频库栏目树延迟加载方式','AllChild',NULL,NULL,NULL,NULL,'TreeConfig','2010-06-22 15:21:47','admin',NULL,NULL)"));
			tran.addWithException(new QueryBuilder(
					"INSERT INTO zdconfig (Type,Name,Value,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('VideoLibShowLevel','视频库栏目树默认展开层级','2',NULL,NULL,NULL,NULL,'TreeConfig','2010-06-22 15:21:19','admin',NULL,NULL)"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		/** 将article的prop字段转移回pageTitle 2010-06-23 */
		try {
			tran.addWithException(new QueryBuilder("update zcarticle set pageTitle=prop1 where prop1 is not null"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		/* 将统计分析中区域统计单立出来 2010-06-23 by wyuch */
		try {
			tran.addWithException(new QueryBuilder(
					"update ZCStatItem set Type='District',SubType='PV' where Type='Client' and SubType='District'"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 增加工作台配置的菜单
		ZDMenuSchema menu = new ZDMenuSchema();
		menu.setID(NoUtil.getMaxID("MenuID"));
		menu.setParentID(122);
		menu.setType("2");
		menu.setName("工作台配置");
		menu.setURL("Site/WorkBenchConfig.jsp");
		menu.setVisiable("Y");
		menu.setIcon("Icons/icon001a7.gif");
		menu.setOrderFlag("14");
		menu.setAddTime(new Date());
		menu.setAddUser("SYSTEM");
		tran.add(menu, Transaction.INSERT);

		// 加入新的IPRanges和ArticleRefer
		tran.add(new QueryBuilder("delete from ZDCode where CodeType=?", "ArticleRefer"));
		byte[] bs = FileUtil.readByte(Config.getContextRealPath() + "WEB-INF/data/installer/1.3.1.ArticleRefer.dat");
		bs = ZipUtil.unzip(bs);
		ZDCodeSet codeSet = (ZDCodeSet) FileUtil.unserialize(bs);
		tran.add(codeSet, Transaction.INSERT);
		tran.add(new QueryBuilder("delete from ZDIPRange"));

		bs = FileUtil.readByte(Config.getContextRealPath() + "WEB-INF/data/installer/1.3.1.IPRanges.dat");
		bs = ZipUtil.unzip(bs);
		ZDIPRangeSet ipSet = (ZDIPRangeSet) FileUtil.unserialize(bs);
		tran.add(ipSet, Transaction.INSERT);

		// 更新会员配置菜单
		try {
			tran.addWithException(new QueryBuilder(
					"UPDATE zdmenu set Name = '会员配置',URL='DataService/MemberConfig.jsp',Icon='Icons/icon025a6.gif' where Name = '会员等级'"));
			tran.addWithException(new QueryBuilder(
					"INSERT INTO zdmemberlevel (ID,Name,Type,Discount,IsDefault,TreeLevel,Score,IsValidate,AddUser,AddTime,ModifyUser,ModifyTime) VALUES (31,'注册会员','用户',1,'Y',0,0,'Y','admin','2010-06-29 18:50:45',NULL,NULL)"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		tran.add(new QueryBuilder("update ZCCatalog set InnerCode=? where InnerCode=?", "002049000001",
				"000020004900000001"));
		tran.add(new QueryBuilder("update ZCCatalog set InnerCode=? where InnerCode=?", "002049000002",
				"000020004900000002"));
		tran.add(new QueryBuilder("update ZCCatalog set InnerCode=? where InnerCode=?", "002049000003",
				"000020004900000003"));
		tran.add(new QueryBuilder("update ZCCatalog set InnerCode=? where InnerCode=?", "002049000004",
				"000020004900000004"));
		tran.add(new QueryBuilder("update ZCCatalog set InnerCode=? where InnerCode=?", "002049000005",
				"000020004900000005"));
		tran.add(new QueryBuilder("update ZCCatalog set InnerCode=? where InnerCode=?", "002049000006",
				"000020004900000006"));
		tran.add(new QueryBuilder("update ZCCatalog set InnerCode=? where InnerCode=?", "002049000007",
				"000020004900000008"));

		tran.add(new QueryBuilder("update ZCCatalog set InnerCode=? where InnerCode=?", "002164000001",
				"000021006400000001"));
		tran.add(new QueryBuilder("update ZCCatalog set InnerCode=? where InnerCode=?", "002164000002",
				"000021006400000002"));
		tran.add(new QueryBuilder("update ZCCatalog set InnerCode=? where InnerCode=?", "002164000003",
				"000021006400000003"));
		tran.add(new QueryBuilder("update ZCCatalog set InnerCode=? where InnerCode=?", "002164000004",
				"000021006400000004"));
		tran.add(new QueryBuilder("update ZCCatalog set InnerCode=? where InnerCode=?", "002164000005",
				"000021006400000005"));
		tran.add(new QueryBuilder("update ZCCatalog set InnerCode=? where InnerCode=?", "002164000006",
				"000021006400000006"));
		tran.add(new QueryBuilder("update ZCCatalog set InnerCode=? where InnerCode=?", "002164000007",
				"000021006400000007"));

		tran.add(new QueryBuilder(
				"update ZCArticle set CatalogInnerCode=(select InnerCode from ZCCatalog where ID=ZCArticle.CatalogID)"));

		// 更新版本号
		try {
			ZDConfigSchema config = new ZDConfigSchema();
			config.setType("System.Version");
			config.setName("当前版本");
			config.setValue("1.3.1.0");
			config.setAddTime(new Date());
			config.setAddUser("SYSTEM");
			tran.add(config, Transaction.DELETE_AND_INSERT);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void updateTo1_3_2(BlockingTransaction tran) {
		String fileName = Config.getContextRealPath() + "WEB-INF/data/installer/1.3.2.sql";
		UpdateSQLParser usp = new UpdateSQLParser(fileName);
		String[] arr = usp.convertToSQLArray(DBConnPool.getDBConnConfig().DBType);
		for (int i = 0; i < arr.length; i++) {
			String sql = arr[i];
			try {
				if (StringUtil.isNotEmpty(sql) && !sql.startsWith("/*")) {
					tran.addWithException(new QueryBuilder(sql));
				} else {
					LogUtil.info(sql);
				}
			} catch (Exception e) {
				if (!sql.toLowerCase().startsWith("drop")) {
					e.printStackTrace();
				}
			}
		}

		// update ZCStatItem set Item=md5(Item) where Type='URL';
		// update ZCStatItem set Memo=Item where Type='URL';
		// 检查是否已经更新了,对于破坏性的更新SQL，要慎重处理
		QueryBuilder qb = new QueryBuilder("select Item from ZCStatItem where Type='URL'");
		DataTable dt = qb.executeDataTable();
		qb = new QueryBuilder("update ZCStatItem set Item=?,Memo=? where Type='URL' and Item=?");
		qb.setBatchMode(true);
		for (int i = 0; i < dt.getRowCount(); i++) {
			String item = dt.getString(i, "Item");
			String memo = dt.getString(i, "Memo");
			if (StringUtil.isEmpty(memo)) {
				qb.add(StringUtil.md5Hex(item));
				qb.add(item);
				qb.add(item);
				qb.addBatch();
			}
		}
		tran.add(qb);

		// 增加图片播放器样式菜单、从FTP采集菜单
		try {
			ZDMenuSchema menu = new ZDMenuSchema();
			menu.setID(NoUtil.getMaxID("MenuID"));
			menu.setParentID(122);
			menu.setType("2");
			menu.setName("播放器样式");
			menu.setURL("Site/ImagePlayerStyles.jsp");
			menu.setVisiable("Y");
			menu.setIcon("Icons/icon039a6.gif");
			menu.setOrderFlag("16");
			menu.setAddTime(new Date());
			menu.setAddUser("SYSTEM");
			tran.add(menu, Transaction.INSERT);

			menu = new ZDMenuSchema();
			menu.setID(NoUtil.getMaxID("MenuID"));
			menu.setParentID(125);
			menu.setType("2");
			menu.setName("从FTP采集");
			menu.setURL("DataChannel/FromFTP.jsp");
			menu.setVisiable("Y");
			menu.setIcon("Icons/icon019a13.gif");
			menu.setOrderFlag("50");
			menu.setAddTime(new Date());
			menu.setAddUser("SYSTEM");
			tran.add(menu, Transaction.INSERT);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 更新版本号
		try {
			ZDConfigSchema config = new ZDConfigSchema();
			config.setType("System.Version");
			config.setName("当前版本");
			config.setValue("1.3.2.0");
			config.setAddTime(new Date());
			config.setAddUser("SYSTEM");
			tran.add(config, Transaction.DELETE_AND_INSERT);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getTarget() {
		return "Config.AfterInit";
	}

	public String getName() {
		return "升级检查程序";
	}

	public void execute(Object[] args) {
		checkUpdate();
	}
}
