package com.nswt.cms.pub;

import java.io.File;
import java.util.regex.Matcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

import com.nswt.cms.template.TagParser;
import com.nswt.framework.Config;
import com.nswt.framework.Constant;
import com.nswt.framework.Current;
import com.nswt.framework.User;
import com.nswt.framework.extend.ExtendManager;
import com.nswt.framework.utility.FileUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.schema.ZCSiteSchema;

/**
 * ���������࣬������Include/Top.jsp,Include/Bottom.jsp,Include/Head.jsp<br>
 * ����Ӧ�������ԡ���������̳���̳ǵȶ�̬Ӧ��ͳһͷ����β��
 * 
 * ���� : 2010-1-17 <br>
 * ����: NSWT <br>
 * ���䣺nswt@nswt.com.cn <br>
 */
public class PublicInclude {
	private static Mapx timeMap = new Mapx(3000);// ��໺��3000���ļ�
	private static Mapx fileMap = new Mapx(3000);
	private static Object mutex = new Object();

	/**
	 * ��ȡ�ļ�ȫ·����Ӧ��HTML��ʵ���˻���
	 */
	public static String getHtml(String siteID,String alias, String path) {
		long current = System.currentTimeMillis();
		Long lastTime = (Long) timeMap.get(path);
		String html = null;
		if (lastTime == null) {
			synchronized (mutex) {
				lastTime = new Long(current);
				timeMap.put(path, lastTime);
				if (new File(path).exists()) {
					html = FileUtil.readText(path, Constant.GlobalCharset);
					html = dealResource(siteID,alias, html);
				} else {
					html = "Include file is not exists!";
				}
				fileMap.put(path, html);
			}
		} else if (lastTime.longValue() < current - 3000) {// 3����һ��
			if (new File(path).lastModified() > lastTime.longValue()) {
				synchronized (mutex) {
					lastTime = new Long(current);
					timeMap.put(path, lastTime);
					if (new File(path).exists()) {
						html = FileUtil.readText(path, Constant.GlobalCharset);
						html = dealResource(siteID,alias, html);
					} else {
						html = "Include file is not exists!";
					}
					fileMap.put(path, html);
				}
			} else {
				synchronized (mutex) {
					lastTime = new Long(current);
					html = fileMap.getString(path);
					timeMap.put(path, lastTime);
				}
			}
		} else {
			html = fileMap.getString(path);
		}
		return html;
	}

	/**
	 * ���ػ�Ա�������˵�
	 */
	public static void getMenu(HttpServletRequest request, JspWriter out) throws Exception {
		String cur = request.getParameter("cur");
		if (StringUtil.isEmpty(cur)) {
			cur = "Menu_MI";
		}
		String siteID = getSiteID(request);
		if (StringUtil.isEmpty(siteID)) {
			out.println("URL�б������SiteID����!");
			return;
		}
		StringBuffer sb = new StringBuffer();
		if ("Y".equals(CMSCache.getSite(siteID).getAllowContribute())) {
			sb.append("<ul class='sidemenu'>");
			sb.append("<li id='Menu_WA'><a href='" + Config.getContextPath()
					+ "Member/WriteArticle.jsp?cur=Menu_WA&SiteID=" + siteID + "'>��վ����Ͷ��</a></li>");
			sb.append("<li id='Menu_MA'><a href='" + Config.getContextPath()
					+ "Member/MemberArticles.jsp?cur=Menu_MA&SiteID=" + siteID + "'>��Ͷ�������</a></li>");
			sb.append("</ul>");
		}
		sb.append("<ul class='sidemenu'>");
		ExtendManager.executeAll("Member.Menu", new Object[] { sb, siteID, request });
		sb.append("<li id='Menu_MI'><a href='" + Config.getContextPath() + "Member/MemberInfo.jsp?cur=Menu_MI&SiteID="
				+ siteID + "'>�༭��������</a></li>");
		sb.append("<li id='Menu_PW'><a href='" + Config.getContextPath() + "Member/Password.jsp?cur=Menu_PW&SiteID="
				+ siteID + "'>�޸�����</a></li>");
		sb.append("</ul>");
		sb.append("<hr class='shadowline'/>");
		sb.append("<ul class='sidemenu'>");
		sb.append("<li id='Menu_Logout'><a href='javascript:void(0)' onclick='doLogout();'>�˳�</a></li>");
		sb.append("</ul>");
		sb.append("<script type='text/javascript'>");
		sb.append("document.getElementById('" + cur + "').className='current'");
		sb.append("</script>");
		out.write(sb.toString());
	}

	public static String getSiteID(HttpServletRequest request) {
		String siteID = request.getParameter("SiteID");
		if (StringUtil.isEmpty(siteID)) {
			siteID = request.getParameter("site");
			if (StringUtil.isEmpty(siteID)) {
				siteID = (String) User.getValue("SiteID");
				if (StringUtil.isEmpty(siteID)) {
					siteID = (String) Current.getVariable("SiteID");
				}
			}
		}
		return siteID;
	}

	/**
	 * ���ع���ͷ��HTML
	 */
	public static void getHead(HttpServletRequest request, JspWriter out) throws Exception {
		String siteID = getSiteID(request);
		if (StringUtil.isEmpty(siteID)) {
			out.println("URL�б������SiteID����!");
			return;
		}
		String path = SiteUtil.getAbsolutePath(siteID);
		ZCSiteSchema site = SiteUtil.getSchema(siteID);
		if (site == null) {
			out.println("Site=" + siteID + "��վ��δ�ҵ�!");
			return;
		}
		String template = site.getHeaderTemplate();
		if (StringUtil.isEmpty(template)) {
			return;// �����
		} else {
			if (template.startsWith("/")) {
				template = template.substring(1);
			}
			template = template.substring(template.indexOf('/') + 1);
		}
		path = path + "/include/" + template;
		String html = getHtml(siteID,site.getAlias(), path);
		out.write(html);
	}

	/**
	 * ���ع�������HTML
	 */
	public static void getTop(HttpServletRequest request, JspWriter out) throws Exception {
		String siteID = getSiteID(request);
		if (StringUtil.isEmpty(siteID)) {
			out.println("URL�б������SiteID����!");
			return;
		}
		String path = SiteUtil.getAbsolutePath(siteID);
		ZCSiteSchema site = SiteUtil.getSchema(siteID);
		if (site == null) {
			out.println("Site=" + siteID + "��վ��δ�ҵ�!");
			return;
		}
		String template = site.getTopTemplate();
		if (StringUtil.isEmpty(template)) {
			template = "head.html";
		} else {
			if (template.startsWith("/")) {
				template = template.substring(1);
			}
			template = template.substring(template.indexOf('/') + 1);
		}
		path = path + "/include/" + template;
		String html = getHtml(siteID,site.getAlias(), path);
		out.write(html);
	}

	/**
	 * ��������HTML
	 */
	public static void getBlock(HttpServletRequest request, String fileName, JspWriter out) throws Exception {
		String siteID = getSiteID(request);
		if (StringUtil.isEmpty(siteID)) {
			out.println("URL�б������SiteID����!");
			return;
		}
		String path = SiteUtil.getAbsolutePath(siteID);
		ZCSiteSchema site = SiteUtil.getSchema(siteID);
		if (site == null) {
			out.println("Site=" + siteID + "��վ��δ�ҵ�!");
			return;
		}
		String html = getHtml(siteID,site.getAlias(), path + fileName);
		out.write(html);
	}

	/**
	 * ���ع����ײ�HTML
	 */
	public static void getBottom(HttpServletRequest request, JspWriter out) throws Exception {
		String siteID = getSiteID(request);
		if (StringUtil.isEmpty(siteID)) {
			out.println("URL�б�������ѽ�Ӧ�õ�SiteID����!");
			return;
		}
		String path = SiteUtil.getAbsolutePath(siteID);
		ZCSiteSchema site = SiteUtil.getSchema(siteID);
		if (site == null) {
			out.println("Site=" + siteID + "��Ӧ��δ�ҵ�!");
			return;
		}
		String template = site.getBottomTemplate();
		if (StringUtil.isEmpty(template)) {
			template = "footer.html";
		} else {
			if (template.startsWith("/")) {
				template = template.substring(1);
			}
			template = template.substring(template.indexOf('/') + 1);
		}
		path = path + "/include/" + template;
		String html = getHtml(siteID,site.getAlias(), path);
		out.write(html);
	}

	/**
	 * ��HTMLԴ�����е����·���滻�ɾ���·��
	 */
	public static String dealResource(String siteID,String alias, String content) {
		if (content == null) {
			return "";
		}
		content = StringUtil.replaceAllIgnoreCase(content, "\\$\\{level\\}", "");
		Matcher m = TagParser.resourcePattern1.matcher(content);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;

		while (m.find(lastEndIndex)) {
			String dealPath = dealURL(siteID,alias, m.group(2));
			sb.append(content.substring(lastEndIndex, m.start()));
			sb.append(" " + m.group(1) + "=" + dealPath + m.group(3));
			lastEndIndex = m.end();
		}
		sb.append(content.substring(lastEndIndex));

		content = sb.toString();
		sb = new StringBuffer();
		m = TagParser.resourcePattern2.matcher(content);
		lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			String dealPath = dealURL(siteID,alias, m.group(3));
			sb.append(content.substring(lastEndIndex, m.start()));
			String separator = m.group(2);
			sb.append(" " + m.group(1) + "=" + separator + dealPath + separator);
			lastEndIndex = m.end();
		}
		sb.append(content.substring(lastEndIndex));

		content = sb.toString();
		sb = new StringBuffer();
		m = TagParser.resourcePatternCss.matcher(content);
		lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			String dealPath = dealURL(siteID,alias, m.group(2));
			sb.append(content.substring(lastEndIndex, m.start()));
			sb.append(m.group(1) + "(" + dealPath + ")");
			lastEndIndex = m.end();
		}
		sb.append(content.substring(lastEndIndex));

		content = sb.toString();
		sb = new StringBuffer();
		m = TagParser.resourcePatternCss2.matcher(content);
		lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			String dealPath = dealURL(siteID,alias, m.group(3));
			sb.append(content.substring(lastEndIndex, m.start()));
			sb.append(m.group(1) + "(" + dealPath + ")");
			lastEndIndex = m.end();
		}
		sb.append(content.substring(lastEndIndex));
		return sb.toString();
	}

	/**
	 * ��URL��д�ɾ���·��
	 */
	private static String dealURL(String siteID,String alias, String url) {
		
		String prefix = SiteUtil.getURL(siteID);
		if (url.startsWith("../")) {
			return prefix + url.substring(3);
		} else {
			if (url.indexOf(':') > 0 || url.indexOf(')') > 0 || url.indexOf('<') > 0 || url.indexOf('#') > 0) {
				return url;
			}
			return prefix + url;
		}
	}
}
