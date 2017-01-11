package com.nswt.framework.controls;

import java.util.regex.Matcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTag;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.nswt.framework.Constant;
import com.nswt.framework.Current;
import com.nswt.framework.utility.HtmlUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.ServletUtil;
import com.nswt.framework.utility.StringUtil;

/**
 * @Author NSWT
 * @Date 2007-6-23
 * @Mail nswt@nswt.com.cn
 */
public class InitTag extends BodyTagSupport {
	private static final long serialVersionUID = 1L;

	public static final String VarNameInCurrent = "InitParams";

	private String method;

	private Mapx map;

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
		/* ${_NSWT_LICENSE_CODE_} */
	}

	public int doStartTag() throws JspException {
		try {
			HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
			if (method == null || method.equals("")) {
				map = ServletUtil.getParameterMap(request);
			} else {
				Current.init(request, (HttpServletResponse) pageContext.getResponse(), method);
				Mapx params = ServletUtil.getParameterMap(request);
				Object o = Current.invokeMethod(method, new Object[] { params });
				if (o == null) {
					o = new Mapx();
				}
				if (!(o instanceof Mapx)) {
					throw new RuntimeException("调用z:init指定的method时发现返回类型不是Mapx");
				}
				map = (Mapx) o;
				Current.setVariable(VarNameInCurrent, map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 原为 BodyTag.EVAL_PAGE 返回6 在Weblogic10.3.3上init标签不能正常处理doAfterBody
		// 修改为 EVAL_BODY_BUFFERED 返回2
		return BodyTag.EVAL_BODY_BUFFERED;
	}

	public int doAfterBody() throws JspException {
		String content = this.getBodyContent().getString();
		try {
			content = HtmlUtil.replacePlaceHolder(content, map, true);
			// 替换${@Field}
			Matcher matcher = Constant.PatternSpeicalField.matcher(content);
			StringBuffer sb = new StringBuffer();
			int lastEndIndex = 0;
			while (matcher.find(lastEndIndex)) {
				sb.append(content.substring(lastEndIndex, matcher.start()));
				Object v = map.get(matcher.group(1));
				if (v != null && !v.equals("")) {
					if (matcher.group().indexOf('#') > 0) {
						sb.append(StringUtil.javaEncode(v.toString()));
					} else {
						sb.append(v.toString());
					}
				} else {
					sb.append(content.substring(matcher.start(), matcher.end()));
				}
				lastEndIndex = matcher.end();
			}
			sb.append(content.substring(lastEndIndex));
			content = sb.toString();
			this.getPreviousOut().print(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}
}
