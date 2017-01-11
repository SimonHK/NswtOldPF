package com.nswt.statical.tag;

import org.apache.commons.lang.ArrayUtils;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.framework.utility.Treex;
import com.nswt.framework.utility.Treex.TreeIterator;
import com.nswt.framework.utility.Treex.TreeNode;
import com.nswt.statical.TemplateData;
import com.nswt.statical.exception.TemplateRuntimeException;
import com.nswt.statical.tag.impl.ZListTag;
import com.nswt.statical.template.TemplateBase;
import com.nswt.statical.template.TemplateCompiler;
import com.nswt.statical.template.TemplateContext;
import com.nswt.statical.template.TemplateFragment;

/*
 * 复合形列表标签。如果有子标签中有<z:list>，则标签体不迭代，如果没有，则标签体直接迭代。
 * 
 * @Author NSWT
 * @Date 2016-8-17
 * @Mail nswt@nswt.com.cn
 */
public abstract class AbstractListTag extends IterativeTag {
	protected DataTable data;
	protected int currentPos;
	protected String item;
	protected int count;
	protected int begin;
	protected String condition;
	protected boolean page;
	protected int pageSize;

	/**
	 * 编译时根据子标签中是否有cms:list或z:list来判断是否需要循环
	 */
	public boolean isIterative(TemplateCompiler compiler) {
		TreeNode node = compiler.getCurrentNode();
		TreeIterator iterator = Treex.iterator(node);
		iterator.next();// 第一个是自己
		while (iterator.hasNext()) {
			TreeNode child = iterator.nextNode();
			TemplateFragment tf = (TemplateFragment) child.getData();
			if (tf.Type == TemplateFragment.FRAGMENT_TAG) {
				if ((tf.TagPrefix.equalsIgnoreCase("cms") || tf.TagPrefix.equalsIgnoreCase("z"))
						&& tf.TagName.equalsIgnoreCase("list")) {// 有可能是cms:else
					return false;
				}
			}
		}
		return true;
	}

	public TagAttributeDesc[] getAllAttributeDescs() {
		TagAttributeDesc[] desc = new TagAttributeDesc[] { new TagAttributeDesc("page"),
				new TagAttributeDesc("count", DataColumn.INTEGER), new TagAttributeDesc("condition"),
				new TagAttributeDesc("pagesize", DataColumn.INTEGER), new TagAttributeDesc("size", DataColumn.INTEGER),
				new TagAttributeDesc("begin", DataColumn.INTEGER) };
		return (TagAttributeDesc[]) ArrayUtils.addAll(getChildAttributeDescs(), desc);
	}

	public int onTagStart() throws TemplateRuntimeException {
		page = "true".equals(attributes.getString("page"));
		count = attributes.getInt("count");
		begin = attributes.getInt("begin");
		pageSize = attributes.getInt("pagesize");
		int size = attributes.getInt("size");
		pageSize = pageSize == 0 ? size : pageSize;
		condition = attributes.getString("condition");
		if (count == 0) {
			count = 20;
		}
		count += begin;
		currentPos = 0;
		if (pageSize != 0) {
			template.setPageSize(pageSize);
			count = pageSize;
			begin = 0;
		}
		if (StringUtil.isNotEmpty(condition) && condition.indexOf("${") != -1) {
			condition = context.replaceHolder(condition);
		}
		prepareData();
		if (page) {
			addPageBar(template, context);
		}
		data = (DataTable) data.clone();
		for (int i = 0; i < begin && i < data.getRowCount(); i++) {
			data.deleteRow(0);
		}
		if (hasChildTag("z", "list")) {
			context.addDataVariable(ZListTag.ZListItemNameKey, item);
			context.addDataVariable(ZListTag.ZListDataNameKey, data);
		}
		return TagBase.CONTINUE;
	}

	public int onTagEnd() {
		return TagBase.CONTINUE;
	}

	public boolean prepareNext() throws TemplateRuntimeException {
		if (data != null && data.getRowCount() > currentPos) {
			if (count == 0 || currentPos < count) {
				context.addDataVariable(item, data.getDataRow(currentPos++));
				return true;
			}
		}
		return false;
	}

	public static void addPageBar(TemplateBase template, TemplateContext context) {
		String dest = template.getDestination();
		if (dest.indexOf("_" + (template.getPageIndex() + 1) + ".") > 0) {
			dest = StringUtil.replaceEx(dest, "_" + (template.getPageIndex() + 1) + ".", ".");
		}
		String firstName = dest.substring(dest.lastIndexOf("/") + 1);
		String otherName = firstName.substring(0, firstName.lastIndexOf(".")) + "_@INDEX"
				+ firstName.substring(firstName.lastIndexOf("."));
		TemplateData td = new TemplateData();
		td.setPageCount(new Double(Math.ceil(template.getPageTotal() * 1.0 / template.getPageSize())).intValue());
		td.setPageSize(template.getPageSize());
		td.setPageIndex(template.getPageIndex());
		td.setTotal(template.getPageTotal());
		td.setFirstFileName(firstName);
		td.setOtherFileName(otherName);
		String html = td.getPageBar(1);
		Mapx map = (Mapx) context.getTree().getRoot().getData();
		map.put("PageBar", html);
	}

	public abstract void prepareData() throws TemplateRuntimeException;

	public abstract TagAttributeDesc[] getChildAttributeDescs();

}
