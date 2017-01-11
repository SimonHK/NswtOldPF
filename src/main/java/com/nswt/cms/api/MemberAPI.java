package com.nswt.cms.api;

import java.util.Iterator;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.utility.Errorx;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.schema.ZDMemberSchema;

public class MemberAPI implements APIInterface {

	private Mapx params;
	
	public boolean delete() {
		
		String UserName = params.getString("UserName");
		if(StringUtil.isNotEmpty(UserName)){
			ZDMemberSchema member = new ZDMemberSchema();
			member.setUserName(UserName);
			if(!member.fill()){
				return false;
			}else{
				Transaction trans=new Transaction();
				trans.add(member,Transaction.DELETE);
				if(trans.commit()){
					return true;
				}else return false;
			}
		}
		return false;
	}
	/*   ��������ͨ�û�
	 * 	 ��������ͨ�û��ɹ� ���� 1 
	 *              ʧ�� ���� -1
	 */
	public long insert() {
		return insert(new Transaction());
	}

	public long insert(Transaction trans) {
		String UserName = params.getString("UserName");
		String PassWord = params.getString("PassWord");
		String realname = params.getString("RealName");
		String Email    = params.getString("Email");
		String Type     = "none";
		String Status   = "N";
		if(StringUtil.isEmpty(UserName)||StringUtil.isEmpty(PassWord)||StringUtil.isEmpty(Email)){
			return -1;
		}
		if (UserName.length()>20) {
			Errorx.addError("��Ա�����20λ");
			return -1;
		}
		ZDMemberSchema member = new ZDMemberSchema();
		member.setUserName(UserName);
		if(member.fill()){
			Errorx.addError(UserName + "��Ա�Ѿ�����!");
			return -1;
		}	
		
		member.setName(realname);
		member.setPassword(StringUtil.md5Hex(PassWord));
		member.setEmail(Email);
		member.setType(Type);
		member.setStatus(Status);
		trans.add(member,Transaction.INSERT);
		if(trans.commit()){
			return 1;
		}else return -1;
	}

	public boolean setSchema(Schema schema) {
		return false;
	}

	public boolean update() {
		String UserName = params.getString("UserName");
		String realname = params.getString("RealName");
		String PassWord = params.getString("PassWord");
		String Email    = params.getString("Email");
		String Type     = "none";
		String Status   = "N";
		if(StringUtil.isEmpty(UserName)||StringUtil.isEmpty(PassWord)||StringUtil.isEmpty(Email)){
			return false;
		}
		if (UserName.length()>20) {
			Errorx.addError("��Ա�����20λ");
			return false;
		}
		if(!StringUtil.isNotEmpty(UserName)){
			Errorx.addError(UserName + "��Ա������!");
			return false;
		}
		Transaction trans = new Transaction();
		ZDMemberSchema member = new ZDMemberSchema();
		member.setUserName(UserName);
		member.fill();
		member.setName(realname);
		member.setPassword(StringUtil.md5Hex(PassWord));
		member.setEmail(Email);
		member.setType(Type);
		member.setStatus(Status);
		trans.add(member,Transaction.UPDATE);
		return trans.commit();
	}
	
	public Mapx getParams() {
		return params;
	}

	public void setParams(Mapx params) {
		convertParams(params);
		this.params = params;
	}
	
	public void convertParams(Mapx params) {
		Iterator iter = params.keySet().iterator();
		while (iter.hasNext()) {
			Object key = iter.next();
			String value = params.getString(key);
			if (StringUtil.isEmpty(value) || "null".equalsIgnoreCase(value)) {
				this.params.put(key, "");
			}
		}
	}
}
