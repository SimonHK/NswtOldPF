package com.nswt.misc;

import java.sql.SQLException;
import java.util.ArrayList;

import com.nswt.cms.stat.StatUtil;
import com.nswt.framework.data.DBConn;
import com.nswt.framework.data.DBConnPool;
import com.nswt.framework.data.DataAccess;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.DataTableUtil;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.utility.FileUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.schema.ZDDistrictSchema;
import com.nswt.schema.ZDIPRangeSchema;
import com.nswt.schema.ZDIPRangeSet;
import com.nswt.schema.ZDIPSchema;
import com.nswt.schema.ZDIPSet;

/**
 * @Author ������
 * @Date 2008-11-17
 * @Mail nswt@nswt.com.cn
 */
public class ImportIPDB {
	public static void main(String[] args) {
		// importTXT();
		// fixAddress();
		// fixDistrictCode();
		// checkIP();
		// importIPRange();
		// verify();

		exportIPRangeAsSQL();

		// System.out.println(new
		// QueryBuilder("select Name from ZDDistrict where Code=?", StatUtil
		// .getDistrictCode("119.6.119.5")).executeString());
		// System.out.println(new
		// QueryBuilder("select Name from ZDDistrict where Code=?", StatUtil
		// .getDistrictCode("119.4.99.97")).executeString());
	}

	public static void exportIPRangeAsSQL() {
		ZDIPRangeSet set = new ZDIPRangeSchema().query();
		StringBuffer sb = new StringBuffer();
		sb.append("delete from table ZDIPRange;\n");
		for (int i = 0; i < set.size(); i++) {
			ZDIPRangeSchema r = set.get(i);
			sb.append("insert into ZDIPRange (DistrictCode,IPRanges) values ('" + r.getDistrictCode() + "','"
					+ r.getIPRanges() + "');\n");
		}
		FileUtil.writeText("F:/IPRanges.sql", sb.toString(), "UTF-8");
		// System.out.println(sb);
	}

	public static void importTXT() {
		String[] pros = new String[] { "�½�", "�ຣ", "����", "����", "������", "����", "����", "�ӱ�", "���", "����", "ɽ��", "����", "����",
				"�㽭", "�Ϻ�", "����", "�㶫", "����", "���", "̨��", "����", "����", "����", "����", "�Ĵ�", "����", "ɽ��", "����", "����", "����",
				"����", "����", "����", "����" };
		String fileName = "F:/qqwry/ip.txt";
		new QueryBuilder("truncate table ZDIP").executeNoQuery();
		DataTable dt = DataTableUtil.txtToDataTable(fileName, new String[] { "IP3", "IP4", "Address", "Memo" }, "\\s+",
				"\\n");
		System.out.println(dt.getDataRow(0));
		System.out.println(dt.getDataRow(1));
		ZDIPSet set = new ZDIPSet();
		for (int i = 0; i < dt.getRowCount(); i++) {
			ZDIPSchema ip = new ZDIPSchema();
			String ip3 = dt.getString(i, "IP3");
			String ip4 = dt.getString(i, "IP4");
			String[] arr1 = ip3.split("\\.");
			String[] arr2 = ip4.split("\\.");
			long ip1 = Long.parseLong(arr1[0]) * 16777216L + Long.parseLong(arr1[1]) * 65536L + Long.parseLong(arr1[2])
					* 256L + Long.parseLong(arr1[3]) - 1L;
			long ip2 = Long.parseLong(arr2[0]) * 16777216 + Long.parseLong(arr2[1]) * 65536 + Long.parseLong(arr2[2])
					* 256 + Long.parseLong(arr2[3]) - 1;
			if (ip1 < 0) {
				ip1 = 0;
			}
			ip.setIP1(new Double(ip1).longValue());
			ip.setIP2(new Double(ip2).longValue());
			ip.setIP3(ip3);
			ip.setIP4(ip4);
			ip.setAddress(dt.getString(i, "Address"));
			ip.setMemo(dt.getString(i, "Memo"));
			if (ip.getMemo().length() > 60) {
				ip.setMemo(ip.getMemo().substring(0, 60));
			}
			boolean flag = false;
			for (int j = 0; j < pros.length; j++) {
				if (ip.getAddress().startsWith(pros[j])) {
					flag = true;
					break;
				}
			}
			if (!flag && ip.getAddress().indexOf("��ѧ") > 0) {
				System.out.println(ip);
			}
			set.add(ip);
			if ((i % 5000 == 0 && i != 0) || i == dt.getRowCount() - 1) {
				set.insert();
				set.clear();
			}
		}
	}

	public static void fixAddress() {
		new QueryBuilder("update ZDIP set Address='' where Address='CZ88.NET'").executeNoQuery();
		new QueryBuilder("update ZDIP set Memo='' where Memo='CZ88.NET'").executeNoQuery();
		new QueryBuilder("update ZDIP set Address='���ɹ������첼��' where Address='���ɹ������첼��'").executeNoQuery();
		new QueryBuilder("update ZDIP set Address='�½����������������' where Address='�½�������'").executeNoQuery();
		new QueryBuilder("update ZDIP set Address='�½���������������' where Address='�½�������'").executeNoQuery();
		new QueryBuilder("update ZDIP set Address='�½����������ɹ�������' where Address='�½�����������'").executeNoQuery();
		new QueryBuilder("update ZDIP set Address='���ɹź��ױ�����' where Address='���ɹź��ױ�����'").executeNoQuery();

		new QueryBuilder("update ZDIP set Address='�����к�����',Memo=concat(Address,Memo) where Address='������ͨ��ѧ'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='�����к�����',Memo=concat(Address,Memo) where Address='����ƾ���ѧ'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='�����к�����',Memo=concat(Address,Memo) where Address='�����ʵ��ѧ'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='�����г�����',Memo=concat(Address,Memo) where Address like '���⾭��ó�״�ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='�����к�����',Memo=concat(Address,Memo) where Address like '�廪��ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='����ʡ�人��',Memo=concat(Address,Memo) where Address like '����ũҵ��ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='����ʡ������',Memo=concat(Address,Memo) where Address like '���ִ�ѧ%'")
				.executeNoQuery();
		new QueryBuilder(
				"update ZDIP set Address='����ʡ������',Memo=concat(Address,Memo) where Address='���Ŵ�ѧ' and Memo like '����У��%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='����ʡ������',Memo=concat(Address,Memo) where Address like '���Ŵ�ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='����ʡ������',Memo=concat(Address,Memo) where Address like '������ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='�Ϻ���������',Memo=concat(Address,Memo) where Address like '�Ϻ���ͨ��ѧ����У��%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='�Ϻ���',Memo=concat(Address,Memo) where Address like '�Ϻ���ͨ��ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='�����к�����',Memo=concat(Address,Memo) where Address like '�����Ƽ���ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='�����к�����',Memo=concat(Address,Memo) where Address like '�������պ����ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='����ʡ�人��',Memo=concat(Address,Memo) where Address like '���пƼ���ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='����ʡ�人��',Memo=concat(Address,Memo) where Address like '�人��ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='ɽ��ʡ������',Memo=concat(Address,Memo) where Address like '��������ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='�����к�����',Memo=concat(Address,Memo) where Address like '������ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='����ʡ������',Memo=concat(Address,Memo) where Address like '������ͨ��ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='�����к�����',Memo=concat(Address,Memo) where Address like '������ͨ��ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='������ʡ��������',Memo=concat(Address,Memo) where Address like '��������ҵ��ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='������ʡ��������',Memo=concat(Address,Memo) where Address like '���������̴�ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='������ʡ��������',Memo=concat(Address,Memo) where Address like '����������ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='�����к�����',Memo=concat(Address,Memo) where Address like '������ҵ��ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='�����к�����',Memo=concat(Address,Memo) where Address like '�й�ũҵ��ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='����ʡ�Ͼ���',Memo=concat(Address,Memo) where Address like '���ϴ�ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='����ʡ�Ͼ���',Memo=concat(Address,Memo) where Address like '�Ͼ���ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='����ʡ��ɳ��',Memo=concat(Address,Memo) where Address like '����ʦ����ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='����ʡ�Ͼ���',Memo=concat(Address,Memo) where Address like '�Ͼ���ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='����ʡ������',Memo=concat(Address,Memo) where Address like '������ҵ��ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='����ʡ������',Memo=concat(Address,Memo) where Address like '����ʯ�ʹ�ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='������ɳƺ����',Memo=concat(Address,Memo) where Address like '�����ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='�����г�����',Memo=concat(Address,Memo) where Address like '������ҽҩ��ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='�����к�����',Memo=concat(Address,Memo) where Address like '��������ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='�����к�����',Memo=concat(Address,Memo) where Address like '������ҵ��ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='�����г�����',Memo=concat(Address,Memo) where Address like '�׶���ó��ѧ����%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='�����з�̨��',Memo=concat(Address,Memo) where Address like '�׶���ó��ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='�����к�����',Memo=concat(Address,Memo) where Address like '����ʦ����ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='����ʡ������',Memo=concat(Address,Memo) where Address like '����ʦ����ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='����ʡ������',Memo=concat(Address,Memo) where Address like '���ݴ�ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='����ʡ�ϲ���',Memo=concat(Address,Memo) where Address like '����ũҵ��ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='�㶫ʡ������',Memo=concat(Address,Memo) where Address like '��������ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='�Ĵ�ʡ�ɶ���',Memo=concat(Address,Memo) where Address like '�Ĵ���ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='�½���³ľ����',Memo=concat(Address,Memo) where Address like '�½���ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='����ʡ��ɳ��',Memo=concat(Address,Memo) where Address like '���ϴ�ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='�����к�����',Memo=concat(Address,Memo) where Address like '������վ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='�㶫ʡ������',Memo=concat(Address,Memo) where Address like '���ϴ�ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='�㶫ʡ������',Memo=concat(Address,Memo) where Address like '����ũҵ��ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='�����г�����',Memo=concat(Address,Memo) where Address like '����������ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='������ʯ��ɽ��',Memo=concat(Address,Memo) where Address like '����ʯ��ɽ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='�����',Memo=concat(Address,Memo) where Address like '�Ͽ���ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='����ʡ֣����',Memo=concat(Address,Memo) where Address like '֣�ݴ�ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='������',Memo=concat(Address,Memo) where Address like '������Ϣ�Ƽ���ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='����ʡ�Ͼ���',Memo=concat(Address,Memo) where Address like '�Ͼ�����ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='�㽭ʡ������',Memo=concat(Address,Memo) where Address like '�㽭��ҵ��ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='�㽭ʡ������',Memo=concat(Address,Memo) where Address like '�㽭�Ƽ�ѧԺ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='�ຣʡ������',Memo=concat(Address,Memo) where Address like '�ຣ��ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='����ʡ��ɳ��',Memo=concat(Address,Memo) where Address like '���ϿƼ���ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='�㽭ʡ������',Memo=concat(Address,Memo) where Address like '������ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='�Ϻ���',Memo=concat(Address,Memo) where Address like '�Ϻ�����ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='�Ϻ���',Memo=concat(Address,Memo) where Address like 'ͬ�ô�ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='����ʡ�Ͼ���',Memo=concat(Address,Memo) where Address like '�Ͼ���ҵ��ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='�Ĵ�ʡ������',Memo=concat(Address,Memo) where Address like '�Ĵ����ϿƼ���ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='�Ĵ�ʡ�ɶ���',Memo=concat(Address,Memo) where Address like '�ɶ�����ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='������ʡ��������',Memo=concat(Address,Memo) where Address like '������ʦ����ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='ɽ��ʡ̫ԭ��',Memo=concat(Address,Memo) where Address like '̫ԭ�Ƽ���ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='ɽ��ʡ̫ԭ��',Memo=concat(Address,Memo) where Address like '�б���ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='ɽ��ʡ�ൺ��',Memo=concat(Address,Memo) where Address like '�ൺ��ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='�㶫ʡ������',Memo=concat(Address,Memo) where Address like '���ϴ�ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='����ʡ�ϲ���',Memo=concat(Address,Memo) where Address like '�ϲ���ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='����ʡ������',Memo=concat(Address,Memo) where Address like '������ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='����ʡ�人��',Memo=concat(Address,Memo) where Address like '������ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='�Ĵ�ʡ�ɶ���',Memo=concat(Address,Memo) where Address like '�Ĵ�ʦ����ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='�Ĵ�ʡ�ɶ���',Memo=concat(Address,Memo) where Address like '�ɶ���Ϣ����ѧԺ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='�Ĵ�ʡ�ɶ���',Memo=concat(Address,Memo) where Address like '������ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='�Ϻ���',Memo=concat(Address,Memo) where Address like '����ʦ����ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='����ʡ�ϲ���',Memo=concat(Address,Memo) where Address like '������ͨ��ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='�Ϻ���',Memo=concat(Address,Memo) where Address like '��������ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='������ʡ��������',Memo=concat(Address,Memo) where Address like '������ҵ��ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='�Ϻ���',Memo=concat(Address,Memo) where Address like '������ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='�����к�����',Memo=concat(Address,Memo) where Address like '�׶�ʦ����ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='����ʡ֣����',Memo=concat(Address,Memo) where Address like '�ƺӿƼ���ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='����ʡ������',Memo=concat(Address,Memo) where Address like '������ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='����ʡ�人��',Memo=concat(Address,Memo) where Address like '���ϲƾ�������ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='������ʡ��������',Memo=concat(Address,Memo) where Address like '����ũҵ��ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='����ʡ������',Memo=concat(Address,Memo) where Address like '��������ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='����ʡ������',Memo=concat(Address,Memo) where Address like '������ҵ��ѧ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='�ӱ�ʡ������',Memo=concat(Address,Memo) where Address like '�����Ƽ�ѧԺ%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='�ӱ�ʡ������',Memo=concat(Address,Memo) where Address like '�����Ƽ�ѧԺ%'")
				.executeNoQuery();
	}

	public static void fixDistrictCode() {
		DataTable dt = new QueryBuilder("select Address,IP1,IP2 from ZDIP").executeDataTable();
		DataTable dt2 = new QueryBuilder(
				"select Name,Code from ZDDistrict where code like '11%' or code like '12%' or code like '31%' "
						+ "or code like '50%' or treelevel in (0,1,2) order by code desc").executeDataTable();
		Mapx map = dt2.toMapx(0, 1);
		Mapx map2 = dt2.toMapx(1, 0);
		Object[] names = dt2.getColumnValues(0);
		QueryBuilder qb = new QueryBuilder("update ZDIP set DistrictCode=? where ip1=? and ip2=?");
		qb.setBatchMode(true);
		for (int i = 0; i < dt.getRowCount(); i++) {
			String address = dt.getString(i, 0);
			for (int j = 0; j < names.length; j++) {
				String name = names[j].toString();
				String code = map.getString(name);
				String parentCode = code.substring(0, 4) + "00";
				if (parentCode.startsWith("11") || parentCode.startsWith("21") || parentCode.startsWith("31")
						|| parentCode.startsWith("50")) {
					parentCode = code.substring(0, 2) + "0000";
				}
				String parentName = map2.getString(parentCode);
				if (StringUtil.isNotEmpty(parentName) && !code.startsWith("00")) {
					if (address.indexOf(name) >= 0 && address.indexOf(parentName) >= 0) {
						qb.add(code);
						qb.add(dt.getString(i, "IP1"));
						qb.add(dt.getString(i, "IP2"));
						qb.addBatch();
						break;
					}
					if (address.indexOf(name.substring(0, 2)) >= 0 && address.indexOf(parentName.substring(0, 2)) >= 0) {
						qb.add(code);
						qb.add(dt.getString(i, "IP1"));
						qb.add(dt.getString(i, "IP2"));
						qb.addBatch();
						break;
					}
				}
			}
		}
		for (int i = 0; i < dt.getRowCount(); i++) {
			String address = dt.getString(i, 0);
			for (int j = 0; j < names.length; j++) {
				String name = names[j].toString();
				String code = map.getString(name);
				String parentCode = code.substring(0, 4) + "00";
				if (parentCode.startsWith("11") || parentCode.startsWith("21") || parentCode.startsWith("31")
						|| parentCode.startsWith("50")) {
					parentCode = code.substring(0, 2) + "0000";
				}
				String parentName = map2.getString(parentCode);
				if (StringUtil.isEmpty(parentName) || code.startsWith("00")) {
					if (address.indexOf(name) >= 0) {
						qb.add(code);
						qb.add(dt.getString(i, "IP1"));
						qb.add(dt.getString(i, "IP2"));
						qb.addBatch();
						break;
					}
					if (name.length() > 2) {
						if (address.indexOf(name.substring(0, 2)) >= 0) {
							qb.add(code);
							qb.add(dt.getString(i, "IP1"));
							qb.add(dt.getString(i, "IP2"));
							qb.addBatch();
							break;
						}
					}
				}
			}
		}
		qb.executeNoQuery();
	}

	public static void fixContry() {
		DBConn conn = DBConnPool.getConnection("DB_TY");
		DataAccess da = new DataAccess(conn);
		try {
			new QueryBuilder("delete from ZDDistrict where Code like '0%'").executeNoQuery();
			DataTable dt = da.executeDataTable(new QueryBuilder("select * from T_DM_GJDQ order by dmsx"));
			for (int i = 1; i <= dt.getRowCount(); i++) {
				ZDDistrictSchema d = new ZDDistrictSchema();
				d.setCode(StringUtil.leftPad(i + "", '0', 6));
				d.setCodeOrder(d.getCode());
				d.setName(dt.getString(i - 1, "MC"));
				d.setTreeLevel(0);
				d.setType("9");
				d.insert();
			}
			new QueryBuilder("update ZDDistrict set Code='000000',CodeOrder='000300' where Name = '�й�'")
					.executeNoQuery();
			System.out.println(dt);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				da.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void checkIP() {
		DataTable dt = new QueryBuilder("select IP1,IP2,DistrictCode from ZDIP order by ip1").executeDataTable();
		long last = 0;
		for (int i = 0; i < dt.getRowCount(); i++) {
			if (last != 0) {
				if (Long.parseLong(dt.getString(i, 0)) - 1 != last) {
					System.out.println(dt.getString(i, 0));
				}
			}
			last = Long.parseLong(dt.getString(i, 1));
		}
	}

	public static void importIPRange() {
		new QueryBuilder("delete from ZDIPRange").executeNoQuery();
		DataTable dt = new QueryBuilder("select IP1,IP2,DistrictCode from ZDIP order by DistrictCode,IP1")
				.executeDataTable();
		long lastIP2 = 0;
		String lastDistrictCode = null;
		ArrayList list = null;
		for (int i = 0; i < dt.getRowCount(); i++) {
			String DistrictCode = dt.getString(i, "DistrictCode");
			long IP1 = Long.parseLong(dt.getString(i, "IP1"));
			long IP2 = Long.parseLong(dt.getString(i, "IP2"));
			if (StringUtil.isEmpty(DistrictCode)) {
				continue;
			}
			if (!DistrictCode.equals(lastDistrictCode)) {
				if (list != null) {
					list.add(new Long(lastIP2));
					StringBuffer sb = new StringBuffer();
					for (int j = 0; j < list.size(); j += 2) {
						if (j != 0) {
							sb.append(",");
						}
						long t1 = ((Long) list.get(j)).longValue();
						long t2 = ((Long) list.get(j + 1)).longValue();
						sb.append(t1 + "+" + (t2 - t1));
					}
					ZDIPRangeSchema range = new ZDIPRangeSchema();
					range.setDistrictCode(lastDistrictCode);
					range.setIPRanges(sb.toString());
					range.insert();
				}
				list = new ArrayList();
				list.add(new Long(IP1));
				lastDistrictCode = DistrictCode;
			} else {
				if (IP1 != lastIP2 + 1) {
					list.add(new Long(lastIP2));
					list.add(new Long(IP1));
				}
			}
			lastIP2 = IP2;
		}

		// �������һ��
		list.add(new Long(lastIP2));
		StringBuffer sb = new StringBuffer();
		for (int j = 0; j < list.size(); j += 2) {
			if (j != 0) {
				sb.append(",");
			}
			long t1 = ((Long) list.get(j)).longValue();
			long t2 = ((Long) list.get(j + 1)).longValue();
			sb.append(t1 + "+" + (t2 - t1));
		}
		ZDIPRangeSchema range = new ZDIPRangeSchema();
		range.setDistrictCode(lastDistrictCode);
		range.setIPRanges(sb.toString());
		range.insert();
	}

	public static void verify() {
		DataTable dt = new QueryBuilder("select IP3,IP4,DistrictCode from ZDIP").executeDataTable();
		for (int i = 0; i < dt.getRowCount(); i++) {
			String code = dt.getString(i, 2);
			String ip1 = dt.getString(i, 0);
			String ip2 = dt.getString(i, 1);
			String str = StatUtil.getDistrictCode(ip1);
			if (StringUtil.isNotEmpty(code) && !code.equals(str)) {
				System.out.println(ip1 + "\t" + code + "\t" + str);
			}
			str = StatUtil.getDistrictCode(ip2);
			if (StringUtil.isNotEmpty(code) && !code.equals(str)) {
				System.out.println(ip2 + "\t" + code + "\t" + str);
			}
		}

	}
}
