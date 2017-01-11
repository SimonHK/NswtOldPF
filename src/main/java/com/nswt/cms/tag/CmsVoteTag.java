package com.nswt.cms.tag;

import com.nswt.cms.template.CmsTemplateData;
import com.nswt.framework.Config;
import com.nswt.framework.data.DataRow;
import com.nswt.framework.utility.StringUtil;
import com.nswt.statical.tag.SimpleTag;
import com.nswt.statical.tag.TagAttributeDesc;
import com.nswt.statical.tag.TagBase;

/**
 * ���� : 2010-7-8 <br>
 * ����: NSWT <br>
 * ���䣺nswt@nswt.com.cn <br>
 */
public class CmsVoteTag extends SimpleTag {
	private DataRow vote = null;

	public TagAttributeDesc[] getAllAttributeDescs() {
		return new TagAttributeDesc[] { new TagAttributeDesc("name") };
	}

	public String getPrefix() {
		return "cms";
	}

	public String getTagName() {
		return "vote";
	}

	public int onTagStart() {
		String name = attributes.getString("name");
		String id = attributes.getString("id");

		CmsTemplateData templateData = new CmsTemplateData();
		templateData.setSite((DataRow) context.getHolderValue("${Site}"));
		templateData.setCatalog((DataRow) context.getHolderValue("${Catalog}"));
		templateData.setLevelStr(context.eval("Level"));
		// �Զ��壬�ɶ�����ʽ
		if (hasChildTag("z", "list")) {
			vote = templateData.getVoteData(name, id);
			if (vote == null) {
				if (StringUtil.isEmpty(id)) {
					template.getWriter().print("����Ϊ" + name + "�ĵ��鲻����!");
				} else {
					template.getWriter().print("IDΪ" + id + "�ĵ��鲻����!");
				}
				return TagBase.SKIP;
			}
			context.addDataVariable("Vote", vote);
			String html = "<div id='vote_" + vote.get("ID")
					+ "' class='votecontainer' style='text-align:left'><form id='voteForm_" + vote.get("ID")
					+ "' name='voteForm_" + vote.get("ID") + "' action='" + Config.getValue("ServicesContext")
					+ "/VoteResult.jsp' method='post' target='_blank'><input type='hidden' id='ID' name='ID' value='"
					+ vote.get("ID") + "'><input type='hidden' id='VoteFlag' name='VoteFlag' value='Y'>";
			template.getWriter().print(html);
			return TagBase.CONTINUE;
		} else {// Ĭ�ϵ�����ʽ������js
			template.getWriter().print(templateData.getVote(name, id));
			return TagBase.SKIP;
		}
	}

	public int onTagEnd() {
		String html = "<input type='submit' value='�ύ' onclick='return checkVote(" + vote.getString("ID")
				+ ");'>&nbsp;&nbsp<input type='button' value='�鿴' onclick='javascript:window.open(\""
				+ Config.getValue("ServicesContext") + "/VoteResult.jsp?ID=" + vote.getString("ID")
				+ "\",\"VoteResult\")'></form></div>";
		template.getWriter().print(html);
		return TagBase.CONTINUE;
	}
}
