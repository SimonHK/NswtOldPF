package com.nswt.cms.dataservice;

import com.nswt.framework.Page;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.pub.NoUtil;
import com.nswt.platform.pub.OrderUtil;
import com.nswt.schema.ZCVoteItemSchema;
import com.nswt.schema.ZCVoteSchema;
import com.nswt.schema.ZCVoteSubjectSchema;

/**
 * @Author ����
 * @Date 2007-8-15
 * @Mail chq@nswt.com
 */

public class VoteItem extends Page {

	public static Mapx init(Mapx params) {
		String VoteID = params.get("ID").toString();
		ZCVoteSchema vote = new ZCVoteSchema();
		vote.setID(VoteID);
		vote.fill();
		params.put("VoteName", vote.getTitle());
		return params;
	}

	public void save() {
		Mapx map = this.Request;
		String voteID = $V("ID");
		ZCVoteSubjectSchema subject = new ZCVoteSubjectSchema();
		ZCVoteItemSchema item = new ZCVoteItemSchema();
		subject.setVoteID(voteID);
		item.setVoteID(voteID);
		String key = null;
		Transaction trans = new Transaction();
		trans.add(subject.query(), Transaction.DELETE_AND_BACKUP);
		trans.add(item.query(), Transaction.DELETE_AND_BACKUP);

		Object[] ks = map.keyArray();
		for (int i = 0; i < map.size(); i++) {
			key = (String) ks[i];
			if (key.startsWith("Subject_")) {
				String subjectID = key.substring(8);
				subject = new ZCVoteSubjectSchema();
				subject.setID(subjectID);
				if(!subject.fill()){
					subject.setID(NoUtil.getMaxID("VoteSubjectID"));
				}
				subject.setVoteID(voteID);
				subject.setType(map.getString("Type_" + subjectID));
				if (StringUtil.isEmpty(subject.getType())) {
					subject.setType("S");
				}
				subject.setSubject(map.getString(key));
				subject.setOrderFlag(OrderUtil.getDefaultOrder());
				trans.add(subject, Transaction.INSERT);
				
				for (int j = 0; j < map.size(); j++) {
					key = (String) ks[j];
					if (key.startsWith("Item_" + subjectID + "_")) {
						item = new ZCVoteItemSchema();
						item.setID(key.substring(("Item_" + subjectID + "_").length()));
						if(!item.fill()){
							item.setID(NoUtil.getMaxID("VoteItemID"));
						}
						item.setVoteID(subject.getVoteID());
						item.setSubjectID(subject.getID());
						if (StringUtil.isNotEmpty(map.getString(key))) {
							item.setItem(map.getString(key));
						} else if ("W".equals(map.getString("Type_" + subjectID))) {
							item.setItem("����");
						} else if ("1".equals(map.getString("ItemType_" + key.substring(5))) 
								|| "2".equals(map.getString("ItemType_" + key.substring(5)))) {
							item.setItem("����");
						} else {
							Response.setLogInfo(0, "ѡ�����ݲ���Ϊ�գ�");
							return;
						}
						item.setScore(map.getString("Score_" + key.substring(5)));
						item.setItemType(map.getString("ItemType_" + key.substring(5)));
						if (StringUtil.isEmpty(item.getItemType())) {
							item.setItemType("0");
						}
						item.setOrderFlag(OrderUtil.getDefaultOrder());
						trans.add(item, Transaction.INSERT);
					} else if ("W".equals(map.getString("Type_" + subjectID)) && (key.startsWith("ItemType_" + subjectID + "_"))) {
						item = new ZCVoteItemSchema();
						item.setID(key.substring(("ItemType_" + subjectID + "_").length()));
						if(!item.fill()){
							item.setID(NoUtil.getMaxID("VoteItemID"));
						}
						item.setVoteID(subject.getVoteID());
						item.setSubjectID(subject.getID());
						item.setItem(subject.getSubject());
						item.setScore(map.getString("Score_" + subjectID + "_" + key.substring(("ItemType_" + subjectID + "_").length())));
						item.setItemType(map.getString("ItemType_" + subjectID + "_" + key.substring(("ItemType_" + subjectID + "_").length())));
						if (StringUtil.isEmpty(item.getItemType())) {
							item.setItemType("1");
						}
						item.setOrderFlag(OrderUtil.getDefaultOrder());
						trans.add(item, Transaction.INSERT);
					}
				}
			}
		}
		if (trans.commit()) {
			Response.setLogInfo(1, "����ɹ�");
			Vote.generateJS(voteID);
		} else {
			Response.setLogInfo(0, "����ʧ��");
		}
	}

//	public static DataTable getVoteSubjects(Mapx params, DataRow parentDR) {
//		String voteID = params.getString("ID");
//		DataTable dt = new QueryBuilder("select * from ZCVoteSubject where voteID =? order by ID", voteID)
//				.executeDataTable();
//
//		return dt;
//	}

	// public static DataTable getVoteItems(Mapx params, DataRow parentDR) {
	// String voteID = params.getString("ID");
	// DataTable dt = new
	// QueryBuilder("select * from ZCVoteItem where voteID =? and SubjectID = ? order by ID",
	// voteID, parentDR.get("ID")).executeDataTable();
	// dt.insertColumn("InputType");
	// dt.insertColumn("DisplayValue");
	// for(int i = 0; i < dt.getRowCount(); i++) {
	// dt.set(i, "InputType", dt.getString(i, "Type").equals("Y") ? "CheckBox" :
	// "Radio");
	// dt.set(i, "DisplayValue", dt.getString(i, "ItemType").equals("0") ?
	// "none" : "");
	// }
	// return dt;
	// }
}
