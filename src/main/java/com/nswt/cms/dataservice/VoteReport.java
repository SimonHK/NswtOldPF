package com.nswt.cms.dataservice;

import com.nswt.cms.stat.report.ChartUtil;
import com.nswt.cms.stat.report.ReportUtil;
import com.nswt.framework.Ajax;
import com.nswt.framework.Current;
import com.nswt.framework.data.DataRow;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.schema.ZCVoteSchema;

public class VoteReport extends Ajax {
	public void getVotePieData() {
		String subjectID = $V("SubjectID");
		DataTable dt = new QueryBuilder("select item,score from zcvoteitem where subjectid = ? order by score desc,OrderFlag,id", Long.parseLong(subjectID))
				.executeDataTable();
		if (dt.getRowCount() == 0) {
			$S("Data", "");
			return;
		}
		for (int i = 0; i < dt.getRowCount(); i++) {
			dt.set(i, "Item", i+1);
		}
		ReportUtil.computeRate(dt, "Score", "Rate");
		ReportUtil.addSuffix(dt, "Rate", "%");
		$S("Data", ChartUtil.getPie3DChart(dt));
	}
	
	public void getVoteColumnData() {
		String subjectID = $V("SubjectID");
		DataTable dt = new QueryBuilder("select item,score from zcvoteitem where subjectid = ? order by score desc,OrderFlag,id", Long.parseLong(subjectID))
				.executeDataTable();
		if (dt.getRowCount() == 0) {
			$S("Data", "");
			return;
		}
		for (int i = 0; i < dt.getRowCount(); i++) {
			dt.set(i, "Item", i+1);
		}
		ReportUtil.computeRate(dt, "Score", "Rate");
		ReportUtil.addSuffix(dt, "Rate", "%");
		$S("Data", ChartUtil.getColumn3DChart(dt));
	}

	public static Mapx init(Mapx params) {
		String ID = params.getString("ID");
		String VoteCatalogID = params.getString("VoteCatalogID");
		if (StringUtil.isEmpty(ID) && StringUtil.isNotEmpty(VoteCatalogID)) {
			DataTable vote = new QueryBuilder("select * from zcvote where votecatalogid = ?", Long.parseLong(VoteCatalogID))
					.executeDataTable();
			if (vote.getRowCount() > 0) {
				ID = vote.getString(0, "ID");
			}
		}
		ZCVoteSchema vote = new ZCVoteSchema();
		vote.setID(ID);
		vote.fill();
		Current.setVariable("SiteID", "" + vote.getSiteID());
		params.put("Title", vote.getTitle());
		return params;
	}

	public static DataTable getList1(Mapx params, DataRow parentDR) {
		String voteID = params.getString("ID");
		String VoteCatalogID = params.getString("VoteCatalogID");
		if (StringUtil.isEmpty(voteID) && StringUtil.isNotEmpty(VoteCatalogID)) {
			DataTable vote = new QueryBuilder("select * from zcvote where votecatalogid = ?", Long.parseLong(VoteCatalogID))
					.executeDataTable();
			if (vote.getRowCount() > 0) {
				voteID = vote.getString(0, "ID");
			}
		}
		String type = "��ѡ";
		DataTable dt = new QueryBuilder("select * from ZCVoteSubject where voteID =? and type!='W' order by OrderFlag,ID", Long.parseLong(voteID))
				.executeDataTable();
		dt.insertColumn("count1");
		dt.insertColumn("Type1");
		for (int i = 0; i < dt.getRowCount(); i++) {
			DataTable dt1 = new QueryBuilder("select * from zcvoteitem where voteID =? and SubjectID=? order by score desc,OrderFlag,id",
					Long.parseLong(voteID), dt.getLong(i, "ID")).executeDataTable();
			dt.set(i, "count1", (dt1.getRowCount() + 1));
			if ("S".equals(dt.getString(i, "Type"))) {
				type = "��ѡ";
			} else if ("D".equals(dt.getString(i, "Type"))) {
				type = "��ѡ";
			} else if ("W".equals(dt.getString(i, "Type"))) {
				type = "¼��";
			}
			dt.set(i, "Type1", type);
		}

		return dt;
	}

	public static DataTable getList2(Mapx params, DataRow parentDR) {
		String voteID = params.getString("ID");
		String VoteCatalogID = params.getString("VoteCatalogID");
		if (StringUtil.isEmpty(voteID) && StringUtil.isNotEmpty(VoteCatalogID)) {
			DataTable vote = new QueryBuilder("select * from zcvote where votecatalogid = ?", Long.parseLong(VoteCatalogID))
					.executeDataTable();
			if (vote.getRowCount() > 0) {
				voteID = vote.getString(0, "ID");
			}
		}
		DataTable dt = new QueryBuilder("select * from zcvoteitem where voteID =? and SubjectID=? order by score desc,OrderFlag,id",
				Long.parseLong(voteID), parentDR.getLong("ID")).executeDataTable();
		dt.insertColumn("count");

		dt.insertColumn("percent");
		long total = 0;
		for (int i = 0; i < dt.getRowCount(); i++) {
			total += dt.getLong(i, "Score");
			dt.set(i, "count", i + 1);
		}
		for (int i = 0; i < dt.getRowCount(); i++) {
			long score = dt.getLong(i, "Score");
			if (total == 0) {
				dt.set(i, "percent", 0.0);
			} else if (score <= total) {
				dt.set(i, "percent", Math.round((score * 100.0) / total));
			}
		}
		return dt;
	}
}
