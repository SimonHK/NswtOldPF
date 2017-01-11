package com.nswt.statical.template;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Javac;
import org.apache.tools.ant.types.Path;

import com.nswt.framework.Config;
import com.nswt.framework.extend.ExtendManager;
import com.nswt.framework.utility.Errorx;
import com.nswt.framework.utility.FileUtil;
import com.nswt.framework.utility.StringUtil;
import com.nswt.framework.utility.Treex;
import com.nswt.framework.utility.Treex.TreeNode;
import com.nswt.framework.utility.Treex.TreeNodeList;
import com.nswt.statical.exception.TemplateCompileException;
import com.nswt.statical.exception.TemplateNotFoundException;
import com.nswt.statical.tag.TagBase;
import com.nswt.statical.tag.TagManager;

/**
 * @Author NSWT
 * @Date 2016-5-30
 * @Mail nswt@nswt.com.cn
 */
public class TemplateCompiler {
	private int VarID = 0;
	private String fileName;
	private TemplateConfig config;
	private TreeNode currentNode;
	private String currenPrefix;
	private long lastModified;
	private TemplatePrintWriter out;
	private String className;
	private ArrayList importList = new ArrayList();

	public String getCurrenPrefix() {
		return currenPrefix;
	}

	public void addImport(String name) {
		importList.add(name);
	}

	public int getVarID() {
		return VarID;
	}

	public String getFileName() {
		return fileName;
	}

	public TemplateConfig getConfig() {
		return config;
	}

	public TreeNode getCurrentNode() {
		return currentNode;
	}

	public TemplatePrintWriter getPrintWriter() {
		return out;
	}

	public void compile(String templateFileName) throws TemplateNotFoundException, TemplateCompileException {
		if (StringUtil.isEmpty(className)) {
			className = getClassName(templateFileName);
		}
		String destDirName = getClassDirectory();
		TemplateCompiler tc = new TemplateCompiler();
		tc.compileToSource(templateFileName);
		String prefix = destDirName + className.replace('.', '/');
		File f = new File(prefix.substring(0, prefix.lastIndexOf("/")));
		f.mkdirs();
		FileUtil.writeText(prefix + ".java", tc.getPrintWriter().getResult());

		Javac jc = new Javac();
		jc.setProject(new Project());
		jc.setFork(false);
		jc.setSrcdir(new Path(jc.getProject(), f.getAbsolutePath()));
		jc.setDestdir(new File(destDirName));
		jc.setClasspath(new Path(jc.getProject(), Config.getContextRealPath() + "WEB-INF/lib;"
				+ Config.getContextRealPath() + "WEB-INF/classes"));
		PrintStream syserr = System.err;
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			PrintStream ps = new PrintStream(os);
			System.setErr(ps);
			jc.setDebug(true);
			jc.setDebugLevel("source,lines");
			jc.execute();
		} catch (BuildException e) {
			throw new TemplateCompileException(new String(os.toByteArray()));
		} finally {
			System.setErr(syserr);
		}
	}

	public void compileToSource(String templateFileName) throws TemplateNotFoundException, TemplateCompileException {
		if (StringUtil.isEmpty(className)) {
			className = getClassName(templateFileName);
		}
		fileName = FileUtil.normalizePath(templateFileName);
		lastModified = new File(fileName).lastModified();
		TemplateParser parser = new TemplateParser(fileName);

		// 增加扩展点，以便于解析之间预处理模板文本
		ExtendManager.executeAll("Template.BeforeParse", new Object[] { parser });
		parser.parse();
		if (Errorx.hasError()) {
			throw new TemplateCompileException(Errorx.getAllMessage());
		}
		// 遍历树，生成待编译的文件
		Treex tree = parser.getTree();
		config = parser.getConfig();

		// 增加扩展点，以便于编译之前进行引入类和包、设置相对路径等操作
		ExtendManager.executeAll("Template.BeforeCompile", new Object[] { this });
		compile(tree, config);
		if (Errorx.hasError()) {
			throw new TemplateCompileException(Errorx.getAllMessage());
		}
	}

	public void compile(Treex tree, TemplateConfig config) {
		String shortClassName = className.substring(className.lastIndexOf(".") + 1);
		shortClassName = shortClassName.substring(0, 1).toUpperCase() + shortClassName.substring(1);
		VarID = 0;
		out = new TemplatePrintWriter(null);
		String packageName = className.substring(0, className.lastIndexOf("."));
		out.print("package " + packageName + ";\n\n");
		out.print("import java.util.*;\n");
		out.print("import com.nswt.framework.utility.Treex;\n");
		out.print("import com.nswt.framework.utility.Treex.TreeNode;\n");
		out.print("import com.nswt.framework.utility.Treex.TreeIterator;\n");
		out.print("import com.nswt.statical.exception.*;\n");
		out.print("import com.nswt.statical.template.*;\n");
		out.print("import com.nswt.statical.tag.*;\n");
		out.print("import com.nswt.statical.tag.impl.*;\n");
		out.print("import com.nswt.statical.tag.modifiers.*;\n");

		for (int i = 0; i < importList.size(); i++) {
			out.print("import " + importList.get(i) + ";\n");
		}
		out.print("\npublic class " + shortClassName + " extends TemplateBase{\n");

		// getConfig()方法
		out.print("\tprivate Treex _tree;\n");
		out.print("\tpublic TemplateConfig getConfig() {\n");
		if (config.getName() == null) {
			out.print("\t\tString Name = null;\n");
		} else {
			out.print("\t\tString Name = \"" + config.getName() + "\";\n");
		}
		if (config.getType() == null) {
			out.print("\t\tString Type = null;\n");
		} else {
			out.print("\t\tString Type = \"" + config.getType() + "\";\n");
		}
		if (config.getAuthor() == null) {
			out.print("\t\tString Author = null;\n");
		} else {
			out.print("\t\tString Author = \"" + config.getAuthor() + "\";\n");
		}
		out.print("\t\tdouble Version = " + config.getVersion() + ";\n");
		if (config.getDescription() == null) {
			out.print("\t\tString Description = null;\n");
		} else {
			out.print("\t\tString Description = \"" + config.getDescription() + "\";\n");
		}
		out.print("\t\tString ScriptStart = \"" + config.getScriptStart() + "\";\n");
		out.print("\t\tString ScriptEnd = \"" + config.getScriptEnd() + "\";\n");
		out.print("\t\tlong lastModified = " + lastModified + "L;\n");
		out.print("\t\tTemplateConfig config = new TemplateConfig(Type, Name, Author, Version, "
				+ "Description, ScriptStart, ScriptEnd,lastModified);\n");
		out.print("\t\treturn config;\n");
		out.print("\t}\n\n");

		// getTemplateFilePath()方法
		out.print("\tpublic String getTemplateFilePath() {\n");
		out.print("\t\treturn \"" + fileName.substring(0, fileName.lastIndexOf("/") + 1) + "\";\n");
		out.print("\t}\n\n");

		// getTagTree()方法
		out.print("\tpublic Treex getTagTree() throws TemplateCompileException{\n");
		out.print("\t\tif(_tree!=null){\n");
		out.print("\t\t\treturn _tree;\n");
		out.print("\t\t}\n");
		out.print("\t\t_tree = new Treex();\n");
		out.print("\t\tTreeNode current = _tree.getRoot();\n");
		TreeNodeList list = tree.getRoot().getChildren();
		for (int i = 0; i < list.size(); i++) {
			compileNodeForTree(list.get(i), "\t\t");
		}
		out.print("\t\treturn _tree;\n");
		out.print("\t}\n\n");
		if (Errorx.hasError()) {
			return;
		}
		VarID = 0;

		// execute()方法
		out.print("\tpublic void execute() throws TemplateRuntimeException{\n");
		list = tree.getRoot().getChildren();
		for (int i = 0; i < list.size(); i++) {
			compileNode(list.get(i), "\t\t");
		}
		out.print("\t}\n}");
	}

	public void compileNode(TreeNode node, String prefix) {
		currentNode = node;
		currenPrefix = prefix;
		TemplateFragment tf = (TemplateFragment) node.getData();
		if (tf.Type == TemplateFragment.FRAGMENT_HTML) {
			String[] arr = tf.FragmentText.split("\\n");
			for (int i = 0; i < arr.length; i++) {
				String line = arr[i];
				if (i != arr.length - 1) {
					line = StringUtil.rightTrim(line);
					out.print(prefix + "out.println(\"");
				} else {
					out.print(prefix + "out.print(\"");
				}
				out.print(StringUtil.javaEncode(line) + "\");\n");
			}
		}
		if (tf.Type == TemplateFragment.FRAGMENT_HOLDER) {
			out.print(prefix + "out.print(context.getHolderValue(\"" + tf.FragmentText + "\"));\n");
		}
		if (tf.Type == TemplateFragment.FRAGMENT_SCRIPT) {
			if (tf.FragmentText.startsWith("=")) {
				out.print(prefix + "out.print(" + tf.FragmentText.substring(1) + ");\n");
			} else {
				out.print(prefix + tf.FragmentText);
			}
		}
		if (tf.Type == TemplateFragment.FRAGMENT_TAG) {
			try {
				compileTag(node, prefix);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static String[] iterativeVarNames = new String[] { "i", "j", "k", "m", "n", "p", "q", "t", "a", "b", "c",
			"d", "e", "f", "g", "h", "l", "o", "r", "s", "u", "v", "w", "x", "y", "z" };

	public void compileTag(TreeNode node, String prefix) throws Exception {
		TemplateFragment tf = (TemplateFragment) node.getData();
		TagBase tag = TagManager.getTagByName(tf.TagPrefix, tf.TagName);
		String className = tag.getClass().getName();
		className = className.substring(className.lastIndexOf(".") + 1);
		String varName = className.substring(0, 1).toLowerCase() + className.substring(1) + VarID++;
		out.print(prefix + className + " " + varName + " = new " + className + "();\n");
		// out.print(prefix + varName + ".setBodyContent(\"" +
		// StringUtil.javaEncode(tf.FragmentText) + "\");\n");
		out.print(prefix + varName + ".onEnter(this);\n");
		outputTagNodeIndex(node, out, varName, prefix);
		Object[] ks = tf.Attributes.keyArray();
		for (int i = 0; i < ks.length; i++) {
			out.print(prefix + varName + ".setAttribute(\"" + ks[i] + "\",\"" + tf.Attributes.getString(ks[i])
					+ "\");\n");
		}
		out.print(prefix + "if(context.isEditTime()){\n");
		out.print(prefix + "\t" + varName + ".editWrapStart();\n");
		out.print(prefix + "}\n");
		if (!tag.onCompileTime(this, varName)) {// 由标签自己决定输出什么语句
			out.print(prefix + "if(" + varName + ".onTagStart()!=TagBase.SKIP){\n");
			if (tag.isIterative(this)) {
				if (node.getLevel() > 26) {
					Errorx.addError("子循环层次超过26层！");
					return;
				}
				String varIterative = iterativeVarNames[node.getLevel() - 1];
				out.print(prefix + "\tfor(int " + varIterative + "=0;" + varName + ".prepareNext();" + varIterative
						+ "++){\n");
				out.print(prefix + "\t\tcontext.addDataVariable(\"i\",new Integer(" + varIterative + "));\n");
				TreeNodeList list = node.getChildren();
				for (int i = 0; i < list.size(); i++) {
					compileNode(list.get(i), prefix + "\t\t");
				}
				out.print(prefix + "\t}\n");
			} else {
				TreeNodeList list = node.getChildren();
				for (int i = 0; i < list.size(); i++) {
					compileNode(list.get(i), prefix + "\t");
				}
			}
			out.print(prefix + "\tif(" + varName + ".onTagEnd()==TagBase.END){\n");
			out.print(prefix + "\t\treturn;\n");
			out.print(prefix + "\t}\n");
			out.print(prefix + "}\n");
		}
		out.print(prefix + "if(context.isEditTime()){\n");
		out.print(prefix + "\t" + varName + ".editWrapEnd();\n");
		out.print(prefix + "}\n");
		out.print(prefix + varName + ".onExit();\n");
	}

	/**
	 * 根据TemplateParser解析结果，确定标签在标签树中的位置
	 */
	private static void outputTagNodeIndex(TreeNode node, TemplatePrintWriter out, String varName, String prefix) {
		TreeNode parent = node.getParent();
		TreeNode current = node;
		ArrayList arr = new ArrayList(6);
		while (parent != null) {
			TreeNodeList list = parent.getChildren();
			int index = -1;
			for (int i = 0; i < list.size(); i++) {
				TemplateFragment tf = (TemplateFragment) list.get(i).getData();
				if (tf.Type == TemplateFragment.FRAGMENT_TAG) {
					index++;
				}
				if (list.get(i) == current) {// 自身
					break;
				}
			}
			arr.add(new Integer(index));
			current = parent;
			parent = parent.getParent();
		}
		StringBuffer sb = new StringBuffer();
		for (int i = arr.size() - 1; i >= 0; i--) {
			sb.append(arr.get(i));
			if (i != 0) {
				sb.append(",");
			}
		}
		out.print(prefix + varName + ".setTagIndexes(new int[]{" + sb + "});\n");
	}

	public void compileNodeForTree(TreeNode node, String prefix) {
		currentNode = node;
		currenPrefix = prefix;
		TemplateFragment tf = (TemplateFragment) node.getData();
		if (tf.Type == TemplateFragment.FRAGMENT_TAG) {
			try {
				compileTagForTree(node, prefix);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void compileTagForTree(TreeNode node, String prefix) throws Exception {
		TemplateFragment tf = (TemplateFragment) node.getData();
		TagBase tag = TagManager.getTagByName(tf.TagPrefix, tf.TagName);
		if (tag == null) {
			Errorx.addError("第" + tf.StartLineNo + "行有错误，不支持的标签：<" + tf.TagPrefix + ":" + tf.TagName + ">");
			return;
		}
		String className = tag.getClass().getName();
		className = className.substring(className.lastIndexOf(".") + 1);
		String varName = "tag" + VarID++;
		out.print(prefix + "TagBase " + varName + " = new " + className + "();\n");
		out.print(prefix + "current = current.addChild(" + varName + ");\n");
		out.print(prefix + varName + ".setStartLineNo(" + tf.StartLineNo + ");\n");
		out.print(prefix + varName + ".setTagTreeNode(current);\n");
		Object[] ks = tf.Attributes.keyArray();
		for (int i = 0; i < ks.length; i++) {
			out.print(prefix + varName + ".setAttribute(\"" + ks[i] + "\",\"" + tf.Attributes.getString(ks[i])
					+ "\");\n");
		}
		TreeNodeList list = node.getChildren();
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				compileNodeForTree(list.get(i), prefix + "\t\t");
			}
		}
		out.print(prefix + "current = current.getParent();\n");
		out.print("\n");
	}

	public static String getClassName(String templateFileName) throws TemplateNotFoundException {
		String prefix = Config.getContextRealPath();
		prefix = FileUtil.normalizePath(prefix);
		templateFileName = FileUtil.normalizePath(templateFileName);
		if (!templateFileName.startsWith(prefix)) {
			throw new TemplateNotFoundException("模板" + templateFileName + "不在正确的目录下!");
		}
		String str = templateFileName.substring(prefix.length());
		if (str.startsWith("/")) {
			str = str.substring(1);
		}
		String[] arr = str.split("\\/");
		StringBuffer sb = new StringBuffer(128);
		for (int i = 0; i < arr.length; i++) {
			String part = arr[i];
			if (StringUtil.isNotEmpty(part)) {
				part = part.replaceAll("\\W", "_");
				if (i != arr.length - 1) {
					part = part.toLowerCase();
				} else {
					part = part.substring(0, 1).toUpperCase() + part.substring(1);
				}
				if (i != 0) {
					sb.append(".");
				}
				sb.append(part);
			}
		}
		return sb.toString();
	}

	public static String getClassDirectory() {
		return Config.getContextRealPath() + "WEB-INF/template/";
	}

	public static void main(String[] args) {
		TemplateCompiler tc = new TemplateCompiler();
		File f = new File(Config.getContextRealPath() + "wwwroot/nswtpDemo/template/");
		File[] fs = f.listFiles();
		for (int i = 0; i < fs.length; i++) {
			if (fs[i].getName().endsWith(".html")) {
				try {
					tc.compileToSource(fs[i].getAbsolutePath());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
