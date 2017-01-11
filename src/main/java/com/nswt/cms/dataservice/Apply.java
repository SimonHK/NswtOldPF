package com.nswt.cms.dataservice;

import java.util.Date;

import com.nswt.framework.Ajax;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.utility.HtmlUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.platform.pub.NoUtil;
import com.nswt.schema.ZCApplySchema;

public class Apply extends Ajax {

	public static final String Gender_F = "F";

	public static final String Gender_M = "M";

	public final static Mapx Gender_MAP = new Mapx();

	static {
		Gender_MAP.put(Gender_M, "��");
		Gender_MAP.put(Gender_F, "Ů");
	}

	public static final String EduLevel_Uni = "05";

	public static final String EduLevel_Jun = "04";

	public static final String EduLevel_Mas = "06";

	public final static Mapx EduLevel_MAP = new Mapx();

	static {
		EduLevel_MAP.put(EduLevel_Uni, "����");
		EduLevel_MAP.put(EduLevel_Jun, "ר��");
		EduLevel_MAP.put(EduLevel_Mas, "˶ʿ");
	}

	public static final String Political_Pol = "01";

	public static final String Political_Pro = "02";

	public static final String Political_Lea = "03";

	public static final String Political_Peo = "04";

	public static final String Political_Oth = "05";

	public final static Mapx Political_MAP = new Mapx();

	static {
		Political_MAP.put(Political_Pol, "�й���Ա");
		Political_MAP.put(Political_Pro, "Ԥ����Ա");
		Political_MAP.put(Political_Lea, "������Ա");
		Political_MAP.put(Political_Peo, "Ⱥ��");
		Political_MAP.put(Political_Oth, "����������ʿ");
	}

	public static Mapx init(Mapx params) {
		String position = params.getString("Position");
		params.put("Political", HtmlUtil.mapxToOptions(Political_MAP, true));
		params.put("EduLevel", HtmlUtil.mapxToOptions(EduLevel_MAP, true));
		params.put("Gender", HtmlUtil.mapxToRadios("Gender", Gender_MAP, Gender_M));
		params.put("Ethnicity", HtmlUtil.codeToOptions("Ethnicity"));
		params.put("District", "");
		params.put("Position", position);

		Mapx district = new QueryBuilder("select code,name from zddistrict where treelevel=1").executeDataTable().toMapx(0, 1);
		params.put("NativePlace", HtmlUtil.mapxToOptions(district));
		return params;
	}

	public void add() {
		ZCApplySchema apply = new ZCApplySchema();
		apply.setAddTime(new Date());
		apply.setID(NoUtil.getMaxID("ID"));
		apply.setValue(Request);
		apply.setAddUser("");
		apply.setAddTime(new Date());
		
		if (apply.insert()) {
			Response.setLogInfo(1, "�����ɹ�");
		} else {
			Response.setLogInfo(0, "����" + apply.getName() + "ʧ��!");
		}
	}
}
