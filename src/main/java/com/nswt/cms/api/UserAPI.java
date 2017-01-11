package com.nswt.cms.api;

import java.util.Date;
import java.util.Iterator;
import java.util.regex.Pattern;

import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
//import com.nswt.framework.license.LicenseInfo;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.utility.Errorx;
//import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.Priv;
import com.nswt.schema.ZDBranchSchema;
import com.nswt.schema.ZDBranchSet;
import com.nswt.schema.ZDPrivilegeSchema;
import com.nswt.schema.ZDUserRoleSchema;
import com.nswt.schema.ZDUserRoleSet;
import com.nswt.schema.ZDUserSchema;

public class UserAPI implements APIInterface {

	private Mapx params;
	
	private static final Pattern userPattern = Pattern.compile("[\\w@\\.\\,\u4e00-\u9fa5]*", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	
	public boolean delete() {
		String username=params.getString("Username");
		username = username.toLowerCase();
		
		if (!userPattern.matcher(username).matches()) {
			return false;
		}
		
		if("administrator".equalsIgnoreCase(username)){
			return false;
		}
		
		if("admin".equalsIgnoreCase(username)){
			return false;
		}
		
		ZDUserSchema user=new ZDUserSchema();
		user.setUserName(username);
		if(!user.fill()){
			return false;
		}
		
		Transaction trans=new Transaction();
		// ɾ���������û�������Ĺ�ϵ
		ZDUserRoleSchema userRole = new ZDUserRoleSchema();
		userRole.setUserName(user.getUserName());
		ZDUserRoleSet userRoleSet = userRole.query();
		trans.add(userRoleSet, Transaction.DELETE);
		// ɾ���û���Ȩ��
		trans.add(new ZDPrivilegeSchema().query(new QueryBuilder("where OwnerType=? and Owner=?", Priv.OWNERTYPE_USER, user.getUserName())), Transaction.DELETE);
		trans.add(user,Transaction.DELETE);
		
		if(trans.commit()){
			return true;
		}else return false;
	}

	/*   �����Ӻ�̨�û�
	 * 	 �����Ӻ�̨�û��ɹ� ���� 1 
	 *              ʧ�� ���� -1
	 *              �Ѵ��� ���� 0
	 */
	public long insert() {
		return insert(new Transaction());
	}
	

	public long insert(Transaction trans) {
//		if (new QueryBuilder("select count(*) from ZDUser").executeInt() >= LicenseInfo.getUserLimit()) {
//			LogUtil.info("��̨�û����������ƣ�����ϵ�����������License��");
//			return -1;
//		}
		
		String username = params.getString("Username");
		String realname = params.getString("RealName");
		String password = params.getString("Password");
		String email = params.getString("Email");
		String branchCode = params.getString("BranchCode");
		String isBranchAdmin = params.getString("IsBranchAdmin");
		String status = params.getString("Status");
		
		String type = params.getString("Type");
		if(StringUtil.isEmpty(username)||StringUtil.isEmpty(password)){
			return -1;
		}
		
		username = username.toLowerCase();
		if (!userPattern.matcher(username).matches()) {
			Errorx.addError("�û������20λ������Ӣ����ĸ�����֣����֣���ǡ�.������@��");
			return -1;
		}
		
		ZDUserSchema user = new ZDUserSchema();
		user.setUserName(username);
		if (user.fill()) {
			Errorx.addError("�û�"+username+"�Ѿ�����.");
			return -1;
		}
		
		user.setRealName(realname);
		if (StringUtil.isEmpty(realname)) {
			user.setRealName(username);
		}
		
		ZDBranchSchema branch = new ZDBranchSchema();
		if (StringUtil.isNotEmpty(branchCode)) {
			branch.setBranchCode(branchCode);
			ZDBranchSet set = branch.query();
			if (set == null || set.size() < 1) {
				Errorx.addError(branchCode + "������������.");
				return -1;
			}
			
			user.setBranchInnerCode(set.get(0).getBranchInnerCode());
		} else {
			user.setBranchInnerCode("0001");
		}
		
		if("Y".equals(isBranchAdmin)){
			user.setIsBranchAdmin("Y");
		}else{
			user.setIsBranchAdmin("N");
		}
		
		if("S".equals(status)){
			user.setStatus("S");
		}else{
			user.setStatus("N");
		}
		
		user.setPassword(StringUtil.md5Hex(password));
		if (StringUtil.isEmpty(type)) {
			user.setType("0");
		} else {
			user.setType(type);
		}
		user.setEmail(email);
		user.setProp1(params.getString("Prop1"));
		user.setAddTime(new Date());
		user.setAddUser("wsdl");
		//����û�
		trans.add(user, Transaction.INSERT);
		// ��ɫ
		String roleCodes = params.getString("RoleCode");
		if (StringUtil.isNotEmpty(roleCodes)) {
			roleCodes="'"+roleCodes.replaceAll(",", "','")+"'";
			DataTable dt=new QueryBuilder("select RoleCode from zdrole where RoleCode in ("+roleCodes+")")
							.executeDataTable();
			
			String[] RoleCodes = (String[])dt.getColumnValues(0);
			Date addTime=new Date();
			for (int i = 0; i < RoleCodes.length; i++) {
				if (StringUtil.isEmpty(RoleCodes[i])) {
					continue;
				}
				ZDUserRoleSchema userRole = new ZDUserRoleSchema();
				userRole.setUserName(user.getUserName());
				userRole.setRoleCode(RoleCodes[i]);
				userRole.setAddTime(addTime);
				userRole.setAddUser("wsdl");
				//����û���ɫ�������¼
				trans.add(userRole, Transaction.INSERT);
			}
		} else {
			ZDUserRoleSchema userRole = new ZDUserRoleSchema();
			userRole.setUserName(user.getUserName());
			userRole.setRoleCode("everyone");
			userRole.setAddTime(new Date());
			userRole.setAddUser("wsdl");
			//����û���ɫ�������¼
			trans.add(userRole, Transaction.INSERT);
		}
		
		if(trans.commit()){
			return 1;
		}else{
			Errorx.addError("�½��û�"+username+"ʧ�ܡ�");
			return -1;
		}
	}

	public boolean setSchema(Schema schema) {
		return false;
	}

	public boolean update() {
		String username = params.getString("Username");
		String realname = params.getString("RealName");
		String password = params.getString("Password");
		String email = params.getString("Email");
		String branchCode = params.getString("BranchCode");
		String isBranchAdmin = params.getString("IsBranchAdmin");
		String status = params.getString("Status");
		
		String type = params.getString("Type");
		if(StringUtil.isEmpty(username)){
			return false;
		}
		
		username = username.toLowerCase();
		if (!userPattern.matcher(username).matches()) {
			Errorx.addError("�û������20λ������Ӣ����ĸ�����֣����֣���ǡ�.������@��");
			return false;
		}
		ZDUserSchema user = new ZDUserSchema();
		user.setUserName(username);
		if (!user.fill()) {
			Errorx.addError(username + "�û�������.");
			return false;
		}
		
		if (StringUtil.isNotEmpty(branchCode)) {
			ZDBranchSchema branch = new ZDBranchSchema();
			branch.setBranchCode(branchCode);
			ZDBranchSet set = branch.query();
			if (set == null || set.size() < 1) {
				Errorx.addError(branchCode + "������������.");
				return false;
			}
			
			user.setBranchInnerCode(set.get(0).getBranchInnerCode());
		}
		
		Transaction trans=new Transaction();
		if (StringUtil.isNotEmpty(realname)) {
			user.setRealName(realname);
		}
		
		if("Y".equals(isBranchAdmin)){
			user.setIsBranchAdmin("Y");
		}else{
			user.setIsBranchAdmin("N");
		}
		
		if("suspend".equals(params.getString("OperationType"))&&"S".equals(user.getStatus())){
			Errorx.addError("�û�"+username+"�Ѿ���ͣ��");
			return false;
		}
		
		if("restore".equals(params.getString("OperationType")) && "N".equals(user.getStatus())){
			Errorx.addError("�û�"+username+"δ��ͣ��");
			return false;
		}
		
		if("S".equals(status)){
			user.setStatus("S");
		}else{
			user.setStatus("N");
		}
		
		if (StringUtil.isNotEmpty(password)) {
			user.setPassword(StringUtil.md5Hex(password));
		}
		if (StringUtil.isEmpty(type)) {
			user.setType("0");
		} else {
			user.setType(type);
		}
		if (StringUtil.isNotEmpty(email)) {
			user.setEmail(email);
		}
		user.setProp1(params.getString("Prop1"));
		user.setModifyTime(new Date());
		user.setModifyUser("wsdl");
		
		//�޸��û�
		trans.add(user, Transaction.UPDATE);
		
		//��ɫ
		
		String roleCodes = params.getString("RoleCode");
		if (StringUtil.isNotEmpty(roleCodes)) {
			ZDUserRoleSchema userRole = new ZDUserRoleSchema();
			userRole.setUserName(user.getUserName());
			trans.add(userRole.query(), Transaction.DELETE);
			
			roleCodes="'"+roleCodes.replaceAll(",", "','")+"'";
			DataTable dt=new QueryBuilder("select RoleCode from zdrole where RoleCode in ("+roleCodes+")")
							.executeDataTable();
			String[] RoleCodes = (String[])dt.getColumnValues(0);

			Date date=new Date();
			for (int i = 0; i < RoleCodes.length; i++) {
				if (StringUtil.isEmpty(RoleCodes[i])) {
					continue;
				}
				userRole = new ZDUserRoleSchema();
				userRole.setUserName(user.getUserName());
				userRole.setRoleCode(RoleCodes[i]);
				userRole.setAddTime(date);
				userRole.setAddUser("wsdl");
				trans.add(userRole, Transaction.INSERT);
			}
		}
		
		if(trans.commit()){
			return true;
		}else return false;
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
				params.put(key, "");
			}
		}
	}
	

}
