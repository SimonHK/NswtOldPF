package com.nswt.cms.template;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.nswt.cms.pub.CatalogUtil;
import com.nswt.cms.pub.SiteUtil;
import com.nswt.framework.Config;
import com.nswt.framework.Constant;
import com.nswt.framework.User;
import com.nswt.framework.data.QueryBuilder;
import com.nswt.framework.data.Transaction;
import com.nswt.framework.utility.Big5Convert;
import com.nswt.framework.utility.Errorx;
import com.nswt.framework.utility.FileUtil;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.platform.pub.NoUtil;
import com.nswt.platform.pub.OrderUtil;
import com.nswt.schema.ZCPageBlockSchema;
import com.nswt.schema.ZCPageBlockSet;
import com.nswt.schema.ZCTemplateBlockRelaSchema;
import com.nswt.schema.ZCTemplateBlockRelaSet;

/**
 * @Author ����
 * @Date 2007-9-4
 * @Mail lanjun@nswt.com
 */
public class TagParser {
	// ��
	private static final Pattern cmsForm = Pattern.compile("<cms:form\\s(.*?)>(.*?)</cms:form>",
			Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	// �б�
	private static final Pattern cmsList = Pattern.compile("<cms:list\\s(.*?)>(.*?)</cms:List>",
			Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	// �ĵ�
	private static final Pattern cmsVar = Pattern.compile("<cms:var\\s(.*?)>(.*?)</cms:var>", Pattern.CASE_INSENSITIVE
			| Pattern.DOTALL);

	// ���б�
	private static final Pattern cmsSubList = Pattern.compile("<cms:sublist\\s(.*?)>(.*?)</cms:subList>",
			Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	// ͼƬ������
	private static final Pattern cmsImagePlayer = Pattern.compile(
			"<cms:imageplayer\\s(.*?)(/>|>(.*?)</cms:ImagePlayer>)", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	// ���
	private static final Pattern cmsAD = Pattern.compile("<cms:ad\\s(.*?)(/>|>(.*?)</cms:ad>)",
			Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	// ����
	private static final Pattern cmsVote = Pattern.compile("<cms:vote\\s(.*?)>(.*?)</cms:vote>",
			Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	// ����
	private static final Pattern cmsComment = Pattern.compile("<cms:comment\\s(.*?)((/>)|(>.*?</cms:comment>))",
			Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	// ����
	private static final Pattern cmsLink = Pattern.compile("<cms:link\\s(.*?)((/>)|(>.*?</cms:link>))",
			Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	// ҳ�����ӣ����統ǰҳ��
	private static final Pattern cmsPage = Pattern.compile("<cms:page\\s(.*?)((/>)|(>.*?</cms:page>))",
			Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	// ��
	private static final Pattern cmsTree = Pattern.compile("<cms:tree\\s(.*?)>(.*?)</cms:tree>",
			Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	// �ļ�����
	private static final Pattern cmsInclude = Pattern.compile("<cms:include\\s(.*?)(/>|(>.*?</cms:include>))",
			Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	public static final Pattern pAttr1 = Pattern.compile("\\s*?(\\w+?)\\s*?=\\s*?(\\\"|\\\')(.*?)\\2",
			Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	public static final Pattern pAttr2 = Pattern.compile("\\s*?(\\w+?)\\s*?=\\s*?([^\\\'\\\"\\s]+)",
			Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	private static final Pattern pField = Pattern.compile("\\$\\{(\\w+?)\\.(\\w+?)(\\|(.*?))??\\}");

	private static final Pattern pageBar = Pattern.compile("\\$\\{PageBar\\}", Pattern.CASE_INSENSITIVE
			| Pattern.DOTALL);
	
	//������ҳ��
	private static final Pattern pageBarBig5 = Pattern.compile("\\$\\{PageBarBig5\\}", Pattern.CASE_INSENSITIVE
			| Pattern.DOTALL);
	
	//Ӣ�İ��ҳ��
	private static final Pattern pageBarEN = Pattern.compile("\\$\\{PageBarEN\\}", Pattern.CASE_INSENSITIVE
			| Pattern.DOTALL);

	private static final Pattern pageBreakBar = Pattern.compile("\\$\\{PageBreakBar\\}", Pattern.CASE_INSENSITIVE
			| Pattern.DOTALL);

	private static final Pattern levelPattern = Pattern.compile("\\$\\{Level\\}", Pattern.CASE_INSENSITIVE
			| Pattern.DOTALL);

	// ƥ������ href=../images/test.gif���ǵȺ�ǰ���пո�����
	public static final Pattern resourcePattern1 = Pattern.compile(
			"\\s(src|href|background|file|virtual|action|name=\"movie\" value)\\s*?=\\s*?([^\\\"\\\'\\s]+?)(\\s|>|/>)",
			Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	// ƥ������ href="../images/test.gif" ���� href="../images/test.gif"
	public static final Pattern resourcePattern2 = Pattern.compile(
			"\\s(src|href|background|file|virtual|action|name=\"movie\" value)\\s*?=\\s*?(\\\"|\\\')(.*?)\\2",
			Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	// ƥ��css�е� url(images/test.gif)
	public static final Pattern resourcePatternCss = Pattern.compile("(:\\s*?url)\\s*?\\(\\s*?([^\\\"\\\'\\s]+)(\\))",
			Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	// ƥ��css�е� url('images/test.gif')��url("images/test.gif")
	public static final Pattern resourcePatternCss2 = Pattern.compile("(:\\s*?url)\\s*?\\(\\s*?(\\\"|\\\')(.*?)\\2(\\))",
			Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	
	private String prefix = "%";

	private String templateFileName;

	private String content;

	private String staticDir;

	private String templateDir;

	private long siteID;

	/**
	 * Ŀ¼�㼶
	 */
	private int dirLevel;

	/**
	 * �Ƿ���ҳ��Ƭ��
	 */
	private boolean isPageBlock;

	/**
	 * Ԥ�����
	 */
	private boolean isPreview;

	private Errorx error = new Errorx();

	private Transaction trans = new Transaction();

	private ZCPageBlockSet blockSet = new ZCPageBlockSet();

	private Mapx pageListPrams = new Mapx();

	private ArrayList fileList = new ArrayList();

	public TagParser() {
		String contextRealPath = Config.getContextRealPath();
		templateDir = contextRealPath + Config.getValue("Statical.TemplateDir").replace('\\', '/');
		staticDir = contextRealPath + Config.getValue("Statical.TargetDir").replace('\\', '/');
		staticDir = staticDir.replaceAll("/+", "/");
	}

	/**
	 * ͼƬ·��
	 */
	private void processPath() {
		String levelString = getLevelStr();
		content = dealResource(content, levelString);
	}

	/**
	 * ����ͼƬ�������ļ�������css���ļ���·��
	 * 
	 * @param content
	 * @return
	 */
	public String dealResource(String content, String levelStr) {
		if (content == null) {
			return "";
		}
		Matcher m = resourcePattern1.matcher(content);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;

		while (m.find(lastEndIndex)) {
			String dealPath = processText(m.group(1), m.group(2), levelStr);
			sb.append(content.substring(lastEndIndex, m.start()));
			sb.append(" " + m.group(1) + "=" + dealPath + m.group(3));
			lastEndIndex = m.end();
		}
		sb.append(content.substring(lastEndIndex));

		content = sb.toString();
		sb = new StringBuffer();
		m = resourcePattern2.matcher(content);
		lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			String dealPath = processText(m.group(1), m.group(3), levelStr);
			sb.append(content.substring(lastEndIndex, m.start()));
			String separator = m.group(2);
			sb.append(" " + m.group(1) + "=" + separator + dealPath + separator);
			lastEndIndex = m.end();
		}
		sb.append(content.substring(lastEndIndex));

		content = sb.toString();
		sb = new StringBuffer();
		m = resourcePatternCss.matcher(content);
		lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			String dealPath = processText(m.group(1), m.group(2), levelStr);
			sb.append(content.substring(lastEndIndex, m.start()));
			sb.append(m.group(1) + "(" + dealPath + ")");
			lastEndIndex = m.end();
		}
		sb.append(content.substring(lastEndIndex));

		content = sb.toString();
		sb = new StringBuffer();
		m = resourcePatternCss2.matcher(content);
		lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			String dealPath = processText(m.group(1), m.group(3), levelStr);
			sb.append(content.substring(lastEndIndex, m.start()));
			sb.append(m.group(1) + "(" + dealPath + ")");
			lastEndIndex = m.end();
		}
		sb.append(content.substring(lastEndIndex));

		// ���ļ��ǰ����ļ���Դ�ļ�ʱ��������${level},����������header_0.htmlʱ����${level}
		if (!isPageBlock) {
			content = sb.toString();
			sb = new StringBuffer();
			lastEndIndex = 0;
			m = levelPattern.matcher(content);
			while (m.find(lastEndIndex)) {
				sb.append(content.substring(lastEndIndex, m.start()));
				sb.append(levelStr);
				lastEndIndex = m.end();
			}
			sb.append(content.substring(lastEndIndex));
		}

		return sb.toString();
	}

	/**
	 * ���������ļ������·��
	 * 
	 * @param srcType
	 *            �ļ�����
	 * @param resourcePath
	 *            Դ�ļ�·��
	 * @param levelStr
	 *            �㼶
	 * @return
	 */
	private String processText(String srcType, String resourcePath, String levelStr) {
		String strPath = "";
		resourcePath = resourcePath.trim();

		if (resourcePath.startsWith("#")) {
			strPath = resourcePath;
		} else if (resourcePath.toLowerCase().startsWith("/") || resourcePath.toLowerCase().indexOf("${") != -1
				|| resourcePath.toLowerCase().indexOf("<"+prefix+"") != -1 || resourcePath.toLowerCase().indexOf("+") != -1
				|| resourcePath.toLowerCase().startsWith("<cms:") || resourcePath.toLowerCase().startsWith("http")
				|| resourcePath.toLowerCase().startsWith("https") || resourcePath.toLowerCase().startsWith("ftp")
				|| resourcePath.toLowerCase().startsWith("javascript")
				|| resourcePath.toLowerCase().startsWith("mailto")) {
			strPath = resourcePath;
		} else {
			int upLevelIndex = resourcePath.lastIndexOf("../");
			if (upLevelIndex != -1) {
				resourcePath = resourcePath.substring(upLevelIndex + 3);
			}
			if (resourcePath.indexOf("//") == -1) {
				// �ļ���
				String fileName = resourcePath.indexOf("/") == -1 ? resourcePath : resourcePath.substring(resourcePath
						.lastIndexOf("/") + 1);

				if (resourcePath.length() - 1 == resourcePath.lastIndexOf("/")) {
					fileName = resourcePath;
				}
				// ��չ��
				String ext = resourcePath.indexOf(".") == -1 ? "" : resourcePath.substring(resourcePath
						.lastIndexOf(".") + 1);
				ext = ext.toLowerCase().trim();

				if ("file".equalsIgnoreCase(srcType) || "virtual".equalsIgnoreCase(srcType)) {
					strPath = levelStr + resourcePath;
				} else if ("url".equalsIgnoreCase(srcType) && "#default#homepage".equalsIgnoreCase(fileName)) {
					strPath = resourcePath;
				} else if ("".equalsIgnoreCase(ext)) {
					strPath = levelStr + resourcePath;
				} else if ("window.external.AddFavorite(location.href,document.title);".equals(fileName)) {
					strPath = resourcePath;
				} else {
					strPath = levelStr + resourcePath;
				}
			} else if (resourcePath.indexOf("//") > 0 && resourcePath.indexOf("#default#homepage") != -1) {
				strPath = resourcePath;
			}
		}
		// LogUtil.getLogger().info("ԭ·����"+resourcePath+",�����·����"+strPath);
		return strPath;
	}

	private void addHeader() {
		content = "<z:config language=\"java\" prefix=\""+prefix+"\"/>" + content;
	}

	/**
	 * ��������<cms:list item="aritlce" count="10"></cms:list>
	 * 
	 */
	private void parseList() {
		Matcher m = cmsList.matcher(content);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		int varIndex = 0;
		while (m.find(lastEndIndex)) {
			sb.append(content.substring(lastEndIndex, m.start()));
			lastEndIndex = m.end();
			Mapx map = getAttrMap(m.group(1));
			String listContent = m.group(2);

			String item = (String) map.get("item");
			if(StringUtil.isEmpty(item)){
				sb.append("ģ�����cms:listδ����item����");
				varIndex++;
				continue;
			}else{
				item = item.toLowerCase();
			}
			
			String type = (String) map.get("type");
			String page = (String) map.get("page");
			String countStr = (String) map.get("count");
			String begin = (String) map.get("begin");
			String pagesizeStr = (String) map.get("pagesize");
			String condition = (String) map.get("condition");
			String displayLevel = (String) map.get("level");
			String var = (String) map.get("var");
			String attribute = (String) map.get("hasattribute");
			if (StringUtil.isNotEmpty(displayLevel)) {
				displayLevel = displayLevel.toLowerCase();
			} else {
				displayLevel = "current";
			}
			map.put("level", displayLevel);

			if (StringUtil.isEmpty(var)) {
				var = item + "_" + varIndex;
				Pattern p = Pattern.compile("(" + item + ")\\.", Pattern.CASE_INSENSITIVE);
				Matcher matcher = p.matcher(listContent);
				listContent = matcher.replaceAll(item + "_" + varIndex + ".");
			}

			listContent = parseSubList(listContent, System.currentTimeMillis(), item, var);

			int count = 0;
			if (StringUtil.isNotEmpty(countStr) && StringUtil.isDigit(countStr)) {
				count = Integer.parseInt(countStr);
			} else {
				count = 50;
			}
			map.put("count", count + "");

			if (StringUtil.isEmpty(pagesizeStr) || !StringUtil.isDigit(pagesizeStr)) {
				pagesizeStr = "0";
			}
			map.put("pagesize", pagesizeStr);

			String beginIndex = null;
			if (StringUtil.isNotEmpty(begin) && StringUtil.isDigit(begin)) {
				beginIndex = begin;
				count += Integer.parseInt(begin);
			} else {
				beginIndex = "0";
			}

			if (page != null && "true".equalsIgnoreCase(page)) {
				pageListPrams = map;
				String jspContent = "<"+prefix+"" + "DataTable dt_" + varIndex + " = TemplateData.getListTable();"
						+ "for(int i=" + beginIndex + ";i<dt_" + varIndex + ".getRowCount();i++){\n" + "DataRow " + var
						+ "  = dt_" + varIndex + ".getDataRow(i);\n" + ""+prefix+">\n";
				jspContent += listContent;
				jspContent += "<"+prefix+"}"+prefix+">";
				sb.append(jspContent);
			} else {
				if ("article".equalsIgnoreCase(item) || "image".equalsIgnoreCase(item)
						|| "video".equalsIgnoreCase(item) || "audio".equalsIgnoreCase(item)
						|| "attachment".equalsIgnoreCase(item) || "goods".equalsIgnoreCase(item)) { // �ĵ��б�
					String catalog = (String) map.get("name");
					String id = (String) map.get("id");
					if (StringUtil.isNotEmpty(id)) {
						catalog = id;
					}
					String parent = (String) map.get("parent");

					// ������ʽд��
					if (StringUtil.isEmpty(catalog) && StringUtil.isNotEmpty(parent)) {
						catalog = parent;
					}

					// ����Name�еı������ʽ
					String catalogStr = null;
					if (StringUtil.isNotEmpty(catalog) && catalog.indexOf("${") != -1) {
						catalogStr = parsePlaceHolderStr(catalog);
					} else {
						catalogStr = "\"" + catalog + "\"";
					}

					String conditionStr = null;
					if (StringUtil.isNotEmpty(condition) && condition.indexOf("${") != -1) {
						conditionStr = parsePlaceHolderStr(condition);
					} else {
						conditionStr = "\"" + condition + "\"";
					}

					String jspContent = "<!--ѭ����" + item + " count��" + count + "-->\n" + "<"+prefix+" DataTable dt_" + varIndex
							+ " = TemplateData.getDocList(\"" + item + "\"," + catalogStr + ",\"" + displayLevel
							+ "\",\"" + attribute + "\",\"" + type + "\",\"" + count + "\"," + conditionStr + ");\n"
							+ "if(dt_" + varIndex + "==null){write(\"û���ҵ���Ŀ��"
							+ (String) map.get("name") + "\");}else{\n" + "for(int i=" + beginIndex + ";i<dt_"
							+ varIndex + ".getRowCount();i++){\n" + "DataRow " + var + "  = dt_" + varIndex
							+ ".getDataRow(i);\n" + ""+prefix+">\n";

					jspContent += listContent;
					jspContent += "<"+prefix+"}}"+prefix+">";

					// ����ҳ��Ƭ��
					if (!isPageBlock && !isPreview) {
						if (catalog == null || "relate".equalsIgnoreCase(type)) { // û��ָ����Ŀ
							// ����
							// Ϊ����ĵ�
							sb.append(jspContent);
						} else {
							sb.append(getListBlock(item, count, type, catalog, parent, jspContent));
						}
					} else {
						sb.append(jspContent);
					}

				} else if ("friendlink".equalsIgnoreCase(item)) { // ��������
					// groupΪ���ӷ���
					String group = map.getString("group");
					if (group == null) {
						group = map.getString("name");
					}

					if (group != null) {
						group = group.toLowerCase();
					}

					String jspContent = "<!--ѭ����" + item + " count��" + count + "-->\n" + "<"+prefix+" DataTable dt_" + varIndex
							+ " = TemplateData.getFriendLinkList(\"" + item + "\",\"" + group + "\",\"" + count
							+ "\",\"" + condition + "\");\n" + "if(dt_" + varIndex + "==null){write(\"û���ҵ��������ӣ�"
							+ (String) map.get("name") + "\");}else{\n" + "for(int i=" + beginIndex + ";i<dt_"
							+ varIndex + ".getRowCount();i++){\n" + "DataRow " + var + "  = dt_" + varIndex
							+ ".getDataRow(i);\n" + ""+prefix+">\n";

					jspContent += listContent;
					jspContent += "<"+prefix+"}}"+prefix+">";

					if (!isPageBlock && this.dirLevel == 0 && !isPreview) {
						String targetFileName = getLinkBlock(item, group, count, jspContent);
						String ext = SiteUtil.getExtensionType(siteID);
						if("jsp".equalsIgnoreCase(ext)){
							sb.append("<%@include file=\"" + getLevelStr() + targetFileName + "\"%>");
						}else{
							sb.append("<!--#include virtual=\"" + getLevelStr() + targetFileName + "\"-->");
						}
					} else {
						sb.append(jspContent);
					}
				} else if ("block".equalsIgnoreCase(item)) { // blockҳ������
					String jspContent = "<!--ѭ����" + item + " count��" + count + "-->\n" + "<"+prefix+" DataTable dt_" + varIndex
							+ " = TemplateData.getBlockList(\"" + item + "\",\"" + count + "\",\"" + condition
							+ "\");\n" + "for(int i=" + beginIndex + ";i<dt_" + varIndex + ".getRowCount();i++){"
							+ "DataRow " + var + "  = dt_" + varIndex + ".getDataRow(i);" + ""+prefix+">";

					jspContent += listContent;
					jspContent += "<"+prefix+"}"+prefix+">";
					sb.append(jspContent);
				} else if ("catalog".equalsIgnoreCase(item)) {
					String parent = (String) map.get("parent");
					String parentID = (String) map.get("id");
					String catalog = (String) map.get("name");
					if (StringUtil.isNotEmpty(parentID)) {
						parent = parentID;
					}

					// ������ʽд��
					if (StringUtil.isEmpty(catalog) && StringUtil.isNotEmpty(parent)) {
						catalog = parent;
					}

					// �����������ʽ
					String catalogStr = null;
					if (StringUtil.isNotEmpty(catalog) && catalog.indexOf("${") != -1) {
						catalogStr = parsePlaceHolder(catalog);
					} else {
						catalogStr = "\"" + catalog + "\"";
					}

					if (StringUtil.isEmpty(displayLevel)) {
						displayLevel = "\"child\"";
					} else {
						displayLevel = "\"" + displayLevel + "\"";
					}

					String jspContent = "<!--ѭ����" + item + " count��" + count + "-->\n" + "<"+prefix+" DataTable dt_" + varIndex
							+ " = TemplateData.getCatalogList(\"" + item + "\",\"" + type + "\"," + catalogStr + ","
							+ displayLevel + ",\"" + count + "\",\"" + condition + "\");\n" + "if(dt_" + varIndex
							+ "==null){write(\"û���ҵ���Ŀ�б�\");}else{\n" + "for(int i=" + beginIndex + ";i<dt_" + varIndex
							+ ".getRowCount();i++){" + "DataRow " + var + "  = dt_" + varIndex + ".getDataRow(i);\n"
							+ ""+prefix+">";
					jspContent += listContent;
					jspContent += "<"+prefix+"}}"+prefix+">";

					sb.append(jspContent);
				} else if ("customtable".equalsIgnoreCase(item)) {
					String tableName = (String) map.get("name");
					String jspContent = "<!--ѭ����" + item + " count��" + count + "-->\n" + "<"+prefix+" DataTable dt_" + varIndex
							+ " = TemplateData.getCustomData(\"" + tableName + "\",\"" + count + "\",\"" + condition
							+ "\");\n" + "if(dt_" + varIndex + "==null){write(\"û���ҵ��Զ�������" + tableName
							+ "��\");}else{\n" + "for(int i=" + beginIndex + ";i<dt_" + varIndex
							+ ".getRowCount();i++){" + "DataRow " + var + "  = dt_" + varIndex + ".getDataRow(i);\n"
							+ ""+prefix+">";
					jspContent += listContent;
					jspContent += "<"+prefix+"}}"+prefix+">";

					sb.append(jspContent);
				} else if ("keyword".equalsIgnoreCase(item)) {
					String jspContent = "<!--ѭ����" + item + " count��" + count + "-->\n" + "<"+prefix+" DataTable dt_" + varIndex
							+ " = TemplateData.getKeywords(\"" + type + "\",\"" + count + "\"" + ");\n" + "if(dt_"
							+ varIndex + "==null){write(\"" + "\");}else{\n" + "for(int i=" + beginIndex + ";i<dt_"
							+ varIndex + ".getRowCount();i++){" + "DataRow " + var + "  = dt_" + varIndex
							+ ".getDataRow(i);\n" + ""+prefix+">";
					jspContent += listContent;
					jspContent += "<"+prefix+"}}"+prefix+">";

					sb.append(jspContent);
				} else if ("magazine".equalsIgnoreCase(item)) {
					String name = (String) map.get("name");
					if (StringUtil.isEmpty(displayLevel)) {
						displayLevel = "\"child\"";
					} else {
						displayLevel = "\"" + displayLevel + "\"";
					}

					String jspContent = "<!--ѭ����" + item + " count��" + count + "-->\n" + "<"+prefix+" DataTable dt_" + varIndex
							+ " = TemplateData.getMagazineList(\"" + item + "\",\"" + name + "\",\"" + count + "\",\""
							+ condition + "\");\n" + "if(dt_" + varIndex + "==null){write(\"û���ҵ��ڿ��б�\");}else{\n"
							+ "for(int i=" + beginIndex + ";i<dt_" + varIndex + ".getRowCount();i++){" + "DataRow "
							+ var + "  = dt_" + varIndex + ".getDataRow(i);\n" + ""+prefix+">";
					jspContent += listContent;
					jspContent += "<"+prefix+"}}"+prefix+">";

					sb.append(jspContent);
				} else if ("magazineissue".equalsIgnoreCase(item)) {
					String name = (String) map.get("name");
					if (StringUtil.isEmpty(displayLevel)) {
						displayLevel = "\"child\"";
					} else {
						displayLevel = "\"" + displayLevel + "\"";
					}

					String jspContent = "<!--ѭ����" + item + " count��" + count + "-->\n" + "<"+prefix+" DataTable dt_" + varIndex
							+ " = TemplateData.getMagazineIssueList(\"" + item + "\",\"" + name + "\",\"" + type
							+ "\",\"" + count + "\",\"" + condition + "\");\n" + "if(dt_" + varIndex
							+ "==null){write(\"û���ҵ��ڿ������б�\");}else{\n" + "for(int i=" + beginIndex + ";i<dt_"
							+ varIndex + ".getRowCount();i++){" + "DataRow " + var + "  = dt_" + varIndex
							+ ".getDataRow(i);\n" + ""+prefix+">";
					jspContent += listContent;
					jspContent += "<"+prefix+"}}"+prefix+">";

					sb.append(jspContent);
				} else if ("page".equalsIgnoreCase(item)) {
					String jspContent = "<!--ѭ����" + item + " count��" + count + "-->\n" + "<"+prefix+" DataTable dt_" + varIndex
							+ " = TemplateData.getArticlePages();\n" + "if(dt_" + varIndex
							+ "==null){write(\"<cms:list item='page\'>����ǩֻ����������ϸҳ��ʹ�á�" + "��\");}else{\n" + "for(int i="
							+ beginIndex + ";i<dt_" + varIndex + ".getRowCount();i++){" + "DataRow " + var + "  = dt_"
							+ varIndex + ".getDataRow(i);\n" + ""+prefix+">";
					jspContent += listContent;
					jspContent += "<"+prefix+"}}"+prefix+">";

					sb.append(jspContent);
				}
			}
			varIndex++;
		}
		sb.append(content.substring(lastEndIndex));
		content = sb.toString();

		if ("true".equalsIgnoreCase(pageListPrams.getString("page"))) {
			parsePageBar();
			parsePageBarBig5();
			parsePageBarEN();
		}
	}

	private String getLinkBlock(String item, String group, int count, String jspContent) {
		String catalogID = "0";

		String cacheDir = templateDir + "/" + SiteUtil.getAlias(siteID);
		cacheDir = cacheDir.replace('\\', '/').replaceAll("//", "/");

		File cacheFileDir = new File(cacheDir + "/cache/template");
		if (!cacheFileDir.exists()) {
			cacheFileDir.mkdirs();
		}

		String groupAlias = StringUtil.getChineseFirstAlpha(group);
		String code = catalogID + "_" + item + "_" + count + "_" + groupAlias + "_" + dirLevel;
		String cacheFileName = "/cache/template/block_" + code + ".html";
		String targetFileName = "cache/page/block_" + code + ".html";

		FileUtil.writeText(cacheDir + cacheFileName, jspContent);

		ZCPageBlockSchema block = new ZCPageBlockSchema();
		long blockID = NoUtil.getMaxID("PageBlockID");
		block.setID(blockID);
		block.setSiteID(siteID);
		// block.setCatalogID(catalogID);
		block.setName(code);
		block.setCode(code);
		block.setFileName(targetFileName);
		block.setTemplate(cacheFileName);
		block.setSortField("PublishDate");

		block.setType(4);
		block.setContent("");
		block.setAddTime(new Date());
		if (User.getCurrent() != null) {
			block.setAddUser(User.getUserName());
		} else {
			block.setAddUser("admin");
		}

		blockSet.add(block);
		return targetFileName;
	}

	private String getListBlock(String item, int count, String type, String catalog, String parent, String jspContent) {
		String pageString = null;
		String key = null;
		String catalogID = null;
		if (StringUtil.isNotEmpty(catalog)) {
			if (StringUtil.isDigit(catalog)) {
				catalogID = catalog;
			} else {
				if (StringUtil.isNotEmpty(parent)) {
					catalogID = CatalogUtil.getIDByName(siteID, parent, catalog);
				} else {
					try {
						if (catalog.indexOf(",") != -1) {
							catalogID = CatalogUtil.getIDsByName(siteID + "", catalog);
						} else if (catalog.indexOf("/") != -1) {
							catalogID = CatalogUtil.getIDByNames(siteID + "", catalog);
						} else {
							catalogID = CatalogUtil.getIDByName(siteID, catalog);
						}
					} catch (Exception e) {
						LogUtil.warn(e.getMessage());
					}
				}
			}
		}

		if (catalogID != null) {
			String cacheDir = templateDir + "/" + SiteUtil.getAlias(siteID);
			cacheDir = cacheDir.replace('\\', '/').replaceAll("//", "/");

			File cacheFileDir = new File(cacheDir + "/cache/template");
			if (!cacheFileDir.exists()) {
				cacheFileDir.mkdirs();
			}
			if (StringUtil.isEmpty(type) || "null".equals(type)) {
				type = "default";
			}
			key = siteID + "_" + catalogID;
			String hex = StringUtil.md5Hex(jspContent).substring(0, 8);
			String code = catalogID + "_" + item + "_" + type + "_" + count + "_" + hex + "_" + dirLevel;
			String cacheFileName = "/cache/template/block_" + code + ".html";
			String targetFileName = "cache/page/block_" + code + ".html";

			FileUtil.writeText(cacheDir + cacheFileName, jspContent);

			if (catalogID.indexOf(",") != -1) {
				String[] catalogIDs = catalogID.split(",");
				for (int m = 0; m < catalogIDs.length; m++) {
					ZCPageBlockSchema block = new ZCPageBlockSchema();
					long blockID = NoUtil.getMaxID("PageBlockID");
					block.setID(blockID);
					block.setSiteID(siteID);
					block.setCatalogID(catalogIDs[m]);
					block.setName(key);
					block.setCode(code);
					block.setFileName(targetFileName);
					block.setTemplate(cacheFileName);
					block.setSortField("PublishDate");

					block.setType(4);
					block.setContent("");
					block.setAddTime(new Date());
					if (User.getCurrent() != null) {
						block.setAddUser(User.getUserName());
					} else {
						block.setAddUser("admin");
					}
					blockSet.add(block);
				}
			} else {
				key = siteID + "_" + catalog;
				ZCPageBlockSchema block = new ZCPageBlockSchema();
				long blockID = NoUtil.getMaxID("PageBlockID");
				block.setID(blockID);
				block.setSiteID(siteID);
				block.setCatalogID(catalogID);
				block.setName(key);
				block.setCode(code);
				block.setFileName(targetFileName);
				block.setTemplate(cacheFileName);
				block.setSortField("PublishDate");

				block.setType(4);
				block.setContent("");
				block.setAddTime(new Date());
				if (User.getCurrent() != null) {
					block.setAddUser(User.getUserName());
				} else {
					block.setAddUser("admin");
				}
				blockSet.add(block);
			}
			String ext = SiteUtil.getExtensionType(siteID);
			if("jsp".equalsIgnoreCase(ext)){
				pageString = "<%@include file=\"" + getLevelStr() + targetFileName + "\"%>";
			}else{
				pageString ="<!--#include virtual=\"" + getLevelStr() + targetFileName + "\"-->";
			}
		} else {
			pageString = "û���ҵ���Ŀ��" + catalog;
		}
		return pageString;
	}

	// �����б��ҳ
	private void parsePageBar() {
		Matcher m = pageBar.matcher(content);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		int count = 0;
		while (m.find(lastEndIndex)) {
			sb.append(content.substring(lastEndIndex, m.start()));
			String jspContent = "<"+prefix+"write(TemplateData.getPageBar(" + count + "));"+prefix+">";
			sb.append(jspContent);
			lastEndIndex = m.end();
			count++;
		}
		sb.append(content.substring(lastEndIndex));
		content = sb.toString();
	}
	
	private void parsePageBarBig5() {
		Matcher m = pageBarBig5.matcher(content);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		int count = 0;
		while (m.find(lastEndIndex)) {
			sb.append(content.substring(lastEndIndex, m.start()));
			String jspContent = "<"+prefix+"write(TemplateData.getPageBarBig5(" + count + "));"+prefix+">";
			sb.append(jspContent);
			lastEndIndex = m.end();
			count++;
		}
		sb.append(content.substring(lastEndIndex));
		content = sb.toString();
	}
	
	private void parsePageBarEN() {
		Matcher m = pageBarEN.matcher(content);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		int count = 0;
		while (m.find(lastEndIndex)) {
			sb.append(content.substring(lastEndIndex, m.start()));
			String jspContent = "<"+prefix+"write(TemplateData.getPageBarEN(" + count + "));"+prefix+">";
			sb.append(jspContent);
			lastEndIndex = m.end();
			count++;
		}
		sb.append(content.substring(lastEndIndex));
		content = sb.toString();
	}
	

	// �������ݷ�ҳ
	private void parsePageBreakBar() {
		Matcher m = pageBreakBar.matcher(content);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		int count = 0;
		while (m.find(lastEndIndex)) {
			sb.append(content.substring(lastEndIndex, m.start()));
			String jspContent = "<"+prefix+"write(TemplateData.getPageBreakBar(" + count + "));"+prefix+">";
			sb.append(jspContent);
			lastEndIndex = m.end();
			count++;
		}
		sb.append(content.substring(lastEndIndex));
		content = sb.toString();
	}

	public String parsePlaceHolder(String content) {
		StringBuffer sb = new StringBuffer();
		Matcher m = pField.matcher(content);
		int lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			sb.append(content.substring(lastEndIndex, m.start()));
			String table = m.group(1);
			String field = m.group(2);

			if (table != null) {
				table = table.toLowerCase();
			}
			if (field != null) {
				field = field.toLowerCase();
			}

			sb.append(table + ".getString(\"" + field + "\")");
			lastEndIndex = m.end();
		}
		sb.append(content.substring(lastEndIndex));
		return sb.toString();
	}

	public String parsePlaceHolderStr(String content) {
		StringBuffer sb = new StringBuffer();
		sb.append("\"");
		Matcher m = pField.matcher(content);
		int lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			sb.append(content.substring(lastEndIndex, m.start()));
			String table = m.group(1);
			String field = m.group(2);

			if (table != null) {
				table = table.toLowerCase();
			}
			if (field != null) {
				field = field.toLowerCase();
			}

			sb.append("\"+" + table + ".getString(\"" + field + "\")+\"");
			lastEndIndex = m.end();
		}
		sb.append(content.substring(lastEndIndex) + "\"");

		content = sb.toString();
		sb = new StringBuffer();
		m = Constant.PatternField.matcher(content);
		lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			sb.append(content.substring(lastEndIndex, m.start()));
			String field = m.group(1);

			sb.append("\"+" + field + "+\"");
			lastEndIndex = m.end();
		}
		sb.append(content.substring(lastEndIndex));

		return sb.toString();
	}

	// �������б�
	private String parseSubList(String subContent, long varIndex, String parentItem, String parentVar) {
		Matcher m = cmsSubList.matcher(subContent);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			sb.append(subContent.substring(lastEndIndex, m.start()));
			lastEndIndex = m.end();
			Mapx map = getAttrMap(m.group(1));
			String listContent = m.group(2);

			String item = ((String) map.get("item")).toLowerCase();
			String type = (String) map.get("type");
			String countStr = (String) map.get("count");
			String condition = (String) map.get("condition");
			String begin = (String) map.get("begin");
			String attribute = (String) map.get("hasattribute");
			// todo �޸���ѭ����var���
			// String subVar = (String) map.get("var");

			String relaColumn = ((String) map.get("RelaColumn"));
			String subNewVar = "";
			if (parentItem.equals(item)) {
				parentItem = parentVar;
				subNewVar = parentItem + "_" + varIndex;
			} else {
				subNewVar = item + "_" + varIndex;
			}

			// �滻�ϲ�ѭ�������ı���
			Pattern p = Pattern.compile("(" + parentItem + ")\\.", Pattern.CASE_INSENSITIVE);
			Matcher matcher = p.matcher(listContent);
			listContent = matcher.replaceAll(parentItem + "_" + varIndex + ".");

			// �滻����ѭ���ı���
			p = Pattern.compile("(" + item + ")\\.", Pattern.CASE_INSENSITIVE);
			matcher = p.matcher(listContent);
			listContent = matcher.replaceAll(item + "_" + varIndex + ".");

			int count = 0;
			if (StringUtil.isNotEmpty(countStr) && StringUtil.isDigit(countStr)) {
				count = Integer.parseInt(countStr);
			} else {
				count = 20;
			}

			String beginIndex = null;
			if (StringUtil.isNotEmpty(begin) && StringUtil.isDigit(begin)) {
				beginIndex = begin;
				count += Integer.parseInt(begin);
			} else {
				beginIndex = "0";
			}

			if ("article".equalsIgnoreCase(item) || "image".equalsIgnoreCase(item) || "video".equalsIgnoreCase(item)
					|| "audio".equalsIgnoreCase(item) || "attachment".equalsIgnoreCase(item)) { // �ĵ��б�
				String catalog = (String) map.get("catalog");
				String level = (String) map.get("level");
				if (StringUtil.isNotEmpty(catalog) && catalog.indexOf("${") != -1) {
					catalog = parsePlaceHolder(catalog);
				} else {
					catalog = "\"" + catalog + "\"";
				}

				String catalogID = null;
				if (relaColumn != null) {
					catalogID = parentVar + ".getString(\"" + relaColumn + "\")";
				} else {
					catalogID = parentVar + ".getString(\"ID\")";
				}

				if (StringUtil.isEmpty(level)) {
					level = "\"all\"";
				} else {
					level = "\"" + level + "\"";
				}

				String jspContent = "<!--ѭ����" + item + " count��" + count + "-->\n" + "<"+prefix+" DataTable dt_" + varIndex
						+ " = TemplateData.getDocList(\"" + item + "\"," + catalogID + "," + level + ",\"" + attribute
						+ "\",\"" + type + "\",\"" + count + "\",\"" + condition + "\");\n" + "if(dt_" + varIndex
						+ "==null){write(\"û���ҵ���Ŀ\");}else{\n" + "for(int i=" + beginIndex + ";i<dt_" + varIndex
						+ ".getRowCount();i++){\n" + "DataRow " + subNewVar + "  = dt_" + varIndex
						+ ".getDataRow(i);\n" + ""+prefix+">\n";

				jspContent += listContent;
				jspContent += "<"+prefix+"}}"+prefix+">";
				sb.append(jspContent);
			} else if ("voteitem".equalsIgnoreCase(item)) {
				String parentID = parentVar + ".getString(\"ID\")";
				String inputType = parentVar + ".getString(\"type\")";
				String jspContent = "<!--ѭ����" + item + " count��" + count + "-->\n" + "<"+prefix+" DataTable dt_" + varIndex
						+ " = TemplateData.getVoteItems(" + parentID + "," + inputType + "," + "" + count + ");\n"
						+ "for(int j=0;j<dt_" + varIndex + ".getRowCount();j++){" + "DataRow " + subNewVar + "  = dt_"
						+ varIndex + ".getDataRow(j);\n" + ""+prefix+">";
				jspContent += listContent;
				jspContent += "<"+prefix+"}"+prefix+">";

				sb.append(jspContent);
			} else {
				String parentID = "\"0\"";
				if (relaColumn != null) {
					parentID = parentVar + ".getString(\"" + relaColumn + "\")";
				} else {
					parentID = parentVar + ".getString(\"ID\")";
				}

				String jspContent = "<!--ѭ����" + item + " count��" + count + "-->\n" + "<"+prefix+" DataTable dt_" + varIndex
						+ " = TemplateData.getCatalogList(\"" + item + "\",\"" + type + "\"," + parentID
						+ ",\"child\"," + "\"" + count + "\"," + "null);\n" + "for(int j=0;j<dt_" + varIndex
						+ ".getRowCount();j++){" + "DataRow " + subNewVar + "  = dt_" + varIndex + ".getDataRow(j);\n"
						+ ""+prefix+">";
				jspContent += listContent;
				jspContent += "<"+prefix+"}"+prefix+">";

				sb.append(jspContent);
			}

			varIndex++;
		}
		sb.append(subContent.substring(lastEndIndex));
		return sb.toString();
	}

	private void parseImagePlayer() {
		Matcher m = cmsImagePlayer.matcher(content);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			sb.append(content.substring(lastEndIndex, m.start()));
			lastEndIndex = m.end();
			Mapx map = getAttrMap(m.group(1));

			String name = (String) map.get("name");
			if (StringUtil.isEmpty(name)) {
				name = (String) map.get("code");
			}
			String width = (String) map.get("width");
			String height = (String) map.get("height");
			String count = (String) map.get("count");
			String cw = (String) map.get("charwidth");

			String jspContent = "" + "<"+prefix+"write(TemplateData.getImagePlayer(\"" + name + "\",\"" + width + "\",\""
					+ height + "\",\"" + count + "\",\"" + cw + "\"));"+prefix+">";
			sb.append(jspContent);
		}
		sb.append(content.substring(lastEndIndex));
		content = sb.toString();
	}

	private void parseTree() {
		Matcher m = cmsTree.matcher(content);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			sb.append(content.substring(lastEndIndex, m.start()));
			lastEndIndex = m.end();
			Mapx map = getAttrMap(m.group(1));
			String id = (String) map.get("id");
			String method = (String) map.get("method");
			String style = (String) map.get("style");
			String levelStr = (String) map.get("level");
			String tagBody = m.group(2);
			tagBody = StringUtil.javaEncode(tagBody);

			String jspContent = "" + "<"+prefix+"write(TemplateData.getTree(\"" + id + "\",\"" + method + "\",\"" + tagBody
					+ "\", \"" + style + "\",\"" + levelStr + "\"));"+prefix+">";
			sb.append(jspContent);
		}
		sb.append(content.substring(lastEndIndex));
		content = sb.toString();
	}

	/**
	 * ��������<cms:AD code=""></cms:AD>�Ĺ���ǩ
	 * 
	 */

	private void parseAD() {
		Matcher m = cmsAD.matcher(content);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			sb.append(content.substring(lastEndIndex, m.start()));
			lastEndIndex = m.end();
			Mapx map = getAttrMap(m.group(1));
			String name = (String) map.get("name");
			String type = (String) map.get("type");
			String jspContent = "" + "<"+prefix+"write(TemplateData.getAD(\"" + name + "\",\""+type+"\"));"+prefix+">";
			sb.append(jspContent);
		}
		sb.append(content.substring(lastEndIndex));
		content = sb.toString();
	}

	/**
	 * ��������<cms:form code=""></cms:form>�Ĺ���ǩ
	 * 
	 */
	private void parseForm() {
		Matcher m = cmsForm.matcher(content);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			sb.append(content.substring(lastEndIndex, m.start()));
			lastEndIndex = m.end();
			Mapx map = getAttrMap(m.group(1));
			String code = (String) map.get("code");
			String jspContent = "" + "<"+prefix+"write(TemplateData.getForm(\"" + code + "\"));"+prefix+">";
			sb.append(jspContent);
		}
		sb.append(content.substring(lastEndIndex));
		content = sb.toString();
	}

	private void parseVote() {
		Matcher m = cmsVote.matcher(content);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			sb.append(content.substring(lastEndIndex, m.start()));
			lastEndIndex = m.end();
			Mapx map = getAttrMap(m.group(1));
			String name = (String) map.get("name");
			String id = (String) map.get("id");
			String voteBodyContent = m.group(2);
			// �Զ��壬�ɶ�����ʽ
			if (voteBodyContent.indexOf("cms:list") > 0) {
				String voteVar = "vote_" + lastEndIndex;
				String jspContent = "" + "<"+prefix+"DataRow " + voteVar + " = TemplateData.getVoteData(\"" + name + "\",\""
						+ id + "\");\n" + "if(" + voteVar + "==null){\n write(\"û���ҵ���Ӧ��ͶƱ����:" + name
						+ "\");\n}else{\n"+prefix+">";
				sb.append(jspContent);

				voteBodyContent = "<div id='vote_${vote.id}' class='votecontainer' style='text-align:left'><form id='voteForm_${vote.id}' name='voteForm_${vote.id}' action='"
						+ Config.getValue("ServicesContext")
						+ "/VoteResult.jsp' method='post' target='_blank'><input type='hidden' id='ID' name='ID' value='${vote.id}'><input type='hidden' id='VoteFlag' name='VoteFlag' value='Y'>"
						+ voteBodyContent
						+ "<input type='submit' value='�ύ' onclick='return checkVote(${vote.id});'>&nbsp;&nbsp<input type='button' value='�鿴' onclick='javascript:window.open(\""
						+ Config.getValue("ServicesContext")
						+ "/VoteResult.jsp?ID=${vote.id}\",\"VoteResult\")'></form></div>";

				// �滻����
				String item = "vote";
				Pattern p = Pattern.compile("(" + item + ")\\.", Pattern.CASE_INSENSITIVE);
				Matcher matcher = p.matcher(voteBodyContent);
				voteBodyContent = matcher.replaceAll(voteVar + ".");
				sb.append(parseVoteList(voteBodyContent, voteVar));
				sb.append("<"+prefix+"}"+prefix+">");
			} else {// Ĭ�ϵ�����ʽ������js
				String jspContent = "" + "<"+prefix+"write(TemplateData.getVote(\"" + name + "\",\"" + id + "\"));"+prefix+">";
				sb.append(jspContent);
			}

		}
		sb.append(content.substring(lastEndIndex));
		content = sb.toString();
	}

	private String parseVoteList(String parseContent, String voteVar) {
		Matcher m = cmsList.matcher(parseContent);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		int varIndex = 0;
		while (m.find(lastEndIndex)) {
			sb.append(parseContent.substring(lastEndIndex, m.start()));
			lastEndIndex = m.end();
			Mapx map = getAttrMap(m.group(1));
			String listContent = m.group(2);

			String item = ((String) map.get("item")).toLowerCase();
			String countStr = (String) map.get("count");
			String begin = (String) map.get("begin");
			String var = (String) map.get("var");

			if (StringUtil.isEmpty(var)) {
				var = item + "_" + varIndex;
				Pattern p = Pattern.compile("(" + item + ")\\.", Pattern.CASE_INSENSITIVE);
				Matcher matcher = p.matcher(listContent);
				listContent = matcher.replaceAll(item + "_" + varIndex + ".");
			}

			listContent = parseSubList(listContent, System.currentTimeMillis(), item, var);

			int count = 0;
			if (StringUtil.isNotEmpty(countStr) && StringUtil.isDigit(countStr)) {
				count = Integer.parseInt(countStr);
			} else {
				count = 20;
			}

			String beginIndex = null;
			if (StringUtil.isNotEmpty(begin) && StringUtil.isDigit(begin)) {
				beginIndex = begin;
				count += Integer.parseInt(begin);
			} else {
				beginIndex = "0";
			}

			if ("voteSubject".equalsIgnoreCase(item)) {
				String jspContent = "<!--ѭ����" + item + " count��" + count + "-->\n" + "<"+prefix+" DataTable dt_" + varIndex
						+ " = TemplateData.getVoteSubjects(" + voteVar + ".getString(\"id\")," + count + ");\n"
						+ "if(dt_" + varIndex + "==null){write(\"û���ҵ�ͶƱ�б�\");}else{\n" + "for(int i=" + beginIndex
						+ ";i<dt_" + varIndex + ".getRowCount();i++){" + "DataRow " + var + "  = dt_" + varIndex
						+ ".getDataRow(i);\n" + ""+prefix+">";
				jspContent += listContent;
				jspContent += "<"+prefix+"}}"+prefix+">";

				sb.append(jspContent);
			}
			varIndex++;
		}
		sb.append(parseContent.substring(lastEndIndex));
		return sb.toString();
	}

	private void parseComment() {
		Matcher m = cmsComment.matcher(content);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			sb.append(content.substring(lastEndIndex, m.start()));
			lastEndIndex = m.end();
			Mapx map = getAttrMap(m.group(1));
			String count = (String) map.get("count");
			String jspContent = ""
					+ "<"+prefix+" if(\"1\".equals(TemplateData.getDoc().getString(\"CommentFlag\"))){\nwrite(TemplateData.getComment(\""
					+ count + "\"));\n}"+prefix+">";
			sb.append(jspContent);
		}
		sb.append(content.substring(lastEndIndex));
		content = sb.toString();
	}

	/**
	 * ����cms:include��ǩ�����Ҷ�Ӧ�İ����ļ���������ڣ����ȡ�ļ��������ļ��еĲ㼶��ϵ��������һ�� �ļ���_level���ļ�������
	 */
	private void parseInclude() {
		Matcher m = cmsInclude.matcher(content);
		StringBuffer sb = new StringBuffer();
		String siteAlias = SiteUtil.getAlias(siteID);

		String sitePath = staticDir + "/" + siteAlias + "/";
		String templateFilePath = sitePath + "template/";
		if (isPreview) {
			sitePath = Config.getContextRealPath();
		}

		int lastEndIndex = 0;

		while (m.find(lastEndIndex)) {
			sb.append(content.substring(lastEndIndex, m.start()));
			lastEndIndex = m.end();
			Mapx map = getAttrMap(m.group(1));
			String file = (String) map.get("file");
			file = file.replaceAll("\\.\\./", "").replaceAll("/+", "/");
			if (isPreview && file.startsWith(Config.getContextPath())) {
				file = StringUtil.replaceEx(file, Config.getContextPath(), "/");
			}
			String includeDir = sitePath + file;
			String includePath = file.substring(0, file.lastIndexOf("/") + 1);

			includeDir = includeDir.substring(0, includeDir.lastIndexOf("/") + 1);

			String fileName = file.substring(file.lastIndexOf("/") + 1);
			if (StringUtil.isEmpty(fileName)) {
				Errorx.addError("cms:includeû������file���ԣ����顣");
			} else {
				String includeContent = null;
				File includeFile = new File(sitePath + file);
				if (!includeFile.exists()) {
					includeFile = new File(templateFilePath + fileName);
					if (!includeFile.exists()) {
						Errorx.addError("�ļ�" + sitePath + file + "�����ڣ����顣");
					} else {
						File includeFileDir = new File(includeDir);
						if (!includeFileDir.exists()) {
							includeFileDir.mkdirs();
						}
						includeContent = FileUtil.readText(includeFile);
						if (includeContent.indexOf("${") != -1) {
							includeContent = "���ȷ�������ҳ��Ƭ�Σ�" + (String) map.get("file");
						}
					}
				} else {
					includeContent = FileUtil.readText(includeFile);
				}
				String levelStr = getLevelStr();
				String fileLevelName = fileName.substring(0, fileName.lastIndexOf('.')) + "_" + dirLevel
						+ fileName.substring(fileName.lastIndexOf('.'));

				this.isPageBlock = false;
				includeContent = dealResource(includeContent, levelStr);

				if (isPreview) { // Ԥ��ֱ�ӽ�������
					sb.append(includeContent);
				} else {
					String includeFileName = includeDir + fileLevelName;
					FileUtil.writeText(includeFileName, includeContent);
					fileList.add(includeFileName);

					if ("Y".equals(Config.getValue("Big5ConvertFlag"))) {
						String big5Html = Big5Convert.toTradition(includeContent);
						big5Html = big5Html.replaceAll("cn/", "big5/");

						String big5FileName = includeFileName.substring(0, includeFileName.lastIndexOf('.')) + "_big5"
								+ includeFileName.substring(includeFileName.lastIndexOf('.'));
						FileUtil.writeText(big5FileName, big5Html);
						LogUtil.info(big5FileName);
						fileList.add(big5FileName);
					}

					file = levelStr + includePath + fileLevelName;
					String ext = SiteUtil.getExtensionType(siteID);
					String jspContent = null;
					if("jsp".equalsIgnoreCase(ext)){
						jspContent = "<%@include file=\"" + file + "\"%>";
					}else{
						jspContent = "<!--#include virtual=\"" + file + "\"-->";
					}
					sb.append(jspContent);
				}
			}
		}
		sb.append(content.substring(lastEndIndex));
		content = sb.toString();
	}

	private String getLevelStr() {
		String levelString = "";
		if (isPreview) {
			levelString = Config.getContextPath() + Config.getValue("Statical.TargetDir") + "/"
					+ SiteUtil.getAlias(siteID) + "/";
			levelString = levelString.replace('\\', '/').replaceAll("/+", "/");
		} else {
			for (int i = 0; i < dirLevel; i++) {
				levelString += "../";
			}
		}

		return levelString;
	}

	private void parseLinkURL() {
		Matcher m = cmsLink.matcher(content);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			sb.append(content.substring(lastEndIndex, m.start()));
			lastEndIndex = m.end();
			Mapx map = getAttrMap(m.group(1));
			String type = (String) map.get("type");
			String code = (String) map.get("code");
			String name = (String) map.get("name");
			String homePageName = (String) map.get("homePageName");
			if (StringUtil.isNotEmpty(homePageName) && StringUtil.isEmpty(name)) {
				name = homePageName;
			}
			String spliter = (String) map.get("spliter");

			String jspContent = "" + "<"+prefix+"write(TemplateData.getLinkURL(\"" + type + "\",\"" + code + "\",\"" + name
					+ "\",\"" + spliter + "\"));"+prefix+">";
			sb.append(jspContent);
		}
		sb.append(content.substring(lastEndIndex));
		content = sb.toString();
	}

	/**
	 * ��������<cms:page type="currentPage"/>�ı�ǩ
	 * 
	 */
	private void parsePage() {
		Matcher m = cmsPage.matcher(content);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			sb.append(content.substring(lastEndIndex, m.start()));
			lastEndIndex = m.end();
			Mapx map = getAttrMap(m.group(1));
			String type = (String) map.get("type");
			String value = (String) map.get("value");
			String name = (String) map.get("name");
			String target = (String) map.get("target");

			String jspContent = "" + "<"+prefix+"write(TemplateData.getPage(\"" + type + "\",\"" + value + "\",\"" + name
					+ "\",\"" + target + "\"));"+prefix+">";
			sb.append(jspContent);
		}
		sb.append(content.substring(lastEndIndex));
		content = sb.toString();
	}

	private void parseVar() {
		Matcher m = cmsVar.matcher(content);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			sb.append(content.substring(lastEndIndex, m.start()));
			lastEndIndex = m.end();
			Mapx map = getAttrMap(m.group(1));
			String type = (String) map.get("type");
			if (StringUtil.isNotEmpty(type)) {
				type = type.toLowerCase();
			}
			String name = (String) map.get("name");
			String title = (String) map.get("title");

			if (StringUtil.isEmpty(name)) {
				name = title;
			}

			if (StringUtil.isEmpty(name)) {
				sb.append("cms:var��ǩ�е�name���Բ���Ϊ�ա�");
			} else {
				String listContent = m.group(2);
				long index = OrderUtil.getDefaultOrder();
				String newVar = type + "_" + index;
				Pattern p = Pattern.compile("(" + type + ")\\.", Pattern.CASE_INSENSITIVE);
				Matcher matcher = p.matcher(listContent);
				listContent = matcher.replaceAll(type + "_" + index + ".");

				String jspContent = null;
				if ("article".equalsIgnoreCase(type) || "image".equalsIgnoreCase(type)
						|| "video".equalsIgnoreCase(type) || "audio".equalsIgnoreCase(type)
						|| "attachment".equalsIgnoreCase(type)) {
					jspContent = "<"+prefix+" DataRow " + newVar + "=TemplateData.getDocument(\"" + type + "\",\"" + name
							+ "\"); if(" + newVar + "==null){write(\"û���ҵ�����" + name + "+\");}else{"+prefix+">";
				} else if ("catalog".equalsIgnoreCase(type)) {
					jspContent = "<"+prefix+" DataRow " + newVar + "=TemplateData.getCatalog(\"" + type + "\",\"" + name
							+ "\"); if(" + newVar + "==null){write(\"û���ҵ�����" + name + "+\");}else{"+prefix+">";
				}

				sb.append(jspContent);
				sb.append(listContent);
				sb.append("<"+prefix+"}"+prefix+">");
			}
		}
		sb.append(content.substring(lastEndIndex));
		content = sb.toString();
	}

	public boolean parse() {
		if (content == null) {
			return false;
		}
		
		String ext = SiteUtil.getExtensionType(siteID);
		if("jsp".equalsIgnoreCase(ext)){
			prefix = "#";
		}
		
		StringBuffer initContent = new StringBuffer();
		initContent.append(content);
		content = initContent.toString();

		processPath();
		parseInclude();
		parseImagePlayer();
		parseAD();
		parseForm();
		parseVote();
		parseComment();
		parseLinkURL();
		parsePage();
		parseTree();
		parseVar();
		parseList();
		parsePageBreakBar();

		addHeader();
		dealBlock();

		return true;
	}

	private void dealBlock() {
		if (blockSet != null && blockSet.size() > 0) {
			// ɾ��ԭ�е�pageblock
			int index = templateFileName.indexOf(staticDir);
			templateFileName = templateFileName.substring(index + staticDir.length());
			templateFileName += "_" + dirLevel;
			trans
					.add(new QueryBuilder(
							"delete from zcpageblock where exists (select blockid from  ZCTemplateBlockRela where filename=? and blockid=zcpageblock.id)",
							templateFileName));
			trans.add(new QueryBuilder("delete  from  ZCTemplateBlockRela where filename=?", templateFileName));
			trans.add(blockSet, Transaction.INSERT);

			// ����ģ���Ӧ������block
			ZCTemplateBlockRelaSet relaSet = new ZCTemplateBlockRelaSet();
			for (int i = 0; i < blockSet.size(); i++) {
				ZCTemplateBlockRelaSchema rela = new ZCTemplateBlockRelaSchema();
				rela.setSiteID(siteID);
				rela.setFileName(templateFileName);
				rela.setBlockID(blockSet.get(i).getID());
				rela.setAddTime(new Date());
				if (User.getCurrent() != null && User.getUserName() != null) {
					rela.setAddUser(User.getUserName());
				} else {
					rela.setAddUser("SYSTEM");
				}

				relaSet.add(rela);
			}
			trans.add(relaSet, Transaction.INSERT);

			if (!trans.commit()) {
			}
		}
	}

	/**
	 * ת��һ������ a="1" c="2" ���ַ���ΪMapx
	 */
	private static Mapx getAttrMap(String str) {
		Mapx map = new Mapx();
		Matcher m = pAttr1.matcher(str);
		int lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			String value = m.group(3);
			if (value != null) {
				value = value.trim();
			}
			map.put(m.group(1).toLowerCase(), value);
			lastEndIndex = m.end();
		}

		m = pAttr2.matcher(str);
		lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			String value = m.group(2);
			if (value != null) {
				value = value.trim();
			}
			map.put(m.group(1).toLowerCase(), value);
			lastEndIndex = m.end();
		}
		return map;
	}

	public String getTemplateFileName() {
		return templateFileName;
	}

	public void setTemplateFileName(String templateFileName) {
		this.templateFileName = templateFileName;
	}

	public Errorx getError() {
		return error;
	}

	public void setError(Errorx error) {
		this.error = error;
	}

	public int getDirLevel() {
		return dirLevel;
	}

	public void setDirLevel(int dirLevel) {
		this.dirLevel = dirLevel;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isPagelock() {
		return isPageBlock;
	}

	public void setPageBlock(boolean isPageBlock) {
		this.isPageBlock = isPageBlock;
	}

	public ZCPageBlockSet getBlockSet() {
		return blockSet;
	}

	public void setBlockSet(ZCPageBlockSet blockSet) {
		this.blockSet = blockSet;
	}

	public long getSiteID() {
		return siteID;
	}

	public void setSiteID(long siteID) {
		this.siteID = siteID;
	}

	public ArrayList getFileList() {
		return fileList;
	}

	public Mapx getPageListPrams() {
		return pageListPrams;
	}

	public void setPageListPrams(Mapx pageListPrams) {
		this.pageListPrams = pageListPrams;
	}

	public boolean isPreview() {
		return isPreview;
	}

	public void setPreview(boolean isPreview) {
		this.isPreview = isPreview;
	}
}
