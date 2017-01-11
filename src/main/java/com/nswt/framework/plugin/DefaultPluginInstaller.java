package com.nswt.framework.plugin;

/**
 * ���� : 2016-11-7 <br>
 * ����: NSWT <br>
 * ���䣺nswt@nswt.com.cn <br>
 */
public class DefaultPluginInstaller implements IPluginInstaller {
	private String pluginID;

	public DefaultPluginInstaller(String pluginID) {
		this.pluginID = pluginID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nswt.framework.plugin.IPluginInstaller#install()
	 */
	public int install() {
		IPlugin plugin = PluginManager.getPlugin(pluginID);
		PluginUtil.installMenu(plugin);
		PluginUtil.installTable(plugin);
		PluginUtil.installCronTask(plugin);
		PluginUtil.installCache(plugin);
		PluginUtil.installExtend(plugin);
		PluginUtil.installFile(plugin);
		return PluginManager.INSTALL_SUCCESS;
	}

}
