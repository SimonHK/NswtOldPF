package com.nswt.cms.webservice;

import com.nswt.cms.api.UserAPI;
import com.nswt.framework.utility.Errorx;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;

/**
 * �û��ӿ�
 * @author lanjun
 *
 */
public class UserOperatorImpl implements UserOperator{
	public UserOperationResponse doUserOperation(UserOperationRequest request) {
		String operationType = request.getOperationType();
		String userCode = request.getUserCode();
	    String userName = request.getUserName();
	    String orgCode  = request.getOrgCode();
	    String orgName = request.getOrgName();
		UserOperationResponse response  = new UserOperationResponse();
		int errorFlag = 0;
		int errorCode = 0;
		String  errorMsg  = "";
		if(CREATE.equals(operationType)){
			Mapx params=new Mapx();
			params.put("Username", userCode);
			params.put("RealName", userName);
			params.put("Password","123456");
			params.put("Email", "");
			params.put("BranchCode", orgCode);
			if (StringUtil.isEmpty(orgCode)) {
				params.put("BranchCode", "0001");
			}
			params.put("Status", "N");
			UserAPI u=new UserAPI();
			u.setParams(params);
			if(u.insert()!=-1){
				LogUtil.info("�½��û��ɹ���"+userCode);
			}else{
				errorMsg = "�½��û�ʧ�ܣ�"+Errorx.getMessages()[0];
				LogUtil.info(errorMsg);
				errorFlag = -1;
				errorCode = 1;
			}
		}else if(UPDATE.equals(operationType)){
			Mapx params = new Mapx();
			params.put("Username", ""+userCode);
			params.put("RealName", ""+userName);
			params.put("BranchCode", ""+orgCode);
			UserAPI u = new UserAPI();
			u.setParams(params);
			
			if(u.update()){
				LogUtil.info("�ɹ��޸��û���"+userCode);
			}else{
				errorMsg = "�޸��û�ʧ�ܣ�"+userCode;
				LogUtil.info(errorMsg);
				errorFlag = -1;
				errorCode = 2;
			}
		}else if(SUSPEND.equals(operationType)){
			Mapx params = new Mapx();
			params.put("Username", ""+userCode);
			params.put("Status", "S");
			params.put("OperationType", "suspend");
			UserAPI u = new UserAPI();
			u.setParams(params);
			
			if(u.update()){
				LogUtil.info("�ɹ���ͣ�û���"+userCode);
			}else{
				errorMsg = "��ͣʧ�ܣ�"+Errorx.getMessages()[0];
				LogUtil.info(errorMsg);
				errorFlag = -1;
				errorCode = 3;
			}
		}else if(RESTORE.equals(operationType)){
			Mapx params = new Mapx();
			params.put("Username", userCode);
			params.put("Status", "N");
			params.put("OperationType", "restore");
			UserAPI u = new UserAPI();
			u.setParams(params);
			
			if(u.update()){
				LogUtil.info("�ɹ��ָ��û���"+userCode);
			}else{
				errorMsg = "�ָ��û�ʧ�ܣ�"+Errorx.getMessages()[0];
				LogUtil.info(errorMsg);
				errorFlag = -1;
				errorCode = 4;
			}
		}else{
			errorFlag = -1;
			errorCode = 5;
			errorMsg = "��֧�ֵĲ�������:"+operationType;
		}
		response.setErrorCode(errorCode);
		response.setErrorFlag(errorFlag);
		response.setErrorMessage(errorMsg);
		return response;
	}
	
}
