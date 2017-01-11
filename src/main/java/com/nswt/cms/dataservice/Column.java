package com.nswt.cms.dataservice;

import java.util.Date;

import com.nswt.framework.Page;
import com.nswt.framework.User;
import com.nswt.framework.controls.DataGridAction;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.HtmlUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.Application;
import com.nswt.platform.pub.NoUtil;
import com.nswt.platform.pub.OrderUtil;
import com.nswt.schema.ZDColumnRelaSchema;
import com.nswt.schema.ZDColumnRelaSet;
import com.nswt.schema.ZDColumnSchema;
import com.nswt.schema.ZDColumnSet;
import com.nswt.schema.ZDColumnValueSchema;
import com.nswt.schema.ZDColumnValueSet;

/**
 * @Author ����
 * @Date 2007-11-9
 * @Mail chq@nswt.com
 */
public class Column extends Page {

	public static Mapx initDialog(Mapx params) {
		String ID = params.getString("ColumnID");
		if (StringUtil.isEmpty(ID)) {
			params.put("VerifyType", HtmlUtil.mapxToOptions(ColumnUtil.VerifyTypeMap));
			params.put("InputType", HtmlUtil.mapxToOptions(ColumnUtil.InputTypeMap));
			params.put("IsMandatory", HtmlUtil.codeToRadios("IsMandatory", "YesOrNo", "N"));
			params.put("MaxLength", "100");
			params.put("ColSize", "265");
			params.put("RowSize", "90");
		} else {
			ZDColumnSchema column = new ZDColumnSchema();
			column.setID(ID);
			column.fill();
			params = column.toMapx();
			params.put("VerifyType", HtmlUtil.mapxToOptions(ColumnUtil.VerifyTypeMap, column.getVerifyType()));
			params.put("InputType", HtmlUtil.mapxToOptions(ColumnUtil.InputTypeMap, column.getInputType()));
			params.put("IsMandatory", HtmlUtil.codeToRadios("IsMandatory", "YesOrNo", column.getIsMandatory()));
		}
		params.put("DataType", HtmlUtil.mapxToOptions(CustomTable.DBTypeMap));
		return params;
	}

	public static void dg1DataBind(DataGridAction dga) {
		String FormID = dga.getParam("FormID");
		String sql = "select ZDColumn.* ,(select FormName from ZDForm where ZDForm.ID = ZDColumn.FormID) as FormName from ZDColumn where FormID=? order by OrderFlag asc ";
		QueryBuilder qb = new QueryBuilder();
		qb.add(FormID);
		qb.setSQL(sql);
		DataTable dt = qb.executeDataTable();
		dt.decodeColumn("VerifyType", ColumnUtil.VerifyTypeMap);
		dt.decodeColumn("InputType", ColumnUtil.InputTypeMap);
		dt.decodeColumn("DataType", CustomTable.DBTypeMap);
		dga.bindData(dt);
	}

	public void add() {
		ZDColumnSchema column = new ZDColumnSchema();
		String code = $V("Code");
		String formID = $V("FormID");
		long count = new QueryBuilder("select count(*) from ZDColumn where FormID=? and Code=?", formID, code)
				.executeLong();
		if (count > 0) {
			Response.setLogInfo(0, "�Ѿ�������ͬ���ֶ�");
			return;
		}
		column.setValue(Request);

		// �ѿո�ȫ�ǿո񣬶��ţ�ȫ�Ƕ��ŵȶ��滻ΪӢ�ĵĶ���
		String defaultValue = column.getDefaultValue();
		defaultValue = defaultValue.replaceAll("����", ",");
		defaultValue = defaultValue.replaceAll("��", ",");
		defaultValue = defaultValue.replaceAll("  ", ",");
		defaultValue = defaultValue.replaceAll(" ", ",");
		defaultValue = defaultValue.replaceAll(",,", ",");
		defaultValue = defaultValue.replaceAll("����", ",");
		defaultValue = defaultValue.replaceAll("��", ",");
		column.setDefaultValue(defaultValue);

		column.setID(NoUtil.getMaxID("ColumnID"));
		column.setOrderFlag(OrderUtil.getDefaultOrder());
		column.setAddTime(new Date());
		column.setAddUser(User.getUserName());
		column.setSiteID(Application.getCurrentSiteID());

		if (ColumnUtil.Input.equals(column.getInputType())) {
			column.setColSize(null);
			column.setRowSize(null);
			column.setListOption("");
		} else if (ColumnUtil.Text.equals(column.getInputType())) {
			column.setListOption("");
		} else if (ColumnUtil.Select.equals(column.getInputType())) {
			column.setColSize(null);
			column.setRowSize(null);
			column.setMaxLength(null);
			column.setVerifyType(ColumnUtil.STRING);
		} else if (ColumnUtil.Radio.equals(column.getInputType())) {
			column.setIsMandatory("N");
			column.setColSize(null);
			column.setRowSize(null);
			column.setMaxLength(null);
			column.setVerifyType(ColumnUtil.STRING);
		} else if (ColumnUtil.Checkbox.equals(column.getInputType())) {
			column.setIsMandatory("N");
			column.setColSize(null);
			column.setRowSize(null);
			column.setMaxLength(null);
			column.setVerifyType(ColumnUtil.STRING);
		} else if (ColumnUtil.DateInput.equals(column.getInputType())) {
			column.setColSize(null);
			column.setRowSize(null);
			column.setMaxLength(null);
			column.setListOption("");
			column.setVerifyType(ColumnUtil.STRING);
		} else if (ColumnUtil.ImageInput.equals(column.getInputType())) {
			column.setIsMandatory("N");
			column.setColSize(null);
			column.setRowSize(null);
			column.setMaxLength(null);
			column.setListOption("");
			column.setVerifyType(ColumnUtil.STRING);
		}

		Transaction trans = new Transaction();
		trans.add(column, Transaction.INSERT);
		if (trans.commit()) {
			Response.setLogInfo(1, "�½��ɹ�");
		} else {
			Response.setLogInfo(0, "�½�ʧ��");
		}

	}

	public void edit() {
		String ID = $V("ColumnID");
		ZDColumnSchema column = new ZDColumnSchema();
		column.setID(ID);
		column.fill();
		long count = new QueryBuilder("select count(*) from ZDColumn where FormID=? and Code=?", $V("FormID"),
				$V("ColumnCode")).executeLong();
		if (count > 1) {
			Response.setLogInfo(0, "�Ѿ�������ͬ���ֶ�");
			return;
		}
		column.setValue(this.Request);

		// �ѿո�ȫ�ǿո񣬶��ţ�ȫ�Ƕ��ŵȶ��滻ΪӢ�ĵĶ���
		String defaultValue = column.getDefaultValue();
		defaultValue = defaultValue.replaceAll("����", ",");
		defaultValue = defaultValue.replaceAll("��", ",");
		defaultValue = defaultValue.replaceAll("  ", ",");
		defaultValue = defaultValue.replaceAll(" ", ",");
		defaultValue = defaultValue.replaceAll(",,", ",");
		defaultValue = defaultValue.replaceAll("����", ",");
		defaultValue = defaultValue.replaceAll("��", ",");
		column.setDefaultValue(defaultValue);

		column.setModifyUser(User.getUserName());
		column.setModifyTime(new Date());

		if (ColumnUtil.Input.equals(column.getInputType())) {
			column.setColSize(null);
			column.setRowSize(null);
			column.setListOption("");
		} else if (ColumnUtil.Text.equals(column.getInputType())) {
			column.setListOption("");
		} else if (ColumnUtil.Select.equals(column.getInputType())) {
			column.setColSize(null);
			column.setRowSize(null);
			column.setMaxLength(null);
			column.setVerifyType(ColumnUtil.STRING);
		} else if (ColumnUtil.Radio.equals(column.getInputType())) {
			column.setIsMandatory("N");
			column.setColSize(null);
			column.setRowSize(null);
			column.setMaxLength(null);
			column.setVerifyType(ColumnUtil.STRING);
		} else if (ColumnUtil.Checkbox.equals(column.getInputType())) {
			column.setIsMandatory("N");
			column.setColSize(null);
			column.setRowSize(null);
			column.setMaxLength(null);
			column.setVerifyType(ColumnUtil.STRING);
		} else if (ColumnUtil.DateInput.equals(column.getInputType())) {
			column.setColSize(null);
			column.setRowSize(null);
			column.setMaxLength(null);
			column.setListOption("");
			column.setVerifyType(ColumnUtil.STRING);
		} else if (ColumnUtil.ImageInput.equals(column.getInputType())) {
			column.setIsMandatory("N");
			column.setColSize(null);
			column.setRowSize(null);
			column.setMaxLength(null);
			column.setListOption("");
			column.setVerifyType(ColumnUtil.STRING);
		}

		if (column.update()) {
			Response.setLogInfo(1, "�޸ĳɹ�!");
		} else {
			Response.setLogInfo(0, "�޸�ʧ��!");
		}
	}

	public void del() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setLogInfo(0, "����IDʱ��������!");
			return;
		}
		ZDColumnSchema column = new ZDColumnSchema();
		Transaction trans = new Transaction();
		ZDColumnSet set = column.query(new QueryBuilder("where id in (" + ids + ")"));
		ZDColumnRelaSet relaSet = new ZDColumnRelaSchema().query(new QueryBuilder("where columnID in (" + ids
				+ ") and RelaType='" + ColumnUtil.RELA_TYPE_CATALOG_COLUMN + "'"));
		ZDColumnValueSet valueSet = new ZDColumnValueSchema().query(new QueryBuilder("where columnID in (" + ids
				+ ") and RelaType='" + ColumnUtil.RELA_TYPE_DOCID + "'"));
		trans.add(set, Transaction.DELETE_AND_BACKUP);
		trans.add(relaSet, Transaction.DELETE_AND_BACKUP);
		trans.add(valueSet, Transaction.DELETE_AND_BACKUP);
		if (trans.commit()) {
			Response.setLogInfo(1, "ɾ���ɹ�");
		} else {
			Response.setLogInfo(0, "ɾ��ʧ��");
		}
	}

	public void sortColumn() {
		String target = $V("Target");
		String orders = $V("Orders");
		String type = $V("Type");
		String formID = $V("FormID");
		if (!StringUtil.checkID(target) && !StringUtil.checkID(orders)) {
			return;
		}
		if (OrderUtil.updateOrder("ZDColumn", type, target, orders, " FormID = " + formID)) {
			Response.setMessage("����ɹ�");
		} else {
			Response.setError("����ʧ��");
		}
	}

	// ������������Ŀ
	public static Mapx initCopyTo(Mapx params) {
		Mapx map = new Mapx();
		String tIDs = params.get("IDs").toString();
		map.put("IDs", tIDs);
		String tCatalogID = params.get("CatalogID").toString();
		map.put("CatalogID", tCatalogID);
		String tSiteID = new QueryBuilder("select siteid from ZCCatalog where id=?", tCatalogID).executeString();
		map.put("SiteID", tSiteID);
		DataTable dt = new QueryBuilder("select name,id from ZCCatalog where siteid=? and id<>? order by id", tSiteID,
				tCatalogID).executeDataTable();
		map.put("optCatalog", HtmlUtil.dataTableToOptions(dt));
		return map;
	}

}