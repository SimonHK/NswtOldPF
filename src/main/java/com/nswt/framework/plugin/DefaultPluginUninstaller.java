package com.nswt.framework.plugin;

/**
 * 日期 : 2016-11-7 <br>
 * 作者: NSWT <br>
 * 邮箱：nswt@nswt.com.cn <br>
 */
public class DefaultPluginUninstaller implements IPluginUninstaller {
	private String pluginID;

	public DefaultPluginUninstaller(String pluginID) {
		this.pluginID = pluginID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nswt.framework.plugin.IPluginUninstaller#uninstall()
	 */
	public int uninstall() {
		System.out.println(pluginID);
		return 0;
	}

}
