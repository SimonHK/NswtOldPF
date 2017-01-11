package com.nswt.bbs;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.nswt.bbs.admin.ForumScore;
import com.nswt.cms.pub.PubFun;
import com.nswt.framework.Ajax;
import com.nswt.framework.User;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.UserLog;
import com.nswt.platform.pub.NoUtil;
import com.nswt.schema.ZCForumAttachmentSchema;
import com.nswt.schema.ZCForumGroupSchema;
import com.nswt.schema.ZCForumSchema;
import com.nswt.schema.ZCPostSchema;
import com.nswt.schema.ZCThemeSchema;
import com.nswt.schema.ZCForumMemberSchema;

public class PostAdd extends Ajax { 
	
	private static Mapx FACE_MAP = new Mapx();
	 
	private static Pattern[] patterns = new Pattern[]{
			Pattern.compile("<script.*?>(.*?)</script>", Pattern.CASE_INSENSITIVE),
			Pattern.compile("\\[b\\](.+?)\\[/b\\]"),
			Pattern.compile("\\[i\\](.+?)\\[/i\\]"),
			Pattern.compile("\\[u\\](.+?)\\[/u\\]"),
			Pattern.compile("\\[code\\](.+?)\\[/code\\]"),
			Pattern.compile("\\[img\\](.+?)\\[/img\\]"),
			Pattern.compile("\\[url\\](.+?)\\[/url\\]")			
	}; 
	
	private static String[] replaceMents = new String[]{
		"","",
		"<font style=\"font-weight:bolder;\">","</font>",
		"<font style=\"font-style:italic\">","</font>",
		"<font style=\"text-decoration:underline;\">","</font>",
		"<div style=\"border-style:1px;border-color:blue;\">���룺<hr/><font color=\"green\">","</font></div>",
		"<img border=0 src=\"http://","\" />",
		"<a href=\"http://","\">",
		"</a>"
	};
	
	static{
		FACE_MAP.put(":)"	, "<img border=0 src='Images/face/smile.gif' />");
		FACE_MAP.put(":lol"	, "<img border=0 src='Images/face/lol.gif' />");
		FACE_MAP.put(":hug:"	, "<img border=0 src='Images/face/hug.gif' />");
		FACE_MAP.put(":victory:"	, "<img border=0 src='Images/face/victory.gif' />");
		FACE_MAP.put(":time:"	, "<img border=0 src='Images/face/time.gif' />");
		FACE_MAP.put(":kiss:"	, "<img border=0 src='Images/face/kiss.gif' />");
		FACE_MAP.put(":handshake"	, "<img border=0 src='Images/face/handshake.gif' />");
		FACE_MAP.put(":call:"	, "<img border=0 src='Images/face/call.gif' />");
		FACE_MAP.put(":loveliness:"	, "<img border=0 src='Images/face/loveliness.gif' />");
		FACE_MAP.put(":Q"	, "<img border=0 src='Images/face/mad.gif' />");
		FACE_MAP.put(":L"	, "<img border=0 src='Images/face/sweat.gif' />");
		FACE_MAP.put(":("	, "<img border=0 src='Images/face/sad.gif' />");
		FACE_MAP.put(":D"	, "<img border=0 src='Images/face/biggrin.gif' />");
		FACE_MAP.put("cry"	, "<img border=0 src='Images/face/cry.gif' />");
		FACE_MAP.put(":@"	, "<img border=0 src='Images/face/huffy.gif' />");
		FACE_MAP.put(":o"	, "<img border=0 src='Images/face/shocked.gif' />");
		FACE_MAP.put(":P"	, "<img border=0 src='Images/face/tongue.gif' />");
		FACE_MAP.put(":$"	, "<img border=0 src='Images/face/shy.gif' />");
		FACE_MAP.put(";P"	, "<img border=0 src='Images/face/titter.gif' />");
		FACE_MAP.put(":funk:"	, "<img border=0 src='Images/face/funk.gif' />");
	}                 
	
	/**
	 * ����BBCODE���������
	 * @param msg
	 * @return
	 */
	public static String processMsg(String msg){ 
		StringBuffer sb = new StringBuffer();
		msg = msg.replaceAll(">", "&gt;");
		msg = msg.replaceAll("<", "&lt;");
		for(int i=0,j=0;i<patterns.length;i++,j+=2){
			Matcher m = patterns[i].matcher(msg);
			while(m.find()){
				String temp = replaceMents[j]+m.group(1)+replaceMents[j+1];
				if(i==patterns.length-1){//<a href=""></a>
					temp+=m.group(1)+replaceMents[j+2];
				}
				m.appendReplacement(sb, temp);					
				m.appendTail(sb);	
				msg = sb.toString();	
				sb.setLength(0);
			}	 
		}		
		sb = new StringBuffer(msg);
		Object[] keys = FACE_MAP.keyArray();		
		for(int i=0;i<keys.length;i++)
		{
			int p,fromIndex = 0;
			String key = (String)keys[i];
			while((p=sb.indexOf(key, fromIndex))>=0){
				sb.replace(p, p+key.length(), FACE_MAP.getString(keys[i]));
				fromIndex = p+FACE_MAP.getString(keys[i]).length();
			}
		}		
		return sb.toString();
	}

	public void add() {
		if (ForumUtil.isNotReplyPost($V("SiteID"), $V("ForumID"))) {
			Response.setLogInfo(0, "��û��Ȩ���ظ���");
			return;
		}
		ForumScore forumScore = new ForumScore($V("SiteID"));
		ForumPriv priv = new ForumPriv($V("SiteID"));
		Transaction trans = new Transaction();
		ZCPostSchema post = new ZCPostSchema();
		ZCThemeSchema theme = new ZCThemeSchema();
		ZCForumMemberSchema user = new ZCForumMemberSchema();
		ZCForumGroupSchema userGroup = new ZCForumGroupSchema();
		ZCForumSchema forum = new ZCForumSchema();

		post.setFirst("N");
		post.setValue(Request);
		post.setID(NoUtil.getMaxID("PostID"));
		post.setSiteID($V("SiteID"));
		post.setFirst("N");		
		post.setMessage(StringUtil.htmlEncode(getQuoteContent($V("PostID")) +processMsg( $V("Message" ))));
		theme.setID(post.getThemeID());
		theme.fill();
		forum = ForumCache.getForum(theme.getForumID());
		post.setAddUser(User.getUserName());
		post.setAddTime(new Date());
		post.setInvisible("Y");
		post.setVerifyFlag(forum.getVerify().equals("Y") ? "N" : "Y");
		user.setUserName(post.getAddUser());
		user.fill();
		userGroup = ForumCache.getGroup(user.getUserGroupID());
		if (userGroup.getVerify().equals("Y")) {
			post.setVerifyFlag("Y");
		}

		trans.add(user, Transaction.UPDATE);
		// ����û�����ֱ�ӷ���������Ƿ���Ҫ������óɷ�
		if (priv.hasPriv("Verify")) {
			post.setVerifyFlag("Y");
		}
		if (post.getVerifyFlag().equals("Y")) {
			post.setLayer(getMAXLayer(post.getThemeID()) + 1);
			forum.setPostCount(forum.getPostCount() + 1);
			user.setReplyCount(user.getReplyCount() + 1);
			forum.setLastPost(post.getSubject());
			forum.setLastPoster(post.getAddUser());
			forum.setLastThemeID(theme.getID());
			user.setForumScore(user.getForumScore() + forumScore.PublishPost);
			ForumUtil.userGroupChange(user);
			theme.setReplyCount(theme.getReplyCount() + 1);
			theme.setLastPostTime(new Date());
			theme.setOrderTime(new Date());
		}
		trans.add(post, Transaction.INSERT);
		trans.add(theme, Transaction.UPDATE);
		trans.add(forum, Transaction.UPDATE);
		if ($V("file").length() > 0) {
			String[] Attachments = $V("file").split(",");
			String[] indexs = $V("indexs").split(",");
			for (int i = 0; i < Attachments.length; i++) {
				ZCForumAttachmentSchema attachment = new ZCForumAttachmentSchema();
				attachment.setID(NoUtil.getMaxID("ForumAttachmentID"));
				attachment.setPostID(post.getID());
				attachment.setSiteID($V("SiteID"));
				String suffix = $V("file" + indexs[i]).substring(($V("file" + indexs[i]).lastIndexOf(".") + 1));
				if (PubFun.isAllowExt(suffix, "Attach")) {
					attachment.setType("attach");
				} else if (PubFun.isAllowExt(suffix, "Image")) {
					attachment.setType("image");
				} else if (PubFun.isAllowExt(suffix, "Audio")) {
					attachment.setType("audio");
				} else if (PubFun.isAllowExt(suffix, "Video")) {
					attachment.setType("video");
				} else {
					Response.setLogInfo(0, "�������ϴ����ļ�����");
					return;
				}
				attachment.setSuffix(suffix);
				attachment.setName($V("file" + indexs[i]).substring($V("file" + indexs[i]).lastIndexOf("\\") + 1));
				String[] file = Attachments[i].split("#");
				attachment.setPath(file[0]);
				attachment.setAttSize(file[1]);
				attachment.setDownCount(0);
				attachment.setAddUser(User.getUserName());
				attachment.setAddTime(new Date());
				trans.add(attachment, Transaction.INSERT);
			}
		}
		if (trans.commit()) {
			if (post.getVerifyFlag().equals("Y")) {
				Response.setLogInfo(1, "�ظ��ɹ�");
			} else {
				Response.setLogInfo(1, "����Ա��������˻��ƣ���ȴ����!");
			}
		} else {
			Response.setLogInfo(0, "�ظ�ʧ��!");
		}
	}

	public static Mapx init(Mapx params) {
		String ForumID = params.getString("ForumID");
		String ThemeID = params.getString("ThemeID");
		String SiteID = params.getString("SiteID");
		ZCThemeSchema theme = new ZCThemeSchema();
		theme.setID(ThemeID);
		theme.setForumID(ForumID);
		theme.fill();
		Mapx map = theme.toMapx();
		ZCForumSchema forum = ForumCache.getForum(ForumID);

		map.put("SiteID", params.getString("SiteID"));
		map.put("Name", forum.getName());
		map.put("ForumID", forum.getID());
		map.put("AddUser", User.getUserName());
		map.put("Priv", ForumUtil.initPriv(ForumID, SiteID));
		map.put("BBSName", ForumUtil.getBBSName(SiteID));
		return map;
	}

	public static Mapx initAddDialog(Mapx params) {
		String PostID = params.getString("ID");
		String SiteID = params.getString("SiteID");
		if (!StringUtil.isEmpty(PostID)) {
			ZCPostSchema post = new ZCPostSchema();
			post.setID(PostID);
			post.fill();
			params = post.toMapx();
			String subject = "������" + post.getAddUser() + "�Ļظ�";
			params.put("SiteID", SiteID);
			params.put("AddUser", User.getUserName());
			params.put("subject", subject);
			params.put("Priv", ForumUtil.initPriv(params.getString("SiteID")));
		}
		return params;
	}

	public static Mapx initEdit(Mapx params) {
		String PostID = params.getString("ID");

		if (!StringUtil.isEmpty(PostID)) {
			ZCPostSchema post = new ZCPostSchema();
			post.setID(PostID);
			post.fill();
			String message = post.getMessage();
			if (message.startsWith("<div class='quote'>")) {
				String subMessage = message.substring(message.lastIndexOf("</div>"));
				post.setMessage(subMessage);
			}

			params = post.toMapx();
			if (post.getFirst().equals("Y")) {
				params.put("verify", "NotNull");
			}
		}
		return params;
	}

	public void editPost() {

		Transaction trans = new Transaction();
		ZCPostSchema post = new ZCPostSchema();
		ZCThemeSchema theme = new ZCThemeSchema();
		post.setID($V("ID"));
		post.fill();
		// �ж��û���û�б༭Ȩ��
		if (!ForumUtil.isEditPost(post.getSiteID() + "", post.getForumID() + "", post.getAddUser())) {
			Response.setLogInfo(0, "��û�б༭���ӵ�Ȩ��");
			return;
		}
		theme.setID(post.getThemeID());
		theme.fill();
		String message = post.getMessage();
		post.setValue(Request);

		if (message.startsWith("<div class='quote'>")) {
			StringBuffer newMessage = new StringBuffer(message.substring(0, message.lastIndexOf("</div>") + 6));
			newMessage.append(post.getMessage());
			post.setMessage(newMessage.toString());
		}
		post.setMessage(StringUtil.htmlEncode(processMsg(post.getMessage())));
		if (post.getFirst().equals("Y")) {
			theme.setSubject(post.getSubject());
		}
		trans.add(post, Transaction.UPDATE);
		trans.add(theme, Transaction.UPDATE);
		if (trans.commit()) {
			UserLog.log(UserLog.FORUM, UserLog.FORUM_EDITPOST, "�༭�û�" + post.getAddUser() + "�����ӳɹ�", Request
					.getClientIP());
			Response.setLogInfo(1, "�޸ĳɹ�");
		} else {
			UserLog.log(UserLog.FORUM, UserLog.FORUM_EDITPOST, "�༭�û�" + post.getAddUser() + "������ʧ��", Request
					.getClientIP());
			Response.setLogInfo(0, "�޸�ʧ��");
		}
	}

	/**
	 * ��ȡ����ĵ�ǰ���¥����
	 * 
	 * @param ThemeID
	 * @return
	 */
	private long getMAXLayer(long ThemeID) {
		QueryBuilder qb = new QueryBuilder("select Layer from ZCPost where ThemeID=? order by Layer desc", ThemeID);
		long layer = qb.executePagedDataTable(1, 0).getLong(0, 0);
		return layer;
	}

	/**
	 * ���ûظ����ַ�������
	 * 
	 * @param PostID
	 * @return
	 */
	private String getQuoteContent(String PostID) {
		ZCPostSchema post = new ZCPostSchema();
		post.setID(PostID);
		post.fill();
		String quote = post.getMessage();
		if (quote.indexOf("<h5>����</h5>") >= 0) {
			quote = StringUtil.replaceAllIgnoreCase(quote, "<h5>����</h5>", "");
		}
		String content = "<div class='quote'><div class='blockquote'><h5>����</h5>";
		content += "<i>" + post.getAddUser() + "</i>������" + post.getAddTime() + "<br>";
		quote = content + quote + "</div></div>";
		return quote;
	}
	
	

}
