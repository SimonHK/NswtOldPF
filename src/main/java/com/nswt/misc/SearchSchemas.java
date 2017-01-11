package com.nswt.misc;

import java.io.File;
import java.util.ArrayList;

public class SearchSchemas {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String filePath = "E:\\workspace\\AVICIT\\Java\\com\\nswt\\schema\\";
		File file = new File(filePath);
		File[] fileList = file.listFiles();
		ArrayList list = new ArrayList();
		for (int i=0; i<fileList.length; i++) {
			if ("java".equals(fileList[i].getName().substring(
					fileList[i].getName().lastIndexOf(".")+1))) {
				list.add(fileList[i].getAbsolutePath());
			}
		}
		
		for (int i=0; i<list.size(); i++) {
			String fileName = list.get(i).toString();
			if (fileName.indexOf("Schema") == -1) {
				continue;
			}
			System.out.println(fileName.substring(fileName.lastIndexOf("\\")+1, fileName.lastIndexOf(".")-6));
		}
	}
}
