package com.nswt.datachannel;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;

import org.apache.commons.lang.ArrayUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.nswt.cms.pub.CatalogUtil;
import com.nswt.framework.Config;
import com.nswt.framework.Constant;
import com.nswt.framework.Page;
import com.nswt.framework.User;
import com.nswt.framework.controls.DataGridAction;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.messages.LongTimeTask;
import com.nswt.framework.utility.Errorx;
import com.nswt.framework.utility.HtmlUtil;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.Application;
import com.nswt.platform.pub.NoUtil;
import com.nswt.schema.ZCWebGatherSchema;
import com.nswt.schema.ZCWebGatherSet;
import com.nswt.search.DocumentList;
import com.nswt.search.crawl.CrawlConfig;
import com.nswt.search.crawl.Crawler;

/**
 * @Author ������
 * @Date 2008-3-31
 * @Mail nswt@nswt.com.cn
 */
public class FromWeb extends Page {
	public static Mapx init(Mapx map) {
		DataTable dt = new QueryBuilder("select code,id,name from ZCCustomTable").executeDataTable();
		for (int i = 0; i < dt.getRowCount(); i++) {
			dt.set(i, 0, dt.getString(i, 0) + "(" + dt.getString(i, 2) + ")");
		}
		map.put("CustomTables", HtmlUtil.dataTableToOptions(dt));
		return map;
	}

	public static Mapx initDialog(Mapx map) {
		String id = map.getString("ID");
		ZCWebGatherSchema wg = new ZCWebGatherSchema();
		wg.setID(Long.parseLong(id));
		wg.fill();
		return map;
	}

	public static void dg1DataBind(DataGridAction dga) {
		String sql = "select ID,Name,Intro,Type,Addtime,Adduser,ConfigXML,Status from ZCWebGather where SiteID=?";
		DataTable dt = new QueryBuilder(sql, Application.getCurrentSiteID()).executeDataTable();
		Mapx map = new Mapx();
		map.put("D", "�ĵ��ɼ�");
		map.put("T", "�Զ���ɼ�");
		dt.decodeColumn("Type", map);
		map = new Mapx();
		map.put("Y", "����");
		map.put("N", "ͣ��");
		dt.decodeColumn("Status", map);
		dt.insertColumn("StartURL");
		dt.insertColumn("ThreadCount");
		for (int i = 0; i < dt.getRowCount(); i++) {
			String xml = dt.getString(i, "ConfigXML");
			CrawlConfig cc = new CrawlConfig();
			cc.parseXML(xml);
			dt.set(i, "StartURL", cc.getUrlLevels()[0]);
			dt.set(i, "ThreadCount", "" + cc.getThreadCount());
		}
		dga.bindData(dt);
	}

	public void getCatalogName() {
		String catalogID = Request.getString("_Param0");
		String name = CatalogUtil.getName(catalogID);
		$S("Name", name);
	}

	public void add() {
		ZCWebGatherSchema wg = new ZCWebGatherSchema();
		String id = $V("ID");
		if (StringUtil.isNotEmpty(id)) {
			wg.setID(Long.parseLong(id));
			wg.fill();
		} else {
			wg.setID(NoUtil.getMaxID("GatherID"));
			wg.setSiteID(Application.getCurrentSiteID());
			wg.setAddTime(new Date());
			wg.setAddUser(User.getUserName());
		}
		wg.setValue(Request);

		Document doc = DocumentHelper.createDocument();
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding(Constant.GlobalCharset);
		Element root = doc.addElement("configs");
		Element ele = root.addElement("config");
		ele.addAttribute("key", "CopyImage");
		ele.addAttribute("value", $V("CopyImage"));

		ele = root.addElement("config");
		ele.addAttribute("key", "CleanLink");
		ele.addAttribute("value", $V("CleanLink"));

		ele = root.addElement("config");
		ele.addAttribute("key", "PublishDateFormat");
		ele.addAttribute("value", $V("PublishDateFormat"));

		ele = root.addElement("config");
		ele.addAttribute("key", "CatalogID");
		ele.addAttribute("value", $V("CatalogID"));

		ele = root.addElement("config");
		ele.addAttribute("key", "ThreadCount");
		ele.addAttribute("value", $V("ThreadCount"));

		ele = root.addElement("config");
		ele.addAttribute("key", "MaxPageCount");
		ele.addAttribute("value", $V("MaxPageCount"));

		ele = root.addElement("config");
		ele.addAttribute("key", "MaxListCount");
		ele.addAttribute("value", $V("MaxListCount"));

		ele = root.addElement("config");
		ele.addAttribute("key", "RetryTimes");
		ele.addAttribute("value", $V("RetryTimes"));

		ele = root.addElement("config");
		ele.addAttribute("key", "ProxyFlag");
		ele.addAttribute("value", $V("ProxyFlag"));

		ele = root.addElement("config");
		ele.addAttribute("key", "ProxyHost");
		ele.addAttribute("value", $V("ProxyHost"));

		ele = root.addElement("config");
		ele.addAttribute("key", "ProxyPort");
		ele.addAttribute("value", $V("ProxyPort"));

		ele = root.addElement("config");
		ele.addAttribute("key", "ProxyUserName");
		ele.addAttribute("value", $V("ProxyUserName"));

		ele = root.addElement("config");
		ele.addAttribute("key", "ProxyPassword");
		ele.addAttribute("value", $V("ProxyPassword"));

		ele = root.addElement("config");
		ele.addAttribute("key", "TimeOut");
		ele.addAttribute("value", $V("TimeOut"));

		ele = root.addElement("config");
		ele.addAttribute("key", "FilterFlag");
		ele.addAttribute("value", $V("FilterFlag"));

		ele = root.addElement("script");
		ele.addAttribute("language", $V("Lang"));
		ele.addCDATA($V("Script"));

		ele = root.addElement("filterExpr");
		ele.addCDATA($V("FilterExpr"));

		Object[] ks = Request.keyArray();
		Object[] vs = Request.valueArray();
		for (int i = 0; i < Request.size(); i++) {
			String key = ks[i].toString();
			if (key.startsWith("RefCode")) {
				String code = vs[i].toString();
				String content = Request.getString(key.replaceAll("RefCode", "Template"));
				ele = root.addElement("template");
				ele.addAttribute("code", code);
				ele.addCDATA(content);
			}
			if (key.startsWith("URL")) {
				String level = key.substring(3);
				String content = Request.getString(key);
				ele = root.addElement("urls");
				ele.addAttribute("level", level);
				ele.addCDATA(content);
			}
			if (key.startsWith("FilterBlock")) {
				String level = key.substring(11);
				String content = Request.getString(key);
				ele = root.addElement("filterBlock");
				ele.addAttribute("no", level);
				ele.addCDATA(content);
			}
		}

		StringWriter sw = new StringWriter();
		try {
			XMLWriter output = new XMLWriter(sw, format);
			output.write(doc);
			output.close();
		} catch (IOException e) {
			LogUtil.info(e.getMessage());
		}
		wg.setConfigXML(sw.toString());
		boolean flag = StringUtil.isEmpty(id) ? wg.insert() : wg.update();
		if (flag) {
			Response.setMessage("�ύ�ɹ�");
		} else {
			Response.setError("�ύʧ��");
		}
	}

	public void del() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("����IDʱ��������!");
			return;
		}
		Transaction trans = new Transaction();
		ZCWebGatherSchema wg = new ZCWebGatherSchema();
		ZCWebGatherSet set = wg.query(new QueryBuilder("where id in (" + ids + ")"));
		trans.add(set, Transaction.DELETE_AND_BACKUP);
		trans.add(new QueryBuilder("delete from ZDSchedule where SourceID in (" + ids + ") and TypeCode='WebCrawl'"));
		if (trans.commit()) {
			String path = Config.getContextRealPath() + CrawlConfig.getWebGatherDir();
			for (int i = 0; i < set.size(); i++) {
				wg = set.get(i);
				String str = path + wg.getID() + "/";
				if (new File(str).exists()) {
					DocumentList list = new DocumentList(str);
					list.delete();
				}
			}
			Response.setStatus(1);
			Response.setMessage("ɾ���ɹ���");
		} else {
			Response.setStatus(0);
			Response.setMessage("�������ݿ�ʱ��������!");
		}
	}

	public void delResult() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("����IDʱ��������!");
			return;
		}
		ZCWebGatherSchema wg = new ZCWebGatherSchema();
		ZCWebGatherSet set = wg.query(new QueryBuilder("where id in (" + ids + ")"));
		try {
			String path = Config.getContextRealPath() + CrawlConfig.getWebGatherDir();
			if (!path.endsWith("/") && !path.endsWith("\\")) {
				path = path + "/";
			}

			for (int i = 0; i < set.size(); i++) {
				wg = set.get(i);
				String str = path + wg.getID() + "/";
				if (new File(str).exists()) {
					DocumentList list = new DocumentList(str);
					if (!list.delete()) {
						Errorx.addError("IDΪ" + set.get(i).getID() + "���������ʧ�ܣ����ֹ�ɾ��" + str);
					}
				}
			}
			Response.setStatus(0);
			if (Errorx.hasError()) {
				Response.setMessage("��ղɼ�������ֳɹ���" + Errorx.printString());
			} else {
				Response.setMessage("��ղɼ�����ɹ�!");
			}
		} catch (Exception e) {
			Response.setStatus(0);
			Response.setMessage("��ղɼ����ʱ��������:����\"" + wg.getName() + "\"����ִ����!");
		}
	}

	public void execute() {
		final long id = Long.parseLong($V("ID"));
		LongTimeTask ltt = LongTimeTask.getInstanceByType("WebGather" + id);
		if (ltt != null) {
			if (!ltt.isAlive()) {
				LongTimeTask.removeInstanceById(ltt.getTaskID());
			} else {
				Response.setError("����������������У�������ֹ��");
				return;
			}
		}
		ltt = new LongTimeTask() {
			public void execute() {
				ZCWebGatherSchema wg = new ZCWebGatherSchema();
				wg.setID(id);
				wg.fill();
				CrawlConfig cc = new CrawlConfig();
				cc.parse(wg);
				Crawler crawler = new Crawler(this);
				crawler.setConfig(cc);
				crawler.crawl();
				setPercent(100);
			}
		};
		ltt.setType("WebGather" + id);
		ltt.setUser(User.getCurrent());
		ltt.start();
		$S("TaskID", "" + ltt.getTaskID());
	}

	public void dealData() {
		final long id = Long.parseLong($V("ID"));
		LongTimeTask ltt = LongTimeTask.getInstanceByType("WebGather" + id);
		if (ltt != null) {
			if (!ltt.isAlive()) {
				LongTimeTask.removeInstanceById(ltt.getTaskID());
			} else {
				Response.setError("����������������У�������ֹ��");
				return;
			}
		}
		ltt = new LongTimeTask() {
			public void execute() {
				ZCWebGatherSchema wg = new ZCWebGatherSchema();
				wg.setID(id);
				wg.fill();
				CrawlConfig cc = new CrawlConfig();
				cc.parse(wg);
				String[] urls = cc.getUrlLevels();
				urls = (String[]) ArrayUtils
						.add(urls,
								"${A}.gif\n${A}.jpg\n${A}.jpeg\n${A}.png\n${A}.bmp\n${A}.GIF\n${A}.JPG\n${A}.JPEG\n${A}.PNG\n${A}.BMP");
				cc.setUrlLevels(urls);

				Crawler crawler = new Crawler(this);
				crawler.setConfig(cc);
				crawler.writeArticle();

				// ��������������δ�ر��ļ���bug�������������ݻ�ʧ�ܡ�
				DocumentList list = crawler.getList();
				if (list != null) {
					list.close();
				}
				setPercent(100);
			}
		};
		ltt.setType("WebGather" + id);
		ltt.setUser(User.getCurrent());
		ltt.start();
		$S("TaskID", "" + ltt.getTaskID());
	}

	public void cancel() {
		final String id = $V("ID");
		Response.setMessage(LongTimeTask.cancelByType("WebGather" + id));
	}

	public static void main(String[] args) {
		ZCWebGatherSchema wg = new ZCWebGatherSchema();
		wg.setID(5);
		wg.fill();
		CrawlConfig cc = new CrawlConfig();
		cc.parse(wg);
		Crawler crawler = new Crawler();
		crawler.setConfig(cc);
		crawler.crawl();
	}
}
