package com.nswt.statical.tag;

import com.nswt.framework.data.DataColumn;
import com.nswt.framework.utility.DateUtil;
import com.nswt.framework.utility.Errorx;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.NumberUtil;
import com.nswt.framework.utility.StringUtil;
import com.nswt.framework.utility.Treex;
import com.nswt.framework.utility.Treex.TreeIterator;
import com.nswt.framework.utility.Treex.TreeNode;
import com.nswt.statical.exception.TemplateRuntimeException;
import com.nswt.statical.template.TemplateBase;
import com.nswt.statical.template.TemplateCompiler;
import com.nswt.statical.template.TemplateContext;

/**
 * @Author 王育春
 * @Date 2016-5-30
 * @Mail nswt@nswt.com.cn
 */
public abstract class TagBase {
	public static final int SKIP = 1;

	public static final int CONTINUE = 2;

	public static final int END = 3;

	public static final String TagAttributeNameInCurrentNode = "_NSWT_TagBase_Current";

	protected TemplateBase template;

	protected TemplateContext context;

	protected String bodyContent;

	protected Mapx attributes = new Mapx();

	protected int startLineNo;

	protected TreeNode tagTreeNode;// 标签树上的节点

	protected int[] tagTreeIndex;

	public abstract int onTagStart() throws TemplateRuntimeException;// 此方法要负责判断是否需要输出内容，并负责往TemplateContext加入变量

	public abstract TagAttributeDesc[] getAllAttributeDescs();

	public abstract int onTagEnd();

	public abstract boolean isIterative(TemplateCompiler compiler);

	public abstract boolean prepareNext() throws TemplateRuntimeException;// 此方法负责判断是否还有下一次迭代，并且往TemplateContext加入循环变量

	public abstract String getPrefix();

	public abstract String getTagName();

	/**
	 * 是否允许在指定的标签之内。某些标签只能在指定的父标签下运行
	 */
	public boolean canWithinTag(String prefix, String name) {
		return true;
	}

	/**
	 * 编译时支持，标签可以影响自己被编译后的代码内容
	 */
	public boolean onCompileTime(TemplateCompiler complier, String tagVarName) {
		return false;
	}

	/**
	 * 编辑状态下在标签体输出之前再输出编辑操作用div
	 */
	public void editWrapStart() {
	}

	/**
	 * 编辑状态下在标签体输出之后再输出编辑操作用div结束标记
	 */
	public void editWrapEnd() {
	}

	public void setBodyContent(String bodyContent) {
		this.bodyContent = bodyContent;
	}

	public String getBodyContent() {
		return bodyContent;
	}

	public int getStartLineNo() {
		return startLineNo;
	}

	public void setStartLineNo(int startLineNo) {
		this.startLineNo = startLineNo;
	}

	public boolean checkAttribute() {
		TagAttributeDesc[] all = this.getAllAttributeDescs();
		if (all == null) {
			return true;
		}
		for (int i = 0; i < all.length; i++) {
			String v = attributes.getString(all[i].getName());
			if (all[i].isMandatory()) {
				if (StringUtil.isEmpty(v)) {
					Errorx.addError("第" + this.startLineNo + "行有错误，标签" + this.getPrefix() + ":" + this.getTagName()
							+ "的属性名" + all[i].getName() + "不能为空");
				}
			}
		}
		return true;
	}

	public void setTagTreeNode(TreeNode node) {
		tagTreeNode = node;
	}

	public void setTagIndexes(int[] indexes) {
		try {
			tagTreeIndex = indexes;
			tagTreeNode = template.getTagTree().getRoot();
			for (int i = 0; i < indexes.length; i++) {
				tagTreeNode = tagTreeNode.getChildren().get(indexes[i]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public TemplateBase getTemplate() {
		return template;
	}

	public void onEnter(TemplateBase template) {
		this.template = template;
		if (template.getContext() != null) {
			context = template.getContext();
			context.addDataVariable("Attibute", attributes); // 可以在模板中使用${Attibute.Name}来引用标签属性
			context.addTagNode(this);
		}
	}

	public void onExit() {
		if (context != null) {
			context.removeTagNode();
		}
	}

	public void setAttribute(String name, String value) {
		TagAttributeDesc[] all = this.getAllAttributeDescs();
		if (all == null) {
			Errorx.addError("第" + this.startLineNo + "行有错误，标签" + this.getPrefix() + ":" + this.getTagName() + "不支持属性名"
					+ name + ",该标签不支持任何属性名!");
			return;
		}
		boolean flag = false;
		Object v = value;
		for (int i = 0; i < all.length; i++) {
			if (all[i].getName().equalsIgnoreCase(name)) {
				flag = true;
				if (StringUtil.isNotEmpty(value)) {
					int dataType = all[i].getDataType();
					if (dataType == DataColumn.INTEGER || dataType == DataColumn.SMALLINT
							|| dataType == DataColumn.LONG) {
						if (!NumberUtil.isInt(value)) {
							Errorx.addError("第" + this.startLineNo + "行有错误，标签" + this.getPrefix() + ":"
									+ this.getTagName() + "的属性" + name + "的值" + value + "不是整型!");
						}
					} else if (dataType == DataColumn.FLOAT || dataType == DataColumn.DECIMAL
							|| dataType == DataColumn.DOUBLE) {
						if (!NumberUtil.isNumber(value)) {
							Errorx.addError("第" + this.startLineNo + "行有错误，标签" + this.getPrefix() + ":"
									+ this.getTagName() + "的属性" + name + "的值" + value + "不是浮点型!");
						}
					} else if (dataType == DataColumn.DATETIME) {
						if (!DateUtil.isDateTime((String) value)) {
							Errorx.addError("第" + this.startLineNo + "行有错误，标签" + this.getPrefix() + ":"
									+ this.getTagName() + "的属性" + name + "的值" + value + "不是日期时间值!");
						}
					}
				}
				break;
			}
		}
		if (!flag) {
			Errorx.addError("第" + this.startLineNo + "行有错误，标签" + this.getPrefix() + ":" + this.getTagName() + "不支持属性名"
					+ name + "!");
		}
		attributes.put(name, v);
	}

	public TagBase getParentTag() {
		TreeNode node = tagTreeNode.getParent();
		if (node == null || node.isRoot()) {
			return null;
		}
		return (TagBase) node.getData();
	}

	public boolean hasChildTag() {
		return tagTreeNode.getChildren().size() > 0;
	}

	public boolean hasChildTag(String prefix, String name) {
		TreeIterator iterator = Treex.iterator(tagTreeNode);
		Class clazz = TagManager.getTagByName(prefix, name).getClass();
		while (iterator.hasNext()) {
			TreeNode child = (TreeNode) iterator.next();
			if (child == tagTreeNode) {
				continue;
			}
			TagBase tag = (TagBase) child.getData();
			if (clazz.isInstance(tag)) {
				return true;
			}
		}
		return false;
	}

	public boolean existParentTag() {
		return !tagTreeNode.getParent().isRoot();
	}

	public boolean existParentTag(String prefix, String name) {
		TreeNode parent = tagTreeNode.getParent();
		while (parent != null && !parent.isRoot()) {
			TagBase tag = (TagBase) parent.getData();
			if (tag.getTagName().equalsIgnoreCase(name) && tag.getPrefix().equalsIgnoreCase(prefix)) {
				return true;
			}
			parent = tagTreeNode.getParent();
		}
		return false;
	}

	public String getAttribute(String key) {
		return attributes.getString(key);
	}

	public Mapx getAttributes() {
		return attributes;
	}

}
