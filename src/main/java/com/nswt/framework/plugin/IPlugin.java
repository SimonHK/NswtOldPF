package com.nswt.framework.plugin;

import java.util.Date;

/**
 * 日期 : 2016-11-7 <br>
 * 作者: NSWT <br>
 * 邮箱：nswt@nswt.com.cn <br>
 */
public interface IPlugin {
	public String getVesion();

	public Date getPublishDate();

	public String getName();

	public String getAuthor();

	public String getPluginID();

	public PluginMenu[] getMenus();

	public String[] getExtendClasses();

	public String[] getCronTaskClases();

	public PluginTable[] getTables();

	public String[] getCacheClasses();

	public String[] getInstallFileNames();

	public String[] getUninstallFileNames();

	public IPluginInstaller getInstaller();

	public IPluginUninstaller getUninstaller();

	public void onPause();

	public void onReuse();
}
