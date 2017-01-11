package com.nswt.framework.controls;

import java.lang.reflect.Method;

import com.nswt.framework.Ajax;
import com.nswt.framework.Constant;
import com.nswt.framework.utility.StringUtil;

/**
 * @Author NSWT
 * @Date 2008-1-28
 * @Mail nswt@nswt.com.cn
 */
public class TreePage extends Ajax {
	public void doWork() {
		try {
			TreeAction ta = new TreeAction();

			ta.setTagBody(StringUtil.htmlDecode($V(Constant.TagBody)));
			String method = $V(Constant.Method);
			ta.setMethod(method);

			if ("true".equals($V(Constant.TreeLazy))) {
				if (!"false".equals($V(Constant.TreeExpand))) {
					ta.setExpand(true);
				}
				ta.setLazy(true);
			}

			if ($V("ParentLevel") != null && !"".equals($V("ParentLevel"))) {
				ta.setParentLevel(Integer.parseInt($V("ParentLevel")));
				ta.setLazyLoad(true);
			}

			// System.out.println(dga.getTagBody());

			ta.setID($V(Constant.ID));
			ta.setParams(Request);

			String levelStr = $V(Constant.TreeLevel);
			String style = $V(Constant.TreeStyle);

			int level = Integer.parseInt(levelStr);
			if (level <= 0) {
				level = 999;
			}
			ta.setLevel(level);
			ta.setStyle(style);

			HtmlP p = new HtmlP();
			p.parseHtml(ta.getTagBody());
			ta.setTemplate(p);

			int index = method.lastIndexOf('.');
			String className = method.substring(0, index);
			method = method.substring(index + 1);
			Class c = Class.forName(className);
			Method m = c.getMethod(method, new Class[] { TreeAction.class });
			m.invoke(null, new Object[] { ta });

			$S("HTML", ta.getHtml());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
