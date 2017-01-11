package com.nswt.statical.template;

import java.io.File;

import com.nswt.framework.Config;
import com.nswt.framework.utility.FileUtil;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.Mapx;
import com.nswt.framework.utility.StringUtil;
import com.nswt.statical.exception.TemplateCompileException;
import com.nswt.statical.exception.TemplateNotFoundException;

/**
 * @Author NSWT
 * @Date 2016-5-29
 * @Mail nswt@nswt.com.cn
 */
public class TemplateManager {
	private static TemplateClassLoader loader;
	private static Object mutex = new Object();

	public static TemplateBase find(String fileName) throws TemplateCompileException, TemplateNotFoundException {
		long t = System.currentTimeMillis();
		if (loader == null) {
			synchronized (mutex) {
				if (loader == null) {
					initLoader();
				}
			}
		}
		String className = TemplateCompiler.getClassName(fileName);
		Class clazz = loadClass(className, fileName);
		try {
			TemplateBase template = (TemplateBase) clazz.newInstance();
			if (isNeedCheck(className)) {
				File f = new File(fileName);
				if (!f.exists()) {
					throw new TemplateNotFoundException("模板" + fileName + "不存在!");
				}
				if (f.lastModified() != template.getLastModified()) {
					try {
						TemplateCompiler tc = new TemplateCompiler();
						tc.compile(fileName);
						initLoader();// 和未找到不同，模板更新必须重置ClassLoader
						clazz = loadClass(className, fileName);
						template = (TemplateBase) clazz.newInstance();
					} catch (TemplateCompileException e1) {
						throw new TemplateNotFoundException("模板" + fileName + "不能正确编译：\n" + e1.getMessage());
					}
				}
			}
			long cost = (System.currentTimeMillis() - t);
			if (cost > 50) {
				LogUtil.info("载入模板" + fileName + "耗时" + cost + "毫秒");
			}
			return template;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static Class loadClass(String className, String fileName) throws TemplateCompileException,
			TemplateNotFoundException {
		Class clazz = null;
		try {
			clazz = loader.loadClass(className);
		} catch (ClassNotFoundException e) {
			try {
				TemplateCompiler tc = new TemplateCompiler();
				tc.compile(fileName);
			} catch (TemplateCompileException e1) {
				throw e1;
			}
		}
		if (clazz == null) {// 编译后再次加载
			try {
				clazz = loader.loadClass(className);
			} catch (ClassNotFoundException e) {
				// e.printStackTrace();
				throw new TemplateNotFoundException("模板" + fileName + "编译后的类文件不能正常载入!");
			}
		}
		return clazz;
	}

	private static Mapx map = new Mapx();

	public static int ScanInterval = 5;// 5秒检查一次

	private static boolean isNeedCheck(String className) {
		long lastTime = map.getLong(className);
		if (System.currentTimeMillis() - lastTime > ScanInterval * 1000) {
			return true;
		}
		return false;
	}

	private static void initLoader() {
		loader = new TemplateClassLoader(TemplateManager.class.getClassLoader(), TemplateCompiler.getClassDirectory());
	}

	public static String getFileText(String fileName) {
		fileName = FileUtil.normalizePath(fileName);
		fileName = StringUtil.replaceEx(fileName, "/template/", "/");
		return FileUtil.readText(fileName);
	}

	public static void main(String[] args) {
		try {
			TemplateBase template = TemplateManager.find(Config.getContextRealPath()
					+ "wwwroot/nswtpDemo/template/kjindex.html");
			// template = TemplateManager.find(Config.getContextRealPath() + "wwwroot/nswtpDemo/template/list.html");
			System.out.println(template.getTemplateFilePath());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
