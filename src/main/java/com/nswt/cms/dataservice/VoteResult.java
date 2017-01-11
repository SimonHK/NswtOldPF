package com.nswt.cms.dataservice;

import com.nswt.schema.*;
import com.nswt.framework.Constant;
import com.nswt.framework.User;
import com.nswt.framework.controls.HtmlTD;
import com.nswt.framework.controls.HtmlTR;
import com.nswt.framework.controls.HtmlTable;
import com.nswt.framework.data.*;
import com.nswt.platform.pub.*;
import com.nswt.framework.utility.*;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VoteResult {
	public static boolean vote(HttpServletRequest request, HttpServletResponse response) {
		String ID = request.getParameter("ID");
		String VoteCatalogID = request.getParameter("VoteCatalogID");
		if (StringUtil.isEmpty(ID) && StringUtil.isEmpty(VoteCatalogID)) {
			try {
				response.getWriter().write("<script language='javascript' >alert('调查ID不能为空');</script>");
				return false;
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		if (StringUtil.isNotEmpty(VoteCatalogID)) {
			DataTable vote = new QueryBuilder("select * from zcvote where votecatalogid = ?", VoteCatalogID)
					.executeDataTable();
			if (vote.getRowCount() > 0) {
				ID = vote.getString(0, "ID");
			}
		}

		String IP = request.getRemoteHost();
		ZCVoteSchema vote = new ZCVoteSchema();
		vote.setID(ID);
		if (!vote.fill()) {
			try {
				response.getWriter().write("<script language='javascript' >alert('此调查不存在，调查ID：" + ID + "');</script>");
				return false;
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		String qs = request.getQueryString();
		if (StringUtil.isEmpty(qs)) {
			String VerifyCode = request.getParameter("VerifyCode");
			Object authCode = User.getValue(Constant.DefaultAuthKey);
			if (!"N".equals(vote.getVerifyFlag()) && (authCode == null || !authCode.equals(VerifyCode))) {
				try {
					response.getWriter().write("<script language='javascript' >alert('投票失败，验证码输入错误');</script>");
					return false;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			boolean flag = true;
			Date now = new Date();
			if (now.before(vote.getStartTime())) {
				try {
					response.getWriter().write(
							"<script language='javascript' >alert('对不起，此调查还没有开始！开始时间为：" + vote.getStartTime()
									+ "，请您到时候再来投票');</script>");
					return false;
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				flag = false;
			}
			if (vote.getEndTime() != null && now.after(vote.getEndTime())) {
				try {
					response.getWriter().write("<script language='javascript' >alert('对不起，此调查已经结束，不再接受投票！');</script>");
					return false;
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				flag = false;
			}
			if (flag && "Y".equals(vote.getIPLimit())) {
				int count = new QueryBuilder("select count(*) from zcvotelog where IP = ? and voteID = ?", IP, ID)
						.executeInt();
				if (count > 0) {
					try {
						response.getWriter().write(
								"<script language='javascript' >alert('您已经投过票了，所以本次投票无效！');</script>");
						return false;
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					flag = false;
				}
			}

			String slit = "$|";
			if (flag) {
				Mapx map = ServletUtil.getParameterMap(request);
				ZCVoteSubjectSchema subject = new ZCVoteSubjectSchema();
				ZCVoteSubjectSet subjectSet = subject.query(new QueryBuilder(" where voteID =? order by ID", Long.parseLong(ID)));
				StringBuffer resultsb = new StringBuffer();
				StringBuffer itemIDsb = new StringBuffer();
				Transaction trans = new Transaction();
				if (StringUtil.isNotEmpty(VoteCatalogID)) {
					for (int i = 0; i < subjectSet.size(); i++) {
						subject = subjectSet.get(i);
						String value = map.getString("VoteDocID");
						String subjectType = subject.getType();
						if ("W".equals(subjectType)) {
							String itemTextValue = map.getString("Subject_" + subject.getID() + "_Item_" + value);
							if (StringUtil.isNotEmpty(itemTextValue)) {
								resultsb.append(value + "$&" + itemTextValue + slit);// "$&"后面保存的是textfield的内容
							} else {
								resultsb.append(value + slit);
							}
						} else if (StringUtil.isNotEmpty(value)) {
							String[] arrs = StringUtil.splitEx(value, ",");
							for (int j = 0; j < arrs.length; j++) {
								itemIDsb.append(new QueryBuilder("select id  from zcvoteitem where votedocid=?",
										arrs[j]).executeOneValue()
										+ ",");
								resultsb.append(arrs[j] + slit);
							}

						} else {
							try {
								response.getWriter().write(
										"<script language='javascript' >alert('对不起，此项还没有投票:" + subject.getSubject()
												+ "');</script>");
								return false;
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				} else {
					for (int i = 0; i < subjectSet.size(); i++) {
						subject = subjectSet.get(i);
						String value = map.getString("Subject_" + subject.getID());
						String subjectType = subject.getType();
						if ("W".equals(subjectType)) {
							if (StringUtil.isNotEmpty(value)) {
								resultsb.append(subject.getID() + ":" + value + slit);
							} else {
								resultsb.append("" + slit);
							}
						} else if (StringUtil.isNotEmpty(value)) {
							String[] arrs = StringUtil.splitEx(value, ",");
							for (int j = 0; j < arrs.length; j++) {
								value = arrs[j];
								if (value.startsWith("Item_")) {
									itemIDsb.append(value.substring(5) + ",");
									resultsb.append(map.getString("Subject_" + subject.getID() + "_" + value) + slit);
								} else {
									itemIDsb.append(value + ",");
									String itemTextValue = map.getString("Subject_" + subject.getID() + "_Item_"
											+ value);
									if (StringUtil.isNotEmpty(itemTextValue)) {
										resultsb.append(value + "$&" + itemTextValue + slit);// "$&"后面保存的是textfield的内容
									} else {
										resultsb.append(value + slit);
									}
								}
							}
						} else {
							try {
								response.getWriter().write(
										"<script language='javascript' >alert('对不起，此项还没有投票:" + subject.getSubject()
												+ "');</script>");
								return false;
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}

				}

				if (StringUtil.isNotEmpty(itemIDsb.toString()) && itemIDsb.length() > 1) {
					trans.add(new QueryBuilder("update zcvoteitem set score=score+1 where id in ("
							+ itemIDsb.deleteCharAt(itemIDsb.length() - 1) + ")"));
				}
				trans.add(new QueryBuilder("update zcvote set total=total+1 where id = ?", Long.parseLong(ID)));
				ZCVoteLogSchema voteLog = new ZCVoteLogSchema();
				voteLog.setID(NoUtil.getMaxID("VoteLogID"));
				voteLog.setIP(IP);
				voteLog.setVoteID(ID);
				voteLog.setResult(resultsb.toString());
				voteLog.setAddTime(new Date());
				trans.add(voteLog, Transaction.INSERT);
				if (trans.commit()) {
					try {
						if("Not".equals(vote.getProp4())){
							response.getWriter().write("<script language='javascript' >alert('投票成功，谢谢您的投票！');</script>");
							return false;
						}else{
							response.getWriter().write("<script language='javascript' >alert('投票成功，谢谢您的投票，请查看本调查的结果！');</script>");
							return true;
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				} else {
					try {
						response.getWriter().write("<script language='javascript' >alert('对不起，投票失败！');</script>");
						return false;
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}

		}
		return true;
	}

	public static void voteResultDetail(HttpServletRequest request, HttpServletResponse response) {
		String votelogID = request.getParameter("ID");
		if (StringUtil.isEmpty(votelogID)) {
			return;
		}
		ZCVoteLogSchema votelog = new ZCVoteLogSchema();
		votelog.setID(votelogID);
		if (!votelog.fill()) {
			return;
		}
		String html = "<table width='100%' border='0' cellspacing='0' cellpadding='6' class='blockTable'>"
				+ "<tr><td colspan='2' valign='middle' class='blockTd' height='30'></td></tr></table>";
		HtmlTable table = new HtmlTable();
		try {
			table.parseHtml(html);
		} catch (Exception e) {
			e.printStackTrace();
		}
		StringBuffer sb = new StringBuffer("<img src='../Icons/icon032a1.gif' />投票明细 ( ");
		sb.append("IP：" + votelog.getIP());
		sb.append("&nbsp;投票人：" + (votelog.getAddUser() == null ? "匿名" : votelog.getAddUser()));
		sb.append(" )");
		table.getTR(0).getTD(0).setInnerHTML(sb.toString());

		String[] resultArr = StringUtil.splitEx(votelog.getResult(), "$|");
		for (int i = 0, j = 0; i < resultArr.length; i++) {
			String str = resultArr[i];
			if (StringUtil.isEmpty(str)) {
				continue;
			}
			String itemID = StringUtil.splitEx(str, "$&")[0];
			if (StringUtil.isEmpty(itemID)) {
				continue;
			}
			if (itemID.indexOf(":") > 0) {
				String text = itemID.substring(itemID.indexOf(":") + 1);
				String subjectID = itemID.substring(0, itemID.indexOf(":"));
				String subjectName = new QueryBuilder("select Subject from ZCVoteSubject where VoteID=? and ID=?",
						votelog.getVoteID(), subjectID).executeString();
				if (StringUtil.isNotEmpty(subjectName)) {
					j++;
					HtmlTR tr = new HtmlTR();
					table.addTR(tr);

					HtmlTD td = new HtmlTD();
					td.InnerHTML = j + "";
					tr.addTD(td);

					td = new HtmlTD();
					tr.addTD(td);
					sb = new StringBuffer();
					sb.append(subjectName);
					sb.append("&nbsp;<font color=red>" + text + "</font>");
					td.InnerHTML = sb.toString();
				}
			} else {
				if (!NumberUtil.isNumber(itemID)) {
					continue;
				}
				DataTable item = new QueryBuilder("select * from ZCVoteItem where ID=?", Long.parseLong(itemID)).executeDataTable();
				if (item.getRowCount() > 0) {
					j++;
					HtmlTR tr = new HtmlTR();
					table.addTR(tr);

					HtmlTD td = new HtmlTD();
					td.InnerHTML = j + "";
					tr.addTD(td);

					td = new HtmlTD();
					tr.addTD(td);
					
					DataTable itemDT = new QueryBuilder("select * from ZCVoteItem where subjectid=? order by orderflag,id", item.getLong(0, "SubjectID")).executeDataTable();
					int l = 1;
					for (int k = 0; k < itemDT.getRowCount(); k++) {
						if(itemID.equals(itemDT.getString(k, "ID"))){
							l = k + 1;
							break;
						}
					}
					sb = new StringBuffer();
					String subjectName = new QueryBuilder("select Subject from ZCVoteSubject where VoteID=? and ID=?",
							votelog.getVoteID(), item.getLong(0, "SubjectID")).executeString();
					sb.append(subjectName);
					sb.append("&nbsp;<font color=red>" + l + ":" + item.getString(0, "Item") + "</font>");

					if (("1".equals(item.getString(0, "ItemType"))||"2".equals(item.getString(0, "ItemType"))) && StringUtil.splitEx(resultArr[i], "$&").length > 1) {
						sb.append("&nbsp;&nbsp;选择原因:" + StringUtil.splitEx(resultArr[i], "$&")[1]);
					}
					td.InnerHTML = sb.toString();
				}
			}
		}
		try {
			response.flushBuffer();
			response.getWriter().println(table.getOuterHtml());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
