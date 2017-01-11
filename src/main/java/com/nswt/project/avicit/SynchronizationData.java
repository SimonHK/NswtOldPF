package com.nswt.project.avicit;

import java.sql.SQLException;

import com.nswt.framework.data.DBConn;
import com.nswt.framework.data.DBConnPool;
import com.nswt.framework.data.DataAccess;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.DateUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.pub.NoUtil;
import com.nswt.schema.ZDBranchSchema;
import com.nswt.schema.ZDBranchSet;
import com.nswt.schema.ZDRoleSchema;
import com.nswt.schema.ZDRoleSet;
import com.nswt.schema.ZDUserRoleSchema;
import com.nswt.schema.ZDUserSchema;
import com.nswt.schema.ZDUserSet;

public class SynchronizationData {

	private static Mapx deptMap = new Mapx();
	
	private static Mapx roleMap = new Mapx();
	
	private static Mapx userMap = new Mapx();
	
	public static boolean excute() {
		if (synchronizationDept() && synchronizationRole() && synchronizationUser() 
				&& dealUserRole() && dealDeptUser()) {
			return true;
		} else {
			return false;
		}
	}
	
	private static void initDeptData() {
		if (deptMap == null) {
			DataTable dt = new QueryBuilder("select * from zdbranch order by orderflag").executeDataTable();
			deptMap = dt.toMapx("Prop1", "BranchInnerCode");
		}
	}
	
	private static void initRoleData() {
		if (roleMap == null) {
			DataTable dt = new QueryBuilder("select * from zdrole").executeDataTable();
			roleMap = dt.toMapx("Prop1", "RoleCode");
		}
	}
	
	private static void initUserData() {
		if (userMap == null) {
			DataTable dt = new QueryBuilder("select * from zduser").executeDataTable();
			userMap = dt.toMapx("Prop1", "UserName");
		}
	}
	
	private static boolean synchronizationDept() {
		DBConn conn = DBConnPool.getConnection("OracleEmployeer");
		DataAccess da = new DataAccess(conn);
		initDeptData();
		try {
			DataTable dt = da.executeDataTable(new QueryBuilder("select * from hr_dept order by PARENT_DEPT_ID,ORDER_BY"));
			Transaction trans = new Transaction();
			for (int j=0; j<dt.getRowCount(); j++) {
				ZDBranchSchema branch = new ZDBranchSchema();
				branch.setProp1(dt.getString(j, "ID"));
				ZDBranchSet set = branch.query();
				int type = Transaction.UPDATE;
				if (set.size() > 0) {
					branch = set.get(0);
				} else {
					String parentInnerCode = deptMap.getString(dt.getString(j, "PARENT_DEPT_ID"));
					branch.setBranchInnerCode(NoUtil.getMaxNo("BranchInnerCode", parentInnerCode, 4));
					branch.setParentInnerCode(parentInnerCode);
					branch.setIsLeaf("N");
					branch.setAddUser(dt.getString(j, "CREATED_BY"));
					branch.setAddTime(DateUtil.parseDateTime(dt.getString(j, "CREATION_DATE")));
					type = Transaction.INSERT;
				}
				branch.setBranchCode(dt.getString(j, "DEPT_CODE"));
				branch.setTreeLevel(dt.getInt(j, "DEPT_LEVEL") + 1);
				if (StringUtil.isNotEmpty(dt.getString(j, "DEPT_TYPE"))) {
					branch.setType(dt.getString(j, "DEPT_TYPE"));
				} else {
					branch.setType("1");
				}
				branch.setProp2(dt.getString(j, "DEPT_CODE_STANDARD"));
				branch.setProp3(dt.getString(j, "DEPT_ALIAS"));
				branch.setProp4(dt.getString(j, "DEPTCREATEDATE"));
				branch.setFax(dt.getString(j, "FAX"));
				branch.setMemo(dt.getString(j, "DEPT_DESC"));
				branch.setName(dt.getString(j, "DEPT_NAME"));
				if (StringUtil.isNotEmpty(dt.getString(j, "ORDER_BY"))) {
					branch.setOrderFlag(dt.getString(j, "ORDER_BY"));
				} else {
					branch.setOrderFlag(0);
				}
				branch.setPhone(dt.getString(j, "TEL"));
				trans.add(branch, type);
				deptMap.put(branch.getProp1(), branch.getBranchInnerCode());
			}
			
			if (trans.commit()) {
				System.out.println("**********************************");
				System.out.println("成功导入部门信息！");
				System.out.println("**********************************");
				return true;
			} else {
				System.out.println("**********************************");
				System.out.println("导入部门信息失败！");
				System.out.println("**********************************");
				return false;
			}
			
		} catch (SQLException e) {
			System.out.println("**********************************");
			System.out.println("导入部门信息失败！");
			System.out.println("**********************************");
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (da != null) {
					da.close();
				}
				
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static boolean synchronizationRole() {
		DBConn conn = DBConnPool.getConnection("OracleUser");
		DataAccess da = new DataAccess(conn);
		initRoleData();
		try {
			DataTable dt = da.executeDataTable(new QueryBuilder("select * from sys_role"));
			boolean returnValue = true;
			for (int i=0; i<dt.getRowCount(); i++) {
				Transaction trans = new Transaction();
				ZDRoleSchema role = new ZDRoleSchema();
				role.setProp1(dt.getString(i, "ID"));
				ZDRoleSet set = role.query();
				int type = Transaction.UPDATE;
				if (set.size() > 0) {
					role = set.get(0);
				} else {
					role.setRoleCode(dt.getString(i, "ROLE_CODE"));
					if (role.fill()) {
						role.setRoleCode(dt.getString(i, "ROLE_CODE") + i);
					}
					role.setBranchInnerCode("0001");
					role.setProp1(dt.getString(i, "ID"));
					role.setAddTime(DateUtil.parseDateTime(dt.getString(i, "CREATION_DATE")));
					role.setAddUser(dt.getString(i, "CREATED_BY"));
					type = Transaction.INSERT;
				}
				
				role.setRoleName(dt.getString(i, "ROLE_NAME"));
				role.setMemo(dt.getString(i, "DESCRIPTION"));
				trans.add(role, type);
				if (trans.commit()) {
					System.out.println("成功导入角色信息！");
					roleMap.put(role.getProp1(), role.getRoleCode());
				} else {
					System.out.println("导入角色信息失败！");
					returnValue = false;
				}
			}
			return returnValue;
		} catch (SQLException e) {
			System.out.println("**********************************");
			System.out.println("导入角色信息失败！");
			System.out.println("**********************************");
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (da != null) {
					da.close();
				}
				
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static boolean synchronizationUser() {
		DBConn conn = DBConnPool.getConnection("OracleUser");
		DataAccess da = new DataAccess(conn);
		try {
			boolean returnValue = true;
			int pageSize = 100;
			int count = Integer.parseInt(da.executeOneValue(new QueryBuilder("select count(*) from sys_user")).toString());
			int pageCount = new Double(Math.ceil(count * 1.0 / pageSize)).intValue();
			for (int i=0; i<pageCount; i++) {
				DataTable dt = da.executePagedDataTable(new QueryBuilder("select * from sys_user"), pageSize, i);
				for (int j=0; j<dt.getRowCount(); j++) {
					Transaction trans = new Transaction();
					int type = Transaction.UPDATE;
					ZDUserSchema user = new ZDUserSchema();
					user.setProp1(dt.getString(j, "ID"));
					ZDUserSet set = user.query();
					if (set.size() > 0) {
						user = set.get(0);
					} else {
						type = Transaction.INSERT;
						user.setUserName(dt.getString(j, "USER_NAME"));
						if (user.fill()) {
							user.setUserName(dt.getString(j, "USER_NAME") + (i*100+j));
							user.setProp6("N");
						} else {
							user.setProp6("Y");
						}
						
						user.setAddTime(DateUtil.parseDateTime(dt.getString(j, "CREATION_DATE")));
						user.setAddUser(dt.getString(j, "CREATED_BY"));
						user.setProp1(dt.getString(j, "ID"));
						user.setType("0");
						user.setBranchInnerCode("0001");
					}
					
					user.setPassword(dt.getString(j, "USER_PASSWORD"));
					user.setEmail(dt.getString(j, "EMAIL"));
					user.setIsBranchAdmin("N");
					user.setProp2(dt.getString(j, "EMPLOYEE_ID"));
					user.setProp3(dt.getString(j, "IC_NO"));
					user.setProp4(dt.getString(j, "CUSTOMER_ID"));
					user.setProp5(dt.getString(j, "FAX"));
					if (StringUtil.isNotEmpty(dt.getString(j, "VALID_FLAG")) && "1".equals(dt.getString(j, "VALID_FLAG"))) {
						user.setStatus("N");
					} else {
						user.setStatus("S");
					}
					user.setTel(dt.getString(j, "TEL"));
					trans.add(user, type);
					if (trans.commit()) {
						System.out.println("成功导入用户！");
						userMap.put(user.getProp1(), user.getUserName());
					} else {
						System.out.println("导入用户失败！");
						returnValue = false;
					}
				}
			}
			return returnValue;
		} catch (SQLException e) {
			System.out.println("**********************************");
			System.out.println("导入用户信息失败！");
			System.out.println("**********************************");
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (da != null) {
					da.close();
				}
				
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static boolean dealUserRole() {
		DBConn conn = DBConnPool.getConnection("OracleUser");
		DataAccess da = new DataAccess(conn);
		try{
			initRoleData();
			initUserData();
			boolean returnValue = true;
			Transaction trans = new Transaction();
			trans.add(new QueryBuilder("delete from zduserrole"));
			if (trans.commit()) {
				int pageSize = 100;
				int count = Integer.parseInt(da.executeOneValue(new QueryBuilder("select count(*) from sys_user_role")).toString());
				int pageCount = new Double(Math.ceil(count * 1.0 / pageSize)).intValue();
				for (int i=0; i<pageCount; i++) {
					DataTable dt = da.executePagedDataTable(new QueryBuilder("select * from sys_user_role"), pageSize, i);
					trans = new Transaction();
					for (int j=0; j<dt.getRowCount(); j++) {
						if (StringUtil.isEmpty(roleMap.getString(dt.getString(j, "SYS_ROLE_ID"))) 
								|| StringUtil.isEmpty(userMap.getString(dt.getString(j, "SYS_USER_ID")))) {
							continue;
						}
						ZDUserRoleSchema userrole = new ZDUserRoleSchema();
						userrole.setAddTime(DateUtil.parseDateTime(dt.getString(j, "CREATION_DATE")));
						userrole.setAddUser(dt.getString(j, "CREATED_BY"));
						userrole.setMemo(dt.getString(j, "VERSION"));
						userrole.setProp1(dt.getString(j, "ID"));
						userrole.setRoleCode(roleMap.getString(dt.getString(j, "SYS_ROLE_ID")));
						userrole.setUserName(userMap.getString(dt.getString(j, "SYS_USER_ID")));
						trans.add(userrole, Transaction.INSERT);
					}
					
					if (trans.commit()) {
						System.out.println("**********************************");
						System.out.println("成功处理用户角色关联！");
						System.out.println("**********************************");
					} else {
						System.out.println("**********************************");
						System.out.println("处理用户角色关联关系失败！");
						System.out.println("**********************************");
						returnValue = false;
					}
				}
			}
			return returnValue;
		} catch (SQLException e) {
			System.out.println("**********************************");
			System.out.println("处理用户角色关联关系失败！");
			System.out.println("**********************************");
			e.printStackTrace();
			return false;
		}
	}
	
	private static boolean dealDeptUser() {
		DBConn conn = DBConnPool.getConnection("OracleEmployeer");
		DataAccess da = new DataAccess(conn);
		initDeptData();
		try {
			boolean returnValue = true;
			int pageSize = 100;
			int count = Integer.parseInt(da.executeOneValue(new QueryBuilder("select count(*) from HR_EMPLOYEE_DEPT_POSITION "
					+ "where IS_CHIEF_DEPT='Y'")).toString());
			int pageCount = new Double(Math.ceil(count * 1.0 / pageSize)).intValue();
			for (int i=0; i<pageCount; i++) {
				DataTable dt = da.executePagedDataTable(new QueryBuilder("select * from HR_EMPLOYEE_DEPT_POSITION " 
						+ "where IS_CHIEF_DEPT='Y'"), pageSize, i);
				Transaction trans = new Transaction();
				for (int j=0; j<dt.getRowCount(); j++) {
					String deptID = dt.getString(j, "HR_DEPT_ID");
					String HR_EMPLOYEE_ID = dt.getString(j, "HR_EMPLOYEE_ID");
					Object name = da.executeOneValue(new QueryBuilder("select NAME from HR_EMPLOYEE where ID=?"
							, HR_EMPLOYEE_ID));
					QueryBuilder qb = new QueryBuilder("update zduser set BranchInnerCode=?,RealName=? where Prop2=?");
					qb.add(deptMap.getString(deptID));
					if (name != null) {
						qb.add(name.toString());
					} else {
						qb.add("");
					}
					qb.add(HR_EMPLOYEE_ID);
					trans.add(qb);
				}
				
				if (trans.commit()) {
					System.out.println("**********************************");
					System.out.println("部门用户关联关系处理成功！");
					System.out.println("**********************************");
				} else {
					System.out.println("**********************************");
					System.out.println("部门用户关联关系处理失败！");
					System.out.println("**********************************");
					returnValue = false;
				}
			}
			return returnValue;
		} catch (SQLException e) {
			System.out.println("**********************************");
			System.out.println("部门用户关联关系处理失败！");
			System.out.println("**********************************");
			e.printStackTrace();
			return false;
		}
	}
}
