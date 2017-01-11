package com.nswt.framework.extend;

import com.nswt.framework.ConfigLoader;
import com.nswt.framework.ConfigLoader.NodeData;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.Mapx;

/**
 * 日期 : 2016-11-7 <br>
 * 作者: NSWT <br>
 * 邮箱：nswt@nswt.com.cn <br>
 */
public class ExtendManager {
	private static Mapx extendMap;
	private static Object mutex = new Object();

	public static void loadConfig() {
		if (extendMap == null) {
			extendMap = new Mapx();
			NodeData[] datas = ConfigLoader.getNodeDataList("*.extend.action");
			if(datas != null){
				for (int i = 0; i < datas.length; i++) {
					String className = datas[i].getAttributes().getString("class");
					String enable = datas[i].getAttributes().getString("enable");
					try {
						Object obj = Class.forName(className).newInstance();
						if (!(obj instanceof IExtendAction)) {
							LogUtil.getLogger().warn("类" + className + "必须继承IExtendAction!");
							continue;
						}
						if (!"false".equals(enable)) {
							IExtendAction action = (IExtendAction) obj;
							Mapx targetMap = (Mapx) extendMap.get(action.getTarget());
							if (targetMap == null) {
								targetMap = new Mapx();
							}
							targetMap.put(action.getClass().getName(), action);
							extendMap.put(action.getTarget(), targetMap);
						}
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public static void addExtendAction(IExtendAction action, String target) {
		synchronized (mutex) {
			Mapx targetMap = (Mapx) extendMap.get(target);
			if (targetMap == null) {
				targetMap = new Mapx();
				extendMap.put(target, targetMap);
			}
			targetMap.put(action.getClass().getName(), action);
		}
	}

	public static boolean hasAction(String target) {
		loadConfig();
		return extendMap.get(target) != null;
	}

	public static IExtendAction[] find(String target) {
		loadConfig();
		Mapx targetMap = (Mapx) extendMap.get(target);
		if (targetMap == null) {
			return new IExtendAction[0];
		} else {
			IExtendAction[] arr = new IExtendAction[targetMap.size()];
			Object[] vs = targetMap.valueArray();
			for (int i = 0; i < arr.length; i++) {
				arr[i] = (IExtendAction) vs[i];
			}
			return arr;
		}
	}

	public static void executeAll(String target, Object[] args) {
		IExtendAction[] actions = find(target);
		for (int i = 0; i < actions.length; i++) {
			actions[i].execute(args);
		}
	}
}
