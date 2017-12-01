package info.zznet.znms.web.util;

import info.zznet.znms.base.constants.SystemConstants;
import info.zznet.znms.base.common.ZNMSLogger;
import info.zznet.znms.base.constants.SystemConstants;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

/**
 * 配置文件操作工具类
 * @author yuanjingtao
 *
 */
public class ConfigUtil {
	//property配置文件的名称
	private static List<String> propertyList = new ArrayList<String>();
    static{
    	propertyList.add("common.properties");
    	propertyList.add("system-config.properties");
    }
    //加载完成后的配置信息map
	private static Map<String,String> configMap =new HashMap<String,String>();
	//加载配置
	public static void  loadConfigure() {
		//从文件中加载
		loadConfigFromFile();
	}
	/**
	 * 从文件中加载配置文件信息
	 */
	private static void loadConfigFromFile() {
		for(String property : propertyList){
			Properties props = new Properties();
			File configFile = new File(SystemConstants.DEPLOY_PATH, property);
			if(!configFile.exists()){
				configFile = new File(PathUtil.getPropertyPath(), property);
			}
			if (configFile.exists()) {
				FileInputStream in = null;
				try {
					in = new FileInputStream(configFile);
					props.load(in);
				} catch (Throwable e) {
					ZNMSLogger.error("", e);
				} finally {
					IOUtil.closeQuietly(in);
				}
			}
			for(Map.Entry<Object, Object> e : props.entrySet()) {
				try {
					String key = (String) e.getKey();
					String value = (String) e.getValue();
					put(key,value);
				} catch (Throwable ex) {
					ZNMSLogger.error("parse common.properties failed.", ex);
				}
			}
		}
	}
	
	/**
	 * 向配置文件map中添加数据
	 * @param key  配置信息key
	 * @param value  配置信息value
	 */
	public static void put(String key, String value) {
		if (StringUtils.isNotEmpty(key)){
			configMap.put(key, value);
			ZNMSLogger.debug("put config.key:" + key + ",vlaue:" + value);
		}
	}
	
	/**
	 * 从配置信息中根据key获取int类型的值
	 * @param key 配置信息key
	 * @param defaultValue 默认的值
	 * @return
	 */
	public static Integer getInt(String key, Integer defaultValue) {
		String value = configMap.get(key);
		Integer returnInt = defaultValue;
		if (value != null) {
			returnInt = Integer.parseInt(value);
		}
		return returnInt;
	}
	
	/**
	 * 从配置信息中根据key获取int类型的值
	 * @param key 配置信息key
	 * @return
	 */
	public static Integer getInt(String key) {
		return getInt(key,null);
	}
	
	/**
	 * 从配置信息中根据key获取String类型的值
	 * @param key 配置信息key
	 * @param defaultValue 默认的值
	 * @return
	 */
	public static String getString(String key, String defaultValue) {
		String value = configMap.get(key);
		return value == null ? defaultValue : value;
	}

	/**
	 * 从配置信息中根据key获取String类型的值
	 * @param key 配置信息key
	 * @return
	 */
	public static String getString(String key) {
		return getString(key,null);
	}

	/**
	 * 从配置信息中根据key获取Boolean类型的值
	 * @param key 配置信息key
	 * @param defaultValue 默认的值
	 * @return
	 */
	public static Boolean getBoolean(String key, Boolean defaultValue) {
		String value = configMap.get(key);
		if ("1".equals(value)||"0".equals(value)){
			return "1".equals(value)?true:false;
		}
		else{
			return value == null ? defaultValue : Boolean.valueOf(value);
		}
	}

	/**
	 * 从配置信息中根据key获取Boolean类型的值
	 * @param key 配置信息key
	 * @return
	 */
	public static Boolean getBoolean(String key) {
		return getBoolean(key,null);
	}
}
