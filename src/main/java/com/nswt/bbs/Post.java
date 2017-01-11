package com.nswt.bbs;

import java.text.DecimalFormat;
import java.util.Date;

import com.nswt.bbs.admin.ForumScore;
import com.nswt.cms.pub.PubFun;
import com.nswt.framework.Ajax;
import com.nswt.framework.Config;
import com.nswt.framework.User;
import com.nswt.framework.cache.CacheManager;
import com.nswt.framework.controls.DataListAction;
import com.nswt.framework.data.DataTable;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.UserLog;
import com.nswt.platform.pub.NoUtil;
import com.nswt.schema.ZCForumAttachmentSchema;
import com.nswt.schema.ZCForumMemberSchema;
import com.nswt.schema.ZCForumSchema;
import com.nswt.schema.ZCPostSchema;
import com.nswt.schema.ZCThemeSchema;

public class Post extends Ajax {

	/**
	 * ��ʾ�����б�
	 * 
	 * @param dla
	 */
	public static void getList(DataListAction dla) {
		String ThemeID = dla.getParams().getString("ThemeID");
		String ForumID = dla.getParam("ForumID");
		String SiteID = dla.getParam("SiteID");

		QueryBuilder qb = new QueryBuilder(
				"select a.*,b.ThemeCount,b.ReplyCount,b.ForumScore, b.NickName,b.HeadImage,b.UseSelfImage,b.ForumSign,b.AdminID,b.DefinedID,c.RegTime RegisterTime,c.LastLoginTime,d.Name GroupName,d.Image,d.Color "
						+ "from ZCPost a,ZCForumMember b,ZDMember c,ZCForumGroup d "
						+ "where a.AddUser=c.UserName and a.AddUser=b.UserName and VerifyFlag='Y' and Invisible='Y' and b.UserGroupID=d.ID and ThemeID=?"
						+ " order by layer", ThemeID);
		if("Y".equals(dla.getParam("LastPage"))){
			dla.getParams().put("LastPage", "");
			dla.setPageIndex((dla.getTotal()-1)/dla.getPageSize());
		}
		DataTable dt = qb.executePagedDataTable(dla.getPageSize(), dla.getPageIndex());
		loadFile(dt);
		dt = addColumns(SiteID, ForumID, dt);
		for (int i = 0; i < dt.getRowCount(); i++) {
			String imagePath = dt.getString(i, "HeadImage");
			if (imagePath.indexOf("../") == 0) {
				dt.set(i, "HeadImage", imagePath.substring(3));
			}
			String message = dt.getString(i, "Message");
			if (StringUtil.isNotEmpty(message)) {
				message = message.replaceAll("\\n", "<br>");
			}
			dt.set(i, "Message", message);
		}
		dla.setTotal(qb);
		dla.bindData(dt);
	}

	/**
	 * ���ӵĸ�����ʾ
	 * 
	 * @param dt
	 */
	public static void loadFile(DataTable dt) {
		dt.insertColumn("file");
		for (int i = 0; i < dt.getRowCount(); i++) {
			String ID = dt.getString(i, "ID");
			DataTable dtFile = new QueryBuilder("select * from ZCForumAttachment where PostID=?", ID)
					.executeDataTable();
			StringBuffer sb = new StringBuffer();
			if (dt.getRowCount() > 0) {
				for (int j = 0; j < dtFile.getRowCount(); j++) {
					float size = Long.parseLong(dtFile.getString(j, "AttSize"));
					DecimalFormat df = new DecimalFormat("#0.0");
					String fileSize = "";
					if (size < 1000000 && size >= 1000) {
						fileSize = df.format((size / 1024)) + "KB";
					} else if (size >= 1000000) {
						fileSize = df.format(size / 1024000) + "MB";
					} else if (size < 1000) {
						fileSize = 1 + "KB";
					}
					String[] ext = { "jpg", "gif", "zip", "rar", "bmp", "png", "doc", "xls", "html", "js", "mov",
							"mp4", "flv", "rm", "wmv", "swf", "txt", "mp3", "avi", "ppt", "pdf", "pptx", "xlsx", "docx" };
					String suffix = dtFile.get(j, "Suffix") + "";
					for (int n = 0; n < ext.length; n++) {
						if (ext[n].equalsIgnoreCase(suffix)) {
							sb.append("<img src='Images/FileType/" + ext[n] + ".gif' width='16' height='16' title='"
									+ suffix + "'/>&nbsp;&nbsp;");
							break;
						}
					}
					sb.append("<a href='#;' onclick='downLoad(" + dtFile.getString(j, "ID") + ")'>"
							+ dtFile.getString(j, "Name") + "</a>(" + fileSize + ") �����أ�"
							+ dtFile.getString(j, "DownCount") + "��<br/><br/>");
					if (dtFile.getString(j, "Type").equals("image")) {
						sb.append("<img  onload='resetSize(this)' src='/" + Config.getContextPath()
								+ dtFile.getString(j, "Path") + "'><br/><br/>");
					}
				}
				dt.set(i, "file", sb.toString());
			} else {
				dt.set(i, "file", "&nbsp;");
			}
		}
	}

	/**
	 * ��������
	 * 
	 */
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
		ZCForumSchema forum = new ZCForumSchema();
		ZCForumMemberSchema user = new ZCForumMemberSchema();

		post.setValue(Request);
		post.setFirst("N");
		post.setID(NoUtil.getMaxID("PostID"));
		post.fill();
		post.setAddUser(User.getUserName());
		post.setAddTime(new Date());
		post.setInvisible("Y");
		post.setSiteID($V("SiteID"));
		theme.setID(post.getThemeID());
		theme.fill();
		forum = ForumCache.getForum(theme.getForumID());
		post.setVerifyFlag(forum.getVerify().equals("Y") ? "N" : "Y");
		user.setUserName(post.getAddUser());
		user.fill();
		if (priv.hasPriv("Verify")) {
			post.setVerifyFlag("Y");
		}
		if (post.getVerifyFlag().equals("Y")) {
			post.setLayer(getMAXLayer(post.getThemeID()) + 1);
			forum.setPostCount(forum.getPostCount() + 1);
			theme.setReplyCount(theme.getReplyCount() + 1);
			theme.setLastPostTime(new Date());
			theme.setOrderTime(new Date());
			user.setReplyCount(user.getReplyCount() + 1);
			user.setForumScore(user.getForumScore() + forumScore.PublishPost);
			ForumUtil.userGroupChange(user);
			trans.add(user, Transaction.UPDATE);
		}
		post.setMessage(PostAdd.processMsg(post.getMessage()));
		trans.add(post, Transaction.INSERT);
		trans.add(theme, Transaction.UPDATE);
		trans.add(forum, Transaction.UPDATE);
		if (StringUtil.isNotEmpty($V("file")) && $V("file").length() > 0) {
			String[] Attachments = $V("file").split(",");
			String[] indexs = $V("indexs").split(",");
			for (int i = 0; i < Attachments.length; i++) {
				ZCForumAttachmentSchema attachment = new ZCForumAttachmentSchema();
				attachment.setID(NoUtil.getMaxID("ForumAttachmentID"));
				attachment.setPostID(post.getID());
				attachment.setSiteID($V("StieID"));
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
				CacheManager.set("Forum", "Forum", forum.getID(), forum);
				Response.setLogInfo(1, "�ظ��ɹ�");
			} else {
				Response.setLogInfo(1, "����Ա��������˻��ƣ���ȴ����!");
			}
		} else {
			Response.setLogInfo(0, "�ظ�ʧ��!");
		}
	}

	/**
	 * ���������
	 * 
	 */
	public void addViewCount() {
		ZCThemeSchema theme = new ZCThemeSchema();
		theme.setID($V("ThemeID"));
		theme.fill();
		theme.setViewCount(theme.getViewCount() + 1);
		if (theme.update()) {
			Response.setLogInfo(1, "");
		} else {
			Response.setLogInfo(0, "");
		}
	}

	public static Mapx init(Mapx params) {
		String ForumID = params.getString("ForumID");
		String ThemeID = params.getString("ThemeID");
		String user = params.getString("User");
		String LastPoster = params.getString("LastPoster");
		String SiteID = params.getString("SiteID");

		QueryBuilder qb = new QueryBuilder("select ThemeID,ForumID from ZCPost where Invisible='Y' and VerifyFlag='Y'"
				+ " and first='Y' and ThemeID>?", ThemeID);
		if (!StringUtil.isEmpty(user) && user.equals("current")) {
			qb.append(" and AddUser=?", User.getUserName());
		} else if (!StringUtil.isEmpty(user) && user.equals("reply")) {
			qb.append(" and exists (select 1 from ZCPost where first='N' and AddUser=? and ThemeID=ZCPost.ThemeID)", User.getUserName());
		} else if (!StringUtil.isEmpty(LastPoster)) {
			qb.append(" and AddUser=?", LastPoster);
		} else {
			qb.append(" and ForumID=?", ForumID);
		}
		qb.append(" order by ThemeID");
		DataTable dtNext = qb.executePagedDataTable(1, 0);
		String sql = qb.getSQL();
		sql = StringUtil.replaceEx(sql, "ThemeID>", "ThemeID<") + " desc";
		qb.setSQL(sql);
		DataTable dtPrevious = qb.executePagedDataTable(1, 0);

		String ThemeIDNext = (dtNext.getRowCount() > 0) ? dtNext.getString(0, "ThemeID") : null;
		String ThemeIDPrevious = (dtPrevious.getRowCount() > 0) ? dtPrevious.getString(0, "ThemeID") : null;
		String ForumIDNext = (dtNext.getRowCount() > 0) ? dtNext.getString(0, "ForumID") : null;
		String ForumIDPrevious = (dtPrevious.getRowCount() > 0) ? dtPrevious.getString(0, "ForumID") : null;
		ZCThemeSchema theme = new ZCThemeSchema();
		theme.setID(ThemeID);
		theme.fill();
		Mapx map = theme.toMapx();
		ZCForumSchema forum = ForumCache.getForum(ForumID);

		ForumPriv priv = new ForumPriv(SiteID);
		ForumRule rule = new ForumRule(ForumID);
		if (ForumUtil.isAdmin(ForumID, SiteID) > 0 && priv.hasPriv("AllowEdit")) {
			map.put("editPriv", "Y");
		}

		if (ForumUtil.isNotReplyPost(SiteID, ForumID)) {
			map.put("RuleReply", "N");
		}

		if (ForumUtil.isReplyPost(SiteID, ForumID)) {
			map.put("NewReplyButton", "<a href='#;' onclick='reply()'>�ظ�������</a>");
		}
		if (rule.getRule("AllowTheme") || ForumUtil.isAdmin(ForumID, SiteID) > 0) {
			if (priv.hasPriv("AllowTheme")) {
				map.put("NewThemeButton", "<a href='ThemeAdd.jsp?ForumID=" + ForumID + "&SiteID=" + SiteID
						+ "'>�����»���</a>");
			}
		}
		map.put("Name", forum.getName());
		map.put("AddUser", User.getUserName());
		map.put("ThemeIDPrevious", ThemeIDPrevious);
		map.put("ThemeIDNext", ThemeIDNext);
		map.put("ForumIDPrevious", ForumIDPrevious);
		map.put("ForumIDNext", ForumIDNext);
		map.put("Priv", ForumUtil.initPriv(ForumID, SiteID));
		map.put("StieID", SiteID);
		map.put("BBSName", ForumUtil.getBBSName(SiteID));
		return map;
	}

	/**
	 * ɾ������
	 * 
	 */
	public void del() {
		String ID = $V("ID");
		ForumScore forumScore = new ForumScore($V("SiteID"));
		Transaction trans = new Transaction();
		ZCPostSchema post = new ZCPostSchema();
		post.setID(ID);
		post.fill();
		post.setInvisible("N");
		trans.add(post, Transaction.UPDATE);
		// ���ɾ���������⣬flagΪtrue
		boolean flag = post.getFirst().equals("Y") ? true : false;
		QueryBuilder qb = new QueryBuilder();
		if (flag) {
			qb.append("update ZCpost set Invisible='N' where ThemeID=?", post.getThemeID());
		} else {
			qb.append("update ZCpost set layer=layer-1 where layer>?", post.getLayer());
		}

		trans.add(qb);
		ZCThemeSchema theme = new ZCThemeSchema();
		theme.setID(post.getThemeID());
		theme.fill();
		if (flag) {
			theme.setStatus("N");
		} else {
			theme.setReplyCount(theme.getReplyCount() - 1);
		}
		trans.add(theme, Transaction.UPDATE);
		ZCForumSchema forum = ForumCache.getForum(theme.getForumID());
		if (flag) {
			// ���ɾ���������⣬���ж��ǲ����������Ȼ������жϸ��İ����е��������Ϣ
			forum.setThemeCount(forum.getThemeCount() - 1);
			forum.setPostCount(forum.getPostCount() - theme.getReplyCount() - 1);
			qb = new QueryBuilder("select count(*) from ZCTheme where status='Y' and VerifyFlag='Y'"
					+ " and ForumID=? and ID>?", forum.getID(), theme.getID());
			int count = qb.executeInt();
			if (count == 0) {
				qb = new QueryBuilder("select Subject,AddUser from ZCTheme where status='Y' and VerifyFlag='Y'"
						+ " and ForumID=? and ID<? order by ID desc", forum.getID(), theme.getID());
				DataTable dt = qb.executePagedDataTable(1, 0);
				if (dt.getRowCount() > 0) {
					forum.setLastPost(dt.getString(0, "Subject"));
					forum.setLastPoster(dt.getString(0, "AddUser"));
				} else {
					forum.setLastPost("");
					forum.setLastPoster("");
				}
			}
		}
		forum.setPostCount(forum.getPostCount() - 1);
		trans.add(forum, Transaction.UPDATE);
		ZCForumMemberSchema user = new ZCForumMemberSchema();
		user.setUserName(post.getAddUser());
		user.fill();
		// ����ɾ����������߻ظ�����̳�û���Ϣ��֮��Ҫ����
		if (flag) {
			user.setForumScore(user.getForumScore() + forumScore.DeleteTheme);
			user.setThemeCount(user.getThemeCount() - 1);
		} else {
			user.setForumScore(user.getForumScore() + forumScore.DeletePost);
			user.setReplyCount(user.getReplyCount() - 1);
		}
		ForumUtil.userGroupChange(user);
		trans.add(user, Transaction.UPDATE);
		if (trans.commit()) {
			CacheManager.set("Forum", "Forum", forum.getID(), forum);
			UserLog.log(UserLog.FORUM, UserLog.FORUM_DELPOST, "ɾ���û�" + post.getAddUser() + "�����ӳɹ�", Request
					.getClientIP());
			if (flag) {
				Response.setLogInfo(2, "ɾ���ɹ�");
				return;
			}
			Response.setLogInfo(1, "ɾ���ɹ�");
		} else {
			UserLog.log(UserLog.FORUM, UserLog.FORUM_DELPOST, "ɾ���û�" + post.getAddUser() + "������ʧ��", Request
					.getClientIP());
			Response.setLogInfo(0, "ɾ��ʧ��!");
		}

	}

	/**
	 * ǰ̨����༭��ťͨ��ajax���ص�ǰ��������
	 * 
	 */
	public void edit() {
		ZCPostSchema post = new ZCPostSchema();
		post.setID($V("ID"));
		if (post.fill()) {
			if (!ForumUtil.isOperateMember(post.getAddUser())) {
				Response.setLogInfo(0, "�����ܱ༭���û��ķ�����");
				return;
			}
			if (post.getInvisible().equals("N")) {
				Response.setLogInfo(0, "�����ѱ�ɾ��");
				return;
			}
			Response.setStatus(1);
			Response.put("Message", post.getMessage());
		} else {
			Response.setLogInfo(0, "����������");
		}
	}

	/**
	 * �Ա༭������ݸ��µ����ݿ�
	 * 
	 */
	public void editSave() {
		Transaction trans = new Transaction();
		ZCPostSchema post = new ZCPostSchema();
		post.setID($V("ID"));
		if (post.fill()) {
			if (!ForumUtil.isEditPost(post.getSiteID() + "", post.getForumID() + "", post.getAddUser())) {
				Response.setLogInfo(0, "��û�б༭���ӵ�Ȩ��");
				return;
			}
			if (post.getInvisible().equals("N")) {
				Response.setLogInfo(0, "�����ѱ�ɾ��");
				return;
			}
			post.setValue(Request);
			post.setMessage(PostAdd.processMsg(post.getMessage()));
			trans.add(post, Transaction.UPDATE);
			if (trans.commit()) {
				UserLog.log(UserLog.FORUM, UserLog.FORUM_EDITPOST, "�༭�û�" + post.getAddUser() + "�����ӳɹ�", Request
						.getClientIP());
				Response.setLogInfo(1, "�޸ĳɹ�");
			} else {
				UserLog.log(UserLog.FORUM, UserLog.FORUM_EDITPOST, "�༭�û�" + post.getAddUser() + "������ʧ��", Request
						.getClientIP());
				Response.setLogInfo(0, "�޸�ʧ��");
			}
		} else {
			Response.setLogInfo(0, "����������");
		}
	}

	/**
	 * ��������
	 * 
	 */
	public void hide() {
		ZCPostSchema post = new ZCPostSchema();
		post.setID($V("ID"));
		post.fill();
		if (StringUtil.isNotEmpty(post.getIsHidden()) && post.getIsHidden().equals("Y")) {
			post.setIsHidden("N");
		} else {
			post.setIsHidden("Y");
		}
		if (post.update()) {
			UserLog.log(UserLog.FORUM, UserLog.FORUM_HIDEPOST, "���/�����û�" + post.getAddUser() + "�����ε����ӳɹ�", Request
					.getClientIP());
			Response.setLogInfo(1, "�����ɹ���");
		} else {
			UserLog.log(UserLog.FORUM, UserLog.FORUM_HIDEPOST, "���/�����û�" + post.getAddUser() + "�����ε�����ʧ��", Request
					.getClientIP());
			Response.setLogInfo(0, "����ʧ�ܣ�");
		}
	}

	/**
	 * �жϵ�ǰ����Ա�Ƿ���Ȩ���Է����û����в���
	 * 
	 */
	public void isOperaterMember() {
		ZCPostSchema post = new ZCPostSchema();
		post.setID($V("ID"));
		post.fill();
		if (!ForumUtil.isOperateMember(post.getAddUser())) {
			Response.setStatus(0);
		} else {
			Response.setStatus(1);
		}
	}

	/**
	 * �õ���Ӧ��������¥����
	 * 
	 * @param ThemeID
	 * @return
	 */
	private long getMAXLayer(long ThemeID) {
		QueryBuilder qb = new QueryBuilder(
				"select Layer from ZCPost where Invisible='Y' and ThemeID=? order by Layer desc", ThemeID);
		long layer = qb.executePagedDataTable(1, 0).getLong(0, 0);
		return layer;
	}

	/**
	 * �����û�Ȩ����Ӱ�ť
	 * 
	 * @param ThemeID
	 * @param dt
	 * @return
	 */
	private static DataTable addColumns(String SiteID, String ForumID, DataTable dt) {
		dt.insertColumn("FirstSubject");
		if (dt.getRowCount() > 0) {
			dt.set(0, "FirstSubject", "<h4>" + dt.getString(0, "Subject") + "</h4>");
		}
		// �����û�Ȩ�޸��û���Ӱ�ť
		dt.insertColumn("Button");
		ForumPriv priv = new ForumPriv(SiteID, ForumID);
		ForumRule rule = new ForumRule(ForumID);
		int flag = ForumUtil.isAdmin(ForumID, SiteID);
		DataTable dtAdmin = new QueryBuilder("select ID,Name,Image,Color from ZCForumGroup where SiteID=?"
				+ " and (SystemName='����' or SystemName='��������' or SystemName='ϵͳ����Ա') order by ID", SiteID)
				.executeDataTable();

		// Object[]faceKeys = ForumFace.MAP_FACE.keyArray();
		for (int i = 0; i < dt.getRowCount(); i++) {
			StringBuffer sb = new StringBuffer();
			// ���������øð����Խ��лظ����鿴������Ȩ�ޡ����������ò��ܻظ����鿴���������û����Ƿ���Ȩ�����лظ�
			if (rule.getRule("ReplyPost") || flag > 0) {
				if (priv.hasPriv("AllowReply")) {
					sb.append("<a href='#;' onclick='quote(" + dt.get(i, "ID")
							+ ")'>����</a>|<a href='javascript:void(0)' onclick=\"fastreply(" + dt.get(i, "Layer") + ",'"
							+ dt.get(i, "AddUser") + "')\">�ظ�</a>");
				}
			}
			// ����û��б༭Ȩ�������༭��ť������û��༭Ȩ�ޣ��鿴��������Ƿ�����༭���˻����������˻�������༭��ť
			if (priv.hasPriv("AllowEdit")) {
				sb.append("|<a href='javascript:void(0)' onclick='isOperaterMember(" + dt.get(i, "ID") + ",1)'>�༭</a>");
			} else if (rule.getRule("EditPost")) {
				if (StringUtil.isNotEmpty(User.getUserName()) && User.getUserName().equals(dt.getString(i, "AddUser"))) {
					sb.append("|<a href='javascript:void(0)' onclick='isOperaterMember(" + dt.get(i, "ID")
							+ ",1)'>�༭</a>");
				}
			}

			if (priv.hasPriv("AllowDel")) {
				sb.append("|<a href='javascript:void(0)' onclick='isOperaterMember(" + dt.get(i, "ID") + ",2)'>ɾ��</a>");
			}
			// ���û��Ƿ��������Ȩ�ޣ���������ѱ���������ʾ"�Ӵ�����"��ť
			if (priv.hasPriv("Hide")) {
				if (StringUtil.isNotEmpty(dt.getString(i, "IsHidden")) && dt.getString(i, "IsHidden").equals("Y")) {
					sb.append("|<a href='javascript:void(0)' onclick='isOperaterMember(" + dt.get(i, "ID")
							+ ",3)'>�������</a>");
				} else {
					sb.append("|<a href='javascript:void(0)' onclick='isOperaterMember(" + dt.get(i, "ID")
							+ ",3)'>����</a>");
				}
			}
			dt.set(i, "Button", sb.toString());

			// ����ͷ��
			if (dt.getString(i, "UseSelfImage").equals("N")) {
				if (StringUtil.isNotEmpty(dt.getString(i, "Image"))) {
					dt.set(i, "HeadImage", dt.getString(i, "Image"));
				}
			}
			// ��������������
			if (StringUtil.isNotEmpty(dt.getString(i, "IsHidden")) && dt.getString(i, "IsHidden").equals("Y")) {
				if (ForumUtil.isAdmin(ForumID, SiteID) > 0) {
					dt.set(i, "Message", "<div class='shield_post'><span>��ʾ��</span>�����ѱ�����,ֻ�а��������Ա�ɼ�</div><br/>"
							+ dt.getString(i, "Message"));
				} else {
					dt.set(i, "Message", "<div class='shield_post'><span>��ʾ��</span>�����ѱ�����</div>");
					dt.set(i, "File", "");
				}
			}
			Loop: for (int j = 0; j < dtAdmin.getRowCount(); j++) {
				if (dt.getLong(i, "AdminID") == dtAdmin.getLong(j, "ID")) {
					if (dtAdmin.get(j, "Name").equals("����")) {
						if (!ForumUtil.isForumAdmin(ForumID, dt.getString(i, "AddUser"))) {
							continue Loop;
						}
					}

					dt.set(i, "GroupName", dtAdmin.getString(j, "Name"));
					dt.set(i, "Color", dtAdmin.getString(j, "Color"));
					if (dt.getString(i, "UseSelfImage").equals("N")) {
						if (StringUtil.isNotEmpty(dt.getString(i, "Image"))) {
							dt.set(i, "HeadImage", dtAdmin.getString(j, "Image"));
						}
					}
					break;
				}
			}
			/*
			 * String message = dt.getString(i,"Message"); for (int j = 0; j <
			 * faceKeys.length; j++) { message =
			 * StringUtil.replaceEx(message,(String) faceKeys[j],
			 * "<img src='Images/face/"
			 * +ForumFace.MAP_FACE.getString(faceKeys[j])+"' >"); } dt.set(i,
			 * "Message", message);
			 */
		}

		return dt;
	}

}
