package com.nswt.framework.plugin;

/**
 * ���� : 2016-11-7 <br>
 * ����: NSWT <br>
 * ���䣺nswt@nswt.com.cn <br>
 */
public class PluginManager {
	public static final int INSTALL_SUCCESS = 0;
	public static final int INSTALL_FAIL = 0;

	public static final int UNINSTALL_SUCCESS = 0;
	public static final int UNINSTALL_FAIL = 0;

	public static void onStartup() {

	}

	public static void install(String pluginFileName) {

	}

	public static IPlugin getPlugin(String pluginID) {
		return null;
	}

	/**
	 * ��ͣʹ�ã����в����صĲ˵�/��չ/��ʱ����/���涼������ʾ������
	 */
	public static void pause(String pluginID) {

	}

	public static void reuse(String pluginID) {

	}

	public static void uninstall(String pluginID) {

	}
}
