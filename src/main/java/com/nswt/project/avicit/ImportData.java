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
import com.nswt.schema.ZDUserRoleSchema;
import com.nswt.schema.ZDUserSchema;

public class ImportData {
	
	private static Mapx deptMap = new Mapx();
	
	private static Mapx roleMap = new Mapx();
	
	private static Mapx userMap = new Mapx();
	
	public static void main(String[] args) {
		Transaction trans = new Transaction();
		trans.add(new QueryBuilder("delete from zdbranch"));
		trans.add(new QueryBuilder("delete from zdmaxno where notype='BranchInnerCode'"));
		trans.add(new QueryBuilder("delete from zduserrole"));
		trans.add(new QueryBuilder("delete from zdrole"));
		trans.add(new QueryBuilder("delete from zduser"));
		trans.commit();
		DBConn employeerConn = DBConnPool.getConnection("OracleEmployeer");
		DataAccess employeerda = new DataAccess(employeerConn);
		synchronizationDept(employeerda);
		DBConn userconn = DBConnPool.getConnection("OracleUser");
		DataAccess userda = new DataAccess(userconn);
		synchronizationRole(userda);
		synchronizationUser(userda);
		dealUserRole(userda);
		dealDeptUser(employeerda);
		try{
			if (employeerda != null) {
				employeerda.close();
			}
			
			if (employeerConn != null) {
				employeerConn.close();
			}
			
			if (userda != null) {
				userda.close();
			}
			
			if (userconn != null) {
				userconn.close();
			}
		} catch (SQLException e) {
			System.out.println("**********************************");
			System.out.println("数据导入失败！");
			System.out.println("**********************************");
			e.printStackTrace();
		}
	}
	
	public static boolean excute() {
		Transaction trans = new Transaction();
		trans.add(new QueryBuilder("delete from zdbranch"));
		trans.add(new QueryBuilder("delete from zdmaxno where notype='BranchInnerCode'"));
		trans.add(new QueryBuilder("delete from zduserrole"));
		trans.add(new QueryBuilder("delete from zdrole"));
		trans.add(new QueryBuilder("delete from zduser"));
		trans.commit();
		DBConn employeerConn = DBConnPool.getConnection("OracleEmployeer");
		DataAccess employeerda = new DataAccess(employeerConn);
		synchronizationDept(employeerda);
		DBConn userconn = DBConnPool.getConnection("OracleUser");
		DataAccess userda = new DataAccess(userconn);
		synchronizationRole(userda);
		synchronizationUser(userda);
		dealUserRole(userda);
		dealDeptUser(employeerda);
		try{
			if (employeerda != null) {
				employeerda.close();
			}
			
			if (employeerConn != null) {
				employeerConn.close();
			}
			
			if (userda != null) {
				userda.close();
			}
			
			if (userconn != null) {
				userconn.close();
			}
			
			return true;
		} catch (SQLException e) {
			System.out.println("**********************************");
			System.out.println("数据导入失败！");
			System.out.println("**********************************");
			e.printStackTrace();
			return false;
		}
	}
	
	public static void synchronizationDept(DataAccess da) {
		if (da != null) {
			try {
				DataTable dt = da.executeDataTable(new QueryBuilder("select * from HR_DEPT " 
						+ "order by PARENT_DEPT_ID,ORDER_BY"));
				for (int j=0; j<dt.getRowCount(); j++) {
					Transaction trans = new Transaction();
					ZDBranchSchema branch = new ZDBranchSchema();
					branch.setProp1(dt.getString(j, "ID"));
					ZDBranchSet set = branch.query();
					int type = Transaction.UPDATE;
					if (set.size() > 0) {
						branch = set.get(0);
					} else {
						String parentInnerCode = deptMap.getString(dt.getString(j, "PARENT_DEPT_ID"));
						if (StringUtil.isNotEmpty(parentInnerCode)) {
							branch.setBranchInnerCode(NoUtil.getMaxNo("BranchInnerCode", parentInnerCode, 4));
							branch.setParentInnerCode(parentInnerCode);
						} else {
							branch.setBranchInnerCode(NoUtil.getMaxNo("BranchInnerCode", 4));
							branch.setParentInnerCode("0");
						}
						branch.setProp1(dt.getString(j, "ID"));
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
					if (trans.commit()) {
						deptMap.put(branch.getProp1(), branch.getBranchInnerCode());
						System.out.println("**********************************");
						System.out.println("成功导入部门信息！");
						System.out.println("**********************************");
					} else {
						System.out.println("**********************************");
						System.out.println("导入失败！");
						System.out.println("**********************************");
					}
				}
				
			} catch (SQLException e) {
				System.out.println("**********************************");
				System.out.println("导入失败！");
				System.out.println("**********************************");
				e.printStackTrace();
			}
		}
	}
	
	public static void synchronizationRole(DataAccess da) {
		if (da != null) {
			try {
				DataTable dt = da.executeDataTable(new QueryBuilder("select * from sys_role"));
				for (int i=0; i<dt.getRowCount(); i++) {
					Transaction trans = new Transaction();
					ZDRoleSchema role = new ZDRoleSchema();
					role.setRoleCode(dt.getString(i, "ROLE_CODE"));
					if (role.fill()) {
						role.setRoleCode(dt.getString(i, "ROLE_CODE") + i);
						role.setProp2("N");
					} else {
						role.setProp2("Y");
					}
					role.setRoleName(dt.getString(i, "ROLE_NAME"));
					role.setBranchInnerCode("0001");
					role.setProp1(dt.getString(i, "ID"));
					role.setMemo(dt.getString(i, "DESCRIPTION"));
					role.setAddTime(DateUtil.parseDateTime(dt.getString(i, "CREATION_DATE")));
					role.setAddUser(dt.getString(i, "CREATED_BY"));
					trans.add(role, Transaction.INSERT);
					if (trans.commit()) {
						System.out.println("成功导入角色信息！");
						roleMap.put(role.getProp1(), role.getRoleCode());
					} else {
						System.out.println("导入角色信息失败！");
					}
				}
			} catch (SQLException e) {
				System.out.println("**********************************");
				System.out.println("导入角色信息失败！");
				System.out.println("**********************************");
				e.printStackTrace();
			}
		}
	}
	
	public static void synchronizationUser(DataAccess da) {
		if (da != null) {
			try {
				int pageSize = 100;
				int count = Integer.parseInt(da.executeOneValue(new QueryBuilder("select count(*) from sys_user")).toString());
				int pageCount = new Double(Math.ceil(count * 1.0 / pageSize)).intValue();
				for (int i=0; i<pageCount; i++) {
					DataTable dt = da.executePagedDataTable(new QueryBuilder("select * from sys_user"), pageSize, i);
					for (int j=0; j<dt.getRowCount(); j++) {
						Transaction trans = new Transaction();
						int type = Transaction.INSERT;
						ZDUserSchema user = new ZDUserSchema();
						user.setUserName(dt.getString(j, "USER_NAME"));
						if (user.fill()) {
							user.setUserName(dt.getString(j, "USER_NAME") + (i*100+j));
							user.setProp6("N");
						} else {
							user.setProp6("Y");
						}
						user.setPassword(dt.getString(j, "USER_PASSWORD"));
						user.setAddTime(DateUtil.parseDateTime(dt.getString(j, "CREATION_DATE")));
						user.setAddUser(dt.getString(j, "CREATED_BY"));
						user.setBranchInnerCode("0001");
						user.setEmail(dt.getString(j, "EMAIL"));
						user.setIsBranchAdmin("N");
						user.setProp1(dt.getString(j, "ID"));
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
						user.setType("0");
						trans.add(user, type);
						if (trans.commit()) {
							System.out.println("成功导入用户！");
							userMap.put(user.getProp1(), user.getUserName());
						} else {
							System.out.println("导入用户失败！");
						}
					}
				}
			} catch (SQLException e) {
				System.out.println("**********************************");
				System.out.println("导入用户失败！");
				System.out.println("**********************************");
				e.printStackTrace();
			}
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
	
	public static void dealUserRole(DataAccess da) {
		if (da != null) {
			try {
				initRoleData();
				initUserData();
				int pageSize = 100;
				int count = Integer.parseInt(da.executeOneValue(new QueryBuilder("select count(*) from sys_user_role")).toString());
				int pageCount = new Double(Math.ceil(count * 1.0 / pageSize)).intValue();
				for (int i=0; i<pageCount; i++) {
					DataTable dt = da.executePagedDataTable(new QueryBuilder("select * from sys_user_role"), pageSize, i);
					Transaction trans = new Transaction();
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
					}
				}
			} catch (SQLException e) {
				System.out.println("**********************************");
				System.out.println("处理用户角色关联关系失败！");
				System.out.println("**********************************");
				e.printStackTrace();
			}
		}
	}
	
	private static void initDeptData() {
		if (deptMap == null) {
			DataTable dt = new QueryBuilder("select * from zdbranch order by orderflag").executeDataTable();
			deptMap = dt.toMapx("Prop1", "BranchInnerCode");
		}
	}
	
	public static void dealDeptUser(DataAccess da) {
		if (da != null) {
			try {
				initDeptData();
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
					}
				}
			} catch (SQLException e) {
				System.out.println("**********************************");
				System.out.println("部门用户关联关系处理失败！");
				System.out.println("**********************************");
				e.printStackTrace();
			}
		}
	}
}
