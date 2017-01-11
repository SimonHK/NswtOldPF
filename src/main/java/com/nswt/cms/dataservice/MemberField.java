package com.nswt.cms.dataservice;

import java.util.Date;
import com.nswt.framework.Page;
import com.nswt.framework.User;
import com.nswt.framework.controls.DataGridAction;
import com.nswt.framework.controls.SelectTag;
import com.nswt.framework.data.DataRow;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.HtmlUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.member.Member;
import com.nswt.platform.Application;
import com.nswt.schema.ZDMemberFieldSchema;
import com.nswt.schema.ZDMemberFieldSet;
import com.nswt.schema.ZDMemberSchema;

/**
 * @Author �솴
 * @Date 2008-8-1
 * @Mail xuzhe@nswt.com
 */

public class MemberField extends Page {
	
	public static final String PREFIX = "_MC_"; // �Զ����ֶ�ǰ׺
	// ������ʽ-InputType
	public static String Input = "input";

	public static String Text = "textarea";

	public static String Selecter = "select";

	public static String Radio = "radio";

	public static String Checkbox = "checkbox";

	public static String DateInput = "date";

	public static String HTMLInput = "html";
	
	public static String TimeInput = "time";
	
	public static Mapx InputTypeMap = new Mapx();

	static {
		InputTypeMap.put(Input, "�ı���");
		InputTypeMap.put(Text, "�����ı���");
		InputTypeMap.put(Selecter, "������");
		InputTypeMap.put(Radio, "��ѡ��");
		InputTypeMap.put(Checkbox, "��ѡ��");
		InputTypeMap.put(DateInput, "���ڿ�");
		InputTypeMap.put(TimeInput, "ʱ���");
		InputTypeMap.put(HTMLInput, "HTML");
	}

	public static String STRING = "1";

	public static String NUMBER = "2";

	public static String INT = "3";

	public static String EMAIL = "5";
	
	public static Mapx VerifyTypeMap = new Mapx();

	static {
		VerifyTypeMap.put(STRING, "��");
		VerifyTypeMap.put(NUMBER, "����");
		VerifyTypeMap.put(INT, "����");
		VerifyTypeMap.put(EMAIL, "����");
	}
	
	public static void dg1DataBind(DataGridAction dga) {
		DataTable dt = new QueryBuilder("select * from ZDMemberField order by AddTime desc").executePagedDataTable(dga.getPageSize(),dga.getPageIndex());
		dt.decodeColumn("InputType",InputTypeMap);
		dt.decodeColumn("VerifyType",VerifyTypeMap);
		dga.bindData(dt);
	}
	
	public static Mapx initDialog(Mapx params) {
 		String Code = params.getString("Code");
		if (StringUtil.isEmpty(Code)) {
			params.put("VerifyType", HtmlUtil.mapxToOptions(VerifyTypeMap));
			params.put("InputType", HtmlUtil.mapxToOptions(InputTypeMap));
			params.put("IsMandatory", HtmlUtil.codeToRadios("IsMandatory", "YesOrNo", "N"));
			params.put("MaxLength", "100");
			params.put("Cols", "265");
			params.put("Rows", "90");
		} else {
			ZDMemberFieldSchema field = new ZDMemberFieldSchema();
			field.setCode(Code);
			field.setSiteID(Application.getCurrentSiteID());
			field.fill();
			params = field.toMapx();
			params.put("VerifyType", HtmlUtil.mapxToOptions(VerifyTypeMap, field.getVerifyType()));
			params.put("InputType", HtmlUtil.mapxToOptions(InputTypeMap, field.getInputType()));
			params.put("IsMandatory", HtmlUtil.codeToRadios("IsMandatory", "YesOrNo", field.getIsMandatory()));
		}
		return params;
	}

	public void add() {
		String hCode = $V("hCode");
		boolean update = false;
		int FieldCount = new QueryBuilder("select count(*) from ZDMemberField where SiteID = ?",Application.getCurrentSiteID()).executeInt();
		ZDMemberFieldSchema field = new ZDMemberFieldSchema();
		field.setCode($V("Code").trim());
		field.setSiteID(Application.getCurrentSiteID());
		if(StringUtil.isEmpty(hCode)&&field.fill()){
			Response.setLogInfo(0,"������ͬ�ֶ�");
			return;
		}else if(StringUtil.isNotEmpty(hCode)&&field.fill()){
			update = true;
		}
		if(!update){
			if(FieldCount<20){
				int index = 1;
				for (int i = 1; i <= 20; i++) {
					if(new QueryBuilder("select count(*) from ZDMemberField where SiteID = ? and RealField = 'Prop"+i+"'",Application.getCurrentSiteID()).executeInt()==0){
						index = i;
						break;
					}
				}
				field.setRealField("Prop"+index);
			}else{
				Response.setLogInfo(0,"�ﵽ��չ�ֶ�����");
				return;
			}
		}
		field.setValue(Request);
		field.setName($V("Name"));
		field.setCode($V("Code"));
		field.setAddUser(User.getUserName());
		field.setAddTime(new Date());

		// �ѿո�ȫ�ǿո񣬶��ţ�ȫ�Ƕ��ŵȶ��滻ΪӢ�ĵĶ���
		String defaultValue = field.getDefaultValue();
		defaultValue = defaultValue.replaceAll("����", ",");
		defaultValue = defaultValue.replaceAll("��", ",");
		defaultValue = defaultValue.replaceAll("  ", ",");
		defaultValue = defaultValue.replaceAll(" ", ",");
		defaultValue = defaultValue.replaceAll(",,", ",");
		defaultValue = defaultValue.replaceAll("����", ",");
		defaultValue = defaultValue.replaceAll("��", ",");
		if(StringUtil.isEmpty(defaultValue)){
			defaultValue = "";
		}
		field.setDefaultValue(defaultValue);
		
		if (Input.equals(field.getInputType())) {
			field.setColSize(null);
			field.setRowSize(null);
			field.setListOption("");
		} else if (Text.equals(field.getInputType())) {
			field.setListOption("");
		} else if (Selecter.equals(field.getInputType())) {
			field.setColSize(null);
			field.setRowSize(null);
			field.setMaxLength(null);
			field.setVerifyType(STRING);
		} else if (Radio.equals(field.getInputType())) {
			field.setIsMandatory("N");
			field.setColSize(null);
			field.setRowSize(null);
			field.setMaxLength(null);
			field.setVerifyType(STRING);
		} else if (Checkbox.equals(field.getInputType())) {
			field.setIsMandatory("N");
			field.setColSize(null);
			field.setRowSize(null);
			field.setMaxLength(null);
			field.setVerifyType(STRING);
		} else if (DateInput.equals(field.getInputType())
				|| TimeInput.equals(field.getInputType())) {
			field.setColSize(null);
			field.setRowSize(null);
			field.setMaxLength(null);
			field.setListOption("");
			field.setVerifyType(STRING);
		}else if (HTMLInput.equals(field.getInputType())) {
			field.setIsMandatory("N");
			field.setColSize(null);
			field.setRowSize(null);
			field.setMaxLength(null);
			field.setListOption("");
			field.setVerifyType(STRING);
		}
		
		Transaction trans = new Transaction();
		if(update){
			trans.add(field,Transaction.UPDATE);
		}else{
			trans.add(field,Transaction.INSERT);
		}
		if (trans.commit()) {
			Response.setLogInfo(1,"�����Ա��չ�ֶγɹ���");
		} else {
			Response.setLogInfo(0,"��������!");
		}
	}
	
	public static String getColumns(long SiteID){
		DataTable dt = new QueryBuilder("select * from ZDMemberField where SiteID = ? order by AddTime asc",SiteID).executeDataTable();
		String Columns = "";
		for (int i = 0; i < dt.getRowCount(); i++) {
			Columns += getColumn(dt.getDataRow(i));
		}
		return Columns;
	}
	
	public static String getColumnAndValue(ZDMemberSchema member){
		DataTable dt = new QueryBuilder("select * from ZDMemberField where SiteID = ? order by AddTime asc",member.getSiteID()).executeDataTable();
		String Columns = "";
		for (int i = 0; i < dt.getRowCount(); i++) {
			Columns += getColumn(dt.getDataRow(i),member,dt.getString(i,"RealField"));
		}
		return Columns;
	}
	
	private static String getColumn(DataRow dr){
		return getColumn(dr,null,null);
	}
	
	private static String getColumn(DataRow dr,ZDMemberSchema member,String realField){
		String columnName = dr.getString("Name");
		String columnCode = dr.getString("Code");
		String inputType = dr.getString("InputType");
		String verifyType = dr.getString("VerifyType");
		String listOption = dr.getString("ListOption");
		String defaultValue = dr.getString("DefaultValue");
		String isMandatory = dr.getString("IsMandatory");
		String maxLength = dr.getString("MaxLength");
		String HTML	= dr.getString("HTML");
		String verifyStr = "verify='" + columnName + "|";
		if ("Y".equals(isMandatory)) {
			verifyStr += "NotNull";
		}
		if (STRING.equals(verifyType)) {

		} else if (NUMBER.equals(verifyType)) {
			verifyStr += "&&Number";
		} else if (INT.equals(verifyType)) {
			verifyStr += "&&Int";
		} else if (EMAIL.equals(verifyType)) {
			verifyStr += "&&Email";
		}
		if (StringUtil.isNotEmpty(maxLength) && !"0".equals(maxLength)) {
			verifyStr += "&&Length<" + maxLength + "'";
		} else {
			verifyStr += "'";
		}
		
		if(member!=null&&realField!=null){
			Mapx map = member.toMapx();
			defaultValue = map.getString(realField);
			if(StringUtil.isEmpty(defaultValue)){
				defaultValue = "";
			}
		}

		columnCode = PREFIX + columnCode;
		StringBuffer sb = new StringBuffer();
		sb.append("<tr><td height='25' align='right' >");
		sb.append(columnName);
		sb.append("��</td><td align='left' >");
		// 1 �����ı�
		if (inputType.equals(Input)) {
			sb.append("<input type='text' size='26' id='" + columnCode + "' name='"+columnCode+"' value='" + defaultValue + "' " + verifyStr + " />");
		}
		// 2 �����ı�
		if (inputType.equals(Text)) {
			sb.append("<textarea style='width:" + dr.getString("ColSize") + "px;height:"+dr.getString("RowSize")+"px' id='" 
					+ columnCode + "' name='"+columnCode+"' " + verifyStr + ">" + defaultValue + "</textarea>");
		}
		// 3 �����б��
		if (inputType.equals(Selecter)) {
			SelectTag select = new SelectTag();
			select.setId(columnCode);
			if ("Y".equals(isMandatory)) {
				select.setVerify(columnName + "|NotNull");
			}
			String[] array = listOption.split("\\n");
			sb.append(select.getHtml(HtmlUtil.arrayToOptions(array, defaultValue, true)));
		}
		// 4 ��ѡ��
		if (inputType.equals(Radio)) {
			String[] array = listOption.split("\\n");
			if (StringUtil.isEmpty(defaultValue) && array.length > 0) {
				defaultValue = array[0];
			}
			sb.append(HtmlUtil.arrayToRadios(columnCode, array, defaultValue));
		}
		// 5 ��ѡ��
		if (inputType.equals(Checkbox)) {
			String[] array = listOption.split("\\n");
			defaultValue = defaultValue.replaceAll("����", ",");
			defaultValue = defaultValue.replaceAll("��", ",");
			defaultValue = defaultValue.replaceAll("  ", ",");
			defaultValue = defaultValue.replaceAll(" ", ",");
			defaultValue = defaultValue.replaceAll(",,", ",");
			defaultValue = defaultValue.replaceAll("����", ",");
			defaultValue = defaultValue.replaceAll("��", ",");
			String[] checkedArray = defaultValue.split(",");
			sb.append(HtmlUtil.arrayToCheckboxes(columnCode, array, checkedArray));
		}
		// 6 ���ڿ�
		if (inputType.equals(DateInput)) {
			sb.append("<input name='" + columnCode + "' id='" + columnCode + "' value='" + defaultValue 
					+ "' type='text'  size='20' ztype='Date' " + verifyStr + " />");
		}
		// 
		if (inputType.equals(TimeInput)) {
			sb.append("<input name='" + columnCode + "' id='" + columnCode + "' value='" + defaultValue 
					+ "' type='text' size='10' ztype='Time' " + verifyStr + " />");
		}
//		 8 HTML
		if (inputType.equals(HTMLInput)) {
			sb.append(HTML);
		}
		sb.append("</td></tr>");
		return sb.toString();
	}
	
	public static Member setPropValues(com.nswt.member.Member member, Mapx Request){
		ZDMemberSchema m = new ZDMemberSchema();
		String siteID = "";
		m.setUserName(member.getUserName());
		if(m.fill()){
			siteID = m.getSiteID()+"";
		}else{
			siteID = Request.getString("SiteID");
		}
		m = setPropValues(m,Request,Long.parseLong(siteID));
		member.setValue(m.toMapx());
		return member;
	}
	
	public static ZDMemberSchema setPropValues(ZDMemberSchema member,Mapx map,long SiteID){
		ZDMemberFieldSchema field = new ZDMemberFieldSchema();
		ZDMemberFieldSet set = field.query(new QueryBuilder(" where SiteID = ?",SiteID));
		for (int i = 0; i < set.size(); i++) {
			String Value = "";
			String RealField = "";
			String Code = "";
			field = new ZDMemberFieldSchema();
			field = set.get(i);
			Code = field.getCode();
			RealField = field.getRealField();
			Value = map.getString(PREFIX+Code);
			map.put(RealField,Value);
		}
		member.setValue(map);
		return member;
	}

	public void del() {
		String Codes = $V("Codes");
		if (Codes.indexOf("\"") >= 0 || Codes.indexOf("\'") >= 0) {
			Response.setStatus(0);
			Response.setMessage("����IDʱ��������!");
			return;
		}
		Codes = StringUtil.replaceEx(Codes, ",", "','");
		Transaction trans = new Transaction();
		ZDMemberFieldSchema field = new ZDMemberFieldSchema();
		ZDMemberFieldSet set = field.query(new QueryBuilder("where SiteID = "+Application.getCurrentSiteID()+" and Code in ('" + Codes + "')"));
		trans.add(set, Transaction.DELETE);
		if (trans.commit()) {
			Response.setStatus(1);
			Response.setMessage("ɾ���ɹ���");
		} else {
			Response.setStatus(0);
			Response.setMessage("�������ݿ�ʱ��������!");
		}
	}

}
