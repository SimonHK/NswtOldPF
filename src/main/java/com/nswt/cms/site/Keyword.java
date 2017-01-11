package com.nswt.cms.site;

import java.util.Date;
import java.util.List;

import com.nswt.framework.Config;
import com.nswt.framework.Page;
import com.nswt.framework.User;
import com.nswt.framework.controls.DataGridAction;
import com.nswt.framework.controls.TreeAction;
import com.nswt.framework.controls.TreeItem;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.DataTableUtil;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.FileUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.Application;
import com.nswt.platform.pub.NoUtil;
import com.nswt.schema.ZCKeywordSchema;
import com.nswt.schema.ZCKeywordSet;
import com.nswt.schema.ZCKeywordTypeSchema;

/**
 * �ؼ��ֹ���
 * 
 * @Author ����
 * @Date 2007-8-21
 * @Mail lanjun@nswt.com
 */
public class Keyword extends Page {

	public static Mapx Type_KeyWordMap = new Mapx(1000);

	public static ZCKeywordSet getKeyWordSet(String typeID) {
		ZCKeywordSet set = (ZCKeywordSet) Type_KeyWordMap.get(typeID);
		if (set == null) {
			updateCache(typeID);
			set = (ZCKeywordSet) Type_KeyWordMap.get(typeID);
		}
		return set;
	}

	private static void updateCache(String typeID) {
		ZCKeywordSchema keyword = new ZCKeywordSchema();
		QueryBuilder qb = new QueryBuilder();
		if (Config.isSQLServer() || Config.isSybase()) {
			qb.setSQL("where KeyWordType like ? order by len(KeyWord) desc");
		} else {
			qb.setSQL("where KeyWordType like ? order by length(KeyWord) desc");
		}
		qb.add("%" + typeID + "%");
		ZCKeywordSet set = keyword.query(qb);
		if (set.size() == 0) {
			Type_KeyWordMap.remove(typeID);
		} else {
			Type_KeyWordMap.put(typeID, set);
		}
	}

	public static void dg1DataBind(DataGridAction dga) {
		String keywordTypeID = dga.getParam("id");
		String word = dga.getParam("Word");
		long siteID = Application.getCurrentSiteID();
		QueryBuilder qb = new QueryBuilder(
				"select ID,Keyword,LinkURL,LinkAlt,LinkTarget,addTime from ZCKeyword where 1=1 ");
		if (StringUtil.isNotEmpty(word)) {
			qb.append(" and Keyword like ? ", "%" + word.trim() + "%");
		}
		qb.append(" and SiteId = ? ");
		qb.add(siteID);
		if (StringUtil.isNotEmpty(keywordTypeID) && !keywordTypeID.trim().equals("null")) {
			qb.append(" and KeywordType like ? ");
			qb.add("%," + keywordTypeID.trim() + ",%");
		}
		if (StringUtil.isNotEmpty(dga.getSortString())) {
			qb.append(dga.getSortString());
		} else {
			qb.append(" order by Keyword asc");
		}
		dga.bindData(qb);
	}

	public void dg1Edit() {
		DataTable dt = (DataTable) Request.get("DT");
		ZCKeywordSet set = new ZCKeywordSet();
		for (int i = 0; i < dt.getRowCount(); i++) {
			ZCKeywordSchema keyword = new ZCKeywordSchema();
			keyword.setID(Integer.parseInt(dt.getString(i, "ID")));
			keyword.fill();
			keyword.setValue(dt.getDataRow(i));
			keyword.setModifyTime(new Date());
			keyword.setModifyUser(User.getUserName());

			set.add(keyword);
		}
		if (set.update()) {
			Type_KeyWordMap.clear();
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("��������!");
		}
	}

	public static Mapx init(Mapx params) {
		return null;
	}

	public static Mapx initDialog(Mapx params) {
		String ID = params.getString("ID");
		ZCKeywordSchema keyword = new ZCKeywordSchema();
		keyword.setID(ID);
		if (StringUtil.isNotEmpty(ID) && keyword.fill()) {
			params.putAll(keyword.toMapx());
		} else {
			params.put("LinkUrl", "http://");
			params.put("LinkTarget", "_blank");
		}
		return params;

	}

	public void add() {
		long siteID = Application.getCurrentSiteID();
		String KeyWord = $V("Keyword").trim();
		int flag = new QueryBuilder("select count(*) from ZCKeyWord where SiteID=? and Keyword=?", siteID, KeyWord)
				.executeInt();
		ZCKeywordSchema keyword = new ZCKeywordSchema();
		if (flag == 0) {
			keyword.setID(NoUtil.getMaxID("KeywordID"));
			keyword.setKeyword(KeyWord);
			keyword.setSiteId(siteID);
			keyword.setLinkUrl($V("LinkURL"));
			keyword.setLinkTarget($V("LinkTarget"));
			keyword.setKeywordType("," + $V("selectdTypes") + ",");
			keyword.setLinkAlt($V("LinkAlt"));
			keyword.setAddTime(new Date());
			keyword.setAddUser(User.getUserName());
			if (keyword.insert()) {
				Type_KeyWordMap.clear();
				Response.setStatus(1);
				Response.setMessage("�����ɹ���");
			} else {
				Response.setStatus(0);
				Response.setMessage("��������!");
			}
		} else {
			keyword = keyword.query(new QueryBuilder("where Keyword = ? and siteID = ?", KeyWord, siteID)).get(0);
			if (StringUtil.isNotEmpty($V("LinkURL")) && !$V("LinkURL").equals("http://")) {
				keyword.setLinkUrl($V("LinkURL"));
			}
			if (StringUtil.isNotEmpty($V("LinkAlt"))) {
				keyword.setLinkAlt($V("LinkAlt"));
			}
			keyword.setLinkTarget($V("LinkTarget"));
			String[] temp = $V("selectdTypes").split(",");
			for (int i = 0; i < temp.length; i++) {
				if (keyword.getKeywordType().indexOf("," + temp[i] + ",") < 0) {
					keyword.setKeywordType(keyword.getKeywordType() + temp[i] + ",");
				}
			}

			if (keyword.update()) {
				if (!StringUtil.checkID(keyword.getKeywordType())) {
					Response.setStatus(0);
					Response.setMessage("����IDʱ��������!");
					return;
				}
				DataTable keywordTypeDT = new QueryBuilder("select * from ZCKeyWordType where SiteID=? and ID in ("
						+ keyword.getKeywordType().substring(1, keyword.getKeywordType().length() - 1) + ")", siteID)
						.executeDataTable();
				StringBuffer message = new StringBuffer();
				for (int i = 0; i < keywordTypeDT.getRowCount(); i++) {
					message.append(keywordTypeDT.get(i, "TypeName") + " ");
				}
				Response.setStatus(1);
				Response.setMessage("�����ɹ������ȵ��ͬʱ���������·����У�<br/>" + "<font class='red'>" + message + "</font>");
			} else {
				Response.setStatus(0);
				Response.setMessage("��������!");
			}
		}
	}

	public void edit() {
		String KeyWord = $V("Keyword").trim();
		String ID = $V("ID");
		QueryBuilder qb = new QueryBuilder("select count(*) from ZCKeyWord where ID != ? and SiteID=? and Keyword=?");
		qb.add(Long.parseLong(ID));
		qb.add(Application.getCurrentSiteID());
		qb.add(KeyWord);
		if (qb.executeInt() == 0) {
			ZCKeywordSchema keyword = new ZCKeywordSchema();
			keyword.setID(ID);
			keyword.fill();
			keyword.setKeyword(KeyWord);
			keyword.setSiteId(Application.getCurrentSiteID());
			keyword.setLinkUrl($V("LinkURL"));
			keyword.setLinkTarget($V("LinkTarget"));
			keyword.setKeywordType("," + $V("selectdTypes") + ",");
			keyword.setLinkAlt($V("LinkAlt"));
			keyword.setModifyTime(new Date());
			keyword.setModifyUser(User.getUserName());
			if (keyword.update()) {
				Type_KeyWordMap.clear();
				Response.setStatus(1);
				Response.setMessage("�޸ĳɹ���");
			} else {
				Response.setStatus(0);
				Response.setMessage("��������!");
			}
		} else {
			Response.setStatus(0);
			Response.setMessage("�Ѿ����ڵĹؼ���!");
		}
	}

	/*
	 * public void add() { ZCKeywordSchema keyword = new ZCKeywordSchema();
	 * keyword.setValue(Request); keyword.setAddTime(new Date());
	 * keyword.setAddUser(User.getUserName()); if (keyword.insert()) {
	 * Response.setStatus(1); Response.setMessage("�����ɹ���"); } else {
	 * Response.setStatus(0); Response.setMessage("��������!"); } }
	 */

	/**
	 * @Author �޽���
	 * @Date 2016-1-28 �ȵ���������룬֧��txt��xls��ʽ��
	 */
	public void importWords() {
		String FilePath = $V("FilePath");
		long SiteID = Application.getCurrentSiteID();
		Transaction trans = new Transaction();
		String Words = $V("KeyWords");
		String selectedCID = $V("selectedCID");
		if (StringUtil.isEmpty(selectedCID)) {
			selectedCID = "";
		}
		String wordsText = "";
		String[] keyWords = null;
		DataTable dt = null;
		if (StringUtil.isEmpty(FilePath)) {
			keyWords = Words.split("\n");
		} else if (FilePath.indexOf("txt") >= 0) {
			FilePath = FilePath.replaceAll("//", "/");
			wordsText = FileUtil.readText(FilePath);
			keyWords = wordsText.split("\n");
		} else if (FilePath.indexOf("xls") >= 0) {
			try {
				dt = DataTableUtil.xlsToDataTable(FilePath);
			} catch (Exception e) {
				e.printStackTrace();
			}
			keyWords = new String[dt.getRowCount()];
			for (int i = 0; i < dt.getRowCount(); i++) {
				keyWords[i] = StringUtil.join(dt.getDataRow(i).toMapx().valueArray());
			}
		}

		String temp = "";
		for (int i = 0; i < keyWords.length; i++) {
			if (!(keyWords[i].equals("\r") || StringUtil.isEmpty(keyWords[i]))) {
				ZCKeywordSchema keyword = new ZCKeywordSchema();
				temp = keyWords[i];
				temp = temp.trim().replaceAll("\\s+", ",");
				temp = temp.replaceAll("��", ",");
				String[] word = StringUtil.splitEx(temp, ",");
				if (word.length != 5 || StringUtil.isEmpty(word[0])) {
					continue;
				} else {
					boolean flag = false;
					if (new QueryBuilder("select count(*) from ZCKeyWord where KeyWord = ? and SiteID=? ", word[0],
							SiteID).executeInt() > 0) {
						flag = true;
					} else {
						flag = false;
					}
					if (flag) {
						String WordID = new QueryBuilder("select ID from ZCKeyWord where KeyWord = ? and SiteID=? ",
								word[0].trim(), SiteID).executeOneValue().toString();
						keyword.setID(WordID);
						keyword.fill();
					} else {
						keyword.setID(NoUtil.getMaxID("KeywordID"));
					}
					keyword.setKeyword(word[0]);
					keyword.setSiteId(SiteID);
					if (StringUtil.isEmpty(word[1])) {
						keyword.setLinkUrl("http://");
					} else {
						keyword.setLinkUrl(word[1].trim());
					}
					if (StringUtil.isNotEmpty(word[2])) {
						keyword.setLinkAlt(word[2].trim());
					}
					if (StringUtil.isDigit(word[3]) && 0 < Integer.parseInt(word[3])
							&& Integer.parseInt(word[3].trim()) < 4) {
						if (word[3].equals("1")) {
							keyword.setLinkTarget("_self");
						} else if (word[3].equals("2")) {
							keyword.setLinkTarget("_blank");
						} else {
							keyword.setLinkTarget("_parent");
						}
					} else {
						keyword.setLinkTarget("_blank");
					}

					String[] typeNames = word[4].split("/");
					for (int j = 0; j < typeNames.length; j++) {
						if (new QueryBuilder("select count(*) from ZCKeyWordType where TypeName = ?", typeNames[j])
								.executeInt() == 0) {
							ZCKeywordTypeSchema type = new ZCKeywordTypeSchema();
							type.setID(NoUtil.getMaxID("KeyWordTypeID"));
							type.setTypeName(typeNames[j].trim());
							type.setSiteID(Application.getCurrentSiteID());
							type.setAddTime(new Date());
							type.setAddUser(User.getUserName());
							type.insert();
						}
						typeNames[j] = "'" + typeNames[j].trim() + "'";
					}
					DataTable typeDT = new QueryBuilder(
							"select ID from ZCKeywordType where siteID = ? and TypeName in ("
									+ StringUtil.join(typeNames) + ")", SiteID).executeDataTable();
					for (int j = 0; j < typeDT.getRowCount(); j++) {
						if (keyword.getKeywordType() == null) {
							keyword.setKeywordType("," + typeDT.getString(j, 0));
						} else if (keyword.getKeywordType().indexOf(typeDT.getString(j, 0)) < 0) {
							keyword.setKeywordType(keyword.getKeywordType() + "," + typeDT.getString(j, 0));
						}
					}
					keyword.setKeywordType(keyword.getKeywordType() + ",");
					if (flag) {
						keyword.setModifyTime(new Date());
						keyword.setModifyUser(User.getUserName());
						trans.add(keyword, Transaction.UPDATE);
					} else {
						keyword.setAddTime(new Date());
						keyword.setAddUser(User.getUserName());
						trans.add(keyword, Transaction.INSERT);
					}
				}
			}
		}

		if (trans.commit()) {
			Type_KeyWordMap.clear();
			Response.setLogInfo(1, "����ɹ�");
		} else {
			Response.setLogInfo(0, "����ʧ��");
		}

	}

	public void del() {
		String ids = $V("IDs");
		String selectedCID = $V("selectedCID");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("����IDʱ��������!");
			return;
		}
		Transaction trans = new Transaction();
		long SiteID = Application.getCurrentSiteID();
		ZCKeywordSchema keyword = new ZCKeywordSchema();
		ZCKeywordSet set = keyword.query(new QueryBuilder("where SiteID=? and id in (" + ids + ")", SiteID));
		if (StringUtil.isEmpty(selectedCID)) {
			trans.add(set, Transaction.DELETE_AND_BACKUP);
		} else {
			for (int i = 0; i < set.size(); i++) {
				keyword = set.get(i);
				String keywordType = keyword.getKeywordType();
				if (keywordType.indexOf("," + selectedCID + ",") >= 0) {
					keyword.setKeywordType(keywordType.replaceAll("," + selectedCID + ",", ","));
				} else {
					ZCKeywordSchema keywordTemp = keyword;
					trans.add(keywordTemp, Transaction.DELETE_AND_BACKUP);
				}
			}
			trans.add(set, Transaction.UPDATE);
		}
		if (trans.commit()) {
			Type_KeyWordMap.clear();
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("�������ݿ�ʱ��������!");
		}
	}

	public static void treeDataBind(TreeAction ta) {
		long siteID = Application.getCurrentSiteID();
		DataTable dt = null;
		QueryBuilder qb = new QueryBuilder("select ID,TypeName from ZCKeywordType Where SiteID=? order by ID", siteID);
		dt = qb.executeDataTable();
		ta.setRootText("�ȵ�ʻ��");
		ta.bindData(dt);
		List items = ta.getItemList();
		for (int i = 1; i < items.size(); i++) {
			TreeItem item = (TreeItem) items.get(i);
			if ("Y".equals(item.getData().getString("SingleFlag"))) {
				item.setIcon("Icons/treeicon11.gif");
			}
		}
	}

	// public void sortKeyword() {
	// String target = $V("Target");
	// String orders = $V("Orders");
	// String type = $V("Type");
	// String typeID = $V("typeID");
	// boolean topFlag = "true".equals($V("TopFlag"));
	// if (!StringUtil.checkID(target) && !StringUtil.checkID(orders)) {
	// return;
	// }
	// Transaction tran = new Transaction();
	// if (topFlag) {
	// QueryBuilder qb = new
	// QueryBuilder("update ZCKeyword set TopFlag='1' where OrderFlag in (" +
	// orders + ")");
	// tran.add(qb);
	// } else {
	// QueryBuilder qb = new
	// QueryBuilder("update ZCKeyword set TopFlag='0' where OrderFlag in (" +
	// orders + ")");
	// tran.add(qb);
	// }
	// OrderUtil.updateOrder("ZCKeyword", "OrderFlag", type, target, orders,
	// null, tran);
	// if (tran.commit()) {
	// final String id = typeID;
	// LongTimeTask ltt = new LongTimeTask() {
	// public void execute() {
	// Publisher p = new Publisher();
	// p.publishCatalog(Long.parseLong(id), false, false, 3);
	// setPercent(100);
	// }
	// };
	// ltt.setUser(User.getCurrent());
	// ltt.start();
	//
	// Response.setMessage("�����ɹ�");
	// } else {
	// Response.setError("����ʧ��");
	// }
	// }

	public void move() {
		String keywordIDs = $V("KeywordIDs");
		String tarTypeID = $V("TypeID");
		if (!StringUtil.checkID(keywordIDs)) {
			Response.setError("�������ݿ�ʱ��������!");
			return;
		}
		if (!StringUtil.checkID(tarTypeID)) {
			Response.setError("����TypeIDʱ��������!");
			return;
		}

		Transaction trans = new Transaction();
		ZCKeywordSchema srcKeyword = new ZCKeywordSchema();
		ZCKeywordSet set = srcKeyword.query(new QueryBuilder("where id in (" + keywordIDs + ")"));

		for (int i = 0; i < set.size(); i++) {
			ZCKeywordSchema keyword = set.get(i);
			String keywordType = keyword.getKeywordType();
			if (keywordType.indexOf(tarTypeID) >= 0) {
				Response.setMessage("�÷������Ѿ������ȵ��\"" + keyword.getKeyword() + "\"");
				return;
			} else {
				keywordType += "," + tarTypeID;
				keyword.setKeywordType(keywordType);
			}

			trans.add(keyword, Transaction.UPDATE);
		}

		if (trans.commit()) {
			Response.setMessage("���Ƴɹ�");
		} else {
			Response.setError("�������ݿ�ʱ��������!");
		}
	}
}
