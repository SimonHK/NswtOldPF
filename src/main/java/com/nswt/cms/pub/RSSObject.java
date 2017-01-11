package com.nswt.cms.pub;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.nswt.framework.Constant;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;

/**
 * @Author NSWT
 * @Date 2008-12-9
 * @Mail nswt@nswt.com.cn
 */

public class RSSObject {
	private ArrayList channels = new ArrayList();

	public String toString() {
		Document doc = DocumentHelper.createDocument();
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding(Constant.GlobalCharset);
		Element root = doc.addElement("rss");
		root.addAttribute("version", "2.0");
		for (int i = 0; i < channels.size(); i++) {
			RSSChannel rchannel = (RSSChannel) channels.get(i);
			Element channel = root.addElement("channel");
			Mapx map = rchannel.getAttributeMap();
			Object[] ks = map.keyArray();
			Object[] vs = map.valueArray();
			for (int j = 0; j < map.size(); j++) {
				Element ele = channel.addElement((String) ks[j]);
				ele.setText(vs[j].toString());
			}
			for (int j = 0; j < rchannel.size(); j++) {
				RSSItem ritem = rchannel.getItem(j);
				Element item = channel.addElement("item");
				map = ritem.getAttributeMap();
				ks = map.keyArray();
				vs = map.valueArray();
				for (int k = 0; k < map.size(); k++) {
					String key = (String) ks[k];
					String value = vs[k].toString();
					Element ele = item.addElement(key);
					if (key.equals("description")) {
						ele.addCDATA(value);
					} else {
						if (StringUtil.isNotEmpty(value)) {
							ele.setText(value);
						} else {
							LogUtil.info(map);
						}
					}
				}
			}
		}

		StringWriter sw = new StringWriter();
		try {
			XMLWriter output = new XMLWriter(sw, format);
			output.write(doc);
			output.close();
		} catch (IOException e) {
			LogUtil.info(e.getMessage());
		}
		return sw.toString();
	}

	public void addChannel(RSSChannel channel) {
		channels.add(channel);
	}
}