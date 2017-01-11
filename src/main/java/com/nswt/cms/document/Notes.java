package com.nswt.cms.document;

import java.util.Calendar;
import java.util.Date;

import com.nswt.framework.Config;
import com.nswt.framework.Page;
import com.nswt.framework.User;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.utility.DateUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.platform.pub.NoUtil;
import com.nswt.schema.ZCNotesSchema;
import com.nswt.schema.ZCNotesSet;

/**
 * @Author �º�ǿ
 * @Date 2008-03-20
 * @Mail chq@nswt.com
 */

public class Notes extends Page {
	public static Mapx init(Mapx params) {
		String curDate = ""; // ��ǰʱ��
		int firstDayOnWeek = 0;// ÿ����1�������ڼ�
		int dayInMonth = 0;// �õ�һ�����ж�����
		int countTR = 0; // �����м���tr

		Object o1 = params.get("cDate");
		Object o2 = params.get("Flag");
		if (o1 != null && o2 != null) {
			String Flag = o2.toString();
			String s = o1.toString();
			if ("-1".equals(Flag)) {// ��ǰһ����
				curDate = DateUtil.toString(DateUtil.addMonth(DateUtil.parse(s), -1));
			} else if ("0".equals(Flag)) { // ����
				curDate = s;
			} else { // ����һ����
				curDate = DateUtil.toString(DateUtil.addMonth(DateUtil.parse(s), 1));
			}
		} else {
			curDate = DateUtil.getCurrentDate();
		}

		Mapx map = new Mapx();
		StringBuffer sb = new StringBuffer();

		sb.append("<table width='100%' cellspacing='0' cellpadding='0' border='1' bordercolor='#aaccee'>");
		sb.append("<tr bgcolor='#DCF0FB' align='center'>");
		sb.append("<td width='15%' height='30'><font class='green'>������</font></td>");
		sb.append("<td width='14%'>����һ</td>");
		sb.append("<td width='14%'>���ڶ�</td>");
		sb.append("<td width='14%'>������</td>");
		sb.append("<td width='14%'>������</td>");
		sb.append("<td width='14%'>������</td>");
		sb.append("<td width='15%'><font class='green'>������</font></td>");
		sb.append("</tr>");

		firstDayOnWeek = DateUtil.getDayOfWeek(DateUtil.getFirstDayOfMonth(curDate));
		dayInMonth = DateUtil.getMaxDayOfMonth(DateUtil.parse(curDate));
		countTR = (int) Math.ceil((dayInMonth + firstDayOnWeek - 1) / 7.0);
		int m = 1;
		DataTable dt = null;

		for (int n = 1; n <= countTR; n++) {
			sb.append("<tr valign='top'>");
			for (int i = 1; i <= 7; i++) {
				sb.append("<td>");
				if ((i < firstDayOnWeek && n == 1) || m > dayInMonth) {
					sb.append("&nbsp;");
				} else {
					sb.append("<table width='100%' border='0' cellspacing='0' cellpadding='0' ");
					if (!(i < firstDayOnWeek && n == 1) && m <= dayInMonth) {
						sb.append("onDblClick='add(" + m + ");'");
					}
					sb.append(">");
					sb.append("<tr>");
					sb
							.append("<td width='40' height='20' align='center' valign='middle' style='border-bottom:#aaccee 1px solid; border-right:#aaccee 1px solid; background-color:#DCF0FB'>");
					if ((i < firstDayOnWeek && n == 1) || m > dayInMonth) {
						sb.append("&nbsp;");
					} else {
						sb.append(m + "��");
						m++;
						sb.append("<br>");
					}
					sb.append("<td bgcolor='#E9F0F8'>&nbsp;</td>");
					sb.append("</tr>");
					sb.append("<tr valign='top'>");
					// ȡֵ��tDate ��ǰ�������
					Calendar cal = Calendar.getInstance();
					cal.setTime(DateUtil.parse(curDate));
					cal.set(Calendar.DAY_OF_MONTH, m - 1);
					String tDate = DateUtil.toString(cal.getTime());
					QueryBuilder qb = new QueryBuilder("select title,id from zcnotes where notetime=? and adduser=?");
					if (Config.isDB2()) {
						qb.setSQL("select title,id from zcnotes where date(notetime)=? and adduser=?");
					}
					qb.add(tDate);
					qb.add(User.getUserName());
					dt = qb.executeDataTable();
					// ����ǵ���Ļ�������׻�ɫ
					if (DateUtil.getCurrentDate().equals(tDate)) {
						sb
								.append("<td colspan='2' height='55' bgcolor='#FFFFCC' onmouseover=\"this.bgColor='#EDFBD2'\" onmouseout=\"this.bgColor='#FFFFCC'\">");
					} else {
						sb
								.append("<td colspan='2' height='55' onmouseover=\"this.bgColor='#EDFBD2'\" onmouseout=\"this.bgColor=''\">");
					}
					for (int j = 0; j < dt.getRowCount(); j++) {
						String str = dt.get(j, 0).toString();
						if (str.length() > 12) {
							str = str.substring(0, 12) + "...";
						}
						sb.append("<a herf='#;' style='cursor:pointer;' onClick='edit(" + (m - 1) + ","
								+ dt.get(j, 1).toString() + ");' onContextMenu='showMenu(event," + (m - 1) + ","
								+ dt.get(j, 1).toString() + ");'>" + str + "</a>");
						sb.append("<br>");
					}
					sb.append("</td>");
					sb.append("</tr>");
					sb.append("</table>");
				}
				sb.append("</td>");
			}
			sb.append("</tr>");
		}
		sb.append("</table>");

		map.put("NoteContent", sb.toString());
		map.put("nowDate", DateUtil.toString(DateUtil.parse(curDate), "yyyy��MM��"));
		map.put("hnowDate", curDate);
		return map;
	}

	public static Mapx initDialog(Mapx params) {
		Object o = params.get("ID");
		Mapx map = new Mapx();
		if (o != null) {
			ZCNotesSchema notes = new ZCNotesSchema();
			notes.setID(Integer.parseInt(o.toString()));
			notes.query();
			ZCNotesSet set = notes.query();
			if (set.size() < 1) {
				return null;
			}
			notes = set.get(0);
			map.put("Title", notes.getTitle());
			map.put("Content", notes.getContent());
		}
		return map;
	}

	public void save() {
		ZCNotesSchema notes = new ZCNotesSchema();
		// ����
		if ("".equals($V("ID"))) {
			notes.setID(NoUtil.getMaxID("NotesID"));
			notes.setTitle($V("Title"));
			notes.setContent($V("Content"));
			// ���㱸��ʱ��
			String nowDate = $V("nowDate");
			String DateOfMonth = $V("DateOfMonth");
			Calendar cal = Calendar.getInstance();
			cal.setTime(DateUtil.parse(nowDate));
			cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(DateOfMonth));
			Date tDate = cal.getTime();
			notes.setNoteTime(tDate);

			notes.setAddTime(new Date());
			notes.setAddUser(User.getUserName());
			if (notes.insert()) {
				Response.setStatus(1);
			} else {
				Response.setStatus(0);
				Response.setMessage("��������!");
			}
		}
		// �޸�
		else {
			notes.setID(Integer.parseInt($V("ID")));
			notes.fill();
			notes.setTitle($V("Title"));
			notes.setContent($V("Content"));
			notes.setModifyTime(new Date());
			if (notes.update()) {
				Response.setStatus(1);
			} else {
				Response.setStatus(0);
				Response.setMessage("��������!");
			}
		}
	}

	public void del() {
		String id = $V("ID");
		ZCNotesSchema notes = new ZCNotesSchema();
		notes.setID(Integer.parseInt(id));
		if (notes.delete()) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("��������!");
		}
	}
}
