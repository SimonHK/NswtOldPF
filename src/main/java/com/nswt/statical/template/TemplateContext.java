package com.nswt.statical.template;

import java.util.ArrayList;
import java.util.Date;

import com.nswt.framework.data.DataRow;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.orm.Schema;
import com.nswt.framework.utility.CaseIgnoreMapx;
import com.nswt.framework.utility.DateUtil;
import com.nswt.framework.utility.Errorx;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.framework.utility.Treex;
import com.nswt.framework.utility.Treex.TreeNode;
import com.nswt.statical.tag.IModifierHandler;
import com.nswt.statical.tag.TagBase;
import com.nswt.statical.tag.TagManager;

/**
 * @Author NSWT
 * @Date 2016-5-29
 * @Mail nswt@nswt.com.cn
 */
public class TemplateContext {
	private boolean isSSIContext = false;
	private boolean isEditTime = false;
	private Treex tree = null;
	private TreeNode current = null;
	private static final String ModifierHandlerKeyName = "_NSWT_Modifier";
	public static final String PrintWriterInCurrentNode = "_NSWT_TagBase_PrintWriter";

	public TemplateContext() {
		tree = new Treex();
		current = tree.getRoot();
	}

	public Object clone() {
		TemplateContext context = new TemplateContext();
		context.isSSIContext = this.isSSIContext;
		context.isEditTime = this.isEditTime;
		context.tree = new Treex();
		context.current = context.tree.getRoot();
		Mapx map = (Mapx) this.tree.getRoot().getData();
		map = (Mapx) map.clone();
		context.current.setData(map);
		return context;
	}

	public void setSSIContext(boolean isSSIContext) {
		this.isSSIContext = isSSIContext;
	}

	/**
	 * 是否是处于ssi环境中
	 */
	public boolean isSSIContext() {
		return isSSIContext;
	}

	/**
	 * 是否是编辑状态下
	 */
	public boolean isEditTime() {
		return isEditTime;
	}

	public void setEditTime(boolean isEditTime) {
		this.isEditTime = isEditTime;
	}

	public Treex getTree() {
		return tree;
	}

	public String eval(String holder) {
		Object obj = getHolderValue(holder);
		if (obj == null) {
			return null;
		} else {
			return obj.toString();
		}
	}

	public int evalInt(String holder) {
		Object obj = getHolderValue(holder);
		if (obj == null) {
			return 0;
		} else {
			if (obj instanceof Number) {
				return ((Number) obj).intValue();
			} else {
				return Integer.parseInt(obj.toString());
			}
		}
	}

	public long evalLong(String holder) {
		Object obj = getHolderValue(holder);
		if (obj == null) {
			return 0;
		} else {
			if (obj instanceof Number) {
				return ((Number) obj).longValue();
			} else {
				return Long.parseLong(obj.toString());
			}
		}
	}

	public double evalDouble(String holder) {
		Object obj = getHolderValue(holder);
		if (obj == null) {
			return 0;
		} else {
			if (obj instanceof Number) {
				return ((Number) obj).doubleValue();
			} else {
				return Double.parseDouble(obj.toString());
			}
		}
	}

	public Date evalDate(String holder) {
		Object obj = getHolderValue(holder);
		if (obj == null) {
			return null;
		} else {
			if (obj instanceof Date) {
				return (Date) obj;
			} else {
				return DateUtil.parseDateTime(obj.toString());
			}
		}
	}

	public DataRow evalDataRow(String holder) {
		Object obj = getHolderValue(holder);
		if (obj == null) {
			return null;
		} else {
			if (obj instanceof DataRow) {
				return (DataRow) obj;
			} else if (obj instanceof Schema) {
				return ((Schema) obj).toDataRow();
			} else if (obj instanceof DataTable) {
				return ((DataTable) obj).getDataRow(0);
			} else {
				return null;
			}
		}
	}

	/**
	 * 将一段文本中的占位符替换成字符串值
	 */
	public String replaceHolder(String content) {
		StringBuffer sb = new StringBuffer();
		int lastIndex = 0;
		while (true) {
			int i1 = content.indexOf("${", lastIndex);
			if (i1 < 0) {
				break;
			}
			sb.append(content.substring(lastIndex, i1));
			int i2 = content.indexOf("}", lastIndex);
			if (i2 < 0) {
				break;
			}
			String holder = content.substring(i1, i2 + 1);
			sb.append(getHolderValue(holder));
			lastIndex = i2 + 1;
		}
		sb.append(content.substring(lastIndex));
		return sb.toString();
	}

	public Object getHolderValue(String holder) {
		if (holder.startsWith("${")) {
			holder = holder.substring(2);
		}
		if (holder.endsWith("}")) {
			holder = holder.substring(0, holder.length() - 1);
		}
		String modifier = null;
		if (holder.indexOf('|') > 0) {
			modifier = holder.substring(holder.indexOf('|') + 1);
			holder = holder.substring(0, holder.indexOf('|'));
		}
		TreeNode node = current;
		String parentPrefix = "parent.";
		while (true) {
			if (holder.toLowerCase().startsWith(parentPrefix)) {
				holder = holder.substring(parentPrefix.length());
				if (node.isRoot()) {
					break;
				}
				node = node.getParent();
			} else {
				break;
			}
		}
		return evalHolder(node, holder, modifier);
	}

	public Object evalHolder(TreeNode node, String holder, String modifierStr) {
		Object value = null;
		String[] arr = holder.split("\\.");
		String varName = arr[0];
		String fieldName = null;
		if (arr.length > 1) {
			fieldName = arr[1];
		}
		TreeNode tn = node;
		while (true) {
			Mapx map = (Mapx) tn.getData();
			Object data = map.get(varName);
			if (data == null) {
			} else if (StringUtil.isNotEmpty(fieldName)) {
				if (data instanceof Mapx) {
					value = ((Mapx) data).get(fieldName);
					break;
				}
				if (data instanceof DataRow) {
					value = ((DataRow) data).get(fieldName);
					break;
				}
			} else {
				return data;
			}
			tn = tn.getParent();
			if (tn == null) {
				break;
			}
		}
		if (StringUtil.isNotEmpty(modifierStr)) {
			// 解析修饰析
			Mapx modifiers = parseModifiers(modifierStr);
			tn = node;
			// 首先执行标签自带的修饰符处理器
			while (true) {
				Mapx map = (Mapx) tn.getData();
				IModifierHandler mh = (IModifierHandler) map.get(ModifierHandlerKeyName);
				if (mh != null && modifiers.containsKey(mh.getName())) {
					value = mh.deal(value, (String[]) modifiers.get(mh.getName()));
					modifiers.remove(mh.getName());
				}
				tn = tn.getParent();
				if (tn == null || tn.isRoot()) {
					break;
				}
			}
			// 执行全局修饰符处理器
			if (value != null) {
				IModifierHandler[] handlers = TagManager.getAllModifers();
				for (int i = 0; i < handlers.length; i++) {
					if (modifiers.containsKey(handlers[i].getName())) {
						value = handlers[i].deal(value, (String[]) modifiers.get(handlers[i].getName()));
						modifiers.remove(handlers[i].getName());
					}
				}
				if (modifiers.size() > 0) {
					Errorx.addMessage("不支持的修饰符:" + StringUtil.join(modifiers.keyArray()));
				}
			}
		}
		return value;
	}

	/**
	 * 解析多个参数，注意转义符，Mapx中的键值应该是String[]
	 */
	public Mapx parseModifiers(String str) {
		Mapx map = new CaseIgnoreMapx();
		char[] cs = str.toCharArray();
		boolean StringFlag = false;
		int lastIndex = 0;
		int lastArg = 0;
		String currentName = null;
		ArrayList argList = null;
		for (int i = 0; i < cs.length; i++) {
			char c = cs[i];
			if (c == '\"') {
				if (i > 0 && cs[i - 1] != '\\') {
					if (StringFlag) {
						StringFlag = false;
					} else {
						StringFlag = true;
					}
				}
			}
			if (StringFlag) {
				continue;
			}
			if (c == '=') {
				if (StringUtil.isNotEmpty(currentName)) {
					String[] args = new String[argList.size()];
					for (int j = 0; j < args.length; j++) {
						args[j] = StringUtil.javaDecode((String) argList.get(j));
					}
					map.put(currentName, args);
				}
				currentName = str.substring(lastIndex, i);
				argList = new ArrayList(4);
				lastArg = i + 1;
				// Replace="A","B"&&CharWidth=20
			}
			if (c == ',' || i == cs.length - 1) {
				if (i == cs.length - 1) {
					i++;
				}
				String arg = str.substring(lastArg, i);
				if (arg.startsWith("\"")) {
					arg = arg.substring(1, arg.length() - 1);
				}
				argList.add(arg);
				lastArg = i + 1;
			}
			if (c == '&' && i > 0 && cs[i - 1] == '&') {
				lastIndex = i + 1;
			}
		}
		if (StringUtil.isNotEmpty(currentName)) {
			String[] args = new String[argList.size()];
			for (int j = 0; j < args.length; j++) {
				args[j] = StringUtil.javaDecode((String) argList.get(j));
			}
			map.put(currentName, args);
		}
		return map;
	}

	public void addDataVariable(String name, DataRow dr) {
		Mapx map = (Mapx) current.getData();
		if (map == null) {
			map = new CaseIgnoreMapx();
			tree.getRoot().setData(map);
		}
		map.put(name, dr);
	}

	public void addDataVariable(String name, Mapx varMap) {
		Mapx map = (Mapx) current.getData();
		if (map == null) {
			map = new CaseIgnoreMapx();
			tree.getRoot().setData(map);
		}
		map.put(name, varMap);
	}

	public void addDataVariable(String name, Object obj) {
		Mapx map = (Mapx) current.getData();
		if (map == null) {
			map = new CaseIgnoreMapx();
			tree.getRoot().setData(map);
		}
		map.put(name, obj);
	}

	public void addModifierHandler(IModifierHandler handler) {
		Mapx map = (Mapx) current.getData();
		if (map == null) {
			map = new CaseIgnoreMapx();
			tree.getRoot().setData(map);
		}
		map.put(ModifierHandlerKeyName, handler);
	}

	public void addTagNode(TagBase tag) {
		Mapx map = new CaseIgnoreMapx();
		map.put(PrintWriterInCurrentNode, new TemplatePrintWriter(this));
		current = current.addChild(map);
	}

	public void removeTagNode() {
		TreeNode parent = current.getParent();
		// 输出到主Writer
		Mapx map = (Mapx) current.getData();
		if (parent != null) {
			parent.removeChild(current);
			current = parent;
		}
		TemplatePrintWriter writer = (TemplatePrintWriter) map.get(PrintWriterInCurrentNode);
		if (writer != null) {
			Mapx pMap = (Mapx) parent.getData();
			TemplatePrintWriter pWriter = (TemplatePrintWriter) pMap.get(PrintWriterInCurrentNode);
			pWriter.write(writer.getResult());
		}
	}

	public TreeNode getCurrent() {
		return current;
	}

	public TemplatePrintWriter getCurrentPrintWriter() {
		Mapx map = (Mapx) current.getData();
		TemplatePrintWriter writer = (TemplatePrintWriter) map.get(PrintWriterInCurrentNode);
		return writer;
	}

	public void clearOutput() {
		TemplatePrintWriter writer = getCurrentPrintWriter();
		if (writer != null) {
			writer.clear();
		}
	}

	public void clearParentOutput() {
		TreeNode parent = current.getParent();
		if (parent == null) {
			return;
		}
		Mapx map = (Mapx) parent.getData();
		TemplatePrintWriter writer = (TemplatePrintWriter) map.get(PrintWriterInCurrentNode);
		if (writer != null) {
			writer.clear();
		}

	}
}
