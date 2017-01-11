package com.nswt.framework;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.nswt.framework.utility.CaseIgnoreMapx;
import com.nswt.framework.utility.FileUtil;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.framework.utility.Treex;
import com.nswt.framework.utility.Treex.TreeNode;
import com.nswt.framework.utility.Treex.TreeNodeList;

/*
 * 统一配置文件解析器
 * 
 * @Author NSWT
 * @Date 2016-9-1
 * @Mail nswt@nswt.com.cn
 */
public class ConfigLoader {
	private static boolean Loaded = false;

	private static Object mutex = new Object();

	private static Treex tree = new Treex();

	/**
	 * 返回true表示重新加载过
	 */
	public static void load() {
		if (!Loaded) {
			synchronized (mutex) {
				if (!Loaded) {// 只启动时加载一次
					String path = Config.getClassesPath();
					File f = new File(path);
					if (!f.exists()) {
						return;
					}
					String file = path + "charset.config";
					if (new File(file).exists()) {
						String txt = FileUtil.readText(file);
						Mapx map = StringUtil.splitToMapxNew(txt, "\n", "=");
						Constant.GlobalCharset = "GBK".equalsIgnoreCase(map.getString("global")) ? "GBK" : "UTF-8";
					}

					File[] fs = f.listFiles();
					for (int i = 0; i < fs.length; i++) {
						f = fs[i];
						if (f.isFile() && f.getName().toLowerCase().endsWith(".xml")) {
							loadOneFile(f, tree);
						}
					}
					NodeData data = getNodeData("framework.application.config", "name", "ComplexDeployMode");
					Config.ComplexDepolyMode = data != null && "true".equals(data.Body);

					LogUtil.info("----" + Config.getAppCode() + "(" + Config.getAppName() + "): Config Initialized----");
				}
			}
			Loaded = true;
		}
	}

	public static void reload() {
		Loaded = false;
		load();
	}

	public static void loadOneFile(File f, Treex tree) {
		SAXReader reader = new SAXReader(false);
		Document doc;
		try {
			doc = reader.read(f);
			Element root = doc.getRootElement();
			convertElement(root, tree.getRoot());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void convertElement(Element ele, TreeNode parent) {
		String name = ele.getName().toLowerCase();
		NodeData data = new NodeData();
		data.TagName = name;
		data.Body = ele.getTextTrim();
		List list = ele.attributes();
		Mapx map = new Mapx();
		for (int i = 0; i < list.size(); i++) {
			Attribute attr = (Attribute) list.get(i);
			map.put(attr.getName(), attr.getValue());
		}
		data.Attributes = map;
		TreeNode node = parent.addChild(data);
		data.treeNode = node;
		list = ele.elements();
		for (int i = 0; i < list.size(); i++) {
			Element child = (Element) list.get(i);
			convertElement(child, node);
		}
	}

	public static NodeData[] getNodeDataList(String path) {
		String[] arr = path.split("\\.");
		TreeNode current = tree.getRoot();
		ArrayList list = new ArrayList();
		list.add(current);
		for (int i = 0; i < arr.length; i++) {
			list = getChildren(list, arr[i]);
			if (list == null) {
				return null;
			}
		}
		if (list.size() == 0) {
			return null;
		} else {
			NodeData[] datas = new NodeData[list.size()];
			for (int i = 0; i < list.size(); i++) {
				TreeNode node = (TreeNode) list.get(i);
				datas[i] = (NodeData) node.getData();
			}
			return datas;
		}
	}

	private static ArrayList getChildren(ArrayList parentList, String pathPart) {
		ArrayList list = new ArrayList();
		for (int i = 0; i < parentList.size(); i++) {
			TreeNode node = (TreeNode) parentList.get(i);
			TreeNodeList nodes = node.getChildren();
			for (int j = 0; j < nodes.size(); j++) {
				NodeData data = (NodeData) nodes.get(j).getData();
				if (pathPart.equals("*") || data.getTagName().equalsIgnoreCase(pathPart)) {
					list.add(nodes.get(j));
				}
			}
		}
		return list;
	}

	public static NodeData getNodeData(String path, String attrName, String attrValue) {
		NodeData[] datas = getNodeDataList(path);
		if (datas != null) {
			for (int i = 0; i < datas.length; i++) {
				if (attrValue.equals(datas[i].Attributes.get(attrName))) {
					return datas[i];
				}
			}
		}
		return null;
	}

	public static class NodeData {
		Mapx Attributes = new CaseIgnoreMapx();
		String TagName;
		String Body;
		TreeNode treeNode;

		public Mapx getAttributes() {
			return Attributes;
		}

		public String getTagName() {
			return TagName;
		}

		public String getBody() {
			return Body;
		}

		public TreeNode getTreeNode() {
			return treeNode;
		}

		public NodeData[] getChildrenDataList() {
			NodeData[] arr = new NodeData[treeNode.getChildren().size()];
			for (int i = 0; i < treeNode.getChildren().size(); i++) {
				arr[i] = (NodeData) treeNode.getChildren().get(i).getData();
			}
			return arr;
		}
	}
}
