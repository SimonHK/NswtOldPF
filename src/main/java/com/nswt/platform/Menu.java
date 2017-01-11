package com.nswt.platform;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.nswt.framework.Page;
import com.nswt.framework.User;
import com.nswt.framework.controls.DataGridAction;
import com.nswt.framework.data.DataRow;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.HtmlUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.pub.NoUtil;
import com.nswt.schema.ZDMenuSchema;
import com.nswt.schema.ZDMenuSet;

/**
 * @Author NSWT
 * @Date 2007-6-19
 * @Mail nswt@nswt.com.cn
 */
public class Menu extends Page {
	public static Mapx MenuCacheMap = new Mapx();

	static {
		updateCache();
	}

	private static void updateCache() {
		String sql = "select * from ZDMenu where (parentid in(select id from ZDMenu where parentid=0 and visiable='Y') or parentid=0) and visiable='Y' order by OrderFlag,id";
		DataTable dt = new QueryBuilder(sql).executeDataTable();
		for (int i = 0; i < dt.getRowCount(); i++) {
			MenuCacheMap.put(dt.getString(i, "URL"), dt.getString(i, "ID"));
		}
	}
	
	public static void dg1DataBind(DataGridAction dga) {
		String sql = "select ID,ParentID,Name,Icon,URL,Visiable,Addtime,Memo,Type,'' as Expand,'' as TreeLevel from ZDMenu order by OrderFlag,id";
		DataTable dt = new QueryBuilder(sql).executeDataTable();
		for (int i = 0; i < dt.getRowCount(); i++) {
			if ("1".equals(dt.get(i, "Type"))) {
				dt.set(i, "Expand", "Y");
			} else {
				dt.set(i, "Expand", "N");
			}
			if ("2".equals(dt.get(i, "Type"))) {
				dt.set(i, "TreeLevel", "1");
			} else {
				dt.set(i, "TreeLevel", "0");
			}
		}
		dga.bindData(dt);
	}

	public void dg1Edit() {
		DataTable dt = (DataTable) Request.get("DT");
		ZDMenuSet set = new ZDMenuSet();
		for (int i = 0; i < dt.getRowCount(); i++) {
			ZDMenuSchema menu = new ZDMenuSchema();
			menu.setID(Integer.parseInt(dt.getString(i, "ID")));
			menu.fill();
			menu.setName(dt.getString(i, "Name"));
			menu.setURL(dt.getString(i, "URL"));
			menu.setMemo(dt.getString(i, "Memo"));
			menu.setIcon(dt.getString(i, "Icon"));
			menu.setVisiable(dt.getString(i, "Visiable"));
			if (menu.getParentID() == 0) {
				if (dt.getString(i, "Expand").equals("Y")) {
					menu.setType("1");
				} else {
					menu.setType("3");
				}
			}
			set.add(menu);
		}
		if (set.update()) {
			updateCache();
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("��������!");
		}
	}

	public static Mapx init(Mapx params) {
		Mapx map = new Mapx();
		DataTable dt = new QueryBuilder("select name,id from zdmenu where ParentID=0 order by OrderFlag,id").executeDataTable();
		map.put("ParentMenu", HtmlUtil.dataTableToOptions(dt));
		return map;
	}

	public void add() {
		ZDMenuSchema menu = new ZDMenuSchema();
		menu.setIcon($V("Icon").substring($V("Icon").indexOf("Icons/")));
		menu.setID(NoUtil.getMaxID("MenuID"));
		menu.setAddTime(new Date());
		menu.setAddUser(User.getUserName());
		menu.setMemo($V("Memo"));
		menu.setName($V("Name"));
		menu.setURL($V("URL"));
		menu.setVisiable($V("Visiable"));
		menu.setParentID(Long.parseLong($V("ParentID")));
		DataTable parentDT = new QueryBuilder("select * from zdmenu where parentID = ? order by orderflag,id"
				, Long.parseLong($V("ParentID"))).executeDataTable();
		if ("0".equals($V("ParentID"))) {
			parentDT = new QueryBuilder("select * from zdmenu order by orderflag,id").executeDataTable();
		}
		long orderflag = 0;
		if (parentDT != null && parentDT.getRowCount() > 0) {
			orderflag = Long.parseLong(parentDT.getString(parentDT.getRowCount()-1, "OrderFlag"));
		} else {
			orderflag = Long.parseLong(new QueryBuilder("select OrderFlag from zdmenu where ID = ?"
					, Long.parseLong($V("ParentID"))).executeString());
			if ("0".equals($V("ParentID"))) {
				orderflag = 0;
			}
		}
		menu.setOrderFlag(orderflag+1);
		Transaction trans = new Transaction();
		if (menu.getParentID() == 0) {
			menu.setType("1");
		} else {
			menu.setType("2");
		}
		
		trans.add(new QueryBuilder("update zdmenu set orderflag = orderflag + 1 where orderflag > ?", orderflag));
		trans.add(menu, Transaction.INSERT);
		if (trans.commit()) {
			updateCache();
			Response.setStatus(1);
			Response.setMessage("��ӳɹ�!");
		} else {
			Response.setStatus(0);
			Response.setMessage("���ʧ�ܣ��������ݿ�ʱ��������!");
		}
	}

	public void del() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("����IDʱ��������!");
			return;
		}
		ZDMenuSchema menu = new ZDMenuSchema();
		ZDMenuSet set = menu.query(new QueryBuilder("where id in (" + ids + ")"));
		StringBuffer menuLog = new StringBuffer("ɾ���˵���");
		for (int i = 0; i < set.size(); i++) {
			menu = set.get(i);
			if (menu.getParentID() == 0) {
				long count = new QueryBuilder("select count(*) from zdmenu where parentid=" + menu.getID() 
						+ " and id not in (" + ids + ")").executeLong();
				if (count > 0) {
					Response.setStatus(0);
					UserLog.log(UserLog.SYSTEM, UserLog.SYSTEM_DELMENU, "ɾ���˵�"+menu.getName()+"ʧ��", Request.getClientIP());
					Response.setMessage("����ɾ���˵�\"" + menu.getName() + "\",�ò˵��»����Ӳ˵�δ��ɾ��!");
					return;
				}
			}
			menuLog.append(menu.getName()+",");
		}
		if (set.delete()) {
			updateCache();
			Response.setStatus(1);
			UserLog.log(UserLog.SYSTEM, UserLog.SYSTEM_DELMENU, menuLog+"�ɹ�", Request.getClientIP());
			Response.setMessage("ɾ���ɹ�!");
		} else {
			Response.setStatus(0);
			UserLog.log(UserLog.SYSTEM, UserLog.SYSTEM_DELMENU, menuLog+"ʧ��", Request.getClientIP());
			Response.setMessage("ɾ��ʧ�ܣ��������ݿ�ʱ��������!");
		}
	}
	
	public void sortMenu () {
		String orderMenu = $V("OrderMenu");
		String nextMenu = $V("NextMenu");
		String ordertype = $V("OrderType");
		if (StringUtil.isEmpty(orderMenu)||StringUtil.isEmpty(nextMenu)||StringUtil.isEmpty(ordertype)) {
			Response.setLogInfo(0, "������������");
			return;
		}
		
		Transaction trans = new Transaction();
		DataTable DT = new DataTable();
		DataTable parentDT = new QueryBuilder("select * from zdMenu where parentID = 0 order by orderflag,id").executeDataTable();
		for (int i = 0; i < parentDT.getRowCount(); i++) {
			DT.insertRow(parentDT.getDataRow(i));
			DataTable childDT = new QueryBuilder("select * from zdMenu where parentID = ? order by orderflag,id",parentDT.getLong(i, "ID")).executeDataTable();
			for (int j = 0; j < childDT.getRowCount(); j++) {
				DT.insertRow(childDT.getDataRow(j));
			}
		}
		
		List branchList = new ArrayList();
		
		//��Ҫ����Ĳ˵����ڵ������ò˵������Ӳ˵���
		DataTable orderDT = new QueryBuilder("select * from zdMenu where parentID = ? or id = ? order by orderflag,id"
				, Long.parseLong(orderMenu), Long.parseLong(orderMenu)).executeDataTable();
		
		//Ҫ���ã��˵�ǰ��˵��󣩲˵�����Ӧ����
		DataTable nextDT = new QueryBuilder("select * from zdMenu where parentID = ? or id = ? order by orderflag,id"
				, Long.parseLong(nextMenu), Long.parseLong(nextMenu)).executeDataTable();
		
		//����������
		if ("before".equalsIgnoreCase(ordertype)) {
			for (int i=0; DT != null && i<DT.getRowCount(); i++) {
				if (DT.getString(i, "ID").equals(nextMenu)) {
					for (int m=0; orderDT != null && m<orderDT.getRowCount(); m++) {
						branchList.add(orderDT.getDataRow(m));
					}
				} else if (DT.getString(i, "ID").equals(orderMenu)) {
					//��������˵���
					i = i -1 + orderDT.getRowCount();
					continue;
				}
				branchList.add(DT.getDataRow(i));
			}
			
		//����������
		} else if ("after".equalsIgnoreCase(ordertype)) {
			for (int i=0; DT != null && i<DT.getRowCount(); i++) {
				if (DT.getString(i, "ID").equals(orderMenu)) {
					//��������˵���
					i = i -1 + orderDT.getRowCount();
					continue;
				} else if (DT.getString(i, "ID").equals(nextMenu)) {
					
					//���� ѡ���������� ����˵���
					for (int m=0; nextDT != null && m<nextDT.getRowCount(); m++) {
						branchList.add(nextDT.getDataRow(m));
					}
					
					for (int j=0; orderDT != null && j<orderDT.getRowCount(); j++) {
						branchList.add(orderDT.getDataRow(j));
					}
					
					//����ѭ������
					i = i -1 + nextDT.getRowCount();
				} else {
					branchList.add(DT.getDataRow(i));
				}
			}
		}
		
		for (int i=0;i<branchList.size();i++) {
			DataRow dr = (DataRow) branchList.get(i);
			trans.add(new QueryBuilder("update zdmenu set orderflag = ? where ID = ?", i, dr.getLong("ID")));
		}
		if (trans.commit()) {
			Response.setLogInfo(1, "����ɹ���");
		} else {
			Response.setLogInfo(0, "����ʧ�ܣ�");
		}
	}
}
