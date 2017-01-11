package com.nswt.workflow;

import java.util.List;

import org.dom4j.Element;

import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.workflow.Workflow.Node;
import com.nswt.workflow.methods.ConditionMethod;
import com.nswt.workflow.methods.ConditionScript;

/**
 * 转移条件
 * 
 * 日期 : 2010-1-7 <br>
 * 作者: NSWT <br>
 * 邮箱：nswt@nswt.com.cn <br>
 */
public class WorkflowTransition {
	private Node node;

	private int id;

	private String name;

	private Mapx map;

	private Node targetNode;

	private String target;

	public WorkflowTransition(Node node, Element ele) {
		this.node = node;
		String strid = ele.attributeValue("id");
		id = Integer.parseInt(strid.substring(strid.lastIndexOf("e") + 1));
		name = ele.attributeValue("name");
		target = ele.attributeValue("target").substring(ele.attributeValue("target").lastIndexOf("e") + 1);
		List list = ele.elements("data");
		map = new Mapx();
		for (int i = 0; i < list.size(); i++) {
			Element data = (Element) list.get(i);
			String k = data.attributeValue("type");
			String v = data.getText();
			map.put(k, v);
		}
	}

	protected void init() {
		int targetID = Integer.parseInt(target);
		targetNode = node.getWorkflow().findNode(targetID);
	}

	public int getID() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Mapx getData() {
		return map;
	}

	public Node getTargetNode() {
		return targetNode;
	}

	public Node getFromNode() {
		return node;
	}

	/**
	 * 判断是否满足转移条件
	 */
	public boolean validate(Context context) throws Exception {
		Object[] keyArray = map.keyArray();
		boolean flag = true;
		for (int i = 0; i < keyArray.length; i++) {
			String key = (String) keyArray[i];
			String value = map.getString(key);
			if ("Script".equalsIgnoreCase(key) && StringUtil.isNotEmpty(value)) {
				ConditionScript cs = new ConditionScript();
				cs.setScript(value);
				flag = cs.validate(context);
			} else if ("Method".equalsIgnoreCase(key) && StringUtil.isNotEmpty(value)) {
				Object o = Class.forName(value).newInstance();
				ConditionMethod cm = null;
				if (o instanceof ConditionMethod) {
					cm = (ConditionMethod) o;
				} else {
					throw new WorkflowException(value + "没有实现ConditionMethod抽象类");
				}
				flag = cm.validate(context);
			}
		}
		return flag;
	}
}
