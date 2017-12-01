package info.zznet.znms.web.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class BeanHelper {
	public static Map<String, Object> convertBeanToMap(Object bean) {
		if(bean == null){
			return null;
		}
		Field[] fields = bean.getClass().getDeclaredFields();
		HashMap<String, Object> data = new HashMap<String, Object>();
		for (Field field : fields) {
			field.setAccessible(true);
			try {
				data.put(field.getName(), field.get(bean));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return data;
	}
}
