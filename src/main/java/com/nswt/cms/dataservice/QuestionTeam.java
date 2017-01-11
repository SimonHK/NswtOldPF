package com.nswt.cms.dataservice;

import java.util.Date;

import com.nswt.framework.Page;
import com.nswt.framework.User;
import com.nswt.framework.controls.TreeAction;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.pub.NoUtil;
import com.nswt.schema.ZCQuestionGroupSchema;
import com.nswt.schema.ZCQuestionGroupSet;

public class QuestionTeam extends Page {
	
	/**
	 * ���νṹ
	 * 
	 */
	public static void treeDataBind(TreeAction ta) {
		DataTable dt = new QueryBuilder(" select InnerCode,ParentInnerCode,TreeLevel,Name from ZCQuestionGroup").executeDataTable();
		ta.setRootText("��������б�");
		ta.setParentIdentifierColumnName("ParentInnerCode");
		ta.setIdentifierColumnName("InnerCode");
		ta.setBranchIcon("Icons/treeicon12.gif");
		ta.setLeafIcon("Icons/treeicon12.gif");
		ta.bindData(dt);
	}
	
	/**
	 * ��ʼ���Ի�ҳ�������ֶ�,��ѯ�����б��
	 * 
	 * @param params
	 * @return
	 */
	public static Mapx initDialog(Mapx params) {
		String InnerCode=params.getString("InnerCode");
		String ParentInnerCode=params.getString("ParentInnerCode");
		if (StringUtil.isNotEmpty(InnerCode)) {
			ZCQuestionGroupSchema group = new ZCQuestionGroupSchema();
			group.setInnerCode(InnerCode);
			if (!group.fill()) {
				return params;
			}
			Mapx map = group.toMapx();
			ParentInnerCode=map.getString("ParentInnerCode");
			if(StringUtil.isNotEmpty(ParentInnerCode)&&!"0".equals(ParentInnerCode)){
				String ParentName=new QueryBuilder("select name from zcquestionGroup where InnerCode=?",ParentInnerCode).executeString();
				map.put("ParentName", ParentName);
				map.put("ParentInnerCode", ParentInnerCode);
			}
			return map;
		} else {
			String ParentName=new QueryBuilder("select name as ParentName from zcquestiongroup where InnerCode=?",ParentInnerCode).executeString();
			params.put("ParentInnerCode", ParentInnerCode);
			params.put("ParentName", ParentName);
			return params;
		}
	}
	/**
	 * ��֤����������
	 *
	 */
	public boolean verifyName(String Name){
		int count=new QueryBuilder("select count(*) from ZCQuestionGroup where Name=?",Name.trim()).executeInt();
		if(count>0&&StringUtil.isNotEmpty(Name)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * ����������Ϣ
	 * 
	 */
	public void add() {
		String ParentInnerCode=$V("ParentInnerCode");
		ZCQuestionGroupSchema group=new ZCQuestionGroupSchema();
		Transaction trans=new Transaction();
		group.setValue(Request);
		if(verifyName(group.getName())){
			Response.setLogInfo(0, "����ʧ��,�������Ѿ�����!");
			return;
		}
		if(StringUtil.isNotEmpty(ParentInnerCode)){
			group.setInnerCode(NoUtil.getMaxNo("GroupInnerCode", ParentInnerCode, 4));
			group.setParentInnerCode(ParentInnerCode);
			int parentTreelevel=new QueryBuilder("select TreeLevel from ZCQuestionGroup where InnerCode=?",ParentInnerCode).executeInt();
			group.setTreeLevel(parentTreelevel+1);
			trans.add(new QueryBuilder("update ZCQuestionGroup set IsLeaf='Y' where InnerCode=?",ParentInnerCode));
		}else{
			group.setInnerCode(NoUtil.getMaxNo("GroupInnerCode", 4));
			group.setParentInnerCode("0");
			group.setTreeLevel(1);
		}
		group.setOrderFlag(0);
		group.setAddUser(User.getUserName());
		Date currentDate=new Date();
		group.setAddTime(currentDate);
		trans.add(group, Transaction.INSERT);
		if (trans.commit()) {
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
		ZCQuestionGroupSchema group = new ZCQuestionGroupSchema();
		String innerCode = $V("InnerCode");
		group.setInnerCode(innerCode);
		group.fill();
		group.setName($V("Name"));
		group.setModifyTime(new Date());
		group.setModifyUser(User.getUserName());
		if (group.update()) {
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
		String InnerCode = $V("InnerCode");
		if (StringUtil.isEmpty(InnerCode)) {
			Response.setStatus(0);
			Response.setMessage("����InnerCodeʱ��������!");
			return;
		}
		Transaction trans=new Transaction();
		ZCQuestionGroupSchema group = new ZCQuestionGroupSchema();
		ZCQuestionGroupSet questionset = group.query(new QueryBuilder("where InnerCode = ?",InnerCode));
		trans.add(questionset,Transaction.DELETE_AND_BACKUP);
		if (trans.commit()) {
			Response.setLogInfo(1, "�����ɹ�!");
		} else {
			Response.setLogInfo(0, "����ʧ��!");
		}
	}
}
