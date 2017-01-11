package com.nswt.framework.controls;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.data.DataRow;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.utility.HtmlUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.ServletUtil;

/**
 * 
 * @author 黄雷
 * @mail nswt@nswt.com
 * @date 2016-12-7
 */

public class SimpleListTag extends BodyTagSupport {

	private static final long serialVersionUID = 1L;

	public String method;

	public DataTable dt; //持有由method执行产生DataTable

	public int index;

	public DataRow dr;  // 当前数据行

	public DataRow getDataRow() {
		return dr;
	}

	/**
	 * 传递当前数据行DataRow给simplelist子标签
	 * @param dr
	 */
	public void transferDataRow(DataRow dr) {
		this.dr = dr;
	}

	public int doStartTag() throws JspException {
		// 先初始化
		index = 0;
		dr = null;

		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		Mapx params = ServletUtil.getParameterMap(request);

		Tag ptag = this.getParent();
		int i = method.lastIndexOf('.');
		String className = method.substring(0, i);
		method = method.substring(i + 1);
		try {
			Class c = Class.forName(className);
			Method m = c.getMethod(method, new Class[] { Mapx.class, DataRow.class });
			Object o = null;
			if (ptag != null && ptag instanceof SimpleListTag) {
				o = m.invoke(null, new Object[] { params, ((SimpleListTag) ptag).getDataRow() });
			} else {
				o = m.invoke(null, new Object[] { params, null });
			}
			if (!(o instanceof DataTable)) {
				throw new RuntimeException("调用z:simplelist指定的method时发现返回类型不是DataTable");
			}
			dt = (DataTable) o;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (dt.getRowCount() > 0) {
			dt.insertColumn(new DataColumn("_RowNo", DataColumn.INTEGER));
			transferDataRow(dt.getDataRow(index++));
			return EVAL_BODY_BUFFERED;
		} else {
			return SKIP_BODY;
		}

	}

	public int doAfterBody() throws JspException {
		BodyContent body = this.getBodyContent();
		String content = body.getString().trim();
		try {
			dr.set("_RowNo", new Integer(index));
			getPreviousOut().write(HtmlUtil.replaceWithDataRow(dr,content));
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (dt.getRowCount() > index) {
			transferDataRow(dt.getDataRow(index++));
			body.clearBody();
			return EVAL_BODY_AGAIN;
		} else {
			return SKIP_BODY;
		}

	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
}
