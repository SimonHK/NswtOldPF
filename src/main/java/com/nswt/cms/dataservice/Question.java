package com.nswt.cms.dataservice;

import java.util.Date;

import com.nswt.framework.Page;
import com.nswt.framework.User;
import com.nswt.framework.controls.DataGridAction;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.pub.NoUtil;
import com.nswt.schema.ZCQuestionSchema;
import com.nswt.schema.ZCQuestionSet;

/**
 * 
 * @author ̷ϲ��
 * @mail 0871huhu@sina.com
 * @date 2016-6-23
 */

public class Question extends Page {

	// static Mapx AutoTransferFlag=new Mapx();
	// static {
	// AutoTransferFlag.put("N","��");
	// AutoTransferFlag.put("Y","��");
	// }
	/**
	 * �������ݰ�
	 * 
	 */
	public static void dg1DataBind(DataGridAction dga) {
		String InnerCode = (String) dga.getParam("QuestionInnerCode");
		QueryBuilder qb = new QueryBuilder("select *  from zcquestion where 1=1 ");
		if (StringUtil.isEmpty(InnerCode)) {
			LogUtil.info("û�еõ�InnerCode��ֵ,��ʷInnerCode��" + dga.getParams().getString("Cookie.Ask.InnerCode"));
			qb.append(" and QuestionInnerCode = ?");
			InnerCode = dga.getParam("Cookie.Ask.InnerCode");
		} else {
			qb.append(" and QuestionInnerCode = ?");
		}
		qb.append(" order by AddTime desc");
		qb.add(InnerCode.trim());
		dga.bindData(qb);
	}

	/**
	 * ��ʼ���Ի�ҳ�������ֶ�,��ѯ�����б��
	 * 
	 * @param params
	 * @return
	 */
	public static Mapx initDialog(Mapx params) {
		String ID = params.getString(("ID"));
		String questionInnerCode = params.getString("QuestionInnerCode");
		if (StringUtil.isNotEmpty(ID)) {
			ZCQuestionSchema question = new ZCQuestionSchema();
			question.setID(ID);
			if (!question.fill()) {
				return params;
			}
			Mapx map = question.toMapx();
			String questionName = new QueryBuilder("select name from zcquestiongroup where InnerCode=?",
					questionInnerCode).executeString();
			map.put("QuestionInnerCode", questionInnerCode);
			map.put("QuestionName", questionName);
			return map;
		} else {
			String questionName = new QueryBuilder("select name from zcquestiongroup where InnerCode=?",
					questionInnerCode).executeString();
			params.put("QuestionInnerCode", questionInnerCode);
			params.put("QuestionName", questionName);
			return params;
		}
	}

	/**
	 * 
	 *
	 */
	// public boolean verifyName(String Name){
	// int count=new
	// QueryBuilder("select count(*) from ZSTeam where Name=?",Name.trim()).executeInt();
	// if(count>0&&StringUtil.isNotEmpty(Name)){
	// return true;
	// }else{
	// return false;
	// }
	// }

	/**
	 * ����������Ϣ
	 * 
	 */
	public void add() {
		String questionInnerCode = $V("QuestionInnerCode");
		if (StringUtil.isEmpty(questionInnerCode)) {
			Response.setLogInfo(0, "����ʧ�ܣ���ѡ�����!");
			return;
		}
		ZCQuestionSchema question = new ZCQuestionSchema();
		question.setID(NoUtil.getMaxID("QuestionID"));
		question.setValue(Request);
		question.setQuestionInnerCode(questionInnerCode);
		question.setAddUser(User.getUserName());
		Date currentDate = new Date();
		question.setAddTime(currentDate);
		if (question.insert()) {
			Response.setLogInfo(1, "�����ɹ�!");
		} else {
			Response.setLogInfo(0, "����ʧ��!");
		}

	}

	/**
	 * �޸�������Ϣ
	 * 
	 */
	public void edit() {
		ZCQuestionSchema question = new ZCQuestionSchema();
		String id = $V("ID");
		question.setID(id);
		question.fill();
		question.setValue(Request);
		question.setModifyTime(new Date());
		question.setModifyUser(User.getUserName());
		if (question.update()) {
			Response.setLogInfo(1, "�����ɹ�!");
		} else {
			Response.setLogInfo(0, "����ʧ��!");
		}

	}

	/**
	 * ɾ��������Ϣ
	 * 
	 */
	public void del() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("����IDʱ��������!");
			return;
		}
		Transaction trans = new Transaction();
		ZCQuestionSchema question = new ZCQuestionSchema();
		ZCQuestionSet questionset = question.query(new QueryBuilder("where ID in (" + ids + ")"));
		trans.add(questionset, Transaction.DELETE_AND_BACKUP);
		if (trans.commit()) {
			Response.setLogInfo(1, "�����ɹ�!");
		} else {
			Response.setLogInfo(0, "����ʧ��!");
		}
	}
}
