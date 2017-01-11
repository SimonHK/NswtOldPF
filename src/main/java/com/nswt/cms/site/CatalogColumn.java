package com.nswt.cms.site;

import java.util.Date;

import com.nswt.cms.dataservice.ColumnUtil;
import com.nswt.cms.pub.CatalogUtil;
import com.nswt.framework.Page;
import com.nswt.framework.User;
import com.nswt.framework.controls.DataGridAction;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.DateUtil;
import com.nswt.framework.utility.HtmlUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.Application;
import com.nswt.platform.pub.NoUtil;
import com.nswt.platform.pub.OrderUtil;
import com.nswt.schema.ZCCatalogSchema;
import com.nswt.schema.ZDColumnRelaSchema;
import com.nswt.schema.ZDColumnSchema;

/**
 * @Author 黄雷
 * @Date 2007-11-9
 * @Mail chq@nswt.com
 */
public class CatalogColumn extends Page {

	public static String Extend_Self = "1";

	public static String Extend_Children = "2";

	public static String Extend_All = "3";

	public static String Extend_SameLevel = "4";

	public static Mapx ExtendMap = new Mapx();

	public static Mapx initDialog(Mapx params) {
		String ID = params.getString("ColumnID");
		if (StringUtil.isEmpty(ID)) {
			params.put("VerifyType", HtmlUtil.mapxToOptions(ColumnUtil.VerifyTypeMap));
			params.put("InputType", HtmlUtil.mapxToOptions(ColumnUtil.InputTypeMap));
			params.put("IsMandatory", HtmlUtil.codeToRadios("IsMandatory", "YesOrNo", "N"));
			params.put("MaxLength", "100");
			params.put("Cols", "265");
			params.put("Rows", "90");
		} else {
			ZDColumnSchema column = new ZDColumnSchema();
			column.setID(ID);
			column.fill();
			params = column.toMapx();
			params.put("VerifyType", HtmlUtil.mapxToOptions(ColumnUtil.VerifyTypeMap, column.getVerifyType()));
			params.put("InputType", HtmlUtil.mapxToOptions(ColumnUtil.InputTypeMap, column.getInputType()));
			params.put("IsMandatory", HtmlUtil.codeToRadios("IsMandatory", "YesOrNo", column.getIsMandatory()));
		}
		return params;
	}

	public static void dg1DataBind(DataGridAction dga) {
		String CatalogID = dga.getParam("CatalogID");
		String sql = "select * from ZDColumn where  exists (select ColumnID from ZDColumnRela where ZDColumnRela.ColumnID=ZDColumn.ID and ZDColumnRela.RelaType='"
				+ ColumnUtil.RELA_TYPE_CATALOG_COLUMN + "' and RelaID =?) order by ID asc";
		QueryBuilder qb = new QueryBuilder();
		qb.add(CatalogID);
		qb.setSQL(sql);
		DataTable dt = qb.executeDataTable();
		dt.decodeColumn("VerifyType", ColumnUtil.VerifyTypeMap);
		dt.decodeColumn("InputType", ColumnUtil.InputTypeMap);
		dga.bindData(dt);
	}

	public void add() {
		String catalogID = $V("CatalogID");
		
		String columnCode = $V("Code");
		int catalogType = new QueryBuilder("select type from zccatalog where id = ?", Long.parseLong(catalogID)).executeInt();
		long count = new QueryBuilder("select count(*) from ZDColumnRela where RelaType='"
				+ ColumnUtil.RELA_TYPE_CATALOG_COLUMN + "' and RelaID = ? and ColumnCode =? ", catalogID, columnCode)
				.executeLong();
		if (count > 0) {
			Response.setLogInfo(0, "已经存在相同的字段");
			return;
		}
		Transaction trans = new Transaction();
		ZDColumnSchema column = new ZDColumnSchema();
		column.setValue(Request);

		// 把空格，全角空格，逗号，全角逗号等都替换为英文的逗号
		String defaultValue = column.getDefaultValue();
		defaultValue = defaultValue.replaceAll("　　", ",");
		defaultValue = defaultValue.replaceAll("　", ",");
		defaultValue = defaultValue.replaceAll("  ", ",");
		defaultValue = defaultValue.replaceAll(" ", ",");
		defaultValue = defaultValue.replaceAll(",,", ",");
		defaultValue = defaultValue.replaceAll("，，", ",");
		defaultValue = defaultValue.replaceAll("，", ",");
		column.setDefaultValue(defaultValue);

		column.setID(NoUtil.getMaxID("ColumnID"));
		column.setSiteID(Application.getCurrentSiteID());
		column.setOrderFlag(OrderUtil.getDefaultOrder());
		column.setAddTime(new Date());
		column.setAddUser(User.getUserName());

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
		} else if (ColumnUtil.DateInput.equals(column.getInputType())
				|| ColumnUtil.TimeInput.equals(column.getInputType())) {
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
		} else if (ColumnUtil.HTMLInput.equals(column.getInputType())) {
			column.setIsMandatory("N");
			column.setColSize(null);
			column.setRowSize(null);
			column.setMaxLength(null);
			column.setListOption("");
			column.setVerifyType(ColumnUtil.STRING);
		}

		String extend = $V("Extend");
		if (Extend_Self.equals(extend)) {
			ZDColumnRelaSchema rela = new ZDColumnRelaSchema();
			rela.setID(NoUtil.getMaxID("ColumnRelaID"));
			rela.setColumnID(column.getID());
			rela.setColumnCode(column.getCode());
			rela.setRelaType(ColumnUtil.RELA_TYPE_CATALOG_COLUMN);
			rela.setRelaID(catalogID);
			rela.setAddTime(column.getAddTime());
			rela.setAddUser(column.getAddUser());
			trans.add(rela, Transaction.INSERT);
		} else if (Extend_Children.equals(extend)) {
			String innerCode = CatalogUtil.getInnerCode(catalogID);
			DataTable childCatalogDT = new QueryBuilder("select id from zccatalog where innercode like '" + innerCode
					+ "%'").executeDataTable();
			for (int i = 0; i < childCatalogDT.getRowCount(); i++) {
				ZDColumnRelaSchema rela = new ZDColumnRelaSchema();
				rela.setColumnCode(column.getCode());
				rela.setRelaType(ColumnUtil.RELA_TYPE_CATALOG_COLUMN);
				rela.setRelaID(childCatalogDT.getString(i, 0));
				if(rela.query().size()>0){
					continue;
				}
				rela.setID(NoUtil.getMaxID("ColumnRelaID"));
				rela.setColumnID(column.getID());
				rela.setColumnCode(column.getCode());
				rela.setRelaType(ColumnUtil.RELA_TYPE_CATALOG_COLUMN);
				rela.setRelaID(childCatalogDT.getString(i, 0));
				rela.setAddTime(column.getAddTime());
				rela.setAddUser(column.getAddUser());
				trans.add(rela, Transaction.INSERT);
			}
		} else if (Extend_All.equals(extend)) {
			DataTable allCatalogDT = new QueryBuilder("select id from zccatalog where Type=" + catalogType
					+ " and siteID =" + Application.getCurrentSiteID()).executeDataTable();
			for (int i = 0; i < allCatalogDT.getRowCount(); i++) {
				ZDColumnRelaSchema rela = new ZDColumnRelaSchema();
				rela.setColumnCode(column.getCode());
				rela.setRelaType(ColumnUtil.RELA_TYPE_CATALOG_COLUMN);
				rela.setRelaID(allCatalogDT.getString(i, 0));
				if(rela.query().size()>0){
					continue;
				}
				rela.setID(NoUtil.getMaxID("ColumnRelaID"));
				rela.setColumnID(column.getID());
				rela.setColumnCode(column.getCode());
				rela.setRelaType(ColumnUtil.RELA_TYPE_CATALOG_COLUMN);
				rela.setRelaID(allCatalogDT.getString(i, 0));
				rela.setAddTime(column.getAddTime());
				rela.setAddUser(column.getAddUser());
				trans.add(rela, Transaction.INSERT);
			}
		} else if (Extend_SameLevel.equals(extend)) {
			int level = new QueryBuilder("select treelevel from zccatalog where id =" + catalogID).executeInt();
			DataTable levelCatalogDT = new QueryBuilder("select id from zccatalog where siteID ="
					+ Application.getCurrentSiteID() + " and Type=" + catalogType + " and treelevel=" + level)
					.executeDataTable();
			for (int i = 0; i < levelCatalogDT.getRowCount(); i++) {
				ZDColumnRelaSchema rela = new ZDColumnRelaSchema();
				rela.setColumnCode(column.getCode());
				rela.setRelaType(ColumnUtil.RELA_TYPE_CATALOG_COLUMN);
				rela.setRelaID(levelCatalogDT.getString(i, 0));
				if(rela.query().size()>0){
					continue;
				}
				
				rela.setID(NoUtil.getMaxID("ColumnRelaID"));
				rela.setColumnID(column.getID());
				rela.setColumnCode(column.getCode());
				rela.setRelaType(ColumnUtil.RELA_TYPE_CATALOG_COLUMN);
				rela.setRelaID(levelCatalogDT.getString(i, 0));
				rela.setAddTime(column.getAddTime());
				rela.setAddUser(column.getAddUser());
				trans.add(rela, Transaction.INSERT);
			}
		}
		// 需要更新栏目修改时间
		ZCCatalogSchema catalog = CatalogUtil.getSchema(catalogID);
		catalog.setModifyTime(new Date());
		catalog.setModifyUser(User.getUserName());
		trans.add(catalog, Transaction.UPDATE);

		trans.add(column, Transaction.INSERT);
		if (trans.commit()) {
			Response.setLogInfo(1, "新建成功");
		} else {
			Response.setLogInfo(0, "新建失败");
		}

	}

	public void save() {
		String catalogID = $V("CatalogID");
		String columnCode = $V("Code");
		long count = new QueryBuilder("select count(*) from ZDColumnRela where RelaType='"
				+ ColumnUtil.RELA_TYPE_CATALOG_COLUMN + "' and RelaID = ? and ColumnCode =? and ColumnID!="
				+ $V("ColumnID"), catalogID, columnCode).executeLong();
		if (count > 0) {
			Response.setLogInfo(0, "已经存在相同的字段");
			return;
		}
		ZDColumnSchema column = new ZDColumnSchema();
		column.setID($V("ColumnID"));
		column.fill();
		column.setValue(this.Request);
		// 把空格，全角空格，逗号，全角逗号等都替换为英文的逗号
		String defaultValue = column.getDefaultValue();
		defaultValue = defaultValue.replaceAll("　　", ",");
		defaultValue = defaultValue.replaceAll("　", ",");
		defaultValue = defaultValue.replaceAll("  ", ",");
		defaultValue = defaultValue.replaceAll(" ", ",");
		defaultValue = defaultValue.replaceAll(",,", ",");
		defaultValue = defaultValue.replaceAll("，，", ",");
		defaultValue = defaultValue.replaceAll("，", ",");
		column.setDefaultValue(defaultValue);

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
			column.setColSize(null);
			column.setRowSize(null);
			column.setMaxLength(null);
			column.setListOption("");
			column.setVerifyType(ColumnUtil.STRING);
		} else if (ColumnUtil.HTMLInput.equals(column.getInputType())) {
			column.setIsMandatory("N");
			column.setColSize(null);
			column.setRowSize(null);
			column.setMaxLength(null);
			column.setListOption("");
			column.setVerifyType(ColumnUtil.STRING);
		}
		Transaction trans = new Transaction();
		trans.add(column, Transaction.UPDATE);
		trans.add(new QueryBuilder("update zdcolumnrela set ColumnCode=? where ColumnID=?", column.getCode(), column
				.getID()));
		trans.add(new QueryBuilder("update zdcolumnvalue set ColumnCode=? where ColumnID=?", column.getCode(), column
				.getID()));

		int catalogType = new QueryBuilder("select type from zccatalog where id = ?", Long.parseLong(catalogID)).executeInt();

		String extend = $V("Extend");
		if (Extend_Self.equals(extend)) {

		} else if (Extend_Children.equals(extend)) {
			String innerCode = CatalogUtil.getInnerCode(catalogID);
			DataTable childCatalogDT = new QueryBuilder("select id from zccatalog where innercode like '" + innerCode
					+ "%' and not exists (select 'x' from zdcolumnrela b where b.ColumnCode='" + column.getCode()
					+ "' and b.RelaType='" + ColumnUtil.RELA_TYPE_CATALOG_COLUMN + "' and b.RelaID = zccatalog.ID)")
					.executeDataTable();
			for (int i = 0; i < childCatalogDT.getRowCount(); i++) {
				ZDColumnRelaSchema rela = new ZDColumnRelaSchema();
				rela.setID(NoUtil.getMaxID("ColumnRelaID"));
				rela.setColumnID(column.getID());
				rela.setColumnCode(column.getCode());
				rela.setRelaType(ColumnUtil.RELA_TYPE_CATALOG_COLUMN);
				rela.setRelaID(childCatalogDT.getString(i, 0));
				rela.setAddTime(column.getAddTime());
				rela.setAddUser(column.getAddUser());
				trans.add(rela, Transaction.INSERT);
			}
		} else if (Extend_All.equals(extend)) {
			DataTable allCatalogDT = new QueryBuilder("select id from zccatalog where Type=" + catalogType
					+ " and siteID =" + Application.getCurrentSiteID()
					+ " and not exists (select 'x' from zdcolumnrela b where b.ColumnCode='" + column.getCode()
					+ "' and b.RelaType='" + ColumnUtil.RELA_TYPE_CATALOG_COLUMN + "' and b.RelaID = zccatalog.ID)")
					.executeDataTable();
			for (int i = 0; i < allCatalogDT.getRowCount(); i++) {
				ZDColumnRelaSchema rela = new ZDColumnRelaSchema();
				rela.setID(NoUtil.getMaxID("ColumnRelaID"));
				rela.setColumnID(column.getID());
				rela.setColumnCode(column.getCode());
				rela.setRelaType(ColumnUtil.RELA_TYPE_CATALOG_COLUMN);
				rela.setRelaID(allCatalogDT.getString(i, 0));
				rela.setAddTime(column.getAddTime());
				rela.setAddUser(column.getAddUser());
				trans.add(rela, Transaction.INSERT);
			}
		} else if (Extend_SameLevel.equals(extend)) {
			int level = new QueryBuilder("select treelevel from zccatalog where id =" + catalogID).executeInt();
			DataTable levelCatalogDT = new QueryBuilder("select id from zccatalog where siteID ="
					+ Application.getCurrentSiteID() + " and Type=" + catalogType + " and treelevel=" + level
					+ " and not exists (select 'x' from zdcolumnrela b where b.ColumnCode='" + column.getCode()
					+ "' and b.RelaType='" + ColumnUtil.RELA_TYPE_CATALOG_COLUMN + "' and b.RelaID = zccatalog.ID)")
					.executeDataTable();
			for (int i = 0; i < levelCatalogDT.getRowCount(); i++) {
				ZDColumnRelaSchema rela = new ZDColumnRelaSchema();
				rela.setID(NoUtil.getMaxID("ColumnRelaID"));
				rela.setColumnID(column.getID());
				rela.setColumnCode(column.getCode());
				rela.setRelaType(ColumnUtil.RELA_TYPE_CATALOG_COLUMN);
				rela.setRelaID(levelCatalogDT.getString(i, 0));
				rela.setAddTime(column.getAddTime());
				rela.setAddUser(column.getAddUser());
				trans.add(rela, Transaction.INSERT);
			}
		}
		
		// 需要更新栏目修改时间
		ZCCatalogSchema catalog = CatalogUtil.getSchema(catalogID);
		catalog.setModifyTime(new Date());
		catalog.setModifyUser(User.getUserName());
		trans.add(catalog, Transaction.UPDATE);

		
		if (trans.commit()) {
			Response.setLogInfo(1, "保存成功!");
		} else {
			Response.setLogInfo(0, "保存失败!");
		}
	}

	public void del() {// 本方法需要注意SQL注入
		String IDs = $V("IDs");
		long catalogID = Long.parseLong($V("CatalogID"));
		Transaction trans = new Transaction();
		String extend = $V("Extend");
		String wherePart = "";
		if (Extend_Self.equals(extend)) {
			wherePart = " where columnID in (" + IDs + ") and RelaType='" + ColumnUtil.RELA_TYPE_CATALOG_COLUMN
					+ "' and RelaID='" + catalogID + "'";
			deleteTableData("ZDColumnRela", wherePart, trans);
			wherePart = " where columnID in (" + IDs + ") and RelaType='" + ColumnUtil.RELA_TYPE_DOCID
					+ "' and RelaID='" + catalogID + "'";
			deleteTableData("ZDColumnValue", wherePart, trans);
		} else if (Extend_Children.equals(extend)) {
			String innerCode = CatalogUtil.getInnerCode(catalogID);
			wherePart = " where columnID in (" + IDs + ") and RelaType='" + ColumnUtil.RELA_TYPE_CATALOG_COLUMN
					+ "' and exists (select '' from ZCCatalog b where b.ID=RelaID and b.InnerCode like '" + innerCode
					+ "%')";
			deleteTableData("ZDColumnRela", wherePart, trans);
			wherePart = " where columnID in (" + IDs + ") and RelaType='" + ColumnUtil.RELA_TYPE_DOCID
					+ "' and exists (select '' from ZCCatalog b where b.ID=RelaID and b.InnerCode like '" + innerCode
					+ "%')";
			deleteTableData("ZDColumnValue", wherePart, trans);
		} else if (Extend_All.equals(extend)) {
			wherePart = " where columnID in (" + IDs + ") and RelaType='" + ColumnUtil.RELA_TYPE_CATALOG_COLUMN
					+ "' and exists (select '' from ZCCatalog b where b.ID=RelaID and b.SiteID = "
					+ Application.getCurrentSiteID() + ")";
			deleteTableData("ZDColumnRela", wherePart, trans);
			wherePart = " where columnID in (" + IDs + ") and RelaType='" + ColumnUtil.RELA_TYPE_DOCID
					+ "' and exists (select '' from ZCCatalog b where b.ID=RelaID and b.SiteID = "
					+ Application.getCurrentSiteID() + ")";
			deleteTableData("ZDColumnValue", wherePart, trans);
		}
		// 如果关联表内已经不存在自定义字段的关联关系，删除ZDColumn表中的数据，彻底清除所有垃圾数据
		wherePart = " where not exists (select 1 from ZDColumnRela where ZDColumn.ID=ColumnID)";
		deleteTableData("ZDColumn", wherePart, trans);
		
		// 需要更新栏目修改时间
		ZCCatalogSchema catalog = CatalogUtil.getSchema(catalogID);
		catalog.setModifyTime(new Date());
		catalog.setModifyUser(User.getUserName());
		trans.add(catalog, Transaction.UPDATE);

		if (trans.commit()) {
			Response.setLogInfo(1, "删除成功");
		} else {
			Response.setLogInfo(0, "删除失败");
		}
	}

	/**
	 * 针对大数据量删除备份的时候用Set会出现内存溢出的问题，可以用以下方法解决。
	 */
	public void deleteTableData(String tableName, String wherePart, Transaction trans) {
		String backupNO = String.valueOf(System.currentTimeMillis()).substring(1, 11);
		String backupOperator = User.getUserName();
		String backupTime = DateUtil.toString(new Date(), "yyyy-MM-dd HH:mm:ss");
		String backup = "insert into b" + tableName + " select " + tableName + ".*,'" + backupNO + "','"
				+ backupOperator + "','" + backupTime + "',null from " + tableName + wherePart;
		String delete = "delete from " + tableName + wherePart;
		trans.add(new QueryBuilder(backup));
		trans.add(new QueryBuilder(delete));
	}

	// 拷贝至其他栏目
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
