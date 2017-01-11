package com.nswt.platform;

import java.util.Calendar;
import java.util.Date;

import com.nswt.framework.Page;
import com.nswt.framework.User;
import com.nswt.framework.controls.DataGridAction;
import com.nswt.framework.data.DataColumn;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.schedule.AbstractTaskManager;
import com.nswt.framework.schedule.CronManager;
import com.nswt.framework.schedule.CronMonitor;
import com.nswt.framework.utility.DateUtil;
import com.nswt.framework.utility.HtmlUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.pub.NoUtil;
import com.nswt.schema.ZDScheduleSchema;

/**
 * @Author ����
 * @Date 2008-3-14
 * @Mail lanjun@nswt.com
 */

public class Schedule extends Page {
	public static Mapx init(Mapx map) {
		Mapx params = CronManager.getInstance().getTaskTypes();
		String types = HtmlUtil.mapxToOptions(params, null, true);
		map.put("TypeCode", types);
		return map;
	}

	public void getUsableTask() {
		String type = $V("TypeCode");
		Mapx map = CronManager.getInstance().getConfigEnableTasks(type);
		Object[] ks = map.keyArray();
		Object[] vs = map.valueArray();
		for (int i = 0; i < map.size(); i++) {
			$S(ks[i].toString(), vs[i]);
		}
	}

	public static void dg1DataBind(DataGridAction dga) {
		String sql = "select * from ZDSchedule " + dga.getSortString();
		DataTable dt = new QueryBuilder(sql).executeDataTable();
		Mapx map = new Mapx();
		map.put("Y", "����");
		map.put("N", "ͣ��");
		dt.decodeColumn("IsUsing", map);

		map = CronManager.getInstance().getTaskTypes();
		dt.decodeColumn("TypeCode", map);

		dt.insertColumn("SourceIDName");
		dt.insertColumn(new DataColumn("NextRunTime", DataColumn.DATETIME));
		for (int i = dt.getRowCount() - 1; i >= 0; i--) {
			String typeCode = dt.getString(i, "TypeCode");
			String sourceID = dt.getString(i, "SourceID");

			// ��ֹ��ָ������ ������ݿ��д��ڼ�¼����framework.xml��ע���˸����񣬻���ֿ�ָ��
			Mapx taskMap = CronManager.getInstance().getConfigEnableTasks(typeCode);
			if (taskMap == null) {
				dt.deleteRow(i);
				continue;
			}
			String sourceIDName = taskMap.getString(sourceID);
			if (StringUtil.isEmpty(sourceIDName)) {// ˵�����ǵ�ǰ�û�������
				dt.deleteRow(i);
				continue;
			}
			dt.set(i, "SourceIDName", sourceIDName);
			Date nextRunTime = null;
			try {
				nextRunTime = CronMonitor.getNextRunTime(dt.getString(i, "CronExpression"));
			} catch (Exception e) {
				nextRunTime = DateUtil.parse("2999-01-01");
			}
			dt.set(i, "NextRunTime", nextRunTime);
		}
		dga.setTotal(dt.getRowCount());
		DataTable result = new DataTable(dt.getDataColumns(), null);
		for (int i = dga.getPageIndex() * dga.getPageSize(); i < (dga.getPageIndex() + 1) * dga.getPageSize()
				&& i < dt.getRowCount(); i++) {
			result.insertRow(dt.getDataRow(i));
		}
		dga.bindData(result);
	}

	public void save() {
		ZDScheduleSchema schedule = new ZDScheduleSchema();
		String id = $V("ID");
		if (StringUtil.isEmpty(id)) {
			schedule.setID(NoUtil.getMaxID("ScheduleID"));
			schedule.setAddTime(new Date());
			schedule.setAddUser(User.getUserName());
		} else {
			schedule.setID(Long.parseLong(id));
			schedule.fill();
			schedule.setModifyTime(new Date());
			schedule.setModifyUser(User.getUserName());
		}
		String startTime = $V("StartDate") + " " + $V("StartTime");
		Request.put("StartTime", startTime);
		if ($V("PlanType").equals("Period")) {
			String period = $V("Period");
			Calendar c = Calendar.getInstance();
			c.setTime(DateUtil.parseDateTime(startTime));
			StringBuffer sb = new StringBuffer();
			if ($V("PeriodType").equals("Minute")) {
				int minute = c.get(Calendar.MINUTE);
				sb.append(minute);
				sb.append("-");
				if (minute == 0) {
					sb.append("59");
				} else {
					sb.append(minute - 1);
				}
				sb.append("/");
				sb.append(period);
				sb.append(" * * * *");
			} else if ($V("PeriodType").equals("Hour")) {
				int minute = c.get(Calendar.MINUTE);
				int hour = c.get(Calendar.HOUR_OF_DAY);
				sb.append(minute);
				sb.append(" ");
				sb.append(hour);
				sb.append("-");
				if (hour == 0) {
					sb.append("23");
				} else {
					sb.append(hour - 1);
				}
				sb.append("/");
				sb.append(period);
				sb.append(" * * *");
			} else if ($V("PeriodType").equals("Day")) {
				int minute = c.get(Calendar.MINUTE);
				int hour = c.get(Calendar.HOUR_OF_DAY);
				int day = c.get(Calendar.DAY_OF_MONTH);
				sb.append(minute);
				sb.append(" ");
				sb.append(hour);
				sb.append(" ");
				sb.append(day);
				sb.append("-");
				sb.append(day - 1);
				sb.append("/");
				sb.append(period);
				sb.append(" * *");
			} else if ($V("PeriodType").equals("Month")) {
				int minute = c.get(Calendar.MINUTE);
				int hour = c.get(Calendar.HOUR_OF_DAY);
				int day = c.get(Calendar.DAY_OF_MONTH);
				int month = c.get(Calendar.MONTH);
				sb.append(minute);
				sb.append(" ");
				sb.append(hour);
				sb.append(" ");
				sb.append(day);
				sb.append(" ");
				sb.append(month);
				sb.append("-");
				sb.append(month - 1);
				sb.append("/");
				sb.append(period);
				sb.append(" *");
			}
			Request.put("CronExpression", sb.toString());
		}
		schedule.setValue(Request);
		try {
			CronMonitor.getNextRunTime(schedule.getCronExpression());
			boolean flag = StringUtil.isEmpty(id) ? schedule.insert() : schedule.update();
			if (!flag) {
				Response.setError("��������!");
			} else {
				Response.setMessage("�����ɹ�!");
			}
		} catch (Exception e) {
			Response.setError("��������:Cron���ʽ����ȷ!");
		}

	}

	public void del() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("����IDʱ��������!");
			return;
		}
		String sql = "delete from ZDSchedule where id in (" + ids + ")";
		Transaction trans = new Transaction();
		trans.add(new QueryBuilder(sql));
		if (!trans.commit()) {
			UserLog.log(UserLog.SYSTEM, UserLog.SYSTEM_DELSCHEDULE, "ɾ����ʱ����ʧ��", Request.getClientIP());
			Response.setError("��������!");
		} else {
			UserLog.log(UserLog.SYSTEM, UserLog.SYSTEM_DELSCHEDULE, "ɾ����ʱ����ɹ�", Request.getClientIP());
			Response.setMessage("�����ɹ�!");
		}
	}

	public void execute() {
		long id = Long.parseLong($V("ID"));
		ZDScheduleSchema s = new ZDScheduleSchema();
		s.setID(id);
		if (!s.fill()) {
			Response.setError("���񲻴��ڻ򼺱�ɾ��!");
			return;
		}
		AbstractTaskManager ctm = CronManager.getInstance().getCronTaskManager(s.getTypeCode());
		if (ctm.isRunning(s.getSourceID())) {
			Response.setMessage("<font color=red>�����������У���ȴ��������������!</font>");
		} else {
			ctm.execute(s.getSourceID());
			Response.setMessage("����ʼ����!");
		}
	}

	public static void main(String[] args) {
		try {
			Date d = CronMonitor.getNextRunTime("50 16 25-24/1 * *");
			System.out.println(DateUtil.toDateTimeString(d));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
