package com.nswt.cms.site;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nswt.framework.data.QueryBuilder;
import com.nswt.schema.ZCSiteSchema;
import com.nswt.schema.ZCSiteSet;

/**
 * �ļ���
 * @author huanglei
 * @mail huanglei@nswt.com
 * @date 2007-9-6
 */

public class FileManager {

	DirectoryFilter dFilter ;

	int count;

	Map treeMap;

	public FileManager(){
		this.dFilter = new DirectoryFilter();
	}
	
	public FileManager(String filterName){
		this.dFilter = new DirectoryFilter(filterName);
	}
	
	/**
	 * ��ߵ��ļ�����
	 * @return
	 */
	public String getLeftHtml() {
		StringBuffer sb = new StringBuffer(51200);
		ZCSiteSet siteSet = null;
		ZCSiteSchema site = null;
		siteSet = new ZCSiteSchema().query(new QueryBuilder(" where 1=1 order by orderflag asc"));
		// ����վ����
		for (int j = 0; j < siteSet.size(); j++) {
			treeMap = null;
			treeMap = new HashMap(128);
			site = siteSet.get(j);
			String rootPath = site.getRootPath();
			File file = new File(rootPath);
			// ���������ļ�����
			if(file.exists()){
				getFileTreeEntry(file, 0, null);
			}

			String dblClick = "";
			if (!treeMap.isEmpty()) {
				dblClick = " ondblclick=\"onParentClick('Site" + site.getID()
						+ "')\"";
			}

			sb.append("<div");
			sb
					.append("  id='site"
							+ site.getID()
							+ "' oncontextmenu = \"this.onclick();showMenu(event,'');\" onclick=\"onEleClick('FileList.jsp','','"+site.getID()+"',this)\" class=tree-item "
							+ dblClick + " onselectstart='return false'>\n");
			if (treeMap.isEmpty()) {
				sb.append("\t<img src='../Icons/treeicon10.gif' />");
				sb.append("<span id='treeplussSite'" + site.getID() + "'>վ�㣺"
						+ site.getName() + "</span>");
			} else {
				sb
						.append("\t<img src='../Icons/treeicon10.gif' onclick=\"onParentClick('Site"
								+ site.getID() + "')\"/>");
				sb.append("<span  id='treeplussSite'" + site.getID() + "'>վ�㣺"
						+ site.getName() + "</span>");
			}
			sb.append("</div>\n");
			if (!treeMap.isEmpty()) {
				sb.append(createTree(null, site.getID()));	
			}
		}
		return sb.toString();
	}

	/**
	 * �ݹ��㷨��
	 * @param dga
	 */
	public StringBuffer createTree(TreeEntry parentEntry, long siteID) {
		StringBuffer sb = new StringBuffer(1024);

		// ����ǵ�һ����Ŀ����վ���id��ΪparentID��������ɻ���
		if (null == parentEntry) {
			sb.append("<div style=\"display:'';\" id='childSite" + siteID
					+ "' >\n");
		} else {
			sb.append("<div style=\"display:none;\" id='child" + parentEntry.ID
					+ "' >\n");
		}

		// ��treeMap�и��ݸ����ȡ�������ӽ���б�list
		List list = (List) treeMap.get(parentEntry);
		for (int i = 0; i < list.size(); i++) {
			TreeEntry entry = (TreeEntry) list.get(i);

			String dblClick = "";
			if (!entry.isLeaf) {
				dblClick = " ondblclick='onParentClick(" + entry.ID + ")'";
			}
			sb
					.append("\t<div id='file"
							+ entry.ID
							+ "' oncontextmenu = \"this.onclick();showMenu(event,'"+entry.para+"')\" onclick=\"onEleClick('FileList.jsp','"
							+ entry.para
							+ "','"+siteID+"',this)\" class=tree-item onselectstart='return false'"
							+ dblClick + ">\n");
			// ����ǰ׺
			for (int k = 0; k < entry.suffix.length(); k++) {
				char c = entry.suffix.charAt(k);
				if (c == '0') {
					sb.append("<img src='../Icons/treeicon08.gif' />");
				} else if (c == '1') {
					sb.append("<img src='../Icons/treeicon03.gif' />");
				}
			}

			if (!entry.isLeaf) {
				if (entry.isLastNode) {
					sb.append("<img src='../Icons/treeicon04.gif' id='treeplus"
							+ entry.ID + "' onclick='onParentClick(" + entry.ID
							+ ",this)'/>");
				} else {
					sb.append("<img src='../Icons/treeicon01.gif' id='treeplus"
							+ entry.ID + "' onclick='onParentClick(" + entry.ID
							+ ",this)'/>");
				}
			} else {
				if (entry.isLastNode) {
					sb.append("<img src='../Icons/treeicon07.gif' />");
				} else {
					sb.append("<img src='../Icons/treeicon06.gif' />");
				}
			}

			sb.append("<img src='../Icons/treeicon09.gif' /><span>"
					+ entry.name + "</span>");
			sb.append("</div>\n");
			if (!entry.isLeaf) {
				sb.append(createTree(entry, siteID));
			}
		}
		sb.append("</div>\n");
		return sb;
	}

	/**
	 * �����ļ���parentFile��������������������ļ�����
	 * 
	 * @param listFiles
	 * @param level
	 * @param ParentEntry
	 */
	public void getFileTreeEntry(File parentFile, int level,
			TreeEntry ParentEntry) {
		File[] listFiles = parentFile.listFiles(dFilter);
		if (ParentEntry != null) {
			if (listFiles.length > 0) {
				ParentEntry.isLeaf = false;
			} else {
				return;
			}
		}
		for (int i = 0; i < listFiles.length; i++) {
			File file = listFiles[i];
			if (file.isDirectory()) {
				boolean isLastNode = false;
				if (i == listFiles.length - 1)
					isLastNode = true;
				TreeEntry entry = new TreeEntry(ParentEntry, ++count, file
						.getName(), true, isLastNode);
				if (ParentEntry == null) {
					entry.suffix = "";
				} else {
					if (ParentEntry.isLastNode) {
						entry.suffix = ParentEntry.suffix + "0";
					} else {
						entry.suffix = ParentEntry.suffix + "1";
					}
				}
				insertFileTreeMap(ParentEntry, entry);
				getFileTreeEntry(file, level + 1, entry);
			}
		}
	}

	/**
	 * ��parentEntry�������Ϊkey,���������ļ���entry��ӵ�����Ӧ��value�У�valueΪһ��List
	 * 
	 * @param parentEntry
	 * @param entry
	 */
	public void insertFileTreeMap(TreeEntry parentEntry, TreeEntry entry) {
		if (treeMap.containsKey(parentEntry)) {
			List list = (List) treeMap.get(parentEntry);
			list.add(entry);
		} else {
			List list = new ArrayList();
			list.add(entry);
			treeMap.put(parentEntry, list);
		}
	}

	class DirectoryFilter implements FileFilter {
		
		String filterName;

		public DirectoryFilter(){
			
		}
		
		public DirectoryFilter(String filterName){
			this.filterName = filterName;
		}
		
		public boolean accept(File f) {
			if (f.isDirectory()) {
				if(filterName==null||"".equals(filterName)){
					return true;
				}else if(filterName.equals(f.getName())){
					return true;
				}else{
					return false;
				}
			}
			return false;
		}
	}

	class TreeEntry {

		TreeEntry parent;

		String suffix = ""; // �����־��������¼������ǰ���ʲô���͵�ͼ��

		int ID;

		String name;

		String para; // �����ֶΣ��������������

		boolean isLeaf; // �Ƿ���Ҷ�ӽ��

		boolean isLastNode; // ��ͬһ�㼶���Ƿ������һ���ļ���

		TreeEntry(TreeEntry parent, int ID, String name, boolean isLeaf,
				boolean isLastNode) {
			this.parent = parent;
			this.ID = ID;
			this.name = name;
			this.isLeaf = isLeaf;
			this.isLastNode = isLastNode;
			if (this.parent == null) {
				this.para = this.name;
			} else {
				this.para = parent.para + "/" + this.name;
			}

		}
	}
}
