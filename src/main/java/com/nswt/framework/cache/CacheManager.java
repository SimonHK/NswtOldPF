package com.nswt.framework.cache;

import com.nswt.framework.ConfigLoader;
import com.nswt.framework.ConfigLoader.NodeData;
import com.nswt.framework.clustering.Clustering;
import com.nswt.framework.utility.LogUtil;
import com.nswt.framework.utility.Mapx;

/**
 * @Author NSWT
 * @Date 2008-10-30
 * @Mail nswt@nswt.com.cn
 */
public class CacheManager {
	private static Object mutex = new Object();

	private static Mapx ProviderMap;

	private static void loadConfig() {
		if (ProviderMap == null) {
			ConfigLoader.load();
			ProviderMap = new Mapx();
			NodeData[] datas = ConfigLoader.getNodeDataList("*.cache.provider");
			if (datas != null) {
				for (int i = 0; i < datas.length; i++) {
					String className = datas[i].getAttributes().getString("class");
					try {
						CacheProvider cp = (CacheProvider) Class.forName(className).newInstance();
						ProviderMap.put(cp.getProviderName(), cp);
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
			CacheProvider cp = new CodeCache();
			ProviderMap.put(cp.getProviderName(), cp);
		}
	}

	/**
	 * 获取指定类型的的CacheProvider
	 */
	public static CacheProvider getCache(String type) {
		if (ProviderMap == null) {
			synchronized (mutex) {
				loadConfig();
			}
		}
		return (CacheProvider) ProviderMap.get(type);
	}

	/**
	 * 获取缓存数据
	 */
	public static Object get(String providerName, String type, int key) {
		return get(providerName, type, String.valueOf(key));
	}

	public static Object get(String providerName, String type, long key) {
		return get(providerName, type, String.valueOf(key));
	}

	public static Object get(String providerName, String type, Object key) {
		CacheProvider cp = getCache(providerName);
		if (cp == null) {
			throw new RuntimeException("未找到CacheProvider:" + providerName);
		}
		Mapx map = (Mapx) cp.TypeMap.get(type);
		if (map == null) {
			synchronized (mutex) {
				if (Clustering.isClustering()) {// 集群下先从缓存中取
					map = Clustering.getMapx(cp.getProviderName() + "_" + type);
					if (map == null) {
						cp.onTypeNotFound(type);
						map = (Mapx) cp.TypeMap.get(type);
						if (map != null) {
							Clustering.putMapx(cp.getProviderName() + "_" + type, map);
						}
					} else {
						cp.TypeMap.put(type, map);
					}
				} else {
					if (map == null) {
						cp.onTypeNotFound(type);
					}
				}
				map = (Mapx) cp.TypeMap.get(type);
				if (map == null) {
					LogUtil.warn("指定的CacheProvider:" + providerName + "下不存在类型为" + type + "的缓存。");
					return null;
				}
			}
		}
		if (!map.containsKey(key)) {
			synchronized (mutex) {
				if (Clustering.isClustering()) {// 集群下先从缓存中取
					if (Clustering.containsKey(cp.getProviderName() + "_" + type + "." + key)) {
						String str = Clustering.get(cp.getProviderName() + "_" + type + "." + key);
						map.put(key, str);
					} else {
						cp.onKeyNotFound(type, key);
						if (map.containsKey(key)) {
							Clustering.put(cp.getProviderName() + "_" + type + "." + key, map.getString(key));
						}
					}
				} else if (!map.containsKey(key)) {
					cp.onKeyNotFound(type, key);
				}
				if (!map.containsKey(key)) {
					LogUtil.warn("获取缓存数据失败:" + providerName + "," + type + "," + key);
				}
			}
		}
		return map.get(key);
	}

	/**
	 * 更新缓存数据
	 */
	public static void set(String providerName, String type, int key, Object value) {
		set(providerName, type, String.valueOf(key), value);
	}

	public static void set(String providerName, String type, long key, Object value) {
		set(providerName, type, String.valueOf(key), value);
	}

	public static void set(String providerName, String type, long key, long value) {
		set(providerName, type, String.valueOf(key), String.valueOf(value));
	}

	public static void set(String providerName, String type, long key, int value) {
		set(providerName, type, String.valueOf(key), String.valueOf(value));
	}

	public static void set(String providerName, String type, int key, long value) {
		set(providerName, type, String.valueOf(key), String.valueOf(value));
	}

	public static void set(String providerName, String type, int key, int value) {
		set(providerName, type, String.valueOf(key), String.valueOf(value));
	}

	public static void set(String providerName, String type, Object key, long value) {
		set(providerName, type, key, String.valueOf(value));
	}

	public static void set(String providerName, String type, Object key, int value) {
		set(providerName, type, key, String.valueOf(value));
	}

	public static void set(String providerName, String type, Object key, Object value) {
		CacheProvider cp = getCache(providerName);
		if (cp == null) {
			LogUtil.warn("未找到CacheProvider:" + providerName);
			return;
		}
		Mapx map = (Mapx) cp.TypeMap.get(type);
		if (map == null) {
			map = new Mapx();
			synchronized (mutex) {
				cp.TypeMap.put(type, map);
			}
		}
		synchronized (mutex) {
			map.put(key, value);
			if (Clustering.isClustering()) {// 集群下需要更新集群缓存
				Clustering.putObject(cp.getProviderName() + "_" + type + "." + key, value);
			}
			cp.onKeySet(type, key, value);
		}
	}

	/**
	 * 删除缓存数据
	 */
	public static void remove(String providerName, String type, int key) {
		remove(providerName, type, String.valueOf(key));
	}

	public static void remove(String providerName, String type, long key) {
		remove(providerName, type, String.valueOf(key));
	}

	public static void remove(String providerName, String type, Object key) {
		CacheProvider cp = getCache(providerName);
		if (cp == null) {
			LogUtil.warn("未找到CacheProvider:" + providerName);
			return;
		}
		Mapx map = (Mapx) cp.TypeMap.get(type);
		if (map == null) {
			LogUtil.warn("指定的CacheProvider:" + providerName + "下没有类型为" + type + "的缓存");
			return;
		}
		synchronized (mutex) {
			map.remove(key);
			if (Clustering.isClustering()) {// 集群下需要删除集群缓存
				Clustering.remove(cp.getProviderName() + "_" + type + "." + key);
			}
		}
	}

	/**
	 * 删除缓存类型
	 */
	public static void removeType(String providerName, String type) {
		CacheProvider cp = getCache(providerName);
		if (cp == null) {
			LogUtil.warn("未找到CacheProvider:" + providerName);
			return;
		}
		synchronized (mutex) {
			cp.TypeMap.remove(type);
			if (Clustering.isClustering()) {// 集群下需要删除集群缓存
				Clustering.remove(cp.getProviderName() + "_" + type);
			}
		}
	}

	/**
	 * 获取缓存类型对应的Mapx。<br>
	 * 注意：可能缓存中只有同一类型的一部分数据，其它数据要等待延迟加载。
	 */
	public static Mapx getMapx(String providerName, String type) {
		CacheProvider cp = getCache(providerName);
		if (cp == null) {
			LogUtil.warn("未找到CacheProvider:" + providerName);
			return null;
		}
		Mapx map = (Mapx) cp.TypeMap.get(type);
		if (map == null) {
			synchronized (mutex) {
				if (Clustering.isClustering()) {// 集群下先从缓存中取
					map = Clustering.getMapx(cp.getProviderName() + "_" + type);
					if (map == null) {
						cp.onTypeNotFound(type);
						map = (Mapx) cp.TypeMap.get(type);
						if (map != null) {
							Clustering.putMapx(cp.getProviderName() + "_" + type, map);
						}
					} else {
						cp.TypeMap.put(type, map);
					}
				} else {
					if (map == null) {
						cp.onTypeNotFound(type);
					}
				}
				map = (Mapx) cp.TypeMap.get(type);
				if (map == null) {
					LogUtil.warn("指定的CacheProvider:" + providerName + "下不存在类型为" + type + "的缓存。");
					return null;
				}
			}
		}
		return map;
	}

	/**
	 * 设置缓存类型对应的Mapx
	 */
	public static void setMapx(String providerName, String type, Mapx map) {
		CacheProvider cp = getCache(providerName);
		if (cp == null) {
			LogUtil.warn("未找到CacheProvider:" + providerName);
			return;
		}
		synchronized (mutex) {
			cp.TypeMap.put(type, map);
		}
		if (Clustering.isClustering()) {
			Clustering.putMapx(cp.getProviderName() + "_" + type, map);
		}
	}

	/**
	 * 获取缓存类型对应的Mapx，请使用getMapx(String providerName, String type)代替
	 * 
	 * @deprecated
	 */
	public static Mapx get(String providerName, String type) {
		return getMapx(providerName, type);
	}
}
