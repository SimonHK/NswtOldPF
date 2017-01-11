package com.nswt.framework.utility;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

import com.nswt.framework.data.DataRow;
import com.nswt.framework.data.DataTable;

/**
 * �������ݽṹ
 * 
 * @Author NSWT
 * @Date 2008-4-16
 * @Mail nswt@nswt.com.cn
 */
public class Treex {
	private TreeNode root = new TreeNode();

	/**
	 * ��ȡ���ڵ����
	 */
	public TreeNode getRoot() {
		return root;
	}

	/**
	 * ���ݽڵ����ݻ�ȡ�ڵ����
	 */
	public TreeNode getNode(Object data) {
		TreeIterator ti = iterator();
		while (ti.hasNext()) {
			TreeNode tn = (TreeNode) ti.next();
			if (tn.getData().equals(data)) {
				return tn;
			}
		}
		return null;
	}

	/**
	 * ������������������
	 */
	public TreeIterator iterator() {
		return new TreeIterator(root);
	}

	/**
	 * ��nodeΪ��ʼ�ڵ㿪ʼ����
	 */
	public static TreeIterator iterator(TreeNode node) {
		return new TreeIterator(node);
	}

	/**
	 * ����������νṹΪ�ַ���
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return toString(Formatter.DefaultFormatter);
	}

	/**
	 * ��ָ���ĸ�ʽ����������νṹ
	 */
	public String toString(Formatter f) {
		StringBuffer sb = new StringBuffer();
		TreeIterator ti = this.iterator();
		while (ti.hasNext()) {
			TreeNode tn = ti.nextNode();
			for (int i = 0; i < tn.getLevel() - 1; i++) {
				// sb.append("�� ");
			}
			TreeNode p = tn.getParent();
			String str = "";
			while (p != null && !p.isRoot()) {
				if (p.isLast()) {
					str = "  " + str;
				} else {
					str = "�� " + str;
				}
				p = p.getParent();
			}
			sb.append(str);
			if (!tn.isRoot()) {
				if (tn.isLast()) {
					sb.append("����");
				} else {
					sb.append("����");
				}
			}
			sb.append(f.format(tn.getData()));
			sb.append("\n");
		}
		return sb.toString();
	}

	/**
	 * ��ȡ���нڵ���ɵ�����
	 */
	public TreeNode[] toArray() {
		TreeIterator ti = new TreeIterator(root);
		ArrayList arr = new ArrayList();
		while (ti.hasNext()) {
			arr.add(ti.next());
		}
		TreeNode[] tns = new TreeNode[arr.size()];
		for (int i = 0; i < arr.size(); i++) {
			tns[i] = (TreeNode) arr.get(i);
		}
		return tns;
	}

	/**
	 * ��ĳһ�ڵ�Ϊ���ڵ�����ýڵ����������ӽڵ�
	 */
	public static class TreeIterator implements Iterator {
		private TreeNode last;

		private TreeNode next;

		private TreeNode start;

		/**
		 * ��nodeΪ���ڵ㣬����һ��������
		 */
		TreeIterator(TreeNode node) {
			start = next = node;
		}

		/**
		 * �Ƿ�����һ���ڵ�
		 * 
		 * @see java.util.Iterator#hasNext()
		 */
		public boolean hasNext() {
			if (next == null) {
				return false;
			}
			if (next != start && next.getLevel() == start.getLevel()) {
				return false;
			}
			return true;
		}

		/**
		 * ��ȡ��һ���ڵ�
		 * 
		 * @see java.util.Iterator#next()
		 */
		public Object next() {
			if (next == null) {
				throw new NoSuchElementException();
			}
			last = next;
			if (next.hasChild()) {
				next = next.getChildren().get(0);
			} else {
				while (next.getNextSibling() == null) {
					if (next.parent.isRoot()) {
						next = null;
						return last;
					} else {
						next = next.parent;
					}
				}
				next = next.getNextSibling();
			}
			return last;
		}

		public TreeNode nextNode() {
			return (TreeNode) next();
		}

		public TreeNode currentNode() {
			return next;
		}

		/**
		 * ɾ����ǰ�ڵ�
		 * 
		 * @see java.util.Iterator#remove()
		 */
		public void remove() {
			if (last == null) {
				throw new IllegalStateException();
			}
			last.parent.getChildren().remove(last);
			last = null;
		}
	}

	/**
	 * ���νṹ�е�һ���ڵ�
	 * 
	 * @author NSWT
	 * @date 2009-11-16
	 * @email nswt@nswt.com.cn
	 */
	public static class TreeNode {
		private int level;

		private Object data;

		private TreeNodeList children = new TreeNodeList(this);

		private TreeNode parent;

		private int pos;// ���ϼ�Ԫ���е�λ��

		/**
		 * ���캯��
		 */
		public TreeNode() {
		}

		/**
		 * Ϊ���ڵ����һ���ӽڵ㣬��Ϊ�ӽڵ�ָ���ڵ�����
		 */
		public TreeNode addChild(Object data) {
			TreeNode tn = new TreeNode();
			tn.level = level + 1;
			tn.data = data;
			tn.parent = this;
			tn.pos = children.size();
			children.arr.add(tn);
			return tn;
		}

		/**
		 * ���ݽڵ�����ɾ��һ���ӽڵ�
		 */
		public void removeChild(Object data) {
			for (int i = 0; i < children.size(); i++) {
				if (children.get(i).getData().equals(data)) {
					children.remove(i);
					break;
				}
			}
		}

		/**
		 * ��һ���ڵ㣬û����һ���ڵ��򷵻�null
		 */
		public TreeNode getPreviousSibling() {
			if (pos == 0) {
				return null;
			}
			return parent.getChildren().get(pos - 1);
		}

		/**
		 * ��һ���ڵ�,û����һ���ڵ��򷵻�null
		 */
		public TreeNode getNextSibling() {
			if (parent == null || pos == parent.getChildren().size() - 1) {
				return null;
			}
			return parent.getChildren().get(pos + 1);
		}

		/**
		 * �ڵ������νṹ�еļ��𣬸��ڵ�Ϊ0
		 */
		public int getLevel() {
			return level;
		}

		/**
		 * �Ƿ��Ǹ��ڵ�
		 */
		public boolean isRoot() {
			return parent == null;
		}

		/**
		 * �Ƿ������һ���ڵ�
		 */
		public boolean isLast() {
			if (parent != null && pos != parent.getChildren().size() - 1) {
				return false;
			}
			return true;
		}

		/**
		 * �Ƿ����ӽڵ�
		 */
		public boolean hasChild() {
			return children.size() != 0;
		}

		/**
		 * ��ȡ��һ���ڵ����
		 */
		public TreeNode getParent() {
			return parent;
		}

		/**
		 * ��ǰ�ڵ��ڸ��ڵ�������ӽڵ����ǵڼ���
		 */
		public int getPosition() {
			return pos;
		}

		/**
		 * ��ȡ�����ӽڵ��б�
		 */
		public TreeNodeList getChildren() {
			return children;
		}

		/**
		 * ��ȡ�ڵ�����
		 */
		public Object getData() {
			return data;
		}

		/**
		 * ���ýڵ�����
		 */
		public void setData(Object data) {
			this.data = data;
		}
	}

	/**
	 * �ڵ��б���
	 * 
	 * @author NSWT
	 * @date 2009-11-16
	 * @email nswt@nswt.com.cn
	 */
	public static class TreeNodeList {
		protected ArrayList arr = new ArrayList();

		private TreeNode parent;

		/**
		 * ����ָ�����ڵ���ӽڵ��б����
		 */
		public TreeNodeList(TreeNode parent) {
			this.parent = parent;
		}

		/**
		 * ���һ���ӽڵ�
		 */
		public void add(TreeNode node) {
			parent.addChild(node);
		}

		/**
		 * ɾ��һ���ӽڵ�
		 */
		public TreeNode remove(TreeNode node) {
			int pos = node.getPosition();
			for (int i = pos + 1; i < arr.size(); i++) {
				TreeNode tn = (TreeNode) arr.get(i);
				tn.pos--;
			}
			arr.remove(node);
			return node;
		}

		/**
		 * ɾ����i���ӽڵ�
		 */
		public TreeNode remove(int i) {
			return remove((TreeNode) arr.get(i));
		}

		/**
		 * ��ȡ��i���ӽڵ�
		 */
		public TreeNode get(int i) {
			return (TreeNode) arr.get(i);
		}

		/**
		 * ��ȡ���һ���ӽڵ�
		 */
		public TreeNode last() {
			return (TreeNode) arr.get(arr.size() - 1);
		}

		/**
		 * �ӽڵ�����
		 */
		public int size() {
			return arr.size();
		}
	}

	/**
	 * ����DataTable����һ�����νṹ��DataTable������ID��ParentID�����ֶΣ������ֶι��ɸ��ӹ�ϵ
	 */
	public static Treex dataTableToTree(DataTable dt) {
		return dataTableToTree(dt, "ID", "ParentID");
	}

	/**
	 * ����DataTable����һ�����νṹ��DataTable��ָ���������ֶα��빹�ɸ��ӹ�ϵ
	 */
	public static Treex dataTableToTree(DataTable dt, String identifierColumnName, String parentIdentifierColumnName) {
		Treex tree = new Treex();
		Mapx map = dt.toMapx(identifierColumnName, parentIdentifierColumnName);
		Mapx map2 = dt.toMapx(parentIdentifierColumnName, identifierColumnName);
		for (int i = 0; i < dt.getRowCount(); i++) {
			String ID = dt.getString(i, identifierColumnName);
			String parentID = map.getString(ID);
			if (StringUtil.isEmpty(parentID) || !map.containsKey(parentID)) {
				DataRow dr = dt.getDataRow(i);
				TreeNode tn = tree.root.addChild(dr);
				dealNode(dt, tn, map2, identifierColumnName, parentIdentifierColumnName);
			}
		}
		return tree;
	}

	/**
	 * �ݹ鴦��DataTable�еĸ��ӹ�ϵ
	 */
	private static void dealNode(DataTable dt, TreeNode tn, Mapx map, String identifierColumnName,
			String parentIdentifierColumnName) {
		DataRow dr = (DataRow) tn.getData();
		String ID = dr.getString(identifierColumnName);
		for (int i = 0; i < dt.getRowCount(); i++) {
			String ChildID = dt.getString(i, identifierColumnName);
			String parentID = dt.getString(i, parentIdentifierColumnName);
			if (ChildID.equals("29") && ID.equals("28")) {
				System.out.println(1);
			}
			if (parentID != null && parentID.equals(ID)) {
				TreeNode childNode = tn.addChild(dt.getDataRow(i));
				if (map.get(ChildID) != null) {// ����Ҷ�ӽڵ�
					dealNode(dt, childNode, map, identifierColumnName, parentIdentifierColumnName);
				}
			}
		}
	}
}
