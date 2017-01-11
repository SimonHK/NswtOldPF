package com.nswt.cms.tag;

import com.nswt.cms.dataservice.Form;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.schema.ZCCustomTableSchema;
import com.nswt.schema.ZCCustomTableSet;
import com.nswt.statical.tag.SimpleTag;
import com.nswt.statical.tag.TagAttributeDesc;
import com.nswt.statical.tag.TagBase;

/**
 * ���� : 2010-7-8 <br>
 * ����: NSWT <br>
 * ���䣺nswt@nswt.com.cn <br>
 */
public class CmsFormTag extends SimpleTag {

	public TagAttributeDesc[] getAllAttributeDescs() {
		return new TagAttributeDesc[] { new TagAttributeDesc("code", true) };
	}

	public String getPrefix() {
		return "cms";
	}

	public String getTagName() {
		return "form";
	}

	public int onTagStart() {
		String code = attributes.getString("code");
		StringBuffer sb = new StringBuffer();
		ZCCustomTableSet set = new ZCCustomTableSchema().query(new QueryBuilder("where Code=?", code));
		if (code == null || set.size() <= 0) {
			sb.append("����" + code + "û���ҵ�");
		} else {
			String parseContent = Form.getRuntimeFormContent(set.get(0));
			sb.append(parseContent);
			sb.append("</form>");
		}
		template.getWriter().print(sb);
		return TagBase.SKIP;
	}
}
