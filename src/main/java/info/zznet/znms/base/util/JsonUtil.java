package info.zznet.znms.base.util;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Title: JSON工具类</p>
 * <p>Description: 提供Json格式字符串与对象之间的相互转化</p>
 * 
 * @author 陈佳伟
 */
public final class JsonUtil {
	
    private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);
    // 全局对象映射（线程安全）
	private static ObjectMapper mapper = new ObjectMapper();

	// 不可创建本类实例
    private JsonUtil() {}
    
    static {
    	mapper.getSerializationConfig().setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));  
    	mapper.getDeserializationConfig().set(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
    
    /**
     * 将对象转化为Json格式字符串
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
    	try {
    		return mapper.writeValueAsString(obj);
    	} 
    	catch (Exception ex) {
    		logger.error("toJson error：" + obj.getClass().getName(), ex);
			return "{}";
		}
    }
    
    /**
     * 将Json格式字符串转化为对象
     * @param <T>
     * @param json
     * @param requiredType
     * @return
     */
    public static <T> T toObject(String json, Class<T> requiredType) {
    	try {
    		return mapper.readValue(json, requiredType);
    	} 
    	catch (Exception ex) {
    		logger.error("toObject error：" + json + " >> " + requiredType.getName(), ex);
			return null;
		}
    }
    
    /**
     * 将Json格式字符串转化为数组
     * @param <T>
     * @param json
     * @param requiredType
     * @return
     */
    public static <T> T[] toArray(String json, Class<T> elementType) {
    	try {
    		return mapper.readValue(json, TypeFactory.arrayType(elementType));
    	} 
    	catch (Exception ex) {
    		logger.error("toArray error：" + json + " >> " + elementType.getName() + "[]", ex);
			return null;
		}
    }
    
    /**
     * 将Json格式字符串转化为集合
     * @param <T>
     * @param json
     * @param requiredType
     * @return
     */
    public static <C extends Collection<T>, T> C toCollection(String json, Class<C> collectionType, Class<T> elementType) {
    	try {
    		return mapper.readValue(json, TypeFactory.collectionType(collectionType, elementType));
    	} 
    	catch (Exception ex) {
    		logger.error("toCollection error：" + json + " >> " + collectionType.getName() + '<' + elementType.getName() + '>', ex);
			return null;
		}
    }
    
    /**
     * 将Json格式字符串转化为映射
     * @param <T>
     * @param json
     * @param requiredType
     * @return
     */
    @SuppressWarnings("rawtypes")
	public static <K, V> Map<K, V> toMap(String json, Class<? extends Map> mapType, Class<K> keyType, Class<V> valueType) {
    	try {
    		return mapper.readValue(json, TypeFactory.mapType(mapType, keyType, valueType));
    	} 
    	catch (Exception ex) {
    		logger.error("toMap error：" + json + " >> " + mapType.getName() + '<' + keyType.getName() + ',' + valueType.getName() + '>', ex);
			return null;
		}
    }
    
}
